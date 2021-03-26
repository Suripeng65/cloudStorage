package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {
	private Integer userId;
	private String username;
	private String salt;
	private String password;
	private String firstName;
	private String lastName;
	
	public User(Integer userId, String username, String salt, String password, String firstName, String lastName) {
		super();
		this.userId = userId;
		this.username = username;
		this.salt = salt;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public int getuserId() {
		return userId;
	}
	public void setuserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getfirstName() {
		return firstName;
	}
	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getlastName() {
		return lastName;
	}
	public void setlastName(String lastName) {
		this.lastName = lastName;
	}


}
