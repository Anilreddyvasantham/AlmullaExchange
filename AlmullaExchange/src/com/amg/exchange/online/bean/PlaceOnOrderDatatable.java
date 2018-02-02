package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
   

public class PlaceOnOrderDatatable implements Serializable{

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
	private String status;
	private Date valueDate;
	private Date createdDate;
	private BigDecimal documentNo;
	private BigDecimal documentYear;
	private String approvedBy;
	private BigDecimal transactionAmountPaid;
	private BigDecimal trnxdocumentYear;
	private BigDecimal trnxdocumentNo;
	
	public BigDecimal getIdNo() {
		return idNo;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getCustomerIdType() {
		return customerIdType;
	}
	public void setCustomerIdType(String customerIdType) {
		this.customerIdType = customerIdType;
	}
	
	public String getCustomerFullName() {
		return customerFullName;
	}
	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}
	
	public String getBeneficiaryFullName() {
		return beneficiaryFullName;
	}
	public void setBeneficiaryFullName(String beneficiaryFullName) {
		this.beneficiaryFullName = beneficiaryFullName;
	}
	
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}
	
	public String getBeneficiaryBankBranchName() {
		return beneficiaryBankBranchName;
	}
	public void setBeneficiaryBankBranchName(String beneficiaryBankBranchName) {
		this.beneficiaryBankBranchName = beneficiaryBankBranchName;
	}
	
	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}
	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}
	
	public String getCurrencyQuote() {
		return currencyQuote;
	}
	public void setCurrencyQuote(String currencyQuote) {
		this.currencyQuote = currencyQuote;
	}
	
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public BigDecimal getRateOffered() {
		return rateOffered;
	}
	public void setRateOffered(BigDecimal rateOffered) {
		this.rateOffered = rateOffered;
	}
	
	public String getNegotiate() {
		return Negotiate;
	}
	public void setNegotiate(String negotiate) {
		Negotiate = negotiate;
	}
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	public BigDecimal getDocumentYear() {
		return documentYear;
	}
	public void setDocumentYear(BigDecimal documentYear) {
		this.documentYear = documentYear;
	}
	
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	public BigDecimal getTrnxdocumentYear() {
		return trnxdocumentYear;
	}
	public void setTrnxdocumentYear(BigDecimal trnxdocumentYear) {
		this.trnxdocumentYear = trnxdocumentYear;
	}
	
	public BigDecimal getTrnxdocumentNo() {
		return trnxdocumentNo;
	}
	public void setTrnxdocumentNo(BigDecimal trnxdocumentNo) {
		this.trnxdocumentNo = trnxdocumentNo;
	}
	
	public BigDecimal getTransactionAmountPaid() {
		return transactionAmountPaid;
	}
	public void setTransactionAmountPaid(BigDecimal transactionAmountPaid) {
		this.transactionAmountPaid = transactionAmountPaid;
	}
	
}
