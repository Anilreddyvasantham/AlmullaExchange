package com.amg.exchange.registration.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SystemAllocationDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal branchSystemInventoryId;
	private BigDecimal branchId;
	private String branchName;
	private String ipaddress;
	private String systemNumber;
	private String system;
	private String userName;
	private String status;
	private String remarks;
	private String createdBy;
	private Date createdDate;
	
	private BigDecimal employeeSystemsAssignId;
	private BigDecimal employeeId;
	
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	public String getSystemNumber() {
		return systemNumber;
	}
	public void setSystemNumber(String systemNumber) {
		this.systemNumber = systemNumber;
	}
	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigDecimal getBranchSystemInventoryId() {
		return branchSystemInventoryId;
	}
	public void setBranchSystemInventoryId(BigDecimal branchSystemInventoryId) {
		this.branchSystemInventoryId = branchSystemInventoryId;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	
	public BigDecimal getEmployeeSystemsAssignId() {
		return employeeSystemsAssignId;
	}
	public void setEmployeeSystemsAssignId(BigDecimal employeeSystemsAssignId) {
		this.employeeSystemsAssignId = employeeSystemsAssignId;
	}
	
	public BigDecimal getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}
	
}
