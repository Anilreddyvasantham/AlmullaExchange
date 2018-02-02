package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ExchangeRateApprovalDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal exchangeRateMasterId;
	private BigDecimal exchangeRateMasterAprId;
	private BigDecimal applicationCountryId;
	private String bankCode;
	private BigDecimal bankId;
	private String branchName;
	private BigDecimal buyRateMAX;
	private BigDecimal buyRateMIN;
	private BigDecimal corporateRate;
	private BigDecimal countryBranchId;
	private String countryCode;
	private BigDecimal countryId;
	private String countryName;
	private String currencyCode;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal locCOD;
	private BigDecimal prvBuyRateMAX;
	private BigDecimal prvBuyRateMIN;
	private BigDecimal prvSellRateMAX;
	private BigDecimal prvSellRateMIN;
	private BigDecimal sellRateMAX;
	private BigDecimal sellRateMIN;
	private String serviceCode;
	private String serviceDescription;
	private BigDecimal serviceMasterId;
	private String authCheckId;
	private String authCheckStatus;
	private String createdBy;
	private Date createdDate;
	
	public BigDecimal getExchangeRateMasterId() {
		return exchangeRateMasterId;
	}
	public void setExchangeRateMasterId(BigDecimal exchangeRateMasterId) {
		this.exchangeRateMasterId = exchangeRateMasterId;
	}
	
	public BigDecimal getExchangeRateMasterAprId() {
		return exchangeRateMasterAprId;
	}
	public void setExchangeRateMasterAprId(BigDecimal exchangeRateMasterAprId) {
		this.exchangeRateMasterAprId = exchangeRateMasterAprId;
	}
	
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	public String getAuthCheckId() {
		return authCheckId;
	}
	public void setAuthCheckId(String authCheckId) {
		this.authCheckId = authCheckId;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public BigDecimal getBuyRateMAX() {
		return buyRateMAX;
	}
	public void setBuyRateMAX(BigDecimal buyRateMAX) {
		this.buyRateMAX = buyRateMAX;
	}
	
	public BigDecimal getBuyRateMIN() {
		return buyRateMIN;
	}
	public void setBuyRateMIN(BigDecimal buyRateMIN) {
		this.buyRateMIN = buyRateMIN;
	}
	
	public BigDecimal getCorporateRate() {
		return corporateRate;
	}
	public void setCorporateRate(BigDecimal corporateRate) {
		this.corporateRate = corporateRate;
	}
	
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public BigDecimal getLocCOD() {
		return locCOD;
	}
	public void setLocCOD(BigDecimal locCOD) {
		this.locCOD = locCOD;
	}
	
	public BigDecimal getPrvBuyRateMAX() {
		return prvBuyRateMAX;
	}
	public void setPrvBuyRateMAX(BigDecimal prvBuyRateMAX) {
		this.prvBuyRateMAX = prvBuyRateMAX;
	}
	
	public BigDecimal getPrvBuyRateMIN() {
		return prvBuyRateMIN;
	}
	public void setPrvBuyRateMIN(BigDecimal prvBuyRateMIN) {
		this.prvBuyRateMIN = prvBuyRateMIN;
	}
	
	public BigDecimal getPrvSellRateMAX() {
		return prvSellRateMAX;
	}
	public void setPrvSellRateMAX(BigDecimal prvSellRateMAX) {
		this.prvSellRateMAX = prvSellRateMAX;
	}
	
	public BigDecimal getPrvSellRateMIN() {
		return prvSellRateMIN;
	}
	public void setPrvSellRateMIN(BigDecimal prvSellRateMIN) {
		this.prvSellRateMIN = prvSellRateMIN;
	}
	
	public BigDecimal getSellRateMAX() {
		return sellRateMAX;
	}
	public void setSellRateMAX(BigDecimal sellRateMAX) {
		this.sellRateMAX = sellRateMAX;
	}
	
	public BigDecimal getSellRateMIN() {
		return sellRateMIN;
	}
	public void setSellRateMIN(BigDecimal sellRateMIN) {
		this.sellRateMIN = sellRateMIN;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public String getServiceDescription() {
		return serviceDescription;
	}
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	
	public BigDecimal getServiceMasterId() {
		return serviceMasterId;
	}
	public void setServiceMasterId(BigDecimal serviceMasterId) {
		this.serviceMasterId = serviceMasterId;
	}
	
	public String getAuthCheckStatus() {
		return authCheckStatus;
	}
	public void setAuthCheckStatus(String authCheckStatus) {
		this.authCheckStatus = authCheckStatus;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
	

}
