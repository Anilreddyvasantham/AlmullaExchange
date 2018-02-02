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

import com.amg.exchange.common.model.CountryMaster;
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
@Table(name = "EX_PAYMENT" )
public class Payment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal paymentId;	
	private CountryMaster countryId;
	private BigDecimal companyId;
	private BigDecimal docCode;
	private BigDecimal docNumber;
	private String paymentmode;
	private String chequeReference;
	private Date chequekdate;
	private String approvalNo;
	private BigDecimal currencyId;
	private String isCashDeposit;
	private BigDecimal cdepDoccod;
	private BigDecimal cdepDocfyr;
	private BigDecimal cdepDocno;
	private Date cdepDate;
	private String isActive;
	private Date acyymm;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal docYear;
	private BigDecimal customerId;
	//private BigDecimal applicationFinancialYear;
	//private BigDecimal applicationNumber;
	private BigDecimal countryBranchId;
	private String remarks;
	private String receiptType;
	private Date paymentDate;
	private String chequeBankCode;
	private BigDecimal paidAmount;
	
	private BigDecimal companyCode;
	private BigDecimal documentId;
	private BigDecimal locCod;
	
	
	@Id
	@GeneratedValue(generator="ex_payment_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_payment_seq",sequenceName="EX_PAYMENT_SEQ",allocationSize=1)
	@Column(name = "PAYMENT_ID")
	public BigDecimal getPaymentId() {
		return paymentId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="APPLICATION_COUNTRY_ID")
	public CountryMaster getCountryId() {
		return countryId;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocCode() {
		return docCode;
	}
	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocNumber() {
		return docNumber;
	}

	@Column(name = "PAYMENT_MODE")
	public String getPaymentmode() {
		return paymentmode;
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
	@Column(name="CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
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
	
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocYear() {
		return docYear;
	}
	
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}

	/*@Column(name = "APP_FINANCE_YEAR")
	public BigDecimal getApplicationFinancialYear() {
		return applicationFinancialYear;
	}
*/
	/*@Column(name = "APP_NO")
	public BigDecimal getApplicationNumber() {
		return applicationNumber;
	}*/

	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	@Column(name = "RECEIPT_TYPE")
	public String getReceiptType() {
		return receiptType;
	}

	@Column(name = "PAYMENT_DATE")
	public Date getPaymentDate() {
		return paymentDate;
	}

	@Column(name = "CHEQUE_BANK_CODE")
	public String getChequeBankCode() {
		return chequeBankCode;
	}

	@Column(name = "PAID_AMOUNT")
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	/*public void setApplicationFinancialYear(BigDecimal applicationFinancialYear) {
		this.applicationFinancialYear = applicationFinancialYear;
	}

	public void setApplicationNumber(BigDecimal applicationNumber) {
		this.applicationNumber = applicationNumber;
	}*/

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setChequeBankCode(String chequeBankCode) {
		this.chequeBankCode = chequeBankCode;
	}

	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	public void setPaymentId(BigDecimal paymentId) {
		this.paymentId = paymentId;
	}

	public void setCountryId(CountryMaster countryId) {
		this.countryId = countryId;
	}
	
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	public void setDocCode(BigDecimal docCode) {
		this.docCode = docCode;
	}
	public void setDocNumber(BigDecimal docNumber) {
		this.docNumber = docNumber;
	}

	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
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
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
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
	
	public void setDocYear(BigDecimal docYear) {
		this.docYear = docYear;
	}
	
	@Column(name = "COMPANY_CODE")
	public BigDecimal getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	@Column(name = "LOCCOD")
	public BigDecimal getLocCod() {
		return locCod;
	}
	public void setLocCod(BigDecimal locCod) {
		this.locCod = locCod;
	}


}
