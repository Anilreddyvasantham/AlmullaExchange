package com.amg.exchange.common.bean;

import java.math.BigDecimal;
import java.util.Date;

public class InsuranceSetUpDataTableBean {
	
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	
	private String insuranceMsgOne;
	private String insuranceMsgTwo;
	private String insuranceArabicMsgOne;
	private String insuranceArabicMsgTwo;
	private String effectiveFromDate;
	private String effectiveToDate;
	
	private BigDecimal insuranceDays;
	private BigDecimal loyaltyPoints;
	private BigDecimal insuranceAmount;
	private BigDecimal insuranceAdditionalAmount;
	
	private BigDecimal insuranceSetUpPk;
	private BigDecimal insuranceMasterDescPk;
	private BigDecimal insuranceMasterDescArabicPk;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	private  Date approvedDate;
	private String approvedBy;
	private String isActive;
	private String remarks;
	
	
	//newly added start
	private String  dynamicActivateDeactivate;
	private Boolean booRenderEditButton;
	private Boolean booHideClear;
	private Boolean booHideEdit;
	private Boolean booRenderDataTable;
	private Boolean booCheckSave;
	private Boolean booCheckUpdate;
	private Boolean remarkCheck;
	private Boolean booActivate;
	private Boolean booCheckDelete;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	 
	
	
	
	
	public String getDynamicActivateDeactivate() {
		return dynamicActivateDeactivate;
	}

	public void setDynamicActivateDeactivate(String dynamicActivateDeactivate) {
		this.dynamicActivateDeactivate = dynamicActivateDeactivate;
	}

	public Boolean getBooRenderEditButton() {
		return booRenderEditButton;
	}

	public void setBooRenderEditButton(Boolean booRenderEditButton) {
		this.booRenderEditButton = booRenderEditButton;
	}

	public Boolean getBooHideClear() {
		return booHideClear;
	}

	public void setBooHideClear(Boolean booHideClear) {
		this.booHideClear = booHideClear;
	}

	public Boolean getBooHideEdit() {
		return booHideEdit;
	}

	public void setBooHideEdit(Boolean booHideEdit) {
		this.booHideEdit = booHideEdit;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public Boolean getBooCheckSave() {
		return booCheckSave;
	}

	public void setBooCheckSave(Boolean booCheckSave) {
		this.booCheckSave = booCheckSave;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}
	//newly added  end
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

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	public String getInsuranceMsgOne() {
		return insuranceMsgOne;
	}

	public void setInsuranceMsgOne(String insuranceMsgOne) {
		this.insuranceMsgOne = insuranceMsgOne;
	}

	public String getInsuranceMsgTwo() {
		return insuranceMsgTwo;
	}

	public void setInsuranceMsgTwo(String insuranceMsgTwo) {
		this.insuranceMsgTwo = insuranceMsgTwo;
	}

	public String getInsuranceArabicMsgOne() {
		return insuranceArabicMsgOne;
	}

	public void setInsuranceArabicMsgOne(String insuranceArabicMsgOne) {
		this.insuranceArabicMsgOne = insuranceArabicMsgOne;
	}

	public String getInsuranceArabicMsgTwo() {
		return insuranceArabicMsgTwo;
	}

	public void setInsuranceArabicMsgTwo(String insuranceArabicMsgTwo) {
		this.insuranceArabicMsgTwo = insuranceArabicMsgTwo;
	}
	 

	public BigDecimal getInsuranceDays() {
		return insuranceDays;
	}

	public void setInsuranceDays(BigDecimal insuranceDays) {
		this.insuranceDays = insuranceDays;
	}

	public BigDecimal getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getInsuranceAdditionalAmount() {
		return insuranceAdditionalAmount;
	}

	public void setInsuranceAdditionalAmount(BigDecimal insuranceAdditionalAmount) {
		this.insuranceAdditionalAmount = insuranceAdditionalAmount;
	}

	public BigDecimal getInsuranceSetUpPk() {
		return insuranceSetUpPk;
	}

	public void setInsuranceSetUpPk(BigDecimal insuranceSetUpPk) {
		this.insuranceSetUpPk = insuranceSetUpPk;
	}

	public String getEffectiveFromDate() {
		return effectiveFromDate;
	}

	public void setEffectiveFromDate(String effectiveFromDate) {
		this.effectiveFromDate = effectiveFromDate;
	}

	public String getEffectiveToDate() {
		return effectiveToDate;
	}

	public void setEffectiveToDate(String effectiveToDate) {
		this.effectiveToDate = effectiveToDate;
	}

	public BigDecimal getInsuranceMasterDescPk() {
		return insuranceMasterDescPk;
	}

	public void setInsuranceMasterDescPk(BigDecimal insuranceMasterDescPk) {
		this.insuranceMasterDescPk = insuranceMasterDescPk;
	}

	public BigDecimal getInsuranceMasterDescArabicPk() {
		return insuranceMasterDescArabicPk;
	}

	public void setInsuranceMasterDescArabicPk(
			BigDecimal insuranceMasterDescArabicPk) {
		this.insuranceMasterDescArabicPk = insuranceMasterDescArabicPk;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getRemarkCheck() {
		return remarkCheck;
	}

	public void setRemarkCheck(Boolean remarkCheck) {
		this.remarkCheck = remarkCheck;
	}

	public Boolean getBooActivate() {
		return booActivate;
	}

	public void setBooActivate(Boolean booActivate) {
		this.booActivate = booActivate;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	

}
