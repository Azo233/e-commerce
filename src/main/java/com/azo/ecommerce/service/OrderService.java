package com.azo.ecommerce.service;

import com.azo.ecommerce.dto.order.OrderRequest;
import com.azo.ecommerce.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getOrders();

    Optional<Order> getOrderById(Long orderId);

    Optional<Order> createOrder(OrderRequest request);

    Optional<Order> updateOrder(OrderRequest request);

    void deleteOrder(Long orderId);
}
