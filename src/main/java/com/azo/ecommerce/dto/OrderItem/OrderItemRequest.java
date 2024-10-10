package com.azo.ecommerce.dto.OrderItem;

import com.azo.ecommerce.model.Category;
import com.azo.ecommerce.model.Order;
import com.azo.ecommerce.model.OrderItem;
import com.azo.ecommerce.model.Product;

public class OrderItemRequest {
    private OrderItem orderItem;
    private Product product;
    private Order order;

    private Category category;

    public OrderItemRequest() {
    }

    public OrderItemRequest(OrderItem orderItem, Product product, Order order, Category category) {
        this.orderItem = orderItem;
        this.product = product;
        this.order = order;
        this.category = category;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
