package com.amg.exchange.foreigncurrency.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import oracle.sql.BLOB;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name = "VW_EX_RECEIPT_PAYMENT")
public class ViewReceiptPayment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal receiptId;
	private BigDecimal fsCountryMaster;
	private BigDecimal localFsCountryMaster;
	private BigDecimal foreignFsCountryMaster;
	private BigDecimal fsCompanyMaster;
	private BigDecimal fsCustomer;
	private BigDecimal documentCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentFinanceYearId;
	private BigDecimal documentNo;
	private BigDecimal countryBranch;
	private BigDecimal purposeOfTransaction;
	private BigDecimal sourceOfIncome;
	private BigDecimal sourceofIncomeId;
	private Date documentDate;
	private String customerName;
	private String transactionType;
	private BigDecimal forignTrnxAmount;
	private BigDecimal localTrnxAmount;
	private BigDecimal transactionActualRate;
	private BigDecimal localNetAmount;
	private String documentStatus;
	private String generalLegerErr;
	private Date generalLegerDate;
	private String isActive;
	private Date accountMMYYYY;
	private BLOB customerSign;
	private BigDecimal colDocCode;
	private BigDecimal colDocFyr;
	private BigDecimal colDocNo;
	private BigDecimal cdepDocCode;
	private BigDecimal cdepDocFyr;
	private BigDecimal cdepDocNo;
	private Date cdepDocDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String remarks;
	private BigDecimal applicationFinanceYear;
	private BigDecimal applicationDocumentNo;
	private String signature;
	private BigDecimal localCommisionCurrencyId;
	private BigDecimal localCommisionAmoumnt;
	private BigDecimal localChargeCurrencyId;
	private BigDecimal localChargeAmount;
	private BigDecimal localDeliveryCurrencyId;
	private BigDecimal localDeliveryAmount;
	private BigDecimal localRateAdjCurrencyId;
	private BigDecimal localRateAmount;
	private BigDecimal localOtherAdjCurrencyId;
	private BigDecimal localOtherAdjAmount;
	private BigDecimal transferReference;
	private BigDecimal transferFinanceYear;
	private BigDecimal customerReference;
	private String westernUnoinMycNo;
	private BigDecimal averageRate;
	private String receiptType;
	private Clob signatureSpecimenClob;
	// newly added
	private String collectionMode;
	private String chequeBankCode;
	private String chequeRef;
	private Date chequeDate;
	private String approvalNo;
	
	private BigDecimal documentId;
	private BigDecimal companyCode;

	private CurrencyWiseDenomination exCurrencyWiseDenomination;
	private List<PurposeOfTransaction> exPurposeOfTransaction= new ArrayList<PurposeOfTransaction>();
	private List<SourceOfIncome> exSourceOfIncome = new ArrayList<SourceOfIncome>();


	@Id	
	@Column(name = "RECEIPT_ID")
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	
	@Column(name = "COUNTRY_ID")
	public BigDecimal getFsCountryMaster() {
		return fsCountryMaster;
	}
	public void setFsCountryMaster(BigDecimal fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	
	@Column(name = "LOCAL_CURRENCY_ID")
	public BigDecimal getLocalFsCountryMaster() {
		return localFsCountryMaster;
	}
	public void setLocalFsCountryMaster(BigDecimal localFsCountryMaster) {
		this.localFsCountryMaster = localFsCountryMaster;
	}

	
	@Column(name = "FOREIGN_CURRENCY_ID")
	public BigDecimal getForeignFsCountryMaster() {
		return foreignFsCountryMaster;
	}
	public void setForeignFsCountryMaster(BigDecimal foreignFsCountryMaster) {
		this.foreignFsCountryMaster = foreignFsCountryMaster;
	}

	
	@Column(name = "COMPANY_ID")
	public BigDecimal getFsCompanyMaster() {
		return fsCompanyMaster;
	}
	public void setFsCompanyMaster(BigDecimal fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getFsCustomer() {
		return fsCustomer;
	}
	public void setFsCustomer(BigDecimal fsCustomer) {
		this.fsCustomer = fsCustomer;
	}

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

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	/*
	@Column(name = "BANK_BRANCH_ID")
	@Column(name = "BRANCH_ID")
	public BankBranch getFsbankBranch() {
		return fsbankBranch;
	}
	public void setFsbankBranch(BankBranch fsbankBranch) {
		this.fsbankBranch = fsbankBranch;
	}
	 */

	
	/*@Column(name = "BANK_BRANCH_ID")*/
	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranch() {
		return countryBranch;
	}
	public void setCountryBranch(BigDecimal countryBranch) {
		this.countryBranch = countryBranch;
	}

	
	@Column(name = "PURPOSE_ID")
	public BigDecimal getPurposeOfTransaction() {
		return purposeOfTransaction;
	}
	public void setPurposeOfTransaction(BigDecimal purposeOfTransaction) {
		this.purposeOfTransaction = purposeOfTransaction;
	}

	
	@Column(name = "SOURCE_ID")
	public BigDecimal getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(BigDecimal sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	@Column(name =  "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name =  "CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name =  "TRANSACTION_TYPE")
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name =  "FOREIGN_TRNX_AMOUNT")
	public BigDecimal getForignTrnxAmount() {
		return forignTrnxAmount;
	}
	public void setForignTrnxAmount(BigDecimal forignTrnxAmount) {
		this.forignTrnxAmount = forignTrnxAmount;
	}

	@Column(name = "LOCAL_TRNX_AMOUNT")
	public BigDecimal getLocalTrnxAmount() {
		return localTrnxAmount;
	}
	public void setLocalTrnxAmount(BigDecimal localTrnxAmount) {
		this.localTrnxAmount = localTrnxAmount;
	}

	@Column(name = "TRANSACTION_ACTUAL_RATE")
	public BigDecimal getTransactionActualRate() {
		return transactionActualRate;
	}
	public void setTransactionActualRate(BigDecimal transactionActualRate) {
		this.transactionActualRate = transactionActualRate;
	}

	@Column(name = "DOCUMENT_STATUS")
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	@Column(name ="LOCAL_NET_AMOUNT")
	public BigDecimal getLocalNetAmount() {
		return localNetAmount;
	}
	public void setLocalNetAmount(BigDecimal localNetAmount) {
		this.localNetAmount = localNetAmount;
	}

	@Column(name = "GENERAL_LEDGER_ERR")
	public String getGeneralLegerErr() {
		return generalLegerErr;
	}
	public void setGeneralLegerErr(String generalLegerErr) {
		this.generalLegerErr = generalLegerErr;
	}

	@Column(name = "GENERAL_LEDGER_DATE")
	public Date getGeneralLegerDate() {
		return generalLegerDate;
	}
	public void setGeneralLegerDate(Date generalLegerDate) {
		this.generalLegerDate = generalLegerDate;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "ACCOUNT_MMYYYY")
	public Date getAccountMMYYYY() {
		return accountMMYYYY;
	}
	public void setAccountMMYYYY(Date accountMMYYYY) {
		this.accountMMYYYY = accountMMYYYY;
	}
	@Column (name = "CUSTOMER_SIGN")
	public BLOB getCustomerSign() {
		return customerSign;
	}
	public void setCustomerSign(BLOB customerSign) {
		this.customerSign = customerSign;
	}

	@Column (name = "COLLECTION_DOC_CODE")
	public BigDecimal getColDocCode() {
		return colDocCode;
	}
	public void setColDocCode(BigDecimal colDocCode) {
		this.colDocCode = colDocCode;
	}

	@Column (name = "COLLECTION_DOC_FINANCE_YEAR")
	public BigDecimal getColDocFyr() {
		return colDocFyr;
	}
	public void setColDocFyr(BigDecimal colDocFyr) {
		this.colDocFyr = colDocFyr;
	}

	@Column (name = "COLLECTION_DOCUMENT_NO")
	public BigDecimal getColDocNo() {
		return colDocNo;
	}
	public void setColDocNo(BigDecimal colDocNo) {
		this.colDocNo = colDocNo;
	}

	@Column (name = "CDEP_DOCCOD")
	public BigDecimal getCdepDocCode() {
		return cdepDocCode;
	}
	public void setCdepDocCode(BigDecimal cdepDocCode) {
		this.cdepDocCode = cdepDocCode;
	}

	@Column (name = "CDEP_DOCFYR")
	public BigDecimal getCdepDocFyr() {
		return cdepDocFyr;
	}
	public void setCdepDocFyr(BigDecimal cdepDocFyr) {
		this.cdepDocFyr = cdepDocFyr;
	}

	@Column (name = "CDEP_DOCNO")
	public BigDecimal getCdepDocNo() {
		return cdepDocNo;
	}
	public void setCdepDocNo(BigDecimal cdepDocNo) {
		this.cdepDocNo = cdepDocNo;
	}

	@Column (name = "CDEP_DOCDAT")
	public Date getCdepDocDate() {
		return cdepDocDate;
	}
	public void setCdepDocDate(Date cdepDocDate) {
		this.cdepDocDate = cdepDocDate;
	}

	@Column (name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column (name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column (name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column (name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "APPLICATION_FINANCE_YEAR", precision = 4, scale = 0)
	public BigDecimal getApplicationFinanceYear() {
		return this.applicationFinanceYear;
	}
	public void setApplicationFinanceYear(BigDecimal applicationFinanceYear) {
		this.applicationFinanceYear = applicationFinanceYear;
	}

	@Column(name = "APPLICATION_DOCUMENT_NO", precision = 14, scale = 0)
	public BigDecimal getApplicationDocumentNo() {
		return this.applicationDocumentNo;
	}
	public void setApplicationDocumentNo(BigDecimal applicationDocumentNo) {
		this.applicationDocumentNo = applicationDocumentNo;
	}

	@Column(name ="SIGNATURE")
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Column(name="LOCAL_COMMISION_CURRENCY_ID")
	public BigDecimal getLocalCommisionCurrencyId() {
		return localCommisionCurrencyId;
	}
	public void setLocalCommisionCurrencyId(BigDecimal localCommisionCurrencyId) {
		this.localCommisionCurrencyId = localCommisionCurrencyId;
	}

	@Column(name="LOCAL_COMMISION_AMOUNT")
	public BigDecimal getLocalCommisionAmoumnt() {
		return localCommisionAmoumnt;
	}
	public void setLocalCommisionAmoumnt(BigDecimal localCommisionAmoumnt) {
		this.localCommisionAmoumnt = localCommisionAmoumnt;
	}

	@Column(name="LOCAL_CHARGE_CURRENCY_ID")
	public BigDecimal getLocalChargeCurrencyId() {
		return localChargeCurrencyId;
	}
	public void setLocalChargeCurrencyId(BigDecimal localChargeCurrencyId) {
		this.localChargeCurrencyId = localChargeCurrencyId;
	}

	@Column(name="LOCAL_CHARGE_AMOUNT")
	public BigDecimal getLocalChargeAmount() {
		return localChargeAmount;
	}
	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}

	@Column(name="LOCAL_DELIVERY_CURRENCY_ID")
	public BigDecimal getLocalDeliveryCurrencyId() {
		return localDeliveryCurrencyId;
	}
	public void setLocalDeliveryCurrencyId(BigDecimal localDeliveryCurrencyId) {
		this.localDeliveryCurrencyId = localDeliveryCurrencyId;
	}

	@Column(name="LOCAL_DELIVERY_AMOUNT")
	public BigDecimal getLocalDeliveryAmount() {
		return localDeliveryAmount;
	}
	public void setLocalDeliveryAmount(BigDecimal localDeliveryAmount) {
		this.localDeliveryAmount = localDeliveryAmount;
	}

	@Column(name="LOCAL_RATE_ADJ_CURRENCY_ID")
	public BigDecimal getLocalRateAdjCurrencyId() {
		return localRateAdjCurrencyId;
	}
	public void setLocalRateAdjCurrencyId(BigDecimal localRateAdjCurrencyId) {
		this.localRateAdjCurrencyId = localRateAdjCurrencyId;
	}

	@Column(name="LOCAL_RATE_ADJ_AMOUNT")
	public BigDecimal getLocalRateAmount() {
		return localRateAmount;
	}
	public void setLocalRateAmount(BigDecimal localRateAmount) {
		this.localRateAmount = localRateAmount;
	}

	@Column(name="LOCAL_OTH_ADJ_CURRENCY_ID")
	public BigDecimal getLocalOtherAdjCurrencyId() {
		return localOtherAdjCurrencyId;
	}
	public void setLocalOtherAdjCurrencyId(BigDecimal localOtherAdjCurrencyId) {
		this.localOtherAdjCurrencyId = localOtherAdjCurrencyId;
	}

	@Column(name="LOCAL_OTH_ADJ_AMOUNT")
	public BigDecimal getLocalOtherAdjAmount() {
		return localOtherAdjAmount;
	}
	public void setLocalOtherAdjAmount(BigDecimal localOtherAdjAmount) {
		this.localOtherAdjAmount = localOtherAdjAmount;
	}	

	@Column(name = "TRANSFER_REFERENCE")
	public BigDecimal getTransferReference() {
		return transferReference;
	}
	public void setTransferReference(BigDecimal transferReference) {
		this.transferReference = transferReference;
	}
	
	@Column(name = "TRANSFER_FINANCE_YR")
	public BigDecimal getTransferFinanceYear() {
		return transferFinanceYear;
	}
	public void setTransferFinanceYear(BigDecimal transferFinanceYear) {
		this.transferFinanceYear = transferFinanceYear;
	}
	
	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	@Column(name = "WESTERN_UNION_MTCNO")
	public String getWesternUnoinMycNo() {
		return westernUnoinMycNo;
	}
	public void setWesternUnoinMycNo(String westernUnoinMycNo) {
		this.westernUnoinMycNo = westernUnoinMycNo;
	}
	
	@Column(name = "AVERAGE_RATE")
	public BigDecimal getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}

	@Column(name = "RECEIPT_TYPE")
	public String getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	@Column(name="DOCUMENT_FINANCE_YEAR_ID")
	public BigDecimal getDocumentFinanceYearId() {
		return documentFinanceYearId;
	}
	public void setDocumentFinanceYearId(BigDecimal documentFinanceYearId) {
		this.documentFinanceYearId = documentFinanceYearId;
	}
	
	@Column(name="SIGNATURE_SPECIMEN_CLOB")
	public Clob getSignatureSpecimenClob() {
		return signatureSpecimenClob;
	}
	public void setSignatureSpecimenClob(Clob signatureSpecimenClob) {
		this.signatureSpecimenClob = signatureSpecimenClob;
	}
	
	@Column(name="SOURCE_OF_INCOME_ID")
	public BigDecimal getSourceofIncomeId() {
		return sourceofIncomeId;
	}
	public void setSourceofIncomeId(BigDecimal sourceofIncomeId) {
		this.sourceofIncomeId = sourceofIncomeId;
	}
	
	@Column(name="COLLECTION_MODE")
	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}
	
	@Column(name="CHEQUE_BANK_CODE")
	public String getChequeBankCode() {
		return chequeBankCode;
	}
	public void setChequeBankCode(String chequeBankCode) {
		this.chequeBankCode = chequeBankCode;
	}
	
	@Column(name="CHEQUE_REFERENCE")
	public String getChequeRef() {
		return chequeRef;
	}
	public void setChequeRef(String chequeRef) {
		this.chequeRef = chequeRef;
	}
	
	@Column(name="CHEQUE_DATE")
	public Date getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}
	
	@Column(name="APPROVAL_NO")
	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	
	@Column(name="DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	
	@Column(name="COMPANY_CODE")
	public BigDecimal getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}

}
