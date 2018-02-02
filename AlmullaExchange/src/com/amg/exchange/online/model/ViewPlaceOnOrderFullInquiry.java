package com.amg.exchange.online.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_PLACE_ORDER")
public class ViewPlaceOnOrderFullInquiry implements Serializable{

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
	private String negotiate;
	private String isActive;
	private Date valueDate;
	private Date createdDate;
	private BigDecimal documentNo;
	private BigDecimal documentYear;
	private String approvedBy;
	private BigDecimal transactionAmountPaid;
	private BigDecimal trnxdocumentYear;
	private BigDecimal trnxdocumentNo;
	
	public ViewPlaceOnOrderFullInquiry() {
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
		return negotiate;
	}
	public void setNegotiate(String negotiate) {
		this.negotiate = negotiate;
	}

	@Column(name="IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name="VALUE_DATE")
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name="DOCUMENT_YEAR")
	public BigDecimal getDocumentYear() {
		return documentYear;
	}
	public void setDocumentYear(BigDecimal documentYear) {
		this.documentYear = documentYear;
	}

	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name="TRANSACTION_AMOUNT_PAID")
	public BigDecimal getTransactionAmountPaid() {
		return transactionAmountPaid;
	}
	public void setTransactionAmountPaid(BigDecimal transactionAmountPaid) {
		this.transactionAmountPaid = transactionAmountPaid;
	}

	@Column(name="REMIT_DOCUMENT_YEAR")
	public BigDecimal getTrnxdocumentYear() {
		return trnxdocumentYear;
	}
	public void setTrnxdocumentYear(BigDecimal trnxdocumentYear) {
		this.trnxdocumentYear = trnxdocumentYear;
	}

	@Column(name="REMIT_DOCUMENT_NUMBER")
	public BigDecimal getTrnxdocumentNo() {
		return trnxdocumentNo;
	}
	public void setTrnxdocumentNo(BigDecimal trnxdocumentNo) {
		this.trnxdocumentNo = trnxdocumentNo;
	}

	
	
	
}
