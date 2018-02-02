package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BankApplicabilityDataTable {


	private static final long serialVersionUID = -3943860350339577651L;

	// TODO
	private BigDecimal countryId;
	private String timeZone;
	private BigDecimal companyId;
	private BigDecimal bankId;
	private BigDecimal bankBranchId;
	private String exchangeId;
	private BigDecimal bankTypeId;
	private String userName;
	private String password;
	private String agentId;
	private String agentPin;
	private BigDecimal bankApplicabilityId;
	
	private String createdBy;
	private Date createdDate;
	private String isActive;
	
	private String companyName;
	private String bankTypeName;
	private String bankName;
	private String countryName;
	private String approvedBy;
	private Date approvedDate;
	
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

	private String modifiedBy;
	private Date modifiedDate;
	
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}
	public String getExchangeId() {
		return exchangeId;
	}
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
	public BigDecimal getBankTypeId() {
		return bankTypeId;
	}
	public void setBankTypeId(BigDecimal bankTypeId) {
		this.bankTypeId = bankTypeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentPin() {
		return agentPin;
	}
	public void setAgentPin(String agentPin) {
		this.agentPin = agentPin;
	}
	public BigDecimal getBankApplicabilityId() {
		return bankApplicabilityId;
	}
	public void setBankApplicabilityId(BigDecimal bankApplicabilityId) {
		this.bankApplicabilityId = bankApplicabilityId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBankTypeName() {
		return bankTypeName;
	}
	public void setBankTypeName(String bankTypeName) {
		this.bankTypeName = bankTypeName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
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

	//added koti
	
	private String bankCode;
	
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	
	
	
}
