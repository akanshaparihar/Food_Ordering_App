package com.example.backend.repository;

import com.example.backend.entity.Order;
import com.example.backend.model.OrderRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserEmail(String userEmail);
}
