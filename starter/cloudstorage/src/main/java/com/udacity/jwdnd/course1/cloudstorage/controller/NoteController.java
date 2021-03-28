package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/notes")
public class NoteController {
	
	private NoteService noteService;
	private UserService userService;
	
	public NoteController(NoteService noteService, UserService userService) {
		super();
		this.noteService = noteService;
		this.userService = userService;
	}

	private int getUserId(Authentication authentication){
		String username = authentication.getName();
		return this.userService.getUserByName(username).getuserId();
	}
	
	@PostMapping("/note")
	public String addNote(@ModelAttribute("note") Note note, Authentication authentication,
			Model model) {
		noteService.addNote(note, authentication.getName());
//		int userid = getUserId(authentication);
//		User user = this.userService.getUserByName(authentication.getName());
//		if(note.getNoteId() != null) {
//			noteService.updateNote(note);
//		}else {
//			String username = authentication.getName();
//			noteService.addNote(note.getNoteTitle(), note.getNoteDescription(), username);
//			model.addAttribute("note", noteService.getNoteListByUser(user.getuserId()));
//		}
//		
//		model.addAttribute("success", true);
		return "redirect:/result?success";
	}
	
	@GetMapping(value = "/delete-note/{noteId}")
	public String deleteNote(
			@PathVariable("noteId") int noteId,
			@ModelAttribute("note") Note note,
			Authentication authentication,
			Model model
			) {
		if(noteId >0) {
			this.noteService.deleteNote(noteId);
			model.addAttribute("success", true);
			return "note";
		}else {
			model.addAttribute("hasError", "Delete Failed!");
			
		}
		return "result";
	}
	
}
