package com.amg.exchange.complaint.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ComplaimtCreationDataTable {
	  
	  //complaint creation variables
	  private BigDecimal companyId;
	  private BigDecimal documentId;
	  private BigDecimal remitdocumentId;
	  private BigDecimal remittanceYear;
	  private BigDecimal remittanceDocNo;
	  private BigDecimal complaintYear;
	  private BigDecimal complaintNo;
	  private Date logDate;
	  
	  //remittance Details variables
	  private BigDecimal countryId;
	  private String countryName;
	  private BigDecimal bankId;
	  private String bankName;
	  private Date remitDate;
	  private BigDecimal branchId;
	  private String branchName;
	  private BigDecimal serviceId;
	  private String serviceName;
	  private BigDecimal customerId;
	  private String customerName;
	  private BigDecimal mobileNumber;
	  private BigDecimal amount;
	  private String beneficiary;
	  private String accountNumber;
	  private BigDecimal foriegnCurrencyId;
	  private String currencyName;
	  private BigDecimal customerReferenceNumber;
	  
	// Last Panel Complaint creation variables
	  private BigDecimal locationId;
	  private String location;
	  private BigDecimal complaintFromId;
	  private String complaintFrom;
	  private BigDecimal complaintTypeId;
	  private String complaintTypeName;
	  private BigDecimal remarksCodeId;
	  private String remarksCode;
	  private String complaintremarks;
	  private BigDecimal complaintLogPk;
	  private BigDecimal applicationCountryId;
	  
	  //normal Variables
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String isActive;
	  private String remarks;
	  
	  //complaint creation variables getters & setters
	  public BigDecimal getCompanyId() {
	  	  return companyId;
	  }
	  public void setCompanyId(BigDecimal companyId) {
	  	  this.companyId = companyId;
	  }
	 
	  public BigDecimal getDocumentId() {
	  	  return documentId;
	  }
	  public void setDocumentId(BigDecimal documentId) {
	  	  this.documentId = documentId;
	  }
	  public BigDecimal getRemitdocumentId() {
	  	  return remitdocumentId;
	  }
	  public void setRemitdocumentId(BigDecimal remitdocumentId) {
	  	  this.remitdocumentId = remitdocumentId;
	  }
	  public BigDecimal getRemittanceYear() {
	  	  return remittanceYear;
	  }
	  public void setRemittanceYear(BigDecimal remittanceYear) {
	  	  this.remittanceYear = remittanceYear;
	  }
	  public BigDecimal getRemittanceDocNo() {
	  	  return remittanceDocNo;
	  }
	  public void setRemittanceDocNo(BigDecimal remittanceDocNo) {
	  	  this.remittanceDocNo = remittanceDocNo;
	  }
	  public BigDecimal getComplaintYear() {
	  	  return complaintYear;
	  }
	  public void setComplaintYear(BigDecimal complaintYear) {
	  	  this.complaintYear = complaintYear;
	  }
	  public BigDecimal getComplaintNo() {
	  	  return complaintNo;
	  }
	  public void setComplaintNo(BigDecimal complaintNo) {
	  	  this.complaintNo = complaintNo;
	  }
	  public Date getLogDate() {
	  	  return logDate;
	  }
	  public void setLogDate(Date logDate) {
	  	  this.logDate = logDate;
	  }
	  //remittance Details variables getters & setters
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
	  public BigDecimal getBankId() {
	  	  return bankId;
	  }
	  public void setBankId(BigDecimal bankId) {
	  	  this.bankId = bankId;
	  }
	  public String getBankName() {
	  	  return bankName;
	  }
	  public void setBankName(String bankName) {
	  	  this.bankName = bankName;
	  }
	  public Date getRemitDate() {
	  	  return remitDate;
	  }
	  public void setRemitDate(Date remitDate) {
	  	  this.remitDate = remitDate;
	  }
	  public BigDecimal getBranchId() {
	  	  return branchId;
	  }
	  public void setBranchId(BigDecimal branchId) {
	  	  this.branchId = branchId;
	  }
	  public String getBranchName() {
	  	  return branchName;
	  }
	  public void setBranchName(String branchName) {
	  	  this.branchName = branchName;
	  }
	  public BigDecimal getServiceId() {
	  	  return serviceId;
	  }
	  public void setServiceId(BigDecimal serviceId) {
	  	  this.serviceId = serviceId;
	  }
	  public String getServiceName() {
	  	  return serviceName;
	  }
	  public void setServiceName(String serviceName) {
	  	  this.serviceName = serviceName;
	  }
	  public String getCustomerName() {
	  	  return customerName;
	  }
	  public void setCustomerName(String customerName) {
	  	  this.customerName = customerName;
	  }
	  public BigDecimal getMobileNumber() {
	  	  return mobileNumber;
	  }
	  public void setMobileNumber(BigDecimal mobileNumber) {
	  	  this.mobileNumber = mobileNumber;
	  }
	  public BigDecimal getAmount() {
	  	  return amount;
	  }
	  public void setAmount(BigDecimal amount) {
	  	  this.amount = amount;
	  }
	  public String getBeneficiary() {
	  	  return beneficiary;
	  }
	  public void setBeneficiary(String beneficiary) {
	  	  this.beneficiary = beneficiary;
	  }
	  public String getAccountNumber() {
	  	  return accountNumber;
	  }
	  public void setAccountNumber(String accountNumber) {
	  	  this.accountNumber = accountNumber;
	  }
	  
	public BigDecimal getCustomerId() {
	  	  return customerId;
	  }
	  public void setCustomerId(BigDecimal customerId) {
	  	  this.customerId = customerId;
	  }
	  	public BigDecimal getForiegnCurrencyId() {
		  	  return foriegnCurrencyId;
		  }
		  public void setForiegnCurrencyId(BigDecimal foriegnCurrencyId) {
		  	  this.foriegnCurrencyId = foriegnCurrencyId;
		  }
		  public String getCurrencyName() {
		  	  return currencyName;
		  }
		  public void setCurrencyName(String currencyName) {
		  	  this.currencyName = currencyName;
		  }
		  public BigDecimal getCustomerReferenceNumber() {
		  	  return customerReferenceNumber;
		  }
		  public void setCustomerReferenceNumber(BigDecimal customerReferenceNumber) {
		  	  this.customerReferenceNumber = customerReferenceNumber;
		  }
	  
	  
	  // Last Panel Complaint creation added getters & setters
	  
	  
	 
	  public String getLocation() {
	  	  return location;
	  }
	  public BigDecimal getLocationId() {
	  	  return locationId;
	  }
	  public void setLocationId(BigDecimal locationId) {
	  	  this.locationId = locationId;
	  }
	  public void setLocation(String location) {
	  	  this.location = location;
	  }
	  public BigDecimal getComplaintFromId() {
	  	  return complaintFromId;
	  }
	  public void setComplaintFromId(BigDecimal complaintFromId) {
	  	  this.complaintFromId = complaintFromId;
	  }
	  public String getComplaintFrom() {
	  	  return complaintFrom;
	  }
	  public void setComplaintFrom(String complaintFrom) {
	  	  this.complaintFrom = complaintFrom;
	  }
	  public BigDecimal getComplaintTypeId() {
	  	  return complaintTypeId;
	  }
	  public void setComplaintTypeId(BigDecimal complaintTypeId) {
	  	  this.complaintTypeId = complaintTypeId;
	  }
	  public String getComplaintTypeName() {
	  	  return complaintTypeName;
	  }
	  public void setComplaintTypeName(String complaintTypeName) {
	  	  this.complaintTypeName = complaintTypeName;
	  }
	  public BigDecimal getRemarksCodeId() {
	  	  return remarksCodeId;
	  }
	  public void setRemarksCodeId(BigDecimal remarksCodeId) {
	  	  this.remarksCodeId = remarksCodeId;
	  }
	  public String getRemarksCode() {
	  	  return remarksCode;
	  }
	  public void setRemarksCode(String remarksCode) {
	  	  this.remarksCode = remarksCode;
	  }
	  
	  public String getComplaintremarks() {
	  	  return complaintremarks;
	  }
	  public void setComplaintremarks(String complaintremarks) {
	  	  this.complaintremarks = complaintremarks;
	  }
	 
	//Complaint complaintLogPk
		public BigDecimal getComplaintLogPk() {
		  return complaintLogPk;
		}

		public void setComplaintLogPk(BigDecimal complaintLogPk) {
		  this.complaintLogPk = complaintLogPk;
		}
	//Application Country	
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  //normal Variables getters&setters
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
	
	  private String complaintAssignedTo;

	  public String getComplaintAssignedTo() {
	  	  return complaintAssignedTo;
	  }
	  public void setComplaintAssignedTo(String complaintAssignedTo) {
	  	  this.complaintAssignedTo = complaintAssignedTo;
	  }
	  
	  
	  
	  
	  
	  
	  
}
