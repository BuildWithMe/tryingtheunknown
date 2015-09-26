package com.services.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.services.common.JdbcQueryConstant;
import com.services.exception.DaoException;

/**
 * This is the Dao layer for all the Crop related Database Interactions
 * 
 * @author Shahbaz.Alam
 */
@Component
public class CropDao {
	@Autowired
	@Qualifier("primaryDs")
	private DataSource datasource;

	/**
	 * This method retrieves all the Purchasers from the Crop Table
	 * 
	 * @returns List<String>
	 */
	public List<String> getAllCrop() throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Map<String, Object>> resultList = null;
		List<String> listPurchasers = null;		
		try{
			resultList = template.queryForList(JdbcQueryConstant.Get_All_Crop);
		}catch(Exception ex){
			throw new DaoException("Exception in PurchaserDao.GetAllCrop", ex);
		}
		if(resultList != null){
			listPurchasers = getCrop(resultList);
		}
		
		return listPurchasers;
	}

	public void removeCrop(String name) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Object> paramObjs = new ArrayList<Object>();
		paramObjs.add(name);
		
		try{
			template.update(JdbcQueryConstant.Remove_Crop, paramObjs.toArray());
		}catch(Exception ex){
			throw new DaoException("Exception in PurchaserDao.RemoveCrop", ex);
		}
	}

	/**
	 * This method adds the Purchaser in the Crop Table
	 * 
	 * @return The number of rows updated
	 */
	public Integer addCrop(String name) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);		
		List<Object> paramObjs = new ArrayList<Object>();
		paramObjs.add(name);
				
		int result = 0;		
		try{
			result = template.update(JdbcQueryConstant.Query_Add_Crop, paramObjs.toArray());
		}catch(Exception ex){
			throw new DaoException("Exception in PurchaserDao.AddCrop", ex);
		}
		return result;
	}
	
	
	private List<String> getCrop(List<Map<String, Object>> resultList) {
		List<String> crops = new ArrayList<String>();
		for(Map<String, Object> map : resultList){
			String crop = (String) map.get("Crop_Name");
			crops.add(crop.toUpperCase());
		}
		return crops;
	}

}
