package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;

public class LocalBankBalanceDatatable {
	
	private int serialNo;
	private String bankCode;
	private String bankName;
	private BigDecimal localCurrency;
	private BigDecimal usdValue;
	public LocalBankBalanceDatatable(){}
	

	
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public BigDecimal getLocalCurrency() {
		return localCurrency;
	}
	public void setLocalCurrency(BigDecimal localCurrency) {
		this.localCurrency = localCurrency;
	}
	
	public BigDecimal getUsdValue() {
		return usdValue;
	}
	public void setUsdValue(BigDecimal usdValue) {
		this.usdValue = usdValue;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}



	public LocalBankBalanceDatatable(int serialNo, String bankCode, String bankName, BigDecimal localCurrency, BigDecimal usdValue) {
		super();
		this.serialNo = serialNo;
		this.bankCode = bankCode;
		this.bankName = bankName;
		this.localCurrency = localCurrency;
		this.usdValue = usdValue;
	}



	@Override
	public String toString() {
		return "LocalBankBalanceDatatable [serialNo=" + serialNo + ", bankCode=" + bankCode + ", bankName=" + bankName + ", localCurrency=" + localCurrency + ", usdValue=" + usdValue + "]";
	}
	
	
	
	
	
	
}
