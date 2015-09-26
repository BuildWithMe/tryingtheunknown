package com.services.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.services.dao.CropDao;
import com.services.exception.CropManagerException;
import com.services.exception.DaoException;


/**
 * This is a central point for all Crop related Operations
 * 
 * @author Shahbaz.Alam
 */
@Component
public class CropManager {
	@Autowired
	private CropDao cropDao;
	
	/**
	 *The method will be used for adding the Crop. It will pass
	 *the crop name to the dao layer to update the Crop Table  
	 */	
	public int addCrop(String name) throws CropManagerException{
		int result = 0;
		try{
			result = cropDao.addCrop(name);
		}catch(DaoException ex){
			throw new CropManagerException("DaoException caught in addCrop", ex);
		}
		return result;		
	}
	
	/**
	 * The method will be used for removing a crop. It will pass
	 * the purchaser name to the dao layer to delete it from the Crop Table
	 */
	public void removeCrop(String name) throws CropManagerException{
		try{
			cropDao.removeCrop(name);
		}catch(DaoException ex){
			throw new CropManagerException("DaoException caught in removeCrop", ex);
		}		
	}
	
	/**
	 * The method will be used for fetching all the crop
	 */
	public List<String> getAllCrop() throws CropManagerException{
		List<String> listCrop = null;
		try{
			listCrop = cropDao.getAllCrop();
		}catch(DaoException ex){
			throw new CropManagerException("DaoException caught in getAllCrop", ex);
		}
		return listCrop;
	}

}
