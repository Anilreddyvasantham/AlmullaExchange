package com.amg.exchange.registration.bean;

import java.math.BigDecimal;
import java.util.Date;

public class IdentityTypeMasterDataTable {
	
	private BigDecimal identityTypeIdPk = null;
	private BigDecimal countryId = null;
	private String countryName = null;
	private BigDecimal businessComponentId = null;
	private String idType = null;
	private String validity = null;
	private BigDecimal languageId = null;
	private String isActive = null;
	private String idCheck = null;
	private String createdBy = null;
	private Date createdDate = null;
	private String modifiedBy = null;
	private Date modifiedDate = null;
	private String approvedBy = null;
	private Date approvedDate = null;
	private String ocrStatus = null;
	private Boolean renderEditButton=false;
	private String dynamicLabelForActivateDeactivate;
	private String remarks = null;
	private String ocrStatusName = null;
	private Boolean editRecord = false;
	private String customerType =null;
	private String customerTypeName =null;
	
	public IdentityTypeMasterDataTable() {
		
	}

    public IdentityTypeMasterDataTable(BigDecimal identityTypeIdPk,
			BigDecimal countryId, String countryName,
			BigDecimal businessComponentId, String idType, String validity,
			BigDecimal languageId, String isActive, String idCheck,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String ocrStatus, Boolean renderEditButton,
			String dynamicLabelForActivateDeactivate, String remarks,
			String ocrStatusName, Boolean editRecord,String customerType,String customerTypeName) {
		super();
		this.identityTypeIdPk = identityTypeIdPk;
		this.countryId = countryId;
		this.countryName = countryName;
		this.businessComponentId = businessComponentId;
		this.idType = idType;
		this.validity = validity;
		this.languageId = languageId;
		this.isActive = isActive;
		this.idCheck = idCheck;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.ocrStatus = ocrStatus;
		this.renderEditButton = renderEditButton;
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
		this.remarks = remarks;
		this.ocrStatusName = ocrStatusName;
		this.editRecord = editRecord;
		this.customerType = customerType;
		this.customerTypeName = customerTypeName;
	}



	public BigDecimal getIdentityTypeIdPk() {
		return identityTypeIdPk;
	}
	public void setIdentityTypeIdPk(BigDecimal identityTypeIdPk) {
		this.identityTypeIdPk = identityTypeIdPk;
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
	public BigDecimal getBusinessComponentId() {
		return businessComponentId;
	}
	public void setBusinessComponentId(BigDecimal businessComponentId) {
		this.businessComponentId = businessComponentId;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
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
	public String getIdCheck() {
		return idCheck;
	}
	public void setIdCheck(String idCheck) {
		this.idCheck = idCheck;
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
	public String getOcrStatus() {
		return ocrStatus;
	}
	public void setOcrStatus(String ocrStatus) {
		this.ocrStatus = ocrStatus;
	}


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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOcrStatusName() {
		return ocrStatusName;
	}

	public void setOcrStatusName(String ocrStatusName) {
		this.ocrStatusName = ocrStatusName;
	}

	public Boolean getEditRecord() {
		return editRecord;
	}

	public void setEditRecord(Boolean editRecord) {
		this.editRecord = editRecord;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

}
