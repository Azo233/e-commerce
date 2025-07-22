package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.orderItem.OrderItemRequest;
import com.azo.ecommerce.model.OrderItem;
import com.azo.ecommerce.model.Product;
import com.azo.ecommerce.model.Order;
import com.azo.ecommerce.repository.OrderItemRepository;
import com.azo.ecommerce.repository.OrderRepository;
import com.azo.ecommerce.repository.ProductsRepository;
import com.azo.ecommerce.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderItemServiceImplementation implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductsRepository productsRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderItemServiceImplementation(OrderItemRepository orderItemRepository, ProductsRepository productsRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productsRepository = productsRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderItem> getOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public Optional<OrderItem> getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId);
    }

    @Override
    public OrderItem createOrderItem(OrderItemRequest request) {
        Product product = productsRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + request.getProductId()));

       Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + request.getOrderId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setOrder(order);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setPriceAtPurchase(request.getPriceAtPurchase());
        orderItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return orderItemRepository.save(orderItem);
    }

    @Override
    public Optional<OrderItem> updateOrderItem(OrderItemRequest request) {
        if (!orderItemRepository.existsById(request.getOrderItemId())) {
            throw new IllegalArgumentException("OrderItem does not exist with ID: " + request.getOrderItemId());
        }

        OrderItem existingOrderItem = orderItemRepository.findById(request.getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found with ID: " + request.getOrderItemId()));

        existingOrderItem.setQuantity(request.getQuantity());
        existingOrderItem.setPriceAtPurchase(request.getPriceAtPurchase());

        if (request.getProductId() != null) {
            Product product = productsRepository.findById(request.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + request.getProductId()));
            existingOrderItem.setProduct(product);
        }

        if (request.getOrderId() != null) {
            Order order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + request.getOrderId()));
            existingOrderItem.setOrder(order);
        }

        return Optional.of(orderItemRepository.save(existingOrderItem));
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new IllegalArgumentException("OrderItem does not exist with ID: " + orderItemId);
        }
        orderItemRepository.deleteById(orderItemId);
    }
}


