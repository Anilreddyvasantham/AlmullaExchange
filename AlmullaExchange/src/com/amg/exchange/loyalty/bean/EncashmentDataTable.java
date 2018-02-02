package com.amg.exchange.loyalty.bean;

import java.math.BigDecimal;
import java.util.Date;

public class EncashmentDataTable {

	  private BigDecimal encashmentPk;
	  private BigDecimal points;
	  private BigDecimal equivalentValue;
	  private String description;
	  private Date effectiveDateFrom;
	  private Date effectiveDateTo;
	  private String activeFlag;
	  private Date minDate;
	  private BigDecimal applicationCountryId;
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
	  // Boolean Variables
	 
	  private Boolean renderEditButton=false;
	  private Boolean booEditButton = false;
	  private Boolean ifEditClicked=false;
	  private Boolean remarksCheck=false;
	  private Boolean activeRecordCheck=false;
	  private Boolean permentDeleteCheck=false;
	  public BigDecimal getEncashmentPk() {
	  	  return encashmentPk;
	  }
	  public void setEncashmentPk(BigDecimal encashmentPk) {
	  	  this.encashmentPk = encashmentPk;
	  }
	  public BigDecimal getPoints() {
	  	  return points;
	  }
	  public void setPoints(BigDecimal points) {
	  	  this.points = points;
	  }
	  public BigDecimal getEquivalentValue() {
	  	  return equivalentValue;
	  }
	  public void setEquivalentValue(BigDecimal equivalentValue) {
	  	  this.equivalentValue = equivalentValue;
	  }
	  
	  public String getDescription() {
	  	  return description;
	  }
	  public void setDescription(String description) {
	  	  this.description = description;
	  }
	  public Date getEffectiveDateFrom() {
	  	  return effectiveDateFrom;
	  }
	  public void setEffectiveDateFrom(Date effectiveDateFrom) {
	  	  this.effectiveDateFrom = effectiveDateFrom;
	  }
	  public Date getEffectiveDateTo() {
	  	  return effectiveDateTo;
	  }
	  public void setEffectiveDateTo(Date effectiveDateTo) {
	  	  this.effectiveDateTo = effectiveDateTo;
	  }
	  public String getActiveFlag() {
	  	  return activeFlag;
	  }
	  public void setActiveFlag(String activeFlag) {
	  	  this.activeFlag = activeFlag;
	  }
	  public Date getMinDate() {
	  	  return minDate;
	  }
	  public void setMinDate(Date minDate) {
	  	  this.minDate = minDate;
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
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  
	  

}
