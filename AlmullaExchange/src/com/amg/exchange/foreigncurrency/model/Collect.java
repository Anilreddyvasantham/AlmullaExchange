package com.amg.exchange.foreigncurrency.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.CurrencyMaster;

@Entity
@Table(name = "EX_COLLECTION")
public class Collect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Collect() {
	}

	private BigDecimal collectionId;
	private CompanyMaster fsCompanyMaster;
	private BigDecimal applicationCountryId;
	private Customer fsCustomer;
	private BigDecimal documentCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private BigDecimal documentId;
	private Date collectDate;
	private CurrencyMaster exCurrencyMaster;
	private BigDecimal paidAmount;
	private BigDecimal refoundAmount;
	private BigDecimal netAmount;
	private String isActive;
	private Date accountMMYYYY;
	//private BigDecimal ammountTranxExch;
	//private BigDecimal totForeignCurrencyAmt;
	private Date generalLegerDate;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private CountryBranch exBankBranch;
	private String receiptType;
	private BigDecimal companyCode;
	private BigDecimal locCode;
	
	private List<ForeignCurrencyAdjust> foreinCurrenecyAdjust;
	private String cashDeclarationIndicator;
	private String totalAmountDeclarationIndicator;
	
	
	
	
	/**
	 * @param collectionId
	 * @param fsCompanyMaster
	 * @param fsCustomer
	 * @param documentCode
	 * @param documentFinanceYear
	 * @param documentNo
	 * @param collectDate
	 * @param exCurrencyMaster
	 * @param paidAmount
	 * @param refoundAmount
	 * @param netAmount
	 * @param isActive
	 * @param accountMMYYYY
	 * @param ammountTranxExch
	 * @param totForeignCurrencyAmt
	 * @param generalLegerDate
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 * @param foreinCurrenecyAdjust
	 */
	public Collect(BigDecimal collectionId, CompanyMaster fsCompanyMaster,
			BigDecimal applicationCountryId, Customer fsCustomer,
			BigDecimal documentCode, BigDecimal documentFinanceYear,
			BigDecimal documentNo, Date collectDate,
			CurrencyMaster exCurrencyMaster, BigDecimal paidAmount,
			BigDecimal refoundAmount, BigDecimal netAmount, String isActive,
			Date accountMMYYYY, Date generalLegerDate, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			CountryBranch exBankBranch,
			List<ForeignCurrencyAdjust> foreinCurrenecyAdjust,BigDecimal locCode) {
		super();
		this.collectionId = collectionId;
		this.fsCompanyMaster = fsCompanyMaster;
		this.applicationCountryId = applicationCountryId;
		this.fsCustomer = fsCustomer;
		this.documentCode = documentCode;
		this.documentFinanceYear = documentFinanceYear;
		this.documentNo = documentNo;
		this.collectDate = collectDate;
		this.exCurrencyMaster = exCurrencyMaster;
		this.paidAmount = paidAmount;
		this.refoundAmount = refoundAmount;
		this.netAmount = netAmount;
		this.isActive = isActive;
		this.accountMMYYYY = accountMMYYYY;
		this.generalLegerDate = generalLegerDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.exBankBranch = exBankBranch;
		this.foreinCurrenecyAdjust = foreinCurrenecyAdjust;
		this.locCode=locCode;
	}

	@Id
	@GeneratedValue(generator="ex_collection_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_collection_seq",sequenceName="EX_COLLECTION_SEQ",allocationSize=1)
	@Column(name = "CASH_COLLECTION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(BigDecimal collectionId) {
		this.collectionId = collectionId;
	}
	
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
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
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}
	public void setFsCustomer(Customer fsCustomer) {
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

	@Column(name = "COLLECT_DATE")
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrencyMaster() {
		return exCurrencyMaster;
	}
	public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		this.exCurrencyMaster = exCurrencyMaster;
	}

	@Column(name = "PAID_AMOUNT")
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	@Column(name = "REFUNDED_AMOUNT")
	public BigDecimal getRefoundAmount() {
		return refoundAmount;
	}
	public void setRefoundAmount(BigDecimal refoundAmount) {
		this.refoundAmount = refoundAmount;
	}

	@Column(name = "NET_AMOUNT")
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
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

	/*@Column(name = "AMOUNT_TRANX_EXCH")
	public BigDecimal getAmmountTranxExch() {
		return ammountTranxExch;
	}

	public void setAmmountTranxExch(BigDecimal ammountTranxExch) {
		this.ammountTranxExch = ammountTranxExch;
	}

	@Column(name = "TOT_FOEIGN_CURRN_AMT")
	public BigDecimal getTotForeignCurrencyAmt() {
		return totForeignCurrencyAmt;
	}

	public void setTotForeignCurrencyAmt(BigDecimal totForeignCurrencyAmt) {
		this.totForeignCurrencyAmt = totForeignCurrencyAmt;
	}*/

	@Column(name = "GENERAL_LEDGER_DATE")
	public Date getGeneralLegerDate() {
		return generalLegerDate;
	}
	public void setGeneralLegerDate(Date generalLegerDate) {
		this.generalLegerDate = generalLegerDate;
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

	@OneToMany(fetch = FetchType.LAZY,mappedBy="collect")
	public List<ForeignCurrencyAdjust> getForeinCurrenecyAdjust() {
		return foreinCurrenecyAdjust;
	}
	public void setForeinCurrenecyAdjust(List<ForeignCurrencyAdjust> foreinCurrenecyAdjust) {
		this.foreinCurrenecyAdjust = foreinCurrenecyAdjust;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getExBankBranch() {
		return exBankBranch;
	}
	public void setExBankBranch(CountryBranch exBankBranch) {
		this.exBankBranch = exBankBranch;
	}

	@Column(name="RECEIPT_TYPE")
	public String getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
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
	
	
	@Column(name = "DECL_PRINT_2KCASH")
	public String getCashDeclarationIndicator() {
		return cashDeclarationIndicator;
	}

	public void setCashDeclarationIndicator(String cashDeclarationIndicator) {
		this.cashDeclarationIndicator = cashDeclarationIndicator;
	}
	
	@Column(name = "DECL_PRINT_TOTALNET")
	public String getTotalAmountDeclarationIndicator() {
		return totalAmountDeclarationIndicator;
	}

	public void setTotalAmountDeclarationIndicator(
			String totalAmountDeclarationIndicator) {
		this.totalAmountDeclarationIndicator = totalAmountDeclarationIndicator;
	}
	
}
