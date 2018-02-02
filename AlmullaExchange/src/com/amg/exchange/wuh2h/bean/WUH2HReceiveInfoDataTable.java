package com.amg.exchange.wuh2h.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class WUH2HReceiveInfoDataTable implements Serializable{/*

	*//**
	 * 
	 *//*
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
	private String senderPostelCode;
	
	private String senderCountryName;	
	private String senderCurrencyName;	
	private String senderCurrencyIsoCode;
	private String senderCountryIsoCode;
	private String senderContactNo;
	private String senderNatinalityNo;
	
	private String receiverCurrencyIsoCode;
	private String receiverCountryIsoCode;
	private String receiverCountryName;
	private String receiverCurrencyName;
	
	private String receiverFirstName;
	private String receiverLasttName;
	
	private String receiverContactPhone;
	private String receiverAddressLine1;
	private String receiverAddressLine2;
	private String receiverCity;
	private String receiverPostelCode;
	private String receiverStreet;
	private String receiverState;
	private String receiverStateZip;
	
	private String receiverMobileCountryCode;
	private String receiverMobileNationalNo;
	
	private Long destinationAmount;
	private Long originationAmount;
	private BigDecimal originationAmountDisplay;	
	private Double exchangeRate;
	private String status;
	
	private Long plusChargesAmount;
	private Long grossTotalAmount;
	private Long charge;
	private Long payAmount;
	private Long totalDiscount;
	private Long dailyLimit;
	private Long transactionLimit;
	private Long taxAmount;
	private Long availableAmount;
	private Long minTransactionLimit;
	private Long moneyTransferLimit;
	private Long sumCharges;
	private String moneyTransferKey;
	
	private String payStatusDescription;
	private String setSenderContactPhone;
	
	
	private String originatingCountryISOCode;
	private String originatingCurrencyISOCode;
	
	private String destinationCountryISOCode;
	private String destinationCurrencyISOCode;
	
	private String originalCountryISOCode;
	private String originalCurrencyISOCode;
	
	private String expectedStateCode;
	private String expectedStateName;
	private String expectedCityName;
	private String originatingCity;
	
	private String receiverDateOfBirth;				
	private String receiverIdType;				
	private String receiverIdNumber;				
	private String receiverIdCountryOfIssue;				
	private String receiverIdExpirationDate;				
	private String receiverIdIssueDate;
	private String senderMessage;
	private Address address;
	private Financials financials;
	private Sender senderDetails;
	
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
	
	public String getMoneyTransferKey() {
		return moneyTransferKey;
	}
	public void setMoneyTransferKey(String moneyTransferKey) {
		this.moneyTransferKey = moneyTransferKey;
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
	public String getReceiverContactPhone() {
		return receiverContactPhone;
	}
	public void setReceiverContactPhone(String receiverContactPhone) {
		this.receiverContactPhone = receiverContactPhone;
	}
	public String getReceiverAddressLine1() {
		return receiverAddressLine1;
	}
	public void setReceiverAddressLine1(String receiverAddressLine1) {
		this.receiverAddressLine1 = receiverAddressLine1;
	}
	public String getReceiverAddressLine2() {
		return receiverAddressLine2;
	}
	public void setReceiverAddressLine2(String receiverAddressLine2) {
		this.receiverAddressLine2 = receiverAddressLine2;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverPostelCode() {
		return receiverPostelCode;
	}
	public void setReceiverPostelCode(String receiverPostelCode) {
		this.receiverPostelCode = receiverPostelCode;
	}
	public String getReceiverStreet() {
		return receiverStreet;
	}
	public void setReceiverStreet(String receiverStreet) {
		this.receiverStreet = receiverStreet;
	}
	public String getReceiverState() {
		return receiverState;
	}
	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}
	public String getReceiverCountryName() {
		return receiverCountryName;
	}
	public void setReceiverCountryName(String receiverCountryName) {
		this.receiverCountryName = receiverCountryName;
	}
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getReceiverCurrencyName() {
		return receiverCurrencyName;
	}
	public void setReceiverCurrencyName(String receiverCurrencyName) {
		this.receiverCurrencyName = receiverCurrencyName;
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
	public Long getDestinationAmount() {
		return destinationAmount;
	}
	public void setDestinationAmount(Long destinationAmount) {
		this.destinationAmount = destinationAmount;
	}
	public Long getOriginationAmount() {
		return originationAmount;
	}
	public void setOriginationAmount(Long originationAmount) {
		this.originationAmount = originationAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getSenderPostelCode() {
		return senderPostelCode;
	}
	public void setSenderPostelCode(String senderPostelCode) {
		this.senderPostelCode = senderPostelCode;
	}
	public String getSenderCountryName() {
		return senderCountryName;
	}
	public void setSenderCountryName(String senderCountryName) {
		this.senderCountryName = senderCountryName;
	}
	public Long getPlusChargesAmount() {
		return plusChargesAmount;
	}
	public void setPlusChargesAmount(Long plusChargesAmount) {
		this.plusChargesAmount = plusChargesAmount;
	}
	public Long getGrossTotalAmount() {
		return grossTotalAmount;
	}
	public void setGrossTotalAmount(Long grossTotalAmount) {
		this.grossTotalAmount = grossTotalAmount;
	}
	public Long getCharge() {
		return charge;
	}
	public void setCharge(Long charge) {
		this.charge = charge;
	}
	public Long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}
	public Long getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(Long totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public Long getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(Long dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public Long getTransactionLimit() {
		return transactionLimit;
	}
	public void setTransactionLimit(Long transactionLimit) {
		this.transactionLimit = transactionLimit;
	}
	public Long getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Long taxAmount) {
		this.taxAmount = taxAmount;
	}
	public Long getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(Long availableAmount) {
		this.availableAmount = availableAmount;
	}
	public Long getMinTransactionLimit() {
		return minTransactionLimit;
	}
	public void setMinTransactionLimit(Long minTransactionLimit) {
		this.minTransactionLimit = minTransactionLimit;
	}
	public Long getMoneyTransferLimit() {
		return moneyTransferLimit;
	}
	public void setMoneyTransferLimit(Long moneyTransferLimit) {
		this.moneyTransferLimit = moneyTransferLimit;
	}
	public Long getSumCharges() {
		return sumCharges;
	}
	public void setSumCharges(Long sumCharges) {
		this.sumCharges = sumCharges;
	}
	public String getOriginatingCountryISOCode() {
		return originatingCountryISOCode;
	}
	public void setOriginatingCountryISOCode(String originatingCountryISOCode) {
		this.originatingCountryISOCode = originatingCountryISOCode;
	}
	public String getOriginatingCurrencyISOCode() {
		return originatingCurrencyISOCode;
	}
	public void setOriginatingCurrencyISOCode(String originatingCurrencyISOCode) {
		this.originatingCurrencyISOCode = originatingCurrencyISOCode;
	}
	public String getDestinationCountryISOCode() {
		return destinationCountryISOCode;
	}
	public void setDestinationCountryISOCode(String destinationCountryISOCode) {
		this.destinationCountryISOCode = destinationCountryISOCode;
	}
	public String getDestinationCurrencyISOCode() {
		return destinationCurrencyISOCode;
	}
	public void setDestinationCurrencyISOCode(String destinationCurrencyISOCode) {
		this.destinationCurrencyISOCode = destinationCurrencyISOCode;
	}
	public String getOriginalCountryISOCode() {
		return originalCountryISOCode;
	}
	public void setOriginalCountryISOCode(String originalCountryISOCode) {
		this.originalCountryISOCode = originalCountryISOCode;
	}
	public String getOriginalCurrencyISOCode() {
		return originalCurrencyISOCode;
	}
	public void setOriginalCurrencyISOCode(String originalCurrencyISOCode) {
		this.originalCurrencyISOCode = originalCurrencyISOCode;
	}
	public String getPayStatusDescription() {
		return payStatusDescription;
	}
	public void setPayStatusDescription(String payStatusDescription) {
		this.payStatusDescription = payStatusDescription;
	}
	public String getSetSenderContactPhone() {
		return setSenderContactPhone;
	}
	public void setSetSenderContactPhone(String setSenderContactPhone) {
		this.setSenderContactPhone = setSenderContactPhone;
	}
	public String getReceiverMobileCountryCode() {
		return receiverMobileCountryCode;
	}
	public void setReceiverMobileCountryCode(String receiverMobileCountryCode) {
		this.receiverMobileCountryCode = receiverMobileCountryCode;
	}
	public String getReceiverMobileNationalNo() {
		return receiverMobileNationalNo;
	}
	public void setReceiverMobileNationalNo(String receiverMobileNationalNo) {
		this.receiverMobileNationalNo = receiverMobileNationalNo;
	}
	public String getExpectedStateCode() {
		return expectedStateCode;
	}
	public void setExpectedStateCode(String expectedStateCode) {
		this.expectedStateCode = expectedStateCode;
	}
	public String getExpectedStateName() {
		return expectedStateName;
	}
	public void setExpectedStateName(String expectedStateName) {
		this.expectedStateName = expectedStateName;
	}
	public String getExpectedCityName() {
		return expectedCityName;
	}
	public void setExpectedCityName(String expectedCityName) {
		this.expectedCityName = expectedCityName;
	}
	public String getOriginatingCity() {
		return originatingCity;
	}
	public void setOriginatingCity(String originatingCity) {
		this.originatingCity = originatingCity;
	}
	public String getReceiverDateOfBirth() {
		return receiverDateOfBirth;
	}
	public void setReceiverDateOfBirth(String receiverDateOfBirth) {
		this.receiverDateOfBirth = receiverDateOfBirth;
	}
	public String getReceiverIdType() {
		return receiverIdType;
	}
	public void setReceiverIdType(String receiverIdType) {
		this.receiverIdType = receiverIdType;
	}
	public String getReceiverIdNumber() {
		return receiverIdNumber;
	}
	public void setReceiverIdNumber(String receiverIdNumber) {
		this.receiverIdNumber = receiverIdNumber;
	}
	public String getReceiverIdCountryOfIssue() {
		return receiverIdCountryOfIssue;
	}
	public void setReceiverIdCountryOfIssue(String receiverIdCountryOfIssue) {
		this.receiverIdCountryOfIssue = receiverIdCountryOfIssue;
	}
	public String getReceiverIdExpirationDate() {
		return receiverIdExpirationDate;
	}
	public void setReceiverIdExpirationDate(String receiverIdExpirationDate) {
		this.receiverIdExpirationDate = receiverIdExpirationDate;
	}
	public String getReceiverIdIssueDate() {
		return receiverIdIssueDate;
	}
	public void setReceiverIdIssueDate(String receiverIdIssueDate) {
		this.receiverIdIssueDate = receiverIdIssueDate;
	}
	public String getReceiverStateZip() {
		return receiverStateZip;
	}
	public void setReceiverStateZip(String receiverStateZip) {
		this.receiverStateZip = receiverStateZip;
	}
	public String getSenderMessage() {
		return senderMessage;
	}
	public void setSenderMessage(String senderMessage) {
		this.senderMessage = senderMessage;
	}
	public String getSenderContactNo() {
		return senderContactNo;
	}
	public void setSenderContactNo(String senderContactNo) {
		this.senderContactNo = senderContactNo;
	}
	public String getSenderNatinalityNo() {
		return senderNatinalityNo;
	}
	public void setSenderNatinalityNo(String senderNatinalityNo) {
		this.senderNatinalityNo = senderNatinalityNo;
	}
	public BigDecimal getOriginationAmountDisplay() {
		return originationAmountDisplay;
	}
	public void setOriginationAmountDisplay(BigDecimal originationAmountDisplay) {
		this.originationAmountDisplay = originationAmountDisplay;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Financials getFinancials() {
		return financials;
	}
	public void setFinancials(Financials financials) {
		this.financials = financials;
	}
	public Sender getSenderDetails() {
		return senderDetails;
	}
	public void setSenderDetails(Sender senderDetails) {
		this.senderDetails = senderDetails;
	}
	
	
	
	
	
*/}
