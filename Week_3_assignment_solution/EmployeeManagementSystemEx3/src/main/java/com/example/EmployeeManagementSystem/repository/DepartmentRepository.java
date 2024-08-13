package com.example.EmployeeManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeManagementSystem.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	// Derived query method to find a department by name
	Department findByName(String name);
}
