package com.azo.ecommerce.controller;

import com.azo.ecommerce.dto.ShoppingCart.ShoppingCartRequest;
import com.azo.ecommerce.model.ShoppingCart;
import com.azo.ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base-url}/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/getAllShoppingCarts")
    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartService.getShoppingCart();
    }

    @GetMapping("/getShoppingCartById")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
        Optional<ShoppingCart> shoppingCart = shoppingCartService.getShoppingCartById(id);
        return shoppingCart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/createShoppingCart")
    public ResponseEntity<ShoppingCart> createShoppingCart(@RequestBody ShoppingCartRequest request) {
        ShoppingCart createdCart = shoppingCartService.createShoppingCart(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);
    }

    @PutMapping("/updateShoppingCart")
    public ResponseEntity<ShoppingCart> updateShoppingCart(@RequestBody ShoppingCartRequest request) {
        Optional<ShoppingCart> updatedCart = shoppingCartService.updateShoppingCart(request);
        return updatedCart.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteShoppingCart")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id) {
        shoppingCartService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

