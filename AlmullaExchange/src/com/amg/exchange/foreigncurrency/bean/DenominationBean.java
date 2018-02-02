package com.amg.exchange.foreigncurrency.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class DenominationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal denominationName;
	//private int noOfNotes;
	private String noOfNotes;
	private BigDecimal purchaseAmount;
	private BigDecimal exchangeRate;
	private BigDecimal salesAmount;
	private int denominationID;
	private String denominationDesc;
	private BigDecimal denominationAmount;
	private String currencyCode;

	/**
	 * @param denominationName
	 * @param noOfNotes
	 * @param purchaseAmount
	 * @param exchangeRate
	 * @param salesAmount
	 */
	public DenominationBean(BigDecimal denominationName, String noOfNotes,
			BigDecimal exchangeRate,int denominationID,String denominationDesc, BigDecimal denominationAmount,String currencyCode) {

		this.denominationName = denominationName;
		this.noOfNotes = noOfNotes;
		this.exchangeRate = exchangeRate;
		this.denominationID = denominationID;
		this.denominationDesc = denominationDesc;
		this.denominationAmount =denominationAmount;
		this.currencyCode= currencyCode;
		
		}
	
	
	/**
	 * @param denominationName
	 * @param noOfNotes
	 */
	public DenominationBean(BigDecimal denominationName, String noOfNotes) {
		
		this.denominationName = denominationName;
		this.noOfNotes = noOfNotes;
	}
	
	public DenominationBean() {
		
	}


	public BigDecimal getDenominationName() {
		return denominationName;
	}
	public void setDenominationName(BigDecimal denominationName) {
		this.denominationName = denominationName;
	}
	public String getNoOfNotes() {
		return noOfNotes;
	}
	public void setNoOfNotes(String noOfNotes) {
		this.noOfNotes = noOfNotes;
	}
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(BigDecimal salesAmount) {
		this.salesAmount = salesAmount;
	}


	public int getDenominationID() {
		return denominationID;
	}


	public void setDenominationID(int denominationID) {
		this.denominationID = denominationID;
	}


	public String getDenominationDesc() {
		return denominationDesc;
	}


	public void setDenominationDesc(String denominationDesc) {
		this.denominationDesc = denominationDesc;
	}
	
	public BigDecimal getDenominationAmount() {
		return denominationAmount;
	}


	public void setDenominationAmount(BigDecimal denominationAmount) {
		this.denominationAmount = denominationAmount;
	}


	public String getCurrencyCode() {
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	

}
