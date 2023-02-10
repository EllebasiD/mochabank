package com.externalbank.otherbank.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.externalbank.otherbank.domain.model.ScheduledOperation;

/**
* Interface for generic CRUD operations on ScheduledOperation repository for specific type ScheduledOperation.
* 
* @author Isabelle Deligniere
*
*/


public interface ScheduledOperationRepository extends CrudRepository<ScheduledOperation, Long>{

}
