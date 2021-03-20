package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

public class UserService {
//	private User user;
	private UserMapper userMapper;
	private HashService hashService;
	public UserService(UserMapper userMapper, HashService hashService) {
		super();
//		this.user = user;
		this.hashService = hashService;
		this.userMapper = userMapper;
	}
	
	public int addUser(User user) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
		return userMapper.insert(new User(user.getUsername(), user.getSalt(), hashedPassword, user.getfirstName(), user.getlastName()));
	}
	public User getUserByName(String username) {
		return userMapper.getUserByName(username);
	}
	public User getUserById(String userId) {
		return userMapper.getUserByName(userId);
	}
	public boolean isUserExist(String username) {
		return userMapper.getUserByName(username) == null;
	}
}
