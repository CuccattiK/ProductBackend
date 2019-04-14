package com.cuccatti.inventory.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuccatti.inventory.model.User;
import com.cuccatti.inventory.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "user")
public class UserController {

	private static Logger logger = LogManager.getLogger(CustomerController.class);

	@Autowired
	private UserService userService;

	@ApiOperation(value = "View a list of existing users", response = Iterable.class)
	@GetMapping("/users")
	public List<User> getAllUsers() {
		logger.info("Accessing getAllUsers");
		return userService.getAllUsers();
	}

	@ApiOperation(value = "Find user by id", response = Iterable.class)
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable(value = "id") Long userId) {
		logger.info("Accessing find User with id of {}", userId);
		return userService.findById(userId);
	}

	@ApiOperation(value = "Add a new user", response = Iterable.class)
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		logger.info("Accessing save User for: lastName: {}, firstName: {}", user.getLastName(), user.getFirstName());
		return userService.createUser(user);
	}

	@ApiOperation(value = "update a user", response = Iterable.class)
	@PutMapping("/users")
	public User updateUser(@Valid @RequestBody User userDetails) {
		logger.info("Updating user with id of {} with lastName: {}, firstName: {}", userDetails.getId(),
				userDetails.getLastName(), userDetails.getFirstName());
		return userService.updateUser(userDetails);
	}

	@ApiOperation(value = "Delete a user by id", response = Iterable.class)
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable(value = "id") Long userId) {
		logger.info("Accessing delete user with id of {}.", userId);
		userService.deleteUser(userId);
	}
}