package com.azo.ecommerce.service;

import com.azo.ecommerce.dto.ShoppingCart.ShoppingCartRequest;
import com.azo.ecommerce.model.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {
    List<ShoppingCart> getShoppingCart();

    Optional<ShoppingCart> getShoppingCartById(Long shoppingCartId);

    ShoppingCart createShoppingCart(ShoppingCartRequest request);

    Optional<ShoppingCart> updateShoppingCart(ShoppingCartRequest request);

    void deleteProduct(Long shoppingCartId);
}
