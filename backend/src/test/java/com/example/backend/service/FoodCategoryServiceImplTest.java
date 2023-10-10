package com.example.backend.service;

import com.example.backend.entity.FoodCategory;
import com.example.backend.repository.FoodCategoryRepository;
import com.example.backend.service.FoodCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FoodCategoryServiceImplTest {

    @InjectMocks
    private FoodCategoryServiceImpl foodCategoryService;

    @Mock
    private FoodCategoryRepository foodCategoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFoodCategories() {
        List<FoodCategory> foodCategories = new ArrayList<>();
        foodCategories.add(new FoodCategory("1", "Category 1", "image1"));
        foodCategories.add(new FoodCategory("2", "Category 2", "image2"));
        when(foodCategoryRepository.findAll()).thenReturn(foodCategories);

        List<FoodCategory> result = foodCategoryService.getAllFoodCategories();
        assertEquals(2,result.size());
    }

    @Test
    public void testGetFoodCategoryById() {

        FoodCategory foodCategory = new FoodCategory("1", "Category 1", "image1");
        when(foodCategoryRepository.findById("1")).thenReturn(Optional.of(foodCategory));

        Optional<FoodCategory> result = foodCategoryService.getFoodCategoryById("1");

        assertEquals("Category 1", result.get().getName());
    }

    @Test
    public void testCreateFoodCategory() {
        FoodCategory newCategory = new FoodCategory("3", "Category 3", "image3");
        when(foodCategoryRepository.save(newCategory)).thenReturn(newCategory);

        FoodCategory result = foodCategoryService.createFoodCategory(newCategory);

        assertEquals("Category 3", result.getName());
    }

    @Test
    public void testUpdateFoodCategory() {
        FoodCategory foodCategoryToUpdate = new FoodCategory("1", "Category 1 Updated", "Updated Image");
        when(foodCategoryRepository.save(foodCategoryToUpdate)).thenReturn(foodCategoryToUpdate);

        foodCategoryService.updateFoodCategory("1", foodCategoryToUpdate);
    }

    @Test
    public void testDeleteFoodCategory() {
        String foodCategoryId = "1";
        foodCategoryService.deleteFoodCategory(foodCategoryId);
    }
}
