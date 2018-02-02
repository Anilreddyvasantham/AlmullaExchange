package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerSpecialDealRequestBean implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal pk;
	private String countryName;
	private String bankName;
	private String currencyName;
	private String requestYear;
	private BigDecimal requestNo;
	private BigDecimal fcAmount;
	private String projectDate; 
	private Boolean booCheck;
	private String custName;
	private String valueDate;
	
	public CustomerSpecialDealRequestBean() {
		
	}
	
	public CustomerSpecialDealRequestBean(BigDecimal pk, String countryName, String bankName,
			String currencyName, String requestYear, BigDecimal requestNo,
			BigDecimal fcAmount, String projectDate) {
		this.setPk(pk);
		this.countryName = countryName;
		this.bankName = bankName;
		this.currencyName = currencyName;
		this.requestYear = requestYear;
		this.requestNo = requestNo;
		this.fcAmount = fcAmount;
		this.projectDate = projectDate;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}


	

	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}




	public String getCurrencyName() {
		return currencyName;
	}


	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}




	public String getRequestYear() {
		return requestYear;
	}


	public void setRequestYear(String requestYear) {
		this.requestYear = requestYear;
	}




	public BigDecimal getRequestNo() {
		return requestNo;
	}


	public void setRequestNo(BigDecimal requestNo) {
		this.requestNo = requestNo;
	}


	public BigDecimal getFcAmount() {
		return fcAmount;
	}


	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}


	public String getProjectDate() {
		return projectDate;
	}


	public void setProjectDate(String projectDate) {
		this.projectDate = projectDate;
	}


	public BigDecimal getPk() {
		return pk;
	}


	public void setPk(BigDecimal pk) {
		this.pk = pk;
	}

	public Boolean getBooCheck() {
		return booCheck;
	}

	public void setBooCheck(Boolean booCheck) {
		this.booCheck = booCheck;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
}
