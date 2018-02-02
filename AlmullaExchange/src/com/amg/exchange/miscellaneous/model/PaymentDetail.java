package com.amg.exchange.miscellaneous.model;

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
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.treasury.model.CurrencyMaster;
/*******************************************************************************************************************

File		: PaymentDetail.java

Project	: AlmullaExchange

Package	: com.amg.exchange.miscellaneous.model

Created	:	
				Date	: 08-Sep-2015
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	:  08-Sep-2015
				By		: Nazish Ehsan Hashmi
				Revision:


********************************************************************************************************************/

@Entity
@Table(name = "EX_PAYMENT_DETAIL" )
public class PaymentDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal paymentDetailsId;	
	private CountryMaster countryId;
	private CompanyMaster companyId;
	private Customer fsCustomer;
	private BigDecimal documentCode;
	private BigDecimal docNumber;
	private CountryBranch branchId;
	private BigDecimal documentLineNo;
	private String paymentmode;
	private BigDecimal chequeBankRef;
	private String chequeReference;
	private Date chequekdate;
	private String approvalNo;
	private CurrencyMaster currencyId;
	private BigDecimal paymentAmt;
	private String isCashDeposit;
	private BigDecimal cdepDoccod;
	private BigDecimal cdepDocfyr;
	private BigDecimal cdepDocno;
	private Date cdepDate;
	private String isActive;
	private String authby;
	private Date authdat;
	private Date acyymm;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Payment paymentId;
	private BigDecimal dcoumentId;
	private BigDecimal documentFinaceyear;
	private Date paymentDate;
	private String debitCard;
	private String dbCardName;
	private PaymentMode paymentModeId;
	
	
	
	@Id
	@GeneratedValue(generator="ex_payment_detail_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_payment_detail_seq",sequenceName="EX_PAYMENT_DETAIL_SEQ",allocationSize=1)
	@Column(name = "PAYMENT_DETAIL_ID")
	public BigDecimal getPaymentDetailsId() {
		return paymentDetailsId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="APPLICATION_COUNTRY_ID")
	public CountryMaster getCountryId() {
		return countryId;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PAYMENT_MODE_ID")
	public PaymentMode getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(PaymentMode paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COMPANY_ID")
	public CompanyMaster getCompanyId() {
		return companyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}
	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}
	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocNumber() {
		return docNumber;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getBranchId() {
		return branchId;
	}

	@Column(name = "DOCUMENT_LINE_NO")
	public BigDecimal getDocumentLineNo() {
		return documentLineNo;
	}
	@Column(name = "PAYMENT_MODE")
	public String getPaymentmode() {
		return paymentmode;
	}
	@Column(name = "CHEQUE_BANK_REF")
	public BigDecimal getChequeBankRef() {
		return chequeBankRef;
	}
	@Column(name = "CHEQUE_REFERENCE")
	public String getChequeReference() {
		return chequeReference;
	}

	@Column(name = "CHEQUE_DATE")
	public Date getChequekdate() {
		return chequekdate;
	}
	@Column(name = "APPROVAL_NO")
	public String getApprovalNo() {
		return approvalNo;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCurrencyId() {
		return currencyId;
	}
	@Column(name = "PAYMENT_AMT")
	public BigDecimal getPaymentAmt() {
		return paymentAmt;
	}
	@Column(name = "IS_CASH_DEPOSIT")
	public String getIsCashDeposit() {
		return isCashDeposit;
	}
	@Column(name = "CDEP_DOCCOD")
	public BigDecimal getCdepDoccod() {
		return cdepDoccod;
	}
	@Column(name = "CDEP_DOCFYR")
	public BigDecimal getCdepDocfyr() {
		return cdepDocfyr;
	}
	@Column(name = "CDEP_DOCNO")
	public BigDecimal getCdepDocno() {
		return cdepDocno;
	}
	@Column(name = "CDEP_DATE")
	public Date getCdepDate() {
		return cdepDate;
	}
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	@Column(name = "AUTHBY")
	public String getAuthby() {
		return authby;
	}
	@Column(name = "AUTHDAT")
	public Date getAuthdat() {
		return authdat;
	}
	@Column(name = "ACCOUNT_MMYYYY")
	public Date getAcyymm() {
		return acyymm;
	}
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "PAYMENT_ID")
	public Payment getPaymentId() {
		return paymentId;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDcoumentId() {
		return dcoumentId;
	}

	public void setDcoumentId(BigDecimal dcoumentId) {
		this.dcoumentId = dcoumentId;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinaceyear() {
		return documentFinaceyear;
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
	@Column(name = "PAYMENT_DATE")
	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setPaymentId(Payment paymentId) {
		this.paymentId = paymentId;
	}

	public void setDocumentFinaceyear(BigDecimal documentFinaceyear) {
		this.documentFinaceyear = documentFinaceyear;
	}

	public void setPaymentDetailsId(BigDecimal paymentDetailsId) {
		this.paymentDetailsId = paymentDetailsId;
	}
	public void setCountryId(CountryMaster countryId) {
		this.countryId = countryId;
	}
	
	public void setCompanyId(CompanyMaster companyId) {
		this.companyId = companyId;
	}

	public void setDocNumber(BigDecimal docNumber) {
		this.docNumber = docNumber;
	}
	public void setBranchId(CountryBranch branchId) {
		this.branchId = branchId;
	}

	public void setDocumentLineNo(BigDecimal documentLineNo) {
		this.documentLineNo = documentLineNo;
	}
	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}
	public void setChequeBankRef(BigDecimal chequeBankRef) {
		this.chequeBankRef = chequeBankRef;
	}
	public void setChequeReference(String chequeReference) {
		this.chequeReference = chequeReference;
	}
	public void setChequekdate(Date chequekdate) {
		this.chequekdate = chequekdate;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	public void setCurrencyId(CurrencyMaster currencyId) {
		this.currencyId = currencyId;
	}
	public void setPaymentAmt(BigDecimal paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public void setIsCashDeposit(String isCashDeposit) {
		this.isCashDeposit = isCashDeposit;
	}
	public void setCdepDoccod(BigDecimal cdepDoccod) {
		this.cdepDoccod = cdepDoccod;
	}
	public void setCdepDocfyr(BigDecimal cdepDocfyr) {
		this.cdepDocfyr = cdepDocfyr;
	}
	public void setCdepDocno(BigDecimal cdepDocno) {
		this.cdepDocno = cdepDocno;
	}
	public void setCdepDate(Date cdepDate) {
		this.cdepDate = cdepDate;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public void setAuthby(String authby) {
		this.authby = authby;
	}
	public void setAuthdat(Date authdat) {
		this.authdat = authdat;
	}
	public void setAcyymm(Date acyymm) {
		this.acyymm = acyymm;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}


}
