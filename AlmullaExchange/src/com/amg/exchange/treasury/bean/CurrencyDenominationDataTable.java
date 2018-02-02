package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CurrencyDenominationDataTable {

	private BigDecimal denominationid;
	private BigDecimal countryId;
	private BigDecimal CurrencyId;
	private String denominationDesc;
	private BigDecimal denominationCode;
	private BigDecimal denomonationAmount;
	private String countryName;
	private String currencyName;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String remarks;
	private boolean booEditButton;
	private Boolean remarksCheck;
	private Boolean permanetDeleteCheck=false;
	private Boolean activateRecordCheck=false;
	private Boolean booRenderDataTableApprove=false;
	private List<CurrencyDenominationDataTable> selectCurrency;
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	public BigDecimal getCurrencyId() {
		return CurrencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		CurrencyId = currencyId;
	}
	
	public String getDenominationDesc() {
		return denominationDesc;
	}
	public void setDenominationDesc(String denominationDesc) {
		this.denominationDesc = denominationDesc;
	}
	
	public BigDecimal getDenomonationAmount() {
		return denomonationAmount;
	}
	public void setDenomonationAmount(BigDecimal denomonationAmount) {
		this.denomonationAmount = denomonationAmount;
	}
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public BigDecimal getDenominationid() {
		return denominationid;
	}
	public void setDenominationid(BigDecimal denominationid) {
		this.denominationid = denominationid;
	}
	
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}
	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
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
	
	public boolean isBooEditButton() {
		return booEditButton;
	}
	public void setBooEditButton(boolean booEditButton) {
		this.booEditButton = booEditButton;
	}
	
	public Boolean getRemarksCheck() {
		return remarksCheck;
	}
	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
	
	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}
	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
	}
	
	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}
	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}
	
	public Boolean getBooRenderDataTableApprove() {
		return booRenderDataTableApprove;
	}
	public void setBooRenderDataTableApprove(Boolean booRenderDataTableApprove) {
		this.booRenderDataTableApprove = booRenderDataTableApprove;
	}
	
	public List<CurrencyDenominationDataTable> getSelectCurrency() {
		return selectCurrency;
	}
	public void setSelectCurrency(List<CurrencyDenominationDataTable> selectCurrency) {
		this.selectCurrency = selectCurrency;
	}

	public BigDecimal getDenominationCode() {
		return denominationCode;
	}
	public void setDenominationCode(BigDecimal denominationCode) {
		this.denominationCode = denominationCode;
	}
	
}
