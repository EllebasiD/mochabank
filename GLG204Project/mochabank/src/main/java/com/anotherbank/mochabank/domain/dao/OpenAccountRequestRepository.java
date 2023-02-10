package com.anotherbank.mochabank.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.anotherbank.mochabank.domain.model.OpenAccountRequest;

/**
* Interface for generic CRUD operations on OpenAccountRequest repository for specific type OpenAccountRequest.
* 
* @author Isabelle Deligniere
*
*/

public interface OpenAccountRequestRepository extends CrudRepository<OpenAccountRequest, String> {

}
