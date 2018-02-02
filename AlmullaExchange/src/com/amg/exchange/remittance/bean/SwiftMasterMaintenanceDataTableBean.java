package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

public class SwiftMasterMaintenanceDataTableBean {

	private BigDecimal swiftCountryId;
	private String swiftCountryName;
	

	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal swiftBankId;
	private BigDecimal bankId;
	private BigDecimal swiftId;
	private String swiftBankName;

	private String swiftLocation;
	private BigDecimal branchCode;
	private String branchName;
	private String cityName;
	private String bankName;
	private String region;
	private BigDecimal branchId;

	private String swiftBIC;
	private String chipsUID;
	private String acronymID;
	private String fedwireID;
	private String abaNumber;

	private String isActive;

	private String firstAddress;
	private String secondAddress;
	private String thirdAddress;

	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	private String approvedBy = null;
	private Date approvedDate;
	private String remarks;
	private String swiftBranchCode;
	private String swiftBankCode;
	
	private Boolean checkSave;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton = false;
	private Boolean remarkCheck;
	private Boolean disableEdit;
	private Boolean booCheckDelete;
	private Boolean booCheckUpdate;
	private Boolean booActivate;
	
	
	private String  swiftCountryCode;
	public String getSwiftCountryCode() {
		return swiftCountryCode;
	}

	public void setSwiftCountryCode(String swiftCountryCode) {
		this.swiftCountryCode = swiftCountryCode;
	}

	public String getSwiftBranchCode() {
		return swiftBranchCode;
	}

	public void setSwiftBranchCode(String swiftBranchCode) {
		this.swiftBranchCode = swiftBranchCode;
	}

	public String getSwiftBankCode() {
		return swiftBankCode;
	}

	public void setSwiftBankCode(String swiftBankCode) {
		this.swiftBankCode = swiftBankCode;
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

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	public Boolean getRemarkCheck() {
		return remarkCheck;
	}

	public void setRemarkCheck(Boolean remarkCheck) {
		this.remarkCheck = remarkCheck;
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

	public BigDecimal getSwiftCountryId() {
		return swiftCountryId;
	}

	public void setSwiftCountryId(BigDecimal swiftCountryId) {
		this.swiftCountryId = swiftCountryId;
	}

	public String getSwiftCountryName() {
		return swiftCountryName;
	}

	public void setSwiftCountryName(String swiftCountryName) {
		this.swiftCountryName = swiftCountryName;
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

	public String getSwiftLocation() {
		return swiftLocation;
	}

	public void setSwiftLocation(String swiftLocation) {
		this.swiftLocation = swiftLocation;
	}

	public BigDecimal getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSwiftBIC() {
		return swiftBIC;
	}

	public void setSwiftBIC(String swiftBIC) {
		this.swiftBIC = swiftBIC;
	}

	public String getChipsUID() {
		return chipsUID;
	}

	public void setChipsUID(String chipsUID) {
		this.chipsUID = chipsUID;
	}

	public String getAcronymID() {
		return acronymID;
	}

	public void setAcronymID(String acronymID) {
		this.acronymID = acronymID;
	}

	public String getFedwireID() {
		return fedwireID;
	}

	public void setFedwireID(String fedwireID) {
		this.fedwireID = fedwireID;
	}

	public String getAbaNumber() {
		return abaNumber;
	}

	public void setAbaNumber(String abaNumber) {
		this.abaNumber = abaNumber;
	}

	public BigDecimal getSwiftBankId() {
		return swiftBankId;
	}

	public void setSwiftBankId(BigDecimal swiftBankId) {
		this.swiftBankId = swiftBankId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getFirstAddress() {
		return firstAddress;
	}

	public void setFirstAddress(String firstAddress) {
		this.firstAddress = firstAddress;
	}

	public String getSecondAddress() {
		return secondAddress;
	}

	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}

	public String getThirdAddress() {
		return thirdAddress;
	}

	public void setThirdAddress(String thirdAddress) {
		this.thirdAddress = thirdAddress;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getSwiftId() {
		return swiftId;
	}

	public void setSwiftId(BigDecimal swiftId) {
		this.swiftId = swiftId;
	}

	public String getSwiftBankName() {
		return swiftBankName;
	}

	public void setSwiftBankName(String swiftBankName) {
		this.swiftBankName = swiftBankName;
	}

	public Boolean getDisableEdit() {
		return disableEdit;
	}

	public void setDisableEdit(Boolean disableEdit) {
		this.disableEdit = disableEdit;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}

	public Boolean getBooActivate() {
		return booActivate;
	}

	public void setBooActivate(Boolean booActivate) {
		this.booActivate = booActivate;
	}

}
