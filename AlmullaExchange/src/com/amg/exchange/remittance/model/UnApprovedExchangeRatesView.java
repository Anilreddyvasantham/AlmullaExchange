package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EX_V_UNAPPR_RATES")
public class UnApprovedExchangeRatesView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal srNo;
	private BigDecimal exchangeRateMasterId;
	private BigDecimal exchangeRateMasterAprId;
	private BigDecimal applicationCountryId;
	private String authCheckId;
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
	private String createdBy;
	private Date createdDate;
	
	@Id
	@Column(name = "SRNO")
	public BigDecimal getSrNo() {
		return srNo;
	}
	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "AUTH_CHECK_IND")
	public String getAuthCheckId() {
		return authCheckId;
	}
	public void setAuthCheckId(String authCheckId) {
		this.authCheckId = authCheckId;
	}
	
	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	@Column(name = "BUY_RATE_MAX")
	public BigDecimal getBuyRateMAX() {
		return buyRateMAX;
	}
	public void setBuyRateMAX(BigDecimal buyRateMAX) {
		this.buyRateMAX = buyRateMAX;
	}
	
	@Column(name = "BUY_RATE_MIN")
	public BigDecimal getBuyRateMIN() {
		return buyRateMIN;
	}
	public void setBuyRateMIN(BigDecimal buyRateMIN) {
		this.buyRateMIN = buyRateMIN;
	}
	
	@Column(name = "CORPORATE_RATE")
	public BigDecimal getCorporateRate() {
		return corporateRate;
	}
	public void setCorporateRate(BigDecimal corporateRate) {
		this.corporateRate = corporateRate;
	}
	
	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	@Column(name = "COUNTRY_CODE")
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	@Column(name = "COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	@Column(name = "COUNTRY_NAME")
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	@Column(name = "CURRENCY_CODE")
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name = "CURRENCY_NAME")
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	@Column(name = "EXCHANGE_RATE_MASTER_ID")
	public BigDecimal getExchangeRateMasterId() {
		return exchangeRateMasterId;
	}
	public void setExchangeRateMasterId(BigDecimal exchangeRateMasterId) {
		this.exchangeRateMasterId = exchangeRateMasterId;
	}
	
	@Column(name = "LOCCOD")
	public BigDecimal getLocCOD() {
		return locCOD;
	}
	public void setLocCOD(BigDecimal locCOD) {
		this.locCOD = locCOD;
	}
	
	@Column(name = "PRV_BUY_RATE_MAX")
	public BigDecimal getPrvBuyRateMAX() {
		return prvBuyRateMAX;
	}
	public void setPrvBuyRateMAX(BigDecimal prvBuyRateMAX) {
		this.prvBuyRateMAX = prvBuyRateMAX;
	}
	
	@Column(name = "PRV_BUY_RATE_MIN")
	public BigDecimal getPrvBuyRateMIN() {
		return prvBuyRateMIN;
	}
	public void setPrvBuyRateMIN(BigDecimal prvBuyRateMIN) {
		this.prvBuyRateMIN = prvBuyRateMIN;
	}

	@Column(name = "PRV_SELL_RATE_MAX")
	public BigDecimal getPrvSellRateMAX() {
		return prvSellRateMAX;
	}
	public void setPrvSellRateMAX(BigDecimal prvSellRateMAX) {
		this.prvSellRateMAX = prvSellRateMAX;
	}
	
	@Column(name = "PRV_SELL_RATE_MIN")
	public BigDecimal getPrvSellRateMIN() {
		return prvSellRateMIN;
	}
	public void setPrvSellRateMIN(BigDecimal prvSellRateMIN) {
		this.prvSellRateMIN = prvSellRateMIN;
	}
	
	@Column(name = "SELL_RATE_MAX")
	public BigDecimal getSellRateMAX() {
		return sellRateMAX;
	}
	public void setSellRateMAX(BigDecimal sellRateMAX) {
		this.sellRateMAX = sellRateMAX;
	}
	
	@Column(name = "SELL_RATE_MIN")
	public BigDecimal getSellRateMIN() {
		return sellRateMIN;
	}
	public void setSellRateMIN(BigDecimal sellRateMIN) {
		this.sellRateMIN = sellRateMIN;
	}
	
	@Column(name = "SERVICE_CODE")
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	@Column(name = "SERVICE_DESCRIPTION")
	public String getServiceDescription() {
		return serviceDescription;
	}
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	
	@Column(name = "SERVICE_MASTER_ID")
	public BigDecimal getServiceMasterId() {
		return serviceMasterId;
	}
	public void setServiceMasterId(BigDecimal serviceMasterId) {
		this.serviceMasterId = serviceMasterId;
	}
	
	@Column(name = "EXCHANGE_RATE_MASTER_APR_ID")
	public BigDecimal getExchangeRateMasterAprId() {
		return exchangeRateMasterAprId;
	}
	public void setExchangeRateMasterAprId(BigDecimal exchangeRateMasterAprId) {
		this.exchangeRateMasterAprId = exchangeRateMasterAprId;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
