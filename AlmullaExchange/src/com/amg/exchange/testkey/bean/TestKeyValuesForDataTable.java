package com.amg.exchange.testkey.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TestKeyValuesForDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal testKeyValueId;
	private BigDecimal bankId;
	private String bankCode;
	private String bankDescription;
	private BigDecimal bankbranchId;
	private BigDecimal bankbranchCode;
	private String bankbranchDescription;
	private BigDecimal accountNoId;
	private String accountNo;
	private String accountDescription;
	private BigDecimal currencyId;
	private String currencyCode;
	private String currencyName;
	private String sendReceiveIndicator;
	private String sendReceiveFullName;
	private BigDecimal calculationOrderNo;
	private String primaryTestKeyCode;
	private String primaryTestKeyCodeFullName;
	private BigDecimal primaryTestKeyValue;
	private BigDecimal secondaryTestKeyCode;
	private BigDecimal secondaryTestKeyCodeValue;
	private String status;
	private String statusFullName;
	private BigDecimal keyValue;
	private String createdBy;
	private Date createdDate;
	private String isActive;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActiveStatus;
	
	//boolean Variables
	private Boolean booRenderDataTable=false;
	private Boolean booSaveOrExit=false;
	private Boolean booClearPanel=false;
	private Boolean booEditButton=false;
	private Boolean remarksCheck=false;
	private Boolean activeRecordCheck=false;
	private Boolean permentDeleteCheck=false;
	
	
	public BigDecimal getTestKeyValueId() {
		return testKeyValueId;
	}
	public void setTestKeyValueId(BigDecimal testKeyValueId) {
		this.testKeyValueId = testKeyValueId;
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
	
	public BigDecimal getBankbranchId() {
		return bankbranchId;
	}
	public void setBankbranchId(BigDecimal bankbranchId) {
		this.bankbranchId = bankbranchId;
	}
	
	public BigDecimal getBankbranchCode() {
		return bankbranchCode;
	}
	public void setBankbranchCode(BigDecimal bankbranchCode) {
		this.bankbranchCode = bankbranchCode;
	}
	
	public String getBankbranchDescription() {
		return bankbranchDescription;
	}
	public void setBankbranchDescription(String bankbranchDescription) {
		this.bankbranchDescription = bankbranchDescription;
	}
	
	public BigDecimal getAccountNoId() {
		return accountNoId;
	}
	public void setAccountNoId(BigDecimal accountNoId) {
		this.accountNoId = accountNoId;
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
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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
	
	public String getPrimaryTestKeyCode() {
		return primaryTestKeyCode;
	}
	public void setPrimaryTestKeyCode(String primaryTestKeyCode) {
		this.primaryTestKeyCode = primaryTestKeyCode;
	}
	
	public BigDecimal getPrimaryTestKeyValue() {
		return primaryTestKeyValue;
	}
	public void setPrimaryTestKeyValue(BigDecimal primaryTestKeyValue) {
		this.primaryTestKeyValue = primaryTestKeyValue;
	}
	
	public BigDecimal getSecondaryTestKeyCode() {
		return secondaryTestKeyCode;
	}
	public void setSecondaryTestKeyCode(BigDecimal secondaryTestKeyCode) {
		this.secondaryTestKeyCode = secondaryTestKeyCode;
	}
	
	public BigDecimal getSecondaryTestKeyCodeValue() {
		return secondaryTestKeyCodeValue;
	}
	public void setSecondaryTestKeyCodeValue(BigDecimal secondaryTestKeyCodeValue) {
		this.secondaryTestKeyCodeValue = secondaryTestKeyCodeValue;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigDecimal getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(BigDecimal keyValue) {
		this.keyValue = keyValue;
	}
	
	public String getSendReceiveFullName() {
		return sendReceiveFullName;
	}
	public void setSendReceiveFullName(String sendReceiveFullName) {
		this.sendReceiveFullName = sendReceiveFullName;
	}
	
	public String getPrimaryTestKeyCodeFullName() {
		return primaryTestKeyCodeFullName;
	}
	public void setPrimaryTestKeyCodeFullName(String primaryTestKeyCodeFullName) {
		this.primaryTestKeyCodeFullName = primaryTestKeyCodeFullName;
	}
	
	public String getStatusFullName() {
		return statusFullName;
	}
	public void setStatusFullName(String statusFullName) {
		this.statusFullName = statusFullName;
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
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	
	public String getIsActiveStatus() {
		return isActiveStatus;
	}
	public void setIsActiveStatus(String isActiveStatus) {
		this.isActiveStatus = isActiveStatus;
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
