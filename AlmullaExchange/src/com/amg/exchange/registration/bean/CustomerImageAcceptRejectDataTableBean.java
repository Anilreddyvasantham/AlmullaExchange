package com.amg.exchange.registration.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerImageAcceptRejectDataTableBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customerName;
	private String customerNameLocal;
	private String customerReference;
	private String idNumber;
	private Date idExpireyDate;
	private String createdDate;
	private String createdBy;
	private BigDecimal  idType;
	private String idTypeName;
	private String branchName;
	private String verifiedDate;
	private String verifiedBy;
	private String complianceStatus;
	public CustomerImageAcceptRejectDataTableBean() {
	
	}
	
	public CustomerImageAcceptRejectDataTableBean(String customerName,
			String customerNameLocal, String customerReference,
			String idNumber, Date idExpireyDate, String createdDate,
			String createdBy, BigDecimal idType, String idTypeName,
			String branchName, String verifiedDate, String verifiedBy,
			String complianceStatus) {
		super();
		this.customerName = customerName;
		this.customerNameLocal = customerNameLocal;
		this.customerReference = customerReference;
		this.idNumber = idNumber;
		this.idExpireyDate = idExpireyDate;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.idType = idType;
		this.idTypeName = idTypeName;
		this.branchName = branchName;
		this.verifiedDate = verifiedDate;
		this.verifiedBy = verifiedBy;
		this.complianceStatus = complianceStatus;
	}



	public String getComplianceStatus() {
		return complianceStatus;
	}


	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}


	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNameLocal() {
		return customerNameLocal;
	}
	public void setCustomerNameLocal(String customerNameLocal) {
		this.customerNameLocal = customerNameLocal;
	}
	public String getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Date getIdExpireyDate() {
		return idExpireyDate;
	}
	public void setIdExpireyDate(Date idExpireyDate) {
		this.idExpireyDate = idExpireyDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public BigDecimal getIdType() {
		return idType;
	}
	public void setIdType(BigDecimal idType) {
		this.idType = idType;
	}
	public String getIdTypeName() {
		return idTypeName;
	}
	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getVerifiedDate() {
		return verifiedDate;
	}
	public void setVerifiedDate(String verifiedDate) {
		this.verifiedDate = verifiedDate;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	

}
