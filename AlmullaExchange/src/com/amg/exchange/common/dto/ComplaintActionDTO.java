package com.amg.exchange.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ComplaintActionDTO implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	
	private String complaintActionCode;
	private String complaintActionEngFullDescription;
	private String complaintActionEngShortDescription;
	private String complaintActionArabicFullDescription;
	private String complaintActionArabicShortDescription;
	private String shortDescription;
	private String fullDescription;
	
	
	private String actionGroup;
	private String approvedBy;
	private String createdBy;
	private String modifiedBy;
	private String isActive;
	private String remarks;
	
	private BigDecimal complaintActionId;
	private BigDecimal complaintActionEngDescId;
	private BigDecimal complaintActionArabicDescId;
	private BigDecimal complaintActionDescId;
	private BigDecimal englishLanguageId;
	private BigDecimal arabicLanguageId;
	private BigDecimal languageId;
	private BigDecimal applicationCountryId;
	
	private Date createdDate;
	private Date modifiedDate;
	private Date approvedDate;
	
	
	
	
	
	public String getComplaintActionCode() {
		return complaintActionCode;
	}
	public void setComplaintActionCode(String complaintActionCode) {
		this.complaintActionCode = complaintActionCode;
	}
	public String getComplaintActionEngFullDescription() {
		return complaintActionEngFullDescription;
	}
	public void setComplaintActionEngFullDescription(
			String complaintActionEngFullDescription) {
		this.complaintActionEngFullDescription = complaintActionEngFullDescription;
	}
	public String getComplaintActionEngShortDescription() {
		return complaintActionEngShortDescription;
	}
	public void setComplaintActionEngShortDescription(
			String complaintActionEngShortDescription) {
		this.complaintActionEngShortDescription = complaintActionEngShortDescription;
	}
	public String getComplaintActionArabicFullDescription() {
		return complaintActionArabicFullDescription;
	}
	public void setComplaintActionArabicFullDescription(
			String complaintActionArabicFullDescription) {
		this.complaintActionArabicFullDescription = complaintActionArabicFullDescription;
	}
	public String getComplaintActionArabicShortDescription() {
		return complaintActionArabicShortDescription;
	}
	public void setComplaintActionArabicShortDescription(
			String complaintActionArabicShortDescription) {
		this.complaintActionArabicShortDescription = complaintActionArabicShortDescription;
	}
	public BigDecimal getComplaintActionId() {
		return complaintActionId;
	}
	public void setComplaintActionId(BigDecimal complaintActionId) {
		this.complaintActionId = complaintActionId;
	}
	public BigDecimal getComplaintActionEngDescId() {
		return complaintActionEngDescId;
	}
	public void setComplaintActionEngDescId(BigDecimal complaintActionEngDescId) {
		this.complaintActionEngDescId = complaintActionEngDescId;
	}
	public BigDecimal getComplaintActionArabicDescId() {
		return complaintActionArabicDescId;
	}
	public void setComplaintActionArabicDescId(
			BigDecimal complaintActionArabicDescId) {
		this.complaintActionArabicDescId = complaintActionArabicDescId;
	}
	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}
	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
	}
	public BigDecimal getArabicLanguageId() {
		return arabicLanguageId;
	}
	public void setArabicLanguageId(BigDecimal arabicLanguageId) {
		this.arabicLanguageId = arabicLanguageId;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
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
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getActionGroup() {
		return actionGroup;
	}
	public void setActionGroup(String actionGroup) {
		this.actionGroup = actionGroup;
	}
	public BigDecimal getLanguageId() {
		return languageId;
	}
	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getFullDescription() {
		return fullDescription;
	}
	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}
	public BigDecimal getComplaintActionDescId() {
		return complaintActionDescId;
	}
	public void setComplaintActionDescId(BigDecimal complaintActionDescId) {
		this.complaintActionDescId = complaintActionDescId;
	}
	
	
	

}
