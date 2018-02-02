package com.amg.exchange.wuh2h.settlement.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class WUTransaction implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String currencyCode;
	private BigDecimal locationCode;
	private BigDecimal settledCount;
	private BigDecimal settledAmount;
	private BigDecimal unsettledCount;
	private BigDecimal unsettledAmount;
	private BigDecimal totalCount;
	private BigDecimal totalAmount;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(BigDecimal locationCode) {
		this.locationCode = locationCode;
	}
	public BigDecimal getSettledCount() {
		return settledCount;
	}
	public void setSettledCount(BigDecimal settledCount) {
		this.settledCount = settledCount;
	}
	public BigDecimal getSettledAmount() {
		return settledAmount;
	}
	public void setSettledAmount(BigDecimal settledAmount) {
		this.settledAmount = settledAmount;
	}
	public BigDecimal getUnsettledCount() {
		return unsettledCount;
	}
	public void setUnsettledCount(BigDecimal unsettledCount) {
		this.unsettledCount = unsettledCount;
	}
	public BigDecimal getUnsettledAmount() {
		return unsettledAmount;
	}
	public void setUnsettledAmount(BigDecimal unsettledAmount) {
		this.unsettledAmount = unsettledAmount;
	}
	public BigDecimal getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(BigDecimal totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
