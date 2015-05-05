package com.services.manager;

import java.util.List;

import com.services.dao.PurchaserDao;
import com.services.exception.PurchaserManagerException;


/*
 * This is a central point for all Purchaser related Operations
 * 
 * @author Shahbaz.Alam
 */
public class PurchaserManager {
	
	private PurchaserDao purchaserDao;
	
	/*
	 *The method will be used for adding the Purchaser. It will pass
	 *the purchaser name to the dao layer to update the Purchaser Table  
	 */	
	public void addPurchaser(String name) throws PurchaserManagerException{
		purchaserDao.addPurchaser(name);
		
	}
	
	/*
	 * The method will be used for removing a purchaser. It will pass
	 * the purchaser name to the dao layer to delete it from the Purchaser Table
	 */
	public void removePurchaser(String name) throws PurchaserManagerException{
		purchaserDao.removePurchaser(name);
	}
	
	/*
	 * The method will be used for fetching all the purchasers
	 */
	public List<String> getAllPurchaser() throws PurchaserManagerException{
		List<String> listPurchaser = purchaserDao.getAllPurchaser();
		return listPurchaser;
	}

}
