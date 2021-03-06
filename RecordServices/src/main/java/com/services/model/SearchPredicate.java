package com.services.model;

public class SearchPredicate {
	
	private String startDate;
	
	private String endDate;
	
	private String purchaserName;
	
	private String cropName;
	
	private String paymentStatus;
	
	private String receiptNbr;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getReceiptNbr() {
		return receiptNbr;
	}

	public void setReceiptNbr(String receiptNbr) {
		this.receiptNbr = receiptNbr;
	}

}
