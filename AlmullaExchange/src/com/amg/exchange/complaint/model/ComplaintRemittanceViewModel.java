package com.amg.exchange.complaint.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_COMPLAINT_REMITTANCE")
public class ComplaintRemittanceViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public BigDecimal remittanceTranxId;
	public BigDecimal beneficiaryBankId;
	public BigDecimal beneficiaryBranchId;
	public String customerId;
	public Date documentDate;
	public BigDecimal foreignTranxAmount;
	public BigDecimal bankCountryId;
	public BigDecimal documentNo;
	public BigDecimal documentId;
	public BigDecimal documentFinYear;
	public String beneficiaryAccountNo;
	public String beneficiaryBankName;
	public String beneficiaryBranchName;
	public String beneficiaryName;
	public String customerName;
	public String mobileNumber;
	public String countryName;
	public BigDecimal serviceId;
	public String serviceName;
	public BigDecimal foreignCurrencyId;
	public String currencyName;
	public BigDecimal companyId;

	public BigDecimal customerReference;
	public BigDecimal documentFinanceYear;

	public ComplaintRemittanceViewModel() {

	}

	public ComplaintRemittanceViewModel(BigDecimal remittanceTranxId,
			BigDecimal beneficiaryBankId, BigDecimal beneficiaryBranchId,
			String customerId, Date documentDate,
			BigDecimal foreignTranxAmount, BigDecimal bankCountryId,
			BigDecimal documentNo, BigDecimal documentId,
			BigDecimal documentFinYear, String beneficiaryAccountNo,
			String beneficiaryBankName, String beneficiaryBranchName,
			String beneficiaryName, String customerName, String mobileNumber,
			String countryName, BigDecimal serviceId, String serviceName,
			BigDecimal foreignCurrencyId, String currencyName,
			BigDecimal companyId, BigDecimal customerReference,
			BigDecimal documentFinanceYear) {
		super();
		this.remittanceTranxId = remittanceTranxId;
		this.beneficiaryBankId = beneficiaryBankId;
		this.beneficiaryBranchId = beneficiaryBranchId;
		this.customerId = customerId;
		this.documentDate = documentDate;
		this.foreignTranxAmount = foreignTranxAmount;
		this.bankCountryId = bankCountryId;
		this.documentNo = documentNo;
		this.documentId = documentId;
		this.documentFinYear = documentFinYear;
		this.beneficiaryAccountNo = beneficiaryAccountNo;
		this.beneficiaryBankName = beneficiaryBankName;
		this.beneficiaryBranchName = beneficiaryBranchName;
		this.beneficiaryName = beneficiaryName;
		this.customerName = customerName;
		this.mobileNumber = mobileNumber;
		this.countryName = countryName;
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.foreignCurrencyId = foreignCurrencyId;
		this.currencyName = currencyName;
		this.companyId = companyId;
		this.customerReference = customerReference;
		this.documentFinanceYear = documentFinanceYear;
	}

	@Id
	@Column(name = "IDNO")
	public BigDecimal getRemittanceTranxId() {
		return remittanceTranxId;
	}

	public void setRemittanceTranxId(BigDecimal remittanceTranxId) {
		this.remittanceTranxId = remittanceTranxId;
	}

	@Column(name = "BANK_ID")
	public BigDecimal getBeneficiaryBankId() {
		return beneficiaryBankId;
	}

	public void setBeneficiaryBankId(BigDecimal beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}

	@Column(name = "BRANCH_ID")
	public BigDecimal getBeneficiaryBranchId() {
		return beneficiaryBranchId;
	}

	public void setBeneficiaryBranchId(BigDecimal beneficiaryBranchId) {
		this.beneficiaryBranchId = beneficiaryBranchId;
	}

	@Column(name = "CUSTOMER_ID")
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "FOREIGN_TRANX_AMOUNT")
	public BigDecimal getForeignTranxAmount() {
		return foreignTranxAmount;
	}

	public void setForeignTranxAmount(BigDecimal foreignTranxAmount) {
		this.foreignTranxAmount = foreignTranxAmount;
	}

	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	@Column(name = "DOCUMENT_FINANCE_FY")
	public BigDecimal getDocumentFinYear() {
		return documentFinYear;
	}

	public void setDocumentFinYear(BigDecimal documentFinYear) {
		this.documentFinYear = documentFinYear;
	}

	@Column(name = "BENEFICIARY_ACCOUNT_NO")
	public String getBeneficiaryAccountNo() {
		return beneficiaryAccountNo;
	}

	public void setBeneficiaryAccountNo(String beneficiaryAccountNo) {
		this.beneficiaryAccountNo = beneficiaryAccountNo;
	}

	@Column(name = "BENEFICIARY_BANK")
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}

	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}

	@Column(name = "BENEFICIARY_BRANCH")
	public String getBeneficiaryBranchName() {
		return beneficiaryBranchName;
	}

	public void setBeneficiaryBranchName(String beneficiaryBranchName) {
		this.beneficiaryBranchName = beneficiaryBranchName;
	}

	@Column(name = "BENEFICIARY_NAME")
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	@Column(name = "CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "CUSTOMER_MOBILE_NO")
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name = "BANK_COUNTRY_NAME")
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Column(name = "SERVICE_ID")
	public BigDecimal getServiceId() {
		return serviceId;
	}

	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "SERVICE_NAME")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "FOREIGN_CURRENCY_ID")
	public BigDecimal getForeignCurrencyId() {
		return foreignCurrencyId;
	}

	public void setForeignCurrencyId(BigDecimal foreignCurrencyId) {
		this.foreignCurrencyId = foreignCurrencyId;
	}

	@Column(name = "CURRENCY_NAME")
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Column(name = "CUSTOMER_REF")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

}
