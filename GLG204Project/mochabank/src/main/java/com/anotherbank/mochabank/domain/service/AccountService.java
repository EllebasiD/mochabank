package com.anotherbank.mochabank.domain.service;

import com.anotherbank.mochabank.domain.dto.AccountDTO;
import com.anotherbank.mochabank.domain.model.Operation;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.OperationException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.exception.TransferException;

public interface AccountService {
	
	public AccountDTO createAccount(final AccountDTO accountDTO) throws CreateException, CheckException;

    public AccountDTO findAccount(final long accountId) throws FinderException, CheckException ;
    
    public AccountDTO findAccountByCustomerId(final String username) throws FinderException;
    
    public void deleteAccount(final long accountId) throws RemoveException, CheckException;

    public Iterable<AccountDTO> findAccounts() throws FinderException;    
    
    public Iterable<Operation> checkTransactions(long accountId) throws FinderException;
    
    public boolean makeDeposit(final long accountId, final long amount) throws FinderException, OperationException;
    
    public boolean orderInternalBankTransfer(final long accountIdA, final long accountIdB, final long amount) throws FinderException, OperationException, TransferException;
    
    public boolean orderExternalBankTransfer(final long accountIdA, final long accountIdB, final long amount) throws FinderException, OperationException, TransferException;
    
    public boolean receiveExternalTransfer(final long accountId, final long amount) throws FinderException, OperationException;

    public boolean scheduleAutoBankTransfer(final long accountIdA, final long accountIdB, long amount, final String minute, final String frequency) throws FinderException, OperationException;
    
}
