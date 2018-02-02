package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class DistrictBean implements Serializable{
	private BigDecimal districId;
	private String districName;
	public  DistrictBean(BigDecimal districId, String districName) {
		this.districId = districId;
		this.districName = districName;
	}
	
	public BigDecimal getDistricId() {
		return districId;
	}
	public void setDistricId(BigDecimal districId) {
		this.districId = districId;
	}
	public String getDistricName() {
		return districName;
	}
	public void setDistricName(String districName) {
		this.districName = districName;
	}

}
