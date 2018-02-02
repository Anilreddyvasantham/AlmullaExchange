package com.amg.exchange.treasury.viewModel;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_EXRATE_BAL_SUMM")
public class CurrencyCountryLevelView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal srNo;
	private String countryCode;
	private String countryName;
	private String currencyCode;
	private String currencyName;
	private BigDecimal fCurBal;
	private BigDecimal curBal;
	private BigDecimal avgRate;
	
	@Id
	@Column(name = "ROW_ID")
	public BigDecimal getSrNo() {
		return srNo;
	}
	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}
	
	@Column(name = "COUNTRY_CODE")
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	@Column(name = "COUNTRY_NAME")
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	@Column(name = "CURRENCY_CODE")
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	@Column(name = "CURRENCY_NAME")
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	@Column(name = "FCURBAL")
	public BigDecimal getfCurBal() {
		return fCurBal;
	}
	public void setfCurBal(BigDecimal fCurBal) {
		this.fCurBal = fCurBal;
	}
	
	@Column(name = "CURBAL")
	public BigDecimal getCurBal() {
		return curBal;
	}
	public void setCurBal(BigDecimal curBal) {
		this.curBal = curBal;
	}
	
	@Column(name = "AVGRATE")
	public BigDecimal getAvgRate() {
		return avgRate;
	}
	public void setAvgRate(BigDecimal avgRate) {
		this.avgRate = avgRate;
	}
	
	

}
