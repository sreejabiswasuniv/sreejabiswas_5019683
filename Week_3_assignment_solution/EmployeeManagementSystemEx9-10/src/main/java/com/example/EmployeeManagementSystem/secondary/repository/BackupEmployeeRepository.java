package com.example.EmployeeManagementSystem.secondary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeManagementSystem.entity.Employee;

@Repository
public interface BackupEmployeeRepository extends JpaRepository<Employee, Long> {
	// Custom query methods (if needed)
	Employee findByBackupEmail(String backupEmail);
}
