package com.amg.exchange.complaint.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ComplaintRemarksDataTable {

	private BigDecimal complaintRemarksId;
	private BigDecimal complaintRemarksDescId;
	private BigDecimal engComplaintRemarksDescId;
	private BigDecimal localComplaintRemarksDescId;
	private String fullEngComplaintRemarksDesc;
	private String fullLocalComplaintRemarksDesc;
	private String shortEngComplaintRemarksDesc;
	private String shortLocalComplaintRemarksDesc;
	private BigDecimal appCountryId;
	private String complaintRemarksCode;
	private String tagRemittance;
	private String tagRemittanceName;
	private String tagBenificiary;
	private String tagBenificiaryName;
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

	public ComplaintRemarksDataTable() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getComplaintRemarksId() {
		return complaintRemarksId;
	}

	public void setComplaintRemarksId(BigDecimal complaintRemarksId) {
		this.complaintRemarksId = complaintRemarksId;
	}

	public BigDecimal getComplaintRemarksDescId() {
		return complaintRemarksDescId;
	}

	public void setComplaintRemarksDescId(BigDecimal complaintRemarksDescId) {
		this.complaintRemarksDescId = complaintRemarksDescId;
	}

	public BigDecimal getEngComplaintRemarksDescId() {
		return engComplaintRemarksDescId;
	}

	public void setEngComplaintRemarksDescId(BigDecimal engComplaintRemarksDescId) {
		this.engComplaintRemarksDescId = engComplaintRemarksDescId;
	}

	public BigDecimal getLocalComplaintRemarksDescId() {
		return localComplaintRemarksDescId;
	}

	public void setLocalComplaintRemarksDescId(BigDecimal localComplaintRemarksDescId) {
		this.localComplaintRemarksDescId = localComplaintRemarksDescId;
	}

	public String getFullEngComplaintRemarksDesc() {
		return fullEngComplaintRemarksDesc;
	}

	public void setFullEngComplaintRemarksDesc(String fullEngComplaintRemarksDesc) {
		this.fullEngComplaintRemarksDesc = fullEngComplaintRemarksDesc;
	}

	public String getFullLocalComplaintRemarksDesc() {
		return fullLocalComplaintRemarksDesc;
	}

	public void setFullLocalComplaintRemarksDesc(String fullLocalComplaintRemarksDesc) {
		this.fullLocalComplaintRemarksDesc = fullLocalComplaintRemarksDesc;
	}

	public String getShortEngComplaintRemarksDesc() {
		return shortEngComplaintRemarksDesc;
	}

	public void setShortEngComplaintRemarksDesc(String shortEngComplaintRemarksDesc) {
		this.shortEngComplaintRemarksDesc = shortEngComplaintRemarksDesc;
	}

	public String getShortLocalComplaintRemarksDesc() {
		return shortLocalComplaintRemarksDesc;
	}

	public void setShortLocalComplaintRemarksDesc(String shortLocalComplaintRemarksDesc) {
		this.shortLocalComplaintRemarksDesc = shortLocalComplaintRemarksDesc;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
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

	public String getComplaintRemarksCode() {
		return complaintRemarksCode;
	}

	public void setComplaintRemarksCode(String complaintRemarksCode) {
		this.complaintRemarksCode = complaintRemarksCode;
	}

	public String getTagRemittance() {
		return tagRemittance;
	}

	public void setTagRemittance(String tagRemittance) {
		this.tagRemittance = tagRemittance;
	}

	public String getTagRemittanceName() {
		return tagRemittanceName;
	}

	public void setTagRemittanceName(String tagRemittanceName) {
		this.tagRemittanceName = tagRemittanceName;
	}

	public String getTagBenificiary() {
		return tagBenificiary;
	}

	public void setTagBenificiary(String tagBenificiary) {
		this.tagBenificiary = tagBenificiary;
	}

	public String getTagBenificiaryName() {
		return tagBenificiaryName;
	}

	public void setTagBenificiaryName(String tagBenificiaryName) {
		this.tagBenificiaryName = tagBenificiaryName;
	}
	
	

}
