package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class CityBean implements Serializable{
	private BigDecimal cityId;
	private String cityName;
    CityBean(BigDecimal cityId,String cityName) {
		 this.cityId = cityId;
		 this.cityName = cityName;
	 }
	public BigDecimal getCityId() {
		return cityId;
	}
	public void setCityId(BigDecimal cityid) {
		this.cityId = cityid;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
