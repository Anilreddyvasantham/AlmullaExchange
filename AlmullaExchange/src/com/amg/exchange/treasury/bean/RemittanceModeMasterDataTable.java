package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class RemittanceModeMasterDataTable {
	
	private BigDecimal serviceId=null;
	private String serviceName;
	private String remitance=null;
	private String englishRemittanceMode=null;
	private String arabicRemittanceMode=null;
	private BigDecimal pkRmtnce=null;
	private BigDecimal pkRemDesc1=null;
	private BigDecimal pkRemDesc2=null;
	private Date createdDate;
	private String createdBy;
	private String approvedBy;
	private Date approvedDate;
	
	private String remarks;
	private String modifiedBy = null;
	private Date modifiedDate = null;
	private String isActive;
	private String userName;
	private Boolean checkSave;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton=false;
	private Boolean booDisableEdit;
	private Boolean booCheckUpdate;
	private Boolean booCheckDelete;

	public Boolean getCheckSave() {
		return checkSave;
	}
	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public BigDecimal getServiceId() {
		return serviceId;
	}
	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}
	public String getRemitance() {
		return remitance;
	}
	public void setRemitance(String remitance) {
		this.remitance = remitance;
	}
	public String getEnglishRemittanceMode() {
		return englishRemittanceMode;
	}
	public void setEnglishRemittanceMode(String englishRemittanceMode) {
		this.englishRemittanceMode = englishRemittanceMode;
	}
	public String getArabicRemittanceMode() {
		return arabicRemittanceMode;
	}
	public void setArabicRemittanceMode(String arabicRemittanceMode) {
		this.arabicRemittanceMode = arabicRemittanceMode;
	}
	public BigDecimal getPkRmtnce() {
		return pkRmtnce;
	}
	public void setPkRmtnce(BigDecimal pkRmtnce) {
		this.pkRmtnce = pkRmtnce;
	}
	public BigDecimal getPkRemDesc1() {
		return pkRemDesc1;
	}
	public void setPkRemDesc1(BigDecimal pkRemDesc1) {
		this.pkRemDesc1 = pkRemDesc1;
	}
	public BigDecimal getPkRemDesc2() {
		return pkRemDesc2;
	}
	public void setPkRemDesc2(BigDecimal pkRemDesc2) {
		this.pkRemDesc2 = pkRemDesc2;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public String getUserName() {
		return userName;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public Boolean getBooDisableEdit() {
		return booDisableEdit;
	}
	public void setBooDisableEdit(Boolean booDisableEdit) {
		this.booDisableEdit = booDisableEdit;
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
	

}
