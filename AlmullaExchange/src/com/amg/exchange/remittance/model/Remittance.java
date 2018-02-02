package com.amg.exchange.remittance.model;

// Generated Jan 7, 2015 12:06:06 PM by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.RemittanceModeMaster;

/*******************************************************************************************************************

File		: Remittance.java

Project	: AlmullaExchange

Package	: com.amg.exchange.remittance.model

Created	:	
				Date	: 7-Jan-2015
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	:  7-Jan-2015
				By		: Nazish Ehsan Hashmi
				Revision:

Description: TODO 

********************************************************************************************************************/
@Entity
@Table(name = "EX_REMITTANCE")
public class Remittance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal remittanceSeqId;
	private DeliveryMode exDeliveryMode;
	private BankBranch exBankBranch;
	private CurrencyMaster exCurrencyMaster;
	private BeneCountryService exBeneCountryService;
	private SourceOfIncome exSourceOfIncome;
	private Document exDocument;
	private CompanyMaster fsCompanyMaster;
	private UserFinancialYear exUserFinancialYear;
	private CountryBranch exCountryBranch;
	private RemittanceModeMaster exRemittanceMode;
	private ServiceApplicabilityRule exServiceApplicabilityRule;
	private PaymentMode exPaymentMode;
	private CountryMaster fsCountryMasterByBankCountryId;
	private CountryMaster fsCountryMasterByApplicationCountryId;
	private AdditionalBankRuleMap exAdditionalBankRule;
	private BankMaster exBankMaster;
	private BankServiceRule exBankServiceRule;
	private RoutingHeader exRoutingHeader;
	private Customer fsCustomer;
	private BeneficaryMaster exBeneficaryMaster;
	private Byte documentCode;
	private Long documentNo;
	private Date documentDate;
	private Short applicationFinanceYr;
	private Long applicationNo;
	private BigDecimal corespondingCountryId;
	private String debitAcct;
	private BigDecimal foreignTrnxAmount;
	private BigDecimal localTranxCurrencyId;
	private BigDecimal localTranxAmount;
	private String quoteName;
	private BigDecimal exchangeRateApplied;
	private String localCommisionCurrencyId;
	private BigDecimal localCommisionAmount;
	private String localChargeCurrencyId;
	private BigDecimal localChargeAmount;
	private BigDecimal localDeliveryCurrencyId;
	private BigDecimal localDeliveryAmount;
	private BigDecimal localNetCurrencyId;
	private BigDecimal localNetTrnxAmount;
	private String transactionStatus;
	private Date transactionPaidDate;
	private String stopPaymentStatus;
	private String cancellationStatus;
	private String generalLedgerErr;
	private String isGeneralLedgerEntry;
	private Date generalLedgerDate;
	private String blackListIndicator;
	private Short batchFinanceYr;
	private Integer batchNumber;
	private Short batchLineNumber;
	private Byte collectionDocumentId;
	private Byte collectionDocumentCode;
	private Short collectionDocumentFinYear;
	private Integer collectionDocumentNo;
	private String fileCreation;
	private String specialIndicator;
	private String receiver;
	private String trnfstsUpdby;
	private Date trnfstsUpddat;
	private Short fileFinanceYr;
	private Integer fileNumber;
	private Date fileCreationDate;
	private String bankFlexField1;
	private String bankFlexField2;
	private String transactionTaggedStatus;
	private Date taggedDate;
	private String taggedBy;
	private String bankReferenceNumber;
	private String deliveryIndicator;
	private String remittanceMode;
	private String deliveryMode;
	private BigDecimal smsSeq;
	private String ecno;
	private String cbkFileCreate;
	private Date accountMmyyyy;
	private String westernUnionMtcno;
	private String printMode;
	private String beneficiaryName;
	private String highValueTrnx;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	public Remittance() {
	}

	public Remittance(BigDecimal remittanceSeqId) {
		this.remittanceSeqId = remittanceSeqId;
	}

	public Remittance(BigDecimal remittanceSeqId,
			DeliveryMode exDeliveryMode, BankBranch exBankBranch,
			CurrencyMaster exCurrencyMaster,
			BeneCountryService exBeneCountryService,
			SourceOfIncome exSourceOfIncome, Document exDocument,
			CompanyMaster fsCompanyMaster,
			UserFinancialYear exUserFinancialYear,
			CountryBranch exCountryBranch, RemittanceModeMaster exRemittanceMode,
			ServiceApplicabilityRule exServiceApplicabilityRule,
			PaymentMode exPaymentMode,
			CountryMaster fsCountryMasterByBankCountryId,
			CountryMaster fsCountryMasterByApplicationCountryId,
			AdditionalBankRuleMap exAdditionalBankRule,
			BankMaster exBankMaster, BankServiceRule exBankServiceRule,
			RoutingHeader exRoutingHeader, Customer fsCustomer,
			BeneficaryMaster exBeneficaryMaster, Byte documentCode,
			Long documentNo, Date documentDate, Short applicationFinanceYr,
			Long applicationNo, BigDecimal corespondingCountryId,
			String debitAcct, BigDecimal foreignTrnxAmount,
			BigDecimal localTranxCurrencyId, BigDecimal localTranxAmount,
			String quoteName, BigDecimal exchangeRateApplied,
			String localCommisionCurrencyId, BigDecimal localCommisionAmount,
			String localChargeCurrencyId, BigDecimal localChargeAmount,
			BigDecimal localDeliveryCurrencyId, BigDecimal localDeliveryAmount,
			BigDecimal localNetCurrencyId, BigDecimal localNetTrnxAmount,
			String transactionStatus, Date transactionPaidDate,
			String stopPaymentStatus, String cancellationStatus,
			String generalLedgerErr, String isGeneralLedgerEntry,
			Date generalLedgerDate, String blackListIndicator,
			Short batchFinanceYr, Integer batchNumber, Short batchLineNumber,
			Byte collectionDocumentId, Byte collectionDocumentCode,
			Short collectionDocumentFinYear, Integer collectionDocumentNo,
			String fileCreation, String specialIndicator, String receiver,
			String trnfstsUpdby, Date trnfstsUpddat, Short fileFinanceYr,
			Integer fileNumber, Date fileCreationDate, String bankFlexField1,
			String bankFlexField2, String transactionTaggedStatus,
			Date taggedDate, String taggedBy, String bankReferenceNumber,
			String deliveryIndicator, String remittanceMode,
			String deliveryMode, BigDecimal smsSeq, String ecno,
			String cbkFileCreate, Date accountMmyyyy, String westernUnionMtcno,
			String printMode, String beneficiaryName, String highValueTrnx,
			String isactive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate) {
		this.remittanceSeqId = remittanceSeqId;
		this.exDeliveryMode = exDeliveryMode;
		this.exBankBranch = exBankBranch;
		this.exCurrencyMaster = exCurrencyMaster;
		this.exBeneCountryService = exBeneCountryService;
		this.exSourceOfIncome = exSourceOfIncome;
		this.exDocument = exDocument;
		this.fsCompanyMaster = fsCompanyMaster;
		this.exUserFinancialYear = exUserFinancialYear;
		this.exCountryBranch = exCountryBranch;
		this.exRemittanceMode = exRemittanceMode;
		this.exServiceApplicabilityRule = exServiceApplicabilityRule;
		this.exPaymentMode = exPaymentMode;
		this.fsCountryMasterByBankCountryId = fsCountryMasterByBankCountryId;
		this.fsCountryMasterByApplicationCountryId = fsCountryMasterByApplicationCountryId;
		this.exAdditionalBankRule = exAdditionalBankRule;
		this.exBankMaster = exBankMaster;
		this.exBankServiceRule = exBankServiceRule;
		this.exRoutingHeader = exRoutingHeader;
		this.fsCustomer = fsCustomer;
		this.exBeneficaryMaster = exBeneficaryMaster;
		this.documentCode = documentCode;
		this.documentNo = documentNo;
		this.documentDate = documentDate;
		this.applicationFinanceYr = applicationFinanceYr;
		this.applicationNo = applicationNo;
		this.corespondingCountryId = corespondingCountryId;
		this.debitAcct = debitAcct;
		this.foreignTrnxAmount = foreignTrnxAmount;
		this.localTranxCurrencyId = localTranxCurrencyId;
		this.localTranxAmount = localTranxAmount;
		this.quoteName = quoteName;
		this.exchangeRateApplied = exchangeRateApplied;
		this.localCommisionCurrencyId = localCommisionCurrencyId;
		this.localCommisionAmount = localCommisionAmount;
		this.localChargeCurrencyId = localChargeCurrencyId;
		this.localChargeAmount = localChargeAmount;
		this.localDeliveryCurrencyId = localDeliveryCurrencyId;
		this.localDeliveryAmount = localDeliveryAmount;
		this.localNetCurrencyId = localNetCurrencyId;
		this.localNetTrnxAmount = localNetTrnxAmount;
		this.transactionStatus = transactionStatus;
		this.transactionPaidDate = transactionPaidDate;
		this.stopPaymentStatus = stopPaymentStatus;
		this.cancellationStatus = cancellationStatus;
		this.generalLedgerErr = generalLedgerErr;
		this.isGeneralLedgerEntry = isGeneralLedgerEntry;
		this.generalLedgerDate = generalLedgerDate;
		this.blackListIndicator = blackListIndicator;
		this.batchFinanceYr = batchFinanceYr;
		this.batchNumber = batchNumber;
		this.batchLineNumber = batchLineNumber;
		this.collectionDocumentId = collectionDocumentId;
		this.collectionDocumentCode = collectionDocumentCode;
		this.collectionDocumentFinYear = collectionDocumentFinYear;
		this.collectionDocumentNo = collectionDocumentNo;
		this.fileCreation = fileCreation;
		this.specialIndicator = specialIndicator;
		this.receiver = receiver;
		this.trnfstsUpdby = trnfstsUpdby;
		this.trnfstsUpddat = trnfstsUpddat;
		this.fileFinanceYr = fileFinanceYr;
		this.fileNumber = fileNumber;
		this.fileCreationDate = fileCreationDate;
		this.bankFlexField1 = bankFlexField1;
		this.bankFlexField2 = bankFlexField2;
		this.transactionTaggedStatus = transactionTaggedStatus;
		this.taggedDate = taggedDate;
		this.taggedBy = taggedBy;
		this.bankReferenceNumber = bankReferenceNumber;
		this.deliveryIndicator = deliveryIndicator;
		this.remittanceMode = remittanceMode;
		this.deliveryMode = deliveryMode;
		this.smsSeq = smsSeq;
		this.ecno = ecno;
		this.cbkFileCreate = cbkFileCreate;
		this.accountMmyyyy = accountMmyyyy;
		this.westernUnionMtcno = westernUnionMtcno;
		this.printMode = printMode;
		this.beneficiaryName = beneficiaryName;
		this.highValueTrnx = highValueTrnx;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}

	@Id
	@GeneratedValue(generator="ex_remittance_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_remittance_seq",sequenceName="EX_REMITTANCE_SEQ",allocationSize=1)
	@Column(name = "REMITTANCE_SEQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getRemittanceSeqId() {
		return this.remittanceSeqId;
	}

	public void setRemittanceSeqId(BigDecimal remittanceSeqId) {
		this.remittanceSeqId = remittanceSeqId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_ID")
	public DeliveryMode getExDeliveryMode() {
		return this.exDeliveryMode;
	}

	public void setExDeliveryMode(DeliveryMode exDeliveryMode) {
		this.exDeliveryMode = exDeliveryMode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_BRANCH_ID")
	public BankBranch getExBankBranch() {
		return this.exBankBranch;
	}

	public void setExBankBranch(BankBranch exBankBranch) {
		this.exBankBranch = exBankBranch;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOREIGN_CURRENCY_ID")
	public CurrencyMaster getExCurrencyMaster() {
		return this.exCurrencyMaster;
	}

	public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		this.exCurrencyMaster = exCurrencyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BENE_COUNTRY_SERVICE_ID")
	public BeneCountryService getExBeneCountryService() {
		return this.exBeneCountryService;
	}

	public void setExBeneCountryService(
			BeneCountryService exBeneCountryService) {
		this.exBeneCountryService = exBeneCountryService;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCE_ID")
	public SourceOfIncome getExSourceOfIncome() {
		return this.exSourceOfIncome;
	}

	public void setExSourceOfIncome(SourceOfIncome exSourceOfIncome) {
		this.exSourceOfIncome = exSourceOfIncome;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getExDocument() {
		return this.exDocument;
	}

	public void setExDocument(Document exDocument) {
		this.exDocument = exDocument;
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
	@JoinColumn(name = "DOCUMENT_FINANCE_YR")
	public UserFinancialYear getExUserFinancialYear() {
		return this.exUserFinancialYear;
	}

	public void setExUserFinancialYear(UserFinancialYear exUserFinancialYear) {
		this.exUserFinancialYear = exUserFinancialYear;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID")
	public CountryBranch getExCountryBranch() {
		return this.exCountryBranch;
	}

	public void setExCountryBranch(CountryBranch exCountryBranch) {
		this.exCountryBranch = exCountryBranch;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REMITTANCE_ID")
	public RemittanceModeMaster getExRemittanceMode() {
		return this.exRemittanceMode;
	}

	public void setExRemittanceMode(RemittanceModeMaster exRemittanceMode) {
		this.exRemittanceMode = exRemittanceMode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_APPLICABILITY_RULE_ID")
	public ServiceApplicabilityRule getExServiceApplicabilityRule() {
		return this.exServiceApplicabilityRule;
	}

	public void setExServiceApplicabilityRule(
			ServiceApplicabilityRule exServiceApplicabilityRule) {
		this.exServiceApplicabilityRule = exServiceApplicabilityRule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENT_MODE_ID")
	public PaymentMode getExPaymentMode() {
		return this.exPaymentMode;
	}

	public void setExPaymentMode(PaymentMode exPaymentMode) {
		this.exPaymentMode = exPaymentMode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_COUNTRY_ID")
	public CountryMaster getFsCountryMasterByBankCountryId() {
		return this.fsCountryMasterByBankCountryId;
	}

	public void setFsCountryMasterByBankCountryId(
			CountryMaster fsCountryMasterByBankCountryId) {
		this.fsCountryMasterByBankCountryId = fsCountryMasterByBankCountryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsCountryMasterByApplicationCountryId() {
		return this.fsCountryMasterByApplicationCountryId;
	}

	public void setFsCountryMasterByApplicationCountryId(
			CountryMaster fsCountryMasterByApplicationCountryId) {
		this.fsCountryMasterByApplicationCountryId = fsCountryMasterByApplicationCountryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDITIONAL_BANK_RULE_ID")
	public AdditionalBankRuleMap getExAdditionalBankRule() {
		return this.exAdditionalBankRule;
	}

	public void setExAdditionalBankRule(
			AdditionalBankRuleMap exAdditionalBankRule) {
		this.exAdditionalBankRule = exAdditionalBankRule;
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
	@JoinColumn(name = "BANK_SERVICE_RULE_ID")
	public BankServiceRule getExBankServiceRule() {
		return this.exBankServiceRule;
	}

	public void setExBankServiceRule(BankServiceRule exBankServiceRule) {
		this.exBankServiceRule = exBankServiceRule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROUTING_HEADER_ID")
	public RoutingHeader getExRoutingHeader() {
		return this.exRoutingHeader;
	}

	public void setExRoutingHeader(RoutingHeader exRoutingHeader) {
		this.exRoutingHeader = exRoutingHeader;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return this.fsCustomer;
	}

	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BENEFICIARY_ID")
	public BeneficaryMaster getExBeneficaryMaster() {
		return this.exBeneficaryMaster;
	}

	public void setExBeneficaryMaster(BeneficaryMaster exBeneficaryMaster) {
		this.exBeneficaryMaster = exBeneficaryMaster;
	}

	@Column(name = "DOCUMENT_CODE", precision = 2, scale = 0)
	public Byte getDocumentCode() {
		return this.documentCode;
	}

	public void setDocumentCode(Byte documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_NO", precision = 14, scale = 0)
	public Long getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(Long documentNo) {
		this.documentNo = documentNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DOCUMENT_DATE", length = 7)
	public Date getDocumentDate() {
		return this.documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "APPLICATION_FINANCE_YR", precision = 4, scale = 0)
	public Short getApplicationFinanceYr() {
		return this.applicationFinanceYr;
	}

	public void setApplicationFinanceYr(Short applicationFinanceYr) {
		this.applicationFinanceYr = applicationFinanceYr;
	}

	@Column(name = "APPLICATION_NO", precision = 14, scale = 0)
	public Long getApplicationNo() {
		return this.applicationNo;
	}

	public void setApplicationNo(Long applicationNo) {
		this.applicationNo = applicationNo;
	}

	@Column(name = "CORESPONDING_COUNTRY_ID", precision = 22, scale = 0)
	public BigDecimal getCorespondingCountryId() {
		return this.corespondingCountryId;
	}

	public void setCorespondingCountryId(BigDecimal corespondingCountryId) {
		this.corespondingCountryId = corespondingCountryId;
	}

	@Column(name = "DEBIT_ACCT", length = 60)
	public String getDebitAcct() {
		return this.debitAcct;
	}

	public void setDebitAcct(String debitAcct) {
		this.debitAcct = debitAcct;
	}

	@Column(name = "FOREIGN_TRNX_AMOUNT", precision = 22, scale = 3)
	public BigDecimal getForeignTrnxAmount() {
		return this.foreignTrnxAmount;
	}

	public void setForeignTrnxAmount(BigDecimal foreignTrnxAmount) {
		this.foreignTrnxAmount = foreignTrnxAmount;
	}

	@Column(name = "LOCAL_TRANX_CURRENCY_ID", precision = 22, scale = 0)
	public BigDecimal getLocalTranxCurrencyId() {
		return this.localTranxCurrencyId;
	}

	public void setLocalTranxCurrencyId(BigDecimal localTranxCurrencyId) {
		this.localTranxCurrencyId = localTranxCurrencyId;
	}

	@Column(name = "LOCAL_TRANX_AMOUNT", precision = 18, scale = 3)
	public BigDecimal getLocalTranxAmount() {
		return this.localTranxAmount;
	}

	public void setLocalTranxAmount(BigDecimal localTranxAmount) {
		this.localTranxAmount = localTranxAmount;
	}

	@Column(name = "QUOTE_NAME", length = 11)
	public String getQuoteName() {
		return this.quoteName;
	}

	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	@Column(name = "EXCHANGE_RATE_APPLIED", precision = 22, scale = 0)
	public BigDecimal getExchangeRateApplied() {
		return this.exchangeRateApplied;
	}

	public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
		this.exchangeRateApplied = exchangeRateApplied;
	}

	@Column(name = "LOCAL_COMMISION_CURRENCY_ID", length = 3)
	public String getLocalCommisionCurrencyId() {
		return this.localCommisionCurrencyId;
	}

	public void setLocalCommisionCurrencyId(String localCommisionCurrencyId) {
		this.localCommisionCurrencyId = localCommisionCurrencyId;
	}

	@Column(name = "LOCAL_COMMISION_AMOUNT", precision = 18, scale = 3)
	public BigDecimal getLocalCommisionAmount() {
		return this.localCommisionAmount;
	}

	public void setLocalCommisionAmount(BigDecimal localCommisionAmount) {
		this.localCommisionAmount = localCommisionAmount;
	}

	@Column(name = "LOCAL_CHARGE_CURRENCY_ID", length = 3)
	public String getLocalChargeCurrencyId() {
		return this.localChargeCurrencyId;
	}

	public void setLocalChargeCurrencyId(String localChargeCurrencyId) {
		this.localChargeCurrencyId = localChargeCurrencyId;
	}

	@Column(name = "LOCAL_CHARGE_AMOUNT", precision = 18, scale = 3)
	public BigDecimal getLocalChargeAmount() {
		return this.localChargeAmount;
	}

	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}

	@Column(name = "LOCAL_DELIVERY_CURRENCY_ID", precision = 22, scale = 0)
	public BigDecimal getLocalDeliveryCurrencyId() {
		return this.localDeliveryCurrencyId;
	}

	public void setLocalDeliveryCurrencyId(BigDecimal localDeliveryCurrencyId) {
		this.localDeliveryCurrencyId = localDeliveryCurrencyId;
	}

	@Column(name = "LOCAL_DELIVERY_AMOUNT", precision = 18, scale = 3)
	public BigDecimal getLocalDeliveryAmount() {
		return this.localDeliveryAmount;
	}

	public void setLocalDeliveryAmount(BigDecimal localDeliveryAmount) {
		this.localDeliveryAmount = localDeliveryAmount;
	}

	@Column(name = "LOCAL_NET_CURRENCY_ID", precision = 22, scale = 0)
	public BigDecimal getLocalNetCurrencyId() {
		return this.localNetCurrencyId;
	}

	public void setLocalNetCurrencyId(BigDecimal localNetCurrencyId) {
		this.localNetCurrencyId = localNetCurrencyId;
	}

	@Column(name = "LOCAL_NET_TRNX_AMOUNT", precision = 18, scale = 3)
	public BigDecimal getLocalNetTrnxAmount() {
		return this.localNetTrnxAmount;
	}

	public void setLocalNetTrnxAmount(BigDecimal localNetTrnxAmount) {
		this.localNetTrnxAmount = localNetTrnxAmount;
	}

	@Column(name = "TRANSACTION_STATUS", length = 1)
	public String getTransactionStatus() {
		return this.transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TRANSACTION_PAID_DATE", length = 7)
	public Date getTransactionPaidDate() {
		return this.transactionPaidDate;
	}

	public void setTransactionPaidDate(Date transactionPaidDate) {
		this.transactionPaidDate = transactionPaidDate;
	}

	@Column(name = "STOP_PAYMENT_STATUS", length = 1)
	public String getStopPaymentStatus() {
		return this.stopPaymentStatus;
	}

	public void setStopPaymentStatus(String stopPaymentStatus) {
		this.stopPaymentStatus = stopPaymentStatus;
	}

	@Column(name = "CANCELLATION_STATUS", length = 1)
	public String getCancellationStatus() {
		return this.cancellationStatus;
	}

	public void setCancellationStatus(String cancellationStatus) {
		this.cancellationStatus = cancellationStatus;
	}

	@Column(name = "GENERAL_LEDGER_ERR", length = 1)
	public String getGeneralLedgerErr() {
		return this.generalLedgerErr;
	}

	public void setGeneralLedgerErr(String generalLedgerErr) {
		this.generalLedgerErr = generalLedgerErr;
	}

	@Column(name = "IS_GENERAL_LEDGER_ENTRY", length = 1)
	public String getIsGeneralLedgerEntry() {
		return this.isGeneralLedgerEntry;
	}

	public void setIsGeneralLedgerEntry(String isGeneralLedgerEntry) {
		this.isGeneralLedgerEntry = isGeneralLedgerEntry;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "GENERAL_LEDGER_DATE", length = 7)
	public Date getGeneralLedgerDate() {
		return this.generalLedgerDate;
	}

	public void setGeneralLedgerDate(Date generalLedgerDate) {
		this.generalLedgerDate = generalLedgerDate;
	}

	@Column(name = "BLACK_LIST_INDICATOR", length = 1)
	public String getBlackListIndicator() {
		return this.blackListIndicator;
	}

	public void setBlackListIndicator(String blackListIndicator) {
		this.blackListIndicator = blackListIndicator;
	}

	@Column(name = "BATCH_FINANCE_YR", precision = 4, scale = 0)
	public Short getBatchFinanceYr() {
		return this.batchFinanceYr;
	}

	public void setBatchFinanceYr(Short batchFinanceYr) {
		this.batchFinanceYr = batchFinanceYr;
	}

	@Column(name = "BATCH_NUMBER", precision = 8, scale = 0)
	public Integer getBatchNumber() {
		return this.batchNumber;
	}

	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}

	@Column(name = "BATCH_LINE_NUMBER", precision = 4, scale = 0)
	public Short getBatchLineNumber() {
		return this.batchLineNumber;
	}

	public void setBatchLineNumber(Short batchLineNumber) {
		this.batchLineNumber = batchLineNumber;
	}

	@Column(name = "COLLECTION_DOCUMENT_ID", precision = 2, scale = 0)
	public Byte getCollectionDocumentId() {
		return this.collectionDocumentId;
	}

	public void setCollectionDocumentId(Byte collectionDocumentId) {
		this.collectionDocumentId = collectionDocumentId;
	}

	@Column(name = "COLLECTION_DOCUMENT_CODE", precision = 2, scale = 0)
	public Byte getCollectionDocumentCode() {
		return this.collectionDocumentCode;
	}

	public void setCollectionDocumentCode(Byte collectionDocumentCode) {
		this.collectionDocumentCode = collectionDocumentCode;
	}

	@Column(name = "COLLECTION_DOCUMENT_FIN_YEAR", precision = 4, scale = 0)
	public Short getCollectionDocumentFinYear() {
		return this.collectionDocumentFinYear;
	}

	public void setCollectionDocumentFinYear(Short collectionDocumentFinYear) {
		this.collectionDocumentFinYear = collectionDocumentFinYear;
	}

	@Column(name = "COLLECTION_DOCUMENT_NO", precision = 8, scale = 0)
	public Integer getCollectionDocumentNo() {
		return this.collectionDocumentNo;
	}

	public void setCollectionDocumentNo(Integer collectionDocumentNo) {
		this.collectionDocumentNo = collectionDocumentNo;
	}

	@Column(name = "FILE_CREATION", length = 1)
	public String getFileCreation() {
		return this.fileCreation;
	}

	public void setFileCreation(String fileCreation) {
		this.fileCreation = fileCreation;
	}

	@Column(name = "SPECIAL_INDICATOR", length = 1)
	public String getSpecialIndicator() {
		return this.specialIndicator;
	}

	public void setSpecialIndicator(String specialIndicator) {
		this.specialIndicator = specialIndicator;
	}

	@Column(name = "RECEIVER", length = 40)
	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "TRNFSTS_UPDBY", length = 15)
	public String getTrnfstsUpdby() {
		return this.trnfstsUpdby;
	}

	public void setTrnfstsUpdby(String trnfstsUpdby) {
		this.trnfstsUpdby = trnfstsUpdby;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TRNFSTS_UPDDAT", length = 7)
	public Date getTrnfstsUpddat() {
		return this.trnfstsUpddat;
	}

	public void setTrnfstsUpddat(Date trnfstsUpddat) {
		this.trnfstsUpddat = trnfstsUpddat;
	}

	@Column(name = "FILE_FINANCE_YR", precision = 4, scale = 0)
	public Short getFileFinanceYr() {
		return this.fileFinanceYr;
	}

	public void setFileFinanceYr(Short fileFinanceYr) {
		this.fileFinanceYr = fileFinanceYr;
	}

	@Column(name = "FILE_NUMBER", precision = 8, scale = 0)
	public Integer getFileNumber() {
		return this.fileNumber;
	}

	public void setFileNumber(Integer fileNumber) {
		this.fileNumber = fileNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FILE_CREATION_DATE", length = 7)
	public Date getFileCreationDate() {
		return this.fileCreationDate;
	}

	public void setFileCreationDate(Date fileCreationDate) {
		this.fileCreationDate = fileCreationDate;
	}

	@Column(name = "BANK_FLEX_FIELD1", length = 200)
	public String getBankFlexField1() {
		return this.bankFlexField1;
	}

	public void setBankFlexField1(String bankFlexField1) {
		this.bankFlexField1 = bankFlexField1;
	}

	@Column(name = "BANK_FLEX_FIELD2", length = 200)
	public String getBankFlexField2() {
		return this.bankFlexField2;
	}

	public void setBankFlexField2(String bankFlexField2) {
		this.bankFlexField2 = bankFlexField2;
	}

	@Column(name = "TRANSACTION_TAGGED_STATUS", length = 1)
	public String getTransactionTaggedStatus() {
		return this.transactionTaggedStatus;
	}

	public void setTransactionTaggedStatus(String transactionTaggedStatus) {
		this.transactionTaggedStatus = transactionTaggedStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TAGGED_DATE", length = 7)
	public Date getTaggedDate() {
		return this.taggedDate;
	}

	public void setTaggedDate(Date taggedDate) {
		this.taggedDate = taggedDate;
	}

	@Column(name = "TAGGED_BY", length = 15)
	public String getTaggedBy() {
		return this.taggedBy;
	}

	public void setTaggedBy(String taggedBy) {
		this.taggedBy = taggedBy;
	}

	@Column(name = "BANK_REFERENCE_NUMBER", length = 20)
	public String getBankReferenceNumber() {
		return this.bankReferenceNumber;
	}

	public void setBankReferenceNumber(String bankReferenceNumber) {
		this.bankReferenceNumber = bankReferenceNumber;
	}

	@Column(name = "DELIVERY_INDICATOR", length = 1)
	public String getDeliveryIndicator() {
		return this.deliveryIndicator;
	}

	public void setDeliveryIndicator(String deliveryIndicator) {
		this.deliveryIndicator = deliveryIndicator;
	}

	@Column(name = "REMITTANCE_MODE", length = 2)
	public String getRemittanceMode() {
		return this.remittanceMode;
	}

	public void setRemittanceMode(String remittanceMode) {
		this.remittanceMode = remittanceMode;
	}

	@Column(name = "DELIVERY_MODE", length = 2)
	public String getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Column(name = "SMS_SEQ", precision = 22, scale = 0)
	public BigDecimal getSmsSeq() {
		return this.smsSeq;
	}

	public void setSmsSeq(BigDecimal smsSeq) {
		this.smsSeq = smsSeq;
	}

	@Column(name = "ECNO", length = 20)
	public String getEcno() {
		return this.ecno;
	}

	public void setEcno(String ecno) {
		this.ecno = ecno;
	}

	@Column(name = "CBK_FILE_CREATE", length = 1)
	public String getCbkFileCreate() {
		return this.cbkFileCreate;
	}

	public void setCbkFileCreate(String cbkFileCreate) {
		this.cbkFileCreate = cbkFileCreate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ACCOUNT_MMYYYY", length = 7)
	public Date getAccountMmyyyy() {
		return this.accountMmyyyy;
	}

	public void setAccountMmyyyy(Date accountMmyyyy) {
		this.accountMmyyyy = accountMmyyyy;
	}

	@Column(name = "WESTERN_UNION_MTCNO", length = 30)
	public String getWesternUnionMtcno() {
		return this.westernUnionMtcno;
	}

	public void setWesternUnionMtcno(String westernUnionMtcno) {
		this.westernUnionMtcno = westernUnionMtcno;
	}

	@Column(name = "PRINT_MODE", length = 1)
	public String getPrintMode() {
		return this.printMode;
	}

	public void setPrintMode(String printMode) {
		this.printMode = printMode;
	}

	@Column(name = "BENEFICIARY_NAME", length = 100)
	public String getBeneficiaryName() {
		return this.beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	@Column(name = "HIGH_VALUE_TRNX", length = 1)
	public String getHighValueTrnx() {
		return this.highValueTrnx;
	}

	public void setHighValueTrnx(String highValueTrnx) {
		this.highValueTrnx = highValueTrnx;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "CREATED_BY", length = 80)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE", length = 7)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 80)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE", length = 7)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
