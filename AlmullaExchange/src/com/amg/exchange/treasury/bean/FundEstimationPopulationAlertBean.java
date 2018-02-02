package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class FundEstimationPopulationAlertBean {

	private BigDecimal applicationCountryId = new BigDecimal(0);
	private BigDecimal bankCountryId = new BigDecimal(0);
	private BigDecimal currencyId = new BigDecimal(0);
	private BigDecimal companyId = new BigDecimal(0);
	private BigDecimal serviceId = new BigDecimal(0);
	private String serviceMasterDesc = new String();
	private BigDecimal bankId = new BigDecimal(0);
	private String bankShortName = new String();
	private BigDecimal foreignCurrencyBalance = new BigDecimal(0);
	private BigDecimal unfundedTrnx = new BigDecimal(0);
	private BigDecimal salesProjAmnt = new BigDecimal(0);
	private BigDecimal totalProjectionAmount = new BigDecimal(0);
	private BigDecimal fundEstimationId = new BigDecimal(0);
	private BigDecimal srNo = new BigDecimal(0);
	private String projectionDate = new String();
	private BigDecimal exchangeRate = new BigDecimal(0);
	private BigDecimal fundEstimtaionAlertId = new BigDecimal(0);
	private String isActive = new String();

	String actionType = new String();

	private BigDecimal totalSalesProjectionAmount = new BigDecimal(0);

	private Date emailDate;

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceMasterDesc() {
		return serviceMasterDesc;
	}

	public void setServiceMasterDesc(String serviceMasterDesc) {
		this.serviceMasterDesc = serviceMasterDesc;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getBankShortName() {
		return bankShortName;
	}

	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}

	public BigDecimal getForeignCurrencyBalance() {
		return foreignCurrencyBalance;
	}

	public void setForeignCurrencyBalance(BigDecimal foreignCurrencyBalance) {
		this.foreignCurrencyBalance = foreignCurrencyBalance;
	}

	public BigDecimal getUnfundedTrnx() {
		return unfundedTrnx;
	}

	public void setUnfundedTrnx(BigDecimal unfundedTrnx) {
		this.unfundedTrnx = unfundedTrnx;
	}

	public BigDecimal getSalesProjAmnt() {
		return salesProjAmnt;
	}

	public void setSalesProjAmnt(BigDecimal salesProjAmnt) {
		this.salesProjAmnt = salesProjAmnt;
	}

	public BigDecimal getTotalProjectionAmount() {
		return totalProjectionAmount;
	}

	public void setTotalProjectionAmount(BigDecimal totalProjectionAmount) {
		this.totalProjectionAmount = totalProjectionAmount;
	}

	public BigDecimal getFundEstimationId() {
		return fundEstimationId;
	}

	public void setFundEstimationId(BigDecimal fundEstimationId) {
		this.fundEstimationId = fundEstimationId;
	}

	public BigDecimal getSrNo() {
		return srNo;
	}

	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}

	public String getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(String projectionDate) {
		this.projectionDate = projectionDate;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Date getEmailDate() {
		return emailDate;
	}

	public void setEmailDate(Date emailDate) {
		this.emailDate = emailDate;
	}

	public BigDecimal getTotalSalesProjectionAmount() {
		return totalSalesProjectionAmount;
	}

	public void setTotalSalesProjectionAmount(BigDecimal totalSalesProjectionAmount) {
		this.totalSalesProjectionAmount = totalSalesProjectionAmount;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public BigDecimal getFundEstimtaionAlertId() {
		return fundEstimtaionAlertId;
	}

	public void setFundEstimtaionAlertId(BigDecimal fundEstimtaionAlertId) {
		this.fundEstimtaionAlertId = fundEstimtaionAlertId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
}
