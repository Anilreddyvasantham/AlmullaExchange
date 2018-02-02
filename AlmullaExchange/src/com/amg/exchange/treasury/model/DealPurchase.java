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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/**
 * ExDealPurchase Created by Chennai ODC
 */
@Entity
@Table(name = "EX_DEAL_PURCHASE")
public class DealPurchase implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4603226081953770699L;
	private BigDecimal dealPurchaseId;
	private CurrencyMaster exCurrencyMaster;
	private SpecialDeal exSpecialDeal;
	private BankAccount exBankAccount;
	private BankMaster exBankMaster;
	private CompanyMaster fsCompanyMaster;
	private SupplierInstructionMaster exSupplierInstructionMaster;
	private CountryMaster fsCountryMaster;
	private Short documentFinYear;
	private Short documentCode;
	private BigDecimal dealRefNumber;
	private BigDecimal purchaseAmount;
	private BigDecimal exchangeRate;
	private Date valueDate;
	private BigDecimal localAmount;
	private BigDecimal localCurrencyExchRate;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	public DealPurchase() {
	}

	public DealPurchase(BigDecimal dealPurchaseId) {
		this.dealPurchaseId = dealPurchaseId;
	}

	public DealPurchase(BigDecimal dealPurchaseId,
			CurrencyMaster exCurrencyMaster, SpecialDeal exSpecialDeal,
			BankAccount exBankAccount, BankMaster exBankMaster,
			CompanyMaster fsCompanyMaster,
			SupplierInstructionMaster exSupplierInstructionMaster,
			CountryMaster fsCountryMaster, Short documentFinYear,
			Short documentCode, BigDecimal dealRefNumber,
			BigDecimal purchaseAmount, BigDecimal exchangeRate, Date valueDate,
			BigDecimal localAmount, BigDecimal localCurrencyExchRate,
			String isactive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate) {
		this.dealPurchaseId = dealPurchaseId;
		this.exCurrencyMaster = exCurrencyMaster;
		this.exSpecialDeal = exSpecialDeal;
		this.exBankAccount = exBankAccount;
		this.exBankMaster = exBankMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.exSupplierInstructionMaster = exSupplierInstructionMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.documentFinYear = documentFinYear;
		this.documentCode = documentCode;
		this.dealRefNumber = dealRefNumber;
		this.purchaseAmount = purchaseAmount;
		this.exchangeRate = exchangeRate;
		this.valueDate = valueDate;
		this.localAmount = localAmount;
		this.localCurrencyExchRate = localCurrencyExchRate;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}
		
	
	@Id
	@GeneratedValue(generator="ex_deal_purchase_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_deal_purchase_seq",sequenceName="EX_DEAL_PURCHASE_SEQ",allocationSize=1)
	@Column(name = "DEAL_PURCHASE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDealPurchaseId() {
		return this.dealPurchaseId;
	}

	public void setDealPurchaseId(BigDecimal dealPurchaseId) {
		this.dealPurchaseId = dealPurchaseId;
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
	@JoinColumn(name = "SPECIAL_DEAL_ID")
	public SpecialDeal getExSpecialDeal() {
		return this.exSpecialDeal;
	}

	public void setExSpecialDeal(SpecialDeal exSpecialDeal) {
		this.exSpecialDeal = exSpecialDeal;
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
	@JoinColumn(name = "SUPPLIER_INST_ID")
	public SupplierInstructionMaster getExSupplierInstructionMaster() {
		return this.exSupplierInstructionMaster;
	}

	public void setExSupplierInstructionMaster(
			SupplierInstructionMaster exSupplierInstructionMaster) {
		this.exSupplierInstructionMaster = exSupplierInstructionMaster;
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

	@Column(name = "DEAL_REF_NUMBER", precision = 22, scale = 0)
	public BigDecimal getDealRefNumber() {
		return this.dealRefNumber;
	}

	public void setDealRefNumber(BigDecimal dealRefNumber) {
		this.dealRefNumber = dealRefNumber;
	}

	@Column(name = "PURCHASE_AMOUNT", precision = 22, scale = 3)
	public BigDecimal getPurchaseAmount() {
		return this.purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	@Column(name = "EXCHANGE_RATE", precision = 10, scale = 6)
	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "VALUE_DATE")
	public Date getValueDate() {
		return this.valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	@Column(name = "LOCAL_AMOUNT", precision = 22, scale = 3)
	public BigDecimal getLocalAmount() {
		return this.localAmount;
	}

	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}

	@Column(name = "LOCAL_CURRENCY_EXCH_RATE", precision = 10, scale = 6)
	public BigDecimal getLocalCurrencyExchRate() {
		return this.localCurrencyExchRate;
	}

	public void setLocalCurrencyExchRate(BigDecimal localCurrencyExchRate) {
		this.localCurrencyExchRate = localCurrencyExchRate;
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
