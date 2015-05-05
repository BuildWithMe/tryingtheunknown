package com.services.exception;

public class RecordManagerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordManagerException(String message){
		super(message);
	}
	
	public RecordManagerException(String message, Throwable cause){
		super(message, cause);
	}
}
