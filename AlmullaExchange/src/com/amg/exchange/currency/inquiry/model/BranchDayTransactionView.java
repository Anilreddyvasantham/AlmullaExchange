package com.amg.exchange.currency.inquiry.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_BRANCH_DAY_TRNX_ENQUIRY")
public class BranchDayTransactionView implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal idNo;
	private BigDecimal documentId;
	private BigDecimal documentCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private Date documentDate;
	private BigDecimal foreignTranxAmount;
	private BigDecimal localTranxAmount;
	private String customerRef;
	private String oldEmos;
	private String mtcNo;
	private BigDecimal customerId;
	private BigDecimal collectionDocCode;
	private BigDecimal collectionDocFinanceYear;
	private BigDecimal collectionDocNumber;
	private BigDecimal refundAmount;
	private BigDecimal employeeId;
	private String transactionType;
	private BigDecimal countryBranchId;
	private Date accountMMYYYY;
	private String quoteName;
	private BigDecimal localTranxCurrencyId;

	public BranchDayTransactionView() {
		super();
	}

	public BranchDayTransactionView(BigDecimal idNo, BigDecimal documentId,
			BigDecimal documentCode, BigDecimal documentFinanceYear,
			BigDecimal documentNo, Date documentDate,
			BigDecimal foreignTranxAmount, BigDecimal localTranxAmount,
			String customerRef, String oldEmos, String mtcNo,
			BigDecimal customerId, BigDecimal collectionDocCode,
			BigDecimal collectionDocFinanceYear,
			BigDecimal collectionDocNumber, BigDecimal refundAmount,
			BigDecimal employeeId, String transactionType,
			BigDecimal countryBranchId, Date accountMMYYYY, String quoteName,
			BigDecimal localTranxCurrencyId) {
		super();
		this.idNo = idNo;
		this.documentId = documentId;
		this.documentCode = documentCode;
		this.documentFinanceYear = documentFinanceYear;
		this.documentNo = documentNo;
		this.documentDate = documentDate;
		this.foreignTranxAmount = foreignTranxAmount;
		this.localTranxAmount = localTranxAmount;
		this.customerRef = customerRef;
		this.oldEmos = oldEmos;
		this.mtcNo = mtcNo;
		this.customerId = customerId;
		this.collectionDocCode = collectionDocCode;
		this.collectionDocFinanceYear = collectionDocFinanceYear;
		this.collectionDocNumber = collectionDocNumber;
		this.refundAmount = refundAmount;
		this.employeeId = employeeId;
		this.transactionType = transactionType;
		this.countryBranchId = countryBranchId;
		this.accountMMYYYY = accountMMYYYY;
		this.quoteName = quoteName;
		this.localTranxCurrencyId = localTranxCurrencyId;
	}

	@Id
	@Column(name = "SLNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	@Column(name = "APPLICATION_TYPE_DESC")
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@Column(name = "CUSTOMER_REFERENCE")
	public String getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(String customerRef) {
		this.customerRef = customerRef;
	}

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "FOREIGN_TRANX_AMOUNT")
	public BigDecimal getForeignTranxAmount() {
		return foreignTranxAmount;
	}
	public void setForeignTranxAmount(BigDecimal foreignTranxAmount) {
		this.foreignTranxAmount = foreignTranxAmount;
	}

	@Column(name = "LOCAL_TRANX_AMOUNT")
	public BigDecimal getLocalTranxAmount() {
		return localTranxAmount;
	}
	public void setLocalTranxAmount(BigDecimal localTranxAmount) {
		this.localTranxAmount = localTranxAmount;
	}

	@Column(name = "REFUNDED_AMOUNT")
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	@Column(name = "TRF_OLD_EMOS")
	public String getOldEmos() {
		return oldEmos;
	}
	public void setOldEmos(String oldEmos) {
		this.oldEmos = oldEmos;
	}

	@Column(name = "WESTERN_UNION_MTCNO")
	public String getMtcNo() {
		return mtcNo;
	}
	public void setMtcNo(String mtcNo) {
		this.mtcNo = mtcNo;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "COLLECTION_DOCUMENT_NO")
	public BigDecimal getCollectionDocNumber() {
		return collectionDocNumber;
	}
	public void setCollectionDocNumber(BigDecimal collectionDocNumber) {
		this.collectionDocNumber = collectionDocNumber;
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

	@Column(name = "EMPLOYEE_ID")
	public BigDecimal getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "ACCOUNT_MMYYYY")
	public Date getAccountMMYYYY() {
		return accountMMYYYY;
	}
	public void setAccountMMYYYY(Date accountMMYYYY) {
		this.accountMMYYYY = accountMMYYYY;
	}

	@Column(name = "QUOTE_NAME")
	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	@Column(name = "LOCAL_TRANX_CURRENCY_ID")
	public BigDecimal getLocalTranxCurrencyId() {
		return localTranxCurrencyId;
	}
	public void setLocalTranxCurrencyId(BigDecimal localTranxCurrencyId) {
		this.localTranxCurrencyId = localTranxCurrencyId;
	}
	
	


}
