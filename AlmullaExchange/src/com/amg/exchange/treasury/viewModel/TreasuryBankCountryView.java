package com.amg.exchange.treasury.viewModel;

/**
 * Author :Rabil Date :11/08/2015
 */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_BANK_COUNTRY")
public class TreasuryBankCountryView implements Serializable {


	private static final long serialVersionUID = 1L;
	private BigDecimal bankCountryId;
	private String bankCountryCode;
	private String bankCountryName;
	private BigDecimal applicationCountryId;


	@Id
	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	@Column(name = "BANK_COUNTRY_NAME")
	public String getBankCountryName() {
		return bankCountryName;
	}

	public void setBankCountryName(String bankCountryName) {
		this.bankCountryName = bankCountryName;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "BANK_COUNTRY_CODE")
	public String getBankCountryCode() {
		return bankCountryCode;
	}

	public void setBankCountryCode(String bankCountryCode) {
		this.bankCountryCode = bankCountryCode;
	}
	
	


}
