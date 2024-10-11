package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.Order.OrderRequest;
import com.azo.ecommerce.model.Customer;
import com.azo.ecommerce.model.Order;
import com.azo.ecommerce.repository.CustomerRepository;
import com.azo.ecommerce.repository.OrderRepository;
import com.azo.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderServiceImplementation(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
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
    public Optional<Order> createOrder(OrderRequest request) {
        // Fetch the customer using the provided customerId
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + request.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer); // Set the customer based on the retrieved customer
        order.setOrderDate(request.getOrder().getOrderDate());
        order.setCreatedAt(request.getOrder().getCreatedAt());
        order.setStatus(request.getOrder().getStatus());
        order.setPaymentStatus(request.getOrder().getPaymentStatus());
        order.setShippingAddress(request.getOrder().getShippingAddress());
        order.setTotalAmount(request.getOrder().getTotalAmount());

        return Optional.of(orderRepository.save(order));
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

        existingOrder.setCustomer(customer);
        existingOrder.setOrderDate(request.getOrder().getOrderDate());
        existingOrder.setCreatedAt(request.getOrder().getCreatedAt());
        existingOrder.setStatus(request.getOrder().getStatus());
        existingOrder.setPaymentStatus(request.getOrder().getPaymentStatus());
        existingOrder.setShippingAddress(request.getOrder().getShippingAddress());
        existingOrder.setTotalAmount(request.getOrder().getTotalAmount());


        return Optional.of(orderRepository.save(existingOrder));
    }


    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Order does not exist with ID: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }
}

