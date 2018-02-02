package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Author Rahamathali Shaik
*/
@Entity
@Table(name= "VW_CORP_CUSTOMER_INFO")
public class CorporateCustomerInfoView implements Serializable{
	

	private static final long serialVersionUID = 2753494597989191331L;
	private BigDecimal corpAddIdNumber;
	private BigDecimal customerId;
	private BigDecimal languageId;
	private BigDecimal objectiveId;
	private String objectiveDesc;
	private String objectiveType;

	
	
	@Column(name = "CUSTOMER_ID")	
	public BigDecimal getCustomerId() {
		return customerId;
	}
	
	@Id
	@Column(name = "OBJECTIVE_ID")
	public BigDecimal getObjectiveId() {
		return objectiveId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setObjectiveId(BigDecimal objectiveId) {
		this.objectiveId = objectiveId;
	}
	@Column(name = "CORP_ADDL_ID")
	public BigDecimal getCorpAddIdNumber() {
		return corpAddIdNumber;
	}
	
	
	@Column(name = "LANGUAGE_ID")
	public BigDecimal getLanguageId() {
		return languageId;
	}
	

	@Column(name = "OBJECTIVE_DESC")
	public String getObjectiveDesc() {
		return objectiveDesc;
	}
	@Column(name = "OBJECTIVE_TYPE")
	public String getObjectiveType() {
		return objectiveType;
	}
	public void setCorpAddIdNumber(BigDecimal corpAddIdNumber) {
		this.corpAddIdNumber = corpAddIdNumber;
	}

	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}

	public void setObjectiveDesc(String objectiveDesc) {
		this.objectiveDesc = objectiveDesc;
	}
	public void setObjectiveType(String objectiveType) {
		this.objectiveType = objectiveType;
	}
}