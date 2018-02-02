package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_FLEX_FIELDS")
public class FlexFiledView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal flexiFieldId;
	private String fieldName;
	private String description;

	@Id
	@Column(name = "FLEX_FIELD_ID")
	public BigDecimal getFlexiFieldId() {
		return flexiFieldId;
	}

	public void setFlexiFieldId(BigDecimal flexiFieldId) {
		this.flexiFieldId = flexiFieldId;
	}

	@Column(name = "FIELD_NAME")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
