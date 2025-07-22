package com.azo.ecommerce.dto.products;

import com.azo.ecommerce.model.Product;

public class ProductsRequest {
    private Product product;

    public ProductsRequest() {
    }

    public ProductsRequest(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
