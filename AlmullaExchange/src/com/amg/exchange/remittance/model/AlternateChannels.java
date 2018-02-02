package com.amg.exchange.remittance.model;

import java.io.Serializable;
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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;

@Entity
@Table(name="EX_ALTERNATE_CHANNELS")
public class AlternateChannels implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private BigDecimal alternateCheanelId;
	private Customer customerId;
	private String channelId;
	private String serviceProviderId;
	private CountryMaster beneCountryId;
	private BeneficaryMaster beneMasterId;
	private BeneficaryRelationship beneRelationId;
	private BankMaster beneBankId;
	private BankBranch beneBranchId;
	private BeneficaryAccount beneAccountId;
	private LanguageType beneLanguageId;
	private BigDecimal fixedAmount;
	private String otpVerificationMethod;
	private String otpVerifiedBy;
	private Date otpVerifiedDate;
	private String isActive;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	/*private String accountNumber;
	private String beneName;*/
	
	public AlternateChannels() {
		
	}
	
	public AlternateChannels(BigDecimal alternateCheanelId,
			Customer customerId,String channelId,String serviceProviderId,
			CountryMaster beneCountryId,BeneficaryMaster beneMasterId,
			BeneficaryRelationship beneRelationId,BankMaster beneBankId,BankBranch beneBranchId,
			BeneficaryAccount beneAccountId,LanguageType beneLanguageId,
			BigDecimal fixedAmount,String otpVerificationMethod,
			String otpVerifiedBy,Date otpVerifiedDate,
			String isActive, Date createdDate, String createdBy,
			Date modifiedDate, String modifiedBy){
			super();
			this.alternateCheanelId = alternateCheanelId;
			this.customerId = customerId;
			this.channelId = channelId;
			this.serviceProviderId = serviceProviderId;
			this.beneCountryId = beneCountryId;
			this.beneMasterId = beneMasterId;
			this.beneRelationId = beneRelationId;
			this.beneBankId = beneBankId;
			this.beneBranchId = beneBranchId;
			this.beneAccountId = beneAccountId;
			this.beneLanguageId = beneLanguageId;
			this.fixedAmount = fixedAmount;
			this.otpVerificationMethod = otpVerificationMethod;
			this.otpVerifiedBy = otpVerifiedBy;
			this.otpVerifiedDate = otpVerifiedDate;
			this.isActive = isActive;
			this.createdDate = createdDate;
			this.createdBy = createdBy;
			this.modifiedDate = modifiedDate;
			this.modifiedBy = modifiedBy;
			//this.accountNumber=accountNumber;
			//this.beneName=beneName;
		
	}
	
	@Id
	@GeneratedValue(generator="ex_alternate_channels_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_alternate_channels_seq",sequenceName="EX_ALTERNATE_CHANNELS_SEQ",allocationSize=1)
	@Column(name="ALTERNATE_CHANNELS_ID",unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getAlternateCheanelId() {
		return alternateCheanelId;
	}

	public void setAlternateCheanelId(BigDecimal alternateCheanelId) {
		this.alternateCheanelId = alternateCheanelId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID")
	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	
	@Column(name="CHANNEL_ID")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Column(name="SERVICE_PROVIDER_ID")
	public String getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(String serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BENE_BRANCH_ID")
	public BankBranch getBeneBranchId() {
		return beneBranchId;
	}

	public void setBeneBranchId(BankBranch beneBranchId) {
		this.beneBranchId = beneBranchId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BENE_COUNTRY_ID")
	public CountryMaster getBeneCountryId() {
		return beneCountryId;
	}

	public void setBeneCountryId(CountryMaster beneCountryId) {
		this.beneCountryId = beneCountryId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BENEFICARY_MASTER_SEQ_ID")
	public BeneficaryMaster getBeneMasterId() {
		return beneMasterId;
	}

	public void setBeneMasterId(BeneficaryMaster beneMasterId) {
		this.beneMasterId = beneMasterId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BENEFICARY_RELATIONSHIP_SEQ_ID")
	public BeneficaryRelationship getBeneRelationId() {
		return beneRelationId;
	}

	public void setBeneRelationId(BeneficaryRelationship beneRelationId) {
		this.beneRelationId = beneRelationId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BENE_BANK_ID")
	public BankMaster getBeneBankId() {
		return beneBankId;
	}

	public void setBeneBankId(BankMaster beneBankId) {
		this.beneBankId = beneBankId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BENEFICARY_ACCOUNT_SEQ_ID")
	public BeneficaryAccount getBeneAccountId() {
		return beneAccountId;
	}

	public void setBeneAccountId(BeneficaryAccount beneAccountId) {
		this.beneAccountId = beneAccountId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BENE_LANGUAGE_ID")
	public LanguageType getBeneLanguageId() {
		return beneLanguageId;
	}

	public void setBeneLanguageId(LanguageType beneLanguageId) {
		this.beneLanguageId = beneLanguageId;
	}

	@Column(name="FIXED_AMOUNT")
	public BigDecimal getFixedAmount() {
		return fixedAmount;
	}

	public void setFixedAmount(BigDecimal fixedAmount) {
		this.fixedAmount = fixedAmount;
	}

	@Column(name="OTP_VERIFICATION_METHOD")
	public String getOtpVerificationMethod() {
		return otpVerificationMethod;
	}

	public void setOtpVerificationMethod(String otpVerificationMethod) {
		this.otpVerificationMethod = otpVerificationMethod;
	}

	@Column(name="OTP_VERIFIED_BY")
	public String getOtpVerifiedBy() {
		return otpVerifiedBy;
	}

	public void setOtpVerifiedBy(String otpVerifiedBy) {
		this.otpVerifiedBy = otpVerifiedBy;
	}

	@Column(name="OTP_VERIFIED_DATE")
	public Date getOtpVerifiedDate() {
		return otpVerifiedDate;
	}

	public void setOtpVerifiedDate(Date otpVerifiedDate) {
		this.otpVerifiedDate = otpVerifiedDate;
	}

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/*@Column(name="ACCOUNT_NUMBER")
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	@Column(name="BENE_NAME")
	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}*/
	
	

}
