package com.amg.exchange.treasury.viewModel;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_EX_CURRENCY_MASTER")
public class CurrencyMasterView implements Serializable {
	 
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CURRENCY_ID")
	private BigDecimal currencyId;
	
	@Column(name="COUNTRY_ID")
	private BigDecimal countryId;
	
	@Column(name="CURRENCY_CODE")
	private String currencyCode;
	
	@Column(name="CURRENCY_NAME")
	private String currencyName;
	
	@Column(name="QUOTE_NAME")
	private String quoteName;
	
	/*@Column(name="CURRENCY_DESC")
	private String currencyDesc;*/
	
	@Column(name="DECIMAL_NAME")
	private String decimalName;
	
	@Column(name="FIMS_CURRENCY_CODE")
	private String fimsCurrencyCode;
	
	@Column(name="ISACTIVE")
	private String isactive;
	
	@Column(name="ARABIC_CURRENCY_NAME")
	private String arabicCurrencyName;
	
	@Column(name="ARABIC_DECIMAL_NAME")
	private String arabicDecimalName;
	
	@Column(name="ARABIC_QUOTE_NAME")
	private String arabicQuoteName;
	
	@Column(name="SWIFT_CURRENCY")
	private String swiftCurrency;
	
	 
	
	@Column(name="ISO_CURRENCY_CODE")
	private String isoCurrencyCode;
	
	@Column(name="REUTERS_CURRENCY")
	private String reutersCurrency;
	 
	@Column(name="DECIMAL_NUMBER")
	private BigDecimal decinalNumber;
	
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
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
	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	/*public String getCurrencyDesc() {
		return currencyDesc;
	}
	public void setCurrencyDesc(String currencyDesc) {
		this.currencyDesc = currencyDesc;
	}*/
	public String getDecimalName() {
		return decimalName;
	}
	public void setDecimalName(String decimalName) {
		this.decimalName = decimalName;
	}
	public String getFimsCurrencyCode() {
		return fimsCurrencyCode;
	}
	public void setFimsCurrencyCode(String fimsCurrencyCode) {
		this.fimsCurrencyCode = fimsCurrencyCode;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public String getArabicCurrencyName() {
		return arabicCurrencyName;
	}
	public void setArabicCurrencyName(String arabicCurrencyName) {
		this.arabicCurrencyName = arabicCurrencyName;
	}
	public String getArabicDecimalName() {
		return arabicDecimalName;
	}
	public void setArabicDecimalName(String arabicDecimalName) {
		this.arabicDecimalName = arabicDecimalName;
	}
	public String getArabicQuoteName() {
		return arabicQuoteName;
	}
	public void setArabicQuoteName(String arabicQuoteName) {
		this.arabicQuoteName = arabicQuoteName;
	}
	public String getSwiftCurrency() {
		return swiftCurrency;
	}
	public void setSwiftCurrency(String swiftCurrency) {
		this.swiftCurrency = swiftCurrency;
	}
	 
	public String getIsoCurrencyCode() {
		return isoCurrencyCode;
	}
	public void setIsoCurrencyCode(String isoCurrencyCode) {
		this.isoCurrencyCode = isoCurrencyCode;
	}
	 
	 
	public BigDecimal getDecinalNumber() {
		return decinalNumber;
	}
	public void setDecinalNumber(BigDecimal decinalNumber) {
		this.decinalNumber = decinalNumber;
	}
	public String getReutersCurrency() {
		return reutersCurrency;
	}
	public void setReutersCurrency(String reutersCurrency) {
		this.reutersCurrency = reutersCurrency;
	}
	

}
