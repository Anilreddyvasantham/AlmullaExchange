package com.amg.exchange.online.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_PLACE_ORDER_INQ")
public class ViewPlaceOnOrderInquiry implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal idNo;
	private String customerNumber;
	private String customerIdType;
	private String customerFullName;
	private String beneficiaryFullName;
	private String beneficiaryBankName;
	private String beneficiaryBankBranchName;
	private String beneficiaryAccountNumber;
	private String currencyQuote;
	private BigDecimal countryBranchId;
	private String createdBy;
	private BigDecimal transactionAmount;
	private BigDecimal rateOffered;
	private String Negotiate;
	private String isActive;
	
	public ViewPlaceOnOrderInquiry() {
		super();
	}
	
	


	@Id
	@Column(name="IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	
	@Column(name="CUSTOMER_NO")
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	@Column(name="CUSTOMER_TYPE_ID")
	public String getCustomerIdType() {
		return customerIdType;
	}
	public void setCustomerIdType(String customerIdType) {
		this.customerIdType = customerIdType;
	}
	
	@Column(name="BENEFICARY_NAME")
	public String getBeneficiaryFullName() {
		return beneficiaryFullName;
	}
	public void setBeneficiaryFullName(String beneficiaryFullName) {
		this.beneficiaryFullName = beneficiaryFullName;
	}
	
	@Column(name="BANK_NAME")
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}
	
	@Column(name="BRANCH_NAME")
	public String getBeneficiaryBankBranchName() {
		return beneficiaryBankBranchName;
	}
	public void setBeneficiaryBankBranchName(String beneficiaryBankBranchName) {
		this.beneficiaryBankBranchName = beneficiaryBankBranchName;
	}
	
	@Column(name="BANK_ACCOUNT_NUMBER")
	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}
	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}
	
	@Column(name="CURRENCY_QUOTE")
	public String getCurrencyQuote() {
		return currencyQuote;
	}
	public void setCurrencyQuote(String currencyQuote) {
		this.currencyQuote = currencyQuote;
	}
	
	@Column(name="COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="CUSTOMER_NAME")
	public String getCustomerFullName() {
		return customerFullName;
	}
	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	@Column(name="TRANSACTION_AMOUNT")
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	@Column(name="RATE_OFFERED")
	public BigDecimal getRateOffered() {
		return rateOffered;
	}
	public void setRateOffered(BigDecimal rateOffered) {
		this.rateOffered = rateOffered;
	}

	@Column(name="NEGOTIATE")
	public String getNegotiate() {
		return Negotiate;
	}
	public void setNegotiate(String negotiate) {
		Negotiate = negotiate;
	}

	@Column(name="IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	
}
