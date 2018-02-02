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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/**
 * ExDailyAccountBalance Created by Chennai ODC
 */
@Entity
@Table(name = "EX_DAILY_ACCOUNT_BALANCE")
public class DailyAccountBalance implements java.io.Serializable {

	private static final long serialVersionUID = 5641764929236381380L;
	private BigDecimal dailyAccountBalanceId;
	private CountryMaster fsCountryMaster;
	private CompanyMaster fsCompanyMaster;
	private String generalLedgerNo;
	private String subLedgerNo;
	private Date documentDate;
	private String glSlNo;
	private CurrencyMaster exCurrencyMaster;
	private BigDecimal foreignCurrencyCloseBalance;
	private BigDecimal localCurrencyCloseBalance;
	private BigDecimal avarageRate;
	private BigDecimal fundForeignCurrencyBalance;
	private BigDecimal fundLocalCurrencyBalance;
	private BigDecimal canForgnCurBal;
	private BigDecimal canLocalCurBal;
	private BigDecimal tranxLocalBalance;
	private BigDecimal tranxForeignBalance;
	private BankMaster bankMaster;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	public DailyAccountBalance() {
	}

	public DailyAccountBalance(BigDecimal dailyAccountBalanceId) {
		this.dailyAccountBalanceId = dailyAccountBalanceId;
	}

	
	public DailyAccountBalance(BigDecimal dailyAccountBalanceId,
			CountryMaster fsCountryMaster, CompanyMaster fsCompanyMaster,
			String generalLedgerNo, String subLedgerNo, Date documentDate,
			String glSlNo, CurrencyMaster exCurrencyMaster,
			BigDecimal foreignCurrencyCloseBalance,
			BigDecimal localCurrencyCloseBalance, BigDecimal avarageRate,
			BigDecimal fundForeignCurrencyBalance, BigDecimal fundLocalCurrencyBalance,
			BigDecimal canForgnCurBal, BigDecimal canLocalCurBal,
			BigDecimal tranxLocalBalance, BigDecimal tranxForeignBalance,
			BankMaster bankMaster, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate) {
		this.dailyAccountBalanceId = dailyAccountBalanceId;
		this.fsCountryMaster = fsCountryMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.generalLedgerNo = generalLedgerNo;
		this.subLedgerNo = subLedgerNo;
		this.documentDate = documentDate;
		this.glSlNo = glSlNo;
		this.exCurrencyMaster = exCurrencyMaster;
		this.foreignCurrencyCloseBalance = foreignCurrencyCloseBalance;
		this.localCurrencyCloseBalance = localCurrencyCloseBalance;
		this.avarageRate = avarageRate;
		this.fundForeignCurrencyBalance = fundForeignCurrencyBalance;
		this.fundLocalCurrencyBalance = fundLocalCurrencyBalance;
		this.canForgnCurBal = canForgnCurBal;
		this.canLocalCurBal = canLocalCurBal;
		this.tranxLocalBalance = tranxLocalBalance;
		this.tranxForeignBalance = tranxForeignBalance;
		this.bankMaster = bankMaster;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	
/*	@Id
	@TableGenerator(name = "subaccountbalanceid", table = "ex_subaccountbalanceidpk", pkColumnName = "subaccountbalanceidkey", pkColumnValue = "subaccountbalanceidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "subaccountbalanceid")
	@Column(name = "DAILY_ACCOUNT_BALANCE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	*/
	
	//Added by kani begin
	
	@Id
	@GeneratedValue(generator="ex_daily_acount_balance_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_daily_acount_balance_seq",sequenceName="EX_DAILY_ACCOUNT_BALANCE_SEQ",allocationSize=1)
	@Column(name = "DAILY_ACCOUNT_BALANCE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	
	
	//Added by kani end 
	
	
	public BigDecimal getDailyAccountBalanceId() {
		return this.dailyAccountBalanceId;
	}
	public void setDailyAccountBalanceId(BigDecimal dailyAccountBalanceId) {
		this.dailyAccountBalanceId = dailyAccountBalanceId;
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
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}
	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}
	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}
	
	@Column(name="GENERAL_LEDGER_NO")
	public String getGeneralLedgerNo() {
		return generalLedgerNo;
	}
	public void setGeneralLedgerNo(String generalLedgerNo) {
		this.generalLedgerNo = generalLedgerNo;
	}

	@Column(name="SUB_LEDGER_NO")
	public String getSubLedgerNo() {
		return subLedgerNo;
	}
	public void setSubLedgerNo(String subLedgerNo) {
		this.subLedgerNo = subLedgerNo;
	}
	
	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return this.documentDate;
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
	public BigDecimal getAvarageRate() {
		return avarageRate;
	}
	public void setAvarageRate(BigDecimal avarageRate) {
		this.avarageRate = avarageRate;
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
	public BigDecimal getCanForgnCurBal() {
		return canForgnCurBal;
	}
	public void setCanForgnCurBal(BigDecimal canForgnCurBal) {
		this.canForgnCurBal = canForgnCurBal;
	}

	@Column(name="CANC_LOCAL_CURRENCY_BALANCE")
	public BigDecimal getCanLocalCurBal() {
		return canLocalCurBal;
	}
	public void setCanLocalCurBal(BigDecimal canLocalCurBal) {
		this.canLocalCurBal = canLocalCurBal;
	}

	@Column(name="TRNX_LOCAL_CURRENCY_BALANCE")
	public BigDecimal getTranxLocalBalance() {
		return tranxLocalBalance;
	}
	public void setTranxLocalBalance(BigDecimal tranxLocalBalance) {
		this.tranxLocalBalance = tranxLocalBalance;
	}

	@Column(name="TRNX_FOREIGN_CURRENCY_BALANCE")
	public BigDecimal getTranxForeignBalance() {
		return tranxForeignBalance;
	}
	public void setTranxForeignBalance(BigDecimal tranxForeignBalance) {
		this.tranxForeignBalance = tranxForeignBalance;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBankMaster() {
		return bankMaster;
	}

	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
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
