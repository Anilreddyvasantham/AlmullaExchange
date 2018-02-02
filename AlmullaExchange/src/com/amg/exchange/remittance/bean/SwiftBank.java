package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;


public class SwiftBank {
	
	
	
	private BigDecimal applicationCountryId;
	private BigDecimal rountingCountryId;
	private BigDecimal currencyId;
	private BigDecimal remitanceId;
	private BigDecimal delivaryId;
	private String fieldName;
	private String beneficarySwiftBank;
	
	private String beneficarySwiftAddress;
	private String errMessage;

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getRountingCountryId() {
		return rountingCountryId;
	}

	public void setRountingCountryId(BigDecimal rountingCountryId) {
		this.rountingCountryId = rountingCountryId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getRemitanceId() {
		return remitanceId;
	}

	public void setRemitanceId(BigDecimal remitanceId) {
		this.remitanceId = remitanceId;
	}

	public BigDecimal getDelivaryId() {
		return delivaryId;
	}

	public void setDelivaryId(BigDecimal delivaryId) {
		this.delivaryId = delivaryId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getBeneficarySwiftBank() {
		return beneficarySwiftBank;
	}

	public void setBeneficarySwiftBank(String beneficarySwiftBank) {
		this.beneficarySwiftBank = beneficarySwiftBank;
	}

	public String getBeneficarySwiftAddress() {
		return beneficarySwiftAddress;
	}

	public void setBeneficarySwiftAddress(String beneficarySwiftAddress) {
		this.beneficarySwiftAddress = beneficarySwiftAddress;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
}
