package com.services.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

}
