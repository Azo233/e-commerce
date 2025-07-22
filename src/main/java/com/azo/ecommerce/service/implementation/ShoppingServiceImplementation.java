package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.shoppingCart.ShoppingCartRequest;
import com.azo.ecommerce.model.Customer;
import com.azo.ecommerce.model.ShoppingCart;
import com.azo.ecommerce.repository.CustomerRepository;
import com.azo.ecommerce.repository.ShoppingCartRepository;
import com.azo.ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingServiceImplementation implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CustomerRepository customerRepository;
    @Autowired
    public ShoppingServiceImplementation(ShoppingCartRepository shoppingCartRepository, CustomerRepository customerRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<ShoppingCart> getShoppingCart() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public Optional<ShoppingCart> getShoppingCartById(Long shoppingCartId) {
        return shoppingCartRepository.findById(shoppingCartId);
    }

    @Override
    public ShoppingCart createShoppingCart(ShoppingCartRequest request) {
        Customer customer = customerRepository.findById(request.getCustomer().getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customer);
        shoppingCart.setCreatedAt(request.getShoppingCart().getCreatedAt());
        shoppingCart.setStatus(request.getShoppingCart().getStatus());

        return shoppingCartRepository.save(shoppingCart);
    }


    @Override
    public Optional<ShoppingCart> updateShoppingCart(ShoppingCartRequest request) {
        if (!shoppingCartRepository.existsById(request.getShoppingCart().getCartId())) {
            throw new IllegalArgumentException("Shopping cart does not exist with ID: " + request.getShoppingCart().getCartId());
        }

        ShoppingCart existingCart = shoppingCartRepository.findById(request.getShoppingCart().getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found with ID: " + request.getShoppingCart().getCartId()));

        Long customerId = request.getCustomer().getCustomerId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));

        existingCart.setCustomer(customer);
        existingCart.setStatus(request.getShoppingCart().getStatus());
        existingCart.setCreatedAt(request.getShoppingCart().getCreatedAt());
        ShoppingCart updatedCart = shoppingCartRepository.save(existingCart);

        return Optional.of(updatedCart);
    }



    @Override
    public void deleteProduct(Long shoppingCartId) {
        if (!shoppingCartRepository.existsById(shoppingCartId)) {
            throw new IllegalArgumentException("Shopping cart does not exist with ID: " + shoppingCartId);
        }

        shoppingCartRepository.deleteById(shoppingCartId);
    }
}
