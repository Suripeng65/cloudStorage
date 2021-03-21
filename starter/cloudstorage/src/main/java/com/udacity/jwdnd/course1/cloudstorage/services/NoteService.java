package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

public class NoteService {
	private NoteMapper noteMapper;
	private UserMapper userMapper;
	public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
		super();
		this.noteMapper = noteMapper;
		this.userMapper = userMapper;
	}
	
	public void deleteNote(int noteId) {
		this.noteMapper.deleteNote(noteId);
	}
	
	public void updateNote(int noteId, String title, String description) {
		this.noteMapper.updateNote(noteId, title, description);
	}
	
	public Note[] getNoteListByUser(int userId) {
		return this.noteMapper.getNoteListForUser(userId);
	}
	
	public int addNote(String title, String description, String userName) {
        int userId = userMapper.getUserByName(userName).getuserId();
        Note note = new Note(0, title, description, userId);
		return this.noteMapper.insert(note);
	}
	
	public Note getNoteById(int noteId) {
		return this.noteMapper.getNoteById(noteId);
	}
	
	public Note getNote(String name) {
		return this.noteMapper.getNote(name);
	}
}
