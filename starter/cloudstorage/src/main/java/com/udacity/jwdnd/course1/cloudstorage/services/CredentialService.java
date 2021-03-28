package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Service
public class CredentialService {
	private CredentialMapper credentialMapper;
	private EncryptionService encryptionService;
	private UserService userService;
	
	public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
		super();
		this.credentialMapper = credentialMapper;
		this.userService = userService;
		this.encryptionService = encryptionService;
	}

	public List<Credential> getCredentialListByUser(String username) {
		return this.credentialMapper.getCredentialsByUser(userService.getUserByName(username).getuserId());
	}
	
	
	public void addCredential(Credential credential, String username) {
	  String key = encryptionService.generateKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);
		int userId = this.userService.getUserByName(username).getuserId();
		credential.setUserId(userId);
        credential.setPassword(encryptedPassword);
        credential.setKey(key);
        credentialMapper.insert(credential);
	}
	public void deleteCredential(int credentialId) {
		this.credentialMapper.deleteCredential(credentialId);
	}
	
	public void updateCredential(Credential credential) {
		String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        credentialMapper.updateCredentials(credential);
	}
}
