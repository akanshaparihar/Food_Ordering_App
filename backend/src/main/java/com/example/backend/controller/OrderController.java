package com.example.backend.controller;

import com.example.backend.entity.Order;
import com.example.backend.model.OrderRequest;
import com.example.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ord -> new ResponseEntity<>(ord, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order createdOrder = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userEmail}")
    public List<Order> getOrdersByUserEmail(@PathVariable String userEmail) {
        return orderService.getOrdersByUserEmail(userEmail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable String id, @RequestBody Order order) {
        orderService.updateOrder(id, order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
