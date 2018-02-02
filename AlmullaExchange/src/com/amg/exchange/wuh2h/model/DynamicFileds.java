package com.amg.exchange.wuh2h.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_WU_DYNAMIC_FIELDS")
public class DynamicFileds implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String idNo;
	private String fieldName;
	private String fieldLength;
	private String fieldId;
	private String fieldRequired;
	private String fieldType;
	private String countryISOCode;
	
	@Id
	@Column(name="ID_NO")
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	@Column(name="WU_FIELD_NAME")
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Column(name="WU_FIELD_SIZE")
	public String getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}
	
	@Column(name="WU_FIELD_ID")
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	
	@Column(name="WU_FIELD_IS_REQUIRED")
	public String getFieldRequired() {
		return fieldRequired;
	}
	public void setFieldRequired(String fieldRequired) {
		this.fieldRequired = fieldRequired;
	}
	
	@Column(name="WU_FIELD_TYPE")
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	@Column(name="WU_COUNTRY_ISO_CODE")
	public String getCountryISOCode() {
		return countryISOCode;
	}
	public void setCountryISOCode(String countryISOCode) {
		this.countryISOCode = countryISOCode;
	}
	
	
}
