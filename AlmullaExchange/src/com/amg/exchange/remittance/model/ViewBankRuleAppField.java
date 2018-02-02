package com.amg.exchange.remittance.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************************************************************************************************

File		: ServiceApplicabilityRule.java

Project	: AlmullaExchange

Package	: com.amg.exchange.remittance.model

Created	:	
				Date	: 26-Feb-2015
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	: 26-Feb-2015 
				By		: Nazish Ehsan Hashmi
				Revision:

Description: TODO 

********************************************************************************************************************/
@Entity
@Table(name="V_BANKRULE_APP_FIELDS")

public class ViewBankRuleAppField {
	
	private BigDecimal ParamDetailsId;
	private String fieldName;
	private String fudDesc;
	private String shdDesc;
	
	@Id
	@Column(name="PARAMETER_DETAILS_ID")
	public BigDecimal getParamDetailsId() {
		return ParamDetailsId;
	}
	public void setParamDetailsId(BigDecimal paramDetailsId) {
		ParamDetailsId = paramDetailsId;
	}
	
	@Column(name="FIELD_NAME")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Column(name="FUDESC")
	public String getFudDesc() {
		return fudDesc;
	}
	public void setFudDesc(String fudDesc) {
		this.fudDesc = fudDesc;
	}
	
	@Column(name="SHDESC")
	public String getShdDesc() {
		return shdDesc;
	}
	
	public void setShdDesc(String shdDesc) {
		this.shdDesc = shdDesc;
	}	


}
