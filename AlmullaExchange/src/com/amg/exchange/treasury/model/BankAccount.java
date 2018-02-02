package com.amg.exchange.treasury.model;

// default package
// Generated May 23, 2014 5:18:25  Created by Chennai ODC

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
 * BankAccount Created by Chennai ODC
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EX_BANK_ACCOUNT")
public class BankAccount implements java.io.Serializable {

	private BigDecimal bankAccountId;
	//private BankBranch exBankBranch;
	private BankMaster exBankMaster;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster fsCountryMaster;
	private BigDecimal currencyId;
	private String debitAcct;
	private String recordStatus;
	private Date createDate;
	private Date updateDate;
	private String creator;
	private String modifier;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private Set<BankAccountDetails> bankAccountList = new HashSet<BankAccountDetails>(0);
	
	public BankAccount() {
	}

	public BankAccount(BigDecimal bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public BankAccount(BigDecimal bankAccountId,
			BankMaster exBankMaster, CompanyMaster fsCompanyMaster,
			CountryMaster fsCountryMaster, BigDecimal currencyId,
			String debitAcct, String recordStatus, Date createDate,
			Date updateDate, String creator, String modifier) {
		this.bankAccountId = bankAccountId;
		//this.exBankBranch = exBankBranch;
		this.exBankMaster = exBankMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.currencyId = currencyId;
		this.debitAcct = debitAcct;
		this.recordStatus = recordStatus;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.creator = creator;
		this.modifier = modifier;
	}

	@Id
	@GeneratedValue(generator="ex_bank_account_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_account_seq",sequenceName="EX_BANK_ACCOUNT_SEQ",allocationSize=1)
	@Column(name = "BANK_ACCOUNT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBankAccountId() {
		return this.bankAccountId;
	}

	public void setBankAccountId(BigDecimal bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_BRANCH_ID")
	public BankBranch getExBankBranch() {
		return this.exBankBranch;
	}

	public void setExBankBranch(BankBranch exBankBranch) {
		this.exBankBranch = exBankBranch;
	}*/

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
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "CURRENCY_ID", length = 3)
	public BigDecimal getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "DEBIT_ACCT", length = 60)
	public String getDebitAcct() {
		return this.debitAcct;
	}

	public void setDebitAcct(String debitAcct) {
		this.debitAcct = debitAcct;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getRecordStatus() {
		return this.recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	

	@Column(name = "MODIFIED_BY", length = 15)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankAccountId")
	public Set<BankAccountDetails> getBankAccountList() {
		return bankAccountList;
	}

	public void setBankAccountList(Set<BankAccountDetails> bankAccountList) {
		this.bankAccountList = bankAccountList;
	}
	
	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
