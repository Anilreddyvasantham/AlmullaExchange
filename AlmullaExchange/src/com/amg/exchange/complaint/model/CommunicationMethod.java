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
@Table(name = "EX_COMMUNICATION_METHOD")
public class CommunicationMethod implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal comMethodId;
	private CountryMaster applicationCountryId;
	private String comMethodCode;
	private String email;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;

	public CommunicationMethod() {
	}

	public CommunicationMethod(BigDecimal comMethodId,
			CountryMaster applicationCountryId, String comMethodCode,
			 String email, String isActive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String remarks) {
		super();
		this.comMethodId = comMethodId;
		this.applicationCountryId = applicationCountryId;
		this.comMethodCode = comMethodCode;
		this.email = email;
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
	@GeneratedValue(generator = "ex_comm_meth_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_comm_meth_seq", sequenceName = "EX_COMM_METH_SEQ", allocationSize = 1)
	@Column(name = "COMMUNICATION_METHOD_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComMethodId() {
		return comMethodId;
	}

	public void setComMethodId(BigDecimal comMethodId) {
		this.comMethodId = comMethodId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountryId() {
		  return applicationCountryId;
	}

	public void setApplicationCountryId(CountryMaster applicationCountryId) {
		  this.applicationCountryId = applicationCountryId;
	}

	

	@Column(name = "COMMUNICATION_METHOD_CODE")
	public String getComMethodCode() {
		return comMethodCode;
	}
        public void setComMethodCode(String comMethodCode) {
		this.comMethodCode = comMethodCode;
	}


	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
