package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.customer.CustomerRequest;
import com.azo.ecommerce.model.Customer;
import com.azo.ecommerce.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplementationTest {
    @Mock
    CustomerRepository mockCustomerRepository;
    @InjectMocks
    private CustomerServiceImplementation customerService;

    @Test
    void getCustomers() {
            Customer customer1 = new Customer();
            customer1.setFirstName("Jane");
            Customer customer2 = new Customer();
            customer2.setFirstName("Anna");

            List<Customer> mockCustomers = Arrays.asList(customer1, customer2);

            when(mockCustomerRepository.findAll()).thenReturn(mockCustomers);

            List<Customer> result = customerService.getCustomers();

            assertEquals(2, result.size());
            assertEquals("Jane", result.get(0).getFirstName());
            verify(mockCustomerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById() {
        Long id = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setFirstName("Jane");

        when(mockCustomerRepository.findById(id)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.getCustomerById(id);

        assertNotNull(result);
        assertEquals(id, result.get().getCustomerId());
        assertEquals("Jane", result.get().getFirstName());

        verify(mockCustomerRepository).findById(id);

    }

    @Test
    void createCustomer() {
        Long id = 1L;

        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setFirstName("Alice");
        customer.setLastName("Johnson");
        customer.setEmail("alice.johnson@example.com");
        customer.setPasswordHash("e99a18c428cb38d5f260853678922e03");
        customer.setPhoneNumber("+1-202-555-0147");
        customer.setAddress("456 Oak Avenue");
        customer.setCity("Denver");
        customer.setState("CO");
        customer.setZipCode("80203");
        customer.setCountry("USA");
        customer.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomer(customer);

        when(mockCustomerRepository.existsById(id)).thenReturn(false);
        when(mockCustomerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customerRequest);

        assertNotNull(createdCustomer);
        assertEquals("Alice", createdCustomer.getFirstName());

        verify(mockCustomerRepository).existsById(id);
        verify(mockCustomerRepository).save(any(Customer.class));
    }


    @Test
    void updateCustomer() {
        Long id = 1L;

        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setFirstName("Alice");
        customer.setLastName("Smith");
        customer.setEmail("alice@example.com");
        // ... set other fields

        CustomerRequest request = new CustomerRequest();
        request.setCustomer(customer);

        when(mockCustomerRepository.existsById(id)).thenReturn(true);
        when(mockCustomerRepository.save(any(Customer.class))).thenReturn(customer);

        Optional<Customer> result = customerService.updateCustomer(request);

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getFirstName());
        verify(mockCustomerRepository).existsById(id);
        verify(mockCustomerRepository).save(customer);
    }

    @Test
    void deleteCustomer() {
        Long id = 1L;

        when(mockCustomerRepository.existsById(id)).thenReturn(true);

        customerService.deleteCustomer(id);

        verify(mockCustomerRepository).existsById(id);
        verify(mockCustomerRepository).deleteById(id);
    }

    @Test
    void getEmail() {
        String email = "test@example.com";

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName("Test");

        when(mockCustomerRepository.getCustomerByEmail(email)).thenReturn(Optional.of(customer));

        Customer result = customerService.getEmail(email);

        assertNotNull(result);
        assertEquals("Test", result.getFirstName());
        verify(mockCustomerRepository).getCustomerByEmail(email);
    }
}