package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class PopulateData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal populateId = null;
	private String populateCode=null;
	private String populateName = null;
	
	public PopulateData() {
		super();
	}

	public BigDecimal getPopulateId() {
		return populateId;
	}
	
	public void setPopulateId(BigDecimal populateId) {
		this.populateId = populateId;
	}
	
	public String getPopulateName() {
		return populateName;
	}
	
	public void setPopulateName(String populateName) {
		this.populateName = populateName;
	}
	
	public String getPopulateCode() {
		return populateCode;
	}

	public void setPopulateCode(String populateCode) {
		this.populateCode = populateCode;
	}

	public PopulateData(BigDecimal populateId, String populateName) {
		super();
		this.populateId = populateId;
		this.populateName = populateName;
	}

	public PopulateData(BigDecimal populateId, String populateCode,
			String populateName) {
		super();
		this.populateId = populateId;
		this.populateCode = populateCode;
		this.populateName = populateName;
	}
	
	
	
}
