package com.example.backend.service;

import com.example.backend.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    public List<Employee> getAllEmployees();

    public Optional<Employee> getEmployeeById(String id);

    public Employee createEmployee(Employee employee);

    public void updateEmployee(String id, Employee employee);

    public void deleteEmployee(String id);
}
