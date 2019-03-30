package com.cuccatti.inventory.test.controller;

import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cuccatti.inventory.controller.ProductController;
import com.cuccatti.inventory.model.Product;
import com.cuccatti.inventory.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductConrollerTest.class)
public class ProductConrollerTest {

	final static String url = "http://localhost:8080";
	final static String endpoint = "/api/products/";
	
	@MockBean
	private Product product;

	@MockBean
	private ProductRepository productRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getProductFailesWhenInvalidProductId() {
		given().when().get(endpoint + "999").then().statusCode(404);
	}

	@Test
	public void getProductSucceedsWhenValidProductId() {
		given().when().get(endpoint + "1").then().statusCode(200);
	}

	@Test
	public void addValidProduct() {
		Map<String, String> product = new HashMap<>();
		product.put("shortDescription", "My example product");
		product.put("fullDescription", "This is the full desc for my example product");
		product.put("quantityOnHand", "300");
		given().contentType("application/json").body(product).when().post("/api/products").then().statusCode(200);
	}

	@Test
	public void addProductFailsWhenInvalidJson() {
		Map<String, String> product = new HashMap<>();
		product.put("FirstNm", "Kyles Product");
		product.put("LastNm", "Kyles Content");
		given().contentType("application/json").body(product).when().post("/api/products").then().statusCode(400);
	}	
	
	
}
