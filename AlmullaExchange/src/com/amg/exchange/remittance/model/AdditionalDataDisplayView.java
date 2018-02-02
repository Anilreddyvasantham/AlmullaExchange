package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_ADDITIONAL_DATA_DISPLAY")
public class AdditionalDataDisplayView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal serviceApplicabilityRuleId;
	private BigDecimal applicationCountryId;
	private BigDecimal routingCountryId;
	private BigDecimal routingCurrencyId;
	private BigDecimal remittanceModeId;
	private BigDecimal deliveryModeId;
	private String flexField;
	private String fieldDescription;
	private String fieldType;
	private BigDecimal fieldLength;
	private String isRendered;
	private String isRequired;
	private BigDecimal minLength;
	private BigDecimal maxLength;
	private String fieldBehaviour;
	private String validationsReq;
	private String isActive;
	
	@Id
	@Column(name = "SERVICE_ID")
	public BigDecimal getServiceApplicabilityRuleId() {
		return serviceApplicabilityRuleId;
	}
	public void setServiceApplicabilityRuleId(BigDecimal serviceApplicabilityRuleId) {
		this.serviceApplicabilityRuleId = serviceApplicabilityRuleId;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "ROUTING_COUNTRY_ID")
	public BigDecimal getRoutingCountryId() {
		return routingCountryId;
	}
	public void setRoutingCountryId(BigDecimal routingCountryId) {
		this.routingCountryId = routingCountryId;
	}
	
	@Column(name = "ROUTING_CURRENCY_ID")
	public BigDecimal getRoutingCurrencyId() {
		return routingCurrencyId;
	}
	public void setRoutingCurrencyId(BigDecimal routingCurrencyId) {
		this.routingCurrencyId = routingCurrencyId;
	}
	
	@Column(name = "REMITTANCE_MODE_ID")
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}
	
	@Column(name = "DELIVERY_MODE_ID")
	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}
	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}
	
	@Column(name = "FLEX_FIELD")
	public String getFlexField() {
		return flexField;
	}
	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}
	
	@Column(name = "FIELD_DESCRIPTION")
	public String getFieldDescription() {
		return fieldDescription;
	}
	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}
	
	@Column(name = "FIELD_TYPE")
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	@Column(name = "FIELD_LENGTH")
	public BigDecimal getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(BigDecimal fieldLength) {
		this.fieldLength = fieldLength;
	}
	
	@Column(name = "IS_RENDERED")
	public String getIsRendered() {
		return isRendered;
	}
	public void setIsRendered(String isRendered) {
		this.isRendered = isRendered;
	}
	
	@Column(name = "IS_REQUIRED")
	public String getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	
	@Column(name = "MIN_LENGTH")
	public BigDecimal getMinLength() {
		return minLength;
	}
	public void setMinLength(BigDecimal minLength) {
		this.minLength = minLength;
	}
	
	@Column(name = "MAX_LENGTH")
	public BigDecimal getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(BigDecimal maxLength) {
		this.maxLength = maxLength;
	}
	
	@Column(name = "FIELD_BEHAVIOUR")
	public String getFieldBehaviour() {
		return fieldBehaviour;
	}
	public void setFieldBehaviour(String fieldBehaviour) {
		this.fieldBehaviour = fieldBehaviour;
	}
	
	@Column(name = "VALIDATIONS")
	public String getValidationsReq() {
		return validationsReq;
	}
	public void setValidationsReq(String validationsReq) {
		this.validationsReq = validationsReq;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	

}
