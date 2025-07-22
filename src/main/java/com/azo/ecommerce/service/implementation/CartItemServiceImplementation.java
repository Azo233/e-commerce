package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.cartItem.CartItemRequest;
import com.azo.ecommerce.model.CartItem;
import com.azo.ecommerce.model.Product;
import com.azo.ecommerce.model.ShoppingCart;
import com.azo.ecommerce.repository.CartItemRepository;
import com.azo.ecommerce.repository.ProductsRepository;
import com.azo.ecommerce.repository.ShoppingCartRepository;
import com.azo.ecommerce.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImplementation implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductsRepository productRepository;

    private final ShoppingCartRepository  shoppingCartRepository;

    public CartItemServiceImplementation(CartItemRepository cartItemRepository, ProductsRepository productRepository, ShoppingCartRepository shoppingCartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public Optional<CartItem> getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }

    public CartItem createCartItem(CartItemRequest request) {
        // Check if the product exists
        if (!productRepository.existsById(request.getProductId())) {
            throw new IllegalArgumentException("Product does not exist with ID: " + request.getProductId());
        }

        // Check if the cart exists
        if (!shoppingCartRepository.existsById(request.getCartId())) {
            throw new IllegalArgumentException("Shopping Cart does not exist with ID: " + request.getCartId());
        }

        // Create a new CartItem instance
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(request.getQuantity());

        // Set the product and cart using their IDs
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + request.getProductId()));
        ShoppingCart cart = shoppingCartRepository.findById(request.getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Shopping Cart not found with ID: " + request.getCartId()));

        cartItem.setProduct(product);
        cartItem.setCart(cart);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Optional<CartItem> updateCartItem(CartItemRequest request) {
        // Check if the cart item exists
        if (!cartItemRepository.existsById(request.getCartItemId())) {
            throw new IllegalArgumentException("CartItem does not exist with ID: " + request.getCartItemId());
        }

        // Check if the product exists
        if (!productRepository.existsById(request.getProductId())) {
            throw new IllegalArgumentException("Product does not exist with ID: " + request.getProductId());
        }

        // Retrieve the existing CartItem
        CartItem existingCartItem = cartItemRepository.findById(request.getCartItemId())
                .orElseThrow(() -> new IllegalArgumentException("CartItem not found with ID: " + request.getCartItemId()));

        // Update the quantity
        existingCartItem.setQuantity(request.getQuantity());

        // Update the product and cart as needed
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + request.getProductId()));
        ShoppingCart cart = shoppingCartRepository.findById(request.getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Shopping Cart not found with ID: " + request.getCartId()));

        existingCartItem.setProduct(product);
        existingCartItem.setCart(cart);

        return Optional.of(cartItemRepository.save(existingCartItem));
    }
    @Override
    public void deleteCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new IllegalArgumentException("CartItem does not exist with ID: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
    }
}
