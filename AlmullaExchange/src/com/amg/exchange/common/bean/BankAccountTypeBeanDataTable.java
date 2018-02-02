package com.amg.exchange.common.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BankAccountTypeBeanDataTable {
	
	private String accountTypeCode;
	private String accountTypeDescLocal;
	private String accountTypeDesc;
	private BigDecimal accountTypePk;
	private BigDecimal accountTypeDescPk;
	private BigDecimal accountTypeLocalDescPk;
	private BigDecimal engLanguageId;
	private BigDecimal arbLanguageId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActive;
	private Boolean remarksCheck;
	private String dynamicLabelForActivateDeactivate;
	private Boolean permanetDeleteCheck=false;
	private Boolean ifEditClicked=false;
	private Boolean activateRecordCheck=false;
	
	private String statusCheck;
	
	
	
	public String getStatusCheck() {
		return statusCheck;
	}
	public void setStatusCheck(String statusCheck) {
		this.statusCheck = statusCheck;
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
	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}
	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
	}
	public BigDecimal getEngLanguageId() {
		return engLanguageId;
	}
	public void setEngLanguageId(BigDecimal engLanguageId) {
		this.engLanguageId = engLanguageId;
	}
	public BigDecimal getArbLanguageId() {
		return arbLanguageId;
	}
	public void setArbLanguageId(BigDecimal arbLanguageId) {
		this.arbLanguageId = arbLanguageId;
	}
	public BigDecimal getAccountTypeLocalDescPk() {
		return accountTypeLocalDescPk;
	}
	public void setAccountTypeLocalDescPk(BigDecimal accountTypeLocalDescPk) {
		this.accountTypeLocalDescPk = accountTypeLocalDescPk;
	}
	public Boolean getRemarksCheck() {
		return remarksCheck;
	}
	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getAccountTypePk() {
		return accountTypePk;
	}
	public void setAccountTypePk(BigDecimal accountTypePk) {
		this.accountTypePk = accountTypePk;
	}
	public BigDecimal getAccountTypeDescPk() {
		return accountTypeDescPk;
	}
	public void setAccountTypeDescPk(BigDecimal accountTypeDescPk) {
		this.accountTypeDescPk = accountTypeDescPk;
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
	public String getAccountTypeCode() {
		return accountTypeCode;
	}
	public void setAccountTypeCode(String accountTypeCode) {
		this.accountTypeCode = accountTypeCode;
	}
	public String getAccountTypeDescLocal() {
		return accountTypeDescLocal;
	}
	public void setAccountTypeDescLocal(String accountTypeDescLocal) {
		this.accountTypeDescLocal = accountTypeDescLocal;
	}
	public String getAccountTypeDesc() {
		return accountTypeDesc;
	}
	public void setAccountTypeDesc(String accountTypeDesc) {
		this.accountTypeDesc = accountTypeDesc;
	}
	
	
	

}
