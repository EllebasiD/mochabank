package com.externalbank.otherbank.authentication.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.externalbank.otherbank.authentication.domain.model.Role;
import com.externalbank.otherbank.authentication.domain.model.User;

/**
* Interface for generic CRUD operations on User repository for specific type User.
* 
* @author Teachers of GLG203 Unit
*
*/

public interface UserRepository extends CrudRepository<User, String> {
	
	public Iterable<User> findAllByRole(Role role);
	
}
