package com.amg.exchange.currency.inquiry.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_CASH_DETAILS")
public class CashDetailsView implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal idNo;
	private BigDecimal companyId;
	private String companyCode;
	private BigDecimal documentId;
	private BigDecimal documentCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentFinnceYearId;
	private BigDecimal documentNo;
	private BigDecimal documentBranchId;
	private BigDecimal branchCode;
	private String documentType;
	private Date documentDate;
	private BigDecimal documentLineNo;
	private String collectionMode;
	private BigDecimal chequeBankReference;
	private String chequeReference;
	private Date chequeDate;
	private String approveNo;
	private BigDecimal currencyId;
	private BigDecimal collectionAmount;
	private BigDecimal refundAmount;
	private BigDecimal netAmount;
	private String inOrOut;
	private String MTCNo;
	private Date createdDate;
	private String createdBy;

	/**
	 * @param idNo
	 * @param companyId
	 * @param companyCode
	 * @param documentId
	 * @param documentCode
	 * @param documentFinanceYear
	 * @param documentFinnceYearId
	 * @param documentNo
	 * @param documentBranchId
	 * @param branchCode
	 * @param documentType
	 * @param documentDate
	 * @param documentLineNo
	 * @param collectionMode
	 * @param chequeBankReference
	 * @param chequeDate
	 * @param approveNo
	 * @param currencyId
	 * @param collectionAmount
	 * @param refundAmount
	 * @param netAmount
	 * @param inOrOut
	 * @param mTCNo
	 * @param createdDate
	 * @param createdBy
	 */
	
	public CashDetailsView(BigDecimal companyId, String companyCode, BigDecimal documentId, BigDecimal documentCode, BigDecimal documentFinanceYear, BigDecimal documentFinnceYearId, BigDecimal documentNo, BigDecimal documentBranchId, BigDecimal branchCode, String documentType, Date documentDate,
			BigDecimal documentLineNo, String collectionMode, BigDecimal chequeBankReference, Date chequeDate, String approveNo, BigDecimal currencyId, BigDecimal collectionAmount, BigDecimal refundAmount, BigDecimal netAmount, String inOrOut, String mTCNo, Date createdDate, String createdBy,
			BigDecimal idNo) {
		super();
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.documentId = documentId;
		this.documentCode = documentCode;
		this.documentFinanceYear = documentFinanceYear;
		this.documentFinnceYearId = documentFinnceYearId;
		this.documentNo = documentNo;
		this.documentBranchId = documentBranchId;
		this.branchCode = branchCode;
		this.documentType = documentType;
		this.documentDate = documentDate;
		this.documentLineNo = documentLineNo;
		this.collectionMode = collectionMode;
		this.chequeBankReference = chequeBankReference;
		this.chequeDate = chequeDate;
		this.approveNo = approveNo;
		this.currencyId = currencyId;
		this.collectionAmount = collectionAmount;
		this.refundAmount = refundAmount;
		this.netAmount = netAmount;
		this.inOrOut = inOrOut;
		MTCNo = mTCNo;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.idNo = idNo;
	}

	public CashDetailsView() {
	}

	@Id
	@Column(name="IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}

	@Column(name = "COMPANY_CODE")
	public String getCompanyCode() {
		return companyCode;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	@Column(name = "DOCUMENT_FINANCE_YR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	@Column(name = "DOCUMENT_FINYR_ID")
	public BigDecimal getDocumentFinnceYearId() {
		return documentFinnceYearId;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	@Column(name = "BRANCH_ID")
	public BigDecimal getDocumentBranchId() {
		return documentBranchId;
	}

	@Column(name = "BRANCH_CODE")
	public BigDecimal getBranchCode() {
		return branchCode;
	}

	@Column(name = "DOCUMENT_TYPE")
	public String getDocumentType() {
		return documentType;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}

	@Column(name = "DOCUMENT_LINE_NO")
	public BigDecimal getDocumentLineNo() {
		return documentLineNo;
	}

	@Column(name = "COLLECTION_MODE")
	public String getCollectionMode() {
		return collectionMode;
	}

	@Column(name = "CHEQUE_BANK_REF")
	public BigDecimal getChequeBankReference() {
		return chequeBankReference;
	}

	@Column(name = "CHEQUE_REFERENCE")
	public String getChequeReference() {
		return chequeReference;
	}

	public void setChequeReference(String chequeReference) {
		this.chequeReference = chequeReference;
	}

	@Column(name = "CHEQUE_DATE")
	public Date getChequeDate() {
		return chequeDate;
	}

	@Column(name = "APPROVAL_NO")
	public String getApproveNo() {
		return approveNo;
	}

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	@Column(name = "COLLECTION_AMOUNT")
	public BigDecimal getCollectionAmount() {
		return collectionAmount;
	}

	@Column(name = "REFUND_AMOUNT")
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	@Column(name = "NET_AMOUNT")
	public BigDecimal getNetAmount() {
		return netAmount;
	}

	@Column(name = "INOROUT")
	public String getInOrOut() {
		return inOrOut;
	}

	@Column(name = "MTCNO")
	public String getMTCNo() {
		return MTCNo;
	}

	public void setInOrOut(String inOrOut) {
		this.inOrOut = inOrOut;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	public void setDocumentFinnceYearId(BigDecimal documentFinnceYearId) {
		this.documentFinnceYearId = documentFinnceYearId;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public void setDocumentBranchId(BigDecimal documentBranchId) {
		this.documentBranchId = documentBranchId;
	}

	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public void setDocumentLineNo(BigDecimal documentLineNo) {
		this.documentLineNo = documentLineNo;
	}

	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}

	public void setChequeBankReference(BigDecimal chequeBankReference) {
		this.chequeBankReference = chequeBankReference;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public void setApproveNo(String approveNo) {
		this.approveNo = approveNo;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public void setCollectionAmount(BigDecimal collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public void setMTCNo(String mTCNo) {
		MTCNo = mTCNo;
	}

}
