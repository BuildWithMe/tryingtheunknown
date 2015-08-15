package com.services.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.services.common.JdbcQueryConstant;
import com.services.exception.DaoException;

/*
 * This is the Dao layer for all the Purchaser related Database Interactions
 * 
 * @author Shahbaz.Alam
 */
public class PurchaserDao {
	
	private DataSource datasource;
	
	/**
	 * This method adds the Purchaser in the Purchaser Table
	 * 
	 * @return The number of rows updated
	 */
	public int addPurchaser(String name) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);		
		List<Object> paramObjs = new ArrayList<Object>();
		paramObjs.add(name);
				
		int result = 0;		
		try{
			result = template.update(JdbcQueryConstant.Query_Add_Purchaser, paramObjs.toArray());
		}catch(Exception ex){
			throw new DaoException("Exception in PurchaserDao.AddPurchaser", ex);
		}
		return result;
		
	}
	
	/**
	 * This method deletes one Purchaser detail from the Purchaser Table
	 */
	public void removePurchaser(String name) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Object> paramObjs = new ArrayList<Object>();
		paramObjs.add(name);
		
		try{
			template.update(JdbcQueryConstant.Remove_Purchaser, paramObjs.toArray());
		}catch(Exception ex){
			throw new DaoException("Exception in PurchaserDao.RemovePurchaser", ex);
		}
		
	}
	
	/**
	 * This method retrieves all the Purchasers from the Purchaser Table
	 * 
	 * @returns List<String>
	 */
	public List<String> getAllPurchaser() throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Map<String, Object>> resultList = null;
		List<String> listPurchasers = null;		
		try{
			resultList = template.queryForList(JdbcQueryConstant.Get_All_Purchaser);
		}catch(Exception ex){
			throw new DaoException("Exception in PurchaserDao.GetAllPurchaser", ex);
		}
		if(resultList != null){
			listPurchasers = getPurchaser(resultList);
		}
		
		return listPurchasers;
		
	}

	private List<String> getPurchaser(List<Map<String, Object>> resultList) {
		List<String> purchasers = new ArrayList<String>();
		for(Map<String, Object> map : resultList){
			String purchaser = (String) map.get("Purchaser_Name");
			purchasers.add(purchaser.toUpperCase());
		}
		return purchasers;
	}

}
