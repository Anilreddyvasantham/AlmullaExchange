package com.amg.exchange.testkey.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class TestKeyValueAccNumDesc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal accountNumberId;
	private String accountNumber;
	private String accountNumberDesc;
	
	public BigDecimal getAccountNumberId() {
		return accountNumberId;
	}
	public void setAccountNumberId(BigDecimal accountNumberId) {
		this.accountNumberId = accountNumberId;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getAccountNumberDesc() {
		return accountNumberDesc;
	}
	public void setAccountNumberDesc(String accountNumberDesc) {
		this.accountNumberDesc = accountNumberDesc;
	}
	
	

}
