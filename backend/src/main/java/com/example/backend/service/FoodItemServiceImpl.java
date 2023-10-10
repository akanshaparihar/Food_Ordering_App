package com.example.backend.service;

import com.example.backend.entity.FoodCategory;
import com.example.backend.entity.FoodItem;
import com.example.backend.exception.CategoryNotFoundException;
import com.example.backend.repository.FoodCategoryRepository;
import com.example.backend.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemServiceImpl implements FoodItemService {
    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    @Override
    public FoodItem getFoodItemById(String id) {
        FoodItem foodItem = foodItemRepository.findById(id).orElse(null);
        if (foodItem == null) {
            throw new RuntimeException("FoodItemId : " + id + " does not exist");
        }
        return foodItem;
    }

    @Override
    public List<FoodItem> getAllFoodItemsByCategory(String categoryName) {
        return foodItemRepository.findByCategoryName(categoryName);
    }

    @Override
    public FoodItem createFoodItem(FoodItem item) {
        return foodItemRepository.save(item);
    }

    @Override
    public void updateFoodItem(String id, FoodItem item) {
        if (foodItemRepository.existsById(id)) {
            item.setId(id);
            foodItemRepository.save(item);
        }
    }

    @Override
    public void deleteFoodItem(String id) {
        foodItemRepository.deleteById(id);
    }

    @Override
    public FoodItem addFoodItemToCategory(String categoryName, FoodItem foodItem) {
        FoodCategory category = foodCategoryRepository.findByName(categoryName);

        if (category == null) {
            throw new CategoryNotFoundException("Category does not exist");
        }

        return foodItemRepository.save(foodItem);
    }
}
