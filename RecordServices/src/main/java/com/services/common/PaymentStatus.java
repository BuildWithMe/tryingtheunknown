package com.services.common;

/**
 * The enum will hold the details of Payment Status
 * Paid or Unpaid
 * 
 * @author Shahbaz.Alam
 *
 */
public enum PaymentStatus {
	PAID("P"), UNPAID("U");
	
	private String id;
	
	private PaymentStatus(String id){
		this.setId(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
