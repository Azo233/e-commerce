package com.azo.ecommerce.dto.CartItemRequest;

import com.azo.ecommerce.model.CartItem;

public class CartItemRequest {
    private CartItem cartItem;

    public CartItemRequest() {
    }

    public CartItemRequest(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
