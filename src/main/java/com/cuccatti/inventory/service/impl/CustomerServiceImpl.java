package com.cuccatti.inventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.cuccatti.inventory.constants.ProductConstants;
import com.cuccatti.inventory.model.Customer;
import com.cuccatti.inventory.repository.CustomerRepository;
import com.cuccatti.inventory.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer findById(Long customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(ProductConstants.CUSTOMER_NOT_FOUND + customerId));
	}
	
	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void updateCustomer(Customer customerDetails) {
		Customer customer = customerRepository.findById(customerDetails.getId())
				.orElseThrow(() -> new ResourceNotFoundException(ProductConstants.CUSTOMER_NOT_FOUND + customerDetails.getId()));

		customer.setFirstName(customerDetails.getFirstName());
		customer.setLastName(customerDetails.getLastName());
		customer.setAddresses(customerDetails.getAddresses());
		customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(ProductConstants.CUSTOMER_NOT_FOUND + customerId));
		customerRepository.delete(customer);
	}

}
