package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BankFieldMappingDataTable {

	  private BigDecimal bankFieldMappingId = null;
	  private String tableName;
	  private String fieldName;
	  private String fieldvalue;
	  private BigDecimal bankId = null;
	  private String bankName;
	  private String bankValue;
	  private String bankValueDesc;
	  private BigDecimal additionalBankRuleId = null;
	  private String additionalBankRuleName;
	  private BigDecimal applicationCountryId;
	  private String dynamicLabelForActivateDeactivate;
	  // common Variables
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String isActive;
	  private String remarks=null;

	  // boolean
	  private Boolean renderEditButton = false;
	  private Boolean booEditButton = false;
	  private Boolean ifEditClicked = false;
	  private Boolean remarksCheck = false;
	  private Boolean activeRecordCheck = false;
	  private Boolean permentDeleteCheck = false;

	  public BigDecimal getBankFieldMappingId() {
		    return bankFieldMappingId;
	  }

	  public void setBankFieldMappingId(BigDecimal bankFieldMappingId) {
		    this.bankFieldMappingId = bankFieldMappingId;
	  }

	  public String getTableName() {
		    return tableName;
	  }

	  public void setTableName(String tableName) {
		    this.tableName = tableName;
	  }

	  public String getFieldName() {
		    return fieldName;
	  }

	  public void setFieldName(String fieldName) {
		    this.fieldName = fieldName;
	  }

	  public String getFieldvalue() {
		    return fieldvalue;
	  }

	  public void setFieldvalue(String fieldvalue) {
		    this.fieldvalue = fieldvalue;
	  }

	  public BigDecimal getBankId() {
		    return bankId;
	  }

	  public void setBankId(BigDecimal bankId) {
		    this.bankId = bankId;
	  }

	  public String getBankValue() {
		    return bankValue;
	  }

	  public void setBankValue(String bankValue) {
		    this.bankValue = bankValue;
	  }

	  public String getBankValueDesc() {
		    return bankValueDesc;
	  }

	  public void setBankValueDesc(String bankValueDesc) {
		    this.bankValueDesc = bankValueDesc;
	  }

	  public BigDecimal getAdditionalBankRuleId() {
		    return additionalBankRuleId;
	  }

	  public void setAdditionalBankRuleId(BigDecimal additionalBankRuleId) {
		    this.additionalBankRuleId = additionalBankRuleId;
	  }

	  public BigDecimal getApplicationCountryId() {
		    return applicationCountryId;
	  }

	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
		    this.applicationCountryId = applicationCountryId;
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

	  public String getBankName() {
	  	  return bankName;
	  }

	  public void setBankName(String bankName) {
	  	  this.bankName = bankName;
	  }

	  public String getDynamicLabelForActivateDeactivate() {
	  	  return dynamicLabelForActivateDeactivate;
	  }

	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
	  	  this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }

	  public String getAdditionalBankRuleName() {
	  	  return additionalBankRuleName;
	  }

	  public void setAdditionalBankRuleName(String additionalBankRuleName) {
	  	  this.additionalBankRuleName = additionalBankRuleName;
	  }

	  
	  
}
