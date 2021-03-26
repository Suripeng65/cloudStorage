package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import java.util.List;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
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
		return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getfirstName(), user.getlastName()));
	}
	public User getUserByName(String username) {
		return userMapper.getUserByName(username);
	}
	public User getUserById(Integer userId) {
		return userMapper.getUserById(userId);
	}
	public boolean isUsernameAvailable(String username) {
		System.out.println("isUsernameAvailable "+userMapper.getUserByName(username));
		return userMapper.getUserByName(username) == null;
	}
	
	public List<User> getAllUsers(){
		return userMapper.getAllUsers();
	}

}