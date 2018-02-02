package com.amg.exchange.wuh2h.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WUH2HSendTxnDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	
	private String mtcn;
	private String newMtcn;
	private String senderFirstName;
	private String senderLastName;
	private String senderCityName;
	private String senderStateName;
	private String senderStreet;
	private String senderAddressLine1;
	private String senderAddressLine2;
	
	private String senderCountryName;	
	private String senderCurrencyName;	
	private String senderCurrencyIsoCode;
	private String senderCountryIsoCode;
	
	private String receiverCurrencyIsoCode;
	private String receiverCountryIsoCode;
	private String receiverCountryName;
	private String receiverCurrencyName;
	
	private String receiverFirstName;
	private String receiverLasttName;
	
	private BigDecimal transactionNumber;
	private BigDecimal documentFinanceYear;
	private BigDecimal collectionDocumentNo;
	private BigDecimal collectionDocumentCode;
	private BigDecimal foreignTxnAmount;
	private Date documentDate;
	private String TransactionType;
	private String foreignCurrencyCode;
	private String terminalAddress;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	
	
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
	public String getSenderCityName() {
		return senderCityName;
	}
	public void setSenderCityName(String senderCityName) {
		this.senderCityName = senderCityName;
	}
	public String getSenderStateName() {
		return senderStateName;
	}
	public void setSenderStateName(String senderStateName) {
		this.senderStateName = senderStateName;
	}
	public String getSenderStreet() {
		return senderStreet;
	}
	public void setSenderStreet(String senderStreet) {
		this.senderStreet = senderStreet;
	}
	public String getSenderAddressLine1() {
		return senderAddressLine1;
	}
	public void setSenderAddressLine1(String senderAddressLine1) {
		this.senderAddressLine1 = senderAddressLine1;
	}
	public String getSenderAddressLine2() {
		return senderAddressLine2;
	}
	public void setSenderAddressLine2(String senderAddressLine2) {
		this.senderAddressLine2 = senderAddressLine2;
	}
	public String getSenderCountryName() {
		return senderCountryName;
	}
	public void setSenderCountryName(String senderCountryName) {
		this.senderCountryName = senderCountryName;
	}
	public String getSenderCurrencyName() {
		return senderCurrencyName;
	}
	public void setSenderCurrencyName(String senderCurrencyName) {
		this.senderCurrencyName = senderCurrencyName;
	}
	public String getSenderCurrencyIsoCode() {
		return senderCurrencyIsoCode;
	}
	public void setSenderCurrencyIsoCode(String senderCurrencyIsoCode) {
		this.senderCurrencyIsoCode = senderCurrencyIsoCode;
	}
	public String getSenderCountryIsoCode() {
		return senderCountryIsoCode;
	}
	public void setSenderCountryIsoCode(String senderCountryIsoCode) {
		this.senderCountryIsoCode = senderCountryIsoCode;
	}
	public String getReceiverCurrencyIsoCode() {
		return receiverCurrencyIsoCode;
	}
	public void setReceiverCurrencyIsoCode(String receiverCurrencyIsoCode) {
		this.receiverCurrencyIsoCode = receiverCurrencyIsoCode;
	}
	public String getReceiverCountryIsoCode() {
		return receiverCountryIsoCode;
	}
	public void setReceiverCountryIsoCode(String receiverCountryIsoCode) {
		this.receiverCountryIsoCode = receiverCountryIsoCode;
	}
	public String getReceiverCountryName() {
		return receiverCountryName;
	}
	public void setReceiverCountryName(String receiverCountryName) {
		this.receiverCountryName = receiverCountryName;
	}
	public String getReceiverCurrencyName() {
		return receiverCurrencyName;
	}
	public void setReceiverCurrencyName(String receiverCurrencyName) {
		this.receiverCurrencyName = receiverCurrencyName;
	}
	public String getReceiverFirstName() {
		return receiverFirstName;
	}
	public void setReceiverFirstName(String receiverFirstName) {
		this.receiverFirstName = receiverFirstName;
	}
	public String getReceiverLasttName() {
		return receiverLasttName;
	}
	public void setReceiverLasttName(String receiverLasttName) {
		this.receiverLasttName = receiverLasttName;
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
	public String getForeignCurrencyCode() {
		return foreignCurrencyCode;
	}
	public void setForeignCurrencyCode(String foreignCurrencyCode) {
		this.foreignCurrencyCode = foreignCurrencyCode;
	}
	public String getTerminalAddress() {
		return terminalAddress;
	}
	public void setTerminalAddress(String terminalAddress) {
		this.terminalAddress = terminalAddress;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	
}
