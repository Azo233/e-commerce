package com.azo.ecommerce.controller;

import com.azo.ecommerce.dto.Products.ProductsRequest;
import com.azo.ecommerce.model.Product;
import com.azo.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base-url}/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getProductById")
    public Optional<Product> getProductById(@RequestParam Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/createProduct")
    public Product createProduct(@RequestBody ProductsRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductsRequest request) {
        Optional<Product> updatedProduct = productService.updateProduct(request);
        return updatedProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Void> deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getProductByName")
    public Optional<Product> getProductByName(@RequestParam String productName) {
        return Optional.ofNullable(productService.getProductByName(productName));
    }
}

