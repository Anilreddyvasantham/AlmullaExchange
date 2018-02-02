package com.amg.exchange.wuh2h.bean;

import java.io.Serializable;

public class WUCorridorCurrencyDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String countryISOCode;
	private String countryNumberCode;
	private String countryDescription;
	private String currencyISOCode;
	private String currencyNumberCode;
	private String currencyDescription;
	public String getCountryISOCode() {
		return countryISOCode;
	}
	public void setCountryISOCode(String countryISOCode) {
		this.countryISOCode = countryISOCode;
	}
	public String getCountryNumberCode() {
		return countryNumberCode;
	}
	public void setCountryNumberCode(String countryNumberCode) {
		this.countryNumberCode = countryNumberCode;
	}
	public String getCountryDescription() {
		return countryDescription;
	}
	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}
	public String getCurrencyISOCode() {
		return currencyISOCode;
	}
	public void setCurrencyISOCode(String currencyISOCode) {
		this.currencyISOCode = currencyISOCode;
	}
	public String getCurrencyNumberCode() {
		return currencyNumberCode;
	}
	public void setCurrencyNumberCode(String currencyNumberCode) {
		this.currencyNumberCode = currencyNumberCode;
	}
	public String getCurrencyDescription() {
		return currencyDescription;
	}
	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}
	
	
	
	
}
