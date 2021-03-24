package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

public class CredentialController {
	private UserService userService;
	private CredentialService credentialService;
	public CredentialController(UserService userService, CredentialService credentialService) {
		super();
		this.userService = userService;
		this.credentialService = credentialService;
	}
	
	private int getUserId(Authentication authentication) {
		return this.userService.getUserByName(authentication.getName()).getuserId();
	}
	
	@GetMapping("/delete/{credentialId}")
	public String deleteCredential(@PathVariable("credentialId") int credentialId,
			@ModelAttribute("credentialForm") Credential credential,
			Authentication authentication,
			Model model
			) {
		
		if(credentialId > 0) {
			this.credentialService.deleteCredential(credentialId);
			model.addAttribute("success", true);
			return "credential";
		}else {
			model.addAttribute("hasError", "Unable to delete credential");
		}
		return "result";
	}
	
	@PostMapping("/credential")
	public String addCredential(@ModelAttribute("credential") Credential credential,
			Authentication authendication,
			Model model
			) {
		int userId = getUserId(authendication);
		this.credentialService.addCredential(credential.getUrl(), credential.getUsername(), credential.getKey(), credential.getPassword());
		model.addAttribute("success", true);
		return "result";
	}
}
