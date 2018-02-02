package com.amg.exchange.complaint.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ComplaintAssignedDataTable {

	  private BigDecimal applicationCountryId;
	  private String complaintAssignedCode;
	  private BigDecimal complaintAssignedId;
	  private String complaintEnglishFullDescription;
	  private String complaintArabicFullDescription;
	  private String complaintEnglishShortDescription;
	  private String complaintArabicShortDescription;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;
	  private String isActive;
	  private Boolean booSaveOrExit=false;
	  private Boolean booRenderDataTable=false;
	  private Boolean renderEditButton=false;
	  private String dynamicLabelForActivateDeactivate;
	  private String complaintAssignedTo;
	  private String logCompalint;
	  private Boolean booEditButton=false;
	  private Boolean booSubmitPanel=false;
	  private Boolean booClearPanel=false;
	  private BigDecimal complaintEnglishFullDescriptionPK;
	  private BigDecimal complaintEnglishShortDescriptionPK;
	  private BigDecimal complaintArabicFullDescriptionPK;
	  private BigDecimal complaintArabicShortDescriptionPK;
	  private Boolean permanetDeleteCheck=false;
	  private Boolean remarksCheck=false;
	  private BigDecimal englishLanguageId;
	  private BigDecimal arabicLanguageId;
	  private Boolean booAdd=false;
	  private Boolean booApproval=false;
	  private Boolean activateRecordCheck=false;
	  private Boolean ifEditClicked=false;
	  private Boolean booReadOnly=false;
	  private String logCompalintId;
	  
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  public String getComplaintAssignedCode() {
	  	  return complaintAssignedCode;
	  }
	  public void setComplaintAssignedCode(String complaintAssignedCode) {
	  	  this.complaintAssignedCode = complaintAssignedCode;
	  }
	  public BigDecimal getComplaintAssignedId() {
	  	  return complaintAssignedId;
	  }
	  public void setComplaintAssignedId(BigDecimal complaintAssignedId) {
	  	  this.complaintAssignedId = complaintAssignedId;
	  }
	  public String getComplaintEnglishFullDescription() {
	  	  return complaintEnglishFullDescription;
	  }
	  public void setComplaintEnglishFullDescription(String complaintEnglishFullDescription) {
	  	  this.complaintEnglishFullDescription = complaintEnglishFullDescription;
	  }
	  public String getComplaintArabicFullDescription() {
	  	  return complaintArabicFullDescription;
	  }
	  public void setComplaintArabicFullDescription(String complaintArabicFullDescription) {
	  	  this.complaintArabicFullDescription = complaintArabicFullDescription;
	  }
	  public String getComplaintEnglishShortDescription() {
	  	  return complaintEnglishShortDescription;
	  }
	  public void setComplaintEnglishShortDescription(String complaintEnglishShortDescription) {
	  	  this.complaintEnglishShortDescription = complaintEnglishShortDescription;
	  }
	  public String getComplaintArabicShortDescription() {
	  	  return complaintArabicShortDescription;
	  }
	  public void setComplaintArabicShortDescription(String complaintArabicShortDescription) {
	  	  this.complaintArabicShortDescription = complaintArabicShortDescription;
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
	  public Boolean getBooSaveOrExit() {
	  	  return booSaveOrExit;
	  }
	  public void setBooSaveOrExit(Boolean booSaveOrExit) {
	  	  this.booSaveOrExit = booSaveOrExit;
	  }
	  public Boolean getBooRenderDataTable() {
	  	  return booRenderDataTable;
	  }
	  public void setBooRenderDataTable(Boolean booRenderDataTable) {
	  	  this.booRenderDataTable = booRenderDataTable;
	  }
	  public Boolean getRenderEditButton() {
	  	  return renderEditButton;
	  }
	  public void setRenderEditButton(Boolean renderEditButton) {
	  	  this.renderEditButton = renderEditButton;
	  }
	  public String getDynamicLabelForActivateDeactivate() {
	  	  return dynamicLabelForActivateDeactivate;
	  }
	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
	  	  this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }

	  public String getLogCompalint() {
	  	  return logCompalint;
	  }
	  public void setLogCompalint(String logCompalint) {
	  	  this.logCompalint = logCompalint;
	  }
	  public Boolean getBooEditButton() {
	  	  return booEditButton;
	  }
	  public void setBooEditButton(Boolean booEditButton) {
	  	  this.booEditButton = booEditButton;
	  }
	  public Boolean getBooSubmitPanel() {
	  	  return booSubmitPanel;
	  }
	  public void setBooSubmitPanel(Boolean booSubmitPanel) {
	  	  this.booSubmitPanel = booSubmitPanel;
	  }
	  public Boolean getBooClearPanel() {
	  	  return booClearPanel;
	  }
	  public void setBooClearPanel(Boolean booClearPanel) {
	  	  this.booClearPanel = booClearPanel;
	  }
	  public BigDecimal getComplaintEnglishFullDescriptionPK() {
	  	  return complaintEnglishFullDescriptionPK;
	  }
	  public void setComplaintEnglishFullDescriptionPK(BigDecimal complaintEnglishFullDescriptionPK) {
	  	  this.complaintEnglishFullDescriptionPK = complaintEnglishFullDescriptionPK;
	  }
	  public BigDecimal getComplaintEnglishShortDescriptionPK() {
	  	  return complaintEnglishShortDescriptionPK;
	  }
	  public void setComplaintEnglishShortDescriptionPK(BigDecimal complaintEnglishShortDescriptionPK) {
	  	  this.complaintEnglishShortDescriptionPK = complaintEnglishShortDescriptionPK;
	  }
	  public BigDecimal getComplaintArabicFullDescriptionPK() {
	  	  return complaintArabicFullDescriptionPK;
	  }
	  public void setComplaintArabicFullDescriptionPK(BigDecimal complaintArabicFullDescriptionPK) {
	  	  this.complaintArabicFullDescriptionPK = complaintArabicFullDescriptionPK;
	  }
	  public BigDecimal getComplaintArabicShortDescriptionPK() {
	  	  return complaintArabicShortDescriptionPK;
	  }
	  public void setComplaintArabicShortDescriptionPK(BigDecimal complaintArabicShortDescriptionPK) {
	  	  this.complaintArabicShortDescriptionPK = complaintArabicShortDescriptionPK;
	  }
	  public Boolean getPermanetDeleteCheck() {
	  	  return permanetDeleteCheck;
	  }
	  public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
	  	  this.permanetDeleteCheck = permanetDeleteCheck;
	  }
	  public Boolean getRemarksCheck() {
	  	  return remarksCheck;
	  }
	  public void setRemarksCheck(Boolean remarksCheck) {
	  	  this.remarksCheck = remarksCheck;
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
	  public Boolean getBooAdd() {
	  	  return booAdd;
	  }
	  public void setBooAdd(Boolean booAdd) {
	  	  this.booAdd = booAdd;
	  }
	  public Boolean getBooApproval() {
	  	  return booApproval;
	  }
	  public void setBooApproval(Boolean booApproval) {
	  	  this.booApproval = booApproval;
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
	  public Boolean getBooReadOnly() {
	  	  return booReadOnly;
	  }
	  public void setBooReadOnly(Boolean booReadOnly) {
	  	  this.booReadOnly = booReadOnly;
	  }
	  public String getLogCompalintId() {
	  	  return logCompalintId;
	  }
	  public void setLogCompalintId(String logCompalintId) {
	  	  this.logCompalintId = logCompalintId;
	  }
	public String getComplaintAssignedTo() {
		return complaintAssignedTo;
	}
	public void setComplaintAssignedTo(String complaintAssignedTo) {
		this.complaintAssignedTo = complaintAssignedTo;
	}
	  
	  
	  
	  
}
