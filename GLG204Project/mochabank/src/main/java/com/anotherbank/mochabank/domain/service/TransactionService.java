package com.anotherbank.mochabank.domain.service;

import com.anotherbank.mochabank.domain.dto.TransactionDTO;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.FinderException;

public interface TransactionService {
	
    public TransactionDTO findTransaction(final long transactionId) throws FinderException, CheckException ;

    public Iterable<TransactionDTO> findTransactions() throws FinderException;
    
}
