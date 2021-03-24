package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Service
public class CredentialService {
	private CredentialMapper credentialMapper;
	private UserMapper userMapper;
	
	public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper) {
		super();
		this.credentialMapper = credentialMapper;
		this.userMapper = userMapper;
	}

	public Credential[] getCredentialListByUser(int userId) {
		return this.credentialMapper.getCredentialsByUser(userId);
	}
	
	public Credential getCredential(int credentialId) {
		return this.credentialMapper.getCredential(credentialId);
	}
	
	public void addCredential(String url, String username, String key, String password) {
		int userId = this.userMapper.getUserByName(username).getuserId();
		Credential credential = new Credential(0, url, username, key, password, userId);
		this.credentialMapper.insert(credential);
	}
	public void deleteCredential(int credentialId) {
		this.credentialMapper.deleteCredential(credentialId);
	}
	
	public void updateCredential(int credentialId, String newUserName, String url, String key, String password) {
		this.credentialMapper.updateCredential(credentialId, newUserName, url, key, password);
	}
}
