package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.model.LoginForm;
import com.example.backend.model.RegisterRequest;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testWelcomePage() {
        ResponseEntity<String> response = userController.welcomePage();
        assertEquals("HI", response.getBody());
    }

    @Test
    public void testSignUp() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@gmail.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstName("Raj");
        registerRequest.setLastName("Singh");
        registerRequest.setPhoneNo("1234567890");
        ResponseEntity<String> response = userController.signUp(registerRequest);
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    public void testLogin() {
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("test@gmail.com");
        loginForm.setPassword("password");
        User user = new User();

        when(userService.loginUser(loginForm)).thenReturn(user);
        ResponseEntity<User> response = userController.login(loginForm);
        assertEquals(user, response.getBody());
    }
}
