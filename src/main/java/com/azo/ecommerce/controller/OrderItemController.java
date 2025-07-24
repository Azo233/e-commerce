package com.azo.ecommerce.controller;

import com.azo.ecommerce.dto.orderItem.OrderItemRequest;
import com.azo.ecommerce.model.OrderItem;
import com.azo.ecommerce.service.OrderItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.base-url}/orderItem")
@Tag(name = "OrderItem")
public class OrderItemController {

    @Autowired
    private final OrderItemService orderItemService;
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping ("/getAllOrderItems")
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getOrderItems();
    }

    @GetMapping("/getOrderItemById")
    public ResponseEntity<OrderItem> getOrderItemById(@RequestParam Long orderItemId) {
        Optional<OrderItem> orderItem = orderItemService.getOrderItemById(orderItemId);
        return orderItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/createOrderItem")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItemRequest request) {
        OrderItem createdOrderItem = orderItemService.createOrderItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
    }
    @PutMapping("/updateOrderItem")
    public ResponseEntity<OrderItem> updateOrderItem(@RequestBody OrderItemRequest request) {
        Optional<OrderItem> updatedOrderItem = orderItemService.updateOrderItem(request);
        return updatedOrderItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/deleteOrderItem")
    public ResponseEntity<Void> deleteOrderItem(@RequestParam Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);
        return ResponseEntity.noContent().build();
    }
}

