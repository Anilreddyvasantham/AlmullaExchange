package com.amg.exchange.common.bean;

import java.math.BigDecimal;
import java.util.Date;

public class SourceOfIncomeDataTableBean {

	
	private BigDecimal sourceOfIncomePk;
	private BigDecimal sourceOfIncomeEngPk;
	private BigDecimal sourceOfIncomeArabicPk;
	private String sourceOfIncomeCode;
	private String sourceOfIncomeEngFullDesc;
	private String sourceOfIncomeEngShortDesc;
	private String sourceOfIncomeArabicFullDesc;
	private String sourceOfIncomeArabicShortDesc;
	private String createdBy;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private String approvedBy;
	private Date approvedDate;
	private BigDecimal companyId;
	private BigDecimal applicationCountryId;
	private String dynamicLabelForActivateDeactivcate;
	private BigDecimal engLanguageId;
	private 	BigDecimal arabicLanguageId;
	private String isActive;
	private String remarks;
	private Boolean permanentDeleteCheck=false;
	private Boolean booCheckUpdate;
	private Boolean activateRecordCheck;
	private Boolean ifEditClicked=false;
	private Boolean remarksCheck;
	private Boolean booHideEditButton=false;
	
	
	
	
	public BigDecimal getSourceOfIncomePk() {
		return sourceOfIncomePk;
	}
	public void setSourceOfIncomePk(BigDecimal sourceOfIncomePk) {
		this.sourceOfIncomePk = sourceOfIncomePk;
	}
	 
	public String getSourceOfIncomeCode() {
		return sourceOfIncomeCode;
	}
	public void setSourceOfIncomeCode(String sourceOfIncomeCode) {
		this.sourceOfIncomeCode = sourceOfIncomeCode;
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
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public String getDynamicLabelForActivateDeactivcate() {
		return dynamicLabelForActivateDeactivcate;
	}
	public void setDynamicLabelForActivateDeactivcate(
			String dynamicLabelForActivateDeactivcate) {
		this.dynamicLabelForActivateDeactivcate = dynamicLabelForActivateDeactivcate;
	}
	public BigDecimal getSourceOfIncomeEngPk() {
		return sourceOfIncomeEngPk;
	}
	public void setSourceOfIncomeEngPk(BigDecimal sourceOfIncomeEngPk) {
		this.sourceOfIncomeEngPk = sourceOfIncomeEngPk;
	}
	public BigDecimal getSourceOfIncomeArabicPk() {
		return sourceOfIncomeArabicPk;
	}
	public void setSourceOfIncomeArabicPk(BigDecimal sourceOfIncomeArabicPk) {
		this.sourceOfIncomeArabicPk = sourceOfIncomeArabicPk;
	}
	public BigDecimal getEngLanguageId() {
		return engLanguageId;
	}
	public void setEngLanguageId(BigDecimal engLanguageId) {
		this.engLanguageId = engLanguageId;
	}
	public BigDecimal getArabicLanguageId() {
		return arabicLanguageId;
	}
	public void setArabicLanguageId(BigDecimal arabicLanguageId) {
		this.arabicLanguageId = arabicLanguageId;
	}
	public String getSourceOfIncomeEngFullDesc() {
		return sourceOfIncomeEngFullDesc;
	}
	public void setSourceOfIncomeEngFullDesc(String sourceOfIncomeEngFullDesc) {
		this.sourceOfIncomeEngFullDesc = sourceOfIncomeEngFullDesc;
	}
	public String getSourceOfIncomeEngShortDesc() {
		return sourceOfIncomeEngShortDesc;
	}
	public void setSourceOfIncomeEngShortDesc(String sourceOfIncomeEngShortDesc) {
		this.sourceOfIncomeEngShortDesc = sourceOfIncomeEngShortDesc;
	}
	public String getSourceOfIncomeArabicFullDesc() {
		return sourceOfIncomeArabicFullDesc;
	}
	public void setSourceOfIncomeArabicFullDesc(
			String sourceOfIncomeArabicFullDesc) {
		this.sourceOfIncomeArabicFullDesc = sourceOfIncomeArabicFullDesc;
	}
	public String getSourceOfIncomeArabicShortDesc() {
		return sourceOfIncomeArabicShortDesc;
	}
	public void setSourceOfIncomeArabicShortDesc(
			String sourceOfIncomeArabicShortDesc) {
		this.sourceOfIncomeArabicShortDesc = sourceOfIncomeArabicShortDesc;
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
	public Boolean getPermanentDeleteCheck() {
		return permanentDeleteCheck;
	}
	public void setPermanentDeleteCheck(Boolean permanentDeleteCheck) {
		this.permanentDeleteCheck = permanentDeleteCheck;
	}
	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}
	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}
	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}
	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}
	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}
	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
	}
	public Boolean getRemarksCheck() {
		return remarksCheck;
	}
	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
	public Boolean getBooHideEditButton() {
		return booHideEditButton;
	}
	public void setBooHideEditButton(Boolean booHideEditButton) {
		this.booHideEditButton = booHideEditButton;
	}
	
	
	
	
	
	
}
