package com.amg.exchange.beneficiary.bean;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.model.BanksView;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.bean.PersonalRemittanceRoutingBankBranches;
import com.amg.exchange.remittance.bean.PersonalRemittanceTeleExistDTBean;
import com.amg.exchange.remittance.bean.PersonalRemmitanceBeneficaryDataTable;
import com.amg.exchange.remittance.bean.PopulateDataWithCode;
import com.amg.exchange.remittance.model.AccountTypeFromView;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BeneficaryStatus;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;
import com.amg.exchange.remittance.model.ViewRoutingAgentLocations;
import com.amg.exchange.remittance.model.ViewRoutingAgents;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IRelationsTypeService;
import com.amg.exchange.remittance.service.IRoutingSetUpDetailsService;
import com.amg.exchange.remittance.service.ISwiftMasterService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceGroupMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.service.IBankApplicabilityService;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.IBeneCountryServices;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

@Component("onlineBeneficiaryCreationBean")
@Scope("session")
public class OnlineBeneficiaryCreationBean implements Serializable {
	private static final Logger log = Logger.getLogger(OnlineBeneficiaryCreationBean.class);
	private static final long serialVersionUID = 1L;
	private String idType = null;
	private String selectType = "Manual";
	private String manual = null;
	private String smartCard = null;
	private BigDecimal selectCard;
	// Beneficary Master
	private String firstName;
	private String secondName;
	private String thirdName;
	private String fourthName;
	private String fifthName;


	private String firstLName;
	private String secondLName;
	private String thirdLName;
	private String fourthLName;


	private BigDecimal nationality;
	private Date dateOfBrith;
	private BigDecimal yearOfBrith;
	private BigDecimal age;
	private String occupation;
	private String state;
	private String district;
	private String city;
	private String noOfRemittance;
	private BigDecimal countryId;
	private String stateName;
	private String cityName;
	private String districtName;
	private String beneficaryStatusName;
	// Beneficary Account
	private BigDecimal serviceGroupIdforCash;
	private BankMaster bank;
	private String bankAccountNumber;
	private String tempAccountNumber;
	private String aliasFirstName;
	private String aliasSecondName;
	private String aliasThirdName;
	private String aliasFourthName;
	private BigDecimal serviceTypeId;
	private BigDecimal proviance;
	private BigDecimal cardType;
	// Beneficary Relations
	private BigDecimal beneficaryRelationshipId;
	// Beneficary Telephone
	private String countryTelephoneNumber;
	private BigDecimal beneficaryStatusId;
	// Remittance Service
	private String benificaryName;
	private BigDecimal beneficaryBankId;
	private String beneficaryBankName;
	private BigDecimal beneficaryBankBranchId;
	private String beneficaryBankBranchName;
	private String additionBankDetails;
	private String chargesOverseas;
	private String agent;
	private BigDecimal currency;
	private BigDecimal modeOfPayment;
	private BigDecimal routingBranch;
	private BigDecimal deliveryMode;
	private BigDecimal beneficarymasterId;
	// rahamath
	private Boolean booRenderBenificarySearchPanel = false;
	private Boolean booRenderBenificaryStatusPanel = false;
	private Boolean booRenderTypeOfServicePanel = false;
	private Boolean booRenderRemittanceServicePanel = false;
	private Boolean booRenderOldSmartCardPanel = false;
	private Boolean booRenderBenificaryFirstPanel = false;
	private Boolean booRenderIndBenificaryStatusPanel = false;
	private Boolean booRenderAgent = false;
	private Boolean booRenderOverseaCharges = false;
	private int selectCardType = 70;
	private BigDecimal customerNo = null;
	private BigDecimal beneficaryTypeId = null;
	private String telephoneNumber ;
	private BigDecimal mobileNumber = null;
	private String address = null;
	private BigDecimal nationalityName = null;
	private String beneficaryName = null;
	private BigDecimal relationId = null;
	private Boolean readOnlyFirstName = false;
	private Boolean readOnlySecondName = false;
	private Boolean readOnlyThirdName = false;
	// private Boolean readOnlyPhoneNo=false;
	private Boolean readOnlyAddress = false;
	private Boolean readOnlyNationality = false;
	private Boolean readOnlyRelations = false;
	private Boolean readOnlyFifthName = false;
	private Boolean readOnlyFourthName = false;
	private Boolean readOnlyDateOfBirth = false;
	private Boolean readOnlyYearOfBirth = false;
	private Boolean readOnlyAge = false;
	private String beneficaryTypeName = null;
	private Boolean disableBeneficaryType = false;
	private Boolean disableDataOfBirth = false;
	private Boolean disableResetDataOfBirth = false;
	private String bankCode = null;
	private BigDecimal bankBranchCode = null;
	private BigDecimal countryTelCode = null;
	// Ramakrishna Code
	private BigDecimal indbenificiaryType;
	private BigDecimal servicecurrencyId;
	private BigDecimal servicebankBranchId;
	private BigDecimal benifisCountryId;
	private BigDecimal benifisCurrencyId;
	private BigDecimal benifisBankId;
	private BigDecimal beneCountryid;
	private BigDecimal benifisStateId;
	private BigDecimal distictId;
	private BigDecimal cityId;
	private boolean disablerelation = false;
	private String countryCode;
	private String mcountryCode;
	/* private BigDecimal serviceId; */
	private BigDecimal serviceGroupId;
	private String ServiceDescription;
	private BigDecimal benificaryTelephone;
	private BigDecimal masterId;
	private BigDecimal customerId;
	private String benificiaryryNameRemittance;
	private Boolean readOnlyOccupation = false;
	private String bankAccountLength;
	private String checkTelePhoneExist;
	private boolean isTelePhoneExist;
	private String checkAccountNoExist;
	private boolean isAccountNoExist;
	private String checkRelationExist;
	private boolean isRelationExist;
	private String relationShipName;
	private Boolean disableDependService = false;
	private String currencyName;
	private boolean singleCurrency = false;
	private boolean multCurrency = true;
	private Boolean typeOfServicePanel = false;
	private BigDecimal customerrefno;
	private String customerFullName;
	private Date customerExpDate;
	private String customerIsActive;
	private String customerExpireDateMsg;
	private boolean disableNationality;
	private BigDecimal databenificarycountry;
	private String databenificarycountryname;
	private BigDecimal databenificarycurrency;
	private String databenificarycurrencyname;
	private String databenificaryservice;
	private BigDecimal databenificarydelivery;
	private BigDecimal dataserviceid;
	private BigDecimal dataAccountnum;
	private String benificarystatus;
	private String databenificarybankname;
	private String databenificarybranchname;
	private String databenificaryname;
	private BigDecimal paymentModeId;
	private String BranchApplicabilty;
	private String exceptionMessage;
	private Boolean booRenderDeliveryModeInputPanel = true;
	private Boolean booRenderDeliveryModeDDPanel = false;
	private String deliveryModeInput;
	private BigDecimal populatedDebitCardNumber;
	private String documentserialityno = null;
	private String customerName;
	private String customerCrNumber;
	private boolean selectrecord = false;
	private String errmsg;
	private Boolean renderBackButton = false;
	private boolean booAuthozed = false;
	private Boolean minagevalidation = false;
	private BigDecimal beneMatsterSeqId;
	private Date minDate = new Date();
	private String beniStatusName;
	private String masterCreatedBy;
	private Date masterCreatedDate;
	private String contactCreatedBy;
	private Date contactCreatedDate;
	private String accountCreatedBy;
	private Date accountCreatedDate;
	private String relationCreatedBy;
	private Date relationCreatedDate;
	private String isActive;
	private BigDecimal applicationCountryId;
	private BigDecimal beneficaryAccountSeqId;
	private BigDecimal beneficaryTelephoneSeqId;
	private Boolean booenableAgentPanel;

	private BigDecimal bankAccountType;
	private BigDecimal searchStateId;
	private BigDecimal searchDistrictId;
	private BigDecimal searchISFCCode;

	private String bankName;
	private String bankBranchName;
	private String countryName;

	// chiru code added
	public String databenificaryservicegroup;
	private BigDecimal dataservicegroupid;
	private String effectiveMinDate;
	// added to fetch state , district , city from bank branch table
	private String beneficaryBankState;
	private String beneficaryBankDistrict;
	private String beneficaryBankCity;


	private BigDecimal agentMaster;
	private BigDecimal agentBranch;
	private BigDecimal serviceProviderBankBranchId;


	private BigDecimal searchBankStateId;
	@Autowired
	IBankApplicabilityService<T> bankApplicabilityService;
	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IBankIndicatorService bankIndicatorService;
	@Autowired
	IRelationsTypeService irelation;
	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;
	@Autowired
	ServiceCodeMasterService serviceMasterService;
	@Autowired
	IBeneCountryServices<T> ibeneCountryServices;
	@Autowired
	IFundEstimationService<T> ifundservice;
	@Autowired
	ICustomerBankService icustomerBankService;
	@Autowired
	IRemittanceModeService iRemitModeMaster;
	@Autowired
	ISwiftMasterService swiftMasterService;
	@Autowired
	IBeneficaryCreation beneficaryCreation;
	@Autowired
	IBankMasterService<T> ibankMasterService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBankBranchDetailsService<T> ibankBranchDetailsService;

	iCypherSecurity cypherSecurity = new CypherSecurityImpl();
	private List<CurrencyMaster> currencyMasterList = new ArrayList<CurrencyMaster>();
	private List<BankAccountDetails> listCurrencyAccountDetails = new ArrayList<BankAccountDetails>();
	private List<PersonalRemmitanceBeneficaryDataTable> coustomerBeneficaryDTList = new ArrayList<PersonalRemmitanceBeneficaryDataTable>();
	private List<CustomerIdProof> customerDetailsList = new ArrayList<CustomerIdProof>();
	/*private List<Relations> relationsList = new ArrayList<Relations>();*/	
	private Map<Integer, String> beneficaryMap = new HashMap<Integer, String>();
	private List<CurrencyMaster> listCurrency = new ArrayList<CurrencyMaster>();
	private List<BankMaster> lstBank = new ArrayList<BankMaster>();
	private List<BankBranch> lstBankbranch = new ArrayList<BankBranch>();
	private List<CountryMasterDesc> lstCountry = new ArrayList<CountryMasterDesc>();
	private List<StateMasterDesc> lststate = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstDistict = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstCity = new ArrayList<CityMasterDesc>();
	private List<BankApplicability> serviceproviderlst = new ArrayList<BankApplicability>();
	private List<ViewBeneServiceCurrency> beneServiceCurrencyList = new ArrayList<ViewBeneServiceCurrency>();
	private List<BeneficaryAccount> beneficaryAccountList = new ArrayList<BeneficaryAccount>();
	private List<BeneficaryContact> beneficiaryTel = new ArrayList<BeneficaryContact>();
	private List<RemittanceModeDescription> servicedatafromdb = new ArrayList<RemittanceModeDescription>();
	private List<DeliveryModeDesc> deliverydataBasedonService = new ArrayList<DeliveryModeDesc>();
	private List<BankBranch> lstforAll = new ArrayList<BankBranch>();
	private List<PersonalRemittanceRoutingBankBranches> lstofRoutingBranches = new ArrayList<PersonalRemittanceRoutingBankBranches>();
	private List<RelationsDescription> relationDescList = new ArrayList<RelationsDescription>();
	private List<ServiceGroupMasterDesc> serviceGroupMasterDescList = new ArrayList<ServiceGroupMasterDesc>();
	private List<RemittanceModeDescription> listRemittanceDesc = new ArrayList<RemittanceModeDescription>();
	private List<DeliveryModeDesc> listdeliveryDesc = new ArrayList<DeliveryModeDesc>();
	private List<CountryMasterDesc> nationalityList = new ArrayList<CountryMasterDesc>();
	private List<CountryMasterDesc> beneCountryList = new ArrayList<CountryMasterDesc>();
	private CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();
	private List<BankApplicability> bankMasterList = new CopyOnWriteArrayList<BankApplicability>();
	private Boolean mainPanelRender = false;
	private List<BeneficaryAccount> lstBeneficaryAccount = new ArrayList<BeneficaryAccount>();
	private Map<String, Object> mapTeleExistToCheck = new HashMap<String, Object>();
	private List<PersonalRemittanceTeleExistDTBean> lstBeneficaryAccountDailog = new ArrayList<PersonalRemittanceTeleExistDTBean>();
	private List<BeneficaryAccount> lstUpdateBeneficaryAccount = new ArrayList<BeneficaryAccount>();
	//private List<BankAccountTypeDesc> lstBankAccountType = new ArrayList<BankAccountTypeDesc>();
	private List<AccountTypeFromView> lstBankAccountTypeFromView = new ArrayList<AccountTypeFromView>();
	private List<BranchDataTable> lstDataTableBankbranch = new ArrayList<BranchDataTable>();
	private List<BranchDataTable> lstFilteredDataTableBankbranch = new ArrayList<BranchDataTable>();
	private BranchDataTable selectedDataTableBankbranch ;
	private List<BankBranchView> lstBankbranchView = new ArrayList<BankBranchView>();
	private List<PopulateDataWithCode> agentAndCorrespondingBankslst = new ArrayList<PopulateDataWithCode>();
	private List<PopulateDataWithCode> agentAndCorrespondingBankBranchlst = new ArrayList<PopulateDataWithCode>();
	private List<PopulateDataWithCode> serviceProviderAndCorrespondingBankslst = new ArrayList<PopulateDataWithCode>();
	private List<ViewRoutingAgentLocations> lstAgentLocationForCash = new ArrayList<ViewRoutingAgentLocations>();




	public List<ViewRoutingAgentLocations> getLstAgentLocationForCash() {
		return lstAgentLocationForCash;
	}
	public void setLstAgentLocationForCash(List<ViewRoutingAgentLocations> lstAgentLocationForCash) {
		this.lstAgentLocationForCash = lstAgentLocationForCash;
	}

	public List<PopulateDataWithCode> getServiceProviderAndCorrespondingBankslst() {
		return serviceProviderAndCorrespondingBankslst;
	}
	public void setServiceProviderAndCorrespondingBankslst(List<PopulateDataWithCode> serviceProviderAndCorrespondingBankslst) {
		this.serviceProviderAndCorrespondingBankslst = serviceProviderAndCorrespondingBankslst;
	}

	public List<PopulateDataWithCode> getAgentAndCorrespondingBankBranchlst() {
		return agentAndCorrespondingBankBranchlst;
	}
	public void setAgentAndCorrespondingBankBranchlst(List<PopulateDataWithCode> agentAndCorrespondingBankBranchlst) {
		this.agentAndCorrespondingBankBranchlst = agentAndCorrespondingBankBranchlst;
	}

	public List<PopulateDataWithCode> getAgentAndCorrespondingBankslst() {
		return agentAndCorrespondingBankslst;
	}
	public void setAgentAndCorrespondingBankslst(List<PopulateDataWithCode> agentAndCorrespondingBankslst) {
		this.agentAndCorrespondingBankslst = agentAndCorrespondingBankslst;
	}

	public BigDecimal getAgentMaster() {
		return agentMaster;
	}

	public void setAgentMaster(BigDecimal agentMaster) {
		this.agentMaster = agentMaster;
	}

	public BigDecimal getAgentBranch() {
		return agentBranch;
	}

	public void setAgentBranch(BigDecimal agentBranch) {
		this.agentBranch = agentBranch;
	}

	public BigDecimal getServiceProviderBankBranchId() {
		return serviceProviderBankBranchId;
	}
	public void setServiceProviderBankBranchId(BigDecimal serviceProviderBankBranchId) {
		this.serviceProviderBankBranchId = serviceProviderBankBranchId;
	}

	public IBankApplicabilityService<T> getBankApplicabilityService() {
		return bankApplicabilityService;
	}

	public void setBankApplicabilityService(IBankApplicabilityService<T> bankApplicabilityService) {
		this.bankApplicabilityService = bankApplicabilityService;
	}

	public BigDecimal getSearchBankStateId() {
		return searchBankStateId;
	}

	public void setSearchBankStateId(BigDecimal searchBankStateId) {
		this.searchBankStateId = searchBankStateId;
	}

	public BigDecimal getBeneCountryid() {
		return beneCountryid;
	}

	public void setBeneCountryid(BigDecimal beneCountryid) {
		this.beneCountryid = beneCountryid;
	}

	public List<BankBranchView> getLstBankbranchView() {
		return lstBankbranchView;
	}

	public void setLstBankbranchView(List<BankBranchView> lstBankbranchView) {
		this.lstBankbranchView = lstBankbranchView;
	}

	public BranchDataTable getSelectedDataTableBankbranch() {
		return selectedDataTableBankbranch;
	}

	public void setSelectedDataTableBankbranch(BranchDataTable selectedDataTableBankbranch) {
		this.selectedDataTableBankbranch = selectedDataTableBankbranch;
	}

	public List<BranchDataTable> getLstFilteredDataTableBankbranch() {
		return lstFilteredDataTableBankbranch;
	}

	public void setLstFilteredDataTableBankbranch(List<BranchDataTable> lstFilteredDataTableBankbranch) {
		this.lstFilteredDataTableBankbranch = lstFilteredDataTableBankbranch;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getTempAccountNumber() {
		return tempAccountNumber;
	}

	public void setTempAccountNumber(String tempAccountNumber) {
		this.tempAccountNumber = tempAccountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public List<BranchDataTable> getLstDataTableBankbranch() {
		return lstDataTableBankbranch;
	}

	public void setLstDataTableBankbranch(List<BranchDataTable> lstDataTableBankbranch) {
		this.lstDataTableBankbranch = lstDataTableBankbranch;
	}

	public BigDecimal getSearchDistrictId() {
		return searchDistrictId;
	}

	public void setSearchDistrictId(BigDecimal searchDistrictId) {
		this.searchDistrictId = searchDistrictId;
	}

	public BigDecimal getSearchISFCCode() {
		return searchISFCCode;
	}

	public void setSearchISFCCode(BigDecimal searchISFCCode) {
		this.searchISFCCode = searchISFCCode;
	}

	public BigDecimal getSearchStateId() {
		return searchStateId;
	}

	public void setSearchStateId(BigDecimal searchStateId) {
		this.searchStateId = searchStateId;
	}

	/*public List<BankAccountTypeDesc> getLstBankAccountType() {
		return lstBankAccountType;
	}

	public void setLstBankAccountType(List<BankAccountTypeDesc> lstBankAccountType) {
		this.lstBankAccountType = lstBankAccountType;
	}*/

	public BigDecimal getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(BigDecimal bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public String getFirstLName() {
		return firstLName;
	}

	public void setFirstLName(String firstLName) {
		this.firstLName = firstLName;
	}

	public String getSecondLName() {
		return secondLName;
	}

	public void setSecondLName(String secondLName) {
		this.secondLName = secondLName;
	}

	public String getThirdLName() {
		return thirdLName;
	}

	public void setThirdLName(String thirdLName) {
		this.thirdLName = thirdLName;
	}

	public String getFourthLName() {
		return fourthLName;
	}

	public void setFourthLName(String fourthLName) {
		this.fourthLName = fourthLName;
	}

	public Boolean getBooRenderAgent() {
		return booRenderAgent;
	}

	public void setBooRenderAgent(Boolean booRenderAgent) {
		this.booRenderAgent = booRenderAgent;
	}

	public Boolean getDisableResetDataOfBirth() {
		return disableResetDataOfBirth;
	}

	public void setDisableResetDataOfBirth(Boolean disableResetDataOfBirth) {
		this.disableResetDataOfBirth = disableResetDataOfBirth;
	}

	public String getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(String effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public String getDatabenificaryservicegroup() {
		return databenificaryservicegroup;
	}

	public void setDatabenificaryservicegroup(String databenificaryservicegroup) {
		this.databenificaryservicegroup = databenificaryservicegroup;
	}

	public BigDecimal getDataservicegroupid() {
		return dataservicegroupid;
	}

	public void setDataservicegroupid(BigDecimal dataservicegroupid) {
		this.dataservicegroupid = dataservicegroupid;
	}

	public BigDecimal getBeneficarymasterId() {
		return beneficarymasterId;
	}

	public void setBeneficarymasterId(BigDecimal beneficarymasterId) {
		this.beneficarymasterId = beneficarymasterId;
	}

	public Boolean getBooenableAgentPanel() {
		return booenableAgentPanel;
	}

	public void setBooenableAgentPanel(Boolean booenableAgentPanel) {
		this.booenableAgentPanel = booenableAgentPanel;
	}

	public String getBeniStatusName() {
		return beniStatusName;
	}

	public void setBeniStatusName(String beniStatusName) {
		this.beniStatusName = beniStatusName;
	}

	public BigDecimal getBeneficaryTelephoneSeqId() {
		return beneficaryTelephoneSeqId;
	}

	public void setBeneficaryTelephoneSeqId(BigDecimal beneficaryTelephoneSeqId) {
		this.beneficaryTelephoneSeqId = beneficaryTelephoneSeqId;
	}

	public BigDecimal getBeneficaryAccountSeqId() {
		return beneficaryAccountSeqId;
	}

	public void setBeneficaryAccountSeqId(BigDecimal beneficaryAccountSeqId) {
		this.beneficaryAccountSeqId = beneficaryAccountSeqId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public String getMasterCreatedBy() {
		return masterCreatedBy;
	}

	public void setMasterCreatedBy(String masterCreatedBy) {
		this.masterCreatedBy = masterCreatedBy;
	}

	public Date getMasterCreatedDate() {
		return masterCreatedDate;
	}

	public void setMasterCreatedDate(Date masterCreatedDate) {
		this.masterCreatedDate = masterCreatedDate;
	}

	public String getContactCreatedBy() {
		return contactCreatedBy;
	}

	public void setContactCreatedBy(String contactCreatedBy) {
		this.contactCreatedBy = contactCreatedBy;
	}

	public Date getContactCreatedDate() {
		return contactCreatedDate;
	}

	public void setContactCreatedDate(Date contactCreatedDate) {
		this.contactCreatedDate = contactCreatedDate;
	}

	public String getAccountCreatedBy() {
		return accountCreatedBy;
	}

	public void setAccountCreatedBy(String accountCreatedBy) {
		this.accountCreatedBy = accountCreatedBy;
	}

	public Date getAccountCreatedDate() {
		return accountCreatedDate;
	}

	public void setAccountCreatedDate(Date accountCreatedDate) {
		this.accountCreatedDate = accountCreatedDate;
	}

	public String getRelationCreatedBy() {
		return relationCreatedBy;
	}

	public void setRelationCreatedBy(String relationCreatedBy) {
		this.relationCreatedBy = relationCreatedBy;
	}

	public Date getRelationCreatedDate() {
		return relationCreatedDate;
	}

	public void setRelationCreatedDate(Date relationCreatedDate) {
		this.relationCreatedDate = relationCreatedDate;
	}

	public BigDecimal getBeneMatsterSeqId() {
		return beneMatsterSeqId;
	}

	public void setBeneMatsterSeqId(BigDecimal beneMatsterSeqId) {
		this.beneMatsterSeqId = beneMatsterSeqId;
	}

	public String getMcountryCode() {
		return mcountryCode;
	}

	public void setMcountryCode(String mcountryCode) {
		this.mcountryCode = mcountryCode;
	}

	public BigDecimal getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(BigDecimal mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getBankAccountLength() {
		return bankAccountLength;
	}

	public void setBankAccountLength(String bankAccountLength) {
		this.bankAccountLength = bankAccountLength;
	}

	public BigDecimal getSelectCard() {
		return selectCard;
	}

	public void setSelectCard(BigDecimal selectCard) {
		this.selectCard = selectCard;
	}

	public Boolean getReadOnlyOccupation() {
		return readOnlyOccupation;
	}

	public void setReadOnlyOccupation(Boolean readOnlyOccupation) {
		this.readOnlyOccupation = readOnlyOccupation;
	}

	public List<PersonalRemittanceRoutingBankBranches> getLstofRoutingBranches() {
		return lstofRoutingBranches;
	}

	public void setLstofRoutingBranches(List<PersonalRemittanceRoutingBankBranches> lstofRoutingBranches) {
		this.lstofRoutingBranches = lstofRoutingBranches;
	}

	public List<BankBranch> getLstforAll() {
		return lstforAll;
	}

	public void setLstforAll(List<BankBranch> lstforAll) {
		this.lstforAll = lstforAll;
	}

	SessionStateManage sessionmanage = new SessionStateManage();

	public List<RemittanceModeDescription> getServicedatafromdb() {
		return servicedatafromdb;
	}

	public void setServicedatafromdb(List<RemittanceModeDescription> servicedatafromdb) {
		this.servicedatafromdb = servicedatafromdb;
	}

	public List<DeliveryModeDesc> getDeliverydataBasedonService() {
		return deliverydataBasedonService;
	}

	public void setDeliverydataBasedonService(List<DeliveryModeDesc> deliverydataBasedonService) {
		this.deliverydataBasedonService = deliverydataBasedonService;
	}

	public List<BeneficaryAccount> getBeneficaryAccountList() {
		return beneficaryAccountList;
	}

	public void setBeneficaryAccountList(List<BeneficaryAccount> beneficaryAccountList) {
		this.beneficaryAccountList = beneficaryAccountList;
	}

	public List<BeneficaryContact> getBeneficiaryTel() {
		return beneficiaryTel;
	}

	public void setBeneficiaryTel(List<BeneficaryContact> beneficiaryTel) {
		this.beneficiaryTel = beneficiaryTel;
	}

	public BigDecimal getBenificaryTelephone() {
		return benificaryTelephone;
	}

	public void setBenificaryTelephone(BigDecimal benificaryTelephone) {
		this.benificaryTelephone = benificaryTelephone;
	}

	public BigDecimal getMasterId() {
		return masterId;
	}

	public void setMasterId(BigDecimal masterId) {
		this.masterId = masterId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getBenificiaryryNameRemittance() {
		return benificiaryryNameRemittance;
	}

	public void setBenificiaryryNameRemittance(String benificiaryryNameRemittance) {
		this.benificiaryryNameRemittance = benificiaryryNameRemittance;
	}

	public String getBeneficaryBankName() {
		return beneficaryBankName;
	}

	public void setBeneficaryBankName(String beneficaryBankName) {
		this.beneficaryBankName = beneficaryBankName;
	}

	public BigDecimal getBeneficaryBankBranchId() {
		return beneficaryBankBranchId;
	}

	public void setBeneficaryBankBranchId(BigDecimal beneficaryBankBranchId) {
		this.beneficaryBankBranchId = beneficaryBankBranchId;
	}

	public String getBeneficaryBankBranchName() {
		return beneficaryBankBranchName;
	}

	public void setBeneficaryBankBranchName(String beneficaryBankBranchName) {
		this.beneficaryBankBranchName = beneficaryBankBranchName;
	}

	public List<BeneficaryStatus> getBenificaryStatusName() {
		return benificaryStatusName;
	}

	public void setBenificaryStatusName(List<BeneficaryStatus> benificaryStatusName) {
		this.benificaryStatusName = benificaryStatusName;
	}

	public String getBeneficaryStatusName() {
		return beneficaryStatusName;
	}

	public void setBeneficaryStatusName(String beneficaryStatusName) {
		this.beneficaryStatusName = beneficaryStatusName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public List<BankApplicability> getServiceproviderlst() {
		return serviceproviderlst;
	}

	public void setServiceproviderlst(List<BankApplicability> serviceproviderlst) {
		this.serviceproviderlst = serviceproviderlst;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<PersonalRemmitanceBeneficaryDataTable> getCoustomerBeneficaryDTList() {
		return coustomerBeneficaryDTList;
	}

	public void setCoustomerBeneficaryDTList(List<PersonalRemmitanceBeneficaryDataTable> coustomerBeneficaryDTList) {
		this.coustomerBeneficaryDTList = coustomerBeneficaryDTList;
	}

	public BigDecimal getProviance() {
		return proviance;
	}

	public void setProviance(BigDecimal proviance) {
		this.proviance = proviance;
	}

	public BigDecimal getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(BigDecimal serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public BigDecimal getCardType() {
		return cardType;
	}

	public void setCardType(BigDecimal cardType) {
		this.cardType = cardType;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getManual() {
		return manual;
	}

	public void setManual(String manual) {
		this.manual = manual;
	}

	public String getSmartCard() {
		return smartCard;
	}

	public void setSmartCard(String smartCard) {
		this.smartCard = smartCard;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getFourthName() {
		return fourthName;
	}

	public void setFourthName(String fourthName) {
		this.fourthName = fourthName;
	}

	public String getFifthName() {
		return fifthName;
	}

	public void setFifthName(String fifthName) {
		this.fifthName = fifthName;
	}

	public BigDecimal getNationality() {
		return nationality;
	}

	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}

	public Date getDateOfBrith() {
		return dateOfBrith;
	}

	public void setDateOfBrith(Date dateOfBrith) {
		this.dateOfBrith = dateOfBrith;
	}

	public BigDecimal getYearOfBrith() {
		return yearOfBrith;
	}

	public void setYearOfBrith(BigDecimal yearOfBrith) {
		this.yearOfBrith = yearOfBrith;
	}

	public BigDecimal getAge() {
		return age;
	}

	public void setAge(BigDecimal age) {
		this.age = age;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNoOfRemittance() {
		return noOfRemittance;
	}

	public void setNoOfRemittance(String noOfRemittance) {
		this.noOfRemittance = noOfRemittance;
	}

	public BankMaster getBank() {
		return bank;
	}

	public void setBank(BankMaster bank) {
		this.bank = bank;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getAliasFirstName() {
		return aliasFirstName;
	}

	public void setAliasFirstName(String aliasFirstName) {
		this.aliasFirstName = aliasFirstName;
	}

	public String getAliasSecondName() {
		return aliasSecondName;
	}

	public void setAliasSecondName(String aliasSecondName) {
		this.aliasSecondName = aliasSecondName;
	}

	public String getAliasThirdName() {
		return aliasThirdName;
	}

	public void setAliasThirdName(String aliasThirdName) {
		this.aliasThirdName = aliasThirdName;
	}

	public String getAliasFourthName() {
		return aliasFourthName;
	}

	public void setAliasFourthName(String aliasFourthName) {
		this.aliasFourthName = aliasFourthName;
	}

	public BigDecimal getBeneficaryRelationshipId() {
		return beneficaryRelationshipId;
	}

	public void setBeneficaryRelationshipId(BigDecimal beneficaryRelationshipId) {
		this.beneficaryRelationshipId = beneficaryRelationshipId;
	}

	public String getCountryTelephoneNumber() {
		return countryTelephoneNumber;
	}

	public void setCountryTelephoneNumber(String countryTelephoneNumber) {
		this.countryTelephoneNumber = countryTelephoneNumber;
	}

	public BigDecimal getBeneficaryStatusId() {
		return beneficaryStatusId;
	}

	public void setBeneficaryStatusId(BigDecimal beneficaryStatusId) {
		this.beneficaryStatusId = beneficaryStatusId;
	}

	public int getSelectCardType() {
		return selectCardType;
	}

	public void setSelectCardType(int selectCardType) {
		this.selectCardType = selectCardType;
	}

	public Boolean getBooRenderOldSmartCardPanel() {
		return booRenderOldSmartCardPanel;
	}

	public void setBooRenderOldSmartCardPanel(Boolean booRenderOldSmartCardPanel) {
		this.booRenderOldSmartCardPanel = booRenderOldSmartCardPanel;
	}

	public Boolean getBooRenderBenificarySearchPanel() {
		return booRenderBenificarySearchPanel;
	}

	public void setBooRenderBenificarySearchPanel(Boolean booRenderBenificarySearchPanel) {
		this.booRenderBenificarySearchPanel = booRenderBenificarySearchPanel;
	}

	public Boolean getBooRenderBenificaryStatusPanel() {
		return booRenderBenificaryStatusPanel;
	}

	public void setBooRenderBenificaryStatusPanel(Boolean booRenderBenificaryStatusPanel) {
		this.booRenderBenificaryStatusPanel = booRenderBenificaryStatusPanel;
	}

	public Boolean getBooRenderTypeOfServicePanel() {
		return booRenderTypeOfServicePanel;
	}

	public void setBooRenderTypeOfServicePanel(Boolean booRenderTypeOfServicePanel) {
		this.booRenderTypeOfServicePanel = booRenderTypeOfServicePanel;
	}

	public Boolean getBooRenderRemittanceServicePanel() {
		return booRenderRemittanceServicePanel;
	}

	public void setBooRenderRemittanceServicePanel(Boolean booRenderRemittanceServicePanel) {
		this.booRenderRemittanceServicePanel = booRenderRemittanceServicePanel;
	}

	public Boolean getBooRenderIndBenificaryStatusPanel() {
		return booRenderIndBenificaryStatusPanel;
	}

	public void setBooRenderIndBenificaryStatusPanel(Boolean booRenderIndBenificaryStatusPanel) {
		this.booRenderIndBenificaryStatusPanel = booRenderIndBenificaryStatusPanel;
	}

	public Boolean getBooRenderBenificaryFirstPanel() {
		return booRenderBenificaryFirstPanel;
	}

	public void setBooRenderBenificaryFirstPanel(Boolean booRenderBenificaryFirstPanel) {
		this.booRenderBenificaryFirstPanel = booRenderBenificaryFirstPanel;
	}

	public IPersonalRemittanceService getiPersonalRemittanceService() {
		return iPersonalRemittanceService;
	}

	public void setiPersonalRemittanceService(IPersonalRemittanceService iPersonalRemittanceService) {
		this.iPersonalRemittanceService = iPersonalRemittanceService;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public BigDecimal getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(BigDecimal bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public BigDecimal getCountryTelCode() {
		return countryTelCode;
	}

	public void setCountryTelCode(BigDecimal countryTelCode) {
		this.countryTelCode = countryTelCode;
	}

	/*
	 * public BigDecimal getServiceId() { return serviceId; }
	 * 
	 * public void setServiceId(BigDecimal serviceId) { this.serviceId =
	 * serviceId; }
	 */
	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}

	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}

	public String getServiceDescription() {
		return ServiceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		ServiceDescription = serviceDescription;
	}

	public Boolean getDisableDataOfBirth() {
		return disableDataOfBirth;
	}

	public void setDisableDataOfBirth(Boolean disableDataOfBirth) {
		this.disableDataOfBirth = disableDataOfBirth;
	}

	public Boolean getDisableBeneficaryType() {
		return disableBeneficaryType;
	}

	public void setDisableBeneficaryType(Boolean disableBeneficaryType) {
		this.disableBeneficaryType = disableBeneficaryType;
	}

	public String getBeneficaryTypeName() {
		return beneficaryTypeName;
	}

	public void setBeneficaryTypeName(String beneficaryTypeName) {
		this.beneficaryTypeName = beneficaryTypeName;
	}

	public Boolean getReadOnlyFirstName() {
		return readOnlyFirstName;
	}

	public void setReadOnlyFirstName(Boolean readOnlyFirstName) {
		this.readOnlyFirstName = readOnlyFirstName;
	}

	public Boolean getReadOnlySecondName() {
		return readOnlySecondName;
	}

	public void setReadOnlySecondName(Boolean readOnlySecondName) {
		this.readOnlySecondName = readOnlySecondName;
	}

	public Boolean getReadOnlyThirdName() {
		return readOnlyThirdName;
	}

	public void setReadOnlyThirdName(Boolean readOnlyThirdName) {
		this.readOnlyThirdName = readOnlyThirdName;
	}

	public Boolean getBooRenderOverseaCharges() {
		return booRenderOverseaCharges;
	}

	public void setBooRenderOverseaCharges(Boolean booRenderOverseaCharges) {
		this.booRenderOverseaCharges = booRenderOverseaCharges;
	}

	public Boolean getReadOnlyAddress() {
		return readOnlyAddress;
	}

	public void setReadOnlyAddress(Boolean readOnlyAddress) {
		this.readOnlyAddress = readOnlyAddress;
	}

	public Boolean getReadOnlyNationality() {
		return readOnlyNationality;
	}

	public void setReadOnlyNationality(Boolean readOnlyNationality) {
		this.readOnlyNationality = readOnlyNationality;
	}

	public Boolean getReadOnlyRelations() {
		return readOnlyRelations;
	}

	public void setReadOnlyRelations(Boolean readOnlyRelations) {
		this.readOnlyRelations = readOnlyRelations;
	}

	public Boolean getReadOnlyFifthName() {
		return readOnlyFifthName;
	}

	public void setReadOnlyFifthName(Boolean readOnlyFifthName) {
		this.readOnlyFifthName = readOnlyFifthName;
	}

	public Boolean getReadOnlyFourthName() {
		return readOnlyFourthName;
	}

	public void setReadOnlyFourthName(Boolean readOnlyFourthName) {
		this.readOnlyFourthName = readOnlyFourthName;
	}

	public Boolean getReadOnlyDateOfBirth() {
		return readOnlyDateOfBirth;
	}

	public void setReadOnlyDateOfBirth(Boolean readOnlyDateOfBirth) {
		this.readOnlyDateOfBirth = readOnlyDateOfBirth;
	}

	public Boolean getReadOnlyYearOfBirth() {
		return readOnlyYearOfBirth;
	}

	public void setReadOnlyYearOfBirth(Boolean readOnlyYearOfBirth) {
		this.readOnlyYearOfBirth = readOnlyYearOfBirth;
	}

	public Boolean getReadOnlyAge() {
		return readOnlyAge;
	}

	public void setReadOnlyAge(Boolean readOnlyAge) {
		this.readOnlyAge = readOnlyAge;
	}

	/*public List<Relations> getRelationsList() {
		return getiPersonalRemittanceService().getRelationsList();
	}

	public void setRelationsList(List<Relations> relationsList) {
		this.relationsList = relationsList;
	}*/

	public List<RelationsDescription> getRelationDescList() {
		return relationDescList;
	}

	public void setRelationDescList(List<RelationsDescription> relationDescList) {
		this.relationDescList = relationDescList;
	}

	public BigDecimal getRelationId() {
		return relationId;
	}

	public void setRelationId(BigDecimal relationId) {
		this.relationId = relationId;
	}

	public String getBeneficaryName() {
		return beneficaryName;
	}

	public void setBeneficaryName(String beneficaryName) {
		this.beneficaryName = beneficaryName;
	}

	public BigDecimal getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(BigDecimal nationalityName) {
		this.nationalityName = nationalityName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public BigDecimal getBeneficaryTypeId() {
		return beneficaryTypeId;
	}

	public void setBeneficaryTypeId(BigDecimal beneficaryTypeId) {
		this.beneficaryTypeId = beneficaryTypeId;
	}

	public List<BeneficaryStatus> getBenificaryStatusList() {
		return benificaryStatusList;
	}

	public void setBenificaryStatusList(List<BeneficaryStatus> benificaryStatusList) {
		this.benificaryStatusList = benificaryStatusList;
	}

	public BigDecimal getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(BigDecimal customerNo) {
		this.customerNo = customerNo;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public BigDecimal getIndbenificiaryType() {
		return indbenificiaryType;
	}

	public void setIndbenificiaryType(BigDecimal indbenificiaryType) {
		this.indbenificiaryType = indbenificiaryType;
	}

	public BigDecimal getServicecurrencyId() {
		return servicecurrencyId;
	}

	public void setServicecurrencyId(BigDecimal servicecurrencyId) {
		this.servicecurrencyId = servicecurrencyId;
	}

	public List<CurrencyMaster> getListCurrency() {
		/* listCurrency=getiPersonalRemittanceService().getCurrencyList(); */
		return listCurrency;
	}

	public void setListCurrency(List<CurrencyMaster> listCurrency) {
		this.listCurrency = listCurrency;
	}

	public BigDecimal getBenifisBankId() {
		return benifisBankId;
	}

	public void setBenifisBankId(BigDecimal benifisBankId) {
		this.benifisBankId = benifisBankId;
	}

	public List<BankMaster> getLstBank() {
		return lstBank;
	}

	public void setLstBank(List<BankMaster> lstBank) {
		this.lstBank = lstBank;
	}

	public List<BankBranch> getLstBankbranch() {
		return lstBankbranch;
	}

	public void setLstBankbranch(List<BankBranch> lstBankbranch) {
		this.lstBankbranch = lstBankbranch;
	}

	public List<CountryMasterDesc> getLstCountry() {
		return lstCountry;
	}

	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}

	public List<StateMasterDesc> getLststate() {
		return lststate;
	}

	public void setLststate(List<StateMasterDesc> lststate) {
		this.lststate = lststate;
	}

	public List<DistrictMasterDesc> getLstDistict() {
		return lstDistict;
	}

	public void setLstDistict(List<DistrictMasterDesc> lstDistict) {
		this.lstDistict = lstDistict;
	}

	public List<CityMasterDesc> getLstCity() {
		return lstCity;
	}

	public void setLstCity(List<CityMasterDesc> lstCity) {
		this.lstCity = lstCity;
	}

	public BigDecimal getServicebankBranchId() {
		return servicebankBranchId;
	}

	public void setServicebankBranchId(BigDecimal servicebankBranchId) {
		this.servicebankBranchId = servicebankBranchId;
	}

	public BigDecimal getBenifisCountryId() {
		return benifisCountryId;
	}

	public void setBenifisCountryId(BigDecimal benifisCountryId) {
		this.benifisCountryId = benifisCountryId;
	}

	public BigDecimal getBenifisCurrencyId() {
		return benifisCurrencyId;
	}

	public void setBenifisCurrencyId(BigDecimal benifisCurrencyId) {
		this.benifisCurrencyId = benifisCurrencyId;
	}

	public BigDecimal getBenifisStateId() {
		return benifisStateId;
	}

	public void setBenifisStateId(BigDecimal benifisStateId) {
		this.benifisStateId = benifisStateId;
	}

	public BigDecimal getDistictId() {
		return distictId;
	}

	public void setDistictId(BigDecimal distictId) {
		this.distictId = distictId;
	}

	public BigDecimal getCityId() {
		return cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

	public void popbanklist() {
		// clear all fields once country change
		clearBankAccountPanel();


		if(getLstBankbranchView() != null){
			setLstBankbranchView(null);
		}

		serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());
		//lstBank.clear();
		//lstBank = generalService.getBankList(getBenifisCountryId());
		fetchAllBeneServiceBanks();
		//benServiceCurrencyList();
		//popStatelist();
	}

	public void clearBankAccountPanel(){
		setServiceGroupId(null);
		setBenifisBankId(null);
		setSearchBankStateId(null);
		setServicebankBranchId(null);
		setBenifisCurrencyId(null);
		setBankAccountType(null);
		setBeneSwiftCode(null);
		setBankAccountNumber(null);
		setAliasFirstName(null);
		setAliasSecondName(null);
		setAliasThirdName(null);
		setAliasFourthName(null);
		setBeneficaryBankState(null);
		setBeneficaryBankDistrict(null);
		setBeneficaryBankCity(null);
		setServiceTypeId(null);
		setAgentMaster(null);
		setAgentBranch(null);
		if(agentAndCorrespondingBankslst != null && !agentAndCorrespondingBankslst.isEmpty()){
			agentAndCorrespondingBankslst.clear();
		}
		if(agentAndCorrespondingBankBranchlst != null && !agentAndCorrespondingBankBranchlst.isEmpty()){
			agentAndCorrespondingBankBranchlst.clear();
		}

	}

	private Boolean booAotherLang;

	public Boolean getBooAotherLang() {
		return booAotherLang;
	}

	public void setBooAotherLang(Boolean booAotherLang) {
		this.booAotherLang = booAotherLang;
	}

	public void popbranchlist() {
		popCurrencylistBank();
		lstDataTableBankbranch.clear();
		if(lstBankbranchView!=null){
			lstBankbranchView.clear();
		}

		setBooAotherLang(true);

		List<BankBranchView> lstBankbranchView = new ArrayList<BankBranchView>();
		if (getSearchBankStateId()!=null && getSearchBankStateId().equals(new BigDecimal(0))) {
			lstBankbranchView = beneficaryCreation.getBranchListfromViewwithStateMissing(getBenifisBankId());
		} else {
			lstBankbranchView = beneficaryCreation.getBranchListfromViewfromState(getSearchBankStateId(), getBenifisBankId());
		}
		if (lstBankbranchView.size() > 0) {
			setLstBankbranchView(lstBankbranchView);
		}

		for (BanksView bankMaster : lstBankFromView) {

			if (bankMaster.getBankId().compareTo(getBenifisBankId()) == 0) {
				setBankCode(bankMaster.getBankCode());

				if(!bankMaster.getLanguageInd().equals(Constants.ENGLISH_LANGUAGE_ID))
				{
					setBooAotherLang(false);

					if(getFirstLName()==null || getFirstLName()!=null && getFirstLName().length()==0)
					{
						RequestContext.getCurrentInstance().execute("localLanaguageMisssing.show();");
						setErrmsg(" Need to enter Local Language's Beneficary names for this bank ");
						return;
					}
				}
				setAliasFirstName(null);
				setAliasSecondName(null);
				setAliasThirdName(null);
				setAliasFourthName(null);

				break;
			}
		}
	}

	public void populateBankAccountType(){
		try {
			//lstBankAccountType = beneficaryCreation.getBankAccountType(sessionmanage.getLanguageId());
			
			if(lstBankAccountTypeFromView != null || !lstBankAccountTypeFromView.isEmpty()){
				lstBankAccountTypeFromView.clear();
			}
			
			lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBenifisCountryId());
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("dself.show();");
			setErrmsg("Exception occured "+ e.getMessage());
			setBenifisBankId(null);
			return;

		}
	}




	public void populateSwiftCode(){
		setBeneSwiftCode(null);
		String swiftCode = swiftMasterService.getSwiftBicFromBankBranch(getServicebankBranchId(), getBenifisBankId());
		if(swiftCode!=null){
			setBeneSwiftCode(swiftCode);
		}

	}

	@SuppressWarnings("unused")	
	public void popbranchcode() {
		lstDataTableBankbranch.clear();
		BankBranch bankdetails = null;
		//setBenifisStateId(null);
		//setDistictId(null);
		//setCityId(null);
		setBeneficaryBankState(null);
		setBeneficaryBankDistrict(null);
		setBeneficaryBankCity(null);
		//lstDistict.clear();
		//lstCity.clear();
		populateSwiftCode();
		populateBankAccountType();
		//popStatelist();
		BranchDataTable datatable = null;
		for (BankBranchView bankBranch : lstBankbranchView) {
			datatable = new BranchDataTable();
			if (bankBranch.getBankBranchId().compareTo(getServicebankBranchId()) == 0) {
				setBankBranchCode(bankBranch.getBranchCode());
				if (bankBranch.getBranchFullName() != null) {
					datatable.setBankFullName(bankBranch.getBranchFullName());
				}
				if (bankBranch.getIfscCode() != null) {
					datatable.setBranchIFSC(bankBranch.getIfscCode());
				}
				if (bankBranch.getStateId() != null) {
					setBeneficaryBankState(bankBranch.getStateName());
					datatable.setStateName(bankBranch.getStateName());
					datatable.setStateId(bankBranch.getStateId());
				}
				if (bankBranch.getDistrictId() != null) {
					setBeneficaryBankDistrict(bankBranch.getDistrictName());
					datatable.setDistrictName(bankBranch.getDistrictName());
					datatable.setDistictId(bankBranch.getDistrictId());
				}
				if (bankBranch.getCityId() != null) {
					setBeneficaryBankCity(bankBranch.getCityName());
					datatable.setCityName(bankBranch.getCityName());
					datatable.setCityId(bankBranch.getCityId());
				}
				if (bankBranch.getSwift() != null) {
					datatable.setSwiftCode(bankBranch.getSwift());
				}
				lstDataTableBankbranch.add(datatable);
			}
		}
		//popDistict();
		//popCitylist();
	}

	@SuppressWarnings("unused")
	public void popbracnch() {
		lstDataTableBankbranch.clear();
		BankBranch bankdetails = null;
		//setBenifisStateId(null);
		//setDistictId(null);
		//setCityId(null);
		//lstDistict.clear();
		//lstCity.clear();
		populateSwiftCode();
		populateBankAccountType();
		//popStatelist();
		BranchDataTable datatable = null;
		for (BankBranchView bankBranch : lstBankbranchView) {
			datatable = new BranchDataTable();
			datatable.setBankBranchId(bankBranch.getBankBranchId());
			if (bankBranch.getBranchFullName() != null) {
				datatable.setBankFullName(bankBranch.getBranchFullName());
			}
			if (bankBranch.getIfscCode() != null) {
				datatable.setBranchIFSC(bankBranch.getIfscCode());
			}
			if (bankBranch.getStateId() != null) {
				datatable.setStateName(bankBranch.getStateName());
				datatable.setStateId(bankBranch.getStateId());
			}
			if (bankBranch.getDistrictId() != null) {
				datatable.setDistrictName(bankBranch.getDistrictName());
				datatable.setDistictId(bankBranch.getDistrictId());
			}
			if (bankBranch.getCityId() != null) {
				datatable.setCityName(bankBranch.getCityName());
				datatable.setCityId(bankBranch.getCityId());
			}
			if (bankBranch.getSwift() != null) {
				datatable.setSwiftCode(bankBranch.getSwift());
			}
			lstDataTableBankbranch.add(datatable);
		}
	}



	@SuppressWarnings("unused")
	public void popviewWindow() {
		lstDataTableBankbranch.clear();
		BankBranch bankdetails = null;
		populateSwiftCode();
		populateBankAccountType();

		if (getSearchBankStateId()!=null && getSearchBankStateId().equals(new BigDecimal(0))) {
			lstBankbranchView = beneficaryCreation.getBranchListfromViewwithStateMissing(getBenifisBankId());
		} else {
			lstBankbranchView = beneficaryCreation.getBranchListfromViewfromState(getSearchBankStateId(), getBenifisBankId());
		}

		BranchDataTable datatable = null;

		for(BankBranchView bankBranch : lstBankbranchView) {

			datatable = new BranchDataTable();

			datatable.setBankBranchId(bankBranch.getBankBranchId());

			if(bankBranch.getBranchFullName()!=null)
			{
				datatable.setBankFullName(bankBranch.getBranchFullName());
			}
			if(bankBranch.getIfscCode()!=null)
			{
				datatable.setBranchIFSC(bankBranch.getIfscCode());
			}

			if(bankBranch.getStateId()!=null)
			{
				datatable.setStateName(bankBranch.getStateName());
				datatable.setStateId(bankBranch.getStateId());
			}

			if(bankBranch.getDistrictId()!=null)
			{
				datatable.setDistrictName(bankBranch.getDistrictName());
				datatable.setDistictId(bankBranch.getDistrictId());
			}

			if(bankBranch.getCityId()!=null)
			{
				datatable.setCityName(bankBranch.getCityName());
				datatable.setCityId(bankBranch.getCityId());
			}
			if(bankBranch.getSwift()!=null)
			{
				datatable.setSwiftCode(bankBranch.getSwift());
			}

			lstDataTableBankbranch.add(datatable);
		}


		RequestContext.getCurrentInstance().execute("viewWindow.show();");
	}



	public void popCurrencylist() {	
		listCurrency = getiPersonalRemittanceService().getCurrencyList(getBenifisCountryId());
	}


	List<StateMaster> lstBankbranchViewforSrtate = new ArrayList<StateMaster>();



	public List<StateMaster> getLstBankbranchViewforSrtate() {
		return lstBankbranchViewforSrtate;
	}

	public void setLstBankbranchViewforSrtate(List<StateMaster> lstBankbranchViewforSrtate) {
		this.lstBankbranchViewforSrtate = lstBankbranchViewforSrtate;
	}

	List<BigDecimal> stateIdList = new ArrayList<BigDecimal>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void popStatelistfromBank() {
		stateIdList.clear();
		lstBankstateList.clear();
		setServicebankBranchId(null);
		setSearchBankStateId(null);
		if(lstBankbranchView!=null) {
			lstBankbranchView.clear();
		}
		lstBankbranchViewforSrtate = beneficaryCreation.getStatefromBranch(getBenifisBankId());
		System.out.println();
		List s1 = new ArrayList();
		s1.add(null);
		if(!lstBankbranchViewforSrtate.isEmpty()){
			lstBankbranchViewforSrtate.removeAll(s1);
		}
		for (StateMaster state : lstBankbranchViewforSrtate) {
			if (state.getStateId() != null)
				stateIdList.add(state.getStateId());
		}
		if(!stateIdList.isEmpty()) {
			lstBankstateList = beneficaryCreation.getBankStateList(stateIdList, sessionmanage.getLanguageId());
			StateMaster others = new StateMaster();
			others.setStateCode("OTHERS");
			others.setStateId(new BigDecimal(0));
			StateMasterDesc OTHERS = new StateMasterDesc();
			OTHERS.setFsStateMaster(others);
			OTHERS.setStateName("OTHERS");
			// OTHERS.setFsLanguageType(fsLanguageType);
			lstBankstateList.add(OTHERS);
		}

		if(lstBankstateList.isEmpty())
		{
			int count = beneficaryCreation.checkBrnachiAvailble(getBenifisBankId());
			if(count==1)
			{
				StateMaster others = new StateMaster();
				others.setStateCode("OTHERS");
				others.setStateId(new BigDecimal(0));
				StateMasterDesc OTHERS = new StateMasterDesc();
				OTHERS.setFsStateMaster(others);
				OTHERS.setStateName("OTHERS");
				// OTHERS.setFsLanguageType(fsLanguageType);
				lstBankstateList.add(OTHERS);
			}
		}
	}


	private List<StateMasterDesc> lstBankstateList = new ArrayList<StateMasterDesc>();



	public List<StateMasterDesc> getLstBankstateList() {
		return lstBankstateList;
	}

	public void setLstBankstateList(List<StateMasterDesc> lstBankstateList) {
		this.lstBankstateList = lstBankstateList;
	}

	public List<CustomerBank> getLocalBankListinCollection() {
		return localBankListinCollection;
	}

	public void popStatelist() {
		setStateName(null);
		setBenifisStateId(null);
		if(getBeneCountryid() != null ){
			lststate = generalService.getStateList(sessionmanage.getLanguageId(), getBeneCountryid());
		}else{
			setBeneCountryid(null);
		}
	}

	public void popDistict() {
		setDistrictName(null);
		setDistictId(null);
		if(getBenifisStateId() != null){
			lstDistict = generalService.getDistrictList(sessionmanage.getLanguageId(), getBeneCountryid(), getBenifisStateId());
			for (StateMasterDesc stateMaster : lststate) {
				if (stateMaster.getFsStateMaster().getStateId().compareTo(getBenifisStateId()) == 0) {
					setStateName(stateMaster.getStateName());
				}
			}
		}else{
			setStateName(null);
			setBenifisStateId(null);
		}
	}	

	public void popCitylist() {
		setCityName(null);
		setCityId(null);
		if(getDistictId() != null){
			lstCity = generalService.getCityList(sessionmanage.getLanguageId(), getBeneCountryid(), getBenifisStateId(), getDistictId());
			for (DistrictMasterDesc districtMaster : lstDistict) {
				if (districtMaster.getFsDistrictMaster().getDistrictId().compareTo(getDistictId()) == 0) {
					setDistrictName(districtMaster.getDistrict());
				}
			}
		}else{
			setDistrictName(null);
			setDistictId(null);
		}
	}

	public void cityNameset() {
		if(getCityId() != null){
			for (CityMasterDesc cityMaster : lstCity) {
				if (cityMaster.getFsCityMaster().getCityId().compareTo(getCityId()) == 0) {
					setCityName(cityMaster.getCityName());
				}
			}
		}else{
			setCityId(null);
			setCityName(null);
		}
	}

	public boolean isDisablerelation() {
		return disablerelation;
	}

	public void setDisablerelation(boolean disablerelation) {
		this.disablerelation = disablerelation;
	}

	public Map<Integer, String> getBeneficaryMap() {
		return beneficaryMap;
	}

	public void setBeneficaryMap(Map<Integer, String> beneficaryMap) {
		this.beneficaryMap = beneficaryMap;
	}

	public String getBenificaryName() {
		return benificaryName;
	}

	public void setBenificaryName(String benificaryName) {
		this.benificaryName = benificaryName;
	}

	public BigDecimal getBeneficaryBankId() {
		return beneficaryBankId;
	}

	public void setBeneficaryBankId(BigDecimal beneficaryBankId) {
		this.beneficaryBankId = beneficaryBankId;
	}

	public String getAdditionBankDetails() {
		return additionBankDetails;
	}

	public void setAdditionBankDetails(String additionBankDetails) {
		this.additionBankDetails = additionBankDetails;
	}

	public String getChargesOverseas() {
		return chargesOverseas;
	}

	public void setChargesOverseas(String chargesOverseas) {
		this.chargesOverseas = chargesOverseas;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public BigDecimal getCurrency() {
		return currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

	public BigDecimal getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(BigDecimal modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public BigDecimal getRoutingBranch() {
		return routingBranch;
	}

	public void setRoutingBranch(BigDecimal routingBranch) {
		this.routingBranch = routingBranch;
	}

	public BigDecimal getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(BigDecimal deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getBeneficaryBankState() {
		return beneficaryBankState;
	}

	public void setBeneficaryBankState(String beneficaryBankState) {
		this.beneficaryBankState = beneficaryBankState;
	}

	public String getBeneficaryBankDistrict() {
		return beneficaryBankDistrict;
	}

	public void setBeneficaryBankDistrict(String beneficaryBankDistrict) {
		this.beneficaryBankDistrict = beneficaryBankDistrict;
	}

	public String getBeneficaryBankCity() {
		return beneficaryBankCity;
	}

	public void setBeneficaryBankCity(String beneficaryBankCity) {
		this.beneficaryBankCity = beneficaryBankCity;
	}

	// For Hiding all panels except firstPanel
	public void hideAllPanels() {
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderRemittanceServicePanel(false);
		setMinagevalidation(false);
		setMainPanelRender(true);
	}

	public void personalRemittancePageNavigation() {
		hideAllPanels();
		assignNullValues();
		setCardType(null);
		setSelectCardType(0);
		setIdNumber(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BigDecimal getCustomerrefno() {
		return customerrefno;
	}

	public void setCustomerrefno(BigDecimal customerrefno) {
		this.customerrefno = customerrefno;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	public Date getCustomerExpDate() {
		return customerExpDate;
	}

	public void setCustomerExpDate(Date customerExpDate) {
		this.customerExpDate = customerExpDate;
	}

	public String getCustomerIsActive() {
		return customerIsActive;
	}

	public void setCustomerIsActive(String customerIsActive) {
		this.customerIsActive = customerIsActive;
	}

	public String getCustomerExpireDateMsg() {
		return customerExpireDateMsg;
	}

	public void setCustomerExpireDateMsg(String customerExpireDateMsg) {
		this.customerExpireDateMsg = customerExpireDateMsg;
	}

	public String getCustomerCrNumber() {
		return customerCrNumber;
	}

	public void setCustomerCrNumber(String customerCrNumber) {
		this.customerCrNumber = customerCrNumber;
	}

	/**
	 * 
	 */
	public void getCustomerDetails() {
		log.info("Entering into getCustomerDetails method ");
		if (customerDetailsList.size() != 0) {
			CustomerIdProof customerDetails = customerDetailsList.get(0);
			setCustomerName(customerDetails.getFsCustomer().getFirstName());
			setCustomerCrNumber(customerDetails.getFsCustomer().getCrNo() == null ? "" : customerDetails.getFsCustomer().getCrNo());
			setCustomerNo(customerDetails.getFsCustomer().getCustomerId());
			setCustomerrefno(customerDetails.getFsCustomer().getCustomerReference());
			setFirstName(customerDetails.getFsCustomer().getFirstName());
			setSecondName(customerDetails.getFsCustomer().getMiddleName());
			setThirdName(customerDetails.getFsCustomer().getLastName());
			setCustomerFullName(nullCheck(getFirstName()) + " " + nullCheck(getSecondName()) + " " + nullCheck(getThirdName()));
			setCustomerIsActive(customerDetails.getFsCustomer().getIsActive());
			setCustomerExpDate(customerDetails.getIdentityExpiryDate());
			if (getCustomerExpDate() != null) {
				setCustomerExpireDateMsg(new SimpleDateFormat("dd/MM/yyyy").format(getCustomerExpDate()));
			}


			setFirstLName(customerDetails.getFsCustomer().getFirstNameLocal());
			setSecondLName(customerDetails.getFsCustomer().getMiddleNameLocal());
			setThirdLName(customerDetails.getFsCustomer().getLastNameLocal());
			setFourthLName(null);
			setNationality(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			setNationalityName(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			log.info("#############################################" + customerDetails.getFsCustomer().getDateOfBirth());
			setBeneCountryid(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			checkingmandatoryOptional();
			setDateOfBrith(customerDetails.getFsCustomer().getDateOfBirth());
			/* setDateOfBrith(null); */
			String teleCountryId = generalService.getTelephoneCountryBasedOnNationality(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			setCountryCode(teleCountryId);
			setMcountryCode(teleCountryId);
			BigDecimal occupationID = generalService.getOccupationId(customerDetails.getFsCustomer().getCustomerId());
			if (occupationID != null) {
				String occupation = generalService.getOccupationDesc(occupationID,sessionmanage.getLanguageId());
				if (occupation != null) {
					setOccupation(occupation);
				}else {
					setOccupation("UN-EMPLOYEE");
				}
			} else {
				setOccupation("UN-EMPLOYEE");
			}
		}
		log.info("Exit into getCustomerDetails method ");
	}

	// first method after go clicked to fetch all customer details
	/*public void goFromOldSmartCardpanel() throws ParseException {
		log.info("Entering into goFromOldSmartCardpanel method");
		setDisableSaveBack(false);
		coustomerBeneficaryDTList.clear();
		lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
		nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
		beneCountryList.addAll(lstCountry);
		 lstCountry.clear 
		customerDetailsList = getiPersonalRemittanceService().getCustomerDetails(getIdNumber(), getSelectCard());
		if (customerDetailsList.size() > 0) {
			getCustomerDetails();
			if (getCustomerIsActive() != null && getCustomerIsActive().equalsIgnoreCase(Constants.Yes)) {
				if (getCustomerExpDate() != null && getCustomerExpDate().compareTo(new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))) >= 0) {
					if (getCustomerCrNumber() != null && !getCustomerCrNumber().equalsIgnoreCase("")) {
						setBooRenderOverseaCharges(true);
						populateCustomerDetailsFromBeneRelation();
					} else {
						setBooRenderOverseaCharges(false);
						populateCustomerDetailsFromBeneRelation();
					}
				} else {
					RequestContext.getCurrentInstance().execute("expiredCustomer.show();");
				}
			} else {
				RequestContext.getCurrentInstance().execute("notactive.show();");
			}
		} else {
			setCardType(null);
			setIdNumber(null);
			setBooRenderBenificaryFirstPanel(true);
			RequestContext.getCurrentInstance().execute("idNotFound.show();");
		}
		clearEditFields();
		log.info("Exit into goFromOldSmartCardpanel method");
	}*/

	// Second Method to Populate Records which is Approved all condition
	/*public void populateCustomerDetailsFromBeneRelation() {
		log.info("Entering into populateCustomerDetailsFromBeneRelation method ");
		List<BeneficaryRelationship> isCoustomerExist = getiPersonalRemittanceService().isCoustomerExistInDB(getCustomerNo());
		Set<BigDecimal> masterIds = new HashSet<BigDecimal>();
		for (int i = 0; i < isCoustomerExist.size(); i++) {
			masterIds.add(isCoustomerExist.get(i).getBeneficaryMaster().getBeneficaryMasterSeqId());
		}
		log.info("isCoustomerExist's size " + isCoustomerExist.size());
		coustomerBeneficaryDTList.clear();
		if (isCoustomerExist.size() > 0) {

	 * for (BeneficaryRelationship beneficaryRelationship :
	 * isCoustomerExist) { String relationName =
	 * beneficaryRelationship.getRelations
	 * ().getEnglishRelationsTypeDesc(); log.info("Relation Name " +
	 * relationName);

			Iterator<BigDecimal> entries = masterIds.iterator();
			while (entries.hasNext()) {
				BigDecimal masterId = entries.next();
				log.info("beneficaryRelationship.getBeneficaryMaster().getBeneficaryMasterSeqId()" + masterId);
				beneficaryAccountList = getiPersonalRemittanceService().getCustomerBeneficaryDetails(masterId);
				List<BeneficaryRelationship> relation = getiPersonalRemittanceService().getCustomerBeneficaryDetailswithRelations(masterId);

				for (int i = 0; i < relation.size(); i++) {
					System.out.println("valueofi is " + i);
					BeneficaryRelationship rel = relation.get(i);
					System.out.println(rel.getCustomerId().getCustomerId());
					System.out.println(rel.getBeneficaryAccount().getBankAccountNumber());
					PersonalRemmitanceBeneficaryDataTable personalRBDataTable = null;
					System.out.println("CCCCCCCCCCC" + getCustomerNo());
					System.out.println("DB custo" + rel.getCustomerId().getCustomerId());
					if (!rel.getCustomerId().getCustomerId().equals(getCustomerNo())) {
						System.out.println("EEEEEEEEEEEEEEEEEEEEEE" + rel.getRelations().getEnglishRelationsTypeDesc());
						if (rel.getRelations().getEnglishRelationsTypeDesc().equalsIgnoreCase("SELF")) {
							continue;
						}
					}

					personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();
					personalRBDataTable.setBenificaryMasterId(rel.getBeneficaryMaster().getBeneficaryMasterSeqId());
					personalRBDataTable.setBenificaryAccDetailsId(rel.getBeneficaryAccount().getBeneficaryAccountSeqId());
					personalRBDataTable.setBenificaryCountryId(rel.getBeneficaryAccount().getBeneficaryCountry().getCountryId());
					personalRBDataTable.setBenificaryCountryName(rel.getBeneficaryAccount().getBeneficaryCountry().getFsCountryMasterDescs().get(0).getCountryName());
					personalRBDataTable.setAccountNo(rel.getBeneficaryAccount().getBankAccountNumber());
					personalRBDataTable.setBenificaryName(rel.getBeneficaryAccount().getBeneficaryMaster().getFirstName());
					personalRBDataTable.setBranchNameId(rel.getBeneficaryAccount().getBankBranch().getBankBranchId());
					personalRBDataTable.setBankName((rel.getBeneficaryAccount().getBank().getBankFullName()));
					personalRBDataTable.setBankNameId(rel.getBeneficaryAccount().getBank().getBankId());
					//	personalRBDataTable.setBankNameId(rel.getBeneficaryAccount().getBank().getBankId());
					personalRBDataTable.setBranchName((rel.getBeneficaryAccount().getBankBranch().getBranchFullName()));
					personalRBDataTable.setLocation((rel.getBeneficaryAccount().getBeneficaryMaster().getNationality()));
					personalRBDataTable.setServiceName((rel.getBeneficaryAccount().getServicegropupId().getServiceGroupCode()));
					personalRBDataTable.setServiceNameId((rel.getBeneficaryAccount().getServicegropupId().getServiceGroupId()));
					personalRBDataTable.setCurrencyId((rel.getBeneficaryAccount().getCurrencyId().getCurrencyId()));
					personalRBDataTable.setCurrencyName((rel.getBeneficaryAccount().getCurrencyId().getCurrencyName()));
					personalRBDataTable.setBranchLocation((rel.getBeneficaryAccount().getBankBranch().getLocation()));
					personalRBDataTable.setRelationName(rel.getRelations().getEnglishRelationsTypeDesc());
					List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"), rel.getBeneficaryAccount().getServicegropupId()
							.getServiceGroupId());
					if (lstServiceGroupMasterDesc.size() > 0) {
						personalRBDataTable.setServiceName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
					}
					personalRBDataTable.setBenificaryCountryName(generalService.getCountryName(sessionmanage.getLanguageId(), rel.getBeneficaryAccount().getBeneficaryCountry().getCountryId()));
					coustomerBeneficaryDTList.add(personalRBDataTable);
				}
			}
		}
		backToBenificaryListScreen();
		log.info("Exit into populateCustomerDetailsFromBeneRelation method ");
	}*/

	public void exitFromBeneficaryCreation() {
		clear();
		setRenderBackButton(true);
		setBooRenderIndBenificaryStatusPanel(false);
		setBeneficaryStatusId(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("exitBeneficary", "yes");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancelBooRenderIndBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancelBooRenderNonIndBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nextFromBooRenderBenificarySearchPanel() {
		beneficiaryStatusList();
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(true);
		setBooRenderIndBenificaryStatusPanel(false);
		setBeneficaryStatusId(null);
		setRenderBackButton(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gotToNewBenificaryCreation() {
		beneficiaryStatusList();
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(true);
		setBooRenderIndBenificaryStatusPanel(false);
		setBeneficaryStatusId(null);
		setRenderBackButton(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiaryCreation.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Boolean booDisableBeneficaryType;

	public Boolean getBooDisableBeneficaryType() {
		return booDisableBeneficaryType;
	}

	public void setBooDisableBeneficaryType(Boolean booDisableBeneficaryType) {
		this.booDisableBeneficaryType = booDisableBeneficaryType;
	}

	public void changeIndidualorNonIndidual() {
		lststate.clear();
		clear();
		setBooDisableBeneficaryType(false);
		for (BeneficaryStatus beneficarystatus : benificaryStatusName) {
			if (beneficarystatus.getBeneficaryStatusId().compareTo(getBeneficaryStatusId()) == 0) {
				setBeneficaryStatusName(beneficarystatus.getBeneficaryStatusName());
				System.out.println("##########" + getBeneficaryStatusName());
				Calendar cal1 = new GregorianCalendar();
				cal1.setTime(new Date());
				// cal1.add(Calendar.YEAR,
				// -Constants.AGE_LIMIT);
				cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
				Date today18 = cal1.getTime();
				SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
				String minDateFinal = form1.format(today18);
				setEffectiveMinDate(minDateFinal);

				if (getBeneficaryStatusName().equals("Individual")) {
					setBeneficaryStatusId(new BigDecimal(1));

					List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.SELF);

					Boolean checkExist = null;
					if (selfid != null) {
						checkExist = beneficaryCreation.checkSelfAlreadyExistorNot(getCustomerNo(), selfid.get(0).getRelations().getRelationsId());
					}
					if(!checkExist){
						setBeneficaryTypeId(new BigDecimal(1));
					}
					else
					{
						setBeneficaryTypeId(new BigDecimal(2));  
					}
					checkBeneficaryType();
					setMinagevalidation(false);
					setRenderBackButton(false);
					setBooRenderBenificaryFirstPanel(false);
					setBooRenderBenificarySearchPanel(false);
					setBooRenderBenificaryStatusPanel(true);
					setBooRenderIndBenificaryStatusPanel(true);
					//setBeneficaryTypeId(new BigDecimal(1));
					setReadOnlyYearOfBirth(false);
					setReadOnlyAge(false);
					System.out.println( "lststate size" +lststate.size());
					if(lststate.size() != 0){
						setLststate(lststate);
					}

					break;
				}
				/*
				 * if (getBeneficaryStatusName().equals(
				 * "Non-Individual")) {
				 * setBeneficaryStatusId(null); try {
				 * FacesContext.getCurrentInstance
				 * ().getExternalContext().redirect (
				 * "../beneficary/beneficiaryCreationNonIndidual.xhtml"
				 * ); } catch (IOException e) {
				 * e.printStackTrace(); }
				 * 
				 * return; }
				 */
				else if (getBeneficaryStatusName().equals("Non-Individual")) {
					setBooDisableBeneficaryType(true);
					setBeneficaryStatusId(new BigDecimal(2));
					setBeneficaryTypeId(new BigDecimal(2));
					checkBeneficaryType();
					setMinagevalidation(false);
					setRenderBackButton(false);
					setBooRenderBenificaryFirstPanel(false);
					setBooRenderBenificarySearchPanel(false);
					setBooRenderBenificaryStatusPanel(true);
					setBooRenderIndBenificaryStatusPanel(true);
					setBooRenderIndBenificaryStatusPanel(true);
					/*  setReadOnlyYearOfBirth(true);
						  setReadOnlyAge(true);*/

					setReadOnlyYearOfBirth(false);
					setReadOnlyAge(false);

					System.out.println( "lststate size" +lststate.size());
					if(lststate.size() != 0){
						setLststate(lststate);
					}

					break;
				}
				else if (getBeneficaryStatusName().equals("Corporate")) {
					setBooDisableBeneficaryType(true);
					setBeneficaryStatusId(new BigDecimal(3));
					setBeneficaryTypeId(new BigDecimal(2));
					checkBeneficaryType();
					setMinagevalidation(false);
					setRenderBackButton(false);
					setBooRenderBenificaryFirstPanel(false);
					setBooRenderBenificarySearchPanel(false);
					setBooRenderBenificaryStatusPanel(true);
					setBooRenderIndBenificaryStatusPanel(true);
					setBooRenderIndBenificaryStatusPanel(true);

					System.out.println( "lststate size" +lststate.size());
					if(lststate.size() != 0){
						setLststate(lststate);
					}

					break;
				}
			}
		}
		/*beneficaryMap.put(1, "Self");
		beneficaryMap.put(2, "Others");
		int statusId = Integer.parseInt(getBeneficaryStatusId().toString());
		if (statusId == 1) {
			setMinagevalidation(false);
			setRenderBackButton(false);
			setBeneficaryTypeId(new BigDecimal(1));
			checkBeneficaryType();
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBooRenderBenificaryStatusPanel(true);
			setBooRenderIndBenificaryStatusPanel(true);
			setBeneficaryTypeId(new BigDecimal(0));
			setFirstName(null);
			setSecondName(null);
			setThirdName(null);
			 @Added clear data 28/01/2015 start 
			setFourthName(null);
			setFifthName(null);
			 @Ended 28/01/2015 
			setAddress(null);
			setNationality(null);
			setNationalityName(null);
			setTelephoneNumber(null);
			setMobileNumber(null);
			setDateOfBrith(null);
			setAddress(null);
			setRelationId(null);
			// setNationalityName(null);
			setCountryCode(null);
			setMcountryCode(null);
			setYearOfBrith(null);
			setAge(null);
		} else if (statusId == 2) {
			checkBeneficaryType();
			setRenderBackButton(false);
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBooRenderBenificaryStatusPanel(true);
			setBooRenderIndBenificaryStatusPanel(false);
			setBeneficaryTypeId(new BigDecimal(2));
			setDisableBeneficaryType(true);
			setReadOnlyFirstName(false);
			setReadOnlySecondName(false);
			setReadOnlyThirdName(false);
			setReadOnlyAddress(false);
			setReadOnlyNationality(false);
			// setReadOnlyPhoneNo(false);
			setFirstName(null);
			setSecondName(null);
			setThirdName(null);
			 @Added clear data 28/01/2015 start 
			setFourthName(null);
			setFifthName(null);
			 @Ended 28/01/2015 
			setAddress(null);
			setNationality(null);
			setNationalityName(null);
			setTelephoneNumber(null);
			setMobileNumber(null);
		} else if (statusId == 0) {
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBooRenderBenificaryStatusPanel(true);
			setBooRenderIndBenificaryStatusPanel(false);
			setMinagevalidation(false);
			setBeneficaryStatusId(null);
			setFirstName(null);
			setSecondName(null);
			setThirdName(null);
			setAddress(null);
			setNationality(null);
			setNationalityName(null);
			setTelephoneNumber(null);
			setMobileNumber(null);
			setDateOfBrith(null);
			setAddress(null);
			setRelationId(null);
			// setNationalityName(null);
			setCountryCode(null);
			setMcountryCode(null);
			setYearOfBrith(null);
			setAge(null);
		}*/
	}

	public void nextBooRenderIndBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nextBooRenderNonIndBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static int calculateMyAge(int year, int month, int day) {
		Calendar birthCal = new GregorianCalendar(year, month, day);
		Calendar presentCal = new GregorianCalendar();
		int age = presentCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
		boolean isMonthGreater = birthCal.get(Calendar.MONTH) >= presentCal.get(Calendar.MONTH);
		boolean isMonthSameButDayGreater = birthCal.get(Calendar.MONTH) == presentCal.get(Calendar.MONTH) && birthCal.get(Calendar.DAY_OF_MONTH) > presentCal.get(Calendar.DAY_OF_MONTH);
		if (isMonthGreater || isMonthSameButDayGreater) {
			age = age - 1;
		}
		System.out.println("%%%%%%%%%%age%%%%%%%%%%%" + age);
		return age;
	}

	public boolean getDisableNationality() {
		return disableNationality;
	}

	public void setDisableNationality(boolean disableNationality) {
		this.disableNationality = disableNationality;
	}

	public void checkBeneficaryType() {
		int benfiTypeId = 0;
		if (getBeneficaryTypeId() != null)
			benfiTypeId = getBeneficaryTypeId().intValue();
		if (benfiTypeId == 2) {

			setFirstName(null);
			setSecondName(null);
			setThirdName(null);
			setFourthName(null);
			setFifthName(null);
			setFirstLName(null);
			setSecondLName(null);
			setThirdLName(null);
			setFourthLName(null);
			setRelationId(null);
			//setNationality(null);
			setNationalityName(null);
			setCountryCode(null);
			setTelephoneNumber(null);
			setMcountryCode(null);
			setMobileNumber(null);
			setOccupation(null);
			setDateOfBrith(null);
			setAge(null);
			setYearOfBrith(null);
			setBeneCountryid(null);
			setBenifisStateId(null);
			setDistictId(null);
			setCityId(null);
			//setAddress(null);
			setBeneficaryBankState(null);
			setBeneficaryBankDistrict(null);
			setBeneficaryBankCity(null);
			lststate.clear();
			lstDistict.clear();
			lstCity.clear();
			setReadOnlyOccupation(false);
			setMinagevalidation(false);
			setDisablerelation(false);
			setReadOnlyFirstName(false);
			setReadOnlySecondName(false);
			setReadOnlyThirdName(false);
			// setReadOnlyPhoneNo(false);
			setReadOnlyAddress(false);
			setReadOnlyNationality(false);
			setReadOnlyRelations(false);
			setReadOnlyFifthName(false);
			setReadOnlyFourthName(false);
			setReadOnlyDateOfBirth(false);
			setReadOnlyYearOfBirth(false);
			setReadOnlyAge(false);
			setDisableDataOfBirth(false);
			if (getDateOfBrith() != null) {
				setDisableResetDataOfBirth(false);
			}
			setDisablerelation(false);
			setDisableNationality(false);
			if (!getBeneficaryStatusName().equals(Constants.CUSTOMERTYPE_INDIVIDUAL)) {
				List<RelationsDescription> tempList = new ArrayList<RelationsDescription>();
				List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.OTHERS);
				for (RelationsDescription relationsDescription : selfid) {
					tempList.add(relationsDescription);
					BigDecimal selfPK = relationsDescription.getRelations().getRelationsId();
					setRelationId(selfPK);
				}
				relationDescList.clear();
				relationDescList.addAll(tempList);
				tempList.clear();
				setDisablerelation(true);
			} else {
				relationDescList = getiPersonalRemittanceService().getRelationsDescriptionList(sessionmanage.getLanguageId());
				List<RelationsDescription> tempList = new ArrayList<RelationsDescription>();
				for (RelationsDescription relationsDescription : relationDescList) {
					if (!relationsDescription.getLocalRelationsDescription().equalsIgnoreCase(Constants.SELF)) {
						tempList.add(relationsDescription);
					}
				}
				relationDescList.clear();
				relationDescList.addAll(tempList);
				tempList.clear();
			}
		} else if (benfiTypeId == 1) {
			//customerDetailsList = getiPersonalRemittanceService().getCustomerDetails(getIdNumber(), getSelectCard());

			BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionmanage.getLanguageId())
					.getFsBizComponentData().getComponentDataId();

			BigDecimal identityTpeIds = generalService.getComponentId(Constants.CIVILID, sessionmanage.getLanguageId())
					.getFsBizComponentData().getComponentDataId();

			if (getSelectCard() != null && idtypeCivilIdnew != null && getSelectCard().intValue() == identityTpeIds.intValue()
					|| getSelectCard().intValue() == idtypeCivilIdnew.intValue()) {

				customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), identityTpeIds);

				if (customerDetailsList.size() == 0) {
					customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idtypeCivilIdnew);
				}
			} else {

				customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), getSelectCard());
			}

			if (customerDetailsList.size() > 0 && customerDetailsList != null) {

				setBeneficaryBankState(null);
				setBeneficaryBankDistrict(null);
				setBeneficaryBankCity(null);
				setBeneCountryid(null);
				setBenifisStateId(null);
				setDistictId(null);
				setCityId(null);
				lststate.clear();
				lstDistict.clear();
				lstCity.clear();
				setFirstName(null);
				setSecondName(null);
				setThirdName(null);
				setAddress(null);
				setNationality(null);
				setNationalityName(null);
				setTelephoneNumber(null);
				setMobileNumber(null);
				setAge(null);
				setYearOfBrith(null);
				setDateOfBrith(null);
				setRelationId(null);
				setFifthName(null);
				setFourthName(null);
				setCountryCode(null);
				setMcountryCode(null);
				getCustomerDetails();
				checkRelationExistFromDb();
				setReadOnlyFirstName(true);
				setReadOnlySecondName(true);
				setReadOnlyThirdName(true);
				// setReadOnlyPhoneNo(true);
				setReadOnlyAddress(true);
				setReadOnlyNationality(true);
				setReadOnlyRelations(true);
				setReadOnlyFifthName(true);
				setReadOnlyFourthName(true);
				setReadOnlyDateOfBirth(false);
				setReadOnlyYearOfBirth(false);
				setReadOnlyAge(false);
				setDisableDataOfBirth(true);
				setDisableResetDataOfBirth(true);
				List<RelationsDescription> tempList = new ArrayList<RelationsDescription>();
				List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.SELF);
				for (RelationsDescription relationsDescription : selfid) {
					tempList.add(relationsDescription);
					BigDecimal selfPK = relationsDescription.getRelations().getRelationsId();
					setRelationId(selfPK);
				}
				relationDescList.clear();
				relationDescList.addAll(tempList);
				tempList.clear();
				setDisablerelation(true);
				setDisableNationality(true);
				setReadOnlyOccupation(true);
				// fetch state details
				popStatelist();
			}
		} else if (benfiTypeId == 0) {
		}


	}


	private BeneficaryMaster beneficaryMasterSave(BigDecimal masterseqId) {
		BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
		beneficaryMaster.setBeneficaryMasterSeqId(masterseqId);
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionmanage.getCountryId());
		beneficaryMaster.setApplicationCountryId(countryMaster);
		CountryMaster countryMaster1 = new CountryMaster();
		countryMaster1.setCountryId(getBeneCountryid());
		beneficaryMaster.setFsCountryMaster(countryMaster1);
		beneficaryMaster.setFirstName(getFirstName());
		beneficaryMaster.setSecondName(getSecondName());
		beneficaryMaster.setThirdName(getThirdName());
		beneficaryMaster.setFourthName(getFourthName());
		beneficaryMaster.setFifthName(getFifthName());
		BeneficaryStatus beneficaryStatus = new BeneficaryStatus();
		beneficaryStatus.setBeneficaryStatusId(getBeneficaryStatusId());
		beneficaryMaster.setBeneficaryStatus(beneficaryStatus);
		beneficaryMaster.setNationality(getNationalityName().toString());
		beneficaryMaster.setDateOfBrith(getDateOfBrith());
		beneficaryMaster.setYearOfBrith(getYearOfBrith());
		beneficaryMaster.setAge(getAge());
		if (getBenifisStateId() != null) {
			StateMaster stateMaster = new StateMaster();
			stateMaster.setStateId(getBenifisStateId());
			beneficaryMaster.setFsStateMaster(stateMaster);
			beneficaryMaster.setStateName(getStateName());
		}
		if (getDistictId() != null) {
			DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(getDistictId());
			beneficaryMaster.setFsDistrictMaster(districtMaster);
			beneficaryMaster.setDistrictName(getDistrictName());
		}
		if (getCityId() != null) {
			CityMaster cityMaster = new CityMaster();
			cityMaster.setCityId(getCityId());
			beneficaryMaster.setFsCityMaster(cityMaster);
			beneficaryMaster.setCityName(getCityName());
		}
		beneficaryMaster.setOccupation(getOccupation());
		beneficaryMaster.setNoOfRemittance(getNoOfRemittance());
		beneficaryMaster.setIsActive(Constants.Yes);
		beneficaryMaster.setCreatedBy(sessionmanage.getUserName());
		beneficaryMaster.setCreatedDate(new Date());
		beneficaryMaster.setLocalFirstName(getFirstLName());
		beneficaryMaster.setLocalSecondName(getSecondLName());
		beneficaryMaster.setLocalThirdName(getThirdLName());
		beneficaryMaster.setLocalFourthName(getFourthLName());
		if (getBeneficaryStatusName().equals(Constants.CUSTOMERTYPE_INDIVIDUAL)) {
			beneficaryMaster.setBeneficaryStatusName(Constants.Individual);
		} else {
			beneficaryMaster.setBeneficaryStatusName(Constants.NonIndividual);
		}
		//	getiPersonalRemittanceService().saveBeneficiaryMaster(beneficaryMaster);
		return beneficaryMaster;
	}
	private BeneficaryMaster beneficaryMasterSave() {
		BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionmanage.getCountryId());
		beneficaryMaster.setApplicationCountryId(countryMaster);
		CountryMaster countryMaster1 = new CountryMaster();
		countryMaster1.setCountryId(getBeneCountryid());
		beneficaryMaster.setFsCountryMaster(countryMaster1);
		beneficaryMaster.setFirstName(getFirstName());
		beneficaryMaster.setSecondName(getSecondName());
		beneficaryMaster.setThirdName(getThirdName());
		beneficaryMaster.setFourthName(getFourthName());
		beneficaryMaster.setFifthName(getFifthName());
		BeneficaryStatus beneficaryStatus = new BeneficaryStatus();
		beneficaryStatus.setBeneficaryStatusId(getBeneficaryStatusId());
		beneficaryMaster.setBeneficaryStatus(beneficaryStatus);
		beneficaryMaster.setNationality(getNationalityName().toString());
		beneficaryMaster.setDateOfBrith(getDateOfBrith());
		beneficaryMaster.setYearOfBrith(getYearOfBrith());
		beneficaryMaster.setAge(getAge());
		if(getBenifisStateId() != null){
			StateMaster stateMaster = new StateMaster();
			stateMaster.setStateId(getBenifisStateId());
			beneficaryMaster.setFsStateMaster(stateMaster);
			beneficaryMaster.setStateName(getStateName());
		}
		if(getDistictId() != null){
			DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(getDistictId());
			beneficaryMaster.setFsDistrictMaster(districtMaster);
			beneficaryMaster.setDistrictName(getDistrictName());
		}
		if(getCityId() != null){
			CityMaster cityMaster = new CityMaster();
			cityMaster.setCityId(getCityId());
			beneficaryMaster.setFsCityMaster(cityMaster);
			beneficaryMaster.setCityName(getCityName());
		}
		beneficaryMaster.setOccupation(getOccupation());
		beneficaryMaster.setNoOfRemittance(getNoOfRemittance());
		beneficaryMaster.setIsActive(Constants.Yes);
		beneficaryMaster.setCreatedBy(sessionmanage.getUserName());
		beneficaryMaster.setCreatedDate(new Date());
		beneficaryMaster.setLocalFirstName(getFirstLName());
		beneficaryMaster.setLocalSecondName(getSecondLName());
		beneficaryMaster.setLocalThirdName(getThirdLName());
		beneficaryMaster.setLocalFourthName(getFourthLName());
		if (getBeneficaryStatusName()!=null && getBeneficaryStatusName().equals(Constants.CUSTOMERTYPE_INDIVIDUAL)) {
			beneficaryMaster.setBeneficaryStatusName(Constants.Individual);
		} else {
			beneficaryMaster.setBeneficaryStatusName(Constants.NonIndividual);
		}
		//getiPersonalRemittanceService().saveBeneficiaryMaster(beneficaryMaster);
		return beneficaryMaster;
	}

	// For Save Beneficary Relation
	public BeneficaryRelationship saveBeneficaryRelation(BeneficaryMaster beneficaryMaster, BeneficaryAccount beneficaryAccount) {
		List<BeneficaryRelationship> objBeneficaryRelationshipList = getiPersonalRemittanceService().isBenificaryRelationExist(beneficaryMaster.getBeneficaryMasterSeqId(), beneficaryAccount.getBeneficaryAccountSeqId());
		log.info("@@@@@@@@@@@@@" + getRelationId());
		List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.SELF);

		BigDecimal selfRelationid  = selfid.get(0).getRelations().getRelationsId();

		//	List<BeneficaryRelationship> objBeneficaryRelationshipList = beneficaryCreation.isBenificaryRelationExist(beneficaryMaster.getBeneficaryMasterSeqId(), getRelationId());

		BeneficaryRelationship beneficaryRelationship = new BeneficaryRelationship();

		if(objBeneficaryRelationshipList.size() > 0  && selfRelationid!= null && selfRelationid.equals(objBeneficaryRelationshipList.get(0).getRelations().getRelationsId())
				&& getBeneficaryTypeId().equals(new BigDecimal(2))	)
		{

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneficaryRelationship.setApplicationCountry(countryMaster);
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerNo());
			beneficaryRelationship.setCustomerId(customer);
			Relations relations = new Relations();
			relations.setRelationsId(getRelationId());
			beneficaryRelationship.setRelations(relations);
			beneficaryRelationship.setIsActive(Constants.Yes);
			beneficaryRelationship.setCreatedBy(sessionmanage.getUserName());
			beneficaryRelationship.setCreatedDate(new Date());
			beneficaryRelationship.setBeneficaryAccount(beneficaryAccount);
			beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
			//getiPersonalRemittanceService().saveBeneficiaryRelationship(beneficaryRelationship);
		}
		else if (objBeneficaryRelationshipList.size() > 0 ) {
			System.out.println("Condition in side ----------------------------------------------------- > ");
			if(objBeneficaryRelationshipList.get(0).getCustomerId().getCustomerId().equals(getCustomerNo()))
			{
				beneficaryRelationship.setBeneficaryRelationshipId((objBeneficaryRelationshipList.get(0).getBeneficaryRelationshipId()));
			}
			else
			{
				System.out.println("###############################different customer#########################");
			}
			beneficaryRelationship.setApplicationCountry(objBeneficaryRelationshipList.get(0).getApplicationCountry());
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerNo());
			beneficaryRelationship.setCustomerId(customer);
			Relations relations = new Relations();
			relations.setRelationsId(getRelationId());
			beneficaryRelationship.setRelations(relations);
			beneficaryRelationship.setIsActive(Constants.Yes);
			beneficaryRelationship.setCreatedBy(sessionmanage.getUserName());
			beneficaryRelationship.setCreatedDate(new Date());
			beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
			beneficaryRelationship.setBeneficaryAccount(beneficaryAccount);
			System.out.println("Condition in side ----------------------------------------------------- > before save ");
			//getiPersonalRemittanceService().saveBeneficiaryRelationship(beneficaryRelationship);
		} else {
			System.out.println("Beneficicary Relation else ---------------------------------- > ");
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneficaryRelationship.setApplicationCountry(countryMaster);
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerNo());
			beneficaryRelationship.setCustomerId(customer);
			Relations relations = new Relations();
			relations.setRelationsId(getRelationId());
			beneficaryRelationship.setRelations(relations);
			beneficaryRelationship.setIsActive(Constants.Yes);
			beneficaryRelationship.setCreatedBy(sessionmanage.getUserName());
			beneficaryRelationship.setCreatedDate(new Date());
			beneficaryRelationship.setBeneficaryAccount(beneficaryAccount);
			beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
			//getiPersonalRemittanceService().saveBeneficiaryRelationship(beneficaryRelationship);
		}

		return beneficaryRelationship;
	}
	// For Save Beneficary Telephone
	public BeneficaryContact saveBeneficaryTelephone(BeneficaryMaster beneficaryMaster) {
		List<BeneficaryAccount> beneficaryAccounts = getiPersonalRemittanceService().isBeneficaryAccountExistInDb(beneficaryMaster.getBeneficaryMasterSeqId());
		BeneficaryContact beneficaryTelaphone = new BeneficaryContact();
		if (beneficaryAccounts.size() > 0) {
			System.out.println("Condition in side ----------------------------------------------------- >3 ");
			beneficaryTelaphone.setApplicationCountryId(beneficaryAccounts.get(0).getBeneApplicationCountry());
			if (getTelephoneNumber() != null) {
				beneficaryTelaphone.setTelephoneNumber(getTelephoneNumber().toString());
			} else {
				beneficaryTelaphone.setTelephoneNumber(null);
			}
			beneficaryTelaphone.setIsActive(Constants.Yes);
			beneficaryTelaphone.setCreatedBy(sessionmanage.getUserName());
			beneficaryTelaphone.setCreatedDate(new Date());
			beneficaryTelaphone.setBeneficaryMaster(beneficaryMaster);
			beneficaryTelaphone.setCountryTelCode(getCountryCode());
			beneficaryTelaphone.setMobileNumber(getMobileNumber());
			System.out.println("Condition in side -----------------------------------------------------  before save 4");
			// getiPersonalRemittanceService().saveBeneficiaryTelephone(beneficaryTelaphone);
		} else {
			System.out.println("Beneficicary Telephone ---------------------------------- > ");
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneficaryTelaphone.setApplicationCountryId(countryMaster);
			beneficaryTelaphone.setCountryTelCode(getCountryCode());
			if (getTelephoneNumber() != null) {
				beneficaryTelaphone.setTelephoneNumber(getTelephoneNumber().toString());
			} else {
				beneficaryTelaphone.setTelephoneNumber(null);
			}
			beneficaryTelaphone.setIsActive(Constants.Yes);
			beneficaryTelaphone.setCreatedBy(sessionmanage.getUserName());
			beneficaryTelaphone.setCreatedDate(new Date());
			beneficaryTelaphone.setBeneficaryMaster(beneficaryMaster);
			beneficaryTelaphone.setMobileNumber(getMobileNumber());
			// getiPersonalRemittanceService().saveBeneficiaryTelephone(beneficaryTelaphone);
		}
		return beneficaryTelaphone;
	}
	// For Save Beneficary Account Detail
	public BeneficaryAccount saveBeneficaryAccount(BeneficaryMaster beneficaryMaster) {
		BeneficaryAccount beneficaryAccount = new BeneficaryAccount();
		try{
			if (getServiceGroupId() != null && lstfetchCashId!=null &&  !(getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0)) {
				beneficaryAccount.setBeneficaryAccountSeqId(getBeneficaryAccountSeqId());
				CountryMaster countryMaster = new CountryMaster();
				// countryMaster.setCountryId(sessionmanage.getCountryId());
				countryMaster.setCountryId(sessionmanage.getCountryId());
				beneficaryAccount.setBeneApplicationCountry(countryMaster);
				CountryMaster countryMaster1 = new CountryMaster();
				countryMaster1.setCountryId(getBenifisCountryId());
				beneficaryAccount.setBeneficaryCountry(countryMaster1);
				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(getBenifisBankId());
				beneficaryAccount.setBank(bankMaster);
				BankBranch bankBranch = new BankBranch();
				bankBranch.setBankBranchId(getServicebankBranchId());
				beneficaryAccount.setBankBranch(bankBranch);
				ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
				serviceGroupMaster.setServiceGroupId(getServiceGroupId());
				beneficaryAccount.setServicegropupId(serviceGroupMaster);
				beneficaryAccount.setBankAccountNumber(getBankAccountNumber());
				beneficaryAccount.setBankAccountTypeId(getBankAccountType());
				beneficaryAccount.setSwiftCode(getBeneSwiftCode());
				beneficaryAccount.setBankCode(getBankCode());
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(getBenifisCurrencyId());
				beneficaryAccount.setCurrencyId(currencyMaster);
				beneficaryAccount.setBankBranchCode(getBankBranchCode());
				beneficaryAccount.setAliasFirstName(getAliasFirstName());
				beneficaryAccount.setAliasSecondName(getAliasSecondName());
				beneficaryAccount.setAliasThirdName(getAliasThirdName());
				beneficaryAccount.setAliasFourthName(getAliasFourthName());
				beneficaryAccount.setIsActive(Constants.Yes);
				beneficaryAccount.setCreatedBy(sessionmanage.getUserName());
				beneficaryAccount.setCreatedDate(new Date());

				beneficaryAccount.setBeneficaryMaster(beneficaryMaster);
				//	getiPersonalRemittanceService().saveBeneficiaryAccount(beneficaryAccount);
			} else {
				//	beneficaryAccount = new BeneficaryAccount();
				//app country
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(sessionmanage.getCountryId());
				beneficaryAccount.setBeneApplicationCountry(countryMaster);
				//bene country
				CountryMaster countryMaster1 = new CountryMaster();
				countryMaster1.setCountryId(getBenifisCountryId());
				beneficaryAccount.setBeneficaryCountry(countryMaster1);
				//service group 
				ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
				serviceGroupMaster.setServiceGroupId(getServiceGroupId());
				beneficaryAccount.setServicegropupId(serviceGroupMaster);

				if (getAgentMaster() != null) {
					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(getAgentMaster());
					beneficaryAccount.setBank(bankMaster);
					BankBranch bankBranch = new BankBranch();
					bankBranch.setBankBranchId(getAgentBranch());
					beneficaryAccount.setBankBranch(bankBranch);
					BankMaster bankMasterServiceProvider = new BankMaster();
					bankMasterServiceProvider.setBankId(getServiceTypeId());
					beneficaryAccount.setServiceProvider(bankMasterServiceProvider);
					if(getServiceProviderBankBranchId() != null){
						beneficaryAccount.setServiceProviderBranchId(getServiceProviderBankBranchId());
					}
				}

				if(getBeneficaryAccountSeqId() != null){
					beneficaryAccount.setBeneficaryAccountSeqId(getBeneficaryAccountSeqId());
					beneficaryAccount.setIsActive(Constants.Yes);
				}else{
					beneficaryAccount.setCreatedBy(sessionmanage.getUserName());
					beneficaryAccount.setCreatedDate(new Date());
					beneficaryAccount.setIsActive(Constants.Yes);
				}
				if(getBenifisCurrencyId() != null){
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(getBenifisCurrencyId());
					beneficaryAccount.setCurrencyId(currencyMaster);
				}
				beneficaryAccount.setBankCode(getBankCode());
				beneficaryAccount.setBankBranchCode(getBankBranchCode());

				beneficaryAccount.setBeneficaryMaster(beneficaryMaster);
				//getiPersonalRemittanceService().saveBeneficiaryAccount(beneficaryAccount);
			}
		}catch(Exception e){
			setErrmsg("This combination already exist in datatable");
			RequestContext.getCurrentInstance().execute("csp.show();");		
		}

		return beneficaryAccount;
	}
	public void saveBeneficaryCreation() throws ParseException {
		saveBeneficaryDetails();
	}
	public void backFromRemmitanceServicePanel() {
		setBooRenderTypeOfServicePanel(true);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderRemittanceServicePanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryFirstPanel(false);
	}

	Boolean localLanaguageSelection ;


	public Boolean getLocalLanaguageSelection() {
		return localLanaguageSelection;
	}

	public void setLocalLanaguageSelection(Boolean localLanaguageSelection) {
		this.localLanaguageSelection = localLanaguageSelection;
	}

	public void backTobeneficiaryDetails() {
		try {
			log.info("Page redirect to bankacccountdetails page");

			if( getBooAotherLang()==null || getBooAotherLang()) {
				setBenifisCountryId(null);
				setServiceGroupId(null);
				setBooenableAgentPanel(true);
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiaryCreation.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}


	public void backTobeneficiaryDetailsfromDifferentService() {
		try {
			log.info("Page redirect to bankacccountdetails page");

			if( getBooAotherLang()==null || getBooAotherLang()) {
				setBenifisCountryId(null);
				setServiceGroupId(null);
				setBooenableAgentPanel(true);
			}
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromDifferntService", "yes");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiaryCreation.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}



	public void nextToRemmitancePanel() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderRemittanceServicePanel(true);
		setBooRenderTypeOfServicePanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderBenificarySearchPanel(false);
	}

	public void backToBenificaryListScreen() {
		try {
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(null);
			setDisableSaveBack(false);
			log.info("Page redirect to bankacccountdetails page");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	public void gotoBeneficaryAccountDetails() {

		log.info("111 :"+firstName+"\t seacond Name :"+secondName+"\t last name :"+thirdName);

		if(getBooAotherLang()!=null && !getBooAotherLang())
		{
			if(getFirstLName()==null || getFirstLName()!=null && getFirstLName().length()==0  ||  getSecondLName()!=null && getSecondLName().length()==0  ) {
				RequestContext.getCurrentInstance().execute("localLang.show();");
				setErrmsg("Please enter Local Arabic first Name and Local Arabic Second Name");
				return;
			}
		}

		// blocked because date of birth , year of birth and age is optional
		/*if(getDateOfBrith() == null && getYearOfBrith() == null && getAge() == null)
		{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please Select Any one Of DateOfBirth / YearOfBirth / Age");
			return;
		}*/


		/*
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDateOfBrith());
		int tempdobyear = calendar.get(Calendar.YEAR);
		if (getBeneficaryTypeId().equals(new BigDecimal(2)) && !getYearOfBrith().equals(new BigDecimal(tempdobyear))) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Date of Birth and year of birth are not matching ");
			return;
		}
		calendar = null;
		calendar = Calendar.getInstance();
		int yrNow = calendar.get(Calendar.YEAR);
		int findage = yrNow - tempdobyear;
		if (getBeneficaryTypeId().equals(new BigDecimal(2)) && !getAge().equals(new BigDecimal(findage))) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Age is not mathcing with Date of Birth and year of birth");
			return;
		}
		 */
		String engName =firstName+" "+ secondName+" "+thirdName;
		String localName =firstLName+" "+ secondLName+" "+thirdLName;
		//System.out.println("engName :"+engName);
		//System.out.println("localName :"+localName);
		/** added by Rabil on 03/12/2015 for EX_P_BANNED_NAME_CHECK*/ 
		String proceErrorMessage = getiPersonalRemittanceService().getBannedNameCheckProcedure(sessionmanage.getCountryId(), engName,localName);

		if(proceErrorMessage!=null && proceErrorMessage.length()>0){
			setErrmsg(proceErrorMessage);
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}


		if (getTelephoneNumber() == null || (getTelephoneNumber()!=null &&  getTelephoneNumber().trim().equals("")) && getMobileNumber() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Telephone or Mobile");
		} else if (getTelephoneNumber() != null && getCountryCode() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Telephone Country Code");
		} else if (getMobileNumber() != null && getMcountryCode() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Mobile Country Code");
		} else {
			try {
				log.info("Page redirect to bankacccountdetails page");
				if(getBooAotherLang()==null || getBooAotherLang()){
					setBooenableAgentPanel(null);
				}
				else
				{
					setBooenableAgentPanel(true);
				}
				FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
			} catch (Exception e) {
				System.out.println("Exception occured" + e);
			}
		}
	}

	public void backFromBenificarySearchPanel() {
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(false);
	}

	public void getServiceFirstPanel() {
		// calculateForeignCurrencyAmount();
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderRemittanceServicePanel(true);
		setBooRenderTypeOfServicePanel(false);
	}

	public void getServiceSecondPanel() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderRemittanceServicePanel(false);
		setBooRenderTypeOfServicePanel(false);
	}

	/*
	 * public void editRecord(PersonalRemmitanceBeneficaryDataTable
	 * personalBenfObj) {
	 * 
	 * }
	 */
	public void callService(PersonalRemmitanceBeneficaryDataTable personalBenfObj) {
	}

	// getting country code according to country selection
	public void getCountryCodeForCountry() {
		for (CountryMasterDesc countryMasterDesc : lstCountry) {
			if (getCountryId().compareTo(countryMasterDesc.getFsCountryMaster().getCountryId()) == 0) {
				setCountryCode(countryMasterDesc.getFsCountryMaster().getCountryTelCode());
				setMcountryCode(countryMasterDesc.getFsCountryMaster().getCountryTelCode());
			}
		}
	}

	List<BankApplicability> serviceProviderInd = new ArrayList<BankApplicability>();

	// to get service provider indicator banks from bank applicability
	public List<BankApplicability> getServiceProviderInd() {
		BigDecimal pkServProBankInd = null;
		List<BankIndicator> serviceBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_SERVICEPRO_BANK);
		if (serviceBankIndlist.size() != 0) {
			pkServProBankInd = serviceBankIndlist.get(0).getBankIndicatorId();
		}
		/* List<BankApplicability> lst = new ArrayList<BankApplicability>(); */
		serviceProviderInd = generalService.getBankListbyIndicatoronly(pkServProBankInd, sessionmanage.getCompanyId());
		if (serviceProviderInd.size() != 0) {
			setServiceproviderlst(serviceProviderInd);
		} else {
			/*
			 * RequestContext.getCurrentInstance().execute(
			 * "noserviceprovider.show();");
			 */
		}
		return serviceProviderInd;
	}

	// method called from menubar - navigation
	public void personalRemittanceMainScreen() {
		try {
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBenifisStateId(null);
			setDistictId(null);
			setCityId(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// clearing all Panels
	public void clear() {
		firstClear();
		secondClear();
	}

	// Clearing First Panel
	public void firstClear() {
		setCountryTelCode(null);
		setTelephoneNumber(null);
		setMobileNumber(null);
		setMcountryCode(null);
	}

	// Clearing Second Panel
	public void secondClear() {
		setServiceGroupId(null);
		setServiceTypeId(null);
		setBenifisCountryId(null);
		setBenifisCurrencyId(null);
		setBenifisBankId(null);
		setSearchBankStateId(null);
		setServicebankBranchId(null);
		setBankAccountNumber(null);
		setBankAccountType(null);
		setBeneSwiftCode(null);
		setAliasFirstName(null);
		setAliasSecondName(null);
		setAliasThirdName(null);
		setAliasFourthName(null);
		setFirstLName(null);
		setSecondLName(null);
		setThirdLName(null);
		setFourthLName(null);
		setCountryName(null);
		setBankName(null);
		setBankBranchName(null);
		// setOccupation(null);
		setBeneficaryBankState(null);
		setBeneficaryBankDistrict(null);
		setBeneficaryBankCity(null);
		setProviance(null);
		setServiceTypeId(null);
		lstBank.clear();
		lstBankbranch.clear();
		//lstBankbranchView.clear();
		//lststate.clear();
		lstDistict.clear();
		lstCity.clear();
		serviceGroupMasterDescList.clear();
		setBeneficaryAccountSeqId(null);
		// bank list from view 
		lstBankFromView.clear();
		if(getLstBankbranchView() != null){
			setLstBankbranchView(null);
		}
		//lstRoutingDetails.clear();
		if(agentAndCorrespondingBankslst != null && !agentAndCorrespondingBankslst.isEmpty()){
			agentAndCorrespondingBankslst.clear();
		}

		setAgentMaster(null);
		setAgentBranch(null);
		setServiceTypeId(null);
		if(agentAndCorrespondingBankBranchlst != null && !agentAndCorrespondingBankBranchlst.isEmpty()){
			agentAndCorrespondingBankBranchlst.clear();
		}

		if(lstContactDetails!=null){
			lstContactDetails.clear();
		}

		if(lstMobileDetails!=null){
			lstMobileDetails.clear();
		}


		setTelephoneNumberSelect(null);
		setMobileNumberSelect(null);

	}

	public String getDatabenificarybankname() {
		return databenificarybankname;
	}

	public void setDatabenificarybankname(String databenificarybankname) {
		this.databenificarybankname = databenificarybankname;
	}

	public String getDatabenificarybranchname() {
		return databenificarybranchname;
	}

	public void setDatabenificarybranchname(String databenificarybranchname) {
		this.databenificarybranchname = databenificarybranchname;
	}

	public String getDatabenificarycountryname() {
		return databenificarycountryname;
	}

	public void setDatabenificarycountryname(String databenificarycountryname) {
		this.databenificarycountryname = databenificarycountryname;
	}

	public String getDatabenificarycurrencyname() {
		return databenificarycurrencyname;
	}

	public void setDatabenificarycurrencyname(String databenificarycurrencyname) {
		this.databenificarycurrencyname = databenificarycurrencyname;
	}

	public BigDecimal getDataAccountnum() {
		return dataAccountnum;
	}

	public void setDataAccountnum(BigDecimal dataAccountnum) {
		this.dataAccountnum = dataAccountnum;
	}

	public String getBenificarystatus() {
		return benificarystatus;
	}

	public void setBenificarystatus(String benificarystatus) {
		this.benificarystatus = benificarystatus;
	}

	public String getBranchApplicabilty() {
		return BranchApplicabilty;
	}

	public void setBranchApplicabilty(String branchApplicabilty) {
		BranchApplicabilty = branchApplicabilty;
	}

	public BigDecimal getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(BigDecimal paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public String getDatabenificaryname() {
		return databenificaryname;
	}

	public void setDatabenificaryname(String databenificaryname) {
		this.databenificaryname = databenificaryname;
	}

	public BigDecimal getDataserviceid() {
		return dataserviceid;
	}

	public void setDataserviceid(BigDecimal dataserviceid) {
		this.dataserviceid = dataserviceid;
	}

	public BigDecimal getDatabenificarycountry() {
		return databenificarycountry;
	}

	public void setDatabenificarycountry(BigDecimal databenificarycountry) {
		this.databenificarycountry = databenificarycountry;
	}

	public BigDecimal getDatabenificarycurrency() {
		return databenificarycurrency;
	}

	public void setDatabenificarycurrency(BigDecimal databenificarycurrency) {
		this.databenificarycurrency = databenificarycurrency;
	}

	public String getDatabenificaryservice() {
		return databenificaryservice;
	}

	public void setDatabenificaryservice(String databenificaryservice) {
		this.databenificaryservice = databenificaryservice;
	}

	public BigDecimal getDatabenificarydelivery() {
		return databenificarydelivery;
	}

	public void setDatabenificarydelivery(BigDecimal databenificarydelivery) {
		this.databenificarydelivery = databenificarydelivery;
	}

	public List<CurrencyMaster> getCurrencyMasterList() {
		return currencyMasterList;
	}

	public void setCurrencyMasterList(List<CurrencyMaster> currencyMasterList) {
		this.currencyMasterList = currencyMasterList;
	}

	// to get All Currency From CurrencyMaster
	public List<CurrencyMaster> getCurrencyListFromDB() {
		return generalService.getCurrencyList();
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getDocumentserialityno() {
		return documentserialityno;
	}

	public void setDocumentserialityno(String documentserialityno) {
		this.documentserialityno = documentserialityno;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	int count = 0;

	public Boolean getTypeOfServicePanel() {
		return typeOfServicePanel;
	}

	public void setTypeOfServicePanel(Boolean typeOfServicePanel) {
		this.typeOfServicePanel = typeOfServicePanel;
	}

	public List<PersonalRemittanceTeleExistDTBean> getLstBeneficaryAccountDailog() {
		return lstBeneficaryAccountDailog;
	}

	public void setLstBeneficaryAccountDailog(List<PersonalRemittanceTeleExistDTBean> lstBeneficaryAccountDailog) {
		this.lstBeneficaryAccountDailog = lstBeneficaryAccountDailog;
	}

	public List<BeneficaryAccount> getLstBeneficaryAccount() {
		return lstBeneficaryAccount;
	}

	public void setLstBeneficaryAccount(List<BeneficaryAccount> lstBeneficaryAccount) {
		this.lstBeneficaryAccount = lstBeneficaryAccount;
	}

	public List<BeneficaryAccount> getLstUpdateBeneficaryAccount() {
		return lstUpdateBeneficaryAccount;
	}

	public void setLstUpdateBeneficaryAccount(List<BeneficaryAccount> lstUpdateBeneficaryAccount) {
		this.lstUpdateBeneficaryAccount = lstUpdateBeneficaryAccount;
	}

	public Map<String, Object> getMapTeleExistToCheck() {
		return mapTeleExistToCheck;
	}

	public void setMapTeleExistToCheck(Map<String, Object> mapTeleExistToCheck) {
		this.mapTeleExistToCheck = mapTeleExistToCheck;
	}

	public void checktelephoneNumberExist() {
		if (getTelephoneNumber() != null) {
			boolean checkDecimal = false;
			try {
				checkDecimal = CollectionUtil.isIntegerValue(new BigDecimal(getTelephoneNumber()));
			} catch (Exception e) {
				checkDecimal = false;
			}
			if (!checkDecimal) {
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("csp.show();");
				setErrmsg("Telephone Number wont accept Char/decimal numbers");
				setTelephoneNumber(null);
				return;
			}
		}
		if (getCountryCode() == null) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("teleCountryExist.show();");
			setTelephoneNumber(null);
		} else {
			if (getTelephoneNumber() != null) {
				lstBeneficaryAccountDailog.clear();
				setCheckTelePhoneExist("NO");
				setTelePhoneExist(false);
				log.info("getCountryCode " + getCountryCode());
				Map<String, Object> mapTeleExist = beneficaryCreation.checkTelephoneExist(getTelephoneNumber(), getCountryCode(), "telephone");
				setMapTeleExistToCheck(mapTeleExist);
				if (mapTeleExist.size() > 0) {
					Boolean telePhoneExist = (Boolean) mapTeleExist.get("TeleExist");
					if (telePhoneExist.booleanValue()) {
						System.out.println("TeleExist");
						Boolean relaShipExist = (Boolean) mapTeleExist.get("RelationExist");
						if (relaShipExist.booleanValue()) {
							Boolean accNoExist = (Boolean) mapTeleExist.get("AccountExist");
							System.out.println("RelationExist");
							if (accNoExist.booleanValue()) {
								@SuppressWarnings("unchecked")
								List<BeneficaryAccount> lstBeneficaryAccount = (List<BeneficaryAccount>) mapTeleExist.get("LstAccountExist");
								setLstBeneficaryAccount(lstBeneficaryAccount);
								for (BeneficaryAccount beneficaryAccount : lstBeneficaryAccount) {
									PersonalRemittanceTeleExistDTBean personalRmTelExist = new PersonalRemittanceTeleExistDTBean();
									personalRmTelExist.setBankAccountNumber(beneficaryAccount.getBankAccountNumber());
									personalRmTelExist.setBankBranchCode(beneficaryAccount.getBankBranchCode());
									personalRmTelExist.setBankFullName(beneficaryAccount.getBank().getBankFullName());
									personalRmTelExist.setBeneficaryAccountSeqId(beneficaryAccount.getBeneficaryAccountSeqId());
									personalRmTelExist.setBranchFullName(beneficaryAccount.getBankBranch().getBranchFullName());
									personalRmTelExist.setBeneficaryName(beneficaryAccount.getBeneficaryMaster().getFirstName() + " " + beneficaryAccount.getBeneficaryMaster().getSecondName());
									personalRmTelExist.setSelectRecord(false);
									lstBeneficaryAccountDailog.add(personalRmTelExist);
								}
								setLstBeneficaryAccountDailog(lstBeneficaryAccountDailog);
								// RequestContext.getCurrentInstance().execute("PF('teleRelaAccountExist').show();");
								System.out.println("AccountExist");
							}
						}
					}
				}
			}
		}
	}

	public void telephoneNumberExistInDB() {
		if (getCountryCode() == null) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("teleCountryExist.show();");
			setTelephoneNumber(null);
		} else {
			lstBeneficaryAccountDailog.clear();
			setCheckTelePhoneExist("NO");
			setTelePhoneExist(false);
			/* String ccc = getCountryCode().trim(); */
			// BigDecimal tleCountry=new BigDecimal(ccc);
			log.info("getCountryCode " + getCountryCode());
			Map<String, Object> mapTeleExist = beneficaryCreation.checkTelephoneExistWithCustIdwithPhone(getTelephoneNumber(), getCustomerNo(), getCountryCode(), "telephone");
			setMapTeleExistToCheck(mapTeleExist);
			if (mapTeleExist.size() > 0) {
				Boolean telePhoneExist = (Boolean) mapTeleExist.get("TeleExist");
				if (telePhoneExist.booleanValue()) {
					System.out.println("TeleExist");
					Boolean relaShipExist = (Boolean) mapTeleExist.get("RelationExist");
					if (relaShipExist.booleanValue()) {
						Boolean accNoExist = (Boolean) mapTeleExist.get("AccountExist");
						System.out.println("RelationExist");
						if (accNoExist.booleanValue()) {
							@SuppressWarnings("unchecked")
							List<BeneficaryAccount> lstBeneficaryAccount = (List<BeneficaryAccount>) mapTeleExist.get("LstAccountExist");
							setLstBeneficaryAccount(lstBeneficaryAccount);
							for (BeneficaryAccount beneficaryAccount : lstBeneficaryAccount) {
								PersonalRemittanceTeleExistDTBean personalRmTelExist = new PersonalRemittanceTeleExistDTBean();
								personalRmTelExist.setBankAccountNumber(beneficaryAccount.getBankAccountNumber());
								personalRmTelExist.setBankBranchCode(beneficaryAccount.getBankBranchCode());
								personalRmTelExist.setBankFullName(beneficaryAccount.getBank().getBankFullName());
								personalRmTelExist.setBeneficaryAccountSeqId(beneficaryAccount.getBeneficaryAccountSeqId());
								personalRmTelExist.setBranchFullName(beneficaryAccount.getBankBranch().getBranchFullName());
								personalRmTelExist.setBeneficaryName(beneficaryAccount.getBeneficaryMaster().getFirstName() + " " + beneficaryAccount.getBeneficaryMaster().getSecondName());
								personalRmTelExist.setSelectRecord(false);
								lstBeneficaryAccountDailog.add(personalRmTelExist);
							}
							setLstBeneficaryAccountDailog(lstBeneficaryAccountDailog);
							//RequestContext.getCurrentInstance().execute("PF('teleRelaAccountExist').show();");
							System.out.println("AccountExist");
						}
					}
				}
			}
		}
	}

	public void mobileNumberExistInDB() {



		if(getMobileNumber()!=null) {
			boolean checkDecimal = CollectionUtil.isIntegerValue(getMobileNumber());

			if(!checkDecimal)
			{
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("csp.show();");
				setErrmsg("Mobile Number wont accept Char/decimal numbers");
				setMobileNumber(null);
				return;
			}

		}

		if (getMcountryCode() == null) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("teleCountryExist.show();");
			setMobileNumber(null);
		} else {
			if (getMobileNumber() != null) {
				lstBeneficaryAccountDailog.clear();
				setCheckTelePhoneExist("NO");
				setTelePhoneExist(false);
				Map<String, Object> mapTeleExist = getiPersonalRemittanceService().checkTelephoneExistWithCustIdwithPhone(getMobileNumber(), getCustomerNo(), getCountryCode(), "mobile");
				setMapTeleExistToCheck(mapTeleExist);
				if (mapTeleExist.size() > 0) {
					Boolean telePhoneExist = (Boolean) mapTeleExist.get("TeleExist");
					if (telePhoneExist.booleanValue()) {
						System.out.println("TeleExist");
						Boolean relaShipExist = (Boolean) mapTeleExist.get("RelationExist");
						if (relaShipExist.booleanValue()) {
							Boolean accNoExist = (Boolean) mapTeleExist.get("AccountExist");
							System.out.println("RelationExist");
							if (accNoExist.booleanValue()) {
								@SuppressWarnings("unchecked")
								List<BeneficaryAccount> lstBeneficaryAccount = (List<BeneficaryAccount>) mapTeleExist.get("LstAccountExist");
								setLstBeneficaryAccount(lstBeneficaryAccount);
								for (BeneficaryAccount beneficaryAccount : lstBeneficaryAccount) {
									PersonalRemittanceTeleExistDTBean personalRmTelExist = new PersonalRemittanceTeleExistDTBean();
									personalRmTelExist.setBankAccountNumber(beneficaryAccount.getBankAccountNumber());
									personalRmTelExist.setBankBranchCode(beneficaryAccount.getBankBranchCode());
									personalRmTelExist.setBankFullName(beneficaryAccount.getBank().getBankFullName());
									personalRmTelExist.setBeneficaryAccountSeqId(beneficaryAccount.getBeneficaryAccountSeqId());
									personalRmTelExist.setBranchFullName(beneficaryAccount.getBankBranch().getBranchFullName());
									personalRmTelExist.setBeneficaryName(beneficaryAccount.getBeneficaryMaster().getFirstName() + " " + beneficaryAccount.getBeneficaryMaster().getSecondName());
									personalRmTelExist.setSelectRecord(false);
									lstBeneficaryAccountDailog.add(personalRmTelExist);
								}
								setLstBeneficaryAccountDailog(lstBeneficaryAccountDailog);
								//RequestContext.getCurrentInstance().execute("PF('teleRelaAccountExist').show();");
								System.out.println("AccountExist");
							}
						}
					}
				}
			}
		}
	}

	public void popbanklistTelePhone(BigDecimal countryId) {
		// lstBank=getiPersonalRemittanceService().getbanklist(getBenifisCurrencyId());
		lstBank = generalService.getBankList(countryId);
		popStatelistTelePhone(countryId);
	}

	public void popStatelistTelePhone(BigDecimal countryId) {
		lststate = generalService.getStateList(sessionmanage.getLanguageId(), countryId);
	}

	public void checkNo() {
		setTelephoneNumber(null);
		setCountryCode(null);
		setMobileNumber(null);
		setMcountryCode(null);
	}

	public void checkRelationExistFromDb() {
		List<BeneficaryRelationship> beneficaryRelationshipLst = getiPersonalRemittanceService().checkRelationExistWithCustId(getRelationId(), getCustomerNo());
		if (beneficaryRelationshipLst.size() > 0) {
			String localRelationShipName = beneficaryRelationshipLst.get(0).getRelations().getEnglishRelationsTypeDesc();
			setRelationShipName(localRelationShipName);
			setCheckRelationExist("YES");
			setRelationExist(false);
			/*
			 * RequestContext context = RequestContext.getCurrentInstance();
			 * context.execute("relationExistCheck.show();");
			 */
		} else {
			setCheckRelationExist("NO");
			setRelationExist(false);
		}
	}

	public void checkRelationYes() {
		setCheckRelationExist("YES");
		setRelationExist(false);
	}

	public void checkRelationNo() {
		setRelationId(null);
	}

	// Check Telephone Existed for particular customer -- 06/02/2015 End-Ram
	// Mohan
	public void fromDialogToTypesServicePanel() {
		String telephoneNumber = getTelephoneNumber();
		List<BeneficaryContact> beneficiaryList = beneficaryCreation.isCoustomerTelephoneExistInDB(telephoneNumber);
		List<BeneficaryAccount> objAccountList = getiPersonalRemittanceService().getBeneficaryAccountDetails(beneficiaryList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId());
		List<BeneficaryMaster> objMasterList = getiPersonalRemittanceService().getBeneficaryMasterDetails(beneficiaryList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId());
		setServiceGroupId(objAccountList.get(0).getServicegropupId().getServiceGroupId());
		setBankAccountNumber(objAccountList.get(0).getBankAccountNumber());
		if((objAccountList.get(0).getBankAccountTypeId()!=null))
		{
			setBankAccountType(getBankAccountType());
		}


		if(objAccountList.get(0).getSwiftCode()!=null)
		{
			setBeneSwiftCode(objAccountList.get(0).getSwiftCode());
		}

		setAliasFirstName(objAccountList.get(0).getAliasFirstName());
		setAliasSecondName(objAccountList.get(0).getAliasSecondName());
		setAliasThirdName(objAccountList.get(0).getAliasThirdName());
		setAliasFourthName(objAccountList.get(0).getAliasFourthName());
		setOccupation(objMasterList.get(0).getOccupation());
		setBenifisCountryId(objAccountList.get(0).getBeneficaryCountry().getCountryId());
		popCurrencylist();
		setBenifisCurrencyId(objAccountList.get(0).getCurrencyId().getCurrencyId());
		popbanklist();
		setBenifisBankId(objAccountList.get(0).getBank().getBankId());
		popbranchlist();
		setServicebankBranchId(objAccountList.get(0).getBankBranch().getBankBranchId());
		if(objMasterList.get(0).getFsCountryMaster() != null){
			setBeneCountryid(objMasterList.get(0).getFsCountryMaster().getCountryId());
		}
		popStatelist();
		if(objMasterList.get(0).getFsStateMaster() != null){
			setBenifisStateId(objMasterList.get(0).getFsStateMaster().getStateId());
		}
		popDistict();
		if(objMasterList.get(0).getFsDistrictMaster() != null){
			setDistictId(objMasterList.get(0).getFsDistrictMaster().getDistrictId());
		}
		popCitylist();
		if(objMasterList.get(0).getFsCityMaster() != null){
			setCityId(objMasterList.get(0).getFsCityMaster().getCityId());
		}

		// saveBeneficaryRelation(beneficiaryList.get(0).getBeneficaryMaster());
		setBooRenderBenificarySearchPanel(true);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// By subramanian for telephone validation Ends
	public void bankAccountNumberExistInDB() {
		log.info("Entering into bankAccountNumberExistInDB method ");
		log.info("getBankAccountNumber " + getBankAccountNumber());
		log.info("getBenifisBankId " + getBenifisBankId());
		log.info("getBenifisCurrencyId  " + getBenifisCurrencyId());
		log.info("getBenifisCountryId  " + getBenifisCountryId());
		log.info("getServicebankBranchId  " + getServicebankBranchId());
		int accountLength = getBankAccountNumber().length();
		int i = 0;
		String lengthValues = "";
		List<BankAccountLength> lstofAccLength = getiPersonalRemittanceService().getBankAccountLengthByBank(getBenifisBankId());
		if (lstofAccLength.size() != 0) {
			for (BankAccountLength bankAccountLength : lstofAccLength) {
				if ((bankAccountLength.getAcLength().compareTo(new BigDecimal(0))!=0) && bankAccountLength.getAcLength().compareTo(new BigDecimal(accountLength)) == 0) {
					i = 1;
					break;
				}

				else if((bankAccountLength.getAcLength().compareTo(new BigDecimal(0))==0))
				{

					i=-1;
				}

				else {
					String commaAdd = "";
					if (!lengthValues.equalsIgnoreCase("")) {
						commaAdd = lengthValues + " OR ";
					}
					lengthValues = commaAdd + bankAccountLength.getAcLength().toPlainString();
					i = 0;
				}
			}
			if (i == 0) {
				setBankAccountNumber(null);
				setBankAccountLength(lengthValues);
				RequestContext.getCurrentInstance().execute("acclengthmismatch.show();");
			} else {

				if(i==-1)
				{
					/*RequestContext.getCurrentInstance().execute("dself.show();");
					setErrmsg("Bank Account length is not avilable for this bank");*/

					System.out.println("*************************************************************");
					System.out.println("Bank Account length is not avilable for this bank");
					System.out.println("*************************************************************");
				}


				setCheckAccountNoExist("NO");
				setAccountNoExist(false);
				/*
				 * List<BeneficaryAccount> beneficiaryAccountDetailList =
				 * getiPersonalRemittanceService
				 * ().isBankAccountNumberExistInDb(getBankAccountNumber(),
				 * getBenifisCountryId(), getBenifisBankId(),
				 * getServicebankBranchId());
				 */


				// List<PersonalRemmitanceBeneficaryDataTable> coustomerBeneficaryDTList = new ArrayList<PersonalRemmitanceBeneficaryDataTable>();
				PersonalRemmitanceBeneficaryDataTable tempTable =null;

				for(int m=0;m<coustomerBeneficaryDTList.size();m++)
				{
					tempTable = coustomerBeneficaryDTList.get(m);

					if(tempTable.getBankAccountNumber()!=null && tempTable.getBankAccountNumber().equals(getBankAccountNumber()) && tempTable.getBankId()!=null &&  tempTable.getBankId().equals(getBenifisBankId()) && 
							tempTable.getCurrencyId()!=null &&	tempTable.getCurrencyId().equals(getBenifisCurrencyId()) && tempTable.getBenificaryCountry()!=null && tempTable.getBenificaryCountry().equals(getBenifisCountryId()) 
							&& tempTable.getBranchId()!=null && tempTable.getBranchId().equals(getServicebankBranchId()) )
					{
						RequestContext.getCurrentInstance().execute("accountcheck.show();");
						setErrmsg(getBankAccountNumber() + " already exist ");
						setBankAccountNumber(null);
						return;
					}
				}


				//	List<BeneficaryAccount> beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExist(getBankAccountNumber(), getBenifisBankId(), getBenifisCurrencyId(), getBenifisCountryId(), getServicebankBranchId());

				List<BeneficaryAccount> beneficiaryAccountDetailList = beneficaryCreation.isBankAccountNumberExist(getBankAccountNumber(), getBenifisBankId(), getBenifisCurrencyId(), getBenifisCountryId(), getServicebankBranchId());

				if(beneficiaryAccountDetailList.size() >0)
				{

					List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.SELF);
					BigDecimal selfRelationid  = selfid.get(0).getRelations().getRelationsId();
					System.out.println(beneficiaryAccountDetailList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId());
					List<BeneficaryRelationship> list = getiPersonalRemittanceService().
							isBenificaryRelationExist(beneficiaryAccountDetailList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId(), beneficiaryAccountDetailList.get(0).getBeneficaryAccountSeqId());

					if(list!=null && getBeneficaryTypeId().equals(new BigDecimal(1)))
					{
						for (int j = 0; j < list.size(); j++) {


							if( selfRelationid.equals(list.get(j).getRelations().getRelationsId()) ){

								RequestContext.getCurrentInstance().execute("dself.show();");
								setErrmsg("Duplicate bank account number entery for SELF - Customer Reference - " + list.get(j).getCustomerId().getCustomerReference() + " Customer Name - " +list.get(j).getCustomerId().getFirstName() + " " + list.get(j).getCustomerId().getLastName() +" already added this account number -" + getBankAccountNumber() );
								setBankAccountNumber(null);
								return;
							}
						}
					}



					setCheckAccountNoExist("YES");
					setAccountNoExist(true);
				}



				/*List<BeneficaryAccount> beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExist(getBankAccountNumber(), getBenifisBankId(), getBenifisCurrencyId(), getBenifisCountryId(), getServicebankBranchId());
				if (beneficiaryAccountDetailList.size() > 0 && getBeneficaryTypeId().equals(new BigDecimal(1))) {
					RequestContext.getCurrentInstance().execute("accountcheck.show();");
					setErrmsg(getBankAccountNumber() + " already exist ");
					setBankAccountNumber(null);
					return;*/
				/*
				 * if (getCheckTelePhoneExist().equalsIgnoreCase("YES") &&
				 * getCheckRelationExist().equalsIgnoreCase("YES")) {
				 * 
				 * RequestContext context =
				 * RequestContext.getCurrentInstance();
				 * context.execute("relationTeleAccountExist.show();");
				 * 
				 * } else {
				 * 
				 * RequestContext context =
				 * RequestContext.getCurrentInstance();
				 * context.execute("bankAccountNumberCheckDailog.show();" );
				 * 
				 * }
				 */
				/*} *//*else {*/
				setCheckAccountNoExist("NO");
				setAccountNoExist(false);
				/*}*/
			}
		} else {
			/*setBankAccountNumber(null);
			RequestContext.getCurrentInstance().execute("acclengtherr.show();");*/
		}
	}

	public void clearAccountField() {
		setBankAccountNumber(null);
		/*if (getTempAccountNumber() != null) {
			setBankAccountNumber(getTempAccountNumber());
		}*/
	}

	public void checkAccNoYes() {
		setCheckAccountNoExist("YES");
		setAccountNoExist(false);
	}

	public void checAcccNokNo() {
		setBankAccountNumber(null);
	}

	public void fromAccountExistDialogToBeneficaryTelephone() {
		List<BeneficaryAccount> beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExistInDb(getBankAccountNumber(), getBenifisCountryId(), getBenifisBankId(), getServicebankBranchId());
		saveBeneficaryTelephone(beneficiaryAccountDetailList.get(0).getBeneficaryMaster());
		/*
		 * saveBeneficaryRelation(beneficiaryAccountDetailList.get(0)
		 * .getBeneficaryMaster());
		 */
		setBooRenderBenificarySearchPanel(true);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(false);
	}

	// By subramanian for telephone validation Ends
	// added by Nazish
	/*
	 * private List<ServiceMasterDesc> serviceMasterDescList = new
	 * ArrayList<ServiceMasterDesc>();
	 */
	public List<ServiceGroupMasterDesc> getServiceGroupMasterDescList() {
		return serviceGroupMasterDescList;
	}

	public void setServiceGroupMasterDescList(List<ServiceGroupMasterDesc> serviceGroupMasterDescList) {
		this.serviceGroupMasterDescList = serviceGroupMasterDescList;
	}

	public Boolean getDisableDependService() {
		return disableDependService;
	}

	public void setDisableDependService(Boolean disableDependService) {
		this.disableDependService = disableDependService;
	}

	List<ServiceGroupMasterDesc> lstfetchCashId;

	public void enableServiceProvider() {
		setBankSpbranches(WarningHandler.showWarningMessage("lbl.agentBranch", sessionmanage.getLanguageId()));
		setBooRenderAgentMaster(true);
		lstDataTableBankbranch.clear();
		if(getLstBankbranchView() != null){
			setLstBankbranchView(null);
		}
		lstBankFromView.clear();
		lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
		setDisableSaveButton(true);
		setBooAotherLang(true);
		if (lstfetchCashId.size() != 0) {
			if (getServiceGroupId() != null && (getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0)) {
				setDisableDependService(false);
				setBooenableAgentPanel(false);
				setServiceTypeId(null);
				//popAgentlist();
				//fetchAllBeneServiceBanks();

				fetchAllBeneServiceProvider();

			} else {
				setServiceTypeId(null);
				setDisableDependService(true);
				setBooenableAgentPanel(true);
				//popBankingChennalbanklist();
				fetchAllBeneServiceBanks();
			}
			clearBeneandAgentDetails();
		}
	}




	List<RoutingDetails> lstRoutingDetails = new ArrayList<RoutingDetails>();


	public List<RoutingDetails> getLstRoutingDetails() {
		return lstRoutingDetails;
	}

	public void setLstRoutingDetails(List<RoutingDetails> lstRoutingDetails) {
		this.lstRoutingDetails = lstRoutingDetails;
	}

	@Autowired
	ServiceCodeMasterService serviceCodeMasterService;

	@Autowired
	IRoutingSetUpDetailsService<T> iroutingSetUpDetailsService;


	public void enableAgentMaster() {
		log.info("Entering into enableAgentMaster method");
		log.info("getAgentMaster" + getAgentMaster());
		if(agentAndCorrespondingBankslst != null && !agentAndCorrespondingBankslst.isEmpty()){
			agentAndCorrespondingBankslst.clear();
		}
		setAgentMaster(null);
		setAgentBranch(null);

		// fetch details from view
		List<ViewRoutingAgents> lstAgents = iroutingSetUpDetailsService.fetchAllRoutingAgents(sessionmanage.getCountryId(), getBenifisCountryId(), getServiceGroupId(),  getServiceTypeId(), getBenifisCurrencyId());

		if(lstAgents != null && lstAgents.size() != 0){
			for (ViewRoutingAgents viewRoutingAgents : lstAgents) {
				PopulateDataWithCode lstBank = new PopulateDataWithCode();

				lstBank.setId(viewRoutingAgents.getAgentBankId());
				lstBank.setCode(viewRoutingAgents.getAgentBankCode());
				lstBank.setName(viewRoutingAgents.getAgentBankName());

				agentAndCorrespondingBankslst.add(lstBank);
			}
		}else{
			if(agentAndCorrespondingBankslst != null && !agentAndCorrespondingBankslst.isEmpty()){
				agentAndCorrespondingBankslst.clear();
			}
		}



		log.info("Exit into enableAgentMaster method");
	}



	public void clearApplicableField()
	{
		log.info("Entering into clearApplicableField method");
		setBenifisCurrencyId(null);
		setAgentMaster(null);
		setAgentBranch(null);

		if(agentAndCorrespondingBankslst != null && !agentAndCorrespondingBankslst.isEmpty()){
			agentAndCorrespondingBankslst.clear();
		}

		if(agentAndCorrespondingBankBranchlst != null && !agentAndCorrespondingBankBranchlst.isEmpty()){
			agentAndCorrespondingBankBranchlst.clear();
		}

		log.info("Exit into clearApplicableField method");
	}



	public void popAgentBranch()
	{
		setAgentBranch(null);
		setLstBankbranchView(null);

		if(agentAndCorrespondingBankBranchlst != null && !agentAndCorrespondingBankBranchlst.isEmpty()){
			agentAndCorrespondingBankBranchlst.clear();
		}

		// fetch details from view
		List<ViewRoutingAgentLocations> lstAgentLocation = iroutingSetUpDetailsService.fetchAllRoutingAgentLocations(sessionmanage.getCountryId(), getBenifisCountryId(), getServiceGroupId(),  getServiceTypeId(), getBenifisCurrencyId(),getAgentMaster());

		if(lstAgentLocation != null && lstAgentLocation.size() != 0){
			setLstAgentLocationForCash(lstAgentLocation);
			for (ViewRoutingAgentLocations viewRoutingAgentLocations : lstAgentLocation) {
				PopulateDataWithCode lstBank = new PopulateDataWithCode();

				lstBank.setId(viewRoutingAgentLocations.getBankBranchId());
				lstBank.setCode(viewRoutingAgentLocations.getBranchCode().toString());
				lstBank.setName(viewRoutingAgentLocations.getBranchFullName());

				agentAndCorrespondingBankBranchlst.add(lstBank);
			}

		}else{
			setLstAgentLocationForCash(null);
			if(agentAndCorrespondingBankBranchlst != null && !agentAndCorrespondingBankBranchlst.isEmpty()){
				agentAndCorrespondingBankBranchlst.clear();
			}

		}



	}

	// if bene country changed clearing service group
	public void clearServicegroups(){
		setServiceGroupId(null);
	}


	public void popBankingChennalbanklist() {
		lstBank.clear();
		List<BankApplicability> FbankList = bankApplicabilityService.getApplicabilityBankingChennalBankList(sessionmanage.getCountryId(), Constants.BANK_INDICATOR_AGENT_BANK);
		for (BankApplicability bankApplicability : FbankList) {
			if (bankApplicability.getBankMaster().getFsCountryMaster().getCountryId().equals(getBenifisCountryId())) {
				lstBank.add(bankApplicability.getBankMaster());
			}
		}
		benServiceCurrencyList();
	}

	public void popAgentlist() {
		lstBank.clear();
		List<BankApplicability> FbankList = bankApplicabilityService.getApplicabilityBankList(sessionmanage.getCountryId(), Constants.BANK_INDICATOR_AGENT_BANK);
		for (BankApplicability bankApplicability : FbankList) {
			lstBank.add(bankApplicability.getBankMaster());
		}
		benServiceCurrencyList();
	}

	public void checkDuplicates() {
		if(!coustomerBeneficaryDTList.isEmpty())
		{
			for(PersonalRemmitanceBeneficaryDataTable datatable :coustomerBeneficaryDTList)
			{
				System.out.println(datatable);
				if(datatable.getBankId() != null && datatable.getBankId().equals(getAgentMaster()) && datatable.getBranchId() != null && datatable.getBranchId().equals(getAgentBranch()) && datatable.getServiceGroupName().equals(Constants.CASHNAME)  
						&& (datatable.getBankAccountNumber()==null ||datatable.getBankAccountNumber()!=null &&  datatable.getBankAccountNumber().equals("")))
				{
					setErrmsg("This combination already exist in datatable");
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}
			}

			for(ViewRoutingAgentLocations datatable :getLstAgentLocationForCash())
			{
				if(datatable.getBankBranchId().compareTo(getAgentBranch())==0)
				{
					setServiceProviderBankBranchId(datatable.getRoutingBranchId());
					break;
				}else{
					setServiceProviderBankBranchId(null);
				}
			}
		}
	}

	public List<RemittanceModeDescription> getListRemittanceDesc() {
		listRemittanceDesc = iPersonalRemittanceService.getremittanceDesc(sessionmanage.getLanguageId());
		return listRemittanceDesc;
	}

	public void setListRemittanceDesc(List<RemittanceModeDescription> listRemittanceDesc) {
		this.listRemittanceDesc = listRemittanceDesc;
	}

	public List<DeliveryModeDesc> getListdeliveryDesc() {
		listdeliveryDesc = iPersonalRemittanceService.lstDeliveryMode(sessionmanage.getLanguageId());
		return listdeliveryDesc;
	}

	public void setListdeliveryDesc(List<DeliveryModeDesc> listdeliveryDesc) {
		this.listdeliveryDesc = listdeliveryDesc;
	}

	public String getDeliveryModeInput() {
		return deliveryModeInput;
	}

	public void setDeliveryModeInput(String deliveryModeInput) {
		this.deliveryModeInput = deliveryModeInput;
	}

	public Boolean getBooRenderDeliveryModeInputPanel() {
		return booRenderDeliveryModeInputPanel;
	}

	public void setBooRenderDeliveryModeInputPanel(Boolean booRenderDeliveryModeInputPanel) {
		this.booRenderDeliveryModeInputPanel = booRenderDeliveryModeInputPanel;
	}

	public Boolean getBooRenderDeliveryModeDDPanel() {
		return booRenderDeliveryModeDDPanel;
	}

	public void setBooRenderDeliveryModeDDPanel(Boolean booRenderDeliveryModeDDPanel) {
		this.booRenderDeliveryModeDDPanel = booRenderDeliveryModeDDPanel;
	}

	public List<BankAccountDetails> getListCurrencyAccountDetails() {
		return listCurrencyAccountDetails;
	}

	public void setListCurrencyAccountDetails(List<BankAccountDetails> listCurrencyAccountDetails) {
		this.listCurrencyAccountDetails = listCurrencyAccountDetails;
	}

	public void popCurrencylistBank() {
		List<BankAccountDetails>	listCurrencyAccountDetails = ifundservice.getCurrencyOfBank(getBenifisBankId());
		setListCurrencyAccountDetails(listCurrencyAccountDetails);
		System.out.println("*********************" + getBenifisBankId());
		System.out.println("*********************" + listCurrencyAccountDetails.size());
	}

	// set currency based on the bank
	public boolean isSingleCurrency() {
		return singleCurrency;
	}

	public void setSingleCurrency(boolean singleCurrency) {
		this.singleCurrency = singleCurrency;
	}

	public boolean isMultCurrency() {
		return multCurrency;
	}

	public void setMultCurrency(boolean multCurrency) {
		this.multCurrency = multCurrency;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public boolean isSelectrecord() {
		return selectrecord;
	}

	public void setSelectrecord(boolean selectrecord) {
		this.selectrecord = selectrecord;
	}

	// to get the accoMMYY value
	@Deprecated
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}
		String year = String.valueOf(new Date().getYear()).substring(1, 3);
		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	public List<CountryMasterDesc> getNationalityList() {
		return nationalityList;// generalService.getNationalityList(sessionmanage.getLanguageId());
	}

	public void setNationalityList(List<CountryMasterDesc> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public List<CountryMasterDesc> getBeneCountryList() {
		return beneCountryList;
	}

	public void setBeneCountryList(List<CountryMasterDesc> beneCountryList) {
		this.beneCountryList = beneCountryList;
	}

	public CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> getColdatatablevalues() {
		return coldatatablevalues;
	}

	public void setColdatatablevalues(CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
		this.coldatatablevalues = coldatatablevalues;
	}

	public List<BankApplicability> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<BankApplicability> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	List<CustomerBank> localBankListinCollection = new ArrayList<CustomerBank>();

	public void setLocalBankListinCollection(List<CustomerBank> localBankListinCollection) {
		this.localBankListinCollection = localBankListinCollection;
	}

	public BigDecimal getPopulatedDebitCardNumber() {
		return populatedDebitCardNumber;
	}

	public void setPopulatedDebitCardNumber(BigDecimal populatedDebitCardNumber) {
		this.populatedDebitCardNumber = populatedDebitCardNumber;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	// End to set the DocumentNo 28/01/2015 ---Ramakrishna
	// START 28/01/2015 ---- Rahamath
	public void assignNullValues() {
		setBenificiaryryNameRemittance(null);
		setCountryTelephoneNumber(null);
		// setBankFullName(null);
		// setBranchFullName(null);
		setDeliveryMode(null);
		setRoutingBranch(null);
		setModeOfPayment(null);
		setCurrencyName(null);
		setCurrency(null);
		setChargesOverseas(null);
		setBenificiaryryNameRemittance(null);
		setDataAccountnum(null);
		setDatabenificarycurrencyname(null);
		setDatabenificarycountryname(null);
		setDatabenificarybankname(null);
		setDatabenificarybranchname(null);
		setAgent(null);
	}

	public Boolean getRenderBackButton() {
		return renderBackButton;
	}

	public void setRenderBackButton(Boolean renderBackButton) {
		this.renderBackButton = renderBackButton;
	}

	// validation checking
	public boolean isBooAuthozed() {
		return booAuthozed;
	}

	public void setBooAuthozed(boolean booAuthozed) {
		this.booAuthozed = booAuthozed;
	}

	// Check Telephone Existed for particular customer -- 06/02/2015 Starts-Ram
	// Mohan
	public String getCheckTelePhoneExist() {
		return checkTelePhoneExist;
	}

	public void setCheckTelePhoneExist(String checkTelePhoneExist) {
		this.checkTelePhoneExist = checkTelePhoneExist;
	}

	public boolean isTelePhoneExist() {
		return isTelePhoneExist;
	}

	public void setTelePhoneExist(boolean isTelePhoneExist) {
		this.isTelePhoneExist = isTelePhoneExist;
	}

	public String getCheckAccountNoExist() {
		return checkAccountNoExist;
	}

	public void setCheckAccountNoExist(String checkAccountNoExist) {
		this.checkAccountNoExist = checkAccountNoExist;
	}

	public boolean isAccountNoExist() {
		return isAccountNoExist;
	}

	public void setAccountNoExist(boolean isAccountNoExist) {
		this.isAccountNoExist = isAccountNoExist;
	}

	public String getCheckRelationExist() {
		return checkRelationExist;
	}

	public void setCheckRelationExist(String checkRelationExist) {
		this.checkRelationExist = checkRelationExist;
	}

	public boolean isRelationExist() {
		return isRelationExist;
	}

	public void setRelationExist(boolean isRelationExist) {
		this.isRelationExist = isRelationExist;
	}

	public String getRelationShipName() {
		return relationShipName;
	}

	public void setRelationShipName(String relationShipName) {
		this.relationShipName = relationShipName;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	// Date OF Birth Validations
	public void yearOfBithSelect() {


		if(getAge()!=null)
		{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Already age entered ");
			setYearOfBrith(null);
			return;
		}

		if(getDateOfBrith()!=null)
		{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Already Date of birth entered ");
			setYearOfBrith(null);
			return;
		}

		if (getYearOfBrith() != null) {
			if (!isInteger(getYearOfBrith().toPlainString())) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Please enter numbers only");
				setYearOfBrith(null);
				return;
			}
			if (getYearOfBrith() != null && getYearOfBrith().toPlainString().length() != 0) {
				if (getYearOfBrith().toPlainString().length() != 4) {
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg("Year should be 4 digits");
					setYearOfBrith(null);
					return;
				}
				Calendar calendar = GregorianCalendar.getInstance();
				int result = getYearOfBrith().compareTo(new BigDecimal(1930));
				if (result == -1) {
					int getyr = Calendar.getInstance().get(Calendar.YEAR) - getYearOfBrith().intValue();
					RequestContext.getCurrentInstance().execute("csp.show();");
					/*
					 * setErrmsg("Customer's age is calculated as " +
					 * (Calendar.getInstance().get(Calendar.YEAR) + " - " +
					 * getYearOfBrith().intValue()) + " = " + getyr +
					 * ".Please verify");
					 */
					setErrmsg("Customer's age is  " + getyr + " based on your input.Please verify");
					setYearOfBrith(null);
					return;
				}
				String dateString = "01-01-" + getYearOfBrith();
				int futureCheck = Calendar.getInstance().get(Calendar.YEAR) - getYearOfBrith().intValue();
				if (futureCheck == 0 || futureCheck < 0) {
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg("System dont allow present year and future year");
					setYearOfBrith(null);
					return;
				}
				log.info("dateString " + dateString);
				Date date = null;
				try {
					date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				log.info("date " + date);
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
				log.info(System.out.printf("Date %s is older than 18? %s", date, calendar.getTime().after(date)));
				if (!calendar.getTime().after(date)) {
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg("Customer's age below 18.Please verify");
					setYearOfBrith(null);
					return;
				}
			}
		}
	}

	public void ageSelect() {
		if (getAge() != null) {
			if (getDateOfBrith() != null && getAge().toPlainString().length() != 0) {
				setAge(null);
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Already Date of Birth entered");
				return;
			} else if (getYearOfBrith() != null && getAge().toPlainString().length() != 0) {
				setAge(null);
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Year Of Birth Already Selected");
				return;
			}
		}
	}

	public void dateOfBithSelect() {
		if (getAge() != null && getDateOfBrith() != null) {
			setDateOfBrith(null);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Age Already Selected ");
			return;
		} else if (getYearOfBrith() != null && getDateOfBrith() != null) {
			setDateOfBrith(null);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Year Of Birth Already Selected");
			return;
		}
	}

	public Boolean getMinagevalidation() {
		return minagevalidation;
	}

	public void setMinagevalidation(Boolean minagevalidation) {
		this.minagevalidation = minagevalidation;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}


	public void dateChange(ActionEvent  event) {


		System.out.println(getDateOfBrith());
		System.out.println("Hello... I am in DateChange");
	}


	public void dateChange1(SelectEvent event) 
	{   
		Date date = (Date)event.getObject();
		System.out.println(date);
		System.out.println(date);
		System.out.println(date);
	}


	public void ageValidation(SelectEvent e) {
		//dateOfBithSelect();
		/*Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -(Integer.parseInt(Constants.AGE_LIMIT)));
		Date today18 = cal.getTime();*/


		if(getDateOfBrith()!=null) {


			Calendar cal = Calendar.getInstance();
			int yrNow = cal.get(Calendar.YEAR);




			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getDateOfBrith());


			int findage = yrNow - calendar.get(Calendar.YEAR);

			setAge(new BigDecimal(findage));
			int yearOfBrith= yrNow - findage;
			setYearOfBrith(new BigDecimal(yearOfBrith));

			setReadOnlyYearOfBirth(true);
			setReadOnlyAge(true);

			/*
		if (getDateOfBrith() != null) {
			if (getDateOfBrith().before(today18)) {
				setMinagevalidation(false);
				setDisableResetDataOfBirth(true);
			} else {
				setMinagevalidation(true);
				setDateOfBrith(null);
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Selected date is under 18");
			}
		}*/
		}
	}

	public Boolean getMainPanelRender() {
		return mainPanelRender;
	}

	public void setMainPanelRender(Boolean mainPanelRender) {
		this.mainPanelRender = mainPanelRender;
	}

	public void backtoPersonalRemit() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			clearEditFields();
			context.redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	private void clearEditFields() {
		setBeneMatsterSeqId(null);
		setBeniStatusName(null);
		/* setBenificaryStatusName(null); */
		setBeneficaryTypeId(null);
		// setFirstName(null);
		setSecondName(null);
		setThirdName(null);
		setFourthName(null);
		setFifthName(null);
		setOccupation(null);
		setBenifisStateId(null);
		setMasterCreatedBy(null);
		setMasterCreatedDate(null);
		setNoOfRemittance(null);
		setBeneficaryStatusId(null);
		setApplicationCountryId(null);
		setIsActive(null);
		setDistictId(null);
		setCityId(null);
		setDistrictName(null);
		setCityName(null);
		setStateName(null);
		setNationalityName(null);
		setBeneficaryRelationshipId(null);
		setRelationId(null);
		// setCustomerNo(null);
		setRelationCreatedBy(null);
		setRelationCreatedDate(null);
		setCountryCode(null);
		setMcountryCode(null);
		setBeneficaryTelephoneSeqId(null);
		setTelephoneNumber(null);
		setMobileNumber(null);
		setYearOfBrith(null);
		setDateOfBrith(null);
		setContactCreatedBy(null);
		setContactCreatedDate(null);
		setAge(null);
		setServiceGroupId(null);
		setServiceTypeId(null);
		setBenifisCountryId(null);
		setBenifisBankId(null);
		setSearchBankStateId(null);
		setBenifisCurrencyId(null);
		setServicebankBranchId(null);
		setBankAccountNumber(null);
		setBankAccountType(null);
		setBeneSwiftCode(null);
		setAliasFirstName(null);
		setAliasSecondName(null);
		setAliasThirdName(null);
		setAliasFourthName(null);
		setBankCode(null);
		setAccountCreatedBy(null);
		setAccountCreatedDate(null);
		setBankBranchCode(null);
		setBeneficaryAccountSeqId(null);
		setBankCode(null);
	}

	public void ageValidationforEdit(SelectEvent e) {
		dateOfBithSelect();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -(Integer.parseInt(Constants.AGE_LIMIT)));
		Date today18 = cal.getTime();
		if (getDateOfBrith() != null) {
			if (getDateOfBrith().before(today18)) {
				setMinagevalidation(false);
			} else {
				setMinagevalidation(true);
				setDateOfBrith(null);
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Selected date is under 18");
			}
		}
	}

	public void test() {
	}


	public void clearservicebankBranchId() {
		setServicebankBranchId(null);
	}

	public void clearDateofBirth() {
		setDateOfBrith(null);
		setAge(null);
		setYearOfBrith(null);
		setReadOnlyYearOfBirth(false);
		setReadOnlyAge(false);
	}

	public void clearBeneandAgentDetails() {
		setBenifisBankId(null);
		setSearchBankStateId(null);
		setServicebankBranchId(null);
		setBenifisCurrencyId(null);
		setBankAccountNumber(null);
		setBankAccountType(null);
		setBeneSwiftCode(null);
		setAliasFirstName(null);
		setAliasSecondName(null);
		setAliasThirdName(null);
		setAliasFourthName(null);
		//setBenifisStateId(null);
		//setDistictId(null);
		//setCityId(null);
	}

	public void isbankAccountNumberExistEdit() {
		log.info("Entering into isbankAccountNumberExistEdit method");
		if (getTempAccountNumber() != null && getBankAccountNumber() != null && !getTempAccountNumber().equals(getBankAccountNumber())) {
			bankAccountNumberExistInDB();
		} else {
			log.info("Account number is not edited ");
		}
		log.info("Exit into isbankAccountNumberExistEdit method");
	}

	private Boolean firstTime;
	private Boolean firstTimeInBeneCreation;
	private String idNumber;
	private List<BeneficaryStatus> benificaryStatusList = new ArrayList<BeneficaryStatus>();
	private List<BeneficaryStatus> benificaryStatusName = new ArrayList<BeneficaryStatus>();

	public static BigDecimal SELFRELATIONID ;

	public void populateBenificaryDetails() {
		if (firstTime == null) {

			lstDataTableBankbranch.clear();

			List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.SELF);

			SELFRELATIONID  = selfid.get(0).getRelations().getRelationsId();

			boolean differntService = true;

			coustomerBeneficaryDTList.clear();
			try {

				setRenderBackButton(true);

				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idNumber") != null) {
					setIdNumber(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idNumber").toString());
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idNumber");
				}
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectCard") != null) {
					setSelectCard(new BigDecimal(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectCard").toString()));
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectCard");
				}
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerNo") != null) {
					setCustomerNo(new BigDecimal(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerNo").toString()));
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pcustomerNo");
				}
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dataTable") != null) {


					@SuppressWarnings("unchecked")
					List<PersonalRemmitanceBeneficaryDataTable> coustomerDTList = (List<PersonalRemmitanceBeneficaryDataTable>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dataTable");
					coustomerBeneficaryDTList.addAll(coustomerDTList);
					System.out.println(coustomerBeneficaryDTList.size());
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("dataTable");
				}



				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromDifferntService") != null) {

					String fromDifferntService =(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromDifferntService");
					if(fromDifferntService.equals("yes"))
					{
						setBooRenderIndBenificaryStatusPanel(true);
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("fromDifferntService");
						beneficiaryStatusListforDiffernt();
						differntService = false;
					}
				}
				else
				{
					setBooAotherLang(null);
					setBooRenderIndBenificaryStatusPanel(false);
					setBeneficaryStatusId(null);
					clear();
				}


				log.info("Id Number from personal remittence is " + getIdNumber());
				log.info("select card value from personal remittence is " + getSelectCard());
				if ((FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromPersonalRemittenceScreen")) != null) {
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("fromPersonalRemittenceScreen");
				}
				log.info("idNumber and selectCard successfully removed from session object");
				nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
				beneCountryList = generalService.getCountryList(sessionmanage.getLanguageId());
				log.info("nationality added");
				//populateBenificaryAccountDetails();
				log.info("country  added");
			} catch (Exception e) {
				log.info("Exception ocured " + e);
				log.info("Exception occured while gettting id number from personal remittence screen");
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
			if(differntService)
				beneficiaryStatusList();
			firstTime = true;
		} else {
			if (firstTimeInBeneCreation == null) {



				if (!(getBooAotherLang()==null || getBooAotherLang())) {

					System.out.println("Clear is not happened here");
				}
				else {
					clear();
					beneficiaryStatusList();
				}
				// setBeneficaryStatusId(null);
				firstTimeInBeneCreation = true;
			} else {
				try {
					if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromPersonalRemittenceScreen") != null) {
						String fromPersonalRemittenceScreen = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromPersonalRemittenceScreen").toString();
						if (fromPersonalRemittenceScreen.equals("yes")) {
							firstTime = null;
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("fromPersonalRemittenceScreen");
							populateBenificaryDetails();
							if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerNo") != null) {
								setCustomerNo(new BigDecimal(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerNo").toString()));
								FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pcustomerNo");
							}
						}
					}
				} catch (Exception e) {
					log.info("Exception occured while gettting id number from personal remittence screen");
				}
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			}
		}
	}

	private void beneficiaryStatusList() {
		clear();
		benificaryStatusList.clear();
		benificaryStatusName.clear();
		List<BeneficaryStatus> beneStatus = getiPersonalRemittanceService().getBeneficaryStatusList();
		List<CountryBranch> lstToCountryBranch = generalService.getBranchDetailsFromBeneStatus(sessionmanage.getCountryId(), new BigDecimal(sessionmanage.getBranchId()));
		if (lstToCountryBranch.size() != 0) {
			for (CountryBranch countryBranch : lstToCountryBranch) {
				if (countryBranch.getCorporateStatus() != null && countryBranch.getCorporateStatus().equalsIgnoreCase(Constants.Yes)) {
					benificaryStatusList.clear();
					benificaryStatusName.clear();
					List<BeneficaryStatus> tempbeneStatus = new ArrayList<BeneficaryStatus>();
					for (BeneficaryStatus finalListStatus : beneStatus) {
						if (!finalListStatus.getBeneficaryStatusName().equals("Corporate")) {
							tempbeneStatus.add(finalListStatus);
						}
					}
					benificaryStatusList.addAll(tempbeneStatus);
					benificaryStatusName.addAll(tempbeneStatus);
					break;
				} else {
					for (BeneficaryStatus finalListStatus : beneStatus) {
						if (finalListStatus.getBeneficaryStatusName() != null && !finalListStatus.getBeneficaryStatusName().equalsIgnoreCase(Constants.Corporate)) {
							benificaryStatusList.add(finalListStatus);
							benificaryStatusName.add(finalListStatus);
						}
					}
				}
			}
		}
	}


	private void beneficiaryStatusListforDiffernt() {
		/*	clear();*/
		benificaryStatusList.clear();
		benificaryStatusName.clear();
		List<BeneficaryStatus> beneStatus = getiPersonalRemittanceService().getBeneficaryStatusList();
		List<CountryBranch> lstToCountryBranch = generalService.getBranchDetailsFromBeneStatus(sessionmanage.getCountryId(), new BigDecimal(sessionmanage.getBranchId()));
		if (lstToCountryBranch.size() != 0) {
			for (CountryBranch countryBranch : lstToCountryBranch) {
				if (countryBranch.getCorporateStatus() != null && countryBranch.getCorporateStatus().equalsIgnoreCase(Constants.Yes)) {
					benificaryStatusList.clear();
					benificaryStatusName.clear();
					List<BeneficaryStatus> tempbeneStatus = new ArrayList<BeneficaryStatus>();
					for (BeneficaryStatus finalListStatus : beneStatus) {
						if (!finalListStatus.getBeneficaryStatusName().equals("Corporate")) {
							tempbeneStatus.add(finalListStatus);
						}
					}
					benificaryStatusList.addAll(tempbeneStatus);
					benificaryStatusName.addAll(tempbeneStatus);
					break;
				} else {
					for (BeneficaryStatus finalListStatus : beneStatus) {
						if (finalListStatus.getBeneficaryStatusName() != null && !finalListStatus.getBeneficaryStatusName().equalsIgnoreCase(Constants.Corporate)) {
							benificaryStatusList.add(finalListStatus);
							benificaryStatusName.add(finalListStatus);
						}
					}
				}
			}
		}
	}


	Boolean disabledServiceGroup ;



	public Boolean getDisabledServiceGroup() {
		return disabledServiceGroup;
	}

	public void setDisabledServiceGroup(Boolean disabledServiceGroup) {
		this.disabledServiceGroup = disabledServiceGroup;
	}

	public void populateBenificaryAccountDetails() {

		if(lstCountry!=null && lstCountry.isEmpty()){
			lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
		}
		if(serviceGroupMasterDescList!=null && serviceGroupMasterDescList.isEmpty()){
			serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());
		}

		ExternalContext context	= FacesContext.getCurrentInstance().getExternalContext();
		String fromPersonalRemittance =(String) context.getSessionMap().get("fromPersonalRemittanceDifferntAccount");
		String fromPersonalRemittanceDifferntService =(String) context.getSessionMap().get("fromPersonalRemittanceDifferntService");

		if(fromPersonalRemittance!=null && fromPersonalRemittance.equals("yes"))
		{
			BigDecimal actualCustomerNo = (BigDecimal) context.getSessionMap().get("customerNumber");

			lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
			PersonalRemmitanceBeneficaryDataTable datatabledetails = (PersonalRemmitanceBeneficaryDataTable)context.getSessionMap().get("datatabledetail");

			//populateCustomerDetailsforDifferentAccount();
			
			try {
				editBeneficarayPage(datatabledetails);
				//editBeneficarayDifferntServicesorAccount(datatabledetails);
			} catch (Exception e) {
				e.printStackTrace();
			}

			setDisabledServiceGroup(true);

			if(getBenifisCountryId() != null && getServiceGroupId() != null){
				fetchAllBeneServiceBanks();
				populateBankAccountType();
			}
			context.getSessionMap().remove("fromPersonalRemittanceDifferntAccount");
			context.getSessionMap().remove("datatabledetail");
			context.getSessionMap().remove("customerNumber");
			setBenifisBankId(null);
			setSearchBankStateId(null);
			setServicebankBranchId(null);
			setBenifisCurrencyId(null);
			setBankAccountNumber(null);
			setBankAccountType(null);
			setBeneSwiftCode(null);
			setAliasFirstName(null);
			setAliasSecondName(null);
			setAliasThirdName(null);
			setAliasFourthName(null);
			setServiceTypeId(null);
			setCustomerNo(actualCustomerNo);
			setBeneficaryAccountSeqId(null);
			setBeneficaryBankState(null);
			setBeneficaryBankDistrict(null);
			setBeneficaryBankCity(null);

		}

		if(fromPersonalRemittanceDifferntService!=null && fromPersonalRemittanceDifferntService.equals("yes"))
		{
			BigDecimal actualCustomerNo = (BigDecimal) context.getSessionMap().get("customerNumber");

			lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
			PersonalRemmitanceBeneficaryDataTable datatabledetails = (PersonalRemmitanceBeneficaryDataTable)context.getSessionMap().get("datatabledetail");

			//populateCustomerDetailsforDifferentAccount();
			try {
				editBeneficarayPage(datatabledetails);
				//editBeneficarayDifferntServicesorAccount(datatabledetails);
			} catch (Exception e) {
				e.printStackTrace();
			}

			setDisabledServiceGroup(false);
			context.getSessionMap().remove("fromPersonalRemittanceDifferntService");
			context.getSessionMap().remove("datatabledetail");
			context.getSessionMap().remove("customerNumber");
			setBenifisCountryId(null);
			setServiceGroupId(null);
			setBenifisBankId(null);
			setSearchBankStateId(null);
			setServicebankBranchId(null);
			setBenifisCurrencyId(null);
			setBankAccountNumber(null);
			setBankAccountType(null);
			setBeneSwiftCode(null);
			setAliasFirstName(null);
			setAliasSecondName(null);
			setAliasThirdName(null);
			setAliasFourthName(null);
			setServiceTypeId(null);
			setCustomerNo(actualCustomerNo);
			setBeneficaryAccountSeqId(null);
			setBeneficaryBankState(null);
			setBeneficaryBankDistrict(null);
			setBeneficaryBankCity(null);
			lstBankbranch.clear();
			beneServiceCurrencyList.clear();
			//lstBankAccountType.clear();
			lstBankAccountTypeFromView.clear();

		}


	}

	Boolean disableSaveButton ;


	public Boolean getDisableSaveButton() {
		return disableSaveButton;
	}

	public void setDisableSaveButton(Boolean disableSaveButton) {
		this.disableSaveButton = disableSaveButton;
	}

	public void getSelectRecordFrom(PersonalRemittanceTeleExistDTBean perRmTeleDtBean) {
		List<BeneficaryAccount> lstBeneficaryAccount = getLstBeneficaryAccount();
		for (int i = 0; i < lstBeneficaryAccount.size(); i++) {
			BeneficaryAccount beneficaryAccount = lstBeneficaryAccount.get(i);
			if (perRmTeleDtBean.getBeneficaryAccountSeqId().compareTo(beneficaryAccount.getBeneficaryAccountSeqId()) == 0) {
				lstUpdateBeneficaryAccount.add(beneficaryAccount);
				backFromRemmitanceServicePanel();
				setServiceGroupId(beneficaryAccount.getServicegropupId().getServiceGroupId());
				enableServiceProvider();
				setBenifisCountryId(beneficaryAccount.getBeneficaryCountry().getCountryId());
				popbanklistTelePhone(beneficaryAccount.getBeneficaryCountry().getCountryId());
				popbranchcodeTelePhone(beneficaryAccount.getBankBranch().getBankBranchId());
				setBenifisBankId(beneficaryAccount.getBank().getBankId());
				popbranchlistTelePhone(beneficaryAccount.getBank().getBankId());
				if (beneficaryAccount.getServiceProvider() != null) {
					setServiceTypeId(beneficaryAccount.getServiceProvider().getBankId());
				}
				benServiceCurrencyList();
				setBenifisCurrencyId(beneficaryAccount.getCurrencyId().getCurrencyId());
				setServicebankBranchId(beneficaryAccount.getBankBranch().getBankBranchId());
				setBankAccountNumber(beneficaryAccount.getBankAccountNumber());
				if(beneficaryAccount.getBankAccountTypeId()!=null) {
					setBankAccountType(beneficaryAccount.getBankAccountTypeId());
				}
				if(beneficaryAccount.getSwiftCode()!=null)
				{
					setBeneSwiftCode(beneficaryAccount.getSwiftCode());
				}
				setAliasFirstName(beneficaryAccount.getAliasFirstName());
				setAliasSecondName(beneficaryAccount.getAliasSecondName());
				setAliasThirdName(beneficaryAccount.getAliasThirdName());
				setAliasFourthName(beneficaryAccount.getAliasFourthName());

				setOccupation(beneficaryAccount.getBeneficaryMaster().getOccupation());
				if(beneficaryAccount.getBeneficaryMaster().getFsCountryMaster() != null){
					setBeneCountryid(beneficaryAccount.getBeneficaryMaster().getFsCountryMaster().getCountryId());
				}
				if(beneficaryAccount.getBeneficaryMaster().getFsStateMaster() != null){
					setBenifisStateId(beneficaryAccount.getBeneficaryMaster().getFsStateMaster().getStateId());
				}
				if(beneficaryAccount.getBeneficaryMaster().getFsDistrictMaster() != null){
					setDistictId(beneficaryAccount.getBeneficaryMaster().getFsDistrictMaster().getDistrictId());
				}
				if(beneficaryAccount.getBeneficaryMaster().getFsCityMaster() != null){
					setCityId(beneficaryAccount.getBeneficaryMaster().getFsCityMaster().getCityId());
				}
				popDistictTelePhone(beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getBeneficaryMaster().getFsStateMaster().getStateId());
				popCitylistTelePhone(beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getBeneficaryMaster().getFsStateMaster().getStateId(), beneficaryAccount.getBeneficaryMaster().getFsDistrictMaster().getDistrictId());


				log.info("Telephone numnber already exist. forwared to benificaryaccountdetails page");
				setDisableSaveButton(false);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
		}
	}

	public void popbranchcodeTelePhone(BigDecimal baranchID) {
		for (BankBranch bankBranchMaster : lstBankbranch) {
			if (bankBranchMaster.getBankBranchId().compareTo(baranchID) == 0) {
				setBankBranchCode(bankBranchMaster.getBranchCode());
			}
		}
	}

	public void popbranchlistTelePhone(BigDecimal bankID) {
		popCurrencylistBankTelePhone(bankID);
		lstBankbranch = getiPersonalRemittanceService().getBankbranchlist(bankID);
		for (BanksView bankMaster : lstBankFromView) {
			if (bankMaster.getBankId().compareTo(bankID) == 0) {
				setBankCode(bankMaster.getBankCode());
			}
		}
	}

	public void popDistictTelePhone(BigDecimal countryId, BigDecimal stateID) {
		lstDistict = generalService.getDistrictList(sessionmanage.getLanguageId(), countryId, stateID);
		for (StateMasterDesc stateMaster : lststate) {
			if (stateMaster.getStateDescId().compareTo(stateID) == 0) {
				setStateName(stateMaster.getStateName());
			}
		}
	}

	public void popCitylistTelePhone(BigDecimal countryId, BigDecimal stateID, BigDecimal disticID) {
		lstCity = generalService.getCityList(sessionmanage.getLanguageId(), countryId, stateID, disticID);
		for (DistrictMasterDesc districtMaster : lstDistict) {
			if (districtMaster.getDistrictDescId().compareTo(disticID) == 0) {
				setDistrictName(districtMaster.getDistrict());
			}
		}
	}

	public void popCurrencylistBankTelePhone(BigDecimal bankID) {
		listCurrencyAccountDetails = ifundservice.getCurrencyOfBank(bankID);
	}

	public void benServiceCurrencyList() {
		beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(getBenifisCountryId());
	}

	public List<ViewBeneServiceCurrency> getBeneServiceCurrencyList() {
		return beneServiceCurrencyList;
	}

	public void setBeneServiceCurrencyList(List<ViewBeneServiceCurrency> beneServiceCurrencyList) {
		this.beneServiceCurrencyList = beneServiceCurrencyList;
	}

	/*public void editBeneficaray(PersonalRemmitanceBeneficaryDataTable datatabledetails) throws Exception {
		clear();
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		// cal1.add(Calendar.YEAR, -Constants.AGE_LIMIT);
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);
		relationDescList = getiPersonalRemittanceService().getRelationsDescriptionList(sessionmanage.getLanguageId());
		log.info("Entering into edit method ");
		log.info(datatabledetails);
		log.info(datatabledetails.getBenificaryMasterId());
		setBooRenderIndBenificaryStatusPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooMoreTelephoneNumbers(false);
		List<BeneficaryMaster> masterList = iPersonalRemittanceService.getAllMasterValues(datatabledetails.getBenificaryMasterId());
		if (masterList != null && !masterList.isEmpty()) {
			setBeneMatsterSeqId(datatabledetails.getBenificaryMasterId());
			setBeniStatusName(masterList.get(0).getBeneficaryStatusName());
			setBenificaryStatusName(benificaryStatusName);
			if(masterList.get(0).getBeneficaryStatus()!=null){
				setBeneficaryStatusId(masterList.get(0).getBeneficaryStatus().getBeneficaryStatusId());
			}

			setFirstName(masterList.get(0).getFirstName());
			setSecondName(masterList.get(0).getSecondName());
			setThirdName(masterList.get(0).getThirdName());
			setFourthName(masterList.get(0).getFourthName());
			setFifthName(masterList.get(0).getFifthName());
			 

			if(masterList.get(0).getFirstName()!=null)
			{
				setFirstName(masterList.get(0).getFirstName());
			}
			else
			{
				setFirstName(null);
			}

			if(masterList.get(0).getSecondName()!=null)
			{
				setSecondName(masterList.get(0).getSecondName());
			}
			else
			{
				setSecondName(null);
			}

			if(masterList.get(0).getThirdName()!=null)
			{
				setThirdName(masterList.get(0).getThirdName());
			}
			else
			{
				setThirdName(null);
			}


			if(masterList.get(0).getFourthName()!=null){
				setFourthName(masterList.get(0).getFourthName());
			}
			else
			{
				setFourthName(null);
			}

			if(masterList.get(0).getFifthName()!=null)
			{
				setFifthName(masterList.get(0).getFifthName());
			}
			else
			{
				setFifthName(null);
			}

			if(masterList.get(0).getLocalFirstName()!=null){
				setFirstLName(masterList.get(0).getLocalFirstName());
			}
			else
			{
				setFirstLName(null);
			}
			if(masterList.get(0).getLocalSecondName()!=null) {
				setSecondLName(masterList.get(0).getLocalSecondName());
			}
			else
			{
				setSecondLName(null);
			}
			if(masterList.get(0).getLocalThirdName()!=null) {
				setThirdLName(masterList.get(0).getLocalThirdName());
			}
			else
			{
				setThirdLName(null);
			}

			if(masterList.get(0).getLocalFourthName()!=null) {
				setFourthLName(masterList.get(0).getLocalFourthName());
			}
			else
			{
				setFourthLName(null);
			}


			setThirdLName(masterList.get(0).getLocalThirdName());
			setFourthLName(masterList.get(0).getLocalFourthName());
			setNationalityName(new BigDecimal(masterList.get(0).getNationality()));
			setOccupation(masterList.get(0).getOccupation());


			setMasterCreatedBy(masterList.get(0).getCreatedBy());
			setMasterCreatedDate(masterList.get(0).getCreatedDate());
			setNoOfRemittance(masterList.get(0).getNoOfRemittance());
			setApplicationCountryId(masterList.get(0).getApplicationCountryId().getCountryId());
			setIsActive(masterList.get(0).getIsActive());
			lstBank = generalService.getBankList(masterList.get(0).getFsCountryMaster().getCountryId());
			beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(masterList.get(0).getFsCountryMaster().getCountryId());
			if(masterList.get(0).getFsCountryMaster() != null){
				setBeneCountryid(masterList.get(0).getFsCountryMaster().getCountryId());
			}
			popStatelist();
			if(masterList.get(0).getFsStateMaster() != null){
				setBenifisStateId(masterList.get(0).getFsStateMaster().getStateId());
			}
			popDistict();
			if(masterList.get(0).getFsDistrictMaster() != null){
				setDistictId(masterList.get(0).getFsDistrictMaster().getDistrictId());
			}
			popCitylist();
			if(masterList.get(0).getFsCityMaster() != null){
				setCityId(masterList.get(0).getFsCityMaster().getCityId());
			}
			setDistrictName(masterList.get(0).getDistrictName());
			setCityName(masterList.get(0).getCityName());
			setStateName(masterList.get(0).getStateName());
			List<BeneficaryContact> contactDetails = iPersonalRemittanceService.getTelephoneDetails(datatabledetails.getBenificaryMasterId());
			if (contactDetails != null && !contactDetails.isEmpty()) {

				List<CountryMasterDesc> countryList =beneficaryCreation.checkCountryCode(contactDetails.get(0).getCountryTelCode());
				if(countryList!= null && !countryList.isEmpty())
				{
					if(countryList.size() >1)
					{
						throw new Exception("Setup Error - Duplicate Tel Country Code exist "+ contactDetails.get(0).getCountryTelCode() );
					}
				}
				setCountryCode(contactDetails.get(0).getCountryTelCode());
				setMcountryCode(contactDetails.get(0).getCountryTelCode());
				setBeneficaryTelephoneSeqId(contactDetails.get(0).getBeneficaryTelephoneSeqId());
				if (contactDetails.get(0).getTelephoneNumber() != null) {
					setTelephoneNumber(contactDetails.get(0).getTelephoneNumber());
				} else {
					setTelephoneNumber(null);
				}
				setMobileNumber(contactDetails.get(0).getMobileNumber());
				setYearOfBrith(masterList.get(0).getYearOfBrith());
				setDateOfBrith(masterList.get(0).getDateOfBrith());
				setContactCreatedBy(contactDetails.get(0).getCreatedBy());
				setContactCreatedDate(contactDetails.get(0).getCreatedDate());
				setAge(masterList.get(0).getAge());
			}
			String type = "";


			List<BeneficaryAccount> accountList = null;
			if (datatabledetails.getAccountNo() != null) {
				//	accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);

				accountList =beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);


			} else {
				accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBenificaryMasterId(), type);
			}
			if (accountList != null && !accountList.isEmpty()) {
				setServiceGroupId(accountList.get(0).getServicegropupId().getServiceGroupId());
				serviceGroupIdforCash = iPersonalRemittanceService.getserviceGroupforCash(sessionmanage.getLanguageId());
				setBenifisCountryId(accountList.get(0).getBeneficaryCountry().getCountryId());
				if (serviceGroupIdforCash != null && serviceGroupIdforCash.equals(getServiceGroupId())) {
					type = "cash";
					List<BeneficaryAccount> accountListwithServiceProvider = null;
					if (datatabledetails.getAccountNo() == null) {
						accountListwithServiceProvider = beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);
						//lstBank.clear();
						//List<BankApplicability> FbankList = bankApplicabilityService.getApplicabilityBankList(sessionmanage.getCountryId(), Constants.BANK_INDICATOR_AGENT_BANK);
						for (BankApplicability bankApplicability : FbankList) {
							lstBank.add(bankApplicability.getBankMaster());
						}
						fetchAllBeneServiceBanks();
					} else {
						accountListwithServiceProvider = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBenificaryMasterId(), type);
					}
					if (accountListwithServiceProvider != null && !accountListwithServiceProvider.isEmpty()) {
						setServiceTypeId(accountListwithServiceProvider.get(0).getBank().getBankId());
						setBooenableAgentPanel(false);
					}
				} else {
					setBooenableAgentPanel(true);
				}
				serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());


				setBenifisBankId(accountList.get(0).getBank().getBankId());
				setBankName(generalService.getBankName(getBenifisBankId()));
				if(accountList.get(0).getBankBranch()!=null){
					setServicebankBranchId(accountList.get(0).getBankBranch().getBankBranchId());
					BankBranch bankBranch = beneficaryCreation.getBankBranch(accountList.get(0).getBank().getBankId(),accountList.get(0).getBankBranch().getBankBranchId(),accountList.get(0).getBeneficaryCountry().getCountryId());
					if(bankBranch!=null) {
						setBankBranchName(bankBranch.getBranchFullName());
						if (bankBranch.getFsStateMaster() != null) {
							setBeneficaryBankState(generalService.getStateName(sessionmanage.getLanguageId(), getBenifisCountryId(), bankBranch.getFsStateMaster().getStateId()));
							if (bankBranch.getFsDistrictMaster() != null) {
								setBeneficaryBankDistrict(generalService.getDistrictName(sessionmanage.getLanguageId(),  getBenifisCountryId(), bankBranch.getFsStateMaster().getStateId(), bankBranch.getFsDistrictMaster().getDistrictId()));
								if (bankBranch.getFsCityMaster() != null) {
									setBeneficaryBankCity(generalService.getCityName(sessionmanage.getLanguageId(),  getBenifisCountryId(), bankBranch.getFsStateMaster().getStateId(), bankBranch.getFsDistrictMaster().getDistrictId(), bankBranch.getFsCityMaster().getCityId()));
								}
							}
						}
					}
				}



				try {
					lstBankAccountType = beneficaryCreation.getBankAccountType(sessionmanage.getLanguageId());

					lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBenifisCountryId());

				} catch (Exception e) {
					RequestContext.getCurrentInstance().execute("dself.show();");
					setErrmsg("Exception occured "+ e.getMessage());
					setBenifisBankId(null);
					return;

				}

				benServiceCurrencyList();
				popCurrencylist();
				setBenifisCurrencyId(accountList.get(0).getCurrencyId().getCurrencyId());
				//setServicebankBranchId(accountList.get(0).getBankBranch().getBankBranchId());
				//setBeneSwiftCode(accountList.get(0).getSwiftCode());
				setBankAccountNumber(accountList.get(0).getBankAccountNumber());
				if(accountList.get(0).getBankAccountTypeId()!=null) {
					setBankAccountType(accountList.get(0).getBankAccountTypeId());
				}
				if(accountList.get(0).getSwiftCode()!=null)
				{
					setBeneSwiftCode(accountList.get(0).getSwiftCode());
				}
				setTempAccountNumber(accountList.get(0).getBankAccountNumber());
				setAliasFirstName(accountList.get(0).getAliasFirstName());
				setAliasSecondName(accountList.get(0).getAliasSecondName());
				setAliasThirdName(accountList.get(0).getAliasThirdName());
				setAliasFourthName(accountList.get(0).getAliasFourthName());
				setBankCode(accountList.get(0).getBankCode());
				setAccountCreatedBy(accountList.get(0).getCreatedBy());
				setAccountCreatedDate(accountList.get(0).getCreatedDate());
				setBankBranchCode(accountList.get(0).getBankBranchCode());
				setBeneficaryAccountSeqId(accountList.get(0).getBeneficaryAccountSeqId());
				setBankCode(accountList.get(0).getBankCode());
				//	fetchingAllSwiftMaster();

			}

			System.out.println("datatabledetails.getBenificaryMasterId() :"+datatabledetails.getBenificaryMasterId()+"\t getBeneficaryAccountSeqId() :"+getBeneficaryAccountSeqId());
			List<BeneficaryRelationship> relationList = iPersonalRemittanceService.isBenificaryRelationExist(datatabledetails.getBenificaryMasterId(), getBeneficaryAccountSeqId());


			if (relationList != null && !relationList.isEmpty()) {
				setBeneficaryRelationshipId(relationList.get(0).getBeneficaryRelationshipId());
				if (relationList.get(0).getRelations().getRelationsId() != null && Constants.SELF.equals(irelation.getEngRelation(relationList.get(0).getRelations().getRelationsId()))) {
					setBeneficaryTypeId(new BigDecimal(1));
					log.info("from Edit ---- SELF");
					setDisableDataOfBirth(true);
				} else {
					setBeneficaryTypeId(new BigDecimal(2));
					setDisableDataOfBirth(false);
					log.info("from Edit ---- others");
				}
				setRelationId(relationList.get(0).getRelations().getRelationsId());
				setCustomerNo(relationList.get(0).getCustomerId().getCustomerId());
				setRelationCreatedBy(relationList.get(0).getCreatedBy());
				setRelationCreatedDate(relationList.get(0).getCreatedDate());
			}
		}
		else
		{
			throw new Exception("BENEFICARY_STATUS is missing for this beneficary");
		}
		lstDataTableBankbranch.clear();
		log.info("Exit into edit method ");
	}*/

	public void editBeneficary() throws Exception {
		nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
		beneCountryList = generalService.getCountryList(sessionmanage.getLanguageId());
		lstCountry = beneCountryList;
		//populateBenificaryAccountDetails();
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("datatabledetails") != null) {
			PersonalRemmitanceBeneficaryDataTable datatabledetails = (PersonalRemmitanceBeneficaryDataTable) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("datatabledetails");
			try {
				editBeneficarayPage(datatabledetails);
			} catch (Exception e) {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				try {
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("error", e.getMessage());
					context.redirect("../remittance/PersonalRemittance.xhtml");
				} catch (Exception e1) {
					System.out.println("Exception occured" + e1);
				}
			}
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("datatabledetails");
		}
	}

	public void updateBeneficaryData() {
		// blocked because date of birth , year of birth and age is optional
		/*if (getDateOfBrith() == null && getAge() == null && getYearOfBrith() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg(" Enter any one the Field : Date Year Of Birth or Age or Date Of Birth ");
			return;
		}*/

		if (getTelephoneNumber() == null && getMobileNumber() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter mobile number or Telephone number");
			return;
		}
		if (getTelephoneNumber() != null  && (getCountryCode()==null || getCountryCode()!=null && getCountryCode().trim().equals("")) ) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please select Telephone Country Code");
			return;
		}
		if (getMobileNumber() != null  && (getMcountryCode()==null || getMcountryCode()!=null && getMcountryCode().trim().equals("")) ) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please select Mobile Country Code");
			return;
		}


		if (getCountryCode() == null && getMcountryCode() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter mobile number Code or Telephone number Code");
			return;
		}

		if (getCountryCode()!= null && getCountryCode().trim().equals("") && getMcountryCode()!= null &&  getMcountryCode().trim().equals("")) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter mobile number Code or Telephone number Code");
			return;
		}

		if(getTelephoneNumber() != null && getTelephoneNumber().trim().equals("") && getMobileNumber()==null )
		{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Telephone number or Mobile Number");
			return;
		}
		BeneficaryContact removeContact = null;
		if(lstContactDetails!=null && lstContactDetails.size() > 1)
		{
			for (BeneficaryContact contactDetails : lstContactDetails) {
				if (contactDetails.getTelephoneNumber().equals(getTelephoneNumberSelect())) {
					removeContact = contactDetails;
				}
			}
		}
		if(removeContact!=null)
		{
			lstContactDetails.remove(removeContact);
			beneficaryCreation.deActivateMultipleMobileNumbers(lstContactDetails);
		}


		BeneficaryContact removeMobileContact = null;
		if(lstMobileDetails!=null && lstMobileDetails.size() > 1 && getMobileNumberSelect()!=null)
		{
			for (BeneficaryContact contactDetails : lstMobileDetails) {
				if (contactDetails.getMobileNumber().equals(getMobileNumberSelect())) {
					removeMobileContact = contactDetails;
				}
			}
		}
		if(removeMobileContact!=null)
		{
			lstMobileDetails.remove(removeMobileContact);
			beneficaryCreation.deActivateMultipleMobileNumbers(lstMobileDetails);
		}

		BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
		beneficaryMaster.setBeneficaryMasterSeqId(getBeneMatsterSeqId());
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(getApplicationCountryId());
		beneficaryMaster.setApplicationCountryId(countryMaster);
		CountryMaster countryMaster1 = new CountryMaster();
		countryMaster1.setCountryId(getBeneCountryid());
		beneficaryMaster.setFsCountryMaster(countryMaster1);
		beneficaryMaster.setFirstName(getFirstName());
		beneficaryMaster.setSecondName(getSecondName());
		beneficaryMaster.setThirdName(getThirdName());
		beneficaryMaster.setFourthName(getFourthName());
		beneficaryMaster.setFifthName(getFifthName());

		beneficaryMaster.setLocalFirstName(getFirstLName());
		beneficaryMaster.setLocalSecondName(getSecondLName());
		beneficaryMaster.setLocalThirdName(getThirdLName());
		beneficaryMaster.setLocalFourthName(getFourthLName());

		BeneficaryStatus beneficaryStatus = new BeneficaryStatus();
		beneficaryStatus.setBeneficaryStatusId(getBeneficaryStatusId());
		beneficaryMaster.setBeneficaryStatus(beneficaryStatus);
		beneficaryMaster.setNationality(getNationalityName().toString());
		beneficaryMaster.setDateOfBrith(getDateOfBrith());
		beneficaryMaster.setYearOfBrith(getYearOfBrith());
		beneficaryMaster.setAge(getAge());
		if(getBenifisStateId() != null){
			StateMaster stateMaster = new StateMaster();
			stateMaster.setStateId(getBenifisStateId());
			beneficaryMaster.setFsStateMaster(stateMaster);
			beneficaryMaster.setStateName(getStateName());
		}
		if(getDistictId() != null){
			DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(getDistictId());
			beneficaryMaster.setFsDistrictMaster(districtMaster);
			beneficaryMaster.setDistrictName(getDistrictName());
		}
		if(getCityId() != null){
			CityMaster cityMaster = new CityMaster();
			cityMaster.setCityId(getCityId());
			beneficaryMaster.setFsCityMaster(cityMaster);
			beneficaryMaster.setCityName(getCityName());
		}
		beneficaryMaster.setOccupation(getOccupation());
		beneficaryMaster.setNoOfRemittance(getNoOfRemittance());
		beneficaryMaster.setIsActive(Constants.Yes);
		beneficaryMaster.setCreatedBy(getMasterCreatedBy());
		beneficaryMaster.setCreatedDate(getMasterCreatedDate());
		beneficaryMaster.setModifiedBy(sessionmanage.getUserName());
		beneficaryMaster.setModifiedDate(new Date());
		beneficaryMaster.setBeneficaryStatusName(getBeniStatusName());
		BeneficaryAccount account = new BeneficaryAccount();
		account.setBeneficaryAccountSeqId(getBeneficaryAccountSeqId());
		account.setBeneApplicationCountry(countryMaster);
		CountryMaster countryMaster2 = new CountryMaster();
		countryMaster2.setCountryId(getBenifisCountryId());
		account.setBeneficaryCountry(countryMaster2);
		BankMaster bankMaster = new BankMaster();
		bankMaster.setBankId(getBenifisBankId());
		account.setBank(bankMaster);
		BankBranch bankBranch = new BankBranch();
		bankBranch.setBankBranchId(getServicebankBranchId());
		account.setBankBranch(bankBranch);
		ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
		serviceGroupMaster.setServiceGroupId(getServiceGroupId());
		account.setServicegropupId(serviceGroupMaster);


		if(getBankAccountNumber() != null){
			account.setBankAccountNumber(getBankAccountNumber());
			int accountLength = getBankAccountNumber().length();
			int i = 0;
			String lengthValues = "";
			List<BankAccountLength> lstofAccLength = getiPersonalRemittanceService().getBankAccountLengthByBank(getBenifisBankId());
			if (lstofAccLength.size() != 0) {
				for (BankAccountLength bankAccountLength : lstofAccLength) {
					if ((bankAccountLength.getAcLength().compareTo(new BigDecimal(0))!=0) && bankAccountLength.getAcLength().compareTo(new BigDecimal(accountLength)) == 0) {
						i = 1;
						break;
					}

					else if((bankAccountLength.getAcLength().compareTo(new BigDecimal(0))==0))
					{

						i=-1;
					}

					else {
						String commaAdd = "";
						if (!lengthValues.equalsIgnoreCase("")) {
							commaAdd = lengthValues + " OR ";
						}
						lengthValues = commaAdd + bankAccountLength.getAcLength().toPlainString();
						i = 0;
					}
				}
				if (i == 0) {
					//	setBankAccountNumber(null);
					setBankAccountLength(lengthValues);
					RequestContext.getCurrentInstance().execute("acclengthmismatch.show();");
					return;
				}

			}
		}
		
		account.setBankAccountTypeId(getBankAccountType());
		account.setSwiftCode(getBeneSwiftCode());
		account.setBankCode(getBankCode());
		account.setBankBranchCode(bankBranch.getBranchCode());
		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(getBenifisCurrencyId());
		account.setCurrencyId(currencyMaster);
		account.setBankBranchCode(getBankBranchCode());
		account.setBankCode(getBankCode());
		account.setAliasFirstName(getAliasFirstName());
		account.setAliasSecondName(getAliasSecondName());
		account.setAliasThirdName(getAliasThirdName());
		account.setAliasFourthName(getAliasFourthName());
		account.setIsActive(Constants.Yes);
		account.setCreatedBy(getAccountCreatedBy());
		account.setCreatedDate(getAccountCreatedDate());
		if (getServiceTypeId() != null) {
			BankMaster bankMasterServiceProvider = new BankMaster();
			bankMasterServiceProvider.setBankId(getServiceTypeId());
			account.setServiceProvider(bankMasterServiceProvider);
		}
		account.setBeneficaryMaster(beneficaryMaster);
		BeneficaryRelationship beneficaryRelationship = new BeneficaryRelationship();
		beneficaryRelationship.setBeneficaryRelationshipId(getBeneficaryRelationshipId());
		CountryMaster cm = new CountryMaster();
		cm.setCountryId(getApplicationCountryId());
		beneficaryRelationship.setApplicationCountry(cm);
		Customer customer = new Customer();
		customer.setCustomerId(getCustomerNo());
		beneficaryRelationship.setCustomerId(customer);
		Relations relations = new Relations();
		relations.setRelationsId(getRelationId());
		beneficaryRelationship.setRelations(relations);
		beneficaryRelationship.setIsActive(Constants.Yes);
		beneficaryRelationship.setCreatedBy(getRelationCreatedBy());
		beneficaryRelationship.setCreatedDate(getRelationCreatedDate());
		beneficaryRelationship.setModifiedBy(sessionmanage.getUserName());
		beneficaryRelationship.setModifiedDate(new Date());
		beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
		beneficaryRelationship.setBeneficaryAccount(account);
		BeneficaryContact contact = new BeneficaryContact();
		contact.setBeneficaryTelephoneSeqId(getBeneficaryTelephoneSeqId());
		contact.setApplicationCountryId(countryMaster);
		if (getTelephoneNumber() != null) {
			contact.setTelephoneNumber(getTelephoneNumber());
		} else {
			contact.setTelephoneNumber(null);
		}
		contact.setIsActive(Constants.Yes);
		if(getContactCreatedBy()!=null) {
			contact.setCreatedBy(getContactCreatedBy());
			contact.setCreatedDate(getContactCreatedDate());
		}
		else
		{
			contact.setCreatedBy(sessionmanage.getUserName());
			contact.setCreatedDate(new Date());
		}
		contact.setBeneficaryMaster(beneficaryMaster);
		contact.setModifiedBy(sessionmanage.getUserName());
		contact.setModifiedDate(new Date());
		contact.setCountryTelCode(getCountryCode());
		contact.setMobileNumber(getMobileNumber());
		try {

			getiPersonalRemittanceService().updateBeneficaryData(beneficaryMaster, account, beneficaryRelationship, contact);
			String errorMessage = beneficaryCreation.getBeneDetailProce(getBeneMatsterSeqId(),getBenifisBankId(),getServicebankBranchId(),getBeneficaryAccountSeqId(),getBenifisCurrencyId(),getCustomerNo());
			if(errorMessage==null){
				RequestContext.getCurrentInstance().execute("beneficarycomplete.show();");
			}else{
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("EX_POPULATE_BENE_DT "+errorMessage);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occured while update the Benificary" + e.getMessage());
			return;
		}
	}

	public void differentBankDetailsSave()
	{
		BeneficaryMaster beneficaryMaster =null;
		BeneficaryContact contact = null;

		System.out.println("##########################################" + getBeneMatsterSeqId());
		List<BeneficaryMaster> masterList = iPersonalRemittanceService.getAllMasterValues(getBeneMatsterSeqId());
		if (masterList != null && !masterList.isEmpty()) {
			beneficaryMaster = masterList.get(0);
		}
		else
		{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occured while create the Benificary");
			return;
		}
		BeneficaryAccount beneficaryAccountSave = saveBeneficaryAccount(beneficaryMaster);
		BeneficaryRelationship beneficaryRelationShip = saveBeneficaryRelation(beneficaryMaster, beneficaryAccountSave);

		beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccountSave, beneficaryRelationShip);

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficarySave", "yes");

		String errorMessage = beneficaryCreation.getBeneDetailProce(getBeneMatsterSeqId(),getBenifisBankId(),getServicebankBranchId(),getBeneficaryAccountSeqId(),getBenifisCurrencyId(),getCustomerNo());
		if(errorMessage==null){
			RequestContext.getCurrentInstance().execute("beneficarycomplete.show();");
		}else{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("EX_POPULATE_BENE_DT "+errorMessage);
			return;
		}
	}




	/*public void populateCustomerDetailsforDifferentAccount() {
		log.info("Entering into populateCustomerDetailsFromBeneRelation method ");
		coustomerBeneficaryDTList.clear();
		List<BenificiaryListView> isCoustomerExist = beneficaryCreation.getBeneficaryList(getCustomerNo());
		PersonalRemmitanceBeneficaryDataTable personalRBDataTable = null;
		BenificiaryListView rel = null;
		if (isCoustomerExist.size() > 0) {
			for (int i = 0; i < isCoustomerExist.size(); i++) {
				rel = isCoustomerExist.get(i);
				personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();
				personalRBDataTable.setBankNameId(rel.getBankId());
				personalRBDataTable.setBenificaryMasterId(rel.getBeneficaryMasterSeqId());
				personalRBDataTable.setBenificaryCountryId(rel.getBenificaryCountry());
				personalRBDataTable.setBenificaryCountryName(rel.getCountryName());
				personalRBDataTable.setAccountNo(rel.getBankAccountNumber());
				personalRBDataTable.setBenificaryName(rel.getBenificaryName());
				personalRBDataTable.setBranchNameId(rel.getBranchId());
				personalRBDataTable.setLocation(rel.getNationalityName());
				personalRBDataTable.setBankName(rel.getBankName());
				personalRBDataTable.setBranchName(rel.getBankBranchName());
				
				 * if (rel.getTelePhoneNumber() != null) {
				 * personalRBDataTable.setTelphoneNum(new
				 * BigDecimal(rel.getTelePhoneNumber())); }
				 
				String telePhone = beneficaryCreation.getTelePhoneNumberString(rel.getBeneficaryMasterSeqId());
				if (telePhone != null) {
					personalRBDataTable.setTelphoneNum(telePhone);
				}
				else
				{
					BigDecimal mobileNUmber = beneficaryCreation.getMobileNumber(rel.getBeneficaryMasterSeqId());
					if(mobileNUmber!=null)
					{
						personalRBDataTable.setTelphoneNum(mobileNUmber.toPlainString());
					}
				}
				personalRBDataTable.setServiceNameId(rel.getServiceGroupId());
				personalRBDataTable.setCurrencyName(rel.getCurrencyName());
				personalRBDataTable.setCurrencyId(rel.getCurrencyId());
				String relationDescription = rel.getRelationShipName();
				personalRBDataTable.setRelationName(relationDescription);
				List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"), rel.getServiceGroupId());
				if (lstServiceGroupMasterDesc.size() > 0) {
					personalRBDataTable.setServiceName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
				}
				coustomerBeneficaryDTList.add(personalRBDataTable);
				personalRBDataTable = null;
				rel = null;
			}
		}
		log.info("Exit into populateCustomerDetailsFromBeneRelation method ");
	}*/


	public void updateTelePhoneCode()
	{
		setCountryCode(null);
		setMcountryCode(null);
		setTelephoneNumber(null);
		setMobileNumber(null);
		if(getNationalityName()!=null) {
			String teleCountryId = generalService.getTelephoneCountryBasedOnNationality(getNationalityName());
			setCountryCode(teleCountryId);
			setMcountryCode(teleCountryId);
			if(getBeneCountryid() == null){
				setBeneCountryid(getNationalityName());
				popStatelist();
				checkingmandatoryOptional();
			}
		}
	}


	public void pageNavigation()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	private List<PopulateData> lstSwiftMasterBank1 = new ArrayList<PopulateData>();

	private String beneSwiftCode;


	public String getBeneSwiftCode() {
		return beneSwiftCode;
	}

	public void setBeneSwiftCode(String beneSwiftCode) {
		this.beneSwiftCode = beneSwiftCode;
	}

	// original edit facility
	public void editBeneficarayPage(PersonalRemmitanceBeneficaryDataTable datatabledetails)  throws Exception{

		log.info("Entering into edit method ");
		log.info(datatabledetails);
		log.info(datatabledetails.getBeneficaryMasterSeqId());

		// clearing first and second panels
		clear();

		setBeneMatsterSeqId(datatabledetails.getBeneficaryMasterSeqId());

		// rendering booleans
		setBooRenderIndBenificaryStatusPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooMoreTelephoneNumbers(false);
		setBooMoremobileNumbers(false);
		// beneficiary type
		if (datatabledetails.getRelationShipId() != null || datatabledetails.getRelationShipName() != null) {
			setBeneficaryRelationshipId(datatabledetails.getBeneficiaryRelationShipSeqId());
			if (datatabledetails.getRelationShipName()==null ? Constants.SELF.equalsIgnoreCase(irelation.getEngRelation(datatabledetails.getRelationShipId())) : Constants.SELF.equalsIgnoreCase(datatabledetails.getRelationShipName())) {
				setBeneficaryTypeId(new BigDecimal(1));
			} else {
				setBeneficaryTypeId(new BigDecimal(2));
			}
		}

		// beneficiary English names and local names
		if(datatabledetails.getFirstName() != null)
		{
			setFirstName(datatabledetails.getFirstName());
		}
		else
		{
			setFirstName(null);
		}

		if(datatabledetails.getSecondName()  != null)
		{
			setSecondName(datatabledetails.getSecondName());
		}
		else
		{
			setSecondName(null);
		}

		if(datatabledetails.getThirdName() != null)
		{
			setThirdName(datatabledetails.getThirdName());
		}
		else
		{
			setThirdName(null);
		}

		if(datatabledetails.getFourthName() != null){
			setFourthName(datatabledetails.getFourthName());
		}
		else
		{
			setFourthName(null);
		}

		if(datatabledetails.getFiftheName() != null)
		{
			setFifthName(datatabledetails.getFiftheName());
		}
		else
		{
			setFifthName(null);
		}

		if(datatabledetails.getFirstNameLocal() != null){
			setFirstLName(datatabledetails.getFirstNameLocal());
		}
		else
		{
			setFirstLName(null);
		}

		if(datatabledetails.getSecondNameLocal() != null) {
			setSecondLName(datatabledetails.getSecondNameLocal());
		}
		else
		{
			setSecondLName(null);
		}

		if(datatabledetails.getThirdNameLocal() != null) {
			setThirdLName(datatabledetails.getThirdNameLocal());
		}
		else
		{
			setThirdLName(null);
		}

		if(datatabledetails.getFourthNameLocal() != null) {
			setFourthLName(datatabledetails.getFourthNameLocal());
		}
		else
		{
			setFourthLName(null);
		}

		/** added by Rabil on 03/12/2015 for EX_P_BANNED_NAME_CHECK*/ 
		String engName =getFirstName()+" "+ getSecondName()+" "+getThirdName();
		String localName =getFirstLName()+" "+ getSecondLName()+" "+getThirdLName();

		String proceErrorMessage = getiPersonalRemittanceService().getBannedNameCheckProcedure(sessionmanage.getCountryId(), engName,localName);

		if(proceErrorMessage!=null && proceErrorMessage.length()>0){
			throw new Exception(proceErrorMessage);
			//setErrmsg(proceErrorMessage);
			//RequestContext.getCurrentInstance().execute("csp.show();");
			//return;
		}

		// Age Calculation
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);

		// relationship list fetching
		relationDescList = getiPersonalRemittanceService().getRelationsDescriptionList(sessionmanage.getLanguageId());
		setRelationId(datatabledetails.getRelationShipId());

		// nationality 
		if(datatabledetails.getNationality() != null){
			setNationalityName(new BigDecimal(datatabledetails.getNationality()));
		}

		// beneficiary Contact details
		List<BeneficaryContact> contactDetails = beneficaryCreation.getTelephoneDetails(datatabledetails.getBeneficaryMasterSeqId());		
		if (contactDetails != null && !contactDetails.isEmpty()) {
			List<CountryMasterDesc> countryList =beneficaryCreation.checkCountryCode(contactDetails.get(0).getCountryTelCode());
			if(countryList!= null && !countryList.isEmpty())
			{
				if(countryList.size() >1)
				{
					throw new Exception("Setup Error - Duplicate Tel Country Code exist "+ contactDetails.get(0).getCountryTelCode());
				}
			}
			setCountryCode(contactDetails.get(0).getCountryTelCode());
			setMcountryCode(contactDetails.get(0).getCountryTelCode());
			setBeneficaryTelephoneSeqId(contactDetails.get(0).getBeneficaryTelephoneSeqId());
			if (contactDetails.get(0).getTelephoneNumber() != null) {
				setTelephoneNumber(contactDetails.get(0).getTelephoneNumber());
			} else {
				setTelephoneNumber(null);
			}
			if (contactDetails.get(0).getMobileNumber() != null) {
				setMobileNumber(contactDetails.get(0).getMobileNumber());
			} else {
				setMobileNumber(null);
			}
		}
		
		if (contactDetails.size() > 1)
		{
			setBooMoreTelephoneNumbers(true);
			setLstContactDetails(contactDetails);
			setBooSingleTelephoneAvailable(false);
			setBooSingleMobileNumberAvailable(false);
		}
		else
		{
			setBooSingleTelephoneAvailable(true);
		}

		setCustomerId(datatabledetails.getCustomerId());
		setCustomerNo(datatabledetails.getCustomerId());
		
		setYearOfBrith(datatabledetails.getYearOfBirth());
		setDateOfBrith(datatabledetails.getDateOfBirth());
		setContactCreatedBy(datatabledetails.getCreatedBy());
		setContactCreatedDate(datatabledetails.getCreatedDate());
		setAge(datatabledetails.getAge());
		setOccupation(datatabledetails.getOccupation());

		// bank country Id and Name
		setBenifisCountryId(datatabledetails.getCountryId());
		setCountryName(datatabledetails.getCountryName());
		setServiceGroupId(datatabledetails.getServiceGroupId());
		// beneficiary country Id and Name
		setBeneCountryid(datatabledetails.getBenificaryCountry());
		


		checkingmandatoryOptional();
		List<BeneficaryMaster> masterList = iPersonalRemittanceService.getAllMasterValues(datatabledetails.getBeneficaryMasterSeqId());
		if(masterList != null && masterList.size() != 0){
			popStatelist();
			if(datatabledetails.getStateName() != null && masterList.get(0).getFsStateMaster() != null){
				setBenifisStateId(masterList.get(0).getFsStateMaster().getStateId());
			}
			popDistict();
			if(datatabledetails.getDistrictName() != null && masterList.get(0).getFsDistrictMaster() != null){
				setDistictId(masterList.get(0).getFsDistrictMaster().getDistrictId());
			}
			popCitylist();
			if(datatabledetails.getCityName() != null && masterList.get(0).getFsCityMaster() != null){
				setCityId(masterList.get(0).getFsCityMaster().getCityId());
			}
		}

		setStateName(datatabledetails.getStateName());
		setDistrictName(datatabledetails.getDistrictName());
		setCityName(datatabledetails.getCityName());
		setMasterCreatedBy(datatabledetails.getCreatedBy());
		setMasterCreatedDate(datatabledetails.getCreatedDate());
		setNoOfRemittance(datatabledetails.getNoOfRemittance()==null?"":datatabledetails.getNoOfRemittance().toString());
		setApplicationCountryId(datatabledetails.getApplicationCountryId());
		setIsActive(datatabledetails.getIsActive());

		if(datatabledetails.getBenificaryStatusId() != null){
			setBeneficaryStatusId(datatabledetails.getBenificaryStatusId());
			setBeniStatusName(datatabledetails.getBenificaryStatusName());
			setBenificaryStatusName(benificaryStatusName);
		}
		
		setBenifisBankId(datatabledetails.getBankId());
		setBankName(datatabledetails.getBankName());
		setServicebankBranchId(datatabledetails.getBranchId());
		setBankBranchName(datatabledetails.getBankBranchName());

		String type = "";
		List<BeneficaryAccount> accountList = null;
		if (datatabledetails.getBankAccountNumber() != null) {
			accountList = beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getBankAccountNumber(), type);
		} else {
			accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBeneficaryMasterSeqId(), type);
		}
		if (accountList != null && !accountList.isEmpty()) {
			if(accountList.get(0).getSwiftCode()!=null)
			{
				setBeneSwiftCode(accountList.get(0).getSwiftCode());
			}
			if(accountList.get(0).getBankAccountTypeId()!=null) {
				setBankAccountType(accountList.get(0).getBankAccountTypeId());
			}
			serviceGroupIdforCash = iPersonalRemittanceService.getserviceGroupforCash(sessionmanage.getLanguageId());
			if (serviceGroupIdforCash != null && serviceGroupIdforCash.equals(getServiceGroupId())) {
				type = "cash";
				List<BeneficaryAccount> accountListwithServiceProvider = null;
				if (datatabledetails.getBankAccountNumber() == null) {
					accountListwithServiceProvider = beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getBankAccountNumber(), type);
					//fetchAllBeneServiceBanks();
					fetchAllBeneServiceProvider();
				} else {
					accountListwithServiceProvider = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBeneficaryMasterSeqId(), type);
				}
				if (accountListwithServiceProvider != null && !accountListwithServiceProvider.isEmpty()) {
					setServiceTypeId(accountListwithServiceProvider.get(0).getBank().getBankId());
					setBooenableAgentPanel(false);
				}
			} else {
				setBooenableAgentPanel(true);
			}
			
			setAliasFirstName(accountList.get(0).getAliasFirstName());
			setAliasSecondName(accountList.get(0).getAliasSecondName());
			setAliasThirdName(accountList.get(0).getAliasThirdName());
			setAliasFourthName(accountList.get(0).getAliasFourthName());
		}

		serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());

		beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(datatabledetails.getCountryId());
		setBenifisCurrencyId(datatabledetails.getCurrencyId());

		try {
			
			if(lstBankAccountTypeFromView != null || !lstBankAccountTypeFromView.isEmpty()){
				lstBankAccountTypeFromView.clear();
			}
			
			lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBenifisCountryId());
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("dself.show();");
			setErrmsg("Exception occured "+ e.getMessage());
			return;
		}

		BankBranchView lstBankbranchView = beneficaryCreation.getBranchListfromView(getBenifisBankId(),getServicebankBranchId());

		if(lstBankbranchView != null){
			setBeneficaryBankState(lstBankbranchView.getStateName());
			setBeneficaryBankDistrict(lstBankbranchView.getDistrictName());
			setBeneficaryBankCity(lstBankbranchView.getCityName());
		}

		setTempAccountNumber(datatabledetails.getBankAccountNumber());
		setBankCode(datatabledetails.getBankCode());
		setBankAccountNumber(datatabledetails.getBankAccountNumber());
		setAccountCreatedBy(datatabledetails.getCreatedBy());
		setAccountCreatedDate(datatabledetails.getCreatedDate());
		setBankBranchCode(datatabledetails.getBranchCode());
		setBeneficaryAccountSeqId(datatabledetails.getBeneficiaryAccountSeqId());
		
		if(datatabledetails.getServiceProvider()!=null)
		{
			setAgentMaster(datatabledetails.getBankId());
			setAgentBranch(datatabledetails.getBranchId());
			setServiceTypeId(datatabledetails.getServiceProvider());
			setBankBranchName(datatabledetails.getBankBranchName());
		}

		lstDataTableBankbranch.clear();
		log.info("Exit into edit method ");
	}


	/*public void editBeneficarayDifferntServicesorAccount(PersonalRemmitanceBeneficaryDataTable datatabledetails) throws Exception {
		clear();
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		// cal1.add(Calendar.YEAR, -Constants.AGE_LIMIT);
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);
		relationDescList = getiPersonalRemittanceService().getRelationsDescriptionList(sessionmanage.getLanguageId());
		
		 * HttpSession session = sessionStateManage.getSession();
		 * session.setAttribute("masterSeqId",
		 * datatabledetails.getBenificaryMasterId());
		 
		log.info("Entering into edit method ");
		log.info(datatabledetails);
		log.info(datatabledetails.getBeneficaryMasterSeqId());
		setBooRenderIndBenificaryStatusPanel(true);
		setBooRenderBenificarySearchPanel(false);
		List<BeneficaryMaster> masterList = iPersonalRemittanceService.getAllMasterValues(datatabledetails.getBeneficaryMasterSeqId());
		if (masterList != null && !masterList.isEmpty()) {
			setBeneMatsterSeqId(datatabledetails.getBeneficaryMasterSeqId());
			setBeniStatusName(masterList.get(0).getBeneficaryStatusName());
			setBenificaryStatusName(benificaryStatusName);
			if(masterList.get(0).getBeneficaryStatus()!=null){
				setBeneficaryStatusId(masterList.get(0).getBeneficaryStatus().getBeneficaryStatusId());
			}

			if(masterList.get(0).getFirstName()!=null)
			{
				setFirstName(masterList.get(0).getFirstName());
			}

			if(masterList.get(0).getSecondName()!=null)
			{
				setSecondName(masterList.get(0).getSecondName());
			}

			if(masterList.get(0).getThirdName()!=null)
			{
				setThirdName(masterList.get(0).getThirdName());
			}


			if(masterList.get(0).getFourthName()!=null){
				setFourthName(masterList.get(0).getFourthName());
			}

			if(masterList.get(0).getFifthName()!=null)
			{
				setFifthName(masterList.get(0).getFifthName());
			}

			if(masterList.get(0).getLocalFirstName()!=null){
				setFirstLName(masterList.get(0).getLocalFirstName());
			}
			else
			{
				setFirstLName(null);
			}
			if(masterList.get(0).getLocalSecondName()!=null) {
				setSecondLName(masterList.get(0).getLocalSecondName());
			}
			else
			{
				setSecondLName(null);
			}
			if(masterList.get(0).getLocalThirdName()!=null) {
				setThirdLName(masterList.get(0).getLocalThirdName());
			}
			else
			{
				setThirdLName(null);
			}

			if(masterList.get(0).getLocalFourthName()!=null) {
				setFourthLName(masterList.get(0).getLocalFourthName());
			}
			else
			{
				setFourthLName(null);
			}


			setThirdLName(masterList.get(0).getLocalThirdName());
			setFourthLName(masterList.get(0).getLocalFourthName());
			if(masterList.get(0).getFsCountryMaster() != null){
				setBeneCountryid(masterList.get(0).getFsCountryMaster().getCountryId());
			}
			if(masterList.get(0).getFsStateMaster() != null){
				setBenifisStateId(masterList.get(0).getFsStateMaster().getStateId());
			}
			if(masterList.get(0).getFsDistrictMaster() != null){
				setDistictId(masterList.get(0).getFsDistrictMaster().getDistrictId());
			}
			if(masterList.get(0).getFsCityMaster() != null){
				setCityId(masterList.get(0).getFsCityMaster().getCityId());
			}

			setOccupation(masterList.get(0).getOccupation());
			setMasterCreatedBy(masterList.get(0).getCreatedBy());
			setMasterCreatedDate(masterList.get(0).getCreatedDate());
			setNoOfRemittance(masterList.get(0).getNoOfRemittance());
			setApplicationCountryId(masterList.get(0).getApplicationCountryId().getCountryId());
			setIsActive(masterList.get(0).getIsActive());
			//lstBank = generalService.getBankList(masterList.get(0).getFsCountryMaster().getCountryId());
			beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(masterList.get(0).getFsCountryMaster().getCountryId());
			setDistrictName(masterList.get(0).getDistrictName());
			setCityName(masterList.get(0).getCityName());
			setStateName(masterList.get(0).getStateName());
			List<BeneficaryContact> contactDetails = iPersonalRemittanceService.getTelephoneDetails(datatabledetails.getBenificaryMasterId());
			if (contactDetails != null && !contactDetails.isEmpty()) {

				List<CountryMasterDesc> countryList =beneficaryCreation.checkCountryCode(contactDetails.get(0).getCountryTelCode());
				if(countryList!= null && !countryList.isEmpty())
				{
					if(countryList.size() >1)
					{
						throw new Exception("Setup Error - Duplicate Tel Country Code exist "+ contactDetails.get(0).getCountryTelCode() );
					}
				}
				setCountryCode(contactDetails.get(0).getCountryTelCode());
				setMcountryCode(contactDetails.get(0).getCountryTelCode());
				setBeneficaryTelephoneSeqId(contactDetails.get(0).getBeneficaryTelephoneSeqId());
				if (contactDetails.get(0).getTelephoneNumber() != null) {
					setTelephoneNumber(contactDetails.get(0).getTelephoneNumber());
				} else {
					setTelephoneNumber(null);
				}
				setMobileNumber(contactDetails.get(0).getMobileNumber());
				setYearOfBrith(masterList.get(0).getYearOfBrith());
				setDateOfBrith(masterList.get(0).getDateOfBrith());
				setContactCreatedBy(contactDetails.get(0).getCreatedBy());
				setContactCreatedDate(contactDetails.get(0).getCreatedDate());
				setAge(masterList.get(0).getAge());
			}
			String type = "";


			List<BeneficaryAccount> accountList = null;
			if (datatabledetails.getAccountNo() != null) {
				//	accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);

				accountList =beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);


			} else {
				accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBenificaryMasterId(), type);
			}
			if (accountList != null && !accountList.isEmpty()) {


				setServiceGroupId(accountList.get(0).getServicegropupId().getServiceGroupId());
				serviceGroupIdforCash = iPersonalRemittanceService.getserviceGroupforCash(sessionmanage.getLanguageId());
				setBenifisCountryId(accountList.get(0).getBeneficaryCountry().getCountryId());
				if (serviceGroupIdforCash != null && serviceGroupIdforCash.equals(getServiceGroupId())) {
					type = "cash";
					List<BeneficaryAccount> accountListwithServiceProvider = null;
					if (datatabledetails.getAccountNo() == null) {
						accountListwithServiceProvider = beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);
						//lstBank.clear();
						//List<BankApplicability> FbankList = bankApplicabilityService.getApplicabilityBankList(sessionmanage.getCountryId(), Constants.BANK_INDICATOR_AGENT_BANK);
						for (BankApplicability bankApplicability : FbankList) {
							lstBank.add(bankApplicability.getBankMaster());
						}
						fetchAllBeneServiceBanks();
					} else {
						accountListwithServiceProvider = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBenificaryMasterId(), type);
					}
					if (accountListwithServiceProvider != null && !accountListwithServiceProvider.isEmpty()) {
						setServiceTypeId(accountListwithServiceProvider.get(0).getBank().getBankId());
						setBooenableAgentPanel(false);
					}
				} else {
					setBooenableAgentPanel(true);
				}
				serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());


				setBenifisBankId(accountList.get(0).getBank().getBankId());
				setBankName(generalService.getBankName(getBenifisBankId()));
				if(accountList.get(0).getBankBranch()!=null){
					setServicebankBranchId(accountList.get(0).getBankBranch().getBankBranchId());
					BankBranch bankBranch = beneficaryCreation.getBankBranch(accountList.get(0).getBank().getBankId(),accountList.get(0).getBankBranch().getBankBranchId(),accountList.get(0).getBeneficaryCountry().getCountryId());
					if(bankBranch!=null) {
						setBankBranchName(bankBranch.getBranchFullName());
						if (bankBranch.getFsStateMaster() != null) {
							setBeneficaryBankState(generalService.getStateName(sessionmanage.getLanguageId(), getBenifisCountryId(), bankBranch.getFsStateMaster().getStateId()));
							if (bankBranch.getFsDistrictMaster() != null) {
								setBeneficaryBankDistrict(generalService.getDistrictName(sessionmanage.getLanguageId(),  getBenifisCountryId(), bankBranch.getFsStateMaster().getStateId(), bankBranch.getFsDistrictMaster().getDistrictId()));
								if (bankBranch.getFsCityMaster() != null) {
									setBeneficaryBankCity(generalService.getCityName(sessionmanage.getLanguageId(),  getBenifisCountryId(), bankBranch.getFsStateMaster().getStateId(), bankBranch.getFsDistrictMaster().getDistrictId(), bankBranch.getFsCityMaster().getCityId()));
								}
							}
						}
					}
				}



				try {
					lstBankAccountType = beneficaryCreation.getBankAccountType(sessionmanage.getLanguageId());

					lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBenifisCountryId());

				} catch (Exception e) {
					RequestContext.getCurrentInstance().execute("dself.show();");
					setErrmsg("Exception occured "+ e.getMessage());
					setBenifisBankId(null);
					return;

				}

				benServiceCurrencyList();
				popCurrencylist();
				setBenifisCurrencyId(accountList.get(0).getCurrencyId().getCurrencyId());
				//setServicebankBranchId(accountList.get(0).getBankBranch().getBankBranchId());
				setBeneSwiftCode(accountList.get(0).getSwiftCode());
				setBankAccountNumber(accountList.get(0).getBankAccountNumber());
				if(accountList.get(0).getBankAccountTypeId()!=null) {
					setBankAccountType(accountList.get(0).getBankAccountTypeId());
				}
				if(accountList.get(0).getSwiftCode()!=null)
				{
					setBeneSwiftCode(accountList.get(0).getSwiftCode());
				}
				setTempAccountNumber(accountList.get(0).getBankAccountNumber());
				setAliasFirstName(accountList.get(0).getAliasFirstName());
				setAliasSecondName(accountList.get(0).getAliasSecondName());
				setAliasThirdName(accountList.get(0).getAliasThirdName());
				setAliasFourthName(accountList.get(0).getAliasFourthName());
				setBankCode(accountList.get(0).getBankCode());
				setAccountCreatedBy(accountList.get(0).getCreatedBy());
				setAccountCreatedDate(accountList.get(0).getCreatedDate());
				setBankBranchCode(accountList.get(0).getBankBranchCode());
				setBeneficaryAccountSeqId(accountList.get(0).getBeneficaryAccountSeqId());
				setBankCode(accountList.get(0).getBankCode());
				//	fetchingAllSwiftMaster();

				popStatelist();
				popDistict();
				popCitylist();

			}
			List<BeneficaryRelationship> relationList = iPersonalRemittanceService.isBenificaryRelationExist(datatabledetails.getBenificaryMasterId(), getBeneficaryAccountSeqId());


			if(masterList.get(0).getNationality()!=null){
				setNationalityName(new BigDecimal(masterList.get(0).getNationality()));
			}
			if (relationList != null && !relationList.isEmpty()) {
				setBeneficaryRelationshipId(relationList.get(0).getBeneficaryRelationshipId());
				if (relationList.get(0).getRelations().getRelationsId() != null && Constants.SELF.equals(irelation.getEngRelation(relationList.get(0).getRelations().getRelationsId()))) {
					setBeneficaryTypeId(new BigDecimal(1));
					log.info("from Edit ---- SELF");
					setDisableDataOfBirth(true);
				} else {
					setBeneficaryTypeId(new BigDecimal(2));
					setDisableDataOfBirth(false);
					log.info("from Edit ---- others");
				}
				setRelationId(relationList.get(0).getRelations().getRelationsId());
				setCustomerNo(relationList.get(0).getCustomerId().getCustomerId());
				setRelationCreatedBy(relationList.get(0).getCreatedBy());
				setRelationCreatedDate(relationList.get(0).getCreatedDate());
			}
		}
		else
		{
			throw new Exception("BENEFICARY_STATUS is missing for this beneficary");
		}
		lstDataTableBankbranch.clear();

		log.info("Exit into edit method ");
		
		 * ExternalContext context =
		 * FacesContext.getCurrentInstance().getExternalContext();
		 
		
		 * try { context.redirect("../beneficary/beneedit.xhtml"); } catch
		 * (Exception e) { System.out.println("Exception occured" + e); }
		 
	}*/



	/*public void editBeneficarayDifferentService(PersonalRemmitanceBeneficaryDataTable datatabledetails) throws Exception {
		clear();
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		// cal1.add(Calendar.YEAR, -Constants.AGE_LIMIT);
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);
		relationDescList = getiPersonalRemittanceService().getRelationsDescriptionList(sessionmanage.getLanguageId());
		
		 * HttpSession session = sessionStateManage.getSession();
		 * session.setAttribute("masterSeqId",
		 * datatabledetails.getBenificaryMasterId());
		 
		log.info("Entering into edit method ");
		log.info(datatabledetails);
		log.info(datatabledetails.getBenificaryMasterId());
		setBooRenderIndBenificaryStatusPanel(true);
		setBooRenderBenificarySearchPanel(false);
		List<BeneficaryMaster> masterList = iPersonalRemittanceService.getAllMasterValues(datatabledetails.getBenificaryMasterId());
		if (masterList != null && !masterList.isEmpty()) {
			setBeneMatsterSeqId(datatabledetails.getBenificaryMasterId());
			setBeniStatusName(masterList.get(0).getBeneficaryStatusName());
			setBenificaryStatusName(benificaryStatusName);
			if(masterList.get(0).getBeneficaryStatus()!=null){
				setBeneficaryStatusId(masterList.get(0).getBeneficaryStatus().getBeneficaryStatusId());
			}
			setFirstName(masterList.get(0).getFirstName());
			setSecondName(masterList.get(0).getSecondName());
			setThirdName(masterList.get(0).getThirdName());
			setFourthName(masterList.get(0).getFourthName());
			setFifthName(masterList.get(0).getFifthName());
			 

			if(masterList.get(0).getFirstName()!=null)
			{
				setFirstName(masterList.get(0).getFirstName());
			}

			if(masterList.get(0).getSecondName()!=null)
			{
				setSecondName(masterList.get(0).getSecondName());
			}

			if(masterList.get(0).getThirdName()!=null)
			{
				setThirdName(masterList.get(0).getThirdName());
			}


			if(masterList.get(0).getFourthName()!=null){
				setFourthName(masterList.get(0).getFourthName());
			}

			if(masterList.get(0).getFifthName()!=null)
			{
				setFifthName(masterList.get(0).getFifthName());
			}

			if(masterList.get(0).getLocalFirstName()!=null){
				setFirstLName(masterList.get(0).getLocalFirstName());
			}
			else
			{
				setFirstLName(null);
			}
			if(masterList.get(0).getLocalSecondName()!=null) {
				setSecondLName(masterList.get(0).getLocalSecondName());
			}
			else
			{
				setSecondLName(null);
			}
			if(masterList.get(0).getLocalThirdName()!=null) {
				setThirdLName(masterList.get(0).getLocalThirdName());
			}
			else
			{
				setThirdLName(null);
			}

			if(masterList.get(0).getLocalFourthName()!=null) {
				setFourthLName(masterList.get(0).getLocalFourthName());
			}
			else
			{
				setFourthLName(null);
			}


			setThirdLName(masterList.get(0).getLocalThirdName());
			setFourthLName(masterList.get(0).getLocalFourthName());
			if(masterList.get(0).getFsCountryMaster() != null){
				setBeneCountryid(masterList.get(0).getFsCountryMaster().getCountryId());
			}
			if(masterList.get(0).getFsStateMaster() != null){
				setBenifisStateId(masterList.get(0).getFsStateMaster().getStateId());
			}
			if(masterList.get(0).getFsDistrictMaster() != null){
				setDistictId(masterList.get(0).getFsDistrictMaster().getDistrictId());
			}
			if(masterList.get(0).getFsCityMaster() != null){
				setCityId(masterList.get(0).getFsCityMaster().getCityId());
			}
			setOccupation(masterList.get(0).getOccupation());
			setMasterCreatedBy(masterList.get(0).getCreatedBy());
			setMasterCreatedDate(masterList.get(0).getCreatedDate());
			setNoOfRemittance(masterList.get(0).getNoOfRemittance());
			setApplicationCountryId(masterList.get(0).getApplicationCountryId().getCountryId());
			setIsActive(masterList.get(0).getIsActive());
			//	lstBank = generalService.getBankList(masterList.get(0).getFsCountryMaster().getCountryId());
			//	beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(masterList.get(0).getFsCountryMaster().getCountryId());
			setDistrictName(masterList.get(0).getDistrictName());
			setCityName(masterList.get(0).getCityName());
			setStateName(masterList.get(0).getStateName());

			List<BeneficaryRelationship> relationList = iPersonalRemittanceService.isBenificaryRelationExist(datatabledetails.getBenificaryMasterId(), getBeneficaryAccountSeqId());

			if(relationList!=null && !relationList.isEmpty())
			{
				setRelationId(relationList.get(0).getRelations().getRelationsId());
			}

			List<BeneficaryContact> contactDetails = iPersonalRemittanceService.getTelephoneDetails(datatabledetails.getBenificaryMasterId());
			if (contactDetails != null && !contactDetails.isEmpty()) {

				List<CountryMasterDesc> countryList =beneficaryCreation.checkCountryCode(contactDetails.get(0).getCountryTelCode());
				if(countryList!= null && !countryList.isEmpty())
				{
					if(countryList.size() >1)
					{
						throw new Exception("Setup Error - Duplicate Tel Country Code exist "+ contactDetails.get(0).getCountryTelCode() );
					}
				}
				setCountryCode(contactDetails.get(0).getCountryTelCode());
				setMcountryCode(contactDetails.get(0).getCountryTelCode());
				setBeneficaryTelephoneSeqId(contactDetails.get(0).getBeneficaryTelephoneSeqId());
				if (contactDetails.get(0).getTelephoneNumber() != null) {
					setTelephoneNumber(contactDetails.get(0).getTelephoneNumber());
				} else {
					setTelephoneNumber(null);
				}
				setMobileNumber(contactDetails.get(0).getMobileNumber());
				setYearOfBrith(masterList.get(0).getYearOfBrith());
				setDateOfBrith(masterList.get(0).getDateOfBrith());
				setContactCreatedBy(contactDetails.get(0).getCreatedBy());
				setContactCreatedDate(contactDetails.get(0).getCreatedDate());
				setAge(masterList.get(0).getAge());
			}
			setBeneficaryAccountSeqId(null);
			if(relationList!=null && !relationList.isEmpty())
			{
				setRelationId(relationList.get(0).getRelations().getRelationsId());
			}
			if (relationList != null && !relationList.isEmpty()) {
				if (relationList.get(0).getRelations().getRelationsId() != null && Constants.SELF.equals(irelation.getEngRelation(relationList.get(0).getRelations().getRelationsId()))) {
					setBeneficaryTypeId(new BigDecimal(1));
				} else {
					setBeneficaryTypeId(new BigDecimal(2));
				}
			}


			String type = "";


			List<BeneficaryAccount> accountList = null;
			if (datatabledetails.getAccountNo() != null) {
			//	accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);

				accountList =beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);


			} else {
				accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBenificaryMasterId(), type);
			}
			if (accountList != null && !accountList.isEmpty()) {
				setServiceGroupId(accountList.get(0).getServicegropupId().getServiceGroupId());
				serviceGroupIdforCash = iPersonalRemittanceService.getserviceGroupforCash(sessionmanage.getLanguageId());
				if (serviceGroupIdforCash != null && serviceGroupIdforCash.equals(getServiceGroupId())) {
					type = "cash";
					List<BeneficaryAccount> accountListwithServiceProvider = null;
					if (datatabledetails.getAccountNo() == null) {
						accountListwithServiceProvider = beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);
						lstBank.clear();
						List<BankApplicability> FbankList = bankApplicabilityService.getApplicabilityBankList(sessionmanage.getCountryId(), Constants.BANK_INDICATOR_AGENT_BANK);
						for (BankApplicability bankApplicability : FbankList) {
							lstBank.add(bankApplicability.getBankMaster());
						}
					} else {
						accountListwithServiceProvider = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBenificaryMasterId(), type);
					}
					if (accountListwithServiceProvider != null && !accountListwithServiceProvider.isEmpty()) {
						setServiceTypeId(accountListwithServiceProvider.get(0).getServiceProvider().getBankId());
						setBooenableAgentPanel(false);
					}
				} else {
					setBooenableAgentPanel(true);
				}
				serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());
				setBenifisCountryId(accountList.get(0).getBeneficaryCountry().getCountryId());

				setBenifisBankId(accountList.get(0).getBank().getBankId());
				popbranchlist();
				popCurrencylist();
				setBenifisCurrencyId(accountList.get(0).getCurrencyId().getCurrencyId());
				setServicebankBranchId(accountList.get(0).getBankBranch().getBankBranchId());
				setBeneSwiftBank1(accountList.get(0).getSwiftId());
				setBankAccountNumber(accountList.get(0).getBankAccountNumber());
				if(accountList.get(0).getBankAccountTypeId()!=null) {
				setBankAccountType(accountList.get(0).getBankAccountTypeId());
				}
				if(accountList.get(0).getSwiftId()!=null)
				{
					setBeneSwiftBank1(accountList.get(0).getSwiftId());
				}
				setTempAccountNumber(accountList.get(0).getBankAccountNumber());
				setAliasFirstName(accountList.get(0).getAliasFirstName());
				setAliasSecondName(accountList.get(0).getAliasSecondName());
				setAliasThirdName(accountList.get(0).getAliasThirdName());
				setAliasFourthName(accountList.get(0).getAliasFourthName());
				setBankCode(accountList.get(0).getBankCode());
				setAccountCreatedBy(accountList.get(0).getCreatedBy());
				setAccountCreatedDate(accountList.get(0).getCreatedDate());
				setBankBranchCode(accountList.get(0).getBankBranchCode());
				setBeneficaryAccountSeqId(accountList.get(0).getBeneficaryAccountSeqId());
				setBankCode(accountList.get(0).getBankCode());
				fetchingAllSwiftMaster();
				popStatelist();
				popDistict();
				popCitylist();

		}


	}*/

	public void selectBranchfromViewWindow(BranchDataTable branchDataTable) {
		System.out.println("Entering into selectBranchfromViewWindow method");
		System.out.println(branchDataTable);
		setServicebankBranchId(branchDataTable.getBankBranchId());
		if (branchDataTable.getStateName() != null) {
			//setBenifisStateId(branchDataTable.getStateId());
			setBeneficaryBankState(branchDataTable.getStateName());
		}
		if (branchDataTable.getDistrictName() != null) {
			//setDistictId(branchDataTable.getDistictId());
			setBeneficaryBankDistrict(branchDataTable.getDistrictName());
		}


		if (branchDataTable.getCityName() != null) {
			//setCityId(branchDataTable.getCityId());
			setBeneficaryBankCity(branchDataTable.getCityName());
		}

		try {
			//popDistict();
			//popCitylist();

			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Exit into selectBranchfromViewWindow method");
	}

	public List<AccountTypeFromView> getLstBankAccountTypeFromView() {
		return lstBankAccountTypeFromView;
	}

	public void setLstBankAccountTypeFromView(List<AccountTypeFromView> lstBankAccountTypeFromView) {
		this.lstBankAccountTypeFromView = lstBankAccountTypeFromView;
	}

	// calling view VW_EX_BANKS for banks based on appcountry,benecountry,servicegroup
	public void fetchAllBeneServiceProvider(){
		lstBankFromView.clear();

		List<ViewRoutingAgents> lstSerProvBanks = iroutingSetUpDetailsService.fetchAllRoutingAgents(sessionmanage.getCountryId(), getBenifisCountryId(), getServiceGroupId(), null, null);

		if(lstSerProvBanks.size() != 0){

			List<PopulateDataWithCode> lstSpBank = new ArrayList<PopulateDataWithCode>();
			List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

			for (ViewRoutingAgents viewRoutingAgents : lstSerProvBanks) {

				if(!duplicateCheck.contains(viewRoutingAgents.getRoutingBankId())){
					duplicateCheck.add(viewRoutingAgents.getRoutingBankId());

					PopulateDataWithCode lstBank = new PopulateDataWithCode();

					lstBank.setId(viewRoutingAgents.getRoutingBankId());
					lstBank.setCode(viewRoutingAgents.getRoutingBankCode());
					lstBank.setName(viewRoutingAgents.getRoutingBankName());

					lstSpBank.add(lstBank);
				}

			}

			setServiceProviderAndCorrespondingBankslst(lstSpBank);

		}else{
			setServiceProviderAndCorrespondingBankslst(null);
		}

		//ArrayList<String> bankIndList = new ArrayList<String>();
		//bankIndList.add("SB"); // Service Provider
		//bankIndList.add("CB"); //CORRESPONDING BANK

		//List<BanksView> lstBanksView = beneficaryCreation.getBankListFromView(sessionmanage.getCountryId(), getBenifisCountryId(), getServiceGroupId(),bankIndList);
		/*if(lstBanksView.size() != 0){
			lstBankFromView.addAll(lstBanksView);
		}*/

		benServiceCurrencyList();
	}


	public void fetchAllBeneServiceBanks(){
		lstBankFromView.clear();

		List<BanksView> lstBanksView = beneficaryCreation.getBankListFromView(sessionmanage.getCountryId(), getBenifisCountryId(), getServiceGroupId());
		if(lstBanksView.size() != 0){
			lstBankFromView.addAll(lstBanksView);
		}

		benServiceCurrencyList();
	}



	// checking cash - service provider bank to know service provide SP or Banks 
	public void checkingServiceProvidersBanks(){
		setBooRenderAgentLocationPanel(false);
		if(lstBankFromView.size() != 0){
			for (BanksView lstbankrecord : lstBankFromView) {
				if (lstbankrecord.getBankId().compareTo(getBenifisBankId())==0) {
					if(lstbankrecord.getBankInd().equalsIgnoreCase(Constants.BANK_INDICATOR_SERVICEPRO_BANK)){
						setBooRenderAgentLocationPanel(true);
						break;
					}
				}
			}
		}
		if(getBooRenderAgentLocationPanel()){
			//enableServiceProvider();
			lstDataTableBankbranch.clear();
			setDisableDependService(false);
			setBooenableAgentPanel(false);
		}else{
			setDisableDependService(true);
			setBooenableAgentPanel(true);
			popbranchlist();
		}
	}



	private List<BanksView> lstBankFromView = new ArrayList<BanksView>();
	private Boolean booRenderAgentLocationPanel = false;
	private Boolean mandatoryOptional = true;
	private Boolean disableSaveBack = false;

	public List<BanksView> getLstBankFromView() {
		return lstBankFromView;
	}
	public void setLstBankFromView(List<BanksView> lstBankFromView) {
		this.lstBankFromView = lstBankFromView;
	}

	public Boolean getBooRenderAgentLocationPanel() {
		return booRenderAgentLocationPanel;
	}
	public void setBooRenderAgentLocationPanel(Boolean booRenderAgentLocationPanel) {
		this.booRenderAgentLocationPanel = booRenderAgentLocationPanel;
	}

	public Boolean getMandatoryOptional() {
		return mandatoryOptional;
	}
	public void setMandatoryOptional(Boolean mandatoryOptional) {
		this.mandatoryOptional = mandatoryOptional;
	}

	public Boolean getDisableSaveBack() {
		return disableSaveBack;
	}
	public void setDisableSaveBack(Boolean disableSaveBack) {
		this.disableSaveBack = disableSaveBack;
	}

	public void fetchAllBeneficaryLocation(){
		setBenifisStateId(null);
		setDistictId(null);
		setCityId(null);
		if(getBeneCountryid() != null){
			checkingmandatoryOptional();
			// setting beneficary state , district and city
			popStatelist();
		}
	}

	// checking mandatory Optional - state and district
	public void checkingmandatoryOptional(){
		setMandatoryOptional(true);
		if(beneCountryList.size() != 0){
			for (CountryMasterDesc lstbenecountrydata : beneCountryList) {
				if(lstbenecountrydata.getFsCountryMaster().getCountryId().compareTo(getBeneCountryid())==0){
					if(lstbenecountrydata.getFsCountryMaster().getCountryCode().equalsIgnoreCase(Constants.IND_CODE)){
						setMandatoryOptional(false);
						break;
					}
				}
			}
		}
	}

	/*public void gotoRemittanceservice() {
		try {
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(null);
			setDisableSaveBack(false);
			PersonalRemmitanceBeneficaryDataTable dataTable = new PersonalRemmitanceBeneficaryDataTable();
			//	dataTable.setBenificaryAccDetailsId(getBeneficaryAccountSeqId());
			dataTable.setBenificaryCountryId(getBenifisCountryId());
			CountryMasterDesc beneCountry = new CountryMasterDesc();
			for (CountryMasterDesc country : nationalityList) {
				if (country.getFsCountryMaster().getCountryId().equals(getBenifisCountryId())) {
					beneCountry = country;
					break;
				}
			}
			dataTable.setBenificaryCountryName(beneCountry.getCountryName());
			dataTable.setBenificaryName(getFirstName() + " " + getSecondName() + " " + nullCheck(getThirdName()) + " " + nullCheck(getFourthName()) + " " + nullCheck(getFifthName()));
			dataTable.setAccountNo(getBankAccountNumber());
			if(getBeneficarymasterId()!=null) {
				dataTable.setBenificaryMasterId(getBeneficarymasterId());
			}
			else
			{
				dataTable.setBenificaryMasterId(getBeneMatsterSeqId());
			}

			BanksView bankMaster = new BanksView();
			for (BanksView tempbankMaster : lstBankFromView) {
				if (tempbankMaster.getBankId().equals(getBenifisBankId())) {
					bankMaster = tempbankMaster;
					break;
				}
			}

			dataTable.setBankNameId(getBenifisBankId());
			dataTable.setBankName(bankMaster.getBankFullName());
			dataTable.setServiceName(getServiceDescription());
			for (ServiceGroupMasterDesc serviceDesc : serviceGroupMasterDescList) {
				if (serviceDesc.getServiceGroupMasterId().getServiceGroupId().equals(getServiceGroupId())) {
					dataTable.setServiceName(serviceDesc.getServiceGroupDesc());
					break;
				}
			}
			dataTable.setServiceNameId(getServiceGroupId());
			dataTable.setBranchName(beneficaryCreation.getBankBranchName(getBenifisBankId(), getServicebankBranchId(), getBenifisCountryId()));
			dataTable.setBranchNameId(getServicebankBranchId());
			if (getTelephoneNumber() != null) {
				dataTable.setTelphoneNum(getTelephoneNumber());
			} else {
				if(getMobileNumber()!=null){
					dataTable.setTelphoneNum(getMobileNumber().toString());
				}
			}

			BigDecimal beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(dataTable.getBenificaryMasterId(),dataTable.getBankNameId(),dataTable.getBranchNameId(),dataTable.getAccountNo());

			dataTable.setBeneficiaryAccountSeqId(beneAccountSeqid);

			CountryMasterDesc nationality = new CountryMasterDesc();
			for (CountryMasterDesc country : nationalityList) {
				if (country.getFsCountryMaster().getCountryId().equals(getNationalityName())) {
					nationality = country;
					break;
				}
			}
			dataTable.setLocation(nationality.getCountryName());
			dataTable.setCurrencyId(getBenifisCurrencyId());
			for (ViewBeneServiceCurrency benecur : beneServiceCurrencyList) {
				if (benecur.getCurrencyId().equals(getBenifisCurrencyId())) {
					dataTable.setCurrencyName(benecur.getCurrencyName());
					break;
				}
			}
			for (RelationsDescription relationsDescription : relationDescList) {
				if (relationsDescription.getRelations().getRelationsId().equals(getRelationId())) {
					dataTable.setRelationName(relationsDescription.getLocalRelationsDescription());
					break;
				}
			}
			System.out.println("***************************************************************************");
			System.out.println(dataTable);
			System.out.println("***************************************************************************");
			log.info("Page redirect to bankacccountdetails page");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficarySave", "yes");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dataTable", dataTable);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}*/

	public void gotoRemittanceservice() {
		try {
			Boolean editBene = false;
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(null);
			setDisableSaveBack(false);
			PersonalRemmitanceBeneficaryDataTable personalRBDataTable = null;

			if(getBeneficarymasterId() == null && getBeneMatsterSeqId() != null) {
				setBeneficarymasterId(getBeneMatsterSeqId());
			}

			BigDecimal beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getBenifisBankId(),getServicebankBranchId(),getBankAccountNumber(),getBenifisCurrencyId());

			List<BenificiaryListView> isCoustomerExist = beneficaryCreation.fetchBeneficiaryNewRecord(getCustomerNo(), getBeneficarymasterId(), beneAccountSeqid,editBene,getBenifisCurrencyId(),getServiceGroupId());

			if(isCoustomerExist.size() != 0){

				BenificiaryListView rel = isCoustomerExist.get(0);

				personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();

				personalRBDataTable.setAccountStatus(rel.getAccountStatus());
				personalRBDataTable.setAge(rel.getAge());
				personalRBDataTable.setApplicationCountryId(rel.getApplicationCountryId());
				personalRBDataTable.setArbenificaryName(rel.getArbenificaryName());
				personalRBDataTable.setBankAccountNumber(rel.getBankAccountNumber());
				personalRBDataTable.setBankBranchName(rel.getBankBranchName());
				personalRBDataTable.setBankCode(rel.getBankCode());
				personalRBDataTable.setBankId(rel.getBankId());
				personalRBDataTable.setBankName(rel.getBankName());
				personalRBDataTable.setBeneficaryMasterSeqId(rel.getBeneficaryMasterSeqId());
				personalRBDataTable.setBeneficiaryAccountSeqId(rel.getBeneficiaryAccountSeqId());
				personalRBDataTable.setBeneficiaryRelationShipSeqId(rel.getBeneficiaryRelationShipSeqId());
				personalRBDataTable.setBenificaryCountry(rel.getCountryId());
				personalRBDataTable.setBenificaryCountryName(rel.getCountryName());
				personalRBDataTable.setBenificaryName(rel.getBenificaryName());
				personalRBDataTable.setBenificaryStatusId(rel.getBenificaryStatusId());
				personalRBDataTable.setBenificaryStatusName(rel.getBenificaryStatusName());
				personalRBDataTable.setBooDisabled(rel.getBankAccountNumber()!=null ? true: false);
				personalRBDataTable.setBranchCode(rel.getBranchCode());
				personalRBDataTable.setBranchId(rel.getBranchId());
				personalRBDataTable.setCityName(rel.getCityName());
				personalRBDataTable.setCountryId(rel.getBenificaryCountry());
				personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName());
				personalRBDataTable.setCreatedBy(rel.getCreatedBy());
				personalRBDataTable.setCreatedDate(rel.getCreatedDate());
				personalRBDataTable.setCurrencyId(rel.getCurrencyId());
				personalRBDataTable.setCurrencyName(rel.getCurrencyName());
				personalRBDataTable.setCurrencyQuoteName(rel.getCurrencyQuoteName()==null?"":rel.getCurrencyQuoteName());
				personalRBDataTable.setCustomerId(rel.getCustomerId());
				personalRBDataTable.setDateOfBirth(rel.getDateOfBirth());
				personalRBDataTable.setDistrictName(rel.getDistrictName());
				personalRBDataTable.setFiftheName(rel.getFiftheName());
				personalRBDataTable.setFifthNameLocal(rel.getFifthNameLocal());
				personalRBDataTable.setFirstName(rel.getFirstName());
				personalRBDataTable.setFirstNameLocal(rel.getFirstNameLocal());
				personalRBDataTable.setFourthName(rel.getFourthName());
				personalRBDataTable.setFourthNameLocal(rel.getFourthNameLocal());
				personalRBDataTable.setIsActive(rel.getIsActive());
				personalRBDataTable.setLocation(rel.getNationalityName());
				personalRBDataTable.setModifiedBy(rel.getModifiedBy());
				personalRBDataTable.setModifiedDate(rel.getModifiedDate());
				personalRBDataTable.setNationality(rel.getNationality());
				personalRBDataTable.setNationalityName(rel.getNationalityName());
				personalRBDataTable.setNoOfRemittance(rel.getNoOfRemittance());
				personalRBDataTable.setOccupation(rel.getOccupation());
				personalRBDataTable.setRelationShipId(rel.getRelationShipId());
				personalRBDataTable.setRelationShipName(rel.getRelationShipName());
				personalRBDataTable.setRemarks(rel.getRemarks());
				personalRBDataTable.setSecondNameLocal(rel.getSecondNameLocal());
				personalRBDataTable.setSecondName(rel.getSecondName());
				personalRBDataTable.setServiceGroupCode(rel.getServiceGroupCode());
				personalRBDataTable.setServiceGroupId(rel.getServiceGroupId());
				personalRBDataTable.setServiceProvider(rel.getServiceProvider());
				personalRBDataTable.setStateName(rel.getStateName());
				personalRBDataTable.setMapSequenceId(rel.getMapSequenceId());
				
				List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"),rel.getServiceGroupId());
				if (lstServiceGroupMasterDesc.size() > 0) {
					personalRBDataTable.setServiceGroupName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
				}
				
				String telePhone = beneficaryCreation.getTelePhoneNumberString(rel.getBeneficaryMasterSeqId());
				if (telePhone != null) {
					personalRBDataTable.setTelphoneNum(telePhone);
				}
				
				personalRBDataTable.setThirdName(rel.getThirdName());
				personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
				personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());

				System.out.println("***************************************************************************");
				System.out.println(personalRBDataTable);
				System.out.println("***************************************************************************");
				log.info("Page redirect to bankacccountdetails page");

			}
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficarySave", "yes");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dataTable", personalRBDataTable);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");


		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}

	}
	
	public void gotoRemittanceserviceFromEditAccService() {
		try {
			Boolean editBene = true;
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(null);
			setDisableSaveBack(false);
			PersonalRemmitanceBeneficaryDataTable personalRBDataTable = null;

			if(getBeneficarymasterId() == null && getBeneMatsterSeqId() != null) {
				setBeneficarymasterId(getBeneMatsterSeqId());
			}

			BigDecimal beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getBenifisBankId(),getServicebankBranchId(),getBankAccountNumber(),getBenifisCurrencyId());

			List<BenificiaryListView> isCoustomerExist = beneficaryCreation.fetchBeneficiaryNewRecord(getCustomerNo(), getBeneficarymasterId(), beneAccountSeqid, editBene,getBenifisCurrencyId(),getServiceGroupId());

			if(isCoustomerExist.size() != 0){

				BenificiaryListView rel = isCoustomerExist.get(0);

				personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();

				personalRBDataTable.setAccountStatus(rel.getAccountStatus());
				personalRBDataTable.setAge(rel.getAge());
				personalRBDataTable.setApplicationCountryId(rel.getApplicationCountryId());
				personalRBDataTable.setArbenificaryName(rel.getArbenificaryName());
				personalRBDataTable.setBankAccountNumber(rel.getBankAccountNumber());
				personalRBDataTable.setBankBranchName(rel.getBankBranchName());
				personalRBDataTable.setBankCode(rel.getBankCode());
				personalRBDataTable.setBankId(rel.getBankId());
				personalRBDataTable.setBankName(rel.getBankName());
				personalRBDataTable.setBeneficaryMasterSeqId(rel.getBeneficaryMasterSeqId());
				personalRBDataTable.setBeneficiaryAccountSeqId(rel.getBeneficiaryAccountSeqId());
				personalRBDataTable.setBeneficiaryRelationShipSeqId(rel.getBeneficiaryRelationShipSeqId());
				personalRBDataTable.setBenificaryCountry(rel.getCountryId());
				personalRBDataTable.setBenificaryCountryName(rel.getCountryName());
				personalRBDataTable.setBenificaryName(rel.getBenificaryName());
				personalRBDataTable.setBenificaryStatusId(rel.getBenificaryStatusId());
				personalRBDataTable.setBenificaryStatusName(rel.getBenificaryStatusName());
				personalRBDataTable.setBooDisabled(rel.getBankAccountNumber()!=null ? true: false);
				personalRBDataTable.setBranchCode(rel.getBranchCode());
				personalRBDataTable.setBranchId(rel.getBranchId());
				personalRBDataTable.setCityName(rel.getCityName());
				personalRBDataTable.setCountryId(rel.getBenificaryCountry());
				personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName());
				personalRBDataTable.setCreatedBy(rel.getCreatedBy());
				personalRBDataTable.setCreatedDate(rel.getCreatedDate());
				personalRBDataTable.setCurrencyId(rel.getCurrencyId());
				personalRBDataTable.setCurrencyName(rel.getCurrencyName());
				personalRBDataTable.setCurrencyQuoteName(rel.getCurrencyQuoteName()==null?"":rel.getCurrencyQuoteName());
				personalRBDataTable.setCustomerId(rel.getCustomerId());
				personalRBDataTable.setDateOfBirth(rel.getDateOfBirth());
				personalRBDataTable.setDistrictName(rel.getDistrictName());
				personalRBDataTable.setFiftheName(rel.getFiftheName());
				personalRBDataTable.setFifthNameLocal(rel.getFifthNameLocal());
				personalRBDataTable.setFirstName(rel.getFirstName());
				personalRBDataTable.setFirstNameLocal(rel.getFirstNameLocal());
				personalRBDataTable.setFourthName(rel.getFourthName());
				personalRBDataTable.setFourthNameLocal(rel.getFourthNameLocal());
				personalRBDataTable.setIsActive(rel.getIsActive());
				personalRBDataTable.setLocation(rel.getNationalityName());
				personalRBDataTable.setModifiedBy(rel.getModifiedBy());
				personalRBDataTable.setModifiedDate(rel.getModifiedDate());
				personalRBDataTable.setNationality(rel.getNationality());
				personalRBDataTable.setNationalityName(rel.getNationalityName());
				personalRBDataTable.setNoOfRemittance(rel.getNoOfRemittance());
				personalRBDataTable.setOccupation(rel.getOccupation());
				personalRBDataTable.setRelationShipId(rel.getRelationShipId());
				personalRBDataTable.setRelationShipName(rel.getRelationShipName());
				personalRBDataTable.setRemarks(rel.getRemarks());
				personalRBDataTable.setSecondNameLocal(rel.getSecondNameLocal());
				personalRBDataTable.setSecondName(rel.getSecondName());
				personalRBDataTable.setServiceGroupCode(rel.getServiceGroupCode());
				personalRBDataTable.setServiceGroupId(rel.getServiceGroupId());
				personalRBDataTable.setServiceProvider(rel.getServiceProvider());
				personalRBDataTable.setStateName(rel.getStateName());
				personalRBDataTable.setMapSequenceId(rel.getMapSequenceId());
				
				List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"),rel.getServiceGroupId());
				if (lstServiceGroupMasterDesc.size() > 0) {
					personalRBDataTable.setServiceGroupName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
				}
				
				String telePhone = beneficaryCreation.getTelePhoneNumberString(rel.getBeneficaryMasterSeqId());
				if (telePhone != null) {
					personalRBDataTable.setTelphoneNum(telePhone);
				}
				
				personalRBDataTable.setThirdName(rel.getThirdName());
				personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
				personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());

				System.out.println("***************************************************************************");
				System.out.println(personalRBDataTable);
				System.out.println("***************************************************************************");
				log.info("Page redirect to bankacccountdetails page");

			}
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficarySave", "yes");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dataTable", personalRBDataTable);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");


		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}

	}

	private String bankSpbranches;
	private boolean booRenderAgentMaster = true;

	public String getBankSpbranches() {
		return bankSpbranches;
	}
	public void setBankSpbranches(String bankSpbranches) {
		this.bankSpbranches = bankSpbranches;
	}

	public boolean isBooRenderAgentMaster() {
		return booRenderAgentMaster;
	}
	public void setBooRenderAgentMaster(boolean booRenderAgentMaster) {
		this.booRenderAgentMaster = booRenderAgentMaster;
	}

	public void editBeneficaryFirstTime() throws Exception {
		nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
		beneCountryList = generalService.getCountryList(sessionmanage.getLanguageId());
		lstCountry = beneCountryList;
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("datatabledetails") != null) {
			PersonalRemmitanceBeneficaryDataTable datatabledetails = (PersonalRemmitanceBeneficaryDataTable) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("datatabledetails");
			try {
				editBeneficarayPageFirstTime(datatabledetails);
			} catch (Exception e) {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				try {
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("error", e.getMessage());
					context.redirect("../remittance/PersonalRemittance.xhtml");
				} catch (Exception e1) {
					System.out.println("Exception occured" + e1);
				}
			}
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("datatabledetails");
		}
	}
	
	public void editBeneficarayPageFirstTime(PersonalRemmitanceBeneficaryDataTable datatabledetails)  throws Exception{

		log.info("Entering into edit method ");
		log.info(datatabledetails);
		log.info(datatabledetails.getBeneficaryMasterSeqId());

		// clearing first and second panels
		clear();

		setBeneMatsterSeqId(datatabledetails.getBeneficaryMasterSeqId());

		// rendering booleans
		setBooRenderIndBenificaryStatusPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooMoreTelephoneNumbers(false);
		setBooMoremobileNumbers(false);
		// beneficiary type
		if (datatabledetails.getRelationShipId() != null || datatabledetails.getRelationShipName() != null) {
			setBeneficaryRelationshipId(datatabledetails.getBeneficiaryRelationShipSeqId());
			if (datatabledetails.getRelationShipName()==null ? Constants.SELF.equalsIgnoreCase(irelation.getEngRelation(datatabledetails.getRelationShipId())) : Constants.SELF.equalsIgnoreCase(datatabledetails.getRelationShipName())) {
				setBeneficaryTypeId(new BigDecimal(1));
			} else {
				setBeneficaryTypeId(new BigDecimal(2));
			}
		}

		// beneficiary English names and local names
		if(datatabledetails.getFirstName() != null)
		{
			setFirstName(datatabledetails.getFirstName());
		}
		else
		{
			setFirstName(null);
		}

		if(datatabledetails.getSecondName()  != null)
		{
			setSecondName(datatabledetails.getSecondName());
		}
		else
		{
			setSecondName(null);
		}

		if(datatabledetails.getThirdName() != null)
		{
			setThirdName(datatabledetails.getThirdName());
		}
		else
		{
			setThirdName(null);
		}

		if(datatabledetails.getFourthName() != null){
			setFourthName(datatabledetails.getFourthName());
		}
		else
		{
			setFourthName(null);
		}

		if(datatabledetails.getFiftheName() != null)
		{
			setFifthName(datatabledetails.getFiftheName());
		}
		else
		{
			setFifthName(null);
		}

		if(datatabledetails.getFirstNameLocal() != null){
			setFirstLName(datatabledetails.getFirstNameLocal());
		}
		else
		{
			setFirstLName(null);
		}

		if(datatabledetails.getSecondNameLocal() != null) {
			setSecondLName(datatabledetails.getSecondNameLocal());
		}
		else
		{
			setSecondLName(null);
		}

		if(datatabledetails.getThirdNameLocal() != null) {
			setThirdLName(datatabledetails.getThirdNameLocal());
		}
		else
		{
			setThirdLName(null);
		}

		if(datatabledetails.getFourthNameLocal() != null) {
			setFourthLName(datatabledetails.getFourthNameLocal());
		}
		else
		{
			setFourthLName(null);
		}

		/** added by Rabil on 03/12/2015 for EX_P_BANNED_NAME_CHECK*/ 
		String engName =getFirstName()+" "+ getSecondName()+" "+getThirdName();
		String localName =getFirstLName()+" "+ getSecondLName()+" "+getThirdLName();

		String proceErrorMessage = getiPersonalRemittanceService().getBannedNameCheckProcedure(sessionmanage.getCountryId(), engName,localName);

		if(proceErrorMessage!=null && proceErrorMessage.length()>0){
			throw new Exception(proceErrorMessage);
			//setErrmsg(proceErrorMessage);
			//RequestContext.getCurrentInstance().execute("csp.show();");
			//return;
		}

		// Age Calculation
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);

		// relationship list fetching
		relationDescList = getiPersonalRemittanceService().getRelationsDescriptionList(sessionmanage.getLanguageId());
		setRelationId(datatabledetails.getRelationShipId());

		// nationality 
		if(datatabledetails.getNationality() != null){
			setNationalityName(new BigDecimal(datatabledetails.getNationality()));
		}

		// beneficiary Contact details
		List<BeneficaryContact> contactDetails = beneficaryCreation.getTelephoneDetails(datatabledetails.getBeneficaryMasterSeqId());		
		if (contactDetails != null && !contactDetails.isEmpty()) {
			List<CountryMasterDesc> countryList =beneficaryCreation.checkCountryCode(contactDetails.get(0).getCountryTelCode());
			if(countryList!= null && !countryList.isEmpty())
			{
				if(countryList.size() >1)
				{
					throw new Exception("Setup Error - Duplicate Tel Country Code exist "+ contactDetails.get(0).getCountryTelCode());
				}
			}
			setCountryCode(contactDetails.get(0).getCountryTelCode());
			setMcountryCode(contactDetails.get(0).getCountryTelCode());
			setBeneficaryTelephoneSeqId(contactDetails.get(0).getBeneficaryTelephoneSeqId());
			if (contactDetails.get(0).getTelephoneNumber() != null) {
				setTelephoneNumber(contactDetails.get(0).getTelephoneNumber());
			} else {
				setTelephoneNumber(null);
			}
			if (contactDetails.get(0).getMobileNumber() != null) {
				setMobileNumber(contactDetails.get(0).getMobileNumber());
			} else {
				setMobileNumber(null);
			}
		}
		
		if (contactDetails.size() > 1)
		{
			setBooMoreTelephoneNumbers(true);
			setLstContactDetails(contactDetails);
			setBooSingleTelephoneAvailable(false);
			setBooSingleMobileNumberAvailable(false);
		}
		else
		{
			setBooSingleTelephoneAvailable(true);
		}

		setCustomerId(datatabledetails.getCustomerId());
		setCustomerNo(datatabledetails.getCustomerId());
		
		setYearOfBrith(datatabledetails.getYearOfBirth());
		setDateOfBrith(datatabledetails.getDateOfBirth());
		setContactCreatedBy(datatabledetails.getCreatedBy());
		setContactCreatedDate(datatabledetails.getCreatedDate());
		setAge(datatabledetails.getAge());
		setOccupation(datatabledetails.getOccupation());

		// bank country Id and Name
		setBenifisCountryId(datatabledetails.getCountryId());
		setCountryName(datatabledetails.getCountryName());
		setServiceGroupId(datatabledetails.getServiceGroupId());
		// beneficiary country Id and Name
		setBeneCountryid(datatabledetails.getBenificaryCountry());
		


		checkingmandatoryOptional();
		List<BeneficaryMaster> masterList = iPersonalRemittanceService.getAllMasterValues(datatabledetails.getBeneficaryMasterSeqId());
		if(masterList != null && masterList.size() != 0){
			popStatelist();
			if(datatabledetails.getStateName() != null && masterList.get(0).getFsStateMaster() != null){
				setBenifisStateId(masterList.get(0).getFsStateMaster().getStateId());
			}
			popDistict();
			if(datatabledetails.getDistrictName() != null && masterList.get(0).getFsDistrictMaster() != null){
				setDistictId(masterList.get(0).getFsDistrictMaster().getDistrictId());
			}
			popCitylist();
			if(datatabledetails.getCityName() != null && masterList.get(0).getFsCityMaster() != null){
				setCityId(masterList.get(0).getFsCityMaster().getCityId());
			}
		}

		setStateName(datatabledetails.getStateName());
		setDistrictName(datatabledetails.getDistrictName());
		setCityName(datatabledetails.getCityName());
		setMasterCreatedBy(datatabledetails.getCreatedBy());
		setMasterCreatedDate(datatabledetails.getCreatedDate());
		setNoOfRemittance(datatabledetails.getNoOfRemittance()==null?"":datatabledetails.getNoOfRemittance().toString());
		setApplicationCountryId(datatabledetails.getApplicationCountryId());
		setIsActive(datatabledetails.getIsActive());

		if(datatabledetails.getBenificaryStatusId() != null){
			setBeneficaryStatusId(datatabledetails.getBenificaryStatusId());
			setBeniStatusName(datatabledetails.getBenificaryStatusName());
			setBenificaryStatusName(benificaryStatusName);
		}
		
		setBenifisBankId(datatabledetails.getBankId());
		setBankName(datatabledetails.getBankName());
		setServicebankBranchId(datatabledetails.getBranchId());
		setBankBranchName(datatabledetails.getBankBranchName());

		String type = "";
		List<BeneficaryAccount> accountList = null;
		if (datatabledetails.getBankAccountNumber() != null) {
			accountList = beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getBankAccountNumber(), type);
		} else {
			accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBeneficaryMasterSeqId(), type);
		}
		if (accountList != null && !accountList.isEmpty()) {
			if(accountList.get(0).getSwiftCode()!=null)
			{
				setBeneSwiftCode(accountList.get(0).getSwiftCode());
			}
			if(accountList.get(0).getBankAccountTypeId()!=null) {
				setBankAccountType(accountList.get(0).getBankAccountTypeId());
			}
			serviceGroupIdforCash = iPersonalRemittanceService.getserviceGroupforCash(sessionmanage.getLanguageId());
			if (serviceGroupIdforCash != null && serviceGroupIdforCash.equals(getServiceGroupId())) {
				type = "cash";
				List<BeneficaryAccount> accountListwithServiceProvider = null;
				if (datatabledetails.getBankAccountNumber() == null) {
					accountListwithServiceProvider = beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getBankAccountNumber(), type);
					//fetchAllBeneServiceBanks();
					fetchAllBeneServiceProvider();
				} else {
					accountListwithServiceProvider = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBeneficaryMasterSeqId(), type);
				}
				if (accountListwithServiceProvider != null && !accountListwithServiceProvider.isEmpty()) {
					setServiceTypeId(accountListwithServiceProvider.get(0).getBank().getBankId());
					setBooenableAgentPanel(false);
				}
			} else {
				fetchAllBeneServiceBanks();
				popFirstbranchlist();
				setBooenableAgentPanel(true);
			}
			
			setAliasFirstName(accountList.get(0).getAliasFirstName());
			setAliasSecondName(accountList.get(0).getAliasSecondName());
			setAliasThirdName(accountList.get(0).getAliasThirdName());
			setAliasFourthName(accountList.get(0).getAliasFourthName());
		}

		serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());

		beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(datatabledetails.getCountryId());
		setBenifisCurrencyId(datatabledetails.getCurrencyId());

		try {
			
			if(lstBankAccountTypeFromView != null || !lstBankAccountTypeFromView.isEmpty()){
				lstBankAccountTypeFromView.clear();
			}
			
			lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBenifisCountryId());
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("dself.show();");
			setErrmsg("Exception occured "+ e.getMessage());
			return;
		}

		BankBranchView lstBankbranchView = beneficaryCreation.getBranchListfromView(getBenifisBankId(),getServicebankBranchId());

		if(lstBankbranchView != null){
			setBeneficaryBankState(lstBankbranchView.getStateName());
			setBeneficaryBankDistrict(lstBankbranchView.getDistrictName());
			setBeneficaryBankCity(lstBankbranchView.getCityName());
		}

		setTempAccountNumber(datatabledetails.getBankAccountNumber());
		setBankCode(datatabledetails.getBankCode());
		setBankAccountNumber(datatabledetails.getBankAccountNumber());
		setAccountCreatedBy(datatabledetails.getCreatedBy());
		setAccountCreatedDate(datatabledetails.getCreatedDate());
		setBankBranchCode(datatabledetails.getBranchCode());
		setBeneficaryAccountSeqId(datatabledetails.getBeneficiaryAccountSeqId());
		
		if(datatabledetails.getServiceProvider()!=null)
		{
			setAgentMaster(datatabledetails.getBankId());
			setAgentBranch(datatabledetails.getBranchId());
			setServiceTypeId(datatabledetails.getServiceProvider());
			setBankBranchName(datatabledetails.getBankBranchName());
		}

		lstDataTableBankbranch.clear();
		log.info("Exit into edit method ");
	}

	// original edit facility
	/*public void editBeneficarayPageFirstTime(PersonalRemmitanceBeneficaryDataTable datatabledetails)  throws Exception{

		clear();
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);
		relationDescList = getiPersonalRemittanceService().getRelationsDescriptionList(sessionmanage.getLanguageId());
		log.info("Entering into edit method ");
		log.info(datatabledetails);
		log.info(datatabledetails.getBenificaryMasterId());
		setBooRenderIndBenificaryStatusPanel(true);
		setBooRenderBenificarySearchPanel(false);
		List<BeneficaryMaster> masterList = iPersonalRemittanceService.getAllMasterValues(datatabledetails.getBenificaryMasterId());
		if (masterList != null && !masterList.isEmpty()) {
			setBeneMatsterSeqId(datatabledetails.getBenificaryMasterId());
			setBeniStatusName(masterList.get(0).getBeneficaryStatusName());
			setBenificaryStatusName(benificaryStatusName);
			if(masterList.get(0).getBeneficaryStatus()!=null){
				setBeneficaryStatusId(masterList.get(0).getBeneficaryStatus().getBeneficaryStatusId());
			}


			if(masterList.get(0).getFirstName()!=null)
			{
				setFirstName(masterList.get(0).getFirstName());
			}
			else
			{
				setFirstName(null);
			}
			if(masterList.get(0).getSecondName()!=null)
			{
				setSecondName(masterList.get(0).getSecondName());
			}
			else
			{
				setSecondName(null);
			}
			if(masterList.get(0).getThirdName()!=null)
			{
				setThirdName(masterList.get(0).getThirdName());
			}
			else
			{
				setThirdName(null);
			}
			if(masterList.get(0).getFourthName()!=null){
				setFourthName(masterList.get(0).getFourthName());
			}
			else
			{
				setFourthName(null);
			}
			if(masterList.get(0).getFifthName()!=null)
			{
				setFifthName(masterList.get(0).getFifthName());
			}
			else
			{
				setFifthName(null);
			}
			if(masterList.get(0).getLocalFirstName()!=null){
				setFirstLName(masterList.get(0).getLocalFirstName());
			}
			else
			{
				setFirstLName(null);
			}
			if(masterList.get(0).getLocalSecondName()!=null) {
				setSecondLName(masterList.get(0).getLocalSecondName());
			}
			else
			{
				setSecondLName(null);
			}
			if(masterList.get(0).getLocalThirdName()!=null) {
				setThirdLName(masterList.get(0).getLocalThirdName());
			}
			else
			{
				setThirdLName(null);
			}
			if(masterList.get(0).getLocalFourthName()!=null) {
				setFourthLName(masterList.get(0).getLocalFourthName());
			}
			else
			{
				setFourthLName(null);
			}


			*//** added by Rabil on 03/12/2015 for EX_P_BANNED_NAME_CHECK*//* 

			String engName =getFirstName()+" "+ getSecondName()+" "+getThirdName();
			String localName =getFirstLName()+" "+ getSecondLName()+" "+getThirdLName();

			String proceErrorMessage = getiPersonalRemittanceService().getBannedNameCheckProcedure(sessionmanage.getCountryId(), engName,localName);

			if(proceErrorMessage!=null && proceErrorMessage.length()>0){
				throw new Exception(proceErrorMessage);
				//setErrmsg(proceErrorMessage);
				//RequestContext.getCurrentInstance().execute("csp.show();");
				//return;
			}

			setOccupation(masterList.get(0).getOccupation());

			setBeneCountryid(masterList.get(0).getFsCountryMaster().getCountryId());
			checkingmandatoryOptional();
			if(masterList.get(0).getNationality()!=null){
				setNationalityName(new BigDecimal(masterList.get(0).getNationality()));
			}
			popStatelist();
			if(masterList.get(0).getFsStateMaster()!=null){
				setBenifisStateId(masterList.get(0).getFsStateMaster().getStateId());
			}
			popDistict();
			if(masterList.get(0).getFsDistrictMaster()!=null){
				setDistictId(masterList.get(0).getFsDistrictMaster().getDistrictId());
			}
			popCitylist();
			if(masterList.get(0).getFsCityMaster()!=null){
				setCityId(masterList.get(0).getFsCityMaster().getCityId());
			}

			setDistrictName(masterList.get(0).getDistrictName());
			setCityName(masterList.get(0).getCityName());
			setStateName(masterList.get(0).getStateName());
			setMasterCreatedBy(masterList.get(0).getCreatedBy());
			setMasterCreatedDate(masterList.get(0).getCreatedDate());
			setNoOfRemittance(masterList.get(0).getNoOfRemittance());
			setApplicationCountryId(masterList.get(0).getApplicationCountryId().getCountryId());
			setIsActive(masterList.get(0).getIsActive());


			//	lstBank = generalService.getBankList(masterList.get(0).getFsCountryMaster().getCountryId());
			//	beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(masterList.get(0).getFsCountryMaster().getCountryId());

			List<BeneficaryContact> contactDetails = iPersonalRemittanceService.getTelephoneDetails(datatabledetails.getBenificaryMasterId());
			if (contactDetails != null && !contactDetails.isEmpty()) {

				List<CountryMasterDesc> countryList =beneficaryCreation.checkCountryCode(contactDetails.get(0).getCountryTelCode());
				if(countryList!= null && !countryList.isEmpty())
				{
					if(countryList.size() >1)
					{
						throw new Exception("Setup Error - Duplicate Tel Country Code exist "+ contactDetails.get(0).getCountryTelCode() );
					}
				}
				setCountryCode(contactDetails.get(0).getCountryTelCode());
				setMcountryCode(contactDetails.get(0).getCountryTelCode());
				setBeneficaryTelephoneSeqId(contactDetails.get(0).getBeneficaryTelephoneSeqId());
				if (contactDetails.get(0).getTelephoneNumber() != null) {
					setTelephoneNumber(contactDetails.get(0).getTelephoneNumber());
				} else {
					setTelephoneNumber(null);
				}
				setMobileNumber(contactDetails.get(0).getMobileNumber());
				setYearOfBrith(masterList.get(0).getYearOfBrith());
				setDateOfBrith(masterList.get(0).getDateOfBrith());
				setContactCreatedBy(contactDetails.get(0).getCreatedBy());
				setContactCreatedDate(contactDetails.get(0).getCreatedDate());
				setAge(masterList.get(0).getAge());
			}
			String type = "";


			List<BeneficaryAccount> accountList = null;
			if (datatabledetails.getAccountNo() != null) {
				//	accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);

				accountList =beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);


			} else {
				accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBenificaryMasterId(), type);
			}
			if (accountList != null && !accountList.isEmpty()) {
				setCountryName(generalService.getCountryName(sessionmanage.getLanguageId(), accountList.get(0).getBeneficaryCountry().getCountryId()));
				setServiceGroupId(accountList.get(0).getServicegropupId().getServiceGroupId());
				serviceGroupIdforCash = iPersonalRemittanceService.getserviceGroupforCash(sessionmanage.getLanguageId());
				setBenifisCountryId(accountList.get(0).getBeneficaryCountry().getCountryId());
				if (serviceGroupIdforCash != null && serviceGroupIdforCash.equals(getServiceGroupId())) {
					type = "cash";
					List<BeneficaryAccount> accountListwithServiceProvider = null;
					if (datatabledetails.getAccountNo() == null) {
						accountListwithServiceProvider = beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBenificaryMasterId(), datatabledetails.getAccountNo(), type);
						//lstBank.clear();
						//List<BankApplicability> FbankList = bankApplicabilityService.getApplicabilityBankList(sessionmanage.getCountryId(), Constants.BANK_INDICATOR_AGENT_BANK);
						for (BankApplicability bankApplicability : FbankList) {
							lstBank.add(bankApplicability.getBankMaster());
						}
						fetchAllBeneServiceBanks();
					} else {
						accountListwithServiceProvider = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBenificaryMasterId(), type);
					}
					if (accountListwithServiceProvider != null && !accountListwithServiceProvider.isEmpty()) {
						setServiceTypeId(accountListwithServiceProvider.get(0).getBank().getBankId());
						setBooenableAgentPanel(false);
					}
				} else {
					setBooenableAgentPanel(true);
				}
				serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());


				setBenifisBankId(accountList.get(0).getBank().getBankId());

				List<BanksView> lstBanksView = beneficaryCreation.getBankListFromView(sessionmanage.getCountryId(), getBenifisCountryId(), getServiceGroupId());
				if(lstBanksView.size() != 0){
					lstBankFromView.addAll(lstBanksView);

					setBooAotherLang(true);

					for (BanksView bankMaster : lstBankFromView) {

						if (bankMaster.getBankId().compareTo(getBenifisBankId()) == 0) {
							setBankCode(bankMaster.getBankCode());

							if(!bankMaster.getLanguageInd().equals(Constants.ENGLISH_LANGUAGE_ID))
							{
								setBooAotherLang(false);
								break;

							}


						}
					}


				}



				lstBankbranchView = beneficaryCreation.getBranchListfromView(getBenifisBankId());
				setBankName(generalService.getBankName(getBenifisBankId()));
				if(accountList.get(0).getBankBranch()!=null){
					setServicebankBranchId(accountList.get(0).getBankBranch().getBankBranchId());
					BankBranch bankBranch = beneficaryCreation.getBankBranch(accountList.get(0).getBank().getBankId(),accountList.get(0).getBankBranch().getBankBranchId(),accountList.get(0).getBeneficaryCountry().getCountryId());
					if(bankBranch!=null) {
						setBankBranchName(bankBranch.getBranchFullName());
						if (bankBranch.getFsStateMaster() != null) {
							setBeneficaryBankState(generalService.getStateName(sessionmanage.getLanguageId(), getBenifisCountryId(), bankBranch.getFsStateMaster().getStateId()));
							if (bankBranch.getFsDistrictMaster() != null) {
								setBeneficaryBankDistrict(generalService.getDistrictName(sessionmanage.getLanguageId(),  getBenifisCountryId(), bankBranch.getFsStateMaster().getStateId(), bankBranch.getFsDistrictMaster().getDistrictId()));
								if (bankBranch.getFsCityMaster() != null) {
									setBeneficaryBankCity(generalService.getCityName(sessionmanage.getLanguageId(),  getBenifisCountryId(), bankBranch.getFsStateMaster().getStateId(), bankBranch.getFsDistrictMaster().getDistrictId(), bankBranch.getFsCityMaster().getCityId()));
								}
							}
						}
					}
				}



				try {
					lstBankAccountType = beneficaryCreation.getBankAccountType(sessionmanage.getLanguageId());

					lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBenifisCountryId());

				} catch (Exception e) {
					RequestContext.getCurrentInstance().execute("dself.show();");
					setErrmsg("Exception occured "+ e.getMessage());
					setBenifisBankId(null);
					return;

				}

				benServiceCurrencyList();
				popCurrencylist();
				setBenifisCurrencyId(accountList.get(0).getCurrencyId().getCurrencyId());
				//setServicebankBranchId(accountList.get(0).getBankBranch().getBankBranchId());
				setBeneSwiftCode(accountList.get(0).getSwiftCode());
				setBankAccountNumber(accountList.get(0).getBankAccountNumber());
				if(accountList.get(0).getBankAccountTypeId()!=null) {
					setBankAccountType(accountList.get(0).getBankAccountTypeId());
				}
				if(accountList.get(0).getSwiftCode()!=null)
				{
					setBeneSwiftCode(accountList.get(0).getSwiftCode());
				}
				setTempAccountNumber(accountList.get(0).getBankAccountNumber());
				setAliasFirstName(accountList.get(0).getAliasFirstName());
				setAliasSecondName(accountList.get(0).getAliasSecondName());
				setAliasThirdName(accountList.get(0).getAliasThirdName());
				setAliasFourthName(accountList.get(0).getAliasFourthName());
				setBankCode(accountList.get(0).getBankCode());
				setAccountCreatedBy(accountList.get(0).getCreatedBy());
				setAccountCreatedDate(accountList.get(0).getCreatedDate());
				setBankBranchCode(accountList.get(0).getBankBranchCode());
				setBeneficaryAccountSeqId(accountList.get(0).getBeneficaryAccountSeqId());
				setBankCode(accountList.get(0).getBankCode());

				if(accountList.get(0).getServiceProvider()!=null)
				{
					setAgentMaster(accountList.get(0).getBank().getBankId());
					setAgentBranch(accountList.get(0).getBankBranch().getBankBranchId());
					setServiceTypeId(accountList.get(0).getServiceProvider().getBankId());

					BankBranch bankBranch = beneficaryCreation.getBankBranch(accountList.get(0).getBank().getBankId(),accountList.get(0).getBankBranch().getBankBranchId(),accountList.get(0).getBeneficaryCountry().getCountryId());
					if(bankBranch!=null) {
						setBankBranchName(bankBranch.getBranchFullName());
					}



				}


				//	fetchingAllSwiftMaster();

			}
			System.out.println("datatabledetails.getBenificaryMasterId() :"+datatabledetails.getBenificaryMasterId()+"\t getBeneficaryAccountSeqId() :"+getBeneficaryAccountSeqId());
			List<BeneficaryRelationship> relationList = iPersonalRemittanceService.isBenificaryRelationExist(datatabledetails.getBenificaryMasterId(), getBeneficaryAccountSeqId());



			if (relationList != null && !relationList.isEmpty()) {
				setBeneficaryRelationshipId(relationList.get(0).getBeneficaryRelationshipId());
				if (relationList.get(0).getRelations().getRelationsId() != null && Constants.SELF.equals(irelation.getEngRelation(relationList.get(0).getRelations().getRelationsId()))) {
					setBeneficaryTypeId(new BigDecimal(1));
					log.info("from Edit ---- SELF");
					setDisableDataOfBirth(true);
				} else {
					setBeneficaryTypeId(new BigDecimal(2));
					setDisableDataOfBirth(false);
					log.info("from Edit ---- others");
				}
				setRelationId(relationList.get(0).getRelations().getRelationsId());
				setCustomerNo(relationList.get(0).getCustomerId().getCustomerId());
				setRelationCreatedBy(relationList.get(0).getCreatedBy());
				setRelationCreatedDate(relationList.get(0).getCreatedDate());
			}
		}
		else
		{
			throw new Exception("BENEFICARY_STATUS is missing for this beneficary");
		}
		lstDataTableBankbranch.clear();


		log.info("Exit into edit method ");
		
		 * ExternalContext context =
		 * FacesContext.getCurrentInstance().getExternalContext();
		 
		
		 * try { context.redirect("../beneficary/beneedit.xhtml"); } catch
		 * (Exception e) { System.out.println("Exception occured" + e); }
		 
	}*/

	public void popFirstbranchlist() {
		//	popCurrencylistBank();
		lstDataTableBankbranch.clear();

		setBooAotherLang(true);

		List<BankBranchView> lstBankbranchView = new ArrayList<BankBranchView>();

		//lstBankbranchView = beneficaryCreation.getBranchListfromView(getBenifisBankId());

		if (lstBankbranchView.size() > 0) {
			setLstBankbranchView(lstBankbranchView);
		}
	}
	
	// For Save Beneficary Master
	@SuppressWarnings("unchecked")
	public void saveBeneficaryDetails() throws ParseException {
		setDisableSaveBack(true);
		System.out.println("Beneficary Master ----------------------------- > ");
		Map<String, Object> mapTeleExist = getMapTeleExistToCheck();
		BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
		BeneficaryAccount beneficaryAccount = new BeneficaryAccount();
		if (mapTeleExist.size() > 0) {
			Boolean telePhoneExist = (Boolean) mapTeleExist.get("TeleExist");
			if (telePhoneExist.booleanValue()) {
				System.out.println("TeleExist");
				List<BeneficaryContact> lstBeneficaryTelaphone = (List<BeneficaryContact>) mapTeleExist.get("LstTeleExist");
				BeneficaryContact beneficaryTelaphone = lstBeneficaryTelaphone.get(0);
				BeneficaryMaster temp = beneficaryTelaphone.getBeneficaryMaster();
				if (temp != null) {
					System.out.println("ttttemmmmmp");
					System.out.println(temp.getFirstName());
					System.out.println(temp.getSecondName());
					System.out.println(temp.getThirdName());
					System.out.println("ttttemmmmmp end here");
					System.out.println("actual");
					System.out.println(getFirstName());
					System.out.println(getSecondName());
					System.out.println(getThirdName());
					System.out.println("actual");
					if (getFirstName() != null && getSecondName() != null && temp.getFirstName() != null && temp.getSecondName() != null && temp.getFirstName().equals(getFirstName()) && temp.getSecondName().equals(getSecondName())) {
						BigDecimal beneMatsterSeqId = beneficaryTelaphone.getBeneficaryMaster().getBeneficaryMasterSeqId();
						beneficaryMaster.setBeneficaryMasterSeqId(beneMatsterSeqId);
					}
				}
				Boolean relaShipExist = (Boolean) mapTeleExist.get("RelationExist");
				List<BeneficaryAccount> lstBeneficaryAccount = new ArrayList<BeneficaryAccount>();
				BigDecimal beneAccountSeqId = null;
				if (relaShipExist.booleanValue()) {
					Boolean accNoExist = (Boolean) mapTeleExist.get("AccountExist");
					System.out.println("RelationExist");
					if (accNoExist.booleanValue()) {
						lstBeneficaryAccount = (List<BeneficaryAccount>) mapTeleExist.get("LstAccountExist");
					}
				}
				if (lstBeneficaryAccount.size() != 0) {
					beneAccountSeqId = lstBeneficaryAccount.get(0).getBeneficaryAccountSeqId();
				}
				// BeneficaryAccount beneficaryAccount = new
				// BeneficaryAccount();
				beneficaryAccount.setBeneficaryAccountSeqId(beneAccountSeqId);
				List<BeneficaryAccount> beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExistInDb(getBankAccountNumber(), getBenifisCountryId(), getBenifisBankId(), getServicebankBranchId());
				if (beneficiaryAccountDetailList.size() == 0) {
					// BeneficaryAccount beneficaryAccountSave =
					// saveBeneficaryAccount(beneficaryMaster);
					BeneficaryContact contact = null;
					if (beneficaryMaster.getBeneficaryMasterSeqId() == null) {
						beneficaryMaster = beneficaryMasterSave();
						contact = saveBeneficaryTelephone(beneficaryMaster);
					}
					beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
					BeneficaryRelationship relationship = saveBeneficaryRelation(beneficaryMaster, beneficaryAccount);

					try {
						beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
					} catch (Exception e) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg(e.getMessage());
						return;
					}
				} else {
					BeneficaryContact contact = null;
					if (SELFRELATIONID.equals(getRelationId())) {
						beneficaryMaster = beneficaryMasterSave();
						contact = saveBeneficaryTelephone(beneficaryMaster);
					} else {
						BigDecimal masterSequenceId = beneficiaryAccountDetailList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId();
						beneficaryMaster = beneficaryMasterSave(masterSequenceId);
					}
					beneficaryAccount = beneficiaryAccountDetailList.get(0);
					beneficaryMaster.setBeneficaryMasterSeqId(beneficaryAccount.getBeneficaryMaster().getBeneficaryMasterSeqId());
					BeneficaryRelationship relationship = saveBeneficaryRelation(beneficaryMaster, beneficaryAccount);
					try {
						beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
					} catch (Exception e) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg(e.getMessage());
						return;
					}
				}
			} else {
				List<BeneficaryAccount> beneficiaryAccountDetailList = null;
				if (lstfetchCashId != null && !lstfetchCashId.isEmpty()) {
					if (getServiceGroupId() != null && !(getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0)) {
						beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExistInDb(getBankAccountNumber(), getBenifisCountryId(), getBenifisBankId(), getServicebankBranchId());
					}
				}
				if (beneficiaryAccountDetailList != null && beneficiaryAccountDetailList.size() == 0) {
					List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.SELF);
					BigDecimal selfRelationid = selfid.get(0).getRelations().getRelationsId();
					BigDecimal masterSequenceId = null;
					if (selfRelationid.equals(getRelationId())) {
						masterSequenceId = beneficaryCreation.checkMasterSequenceExist(getCustomerNo(), getRelationId());
						System.out.println("masterSequenceId " + masterSequenceId);
					}
					if (masterSequenceId != null) {
						beneficaryMaster = beneficaryMasterSave(masterSequenceId);
					} else {
						beneficaryMaster = beneficaryMasterSave();
					}
					BeneficaryContact contact = saveBeneficaryTelephone(beneficaryMaster);
					// BeneficaryAccount beneficaryAccountSave =
					// saveBeneficaryAccount(beneficaryMaster);
					beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
					BeneficaryRelationship relationship = saveBeneficaryRelation(beneficaryMaster, beneficaryAccount);
					try {
						beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
					} catch (Exception e) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg(e.getMessage());
						return;
					}
				} else if (beneficiaryAccountDetailList == null) {
					beneficaryMaster = beneficaryMasterSave();
					BeneficaryContact contact = saveBeneficaryTelephone(beneficaryMaster);
					beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
					BeneficaryRelationship relationship = saveBeneficaryRelation(beneficaryMaster, beneficaryAccount);
					try {
						beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
					} catch (Exception e) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg(e.getMessage());
						return;
					}
				} else {
					if (SELFRELATIONID.equals(getRelationId())) {
						beneficaryMaster = beneficaryMasterSave();
					} else {
						BigDecimal masterSequenceId = beneficiaryAccountDetailList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId();
						beneficaryMaster = beneficaryMasterSave(masterSequenceId);
					}
					BeneficaryContact contact = saveBeneficaryTelephone(beneficaryMaster);
					beneficaryAccount = beneficiaryAccountDetailList.get(0);
					beneficaryMaster.setBeneficaryMasterSeqId(beneficaryAccount.getBeneficaryMaster().getBeneficaryMasterSeqId());
					BeneficaryRelationship relationship = saveBeneficaryRelation(beneficaryMaster, beneficaryAccount);
					try {
						beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
					} catch (Exception e) {
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg(e.getMessage());
						return;
					}
				}
			}
		} else {
			beneficaryMaster = beneficaryMasterSave();
			BeneficaryContact contact = saveBeneficaryTelephone(beneficaryMaster);
			List<BeneficaryAccount> beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExistInDb(getBankAccountNumber(), getBenifisCountryId(), getBenifisBankId(), getServicebankBranchId());
			if (beneficiaryAccountDetailList.size() == 0) {
				beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
				BeneficaryRelationship relationship = saveBeneficaryRelation(beneficaryMaster, beneficaryAccount);
				try {
					beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
				} catch (Exception e) {
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg(e.getMessage());
					return;
				}
			} else {
				// BeneficaryAccount beneficaryAccountExist =
				// beneficiaryAccountDetailList.get(0);
				beneficaryAccount = beneficiaryAccountDetailList.get(0);
				BeneficaryRelationship relationship = saveBeneficaryRelation(beneficaryMaster, beneficaryAccount);
				try {
					beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
				} catch (Exception e) {
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg(e.getMessage());
					return;
				}
			}
		}
		setBeneficarymasterId(beneficaryMaster.getBeneficaryMasterSeqId());
		String errorMessage = beneficaryCreation.getBeneDetailProce(beneficaryMaster.getBeneficaryMasterSeqId(), getBenifisBankId(), getServicebankBranchId(), beneficaryAccount.getBeneficaryAccountSeqId(), getBenifisCurrencyId(), getCustomerNo());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
		if (errorMessage == null) {
			RequestContext.getCurrentInstance().execute("beneficarycomplete.show();");
		} else {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("EX_POPULATE_BENE_DT " + errorMessage);
		}
	}





	private Boolean booMoreTelephoneNumbers;
	List<BeneficaryContact> lstContactDetails;
	private String telephoneNumberSelect ;


	private Boolean booMoremobileNumbers;
	private BigDecimal mobileNumberSelect ;
	List<BeneficaryContact> lstMobileDetails;


	private boolean booSingleTelephoneAvailable;

	private boolean booSingleMobileNumberAvailable;

	public boolean isBooSingleTelephoneAvailable() {
		return booSingleTelephoneAvailable;
	}

	public void setBooSingleTelephoneAvailable(boolean booSingleTelephoneAvailable) {
		this.booSingleTelephoneAvailable = booSingleTelephoneAvailable;
	}

	public boolean isBooSingleMobileNumberAvailable() {
		return booSingleMobileNumberAvailable;
	}

	public void setBooSingleMobileNumberAvailable(boolean booSingleMobileNumberAvailable) {
		this.booSingleMobileNumberAvailable = booSingleMobileNumberAvailable;
	}

	public List<BeneficaryContact> getLstMobileDetails() {
		return lstMobileDetails;
	}

	public void setLstMobileDetails(List<BeneficaryContact> lstMobileDetails) {
		this.lstMobileDetails = lstMobileDetails;
	}

	public Boolean getBooMoremobileNumbers() {
		return booMoremobileNumbers;
	}

	public void setBooMoremobileNumbers(Boolean booMoremobileNumbers) {
		this.booMoremobileNumbers = booMoremobileNumbers;
	}

	public BigDecimal getMobileNumberSelect() {
		return mobileNumberSelect;
	}

	public void setMobileNumberSelect(BigDecimal mobileNumberSelect) {
		this.mobileNumberSelect = mobileNumberSelect;
	}

	public Boolean getBooMoreTelephoneNumbers() {
		return booMoreTelephoneNumbers;
	}

	public void setBooMoreTelephoneNumbers(Boolean booMoreTelephoneNumbers) {
		this.booMoreTelephoneNumbers = booMoreTelephoneNumbers;
	}

	public List<BeneficaryContact> getLstContactDetails() {
		return lstContactDetails;
	}

	public void setLstContactDetails(List<BeneficaryContact> lstContactDetails) {
		this.lstContactDetails = lstContactDetails;
	}

	public String getTelephoneNumberSelect() {
		return telephoneNumberSelect;
	}

	public void setTelephoneNumberSelect(String telephoneNumberSelect) {
		this.telephoneNumberSelect = telephoneNumberSelect;
	}


	public void putTelephoneField()
	{
		if (getTelephoneNumberSelect() != null && !getTelephoneNumberSelect().equals("")) {
			System.out.println("Selected Telephone number is " + getTelephoneNumberSelect());
			setTelephoneNumber(getTelephoneNumberSelect());
			for (BeneficaryContact contactDetails : lstContactDetails) {
				if (contactDetails.getTelephoneNumber().equals(getTelephoneNumberSelect())) {
					setCountryCode(contactDetails.getCountryTelCode());
					setMcountryCode(contactDetails.getCountryTelCode());
					setBeneficaryTelephoneSeqId(contactDetails.getBeneficaryTelephoneSeqId());
					if (contactDetails.getTelephoneNumber() != null) {
						setTelephoneNumber(contactDetails.getTelephoneNumber());
					} else {
						setTelephoneNumber(null);
					}
					if(lstMobileDetails!=null && lstMobileDetails.size() >0)
					{

					}
					else if (isBooSingleMobileNumberAvailable()) 
					{

					}
					else
					{
						setMobileNumber(contactDetails.getMobileNumber());
					}
					setContactCreatedBy(contactDetails.getCreatedBy());
					setContactCreatedDate(contactDetails.getCreatedDate());
				}
			}
		}
	}



	public void putMobilephoneField()
	{
		if (getMobileNumberSelect() != null ) {
			System.out.println("Selected Mobile number is " + getMobileNumberSelect());
			setMobileNumber(getMobileNumberSelect());
			for (BeneficaryContact contactDetails : lstMobileDetails) {
				if (contactDetails.getMobileNumber().equals(getMobileNumberSelect())) {
					setCountryCode(contactDetails.getCountryTelCode());
					setMcountryCode(contactDetails.getCountryTelCode());
					setBeneficaryTelephoneSeqId(contactDetails.getBeneficaryTelephoneSeqId());
					if(lstContactDetails!=null && lstContactDetails.size() >0) {

					}
					else
					{
						if (contactDetails.getTelephoneNumber() != null) {
							setTelephoneNumber(contactDetails.getTelephoneNumber());
						} else {
							setTelephoneNumber(null);
						}
					}


				}

			}

		}
	}

}
