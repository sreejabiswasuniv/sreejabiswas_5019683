package com.example.EmployeeManagementSystem.projection;

public class EmployeeDepartmentDTO {

	private String name;
	private String departmentName;

	public EmployeeDepartmentDTO(String name, String departmentName) {
		this.name = name;
		this.departmentName = departmentName;
	}

	// Getters and setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
