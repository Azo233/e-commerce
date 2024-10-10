package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.ShoppingCart.ShoppingCartRequest;
import com.azo.ecommerce.model.Customer;
import com.azo.ecommerce.model.ShoppingCart;
import com.azo.ecommerce.repository.ShoppingCartRepository;
import com.azo.ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingServiceImplementation implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingServiceImplementation(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<ShoppingCart> getShoppingCart() {
        // Retrieve all shopping carts from the repository
        return shoppingCartRepository.findAll();
    }

    @Override
    public Optional<ShoppingCart> getShoppingCartById(Long shoppingCartId) {
        // Retrieve a shopping cart by its ID
        return shoppingCartRepository.findById(shoppingCartId);
    }

    @Override
    public ShoppingCart createShoppingCart(ShoppingCartRequest request) {
        Customer customer = request.getCustomer();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customer);
        shoppingCart.setCreatedAt(request.getShoppingCart().getCreatedAt());
        shoppingCart.setCartId(request.getShoppingCart().getCartId());
        shoppingCart.setStatus(request.getShoppingCart().getStatus());

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> updateShoppingCart(ShoppingCartRequest request) {
        // Check if the shopping cart exists
        if (!shoppingCartRepository.existsById(request.getShoppingCart().getCartId())) {
            throw new IllegalArgumentException("Shopping cart does not exist with ID: " + request.getShoppingCart().getCartId());
        }

        // Retrieve the existing shopping cart
        ShoppingCart existingCart = shoppingCartRepository.findById(request.getShoppingCart().getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found with ID: " + request.getShoppingCart().getCartId()));

        // Update properties of the existing shopping cart
        existingCart.setCustomer(request.getCustomer());
        existingCart.setCreatedAt(request.getShoppingCart().getCreatedAt());
        existingCart.setStatus(request.getShoppingCart().getStatus());

        // Save the updated shopping cart
        return Optional.of(shoppingCartRepository.save(existingCart));
    }


    @Override
    public void deleteProduct(Long shoppingCartId) {
        // Check if the shopping cart exists
        if (!shoppingCartRepository.existsById(shoppingCartId)) {
            throw new IllegalArgumentException("Shopping cart does not exist with ID: " + shoppingCartId);
        }

        // Delete the shopping cart
        shoppingCartRepository.deleteById(shoppingCartId);
    }
}
