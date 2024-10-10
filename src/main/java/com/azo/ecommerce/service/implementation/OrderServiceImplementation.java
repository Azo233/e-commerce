package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.Order.OrderRequest;
import com.azo.ecommerce.model.Order;
import com.azo.ecommerce.repository.OrderRepository;
import com.azo.ecommerce.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImplementation(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return Optional.empty();
    }

    @Override
    public Order createOrder(OrderRequest request) {
        return null;
    }

    @Override
    public Optional<Order> updateOrder(OrderRequest request) {
        return Optional.empty();
    }

    @Override
    public void deleteOrder(Long orderId) {

    }
}
