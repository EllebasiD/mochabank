package com.externalbank.otherbank.domain.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.externalbank.otherbank.domain.model.Account;
import com.externalbank.otherbank.domain.model.Transaction;

/**
* Interface for generic CRUD operations on Transaction repository for specific type Transaction.
* 
* @author Isabelle Deligniere
*
*/

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	
	Set<Transaction> findAllByAccountA(Account accountA);
	
}
