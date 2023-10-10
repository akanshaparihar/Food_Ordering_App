package com.example.backend.entity;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Entity
@Document("users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
}
