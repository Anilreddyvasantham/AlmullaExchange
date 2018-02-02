package com.amg.exchange.remittance.bean;

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
	private BigDecimal additionalBankRuleId;
	private String fieldName;
	private String flexField;
	private BigDecimal orderNo;
	private BigDecimal serviceApplicabiltyRuleiD;
	private BigDecimal applicationCountryId;
	private BigDecimal currencyId;
	private BigDecimal remittanceModeId;
	private BigDecimal deliverModeId;
	private BigDecimal bankId;
	private BigDecimal countryId;
	private BigDecimal additionalBankRuleAdditionalDataId;
	private String filedName;
	private String bankCode;
	private String bankDescription;
	private BigDecimal additionalBankRuleAmiecId;
	private String amiecCode;
	private String amiecDescription;
	
	
	/**
	 * 
	 */
	public AdditionalBankDetailsView() {
		super();
	}
	
	
	public AdditionalBankDetailsView(BigDecimal additionalBankRuleId,
			String fieldName, String flexField, BigDecimal orderNo,
			BigDecimal serviceApplicabiltyRuleiD,
			BigDecimal applicationCountryId, BigDecimal currencyId,
			BigDecimal remittanceModeId, BigDecimal deliverModeId,
			BigDecimal bankId, BigDecimal countryId,
			BigDecimal additionalBankRuleAdditionalDataId, String filedName,
			String bankCode, String bankDescription,
			BigDecimal additionalBankRuleAmiecId, String amiecCode,
			String amiecDescription) {
		super();
		this.additionalBankRuleId = additionalBankRuleId;
		this.fieldName = fieldName;
		this.flexField = flexField;
		this.orderNo = orderNo;
		this.serviceApplicabiltyRuleiD = serviceApplicabiltyRuleiD;
		this.applicationCountryId = applicationCountryId;
		this.currencyId = currencyId;
		this.remittanceModeId = remittanceModeId;
		this.deliverModeId = deliverModeId;
		this.bankId = bankId;
		this.countryId = countryId;
		this.additionalBankRuleAdditionalDataId = additionalBankRuleAdditionalDataId;
		this.filedName = filedName;
		this.bankCode = bankCode;
		this.bankDescription = bankDescription;
		this.additionalBankRuleAmiecId = additionalBankRuleAmiecId;
		this.amiecCode = amiecCode;
		this.amiecDescription = amiecDescription;
	}


	@Id
	@Column(name="ADDITIONAL_BANK_RULE_ID")
	public BigDecimal getAdditionalBankRuleId() {
		return additionalBankRuleId;
	}
	@Column(name="FIELD_NAME")
	public String getFieldName() {
		return fieldName;
	}
	@Column(name="FLEX_FIELD")
	public String getFlexField() {
		return flexField;
	}
	@Column(name="ORDER_NO")
	public BigDecimal getOrderNo() {
		return orderNo;
	}
	@Column(name="SERVICE_APPLICABILITY_RULE_ID")
	public BigDecimal getServiceApplicabiltyRuleiD() {
		return serviceApplicabiltyRuleiD;
	}
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	@Column(name="CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	@Column(name="REMITTANCE_MODE_ID")
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	@Column(name="DELIVERY_MODE_ID")
	public BigDecimal getDeliverModeId() {
		return deliverModeId;
	}
	@Column(name="BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	@Column(name="COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	@Column(name="ADD_BANK_RULE_ADDDATA_ID")
	public BigDecimal getAdditionalBankRuleAdditionalDataId() {
		return additionalBankRuleAdditionalDataId;
	}
	@Column(name="FIELD_TYPE")
	public String getFiledName() {
		return filedName;
	}
	@Column(name="BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	@Column(name="BANK_DESC")
	public String getBankDescription() {
		return bankDescription;
	}
	@Column(name="ADDITIONAL_BANK_RULE_AMIEC_ID")
	public BigDecimal getAdditionalBankRuleAmiecId() {
		return additionalBankRuleAmiecId;
	}
	@Column(name="AMIEC_CODE")
	public String getAmiecCode() {
		return amiecCode;
	}
	@Column(name="AMIEC_DESC")
	public String getAmiecDescription() {
		return amiecDescription;
	}
	public void setAdditionalBankRuleId(BigDecimal additionalBankRuleId) {
		this.additionalBankRuleId = additionalBankRuleId;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}
	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}
	public void setServiceApplicabiltyRuleiD(BigDecimal serviceApplicabiltyRuleiD) {
		this.serviceApplicabiltyRuleiD = serviceApplicabiltyRuleiD;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}
	public void setDeliverModeId(BigDecimal deliverModeId) {
		this.deliverModeId = deliverModeId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public void setAdditionalBankRuleAdditionalDataId(
			BigDecimal additionalBankRuleAdditionalDataId) {
		this.additionalBankRuleAdditionalDataId = additionalBankRuleAdditionalDataId;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public void setBankDescription(String bankDescription) {
		this.bankDescription = bankDescription;
	}
	public void setAdditionalBankRuleAmiecId(BigDecimal additionalBankRuleAmiecId) {
		this.additionalBankRuleAmiecId = additionalBankRuleAmiecId;
	}
	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}
	public void setAmiecDescription(String amiecDescription) {
		this.amiecDescription = amiecDescription;
	}
}



	
	
