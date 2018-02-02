package com.amg.exchange.stoppayment.model;

	// Nazish Ehsan hashmi

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
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
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.RemittanceModeMaster;

	
	@Entity
	@Table(name = "EX_REMIT_TRNX")
	public class RemittanceTransaction implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private BigDecimal remittanceTransactionId;
		private CountryMaster applicationCountryId;
		private CompanyMaster companyId;
		private BigDecimal documentFinanceYear;
		private Document documentId;
		private BigDecimal documentNo;
		private CountryBranch branchId;
		private Date documentDate;
		private BigDecimal applicationFinanceYear;
		private BigDecimal applicationDocumentNo;
		private Customer customerId;
		private BigDecimal customerRef;
		private CountryMaster bankCountryId;
		//private CountryMaster corespondingCountryId;
		private BankMaster bankId;
		private BankBranch bankBranchId;
		private String debitAccountNo;
		private CurrencyMaster foreignCurrencyId;
		private BigDecimal foreignTranxAmount;
		private CurrencyMaster localTranxCurrencyId;
		private BigDecimal localTranxAmount;
		private BigDecimal exchangeRateApplied;
		private CurrencyMaster localCommisionCurrencyId;
		private BigDecimal localCommisionAmount;
		private CurrencyMaster localChargeCurrencyId;
		private BigDecimal localChargeAmount;
		private CurrencyMaster localDeliveryCurrencyId;
		private BigDecimal localDeliveryAmount;
		private CurrencyMaster localNetCurrencyId;
		private BigDecimal localNetTranxAmount;
		private String transactionStatus;
		private String transactionUpdatedBy;
		private Date transactionUpdatedDate;
		//private BigDecimal batchFinancialYear;
		//private BigDecimal batchDocumentNumber;
		private String generalLedgerEntry;
		private String generalLedgerErr;
		private BigDecimal fileFinancialYear;
		private BigDecimal fileNumber;
		private String bankReference;
		private String transferMode;
		private String webServiceStatus;
		private String fileCreation;
		private BigDecimal smsSeqNumber;
		private String highValueTranx;
		private BigDecimal collectionDocId;
		private BigDecimal collectionDocCode;
		private BigDecimal collectionDocFinanceYear;
		private BigDecimal collectionDocumentNo;
		private String blackListIndicator;
		private RemittanceModeMaster remittanceModeId;
		private DeliveryMode deliveryModeId;
		private Date accountMmyyyy;
		private String westernUnionMtcno;
		private Date createdDate;
		private String createdBy;
		private Date modifiedDate;
		private String modifiedBy;
		private String isactive;
		private BigDecimal usdAmount;
		private String highValueAuthUser;
		private Date highValueAuthDate;
		private String customerSignature;
		private Clob customerSignatureClob;
		private BigDecimal documentFinanceYr;
		private BigDecimal sourceofincome;
		private String instruction;
		
		// newly added columns DW_FLAG,CUSTOMER_NAME,TRANSFER_MODE_ID,ORIGINAL_EXCHANGE_RATE,SPOT_RATE_IND,LOYALTY_POINTS_IND,LOYALTY_POINTS_ENCASHED,EMPLOYEE_ID
		private String dwFlag;
		private String customerName;
		private BigDecimal transferModeId;
		private BigDecimal originalExchangeRate;
		private String spotRateInd;
		private String loyaltyPointsInd;
		private BigDecimal loyaltyPointsEncashed;
		private BigDecimal employeeId;
		

		public RemittanceTransaction() {
		}
			
		public RemittanceTransaction(BigDecimal remittanceTransactionId,
				CountryMaster applicationCountryId, CompanyMaster companyId,
				BigDecimal documentFinanceYear, Document documentId,
				BigDecimal documentNo, CountryBranch branchId,
				Date documentDate, BigDecimal applicationFinanceYear,
				BigDecimal applicationDocumentNo, Customer customerId,
				BigDecimal customerRef, CountryMaster bankCountryId,
				BankMaster bankId, BankBranch bankBranchId,
				String debitAccountNo, CurrencyMaster foreignCurrencyId,
				BigDecimal foreignTranxAmount,
				CurrencyMaster localTranxCurrencyId,
				BigDecimal localTranxAmount, BigDecimal exchangeRateApplied,
				CurrencyMaster localCommisionCurrencyId,
				BigDecimal localCommisionAmount,
				CurrencyMaster localChargeCurrencyId,
				BigDecimal localChargeAmount,
				CurrencyMaster localDeliveryCurrencyId,
				BigDecimal localDeliveryAmount,
				CurrencyMaster localNetCurrencyId,
				BigDecimal localNetTranxAmount, String transactionStatus,
				String transactionUpdatedBy, Date transactionUpdatedDate,
				String generalLedgerEntry, String generalLedgerErr,
				BigDecimal fileFinancialYear, BigDecimal fileNumber,
				String bankReference, String transferMode,
				String webServiceStatus, String fileCreation,
				BigDecimal smsSeqNumber, String highValueTranx,
				BigDecimal collectionDocId, BigDecimal collectionDocCode,
				BigDecimal collectionDocFinanceYear,
				BigDecimal collectionDocumentNo, String blackListIndicator,
				RemittanceModeMaster remittanceModeId,
				DeliveryMode deliveryModeId, Date accountMmyyyy,
				String westernUnionMtcno, Date createdDate, String createdBy,
				Date modifiedDate, String modifiedBy, String isactive,
				BigDecimal usdAmount, String highValueAuthUser,
				Date highValueAuthDate, String customerSignature,
				Clob customerSignatureClob, BigDecimal documentFinanceYr,
				BigDecimal sourceofincome, String instruction, String dwFlag,
				String customerName, BigDecimal transferModeId,
				BigDecimal originalExchangeRate, String spotRateInd,
				String loyaltyPointsInd, BigDecimal loyaltyPointsEncashed,
				BigDecimal employeeId) {
			super();
			this.remittanceTransactionId = remittanceTransactionId;
			this.applicationCountryId = applicationCountryId;
			this.companyId = companyId;
			this.documentFinanceYear = documentFinanceYear;
			this.documentId = documentId;
			this.documentNo = documentNo;
			this.branchId = branchId;
			this.documentDate = documentDate;
			this.applicationFinanceYear = applicationFinanceYear;
			this.applicationDocumentNo = applicationDocumentNo;
			this.customerId = customerId;
			this.customerRef = customerRef;
			this.bankCountryId = bankCountryId;
			this.bankId = bankId;
			this.bankBranchId = bankBranchId;
			this.debitAccountNo = debitAccountNo;
			this.foreignCurrencyId = foreignCurrencyId;
			this.foreignTranxAmount = foreignTranxAmount;
			this.localTranxCurrencyId = localTranxCurrencyId;
			this.localTranxAmount = localTranxAmount;
			this.exchangeRateApplied = exchangeRateApplied;
			this.localCommisionCurrencyId = localCommisionCurrencyId;
			this.localCommisionAmount = localCommisionAmount;
			this.localChargeCurrencyId = localChargeCurrencyId;
			this.localChargeAmount = localChargeAmount;
			this.localDeliveryCurrencyId = localDeliveryCurrencyId;
			this.localDeliveryAmount = localDeliveryAmount;
			this.localNetCurrencyId = localNetCurrencyId;
			this.localNetTranxAmount = localNetTranxAmount;
			this.transactionStatus = transactionStatus;
			this.transactionUpdatedBy = transactionUpdatedBy;
			this.transactionUpdatedDate = transactionUpdatedDate;
			this.generalLedgerEntry = generalLedgerEntry;
			this.generalLedgerErr = generalLedgerErr;
			this.fileFinancialYear = fileFinancialYear;
			this.fileNumber = fileNumber;
			this.bankReference = bankReference;
			this.transferMode = transferMode;
			this.webServiceStatus = webServiceStatus;
			this.fileCreation = fileCreation;
			this.smsSeqNumber = smsSeqNumber;
			this.highValueTranx = highValueTranx;
			this.collectionDocId = collectionDocId;
			this.collectionDocCode = collectionDocCode;
			this.collectionDocFinanceYear = collectionDocFinanceYear;
			this.collectionDocumentNo = collectionDocumentNo;
			this.blackListIndicator = blackListIndicator;
			this.remittanceModeId = remittanceModeId;
			this.deliveryModeId = deliveryModeId;
			this.accountMmyyyy = accountMmyyyy;
			this.westernUnionMtcno = westernUnionMtcno;
			this.createdDate = createdDate;
			this.createdBy = createdBy;
			this.modifiedDate = modifiedDate;
			this.modifiedBy = modifiedBy;
			this.isactive = isactive;
			this.usdAmount = usdAmount;
			this.highValueAuthUser = highValueAuthUser;
			this.highValueAuthDate = highValueAuthDate;
			this.customerSignature = customerSignature;
			this.customerSignatureClob = customerSignatureClob;
			this.documentFinanceYr = documentFinanceYr;
			this.sourceofincome = sourceofincome;
			this.instruction = instruction;
			this.dwFlag = dwFlag;
			this.customerName = customerName;
			this.transferModeId = transferModeId;
			this.originalExchangeRate = originalExchangeRate;
			this.spotRateInd = spotRateInd;
			this.loyaltyPointsInd = loyaltyPointsInd;
			this.loyaltyPointsEncashed = loyaltyPointsEncashed;
			this.employeeId = employeeId;
		}




		@Id
		@GeneratedValue(generator="ex_remittance_transaction_seq",strategy=GenerationType.SEQUENCE)
		@SequenceGenerator(name="ex_remittance_transaction_seq",sequenceName="EX_REMITTANCE_TRANSACTION_SEQ",allocationSize=1)
		@Column(name = "REMITTANCE_TRANSACTION_ID", unique = true, nullable = false, precision = 22, scale = 0)
		public BigDecimal getRemittanceTransactionId() {
			return this.remittanceTransactionId;
		}
		public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
			this.remittanceTransactionId = remittanceTransactionId;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "APPLICATION_COUNTRY_ID")
		public CountryMaster getApplicationCountryId() {
			return applicationCountryId;
		}
		public void setApplicationCountryId(CountryMaster applicationCountryId) {
			this.applicationCountryId = applicationCountryId;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "COMPANY_ID")
		public CompanyMaster getCompanyId() {
			return companyId;
		}
		public void setCompanyId(CompanyMaster companyId) {
			this.companyId = companyId;
		}
		
		//@ManyToOne(fetch = FetchType.LAZY)
		//@JoinColumn(name = "DOCUMENT_FINANCE_YEAR")
		
		@Column(name = "DOCUMENT_FINANCE_YEAR")
		public BigDecimal getDocumentFinanceYear() {
			return documentFinanceYear;
		}
		public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
			this.documentFinanceYear = documentFinanceYear;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "DOCUMENT_ID")
		public Document getDocumentId() {
			return documentId;
		}
		public void setDocumentId(Document documentId) {
			this.documentId = documentId;
		}
		
		@Column(name = "DOCUMENT_NO")
		public BigDecimal getDocumentNo() {
			return documentNo;
		}
		public void setDocumentNo(BigDecimal documentNo) {
			this.documentNo = documentNo;
		}
		
		//@JoinColumn(name = "BRANCH_ID")
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="COUNTRY_BRANCH_ID")
		public CountryBranch getBranchId() {
			return branchId;
		}
		public void setBranchId(CountryBranch branchId) {
			this.branchId = branchId;
		}
		
		@Column(name = "DOCUMENT_DATE")
		public Date getDocumentDate() {
			return documentDate;
		}
		public void setDocumentDate(Date documentDate) {
			this.documentDate = documentDate;
		}
		
		//@ManyToOne(fetch = FetchType.LAZY)
		//@JoinColumn(name = "APPLICATION_FINANCE_YEAR")
		
		@Column(name = "APPLICATION_FINANCE_YEAR")
		public BigDecimal getApplicationFinanceYear() {
			return applicationFinanceYear;
		}
		public void setApplicationFinanceYear(BigDecimal applicationFinanceYear) {
			this.applicationFinanceYear = applicationFinanceYear;
		}
		
		@Column(name = "APPLICATION_DOCUMENT_NO")
		public BigDecimal getApplicationDocumentNo() {
			return applicationDocumentNo;
		}
		public void setApplicationDocumentNo(BigDecimal applicationDocumentNo) {
			this.applicationDocumentNo = applicationDocumentNo;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "CUSTOMER_ID")
		public Customer getCustomerId() {
			return customerId;
		}
		public void setCustomerId(Customer customerId) {
			this.customerId = customerId;
		}
		
		@Column(name = "CUSTOMER_REFERENCE")
		public BigDecimal getCustomerRef() {
			return customerRef;
		}
		public void setCustomerRef(BigDecimal customerRef) {
			this.customerRef = customerRef;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "BANK_COUNTRY_ID")
		public CountryMaster getBankCountryId() {
			return bankCountryId;
		}
		public void setBankCountryId(CountryMaster bankCountryId) {
			this.bankCountryId = bankCountryId;
		}
		
		/*@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "CORESPONDING_COUNTRY_ID")
		public CountryMaster getCorespondingCountryId() {
			return corespondingCountryId;
		}

		public void setCorespondingCountryId(CountryMaster corespondingCountryId) {
			this.corespondingCountryId = corespondingCountryId;
		}*/
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "BANK_ID")
		public BankMaster getBankId() {
			return bankId;
		}
		public void setBankId(BankMaster bankId) {
			this.bankId = bankId;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "BANK_BRANCH_ID")
		public BankBranch getBankBranchId() {
			return bankBranchId;
		}
		public void setBankBranchId(BankBranch bankBranchId) {
			this.bankBranchId = bankBranchId;
		}
		
		@Column(name = "DEBIT_ACCOUNT_NO")		
		public String getDebitAccountNo() {
			return debitAccountNo;
		}
		public void setDebitAccountNo(String debitAccountNo) {
			this.debitAccountNo = debitAccountNo;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "FOREIGN_CURRENCY_ID")
		public CurrencyMaster getForeignCurrencyId() {
			return foreignCurrencyId;
		}
		public void setForeignCurrencyId(CurrencyMaster foreignCurrencyId) {
			this.foreignCurrencyId = foreignCurrencyId;
		}
		
		@Column(name = "FOREIGN_TRANX_AMOUNT")
		public BigDecimal getForeignTranxAmount() {
			return foreignTranxAmount;
		}
		public void setForeignTranxAmount(BigDecimal foreignTranxAmount) {
			this.foreignTranxAmount = foreignTranxAmount;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "LOCAL_TRANX_CURRENCY_ID")
		public CurrencyMaster getLocalTranxCurrencyId() {
			return localTranxCurrencyId;
		}
		public void setLocalTranxCurrencyId(CurrencyMaster localTranxCurrencyId) {
			this.localTranxCurrencyId = localTranxCurrencyId;
		}
		
		@Column(name = "LOCAL_TRANX_AMOUNT")
		public BigDecimal getLocalTranxAmount() {
			return localTranxAmount;
		}
		public void setLocalTranxAmount(BigDecimal localTranxAmount) {
			this.localTranxAmount = localTranxAmount;
		}
		
		@Column(name = "EXCHANGE_RATE_APPLIED")
		public BigDecimal getExchangeRateApplied() {
			return exchangeRateApplied;
		}
		public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
			this.exchangeRateApplied = exchangeRateApplied;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "LOCAL_COMMISION_CURRENCY_ID")
		public CurrencyMaster getLocalCommisionCurrencyId() {
			return localCommisionCurrencyId;
		}
		public void setLocalCommisionCurrencyId(CurrencyMaster localCommisionCurrencyId) {
			this.localCommisionCurrencyId = localCommisionCurrencyId;
		}
		
		@Column(name = "LOCAL_COMMISION_AMOUNT")
		public BigDecimal getLocalCommisionAmount() {
			return localCommisionAmount;
		}
		public void setLocalCommisionAmount(BigDecimal localCommisionAmount) {
			this.localCommisionAmount = localCommisionAmount;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "LOCAL_CHARGE_CURRENCY_ID")
		public CurrencyMaster getLocalChargeCurrencyId() {
			return localChargeCurrencyId;
		}
		public void setLocalChargeCurrencyId(CurrencyMaster localChargeCurrencyId) {
			this.localChargeCurrencyId = localChargeCurrencyId;
		}
		
		@Column(name = "LOCAL_CHARGE_AMOUNT")
		public BigDecimal getLocalChargeAmount() {
			return localChargeAmount;
		}
		public void setLocalChargeAmount(BigDecimal localChargeAmount) {
			this.localChargeAmount = localChargeAmount;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "LOCAL_DELIVERY_CURRENCY_ID")
		public CurrencyMaster getLocalDeliveryCurrencyId() {
			return localDeliveryCurrencyId;
		}
		public void setLocalDeliveryCurrencyId(CurrencyMaster localDeliveryCurrencyId) {
			this.localDeliveryCurrencyId = localDeliveryCurrencyId;
		}
		
		@Column(name = "LOCAL_DELIVERY_AMOUNT")
		public BigDecimal getLocalDeliveryAmount() {
			return localDeliveryAmount;
		}
		public void setLocalDeliveryAmount(BigDecimal localDeliveryAmount) {
			this.localDeliveryAmount = localDeliveryAmount;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "LOCAL_NET_CURRENCY_ID")
		public CurrencyMaster getLocalNetCurrencyId() {
			return localNetCurrencyId;
		}
		public void setLocalNetCurrencyId(CurrencyMaster localNetCurrencyId) {
			this.localNetCurrencyId = localNetCurrencyId;
		}
		
		@Column(name = "LOCAL_NET_TRANX_AMOUNT")
		public BigDecimal getLocalNetTranxAmount() {
			return localNetTranxAmount;
		}
		public void setLocalNetTranxAmount(BigDecimal localNetTranxAmount) {
			this.localNetTranxAmount = localNetTranxAmount;
		}
		
		@Column(name = "TRANSACTION_STATUS")
		public String getTransactionStatus() {
			return transactionStatus;
		}
		public void setTransactionStatus(String transactionStatus) {
			this.transactionStatus = transactionStatus;
		}
		
		@Column(name = "TRANSACTION_UPDATED_BY")
		public String getTransactionUpdatedBy() {
			return transactionUpdatedBy;
		}
		public void setTransactionUpdatedBy(String transactionUpdatedBy) {
			this.transactionUpdatedBy = transactionUpdatedBy;
		}
		
		@Column(name = "TRANSACTION_UPDATED_DATE")
		public Date getTransactionUpdatedDate() {
			return transactionUpdatedDate;
		}
		public void setTransactionUpdatedDate(Date transactionUpdatedDate) {
			this.transactionUpdatedDate = transactionUpdatedDate;
		}
		
		/*@Column(name = "BATCH_FINANCIAL_YEAR")
		public BigDecimal getBatchFinancialYear() {
			return batchFinancialYear;
		}

		public void setBatchFinancialYear(BigDecimal batchFinancialYear) {
			this.batchFinancialYear = batchFinancialYear;
		}
		@Column(name = "BATCH_DOCUMENT_NUMBER")
		public BigDecimal getBatchDocumentNumber() {
			return batchDocumentNumber;
		}

		public void setBatchDocumentNumber(BigDecimal batchDocumentNumber) {
			this.batchDocumentNumber = batchDocumentNumber;
		}*/
		
		@Column(name = "GENERAL_LEDGER_ENTRY")
		public String getGeneralLedgerEntry() {
			return generalLedgerEntry;
		}
		public void setGeneralLedgerEntry(String generalLedgerEntry) {
			this.generalLedgerEntry = generalLedgerEntry;
		}
		
		@Column(name = "GENERAL_LEDGER_ERR")
		public String getGeneralLedgerErr() {
			return generalLedgerErr;
		}
		public void setGeneralLedgerErr(String generalLedgerErr) {
			this.generalLedgerErr = generalLedgerErr;
		}
		
		@Column(name = "FILE_FINANCE_YEAR")
		public BigDecimal getFileFinancialYear() {
			return fileFinancialYear;
		}
		public void setFileFinancialYear(BigDecimal fileFinancialYear) {
			this.fileFinancialYear = fileFinancialYear;
		}
		
		@Column(name = "FILE_NUMBER")
		public BigDecimal getFileNumber() {
			return fileNumber;
		}
		public void setFileNumber(BigDecimal fileNumber) {
			this.fileNumber = fileNumber;
		}
		
		@Column(name = "BANK_REFERENCE")
		public String getBankReference() {
			return bankReference;
		}
		public void setBankReference(String bankReference) {
			this.bankReference = bankReference;
		}
		
		@Column(name = "TRANSFER_MODE")
		public String getTransferMode() {
			return transferMode;
		}
		public void setTransferMode(String transferMode) {
			this.transferMode = transferMode;
		}
		
		@Column(name = "WEB_SERVICE_STATUS")
		public String getWebServiceStatus() {
			return webServiceStatus;
		}
		public void setWebServiceStatus(String webServiceStatus) {
			this.webServiceStatus = webServiceStatus;
		}
		
		@Column(name = "FILE_CREATION")
		public String getFileCreation() {
			return fileCreation;
		}
		public void setFileCreation(String fileCreation) {
			this.fileCreation = fileCreation;
		}
		
		@Column(name = "SMS_SEQ_NUMBER")
		public BigDecimal getSmsSeqNumber() {
			return smsSeqNumber;
		}
		public void setSmsSeqNumber(BigDecimal smsSeqNumber) {
			this.smsSeqNumber = smsSeqNumber;
		}
		
		@Column(name = "HIGH_VALUE_TRANX")
		public String getHighValueTranx() {
			return highValueTranx;
		}
		public void setHighValueTranx(String highValueTranx) {
			this.highValueTranx = highValueTranx;
		}
		
		@Column(name = "COLLECTION_DOC_ID")
		public BigDecimal getCollectionDocId() {
			return collectionDocId;
		}
		public void setCollectionDocId(BigDecimal collectionDocId) {
			this.collectionDocId = collectionDocId;
		}
		
		@Column(name = "COLLECTION_DOC_CODE")
		public BigDecimal getCollectionDocCode() {
			return collectionDocCode;
		}
		public void setCollectionDocCode(BigDecimal collectionDocCode) {
			this.collectionDocCode = collectionDocCode;
		}

		@Column(name = "COLLECTION_DOC_FINANCE_YEAR")
		public BigDecimal getCollectionDocFinanceYear() {
			return collectionDocFinanceYear;
		}
		public void setCollectionDocFinanceYear(BigDecimal collectionDocFinanceYear) {
			this.collectionDocFinanceYear = collectionDocFinanceYear;
		}
		
		@Column(name = "COLLECTION_DOCUMENT_NO")
		public BigDecimal getCollectionDocumentNo() {
			return collectionDocumentNo;
		}
		public void setCollectionDocumentNo(BigDecimal collectionDocumentNo) {
			this.collectionDocumentNo = collectionDocumentNo;
		}
		
		@Column(name = "BLACK_LIST_INDICATOR")
		public String getBlackListIndicator() {
			return blackListIndicator;
		}
		public void setBlackListIndicator(String blackListIndicator) {
			this.blackListIndicator = blackListIndicator;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "REMITTANCE_MODE_ID")
		public RemittanceModeMaster getRemittanceModeId() {
			return remittanceModeId;
		}
		public void setRemittanceModeId(RemittanceModeMaster remittanceModeId) {
			this.remittanceModeId = remittanceModeId;
		}
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "DELIVERY_MODE_ID")
		public DeliveryMode getDeliveryModeId() {
			return deliveryModeId;
		}
		public void setDeliveryModeId(DeliveryMode deliveryModeId) {
			this.deliveryModeId = deliveryModeId;
		}
		
		@Column(name = "ACCOUNT_MMYYYY")
		public Date getAccountMmyyyy() {
			return accountMmyyyy;
		}
		public void setAccountMmyyyy(Date accountMmyyyy) {
			this.accountMmyyyy = accountMmyyyy;
		}
		
		@Column(name = "WESTERN_UNION_MTCNO")
		public String getWesternUnionMtcno() {
			return westernUnionMtcno;
		}
		public void setWesternUnionMtcno(String westernUnionMtcno) {
			this.westernUnionMtcno = westernUnionMtcno;
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
		
		@Column(name = "ISACTIVE")
		public String getIsactive() {
			return isactive;
		}
		public void setIsactive(String isactive) {
			this.isactive = isactive;
		}
		
		@Column(name = "USD_AMOUNT")
		public BigDecimal getUsdAmount() {
			return usdAmount;
		}
		public void setUsdAmount(BigDecimal usdAmount) {
			this.usdAmount = usdAmount;
		}

		@Column(name = "HIGH_VALUE_AUTHUSER")
		public String getHighValueAuthUser() {
			return highValueAuthUser;
		}
		public void setHighValueAuthUser(String highValueAuthUser) {
			this.highValueAuthUser = highValueAuthUser;
		}

		@Column(name = "HIGH_VALUE_AUTHDAT")
		public Date getHighValueAuthDate() {
			return highValueAuthDate;
		}
		public void setHighValueAuthDate(Date highValueAuthDate) {
			this.highValueAuthDate = highValueAuthDate;
		}

		@Column(name = "SIGNATURE_SPECIMEN")
		public String getCustomerSignature() {
			return customerSignature;
		}
		public void setCustomerSignature(String customerSignature) {
			this.customerSignature = customerSignature;
		}

		@Column(name = "DOCUMENT_FINANCE_YEAR_ID")
		public BigDecimal getDocumentFinanceYr() {
			return documentFinanceYr;
		}
		public void setDocumentFinanceYr(BigDecimal documentFinanceYr) {
			this.documentFinanceYr = documentFinanceYr;
		}

		@Column(name = "SOURCE_OF_INCOME_ID")
		public BigDecimal getSourceofincome() {
			return sourceofincome;
		}
		public void setSourceofincome(BigDecimal sourceofincome) {
			this.sourceofincome = sourceofincome;
		}
		
		@Column(name = "INSTRUCTION")
		public String getInstruction() {
			return instruction;
		}
		public void setInstruction(String instruction) {
			this.instruction = instruction;
		}

		@Column(name="SIGNATURE_SPECIMEN_CLOB")
		public Clob getCustomerSignatureClob() {
			return customerSignatureClob;
		}
		public void setCustomerSignatureClob(Clob customerSignatureClob) {
			this.customerSignatureClob = customerSignatureClob;
		}

		@Column(name="DW_FLAG")
		public String getDwFlag() {
			return dwFlag;
		}
		public void setDwFlag(String dwFlag) {
			this.dwFlag = dwFlag;
		}

		@Column(name="CUSTOMER_NAME")
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		@Column(name="TRANSFER_MODE_ID")
		public BigDecimal getTransferModeId() {
			return transferModeId;
		}
		public void setTransferModeId(BigDecimal transferModeId) {
			this.transferModeId = transferModeId;
		}

		@Column(name="ORIGINAL_EXCHANGE_RATE")
		public BigDecimal getOriginalExchangeRate() {
			return originalExchangeRate;
		}
		public void setOriginalExchangeRate(BigDecimal originalExchangeRate) {
			this.originalExchangeRate = originalExchangeRate;
		}

		@Column(name="SPOT_RATE_IND")
		public String getSpotRateInd() {
			return spotRateInd;
		}
		public void setSpotRateInd(String spotRateInd) {
			this.spotRateInd = spotRateInd;
		}

		@Column(name="LOYALTY_POINTS_IND")
		public String getLoyaltyPointsInd() {
			return loyaltyPointsInd;
		}
		public void setLoyaltyPointsInd(String loyaltyPointsInd) {
			this.loyaltyPointsInd = loyaltyPointsInd;
		}

		@Column(name="LOYALTY_POINTS_ENCASHED")
		public BigDecimal getLoyaltyPointsEncashed() {
			return loyaltyPointsEncashed;
		}
		public void setLoyaltyPointsEncashed(BigDecimal loyaltyPointsEncashed) {
			this.loyaltyPointsEncashed = loyaltyPointsEncashed;
		}

		@Column(name="EMPLOYEE_ID")
		public BigDecimal getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(BigDecimal employeeId) {
			this.employeeId = employeeId;
		}
		
		
		
		
	}


