package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author Nazish Ehsan Hashmi
 */
@Entity
@Table(name="VW_EX_CORRES_BANK_COUNTRY")
public class ViewCorrespondingBankCountry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal bankCountryId;
    private String countryName;
    private String bankCountryCode;
    
    @Id
    @Column(name="BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	@Column(name="COUNTRY_NAME ")
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	@Column(name="BANK_COUNTRY_CODE")
	public String getBankCountryCode() {
		return bankCountryCode;
	}
	public void setBankCountryCode(String bankCountryCode) {
		this.bankCountryCode = bankCountryCode;
	}
    
    

}
