package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
@Mapper
public interface NoteMapper {
	@Select("SELECT * FROM NOTES WHERE notename = #{noteName}")
	Note getNote(String noteName);
	
	@Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
	Note getNoteById(int noteId);
	
	@Select("SELECT * FROM NOTES WHERE userid = #{userId}")
	List<Note> getNoteListForUser(int userId);
	
	@Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES(#{note.noteTitle}, #{note.noteDescription}, #{note.userId})")
	@Options(useGeneratedKeys = true, keyProperty = "noteId")
	int insert(Note note);
	
	@Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
	void deleteNote(int noteId);
	
	@Update("UPDATE NOTES SET notetitle = #{title}, notedescription = #{description} WHERE noteid = #{noteId}")
	void updateNote(int noteId, String title, String description);
}
