package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.remittance.model.Remittance;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.model.PipsMaster;

@Entity
@Table(name = "EX_COUNTRY_BRANCH")
public class CountryBranch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal countryBranchId;
	private CountryMaster countryMaster;
	private String branchName;

	private BigDecimal accountCode;
	private String corporateStatus;
	private BigDecimal branchId;
	private String headOfficeIndicator;
	private BigDecimal telephoneNumber;
	private String emailId;
	private BigDecimal areaCode;
	private BigDecimal ecNumeber;
	private String ipAddress;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String scanInd;
	private String digitalSignInd;

	private Set<ExchangeRate> exchangeRate;
	private Set<PipsMaster> pipsMaster;
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);

	private Set<Employee> fsEmployee;
	private Set<Stock> fsStock;
	private String wuAccountCode;

	public CountryBranch(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public CountryBranch() {
		super();
	}

	public CountryBranch(BigDecimal countryBranchId,
			CountryMaster countryMaster, String branchName,
			BigDecimal accountCode, String corporateStatus,
			BigDecimal branchId, String headOfficeIndicator,
			BigDecimal telephoneNumber, String emailId, BigDecimal areaCode,
			BigDecimal ecNumeber, String ipAddress, String isActive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String remarks, String scanInd, String digitalSignInd,
			Set<ExchangeRate> exchangeRate, Set<PipsMaster> pipsMaster,
			Set<Remittance> exRemittance, Set<Employee> fsEmployee,
			Set<Stock> fsStock, String wuAccountCode) {
		super();
		this.countryBranchId = countryBranchId;
		this.countryMaster = countryMaster;
		this.branchName = branchName;
		this.accountCode = accountCode;
		this.corporateStatus = corporateStatus;
		this.branchId = branchId;
		this.headOfficeIndicator = headOfficeIndicator;
		this.telephoneNumber = telephoneNumber;
		this.emailId = emailId;
		this.areaCode = areaCode;
		this.ecNumeber = ecNumeber;
		this.ipAddress = ipAddress;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.scanInd = scanInd;
		this.digitalSignInd = digitalSignInd;
		this.exchangeRate = exchangeRate;
		this.pipsMaster = pipsMaster;
		this.exRemittance = exRemittance;
		this.fsEmployee = fsEmployee;
		this.fsStock = fsStock;
		this.wuAccountCode = wuAccountCode;
	}

	/*	@GeneratedValue
	@Id
	@Column(name = "COUNTRY_BRANCH_ID")*/
	@Id
	@GeneratedValue(generator="fs_country_branch_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_country_branch_seq",sequenceName="EX_COUNTRY_BRANCH_SEQ",allocationSize=1)
	@Column(name = "COUNTRY_BRANCH_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	@OneToMany(mappedBy = "fsCountryBranch")
	public Set<Employee> getFsEmployee() {
		return fsEmployee;
	}

	public void setFsEmployee(Set<Employee> fsEmployee) {
		this.fsEmployee = fsEmployee;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getCountryMaster() {
		return countryMaster;
	}

	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}

	@Column(name = "BRANCH_NAME")
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@OneToMany(mappedBy = "countryBranch")
	public Set<Stock> getFsStock() {
		return fsStock;
	}

	public void setFsStock(Set<Stock> fsStock) {
		this.fsStock = fsStock;
	}

	@OneToMany(mappedBy = "countryBranchId")
	public Set<ExchangeRate> getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Set<ExchangeRate> exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@OneToMany(mappedBy = "countryBranch")
	public Set<PipsMaster> getPipsMaster() {
		return pipsMaster;
	}

	public void setPipsMaster(Set<PipsMaster> pipsMaster) {
		this.pipsMaster = pipsMaster;
	}

	@OneToMany(mappedBy = "exCountryBranch")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}

	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}

	/*
	 * added new column by ramakrishna 26-02-2015
	 */
	@Column(name = "ACCOUNT_CODE")
	public BigDecimal getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(BigDecimal accountCode) {
		this.accountCode = accountCode;
	}

	@Column(name = "CORPORATE_STATUS")
	public String getCorporateStatus() {
		return corporateStatus;
	}

	public void setCorporateStatus(String corporateStatus) {
		this.corporateStatus = corporateStatus;
	}

	@Column(name = "BRANCH_ID")
	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	@Column(name = "HEAD_OFFICE_INDICATOR")
	public String getHeadOfficeIndicator() {
		return headOfficeIndicator;
	}

	public void setHeadOfficeIndicator(String headOfficeIndicator) {
		this.headOfficeIndicator = headOfficeIndicator;
	}

	@Column(name = "TELEPHONE_NUMBER")
	public BigDecimal getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(BigDecimal telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	@Column(name = "EMAIL_ID")
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name = "AREA_CODE")
	public BigDecimal getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(BigDecimal areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "EC_NUMBER")
	public BigDecimal getEcNumeber() {
		return ecNumeber;
	}

	public void setEcNumeber(BigDecimal ecNumeber) {
		this.ecNumeber = ecNumeber;
	}

	@Column(name = "IP_ADDRESS")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
	
	@Column(name = "SCAN_IND")
	public String getScanInd() {
		return scanInd;
	}

	public void setScanInd(String scanInd) {
		this.scanInd = scanInd;
	}
	
	@Column(name = "DIGITAL_SIGNATURE_IND")
	public String getDigitalSignInd() {
		return digitalSignInd;
	}

	public void setDigitalSignInd(String digitalSignInd) {
		this.digitalSignInd = digitalSignInd;
	}	
	
	@Column(name = "WU_ACCOUNT_CODE")
	public String getWuAccountCode() {
		return wuAccountCode;
	}

	public void setWuAccountCode(String wuAccountCode) {
		this.wuAccountCode = wuAccountCode;
	}

}
