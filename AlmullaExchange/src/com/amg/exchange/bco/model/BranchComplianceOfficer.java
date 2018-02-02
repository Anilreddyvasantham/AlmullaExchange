package com.amg.exchange.bco.model;

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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;

@Entity
@Table(name = "EX_AML_CHECK")
public class BranchComplianceOfficer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal amlCheckId;
	private CountryBranch countryBranchId;
	private Customer customerId;
	private CountryMaster applicationCountryId;
	private String customerReferenceCode;
	private String amlCode;
	private BigDecimal amlNumberOfHits;
	private Date amlClearDate;
	private String amlClearBy;
	private String accountYearMonth;
	private String isActive;
	private String remarks;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	
	@Id
	@GeneratedValue(generator = "ex_aml_check_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_aml_check_seq", sequenceName = "EX_AML_CHECK_SEQ", allocationSize = 1)
	@Column(name = "AML_CHECK_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getAmlCheckId() {
		return amlCheckId;
	}

	public void setAmlCheckId(BigDecimal amlCheckId) {
		this.amlCheckId = amlCheckId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_BRANCH_ID")
	public CountryBranch getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(CountryBranch countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(CountryMaster applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "CUSTOMER_REFERENCE_CODE")
	public String getCustomerReferenceCode() {
		return customerReferenceCode;
	}

	public void setCustomerReferenceCode(String customerReferenceCode) {
		this.customerReferenceCode = customerReferenceCode;
	}

	@Column(name = "AML_CODE")
	public String getAmlCode() {
		return amlCode;
	}

	public void setAmlCode(String amlCode) {
		this.amlCode = amlCode;
	}

	@Column(name = "AML_NO_OF_HITS")
	public BigDecimal getAmlNumberOfHits() {
		return amlNumberOfHits;
	}

	public void setAmlNumberOfHits(BigDecimal amlNumberOfHits) {
		this.amlNumberOfHits = amlNumberOfHits;
	}

	@Column(name = "AML_CLEAR_DATE")
	public Date getAmlClearDate() {
		return amlClearDate;
	}

	public void setAmlClearDate(Date amlClearDate) {
		this.amlClearDate = amlClearDate;
	}

	@Column(name = "ACCOUNT_YEAR_MONTH")
	public String getAccountYearMonth() {
		return accountYearMonth;
	}

	public void setAccountYearMonth(String accountYearMonth) {
		this.accountYearMonth = accountYearMonth;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "AML_CLEAR_BY")
	public String getAmlClearBy() {
		return amlClearBy;
	}

	public void setAmlClearBy(String amlClearBy) {
		this.amlClearBy = amlClearBy;
	}

}
