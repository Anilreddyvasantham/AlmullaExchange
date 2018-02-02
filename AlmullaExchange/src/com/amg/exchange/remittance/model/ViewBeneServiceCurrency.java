package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_BENE_SERVICE_CURRENCY")
public class ViewBeneServiceCurrency implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal currencyId;
	private String currencyCode;
	private String currencyName;
	private BigDecimal beneCountryId;
	private BigDecimal sNo;
	
	public ViewBeneServiceCurrency() {
		super();
	}
	
	public ViewBeneServiceCurrency(BigDecimal currencyId, String currencyCode,
			String currencyName, BigDecimal beneCountryId, BigDecimal sNo) {
		super();
		this.currencyId = currencyId;
		this.currencyCode = currencyCode;
		this.currencyName = currencyName;
		this.beneCountryId = beneCountryId;
		this.sNo = sNo;
	}

	@Id
	@Column(name="SLNO")	
	public BigDecimal getsNo() {
		return sNo;
	}
	public void setsNo(BigDecimal sNo) {
		this.sNo = sNo;
	}
	
	@Column(name="CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name="EMOS_CURRENCY_CODE")
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	@Column(name="CURRENCY_NAME")
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	@Column(name="BENE_COUNTRY_ID")
	public BigDecimal getBeneCountryId() {
		return beneCountryId;
	}
	public void setBeneCountryId(BigDecimal beneCountryId) {
		this.beneCountryId = beneCountryId;
	}
	
	
}
