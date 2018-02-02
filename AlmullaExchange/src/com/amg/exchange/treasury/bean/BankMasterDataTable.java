package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BankMasterDataTable {


	private BigDecimal bankIdInternal = null;
	private String bankCode = null;
	private String fullName = null;
	private String shortName = null;
	private String languageBank = null;
	private String address1 = null;
	private String address2 = null;
	private BigDecimal currencyId = null;
	private BigDecimal countryId = null;
	private String currencyCode = null;
	private BigDecimal stateId = null;
	private BigDecimal districtId = null;
	private BigDecimal cityId = null;
	private String zip = null;
	private String telephone = null;
	private String fax = null;
	private String email = null;
	private String contactPerson = null;
	private String designation = null;
	private String department = null;
	private String localFullName = null;
	private String localShortname = null;
	private String localAddress1 = null;
	private String localAddress2 = null;
	private String localContactPerson = null;
	private String localDesignation = null;
	private String localDepartment = null;
	private String IFSCLength = null;
	private String reutersBankNname = null;
	private String fileSpecificOrAll = null;
	private String fileBranchWiseYOrN = null;
	private String MICRReuters = null;
	private String remmiterModeYOrN = null;
	private String allowNoBank = null;
	private String createdBy = null;
	private Date createdDate = null;
	private String remarks;
	
	private String isStatus = null;
	private String dynamicLabelForActivateDeactivate=null;
	
	public BigDecimal getBankIdInternal() {
		return bankIdInternal;
	}
	public void setBankIdInternal(BigDecimal bankIdInternal) {
		this.bankIdInternal = bankIdInternal;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getLanguageBank() {
		return languageBank;
	}
	public void setLanguageBank(String languageBank) {
		this.languageBank = languageBank;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getStateId() {
		return stateId;
	}
	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}
	public BigDecimal getDistrictId() {
		return districtId;
	}
	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}
	public BigDecimal getCityId() {
		return cityId;
	}
	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getLocalFullName() {
		return localFullName;
	}
	public void setLocalFullName(String localFullName) {
		this.localFullName = localFullName;
	}
	public String getLocalShortname() {
		return localShortname;
	}
	public void setLocalShortname(String localShortname) {
		this.localShortname = localShortname;
	}
	public String getLocalAddress1() {
		return localAddress1;
	}
	public void setLocalAddress1(String localAddress1) {
		this.localAddress1 = localAddress1;
	}
	public String getLocalAddress2() {
		return localAddress2;
	}
	public void setLocalAddress2(String localAddress2) {
		this.localAddress2 = localAddress2;
	}
	public String getLocalContactPerson() {
		return localContactPerson;
	}
	public void setLocalContactPerson(String localContactPerson) {
		this.localContactPerson = localContactPerson;
	}
	public String getLocalDesignation() {
		return localDesignation;
	}
	public void setLocalDesignation(String localDesignation) {
		this.localDesignation = localDesignation;
	}
	public String getLocalDepartment() {
		return localDepartment;
	}
	public void setLocalDepartment(String localDepartment) {
		this.localDepartment = localDepartment;
	}
	public String getIFSCLength() {
		return IFSCLength;
	}
	public void setIFSCLength(String iFSCLength) {
		IFSCLength = iFSCLength;
	}
	public String getReutersBankNname() {
		return reutersBankNname;
	}
	public void setReutersBankNname(String reutersBankNname) {
		this.reutersBankNname = reutersBankNname;
	}
	public String getFileSpecificOrAll() {
		return fileSpecificOrAll;
	}
	public void setFileSpecificOrAll(String fileSpecificOrAll) {
		this.fileSpecificOrAll = fileSpecificOrAll;
	}
	public String getFileBranchWiseYOrN() {
		return fileBranchWiseYOrN;
	}
	public void setFileBranchWiseYOrN(String fileBranchWiseYOrN) {
		this.fileBranchWiseYOrN = fileBranchWiseYOrN;
	}
	public String getMICRReuters() {
		return MICRReuters;
	}
	public void setMICRReuters(String mICRReuters) {
		MICRReuters = mICRReuters;
	}
	public String getRemmiterModeYOrN() {
		return remmiterModeYOrN;
	}
	public void setRemmiterModeYOrN(String remmiterModeYOrN) {
		this.remmiterModeYOrN = remmiterModeYOrN;
	}
	public String getAllowNoBank() {
		return allowNoBank;
	}
	public void setAllowNoBank(String allowNoBank) {
		this.allowNoBank = allowNoBank;
	}
	public String getIsStatus() {
		return isStatus;
	}
	public void setIsStatus(String isStatus) {
		this.isStatus = isStatus;
	}
	
	public String getDynamicLabelForActivateDeactivate() {
	 	return dynamicLabelForActivateDeactivate;
	 }

	 public void setDynamicLabelForActivateDeactivate(
	 		String dynamicLabelForActivateDeactivate) {
	 	this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
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
	
	private String fetchCreateBy;

	public String getFetchCreateBy() {
		return fetchCreateBy;
	}
	public void setFetchCreateBy(String fetchCreateBy) {
		this.fetchCreateBy = fetchCreateBy;
	}
	 
	private Date fetchCreateDate;

	public Date getFetchCreateDate() {
		return fetchCreateDate;
	}
	public void setFetchCreateDate(Date fetchCreateDate) {
		this.fetchCreateDate = fetchCreateDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
}
