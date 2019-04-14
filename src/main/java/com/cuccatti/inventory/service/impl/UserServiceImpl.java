package com.cuccatti.inventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.cuccatti.inventory.constants.ProductConstants;
import com.cuccatti.inventory.model.User;
import com.cuccatti.inventory.repository.UserRepository;
import com.cuccatti.inventory.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ProductConstants.USER_NOT_FOUND + userId));
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void updateUser(User userDetails) {
		User user = userRepository.findById(userDetails.getId()).orElseThrow(
				() -> new ResourceNotFoundException(ProductConstants.USER_NOT_FOUND + userDetails.getId()));

		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmailId(userDetails.getEmailId());
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(ProductConstants.USER_NOT_FOUND + userId));
		userRepository.delete(user);

	}

}
