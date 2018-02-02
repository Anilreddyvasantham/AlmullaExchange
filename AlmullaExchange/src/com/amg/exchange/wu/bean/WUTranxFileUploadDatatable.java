package com.amg.exchange.wu.bean;

import java.io.Serializable;

public class WUTranxFileUploadDatatable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String originatingCountryCode;
	private String oOriginatingCurrencyCode;
	private String terminalID;
	private String opID;
	private String supervopID;
	private String userName;
	private String mtcNo;
	private String receiverName;
	private String senderName;
	private String destinationCountryCode;
	private String destinationCurrencyCode;
	private String type;
	private String creationDate;
	private String principalAmount;
	private String charge;
	private String deliveryCharge;
	private String messageCharge;
	private String promotionDiscount;
	private String exchangeRate;
	private String expectedPayoutAmount;
	private String totalCharges;
	private String totalTaxes;
	private String paymentType;
	private String collectAmount;
	private String inorOut;
	
	//private String paymentType;
	public String getOriginatingCountryCode() {
		return originatingCountryCode;
	}
	public void setOriginatingCountryCode(String originatingCountryCode) {
		this.originatingCountryCode = originatingCountryCode;
	}
	
	public String getoOriginatingCurrencyCode() {
		return oOriginatingCurrencyCode;
	}
	public void setoOriginatingCurrencyCode(String oOriginatingCurrencyCode) {
		this.oOriginatingCurrencyCode = oOriginatingCurrencyCode;
	}
	
	public String getTerminalID() {
		return terminalID;
	}
	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}
	
	public String getOpID() {
		return opID;
	}
	public void setOpID(String opID) {
		this.opID = opID;
	}
	
	public String getSupervopID() {
		return supervopID;
	}
	public void setSupervopID(String supervopID) {
		this.supervopID = supervopID;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMtcNo() {
		return mtcNo;
	}
	public void setMtcNo(String mtcNo) {
		this.mtcNo = mtcNo;
	}
	
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	public String getDestinationCountryCode() {
		return destinationCountryCode;
	}
	public void setDestinationCountryCode(String destinationCountryCode) {
		this.destinationCountryCode = destinationCountryCode;
	}
	
	public String getDestinationCurrencyCode() {
		return destinationCurrencyCode;
	}
	public void setDestinationCurrencyCode(String destinationCurrencyCode) {
		this.destinationCurrencyCode = destinationCurrencyCode;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(String principalAmount) {
		this.principalAmount = principalAmount;
	}
	
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	
	public String getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(String deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	
	public String getMessageCharge() {
		return messageCharge;
	}
	public void setMessageCharge(String messageCharge) {
		this.messageCharge = messageCharge;
	}
	
	public String getPromotionDiscount() {
		return promotionDiscount;
	}
	public void setPromotionDiscount(String promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}
	
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public String getExpectedPayoutAmount() {
		return expectedPayoutAmount;
	}
	public void setExpectedPayoutAmount(String expectedPayoutAmount) {
		this.expectedPayoutAmount = expectedPayoutAmount;
	}
	
	public String getTotalCharges() {
		return totalCharges;
	}
	public void setTotalCharges(String totalCharges) {
		this.totalCharges = totalCharges;
	}
	
	public String getTotalTaxes() {
		return totalTaxes;
	}
	public void setTotalTaxes(String totalTaxes) {
		this.totalTaxes = totalTaxes;
	}
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public String getCollectAmount() {
		return collectAmount;
	}
	public void setCollectAmount(String collectAmount) {
		this.collectAmount = collectAmount;
	}
	
	public String getInorOut() {
		return inorOut;
	}
	public void setInorOut(String inorOut) {
		this.inorOut = inorOut;
	}
	
}
