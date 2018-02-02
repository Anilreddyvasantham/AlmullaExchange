package com.amg.exchange.complaint.customer.support.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.complaint.model.CommunicationMethod;
import com.amg.exchange.complaint.model.ComplaintAction;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.treasury.model.Document;

@Entity
@Table(name = "EX_COMPLAINT_FOLLOWUP")
public class ComplaintFollowup implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal complaintFollowupId;
	private CountryMaster applicationCountryId;
	private CompanyMaster companyId;
	private Document documentId;
	private BigDecimal complaintFinanceYear;
	private BigDecimal complaintReference;
	private Date followupDate;
	private ComplaintRemarksMaster remarksId;
	private String remarksCode;
	private ComplaintAssigned assigntoId;
	private String assigntoCode;
	private Date assignDate;
	private CommunicationMethod communicationMethodId;
	private String communicationMathodCode;
	private ComplaintAction complaintActionId;
	private String complaintActionCode;
	private String emailTo;
	private String emailSubject;
	private String emailText;
	private String testKey;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;

	public ComplaintFollowup() {
	}

	public ComplaintFollowup(BigDecimal complaintFollowupId,
			CountryMaster applicationCountryId, CompanyMaster companyId,
			Document documentId, BigDecimal complaintFinanceYear,
			BigDecimal complaintReference, Date followupDate,
			ComplaintRemarksMaster remarksId, String remarksCode,
			ComplaintAssigned assigntoId, String assigntoCode, Date assignDate,
			CommunicationMethod communicationMethodId,
			String communicationMathodCode, ComplaintAction complaintActionId,
			String complaintActionCode, String emailTo, String emailSubject,
			String emailText, String testKey, String isActive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String remarks) {
		super();
		this.complaintFollowupId = complaintFollowupId;
		this.applicationCountryId = applicationCountryId;
		this.companyId = companyId;
		this.documentId = documentId;
		this.complaintFinanceYear = complaintFinanceYear;
		this.complaintReference = complaintReference;
		this.followupDate = followupDate;
		this.remarksId = remarksId;
		this.remarksCode = remarksCode;
		this.assigntoId = assigntoId;
		this.assigntoCode = assigntoCode;
		this.assignDate = assignDate;
		this.communicationMethodId = communicationMethodId;
		this.communicationMathodCode = communicationMathodCode;
		this.complaintActionId = complaintActionId;
		this.complaintActionCode = complaintActionCode;
		this.emailTo = emailTo;
		this.emailSubject = emailSubject;
		this.emailText = emailText;
		this.testKey = testKey;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(generator = "ex_complaint_followup_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_complaint_followup_seq", sequenceName = "EX_COMPLAINT_FOLLOWUP_SEQ", allocationSize = 1)
	@Column(name = "COMPLAINT_FOLLOWUP_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComplaintFollowupId() {
		return complaintFollowupId;
	}

	public void setComplaintFollowupId(BigDecimal complaintFollowupId) {
		this.complaintFollowupId = complaintFollowupId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(CountryMaster applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getCompanyId() {
		return companyId;
	}

	public void setCompanyId(CompanyMaster companyId) {
		this.companyId = companyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Document documentId) {
		this.documentId = documentId;
	}

	@Column(name = "COMPLAINT_FINANCE_YEAR")
	public BigDecimal getComplaintFinanceYear() {
		return complaintFinanceYear;
	}

	public void setComplaintFinanceYear(BigDecimal complaintFinanceYear) {
		this.complaintFinanceYear = complaintFinanceYear;
	}

	@Column(name = "COMPLAINT_REFERENCE")
	public BigDecimal getComplaintReference() {
		return complaintReference;
	}

	public void setComplaintReference(BigDecimal complaintReference) {
		this.complaintReference = complaintReference;
	}
	

	@Column(name = "FOLLOWUP_DATE")	
	public Date getFollowupDate() {
		return followupDate;
	}

	public void setFollowupDate(Date followupDate) {
		this.followupDate = followupDate;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REMARKS_ID")
	public ComplaintRemarksMaster getRemarksId() {
		return remarksId;
	}

	

	public void setRemarksId(ComplaintRemarksMaster remarksId) {
		this.remarksId = remarksId;
	}

	@Column(name = "REMARKS_CODE")
	public String getRemarksCode() {
		return remarksCode;
	}

	public void setRemarksCode(String remarksCode) {
		this.remarksCode = remarksCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSIGN_TO_ID")
	public ComplaintAssigned getAssigntoId() {
		return assigntoId;
	}

	public void setAssigntoId(ComplaintAssigned assigntoId) {
		this.assigntoId = assigntoId;
	}

	@Column(name = "ASSIGN_TO_CODE")
	public String getAssigntoCode() {
		return assigntoCode;
	}

	public void setAssigntoCode(String assigntoCode) {
		this.assigntoCode = assigntoCode;
	}

	@Column(name = "ASSIGN_DATE")
	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMMUNICATION_METHOD_ID")
	public CommunicationMethod getCommunicationMethodId() {
		return communicationMethodId;
	}

	public void setCommunicationMethodId(
			CommunicationMethod communicationMethodId) {
		this.communicationMethodId = communicationMethodId;
	}

	@Column(name = "COMMUNICATION_METHOD_CODE")
	public String getCommunicationMathodCode() {
		return communicationMathodCode;
	}

	public void setCommunicationMathodCode(String communicationMathodCode) {
		this.communicationMathodCode = communicationMathodCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_ACTION_ID")
	public ComplaintAction getComplaintActionId() {
		return complaintActionId;
	}

	public void setComplaintActionId(ComplaintAction complaintActionId) {
		this.complaintActionId = complaintActionId;
	}

	@Column(name = "COMPLAINT_ACTION_CODE")
	public String getComplaintActionCode() {
		return complaintActionCode;
	}

	public void setComplaintActionCode(String complaintActionCode) {
		this.complaintActionCode = complaintActionCode;
	}

	@Column(name = "EMAIL_TO")
	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	@Column(name = "EMAIL_SUBJECT")
	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	@Column(name = "EMAIL_TEXT")
	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	@Column(name = "TESTKEY")
	public String getTestKey() {
		return testKey;
	}

	public void setTestKey(String testKey) {
		this.testKey = testKey;
	}

	@Column(name = "IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
