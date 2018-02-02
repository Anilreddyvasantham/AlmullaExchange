package com.amg.exchange.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CurrencyMasterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String currencyCode;
	private BigDecimal currencyId;
	private String currencyDecs;
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyDecs() {
		return currencyDecs;
	}
	public void setCurrencyDecs(String currencyDecs) {
		this.currencyDecs = currencyDecs;
	}
	
	
	

}
