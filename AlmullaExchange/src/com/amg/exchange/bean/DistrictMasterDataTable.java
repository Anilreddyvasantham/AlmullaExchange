/**
 * 
 */
package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Subramaniam
 * 
 */
public class DistrictMasterDataTable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal districtId;
	private BigDecimal stateId;
	private String districtCode;
	private String districtActive;
	private String createdBy;
	private String updatedBy;
	private Date creationDate;
	private Date lastUpdated;
	private BigDecimal districtDescId;
	private BigDecimal districtArabicDescId;
	private BigDecimal englishLanguageTypeId;
	private BigDecimal localLanguageTypeId;	
	private String localDistrictName;
	private String englishDistrictName;
//	private String remarks;
	private Date approvedDate;
	private String approvedBy;
	private String stateName;
	private String stateCode;
	private BigDecimal countryId;
	private String countryName;
	private String countryCode;
	//private String cityStatus;
	
	private Boolean remarksCheck = false;
	private Boolean activateRecordCheck = false;

	private Boolean booCheckDelete = false;

	private String dynamicLabelForActivateDeactivate;
	

	private Boolean isCheck =false;
	private Boolean disableCheck;

	public DistrictMasterDataTable() {
		// TODO Auto-generated constructor stub
	}

	
	


	public DistrictMasterDataTable(BigDecimal districtId, BigDecimal stateId, String districtCode, String districtActive, String createdBy, String updatedBy, Date creationDate, Date lastUpdated, BigDecimal districtDescId, BigDecimal districtArabicDescId, BigDecimal englishLanguageTypeId,
			BigDecimal localLanguageTypeId, String localDistrictName, String englishDistrictName, Date approvedDate, String approvedBy, String stateName, String stateCode, BigDecimal countryId, String countryName, String countryCode, Boolean remarksCheck, Boolean activateRecordCheck,
			Boolean booCheckDelete, String dynamicLabelForActivateDeactivate, Boolean isCheck, Boolean disableCheck) {
		this.districtId = districtId;
		this.stateId = stateId;
		this.districtCode = districtCode;
		this.districtActive = districtActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.creationDate = creationDate;
		this.lastUpdated = lastUpdated;
		this.districtDescId = districtDescId;
		this.districtArabicDescId = districtArabicDescId;
		this.englishLanguageTypeId = englishLanguageTypeId;
		this.localLanguageTypeId = localLanguageTypeId;
		this.localDistrictName = localDistrictName;
		this.englishDistrictName = englishDistrictName;
		this.approvedDate = approvedDate;
		this.approvedBy = approvedBy;
		this.stateName = stateName;
		this.stateCode = stateCode;
		this.countryId = countryId;
		this.countryName = countryName;
		this.countryCode = countryCode;
		this.remarksCheck = remarksCheck;
		this.activateRecordCheck = activateRecordCheck;
		this.booCheckDelete = booCheckDelete;
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
		this.isCheck = isCheck;
		this.disableCheck = disableCheck;
	}





	public BigDecimal getEnglishLanguageTypeId() {
		return englishLanguageTypeId;
	}


	public void setEnglishLanguageTypeId(BigDecimal englishLanguageTypeId) {
		this.englishLanguageTypeId = englishLanguageTypeId;
	}


	public BigDecimal getLocalLanguageTypeId() {
		return localLanguageTypeId;
	}


	public void setLocalLanguageTypeId(BigDecimal localLanguageTypeId) {
		this.localLanguageTypeId = localLanguageTypeId;
	}


	public BigDecimal getDistrictId() {
		return districtId;
	}

	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
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

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictActive() {
		return districtActive;
	}

	public void setDistrictActive(String districtActive) {
		this.districtActive = districtActive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public BigDecimal getDistrictDescId() {
		return districtDescId;
	}

	public void setDistrictDescId(BigDecimal districtDescId) {
		this.districtDescId = districtDescId;
	}

	

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}


	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
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

	public String getLocalDistrictName() {
		return localDistrictName;
	}

	public void setLocalDistrictName(String localDistrictName) {
		this.localDistrictName = localDistrictName;
	}

	public String getEnglishDistrictName() {
		return englishDistrictName;
	}

	public void setEnglishDistrictName(String englishDistrictName) {
		this.englishDistrictName = englishDistrictName;
	}

	/*public String getCityStatus() {
		return cityStatus;
	}

	public void setCityStatus(String cityStatus) {
		this.cityStatus = cityStatus;
	}*/

	

	public BigDecimal getDistrictArabicDescId() {
		return districtArabicDescId;
	}

	public void setDistrictArabicDescId(BigDecimal districtArabicDescId) {
		this.districtArabicDescId = districtArabicDescId;
	}

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}

	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
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

	
	
}
