package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.order.OrderRequest;
import com.azo.ecommerce.model.*;
import com.azo.ecommerce.repository.*;
import com.azo.ecommerce.service.KafkaService.KafkaProducerService;
import com.azo.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductsRepository productsRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public OrderServiceImplementation(OrderRepository orderRepository,
                                      CustomerRepository customerRepository,
                                      OrderItemRepository orderItemRepository,
                                      ShoppingCartRepository shoppingCartRepository,
                                      CartItemRepository cartItemRepository,
                                      ProductsRepository productsRepository,
                                      KafkaProducerService kafkaProducerService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderItemRepository = orderItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productsRepository = productsRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    @Transactional
    public Optional<Order> createOrder(OrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + request.getCustomerId()));

        List<ShoppingCart> carts = shoppingCartRepository.findAll().stream()
                .filter(cart -> cart.getCustomer().getCustomerId().equals(customer.getCustomerId())
                        && "ACTIVE".equalsIgnoreCase(cart.getStatus()))
                .collect(Collectors.toList());

        if (carts.isEmpty()) {
            throw new IllegalStateException("No active shopping cart found for customer: " + customer.getCustomerId());
        }

        ShoppingCart activeCart = carts.get(0);

        List<CartItem> cartItems = cartItemRepository.findAll().stream()
                .filter(item -> item.getCart().getCartId().equals(activeCart.getCartId()))
                .collect(Collectors.toList());

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Shopping cart is empty for customer: " + customer.getCustomerId());
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(request.getOrder().getOrderDate());
        order.setCreatedAt(request.getOrder().getCreatedAt());
        order.setStatus(request.getOrder().getStatus() != null ? request.getOrder().getStatus() : "PENDING");
        order.setPaymentStatus(request.getOrder().getPaymentStatus() != null ? request.getOrder().getPaymentStatus() : "PENDING");
        order.setShippingAddress(request.getOrder().getShippingAddress() != null ?
                request.getOrder().getShippingAddress() :
                customer.getAddress() + ", " + customer.getCity() + ", " + customer.getState() + " " + customer.getZipCode());

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal calculatedTotal = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new IllegalStateException("Insufficient stock for product: " + product.getName() +
                        " (Available: " + product.getStockQuantity() + ", Requested: " + cartItem.getQuantity() + ")");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());

            orderItems.add(orderItem);
            calculatedTotal = calculatedTotal.add(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productsRepository.save(product);

            String inventoryData = """
                {
                    "action": "STOCK_REDUCED",
                    "productId": %d,
                    "productName": "%s",
                    "quantityReduced": %d,
                    "remainingStock": %d,
                    "orderId": %d,
                    "timestamp": "%s"
                }
                """.formatted(
                    product.getProductId(),
                    product.getName(),
                    cartItem.getQuantity(),
                    product.getStockQuantity(),
                    savedOrder.getOrderId(),
                    java.time.Instant.now()
            );
            kafkaProducerService.sendInventoryUpdate(inventoryData);
        }

        // Save all order items
        orderItemRepository.saveAll(orderItems);

        // Update order with calculated total
        savedOrder.setTotalAmount(calculatedTotal);
        savedOrder = orderRepository.save(savedOrder);

        // Send order created event with items
        String orderData = """
            {
                "action": "ORDER_CREATED",
                "orderId": %d,
                "customerId": %d,
                "customerName": "%s %s",
                "customerEmail": "%s",
                "orderDate": "%s",
                "status": "%s",
                "paymentStatus": "%s",
                "shippingAddress": "%s",
                "totalAmount": %.2f,
                "itemCount": %d,
                "items": [%s],
                "timestamp": "%s"
            }
            """.formatted(
                savedOrder.getOrderId(),
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                savedOrder.getOrderDate(),
                savedOrder.getStatus(),
                savedOrder.getPaymentStatus(),
                savedOrder.getShippingAddress(),
                savedOrder.getTotalAmount(),
                orderItems.size(),
                orderItems.stream()
                        .map(item -> """
                        {"productId": %d, "productName": "%s", "quantity": %d, "unitPrice": %.2f, "subtotal": %.2f}"""
                                .formatted(
                                        item.getProduct().getProductId(),
                                        item.getProduct().getName(),
                                        item.getQuantity(),
                                        item.getPriceAtPurchase(),
                                        item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity()))
                                ))
                        .collect(Collectors.joining(", ")),
                java.time.Instant.now()
        );
        kafkaProducerService.sendOrderEvent(orderData);

        // Send customer notification
        String notificationData = """
            {
                "type": "ORDER_CONFIRMATION",
                "customerId": %d,
                "customerEmail": "%s",
                "orderId": %d,
                "message": "Your order #%d has been confirmed with %d items. Total: $%.2f",
                "timestamp": "%s"
            }
            """.formatted(
                customer.getCustomerId(),
                customer.getEmail(),
                savedOrder.getOrderId(),
                savedOrder.getOrderId(),
                orderItems.size(),
                savedOrder.getTotalAmount(),
                java.time.Instant.now()
        );
        kafkaProducerService.sendCustomerNotification(notificationData);

        // Send audit log
        String auditData = String.format("Customer %s %s (ID: %d) created order #%d with %d items. Total: $%.2f",
                customer.getFirstName(),
                customer.getLastName(),
                customer.getCustomerId(),
                savedOrder.getOrderId(),
                orderItems.size(),
                savedOrder.getTotalAmount()
        );
        kafkaProducerService.sendAuditLog(auditData);

        // Clear the shopping cart after successful order
        cartItemRepository.deleteAll(cartItems);
        activeCart.setStatus("COMPLETED");
        shoppingCartRepository.save(activeCart);

        return Optional.of(savedOrder);
    }

    @Override
    public Optional<Order> updateOrder(OrderRequest request) {
        Long orderId = request.getOrder().getOrderId();
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Order does not exist with ID: " + orderId);
        }

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + request.getCustomerId()));

        // Store old status for comparison
        String oldStatus = existingOrder.getStatus();
        String oldPaymentStatus = existingOrder.getPaymentStatus();

        existingOrder.setCustomer(customer);
        existingOrder.setOrderDate(request.getOrder().getOrderDate());
        existingOrder.setStatus(request.getOrder().getStatus());
        existingOrder.setPaymentStatus(request.getOrder().getPaymentStatus());
        existingOrder.setShippingAddress(request.getOrder().getShippingAddress());
        existingOrder.setTotalAmount(request.getOrder().getTotalAmount());

        Order updatedOrder = orderRepository.save(existingOrder);

        // Send order update event
        String orderUpdateData = """
            {
                "action": "ORDER_UPDATED",
                "orderId": %d,
                "customerId": %d,
                "oldStatus": "%s",
                "newStatus": "%s",
                "oldPaymentStatus": "%s",
                "newPaymentStatus": "%s",
                "timestamp": "%s"
            }
            """.formatted(
                updatedOrder.getOrderId(),
                customer.getCustomerId(),
                oldStatus,
                updatedOrder.getStatus(),
                oldPaymentStatus,
                updatedOrder.getPaymentStatus(),
                java.time.Instant.now()
        );
        kafkaProducerService.sendOrderEvent(orderUpdateData);

        // Send audit log
        String auditData = String.format("Order #%d updated. Status: %s -> %s, Payment: %s -> %s",
                updatedOrder.getOrderId(),
                oldStatus,
                updatedOrder.getStatus(),
                oldPaymentStatus,
                updatedOrder.getPaymentStatus()
        );
        kafkaProducerService.sendAuditLog(auditData);

        return Optional.of(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Order does not exist with ID: " + orderId);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // Send order cancelled event
        String orderCancelData = """
            {
                "action": "ORDER_CANCELLED",
                "orderId": %d,
                "customerId": %d,
                "totalAmount": %.2f,
                "timestamp": "%s"
            }
            """.formatted(
                order.getOrderId(),
                order.getCustomer().getCustomerId(),
                order.getTotalAmount(),
                java.time.Instant.now()
        );
        kafkaProducerService.sendOrderEvent(orderCancelData);

        // Send audit log
        String auditData = String.format("Order #%d cancelled/deleted for customer %s %s",
                order.getOrderId(),
                order.getCustomer().getFirstName(),
                order.getCustomer().getLastName()
        );
        kafkaProducerService.sendAuditLog(auditData);

        orderRepository.deleteById(orderId);
    }
}