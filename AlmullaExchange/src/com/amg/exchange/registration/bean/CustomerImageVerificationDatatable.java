package com.amg.exchange.registration.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerImageVerificationDatatable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerName;
	private String customerNameLocal;
	private String customerReference;
	private String idNumber;
	private Date idExpireyDate;
	private String fileId;
	private Boolean disableCheck;
	private Boolean isCheck =false;
	private Date createdDate;
	private String createdBy;
	
	private BigDecimal  customerImageVerificationId;
	private BigDecimal  idType;
	private String idTypeName;
	private String branchName;
	
	private BigDecimal customerId;
	private Boolean permanetRejectCheck=false;
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String string) {
		this.createdBy = string;
	}
	public BigDecimal getCustomerImageVerificationId() {
		return customerImageVerificationId;
	}
	public void setCustomerImageVerificationId(BigDecimal customerImageVerificationId) {
		this.customerImageVerificationId = customerImageVerificationId;
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
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Boolean getDisableCheck() {
		return disableCheck;
	}
	public void setDisableCheck(Boolean disableCheck) {
		this.disableCheck = disableCheck;
	}
	public Boolean getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
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
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public Boolean getPermanetRejectCheck() {
		return permanetRejectCheck;
	}
	public void setPermanetRejectCheck(Boolean permanetRejectCheck) {
		this.permanetRejectCheck = permanetRejectCheck;
	}
	public CustomerImageVerificationDatatable(String customerName,
			String customerNameLocal, String customerReference,
			String idNumber, Date idExpireyDate, String fileId,
			Boolean disableCheck, Boolean isCheck, Date createdDate,
			String createdBy, BigDecimal customerImageVerificationId,
			BigDecimal idType, String idTypeName, String branchName,BigDecimal customerId,Boolean permanetRejectCheck) {
		super();
		this.customerName = customerName;
		this.customerNameLocal = customerNameLocal;
		this.customerReference = customerReference;
		this.idNumber = idNumber;
		this.idExpireyDate = idExpireyDate;
		this.fileId = fileId;
		this.disableCheck = disableCheck;
		this.isCheck = isCheck;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.customerImageVerificationId = customerImageVerificationId;
		this.idType = idType;
		this.idTypeName = idTypeName;
		this.branchName = branchName;
		this.customerId= customerId;
		this.permanetRejectCheck= permanetRejectCheck;
	}
	public CustomerImageVerificationDatatable() {
	}
	
	
	
	
}
