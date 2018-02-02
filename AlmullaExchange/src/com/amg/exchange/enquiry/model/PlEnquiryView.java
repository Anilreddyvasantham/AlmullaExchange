package com.amg.exchange.enquiry.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author Nazish Ehsan Hashmi
 */
@Entity
@Table(name="V_EX_PL_INQUIRY")
public class PlEnquiryView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal applicationCountryId;
    private String bankCode;
    private BigDecimal bankCountryId;
    private BigDecimal bankId;
    private BigDecimal currencyId;
    private String faFundAccountNo;
    private BigDecimal foreignCurrencyBal;
    private BigDecimal hvTrx;
    private BigDecimal localCurrencyBal;
    private BigDecimal minimumBalance;
    
    private String eftBalance;
    private String ttBalance;
    private String cashBalance;
    private String projectionBalance;
    private String glSno;
    private BigDecimal unfundedTrx;
    
    private BigDecimal srNo;
  
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name="BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name="BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
    @Id
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
	
	@Column(name="FA_FUND_ACCOUNT_NUMBER")
	public String getFaFundAccountNo() {
		return faFundAccountNo;
	}
	public void setFaFundAccountNo(String faFundAccountNo) {
		this.faFundAccountNo = faFundAccountNo;
	}
	
	@Column(name="FOREIGN_CURRENCY_BALANCE")
	public BigDecimal getForeignCurrencyBal() {
		return foreignCurrencyBal;
	}
	public void setForeignCurrencyBal(BigDecimal foreignCurrencyBal) {
		this.foreignCurrencyBal = foreignCurrencyBal;
	}
	
	@Column(name="HV_TRNX")
	public BigDecimal getHvTrx() {
		return hvTrx;
	}
	public void setHvTrx(BigDecimal hvTrx) {
		this.hvTrx = hvTrx;
	}
	
	@Column(name="LOCAL_CURRENCY_BALANCE")
	public BigDecimal getLocalCurrencyBal() {
		return localCurrencyBal;
	}
	public void setLocalCurrencyBal(BigDecimal localCurrencyBal) {
		this.localCurrencyBal = localCurrencyBal;
	}
	
	@Column(name="MINIMUM_BALANCE")
	public BigDecimal getMinimumBalance() {
		return minimumBalance;
	}
	public void setMinimumBalance(BigDecimal minimumBalance) {
		this.minimumBalance = minimumBalance;
	}
	
	@Column(name="EFT_BALANCE")
	public String getEftBalance() {
		return eftBalance;
	}
	public void setEftBalance(String eftBalance) {
		this.eftBalance = eftBalance;
	}
	
	@Column(name="TT_BALANCE")
	public String getTtBalance() {
		return ttBalance;
	}
	public void setTtBalance(String ttBalance) {
		this.ttBalance = ttBalance;
	}
	
	@Column(name="CASH_BALANCE")
	public String getCashBalance() {
		return cashBalance;
	}
	public void setCashBalance(String cashBalance) {
		this.cashBalance = cashBalance;
	}
	
	@Column(name="PROEJCTION_BALANCE")
	
	public String getProjectionBalance() {
		return projectionBalance;
	}
	public void setProjectionBalance(String projectionBalance) {
		this.projectionBalance = projectionBalance;
	}
	
	@Column(name="GL_SL_NUMBER")
	public String getGlSno() {
		return glSno;
	}
	public void setGlSno(String glSno) {
		this.glSno = glSno;
	}
	
	@Column(name="UNFUNDED_TRNX")
	public BigDecimal getUnfundedTrx() {
		return unfundedTrx;
	}
	public void setUnfundedTrx(BigDecimal unfundedTrx) {
		this.unfundedTrx = unfundedTrx;
	}
	
	@Id
	@Column(name="SRNO")
	public BigDecimal getSrNo() {
		return srNo;
	}
	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}
	
    
}
