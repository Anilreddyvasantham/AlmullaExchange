package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Author Rahamathali Shaik
*/
@Entity
@Table(name= "VW_EX_CUSTOMER_INFO")
public class CustomerInfoView implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private String verificationToken;
	private BigDecimal customerReference;
	private String introducedBy;
	private Date lastTransactionDate;
	private String pepIndicator;
	private Date introducedDate;
	private String countryName;
	private String componentDescription;
	private String articalLevel;
	private String articalName;
	private String incomeRangeName;
	private BigDecimal customerId;
	private BigDecimal countryId;
	private BigDecimal companyId;
	private BigDecimal customerTypeId;
	private BigDecimal languageId;
	private String shortName;
	private String shortNameLocal;
	private BigDecimal groupId;
	private String amlStatus;
	private BigDecimal branchCode;
	private String activatedInd;
	private Date activatedDate;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String titleLocal;
	private String firstNameLocal;
	private String middleNameLocal;
	private String lastNameLocal;
	private String gender;
	private Date dateOfBirth;
	private BigDecimal nationality;
	private String alterEmailId;
	private String mobile;
	private String signature;
	private String fingerPrintImg;
	private String medicalInsurenceIndicator;
	private String companyName;
	private String companyNameLocal;
	private BigDecimal objectiveId;
	private String email;
	private String crNo;
	private String placeOfBirth;
	private String countryOfBirth;
	private String fatherName;
	private String createdBy;
	private Date creationDate;
	private String contactPerson;
	private BigDecimal contactNumber;
	private BigDecimal articlaDetailId;
	private String isActive;
	private BigDecimal incomeRange;
	private BigDecimal paidUpCapital;
	private BigDecimal dailyTranscationLimit;
	private BigDecimal weeklyTransactionLimit;
	private BigDecimal monthlyTransactionLimit;
	//private BigDecimal halfYearlyTransactionLimit;
	private BigDecimal annualTransactionLimit;
	//private BigDecimal quaterlyTransactionLimit;
	private String sponsorName;
	private String relationName;
	private String branchName;
	private BigDecimal auditGrossIncome;
	private Date auditStatementDate;
	private String objectiveDesc;
	private Clob signatureSpecimenClob;
	
	
/*	@Column(name = "HY_TXN_LIMIT")
	public BigDecimal getHalfYearlyTransactionLimit() {
		return halfYearlyTransactionLimit;
	}
	public void setHalfYearlyTransactionLimit(BigDecimal halfYearlyTransactionLimit) {
		this.halfYearlyTransactionLimit = halfYearlyTransactionLimit;
	}*/
	
	
	
	@Column(name = "ANNUAL_TXN_LIMIT")
	public BigDecimal getAnnualTransactionLimit() {
		return annualTransactionLimit;
	}
	@Column(name = "OBJECTIVEDESC")
	public String getObjectiveDesc() {
		return objectiveDesc;
	}
	public void setObjectiveDesc(String objectiveDesc) {
		this.objectiveDesc = objectiveDesc;
	}
	@Column(name = "AUDIT_GROSS_INCOME")
	public BigDecimal getAuditGrossIncome() {
		return auditGrossIncome;
	}
	@Column(name = "AUDIT_STATEMENT_DT")
	public Date getAuditStatementDate() {
		return auditStatementDate;
	}
	public void setAuditGrossIncome(BigDecimal auditGrossIncome) {
		this.auditGrossIncome = auditGrossIncome;
	}
	public void setAuditStatementDate(Date auditStatementDate) {
		this.auditStatementDate = auditStatementDate;
	}
	@Column(name = "BRANCHNAME")
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public void setAnnualTransactionLimit(BigDecimal annualTransactionLimit) {
		this.annualTransactionLimit = annualTransactionLimit;
	}
	@Column(name = "VERIFICATIONTOKEN")
	public String getVerificationToken() {
		return verificationToken;
	}
	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}
	@Column(name = "CUSTOMERREFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	@Column(name = "INTRDUCEDBY")
	public String getIntroducedBy() {
		return introducedBy;
	}
	public void setIntroducedBy(String introducedBy) {
		this.introducedBy = introducedBy;
	}
	@Column(name = "LASTTXNDATE")
	public Date getLastTransactionDate() {
		return lastTransactionDate;
	}
	public void setLastTransactionDate(Date lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}
	@Column(name = "PEPINDICATOR")
	public String getPepIndicator() {
		return pepIndicator;
	}
	public void setPepIndicator(String pepIndicator) {
		this.pepIndicator = pepIndicator;
	}
	@Column(name = "INTRODUCEDDATE")
	public Date getIntroducedDate() {
		return introducedDate;
	}
	public void setIntroducedDate(Date introducedDate) {
		this.introducedDate = introducedDate;
	}
	@Column(name = "COUNTRYNAME")
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	@Column(name = "COMPONENTDESC")
	public String getComponentDescription() {
		return componentDescription;
	}
	public void setComponentDescription(String componentDescription) {
		this.componentDescription = componentDescription;
	}
	@Column(name = "ARTICLELEVEL")
	public String getArticalLevel() {
		return articalLevel;
	}
	public void setArticalLevel(String articalLevel) {
		this.articalLevel = articalLevel;
	}
	@Column(name = "ARTICLENAME")
	public String getArticalName() {
		return articalName;
	}
	public void setArticalName(String articalName) {
		this.articalName = articalName;
	}
	@Column(name = "INCOMERANGENAME")
	public String getIncomeRangeName() {
		return incomeRangeName;
	}
	public void setIncomeRangeName(String incomeRangeName) {
		this.incomeRangeName = incomeRangeName;
	}
	@Id
	@Column(name = "CUSTOMERID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	@Column(name = "COUNTRYID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	@Column(name = "COMPANYID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	@Column(name = "CUSTOMERTYPEID")
	public BigDecimal getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(BigDecimal customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	@Column(name = "LANGUAGEID")
	public BigDecimal getLanguageId() {
		return languageId;
	}
	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}
	@Column(name = "SHORTNAME")
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Column(name = "SHORTNAMELOCAL")
	public String getShortNameLocal() {
		return shortNameLocal;
	}
	public void setShortNameLocal(String shortNameLocal) {
		this.shortNameLocal = shortNameLocal;
	}
	@Column(name = "GROUPID")
	public BigDecimal getGroupId() {
		return groupId;
	}
	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}
	@Column(name = "AMLSTATUS")
	public String getAmlStatus() {
		return amlStatus;
	}
	public void setAmlStatus(String amlStatus) {
		this.amlStatus = amlStatus;
	}
	@Column(name = "BRANCHCODE")
	public BigDecimal getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}
	@Column(name = "ACTIVATEDIND")
	public String getActivatedInd() {
		return activatedInd;
	}
	public void setActivatedInd(String activatedInd) {
		this.activatedInd = activatedInd;
	}
	@Column(name = "ACTIVATEDDATE")
	public Date getActivatedDate() {
		return activatedDate;
	}
	public void setActivatedDate(Date activatedDate) {
		this.activatedDate = activatedDate;
	}
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "FIRSTNAME")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name = "MIDDLENAME")
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	@Column(name = "LASTNAME")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column(name = "TITLELOCAL")
	public String getTitleLocal() {
		return titleLocal;
	}
	public void setTitleLocal(String titleLocal) {
		this.titleLocal = titleLocal;
	}
	@Column(name = "FIRSTNAMELOCAL")
	public String getFirstNameLocal() {
		return firstNameLocal;
	}
	public void setFirstNameLocal(String firstNameLocal) {
		this.firstNameLocal = firstNameLocal;
	}
	@Column(name = "MIDDLENAMELOCAL")
	public String getMiddleNameLocal() {
		return middleNameLocal;
	}
	public void setMiddleNameLocal(String middleNameLocal) {
		this.middleNameLocal = middleNameLocal;
	}
	@Column(name = "LASTNAMELOCAL")
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
	@Column(name = "DOB")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	@Column(name = "NATIONALITY")
	public BigDecimal getNationality() {
		return nationality;
	}
	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}
	@Column(name = "ALTERMAILID")
	public String getAlterEmailId() {
		return alterEmailId;
	}
	public void setAlterEmailId(String alterEmailId) {
		this.alterEmailId = alterEmailId;
	}
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name = "SIGNATURE")
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Column(name = "FINGERPRINT")
	public String getFingerPrintImg() {
		return fingerPrintImg;
	}
	public void setFingerPrintImg(String fingerPrintImg) {
		this.fingerPrintImg = fingerPrintImg;
	}
	@Column(name = "MEDICALINSURANCEIND")
	public String getMedicalInsurenceIndicator() {
		return medicalInsurenceIndicator;
	}
	public void setMedicalInsurenceIndicator(String medicalInsurenceIndicator) {
		this.medicalInsurenceIndicator = medicalInsurenceIndicator;
	}
	@Column(name = "COMPANYNAME")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(name = "COMPANYNAMELOCAL")
	public String getCompanyNameLocal() {
		return companyNameLocal;
	}
	public void setCompanyNameLocal(String companyNameLocal) {
		this.companyNameLocal = companyNameLocal;
	}
	@Column(name = "OBJECTTIVEID")
	public BigDecimal getObjectiveId() {
		return objectiveId;
	}
	public void setObjectiveId(BigDecimal objectiveId) {
		this.objectiveId = objectiveId;
	}
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "CRNO")
	public String getCrNo() {
		return crNo;
	}
	public void setCrNo(String crNo) {
		this.crNo = crNo;
	}
	@Column(name = "POB")
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	@Column(name = "COB")
	public String getCountryOfBirth() {
		return countryOfBirth;
	}
	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}
	@Column(name = "FATHERNAME")
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	@Column(name = "CREATEDBY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "CREATEDON")
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	@Column(name = "CONTACTPERSON")
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	@Column(name = "CONTACTNO")
	public BigDecimal getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(BigDecimal contactNumber) {
		this.contactNumber = contactNumber;
	}
	@Column(name = "ARTICLE_DETID")
	public BigDecimal getArticlaDetailId() {
		return articlaDetailId;
	}
	public void setArticlaDetailId(BigDecimal articlaDetailId) {
		this.articlaDetailId = articlaDetailId;
	}
	@Column(name = "ACTIVESTATUS")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Column(name = "INCOMERANGE")
	public BigDecimal getIncomeRange() {
		return incomeRange;
	}
	public void setIncomeRange(BigDecimal incomeRange) {
		this.incomeRange = incomeRange;
	}
	@Column(name = "PAIDUP_CAPITAL")
	public BigDecimal getPaidUpCapital() {
		return paidUpCapital;
	}
	public void setPaidUpCapital(BigDecimal paidUpCapital) {
		this.paidUpCapital = paidUpCapital;
	}
	@Column(name = "DL_TXN_LIMIT")
	public BigDecimal getDailyTranscationLimit() {
		return dailyTranscationLimit;
	}
	public void setDailyTranscationLimit(BigDecimal dailyTranscationLimit) {
		this.dailyTranscationLimit = dailyTranscationLimit;
	}
	@Column(name = "WK_TXN_LIMIT")
	public BigDecimal getWeeklyTransactionLimit() {
		return weeklyTransactionLimit;
	}
	public void setWeeklyTransactionLimit(BigDecimal weeklyTransactionLimit) {
		this.weeklyTransactionLimit = weeklyTransactionLimit;
	}
	@Column(name = "MO_TXN_LIMIT")
	public BigDecimal getMonthlyTransactionLimit() {
		return monthlyTransactionLimit;
	}
	public void setMonthlyTransactionLimit(BigDecimal monthlyTransactionLimit) {
		this.monthlyTransactionLimit = monthlyTransactionLimit;
	}
	/*@Column(name = "QY_TXN_LIMIT")
	public BigDecimal getQuaterlyTransactionLimit() {
		return quaterlyTransactionLimit;
	}
	public void setQuaterlyTransactionLimit(BigDecimal quaterlyTransactionLimit) {
		this.quaterlyTransactionLimit = quaterlyTransactionLimit;
	}*/
	
	@Column(name = "SPONSORNAME")
	public String getSponsorName() {
		return sponsorName;
	}
	@Column(name = "RELATIONNAME")
	public String getRelationName() {
		return relationName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	@Column(name = "SIGNATURESPECIMENCLOB")
	public Clob getSignatureSpecimenClob() {
		return signatureSpecimenClob;
	}
	public void setSignatureSpecimenClob(Clob signatureSpecimenClob) {
		this.signatureSpecimenClob = signatureSpecimenClob;
	}
	
	
	
}
