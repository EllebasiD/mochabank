package com.anotherbank.mochabank.authentication.domain.service;

import com.anotherbank.mochabank.authentication.domain.dto.BankClerkDTO;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.exception.UpdateException;

public interface BankClerkService extends UserService {
	
	public BankClerkDTO createBankClerk(final BankClerkDTO bankClerkDTO) throws CreateException, CheckException;

    public BankClerkDTO findBankClerk(final String bankClerkId) throws FinderException, CheckException ;
    
    public void deleteBankClerk(final String bankClerkId) throws RemoveException, CheckException;

    public void updateBankClerk(final BankClerkDTO bankClerkDTO) throws UpdateException, CheckException;

    public Iterable<BankClerkDTO> findBankClerks() throws FinderException;
}
