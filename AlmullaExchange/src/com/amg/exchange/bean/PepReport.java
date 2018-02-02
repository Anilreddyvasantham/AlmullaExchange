package com.amg.exchange.bean;

import java.io.Serializable;


public class PepReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String countryBranch;
	private String country;
	private String customerName;
	private String validUpto;
	private String civilId;
	private String logoPath;
	private String pepNoteBody;
	private String pepDeclarationBody;
	
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	public String getCountryBranch() {
		return countryBranch;
	}
	public void setCountryBranch(String countryBranch) {
		this.countryBranch = countryBranch;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getValidUpto() {
		return validUpto;
	}
	public void setValidUpto(String validUpto) {
		this.validUpto = validUpto;
	}
	
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	
	public String getPepNoteBody() {
		return pepNoteBody;
	}
	public void setPepNoteBody(String pepNoteBody) {
		this.pepNoteBody = pepNoteBody;
	}
	
	public String getPepDeclarationBody() {
		return pepDeclarationBody;
	}
	public void setPepDeclarationBody(String pepDeclarationBody) {
		this.pepDeclarationBody = pepDeclarationBody;
	}
	
	
}
