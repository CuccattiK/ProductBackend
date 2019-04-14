package com.cuccatti.inventory.controller;

import java.util.List;

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

import com.cuccatti.inventory.model.Customer;
import com.cuccatti.inventory.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "/customer")
public class CustomerController {

	private static Logger logger = LogManager.getLogger(CustomerController.class);

	@Autowired
	CustomerService customerService;

	@ApiOperation(value = "View a list of existing customers", response = Iterable.class)
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	@ApiOperation(value = "Find a customer by id", response = Iterable.class)
	@GetMapping("/customers/{id}")
	public Customer getCustomerById(@PathVariable(value = "id") Long customerId) {
		logger.info("Accessing find Customer with id of {}", customerId);
		return customerService.findById(customerId);
	}

	@ApiOperation(value = "Add a new customer", response = Iterable.class)
	@PostMapping("/customers")
	public Customer createCustomer(@Valid @RequestBody Customer customer) {
		logger.info("Accessing save Customer for: lastName: {}, firstName: {}", customer.getLastName(),
				customer.getFirstName());
		return customerService.createCustomer(customer);
	}

	@ApiOperation(value = "Update a customer", response = Iterable.class)
	@PutMapping("/customers")
	public void updateCustomer(@Valid @RequestBody Customer customerDetails) {
		logger.info("Updating Customer with id of {} with lastName: {}, firstName: {}", customerDetails.getId(),
				customerDetails.getLastName(), customerDetails.getFirstName());
		customerService.createCustomer(customerDetails);
	}

	@ApiOperation(value = "Delete a customer by id", response = Iterable.class)
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable(value = "id") Long customerId) {
		logger.info("Accessing delete customer with customer id of {}.", customerId);
		customerService.deleteCustomer(customerId);
	}

}
