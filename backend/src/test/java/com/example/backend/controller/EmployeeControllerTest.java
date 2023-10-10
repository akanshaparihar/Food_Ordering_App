package com.example.backend.controller;

import com.example.backend.entity.Employee;
import com.example.backend.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> mockEmployees = Arrays.asList(
                new Employee("1", "Mukesh", "Delivery Boy"),
                new Employee("2", "Suresh", "Delivery Boy")
        );
        when(employeeService.getAllEmployees()).thenReturn(mockEmployees);
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEmployees, response.getBody());
    }

    @Test
    public void testGetEmployeeById() {
        Employee mockEmployee1 = new Employee("1", "Mukesh", "Delivery Boy");
        Employee mockEmployee2 = new Employee("2","Suresh","Delivery Boy");
        when(employeeService.getEmployeeById("1")).thenReturn(Optional.of(mockEmployee1));
        when(employeeService.getEmployeeById("2")).thenReturn(Optional.of(mockEmployee2));
        ResponseEntity<Employee> response1 = employeeController.getEmployeeById("1");
        ResponseEntity<Employee>response2 = employeeController.getEmployeeById("2");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(mockEmployee1, response1.getBody());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(mockEmployee2, response2.getBody());
    }

    @Test
    public void testCreateEmployee() {

        Employee mockEmployee = new Employee("1", "Mukesh", "Delivery Boy");
        when(employeeService.createEmployee(mockEmployee)).thenReturn(mockEmployee);
        ResponseEntity<Employee> response = employeeController.createEmployee(mockEmployee);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockEmployee, response.getBody());
    }

    @Test
    public void testUpdateEmployee() {

        Employee mockEmployee = new Employee("1", "Raj", "Delivery Boy");
        doNothing().when(employeeService).updateEmployee("1", mockEmployee);
        ResponseEntity<Void> response = employeeController.updateEmployee("1", mockEmployee);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteEmployee() {

        doNothing().when(employeeService).deleteEmployee("1");
        ResponseEntity<Void> response = employeeController.deleteEmployee("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
