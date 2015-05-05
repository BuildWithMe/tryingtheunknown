package com.services.exception;

public class PurchaserManagerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PurchaserManagerException(String message){
		super(message);
	}
	
	public PurchaserManagerException(String message, Throwable cause){
		super(message, cause);
	}

}
