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
@Table(name = "EX_TELEMART_FOLWUP")
public class TelemartFolwUp implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal telemartFolwUpId;
	private BigDecimal customerId;
	private BigDecimal employeeId;
	private Date folwUpDate;
	private String isActive;
	private Date nextFolwUpDate;
	private String remarks;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private BigDecimal applicationCountryId;
	private String folwUpCode;
	private BigDecimal teleMartCustomerId;
	
	public TelemartFolwUp() {
		super();
	}
	
	public TelemartFolwUp(BigDecimal telemartFolwUpId, BigDecimal customerId,
			BigDecimal employeeId, Date folwUpDate, String isActive,
			Date nextFolwUpDate, String remarks, String createdBy,
			Date modifiedDate, String modifiedBy,
			BigDecimal applicationCountryId, String folwUpCode,
			BigDecimal teleMartCustomerId) {
		super();
		this.telemartFolwUpId = telemartFolwUpId;
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.folwUpDate = folwUpDate;
		this.isActive = isActive;
		this.nextFolwUpDate = nextFolwUpDate;
		this.remarks = remarks;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.applicationCountryId = applicationCountryId;
		this.folwUpCode = folwUpCode;
		this.teleMartCustomerId = teleMartCustomerId;
	}

	//Getters and setters.
	@Id
	@GeneratedValue(generator="ex_telemart_folwup_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_telemart_folwup_seq" ,sequenceName="EX_TELEMART_FOLWUP_SEQ",allocationSize=1)
	@Column(name = "TELEMART_FOLWUP_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getTelemartFolwUpId() {
		return telemartFolwUpId;
	}
	public void setTelemartFolwUpId(BigDecimal telemartFolwUpId) {
		this.telemartFolwUpId = telemartFolwUpId;
	}
	
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "EMPLOYEE_ID")
	public BigDecimal getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
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
	
	@Column(name = "NEXT_FOLW_UP_DATE")
	public Date getNextFolwUpDate() {
		return nextFolwUpDate;
	}
	public void setNextFolwUpDate(Date nextFolwUpDate) {
		this.nextFolwUpDate = nextFolwUpDate;
	}
	
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "FOLW_UP_CODE")
	public String getFolwUpCode() {
		return folwUpCode;
	}
	public void setFolwUpCode(String folwUpCode) {
		this.folwUpCode = folwUpCode;
	}
	
	@Column(name = "TELEMART_CUSTOMER_ID")
	public BigDecimal getTeleMartCustomerId() {
		return teleMartCustomerId;
	}
	public void setTeleMartCustomerId(BigDecimal teleMartCustomerId) {
		this.teleMartCustomerId = teleMartCustomerId;
	}
	
}
