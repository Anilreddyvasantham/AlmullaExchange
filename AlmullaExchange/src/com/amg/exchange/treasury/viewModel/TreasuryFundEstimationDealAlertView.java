package com.amg.exchange.treasury.viewModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_FUND_ESTIMATION_ALERT")
public class TreasuryFundEstimationDealAlertView implements Serializable {

	private BigDecimal fundEstimtaionAlertId = new BigDecimal(0);
	private BigDecimal fundEstimtaionId = new BigDecimal(0);
	private BigDecimal applicationCountryId = new BigDecimal(0);
	private BigDecimal bankCountryId = new BigDecimal(0);
	private BigDecimal currencyId = new BigDecimal(0);
	private BigDecimal companyId = new BigDecimal(0);
	private BigDecimal bankId = new BigDecimal(0);
	private BigDecimal serviceId = new BigDecimal(0);
	private Date projectionDate;

	private BigDecimal exchangeRate = new BigDecimal(0);

	private BigDecimal totalSalesProjectionAmount = new BigDecimal(0);

	private BigDecimal salesProjectionAmount = new BigDecimal(0);
	private String createdBy = new String();
	private Date createdDate;
	private String modifiedBy = new String();
	private Date modifiedDate;
	private Date emailDate;
	private BigDecimal srNo = new BigDecimal(0);
	private String bankShortName = new String();

	private String isActive = new String();

	@Column(name = "FUND_ESTIMATION_ID")
	public BigDecimal getFundEstimtaionId() {
		return this.fundEstimtaionId;
	}

	public void setFundEstimtaionId(BigDecimal fundEstimtaionId) {
		this.fundEstimtaionId = fundEstimtaionId;
	}

	@Column(name = "PROJECTION_DATE")
	public Date getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
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

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "SERVICE_ID")
	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Column(name = "EXCHANGE_RATE")
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "SALES_PROJECTION")
	public BigDecimal getSalesProjectionAmount() {
		return salesProjectionAmount;
	}

	public void setSalesProjectionAmount(BigDecimal salesProjectionAmount) {
		this.salesProjectionAmount = salesProjectionAmount;
	}

	@Column(name = "EMAIL_DATE")
	public Date getEmailDate() {
		return emailDate;
	}

	public void setEmailDate(Date emailDate) {
		this.emailDate = emailDate;
	}

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	@Id
	@Column(name = "SRNO")
	public BigDecimal getSrNo() {
		return srNo;
	}

	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}

	@Column(name = "BANK_CODE")
	public String getBankShortName() {
		return bankShortName;
	}

	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}

	@Column(name = "SYS_CALC_SALES_PROJECTION")
	public BigDecimal getTotalSalesProjectionAmount() {
		return totalSalesProjectionAmount;
	}

	public void setTotalSalesProjectionAmount(BigDecimal totalSalesProjectionAmount) {
		this.totalSalesProjectionAmount = totalSalesProjectionAmount;
	}

	@Column(name = "FUND_ESTIMATION_ALERT_ID")
	public BigDecimal getFundEstimtaionAlertId() {
		return fundEstimtaionAlertId;
	}

	public void setFundEstimtaionAlertId(BigDecimal fundEstimtaionAlertId) {
		this.fundEstimtaionAlertId = fundEstimtaionAlertId;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
