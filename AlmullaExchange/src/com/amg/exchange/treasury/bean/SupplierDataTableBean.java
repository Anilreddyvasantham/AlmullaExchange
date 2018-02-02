package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;

public class SupplierDataTableBean {
	
	
	private String customerRefId;
	private String customerName;
	private String mobileNumber;
	private String nationality;
	private String email;
	private String sundryDebtorRef;
	private BigDecimal bankId;
	private String bankName;
	private String bankDescription;
	private BigDecimal currencyId;
	private String currencyName;
	private String accountNumber;
	private BigDecimal customerPk;
	
	private BigDecimal treasuryCustomerPk;
	
	
	
	public BigDecimal getTreasuryCustomerPk() {
		return treasuryCustomerPk;
	}
	public void setTreasuryCustomerPk(BigDecimal treasuryCustomerPk) {
		this.treasuryCustomerPk = treasuryCustomerPk;
	}
	public BigDecimal getCustomerPk() {
		return customerPk;
	}
	public void setCustomerPk(BigDecimal customerPk) {
		this.customerPk = customerPk;
	}
	public String getCustomerRefId() {
		return customerRefId;
	}
	public void setCustomerRefId(String customerRefId) {
		this.customerRefId = customerRefId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSundryDebtorRef() {
		return sundryDebtorRef;
	}
	public void setSundryDebtorRef(String sundryDebtorRef) {
		this.sundryDebtorRef = sundryDebtorRef;
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
	public String getBankDescription() {
		return bankDescription;
	}
	public void setBankDescription(String bankDescription) {
		this.bankDescription = bankDescription;
	}
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
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	
	
	
	

}
