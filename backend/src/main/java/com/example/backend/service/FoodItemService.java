package com.example.backend.service;

import com.example.backend.entity.FoodItem;

import java.util.List;



public interface FoodItemService {
    List<FoodItem> getAllFoodItems();

    FoodItem getFoodItemById(String id);

    List<FoodItem> getAllFoodItemsByCategory(String categoryName);

    FoodItem createFoodItem(FoodItem item);

    void updateFoodItem(String id, FoodItem item);

    void deleteFoodItem(String id);

    FoodItem addFoodItemToCategory(String categoryName, FoodItem foodItem);
}
