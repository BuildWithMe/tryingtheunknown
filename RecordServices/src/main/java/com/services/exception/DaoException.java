package com.services.exception;

/*
 * This will be the Single exception thrown by all the Dao Classes
 * 
 * @author Shahbaz.Alam
 */
public class DaoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DaoException(String message){
		super(message);
	}
	
	public DaoException(String message, Throwable cause){
		super(message, cause);
	}

}
