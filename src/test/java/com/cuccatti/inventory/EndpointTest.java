package com.cuccatti.inventory;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EndpointTest.class)
public class EndpointTest {

	final static String url = "http://localhost:8080";
	final static String customerEndpoint = "/api/customers/";
	final static String noteEndpoint = "/api/notes/";
	final static String productEndpoint = "/api/products/";
	final static String addressEndpoint = "/api/address/";

	@Test
	public void makeSureBackEndIsUp() {
		given().when().get(url).then().statusCode(200);
	}

	@Test
	public void pingCustomerService() {
		given().when().get(customerEndpoint).then().statusCode(200);
	}

	@Test
	public void pingNoteService() {
		given().when().get(noteEndpoint).then().statusCode(200);
	}

	@Test
	public void pingProductService() {
		given().when().get(productEndpoint).then().statusCode(200);
	}
	
	@Test
	public void pingAddressService() {
		given().when().get(addressEndpoint).then().statusCode(200);
	}

}
