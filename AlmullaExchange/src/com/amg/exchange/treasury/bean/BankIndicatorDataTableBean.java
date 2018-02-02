package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BankIndicatorDataTableBean  {

 	private BigDecimal bankIndicatorId;
 	private BigDecimal bannIndicatorDecPk;
 	private BigDecimal bankIndicatorEnglishDecPk;
 	private BigDecimal bankIndicatorLocalDecPk;
 	private String bankIndicatorCode;
	//private String bankIndicatorName;
 	private String bankIndicatorDescInLocal ;
 	private String bankIndicatorDescInEnglish;
 	private String isActive;
 	private String remarks;
 	private String createdBy;
 	private Date createdDate;
 	private String modifiedBy;
 	private Date modifiedDate;
 	private String approvedBy;
 	private Date approvedDate;
 	
 	private Boolean checkSave;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton = false;
	private Boolean remarkCheck;
	private Boolean booCheckUpdate;
	
	
	private Boolean booCheckDelete;
	
	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}
 	
 	public BigDecimal getBankIndicatorId() {
		return bankIndicatorId;
	}
	public void setBankIndicatorId(BigDecimal bankIndicatorId) {
		this.bankIndicatorId = bankIndicatorId;
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
	public String getBankIndicatorCode() {
		return bankIndicatorCode;
	}
	public void setBankIndicatorCode(String bankIndicatorCode) {
		this.bankIndicatorCode = bankIndicatorCode;
	}
	public String getBankIndicatorDescInLocal() {
		return bankIndicatorDescInLocal;
	}
	public void setBankIndicatorDescInLocal(String bankIndicatorDescInLocal) {
		this.bankIndicatorDescInLocal = bankIndicatorDescInLocal;
	}
	public String getBankIndicatorDescInEnglish() {
		return bankIndicatorDescInEnglish;
	}
	public void setBankIndicatorDescInEnglish(String bankIndicatorDescInEnglish) {
		this.bankIndicatorDescInEnglish = bankIndicatorDescInEnglish;
	}
	public Boolean getCheckSave() {
		return checkSave;
	}
	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
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
	public BigDecimal getBannIndicatorDecPk() {
		return bannIndicatorDecPk;
	}
	public void setBannIndicatorDecPk(BigDecimal bannIndicatorDecPk) {
		this.bannIndicatorDecPk = bannIndicatorDecPk;
	}
	public BigDecimal getBankIndicatorEnglishDecPk() {
		return bankIndicatorEnglishDecPk;
	}
	public void setBankIndicatorEnglishDecPk(BigDecimal bankIndicatorEnglishDecPk) {
		this.bankIndicatorEnglishDecPk = bankIndicatorEnglishDecPk;
	}
	public BigDecimal getBankIndicatorLocalDecPk() {
		return bankIndicatorLocalDecPk;
	}
	public void setBankIndicatorLocalDecPk(BigDecimal bankIndicatorLocalDecPk) {
		this.bankIndicatorLocalDecPk = bankIndicatorLocalDecPk;
	}
	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}
	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}
	
 	

}
