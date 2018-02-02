package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_SWIFT_COUNTRY")
public class SwiftMasterCountryView implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  
	  private String countryCode;
	  private String countryFullName;
	  private String countryShortName;
	  private String countryArabicFullName;
	  private String countryArabicShortName;
	  private String isoCountry;
	  public SwiftMasterCountryView() {
		    super();
	  }
	  
	
	  public SwiftMasterCountryView(String countryCode,
			String countryFullName, String countryShortName,
			String countryArabicFullName, String countryArabicShortName,
			String isoCountry) {
		super();
		this.countryCode = countryCode;
		this.countryFullName = countryFullName;
		this.countryShortName = countryShortName;
		this.countryArabicFullName = countryArabicFullName;
		this.countryArabicShortName = countryArabicShortName;
		this.isoCountry = isoCountry;
	}

	@Id
	@Column(name = "COUNTRY_CODE")
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@Column(name = "COUNTRY_FULL_NAME")
	public String getCountryFullName() {
		return countryFullName;
	}
	public void setCountryFullName(String countryFullName) {
		this.countryFullName = countryFullName;
	}
	@Column(name = "COUNTRY_SHORT_NAME")
	public String getCountryShortName() {
		return countryShortName;
	}
	public void setCountryShortName(String countryShortName) {
		this.countryShortName = countryShortName;
	}
	@Column(name = "COUNTRY_ARABIC_FULL_NAME")
	public String getCountryArabicFullName() {
		return countryArabicFullName;
	}
	public void setCountryArabicFullName(String countryArabicFullName) {
		this.countryArabicFullName = countryArabicFullName;
	}
	@Column(name = "COUNTRY_ARABIC_SHORT_NAME")
	public String getCountryArabicShortName() {
		return countryArabicShortName;
	}
	public void setCountryArabicShortName(String countryArabicShortName) {
		this.countryArabicShortName = countryArabicShortName;
	}
	@Column(name = "ISO_COUNTRY")
	public String getIsoCountry() {
		return isoCountry;
	}
	public void setIsoCountry(String isoCountry) {
		this.isoCountry = isoCountry;
	}
	  
	  
}
