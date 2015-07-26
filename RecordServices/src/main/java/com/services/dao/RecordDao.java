package com.services.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.services.exception.DaoException;
import com.services.model.Record;

public class RecordDao {
	
	@Autowired
	private DataSource datasource;
	
	public void saveRecord(Record record) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		
	}
	
	public List<Record> getAllRecordForCurrentDate() throws DaoException{
		return null;
	}
	
	public void deleteRecord(BigDecimal recordId) throws DaoException{
		
	}
	
	public void updateRecord(Record record) throws DaoException{
		
	}

}
