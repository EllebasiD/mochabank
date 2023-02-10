package com.externalbank.otherbank.domain.service;

import com.externalbank.otherbank.domain.dto.TransactionDTO;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.FinderException;

public interface TransactionService {
	
    public TransactionDTO findTransaction(final long transactionId) throws FinderException, CheckException ;

    public Iterable<TransactionDTO> findTransactions() throws FinderException;
    
}
