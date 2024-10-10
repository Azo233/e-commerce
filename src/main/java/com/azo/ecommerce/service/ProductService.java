package com.azo.ecommerce.service;

import com.azo.ecommerce.dto.Products.ProductsRequest;
import com.azo.ecommerce.dto.categoy.CategoryRequest;
import com.azo.ecommerce.model.Category;
import com.azo.ecommerce.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long productId);

    Product createProduct(ProductsRequest request);

    Optional<Product> updateProduct(ProductsRequest request);

    void deleteProduct(Long productId);

    Product getProductByName(String productName);
}
