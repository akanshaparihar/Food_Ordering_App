package com.example.backend.service;

import com.example.backend.entity.FoodCategory;
import com.example.backend.entity.FoodItem;
import com.example.backend.exception.CategoryNotFoundException;
import com.example.backend.repository.FoodCategoryRepository;
import com.example.backend.repository.FoodItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoodItemServiceImplTest {

    @InjectMocks
    private FoodItemServiceImpl foodItemService;

    @Mock
    private FoodItemRepository foodItemRepository;

    @Mock
    private FoodCategoryRepository foodCategoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFoodItems() {
        List<FoodItem> foodItems = new ArrayList<>();
        when(foodItemRepository.findAll()).thenReturn(foodItems);

        List<FoodItem> result = foodItemService.getAllFoodItems();

        assertEquals(foodItems, result);
    }

    @Test
    public void testGetFoodItemById() {
        String itemId = "1";
        FoodItem foodItem = new FoodItem();
        foodItem.setId(itemId);

        when(foodItemRepository.findById(itemId)).thenReturn(Optional.of(foodItem));

        FoodItem result = foodItemService.getFoodItemById(itemId);

        assertEquals(itemId, result.getId());
    }

    @Test
    public void testGetAllFoodItemsByCategory() {
        String categoryName = "Test Category";
        List<FoodItem> foodItems = new ArrayList<>();

        when(foodItemRepository.findByCategoryName(categoryName)).thenReturn(foodItems);

        List<FoodItem> result = foodItemService.getAllFoodItemsByCategory(categoryName);

        assertEquals(foodItems, result);
    }

    @Test
    public void testCreateFoodItem() {
        FoodItem foodItem = new FoodItem();
        when(foodItemRepository.save(foodItem)).thenReturn(foodItem);

        FoodItem result = foodItemService.createFoodItem(foodItem);

        assertEquals(foodItem, result);
    }

    @Test
    public void testUpdateFoodItem() {
        String itemId = "1";
        FoodItem foodItem = new FoodItem();
        foodItem.setId(itemId);

        when(foodItemRepository.save(foodItem)).thenReturn(foodItem);

        assertDoesNotThrow(() -> foodItemService.updateFoodItem(itemId, foodItem));
    }

    @Test
    public void testDeleteFoodItem() {
        String itemId = "1";
        assertDoesNotThrow(() -> foodItemService.deleteFoodItem(itemId));
    }

    @Test
    public void testAddFoodItemToCategory() {
        String categoryName = "Test Category";
        FoodCategory category = new FoodCategory();
        category.setName(categoryName);

        FoodItem foodItem = new FoodItem();

        when(foodCategoryRepository.findByName(categoryName)).thenReturn(category);
        when(foodItemRepository.save(foodItem)).thenReturn(foodItem);

        FoodItem result = foodItemService.addFoodItemToCategory(categoryName, foodItem);

        assertEquals(foodItem, result);
    }
}
