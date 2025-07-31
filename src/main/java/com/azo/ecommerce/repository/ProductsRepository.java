package com.azo.ecommerce.repository;

import com.azo.ecommerce.model.Category;
import com.azo.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    Product findByName (String productName);
}
