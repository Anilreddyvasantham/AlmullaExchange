package com.amg.exchange.online.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PlaceOrderRateFeedBranchSupportDataTable {

	//page level Variables
	private BigDecimal placeOrderPk;
	private BigDecimal countryBranchId;
	private String countryBranchName;
	private String mobileNumber;
	private BigDecimal placeOrderDocNumber;
	private BigDecimal placeOrderDocYear;
	private BigDecimal exchangeRate;
	private BigDecimal bankId;
	private String bankName;
	private BigDecimal currencyId;
	private String currencyName;
	private String customerRefAndName;
	private BigDecimal customerId;
	private String customerName;
	private BigDecimal amount;
	
	
	
	//common Variables
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	
	private BigDecimal routingCountry;
	private BigDecimal foriegnCurrency;
	private BigDecimal remitMode;
	private BigDecimal deliveryMode;
	private BigDecimal routingBank;
	private BigDecimal documentFinanceyear;
	private BigDecimal documentNo;
	private BigDecimal beneficiaryCountryId;
	private String beneficiaryAccountNo;
	private String customerEmail;
	private BigDecimal remittType;
	private BigDecimal beneficiaryBankId;
	private BigDecimal customerRefNo;
	private BigDecimal beneficiaryMasterId;
	private String beneficiaryName;
	private BigDecimal beneCurrencyId;
	
	public BigDecimal getPlaceOrderPk() {
		return placeOrderPk;
	}
	public void setPlaceOrderPk(BigDecimal placeOrderPk) {
		this.placeOrderPk = placeOrderPk;
	}
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	public String getCountryBranchName() {
		return countryBranchName;
	}
	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public BigDecimal getPlaceOrderDocNumber() {
		return placeOrderDocNumber;
	}
	public void setPlaceOrderDocNumber(BigDecimal placeOrderDocNumber) {
		this.placeOrderDocNumber = placeOrderDocNumber;
	}
	public BigDecimal getPlaceOrderDocYear() {
		return placeOrderDocYear;
	}
	public void setPlaceOrderDocYear(BigDecimal placeOrderDocYear) {
		this.placeOrderDocYear = placeOrderDocYear;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRoutingCountry() {
		return routingCountry;
	}
	public void setRoutingCountry(BigDecimal routingCountry) {
		this.routingCountry = routingCountry;
	}
	public BigDecimal getForiegnCurrency() {
		return foriegnCurrency;
	}
	public void setForiegnCurrency(BigDecimal foriegnCurrency) {
		this.foriegnCurrency = foriegnCurrency;
	}
	public BigDecimal getRemitMode() {
		return remitMode;
	}
	public void setRemitMode(BigDecimal remitMode) {
		this.remitMode = remitMode;
	}
	public BigDecimal getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(BigDecimal deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public BigDecimal getRoutingBank() {
		return routingBank;
	}
	public void setRoutingBank(BigDecimal routingBank) {
		this.routingBank = routingBank;
	}
	public BigDecimal getDocumentFinanceyear() {
		return documentFinanceyear;
	}
	public void setDocumentFinanceyear(BigDecimal documentFinanceyear) {
		this.documentFinanceyear = documentFinanceyear;
	}
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}
	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}
	public String getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}
	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public BigDecimal getRemittType() {
		return remittType;
	}
	public void setRemittType(BigDecimal remittType) {
		this.remittType = remittType;
	}
	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}
	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}
	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}
	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
	}
	public BigDecimal getBeneficiaryMasterId() {
		return beneficiaryMasterId;
	}
	public void setBeneficiaryMasterId(BigDecimal beneficiaryMasterId) {
		this.beneficiaryMasterId = beneficiaryMasterId;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public BigDecimal getBeneCurrencyId() {
		return beneCurrencyId;
	}
	public void setBeneCurrencyId(BigDecimal beneCurrencyId) {
		this.beneCurrencyId = beneCurrencyId;
	}
	
	
	
	
}
