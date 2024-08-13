package com.example.EmployeeManagementSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
/*
 * @Getter
 * 
 * @Setter
 * 
 * @NoArgsConstructor
 */

@NamedQueries({
		@NamedQuery(name = "Employee.findByDepartmentName", query = "SELECT e FROM Employee e WHERE e.department.name = :departmentName"),
		@NamedQuery(name = "Employee.findAllSortedByName", query = "SELECT e FROM Employee e ORDER BY e.name ASC") })
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(Long id, String name, String email, Department department) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.department = department;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
