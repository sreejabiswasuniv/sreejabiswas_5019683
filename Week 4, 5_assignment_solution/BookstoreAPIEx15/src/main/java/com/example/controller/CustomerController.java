package com.example.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
	public CollectionModel<EntityModel<Customer>> getAllCustomers() {
		List<EntityModel<Customer>> customers = customerRepository.findAll().stream()
				.map(customer -> EntityModel.of(customer,
						linkTo(methodOn(CustomerController.class).getCustomerById(customer.getId())).withSelfRel(),
						linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers")))
				.collect(Collectors.toList());

		return CollectionModel.of(customers,
				linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Customer>> getCustomerById(@PathVariable Long id) {
		return customerRepository.findById(id)
				.map(customer -> EntityModel.of(customer,
						linkTo(methodOn(CustomerController.class).getCustomerById(id)).withSelfRel(),
						linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers")))
				.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EntityModel<Customer>> createCustomer(@Validated @RequestBody Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);
		EntityModel<Customer> resource = EntityModel.of(savedCustomer,
				linkTo(methodOn(CustomerController.class).getCustomerById(savedCustomer.getId())).withSelfRel(),
				linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));

		return ResponseEntity.created(resource.getRequiredLink("self").toUri()).body(resource);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Customer>> updateCustomer(@PathVariable Long id,
			@Validated @RequestBody Customer customerDetails) {
		return customerRepository.findById(id).map(customer -> {
			customer.setName(customerDetails.getName());
			customer.setEmail(customerDetails.getEmail());
			Customer updatedCustomer = customerRepository.save(customer);

			EntityModel<Customer> resource = EntityModel.of(updatedCustomer,
					linkTo(methodOn(CustomerController.class).getCustomerById(updatedCustomer.getId())).withSelfRel(),
					linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));

			return ResponseEntity.ok(resource);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
		return customerRepository.findById(id).map(customer -> {
			customerRepository.delete(customer);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
