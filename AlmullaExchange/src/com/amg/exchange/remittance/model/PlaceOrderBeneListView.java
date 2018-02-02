package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_BENE_LIST_PO")
public class PlaceOrderBeneListView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal slNo;
	private BigDecimal beneficaryMasterSeqId;
	//private BigDecimal applicationCountryId;
	/*private String firstName;
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
	private String benificaryStatusName;
	private String remarks;
	private String bankName;
	private String bankBranchName;
	private String accountStatus;*/
	private String bankAccountNumber;
	private BigDecimal currencyId;
	private BigDecimal branchCode;
	private String bankCode;
	private BigDecimal serviceGroupId;
	private String benificaryName;
	private BigDecimal customerId;
	private BigDecimal bankId;
	private BigDecimal branchId;
	private String serviceGroupCode;
	private String currencyQuoteName;
	private BigDecimal beneficiaryAccountSeqId;
	private BigDecimal countryId;
	private String countryName;
	private BigDecimal benificaryCountry;
	private String benificaryBankCountryName;
	private String bankName;
	private BigDecimal customerRef;
	
	@Id
	@Column(name = "SLNO")
	public BigDecimal getSlNo() {
		return slNo;
	}
	public void setSlNo(BigDecimal slNo) {
		this.slNo = slNo;
	}
	
	@Id
	@Column(name = "BENEFICARY_MASTER_SEQ_ID")
	public BigDecimal getBeneficaryMasterSeqId() {
		return beneficaryMasterSeqId;
	}
	public void setBeneficaryMasterSeqId(BigDecimal beneficaryMasterSeqId) {
		this.beneficaryMasterSeqId = beneficaryMasterSeqId;
	}
	
	/*public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}*/
	
	@Column(name = "BANK_ACCOUNT_NUMBER")
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	
	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
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
	
	@Column(name = "SERVICE_GROUP_ID")
	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}
	
	@Column(name = "BENEFICIARY_NAME")
	public String getBenificaryName() {
		return benificaryName;
	}
	public void setBenificaryName(String benificaryName) {
		this.benificaryName = benificaryName;
	}
	
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	@Column(name = "BANK_BRANCH_ID")
	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}
	
	@Column(name = "SERVICE_GROUP_CODE")
	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}
	
	@Column(name = "CURRENCY_QUOTE_NAME")
	public String getCurrencyQuoteName() {
		return currencyQuoteName;
	}
	public void setCurrencyQuoteName(String currencyQuoteName) {
		this.currencyQuoteName = currencyQuoteName;
	}
	
	@Column(name = "BENEFICARY_ACCOUNT_SEQ_ID")
	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}
	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}
	
	@Column(name = "COUNTRY_ID")
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	@Column(name = "COUNTRY_NAME")
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	@Column(name = "BENEFICARY_COUNTRY")
	public BigDecimal getBenificaryCountry() {
		return benificaryCountry;
	}
	public void setBenificaryCountry(BigDecimal benificaryCountry) {
		this.benificaryCountry = benificaryCountry;
	}
	
	@Column(name = "BENEFICARY_BANK_COUNTRY_NAME")
	public String getBenificaryBankCountryName() {
		return benificaryBankCountryName;
	}
	public void setBenificaryBankCountryName(String benificaryBankCountryName) {
		this.benificaryBankCountryName = benificaryBankCountryName;
	}
	
	@Column(name = "BANK_NAME")
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}
	
	
}
