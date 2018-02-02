package com.amg.exchange.wuh2h.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_WU_PURPOSE_CODE")
public class DynamicPurposeCode implements Serializable { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String idNo;
	private String purposeCode;
	private String purposeCodeDesc;
	private String fieldId;
	private String fieldName;
	private String isoCountryCode;
	
	@Id
	@Column(name="ID_NO")
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	@Column(name="WU_PURPOSE_CODE")
	public String getPurposeCode() {
		return purposeCode;
	}
	public void setPurposeCode(String purposeCode) {
		this.purposeCode = purposeCode;
	}
	
	@Column(name="WU_PURPOSE_DESCRIPTION")
	public String getPurposeCodeDesc() {
		return purposeCodeDesc;
	}
	public void setPurposeCodeDesc(String purposeCodeDesc) {
		this.purposeCodeDesc = purposeCodeDesc;
	}
	
	@Column(name="WU_FIELD_ID")
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	
	@Column(name="WU_FIELD_NAME")
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Column(name="WU_COUNTRY_ISO_CODE")
	public String getIsoCountryCode() {
		return isoCountryCode;
	}
	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}
	
	
}
