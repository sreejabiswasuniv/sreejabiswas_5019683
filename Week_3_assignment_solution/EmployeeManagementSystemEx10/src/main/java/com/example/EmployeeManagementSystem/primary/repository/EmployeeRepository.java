package com.example.EmployeeManagementSystem.primary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeManagementSystem.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	// Custom query methods (if needed)
	Employee findByEmail(String email);
}
