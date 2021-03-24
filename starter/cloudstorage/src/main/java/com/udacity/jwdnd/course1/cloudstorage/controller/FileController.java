package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	private String getUserName( Authentication authentication) {
		return authentication.getName();
	}
	@GetMapping("/delete/{fileId}")
	public String deleteFile(@PathVariable("fileId") int fileId, Model model) {
		if(fileId > 0) {
			this.fileService.deleteFileById(fileId);
			model.addAttribute("success", true);
			return "file";
		}else {
			model.addAttribute("hasError", "Delete File Failed!");
		}
		return "result";
	}
	
	@PostMapping("/upload-file")
	public String uploadFile( @RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) {
		String uploadError = null;
		int userid = getUserId(authentication);
		String username = getUserName(authentication);
		String fileName = fileUpload.getOriginalFilename();
		if(fileUpload.isEmpty()) {
			uploadError = "Empty file!";
		}else if(this.fileService.getFile(fileUpload.getName()) !=  null) {
			uploadError = "File already exists!";
		}else {
			try {
				this.fileService.addFile(fileUpload, username);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		if(uploadError == null) {
			model.addAttribute("success", true);
		}else {
			model.addAttribute("hasError", uploadError);
		}
		return "result";
	}
	
}
