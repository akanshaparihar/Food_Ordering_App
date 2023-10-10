package com.example.backend.service;

import com.example.backend.entity.Employee;
import com.example.backend.repository.EmployeeRepository;
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

public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(new Employee("1", "Mukesh","Delivery"));
        mockEmployees.add(new Employee("2", "Raj","Delivery"));

        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(2, employees.size());
        assertEquals("Mukesh", employees.get(0).getName());
        assertEquals("Raj", employees.get(1).getName());
    }

    @Test
    public void testGetEmployeeById() {
        String employeeId = "1";
        Employee mockEmployee = new Employee(employeeId, "Mukesh","Delivery");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockEmployee));

        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);

        assertTrue(employee.isPresent());
        assertEquals("Mukesh", employee.get().getName());
    }

    @Test
    public void testCreateEmployee() {
        Employee newEmployee = new Employee(null, "New Employee","Delivery");
        Employee savedEmployee = new Employee("1", "New Employee","Delivery");

        when(employeeRepository.save(newEmployee)).thenReturn(savedEmployee);

        Employee createdEmployee = employeeService.createEmployee(newEmployee);

        assertNotNull(createdEmployee.getId());
        assertEquals("New Employee", createdEmployee.getName());
    }

    @Test
    public void testUpdateEmployee() {
        String employeeId = "1";
        Employee updatedEmployee = new Employee(employeeId, "Updated Employee","Delivery");

        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        employeeService.updateEmployee(employeeId, updatedEmployee);

    }

    @Test
    public void testDeleteEmployee() {
        String employeeId = "1";
        employeeService.deleteEmployee(employeeId);
    }
}
