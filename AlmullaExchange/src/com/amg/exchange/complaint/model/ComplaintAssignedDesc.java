package com.amg.exchange.complaint.model;

import java.io.Serializable;
import java.math.BigDecimal;

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

import com.amg.exchange.common.model.LanguageType;

@Entity
@Table(name = "EX_COMPLAINT_ASSIGNED_DESC")
public class ComplaintAssignedDesc implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal complaintAssignedDescId;
	private ComplaintAssigned complaintAssigned;
	private LanguageType languageId;
	private String shortDescription;
	private String fullDescription;

	public ComplaintAssignedDesc() {
	}

	public ComplaintAssignedDesc(BigDecimal complaintAssignedDescId,
			ComplaintAssigned complaintAssigned, LanguageType languageId,
			String shortDescription, String fullDescription) {
		super();
		this.complaintAssignedDescId = complaintAssignedDescId;
		this.complaintAssigned = complaintAssigned;
		this.languageId = languageId;
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
	}

	@Id 
	@GeneratedValue(generator = "ex_complaint_assigned_desc_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_complaint_assigned_desc_seq", sequenceName = "EX_COMPLAINT_ASSIGNED_DESC_SEQ", allocationSize = 1)
	@Column(name ="COMPLAINT_ASSIGNED_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getcomplaintAssignedDescId() {
		return complaintAssignedDescId;
	}

	public void setComplaintAssignedDescId(BigDecimal complaintAssignedDescId) {
		this.complaintAssignedDescId = complaintAssignedDescId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_ASSIGNED_ID")
	public ComplaintAssigned getComplaintAssigned() {
		return complaintAssigned;
	}

	public void setComplaintAssigned(ComplaintAssigned complaintAssigned) {
		this.complaintAssigned = complaintAssigned;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageId() {
		return languageId;
	}

	public void setLanguageId(LanguageType languageId) {
		this.languageId = languageId;
	}

	@Column(name = "SHORT_DESC")
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	@Column(name = "FULL_DESC")
	public String getFullDescription() {
		return fullDescription;
	}

	
	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	

}
