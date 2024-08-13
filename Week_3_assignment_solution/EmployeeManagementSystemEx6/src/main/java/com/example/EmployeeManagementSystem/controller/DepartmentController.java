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

import com.example.EmployeeManagementSystem.entity.Department;
import com.example.EmployeeManagementSystem.repository.DepartmentRepository;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	@Autowired
	private DepartmentRepository departmentRepository;

	// Create a new Department
	@PostMapping
	public Department createDepartment(@RequestBody Department department) {
		return departmentRepository.save(department);
	}

	// Get all Departments
	@GetMapping
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	// Get a Department by ID
	@GetMapping("/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
		Optional<Department> department = departmentRepository.findById(id);
		return department.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Update a Department
	@PutMapping("/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable Long id,
			@RequestBody Department updatedDepartment) {
		Optional<Department> departmentOptional = departmentRepository.findById(id);
		if (departmentOptional.isPresent()) {
			Department department = departmentOptional.get();
			department.setName(updatedDepartment.getName());
			return ResponseEntity.ok(departmentRepository.save(department));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Delete a Department
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
		if (departmentRepository.existsById(id)) {
			departmentRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
