package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
	private UserService userService;
	private CredentialService credentialService;
	public CredentialController(UserService userService, CredentialService credentialService) {
		super();
		this.userService = userService;
		this.credentialService = credentialService;
	}
	
	
	@GetMapping("/delete/{credentialId}")
	public String deleteCredential(
			@PathVariable int credentialId, 
			@ModelAttribute Credential credential,
			Authentication authentication,
			RedirectAttributes redirectAttributes
			) {
		try {
			credentialService.deleteCredential(credentialId);		
			redirectAttributes.addFlashAttribute("success", true);
			redirectAttributes.addFlashAttribute("successMessage", "Credential Deleted");
		}catch (Exception e) {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error!" + e.getMessage());
        }
		return "redirect:/home";
	}


    @PutMapping()
    public String updateCredentials(@ModelAttribute Credential credential) {
        credentialService.updateCredential(credential);
        return "redirect:/home";
    }
    
	@PostMapping()
	public String addCredential(Credential credential,
			Authentication authendication,
			Model model
			) {
		this.credentialService.addCredential(credential, authendication.getName());
		return "redirect:/result?success";
	}
}
