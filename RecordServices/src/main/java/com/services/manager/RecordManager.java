package com.services.manager;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.services.dao.RecordDao;
import com.services.exception.DaoException;
import com.services.exception.RecordManagerException;
import com.services.model.Record;

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
		DateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		try{
			listRecord = recordDao.getAllRecordForCurrentDate(currentDate);
		}catch(DaoException ex){
			throw new RecordManagerException("DaoException Caught in Updating Record", ex);
		}
		return listRecord;
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
