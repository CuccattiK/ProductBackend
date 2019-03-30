package com.cuccatti.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cuccatti.inventory.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM customer WHERE lst_nm = ?1")
	Customer findCustomerByLastname(String lastname);

	@Query(nativeQuery = true, value = "select C.*, A.* from customer C inner join customer_addresses ca on C.id = ca.customers_id inner join address A on A.id = ca.addresses_id")
	List<Customer> findAll();
}