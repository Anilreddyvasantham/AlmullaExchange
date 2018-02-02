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
@Table(name="EX_COMPLAINT_TYPE")
public class ComplaintTypeMaster implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private BigDecimal complaintTypeId;	
	private CountryMaster appCountryId;
	private String complaintTypeCode;
	private String sendBulk;	
	private BigDecimal osDays;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;

	public ComplaintTypeMaster() {
	}

	
	public ComplaintTypeMaster(BigDecimal complaintTypeId, CountryMaster appCountryId, String complaintTypeCode, String sendBulk, BigDecimal osDays, String isActive, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate, String remarks) {
		this.complaintTypeId = complaintTypeId;
		this.appCountryId = appCountryId;
		this.complaintTypeCode = complaintTypeCode;
		this.sendBulk = sendBulk;
		this.osDays = osDays;
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
	@GeneratedValue(generator="ex_complaint_type_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_complaint_type_seq",sequenceName="EX_COMPLAINT_TYPE_SEQ",allocationSize=1)
	@Column(name = "COMPLAINT_TYPE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(BigDecimal complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(CountryMaster appCountryId) {
		this.appCountryId = appCountryId;
	}
	@Column(name="COMPLAINT_TYPE_CODE")
	public String getComplaintTypeCode() {
		return complaintTypeCode;
	}

	public void setComplaintTypeCode(String complaintTypeCode) {
		this.complaintTypeCode = complaintTypeCode;
	}
	@Column(name="SEND_BULK")
	public String getSendBulk() {
		return sendBulk;
	}

	public void setSendBulk(String sendBulk) {
		this.sendBulk = sendBulk;
	}
	@Column(name="OS_DAYS")
	public BigDecimal getOsDays() {
		return osDays;
	}

	public void setOsDays(BigDecimal osDays) {
		this.osDays = osDays;
	}

	@Column(name="IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}


	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}


	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
