package com.amg.exchange.treasury.deal.supplier.model;

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
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;

	@Entity
	@Table(name = "EX_DAYBOOK_DETAILS")
	public class DayBookDetails implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BigDecimal dayBookDetailsId;
		private DayBookHeader dayBookHeaderId;
		private CountryMaster dayBookCountryMaster;
		private CompanyMaster dayBookCompanyMaster;
		private BigDecimal dayBookFinanceYear;
		private Document dayBookDocumentId;
		private BigDecimal dayBookDocumentNumber;
		private Date dayBookdocumentDate;
		private Date dayBookAccyymm;
		private BigDecimal dayBookLineNumber;
		private String dayBookLineType;
		private CurrencyMaster dayBookCurrencyId;
		private BigDecimal dayBookFcAmount;
		private BigDecimal dayBookExchangeRate;
		private BigDecimal dayBookLocalAmount;
		private String dayBookFaAccountNumber;
		private String dayBookSubLedgerIndicator;
		private String dayBookOpenItemRef;
		private String dayBookOpenItem;
		private String createdBy;
		private Date createdDate;
		private String modifiedBy;
		private Date modifiedDate;
		private String approvedBy;
		private Date approvedDate;
		private Date valueDate;
		private String particulars;
		
		private BankMaster dayBookDetailsBankMaster;
		private BankAccountDetails dayBookDetailsBankAccountDetails;
		
		public DayBookDetails(){}
		public DayBookDetails(BigDecimal dayBookDetailsId) {
			this.dayBookDetailsId = dayBookDetailsId;
		}
		
		public DayBookDetails(BigDecimal dayBookDetailsId,
				DayBookHeader dayBookHeaderId,
				CountryMaster dayBookCountryMaster,
				CompanyMaster dayBookCompanyMaster,
				BigDecimal dayBookFinanceYear, Document dayBookDocumentId,
				BigDecimal dayBookDocumentNumber, Date dayBookdocumentDate,
				Date dayBookAccyymm, BigDecimal dayBookLineNumber,
				String dayBookLineType, CurrencyMaster dayBookCurrencyId,
				BigDecimal dayBookFcAmount, BigDecimal dayBookExchangeRate,
				BigDecimal dayBookLocalAmount, String dayBookFaAccountNumber,
				String dayBookSubLedgerIndicator, String dayBookOpenItemRef,
				String dayBookOpenItem, String createdBy, Date createdDate,
				String modifiedBy, Date modifiedDate, String approvedBy,
				Date approvedDate,Date valueDate,String particulars, BankMaster dayBookDetailsBankMaster,
				BankAccountDetails dayBookDetailsBankAccountDetails) {
			super();
			this.dayBookDetailsId = dayBookDetailsId;
			this.dayBookCountryMaster = dayBookCountryMaster;
			this.dayBookCompanyMaster = dayBookCompanyMaster;
			this.dayBookFinanceYear = dayBookFinanceYear;
			this.dayBookDocumentId = dayBookDocumentId;
			this.dayBookDocumentNumber = dayBookDocumentNumber;
			this.dayBookdocumentDate = dayBookdocumentDate;
			this.dayBookAccyymm = dayBookAccyymm;
			this.dayBookLineNumber = dayBookLineNumber;
			this.dayBookLineType = dayBookLineType;
			this.dayBookCurrencyId = dayBookCurrencyId;
			this.dayBookFcAmount = dayBookFcAmount;
			this.dayBookExchangeRate = dayBookExchangeRate;
			this.dayBookLocalAmount = dayBookLocalAmount;
			this.dayBookFaAccountNumber = dayBookFaAccountNumber;
			this.dayBookSubLedgerIndicator = dayBookSubLedgerIndicator;
			this.dayBookOpenItemRef = dayBookOpenItemRef;
			this.dayBookOpenItem = dayBookOpenItem;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.modifiedBy = modifiedBy;
			this.modifiedDate = modifiedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.dayBookHeaderId = dayBookHeaderId;
			this.valueDate = valueDate;
			this.particulars = particulars;
			this.dayBookDetailsBankMaster=dayBookDetailsBankMaster;
			this.dayBookDetailsBankAccountDetails=dayBookDetailsBankAccountDetails;
		}
		  
		@Id
	     @GeneratedValue(generator="ex_daybook_details_seq",strategy=GenerationType.SEQUENCE)
	     @SequenceGenerator(name="ex_daybook_details_seq", sequenceName="EX_DAYBOOK_DETAILS_SEQ",allocationSize=1)
	     @Column(name="DAYBOOK_DETAILS_ID", unique = true, nullable = false, precision = 22, scale = 0)
		public BigDecimal getDayBookDetailsId() {
			return dayBookDetailsId;
		}

		public void setDayBookDetailsId(BigDecimal dayBookDetailsId) {
			this.dayBookDetailsId = dayBookDetailsId;
		}
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "DAYBOOK_HEADER_ID")
		public DayBookHeader getDayBookHeaderId() {
			return dayBookHeaderId;
		}

		public void setDayBookHeaderId(DayBookHeader dayBookHeaderId) {
			this.dayBookHeaderId = dayBookHeaderId;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "APPLICATION_COUNTRY_ID")
		public CountryMaster getDayBookCountryMaster() {
			return dayBookCountryMaster;
		}

		public void setDayBookCountryMaster(CountryMaster dayBookCountryMaster) {
			this.dayBookCountryMaster = dayBookCountryMaster;
		}
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "COMPANY_ID")
		public CompanyMaster getDayBookCompanyMaster() {
			return dayBookCompanyMaster;
		}

		public void setDayBookCompanyMaster(CompanyMaster dayBookCompanyMaster) {
			this.dayBookCompanyMaster = dayBookCompanyMaster;
		}
		@Column(name = "DOCUMENT_FINANCE_YEAR")
		public BigDecimal getDayBookFinanceYear() {
			return dayBookFinanceYear;
		}

		public void setDayBookFinanceYear(BigDecimal dayBookFinanceYear) {
			this.dayBookFinanceYear = dayBookFinanceYear;
		}
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "DOCUMENT_ID")
		public Document getDayBookDocumentId() {
			return dayBookDocumentId;
		}

		public void setDayBookDocumentId(Document dayBookDocumentId) {
			this.dayBookDocumentId = dayBookDocumentId;
		}
		@Column(name = "DOCUMENT_NUMBER")
		public BigDecimal getDayBookDocumentNumber() {
			return dayBookDocumentNumber;
		}

		public void setDayBookDocumentNumber(BigDecimal dayBookDocumentNumber) {
			this.dayBookDocumentNumber = dayBookDocumentNumber;
		}
		@Column(name = "DOCUMENT_DATE")
		public Date getDayBookdocumentDate() {
			return dayBookdocumentDate;
		}

		public void setDayBookdocumentDate(Date dayBookdocumentDate) {
			this.dayBookdocumentDate = dayBookdocumentDate;
		}
		@Column(name = "ACYYMM")
		public Date getDayBookAccyymm() {
			return dayBookAccyymm;
		}

		public void setDayBookAccyymm(Date dayBookAccyymm) {
			this.dayBookAccyymm = dayBookAccyymm;
		}
		@Column(name = "LINE_NUMBER")
		public BigDecimal getDayBookLineNumber() {
			return dayBookLineNumber;
		}

		public void setDayBookLineNumber(BigDecimal dayBookLineNumber) {
			this.dayBookLineNumber = dayBookLineNumber;
		}
		@Column(name = "LINE_TYPE")
		public String getDayBookLineType() {
			return dayBookLineType;
		}

		public void setDayBookLineType(String dayBookLineType) {
			this.dayBookLineType = dayBookLineType;
		}
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "CURRENCY_ID")
		public CurrencyMaster getDayBookCurrencyId() {
			return dayBookCurrencyId;
		}

		public void setDayBookCurrencyId(CurrencyMaster dayBookCurrencyId) {
			this.dayBookCurrencyId = dayBookCurrencyId;
		}
		@Column(name = "FC_AMOUNT")
		public BigDecimal getDayBookFcAmount() {
			return dayBookFcAmount;
		}

		public void setDayBookFcAmount(BigDecimal dayBookFcAmount) {
			this.dayBookFcAmount = dayBookFcAmount;
		}
		@Column(name = "EXCHANGE_RATE")
		public BigDecimal getDayBookExchangeRate() {
			return dayBookExchangeRate;
		}

		public void setDayBookExchangeRate(BigDecimal dayBookExchangeRate) {
			this.dayBookExchangeRate = dayBookExchangeRate;
		}
		@Column(name = "LOCAL_AMOUNT")
		public BigDecimal getDayBookLocalAmount() {
			return dayBookLocalAmount;
		}

		public void setDayBookLocalAmount(BigDecimal dayBookLocalAmount) {
			this.dayBookLocalAmount = dayBookLocalAmount;
		}
		@Column(name = "FA_ACCOUNT_NUMBER")
		public String getDayBookFaAccountNumber() {
			return dayBookFaAccountNumber;
		}

		public void setDayBookFaAccountNumber(String dayBookFaAccountNumber) {
			this.dayBookFaAccountNumber = dayBookFaAccountNumber;
		}
		@Column(name = "SUBLEDGER_IND")
		public String getDayBookSubLedgerIndicator() {
			return dayBookSubLedgerIndicator;
		}

		public void setDayBookSubLedgerIndicator(String dayBookSubLedgerIndicator) {
			this.dayBookSubLedgerIndicator = dayBookSubLedgerIndicator;
		}
		@Column(name = "OPEN_ITEM_REF")
		public String getDayBookOpenItemRef() {
			return dayBookOpenItemRef;
		}

		public void setDayBookOpenItemRef(String dayBookOpenItemRef) {
			this.dayBookOpenItemRef = dayBookOpenItemRef;
		}
		@Column(name = "OPEN_ITEM_ID")
		public String getDayBookOpenItem() {
			return dayBookOpenItem;
		}

		public void setDayBookOpenItem(String dayBookOpenItem) {
			this.dayBookOpenItem = dayBookOpenItem;
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
		@Column(name = "MODIFIED_BY")
		public String getModifiedBy() {
			return modifiedBy;
		}

		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		@Column(name = "MODIFIED_DATE")
		public Date getModifiedDate() {
			return modifiedDate;
		}

		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
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
		@Column(name = "VALUE_DATE")
		public Date getValueDate() {
			return valueDate;
		}

		public void setValueDate(Date valueDate) {
			this.valueDate = valueDate;
		}
		
		@Column(name = "PARTICULARS")
		public String getParticulars() {
			return particulars;
		}
		public void setParticulars(String particulars) {
			this.particulars = particulars;
		}
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "BANK_ID")
		public BankMaster getDayBookDetailsBankMaster() {
			return dayBookDetailsBankMaster;
		}
		public void setDayBookDetailsBankMaster(BankMaster dayBookDetailsBankMaster) {
			this.dayBookDetailsBankMaster = dayBookDetailsBankMaster;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "BANK_ACCT_DET_ID")
		public BankAccountDetails getDayBookDetailsBankAccountDetails() {
			return dayBookDetailsBankAccountDetails;
		}
		public void setDayBookDetailsBankAccountDetails(
				BankAccountDetails dayBookDetailsBankAccountDetails) {
			this.dayBookDetailsBankAccountDetails = dayBookDetailsBankAccountDetails;
		}

		
	}