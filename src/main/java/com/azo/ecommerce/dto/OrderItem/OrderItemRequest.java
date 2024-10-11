package com.azo.ecommerce.dto.OrderItem;

import com.azo.ecommerce.model.Category;
import com.azo.ecommerce.model.Order;
import com.azo.ecommerce.model.OrderItem;
import com.azo.ecommerce.model.Product;

import java.math.BigDecimal;

public class OrderItemRequest {

        private Long orderItemId;
        private Long orderId;       // The ID of the associated order
        private Long productId;     // The ID of the product
        private int quantity;       // The quantity of the product
        private BigDecimal priceAtPurchase;  // Price at the time of purchase

        public OrderItemRequest() {
        }

    public OrderItemRequest(Long orderItemId, Long orderId, Long productId, int quantity, BigDecimal priceAtPurchase) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
}
