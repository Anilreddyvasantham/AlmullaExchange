package com.amg.exchange.complaint.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ComplaintEnquiryDataTable {

	  private BigDecimal receiptNumber;
	  private BigDecimal locationId;
	  private String locationName;
	  private Date logDate;
	  private BigDecimal dealYear;
	  private BigDecimal complaintTypeId;
	  private String complaintTypeCode;
	  private String complaintTypeDesc;
	  private BigDecimal complaintAssignedId;
	  private String complaintAssignedCode;
	  private String complaintAssignedDesc;
	  private BigDecimal takenById;
	  private String takenByCode;
	  private String takenByDesc;
	  private BigDecimal remarksId;
	  private String remarksCode;
	  private String remarksDesc;
	  
	  private String priorityType;
	  private String remarks;
	  private String pendingWith;
	  private String createdBy;
	  private Date createdDate;
	  private String isActive;
	  private Boolean select = false;
	  public BigDecimal getReceiptNumber() {
	  	  return receiptNumber;
	  }
	  public void setReceiptNumber(BigDecimal receiptNumber) {
	  	  this.receiptNumber = receiptNumber;
	  }
	  public BigDecimal getLocationId() {
	  	  return locationId;
	  }
	  public void setLocationId(BigDecimal locationId) {
	  	  this.locationId = locationId;
	  }
	  public String getLocationName() {
	  	  return locationName;
	  }
	  public void setLocationName(String locationName) {
	  	  this.locationName = locationName;
	  }
	  public Date getLogDate() {
	  	  return logDate;
	  }
	  public void setLogDate(Date logDate) {
	  	  this.logDate = logDate;
	  }
	  public BigDecimal getDealYear() {
	  	  return dealYear;
	  }
	  public void setDealYear(BigDecimal dealYear) {
	  	  this.dealYear = dealYear;
	  }
	  
	  public String getPriorityType() {
	  	  return priorityType;
	  }
	  public void setPriorityType(String priorityType) {
	  	  this.priorityType = priorityType;
	  }
	  public String getRemarks() {
	  	  return remarks;
	  }
	  public void setRemarks(String remarks) {
	  	  this.remarks = remarks;
	  }
	  public String getPendingWith() {
	  	  return pendingWith;
	  }
	  public void setPendingWith(String pendingWith) {
	  	  this.pendingWith = pendingWith;
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
	  public String getIsActive() {
	  	  return isActive;
	  }
	  public void setIsActive(String isActive) {
	  	  this.isActive = isActive;
	  }
	  public BigDecimal getComplaintTypeId() {
	  	  return complaintTypeId;
	  }
	  public void setComplaintTypeId(BigDecimal complaintTypeId) {
	  	  this.complaintTypeId = complaintTypeId;
	  }
	  public String getComplaintTypeCode() {
	  	  return complaintTypeCode;
	  }
	  public void setComplaintTypeCode(String complaintTypeCode) {
	  	  this.complaintTypeCode = complaintTypeCode;
	  }
	  public String getComplaintTypeDesc() {
	  	  return complaintTypeDesc;
	  }
	  public void setComplaintTypeDesc(String complaintTypeDesc) {
	  	  this.complaintTypeDesc = complaintTypeDesc;
	  }
	  public BigDecimal getComplaintAssignedId() {
	  	  return complaintAssignedId;
	  }
	  public void setComplaintAssignedId(BigDecimal complaintAssignedId) {
	  	  this.complaintAssignedId = complaintAssignedId;
	  }
	  public String getComplaintAssignedCode() {
	  	  return complaintAssignedCode;
	  }
	  public void setComplaintAssignedCode(String complaintAssignedCode) {
	  	  this.complaintAssignedCode = complaintAssignedCode;
	  }
	  public String getComplaintAssignedDesc() {
	  	  return complaintAssignedDesc;
	  }
	  public void setComplaintAssignedDesc(String complaintAssignedDesc) {
	  	  this.complaintAssignedDesc = complaintAssignedDesc;
	  }
	  public BigDecimal getTakenById() {
	  	  return takenById;
	  }
	  public void setTakenById(BigDecimal takenById) {
	  	  this.takenById = takenById;
	  }
	  public String getTakenByCode() {
	  	  return takenByCode;
	  }
	  public void setTakenByCode(String takenByCode) {
	  	  this.takenByCode = takenByCode;
	  }
	  public String getTakenByDesc() {
	  	  return takenByDesc;
	  }
	  public void setTakenByDesc(String takenByDesc) {
	  	  this.takenByDesc = takenByDesc;
	  }
	  public BigDecimal getRemarksId() {
	  	  return remarksId;
	  }
	  public void setRemarksId(BigDecimal remarksId) {
	  	  this.remarksId = remarksId;
	  }
	  public String getRemarksCode() {
	  	  return remarksCode;
	  }
	  public void setRemarksCode(String remarksCode) {
	  	  this.remarksCode = remarksCode;
	  }
	  public String getRemarksDesc() {
	  	  return remarksDesc;
	  }
	  public void setRemarksDesc(String remarksDesc) {
	  	  this.remarksDesc = remarksDesc;
	  }
	public Boolean getSelect() {
		return select;
	}
	public void setSelect(Boolean select) {
		this.select = select;
	}
	  
	  
	  
}
