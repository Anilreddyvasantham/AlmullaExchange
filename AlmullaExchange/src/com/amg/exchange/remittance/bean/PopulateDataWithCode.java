package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class PopulateDataWithCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal id;
    private String code;
    private String name;
    
	public PopulateDataWithCode() {
		super();
	}

	public PopulateDataWithCode(BigDecimal id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
