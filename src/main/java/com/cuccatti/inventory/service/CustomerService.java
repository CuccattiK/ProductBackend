package com.cuccatti.inventory.service;

import java.util.List;

import com.cuccatti.inventory.model.Customer;

public interface CustomerService {
	public List<Customer> getAllCustomers();
	public Customer findById(Long customerId);
	public Customer createCustomer(Customer customer);
	public void updateCustomer(Customer customer);
	public void deleteCustomer(Long customerId);
}
