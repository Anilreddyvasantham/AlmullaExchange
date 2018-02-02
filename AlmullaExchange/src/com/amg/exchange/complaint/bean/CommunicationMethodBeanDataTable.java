package com.amg.exchange.complaint.bean;

import java.math.BigDecimal;
import java.util.Date;

public class CommunicationMethodBeanDataTable {

	
	private String communicationMethodCode; 
	private String email;
	private String emailId;
	private String communicationFullDesc;
	private String communicationShortDesc;
	private String communicationFullDescLocal;
	private String communicationShortDescLocal;
	private BigDecimal communicationMethodPk;
	private BigDecimal communicationMethodDescPk;
	private BigDecimal communicationMethodLocalDescPk;
	private BigDecimal engLanguageId;
	private BigDecimal arbLanguageId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String isActive;
	private Boolean remarksCheck;
	private String dynamicLabelForActivateDeactivate;
	private Boolean permanetDeleteCheck=false;
	private Boolean ifEditClicked=false;
	private Boolean activateRecordCheck=false;
	private String statusCheck;
	private BigDecimal countryId;
	
	
	
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getCommunicationFullDescLocal() {
		return communicationFullDescLocal;
	}
	public void setCommunicationFullDescLocal(String communicationFullDescLocal) {
		this.communicationFullDescLocal = communicationFullDescLocal;
	}
	public String getCommunicationShortDescLocal() {
		return communicationShortDescLocal;
	}
	public void setCommunicationShortDescLocal(String communicationShortDescLocal) {
		this.communicationShortDescLocal = communicationShortDescLocal;
	}
	public String getCommunicationMethodCode() {
		return communicationMethodCode;
	}
	public void setCommunicationMethodCode(String communicationMethodCode) {
		this.communicationMethodCode = communicationMethodCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCommunicationFullDesc() {
		return communicationFullDesc;
	}
	public void setCommunicationFullDesc(String communicationFullDesc) {
		this.communicationFullDesc = communicationFullDesc;
	}
	public String getCommunicationShortDesc() {
		return communicationShortDesc;
	}
	public void setCommunicationShortDesc(String communicationShortDesc) {
		this.communicationShortDesc = communicationShortDesc;
	}

	public BigDecimal getCommunicationMethodPk() {
		return communicationMethodPk;
	}
	public void setCommunicationMethodPk(BigDecimal communicationMethodPk) {
		this.communicationMethodPk = communicationMethodPk;
	}
	public BigDecimal getCommunicationMethodDescPk() {
		return communicationMethodDescPk;
	}
	public void setCommunicationMethodDescPk(BigDecimal communicationMethodDescPk) {
		this.communicationMethodDescPk = communicationMethodDescPk;
	}
	public BigDecimal getCommunicationMethodLocalDescPk() {
		return communicationMethodLocalDescPk;
	}
	public void setCommunicationMethodLocalDescPk(
			BigDecimal communicationMethodLocalDescPk) {
		this.communicationMethodLocalDescPk = communicationMethodLocalDescPk;
	}
	public BigDecimal getEngLanguageId() {
		return engLanguageId;
	}
	public void setEngLanguageId(BigDecimal engLanguageId) {
		this.engLanguageId = engLanguageId;
	}
	public BigDecimal getArbLanguageId() {
		return arbLanguageId;
	}
	public void setArbLanguageId(BigDecimal arbLanguageId) {
		this.arbLanguageId = arbLanguageId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Boolean getRemarksCheck() {
		return remarksCheck;
	}
	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}
	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
	}
	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}
	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
	}
	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}
	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}
	public String getStatusCheck() {
		return statusCheck;
	}
	public void setStatusCheck(String statusCheck) {
		this.statusCheck = statusCheck;
	}
	public String getEmailId() {
	  return emailId;
	}
	public void setEmailId(String emailId) {
	  this.emailId = emailId;
	}
	
	
	
	
}
