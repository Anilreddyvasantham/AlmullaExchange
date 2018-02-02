package com.amg.exchange.complaint.customer.support.bean;

import java.math.BigDecimal;

public class ComplaintSummaryDataTable {

	private BigDecimal complaintReferenceNo;
	private BigDecimal serviceId;
	private BigDecimal bankId;
	private BigDecimal countryId;
	private BigDecimal complaintTypeId;
	private String complaintTypeCode;
	private String complaintTypeDesc;
	private String bankFullName;
	private String countryName;
	private String serviceDescription;
	private BigDecimal totalComplaint;
	private BigDecimal pendingComplaint;
	private BigDecimal applicationCountryId;
	

	public ComplaintSummaryDataTable() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getComplaintReferenceNo() {
		return complaintReferenceNo;
	}

	public void setComplaintReferenceNo(BigDecimal complaintReferenceNo) {
		this.complaintReferenceNo = complaintReferenceNo;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(BigDecimal complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public String getComplaintTypeCode() {
		return complaintTypeCode;
	}

	public void setComplaintTypeCode(String complaintTypeCode) {
		this.complaintTypeCode = complaintTypeCode;
	}

	public String getComplaintTypeDesc() {
		return complaintTypeDesc;
	}

	public void setComplaintTypeDesc(String complaintTypeDesc) {
		this.complaintTypeDesc = complaintTypeDesc;
	}

	public String getBankFullName() {
		return bankFullName;
	}

	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public BigDecimal getTotalComplaint() {
		return totalComplaint;
	}

	public void setTotalComplaint(BigDecimal totalComplaint) {
		this.totalComplaint = totalComplaint;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getPendingComplaint() {
		return pendingComplaint;
	}

	public void setPendingComplaint(BigDecimal pendingComplaint) {
		this.pendingComplaint = pendingComplaint;
	}
	
	

}
