package com.azo.ecommerce.service;

import com.azo.ecommerce.dto.cartItem.CartItemRequest;
import com.azo.ecommerce.model.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    List<CartItem> getCartItems();

    Optional<CartItem> getCartItemById(Long cartItemId);

    CartItem createCartItem(CartItemRequest request);

    Optional<CartItem> updateCartItem(CartItemRequest request);

    void deleteCartItem(Long cartItemId);
}
