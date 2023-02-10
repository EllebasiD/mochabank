package com.externalbank.otherbank.authentication.domain.service;

import com.externalbank.otherbank.authentication.domain.dto.UserDTO;
import com.externalbank.otherbank.authentication.domain.model.Role;
import com.externalbank.otherbank.exception.CheckException;
import com.externalbank.otherbank.exception.FinderException;

public interface UserService {
	
	public UserDTO findUser(final String userId) throws FinderException, CheckException ;

    public Iterable<UserDTO> findUsers() throws FinderException;

	public Iterable<UserDTO> findUsersByRole(Role role);
}
