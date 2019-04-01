package com.cuccatti.inventory.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuccatti.inventory.constants.ProductConstants;
import com.cuccatti.inventory.model.User;
import com.cuccatti.inventory.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "customer")
public class UserController {

	private static Logger logger = LogManager.getLogger(CustomerController.class);

	@Autowired
	private UserRepository userRepository;

	@ApiOperation(value = "View a list of existing users", response = Iterable.class)
	@GetMapping("/users")
	public List<User> getAllUsers() {
		logger.info("Accessing getAllUsers");
		return userRepository.findAll();
	}

	@ApiOperation(value = "Find user by id", response = Iterable.class)
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable(value = "id") Long userId) {

		logger.info("Accessing find User with id of {}", userId);

		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ProductConstants.USER_NOT_FOUND + userId));
	}

	@ApiOperation(value = "Add a new user", response = Iterable.class)
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		logger.info("Accessing save User for: lastName: {}, firstName: {}", user.getLastName(), user.getFirstName());
		return userRepository.save(user);
	}

	@ApiOperation(value = "update a user", response = Iterable.class)
	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable(value = "id") Long userId,
			@Valid @RequestBody User userDetails) {

		logger.info("Updating user with id of {} with lastName: {}, firstName: {}", userId, userDetails.getLastName(),
				userDetails.getFirstName());

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ProductConstants.USER_NOT_FOUND + userId));

		user.setEmailId(userDetails.getEmailId());
		user.setLastName(userDetails.getLastName());
		user.setFirstName(userDetails.getFirstName());
		user.setLastModifiedDate(new Date());

		return userRepository.save(user);
	}

	@ApiOperation(value = "Delete a user by id", response = Iterable.class)
	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) {

		logger.info("Accessing delete user with id of {}.", userId);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ProductConstants.USER_NOT_FOUND + userId));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}