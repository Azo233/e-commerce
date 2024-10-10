package com.azo.ecommerce.dto.ShoppingCartRequest;

import com.azo.ecommerce.model.ShoppingCart;

public class ShoppingCartRequest {
    private ShoppingCart shoppingCart;

    public ShoppingCartRequest() {
    }

    public ShoppingCartRequest(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
