package com.example.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.EmployeeManagementSystem.entity.Employee;
import com.example.EmployeeManagementSystem.projection.EmployeeDepartmentDTO;
import com.example.EmployeeManagementSystem.projection.EmployeeDepartmentProjection;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT e.name AS name, d.name AS departmentName FROM Employee e JOIN e.department d")
	List<EmployeeDepartmentProjection> findEmployeeDepartmentData();

	@Query("SELECT new com.example.EmployeeManagementSystem.projection.EmployeeDepartmentDTO(e.name, d.name) "
			+ "FROM Employee e JOIN e.department d")
	List<EmployeeDepartmentDTO> findEmployeeDepartmentDataAsDTO();
}
