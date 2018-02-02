package com.amg.exchange.telemarketing.bean;

import java.math.BigDecimal;
import java.util.Date;

public class TelemarketingBranchWiseTable {
	
	private BigDecimal telmartBranchwiseTableId;
	private BigDecimal customerRefNum;
	private BigDecimal cusId;
	private String customerName;
	private String mobileNum;
	private BigDecimal nationality;
	private String nationalityName;
	private Date lastTransDate;
	private boolean selectedRecords;
	
	
	
	//Getters and Setters.	
	public String getCustomerName() {
		return customerName;
	}
	public BigDecimal getCusId() {
		return cusId;
	}
	
	public void setCusId(BigDecimal cusId) {
		this.cusId = cusId;
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
	
	public BigDecimal getNationality() {
		return nationality;
	}
	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}
	
	public Date getLastTransDate() {
		return lastTransDate;
	}
	public void setLastTransDate(Date lastTransDate) {
		this.lastTransDate = lastTransDate;
	}
	
	public BigDecimal getTelmartBranchwiseTableId() {
		return telmartBranchwiseTableId;
	}
	public void setTelmartBranchwiseTableId(BigDecimal telmartBranchwiseTableId) {
		this.telmartBranchwiseTableId = telmartBranchwiseTableId;
	}
	
	public BigDecimal getCustomerRefNum() {
		return customerRefNum;
	}
	public void setCustomerRefNum(BigDecimal customerRefNum) {
		this.customerRefNum = customerRefNum;
	}
	
	public String getNationalityName() {
		return nationalityName;
	}
	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}
	
	public boolean isSelectedRecords() {
		return selectedRecords;
	}
	public void setSelectedRecords(boolean selectedRecords) {
		this.selectedRecords = selectedRecords;
	}
		
}
