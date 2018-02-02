package com.amg.exchange.complaint.customer.support.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ComplaintRegisteredToRemittancesDataTable implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal complaintLogId;
	private BigDecimal applicationCountry;
	private BigDecimal companyMaster;
	private BigDecimal remittanceCompany;
	private BigDecimal document;
	private BigDecimal customer;
	private BigDecimal tokenById;
	private BigDecimal customerReference;
	private BigDecimal country;
	private BigDecimal service;
	private BigDecimal bank;
	private BigDecimal countryBranch;
	private BigDecimal assignedToId;
	private BigDecimal complaintFromId;
	private BigDecimal complaintType;
	private BigDecimal complaintRemarks;
	private String countryBranchCode;
	private String countryBranchName;
	private String tokenByCode;
	private String assignedToCode;
	private String complaintFromCode;
	private String complaintTypeCode;
	private BigDecimal complaintFinancialYear;
	private BigDecimal complaintReference;
	private Date logDate;
	private Date assignedDate;
	private String complaintRemarksCode;
	private Date closedDate;
	private BigDecimal smsSequence;
	private String remittanceCompanyCode;
	private BigDecimal remittanceDocument;
	private String remittanceDocumentCode;
	private BigDecimal remittanceDocumentFinancialYear;
	private BigDecimal remittanceDocumentNo;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private Date remittanceDate;
	private BigDecimal foriegnCurrencyAmount;
	private String foriegnCurrencyName;
	private String communicationMethodName;
	private String assignedDescription;
	
	private String navFlag;
	
	private Boolean booCheckbox = false;

	public ComplaintRegisteredToRemittancesDataTable() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getComplaintLogId() {
		return complaintLogId;
	}

	public void setComplaintLogId(BigDecimal complaintLogId) {
		this.complaintLogId = complaintLogId;
	}

	public BigDecimal getApplicationCountry() {
		return applicationCountry;
	}

	public void setApplicationCountry(BigDecimal applicationCountry) {
		this.applicationCountry = applicationCountry;
	}

	public BigDecimal getCompanyMaster() {
		return companyMaster;
	}

	public void setCompanyMaster(BigDecimal companyMaster) {
		this.companyMaster = companyMaster;
	}

	public BigDecimal getRemittanceCompany() {
		return remittanceCompany;
	}

	public void setRemittanceCompany(BigDecimal remittanceCompany) {
		this.remittanceCompany = remittanceCompany;
	}

	public BigDecimal getDocument() {
		return document;
	}

	public void setDocument(BigDecimal document) {
		this.document = document;
	}

	public BigDecimal getCustomer() {
		return customer;
	}

	public void setCustomer(BigDecimal customer) {
		this.customer = customer;
	}

	public BigDecimal getTokenById() {
		return tokenById;
	}

	public void setTokenById(BigDecimal tokenById) {
		this.tokenById = tokenById;
	}

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	public BigDecimal getCountry() {
		return country;
	}

	public void setCountry(BigDecimal country) {
		this.country = country;
	}

	public BigDecimal getService() {
		return service;
	}

	public void setService(BigDecimal service) {
		this.service = service;
	}

	public BigDecimal getBank() {
		return bank;
	}

	public void setBank(BigDecimal bank) {
		this.bank = bank;
	}

	public BigDecimal getCountryBranch() {
		return countryBranch;
	}

	public void setCountryBranch(BigDecimal countryBranch) {
		this.countryBranch = countryBranch;
	}

	public BigDecimal getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(BigDecimal assignedToId) {
		this.assignedToId = assignedToId;
	}

	public BigDecimal getComplaintFromId() {
		return complaintFromId;
	}

	public void setComplaintFromId(BigDecimal complaintFromId) {
		this.complaintFromId = complaintFromId;
	}

	public BigDecimal getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(BigDecimal complaintType) {
		this.complaintType = complaintType;
	}

	public BigDecimal getComplaintRemarks() {
		return complaintRemarks;
	}

	public void setComplaintRemarks(BigDecimal complaintRemarks) {
		this.complaintRemarks = complaintRemarks;
	}

	public String getCountryBranchCode() {
		return countryBranchCode;
	}

	public void setCountryBranchCode(String countryBranchCode) {
		this.countryBranchCode = countryBranchCode;
	}

	public String getTokenByCode() {
		return tokenByCode;
	}

	public void setTokenByCode(String tokenByCode) {
		this.tokenByCode = tokenByCode;
	}

	public String getAssignedToCode() {
		return assignedToCode;
	}

	public void setAssignedToCode(String assignedToCode) {
		this.assignedToCode = assignedToCode;
	}

	public String getComplaintFromCode() {
		return complaintFromCode;
	}

	public void setComplaintFromCode(String complaintFromCode) {
		this.complaintFromCode = complaintFromCode;
	}

	public String getComplaintTypeCode() {
		return complaintTypeCode;
	}

	public void setComplaintTypeCode(String complaintTypeCode) {
		this.complaintTypeCode = complaintTypeCode;
	}

	public BigDecimal getComplaintFinancialYear() {
		return complaintFinancialYear;
	}

	public void setComplaintFinancialYear(BigDecimal complaintFinancialYear) {
		this.complaintFinancialYear = complaintFinancialYear;
	}

	public BigDecimal getComplaintReference() {
		return complaintReference;
	}

	public void setComplaintReference(BigDecimal complaintReference) {
		this.complaintReference = complaintReference;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public String getComplaintRemarksCode() {
		return complaintRemarksCode;
	}

	public void setComplaintRemarksCode(String complaintRemarksCode) {
		this.complaintRemarksCode = complaintRemarksCode;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public BigDecimal getSmsSequence() {
		return smsSequence;
	}

	public void setSmsSequence(BigDecimal smsSequence) {
		this.smsSequence = smsSequence;
	}

	public String getRemittanceCompanyCode() {
		return remittanceCompanyCode;
	}

	public void setRemittanceCompanyCode(String remittanceCompanyCode) {
		this.remittanceCompanyCode = remittanceCompanyCode;
	}
	
	public BigDecimal getRemittanceDocument() {
		return remittanceDocument;
	}

	public void setRemittanceDocument(BigDecimal remittanceDocument) {
		this.remittanceDocument = remittanceDocument;
	}

	public String getRemittanceDocumentCode() {
		return remittanceDocumentCode;
	}

	public void setRemittanceDocumentCode(String remittanceDocumentCode) {
		this.remittanceDocumentCode = remittanceDocumentCode;
	}

	public BigDecimal getRemittanceDocumentFinancialYear() {
		return remittanceDocumentFinancialYear;
	}

	public void setRemittanceDocumentFinancialYear(BigDecimal remittanceDocumentFinancialYear) {
		this.remittanceDocumentFinancialYear = remittanceDocumentFinancialYear;
	}

	public BigDecimal getRemittanceDocumentNo() {
		return remittanceDocumentNo;
	}

	public void setRemittanceDocumentNo(BigDecimal remittanceDocumentNo) {
		this.remittanceDocumentNo = remittanceDocumentNo;
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

	public String getCountryBranchName() {
		return countryBranchName;
	}

	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}

	

	public Boolean getBooCheckbox() {
		return booCheckbox;
	}

	public void setBooCheckbox(Boolean booCheckbox) {
		this.booCheckbox = booCheckbox;
	}

	public Date getRemittanceDate() {
		return remittanceDate;
	}

	public void setRemittanceDate(Date remittanceDate) {
		this.remittanceDate = remittanceDate;
	}

	public BigDecimal getForiegnCurrencyAmount() {
		return foriegnCurrencyAmount;
	}

	public void setForiegnCurrencyAmount(BigDecimal foriegnCurrencyAmount) {
		this.foriegnCurrencyAmount = foriegnCurrencyAmount;
	}

	public String getForiegnCurrencyName() {
		return foriegnCurrencyName;
	}

	public void setForiegnCurrencyName(String foriegnCurrencyName) {
		this.foriegnCurrencyName = foriegnCurrencyName;
	}

	public String getCommunicationMethodName() {
		return communicationMethodName;
	}

	public void setCommunicationMethodName(String communicationMethodName) {
		this.communicationMethodName = communicationMethodName;
	}

	public String getAssignedDescription() {
		return assignedDescription;
	}

	public void setAssignedDescription(String assignedDescription) {
		this.assignedDescription = assignedDescription;
	}

	public String getNavFlag() {
		return navFlag;
	}

	public void setNavFlag(String navFlag) {
		this.navFlag = navFlag;
	}

	
	
	

}
