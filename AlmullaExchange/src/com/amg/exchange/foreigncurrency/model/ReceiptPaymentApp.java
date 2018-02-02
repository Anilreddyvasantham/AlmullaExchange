package com.amg.exchange.foreigncurrency.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import oracle.sql.BLOB;

@Entity
@Table(name = "EX_APPL_RECEIPT_PAYMENT")
public class ReceiptPaymentApp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal receiptId;
	private Date accountMMYYYY;
	private String applicationStatus;
	private BigDecimal branchId;
	private BigDecimal cdepDocCode;
	private Date cdepDocDate;
	private BigDecimal cdepDocFyr;
	private BigDecimal cdepDocNo;
	private BigDecimal colDocCode;	
	private BigDecimal colDocFyr;
	private BigDecimal colDocNo;
	private BigDecimal companyId;
	private BigDecimal countryId;
	private String createdBy;
	private Date createdDate;
	private BigDecimal customerId;
	private String customerName;
	private BLOB customerSign;
	private BigDecimal documentCode;
	private Date documentDate;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private String documentStatus;
	private BigDecimal foreignCurrencyId;
	private BigDecimal forignTrnxAmount;
	private Date generalLegerDate;
	private String generalLegerErr;
	private String isActive;
	private BigDecimal localCurrencyId;
	private BigDecimal localNetAmount;
	private BigDecimal localTrnxAmount;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal purposeofTransactionId;
	private String remarks;
	private BigDecimal sourceofIncomeId;
	private BigDecimal transactionActualRate;
	private BigDecimal transactionFinanceYear;
	private BigDecimal transactionRefNo;	
	private String transactionType;
	private BigDecimal sourceOfIncomeid;
	private Clob signatureSpacimenClob;
	private String transactionIPAddress;
	
	public ReceiptPaymentApp() {
		
	}
	
	/**
	 * @param receiptId
	 * @param fsCountryMaster
	 * @param localFsCountryMaster
	 * @param foreignFsCountryMaster
	 * @param fsCompanyMaster
	 * @param fsCustomer
	 * @param documentCode
	 * @param documentFinanceYear
	 * @param documentNo
	 * @param fsbankBranch
	 * @param purposeOfTransaction
	 * @param sourceOfIncome
	 * @param documentDate
	 * @param customerName
	 * @param transactionType
	 * @param forignTrnxAmount
	 * @param localTrnxAmount
	 * @param transactionActualRate
	 * @param localNetAmount
	 * @param documentStatus
	 * @param generalLegerErr
	 * @param generalLegerDate
	 * @param isActive
	 * @param accountMMYYYY
	 * @param customerSign
	 * @param colDocCode
	 * @param colDocFyr
	 * @param colDocNo
	 * @param cdepDocCode
	 * @param cdepDocFyr
	 * @param cdepDocNo
	 * @param cdepDocDate
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 * @param remarks
	 * @param exCurrencyWiseDenomination
	 * @param exPurposeOfTransaction
	 * @param exSourceOfIncome
	 */


	@Id
	@GeneratedValue(generator="ex_receipt_payment_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_receipt_payment_seq",sequenceName="EX_RECEIPT_PAYMENT_SEQ",allocationSize=1)
	@Column(name = "RECEIPT_PAYMENT_APP_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	
	@Column(name = "COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	@Column(name = "LOCAL_CURRENCY_ID")
	public BigDecimal getLocalCurrencyId() {
		return localCurrencyId;
	}
	public void setLocalCurrencyId(BigDecimal localCurrencyId) {
		this.localCurrencyId = localCurrencyId;
	}

	@Column(name = "FOREIGN_CURRENCY_ID")
	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}
	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
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
	
	@Column(name="COUNTRY_BRANCH_ID")
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	
	@Column(name = "PURPOSE_ID")
	public BigDecimal getPurposeofTransactionId() {
		return purposeofTransactionId;
	}
	public void setPurposeofTransactionId(BigDecimal purposeofTransactionId) {
		this.purposeofTransactionId = purposeofTransactionId;
	}
	
	@Column(name = "SOURCE_ID")
	public BigDecimal getSourceofIncomeId() {
		return sourceofIncomeId;
	}
	public void setSourceofIncomeId(BigDecimal sourceofIncomeId) {
		this.sourceofIncomeId = sourceofIncomeId;
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
	
	@Column(name = "APPLICATION_STATUS")
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	@Column(name = "TRANSACTION_REF_NO")
	public BigDecimal getTransactionRefNo() {
		return transactionRefNo;
	}
	public void setTransactionRefNo(BigDecimal transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}

	@Column(name = "TRANSACTION_FINANCE_YEAR")
	public BigDecimal getTransactionFinanceYear() {
		return transactionFinanceYear;
	}
	public void setTransactionFinanceYear(BigDecimal transactionFinanceYear) {
		this.transactionFinanceYear = transactionFinanceYear;
	}

	@Column(name = "SIGNATURE_SPECIMEN_CLOB")
	public Clob getSignatureSpacimenClob() {
		return signatureSpacimenClob;
	}
	public void setSignatureSpacimenClob(Clob signatureSpacimenClob) {
		this.signatureSpacimenClob = signatureSpacimenClob;
	}

	@Column(name = "SOURCE_OF_INCOME_ID")
	public BigDecimal getSourceOfIncomeid() {
		return sourceOfIncomeid;
	}
	public void setSourceOfIncomeid(BigDecimal sourceOfIncomeid) {
		this.sourceOfIncomeid = sourceOfIncomeid;
	}

	@Column(name = "TRANSACTION_IPADDRESS")
	public String getTransactionIPAddress() {
		return transactionIPAddress;
	}
	public void setTransactionIPAddress(String transactionIPAddress) {
		this.transactionIPAddress = transactionIPAddress;
	}
}