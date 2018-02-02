package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankServiceBalanceAdjustDatatable implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private BigDecimal bankId;
	private BigDecimal currencyId;
	private BigDecimal countryId;
 	private  String balanceId;
 	private String bankName;
 	private String accountNumber;
 	private String glslNumber;
 	private String bankFcBalance;
 	private BigDecimal eftUnfundAmount;
 	private BigDecimal ttUnfundAmount;
 	private BigDecimal cashUnfundAmount;
 	private BigDecimal eftCurrenctBalance;
 	private BigDecimal ttCurrentBalance;
 	private BigDecimal cashCurrentBalance;
 	private Boolean booEnableselectCheck=false;
	private Boolean selectCheck=false;
	private String dynamicLabelForActivateDeactivate;
	
	private BigDecimal bankDetailId;
	private BigDecimal averageRate;
	private BigDecimal comanyId;
	private BigDecimal localCurrencyBalance;
	
	public BigDecimal getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}
	public BigDecimal getComanyId() {
		return comanyId;
	}
	public void setComanyId(BigDecimal comanyId) {
		this.comanyId = comanyId;
	}
	public BigDecimal getLocalCurrencyBalance() {
		return localCurrencyBalance;
	}
	public void setLocalCurrencyBalance(BigDecimal localCurrencyBalance) {
		this.localCurrencyBalance = localCurrencyBalance;
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
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getBalanceId() {
		return balanceId;
	}
	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getGlslNumber() {
		return glslNumber;
	}
	public void setGlslNumber(String glslNumber) {
		this.glslNumber = glslNumber;
	}
	public String getBankFcBalance() {
		return bankFcBalance;
	}
	public void setBankFcBalance(String bankFcBalance) {
		this.bankFcBalance = bankFcBalance;
	}
	public BigDecimal getEftUnfundAmount() {
		return eftUnfundAmount;
	}
	public void setEftUnfundAmount(BigDecimal eftUnfundAmount) {
		this.eftUnfundAmount = eftUnfundAmount;
	}
	public BigDecimal getTtUnfundAmount() {
		return ttUnfundAmount;
	}
	public void setTtUnfundAmount(BigDecimal ttUnfundAmount) {
		this.ttUnfundAmount = ttUnfundAmount;
	}
	public BigDecimal getCashUnfundAmount() {
		return cashUnfundAmount;
	}
	public void setCashUnfundAmount(BigDecimal cashUnfundAmount) {
		this.cashUnfundAmount = cashUnfundAmount;
	}
	public BigDecimal getEftCurrenctBalance() {
		return eftCurrenctBalance;
	}
	public void setEftCurrenctBalance(BigDecimal eftCurrenctBalance) {
		this.eftCurrenctBalance = eftCurrenctBalance;
	}
	public BigDecimal getTtCurrentBalance() {
		return ttCurrentBalance;
	}
	public void setTtCurrentBalance(BigDecimal ttCurrentBalance) {
		this.ttCurrentBalance = ttCurrentBalance;
	}
	public BigDecimal getCashCurrentBalance() {
		return cashCurrentBalance;
	}
	public void setCashCurrentBalance(BigDecimal cashCurrentBalance) {
		this.cashCurrentBalance = cashCurrentBalance;
	}
	public Boolean getBooEnableselectCheck() {
		return booEnableselectCheck;
	}
	public void setBooEnableselectCheck(Boolean booEnableselectCheck) {
		this.booEnableselectCheck = booEnableselectCheck;
	}
	public Boolean getSelectCheck() {
		return selectCheck;
	}
	public void setSelectCheck(Boolean selectCheck) {
		this.selectCheck = selectCheck;
	}
	 
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public BigDecimal getBankDetailId() {
		return bankDetailId;
	}
	public void setBankDetailId(BigDecimal bankDetailId) {
		this.bankDetailId = bankDetailId;
	}

	
	
}
