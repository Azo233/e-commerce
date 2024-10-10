package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.CartItem.CartItemRequest;
import com.azo.ecommerce.model.CartItem;
import com.azo.ecommerce.repository.CartItemRepository;
import com.azo.ecommerce.repository.ProductsRepository;
import com.azo.ecommerce.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImplementation implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductsRepository productRepository;

    public CartItemServiceImplementation(CartItemRepository cartItemRepository, ProductsRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public Optional<CartItem> getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }

    @Override
    public CartItem createCartItem(CartItemRequest request) {
        if (!productRepository.existsById(request.getProduct().getProductId())) {
            throw new IllegalArgumentException("Product does not exist with ID: " + request.getProduct().getProductId());
        }

        if (request.getCartItem().getCartItemId() != null && cartItemRepository.existsById(request.getCartItem().getCartItemId())) {
            throw new IllegalArgumentException("CartItem already exists with ID: " + request.getCartItem().getCartItemId());
        }

        request.getCartItem().setProduct(request.getProduct());

        return cartItemRepository.save(request.getCartItem());
    }

    @Override
    public Optional<CartItem> updateCartItem(CartItemRequest request) {
        if (!cartItemRepository.existsById(request.getCartItem().getCartItemId())) {
            throw new IllegalArgumentException("CartItem does not exist with ID: " + request.getCartItem().getCartItemId());
        }
        if (!productRepository.existsById(request.getProduct().getProductId())) {
            throw new IllegalArgumentException("Product does not exist with ID: " + request.getProduct().getProductId());
        }

        request.getCartItem().setProduct(request.getProduct());

        return Optional.of(cartItemRepository.save(request.getCartItem()));
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new IllegalArgumentException("CartItem does not exist with ID: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
    }
}
