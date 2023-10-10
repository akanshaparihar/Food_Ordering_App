package com.example.backend.service;

import com.example.backend.entity.FoodCategory;

import java.util.List;
import java.util.Optional;

public interface FoodCategoryService {

     public List<FoodCategory> getAllFoodCategories();

     public Optional<FoodCategory> getFoodCategoryById(String id);

     public FoodCategory createFoodCategory(FoodCategory category);

     public void updateFoodCategory(String id, FoodCategory category);

     public void deleteFoodCategory(String id);
}
