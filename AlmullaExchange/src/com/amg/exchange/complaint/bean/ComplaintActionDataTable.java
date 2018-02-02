package com.amg.exchange.complaint.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ComplaintActionDataTable {

	private String complaintActionCode;
	private String complaintActionFullDescription;
	private String complaintActionShortDescription;
	private String complaintActionArabicFullDescription;
	private String complaintActionArabicShortDescription;
	private String actionGroup;
	private BigDecimal complaintActionPk;
	private BigDecimal complaintActionDescEnglishPk;
	private BigDecimal complaintActionDescArabicPk;
	private BigDecimal englishLanguageId;
	private BigDecimal arabicLanguageId;
	
	private BigDecimal applicationCountryId;
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActive;
	private String dynamicLabelForActivateDeactivate;
	private Boolean ifClickEdit=false;
	private Boolean remarksCheck;
	private Boolean permanentDeleteCheck;
	private Boolean activateRecordCheck;
	private String actionGroupId;
	public Boolean hideEditButton;
	public Boolean hideSubmitButton;
	private Boolean hideClearButton;
	public Boolean getHideEditButton() {
		return hideEditButton;
	}
	public void setHideEditButton(Boolean hideEditButton) {
		this.hideEditButton = hideEditButton;
	}
	public Boolean getHideSubmitButton() {
		return hideSubmitButton;
	}
	public void setHideSubmitButton(Boolean hideSubmitButton) {
		this.hideSubmitButton = hideSubmitButton;
	}
	
	
	public String getComplaintActionCode() {
		return complaintActionCode;
	}
	public void setComplaintActionCode(String complaintActionCode) {
		this.complaintActionCode = complaintActionCode;
	}
	public String getComplaintActionFullDescription() {
		return complaintActionFullDescription;
	}
	public void setComplaintActionFullDescription(
			String complaintActionFullDescription) {
		this.complaintActionFullDescription = complaintActionFullDescription;
	}
	public String getComplaintActionShortDescription() {
		return complaintActionShortDescription;
	}
	public void setComplaintActionShortDescription(
			String complaintActionShortDescription) {
		this.complaintActionShortDescription = complaintActionShortDescription;
	}
	public String getComplaintActionArabicFullDescription() {
		return complaintActionArabicFullDescription;
	}
	public void setComplaintActionArabicFullDescription(
			String complaintActionArabicFullDescription) {
		this.complaintActionArabicFullDescription = complaintActionArabicFullDescription;
	}
	public String getComplaintActionArabicShortDescription() {
		return complaintActionArabicShortDescription;
	}
	public void setComplaintActionArabicShortDescription(
			String complaintActionArabicShortDescription) {
		this.complaintActionArabicShortDescription = complaintActionArabicShortDescription;
	}
	public String getActionGroup() {
		return actionGroup;
	}
	public void setActionGroup(String actionGroup) {
		this.actionGroup = actionGroup;
	}
	 
	public BigDecimal getComplaintActionDescEnglishPk() {
		return complaintActionDescEnglishPk;
	}
	public void setComplaintActionDescEnglishPk(
			BigDecimal complaintActionDescEnglishPk) {
		this.complaintActionDescEnglishPk = complaintActionDescEnglishPk;
	}
	public BigDecimal getComplaintActionDescArabicPk() {
		return complaintActionDescArabicPk;
	}
	public void setComplaintActionDescArabicPk(
			BigDecimal complaintActionDescArabicPk) {
		this.complaintActionDescArabicPk = complaintActionDescArabicPk;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}
	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
	}
	public BigDecimal getArabicLanguageId() {
		return arabicLanguageId;
	}
	public void setArabicLanguageId(BigDecimal arabicLanguageId) {
		this.arabicLanguageId = arabicLanguageId;
	}
	public Boolean getIfClickEdit() {
		return ifClickEdit;
	}
	public void setIfClickEdit(Boolean ifClickEdit) {
		this.ifClickEdit = ifClickEdit;
	}
	public Boolean getRemarksCheck() {
		return remarksCheck;
	}
	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
	public Boolean getPermanentDeleteCheck() {
		return permanentDeleteCheck;
	}
	public void setPermanentDeleteCheck(Boolean permanentDeleteCheck) {
		this.permanentDeleteCheck = permanentDeleteCheck;
	}
	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}
	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}
	public BigDecimal getComplaintActionPk() {
		return complaintActionPk;
	}
	public void setComplaintActionPk(BigDecimal complaintActionPk) {
		this.complaintActionPk = complaintActionPk;
	}
	public String getActionGroupId() {
		return actionGroupId;
	}
	public void setActionGroupId(String actionGroupId) {
		this.actionGroupId = actionGroupId;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public Boolean getHideClearButton() {
		return hideClearButton;
	}
	public void setHideClearButton(Boolean hideClearButton) {
		this.hideClearButton = hideClearButton;
	}
	
	
	
	
	
	
}
