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
@Table(name = "EX_COMPLAINT_REMARKS")
public class ComplaintRemarksMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal complaintRemarksId;
	private CountryMaster appCountryId;
	private String complaintRemarksCode;
	private String tagRemittance;
	private String tagBenificiary;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;

	public ComplaintRemarksMaster() {
	}

	@Id
	@GeneratedValue(generator = "ex_complaint_remarks_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_complaint_remarks_seq", sequenceName = "EX_COMPLAINT_REMARKS_SEQ", allocationSize = 1)
	@Column(name = "COMPLAINT_REMARKS_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComplaintRemarksId() {
		return complaintRemarksId;
	}

	public void setComplaintRemarksId(BigDecimal complaintRemarksId) {
		this.complaintRemarksId = complaintRemarksId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(CountryMaster appCountryId) {
		this.appCountryId = appCountryId;
	}

	@Column(name = "REMARKS_CODE")
	public String getComplaintRemarksCode() {
		return complaintRemarksCode;
	}

	public void setComplaintRemarksCode(String complaintRemarksCode) {
		this.complaintRemarksCode = complaintRemarksCode;
	}

	@Column(name = "TAG_REMITTANCE")
	public String getTagRemittance() {
		return tagRemittance;
	}

	public void setTagRemittance(String tagRemittance) {
		this.tagRemittance = tagRemittance;
	}

	@Column(name = "TAG_BENEFICIARY")
	public String getTagBenificiary() {
		return tagBenificiary;
	}

	public void setTagBenificiary(String tagBenificiary) {
		this.tagBenificiary = tagBenificiary;
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
