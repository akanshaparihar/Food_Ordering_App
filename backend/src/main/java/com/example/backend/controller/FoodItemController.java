package com.example.backend.controller;

import com.example.backend.entity.FoodItem;
import com.example.backend.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food-items")
public class FoodItemController {
    @Autowired
    private FoodItemService service;

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {
        List<FoodItem> foodItems = service.getAllFoodItems();
        return new ResponseEntity<>(foodItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable String id) {
        FoodItem foodItem = service.getFoodItemById(id);
        return new ResponseEntity<>(foodItem, HttpStatus.OK);
    }

    @GetMapping("/by-category/{categoryName}")
    public ResponseEntity<List<FoodItem>> getAllFoodItemsByCategory(@PathVariable String categoryName) {
        List<FoodItem> foodItems = service.getAllFoodItemsByCategory(categoryName);
        return new ResponseEntity<>(foodItems, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FoodItem> createFoodItem(@RequestBody FoodItem item) {
        FoodItem createdItem = service.createFoodItem(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PostMapping("/add-to-category/{categoryName}")
    public ResponseEntity<FoodItem> addFoodItemToCategory(
            @PathVariable String categoryName,
            @RequestBody FoodItem foodItem) {
        FoodItem addedFoodItem = service.addFoodItemToCategory(categoryName, foodItem);
        return new ResponseEntity<>(addedFoodItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFoodItem(@PathVariable String id, @RequestBody FoodItem item) {
        service.updateFoodItem(id, item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable String id) {
        service.deleteFoodItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
