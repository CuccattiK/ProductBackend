package com.cuccatti.inventory.test.controller;

import static org.mockito.ArgumentMatchers.refEq;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cuccatti.inventory.controller.CustomerController;
import com.cuccatti.inventory.filter.CORSFilter;
import com.cuccatti.inventory.model.Customer;
import com.cuccatti.inventory.service.CustomerService;
import com.cuccatti.inventory.util.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CustomerControllerTest {

	final static String URL = "http://localhost:8080";
	final static String ENDPOINT = "/api/customers/";
	final static String GET_ALL_CUSTOMERS = URL + ENDPOINT;
	final static String GET_CUSTOMER_BY_ID = URL + ENDPOINT + "{id}";

	final static String CREATE_CUSTOMER = URL + ENDPOINT;
	final static String UPDATE_CUSTOMER = URL + ENDPOINT;
	final static String DELETE_CUSTOMER = URL + ENDPOINT + "{id}";

	private MockMvc mockMvc;

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerController customerController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).addFilters(new CORSFilter()).build();
	}

	@Test
	public void getAllCustomersSuccess() throws Exception {
		List<Customer> customers = Arrays.asList(
				Customer.builder().id(1L).email("js@yahoo.com").firstName("John").lastName("Smith").build(),
				Customer.builder().id(2L).email("ms@yahoo.com").firstName("Mary").lastName("Smith").build());
		when(customerService.getAllCustomers()).thenReturn(customers);
		mockMvc.perform(get(GET_ALL_CUSTOMERS)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].firstName", is("John"))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].firstName", is("Mary")));
		verify(customerService, times(1)).getAllCustomers();
		verifyNoMoreInteractions(customerService);
	}

	@Test
	public void getCustomerByIdSuccess() throws Exception {
		Customer customer = Customer.builder().id(1L).email("js@yahoo.com").firstName("John").lastName("Smith").build();
		when(customerService.findById(1L)).thenReturn(customer);
		mockMvc.perform(get(GET_CUSTOMER_BY_ID, 1)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.firstName", is("John")));
		verify(customerService, times(1)).findById(1L);
		verifyNoMoreInteractions(customerService);
	}

	@Test
	public void createCustomerSucceeds() throws Exception {
		Customer customer = Customer.builder().id(1L).email("js@yahoo.com").firstName("John").lastName("Smith").build();
		when(customerService.createCustomer(customer)).thenReturn(customer);
		mockMvc.perform(
				post(CREATE_CUSTOMER).contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(customer)))
				.andExpect(status().isOk());
		verify(customerService, times(1)).createCustomer(refEq(customer));
		verifyNoMoreInteractions(customerService);
	}

	@Test
	public void updateCustomerSuccess() throws Exception {
		Customer customer = Customer.builder().id(1L).email("js@yahoo.com").firstName("John").lastName("Smith").build();

		when(customerService.findById(customer.getId())).thenReturn(customer);
		when(customerService.updateCustomer(customer)).thenReturn(customer);

		mockMvc.perform(
				put(UPDATE_CUSTOMER).contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(customer)))
				.andExpect(status().isOk());

		verify(customerService, times(1)).updateCustomer(refEq(customer));
		verifyNoMoreInteractions(customerService);
	}

	@Test
	public void deleteCustomerSuccess() throws Exception {
		Customer customer = Customer.builder().id(1L).email("js@yahoo.com").firstName("John").lastName("Smith").build();

		doNothing().when(customerService).deleteCustomer(customer.getId());
		mockMvc.perform(delete(DELETE_CUSTOMER, customer.getId())).andExpect(status().isOk());

		verify(customerService, times(1)).deleteCustomer(customer.getId());
		verifyNoMoreInteractions(customerService);
	}

}
