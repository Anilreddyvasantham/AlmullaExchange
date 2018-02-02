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
@Table(name = "EX_COMPLAINT_REMARKS_DESC")
public class ComplaintRemarksDesc implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal complaintRemarksDescId;
	private String fullDesc;
	private String shortDesc;
	private LanguageType languageId;
	private ComplaintRemarksMaster complaintRemarks;

	public ComplaintRemarksDesc() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator = "ex_complaint_remarks_Desc_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_complaint_remarks_Desc_seq", sequenceName = "EX_COMPLAINT_REMARKS_DESC_SEQ", allocationSize = 1)
	@Column(name = "COMPLAINT_REMARKS_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComplaintRemarksDescId() {
		return complaintRemarksDescId;
	}

	public void setComplaintRemarksDescId(BigDecimal complaintRemarksDescId) {
		this.complaintRemarksDescId = complaintRemarksDescId;
	}

	@Column(name = "FULL_DESC")
	public String getFullDesc() {
		return fullDesc;
	}

	public void setFullDesc(String fullDesc) {
		this.fullDesc = fullDesc;
	}

	@Column(name = "SHORT_DESC")
	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageId() {
		return languageId;
	}

	public void setLanguageId(LanguageType languageId) {
		this.languageId = languageId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_REMARKS_ID")
	public ComplaintRemarksMaster getComplaintRemarks() {
		return complaintRemarks;
	}

	public void setComplaintRemarks(ComplaintRemarksMaster complaintRemarks) {
		this.complaintRemarks = complaintRemarks;
	}

}
