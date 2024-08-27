package com.example.entity;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@Size(min = 1, max = 100)
	private String title;

	@NonNull
	@Size(min = 1, max = 50)
	private String author;

	@Min(0)
	private double price;

	@Version
	private int version;

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(Long id, @Size(min = 1, max = 100) String title, @Size(min = 1, max = 50) String author,
			@Min(0) double price, int version) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.price = price;
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	// Getters and Setters

}
