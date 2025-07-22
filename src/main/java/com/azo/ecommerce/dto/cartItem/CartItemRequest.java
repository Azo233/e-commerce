package com.azo.ecommerce.dto.cartItem;

public class CartItemRequest {

    private Long cartItemId;
    private Long productId;  // Just the product ID
    private Long cartId;     // Just the cart ID
    private int quantity;
    public CartItemRequest() {
    }

    public CartItemRequest(Long cartItemId, Long productId, Long cartId, int quantity) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
