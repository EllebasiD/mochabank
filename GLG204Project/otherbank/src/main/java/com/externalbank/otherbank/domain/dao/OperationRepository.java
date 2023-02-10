package com.externalbank.otherbank.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.externalbank.otherbank.domain.model.Operation;

/**
* Interface for generic CRUD operations on Operation repository for specific type Operation.
* 
* @author Isabelle Deligniere
*
*/

public interface OperationRepository extends CrudRepository<Operation, Long> {

}
