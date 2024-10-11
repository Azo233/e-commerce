package com.azo.ecommerce.dto.Order;

import com.azo.ecommerce.model.Customer;
import com.azo.ecommerce.model.Order;

public class OrderRequest {
    private Order order;
    private Long customerId;

    public OrderRequest() {
    }

    public OrderRequest(Order order, Long customerId) {
        this.order = order;
        this.customerId = customerId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}

