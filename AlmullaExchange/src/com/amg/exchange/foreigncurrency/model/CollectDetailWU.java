package com.amg.exchange.foreigncurrency.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="EX_WU_COLLECT_DET")
public class CollectDetailWU implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	private BigDecimal companyCode;
	private BigDecimal documentCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private Date documentDate;
	private String collectionMode;
	
	private String currencyCode;
	private String debitCard;
	private String dbCardName;
	private String createdBy;
	private Date createdDate;
	private BigDecimal collAmt;
	private BigDecimal bankCode;
	private String approvalNo;

	public CollectDetailWU() {
	
	}
	
	
	
	public CollectDetailWU(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYear, BigDecimal documentNo, Date documentDate, String collectionMode, String currencyCode, String debitCard, String dbCardName, String createdBy, Date createdDate, BigDecimal collAmt,
			BigDecimal bankCode, String approvalNo) {
		super();
		this.companyCode = companyCode;
		this.documentCode = documentCode;
		this.documentFinanceYear = documentFinanceYear;
		this.documentNo = documentNo;
		this.documentDate = documentDate;
		this.collectionMode = collectionMode;
		this.currencyCode = currencyCode;
		this.debitCard = debitCard;
		this.dbCardName = dbCardName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.collAmt = collAmt;
		this.bankCode = bankCode;
		this.approvalNo = approvalNo;
	}



	@Column(name = "DOCCOD")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	@Column(name = "DOCFYR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	@Column(name = "DOCNO")
	@Id
	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	@Column(name = "DOCDAT")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name = "COLLMOD")
	public String getCollectionMode() {
		return collectionMode;
	}

	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}
	
	@Column(name = "APRNO")
	public String getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	
	@Column(name = "COLLAMT")
	public BigDecimal getCollAmt() {
		return collAmt;
	}

	public void setCollAmt(BigDecimal collAmt) {
		this.collAmt = collAmt;
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
	
	@Column(name = "CREATOR")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "CRTDAT")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "COMCOD")
	public BigDecimal getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}
	
	@Column(name = "CURCOD")
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	@Column(name = "BNKCOD")
	public BigDecimal getBankCode() {
		return bankCode;
	}

	public void setBankCode(BigDecimal bankCode) {
		this.bankCode = bankCode;
	}
	
}
