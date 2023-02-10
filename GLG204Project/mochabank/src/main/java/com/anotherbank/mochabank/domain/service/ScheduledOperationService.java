package com.anotherbank.mochabank.domain.service;

import com.anotherbank.mochabank.domain.dto.ScheduledOperationDTO;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;

public interface ScheduledOperationService {
	
	public ScheduledOperationDTO createScheduledOperation(final ScheduledOperationDTO scheduledOperationDTO) throws CreateException, CheckException;

    public ScheduledOperationDTO findScheduledOperation(final long scheduledOperationId) throws FinderException, CheckException ;

    public Iterable<ScheduledOperationDTO> findScheduledOperations() throws FinderException;
    
}
