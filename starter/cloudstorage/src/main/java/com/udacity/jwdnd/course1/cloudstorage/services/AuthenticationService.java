package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class AuthenticationService implements AuthenticationProvider{
	
	private UserMapper userMapper;
	private HashService hashService;
	
	public AuthenticationService(UserMapper userMapper, HashService hashService) {
		super();
		this.userMapper = userMapper;
		this.hashService = hashService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationServiceException{
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		User user = this.userMapper.getUserByName(username);
		if(user != null) {
			String encodedSalt = user.getSalt();
			String hashedPassword = hashService.getHashedValue(password, encodedSalt);
			
			if(user.getPassword().equals(hashedPassword)){
				return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
