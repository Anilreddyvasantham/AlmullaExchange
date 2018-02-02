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
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.Customer;



/*******************************************************************************************************************

File		: TreasuryDealDetail.java

Project	: AlmullaExchange

Package	: com.amg.exchange.treasury.model

Created	:	
				Date	: 17-Nov-2014 
				By		: Ram Mohan Reddy.P
				Revision:

Last Change:
				Date	: 25-Nov-2014 
				By		: Nazish Hashmi
				Revision:

Description:
 * @param <TreasuryStandardInstruction>
 ********************************************************************************************************************/
@Entity
@Table(name = "EX_TREASURY_DEAL_DETAIL")
public class TreasuryDealDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal treasuryDealDetailId;
	private CountryMaster treasuryDealCountryMaster;
	private CompanyMaster treasuryDealCompanyMaster;
	private BigDecimal treasuryDealUserFinanceYear;
	private Document treasuryDealDocument;
	private BankMaster treasuryDealBankMaster;
	private CurrencyMaster treasuryDealDetailCurrencyMaster;
	private BankAccountDetails treasuryDealDetailBankAccountDetails;
	private TreasuryDealHeader treasuryDealHeader;
	private CustomerSpecialDealRequest customerSpecialDealRequest ;
	private BigDecimal avgRate ;
	//private TreasuryStandardInstruction treasuryStandardInstruction;
	private AccountBalance accountBalance;
	private BigDecimal standardInstruction;

	private String lineType;
	private BigDecimal lineNumber;
	private Date valueDate;
	private BigDecimal exchange;
	private String multiplicationDivision;
	private BigDecimal customerReference;
	private BigDecimal specialRequestCompanyId;
	private BigDecimal specialRequestDocumentCode;
	private BigDecimal  specialRequestFinanceYear;
	private BigDecimal specialRequestDocNumber;
	private BigDecimal fcAmount;
	private BigDecimal localExchangeRate;
	private BigDecimal localAmount;
	private BigDecimal documentNumber;
	private String isActive;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String faAccountNo;
	private String subLedgerInd;
	private String openItemRef;
	private String openItemId;
	private BigDecimal saleAmount;
	

	public TreasuryDealDetail()
	{

	}
	
	public TreasuryDealDetail(BigDecimal treasuryDealDetailId)
	{
		this.treasuryDealDetailId=treasuryDealDetailId;
	}
	
	
	public TreasuryDealDetail(BigDecimal treasuryDealDetailId,
			CountryMaster treasuryDealCountryMaster,
			CompanyMaster treasuryDealCompanyMaster,
			BigDecimal treasuryDealUserFinanceYear,
			Document treasuryDealDocument, BankMaster treasuryDealBankMaster,
			CurrencyMaster treasuryDealDetailCurrencyMaster,
			BankAccountDetails treasuryDealDetailBankAccountDetails,
			TreasuryDealHeader treasuryDealHeader,
			CustomerSpecialDealRequest customerSpecialDealRequest,
			BigDecimal avgRate, AccountBalance accountBalance,
			BigDecimal standardInstruction, String lineType,
			BigDecimal lineNumber, Date valueDate, BigDecimal exchange,
			String multiplicationDivision, BigDecimal customerReference,
			BigDecimal specialRequestCompanyId,
			BigDecimal specialRequestDocumentCode,
			BigDecimal specialRequestFinanceYear,
			BigDecimal specialRequestDocNumber, BigDecimal fcAmount,
			BigDecimal localExchangeRate, BigDecimal localAmount,
			BigDecimal documentNumber, String isActive, Date createdDate,
			String createdBy, Date modifiedDate, String modifiedBy,
			String faAccountNo, String subLedgerInd, String openItemRef,
			String openItemId) {
		super();
		this.treasuryDealDetailId = treasuryDealDetailId;
		this.treasuryDealCountryMaster = treasuryDealCountryMaster;
		this.treasuryDealCompanyMaster = treasuryDealCompanyMaster;
		this.treasuryDealUserFinanceYear = treasuryDealUserFinanceYear;
		this.treasuryDealDocument = treasuryDealDocument;
		this.treasuryDealBankMaster = treasuryDealBankMaster;
		this.treasuryDealDetailCurrencyMaster = treasuryDealDetailCurrencyMaster;
		this.treasuryDealDetailBankAccountDetails = treasuryDealDetailBankAccountDetails;
		this.treasuryDealHeader = treasuryDealHeader;
		this.customerSpecialDealRequest = customerSpecialDealRequest;
		this.avgRate = avgRate;
		this.accountBalance = accountBalance;
		this.standardInstruction = standardInstruction;
		this.lineType = lineType;
		this.lineNumber = lineNumber;
		this.valueDate = valueDate;
		this.exchange = exchange;
		this.multiplicationDivision = multiplicationDivision;
		this.customerReference = customerReference;
		this.specialRequestCompanyId = specialRequestCompanyId;
		this.specialRequestDocumentCode = specialRequestDocumentCode;
		this.specialRequestFinanceYear = specialRequestFinanceYear;
		this.specialRequestDocNumber = specialRequestDocNumber;
		this.fcAmount = fcAmount;
		this.localExchangeRate = localExchangeRate;
		this.localAmount = localAmount;
		this.documentNumber = documentNumber;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.faAccountNo = faAccountNo;
		this.subLedgerInd = subLedgerInd;
		this.openItemRef = openItemRef;
		this.openItemId = openItemId;
	}
	@Id
	@GeneratedValue(generator="ex_treasury_deal_detail_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_treasury_deal_detail_seq",sequenceName="EX_TREASURY_DEAL_DETAIL_SEQ",allocationSize=1)
	@Column(name = "TREASURY_DEAL_DETAIL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getTreasuryDealDetailId() {
		return treasuryDealDetailId;
	}
	public void setTreasuryDealDetailId(BigDecimal treasuryDealDetailId) {
		this.treasuryDealDetailId = treasuryDealDetailId;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getTreasuryDealCountryMaster() {
		return treasuryDealCountryMaster;
	}
	public void setTreasuryDealCountryMaster(CountryMaster treasuryDealCountryMaster) {
		this.treasuryDealCountryMaster = treasuryDealCountryMaster;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getTreasuryDealCompanyMaster() {
		return treasuryDealCompanyMaster;
	}
	public void setTreasuryDealCompanyMaster(CompanyMaster treasuryDealCompanyMaster) {
		this.treasuryDealCompanyMaster = treasuryDealCompanyMaster;
	}
	
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getTreasuryDealUserFinanceYear() {
		return treasuryDealUserFinanceYear;
	}
	public void setTreasuryDealUserFinanceYear(
			BigDecimal treasuryDealUserFinanceYear) {
		this.treasuryDealUserFinanceYear = treasuryDealUserFinanceYear;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getTreasuryDealDocument() {
		return treasuryDealDocument;
	}
	public void setTreasuryDealDocument(Document treasuryDealDocument) {
		this.treasuryDealDocument = treasuryDealDocument;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getTreasuryDealBankMaster() {
		return treasuryDealBankMaster;
	}
	public void setTreasuryDealBankMaster(BankMaster treasuryDealBankMaster) {
		this.treasuryDealBankMaster = treasuryDealBankMaster;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getTreasuryDealDetailCurrencyMaster() {
		return treasuryDealDetailCurrencyMaster;
	}
	public void setTreasuryDealDetailCurrencyMaster(
			CurrencyMaster treasuryDealDetailCurrencyMaster) {
		this.treasuryDealDetailCurrencyMaster = treasuryDealDetailCurrencyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ACCT_DET_ID")
	public BankAccountDetails getTreasuryDealDetailBankAccountDetails() {
		return treasuryDealDetailBankAccountDetails;
	}
	public void setTreasuryDealDetailBankAccountDetails(
			BankAccountDetails treasuryDealDetailBankAccountDetails) {
		this.treasuryDealDetailBankAccountDetails = treasuryDealDetailBankAccountDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TREASURY_DEAL_HEADER_ID", nullable = false)
	public TreasuryDealHeader getTreasuryDealHeader() {
		return treasuryDealHeader;
	}
	public void setTreasuryDealHeader(TreasuryDealHeader treasuryDealHeader) {
		this.treasuryDealHeader = treasuryDealHeader;
	}

	@Column(name = "LINE_TYPE")
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	@Column(name = "LINE_NUMBER")
	public BigDecimal getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(BigDecimal lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Column(name = "VALUE_DATE")
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	@Column(name = "EXCHANGE_RATE")
	public BigDecimal getExchange() {
		return exchange;
	}
	public void setExchange(BigDecimal exchange) {
		this.exchange = exchange;
	}

	@Column(name = "MULTIPLICATION_DIVISION")
	public String getMultiplicationDivision() {
		return multiplicationDivision;
	}
	public void setMultiplicationDivision(String multiplicationDivision) {
		this.multiplicationDivision = multiplicationDivision;
	}

	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	@Column(name = "SPECIAL_REQUEST_COMPANY_ID")
	public BigDecimal getSpecialRequestCompanyId() {
		return specialRequestCompanyId;
	}
	
	public void setSpecialRequestCompanyId(BigDecimal specialRequestCompanyId) {
		this.specialRequestCompanyId = specialRequestCompanyId;
	}
	
	@Column(name = "SPECIAL_REQUEST_DOCUMENT_CODE")
	public BigDecimal getSpecialRequestDocumentCode() {
		return specialRequestDocumentCode;
	}
	public void setSpecialRequestDocumentCode(BigDecimal specialRequestDocumentCode) {
		this.specialRequestDocumentCode = specialRequestDocumentCode;
	}
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIAL_REQUEST_FINANCE_YEAR")
	public UserFinancialYear getSpecialRequestFinanceYear() {
		return specialRequestFinanceYear;
	}
	public void setSpecialRequestFinanceYear(
			UserFinancialYear specialRequestFinanceYear) {
		this.specialRequestFinanceYear = specialRequestFinanceYear;
	}*/
	
	@Column(name="SPECIAL_REQUEST_FINANCE_YEAR")
	public BigDecimal getSpecialRequestFinanceYear() {
		return specialRequestFinanceYear;
	}
	public void setSpecialRequestFinanceYear(
			BigDecimal specialRequestFinanceYear) {
		this.specialRequestFinanceYear = specialRequestFinanceYear;
	}
	
	
	@Column(name = "SPECIAL_REQUEST_DOC_NUMBER")
	public BigDecimal getSpecialRequestDocNumber() {
		return specialRequestDocNumber;
	}
	public void setSpecialRequestDocNumber(BigDecimal specialRequestDocNumber) {
		this.specialRequestDocNumber = specialRequestDocNumber;
	}

	@Column(name = "FC_AMOUNT")
	public BigDecimal getFcAmount() {
		return fcAmount;
	}
	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	@Column(name = "LOCAL_EXCHANGE_RATE")
	public BigDecimal getLocalExchangeRate() {
		return localExchangeRate;
	}
	public void setLocalExchangeRate(BigDecimal localExchangeRate) {
		this.localExchangeRate = localExchangeRate;
	}

	@Column(name = "LOCAL_AMOUNT")
	public BigDecimal getLocalAmount() {
		return localAmount;
	}
	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_SPECIAL_DEAL_REQ_ID")
	public CustomerSpecialDealRequest getCustomerSpecialDealRequest() {
		return customerSpecialDealRequest;
	}
	public void setCustomerSpecialDealRequest(
			CustomerSpecialDealRequest customerSpecialDealRequest) {
		this.customerSpecialDealRequest = customerSpecialDealRequest;
	}
	@Column(name = "AVG_RATE")
	public BigDecimal getAvgRate() {
		return avgRate;
	}
	public void setAvgRate(BigDecimal avgRate) {
		this.avgRate = avgRate;
	}
	@Column(name = "DOCUMENT_NUMBER")
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	/*Removed TREASURY_STANDARD_INSTRUCT_ID from Table
	 * 30-Dec-2014
	 * By Ram Mohan
	 */
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TREASURY_STANDARD_INSTRUCT_ID")
	public TreasuryStandardInstruction getTreasuryStandardInstruction() {
		return treasuryStandardInstruction;
	}
	public void setTreasuryStandardInstruction(
			TreasuryStandardInstruction treasuryStandardInstruction) {
		this.treasuryStandardInstruction = treasuryStandardInstruction;
	}*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_BALANCE_ID")
	public AccountBalance getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(AccountBalance accountBalance) {
		this.accountBalance = accountBalance;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Column(name = "STAND_INSRUCT_ID")
	public BigDecimal getStandardInstruction() {
		return standardInstruction;
	}
	public void setStandardInstruction(BigDecimal standardInstruction) {
		this.standardInstruction = standardInstruction;
	}
	
	@Column(name = "FA_ACCOUNT_NUMBER")
	public String getFaAccountNo() {
		return faAccountNo;
	}
	public void setFaAccountNo(String faAccountNo) {
		this.faAccountNo = faAccountNo;
	}
	
	@Column(name = "SUBLEDGER_IND")
	public String getSubLedgerInd() {
		return subLedgerInd;
	}
	public void setSubLedgerInd(String subLedgerInd) {
		this.subLedgerInd = subLedgerInd;
	}
	
	@Column(name = "OPEN_ITEM_REF")
	public String getOpenItemRef() {
		return openItemRef;
	}
	public void setOpenItemRef(String openItemRef) {
		this.openItemRef = openItemRef;
	}
	
	@Column(name = "OPEN_ITEM_ID")
	public String getOpenItemId() {
		return openItemId;
	}
	public void setOpenItemId(String openItemId) {
		this.openItemId = openItemId;
	}

	@Column(name = "SALE_AMOUNT")
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	

	
}
