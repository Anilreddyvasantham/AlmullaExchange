package com.amg.exchange.aop;

import java.io.Serializable;
import java.math.BigDecimal;

public class FcSaleReport  implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private BigDecimal receiptYear;
	private String receiptNo;
	private String location;
	private String customerName;
	private String telephoneNo;
	private String purchageCurrency;
	private String saleCurrency;
	private String saleAmount;
	private String totalSaleAmount;
	private String totalPurchageAmount;
	private String exchageRate;
	private String remrks;
	private String purposeOfTransaction;
	private String sourceOfIncome;
	private String signature;
	private BigDecimal customerId;
	private String civilId;
	private String idExpdate;
	private String insurence;
	private String todayDate;
	private String commision;
	private String amountRefund;
	private String paymentMode;
	private String logoPath;
	private String loyaltyPointExpiring;
	private String userName;
	private String cashierSignature;
	
	private String waterMarkLogoPath;
	private Boolean waterMarkCheck;
	
	
	

	public Boolean getWaterMarkCheck() {
		return waterMarkCheck;
	}
	public void setWaterMarkCheck(Boolean waterMarkCheck) {
		this.waterMarkCheck = waterMarkCheck;
	}
	public String getWaterMarkLogoPath() {
		return waterMarkLogoPath;
	}
	public void setWaterMarkLogoPath(String waterMarkLogoPath) {
		this.waterMarkLogoPath = waterMarkLogoPath;
	}
	public String getLoyaltyPointExpiring() {
		return loyaltyPointExpiring;
	}
	public String getUserName() {
		return userName;
	}
	public String getCashierSignature() {
		return cashierSignature;
	}
	public void setLoyaltyPointExpiring(String loyaltyPointExpiring) {
		this.loyaltyPointExpiring = loyaltyPointExpiring;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setCashierSignature(String cashierSignature) {
		this.cashierSignature = cashierSignature;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getAmountRefund() {
		return amountRefund;
	}
	public void setAmountRefund(String amountRefund) {
		this.amountRefund = amountRefund;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getCommision() {
		return commision;
	}
	public void setCommision(String commision) {
		this.commision = commision;
	}
	public String getTodayDate() {
		return todayDate;
	}
	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}
	public String getInsurence() {
		return insurence;
	}
	public void setInsurence(String insurence) {
		this.insurence = insurence;
	}
	public String getIdExpdate() {
		return idExpdate;
	}
	public void setIdExpdate(String idExpdate) {
		this.idExpdate = idExpdate;
	}
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getReceiptYear() {
		return receiptYear;
	}
	public void setReceiptYear(BigDecimal receiptYear) {
		this.receiptYear = receiptYear;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getPurchageCurrency() {
		return purchageCurrency;
	}
	public void setPurchageCurrency(String purchageCurrency) {
		this.purchageCurrency = purchageCurrency;
	}
	public String getSaleCurrency() {
		return saleCurrency;
	}
	public void setSaleCurrency(String saleCurrency) {
		this.saleCurrency = saleCurrency;
	}
	public String getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(String saleAmount) {
		this.saleAmount = saleAmount;
	}
	public String getTotalSaleAmount() {
		return totalSaleAmount;
	}
	public void setTotalSaleAmount(String totalSaleAmount) {
		this.totalSaleAmount = totalSaleAmount;
	}
	public String getTotalPurchageAmount() {
		return totalPurchageAmount;
	}
	public void setTotalPurchageAmount(String totalPurchageAmount) {
		this.totalPurchageAmount = totalPurchageAmount;
	}
	public String getExchageRate() {
		return exchageRate;
	}
	public void setExchageRate(String exchageRate) {
		this.exchageRate = exchageRate;
	}
	public String getRemrks() {
		return remrks;
	}
	public void setRemrks(String remrks) {
		this.remrks = remrks;
	}
	public String getPurposeOfTransaction() {
		return purposeOfTransaction;
	}
	public void setPurposeOfTransaction(String purposeOfTransaction) {
		this.purposeOfTransaction = purposeOfTransaction;
	}
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	

}
