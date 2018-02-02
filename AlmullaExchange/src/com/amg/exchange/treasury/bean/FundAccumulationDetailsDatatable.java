package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;

public class FundAccumulationDetailsDatatable {
	
	private int count;
	private BigDecimal salesForeignCurrencyPrjection;
	private BigDecimal usdSalesForeignCurrencyPrjection;
	private String date;
	private BigDecimal ikonRate;
	private String bankCountry;
	private String bankName;
	private String currency;
	private String accountNumber;
	
	public FundAccumulationDetailsDatatable(int count,
			BigDecimal salesForeignCurrencyPrjection,
			BigDecimal usdSalesForeignCurrencyPrjection, String date,
			BigDecimal ikonRate, String bankCountry, String bankName,
			String currency, String accountNumber) {
		
		this.count = count;
		this.salesForeignCurrencyPrjection = salesForeignCurrencyPrjection;
		this.usdSalesForeignCurrencyPrjection = usdSalesForeignCurrencyPrjection;
		this.date = date;
		this.ikonRate = ikonRate;
		this.bankCountry = bankCountry;
		this.bankName = bankName;
		this.currency = currency;
		this.accountNumber = accountNumber;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public BigDecimal getSalesForeignCurrencyPrjection() {
		return salesForeignCurrencyPrjection;
	}
	public void setSalesForeignCurrencyPrjection(
			BigDecimal salesForeignCurrencyPrjection) {
		this.salesForeignCurrencyPrjection = salesForeignCurrencyPrjection;
	}
	public BigDecimal getUsdSalesForeignCurrencyPrjection() {
		return usdSalesForeignCurrencyPrjection;
	}
	public void setUsdSalesForeignCurrencyPrjection(
			BigDecimal usdSalesForeignCurrencyPrjection) {
		this.usdSalesForeignCurrencyPrjection = usdSalesForeignCurrencyPrjection;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public BigDecimal getIkonRate() {
		return ikonRate;
	}
	public void setIkonRate(BigDecimal ikonRate) {
		this.ikonRate = ikonRate;
	}
	public String getBankCountry() {
		return bankCountry;
	}
	public void setBankCountry(String bankCountry) {
		this.bankCountry = bankCountry;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	
	
	

}
