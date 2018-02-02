package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************************************************************************************************

File		: ServiceApplicabilityRule.java

Project	: AlmullaExchange

Package	: com.amg.exchange.remittance.model

Created	:	
				Date	: 27-Feb-2015
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	: 27-Feb-2015 
				By		: Nazish Ehsan Hashmi
				Revision:

Description: TODO 

********************************************************************************************************************/
@Entity
@Table(name="EX_VIEW_SERVICE_APPLTY_RULE")

public class ViewServiceAppRule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal serviceAppRuleId;
	private String fieldName;
	private String fieldDesc;
	private String fieldType;
	private String navicable;
	private String mandatory;
	private BigDecimal minLenght;
	private BigDecimal maxLenght;
	private String validate;
	private BigDecimal fieldLength;
	
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal deliveryModeId;
	private BigDecimal remittanceModeId;
	private BigDecimal serviceApplicabilityRuleId;
	private String createdBy;
	private Date craetedDate;
	
	
	@Id
	@Column(name="SERVICE_APPLTY_RULE_ID")
	public BigDecimal getServiceAppRuleId() {
		return serviceAppRuleId;
	}
	public void setServiceAppRuleId(BigDecimal serviceAppRuleId) {
		this.serviceAppRuleId = serviceAppRuleId;
	}
	
	@Column(name="FIELD_NAME")
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Column(name="FIELD_DESC")
	public String getFieldDesc() {
		return fieldDesc;
	}
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}
	
	@Column(name="FIELD_TYPE")
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	@Column(name="NAVICABLE")
	public String getNavicable() {
		return navicable;
	}
	public void setNavicable(String navicable) {
		this.navicable = navicable;
	}
	
	@Column(name="MANDATORY")
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	
	@Column(name="MIN_LENGTH")
	public BigDecimal getMinLenght() {
		return minLenght;
	}
	public void setMinLenght(BigDecimal minLenght) {
		this.minLenght = minLenght;
	}
	
	@Column(name="MAX_LENGTH")
	public BigDecimal getMaxLenght() {
		return maxLenght;
	}
	public void setMaxLenght(BigDecimal maxLenght) {
		this.maxLenght = maxLenght;
	}
	
	@Column(name="VALIDATIONS")
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}

	@Column(name="FIELD_LENGTH")
	public BigDecimal getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(BigDecimal fieldLength) {
		this.fieldLength = fieldLength;
	}
	
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	@Column(name="CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name="DELIVERY_MODE_ID")
	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}
	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}
	
	@Column(name="REMITTANCE_MODE_ID")
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}
	
	@Column(name="SERVICE_APPLICABILITY_RULE_ID")
	public BigDecimal getServiceApplicabilityRuleId() {
		return serviceApplicabilityRuleId;
	}
	public void setServiceApplicabilityRuleId(BigDecimal serviceApplicabilityRuleId) {
		this.serviceApplicabilityRuleId = serviceApplicabilityRuleId;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCraetedDate() {
		return craetedDate;
	}
	public void setCraetedDate(Date craetedDate) {
		this.craetedDate = craetedDate;
	}

	
}
