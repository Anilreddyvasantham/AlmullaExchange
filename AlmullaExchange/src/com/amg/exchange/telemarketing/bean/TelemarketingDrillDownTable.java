package com.amg.exchange.telemarketing.bean;

import java.math.BigDecimal;
import java.util.Date;

public class TelemarketingDrillDownTable {
	
	private String customerName;
	private BigDecimal cusId;
	private String mobileNum;
	private Date lastFollowUpDate;
	private String lastFollowUpCode;
	private String remarks;
	private String employeeName;
	private Date allocatedDate;
	private BigDecimal customerRefNum;
	private Date nextFollowUpDate;
	
	
	//Getters and setters.
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
	public Date getLastFollowUpDate() {
		return lastFollowUpDate;
	}
	public void setLastFollowUpDate(Date lastFollowUpDate) {
		this.lastFollowUpDate = lastFollowUpDate;
	}
	
	public String getLastFollowUpCode() {
		return lastFollowUpCode;
	}
	public void setLastFollowUpCode(String lastFollowUpCode) {
		this.lastFollowUpCode = lastFollowUpCode;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public Date getAllocatedDate() {
		return allocatedDate;
	}
	public void setAllocatedDate(Date allocatedDate) {
		this.allocatedDate = allocatedDate;
	}
	
	public BigDecimal getCusId() {
		return cusId;
	}
	public void setCusId(BigDecimal cusId) {
		this.cusId = cusId;
	}
	
	public BigDecimal getCustomerRefNum() {
		return customerRefNum;
	}
	public void setCustomerRefNum(BigDecimal customerRefNum) {
		this.customerRefNum = customerRefNum;
	}	
	
	public Date getNextFollowUpDate() {
		return nextFollowUpDate;
	}
	public void setNextFollowUpDate(Date nextFollowUpDate) {
		this.nextFollowUpDate = nextFollowUpDate;
	}	
	

}
