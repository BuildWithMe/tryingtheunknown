package com.services.model;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * This is a User Object
 * It holds the Details of the user
 * 
 * @author Shahbaz.Alam
 */
@XmlRootElement
public class User {
	
	private String fullName;
	
	private String userId;
	
	private String password;
	
	private boolean isAdmin;
	
	private String newPassword;

	
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	

}
