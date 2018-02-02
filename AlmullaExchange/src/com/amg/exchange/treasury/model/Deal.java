package com.amg.exchange.treasury.model;

// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.Customer;

/**
 * ExDeal Created by Chennai ODC
 */
@Entity
@Table(name = "EX_DEAL", uniqueConstraints = @UniqueConstraint(columnNames = {
		"COUNTRY_ID", "COMPANY_ID", "DOCUMENT_FIN_YEAR", "DOCUMENT_CODE",
		"DOCUMENT_NUMBER" }))
public class Deal implements java.io.Serializable {

	/**
	 * Generated Serial UID.
	 */
	private static final long serialVersionUID = -1843625079852965346L;
	private BigDecimal dealId;
	private CurrencyMaster exCurrencyMaster;
	private BankBranch exBankBranch;
	private AccountBalance exAccountBalance;
	private BankMaster exBankMaster;
	private CompanyMaster fsCompanyMaster;
	private Customer fsCustomer;
	private OutrightSwapMaster exOutrightSwapMaster;
	private CountryMaster fsCountryMaster;
	private Short documentFinYear;
	private Short documentCode;
	private BigDecimal documentNumber;
	private Date documentDate;
	private BigDecimal dealRefNumber;
	private Date dealRefDate;
	private String dealer;
	private String isspecialDeal;
	private String concludedBy;
	private BigDecimal dealWithBank;
	private String reason;
	private String rejectedReason;
	private BigDecimal dealTypeMasterId;
	private BigDecimal conclusionType;
	private BigDecimal localExchRate;
	private BigDecimal foreignExchRate;
	private String mltdiv;
	private String reasonCode;
	private Date approvedDate;
	private String remark;
	private String description;
	private String isSupplerDeal;
	private String isactive;
	private BigDecimal fileNo;
	private Date fileDate;
	private Date validDate;
	private BigDecimal subLedgerNo;
	private String approvedBy;
	private Date accountYear;
	private String subLedgerInd;
	private BigDecimal localAmount;
	private BigDecimal foreginCurrencyBal;
	private Date glDate;
	private String trggerInd;
	private String createPaymentVoucher;
	private String dealInd;
	private Byte dealRef;
	private Byte dealCancel;
	private Short dealCancelYear;
	private Byte dealCancelDoc;
	private BigDecimal dealCancelNumber;
	private Byte paymentVoucherRef;
	private Short paymentVoucherYear;
	private Byte paymentVoucherDoc;
	private Byte paymentVoucherNumber;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Set<DealSales> exDealSaleses = new HashSet<DealSales>(0);
	private Set<DealSupplier> exDealSuppliers = new HashSet<DealSupplier>(0);

	public Deal() {
	}

	public Deal(BigDecimal dealId) {
		this.dealId = dealId;
	}

	public Deal(BigDecimal dealId, CurrencyMaster exCurrencyMaster,
			BankBranch exBankBranch, AccountBalance exAccountBalance,
			BankMaster exBankMaster, CompanyMaster fsCompanyMaster,
			Customer fsCustomer, OutrightSwapMaster exOutrightSwapMaster,
			CountryMaster fsCountryMaster, Short documentFinYear,
			Short documentCode, BigDecimal documentNumber,
			Date documentDate, BigDecimal dealRefNumber,
			Date dealRefDate, String dealer, String isspecialDeal,
			String concludedBy, BigDecimal dealWithBank, String reason,
			String rejectedReason, BigDecimal dealTypeMasterId,
			BigDecimal conclusionType, BigDecimal localExchRate,
			BigDecimal foreignExchRate, String mltdiv, String reasonCode,
			Date approvedDate, String remark, String description,
			String isSupplerDeal, String isactive, BigDecimal fileNo,
			Date fileDate, Date validDate,
			BigDecimal subLedgerNo, String approvedBy,
			Date accountYear, String subLedgerInd,
			BigDecimal localAmount, BigDecimal foreginCurrencyBal,
			Date glDate, String trggerInd, String createPaymentVoucher,
			String dealInd, Byte dealRef, Byte dealCancel,
			Short dealCancelYear, Byte dealCancelDoc,
			BigDecimal dealCancelNumber, Byte paymentVoucherRef,
			Short paymentVoucherYear, Byte paymentVoucherDoc,
			Byte paymentVoucherNumber, String createdBy,
			Date createdDate, String modifiedBy,
			Date modifiedDate, Set<DealSales> exDealSaleses,
			Set<DealSupplier> exDealSuppliers) {
		this.dealId = dealId;
		this.exCurrencyMaster = exCurrencyMaster;
		this.exBankBranch = exBankBranch;
		this.exAccountBalance = exAccountBalance;
		this.exBankMaster = exBankMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCustomer = fsCustomer;
		this.exOutrightSwapMaster = exOutrightSwapMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.documentFinYear = documentFinYear;
		this.documentCode = documentCode;
		this.documentNumber = documentNumber;
		this.documentDate = documentDate;
		this.dealRefNumber = dealRefNumber;
		this.dealRefDate = dealRefDate;
		this.dealer = dealer;
		this.isspecialDeal = isspecialDeal;
		this.concludedBy = concludedBy;
		this.dealWithBank = dealWithBank;
		this.reason = reason;
		this.rejectedReason = rejectedReason;
		this.dealTypeMasterId = dealTypeMasterId;
		this.conclusionType = conclusionType;
		this.localExchRate = localExchRate;
		this.foreignExchRate = foreignExchRate;
		this.mltdiv = mltdiv;
		this.reasonCode = reasonCode;
		this.approvedDate = approvedDate;
		this.remark = remark;
		this.description = description;
		this.isSupplerDeal = isSupplerDeal;
		this.isactive = isactive;
		this.fileNo = fileNo;
		this.fileDate = fileDate;
		this.validDate = validDate;
		this.subLedgerNo = subLedgerNo;
		this.approvedBy = approvedBy;
		this.accountYear = accountYear;
		this.subLedgerInd = subLedgerInd;
		this.localAmount = localAmount;
		this.foreginCurrencyBal = foreginCurrencyBal;
		this.glDate = glDate;
		this.trggerInd = trggerInd;
		this.createPaymentVoucher = createPaymentVoucher;
		this.dealInd = dealInd;
		this.dealRef = dealRef;
		this.dealCancel = dealCancel;
		this.dealCancelYear = dealCancelYear;
		this.dealCancelDoc = dealCancelDoc;
		this.dealCancelNumber = dealCancelNumber;
		this.paymentVoucherRef = paymentVoucherRef;
		this.paymentVoucherYear = paymentVoucherYear;
		this.paymentVoucherDoc = paymentVoucherDoc;
		this.paymentVoucherNumber = paymentVoucherNumber;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.exDealSaleses = exDealSaleses;
		this.exDealSuppliers = exDealSuppliers;

	}
	
	@Id
	@GeneratedValue(generator="ex_deal_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_deal_seq",sequenceName="EX_DEAL_SEQ",allocationSize=1)
	@Column(name = "DEAL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDealId() {
		return this.dealId;
	}

	public void setDealId(BigDecimal dealId) {
		this.dealId = dealId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrencyMaster() {
		return this.exCurrencyMaster;
	}

	public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		this.exCurrencyMaster = exCurrencyMaster;
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
	@JoinColumn(name = "ACCOUNT_ID")
	public AccountBalance getExAccountBalance() {
		return this.exAccountBalance;
	}

	public void setExAccountBalance(AccountBalance exAccountBalance) {
		this.exAccountBalance = exAccountBalance;
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
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
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
	@JoinColumn(name = "OUTRIGHT_SWAP_ID")
	public OutrightSwapMaster getExOutrightSwapMaster() {
		return this.exOutrightSwapMaster;
	}

	public void setExOutrightSwapMaster(OutrightSwapMaster exOutrightSwapMaster) {
		this.exOutrightSwapMaster = exOutrightSwapMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "DOCUMENT_FIN_YEAR", precision = 4, scale = 0)
	public Short getDocumentFinYear() {
		return this.documentFinYear;
	}

	public void setDocumentFinYear(Short documentFinYear) {
		this.documentFinYear = documentFinYear;
	}

	@Column(name = "DOCUMENT_CODE", precision = 4, scale = 0)
	public Short getDocumentCode() {
		return this.documentCode;
	}

	public void setDocumentCode(Short documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_NUMBER", precision = 22, scale = 0)
	public BigDecimal getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return this.documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "DEAL_REF_NUMBER", precision = 22, scale = 0)
	public BigDecimal getDealRefNumber() {
		return this.dealRefNumber;
	}

	public void setDealRefNumber(BigDecimal dealRefNumber) {
		this.dealRefNumber = dealRefNumber;
	}

	@Column(name = "DEAL_REF_DATE")
	public Date getDealRefDate() {
		return this.dealRefDate;
	}

	public void setDealRefDate(Date dealRefDate) {
		this.dealRefDate = dealRefDate;
	}

	@Column(name = "DEALER", length = 50)
	public String getDealer() {
		return this.dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	@Column(name = "ISSPECIAL_DEAL", length = 1)
	public String getIsspecialDeal() {
		return this.isspecialDeal;
	}

	public void setIsspecialDeal(String isspecialDeal) {
		this.isspecialDeal = isspecialDeal;
	}

	@Column(name = "CONCLUDED_BY", length = 50)
	public String getConcludedBy() {
		return this.concludedBy;
	}

	public void setConcludedBy(String concludedBy) {
		this.concludedBy = concludedBy;
	}

	@Column(name = "DEAL_WITH_BANK", precision = 22, scale = 0)
	public BigDecimal getDealWithBank() {
		return this.dealWithBank;
	}

	public void setDealWithBank(BigDecimal dealWithBank) {
		this.dealWithBank = dealWithBank;
	}

	@Column(name = "REASON", length = 50)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "REJECTED_REASON", length = 50)
	public String getRejectedReason() {
		return this.rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	@Column(name = "DEAL_TYPE_MASTER_ID", precision = 22, scale = 0)
	public BigDecimal getDealTypeMasterId() {
		return this.dealTypeMasterId;
	}

	public void setDealTypeMasterId(BigDecimal dealTypeMasterId) {
		this.dealTypeMasterId = dealTypeMasterId;
	}

	@Column(name = "CONCLUSION_TYPE", precision = 22, scale = 0)
	public BigDecimal getConclusionType() {
		return this.conclusionType;
	}

	public void setConclusionType(BigDecimal conclusionType) {
		this.conclusionType = conclusionType;
	}

	@Column(name = "LOCAL_EXCH_RATE", precision = 22, scale = 0)
	public BigDecimal getLocalExchRate() {
		return this.localExchRate;
	}

	public void setLocalExchRate(BigDecimal localExchRate) {
		this.localExchRate = localExchRate;
	}

	@Column(name = "FOREIGN_EXCH_RATE", precision = 22, scale = 0)
	public BigDecimal getForeignExchRate() {
		return this.foreignExchRate;
	}

	public void setForeignExchRate(BigDecimal foreignExchRate) {
		this.foreignExchRate = foreignExchRate;
	}

	@Column(name = "MLTDIV", length = 1)
	public String getMltdiv() {
		return this.mltdiv;
	}

	public void setMltdiv(String mltdiv) {
		this.mltdiv = mltdiv;
	}

	@Column(name = "REASON_CODE", length = 2)
	public String getReasonCode() {
		return this.reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name = "REMARK", length = 2000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DESCRIPTION", length = 2000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "IS_SUPPLER_DEAL", length = 1)
	public String getIsSupplerDeal() {
		return this.isSupplerDeal;
	}

	public void setIsSupplerDeal(String isSupplerDeal) {
		this.isSupplerDeal = isSupplerDeal;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "FILE_NO", precision = 22, scale = 0)
	public BigDecimal getFileNo() {
		return this.fileNo;
	}

	public void setFileNo(BigDecimal fileNo) {
		this.fileNo = fileNo;
	}

	@Column(name = "FILE_DATE")
	public Date getFileDate() {
		return this.fileDate;
	}

	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

	@Column(name = "VALID_DATE")
	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Column(name = "SUB_LEDGER_NO", precision = 22, scale = 0)
	public BigDecimal getSubLedgerNo() {
		return this.subLedgerNo;
	}

	public void setSubLedgerNo(BigDecimal subLedgerNo) {
		this.subLedgerNo = subLedgerNo;
	}

	@Column(name = "APPROVED_BY", length = 50)
	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name = "ACCOUNT_YEAR")
	public Date getAccountYear() {
		return this.accountYear;
	}

	public void setAccountYear(Date accountYear) {
		this.accountYear = accountYear;
	}

	@Column(name = "SUB_LEDGER_IND", length = 1)
	public String getSubLedgerInd() {
		return this.subLedgerInd;
	}

	public void setSubLedgerInd(String subLedgerInd) {
		this.subLedgerInd = subLedgerInd;
	}

	@Column(name = "LOCAL_AMOUNT", precision = 22, scale = 3)
	public BigDecimal getLocalAmount() {
		return this.localAmount;
	}

	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}

	@Column(name = "FOREGIN_CURRENCY_BAL", precision = 22, scale = 3)
	public BigDecimal getForeginCurrencyBal() {
		return this.foreginCurrencyBal;
	}

	public void setForeginCurrencyBal(BigDecimal foreginCurrencyBal) {
		this.foreginCurrencyBal = foreginCurrencyBal;
	}

	@Column(name = "GL_DATE")
	public Date getGlDate() {
		return this.glDate;
	}

	public void setGlDate(Date glDate) {
		this.glDate = glDate;
	}

	@Column(name = "TRGGER_IND", length = 1)
	public String getTrggerInd() {
		return this.trggerInd;
	}

	public void setTrggerInd(String trggerInd) {
		this.trggerInd = trggerInd;
	}

	@Column(name = "CREATE_PAYMENT_VOUCHER", length = 1)
	public String getCreatePaymentVoucher() {
		return this.createPaymentVoucher;
	}

	public void setCreatePaymentVoucher(String createPaymentVoucher) {
		this.createPaymentVoucher = createPaymentVoucher;
	}

	@Column(name = "DEAL_IND", length = 1)
	public String getDealInd() {
		return this.dealInd;
	}

	public void setDealInd(String dealInd) {
		this.dealInd = dealInd;
	}

	@Column(name = "DEAL_REF", precision = 2, scale = 0)
	public Byte getDealRef() {
		return this.dealRef;
	}

	public void setDealRef(Byte dealRef) {
		this.dealRef = dealRef;
	}

	@Column(name = "DEAL_CANCEL", precision = 2, scale = 0)
	public Byte getDealCancel() {
		return this.dealCancel;
	}

	public void setDealCancel(Byte dealCancel) {
		this.dealCancel = dealCancel;
	}

	@Column(name = "DEAL_CANCEL_YEAR", precision = 4, scale = 0)
	public Short getDealCancelYear() {
		return this.dealCancelYear;
	}

	public void setDealCancelYear(Short dealCancelYear) {
		this.dealCancelYear = dealCancelYear;
	}

	@Column(name = "DEAL_CANCEL_DOC", precision = 2, scale = 0)
	public Byte getDealCancelDoc() {
		return this.dealCancelDoc;
	}

	public void setDealCancelDoc(Byte dealCancelDoc) {
		this.dealCancelDoc = dealCancelDoc;
	}

	@Column(name = "DEAL_CANCEL_NUMBER", precision = 22, scale = 0)
	public BigDecimal getDealCancelNumber() {
		return this.dealCancelNumber;
	}

	public void setDealCancelNumber(BigDecimal dealCancelNumber) {
		this.dealCancelNumber = dealCancelNumber;
	}

	@Column(name = "PAYMENT_VOUCHER_REF", precision = 2, scale = 0)
	public Byte getPaymentVoucherRef() {
		return this.paymentVoucherRef;
	}

	public void setPaymentVoucherRef(Byte paymentVoucherRef) {
		this.paymentVoucherRef = paymentVoucherRef;
	}

	@Column(name = "PAYMENT_VOUCHER_YEAR", precision = 4, scale = 0)
	public Short getPaymentVoucherYear() {
		return this.paymentVoucherYear;
	}

	public void setPaymentVoucherYear(Short paymentVoucherYear) {
		this.paymentVoucherYear = paymentVoucherYear;
	}

	@Column(name = "PAYMENT_VOUCHER_DOC", precision = 2, scale = 0)
	public Byte getPaymentVoucherDoc() {
		return this.paymentVoucherDoc;
	}

	public void setPaymentVoucherDoc(Byte paymentVoucherDoc) {
		this.paymentVoucherDoc = paymentVoucherDoc;
	}

	@Column(name = "PAYMENT_VOUCHER_NUMBER", precision = 2, scale = 0)
	public Byte getPaymentVoucherNumber() {
		return this.paymentVoucherNumber;
	}

	public void setPaymentVoucherNumber(Byte paymentVoucherNumber) {
		this.paymentVoucherNumber = paymentVoucherNumber;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 15)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exDeal")
	public Set<DealSales> getExDealSaleses() {
		return this.exDealSaleses;
	}

	public void setExDealSaleses(Set<DealSales> exDealSaleses) {
		this.exDealSaleses = exDealSaleses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exDeal")
	public Set<DealSupplier> getExDealSuppliers() {
		return this.exDealSuppliers;
	}

	public void setExDealSuppliers(Set<DealSupplier> exDealSuppliers) {
		this.exDealSuppliers = exDealSuppliers;
	}

}
