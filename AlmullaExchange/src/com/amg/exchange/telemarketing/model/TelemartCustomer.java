package com.amg.exchange.telemarketing.model;

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
@Table(name = "EX_TELEMART_CUSTOMER")
public class TelemartCustomer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;	
	
	private BigDecimal telemartCustomerId;	
	private BigDecimal applicationCountryId;	
	private BigDecimal customerId;	
	private BigDecimal countryBranchId;	
	private BigDecimal employeeId;	
	private Date allocatedDate;	
	private Date folwUpDate;	
	private String isActive;	
	private String remarks;	
	private Date createdDate;	
	private String createdBy;	
	private Date modifiedDate;	
	private String modifiedBy;	
	private String folwUpCode;
	private Date nextFolwUpDate;
	
	public TelemartCustomer() {
		super();
	}

	public TelemartCustomer(BigDecimal telemartCustomerId,
			BigDecimal applicationCountryId, BigDecimal customerId,
			BigDecimal countryBranchId, BigDecimal employeeId,
			Date allocatedDate, Date folwUpDate, String isActive,
			String remarks, Date createdDate, String createdBy,
			Date modifiedDate, String modifiedBy, String folwUpCode,
			Date nextFolwUpDate) {
		super();
		this.telemartCustomerId = telemartCustomerId;
		this.applicationCountryId = applicationCountryId;
		this.customerId = customerId;
		this.countryBranchId = countryBranchId;
		this.employeeId = employeeId;
		this.allocatedDate = allocatedDate;
		this.folwUpDate = folwUpDate;
		this.isActive = isActive;
		this.remarks = remarks;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.folwUpCode = folwUpCode;
		this.nextFolwUpDate = nextFolwUpDate;
	}

	// Getters and setters.
	
	@Id
	@GeneratedValue(generator="ex_telemart_customer_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_telemart_customer_seq" ,sequenceName="EX_TELEMART_CUSTOMER_SEQ",allocationSize=1)
	@Column(name = "TELEMART_CUSTOMER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getTelemartCustomerId() {
		return telemartCustomerId;
	}
	public void setTelemartCustomerId(BigDecimal telemartCustomerId) {
		this.telemartCustomerId = telemartCustomerId;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@Column(name = "EMPLOYEE_ID")
	public BigDecimal getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "ALLOCATED_DATE")
	public Date getAllocatedDate() {
		return allocatedDate;
	}
	public void setAllocatedDate(Date allocatedDate) {
		this.allocatedDate = allocatedDate;
	}

	@Column(name = "FOLW_UP_DATE")
	public Date getFolwUpDate() {
		return folwUpDate;
	}
	public void setFolwUpDate(Date folwUpDate) {
		this.folwUpDate = folwUpDate;
	}

	@Column(name = "ISACTIVE")
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

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "FOLW_UP_CODE")
	public String getFolwUpCode() {
		return folwUpCode;
	}
	public void setFolwUpCode(String folwUpCode) {
		this.folwUpCode = folwUpCode;
	}	

	@Column(name = "NEXT_FOLW_UP_DATE")
	public Date getNextFolwUpDate() {
		return nextFolwUpDate;
	}
	public void setNextFolwUpDate(Date nextFolwUpDate) {
		this.nextFolwUpDate = nextFolwUpDate;
	}

}
