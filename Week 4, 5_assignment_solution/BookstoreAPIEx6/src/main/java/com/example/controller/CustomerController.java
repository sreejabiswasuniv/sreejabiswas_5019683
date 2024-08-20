package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerRepository customerRepository;

	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	// POST /customers - Create a new customer from JSON request body
	@PostMapping
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
	}

	// POST /customers/form - Register a new customer with form data
	@PostMapping("/form")
	public ResponseEntity<Customer> registerCustomerFromForm(HttpServletRequest request) {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		if (name == null || email == null) {
			return ResponseEntity.badRequest().build();
		}

		Customer customer = new Customer();
		customer.setName(name);
		customer.setEmail(email);
		customer.setPhone(phone);
		customer.setAddress(address);

		Customer savedCustomer = customerRepository.save(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
	}
}
