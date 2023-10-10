package com.example.backend.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String categoryDoesNotExists) {
        super(categoryDoesNotExists);
    }
}
