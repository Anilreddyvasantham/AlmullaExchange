package com.amg.exchange.treasury.viewModel;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_FUND_ESTIMATION_DEALS")
public class TreasuryFundestimationAlertView implements Serializable {

	private BigDecimal applicationCountryId = new BigDecimal(0);
	private BigDecimal bankCountryId = new BigDecimal(0);
	private BigDecimal currencyId = new BigDecimal(0);
	private BigDecimal serviceId = new BigDecimal(0);

	private BigDecimal companyId = new BigDecimal(0);

	private BigDecimal bankId = new BigDecimal(0);
	private String bankShortName = new String();
	private BigDecimal salesProjAmnt = new BigDecimal(0);
	private BigDecimal srNo = new BigDecimal(0);
	private BigDecimal fundEstimationId = new BigDecimal(0);
	private String projectionDate = new String();

	// private String isActive = new String();

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	/**
	 * @param applicationCountryId
	 *            the applicationCountryId to set
	 */
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	/**
	 * @return the bankCountryId
	 */

	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	/**
	 * @param bankCountryId
	 *            the bankCountryId to set
	 */
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	/**
	 * @return the currencyId
	 */

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	/**
	 * @param currencyId
	 *            the currencyId to set
	 */
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * @return the serviceId
	 */
	@Column(name = "SERVICE_ID")
	public BigDecimal getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId
	 *            the serviceId to set
	 */
	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the bankId
	 */
	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}

	/**
	 * @param bankId
	 *            the bankId to set
	 */
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the bankShortName
	 */
	@Column(name = "BANK_SHORT_NAME")
	public String getBankShortName() {
		return bankShortName;
	}

	/**
	 * @param bankShortName
	 *            the bankShortName to set
	 */
	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}

	/**
	 * @return the salesProjAmnt
	 */
	@Column(name = "SYS_CALC_SALES_PROJECTION")
	public BigDecimal getSalesProjAmnt() {
		return salesProjAmnt;
	}

	/**
	 * @param salesProjAmnt
	 *            the salesProjAmnt to set
	 */
	public void setSalesProjAmnt(BigDecimal salesProjAmnt) {
		this.salesProjAmnt = salesProjAmnt;
	}

	/**
	 * @return the srNo
	 */
	@Id
	@Column(name = "SRNO")
	public BigDecimal getSrNo() {
		return srNo;
	}

	/**
	 * @param srNo
	 *            the srNo to set
	 */
	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}

	@Column(name = "FUND_ESTIMATION_ID")
	public BigDecimal getFundEstimationId() {
		return fundEstimationId;
	}

	public void setFundEstimationId(BigDecimal fundEstimationId) {
		this.fundEstimationId = fundEstimationId;
	}

	@Column(name = "PROJECTION_DATE")
	public String getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(String projectionDate) {
		this.projectionDate = projectionDate;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

}
