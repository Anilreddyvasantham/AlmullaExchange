package com.amg.exchange.treasury.viewModel;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_GLCBAL")
public class CurrencyGLLevelView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal srNo;
	private String bankCode;
	private String bankFullName;
	private String currencyCode;
	private String currencyName;
	private String glNo;
	private BigDecimal rateFCurBal;
	private BigDecimal rateCurBal;
	private BigDecimal rateAvgRate;
	
	@Id
	@Column(name = "ROW_ID")
	public BigDecimal getSrNo() {
		return srNo;
	}
	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}
	
	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "BANK_FULL_NAME")
	public String getBankFullName() {
		return bankFullName;
	}
	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
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
	
	@Column(name = "SLNO")
	public String getGlNo() {
		return glNo;
	}
	public void setGlNo(String glNo) {
		this.glNo = glNo;
	}
	
	@Column(name = "RATE_FCURBAL")
	public BigDecimal getRateFCurBal() {
		return rateFCurBal;
	}
	public void setRateFCurBal(BigDecimal rateFCurBal) {
		this.rateFCurBal = rateFCurBal;
	}
	
	@Column(name = "RATE_CURBAL")
	public BigDecimal getRateCurBal() {
		return rateCurBal;
	}
	public void setRateCurBal(BigDecimal rateCurBal) {
		this.rateCurBal = rateCurBal;
	}
	
	@Column(name = "RATE_AVGRATE")
	public BigDecimal getRateAvgRate() {
		return rateAvgRate;
	}
	public void setRateAvgRate(BigDecimal rateAvgRate) {
		this.rateAvgRate = rateAvgRate;
	}

}
