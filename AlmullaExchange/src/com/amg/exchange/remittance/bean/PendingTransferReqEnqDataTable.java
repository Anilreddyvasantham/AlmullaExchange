package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PendingTransferReqEnqDataTable {

	  private String applicationNumber;
	  private String date;
	  private BigDecimal currencyId;
	  private String currencyName;
	  private BigDecimal foreignCurrencyAmount;
	  private BigDecimal exchangeRate;
	  private BigDecimal localAmount;
	  private BigDecimal countryId;
	  private BigDecimal bankId;
	  private String bankName;
	  private BigDecimal remittanceId;
	  private String remittanceMode;
	  private BigDecimal deliveryId;
	  private String deliveryMode;
	  private String customerReference;
	  private BigDecimal customerRef;
	  private String fcCurrencyAmountDisplay;

	  // Pending Transfer Request Inquiry Detail screen properties
	  private String applicationRef;
	  private Date applicationDate;
	  private String telephone;
	  private String beneficiaryName;
	  private String futherInstruction;
	  private String product;
	  private String correspondingBank;
	  private String payableAt;
	  private BigDecimal saleAmount;
	  private String status;
	  private String transactionNo;
	  private String transferDate;
	  private String validUpto;
	  private BigDecimal purchageAmount;
	  private String transactionStatus;
	  private BigDecimal commision;
	  private String courier;
	  private BigDecimal charges;
	  private String deliveryDate;
	  private String deliveryAmount;
	  private String paymentDate;
	  private BigDecimal netAmount;
	  private String debitDate;
	  private String address;
	  private String createdBy;
	  private String modifiedBy;
	  private String branchName;
	  private Date createdDate;
	  private Date modifiedDate;
	  //Added by Rabil on 06 Dec 2016
	  private String appStatus;
	  private String paymnetId;
	  private String resultCode;
	  private String loyaltyInd;
	  private BigDecimal loyaltEncash;
	  private String styleColor;

	  public BigDecimal getCustomerRef() {
		    return customerRef;
	  }

	  public void setCustomerRef(BigDecimal customerRef) {
		    this.customerRef = customerRef;
	  }

	  public String getApplicationRef() {
		    return applicationRef;
	  }

	  public Date getApplicationDate() {
		    return applicationDate;
	  }

	  public String getTelephone() {
		    return telephone;
	  }

	  public String getBeneficiaryName() {
		    return beneficiaryName;
	  }

	  public String getFutherInstruction() {
		    return futherInstruction;
	  }

	  public String getProduct() {
		    return product;
	  }

	  public String getCorrespondingBank() {
		    return correspondingBank;
	  }

	  public String getPayableAt() {
		    return payableAt;
	  }

	  public BigDecimal getSaleAmount() {
		    return saleAmount;
	  }

	  public String getStatus() {
		    return status;
	  }

	  public String getTransactionNo() {
		    return transactionNo;
	  }

	  public String getTransferDate() {
		    return transferDate;
	  }

	  public String getValidUpto() {
		    return validUpto;
	  }

	  public BigDecimal getPurchageAmount() {
		    return purchageAmount;
	  }

	  public String getTransactionStatus() {
		    return transactionStatus;
	  }

	  public BigDecimal getCommision() {
		    return commision;
	  }

	  public String getCourier() {
		    return courier;
	  }

	  public BigDecimal getCharges() {
		    return charges;
	  }

	  public String getDeliveryDate() {
		    return deliveryDate;
	  }

	  public String getDeliveryAmount() {
		    return deliveryAmount;
	  }

	  public String getPaymentDate() {
		    return paymentDate;
	  }

	  public BigDecimal getNetAmount() {
		    return netAmount;
	  }

	  public String getDebitDate() {
		    return debitDate;
	  }

	  public String getAddress() {
		    return address;
	  }

	  public String getCreatedBy() {
		    return createdBy;
	  }

	  public String getModifiedBy() {
		    return modifiedBy;
	  }

	  public String getBranchName() {
		    return branchName;
	  }

	  public Date getCreatedDate() {
		    return createdDate;
	  }

	  public Date getModifiedDate() {
		    return modifiedDate;
	  }

	  public void setApplicationRef(String applicationRef) {
		    this.applicationRef = applicationRef;
	  }

	  public void setApplicationDate(Date applicationDate) {
		    this.applicationDate = applicationDate;
	  }

	  public void setTelephone(String telephone) {
		    this.telephone = telephone;
	  }

	  public void setBeneficiaryName(String beneficiaryName) {
		    this.beneficiaryName = beneficiaryName;
	  }

	  public void setFutherInstruction(String futherInstruction) {
		    this.futherInstruction = futherInstruction;
	  }

	  public void setProduct(String product) {
		    this.product = product;
	  }

	  public void setCorrespondingBank(String correspondingBank) {
		    this.correspondingBank = correspondingBank;
	  }

	  public void setPayableAt(String payableAt) {
		    this.payableAt = payableAt;
	  }

	  public void setSaleAmount(BigDecimal saleAmount) {
		    this.saleAmount = saleAmount;
	  }

	  public void setStatus(String status) {
		    this.status = status;
	  }

	  public void setTransactionNo(String transactionNo) {
		    this.transactionNo = transactionNo;
	  }

	  public void setTransferDate(String transferDate) {
		    this.transferDate = transferDate;
	  }

	  public void setValidUpto(String validUpto) {
		    this.validUpto = validUpto;
	  }

	  public void setPurchageAmount(BigDecimal purchageAmount) {
		    this.purchageAmount = purchageAmount;
	  }

	  public void setTransactionStatus(String transactionStatus) {
		    this.transactionStatus = transactionStatus;
	  }

	  public void setCommision(BigDecimal commision) {
		    this.commision = commision;
	  }

	  public void setCourier(String courier) {
		    this.courier = courier;
	  }

	  public void setCharges(BigDecimal charges) {
		    this.charges = charges;
	  }

	  public void setDeliveryDate(String deliveryDate) {
		    this.deliveryDate = deliveryDate;
	  }

	  public void setDeliveryAmount(String deliveryAmount) {
		    this.deliveryAmount = deliveryAmount;
	  }

	  public void setPaymentDate(String paymentDate) {
		    this.paymentDate = paymentDate;
	  }

	  public void setNetAmount(BigDecimal netAmount) {
		    this.netAmount = netAmount;
	  }

	  public void setDebitDate(String debitDate) {
		    this.debitDate = debitDate;
	  }

	  public void setAddress(String address) {
		    this.address = address;
	  }

	  public void setCreatedBy(String createdBy) {
		    this.createdBy = createdBy;
	  }

	  public void setModifiedBy(String modifiedBy) {
		    this.modifiedBy = modifiedBy;
	  }

	  public void setBranchName(String branchName) {
		    this.branchName = branchName;
	  }

	  public void setCreatedDate(Date createdDate) {
		    this.createdDate = createdDate;
	  }

	  public void setModifiedDate(Date modifiedDate) {
		    this.modifiedDate = modifiedDate;
	  }

	  public BigDecimal getExchangeRate() {
		    return exchangeRate;
	  }

	  public void setExchangeRate(BigDecimal exchangeRate) {
		    this.exchangeRate = exchangeRate;
	  }

	  public String getApplicationNumber() {
		    return applicationNumber;
	  }

	  public String getDate() {
		    return date;
	  }

	  public BigDecimal getCurrencyId() {
		    return currencyId;
	  }

	  public String getCurrencyName() {
		    return currencyName;
	  }

	  public BigDecimal getForeignCurrencyAmount() {
		    return foreignCurrencyAmount;
	  }

	  public BigDecimal getLocalAmount() {
		    return localAmount;
	  }

	  public BigDecimal getCountryId() {
		    return countryId;
	  }

	  public BigDecimal getBankId() {
		    return bankId;
	  }

	  public String getBankName() {
		    return bankName;
	  }

	  public BigDecimal getRemittanceId() {
		    return remittanceId;
	  }

	  public String getRemittanceMode() {
		    return remittanceMode;
	  }

	  public BigDecimal getDeliveryId() {
		    return deliveryId;
	  }

	  public String getDeliveryMode() {
		    return deliveryMode;
	  }

	  public String getCustomerReference() {
		    return customerReference;
	  }

	  public void setApplicationNumber(String applicationNumber) {
		    this.applicationNumber = applicationNumber;
	  }

	  public void setDate(String date) {
		    this.date = date;
	  }

	  public void setCurrencyId(BigDecimal currencyId) {
		    this.currencyId = currencyId;
	  }

	  public void setCurrencyName(String currencyName) {
		    this.currencyName = currencyName;
	  }

	  public void setForeignCurrencyAmount(BigDecimal foreignCurrencyAmount) {
		    this.foreignCurrencyAmount = foreignCurrencyAmount;
	  }

	  public void setLocalAmount(BigDecimal localAmount) {
		    this.localAmount = localAmount;
	  }

	  public void setCountryId(BigDecimal countryId) {
		    this.countryId = countryId;
	  }

	  public void setBankId(BigDecimal bankId) {
		    this.bankId = bankId;
	  }

	  public void setBankName(String bankName) {
		    this.bankName = bankName;
	  }

	  public void setRemittanceId(BigDecimal remittanceId) {
		    this.remittanceId = remittanceId;
	  }

	  public void setRemittanceMode(String remittanceMode) {
		    this.remittanceMode = remittanceMode;
	  }

	  public void setDeliveryId(BigDecimal deliveryId) {
		    this.deliveryId = deliveryId;
	  }

	  public void setDeliveryMode(String deliveryMode) {
		    this.deliveryMode = deliveryMode;
	  }

	  public void setCustomerReference(String customerReference) {
		    this.customerReference = customerReference;
	  }

	  public String getFcCurrencyAmountDisplay() {
		    return fcCurrencyAmountDisplay;
	  }

	  public void setFcCurrencyAmountDisplay(String fcCurrencyAmountDisplay) {
		    this.fcCurrencyAmountDisplay = fcCurrencyAmountDisplay;
	  }

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getPaymnetId() {
		return paymnetId;
	}

	public void setPaymnetId(String paymnetId) {
		this.paymnetId = paymnetId;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getLoyaltyInd() {
		return loyaltyInd;
	}

	public void setLoyaltyInd(String loyaltyInd) {
		this.loyaltyInd = loyaltyInd;
	}

	public BigDecimal getLoyaltEncash() {
		return loyaltEncash;
	}

	public void setLoyaltEncash(BigDecimal loyaltEncash) {
		this.loyaltEncash = loyaltEncash;
	}

	public String getStyleColor() {
		return styleColor;
	}

	public void setStyleColor(String styleColor) {
		this.styleColor = styleColor;
	}

}
