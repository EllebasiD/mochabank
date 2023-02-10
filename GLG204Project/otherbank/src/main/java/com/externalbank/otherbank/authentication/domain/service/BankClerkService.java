package com.externalbank.otherbank.authentication.domain.service;

import com.externalbank.otherbank.authentication.domain.dto.BankClerkDTO;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.CreateException;
import com.externalbank.otherbank.exception.FinderException;
import com.externalbank.otherbank.exception.RemoveException;
import com.externalbank.otherbank.exception.UpdateException;

public interface BankClerkService extends UserService {
	
	public BankClerkDTO createBankClerk(final BankClerkDTO bankClerkDTO) throws CreateException, CheckException;

    public BankClerkDTO findBankClerk(final String bankClerkId) throws FinderException, CheckException ;
    
    public void deleteBankClerk(final String bankClerkId) throws RemoveException, CheckException;

    public void updateBankClerk(final BankClerkDTO bankClerkDTO) throws UpdateException, CheckException;

    public Iterable<BankClerkDTO> findBankClerks() throws FinderException;
}
