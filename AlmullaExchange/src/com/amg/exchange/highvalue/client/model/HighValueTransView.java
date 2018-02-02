package com.amg.exchange.highvalue.client.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_HIGH_VALUE_TRANS_VIEW")
public class HighValueTransView implements Serializable {

	  /**
	 * 
	 */
	  private static final long serialVersionUID = 1L;
	  private BigDecimal customerId;
	  private BigDecimal customerReference;
	  private BigDecimal locationId;
	  private String locationCode;
	  private BigDecimal totalTransaction;
	  private BigDecimal totalRemittance;
	  private Date logDate;
	  private Date visitDate;
	  private String visitBy;
	  private Date followUpDate;
	  private String followUpBy;
	  private Date profileDate;
	  private String profileBy;
	  private String conclusionRemarks;
	  private String employeeNo;
	  private String customerName;
	  private BigDecimal mobileNumber;

	  public HighValueTransView() {

	  }

	  public HighValueTransView(BigDecimal customerId, BigDecimal customerReference, BigDecimal locationId, String locationCode, BigDecimal totalTransaction, BigDecimal totalRemittance, Date logDate, Date visitDate, String visitBy, Date followUpDate, String followUpBy, Date profileDate,
			      String profileBy, String conclusionRemarks, String employeeNo, String customerName, BigDecimal mobileNumber) { 
		    super();
		    this.customerId = customerId;
		    this.customerReference = customerReference;
		    this.locationId = locationId;
		    this.locationCode = locationCode;
		    this.totalTransaction = totalTransaction;
		    this.totalRemittance = totalRemittance;
		    this.logDate = logDate;
		    this.visitDate = visitDate;
		    this.visitBy = visitBy;
		    this.followUpDate = followUpDate;
		    this.followUpBy = followUpBy;
		    this.profileDate = profileDate;
		    this.profileBy = profileBy;
		    this.conclusionRemarks = conclusionRemarks;
		    this.employeeNo = employeeNo;
		    this.customerName = customerName;
		    this.mobileNumber = mobileNumber;
	  }

	
	  @Column(name = "CUSTOMER_ID")
	  public BigDecimal getCustomerId() {
		    return customerId;
	  }

	  public void setCustomerId(BigDecimal customerId) {
		    this.customerId = customerId;
	  }
	  @Id
	  @Column(name = "CUSTOMER_REFERENCE")
	  public BigDecimal getCustomerReference() {
		    return customerReference;
	  }

	  public void setCustomerReference(BigDecimal customerReference) {
		    this.customerReference = customerReference;
	  }

	  @Column(name = "LOCATION_ID")
	  public BigDecimal getLocationId() {
		    return locationId;
	  }

	  public void setLocationId(BigDecimal locationId) {
		    this.locationId = locationId;
	  }

	  @Column(name = "LOCATION_CODE")
	  public String getLocationCode() {
		    return locationCode;
	  }

	  public void setLocationCode(String locationCode) {
		    this.locationCode = locationCode;
	  }

	  @Column(name = "TOTAL_TRANSACTION")
	  public BigDecimal getTotalTransaction() {
		    return totalTransaction;
	  }

	  public void setTotalTransaction(BigDecimal totalTransaction) {
		    this.totalTransaction = totalTransaction;
	  }

	  @Column(name = "TOTAL_REMITTANCE")
	  public BigDecimal getTotalRemittance() {
		    return totalRemittance;
	  }

	  public void setTotalRemittance(BigDecimal totalRemittance) {
		    this.totalRemittance = totalRemittance;
	  }

	  @Column(name = "LOG_DATE")
	  public Date getLogDate() {
		    return logDate;
	  }

	  public void setLogDate(Date logDate) {
		    this.logDate = logDate;
	  }

	  @Column(name = "VISIT_DATE")
	  public Date getVisitDate() {
		    return visitDate;
	  }

	  public void setVisitDate(Date visitDate) {
		    this.visitDate = visitDate;
	  }

	  @Column(name = "VISIT_BY")
	  public String getVisitBy() {
		    return visitBy;
	  }

	  public void setVisitBy(String visitBy) {
		    this.visitBy = visitBy;
	  }

	  @Column(name = "FOLLOW_UP_DATE")
	  public Date getFollowUpDate() {
		    return followUpDate;
	  }

	  public void setFollowUpDate(Date followUpDate) {
		    this.followUpDate = followUpDate;
	  }

	  @Column(name = "FOLLOW_UP_BY")
	  public String getFollowUpBy() {
		    return followUpBy;
	  }

	  public void setFollowUpBy(String followUpBy) {
		    this.followUpBy = followUpBy;
	  }

	  @Column(name = "PROFILE_DATE")
	  public Date getProfileDate() {
		    return profileDate;
	  }

	  public void setProfileDate(Date profileDate) {
		    this.profileDate = profileDate;
	  }

	  @Column(name = "PROFILE_BY")
	  public String getProfileBy() {
		    return profileBy;
	  }

	  public void setProfileBy(String profileBy) {
		    this.profileBy = profileBy;
	  }

	  @Column(name = "CONCLUSION_REMARKS")
	  public String getConclusionRemarks() {
		    return conclusionRemarks;
	  }

	  public void setConclusionRemarks(String conclusionRemarks) {
		    this.conclusionRemarks = conclusionRemarks;
	  }

	  @Column(name = "EMPLOYEE_NO")
	  public String getEmployeeNo() {
		    return employeeNo;
	  }

	  public void setEmployeeNo(String employeeNo) {
		    this.employeeNo = employeeNo;
	  }

	  @Column(name = "FIRST_NAME")
	  public String getCustomerName() {
		    return customerName;
	  }

	  public void setCustomerName(String customerName) {
		    this.customerName = customerName;
	  }

	  @Column(name = "MOBILE")
	  public BigDecimal getMobileNumber() {
		    return mobileNumber;
	  }

	  public void setMobileNumber(BigDecimal mobileNumber) {
		    this.mobileNumber = mobileNumber;
	  }

}
