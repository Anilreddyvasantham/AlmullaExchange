package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_LIST_BENEFICIARY")
public class BenificiaryListView implements Serializable {

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
	private BigDecimal stateId;
	private BigDecimal districtId;
	private BigDecimal cityId;
	private String stateName;
	private String districtName;
	private String cityName;
	private BigDecimal noOfRemittance;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String benificaryStatusName;
	private String remarks;
	private String bankName;
	private String bankBranchName;
	private String bankAccountNumber;
	private BigDecimal currencyId;
	private String accountStatus;
	private BigDecimal branchCode;
	private String bankCode;
	private BigDecimal serviceProvider;
	private BigDecimal serviceGroupId;
	private String relationShipName;
	private BigDecimal relationShipId;
	private String benificaryName;
	private BigDecimal customerId;
	private String arbenificaryName;
	private BigDecimal bankId;
	private String currencyName;
	private BigDecimal branchId;
	private String serviceGroupCode;
	private String currencyQuoteName;
	private BigDecimal beneficiaryAccountSeqId;
	private BigDecimal beneficiaryRelationShipSeqId;
	private BigDecimal countryId;
	private String countryName;
	private BigDecimal benificaryCountry;
	private String benificaryBankCountryName;
	private BigDecimal mapSequenceId;
	private Date lastJavaRemittance;
	private Date lastEmosRemittance;
	private String creationType;
	private BigDecimal bankAccountTypeId;
	private String swiftBic;
	//added by koti
	private String buildingNo;
	private String flatNo;
	private String streetNo;

	@Id
	@Column(name = "IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	@Id
	@Column(name = "BENEFICARY_MASTER_SEQ_ID")
	public BigDecimal getBeneficaryMasterSeqId() {
		return beneficaryMasterSeqId;
	}
	public void setBeneficaryMasterSeqId(BigDecimal beneficaryMasterSeqId) {
		this.beneficaryMasterSeqId = beneficaryMasterSeqId;
	}
	
	@Column(name = "BANK_BRANCH_ID")
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	@Column(name = "NATIONALITY_NAME")
	public String getNationalityName() {
		return nationalityName;
	}
	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	@Column(name = "CURRENCY_NAME")
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Column(name = "COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
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

	@Column(name = "SECOND_NAME")
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	@Column(name = "THIRD_NAME")
	public String getThirdName() {
		return thirdName;
	}
	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	@Column(name = "FOURTH_NAME")
	public String getFourthName() {
		return fourthName;
	}
	public void setFourthName(String fourthName) {
		this.fourthName = fourthName;
	}

	@Column(name = "FIFTH_NAME")
	public String getFiftheName() {
		return fiftheName;
	}
	public void setFiftheName(String fiftheName) {
		this.fiftheName = fiftheName;
	}

	@Column(name = "BENEFICARY_STATUS_ID")
	public BigDecimal getBenificaryStatusId() {
		return benificaryStatusId;
	}
	public void setBenificaryStatusId(BigDecimal benificaryStatusId) {
		this.benificaryStatusId = benificaryStatusId;
	}

	@Column(name = "NATIONALITY")
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "DATE_OF_BIRTH")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "YEAR_OF_BIRTH")
	public BigDecimal getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(BigDecimal yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	@Column(name = "AGE")
	public BigDecimal getAge() {
		return age;
	}
	public void setAge(BigDecimal age) {
		this.age = age;
	}
	
	@Column(name = "OCCUPATION")
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	@Column(name = "STATE_NAME")
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Column(name = "DISTRICT_NAME")
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	@Column(name = "CITY_NAME")
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "NO_OF_REMITTANCE")
	public BigDecimal getNoOfRemittance() {
		return noOfRemittance;
	}
	public void setNoOfRemittance(BigDecimal noOfRemittance) {
		this.noOfRemittance = noOfRemittance;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	@Column(name = "COUNTRY_NAME")
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Column(name = "BENEFICARY_STATUS_NAME")
	public String getBenificaryStatusName() {
		return benificaryStatusName;
	}
	public void setBenificaryStatusName(String benificaryStatusName) {
		this.benificaryStatusName = benificaryStatusName;
	}

	@Column(name = "FIRST_NAME_LOCAL")
	public String getFirstNameLocal() {
		return firstNameLocal;
	}
	public void setFirstNameLocal(String firstNameLocal) {
		this.firstNameLocal = firstNameLocal;
	}
	
	@Column(name = "SECOND_NAME_LOCAL")
	public String getSecondNameLocal() {
		return secondNameLocal;
	}
	public void setSecondNameLocal(String secondNameLocal) {
		this.secondNameLocal = secondNameLocal;
	}

	@Column(name = "THIRD_NAME_LOCAL")
	public String getThirdNameLocal() {
		return thirdNameLocal;
	}
	public void setThirdNameLocal(String thirdNameLocal) {
		this.thirdNameLocal = thirdNameLocal;
	}

	@Column(name = "FOURTH_NAME_LOCAL")
	public String getFourthNameLocal() {
		return fourthNameLocal;
	}
	public void setFourthNameLocal(String fourthNameLocal) {
		this.fourthNameLocal = fourthNameLocal;
	}

	@Column(name = "FIFTH_NAME_LOCAL")
	public String getFifthNameLocal() {
		return fifthNameLocal;
	}
	public void setFifthNameLocal(String fifthNameLocal) {
		this.fifthNameLocal = fifthNameLocal;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "BENEFICARY_COUNTRY")
	public BigDecimal getBenificaryCountry() {
		return benificaryCountry;
	}
	public void setBenificaryCountry(BigDecimal benificaryCountry) {
		this.benificaryCountry = benificaryCountry;
	}

	@Column(name = "BANK_NAME")
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BANK_BRANCH_NAME")
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	@Column(name = "BANK_ACCOUNT_NUMBER")
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	@Id
	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name = "ACCOUNT_STATUS")
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	@Column(name = "BRANCH_CODE")
	public BigDecimal getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "SERVICE_PROVIDER")
	public BigDecimal getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(BigDecimal serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	@Column(name = "SERVICE_GROUP_ID")
	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}

	@Column(name = "RELATION_NAME")
	public String getRelationShipName() {
		return relationShipName;
	}
	public void setRelationShipName(String relationShipName) {
		this.relationShipName = relationShipName;
	}

	@Column(name = "BENE_NAME")
	public String getBenificaryName() {
		return benificaryName;
	}
	public void setBenificaryName(String benificaryName) {
		this.benificaryName = benificaryName;
	}

	@Column(name="CURRENCY_QUOTE_NAME")
	public String getCurrencyQuoteName() {
		return currencyQuoteName;
	}
	public void setCurrencyQuoteName(String currencyQuoteName) {
		this.currencyQuoteName = currencyQuoteName;
	}

	@Column(name="BENEFICARY_ACCOUNT_SEQ_ID")
	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}
	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}

	@Column(name="BENEFICARY_RELATIONSHIP_SEQ_ID")
	public BigDecimal getBeneficiaryRelationShipSeqId() {
		return beneficiaryRelationShipSeqId;
	}
	public void setBeneficiaryRelationShipSeqId(BigDecimal beneficiaryRelationShipSeqId) {
		this.beneficiaryRelationShipSeqId = beneficiaryRelationShipSeqId;
	}
	
	@Column(name="AR_BENE_NAME")
	public String getArbenificaryName() {
		return arbenificaryName;
	}
	public void setArbenificaryName(String arbenificaryName) {
		this.arbenificaryName = arbenificaryName;
	}
	
	@Column(name="RELATIONSHIP_ID")
	public BigDecimal getRelationShipId() {
		return relationShipId;
	}
	public void setRelationShipId(BigDecimal relationShipId) {
		this.relationShipId = relationShipId;
	}
	
	@Column(name="SERVICE_GROUP_CODE")
	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}
	
	@Column(name="BENEFICARY_BANK_COUNTRY_NAME")
	public String getBenificaryBankCountryName() {
		return benificaryBankCountryName;
	}
	public void setBenificaryBankCountryName(String benificaryBankCountryName) {
		this.benificaryBankCountryName = benificaryBankCountryName;
	}
	
	@Column(name="MAP_BENE_SEQ")
	public BigDecimal getMapSequenceId() {
		return mapSequenceId;
	}
	public void setMapSequenceId(BigDecimal mapSequenceId) {
		this.mapSequenceId = mapSequenceId;
	}
	
	@Column(name="LAST_JAVA_REMITTANCE")
	public Date getLastJavaRemittance() {
		return lastJavaRemittance;
	}
	public void setLastJavaRemittance(Date lastJavaRemittance) {
		this.lastJavaRemittance = lastJavaRemittance;
	}
	
	@Column(name="LAST_EMOS_REMITTANCE")
	public Date getLastEmosRemittance() {
		return lastEmosRemittance;
	}
	public void setLastEmosRemittance(Date lastEmosRemittance) {
		this.lastEmosRemittance = lastEmosRemittance;
	}
	
	@Column(name="STATE_ID")
	public BigDecimal getStateId() {
		return stateId;
	}
	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}
	
	@Column(name="DISTRICT_ID")
	public BigDecimal getDistrictId() {
		return districtId;
	}
	public void setDistrictId(BigDecimal districtId) {
		this.districtId = districtId;
	}
	
	@Column(name="CITY_ID")
	public BigDecimal getCityId() {
		return cityId;
	}
	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}
	
	@Column(name="CREATION_TYPE")
	public String getCreationType() {
		return creationType;
	}
	public void setCreationType(String creationType) {
		this.creationType = creationType;
	}
	
	@Column(name="BANK_ACCOUNT_TYPE_ID")
	public BigDecimal getBankAccountTypeId() {
		return bankAccountTypeId;
	}
	public void setBankAccountTypeId(BigDecimal bankAccountTypeId) {
		this.bankAccountTypeId = bankAccountTypeId;
	}
	@Column(name="SWIFT_BIC")
	public String getSwiftBic() {
		return swiftBic;
	}
	public void setSwiftBic(String swiftBic) {
		this.swiftBic = swiftBic;
	}
	@Column(name = "BUILDING_NO")
	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
	@Column(name = "FLAT")
	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}
	@Column(name = "STREET")
	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}
}
