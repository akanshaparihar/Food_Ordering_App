package com.example.backend.service;


import com.example.backend.entity.User;
import com.example.backend.exception.UserAlreadyExistsException;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.model.LoginForm;
import com.example.backend.model.RegisterRequest;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        assertDoesNotThrow(() -> userService.registerUser(request));

    }

    @Test
    void testRegisterUserUserAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");


        when(userRepository.findByEmail("test@example.com")).thenReturn(new User());

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(request));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginUser() {
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("test@example.com");
        loginForm.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt(12)));
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userService.loginUser(loginForm);

        assertNotNull(result);
    }

    @Test
    void testLoginUserUserNotFound() {
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("test@example.com");
        loginForm.setPassword("password");

        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.loginUser(loginForm));
    }

    @Test
    void testLoginUserWrongPassword() {
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("test@example.com");
        loginForm.setPassword("wrongpassword");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt(12)));
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        assertThrows(UserNotFoundException.class, () -> userService.loginUser(loginForm));
    }

}

