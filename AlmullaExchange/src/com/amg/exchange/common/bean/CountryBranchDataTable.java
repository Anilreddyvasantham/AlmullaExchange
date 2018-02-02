package com.amg.exchange.common.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CountryBranchDataTable {

	private BigDecimal countryBranchId;
	private BigDecimal applicationCountryId;
	private String branchName;

	private BigDecimal accountCode;
	private String corporateStatus;
	private String corporateStatusName;
	private BigDecimal branchCode;
	private String headOfficeIndicator;
	private String headOfficeIndicatorName;
	private BigDecimal telephoneNumber;
	private String emailId;
	private BigDecimal areaCode;
	private BigDecimal ecNumeber;
	private String ipAddress;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private Boolean remarksCheck = false;
	private Boolean checkSave;
	private String wuAccCode;

	private Boolean disableEdit = false;
	private Boolean booCheckUpdate = false;
	private Boolean booCheckDelete = false;

	private Boolean activateRecordCheck = false;
	private String dynamicLabelForActivateDeactivate;
	//added new Field 20/09/2016
	private String digitalSignTypeInd;

	public CountryBranchDataTable() {
		// TODO Auto-generated constructor stub
	}

	public String getCorporateStatusName() {
		return corporateStatusName;
	}

	public void setCorporateStatusName(String corporateStatusName) {
		this.corporateStatusName = corporateStatusName;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(BigDecimal accountCode) {
		this.accountCode = accountCode;
	}

	public String getCorporateStatus() {
		return corporateStatus;
	}

	public void setCorporateStatus(String corporateStatus) {
		this.corporateStatus = corporateStatus;
	}

	public BigDecimal getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	public String getHeadOfficeIndicator() {
		return headOfficeIndicator;
	}

	public void setHeadOfficeIndicator(String headOfficeIndicator) {
		this.headOfficeIndicator = headOfficeIndicator;
	}

	public BigDecimal getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(BigDecimal telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public BigDecimal getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(BigDecimal areaCode) {
		this.areaCode = areaCode;
	}

	public BigDecimal getEcNumeber() {
		return ecNumeber;
	}

	public void setEcNumeber(BigDecimal ecNumeber) {
		this.ecNumeber = ecNumeber;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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



	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
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

	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public String getHeadOfficeIndicatorName() {
		return headOfficeIndicatorName;
	}

	public void setHeadOfficeIndicatorName(String headOfficeIndicatorName) {
		this.headOfficeIndicatorName = headOfficeIndicatorName;
	}

	private String scanIndicatorOrType;


	public String getScanIndicatorOrType() {
		return scanIndicatorOrType;
	}

	public void setScanIndicatorOrType(String scanIndicatorOrType) {
		this.scanIndicatorOrType = scanIndicatorOrType;
	}

	private Boolean renderEditButton=false;


	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	private Boolean ifEditClicked=false;

	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}

	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
	}

	public String getDigitalSignTypeInd() {
		return digitalSignTypeInd;
	}

	public void setDigitalSignTypeInd(String digitalSignTypeInd) {
		this.digitalSignTypeInd = digitalSignTypeInd;
	}

	public String getWuAccCode() {
		return wuAccCode;
	}

	public void setWuAccCode(String wuAccCode) {
		this.wuAccCode = wuAccCode;
	}

}
