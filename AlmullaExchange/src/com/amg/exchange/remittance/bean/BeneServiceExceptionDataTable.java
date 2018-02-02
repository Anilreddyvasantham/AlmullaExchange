/**
 * 
 */
package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Subramaniam
 * 
 */
public class BeneServiceExceptionDataTable {

	/**
	 * 
	 */
	public BeneServiceExceptionDataTable() {
		// TODO Auto-generated constructor stub
	}

	private BigDecimal beneServiceExcepSetup;
	private BigDecimal appCountryId;
	private BigDecimal countryId;
	private String countryCode;
	private BigDecimal bankId;
	private String bankCode;
	private BigDecimal bankBranchId;
	private String bankBranchCode;
	private BigDecimal remittanceModeId;
	private String remittanceCode;
	private BigDecimal deliveryId;
	private String deliveryModeCode;
	private String recordStatus;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String activatedBy;
	private Date activatedDate;
	private BigDecimal currency;
	private String currencyCode;
	private String currencyName;
	private String branchName;
	private String remittanceDescription;
	private String deliveryDescription;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton = false;
	private String countryName;
	private Boolean remarksCheck = false;
	private Boolean activateRecordCheck = false;

	private Boolean booCheckDelete = false;

	private String remarks;

	public BeneServiceExceptionDataTable(BigDecimal beneServiceExcepSetup, BigDecimal appCountryId, BigDecimal countryId, String countryCode, BigDecimal bankId, String bankCode, BigDecimal bankBranchId, String bankBranchCode, BigDecimal remittanceModeId, String remittanceCode,
			BigDecimal deliveryId, String deliveryModeCode, String recordStatus, String isActive, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String activatedBy, Date activatedDate, BigDecimal currency, String currencyCode, String currencyName, String branchName,
			String remittanceDescription, String deliveryDescription, String dynamicLabelForActivateDeactivate, Boolean renderEditButton, String countryName, Boolean remarksCheck, Boolean activateRecordCheck, Boolean booCheckDelete, String remarks) {
		this.beneServiceExcepSetup = beneServiceExcepSetup;
		this.appCountryId = appCountryId;
		this.countryId = countryId;
		this.countryCode = countryCode;
		this.bankId = bankId;
		this.bankCode = bankCode;
		this.bankBranchId = bankBranchId;
		this.bankBranchCode = bankBranchCode;
		this.remittanceModeId = remittanceModeId;
		this.remittanceCode = remittanceCode;
		this.deliveryId = deliveryId;
		this.deliveryModeCode = deliveryModeCode;
		this.recordStatus = recordStatus;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.activatedBy = activatedBy;
		this.activatedDate = activatedDate;
		this.currency = currency;
		this.currencyCode = currencyCode;
		this.currencyName = currencyName;
		this.branchName = branchName;
		this.remittanceDescription = remittanceDescription;
		this.deliveryDescription = deliveryDescription;
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
		this.renderEditButton = renderEditButton;
		this.countryName = countryName;
		this.remarksCheck = remarksCheck;
		this.activateRecordCheck = activateRecordCheck;
		this.booCheckDelete = booCheckDelete;
		this.remarks = remarks;
	}

	public BigDecimal getBeneServiceExcepSetup() {
		return beneServiceExcepSetup;
	}

	public void setBeneServiceExcepSetup(BigDecimal beneServiceExcepSetup) {
		this.beneServiceExcepSetup = beneServiceExcepSetup;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}

	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	public String getRemittanceCode() {
		return remittanceCode;
	}

	public void setRemittanceCode(String remittanceCode) {
		this.remittanceCode = remittanceCode;
	}

	public BigDecimal getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(BigDecimal deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getDeliveryModeCode() {
		return deliveryModeCode;
	}

	public void setDeliveryModeCode(String deliveryModeCode) {
		this.deliveryModeCode = deliveryModeCode;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public BigDecimal getCurrency() {
		return currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getRemittanceDescription() {
		return remittanceDescription;
	}

	public void setRemittanceDescription(String remittanceDescription) {
		this.remittanceDescription = remittanceDescription;
	}

	public String getDeliveryDescription() {
		return deliveryDescription;
	}

	public void setDeliveryDescription(String deliveryDescription) {
		this.deliveryDescription = deliveryDescription;
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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getActivatedBy() {
		return activatedBy;
	}

	public void setActivatedBy(String activatedBy) {
		this.activatedBy = activatedBy;
	}

	public Date getActivatedDate() {
		return activatedDate;
	}

	public void setActivatedDate(Date activatedDate) {
		this.activatedDate = activatedDate;
	}

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}

	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
