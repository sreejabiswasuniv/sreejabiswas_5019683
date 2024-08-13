package com.example.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.EmployeeManagementSystem.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	// Find department by name using @Query
	@Query("SELECT d FROM Department d WHERE d.name = :name")
	Department findByNameCustomQuery(String name);

	// Find all departments with more than a certain number of employees
	@Query("SELECT d FROM Department d WHERE size(d.employees) > :minEmployeeCount")
	List<Department> findDepartmentsWithMoreThanMinEmployees(int minEmployeeCount);
}
