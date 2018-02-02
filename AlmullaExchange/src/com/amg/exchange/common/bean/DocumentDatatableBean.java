package com.amg.exchange.common.bean;

import java.math.BigDecimal;
import java.util.Date;

public class DocumentDatatableBean {
	
	private String createdBy;
	private Date createdDate;
	private String remarks;
	private Date modifiedDate;
	private String modifiedBy;
	private String approvedBy;
	private Date approvedDate;
	private String dynamicActivateDeactivate;
	
	private String isActive;
	private BigDecimal documentCode;
	private String documentDescription;
	private BigDecimal documentMasterPk;
	
	private Boolean hideEditButton;
	private Boolean checkSave;
	private Boolean booCheckDelete;
	private Boolean remarkCheck;
	private Boolean booCheckUpdate;
	private Boolean booRenderEditButton;
	private Boolean booActivate;
	
	
	
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	public BigDecimal getDocumentMasterPk() {
		return documentMasterPk;
	}
	public void setDocumentMasterPk(BigDecimal documentMasterPk) {
		this.documentMasterPk = documentMasterPk;
	}
	public Boolean getHideEditButton() {
		return hideEditButton;
	}
	public void setHideEditButton(Boolean hideEditButton) {
		this.hideEditButton = hideEditButton;
	}
	public String getDocumentDescription() {
		return documentDescription;
	}
	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}
	public String getDynamicActivateDeactivate() {
		return dynamicActivateDeactivate;
	}
	public void setDynamicActivateDeactivate(String dynamicActivateDeactivate) {
		this.dynamicActivateDeactivate = dynamicActivateDeactivate;
	}
	public Boolean getCheckSave() {
		return checkSave;
	}
	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}
	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}
	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
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
	public Boolean getBooRenderEditButton() {
		return booRenderEditButton;
	}
	public void setBooRenderEditButton(Boolean booRenderEditButton) {
		this.booRenderEditButton = booRenderEditButton;
	}
	public Boolean getBooActivate() {
		return booActivate;
	}
	public void setBooActivate(Boolean booActivate) {
		this.booActivate = booActivate;
	}
	
	
	
	

}
