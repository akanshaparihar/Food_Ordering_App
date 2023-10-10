package com.example.backend.service;

import com.example.backend.entity.FoodItem;
import com.example.backend.entity.Order;
import com.example.backend.entity.User;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.model.OrderItem;
import com.example.backend.model.OrderItemRequest;
import com.example.backend.model.OrderRequest;
import com.example.backend.repository.FoodItemRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(orderRequest.getTotalPrice());

        String userEmail = orderRequest.getUserEmail();
        User user = userRepository.findByEmail(userEmail);

        order.setUserEmail(orderRequest.getUserEmail());


        List<OrderItemRequest> itemRequests = orderRequest.getOrderItemsList();
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : itemRequests) {
            OrderItem orderItem = new OrderItem();
            orderItem.setName(itemRequest.getName());
            orderItem.setQuantity(itemRequest.getQuantity());

            FoodItem foodItem = foodItemRepository.findByName(itemRequest.getName());
            if(foodItem != null){
                orderItem.setPrice(foodItem.getPrice());
            }

          /*  String uniqueItemId = UUID.randomUUID().toString();
            orderItem.setId(uniqueItemId);*/
            orderItem.setId(foodItem.getId());

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

    public void updateOrder(String id, Order order) {
        if (orderRepository.existsById(id)) {
            order.setId(id);
            orderRepository.save(order);
        }
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getOrdersByUserEmail(String userEmail) {
        return orderRepository.findByUserEmail(userEmail);
    }

}
