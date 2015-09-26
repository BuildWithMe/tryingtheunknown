package com.services.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.services.exception.CropManagerException;
import com.services.exception.PurchaserManagerException;
import com.services.exception.RecordManagerException;
import com.services.exception.ServiceException;
import com.services.exception.UserManagerException;
import com.services.manager.CropManager;
import com.services.manager.PurchaserManager;
import com.services.manager.RecordManager;
import com.services.manager.UserManager;
import com.services.model.GraphResponse;
import com.services.model.Record;
import com.services.model.SearchPredicate;
import com.services.model.User;

/**
 * This class acts as the central layer for all rest exposed methods
 * 
 * @author Shahbaz.Alam
 */
@RestController
public class RestLayer {
	private static final Logger logger = LoggerFactory.getLogger(RestLayer.class);
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private PurchaserManager purchaserManager;
	
	@Autowired
	private RecordManager recordManager;
	
	@Autowired
	private CropManager cropManager;
	
	/**
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
			logger.error("Exception caught in validate user>>>>>"+ex.getMessage());
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		if(user == null){
			logger.error("Invalid authentication>>>>>");
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}
	
	/**
	 * This is a post service which takes the User object from the
	 * Request Body. It passes the User Object to the manager for adding it to
	 * the User Table
	 */
	@RequestMapping(value="/addUser", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody User user){
		int result = 0;
		try{
			result = userManager.addUser(user);
			if(result == 0){
				logger.error("Failed to add the user>>>>>"+user.getFullName());
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(UserManagerException ex){
			logger.error("Exception caught in add User>>>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * This is a post service which takes the User object from the
	 * Request Body. It passes the User Object to the manager for removing it from
	 * the User Table
	 */
	@RequestMapping(value="/removeUser", method = RequestMethod.POST)
	public ResponseEntity<String> removeUser(@RequestBody User user){
		try{
			userManager.removeUser(user);
		}catch(UserManagerException ex){
			logger.error("Exception caught in remove user>>>"+ex.getMessage());
			return new ResponseEntity<String>("Exception caught in Removing the User", HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return new ResponseEntity<String>("Successfully Removed", HttpStatus.OK);
	}
	
	/**
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
			logger.error("Exception caught in get all user>>>>"+ex.getMessage());
			return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<User>>(listUsers,HttpStatus.OK);
	}
	
	/**
	 * This is a Get Service which will return all the list of Purchaser Name
	 */
	@RequestMapping(value = "/getAllPurchaser" , method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAllPurchaser(){
		List<String> listPurchasers = null;
		try{
			listPurchasers = purchaserManager.getAllPurchaser();
		}catch(PurchaserManagerException ex){
			logger.error("Exception caught in get all purchaser>>>"+ex.getMessage());
			return new ResponseEntity<List<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<String>>(listPurchasers,HttpStatus.OK);		
	}
	
	/**
	 * This is a post service which takes the Purchaser Name from the
	 * Url path. It passes the purchaser to the manager for removing it from
	 * the Purchaser Table
	 */
	@RequestMapping(value="/removePurchaser/{purchaserName}", method = RequestMethod.POST)
	public ResponseEntity<?> removePurchaser(@PathVariable String purchaserName){
		try{
			purchaserManager.removePurchaser(purchaserName);
		}catch(PurchaserManagerException ex){
			logger.error("Exception caught in removePurchaser>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * This is a post service which takes the Purchaser Name from the
	 * Url path. It passes the purchaser to the manager for adding it to
	 * the Purchaser Table
	 */
	@RequestMapping(value="/addPurchaser/{purchaserName}", method = RequestMethod.POST)
	public ResponseEntity<?> addPurchaser(@PathVariable String purchaserName){
		try{
			purchaserManager.addPurchaser(purchaserName);
		}catch(PurchaserManagerException ex){
			logger.error("Exception caught in addPurchaser>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * This is a Post Service to add a Record. It takes the Record Object from the
	 * Request Body. It also returns the List of Records entered for the present
	 * Date
	 */
	@RequestMapping(value="/saveRecord", method = RequestMethod.POST)
	public ResponseEntity<List<Record>> saveRecord(@RequestBody Record record){
		List<Record> listRecord = null;
		if(record == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try{
			listRecord = recordManager.saveAndReturnRecords(record);
		}catch(RecordManagerException ex){
			logger.error("Exception caught in saveRecord>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(listRecord,HttpStatus.OK);
	}
	
	/**
	 * This is a Delete Service to delete a record. It takes the RecordId from the
	 * URL. It returns a success status on successful deletion
	 */
	@RequestMapping(value="/deleteRecord/recordId/{recordId}", method = RequestMethod.POST)
	public ResponseEntity<?> deleteRecord(@PathVariable BigDecimal recordId){
		try{
			recordManager.deleteRecord(recordId);
		}catch(RecordManagerException ex){
			logger.error("Exception caught in deleteRecord>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * This is a post service to update a Record. It takes the Record Object
	 * from the Request Body and passes on to the Manager for updating
	 */
	@RequestMapping(value="/updateRecord", method = RequestMethod.POST)
	public ResponseEntity<?> updateRecord(@RequestBody Record record){
		int result = 0;
		try{
			result = recordManager.updateRecord(record);
			if(result == 0){
				return new ResponseEntity<String>("Failed to Update the Record", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(RecordManagerException ex){
			logger.error("Exception caught in updateRecord>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * This is a get service for returning list of records for the current date
	 */
	@RequestMapping(value = "/getAllRecords", method = RequestMethod.GET)
	public ResponseEntity<List<Record>> getAllRecordsForCurrentDate(){
		List<Record> listRecords = null;
		try{
			listRecords = recordManager.getAllRecordsForCurrentDate();
		}catch(RecordManagerException ex){
			logger.error("Exception caught in getAllRecordsForCurrentDate>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(listRecords,HttpStatus.OK);
		
	}
	
	/**
	 * This is a get service to check whether a userid is already present 
	 */
	@RequestMapping(value = "/checkUser/{userid}", method = RequestMethod.GET)
	public ResponseEntity<?> checkUserId(@PathVariable String userid){
		boolean value = false;
		try{
			value = userManager.checkUser(userid);
		}catch(UserManagerException ex){
			logger.error("Exception caught in checkUserId>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(value){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);		
	}
	
	/**
	 * This is a post service to search records
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<List<Record>> search(@RequestBody SearchPredicate searchPredicate){
		List<Record> listRecord = null;
		try{
			listRecord = recordManager.searchRecords(searchPredicate);
		}catch(RecordManagerException ex){
			logger.error("Exception caught in searchRecords>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(listRecord == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(listRecord,HttpStatus.OK);
	}
	
	/**
	 * This service is for change password of a user
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestBody User user){
		try{
			userManager.changePassword(user);
		}catch(UserManagerException ex){
			logger.error("User Details cannot be validated for user"+user.getUserId()+"Exception"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * This service returns the tax details 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getGraphDetails", method = RequestMethod.GET)
	public ResponseEntity<GraphResponse> getGraphDetails(){
		GraphResponse response = null;
		try{
			response = recordManager.getGraphDetails();
		}catch(RecordManagerException ex){
			logger.error("Exception caught in getGraphDetails>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	/**
	 * The service is used to clear the memory between a certain date range
	 */
	@RequestMapping(value = "/clearMemory/startDate/{startDate}/endDate/{endDate}", method = RequestMethod.DELETE)
	public ResponseEntity<?> clearMemory(@PathVariable String startDate, @PathVariable String endDate){
		try{
			recordManager.clearMemory(startDate,endDate);
		}catch(RecordManagerException ex){
			logger.error("Exception caught in clearMemory>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * This is a Get Service which will return all the list of Crop Name
	 */
	@RequestMapping(value = "/getAllCrop" , method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAllCrop(){
		List<String> listCrops = null;
		try{
			listCrops = cropManager.getAllCrop();
		}catch(CropManagerException ex){
			logger.error("Exception caught in get all crop>>>"+ex.getMessage());
			return new ResponseEntity<List<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<String>>(listCrops,HttpStatus.OK);		
	}
	
	/**
	 * This is a post service which takes the Crop Name from the
	 * Url path. It passes the crop to the manager for removing it from
	 * the Crop Table
	 */
	@RequestMapping(value="/removeCrop/{cropName}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeCrop(@PathVariable String cropName){
		try{
			cropManager.removeCrop(cropName);
		}catch(CropManagerException ex){
			logger.error("Exception caught in removeCrop>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * This is a post service which takes the Crop Name from the
	 * Url path. It passes the crop to the manager for adding it to
	 * the Crop Table
	 */
	@RequestMapping(value="/addCrop/{cropName}", method = RequestMethod.POST)
	public ResponseEntity<?> addCrop(@PathVariable String cropName){
		try{
			cropManager.addCrop(cropName);
		}catch(CropManagerException ex){
			logger.error("Exception caught in addCrop>>>"+ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
