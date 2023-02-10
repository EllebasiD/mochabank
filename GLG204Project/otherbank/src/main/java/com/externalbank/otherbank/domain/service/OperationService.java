package com.externalbank.otherbank.domain.service;

import com.externalbank.otherbank.domain.dto.OperationDTO;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.CreateException;
import com.externalbank.otherbank.exception.FinderException;

public interface OperationService {
	
	public OperationDTO createOperation(final OperationDTO operationDTO) throws CreateException, CheckException;

    public OperationDTO findOperation(final long operationId) throws FinderException, CheckException ;

    public Iterable<OperationDTO> findOperations() throws FinderException;
    
}
