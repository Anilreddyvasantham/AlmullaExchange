package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class SearchSwiftCodeDataTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String swiftCode;
	private String swiftBankName;
	private String countryCode;
	private BigDecimal swiftBankId;
	
	
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	public String getSwiftBankName() {
		return swiftBankName;
	}
	public void setSwiftBankName(String swiftBankName) {
		this.swiftBankName = swiftBankName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public BigDecimal getSwiftBankId() {
		return swiftBankId;
	}
	public void setSwiftBankId(BigDecimal swiftBankId) {
		this.swiftBankId = swiftBankId;
	}
	
	
	
}
