package com.amg.exchange.wuh2h.settlement.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_WU_SUMMARY_REPORT")
public class WUTxnSummaryView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String idNo;;
	private String type;
	private String currencyCode;
	private BigDecimal locationCode;
	private BigDecimal settledCount;
	private BigDecimal settledAmount;
	private BigDecimal unsettledCount;
	private BigDecimal unsettledAmount;
	private Date accountYearMonth;
	private Date createdDate;
	
	
	
	
	@Id
	@Column(name="ID_NO")
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	@Column(name="TYP")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="CURCOD")
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	@Column(name="LOCCOD")
	public BigDecimal getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(BigDecimal locationCode) {
		this.locationCode = locationCode;
	}
	
	@Column(name="SETT_CNT")
	public BigDecimal getSettledCount() {
		return settledCount;
	}
	public void setSettledCount(BigDecimal settledCount) {
		this.settledCount = settledCount;
	}
	
	@Column(name="SETT_AMT")
	public BigDecimal getSettledAmount() {
		return settledAmount;
	}
	public void setSettledAmount(BigDecimal settledAmount) {
		this.settledAmount = settledAmount;
	}
	
	@Column(name="UNSETT_CNT")
	public BigDecimal getUnsettledCount() {
		return unsettledCount;
	}
	public void setUnsettledCount(BigDecimal unsettledCount) {
		this.unsettledCount = unsettledCount;
	}
	
	@Column(name="UNSETT_AMT")
	public BigDecimal getUnsettledAmount() {
		return unsettledAmount;
	}
	public void setUnsettledAmount(BigDecimal unsettledAmount) {
		this.unsettledAmount = unsettledAmount;
	}
	
	@Column(name="ACYYMM")
	public Date getAccountYearMonth() {
		return accountYearMonth;
	}
	public void setAccountYearMonth(Date accountYearMonth) {
		this.accountYearMonth = accountYearMonth;
	}
	
	@Column(name="CRTDAT")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
