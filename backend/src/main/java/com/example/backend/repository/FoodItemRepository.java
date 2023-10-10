package com.example.backend.repository;

import com.example.backend.entity.FoodItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FoodItemRepository extends MongoRepository<FoodItem, String> {
    FoodItem findByName(String name);
    List<FoodItem> findByCategoryName(String categoryName);
}
