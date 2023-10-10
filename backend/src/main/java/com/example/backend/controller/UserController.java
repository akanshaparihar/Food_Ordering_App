package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.model.LoginForm;
import com.example.backend.model.RegisterRequest;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<String> welcomePage(){
        return ResponseEntity.ok("HI");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginForm loginForm) {
        User user = userService.loginUser(loginForm);
        return ResponseEntity.ok(user);
    }
}
