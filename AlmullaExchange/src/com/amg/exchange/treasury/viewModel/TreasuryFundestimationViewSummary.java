package com.amg.exchange.treasury.viewModel;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_FUNDESTIMATION_SUMMARY")
public class TreasuryFundestimationViewSummary implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal applicationCountryId = new BigDecimal(0);
	private BigDecimal bankCountryId = new BigDecimal(0);
	private BigDecimal currencyId = new BigDecimal(0);
	private BigDecimal serviceId = new BigDecimal(0);
	private String serviceMasterDesc = new String();
	private BigDecimal bankId = new BigDecimal(0);
	private String bankShortName = new String();
	private BigDecimal foreignCurrencyBalance = new BigDecimal(0);
	private BigDecimal unfundedTrnx = new BigDecimal(0);
	private BigDecimal salesProjAmnt = new BigDecimal(0);
	private BigDecimal srNo = new BigDecimal(0);
	private BigDecimal companyId;
	private Date projectionDate;
	private BigDecimal usdTotalsalesProjAmnt;
	private BigDecimal tomAmount;
	private BigDecimal spotAmount;
	private BigDecimal cashAmount;
	
	@Column(name = "TOM_AMOUNT")
	public BigDecimal getTomAmount() {
		return tomAmount;
	}

	public void setTomAmount(BigDecimal tomAmount) {
		this.tomAmount = tomAmount;
	}
	@Column(name = "SPOT_AMOUNT")
	public BigDecimal getSpotAmount() {
		return spotAmount;
	}

	public void setSpotAmount(BigDecimal spotAmount) {
		this.spotAmount = spotAmount;
	}
	@Column(name = "CASH_AMOUNT")
	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}



	
	/**
	 * @return the applicationCountryId
	 */

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
	 * @return the serviceMasterDesc
	 */
	@Column(name = "SERVICE_MASTER_DESC")
	public String getServiceMasterDesc() {
		return serviceMasterDesc;
	}

	/**
	 * @param serviceMasterDesc
	 *            the serviceMasterDesc to set
	 */
	public void setServiceMasterDesc(String serviceMasterDesc) {
		this.serviceMasterDesc = serviceMasterDesc;
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
	 * @return the foreignCurrencyBalance
	 */
	@Column(name = "FOREIGN_CURRENCY_BALANCE")
	public BigDecimal getForeignCurrencyBalance() {
		return foreignCurrencyBalance;
	}

	/**
	 * @param foreignCurrencyBalance
	 *            the foreignCurrencyBalance to set
	 */
	public void setForeignCurrencyBalance(BigDecimal foreignCurrencyBalance) {
		this.foreignCurrencyBalance = foreignCurrencyBalance;
	}

	/**
	 * @return the unfundedTrnx
	 */
	@Column(name = "UNFUNDED_TRNX")
	public BigDecimal getUnfundedTrnx() {
		return unfundedTrnx;
	}

	/**
	 * @param unfundedTrnx
	 *            the unfundedTrnx to set
	 */
	public void setUnfundedTrnx(BigDecimal unfundedTrnx) {
		this.unfundedTrnx = unfundedTrnx;
	}

	/**
	 * @return the salesProjAmnt
	 */
	@Column(name = "SALES_PROJECTION_AMT")
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

	@Column(name="COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
   
	@Column(name="PROJECTION_DATE")
	public Date getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}
	
	@Column(name="USD_VAL_TOTAL_SALES_PROJECTION")
	public BigDecimal getUsdTotalsalesProjAmnt() {
		return usdTotalsalesProjAmnt;
	}

	public void setUsdTotalsalesProjAmnt(BigDecimal usdTotalsalesProjAmnt) {
		this.usdTotalsalesProjAmnt = usdTotalsalesProjAmnt;
	}

}