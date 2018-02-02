package com.amg.exchange.enquiry.bean;

import java.math.BigDecimal;

public class AverageRateBankORCurrencyWiseEnquiryDataTable {
	
	//Data Table 1 Properties
	private String bankName;
	private String bankCode;
	private String currencyName;
	private BigDecimal bankId;
	private BigDecimal currencyId;
	private String foreignCurrencyBalForCommonPool;
	private BigDecimal localBalanceForCommonPool;
	private BigDecimal avgRate;
	private BigDecimal foreignCurrencyBalForProvision;
	private BigDecimal localBalanceForProvision;
	private BigDecimal totalForeigncurrencyBalance;
	private BigDecimal specialDealFcAmount;
	private String countryName;
	
	
	//Data Table 2 Properties
	private String customerName;
	private BigDecimal customerReference;
	private BigDecimal foreignCurrencyBalance;
	
	
	
	
	
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getSpecialDealFcAmount() {
		return specialDealFcAmount;
	}
	public void setSpecialDealFcAmount(BigDecimal specialDealFcAmount) {
		this.specialDealFcAmount = specialDealFcAmount;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	public BigDecimal getForeignCurrencyBalance() {
		return foreignCurrencyBalance;
	}
	public void setForeignCurrencyBalance(BigDecimal foreignCurrencyBalance) {
		this.foreignCurrencyBalance = foreignCurrencyBalance;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getForeignCurrencyBalForCommonPool() {
		return foreignCurrencyBalForCommonPool;
	}
	public void setForeignCurrencyBalForCommonPool(
			String foreignCurrencyBalForCommonPool) {
		this.foreignCurrencyBalForCommonPool = foreignCurrencyBalForCommonPool;
	}
	public BigDecimal getLocalBalanceForCommonPool() {
		return localBalanceForCommonPool;
	}
	public void setLocalBalanceForCommonPool(BigDecimal localBalanceForCommonPool) {
		this.localBalanceForCommonPool = localBalanceForCommonPool;
	}
	public BigDecimal getAvgRate() {
		return avgRate;
	}
	public void setAvgRate(BigDecimal avgRate) {
		this.avgRate = avgRate;
	}
	public BigDecimal getForeignCurrencyBalForProvision() {
		return foreignCurrencyBalForProvision;
	}
	public void setForeignCurrencyBalForProvision(
			BigDecimal foreignCurrencyBalForProvision) {
		this.foreignCurrencyBalForProvision = foreignCurrencyBalForProvision;
	}
	public BigDecimal getLocalBalanceForProvision() {
		return localBalanceForProvision;
	}
	public void setLocalBalanceForProvision(BigDecimal localBalanceForProvision) {
		this.localBalanceForProvision = localBalanceForProvision;
	}
	public BigDecimal getTotalForeigncurrencyBalance() {
		return totalForeigncurrencyBalance;
	}
	public void setTotalForeigncurrencyBalance(
			BigDecimal totalForeigncurrencyBalance) {
		this.totalForeigncurrencyBalance = totalForeigncurrencyBalance;
	}

	 private String foreignCurrencyBalForCommonPoolDisplay;
	 private String localBalanceForCommonPoolDisplay;
	  private String totalForeigncurrencyBalanceDisplay;
	  private String foreignCurrencyBalanceDisplay;

	  public String getForeignCurrencyBalForCommonPoolDisplay() {
	  	  return foreignCurrencyBalForCommonPoolDisplay;
	  }
	  public void setForeignCurrencyBalForCommonPoolDisplay(String foreignCurrencyBalForCommonPoolDisplay) {
	  	  this.foreignCurrencyBalForCommonPoolDisplay = foreignCurrencyBalForCommonPoolDisplay;
	  }
	  public String getLocalBalanceForCommonPoolDisplay() {
	  	  return localBalanceForCommonPoolDisplay;
	  }
	  public void setLocalBalanceForCommonPoolDisplay(String localBalanceForCommonPoolDisplay) {
	  	  this.localBalanceForCommonPoolDisplay = localBalanceForCommonPoolDisplay;
	  }
	  public String getTotalForeigncurrencyBalanceDisplay() {
	  	  return totalForeigncurrencyBalanceDisplay;
	  }
	  public void setTotalForeigncurrencyBalanceDisplay(String totalForeigncurrencyBalanceDisplay) {
	  	  this.totalForeigncurrencyBalanceDisplay = totalForeigncurrencyBalanceDisplay;
	  }
	  public String getForeignCurrencyBalanceDisplay() {
	  	  return foreignCurrencyBalanceDisplay;
	  }
	  public void setForeignCurrencyBalanceDisplay(String foreignCurrencyBalanceDisplay) {
	  	  this.foreignCurrencyBalanceDisplay = foreignCurrencyBalanceDisplay;
	  }
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	  
	  
	  
	


}
