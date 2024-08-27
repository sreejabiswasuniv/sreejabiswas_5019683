package com.example.entity;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Size;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@Size(min = 1, max = 100)
	private String name;

	@NonNull
	@Size(min = 5, max = 100)
	private String email;

	@Version
	private int version;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(Long id, @Size(min = 1, max = 100) String name, @Size(min = 5, max = 100) String email,
			int version) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.version = version;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	// Getters and Setters

}
