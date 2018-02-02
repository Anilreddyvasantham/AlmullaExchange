package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankServiceRuleMasterDataTable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal bankChargesId = null;// pk
	private String chargeType;
	private BigDecimal chargeFor;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private BigDecimal chargeAmount;
	private String createdByBankCharges;
	private Date createdDateBankCharges;
	private String modifiedByBankCharges;
	private Date modifiedDateBankCharges;
	private String chargeFordis;
	private String chargeTypedis;
	private BigDecimal currencyCode;
	private BigDecimal costCurrencyCode;
	private BigDecimal costAmount;
	private Date createdDate;
	private String createdBy;
	private String currencyCodeName;
	private String costCurrencyCodeName;
	private String isActive;
	private Boolean checkSave;
	private String remarks;
	private Boolean remarkCheck;
	private Date approvedDate;
	private String approvedBy;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton = false;

	public BankServiceRuleMasterDataTable() {
	}

	public BankServiceRuleMasterDataTable(BigDecimal bankChargesId, String chargeType, BigDecimal chargeFor, BigDecimal fromAmount, BigDecimal toAmount, BigDecimal chargeAmount, String createdByBankCharges, Date createdDateBankCharges, String modifiedByBankCharges, Date modifiedDateBankCharges,
			String chargeFordis, String chargeTypedis, BigDecimal currencyCode, BigDecimal costCurrencyCode, BigDecimal costAmount, Date createdDate, String createdBy, String currencyCodeName, String costCurrencyCodeName,String isActive) {
		super();
		this.bankChargesId = bankChargesId;
		this.chargeType = chargeType;
		this.chargeFor = chargeFor;
		this.fromAmount = fromAmount;
		this.toAmount = toAmount;
		this.chargeAmount = chargeAmount;
		this.createdByBankCharges = createdByBankCharges;
		this.createdDateBankCharges = createdDateBankCharges;
		this.modifiedByBankCharges = modifiedByBankCharges;
		this.modifiedDateBankCharges = modifiedDateBankCharges;
		this.chargeFordis = chargeFordis;
		this.chargeTypedis = chargeTypedis;
		this.currencyCode = currencyCode;
		this.costCurrencyCode = costCurrencyCode;
		this.costAmount = costAmount;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.currencyCodeName = currencyCodeName;
		this.costCurrencyCodeName = costCurrencyCodeName;
		this.isActive = isActive;
	}

	public BigDecimal getBankChargesId() {
		return bankChargesId;
	}

	public void setBankChargesId(BigDecimal bankChargesId) {
		this.bankChargesId = bankChargesId;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public BigDecimal getChargeFor() {
		return chargeFor;
	}

	public void setChargeFor(BigDecimal chargeFor) {
		this.chargeFor = chargeFor;
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

	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getCreatedByBankCharges() {
		return createdByBankCharges;
	}

	public void setCreatedByBankCharges(String createdByBankCharges) {
		this.createdByBankCharges = createdByBankCharges;
	}

	public Date getCreatedDateBankCharges() {
		return createdDateBankCharges;
	}

	public void setCreatedDateBankCharges(Date createdDateBankCharges) {
		this.createdDateBankCharges = createdDateBankCharges;
	}

	public String getModifiedByBankCharges() {
		return modifiedByBankCharges;
	}

	public void setModifiedByBankCharges(String modifiedByBankCharges) {
		this.modifiedByBankCharges = modifiedByBankCharges;
	}

	public String getChargeFordis() {
		return chargeFordis;
	}

	public void setChargeFordis(String chargeFordis) {
		this.chargeFordis = chargeFordis;
	}

	public String getChargeTypedis() {
		return chargeTypedis;
	}

	public void setChargeTypedis(String chargeTypedis) {
		this.chargeTypedis = chargeTypedis;
	}

	public Date getModifiedDateBankCharges() {
		return modifiedDateBankCharges;
	}

	public void setModifiedDateBankCharges(Date modifiedDateBankCharges) {
		this.modifiedDateBankCharges = modifiedDateBankCharges;
	}

	public BigDecimal getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(BigDecimal currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getCostCurrencyCode() {
		return costCurrencyCode;
	}

	public void setCostCurrencyCode(BigDecimal costCurrencyCode) {
		this.costCurrencyCode = costCurrencyCode;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCurrencyCodeName() {
		return currencyCodeName;
	}

	public void setCurrencyCodeName(String currencyCodeName) {
		this.currencyCodeName = currencyCodeName;
	}

	public String getCostCurrencyCodeName() {
		return costCurrencyCodeName;
	}

	public void setCostCurrencyCodeName(String costCurrencyCodeName) {
		this.costCurrencyCodeName = costCurrencyCodeName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Boolean getCheckSave() {
		return checkSave;
	}

	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
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
}
