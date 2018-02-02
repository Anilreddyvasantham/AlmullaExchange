package com.amg.exchange.complaint.bean;

import java.util.Date;

public class ComplainPendingEnquiryDatatable {
	
	private Date logDate = null;
	private String user = null;
	private String brc = null;
	private String complainType = null;
	private String priority = null;
	private String remarks = null;
	private String regidteredBy = null;
	private Date closedDate = null;
	


	public Date getLogDate() {
		return logDate;
	}



	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}



	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
	}



	public String getBrc() {
		return brc;
	}



	public void setBrc(String brc) {
		this.brc = brc;
	}



	public String getComplainType() {
		return complainType;
	}



	public void setComplainType(String complainType) {
		this.complainType = complainType;
	}



	public String getPriority() {
		return priority;
	}



	public void setPriority(String priority) {
		this.priority = priority;
	}



	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	public String getRegidteredBy() {
		return regidteredBy;
	}



	public void setRegidteredBy(String regidteredBy) {
		this.regidteredBy = regidteredBy;
	}



	public Date getClosedDate() {
		return closedDate;
	}



	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
	

}
