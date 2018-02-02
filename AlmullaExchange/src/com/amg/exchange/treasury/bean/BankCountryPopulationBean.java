package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;

public class BankCountryPopulationBean {
	
	private BigDecimal bankCountryId = null;
	private String bankCountryName = null;
	private String bankCountryCode = null;
	
	public BankCountryPopulationBean(BigDecimal bankCountryId, String bankCountryName,String bankCountryCode) {
		this.bankCountryId = bankCountryId;
		this.bankCountryName = bankCountryName;
		this.bankCountryCode= bankCountryCode;
	}
	
	
	 

	  /*public BankCountryPopulationBean(BigDecimal bankCountryId, String bankCountryName) {
		    this.bankCountryId = bankCountryId;
		    this.bankCountryName = bankCountryName;
	  }*/




public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	public String getBankCountryName() {
		return bankCountryName;
	}
	public void setBankCountryName(String bankCountryName) {
		this.bankCountryName = bankCountryName;
	}

	  public String getBankCountryCode() {
		    return bankCountryCode;
	  }

	  public void setBankCountryCode(String bankCountryCode) {
		    this.bankCountryCode = bankCountryCode;
	  }
	
	
}
