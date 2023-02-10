package com.anotherbank.mochabank.authentication.domain.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anotherbank.mochabank.authentication.domain.dto.UserDTO;
import com.anotherbank.mochabank.exception.CheckException;
import com.anotherbank.mochabank.exception.FinderException;

/**
 * This class is a facade for user details services.
 * 
 * @author Teachers of GLG203 Unit
 *
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO userDTO = null;
		try {
			userDTO = userService.findUser(username);
		} catch (FinderException | CheckException e) {
			e.printStackTrace();
		}
		if (userDTO == null) {
			throw new UsernameNotFoundException("User not found");
		} else {
			List<SimpleGrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(userDTO.getRoleName()));
			return new org.springframework.security.core.userdetails.User(userDTO.getUsername(), userDTO.getPassword(), grantedAuthorities);
		}
	}

}
