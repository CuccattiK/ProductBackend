package com.cuccatti.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cuccatti.inventory.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}