package com.cuccatti.inventory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuccatti.inventory.exception.ResourceNotFoundException;
import com.cuccatti.inventory.model.Customer;
import com.cuccatti.inventory.repository.CustomerRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "customer")
public class CustomerController {

	private static Logger logger = LogManager.getLogger(CustomerController.class);

	@Autowired
	CustomerRepository customerRepository;

	@ApiOperation(value = "View a list of existing customers", response = Iterable.class)
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		logger.info("Accessing getAllCustomers");
		return customerRepository.findAll();
	}

	@ApiOperation(value = "Find a customer by id", response = Iterable.class)
	@GetMapping("/customers/{id}")
	public Customer getCustomerById(@PathVariable(value = "id") Long customerId) {

		logger.info("Accessing find Customer with id of {}", customerId);

		return customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
	}

	@ApiOperation(value = "Add a new customer", response = Iterable.class)
	@PostMapping("/customers")
	public Customer createCustomer(@Valid @RequestBody Customer customer) {
		logger.info("Accessing save Customer for: lastName: {}, firstName: {}", customer.getLastName(),
				customer.getFirstName());
		return customerRepository.save(customer);
	}

	@ApiOperation(value = "Update a customer by id", response = Iterable.class)
	@PutMapping("/customers/{id}")
	public Customer updateCustomer(@PathVariable(value = "id") Long customerId,
			@Valid @RequestBody Customer customerDetails) {

		logger.info("Updating Customer with id of {} with lastName: {}, firstName: {}", customerId,
				customerDetails.getLastName(), customerDetails.getFirstName());

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

		customer.setFirstName(customerDetails.getFirstName());
		customer.setLastName(customerDetails.getLastName());
		customer.setAddresses(customerDetails.getAddresses());

		return customerRepository.save(customer);
	}

	@ApiOperation(value = "Delete a customer by id", response = Iterable.class)
	@DeleteMapping("/customers/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId) {
		logger.info("Accessing delete customer with customer id of {}.", customerId);

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

		customerRepository.delete(customer);
		
		customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
