package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CorporateRemittanceBenificaryDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal benificaryAccDetailsId;
	private String benificaryName;
	private String benificaryCountryName;
	private BigDecimal benificaryCountryId;
	private BigDecimal benificaryMasterId;
	private String bankName;
	private BigDecimal bankNameId;
	private String serviceName;
	private BigDecimal serviceNameId;
	private String branchName;
	private BigDecimal branchNameId;
	private String branchLocation;
	private BigDecimal telphoneNum;
	private String location;
	private BigDecimal accountNo;
	private String currencyName;
	private BigDecimal currencyId;
	
	public String getBenificaryName() {
		return benificaryName;
	}
	
	public void setBenificaryName(String benificaryName) {
		this.benificaryName = benificaryName;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public BigDecimal getAccountNo() {
		return accountNo;
	}
	
	public void setAccountNo(BigDecimal accountNo) {
		this.accountNo = accountNo;
	}

	public String getBenificaryCountryName() {
		return benificaryCountryName;
	}

	public void setBenificaryCountryName(String benificaryCountryName) {
		this.benificaryCountryName = benificaryCountryName;
	}

	public BigDecimal getBenificaryCountryId() {
		return benificaryCountryId;
	}

	public void setBenificaryCountryId(BigDecimal benificaryCountryId) {
		this.benificaryCountryId = benificaryCountryId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getBankNameId() {
		return bankNameId;
	}

	public void setBankNameId(BigDecimal bankNameId) {
		this.bankNameId = bankNameId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public BigDecimal getServiceNameId() {
		return serviceNameId;
	}

	public void setServiceNameId(BigDecimal serviceNameId) {
		this.serviceNameId = serviceNameId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getBranchNameId() {
		return branchNameId;
	}

	public void setBranchNameId(BigDecimal branchNameId) {
		this.branchNameId = branchNameId;
	}

	public BigDecimal getTelphoneNum() {
		return telphoneNum;
	}

	public void setTelphoneNum(BigDecimal telphoneNum) {
		this.telphoneNum = telphoneNum;
	}

	public BigDecimal getBenificaryMasterId() {
		return benificaryMasterId;
	}

	public void setBenificaryMasterId(BigDecimal benificaryMasterId) {
		this.benificaryMasterId = benificaryMasterId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getBenificaryAccDetailsId() {
		return benificaryAccDetailsId;
	}

	public void setBenificaryAccDetailsId(BigDecimal benificaryAccDetailsId) {
		this.benificaryAccDetailsId = benificaryAccDetailsId;
	}

	public String getBranchLocation() {
		return branchLocation;
	}

	public void setBranchLocation(String branchLocation) {
		this.branchLocation = branchLocation;
	}
	
	
	
}
