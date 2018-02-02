package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class TotalUsdRequirementDetails implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private BigDecimal usdCurrentBalanace;
	private BigDecimal calUsdSaleProjection;
	private BigDecimal calFcTousdRequirement;
	private BigDecimal calTotalUsdrequirement;
	private String appCountry;
	private String appCountryId;
	
	
	
	
	public BigDecimal getUsdCurrentBalanace() {
		return usdCurrentBalanace;
	}
	public void setUsdCurrentBalanace(BigDecimal usdCurrentBalanace) {
		this.usdCurrentBalanace = usdCurrentBalanace;
	}
	public BigDecimal getCalUsdSaleProjection() {
		return calUsdSaleProjection;
	}
	public void setCalUsdSaleProjection(BigDecimal calUsdSaleProjection) {
		this.calUsdSaleProjection = calUsdSaleProjection;
	}
	public BigDecimal getCalFcTousdRequirement() {
		return calFcTousdRequirement;
	}
	public void setCalFcTousdRequirement(BigDecimal calFcTousdRequirement) {
		this.calFcTousdRequirement = calFcTousdRequirement;
	}
	public BigDecimal getCalTotalUsdrequirement() {
		return calTotalUsdrequirement;
	}
	public void setCalTotalUsdrequirement(BigDecimal calTotalUsdrequirement) {
		this.calTotalUsdrequirement = calTotalUsdrequirement;
	}
	public String getAppCountry() {
		return appCountry;
	}
	public void setAppCountry(String appCountry) {
		this.appCountry = appCountry;
	}
	public String getAppCountryId() {
		return appCountryId;
	}
	public void setAppCountryId(String appCountryId) {
		this.appCountryId = appCountryId;
	}
	
	
	
	

}
