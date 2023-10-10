package com.example.backend.model;

import lombok.Data;

@Data
public class OrderItemRequest {
    private String name;
    private int quantity;
}
