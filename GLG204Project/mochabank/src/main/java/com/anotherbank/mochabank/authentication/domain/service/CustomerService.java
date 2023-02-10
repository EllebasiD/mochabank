package com.anotherbank.mochabank.authentication.domain.service;

import com.anotherbank.mochabank.authentication.domain.dto.CustomerDTO;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.exception.UpdateException;

public interface CustomerService extends UserService {
	
	public CustomerDTO createCustomer(final CustomerDTO customerDTO) throws CreateException, CheckException;

    public CustomerDTO findCustomer(final String customerId) throws FinderException, CheckException ;

    public void deleteCustomer(final String customerId) throws RemoveException, CheckException;

    public void updateCustomer(final CustomerDTO customerDTO) throws UpdateException, CheckException;

    public Iterable<CustomerDTO> findCustomers() throws FinderException;

	public CustomerDTO findCustomerByAccountId(final long accountId) throws FinderException, CheckException;
}
