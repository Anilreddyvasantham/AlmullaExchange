package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class AMIECAndBankMappingApprovalDataTable {
	
	private BigDecimal amiecAndBankMappingPK;
	private String flexField;
	private String fieldName;
	private BigDecimal countryId;
	private String countryName;
	private String bankName;
	private BigDecimal bankId;
	private String bankCode;
	private String amiecCode;
	private String amiecDescription;
	private String bankDecription;

	private String createdBy;
	private Date createdDate;
	private String isActive;
	private Date approvedDate;
	private String approvedBy;
	
	private Date modifiedDate;
	private String modifiedBy;
	
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getAmiecAndBankMappingPK() {
		return amiecAndBankMappingPK;
	}
	public void setAmiecAndBankMappingPK(BigDecimal amiecAndBankMappingPK) {
		this.amiecAndBankMappingPK = amiecAndBankMappingPK;
	}
	public String getFlexField() {
		return flexField;
	}
	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAmiecCode() {
		return amiecCode;
	}
	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}
	public String getAmiecDescription() {
		return amiecDescription;
	}
	public void setAmiecDescription(String amiecDescription) {
		this.amiecDescription = amiecDescription;
	}
	public String getBankDecription() {
		return bankDecription;
	}
	public void setBankDecription(String bankDecription) {
		this.bankDecription = bankDecription;
	}
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	
	

}
