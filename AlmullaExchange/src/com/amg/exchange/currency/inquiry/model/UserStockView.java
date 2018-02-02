package com.amg.exchange.currency.inquiry.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_STOCK")
public class UserStockView implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal stockId;
	private String oracleUser;
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

	private BigDecimal countryBranchId;
	private String employeeName;
	private String branchName;
	private String denomonationDesc;
	private BigDecimal denominationAmount;
	private String currencyName;
	private String currencyCode;
	private BigDecimal fcAmount;

	public UserStockView() {
	}

	public UserStockView(BigDecimal stockId, String oracleUser, Date logDate,
			String accountYearMonth, BigDecimal currencyId,
			BigDecimal denominationId, BigDecimal openQuantity,
			BigDecimal purchaseQuantity, BigDecimal saleQuantity,
			BigDecimal transactionQuantity, BigDecimal receivedQuantity,
			BigDecimal closureQuantity, BigDecimal countryBranchId,
			String employeeName, String branchName, String denomonationDesc,
			BigDecimal denominationAmount,BigDecimal fcAmount) {
		super();
		this.stockId = stockId;
		this.oracleUser = oracleUser;
		this.logDate = logDate;
		this.accountYearMonth = accountYearMonth;
		this.currencyId = currencyId;
		this.denominationId = denominationId;
		this.openQuantity = openQuantity;
		this.purchaseQuantity = purchaseQuantity;
		this.saleQuantity = saleQuantity;
		this.transactionQuantity = transactionQuantity;
		this.receivedQuantity = receivedQuantity;
		this.closureQuantity = closureQuantity;
		this.countryBranchId = countryBranchId;
		this.employeeName = employeeName;
		this.branchName = branchName;
		this.denomonationDesc = denomonationDesc;
		this.denominationAmount = denominationAmount;
		this.fcAmount = fcAmount;
	}

	@Id
	@Column(name = "STOCK_ID")
	public BigDecimal getStockId() {
		return stockId;
	}

	public void setStockId(BigDecimal stockId) {
		this.stockId = stockId;
	}

	@Column(name = "ORACLE_USER")
	public String getOracleUser() {
		return oracleUser;
	}

	public void setOracleUser(String oracleUser) {
		this.oracleUser = oracleUser;
	}

	@Column(name = "LOG_DATE")
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	@Column(name = "ACCOUNT_MMYYYY")
	public String getAccountYearMonth() {
		return accountYearMonth;
	}

	public void setAccountYearMonth(String accountYearMonth) {
		this.accountYearMonth = accountYearMonth;
	}

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "DENOMINATION_ID")
	public BigDecimal getDenominationId() {
		return denominationId;
	}

	public void setDenominationId(BigDecimal denominationId) {
		this.denominationId = denominationId;
	}

	@Column(name = "OPEN_QTY")
	public BigDecimal getOpenQuantity() {
		return openQuantity;
	}

	public void setOpenQuantity(BigDecimal openQuantity) {
		this.openQuantity = openQuantity;
	}

	@Column(name = "PURCHASE_QTY")
	public BigDecimal getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(BigDecimal purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	@Column(name = "SALE_QTY")
	public BigDecimal getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(BigDecimal saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	//@Column(name = "TRANF_QTY")
	@Column(name = "TRANX_QTY")
	public BigDecimal getTransactionQuantity() {
		return transactionQuantity;
	}

	public void setTransactionQuantity(BigDecimal transactionQuantity) {
		this.transactionQuantity = transactionQuantity;
	}

	@Column(name = "RECEIVED_QTY")
	public BigDecimal getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(BigDecimal receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	@Column(name = "CLOSER_QTY")
	public BigDecimal getClosureQuantity() {
		return closureQuantity;
	}

	public void setClosureQuantity(BigDecimal closureQuantity) {
		this.closureQuantity = closureQuantity;
	}

	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@Column(name = "EMPLOYEE_NAME")
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "DENOMINATION_DESC")
	public String getDenomonationDesc() {
		return denomonationDesc;
	}

	public void setDenomonationDesc(String denomonationDesc) {
		this.denomonationDesc = denomonationDesc;
	}

	@Column(name = "DENOMINATION_AMOUNT")
	public BigDecimal getDenominationAmount() {
		return denominationAmount;
	}

	public void setDenominationAmount(BigDecimal denominationAmount) {
		this.denominationAmount = denominationAmount;
	}

	@Column(name = "CURRENCY_NAME")
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	@Column(name = "FC_AMOUNT")
	public BigDecimal getFcAmount() {
		return fcAmount;
	}

	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	@Column(name = "CURRENCY_CODE")
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	

}
