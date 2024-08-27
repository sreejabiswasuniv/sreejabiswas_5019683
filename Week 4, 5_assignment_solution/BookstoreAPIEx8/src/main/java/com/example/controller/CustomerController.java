package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		return customerRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Customer createCustomer(@Validated @RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,
			@Validated @RequestBody Customer customerDetails) {
		return customerRepository.findById(id).map(customer -> {
			customer.setName(customerDetails.getName());
			customer.setEmail(customerDetails.getEmail());
			return ResponseEntity.ok(customerRepository.save(customer));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public String deleteCustomer(@PathVariable Long id) {
		return customerRepository.findById(id).map(customer -> {
			customerRepository.delete(customer);
			return "Customer deleted successfully.";
		}).orElse("Customer not found.");
	}

}
