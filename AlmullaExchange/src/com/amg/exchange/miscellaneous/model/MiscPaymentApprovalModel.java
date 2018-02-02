package com.amg.exchange.miscellaneous.model;
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
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.registration.model.Customer;


@Entity
@Table(name = "EX_APPL_RECEIPT_PAYMENT")
public class MiscPaymentApprovalModel implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
		
	private BigDecimal receiptPaymentAproveId;	
	private CompanyMaster companyId;
	private CountryMaster countryId;
	private Customer customerId;
	private BigDecimal documentCode;
	private BigDecimal docFinanceYear;
	private BigDecimal documentNo;
	private BigDecimal branchId;
	private Date documentDate;
	private String customerName;
	private String transactionType;
	private BigDecimal foreignCurrencyId;
	private BigDecimal foreginTransactionAmount;
	private BigDecimal localCurrencyId;
	private BigDecimal localTransactionAmount;
	private BigDecimal actualTransactionRate;
	private BigDecimal localNetAmount;
	private String documentStatus;
	private String  ledgerGenErr;
	private Date    ledgerGenDate;
	private String isActive;
	private Date accountMonYY;
	private SourceOfIncome sourceId;
	private PurposeOfTransaction purposeId;
	private String customerSign;
	private BigDecimal collectionDocCode;
	private BigDecimal collectionDocFinanceYear;
	private BigDecimal collectionDocNo;
	private BigDecimal collectionDepositDoc;
	private BigDecimal collectionDepositDocFinanceYear;
	private BigDecimal collectionDepositDocNo;
	private Date collectionDepositDocDate;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String remarks;
	private String applicationStatus;
	
	
	public MiscPaymentApprovalModel( BigDecimal receiptPaymentAproveId, CompanyMaster companyId, CountryMaster countryId, Customer customerId,BigDecimal documentCode,
			 BigDecimal docFinanceYear, BigDecimal documentNo, BigDecimal branchId, Date documentDate, String customerName,	 String transactionType,
			 BigDecimal foreignCurrencyId, BigDecimal foreginTransactionAmount, BigDecimal localCurrencyId, BigDecimal localTransactionAmount,
			 BigDecimal actualTransactionRate, BigDecimal localNetAmount,String documentStatus, String  ledgerGenErr, Date ledgerGenDate,
			 String isActive, Date accountMonYY, SourceOfIncome sourceId, PurposeOfTransaction purposeId, String customerSign,BigDecimal collectionDocCode,
			 BigDecimal collectionDocFinanceYear, BigDecimal collectionDocNo, BigDecimal collectionDepositDoc, BigDecimal collectionDepositDocFinanceYear,
			 BigDecimal collectionDepositDocNo, Date collectionDepositDocDate, Date createdDate,String createdBy,Date modifiedDate, String modifiedBy,
			 String remarks,String applicationStatus) {
		
		super();
		
		
		 this.receiptPaymentAproveId=receiptPaymentAproveId;		
		 this.companyId=companyId;
		 this.countryId=countryId;
		 this.customerId=customerId;
		 this.documentCode=documentCode;
		 this.docFinanceYear=documentCode;
		 this.documentNo=documentNo;
		 this.branchId=branchId;
		 this.documentDate=documentDate;
		 this.customerName=customerName;
		 this.transactionType=transactionType;
		 this.foreignCurrencyId=foreignCurrencyId;
		 this.foreginTransactionAmount=foreginTransactionAmount;
		 this.localCurrencyId=localCurrencyId;
		 this.localTransactionAmount=localTransactionAmount;
		 this.actualTransactionRate=actualTransactionRate;
		 this.localNetAmount=localNetAmount;
		 this.documentStatus=documentStatus;
		 this.ledgerGenErr=ledgerGenErr;
		 this.ledgerGenDate=ledgerGenDate;
		 this.isActive=isActive;
		 this.accountMonYY=accountMonYY;
		 this.sourceId=sourceId;
		 this.purposeId=purposeId;
		 this.customerSign=customerSign;
		 this.collectionDocCode=collectionDocCode;
		 this.collectionDocFinanceYear=collectionDocFinanceYear;
		 this.collectionDocNo=collectionDocNo;
		 this.collectionDepositDoc=collectionDepositDoc;
		 this.collectionDepositDocFinanceYear=collectionDepositDocFinanceYear;
		 this.collectionDepositDocNo=collectionDepositDocNo;
		 this.collectionDepositDocDate=collectionDepositDocDate;
		 this.createdDate=createdDate;
		 this.createdBy=createdBy;
		 this.modifiedDate=modifiedDate;
		 this.modifiedBy=modifiedBy;
		 this.remarks=remarks;
		 this.remarks=applicationStatus;
		
	}

		
	@Id
	@GeneratedValue(generator="ex_receipt_payment_app_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_receipt_payment_app_seq",sequenceName="EX_RECEIPT_PAYMENT_APP_SEQ",allocationSize=1)
	@Column(name = "RECEIPT_PAYMENT_APP_ID")
	public BigDecimal getReceiptPaymentAproveId() {
		return receiptPaymentAproveId;
	}

	public void setReceiptPaymentAproveId(BigDecimal receiptPaymentAproveId) {
		this.receiptPaymentAproveId = receiptPaymentAproveId;
	}
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COMPANY_ID")
	public CompanyMaster getCompanyId() {
		return companyId;
	}

	public void setCompanyId(CompanyMaster companyId) {
		this.companyId = companyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COUNTRY_ID")
	
	public CountryMaster getCountryId() {
		return countryId;
	}

	public void setCountryId(CountryMaster countryId) {
		this.countryId = countryId;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID")
	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	
	@Column(name="DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	
	@Column(name="DOCUMENT_FINANCE_YR")
	public BigDecimal getDocFinanceYear() {
		return docFinanceYear;
	}

	public void setDocFinanceYear(BigDecimal docFinanceYear) {
		this.docFinanceYear = docFinanceYear;
	}

	
	
	@Column(name="DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name="BRANCH_ID")
	public BigDecimal getBranchId() {
		return branchId;
	}
		
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	
	@Column(name="DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name="CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	
	@Column(name="TRANSACTION_TYPE")
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	
	@Column(name="FOREIGN_CURRENCY_ID")
	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}

	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}

	@Column(name="FOREIGN_TRNX_AMOUNT")
	public BigDecimal getForeginTransactionAmount() {
		return foreginTransactionAmount;
	}

	public void setForeginTransactionAmount(BigDecimal foreginTransactionAmount) {
		this.foreginTransactionAmount = foreginTransactionAmount;
	}

	@Column(name="LOCAL_CURRENCY_ID")
	public BigDecimal getLocalCurrencyId() {
		return localCurrencyId;
	}

	public void setLocalCurrencyId(BigDecimal localCurrencyId) {
		this.localCurrencyId = localCurrencyId;
	}

	@Column(name="LOCAL_TRNX_AMOUNT")
	public BigDecimal getLocalTransactionAmount() {
		return localTransactionAmount;
	}

	public void setLocalTransactionAmount(BigDecimal localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}

	@Column(name="TRANSACTION_ACTUAL_RATE")
	public BigDecimal getActualTransactionRate() {
		return actualTransactionRate;
	}

	public void setActualTransactionRate(BigDecimal actualTransactionRate) {
		this.actualTransactionRate = actualTransactionRate;
	}

	@Column(name="LOCAL_NET_AMOUNT")
	public BigDecimal getLocalNetAmount() {
		return localNetAmount;
	}

	public void setLocalNetAmount(BigDecimal localNetAmount) {
		this.localNetAmount = localNetAmount;
	}

	@Column(name="DOCUMENT_STATUS")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	@Column(name="GENERAL_LEDGER_ERR")
	public String getLedgerGenErr() {
		return ledgerGenErr;
	}

	public void setLedgerGenErr(String ledgerGenErr) {
		this.ledgerGenErr = ledgerGenErr;
	}

	@Column(name="GENERAL_LEDGER_DATE")
	public Date getLedgerGenDate() {
		return ledgerGenDate;
	}

	public void setLedgerGenDate(Date ledgerGenDate) {
		this.ledgerGenDate = ledgerGenDate;
	}

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name="ACCOUNT_MMYYYY")
	public Date getAccountMonYY() {
		return accountMonYY;
	}

	public void setAccountMonYY(Date accountMonYY) {
		this.accountMonYY = accountMonYY;
	}

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SOURCE_ID")
	public SourceOfIncome getSourceId() {
		return sourceId;
	}

	public void setSourceId(SourceOfIncome sourceId) {
		this.sourceId = sourceId;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PURPOSE_ID")
	public PurposeOfTransaction getPurposeId() {
		return purposeId;
	}

	
	public void setPurposeId(PurposeOfTransaction purposeId) {
		this.purposeId = purposeId;
	}
	

	@Column(name="CUSTOMER_SIGN")
	public String getCustomerSign() {
		return customerSign;
	}

	public void setCustomerSign(String customerSign) {
		this.customerSign = customerSign;
	}

	@Column(name="COL_DOCCOD")
	public BigDecimal getCollectionDocCode() {
		return collectionDocCode;
	}

	public void setCollectionDocCode(BigDecimal collectionDocCode) {
		this.collectionDocCode = collectionDocCode;
	}

	@Column(name="COL_DOCFYR")
	public BigDecimal getCollectionDocFinanceYear() {
		return collectionDocFinanceYear;
	}

	public void setCollectionDocFinanceYear(BigDecimal collectionDocFinanceYear) {
		this.collectionDocFinanceYear = collectionDocFinanceYear;
	}

	@Column(name="COL_DOCNO")
	public BigDecimal getCollectionDocNo() {
		return collectionDocNo;
	}

	public void setCollectionDocNo(BigDecimal collectionDocNo) {
		this.collectionDocNo = collectionDocNo;
	}

	@Column(name="CDEP_DOCCOD")
	public BigDecimal getCollectionDepositDoc() {
		return collectionDepositDoc;
	}

	public void setCollectionDepositDoc(BigDecimal collectionDepositDoc) {
		this.collectionDepositDoc = collectionDepositDoc;
	}

	@Column(name="CDEP_DOCFYR")
	public BigDecimal getCollectionDepositDocFinanceYear() {
		return collectionDepositDocFinanceYear;
	}

	public void setCollectionDepositDocFinanceYear(
			BigDecimal collectionDepositDocFinanceYear) {
		this.collectionDepositDocFinanceYear = collectionDepositDocFinanceYear;
	}

	@Column(name="CDEP_DOCNO")
	public BigDecimal getCollectionDepositDocNo() {
		return collectionDepositDocNo;
	}

	public void setCollectionDepositDocNo(BigDecimal collectionDepositDocNo) {
		this.collectionDepositDocNo = collectionDepositDocNo;
	}

	@Column(name="CDEP_DOCDAT")
	public Date getCollectionDepositDocDate() {
		return collectionDepositDocDate;
	}

	public void setCollectionDepositDocDate(Date collectionDepositDocDate) {
		this.collectionDepositDocDate = collectionDepositDocDate;
	}

	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name="REMARKS")
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
