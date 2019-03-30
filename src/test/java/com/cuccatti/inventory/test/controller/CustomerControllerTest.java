package com.cuccatti.inventory.test.controller;

import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cuccatti.inventory.model.Customer;
import com.cuccatti.inventory.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerControllerTest.class)
public class CustomerControllerTest {

	final static String url = "http://localhost:8080";
	final static String endpoint = "/api/customers/";

	/*
	 * Mockito is a mocking framework, JAVA-based library that is used for effective unit testing of 
	 * JAVA applications. Mockito is used to mock interfaces so that a dummy functionality can be 
	 * added to a mock interface that can be used in unit testing.
	 */
	@MockBean
	private Customer customer;

	@MockBean
	private CustomerRepository customerRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getCustomerFailesWhenInvalidCustomerId() {
		given().when().get(endpoint + "999").then().statusCode(404);
	}

	@Test
	public void getCustomerSucceedsWhenValidCustomerId() {

		given().when().get(endpoint + "1").then().statusCode(200);
	}

	@Test
	public void addValidCustomer() {
		Map<String, String> customer = new HashMap<>();
		customer.put("firstName", "Kyle");
		customer.put("lastName", "Cuccatti");
		customer.put("middleName", "Jamison");
		given().contentType("application/json").body(customer).when().post("/api/customers").then().statusCode(200);
	}

	@Test
	public void addCustomerFailsWhenInvalidJson() {
		Map<String, String> customer = new HashMap<>();
		customer.put("FirstNm", "Kyles Customer");
		customer.put("LastNm", "Kyles Content");
		given().contentType("application/json").body(customer).when().post("/api/customers").then().statusCode(400);
	}

	@Test
	public void deleteCustomerSucceedsWhenValidCustomerId() {
		given().when().delete(endpoint + "1").then().statusCode(200);
	}

}
