package com.azo.ecommerce.dto.Order;

import com.azo.ecommerce.model.Customer;
import com.azo.ecommerce.model.Order;

public class OrderRequest {
    private Order order;

    private Customer customer;

    public OrderRequest() {
    }

    public OrderRequest(Order order, Customer customer) {
        this.order = order;
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
