package com.amg.exchange.remittance.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_COUNTRY_ACCOUNT_TYPE")
public class AccountTypeFromView {

	String flexiFiled;
	String amiecCode;
	String amiecDesc;
	BigDecimal srNo;
	BigDecimal countryId;
	BigDecimal additionalBankRuleId;
	BigDecimal additionalAmiecId;



@Column(name="FLEX_FIELD")
public String getFlexiFiled() {
	return flexiFiled;
}
public void setFlexiFiled(String flexiFiled) {
	this.flexiFiled = flexiFiled;
}
@Column(name="AMIEC_CODE")
public String getAmiecCode() {
	return amiecCode;
}
public void setAmiecCode(String amiecCode) {
	this.amiecCode = amiecCode;
}
@Column(name="AMIEC_DESC")
public String getAmiecDesc() {
	return amiecDesc;
}
public void setAmiecDesc(String amiecDesc) {
	this.amiecDesc = amiecDesc;
}
@Id
@Column(name="SRL_ID")
public BigDecimal getSrNo() {
	return srNo;
}
public void setSrNo(BigDecimal srNo) {
	this.srNo = srNo;
}
@Column(name="COUNTRY_ID")
public BigDecimal getCountryId() {
	return countryId;
}
public void setCountryId(BigDecimal countryId) {
	this.countryId = countryId;
}
@Column(name="ADDITIONAL_BANK_RULE_ID")
public BigDecimal getAdditionalBankRuleId() {
	return additionalBankRuleId;
}
public void setAdditionalBankRuleId(BigDecimal additionalBankRuleId) {
	this.additionalBankRuleId = additionalBankRuleId;
}
@Column(name="ADDITIONAL_BANK_RULE_AMIEC_ID")
public BigDecimal getAdditionalAmiecId() {
	return additionalAmiecId;
}
public void setAdditionalAmiecId(BigDecimal additionalAmiecId) {
	this.additionalAmiecId = additionalAmiecId;
}
}