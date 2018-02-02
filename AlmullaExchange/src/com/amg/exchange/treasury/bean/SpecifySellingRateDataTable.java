package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SpecifySellingRateDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String specialReference;
	private String projectiondate;
	private String customer;
	private String bank;
	private String currency;
	private String fundingOption;
	private BigDecimal fcAmount;
	private BigDecimal sellRate = null;
	private BigDecimal buyRate;
	private BigDecimal avarageRate;
	private BigDecimal specialCustReqId;
	private BigDecimal pk;
	private BigDecimal bankCountryId = null;
	private BigDecimal bankId = null;
	private BigDecimal currencyId = null;
	private BigDecimal customerId = null;
	private BigDecimal appCountryId = null;
	private Boolean editable = false;
	private Boolean nonEditable = true;
	private Boolean dispWaterMark=true;
	private Boolean readOnlySellRate=true;
	private Boolean disablefundingOption;
	private String valuedate;
	private BigDecimal documentNo;

	public Boolean getReadOnlySellRate() {
		return readOnlySellRate;
	}

	public void setReadOnlySellRate(Boolean readOnlySellRate) {
		this.readOnlySellRate = readOnlySellRate;
	}

	public Boolean getDispWaterMark() {
		return dispWaterMark;
	}

	public void setDispWaterMark(Boolean dispWaterMark) {
		this.dispWaterMark = dispWaterMark;
	}

	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public String getSpecialReference() {
		return specialReference;
	}
	public void setSpecialReference(String specialReference) {
		this.specialReference = specialReference;
	}

	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getFundingOption() {
		return fundingOption;
	}
	public void setFundingOption(String fundingOption) {
		this.fundingOption = fundingOption;
	}
	public BigDecimal getFcAmount() {
		return fcAmount;
	}
	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}
	public  BigDecimal getSellRate() {
		return sellRate;
	}
	public void setSellRate(BigDecimal sellRate) {
		this.sellRate = sellRate;
	}
	public BigDecimal getBuyRate() {
		return buyRate;
	}
	public void setBuyRate(BigDecimal buyRate) {
		this.buyRate = buyRate;
	}
	public BigDecimal getAvarageRate() {
		return avarageRate;
	}
	public void setAvarageRate(BigDecimal avarageRate) {
		this.avarageRate = avarageRate;
	}
	public BigDecimal getSpecialCustReqId() {
		return specialCustReqId;
	}
	public void setSpecialCustReqId(BigDecimal specialCustReqId) {
		this.specialCustReqId = specialCustReqId;
	}

	public BigDecimal getPk() {
		return pk;
	}

	public void setPk(BigDecimal pk) {
		this.pk = pk;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getNonEditable() {
		return nonEditable;
	}

	public void setNonEditable(Boolean nonEditable) {
		this.nonEditable = nonEditable;
	}

	public Boolean getDisablefundingOption() {
		return disablefundingOption;
	}

	public void setDisablefundingOption(Boolean disablefundingOption) {
		this.disablefundingOption = disablefundingOption;
	}

	public String getProjectiondate() {
		return projectiondate;
	}

	public void setProjectiondate(String projectiondate) {
		this.projectiondate = projectiondate;
	}

	public String getValuedate() {
		return valuedate;
	}

	public void setValuedate(String valuedate) {
		this.valuedate = valuedate;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}




}
	
	


