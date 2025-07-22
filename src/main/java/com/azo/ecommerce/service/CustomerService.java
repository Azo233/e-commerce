package com.azo.ecommerce.service;

import com.azo.ecommerce.dto.customer.CustomerRequest;
import com.azo.ecommerce.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getCustomers();

    Optional<Customer> getCustomerById(Long customerId);

    Customer createCustomer(CustomerRequest request);

    Optional<Customer> updateCustomer(CustomerRequest request);

    void deleteCustomer(Long customerId);
    Customer getEmail(String emailName);
}
