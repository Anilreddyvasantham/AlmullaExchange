package com.amg.exchange.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amg.exchange.registration.model.CustomerContactDetailView;
import com.amg.exchange.registration.model.CustomerEmployeeInfoView;
import com.amg.exchange.registration.model.CustomerIdproofView;

public class CustomerMainReport {

	private String title;
	private String titleLocal;
	private String firstName;
	private String firstNameLocal;
	private String middleName;
	private String middleNameLocal;
	private String lastName;
	private String lastNameLocal;
	private String shortName;
	private String shortNameLocal;
	private String nationality;
	private String amlStatus;
	private String email;
	private String alternativeEmail;
	private String gender;
	private Date dateOfBirth;
	private String mobileNo;
	private BigDecimal customerReference;
	private String introducedBy;
	private String introducedDate;
	private String pepIndicator;
	private String medicalInsurence;
	private String artical;
	private String level;
	private String incomeRange;
	private BigDecimal dailyLimit;
	private BigDecimal weeklyLimit;
	private BigDecimal monthlyLimit;
	private BigDecimal quaterlyLimit;
	private BigDecimal halfyearlyLimit;
	private BigDecimal annualLimit;
	private String logoPath;
	private String signature;
	private String componentDescription;
	private String countryName;
	private String subReport;
	private String sponsorName;
	private String relationName;
	private String customerBranch;
	private String currentDate;
	private String companyName;
	
	
	private List<CustomerContactDetailView> contactDetailList;
	private List<CustomerEmployeeInfoView> employeeInfoList;
	private List<CustomerIdproofView> idProofList;
	private List<CustomerMainReport> sponsorList;
	private String signatureSpecimenClob;
	
	
	
	
	
	public List<CustomerMainReport> getSponsorList() {
		return sponsorList;
	}
	public void setSponsorList(List<CustomerMainReport> sponsorList) {
		this.sponsorList = sponsorList;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getCustomerBranch() {
		return customerBranch;
	}
	public void setCustomerBranch(String customerBranch) {
		this.customerBranch = customerBranch;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	public String getSubReport() {
		return subReport;
	}
	public void setSubReport(String subReport) {
		this.subReport = subReport;
	}
	public List<CustomerContactDetailView> getContactDetailList() {
		return contactDetailList;
	}
	public void setContactDetailList(
			List<CustomerContactDetailView> contactDetailList) {
		this.contactDetailList = contactDetailList;
	}
	public List<CustomerEmployeeInfoView> getEmployeeInfoList() {
		return employeeInfoList;
	}
	public void setEmployeeInfoList(List<CustomerEmployeeInfoView> employeeInfoList) {
		this.employeeInfoList = employeeInfoList;
	}
	public List<CustomerIdproofView> getIdProofList() {
		return idProofList;
	}
	public void setIdProofList(List<CustomerIdproofView> idProofList) {
		this.idProofList = idProofList;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getComponentDescription() {
		return componentDescription;
	}
	public void setComponentDescription(String componentDescription) {
		this.componentDescription = componentDescription;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleLocal() {
		return titleLocal;
	}
	public void setTitleLocal(String titleLocal) {
		this.titleLocal = titleLocal;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstNameLocal() {
		return firstNameLocal;
	}
	public void setFirstNameLocal(String firstNameLocal) {
		this.firstNameLocal = firstNameLocal;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getMiddleNameLocal() {
		return middleNameLocal;
	}
	public void setMiddleNameLocal(String middleNameLocal) {
		this.middleNameLocal = middleNameLocal;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastNameLocal() {
		return lastNameLocal;
	}
	public void setLastNameLocal(String lastNameLocal) {
		this.lastNameLocal = lastNameLocal;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getShortNameLocal() {
		return shortNameLocal;
	}
	public void setShortNameLocal(String shortNameLocal) {
		this.shortNameLocal = shortNameLocal;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getAmlStatus() {
		return amlStatus;
	}
	public void setAmlStatus(String amlStatus) {
		this.amlStatus = amlStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAlternativeEmail() {
		return alternativeEmail;
	}
	public void setAlternativeEmail(String alternativeEmail) {
		this.alternativeEmail = alternativeEmail;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getIntroducedBy() {
		return introducedBy;
	}
	public void setIntroducedBy(String introducedBy) {
		this.introducedBy = introducedBy;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	public String getIntroducedDate() {
		return introducedDate;
	}
	public void setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
	}
	public BigDecimal getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(BigDecimal dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public BigDecimal getWeeklyLimit() {
		return weeklyLimit;
	}
	public void setWeeklyLimit(BigDecimal weeklyLimit) {
		this.weeklyLimit = weeklyLimit;
	}
	public BigDecimal getMonthlyLimit() {
		return monthlyLimit;
	}
	public void setMonthlyLimit(BigDecimal monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
	}
	public BigDecimal getQuaterlyLimit() {
		return quaterlyLimit;
	}
	public void setQuaterlyLimit(BigDecimal quaterlyLimit) {
		this.quaterlyLimit = quaterlyLimit;
	}
	public BigDecimal getHalfyearlyLimit() {
		return halfyearlyLimit;
	}
	public void setHalfyearlyLimit(BigDecimal halfyearlyLimit) {
		this.halfyearlyLimit = halfyearlyLimit;
	}
	public BigDecimal getAnnualLimit() {
		return annualLimit;
	}
	public void setAnnualLimit(BigDecimal annualLimit) {
		this.annualLimit = annualLimit;
	}
	public String getPepIndicator() {
		return pepIndicator;
	}
	public void setPepIndicator(String pepIndicator) {
		this.pepIndicator = pepIndicator;
	}
	public String getMedicalInsurence() {
		return medicalInsurence;
	}
	public void setMedicalInsurence(String medicalInsurence) {
		this.medicalInsurence = medicalInsurence;
	}
	public String getArtical() {
		return artical;
	}
	public void setArtical(String artical) {
		this.artical = artical;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getIncomeRange() {
		return incomeRange;
	}
	public void setIncomeRange(String incomeRange) {
		this.incomeRange = incomeRange;
	}
	
	public String getIdProofFor() {
		return IdProofFor;
	}
	public void setIdProofFor(String idProofFor) {
		IdProofFor = idProofFor;
	}

	private String IdProofFor;





	public String getSignatureSpecimenClob() {
		return signatureSpecimenClob;
	}
	public void setSignatureSpecimenClob(String signatureSpecimenClob) {
		this.signatureSpecimenClob = signatureSpecimenClob;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
	
	
	
}
