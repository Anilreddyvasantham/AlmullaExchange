package com.amg.exchange.currency.inquiry.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_CASH_BALANCE")
public class CashPositionByLocationView implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal idNo;
	private BigDecimal branchId;
	private BigDecimal currencyId;
	private BigDecimal cashAmount;
	private BigDecimal nonCashAmount;
	private BigDecimal netAmount;
	private String createdBY;
	private String quoteName;
	private String currencyName;
	private String branchName;
	
	@Id
	@Column(name="IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	@Column(name = "BRANCH_ID")
	public BigDecimal getBranchId() {
		return branchId;
	}
	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	@Column(name = "CASH_AMOUNT")
	public BigDecimal getCashAmount() {
		return cashAmount;
	}
	@Column(name = "NON_CASH_AMOUNT")
	public BigDecimal getNonCashAmount() {
		return nonCashAmount;
	}
	@Column(name = "NET_AMOUNT")
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	@Column(name = "CREATED_BY")
	public String getCreatedBY() {
		return createdBY;
	}
	@Column(name = "CURRENCY_NAME")
	public String getCurrencyName() {
		return currencyName;
	}
	@Column(name = "QUOTE_NAME")
	public String getQuoteName() {
		return quoteName;
	}
	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}
	public void setNonCashAmount(BigDecimal nonCashAmount) {
		this.nonCashAmount = nonCashAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public void setCreatedBY(String createdBY) {
		this.createdBY = createdBY;
	}
	
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	
	

}
