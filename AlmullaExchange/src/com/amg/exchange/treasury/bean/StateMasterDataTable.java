package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class StateMasterDataTable {

	  private BigDecimal stateId;
	  private String stateCode;
	  private BigDecimal countryId;
	  private String countryName;
	  private String isActive;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;
	  private String englishStateName;
	  private String arabicStateName;
	  private String dynamicLabelForActivateDeactivate;
	  private Boolean renderEditButton = false;
	  private BigDecimal stateMasterPK;
	  private BigDecimal englishStateDescPK;
	  private BigDecimal arabicStateDescPK;
	  private BigDecimal engLanuageId;
	  private BigDecimal ardLanguageId;
	  private Boolean remarksChecks;
	  private boolean booEditButton;
	  private String countryCode;
	  private String checkStatus;
	  private Boolean permanetDeleteCheck = false;
	  private Boolean activateRecordCheck = false;

	  private Boolean remarksCheck = false;
	  private Boolean activeRecordCheck = false;
	  private Boolean permentDeleteCheck = false;

	  public BigDecimal getStateId() {
		    return stateId;
	  }

	  public void setStateId(BigDecimal stateId) {
		    this.stateId = stateId;
	  }

	  public String getStateCode() {
		    return stateCode;
	  }

	  public void setStateCode(String stateCode) {
		    this.stateCode = stateCode;
	  }

	  public BigDecimal getCountryId() {
		    return countryId;
	  }

	  public void setCountryId(BigDecimal countryId) {
		    this.countryId = countryId;
	  }

	  public String getCountryName() {
		    return countryName;
	  }

	  public void setCountryName(String countryName) {
		    this.countryName = countryName;
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

	  public String getEnglishStateName() {
		    return englishStateName;
	  }

	  public void setEnglishStateName(String englishStateName) {
		    this.englishStateName = englishStateName;
	  }

	  public String getArabicStateName() {
		    return arabicStateName;
	  }

	  public void setArabicStateName(String arabicStateName) {
		    this.arabicStateName = arabicStateName;
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

	  public BigDecimal getStateMasterPK() {
		    return stateMasterPK;
	  }

	  public void setStateMasterPK(BigDecimal stateMasterPK) {
		    this.stateMasterPK = stateMasterPK;
	  }

	  public BigDecimal getEnglishStateDescPK() {
		    return englishStateDescPK;
	  }

	  public void setEnglishStateDescPK(BigDecimal englishStateDescPK) {
		    this.englishStateDescPK = englishStateDescPK;
	  }

	  public BigDecimal getArabicStateDescPK() {
		    return arabicStateDescPK;
	  }

	  public void setArabicStateDescPK(BigDecimal arabicStateDescPK) {
		    this.arabicStateDescPK = arabicStateDescPK;
	  }

	  public BigDecimal getEngLanuageId() {
		    return engLanuageId;
	  }

	  public void setEngLanuageId(BigDecimal engLanuageId) {
		    this.engLanuageId = engLanuageId;
	  }

	  public BigDecimal getArdLanguageId() {
		    return ardLanguageId;
	  }

	  public void setArdLanguageId(BigDecimal ardLanguageId) {
		    this.ardLanguageId = ardLanguageId;
	  }

	  public Boolean getRemarksChecks() {
		    return remarksChecks;
	  }

	  public void setRemarksChecks(Boolean remarksChecks) {
		    this.remarksChecks = remarksChecks;
	  }

	  public boolean isBooEditButton() {
		    return booEditButton;
	  }

	  public void setBooEditButton(boolean booEditButton) {
		    this.booEditButton = booEditButton;
	  }

	  public String getCountryCode() {
		    return countryCode;
	  }

	  public void setCountryCode(String countryCode) {
		    this.countryCode = countryCode;
	  }

	  public String getCheckStatus() {
		    return checkStatus;
	  }

	  public void setCheckStatus(String checkStatus) {
		    this.checkStatus = checkStatus;
	  }

	  public Boolean getPermanetDeleteCheck() {
		    return permanetDeleteCheck;
	  }

	  public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		    this.permanetDeleteCheck = permanetDeleteCheck;
	  }

	  public Boolean getActivateRecordCheck() {
		    return activateRecordCheck;
	  }

	  public void setActivateRecordCheck(Boolean activateRecordCheck) {
		    this.activateRecordCheck = activateRecordCheck;
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
