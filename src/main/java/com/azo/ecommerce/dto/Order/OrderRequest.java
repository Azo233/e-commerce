package com.azo.ecommerce.dto.Order;

import com.azo.ecommerce.model.Order;

public class OrderRequest {
    private Order order;

    public OrderRequest() {
    }

    public OrderRequest(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
