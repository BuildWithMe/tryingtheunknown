package com.services.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * This is a domain object for a single record in the database
 * 
 * @author Shahbaz.Alam
 */
@XmlRootElement
public class Record {
	
	private Calendar date;
	
	private String purchaserName;
	
	private String verticalNbr;
	
	private String cropName;
	
	private BigDecimal bagQty;
	
	private BigDecimal netWeight;
	
	private BigDecimal actualWeight;
	
	private BigDecimal cropRate;
	
	private BigDecimal totalCost;
	
	private BigDecimal marketFee;
	
	private BigDecimal supervisionFee;
	
	private BigDecimal totalTax;

	
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getVerticalNbr() {
		return verticalNbr;
	}

	public void setVerticalNbr(String verticalNbr) {
		this.verticalNbr = verticalNbr;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public BigDecimal getBagQty() {
		return bagQty;
	}

	public void setBagQty(BigDecimal bagQty) {
		this.bagQty = bagQty;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public BigDecimal getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(BigDecimal actualWeight) {
		this.actualWeight = actualWeight;
	}

	public BigDecimal getCropRate() {
		return cropRate;
	}

	public void setCropRate(BigDecimal cropRate) {
		this.cropRate = cropRate;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getMarketFee() {
		return marketFee;
	}

	public void setMarketFee(BigDecimal marketFee) {
		this.marketFee = marketFee;
	}

	public BigDecimal getSupervisionFee() {
		return supervisionFee;
	}

	public void setSupervisionFee(BigDecimal supervisionFee) {
		this.supervisionFee = supervisionFee;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}


}
