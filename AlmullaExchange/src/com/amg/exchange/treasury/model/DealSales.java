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

/**
 * ExDealSales Created by Chennai ODC
 */
@Entity
@Table(name = "EX_DEAL_SALES")
public class DealSales implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8907417615440207641L;
	private BigDecimal dealSalesId;
	private CurrencyMaster exCurrencyMaster;
	private BankAccount exBankAccount;
	private BankMaster exBankMaster;
	private SupplierInstructionMaster exSupplierInstructionMaster;
	private Deal exDeal;
	private String bankDealReferenceNo;
	private BigDecimal purchaseAmount;
	private Short documentFinYear;
	private Short documentCode;
	private Date dealSaleDate;
	private BigDecimal dealSaleAmount;
	private BigDecimal exchangeRate;
	private BigDecimal localAmount;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	public DealSales() {
	}

	public DealSales(BigDecimal dealSalesId) {
		this.dealSalesId = dealSalesId;
	}
   
	public DealSales(BigDecimal dealSalesId, CurrencyMaster exCurrencyMaster,
			BankAccount exBankAccount, BankMaster exBankMaster,
			SupplierInstructionMaster exSupplierInstructionMaster, Deal exDeal,
			String bankDealReferenceNo, BigDecimal purchaseAmount,
			Short documentFinYear, Short documentCode, Date dealSaleDate,
			BigDecimal dealSaleAmount, BigDecimal exchangeRate,
			BigDecimal localAmount, String isactive, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate) {
		this.dealSalesId = dealSalesId;
		this.exCurrencyMaster = exCurrencyMaster;
		this.exBankAccount = exBankAccount;
		this.exBankMaster = exBankMaster;
		this.exSupplierInstructionMaster = exSupplierInstructionMaster;
		this.exDeal = exDeal;
		this.bankDealReferenceNo = bankDealReferenceNo;
		this.purchaseAmount = purchaseAmount;
		this.documentFinYear = documentFinYear;
		this.documentCode = documentCode;
		this.dealSaleDate = dealSaleDate;
		this.dealSaleAmount = dealSaleAmount;
		this.exchangeRate = exchangeRate;
		this.localAmount = localAmount;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	
	@Id
	@GeneratedValue(generator="ex_deal_sales_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_deal_sales_seq",sequenceName="EX_DEAL_SALES_SEQ",allocationSize=1)
	@Column(name = "DEAL_SALES_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDealSalesId() {
		return this.dealSalesId;
	}

	public void setDealSalesId(BigDecimal dealSalesId) {
		this.dealSalesId = dealSalesId;
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
	@JoinColumn(name = "SUPPLIER_INST_ID")
	public SupplierInstructionMaster getExSupplierInstructionMaster() {
		return this.exSupplierInstructionMaster;
	}

	public void setExSupplierInstructionMaster(
			SupplierInstructionMaster exSupplierInstructionMaster) {
		this.exSupplierInstructionMaster = exSupplierInstructionMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEAL_ID")
	public Deal getExDeal() {
		return this.exDeal;
	}

	public void setExDeal(Deal exDeal) {
		this.exDeal = exDeal;
	}

	@Column(name = "BANK_DEAL_REFERENCE_NO", length = 20)
	public String getBankDealReferenceNo() {
		return this.bankDealReferenceNo;
	}

	public void setBankDealReferenceNo(String bankDealReferenceNo) {
		this.bankDealReferenceNo = bankDealReferenceNo;
	}

	@Column(name = "PURCHASE_AMOUNT", precision = 22, scale = 3)
	public BigDecimal getPurchaseAmount() {
		return this.purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
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

	@Column(name = "DEAL_SALE_DATE")
	public Date getDealSaleDate() {
		return this.dealSaleDate;
	}

	public void setDealSaleDate(Date dealSaleDate) {
		this.dealSaleDate = dealSaleDate;
	}

	@Column(name = "DEAL_SALE_AMOUNT", precision = 22, scale = 3)
	public BigDecimal getDealSaleAmount() {
		return this.dealSaleAmount;
	}

	public void setDealSaleAmount(BigDecimal dealSaleAmount) {
		this.dealSaleAmount = dealSaleAmount;
	}

	@Column(name = "EXCHANGE_RATE", precision = 10, scale = 6)
	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "LOCAL_AMOUNT", precision = 22, scale = 3)
	public BigDecimal getLocalAmount() {
		return this.localAmount;
	}

	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
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
