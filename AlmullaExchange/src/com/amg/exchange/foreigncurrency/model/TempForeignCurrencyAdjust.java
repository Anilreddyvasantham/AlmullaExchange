package com.amg.exchange.foreigncurrency.model;

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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name = "EX_TEMP_CURRENCY_ADJUST")
public class TempForeignCurrencyAdjust implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigDecimal foreignCurrencyAdjustId;
	private CountryMaster fsCountryMaster;
	private Customer fsCustomer;
	private BigDecimal documentCode;
	private TempCollection tempCollection;
	private BigDecimal documentLineNumber;
	private Date documentDate;
	private Date accountmmyyyy;
	private CountryBranch countryBranch;
	private CurrencyMaster fsCurrencyMaster;
	private BigDecimal adjustmentAmount;
	private BigDecimal notesQuantity;
	private CurrencyWiseDenomination fsDenominationId; 
	private BigDecimal exchangeRate;
	private BigDecimal denaminationAmount;
	private String progNumber;
	private String documentStatus;
	private String transactionType;
	private Date createdDate;
	private String createdBy;
	private String westernUnionMicNo;
	private BigDecimal documentFinanceYear;
	
	

	public TempForeignCurrencyAdjust() {
	}

	@Id
	@GeneratedValue(generator = "ex_foreign_currency_adjust_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_foreign_currency_adjust_seq", sequenceName = "EX_TEMP_CURRENCY_ADJUST_SEQ", allocationSize = 1)
	@Column(name = "CURRENCY_ADJUST_SEQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getForeignCurrencyAdjustId() {
		return foreignCurrencyAdjustId;
	}

	public void setForeignCurrencyAdjustId(BigDecimal foreignCurrencyAdjustId) {
		this.foreignCurrencyAdjustId = foreignCurrencyAdjustId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}

	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getFsCurrencyMaster() {
		return fsCurrencyMaster;
	}

	public void setFsCurrencyMaster(CurrencyMaster fsCurrencyMaster) {
		this.fsCurrencyMaster = fsCurrencyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DENOMINATION_ID")
	public CurrencyWiseDenomination getFsDenominationId() {
		return fsDenominationId;
	}

	public void setFsDenominationId(CurrencyWiseDenomination fsDenominationId) {
		this.fsDenominationId = fsDenominationId;
	}

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_LINE_NO")
	public BigDecimal getDocumentLineNumber() {
		return documentLineNumber;
	}

	public void setDocumentLineNumber(BigDecimal documentLineNumber) {
		this.documentLineNumber = documentLineNumber;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "ACCOUNT_MMYYYY")
	public Date getAccountmmyyyy() {
		return accountmmyyyy;
	}

	public void setAccountmmyyyy(Date accountmmyyyy) {
		this.accountmmyyyy = accountmmyyyy;
	}

	@Column(name = "ADJUSTMENT_AMOUNT")
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	@Column(name = "NOTES_QTY")
	public BigDecimal getNotesQuantity() {
		return notesQuantity;
	}

	public void setNotesQuantity(BigDecimal notesQuantity) {
		this.notesQuantity = notesQuantity;
	}

	@Column(name = "EXCHANGE_RATE")
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "DENOMINATION_AMOUNT")
	public BigDecimal getDenaminationAmount() {
		return denaminationAmount;
	}

	public void setDenaminationAmount(BigDecimal denaminationAmount) {
		this.denaminationAmount = denaminationAmount;
	}

	@Column(name = "PROGNO")
	public String getProgNumber() {
		return progNumber;
	}

	public void setProgNumber(String progNumber) {
		this.progNumber = progNumber;
	}

	@Column(name = "DOCUMENT_STATUS")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	@Column(name = "TRANSACTION_TYPE")
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_SEQ_ID")
	public TempCollection getTempCollection() {
		return tempCollection;
	}

	public void setTempCollection(TempCollection tempCollection) {
		this.tempCollection = tempCollection;
	}

	@Column(name = "WESTERN_UNION_MTCNO")
	public String getWesternUnionMicNo() {
		return westernUnionMicNo;
	}

	public void setWesternUnionMicNo(String westernUnionMicNo) {
		this.westernUnionMicNo = westernUnionMicNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getCountryBranch() {
		return countryBranch;
	}

	public void setCountryBranch(CountryBranch countryBranch) {
		this.countryBranch = countryBranch;
	}

	@Column(name="DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
}