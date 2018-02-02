package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_BRANCH_SYSTEM_INVENTORY")
public class BranchSystemInventory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal branchSystemInventoryId;
	private BigDecimal countryBranchId;
	private String branchName;
	private String systemName;
	private String ipAddress;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String isActive;
	private String remarks;
	
	@Id
	@GeneratedValue(generator="ex_branch_system_inventory_seq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_branch_system_inventory_seq",sequenceName="EX_BRANCH_SYSTEM_INVENTORY_SEQ",allocationSize=1)
	@Column(name = "EX_BRANCH_SYSTEM_INVENTORY_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBranchSystemInventoryId() {
		return branchSystemInventoryId;
	}
	public void setBranchSystemInventoryId(BigDecimal branchSystemInventoryId) {
		this.branchSystemInventoryId = branchSystemInventoryId;
	}
	
	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	@Column(name = "SYSTEM_NAME")
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	@Column(name = "IP_ADDRESS")
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "CREATION_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "UPDATED_BY")
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Column(name = "UPDATED_DATE")
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@Column(name = "IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
