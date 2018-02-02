package com.amg.exchange.currency.inquiry.bean;

import java.math.BigDecimal;
import java.util.Date;

public class StockDataTable {

	private BigDecimal countryBranchId;
	private String username;
	private String denominationDesc;

	private BigDecimal stockId;
	private Date logDate;
	private String accountYearMonth;
	private BigDecimal currencyId;

	private BigDecimal denominationId;
	private BigDecimal openQuantity;
	private BigDecimal purchaseQuantity;
	private BigDecimal saleQuantity;
	private BigDecimal transactionQuantity;
	private BigDecimal receivedQuantity;
	private BigDecimal closureQuantity;

	private BigDecimal fcAmount;
	private BigDecimal totalFcAmount;

	private String employeeName;
	private String branchName;
	private String denomonationDesc;
	private BigDecimal denominationAmount;
	private String currencyName;

	public BigDecimal getStockId() {
		return stockId;
	}

	public void setStockId(BigDecimal stockId) {
		this.stockId = stockId;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getAccountYearMonth() {
		return accountYearMonth;
	}

	public void setAccountYearMonth(String accountYearMonth) {
		this.accountYearMonth = accountYearMonth;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getDenominationId() {
		return denominationId;
	}

	public void setDenominationId(BigDecimal denominationId) {
		this.denominationId = denominationId;
	}

	public BigDecimal getOpenQuantity() {
		return openQuantity;
	}

	public void setOpenQuantity(BigDecimal openQuantity) {
		this.openQuantity = openQuantity;
	}

	public BigDecimal getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(BigDecimal purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public BigDecimal getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(BigDecimal saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	public BigDecimal getTransactionQuantity() {
		return transactionQuantity;
	}

	public void setTransactionQuantity(BigDecimal transactionQuantity) {
		this.transactionQuantity = transactionQuantity;
	}

	public BigDecimal getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(BigDecimal receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public BigDecimal getClosureQuantity() {
		return closureQuantity;
	}

	public void setClosureQuantity(BigDecimal closureQuantity) {
		this.closureQuantity = closureQuantity;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDenomonationDesc() {
		return denomonationDesc;
	}

	public void setDenomonationDesc(String denomonationDesc) {
		this.denomonationDesc = denomonationDesc;
	}

	public BigDecimal getDenominationAmount() {
		return denominationAmount;
	}

	public void setDenominationAmount(BigDecimal denominationAmount) {
		this.denominationAmount = denominationAmount;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDenominationDesc() {
		return denominationDesc;
	}

	public void setDenominationDesc(String denominationDesc) {
		this.denominationDesc = denominationDesc;
	}

	public BigDecimal getFcAmount() {
		return fcAmount;
	}

	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	public BigDecimal getTotalFcAmount() {
		return totalFcAmount;
	}

	public void setTotalFcAmount(BigDecimal totalFcAmount) {
		this.totalFcAmount = totalFcAmount;
	}

}
