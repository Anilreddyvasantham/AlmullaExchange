package com.amg.exchange.complaint.customer.support.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_COMPLAINT_SUMMARY")
public class ComplaintSummary implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal idNo;
	private BigDecimal serviceId;
	private BigDecimal bankId;
	private BigDecimal countryId;
	private BigDecimal complaintTypeId;
	private String complaintTypeCode;
	private String complaintTypeDesc;
	private String bankFullName;
	private String countryName;
	private String serviceDescription;
	private BigDecimal applicationCountryId;
	private String assignToCode;
	private Date closedDate;

	public ComplaintSummary(BigDecimal idNo, BigDecimal serviceId, BigDecimal bankId, BigDecimal countryId, BigDecimal complaintTypeId, String complaintTypeCode, String complaintTypeDesc, String bankFullName, String countryName, String serviceDescription,
			 Date closedDate) {
		this.idNo = idNo;
		this.serviceId = serviceId;
		this.bankId = bankId;
		this.countryId = countryId;
		this.complaintTypeId = complaintTypeId;
		this.complaintTypeCode = complaintTypeCode;
		this.complaintTypeDesc = complaintTypeDesc;
		this.bankFullName = bankFullName;
		this.countryName = countryName;
		this.serviceDescription = serviceDescription;
		//this.applicationCountryId = applicationCountryId;
		//this.assignToCode = assignToCode;
		this.closedDate = closedDate;
	}

	public ComplaintSummary() {
	}

	@Id
	@Column(name = "ID_NO")
	public BigDecimal getIdNo() {
		return idNo;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	@Column(name = "SERVICE_ID")
	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	@Column(name = "COMPLAINT_TYPE_ID")
	public BigDecimal getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(BigDecimal complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	@Column(name = "COMPLAINT_TYPE_CODE")
	public String getComplaintTypeCode() {
		return complaintTypeCode;
	}

	public void setComplaintTypeCode(String complaintTypeCode) {
		this.complaintTypeCode = complaintTypeCode;
	}

	@Column(name = "COMPLAINT_TYPE_DESC")
	public String getComplaintTypeDesc() {
		return complaintTypeDesc;
	}

	public void setComplaintTypeDesc(String complaintTypeDesc) {
		this.complaintTypeDesc = complaintTypeDesc;
	}

	@Column(name = "BANK_FULL_NAME")
	public String getBankFullName() {
		return bankFullName;
	}

	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}

	@Column(name = "SERVICE_DESCRIPTION")
	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	
	@Column(name = "COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	@Column(name = "COUNTRY_NAME")
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/*@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}*/
	
	/*@Column(name = "ASSIGN_TO_CODE")
	public String getAssignToCode() {
		return assignToCode;
	}

	public void setAssignToCode(String assignToCode) {
		this.assignToCode = assignToCode;
	}*/
	
	@Column(name = "CLOSED_DATE")
	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

}
