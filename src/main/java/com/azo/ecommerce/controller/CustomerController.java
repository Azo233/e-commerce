package com.azo.ecommerce.controller;

import com.azo.ecommerce.dto.customer.CustomerRequest;
import com.azo.ecommerce.model.Customer;
import com.azo.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base-url}/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Get all customers
    @GetMapping("/getAllCustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getCustomers();
    }

    // Get a customer by ID
    @GetMapping("/getCustomerById")
    public Optional<Customer> getCustomerById(@RequestParam Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    // Create a new customer
    @PostMapping("/createCustomer")
    public Customer createCustomer(@RequestBody CustomerRequest request) {
        return customerService.createCustomer(request);
    }

    // Update an existing customer
    @PutMapping("/updateCustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerRequest request) {
        Optional<Customer> updatedCustomer = customerService.updateCustomer(request);
        return updatedCustomer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a customer
    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<Void> deleteCustomer(@RequestParam Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    // Get a customer by email
    @GetMapping("/getCustomerByEmail")
    public Customer getCustomerByEmail(@RequestParam String email) {
        return customerService.getEmail(email);
    }
}

