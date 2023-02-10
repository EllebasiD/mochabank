package com.externalbank.otherbank.domain.service;

import com.externalbank.otherbank.domain.dto.ScheduledOperationDTO;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.CreateException;
import com.externalbank.otherbank.exception.FinderException;

public interface ScheduledOperationService {
	
	public ScheduledOperationDTO createScheduledOperation(final ScheduledOperationDTO scheduledOperationDTO) throws CreateException, CheckException;

    public ScheduledOperationDTO findScheduledOperation(final long scheduledOperationId) throws FinderException, CheckException ;

    public Iterable<ScheduledOperationDTO> findScheduledOperations() throws FinderException;
    
}
