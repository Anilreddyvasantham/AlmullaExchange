package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;

public class ServiceApplicabilityRuleDataTable {

	private BigDecimal serviceApplivcabilityRuleId = null; //PK
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal remittanceModeId;
	private BigDecimal deliveryId;
	private String fieldName;
	private String fielDesc;
	private String navigable;
	private String mandatory;
	private String fielType;
	private String fielLength;
	private String validation;
	private BigDecimal min;
	private BigDecimal max;
	private int serialNo;
	private String countryName;
	private String currency;
	private String remittance;
	private String delivery;
	private String createdBy;
	private Date createdDate;

	private BigDecimal id;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;

	private Boolean isCheck =false;
	private Boolean disableCheck;
	private String userName;



	public Boolean getDisableCheck() {
		return disableCheck;
	}
	public void setDisableCheck(Boolean disableCheck) {
		this.disableCheck = disableCheck;
	}

	public Boolean getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getServiceApplivcabilityRuleId() {
		return serviceApplivcabilityRuleId;
	}
	public void setServiceApplivcabilityRuleId(BigDecimal serviceApplivcabilityRuleId) {
		this.serviceApplivcabilityRuleId = serviceApplivcabilityRuleId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	public BigDecimal getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(BigDecimal deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFielDesc() {
		return fielDesc;
	}
	public void setFielDesc(String fielDesc) {
		this.fielDesc = fielDesc;
	}

	public String getNavigable() {
		return navigable;
	}
	public void setNavigable(String navigable) {
		this.navigable = navigable;
	}

	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getFielType() {
		return fielType;
	}
	public void setFielType(String fielType) {
		this.fielType = fielType;
	}

	public String getFielLength() {
		return fielLength;
	}
	public void setFielLength(String fielLength) {
		this.fielLength = fielLength;
	}

	public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}

	public BigDecimal getMin() {
		return min;
	}
	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}
	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRemittance() {
		return remittance;
	}
	public void setRemittance(String remittance) {
		this.remittance = remittance;
	}

	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	  private BigDecimal serviceApplanguageId;
	  private String landuageName;

	  

	  public BigDecimal getServiceApplanguageId() {
	  	  return serviceApplanguageId;
	  }
	  public void setServiceApplanguageId(BigDecimal serviceApplanguageId) {
	  	  this.serviceApplanguageId = serviceApplanguageId;
	  }
	  public String getLanduageName() {
		    return landuageName;
	  }

	  public void setLanduageName(String landuageName) {
		    this.landuageName = landuageName;
	  }
	
	  private List<LanguageType> lstLanguageTypes;

	  public List<LanguageType> getLstLanguageTypes() {
	  	  return lstLanguageTypes;
	  }
	  public void setLstLanguageTypes(List<LanguageType> lstLanguageTypes) {
	  	  this.lstLanguageTypes = lstLanguageTypes;
	  }
	  
	  

}
