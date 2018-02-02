package com.amg.exchange.beneficiary.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "VW_EX_BANKS")
@Table(name = "VW_EX_CHANNEL_BANKS")
public class BanksView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal rowId;
	private BigDecimal applicationCountryId;
	private BigDecimal bankCountryId;
	private BigDecimal bankId;
	private String bankCode;
	private String bankFullName;
	private BigDecimal serviceGroupId;
	private String bankInd;
	private String languageInd;
	
	@Id
	@Column(name = "ROW_ID")
	public BigDecimal getRowId() {
		return rowId;
	}
	public void setRowId(BigDecimal rowId) {
		this.rowId = rowId;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
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
	
	@Column(name = "SERVICE_GROUP_ID")
	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}
	
	@Column(name = "BANK_IND")
	public String getBankInd() {
		return bankInd;
	}
	public void setBankInd(String bankInd) {
		this.bankInd = bankInd;
	}
	
	@Column(name = "LANGUAGE_IND")
	public String getLanguageInd() {
		return languageInd;
	}
	public void setLanguageInd(String languageInd) {
		this.languageInd = languageInd;
	}
	
	
	
	

}
