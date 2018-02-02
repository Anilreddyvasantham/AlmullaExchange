package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_ADDITIONAL_BANK_DETAILS")
public class AdditionalBankDetailsView implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  private BigDecimal srlId;
	  private String flexField;
	  private BigDecimal countryId;
	  private String amiecCode;
	  private String amieceDescription;
	  private BigDecimal bankId;
	  private String bankCode;
	  private String bankDescription;
	  private BigDecimal serviceApplicabilityRuleId;
	  private BigDecimal applicationCountryId;
	  private BigDecimal currencyId;
	  private BigDecimal remittanceId;
	  private BigDecimal deliveryId;
	  private String fieldType;

	  public AdditionalBankDetailsView() {
		    super();

	  }

	  public AdditionalBankDetailsView(BigDecimal srlId, String flexField, BigDecimal countryId, String amiecCode, String amieceDescription, BigDecimal bankId, String bankCode, String bankDescription, BigDecimal serviceApplicabilityRuleId, BigDecimal applicationCountryId, BigDecimal currencyId,
			      BigDecimal remittanceId, BigDecimal deliveryId, String fieldType) {
		    super();
		    this.srlId = srlId;
		    this.flexField = flexField;
		    this.countryId = countryId;
		    this.amiecCode = amiecCode;
		    this.amieceDescription = amieceDescription;
		    this.bankId = bankId;
		    this.bankCode = bankCode;
		    this.bankDescription = bankDescription;
		    this.serviceApplicabilityRuleId = serviceApplicabilityRuleId;
		    this.applicationCountryId = applicationCountryId;
		    this.currencyId = currencyId;
		    this.remittanceId = remittanceId;
		    this.deliveryId = deliveryId;
		    this.fieldType = fieldType;
	  }

	  @Id
	  @Column(name = "SRL_ID")
	  public BigDecimal getSrlId() {
		    return srlId;
	  }

	  public void setSrlId(BigDecimal srlId) {
		    this.srlId = srlId;
	  }

	  @Column(name = "FLEX_FIELD")
	  public String getFlexField() {
		    return flexField;
	  }

	  public void setFlexField(String flexField) {
		    this.flexField = flexField;
	  }

	  @Column(name = "COUNTRY_ID")
	  public BigDecimal getCountryId() {
		    return countryId;
	  }

	  public void setCountryId(BigDecimal countryId) {
		    this.countryId = countryId;
	  }

	  @Column(name = "AMIEC_CODE")
	  public String getAmiecCode() {
		    return amiecCode;
	  }

	  public void setAmiecCode(String amiecCode) {
		    this.amiecCode = amiecCode;
	  }

	  @Column(name = "AMIEC_DESCRIPTION")
	  public String getAmieceDescription() {
		    return amieceDescription;
	  }

	  public void setAmieceDescription(String amieceDescription) {
		    this.amieceDescription = amieceDescription;
	  }

	  @Column(name = "BANK_ID")
	  public BigDecimal getBankId() {
		    return bankId;
	  }

	  public void setBankId(BigDecimal bankId) {
		    this.bankId = bankId;
	  }

	  @Column(name = "BANK_CODE")
	  public String getBankCode() {
		    return bankCode;
	  }

	  public void setBankCode(String bankCode) {
		    this.bankCode = bankCode;
	  }

	  @Column(name = "BANK_DESCRIPTION")
	  public String getBankDescription() {
		    return bankDescription;
	  }

	  public void setBankDescription(String bankDescription) {
		    this.bankDescription = bankDescription;
	  }

	  @Column(name = "SERVICE_APPLICABILITY_RULE_ID")
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

	  @Column(name = "CURRENCY_ID")
	  public BigDecimal getCurrencyId() {
		    return currencyId;
	  }

	  public void setCurrencyId(BigDecimal currencyId) {
		    this.currencyId = currencyId;
	  }

	  @Column(name = "REMITTANCE_MODE_ID")
	  public BigDecimal getRemittanceId() {
		    return remittanceId;
	  }

	  public void setRemittanceId(BigDecimal remittanceId) {
		    this.remittanceId = remittanceId;
	  }

	  @Column(name = "DELIVERY_MODE_ID")
	  public BigDecimal getDeliveryId() {
		    return deliveryId;
	  }

	  public void setDeliveryId(BigDecimal deliveryId) {
		    this.deliveryId = deliveryId;
	  }

	  @Column(name = "FIELD_TYPE")
	  public String getFieldType() {
		    return fieldType;
	  }

	  public void setFieldType(String fieldType) {
		    this.fieldType = fieldType;
	  }

}
