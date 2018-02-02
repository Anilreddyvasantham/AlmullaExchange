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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/**
 * ExFundEstimation Created by Chennai ODC
 */
@Entity
@Table(name = "EX_FUND_ESTIMATION_DETAILS")
public class FundEstimationDetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private BigDecimal fundEstimtaionDetailsId;
	
	private FundEstimation fundEstimtaionId;
	
/*	private CountryMaster exFundEstimtaionDetailsForApplicationCountry;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster exFundEstimtaionDeatilsForBankCountry;
	private CurrencyMaster exCurrenyMaster;*/
	
	//private BankAccountDetails exFundEstimationDetails;
	
	private BigDecimal exFundEstimationDetails;
	

	//private BankMaster exBankMaster;
	
	
	private BigDecimal applicationCountryId = new BigDecimal(0);
	private BigDecimal bankCountryId = new BigDecimal(0);
	private BigDecimal currencyId = new BigDecimal(0);
	private BigDecimal companyId = new BigDecimal(0);
	
	
	private BigDecimal bankId;
	
	
	
	
	private Date projectionDate;
	private BigDecimal ikonRate;
	private BigDecimal salesForeignCurrencyProjection;
	private BigDecimal usdValue;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	
	
	private Set<FundEstimationDays> exfundEstimationDetailsId = new HashSet<FundEstimationDays>(0);
	
	
	

	
	
	
	
	
	public FundEstimationDetails() {

	}
	
	public FundEstimationDetails(BigDecimal fundEstimtaionDetailsId) {
		this.fundEstimtaionDetailsId =fundEstimtaionDetailsId;
	}

	public FundEstimationDetails(BigDecimal fundEstimtaionDetailsId,
			FundEstimation fundEstimtaionId,
			BigDecimal applicationCountryId,
			BigDecimal bankCountryId,
			BigDecimal currencyId,
			BigDecimal companyId,
			 BigDecimal bankId,
			//BankMaster exBankMaster,
			Date projectionDate, BigDecimal ikonRate,
			BigDecimal salesForeignCurrencyProjection, BigDecimal usdValue,
			//BankAccountDetails exFundEstimationDetails
			BigDecimal exFundEstimationDetails) 
	{
		this.fundEstimtaionDetailsId = fundEstimtaionDetailsId;
		this.fundEstimtaionId = fundEstimtaionId;
		this.applicationCountryId =applicationCountryId;
		this.bankCountryId =bankCountryId;
		this.companyId 		=companyId;
		this.currencyId     = currencyId;
		this.bankId = bankId;
		//this.exBankMaster = exBankMaster;
		this.projectionDate = projectionDate;
		this.ikonRate = ikonRate;
		this.salesForeignCurrencyProjection = salesForeignCurrencyProjection;
		this.usdValue = usdValue;
		//this.exFundEstimationDetails = exFundEstimationDetails;
		this.exFundEstimationDetails = exFundEstimationDetails;
	}
	
	@Id
	@GeneratedValue(generator="ex_fund_estimation_details_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_fund_estimation_details_seq",sequenceName="EX_FUND_ESTIMATION_DETAILS_SEQ",allocationSize=1)
	@Column(name = "FUND_ESTIMATION_DETAILS_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getFundEstimtaionDetailsId() {
		return fundEstimtaionDetailsId;
	}

	public void setFundEstimtaionDetailsId(BigDecimal fundEstimtaionDetailsId) {
		this.fundEstimtaionDetailsId = fundEstimtaionDetailsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUND_ESTIMATION_ID")
	public FundEstimation getFundEstimtaionId() {
		return fundEstimtaionId;
	}

	public void setFundEstimtaionId(FundEstimation fundEstimtaionId) {
		this.fundEstimtaionId = fundEstimtaionId;
	}

	@Column(name = "USD_VALUE")
	public BigDecimal getUsdValue() {
		return usdValue;
	}

	public void setUsdValue(BigDecimal usdValue) {
		this.usdValue = usdValue;
	}

	@Column(name = "PROJECTION_DATE")
	public Date getProjectionDate() {
		return projectionDate;
	}

	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}

	@Column(name = "IKON_RATE")
	public BigDecimal getIkonRate() {
		return ikonRate;
	}
	public void setIkonRate(BigDecimal ikonRate) {
		this.ikonRate = ikonRate;
	}

	@Column(name = "SALES_FC_PROJECTION")
	public BigDecimal getSalesForeignCurrencyProjection() {
		return salesForeignCurrencyProjection;
	}

	public void setSalesForeignCurrencyProjection(
			BigDecimal salesForeignCurrencyProjection) {
		this.salesForeignCurrencyProjection = salesForeignCurrencyProjection;
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fundEstimationDetailsId")
	public Set<FundEstimationDays> getExfundEstimationDetailsId() {
		return exfundEstimationDetailsId;
	}

	public void setExfundEstimationDetailsId(Set<FundEstimationDays> exfundEstimationDetailsId) {
		this.exfundEstimationDetailsId = exfundEstimationDetailsId;
	}


	
	@Column(name = "BANK_ACCT_DET_ID")
	public BigDecimal getExFundEstimationDetails() {
		return exFundEstimationDetails;
	}
	public void setExFundEstimationDetails(BigDecimal exFundEstimationDetails) {
		this.exFundEstimationDetails = exFundEstimationDetails;
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

	@Column(name="BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	@Column(name="CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name="COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	 
	
	

	}
