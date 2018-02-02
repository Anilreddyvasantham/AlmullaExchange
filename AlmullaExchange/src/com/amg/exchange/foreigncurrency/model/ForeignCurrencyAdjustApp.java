package com.amg.exchange.foreigncurrency.model;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name = "EX_APPL_CURRENCY_ADJUST")
public class ForeignCurrencyAdjustApp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal foreignCurrencyAdjustId;
	private Customer fsCustomer;
	private CountryMaster fsCountryMaster;
	private CompanyMaster fsCompanyMaster;
	private CountryBranch fsCountryBranchMaster;// Need to check with tables
	private CurrencyMaster fsCurrencyMaster;
	private CurrencyWiseDenomination fsDenominationId; // need to check
	private String oracleUser;
	private int documentCode;
	private int documentFinanceYear;
	private int documentNo;
	private int documentLineNumber;
	private Date documentDate;
	private Date accountmmyyyy;
	private int adjustmentAmount;
	private int notesQuantity;
	private BigDecimal exchangeRate;
	private BigDecimal denaminationAmount;
	private String progNumber;
	private String documentStatus;
	private String stockUpdated;
	private String transactionType;
	private Date approvalDate;
	private String approvalBy;
	private String createdBy;
	private Date createdDate;
	private String purposeOfTransaction;
	private String sourceOfIncome;
	private String customerSignature;
	private String remarks;
	private String applicationStatus;
	
	Set<ReceiptPaymentApp> receiptPaymentAppList = new HashSet<ReceiptPaymentApp>();
	
	public ForeignCurrencyAdjustApp(BigDecimal foreignCurrencyAdjustId,
			Customer fsCustomer, CountryMaster fsCountryMaster,
			CompanyMaster fsCompanyMaster, CountryBranch fsCountryBranchMaster,
			CurrencyMaster fsCurrencyMaster,
			CurrencyWiseDenomination fsDenominationId, String oracleUser,
			int documentCode, int documentFinanceYear, int documentNo,
			int documentLineNumber, Date documentDate, Date accountmmyyyy,
			int adjustmentAmount, int notesQuantity, BigDecimal exchangeRate,
			BigDecimal denaminationAmount, String progNumber,
			String documentStatus, String stockUpdated, String transactionType,
			Date approvalDate, String approvalBy, String createdBy,
			Date createdDate, String purposeOfTransaction,
			String sourceOfIncome, String customerSignature, String remarks,
			Set<ReceiptPaymentApp> receiptPaymentAppList,
			String applicationStatus) {
		
		this.foreignCurrencyAdjustId = foreignCurrencyAdjustId;
		this.fsCustomer = fsCustomer;
		this.fsCountryMaster = fsCountryMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryBranchMaster = fsCountryBranchMaster;
		this.fsCurrencyMaster = fsCurrencyMaster;
		this.fsDenominationId = fsDenominationId;
		this.oracleUser = oracleUser;
		this.documentCode = documentCode;
		this.documentFinanceYear = documentFinanceYear;
		this.documentNo = documentNo;
		this.documentLineNumber = documentLineNumber;
		this.documentDate = documentDate;
		this.accountmmyyyy = accountmmyyyy;
		this.adjustmentAmount = adjustmentAmount;
		this.notesQuantity = notesQuantity;
		this.exchangeRate = exchangeRate;
		this.denaminationAmount = denaminationAmount;
		this.progNumber = progNumber;
		this.documentStatus = documentStatus;
		this.stockUpdated = stockUpdated;
		this.transactionType = transactionType;
		this.approvalDate = approvalDate;
		this.approvalBy = approvalBy;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.purposeOfTransaction = purposeOfTransaction;
		this.sourceOfIncome = sourceOfIncome;
		this.customerSignature = customerSignature;
		this.remarks = remarks;
		this.receiptPaymentAppList = receiptPaymentAppList ;
		this.applicationStatus=applicationStatus;
	}

	public ForeignCurrencyAdjustApp() {

	}

	@Id
	@GeneratedValue(generator="ex_fcurrency_adjt_app_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_fcurrency_adjt_app_seq",sequenceName="EX_FCURRENCY_ADJT_APP_SEQ",allocationSize=1)
	@Column(name = "FCURRENCY_ADJUST_APP_ID", unique = true, nullable = false, precision = 22, scale = 0)
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
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}
	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getFsCountryBranchMaster() {
		return fsCountryBranchMaster;
	}
	public void setFsCountryBranchMaster(CountryBranch fsCountryBranchMaster) {
		this.fsCountryBranchMaster = fsCountryBranchMaster;
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

	@Column(name = "ORACLE_USER")
	public String getOracleUser() {
		return oracleUser;
	}
	public void setOracleUser(String oracleUser) {
		this.oracleUser = oracleUser;
	}

	@Column(name = "DOCUMENT_CODE")
	public int getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(int documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public int getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(int documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	@Column(name = "DOCUMENT_NO")
	public int getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(int documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "DOCUMENT_LINE_NO")
	public int getDocumentLineNumber() {
		return documentLineNumber;
	}
	public void setDocumentLineNumber(int documentLineNumber) {
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
	public int getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(int adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	@Column(name = "NOTES_QTY")
	public int getNotesQuantity() {
		return notesQuantity;
	}
	public void setNotesQuantity(int notesQuantity) {
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

	@Column(name = "STOCK_UPDATED")
	public String getStockUpdated() {
		return stockUpdated;
	}
	public void setStockUpdated(String stockUpdated) {
		this.stockUpdated = stockUpdated;
	}

	@Column(name = "TRANSACTION_TYPE")
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name = "APPROVAL_DATE")
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	@Column(name = "APPROVAL_BY")
	public String getApprovalBy() {
		return approvalBy;
	}
	public void setApprovalBy(String approvalBy) {
		this.approvalBy = approvalBy;
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

	@Column(name = "PURPOSE_OF_TRANSACTION")
	public String getPurposeOfTransaction() {
		return purposeOfTransaction;
	}
	public void setPurposeOfTransaction(String purposeOfTransaction) {
		this.purposeOfTransaction = purposeOfTransaction;
	}

	@Column(name = "SOURCE_OF_INCOME")
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	@Column(name = "CUSTOMER_SIGNATURE")
	public String getCustomerSignature() {
		return customerSignature;
	}
	public void setCustomerSignature(String customerSignature) {
		this.customerSignature = customerSignature;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "APPLICATION_STATUS")
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	
	
	
}
