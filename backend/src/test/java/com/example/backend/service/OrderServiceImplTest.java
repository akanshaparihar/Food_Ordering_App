package com.example.backend.service;

import com.example.backend.entity.FoodItem;
import com.example.backend.entity.Order;
import com.example.backend.entity.User;
import com.example.backend.model.OrderItem;
import com.example.backend.model.OrderItemRequest;
import com.example.backend.model.OrderRequest;
import com.example.backend.repository.FoodItemRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FoodItemRepository foodItemRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder() {
        User user = new User();
        user.setFirstName("Raj");
        user.setLastName("Singh");
        user.setEmail("raj@gmail.com");
        user.setPassword("123456");
        user.setPhoneNumber("123456890");

        FoodItem foodItem = new FoodItem();
        foodItem.setId("i1");
        foodItem.setName("Item 1");
        foodItem.setPrice(10.0);
        foodItem.setDescription("description");
        foodItem.setCategoryName("category 1");
        foodItem.setImageUrl("image.jpg");

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setTotalPrice(100.0);
        orderRequest.setUserEmail("raj@gmail.com");
        List<OrderItemRequest> itemRequests = new ArrayList<>();
        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setName("Item 1");
        itemRequest.setQuantity(2);
        itemRequests.add(itemRequest);
        orderRequest.setOrderItemsList(itemRequests);

        when(userRepository.findByEmail("raj@gmail.com")).thenReturn(user);

        when(foodItemRepository.findByName("Item 1")).thenReturn(foodItem);

        Order savedOrder = new Order();
        savedOrder.setId("1");
        savedOrder.setTotalPrice(100.0);
        savedOrder.setUserEmail("raj@gmail.com");

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Order createdOrder = orderService.createOrder(orderRequest);

        assertEquals("raj@gmail.com", createdOrder.getUserEmail());
        assertEquals(100.0, createdOrder.getTotalPrice());
    }


    @Test
    public void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("1", LocalDateTime.now(), 50.0,"A@A" ,new ArrayList<>()));
        orders.add(new Order("2", LocalDateTime.now(), 75.0,"A@A" ,new ArrayList<>()));

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(orders, result);
    }

    @Test
    public void testGetOrderById() {
        String orderId = "1";
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(orderId);

        assertTrue(result.isPresent());
        assertEquals(orderId, result.get().getId());
    }



    @Test
    public void testUpdateOrder() {
        String orderId = "1";
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.save(order)).thenReturn(order);

        assertDoesNotThrow(() -> orderService.updateOrder(orderId, order));
    }

    @Test
    public void testDeleteOrder() {
        String orderId = "1";

        assertDoesNotThrow(() -> orderService.deleteOrder(orderId));
    }
}
