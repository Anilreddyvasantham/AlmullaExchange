package com.amg.exchange.complaint.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "V_EX_PENDING_COMP_INQUIRY")
public class ComplaintPendingEnquiryView implements Serializable {

	  private static final long serialVersionUID = 1L;
	  
	  private BigDecimal locationId;
	  private String locationCode;
	  private Date logDate;
	  private BigDecimal complaintReference;
	  private BigDecimal complaintTypeId;
	  private String complaintTypeCode;
	  private String remarks;
	  private BigDecimal assignedId;
	  private String assignedCode;
	  private BigDecimal tekenById;
	  private String takenByCode;
	  private BigDecimal remarksId;
	  private String remarksCode;
	  private Date closedDate;
	  private String complaintTypeDesc;
	  private String complaintAssignedDesc;
	  private String remarksDesc;
	  private String branchName;
	  private String takenByDesc;
	  private BigDecimal dealYear;
	  
	  
	  public ComplaintPendingEnquiryView() {
		  
	  }
	  
	  
	
	  
	  @Column(name = "LOCATION_ID")
	  public BigDecimal getLocationId() {
	  	  return locationId;
	  }
	  public void setLocationId(BigDecimal locationId) {
	  	  this.locationId = locationId;
	  }
	  @Column(name = "LOCATION_CODE")
	  public String getLocationCode() {
	  	  return locationCode;
	  }
	  public void setLocationCode(String locationCode) {
	  	  this.locationCode = locationCode;
	  }
	  @Column(name = "LOG_DATE")
	  public Date getLogDate() {
	  	  return logDate;
	  }
	  public void setLogDate(Date logDate) {
	  	  this.logDate = logDate;
	  }
	  @Id
	  @Column(name = "COMPLAINT_REFERENCE")
	  public BigDecimal getComplaintReference() {
	  	  return complaintReference;
	  }
	  public void setComplaintReference(BigDecimal complaintReference) {
	  	  this.complaintReference = complaintReference;
	  }
	  @Column(name = "COMPLAINT_TYPE_ID")
	  public BigDecimal getComplaintTypeId() {
	  	  return complaintTypeId;
	  }
	  public void setComplaintTypeId(BigDecimal complaintTypeId) {
	  	  this.complaintTypeId = complaintTypeId;
	  }
	  @Column(name = "COMPLAINT_TYPE_CODE")
	  public String getComplaintTypeCode() {
	  	  return complaintTypeCode;
	  }
	  public void setComplaintTypeCode(String complaintTypeCode) {
	  	  this.complaintTypeCode = complaintTypeCode;
	  }
	  @Column(name = "REMARKS")
	  public String getRemarks() {
	  	  return remarks;
	  }
	  public void setRemarks(String remarks) {
	  	  this.remarks = remarks;
	  }
	  @Column(name = "ASSIGN_TO_ID")
	  public BigDecimal getAssignedId() {
	  	  return assignedId;
	  }
	  public void setAssignedId(BigDecimal assignedId) {
	  	  this.assignedId = assignedId;
	  }
	  @Column(name = "ASSIGN_TO_CODE")
	  public String getAssignedCode() {
	  	  return assignedCode;
	  }
	  public void setAssignedCode(String assignedCode) {
	  	  this.assignedCode = assignedCode;
	  }
	  @Column(name = "TAKEN_BY_ID")
	  public BigDecimal getTekenById() {
	  	  return tekenById;
	  }
	  public void setTekenById(BigDecimal tekenById) {
	  	  this.tekenById = tekenById;
	  }
	  @Column(name = "TAKEN_BY_CODE")
	  public String getTakenByCode() {
	  	  return takenByCode;
	  }
	  public void setTakenByCode(String takenByCode) {
	  	  this.takenByCode = takenByCode;
	  }
	  @Column(name = "REMARKS_ID")
	  public BigDecimal getRemarksId() {
	  	  return remarksId;
	  }
	  public void setRemarksId(BigDecimal remarksId) {
	  	  this.remarksId = remarksId;
	  }
	  @Column(name = "REMARKS_CODE")
	  public String getRemarksCode() {
	  	  return remarksCode;
	  }
	  public void setRemarksCode(String remarksCode) {
	  	  this.remarksCode = remarksCode;
	  }
	  @Column(name = "CLOSED_DATE")
	  public Date getClosedDate() {
	  	  return closedDate;
	  }
	  
	  public void setClosedDate(Date closedDate) {
		    this.closedDate = closedDate;
	  }
	  @Column(name = "COMPLAINT_TYPE_DESC")
	  public String getComplaintTypeDesc() {
		    return complaintTypeDesc;
	  }
	 
	  public void setComplaintTypeDesc(String complaintTypeDesc) {
		    this.complaintTypeDesc = complaintTypeDesc;
	  }
	  @Column(name = "COMPLAINT_ASSIGNED_DESC")
	  public String getComplaintAssignedDesc() {
		    return complaintAssignedDesc;
	  }
	  
	  public void setComplaintAssignedDesc(String complaintAssignedDesc) {
		    this.complaintAssignedDesc = complaintAssignedDesc;
	  }
	  @Column(name = "COMPLAINT_REMARKS_DESC")
	  public String getRemarksDesc() {
		    return remarksDesc;
	  }

	  public void setRemarksDesc(String remarksDesc) {
		    this.remarksDesc = remarksDesc;
	  }
	  @Column(name = "BRANCH_NAME")
	  public String getBranchName() {
		    return branchName;
	  }

	  public void setBranchName(String branchName) {
		    this.branchName = branchName;
	  }
	  @Column(name = "COMPLAINT_TAKENBY_DESC")
	  public String getTakenByDesc() {
		    return takenByDesc;
	  }

	  public void setTakenByDesc(String takenByDesc) {
		    this.takenByDesc = takenByDesc;
	  }
	  @Column(name = "COMPLAINT_FINANCE_YEAR")
	  public BigDecimal getDealYear() {
		    return dealYear;
	  }

	  public void setDealYear(BigDecimal dealYear) {
		    this.dealYear = dealYear;
	  }
	  

}
