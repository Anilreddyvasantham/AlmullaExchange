package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class AdditionBankRuleWithAlMullaCodeApprovalDataTable {

	
	private BigDecimal addtionAlmullaBankPK;
	private String flexField;
	private String fieldName;
	private BigDecimal countryId;
	private String countryName;
	private String almullaDescription;
	private String almullaCode;
	private String createdBy;
	private String modifiedBy;
	private String isActive;
	private Boolean isCheck =false;
	private Boolean disableCheck;
	private String userName;
	
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public BigDecimal getAddtionAlmullaBankPK() {
		return addtionAlmullaBankPK;
	}
	public void setAddtionAlmullaBankPK(BigDecimal addtionAlmullaBankPK) {
		this.addtionAlmullaBankPK = addtionAlmullaBankPK;
	}
	
	public String getFlexField() {
		return flexField;
	}
	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getAlmullaDescription() {
		return almullaDescription;
	}
	public void setAlmullaDescription(String almullaDescription) {
		this.almullaDescription = almullaDescription;
	}
	
	public String getAlmullaCode() {
		return almullaCode;
	}
	public void setAlmullaCode(String almullaCode) {
		this.almullaCode = almullaCode;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public Boolean getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}
	
	public Boolean getDisableCheck() {
		return disableCheck;
	}
	public void setDisableCheck(Boolean disableCheck) {
		this.disableCheck = disableCheck;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	
	
	
}
