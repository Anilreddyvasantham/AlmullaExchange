package com.amg.exchange.remittance.model;

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
import com.amg.exchange.registration.model.Customer;

@Entity
@Table(name = "EX_AUTHORIZED_LOG")
public class AuthorizedLog implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal authorizedLogId;
	private CountryMaster appCountryId;
	private Customer customerId;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private BigDecimal documentFinancialYear;
	private Date documentDate;
	private Date accountYearMonth;
	private String authorizedType;
	private String authorizedBy;
	private String authorizedPassword;
	private String amlRemarks;
	private Date createdDate;
	private String createdBy;
	private CountryMaster beneCountryId;
	private CompanyMaster companymaster;

	public AuthorizedLog() {

	}

	public AuthorizedLog(BigDecimal authorizedLogId, CountryMaster appCountryId, Customer customerId, BigDecimal documentId, BigDecimal documentNo, BigDecimal documentFinancialYear, Date documentDate, Date accountYearMonth, String authorizedType, String authorizedBy, String authorizedPassword,
			String amlRemarks, Date createdDate, String createdBy, CountryMaster beneCountryId, CompanyMaster companymaster) {
		super();
		this.authorizedLogId = authorizedLogId;
		this.appCountryId = appCountryId;
		this.customerId = customerId;
		this.documentId = documentId;
		this.documentNo = documentNo;
		this.documentFinancialYear = documentFinancialYear;
		this.documentDate = documentDate;
		this.accountYearMonth = accountYearMonth;
		this.authorizedType = authorizedType;
		this.authorizedBy = authorizedBy;
		this.authorizedPassword = authorizedPassword;
		this.amlRemarks = amlRemarks;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.beneCountryId = beneCountryId;
		this.companymaster = companymaster;
	}

	@Id
	@GeneratedValue(generator = "ex_authorized_log_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_authorized_log_seq", sequenceName = "EX_AUTHORIZED_LOG_SEQ", allocationSize = 1)
	@Column(name = "AUTHORIZED_LOG_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getAuthorizedLogId() {
		return authorizedLogId;
	}

	public void setAuthorizedLogId(BigDecimal authorizedLogId) {
		this.authorizedLogId = authorizedLogId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BENE_COUNTRY_ID")
	public CountryMaster getBeneCountryId() {
		return beneCountryId;
	}

	public void setBeneCountryId(CountryMaster beneCountryId) {
		this.beneCountryId = beneCountryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(CountryMaster appCountryId) {
		this.appCountryId = appCountryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
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

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}

	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "ACYYMM")
	public Date getAccountYearMonth() {
		return accountYearMonth;
	}

	public void setAccountYearMonth(Date accountYearMonth) {
		this.accountYearMonth = accountYearMonth;
	}

	@Column(name = "AUTHORIZED_TYPE")
	public String getAuthorizedType() {
		return authorizedType;
	}

	public void setAuthorizedType(String authorizedType) {
		this.authorizedType = authorizedType;
	}

	@Column(name = "AUTHORIZED_BY")
	public String getAuthorizedBy() {
		return authorizedBy;
	}

	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}

	@Column(name = "AUTHORIZED_PASSWORD")
	public String getAuthorizedPassword() {
		return authorizedPassword;
	}

	public void setAuthorizedPassword(String authorizedPassword) {
		this.authorizedPassword = authorizedPassword;
	}

	@Column(name = "AML_REMARKS")
	public String getAmlRemarks() {
		return amlRemarks;
	}

	public void setAmlRemarks(String amlRemarks) {
		this.amlRemarks = amlRemarks;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getCompanymaster() {
		return companymaster;
	}

	public void setCompanymaster(CompanyMaster companymaster) {
		this.companymaster = companymaster;
	}


}
