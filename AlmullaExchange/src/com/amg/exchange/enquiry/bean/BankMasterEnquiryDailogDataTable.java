package com.amg.exchange.enquiry.bean;

import java.math.BigDecimal;

public class BankMasterEnquiryDailogDataTable {
	
	private String bankName;
	private String contactPerson;
	private String status;
	
	// for bank length
	private BigDecimal bankAccountLength;
	
	//for bank account details
	private String bankAccountNumber;
	private BigDecimal minimumBalance;
	private BigDecimal currencyId;
	private String currencyName;
	private String faAccountNumber;
	private String faFundAccountNumber;
	private BigDecimal accountTypeId;
	private String accountTypeName;
	private BigDecimal overDraftLimit;
	
	//for bank Contact Details
	private String department;
	private String mobileNumber;
	private String designation;
	private String zone;
	private BigDecimal zoneId;
	
	
	
	
	
	public String getFaAccountNumber() {
		return faAccountNumber;
	}
	public void setFaAccountNumber(String faAccountNumber) {
		this.faAccountNumber = faAccountNumber;
	}
	public String getFaFundAccountNumber() {
		return faFundAccountNumber;
	}
	public void setFaFundAccountNumber(String faFundAccountNumber) {
		this.faFundAccountNumber = faFundAccountNumber;
	}
	public BigDecimal getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(BigDecimal accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public String getAccountTypeName() {
		return accountTypeName;
	}
	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}
	public BigDecimal getOverDraftLimit() {
		return overDraftLimit;
	}
	public void setOverDraftLimit(BigDecimal overDraftLimit) {
		this.overDraftLimit = overDraftLimit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public BigDecimal getZoneId() {
		return zoneId;
	}
	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public BigDecimal getMinimumBalance() {
		return minimumBalance;
	}
	public void setMinimumBalance(BigDecimal minimumBalance) {
		this.minimumBalance = minimumBalance;
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
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getBankAccountLength() {
		return bankAccountLength;
	}
	public void setBankAccountLength(BigDecimal bankAccountLength) {
		this.bankAccountLength = bankAccountLength;
	}
	
	
	
	

}
