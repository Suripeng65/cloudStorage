package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
//@RequestMapping("/home")
public class HomeController {
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private CredentialService credentialService;
	@Autowired
	private EncryptionService encryptionService;

	
	public HomeController(UserService userService, FileService fileService, NoteService noteService,
			CredentialService credentialService, EncryptionService encryptionService) {
		super();
		this.userService = userService;
		this.fileService = fileService;
		this.noteService = noteService;
		this.credentialService = credentialService;
		this.encryptionService = encryptionService;
	}
	private int getUserId(Authentication authentication) {
		String username = authentication.getName();
		return this.userService.getUserByName(username).getuserId();
	}
	@GetMapping("/home")
	public String getHomePage(
			@ModelAttribute("note") Note note, 
			@ModelAttribute("credential") Credential credential, 
			Authentication authentication,
			Model model) {
		int userId = getUserId(authentication);
		model.addAttribute("fileList", this.fileService.getFileListing(userId));
		model.addAttribute("noteList", this.noteService.getNoteListByUser(userId));
		model.addAttribute("credentialList", this.credentialService.getCredentialListByUser(authentication.getName()));
		model.addAttribute("encryption", encryptionService);
		
		return "home";
	}

    @GetMapping("/result")
    public String showResult() {
        return "result";
    }
}
