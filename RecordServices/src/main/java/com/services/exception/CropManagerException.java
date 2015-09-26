package com.services.exception;

public class CropManagerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CropManagerException(String message){
		super(message);
	}
	
	public CropManagerException(String message, Throwable cause){
		super(message, cause);
	}

}
