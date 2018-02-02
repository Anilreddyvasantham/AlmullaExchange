package com.amg.exchange.loyalty.bean;

import java.math.BigDecimal;
import java.util.Date;

public class LoyaltyTypeDataTable {

	private BigDecimal loyalityTypeId;
	private String loyalityTypeCode;
	private String loyalityType;
	private String corporateCode;
	private String corporatePoints;
	private String employeePoints;
	private BigDecimal levelNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	
	private BigDecimal loyalityTypeDescId;
	private BigDecimal loyaltyTypeArabicDescId;
	private String shortDescEnglish;
	private String fullDescEnglish;
	private String shortDescLocal;
	private String fullDescLocal;
	private BigDecimal applicationCountryId;
	
	private String dynamicLabelForActivateDeactivate;
	
	private Boolean remarksCheck = false;
	private Boolean activateRecordCheck = false;

	private Boolean booCheckDelete = false;
	
	
	
	public LoyaltyTypeDataTable() {
	}
	public BigDecimal getLoyalityTypeId() {
		return loyalityTypeId;
	}
	public void setLoyalityTypeId(BigDecimal loyalityTypeId) {
		this.loyalityTypeId = loyalityTypeId;
	}
	public String getLoyalityTypeCode() {
		return loyalityTypeCode;
	}
	public void setLoyalityTypeCode(String loyalityTypeCode) {
		this.loyalityTypeCode = loyalityTypeCode;
	}
	public String getLoyalityType() {
		return loyalityType;
	}
	public void setLoyalityType(String loyalityType) {
		this.loyalityType = loyalityType;
	}
	public String getCorporateCode() {
		return corporateCode;
	}
	public void setCorporateCode(String corporateCode) {
		this.corporateCode = corporateCode;
	}
	public String getCorporatePoints() {
		return corporatePoints;
	}
	public void setCorporatePoints(String corporatePoints) {
		this.corporatePoints = corporatePoints;
	}
	public String getEmployeePoints() {
		return employeePoints;
	}
	public void setEmployeePoints(String employeePoints) {
		this.employeePoints = employeePoints;
	}
	public BigDecimal getLevelNo() {
		return levelNo;
	}
	public void setLevelNo(BigDecimal levelNo) {
		this.levelNo = levelNo;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getLoyalityTypeDescId() {
		return loyalityTypeDescId;
	}
	public void setLoyalityTypeDescId(BigDecimal loyalityTypeDescId) {
		this.loyalityTypeDescId = loyalityTypeDescId;
	}
	public String getShortDescEnglish() {
		return shortDescEnglish;
	}
	public void setShortDescEnglish(String shortDescEnglish) {
		this.shortDescEnglish = shortDescEnglish;
	}
	public String getFullDescEnglish() {
		return fullDescEnglish;
	}
	public void setFullDescEnglish(String fullDescEnglish) {
		this.fullDescEnglish = fullDescEnglish;
	}
	public String getShortDescLocal() {
		return shortDescLocal;
	}
	public void setShortDescLocal(String shortDescLocal) {
		this.shortDescLocal = shortDescLocal;
	}
	public String getFullDescLocal() {
		return fullDescLocal;
	}
	public void setFullDescLocal(String fullDescLocal) {
		this.fullDescLocal = fullDescLocal;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public Boolean getRemarksCheck() {
		return remarksCheck;
	}
	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}
	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}
	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}
	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public BigDecimal getLoyaltyTypeArabicDescId() {
		return loyaltyTypeArabicDescId;
	}
	public void setLoyaltyTypeArabicDescId(BigDecimal loyaltyTypeArabicDescId) {
		this.loyaltyTypeArabicDescId = loyaltyTypeArabicDescId;
	}
	
	

}
