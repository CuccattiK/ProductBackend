package com.cuccatti.inventory.service;

import java.util.List;

import com.cuccatti.inventory.model.User;

public interface UserService {
	public List<User> getAllUsers();
	public User findById(Long userId);
	public User createUser(User user);
	public User updateUser(User user);
	public void deleteUser(Long userId);
}
