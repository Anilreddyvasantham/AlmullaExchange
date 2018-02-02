package com.amg.exchange.common.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PurposeOfTransctionDataTable {

	  private BigDecimal purposeOfTransctionPk;
	  private BigDecimal companyId;
	  private String CompanyName;
	  private BigDecimal applicationCountryId;
	  private String purposeOfCode;
	  private String englishFullDesc;
	  private String englishShortDesc;
	  private String arabicFullDesc;
	  private String arablicShortDesc;
	  // common Variables
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String isActive;
	  private String remarks;
	  private String dynamicLabelForActivateDeactivate;
	  //boolean
	  private Boolean renderEditButton=false;
	  private Boolean booEditButton = false;
	  private Boolean ifEditClicked=false;
	  private Boolean remarksCheck=false;
	  private Boolean activeRecordCheck=false;
	  private Boolean permentDeleteCheck=false;
	  public BigDecimal getPurposeOfTransctionPk() {
	  	  return purposeOfTransctionPk;
	  }
	  public void setPurposeOfTransctionPk(BigDecimal purposeOfTransctionPk) {
	  	  this.purposeOfTransctionPk = purposeOfTransctionPk;
	  }
	  public BigDecimal getCompanyId() {
	  	  return companyId;
	  }
	  public void setCompanyId(BigDecimal companyId) {
	  	  this.companyId = companyId;
	  }
	  public String getCompanyName() {
	  	  return CompanyName;
	  }
	  public void setCompanyName(String companyName) {
	  	  CompanyName = companyName;
	  }
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  public String getPurposeOfCode() {
	  	  return purposeOfCode;
	  }
	  public void setPurposeOfCode(String purposeOfCode) {
	  	  this.purposeOfCode = purposeOfCode;
	  }
	  public String getEnglishFullDesc() {
	  	  return englishFullDesc;
	  }
	  public void setEnglishFullDesc(String englishFullDesc) {
	  	  this.englishFullDesc = englishFullDesc;
	  }
	  public String getEnglishShortDesc() {
	  	  return englishShortDesc;
	  }
	  public void setEnglishShortDesc(String englishShortDesc) {
	  	  this.englishShortDesc = englishShortDesc;
	  }
	  public String getArabicFullDesc() {
	  	  return arabicFullDesc;
	  }
	  public void setArabicFullDesc(String arabicFullDesc) {
	  	  this.arabicFullDesc = arabicFullDesc;
	  }
	  public String getArablicShortDesc() {
	  	  return arablicShortDesc;
	  }
	  public void setArablicShortDesc(String arablicShortDesc) {
	  	  this.arablicShortDesc = arablicShortDesc;
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
	  public String getDynamicLabelForActivateDeactivate() {
	  	  return dynamicLabelForActivateDeactivate;
	  }
	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
	  	  this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }
	  public Boolean getRenderEditButton() {
	  	  return renderEditButton;
	  }
	  public void setRenderEditButton(Boolean renderEditButton) {
	  	  this.renderEditButton = renderEditButton;
	  }
	  public Boolean getBooEditButton() {
	  	  return booEditButton;
	  }
	  public void setBooEditButton(Boolean booEditButton) {
	  	  this.booEditButton = booEditButton;
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
	  public Boolean getActiveRecordCheck() {
	  	  return activeRecordCheck;
	  }
	  public void setActiveRecordCheck(Boolean activeRecordCheck) {
	  	  this.activeRecordCheck = activeRecordCheck;
	  }
	  public Boolean getPermentDeleteCheck() {
	  	  return permentDeleteCheck;
	  }
	  public void setPermentDeleteCheck(Boolean permentDeleteCheck) {
	  	  this.permentDeleteCheck = permentDeleteCheck;
	  }
	  
	  
	  
}
