package com.amg.exchange.complaint.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ComplaintAssignedDTO implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = -7654243743136705849L;
	  
	  private BigDecimal applicationCountryId;
	  private BigDecimal complaintAssignedId;
	  private BigDecimal complaintAssignedDescId;
	  private BigDecimal languageId;
	  private BigDecimal arabicLanguageId;
	  private BigDecimal englishLanguageId;
	  
	  private String complaintAssignedCode;
	  private String complaintAssignedTo;
	  private String logCompalint;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;
	  private String isActive;
	  private String complaintEnglishFullDescription;
	  private String complaintArabicFullDescription;
	  private String complaintEnglishShortDescription;
	  private String complaintArabicShortDescription;
	  private BigDecimal complaintEnglishFullDescriptionId;
	  private BigDecimal complaintArabicFullDescriptionId;
	  private String fullDescription;
	  private String shortDescription;
	  private String logCompalintId;
	  
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  public String getComplaintAssignedCode() {
	  	  return complaintAssignedCode;
	  }
	  public void setComplaintAssignedCode(String complaintAssignedCode) {
	  	  this.complaintAssignedCode = complaintAssignedCode;
	  }
	  public BigDecimal getComplaintAssignedId() {
	  	  return complaintAssignedId;
	  }
	  public void setComplaintAssignedId(BigDecimal complaintAssignedId) {
	  	  this.complaintAssignedId = complaintAssignedId;
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
	  public String getIsActive() {
	  	  return isActive;
	  }
	  public void setIsActive(String isActive) {
	  	  this.isActive = isActive;
	  }
	  public String getComplaintEnglishFullDescription() {
	  	  return complaintEnglishFullDescription;
	  }
	  public void setComplaintEnglishFullDescription(String complaintEnglishFullDescription) {
	  	  this.complaintEnglishFullDescription = complaintEnglishFullDescription;
	  }
	  public String getComplaintArabicFullDescription() {
	  	  return complaintArabicFullDescription;
	  }
	  public void setComplaintArabicFullDescription(String complaintArabicFullDescription) {
	  	  this.complaintArabicFullDescription = complaintArabicFullDescription;
	  }
	  public String getComplaintEnglishShortDescription() {
	  	  return complaintEnglishShortDescription;
	  }
	  public void setComplaintEnglishShortDescription(String complaintEnglishShortDescription) {
	  	  this.complaintEnglishShortDescription = complaintEnglishShortDescription;
	  }
	  public String getComplaintArabicShortDescription() {
	  	  return complaintArabicShortDescription;
	  }
	  public void setComplaintArabicShortDescription(String complaintArabicShortDescription) {
	  	  this.complaintArabicShortDescription = complaintArabicShortDescription;
	  }
	  public BigDecimal getComplaintEnglishFullDescriptionId() {
	  	  return complaintEnglishFullDescriptionId;
	  }
	  public void setComplaintEnglishFullDescriptionId(BigDecimal complaintEnglishFullDescriptionId) {
	  	  this.complaintEnglishFullDescriptionId = complaintEnglishFullDescriptionId;
	  }
	  public BigDecimal getComplaintArabicFullDescriptionId() {
	  	  return complaintArabicFullDescriptionId;
	  }
	  public void setComplaintArabicFullDescriptionId(BigDecimal complaintArabicFullDescriptionId) {
	  	  this.complaintArabicFullDescriptionId = complaintArabicFullDescriptionId;
	  }
	  public BigDecimal getArabicLanguageId() {
	  	  return arabicLanguageId;
	  }
	  public void setArabicLanguageId(BigDecimal arabicLanguageId) {
	  	  this.arabicLanguageId = arabicLanguageId;
	  }
	  public BigDecimal getEnglishLanguageId() {
	  	  return englishLanguageId;
	  }
	  public void setEnglishLanguageId(BigDecimal englishLanguageId) {
	  	  this.englishLanguageId = englishLanguageId;
	  }
	  public BigDecimal getComplaintAssignedDescId() {
	  	  return complaintAssignedDescId;
	  }
	  public void setComplaintAssignedDescId(BigDecimal complaintAssignedDescId) {
	  	  this.complaintAssignedDescId = complaintAssignedDescId;
	  }
	  public BigDecimal getLanguageId() {
	  	  return languageId;
	  }
	  public void setLanguageId(BigDecimal languageId) {
	  	  this.languageId = languageId;
	  }
	  public String getFullDescription() {
	  	  return fullDescription;
	  }
	  public void setFullDescription(String fullDescription) {
	  	  this.fullDescription = fullDescription;
	  }
	  public String getShortDescription() {
	  	  return shortDescription;
	  }
	  public void setShortDescription(String shortDescription) {
	  	  this.shortDescription = shortDescription;
	  }
	  public String getComplaintAssignedTo() {
	  	  return complaintAssignedTo;
	  }
	  public void setComplaintAssignedTo(String complaintAssignedTo) {
	  	  this.complaintAssignedTo = complaintAssignedTo;
	  }
	  public String getLogCompalint() {
	  	  return logCompalint;
	  }
	  public void setLogCompalint(String logCompalint) {
	  	  this.logCompalint = logCompalint;
	  }
	  public String getLogCompalintId() {
	  	  return logCompalintId;
	  }
	  public void setLogCompalintId(String logCompalintId) {
	  	  this.logCompalintId = logCompalintId;
	  }
	  
	  
	  

}
