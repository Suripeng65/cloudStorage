package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Mapper
public interface CredentialMapper {
	
   @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
   Credential getCredential(int credentialId);
   
   @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
           "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
   @Options(useGeneratedKeys = true, keyProperty = "credentialId")
   void insert(Credential credential);
   
   @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
   void deleteCredential(int credentialId);
   
   @Update("UPDATE CREDENTIALS SET url = #{url}, key=#{key}, password = #{password}, username = #{username} WHERE credentialid = #{credentialId}")
   void updateCredentials(Credential credential);
   
   @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
   List<Credential> getCredentialsByUser(int userid);
}
