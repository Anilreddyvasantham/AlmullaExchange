package com.amg.exchange.treasury.model;

// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

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
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/**
 * ExFundProjection Created by Chennai ODC
 */
@Entity
@Table(name = "EX_FUND_PROJECTION", uniqueConstraints = @UniqueConstraint(columnNames = {
		"COUNTRY_ID", "COMPANY_ID", "DOCUMENT_FIN_YEAR", "DOCUMENT_CODE",
		"DOUMENT_NUMBER" }))
public class FundProjection implements java.io.Serializable {

	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = 1807214386810098260L;
	private BigDecimal fundProjectionId;
	private CurrencyMaster exCurrencyMaster;
	private BankAccount exBankAccount;
	private BankMaster exBankMaster;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster fsCountryMaster;
	private Short documentFinYear;
	private Short documentCode;
	private BigDecimal doumentNumber;
	private Date requestDate;
	private Date validDate;
	private BigDecimal salesProjectionAmount;
	private BigDecimal previousDayBankBalance;
	private BigDecimal fundRequired;
	private BigDecimal iconRate;
	private BigDecimal usdAmount;
	private String transferStatus;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	public FundProjection() {
	}

	public FundProjection(BigDecimal fundProjectionId) {
		this.fundProjectionId = fundProjectionId;
	}

	public FundProjection(BigDecimal fundProjectionId,
			CurrencyMaster exCurrencyMaster, BankAccount exBankAccount,
			BankMaster exBankMaster, CompanyMaster fsCompanyMaster,
			CountryMaster fsCountryMaster, Short documentFinYear,
			Short documentCode, BigDecimal doumentNumber, Date requestDate,
			Date validDate, BigDecimal salesProjectionAmount,
			BigDecimal previousDayBankBalance, BigDecimal fundRequired,
			BigDecimal iconRate, BigDecimal usdAmount, String transferStatus,
			String isactive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate) {
		this.fundProjectionId = fundProjectionId;
		this.exCurrencyMaster = exCurrencyMaster;
		this.exBankAccount = exBankAccount;
		this.exBankMaster = exBankMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.documentFinYear = documentFinYear;
		this.documentCode = documentCode;
		this.doumentNumber = doumentNumber;
		this.requestDate = requestDate;
		this.validDate = validDate;
		this.salesProjectionAmount = salesProjectionAmount;
		this.previousDayBankBalance = previousDayBankBalance;
		this.fundRequired = fundRequired;
		this.iconRate = iconRate;
		this.usdAmount = usdAmount;
		this.transferStatus = transferStatus;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(generator="ex_fund_projection_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_fund_projection_seq",sequenceName="EX_FUND_PROJECTION_SEQ",allocationSize=1)
	@Column(name = "FUND_PROJECTION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getFundProjectionId() {
		return this.fundProjectionId;
	}

	public void setFundProjectionId(BigDecimal fundProjectionId) {
		this.fundProjectionId = fundProjectionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrencyMaster() {
		return this.exCurrencyMaster;
	}

	public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		this.exCurrencyMaster = exCurrencyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ACCOUNT_ID")
	public BankAccount getExBankAccount() {
		return this.exBankAccount;
	}

	public void setExBankAccount(BankAccount exBankAccount) {
		this.exBankAccount = exBankAccount;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getExBankMaster() {
		return this.exBankMaster;
	}

	public void setExBankMaster(BankMaster exBankMaster) {
		this.exBankMaster = exBankMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "DOCUMENT_FIN_YEAR", precision = 4, scale = 0)
	public Short getDocumentFinYear() {
		return this.documentFinYear;
	}

	public void setDocumentFinYear(Short documentFinYear) {
		this.documentFinYear = documentFinYear;
	}

	@Column(name = "DOCUMENT_CODE", precision = 4, scale = 0)
	public Short getDocumentCode() {
		return this.documentCode;
	}

	public void setDocumentCode(Short documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOUMENT_NUMBER", precision = 22, scale = 0)
	public BigDecimal getDoumentNumber() {
		return this.doumentNumber;
	}

	public void setDoumentNumber(BigDecimal doumentNumber) {
		this.doumentNumber = doumentNumber;
	}

	@Column(name = "REQUEST_DATE")
	public Date getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	@Column(name = "VALID_DATE")
	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Column(name = "SALES_PROJECTION_AMOUNT", precision = 22, scale = 3)
	public BigDecimal getSalesProjectionAmount() {
		return this.salesProjectionAmount;
	}

	public void setSalesProjectionAmount(BigDecimal salesProjectionAmount) {
		this.salesProjectionAmount = salesProjectionAmount;
	}

	@Column(name = "PREVIOUS_DAY_BANK_BALANCE", precision = 22, scale = 3)
	public BigDecimal getPreviousDayBankBalance() {
		return this.previousDayBankBalance;
	}

	public void setPreviousDayBankBalance(BigDecimal previousDayBankBalance) {
		this.previousDayBankBalance = previousDayBankBalance;
	}

	@Column(name = "FUND_REQUIRED", precision = 22, scale = 3)
	public BigDecimal getFundRequired() {
		return this.fundRequired;
	}

	public void setFundRequired(BigDecimal fundRequired) {
		this.fundRequired = fundRequired;
	}

	@Column(name = "ICON_RATE", precision = 10, scale = 6)
	public BigDecimal getIconRate() {
		return this.iconRate;
	}

	public void setIconRate(BigDecimal iconRate) {
		this.iconRate = iconRate;
	}

	@Column(name = "USD_AMOUNT", precision = 22, scale = 3)
	public BigDecimal getUsdAmount() {
		return this.usdAmount;
	}

	public void setUsdAmount(BigDecimal usdAmount) {
		this.usdAmount = usdAmount;
	}

	@Column(name = "TRANSFER_STATUS", length = 1)
	public String getTransferStatus() {
		return this.transferStatus;
	}

	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 15)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
