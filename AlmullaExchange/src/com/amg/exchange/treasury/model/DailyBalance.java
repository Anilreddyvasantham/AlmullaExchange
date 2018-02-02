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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/**
 * ExAccountBalance Created by Chennai ODC
 */
@Entity
@Table(name = "EX_DAILY_ACCOUNT_BALANCE")
public class DailyBalance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -59155724624336535L;
	private BigDecimal dailyAccountBalanceId;
	private CountryMaster applicationCountry;
	private CompanyMaster company;
	private String generalLedgerNo;
	private String subLedgerNumber;
	private Date documentDate;
	private String glSlNo;
	private CurrencyMaster currencyMaster;
	private BigDecimal foreignCurrencyCloseBalance;
	private BigDecimal localCurrencyCloseBalance;
	private BigDecimal avgRate;
	private BigDecimal fundForeignCurrencyBalance;
	private BigDecimal fundLocalCurrencyBalance;
	private BigDecimal cancForeignCurrencyBal; 
	private BigDecimal cancLocalCurrencyBal; 
	private BigDecimal tranxLocalCurrencyBal;
	private BigDecimal tranxForeignCurrencyBal;
	private String cretaedBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BankMaster	bankMaster;

	public DailyBalance() {
	}

	public DailyBalance(BigDecimal dailyAccountBalanceId) {
		this.dailyAccountBalanceId = dailyAccountBalanceId;
	}

	public DailyBalance(BigDecimal dailyAccountBalanceId,
			CountryMaster applicationCountry, CompanyMaster company,
			String generalLedgerNo, String subLedgerNumber, Date documentDate,
			String glSlNo, CurrencyMaster currencyMaster,
			BigDecimal foreignCurrencyCloseBalance, BigDecimal localCurrencyCloseBalance,
			BigDecimal avgRate, BigDecimal fundForeignCurrencyBalance,
			BigDecimal fundLocalCurrencyBalance,
			BigDecimal cancForeignCurrencyBal, BigDecimal cancLocalCurrencyBal,
			BigDecimal tranxLocalCurrencyBal,
			BigDecimal tranxForeignCurrencyBal, BankMaster bankMaster) {
		this.dailyAccountBalanceId = dailyAccountBalanceId;
		this.applicationCountry = applicationCountry;
		this.company = company;
		this.generalLedgerNo = generalLedgerNo;
		this.subLedgerNumber = subLedgerNumber;
		this.documentDate = documentDate;
		this.glSlNo = glSlNo;
		this.currencyMaster = currencyMaster;
		this.foreignCurrencyCloseBalance = foreignCurrencyCloseBalance;
		this.localCurrencyCloseBalance = localCurrencyCloseBalance;
		this.avgRate = avgRate;
		this.fundForeignCurrencyBalance = fundForeignCurrencyBalance;
		this.fundLocalCurrencyBalance = fundLocalCurrencyBalance;
		this.cancForeignCurrencyBal = cancForeignCurrencyBal;
		this.cancLocalCurrencyBal = cancLocalCurrencyBal;
		this.tranxLocalCurrencyBal = tranxLocalCurrencyBal;
		this.tranxForeignCurrencyBal = tranxForeignCurrencyBal;
		this.bankMaster = bankMaster;
	}
	
	
	@Id
	@GeneratedValue(generator="ex_daily_account_balance_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_daily_account_balance_seq",sequenceName="EX_DAILY_ACCOUNT_BALANCE_SEQ",allocationSize=1)
	@Column(name = "DAILY_ACCOUNT_BALANCE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDailyAccountBalanceId() {
		return this.dailyAccountBalanceId;
	}
	public void setDailyAccountBalanceId(BigDecimal dailyAccountBalanceId) {
		this.dailyAccountBalanceId = dailyAccountBalanceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCurrencyMaster() {
		return this.currencyMaster;
	}
	public void setCurrencyMaster(CurrencyMaster currencyMaster) {
		this.currencyMaster = currencyMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountry() {
		return applicationCountry;
	}
	public void setApplicationCountry(CountryMaster applicationCountry) {
		this.applicationCountry = applicationCountry;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getCompany() {
		return company;
	}
	public void setCompany(CompanyMaster company) {
		this.company = company;
	}
	
	@Column(name="GENERAL_LEDGER_NO")
	public String getGeneralLedgerNo() {
		return generalLedgerNo;
	}
	public void setGeneralLedgerNo(String generalLedgerNo) {
		this.generalLedgerNo = generalLedgerNo;
	}

	@Column(name="SUB_LEDGER_NO")
	public String getSubLedgerNumber() {
		return subLedgerNumber;
	}
	public void setSubLedgerNumber(String subLedgerNumber) {
		this.subLedgerNumber = subLedgerNumber;
	}

	@Column(name="DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name="GL_SL_NO")
	public String getGlSlNo() {
		return glSlNo;
	}
	public void setGlSlNo(String glSlNo) {
		this.glSlNo = glSlNo;
	}
	
	@Column(name="FOREIGN_CURRENCY_CLOSE_BALANCE")
	public BigDecimal getForeignCurrencyCloseBalance() {
		return foreignCurrencyCloseBalance;
	}
	public void setForeignCurrencyCloseBalance(BigDecimal foreignCurrencyCloseBalance) {
		this.foreignCurrencyCloseBalance = foreignCurrencyCloseBalance;
	}
	
	@Column(name="LOCAL_CURRENCY_CLOSE_BALANCE")
	public BigDecimal getLocalCurrencyCloseBalance() {
		return localCurrencyCloseBalance;
	}
	public void setLocalCurrencyCloseBalance(BigDecimal localCurrencyCloseBalance) {
		this.localCurrencyCloseBalance = localCurrencyCloseBalance;
	}
	
	@Column(name="AVERAGE_RATE")
	public BigDecimal getAvgRate() {
		return avgRate;
	}
	public void setAvgRate(BigDecimal avgRate) {
		this.avgRate = avgRate;
	}
	
	@Column(name="FUND_FOREIGN_CURRENCY_BALANCE")
	public BigDecimal getFundForeignCurrencyBalance() {
		return fundForeignCurrencyBalance;
	}
	public void setFundForeignCurrencyBalance(BigDecimal fundForeignCurrencyBalance) {
		this.fundForeignCurrencyBalance = fundForeignCurrencyBalance;
	}
	
	@Column(name="FUND_LOCAL_CURRENCY_BALANCE")
	public BigDecimal getFundLocalCurrencyBalance() {
		return fundLocalCurrencyBalance;
	}
	public void setFundLocalCurrencyBalance(BigDecimal fundLocalCurrencyBalance) {
		this.fundLocalCurrencyBalance = fundLocalCurrencyBalance;
	}
	
	@Column(name="CANC_FOREIGN_CURRENCY_BALANCE")
	public BigDecimal getCancForeignCurrencyBal() {
		return cancForeignCurrencyBal;
	}
	public void setCancForeignCurrencyBal(BigDecimal cancForeignCurrencyBal) {
		this.cancForeignCurrencyBal = cancForeignCurrencyBal;
	}
	
	@Column(name="CANC_LOCAL_CURRENCY_BALANCE")
	public BigDecimal getCancLocalCurrencyBal() {
		return cancLocalCurrencyBal;
	}
	public void setCancLocalCurrencyBal(BigDecimal cancLocalCurrencyBal) {
		this.cancLocalCurrencyBal = cancLocalCurrencyBal;
	}
	
	@Column(name="TRNX_LOCAL_CURRENCY_BALANCE")
	public BigDecimal getTranxLocalCurrencyBal() {
		return tranxLocalCurrencyBal;
	}
	public void setTranxLocalCurrencyBal(BigDecimal tranxLocalCurrencyBal) {
		this.tranxLocalCurrencyBal = tranxLocalCurrencyBal;
	}
	
	@Column(name="TRNX_FOREIGN_CURRENCY_BALANCE")
	public BigDecimal getTranxForeignCurrencyBal() {
		return tranxForeignCurrencyBal;
	}
	public void setTranxForeignCurrencyBal(BigDecimal tranxForeignCurrencyBal) {
		this.tranxForeignCurrencyBal = tranxForeignCurrencyBal;
	}

	@Column(name="CREATED_BY")
	public String getCretaedBy() {
		return cretaedBy;
	}
	public void setCretaedBy(String cretaedBy) {
		this.cretaedBy = cretaedBy;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBankMaster() {
		return bankMaster;
	}
	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
	}

}
