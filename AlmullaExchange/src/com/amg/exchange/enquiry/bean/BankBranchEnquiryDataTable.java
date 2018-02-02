package com.amg.exchange.enquiry.bean;

import java.math.BigDecimal;

public class BankBranchEnquiryDataTable {

	private BigDecimal branchCode;
	private String branchName;
	private String ifscCode;
	private BigDecimal countryId;
	private BigDecimal cityId;
	private BigDecimal distrctId;
	private BigDecimal stateId;
	private String countryName;
	private String cityName;
	private String distictName;
	private String stateName;
	private String zipCode;
	private String telephoneNumnber;
	private String contactPerson;
	private String status;
	
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public BigDecimal getCityId() {
		return cityId;
	}
	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}
	public BigDecimal getDistrctId() {
		return distrctId;
	}
	public void setDistrctId(BigDecimal distrctId) {
		this.distrctId = distrctId;
	}
	public BigDecimal getStateId() {
		return stateId;
	}
	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistictName() {
		return distictName;
	}
	public void setDistictName(String distictName) {
		this.distictName = distictName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getTelephoneNumnber() {
		return telephoneNumnber;
	}
	public void setTelephoneNumnber(String telephoneNumnber) {
		this.telephoneNumnber = telephoneNumnber;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	
	
}
