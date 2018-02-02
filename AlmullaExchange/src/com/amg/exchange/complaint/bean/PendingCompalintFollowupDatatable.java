package com.amg.exchange.complaint.bean;

import java.util.Date;

public class PendingCompalintFollowupDatatable {
	
	private Date date = null;
	private String action = null;
	private String remarks = null;
	private String followupUser = null;
	private String commMethod = null;
	private String assignTo = null;

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFollowupUser() {
		return followupUser;
	}
	public void setFollowupUser(String followupUser) {
		this.followupUser = followupUser;
	}
	public String getCommMethod() {
		return commMethod;
	}
	public void setCommMethod(String commMethod) {
		this.commMethod = commMethod;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	
	

}
