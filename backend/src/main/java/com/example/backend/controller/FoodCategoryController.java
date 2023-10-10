package com.example.backend.controller;

import com.example.backend.entity.FoodCategory;
import com.example.backend.service.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/food-categories")
public class FoodCategoryController {
    @Autowired
    private FoodCategoryService service;

    @GetMapping
    public ResponseEntity<List<FoodCategory>> getAllFoodCategories() {
        List<FoodCategory> foodCategories = service.getAllFoodCategories();
        return new ResponseEntity<>(foodCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodCategory> getFoodCategoryById(@PathVariable String id) {
        Optional<FoodCategory> foodCategory = service.getFoodCategoryById(id);
        return foodCategory.map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<FoodCategory> createFoodCategory(@RequestBody FoodCategory category) {
        FoodCategory createdCategory = service.createFoodCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFoodCategory(@PathVariable String id, @RequestBody FoodCategory category) {
        service.updateFoodCategory(id, category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodCategory(@PathVariable String id) {
        service.deleteFoodCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

