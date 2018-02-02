package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;

public class CountryCurrencyPopulationBean {

	private BigDecimal applicationCountryId = new BigDecimal(0);
	private BigDecimal bankCountryId = new BigDecimal(0);
	private BigDecimal currencyId = new BigDecimal(0);
	private String currencyName = new String();
	private String arabicCurrencyName = new String();
	private String currencyCode;

	/*public CountryCurrencyPopulationBean(BigDecimal currencyId, String currencyName) {
		this.currencyId = currencyId;
		this.currencyName = currencyName;
	}*/
	
	public CountryCurrencyPopulationBean(BigDecimal currencyId,String currencyName, String currencyCode) {
		super();
		this.currencyId = currencyId;
		this.currencyName = currencyName;
		this.currencyCode = currencyCode;
	}
	

	/**
	 * @return the applicationCountryId
	 */
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	/**
	 * @param applicationCountryId
	 *            the applicationCountryId to set
	 */
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	/**
	 * @return the bankCountryId
	 */
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	/**
	 * @param bankCountryId
	 *            the bankCountryId to set
	 */
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	/**
	 * @return the currencyId
	 */
	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	/**
	 * @param currencyId
	 *            the currencyId to set
	 */
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * @return the currencyName
	 */
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * @param currencyName
	 *            the currencyName to set
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * @return the arabicCurrencyName
	 */
	public String getArabicCurrencyName() {
		return arabicCurrencyName;
	}

	/**
	 * @param arabicCurrencyName
	 *            the arabicCurrencyName to set
	 */
	public void setArabicCurrencyName(String arabicCurrencyName) {
		this.arabicCurrencyName = arabicCurrencyName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	
}
