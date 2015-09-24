package com.services.manager;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.services.dao.RecordDao;
import com.services.exception.DaoException;
import com.services.exception.RecordManagerException;
import com.services.model.GraphResponse;
import com.services.model.Record;
import com.services.model.SearchPredicate;

/**
 * This is a central point for all Record related operations
 * 
 * @author Shahbaz.Alam
 */
@Component
public class RecordManager {
	
	@Autowired
	private RecordDao recordDao;
	
	/**
	 * The method takes the Record object and passes on to the Dao
	 * If the Update is successful, it fetches all the list of records
	 * for that particular date and returns
	 * 
	 * @return List<Record>
	 */
	public List<Record> saveAndReturnRecords(Record record) throws RecordManagerException{
		List<Record> listRecords = null;
		calculateOtherFieldsOfRecord(record);
		try{
			int result = recordDao.saveRecord(record);
			if(result == 1){
				listRecords = recordDao.getAllRecordForCurrentDate(record.getDate());
			}else{
				throw new RecordManagerException("Failed to Save the Record");
			}
		}catch(DaoException ex){
			throw new RecordManagerException("DaoException Caught in saveAndReturnRecords", ex);
		}		
		return listRecords;
	}
	
	/**
	 * This method will delete the Record from the DB. It takes in the 
	 * Record ID and passes on to the Dao Layer.
	 * 
	 * @param recordId
	 */
	public void deleteRecord(BigDecimal recordId) throws RecordManagerException{
		try{
			recordDao.deleteRecord(recordId);
		}catch(DaoException ex){
			throw new RecordManagerException("DaoException Caught in deleteRecord with Record Id:"+recordId, ex);
		}
	}
	
	/**
	 * This method will update the Record in the DB
	 * It takes the Record object, primarily containing Record Id,
	 * Payment Date, Payment Status and Payment Mode
	 * 
	 * @param record
	 */
	public int updateRecord(Record record) throws RecordManagerException{
		int result = 0;
		try{
			result = recordDao.updateRecord(record);
		}catch(DaoException ex){
			throw new RecordManagerException("DaoException Caught in Updating Record", ex);
		}
		return result;
	}
	
	/**
	 * This method returns the list of records for the current date.
	 * It takes the current system date and passes to the dao layer 
	 * for retrieval
	 * @throws RecordManagerException 
	 * 
	 */
	public List<Record> getAllRecordsForCurrentDate() throws RecordManagerException {
		List<Record> listRecord = null;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		try{
			listRecord = recordDao.getAllRecordForCurrentDate(currentDate);
		}catch(DaoException ex){
			throw new RecordManagerException("DaoException Caught in Updating Record", ex);
		}
		return listRecord;
	}
	
	/**
	 * The method is used to search a list of records based on the given
	 * search criteria. Search can be made on the basis of;
	 * a given date,
	 * between two given dates,
	 * dates with crop name
	 * dates with purchaser name
	 * dates with crop name and purchaser name
	 * 
	 * @param searchPredicate
	 * @return
	 * @throws RecordManagerException
	 */
	public List<Record> searchRecords(SearchPredicate searchPredicate) throws RecordManagerException {
		try{
			return recordDao.searchRecords(searchPredicate);
		}catch(DaoException ex){
			throw new RecordManagerException("DaoException Caught in searchRecords", ex);
		}
	}
	
	/**
	 * The method retrieves the total tax paid and unpaid for a particular
	 * month
	 * 
	 * @return
	 * @throws RecordManagerException
	 */
	public GraphResponse getGraphDetails() throws RecordManagerException{
		GraphResponse response = null;
		SearchPredicate predicate = new SearchPredicate();
		StringBuilder startFinancialYear = new StringBuilder("01/04/");
		String currentDate = getCurrentDate();
		String[] arr = currentDate.split("/");
		Integer currentMonth = Integer.valueOf(arr[1]);
		if(currentMonth < 4){
			Integer year = Calendar.getInstance().get(Calendar.YEAR) - 1;
			startFinancialYear.append(year);
		}else{
			startFinancialYear.append(Calendar.getInstance().get(Calendar.YEAR));
		}
		predicate.setStartDate(startFinancialYear.toString());
		predicate.setEndDate(currentDate);
		
		List<Record> listRecord = null;
		try{
			listRecord = recordDao.searchRecords(predicate);
		}catch(DaoException ex){
			throw new RecordManagerException("DaoException Caught in searchRecords", ex);
		}
		if(CollectionUtils.isNotEmpty(listRecord)){
			response = populateGraphDetails(listRecord);
		}
		return response;
	}
	
	/**
	 * The method clears the db details of all the records between the 
	 * start date and end date
	 * @param startDate
	 * @param endDate
	 * @throws RecordManagerException
	 */
	public void clearMemory(String startDate, String endDate) throws RecordManagerException{
		try{
			recordDao.clearMemory(startDate, endDate);
		}catch(DaoException ex){
			throw new RecordManagerException("DaoException Caught in clearMemory", ex);
		}
		
	}
	
	private GraphResponse populateGraphDetails(List<Record> listRecord) {
		GraphResponse response = new GraphResponse();
		Map<String, Object> paidTax = new HashMap<String, Object>();
		Map<String, Object> unpaidTax = new HashMap<String, Object>();
		Map<String, List<Record>> recordMap = new HashMap<String, List<Record>>();
		for(Record record : listRecord){
			String[] arr = record.getDate().split("/");
			if(recordMap.containsKey(arr[1])){
				recordMap.get(arr[1]).add(record);
			}else{
				List<Record> recList = new ArrayList<Record>();
				recList.add(record);
				recordMap.put(arr[1], recList);
			}
		}
		for(String key : recordMap.keySet()){
			BigDecimal ptotalTax = new BigDecimal("0");
			BigDecimal utotalTax = new BigDecimal("0");
			for(Record record : recordMap.get(key)){
				if(record.getPaymentStatus().equalsIgnoreCase("P")){
					ptotalTax = ptotalTax.add(record.getTotalTax());
				}else{
					utotalTax = utotalTax.add(record.getTotalTax());
				}				
			}
			paidTax.put(key, ptotalTax);
			unpaidTax.put(key, utotalTax);
		}
		response.setPaidTax(paidTax);
		response.setUnpaidTax(unpaidTax);		
		return response;
	}

	private String getCurrentDate(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(cal.getTime());
	}	

	private void calculateOtherFieldsOfRecord(Record record) {
		BigDecimal netWt = record.getNetWeight();
		BigDecimal bagQty = record.getBagQty();
		BigDecimal cropRate = record.getCropRate();
		
		//calculation starts
		BigDecimal actualWt = netWt.subtract(bagQty);
		
		BigDecimal totalCost = actualWt.multiply(cropRate);
		totalCost = totalCost.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal marketFee = totalCost.divide(new BigDecimal("100"));
		
		BigDecimal supervisionFee = marketFee.multiply(new BigDecimal("0.05"));
		supervisionFee = supervisionFee.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal totalTax = marketFee.add(supervisionFee);
		totalTax = totalTax.setScale(2, BigDecimal.ROUND_HALF_UP);
		//calculation ends
		
		record.setActualWeight(actualWt);
		record.setTotalCost(totalCost);
		record.setMarketFee(marketFee);
		record.setSupervisionFee(supervisionFee);
		record.setTotalTax(totalTax);
				
	}

}
