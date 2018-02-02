package com.amg.exchange.treasury.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "EX_FUND_ESTIMATION_DAYS")
public class FundEstimationDays implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal fundEstimtaionDaysId;
	
	private FundEstimation fundEstimtaionId;
	private FundEstimationDetails fundEstimationDetailsId;
	
	
	
	
	
/*	private CountryMaster exFundEstimationDaysApplicationCountry;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster exFundEstimationDaysBankCountry;
	private CurrencyMaster exCurrenyMaster;
	*/
	
	
	
	
	private BigDecimal applicationCountryId = new BigDecimal(0);
	private BigDecimal bankCountryId = new BigDecimal(0);
	private BigDecimal currencyId = new BigDecimal(0);
	private BigDecimal companyId = new BigDecimal(0);
	
	private BigDecimal bankId;
	
	private Date projectionDate;
	private Date estimateDate;
	private Date previousMonthDate1;
	private Date previousMonthDate2;
	private Date previousMonthDate3;
/*	private Date previousMonthDate4;
	private Date previousMonthDate5;
	private Date previousMonthDate6;
	private Date previousMonthDate7;
	private Date previousMonthDate8;
	private Date previousMonthDate9;*/
	private String previousMonthValue1;
	private String previousMonthValue2;
	private String previousMonthValue3;
/*	private String previousMonthValue4;
	private String previousMonthValue5;
	private String previousMonthValue6;
	private String previousMonthValue7;
	private String previousMonthValue8;
	private String previousMonthValue9;*/
	private Date previousMonthWeekDate1;
	private Date previousMonthWeekDate2;
	private Date previousMonthWeekDate3;
/*	private Date previousMonthWeekDate4;
	private Date previousMonthWeekDate5;
	private Date previousMonthWeekDate6;
	private Date previousMonthWeekDate7;
	private Date previousMonthWeekDate8;
	private Date previousMonthWeekDate9;*/
	private String previousMonthWeekValue1;
	private String previousMonthWeekValue2;
	private String previousMonthWeekValue3;
/*	private String previousMonthWeekValue4;
	private String previousMonthWeekValue5;
	private String previousMonthWeekValue6;
	private String previousMonthWeekValue7;
	private String previousMonthWeekValue8;
	private String previousMonthWeekValue9;*/
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	private BigDecimal serviceId;
	
	public FundEstimationDays(){

	}

	public FundEstimationDays(BigDecimal fundEstimtaionDaysId,
			FundEstimation fundEstimtaionId,
			FundEstimationDetails fundEstimationDetailsId,
			BigDecimal applicationCountryId,
			BigDecimal bankCountryId,
			BigDecimal currencyId,
			BigDecimal companyId,
			BigDecimal bankId,
			Date projectionDate, Date estimateDate,
			Date previousMonthDate1, Date previousMonthDate2,
			Date previousMonthDate3, String previousMonthValue1,
			String previousMonthValue2, String previousMonthValue3,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate,BigDecimal serviceId) {
		super();
		this.fundEstimtaionDaysId = fundEstimtaionDaysId;
		this.fundEstimtaionId = fundEstimtaionId;
		this.fundEstimationDetailsId = fundEstimationDetailsId;
		this.applicationCountryId =applicationCountryId;
		this.bankCountryId =bankCountryId;
		this.companyId 		=companyId;
		this.currencyId     = currencyId;
		this.bankId=bankId;
		this.projectionDate = projectionDate;
		this.estimateDate = estimateDate;
		this.previousMonthDate1 = previousMonthDate1;
		this.previousMonthDate2 = previousMonthDate2;
		this.previousMonthDate3 = previousMonthDate3;
		this.previousMonthValue1 = previousMonthValue1;
		this.previousMonthValue2 = previousMonthValue2;
		this.previousMonthValue3 = previousMonthValue3;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.serviceId=serviceId;
	}
	
	@Id
	@GeneratedValue(generator="ex_fund_estimation_days_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_fund_estimation_days_seq",sequenceName="EX_FUND_ESTIMATION_DAYS_SEQ",allocationSize=1)
	@Column(name = "FUND_ESTIMATION_DAYS_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getFundEstimtaionDaysId() {
		return fundEstimtaionDaysId;
	}

	public void setFundEstimtaionDaysId(BigDecimal fundEstimtaionDaysId) {
		this.fundEstimtaionDaysId = fundEstimtaionDaysId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUND_ESTIMATION_DETAILS_ID")
	public FundEstimationDetails getFundEstimationDetailsId() {
		return fundEstimationDetailsId;
	}

	public void setFundEstimationDetailsId(
			FundEstimationDetails fundEstimationDetailsId) {
		this.fundEstimationDetailsId = fundEstimationDetailsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUND_ESTIMATION_ID")
	public FundEstimation getFundEstimtaionId() {
		return fundEstimtaionId;
	}

	public void setFundEstimtaionId(FundEstimation fundEstimtaionId) {
		this.fundEstimtaionId = fundEstimtaionId;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getExFundEstimationDaysApplicationCountry() {
		return exFundEstimationDaysApplicationCountry;

	}

	public void setExFundEstimationDaysApplicationCountry(
			CountryMaster exFundEstimationForApplicationCountry) {
		this.exFundEstimationDaysApplicationCountry = exFundEstimationForApplicationCountry;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_COUNTRY_ID")
	public CountryMaster getExFundEstimationDaysBankCountry() {
		return exFundEstimationDaysBankCountry;
	}

	public void setExFundEstimationDaysBankCountry(
			CountryMaster exFundEstimationDaysBankCountry) {
		this.exFundEstimationDaysBankCountry = exFundEstimationDaysBankCountry;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrenyMaster() {
		return exCurrenyMaster;
	}

	public void setExCurrenyMaster(CurrencyMaster exCurrenyMaster) {
		this.exCurrenyMaster = exCurrenyMaster;
	}
*/
/*	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getExBankMaster() {
		return exBankMaster;
	}

	public void setExBankMaster(BankMaster exBankMaster) {
		this.exBankMaster = exBankMaster;
	}
*/

	@Column(name = "PROJECTION_DATE")
	public Date getProjectionDate() {
		return projectionDate;
	}

	@Column(name = " ESTIMATE_DATE")
	public Date getEstimateDate() {
		return estimateDate;
	}

	public void setEstimateDate(Date estimateDate) {
		this.estimateDate = estimateDate;
	}

	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}

	@Column(name = "PREV_MONTH_DATE1")
	public Date getPreviousMonthDate1() {
		return previousMonthDate1;
	}

	public void setPreviousMonthDate1(Date previousMonthDate1) {
		this.previousMonthDate1 = previousMonthDate1;
	}

	@Column(name = "PREV_MONTH_DATE2")
	public Date getPreviousMonthDate2() {
		return previousMonthDate2;
	}

	public void setPreviousMonthDate2(Date previousMonthDate2) {
		this.previousMonthDate2 = previousMonthDate2;
	}

	@Column(name = "PREV_MONTH_DATE3")
	public Date getPreviousMonthDate3() {
		return previousMonthDate3;
	}

	public void setPreviousMonthDate3(Date previousMonthDate3) {
		this.previousMonthDate3 = previousMonthDate3;
	}

	@Column(name = "PREV_MONTH_VALUE1")
	public String getPreviousMonthValue1() {
		return previousMonthValue1;
	}

	public void setPreviousMonthValue1(String previousMonthValue1) {
		this.previousMonthValue1 = previousMonthValue1;
	}

	@Column(name = "PREV_MONTH_VALUE2")
	public String getPreviousMonthValue2() {
		return previousMonthValue2;
	}

	public void setPreviousMonthValue2(String previousMonthValue2) {
		this.previousMonthValue2 = previousMonthValue2;
	}

	@Column(name = "PREV_MONTH_VALUE3")
	public String getPreviousMonthValue3() {
		return previousMonthValue3;
	}

	public void setPreviousMonthValue3(String previousMonthValue3) {
		this.previousMonthValue3 = previousMonthValue3;
	}
	
	@Column(name="PREV_MONTH_WEEK_DATE1")
	public Date getPreviousMonthWeekDate1() {
		return previousMonthWeekDate1;
	}

	public void setPreviousMonthWeekDate1(Date previousMonthWeekDate1) {
		this.previousMonthWeekDate1 = previousMonthWeekDate1;
	}
	@Column(name="PREV_MONTH_WEEK_DATE2")
	public Date getPreviousMonthWeekDate2() {
		return previousMonthWeekDate2;
	}

	public void setPreviousMonthWeekDate2(Date previousMonthWeekDate2) {
		this.previousMonthWeekDate2 = previousMonthWeekDate2;
	}
	@Column(name="PREV_MONTH_WEEK_DATE3")
	public Date getPreviousMonthWeekDate3() {
		return previousMonthWeekDate3;
	}

	public void setPreviousMonthWeekDate3(Date previousMonthWeekDate3) {
		this.previousMonthWeekDate3 = previousMonthWeekDate3;
	}
	@Column(name="PREV_MONTH_WEEK_VALUE1")
	public String getPreviousMonthWeekValue1() {
		return previousMonthWeekValue1;
	}

	public void setPreviousMonthWeekValue1(String previousMonthWeekValue1) {
		this.previousMonthWeekValue1 = previousMonthWeekValue1;
	}
	@Column(name="PREV_MONTH_WEEK_VALUE2")
	public String getPreviousMonthWeekValue2() {
		return previousMonthWeekValue2;
	}

	public void setPreviousMonthWeekValue2(String previousMonthWeekValue2) {
		this.previousMonthWeekValue2 = previousMonthWeekValue2;
	}
	@Column(name="PREV_MONTH_WEEK_VALUE3")
	public String getPreviousMonthWeekValue3() {
		return previousMonthWeekValue3;
	}

	public void setPreviousMonthWeekValue3(String previousMonthWeekValue3) {
		this.previousMonthWeekValue3 = previousMonthWeekValue3;
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
/*	
	@Column(name = "PREV_MONTH_DATE4")
	public Date getPreviousMonthDate4() {
		return previousMonthDate4;
	}

	public void setPreviousMonthDate4(Date previousMonthDate4) {
		this.previousMonthDate4 = previousMonthDate4;
	}

	@Column(name = "PREV_MONTH_DATE5")
	public Date getPreviousMonthDate5() {
		return previousMonthDate5;
	}

	public void setPreviousMonthDate5(Date previousMonthDate5) {
		this.previousMonthDate5 = previousMonthDate5;
	}

	@Column(name = "PREV_MONTH_DATE6")
	public Date getPreviousMonthDate6() {
		return previousMonthDate6;
	}

	public void setPreviousMonthDate6(Date previousMonthDate6) {
		this.previousMonthDate6 = previousMonthDate6;
	}

	@Column(name = "PREV_MONTH_DATE7")
	public Date getPreviousMonthDate7() {
		return previousMonthDate7;
	}

	public void setPreviousMonthDate7(Date previousMonthDate7) {
		this.previousMonthDate7 = previousMonthDate7;
	}

	@Column(name = "PREV_MONTH_DATE8")
	public Date getPreviousMonthDate8() {
		return previousMonthDate8;
	}

	public void setPreviousMonthDate8(Date previousMonthDate8) {
		this.previousMonthDate8 = previousMonthDate8;
	}

	@Column(name = "PREV_MONTH_DATE9")
	public Date getPreviousMonthDate9() {
		return previousMonthDate9;
	}

	public void setPreviousMonthDate9(Date previousMonthDate9) {
		this.previousMonthDate9 = previousMonthDate9;
	}

	@Column(name = "PREV_MONTH_VALUE4")
	public String getPreviousMonthValue4() {
		return previousMonthValue4;
	}

	public void setPreviousMonthValue4(String previousMonthValue4) {
		this.previousMonthValue4 = previousMonthValue4;
	}

	@Column(name = "PREV_MONTH_VALUE5")
	public String getPreviousMonthValue5() {
		return previousMonthValue5;
	}

	public void setPreviousMonthValue5(String previousMonthValue5) {
		this.previousMonthValue5 = previousMonthValue5;
	}

	@Column(name = "PREV_MONTH_VALUE6")
	public String getPreviousMonthValue6() {
		return previousMonthValue6;
	}

	public void setPreviousMonthValue6(String previousMonthValue6) {
		this.previousMonthValue6 = previousMonthValue6;
	}

	@Column(name = "PREV_MONTH_VALUE7")
	public String getPreviousMonthValue7() {
		return previousMonthValue7;
	}

	public void setPreviousMonthValue7(String previousMonthValue7) {
		this.previousMonthValue7 = previousMonthValue7;
	}

	@Column(name = "PREV_MONTH_VALUE8")
	public String getPreviousMonthValue8() {
		return previousMonthValue8;
	}

	public void setPreviousMonthValue8(String previousMonthValue8) {
		this.previousMonthValue8 = previousMonthValue8;
	}

	@Column(name = "PREV_MONTH_VALUE9")
	public String getPreviousMonthValue9() {
		return previousMonthValue9;
	}

	public void setPreviousMonthValue9(String previousMonthValue9) {
		this.previousMonthValue9 = previousMonthValue9;
	}

	@Column(name = "PREV_MONTH_WEEK_DATE4")
	public Date getPreviousMonthWeekDate4() {
		return previousMonthWeekDate4;
	}

	public void setPreviousMonthWeekDate4(Date previousMonthWeekDate4) {
		this.previousMonthWeekDate4 = previousMonthWeekDate4;
	}

	@Column(name = "PREV_MONTH_WEEK_DATE5")
	public Date getPreviousMonthWeekDate5() {
		return previousMonthWeekDate5;
	}

	public void setPreviousMonthWeekDate5(Date previousMonthWeekDate5) {
		this.previousMonthWeekDate5 = previousMonthWeekDate5;
	}

	@Column(name = "PREV_MONTH_WEEK_DATE6")
	public Date getPreviousMonthWeekDate6() {
		return previousMonthWeekDate6;
	}

	public void setPreviousMonthWeekDate6(Date previousMonthWeekDate6) {
		this.previousMonthWeekDate6 = previousMonthWeekDate6;
	}

	@Column(name = "PREV_MONTH_WEEK_DATE7")
	public Date getPreviousMonthWeekDate7() {
		return previousMonthWeekDate7;
	}

	public void setPreviousMonthWeekDate7(Date previousMonthWeekDate7) {
		this.previousMonthWeekDate7 = previousMonthWeekDate7;
	}

	@Column(name = "PREV_MONTH_WEEK_DATE8")
	public Date getPreviousMonthWeekDate8() {
		return previousMonthWeekDate8;
	}

	public void setPreviousMonthWeekDate8(Date previousMonthWeekDate8) {
		this.previousMonthWeekDate8 = previousMonthWeekDate8;
	}

	@Column(name = "PREV_MONTH_WEEK_DATE9")
	public Date getPreviousMonthWeekDate9() {
		return previousMonthWeekDate9;
	}

	public void setPreviousMonthWeekDate9(Date previousMonthWeekDate9) {
		this.previousMonthWeekDate9 = previousMonthWeekDate9;
	}

	@Column(name = "PREV_MONTH_WEEK_VALUE4")
	public String getPreviousMonthWeekValue4() {
		return previousMonthWeekValue4;
	}

	public void setPreviousMonthWeekValue4(String previousMonthWeekValue4) {
		this.previousMonthWeekValue4 = previousMonthWeekValue4;
	}

	@Column(name = "PREV_MONTH_WEEK_VALUE5")
	public String getPreviousMonthWeekValue5() {
		return previousMonthWeekValue5;
	}

	public void setPreviousMonthWeekValue5(String previousMonthWeekValue5) {
		this.previousMonthWeekValue5 = previousMonthWeekValue5;
	}

	@Column(name = "PREV_MONTH_WEEK_VALUE6")
	public String getPreviousMonthWeekValue6() {
		return previousMonthWeekValue6;
	}

	public void setPreviousMonthWeekValue6(String previousMonthWeekValue6) {
		this.previousMonthWeekValue6 = previousMonthWeekValue6;
	}

	@Column(name = "PREV_MONTH_WEEK_VALUE7")
	public String getPreviousMonthWeekValue7() {
		return previousMonthWeekValue7;
	}

	public void setPreviousMonthWeekValue7(String previousMonthWeekValue7) {
		this.previousMonthWeekValue7 = previousMonthWeekValue7;
	}

	@Column(name = "PREV_MONTH_WEEK_VALUE8")
	public String getPreviousMonthWeekValue8() {
		return previousMonthWeekValue8;
	}

	public void setPreviousMonthWeekValue8(String previousMonthWeekValue8) {
		this.previousMonthWeekValue8 = previousMonthWeekValue8;
	}

	@Column(name = "PREV_MONTH_WEEK_VALUE9")
	public String getPreviousMonthWeekValue9() {
		return previousMonthWeekValue9;
	}

	public void setPreviousMonthWeekValue9(String previousMonthWeekValue9) {
		this.previousMonthWeekValue9 = previousMonthWeekValue9;
	}
	
*/

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

	@Column(name="SERVICE_ID")
	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	
}
