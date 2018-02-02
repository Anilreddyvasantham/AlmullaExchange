package com.amg.exchange.treasury.model;

// default package
// Generated May 23, 2014 5:18:25  Created by Chennai ODC

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.Remittance;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;

/**
 * BankMaster Created by Chennai ODC
 */
@Entity
@Table(name = "EX_BANK_MASTER")
public class BankMaster implements java.io.Serializable {

	/**
	 * Generated Serial UID 
	 */
	private static final long serialVersionUID = 3948079494013331401L;
	private BigDecimal bankId;
	private CountryMaster fsCountryMaster;
	//private StateMaster fsStateMaster;
//	private CityMaster fsCityMaster;
	//18/10/2014 we removed FS_Application_CountryID from Bank Master Table
	//private CountryMaster fsApplicationCountryMaster;
//private DistrictMaster fsDistrictMaster;
	//private CurrencyMaster currencyMaster;
	private String bankCode;
	private String bankFullName;
	private String bankShortName;
	private String address1;
	private String address2;
	private String zipCode;
	private String teleponeNo;
	private String faxNo;
	private String email;
	private String recordStatus;
	private String bankFullNameAr;
	private String bankShortNameAr;
	private String address1Ar;
	private String address2Ar;
	private String languageInd;
	private String fileBranch;
	private String fileRemitMode;
	private String fileAlls;
	private BigDecimal ifscLen;
	private String reutersBankName;
	private String creator;
	private Date createDate;
	private Date updateDate;
	private String modifier;
	private String micrCode;	
	private String allowNoBank;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String splitIndicator;
	private String bankBranchCheck;
	/*
	 * private Set exBankContactDetailses = new HashSet(0); private Set
	 * exBankAccountLengths = new HashSet(0); private Set exBankBranches = new
	 * HashSet(0); private Set exBankAccounts = new HashSet(0); private Set
	 * exBankApplicabilities = new HashSet(0); private Set
	 * exBankAccountDetailses = new HashSet(0);
	 */
	private List<BankContactDetails> exBankContactDetailses = new ArrayList<BankContactDetails>();
	private List<BankAccountLength> exBankAccountLengths = new ArrayList<BankAccountLength>();
	private List<BankBranch> exBankBranches = new ArrayList<BankBranch>();
	private List<BankAccount> exBankAccounts = new ArrayList<BankAccount>();
	private List<BankApplicability> exBankApplicabilities = new ArrayList<BankApplicability>();
	private List<BankAccountDetails> exBankAccountDetailses = new ArrayList<BankAccountDetails>();
	private Set<SupplierInstructionMaster> exSupplierInstructionMasters = new HashSet <SupplierInstructionMaster>(0);
	private Set<SpecialDeal> exSpecialDeals = new HashSet<SpecialDeal>(0);
	
	
/*	private Set<FundEstimation> exFundEstimation = new HashSet<FundEstimation>(0);
	private Set<FundEstimationDetails> exFundEstimtaionDetails = new HashSet<FundEstimationDetails>(0);
	private Set<FundEstimationDays> exFundEstimationDays = new HashSet<FundEstimationDays>(0);
	*/
	
	private Set<DailyBalance> dailyBalance = new HashSet<DailyBalance>(0);
	private Set<DailyAccountBalance> dailyAccountBalance = new HashSet<DailyAccountBalance>(0);
	
	private List<CustomerSpecialDealRequest> customerSpeacialDealReqBankMaster = new ArrayList<CustomerSpecialDealRequest>();
	private List<AccountBalance> accountBalanc = new ArrayList<AccountBalance>();
	private Set<TreasuryDealHeader> exDealHeader = new HashSet<TreasuryDealHeader>(0);
	private Set<TreasuryDealDetail> exDealDetail = new HashSet<TreasuryDealDetail>(0);
	
	private Set<TreasuryCustomerSupplier>  dealSupplierBank = new HashSet<TreasuryCustomerSupplier>();
	private List<ExchangeRate> exchangeRate = new ArrayList<ExchangeRate>();
	private List<PipsMaster> pipsMaster = new ArrayList<PipsMaster>();
	/*private Set<BankLength> bankLength=new HashSet<BankLength>();*/
	private List<BankServiceRule> bankServiceRule=new ArrayList<BankServiceRule>();
	private List<SpecialRateRequest> specialRateRequest=new ArrayList<SpecialRateRequest>();
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
	private Set<RemittanceApplication> exRemittanceApplication = new HashSet<RemittanceApplication>(0);
	private List<CustomerBank> customerBank=new ArrayList<CustomerBank>();
	//private List<SwiftMaster> swiftMaster=new ArrayList<SwiftMaster>();
	
	public BankMaster() {
		super();
	}

	public BankMaster(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	public BankMaster(BigDecimal bankId, CountryMaster fsCountryMaster,
			String bankCode, String bankFullName, String bankShortName,
			String address1, String address2, String zipCode,
			String teleponeNo, String faxNo, String email, String recordStatus,
			String bankFullNameAr, String bankShortNameAr, String address1Ar,
			String address2Ar, String languageInd, String fileBranch,
			String fileRemitMode, String fileAlls, BigDecimal ifscLen,
			String reutersBankName, String creator, Date createDate,
			Date updateDate, String modifier, String micrCode,
			String allowNoBank, String approvedBy, Date approvedDate,
			String remarks, String splitIndicator, String bankBranchCheck,
			List<BankContactDetails> exBankContactDetailses,
			List<BankAccountLength> exBankAccountLengths,
			List<BankBranch> exBankBranches, List<BankAccount> exBankAccounts,
			List<BankApplicability> exBankApplicabilities,
			List<BankAccountDetails> exBankAccountDetailses,
			Set<SupplierInstructionMaster> exSupplierInstructionMasters,
			Set<SpecialDeal> exSpecialDeals, Set<DailyBalance> dailyBalance,
			Set<DailyAccountBalance> dailyAccountBalance,
			List<CustomerSpecialDealRequest> customerSpeacialDealReqBankMaster,
			List<AccountBalance> accountBalanc,
			Set<TreasuryDealHeader> exDealHeader,
			Set<TreasuryDealDetail> exDealDetail,
			Set<TreasuryCustomerSupplier> dealSupplierBank,
			List<ExchangeRate> exchangeRate, List<PipsMaster> pipsMaster,
			List<BankServiceRule> bankServiceRule,
			List<SpecialRateRequest> specialRateRequest,
			Set<Remittance> exRemittance,
			Set<RemittanceApplication> exRemittanceApplication,
			List<CustomerBank> customerBank) {
		super();
		this.bankId = bankId;
		this.fsCountryMaster = fsCountryMaster;
		this.bankCode = bankCode;
		this.bankFullName = bankFullName;
		this.bankShortName = bankShortName;
		this.address1 = address1;
		this.address2 = address2;
		this.zipCode = zipCode;
		this.teleponeNo = teleponeNo;
		this.faxNo = faxNo;
		this.email = email;
		this.recordStatus = recordStatus;
		this.bankFullNameAr = bankFullNameAr;
		this.bankShortNameAr = bankShortNameAr;
		this.address1Ar = address1Ar;
		this.address2Ar = address2Ar;
		this.languageInd = languageInd;
		this.fileBranch = fileBranch;
		this.fileRemitMode = fileRemitMode;
		this.fileAlls = fileAlls;
		this.ifscLen = ifscLen;
		this.reutersBankName = reutersBankName;
		this.creator = creator;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.modifier = modifier;
		this.micrCode = micrCode;
		this.allowNoBank = allowNoBank;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.splitIndicator = splitIndicator;
		this.bankBranchCheck = bankBranchCheck;
		this.exBankContactDetailses = exBankContactDetailses;
		this.exBankAccountLengths = exBankAccountLengths;
		this.exBankBranches = exBankBranches;
		this.exBankAccounts = exBankAccounts;
		this.exBankApplicabilities = exBankApplicabilities;
		this.exBankAccountDetailses = exBankAccountDetailses;
		this.exSupplierInstructionMasters = exSupplierInstructionMasters;
		this.exSpecialDeals = exSpecialDeals;
		this.dailyBalance = dailyBalance;
		this.dailyAccountBalance = dailyAccountBalance;
		this.customerSpeacialDealReqBankMaster = customerSpeacialDealReqBankMaster;
		this.accountBalanc = accountBalanc;
		this.exDealHeader = exDealHeader;
		this.exDealDetail = exDealDetail;
		this.dealSupplierBank = dealSupplierBank;
		this.exchangeRate = exchangeRate;
		this.pipsMaster = pipsMaster;
		this.bankServiceRule = bankServiceRule;
		this.specialRateRequest = specialRateRequest;
		this.exRemittance = exRemittance;
		this.exRemittanceApplication = exRemittanceApplication;
		this.customerBank = customerBank;
	}

	@Id
	@GeneratedValue(generator="ex_bank_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_master_seq",sequenceName="EX_BANK_MASTER_SEQ",allocationSize=1)
	@Column(name = "BANK_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBankId() {
		return this.bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID")
	public StateMaster getFsStateMaster() {
		return this.fsStateMaster;
	}

	public void setFsStateMaster(StateMaster fsStateMaster) {
		this.fsStateMaster = fsStateMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	public CityMaster getFsCityMaster() {
		return this.fsCityMaster;
	}

	public void setFsCityMaster(CityMaster fsCityMaster) {
		this.fsCityMaster = fsCityMaster;
	}*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}
	
	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}
	
	/*
	 * 
	 * 
	 * Application country coming from Bank Applicability table
	 */
	//18/10/2014  we removed FS_Application_CountryID from Bank Master Table
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsApplicationCountryMaster() {
		return fsApplicationCountryMaster;
	}*/

	//18/10/2014  we removed FS_Application_CountryID from Bank Master Table
/*	public void setFsApplicationCountryMaster(CountryMaster fsApplicationCountryMaster) {
		this.fsApplicationCountryMaster = fsApplicationCountryMaster;
	}*/
	
	@Column(name = "BANK_CODE", length = 10)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "BANK_FULL_NAME", unique=true, length = 150)
	public String getBankFullName() {
		return this.bankFullName;
	}

	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}

	@Column(name = "BANK_SHORT_NAME", length = 80)
	public String getBankShortName() {
		return this.bankShortName;
	}

	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}

	@Column(name = "ADDRESS1", length = 100)
	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "ADDRESS2", length = 100)
	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name = "ZIP_CODE", length = 15)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "TELEPONE_NO", length = 30)
	public String getTeleponeNo() {
		return this.teleponeNo;
	}

	public void setTeleponeNo(String teleponeNo) {
		this.teleponeNo = teleponeNo;
	}

	@Column(name = "FAX_NO", length = 30)
	public String getFaxNo() {
		return this.faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	@Column(name = "EMAIL", length = 30)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getRecordStatus() {
		return this.recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	

	@Column(name = "ALLOW_NO_BANK", length = 1)
	public String getAllowNoBank() {
		return allowNoBank;
	}

	public void setAllowNoBank(String allowNoBank) {
		this.allowNoBank = allowNoBank;
	}
	

	@Column(name = "BANK_FULL_NAME_AR", length = 150)
	public String getBankFullNameAr() {
		return this.bankFullNameAr;
	}

	public void setBankFullNameAr(String bankFullNameAr) {
		this.bankFullNameAr = bankFullNameAr;
	}

	@Column(name = "BANK_SHORT_NAME_AR", length = 100)
	public String getBankShortNameAr() {
		return this.bankShortNameAr;
	}

	public void setBankShortNameAr(String bankShortNameAr) {
		this.bankShortNameAr = bankShortNameAr;
	}

	@Column(name = "ADDRESS1_AR", length = 120)
	public String getAddress1Ar() {
		return this.address1Ar;
	}

	public void setAddress1Ar(String address1Ar) {
		this.address1Ar = address1Ar;
	}

	@Column(name = "ADDRESS2_AR", length = 120)
	public String getAddress2Ar() {
		return this.address2Ar;
	}

	public void setAddress2Ar(String address2Ar) {
		this.address2Ar = address2Ar;
	}

	@Column(name = "LANGUAGE_IND", length = 1)
	public String getLanguageInd() {
		return this.languageInd;
	}

	public void setLanguageInd(String languageInd) {
		this.languageInd = languageInd;
	}

	@Column(name = "FILE_BRANCH", length = 1)
	public String getFileBranch() {
		return this.fileBranch;
	}

	public void setFileBranch(String fileBranch) {
		this.fileBranch = fileBranch;
	}

	@Column(name = "FILE_REMIT_MODE", length = 1)
	public String getFileRemitMode() {
		return this.fileRemitMode;
	}

	public void setFileRemitMode(String fileRemitMode) {
		this.fileRemitMode = fileRemitMode;
	}

	@Column(name = "FILE_ALLS", length = 1)
	public String getFileAlls() {
		return this.fileAlls;
	}

	public void setFileAlls(String fileAlls) {
		this.fileAlls = fileAlls;
	}

	@Column(name = "IFSC_LEN", precision = 22, scale = 0)
	public BigDecimal getIfscLen() {
		return this.ifscLen;
	}

	public void setIfscLen(BigDecimal ifscLen) {
		this.ifscLen = ifscLen;
	}

	@Column(name = "REUTERS_BANK_NAME", length = 100)
	public String getReutersBankName() {
		return this.reutersBankName;
	}

	public void setReutersBankName(String reutersBankName) {
		this.reutersBankName = reutersBankName;
	}

	@Column(name = "CREATOR", length = 15)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	@Column(name = "MODIFIER", length = 15)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankMaster")
	public List<BankContactDetails> getExBankContactDetailses() {
		return exBankContactDetailses;
	}

	public void setExBankContactDetailses(
			List<BankContactDetails> exBankContactDetailses) {
		this.exBankContactDetailses = exBankContactDetailses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankMaster")
	public List<BankAccountLength> getExBankAccountLengths() {
		return exBankAccountLengths;
	}

	public void setExBankAccountLengths(
			List<BankAccountLength> exBankAccountLengths) {
		this.exBankAccountLengths = exBankAccountLengths;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankMaster")
	public List<BankBranch> getExBankBranches() {
		return exBankBranches;
	}

	public void setExBankBranches(List<BankBranch> exBankBranches) {
		this.exBankBranches = exBankBranches;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankMaster")
	public List<BankAccount> getExBankAccounts() {
		return exBankAccounts;
	}

	public void setExBankAccounts(List<BankAccount> exBankAccounts) {
		this.exBankAccounts = exBankAccounts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankMaster")
	public List<BankApplicability> getExBankApplicabilities() {
		return exBankApplicabilities;
	}

	public void setExBankApplicabilities(
			List<BankApplicability> exBankApplicabilities) {
		this.exBankApplicabilities = exBankApplicabilities;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankMaster")
	public List<BankAccountDetails> getExBankAccountDetailses() {
		return exBankAccountDetailses;
	}

	public void setExBankAccountDetailses(
			List<BankAccountDetails> exBankAccountDetailses) {
		this.exBankAccountDetailses = exBankAccountDetailses;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRICT_ID", nullable = false)
	public DistrictMaster getFsDistrictMaster() {
		return fsDistrictMaster;
	}

	public void setFsDistrictMaster(DistrictMaster fsDistrictMaster) {
		this.fsDistrictMaster = fsDistrictMaster;
	}*/
	/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID", nullable = false)
	public CurrencyMaster getCurrencyMaster() {
		return currencyMaster;
	}
	public void setCurrencyMaster(CurrencyMaster currencyMaster) {
		this.currencyMaster = currencyMaster;
	}*/

	@Column(name = "MICR_CODE")
	public String getMicrCode() {
		return micrCode;
	}

	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankMaster")
	public Set<SupplierInstructionMaster> getExSupplierInstructionMasters() {
		return this.exSupplierInstructionMasters;
	}

	public void setExSupplierInstructionMasters(Set<SupplierInstructionMaster> exSupplierInstructionMasters) {
		this.exSupplierInstructionMasters = exSupplierInstructionMasters;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankMaster")
	public Set<SpecialDeal> getExSpecialDeals() {
		return this.exSpecialDeals;
	}

	public void setExSpecialDeals(Set<SpecialDeal> exSpecialDeals) {
		this.exSpecialDeals = exSpecialDeals;
	}
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankMaster")
	public Set<FundEstimation> getExFundEstimation() {
		return exFundEstimation;
	}

	public void setExFundEstimation(Set<FundEstimation> exFundEstimation) {
		this.exFundEstimation = exFundEstimation;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankMaster")
	public Set<FundEstimationDetails> getExFundEstimtaionDetails() {
		return exFundEstimtaionDetails;
	}
	public void setExFundEstimtaionDetails(Set<FundEstimationDetails> exFundEstimtaionDetails) {
		this.exFundEstimtaionDetails = exFundEstimtaionDetails;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankMaster")
	public Set<FundEstimationDays> getExFundEstimationDays() {
		return exFundEstimationDays;
	}

	public void setExFundEstimationDays(Set<FundEstimationDays> exFundEstimationDays) {
		this.exFundEstimationDays = exFundEstimationDays;
	}*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankMaster")
	public Set<DailyBalance> getDailyBalance() {
		return dailyBalance;
	}

	public void setDailyBalance(Set<DailyBalance> dailyBalance) {
		this.dailyBalance = dailyBalance;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankMaster")
	public Set<DailyAccountBalance> getDailyAccountBalance() {
		return dailyAccountBalance;
	}

	public void setDailyAccountBalance(Set<DailyAccountBalance> dailyAccountBalance) {
		this.dailyAccountBalance = dailyAccountBalance;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerSpeacialDealReqBankMaster")
	public List<CustomerSpecialDealRequest> getCustomerSpeacialDealReqBankMaster() {
		return customerSpeacialDealReqBankMaster;
	}

	public void setCustomerSpeacialDealReqBankMaster(
			List<CustomerSpecialDealRequest> customerSpeacialDealReqBankMaster) {
		this.customerSpeacialDealReqBankMaster = customerSpeacialDealReqBankMaster;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankMaster")
	public List<AccountBalance> getAccountBalanc() {
		return accountBalanc;
	}

	public void setAccountBalanc(List<AccountBalance> accountBalanc) {
		this.accountBalanc = accountBalanc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exBankMaster")
	public Set<TreasuryDealHeader> getExDealHeader() {
		return exDealHeader;
	}

	public void setExDealHeader(Set<TreasuryDealHeader> exDealHeader) {
		this.exDealHeader = exDealHeader;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryDealBankMaster")
	public Set<TreasuryDealDetail> getExDealDetail() {
		return exDealDetail;
	}

	public void setExDealDetail(Set<TreasuryDealDetail> exDealDetail) {
		this.exDealDetail = exDealDetail;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankMaster")
	public Set<TreasuryCustomerSupplier> getDealSupplierBank() {
		return dealSupplierBank;
	}

	public void setDealSupplierBank(Set<TreasuryCustomerSupplier> dealSupplierBank) {
		this.dealSupplierBank = dealSupplierBank;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankMaster")
	public List<ExchangeRate> getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(List<ExchangeRate> exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankMaster")
	public List<PipsMaster> getPipsMaster() {
		return pipsMaster;
	}

	public void setPipsMaster(List<PipsMaster> pipsMaster) {
		this.pipsMaster = pipsMaster;
	}

/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankMaster")
	public Set<BankLength> getBankLength() {
		return bankLength;
	}

	public void setBankLength(Set<BankLength> bankLength) {
		this.bankLength = bankLength;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankId")
	public List<BankServiceRule> getBankServiceRule() {
		return bankServiceRule;
	}

	public void setBankServiceRule(List<BankServiceRule> bankServiceRule) {
		this.bankServiceRule = bankServiceRule;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="fsBankMaster")
	public List<SpecialRateRequest> getSpecialRateRequest() {
		return specialRateRequest;
	}

	public void setSpecialRateRequest(
			List<SpecialRateRequest> specialRateRequest) {
		this.specialRateRequest = specialRateRequest;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="exBankMaster")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}

	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="exBankMaster")
	public Set<RemittanceApplication> getExRemittanceApplication() {
		return exRemittanceApplication;
	}

	public void setExRemittanceApplication(
			Set<RemittanceApplication> exRemittanceApplication) {
		this.exRemittanceApplication = exRemittanceApplication;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBankMaster")
	public List<CustomerBank> getCustomerBank() {
		return customerBank;
	}

	public void setCustomerBank(List<CustomerBank> customerBank) {
		this.customerBank = customerBank;
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
	
	@Column(name = "SPLIT_INDICATOR")
	public String getSplitIndicator() {
		return splitIndicator;
	}

	public void setSplitIndicator(String splitIndicator) {
		this.splitIndicator = splitIndicator;
	}
	 
	@Column(name = "BANK_BRANCH_CHECK")
	public String getBankBranchCheck() {
		return bankBranchCheck;
	}
	public void setBankBranchCheck(String bankBranchCheck) {
		this.bankBranchCheck = bankBranchCheck;
	}
	
}
