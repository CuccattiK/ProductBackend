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

import com.cuccatti.inventory.controller.UserController;
import com.cuccatti.inventory.model.Customer;
import com.cuccatti.inventory.model.User;
import com.cuccatti.inventory.util.Utils;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTest {

	final static String URL = "http://localhost:8080";
	final static String ENDPOINT = "/api/customers/";
	final static String GET_ALL_USERS = URL + ENDPOINT;
	final static String GET_USER_BY_ID = URL + ENDPOINT + "1";
	final static String CREATE_USER = URL + ENDPOINT;
	final static String UPDATE_USER = URL + ENDPOINT;
	final static String DELETE_USER = URL + ENDPOINT + "1";

	private MockMvc mockMvc;

	@Autowired
	UserController userController;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getAllUsers() throws Exception {
		mockMvc.perform(get(GET_ALL_USERS).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		// Mockito.verify(customerController, Mockito.times(1)).getAllCustomers();
	}

	@Test
	public void findUserById() throws Exception {
		mockMvc.perform(get(GET_USER_BY_ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void createUser() throws Exception {
		User user = new User(1L, "John", "Doe", "john.doe@yahoo.com");
        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_USER)
				  .content(Utils.asJsonString(user))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void updateCustomer() throws Exception {
		Customer customer = new Customer(1L, "John", "Doe");
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_USER)
				  .content(Utils.asJsonString(customer))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void deleteUser() throws Exception {
		mockMvc.perform(delete(DELETE_USER).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	
}
