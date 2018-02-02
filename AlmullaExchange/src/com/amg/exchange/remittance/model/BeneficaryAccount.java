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
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ServiceGroupMaster;


@Entity
@Table(name = "EX_BENEFICARY_ACCOUNT")
public class BeneficaryAccount implements Serializable {
	
	
	private static final long serialVersionUID = 2315791709068216697L;
	
	private BigDecimal beneficaryAccountSeqId;
	private CountryMaster beneApplicationCountry;
	private CountryMaster beneficaryCountry;
	private BeneficaryMaster beneficaryMaster;
	private BankMaster bank;
	private BankBranch bankBranch;
	private String bankAccountNumber;
	private CurrencyMaster currencyId;
	private ServiceGroupMaster servicegropupId ;
	private String aliasFirstName;
	private String aliasSecondName;
	private String aliasThirdName;
	private String aliasFourthName;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String bankCode;
	private BigDecimal bankBranchCode;
	private BankMaster serviceProvider;
	private BigDecimal serviceProviderBranchId;
	private String swiftCode;
	private BigDecimal bankAccountTypeId; 
	private String recordStaus;
	private Date lastJavaRemittence;
	private Date lastEmosRemittance; 

	public BeneficaryAccount() {
	}	

	public BeneficaryAccount(BigDecimal beneficaryAccountSeqId,
			CountryMaster beneApplicationCountry,
			CountryMaster beneficaryCountry, BeneficaryMaster beneficaryMaster,
			BankMaster bank, BankBranch bankBranch, String bankAccountNumber,
			CurrencyMaster currencyId, ServiceGroupMaster servicegropupId,
			String aliasFirstName, String aliasSecondName,
			String aliasThirdName, String aliasFourthName, String isActive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String bankCode, BigDecimal bankBranchCode,
			BankMaster serviceProvider, BigDecimal serviceProviderBranchId,
			String swiftCode, BigDecimal bankAccountTypeId,Date lastEmosRemittance) {
		super();
		this.beneficaryAccountSeqId = beneficaryAccountSeqId;
		this.beneApplicationCountry = beneApplicationCountry;
		this.beneficaryCountry = beneficaryCountry;
		this.beneficaryMaster = beneficaryMaster;
		this.bank = bank;
		this.bankBranch = bankBranch;
		this.bankAccountNumber = bankAccountNumber;
		this.currencyId = currencyId;
		this.servicegropupId = servicegropupId;
		this.aliasFirstName = aliasFirstName;
		this.aliasSecondName = aliasSecondName;
		this.aliasThirdName = aliasThirdName;
		this.aliasFourthName = aliasFourthName;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.bankCode = bankCode;
		this.bankBranchCode = bankBranchCode;
		this.serviceProvider = serviceProvider;
		this.serviceProviderBranchId = serviceProviderBranchId;
		this.swiftCode = swiftCode;
		this.bankAccountTypeId = bankAccountTypeId;
		this.lastEmosRemittance=lastEmosRemittance;
	}

	@Id
	@GeneratedValue(generator="ex_beneficary_account_seq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_beneficary_account_seq",sequenceName="EX_BENEFICARY_ACCOUNT_SEQ",allocationSize=1)
	@Column(name = "BENEFICARY_ACCOUNT_SEQ_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBeneficaryAccountSeqId() {
		return beneficaryAccountSeqId;
	}
	public void setBeneficaryAccountSeqId(BigDecimal beneficaryAccountSeqId) {
		this.beneficaryAccountSeqId = beneficaryAccountSeqId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")	
	public CountryMaster getBeneApplicationCountry() {
		return beneApplicationCountry;
	}
	public void setBeneApplicationCountry(CountryMaster beneApplicationCountry) {
		this.beneApplicationCountry = beneApplicationCountry;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BENEFICARY_COUNTRY")
	public CountryMaster getBeneficaryCountry() {
		return beneficaryCountry;
	}
	public void setBeneficaryCountry(CountryMaster beneficaryCountry) {
		this.beneficaryCountry = beneficaryCountry;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BENEFICARY_MASTER_SEQ_ID")
	public BeneficaryMaster getBeneficaryMaster() {
		return beneficaryMaster;
	}
	public void setBeneficaryMaster(BeneficaryMaster beneficaryMaster) {
		this.beneficaryMaster = beneficaryMaster;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBank() {
		return bank;
	}
	public void setBank(BankMaster bank) {
		this.bank = bank;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BANK_BRANCH_ID")
	public BankBranch getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_GROUP_ID")
	public ServiceGroupMaster getServicegropupId() {
		return servicegropupId;
	}
	public void setServicegropupId(ServiceGroupMaster servicegropupId) {
		this.servicegropupId = servicegropupId;
	}

	@Column(name = "BANK_ACCOUNT_NUMBER")
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(CurrencyMaster currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name="ALIAS_FIRST_NAME")
	public String getAliasFirstName() {
		return aliasFirstName;
	}
	public void setAliasFirstName(String aliasFirstName) {
		this.aliasFirstName = aliasFirstName;
	}
	
	@Column(name="ALIAS_SECOND_NAME")
	public String getAliasSecondName() {
		return aliasSecondName;
	}
	public void setAliasSecondName(String aliasSecondName) {
		this.aliasSecondName = aliasSecondName;
	}
	
	@Column(name="ALIAS_THIRD_NAME")
	public String getAliasThirdName() {
		return aliasThirdName;
	}
	public void setAliasThirdName(String aliasThirdName) {
		this.aliasThirdName = aliasThirdName;
	}
	
	@Column(name="ALIAS_FOURTH_NAME")
	public String getAliasFourthName() {
		return aliasFourthName;
	}
	public void setAliasFourthName(String aliasFourthName) {
		this.aliasFourthName = aliasFourthName;
	}

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name="BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name="BRANCH_CODE")
	public BigDecimal getBankBranchCode() {
		return bankBranchCode;
	}
	public void setBankBranchCode(BigDecimal bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SERVICE_PROVIDER")
	public BankMaster getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(BankMaster serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	@Column(name="BANK_ACCOUNT_TYPE_ID")
	public BigDecimal getBankAccountTypeId() {
		return bankAccountTypeId;
	}
	public void setBankAccountTypeId(BigDecimal bankAccountTypeId) {
		this.bankAccountTypeId = bankAccountTypeId;
	}

	@Column(name="SWIFT_BIC")
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	
	@Column(name="SERVICE_PROVIDER_BRANCH_ID")
	public BigDecimal getServiceProviderBranchId() {
		return serviceProviderBranchId;
	}
	public void setServiceProviderBranchId(BigDecimal serviceProviderBranchId) {
		this.serviceProviderBranchId = serviceProviderBranchId;
	}

	@Column(name="CREATION_TYPE")
	public String getRecordStaus() {
		return recordStaus;
	}

	public void setRecordStaus(String recordStaus) {
		this.recordStaus = recordStaus;
	}

	@Column(name="LAST_JAVA_REMITTANCE")
	public Date getLastJavaRemittence() {
		return lastJavaRemittence;
	}

	public void setLastJavaRemittence(Date lastJavaRemittence) {
		this.lastJavaRemittence = lastJavaRemittence;
	}
	@Column(name="LAST_EMOS_REMITTANCE")
	public Date getLastEmosRemittance() {
		return lastEmosRemittance;
	}

	public void setLastEmosRemittance(Date lastEmosRemittance) {
		this.lastEmosRemittance = lastEmosRemittance;
	}
	
	
	
	
}
