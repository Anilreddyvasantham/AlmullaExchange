package com.amg.exchange.common.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BankBannedWordsMaintananceDataTableBean {
	
	private BigDecimal bankBannedPk;
	private String bankCode;
	private BigDecimal bankId;
	private String bankBannedName;
	private String createdBy;
	private String modifiedBy;
	private String approvedBy;
	private Date createdDate;
	private Date modifiedDate;
	private Date approvedDate;
	private BigDecimal countryId;
	private String countryCode;
	private String selectionMode;
	private String isActive;
	private Boolean modifyStatus=false;
	private String dynamicLabelActivateDeactivate;
	
	
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getBankBannedName() {
		return bankBannedName;
	}
	public void setBankBannedName(String bankBannedName) {
		this.bankBannedName = bankBannedName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getSelectionMode() {
		return selectionMode;
	}
	public void setSelectionMode(String selectionMode) {
		this.selectionMode = selectionMode;
	}
	public BigDecimal getBankBannedPk() {
		return bankBannedPk;
	}
	public void setBankBannedPk(BigDecimal bankBannedPk) {
		this.bankBannedPk = bankBannedPk;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Boolean getModifyStatus() {
		return modifyStatus;
	}
	public void setModifyStatus(Boolean modifyStatus) {
		this.modifyStatus = modifyStatus;
	}
	public String getDynamicLabelActivateDeactivate() {
		return dynamicLabelActivateDeactivate;
	}
	public void setDynamicLabelActivateDeactivate(
			String dynamicLabelActivateDeactivate) {
		this.dynamicLabelActivateDeactivate = dynamicLabelActivateDeactivate;
	}
	
	
	
	
	
	
	
	
	
	
}
