package com.amg.exchange.online.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ChequeClearPlaceOrderDataTable {

	//Page level Variables
	private BigDecimal customerRefNo;
	private BigDecimal documentNumber;
	private String beneficiaryName;
	private BigDecimal beneficiaryBankId;
	private String beneficiaryBankName;
	private BigDecimal rateOffered;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal transctionAmount;
	private String customerRefAndName;
	private BigDecimal remitServiceId;
	private BigDecimal remitRemittanceId;
	private BigDecimal remitDeliveryId;
	private BigDecimal rateOfferedPk;
	private BigDecimal customerId;
	private String customerName;
	private String amountAndQtyName;
	private BigDecimal beneficiaryMasterId;
	private BigDecimal beneficiaryAccountSeqId;
	private String currencyQtyName;
	private BigDecimal routingCountry;
	private BigDecimal routingBank;
	private BigDecimal remitDocumentNumber;
	private BigDecimal remitDocumentFinanceYear;
	private BigDecimal paymentmodeId;
	private String paymentCode;
	//cheque Details
	private String chequeIndicator;
	private BigDecimal chequeBankCode;
	private String chequeReference;
	private Date chequeDate;
	//common Variables
	private String errorMessage;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private boolean selectCheckBox=false;
	private String emailId;
	private BigDecimal remitLocalAmount;
	private String remitLocalAmountAndQtyName;
	
	private BigDecimal freignTranxAmount;
	private BigDecimal localCommissionAmount;
	
	
	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}
	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
	}
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}
	public BigDecimal getRateOffered() {
		return rateOffered;
	}
	public void setRateOffered(BigDecimal rateOffered) {
		this.rateOffered = rateOffered;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public BigDecimal getTransctionAmount() {
		return transctionAmount;
	}
	public void setTransctionAmount(BigDecimal transctionAmount) {
		this.transctionAmount = transctionAmount;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
	public String getCustomerRefAndName() {
		return customerRefAndName;
	}
	public void setCustomerRefAndName(String customerRefAndName) {
		this.customerRefAndName = customerRefAndName;
	}
	public BigDecimal getRemitServiceId() {
		return remitServiceId;
	}
	public void setRemitServiceId(BigDecimal remitServiceId) {
		this.remitServiceId = remitServiceId;
	}
	public BigDecimal getRemitRemittanceId() {
		return remitRemittanceId;
	}
	public void setRemitRemittanceId(BigDecimal remitRemittanceId) {
		this.remitRemittanceId = remitRemittanceId;
	}
	public BigDecimal getRemitDeliveryId() {
		return remitDeliveryId;
	}
	public void setRemitDeliveryId(BigDecimal remitDeliveryId) {
		this.remitDeliveryId = remitDeliveryId;
	}
	public BigDecimal getRateOfferedPk() {
		return rateOfferedPk;
	}
	public void setRateOfferedPk(BigDecimal rateOfferedPk) {
		this.rateOfferedPk = rateOfferedPk;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAmountAndQtyName() {
		return amountAndQtyName;
	}
	public void setAmountAndQtyName(String amountAndQtyName) {
		this.amountAndQtyName = amountAndQtyName;
	}
	public BigDecimal getBeneficiaryMasterId() {
		return beneficiaryMasterId;
	}
	public void setBeneficiaryMasterId(BigDecimal beneficiaryMasterId) {
		this.beneficiaryMasterId = beneficiaryMasterId;
	}
	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}
	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}
	public String getCurrencyQtyName() {
		return currencyQtyName;
	}
	public void setCurrencyQtyName(String currencyQtyName) {
		this.currencyQtyName = currencyQtyName;
	}
	public BigDecimal getRoutingCountry() {
		return routingCountry;
	}
	public void setRoutingCountry(BigDecimal routingCountry) {
		this.routingCountry = routingCountry;
	}
	public BigDecimal getRoutingBank() {
		return routingBank;
	}
	public void setRoutingBank(BigDecimal routingBank) {
		this.routingBank = routingBank;
	}
	public BigDecimal getRemitDocumentNumber() {
		return remitDocumentNumber;
	}
	public void setRemitDocumentNumber(BigDecimal remitDocumentNumber) {
		this.remitDocumentNumber = remitDocumentNumber;
	}
	public BigDecimal getRemitDocumentFinanceYear() {
		return remitDocumentFinanceYear;
	}
	public void setRemitDocumentFinanceYear(BigDecimal remitDocumentFinanceYear) {
		this.remitDocumentFinanceYear = remitDocumentFinanceYear;
	}
	public BigDecimal getPaymentmodeId() {
		return paymentmodeId;
	}
	public void setPaymentmodeId(BigDecimal paymentmodeId) {
		this.paymentmodeId = paymentmodeId;
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public boolean isSelectCheckBox() {
		return selectCheckBox;
	}
	public void setSelectCheckBox(boolean selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}
	public String getChequeIndicator() {
		return chequeIndicator;
	}
	public void setChequeIndicator(String chequeIndicator) {
		this.chequeIndicator = chequeIndicator;
	}
	public BigDecimal getChequeBankCode() {
		return chequeBankCode;
	}
	public void setChequeBankCode(BigDecimal chequeBankCode) {
		this.chequeBankCode = chequeBankCode;
	}
	public String getChequeReference() {
		return chequeReference;
	}
	public void setChequeReference(String chequeReference) {
		this.chequeReference = chequeReference;
	}
	public Date getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public BigDecimal getRemitLocalAmount() {
		return remitLocalAmount;
	}
	public void setRemitLocalAmount(BigDecimal remitLocalAmount) {
		this.remitLocalAmount = remitLocalAmount;
	}
	public String getRemitLocalAmountAndQtyName() {
		return remitLocalAmountAndQtyName;
	}
	public void setRemitLocalAmountAndQtyName(String remitLocalAmountAndQtyName) {
		this.remitLocalAmountAndQtyName = remitLocalAmountAndQtyName;
	}
	public BigDecimal getFreignTranxAmount() {
		return freignTranxAmount;
	}
	public void setFreignTranxAmount(BigDecimal freignTranxAmount) {
		this.freignTranxAmount = freignTranxAmount;
	}
	public BigDecimal getLocalCommissionAmount() {
		return localCommissionAmount;
	}
	public void setLocalCommissionAmount(BigDecimal localCommissionAmount) {
		this.localCommissionAmount = localCommissionAmount;
	}
	
	
}
