package com.amg.exchange.wuh2h.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_WU_CORRIDOR_CURRENCY")
public class WUCorridorCurrency implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String idNo;
	private String countryISOCode;
	private String countryNumberCode;
	private String countryDescription;
	private String currencyISOCode;
	private String currencyNumberCode;
	private String currencyDescription;
	private String isActive;
	
	@Id
	@Column(name="ID_NO")
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	@Column(name="WU_COUNTRY_NUMBER_CODE")
	public String getCountryNumberCode() {
		return countryNumberCode;
	}
	public void setCountryNumberCode(String countryNumberCode) {
		this.countryNumberCode = countryNumberCode;
	}
	
	@Column(name="WU_COUNTRY_DESCRIPTION")
	public String getCountryDescription() {
		return countryDescription;
	}
	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}
	
	@Column(name="WU_CURRENCY_NUMBER_CODE")
	public String getCurrencyNumberCode() {
		return currencyNumberCode;
	}
	public void setCurrencyNumberCode(String currencyNumberCode) {
		this.currencyNumberCode = currencyNumberCode;
	}
	
	@Column(name="WU_CURRENCY_DESCRIPTION")
	public String getCurrencyDescription() {
		return currencyDescription;
	}
	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}
	
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="WU_COUNTRY_ISO_CODE")
	public String getCountryISOCode() {
		return countryISOCode;
	}
	public void setCountryISOCode(String countryISOCode) {
		this.countryISOCode = countryISOCode;
	}
	
	@Column(name="WU_CURRENCY_ISO_CODE")
	public String getCurrencyISOCode() {
		return currencyISOCode;
	}
	public void setCurrencyISOCode(String currencyISOCode) {
		this.currencyISOCode = currencyISOCode;
	}
	
	
}
