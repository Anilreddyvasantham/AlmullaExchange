package com.amg.exchange.treasury.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ExFundEstimation Created by Rabil
 */
@Entity
@Table(name = "EX_FUND_ESTIMATION_ALERT")
public class FundEstimationAlert implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -59155724624336535L;

	private BigDecimal fundEstimtaionAlertId;
	private BigDecimal fundEstimtaionId = new BigDecimal(0);
	private BigDecimal applicationCountryId = new BigDecimal(0);
	private BigDecimal bankCountryId = new BigDecimal(0);
	private BigDecimal currencyId = new BigDecimal(0);
	private BigDecimal companyId = new BigDecimal(0);
	private BigDecimal bankId = new BigDecimal(0);
	private BigDecimal serviceId = new BigDecimal(0);
	private Date projectionDate;

	private BigDecimal exchangeRate = new BigDecimal(0);
	private BigDecimal salesProjectionAmount = new BigDecimal(0);
	private String createdBy = new String();
	private Date createdDate;
	private String modifiedBy = new String();
	private Date modifiedDate;
	private Date emailDate;

	private String isActive = new String();

	@Id
	@GeneratedValue(generator = "ex_fund_estimation_alert_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_fund_estimation_alert_seq", sequenceName = "EX_FUND_ESTIMATION_ALERT_SEQ", allocationSize = 1)
	@Column(name = "FUND_ESTIMATION_ALERT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getFundEstimtaionAlertId() {
		return fundEstimtaionAlertId;
	}

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

	public void setFundEstimtaionAlertId(BigDecimal fundEstimtaionAlertId) {
		this.fundEstimtaionAlertId = fundEstimtaionAlertId;
	}

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
