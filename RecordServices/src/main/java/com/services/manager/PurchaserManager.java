package com.services.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.services.dao.PurchaserDao;
import com.services.exception.DaoException;
import com.services.exception.PurchaserManagerException;


/**
 * This is a central point for all Purchaser related Operations
 * 
 * @author Shahbaz.Alam
 */
@Component
public class PurchaserManager {
	
	private PurchaserDao purchaserDao;
	
	/**
	 *The method will be used for adding the Purchaser. It will pass
	 *the purchaser name to the dao layer to update the Purchaser Table  
	 */	
	public int addPurchaser(String name) throws PurchaserManagerException{
		int result = 0;
		try{
			result = purchaserDao.addPurchaser(name);
		}catch(DaoException ex){
			throw new PurchaserManagerException("DaoException caught in AddPurchaser", ex);
		}
		return result;		
	}
	
	/**
	 * The method will be used for removing a purchaser. It will pass
	 * the purchaser name to the dao layer to delete it from the Purchaser Table
	 */
	public void removePurchaser(String name) throws PurchaserManagerException{
		try{
			purchaserDao.removePurchaser(name);
		}catch(DaoException ex){
			throw new PurchaserManagerException("DaoException caught in RemovePurchaser", ex);
		}		
	}
	
	/**
	 * The method will be used for fetching all the purchasers
	 */
	public List<String> getAllPurchaser() throws PurchaserManagerException{
		List<String> listPurchaser = null;
		try{
			listPurchaser = purchaserDao.getAllPurchaser();
		}catch(DaoException ex){
			throw new PurchaserManagerException("DaoException caught in GetAllPurchaser", ex);
		}
		return listPurchaser;
	}

}
