package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class RoutingCountry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal routingCountryId;
	private String routingCountryName;
	private BigDecimal routingBankId;
	private String routingBankName;
	
	
	public RoutingCountry(BigDecimal routingCountryId, String routingCountryName) {
		this.routingCountryId = routingCountryId;
		this.routingCountryName = routingCountryName;
	}


	public BigDecimal getRoutingCountryId() {
		return routingCountryId;
	}


	public void setRoutingCountryId(BigDecimal routingCountryId) {
		this.routingCountryId = routingCountryId;
	}


	public String getRoutingCountryName() {
		return routingCountryName;
	}


	public void setRoutingCountryName(String routingCountryName) {
		this.routingCountryName = routingCountryName;
	}


	public BigDecimal getRoutingBankId() {
		return routingBankId;
	}


	public void setRoutingBankId(BigDecimal routingBankId) {
		this.routingBankId = routingBankId;
	}


	public String getRoutingBankName() {
		return routingBankName;
	}


	public void setRoutingBankName(String routingBankName) {
		this.routingBankName = routingBankName;
	}
	
	
	
}
