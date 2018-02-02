package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="VW_EX_HIGH_VALUE_DEAL_REQ")
public class HighValueDealRequestView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal idNo;
	private BigDecimal applicationCountryId;
	private BigDecimal documentNumber;
	private BigDecimal currencyId;
	private BigDecimal bankId;
	private BigDecimal foreignCurrencyAmount;
	private Date validUpto;
	private BigDecimal sellRate;
	private String applicatonCountryName;
	private String quoteName;
	private String bankFullName;
	private String customerName;
	private BigDecimal countryId;
	private String countryName;
	
	
	@Id
	@Column(name="IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	
	@Column(name="DOCUMENT_NUMBER")
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	
	@Column(name="CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	
	@Column(name="BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	
	@Column(name="FOREIGN_CURRENCY_AMOUNT")
	public BigDecimal getForeignCurrencyAmount() {
		return foreignCurrencyAmount;
	}
	
	@Column(name="VALID_UPTO")
	public Date getValidUpto() {
		return validUpto;
	}
	
	@Column(name="SELL_RATE")
	public BigDecimal getSellRate() {
		return sellRate;
	}
	
	@Column(name="APPLICATION_COUNTRY_NAME")
	public String getApplicatonCountryName() {
		return applicatonCountryName;
	}
	
	@Column(name="QUOTE_NAME")
	public String getQuoteName() {
		return quoteName;
	}
	
	@Column(name="BANK_FULL_NAME")
	public String getBankFullName() {
		return bankFullName;
	}
	
	@Column(name="CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public void setForeignCurrencyAmount(BigDecimal foreignCurrencyAmount) {
		this.foreignCurrencyAmount = foreignCurrencyAmount;
	}
	public void setValidUpto(Date validUpto) {
		this.validUpto = validUpto;
	}
	public void setSellRate(BigDecimal sellRate) {
		this.sellRate = sellRate;
	}
	public void setApplicatonCountryName(String applicatonCountryName) {
		this.applicatonCountryName = applicatonCountryName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name="COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	@Column(name="COUNTRY_NAME")
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	
	
	
	
	

}
