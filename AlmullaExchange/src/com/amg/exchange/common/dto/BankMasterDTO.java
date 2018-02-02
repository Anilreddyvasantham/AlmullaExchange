package com.amg.exchange.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BankMasterDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String bankCode;
	private BigDecimal bankId;
	private String bankDecs;
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public String getBankDecs() {
		return bankDecs;
	}
	public void setBankDecs(String bankDecs) {
		this.bankDecs = bankDecs;
	}
	
	
	

}
