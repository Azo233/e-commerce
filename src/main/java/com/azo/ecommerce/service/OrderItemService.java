package com.azo.ecommerce.service;

import com.azo.ecommerce.dto.orderItem.OrderItemRequest;
import com.azo.ecommerce.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItem> getOrderItems();

    Optional<OrderItem> getOrderItemById(Long orderItemId);

    OrderItem createOrderItem(OrderItemRequest request);

    Optional<OrderItem> updateOrderItem(OrderItemRequest request);

    void deleteOrderItem(Long orderItemId);
}
