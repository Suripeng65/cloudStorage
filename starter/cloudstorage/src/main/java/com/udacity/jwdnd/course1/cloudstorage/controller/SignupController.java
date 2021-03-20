package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {
	private UserService userService;
	public SignupController(UserService userService) {
        this.userService = userService;
	}
	@GetMapping()
	public String signupView() {
		return "signup";
	}
	
	@PostMapping()
	public String signup(User user, Model model) {
		String signupError = null;
//		check if user has already signed up
		if (!userService.isUserExist(user.getUsername())) {
            signupError = "The username already exists.";
        }
// if user doesnt exist in database, sign user up!
        if (signupError == null) {
            int rowsAdded = userService.addUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }
        
		return "signup";
	}

}