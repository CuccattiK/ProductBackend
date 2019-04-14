package com.cuccatti.inventory.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cuccatti.inventory.controller.CustomerController;
import com.cuccatti.inventory.model.Customer;
import com.cuccatti.inventory.util.Utils;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CustomerControllerTest {

	final static String URL = "http://localhost:8080";
	final static String ENDPOINT = "/api/customers/";
	final static String GET_ALL_CUSTOMERS = URL + ENDPOINT;
	final static String GET_CUSTOMER_BY_ID = URL + ENDPOINT + "1";
	final static String CREATE_CUSTOMER = URL + ENDPOINT;
	final static String UPDATE_CUSTOMER = URL + ENDPOINT;
	final static String DELETE_CUSTOMER = URL + ENDPOINT + "1";

	private MockMvc mockMvc;

	@Autowired
	CustomerController customerController;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getAllCustomers() throws Exception {
		mockMvc.perform(get(GET_ALL_CUSTOMERS).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		// Mockito.verify(customerController, Mockito.times(1)).getAllCustomers();
	}

	@Test
	public void findCustomerById() throws Exception {
		mockMvc.perform(get(GET_CUSTOMER_BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void createCustomer() throws Exception {
		Customer customer = new Customer(1L, "John", "Doe");
	    mockMvc.perform(MockMvcRequestBuilders.post(CREATE_CUSTOMER)
				  .content(Utils.asJsonString(customer))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void updateCustomer() throws Exception {
		Customer customer = new Customer(1L, "John", "Doe");
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_CUSTOMER)
				  .content(Utils.asJsonString(customer))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void deleteCustomer() throws Exception {
		mockMvc.perform(delete(DELETE_CUSTOMER).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	
}
