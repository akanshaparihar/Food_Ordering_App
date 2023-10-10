package com.example.backend.service;

import com.example.backend.entity.Employee;
import com.example.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Optional<Employee> getEmployeeById(String id) {
        return repository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    public void updateEmployee(String id, Employee employee) {
        if (repository.existsById(id)) {
            employee.setId(id);
            repository.save(employee);
        }
    }

    public void deleteEmployee(String id) {
        repository.deleteById(id);
    }
}
