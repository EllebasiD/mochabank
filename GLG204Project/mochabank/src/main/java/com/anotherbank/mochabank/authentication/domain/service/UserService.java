package com.anotherbank.mochabank.authentication.domain.service;

import com.anotherbank.mochabank.authentication.domain.dto.UserDTO;
import com.anotherbank.mochabank.authentication.domain.model.Role;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.FinderException;

public interface UserService {
	
	public UserDTO findUser(final String userId) throws FinderException, CheckException ;

    public Iterable<UserDTO> findUsers() throws FinderException;

	public Iterable<UserDTO> findUsersByRole(Role role);
}
