package com.anotherbank.mochabank.authentication.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.anotherbank.mochabank.authentication.domain.model.Role;

/**
* Interface for generic CRUD operations on User repository for specific type Role.
* 
* @author Teachers of GLG203 Unit
*/

public interface RoleRepository extends CrudRepository<Role, Integer> {	
	
	public Role findByName(String name);
	
}
