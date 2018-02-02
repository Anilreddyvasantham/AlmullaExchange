package com.amg.exchange.common.model;

import java.math.BigDecimal;
import java.sql.Blob;
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


/*import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.Collection;
import com.amg.exchange.foreigncurrency.model.CurrencyDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;*/
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.ApplicationAmlCheck;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.Remittance;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankDdPrintLoc;
import com.amg.exchange.treasury.model.DailyAccountBalance;
import com.amg.exchange.treasury.model.DailyBalance;
import com.amg.exchange.treasury.model.Deal;
import com.amg.exchange.treasury.model.DealConclusionType;
import com.amg.exchange.treasury.model.DealPurchase;
import com.amg.exchange.treasury.model.DealSupplier;
import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.model.FundProjection;
import com.amg.exchange.treasury.model.Message;
import com.amg.exchange.treasury.model.OutrightSwapMaster;
import com.amg.exchange.treasury.model.SpecialDeal;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;

/*******************************************************************************************************************

		 File		: CompanyMaster.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 4:43:50 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 19-Nov-2014 12:30 pm
 						By		: Nazish Ehsan hashmi
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Entity
@Table(name = "FS_COMPANY_MASTER" )
public class CompanyMaster implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal companyId;
	private BigDecimal companyCode;
	private String isActive;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	private CountryMaster countryMaster;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	
	private String registrationNumber;
	private String licNumber;
	private String address1;
	private String address2;
	private String address3;
	private String telephoneNo;
	private String faxNo;
	private String email;
	private String estYear;
	private BigDecimal currencyId;
	private String capitalAmount;
	private BigDecimal grpcod;
	private BigDecimal finbgn;
	private BigDecimal finend;
	private String arabicAddress1;
	private String arabicAddress2;
	private String arabicAddress3;
	private Blob logoImg1;
	private Blob stampImg1;
	private String swiftBic;
	private String telexNo;		
	
	

	private List<CompanyMasterDesc> fsCompanyMasterDescs = new ArrayList<CompanyMasterDesc>();
	private List<Customer> fsCustomers = new ArrayList<Customer>();
	private List<CustomerEmploymentInfo> fsCustomerEmploymentInfos = new ArrayList<CustomerEmploymentInfo>();
	private List<CustomerLogin> fsCustomerLogins = new ArrayList<CustomerLogin>();
	private List<RuleApplicationDesc> fsRuleApplicationDescs = new ArrayList<RuleApplicationDesc>();
	private List<BusinessComponentConf> fsBusinessComponentConfs = new ArrayList<BusinessComponentConf>();
	private Set<SpecialDeal> exSpecialDeals = new HashSet<SpecialDeal>(0);
	private Set<OutrightSwapMaster> exOutrightSwapMasters = new HashSet<OutrightSwapMaster>(0);
	private Set<Message> exMessages = new HashSet<Message>(0);
	private Set<FundProjection> exFundProjections = new HashSet<FundProjection>(0);
	private Set<DealSupplier> exDealSuppliers = new HashSet<DealSupplier>(0);
	private Set<DealPurchase> exDealPurchases = new HashSet<DealPurchase>(0);
	private Set<DealConclusionType> exDealConclusionTypes = new HashSet<DealConclusionType>(0);
	private Set<Deal> exDeals = new HashSet<Deal>(0);
	/*private Set<FundEstimation> exFundEstimtaion = new HashSet<FundEstimation>(0);
	private Set<FundEstimationDetails> exFundEstimtaionDetails = new HashSet<FundEstimationDetails>(0);
	private Set<FundEstimationDays> exFundEstimationDays = new HashSet<FundEstimationDays>(0);*/
	//private Set<CurrencyWiseDenomination> exCurrencyWiseDenomination = new HashSet<CurrencyWiseDenomination>(0);
	
	/*private Set<ForeignCurrencyAdjust> exForeignCurrencyAdjusts = new HashSet<ForeignCurrencyAdjust>(0);
	private Set<SourceOfIncome> exSourceOfIncomes = new HashSet<SourceOfIncome>(0);*/
	private Set<BankDdPrintLoc> exBankDdPrintLocs = new HashSet<BankDdPrintLoc>(0);
	private Set<ArticleMaster> fsArticleMasters = new HashSet<ArticleMaster>(0);
	private Set<BankApplicability> exBankApplicabilities = new HashSet<BankApplicability>(0);
	private Set<BankAccount> exBankAccounts = new HashSet<BankAccount>(0);
	private Set<BankAccountDetails> exBankAccountDetailses = new HashSet<BankAccountDetails>(0);
	private Set<DailyBalance> dailyBalance = new HashSet<DailyBalance>(0);
	private Set<DailyAccountBalance> dailyAccountBalance = new HashSet<DailyAccountBalance>(0);
	/*private Set<ReceiptPayment> exReceiptPayments = new HashSet<ReceiptPayment>(0);
	private Set<Collection> exCollections = new HashSet<Collection>(0);
	private Set<CollectDetail> exCollectDetails = new HashSet<CollectDetail>(0);
	private Set<PurposeOfTransaction> exPurposeOfTransactions = new HashSet<PurposeOfTransaction>(0);
	private Set<CurrencyDenomination> exCurrencyDenominations = new HashSet<CurrencyDenomination>(0);*/
	private Set<AccountBalance> accountBalance = new HashSet<AccountBalance>(0);
	private Set<Employee> employees=new HashSet<Employee>();
	private Set<TreasuryDealHeader> exDealHeader = new HashSet<TreasuryDealHeader>(0);
	private Set<TreasuryDealDetail> exDealDetail = new HashSet<TreasuryDealDetail>(0);
	private Set<TreasuryStandardInstruction> exTreasuryStandardIns = new HashSet<TreasuryStandardInstruction>(0);
	/*private List<TreasuryDealDetail> exTreasuryDealDetails = new ArrayList<TreasuryDealDetail>();*/
	private Set<DayBookHeader> exDayBook = new HashSet<DayBookHeader>(0);
	
	private Set<DayBookDetails> dayBookDetailsList = new HashSet<DayBookDetails>(0);
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
	private List<SpecialRateRequest> specialRateRequest=new ArrayList<SpecialRateRequest>();
	private Set<RemittanceApplication> exRemittanceApplication = new HashSet<RemittanceApplication>(0);
	
	//private Set<RemiittanceApplication> exRemiittanceApplication = new HashSet<RemiittanceApplication>(0);
	private Set<RemittanceAppBenificiary> exRemiittanceAppBenificiary = new HashSet<RemittanceAppBenificiary>(0);
	private Set<ApplicationAmlCheck> exApplicationAmlCheck = new HashSet<ApplicationAmlCheck>(0);
	private Set<RemitApplAml> exRemitApplAml = new HashSet<RemitApplAml>(0);
	private Set<AdditionalInstructionData> additionalInstData = new HashSet<AdditionalInstructionData>(0);
	private Set<ApplicationSetup> appicationSetup=new HashSet<ApplicationSetup>(0);
	private List<InsuranceMaster>  exInsuranceSetup=new ArrayList<InsuranceMaster>();
	//private String companyInitial;
	
	public CompanyMaster() {
	}

	public CompanyMaster(BigDecimal companyId) {
		this.companyId = companyId;
	}

	

	public CompanyMaster(BigDecimal companyId, BigDecimal companyCode,
			String companyActive, String createdBy, String updatedBy,
			Date creationDate, Date lastUpdated,
			List<CompanyMasterDesc> fsCompanyMasterDescs,
			List<Customer> fsCustomers,
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos,
			List<CustomerLogin> fsCustomerLogins,
			List<RuleApplicationDesc> fsRuleApplicationDescs,
			List<BusinessComponentConf> fsBusinessComponentConfs,
			Set<SpecialDeal> exSpecialDeals,
			Set<OutrightSwapMaster> exOutrightSwapMasters,
			Set<Message> exMessages,
			Set<FundProjection> exFundProjections,
			Set<DealSupplier> exDealSuppliers,
			Set<DealPurchase> exDealPurchases,
			Set<DealConclusionType> exDealConclusionTypes,
			Set<Deal> exDeals,/*Set<ForeignCurrencyAdjust> exForeignCurrencyAdjusts,
			Set<SourceOfIncome> exSourceOfIncomes,*/
			Set<BankDdPrintLoc> exBankDdPrintLocs,
			Set<ArticleMaster> fsArticleMasters,
			Set<BankApplicability> exBankApplicabilities,
			/*Set<ReceiptPayment> exReceiptPayments,*/
			Set<BankAccount> exBankAccounts,/*,Set<Collection> exCollections,*/
			/*Set<CollectDetail> exCollectDetails,
			Set<PurposeOfTransaction> exPurposeOfTransactions,
			Set<CurrencyDenomination> exCurrencyDenominations,*/
			Set<BankAccountDetails> exBankAccountDetailses,
			Set<AccountBalance> accountBalance,
			Set<Employee> employees,
			Set<TreasuryDealHeader> exDealHeader,
			Set<TreasuryDealDetail> exDealDetail,
			Set<TreasuryStandardInstruction> exTreasuryStandardIns,
			Set<DayBookHeader> exDayBook,
			Set<DayBookDetails> dayBookDetailsList,
			Set<Remittance> exRemittance,
			List<SpecialRateRequest> specialRateRequest,
			Set<RemittanceApplication> exRemittanceApplication,
			//Set<RemiittanceApplication> exRemiittanceApplication,
			 Set<RemittanceAppBenificiary> exRemiittanceAppBenificiary,
			 Set<ApplicationAmlCheck> exApplicationAmlCheck,
			 Set<RemitApplAml> exRemitApplAml,
             Set<AdditionalInstructionData> additionalInstData,CountryMaster countryMaster,List<InsuranceMaster>  exInsuranceSetup,String companyInitial) {
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.isActive = companyActive;
		this.createdBy = createdBy;
		this.modifiedBy = updatedBy;
		this.createdDate = creationDate;
		this.modifiedDate = lastUpdated;
		this.fsCompanyMasterDescs = fsCompanyMasterDescs;
		this.fsCustomers = fsCustomers;
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
		this.fsCustomerLogins = fsCustomerLogins;
		this.fsRuleApplicationDescs = fsRuleApplicationDescs;
		this.fsBusinessComponentConfs = fsBusinessComponentConfs;
		this.exSpecialDeals = exSpecialDeals;
		this.exOutrightSwapMasters = exOutrightSwapMasters;
		this.exMessages = exMessages;
		this.exFundProjections=exFundProjections;
		this.exDealSuppliers=exDealSuppliers;
		this.exDealPurchases=exDealPurchases;
		this.exDealConclusionTypes=exDealConclusionTypes;
		this.exDeals=exDeals;
		/*this.exForeignCurrencyAdjusts=exForeignCurrencyAdjusts;
		this.exSourceOfIncomes=exSourceOfIncomes;*/
		this.exBankDdPrintLocs=exBankDdPrintLocs;
		this.fsArticleMasters=fsArticleMasters;
		this.exBankApplicabilities=exBankApplicabilities;		
		this.exBankAccounts=exBankAccounts;
		/*this.exReceiptPayments=exReceiptPayments;
		this.exCollections=exCollections;
		this.exCollectDetails=exCollectDetails;
		this.exPurposeOfTransactions=exPurposeOfTransactions;
		this.exCurrencyDenominations=exCurrencyDenominations;*/
		this.exBankAccountDetailses=exBankAccountDetailses;
		this.accountBalance=accountBalance;
		this.employees =  employees;
		this.exDealHeader = exDealHeader;
		this.exDealDetail=exDealDetail;
		this.exTreasuryStandardIns = exTreasuryStandardIns;
		this.exDayBook=exDayBook;
		this.dayBookDetailsList = dayBookDetailsList;
	    this.exRemittance = exRemittance;	
	    this.specialRateRequest=specialRateRequest;
	    this.exRemittanceApplication = exRemittanceApplication;
	    //this.exRemiittanceApplication = exRemiittanceApplication;
	    this.exRemiittanceAppBenificiary = exRemiittanceAppBenificiary;
	    this.exApplicationAmlCheck = exApplicationAmlCheck;
	    this.exRemitApplAml = exRemitApplAml;
	    this.additionalInstData = additionalInstData;
	    this.countryMaster=countryMaster;
	    this.exInsuranceSetup=exInsuranceSetup;
	    //this.companyInitial = companyInitial;
	}

	@Id
	@GeneratedValue(generator = "fs_company_master_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_company_master_seq", sequenceName = "FS_COMPANY_MASTER_SEQ", allocationSize = 1)
	@Column(name = "COMPANY_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Column(name = "COMPANY_CODE", length = 2)
	public BigDecimal getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}

	
	@Column(name = "ISACTIVE", length = 1)
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "CREATED_BY", length = 30)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	
	
	
	
	
	
	@Column(name = "MODIFIED_BY", length = 30)
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public List<CompanyMasterDesc> getFsCompanyMasterDescs() {
		return this.fsCompanyMasterDescs;
	}

	public void setFsCompanyMasterDescs(
			List<CompanyMasterDesc> fsCompanyMasterDescs) {
		this.fsCompanyMasterDescs = fsCompanyMasterDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public List<Customer> getFsCustomers() {
		return this.fsCustomers;
	}

	public void setFsCustomers(List<Customer> fsCustomers) {
		this.fsCustomers = fsCustomers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public List<CustomerLogin> getFsCustomerLogins() {
		return this.fsCustomerLogins;
	}

	public void setFsCustomerLogins(List<CustomerLogin> fsCustomerLogins) {
		this.fsCustomerLogins = fsCustomerLogins;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public List<RuleApplicationDesc> getFsRuleApplicationDescs() {
		return this.fsRuleApplicationDescs;
	}

	public void setFsRuleApplicationDescs(
			List<RuleApplicationDesc> fsRuleApplicationDescs) {
		this.fsRuleApplicationDescs = fsRuleApplicationDescs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public List<CustomerEmploymentInfo> getFsCustomerEmploymentInfos() {
		return fsCustomerEmploymentInfos;
	}

	public void setFsCustomerEmploymentInfos(
			List<CustomerEmploymentInfo> fsCustomerEmploymentInfos) {
		this.fsCustomerEmploymentInfos = fsCustomerEmploymentInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public List<BusinessComponentConf> getFsBusinessComponentConfs() {
		return fsBusinessComponentConfs;
	}

	public void setFsBusinessComponentConfs(
			List<BusinessComponentConf> fsBusinessComponentConfs) {
		this.fsBusinessComponentConfs = fsBusinessComponentConfs;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<SpecialDeal> getExSpecialDeals() {
		return this.exSpecialDeals;
	}

	public void setExSpecialDeals(Set<SpecialDeal> exSpecialDeals) {
		this.exSpecialDeals = exSpecialDeals;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<OutrightSwapMaster> getExOutrightSwapMasters() {
		return this.exOutrightSwapMasters;
	}

	public void setExOutrightSwapMasters(Set<OutrightSwapMaster> exOutrightSwapMasters) {
		this.exOutrightSwapMasters = exOutrightSwapMasters;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<Message> getExMessages() {
		return this.exMessages;
	}

	public void setExMessages(Set<Message> exMessages) {
		this.exMessages = exMessages;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<FundProjection> getExFundProjections() {
		return this.exFundProjections;
	}

	public void setExFundProjections(Set<FundProjection> exFundProjections) {
		this.exFundProjections = exFundProjections;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<DealSupplier> getExDealSuppliers() {
		return this.exDealSuppliers;
	}

	public void setExDealSuppliers(Set<DealSupplier> exDealSuppliers) {
		this.exDealSuppliers = exDealSuppliers;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<DealPurchase> getExDealPurchases() {
		return this.exDealPurchases;
	}

	public void setExDealPurchases(Set<DealPurchase> exDealPurchases) {
		this.exDealPurchases = exDealPurchases;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<DealConclusionType> getExDealConclusionTypes() {
		return this.exDealConclusionTypes;
	}

	public void setExDealConclusionTypes(Set<DealConclusionType> exDealConclusionTypes) {
		this.exDealConclusionTypes = exDealConclusionTypes;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<Deal> getExDeals() {
		return this.exDeals;
	}

	public void setExDeals(Set<Deal> exDeals) {
		this.exDeals = exDeals;
	}

	
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<ForeignCurrencyAdjust> getExForeignCurrencyAdjusts() {
		return this.exForeignCurrencyAdjusts;
	}

	public void setExForeignCurrencyAdjusts(Set<ForeignCurrencyAdjust> exForeignCurrencyAdjusts) {
		this.exForeignCurrencyAdjusts = exForeignCurrencyAdjusts;
	}*/

	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<SourceOfIncome> getExSourceOfIncomes() {
		return this.exSourceOfIncomes;
	}

	public void setExSourceOfIncomes(Set<SourceOfIncome> exSourceOfIncomes) {
		this.exSourceOfIncomes = exSourceOfIncomes;
	}*/

	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<BankDdPrintLoc> getExBankDdPrintLocs() {
		return this.exBankDdPrintLocs;
	}

	public void setExBankDdPrintLocs(Set<BankDdPrintLoc> exBankDdPrintLocs) {
		this.exBankDdPrintLocs = exBankDdPrintLocs;
	}

	

	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<ArticleMaster> getFsArticleMasters() {
		return this.fsArticleMasters;
	}

	public void setFsArticleMasters(Set<ArticleMaster> fsArticleMasters) {
		this.fsArticleMasters = fsArticleMasters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<BankApplicability> getExBankApplicabilities() {
		return this.exBankApplicabilities;
	}

	public void setExBankApplicabilities(Set<BankApplicability> exBankApplicabilities) {
		this.exBankApplicabilities = exBankApplicabilities;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<ReceiptPayment> getExReceiptPayments() {
		return this.exReceiptPayments;
	}

	public void setExReceiptPayments(Set<ReceiptPayment> exReceiptPayments) {
		this.exReceiptPayments = exReceiptPayments;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<BankAccount> getExBankAccounts() {
		return this.exBankAccounts;
	}

	public void setExBankAccounts(Set<BankAccount> exBankAccounts) {
		this.exBankAccounts = exBankAccounts;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<Collection> getExCollections() {
		return this.exCollections;
	}

	public void setExCollections(Set<Collection> exCollections) {
		this.exCollections = exCollections;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<CollectDetail> getExCollectDetails() {
		return this.exCollectDetails;
	}

	public void setExCollectDetails(Set<CollectDetail> exCollectDetails) {
		this.exCollectDetails = exCollectDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<PurposeOfTransaction> getExPurposeOfTransactions() {
		return this.exPurposeOfTransactions;
	}

	public void setExPurposeOfTransactions(Set<PurposeOfTransaction> exPurposeOfTransactions) {
		this.exPurposeOfTransactions = exPurposeOfTransactions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<CurrencyDenomination> getExCurrencyDenominations() {
		return this.exCurrencyDenominations;
	}

	public void setExCurrencyDenominations(Set<CurrencyDenomination> exCurrencyDenominations) {
		this.exCurrencyDenominations = exCurrencyDenominations;
	}
*/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<BankAccountDetails> getExBankAccountDetailses() {
		return this.exBankAccountDetailses;
	}

	public void setExBankAccountDetailses(Set<BankAccountDetails> exBankAccountDetailses) {
		this.exBankAccountDetailses = exBankAccountDetailses;
	}
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<FundEstimation> getExFundEstimtaion() {
		return exFundEstimtaion;
	}

	public void setExFundEstimtaion(Set<FundEstimation> exFundEstimtaion) {
		this.exFundEstimtaion = exFundEstimtaion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<FundEstimationDetails> getExFundEstimtaionDetails() {
		return exFundEstimtaionDetails;
	}

	public void setExFundEstimtaionDetails(
			Set<FundEstimationDetails> exFundEstimtaionDetails) {
		this.exFundEstimtaionDetails = exFundEstimtaionDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<FundEstimationDays> getExFundEstimationDays() {
		return exFundEstimationDays;
	}

	public void setExFundEstimationDays(Set<FundEstimationDays> exFundEstimationDays) {
		this.exFundEstimationDays = exFundEstimationDays;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<DailyBalance> getDailyBalance() {
		return dailyBalance;
	}

	public void setDailyBalance(Set<DailyBalance> dailyBalance) {
		this.dailyBalance = dailyBalance;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<DailyAccountBalance> getDailyAccountBalance() {
		return dailyAccountBalance;
	}

	public void setDailyAccountBalance(Set<DailyAccountBalance> dailyAccountBalance) {
		this.dailyAccountBalance = dailyAccountBalance;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyMaster")
	public Set<AccountBalance> getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Set<AccountBalance> accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<CurrencyWiseDenomination> getExCurrencyWiseDenomination() {
		return exCurrencyWiseDenomination;
	}

	public void setExCurrencyWiseDenomination(
			Set<CurrencyWiseDenomination> exCurrencyWiseDenomination) {
		this.exCurrencyWiseDenomination = exCurrencyWiseDenomination;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<TreasuryDealHeader> getExDealHeader() {
		return exDealHeader;
	}

	public void setExDealHeader(Set<TreasuryDealHeader> exDealHeader) {
		this.exDealHeader = exDealHeader;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryDealCompanyMaster")
	public Set<TreasuryDealDetail> getExDealDetail() {
		return exDealDetail;
	}

	public void setExDealDetail(Set<TreasuryDealDetail> exDealDetail) {
		this.exDealDetail = exDealDetail;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasurycomCompanyMaster")
	public Set<TreasuryStandardInstruction> getExTreasuryStandardIns() {
		return exTreasuryStandardIns;
	}

	public void setExTreasuryStandardIns(
			Set<TreasuryStandardInstruction> exTreasuryStandardIns) {
		this.exTreasuryStandardIns = exTreasuryStandardIns;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dayBookCompanyMaster")
	public Set<DayBookHeader> getExDayBook() {
		return exDayBook;
	}

	public void setExDayBook(Set<DayBookHeader> exDayBook) {
		this.exDayBook = exDayBook;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dayBookCompanyMaster")
	public Set<DayBookDetails> getDayBookDetailsList() {
		return dayBookDetailsList;
	}

	public void setDayBookDetailsList(Set<DayBookDetails> dayBookDetailsList) {
		this.dayBookDetailsList = dayBookDetailsList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}

	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "specialRequestCompanyId")
	public List<TreasuryDealDetail> getExTreasuryDealDetails() {
		return exTreasuryDealDetails;
	}

	public void setExTreasuryDealDetails(
			List<TreasuryDealDetail> exTreasuryDealDetails) {
		this.exTreasuryDealDetails = exTreasuryDealDetails;
	}
	*/
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyMaster")
	public List<SpecialRateRequest> getSpecialRateRequest() {
		return specialRateRequest;
	}

	public void setSpecialRateRequest(List<SpecialRateRequest> specialRateRequest) {
		this.specialRateRequest = specialRateRequest;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<RemittanceApplication> getExRemittanceApplication() {
		return exRemittanceApplication;
	}

	public void setExRemittanceApplication(
			Set<RemittanceApplication> exRemittanceApplication) {
		this.exRemittanceApplication = exRemittanceApplication;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
    public Set<RemiittanceApplication> getExRemiittanceApplication() {
		return exRemiittanceApplication;
	}

	public void setExRemiittanceApplication(
			Set<RemiittanceApplication> exRemiittanceApplication) {
		this.exRemiittanceApplication = exRemiittanceApplication;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="fsCompanyMaster")
	public Set<RemittanceAppBenificiary> getExRemiittanceAppBenificiary() {
		return exRemiittanceAppBenificiary;
	}

	public void setExRemiittanceAppBenificiary(
			Set<RemittanceAppBenificiary> exRemiittanceAppBenificiary) {
		this.exRemiittanceAppBenificiary = exRemiittanceAppBenificiary;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<ApplicationAmlCheck> getExApplicationAmlCheck() {
		return exApplicationAmlCheck;
	}

	public void setExApplicationAmlCheck(
			Set<ApplicationAmlCheck> exApplicationAmlCheck) {
		this.exApplicationAmlCheck = exApplicationAmlCheck;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<RemitApplAml> getExRemitApplAml() {
		return exRemitApplAml;
	}

	public void setExRemitApplAml(Set<RemitApplAml> exRemitApplAml) {
		this.exRemitApplAml = exRemitApplAml;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<AdditionalInstructionData> getAdditionalInstData() {
		return additionalInstData;
	}

	public void setAdditionalInstData(
			Set<AdditionalInstructionData> additionalInstData) {
		this.additionalInstData = additionalInstData;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public Set<ApplicationSetup> getAppicationSetup() {
		return appicationSetup;
	}

	public void setAppicationSetup(Set<ApplicationSetup> appicationSetup) {
		this.appicationSetup = appicationSetup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getCountryMaster() {
		return countryMaster;
	}

	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
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
 
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCompanyMaster")
	public List<InsuranceMaster> getExInsuranceSetup() {
		return exInsuranceSetup;
	}

	public void setExInsuranceSetup(List<InsuranceMaster> exInsuranceSetup) {
		this.exInsuranceSetup = exInsuranceSetup;
	}
	
	@Column(name = "REGISTRATION_NUMBER")
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	@Column(name = "LIC_NUMBER")
	public String getLicNumber() {
		return licNumber;
	}
	@Column(name = "ADDRESS1")
	public String getAddress1() {
		return address1;
	}
	@Column(name = "ADDRESS2")
	public String getAddress2() {
		return address2;
	}
	@Column(name = "ADDRESS3")
	public String getAddress3() {
		return address3;
	}
	@Column(name = "TELEPHONE_NO")
	public String getTelephoneNo() {
		return telephoneNo;
	}
	@Column(name = "FAX_NO")
	public String getFaxNo() {
		return faxNo;
	}
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	@Column(name = "EST_YEAR")
	public String getEstYear() {
		return estYear;
	}
	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	@Column(name = "CAPITAL_AMOUNT")
	public String getCapitalAmount() {
		return capitalAmount;
	}
	@Column(name = "GRPCOD")
	public BigDecimal getGrpcod() {
		return grpcod;
	}
	@Column(name = "FINBGN")
	public BigDecimal getFinbgn() {
		return finbgn;
	}
	@Column(name = "FINEND")
	public BigDecimal getFinend() {
		return finend;
	}
	@Column(name = "AR_ADDR1")
	public String getArabicAddress1() {
		return arabicAddress1;
	}
	@Column(name = "AR_ADDR2")
	public String getArabicAddress2() {
		return arabicAddress2;
	}
	@Column(name = "AR_ADDR3")
	public String getArabicAddress3() {
		return arabicAddress3;
	}
	@Column(name = "LOGO_IMG1", nullable = true,  length = 10485760 )
	public Blob getLogoImg1() {
		return logoImg1;
	}
	@Column(name = "STAMP_IMG", nullable = true,  length = 10485760 )
	public Blob getStampImg1() {
		return stampImg1;
	}
	@Column(name = "SWIFT_BIC")
	public String getSwiftBic() {
		return swiftBic;
	}
	@Column(name = "TELEX_NO")
	public String getTelexNo() {
		return telexNo;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public void setLicNumber(String licNumber) {
		this.licNumber = licNumber;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setEstYear(String estYear) {
		this.estYear = estYear;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public void setCapitalAmount(String capitalAmount) {
		this.capitalAmount = capitalAmount;
	}
	public void setGrpcod(BigDecimal grpcod) {
		this.grpcod = grpcod;
	}
	public void setFinbgn(BigDecimal finbgn) {
		this.finbgn = finbgn;
	}
	public void setFinend(BigDecimal finend) {
		this.finend = finend;
	}
	public void setArabicAddress1(String arabicAddress1) {
		this.arabicAddress1 = arabicAddress1;
	}
	public void setArabicAddress2(String arabicAddress2) {
		this.arabicAddress2 = arabicAddress2;
	}
	public void setArabicAddress3(String arabicAddress3) {
		this.arabicAddress3 = arabicAddress3;
	}
	public void setLogoImg1(Blob logoImg1) {
		this.logoImg1 = logoImg1;
	}
	public void setStampImg1(Blob stampImg1) {
		this.stampImg1 = stampImg1;
	}
	public void setSwiftBic(String swiftBic) {
		this.swiftBic = swiftBic;
	}
	public void setTelexNo(String telexNo) {
		this.telexNo = telexNo;
	}

	/*@Column(name="COMPANY_INITIAL")
	public String getCompanyInitial() {
		return companyInitial;
	}

	public void setCompanyInitial(String companyInitial) {
		this.companyInitial = companyInitial;
	}*/
	
	
	
}
