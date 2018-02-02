package com.amg.exchange.stoppayment.bean;

import java.math.BigDecimal;
import java.util.Date;

public class StopPaymentCollectionReport {
	
	private BigDecimal customerRefNo;
	private String customerName;
	private String telePhoneNumber;
	private String civilId;
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
	private Date refDate;
	private BigDecimal refYear;
	private BigDecimal paymentYear;
	private String logoPath;
	private String refundCurrencyName;
	private String contactDetails;
	private String beneDetails;
	private BigDecimal fcAmount;
	private BigDecimal receiptNo;
	private Date paymentDate;
	private BigDecimal eftNo;
	private String paymentBy;
	private BigDecimal amountPaid;
	private BigDecimal amountNet;
	private BigDecimal spcharges;
	private BigDecimal amountRefund;
	private BigDecimal ddNo;
	private BigDecimal transferAmount;
	private BigDecimal  drawnOn;
	private BigDecimal civilid;
	private  String beneBank;
	private String custInfo;
	private String companyName;
	private String product;
	private String locCur;
	private String address;
	private String receiptNoForCollect;
	private String eftNoandYear;
	private String telPhone;
	private String refDateReport;
	private String receiptDate;
	private String letterBody;
	
	
	
	public String getCustRef() {
		return custRef;
	}
	public void setCustRef(String custRef) {
		this.custRef = custRef;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public Date getTranxDate() {
		return tranxDate;
	}
	public void setTranxDate(Date tranxDate) {
		this.tranxDate = tranxDate;
	}
	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	private String custRef;
	private String finYear;
	private String transactionNo;
	private Date tranxDate;
	private String quoteName;
	
	
	
	
	public BigDecimal getDdNo() {
		return ddNo;
	}
	public void setDdNo(BigDecimal ddNo) {
		this.ddNo = ddNo;
	}
	public BigDecimal getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}
	public BigDecimal getDrawnOn() {
		return drawnOn;
	}
	public void setDrawnOn(BigDecimal drawnOn) {
		this.drawnOn = drawnOn;
	}
	public BigDecimal getCivilid() {
		return civilid;
	}
	public void setCivilid(BigDecimal civilid) {
		this.civilid = civilid;
	}
	public BigDecimal getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(BigDecimal receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public BigDecimal getEftNo() {
		return eftNo;
	}
	public void setEftNo(BigDecimal eftNo) {
		this.eftNo = eftNo;
	}
	public String getPaymentBy() {
		return paymentBy;
	}
	public void setPaymentBy(String paymentBy) {
		this.paymentBy = paymentBy;
	}
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}
	public BigDecimal getAmountNet() {
		return amountNet;
	}
	public void setAmountNet(BigDecimal amountNet) {
		this.amountNet = amountNet;
	}
	public BigDecimal getSpcharges() {
		return spcharges;
	}
	public void setSpcharges(BigDecimal spcharges) {
		this.spcharges = spcharges;
	}
	public BigDecimal getAmountRefund() {
		return amountRefund;
	}
	public void setAmountRefund(BigDecimal amountRefund) {
		this.amountRefund = amountRefund;
	}
	public BigDecimal getFcAmount() {
		return fcAmount;
	}
	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}
	public String getBeneDetails() {
		return beneDetails;
	}
	public void setBeneDetails(String beneDetails) {
		this.beneDetails = beneDetails;
	}
	public String getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	public String getTelePhoneNumber() {
		return telePhoneNumber;
	}
	public void setTelePhoneNumber(String telePhoneNumber) {
		this.telePhoneNumber = telePhoneNumber;
	}
	public BigDecimal getCustomerRefNo() {
		return customerRefNo;
	}
	public void setCustomerRefNo(BigDecimal customerRefNo) {
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
	public String getDrawnBankLocation() {
		return drawnBankLocation;
	}
	public void setDrawnBankLocation(String drawnBankLocation) {
		this.drawnBankLocation = drawnBankLocation;
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
	public Date getRefDate() {
		return refDate;
	}
	public void setRefDate(Date refDate) {
		this.refDate = refDate;
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
	public String getRefundCurrencyName() {
		return refundCurrencyName;
	}
	public void setRefundCurrencyName(String refundCurrencyName) {
		this.refundCurrencyName = refundCurrencyName;
	}
	public String getBeneBank() {
		return beneBank;
	}
	public void setBeneBank(String beneBank) {
		this.beneBank = beneBank;
	}
	public String getCustInfo() {
		return custInfo;
	}
	public void setCustInfo(String custInfo) {
		this.custInfo = custInfo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getLocCur() {
		return locCur;
	}
	public void setLocCur(String locCur) {
		this.locCur = locCur;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReceiptNoForCollect() {
		return receiptNoForCollect;
	}
	public void setReceiptNoForCollect(String receiptNoForCollect) {
		this.receiptNoForCollect = receiptNoForCollect;
	}
	public String getEftNoandYear() {
		return eftNoandYear;
	}
	public void setEftNoandYear(String eftNoandYear) {
		this.eftNoandYear = eftNoandYear;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getRefDateReport() {
		return refDateReport;
	}
	public void setRefDateReport(String refDateReport) {
		this.refDateReport = refDateReport;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getLetterBody() {
		return letterBody;
	}
	public void setLetterBody(String letterBody) {
		this.letterBody = letterBody;
	}
	
}
