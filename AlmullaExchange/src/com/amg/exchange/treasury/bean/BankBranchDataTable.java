package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BankBranchDataTable {


	private static final long serialVersionUID = 1L;
	private String branchCode;
	private String fullName;
	private String shortName;
	private String address1;
	private String address2;
	private String zipCode;
	private String telephoneNumber;
	private String fax;
	private String email;
	private String contactPerson;
	
	private String bankFullName;
	
	private String contactdesignation;
	private String contactDepartment;
	private String micrCode;
	private String fullNameLocal;
	private String shortNameLocal;
	private String address1Local;
	private String address2Local;
	private String contactPersonLocal;
	private String contactdesignationLocal;
	private String contactDepartmentLocal;
	private String swift;
	private String debitBranchID;
	private String branchIFSC;
	private String routingID;
	private String mobileNo;
	private BigDecimal bankID;
	
	private Date createddate;
	private String createdby;
	private Boolean booUpdateBranchCode=true;
	private Boolean booSystemBranchCode=false;
	private String dBbranchCode;
	private Boolean editButton;
	private String recordStatus;

	
	
	private String branchNameMsg;

	private BigDecimal countryId;
	private BigDecimal stateId;
	private BigDecimal cityId;
	private BigDecimal districtId;
	
	private String createdBy = null;
	private Date createdDate = null;
	
	private String isStatus = null;
	private String dynamicLabelForActivateDeactivate=null;
	private String bankCode;
	private String location;
	
	private Boolean isSelected=false;
	private BigDecimal branchPk;
	
	private Date modifiedDate;
	private String modifiedBy;
	private String remarks;
		
	public BigDecimal getBranchPk() {
		return branchPk;
	}
	public void setBranchPk(BigDecimal branchPk) {
		this.branchPk = branchPk;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
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
	public String getContactdesignation() {
		return contactdesignation;
	}
	public void setContactdesignation(String contactdesignation) {
		this.contactdesignation = contactdesignation;
	}
	public String getContactDepartment() {
		return contactDepartment;
	}
	public void setContactDepartment(String contactDepartment) {
		this.contactDepartment = contactDepartment;
	}
	public String getMicrCode() {
		return micrCode;
	}
	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}
	public String getFullNameLocal() {
		return fullNameLocal;
	}
	public void setFullNameLocal(String fullNameLocal) {
		this.fullNameLocal = fullNameLocal;
	}
	public String getShortNameLocal() {
		return shortNameLocal;
	}
	public void setShortNameLocal(String shortNameLocal) {
		this.shortNameLocal = shortNameLocal;
	}
	public String getAddress1Local() {
		return address1Local;
	}
	public void setAddress1Local(String address1Local) {
		this.address1Local = address1Local;
	}
	public String getAddress2Local() {
		return address2Local;
	}
	public void setAddress2Local(String address2Local) {
		this.address2Local = address2Local;
	}
	public String getContactPersonLocal() {
		return contactPersonLocal;
	}
	public void setContactPersonLocal(String contactPersonLocal) {
		this.contactPersonLocal = contactPersonLocal;
	}
	public String getContactdesignationLocal() {
		return contactdesignationLocal;
	}
	public void setContactdesignationLocal(String contactdesignationLocal) {
		this.contactdesignationLocal = contactdesignationLocal;
	}
	public String getContactDepartmentLocal() {
		return contactDepartmentLocal;
	}
	public void setContactDepartmentLocal(String contactDepartmentLocal) {
		this.contactDepartmentLocal = contactDepartmentLocal;
	}
	public String getSwift() {
		return swift;
	}
	public void setSwift(String swift) {
		this.swift = swift;
	}
	public String getDebitBranchID() {
		return debitBranchID;
	}
	public void setDebitBranchID(String debitBranchID) {
		this.debitBranchID = debitBranchID;
	}
	public String getBranchIFSC() {
		return branchIFSC;
	}
	public void setBranchIFSC(String branchIFSC) {
		this.branchIFSC = branchIFSC;
	}
	public String getRoutingID() {
		return routingID;
	}
	public void setRoutingID(String routingID) {
		this.routingID = routingID;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public BigDecimal getBankID() {
		return bankID;
	}
	public void setBankID(BigDecimal bankID) {
		this.bankID = bankID;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Boolean getBooUpdateBranchCode() {
		return booUpdateBranchCode;
	}
	public void setBooUpdateBranchCode(Boolean booUpdateBranchCode) {
		this.booUpdateBranchCode = booUpdateBranchCode;
	}
	public Boolean getBooSystemBranchCode() {
		return booSystemBranchCode;
	}
	public void setBooSystemBranchCode(Boolean booSystemBranchCode) {
		this.booSystemBranchCode = booSystemBranchCode;
	}
	public String getdBbranchCode() {
		return dBbranchCode;
	}
	public void setdBbranchCode(String dBbranchCode) {
		this.dBbranchCode = dBbranchCode;
	}
	public Boolean getEditButton() {
		return editButton;
	}
	public void setEditButton(Boolean editButton) {
		this.editButton = editButton;
	}
	public String getBranchNameMsg() {
		return branchNameMsg;
	}
	public void setBranchNameMsg(String branchNameMsg) {
		this.branchNameMsg = branchNameMsg;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public BigDecimal getStateId() {
		return stateId;
	}
	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}
	public BigDecimal getCityId() {
		return cityId;
	}
	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}
	public BigDecimal getDistrictId() {
		return districtId;
	}
	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBankFullName() {
		return bankFullName;
	}
	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
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
	
	private String approvedBy;
	private Date approvedDate;

	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
