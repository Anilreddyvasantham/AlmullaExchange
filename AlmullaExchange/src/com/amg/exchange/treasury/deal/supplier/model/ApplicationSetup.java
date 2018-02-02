package com.amg.exchange.treasury.deal.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/** Author Nagarjuna CREATED DATE: 02/03/2015 **/
@Entity
@Table(name = "EX_APPLICATION_SETUP")
public class ApplicationSetup implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal applicationSetupId;
	private CountryMaster appSetupCountryMaster;
	private String faAccountNumber;

	private CompanyMaster fsCompanyMaster;
	private String emailIndicator;
	private String smsIndicator;
	private String scanIndicator;
	private BigDecimal headOfficeLocationBranchCode;
	private String insuranceIndicator;
	private BigDecimal idExpiryYears;
	private BigDecimal payableGLNumber;
	private String loyalityIndicator;
	private BigDecimal roundFactor;
	private String amlCheck;
	private String orsIndicator;

	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	
	private String emailHost;
	private String emailUserName;
	private String emailPassword;
	private BigDecimal emailPortNo;
	private String emailAliasName;
	private String emailId;
	
	private String amtbcPromotion;
	
	public ApplicationSetup() {

	}

	public ApplicationSetup(BigDecimal applicationSetupId) {
		super();
		this.applicationSetupId = applicationSetupId;
	}

	public ApplicationSetup(BigDecimal applicationSetupId,
			CountryMaster appSetupCountryMaster,
			String faAccountNumber, CompanyMaster fsCompanyMaster,
			String emailIndicator, String smsIndicator, String scanIndicator,
			BigDecimal headOfficeLocationBranchCode, String insuranceIndicator,
			BigDecimal idExpiryYears, BigDecimal payableGLNumber,
			String loyalityIndicator, BigDecimal roundFactor, String amlCheck,
			String orsIndicator, Date createdDate, String createdBy,
			Date modifiedDate, String modifiedBy) {
		super();
		this.applicationSetupId = applicationSetupId;
		this.appSetupCountryMaster = appSetupCountryMaster;
		this.faAccountNumber = faAccountNumber;
		this.fsCompanyMaster = fsCompanyMaster;
		this.emailIndicator = emailIndicator;
		this.smsIndicator = smsIndicator;
		this.scanIndicator = scanIndicator;
		this.headOfficeLocationBranchCode = headOfficeLocationBranchCode;
		this.insuranceIndicator = insuranceIndicator;
		this.idExpiryYears = idExpiryYears;
		this.payableGLNumber = payableGLNumber;
		this.loyalityIndicator = loyalityIndicator;
		this.roundFactor = roundFactor;
		this.amlCheck = amlCheck;
		this.orsIndicator = orsIndicator;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	@Id
	@GeneratedValue(generator = "ex_application_setup_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_application_setup_seq", sequenceName = "EX_APPLICATION_SETUP_SEQ", allocationSize = 1)
	@Column(name = "APPLICATION_SETUP_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getApplicationSetupId() {
		return applicationSetupId;
	}

	public void setApplicationSetupId(BigDecimal applicationSetupId) {
		this.applicationSetupId = applicationSetupId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getAppSetupCountryMaster() {
		return appSetupCountryMaster;
	}

	public void setAppSetupCountryMaster(CountryMaster appSetupCountryMaster) {
		this.appSetupCountryMaster = appSetupCountryMaster;
	}

	@Column(name = "FA_ACCOUNT_NUMBER")
	public String getFaAccountNumber() {
		return faAccountNumber;
	}

	public void setFaAccountNumber(String faAccountNumber) {
		this.faAccountNumber = faAccountNumber;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@Column(name = "EMAIL_INDICATOR")
	public String getEmailIndicator() {
		return emailIndicator;
	}

	public void setEmailIndicator(String emailIndicator) {
		this.emailIndicator = emailIndicator;
	}

	@Column(name = "SMS_INDICATOR")
	public String getSmsIndicator() {
		return smsIndicator;
	}

	public void setSmsIndicator(String smsIndicator) {
		this.smsIndicator = smsIndicator;
	}

	@Column(name = "SCAN_INDICATOR")
	public String getScanIndicator() {
		return scanIndicator;
	}

	public void setScanIndicator(String scanIndicator) {
		this.scanIndicator = scanIndicator;
	}

	@Column(name = "HD_OFFICE_LOCATION_BRANCHCODE")
	public BigDecimal getHeadOfficeLocationBranchCode() {
		return headOfficeLocationBranchCode;
	}

	public void setHeadOfficeLocationBranchCode(
			BigDecimal headOfficeLocationBranchCode) {
		this.headOfficeLocationBranchCode = headOfficeLocationBranchCode;
	}

	@Column(name = "INSURANCE_INDICATOR")
	public String getInsuranceIndicator() {
		return insuranceIndicator;
	}

	public void setInsuranceIndicator(String insuranceIndicator) {
		this.insuranceIndicator = insuranceIndicator;
	}

	@Column(name = "ID_EXPIRY_YEARS")
	public BigDecimal getIdExpiryYears() {
		return idExpiryYears;
	}

	public void setIdExpiryYears(BigDecimal idExpiryYears) {
		this.idExpiryYears = idExpiryYears;
	}

	@Column(name = "PAYABLE_GLNUMBER")
	public BigDecimal getPayableGLNumber() {
		return payableGLNumber;
	}

	public void setPayableGLNumber(BigDecimal payableGLNumber) {
		this.payableGLNumber = payableGLNumber;
	}

	@Column(name = "LOYALITY_INDICATOR")
	public String getLoyalityIndicator() {
		return loyalityIndicator;
	}

	public void setLoyalityIndicator(String loyalityIndicator) {
		this.loyalityIndicator = loyalityIndicator;
	}

	@Column(name = "ROUND_FACTOR")
	public BigDecimal getRoundFactor() {
		return roundFactor;
	}

	public void setRoundFactor(BigDecimal roundFactor) {
		this.roundFactor = roundFactor;
	}

	@Column(name = "AML_CHECK")
	public String getAmlCheck() {
		return amlCheck;
	}

	public void setAmlCheck(String amlCheck) {
		this.amlCheck = amlCheck;
	}

	@Column(name = "ORS_INDICATOR")
	public String getOrsIndicator() {
		return orsIndicator;
	}

	public void setOrsIndicator(String orsIndicator) {
		this.orsIndicator = orsIndicator;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "EMAIL_HOST")
	public String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}
	
	@Column(name = "EMAIL_USER_NAME")
	public String getEmailUserName() {
		return emailUserName;
	}

	public void setEmailUserName(String emailUserName) {
		this.emailUserName = emailUserName;
	}

	@Column(name = "EMAIL_PASSWORD")
	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	@Column(name = "EMAIL_PORT_NO")
	public BigDecimal getEmailPortNo() {
		return emailPortNo;
	}

	public void setEmailPortNo(BigDecimal emailPortNo) {
		this.emailPortNo = emailPortNo;
	}

	@Column(name = "EMAIL_ALIAS_NAME")
	public String getEmailAliasName() {
		return emailAliasName;
	}

	public void setEmailAliasName(String emailAliasName) {
		this.emailAliasName = emailAliasName;
	}

	@Column(name = "EXCHANGE_FROM_EMAIL_ID")
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Column(name="AMTBC_PROMOTION")
	public String getAmtbcPromotion() {
		return amtbcPromotion;
	}

	public void setAmtbcPromotion(String amtbcPromotion) {
		this.amtbcPromotion = amtbcPromotion;
	}
	
}
