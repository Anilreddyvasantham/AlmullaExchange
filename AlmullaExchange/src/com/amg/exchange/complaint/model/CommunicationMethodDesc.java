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
@Table(name = "EX_COMMUNICATION_METHOD_DESC")
public class CommunicationMethodDesc implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal commMethDescId;
	private CommunicationMethod communicationMethodId;
	private LanguageType languageId;
	private String shortDescription;
	private String fullDescription;

	public CommunicationMethodDesc() {
	}

	public CommunicationMethodDesc(BigDecimal commMethDescId,
			CommunicationMethod communicationMethodId,
			LanguageType languageId,
			String shortDescription, String fullDescription) {
		super();
		this.commMethDescId = commMethDescId;
		this.communicationMethodId = communicationMethodId;
		this.languageId = languageId;
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
	}

	@Id
	@GeneratedValue(generator = "ex_comm_meth_desc_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_comm_meth_desc_seq", sequenceName = "EX_COMM_METH_DESC_SEQ", allocationSize = 1)
	@Column(name = "COMMUNICATION_METHOD_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCommMethDescId() {
		return commMethDescId;
	}

	public void setCommMethDescId(BigDecimal commMethDescId) {
		this.commMethDescId = commMethDescId;
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
