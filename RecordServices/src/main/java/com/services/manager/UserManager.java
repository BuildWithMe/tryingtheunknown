package com.services.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.services.dao.UserDao;
import com.services.exception.DaoException;
import com.services.exception.UserManagerException;
import com.services.model.User;

/**
 * This is a central point for all User activities
 * 
 * @author Shahbaz.Alam
 */
@Component
public class UserManager {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * This method validates the username and password 
	 * against the User Table. If the username and password
	 * are present in the table, it will fetch the corresponding 
	 * User Full Name and return, else will return null indicating 
	 * that there is no such user
	 */
	public User validateUser(String userName, String password) throws UserManagerException{
		User user = null;
		try{
			user = userDao.getUser(userName, password);
		}catch(DaoException ex){
			throw new UserManagerException("DaoException Caught in ValidateUser", ex);
		}		
		return user;		
	}
	
	
	/**
	 * This method adds a User into the User Table. It calls the UserDao
	 * for the operation. If the user is updated the userAdded flag is set to be true
	 * 
	 * @return boolean
	 */
	public int addUser(User user) throws UserManagerException{
		int result = 0;
		try{
			result = userDao.addUser(user);
		}catch(DaoException ex){
			throw new UserManagerException("DaoException Caught in AddUser", ex);
		}
		return result;
	}
	
	/**
	 * This method removes a user from the User Table. It call the UserDao
	 * for the operation
	 */
	public void removeUser(User user) throws UserManagerException{
		try{
			userDao.removeUser(user);
		}catch(DaoException ex){
			throw new UserManagerException("DaoException Caught in RemoveUser", ex);
		}
	}
	
	/**
	 * This method retrieves all the users
	 * 
	 * @return List<User>
	 */
	public List<User> getAllUser() throws UserManagerException{
		List<User> listUsers = null;
		try{
			listUsers = userDao.getAllUser();
		}catch(DaoException ex){
			throw new UserManagerException("DaoException Caught in GetAllUser", ex);
		}		
		return listUsers;
	}


	/**
	 * Checks whether the given user id is already present or not
	 * @param userid
	 * @return
	 * @throws UserManagerException 
	 */
	public boolean checkUser(String userid) throws UserManagerException {
		try{
			return userDao.checkUser(userid);
		}catch(DaoException ex){
			throw new UserManagerException(ex);
		}
	}
	

}
