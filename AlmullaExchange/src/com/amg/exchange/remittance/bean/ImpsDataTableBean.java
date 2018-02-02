package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ImpsDataTableBean {
	
	private BigDecimal impsPk;
	private String corrspBankCode;
	private String beneBankCode;
	private BigDecimal beneBankId;
	private BigDecimal correspBankId;
	private BigDecimal  applicationCountryId;
	
	private String correspBankDesc;
	private String beneBankDesc;
	
	private String createdBy;
	private String modifiedBy;
	private String approvedBy;
	private Date createdDate;
	private Date modifiedDate;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	private String dynamicLabelForActivateDeactivate;
	private Boolean booCheckDelete;
	private Boolean booActivate;
	private Boolean remarkCheck;
	private Boolean checkSave=false;
	private Boolean booRenderDataTable;
	private Boolean booRenderSubmitPanel;
	private Boolean activateRecordCheck=false;
	private Boolean permanentDeleteCheck;
	private Boolean booUpdate;
	
	
	
	public String getCorrspBankCode() {
		return corrspBankCode;
	}
	public void setCorrspBankCode(String corrspBankCode) {
		this.corrspBankCode = corrspBankCode;
	}
	public String getBeneBankCode() {
		return beneBankCode;
	}
	public void setBeneBankCode(String beneBankCode) {
		this.beneBankCode = beneBankCode;
	}
	public BigDecimal getBeneBankId() {
		return beneBankId;
	}
	public void setBeneBankId(BigDecimal beneBankId) {
		this.beneBankId = beneBankId;
	}
	public BigDecimal getCorrespBankId() {
		return correspBankId;
	}
	public void setCorrespBankId(BigDecimal correspBankId) {
		this.correspBankId = correspBankId;
	}
	public String getCorrespBankDesc() {
		return correspBankDesc;
	}
	public void setCorrespBankDesc(String correspBankDesc) {
		this.correspBankDesc = correspBankDesc;
	}
	public String getBeneBankDesc() {
		return beneBankDesc;
	}
	public void setBeneBankDesc(String beneBankDesc) {
		this.beneBankDesc = beneBankDesc;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getImpsPk() {
		return impsPk;
	}
	public void setImpsPk(BigDecimal impsPk) {
		this.impsPk = impsPk;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}
	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}
	public Boolean getBooActivate() {
		return booActivate;
	}
	public void setBooActivate(Boolean booActivate) {
		this.booActivate = booActivate;
	}
	public Boolean getRemarkCheck() {
		return remarkCheck;
	}
	public void setRemarkCheck(Boolean remarkCheck) {
		this.remarkCheck = remarkCheck;
	}
	public Boolean getCheckSave() {
		return checkSave;
	}
	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	public Boolean getBooRenderSubmitPanel() {
		return booRenderSubmitPanel;
	}
	public void setBooRenderSubmitPanel(Boolean booRenderSubmitPanel) {
		this.booRenderSubmitPanel = booRenderSubmitPanel;
	}
	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}
	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}
	public Boolean getPermanentDeleteCheck() {
		return permanentDeleteCheck;
	}
	public void setPermanentDeleteCheck(Boolean permanentDeleteCheck) {
		this.permanentDeleteCheck = permanentDeleteCheck;
	}
	public Boolean getBooUpdate() {
		return booUpdate;
	}
	public void setBooUpdate(Boolean booUpdate) {
		this.booUpdate = booUpdate;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	
	
	
	
	
}
