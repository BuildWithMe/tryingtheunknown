package com.services.model;

import java.util.Map;

public class GraphResponse {
	
	private Map<String, Object> paidTax;
	
	private Map<String, Object> unpaidTax;

	public Map<String, Object> getPaidTax() {
		return paidTax;
	}

	public void setPaidTax(Map<String, Object> paidTax) {
		this.paidTax = paidTax;
	}

	public Map<String, Object> getUnpaidTax() {
		return unpaidTax;
	}

	public void setUnpaidTax(Map<String, Object> unpaidTax) {
		this.unpaidTax = unpaidTax;
	}

}
