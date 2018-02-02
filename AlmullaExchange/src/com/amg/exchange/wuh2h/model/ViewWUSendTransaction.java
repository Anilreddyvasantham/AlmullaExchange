package com.amg.exchange.wuh2h.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_WU_CUSTOMER_SEND_TXN")
public class ViewWUSendTransaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal idno;
	private BigDecimal customerReference;	
	private BigDecimal documentNumber;	
	private String beneficaryAccountNumber;	
	private String beneficaryBankName;	
	private String beneficaryBranchName;	
	private String beneficaryName;	
	private Date documentDate;	
	private BigDecimal documentFinanceYear;	
	private BigDecimal foreignTransactionAmount;	
	private String currencyQuoteName;	
	private String transactionStatusDesc;	
	private String transactionTypeDesc;	
	private BigDecimal collectionDocumentNo;		
	private BigDecimal collectionDocumentCode;	
	private BigDecimal  collectionDocumentFinYear;
	private String bankCode;
	private String mtcn;
	private String transactionStatus;
	private String terminalAddress;
	private BigDecimal applicationCountryId;
	private BigDecimal documentCode;
	private BigDecimal companyId;
	
	@Id
	@Column(name = "ID_NO")
	public BigDecimal getIdno() {
		return idno;
	}
	public void setIdno(BigDecimal idno) {
		this.idno = idno;
	}
	
	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	
	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	@Column(name = "BENEFICIARY_ACCOUNT_NO")
	public String getBeneficaryAccountNumber() {
		return beneficaryAccountNumber;
	}
	public void setBeneficaryAccountNumber(String beneficaryAccountNumber) {
		this.beneficaryAccountNumber = beneficaryAccountNumber;
	}
	
	@Column(name = "BENEFICIARY_BANK")
	public String getBeneficaryBankName() {
		return beneficaryBankName;
	}
	public void setBeneficaryBankName(String beneficaryBankName) {
		this.beneficaryBankName = beneficaryBankName;
	}
	
	@Column(name = "BENEFICIARY_BRANCH")
	public String getBeneficaryBranchName() {
		return beneficaryBranchName;
	}
	public void setBeneficaryBranchName(String beneficaryBranchName) {
		this.beneficaryBranchName = beneficaryBranchName;
	}
	
	@Column(name = "BENEFICIARY_NAME")
	public String getBeneficaryName() {
		return beneficaryName;
	}
	public void setBeneficaryName(String beneficaryName) {
		this.beneficaryName = beneficaryName;
	}	
	
	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	
	
	@Column(name = "FOREIGN_TRANX_AMOUNT")
	public BigDecimal getForeignTransactionAmount() {
		return foreignTransactionAmount;
	}
	public void setForeignTransactionAmount(BigDecimal foreignTransactionAmount) {
		this.foreignTransactionAmount = foreignTransactionAmount;
	}
	
	@Column(name = "FOREIGN_CURRENCY_QUOTE_NAME")
	public String getCurrencyQuoteName() {
		return currencyQuoteName;
	}
	public void setCurrencyQuoteName(String currencyQuoteName) {
		this.currencyQuoteName = currencyQuoteName;
	}
	
	
	@Column(name = "TRANSACTION_STATUS_DESC")
	public String getTransactionStatusDesc() {
		return transactionStatusDesc;
	}
	public void setTransactionStatusDesc(String transactionStatusDesc) {
		this.transactionStatusDesc = transactionStatusDesc;
	}
	
	@Column(name = "TRANSACTION_TYPE_DESC")
	public String getTransactionTypeDesc() {
		return transactionTypeDesc;
	}
	public void setTransactionTypeDesc(String transactionTypeDesc) {
		this.transactionTypeDesc = transactionTypeDesc;
	}
	
	@Column(name = "COLLECTION_DOCUMENT_NO")
	public BigDecimal getCollectionDocumentNo() {
		return collectionDocumentNo;
	}
	public void setCollectionDocumentNo(BigDecimal collectionDocumentNo) {
		this.collectionDocumentNo = collectionDocumentNo;
	}
	
	@Column(name = "COLLECTION_DOC_CODE")
	public BigDecimal getCollectionDocumentCode() {
		return collectionDocumentCode;
	}
	public void setCollectionDocumentCode(BigDecimal collectionDocumentCode) {
		this.collectionDocumentCode = collectionDocumentCode;
	}
	
	@Column(name = "COLLECTION_DOC_FINANCE_YEAR")
	public BigDecimal getCollectionDocumentFinYear() {
		return collectionDocumentFinYear;
	}
	public void setCollectionDocumentFinYear(BigDecimal collectionDocumentFinYear) {
		this.collectionDocumentFinYear = collectionDocumentFinYear;
	}
	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@Column(name = "WESTERN_UNION_MTCNO")
	public String getMtcn() {
		return mtcn;
	}
	public void setMtcn(String mtcn) {
		this.mtcn = mtcn;
	}
	@Column(name = "TRANSACTION_STATUS")
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	@Column(name = "WU_TERMINAL_ADDRESS")
	public String getTerminalAddress() {
		return terminalAddress;
	}
	public void setTerminalAddress(String terminalAddress) {
		this.terminalAddress = terminalAddress;
	}
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	
	
	
}
