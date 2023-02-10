package com.externalbank.otherbank.domain.service;

import com.externalbank.otherbank.domain.dto.OpenAccountRequestDTO;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.CreateException;
import com.externalbank.otherbank.exception.FinderException;
import com.externalbank.otherbank.exception.RemoveException;

public interface OpenAccountRequestService {
	
	public OpenAccountRequestDTO createOpenAccountRequest(final OpenAccountRequestDTO openAccountRequestDTO) throws CreateException, CheckException;

    public OpenAccountRequestDTO findOpenAccountRequest(final String openAccountRequestUsername) throws FinderException, CheckException ;

    public void deleteOpenAccountRequest(final String openAccountRequestUsername) throws RemoveException, CheckException;

    public Iterable<OpenAccountRequestDTO> findOpenAccountRequests() throws FinderException;
}
