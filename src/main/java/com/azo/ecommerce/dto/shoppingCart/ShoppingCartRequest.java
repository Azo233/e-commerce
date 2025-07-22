package com.azo.ecommerce.dto.shoppingCart;

import com.azo.ecommerce.model.Customer;
import com.azo.ecommerce.model.ShoppingCart;

public class ShoppingCartRequest {
    private ShoppingCart shoppingCart;

    private Customer customer;

    public ShoppingCartRequest() {
    }

    public ShoppingCartRequest(ShoppingCart shoppingCart, Customer customer) {
        this.shoppingCart = shoppingCart;
        this.customer = customer;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
