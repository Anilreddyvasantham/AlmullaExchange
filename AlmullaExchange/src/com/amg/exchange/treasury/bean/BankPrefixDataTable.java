package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankPrefixDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String bankCode;
	private String bankName;
	private String bankPrefix;
	private String dynamicLabelForActivateDeactivate;
	private BigDecimal bankPrefixPkId;
	private BigDecimal bankId;
	private String isActive;
 	private String remarks;
 	private String createdBy;
 	private Date createdDate;
 	private String modifiedBy;
 	private Date modifiedDate;
 	private String approvedBy;
 	private Date approvedDate;
 	private Boolean checkSave;
	private Boolean renderEditButton = false;
	private Boolean remarkCheck;
	private Boolean booCheckUpdate;
	private Boolean booCheckDelete;
	private String currenctStatus;
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankPrefix() {
		return bankPrefix;
	}
	public void setBankPrefix(String bankPrefix) {
		this.bankPrefix = bankPrefix;
	}
	
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	
	public BigDecimal getBankPrefixPkId() {
		return bankPrefixPkId;
	}
	public void setBankPrefixPkId(BigDecimal bankPrefixPkId) {
		this.bankPrefixPkId = bankPrefixPkId;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
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
	
	public Boolean getCheckSave() {
		return checkSave;
	}
	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}
	
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}
	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	
	public Boolean getRemarkCheck() {
		return remarkCheck;
	}
	public void setRemarkCheck(Boolean remarkCheck) {
		this.remarkCheck = remarkCheck;
	}
	
	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}
	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}
	
	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}
	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}
	
	public String getCurrenctStatus() {
		return currenctStatus;
	}
	public void setCurrenctStatus(String currenctStatus) {
		this.currenctStatus = currenctStatus;
	}

}
