package com.example.backend.controller;

import com.example.backend.entity.FoodCategory;
import com.example.backend.service.FoodCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoodCategoryControllerTest {

    @InjectMocks
    private FoodCategoryController foodCategoryController;

    @Mock
    private FoodCategoryService foodCategoryService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFoodCategories() {
        List<FoodCategory> mockFoodCategories = new ArrayList<>();
        mockFoodCategories.add(new FoodCategory("1","Category 1", "image1"));
        mockFoodCategories.add(new FoodCategory("2", "Category 2", "image2"));
        when(foodCategoryService.getAllFoodCategories()).thenReturn(mockFoodCategories);
        ResponseEntity<List<FoodCategory>> response = foodCategoryController.getAllFoodCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Category 1", response.getBody().get(0).getName());
        assertEquals("Category 2", response.getBody().get(1).getName());
    }

    @Test
    public void testGetFoodCategoryById() {
        String categoryId = "1";
        FoodCategory mockCategory = new FoodCategory(categoryId, "Category 1", "image1");
        when(foodCategoryService.getFoodCategoryById(categoryId)).thenReturn(Optional.of(mockCategory));
        ResponseEntity<FoodCategory> response = foodCategoryController.getFoodCategoryById(categoryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category 1", response.getBody().getName());
    }


    @Test
    public void testCreateFoodCategory() {
        FoodCategory newCategory = new FoodCategory(null, "New Category", "image");
        FoodCategory savedCategory = new FoodCategory("1", "New Category", "image");
        when(foodCategoryService.createFoodCategory(newCategory)).thenReturn(savedCategory);
        ResponseEntity<FoodCategory> response = foodCategoryController.createFoodCategory(newCategory);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("New Category", response.getBody().getName());
    }

    @Test
    public void testUpdateFoodCategory() {
        String categoryId = "1";
        FoodCategory updatedCategory = new FoodCategory(categoryId, "Updated Category", "updated Image");
        ResponseEntity<Void> response = foodCategoryController.updateFoodCategory(categoryId, updatedCategory);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteFoodCategory() {
        String categoryId = "1";
        ResponseEntity<Void> response = foodCategoryController.deleteFoodCategory(categoryId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
