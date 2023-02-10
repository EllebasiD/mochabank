package com.externalbank.otherbank.authentication.domain.service;

import com.externalbank.otherbank.authentication.domain.dto.ProspectCustomerDTO;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.CreateException;
import com.externalbank.otherbank.exception.FinderException;
import com.externalbank.otherbank.exception.RemoveException;
import com.externalbank.otherbank.exception.UpdateException;

public interface ProspectCustomerService extends UserService {
	
	public ProspectCustomerDTO createProspectCustomer(final ProspectCustomerDTO prospectCustomerDTO) throws CreateException, CheckException;

    public ProspectCustomerDTO findProspectCustomer(final String prospectCustomerId) throws FinderException, CheckException ;

    public void deleteProspectCustomer(final String prospectCustomerId) throws RemoveException, CheckException;

    public void updateProspectCustomer(final ProspectCustomerDTO prospectCustomerDTO) throws UpdateException, CheckException;

    public Iterable<ProspectCustomerDTO> findProspectCustomers() throws FinderException;
}
