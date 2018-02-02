package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CashExchangeRateApprovalDataBase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal applicationCountryId;
	private BigDecimal baseCurrencyId;
	private String baseCurrencyCode;
	private String baseCurrencyName;
	private BigDecimal alternativeCurrencyId;
	private String alternativeCurrencyCode;
	private String alternativeCurrencyName;
	private BigDecimal countryBranchId;
	private BigDecimal locCod;
	private String branchName;
	private BigDecimal salMinRate;
	private BigDecimal salMaxRate;
	private BigDecimal buyMinRate;
	private BigDecimal buyMaxRate;
	private BigDecimal prvSalMinRate;
	private BigDecimal prvSalMaxRate;
	private BigDecimal prvBuyMinRate;
	private BigDecimal prvBuyMaxRate;
	private String authCheckInd;
	private BigDecimal cashRateId;
	private String authCheckStatus;
	private BigDecimal unAppcashRateId;
	private String createdBy;
	private Date createdDate;
	
	
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	public BigDecimal getBaseCurrencyId() {
		return baseCurrencyId;
	}
	public void setBaseCurrencyId(BigDecimal baseCurrencyId) {
		this.baseCurrencyId = baseCurrencyId;
	}
	
	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}
	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}
	
	public String getBaseCurrencyName() {
		return baseCurrencyName;
	}
	public void setBaseCurrencyName(String baseCurrencyName) {
		this.baseCurrencyName = baseCurrencyName;
	}
	
	public BigDecimal getAlternativeCurrencyId() {
		return alternativeCurrencyId;
	}
	public void setAlternativeCurrencyId(BigDecimal alternativeCurrencyId) {
		this.alternativeCurrencyId = alternativeCurrencyId;
	}
	
	public String getAlternativeCurrencyCode() {
		return alternativeCurrencyCode;
	}
	public void setAlternativeCurrencyCode(String alternativeCurrencyCode) {
		this.alternativeCurrencyCode = alternativeCurrencyCode;
	}
	
	public String getAlternativeCurrencyName() {
		return alternativeCurrencyName;
	}
	public void setAlternativeCurrencyName(String alternativeCurrencyName) {
		this.alternativeCurrencyName = alternativeCurrencyName;
	}
	
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	public BigDecimal getLocCod() {
		return locCod;
	}
	public void setLocCod(BigDecimal locCod) {
		this.locCod = locCod;
	}
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public BigDecimal getSalMinRate() {
		return salMinRate;
	}
	public void setSalMinRate(BigDecimal salMinRate) {
		this.salMinRate = salMinRate;
	}
	
	public BigDecimal getSalMaxRate() {
		return salMaxRate;
	}
	public void setSalMaxRate(BigDecimal salMaxRate) {
		this.salMaxRate = salMaxRate;
	}
	
	public BigDecimal getBuyMinRate() {
		return buyMinRate;
	}
	public void setBuyMinRate(BigDecimal buyMinRate) {
		this.buyMinRate = buyMinRate;
	}
	
	public BigDecimal getBuyMaxRate() {
		return buyMaxRate;
	}
	public void setBuyMaxRate(BigDecimal buyMaxRate) {
		this.buyMaxRate = buyMaxRate;
	}
	
	public BigDecimal getPrvSalMinRate() {
		return prvSalMinRate;
	}
	public void setPrvSalMinRate(BigDecimal prvSalMinRate) {
		this.prvSalMinRate = prvSalMinRate;
	}
	
	public BigDecimal getPrvSalMaxRate() {
		return prvSalMaxRate;
	}
	public void setPrvSalMaxRate(BigDecimal prvSalMaxRate) {
		this.prvSalMaxRate = prvSalMaxRate;
	}
	
	public BigDecimal getPrvBuyMinRate() {
		return prvBuyMinRate;
	}
	public void setPrvBuyMinRate(BigDecimal prvBuyMinRate) {
		this.prvBuyMinRate = prvBuyMinRate;
	}
	
	public BigDecimal getPrvBuyMaxRate() {
		return prvBuyMaxRate;
	}
	public void setPrvBuyMaxRate(BigDecimal prvBuyMaxRate) {
		this.prvBuyMaxRate = prvBuyMaxRate;
	}
	
	public String getAuthCheckInd() {
		return authCheckInd;
	}
	public void setAuthCheckInd(String authCheckInd) {
		this.authCheckInd = authCheckInd;
	}
	
	public BigDecimal getCashRateId() {
		return cashRateId;
	}
	public void setCashRateId(BigDecimal cashRateId) {
		this.cashRateId = cashRateId;
	}
	
	public String getAuthCheckStatus() {
		return authCheckStatus;
	}
	public void setAuthCheckStatus(String authCheckStatus) {
		this.authCheckStatus = authCheckStatus;
	}
	
	public BigDecimal getUnAppcashRateId() {
		return unAppcashRateId;
	}
	public void setUnAppcashRateId(BigDecimal unAppcashRateId) {
		this.unAppcashRateId = unAppcashRateId;
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
