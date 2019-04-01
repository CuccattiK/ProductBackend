package com.cuccatti.inventory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.cuccatti.inventory.security.impl.AuditorAwareImpl;

// Lets us know that this is the class that starts up the spring boot app
@SpringBootApplication
//Allows the created at and last updated date to be automatically populated
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class WebApplication extends SpringBootServletInitializer {

	private static final Logger LOGGER = LogManager.getLogger(WebApplication.class);

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
		LOGGER.info("Starting Spring App");
	}

}
