package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;

public class BankCurrencyPopulateBean {
	
	private BigDecimal currencyId = null;
	private String currencyName =null;
	
	public BankCurrencyPopulateBean(BigDecimal currencyId, String currencyName) {
		this.setCurrencyId(currencyId);
		this.setCurrencyName(currencyName);
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	
	
	
}
