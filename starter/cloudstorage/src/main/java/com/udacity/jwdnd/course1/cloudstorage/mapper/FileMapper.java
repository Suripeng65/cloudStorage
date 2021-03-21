package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
@Mapper
public interface FileMapper {
	@Select("SELECT * FROM FILES WHERE filename = #{fileName}")
	File getFile(String fileName);
	
	@Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
	File getFile(int fileId);
	
    @Select("SELECT filename FROM FILES WHERE userid = #{userId}")
    String[] getFileListings(Integer userId);
	
	@Insert("INSERT INTO FILES ((filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
	@Options(useGeneratedKeys = true, keyProperty="fileId")
	int insert(File file);
	
	@Delete("DELETE FROM FILES WHERE filename = #{fileName}")
	void deleteFile(String fileName);
}
