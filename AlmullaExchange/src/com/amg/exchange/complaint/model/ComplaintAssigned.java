package com.amg.exchange.complaint.model;

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

import com.amg.exchange.common.model.CountryMaster;

@Entity
@Table(name = "EX_COMPLAINT_ASSIGNED")
public class ComplaintAssigned implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal complaintAssignedId;
	private CountryMaster applicationCountryId;
	private String complaintAssignedCode;
	private String assignTo;
	private String isActive;
	private String logComplaint;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;

	public ComplaintAssigned() {
	}

	public ComplaintAssigned(BigDecimal complaintAssignedId,
			CountryMaster applicationCountryId, String complaintAssignedCode,
			 String isActive, String logComplaint,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String remarks) {
		super();
		this.complaintAssignedId = complaintAssignedId;
		this.applicationCountryId = applicationCountryId;
		this.complaintAssignedCode = complaintAssignedCode;
		//this.assignTo = assignTo;
		this.isActive = isActive;
		this.logComplaint = logComplaint;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(generator = "ex_complaint_assigned_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_complaint_assigned_seq", sequenceName = "EX_COMPLAINT_ASSIGNED_SEQ", allocationSize = 1)
	@Column(name = "COMPLAINT_ASSIGNED_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComplaintAssignedId() {
		return complaintAssignedId;
	}

	public void setComplaintAssignedId(BigDecimal complaintAssignedId) {
		this.complaintAssignedId = complaintAssignedId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountryId() {
		  return applicationCountryId;
	}

	public void setApplicationCountryId(CountryMaster applicationCountryId) {
		  this.applicationCountryId = applicationCountryId;
	}


	@Column(name = "COMPLAINT_ASSIGNE_CODE")
	public String getComplaintAssignedCode() {
		return complaintAssignedCode;
	}

	
       public void setComplaintAssignedCode(String complaintAssignedCode) {
		this.complaintAssignedCode = complaintAssignedCode;
	}

	@Column(name = "ASSIGN_TO")
	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "LOG_COMPLAINT")
	public String getLogComplaint() {
		return logComplaint;
	}

	public void setLogComplaint(String logComplaint) {
		this.logComplaint = logComplaint;
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

	@Column(name = "RAMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
