package com.amg.exchange.bean;

public class Nationality {
	
	private String nationalityCode;
	private String nationalityName;
	
	public Nationality(String code, String name) {
		this.nationalityCode = code;
		this.nationalityName = name;
	}

	public String getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}
	
	

}
