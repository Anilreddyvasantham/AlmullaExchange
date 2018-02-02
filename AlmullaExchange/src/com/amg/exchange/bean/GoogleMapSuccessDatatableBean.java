package com.amg.exchange.bean;

import java.math.BigDecimal;

public class GoogleMapSuccessDatatableBean {

	private BigDecimal contactType = null;
	
	
	private BigDecimal countryId = null;

	
	private BigDecimal stateId = null;
	
	
	private BigDecimal districtId = null;
	
	
	private BigDecimal cityId = null;


	public GoogleMapSuccessDatatableBean() {
		
	}


	public GoogleMapSuccessDatatableBean(BigDecimal contactType,
			BigDecimal countryId, BigDecimal stateId, BigDecimal districtId,
			BigDecimal cityId) {
		super();
		this.contactType = contactType;
		this.countryId = countryId;
		this.stateId = stateId;
		this.districtId = districtId;
		this.cityId = cityId;
	}


	public BigDecimal getContactType() {
		return contactType;
	}


	public void setContactType(BigDecimal contactType) {
		this.contactType = contactType;
	}


	public BigDecimal getCountryId() {
		return countryId;
	}


	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}


	public BigDecimal getStateId() {
		return stateId;
	}


	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}


	public BigDecimal getDistrictId() {
		return districtId;
	}


	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}


	public BigDecimal getCityId() {
		return cityId;
	}


	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}
	
	
}
