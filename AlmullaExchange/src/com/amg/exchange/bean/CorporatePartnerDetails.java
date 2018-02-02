package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CorporatePartnerDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal partnerIdNumber;
	private String partnerName;
	private String partnerNameLocal;
	private Date expiryDate;
	private String transactionOnBehalf;
	private String partnerDetails;
	private String percentage;
	private String authorizedSignatory;
	private Date effectiveDate;
	private Date validUpTo;
	private String mobileNo;
	private String email;
	private String designation;
	
	
	
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigDecimal getPartnerIdNumber() {
		return partnerIdNumber;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public String getPartnerNameLocal() {
		return partnerNameLocal;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public String getTransactionOnBehalf() {
		return transactionOnBehalf;
	}
	public String getPartnerDetails() {
		return partnerDetails;
	}
	public String getPercentage() {
		return percentage;
	}
	public String getAuthorizedSignatory() {
		return authorizedSignatory;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public Date getValidUpTo() {
		return validUpTo;
	}
	public void setPartnerIdNumber(BigDecimal partnerIdNumber) {
		this.partnerIdNumber = partnerIdNumber;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public void setPartnerNameLocal(String partnerNameLocal) {
		this.partnerNameLocal = partnerNameLocal;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public void setTransactionOnBehalf(String transactionOnBehalf) {
		this.transactionOnBehalf = transactionOnBehalf;
	}
	public void setPartnerDetails(String partnerDetails) {
		this.partnerDetails = partnerDetails;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public void setAuthorizedSignatory(String authorizedSignatory) {
		this.authorizedSignatory = authorizedSignatory;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public void setValidUpTo(Date validUpTo) {
		this.validUpTo = validUpTo;
	}

	
}
