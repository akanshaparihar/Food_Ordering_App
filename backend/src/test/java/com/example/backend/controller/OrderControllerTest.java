package com.example.backend.controller;

import com.example.backend.controller.OrderController;
import com.example.backend.entity.Order;
import com.example.backend.model.OrderRequest;
import com.example.backend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(new Order("1", LocalDateTime.now(), 50.0,"A@A" ,new ArrayList<>()));
        mockOrders.add(new Order("2", LocalDateTime.now(), 75.0,"A@A" ,new ArrayList<>()));
        when(orderService.getAllOrders()).thenReturn(mockOrders);

        ResponseEntity<List<Order>> responseEntity = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockOrders, responseEntity.getBody());
    }

    @Test
    public void testGetOrderById() {
        Order mockOrder = new Order("1", LocalDateTime.now(), 50.0, "A@A",new ArrayList<>());
        when(orderService.getOrderById("1")).thenReturn(Optional.of(mockOrder));

        ResponseEntity<Order> responseEntity = orderController.getOrderById("1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockOrder, responseEntity.getBody());
    }

    @Test
    public void testCreateOrder() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderDate(LocalDateTime.now());
        orderRequest.setTotalPrice(75.0);

        Order mockOrder = new Order("3", orderRequest.getOrderDate(), orderRequest.getTotalPrice(),"A@A", new ArrayList<>());
        when(orderService.createOrder(orderRequest)).thenReturn(mockOrder);

        ResponseEntity<Order> responseEntity = orderController.createOrder(orderRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockOrder, responseEntity.getBody());
    }

    @Test
    public void testGetOrdersByUserEmail() {

        String userEmail = "test@example.com";
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("1", LocalDateTime.now(), 50.0,"A@A" ,new ArrayList<>()));
        orders.add(new Order("2", LocalDateTime.now(), 75.0,"A@A" ,new ArrayList<>()));

        when(orderService.getOrdersByUserEmail(userEmail)).thenReturn(orders);

        List<Order> result = orderController.getOrdersByUserEmail(userEmail);

        assertEquals(orders, result);
    }



    @Test
    public void testDeleteOrder() {
        doNothing().when(orderService).deleteOrder("1");
        ResponseEntity<Void> response = orderController.deleteOrder("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
