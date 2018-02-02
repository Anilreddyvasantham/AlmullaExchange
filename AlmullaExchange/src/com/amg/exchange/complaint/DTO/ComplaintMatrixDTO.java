package com.amg.exchange.complaint.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ComplaintMatrixDTO implements Serializable{

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  
	  private BigDecimal appCountryId;
	  private BigDecimal countryId;
	  private BigDecimal bankId;
	  private BigDecimal serviceId;
	  private BigDecimal complaintTypeId;
	  private BigDecimal complaintTakenById;
	  private BigDecimal complaintActionId;
	  private BigDecimal complaintDestionId;
	  private BigDecimal communicationMethodId;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;
	  private String isActive;
	  private BigDecimal compalintMatrixPk;
	  
	  
	  public BigDecimal getAppCountryId() {
	  	  return appCountryId;
	  }
	  public void setAppCountryId(BigDecimal appCountryId) {
	  	  this.appCountryId = appCountryId;
	  }
	  public BigDecimal getCountryId() {
	  	  return countryId;
	  }
	  public void setCountryId(BigDecimal countryId) {
	  	  this.countryId = countryId;
	  }
	  public BigDecimal getBankId() {
	  	  return bankId;
	  }
	  public void setBankId(BigDecimal bankId) {
	  	  this.bankId = bankId;
	  }
	  public BigDecimal getServiceId() {
	  	  return serviceId;
	  }
	  public void setServiceId(BigDecimal serviceId) {
	  	  this.serviceId = serviceId;
	  }
	  public BigDecimal getComplaintTypeId() {
	  	  return complaintTypeId;
	  }
	  public void setComplaintTypeId(BigDecimal complaintTypeId) {
	  	  this.complaintTypeId = complaintTypeId;
	  }
	  public BigDecimal getComplaintTakenById() {
	  	  return complaintTakenById;
	  }
	  public void setComplaintTakenById(BigDecimal complaintTakenById) {
	  	  this.complaintTakenById = complaintTakenById;
	  }
	  public BigDecimal getComplaintActionId() {
	  	  return complaintActionId;
	  }
	  public void setComplaintActionId(BigDecimal complaintActionId) {
	  	  this.complaintActionId = complaintActionId;
	  }
	  public BigDecimal getComplaintDestionId() {
	  	  return complaintDestionId;
	  }
	  public void setComplaintDestionId(BigDecimal complaintDestionId) {
	  	  this.complaintDestionId = complaintDestionId;
	  }
	  public BigDecimal getCommunicationMethodId() {
	  	  return communicationMethodId;
	  }
	  public void setCommunicationMethodId(BigDecimal communicationMethodId) {
	  	  this.communicationMethodId = communicationMethodId;
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
	  public BigDecimal getCompalintMatrixPk() {
	  	  return compalintMatrixPk;
	  }
	  public void setCompalintMatrixPk(BigDecimal compalintMatrixPk) {
	  	  this.compalintMatrixPk = compalintMatrixPk;
	  }
	  
	   

}
