package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CurrencyGL_CountryDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal currencyId;
	private String currencyName;
	private String currencyCode;
	private BigDecimal countryId;
	private String countryName;
	private String countryCode;
	private BigDecimal bankId;
	private String bankName;
	private String bankCode;
	private String glNo;
	private BigDecimal foreignBalance;
	private BigDecimal currentBalance;
	private BigDecimal averageRate;
	
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getGlNo() {
		return glNo;
	}
	public void setGlNo(String glNo) {
		this.glNo = glNo;
	}
	
	public BigDecimal getForeignBalance() {
		return foreignBalance;
	}
	public void setForeignBalance(BigDecimal foreignBalance) {
		this.foreignBalance = foreignBalance;
	}
	
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
	
	public BigDecimal getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}
	
	
	

}
