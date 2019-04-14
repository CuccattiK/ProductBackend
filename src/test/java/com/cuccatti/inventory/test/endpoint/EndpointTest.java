package com.cuccatti.inventory.test.endpoint;

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
	final static String userEndpoint = "/api/users";
	
	@Test
	public void makeSureBackEndIsUp() {
		given().when().get(url).then().statusCode(200);
	}

	@Test
	public void pingCustomerService() {
		given().when().get(customerEndpoint).then().statusCode(200);
	}

	@Test
	public void pingUserService() {
		given().when().get(userEndpoint).then().statusCode(200);
	}

}
