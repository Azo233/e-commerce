package com.azo.ecommerce.dto.OrderItemRequest;

import com.azo.ecommerce.model.OrderItem;

public class OrderItemRequest {
    private OrderItem orderItem;

    public OrderItemRequest() {
    }

    public OrderItemRequest(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
