package com.amg.exchange.wuh2h.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WUH2HReceiveTxnDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mtcn;
	private String newMtcn;
	private String senderFirstName;
	private String senderLastName;
	private String receiverLastName;
	private String receiverFirstName;
	
	private BigDecimal transactionNumber;
	private BigDecimal documentFinanceYear;
	private BigDecimal collectionDocumentNo;
	private BigDecimal collectionDocumentCode;
	private BigDecimal foreignTxnAmount;
	private Date documentDate;
	private String TransactionType;
	private String foreignCurrencyCode;
	private BigDecimal localTxnAmount;
	private String localCurrencyCode;
	private String terminalAddress;
	private BigDecimal companyId;
	private BigDecimal applicationCountryId;
	
	public String getMtcn() {
		return mtcn;
	}
	public void setMtcn(String mtcn) {
		this.mtcn = mtcn;
	}
	public String getNewMtcn() {
		return newMtcn;
	}
	public void setNewMtcn(String newMtcn) {
		this.newMtcn = newMtcn;
	}
	public String getSenderFirstName() {
		return senderFirstName;
	}
	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}
	public String getSenderLastName() {
		return senderLastName;
	}
	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}
	public BigDecimal getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(BigDecimal transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	public BigDecimal getCollectionDocumentNo() {
		return collectionDocumentNo;
	}
	public void setCollectionDocumentNo(BigDecimal collectionDocumentNo) {
		this.collectionDocumentNo = collectionDocumentNo;
	}
	public BigDecimal getCollectionDocumentCode() {
		return collectionDocumentCode;
	}
	public void setCollectionDocumentCode(BigDecimal collectionDocumentCode) {
		this.collectionDocumentCode = collectionDocumentCode;
	}
	public BigDecimal getForeignTxnAmount() {
		return foreignTxnAmount;
	}
	public void setForeignTxnAmount(BigDecimal foreignTxnAmount) {
		this.foreignTxnAmount = foreignTxnAmount;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public String getTransactionType() {
		return TransactionType;
	}
	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}
	public String getReceiverLastName() {
		return receiverLastName;
	}
	public void setReceiverLastName(String receiverLastName) {
		this.receiverLastName = receiverLastName;
	}
	public String getReceiverFirstName() {
		return receiverFirstName;
	}
	public void setReceiverFirstName(String receiverFirstName) {
		this.receiverFirstName = receiverFirstName;
	}
	public BigDecimal getLocalTxnAmount() {
		return localTxnAmount;
	}
	public void setLocalTxnAmount(BigDecimal localTxnAmount) {
		this.localTxnAmount = localTxnAmount;
	}
	public String getLocalCurrencyCode() {
		return localCurrencyCode;
	}
	public void setLocalCurrencyCode(String localCurrencyCode) {
		this.localCurrencyCode = localCurrencyCode;
	}
	public String getTerminalAddress() {
		return terminalAddress;
	}
	public void setTerminalAddress(String terminalAddress) {
		this.terminalAddress = terminalAddress;
	}
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	
	
}
