package com.azo.ecommerce.controller;

import com.azo.ecommerce.dto.Order.OrderRequest;
import com.azo.ecommerce.model.Order;
import com.azo.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.base-url}/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create a new order
    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order createdOrder = orderService.createOrder(orderRequest)
                .orElseThrow(() -> new IllegalArgumentException("Unable to create order"));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    // Update an existing order
    @PutMapping("/updateOrder")
    public ResponseEntity<Order> updateOrder(@RequestBody OrderRequest orderRequest) {
        Order updatedOrder = orderService.updateOrder(orderRequest)
                .orElseThrow(() -> new IllegalArgumentException("Unable to update order"));
        return ResponseEntity.ok(updatedOrder);
    }

    // Get all orders
    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    // Get an order by ID
    @GetMapping("/getOrderById")
    public ResponseEntity<Order> getOrderById(@RequestParam Long orderId) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        return ResponseEntity.ok(order);
    }

    // Delete an order by ID
    @DeleteMapping("/deleteOrder")
    public ResponseEntity<Void> deleteOrder(@RequestParam Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
