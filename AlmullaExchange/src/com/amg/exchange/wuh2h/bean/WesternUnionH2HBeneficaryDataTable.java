package com.amg.exchange.wuh2h.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WesternUnionH2HBeneficaryDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal idNo;
	private BigDecimal beneficaryMasterSeqId;
	private BigDecimal applicationCountryId;
	private String firstName;
	private String secondName;
	private String thirdName;
	private String fourthName;
	private String fiftheName;
	private String firstNameLocal;
	private String secondNameLocal;
	private String thirdNameLocal;
	private String fourthNameLocal;
	private String fifthNameLocal;
	private BigDecimal benificaryStatusId;
	private String nationality;
	private String nationalityName;
	private Date dateOfBirth;
	private BigDecimal yearOfBirth;
	private BigDecimal age;
	private String occupation;
	private String stateName;
	private String districtName;
	private String cityName;
	private BigDecimal noOfRemittance;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal countryId;
	private String countryName;
	private String benificaryStatusName;
	private String remarks;
	private BigDecimal benificaryCountry;
	private String bankBranchName;
	private String bankAccountNumber;
	private String accountStatus;
	private BigDecimal branchCode;
	private String bankCode;
	private BigDecimal serviceProvider;
	private BigDecimal serviceGroupId;
	private String relationShipName;
	private BigDecimal relationShipId;
	private BigDecimal customerId;
	private String arbenificaryName;
	private BigDecimal bankId;
	private BigDecimal branchId;
	private String serviceGroupCode;
	private String bankName;
	private BigDecimal currencyId;
	private String benificaryName;
	private String currencyName;
	private String currencyQuoteName;
	private BigDecimal beneficiaryAccountSeqId;
	private BigDecimal beneficiaryRelationShipSeqId;
	private String benificaryCountryName;
	private String serviceGroupName;
	private String telphoneNum;
	private String location;
	private Boolean booDisabled;
	
	private String buildingNo;
	
	
	/*
	 * New 
	 */
	
	private String swiftBic; 
	
	public BigDecimal getIdNo() {
		return idNo;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	public BigDecimal getBeneficaryMasterSeqId() {
		return beneficaryMasterSeqId;
	}
	public void setBeneficaryMasterSeqId(BigDecimal beneficaryMasterSeqId) {
		this.beneficaryMasterSeqId = beneficaryMasterSeqId;
	}
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getThirdName() {
		return thirdName;
	}
	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}
	public String getFourthName() {
		return fourthName;
	}
	public void setFourthName(String fourthName) {
		this.fourthName = fourthName;
	}
	public String getFiftheName() {
		return fiftheName;
	}
	public void setFiftheName(String fiftheName) {
		this.fiftheName = fiftheName;
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
	public String getFourthNameLocal() {
		return fourthNameLocal;
	}
	public void setFourthNameLocal(String fourthNameLocal) {
		this.fourthNameLocal = fourthNameLocal;
	}
	public String getFifthNameLocal() {
		return fifthNameLocal;
	}
	public void setFifthNameLocal(String fifthNameLocal) {
		this.fifthNameLocal = fifthNameLocal;
	}
	public BigDecimal getBenificaryStatusId() {
		return benificaryStatusId;
	}
	public void setBenificaryStatusId(BigDecimal benificaryStatusId) {
		this.benificaryStatusId = benificaryStatusId;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getNationalityName() {
		return nationalityName;
	}
	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public BigDecimal getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(BigDecimal yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	public BigDecimal getAge() {
		return age;
	}
	public void setAge(BigDecimal age) {
		this.age = age;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public BigDecimal getNoOfRemittance() {
		return noOfRemittance;
	}
	public void setNoOfRemittance(BigDecimal noOfRemittance) {
		this.noOfRemittance = noOfRemittance;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getBenificaryStatusName() {
		return benificaryStatusName;
	}
	public void setBenificaryStatusName(String benificaryStatusName) {
		this.benificaryStatusName = benificaryStatusName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getBenificaryCountry() {
		return benificaryCountry;
	}
	public void setBenificaryCountry(BigDecimal benificaryCountry) {
		this.benificaryCountry = benificaryCountry;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public BigDecimal getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public BigDecimal getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(BigDecimal serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}
	public String getRelationShipName() {
		return relationShipName;
	}
	public void setRelationShipName(String relationShipName) {
		this.relationShipName = relationShipName;
	}
	public BigDecimal getRelationShipId() {
		return relationShipId;
	}
	public void setRelationShipId(BigDecimal relationShipId) {
		this.relationShipId = relationShipId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public String getArbenificaryName() {
		return arbenificaryName;
	}
	public void setArbenificaryName(String arbenificaryName) {
		this.arbenificaryName = arbenificaryName;
	}
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public String getBenificaryName() {
		return benificaryName;
	}
	public void setBenificaryName(String benificaryName) {
		this.benificaryName = benificaryName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCurrencyQuoteName() {
		return currencyQuoteName;
	}
	public void setCurrencyQuoteName(String currencyQuoteName) {
		this.currencyQuoteName = currencyQuoteName;
	}
	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}
	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}
	public BigDecimal getBeneficiaryRelationShipSeqId() {
		return beneficiaryRelationShipSeqId;
	}
	public void setBeneficiaryRelationShipSeqId(
			BigDecimal beneficiaryRelationShipSeqId) {
		this.beneficiaryRelationShipSeqId = beneficiaryRelationShipSeqId;
	}
	public String getBenificaryCountryName() {
		return benificaryCountryName;
	}
	public void setBenificaryCountryName(String benificaryCountryName) {
		this.benificaryCountryName = benificaryCountryName;
	}
	public String getTelphoneNum() {
		return telphoneNum;
	}
	public void setTelphoneNum(String telphoneNum) {
		this.telphoneNum = telphoneNum;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Boolean getBooDisabled() {
		return booDisabled;
	}
	public void setBooDisabled(Boolean booDisabled) {
		this.booDisabled = booDisabled;
	}
	public String getServiceGroupName() {
		return serviceGroupName;
	}
	public void setServiceGroupName(String serviceGroupName) {
		this.serviceGroupName = serviceGroupName;
	}
	
	
	
	public String getSwiftBic() {
		return swiftBic;
	}
	public void setSwiftBic(String swiftBic) {
		this.swiftBic = swiftBic;
	}
	
	
	public String getBuildingNo() {
		return buildingNo;
	}
	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
	
	
	public Object[] getCompositeKey() {
	    return new Object[] { benificaryName, relationShipName,benificaryCountryName};
	}
	@Override
	public String toString() {
		return "WesternUnionH2HBeneficaryDataTable [idNo=" + idNo
				+ ", beneficaryMasterSeqId=" + beneficaryMasterSeqId
				+ ", applicationCountryId=" + applicationCountryId
				+ ", firstName=" + firstName + ", secondName=" + secondName
				+ ", thirdName=" + thirdName + ", fourthName=" + fourthName
				+ ", fiftheName=" + fiftheName + ", firstNameLocal="
				+ firstNameLocal + ", secondNameLocal=" + secondNameLocal
				+ ", thirdNameLocal=" + thirdNameLocal + ", fourthNameLocal="
				+ fourthNameLocal + ", fifthNameLocal=" + fifthNameLocal
				+ ", benificaryStatusId=" + benificaryStatusId
				+ ", nationality=" + nationality + ", nationalityName="
				+ nationalityName + ", dateOfBirth=" + dateOfBirth
				+ ", yearOfBirth=" + yearOfBirth + ", age=" + age
				+ ", occupation=" + occupation + ", stateName=" + stateName
				+ ", districtName=" + districtName + ", cityName=" + cityName
				+ ", noOfRemittance=" + noOfRemittance + ", isActive="
				+ isActive + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", countryId=" + countryId
				+ ", countryName=" + countryName + ", benificaryStatusName="
				+ benificaryStatusName + ", remarks=" + remarks
				+ ", benificaryCountry=" + benificaryCountry
				+ ", bankBranchName=" + bankBranchName + ", bankAccountNumber="
				+ bankAccountNumber + ", accountStatus=" + accountStatus
				+ ", branchCode=" + branchCode + ", bankCode=" + bankCode
				+ ", serviceProvider=" + serviceProvider + ", serviceGroupId="
				+ serviceGroupId + ", relationShipName=" + relationShipName
				+ ", relationShipId=" + relationShipId + ", customerId="
				+ customerId + ", arbenificaryName=" + arbenificaryName
				+ ", bankId=" + bankId + ", branchId=" + branchId
				+ ", serviceGroupCode=" + serviceGroupCode + ", bankName="
				+ bankName + ", currencyId=" + currencyId + ", benificaryName="
				+ benificaryName + ", currencyName=" + currencyName
				+ ", currencyQuoteName=" + currencyQuoteName
				+ ", beneficiaryAccountSeqId=" + beneficiaryAccountSeqId
				+ ", beneficiaryRelationShipSeqId="
				+ beneficiaryRelationShipSeqId + ", benificaryCountryName="
				+ benificaryCountryName + ", serviceGroupName="
				+ serviceGroupName + ", telphoneNum=" + telphoneNum
				+ ", location=" + location + ", booDisabled=" + booDisabled
				+ ", buildingNo=" + buildingNo + ", swiftBic=" + swiftBic + "]";
	}
	
	
	
}
