package com.example.backend.service;

import com.example.backend.entity.Order;
import com.example.backend.model.OrderRequest;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public List<Order> getAllOrders();

    public Optional<Order> getOrderById(String id);

    public Order createOrder(OrderRequest order);

    public void updateOrder(String id, Order order);

    public void deleteOrder(String id);

    public List<Order> getOrdersByUserEmail(String userEmail);
}
