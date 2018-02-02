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
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name ="EX_COLLECT_DETAIL")
public class CollectDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private BigDecimal cashCollectionDetailId;
	private Collect cashCollectionId;
	private Customer fsCustomer;
	private CountryMaster fsCountryMaster;
	private CompanyMaster fsCompanyMaster;
	private BigDecimal documentCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private BigDecimal documentId;
	private CountryBranch exBankBranch;
	private Date documentDate;
	private BigDecimal documentLineNo;
	private String collectionMode;
	private String chequeBankRef;
	private String chequeRef;
	private Date chequeDate;
	private String approvalNo;
	private CurrencyMaster exCurrencyMaster;
	private BigDecimal collAmt;
	private String isCashDeposit;
	private BigDecimal cdepDocCode;
	private BigDecimal cdepDocFyr;
	private BigDecimal cdepDocNo;
	private Date cdepDocDate;
	private String isActive;
	private BigDecimal voucherYear;
	private BigDecimal voucherNo;
	private String debitCard;
	private String dbCardName;
	private String authby;
	private Date authdate;
	private Date acyymm;
	private String payId;
	private String transId;
	private String refId;
	//private BigDecimal dealFinYear;
	//private BigDecimal dealId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal paymentModeId;
	//Added on 07/01/2016
	private String knetReceipt;
	private String knetReceiptDateTime;
	private BigDecimal companyCode;
	private BigDecimal locCode;

	/**
	 * @param cashCollectionDetailId
	 * @param cashCollectionId
	 * @param fsCustomer
	 * @param fsCountryMaster
	 * @param fsCompanyMaster
	 * @param documentCode
	 * @param documentFinanceYear
	 * @param documentNo
	 * @param exBankBranch
	 * @param documentDate
	 * @param documentLineNo
	 * @param collectionMode
	 * @param chequeBankRef
	 * @param chequeRef
	 * @param chequeDate
	 * @param approvalNo
	 * @param exCurrencyMaster
	 * @param collAmt
	 * @param isCashDeposit
	 * @param cdepDocCode
	 * @param cdepDocFyr
	 * @param cdepDocNo
	 * @param cdepDocDate
	 * @param isActive
	 * @param voucherYear
	 * @param voucherNo
	 * @param debitCard
	 * @param dbCardName
	 * @param authby
	 * @param authdate
	 * @param acyymm
	 * @param payId
	 * @param transId
	 * @param refId
	 * @param dealFinYear
	 * @param dealId
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 */
	public CollectDetail(BigDecimal cashCollectionDetailId,
			Collect cashCollectionId, Customer fsCustomer,
			CountryMaster fsCountryMaster, CompanyMaster fsCompanyMaster,
			BigDecimal documentCode, BigDecimal documentFinanceYear,
			BigDecimal documentNo, CountryBranch exBankBranch, Date documentDate,
			BigDecimal documentLineNo, String collectionMode,
			String chequeBankRef, String chequeRef, Date chequeDate,
			String approvalNo, CurrencyMaster exCurrencyMaster,
			BigDecimal collAmt, String isCashDeposit, BigDecimal cdepDocCode,
			BigDecimal cdepDocFyr, BigDecimal cdepDocNo, Date cdepDocDate,
			String isActive, BigDecimal voucherYear, BigDecimal voucherNo,
			String debitCard, String dbCardName, String authby, Date authdate,
			Date acyymm, String payId, String transId, String refId,
			BigDecimal dealFinYear, BigDecimal dealId, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,BigDecimal locCode) {
		this.cashCollectionDetailId = cashCollectionDetailId;
		this.cashCollectionId = cashCollectionId;
		this.fsCustomer = fsCustomer;
		this.fsCountryMaster = fsCountryMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.documentCode = documentCode;
		this.documentFinanceYear = documentFinanceYear;
		this.documentNo = documentNo;
		this.exBankBranch = exBankBranch;
		this.documentDate = documentDate;
		this.documentLineNo = documentLineNo;
		this.collectionMode = collectionMode;
		this.chequeBankRef = chequeBankRef;
		this.chequeRef = chequeRef;
		this.chequeDate = chequeDate;
		this.approvalNo = approvalNo;
		this.exCurrencyMaster = exCurrencyMaster;
		this.collAmt = collAmt;
		this.isCashDeposit = isCashDeposit;
		this.cdepDocCode = cdepDocCode;
		this.cdepDocFyr = cdepDocFyr;
		this.cdepDocNo = cdepDocNo;
		this.cdepDocDate = cdepDocDate;
		this.isActive = isActive;
		this.voucherYear = voucherYear;
		this.voucherNo = voucherNo;
		this.debitCard = debitCard;
		this.dbCardName = dbCardName;
		this.authby = authby;
		this.authdate = authdate;
		this.acyymm = acyymm;
		this.payId = payId;
		this.transId = transId;
		this.refId = refId;
		//this.dealFinYear = dealFinYear;
		//this.dealId = dealId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.locCode=locCode;
	}
	/**
	 * 
	 */
	public CollectDetail() {

	}
	
	@Id
	@GeneratedValue(generator="ex_collect_details_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_collect_details_seq",sequenceName="EX_COLLECT_DETAILS_SEQ",allocationSize=1)
	@Column(name = "CASH_COLLECTION_DETAIL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCashCollectionDetailId() {
		return cashCollectionDetailId;
	}
	public void setCashCollectionDetailId(BigDecimal cashCollectionDetailId) {
		this.cashCollectionDetailId = cashCollectionDetailId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CASH_COLLECTION_ID")
	public Collect getCashCollectionId() {
		return cashCollectionId;
	}
	public void setCashCollectionId(Collect cashCollectionId) {
		this.cashCollectionId = cashCollectionId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}
	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return fsCountryMaster;
	}
	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return fsCompanyMaster;
	}
	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	/*@JoinColumn(name = "BANK_BRANCH_ID")*/
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getExBankBranch() {
		return exBankBranch;
	}
	public void setExBankBranch(CountryBranch exBankBranch) {
		this.exBankBranch = exBankBranch;
	}
	
	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name = "DOCUMENT_LINE_NO")
	public BigDecimal getDocumentLineNo() {
		return documentLineNo;
	}
	public void setDocumentLineNo(BigDecimal documentLineNo) {
		this.documentLineNo = documentLineNo;
	}
	
	@Column(name = "COLLECTION_MODE")
	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}
	
	@Column(name = "CHEQUE_BANK_CODE")
	public String getChequeBankRef() {
		return chequeBankRef;
	}
	public void setChequeBankRef(String chequeBankRef) {
		this.chequeBankRef = chequeBankRef;
	}
	
	@Column(name = "CHEQUE_REFERENCE")
	public String getChequeRef() {
		return chequeRef;
	}
	public void setChequeRef(String chequeRef) {
		this.chequeRef = chequeRef;
	}
	
	@Column(name = "CHEQUE_DATE")
	public Date getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}
	
	@Column(name = "APPROVAL_NO")
	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name ="CURRENCY_ID")
	public CurrencyMaster getExCurrencyMaster() {
		return exCurrencyMaster;
	}
	public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		this.exCurrencyMaster = exCurrencyMaster;
	}
	
	@Column(name = "COLLAMT")
	public BigDecimal getCollAmt() {
		return collAmt;
	}
	public void setCollAmt(BigDecimal collAmt) {
		this.collAmt = collAmt;
	}
	
	@Column(name = "IS_CASH_DEPOSIT")
	public String getIsCashDeposit() {
		return isCashDeposit;
	}
	public void setIsCashDeposit(String isCashDeposit) {
		this.isCashDeposit = isCashDeposit;
	}
	
	@Column(name = "CDEP_DOCCOD")
	public BigDecimal getCdepDocCode() {
		return cdepDocCode;
	}
	public void setCdepDocCode(BigDecimal cdepDocCode) {
		this.cdepDocCode = cdepDocCode;
	}
	
	@Column(name = "CDEP_DOCFYR")
	public BigDecimal getCdepDocFyr() {
		return cdepDocFyr;
	}
	public void setCdepDocFyr(BigDecimal cdepDocFyr) {
		this.cdepDocFyr = cdepDocFyr;
	}
	
	@Column(name = "CDEP_DOCNO")
	public BigDecimal getCdepDocNo() {
		return cdepDocNo;
	}
	public void setCdepDocNo(BigDecimal cdepDocNo) {
		this.cdepDocNo = cdepDocNo;
	}
	
	@Column(name = "CDEP_DATE")
	public Date getCdepDocDate() {
		return cdepDocDate;
	}
	public void setCdepDocDate(Date cdepDocDate) {
		this.cdepDocDate = cdepDocDate;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "VOUCHER_YEAR")
	public BigDecimal getVoucherYear() {
		return voucherYear;
	}
	public void setVoucherYear(BigDecimal voucherYear) {
		this.voucherYear = voucherYear;
	}
	
	@Column(name = "VOUCHER_NO")
	public BigDecimal getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(BigDecimal voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	@Column(name = "DEBIT_CARD")
	public String getDebitCard() {
		return debitCard;
	}
	public void setDebitCard(String debitCard) {
		this.debitCard = debitCard;
	}
	
	@Column(name = "DB_CARD_NAME")
	public String getDbCardName() {
		return dbCardName;
	}
	public void setDbCardName(String dbCardName) {
		this.dbCardName = dbCardName;
	}
	
	@Column(name = "AUTHBY")
	public String getAuthby() {
		return authby;
	}
	public void setAuthby(String authby) {
		this.authby = authby;
	}
	
	@Column(name = "AUTHDAT")
	public Date getAuthdate() {
		return authdate;
	}
	public void setAuthdate(Date authdate) {
		this.authdate = authdate;
	}
	
	@Column(name = "ACYYMM")
	public Date getAcyymm() {
		return acyymm;
	}
	public void setAcyymm(Date acyymm) {
		this.acyymm = acyymm;
	}
	
	@Column(name = "PAY_ID")
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	
	@Column(name = "TRAN_ID")
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	
	@Column(name = "REF_ID")
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	/*@Column(name = "DEAL_FIN_YEAR")
	public BigDecimal getDealFinYear() {
		return dealFinYear;
	}

	public void setDealFinYear(BigDecimal dealFinYear) {
		this.dealFinYear = dealFinYear;
	}
	@Column(name = "DEAL_ID")
	public BigDecimal getDealId() {
		return dealId;
	}

	public void setDealId(BigDecimal dealId) {
		this.dealId = dealId;
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
	
	@Column(name="PAYMENT_MODE_ID")
	public BigDecimal getPaymentModeId() {
		return paymentModeId;
	}
	public void setPaymentModeId(BigDecimal paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	@Column(name="KNET_RECEIPT")
	public String getKnetReceipt() {
		return knetReceipt;
	}
	public void setKnetReceipt(String knetReceipt) {
		this.knetReceipt = knetReceipt;
	}
	
	@Column(name="KNET_RECEIPT_DATE_TIME")
	public String getKnetReceiptDateTime()
	{
		return knetReceiptDateTime;
	}
	public void setKnetReceiptDateTime(String knetReceiptDateTime)
	{
		this.knetReceiptDateTime = knetReceiptDateTime;
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
	
	@Column(name="LOCCOD")
	public BigDecimal getLocCode() {
		return locCode;
	}
	public void setLocCode(BigDecimal locCode) {
		this.locCode = locCode;
	}

	
}
