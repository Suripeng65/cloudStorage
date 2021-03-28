package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class NoteService {
	private NoteMapper noteMapper;
	private UserMapper userMapper;
	private UserService userService;
	public NoteService(NoteMapper noteMapper, UserService userService, UserMapper userMapper) {
		super();
		this.noteMapper = noteMapper;
		this.userMapper = userMapper;
		this.userService = userService;
	}
	
	public void deleteNote(int noteId) {
		this.noteMapper.deleteNote(noteId);
	}
	
	public void updateNote(Note note, String username) {
		note.setUserId(userService.getUserByName(username).getuserId());
		this.noteMapper.updateNote(note);
//		this.noteMapper.updateNote(noteId, title, description);
	}
	
	public List<Note> getNoteListByUser(int userId) {
		return this.noteMapper.getNoteListForUser(userId);
	}
	
	public void addNote(Note note, String userName) {
        note.setUserId(userService.getUserByName(userName).getuserId());
        noteMapper.insert(note);
	}
	
	public Note getNoteById(int noteId) {
		return this.noteMapper.getNoteById(noteId);
	}
	
	public Note getNote(String name) {
		return this.noteMapper.getNote(name);
	}
}
