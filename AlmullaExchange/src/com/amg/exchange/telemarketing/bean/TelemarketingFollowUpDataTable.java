package com.amg.exchange.telemarketing.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TelemarketingFollowUpDataTable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal telmartFollowupTableId;
	private BigDecimal cusId;
	private BigDecimal empId;
	private BigDecimal appCountryId;
	private String customerName;
	private String mobileNum;
	private Date lastFollowUpDate;
	private String lastFollowUp;
	private String remarks;
	private BigDecimal teleMartCustomerId;
	private boolean selectedRecords;
	private Date nextFollowUpDate;
	private BigDecimal customerRefNum;
	
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
	
	public String getLastFollowUp() {
		return lastFollowUp;
	}
	public void setLastFollowUp(String lastFollowUp) {
		this.lastFollowUp = lastFollowUp;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public BigDecimal getCusId() {
		return cusId;
	}
	public void setCusId(BigDecimal cusId) {
		this.cusId = cusId;
	}
	
	public BigDecimal getTelmartFollowupTableId() {
		return telmartFollowupTableId;
	}
	public void setTelmartFollowupTableId(BigDecimal telmartFollowupTableId) {
		this.telmartFollowupTableId = telmartFollowupTableId;
	}
	
	public BigDecimal getEmpId() {
		return empId;
	}
	public void setEmpId(BigDecimal empId) {
		this.empId = empId;
	}
	
	public BigDecimal getAppCountryId() {
		return appCountryId;
	}
	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}
	
	public BigDecimal getTeleMartCustomerId() {
		return teleMartCustomerId;
	}
	public void setTeleMartCustomerId(BigDecimal teleMartCustomerId) {
		this.teleMartCustomerId = teleMartCustomerId;
	}
	
	public boolean isSelectedRecords() {
		return selectedRecords;
	}
	public void setSelectedRecords(boolean selectedRecords) {
		this.selectedRecords = selectedRecords;
	}
	
	public Date getNextFollowUpDate() {
		return nextFollowUpDate;
	}
	public void setNextFollowUpDate(Date nextFollowUpDate) {
		this.nextFollowUpDate = nextFollowUpDate;
	}
	
	public BigDecimal getCustomerRefNum() {
		return customerRefNum;
	}
	public void setCustomerRefNum(BigDecimal customerRefNum) {
		this.customerRefNum = customerRefNum;
	}
	
	
}
