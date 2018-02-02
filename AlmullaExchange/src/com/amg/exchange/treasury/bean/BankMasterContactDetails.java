package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BankMasterContactDetails {
	
	private BigDecimal internalContactId = null;
	private String bank = null;
	private BigDecimal bankId = null;
	private String  zone = null;
	private String contactPerson = null;
	private String contactDep = null;
	private String contactDesignation = null;
	private String mobile = null;

	private String localContactPerson = null;
	private String localContactDepartment = null;
	private String localContactDesignation= null;
	private BigDecimal zoneId = null;
	private String zoneName=null;
	private String contactBankCode;
	private Date createdDate;
	private String createdBy;
	
	private Date updatedDate;
	private String updatedBy;
	
	
	
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getContactBankCode() {
		return contactBankCode;
	}
	public void setContactBankCode(String contactBankCode) {
		this.contactBankCode = contactBankCode;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) { 
		this.zone = zone;
	}
	
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	public String getContactDep() {
		return contactDep;
	}
	public void setContactDep(String contactDep) {
		this.contactDep = contactDep;
	}
	
	public String getContactDesignation() {
		return contactDesignation;
	}
	public void setContactDesignation(String contactDesignation) {
		this.contactDesignation = contactDesignation;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getLocalContactPerson() {
		return localContactPerson;
	}
	public void setLocalContactPerson(String localContactPerson) {
		this.localContactPerson = localContactPerson;
	}
	
	public String getLocalContactDepartment() {
		return localContactDepartment;
	}
	public void setLocalContactDepartment(String localContactDepartment) {
		this.localContactDepartment = localContactDepartment;
	}
	
	public String getLocalContactDesignation() {
		return localContactDesignation;
	}
	public void setLocalContactDesignation(String localContactDesignation) {
		this.localContactDesignation = localContactDesignation;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	public BigDecimal getInternalContactId() {
		return internalContactId;
	}
	public void setInternalContactId(BigDecimal internalContactId) {
		this.internalContactId = internalContactId;
	}
	
	
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public BigDecimal getZoneId() {
		return zoneId;
	}
	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}
	
	
	
	public BankMasterContactDetails() {
	}
	public BankMasterContactDetails(BigDecimal internalContactId, String bank,
			BigDecimal bankId, String zone, String contactPerson,
			String contactDep, String contactDesignation, String mobile,
			String localContactPerson, String localContactDepartment,
			String localContactDesignation, BigDecimal zoneId, String zoneName,
			String contactBankCode) {
		super();
		this.internalContactId = internalContactId;
		this.bank = bank;
		this.bankId = bankId;
		this.zone = zone;
		this.contactPerson = contactPerson;
		this.contactDep = contactDep;
		this.contactDesignation = contactDesignation;
		this.mobile = mobile;
		this.localContactPerson = localContactPerson;
		this.localContactDepartment = localContactDepartment;
		this.localContactDesignation = localContactDesignation;
		this.zoneId = zoneId;
		this.zoneName = zoneName;
		this.contactBankCode = contactBankCode;
	}

	
}
