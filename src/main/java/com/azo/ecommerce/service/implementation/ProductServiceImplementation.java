package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.Products.ProductsRequest;
import com.azo.ecommerce.model.Product;
import com.azo.ecommerce.repository.ProductsRepository;
import com.azo.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductsRepository productsRepository;

    public ProductServiceImplementation(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productsRepository.findById(productId);
    }

    @Override
    public Product createProduct(ProductsRequest request) {
        if (request.getProduct().getProductId() != null && productsRepository.existsById(request.getProduct().getProductId())) {
            throw new IllegalArgumentException("Product already exists with ID: " + request.getProduct().getProductId());
        }
        return productsRepository.save(request.getProduct());
    }

    @Override
    public Optional<Product> updateProduct(ProductsRequest request) {
        if (!productsRepository.existsById(request.getProduct().getProductId())) {
            throw new IllegalArgumentException("Product does not exist with ID: " + request.getProduct().getProductId());
        }
        return Optional.of(productsRepository.save(request.getProduct()));
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productsRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product does not exist with ID: " + productId);
        }
        productsRepository.deleteById(productId);
    }

    @Override
    public Product getProductByName(String productName) {
        return productsRepository.findByName(productName);
    }
}

