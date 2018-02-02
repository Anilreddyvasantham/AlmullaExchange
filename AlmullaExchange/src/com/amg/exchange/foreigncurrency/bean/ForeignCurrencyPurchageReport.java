package com.amg.exchange.foreigncurrency.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ForeignCurrencyPurchageReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal customerId;
	private String customerName;
	private BigDecimal contactNo;
	private String civilIdNo;
	private Date idExpiryDate;
	private String insurence1;
	private String insurence2;
	private String location;
	private BigDecimal mobileNo;
	private BigDecimal receiptNo;
	private BigDecimal noOfTransaction;
	private Date date;
	private String foreignCurrency;
	private BigDecimal transactionNo;
	private BigDecimal saleAmount;
	private BigDecimal exchangeRate;
	private BigDecimal purchageRate;
	private BigDecimal commision;
	private BigDecimal netAmount;
	private String sourceOfIncome;
	private String purpose;
	private BigDecimal amountPaid;
	private String paymentMode;
	private BigDecimal refundAmount;
	private BigDecimal netPaidAmount;
	private String signature;
	private String logoPath;
	private BigDecimal documentNo;
	private String employeeName;
	private BigDecimal customerRefNo;
	private BigDecimal docFynYr;
	private String locCurrency;
	private String bothCurrency;
	private String cashierSignature;
	private String loyaltyPointsExp;


	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
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
	
	public BigDecimal getContactNo() {
		return contactNo;
	}
	public void setContactNo(BigDecimal contactNo) {
		this.contactNo = contactNo;
	}
	
	public String getCivilIdNo() {
		return civilIdNo;
	}
	public void setCivilIdNo(String civilIdNo) {
		this.civilIdNo = civilIdNo;
	}
	
	public Date getIdExpiryDate() {
		return idExpiryDate;
	}
	public void setIdExpiryDate(Date idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}

	public String getInsurence1() {
		return insurence1;
	}
	public void setInsurence1(String insurence1) {
		this.insurence1 = insurence1;
	}
	
	public String getInsurence2() {
		return insurence2;
	}
	public void setInsurence2(String insurence2) {
		this.insurence2 = insurence2;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public BigDecimal getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(BigDecimal mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public BigDecimal getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(BigDecimal receiptNo) {
		this.receiptNo = receiptNo;
	}
	
	public BigDecimal getNoOfTransaction() {
		return noOfTransaction;
	}
	public void setNoOfTransaction(BigDecimal noOfTransaction) {
		this.noOfTransaction = noOfTransaction;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getForeignCurrency() {
		return foreignCurrency;
	}
	public void setForeignCurrency(String foreignCurrency) {
		this.foreignCurrency = foreignCurrency;
	}
	
	public BigDecimal getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(BigDecimal transactionNo) {
		this.transactionNo = transactionNo;
	}
	
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public BigDecimal getPurchageRate() {
		return purchageRate;
	}
	public void setPurchageRate(BigDecimal purchageRate) {
		this.purchageRate = purchageRate;
	}
	
	public BigDecimal getCommision() {
		return commision;
	}
	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}
	
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}
	
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	public BigDecimal getNetPaidAmount() {
		return netPaidAmount;
	}
	public void setNetPaidAmount(BigDecimal netPaidAmount) {
		this.netPaidAmount = netPaidAmount;
	}
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}
	public BigDecimal getDocFynYr() {
		return docFynYr;
	}
	
	public void setDocFynYr(BigDecimal docFynYr) {
		this.docFynYr = docFynYr;
	}
	public void setCustomerRefNo(BigDecimal customerRefNo) {
		this.customerRefNo = customerRefNo;
	}
	
	public String getLocCurrency() {
		return locCurrency;
	}
	public void setLocCurrency(String locCurrency) {
		this.locCurrency = locCurrency;
	}
	
	public String getBothCurrency() {
		return bothCurrency;
	}
	public void setBothCurrency(String bothCurrency) {
		this.bothCurrency = bothCurrency;
	}
	
	public String getCashierSignature() {
		return cashierSignature;
	}
	public void setCashierSignature(String cashierSignature) {
		this.cashierSignature = cashierSignature;
	}
	
	public String getLoyaltyPointsExp() {
		return loyaltyPointsExp;
	}
	public void setLoyaltyPointsExp(String loyaltyPointsExp) {
		this.loyaltyPointsExp = loyaltyPointsExp;
	}



}
