package com.anotherbank.mochabank.authentication.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.anotherbank.mochabank.authentication.domain.model.BankClerk;
import com.anotherbank.mochabank.authentication.domain.model.Role;

/**
* Interface for generic CRUD operations on BankClerk repository for specific type BankClerk.
* 
* @author Isabelle Deligniere
*
*/

public interface BankClerkRepository extends CrudRepository<BankClerk, String>{
	
	public Iterable<BankClerk> findAllByRole(Role role);
	
}
