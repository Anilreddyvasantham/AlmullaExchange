package com.amg.exchange.currency.inquiry.bean;

import java.math.BigDecimal;
import java.util.Date;

public class UserwiseTransactionDataTable {

	private BigDecimal idNo;
	private String transactionType;
	private String collectionMode;
	private BigDecimal countryBranchId;
	private String customerRef;
	private BigDecimal documentCode;
	private Date documentDate;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private BigDecimal foreignTranxAmount;
	private BigDecimal localTranxAmount;
	private BigDecimal refundAmount;
	private String oldEmos;
	private String mtcNo;
	private BigDecimal customerId;
	private BigDecimal cash;
	private BigDecimal cheque;
	private BigDecimal kNet;
	private BigDecimal others;
	
	
	
	
	
	
	

	public BigDecimal getIdNo() {
		return idNo;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCollectionMode() {
		return collectionMode;
	}

	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public String getCustomerRef() {
		return customerRef;
	}

	public void setCustomerRef(String customerRef) {
		this.customerRef = customerRef;
	}

	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public BigDecimal getForeignTranxAmount() {
		return foreignTranxAmount;
	}

	public void setForeignTranxAmount(BigDecimal foreignTranxAmount) {
		this.foreignTranxAmount = foreignTranxAmount;
	}

	public BigDecimal getLocalTranxAmount() {
		return localTranxAmount;
	}

	public void setLocalTranxAmount(BigDecimal localTranxAmount) {
		this.localTranxAmount = localTranxAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getOldEmos() {
		return oldEmos;
	}

	public void setOldEmos(String oldEmos) {
		this.oldEmos = oldEmos;
	}

	public String getMtcNo() {
		return mtcNo;
	}

	public void setMtcNo(String mtcNo) {
		this.mtcNo = mtcNo;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getCheque() {
		return cheque;
	}

	public void setCheque(BigDecimal cheque) {
		this.cheque = cheque;
	}

	public BigDecimal getkNet() {
		return kNet;
	}

	public void setkNet(BigDecimal kNet) {
		this.kNet = kNet;
	}

	public BigDecimal getOthers() {
		return others;
	}

	public void setOthers(BigDecimal others) {
		this.others = others;
	}

	@Override
	public String toString() {
		return "UserwiseTransactionDataTable [idNo=" + idNo + ", transactionType=" + transactionType + ", collectionMode=" + collectionMode + ", countryBranchId=" + countryBranchId + ", customerRef=" + customerRef + ", documentCode=" + documentCode + ", documentDate=" + documentDate
				+ ", documentFinanceYear=" + documentFinanceYear + ", documentId=" + documentId + ", documentNo=" + documentNo + ", foreignTranxAmount=" + foreignTranxAmount + ", localTranxAmount=" + localTranxAmount + ", refundAmount=" + refundAmount + ", oldEmos=" + oldEmos + ", mtcNo=" + mtcNo
				+ ", customerId=" + customerId + ", cash=" + cash + ", cheque=" + cheque + ", kNet=" + kNet + ", others=" + others + "]";
	}
	
	
	

}
