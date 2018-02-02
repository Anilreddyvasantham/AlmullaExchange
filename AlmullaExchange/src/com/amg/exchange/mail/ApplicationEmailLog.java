package com.amg.exchange.mail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_EMAIL_LOG")
public class ApplicationEmailLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal emailLogId;
	private String userName;
	private Date createdDate;
	private String emailType;
	private BigDecimal countryBranchId;
	private String ipAddress;
	private BigDecimal customerId;
	private BigDecimal customerReference;
	private String customerName;
	private BigDecimal bankId;
	private String bankName;
	private BigDecimal bankBranchId;
	private String bankBranchName;
	private BigDecimal bankCountryId;
	private String bankCountryName;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal beneficiaryId;
	private String beneficiaryName;
	private String employeeNumber;
	private String civilId;
	private String partnerCivilId;
	private BigDecimal employeeId;
	

	public ApplicationEmailLog() {
		super();
	}
	
	@Id
	@GeneratedValue(generator="ex_email_log_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_email_log_seq",sequenceName="EX_EMAIL_LOG_SEQ",allocationSize=1)
	@Column(name = "EMAIL_LOG_ID")
	public BigDecimal getEmailLogId() {
		return emailLogId;
	}
	public void setEmailLogId(BigDecimal emailLogId) {
		this.emailLogId = emailLogId;
	}
	
	@Column(name = "USERNAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "EMAIL_TYPE")
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	
	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	
	@Column(name = "IP_ADDRESS")
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	
	@Column(name = "CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Column(name = "BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	@Column(name = "BANK_NAME")
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Column(name = "BANK_BRANCH_ID")
	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}
	
	@Column(name = "BANK_BRANCH_NAME")
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	
	@Column(name = "BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	@Column(name = "BANK_COUNTRY_NAME")
	public String getBankCountryName() {
		return bankCountryName;
	}
	public void setBankCountryName(String bankCountryName) {
		this.bankCountryName = bankCountryName;
	}
	
	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name = "CURRENCY_QUOTE")
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	@Column(name = "BENEFICIARY_ID")
	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	
	@Column(name = "BENEFICIARY_NAME")
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
	@Column(name = "EMPLOYEE_NUMBER")
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	@Column(name = "CIVIL_ID")
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	@Column(name = "PARTNER_CIVIL_ID")
	public String getPartnerCivilId() {
		return partnerCivilId;
	}
	public void setPartnerCivilId(String partnerCivilId) {
		this.partnerCivilId = partnerCivilId;
	}

	@Column(name = "EMPLOYEE_ID")
	public BigDecimal getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}
	
}
