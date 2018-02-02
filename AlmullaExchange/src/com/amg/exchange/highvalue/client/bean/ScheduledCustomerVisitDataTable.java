package com.amg.exchange.highvalue.client.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.amg.exchange.util.Constants;

public class ScheduledCustomerVisitDataTable {

	  private BigDecimal locationId;
	  private String locationName;
	  private Date logDate;
	  private BigDecimal customerReferenceNo;
	  private String customerName;
	  private BigDecimal mobileNumber;
	  private Date visitDate;
	  private String visitBy;
	  private Date minDate = new Date();
	  private Date effectiveMaxDate;
	  private Boolean booRead = false;
	  private Boolean selectCheckBox = false;
	  private Date formDate;
	  private Date toDate;
	  private String help;
	  // customer information related variables
	  private BigDecimal customerId;
	  private BigDecimal totalTrancation;
	  private BigDecimal totalRemittance;
	  private String followupBy;
	  private Date followUpDate;
	  private String profileUpBy;
	  private Date profileDate;
	  private String conclusionRemarks;
	  private String employeeNo;
	  private String firstNameLocal;
	  private String secondNameLocal;
	  private String thirdNameLocal;
	  private String gender;
	  private Date dateOfBrith;
	  private BigDecimal nationality;
	  private String alternateEmail;
	  private String email;
	  private String placeOfBrith;
	  private String countryOfBrith;
	  private String fatherName;
	  private BigDecimal contactNumber;
	  private String nationlityName;

	  public BigDecimal getLocationId() {
		    return locationId;
	  }

	  public void setLocationId(BigDecimal locationId) {
		    this.locationId = locationId;
	  }

	  public String getLocationName() {
		    return locationName;
	  }

	  public void setLocationName(String locationName) {
		    this.locationName = locationName;
	  }

	  public Date getLogDate() {
		    return logDate;
	  }

	  public void setLogDate(Date logDate) {
		    this.logDate = logDate;
	  }

	  public BigDecimal getCustomerReferenceNo() {
		    return customerReferenceNo;
	  }

	  public void setCustomerReferenceNo(BigDecimal customerReferenceNo) {
		    this.customerReferenceNo = customerReferenceNo;
	  }

	  public String getCustomerName() {
		    return customerName;
	  }

	  public void setCustomerName(String customerName) {
		    this.customerName = customerName;
	  }

	  public BigDecimal getMobileNumber() {
		    return mobileNumber;
	  }

	  public void setMobileNumber(BigDecimal mobileNumber) {
		    this.mobileNumber = mobileNumber;
	  }

	  public Date getVisitDate() {
		    return visitDate;
	  }

	  public void setVisitDate(Date visitDate) {
		    this.visitDate = visitDate;
	  }

	  public String getVisitBy() {
		    return visitBy;
	  }

	  public void setVisitBy(String visitBy) {
		    this.visitBy = visitBy;
	  }

	  public Date getMinDate() {
		    return minDate;
	  }

	  public void setMinDate(Date minDate) {
		    this.minDate = minDate;
	  }

	  public Date getEffectiveMaxDate() {
		    return new Date();
	  }

	  public void setEffectiveMaxDate(Date effectiveMaxDate) {
		    this.effectiveMaxDate = effectiveMaxDate;
	  }

	  public Boolean getBooRead() {
		    return booRead;
	  }

	  public void setBooRead(Boolean booRead) {
		    this.booRead = booRead;
	  }

	  public Boolean getSelectCheckBox() {
		    return selectCheckBox;
	  }

	  public void setSelectCheckBox(Boolean selectCheckBox) {
		    this.selectCheckBox = selectCheckBox;
	  }

	  public Date getFormDate() {
		    return formDate;
	  }

	  public void setFormDate(Date formDate) {
		    this.formDate = formDate;
	  }

	  public Date getToDate() {
		    return toDate;
	  }

	  public void setToDate(Date toDate) {
		    this.toDate = toDate;
	  }

	  public String getHelp() {
		    return help;
	  }

	  public void setHelp(String help) {
		    this.help = help;
	  }

	  public BigDecimal getCustomerId() {
		    return customerId;
	  }

	  public void setCustomerId(BigDecimal customerId) {
		    this.customerId = customerId;
	  }

	  public BigDecimal getTotalTrancation() {
		    return totalTrancation;
	  }

	  public void setTotalTrancation(BigDecimal totalTrancation) {
		    this.totalTrancation = totalTrancation;
	  }

	  public BigDecimal getTotalRemittance() {
		    return totalRemittance;
	  }

	  public void setTotalRemittance(BigDecimal totalRemittance) {
		    this.totalRemittance = totalRemittance;
	  }

	  public String getFollowupBy() {
		    return followupBy;
	  }

	  public void setFollowupBy(String followupBy) {
		    this.followupBy = followupBy;
	  }

	  public Date getFollowUpDate() {
		    return followUpDate;
	  }

	  public void setFollowUpDate(Date followUpDate) {
		    this.followUpDate = followUpDate;
	  }

	  public String getProfileUpBy() {
		    return profileUpBy;
	  }

	  public void setProfileUpBy(String profileUpBy) {
		    this.profileUpBy = profileUpBy;
	  }

	  public Date getProfileDate() {
		    return profileDate;
	  }

	  public void setProfileDate(Date profileDate) {
		    this.profileDate = profileDate;
	  }

	  public String getConclusionRemarks() {
		    return conclusionRemarks;
	  }

	  public void setConclusionRemarks(String conclusionRemarks) {
		    this.conclusionRemarks = conclusionRemarks;
	  }

	  public String getEmployeeNo() {
		    return employeeNo;
	  }

	  public void setEmployeeNo(String employeeNo) {
		    this.employeeNo = employeeNo;
	  }

	  public String getFirstNameLocal() {
		    return firstNameLocal;
	  }

	  public void setFirstNameLocal(String firstNameLocal) {
		    this.firstNameLocal = firstNameLocal;
	  }

	  public String getSecondNameLocal() {
		    return secondNameLocal;
	  }

	  public void setSecondNameLocal(String secondNameLocal) {
		    this.secondNameLocal = secondNameLocal;
	  }

	  public String getThirdNameLocal() {
		    return thirdNameLocal;
	  }

	  public void setThirdNameLocal(String thirdNameLocal) {
		    this.thirdNameLocal = thirdNameLocal;
	  }

	  public String getGender() {
		    return gender;
	  }

	  public void setGender(String gender) {
		    this.gender = gender;
	  }

	  public Date getDateOfBrith() {
		    return dateOfBrith;
	  }

	  public void setDateOfBrith(Date dateOfBrith) {
		    this.dateOfBrith = dateOfBrith;
	  }

	  public BigDecimal getNationality() {
		    return nationality;
	  }

	  public void setNationality(BigDecimal nationality) {
		    this.nationality = nationality;
	  }

	  public String getAlternateEmail() {
		    return alternateEmail;
	  }

	  public void setAlternateEmail(String alternateEmail) {
		    this.alternateEmail = alternateEmail;
	  }

	  public String getEmail() {
		    return email;
	  }

	  public void setEmail(String email) {
		    this.email = email;
	  }

	  public String getPlaceOfBrith() {
		    return placeOfBrith;
	  }

	  public void setPlaceOfBrith(String placeOfBrith) {
		    this.placeOfBrith = placeOfBrith;
	  }

	  public String getCountryOfBrith() {
		    return countryOfBrith;
	  }

	  public void setCountryOfBrith(String countryOfBrith) {
		    this.countryOfBrith = countryOfBrith;
	  }

	  public String getFatherName() {
		    return fatherName;
	  }

	  public void setFatherName(String fatherName) {
		    this.fatherName = fatherName;
	  }

	  public BigDecimal getContactNumber() {
		    return contactNumber;
	  }

	  public void setContactNumber(BigDecimal contactNumber) {
		    this.contactNumber = contactNumber;
	  }

	  public String getNationlityName() {
	  	  return nationlityName;
	  }

	  public void setNationlityName(String nationlityName) {
	  	  this.nationlityName = nationlityName;
	  }

	  
}
