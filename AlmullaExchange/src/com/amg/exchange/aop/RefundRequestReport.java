package com.amg.exchange.aop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RefundRequestReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal refundFor;
	private String exchangeRate;
	private BigDecimal refund;
	private BigDecimal netRefund;
	private BigDecimal paymentNo;
	private String paymentDate;
	private BigDecimal refNo;
	private String refundOf;
	private Date refundDate;
	private String customerRefNo;
	private String customerName;
	private String favouringBankName;
	private String favouringBankAccNo;
	private String favouringBankBranch;
	private String favouringBankLocation;
	private String drawnBank;
	private String drawnBankBranch;
	private String drawnBankLocation;
	private String refundForCurrencyName;
	private String exchangeRateCurrencyName;
	private String netRefundCurrencyName;
	private String refDate;
	private BigDecimal refYear;
	private BigDecimal paymentYear;
	private String logoPath;
	private String refundCurrencyName;
	private String companyName;
	private String address;
	private String signature;
	private String idType;
	private String idNumber;

	
	

	public RefundRequestReport() {
	}

	public BigDecimal getRefundFor() {
		return refundFor;
	}

	public void setRefundFor(BigDecimal refundFor) {
		this.refundFor = refundFor;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getRefund() {
		return refund;
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

	public BigDecimal getNetRefund() {
		return netRefund;
	}

	public void setNetRefund(BigDecimal netRefund) {
		this.netRefund = netRefund;
	}

	public BigDecimal getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(BigDecimal paymentNo) {
		this.paymentNo = paymentNo;
	}

	

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	

	public String getRefDate() {
		return refDate;
	}

	public void setRefDate(String refDate) {
		this.refDate = refDate;
	}

	public BigDecimal getRefNo() {
		return refNo;
	}

	public void setRefNo(BigDecimal refNo) {
		this.refNo = refNo;
	}

	public String getRefundOf() {
		return refundOf;
	}

	public void setRefundOf(String refundOf) {
		this.refundOf = refundOf;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getCustomerRefNo() {
		return customerRefNo;
	}

	public void setCustomerRefNo(String customerRefNo) {
		this.customerRefNo = customerRefNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getFavouringBankName() {
		return favouringBankName;
	}

	public void setFavouringBankName(String favouringBankName) {
		this.favouringBankName = favouringBankName;
	}

	public String getFavouringBankAccNo() {
		return favouringBankAccNo;
	}

	public void setFavouringBankAccNo(String favouringBankAccNo) {
		this.favouringBankAccNo = favouringBankAccNo;
	}

	public String getFavouringBankBranch() {
		return favouringBankBranch;
	}

	public void setFavouringBankBranch(String favouringBankBranch) {
		this.favouringBankBranch = favouringBankBranch;
	}

	public String getFavouringBankLocation() {
		return favouringBankLocation;
	}

	public void setFavouringBankLocation(String favouringBankLocation) {
		this.favouringBankLocation = favouringBankLocation;
	}

	public String getDrawnBank() {
		return drawnBank;
	}

	public void setDrawnBank(String drawnBank) {
		this.drawnBank = drawnBank;
	}

	public String getDrawnBankBranch() {
		return drawnBankBranch;
	}

	public void setDrawnBankBranch(String drawnBankBranch) {
		this.drawnBankBranch = drawnBankBranch;
	}

	public String getRefundForCurrencyName() {
		return refundForCurrencyName;
	}

	public void setRefundForCurrencyName(String refundForCurrencyName) {
		this.refundForCurrencyName = refundForCurrencyName;
	}

	public String getExchangeRateCurrencyName() {
		return exchangeRateCurrencyName;
	}

	public void setExchangeRateCurrencyName(String exchangeRateCurrencyName) {
		this.exchangeRateCurrencyName = exchangeRateCurrencyName;
	}

	public String getNetRefundCurrencyName() {
		return netRefundCurrencyName;
	}

	public void setNetRefundCurrencyName(String netRefundCurrencyName) {
		this.netRefundCurrencyName = netRefundCurrencyName;
	}

	

	public BigDecimal getRefYear() {
		return refYear;
	}

	public void setRefYear(BigDecimal refYear) {
		this.refYear = refYear;
	}

	public BigDecimal getPaymentYear() {
		return paymentYear;
	}

	public void setPaymentYear(BigDecimal paymentYear) {
		this.paymentYear = paymentYear;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getDrawnBankLocation() {
		return drawnBankLocation;
	}

	public void setDrawnBankLocation(String drawnBankLocation) {
		this.drawnBankLocation = drawnBankLocation;
	}

	public String getRefundCurrencyName() {
		return refundCurrencyName;
	}

	public void setRefundCurrencyName(String refundCurrencyName) {
		this.refundCurrencyName = refundCurrencyName;
	}

	 
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	private String paymentNoAndYear;
	private String remitYearNumber;



	public String getPaymentNoAndYear() {
		return paymentNoAndYear;
	}

	public void setPaymentNoAndYear(String paymentNoAndYear) {
		this.paymentNoAndYear = paymentNoAndYear;
	}

	public String getRemitYearNumber() {
		return remitYearNumber;
	}

	public void setRemitYearNumber(String remitYearNumber) {
		this.remitYearNumber = remitYearNumber;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	
}
