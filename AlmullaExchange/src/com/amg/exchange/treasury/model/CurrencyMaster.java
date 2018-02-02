package com.amg.exchange.treasury.model;

// default package
// Generated Jul 11, 2014 10:20:31 AM by Hibernate Tools 3.4.0.CR1

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
import com.amg.exchange.common.model.RoleWiseCurrencyLimit;
import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.model.Remittance;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;

/*import com.amg.exchange.foreigncurrency.model.CollectDetail;
 import com.amg.exchange.foreigncurrency.model.Collection;
 import com.amg.exchange.foreigncurrency.model.CurrencyDenomination;
 import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
 import com.amg.exchange.foreigncurrency.model.Stock;*/

/**
 * @author justin
 */

@Entity
@Table(name = "EX_CURRENCY_MASTER")
public class CurrencyMaster implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3842382778044486312L;
	private BigDecimal currencyId;
	private CountryMaster fsCountryMaster;
	private String currencyCode;
	private String currencyName;
	private String quoteName;
	//private Byte currencyDesc;
	private String decimalName;
	private String fimsCurrencyCode;
	private String isactive;
	private String arabicCurrencyName;
	private String arabicDecimalName;
	private String arabicQuoteName;
	private String swiftCurrency;
	private String isoCurrencyCode;
	private BigDecimal decinalNumber;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String allowFCPurchase;
	private String allowFCSale;

	private Set<DealSales> exDealSaleses = new HashSet<DealSales>(0);
	private Set<SupplierInstructionMaster> exSupplierInstructionMasters = new HashSet<SupplierInstructionMaster>(0);
	/*
	 * private Set<FundProjection> exFundProjections = new
	 * HashSet<FundProjection>(0); private Set<FundProjection> exFundProjections
	 * = new HashSet<FundProjection>(0);
	 */
	private Set<FundProjection> exFundProjections = new HashSet<FundProjection>(0);

	private Set<DailyAccountBalance> exDailyAccountBalances = new HashSet<DailyAccountBalance>(0);
	private Set<DealSupplier> exDealSuppliers = new HashSet<DealSupplier>(0);
	private Set<MonthAccountBalance> exMonthAccountBalances = new HashSet<MonthAccountBalance>(0);
	private Set<DealPurchase> exDealPurchases = new HashSet<DealPurchase>(0);
	private Set<Deal> exDeals = new HashSet<Deal>(0);
	private Set<SpecialDeal> exSpecialDeals = new HashSet<SpecialDeal>(0);
	private Set<AccountBalance> exAccountBalances = new HashSet<AccountBalance>(0);
	// private Set<BankMaster> bankMaster = new HashSet<BankMaster>(0);
	/*
	 * private Set<BankAccountDetails> bankAccountDetails = new
	 * HashSet<BankAccountDetails>(0);
	 * 
	 * private Set<FundEstimation> fundEstimationCurrency = new
	 * HashSet<FundEstimation>(0); private Set<FundEstimationDetails>
	 * exFundEstimtaionDetails = new HashSet<FundEstimationDetails>(0); private
	 * Set<FundEstimationDays> exFundEstimationDays = new
	 * HashSet<FundEstimationDays>(0);
	 */
	private Set<DailyBalance> dailyBalance = new HashSet<DailyBalance>(0);
	private Set<CustomerSpecialDealRequest> customerSpecialDealRequest = new HashSet<CustomerSpecialDealRequest>(0);
	// private Set<Employee> employee = new HashSet<Employee>(0);
	private Set<TreasuryDealDetail> exDealDetail = new HashSet<TreasuryDealDetail>(0);
	private Set<TreasuryCustomerSupplier> treasuryCustomerSupplierCurrency = new HashSet<TreasuryCustomerSupplier>(0);
	private Set<DayBookDetails> dayDetailsList = new HashSet<DayBookDetails>(0);
	private Set<ExchangeRate> exchangeRate = new HashSet<ExchangeRate>(0);
	private Set<PipsMaster> pipsMaster = new HashSet<PipsMaster>(0);
	private Set<BeneCountryService> beneCountryServiceMaster = new HashSet<BeneCountryService>(0);
	private Set<CurrencyOtherInformation> currencyOtherInfo = new HashSet<CurrencyOtherInformation>();
	/* fsCurrencyMaster */

	/*
	 * private Set<CurrencyDenomination> exCurrencyDenominations = new
	 * HashSet<CurrencyDenomination>(0); private Set<Collection> exCollections =
	 * new HashSet<Collection>(0); private Set<Stock> exStocks = new
	 * HashSet<Stock>(0); private Set<ForeignCurrencyAdjust>
	 * exForeignCurrencyAdjusts = new HashSet<ForeignCurrencyAdjust>(0); private
	 * Set<CollectDetail> exCollectDetails = new HashSet<CollectDetail>(0);
	 */
	private List<ServiceApplicabilityRule> serviceApplicabilityRule = new ArrayList<ServiceApplicabilityRule>();
	private List<BankServiceRule> bankServiceRule = new ArrayList<BankServiceRule>();
	/*
	 * private List<BankServiceRule> bankServiceForcomission=new
	 * ArrayList<BankServiceRule>(); private List<BankServiceRule>
	 * bankServiceForcharge=new ArrayList<BankServiceRule>(); private
	 * List<BankServiceRule> bankServiceForCost=new
	 * ArrayList<BankServiceRule>();
	 */
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);
	private Set<RemittanceApplication> exCurrencyMasterByForeignCurrencyId = new HashSet<RemittanceApplication>();
	private Set<RemittanceApplication> exCurrencyMasterByLocalCommisionCurrencyId = new HashSet<RemittanceApplication>();
	private Set<RemittanceApplication> exCurrencyMasterByLocalTranxCurrencyId = new HashSet<RemittanceApplication>();
	private Set<RemittanceApplication> exCurrencyMasterByLocalChargeCurrencyId = new HashSet<RemittanceApplication>();
	private Set<RemittanceApplication> exCurrencyMasterByLocalNetCurrencyId = new HashSet<RemittanceApplication>();
	private Set<RemittanceApplication> exCurrencyMasterByLocalDeliveryCurrencyId = new HashSet<RemittanceApplication>();
	private Set<RoleWiseCurrencyLimit> roleWiseCurrencyLimit = new HashSet<RoleWiseCurrencyLimit>();
	//private Set<RemittanceApplication> exRemiittanceApplicationsForLocalDeliveryCurrencyId = new HashSet<RemiittanceApplication>(0);
	//private Set<RemittanceApplication> exRemiittanceApplicationsForLocalNetCurrencyId = new HashSet<RemiittanceApplication>(0);
	//private Set<RemiittanceApplication> exRemiittanceApplicationsForLocalCommisionCurrencyId = new HashSet<RemiittanceApplication>(0);
	//private Set<RemiittanceApplication> exRemiittanceApplicationsForLocalChargeCurrencyId = new HashSet<RemiittanceApplication>(0);
	//private Set<RemiittanceApplication> exRemiittanceApplicationsForForeignCurrencyId = new HashSet<RemiittanceApplication>(0);
	//private Set<RemiittanceApplication> exRemiittanceApplicationsForLocalTranxCurrencyId = new HashSet<RemiittanceApplication>(0);

	public CurrencyMaster() {
	}

	public CurrencyMaster(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public CurrencyMaster(
			BigDecimal currencyId,
			CountryMaster fsCountryMaster,
			String currencyCode,
			String currencyName,
			String quoteName,
			String decimalName,
			String fimsCurrencyCode,
			String isactive,
			String arabicCurrencyName,
			String arabicDecimalName,
			String arabicQuoteName,
			String swiftCurrency,
			String isoCurrencyCode,
			BigDecimal decinalNumber,
			String createdBy,
			Date createdDate,
			String modifiedBy,
			Date modifiedDate,
			String approvedBy,
			Date approvedDate,
			String remarks,
			String allowFCPurchase,
			String allowFCSale,
			Set<DealSales> exDealSaleses,
			Set<SupplierInstructionMaster> exSupplierInstructionMasters,
			Set<FundProjection> exFundProjections,
			Set<DailyAccountBalance> exDailyAccountBalances,
			Set<DealSupplier> exDealSuppliers,
			Set<MonthAccountBalance> exMonthAccountBalances,
			Set<DealPurchase> exDealPurchases,
			Set<Deal> exDeals,
			Set<SpecialDeal> exSpecialDeals,
			Set<AccountBalance> exAccountBalances,
			Set<DailyBalance> dailyBalance,
			Set<CustomerSpecialDealRequest> customerSpecialDealRequest,
			Set<TreasuryDealDetail> exDealDetail,
			Set<TreasuryCustomerSupplier> treasuryCustomerSupplierCurrency,
			Set<DayBookDetails> dayDetailsList,
			Set<ExchangeRate> exchangeRate,
			Set<PipsMaster> pipsMaster,
			Set<BeneCountryService> beneCountryServiceMaster,
			Set<CurrencyOtherInformation> currencyOtherInfo,
			List<ServiceApplicabilityRule> serviceApplicabilityRule,
			List<BankServiceRule> bankServiceRule,
			Set<Remittance> exRemittance,
			Set<RemittanceApplication> exCurrencyMasterByForeignCurrencyId,
			Set<RemittanceApplication> exCurrencyMasterByLocalCommisionCurrencyId,
			Set<RemittanceApplication> exCurrencyMasterByLocalTranxCurrencyId,
			Set<RemittanceApplication> exCurrencyMasterByLocalChargeCurrencyId,
			Set<RemittanceApplication> exCurrencyMasterByLocalNetCurrencyId,
			Set<RemittanceApplication> exCurrencyMasterByLocalDeliveryCurrencyId,
			Set<RoleWiseCurrencyLimit> roleWiseCurrencyLimit
			//,
			//Set<RemiittanceApplication> exRemiittanceApplicationsForLocalDeliveryCurrencyId,
			//Set<RemiittanceApplication> exRemiittanceApplicationsForLocalNetCurrencyId,
			//Set<RemiittanceApplication> exRemiittanceApplicationsForLocalCommisionCurrencyId,
			//Set<RemiittanceApplication> exRemiittanceApplicationsForLocalChargeCurrencyId,
			//Set<RemiittanceApplication> exRemiittanceApplicationsForForeignCurrencyId,
			//Set<RemiittanceApplication> exRemiittanceApplicationsForLocalTranxCurrencyId
			) {
		super();
		this.currencyId = currencyId;
		this.fsCountryMaster = fsCountryMaster;
		this.currencyCode = currencyCode;
		this.currencyName = currencyName;
		this.quoteName = quoteName;
		this.decimalName = decimalName;
		this.fimsCurrencyCode = fimsCurrencyCode;
		this.isactive = isactive;
		this.arabicCurrencyName = arabicCurrencyName;
		this.arabicDecimalName = arabicDecimalName;
		this.arabicQuoteName = arabicQuoteName;
		this.swiftCurrency = swiftCurrency;
		this.isoCurrencyCode = isoCurrencyCode;
		this.decinalNumber = decinalNumber;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.allowFCPurchase = allowFCPurchase;
		this.allowFCSale = allowFCSale;
		this.exDealSaleses = exDealSaleses;
		this.exSupplierInstructionMasters = exSupplierInstructionMasters;
		this.exFundProjections = exFundProjections;
		this.exDailyAccountBalances = exDailyAccountBalances;
		this.exDealSuppliers = exDealSuppliers;
		this.exMonthAccountBalances = exMonthAccountBalances;
		this.exDealPurchases = exDealPurchases;
		this.exDeals = exDeals;
		this.exSpecialDeals = exSpecialDeals;
		this.exAccountBalances = exAccountBalances;
		this.dailyBalance = dailyBalance;
		this.customerSpecialDealRequest = customerSpecialDealRequest;
		this.exDealDetail = exDealDetail;
		this.treasuryCustomerSupplierCurrency = treasuryCustomerSupplierCurrency;
		this.dayDetailsList = dayDetailsList;
		this.exchangeRate = exchangeRate;
		this.pipsMaster = pipsMaster;
		this.beneCountryServiceMaster = beneCountryServiceMaster;
		this.currencyOtherInfo = currencyOtherInfo;
		this.serviceApplicabilityRule = serviceApplicabilityRule;
		this.bankServiceRule = bankServiceRule;
		this.exRemittance = exRemittance;
		this.exCurrencyMasterByForeignCurrencyId = exCurrencyMasterByForeignCurrencyId;
		this.exCurrencyMasterByLocalCommisionCurrencyId = exCurrencyMasterByLocalCommisionCurrencyId;
		this.exCurrencyMasterByLocalTranxCurrencyId = exCurrencyMasterByLocalTranxCurrencyId;
		this.exCurrencyMasterByLocalChargeCurrencyId = exCurrencyMasterByLocalChargeCurrencyId;
		this.exCurrencyMasterByLocalNetCurrencyId = exCurrencyMasterByLocalNetCurrencyId;
		this.exCurrencyMasterByLocalDeliveryCurrencyId = exCurrencyMasterByLocalDeliveryCurrencyId;
		this.roleWiseCurrencyLimit = roleWiseCurrencyLimit;
		//this.exRemiittanceApplicationsForLocalDeliveryCurrencyId = exRemiittanceApplicationsForLocalDeliveryCurrencyId;
		//this.exRemiittanceApplicationsForLocalNetCurrencyId = exRemiittanceApplicationsForLocalNetCurrencyId;
		//this.exRemiittanceApplicationsForLocalCommisionCurrencyId = exRemiittanceApplicationsForLocalCommisionCurrencyId;
		//this.exRemiittanceApplicationsForLocalChargeCurrencyId = exRemiittanceApplicationsForLocalChargeCurrencyId;
		//this.exRemiittanceApplicationsForForeignCurrencyId = exRemiittanceApplicationsForForeignCurrencyId;
		//this.exRemiittanceApplicationsForLocalTranxCurrencyId = exRemiittanceApplicationsForLocalTranxCurrencyId;
	}

	@Id
	@GeneratedValue(generator = "ex_currency_master_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_currency_master_seq", sequenceName = "EX_CURRENCY_MASTER_SEQ", allocationSize = 1)
	@Column(name = "CURRENCY_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "CURRENCY_CODE", length = 3)
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "CURRENCY_NAME", length = 60)
	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Column(name = "QUOTE_NAME", length = 5)
	public String getQuoteName() {
		return this.quoteName;
	}

	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	/*@Column(name = "CURRENCY_DESC", precision = 2, scale = 0)
	public Byte getCurrencyDesc() {
		return this.currencyDesc;
	}

	public void setCurrencyDesc(Byte currencyDesc) {
		this.currencyDesc = currencyDesc;
	}*/

	@Column(name = "DECIMAL_NAME", length = 10)
	public String getDecimalName() {
		return this.decimalName;
	}

	public void setDecimalName(String decimalName) {
		this.decimalName = decimalName;
	}

	@Column(name = "FIMS_CURRENCY_CODE", length = 3)
	public String getFimsCurrencyCode() {
		return this.fimsCurrencyCode;
	}

	public void setFimsCurrencyCode(String fimsCurrencyCode) {
		this.fimsCurrencyCode = fimsCurrencyCode;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "ARABIC_CURRENCY_NAME", length = 120)
	public String getArabicCurrencyName() {
		return this.arabicCurrencyName;
	}

	public void setArabicCurrencyName(String arabicCurrencyName) {
		this.arabicCurrencyName = arabicCurrencyName;
	}

	@Column(name = "ARABIC_DECIMAL_NAME", length = 20)
	public String getArabicDecimalName() {
		return this.arabicDecimalName;
	}

	public void setArabicDecimalName(String arabicDecimalName) {
		this.arabicDecimalName = arabicDecimalName;
	}

	@Column(name = "ARABIC_QUOTE_NAME", length = 100)
	public String getArabicQuoteName() {
		return this.arabicQuoteName;
	}

	public void setArabicQuoteName(String arabicQuoteName) {
		this.arabicQuoteName = arabicQuoteName;
	}

	@Column(name = "SWIFT_CURRENCY", length = 3)
	public String getSwiftCurrency() {
		return this.swiftCurrency;
	}

	public void setSwiftCurrency(String swiftCurrency) {
		this.swiftCurrency = swiftCurrency;
	}

	@Column(name = "ISO_CURRENCY_CODE", length = 6)
	public String getIsoCurrencyCode() {
		return this.isoCurrencyCode;
	}

	public void setIsoCurrencyCode(String isoCurrencyCode) {
		this.isoCurrencyCode = isoCurrencyCode;
	}

	@Column(name = "DECIMAL_NUMBER", length = 1)
	public BigDecimal getDecinalNumber() {
		return decinalNumber;
	}

	public void setDecinalNumber(BigDecimal decinalNumber) {
		this.decinalNumber = decinalNumber;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<DealSales> getExDealSaleses() {
		return this.exDealSaleses;
	}

	public void setExDealSaleses(Set<DealSales> exDealSaleses) {
		this.exDealSaleses = exDealSaleses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<SupplierInstructionMaster> getExSupplierInstructionMasters() {
		return this.exSupplierInstructionMasters;
	}

	public void setExSupplierInstructionMasters(Set<SupplierInstructionMaster> exSupplierInstructionMasters) {
		this.exSupplierInstructionMasters = exSupplierInstructionMasters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<FundProjection> getExFundProjections() {
		return this.exFundProjections;
	}

	public void setExFundProjections(Set<FundProjection> exFundProjections) {
		this.exFundProjections = exFundProjections;
	}

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster") public
	 * Set<Stock> getExStocks() { return this.exStocks; }
	 * 
	 * public void setExStocks(Set<Stock> exStocks) { this.exStocks = exStocks;
	 * }
	 * 
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster") public
	 * Set getExForeignCurrencyAdjusts() { return this.exForeignCurrencyAdjusts;
	 * }
	 * 
	 * public void setExForeignCurrencyAdjusts(Set exForeignCurrencyAdjusts) {
	 * this.exForeignCurrencyAdjusts = exForeignCurrencyAdjusts; }
	 */

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<DailyAccountBalance> getExDailyAccountBalances() {
		return this.exDailyAccountBalances;
	}

	public void setExDailyAccountBalances(Set<DailyAccountBalance> exDailyAccountBalances) {
		this.exDailyAccountBalances = exDailyAccountBalances;
	}

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster") public
	 * Set<CollectDetail> getExCollectDetails() { return this.exCollectDetails;
	 * }
	 * 
	 * public void setExCollectDetails(Set<CollectDetail> exCollectDetails) {
	 * this.exCollectDetails = exCollectDetails; }
	 */

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<MonthAccountBalance> getExMonthAccountBalances() {
		return this.exMonthAccountBalances;
	}

	public void setExMonthAccountBalances(Set<MonthAccountBalance> exMonthAccountBalances) {
		this.exMonthAccountBalances = exMonthAccountBalances;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<DealPurchase> getExDealPurchases() {
		return this.exDealPurchases;
	}

	public void setExDealPurchases(Set<DealPurchase> exDealPurchases) {
		this.exDealPurchases = exDealPurchases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<Deal> getExDeals() {
		return this.exDeals;
	}

	public void setExDeals(Set<Deal> exDeals) {
		this.exDeals = exDeals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<SpecialDeal> getExSpecialDeals() {
		return this.exSpecialDeals;
	}

	public void setExSpecialDeals(Set<SpecialDeal> exSpecialDeals) {
		this.exSpecialDeals = exSpecialDeals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<AccountBalance> getExAccountBalances() {
		return this.exAccountBalances;
	}

	public void setExAccountBalances(Set<AccountBalance> exAccountBalances) {
		this.exAccountBalances = exAccountBalances;
	}

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster") public
	 * Set getExCurrencyDenominations() { return this.exCurrencyDenominations; }
	 * 
	 * public void setExCurrencyDenominations( Set exCurrencyDenominations) {
	 * this.exCurrencyDenominations = exCurrencyDenominations; }
	 */

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<DealSupplier> getExDealSuppliers() {
		return this.exDealSuppliers;
	}

	public void setExDealSuppliers(Set<DealSupplier> exDealSuppliers) {
		this.exDealSuppliers = exDealSuppliers;
	}

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "currencyMaster") public
	 * Set<BankMaster> getBankMaster() { return bankMaster; } public void
	 * setBankMaster(Set<BankMaster> bankMaster) { this.bankMaster = bankMaster;
	 * }
	 */

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCurrencyMaster") public
	 * Set<BankAccountDetails> getBankAccountDetails() { return
	 * bankAccountDetails; }
	 * 
	 * public void setBankAccountDetails(Set<BankAccountDetails>
	 * bankAccountDetails) { this.bankAccountDetails = bankAccountDetails; }
	 */

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrenyMaster") public
	 * Set<FundEstimation> getFundEstimationCurrency() { return
	 * fundEstimationCurrency; }
	 * 
	 * public void setFundEstimationCurrency(Set<FundEstimation>
	 * fundEstimationCurrency) { this.fundEstimationCurrency =
	 * fundEstimationCurrency; }
	 * 
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrenyMaster") public
	 * Set<FundEstimationDetails> getExFundEstimtaionDetails() { return
	 * exFundEstimtaionDetails; }
	 * 
	 * public void setExFundEstimtaionDetails(Set<FundEstimationDetails>
	 * exFundEstimtaionDetails) { this.exFundEstimtaionDetails =
	 * exFundEstimtaionDetails; }
	 * 
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrenyMaster") public
	 * Set<FundEstimationDays> getExFundEstimationDays() { return
	 * exFundEstimationDays; }
	 * 
	 * public void setExFundEstimationDays(Set<FundEstimationDays>
	 * exFundEstimationDays) { this.exFundEstimationDays = exFundEstimationDays;
	 * }
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "currencyMaster")
	public Set<DailyBalance> getDailyBalance() {
		return dailyBalance;
	}

	public void setDailyBalance(Set<DailyBalance> dailyBalance) {
		this.dailyBalance = dailyBalance;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerSpeacialDealReqCurrencyMaster")
	public Set<CustomerSpecialDealRequest> getCustomerSpecialDealRequest() {
		return customerSpecialDealRequest;
	}

	public void setCustomerSpecialDealRequest(Set<CustomerSpecialDealRequest> customerSpecialDealRequest) {
		this.customerSpecialDealRequest = customerSpecialDealRequest;
	}

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "currencyMaster") public
	 * Set<Employee> getEmployee() { return employee; } public void
	 * setEmployee(Set<Employee> employee) { this.employee = employee; }
	 */

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "treasuryDealDetailCurrencyMaster")
	public Set<TreasuryDealDetail> getExDealDetail() {
		return exDealDetail;
	}

	public void setExDealDetail(Set<TreasuryDealDetail> exDealDetail) {
		this.exDealDetail = exDealDetail;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dealSupplierCurrency")
	public Set<TreasuryCustomerSupplier> getTreasuryCustomerSupplierCurrency() {
		return treasuryCustomerSupplierCurrency;
	}

	public void setTreasuryCustomerSupplierCurrency(Set<TreasuryCustomerSupplier> treasuryCustomerSupplierCurrency) {
		this.treasuryCustomerSupplierCurrency = treasuryCustomerSupplierCurrency;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dayBookCurrencyId")
	public Set<DayBookDetails> getDayDetailsList() {
		return dayDetailsList;
	}

	public void setDayDetailsList(Set<DayBookDetails> dayDetailsList) {
		this.dayDetailsList = dayDetailsList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "currencyId")
	public Set<ExchangeRate> getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Set<ExchangeRate> exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "currencyMaster")
	public Set<PipsMaster> getPipsMaster() {
		return pipsMaster;
	}

	public void setPipsMaster(Set<PipsMaster> pipsMaster) {
		this.pipsMaster = pipsMaster;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "currencyId")
	public Set<BeneCountryService> getBeneCountryServiceMaster() {
		return beneCountryServiceMaster;
	}

	public void setBeneCountryServiceMaster(Set<BeneCountryService> beneCountryServiceMaster) {
		this.beneCountryServiceMaster = beneCountryServiceMaster;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "currencyId")
	public List<ServiceApplicabilityRule> getServiceApplicabilityRule() {
		return serviceApplicabilityRule;
	}

	public void setServiceApplicabilityRule(List<ServiceApplicabilityRule> serviceApplicabilityRule) {
		this.serviceApplicabilityRule = serviceApplicabilityRule;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "currencyId")
	public List<BankServiceRule> getBankServiceRule() {
		return bankServiceRule;
	}

	public void setBankServiceRule(List<BankServiceRule> bankServiceRule) {
		this.bankServiceRule = bankServiceRule;
	}

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "comissionCurrency") public
	 * List<BankServiceRule> getBankServiceForcomission() { return
	 * bankServiceForcomission; }
	 * 
	 * public void setBankServiceForcomission( List<BankServiceRule>
	 * bankServiceForcomission) { this.bankServiceForcomission =
	 * bankServiceForcomission; }
	 * 
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "chargeCurrency") public
	 * List<BankServiceRule> getBankServiceForcharge() { return
	 * bankServiceForcharge; }
	 * 
	 * public void setBankServiceForcharge(List<BankServiceRule>
	 * bankServiceForcharge) { this.bankServiceForcharge = bankServiceForcharge;
	 * }
	 * 
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "costCurrency") public
	 * List<BankServiceRule> getBankServiceForCost() { return
	 * bankServiceForCost; }
	 * 
	 * public void setBankServiceForCost(List<BankServiceRule>
	 * bankServiceForCost) { this.bankServiceForCost = bankServiceForCost; }
	 */

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}

	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByForeignCurrencyId")
	public Set<RemittanceApplication> getExCurrencyMasterByForeignCurrencyId() {
		return exCurrencyMasterByForeignCurrencyId;
	}

	public void setExCurrencyMasterByForeignCurrencyId(Set<RemittanceApplication> exCurrencyMasterByForeignCurrencyId) {
		this.exCurrencyMasterByForeignCurrencyId = exCurrencyMasterByForeignCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalCommisionCurrencyId")
	public Set<RemittanceApplication> getExCurrencyMasterByLocalCommisionCurrencyId() {
		return exCurrencyMasterByLocalCommisionCurrencyId;
	}

	public void setExCurrencyMasterByLocalCommisionCurrencyId(Set<RemittanceApplication> exCurrencyMasterByLocalCommisionCurrencyId) {
		this.exCurrencyMasterByLocalCommisionCurrencyId = exCurrencyMasterByLocalCommisionCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalTranxCurrencyId")
	public Set<RemittanceApplication> getExCurrencyMasterByLocalTranxCurrencyId() {
		return exCurrencyMasterByLocalTranxCurrencyId;
	}

	public void setExCurrencyMasterByLocalTranxCurrencyId(Set<RemittanceApplication> exCurrencyMasterByLocalTranxCurrencyId) {
		this.exCurrencyMasterByLocalTranxCurrencyId = exCurrencyMasterByLocalTranxCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalChargeCurrencyId")
	public Set<RemittanceApplication> getExCurrencyMasterByLocalChargeCurrencyId() {
		return exCurrencyMasterByLocalChargeCurrencyId;
	}

	public void setExCurrencyMasterByLocalChargeCurrencyId(Set<RemittanceApplication> exCurrencyMasterByLocalChargeCurrencyId) {
		this.exCurrencyMasterByLocalChargeCurrencyId = exCurrencyMasterByLocalChargeCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalNetCurrencyId")
	public Set<RemittanceApplication> getExCurrencyMasterByLocalNetCurrencyId() {
		return exCurrencyMasterByLocalNetCurrencyId;
	}

	public void setExCurrencyMasterByLocalNetCurrencyId(Set<RemittanceApplication> exCurrencyMasterByLocalNetCurrencyId) {
		this.exCurrencyMasterByLocalNetCurrencyId = exCurrencyMasterByLocalNetCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalDeliveryCurrencyId")
	public Set<RemittanceApplication> getExCurrencyMasterByLocalDeliveryCurrencyId() {
		return exCurrencyMasterByLocalDeliveryCurrencyId;
	}

	public void setExCurrencyMasterByLocalDeliveryCurrencyId(Set<RemittanceApplication> exCurrencyMasterByLocalDeliveryCurrencyId) {
		this.exCurrencyMasterByLocalDeliveryCurrencyId = exCurrencyMasterByLocalDeliveryCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsCurrencyMaster")
	public Set<RoleWiseCurrencyLimit> getRoleWiseCurrencyLimit() {
		return roleWiseCurrencyLimit;
	}

	public void setRoleWiseCurrencyLimit(Set<RoleWiseCurrencyLimit> roleWiseCurrencyLimit) {
		this.roleWiseCurrencyLimit = roleWiseCurrencyLimit;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalDeliveryCurrencyId")
	public Set<RemiittanceApplication> getExRemiittanceApplicationsForLocalDeliveryCurrencyId() {
		return exRemiittanceApplicationsForLocalDeliveryCurrencyId;
	}

	public void setExRemiittanceApplicationsForLocalDeliveryCurrencyId(Set<RemiittanceApplication> exRemiittanceApplicationsForLocalDeliveryCurrencyId) {
		this.exRemiittanceApplicationsForLocalDeliveryCurrencyId = exRemiittanceApplicationsForLocalDeliveryCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalNetCurrencyId")
	public Set<RemiittanceApplication> getExRemiittanceApplicationsForLocalNetCurrencyId() {
		return exRemiittanceApplicationsForLocalNetCurrencyId;
	}

	public void setExRemiittanceApplicationsForLocalNetCurrencyId(Set<RemiittanceApplication> exRemiittanceApplicationsForLocalNetCurrencyId) {
		this.exRemiittanceApplicationsForLocalNetCurrencyId = exRemiittanceApplicationsForLocalNetCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalCommisionCurrencyId")
	public Set<RemiittanceApplication> getExRemiittanceApplicationsForLocalCommisionCurrencyId() {
		return exRemiittanceApplicationsForLocalCommisionCurrencyId;
	}

	public void setExRemiittanceApplicationsForLocalCommisionCurrencyId(Set<RemiittanceApplication> exRemiittanceApplicationsForLocalCommisionCurrencyId) {
		this.exRemiittanceApplicationsForLocalCommisionCurrencyId = exRemiittanceApplicationsForLocalCommisionCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalNetCurrencyId")
	public Set<RemiittanceApplication> getExRemiittanceApplicationsForLocalChargeCurrencyId() {
		return exRemiittanceApplicationsForLocalChargeCurrencyId;
	}

	public void setExRemiittanceApplicationsForLocalChargeCurrencyId(Set<RemiittanceApplication> exRemiittanceApplicationsForLocalChargeCurrencyId) {
		this.exRemiittanceApplicationsForLocalChargeCurrencyId = exRemiittanceApplicationsForLocalChargeCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalNetCurrencyId")
	public Set<RemiittanceApplication> getExRemiittanceApplicationsForForeignCurrencyId() {
		return exRemiittanceApplicationsForForeignCurrencyId;
	}

	public void setExRemiittanceApplicationsForForeignCurrencyId(Set<RemiittanceApplication> exRemiittanceApplicationsForForeignCurrencyId) {
		this.exRemiittanceApplicationsForForeignCurrencyId = exRemiittanceApplicationsForForeignCurrencyId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMasterByLocalNetCurrencyId")
	public Set<RemiittanceApplication> getExRemiittanceApplicationsForLocalTranxCurrencyId() {
		return exRemiittanceApplicationsForLocalTranxCurrencyId;
	}

	public void setExRemiittanceApplicationsForLocalTranxCurrencyId(Set<RemiittanceApplication> exRemiittanceApplicationsForLocalTranxCurrencyId) {
		this.exRemiittanceApplicationsForLocalTranxCurrencyId = exRemiittanceApplicationsForLocalTranxCurrencyId;
	}*/

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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exCurrencyMaster")
	public Set<CurrencyOtherInformation> getCurrencyOtherInfo() {
		return currencyOtherInfo;
	}

	public void setCurrencyOtherInfo(Set<CurrencyOtherInformation> currencyOtherInfo) {
		this.currencyOtherInfo = currencyOtherInfo;
	}
	
	@Column(name = "ALLOW_FC_PURCHASE")
	public String getAllowFCPurchase() {
		return allowFCPurchase;
	}

	public void setAllowFCPurchase(String allowFCPurchase) {
		this.allowFCPurchase = allowFCPurchase;
	}

	@Column(name = "ALLOW_FC_SALE")
	public String getAllowFCSale() {
		return allowFCSale;
	}

	public void setAllowFCSale(String allowFCSale) {
		this.allowFCSale = allowFCSale;
	}

}
