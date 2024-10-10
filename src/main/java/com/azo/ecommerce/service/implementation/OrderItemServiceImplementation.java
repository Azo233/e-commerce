package com.azo.ecommerce.service.implementation;

import com.azo.ecommerce.dto.OrderItem.OrderItemRequest;
import com.azo.ecommerce.model.Category;
import com.azo.ecommerce.model.OrderItem;
import com.azo.ecommerce.model.Product;
import com.azo.ecommerce.repository.OrderItemRepository;
import com.azo.ecommerce.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImplementation implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItem> getOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public Optional<OrderItem> getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId);
    }

    @Override
    public OrderItem createOrderItem(OrderItemRequest request) {
        OrderItem orderItem = new OrderItem();

        // Create and set the Category object
        Category category = new Category();
        category.setName(request.getCategory().getName());
        category.setCategoryId(request.getCategory().getCategoryId());
        category.setDescription(request.getCategory().getDescription());
        category.setCreatedAt(request.getOrderItem().getCreatedAt());

        // Create and set the Product object
        Product product = new Product();
        product.setProductId(request.getProduct().getProductId());
        product.setCreatedAt(request.getProduct().getCreatedAt());
        product.setPrice(request.getProduct().getPrice());
        product.setCategory(category);
        product.setName(request.getProduct().getName());
        product.setDescription(request.getProduct().getDescription());
        product.setStockQuantity(request.getProduct().getStockQuantity());
        product.setUpdatedAt(request.getProduct().getUpdatedAt());

        // Set the Product in the OrderItem
        orderItem.setProduct(product);
        orderItem.setQuantity(request.getOrderItem().getQuantity());
        orderItem.setOrder(request.getOrder());
        orderItem.setCreatedAt(request.getOrderItem().getCreatedAt());
        orderItem.setOrderItemId(request.getOrderItem().getOrderItemId());
        orderItem.setPriceAtPurchase(request.getOrderItem().getPriceAtPurchase());

        return orderItemRepository.save(orderItem);
    }

    @Override
    public Optional<OrderItem> updateOrderItem(OrderItemRequest request) {
        if (!orderItemRepository.existsById(request.getOrderItem().getOrderItemId())) {
            throw new IllegalArgumentException("OrderItem does not exist with ID: " + request.getOrderItem().getOrderItemId());
        }
        OrderItem existingOrderItem = orderItemRepository.findById(request.getOrderItem().getOrderItemId())
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found with ID: " + request.getOrderItem().getOrderItemId()));

        existingOrderItem.setQuantity(request.getOrderItem().getQuantity());
        existingOrderItem.setPriceAtPurchase(request.getOrderItem().getPriceAtPurchase());

        Product product = existingOrderItem.getProduct();
        if (product != null) {
            product.setName(request.getProduct().getName());
            product.setDescription(request.getProduct().getDescription());
            product.setPrice(request.getProduct().getPrice());
            product.setStockQuantity(request.getProduct().getStockQuantity());
            product.setUpdatedAt(request.getProduct().getUpdatedAt());
        }

        return Optional.of(orderItemRepository.save(existingOrderItem));
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new IllegalArgumentException("OrderItem does not exist with ID: " + orderItemId);
        }
        orderItemRepository.deleteById(orderItemId);
    }
}


