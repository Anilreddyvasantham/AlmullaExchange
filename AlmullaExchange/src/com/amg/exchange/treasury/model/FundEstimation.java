package com.amg.exchange.treasury.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ExFundEstimation Created by Chennai ODC
 */
@Entity
@Table(name = "EX_FUND_ESTIMATION")
public class FundEstimation implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -59155724624336535L;

	private BigDecimal fundEstimtaionId;

	// private CountryMaster exFundEstimationForApplicationCountry;
	// private CompanyMaster fsCompanyMaster;
	// private CountryMaster exFundEstimationForBankCountry;
	// private CurrencyMaster exCurrenyMaster;

	private BigDecimal applicationCountryId = new BigDecimal(0);
	private BigDecimal bankCountryId = new BigDecimal(0);
	private BigDecimal currencyId = new BigDecimal(0);
	private BigDecimal companyId = new BigDecimal(0);
	private BigDecimal bankId = new BigDecimal(0);

	private Date projectionDate;
	private BigDecimal estimateNumberOfDays;
	private BigDecimal ikonRate;
	private BigDecimal previousDaysCurrentBalance;
	private BigDecimal usdCurrentBalanace;
	private BigDecimal valueDatedTransaction;
	private BigDecimal uSdValueDatedTransaction;
	private BigDecimal systemGeneratedSalesProjectionAmount;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Set<FundEstimationDetails> exFundEstimtaionDetails = new HashSet<FundEstimationDetails>(0);
	private Set<FundEstimationDays> exFundEstimationDays = new HashSet<FundEstimationDays>(0);

	private BigDecimal usdValueTotalSalesProjection;

	private BigDecimal serviceId = new BigDecimal(0);
	private BigDecimal cashAmount;
	@Column(name = "CASH_AMOUNT")
	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}
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

	private BigDecimal tomAmount;
	private BigDecimal spotAmount;

	public FundEstimation()
	{

	}

	public FundEstimation(BigDecimal fundEstimtaionId)
	{
		this.fundEstimtaionId = fundEstimtaionId;
	}

	public FundEstimation(BigDecimal fundEstimtaionId, BigDecimal applicationCountryId, BigDecimal bankCountryId, BigDecimal currencyId,
			BigDecimal companyId, BigDecimal bankId, Date projectionDate, BigDecimal estimateNumberOfDays, BigDecimal ikonRate,
			BigDecimal previousDaysCurrentBalance, BigDecimal usdCurrentBalanace, BigDecimal valueDatedTransaction, BigDecimal uSdValueDatedTransaction,
			BigDecimal systemGeneratedSalesProjectionAmount)
	{

		this.fundEstimtaionId = fundEstimtaionId;
		this.applicationCountryId = applicationCountryId;
		this.bankCountryId = bankCountryId;
		this.companyId = companyId;
		this.currencyId = currencyId;
		this.bankId = bankId;
		this.projectionDate = projectionDate;
		this.estimateNumberOfDays = estimateNumberOfDays;
		this.ikonRate = ikonRate;
		this.previousDaysCurrentBalance = previousDaysCurrentBalance;
		this.usdCurrentBalanace = usdCurrentBalanace;
		this.valueDatedTransaction = valueDatedTransaction;
		this.uSdValueDatedTransaction = uSdValueDatedTransaction;
		this.systemGeneratedSalesProjectionAmount = systemGeneratedSalesProjectionAmount;
	}

	@Id
	@GeneratedValue(generator = "ex_fund_estimation_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_fund_estimation_seq", sequenceName = "EX_FUND_ESTIMATION_SEQ", allocationSize = 1)
	@Column(name = "FUND_ESTIMATION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getFundEstimtaionId()
	{
		return this.fundEstimtaionId;
	}

	public void setFundEstimtaionId(BigDecimal fundEstimtaionId)
	{
		this.fundEstimtaionId = fundEstimtaionId;
	}

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "APPLICATION_COUNTRY_ID") public CountryMaster
	 * getExFundEstimationForApplicationCountry() { return
	 * exFundEstimationForApplicationCountry; } public void
	 * setExFundEstimationForApplicationCountry(CountryMaster
	 * exFundEstimationForApplicationCountry) {
	 * this.exFundEstimationForApplicationCountry =
	 * exFundEstimationForApplicationCountry; }
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "COMPANY_ID") public CompanyMaster
	 * getFsCompanyMaster() { return fsCompanyMaster; } public void
	 * setFsCompanyMaster(CompanyMaster fsCompanyMaster) { this.fsCompanyMaster
	 * = fsCompanyMaster; }
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "BANK_COUNTRY_ID") public CountryMaster
	 * getExFundEstimationForBankCountry() { return
	 * exFundEstimationForBankCountry; } public void
	 * setExFundEstimationForBankCountry(CountryMaster
	 * exFundEstimationForBankCountry) { this.exFundEstimationForBankCountry =
	 * exFundEstimationForBankCountry; }
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "CURRENCY_ID") public CurrencyMaster
	 * getExCurrenyMaster() { return exCurrenyMaster; } public void
	 * setExCurrenyMaster(CurrencyMaster exCurrenyMaster) { this.exCurrenyMaster
	 * = exCurrenyMaster; }
	 */

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "BANK_ID") public BankMaster getExBankMaster() {
	 * return exBankMaster; } public void setExBankMaster(BankMaster
	 * exBankMaster) { this.exBankMaster = exBankMaster; }
	 */

	@Column(name = "PROJECTION_DATE")
	public Date getProjectionDate()
	{
		return projectionDate;
	}

	public void setProjectionDate(Date projectionDate)
	{
		this.projectionDate = projectionDate;
	}

	@Column(name = "ESTIMATE_DAYS")
	public BigDecimal getEstimateNumberOfDays()
	{
		return estimateNumberOfDays;
	}

	public void setEstimateNumberOfDays(BigDecimal estimateNumberOfDays)
	{
		this.estimateNumberOfDays = estimateNumberOfDays;
	}

	@Column(name = "IKON_RATE")
	public BigDecimal getIkonRate()
	{
		return ikonRate;
	}

	public void setIkonRate(BigDecimal ikonRate)
	{
		this.ikonRate = ikonRate;
	}

	@Column(name = "PREVIOUS_DAY_CURRENCY_BALANCE")
	public BigDecimal getPreviousDaysCurrentBalance()
	{
		return previousDaysCurrentBalance;
	}

	public void setPreviousDaysCurrentBalance(BigDecimal previousDaysCurrentBalance)
	{
		this.previousDaysCurrentBalance = previousDaysCurrentBalance;
	}

	@Column(name = "CURRENT_USD_BALANCE")
	public BigDecimal getUsdCurrentBalanace()
	{
		return usdCurrentBalanace;
	}

	public void setUsdCurrentBalanace(BigDecimal usdCurrentBalanace)
	{
		this.usdCurrentBalanace = usdCurrentBalanace;
	}

	@Column(name = "VALUE_DATED_TRNX")
	public BigDecimal getValueDatedTransaction()
	{
		return valueDatedTransaction;
	}

	public void setValueDatedTransaction(BigDecimal valueDatedTransaction)
	{
		this.valueDatedTransaction = valueDatedTransaction;
	}

	@Column(name = "USD_VALUE_FOR_VALUE_DATED_TRNX")
	public BigDecimal getuSdValueDatedTransaction()
	{
		return uSdValueDatedTransaction;
	}

	public void setuSdValueDatedTransaction(BigDecimal uSdValueDatedTransaction)
	{
		this.uSdValueDatedTransaction = uSdValueDatedTransaction;
	}

	@Column(name = "SYS_CALC_SALES_PROJECTION")
	public BigDecimal getSystemGeneratedSalesProjectionAmount()
	{
		return systemGeneratedSalesProjectionAmount;
	}

	public void setSystemGeneratedSalesProjectionAmount(BigDecimal systemGeneratedSalesProjectionAmount)
	{
		this.systemGeneratedSalesProjectionAmount = systemGeneratedSalesProjectionAmount;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy()
	{
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy)
	{
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate()
	{
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate)
	{
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fundEstimtaionId")
	public Set<FundEstimationDetails> getExFundEstimtaionDetails()
	{
		return exFundEstimtaionDetails;
	}

	public void setExFundEstimtaionDetails(Set<FundEstimationDetails> exFundEstimtaionDetails)
	{
		this.exFundEstimtaionDetails = exFundEstimtaionDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fundEstimtaionId")
	public Set<FundEstimationDays> getExFundEstimationDays()
	{
		return exFundEstimationDays;
	}

	public void setExFundEstimationDays(Set<FundEstimationDays> exFundEstimationDays)
	{
		this.exFundEstimationDays = exFundEstimationDays;
	}

	@Column(name = "SERVICE_ID")
	public BigDecimal getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId)
	{
		this.serviceId = serviceId;
	}

	@Column(name = "BANK_ID")
	public BigDecimal getBankId()
	{
		return bankId;
	}

	public void setBankId(BigDecimal bankId)
	{
		this.bankId = bankId;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId()
	{
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId)
	{
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId()
	{
		return bankCountryId;
	}

	public void setBankCountryId(BigDecimal bankCountryId)
	{
		this.bankCountryId = bankCountryId;
	}

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId()
	{
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId)
	{
		this.currencyId = currencyId;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId)
	{
		this.companyId = companyId;
	}

	@Column(name = "USD_VAL_TOTAL_SALES_PROJECTION")
	public BigDecimal getUsdValueTotalSalesProjection()
	{
		return usdValueTotalSalesProjection;
	}

	public void setUsdValueTotalSalesProjection(BigDecimal usdValueTotalSalesProjection)
	{
		this.usdValueTotalSalesProjection = usdValueTotalSalesProjection;
	}

}
