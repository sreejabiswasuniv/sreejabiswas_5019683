package com.example.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeManagementSystem.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	// Derived query method to find employees by department name
	List<Employee> findByDepartmentName(String departmentName);

	// Derived query method to find an employee by email
	Employee findByEmail(String email);
}
