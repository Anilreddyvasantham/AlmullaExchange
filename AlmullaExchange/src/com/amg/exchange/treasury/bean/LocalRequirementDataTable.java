package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class LocalRequirementDataTable {
	
	
	private String bankCountry=null;
	private BigDecimal bankCountryId=null;
	private String bankCurrency=null;
	private BigDecimal totalAmount=null;
	private BigDecimal localBalance=null;
	private Date saleProjection=null;
	private String bankName=null;
	
	
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Date getSaleProjection() {
		return saleProjection;
	}
	public void setSaleProjection(Date saleProjection) {
		this.saleProjection = saleProjection;
	}
	public String getBankCountry() {
		return bankCountry;
	}
	public void setBankCountry(String bankCountry) {
		this.bankCountry = bankCountry;
	}
	public String getBankCurrency() {
		return bankCurrency;
	}
	public void setBankCurrency(String bankCurrency) {
		this.bankCurrency = bankCurrency;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getLocalBalance() {
		return localBalance;
	}
	public void setLocalBalance(BigDecimal localBalance) {
		this.localBalance = localBalance;
	}
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	
	

}
