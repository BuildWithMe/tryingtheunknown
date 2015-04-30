package com.services.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.services.dao.UserDao;
import com.services.exception.UserManagerException;
import com.services.model.User;

/*
 * This is a central point for all User activities
 * 
 * @author Shahbaz.Alam
 */
public class UserManager {
	
	@Autowired
	private UserDao userDao;
	
	/*
	 * This method validates the username and password 
	 * against the User Table. If the username and password
	 * are present in the table, it will fetch the corresponding 
	 * User Full Name and return, else will return null indicating 
	 * that there is no such user
	 */
	public User validateUser(String userName, String password) throws UserManagerException{
		User user = userDao.getUser(userName, password);
		
		return user;		
	}
	
	
	/*
	 * This method adds a User into the User Table. It calls the UserDao
	 * for the operation
	 */
	public void addUser(User user) throws UserManagerException{
		userDao.addUser(user);
	}
	
	/*
	 * This method removes a user from the User Table. It call the UserDao
	 * for the operation
	 */
	public void removeUser(User user) throws UserManagerException{
		userDao.removeUser(user);
	}
	

}
