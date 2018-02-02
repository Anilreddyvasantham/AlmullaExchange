package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class AdditionBankRuleMapApprovalDataTable {
	
	private BigDecimal addtionBankRuleMapPK;
	private String flexField;
	private String fieldName;
	private BigDecimal countryId;
	private String countryName;
	private String orderNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private String isActive;
	private Date modifiedDate;
	
	
	
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public BigDecimal getAddtionBankRuleMapPK() {
		return addtionBankRuleMapPK;
	}
	public void setAddtionBankRuleMapPK(BigDecimal addtionBankRuleMapPK) {
		this.addtionBankRuleMapPK = addtionBankRuleMapPK;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	  public Date getCreatedDate() {
		    return createdDate;
	  }

	  public void setCreatedDate(Date createdDate) {
		    this.createdDate = createdDate;
	  }

	  public Date getModifiedDate() {
		    return modifiedDate;
	  }

	  public void setModifiedDate(Date modifiedDate) {
		    this.modifiedDate = modifiedDate;
	  }
	
	
	
	

}
