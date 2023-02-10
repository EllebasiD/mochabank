package com.anotherbank.mochabank.domain.service;

import com.anotherbank.mochabank.domain.dto.OperationDTO;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;

public interface OperationService {
	
	public OperationDTO createOperation(final OperationDTO operationDTO) throws CreateException, CheckException;

    public OperationDTO findOperation(final long operationId) throws FinderException, CheckException ;

    public Iterable<OperationDTO> findOperations() throws FinderException;
    
}
