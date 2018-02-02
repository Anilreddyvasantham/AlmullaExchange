package com.amg.exchange.bean;

import java.io.Serializable;

public class District implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String districtName;
	private String districtCode;
	
	public District(String code, String name){
		this.districtCode = code;
		this.districtName = name;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	
	
}
