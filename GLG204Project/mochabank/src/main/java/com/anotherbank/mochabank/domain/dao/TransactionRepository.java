package com.anotherbank.mochabank.domain.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.anotherbank.mochabank.domain.model.Account;
import com.anotherbank.mochabank.domain.model.Transaction;

/**
* Interface for generic CRUD operations on Transaction repository for specific type Transaction.
* 
* @author Isabelle Deligniere
*
*/

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	
	Set<Transaction> findAllByAccountA(Account accountA);
	
}
