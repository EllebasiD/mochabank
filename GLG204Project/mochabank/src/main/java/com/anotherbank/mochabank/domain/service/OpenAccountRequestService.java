package com.anotherbank.mochabank.domain.service;

import com.anotherbank.mochabank.domain.dto.OpenAccountRequestDTO;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.CreateException;
import com.anotherbank.mochabank.exception.FinderException;
import com.anotherbank.mochabank.exception.RemoveException;

public interface OpenAccountRequestService {
	
	public OpenAccountRequestDTO createOpenAccountRequest(final OpenAccountRequestDTO openAccountRequestDTO) throws CreateException, CheckException;

    public OpenAccountRequestDTO findOpenAccountRequest(final String openAccountRequestUsername) throws FinderException, CheckException ;

    public void deleteOpenAccountRequest(final String openAccountRequestUsername) throws RemoveException, CheckException;

    public Iterable<OpenAccountRequestDTO> findOpenAccountRequests() throws FinderException;
}
