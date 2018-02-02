package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;

public class RoutingSetupBankDetails {
	
	private BigDecimal bankId = null;
	private String bankName = null;
	private String bankCode = null;
	private BigDecimal bankIndicator = null;
	
	public RoutingSetupBankDetails(BigDecimal bankId, String bankName, String bankCode, BigDecimal bankIndicator) {
		super();
		this.bankId = bankId;
		this.bankName = bankName;
		this.bankCode = bankCode;
		this.bankIndicator = bankIndicator;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public BigDecimal getBankIndicator() {
		return bankIndicator;
	}
	
	public void setBankIndicator(BigDecimal bankIndicator) {
		this.bankIndicator = bankIndicator;
	}
	
	

}
