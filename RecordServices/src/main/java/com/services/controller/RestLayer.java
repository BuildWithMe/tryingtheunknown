package com.services.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.services.exception.PurchaserManagerException;
import com.services.exception.RecordManagerException;
import com.services.exception.ServiceException;
import com.services.exception.UserManagerException;
import com.services.manager.PurchaserManager;
import com.services.manager.RecordManager;
import com.services.manager.UserManager;
import com.services.model.Record;
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
	
	@Autowired
	private PurchaserManager purchaserManager;
	
	@Autowired
	private RecordManager recordManager;
	
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
		int result = 0;
		try{
			result = userManager.addUser(user);
			if(result == 0){
				return new ResponseEntity<String>("Failed to Add the User", HttpStatus.INTERNAL_SERVER_ERROR);
			}
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
	
	/*
	 * This is a Get Service which will return all the list of Users
	 * 
	 * @return List<User>
	 */
	@RequestMapping(value="/getAllUser", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser(){
		List<User> listUsers = null;
		try{
			listUsers = userManager.getAllUser();
		}catch(UserManagerException ex){
			System.out.println("Exception Caught in UserManager.GetAllUser"+ex);
			return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<User>>(listUsers,HttpStatus.OK);
	}
	
	/*
	 * This is a Get Service which will return all the list of Purchaser Name
	 */
	@RequestMapping(value = "/getAllPurchaser" , method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAllPurchaser(){
		List<String> listPurchasers = null;
		try{
			listPurchasers = purchaserManager.getAllPurchaser();
		}catch(PurchaserManagerException ex){
			System.out.println("Exception Caught in PurchaserManager.GetAllPurchaser"+ex);
			return new ResponseEntity<List<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<String>>(listPurchasers,HttpStatus.OK);		
	}
	
	/*
	 * This is a post service which takes the Purchaser Name from the
	 * Url path. It passes the purchaser to the manager for removing it from
	 * the Purchaser Table
	 */
	@RequestMapping(value="/removePurchaser/{purchaserName}", method = RequestMethod.POST)
	public ResponseEntity<?> removePurchaser(@PathVariable String purchaserName){
		try{
			purchaserManager.removePurchaser(purchaserName);
		}catch(PurchaserManagerException ex){
			System.out.println("Exception Caught in PurchaserManager.RemovePurchaser"+ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/*
	 * This is a post service which takes the Purchaser Name from the
	 * Url path. It passes the purchaser to the manager for adding it to
	 * the Purchaser Table
	 */
	@RequestMapping(value="/addPurchaser/{purchaserName}", method = RequestMethod.POST)
	public ResponseEntity<?> addPurchaser(@PathVariable String purchaserName){
		try{
			purchaserManager.addPurchaser(purchaserName);
		}catch(PurchaserManagerException ex){
			System.out.println("Exception Caught in PurchaserManager.AddPurchaser"+ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/*
	 * This is a Post Service to add a Record. It takes the Record Object from the
	 * Request Body. It also returns the List of Records entered for the present
	 * Date
	 */
	public ResponseEntity<List<Record>> saveRecord(@RequestBody Record record){
		List<Record> listRecord = null;
		if(record == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try{
			listRecord = recordManager.saveAndReturnRecords(record);
		}catch(RecordManagerException ex){
			System.out.println("Exception Caught in RecordManager.SaveAndReturnRecords"+ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(listRecord,HttpStatus.OK);
	}
	
	
	
	
		

}
