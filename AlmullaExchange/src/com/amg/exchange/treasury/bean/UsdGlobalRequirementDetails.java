package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;

public class UsdGlobalRequirementDetails {
	
	private BigDecimal salesProjectionValue;
	private BigDecimal valueDatedTransaction;
	private BigDecimal bankBalance;
	private BigDecimal usdEquivalantAmount;
	private BigDecimal ikonRate;
	private String bankCountry;
	private String bank;
	private String bankCode;
	private String currency;
	private String accountNumber;
	
	
	
	
	public UsdGlobalRequirementDetails() {
	}



	public String getBankCode() {
		return bankCode;
	}



	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}



	public BigDecimal getSalesProjectionValue() {
		return salesProjectionValue;
	}
	public void setSalesProjectionValue(BigDecimal salesProjectionValue) {
		this.salesProjectionValue = salesProjectionValue;
	}
	
	public BigDecimal getValueDatedTransaction() {
		return valueDatedTransaction;
	}
	public void setValueDatedTransaction(BigDecimal valueDatedTransaction) {
		this.valueDatedTransaction = valueDatedTransaction;
	}
	
	public BigDecimal getBankBalance() {
		return bankBalance;
	}
	public void setBankBalance(BigDecimal bankBalance) {
		this.bankBalance = bankBalance;
	}
	
	public BigDecimal getUsdEquivalantAmount() {
		return usdEquivalantAmount;
	}
	public void setUsdEquivalantAmount(BigDecimal usdEquivalantAmount) {
		this.usdEquivalantAmount = usdEquivalantAmount;
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
	
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
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
