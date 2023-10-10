package com.example.backend.service;

import com.example.backend.entity.FoodCategory;
import com.example.backend.repository.FoodCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodCategoryServiceImpl implements FoodCategoryService{
    @Autowired
    private FoodCategoryRepository repository;

    public List<FoodCategory> getAllFoodCategories() {
        return repository.findAll();
    }

    public Optional<FoodCategory> getFoodCategoryById(String id) {
        return repository.findById(id);
    }

    public FoodCategory createFoodCategory(FoodCategory category) {
        return repository.save(category);
    }

    public void updateFoodCategory(String id, FoodCategory category) {
        if (repository.existsById(id)) {
            category.setId(id);
            repository.save(category);
        }
    }

    public void deleteFoodCategory(String id) {
        repository.deleteById(id);
    }
}
