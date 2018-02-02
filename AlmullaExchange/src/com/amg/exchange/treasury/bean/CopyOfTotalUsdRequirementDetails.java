package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CopyOfTotalUsdRequirementDetails implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal usdCurrentBalanace;
	private double calUsdSaleProjection;
	private double calFcTousdRequirement;
	private double calTotalUsdrequirement;
	
	
	public double getCalFcTousdRequirement() {
		return calFcTousdRequirement;
	}

	public void setCalFcTousdRequirement(double calFcTousdRequirement) {
		this.calFcTousdRequirement = calFcTousdRequirement;
	}

	public double getCalTotalUsdrequirement() {
		return calTotalUsdrequirement;
	}

	public void setCalTotalUsdrequirement(double calTotalUsdrequirement) {
		this.calTotalUsdrequirement = calTotalUsdrequirement;
	}

	public double getCalUsdSaleProjection() {
		return calUsdSaleProjection;
	}

	public void setCalUsdSaleProjection(double calUsdSaleProjection) {
		this.calUsdSaleProjection = calUsdSaleProjection;
	}

	public CopyOfTotalUsdRequirementDetails() {
		
	}
	
	public BigDecimal getUsdCurrentBalanace() {
		return usdCurrentBalanace;
	}
	public void setUsdCurrentBalanace(BigDecimal usdCurrentBalanace) {
		this.usdCurrentBalanace = usdCurrentBalanace;
	}

	public CopyOfTotalUsdRequirementDetails(BigDecimal usdCurrentBalanace,
			double calUsdSaleProjection, double calFcTousdRequirement,
			double calTotalUsdrequirement) {
		super();
		this.usdCurrentBalanace = usdCurrentBalanace;
		this.calUsdSaleProjection = calUsdSaleProjection;
		this.calFcTousdRequirement = calFcTousdRequirement;
		this.calTotalUsdrequirement = calTotalUsdrequirement;
	}
	
	
	
	
	
	
}
