package com.amg.exchange.enquiry.bean;

import java.math.BigDecimal;

public class BankMasterEnquiryDataTable {
	
	private BigDecimal bankId;
	private String bankName;
	private String bankCode;
	private String bankLength;
	private String contactDetails;
	private String accountDetails;
	private String status;
	private String bankIndicator;
	
	

	
	public String getBankIndicator() {
		return bankIndicator;
	}
	public void setBankIndicator(String bankIndicator) {
		this.bankIndicator = bankIndicator;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getBankLength() {
		return bankLength;
	}
	public void setBankLength(String bankLength) {
		this.bankLength = bankLength;
	}
	public String getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}
	public String getAccountDetails() {
		return accountDetails;
	}
	public void setAccountDetails(String accountDetails) {
		this.accountDetails = accountDetails;
	}
	
	
	
	
	

}
