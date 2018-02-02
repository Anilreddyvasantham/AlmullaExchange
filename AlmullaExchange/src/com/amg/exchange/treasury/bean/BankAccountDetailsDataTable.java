package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BankAccountDetailsDataTable {


	private static final long serialVersionUID = 1L;

	private BigDecimal bankAccountDetId = null;// PK
	private BigDecimal bankId = null;
	private String accountNo = null;
	private String accountType = null;
	public BigDecimal getBankAccountDetId() {
		return bankAccountDetId;
	}
	public void setBankAccountDetId(BigDecimal bankAccountDetId) {
		this.bankAccountDetId = bankAccountDetId;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountDesc() {
		return accountDesc;
	}
	public void setAccountDesc(String accountDesc) {
		this.accountDesc = accountDesc;
	}
	public BigDecimal getCurrency() {
		return currency;
	}
	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}
	public BigDecimal getMinBalance() {
		return minBalance;
	}
	public void setMinBalance(BigDecimal minBalance) {
		this.minBalance = minBalance;
	}
	public BigDecimal getOdLimit() {
		return odLimit;
	}
	public void setOdLimit(BigDecimal odLimit) {
		this.odLimit = odLimit;
	}
	public String getGlNo() {
		return glNo;
	}
	public void setGlNo(String glNo) {
		this.glNo = glNo;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContPersonLocal() {
		return contPersonLocal;
	}
	public void setContPersonLocal(String contPersonLocal) {
		this.contPersonLocal = contPersonLocal;
	}
	public String getDepartmentLocal() {
		return departmentLocal;
	}
	public void setDepartmentLocal(String departmentLocal) {
		this.departmentLocal = departmentLocal;
	}
	public String getDesignationLocal() {
		return designationLocal;
	}
	public void setDesignationLocal(String designationLocal) {
		this.designationLocal = designationLocal;
	}
	public String getFundGlNo() {
		return fundGlNo;
	}
	public void setFundGlNo(String fundGlNo) {
		this.fundGlNo = fundGlNo;
	}
	public BigDecimal getInternalMinBalance() {
		return internalMinBalance;
	}
	public void setInternalMinBalance(BigDecimal internalMinBalance) {
		this.internalMinBalance = internalMinBalance;
	}
	public Boolean getAccountdetail() {
		return accountdetail;
	}
	public void setAccountdetail(Boolean accountdetail) {
		this.accountdetail = accountdetail;
	}
	public Boolean getContactdetail() {
		return contactdetail;
	}
	public void setContactdetail(Boolean contactdetail) {
		this.contactdetail = contactdetail;
	}
	public Boolean getContactdetaillocal() {
		return contactdetaillocal;
	}
	public void setContactdetaillocal(Boolean contactdetaillocal) {
		this.contactdetaillocal = contactdetaillocal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String accountDesc = null;
	private BigDecimal currency = null;
	private BigDecimal minBalance = null;
	private BigDecimal odLimit = null;
	private String glNo = null;
	private BigDecimal countryId = null;
	private String telephone = null;
	private String fax = null;
	private String email = null;
	private String contactPerson = null;
	private String department = null;
	private String designation = null;
	private String mobile = null;
	private String contPersonLocal = null;
	private String departmentLocal = null;
	private String designationLocal = null;
	private String fundGlNo = null;
	private BigDecimal internalMinBalance = null;
	private Boolean accountdetail = true;
	private Boolean contactdetail = false;
	private Boolean contactdetaillocal = false;
}
