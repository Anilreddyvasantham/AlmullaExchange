package com.amg.exchange.treasury.model;
// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

import java.io.Serializable;
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

/*******************************************************************************************************************

File		: AccountBalance.java

Project	: AlmullaExchange

Package	: com.amg.exchange.registration.model

Last Change:
				Date	: 19-Nov-2014 
				By		: Nazish Ehsan Hashmi
				Revision:

Description:
********************************************************************************************************************/

@Entity
@Table(name = "EX_ACCOUNT_BALANCE")
public class AccountBalance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -59155724624336535L;
	private BigDecimal accountId;
	private CurrencyMaster exCurrencyMaster;
	private CountryMaster fsCountryMaster;
	private CompanyMaster companyMaster;
	private BankMaster bankMaster;
	private BankAccountDetails exBankAccountDetails;
	private String accountNo;
	private BigDecimal localBalance;
	private BigDecimal foreignBalance;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String generalLeaderNo;
	private BigDecimal subCode;
	private String glSlNumber;
	private BigDecimal averageRate;
	private BankApplicability bankApplicability;
	private BigDecimal eftCurrentBalance;
	private BigDecimal ttCurrentBalance;
	private BigDecimal cashCurrencyBalance;
	
	


	
	
	private Set<Deal> exDeals = new HashSet<Deal>(0);
	private Set<TreasuryDealDetail> exTreasuryDealDetails = new HashSet<TreasuryDealDetail>(0);

	public AccountBalance() {
	}

	public AccountBalance(BigDecimal accountId) {
		this.accountId = accountId;
	}

	

	public AccountBalance(BigDecimal accountId,
			CurrencyMaster exCurrencyMaster, CountryMaster fsCountryMaster,
			CompanyMaster companyMaster, BankMaster bankMaster,
			BankAccountDetails exBankAccountDetails,
			String accountNo, BigDecimal localBalance,
			BigDecimal foreignBalance, String createdBy,
			Date createdDate, String modifiedBy,
			Date modifiedDate, String generalLeaderNo,
			BigDecimal subCode, String glSlNumber, BigDecimal averageRate,
			Set<Deal> exDeals,
			Set<TreasuryDealDetail> exTreasuryDealDetails,BigDecimal eftCurrentBalance,BigDecimal ttCurrentBalance,BigDecimal cashCurrencyBalance) {
		super();
		this.accountId = accountId;
		this.exCurrencyMaster = exCurrencyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.companyMaster = companyMaster;
		this.bankMaster = bankMaster;
		this.exBankAccountDetails = exBankAccountDetails;
		this.accountNo = accountNo;
		this.localBalance = localBalance;
		this.foreignBalance = foreignBalance;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.generalLeaderNo = generalLeaderNo;
		this.subCode = subCode;
		this.glSlNumber = glSlNumber;
		this.averageRate = averageRate;
		this.exDeals = exDeals;
		this.exTreasuryDealDetails = exTreasuryDealDetails;
		this.eftCurrentBalance=eftCurrentBalance;
		this.ttCurrentBalance=ttCurrentBalance;
		this.cashCurrencyBalance=cashCurrencyBalance;
	}

	@Id
	@GeneratedValue(generator="ex_account_balance_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_account_balance_seq",sequenceName="EX_ACCOUNT_BALANCE_SEQ",allocationSize=1)
	@Column(name = "ACCOUNT_BALANCE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getAccountId() {
		return this.accountId;
	}

	public void setAccountId(BigDecimal accountId) {
		this.accountId = accountId;
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


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getCompanyMaster() {
		return companyMaster;
	}

	public void setCompanyMaster(CompanyMaster companyMaster) {
		this.companyMaster = companyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBankMaster() {
		return bankMaster;
	}

	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ACCT_DET_ID")
	public BankAccountDetails getExBankAccountDetails() {
		return exBankAccountDetails;
	}

	public void setExBankAccountDetails(BankAccountDetails exBankAccountDetails) {
		this.exBankAccountDetails = exBankAccountDetails;
	}

	@Column(name = "ACCOUNT_NUMBER", length = 33)
	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Column(name = "LOCAL_CURRENCY_BALANCE", precision = 22, scale = 3)
	public BigDecimal getLocalBalance() {
		return this.localBalance;
	}

	public void setLocalBalance(BigDecimal localBalance) {
		this.localBalance = localBalance;
	}

	@Column(name = "FOREIGN_CURRENCY_BALANCE", precision = 22, scale = 3)
	public BigDecimal getForeignBalance() {
		return this.foreignBalance;
	}

	public void setForeignBalance(BigDecimal foreignBalance) {
		this.foreignBalance = foreignBalance;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exAccountBalance")
	public Set<Deal> getExDeals() {
		return this.exDeals;
	}

	public void setExDeals(Set<Deal> exDeals) {
		this.exDeals = exDeals;
	}
	
	@Column(name = "GENERAL_LEDGER_NO")
	public String getGeneralLeaderNo() {
		return generalLeaderNo;
	}

	public void setGeneralLeaderNo(String generalLeaderNo) {
		this.generalLeaderNo = generalLeaderNo;
	}

	@Column(name = "SUB_CODE")
	public BigDecimal getSubCode() {
		return subCode;
	}

	public void setSubCode(BigDecimal subCode) {
		this.subCode = subCode;
	}

	@Column(name = "GL_SL_NUMBER")
	public String getGlSlNumber() {
		return glSlNumber;
	}

	public void setGlSlNumber(String glSlNumber) {
		this.glSlNumber = glSlNumber;
	}

	@Column(name = "AVERAGE_RATE")
	public BigDecimal getAverageRate() {
		return averageRate;
	}

	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_APPLICABILITY_ID")
	public BankApplicability getBankApplicability() {
		return bankApplicability;
	}

	public void setBankApplicability(BankApplicability bankApplicability) {
		this.bankApplicability = bankApplicability;
	}*/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accountBalance")
	public Set<TreasuryDealDetail> getExTreasuryDealDetails() {
		return exTreasuryDealDetails;
	}

	public void setExTreasuryDealDetails(Set<TreasuryDealDetail> exTreasuryDealDetails) {
		this.exTreasuryDealDetails = exTreasuryDealDetails;
	}
	@Column(name = "EFT_CURRENT_BAL")
	public BigDecimal getEftCurrentBalance() {
		return eftCurrentBalance;
	}

	public void setEftCurrentBalance(BigDecimal eftCurrentBalance) {
		this.eftCurrentBalance = eftCurrentBalance;
	}
	@Column(name = "TT_CURRENT_BAL")
	public BigDecimal getTtCurrentBalance() {
		return ttCurrentBalance;
	}

	public void setTtCurrentBalance(BigDecimal ttCurrentBalance) {
		this.ttCurrentBalance = ttCurrentBalance;
	}
	@Column(name = "CASH_CURRENT_BAL")
	public BigDecimal getCashCurrencyBalance() {
		return cashCurrencyBalance;
	}

	public void setCashCurrencyBalance(BigDecimal cashCurrencyBalance) {
		this.cashCurrencyBalance = cashCurrencyBalance;
	}
	

}
