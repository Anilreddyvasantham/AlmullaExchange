package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class AddDynamicLebel {
	
	private String lebelDesc;
	private String flexiField;
	private String variableName;
	private BigDecimal lebelId;
	private String fieldType;
	private String navicable;
	private String mandatory;
	private BigDecimal minLenght;
	private BigDecimal maxLenght;
	private String validation;
	private String isActive;
	private BigDecimal fieldLength;
	private Boolean required = false;
	
	

	public AddDynamicLebel() {
		
	}

	
	

	public AddDynamicLebel(String lebelDesc, String flexiField,
			String variableName, BigDecimal lebelId, String fieldType,
			String navicable, String mandatory, BigDecimal minLenght,
			BigDecimal maxLenght, String validation, String isActive,
			BigDecimal fieldLength,Boolean required) {
		super();
		this.lebelDesc = lebelDesc;
		this.flexiField = flexiField;
		this.variableName = variableName;
		this.lebelId = lebelId;
		this.fieldType = fieldType;
		this.navicable = navicable;
		this.mandatory = mandatory;
		this.minLenght = minLenght;
		this.maxLenght = maxLenght;
		this.validation = validation;
		this.isActive = isActive;
		this.fieldLength = fieldLength;
		this.required = this.required;
	}




	public String getLebelDesc() {
		return lebelDesc;
	}

	public void setLebelDesc(String lebelDesc) {
		this.lebelDesc = lebelDesc;
	}


	public String getVariableName() {
		return variableName;
	}


	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}


	public String getFlexiField() {
		return flexiField;
	}


	public void setFlexiField(String flexiField) {
		this.flexiField = flexiField;
	}


	public BigDecimal getLebelId() {
		return lebelId;
	}


	public void setLebelId(BigDecimal lebelId) {
		this.lebelId = lebelId;
	}


	public String getFieldType() {
		return fieldType;
	}


	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}


	public String getNavicable() {
		return navicable;
	}


	public void setNavicable(String navicable) {
		this.navicable = navicable;
	}


	public String getMandatory() {
		return mandatory;
	}


	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}


	public BigDecimal getMinLenght() {
		return minLenght;
	}


	public void setMinLenght(BigDecimal minLenght) {
		this.minLenght = minLenght;
	}


	public BigDecimal getMaxLenght() {
		return maxLenght;
	}


	public void setMaxLenght(BigDecimal maxLenght) {
		this.maxLenght = maxLenght;
	}


	public String getValidation() {
		return validation;
	}


	public void setValidation(String validation) {
		this.validation = validation;
	}


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public BigDecimal getFieldLength() {
		return fieldLength;
	}


	public void setFieldLength(BigDecimal fieldLength) {
		this.fieldLength = fieldLength;
	}




	public Boolean getRequired() {
		return required;
	}




	public void setRequired(Boolean required) {
		this.required = required;
	}



	

}
