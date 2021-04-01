package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class FileService {
	private FileMapper fileMapper;
	private UserMapper userMapper;
	
	public FileService(FileMapper fileMapper, UserMapper userMapper) {
		super();
		this.fileMapper = fileMapper;
		this.userMapper = userMapper;
	}

	public void deleteFile(File file) {
		fileMapper.deleteFile(file.getFileName());
	}
	public void deleteFileById(int fileId) {
		fileMapper.deleteFileById(fileId);
	}
	public int addFile(MultipartFile multipartFile, Integer userid) throws IOException {
//		InputStream fis = multipartFile.getInputStream();
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        int nRead;
//        byte[] data = new byte[1024];
//        while ((nRead = fis.read(data, 0, data.length)) != -1) {
//            buffer.write(data, 0, nRead);
//        }
//        buffer.flush();
//        byte[] fileData = buffer.toByteArray();

        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        File file = new File(null, fileName, contentType, fileSize, userid, multipartFile.getBytes());
		return fileMapper.insert(file);
	}
	
	public File getFile(String fileName, String username) {
		User user = this.userMapper.getUserByName(username);
		return fileMapper.getFileByUser(fileName, user.getuserId());
	}
	public List<File> getFileListing(int userId) {
		return fileMapper.getFileListings(userId);
	}

	public File getFileById(Integer id) {
		return this.fileMapper.getFileById(id);
	}
}
