package com.externalbank.otherbank.authentication.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.externalbank.otherbank.authentication.domain.model.BankClerk;
import com.externalbank.otherbank.authentication.domain.model.Role;

/**
* Interface for generic CRUD operations on BankClerk repository for specific type BankClerk.
* 
* @author Isabelle Deligniere
*
*/

public interface BankClerkRepository extends CrudRepository<BankClerk, String>{
	
	public Iterable<BankClerk> findAllByRole(Role role);
	
}
