package com.amg.exchange.treasury.model;

// default package
// Generated May 23, 2014 5:18:25  Created by Chennai ODC

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/**
 * BankApplicability Created by Chennai ODC
 */
@Entity
@Table(name = "EX_BANK_APPLICABILITY")
public class BankApplicability implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal applicabilityId;
	private BankMaster bankMaster;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster fsCountryMaster;
	private BankIndicator bankInd;
	private String websrvUsername;
	private String websrvPassword;
	private String websrvAgntId;
	private String websrvAgntPin;
	private String websrvInd;
	private BigDecimal timezone;
	private String exchangeId;
	private String isActive;
	private Date createDate;
	private Date updateDate;
	private String creator;
	private String modifier;
	private String remarks;
	
	private String approvedBy;
	private Date approvedDate;
	
	//private Set<AccountBalance> accountBanalce = new HashSet<AccountBalance>(0);
	
	

	public BankApplicability(BankIndicator bankInd) {
		super();
		this.bankInd = bankInd;
	}

	public BankApplicability() {
	}

	public BankApplicability(BigDecimal applicabilityId) {
		this.applicabilityId = applicabilityId;
	}

	public BankApplicability(BigDecimal applicabilityId, BankMaster bankMaster,
			CompanyMaster fsCompanyMaster, CountryMaster fsCountryMaster,
			BankIndicator bankInd, String websrvUsername, String websrvPassword,
			String websrvAgntId, String websrvAgntPin, String websrvInd,
			BigDecimal timezone, String exchangeId, String isActive,
			Date createDate, Date updateDate, String creator, String modifier, String remarks) {
		this.applicabilityId = applicabilityId;
		this.bankMaster = bankMaster;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.bankInd = bankInd;
		this.websrvUsername = websrvUsername;
		this.websrvPassword = websrvPassword;
		this.websrvAgntId = websrvAgntId;
		this.websrvAgntPin = websrvAgntPin;
		this.websrvInd = websrvInd;
		this.timezone = timezone;
		this.exchangeId = exchangeId;
		this.isActive = isActive;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.creator = creator;
		this.modifier = modifier;
		this.remarks = remarks;

	}

	public BankApplicability(BigDecimal bankId, String bankFullName) {
	}

	@Id
	@GeneratedValue(generator="ex_bank_applicability_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_applicability_seq",sequenceName="EX_BANK_APPLICABILITY_SEQ",allocationSize=1)
	@Column(name = "APPLICABILITY_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getApplicabilityId() {
		return this.applicabilityId;
	}

	public void setApplicabilityId(BigDecimal applicabilityId) {
		this.applicabilityId = applicabilityId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBankMaster() {
		return this.bankMaster;
	}

	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_INDICATOR_ID")
	public BankIndicator getBankInd() {
		return bankInd;
	}

	public void setBankInd(BankIndicator bankInd) {
		this.bankInd = bankInd;
	}

	@Column(name = "WEBSRV_USERNAME", length = 30)
	public String getWebsrvUsername() {
		return this.websrvUsername;
	}

	public void setWebsrvUsername(String websrvUsername) {
		this.websrvUsername = websrvUsername;
	}

	@Column(name = "WEBSRV_PASSWORD", length = 30)
	public String getWebsrvPassword() {
		return this.websrvPassword;
	}

	public void setWebsrvPassword(String websrvPassword) {
		this.websrvPassword = websrvPassword;
	}

	@Column(name = "WEBSRV_AGNT_ID", length = 30)
	public String getWebsrvAgntId() {
		return this.websrvAgntId;
	}

	public void setWebsrvAgntId(String websrvAgntId) {
		this.websrvAgntId = websrvAgntId;
	}

	@Column(name = "WEBSRV_AGNT_PIN", length = 30)
	public String getWebsrvAgntPin() {
		return this.websrvAgntPin;
	}

	public void setWebsrvAgntPin(String websrvAgntPin) {
		this.websrvAgntPin = websrvAgntPin;
	}

	@Column(name = "WEBSRV_IND", length = 1)
	public String getWebsrvInd() {
		return this.websrvInd;
	}

	public void setWebsrvInd(String websrvInd) {
		this.websrvInd = websrvInd;
	}

	@Column(name = "TIMEZONE", precision = 22, scale = 0)
	public BigDecimal getTimezone() {
		return this.timezone;
	}

	public void setTimezone(BigDecimal timezone) {
		this.timezone = timezone;
	}

	@Column(name = "EXCHANGE_ID", length = 20)
	public String getExchangeId() {
		return this.exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "CREATOR", length = 15)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "MODIFIER", length = 15)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankApplicability")
	public Set<AccountBalance> getAccountBanalce() {
		return accountBanalce;
	}

	public void setAccountBanalce(Set<AccountBalance> accountBanalce) {
		this.accountBanalce = accountBanalce;
	}*/
	
	@Column(name = "REMARKS", length = 1)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

}
