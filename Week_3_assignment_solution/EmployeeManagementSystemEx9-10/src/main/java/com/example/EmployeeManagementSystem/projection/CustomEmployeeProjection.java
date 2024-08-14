package com.example.EmployeeManagementSystem.projection;

import org.springframework.beans.factory.annotation.Value;

public interface CustomEmployeeProjection {

	String getName();

	@Value("#{target.name + ' (' + target.department.name + ')'}")
	String getEmployeeWithDepartment();
}
