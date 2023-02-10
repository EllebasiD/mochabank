package com.externalbank.otherbank.authentication.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.externalbank.otherbank.authentication.domain.model.Customer;
import com.externalbank.otherbank.authentication.domain.model.Role;
import com.externalbank.otherbank.domain.model.Account;

/**
* Interface for generic CRUD operations on Customer repository for specific type Customer.
* 
* @author Isabelle Deligniere
*
*/

public interface CustomerRepository extends CrudRepository<Customer, String>{
	
	Iterable<Customer> findAllByRole(Role role);
	
	Customer findByAccount(Account account);
	
}
