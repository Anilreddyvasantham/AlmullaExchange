package com.amg.exchange.complaint.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ComplaintTypeDataTable {

	private BigDecimal complaintTypeId;
	private BigDecimal complaintTypeDescId;
	private BigDecimal engComplaintTypeDescId;
	private BigDecimal localComplaintTypeDescId;
	private String fullEngComplaintTypeDesc;
	private String fullLocalComplaintTypeDesc;
	private String shortEngComplaintTypeDesc;
	private String shortLocalComplaintTypeDesc;
	private BigDecimal appCountryId;
	private String complaintCode;
	private String sendBulk;
	private String sendBulkName;
	private BigDecimal osDays;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String dynamicLabelForActivateDeactivate;
	private BigDecimal localLanguageId;
	private BigDecimal englishLanguageId;
	
	
	private Boolean remarksCheck = false;
	private Boolean activateRecordCheck = false;

	 private Boolean booCheckDelete = false;

	public ComplaintTypeDataTable() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(BigDecimal complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public BigDecimal getComplaintTypeDescId() {
		return complaintTypeDescId;
	}

	public void setComplaintTypeDescId(BigDecimal complaintTypeDescId) {
		this.complaintTypeDescId = complaintTypeDescId;
	}

	public BigDecimal getEngComplaintTypeDescId() {
		return engComplaintTypeDescId;
	}

	public void setEngComplaintTypeDescId(BigDecimal engComplaintTypeDescId) {
		this.engComplaintTypeDescId = engComplaintTypeDescId;
	}

	public BigDecimal getLocalComplaintTypeDescId() {
		return localComplaintTypeDescId;
	}

	public void setLocalComplaintTypeDescId(BigDecimal localComplaintTypeDescId) {
		this.localComplaintTypeDescId = localComplaintTypeDescId;
	}

	public String getFullEngComplaintTypeDesc() {
		return fullEngComplaintTypeDesc;
	}

	public void setFullEngComplaintTypeDesc(String fullEngComplaintTypeDesc) {
		this.fullEngComplaintTypeDesc = fullEngComplaintTypeDesc;
	}

	public String getFullLocalComplaintTypeDesc() {
		return fullLocalComplaintTypeDesc;
	}

	public void setFullLocalComplaintTypeDesc(String fullLocalComplaintTypeDesc) {
		this.fullLocalComplaintTypeDesc = fullLocalComplaintTypeDesc;
	}

	public String getShortEngComplaintTypeDesc() {
		return shortEngComplaintTypeDesc;
	}

	public void setShortEngComplaintTypeDesc(String shortEngComplaintTypeDesc) {
		this.shortEngComplaintTypeDesc = shortEngComplaintTypeDesc;
	}

	public String getShortLocalComplaintTypeDesc() {
		return shortLocalComplaintTypeDesc;
	}

	public void setShortLocalComplaintTypeDesc(String shortLocalComplaintTypeDesc) {
		this.shortLocalComplaintTypeDesc = shortLocalComplaintTypeDesc;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public String getComplaintCode() {
		return complaintCode;
	}

	public void setComplaintCode(String complaintCode) {
		this.complaintCode = complaintCode;
	}

	public String getSendBulk() {
		return sendBulk;
	}

	public void setSendBulk(String sendBulk) {
		this.sendBulk = sendBulk;
	}

	public BigDecimal getOsDays() {
		return osDays;
	}

	public void setOsDays(BigDecimal osDays) {
		this.osDays = osDays;
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

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public BigDecimal getLocalLanguageId() {
		return localLanguageId;
	}

	public void setLocalLanguageId(BigDecimal localLanguageId) {
		this.localLanguageId = localLanguageId;
	}

	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}

	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
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

	public String getSendBulkName() {
		return sendBulkName;
	}

	public void setSendBulkName(String sendBulkName) {
		this.sendBulkName = sendBulkName;
	}
	
	

}
