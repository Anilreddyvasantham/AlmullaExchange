package com.amg.exchange.registration.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CityMasterDataTable {

	  private BigDecimal cityIdPk = null;
	  private BigDecimal cityDescIdLocalPk = null;
	  private BigDecimal cityDescIdEnglishPk = null;
	  private BigDecimal cityDescIdPk = null;
	  private BigDecimal countryId = null;
	  private String countryName = null;
	  private String countryCode = null;
	  private BigDecimal stateId = null;
	  private String stateName = null;
	  private String stateCode = null;
	  private BigDecimal districtId = null;
	  private String districtCode = null;
	  private String districtName = null;
	  private BigDecimal cityId = null;
	  private String cityName = null;
	  private String cityNameLocal = null;
	  private String cityCode = null;
	  private BigDecimal languageId = null;
	  private String isActive = null;
	  private String createdBy = null;
	  private Date createdDate = null;
	  private String modifiedBy = null;
	  private Date modifiedDate = null;
	  private String approvedBy = null;
	  private Date approvedDate = null;
	  private Boolean booRenderDataTable = false;
	  private String dynamicLabelForActivateDeactivate;
	  private String remarks = null;
	  private BigDecimal engLanguageId;
	  private BigDecimal arbLanguageId;
	  private String checkStatus = null;

	  private Boolean remarksCheck = false;
	  private Boolean permanetDeleteCheck = false;
	  private Boolean activateRecordCheck = false;

	  public CityMasterDataTable() {
	  }

	  public CityMasterDataTable(BigDecimal cityIdPk, BigDecimal cityDescIdLocalPk, BigDecimal cityDescIdEnglishPk, BigDecimal cityDescIdPk, BigDecimal countryId, String countryName, String countryCode, BigDecimal stateId, String stateName, String stateCode, BigDecimal districtId,
			      String districtCode, String districtName, BigDecimal cityId, String cityName, String cityNameLocal, String cityCode, BigDecimal languageId, String isActive, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate,
			      Boolean booRenderDataTable, String dynamicLabelForActivateDeactivate, String remarks, BigDecimal engLanguageId, BigDecimal arbLanguageId, String checkStatus) {
		    super();
		    this.cityIdPk = cityIdPk;
		    this.cityDescIdLocalPk = cityDescIdLocalPk;
		    this.cityDescIdEnglishPk = cityDescIdEnglishPk;
		    this.cityDescIdPk = cityDescIdPk;
		    this.countryId = countryId;
		    this.countryName = countryName;
		    this.countryCode = countryCode;
		    this.stateId = stateId;
		    this.stateName = stateName;
		    this.stateCode = stateCode;
		    this.districtId = districtId;
		    this.districtCode = districtCode;
		    this.districtName = districtName;
		    this.cityId = cityId;
		    this.cityName = cityName;
		    this.cityNameLocal = cityNameLocal;
		    this.cityCode = cityCode;
		    this.languageId = languageId;
		    this.isActive = isActive;
		    this.createdBy = createdBy;
		    this.createdDate = createdDate;
		    this.modifiedBy = modifiedBy;
		    this.modifiedDate = modifiedDate;
		    this.approvedBy = approvedBy;
		    this.approvedDate = approvedDate;
		    this.booRenderDataTable = booRenderDataTable;
		    this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
		    this.remarks = remarks;
		    this.engLanguageId = engLanguageId;
		    this.arbLanguageId = arbLanguageId;
		    this.checkStatus = checkStatus;
	  }

	  public BigDecimal getCityIdPk() {
		    return cityIdPk;
	  }

	  public void setCityIdPk(BigDecimal cityIdPk) {
		    this.cityIdPk = cityIdPk;
	  }

	  public BigDecimal getCityDescIdLocalPk() {
		    return cityDescIdLocalPk;
	  }

	  public void setCityDescIdLocalPk(BigDecimal cityDescIdLocalPk) {
		    this.cityDescIdLocalPk = cityDescIdLocalPk;
	  }

	  public BigDecimal getCityDescIdEnglishPk() {
		    return cityDescIdEnglishPk;
	  }

	  public void setCityDescIdEnglishPk(BigDecimal cityDescIdEnglishPk) {
		    this.cityDescIdEnglishPk = cityDescIdEnglishPk;
	  }

	  public BigDecimal getCityDescIdPk() {
		    return cityDescIdPk;
	  }

	  public void setCityDescIdPk(BigDecimal cityDescIdPk) {
		    this.cityDescIdPk = cityDescIdPk;
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

	  public String getCountryCode() {
		    return countryCode;
	  }

	  public void setCountryCode(String countryCode) {
		    this.countryCode = countryCode;
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

	  public String getStateCode() {
		    return stateCode;
	  }

	  public void setStateCode(String stateCode) {
		    this.stateCode = stateCode;
	  }

	  public BigDecimal getDistrictId() {
		    return districtId;
	  }

	  public void setDistrictId(BigDecimal districtId) {
		    this.districtId = districtId;
	  }

	  public String getDistrictName() {
		    return districtName;
	  }

	  public void setDistrictName(String districtName) {
		    this.districtName = districtName;
	  }

	  public BigDecimal getCityId() {
		    return cityId;
	  }

	  public void setCityId(BigDecimal cityId) {
		    this.cityId = cityId;
	  }

	  public String getCityName() {
		    return cityName;
	  }

	  public void setCityName(String cityName) {
		    this.cityName = cityName;
	  }

	  public String getCityNameLocal() {
		    return cityNameLocal;
	  }

	  public void setCityNameLocal(String cityNameLocal) {
		    this.cityNameLocal = cityNameLocal;
	  }

	  public String getCityCode() {
		    return cityCode;
	  }

	  public void setCityCode(String cityCode) {
		    this.cityCode = cityCode;
	  }

	  public BigDecimal getLanguageId() {
		    return languageId;
	  }

	  public void setLanguageId(BigDecimal languageId) {
		    this.languageId = languageId;
	  }

	  public String getIsActive() {
		    return isActive;
	  }

	  public void setIsActive(String isActive) {
		    this.isActive = isActive;
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

	  public Boolean getBooRenderDataTable() {
		    return booRenderDataTable;
	  }

	  public void setBooRenderDataTable(Boolean booRenderDataTable) {
		    this.booRenderDataTable = booRenderDataTable;
	  }

	  public String getDynamicLabelForActivateDeactivate() {
		    return dynamicLabelForActivateDeactivate;
	  }

	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		    this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }

	  public String getRemarks() {
		    return remarks;
	  }

	  public void setRemarks(String remarks) {
		    this.remarks = remarks;
	  }

	  public BigDecimal getEngLanguageId() {
		    return engLanguageId;
	  }

	  public void setEngLanguageId(BigDecimal engLanguageId) {
		    this.engLanguageId = engLanguageId;
	  }

	  public BigDecimal getArbLanguageId() {
		    return arbLanguageId;
	  }

	  public void setArbLanguageId(BigDecimal arbLanguageId) {
		    this.arbLanguageId = arbLanguageId;
	  }

	  public String getCheckStatus() {
		    return checkStatus;
	  }

	  public void setCheckStatus(String checkStatus) {
		    this.checkStatus = checkStatus;
	  }

	  public String getDistrictCode() {
		    return districtCode;
	  }

	  public void setDistrictCode(String districtCode) {
		    this.districtCode = districtCode;
	  }

	  public Boolean getRemarksCheck() {
		    return remarksCheck;
	  }

	  public void setRemarksCheck(Boolean remarksCheck) {
		    this.remarksCheck = remarksCheck;
	  }

	  public Boolean getPermanetDeleteCheck() {
		    return permanetDeleteCheck;
	  }

	  public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		    this.permanetDeleteCheck = permanetDeleteCheck;
	  }

	  public Boolean getActivateRecordCheck() {
		    return activateRecordCheck;
	  }

	  public void setActivateRecordCheck(Boolean activateRecordCheck) {
		    this.activateRecordCheck = activateRecordCheck;
	  }

	  private Boolean ifEditClicked = false;

	  public Boolean getIfEditClicked() {
		    return ifEditClicked;
	  }

	  public void setIfEditClicked(Boolean ifEditClicked) {
		    this.ifEditClicked = ifEditClicked;
	  }

}
