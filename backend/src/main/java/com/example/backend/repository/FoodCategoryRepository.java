package com.example.backend.repository;

import com.example.backend.entity.FoodCategory;
import com.example.backend.entity.FoodItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FoodCategoryRepository extends MongoRepository<FoodCategory, String> {
    FoodCategory findByName(String categoryName);
}
