package com.azo.ecommerce.dto.customer;

import com.azo.ecommerce.model.Customer;

public class CustomerRequest {
    private Customer customer;

    public CustomerRequest() {
    }

    public CustomerRequest(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
