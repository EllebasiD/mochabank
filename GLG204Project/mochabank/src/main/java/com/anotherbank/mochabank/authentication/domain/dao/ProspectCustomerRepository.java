package com.anotherbank.mochabank.authentication.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.anotherbank.mochabank.authentication.domain.model.ProspectCustomer;
import com.anotherbank.mochabank.authentication.domain.model.Role;

/**
* Interface for generic CRUD operations on ProspectCustomer repository for specific type ProspectCustomer.
* 
* @author Isabelle Deligniere
*
*/

public interface ProspectCustomerRepository extends CrudRepository<ProspectCustomer, String>{
	
	public Iterable<ProspectCustomer> findAllByRole(Role role);
	
}
