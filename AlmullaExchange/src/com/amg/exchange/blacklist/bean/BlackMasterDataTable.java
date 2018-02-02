package com.amg.exchange.blacklist.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BlackMasterDataTable {

	  private BigDecimal blackMasterPk;
	  private BigDecimal blackListDetailsPk;
	  private BigDecimal applicationCountryId;
	  private BigDecimal englishId;
	  private String englishName;
	  private BigDecimal arabicId;
	  private String arabicName;
	  private String sequenceNumber;
	  private String englishAddress;
	  private String arabicAddress;
	  private Date dateOfBrith;
	  private String placeOfBrith;
	  private String cbkRefereceNumber;
	  private Date cbkDate;
	  private BigDecimal partyTypeId;
	  private String partyTypeName;
	  private BigDecimal nationalityId;
	  private String nationalityName;
	  private BigDecimal idType;
	  private String idName;
	  private String idNumber;
	  private String dynamicLabelForActivateDeactivate;
	  private Boolean renderEditButton=false;
	  private Boolean booEditButton=false;
	  private BigDecimal tokenKey;
	//common variables
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;
	  private String isActive;
	  
	  public BigDecimal getBlackMasterPk() {
	  	  return blackMasterPk;
	  }
	  public void setBlackMasterPk(BigDecimal blackMasterPk) {
	  	  this.blackMasterPk = blackMasterPk;
	  }
	  public BigDecimal getBlackListDetailsPk() {
	  	  return blackListDetailsPk;
	  }
	  public void setBlackListDetailsPk(BigDecimal blackListDetailsPk) {
	  	  this.blackListDetailsPk = blackListDetailsPk;
	  }
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  public BigDecimal getEnglishId() {
	  	  return englishId;
	  }
	  public void setEnglishId(BigDecimal englishId) {
	  	  this.englishId = englishId;
	  }
	  public BigDecimal getArabicId() {
	  	  return arabicId;
	  }
	  public void setArabicId(BigDecimal arabicId) {
	  	  this.arabicId = arabicId;
	  }
	  public String getEnglishName() {
	  	  return englishName;
	  }
	  public void setEnglishName(String englishName) {
	  	  this.englishName = englishName;
	  }
	  public String getArabicName() {
	  	  return arabicName;
	  }
	  public void setArabicName(String arabicName) {
	  	  this.arabicName = arabicName;
	  }
	  public String getSequenceNumber() {
	  	  return sequenceNumber;
	  }
	  public void setSequenceNumber(String sequenceNumber) {
	  	  this.sequenceNumber = sequenceNumber;
	  }
	  public String getEnglishAddress() {
	  	  return englishAddress;
	  }
	  public void setEnglishAddress(String englishAddress) {
	  	  this.englishAddress = englishAddress;
	  }
	  public String getArabicAddress() {
	  	  return arabicAddress;
	  }
	  public void setArabicAddress(String arabicAddress) {
	  	  this.arabicAddress = arabicAddress;
	  }
	  public Date getDateOfBrith() {
	  	  return dateOfBrith;
	  }
	  public void setDateOfBrith(Date dateOfBrith) {
	  	  this.dateOfBrith = dateOfBrith;
	  }
	  public String getPlaceOfBrith() {
	  	  return placeOfBrith;
	  }
	  public void setPlaceOfBrith(String placeOfBrith) {
	  	  this.placeOfBrith = placeOfBrith;
	  }
	  public String getCbkRefereceNumber() {
	  	  return cbkRefereceNumber;
	  }
	  public void setCbkRefereceNumber(String cbkRefereceNumber) {
	  	  this.cbkRefereceNumber = cbkRefereceNumber;
	  }
	  public Date getCbkDate() {
	  	  return cbkDate;
	  }
	  public void setCbkDate(Date cbkDate) {
	  	  this.cbkDate = cbkDate;
	  }
	  public BigDecimal getPartyTypeId() {
	  	  return partyTypeId;
	  }
	  public void setPartyTypeId(BigDecimal partyTypeId) {
	  	  this.partyTypeId = partyTypeId;
	  }
	  public String getPartyTypeName() {
	  	  return partyTypeName;
	  }
	  public void setPartyTypeName(String partyTypeName) {
	  	  this.partyTypeName = partyTypeName;
	  }
	  public BigDecimal getNationalityId() {
	  	  return nationalityId;
	  }
	  public void setNationalityId(BigDecimal nationalityId) {
	  	  this.nationalityId = nationalityId;
	  }
	  public String getNationalityName() {
	  	  return nationalityName;
	  }
	  public void setNationalityName(String nationalityName) {
	  	  this.nationalityName = nationalityName;
	  }
	  public BigDecimal getIdType() {
	  	  return idType;
	  }
	  public void setIdType(BigDecimal idType) {
	  	  this.idType = idType;
	  }
	  public String getIdName() {
	  	  return idName;
	  }
	  public void setIdName(String idName) {
	  	  this.idName = idName;
	  }
	  public String getIdNumber() {
	  	  return idNumber;
	  }
	  public void setIdNumber(String idNumber) {
	  	  this.idNumber = idNumber;
	  }
	  public String getDynamicLabelForActivateDeactivate() {
	  	  return dynamicLabelForActivateDeactivate;
	  }
	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
	  	  this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
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
	  public BigDecimal getTokenKey() {
	  	  return tokenKey;
	  }
	  public void setTokenKey(BigDecimal tokenKey) {
	  	  this.tokenKey = tokenKey;
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
	  public String getRemarks() {
	  	  return remarks;
	  }
	  public void setRemarks(String remarks) {
	  	  this.remarks = remarks;
	  }
	  public String getIsActive() {
	  	  return isActive;
	  }
	  public void setIsActive(String isActive) {
	  	  this.isActive = isActive;
	  }
	  
	  
	  
}
