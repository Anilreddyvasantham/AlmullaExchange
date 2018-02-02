package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EX_V_UNAPPR_CASHRATES")
public class ViewUnApprovedCashRate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal srNo;
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
	private BigDecimal unAppcashRateId;
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
	
	@Column(name = "BASE_CURRENCY_ID")
	public BigDecimal getBaseCurrencyId() {
		return baseCurrencyId;
	}
	public void setBaseCurrencyId(BigDecimal baseCurrencyId) {
		this.baseCurrencyId = baseCurrencyId;
	}
	
	@Column(name = "BASE_CURRENCY_CODE")
	public String getBaseCurrencyCode() {
		return baseCurrencyCode;
	}
	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}
	
	@Column(name = "BASE_CURRENCY_NAME")
	public String getBaseCurrencyName() {
		return baseCurrencyName;
	}
	public void setBaseCurrencyName(String baseCurrencyName) {
		this.baseCurrencyName = baseCurrencyName;
	}
	
	@Column(name = "ALTERNATIVE_CURRENCY_ID")
	public BigDecimal getAlternativeCurrencyId() {
		return alternativeCurrencyId;
	}
	public void setAlternativeCurrencyId(BigDecimal alternativeCurrencyId) {
		this.alternativeCurrencyId = alternativeCurrencyId;
	}
	
	@Column(name = "ALTERNATIVE_CURRENCY_CODE")
	public String getAlternativeCurrencyCode() {
		return alternativeCurrencyCode;
	}
	public void setAlternativeCurrencyCode(String alternativeCurrencyCode) {
		this.alternativeCurrencyCode = alternativeCurrencyCode;
	}
	
	@Column(name = "ALTERNATIVE_CURRENCY_NAME")
	public String getAlternativeCurrencyName() {
		return alternativeCurrencyName;
	}
	public void setAlternativeCurrencyName(String alternativeCurrencyName) {
		this.alternativeCurrencyName = alternativeCurrencyName;
	}
	
	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	@Column(name = "LOCCOD")
	public BigDecimal getLocCod() {
		return locCod;
	}
	public void setLocCod(BigDecimal locCod) {
		this.locCod = locCod;
	}
	
	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	@Column(name = "SAL_MIN_RATE")
	public BigDecimal getSalMinRate() {
		return salMinRate;
	}
	public void setSalMinRate(BigDecimal salMinRate) {
		this.salMinRate = salMinRate;
	}
	
	@Column(name = "SAL_MAX_RATE")
	public BigDecimal getSalMaxRate() {
		return salMaxRate;
	}
	public void setSalMaxRate(BigDecimal salMaxRate) {
		this.salMaxRate = salMaxRate;
	}
	
	@Column(name = "BUY_MIN_RATE")
	public BigDecimal getBuyMinRate() {
		return buyMinRate;
	}
	public void setBuyMinRate(BigDecimal buyMinRate) {
		this.buyMinRate = buyMinRate;
	}
	
	@Column(name = "BUY_MAX_RATE")
	public BigDecimal getBuyMaxRate() {
		return buyMaxRate;
	}
	public void setBuyMaxRate(BigDecimal buyMaxRate) {
		this.buyMaxRate = buyMaxRate;
	}
	
	@Column(name = "PRV_SAL_MIN_RATE")
	public BigDecimal getPrvSalMinRate() {
		return prvSalMinRate;
	}
	public void setPrvSalMinRate(BigDecimal prvSalMinRate) {
		this.prvSalMinRate = prvSalMinRate;
	}
	
	@Column(name = "PRV_SAL_MAX_RATE")
	public BigDecimal getPrvSalMaxRate() {
		return prvSalMaxRate;
	}
	public void setPrvSalMaxRate(BigDecimal prvSalMaxRate) {
		this.prvSalMaxRate = prvSalMaxRate;
	}
	
	@Column(name = "PRV_BUY_MIN_RATE")
	public BigDecimal getPrvBuyMinRate() {
		return prvBuyMinRate;
	}
	public void setPrvBuyMinRate(BigDecimal prvBuyMinRate) {
		this.prvBuyMinRate = prvBuyMinRate;
	}
	
	@Column(name = "PRV_BUY_MAX_RATE")
	public BigDecimal getPrvBuyMaxRate() {
		return prvBuyMaxRate;
	}
	public void setPrvBuyMaxRate(BigDecimal prvBuyMaxRate) {
		this.prvBuyMaxRate = prvBuyMaxRate;
	}
	
	@Column(name = "AUTH_CHECK_IND")
	public String getAuthCheckInd() {
		return authCheckInd;
	}
	public void setAuthCheckInd(String authCheckInd) {
		this.authCheckInd = authCheckInd;
	}
	
	@Column(name = "CASH_RATE_ID")
	public BigDecimal getCashRateId() {
		return cashRateId;
	}
	public void setCashRateId(BigDecimal cashRateId) {
		this.cashRateId = cashRateId;
	}

	@Column(name = "UNAPP_CASHRATE_ID")
	public BigDecimal getUnAppcashRateId() {
		return unAppcashRateId;
	}
	public void setUnAppcashRateId(BigDecimal unAppcashRateId) {
		this.unAppcashRateId = unAppcashRateId;
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
