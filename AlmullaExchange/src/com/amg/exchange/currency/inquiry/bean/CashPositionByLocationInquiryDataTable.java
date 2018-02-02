package com.amg.exchange.currency.inquiry.bean;

import java.math.BigDecimal;

public class CashPositionByLocationInquiryDataTable {
	
	
	private BigDecimal branchId;
	private BigDecimal currencyId;
	private BigDecimal cashAmount;
	private BigDecimal nonCashAmount;
	private BigDecimal netAmount;
	private String createdBY;
	private String quoteName;
	private String currencyName;
	private String branchName;
	
	
	

	public CashPositionByLocationInquiryDataTable() {
	}
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}
	public BigDecimal getNonCashAmount() {
		return nonCashAmount;
	}
	public void setNonCashAmount(BigDecimal nonCashAmount) {
		this.nonCashAmount = nonCashAmount;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public String getCreatedBY() {
		return createdBY;
	}
	public void setCreatedBY(String createdBY) {
		this.createdBY = createdBY;
	}
	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	

}
