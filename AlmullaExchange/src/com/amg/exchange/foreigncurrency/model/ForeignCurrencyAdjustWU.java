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
//@Table(name = "D_EX_CURRENCY_ADJUST")
@Table(name = "EX_TEMP_CURRENCY_ADJUST")
public class ForeignCurrencyAdjustWU implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal foreignCurrencyAdjustId;
	private Customer fsCustomer;
	private CountryMaster fsCountryMaster;
	//private CompanyMaster fsCompanyMaster;
	private CountryBranch countryBranch;// Nedd to check with tables
	private CurrencyMaster fsCurrencyMaster;
	private CurrencyWiseDenomination fsDenominationId; // need to check
	//private String oracleUser;
	private BigDecimal documentCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private BigDecimal documentLineNumber;
	private Date documentDate;
	private Date accountmmyyyy;
	private BigDecimal adjustmentAmount;
	private BigDecimal notesQuantity;
	private BigDecimal exchangeRate;
	private BigDecimal denaminationAmount;
	private String progNumber;
	private String documentStatus;
	//private String stockUpdated;
	private String transactionType;
	//private Date approvalDate;
	//private String approvalBy;
	private String createdBy;
	private Date createdDate;
	//private String modifiedBy;
	//private Date modifiedDate;
	
	//private Collect collect;
	
	private String mtcNo;
	
	
	
	

	/**
	 * @param foreignCurrencyAdjustId
	 * @param fsCustomer
	 * @param fsCountryMaster
	 * @param fsCompanyMaster
	 * @param countryBranch
	 * @param fsCurrencyMaster
	 * @param fsDenominationId
	 * @param oracleUser
	 * @param documentCode
	 * @param documentFinanceYear
	 * @param documentNo
	 * @param documentLineNumber
	 * @param documentDate
	 * @param accountmmyyyy
	 * @param adjustmentAmount
	 * @param notesQuantity
	 * @param exchangeRate
	 * @param denaminationAmount
	 * @param progNumber
	 * @param documentStatus
	 * @param stockUpdated
	 * @param transactionType
	 * @param approvalDate
	 * @param approvalBy
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 * @param collect
	 */
	
	public ForeignCurrencyAdjustWU() {

	}
	
	

	public ForeignCurrencyAdjustWU(BigDecimal foreignCurrencyAdjustId, Customer fsCustomer, CountryMaster fsCountryMaster, CountryBranch countryBranch, CurrencyMaster fsCurrencyMaster, CurrencyWiseDenomination fsDenominationId, BigDecimal documentCode, BigDecimal documentNo,
			BigDecimal documentLineNumber, Date documentDate, Date accountmmyyyy, BigDecimal adjustmentAmount, BigDecimal notesQuantity, BigDecimal exchangeRate, BigDecimal denaminationAmount, String progNumber, String documentStatus, String transactionType, String createdBy, Date createdDate,
			String mtcNo) {
		super();
		this.foreignCurrencyAdjustId = foreignCurrencyAdjustId;
		this.fsCustomer = fsCustomer;
		this.fsCountryMaster = fsCountryMaster;
		this.countryBranch = countryBranch;
		this.fsCurrencyMaster = fsCurrencyMaster;
		this.fsDenominationId = fsDenominationId;
		this.documentCode = documentCode;
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
		this.transactionType = transactionType;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.mtcNo = mtcNo;
	}



	@Id
	@GeneratedValue(generator="ex_temp_currency_adjust_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_temp_currency_adjust_seq",sequenceName="EX_TEMP_CURRENCY_ADJUST_SEQ",allocationSize=1)
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

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}*/

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_BRANCH_ID")
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getFsBankBranchMaster() {
		return countryBranch;
	}

	public void setFsBankBranchMaster(CountryBranch countryBranch) {
		this.countryBranch = countryBranch;
	}*/

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

	/*@Column(name = "ORACLE_USER")
	public String getOracleUser() {
		return oracleUser;
	}

	public void setOracleUser(String oracleUser) {
		this.oracleUser = oracleUser;
	}*/

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	@Column(name = "DOCUMENT_SEQ_ID")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
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

	/*@Column(name = "STOCK_UPDATED")
	public String getStockUpdated() {
		return stockUpdated;
	}

	public void setStockUpdated(String stockUpdated) {
		this.stockUpdated = stockUpdated;
	}*/

	@Column(name = "TRANSACTION_TYPE")
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/*@Column(name = "APPROVAL_DATE")
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}*/

	/*@Column(name = "APPROVAL_BY")
	public String getApprovalBy() {
		return approvalBy;
	}

	public void setApprovalBy(String approvalBy) {
		this.approvalBy = approvalBy;
	}*/

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

	/*@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}*/

	/*@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}*/

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CASH_COLLECTION_ID")
	public Collect getCollect() {
		return collect;
	}

	public void setCollect(Collect collect) {
		this.collect = collect;
	}*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getCountryBranch() {
		return countryBranch;
	}

	public void setCountryBranch(CountryBranch countryBranch) {
		this.countryBranch = countryBranch;
	}
	@Column(name = "WESTERN_UNION_MTCNO")
	public String getMtcNo() {
		return mtcNo;
	}

	public void setMtcNo(String mtcNo) {
		this.mtcNo = mtcNo;
	}
	
	
}