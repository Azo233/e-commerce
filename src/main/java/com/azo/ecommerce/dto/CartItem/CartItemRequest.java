package com.azo.ecommerce.dto.CartItem;

import com.azo.ecommerce.model.CartItem;
import com.azo.ecommerce.model.Product;

public class CartItemRequest {
    private CartItem cartItem;
    private Product product;

    public CartItemRequest() {
    }

    public CartItemRequest(CartItem cartItem, Product product) {
        this.cartItem = cartItem;
        this.product = product;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
