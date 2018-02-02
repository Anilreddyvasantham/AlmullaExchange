package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_BANK_DEBIT_CARD_LEN")
public class BankDebitCardLengthViewModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public BigDecimal appCountryId;
	public String bankCode;
	public BigDecimal bankLength;
	
	
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getAppCountryId() {
		return appCountryId;
	}
	
	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}
	
	@Id
	@Column(name="BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name="DEBIT_CARD_LENGTH")
	public BigDecimal getBankLength() {
		return bankLength;
	}
	
	public void setBankLength(BigDecimal bankLength) {
		this.bankLength = bankLength;
	}

}
