package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CorporateCompanyIdentityDocument implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String companyIdNumber;
	private Date companyIdExpiryDate;
	private String companyIdType;
	
	
	public String getCompanyIdNumber() {
		return companyIdNumber;
	}
	public Date getCompanyIdExpiryDate() {
		return companyIdExpiryDate;
	}
	public String getCompanyIdType() {
		return companyIdType;
	}
	public void setCompanyIdNumber(String companyIdNumber) {
		this.companyIdNumber = companyIdNumber;
	}
	public void setCompanyIdExpiryDate(Date companyIdExpiryDate) {
		this.companyIdExpiryDate = companyIdExpiryDate;
	}
	public void setCompanyIdType(String companyIdType) {
		this.companyIdType = companyIdType;
	}

	
}
