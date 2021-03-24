package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/files")
public class FileController {
	private FileService fileService;
	private UserService userService;
	
	public FileController(FileService fileService, UserService userService) {
		this.fileService = fileService;
		this.userService = userService;
	}
	
	private int getUserId( Authentication authentication ) {
		String username = authentication.getName();
		return this.userService.getUserByName(username).getuserId();
	}
	
	@GetMapping("/delete/{fileId}")
	public String deleteFile(@PathVariable("fileId") int fileId, Model model) {
		if(fileId > 0) {
			this.fileService.deleteFileById(fileId);
		}
		return "file";
	}
	
}
