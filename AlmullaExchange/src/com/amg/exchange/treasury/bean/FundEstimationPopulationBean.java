package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FundEstimationPopulationBean {

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
	private BigDecimal additionalSalesProjAmnt = new BigDecimal(0);
	private BigDecimal totalProjectionAmount = new BigDecimal(0);
	private BigDecimal usdTotalProjectionAmount = new BigDecimal(0);
	private BigDecimal fundEstimationId = new BigDecimal(0);
	private BigDecimal srNo = new BigDecimal(0);
	private List<FundEstimationDaysDtable> addingDaysForCalculation;
	private String salesProjAmntForDisplay;
	private BigDecimal additionalSalesProjAmntForDisplay = new BigDecimal(0);
	private String totalProjectionAmountForDisplay ;
	private String usdTotalProjectionAmountForDisplay ;
	private String foreignCurrencyBalanceForDisplay;
	private String unfundedTrnxForDisplay;
	private BigDecimal  cashOrTomOrSpotAmount=new BigDecimal(0);
	public BigDecimal getSpotAmount() {
		return spotAmount;
	}

	public void setSpotAmount(BigDecimal spotAmount) {
		this.spotAmount = spotAmount;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getTomAmount() {
		return tomAmount;
	}

	public void setTomAmount(BigDecimal tomAmount) {
		this.tomAmount = tomAmount;
	}

	private BigDecimal spotAmount=new BigDecimal(0);
	private BigDecimal cashAmount=new BigDecimal(0);
	private BigDecimal tomAmount=new BigDecimal(0);
	
	//new added
	private BigDecimal companyId;
	private Date projectionDate;
	//private BigDecimal usdTotalsalesProjAmnt;
	
	/**
	 * @return the applicationCountryId
	 */
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
	 * @return the additionalSalesProjAmnt
	 */
	public BigDecimal getAdditionalSalesProjAmnt() {
		return additionalSalesProjAmnt;
	}

	/**
	 * @param additionalSalesProjAmnt
	 *            the additionalSalesProjAmnt to set
	 */
	public void setAdditionalSalesProjAmnt(BigDecimal additionalSalesProjAmnt) {
		this.additionalSalesProjAmnt = additionalSalesProjAmnt;
	}

	/**
	 * @return the totalProjectionAmount
	 */
	public BigDecimal getTotalProjectionAmount() {
		return totalProjectionAmount;
	}

	/**
	 * @param totalProjectionAmount
	 *            the totalProjectionAmount to set
	 */
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

	public List<FundEstimationDaysDtable> getAddingDaysForCalculation() {
		return addingDaysForCalculation;
	}

	public void setAddingDaysForCalculation(
			List<FundEstimationDaysDtable> addingDaysForCalculation) {
		this.addingDaysForCalculation = addingDaysForCalculation;
	}

	public String getSalesProjAmntForDisplay() {
		return salesProjAmntForDisplay;
	}

	public void setSalesProjAmntForDisplay(String salesProjAmntForDisplay) {
		this.salesProjAmntForDisplay = salesProjAmntForDisplay;
	}

	public BigDecimal getAdditionalSalesProjAmntForDisplay() {
		return additionalSalesProjAmntForDisplay;
	}

	public void setAdditionalSalesProjAmntForDisplay(
			BigDecimal additionalSalesProjAmntForDisplay) {
		this.additionalSalesProjAmntForDisplay = additionalSalesProjAmntForDisplay;
	}

	public String getTotalProjectionAmountForDisplay() {
		return totalProjectionAmountForDisplay;
	}

	public void setTotalProjectionAmountForDisplay(
			String totalProjectionAmountForDisplay) {
		this.totalProjectionAmountForDisplay = totalProjectionAmountForDisplay;
	}

	public String getForeignCurrencyBalanceForDisplay() {
		return foreignCurrencyBalanceForDisplay;
	}

	public void setForeignCurrencyBalanceForDisplay(
			String foreignCurrencyBalanceForDisplay) {
		this.foreignCurrencyBalanceForDisplay = foreignCurrencyBalanceForDisplay;
	}

	public String getUnfundedTrnxForDisplay() {
		return unfundedTrnxForDisplay;
	}

	public void setUnfundedTrnxForDisplay(String unfundedTrnxForDisplay) {
		this.unfundedTrnxForDisplay = unfundedTrnxForDisplay;
	}

	public String getUsdTotalProjectionAmountForDisplay() {
		return usdTotalProjectionAmountForDisplay;
	}

	public void setUsdTotalProjectionAmountForDisplay(String usdTotalProjectionAmountForDisplay) {
		this.usdTotalProjectionAmountForDisplay = usdTotalProjectionAmountForDisplay;
	}

	public BigDecimal getUsdTotalProjectionAmount() {
		return usdTotalProjectionAmount;
	}

	public void setUsdTotalProjectionAmount(BigDecimal usdTotalProjectionAmount) {
		this.usdTotalProjectionAmount = usdTotalProjectionAmount;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public Date getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}

	/*public BigDecimal getUsdTotalsalesProjAmnt() {
		return usdTotalsalesProjAmnt;
	}

	public void setUsdTotalsalesProjAmnt(BigDecimal usdTotalsalesProjAmnt) {
		this.usdTotalsalesProjAmnt = usdTotalsalesProjAmnt;
	}*/

	public BigDecimal getCashOrTomOrSpotAmount() {
		return cashOrTomOrSpotAmount;
	}

	public void setCashOrTomOrSpotAmount(BigDecimal cashOrTomOrSpotAmount) {
		this.cashOrTomOrSpotAmount = cashOrTomOrSpotAmount;
	}
	
	

	
	
}
