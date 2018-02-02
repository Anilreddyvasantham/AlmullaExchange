package com.amg.exchange.treasury.model;

// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

import java.io.Serializable;
import java.math.BigDecimal;

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

import com.amg.exchange.common.model.CountryMaster;

/**
 * ExMonthAccountBalance Created by Chennai ODC
 */
@Entity
@Table(name = "EX_MONTH_ACCOUNT_BALANCE") 
public class MonthAccountBalance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2684405796435275526L;
	private BigDecimal subLedgerBalanceId;
	private CurrencyMaster exCurrencyMaster;
	private CountryMaster fsCountryMaster;
	private String subLedgerNo;
	private Serializable accountingMthYear;
	private BigDecimal localBalance;
	private BigDecimal foreignBalanc;
	private String createdBy;
	private Serializable createdDate;
	private String modifiedBy;
	private Serializable modifiedDate;

	public MonthAccountBalance() {
	}

	public MonthAccountBalance(BigDecimal subLedgerBalanceId) {
		this.subLedgerBalanceId = subLedgerBalanceId;
	}

	public MonthAccountBalance(BigDecimal subLedgerBalanceId,
			CurrencyMaster exCurrencyMaster, CountryMaster fsCountryMaster,
			String subLedgerNo, Serializable accountingMthYear,
			BigDecimal localBalance, BigDecimal foreignBalanc,
			String createdBy, Serializable createdDate, String modifiedBy,
			Serializable modifiedDate) {
		this.subLedgerBalanceId = subLedgerBalanceId;
		this.exCurrencyMaster = exCurrencyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.subLedgerNo = subLedgerNo;
		this.accountingMthYear = accountingMthYear;
		this.localBalance = localBalance;
		this.foreignBalanc = foreignBalanc;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(generator="ex_month_account_balance_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_month_account_balance_seq",sequenceName="EX_MONTH_ACCOUNT_BALANCE_SEQ",allocationSize=1)
	@Column(name = "SUB_LEDGER_BALANCE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getSubLedgerBalanceId() {
		return this.subLedgerBalanceId;
	}

	public void setSubLedgerBalanceId(BigDecimal subLedgerBalanceId) {
		this.subLedgerBalanceId = subLedgerBalanceId;
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
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "SUB_LEDGER_NO", length = 33)
	public String getSubLedgerNo() {
		return this.subLedgerNo;
	}

	public void setSubLedgerNo(String subLedgerNo) {
		this.subLedgerNo = subLedgerNo;
	}

	@Column(name = "ACCOUNTING_MTH_YEAR")
	public Serializable getAccountingMthYear() {
		return this.accountingMthYear;
	}

	public void setAccountingMthYear(Serializable accountingMthYear) {
		this.accountingMthYear = accountingMthYear;
	}

	@Column(name = "LOCAL_BALANCE", precision = 22, scale = 3)
	public BigDecimal getLocalBalance() {
		return this.localBalance;
	}

	public void setLocalBalance(BigDecimal localBalance) {
		this.localBalance = localBalance;
	}

	@Column(name = "FOREIGN_BALANC", precision = 22, scale = 3)
	public BigDecimal getForeignBalanc() {
		return this.foreignBalanc;
	}

	public void setForeignBalanc(BigDecimal foreignBalanc) {
		this.foreignBalanc = foreignBalanc;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Serializable getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Serializable createdDate) {
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
	public Serializable getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Serializable modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
