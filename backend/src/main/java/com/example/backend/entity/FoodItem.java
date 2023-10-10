package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Entity
@Document
@AllArgsConstructor
@NoArgsConstructor
public class FoodItem {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private double price;
    private String description;
    private String categoryName;
    private String imageUrl;
}
