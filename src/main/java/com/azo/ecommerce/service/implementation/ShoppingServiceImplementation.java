package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.ShoppingCart.ShoppingCartRequest;
import com.azo.ecommerce.model.ShoppingCart;
import com.azo.ecommerce.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingServiceImplementation implements ShoppingCartService {
    @Override
    public List<ShoppingCart> getShoppingCart() {
        return null;
    }

    @Override
    public Optional<ShoppingCart> getShoppingCartById(Long shoppingCartId) {
        return Optional.empty();
    }

    @Override
    public ShoppingCart createShoppingCart(ShoppingCartRequest request) {
        return null;
    }

    @Override
    public Optional<ShoppingCart> updateShoppingCart(ShoppingCartRequest request) {
        return Optional.empty();
    }

    @Override
    public void deleteProduct(Long shoppingCartId) {

    }
}
