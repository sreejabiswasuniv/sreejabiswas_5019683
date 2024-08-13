package com.example.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeManagementSystem.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	// Find employees by name
	List<Employee> findByName(String name);

	// Find employees by department name
	List<Employee> findByDepartmentName(String departmentName);

	// Find employees by email containing a certain string (case insensitive)
	List<Employee> findByEmailContainingIgnoreCase(String emailFragment);
}
