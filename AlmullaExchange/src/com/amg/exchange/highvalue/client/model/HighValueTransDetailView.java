package com.amg.exchange.highvalue.client.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_HIGH_VALUE_TRANS_DETAIL_VIEW")
public class HighValueTransDetailView implements Serializable {

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
	private BigDecimal applicationCountryId;
	private String firstName;
	private String mobile;
	private String firstNameLocal;
	private String middleNameLocal;
	private String lastNameLocal;
	private String gender;
	private BigDecimal nationality;
	private String alterEmailId;
	private String email;
	private Date dateOfBirth; 
	private String placeOfBirth; 
	private String countryOfBirth;
	private String fatherName;
	private BigDecimal contactNumber;
	
	

	public HighValueTransDetailView() {

	}

	public HighValueTransDetailView(BigDecimal customerId,
			BigDecimal customerReference, BigDecimal locationId,
			String locationCode, BigDecimal totalTransaction,
			BigDecimal totalRemittance, Date logDate, Date visitDate,
			String visitBy, Date followUpDate, String followUpBy,
			Date profileDate, String profileBy, String conclusionRemarks,
			String employeeNo, String firstName, String mobile,
			BigDecimal applicationCountryId, String firstNameLocal,
			String middleNameLocal, String lastNameLocal, String gender,
			BigDecimal nationality, String alterEmailId, String email, 
			Date dateOfBirth,String placeOfBirth, String countryOfBirth, 
			String fatherName, BigDecimal contactNumber ) {
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
		this.firstName = firstName;
		this.mobile = mobile;
		this.applicationCountryId = applicationCountryId;
		this.firstNameLocal = firstNameLocal;
		this.middleNameLocal=middleNameLocal;
		this.lastNameLocal= lastNameLocal;
		this.gender= gender;
		this.nationality = nationality;
		this.alterEmailId = alterEmailId;
		this.email =email;
		this.placeOfBirth=placeOfBirth;
		this.dateOfBirth=dateOfBirth;
		this.fatherName=fatherName;
		this.countryOfBirth=countryOfBirth;
		this.contactNumber=contactNumber;
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

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "FIRST_NAME_LOCAL")
	public String getFirstNameLocal() {
		return firstNameLocal;
	}

	public void setFirstNameLocal(String firstNameLocal) {
		this.firstNameLocal = firstNameLocal;
	}
	@Column(name = "MIDDLE_NAME_LOCAL")
	public String getMiddleNameLocal() {
		return middleNameLocal;
	}

	public void setMiddleNameLocal(String middleNameLocal) {
		this.middleNameLocal = middleNameLocal;
	}
	@Column(name = "LAST_NAME_LOCAL")
	public String getLastNameLocal() {
		return lastNameLocal;
	}

	public void setLastNameLocal(String lastNameLocal) {
		this.lastNameLocal = lastNameLocal;
	}
	@Column(name = "GENDER")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	@Column(name = "NATIONALITY ")
	public BigDecimal getNationality() {
		return nationality;
	}

	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}
	@Column(name = "ALTER_EMAIL_ID")
	public String getAlterEmailId() {
		return alterEmailId;
	}

	public void setAlterEmailId(String alterEmailId) {
		this.alterEmailId = alterEmailId;
	}
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "DATE_OF_BIRTH")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	@Column(name = "PLACE_OF_BIRTH")
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	@Column(name = "COUNTRY_OF_BIRTH")
	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}
	@Column(name = "FATHER_NAME")
	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	@Column(name = "CONTACT_NUMBER ")
	public BigDecimal getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(BigDecimal contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	
	
	
	
}
