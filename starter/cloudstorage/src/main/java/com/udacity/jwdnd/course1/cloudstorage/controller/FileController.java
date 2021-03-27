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

import com.udacity.jwdnd.course1.cloudstorage.model.User;
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
	public String uploadFile( MultipartFile fileUpload, Authentication authentication, Model model) {
		String uploadError = null;
		User user = this.userService.getUserByName(authentication.getName());
		String fileName = fileUpload.getOriginalFilename();
		
		if(fileUpload.isEmpty()) {
			uploadError = "Empty file!";
			return "redirect:/result?error";
		}else if(this.fileService.getFile(fileName) !=  null) {
			uploadError = "File already exists!";
		}else {
			try {
				this.fileService.addFile(fileUpload, user.getuserId());
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		return "redirect:/result?success";
	}
	
}
