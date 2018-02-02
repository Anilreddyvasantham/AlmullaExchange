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
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;

/**
 * Author Rahamathali Shaik
 */

@Entity
@Table(name="EX_CASH_DETAILS")
public class CashDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal cashDetailsId;
	private CashHeader cashHeaderId;
	private CountryMaster applicationCountryId;
	private CompanyMaster companyId;
	private Document documentId;
	private CurrencyMaster currencyId;
	private BigDecimal documentCode;
	private UserFinancialYear documentFinancialYear;
	private BigDecimal documentNo;
	private CountryBranch countryBranchId;
	private BigDecimal countryBranchCode;
	private Date documentDate;
	private BigDecimal documentLineNo;
	private BigDecimal totalValue;
	private BigDecimal fromCdeptCompanyCode;
	private BigDecimal fromCdeptDocumentCode;
	private BigDecimal fromCdeptDocumentFinancialYear;
	private BigDecimal fromCdeptDocumentNo;
	private BigDecimal toCdeptCompanyCode;
	private BigDecimal toCdeptDocumentCode;
	private BigDecimal toCdeptDocumentFinancialYear;
	private BigDecimal toCdeptDocumentNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	
	
	
	public CashDetails() {
	}



	public CashDetails(BigDecimal cashDetailsId, CashHeader cashHeaderId,
			CountryMaster applicationCountryId, CompanyMaster companyId,
			Document documentId, CurrencyMaster currencyId,
			BigDecimal documentCode, UserFinancialYear documentFinancialYear,
			BigDecimal documentNo, CountryBranch countryBranchId,
			BigDecimal countryBranchCode, Date documentDate,
			BigDecimal documentLineNo, BigDecimal totalValue,
			BigDecimal fromCdeptCompanyCode, BigDecimal fromCdeptDocumentCode,
			BigDecimal fromCdeptDocumentFinancialYear,
			BigDecimal fromCdeptDocumentNo, BigDecimal toCdeptCompanyCode,
			BigDecimal toCdeptDocumentCode,
			BigDecimal toCdeptDocumentFinancialYear,
			BigDecimal toCdeptDocumentNo, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, String isActive) {
		this.cashDetailsId = cashDetailsId;
		this.cashHeaderId = cashHeaderId;
		this.applicationCountryId = applicationCountryId;
		this.companyId = companyId;
		this.documentId = documentId;
		this.currencyId = currencyId;
		this.documentCode = documentCode;
		this.documentFinancialYear = documentFinancialYear;
		this.documentNo = documentNo;
		this.countryBranchId = countryBranchId;
		this.countryBranchCode = countryBranchCode;
		this.documentDate = documentDate;
		this.documentLineNo = documentLineNo;
		this.totalValue = totalValue;
		this.fromCdeptCompanyCode = fromCdeptCompanyCode;
		this.fromCdeptDocumentCode = fromCdeptDocumentCode;
		this.fromCdeptDocumentFinancialYear = fromCdeptDocumentFinancialYear;
		this.fromCdeptDocumentNo = fromCdeptDocumentNo;
		this.toCdeptCompanyCode = toCdeptCompanyCode;
		this.toCdeptDocumentCode = toCdeptDocumentCode;
		this.toCdeptDocumentFinancialYear = toCdeptDocumentFinancialYear;
		this.toCdeptDocumentNo = toCdeptDocumentNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
	}


	@Id
	@GeneratedValue(generator="ex_cash_details_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_cash_details_seq",sequenceName="EX_CASH_DETAILS_SEQ",allocationSize=1)
	@Column(name ="CASH_DETAILS_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getCashDetailsId() {
		return cashDetailsId;
	}

	public void setCashDetailsId(BigDecimal cashDetailsId) {
		this.cashDetailsId = cashDetailsId;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CASH_HEADER_ID")
	public CashHeader getCashHeaderId() {
		return cashHeaderId;
	}



	public void setCashHeaderId(CashHeader cashHeaderId) {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCurrencyId() {
		return currencyId;
	}



	public void setCurrencyId(CurrencyMaster currencyId) {
		this.currencyId = currencyId;
	}


	@Column(name="DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}



	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}


	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_FINANCE_YR")
	public UserFinancialYear getDocumentFinancialYear() {
		return documentFinancialYear;
	}



	public void setDocumentFinancialYear(UserFinancialYear documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
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


	@Column(name="DOCUMENT_LNO")
	public BigDecimal getDocumentLineNo() {
		return documentLineNo;
	}



	public void setDocumentLineNo(BigDecimal documentLineNo) {
		this.documentLineNo = documentLineNo;
	}


	@Column(name="TOTAL_VALUE")
	public BigDecimal getTotalValue() {
		return totalValue;
	}



	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	@Column(name="FR_CDEP_COMPANY_CODE")
	public BigDecimal getFromCdeptCompanyCode() {
		return fromCdeptCompanyCode;
	}



	public void setFromCdeptCompanyCode(BigDecimal fromCdeptCompanyCode) {
		this.fromCdeptCompanyCode = fromCdeptCompanyCode;
	}


	@Column(name="FR_CDEP_DOCUMENT_CODE")
	public BigDecimal getFromCdeptDocumentCode() {
		return fromCdeptDocumentCode;
	}



	public void setFromCdeptDocumentCode(BigDecimal fromCdeptDocumentCode) {
		this.fromCdeptDocumentCode = fromCdeptDocumentCode;
	}


	@Column(name="FR_CDEP_DOCUMENT_FIN_YEAR")
	public BigDecimal getFromCdeptDocumentFinancialYear() {
		return fromCdeptDocumentFinancialYear;
	}



	public void setFromCdeptDocumentFinancialYear(
			BigDecimal fromCdeptDocumentFinancialYear) {
		this.fromCdeptDocumentFinancialYear = fromCdeptDocumentFinancialYear;
	}


	@Column(name="FR_CDEP_DOCUMENT_NO")
	public BigDecimal getFromCdeptDocumentNo() {
		return fromCdeptDocumentNo;
	}



	public void setFromCdeptDocumentNo(BigDecimal fromCdeptDocumentNo) {
		this.fromCdeptDocumentNo = fromCdeptDocumentNo;
	}


	@Column(name="TO_CDEP_COMPANY_CODE")
	public BigDecimal getToCdeptCompanyCode() {
		return toCdeptCompanyCode;
	}



	public void setToCdeptCompanyCode(BigDecimal toCdeptCompanyCode) {
		this.toCdeptCompanyCode = toCdeptCompanyCode;
	}


	@Column(name="TO_CDEP_DOCUMENT_CODE")
	public BigDecimal getToCdeptDocumentCode() {
		return toCdeptDocumentCode;
	}



	public void setToCdeptDocumentCode(BigDecimal toCdeptDocumentCode) {
		this.toCdeptDocumentCode = toCdeptDocumentCode;
	}


	@Column(name="TO_CDEP_DOCUMENT_FIN_YEAR")
	public BigDecimal getToCdeptDocumentFinancialYear() {
		return toCdeptDocumentFinancialYear;
	}



	public void setToCdeptDocumentFinancialYear(
			BigDecimal toCdeptDocumentFinancialYear) {
		this.toCdeptDocumentFinancialYear = toCdeptDocumentFinancialYear;
	}


	@Column(name="TO_CDEP_DOCUMENT_NO")
	public BigDecimal getToCdeptDocumentNo() {
		return toCdeptDocumentNo;
	}



	public void setToCdeptDocumentNo(BigDecimal toCdeptDocumentNo) {
		this.toCdeptDocumentNo = toCdeptDocumentNo;
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


	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}



	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	
	
	
}
