package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class AlternateChannelDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal alternateCannelsId;
	private String beneCountryName;
	private String beneName;
	private String beneBank;
	private String beneBankBranch;
	private String beneAccountNumber;
	private BigDecimal fixedAmount;
	private String languageName;
	private String serviceProviderName;
	
	
	
	public BigDecimal getAlternateCannelsId() {
		return alternateCannelsId;
	}
	public void setAlternateCannelsId(BigDecimal alternateCannelsId) {
		this.alternateCannelsId = alternateCannelsId;
	}
	public String getBeneCountryName() {
		return beneCountryName;
	}
	public void setBeneCountryName(String beneCountryName) {
		this.beneCountryName = beneCountryName;
	}
	public String getBeneName() {
		return beneName;
	}
	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}
	public String getBeneBank() {
		return beneBank;
	}
	public void setBeneBank(String beneBank) {
		this.beneBank = beneBank;
	}
	public String getBeneBankBranch() {
		return beneBankBranch;
	}
	public void setBeneBankBranch(String beneBankBranch) {
		this.beneBankBranch = beneBankBranch;
	}
	public String getBeneAccountNumber() {
		return beneAccountNumber;
	}
	public void setBeneAccountNumber(String beneAccountNumber) {
		this.beneAccountNumber = beneAccountNumber;
	}
	public BigDecimal getFixedAmount() {
		return fixedAmount;
	}
	public void setFixedAmount(BigDecimal fixedAmount) {
		this.fixedAmount = fixedAmount;
	}
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	public String getServiceProviderName() {
		return serviceProviderName;
	}
	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
	
	

}
