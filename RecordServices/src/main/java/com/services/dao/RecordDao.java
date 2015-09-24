package com.services.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.services.common.JdbcQueryConstant;
import com.services.common.PaymentMode;
import com.services.exception.DaoException;
import com.services.model.Record;
import com.services.model.SearchPredicate;

@Component
public class RecordDao {
	
	@Autowired
	@Qualifier("primaryDs")
	private DataSource datasource;
	
	/**
	 * The method is used to save a single record in the Record Table
	 * 
	 * @param record
	 * @throws DaoException
	 */
	public int saveRecord(Record record) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Object> paramObjs = new ArrayList<Object>();
		paramObjs.add(record.getDate());
		paramObjs.add(record.getPurchaserName());
		paramObjs.add(record.getVehicleNbr());
		paramObjs.add(record.getCropName());
		paramObjs.add(record.getBagQty());
		paramObjs.add(record.getNetWeight());
		paramObjs.add(record.getActualWeight());
		paramObjs.add(record.getCropRate());
		paramObjs.add(record.getTotalCost());
		paramObjs.add(record.getMarketFee());
		paramObjs.add(record.getSupervisionFee());
		paramObjs.add(record.getTotalTax());
		paramObjs.add(record.getPaymentDate());
		paramObjs.add(record.getPaymentMode());
		paramObjs.add(record.getPaymentStatus());
		paramObjs.add(record.getChecqueNbr());
		
		int result = 0;
		try{
			result = template.update(JdbcQueryConstant.Save_Record, paramObjs.toArray());
		}catch(DataAccessException ex){
			throw new DaoException("Exception in RecordDao.SaveRecord", ex);
		}
		return result;		
	}
	
	/**
	 * The method returns all the records in the db for a specific date 
	 * passed from the request
	 * 
	 * @return List<Record>
	 * @throws DaoException
	 */
	public List<Record> getAllRecordForCurrentDate(String currentDate) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Record> listRecords = null;
		List<Map<String, Object>> queryResult = null;
		List<Object> paramObjs = new ArrayList<Object>();		
		paramObjs.add(currentDate);
		
		try{
			queryResult = template.queryForList(JdbcQueryConstant.Get_All_Record_For_Current_Date, paramObjs.toArray());
		}catch(DataAccessException ex){
			throw new DaoException("Exception caught in RecordDao.GetAllRecordForCurrentDate", ex);
		}
		if(queryResult != null){
			listRecords = getAllRecords(queryResult);
		}
		return listRecords;
	}
	

	/**
	 * The method deletes a record from the DB based on the Record ID
	 * coming as a request param
	 * 
	 * @param recordId
	 * @throws DaoException
	 */
	public void deleteRecord(BigDecimal recordId) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Object> paramObjs = new ArrayList<Object>();		
		paramObjs.add(recordId);
		
		try{
			template.update(JdbcQueryConstant.Delete_Record, paramObjs.toArray());
		}catch(DataAccessException ex){
			throw new DaoException("Exception caught in RecordDao.DeleteRecord", ex);
		}
	}
	
	/**
	 * The method generally updates the payment details
	 * of a record. It checks if the payment mode is by checque
	 * then it even updates the CHECQUE_NBR column of the table
	 * 
	 * @param record
	 * @return no. of row updated
	 * @throws DaoException
	 */
	public int updateRecord(Record record) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Object> paramObjs = new ArrayList<Object>();
		paramObjs.add(record.getPaymentDate());
		paramObjs.add(record.getPaymentMode());
		paramObjs.add(record.getPaymentStatus());
		
		StringBuilder sql = new StringBuilder(JdbcQueryConstant.Update_Record);
		if(PaymentMode.CHECQUE.getId() == record.getPaymentMode()){
			sql.append(JdbcQueryConstant.Add_Checque_Nbr);
			paramObjs.add(record.getChecqueNbr());
		}
		sql.append(JdbcQueryConstant.WHERE_RECORD_ID);
		
		int result = 0;
		try{
			result = template.update(sql.toString(), paramObjs.toArray());
		}catch(DataAccessException ex){
			throw new DaoException("Exception caught in RecordDao.UpdateRecord", ex);
		}
		return result;		
	}
	
	/**
	 * The method builds the search query to fetch the records
	 * The sql is  built dynamically based on the input
	 * 
	 * @param SearchPredicate
	 * @return List<Record>
	 */
	public List<Record> searchRecords(SearchPredicate predicate)  throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Record> listRecords = null;
		List<Map<String, Object>> queryResult = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select * from Record where Date");
		if(predicate.getEndDate() != null && !predicate.getEndDate().isEmpty()){
			sql.append(" between '"+predicate.getStartDate()+"' and '"+predicate.getEndDate()+"'");
		}else{
			sql.append(" = '"+predicate.getStartDate()+"'");
		}
		if(predicate.getCropName() != null && !predicate.getCropName().isEmpty()){
			sql.append(" and crop_name = '"+predicate.getCropName()+"'");
		}
		if(predicate.getPurchaserName() != null && !predicate.getPurchaserName().isEmpty()){
			sql.append(" and purchaser_name = '"+predicate.getPurchaserName()+"'");
		}
		if(predicate.getPaymentStatus() != null && !predicate.getPaymentStatus().isEmpty()){
			sql.append(" and payment_status = '"+predicate.getPaymentStatus()+"'");
		}
		
		try{
			queryResult = template.queryForList(sql.toString());
		}catch(DataAccessException ex){
			throw new DaoException("Exception caught in RecordDao.searchRecords", ex);
		}
		if(CollectionUtils.isNotEmpty(queryResult)){
			listRecords = getAllRecords(queryResult);
		}		
		return listRecords;
	}
	/**
	 * The method deletes all the records between the specified range
	 * @param startDate
	 * @param endDate
	 * @throws DaoException 
	 */
	public void clearMemory(String startDate, String endDate) throws DaoException {
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Object> paramObjs = new ArrayList<Object>();		
		paramObjs.add(startDate);
		paramObjs.add(endDate);
		
		try{
			template.update(JdbcQueryConstant.Clear_Memory, paramObjs.toArray());
		}catch(DataAccessException ex){
			throw new DaoException("Exception caught in RecordDao.DeleteRecord", ex);
		}
		
	}
	
	private List<Record> getAllRecords(List<Map<String, Object>> queryResult) {
		List<Record> listRecord = new ArrayList<Record>();
		Record record = null;
		for(Map<String, Object> map : queryResult){
			record = new Record();
			record.setRecordId((BigDecimal) map.get("RECORD_ID"));
			record.setDate(getDateAsString((Date) map.get("DATE")));
			record.setPurchaserName((String) map.get("PURCHASER_NAME"));
			record.setVehicleNbr((String) map.get("VEHICLE_NBR"));
			record.setCropName((String) map.get("CROP_NAME"));
			record.setBagQty((BigDecimal) map.get("BAG_QTY"));
			record.setNetWeight((BigDecimal) map.get("NET_WEIGHT"));
			record.setActualWeight((BigDecimal) map.get("ACTUAL_WEIGHT"));
			record.setCropRate((BigDecimal) map.get("CROP_RATE"));
			record.setTotalCost((BigDecimal) map.get("TOTAL_COST"));
			record.setMarketFee((BigDecimal) map.get("MARKET_FEE"));
			record.setSupervisionFee((BigDecimal) map.get("SUPERVISION_FEE"));
			record.setTotalTax((BigDecimal) map.get("TOTAL_TAX"));
			record.setPaymentDate(getDateAsString((Date) map.get("PAYMENT_DATE")));
			record.setPaymentMode((Integer) map.get("PAYMENT_MODE"));
			record.setPaymentStatus((String) map.get("PAYMENT_STATUS"));
			record.setChecqueNbr((String) map.get("CHECQUE_NBR"));
			listRecord.add(record);
		}
		return listRecord;
	}
	
	private String getDateAsString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}

}
