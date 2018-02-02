package com.amg.exchange.loyalty.bean;

import java.math.BigDecimal;
import java.util.Date;

public class LoyaltyParameterSettingDataTable {

	  // page level Variables
	  private BigDecimal ltyParameterPk;
	  private BigDecimal categoryId;
	  private String categoryName;
	  private BigDecimal loyaltyTypeId;
	  private String loyaltyName;
	  private BigDecimal countryId;
	  private String countryName;
	  private BigDecimal bankId;
	  private String bankname;
	  private BigDecimal currencyId;
	  private String currencyName;
	  private BigDecimal operatorId;
	  private String operatorName;
	  private BigDecimal amount;
	  private BigDecimal remittanceModeId;
	  private String remittanceName;
	  private BigDecimal deliveryModeId;
	  private String deliveryName;
	  private BigDecimal noOfDays;
	  private BigDecimal dayOfTheWeekId;
	  private String dayOfTheWeekName;
	  private BigDecimal fromAmount;
	  private BigDecimal toAmount;
	  private Date fromTime;
	  private Date toTime;
	  private BigDecimal loyaltyPoints;
	  private String relatopnShipToIntroDuction;
	  private BigDecimal stateId;
	  private String stateName;
	  private BigDecimal applicationCountryId;
	  private String templateCode;
	  private String templateCriteria;
	  // common Variables
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String isActive;
	  private String remarks;
	  private String dynamicLabelForActivateDeactivate;
	  //boolean
	  private Boolean renderEditButton=false;
	  private Boolean booEditButton = false;
	  private Boolean ifEditClicked=false;
	  private Boolean remarksCheck=false;
	  private Boolean activeRecordCheck=false;
	  private Boolean permentDeleteCheck=false;
	  
	  public BigDecimal getLtyParameterPk() {
	  	  return ltyParameterPk;
	  }

	  public void setLtyParameterPk(BigDecimal ltyParameterPk) {
	  	  this.ltyParameterPk = ltyParameterPk;
	  }

	  public BigDecimal getCategoryId() {
		    return categoryId;
	  }

	  public void setCategoryId(BigDecimal categoryId) {
		    this.categoryId = categoryId;
	  }

	  public String getCategoryName() {
		    return categoryName;
	  }

	  public void setCategoryName(String categoryName) {
		    this.categoryName = categoryName;
	  }

	  public BigDecimal getLoyaltyTypeId() {
		    return loyaltyTypeId;
	  }

	  public void setLoyaltyTypeId(BigDecimal loyaltyTypeId) {
		    this.loyaltyTypeId = loyaltyTypeId;
	  }

	  public String getLoyaltyName() {
		    return loyaltyName;
	  }

	  public void setLoyaltyName(String loyaltyName) {
		    this.loyaltyName = loyaltyName;
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

	  public BigDecimal getBankId() {
		    return bankId;
	  }

	  public void setBankId(BigDecimal bankId) {
		    this.bankId = bankId;
	  }

	  public String getBankname() {
		    return bankname;
	  }

	  public void setBankname(String bankname) {
		    this.bankname = bankname;
	  }

	  public BigDecimal getCurrencyId() {
		    return currencyId;
	  }

	  public void setCurrencyId(BigDecimal currencyId) {
		    this.currencyId = currencyId;
	  }

	  public String getCurrencyName() {
		    return currencyName;
	  }

	  public void setCurrencyName(String currencyName) {
		    this.currencyName = currencyName;
	  }

	  public BigDecimal getOperatorId() {
		    return operatorId;
	  }

	  public void setOperatorId(BigDecimal operatorId) {
		    this.operatorId = operatorId;
	  }

	  public String getOperatorName() {
		    return operatorName;
	  }

	  public void setOperatorName(String operatorName) {
		    this.operatorName = operatorName;
	  }

	  public BigDecimal getAmount() {
		    return amount;
	  }

	  public void setAmount(BigDecimal amount) {
		    this.amount = amount;
	  }

	  public BigDecimal getRemittanceModeId() {
		    return remittanceModeId;
	  }

	  public void setRemittanceModeId(BigDecimal remittanceModeId) {
		    this.remittanceModeId = remittanceModeId;
	  }

	  public String getRemittanceName() {
		    return remittanceName;
	  }

	  public void setRemittanceName(String remittanceName) {
		    this.remittanceName = remittanceName;
	  }

	  public BigDecimal getDeliveryModeId() {
		    return deliveryModeId;
	  }

	  public void setDeliveryModeId(BigDecimal deliveryModeId) {
		    this.deliveryModeId = deliveryModeId;
	  }

	  public String getDeliveryName() {
		    return deliveryName;
	  }

	  public void setDeliveryName(String deliveryName) {
		    this.deliveryName = deliveryName;
	  }

	  public BigDecimal getNoOfDays() {
		    return noOfDays;
	  }

	  public void setNoOfDays(BigDecimal noOfDays) {
		    this.noOfDays = noOfDays;
	  }

	  public BigDecimal getDayOfTheWeekId() {
		    return dayOfTheWeekId;
	  }

	  public void setDayOfTheWeekId(BigDecimal dayOfTheWeekId) {
		    this.dayOfTheWeekId = dayOfTheWeekId;
	  }

	  public String getDayOfTheWeekName() {
		    return dayOfTheWeekName;
	  }

	  public void setDayOfTheWeekName(String dayOfTheWeekName) {
		    this.dayOfTheWeekName = dayOfTheWeekName;
	  }

	  public BigDecimal getFromAmount() {
		    return fromAmount;
	  }

	  public void setFromAmount(BigDecimal fromAmount) {
		    this.fromAmount = fromAmount;
	  }

	  public BigDecimal getToAmount() {
		    return toAmount;
	  }

	  public void setToAmount(BigDecimal toAmount) {
		    this.toAmount = toAmount;
	  }

	  public Date getFromTime() {
		    return fromTime;
	  }

	  public void setFromTime(Date fromTime) {
		    this.fromTime = fromTime;
	  }

	  public Date getToTime() {
		    return toTime;
	  }

	  public void setToTime(Date toTime) {
		    this.toTime = toTime;
	  }

	  public BigDecimal getLoyaltyPoints() {
		    return loyaltyPoints;
	  }

	  public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		    this.loyaltyPoints = loyaltyPoints;
	  }

	  public String getRelatopnShipToIntroDuction() {
		    return relatopnShipToIntroDuction;
	  }

	  public void setRelatopnShipToIntroDuction(String relatopnShipToIntroDuction) {
		    this.relatopnShipToIntroDuction = relatopnShipToIntroDuction;
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

	  public String getApprovedBy() {
		    return approvedBy;
	  }

	  public void setApprovedBy(String approvedBy) {
		    this.approvedBy = approvedBy;
	  }

	  public Date getApprovedDate() {
		    return approvedDate;
	  }

	  public void setApprovedDate(Date approvedDate) {
		    this.approvedDate = approvedDate;
	  }

	  public String getIsActive() {
		    return isActive;
	  }

	  public void setIsActive(String isActive) {
		    this.isActive = isActive;
	  }

	  public String getRemarks() {
		    return remarks;
	  }

	  public void setRemarks(String remarks) {
		    this.remarks = remarks;
	  }

	  public String getDynamicLabelForActivateDeactivate() {
		    return dynamicLabelForActivateDeactivate;
	  }

	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		    this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }

	  public BigDecimal getStateId() {
	  	  return stateId;
	  }

	  public void setStateId(BigDecimal stateId) {
	  	  this.stateId = stateId;
	  }

	  public String getStateName() {
	  	  return stateName;
	  }

	  public void setStateName(String stateName) {
	  	  this.stateName = stateName;
	  }

	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }

	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }

	  public Boolean getRenderEditButton() {
	  	  return renderEditButton;
	  }

	  public void setRenderEditButton(Boolean renderEditButton) {
	  	  this.renderEditButton = renderEditButton;
	  }

	  public Boolean getBooEditButton() {
	  	  return booEditButton;
	  }

	  public void setBooEditButton(Boolean booEditButton) {
	  	  this.booEditButton = booEditButton;
	  }

	  public Boolean getIfEditClicked() {
	  	  return ifEditClicked;
	  }

	  public void setIfEditClicked(Boolean ifEditClicked) {
	  	  this.ifEditClicked = ifEditClicked;
	  }

	  public Boolean getRemarksCheck() {
	  	  return remarksCheck;
	  }

	  public void setRemarksCheck(Boolean remarksCheck) {
	  	  this.remarksCheck = remarksCheck;
	  }

	  public Boolean getActiveRecordCheck() {
	  	  return activeRecordCheck;
	  }

	  public void setActiveRecordCheck(Boolean activeRecordCheck) {
	  	  this.activeRecordCheck = activeRecordCheck;
	  }

	  public Boolean getPermentDeleteCheck() {
	  	  return permentDeleteCheck;
	  }

	  public void setPermentDeleteCheck(Boolean permentDeleteCheck) {
	  	  this.permentDeleteCheck = permentDeleteCheck;
	  }

	  public String getTemplateCode() {
	  	  return templateCode;
	  }

	  public void setTemplateCode(String templateCode) {
	  	  this.templateCode = templateCode;
	  }

	  public String getTemplateCriteria() {
	  	  return templateCriteria;
	  }

	  public void setTemplateCriteria(String templateCriteria) {
	  	  this.templateCriteria = templateCriteria;
	  }
	  

}
