package com.services.common;

/**
 * The enum will hold Payment Mode Details
 * Cash or Checque 
 * 
 * @author Shahbaz.Alam
 *
 */
public enum PaymentMode {
	
	CASH(1), CHECQUE(2);
	
	private Integer id;
	
	private PaymentMode(Integer id){
		this.setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
