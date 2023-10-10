package com.example.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "First Name is required")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "First Name must contain only alphabets")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last Name must contain only alphabets")
    private String lastName;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone Number must be a 10-digit number")
    private String phoneNo;
}
