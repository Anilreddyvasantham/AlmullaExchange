package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ZoneMasterDataTable {
	private String zoneCode;
	private String zoneDescription;
	private String zoneDescriptionLocal;
	private BigDecimal zoneIdpk;
	private BigDecimal zoneDescPk;
	private BigDecimal zoneDescLocalPk;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private Boolean remarksCheck;
	private String IsActive;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton;
	private Boolean booEditButton;
	private Boolean permanetDeleteCheck=false;
	private Boolean activateRecordCheck=false;
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public String getZoneDescription() {
		return zoneDescription;
	}
	public void setZoneDescription(String zoneDescription) {
		this.zoneDescription = zoneDescription;
	}
	public String getZoneDescriptionLocal() {
		return zoneDescriptionLocal;
	}
	public void setZoneDescriptionLocal(String zoneDescriptionLocal) {
		this.zoneDescriptionLocal = zoneDescriptionLocal;
	}
	public BigDecimal getZoneIdpk() {
		return zoneIdpk;
	}
	public void setZoneIdpk(BigDecimal zoneIdpk) {
		this.zoneIdpk = zoneIdpk;
	}
	public BigDecimal getZoneDescPk() {
		return zoneDescPk;
	}
	public void setZoneDescPk(BigDecimal zoneDescPk) {
		this.zoneDescPk = zoneDescPk;
	}
	public BigDecimal getZoneDescLocalPk() {
		return zoneDescLocalPk;
	}
	public void setZoneDescLocalPk(BigDecimal zoneDescLocalPk) {
		this.zoneDescLocalPk = zoneDescLocalPk;
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
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public String getIsActive() {
		return IsActive;
	}
	public void setIsActive(String isActive) {
		IsActive = isActive;
	}
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}
	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	public Boolean getRemarksCheck() {
		return remarksCheck;
	}
	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
	public Boolean getBooEditButton() {
		return booEditButton;
	}
	public void setBooEditButton(Boolean booEditButton) {
		this.booEditButton = booEditButton;
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
	
	
}
