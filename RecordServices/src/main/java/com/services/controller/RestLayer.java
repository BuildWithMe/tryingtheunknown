package com.services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.services.exception.ServiceException;
import com.services.exception.UserManagerException;
import com.services.manager.UserManager;
import com.services.model.User;

/*
 * This class acts as the central layer for all rest exposed methods
 * 
 * @author Shahbaz.Alam
 */
@RestController
public class RestLayer {
	
	@Autowired
	private UserManager userManager;
	
	/*
	 * This is a Get Service which takes the username and password and pass it 
	 * to the validation method
	 * 
	 * @return User.Full Name, for valid login or Null for Invalid Login
	 * 
	 */	
	@RequestMapping(value = "/login/userid/{userid}/password/{password}",
			method = RequestMethod.GET)
	public ResponseEntity<Object> validateUser(
			@PathVariable String userid, @PathVariable String password) throws ServiceException {
		User user = null;
		try{
			user = userManager.validateUser(userid, password);
		}catch(UserManagerException ex){
			System.out.println("Exception Caught in UserManager.ValidateUser" +ex);
			return new ResponseEntity<Object>("Exception Caught in Validating User", HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		if(user == null){
			return new ResponseEntity<Object>("Invalid User", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}
	
	/*
	 * This is a post service which takes the User object from the
	 * Request Body. It passes the User Object to the manager for adding it to
	 * the User Table
	 */
	@RequestMapping(value="/addUser", method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody User user){
		try{
			userManager.addUser(user);
		}catch(UserManagerException ex){
			System.out.println("Exception Caught in UserManager.AddUser"+ex);
			return new ResponseEntity<String>("Exception caught in Adding the User", HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return new ResponseEntity<String>("Successfully Added", HttpStatus.OK);
	}
	
	/*
	 * This is a post service which takes the User object from the
	 * Request Body. It passes the User Object to the manager for removing it from
	 * the User Table
	 */
	@RequestMapping(value="/removeUser", method = RequestMethod.POST)
	public ResponseEntity<String> removeUser(@RequestBody User user){
		try{
			userManager.removeUser(user);
		}catch(UserManagerException ex){
			System.out.println("Exception Caught in UserManager.RemoveUser"+ex);
			return new ResponseEntity<String>("Exception caught in Removing the User", HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return new ResponseEntity<String>("Successfully Removed", HttpStatus.OK);
	}
		

}
