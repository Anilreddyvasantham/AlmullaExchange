package com.amg.exchange.bco.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BranchComplianceDataTable {

	private BigDecimal amlCheckId;
	private BigDecimal countryBranchId;
	private BigDecimal customerId;
	private BigDecimal applicationCountryId;
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
	private String nationality;
	private BigDecimal countryId;
	private String idNumber;
	private String idType;
	private String passport;
	private String idTypeDesc;

	private Map<BigDecimal, String> idTypeMap = new HashMap<BigDecimal, String>();

	public BranchComplianceDataTable() {

	}

	public BranchComplianceDataTable(BigDecimal amlCheckId,
			BigDecimal countryBranchId, BigDecimal customerId,
			BigDecimal applicationCountryId, String customerReferenceCode,
			String amlCode, BigDecimal amlNumberOfHits, Date amlClearDate,
			String amlClearBy, String accountYearMonth, String isActive,
			String remarks, String createdBy, Date createdDate,
			String modifiedBy, String nationality, BigDecimal countryId,
			String idNumber, String idType, String passport,
			Map<BigDecimal, String> idTypeMap, Date modifiedDate,
			String firstName, String branchName, String idTypeDesc) {
		super();
		this.amlCheckId = amlCheckId;
		this.countryBranchId = countryBranchId;
		this.customerId = customerId;
		this.applicationCountryId = applicationCountryId;
		this.customerReferenceCode = customerReferenceCode;
		this.amlCode = amlCode;
		this.amlNumberOfHits = amlNumberOfHits;
		this.amlClearDate = amlClearDate;
		this.amlClearBy = amlClearBy;
		this.accountYearMonth = accountYearMonth;
		this.isActive = isActive;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.nationality = nationality;
		this.countryId = countryId;
		this.idNumber = idNumber;
		this.idType = idType;
		this.passport = passport;
		this.idTypeMap = idTypeMap;
		this.modifiedDate = modifiedDate;
		this.firstName = firstName;
		this.branchName = branchName;
		this.idTypeDesc = idTypeDesc;
	}

	private Date modifiedDate;
	private String firstName;
	private String branchName;

	// private Boolean selected;

	public BigDecimal getAmlCheckId() {
		return amlCheckId;
	}

	public void setAmlCheckId(BigDecimal amlCheckId) {
		this.amlCheckId = amlCheckId;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public String getCustomerReferenceCode() {
		return customerReferenceCode;
	}

	public void setCustomerReferenceCode(String customerReferenceCode) {
		this.customerReferenceCode = customerReferenceCode;
	}

	public String getAmlCode() {
		return amlCode;
	}

	public void setAmlCode(String amlCode) {
		this.amlCode = amlCode;
	}

	public BigDecimal getAmlNumberOfHits() {
		return amlNumberOfHits;
	}

	public void setAmlNumberOfHits(BigDecimal amlNumberOfHits) {
		this.amlNumberOfHits = amlNumberOfHits;
	}

	public Date getAmlClearDate() {
		return amlClearDate;
	}

	public void setAmlClearDate(Date amlClearDate) {
		this.amlClearDate = amlClearDate;
	}

	public String getAccountYearMonth() {
		return accountYearMonth;
	}

	public void setAccountYearMonth(String accountYearMonth) {
		this.accountYearMonth = accountYearMonth;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAmlClearBy() {
		return amlClearBy;
	}

	public void setAmlClearBy(String amlClearBy) {
		this.amlClearBy = amlClearBy;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public Map<BigDecimal, String> getIdTypeMap() {
		return idTypeMap;
	}

	public void setIdTypeMap(Map<BigDecimal, String> idTypeMap) {
		this.idTypeMap = idTypeMap;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdTypeDesc() {
		return idTypeDesc;
	}

	public void setIdTypeDesc(String idTypeDesc) {
		this.idTypeDesc = idTypeDesc;
	}

	/*
	 * public Boolean getSelected() { return selected; }
	 * 
	 * public void setSelected(Boolean selected) { this.selected = selected; }
	 */

}
