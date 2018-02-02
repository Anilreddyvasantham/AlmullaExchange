package com.amg.exchange.beneficiary.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_BANK_BRANCH")
public class BankBranchView implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "BANK_BRANCH_ID")
	private BigDecimal bankBranchId;
	@Column(name = "ADDRESS1")
	private String address1;
	@Column(name = "ADDRESS2")
	private String address2;
	@Column(name = "BANK_ID")
	private BigDecimal bankId;
	@Column(name = "BRANCH_CODE")
	private BigDecimal branchCode;
	@Column(name = "BRANCH_FULL_NAME")
	private String branchFullName;
	@Column(name = "CITY_ID")
	private BigDecimal cityId;
	@Column(name = "STATE_ID")
	private BigDecimal stateId;
	@Column(name = "DISTRICT_ID")
	private BigDecimal districtId;
	@Column(name = "CITY_NAME")
	private String cityName;
	@Column(name = "STATE_NAME")
	private String stateName;
	@Column(name = "DISTRICT_NAME")
	private String districtName;
	@Column(name = "SWIFT_BIC")
	private String swift;
	@Column(name = "ZIP_CODE")
	private String zipcode;
	@Column(name = "IFSC")
	private String ifscCode;
	@Column(name = "DEBIT_BRANC_ID")
	private String debitBranchId;
	@Column(name = "ROUTING_ID")
	private String routingId;
	@Column(name = "COUNTRY_NAME")
	private String countryName;
	@Column(name = "LOCATION")
	private String location;
	@Column(name = "COUNTRY_ID")
	private BigDecimal countryId;
	@Column(name = "TELEPHONE_NO")
	private String telephoneNo;
	@Column(name = "FAX_NO")
	private String faxNo;
	@Column(name = "EMAIL")
	private String email;
	

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
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

	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchFullName() {
		return branchFullName;
	}
	public void setBranchFullName(String branchFullName) {
		this.branchFullName = branchFullName;
	}

	public BigDecimal getCityId() {
		return cityId;
	}
	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
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

	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getSwift() {
		return swift;
	}
	public void setSwift(String swift) {
		this.swift = swift;
	}

	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getDebitBranchId() {
		return debitBranchId;
	}
	public void setDebitBranchId(String debitBranchId) {
		this.debitBranchId = debitBranchId;
	}
	
	public String getRoutingId() {
		return routingId;
	}
	public void setRoutingId(String routingId) {
		this.routingId = routingId;
	}
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "BankBranchView [bankBranchId=" + bankBranchId + ", address1=" + address1 + ", address2=" + address2 + ", bankId=" + bankId + ", branchCode=" + branchCode + ", branchFullName=" + branchFullName + ", cityId=" + cityId + ", stateId=" + stateId + ", districtId=" + districtId
				+ ", cityName=" + cityName + ", stateName=" + stateName + ", districtName=" + districtName + ", swift=" + swift + ", zipcode=" + zipcode + ", ifscCode=" + ifscCode + "]";
	}
	
	
	
	
}
