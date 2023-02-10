package com.anotherbank.mochabank.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.anotherbank.mochabank.authentication.domain.model.Customer;
import com.anotherbank.mochabank.domain.model.Account;

/**
* Interface for generic CRUD operations on Account repository for specific type Account.
* 
* @author Isabelle Deligniere
*
*/

public interface AccountRepository extends CrudRepository<Account, Long> {
	
	Account findByCustomer(Customer customer);
	
}
