package com.amg.exchange.complaint.model;

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
@Table(name = "EX_COMPLAINT_ACTION_DESC")
public class ComplaintActionDesc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal complaintActionDescId;
	private ComplaintAction complaintAction;
 	private LanguageType languageId;
	private String shortDescription;
	private String fullDescription;

	public ComplaintActionDesc() {
	}

	public ComplaintActionDesc(BigDecimal complaintActionDescId,
			ComplaintAction complaintAction,
			LanguageType languageId, String shortDescription,
			String fullDescription) {
		super();
		this.complaintActionDescId = complaintActionDescId;
		this.complaintAction = complaintAction;
		this.languageId = languageId;
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
	}

	@Id
	@GeneratedValue(generator = "ex_complaint_action_desc_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_complaint_action_desc_seq", sequenceName = "EX_COMPLAINT_ACTION_DESC_SEQ", allocationSize = 1)
	@Column(name = "COMPLAINT_ACTION_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComplaintActionDescId() {
		return complaintActionDescId;
	}

	public void setComplaintActionDescId(BigDecimal complaintActionDescId) {
		this.complaintActionDescId = complaintActionDescId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_ACTION_ID")
	public ComplaintAction getComplaintAction() {
		return complaintAction;
	}

	public void setComplaintAction(ComplaintAction complaintAction) {
		this.complaintAction = complaintAction;
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
