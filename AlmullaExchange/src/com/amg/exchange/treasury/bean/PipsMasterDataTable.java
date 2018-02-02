package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PipsMasterDataTable {
	
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal bankId;
	private String bankName;
	private BigDecimal serviceId;
	private String serviceName;
	private BigDecimal branchId;
	private String branchName;
	private String countryName;
	private String currencyName;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private BigDecimal pips;
	private BigDecimal pipsMasterPk;
	private String createdBy;
	private Date createdDate;
	private String ModifiedBy;
	private Date modifiedDate;
	private String isActive;
	
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
		return ModifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getServiceId() {
		return serviceId;
	}
	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public BigDecimal getFromAmount() {
		return fromAmount;
	}
	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}
	public BigDecimal getToAmount() {
		return toAmount;
	}
	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}
	public BigDecimal getPips() {
		return pips;
	}
	public void setPips(BigDecimal pips) {
		this.pips = pips;
	}
	public BigDecimal getPipsMasterPk() {
		return pipsMasterPk;
	}
	public void setPipsMasterPk(BigDecimal pipsMasterPk) {
		this.pipsMasterPk = pipsMasterPk;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	private String pipsTypeCode;
	private String pipsTypeFullName;

	public String getPipsTypeCode() {
		return pipsTypeCode;
	}
	public void setPipsTypeCode(String pipsTypeCode) {
		this.pipsTypeCode = pipsTypeCode;
	}
	public String getPipsTypeFullName() {
		return pipsTypeFullName;
	}
	public void setPipsTypeFullName(String pipsTypeFullName) {
		this.pipsTypeFullName = pipsTypeFullName;
	}
	
	

	
	
	
}
