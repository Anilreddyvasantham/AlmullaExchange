package com.amg.exchange.treasury.viewModel;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_ESTIMATION_DAYS")
public class TreasuryEstimationDaysView implements Serializable {

	private BigDecimal srNo = new BigDecimal(0);
	private BigDecimal applicationCountryId = new BigDecimal(0);
	private BigDecimal bankCountryId = new BigDecimal(0);
	private BigDecimal currencyId = new BigDecimal(0);
	private BigDecimal iKonRate = new BigDecimal(0);
	private BigDecimal estimationDays = new BigDecimal(0);
	private String projectionDate = new String();

	@Id
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

	@Id
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
	@Id
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

	@Column(name = "IKON_RATE")
	public BigDecimal getiKonRate() {
		return iKonRate;
	}

	public void setiKonRate(BigDecimal iKonRate) {
		this.iKonRate = iKonRate;
	}

	@Column(name = "ESTIMATE_DAYS")
	public BigDecimal getEstimationDays() {
		return estimationDays;
	}

	public void setEstimationDays(BigDecimal estimationDays) {
		this.estimationDays = estimationDays;
	}

	@Column(name = "PROJECTION_DATE")
	public String getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(String projectionDate) {
		this.projectionDate = projectionDate;
	}
}