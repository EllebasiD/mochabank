package com.anotherbank.mochabank.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.anotherbank.mochabank.domain.model.Operation;

/**
* Interface for generic CRUD operations on Operation repository for specific type Operation.
* 
* @author Isabelle Deligniere
*
*/

public interface OperationRepository extends CrudRepository<Operation, Long> {

}
