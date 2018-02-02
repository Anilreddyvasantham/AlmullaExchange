package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.amg.exchange.util.Constants;

public class RoutingSetUpDetailsTable {
	
	private BigDecimal routingSetupDetailsId;
	private String bankCountryName;
	private BigDecimal bankCountryId;
	private String bankName;
	private BigDecimal bankId;
	private String currencyName;
	private BigDecimal currencyId;
	private String isActive = Constants.Yes;
	private String createdBy;
	private Date createdDate;
	
	public String getBankCountryName() {
		return bankCountryName;
	}
	
	public void setBankCountryName(String bankCountryName) {
		this.bankCountryName = bankCountryName;
	}
	
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
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
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public BigDecimal getRoutingSetupDetailsId() {
		return routingSetupDetailsId;
	}

	public void setRoutingSetupDetailsId(BigDecimal routingSetupDetailsId) {
		this.routingSetupDetailsId = routingSetupDetailsId;
	}

}
