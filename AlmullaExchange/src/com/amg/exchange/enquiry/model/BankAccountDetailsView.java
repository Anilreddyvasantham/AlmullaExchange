package com.amg.exchange.enquiry.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_BANK_ACCT_DTLS")
public class BankAccountDetailsView implements Serializable{

	private static final long serialVersionUID = 1L;

	
	public BankAccountDetailsView() {
	}

	@Id
	@Column(name="FA_ACCOUNT_NUMBER")
	private String faAccountNumber;
	
	@Column(name="BANK_ID")
	private BigDecimal bankId;
	
	@Column(name="CURRENCY_ID")
	private BigDecimal currencyId;
	
	@Column(name="FOREIGN_CURRENCY_BALANCE")
	private String foreignCurrencyBalance;
	
	@Column(name="LOCAL_CURRENCY_BALANCE")
	private BigDecimal localCurrencyBalance;
	
	@Column(name="AVERAGE_RATE")
	private BigDecimal averageRate;
	
	@Column(name="SPECIAL_DEAL_FC_AMOUNT")
	private BigDecimal speacialDealFcAmount;

	@Column(name="FC_PROV_BALANCE")
	private BigDecimal fcProvBalance;
	
	@Column(name="LC_PROV_BALANCE")
	private BigDecimal lcProvBalance;
	
	@Column(name="BANK_CODE")
	private String bankCode;
	
	@Column(name="BANK_NAME")
	private String bankName;
	
	@Column(name="CURRENCY_CODE")
	private String currencyCode;
	
	@Column(name="CURRENCY_NAME")
	private String currencyName;

	@Column(name="TOTAL_FC_AMOUNT")
	private BigDecimal totalForeignCurrencyAmount;
	
	@Column(name="COUNTRY_ID")
	private BigDecimal countryId;
	

	public BigDecimal getCountryId() {
		return countryId;
	}


	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}


	public BankAccountDetailsView(String faAccountNumber, BigDecimal bankId,
			BigDecimal currencyId, String foreignCurrencyBalance,
			BigDecimal localCurrencyBalance, BigDecimal averageRate,
			BigDecimal speacialDealFcAmount, BigDecimal fcProvBalance,
			BigDecimal lcProvBalance, String bankCode, String bankName,
			String currencyCode, String currencyName,
			BigDecimal totalForeignCurrencyAmount,BigDecimal countryId) {
		super();
		this.faAccountNumber = faAccountNumber;
		this.bankId = bankId;
		this.currencyId = currencyId;
		this.foreignCurrencyBalance = foreignCurrencyBalance;
		this.localCurrencyBalance = localCurrencyBalance;
		this.averageRate = averageRate;
		this.speacialDealFcAmount = speacialDealFcAmount;
		this.fcProvBalance = fcProvBalance;
		this.lcProvBalance = lcProvBalance;
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.currencyCode = currencyCode;
		this.currencyName = currencyName;
		this.totalForeignCurrencyAmount = totalForeignCurrencyAmount;
		this.countryId=countryId;
	}

	
	public BigDecimal getTotalForeignCurrencyAmount() {
		return totalForeignCurrencyAmount;
	}


	public void setTotalForeignCurrencyAmount(BigDecimal totalForeignCurrencyAmount) {
		this.totalForeignCurrencyAmount = totalForeignCurrencyAmount;
	}


	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getFaAccountNumber() {
		return faAccountNumber;
	}

	public void setFaAccountNumber(String faAccountNumber) {
		this.faAccountNumber = faAccountNumber;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getForeignCurrencyBalance() {
		return foreignCurrencyBalance;
	}

	public void setForeignCurrencyBalance(String foreignCurrencyBalance) {
		this.foreignCurrencyBalance = foreignCurrencyBalance;
	}

	public BigDecimal getLocalCurrencyBalance() {
		return localCurrencyBalance;
	}

	public void setLocalCurrencyBalance(BigDecimal localCurrencyBalance) {
		this.localCurrencyBalance = localCurrencyBalance;
	}

	public BigDecimal getAverageRate() {
		return averageRate;
	}

	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}

	public BigDecimal getSpeacialDealFcAmount() {
		return speacialDealFcAmount;
	}

	public void setSpeacialDealFcAmount(BigDecimal speacialDealFcAmount) {
		this.speacialDealFcAmount = speacialDealFcAmount;
	}

	public BigDecimal getFcProvBalance() {
		return fcProvBalance;
	}

	public void setFcProvBalance(BigDecimal fcProvBalance) {
		this.fcProvBalance = fcProvBalance;
	}

	public BigDecimal getLcProvBalance() {
		return lcProvBalance;
	}

	public void setLcProvBalance(BigDecimal lcProvBalance) {
		this.lcProvBalance = lcProvBalance;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	
	
	
	
	
	
	
}
