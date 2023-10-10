package com.example.backend.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
public class OrderItem {
    @Id
    private String id;
    private String name;
    private int quantity;
    private double price;
}