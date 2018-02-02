package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_FUND_CONSOLIDATED")
public class FundConsolidatedView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal fundEstimtaionId;
	private BigDecimal applicationCountryId;
	private String bankCode;
	private String bankCountryCode;
	private BigDecimal bankCountryId;
	private String bankCountryName;
	private BigDecimal bankId;
	private String currencyCode;
	private BigDecimal currencyId;
	private BigDecimal dealAmount;
	private BigDecimal diffAmount;
	private BigDecimal foreignCurrencyBalance;
	private BigDecimal fundRequiredAmount;
	private BigDecimal ikonRate;
	private String localCurrency;
	private BigDecimal localEquivalentAmount;
	private Date projectionDate;
	private String servicedesc;
	private BigDecimal serviceId;
	private BigDecimal totalSalesProjection;
	private BigDecimal unFundedTrnx;
	private BigDecimal usdEquivalentAmount;
	private BigDecimal usdLocalCurrRate;
	
	@Id
	@Column(name = "FUND_ESTIMATION_ID")
	public BigDecimal getFundEstimtaionId() {
		return fundEstimtaionId;
	}
	public void setFundEstimtaionId(BigDecimal fundEstimtaionId) {
		this.fundEstimtaionId = fundEstimtaionId;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "BANK_COUNTRY_CODE")
	public String getBankCountryCode() {
		return bankCountryCode;
	}
	public void setBankCountryCode(String bankCountryCode) {
		this.bankCountryCode = bankCountryCode;
	}
	
	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	@Column(name = "BANK_COUNTRY_NAME")
	public String getBankCountryName() {
		return bankCountryName;
	}
	public void setBankCountryName(String bankCountryName) {
		this.bankCountryName = bankCountryName;
	}
	
	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
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
	
	@Column(name = "DEAL_AMOUNT")
	public BigDecimal getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(BigDecimal dealAmount) {
		this.dealAmount = dealAmount;
	}
	
	@Column(name = "DIFF_AMOUNT")
	public BigDecimal getDiffAmount() {
		return diffAmount;
	}
	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}
	
	@Column(name = "FOREIGN_CURRENCY_BALANCE")
	public BigDecimal getForeignCurrencyBalance() {
		return foreignCurrencyBalance;
	}
	public void setForeignCurrencyBalance(BigDecimal foreignCurrencyBalance) {
		this.foreignCurrencyBalance = foreignCurrencyBalance;
	}
	
	@Column(name = "FUND_REQUIRED_AMOUNT")
	public BigDecimal getFundRequiredAmount() {
		return fundRequiredAmount;
	}
	public void setFundRequiredAmount(BigDecimal fundRequiredAmount) {
		this.fundRequiredAmount = fundRequiredAmount;
	}
	
	@Column(name = "IKON_RATE")
	public BigDecimal getIkonRate() {
		return ikonRate;
	}
	public void setIkonRate(BigDecimal ikonRate) {
		this.ikonRate = ikonRate;
	}
	
	@Column(name = "LOCAL_CURRENCY")
	public String getLocalCurrency() {
		return localCurrency;
	}
	public void setLocalCurrency(String localCurrency) {
		this.localCurrency = localCurrency;
	}
	
	@Column(name = "LOCAL_EQUIVALENT_AMOUNT")
	public BigDecimal getLocalEquivalentAmount() {
		return localEquivalentAmount;
	}
	public void setLocalEquivalentAmount(BigDecimal localEquivalentAmount) {
		this.localEquivalentAmount = localEquivalentAmount;
	}
	
	@Column(name = "PROJECTION_DATE")
	public Date getProjectionDate() {
		return projectionDate;
	}
	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}
	
	@Column(name = "SERVICE_DESC")
	public String getServicedesc() {
		return servicedesc;
	}
	public void setServicedesc(String servicedesc) {
		this.servicedesc = servicedesc;
	}
	
	@Column(name = "SERVICE_ID")
	public BigDecimal getServiceId() {
		return serviceId;
	}
	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}
	
	@Column(name = "TOTAL_SALES_PROJECTION")
	public BigDecimal getTotalSalesProjection() {
		return totalSalesProjection;
	}
	public void setTotalSalesProjection(BigDecimal totalSalesProjection) {
		this.totalSalesProjection = totalSalesProjection;
	}
	
	@Column(name = "UNFUNDED_TRNX")
	public BigDecimal getUnFundedTrnx() {
		return unFundedTrnx;
	}
	public void setUnFundedTrnx(BigDecimal unFundedTrnx) {
		this.unFundedTrnx = unFundedTrnx;
	}
	
	@Column(name = "USD_EQUIVALENT_AMOUNT")
	public BigDecimal getUsdEquivalentAmount() {
		return usdEquivalentAmount;
	}
	public void setUsdEquivalentAmount(BigDecimal usdEquivalentAmount) {
		this.usdEquivalentAmount = usdEquivalentAmount;
	}
	
	@Column(name = "USD_LOCALCURR_RATE")
	public BigDecimal getUsdLocalCurrRate() {
		return usdLocalCurrRate;
	}
	public void setUsdLocalCurrRate(BigDecimal usdLocalCurrRate) {
		this.usdLocalCurrRate = usdLocalCurrRate;
	}
	
	
	

}
