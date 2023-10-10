package com.example.backend.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userNotFound) {
        super((userNotFound));
    }
}
