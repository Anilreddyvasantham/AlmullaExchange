package com.amg.exchange.wuh2h.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_WU_CORRIDOR_COUNTRY")
public class WUCorridorCountry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String countryISOCode;
	private String countryDescription;
	
	
	@Id
	@Column(name="WU_COUNTRY_ISO_CODE")
	public String getCountryISOCode() {
		return countryISOCode;
	}
	public void setCountryISOCode(String countryISOCode) {
		this.countryISOCode = countryISOCode;
	}
	
	@Column(name="WU_COUNTRY_DESCRIPTION")
	public String getCountryDescription() {
		return countryDescription;
	}
	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}
	
	
}
