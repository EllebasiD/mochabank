package com.anotherbank.mochabank.authentication.domain.service;

import com.anotherbank.mochabank.authentication.domain.dto.ProspectCustomerDTO;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.RemoveException;
import com.anotherbank.mochabank.exception.UpdateException;

public interface ProspectCustomerService extends UserService {
	
	public ProspectCustomerDTO createProspectCustomer(final ProspectCustomerDTO prospectCustomerDTO) throws CreateException, CheckException;

    public ProspectCustomerDTO findProspectCustomer(final String prospectCustomerId) throws FinderException, CheckException ;

    public void deleteProspectCustomer(final String prospectCustomerId) throws RemoveException, CheckException;

    public void updateProspectCustomer(final ProspectCustomerDTO prospectCustomerDTO) throws UpdateException, CheckException;

    public Iterable<ProspectCustomerDTO> findProspectCustomers() throws FinderException;
}
