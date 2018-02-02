package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class PersonalRemittanceRoutingBankBranches implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal bankbranchid;
	private String bankbranchName;
	private BigDecimal bankid;
	private String bankName;
	private String countryName;
	private BigDecimal countryid;
	
	
	
	
	public PersonalRemittanceRoutingBankBranches() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getBankbranchid() {
		return bankbranchid;
	}
	
	public void setBankbranchid(BigDecimal bankbranchid) {
		this.bankbranchid = bankbranchid;
	}
	
	public String getBankbranchName() {
		return bankbranchName;
	}
	
	public void setBankbranchName(String bankbranchName) {
		this.bankbranchName = bankbranchName;
	}
	
	public BigDecimal getBankid() {
		return bankid;
	}
	
	public void setBankid(BigDecimal bankid) {
		this.bankid = bankid;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public BigDecimal getCountryid() {
		return countryid;
	}
	
	public void setCountryid(BigDecimal countryid) {
		this.countryid = countryid;
	}
	
	

}
