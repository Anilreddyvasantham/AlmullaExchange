package com.amg.exchange.complaint.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "V_EX_COMP_FOLLOWUP_INQUIRY")
public class ComplaintEnquiryRemittanceView implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal id;
	private Date followUpDate ;
	private BigDecimal remraksId;
	private String remarksCode;
	private BigDecimal assignToId;
	private String assignToCode;
	private BigDecimal communicationMethodId;
	private String communicationMethodCode;
	private BigDecimal complaintActionId;
	private String communicationActionCode;
	private  String complaintremarksDesc;
	private  String complaintAssighedDesc;
	private String communicationMethodDesc;
	private String complaintActiondesc;
	private BigDecimal complaintRefeenceNumber;
	
	
	
	public ComplaintEnquiryRemittanceView() {
	  super();
}

	@Id
	@Column(name = "IDNO")
	public BigDecimal getId() {
		return id;
	}
	
	@Column(name = "FOLLOWUP_DATE")
	public Date getFollowUpDate() {
		return followUpDate;
	}
	@Column(name = "REMARKS_ID")
	public BigDecimal getRemraksId() {
		return remraksId;
	}
	@Column(name = "REMARKS_CODE")
	public String getRemarksCode() {
		return remarksCode;
	}
	@Column(name = "ASSIGN_TO_ID")
	public BigDecimal getAssignToId() {
		return assignToId;
	}
	@Column(name = "ASSIGN_TO_CODE")
	public String getAssignToCode() {
		return assignToCode;
	}
	@Column(name = "COMMUNICATION_METHOD_ID")
	public BigDecimal getCommunicationMethodId() {
		return communicationMethodId;
	}
	@Column(name = "COMMUNICATION_METHOD_CODE")
	public String getCommunicationMethodCode() {
		return communicationMethodCode;
	}
	@Column(name = "COMPLAINT_ACTION_ID")
	public BigDecimal getComplaintActionId() {
		return complaintActionId;
	}
	@Column(name = "COMPLAINT_ACTION_CODE")
	public String getCommunicationActionCode() {
		return communicationActionCode;
	}
	@Column(name = "COMPLAINT_REMARKS_DESC")
	public String getComplaintremarksDesc() {
		return complaintremarksDesc;
	}
	@Column(name = "COMPLAINT_ASSIGNED_DESC")
	public String getComplaintAssighedDesc() {
		return complaintAssighedDesc;
	}
	@Column(name = "COMMUNICATION_METHOD_DESC")
	public String getCommunicationMethodDesc() {
		return communicationMethodDesc;
	}
	@Column(name = "COMPLAINT_ACTION_DESC")
	public String getComplaintActiondesc() {
		return complaintActiondesc;
	}
	@Column(name = "COMPLAINT_REFERENCE")
	public BigDecimal getComplaintRefeenceNumber() {
		return complaintRefeenceNumber;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public void setFollowUpDate(Date followUpDate) {
		this.followUpDate = followUpDate;
	}
	public void setRemraksId(BigDecimal remraksId) {
		this.remraksId = remraksId;
	}
	public void setRemarksCode(String remarksCode) {
		this.remarksCode = remarksCode;
	}
	public void setAssignToId(BigDecimal assignToId) {
		this.assignToId = assignToId;
	}
	public void setAssignToCode(String assignToCode) {
		this.assignToCode = assignToCode;
	}
	public void setCommunicationMethodId(BigDecimal communicationMethodId) {
		this.communicationMethodId = communicationMethodId;
	}
	public void setCommunicationMethodCode(String communicationMethodCode) {
		this.communicationMethodCode = communicationMethodCode;
	}
	public void setComplaintActionId(BigDecimal complaintActionId) {
		this.complaintActionId = complaintActionId;
	}
	public void setCommunicationActionCode(String communicationActionCode) {
		this.communicationActionCode = communicationActionCode;
	}
	public void setComplaintremarksDesc(String complaintremarksDesc) {
		this.complaintremarksDesc = complaintremarksDesc;
	}
	public void setComplaintAssighedDesc(String complaintAssighedDesc) {
		this.complaintAssighedDesc = complaintAssighedDesc;
	}
	public void setCommunicationMethodDesc(String communicationMethodDesc) {
		this.communicationMethodDesc = communicationMethodDesc;
	}
	public void setComplaintActiondesc(String complaintActiondesc) {
		this.complaintActiondesc = complaintActiondesc;
	}
	public void setComplaintRefeenceNumber(BigDecimal complaintRefeenceNumber) {
		this.complaintRefeenceNumber = complaintRefeenceNumber;
	}

}
