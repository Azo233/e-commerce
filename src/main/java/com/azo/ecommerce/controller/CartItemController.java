package com.azo.ecommerce.controller;

import com.azo.ecommerce.dto.CartItem.CartItemRequest;
import com.azo.ecommerce.model.CartItem;
import com.azo.ecommerce.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base-url}/cartItem")
public class CartItemController {

    @Autowired
    CartItemService cartItemService;

    @GetMapping("/getAllCartItems")
    public List<CartItem> getAllCartItems() {
        return cartItemService.getCartItems();
    }

    @GetMapping("/getCartItemById")
    public Optional<CartItem> getCartItemById(@RequestParam Long cartItemId) {
        return cartItemService.getCartItemById(cartItemId);
    }

    @PostMapping("/createCartItem")
    public CartItem createCartItem(@RequestBody CartItemRequest request) {
        return cartItemService.createCartItem(request);
    }

    @PutMapping("/updateCartItem")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItemRequest request) {
        Optional<CartItem> updatedCartItem = cartItemService.updateCartItem(request);
        return updatedCartItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteCartItem")
    public ResponseEntity<Void> deleteCartItem(@RequestParam Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
