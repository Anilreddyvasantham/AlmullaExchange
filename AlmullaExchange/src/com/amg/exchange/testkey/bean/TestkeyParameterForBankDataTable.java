package com.amg.exchange.testkey.bean;

import java.math.BigDecimal;
import java.util.Date;

public class TestkeyParameterForBankDataTable {
	  
	  private BigDecimal testKeyParameterPk;
	  private BigDecimal applicationCountryId;
	  private BigDecimal bankId;
	  private String bankName;
	  private String bankCode;
	  private String bankDescription;
	  private String accountNo;
	  private String accountDescription;
	  private BigDecimal currencyId;
	  private String currencyName;
	  private BigDecimal branchCode;
	  private String sendReceiveIndicator;
	  private BigDecimal calculationOrderNo;
	  private String calculationType;
	  private String primaryTestKey;
	  private String secondaryTestKey;
	  private String seconaryIndicator;
	  private String recordStatus;
	  private String serialIndicator;
	  private String serialIndicatorForSave;
	  private String calculationTypeForSave;
	  private String endOfSerial;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private Boolean renderEditButton;
	  private String dynamicLabelForActivateDeactivate;
	  private String accountNumber;
	  private String branchName;
	  private String isActive;
	  private String remarks;
	  private String approvedBy;
	  private Date approvedDate;
	  private String primaryTestKeyName;
	  private String countryBranchId;
	  private BigDecimal bankBranchCode;
	  private String primaryTestKeyParameterName;
	  private String secondaryTestKeyParameterName;
	  

	  //boolean Variables
	  private Boolean booRenderDataTable=false;
	  private Boolean booSaveOrExit=false;
	  private Boolean booClearPanel=false;
	  private Boolean booEditButton=false;
	  private Boolean remarksCheck=false;
	  private Boolean activeRecordCheck=false;
	  private Boolean permentDeleteCheck=false;
	  private Boolean ifEditClicked=false;
	  
	public BigDecimal getTestKeyParameterPk() {
	  	  return testKeyParameterPk;
	  }
	  public void setTestKeyParameterPk(BigDecimal testKeyParameterPk) {
	  	  this.testKeyParameterPk = testKeyParameterPk;
	  }
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
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
	  public String getBankDescription() {
	  	  return bankDescription;
	  }
	  public void setBankDescription(String bankDescription) {
	  	  this.bankDescription = bankDescription;
	  }
	  public String getAccountNo() {
	  	  return accountNo;
	  }
	  public void setAccountNo(String accountNo) {
	  	  this.accountNo = accountNo;
	  }
	  public String getAccountDescription() {
	  	  return accountDescription;
	  }
	  public void setAccountDescription(String accountDescription) {
	  	  this.accountDescription = accountDescription;
	  }
	  public BigDecimal getCurrencyId() {
	  	  return currencyId;
	  }
	  public void setCurrencyId(BigDecimal currencyId) {
	  	  this.currencyId = currencyId;
	  }
	  public String getCurrencyName() {
	  	  return currencyName;
	  }
	  public void setCurrencyName(String currencyName) {
	  	  this.currencyName = currencyName;
	  }
	  public BigDecimal getBranchCode() {
	  	  return branchCode;
	  }
	  public void setBranchCode(BigDecimal branchCode) {
	  	  this.branchCode = branchCode;
	  }
	  public String getSendReceiveIndicator() {
	  	  return sendReceiveIndicator;
	  }
	  public void setSendReceiveIndicator(String sendReceiveIndicator) {
	  	  this.sendReceiveIndicator = sendReceiveIndicator;
	  }
	  public BigDecimal getCalculationOrderNo() {
	  	  return calculationOrderNo;
	  }
	  public void setCalculationOrderNo(BigDecimal calculationOrderNo) {
	  	  this.calculationOrderNo = calculationOrderNo;
	  }
	  public String getCalculationType() {
	  	  return calculationType;
	  }
	  public void setCalculationType(String calculationType) {
	  	  this.calculationType = calculationType;
	  }
	  public String getPrimaryTestKey() {
	  	  return primaryTestKey;
	  }
	  public void setPrimaryTestKey(String primaryTestKey) {
	  	  this.primaryTestKey = primaryTestKey;
	  }
	  public String getSecondaryTestKey() {
	  	  return secondaryTestKey;
	  }
	  public void setSecondaryTestKey(String secondaryTestKey) {
	  	  this.secondaryTestKey = secondaryTestKey;
	  }
	  public String getRecordStatus() {
	  	  return recordStatus;
	  }
	  public void setRecordStatus(String recordStatus) {
	  	  this.recordStatus = recordStatus;
	  }
	  public String getSerialIndicator() {
	  	  return serialIndicator;
	  }
	  public void setSerialIndicator(String serialIndicator) {
	  	  this.serialIndicator = serialIndicator;
	  }
	  public String getEndOfSerial() {
	  	  return endOfSerial;
	  }
	  public void setEndOfSerial(String endOfSerial) {
	  	  this.endOfSerial = endOfSerial;
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
	  public Boolean getBooRenderDataTable() {
	  	  return booRenderDataTable;
	  }
	  public void setBooRenderDataTable(Boolean booRenderDataTable) {
	  	  this.booRenderDataTable = booRenderDataTable;
	  }
	  public Boolean getBooSaveOrExit() {
	  	  return booSaveOrExit;
	  }
	  public void setBooSaveOrExit(Boolean booSaveOrExit) {
	  	  this.booSaveOrExit = booSaveOrExit;
	  }
	  public String getSeconaryIndivator() {
	  	  return seconaryIndicator;
	  }
	  public void setSeconaryIndicator(String seconaryIndicator) {
	  	  this.seconaryIndicator = seconaryIndicator;
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
	  public String getBankName() {
	  	  return bankName;
	  }
	  public void setBankName(String bankName) {
	  	  this.bankName = bankName;
	  }
	  public String getAccountNumber() {
	  	  return accountNumber;
	  }
	  public void setAccountNumber(String accountNumber) {
	  	  this.accountNumber = accountNumber;
	  }
	  public String getBranchName() {
	  	  return branchName;
	  }
	  public void setBranchName(String branchName) {
	  	  this.branchName = branchName;
	  }
	  public String getIsActive() {
	  	  return isActive;
	  }
	  public void setIsActive(String isActive) {
	  	  this.isActive = isActive;
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
	  public String getPrimaryTestKeyName() {
	  	  return primaryTestKeyName;
	  }
	  public void setPrimaryTestKeyName(String primaryTestKeyName) {
	  	  this.primaryTestKeyName = primaryTestKeyName;
	  }
	  public String getRemarks() {
	  	  return remarks;
	  }
	  public void setRemarks(String remarks) {
	  	  this.remarks = remarks;
	  }
	  public Boolean getBooClearPanel() {
	  	  return booClearPanel;
	  }
	  public void setBooClearPanel(Boolean booClearPanel) {
	  	  this.booClearPanel = booClearPanel;
	  }
	  public Boolean getBooEditButton() {
	  	  return booEditButton;
	  }
	  public void setBooEditButton(Boolean booEditButton) {
	  	  this.booEditButton = booEditButton;
	  }
	  public String getCountryBranchId() {
	  	  return countryBranchId;
	  }
	  public void setCountryBranchId(String countryBranchId) {
	  	  this.countryBranchId = countryBranchId;
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
	  public Boolean getIfEditClicked() {
	  	  return ifEditClicked;
	  }
	  public void setIfEditClicked(Boolean ifEditClicked) {
	  	  this.ifEditClicked = ifEditClicked;
	  }
	  public BigDecimal getBankBranchCode() {
	  	  return bankBranchCode;
	  }
	  public void setBankBranchCode(BigDecimal bankBranchCode) {
	  	  this.bankBranchCode = bankBranchCode;
	  }
	  public String getPrimaryTestKeyParameterName() {
	  	  return primaryTestKeyParameterName;
	  }
	  public void setPrimaryTestKeyParameterName(String primaryTestKeyParameterName) {
	  	  this.primaryTestKeyParameterName = primaryTestKeyParameterName;
	  }
	  public String getSecondaryTestKeyParameterName() {
	  	  return secondaryTestKeyParameterName;
	  }
	  public void setSecondaryTestKeyParameterName(String secondaryTestKeyParameterName) {
	  	  this.secondaryTestKeyParameterName = secondaryTestKeyParameterName;
	  }
	public String getSerialIndicatorForSave() {
		return serialIndicatorForSave;
	}
	public void setSerialIndicatorForSave(String serialIndicatorForSave) {
		this.serialIndicatorForSave = serialIndicatorForSave;
	}
	public String getCalculationTypeForSave() {
		return calculationTypeForSave;
	}
	public void setCalculationTypeForSave(String calculationTypeForSave) {
		this.calculationTypeForSave = calculationTypeForSave;
	}
	 
	  
	  
}
