package com.example.backend.entity;

import com.example.backend.model.OrderItem;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Data
@Entity
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String id;
    private LocalDateTime orderDate;
    private double totalPrice;
    private String userEmail;
    private List<OrderItem> orderItems;

}
