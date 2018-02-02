package com.amg.exchange.cashtransfer.model;

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
import com.amg.exchange.treasury.model.Document;

/**
 * Author Rahamathali Shaik
 */
@Entity
@Table(name="EX_CASH_HEADER")
public class CashHeader implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private BigDecimal cashHeaderId;
	private CountryMaster applicationCountryId;
	private CompanyMaster companyId;
	private Document documentId;
	private BigDecimal documentCode;
	private BigDecimal  financialYear;
	private BigDecimal documentNo;
	private CountryBranch countryBranchId;
	private BigDecimal countryBranchCode;
	private Date documentDate;
	private String fromCashier;
	private String toCashier;
	private BigDecimal toCountryBranchCode;
	private String isActive;
	private Date receivedDate;
	private String glStatus;
	private Date glDate;
	private String stockUpdated;
	private BigDecimal kioskSeq;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	
	public CashHeader() {
	}
	
	
	public CashHeader(BigDecimal cashHeaderId,
			CountryMaster applicationCountryId, CompanyMaster companyId,
			Document documentId, BigDecimal documentCode,
			BigDecimal financialYear, BigDecimal documentNo,
			CountryBranch countryBranchId, BigDecimal countryBranchCode,
			Date documentDate, String fromCashier, String toCashier,
			BigDecimal toCountryBranchCode, String isActive, Date receivedDate,
			String glStatus, Date glDate, String stockUpdated,
			BigDecimal kioskSeq, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate) {
		super();
		this.cashHeaderId = cashHeaderId;
		this.applicationCountryId = applicationCountryId;
		this.companyId = companyId;
		this.documentId = documentId;
		this.documentCode = documentCode;
		this.financialYear = financialYear;
		this.documentNo = documentNo;
		this.countryBranchId = countryBranchId;
		this.countryBranchCode = countryBranchCode;
		this.documentDate = documentDate;
		this.fromCashier = fromCashier;
		this.toCashier = toCashier;
		this.toCountryBranchCode = toCountryBranchCode;
		this.isActive = isActive;
		this.receivedDate = receivedDate;
		this.glStatus = glStatus;
		this.glDate = glDate;
		this.stockUpdated = stockUpdated;
		this.kioskSeq = kioskSeq;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}




	@Id
	@GeneratedValue(generator="ex_cash_header_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_cash_header_seq",sequenceName="EX_CASH_HEADER_SEQ",allocationSize=1)
	@Column(name ="CASH_HEADER_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getCashHeaderId() {
		return cashHeaderId;
	}
	public void setCashHeaderId(BigDecimal cashHeaderId) {
		this.cashHeaderId = cashHeaderId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(CountryMaster applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getCompanyId() {
		return companyId;
	}
	public void setCompanyId(CompanyMaster companyId) {
		this.companyId = companyId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Document documentId) {
		this.documentId = documentId;
	}
	
	@Column(name="DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	
	@Column(name="DOCUMENT_FINANCE_YR")
	public BigDecimal getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(BigDecimal financialYear) {
		this.financialYear = financialYear;
	}

	@Column(name="DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(CountryBranch countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	@Column(name="COUNTRY_BRANCH_CODE")
	public BigDecimal getCountryBranchCode() {
		return countryBranchCode;
	}
	public void setCountryBranchCode(BigDecimal countryBranchCode) {
		this.countryBranchCode = countryBranchCode;
	}
	
	@Column(name="DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name="FROM_CASHIER")
	public String getFromCashier() {
		return fromCashier;
	}
	public void setFromCashier(String fromCashier) {
		this.fromCashier = fromCashier;
	}
	
	@Column(name="TO_CASHIER")
	public String getToCashier() {
		return toCashier;
	}
	public void setToCashier(String toCashier) {
		this.toCashier = toCashier;
	}
	
	@Column(name="TO_COUNTRY_BRANCH_CODE")
	public BigDecimal getToCountryBranchCode() {
		return toCountryBranchCode;
	}
	public void setToCountryBranchCode(BigDecimal toCountryBranchCode) {
		this.toCountryBranchCode = toCountryBranchCode;
	}
	
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="RECEIVED_DATE")
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	@Column(name="GL_STATUS")
	public String getGlStatus() {
		return glStatus;
	}
	public void setGlStatus(String glStatus) {
		this.glStatus = glStatus;
	}
	
	@Column(name="GL_DATE")
	public Date getGlDate() {
		return glDate;
	}
	public void setGlDate(Date glDate) {
		this.glDate = glDate;
	}
	
	@Column(name="STOCK_UPDATED")
	public String getStockUpdated() {
		return stockUpdated;
	}
	public void setStockUpdated(String stockUpdated) {
		this.stockUpdated = stockUpdated;
	}
	
	@Column(name="KIOSK_SEQ")
	public BigDecimal getKioskSeq() {
		return kioskSeq;
	}
	public void setKioskSeq(BigDecimal kioskSeq) {
		this.kioskSeq = kioskSeq;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
