package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class CountryBean implements Serializable{
	
	private BigDecimal countryId;
	private String countryName;
	
	public CountryBean(BigDecimal stateId, String stateName) {
		this.countryId = stateId;
		this.countryName = stateName;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	
}
