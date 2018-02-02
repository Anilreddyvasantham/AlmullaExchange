package com.amg.exchange.wuh2h.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_RECEIVE_TRANSACTION")
public class ReceiveReceiptView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal idNo;
	private BigDecimal documentCode;
	private BigDecimal documentNo;
	private BigDecimal documentFinanceYear;
	private Date documentDate;
	private String customerName;
	private BigDecimal customerId;
	private BigDecimal customerReference;
	private BigDecimal countryBranchId;
	private BigDecimal countryId;
	private BigDecimal foreignCurrencyid;
	private BigDecimal foreignTransactionAmount;
	private BigDecimal localTransactionAmount;
	private BigDecimal localNetAmount;
	private BigDecimal localCommissionAmount;
	private BigDecimal localCurrencyId;
	private BigDecimal purposeId;
	private String transactionType;
	private String mtcno;
	private String collectionMode;
	private BigDecimal collectonDocumentFinanceYear;
	private BigDecimal collectonDocumentNo;
	private BigDecimal collectonDocumentCode;
	private BigDecimal sourceId;
	private BigDecimal transferReference;
	private BigDecimal receiptId;
	private String purposeOfTransaction;
	private String sourceOfIncome;
	private BigDecimal applicationCountryId;
	private BigDecimal paymentId;
	private Date paymentDate;
	private String paymentMode;
	private BigDecimal paidAmount;
	private String receiptType;
	private BigDecimal companyId;
	private String approvalNo;
	private String foreignCurrencyQuoteName;
	
	private String wuSenderCountry;
	private String wuSenderState;
	private String wuSenderCity;
	private String wuSenderMobile;
	private String wuSenderFirstName;
	private String wuSenderLastName;
	private String wuSenderMessage;
	private BigDecimal exchangeRate;
	private String wuPurposeOfTransaction;
	private String terminalAddress;
	
	
	private Clob customerSignatureClob;
	
	@Id
	@Column(name="ID_NO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	
	@Column(name="DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	
	@Column(name="DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}	
		
	@Column(name="DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	
	@Column(name="DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name="CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Column(name="CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name="CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	
	@Column(name="COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	@Column(name="COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	@Column(name="FOREIGN_CURRENCY_ID")
	public BigDecimal getForeignCurrencyid() {
		return foreignCurrencyid;
	}
	public void setForeignCurrencyid(BigDecimal foreignCurrencyid) {
		this.foreignCurrencyid = foreignCurrencyid;
	}
	
	@Column(name="FOREIGN_TRNX_AMOUNT")
	public BigDecimal getForeignTransactionAmount() {
		return foreignTransactionAmount;
	}
	public void setForeignTransactionAmount(BigDecimal foreignTransactionAmount) {
		this.foreignTransactionAmount = foreignTransactionAmount;
	}
	
	@Column(name="LOCAL_TRNX_AMOUNT")
	public BigDecimal getLocalTransactionAmount() {
		return localTransactionAmount;
	}
	public void setLocalTransactionAmount(BigDecimal localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}
	
	@Column(name="LOCAL_NET_AMOUNT")
	public BigDecimal getLocalNetAmount() {
		return localNetAmount;
	}
	public void setLocalNetAmount(BigDecimal localNetAmount) {
		this.localNetAmount = localNetAmount;
	}
	
	@Column(name="LOCAL_COMMISION_AMOUNT")
	public BigDecimal getLocalCommissionAmount() {
		return localCommissionAmount;
	}
	public void setLocalCommissionAmount(BigDecimal localCommissionAmount) {
		this.localCommissionAmount = localCommissionAmount;
	}
	
	@Column(name="LOCAL_CURRENCY_ID")
	public BigDecimal getLocalCurrencyId() {
		return localCurrencyId;
	}
	public void setLocalCurrencyId(BigDecimal localCurrencyId) {
		this.localCurrencyId = localCurrencyId;
	}
	
	@Column(name="PURPOSE_ID")
	public BigDecimal getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(BigDecimal purposeId) {
		this.purposeId = purposeId;
	}
	
	@Column(name="TRANSACTION_TYPE")
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	@Column(name="WESTERN_UNION_MTCNO")
	public String getMtcno() {
		return mtcno;
	}
	public void setMtcno(String mtcno) {
		this.mtcno = mtcno;
	}
	
	@Column(name="COLLECTION_MODE")
	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}
	
	@Column(name="COLLECTION_DOC_FINANCE_YEAR")
	public BigDecimal getCollectonDocumentFinanceYear() {
		return collectonDocumentFinanceYear;
	}
	public void setCollectonDocumentFinanceYear(
			BigDecimal collectonDocumentFinanceYear) {
		this.collectonDocumentFinanceYear = collectonDocumentFinanceYear;
	}
	
	@Column(name="COLLECTION_DOCUMENT_NO")
	public BigDecimal getCollectonDocumentNo() {
		return collectonDocumentNo;
	}
	public void setCollectonDocumentNo(BigDecimal collectonDocumentNo) {
		this.collectonDocumentNo = collectonDocumentNo;
	}
	
	
	@Column(name="COLLECTION_DOC_CODE")
	public BigDecimal getCollectonDocumentCode() {
		return collectonDocumentCode;
	}
	public void setCollectonDocumentCode(BigDecimal collectonDocumentCode) {
		this.collectonDocumentCode = collectonDocumentCode;
	}
	@Column(name="SOURCE_OF_INCOME_ID")
	public BigDecimal getSourceId() {
		return sourceId;
	}
	public void setSourceId(BigDecimal sourceId) {
		this.sourceId = sourceId;
	}
	
	@Column(name="TRANSFER_REFERENCE")
	public BigDecimal getTransferReference() {
		return transferReference;
	}
	public void setTransferReference(BigDecimal transferReference) {
		this.transferReference = transferReference;
	}
	
	@Column(name="RECEIPT_ID")
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	
	@Column(name="PURPOSE_OF_TRANSACTION")
	public String getPurposeOfTransaction() {
		return purposeOfTransaction;
	}
	public void setPurposeOfTransaction(String purposeOfTransaction) {
		this.purposeOfTransaction = purposeOfTransaction;
	}
	
	@Column(name="SOURCE_OF_INCOME")
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}
	
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name="PAYMENT_ID")
	public BigDecimal getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(BigDecimal paymentId) {
		this.paymentId = paymentId;
	}
	
	@Column(name="PAYMENT_DATE")
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	@Column(name="PAYMENT_MODE")
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	@Column(name="PAID_AMOUNT")
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	
	@Column(name="RECEIPT_TYPE")
	public String getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	
	@Column(name="COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	@Column(name="APPROVAL_NO")
	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	
	@Column(name="FOREIGN_CURRENCY_QUOTE_NAME")
	public String getForeignCurrencyQuoteName() {
		return foreignCurrencyQuoteName;
	}
	public void setForeignCurrencyQuoteName(String foreignCurrencyQuoteName) {
		this.foreignCurrencyQuoteName = foreignCurrencyQuoteName;
	}
	
	@Column(name="WU_SENDER_COUNTRY_NAME")
	public String getWuSenderCountry() {
		return wuSenderCountry;
	}
	public void setWuSenderCountry(String wuSenderCountry) {
		this.wuSenderCountry = wuSenderCountry;
	}
	
	@Column(name="WU_SENDER_STATE")
	public String getWuSenderState() {
		return wuSenderState;
	}
	public void setWuSenderState(String wuSenderState) {
		this.wuSenderState = wuSenderState;
	}
	
	@Column(name="WU_SENDER_CITY")
	public String getWuSenderCity() {
		return wuSenderCity;
	}
	public void setWuSenderCity(String wuSenderCity) {
		this.wuSenderCity = wuSenderCity;
	}
	
	@Column(name="WU_SENDER_MOBILENO")
	public String getWuSenderMobile() {
		return wuSenderMobile;
	}
	public void setWuSenderMobile(String wuSenderMobile) {
		this.wuSenderMobile = wuSenderMobile;
	}
	
	@Column(name="WU_SENDER_FIRST_NAME")
	public String getWuSenderFirstName() {
		return wuSenderFirstName;
	}
	public void setWuSenderFirstName(String wuSenderFirstName) {
		this.wuSenderFirstName = wuSenderFirstName;
	}
	
	@Column(name="WU_SENDER_LAST_NAME")
	public String getWuSenderLastName() {
		return wuSenderLastName;
	}
	public void setWuSenderLastName(String wuSenderLastName) {
		this.wuSenderLastName = wuSenderLastName;
	}
	
	@Column(name="WU_SENDER_MESSAGE")
	public String getWuSenderMessage() {
		return wuSenderMessage;
	}
	public void setWuSenderMessage(String wuSenderMessage) {
		this.wuSenderMessage = wuSenderMessage;
	}
	
	@Column(name="TRANSACTION_ACTUAL_RATE")
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	@Column(name="WU_PURPOSE_OF_TRANSACTION")
	public String getWuPurposeOfTransaction() {
		return wuPurposeOfTransaction;
	}
	public void setWuPurposeOfTransaction(String wuPurposeOfTransaction) {
		this.wuPurposeOfTransaction = wuPurposeOfTransaction;
	}
	@Column(name="SIGNATURE_SPECIMEN_CLOB")
	public Clob getCustomerSignatureClob() {
		return customerSignatureClob;
	}
	public void setCustomerSignatureClob(Clob customerSignatureClob) {
		this.customerSignatureClob = customerSignatureClob;
	}
	@Column(name="WU_TERMINAL_ADDRESS")
	public String getTerminalAddress() {
		return terminalAddress;
	}
	public void setTerminalAddress(String terminalAddress) {
		this.terminalAddress = terminalAddress;
	}
	
	
}
