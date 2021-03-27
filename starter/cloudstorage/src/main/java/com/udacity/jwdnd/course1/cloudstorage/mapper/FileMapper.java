package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
@Mapper
public interface FileMapper {
	@Select("SELECT * FROM FILES WHERE filename = #{fileName}")
	File getFile(String fileName);
	
	@Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
	File getFileById(int fileId);
	
    @Select("SELECT filename FROM FILES WHERE userid = #{userId}")
    List<File> getFileListings(Integer userId);
    
    @Select("SELECT filename FROM FILES WHERE username = #{userName}")
    List<File> getFileListbyUserName(String userName);
	
    @Insert("INSERT INTO FILES (filename, filesize, contenttype, filedata, userid) VALUES (#{file.fileName}, #{file.fileSize}, #{file.contentType}, #{file.fileData}, #{file.userId})")
    int insert(File file, int userid);

	
	@Delete("DELETE FROM FILES WHERE filename = #{fileName}")
	void deleteFile(String fileName);
	
	@Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
	void deleteFileById(int fileId);
}
