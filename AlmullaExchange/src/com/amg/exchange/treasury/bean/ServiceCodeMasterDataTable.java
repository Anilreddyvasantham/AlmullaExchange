package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;


public class ServiceCodeMasterDataTable {
 private String serviceCode;
 private String serviceDescription;
 private String localServiceDescription;
 private BigDecimal serviceMasterPk;
 private BigDecimal serviceDescPk;
 private BigDecimal serviceDescLocalPk;
 
 private String createdBy;
 private Date createdDate;
 private String modfiedBy;
 private Date modifiedDate;
 private String isActive;
 private Boolean checkSave;
 private String remarks; 
 private BigDecimal serviceGroup;
 
 private Boolean remarkCheck=false;
 
 private Date activateDate;
 private String activateBy;
 private Boolean disableEdit = false;
 private Boolean booCheckUpdate = false;
 private Boolean booCheckDelete = false;
 
 private Boolean activateRecordCheck = false;
 
 
 
 
 
 public Boolean getActivateRecordCheck() {
	return activateRecordCheck;
}

public void setActivateRecordCheck(Boolean activateRecordCheck) {
	this.activateRecordCheck = activateRecordCheck;
}

public Date getActivateDate() {
	return activateDate;
}

public void setActivateDate(Date activateDate) {
	this.activateDate = activateDate;
}

public String getActivateBy() {
	return activateBy;
}

public void setActivateBy(String activateBy) {
	this.activateBy = activateBy;
}

public Boolean getRemarkCheck() {
	return remarkCheck;
}

public void setRemarkCheck(Boolean remarkCheck) {
	this.remarkCheck = remarkCheck;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public Boolean getCheckSave() {
	return checkSave;
}

public void setCheckSave(Boolean checkSave) {
	this.checkSave = checkSave;
}

public String getIsActive() {
	return isActive;
}

public void setIsActive(String isActive) {
	this.isActive = isActive;
}
private String dynamicLabelForActivateDeactivate;
 private Boolean renderEditButton=false;



 public Boolean getRenderEditButton() {
 	return renderEditButton;
 }

 public void setRenderEditButton(Boolean renderEditButton) {
 	this.renderEditButton = renderEditButton;
 }

 public String getDynamicLabelForActivateDeactivate() {
 	return dynamicLabelForActivateDeactivate;
 }

 public void setDynamicLabelForActivateDeactivate(
 		String dynamicLabelForActivateDeactivate) {
 	this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
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
public String getModfiedBy() {
	return modfiedBy;
}
public void setModfiedBy(String modfiedBy) {
	this.modfiedBy = modfiedBy;
}
public Date getModifiedDate() {
	return modifiedDate;
}
public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
}

 
 


public String getServiceCode() {
	return serviceCode;
}
public void setServiceCode(String serviceCode) {
	this.serviceCode = serviceCode;
}
public String getServiceDescription() {
	return serviceDescription;
}
public void setServiceDescription(String serviceDescription) {
	this.serviceDescription = serviceDescription;
}
public String getLocalServiceDescription() {
	return localServiceDescription;
}
public void setLocalServiceDescription(String localServiceDescription) {
	this.localServiceDescription = localServiceDescription;
}
public BigDecimal getServiceMasterPk() {
	return serviceMasterPk;
}
public void setServiceMasterPk(BigDecimal serviceMasterPk) {
	this.serviceMasterPk = serviceMasterPk;
}
public BigDecimal getServiceDescPk() {
	return serviceDescPk;
}
public void setServiceDescPk(BigDecimal serviceDescPk) {
	this.serviceDescPk = serviceDescPk;
}
public BigDecimal getServiceDescLocalPk() {
	return serviceDescLocalPk;
}
public void setServiceDescLocalPk(BigDecimal serviceDescLocalPk) {
	this.serviceDescLocalPk = serviceDescLocalPk;
}

public Boolean getDisableEdit() {
	return disableEdit;
}

public void setDisableEdit(Boolean disableEdit) {
	this.disableEdit = disableEdit;
}

public Boolean getBooCheckUpdate() {
	return booCheckUpdate;
}

public void setBooCheckUpdate(Boolean booCheckUpdate) {
	this.booCheckUpdate = booCheckUpdate;
}

public Boolean getBooCheckDelete() {
	return booCheckDelete;
}

public void setBooCheckDelete(Boolean booCheckDelete) {
	this.booCheckDelete = booCheckDelete;
}

public BigDecimal getServiceGroup() {
	return serviceGroup;
}

public void setServiceGroup(BigDecimal serviceGroup) {
	this.serviceGroup = serviceGroup;
}

private String serviceGroupName;


public String getServiceGroupName() {
	  return serviceGroupName;
}

public void setServiceGroupName(String serviceGroupName) {
	  this.serviceGroupName = serviceGroupName;
}
private BigDecimal englishLanguageId;
private BigDecimal arabicLanguageId;



public BigDecimal getEnglishLanguageId() {
	  return englishLanguageId;
}

public void setEnglishLanguageId(BigDecimal englishLanguageId) {
	  this.englishLanguageId = englishLanguageId;
}

public BigDecimal getArabicLanguageId() {
	  return arabicLanguageId;
}

public void setArabicLanguageId(BigDecimal arabicLanguageId) {
	  this.arabicLanguageId = arabicLanguageId;
}




}
