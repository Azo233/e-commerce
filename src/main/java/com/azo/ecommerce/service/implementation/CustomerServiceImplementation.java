package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.customer.CustomerRequest;
import com.azo.ecommerce.model.Customer;
import com.azo.ecommerce.repository.CustomerRepository;
import com.azo.ecommerce.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Customer createCustomer(CustomerRequest request) {
        if (request.getCustomer().getCustomerId() != null && customerRepository.existsById(request.getCustomer().getCustomerId())) {
            throw new IllegalArgumentException("Customer already exists with ID: " + request.getCustomer().getCustomerId());
        }
        return customerRepository.save(request.getCustomer());
    }

    @Override
    public Optional<Customer> updateCustomer(CustomerRequest request) {
        if (!customerRepository.existsById(request.getCustomer().getCustomerId())) {
            throw new IllegalArgumentException("Customer does not exist with ID: " + request.getCustomer().getCustomerId());
        }
        return Optional.of(customerRepository.save(request.getCustomer()));
    }

    @Override
    public void deleteCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer does not exist with ID: " + customerId);
        }
        customerRepository.deleteById(customerId);
    }

    @Override
    public Customer getEmail(String emailName) {
        return customerRepository.getCustomerByEmail(emailName)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with email: " + emailName));
    }
}

