package com.example.backend.controller;

import com.example.backend.entity.FoodItem;
import com.example.backend.service.FoodItemService;
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

public class FoodItemControllerTest {

    @InjectMocks
    private FoodItemController foodItemController;

    @Mock
    private FoodItemService foodItemService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFoodItems() {
        List<FoodItem> mockFoodItems = new ArrayList<>();
        mockFoodItems.add(new FoodItem("1", "Item 1", 10.0, "description 1","Category 1","image1"));
        mockFoodItems.add(new FoodItem("2", "Item 2", 15.0, "description 2","Category 2", "image2"));

        when(foodItemService.getAllFoodItems()).thenReturn(mockFoodItems);
        ResponseEntity<List<FoodItem>> response = foodItemController.getAllFoodItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Item 1", response.getBody().get(0).getName());
        assertEquals("Item 2", response.getBody().get(1).getName());
    }

    @Test
    public void testGetFoodItemById() {
        FoodItem mockFoodItem = new FoodItem("1", "Item 1", 10.0, "description 1","Category 1", "image1");
        when(foodItemService.getFoodItemById("1")).thenReturn(mockFoodItem);
        ResponseEntity<FoodItem> response1 = foodItemController.getFoodItemById("1");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals("Item 1", response1.getBody().getName());
    }

    @Test
    public void testGetAllFoodItemsByCategory() {
        List<FoodItem> mockFoodItems = new ArrayList<>();
        mockFoodItems.add(new FoodItem("1", "Item 1", 10.0, "description 1","Category 1", "imageA"));
        mockFoodItems.add(new FoodItem("2", "Item 2", 15.0, "description 2","Category 1", "imageB"));
        when(foodItemService.getAllFoodItemsByCategory("Category 1")).thenReturn(mockFoodItems);
        ResponseEntity<List<FoodItem>> response1 = foodItemController.getAllFoodItemsByCategory("Category 1");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(2, response1.getBody().size());
        assertEquals("Item 1", response1.getBody().get(0).getName());
        assertEquals("Item 2", response1.getBody().get(1).getName());
    }

    @Test
    public void testCreateFoodItem() {
        FoodItem newItem = new FoodItem(null, "New Item", 12.0,"description", "Category", "imageA");
        FoodItem savedItem = new FoodItem("1", "New Item", 12.0,"description", "Category", "imageA");
        when(foodItemService.createFoodItem(newItem)).thenReturn(savedItem);
        ResponseEntity<FoodItem> response = foodItemController.createFoodItem(newItem);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("New Item", response.getBody().getName());
    }

    @Test
    public void testAddFoodItemToCategory() {
        FoodItem newItem = new FoodItem(null, "New Item", 12.0, "description","Category", "image");
        FoodItem addedItem = new FoodItem("1", "New Item", 12.0, "description","Category", "image");
        when(foodItemService.addFoodItemToCategory("Category", newItem)).thenReturn(addedItem);
        ResponseEntity<FoodItem> response = foodItemController.addFoodItemToCategory("Category", newItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("New Item", response.getBody().getName());
    }

    @Test
    public void testUpdateFoodItem() {
        FoodItem updatedItem = new FoodItem("1", "Updated Item", 15.0, "description","Category", "Updated image");
        doNothing().when(foodItemService).updateFoodItem("1", updatedItem);
        ResponseEntity<Void> response = foodItemController.updateFoodItem("1", updatedItem);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteFoodItem() {
        doNothing().when(foodItemService).deleteFoodItem("1");
        ResponseEntity<Void> response = foodItemController.deleteFoodItem("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

