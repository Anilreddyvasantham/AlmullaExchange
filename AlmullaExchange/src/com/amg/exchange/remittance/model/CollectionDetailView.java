package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_COLLECTION_DETAIL")
public class CollectionDetailView implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal idno;
	private BigDecimal paidAmount;
	private BigDecimal refundedAmount;
	private BigDecimal netAmount;
	private BigDecimal documentNo;
	private String collectionMode;
	private BigDecimal companyId;
	private BigDecimal documentCode;
	private BigDecimal documentFinancialYear;
	
	public CollectionDetailView() {
		super();
	}

	public CollectionDetailView(BigDecimal idno, BigDecimal paidAmount,
			BigDecimal refundedAmount, BigDecimal netAmount,
			BigDecimal documentNo, String collectionMode, BigDecimal companyId,
			BigDecimal documentCode, BigDecimal documentFinancialYear) {
		super();
		this.idno = idno;
		this.paidAmount = paidAmount;
		this.refundedAmount = refundedAmount;
		this.netAmount = netAmount;
		this.documentNo = documentNo;
		this.collectionMode = collectionMode;
		this.companyId = companyId;
		this.documentCode = documentCode;
		this.documentFinancialYear = documentFinancialYear;
	}



	@Id
	@Column(name = "IDNO")
	public BigDecimal getIdno() {
		return idno;
	}
	public void setIdno(BigDecimal idno) {
		this.idno = idno;
	}

	@Column(name = "PAID_AMOUNT")
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}

	@Column(name = "REFUNDED_AMOUNT")
	public BigDecimal getRefundedAmount() {
		return refundedAmount;
	}
	public void setRefundedAmount(BigDecimal refundedAmount) {
		this.refundedAmount = refundedAmount;
	}

	@Column(name = "NET_AMOUNT")
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "COLLECTION_MODE")
	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}
	
	@Column(name="COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Column(name="DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name="DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}
	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	@Override
	public String toString() {
		return "CollectionDetailView [idno=" + idno + ", paidAmount=" + paidAmount + ", refundedAmount=" + refundedAmount + ", netAmount=" + netAmount + ", documentNo=" + documentNo + ", collectionMode=" + collectionMode + "]";
	}
	
	

}
