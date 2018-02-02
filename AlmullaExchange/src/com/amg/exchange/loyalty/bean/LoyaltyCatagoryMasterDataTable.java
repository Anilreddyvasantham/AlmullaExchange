package com.amg.exchange.loyalty.bean;

import java.math.BigDecimal;
import java.util.Date;

public class LoyaltyCatagoryMasterDataTable {

	private BigDecimal loyaltyCatagoryId;
	private BigDecimal loyaltyCatagoryDescId;
	private BigDecimal localLanguageId;
	private BigDecimal englishLanguageId;
	private BigDecimal engLoyaltyCatagoryDescId;
	private BigDecimal localLoyaltyCatagoryDescId;
	private BigDecimal appCountryId;
	
	private String fullEngLoyaltyCatagoryDesc;
	private String fullLocalLoyaltyCatagoryDesc;
	private String shortEngLoyaltyCatagoryDesc;
	private String shortLocalLoyaltyCatagoryDesc;
	
	private String categoryCode;
	private String fieldName;
	private String validOption;
	private String validOptionName;
	private String categoryType;
	private BigDecimal categoryTypeId;
	
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	
	private String remarks;
	private String dynamicLabelForActivateDeactivate;
	private Boolean remarksCheck = false;
	private Boolean activateRecordCheck = false;

	private Boolean booCheckDelete = false;

	public LoyaltyCatagoryMasterDataTable() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getLoyaltyCatagoryId() {
		return loyaltyCatagoryId;
	}

	public void setLoyaltyCatagoryId(BigDecimal loyaltyCatagoryId) {
		this.loyaltyCatagoryId = loyaltyCatagoryId;
	}

	
	public BigDecimal getLoyaltyCatagoryDescId() {
		return loyaltyCatagoryDescId;
	}

	public void setLoyaltyCatagoryDescId(BigDecimal loyaltyCatagoryDescId) {
		this.loyaltyCatagoryDescId = loyaltyCatagoryDescId;
	}

	public BigDecimal getLocalLanguageId() {
		return localLanguageId;
	}

	public void setLocalLanguageId(BigDecimal localLanguageId) {
		this.localLanguageId = localLanguageId;
	}

	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}

	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
	}

	public BigDecimal getEngLoyaltyCatagoryDescId() {
		return engLoyaltyCatagoryDescId;
	}

	public void setEngLoyaltyCatagoryDescId(BigDecimal engLoyaltyCatagoryDescId) {
		this.engLoyaltyCatagoryDescId = engLoyaltyCatagoryDescId;
	}

	public BigDecimal getLocalLoyaltyCatagoryDescId() {
		return localLoyaltyCatagoryDescId;
	}

	public void setLocalLoyaltyCatagoryDescId(BigDecimal localLoyaltyCatagoryDescId) {
		this.localLoyaltyCatagoryDescId = localLoyaltyCatagoryDescId;
	}

	public String getFullEngLoyaltyCatagoryDesc() {
		return fullEngLoyaltyCatagoryDesc;
	}

	public void setFullEngLoyaltyCatagoryDesc(String fullEngLoyaltyCatagoryDesc) {
		this.fullEngLoyaltyCatagoryDesc = fullEngLoyaltyCatagoryDesc;
	}

	public String getFullLocalLoyaltyCatagoryDesc() {
		return fullLocalLoyaltyCatagoryDesc;
	}

	public void setFullLocalLoyaltyCatagoryDesc(String fullLocalLoyaltyCatagoryDesc) {
		this.fullLocalLoyaltyCatagoryDesc = fullLocalLoyaltyCatagoryDesc;
	}

	public String getShortEngLoyaltyCatagoryDesc() {
		return shortEngLoyaltyCatagoryDesc;
	}

	public void setShortEngLoyaltyCatagoryDesc(String shortEngLoyaltyCatagoryDesc) {
		this.shortEngLoyaltyCatagoryDesc = shortEngLoyaltyCatagoryDesc;
	}

	public String getShortLocalLoyaltyCatagoryDesc() {
		return shortLocalLoyaltyCatagoryDesc;
	}

	public void setShortLocalLoyaltyCatagoryDesc(String shortLocalLoyaltyCatagoryDesc) {
		this.shortLocalLoyaltyCatagoryDesc = shortLocalLoyaltyCatagoryDesc;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getValidOption() {
		return validOption;
	}

	public void setValidOption(String validOption) {
		this.validOption = validOption;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getValidOptionName() {
		return validOptionName;
	}

	public void setValidOptionName(String validOptionName) {
		this.validOptionName = validOptionName;
	}

	public BigDecimal getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(BigDecimal categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}
	
	

}
