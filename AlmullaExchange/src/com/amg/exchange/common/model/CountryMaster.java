package com.amg.exchange.common.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.ApplicationAmlCheck;
import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.Remittance;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.DailyAccountBalance;
import com.amg.exchange.treasury.model.DailyBalance;
import com.amg.exchange.treasury.model.Data;
import com.amg.exchange.treasury.model.Deal;
import com.amg.exchange.treasury.model.DealConclusionType;
import com.amg.exchange.treasury.model.DealPurchase;
import com.amg.exchange.treasury.model.DealSupplier;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.model.FundProjection;
import com.amg.exchange.treasury.model.Message;
import com.amg.exchange.treasury.model.MonthAccountBalance;
import com.amg.exchange.treasury.model.OutrightSwapMaster;
import com.amg.exchange.treasury.model.PipsMaster;
import com.amg.exchange.treasury.model.SpecialDeal;
import com.amg.exchange.treasury.model.SupplierMaster;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;

/*******************************************************************************************************************
 * File : CountryMaster.java
 * 
 * Project : AlmullaExchange
 * 
 * Package : com.amg.exchange.model
 * 
 * Created : Date : 29-May-2014 5:00:05 pm By : Justin Vincent Revision:
 * 
 * Last Change: Date : 19-Nov-2014 5:51:05 pm By : Nazish Ehsan Hashmi Revision:
 * 
 * Description: TODO
 ********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_COUNTRY_MASTER")
public class CountryMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal countryId;
	private String countryCode;
	private String countryAlpha2Code;
	private String countryAlpha3Code;
	private String countryIsoCode;
	private String countryTelCode;
	private String countryActive;
	private String businessCountry;
	private List<StateMaster> fsStateMasters = new ArrayList<StateMaster>();
	private List<ContactDetail> fsContactDetails = new ArrayList<ContactDetail>();
	private List<CustomerLogin> fsCustomerLogins = new ArrayList<CustomerLogin>();
	private List<CountryMasterDesc> fsCountryMasterDescs = new ArrayList<CountryMasterDesc>();
	private List<CustomerEmploymentInfo> fsCustomerEmploymentInfos = new ArrayList<CustomerEmploymentInfo>();
	private List<Customer> fsCustomersForNationality = new ArrayList<Customer>();
	private List<Customer> fsCustomersForCountryId = new ArrayList<Customer>();
	private Set<CurrencyMaster> exCurrencyMasters = new HashSet<CurrencyMaster>(
			0);
	private List<RuleApplicationDesc> fsRuleApplicationDescs = new ArrayList<RuleApplicationDesc>();
	private List<BusinessComponentConf> fsBusinessComponentConfs = new ArrayList<BusinessComponentConf>();
	private Set<SupplierMaster> exSupplierMasters = new HashSet<SupplierMaster>(
			0);
	private Set<SpecialDeal> exSpecialDeals = new HashSet<SpecialDeal>(0);
	private Set<OutrightSwapMaster> exOutrightSwapMasters = new HashSet<OutrightSwapMaster>(
			0);
	private Set<MonthAccountBalance> exMonthAccountBalances = new HashSet<MonthAccountBalance>(
			0);
	private Set<Message> exMessages = new HashSet<Message>(0);
	private Set<FundProjection> exFundProjections = new HashSet<FundProjection>(
			0);
	private Set<DealSupplier> exDealSuppliers = new HashSet<DealSupplier>(0);
	private Set<DealPurchase> exDealPurchases = new HashSet<DealPurchase>(0);
	private Set<DealConclusionType> exDealConclusionTypes = new HashSet<DealConclusionType>(
			0);

	/*private Set<FundEstimation> exFundEstimtaionForApplicationCountry = new HashSet<FundEstimation>(
			0);
	private Set<FundEstimation> exFundEstimtaionForBankCountry = new HashSet<FundEstimation>(
			0);

	private Set<FundEstimationDetails> exFundEstimtaionDetailsForApplicationCountry = new HashSet<FundEstimationDetails>(
			0);
	private Set<FundEstimationDetails> exFundEstimtaionDeatilsForBankCountry = new HashSet<FundEstimationDetails>(
			0);

	private Set<FundEstimationDays> exFundEstimationDaysApplicationCountry = new HashSet<FundEstimationDays>(
			0);
	private Set<FundEstimationDays> exFundEstimationDaysBankCountry = new HashSet<FundEstimationDays>(
			0);*/

	private Set<DailyBalance> dailyBalance = new HashSet<DailyBalance>(0);

	private Set<Deal> exDeals = new HashSet<Deal>(0);

	private Set<BankMaster> fsCountryMaster = new HashSet<BankMaster>(0);
	//18/10/2014  we removed FS_Application_CountryID from Bank Master Table
	/*private Set<BankMaster> fsApplicationCountryMaster = new HashSet<BankMaster>(
			0)*/;
	private Set<DailyAccountBalance> exdailyAccountBalance = new HashSet<DailyAccountBalance>(
			0);
	private Set<CustomerSpecialDealRequest> applicationCountryCountryMaster = new HashSet<CustomerSpecialDealRequest>(
			0);
	private Set<CustomerSpecialDealRequest> customerSpeacialDealReqCountryMaster = new HashSet<CustomerSpecialDealRequest>(
			0);
	private Set<TreasuryDealHeader> exDealHeader = new HashSet<TreasuryDealHeader>(
			0);

	private Set<TreasuryDealDetail> exDealDetail = new HashSet<TreasuryDealDetail>(
			0);
	private Set<TreasuryStandardInstruction> exTreasuryStandardIns = new HashSet<TreasuryStandardInstruction>(0);
	
	private Set<DayBookHeader> exDayBook = new HashSet<DayBookHeader>(0);
	
	private Set<ApplicationSetup> exApplicationSetup= new HashSet<ApplicationSetup>(0);
	
	private Set<DayBookDetails> dayBookDetailsList = new HashSet<DayBookDetails>(0);
	
	private List<Data> dataList=new ArrayList<Data>();
	private List<ExchangeRate> exchangeRate=new ArrayList<ExchangeRate>();
	private List<PipsMaster> pipsMaster=new ArrayList<PipsMaster>();
	private List<BeneCountryService> beneCountryServiceMaster=new ArrayList<BeneCountryService>();
	private List<MarketingData> maketlist=new ArrayList<MarketingData>();
	private List<ServiceApplicabilityRule> serviceApplicabilityRule=new ArrayList<ServiceApplicabilityRule>();
	private List<BeneficaryAccount> beneApplicationCountry=new ArrayList<BeneficaryAccount>();
	private List<BeneficaryAccount> beneficaryCountry=new ArrayList<BeneficaryAccount>();
	private List<BankServiceRule> bankServieRule =new ArrayList<BankServiceRule>();
	//private List<SwiftMaster> swiftMaster=new ArrayList<SwiftMaster>();
	private List<InsuranceMaster> insuranceSetUp=new ArrayList<InsuranceMaster>();
	private Set<Remittance> exRemittanceforBankCountry = new HashSet<Remittance>(0);
	private Set<Remittance> exRemittanceforAppCountry = new HashSet<Remittance>(0);
	
	//private Set<RemittanceApplication> fsCountryMasterByCorespondingCountryId = new HashSet<RemittanceApplication>();
	private Set<RemittanceApplication> fsCountryMasterByBankCountryId = new HashSet<RemittanceApplication>();
	private Set<RemittanceApplication> fsCountryMasterByApplicationCountryId = new HashSet<RemittanceApplication>();
	
	//private Set<RemiittanceApplication> fsCountryMasterByBankCountry = new HashSet<RemiittanceApplication>();
	//private Set<RemiittanceApplication> fsCountryMasterByApplicationCountry = new HashSet<RemiittanceApplication>();
	private Set<ApplicationAmlCheck> exApplicationAmlCheck = new HashSet<ApplicationAmlCheck>(0);
	private Set<RemitApplAml> exRemitApplAml = new HashSet<RemitApplAml>(0);
	private Set<AdditionalInstructionData> additionalInstData = new HashSet<AdditionalInstructionData>(0);
	private Set<ServiceApplicabilityRule> serviceAppRuleCountry = new HashSet<ServiceApplicabilityRule>(0);
	private Set<CurrencyOtherInformation> currencyOtherInfo=new HashSet<CurrencyOtherInformation>(0);
	
	private String createdBy;
	private Date createdDate;
	private String isActive;
	
	private Date approvedDate;
	private String approvedBy;
	private BigDecimal spilitIndicator;
	private String stateStatus;
	
	public CountryMaster() {
		super();
	}

	public CountryMaster(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public CountryMaster(
			BigDecimal countryId,
			String countryCode,
			String countryAlpha2Code,
			String countryAlpha3Code,
			String countryIsoCode,
			String countryTelCode,
			String countryActive,
			String businessCountry,
			List<StateMaster> fsStateMasters,
			List<ContactDetail> fsContactDetails,
			List<CustomerLogin> fsCustomerLogins,
			List<CountryMasterDesc> fsCountryMasterDescs,
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos,
			List<Customer> fsCustomersForNationality,
			List<Customer> fsCustomersForCountryId,
			Set<CurrencyMaster> exCurrencyMasters,
			List<RuleApplicationDesc> fsRuleApplicationDescs,
			List<BusinessComponentConf> fsBusinessComponentConfs,
			Set<SupplierMaster> exSupplierMasters,
			Set<SpecialDeal> exSpecialDeals,
			Set<OutrightSwapMaster> exOutrightSwapMasters,
			Set<MonthAccountBalance> exMonthAccountBalances,
			Set<Message> exMessages,
			Set<FundProjection> exFundProjections,
			Set<DealSupplier> exDealSuppliers,
			Set<DealPurchase> exDealPurchases,
			Set<DealConclusionType> exDealConclusionTypes,
			Set<DailyBalance> dailyBalance,
			Set<Deal> exDeals,
			Set<BankMaster> fsCountryMaster,
			Set<DailyAccountBalance> exdailyAccountBalance,
			Set<CustomerSpecialDealRequest> applicationCountryCountryMaster,
			Set<CustomerSpecialDealRequest> customerSpeacialDealReqCountryMaster,
			Set<TreasuryDealHeader> exDealHeader,
			Set<TreasuryDealDetail> exDealDetail,
			Set<TreasuryStandardInstruction> exTreasuryStandardIns,
			Set<DayBookHeader> exDayBook,
			Set<ApplicationSetup> exApplicationSetup,
			Set<DayBookDetails> dayBookDetailsList, List<Data> dataList,
			List<ExchangeRate> exchangeRate, List<PipsMaster> pipsMaster,
			List<BeneCountryService> beneCountryServiceMaster,
			List<MarketingData> maketlist,
			List<ServiceApplicabilityRule> serviceApplicabilityRule,
			List<BeneficaryAccount> beneApplicationCountry,
			List<BeneficaryAccount> beneficaryCountry,
			List<BankServiceRule> bankServieRule,
			List<InsuranceMaster> insuranceSetUp,
			Set<Remittance> exRemittanceforBankCountry,
			Set<Remittance> exRemittanceforAppCountry,
			Set<RemittanceApplication> fsCountryMasterByBankCountryId,
			Set<RemittanceApplication> fsCountryMasterByApplicationCountryId,
			//Set<RemiittanceApplication> fsCountryMasterByBankCountry,
			//Set<RemiittanceApplication> fsCountryMasterByApplicationCountry,
			Set<ApplicationAmlCheck> exApplicationAmlCheck,
			Set<RemitApplAml> exRemitApplAml,
			Set<AdditionalInstructionData> additionalInstData,
			Set<ServiceApplicabilityRule> serviceAppRuleCountry,
			Set<CurrencyOtherInformation> currencyOtherInfo, String createdBy, Date createdDate, String isActive,Date approvedDate, String approvedBy,
			BigDecimal spilitIndicator) {
		super();
		this.countryId = countryId;
		this.countryCode = countryCode;
		this.countryAlpha2Code = countryAlpha2Code;
		this.countryAlpha3Code = countryAlpha3Code;
		this.countryIsoCode = countryIsoCode;
		this.countryTelCode = countryTelCode;
		this.countryActive = countryActive;
		this.businessCountry = businessCountry;
		this.fsStateMasters = fsStateMasters;
		this.fsContactDetails = fsContactDetails;
		this.fsCustomerLogins = fsCustomerLogins;
		this.fsCountryMasterDescs = fsCountryMasterDescs;
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
		this.fsCustomersForNationality = fsCustomersForNationality;
		this.fsCustomersForCountryId = fsCustomersForCountryId;
		this.exCurrencyMasters = exCurrencyMasters;
		this.fsRuleApplicationDescs = fsRuleApplicationDescs;
		this.fsBusinessComponentConfs = fsBusinessComponentConfs;
		this.exSupplierMasters = exSupplierMasters;
		this.exSpecialDeals = exSpecialDeals;
		this.exOutrightSwapMasters = exOutrightSwapMasters;
		this.exMonthAccountBalances = exMonthAccountBalances;
		this.exMessages = exMessages;
		this.exFundProjections = exFundProjections;
		this.exDealSuppliers = exDealSuppliers;
		this.exDealPurchases = exDealPurchases;
		this.exDealConclusionTypes = exDealConclusionTypes;
		this.dailyBalance = dailyBalance;
		this.exDeals = exDeals;
		this.fsCountryMaster = fsCountryMaster;
		this.exdailyAccountBalance = exdailyAccountBalance;
		this.applicationCountryCountryMaster = applicationCountryCountryMaster;
		this.customerSpeacialDealReqCountryMaster = customerSpeacialDealReqCountryMaster;
		this.exDealHeader = exDealHeader;
		this.exDealDetail = exDealDetail;
		this.exTreasuryStandardIns = exTreasuryStandardIns;
		this.exDayBook = exDayBook;
		this.exApplicationSetup = exApplicationSetup;
		this.dayBookDetailsList = dayBookDetailsList;
		this.dataList = dataList;
		this.exchangeRate = exchangeRate;
		this.pipsMaster = pipsMaster;
		this.beneCountryServiceMaster = beneCountryServiceMaster;
		this.maketlist = maketlist;
		this.serviceApplicabilityRule = serviceApplicabilityRule;
		this.beneApplicationCountry = beneApplicationCountry;
		this.beneficaryCountry = beneficaryCountry;
		this.bankServieRule = bankServieRule;
		this.insuranceSetUp = insuranceSetUp;
		this.exRemittanceforBankCountry = exRemittanceforBankCountry;
		this.exRemittanceforAppCountry = exRemittanceforAppCountry;
		this.fsCountryMasterByBankCountryId = fsCountryMasterByBankCountryId;
		this.fsCountryMasterByApplicationCountryId = fsCountryMasterByApplicationCountryId;
		//this.fsCountryMasterByBankCountry = fsCountryMasterByBankCountry;
		//this.fsCountryMasterByApplicationCountry = fsCountryMasterByApplicationCountry;
		this.exApplicationAmlCheck = exApplicationAmlCheck;
		this.exRemitApplAml = exRemitApplAml;
		this.additionalInstData = additionalInstData;
		this.serviceAppRuleCountry = serviceAppRuleCountry;
		this.currencyOtherInfo = currencyOtherInfo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.isActive = isActive;
		this.approvedDate = approvedDate;
		this.approvedBy = approvedBy;
		this.spilitIndicator = spilitIndicator;
	}

	/*@Id
	@TableGenerator(name = "countryid", table = "fs_countryidpk", pkColumnName = "countryidkey", pkColumnValue = "countryidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "countryid")*/
	
	@Id
	@GeneratedValue(generator="fs_country_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_country_master_seq",sequenceName="FS_COUNTRY_MASTER_SEQ",allocationSize=1)
	@Column(name = "COUNTRY_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCountryId() {
		return this.countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	@Column(name = "COUNTRY_CODE", precision = 3, scale = 0)
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name = "COUNTRY_ALPHA2_CODE", length = 2)
	public String getCountryAlpha2Code() {
		return this.countryAlpha2Code;
	}

	public void setCountryAlpha2Code(String countryAlpha2Code) {
		this.countryAlpha2Code = countryAlpha2Code;
	}

	@Column(name = "COUNTRY_ALPHA3_CODE", length = 3)
	public String getCountryAlpha3Code() {
		return this.countryAlpha3Code;
	}

	public void setCountryAlpha3Code(String countryAlpha3Code) {
		this.countryAlpha3Code = countryAlpha3Code;
	}

	@Column(name = "COUNTRY_ISO_CODE", length = 20)
	public String getCountryIsoCode() {
		return this.countryIsoCode;
	}

	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
	}

	@Column(name = "COUNTRY_TEL_CODE", length = 10)
	public String getCountryTelCode() {
		return this.countryTelCode;
	}

	public void setCountryTelCode(String countryTelCode) {
		this.countryTelCode = countryTelCode;
	}

	@Column(name = "COUNTRY_ACTIVE", length = 1)
	public String getCountryActive() {
		return this.countryActive;
	}

	public void setCountryActive(String countryActive) {
		this.countryActive = countryActive;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public List<CustomerLogin> getFsCustomerLogins() {
		return this.fsCustomerLogins;
	}

	public void setFsCustomerLogins(List<CustomerLogin> fsCustomerLogins) {
		this.fsCustomerLogins = fsCustomerLogins;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "fsCountryMaster")
	public List<CountryMasterDesc> getFsCountryMasterDescs() {
		return this.fsCountryMasterDescs;
	}

	public void setFsCountryMasterDescs(
			List<CountryMasterDesc> fsCountryMasterDescs) {
		this.fsCountryMasterDescs = fsCountryMasterDescs;
	}

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster") public
	 * List<Customer> getFsCustomers() { return this.fsCustomers; }
	 * 
	 * public void setFsCustomers(List<Customer> fsCustomers) { this.fsCustomers
	 * = fsCustomers; }
	 */

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public List<RuleApplicationDesc> getFsRuleApplicationDescs() {
		return this.fsRuleApplicationDescs;
	}

	public void setFsRuleApplicationDescs(
			List<RuleApplicationDesc> fsRuleApplicationDescs) {
		this.fsRuleApplicationDescs = fsRuleApplicationDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public List<ContactDetail> getFsContactDetails() {
		return fsContactDetails;
	}

	public void setFsContactDetails(List<ContactDetail> fsContactDetails) {
		this.fsContactDetails = fsContactDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public List<StateMaster> getFsStateMasters() {
		return fsStateMasters;
	}

	public void setFsStateMasters(List<StateMaster> fsStateMasters) {
		this.fsStateMasters = fsStateMasters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public List<CustomerEmploymentInfo> getFsCustomerEmploymentInfos() {
		return fsCustomerEmploymentInfos;
	}

	public void setFsCustomerEmploymentInfos(
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos) {
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMasterByNationality")
	public List<Customer> getFsCustomersForNationality() {
		return fsCustomersForNationality;
	}

	public void setFsCustomersForNationality(
			List<Customer> fsCustomersForNationality) {
		this.fsCustomersForNationality = fsCustomersForNationality;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMasterByCountryId")
	public List<Customer> getFsCustomersForCountryId() {
		return fsCustomersForCountryId;
	}

	public void setFsCustomersForCountryId(
			List<Customer> fsCustomersForCountryId) {
		this.fsCustomersForCountryId = fsCustomersForCountryId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public List<BusinessComponentConf> getFsBusinessComponentConfs() {
		return fsBusinessComponentConfs;
	}

	public void setFsBusinessComponentConfs(
			List<BusinessComponentConf> fsBusinessComponentConfs) {
		this.fsBusinessComponentConfs = fsBusinessComponentConfs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<CurrencyMaster> getExCurrencyMasters() {
		return this.exCurrencyMasters;
	}

	public void setExCurrencyMasters(Set<CurrencyMaster> exCurrencyMasters) {
		this.exCurrencyMasters = exCurrencyMasters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<SupplierMaster> getExSupplierMasters() {
		return this.exSupplierMasters;
	}

	public void setExSupplierMasters(Set<SupplierMaster> exSupplierMasters) {
		this.exSupplierMasters = exSupplierMasters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<SpecialDeal> getExSpecialDeals() {
		return this.exSpecialDeals;
	}

	public void setExSpecialDeals(Set<SpecialDeal> exSpecialDeals) {
		this.exSpecialDeals = exSpecialDeals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<OutrightSwapMaster> getExOutrightSwapMasters() {
		return this.exOutrightSwapMasters;
	}

	public void setExOutrightSwapMasters(
			Set<OutrightSwapMaster> exOutrightSwapMasters) {
		this.exOutrightSwapMasters = exOutrightSwapMasters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<MonthAccountBalance> getExMonthAccountBalances() {
		return this.exMonthAccountBalances;
	}

	public void setExMonthAccountBalances(
			Set<MonthAccountBalance> exMonthAccountBalances) {
		this.exMonthAccountBalances = exMonthAccountBalances;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<Message> getExMessages() {
		return this.exMessages;
	}

	public void setExMessages(Set<Message> exMessages) {
		this.exMessages = exMessages;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<FundProjection> getExFundProjections() {
		return this.exFundProjections;
	}

	public void setExFundProjections(Set<FundProjection> exFundProjections) {
		this.exFundProjections = exFundProjections;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<DealSupplier> getExDealSuppliers() {
		return this.exDealSuppliers;
	}

	public void setExDealSuppliers(Set<DealSupplier> exDealSuppliers) {
		this.exDealSuppliers = exDealSuppliers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<DealPurchase> getExDealPurchases() {
		return this.exDealPurchases;
	}

	public void setExDealPurchases(Set<DealPurchase> exDealPurchases) {
		this.exDealPurchases = exDealPurchases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<DealConclusionType> getExDealConclusionTypes() {
		return this.exDealConclusionTypes;
	}

	public void setExDealConclusionTypes(
			Set<DealConclusionType> exDealConclusionTypes) {
		this.exDealConclusionTypes = exDealConclusionTypes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<Deal> getExDeals() {
		return this.exDeals;
	}

	public void setExDeals(Set<Deal> exDeals) {
		this.exDeals = exDeals;
	}

	@Column(name = "BUSINESS_COUNTRY", length = 1)
	public String getBusinessCountry() {
		return businessCountry;
	}

	public void setBusinessCountry(String businessCountry) {
		this.businessCountry = businessCountry;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "exFundEstimationForApplicationCountry")
	public Set<FundEstimation> getExFundEstimtaionForApplicationCountry() {
		return exFundEstimtaionForApplicationCountry;
	}

	public void setExFundEstimtaionForApplicationCountry(
			Set<FundEstimation> exFundEstimtaionForApplicationCountry) {
		this.exFundEstimtaionForApplicationCountry = exFundEstimtaionForApplicationCountry;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exFundEstimationForBankCountry")
	public Set<FundEstimation> getExFundEstimtaionForBankCountry() {
		return exFundEstimtaionForBankCountry;
	}

	public void setExFundEstimtaionForBankCountry(
			Set<FundEstimation> exFundEstimtaionForBankCountry) {
		this.exFundEstimtaionForBankCountry = exFundEstimtaionForBankCountry;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exFundEstimtaionDetailsForApplicationCountry")
	public Set<FundEstimationDetails> getExFundEstimtaionDetailsForApplicationCountry() {
		return exFundEstimtaionDetailsForApplicationCountry;
	}

	public void setExFundEstimtaionDetailsForApplicationCountry(
			Set<FundEstimationDetails> exFundEstimtaionDetailsForApplicationCountry) {
		this.exFundEstimtaionDetailsForApplicationCountry = exFundEstimtaionDetailsForApplicationCountry;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exFundEstimtaionDeatilsForBankCountry")
	public Set<FundEstimationDetails> getExFundEstimtaionDeatilsForBankCountry() {
		return exFundEstimtaionDeatilsForBankCountry;
	}

	public void setExFundEstimtaionDeatilsForBankCountry(
			Set<FundEstimationDetails> exFundEstimtaionDeatilsForBankCountry) {
		this.exFundEstimtaionDeatilsForBankCountry = exFundEstimtaionDeatilsForBankCountry;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exFundEstimationDaysApplicationCountry")
	public Set<FundEstimationDays> getExFundEstimationDaysApplicationCountry() {
		return exFundEstimationDaysApplicationCountry;
	}

	public void setExFundEstimationDaysApplicationCountry(
			Set<FundEstimationDays> exFundEstimationDaysApplicationCountry) {
		this.exFundEstimationDaysApplicationCountry = exFundEstimationDaysApplicationCountry;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exFundEstimationDaysBankCountry")
	public Set<FundEstimationDays> getExFundEstimationDaysBankCountry() {
		return exFundEstimationDaysBankCountry;
	}

	public void setExFundEstimationDaysBankCountry(
			Set<FundEstimationDays> exFundEstimationDaysBankCountry) {
		this.exFundEstimationDaysBankCountry = exFundEstimationDaysBankCountry;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationCountry")
	public Set<DailyBalance> getDailyBalance() {
		return dailyBalance;
	}

	public void setDailyBalance(Set<DailyBalance> dailyBalance) {
		this.dailyBalance = dailyBalance;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<BankMaster> getFsCountryMaster() {
		return fsCountryMaster;
	}

	public void setFsCountryMaster(Set<BankMaster> fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	/*18/10/2014  we removed FS_Application_CountryID from Bank Master Table
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsApplicationCountryMaster")
	public Set<BankMaster> getFsApplicationCountryMaster() {
		return fsApplicationCountryMaster;
	}*/

	/*18/10/2014  we removed FS_Application_CountryID from Bank Master Table
	public void setFsApplicationCountryMaster(
			Set<BankMaster> fsApplicationCountryMaster) {
		this.fsApplicationCountryMaster = fsApplicationCountryMaster;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<DailyAccountBalance> getExdailyAccountBalance() {
		return exdailyAccountBalance;
	}

	public void setExdailyAccountBalance(
			Set<DailyAccountBalance> exdailyAccountBalance) {
		this.exdailyAccountBalance = exdailyAccountBalance;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationCountryCountryMaster")
	public Set<CustomerSpecialDealRequest> getApplicationCountryCountryMaster() {
		return applicationCountryCountryMaster;
	}

	public void setApplicationCountryCountryMaster(
			Set<CustomerSpecialDealRequest> applicationCountryCountryMaster) {
		this.applicationCountryCountryMaster = applicationCountryCountryMaster;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerSpeacialDealReqCountryMaster")
	public Set<CustomerSpecialDealRequest> getCustomerSpeacialDealReqCountryMaster() {
		return customerSpeacialDealReqCountryMaster;
	}

	public void setCustomerSpeacialDealReqCountryMaster(
			Set<CustomerSpecialDealRequest> customerSpeacialDealReqCountryMaster) {
		this.customerSpeacialDealReqCountryMaster = customerSpeacialDealReqCountryMaster;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<TreasuryDealHeader> getExDealHeader() {
		return exDealHeader;
	}

	public void setExDealHeader(Set<TreasuryDealHeader> exDealHeader) {
		this.exDealHeader = exDealHeader;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryDealCountryMaster")
	public Set<TreasuryDealDetail> getExDealDetail() {
		return exDealDetail;
	}

	public void setExDealDetail(Set<TreasuryDealDetail> exDealDetail) {
		this.exDealDetail = exDealDetail;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryCountryMaster")
	public Set<TreasuryStandardInstruction> getExTreasuryStandardIns() {
		return exTreasuryStandardIns;
	}

	public void setExTreasuryStandardIns(
			Set<TreasuryStandardInstruction> exTreasuryStandardIns) {
		this.exTreasuryStandardIns = exTreasuryStandardIns;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dayBookCountryMaster")
	public Set<DayBookHeader> getExDayBook() {
		return exDayBook;
	}

	public void setExDayBook(Set<DayBookHeader> exDayBook) {
		this.exDayBook = exDayBook;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appSetupCountryMaster")
	public Set<ApplicationSetup> getExApplicationSetup() {
		return exApplicationSetup;
	}

	public void setExApplicationSetup(Set<ApplicationSetup> exApplicationSetup) {
		this.exApplicationSetup = exApplicationSetup;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dayBookCountryMaster")
	public Set<DayBookDetails> getDayBookDetailsList() {
		return dayBookDetailsList;
	}

	public void setDayBookDetailsList(Set<DayBookDetails> dayBookDetailsList) {
		this.dayBookDetailsList = dayBookDetailsList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsApplicationCountryMaster")
	public List<Data> getDataList() {
		return dataList;
	}

	public void setDataList(List<Data> dataList) {
		this.dataList = dataList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationCountryId")
	public List<ExchangeRate> getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(List<ExchangeRate> exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "countryMaster")
	public List<PipsMaster> getPipsMaster() {
		return pipsMaster;
	}

	public void setPipsMaster(List<PipsMaster> pipsMaster) {
		this.pipsMaster = pipsMaster;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationCountryId")
	public List<BeneCountryService> getBeneCountryServiceMaster() {
		return beneCountryServiceMaster;
	}

	public void setBeneCountryServiceMaster(
			List<BeneCountryService> beneCountryServiceMaster) {
		this.beneCountryServiceMaster = beneCountryServiceMaster;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationCountry")
	public List<MarketingData> getMaketlist() {
		return maketlist;
	}

	public void setMaketlist(List<MarketingData> maketlist) {
		this.maketlist = maketlist;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationCountryId")
	public List<ServiceApplicabilityRule> getServiceApplicabilityRule() {
		return serviceApplicabilityRule;
	}

	public void setServiceApplicabilityRule(
			List<ServiceApplicabilityRule> serviceApplicabilityRule) {
		this.serviceApplicabilityRule = serviceApplicabilityRule;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "beneApplicationCountry")
	public List<BeneficaryAccount> getBeneApplicationCountry() {
		return beneApplicationCountry;
	}

	public void setBeneApplicationCountry(
			List<BeneficaryAccount> beneApplicationCountry) {
		this.beneApplicationCountry = beneApplicationCountry;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "beneficaryCountry")
	public List<BeneficaryAccount> getBeneficaryCountry() {
		return beneficaryCountry;
	}

	public void setBeneficaryCountry(List<BeneficaryAccount> beneficaryCountry) {
		this.beneficaryCountry = beneficaryCountry;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "countryId")
	public List<BankServiceRule> getBankServieRule() {
		return bankServieRule;
	}

	public void setBankServieRule(List<BankServiceRule> bankServieRule) {
		this.bankServieRule = bankServieRule;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMasterByBankCountryId")
	public Set<Remittance> getExRemittanceforBankCountry() {
		return exRemittanceforBankCountry;
	}

	public void setExRemittanceforBankCountry(
			Set<Remittance> exRemittanceforBankCountry) {
		this.exRemittanceforBankCountry = exRemittanceforBankCountry;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMasterByApplicationCountryId")
	public Set<Remittance> getExRemittanceforAppCountry() {
		return exRemittanceforAppCountry;
	}

	public void setExRemittanceforAppCountry(
			Set<Remittance> exRemittanceforAppCountry) {
		this.exRemittanceforAppCountry = exRemittanceforAppCountry;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMasterByCorespondingCountryId")
	public Set<RemittanceApplication> getFsCountryMasterByCorespondingCountryId() {
		return fsCountryMasterByCorespondingCountryId;
	}

	public void setFsCountryMasterByCorespondingCountryId(
			Set<RemittanceApplication> fsCountryMasterByCorespondingCountryId) {
		this.fsCountryMasterByCorespondingCountryId = fsCountryMasterByCorespondingCountryId;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMasterByBankCountryId")
	public Set<RemittanceApplication> getFsCountryMasterByBankCountryId() {
		return fsCountryMasterByBankCountryId;
	}

	public void setFsCountryMasterByBankCountryId(
			Set<RemittanceApplication> fsCountryMasterByBankCountryId) {
		this.fsCountryMasterByBankCountryId = fsCountryMasterByBankCountryId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMasterByApplicationCountryId")
	public Set<RemittanceApplication> getFsCountryMasterByApplicationCountryId() {
		return fsCountryMasterByApplicationCountryId;
	}

	public void setFsCountryMasterByApplicationCountryId(
			Set<RemittanceApplication> fsCountryMasterByApplicationCountryId) {
		this.fsCountryMasterByApplicationCountryId = fsCountryMasterByApplicationCountryId;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<RemiittanceApplication> getFsCountryMasterByBankCountry() {
		return fsCountryMasterByBankCountry;
	}

	public void setFsCountryMasterByBankCountry(
			Set<RemiittanceApplication> fsCountryMasterByBankCountry) {
		this.fsCountryMasterByBankCountry = fsCountryMasterByBankCountry;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMasterapplicationCountry")
	public Set<RemiittanceApplication> getFsCountryMasterByApplicationCountry() {
		return fsCountryMasterByApplicationCountry;
	}

	public void setFsCountryMasterByApplicationCountry(
			Set<RemiittanceApplication> fsCountryMasterByApplicationCountry) {
		this.fsCountryMasterByApplicationCountry = fsCountryMasterByApplicationCountry;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<ApplicationAmlCheck> getExApplicationAmlCheck() {
		return exApplicationAmlCheck;
	}

	public void setExApplicationAmlCheck(
			Set<ApplicationAmlCheck> exApplicationAmlCheck) {
		this.exApplicationAmlCheck = exApplicationAmlCheck;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<RemitApplAml> getExRemitApplAml() {
		return exRemitApplAml;
	}

	public void setExRemitApplAml(Set<RemitApplAml> exRemitApplAml) {
		this.exRemitApplAml = exRemitApplAml;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<AdditionalInstructionData> getAdditionalInstData() {
		return additionalInstData;
	}

	public void setAdditionalInstData(
			Set<AdditionalInstructionData> additionalInstData) {
		this.additionalInstData = additionalInstData;
	}

	 

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "countryId")
	public Set<ServiceApplicabilityRule> getServiceAppRuleCountry() {
		return serviceAppRuleCountry;
	}

	public void setServiceAppRuleCountry(
			Set<ServiceApplicabilityRule> serviceAppRuleCountry) {
		this.serviceAppRuleCountry = serviceAppRuleCountry;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "applicationCountryId")
	public List<InsuranceMaster> getInsuranceSetUp() {
		return insuranceSetUp;
	}

	public void setInsuranceSetUp(List<InsuranceMaster> insuranceSetUp) {
		this.insuranceSetUp = insuranceSetUp;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCountryMaster")
	public Set<CurrencyOtherInformation> getCurrencyOtherInfo() {
		return currencyOtherInfo;
	}

	public void setCurrencyOtherInfo(Set<CurrencyOtherInformation> currencyOtherInfo) {
		this.currencyOtherInfo = currencyOtherInfo;
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
	
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Column(name = "SPLIT_INDICATOR")
	public BigDecimal getSpilitIndicator() {
		return spilitIndicator;
	}	
	
	public void setSpilitIndicator(BigDecimal spilitIndicator) {
		this.spilitIndicator = spilitIndicator;
	}
	
	@Column(name = "STATE_STATUS")
	public String getStateStatus() {
		return stateStatus;
	}

	public void setStateStatus(String stateStatus) {
		this.stateStatus = stateStatus;
	}
	
}
