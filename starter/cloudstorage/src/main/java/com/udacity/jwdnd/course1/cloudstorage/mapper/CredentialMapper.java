package com.udacity.jwdnd.course1.cloudstorage.mapper;

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
           "VALUES(#{url}, #{userName}, #{key}, #{password}, #{userid})")
   @Options(useGeneratedKeys = true, keyProperty = "credentialid")
   void insert(Credential credential);
   
   @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
   void deleteCredential(int credentialid);
   
   @Update("UPDATE CREDENTIALS SET url = #{url}, key=#{key}, password = #{password}, username = #{newuserName} WHERE credentialid = #{credentialId}")
   void updateCredential(int credentialId, String newUserName, String url, String key, String password);
   
   @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
   Credential[] getCredentialsByUser(int userid);
}
