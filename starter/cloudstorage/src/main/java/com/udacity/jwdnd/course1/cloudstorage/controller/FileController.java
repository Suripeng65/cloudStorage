package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
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
	public String deleteFile(@PathVariable int fileId, @ModelAttribute File file, Authentication authentication, RedirectAttributes redirectAttributes) {
		try {
			fileService.deleteFileById(fileId);
			redirectAttributes.addFlashAttribute("success", true);
			redirectAttributes.addFlashAttribute("successMessage", "file Deleted");
		}catch (Exception e) {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error!" + e.getMessage());
        }
		
		return "redirect:/home";
	}
	
	@PostMapping("/upload-file")
	public String uploadFile( MultipartFile fileUpload, Authentication authentication, Model model,
			RedirectAttributes redirectAttributes) {		
		if((fileUpload.getSize() > 5242880)) {
			model.addAttribute("error", "File size exceed maximum");
			throw new MaxUploadSizeExceededException(fileUpload.getSize());
//			return "redirect:/result?error";
		}
		String uploadError = null;
		User user = this.userService.getUserByName(authentication.getName());
		String fileName = fileUpload.getOriginalFilename();
		if(fileUpload.isEmpty()) {
			uploadError = "Empty file!";
			return "redirect:/result?error";
		}else if(this.fileService.getFile(fileName) !=  null) {
			uploadError = "File already exists!";
			redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", uploadError);
			return "redirect:/home";
		}else {
			try {
				this.fileService.addFile(fileUpload, user.getuserId());
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		return "redirect:/result?success";
	}
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") Integer fileId) {
        File file = fileService.getFileById(fileId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(httpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + file.getFileName());
        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);

    }
	
}
