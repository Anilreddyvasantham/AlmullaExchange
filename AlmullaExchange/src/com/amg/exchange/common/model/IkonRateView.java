package com.amg.exchange.common.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_EX_IKON_RATE")
public class IkonRateView  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="IKON_RATE_ID")
	private BigDecimal ikonRateId;
	@Column(name="APPLICATION_COUNTRY_ID")
	private BigDecimal applicationCountryId;
	@Column(name="CURRENCY_ID")
	private BigDecimal currencyId;
	@Column(name="CURRENCY_CODE")
	private String currencyCode;
	@Column(name="ISO_CURRENCY_CODE")
	private String isoCurrencyCode;
	@Column(name="CURRENCY_NAME")
	private String currencyName;
	@Column(name="CURRENCY_PRICE")
	private BigDecimal currencyPrice;
	
	
	
	
	public BigDecimal getIkonRateId() {
		return ikonRateId;
	}
	public void setIkonRateId(BigDecimal ikonRateId) {
		this.ikonRateId = ikonRateId;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getIsoCurrencyCode() {
		return isoCurrencyCode;
	}
	public void setIsoCurrencyCode(String isoCurrencyCode) {
		this.isoCurrencyCode = isoCurrencyCode;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public BigDecimal getCurrencyPrice() {
		return currencyPrice;
	}
	public void setCurrencyPrice(BigDecimal currencyPrice) {
		this.currencyPrice = currencyPrice;
	}
	
	
	
		
}
