package com.amg.exchange.wuh2h.bean;

import java.io.Serializable;

public class WUDynamicPurposeCodeDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String purposeCode;
	private String purposeCodeDesc;
	private String fieldId;
	private String fieldName;
	private String isoCountryCode;
	public String getPurposeCode() {
		return purposeCode;
	}
	public void setPurposeCode(String purposeCode) {
		this.purposeCode = purposeCode;
	}
	public String getPurposeCodeDesc() {
		return purposeCodeDesc;
	}
	public void setPurposeCodeDesc(String purposeCodeDesc) {
		this.purposeCodeDesc = purposeCodeDesc;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getIsoCountryCode() {
		return isoCountryCode;
	}
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}
	
	
}
