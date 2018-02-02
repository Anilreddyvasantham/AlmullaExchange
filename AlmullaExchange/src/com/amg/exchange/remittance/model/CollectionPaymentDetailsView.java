package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_COLLECT_PAYMENT_DETAILS")
public class CollectionPaymentDetailsView implements Serializable{

	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="IDNO")
	private BigDecimal idNo;
	
	@Column(name="COUNTRY_ID")
	private BigDecimal countryId;
	
	@Column(name="COMPANY_ID")
	private BigDecimal companyId;
	
	@Column(name="DOCUMENT_CODE")
	private BigDecimal documentCode;
	
	@Column(name="DOCUMENT_FINANCE_YEAR")
	private BigDecimal documentFinancialYear;
	
	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNo;
	
	@Column(name="COLLECTION_MODE")
	private String collectionMode;
	
	@Column(name="COLLAMT")
	private BigDecimal collectAmount;
	
	@Column(name="COLLECTION_DESCRIPTION")
	private String collectionModeDesc;
	
	@Column(name="APPROVAL_NO")
	private String approvalNo;
	
	@Column(name="TRAN_ID")
	private String transactionId;
	
	@Column(name="KNET_RECEIPT_DATE_TIME")
	private String knetReceiptDatenTime;
	
	

	
	public String getApprovalNo() {
		return approvalNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getKnetReceiptDatenTime() {
		return knetReceiptDatenTime;
	}

	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setKnetReceiptDatenTime(String knetReceiptDatenTime) {
		this.knetReceiptDatenTime = knetReceiptDatenTime;
	}

	public String getCollectionModeDesc() {
		return collectionModeDesc;
	}

	public void setCollectionModeDesc(String collectionModeDesc) {
		this.collectionModeDesc = collectionModeDesc;
	}

	public BigDecimal getIdNo() {
		return idNo;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public String getCollectionMode() {
		return collectionMode;
	}

	public BigDecimal getCollectAmount() {
		return collectAmount;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}

	public void setCollectAmount(BigDecimal collectAmount) {
		this.collectAmount = collectAmount;
	}
	
	
}
