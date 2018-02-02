package com.amg.exchange.online.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "VW_EX_SOURCE_CURRENCY")
public class SourceCurrencyView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal idNo;
	private BigDecimal bankId;
	private BigDecimal currencyId;
	private String currencyCode;
	private String currencyName;
	
	@Id
	@Column(name="IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	
	@Column(name="BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	@Column(name="CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name="CURRENCY_CODE")
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
	
	

}

