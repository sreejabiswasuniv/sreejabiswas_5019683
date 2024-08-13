package com.example.EmployeeManagementSystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeManagementSystem.entity.Employee;
import com.example.EmployeeManagementSystem.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	// Create a new Employee
	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	// Get all Employees
	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	// Get an Employee by ID
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Update an Employee
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		if (employeeOptional.isPresent()) {
			Employee employee = employeeOptional.get();
			employee.setName(updatedEmployee.getName());
			employee.setEmail(updatedEmployee.getEmail());
			employee.setDepartment(updatedEmployee.getDepartment());
			return ResponseEntity.ok(employeeRepository.save(employee));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Delete an Employee
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		if (employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
