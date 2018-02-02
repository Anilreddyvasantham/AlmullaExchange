package com.amg.exchange.stoppayment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_REFUND")
public class RefundInquiryView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal companyId;
	private BigDecimal applicationCountryId;
	private BigDecimal documentCode;
	private BigDecimal documentYear;
	private BigDecimal documentNo;
	private BigDecimal customerId;
	private Date documentDate;
	private String foreignCurrencyCode;
	private BigDecimal foreignTrnxAmount;
	private BigDecimal trnxActualRate;
	private String localCurrencyCode;
	private BigDecimal localTrnxAmount;
	private BigDecimal localNetAmount;
	private BigDecimal locationCode;
	private String paymentMode;
	private String bankCode;
	private String bankDesc;
	private String branchCode;
	private String branchDesc;
	private String locationDesc;
	private BigDecimal transactionYear;
	private BigDecimal transactionNo;
	private Date transactionDate;
	private String paymentModeDesc;
	private BigDecimal transactionId;
	private BigDecimal chequeBankCode;
	private Date chequeDate;
	private String chequeReference;
	private String approvalNo;
	private BigDecimal foreignCurrencyId;
	private BigDecimal localCurrencyId;
	private String remarks;
	private String documentStatus;
	
	
	public RefundInquiryView() {
		super();
	}	
	
	public RefundInquiryView(BigDecimal companyId,
			BigDecimal applicationCountryId, BigDecimal documentCode,
			BigDecimal documentYear, BigDecimal documentNo,
			BigDecimal customerId, Date documentDate,
			String foreignCurrencyCode, BigDecimal foreignTrnxAmount,
			BigDecimal trnxActualRate, String localCurrencyCode,
			BigDecimal localTrnxAmount, BigDecimal localNetAmount,
			BigDecimal locationCode, String paymentMode, String bankCode,
			String bankDesc, String branchCode, String branchDesc,
			String locationDesc, BigDecimal transactionYear,
			BigDecimal transactionNo, Date transactionDate,
			String paymentModeDesc, BigDecimal transactionId,
			BigDecimal chequeBankCode, Date chequeDate, String chequeReference,
			String approvalNo) {
		super();
		this.companyId = companyId;
		this.applicationCountryId = applicationCountryId;
		this.documentCode = documentCode;
		this.documentYear = documentYear;
		this.documentNo = documentNo;
		this.customerId = customerId;
		this.documentDate = documentDate;
		this.foreignCurrencyCode = foreignCurrencyCode;
		this.foreignTrnxAmount = foreignTrnxAmount;
		this.trnxActualRate = trnxActualRate;
		this.localCurrencyCode = localCurrencyCode;
		this.localTrnxAmount = localTrnxAmount;
		this.localNetAmount = localNetAmount;
		this.locationCode = locationCode;
		this.paymentMode = paymentMode;
		this.bankCode = bankCode;
		this.bankDesc = bankDesc;
		this.branchCode = branchCode;
		this.branchDesc = branchDesc;
		this.locationDesc = locationDesc;
		this.transactionYear = transactionYear;
		this.transactionNo = transactionNo;
		this.transactionDate = transactionDate;
		this.paymentModeDesc = paymentModeDesc;
		this.transactionId = transactionId;
		this.chequeBankCode = chequeBankCode;
		this.chequeDate = chequeDate;
		this.chequeReference = chequeReference;
		this.approvalNo = approvalNo;
	}

	@Id	
	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	@Column(name = "COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentYear() {
		return documentYear;
	}
	public void setDocumentYear(BigDecimal documentYear) {
		this.documentYear = documentYear;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "FOREIGN_CURRENCY_CODE")
	public String getForeignCurrencyCode() {
		return foreignCurrencyCode;
	}
	public void setForeignCurrencyCode(String foreignCurrencyCode) {
		this.foreignCurrencyCode = foreignCurrencyCode;
	}

	@Column(name = "FOREIGN_TRNX_AMOUNT")
	public BigDecimal getForeignTrnxAmount() {
		return foreignTrnxAmount;
	}
	public void setForeignTrnxAmount(BigDecimal foreignTrnxAmount) {
		this.foreignTrnxAmount = foreignTrnxAmount;
	}

	@Column(name = "TRANSACTION_ACTUAL_RATE")
	public BigDecimal getTrnxActualRate() {
		return trnxActualRate;
	}
	public void setTrnxActualRate(BigDecimal trnxActualRate) {
		this.trnxActualRate = trnxActualRate;
	}

	@Column(name = "LOCAL_CURRENCY_CODE")
	public String getLocalCurrencyCode() {
		return localCurrencyCode;
	}
	public void setLocalCurrencyCode(String localCurrencyCode) {
		this.localCurrencyCode = localCurrencyCode;
	}

	@Column(name = "LOCAL_TRNX_AMOUNT")
	public BigDecimal getLocalTrnxAmount() {
		return localTrnxAmount;
	}
	public void setLocalTrnxAmount(BigDecimal localTrnxAmount) {
		this.localTrnxAmount = localTrnxAmount;
	}

	@Column(name = "LOCAL_NET_AMOUNT")
	public BigDecimal getLocalNetAmount() {
		return localNetAmount;
	}
	public void setLocalNetAmount(BigDecimal localNetAmount) {
		this.localNetAmount = localNetAmount;
	}

	@Column(name = "LOCCOD")
	public BigDecimal getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(BigDecimal locationCode) {
		this.locationCode = locationCode;
	}

	@Column(name = "PAYMENT_MODE")
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "BANK_DESC")
	public String getBankDesc() {
		return bankDesc;
	}
	public void setBankDesc(String bankDesc) {
		this.bankDesc = bankDesc;
	}

	@Column(name = "BRANCH_CODE")
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@Column(name = "BRANCH_DESC")
	public String getBranchDesc() {
		return branchDesc;
	}
	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}

	@Column(name = "LOCATION_DESC")
	public String getLocationDesc() {
		return locationDesc;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	@Column(name = "TRANSACTION_YEAR")
	public BigDecimal getTransactionYear() {
		return transactionYear;
	}
	public void setTransactionYear(BigDecimal transactionYear) {
		this.transactionYear = transactionYear;
	}

	@Column(name = "TRANSACTION_NO")
	public BigDecimal getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(BigDecimal transactionNo) {
		this.transactionNo = transactionNo;
	}

	@Column(name = "TRANSACTION_DATE")
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Column(name = "PAYMENT_DESC")
	public String getPaymentModeDesc() {
		return paymentModeDesc;
	}
	public void setPaymentModeDesc(String paymentModeDesc) {
		this.paymentModeDesc = paymentModeDesc;
	}

	@Column(name = "TRANSACTION_ID")
	public BigDecimal getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	@Column(name = "CHEQUE_BANK_CODE")
	public BigDecimal getChequeBankCode() {
		return chequeBankCode;
	}
	public void setChequeBankCode(BigDecimal chequeBankCode) {
		this.chequeBankCode = chequeBankCode;
	}

	@Column(name = "CHEQUE_DATE")
	public Date getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	@Column(name = "CHEQUE_REFERENCE")
	public String getChequeReference() {
		return chequeReference;
	}
	public void setChequeReference(String chequeReference) {
		this.chequeReference = chequeReference;
	}

	@Column(name = "APPROVAL_NO")
	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}

	@Column(name = "FOREIGN_CURRENCY_ID")
	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}
	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}

	@Column(name = "LOCAL_CURRNECY_ID")
	public BigDecimal getLocalCurrencyId() {
		return localCurrencyId;
	}
	public void setLocalCurrencyId(BigDecimal localCurrencyId) {
		this.localCurrencyId = localCurrencyId;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "DOCUMENT_STATUS")
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}	
	
}
