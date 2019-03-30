package com.cuccatti.inventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


// Allows the created at and last updated date to be automatically populated
@EnableJpaAuditing

// Lets us know that this is the class that starts up the spring boot app
@SpringBootApplication
public class WebApplication extends SpringBootServletInitializer {

	private static final Logger LOGGER = LogManager.getLogger(WebApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
		LOGGER.info("Starting Spring App");
	}

}
