package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddBankDDPrintLocBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//TODO
	private BigDecimal countryId;
	private String dDAgent;
	private String dDPrintLocation;
	private String countryName;
	private BigDecimal stateId;
	private BigDecimal districtId;
	private BigDecimal cityId;
	private BigDecimal bankBranchId;
	private String agentName;
	//TODO
	public AddBankDDPrintLocBean(String countryName, String dDAgent,String dDPrintLocation,BigDecimal countryId,BigDecimal stateId,BigDecimal districtId,BigDecimal  cityId,BigDecimal bankBranchId,String agentName) {
		this.countryName = countryName;
		this.dDAgent = dDAgent;
		this.dDPrintLocation = dDPrintLocation;
		this.countryId = countryId;
		this.cityId = cityId;
		this.stateId = stateId;
		this.districtId = districtId;
		this.bankBranchId = bankBranchId;
		this.agentName = agentName;
		
	}
	
	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
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

	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getdDAgent() {
		return dDAgent;
	}
	public void setdDAgent(String dDAgent) {
		this.dDAgent = dDAgent;
	}
	public String getdDPrintLocation() {
		return dDPrintLocation;
	}
	public void setdDPrintLocation(String dDPrintLocation) {
		this.dDPrintLocation = dDPrintLocation;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	
	
	
}
