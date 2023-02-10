package com.externalbank.otherbank.authentication.domain.service;

import com.externalbank.otherbank.authentication.domain.dto.CustomerDTO;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.CreateException;
import com.externalbank.otherbank.exception.FinderException;
import com.externalbank.otherbank.exception.RemoveException;
import com.externalbank.otherbank.exception.UpdateException;

public interface CustomerService extends UserService {
	
	public CustomerDTO createCustomer(final CustomerDTO customerDTO) throws CreateException, CheckException;

    public CustomerDTO findCustomer(final String customerId) throws FinderException, CheckException ;

    public void deleteCustomer(final String customerId) throws RemoveException, CheckException;

    public void updateCustomer(final CustomerDTO customerDTO) throws UpdateException, CheckException;

    public Iterable<CustomerDTO> findCustomers() throws FinderException;

	public CustomerDTO findCustomerByAccountId(final long accountId) throws FinderException, CheckException;
}
