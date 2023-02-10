package com.anotherbank.mochabank.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.anotherbank.mochabank.domain.model.ScheduledOperation;

/**
* Interface for generic CRUD operations on ScheduledOperation repository for specific type ScheduledOperation.
* 
* @author Isabelle Deligniere
*
*/


public interface ScheduledOperationRepository extends CrudRepository<ScheduledOperation, Long>{

}
