package com.amg.exchange.highvalue.client.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;


@Entity
@Table(name = "EX_HIGH_VALUE_CLIENT")
public class HighValueClient implements Serializable {

	  private static final long serialVersionUID = 1L;

	  private BigDecimal highValueClientId;
	  // private BigDecimal applicationCountryId;
	  private CountryMaster applicationCountryId;
	  // private BigDecimal locationId;
	  private CountryBranch locationId;
	  private String locationCode;
	  // private BigDecimal customerId;
	  private BigDecimal customerId;
	  private BigDecimal customerReferenceNo;
	  private String customerCompProfile;
	  private String conclusionRemarks;
	  private Date visitDate;
	  private String VisitBy;
	  private BigDecimal totalTransaction;
	  private BigDecimal totalRemittance;
	  private Date profileDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;
	  private String profileBy;
	  private BigDecimal professionCode;
	  private Date logDate;
	  private Date followUpdate;
	  private String followUpBy;
	  private Date expiryDate;
	  private String employeeNo;
	  private String createdBy;
	  private Date createdDate;

	  public HighValueClient() {
		    super();
	  }

	 
	  public HighValueClient(BigDecimal highValueClientId, CountryMaster applicationCountryId, CountryBranch locationId, String locationCode, BigDecimal customerId, BigDecimal customerReferenceNo, String customerCompProfile, String conclusionRemarks, Date visitDate, String visitBy,
			      BigDecimal totalTransaction, BigDecimal totalRemittance, Date profileDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate, String remarks, String profileBy, BigDecimal professionCode, Date logDate, Date followUpdate, String followUpBy,
			      Date expiryDate, String employeeNo, String createdBy, Date createdDate) {
		    super();
		    this.highValueClientId = highValueClientId;
		    this.applicationCountryId = applicationCountryId;
		    this.locationId = locationId;
		    this.locationCode = locationCode;
		    this.customerId = customerId;
		    this.customerReferenceNo = customerReferenceNo;
		    this.customerCompProfile = customerCompProfile;
		    this.conclusionRemarks = conclusionRemarks;
		    this.visitDate = visitDate;
		    VisitBy = visitBy;
		    this.totalTransaction = totalTransaction;
		    this.totalRemittance = totalRemittance;
		    this.profileDate = profileDate;
		    this.modifiedBy = modifiedBy;
		    this.modifiedDate = modifiedDate;
		    this.approvedBy = approvedBy;
		    this.approvedDate = approvedDate;
		    this.remarks = remarks;
		    this.profileBy = profileBy;
		    this.professionCode = professionCode;
		    this.logDate = logDate;
		    this.followUpdate = followUpdate;
		    this.followUpBy = followUpBy;
		    this.expiryDate = expiryDate;
		    this.employeeNo = employeeNo;
		    this.createdBy = createdBy;
		    this.createdDate = createdDate;
	  }


	  @Id
	  @GeneratedValue(generator = "ex_high_value_client_seq", strategy = GenerationType.SEQUENCE)
	  @SequenceGenerator(name = "ex_high_value_client_seq", sequenceName = "EX_HIGH_VALUE_CLIENT_SEQ", allocationSize = 1)
	  @Column(name = "HIGH_VALUE_CLIENT_ID", nullable = false)
	  public BigDecimal getHighValueClientId() {
		    return highValueClientId;
	  }

	  public void setHighValueClientId(BigDecimal highValueClientId) {
		    this.highValueClientId = highValueClientId;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "APPLICATION_COUNTRY_ID")
	  public CountryMaster getApplicationCountryId() {
		    return applicationCountryId;
	  }

	  public void setApplicationCountryId(CountryMaster applicationCountryId) {
		    this.applicationCountryId = applicationCountryId;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "LOCATION_ID")
	  public CountryBranch getLocationId() {
		    return locationId;
	  }

	  public void setLocationId(CountryBranch locationId) {
		    this.locationId = locationId;
	  }

	  @Column(name = "LOCATION_CODE")
	  public String getLocationCode() {
		    return locationCode;
	  }

	  public void setLocationCode(String locationCode) {
		    this.locationCode = locationCode;
	  }

	  @Column(name = "CUSTOMER_ID")
	  public BigDecimal getCustomerId() {
		    return customerId;
	  }

	  public void setCustomerId(BigDecimal customerId) {
		    this.customerId = customerId;
	  }

	  @Column(name = "CUSTOMER_REFERENCE")
	  public BigDecimal getCustomerReferenceNo() {
		    return customerReferenceNo;
	  }

	  public void setCustomerReferenceNo(BigDecimal customerReferenceNo) {
		    this.customerReferenceNo = customerReferenceNo;
	  }

	  @Column(name = "CUSTOMER_COMP_PROFILE")
	  public String getCustomerCompProfile() {
		    return customerCompProfile;
	  }

	  public void setCustomerCompProfile(String customerCompProfile) {
		    this.customerCompProfile = customerCompProfile;
	  }

	  @Column(name = "CONCLUSION_REMARKS")
	  public String getConclusionRemarks() {
		    return conclusionRemarks;
	  }

	  public void setConclusionRemarks(String conclusionRemarks) {
		    this.conclusionRemarks = conclusionRemarks;
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
		    return VisitBy;
	  }

	  public void setVisitBy(String visitBy) {
		    VisitBy = visitBy;
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

	  @Column(name = "PROFILE_DATE")
	  public Date getProfileDate() {
		    return profileDate;
	  }

	  public void setProfileDate(Date profileDate) {
		    this.profileDate = profileDate;
	  }

	  @Column(name = "MODIFIED_BY")
	  public String getModifiedBy() {
		    return modifiedBy;
	  }

	  public void setModifiedBy(String modifiedBy) {
		    this.modifiedBy = modifiedBy;
	  }

	  @Column(name = "MODIFIED_DATE")
	  public Date getModifiedDate() {
		    return modifiedDate;
	  }

	  public void setModifiedDate(Date modifiedDate) {
		    this.modifiedDate = modifiedDate;
	  }

	  @Column(name = "APPROVED_BY")
	  public String getApprovedBy() {
		    return approvedBy;
	  }

	  public void setApprovedBy(String approvedBy) {
		    this.approvedBy = approvedBy;
	  }

	  @Column(name = "APPROVED_DATE")
	  public Date getApprovedDate() {
		    return approvedDate;
	  }

	  public void setApprovedDate(Date approvedDate) {
		    this.approvedDate = approvedDate;
	  }

	  @Column(name = "REMARKS")
	  public String getRemarks() {
		    return remarks;
	  }

	  public void setRemarks(String remarks) {
		    this.remarks = remarks;
	  }

	  @Column(name = "PROFILE_BY")
	  public String getProfileBy() {
		    return profileBy;
	  }

	  public void setProfileBy(String profileBy) {
		    this.profileBy = profileBy;
	  }

	  @Column(name = "OCCUPATION_ID")
	  public BigDecimal getProfessionCode() {
		    return professionCode;
	  }

	  public void setProfessionCode(BigDecimal professionCode) {
		    this.professionCode = professionCode;
	  }

	  @Column(name = "LOG_DATE")
	  public Date getLogDate() {
		    return logDate;
	  }

	  public void setLogDate(Date logDate) {
		    this.logDate = logDate;
	  }

	  @Column(name = "FOLLOW_UP_DATE")
	  public Date getFollowUpdate() {
		    return followUpdate;
	  }

	  public void setFollowUpdate(Date followUpdate) {
		    this.followUpdate = followUpdate;
	  }

	  @Column(name = "FOLLOW_UP_BY")
	  public String getFollowUpBy() {
		    return followUpBy;
	  }

	  public void setFollowUpBy(String followUpBy) {
		    this.followUpBy = followUpBy;
	  }

	  @Column(name = "EXPIRY_DATE")
	  public Date getExpiryDate() {
		    return expiryDate;
	  }

	  public void setExpiryDate(Date expiryDate) {
		    this.expiryDate = expiryDate;
	  }

	  @Column(name = "EMPLOYEE_NO")
	  public String getEmployeeNo() {
		    return employeeNo;
	  }

	  public void setEmployeeNo(String employeeNo) {
		    this.employeeNo = employeeNo;
	  }

	  @Column(name = "CREATED_BY")
	  public String getCreatedBy() {
		    return createdBy;
	  }

	  public void setCreatedBy(String createdBy) {
		    this.createdBy = createdBy;
	  }

	  @Column(name = "CREATED_DATE")
	  public Date getCreatedDate() {
		    return createdDate;
	  }

	  public void setCreatedDate(Date createdDate) {
		    this.createdDate = createdDate;
	  }

}
