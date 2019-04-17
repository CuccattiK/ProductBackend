package com.cuccatti.inventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cuccatti.inventory.constants.ProductConstants;
import com.cuccatti.inventory.model.Customer;
import com.cuccatti.inventory.repository.CustomerRepository;
import com.cuccatti.inventory.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> getAllCustomers() {
		LOGGER.info("Accessing getAllCustomers");
		return customerRepository.findAll();
	}

	@Override
	public Customer findById(Long customerId) {
		LOGGER.info("Accessing find customer with id of {}", customerId);
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(ProductConstants.CUSTOMER_NOT_FOUND + customerId));
	}

	@Override
	public Customer createCustomer(Customer customer) {
		LOGGER.info("Accessing save customer for: lastName: {}, firstName: {}", customer.getLastName(),
				customer.getFirstName());
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customerDetails) {
		LOGGER.info("Updating customer with id of {}, lastName: {}, firstName: {}", customerDetails.getId(),
				customerDetails.getLastName(), customerDetails.getFirstName());

		Customer customer = customerRepository.findById(customerDetails.getId()).orElseThrow(
				() -> new ResourceNotFoundException(ProductConstants.CUSTOMER_NOT_FOUND + customerDetails.getId()));

		customer.setFirstName(customerDetails.getFirstName());
		customer.setLastName(customerDetails.getLastName());
		customer.setAddresses(customerDetails.getAddresses());
		customer.setPhones(customerDetails.getPhones());
		return customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(Long customerId) {
		LOGGER.info("Accessing delete customer with id of {}.", customerId);
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(ProductConstants.CUSTOMER_NOT_FOUND + customerId));
		customerRepository.delete(customer);
	}

}
