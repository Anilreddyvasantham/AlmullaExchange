package com.amg.exchange.beneficiary.bean;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.model.BanksView;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
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
import com.amg.exchange.remittance.bean.PersonalRemittanceBean;
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
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
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
import com.amg.exchange.wu.bean.WUTranxFileUploadBean;
import com.amg.exchange.wu.bean.WesternUnionTransferBean;
import com.amg.exchange.wu.service.IWesternUnionService;
import com.amg.exchange.wuh2h.bean.WesternUnionHToHBean;

@Component("beneficiaryCreationBean")
@Scope("session")
public class BeneficiaryCreationBean implements Serializable {
	private static final Logger log = Logger.getLogger(BeneficiaryCreationBean.class);
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionmanage = new SessionStateManage();

	private Boolean mainPanelRender = false;
	private String idType = null;
	private String selectType = "Manual";
	private String manual = null;
	private String smartCard = null;
	private BigDecimal selectCard;

	// Beneficiary Master
	private String firstName;
	private String secondName;
	private String thirdName;
	private String fourthName;
	private String fifthName;
	private String firstLName;
	private String secondLName;
	private String thirdLName;
	private String fourthLName;
	//Added by Rabil on 17 April 2017 
	private String  fifthLName;
	//end  by Rabil on 17 April 2017 
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
	private Boolean booMoreTelephoneNumbers;
	private String telephoneNumberSelect ;
	private Boolean booMoremobileNumbers;
	private BigDecimal mobileNumberSelect ;
	private boolean booSingleTelephoneAvailable;
	private boolean booSingleMobileNumberAvailable;
	private boolean boorenderBenemaster = false;

	// Beneficiary Account
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
	private Boolean booAotherLang = false;
	private Boolean booDisableBeneficaryType;
	private Boolean booRenderAgentLocationPanel = false;
	private Boolean mandatoryOptional = true;
	private Boolean disableSaveBack = false;

	// Beneficiary Relations
	private BigDecimal beneficaryRelationshipId;
	private BigDecimal activatebeneficaryRelationshipId;

	// Beneficiary Telephone
	private String countryTelephoneNumber;
	private BigDecimal beneficaryStatusId;

	// Remittance Service
	private String benificaryName;
	//private BigDecimal beneficaryBankId;
	private String beneficaryBankName;
	//private BigDecimal beneficaryBankBranchId;
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
	private BigDecimal indbenificiaryType;
	private BigDecimal servicecurrencyId;
	private BigDecimal servicebankBranchId;
	private BigDecimal benifisCountryId;
	private String benifisCountryName;
	private BigDecimal benifisCurrencyId;
	private BigDecimal benifisBankId;
	private BigDecimal beneCountryid;
	private BigDecimal benifisStateId;
	private BigDecimal distictId;
	private BigDecimal cityId;
	private boolean disablerelation = false;
	private String countryCode;
	private String mcountryCode;
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
	//private Boolean disableDependService = false;
	private String currencyName;
	private boolean singleCurrency = false;
	private boolean multCurrency = true;
	private Boolean typeOfServicePanel = false;
	private BigDecimal customerrefno;
	private String customerFullName;
	private String customerEmail;
	private String customerLocalFullName;
	private String customerType;
	private BigDecimal customerTypeId;
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
	//private BigDecimal beneMatsterSeqId;
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
	//private String countryName;
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
	private String beneNationalaityName;
	private String beneRelationshipName;
	private BigDecimal mapSequenceId=null;
	private BigDecimal searchBankStateId;

	//western_union_purpose
	private boolean booRenderWesterUnion = false;
	private boolean booRenderBeneficaryCreation = true;
	private boolean booRenderWesterUnionUpload = false;
	// used while edit time
	private String westernunionBank;
	private String westernunionBranch;
	private boolean westernunionPanel = false;
	private boolean booEnableBankChannelPanel = false;
	private boolean beneAccEditOrCreate = false;
	private String saveOrNextButton = Constants.Next;
	private String errorMsg;
	private Boolean ageFocus = false;
	
	private Boolean disableBeneficiaryAccountExist = false;


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
	@Autowired
	IServiceGroupMasterService serviceGroupMasterService;
	@Autowired
	WesternUnionTransferBean<T> westernUnionTransferBean;
	@Autowired
	IWesternUnionService westernUnionService;
	@Autowired
	ApplicationContext appContext;
	@Autowired
	WUTranxFileUploadBean<T> wuTranxFileUploadBean;
	
	@Autowired
	WesternUnionHToHBean<T> wuh2hBean;
	

	iCypherSecurity cypherSecurity = new CypherSecurityImpl();
	private List<CurrencyMaster> currencyMasterList = new ArrayList<CurrencyMaster>();
	private List<BankAccountDetails> listCurrencyAccountDetails = new ArrayList<BankAccountDetails>();
	private List<PersonalRemmitanceBeneficaryDataTable> coustomerBeneficaryDTList = new ArrayList<PersonalRemmitanceBeneficaryDataTable>();
	private List<CustomerIdProof> customerDetailsList = new ArrayList<CustomerIdProof>();
	private Map<Integer, String> beneficaryMap = new HashMap<Integer, String>();
	private List<CurrencyMaster> listCurrency = new ArrayList<CurrencyMaster>();
	private List<BankMaster> lstBank = new ArrayList<BankMaster>();
	private List<BankBranch> lstBankbranch = new ArrayList<BankBranch>();
	private List<CountryMasterDesc> lstCountry = new ArrayList<CountryMasterDesc>();
	private List<StateMasterDesc> lststate = new ArrayList<StateMasterDesc>();
	private List<StateMasterDesc> lstBankCountrystate = new ArrayList<StateMasterDesc>();
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
	private List<BeneficaryAccount> lstBeneficaryAccount = new ArrayList<BeneficaryAccount>();
	private Map<String, Object> mapTeleExistToCheck = new HashMap<String, Object>();
	private List<PersonalRemittanceTeleExistDTBean> lstBeneficaryAccountDailog = new ArrayList<PersonalRemittanceTeleExistDTBean>();
	private List<BeneficaryAccount> lstUpdateBeneficaryAccount = new ArrayList<BeneficaryAccount>();
	private List<AccountTypeFromView> lstBankAccountTypeFromView = new ArrayList<AccountTypeFromView>();
	private List<BranchDataTable> lstDataTableBankbranch = new ArrayList<BranchDataTable>();
	private List<BranchDataTable> lstFilteredDataTableBankbranch = new ArrayList<BranchDataTable>();
	private BranchDataTable selectedDataTableBankbranch ;
	private List<BankBranchView> lstBankbranchView = new ArrayList<BankBranchView>();
	private List<PopulateDataWithCode> agentAndCorrespondingBankslst = new ArrayList<PopulateDataWithCode>();
	private List<PopulateDataWithCode> agentAndCorrespondingBankBranchlst = new ArrayList<PopulateDataWithCode>();
	private List<PopulateDataWithCode> serviceProviderAndCorrespondingBankslst = new ArrayList<PopulateDataWithCode>();
	private List<ViewRoutingAgentLocations> lstAgentLocationForCash = new ArrayList<ViewRoutingAgentLocations>();
	private List<ViewRoutingAgents> lstAgentsBanks = new ArrayList<ViewRoutingAgents>();
	private PersonalRemmitanceBeneficaryDataTable dataTableBeneObj = new PersonalRemmitanceBeneficaryDataTable();
	private List<BanksView> lstBankFromView = new ArrayList<BanksView>();

	// Bank Account Screen Details Start
	public void clearFirstTime(){
		// clearing all fields
		clear();
		// beneficiary bank country
		if(lstCountry!=null && lstCountry.isEmpty()){
			lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());			
		}
		// service group
		if(serviceGroupMasterDescList!=null && serviceGroupMasterDescList.isEmpty()){
			serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());
		}
		// new beneficiary Next 
		setSaveOrNextButton(Constants.Next);		
	}
	
	public void agefocusValue(){
		setAgeFocus(false);
	}

	// populate clearing all and fetch bank account type
	public void popbanklist() {
		// clear all fields once country change
		clearBankAccountPanel();
		if(lstBankFromView != null || !lstBankFromView.isEmpty()){
			lstBankFromView.clear();
		}
		if(lstBankbranchView != null || !lstBankbranchView.isEmpty()){
			lstBankbranchView.clear();
		}
		if(getServiceGroupId() != null){
			enableServiceProvider();
		}				
		// populate bank account type		
		populateBankAccountType();
		
		currency();

	}
	
	private void currency(){
		try{
			setBenifisCurrencyId(null);
			BigDecimal currencyId = iPersonalRemittanceService.getCurrencyId(getBenifisCountryId());
			
			if(currencyId != null && currencyId.compareTo(BigDecimal.ZERO) != 0)
			{
				setBenifisCurrencyId(currencyId);
			}else
			{
				setBenifisCurrencyId(null);
			}	
		
		}catch (Exception e) {
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}	
		
	}

	// bank list of cash product and banking channel
	public void enableServiceProvider() {
		if(agentAndCorrespondingBankslst != null || !agentAndCorrespondingBankslst.isEmpty()){
			agentAndCorrespondingBankslst.clear();
		}
		setAgentMaster(null);
		setAgentBranch(null);
		setAgentMasterBankName(null);
		if(agentAndCorrespondingBankBranchlst != null || !agentAndCorrespondingBankBranchlst.isEmpty()){
			agentAndCorrespondingBankBranchlst.clear();
		}
		setBankSpbranches(WarningHandler.showWarningMessage("lbl.agentBranch", sessionmanage.getLanguageId()));
		setBooRenderAgentMaster(true);
		lstDataTableBankbranch.clear();
		if(lstBankbranchView != null || !lstBankbranchView.isEmpty()){
			lstBankbranchView.clear();
		}
		lstBankFromView.clear();
		lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
		setDisableSaveButton(true);
		setBooAotherLang(false);
		if (lstfetchCashId.size() != 0) {
			if (getServiceGroupId() != null && (getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0)) {
				setWesternunionPanel(false);
				setBooenableAgentPanel(true);
				setBooEnableBankChannelPanel(false);

				setServiceTypeId(null);
				fetchAllBeneServiceProvider();
			} else {
				setServiceTypeId(null);
				setWesternunionPanel(false);
				setBooenableAgentPanel(false);
				setBooEnableBankChannelPanel(true);

				fetchAllBeneServiceBanks();
				setBooDisbleChoseeBranchButton(true);
				setBankBranchFullName(null);
			}
			clearBeneandAgentDetails();
		}		
	}

	// clear bank account details based on country select
	public void clearBankAccountPanel(){
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
		setAgentMasterBankName(null);
		setAgentBranch(null);
		setBankCode(null);
		setBeneficaryBankName(null);
		setBankBranchCode(null);
		if(agentAndCorrespondingBankslst != null || !agentAndCorrespondingBankslst.isEmpty()){
			agentAndCorrespondingBankslst.clear();
		}
		if(agentAndCorrespondingBankBranchlst != null || !agentAndCorrespondingBankBranchlst.isEmpty()){
			agentAndCorrespondingBankBranchlst.clear();
		}

	}

	// bank selection - bank channel
	public void popStatelistfromBank() {
		stateIdList.clear();
		setServicebankBranchId(null);
		setBankBranchFullName(null);
		setSearchBankStateId(null);
		setBeneficaryBankState(null);
		setBeneficaryBankDistrict(null);
		setBeneficaryBankCity(null);
		setBeneSwiftCode(null);
		setBankAccountNumber(null);
		setEnableDatatable(false);
		setBeneficaryBankName(null);
		if(lstBankbranchView != null || !lstBankbranchView.isEmpty()){
			lstBankbranchView.clear();
		}

		setBooDisbleChoseeBranchButton(false);

		setBooAotherLang(false);

		if(getBenifisCountryId() != null ){
			lstBankCountrystate = generalService.getStateList(sessionmanage.getLanguageId(), getBenifisCountryId());
			for (CountryMasterDesc lstCountrydata : lstCountry) {
				if(lstCountrydata.getFsCountryMaster().getCountryId().compareTo(getBenifisCountryId())==0){
					setBenifisCountryName(lstCountrydata.getCountryName());
					break;
				}
			}
		}

		// arabic name required or not
		for (BanksView bankMaster : lstBankFromView) {

			if (bankMaster.getBankId().compareTo(getBenifisBankId()) == 0) {
				setBankCode(bankMaster.getBankCode());
				setBeneficaryBankName(bankMaster.getBankFullName());

				if(!bankMaster.getLanguageInd().equals(Constants.ENGLISH_LANGUAGE_ID))
				{
					// based on country and bank id language is mandatory for beneficiary first_L name and second_L
					setBooAotherLang(true);
					// checking while edit of service and account LFirst Name and LSecond Name Mandatory for based on country and bank
					if(isBeneAccEditOrCreate()){
						if(getBooAotherLang()){
							if(getFirstLName()==null || getFirstLName()!=null && getFirstLName().length()==0){
								// next
								setSaveOrNextButton(Constants.Next);
							}else{
								// save
								setSaveOrNextButton(Constants.Save);
							}
						}else{
							// next
							setSaveOrNextButton(Constants.Save);
						}
					}else{
						// next
						setSaveOrNextButton(Constants.Next);
					}
				}else{
					if(isBeneAccEditOrCreate()){
						// next
						setSaveOrNextButton(Constants.Save);
					}else{
						// next
						setSaveOrNextButton(Constants.Next);
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

	// search bank branch moving from bank account to bank branch search
	public void searchClickedCreationbene() {
		try {
			HttpSession session = sessionmanage.getSession();
			session.setAttribute("BenificiaryBankID", getBenifisBankId());
			session.setAttribute("BenificiaryBankName", getBeneficaryBankName());
			session.setAttribute("CreationBaneficiary", true);
			session.setAttribute("BenificiaryCountryID",getBenifisCountryId());
			session.setAttribute("BenificiaryCountryName", getBenifisCountryName());
			session.setAttribute("stateList",null);
			session.setAttribute("stateList", lstBankCountrystate);
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../beneficary/searchbranchdetails.xhtml");

		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}
	}

	// bank account number validation - account number length check and bank account available in database
	public void bankAccountNumberExistInDB() {
		log.info("Entering into bankAccountNumberExistInDB method ");
		log.info("getBankAccountNumber " + getBankAccountNumber());
		log.info("getBenifisBankId " + getBenifisBankId());
		log.info("getBenifisCurrencyId  " + getBenifisCurrencyId());
		log.info("getBenifisCountryId  " + getBenifisCountryId());
		log.info("getServicebankBranchId  " + getServicebankBranchId());
		
		if(!isBeneAccEditOrCreate()){
			// clear beneficiary master details
			clearBeneMaster();
			setBeneficarymasterId(null);
		}else{
			setSaveOrNextButton(Constants.Save);
		}
		setActivatebeneficaryRelationshipId(null);
		setBoorenderBenemaster(false);
		setBooDisableBeneficaryType(false);
		setDisableDataOfBirth(false);
		setDisableNationality(false);
		setDisableBeneficiaryAccountExist(false);
		int accountLength = getBankAccountNumber().length();
		int i = 0;
		String lengthValues = "";
		List<BigDecimal> lstDuplicateCheck = new ArrayList<BigDecimal>();
		List<BankAccountLength> lstofAccLength = getiPersonalRemittanceService().getBankAccountLengthByBank(getBenifisBankId());
		if (lstofAccLength.size() != 0) {
			for (BankAccountLength bankAccountLength : lstofAccLength) {
				if ((bankAccountLength.getAcLength().compareTo(BigDecimal.ZERO)!=0) && bankAccountLength.getAcLength().compareTo(new BigDecimal(accountLength)) == 0) {
					i = 1;
					break;
				} else {
					if(!lstDuplicateCheck.contains(bankAccountLength.getAcLength()) && bankAccountLength.getAcLength().compareTo(BigDecimal.ZERO)!=0){
						lstDuplicateCheck.add(bankAccountLength.getAcLength());
						String commaAdd = "";
						if (!lengthValues.equalsIgnoreCase("")) {
							commaAdd = lengthValues + " OR ";
						}
						lengthValues = commaAdd + bankAccountLength.getAcLength().toPlainString();
						i = 2;
					}
				}
			}
		}

		if (i == 2) {
			setBankAccountNumber(null);
			setBankAccountLength(lengthValues);
			RequestContext.getCurrentInstance().execute("acclengthmismatch.show();");
		} else {
			try {
				// checking duplicates if master id will be empty here 							
				//HashMap<String, Object> checkDuplicates = checkDuplicatesForCashBankingChannel(getBeneficarymasterId());
				HashMap<String, Object> checkDuplicates = checkDuplicatesForCashBankingChannel(null);
				Boolean status = (Boolean)checkDuplicates.get("Status");
				BeneficaryAccount beneAcc = (BeneficaryAccount)checkDuplicates.get("BeneAccRec");
				System.out.println("Customer Id :" +getCustomerNo());
				if(status != null && !status){
					if(beneAcc != null){
						if(beneAcc.getBeneficaryAccountSeqId() != null && beneAcc.getBeneficaryMaster().getBeneficaryMasterSeqId() != null){
							// checking relationship and customer id matching or not
							setBeneficarymasterId(beneAcc.getBeneficaryMaster().getBeneficaryMasterSeqId());
							List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices.checkBenificaryRelationExist(getBeneficarymasterId(), beneAcc.getBeneficaryAccountSeqId());
							if(lstBeneRelationShip != null && !lstBeneRelationShip.isEmpty() && getCustomerNo() != null){
								Boolean checkStatus = false;
								String errorStatus = Constants.No;
								List<BigDecimal> duplCheck = new ArrayList<BigDecimal>();
								for (BeneficaryRelationship beneficaryRelationship : lstBeneRelationShip) {
									checkStatus = false;
									errorStatus = Constants.No;
									if(!duplCheck.contains(beneficaryRelationship.getBeneficaryRelationshipId())){
										duplCheck.add(beneficaryRelationship.getBeneficaryRelationshipId());
										if(beneficaryRelationship.getCustomerId().getCustomerId() != null && getCustomerNo() != null && beneficaryRelationship.getCustomerId().getCustomerId().compareTo(getCustomerNo())==0){
											checkStatus = true;
											if(beneficaryRelationship.getIsActive().equalsIgnoreCase(Constants.Yes) && beneAcc.getIsActive().equalsIgnoreCase(Constants.Yes)){
												setBeneficaryAccountSeqId(null);
												setActivatebeneficaryRelationshipId(null);
												errorStatus = Constants.Yes;
												break;
											}else{
												setBeneficaryAccountSeqId(beneficaryRelationship.getBeneficaryAccount().getBeneficaryAccountSeqId());
												setActivatebeneficaryRelationshipId(beneficaryRelationship.getBeneficaryRelationshipId());
												errorStatus = Constants.No;
												break;
											}
										}else{
											checkStatus = false;
										}
									}
								}
								if(checkStatus){
									if(errorStatus.equalsIgnoreCase(Constants.Yes)){
										setBankAccountNumber(null);
										// beneficiary exists , need name of the customer
										// List<BeneficaryMaster> lstBenedetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
										// duplicates exists - Beneficiary Account checking MasterSeqId,BankCode,BankCountryId,currencyId 
										setErrmsg("This combination already exist for Same Customer. Please check Beneficiary list");
										RequestContext.getCurrentInstance().execute("csp.show();");
										return;
									}else{
										setErrmsg("This combination already exist for Same Customer but De-Activated!!. Do you want to Activate ?");
										RequestContext.getCurrentInstance().execute("deactivedRecord.show();");
										//RequestContext.getCurrentInstance().execute("csp.show();");
										// if creating only enter. Edit of service and account should not enter
										if(!isBeneAccEditOrCreate()){
											// beneficiary exists , need name of the customer
											List<BeneficaryMaster> lstBeneDetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
											List<BeneficaryRelationship> lstBeneRelationShipDetails = lstBeneRelationShip;
											BeneficaryAccount beneAccDetails = beneAcc;
											// bank account type fetching
											beneficiaryStatusList();
											fetchAllBeneficiaryDetails(lstBeneDetails,lstBeneRelationShipDetails,beneAccDetails);
										}

									}

								}else{
									// new Record for this customer but record exists for different customer
									// setErrmsg("This combination already exist for Different Customer.");
									// RequestContext.getCurrentInstance().execute("csp.show();");
									// if creating only enter. Edit of service and account should not enter
									if(!isBeneAccEditOrCreate()){
										// beneficiary exists , need name of the customer
										List<BeneficaryMaster> lstBeneDetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
										List<BeneficaryRelationship> lstBeneRelationShipDetails = lstBeneRelationShip;
										BeneficaryAccount beneAccDetails = beneAcc;
										// bank account type fetching
										beneficiaryStatusList();
										fetchAllBeneficiaryDetails(lstBeneDetails,lstBeneRelationShipDetails,beneAccDetails);
										// disable all master details
										setBoorenderBenemaster(true);
										setBooDisableBeneficaryType(true);
										setDisableBeneficiaryAccountExist(true);
										setDisableNationality(true);
									}else{
										List<BeneficaryMaster> lstBeneDetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
										List<BeneficaryRelationship> lstBeneRelationShipDetails = lstBeneRelationShip;
										BeneficaryAccount beneAccDetails = beneAcc;
										// bank account type fetching
										beneficiaryStatusList();
										fetchAllBeneficiaryDetails(lstBeneDetails,lstBeneRelationShipDetails,beneAccDetails);
										// disable all master details
										setBoorenderBenemaster(true);
										setBooDisableBeneficaryType(true);
										setDisableBeneficiaryAccountExist(true);
										setDisableNationality(true);
										setSaveOrNextButton(Constants.Next);
									}
								}
							}else{
								// Relationship coming empty - not possible
								System.out.println("Relationship coming empty - not possible");
							}
						}else{
							// beneficiary account Seq Id null - not possible
							System.out.println("beneficiary account Seq Id null - not possible");
						}
					}else{
						// beneficiary account null - not possible
						System.out.println(" beneficiary account null - not possible");
					}
				}else{
					// new Record
					if(!isBeneAccEditOrCreate()){
						beneficiaryStatusList();
					}
				}
			} catch (Exception e) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg(e.getMessage());
				return;
			}
		}
	}

	// fetch All Beneficiary Master , Account Details and Relationship to show
	public void fetchAllBeneficiaryDetails(List<BeneficaryMaster> lstBeneDetails,List<BeneficaryRelationship> lstBeneRelationShipDetails,BeneficaryAccount beneAccDetails){
		List<BeneficaryMaster> lstBeneMasterDetails = lstBeneDetails;
		List<BeneficaryRelationship> lstBeneRelationDetails = lstBeneRelationShipDetails;
		BeneficaryAccount beneAccNumDetails = beneAccDetails;
		BeneficaryMaster datatabledetails = null;
		if(lstBeneMasterDetails != null){
			datatabledetails = lstBeneMasterDetails.get(0);
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

		if(datatabledetails.getFifthName() != null)
		{
			setFifthName(datatabledetails.getFifthName());
		}
		else
		{
			setFifthName(null);
		}

		if(datatabledetails.getLocalFirstName() != null){
			setFirstLName(datatabledetails.getLocalFirstName());
		}
		else
		{
			setFirstLName(null);
		}

		if(datatabledetails.getLocalSecondName() != null) {
			setSecondLName(datatabledetails.getLocalSecondName());
		}
		else
		{
			setSecondLName(null);
		}

		if(datatabledetails.getLocalThirdName() != null) {
			setThirdLName(datatabledetails.getLocalThirdName());
		}
		else
		{
			setThirdLName(null);
		}

		if(datatabledetails.getLocalFourthName() != null) {
			setFourthLName(datatabledetails.getLocalFourthName());
		}
		else
		{
			setFourthLName(null);
		}
		
		if(datatabledetails.getLocalFifthName() != null) {
			setFifthLName(datatabledetails.getLocalFifthName());
		}
		else
		{
			setFifthLName(null);
		}

		/*String engName =getFirstName()+" "+ getSecondName()+" "+getThirdName();
		String localName =getFirstLName()+" "+ getSecondLName()+" "+getThirdLName();

		String proceErrorMessage = getiPersonalRemittanceService().getBannedNameCheckProcedure(sessionmanage.getCountryId(), engName,localName);

		if(proceErrorMessage!=null && proceErrorMessage.length()>0){
			setErrmsg(proceErrorMessage);
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}*/

		// Age Calculation
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);

		// relationship list fetching
		// relationDescList = getiPersonalRemittanceService().getRelationsDescriptionList(sessionmanage.getLanguageId());
		//setRelationId(datatabledetails.getRelationShipId());
		/*if(datatabledetails.getMapSequenceId()!=null){
			setMapSequenceId(datatabledetails.getMapSequenceId());
		}*/

		// nationality 
		if(datatabledetails.getNationality() != null){
			setNationalityName(new BigDecimal(datatabledetails.getNationality()));
		}

		// beneficiary Contact details
		List<BeneficaryContact> contactDetails = beneficaryCreation.getTelephoneDetails(datatabledetails.getBeneficaryMasterSeqId());		
		if (contactDetails != null && !contactDetails.isEmpty()) {

			if(contactDetails.get(0).getCountryTelCode() != null){
				setCountryCode(contactDetails.get(0).getCountryTelCode());
				setMcountryCode(contactDetails.get(0).getCountryTelCode());
			}else{
				List<CountryMasterDesc> countryListForContactCode = beneficaryCreation.fetchCountryContactCode(datatabledetails.getFsCountryMaster().getCountryId());
				if(countryListForContactCode.size() != 0){
					setCountryCode(countryListForContactCode.get(0).getFsCountryMaster().getCountryTelCode());
					setMcountryCode(countryListForContactCode.get(0).getFsCountryMaster().getCountryTelCode());
				}
			}

			setBeneficaryTelephoneSeqId(contactDetails.get(0).getBeneficaryTelephoneSeqId());
			if (contactDetails.get(0).getTelephoneNumber() != null) {
				setTelephoneNumber(contactDetails.get(0).getTelephoneNumber().trim());
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

		//setCustomerId(datatabledetails.getCustomerId());
		//setCustomerNo(datatabledetails.getCustomerId());

		setYearOfBrith(datatabledetails.getYearOfBrith());
		setDateOfBrith(datatabledetails.getDateOfBrith());
		setContactCreatedBy(datatabledetails.getCreatedBy());
		setContactCreatedDate(datatabledetails.getCreatedDate());
		setAge(datatabledetails.getAge());
		setOccupation(datatabledetails.getOccupation());

		// bank country Id and Name
		setBenifisCountryId(beneAccNumDetails.getBeneficaryCountry().getCountryId());
		/*if(getBenifisCountryId() != null ){
			lstBankCountrystate = generalService.getStateList(sessionmanage.getLanguageId(), getBenifisCountryId());
		}*/
		//setBenifisCountryName(datatabledetails.getCountryName());
		//setCountryName(datatabledetails.getCountryName());
		setServiceGroupId(beneAccNumDetails.getServicegropupId().getServiceGroupId());
		// beneficiary country Id and Name
		setBeneCountryid(datatabledetails.getFsCountryMaster().getCountryId());
		setBeneSwiftCode(beneAccNumDetails.getSwiftCode());
		setBankAccountType(beneAccNumDetails.getBankAccountTypeId());

		checkingmandatoryOptional();

		if(datatabledetails != null){
			popStatelist();
			if(datatabledetails.getStateName() != null && datatabledetails.getFsStateMaster() != null){
				setBenifisStateId(datatabledetails.getFsStateMaster().getStateId());
			}
			popDistict();
			if(datatabledetails.getDistrictName() != null && datatabledetails.getFsDistrictMaster() != null){
				setDistictId(datatabledetails.getFsDistrictMaster().getDistrictId());
			}
			popCitylist();
			if(datatabledetails.getCityName() != null && datatabledetails.getFsCityMaster() != null){
				setCityId(datatabledetails.getFsCityMaster().getCityId());
			}
		}

		setStateName(datatabledetails.getStateName());
		setDistrictName(datatabledetails.getDistrictName());
		setCityName(datatabledetails.getCityName());
		setMasterCreatedBy(datatabledetails.getCreatedBy());
		setMasterCreatedDate(datatabledetails.getCreatedDate());
		setNoOfRemittance(datatabledetails.getNoOfRemittance()==null?"":datatabledetails.getNoOfRemittance().toString());
		setApplicationCountryId(datatabledetails.getApplicationCountryId().getCountryId());
		setIsActive(datatabledetails.getIsActive());

		if(datatabledetails.getBeneficaryStatus() != null){
			setBeneficaryStatusId(datatabledetails.getBeneficaryStatus().getBeneficaryStatusId());
			//setBeniStatusName(datatabledetails.getBeneficaryStatus().getBeneficaryStatusName());
			//setBenificaryStatusName(benificaryStatusName);
		}

		setBenifisBankId(beneAccNumDetails.getBank().getBankId());
		setBeneficaryBankName(beneAccNumDetails.getBank().getBankFullName());
		setBankName(beneAccNumDetails.getBank().getBankFullName());
		setBankCode(beneAccNumDetails.getBankCode());
		setServicebankBranchId(beneAccNumDetails.getBankBranch().getBankBranchId());
		setBankBranchName(beneAccNumDetails.getBankBranch().getBranchFullName());
		setBankBranchCode(beneAccNumDetails.getBankBranch().getBranchCode());

		setBenifisCurrencyId(beneAccNumDetails.getCurrencyId().getCurrencyId());

		setAliasFirstName(beneAccNumDetails.getAliasFirstName());
		setAliasSecondName(beneAccNumDetails.getAliasSecondName());
		setAliasThirdName(beneAccNumDetails.getAliasThirdName());
		setAliasFourthName(beneAccNumDetails.getAliasFourthName());
		setLastJavaRemittence(beneAccNumDetails.getLastJavaRemittence());
		setRecordStaus(beneAccNumDetails.getRecordStaus());

	}

	// cash product - start
	public void clearApplicableField()
	{
		log.info("Entering into clearApplicableField method");
		setBenifisCurrencyId(null);
		setAgentMaster(null);
		setAgentBranch(null);
		setAgentMasterBankName(null);

		if(agentAndCorrespondingBankslst != null || !agentAndCorrespondingBankslst.isEmpty()){
			agentAndCorrespondingBankslst.clear();
		}

		if(agentAndCorrespondingBankBranchlst != null || !agentAndCorrespondingBankBranchlst.isEmpty()){
			agentAndCorrespondingBankBranchlst.clear();
		}

		log.info("Exit into clearApplicableField method");
	}

	// bank of cash product agents and service providers
	public void enableAgentMaster() {
		log.info("Entering into enableAgentMaster method");
		log.info("getAgentMaster" + getAgentMaster());
		if(agentAndCorrespondingBankslst != null || !agentAndCorrespondingBankslst.isEmpty()){
			agentAndCorrespondingBankslst.clear();
		}
		setAgentMaster(null);
		setAgentBranch(null);
		setBankCode(null);
		setBeneficaryBankName(null);
		setAgentMasterBankName(null);
		if(agentAndCorrespondingBankBranchlst != null || !agentAndCorrespondingBankBranchlst.isEmpty()){
			agentAndCorrespondingBankBranchlst.clear();
		}
		if(lstAgentsBanks != null || !lstAgentsBanks.isEmpty()){
			lstAgentsBanks.clear();
		}

		// fetch details from view
		List<ViewRoutingAgents> lstAgents = iroutingSetUpDetailsService.fetchAllRoutingAgents(sessionmanage.getCountryId(), getBenifisCountryId(), getServiceGroupId(),  getServiceTypeId(), getBenifisCurrencyId());

		if(lstAgents != null && lstAgents.size() != 0){
			lstAgentsBanks.addAll(lstAgents);
			if(lstAgents.size() == 1){
				setBooMultipleService(false);
				setBooSingleService(true);
				for (ViewRoutingAgents viewRoutingAgents : lstAgents) {
					setAgentMaster(viewRoutingAgents.getAgentBankId());
					setBankCode(viewRoutingAgents.getAgentBankCode());
					setAgentMasterBankName(viewRoutingAgents.getAgentBankName());
				}
				popAgentBranch();
			}else{
				setBooMultipleService(true);
				setBooSingleService(false);
				for (ViewRoutingAgents viewRoutingAgents : lstAgents) {
					PopulateDataWithCode lstBank = new PopulateDataWithCode();

					lstBank.setId(viewRoutingAgents.getAgentBankId());
					lstBank.setCode(viewRoutingAgents.getAgentBankCode());
					lstBank.setName(viewRoutingAgents.getAgentBankName());

					agentAndCorrespondingBankslst.add(lstBank);
				}
			}
		}else{
			if(agentAndCorrespondingBankslst != null || !agentAndCorrespondingBankslst.isEmpty()){
				agentAndCorrespondingBankslst.clear();
			}
			if(lstAgentsBanks != null || !lstAgentsBanks.isEmpty()){
				lstAgentsBanks.clear();
			}
			setBooMultipleService(false);
			setBooSingleService(true);
		}
		log.info("Exit into enableAgentMaster method");
	}

	// populate agents bank branches
	public void popAgentBranch()
	{
		setAgentBranch(null);
		if(lstBankbranchView != null || !lstBankbranchView.isEmpty()){
			lstBankbranchView.clear();
		}

		if(agentAndCorrespondingBankBranchlst != null || !agentAndCorrespondingBankBranchlst.isEmpty()){
			agentAndCorrespondingBankBranchlst.clear();
		}

		if(getAgentMaster() != null && lstAgentsBanks.size() != 0){

			// fetch details from view
			List<ViewRoutingAgentLocations> lstAgentLocation = iroutingSetUpDetailsService.fetchAllRoutingAgentLocations(sessionmanage.getCountryId(), getBenifisCountryId(), getServiceGroupId(),  getServiceTypeId(), getBenifisCurrencyId(),getAgentMaster());

			if(lstAgentLocation != null && lstAgentLocation.size() != 0){
				setLstAgentLocationForCash(lstAgentLocation);
				for (ViewRoutingAgentLocations viewRoutingAgentLocations : lstAgentLocation) {
					PopulateDataWithCode lstBank = new PopulateDataWithCode();

					lstBank.setId(viewRoutingAgentLocations.getBankBranchId());
					lstBank.setCode(viewRoutingAgentLocations.getBranchCode()==null ? null : viewRoutingAgentLocations.getBranchCode().toString());
					lstBank.setName(viewRoutingAgentLocations.getBranchFullName());

					agentAndCorrespondingBankBranchlst.add(lstBank);
				}

			}else{
				//setLstAgentLocationForCash(null);
				if(agentAndCorrespondingBankBranchlst != null || !agentAndCorrespondingBankBranchlst.isEmpty()){
					agentAndCorrespondingBankBranchlst.clear();
				}
				if(lstAgentLocationForCash != null || !lstAgentLocationForCash.isEmpty()){
					lstAgentLocationForCash.clear();
				}

			}

			setBooAotherLang(false);
			for (ViewRoutingAgents datatable : lstAgentsBanks) {

				if (getAgentMaster() != null && datatable.getAgentBankId().compareTo(getAgentMaster()) == 0) {
					setBankCode(datatable.getAgentBankCode());

					if(!datatable.getLanguageInd().equals(Constants.ENGLISH_LANGUAGE_ID))
					{
						// based on country and bank id language is mandatory for beneficiary first_L name and second_L
						setBooAotherLang(true);
						// checking while edit of service and account LFirst Name and LSecond Name Mandatory for based on country and bank
						if(isBeneAccEditOrCreate()){
							if(getBooAotherLang()){
								if(getFirstLName()==null || getFirstLName()!=null && getFirstLName().length()==0){
									// next
									setSaveOrNextButton(Constants.Next);
								}else{
									// save
									setSaveOrNextButton(Constants.Save);
								}
							}else{
								// next
								setSaveOrNextButton(Constants.Save);
							}
						}else{
							// next
							setSaveOrNextButton(Constants.Next);
						}
					}else{
						if(isBeneAccEditOrCreate()){
							// next
							setSaveOrNextButton(Constants.Save);
						}else{
							// next
							setSaveOrNextButton(Constants.Next);
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

	}

	// setting bank code of agents and branch code on selection of branches
	public void fetchingBankBranchCode(){

		// agents bank code setting
		if(lstAgentsBanks != null && !lstAgentsBanks.isEmpty() && getAgentMaster() != null && getBankCode() == null){
			for (ViewRoutingAgents banksview : lstAgentsBanks) {
				if(banksview.getAgentBankId().compareTo(getAgentMaster())==0){
					setBankCode(banksview.getAgentBankCode());
					break;
				}else{
					setBankCode(null);
				}
			}
		}

		// agents bank Branch code setting
		if(lstAgentLocationForCash != null && !lstAgentLocationForCash.isEmpty() && getAgentBranch() != null){
			for (ViewRoutingAgentLocations banksBranchview : lstAgentLocationForCash) {
				if(banksBranchview.getBankBranchId().compareTo(getAgentBranch())==0){
					setBankBranchCode(banksBranchview.getBranchCode());
					setServiceProviderBankBranchId(banksBranchview.getRoutingBranchId());
					break;
				}else{
					setBankBranchCode(null);
					setServiceProviderBankBranchId(null);
				}
			}
		}

		if(getAgentBranch() != null){
			setServicebankBranchId(getAgentBranch());
		}
	}

	// beneficiary bank account to remittance page - dialogue box ask Yes or No
	public void checkingBacktoRemittance(){
		RequestContext.getCurrentInstance().execute("backcheckforBeneCreation.show();");
	}

	// divert save page or next page
	public void gotoNextOrSave(){
		setWesternunionPanel(false);
		setBooRenderWesterUnion(false);
		setBooRenderWesterUnionUpload(false);
		try {
			if(getSaveOrNextButton() != null && getSaveOrNextButton().equalsIgnoreCase(Constants.Next)){
				if(getBooBenefiCorporateRemit()){
					gotoBeneficaryAccountDetails();
					toFetchRelationCorporate();
				}else{
					gotoBeneficaryAccountDetails();
				}
				checkingmandatoryOptional();
			}else if(getSaveOrNextButton() != null && getSaveOrNextButton().equalsIgnoreCase(Constants.Save)){
				saveBeneficaryCreation();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	// next button to go beneficiary creation page
	public void gotoBeneficaryAccountDetails() {

		setWesternunionPanel(false);
		setBooRenderBeneficaryCreation(true);
		try {
			log.info("Page redirect to bankacccountdetails page");
			
			// nationality from fs_country master
			if(nationalityList!=null && nationalityList.isEmpty()){
				nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
			}
			// benficiary country from fs_country master
			if(beneCountryList!=null && beneCountryList.isEmpty() && lstCountry != null && lstCountry.isEmpty()){
				beneCountryList = generalService.getCountryList(sessionmanage.getLanguageId());
			}else{
				beneCountryList = lstCountry;
			}

			if(lstfetchCashId == null || lstfetchCashId.isEmpty()){
				lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
			}

			if(!isBeneAccEditOrCreate()){
				if(lstfetchCashId != null && !lstfetchCashId.isEmpty()){
					//setServiceProviderBankBranchId(getAgentBranch());
					if(getServiceGroupId() != null && getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0){
						// cash Product
						// bank account type fetching
						beneficiaryStatusList();
					}
				}
			}
			
			if(getAge() == null && getDateOfBrith() == null && getYearOfBrith() == null)
			{
				setReadOnlyDateOfBirth(false);
				setReadOnlyYearOfBirth(false);
				setReadOnlyAge(false);
				setDisableDataOfBirth(false);
				setDisableResetDataOfBirth(false);
			}
			
			if(getDisableBeneficiaryAccountExist()){
				// fetch already exists for that user
			}else{
				BankBranchView lstBankbranchView = null;
				if(getBenifisBankId() != null && getServicebankBranchId() != null){
					lstBankbranchView = beneficaryCreation.getBranchListfromView(getBenifisBankId(),getServicebankBranchId());
				}else{
					lstBankbranchView = beneficaryCreation.getBranchListfromView(getAgentMaster(),getAgentBranch());
				}
				
				popStatelist();
				if(lstBankbranchView != null){
					setBenifisStateId(lstBankbranchView.getStateId());	
					popDistict();			
					setDistictId(lstBankbranchView.getDistrictId());
					popCitylist();
					setCityId(lstBankbranchView.getCityId());
				}
			}
							
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiaryCreation.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	// bank account type fetching
	private void beneficiaryStatusList() {
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

			if(benificaryStatusList != null && !benificaryStatusList.isEmpty()){
				setBeneficaryStatusId(benificaryStatusList.get(0).getBeneficaryStatusId());
				changeIndidualorNonIndidual();
			}
		}
	}

	// beneficiary creation page -  start
	public void changeIndidualorNonIndidual() {
		lststate.clear();
		setBooDisableBeneficaryType(false);
		
		if(lstCountry!=null && lstCountry.isEmpty()){
			lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
		}
		
		
		for (BeneficaryStatus beneficarystatus : benificaryStatusName) {
			if (beneficarystatus.getBeneficaryStatusId().compareTo(getBeneficaryStatusId()) == 0) {
				setBeneficaryStatusName(beneficarystatus.getBeneficaryStatusName());
				System.out.println("##########" + getBeneficaryStatusName());
				Calendar cal1 = new GregorianCalendar();
				cal1.setTime(new Date());
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
					if(getBooBenefiCorporateRemit()){
						setBeneficaryTypeId(new BigDecimal(2));
					}else{
						if(!checkExist){
							setBeneficaryTypeId(new BigDecimal(1));
						}
						else
						{
							setBeneficaryTypeId(new BigDecimal(2));  
						}
					}
					checkBeneficaryType();
					setMinagevalidation(false);
					setRenderBackButton(false);
					setBooRenderBenificaryFirstPanel(false);
					setBooRenderBenificarySearchPanel(false);
					setBooRenderBenificaryStatusPanel(true);
					setBooRenderIndBenificaryStatusPanel(true);
					setReadOnlyYearOfBirth(false);
					setReadOnlyAge(false);
					System.out.println( "lststate size" +lststate.size());
					if(lststate.size() != 0){
						setLststate(lststate);
					}

					break;
				}

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
	}

	// Individual or Non-Individual selection Self and Others Selected
	public void checkBeneficaryType() {
		int benfiTypeId = 0;
		if (getBeneficaryTypeId() != null){

			benfiTypeId = getBeneficaryTypeId().intValue();

			if(getSelectCard() != null){

				//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber().toUpperCase(), getSelectCard());
				customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber().toUpperCase(), getSelectCard());

				if(customerDetailsList.size() != 0){
					// no need to go else
				}else{
					// comparing with civil id
					BigDecimal identityTpeIds = generalService.getComponentId(Constants.CIVILID, sessionmanage.getLanguageId()).getFsBizComponentData().getComponentDataId();

					if(getSelectCard().compareTo(identityTpeIds)!=0){
						//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), identityTpeIds);
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), identityTpeIds);
						if(customerDetailsList.size() != 0){
							// no need to go else. It is checking with civil id
						}else{
							// comparing with civil id new
							BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionmanage.getLanguageId()).getFsBizComponentData().getComponentDataId();
							//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), idtypeCivilIdnew);
							customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idtypeCivilIdnew);
							if(customerDetailsList.size() != 0){
								// no need to go else. It is checking with civil id new
							}else{
								// failed all conditions
								/*setCardType(null);
								setIdNumber(null);
								setBooRenderBenificaryFirstPanel(true);
								RequestContext.getCurrentInstance().execute("idNotFound.show();");*/
							}
						}
					}else{
						// comparing with civil id new
						BigDecimal idtypeCivilIdnew = null;
						if(getBooBenefiCorporateRemit()){
							idtypeCivilIdnew=generalService.getComponentId(Constants.COMPANY_REGISTRATION_DOC, sessionmanage.getLanguageId()).getFsBizComponentData().getComponentDataId();
						}else{
							idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionmanage.getLanguageId()).getFsBizComponentData().getComponentDataId();
						}
						//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), idtypeCivilIdnew);
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idtypeCivilIdnew);
						if(customerDetailsList.size() != 0){
							// no need to go else. It is checking with civil id new
						}else{
							// failed all conditions
							/*setCardType(null);
							setIdNumber(null);
							setBooRenderBenificaryFirstPanel(true);
							RequestContext.getCurrentInstance().execute("idNotFound.show();");*/
						}

					}
				}

			}

			/*BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionmanage.getLanguageId()).getFsBizComponentData().getComponentDataId();

			BigDecimal identityTpeIds = generalService.getComponentId(Constants.CIVILID, sessionmanage.getLanguageId()).getFsBizComponentData().getComponentDataId();

			if (getSelectCard() != null && idtypeCivilIdnew != null && getSelectCard().intValue() == identityTpeIds.intValue() || getSelectCard().intValue() == idtypeCivilIdnew.intValue()) {
				customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), identityTpeIds);
				if (customerDetailsList.size() == 0) {
					customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), idtypeCivilIdnew);
				}
			} else {
				customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), getSelectCard());
			}*/

			if (benfiTypeId == 2) {

				if (customerDetailsList.size() > 0 && customerDetailsList != null) {
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
					//setBenifisStateId(null);
					//setBenifisCountryId(null);
					//setDistictId(null);
					//setCityId(null);
					//lststate.clear();
					//lstDistict.clear();
					//lstCity.clear();
					setReadOnlyOccupation(false);
					setMinagevalidation(false);
					setDisablerelation(false);
					setReadOnlyFirstName(false);
					setReadOnlySecondName(false);
					setReadOnlyThirdName(false);
					setReadOnlyAddress(false);
					setReadOnlyNationality(false);
					setReadOnlyRelations(false);
					setReadOnlyFifthName(false);
					setReadOnlyFourthName(false);
					setReadOnlyDateOfBirth(false);
					setReadOnlyYearOfBirth(false);
					setReadOnlyAge(false);
					setDisableDataOfBirth(false);
					setDisableBeneficiaryAccountExist(false);
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
							//if (!relationsDescription.getLocalRelationsDescription().equalsIgnoreCase(Constants.SELF)) {
								tempList.add(relationsDescription);
							//}
						}
						relationDescList.clear();
						relationDescList.addAll(tempList);
						tempList.clear();
					}

					// setting customer nationality and bene country and state pop for others
					getCustomerForOthers();
				}

			} else if (benfiTypeId == 1) {

				if (customerDetailsList.size() > 0 && customerDetailsList != null) {

					setBeneCountryid(null);
					//setBenifisCountryId(null);
					//setBenifisStateId(null);
					//setDistictId(null);
					//setCityId(null);
					//lststate.clear();
					//lstDistict.clear();
					//lstCity.clear();
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
					setDisableBeneficiaryAccountExist(false);
					setDisableDataOfBirth(true);
					setDisableResetDataOfBirth(true);
					//List<RelationsDescription> tempList = new ArrayList<RelationsDescription>();
					List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.SELF);
					for (RelationsDescription relationsDescription : selfid) {
						//tempList.add(relationsDescription);
						BigDecimal selfPK = relationsDescription.getRelations().getRelationsId();
						setRelationId(selfPK);
					}
					
					relationDescList = getiPersonalRemittanceService().getRelationsDescriptionList(sessionmanage.getLanguageId());
					List<RelationsDescription> tempList = new ArrayList<RelationsDescription>();
					for (RelationsDescription relationsDescription : relationDescList) {
							tempList.add(relationsDescription);
					}
					relationDescList.clear();
					relationDescList.addAll(tempList);
					tempList.clear();
					
					setDisablerelation(true);
					setDisableNationality(true);
					setReadOnlyOccupation(true);
					
					// fetch state details - 14/03/2017 
					//popStatelist(); 
				}
			} else if (benfiTypeId == 0) {
				// bene type not self and other
			}

			// checking district mandatory or not
			checkingmandatoryOptional();
		}
	}

	// checking relation type exists in database
	public void checkRelationExistFromDb() {
		List<BeneficaryRelationship> beneficaryRelationshipLst = getiPersonalRemittanceService().checkRelationExistWithCustId(getRelationId(), getCustomerNo());
		if (beneficaryRelationshipLst.size() > 0) {
			String localRelationShipName = beneficaryRelationshipLst.get(0).getRelations().getEnglishRelationsTypeDesc();
			setRelationShipName(localRelationShipName);
			setCheckRelationExist("YES");
			setRelationExist(false);
		} else {
			setCheckRelationExist("NO");
			setRelationExist(false);
		}
	}

	// populate beneficiary country states based on beneficiary country
	public void popStatelist() {
		setStateName(null);
		setBenifisStateId(null);
		if(getBeneCountryid() != null ){
			lststate = generalService.getStateList(sessionmanage.getLanguageId(), getBeneCountryid());
		}else{
			setBeneCountryid(null);
		}
	}

	// checking mandatory Optional - state and district
	public void checkingmandatoryOptional(){
		setMandatoryOptional(true);
		if(beneCountryList.size() != 0 && getBeneCountryid() != null){
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

	// back to beneficiary account
	public void backTobeneficiaryDetails() {
		setAgeFocus(false);
		try {
			log.info("Page redirect to bankacccountdetails page");

			setBooRenderWesterUnion(false); // off western union panel

			/*if(getBooAotherLang()==null || getBooAotherLang()) {
				// direct call new bene time
				setBenifisCountryId(null);
				setServiceGroupId(null);
				setBooenableAgentPanel(true);
			}else{*/
			setBooRenderWesterUnionUpload(false); // off western union Upload panel
			setBooRenderBeneficaryCreation(true);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	// fetching telephone code
	public void updateTelePhoneCode()
	{
		setCountryCode(null);
		setMcountryCode(null);
		setTelephoneNumber(null);
		setMobileNumber(null);
		if(getNationalityName()!=null) {
			String teleCountryCode = generalService.getTelephoneCountryBasedOnNationality(getNationalityName());
			if(teleCountryCode != null) {
				setCountryCode(teleCountryCode);
				setMcountryCode(teleCountryCode);
				setBeneCountryid(getNationalityName());
				popStatelist();
				checkingmandatoryOptional();
			}else{
				setBeneCountryid(getNationalityName());
				popStatelist();
				checkingmandatoryOptional();
				setErrmsg("Country Telphone Code Not Available");
				RequestContext.getCurrentInstance().execute("csp.show();");
			}

		}
	}

	// age validation
	public void ageValidation(SelectEvent e) {		

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

		}
		setAgeFocus(true);
	}

	// age selection
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

	// beneficiary country details
	public void fetchAllBeneficaryLocation(){
		setAgeFocus(false);
		setBenifisStateId(null);
		setDistictId(null);
		setCityId(null);
		if(getBeneCountryid() != null){
			checkingmandatoryOptional();
			// setting beneficiary state , district and city
			popStatelist();
		}
	}

	// fetching district list for beneficiary state
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

	// fetching city list for beneficiary district
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

	// after selecting of city
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

	// save beneficiary final save for cash product and banking channel
	public void saveBeneficiaryCashBankingChannel(){

		try{
			setDisableSaveBack(true);
			System.out.println("Beneficary Master ----------------------------- > ");
			BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
			BeneficaryAccount beneficaryAccount = new BeneficaryAccount();
			BeneficaryContact contact = new BeneficaryContact();
			BeneficaryRelationship relationship = new BeneficaryRelationship();
			Map<String, Object> mapBeneMasterCheck = new HashMap<String, Object>();

			if(getFirstName() != null && !getFirstName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("FirstName", getFirstName().toUpperCase());
			}
			if(getSecondName() != null && !getSecondName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("SecondName", getSecondName().toUpperCase());
			}

			/*if(getThirdName() != null && !getThirdName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("ThirdName", getThirdName().toUpperCase());
			}
			if(getFourthName() != null && !getFourthName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("FourthName", getFourthName().toUpperCase());
			}
			if(getFifthName() != null && !getFifthName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("FifthName", getFifthName().toUpperCase());
			}
			if(getFirstLName() != null && !getFirstLName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("FirstLName", getFirstLName());
			}
			if(getSecondLName() != null && !getSecondLName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("SecondLName", getSecondLName());
			}
			if(getThirdLName() != null && !getThirdLName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("ThirdLName", getThirdLName());
			}
			if(getFourthLName() != null && !getFourthLName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("FourthLName", getFourthLName());
			}*/

			if(getBeneCountryid() != null){
				mapBeneMasterCheck.put("BeneCountryId", getBeneCountryid());
			}

		
			
			mapBeneMasterCheck.put("customerId", getCustomerNo());
			
			String engName = getFirstName() + " " + getSecondName() + " " + getThirdName();
			String localName =getFirstLName() + " " + getSecondLName() + " " + getThirdLName();

			/** added by Rabil on 03/12/2015 for EX_P_BANNED_NAME_CHECK*/ 
			String proceErrorMessage = getiPersonalRemittanceService().getBannedNameCheckProcedure(sessionmanage.getCountryId(), engName,localName);

			if(proceErrorMessage!=null && proceErrorMessage.length()>0){
				setErrmsg(proceErrorMessage);
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}

			List<BeneficaryMaster> lstBeneMaster = westernUnionService.fetchBeneMasterRecForWU(mapBeneMasterCheck);

			if(lstBeneMaster != null && lstBeneMaster.size() != 0){

				for (BeneficaryMaster beneMaster : lstBeneMaster) {

					if (getFirstName() != null && getSecondName() != null && beneMaster.getFirstName() != null && beneMaster.getSecondName() != null && beneMaster.getFirstName().equalsIgnoreCase(getFirstName().toUpperCase()) && beneMaster.getSecondName().equalsIgnoreCase(getSecondName().toUpperCase())) {

						Boolean check = false;

						//if(Objects.equals(getThirdName() == null || getThirdName().equalsIgnoreCase("") ? null:getThirdName().toUpperCase(), beneMaster.getThirdName())){
						//if(Objects.equals(getFourthName() == null || getFourthName().equalsIgnoreCase("") ? null:getFourthName().toUpperCase(), beneMaster.getFourthName())){
						//if(Objects.equals(getFifthName() == null || getFifthName().equalsIgnoreCase("") ? null:getFifthName().toUpperCase(), beneMaster.getFifthName())){
						if(getBeneCountryid() != null && beneMaster.getFsCountryMaster() != null && getBeneCountryid().compareTo(beneMaster.getFsCountryMaster().getCountryId())==0){
							// old record
							check = true;
						}else{
							// new record
							check = false;
						}
						/*}else{
									// new record
									check = false;
								}*/
						/*}else{
								// new record
								check = false;
							}*/
						/*}else{
							// new record
							check = false;
						}*/

						if(check){
							beneficaryMaster = beneficaryMasterSave(beneMaster);
							break;
						}
					}
				}

				if(beneficaryMaster.getBeneficaryMasterSeqId() != null){

					// checking telephone and mobile with beneficiary contact details
					List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(beneficaryMaster.getBeneficaryMasterSeqId());
					Boolean teleMobCheck = false;
					for (BeneficaryContact beneficaryContact : telePhone) {
						if (telePhone != null && telePhone.size() != 0) {
							if(beneficaryContact.getTelephoneNumber()!=null){
								if(getTelephoneNumber() != null && getTelephoneNumber().trim().equalsIgnoreCase(beneficaryContact.getTelephoneNumber())){
									// old
									contact = beneficaryContact;
									teleMobCheck = true;
									break;
								}else{
									// new
									teleMobCheck = false;
								}
							}else if(beneficaryContact.getMobileNumber() !=null){
								if(getMobileNumber().compareTo(beneficaryContact.getMobileNumber())==0){
									// old
									contact = beneficaryContact;
									teleMobCheck = true;
									break;
								}else{
									// new
									teleMobCheck = false;
								}
							}
						}
					}

					if(teleMobCheck){
						// mobile same
						try {
							// checking duplicates if master id is old 							
							if(beneficaryMaster.getBeneficaryMasterSeqId() != null){
								HashMap<String, Object> checkDuplicates = checkDuplicatesForCashBankingChannel(beneficaryMaster.getBeneficaryMasterSeqId());
								Boolean status = (Boolean)checkDuplicates.get("Status");
								BeneficaryAccount beneAcc = (BeneficaryAccount)checkDuplicates.get("BeneAccRec");
								System.out.println("Customer Id :" +getCustomerNo());
								if(status != null && !status){
									if(beneAcc != null){
										if(beneAcc.getBeneficaryAccountSeqId() != null){
											// checking relationship and customer id matching or not
											List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices.checkBenificaryRelationExist(beneficaryMaster.getBeneficaryMasterSeqId(), beneAcc.getBeneficaryAccountSeqId());
											if(lstBeneRelationShip != null && !lstBeneRelationShip.isEmpty() && getCustomerNo() != null){
												Boolean checkStatus = false;
												for (BeneficaryRelationship beneficaryRelationship : lstBeneRelationShip) {
													checkStatus = false;
													if(beneficaryRelationship.getCustomerId().getCustomerId().compareTo(getCustomerNo())==0){
														checkStatus = true;
														break;
													}else{
														checkStatus = false;
													}
												}
												if(checkStatus){
													// duplicates exists - Beneficiary Account checking MasterSeqId,BankCode,BankCountryId,currencyId 
													setErrmsg("This combination already exist in datatable");
													RequestContext.getCurrentInstance().execute("csp.show();");
													return;
												}else{
													try{
														// creating new record customer maybe different
														beneficaryAccount = beneAcc;
														relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
														beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
													}catch(Exception e){
														setErrmsg(e.getMessage());
														RequestContext.getCurrentInstance().execute("csp.show();");
														return;
													}
												}
											}else{
												// Relationship coming empty - not possible
												System.out.println("Relationship coming empty - not possible");
											}
										}else{
											// beneficiary account Seq Id null - not possible
											System.out.println("beneficiary account Seq Id null - not possible");
										}
									}else{
										// beneficiary account null - not possible
										System.out.println(" beneficiary account null - not possible");
									}
								}else{
									try{
										// creating new record
										setBeneficaryAccountSeqId(null);
										beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
										relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
										beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
									}catch(Exception e){
										setErrmsg(e.getMessage());
										RequestContext.getCurrentInstance().execute("csp.show();");
										return;
									}
								}
							}else{
								try{
									// creating new record
									beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
									relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
									beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
								}catch(Exception e){
									setErrmsg(e.getMessage());
									RequestContext.getCurrentInstance().execute("csp.show();");
									return;
								}
							}
						} catch (Exception e) {
							RequestContext.getCurrentInstance().execute("csp.show();");
							setErrmsg(e.getMessage());
							return;
						}

					}else{
						contact = saveBeneficaryTelephone(beneficaryMaster);

						try {
							// checking duplicates if master id is old 							
							if(beneficaryMaster.getBeneficaryMasterSeqId() != null){
								HashMap<String, Object> checkDuplicates = checkDuplicatesForCashBankingChannel(beneficaryMaster.getBeneficaryMasterSeqId());
								Boolean status = (Boolean)checkDuplicates.get("Status");
								BeneficaryAccount beneAcc = (BeneficaryAccount)checkDuplicates.get("BeneAccRec");
								System.out.println("Customer Id :" +getCustomerNo());
								if(status != null && !status){
									if(beneAcc != null){
										if(beneAcc.getBeneficaryAccountSeqId() != null){
											// checking relationship and customer id matching or not
											List<BeneficaryRelationship> lstBeneRelationShip = iPersonalRemittanceService.isBenificaryRelationExist(beneficaryMaster.getBeneficaryMasterSeqId(), beneAcc.getBeneficaryAccountSeqId());
											if(lstBeneRelationShip != null && !lstBeneRelationShip.isEmpty() && getCustomerNo() != null){
												Boolean checkStatus = false;
												for (BeneficaryRelationship beneficaryRelationship : lstBeneRelationShip) {
													checkStatus = false;
													if(beneficaryRelationship.getCustomerId().getCustomerId().compareTo(getCustomerNo())==0){
														checkStatus = true;
														break;
													}else{
														checkStatus = false;
													}
												}
												if(checkStatus){
													// duplicates exists - Beneficiary Account checking MasterSeqId,BankCode,BankCountryId,currencyId 
													setErrmsg("This combination already exist in datatable");
													RequestContext.getCurrentInstance().execute("csp.show();");
													return;
												}else{
													try{
														// creating new record customer maybe different
														beneficaryAccount = beneAcc;
														relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
														beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
													}catch(Exception e){
														setErrmsg(e.getMessage());
														RequestContext.getCurrentInstance().execute("csp.show();");
														return;
													}
												}
											}else{
												// Relationship coming empty - not possible
												System.out.println("Relationship coming empty - not possible");
											}
										}else{
											// beneficiary account Seq Id null - not possible
											System.out.println("beneficiary account Seq Id null - not possible");
										}
									}else{
										// beneficiary account null - not possible
										System.out.println(" beneficiary account null - not possible");
									}
								}else{
									try{
										// creating new record
										beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
										relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
										beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
									}catch(Exception e){
										setErrmsg(e.getMessage());
										RequestContext.getCurrentInstance().execute("csp.show();");
										return;
									}
								}
							}else{
								try{
									// creating new record
									beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
									relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
									beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
								}catch(Exception e){
									setErrmsg(e.getMessage());
									RequestContext.getCurrentInstance().execute("csp.show();");
									return;
								}
							}
						} catch (Exception e) {
							RequestContext.getCurrentInstance().execute("csp.show();");
							setErrmsg(e.getMessage());
							return;
						}
					}

				}else{
					try{
						beneficaryMaster = beneficaryMasterSave();
						contact = saveBeneficaryTelephone(beneficaryMaster);
						beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
						relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);

						// creating new record
						beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
					}catch(Exception e){
						setErrmsg(e.getMessage());
						RequestContext.getCurrentInstance().execute("csp.show();");
						return;
					}
				}
			}else{
				try{
					beneficaryMaster = beneficaryMasterSave();
					contact = saveBeneficaryTelephone(beneficaryMaster);
					beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
					relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);

					// creating new record
					beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
				}catch(Exception e){
					setErrmsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}

			}

			setBeneficarymasterId(beneficaryMaster.getBeneficaryMasterSeqId());
			String errorMessage = null;

			if(getBenifisBankId() != null && getServicebankBranchId() != null){
				errorMessage = beneficaryCreation.getBeneDetailProce(beneficaryMaster.getBeneficaryMasterSeqId(), getBenifisBankId(), getServicebankBranchId(), beneficaryAccount.getBeneficaryAccountSeqId(), getBenifisCurrencyId(), getCustomerNo());
			}else{
				errorMessage = beneficaryCreation.getBeneDetailProce(beneficaryMaster.getBeneficaryMasterSeqId(), getAgentMaster(), getAgentBranch(), beneficaryAccount.getBeneficaryAccountSeqId(), getBenifisCurrencyId(), getCustomerNo());
			}

			if (errorMessage == null) {
				RequestContext.getCurrentInstance().execute("beneficarycomplete.show();");
			} else {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("EX_POPULATE_BENE_DT " + errorMessage);
			}
		}catch(Exception e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
	}

	// beneficiary new record save
	private BeneficaryMaster beneficaryMasterSave() {
		BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
		try{
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneficaryMaster.setApplicationCountryId(countryMaster);
			CountryMaster countryMaster1 = new CountryMaster();
			countryMaster1.setCountryId(getBeneCountryid());
			beneficaryMaster.setFsCountryMaster(countryMaster1);
			beneficaryMaster.setFirstName(getFirstName()==null ? null : getFirstName().toUpperCase());
			beneficaryMaster.setSecondName(getSecondName()==null ? null : getSecondName().toUpperCase());
			beneficaryMaster.setThirdName(getThirdName()==null ? null : getThirdName().toUpperCase());
			beneficaryMaster.setFourthName(getFourthName()==null ? null : getFourthName().toUpperCase());
			beneficaryMaster.setFifthName(getFifthName()==null ? null : getFifthName().toUpperCase());
			if(getBeneficaryStatusId() != null){
				BeneficaryStatus beneficaryStatus = new BeneficaryStatus();
				beneficaryStatus.setBeneficaryStatusId(getBeneficaryStatusId());
				beneficaryMaster.setBeneficaryStatus(beneficaryStatus);
			}

			beneficaryMaster.setNationality(getNationalityName().toString());
			beneficaryMaster.setDateOfBrith(getDateOfBrith());
			beneficaryMaster.setYearOfBrith(getYearOfBrith());
			beneficaryMaster.setAge(getAge());

			if (getBenifisStateId() != null) {
				StateMaster stateMaster = new StateMaster();
				stateMaster.setStateId(getBenifisStateId());
				beneficaryMaster.setFsStateMaster(stateMaster);
				if(getStateName() != null){
					beneficaryMaster.setStateName(getStateName());
				}else{
					String statename = generalService.getStateName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId());
					if (statename != null) {
						beneficaryMaster.setStateName(statename);
					}
				}
			}

			if (getDistictId() != null) {
				DistrictMaster districtMaster = new DistrictMaster();
				districtMaster.setDistrictId(getDistictId());
				beneficaryMaster.setFsDistrictMaster(districtMaster);
				if(getDistrictName() != null){
					beneficaryMaster.setDistrictName(getDistrictName());
				}else{
					String districtname = generalService.getDistrictName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId(), getDistictId());
					if (districtname != null) {
						beneficaryMaster.setDistrictName(districtname);
					}
				}
			}

			if (getCityId() != null) {
				CityMaster cityMaster = new CityMaster();
				cityMaster.setCityId(getCityId());
				beneficaryMaster.setFsCityMaster(cityMaster);
				if(getCityName() != null){
					beneficaryMaster.setCityName(getCityName());
				}else{
					String cityname = generalService.getCityName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId(), getDistictId(), getCityId());
					if (cityname != null) {
						beneficaryMaster.setCityName(cityname);
					}
				}
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
			beneficaryMaster.setLocalFifthName(getFifthLName());
			if (getBeneficaryStatusName()!=null && getBeneficaryStatusName().equals(Constants.CUSTOMERTYPE_INDIVIDUAL)) {
				beneficaryMaster.setBeneficaryStatusName(Constants.Individual);
			} else {
				beneficaryMaster.setBeneficaryStatusName(Constants.NonIndividual);
			}
		}catch(Exception e){
			setErrmsg(e.getMessage());
			log.info("Warning Message beneficaryMasterSave1 for csp : " + getErrmsg());
			RequestContext.getCurrentInstance().execute("csp.show();");		
		}
		//getiPersonalRemittanceService().saveBeneficiaryMaster(beneficaryMaster);
		return beneficaryMaster;
	}

	// beneficiary save - already record exists in database
	private BeneficaryMaster beneficaryMasterSave(BeneficaryMaster masterseqId) {
		BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
		try{
			beneficaryMaster.setBeneficaryMasterSeqId(masterseqId.getBeneficaryMasterSeqId());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneficaryMaster.setApplicationCountryId(countryMaster);

			CountryMaster countryMaster1 = new CountryMaster();
			countryMaster1.setCountryId(getBeneCountryid());
			beneficaryMaster.setFsCountryMaster(countryMaster1);

			beneficaryMaster.setFirstName(getFirstName()==null?null:getFirstName().toUpperCase());
			beneficaryMaster.setSecondName(getSecondName()==null?null:getSecondName().toUpperCase());
			beneficaryMaster.setThirdName(getThirdName()==null?null:getThirdName().toUpperCase());
			beneficaryMaster.setFourthName(getFourthName()==null?null:getFourthName().toUpperCase());
			beneficaryMaster.setFifthName(getFifthName()==null?null:getFifthName().toUpperCase());

			beneficaryMaster.setLocalFirstName(getFirstLName());
			beneficaryMaster.setLocalSecondName(getSecondLName());
			beneficaryMaster.setLocalThirdName(getThirdLName());
			beneficaryMaster.setLocalFourthName(getFourthLName());
			beneficaryMaster.setLocalFifthName(getFifthLName());

			if(masterseqId.getBeneficaryStatus() != null){
				BeneficaryStatus beneficaryStatus = new BeneficaryStatus();
				beneficaryStatus.setBeneficaryStatusId(masterseqId.getBeneficaryStatus().getBeneficaryStatusId());
				beneficaryMaster.setBeneficaryStatus(beneficaryStatus);
			}

			beneficaryMaster.setNationality(getNationalityName().toString());
			beneficaryMaster.setDateOfBrith(getDateOfBrith());
			beneficaryMaster.setYearOfBrith(getYearOfBrith());
			beneficaryMaster.setAge(getAge());

			if (getBenifisStateId() != null) {
				StateMaster stateMaster = new StateMaster();
				stateMaster.setStateId(getBenifisStateId());
				beneficaryMaster.setFsStateMaster(stateMaster);
				if(getStateName() != null){
					beneficaryMaster.setStateName(getStateName());
				}else{
					String statename = generalService.getStateName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId());
					if (statename != null) {
						beneficaryMaster.setStateName(statename);
					}
				}
			}

			if (getDistictId() != null) {
				DistrictMaster districtMaster = new DistrictMaster();
				districtMaster.setDistrictId(getDistictId());
				beneficaryMaster.setFsDistrictMaster(districtMaster);
				if(getDistrictName() != null){
					beneficaryMaster.setDistrictName(getDistrictName());
				}else{
					String districtname = generalService.getDistrictName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId(), getDistictId());
					if (districtname != null) {
						beneficaryMaster.setDistrictName(districtname);
					}
				}
			}

			if (getCityId() != null) {
				CityMaster cityMaster = new CityMaster();
				cityMaster.setCityId(getCityId());
				beneficaryMaster.setFsCityMaster(cityMaster);
				if(getCityName() != null){
					beneficaryMaster.setCityName(getCityName());
				}else{
					String cityname = generalService.getCityName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId(), getDistictId(), getCityId());
					if (cityname != null) {
						beneficaryMaster.setCityName(cityname);
					}
				}
			}

			beneficaryMaster.setOccupation(getOccupation());
			beneficaryMaster.setNoOfRemittance(getNoOfRemittance());
			beneficaryMaster.setIsActive(Constants.Yes);
			beneficaryMaster.setCreatedBy(masterseqId.getCreatedBy());
			beneficaryMaster.setCreatedDate(masterseqId.getCreatedDate());
			beneficaryMaster.setModifiedBy(sessionmanage.getUserName());
			beneficaryMaster.setModifiedDate(new Date());

			if (getBeneficaryStatusName().equals(Constants.CUSTOMERTYPE_INDIVIDUAL)) {
				beneficaryMaster.setBeneficaryStatusName(Constants.Individual);
			} else {
				beneficaryMaster.setBeneficaryStatusName(Constants.NonIndividual);
			}

			beneficaryMaster.setBuildingNo(getBeneHouseNo());
			beneficaryMaster.setFlatNo(getBeneFlatNo());
			beneficaryMaster.setStreetNo(getBeneStreetNo());
		}catch(Exception e){
			setErrmsg(e.getMessage());
			log.info("Warning Message beneficaryMasterSave for csp : " + getErrmsg());
			RequestContext.getCurrentInstance().execute("csp.show();");		
		}

		//	getiPersonalRemittanceService().saveBeneficiaryMaster(beneficaryMaster);
		return beneficaryMaster;
	}

	// For Save Beneficiary Relation
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

	// For Save Beneficiary Telephone
	public BeneficaryContact saveBeneficaryTelephone(BeneficaryMaster beneficaryMaster) {
		List<BeneficaryAccount> beneficaryAccounts = getiPersonalRemittanceService().isBeneficaryAccountExistInDb(beneficaryMaster.getBeneficaryMasterSeqId());
		BeneficaryContact beneficaryTelaphone = new BeneficaryContact();
		if (beneficaryAccounts.size() > 0) {
			System.out.println("Condition in side ----------------------------------------------------- >3 ");
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneficaryTelaphone.setApplicationCountryId(countryMaster);
			if (getTelephoneNumber() != null) {
				beneficaryTelaphone.setTelephoneNumber(getTelephoneNumber().trim());
			} else {
				beneficaryTelaphone.setTelephoneNumber(null);
			}
			beneficaryTelaphone.setIsActive(Constants.Yes);
			beneficaryTelaphone.setCreatedBy(sessionmanage.getUserName());
			beneficaryTelaphone.setCreatedDate(new Date());
			beneficaryTelaphone.setBeneficaryMaster(beneficaryMaster);
			if(getCountryCode() != null && getCountryCode().length() != 0 && getCountryCode().length() <= 10){
				beneficaryTelaphone.setCountryTelCode(getCountryCode());
			}

			beneficaryTelaphone.setMobileNumber(getMobileNumber());
			System.out.println("Condition in side -----------------------------------------------------  before save 4");
			// getiPersonalRemittanceService().saveBeneficiaryTelephone(beneficaryTelaphone);
		} else {
			System.out.println("Beneficicary Telephone ---------------------------------- > ");
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneficaryTelaphone.setApplicationCountryId(countryMaster);
			if(getCountryCode() != null && getCountryCode().length() != 0 && getCountryCode().length() <= 10){
				beneficaryTelaphone.setCountryTelCode(getCountryCode());
			}
			if(getTelephoneNumber() != null) {
				beneficaryTelaphone.setTelephoneNumber(getTelephoneNumber().trim());
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
				beneficaryAccount.setRecordStaus("N");
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
				beneficaryAccount.setRecordStaus("N");
				beneficaryAccount.setBeneficaryMaster(beneficaryMaster);
				//getiPersonalRemittanceService().saveBeneficiaryAccount(beneficaryAccount);
			}
		}catch(Exception e){
			setErrmsg("This combination already exist in datatable");
			log.info("Warning Message for csp : " + getErrmsg());
			RequestContext.getCurrentInstance().execute("csp.show();");		
		}

		return beneficaryAccount;
	}

	// checking beneficiary Account table weather combination record exists
	public HashMap<String, Object> checkDuplicatesForCashBankingChannel(BigDecimal beneMasterSeqId) {

		Boolean status = false;
		BeneficaryAccount lstBeneAccRec = null;
		HashMap<String, Object> lstDuplicateAccCheck = new HashMap<String, Object>();

		if(lstfetchCashId == null || lstfetchCashId.isEmpty()){
			lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
		}

		if(lstfetchCashId != null && !lstfetchCashId.isEmpty()){
			if(getServiceGroupId() != null && getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0){
				// cash Product
				if(beneMasterSeqId != null){
					List<BeneficaryAccount> checkRecord = ibeneCountryServices.checkDuplicateForCash(beneMasterSeqId,getBenifisCountryId(),getServiceGroupId(),getServiceTypeId(),getBenifisCurrencyId(),getAgentMaster(),getAgentBranch());
					if(checkRecord != null && !checkRecord.isEmpty()){
						lstBeneAccRec = checkRecord.get(0);
						status = false;
					}else{
						status = true;
					}
				}else{
					status = true;
				}
			}else{
				// Banking Channel
				List<BeneficaryAccount> checkRecord = ibeneCountryServices.checkDuplicateForBankingChannel(beneMasterSeqId,getBenifisCountryId(),getServiceGroupId(),getBenifisBankId(),getServicebankBranchId(),getBankAccountNumber(),getBenifisCurrencyId());
				if(checkRecord != null && !checkRecord.isEmpty()){
					lstBeneAccRec = checkRecord.get(0);
					status = false;
				}else{
					status = true;
				}
			}
		}else{
			status = false;
		}

		// adding to map
		lstDuplicateAccCheck.put("Status", status);
		lstDuplicateAccCheck.put("BeneAccRec", lstBeneAccRec);

		return lstDuplicateAccCheck;
	}


	//-------Western Union Page only Beneficiary Master Page---------------------------------------------
	public void renderWesternUnionNavigation(){
		// clearing all fields
		clear();
		if(lstCountry!=null && lstCountry.isEmpty()){
			lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
		}
		// nationality from fs_country master
		if(nationalityList!=null && nationalityList.isEmpty()){
			nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
		}
		// benficiary country from fs_country master
		if(beneCountryList!=null && beneCountryList.isEmpty() && lstCountry != null && lstCountry.isEmpty()){
			beneCountryList = generalService.getCountryList(sessionmanage.getLanguageId());
		}else{
			beneCountryList = lstCountry;
		}

		setBooRenderWesterUnion(true);
		setBooRenderWesterUnionUpload(false);
		setBooRenderBeneficaryCreation(false);
		beneficiaryStatusList();
		benServiceCurrencyList();
		popStatelist();
	}
	
	public void renderWesternUnionUploadNavigation(){
		// clearing all fields
		clear();
		if(lstCountry!=null && lstCountry.isEmpty()){
			lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
		}
		// nationality from fs_country master
		if(nationalityList!=null && nationalityList.isEmpty()){
			nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
		}
		// benficiary country from fs_country master
		if(beneCountryList!=null && beneCountryList.isEmpty() && lstCountry != null && lstCountry.isEmpty()){
			beneCountryList = generalService.getCountryList(sessionmanage.getLanguageId());
		}else{
			beneCountryList = lstCountry;
		}

		setBooRenderWesterUnionUpload(true);
		setBooRenderWesterUnion(false);
		setBooRenderBeneficaryCreation(false);
		beneficiaryStatusList();
		benServiceCurrencyList();
		popStatelist();
	}

	public void benServiceCurrencyList() {
		beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(getBenifisCountryId());
	}


	//-------Edit Beneficiary only Beneficiary Account Page---------------------------------------------

	public void renderBeneficiaryAccountNavigation(){
		try {
			// clearing all fields
			clear();
			// beneficiary bank country
			if(lstCountry!=null && lstCountry.isEmpty()){
				lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
			}
			// service group
			if(serviceGroupMasterDescList!=null && serviceGroupMasterDescList.isEmpty()){
				serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());
			}
			beneficiaryStatusList();
			editBeneficaryFirstTime();
			secondAccountDetails();

			if(isWesternunionPanel()){
				setWesternunionPanel(false);
				setBooenableAgentPanel(true);
				setBooEnableBankChannelPanel(false);
			}

			//setDisabledServiceGroup(true);
			setSaveOrNextButton(Constants.Save);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// activate record to Y in beneficiary Relation Ship Table
	public void activateBeneficiaryAccount(){
		if(getActivatebeneficaryRelationshipId() != null){
			String status = Constants.Yes;
			//iPersonalRemittanceService.deleteBeneAccountRecord(getActivatebeneficaryRelationshipId(),status);
			iPersonalRemittanceService.deleteBeneAccountRecord(getBeneficaryAccountSeqId() ,getActivatebeneficaryRelationshipId(),status);

			BigDecimal beneAccountSeqid = null;

			if(getBeneficarymasterId() != null && getBenifisBankId() != null && getServicebankBranchId() != null && getBankAccountNumber() != null && getBenifisCurrencyId() != null){
				beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getBenifisBankId(),getServicebankBranchId(),getBankAccountNumber(),getBenifisCurrencyId());
			}else if(getBeneficarymasterId() != null && getAgentMaster() != null && getAgentBranch() != null && getBenifisCurrencyId() != null){
				//beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getAgentMaster(),getAgentBranch(),getBankAccountNumber(),getBenifisCurrencyId());
				beneAccountSeqid= beneficaryCreation.getbeneAccountSeqIdForCash(getBeneficarymasterId(),getAgentMaster(),getAgentBranch(),getBankAccountNumber(),getBenifisCurrencyId());
			}else{
				// not cash or banking channel
			}

			String errorMessage = null;
			if(getBenifisBankId() != null && getServicebankBranchId() != null){
				errorMessage = beneficaryCreation.getBeneDetailProce(getBeneficarymasterId(), getBenifisBankId(), getServicebankBranchId(), beneAccountSeqid, getBenifisCurrencyId(), getCustomerNo());
			}
			if (errorMessage == null) {
				RequestContext.getCurrentInstance().execute("beneficarycomplete.show();");
			} else {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("EX_POPULATE_BENE_DT " + errorMessage);
			}

		}else{
			// bene relation ship is null
			setErrmsg("Relation Ship is not Available");
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	// activate record to Y in beneficiary Relation Ship Table
	public void activateBeneficiaryAccountNO(){
		setBankAccountNumber(null);
	}

	// first time edit of beneficiary all details
	public void renderBeneficiaryFullEditNavigation(){
		// clearing all fields
		clear();
		if(nationalityList!=null && nationalityList.isEmpty()) {
			nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
		}
		if(beneCountryList!=null && beneCountryList.isEmpty() && lstCountry != null && lstCountry.isEmpty()){
			beneCountryList = generalService.getCountryList(sessionmanage.getLanguageId());
		}else{
			beneCountryList = lstCountry;
		}
		lstCountry = beneCountryList;

		editBeneficaryFirstTime();

		// alias name condition
		setBooAotherLang(false);
		for (BanksView bankMaster : lstBankFromView) {
			if (bankMaster.getBankId().compareTo(getBenifisBankId()) == 0) {
				if(!bankMaster.getLanguageInd().equals(Constants.ENGLISH_LANGUAGE_ID))
				{
					setBooAotherLang(true);
				}
				break;
			}
		}
	}

	// bank account number validation - account number length check and bank account available in database
	public void bankAccountNumberExistInDBForFirstEdit() {
		log.info("Entering into bankAccountNumberExistInDB method ");
		log.info("getBankAccountNumber " + getBankAccountNumber());
		log.info("getBenifisBankId " + getBenifisBankId());
		log.info("getBenifisCurrencyId  " + getBenifisCurrencyId());
		log.info("getBenifisCountryId  " + getBenifisCountryId());
		log.info("getServicebankBranchId  " + getServicebankBranchId());
		//setBeneficarymasterId(null);
		int accountLength = getBankAccountNumber().length();
		int i = 0;
		String lengthValues = "";
		List<BigDecimal> lstDuplicateCheck = new ArrayList<BigDecimal>();
		List<BankAccountLength> lstofAccLength = getiPersonalRemittanceService().getBankAccountLengthByBank(getBenifisBankId());
		if (lstofAccLength.size() != 0) {
			for (BankAccountLength bankAccountLength : lstofAccLength) {
				if ((bankAccountLength.getAcLength().compareTo(BigDecimal.ZERO)!=0) && bankAccountLength.getAcLength().compareTo(new BigDecimal(accountLength)) == 0) {
					i = 1;
					break;
				} else {
					if(!lstDuplicateCheck.contains(bankAccountLength.getAcLength()) && bankAccountLength.getAcLength().compareTo(BigDecimal.ZERO)!=0){
						lstDuplicateCheck.add(bankAccountLength.getAcLength());
						String commaAdd = "";
						if (!lengthValues.equalsIgnoreCase("")) {
							commaAdd = lengthValues + " OR ";
						}
						lengthValues = commaAdd + bankAccountLength.getAcLength().toPlainString();
						i = 2;
					}
				}
			}
		}

		if (i == 2) {
			setBankAccountNumber(null);
			setBankAccountLength(lengthValues);
			RequestContext.getCurrentInstance().execute("acclengthmismatch.show();");
		} else {
			try {
				// checking duplicates if master id will be empty here 		
				BigDecimal beneMasterId = null;
				HashMap<String, Object> checkDuplicates = checkDuplicatesForCashBankingChannel(beneMasterId);
				Boolean status = (Boolean)checkDuplicates.get("Status");
				BeneficaryAccount beneAcc = (BeneficaryAccount)checkDuplicates.get("BeneAccRec");
				System.out.println("Customer Id :" +getCustomerNo());
				if(status != null && !status){
					if(beneAcc != null){
						if(beneAcc.getBeneficaryAccountSeqId() != null && beneAcc.getBeneficaryMaster().getBeneficaryMasterSeqId() != null){
							// checking relationship and customer id matching or not
							//setBeneficarymasterId(beneAcc.getBeneficaryMaster().getBeneficaryMasterSeqId());
							List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices.checkBenificaryRelationExist(beneAcc.getBeneficaryMaster().getBeneficaryMasterSeqId(), beneAcc.getBeneficaryAccountSeqId());
							if(lstBeneRelationShip != null && !lstBeneRelationShip.isEmpty() && getCustomerNo() != null){
								if(getBeneficarymasterId() != null && getBeneficarymasterId().compareTo(beneAcc.getBeneficaryMaster().getBeneficaryMasterSeqId())!=0){
									// new Record for this customer but record exists for different customer
									setBeneficarymasterId(null);
									setErrmsg("This combination already exist for Different Beneficiary");
									RequestContext.getCurrentInstance().execute("csp.show();");
								}								
							}else{
								// Relationship coming empty - not possible
								System.out.println("Relationship coming empty - not possible");
							}
						}else{
							// beneficiary account Seq Id null - not possible
							System.out.println("beneficiary account Seq Id null - not possible");
						}
					}else{
						// beneficiary account null - not possible
						System.out.println(" beneficiary account null - not possible");
					}
				}else{
					// allow to modify Record with new details
				}
			} catch (Exception e) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg(e.getMessage());
				return;
			}
		}
	}
	
	// check beneficiary account type
	public boolean chkBeneficiaryAccountType(){

		boolean chkAccType = false;
		if(getBankAccountNumber() != null && getBenifisCountryId() != null && getBankAccountType() != null){
			int valueAvail = 0;
			List<AccountTypeFromView> lstAccType = beneficaryCreation.getAccountTypeFromView(getBenifisCountryId());
			if(lstAccType != null && lstAccType.size() != 0){
				for (AccountTypeFromView accountTypeFromView : lstAccType) {
					if(accountTypeFromView.getAdditionalAmiecId() != null && accountTypeFromView.getAdditionalAmiecId().compareTo(getBankAccountType())==0){
						valueAvail = 1;
						break;
					}
				}

				if(valueAvail == 1){
					chkAccType = true;
				}else{
					chkAccType = false;
				}
			}else{
				chkAccType = false;
			}
		}else{
			chkAccType = true;
		}

		return chkAccType;
	}
	
	// check beneficiary account type
	public boolean chkBeneficiaryAccountTypeToRemit(BenificiaryListView rel){

		boolean chkAccType = false;
		if(getBankAccountNumber() != null && rel.getBenificaryCountry() != null && rel.getBankAccountTypeId() != null){
			int valueAvail = 0;
			List<AccountTypeFromView> lstAccType = beneficaryCreation.getAccountTypeFromView(rel.getBenificaryCountry());
			if(lstAccType != null && lstAccType.size() != 0){
				for (AccountTypeFromView accountTypeFromView : lstAccType) {
					if(accountTypeFromView.getAdditionalAmiecId() != null && accountTypeFromView.getAdditionalAmiecId().compareTo(rel.getBankAccountTypeId())==0){
						valueAvail = 1;
						break;
					}
				}

				if(valueAvail == 1){
					chkAccType = true;
				}else{
					chkAccType = false;
				}
			}else{
				chkAccType = false;
			}
		}else{
			chkAccType = true;
		}

		return chkAccType;
	}

	// update beneficiary modification
	public void updateBeneficaryData() {
		setAgeFocus(false);
		
		// bank account type check
		boolean chkAccTypeValue = chkBeneficiaryAccountType();
		if(!chkAccTypeValue){
			log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getBenifisCountryId());
			log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getBankAccountType());
			log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getCustomerId());
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Account Type Mismatch");
			return;
		}

		if (getTelephoneNumber() == null && getMobileNumber() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter mobile number or Telephone number");
			return;
		}

		if (getTelephoneNumber() != null  && (getCountryCode()==null || getCountryCode()!=null &&  getCountryCode().length() == 0)) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please select Telephone Country Code");
			return;
		}

		if (getMobileNumber() != null  && (getMcountryCode()==null || getMcountryCode()!=null &&  getMcountryCode().length() == 0)) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please select Mobile Country Code");
			return;
		}
		
		/*if(getAge() == null && getDateOfBrith() == null && getYearOfBrith() == null)
		{
			setErrmsg("Age or Date of Birth or Year of Birth any one mandatory!!!");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}*/

		if(getBankBranchName()==null)
		{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please select Branch");
			return;
		}

		if (getCountryCode() == null && getMcountryCode() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter mobile number Code or Telephone number Code");
			return;
		}

		if ((getCountryCode()!= null && getCountryCode().length() == 0) || (getMcountryCode()!=null &&  getMcountryCode().length() == 0)) {
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
				if (contactDetails.getTelephoneNumber() != null && getTelephoneNumberSelect() != null && contactDetails.getTelephoneNumber().equals(getTelephoneNumberSelect())) {
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
				if (contactDetails.getMobileNumber() != null && getMobileNumberSelect() != null && contactDetails.getMobileNumber().equals(getMobileNumberSelect())) {
					removeMobileContact = contactDetails;
				}
			}
		}

		if(removeMobileContact!=null)
		{
			lstMobileDetails.remove(removeMobileContact);
			beneficaryCreation.deActivateMultipleMobileNumbers(lstMobileDetails);
		}

		// beneficiary master
		BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
		beneficaryMaster.setBeneficaryMasterSeqId(getBeneficarymasterId());
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(getApplicationCountryId());
		beneficaryMaster.setApplicationCountryId(countryMaster);
		CountryMaster countryMaster1 = new CountryMaster();
		countryMaster1.setCountryId(getBeneCountryid());
		beneficaryMaster.setFsCountryMaster(countryMaster1);
		beneficaryMaster.setFirstName(getFirstName()==null ? null : getFirstName().toUpperCase());
		beneficaryMaster.setSecondName(getSecondName()==null ? null : getSecondName().toUpperCase());
		beneficaryMaster.setThirdName(getThirdName()==null ? null : getThirdName().toUpperCase());
		beneficaryMaster.setFourthName(getFourthName()==null ? null : getFourthName().toUpperCase());
		beneficaryMaster.setFifthName(getFifthName()==null ? null : getFifthName().toUpperCase());

		beneficaryMaster.setLocalFirstName(getFirstLName());
		beneficaryMaster.setLocalSecondName(getSecondLName());
		beneficaryMaster.setLocalThirdName(getThirdLName());
		beneficaryMaster.setLocalFourthName(getFourthLName());
		beneficaryMaster.setLocalFifthName(getFifthLName());
		if(getBeneficaryStatusId() != null){
			BeneficaryStatus beneficaryStatus = new BeneficaryStatus();
			beneficaryStatus.setBeneficaryStatusId(getBeneficaryStatusId());
			beneficaryMaster.setBeneficaryStatus(beneficaryStatus);
		}

		beneficaryMaster.setNationality(getNationalityName().toString());
		beneficaryMaster.setDateOfBrith(getDateOfBrith());
		beneficaryMaster.setYearOfBrith(getYearOfBrith());
		beneficaryMaster.setAge(getAge());

		if (getBenifisStateId() != null) {
			StateMaster stateMaster = new StateMaster();
			stateMaster.setStateId(getBenifisStateId());
			beneficaryMaster.setFsStateMaster(stateMaster);
			if(getStateName() != null){
				beneficaryMaster.setStateName(getStateName());
			}else{
				String statename = generalService.getStateName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId());
				if (statename != null) {
					beneficaryMaster.setStateName(statename);
				}
			}
		}

		if (getDistictId() != null) {
			DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(getDistictId());
			beneficaryMaster.setFsDistrictMaster(districtMaster);
			if(getDistrictName() != null){
				beneficaryMaster.setDistrictName(getDistrictName());
			}else{
				String districtname = generalService.getDistrictName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId(), getDistictId());
				if (districtname != null) {
					beneficaryMaster.setDistrictName(districtname);
				}
			}
		}

		if (getCityId() != null) {
			CityMaster cityMaster = new CityMaster();
			cityMaster.setCityId(getCityId());
			beneficaryMaster.setFsCityMaster(cityMaster);
			if(getCityName() != null){
				beneficaryMaster.setCityName(getCityName());
			}else{
				String cityname = generalService.getCityName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId(), getDistictId(), getCityId());
				if (cityname != null) {
					beneficaryMaster.setCityName(cityname);
				}
			}
		}

		beneficaryMaster.setOccupation(getOccupation());
		beneficaryMaster.setNoOfRemittance(getNoOfRemittance());
		beneficaryMaster.setIsActive(Constants.Yes);
		beneficaryMaster.setCreatedBy(getMasterCreatedBy());
		beneficaryMaster.setCreatedDate(getMasterCreatedDate());
		beneficaryMaster.setModifiedBy(sessionmanage.getUserName());
		beneficaryMaster.setModifiedDate(new Date());
		beneficaryMaster.setBeneficaryStatusName(getBeniStatusName());
		
		beneficaryMaster.setBuildingNo(getBeneHouseNo());
		beneficaryMaster.setFlatNo(getBeneFlatNo());
		beneficaryMaster.setStreetNo(getBeneStreetNo());
		
		
		// beneficiary account
		BeneficaryAccount account = new BeneficaryAccount();
		account.setBeneficaryAccountSeqId(getBeneficaryAccountSeqId());
		account.setBeneApplicationCountry(countryMaster);
		CountryMaster countryMaster2 = new CountryMaster();
		countryMaster2.setCountryId(getBenifisCountryId());
		account.setBeneficaryCountry(countryMaster2);
		if(getBenifisBankId() != null){
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getBenifisBankId());
			account.setBank(bankMaster);
		}else if(getAgentMaster() != null){
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getAgentMaster());
			account.setBank(bankMaster);
		}
		if(getServicebankBranchId() != null){
			BankBranch bankBranch = new BankBranch();
			bankBranch.setBankBranchId(getServicebankBranchId());
			account.setBankBranch(bankBranch);
		}else if(getAgentBranch() != null){
			BankBranch bankBranch = new BankBranch();
			bankBranch.setBankBranchId(getAgentBranch());
			account.setBankBranch(bankBranch);
		}

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
		//account.setBankBranchCode(bankBranch.getBranchCode());
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
		if(getAccountCreatedBy() != null && getAccountCreatedDate() != null){
			account.setCreatedBy(getAccountCreatedBy());
			account.setCreatedDate(getAccountCreatedDate());
		}else{
			account.setCreatedBy(getMasterCreatedBy());
			account.setCreatedDate(getMasterCreatedDate());
		}
		
		account.setModifiedBy(sessionmanage.getUserName());
		account.setModifiedDate(new Date());
		account.setLastJavaRemittence(getLastJavaRemittence());
		account.setRecordStaus(getRecordStaus());
		if (getServiceTypeId() != null) {
			BankMaster bankMasterServiceProvider = new BankMaster();
			bankMasterServiceProvider.setBankId(getServiceTypeId());
			account.setServiceProvider(bankMasterServiceProvider);
			if(getServiceProviderBankBranchId() != null){
				account.setServiceProviderBranchId(getServiceProviderBankBranchId());
			}
		}
		account.setBeneficaryMaster(beneficaryMaster);
		
		// beneficiary contact
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
		if(getBeneficarymasterId() != null && getBeneficaryAccountSeqId() != null){
			List<BeneficaryRelationship> lstBeneCnt = iPersonalRemittanceService.isBenificaryRelationExist(getBeneficarymasterId(), getBeneficaryAccountSeqId());
			if(lstBeneCnt != null && !lstBeneCnt.isEmpty()){
				BeneficaryRelationship beneRel = lstBeneCnt.get(0);
				if(beneRel.getCreatedBy() != null && beneRel.getCreatedDate() != null){
					beneficaryRelationship.setCreatedBy(beneRel.getCreatedBy());
					beneficaryRelationship.setCreatedDate(beneRel.getCreatedDate());
				}else{
					beneficaryRelationship.setCreatedBy(getMasterCreatedBy());
					beneficaryRelationship.setCreatedDate(getMasterCreatedDate());
				}
			}else{
				beneficaryRelationship.setCreatedBy(getMasterCreatedBy());
				beneficaryRelationship.setCreatedDate(getMasterCreatedDate());
			}
		}
		
		beneficaryRelationship.setModifiedBy(sessionmanage.getUserName());
		beneficaryRelationship.setModifiedDate(new Date());
		beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
		beneficaryRelationship.setBeneficaryAccount(account);
		if(getMapSequenceId()!=null){
			beneficaryRelationship.setMapSequenceId(getMapSequenceId());
		}
		
		// beneficiary Contact
		BeneficaryContact contact = new BeneficaryContact();
		contact.setBeneficaryTelephoneSeqId(getBeneficaryTelephoneSeqId());
		contact.setApplicationCountryId(countryMaster);
		if (getTelephoneNumber() != null) {
			contact.setTelephoneNumber(getTelephoneNumber().trim());
		} else {
			contact.setTelephoneNumber(null);
		}
		contact.setIsActive(Constants.Yes);
		
		if(getContactCreatedBy() != null && getContactCreatedDate() != null){
			contact.setCreatedBy(getContactCreatedBy());
			contact.setCreatedDate(getContactCreatedDate());
		}else if(getBeneficaryTelephoneSeqId() != null ) {
			List<BeneficaryContact> lstBeneCnt = beneficaryCreation.getTelephoneDetails(getBeneficaryTelephoneSeqId());
			
			if(lstBeneCnt != null && !lstBeneCnt.isEmpty()){
				BeneficaryContact beneContact = lstBeneCnt.get(0);
				if(beneContact.getCreatedBy() != null && beneContact.getCreatedDate() != null ){
					contact.setCreatedBy(beneContact.getCreatedBy());
					contact.setCreatedDate(beneContact.getCreatedDate());
				}else{
					contact.setCreatedBy(getMasterCreatedBy());
					contact.setCreatedDate(getMasterCreatedDate());
				}
			}
		}
		
		contact.setModifiedBy(sessionmanage.getUserName());
		contact.setModifiedDate(new Date());
		
		contact.setBeneficaryMaster(beneficaryMaster);
		if(getCountryCode() != null && getCountryCode().length() != 0 && getCountryCode().length() <= 10 ){
			contact.setCountryTelCode(getCountryCode());
		}

		contact.setMobileNumber(getMobileNumber());
		try {
			
			String errMsg = checkBeneficiaryDetailsWithProc();
			
			if(errMsg != null){
				setErrmsg(errMsg);
				log.info("Warning Message for csp : " + getErrmsg());
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}else{
				// proceed to save
				getiPersonalRemittanceService().updateBeneficaryData(beneficaryMaster, account, beneficaryRelationship, contact);
				
				String errorMessage = null;
				if(getBenifisBankId() != null && getServicebankBranchId() != null){
					errorMessage = beneficaryCreation.getBeneDetailProce(getBeneficarymasterId(),getBenifisBankId(),getServicebankBranchId(),getBeneficaryAccountSeqId(),getBenifisCurrencyId(),getCustomerNo());
				}else{
					errorMessage = beneficaryCreation.getBeneDetailProce(getBeneficarymasterId(), getAgentMaster(), getAgentBranch(), getBeneficaryAccountSeqId(), getBenifisCurrencyId(), getCustomerNo());
				}

				if(errorMessage==null){
					
					if(isBooRenderWesterUnion()){
						RequestContext.getCurrentInstance().execute("beneficarycompleteWU.show();");
					} else if(isBooRenderWesterUnionUpload()){
						RequestContext.getCurrentInstance().execute("beneficarycompleteWUupload.show();");
					} else{
						RequestContext.getCurrentInstance().execute("beneficarycomplete.show();");
					}
					
					
				}else{
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg("EX_POPULATE_BENE_DT "+errorMessage);
					return;
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Exception occured while update the Benificary" + e.getMessage());
			return;
		}
	}

	// beneficiary with different service and different account page
	public void editBeneficaryFirstTime(){
		if (getDataTableBeneObj() != null) {
			try {
				PersonalRemmitanceBeneficaryDataTable dataTableBene = getDataTableBeneObj();
				setEnableSearchPage(null);
				editBeneficarayPageFirstTime(dataTableBene);
			} catch (Exception e) {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				try {
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("error", e.getMessage());
					//context.redirect("../remittance/PersonalRemittance.xhtml");
					PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
					objectPersonalRemittance.personalRemittanceBackFromBene(null);
					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
				} catch (Exception e1) {
					System.out.println("Exception occured" + e1);
				}
			}
		}
	}

	// fetch all beneficiary details from dataTable object
	public void editBeneficarayPageFirstTime(PersonalRemmitanceBeneficaryDataTable datatabledetails){
		
		try{
			log.info("Entering into edit method ");
			log.info(datatabledetails);
			log.info(datatabledetails.getBeneficaryMasterSeqId());

			// clearing first and second panels
			clear();
			clearSearch();

			setBeneficarymasterId(datatabledetails.getBeneficaryMasterSeqId());

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
			
			if(datatabledetails.getFifthNameLocal() != null) {
				setFifthLName(datatabledetails.getFifthNameLocal());
			}
			else
			{
				setFifthLName(null);
			}
			

			/** added by Rabil on 03/12/2015 for EX_P_BANNED_NAME_CHECK*/ 
			String engName =getFirstName()+" "+ getSecondName()+" "+getThirdName();
			String localName =getFirstLName()+" "+ getSecondLName()+" "+getThirdLName();

			String proceErrorMessage = getiPersonalRemittanceService().getBannedNameCheckProcedure(sessionmanage.getCountryId(), engName,localName);

			if(proceErrorMessage!=null && proceErrorMessage.length()>0){
				//throw new Exception(proceErrorMessage);
				setErrmsg(proceErrorMessage);
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
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
			if(datatabledetails.getMapSequenceId()!=null){
				setMapSequenceId(datatabledetails.getMapSequenceId());
			}

			// nationality 
			if(datatabledetails.getNationality() != null){
				setNationalityName(new BigDecimal(datatabledetails.getNationality()));
			}

			// beneficiary Contact details
			List<BeneficaryContact> contactDetails = beneficaryCreation.getTelephoneDetails(datatabledetails.getBeneficaryMasterSeqId());		
			if (contactDetails != null && !contactDetails.isEmpty()) {
				
				BeneficaryContact benecontact = contactDetails.get(0);
				if(benecontact.getCreatedBy() != null && benecontact.getCreatedDate() != null){
					setContactCreatedBy(benecontact.getCreatedBy());
					setContactCreatedDate(benecontact.getCreatedDate());
				}else{
					setContactCreatedBy(datatabledetails.getCreatedBy());
					setContactCreatedDate(datatabledetails.getCreatedDate());
				}
				
				List<CountryMasterDesc> countryList =beneficaryCreation.checkCountryCode(benecontact.getCountryTelCode());
				if(countryList!= null && !countryList.isEmpty())
				{
					if(countryList.size() >1)
					{
						//throw new Exception("Setup Error - Duplicate Tel Country Code exist "+ contactDetails.get(0).getCountryTelCode());
						setErrmsg("Setup Error - Duplicate Tel Country Code exist "+ benecontact.getCountryTelCode());
						RequestContext.getCurrentInstance().execute("csp.show();");
					}
				}

				if(benecontact.getCountryTelCode() != null){
					setCountryCode(benecontact.getCountryTelCode());
					setMcountryCode(benecontact.getCountryTelCode());
				}else{
					List<CountryMasterDesc> countryListForContactCode = beneficaryCreation.fetchCountryContactCode(datatabledetails.getCountryId());
					if(countryListForContactCode.size() != 0){
						setCountryCode(countryListForContactCode.get(0).getFsCountryMaster().getCountryTelCode());
						setMcountryCode(countryListForContactCode.get(0).getFsCountryMaster().getCountryTelCode());
					}
				}

				setBeneficaryTelephoneSeqId(benecontact.getBeneficaryTelephoneSeqId());
				if (contactDetails.get(0).getTelephoneNumber() != null) {
					setTelephoneNumber(benecontact.getTelephoneNumber().trim());
				} else {
					setTelephoneNumber(null);
				}
				if (contactDetails.get(0).getMobileNumber() != null) {
					setMobileNumber(benecontact.getMobileNumber());
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
			setAge(datatabledetails.getAge());
			
			if(getAge() == null && getDateOfBrith() == null && getYearOfBrith() == null)
			{
				setReadOnlyDateOfBirth(false);
				setReadOnlyYearOfBirth(false);
				setReadOnlyAge(false);
				setDisableDataOfBirth(false);
				setDisableResetDataOfBirth(false);
			}
			
			setOccupation(datatabledetails.getOccupation());

			// bank country Id and Name
			setBenifisCountryId(datatabledetails.getCountryId());
			if(getBenifisCountryId() != null ){
				lstBankCountrystate = generalService.getStateList(sessionmanage.getLanguageId(), getBenifisCountryId());
			}
			benServiceCurrencyList();
			setBenifisCountryName(datatabledetails.getCountryName());
			//setCountryName(datatabledetails.getCountryName());
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
			setBeneficaryBankName(datatabledetails.getBankName());
			setBankName(datatabledetails.getBankName());
			setBankCode(datatabledetails.getBankCode());
			setServicebankBranchId(datatabledetails.getBranchId());
			setBankBranchName(datatabledetails.getBankBranchName());
			setBankBranchCode(datatabledetails.getBranchCode());

			setBenifisCurrencyId(datatabledetails.getCurrencyId());

			BankBranchView lstBankbranchView = beneficaryCreation.getBranchListfromView(getBenifisBankId(),getServicebankBranchId());

			String type = "";
			List<BeneficaryAccount> accountList = null;
			if (datatabledetails.getBankAccountNumber() != null) {
				//accountList = beneficaryCreation.getCustomerBeneficaryDetailswithAccountNO(datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getBankAccountNumber(), type);
				accountList = beneficaryCreation.getbeneAccountDetailsWithAccNo(datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getBankId(), datatabledetails.getBranchId(), datatabledetails.getBankAccountNumber(), datatabledetails.getCurrencyId(), datatabledetails.getBeneficiaryAccountSeqId());
			} else {
				//accountList = iPersonalRemittanceService.getCustomerBeneficaryDetailswithAll(datatabledetails.getBeneficaryMasterSeqId(), type);
				accountList = beneficaryCreation.getbeneAccountDetailsForCashWU(datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getBankId(), datatabledetails.getBranchId(), datatabledetails.getCurrencyId(), datatabledetails.getBeneficiaryAccountSeqId(),datatabledetails.getServiceProvider());
			}
			if (accountList != null && !accountList.isEmpty()) {

				for (BeneficaryAccount beneficaryAccount : accountList) {

					if(beneficaryAccount.getBank().getBankId().compareTo(datatabledetails.getBankId())==0){
						if(beneficaryAccount.getSwiftCode() != null)
						{
							setBeneSwiftCode(beneficaryAccount.getSwiftCode());
						}
						if(beneficaryAccount.getBankAccountTypeId() != null) {
							setBankAccountType(beneficaryAccount.getBankAccountTypeId());
						}
						
						if(beneficaryAccount.getCreatedBy() != null && beneficaryAccount.getCreatedDate() != null){
							setAccountCreatedBy(beneficaryAccount.getCreatedBy());
							setAccountCreatedDate(beneficaryAccount.getCreatedDate());
						}else{
							setAccountCreatedBy(datatabledetails.getCreatedBy());
							setAccountCreatedDate(datatabledetails.getCreatedDate());
						}
						
						serviceGroupIdforCash = iPersonalRemittanceService.getserviceGroupforCash(sessionmanage.getLanguageId());
						if (serviceGroupIdforCash != null && serviceGroupIdforCash.equals(getServiceGroupId())) {

							if(datatabledetails.getBankCode() != null && datatabledetails.getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
								// setting western union bank id , branch Id and currency
								setWesternunionPanel(true);
								setBooenableAgentPanel(false);
								setBooEnableBankChannelPanel(false);

								List<BankMaster> bankMasterdetails = ibankMasterService.getBankMasterInfo(Constants.WU_BANK_CODE);
								if(bankMasterdetails != null && bankMasterdetails.size() != 0){

									BankMaster bankMasterId = bankMasterdetails.get(0);

									if(bankMasterId.getBankId() != null){
										setAgentMaster(bankMasterId.getBankId());  // setting bankID western union
										setWesternunionBank(bankMasterId.getBankFullName());
										setBankCode(bankMasterId.getBankCode());

										List<BankBranch> bankBranchdetails = bankBranchDetailsService.getBranchCodebyBank(bankMasterId.getBankId());

										if(bankBranchdetails != null && bankBranchdetails.size() != 0){

											BankBranch bankBranchId = bankBranchdetails.get(0);

											if(bankBranchId.getBankBranchId() != null){
												setAgentBranch(bankBranchId.getBankBranchId()); // western union bank branch id
												setWesternunionBranch(bankBranchId.getBranchFullName());
												setBankBranchCode(bankBranchId.getBranchCode());
											}
										}
									}
								}

							}else{
								setWesternunionPanel(false);
								setBooenableAgentPanel(true);
								setBooEnableBankChannelPanel(false);

								fetchAllBeneServiceProvider();
								fetchAllAgentsMasterBanks();
								setAgentMaster(datatabledetails.getBankId());
								popAgentBranch();
								if(datatabledetails.getServiceProvider()!=null)
								{
									setAgentBranch(datatabledetails.getBranchId());
									setServiceTypeId(datatabledetails.getServiceProvider());
									setBankBranchName(datatabledetails.getBankBranchName());
								}

								if(lstAgentsBanks != null && !lstAgentsBanks.isEmpty() && datatabledetails.getBankId() != null && datatabledetails.getBankCode()==null){
									for (ViewRoutingAgents banksview : lstAgentsBanks) {
										if(banksview.getAgentBankId().compareTo(datatabledetails.getBankId())==0){
											setBankCode(banksview.getAgentBankCode());
											break;
										}else{
											setBankCode(null);
										}
									}
								}

								if(lstAgentLocationForCash != null && !lstAgentLocationForCash.isEmpty() && datatabledetails.getBranchId() != null && datatabledetails.getBranchCode()==null){
									for (ViewRoutingAgentLocations banksBranchview : lstAgentLocationForCash) {
										if(banksBranchview.getBankBranchId().compareTo(datatabledetails.getBranchId())==0){
											setBankBranchCode(banksBranchview.getBranchCode());
											break;
										}else{
											setBankBranchCode(null);
										}
									}
								}

								if(lstAgentLocationForCash != null && !lstAgentLocationForCash.isEmpty() && datatabledetails.getBranchId() != null){
									for (ViewRoutingAgentLocations banksBranchview : lstAgentLocationForCash) {
										if(banksBranchview.getBankBranchId().compareTo(datatabledetails.getBranchId())==0){
											setServiceProviderBankBranchId(banksBranchview.getRoutingBranchId());
											break;
										}else{
											setServiceProviderBankBranchId(null);
										}
									}
								}
							}

						}else {
							setWesternunionPanel(false);
							setBooenableAgentPanel(false);
							setBooEnableBankChannelPanel(true);

							fetchAllBeneServiceBanks();

							if(lstBankFromView != null && !lstBankFromView.isEmpty() && datatabledetails.getBankId() != null && datatabledetails.getBankCode()==null){
								for (BanksView banksview : lstBankFromView) {
									if(banksview.getBankId().compareTo(datatabledetails.getBankId())==0){
										setBankCode(banksview.getBankCode());
										break;
									}else{
										setBankCode(null);
									}
								}
							}

							if(lstBankbranchView != null && datatabledetails.getBranchId() != null && datatabledetails.getBranchCode()==null){
								setBankBranchCode(lstBankbranchView.getBranchCode());
							}

						}
					}

				}
				setAliasFirstName(accountList.get(0).getAliasFirstName());
				setAliasSecondName(accountList.get(0).getAliasSecondName());
				setAliasThirdName(accountList.get(0).getAliasThirdName());
				setAliasFourthName(accountList.get(0).getAliasFourthName());
				setLastJavaRemittence(accountList.get(0).getLastJavaRemittence());
				setRecordStaus(accountList.get(0).getRecordStaus());
			}

			serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());

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

			if(lstBankbranchView != null){
				setBeneficaryBankState(lstBankbranchView.getStateName());
				setBeneficaryBankDistrict(lstBankbranchView.getDistrictName());
				setBeneficaryBankCity(lstBankbranchView.getCityName());
			}

			setTempAccountNumber(datatabledetails.getBankAccountNumber());
			setBankAccountNumber(datatabledetails.getBankAccountNumber());
			setBeneficaryAccountSeqId(datatabledetails.getBeneficiaryAccountSeqId());

			//added By koti @ 11/08/2016
			setBeneAddressDetails(datatabledetails.getBeneAddressDetails());
			setBeneHouseNo(datatabledetails.getBeneHouseNo());
			setBeneFlatNo(datatabledetails.getBeneFlatNo());
			setBeneStreetNo(datatabledetails.getBeneStreetNo());
			
			lstDataTableBankbranch.clear();
			log.info("Exit into edit method ");
		}catch(Exception e){
			log.info("editBeneficarayPageFirstTime() : "+e.getMessage());
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
		//setMcountryCode(null);
		setBoorenderBenemaster(false);
	}

	// Clearing Second Panel
	public void secondClear() {
		setServiceGroupId(null);
		setServiceTypeId(null);
		setBenifisCountryId(null);
		setBenifisCountryName(null);
		//setBenifisCurrencyId(null);
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
		setFifthLName(null);
		//setCountryName(null);
		setBankName(null);
		setBankBranchName(null);
		// setOccupation(null);
		setBeneficaryBankState(null);
		setBeneficaryBankDistrict(null);
		setBeneficaryBankCity(null);
		setProviance(null);
		setServiceTypeId(null);
		lstBank.clear();
		setCurrencyName(null);
		lstBankbranch.clear();
		//lstBankbranchView.clear();
		//lststate.clear();
		lstDistict.clear();
		lstCity.clear();
		serviceGroupMasterDescList.clear();
		setBeneficaryAccountSeqId(null);
		// bank list from view 
		if(lstBankFromView != null || !lstBankFromView.isEmpty()){
			lstBankFromView.clear();
		}
		if(lstBankbranchView != null || !lstBankbranchView.isEmpty()){
			lstBankbranchView.clear();
		}
		//lstRoutingDetails.clear();
		if(agentAndCorrespondingBankslst != null || !agentAndCorrespondingBankslst.isEmpty()){
			agentAndCorrespondingBankslst.clear();
		}

		setAgentMaster(null);
		setAgentMasterBankName(null);
		setAgentBranch(null);
		setServiceTypeId(null);
		if(agentAndCorrespondingBankBranchlst != null || !agentAndCorrespondingBankBranchlst.isEmpty()){
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
		setBeneNationalaityName(null);
		setBeneRelationshipName(null);

		setLastJavaRemittence(null);
		setRecordStaus(null);
		setBooDisableAccountType(null);
		setMapSequenceId(null);
		setBeneAddressDetails(null);
		setBeneHouseNo(null);
		setBeneFlatNo(null);
		setBeneStreetNo(null);
	}

	// Clearing Second Panel
	public void secondAccountDetails() {
		setServiceTypeId(null);
		setBenifisCurrencyId(null);
		setBenifisBankId(null);
		setSearchBankStateId(null);
		setServicebankBranchId(null);
		setBooDisbleChoseeBranchButton(true);
		setBankAccountNumber(null);
		setBankAccountType(null);
		setBeneSwiftCode(null);
		setAliasFirstName(null);
		setAliasSecondName(null);
		setAliasThirdName(null);
		setAliasFourthName(null);
		setBeneficaryBankState(null);
		setBeneficaryBankDistrict(null);
		setBeneficaryBankCity(null);
		setProviance(null);
		setAgentMaster(null);
		setAgentMasterBankName(null);
		setAgentBranch(null);
		setTelephoneNumberSelect(null);
		setMobileNumberSelect(null);
		setBeneNationalaityName(null);
		setBeneRelationshipName(null);
		setLastJavaRemittence(null);
		setRecordStaus(null);
		setBooDisableAccountType(null);
		setMapSequenceId(null);
	}


	public void popbranchlist() {
		popCurrencylistBank();
		lstDataTableBankbranch.clear();
		if(lstBankbranchView != null || !lstBankbranchView.isEmpty()){
			lstBankbranchView.clear();
		}

		setBooAotherLang(false);

		List<BankBranchView> lstBankbranchView = new ArrayList<BankBranchView>();
		if (getSearchBankStateId()!=null && getSearchBankStateId().equals(new BigDecimal(0))) {
			lstBankbranchView = beneficaryCreation.getBranchListfromViewwithStateMissing(getBenifisBankId());
		} else {
			lstBankbranchView = beneficaryCreation.getBranchListfromViewfromState(getSearchBankStateId(), getBenifisBankId());
		}
		if (lstBankbranchView.size() > 0) {
			lstBankbranchView.addAll(lstBankbranchView);
		}

		for (BanksView bankMaster : lstBankFromView) {

			if (bankMaster.getBankId().compareTo(getBenifisBankId()) == 0) {
				setBankCode(bankMaster.getBankCode());
				setBeneficaryBankName(bankMaster.getBankFullName());

				if(!bankMaster.getLanguageInd().equals(Constants.ENGLISH_LANGUAGE_ID))
				{
					setBooAotherLang(true);
					/*if(getFirstLName()==null || getFirstLName()!=null && getFirstLName().length()==0)
					{
						RequestContext.getCurrentInstance().execute("localLanaguageMisssing.show();");
						setErrmsg(" Need to enter Local Language's Beneficary names for this bank ");
						return;

					}*/
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
		//populateSwiftCode();
		//populateBankAccountType();
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
		//populateSwiftCode();
		//populateBankAccountType();
		//popStatelist();
		BranchDataTable datatable = null;
		for (BankBranchView bankBranch : lstBankbranchView) {
			datatable = new BranchDataTable();
			if(bankBranch.getBankBranchId() != null){
				datatable.setBankBranchId(bankBranch.getBankBranchId());
				datatable.setBankBranchCode(bankBranch.getBranchCode());
			}
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
		//populateSwiftCode();
		//populateBankAccountType();

		if (getSearchBankStateId()!=null && getSearchBankStateId().equals(new BigDecimal(0))) {
			lstBankbranchView = beneficaryCreation.getBranchListfromViewwithStateMissing(getBenifisBankId());
		} else {
			lstBankbranchView = beneficaryCreation.getBranchListfromViewfromState(getSearchBankStateId(), getBenifisBankId());
		}

		BranchDataTable datatable = null;

		for(BankBranchView bankBranch : lstBankbranchView) {

			datatable = new BranchDataTable();
			if(bankBranch.getBankBranchId() != null){
				datatable.setBankBranchId(bankBranch.getBankBranchId());
				datatable.setBankBranchCode(bankBranch.getBranchCode());
			}
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




	/*public List<StateMasterDesc> getLstBankstateList() {
		return lstBankstateList;
	}
	public void setLstBankstateList(List<StateMasterDesc> lstBankstateList) {
		this.lstBankstateList = lstBankstateList;
	}*/

	public List<CustomerBank> getLocalBankListinCollection() {
		return localBankListinCollection;
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
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

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

		clearCustomerDetails();

		try{
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
				setCustomerLocalFullName(nullCheck(customerDetails.getFsCustomer().getFirstNameLocal()) + " " + nullCheck(customerDetails.getFsCustomer().getMiddleNameLocal()) + " " + nullCheck(customerDetails.getFsCustomer().getLastNameLocal()));
				setCustomerIsActive(customerDetails.getFsCustomer().getIsActive());
				setCustomerExpDate(customerDetails.getIdentityExpiryDate());
				setCustomerTypeId(customerDetails.getFsBizComponentDataByCustomerTypeId().getComponentDataId());
				String customerTypeString = iPersonalRemittanceService.getCustomerType(getCustomerTypeId());
				if (customerTypeString != null) {
					setCustomerType(customerTypeString);
				}
				if (getCustomerExpDate() != null) {
					setCustomerExpireDateMsg(new SimpleDateFormat("dd/MM/yyyy").format(getCustomerExpDate()));
				}
				setNationality(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
				setNationalityName(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
				setDateOfBrith(customerDetails.getFsCustomer().getDateOfBirth());

				setBeneCountryid(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
				
				checkingmandatoryOptional();
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
				if(customerDetails.getFsCustomer().getEmail()!=null)
				{
					setCustomerEmail(customerDetails.getFsCustomer().getEmail());
				}

			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		log.info("Exit into getCustomerDetails method ");
	}

	// clear All Customer Details
	public void clearCustomerDetails(){
		setCustomerName(null);
		setCustomerCrNumber(null);
		setCustomerNo(null);
		setCustomerrefno(null);
		setFirstName(null);
		setSecondName(null);
		setThirdName(null);
		setCustomerFullName(null);
		setCustomerLocalFullName(null);
		setCustomerIsActive(null);
		setCustomerExpDate(null);
		setCustomerTypeId(null);
		setCustomerType(null);
		setCustomerExpireDateMsg(null);
		setNationality(null);
		setNationalityName(null);
		setDateOfBrith(null);
		setCountryCode(null);
		setMcountryCode(null);
		setOccupation(null);
		setCustomerEmail(null);
	}

	public void exitFromBeneficaryCreation() {
		clear();
		setRenderBackButton(true);
		setBooRenderIndBenificaryStatusPanel(false);
		setBeneficaryStatusId(null);
		try {
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("exitBeneficary", "yes");
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

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
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

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
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

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
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

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
		setBooRenderWesterUnion(false);
		setBooRenderWesterUnionUpload(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiaryCreation.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nextBooRenderIndBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(true);
		try {
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

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
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

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

	public void getCustomerForOthers(){

		if (customerDetailsList.size() != 0) {
			CustomerIdProof customerDetails = customerDetailsList.get(0);
			setNationality(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			setNationalityName(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			String teleCountryId = generalService.getTelephoneCountryBasedOnNationality(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			if(teleCountryId != null){
				setCountryCode(teleCountryId);
				setMcountryCode(teleCountryId);
			}
			setBeneCountryid(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			// only for western union set Beneficiary Bank Country
			/*if(isBooRenderWesterUnion()){
				setBenifisCountryId(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			}*/
			
			// populate states list - 14/03/2017
			//popStatelist();
		}

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

	public void backTobeneficiaryDetailsfromDifferentService() {
		try {
			log.info("Page redirect to bankacccountdetails page");

			setBooRenderWesterUnion(false);
			setBooRenderWesterUnionUpload(false);

			if(getBooAotherLang()==null || getBooAotherLang()) {
				setBenifisCountryId(null);
				setServiceGroupId(null);
				setBooenableAgentPanel(true);
			}else{
				// different service or account
				setBooRenderWesterUnion(false);
				setBooRenderWesterUnionUpload(false);
				setBooRenderBeneficaryCreation(true);
			}
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromDifferntService", "yes");
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
			setBooAotherLang(false);
			setDisableSaveBack(false);
			log.info("Page redirect to bankacccountdetails page");
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	public void backToBenificaryListScreens() {
		setAgeFocus(false);
		try {
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(false);
			setDisableSaveBack(false);
			log.info("Page redirect to bankacccountdetails page");
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);
			objectPersonalRemittance.setBeneficiaryCountryId(objectPersonalRemittance.getNationality());
			objectPersonalRemittance.populateCustomerDetailsFromBeneRelation();

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
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
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// to get All Currency From CurrencyMaster
	public List<CurrencyMaster> getCurrencyListFromDB() {
		return generalService.getCurrencyList();
	}

	int count = 0;

	public void checktelephoneNumberExist() {
		if (getTelephoneNumber() != null && !getTelephoneNumber().equalsIgnoreCase("")) {
			boolean checkDecimal = false;
			try {
				checkDecimal = CollectionUtil.isIntegerValue(new BigDecimal(getTelephoneNumber().trim()));
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
			if (getTelephoneNumber() != null && !getTelephoneNumber().equalsIgnoreCase("")) {
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
		if(getBankAccountType()!=null)
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
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clearAccountField() {
		setBankAccountNumber(null);
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
		setBooRenderBenificarySearchPanel(true);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(false);
	}

	public List<ServiceGroupMasterDesc> getServiceGroupMasterDescList() {
		return serviceGroupMasterDescList;
	}
	public void setServiceGroupMasterDescList(List<ServiceGroupMasterDesc> serviceGroupMasterDescList) {
		this.serviceGroupMasterDescList = serviceGroupMasterDescList;
	}

	List<ServiceGroupMasterDesc> lstfetchCashId;


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

	/*public void checkDuplicates() {
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
	}*/

	/*public Boolean checkDuplicates(BigDecimal beneMasterSeqId) {

		Boolean status = false;

		if(lstfetchCashId == null || lstfetchCashId.isEmpty()){
			lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
		}

		if(lstfetchCashId != null && !lstfetchCashId.isEmpty()){
			//setServiceProviderBankBranchId(getAgentBranch());
			if(getServiceGroupId() != null && getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0){
				// cash Product
				List<BeneficaryAccount> checkRecord = ibeneCountryServices.checkDuplicateForCash(beneMasterSeqId , getBenifisCountryId(),getServiceGroupId(),getServiceTypeId(),getBenifisCurrencyId(),getAgentMaster(),getAgentBranch());
				if(checkRecord){
					setAgentBranch(null);
					status = false;
				}else{
					status = true;
				}
			}else{
				// Banking Channel
				List<BeneficaryAccount> checkRecord = ibeneCountryServices.checkDuplicateForBankingChannel(beneMasterSeqId, getBenifisCountryId(),getServiceGroupId(),getBenifisBankId(),getServicebankBranchId(),getBankAccountNumber(),getBenifisCurrencyId());
				if(checkRecord){
					status = false;
				}else{
					status = true;
				}
			}
		}else{
			status = false;
		}
		return status;
	}*/

	public HashMap<String, Object> checkDuplicatesforWesternUnion(BigDecimal beneMasterSeqId) {

		Boolean status = false;
		BeneficaryAccount lstBeneAccRec = null;
		HashMap<String, Object> lstDuplicateAccCheck = new HashMap<String, Object>();

		// checking western union records
		if(getBankCode() != null && getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
			// cash Product - Western Union
			List<BeneficaryAccount> lstBene = westernUnionService.getBeneficaryAccountDetails(beneMasterSeqId,Constants.WU_BANK_CODE,getBenifisCountryId(),getBenifisCurrencyId());
			if(lstBene != null && lstBene.size() != 0){
				status = false;
				lstBeneAccRec = lstBene.get(0);
			}else{
				status = true;
			}
		}else{
			status = true;
		}
		// adding to map
		lstDuplicateAccCheck.put("Status", status);
		lstDuplicateAccCheck.put("BeneAccRec", lstBeneAccRec);

		return lstDuplicateAccCheck;
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

		//String year = String.valueOf(new Date().getYear()).substring(1, 3);
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String year =String.valueOf(calendar.get(Calendar.YEAR));

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

	public Boolean getMainPanelRender() {
		return mainPanelRender;
	}

	public void setMainPanelRender(Boolean mainPanelRender) {
		this.mainPanelRender = mainPanelRender;
	}

	public void checkingEditBacktoRemittance(){
		setAgeFocus(false);
		RequestContext.getCurrentInstance().execute("backcheckforRemit.show();");
	}

	public void backtoPersonalRemit() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			clearEditFields();
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);
			objectPersonalRemittance.setBeneficiaryCountryId(objectPersonalRemittance.getNationality());
			objectPersonalRemittance.populateCustomerDetailsFromBeneRelation();

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	private void clearEditFields() {
		//setBeneMatsterSeqId(null);
		setBeneficarymasterId(null);
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
		setBenifisCountryName(null);
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
		setBeneficaryBankName(null);
		setAccountCreatedBy(null);
		setAccountCreatedDate(null);
		setBankBranchCode(null);
		setBeneficaryAccountSeqId(null);
		setBeniStatusName(null);
		setBeneficaryTypeId(null);
		setBeneAddressDetails(null);
		setBeneHouseNo(null);
		setBeneFlatNo(null);
		setBeneStreetNo(null);
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
		setAgeFocus(false);
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
		//setBenifisCurrencyId(null);
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

	/*public void populateBenificaryDetails() {
		if (firstTime == null) {

			lstDataTableBankbranch.clear();

			List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.SELF);

			SELFRELATIONID  = selfid.get(0).getRelations().getRelationsId();

			boolean differntService = true;

			if(coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()){
				coustomerBeneficaryDTList.clear();
			}

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
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerRef") != null) {
					setCustomerrefno(new BigDecimal(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerRef").toString()));
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pcustomerRef");
				}
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerName") != null) {
					setCustomerFullName(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerName").toString());
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pcustomerName");
				}
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dataTable") != null) {
					@SuppressWarnings("unchecked")
					List<PersonalRemmitanceBeneficaryDataTable> coustomerDTList = (List<PersonalRemmitanceBeneficaryDataTable>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dataTable");
					coustomerBeneficaryDTList.addAll(coustomerDTList);
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("dataTable");
				}

				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromWU") != null) {

					String fromDifferntService =(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromWU");
					if(fromDifferntService.equalsIgnoreCase(Constants.Yes))
					{
						setBooRenderWesterUnion(true);
						setBooRenderBeneficaryCreation(false);
					}else{
						setBooRenderWesterUnion(false);
						setBooRenderBeneficaryCreation(true);
					}
				}else{
					setBooRenderWesterUnion(false);
					setBooRenderBeneficaryCreation(true);
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
				}else{
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
			if(differntService){
				beneficiaryStatusList();
			}
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

					if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromWU") != null) {

						String fromDifferntService =(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromWU");
						if(fromDifferntService.equalsIgnoreCase(Constants.Yes))
						{
							setBooRenderWesterUnion(true);
							setBooRenderBeneficaryCreation(false);
						}else{
							setBooRenderWesterUnion(false);
							setBooRenderBeneficaryCreation(true);
						}
					}else{
						setBooRenderWesterUnion(false);
						setBooRenderBeneficaryCreation(true);
					}

				} catch (Exception e) {
					log.info("Exception occured while gettting id number from personal remittence screen");
				}
			}
		}
	}*/


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

	/*public void populateBenificaryAccountDetails() {

		if(lstCountry!=null && lstCountry.isEmpty()){
			lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
		}

		if(serviceGroupMasterDescList!=null && serviceGroupMasterDescList.isEmpty()){
			serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());
		}

		if(lstfetchCashId==null || (lstfetchCashId!=null && lstfetchCashId.isEmpty()))
		{
			lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
		}

		ExternalContext context	= FacesContext.getCurrentInstance().getExternalContext();
		String fromPersonalRemittance =(String) context.getSessionMap().get("fromPersonalRemittanceDifferntAccount");
		String fromPersonalRemittanceDifferntService =(String) context.getSessionMap().get("fromPersonalRemittanceDifferntService");

		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerRef") != null) {
			setCustomerrefno(new BigDecimal(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerRef").toString()));
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pcustomerRef");
		}
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerName") != null) {
			setCustomerFullName(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pcustomerName").toString());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pcustomerName");
		}

		if(fromPersonalRemittance!=null && fromPersonalRemittance.equals("yes"))
		{
			BigDecimal actualCustomerNo = (BigDecimal) context.getSessionMap().get("customerNumber");

			PersonalRemmitanceBeneficaryDataTable datatabledetails = (PersonalRemmitanceBeneficaryDataTable)context.getSessionMap().get("datatabledetail");

			try {
				editBeneficarayPageFirstTime(datatabledetails);
				if(isWesternunionPanel()){
					setBooenableAgentPanel(true);
					setWesternunionPanel(false);
					fetchAllBeneServiceProvider();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			setDisabledServiceGroup(true);
			setDisableSaveButton(true);
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
			clearSearch();
			setBooDisbleChoseeBranchButton(true);
			setAgentMaster(null);
			setAgentMasterBankName(null);
			setAgentBranch(null);
			setBankCode(null);
			setBeneficaryBankName(null);
			setBankBranchCode(null);
			// clear cash product
			if(agentAndCorrespondingBankslst != null || !agentAndCorrespondingBankslst.isEmpty()){
				agentAndCorrespondingBankslst.clear();
			}
			if(agentAndCorrespondingBankBranchlst != null || !agentAndCorrespondingBankBranchlst.isEmpty()){
				agentAndCorrespondingBankBranchlst.clear();
			}

		}

		if(fromPersonalRemittanceDifferntService!=null && fromPersonalRemittanceDifferntService.equals("yes"))
		{
			BigDecimal actualCustomerNo = (BigDecimal) context.getSessionMap().get("customerNumber");
			PersonalRemmitanceBeneficaryDataTable datatabledetails = (PersonalRemmitanceBeneficaryDataTable)context.getSessionMap().get("datatabledetail");

			try {
				editBeneficarayPageFirstTime(datatabledetails);
				if(isWesternunionPanel()){
					setBooenableAgentPanel(true);
					setWesternunionPanel(false);
					fetchAllBeneServiceProvider();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			setDisabledServiceGroup(false);
			context.getSessionMap().remove("fromPersonalRemittanceDifferntService");
			context.getSessionMap().remove("datatabledetail");
			context.getSessionMap().remove("customerNumber");
			setDisableSaveButton(true);
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
			clearSearch();
			setBooDisbleChoseeBranchButton(true);
			setAgentMaster(null);
			setAgentMasterBankName(null);
			setAgentBranch(null);
			// clear cash product
			if(agentAndCorrespondingBankslst != null || !agentAndCorrespondingBankslst.isEmpty()){
				agentAndCorrespondingBankslst.clear();
			}
			if(agentAndCorrespondingBankBranchlst != null || !agentAndCorrespondingBankBranchlst.isEmpty()){
				agentAndCorrespondingBankBranchlst.clear();
			}

		}
	}*/

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

	public List<ViewBeneServiceCurrency> getBeneServiceCurrencyList() {
		return beneServiceCurrencyList;
	}

	public void setBeneServiceCurrencyList(List<ViewBeneServiceCurrency> beneServiceCurrencyList) {
		this.beneServiceCurrencyList = beneServiceCurrencyList;
	}	

	public void editBeneficary() throws Exception {
		// nationality from fs_country master
		if(nationalityList!=null && nationalityList.isEmpty()){
			nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
		}
		// benficiary country from fs_country master
		if(beneCountryList!=null && beneCountryList.isEmpty() && lstCountry != null && lstCountry.isEmpty()){
			beneCountryList = generalService.getCountryList(sessionmanage.getLanguageId());
		}else{
			beneCountryList = lstCountry;
		}
		lstCountry = beneCountryList;
		//populateBenificaryAccountDetails();
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("datatabledetails") != null) {
			PersonalRemmitanceBeneficaryDataTable datatabledetails = (PersonalRemmitanceBeneficaryDataTable) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("datatabledetails");
			try {
				//editBeneficarayPage(datatabledetails);
				editBeneficarayPageFirstTime(datatabledetails);
			} catch (Exception e) {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				try {
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("error", e.getMessage());
					PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
					objectPersonalRemittance.personalRemittanceBackFromBene(null);

					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
				} catch (Exception e1) {
					System.out.println("Exception occured" + e1);
				}
			}
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("datatabledetails");
		}
	}

	public void differentBankDetailsSave()
	{

		try {
			BeneficaryMaster beneficaryMaster =null;
			BeneficaryContact contact = null;
			List<BeneficaryMaster> masterList = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
			if (masterList != null && !masterList.isEmpty()) {
				beneficaryMaster = masterList.get(0);
			}
			else
			{
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Exception occured while create the Benificary");
				return;
			}

			// checking duplicates if master id is old 							
			if(getBeneficarymasterId() != null){
				HashMap<String, Object> checkDuplicates = checkDuplicatesForCashBankingChannel(getBeneficarymasterId());
				Boolean status = (Boolean)checkDuplicates.get("Status");
				BeneficaryAccount beneAcc = (BeneficaryAccount)checkDuplicates.get("BeneAccRec");
				System.out.println("Customer Id :" +getCustomerNo());
				if(status != null && !status){
					if(beneAcc != null){
						if(beneAcc.getBeneficaryAccountSeqId() != null){
							// checking relationship and customer id matching or not
							List<BeneficaryRelationship> lstBeneRelationShip = iPersonalRemittanceService.isBenificaryRelationExist(getBeneficarymasterId(), beneAcc.getBeneficaryAccountSeqId());
							if(lstBeneRelationShip != null && !lstBeneRelationShip.isEmpty() && getCustomerNo() != null){
								Boolean checkStatus = false;
								for (BeneficaryRelationship beneficaryRelationship : lstBeneRelationShip) {
									checkStatus = false;
									if(beneficaryRelationship.getCustomerId().getCustomerId().compareTo(getCustomerNo())==0){
										checkStatus = true;
										break;
									}else{
										checkStatus = false;
									}
								}
								if(checkStatus){
									// duplicates exists - Beneficiary Account checking MasterSeqId,BankCode,BankCountryId,currencyId 
									setErrmsg("This combination already exist in datatable");
									RequestContext.getCurrentInstance().execute("csp.show();");
									return;
								}
							}
						}
					}
				}
			}

			BeneficaryAccount beneficaryAccountSave = saveBeneficaryAccount(beneficaryMaster);
			BeneficaryRelationship beneficaryRelationShip = saveBeneficaryRelation(beneficaryMaster, beneficaryAccountSave);

			beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccountSave, beneficaryRelationShip);

			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficarySave", "yes");
			String errorMessage = null;
			if(getBenifisBankId() != null && getServicebankBranchId() != null){
				errorMessage = beneficaryCreation.getBeneDetailProce(getBeneficarymasterId(),getBenifisBankId(),getServicebankBranchId(),beneficaryAccountSave.getBeneficaryAccountSeqId(),getBenifisCurrencyId(),getCustomerNo());
			}else{
				errorMessage = beneficaryCreation.getBeneDetailProce(getBeneficarymasterId(), getAgentMaster(), getAgentBranch(), beneficaryAccountSave.getBeneficaryAccountSeqId(), getBenifisCurrencyId(), getCustomerNo());
			}

			if(errorMessage==null){
				RequestContext.getCurrentInstance().execute("beneficarycomplete.show();");
			}else{
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("EX_POPULATE_BENE_DT "+errorMessage);
				return;
			}

		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg(e.getMessage());
			return;
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

	private String beneSwiftCode;

	public String getBeneSwiftCode() {
		return beneSwiftCode;
	}

	public void setBeneSwiftCode(String beneSwiftCode) {
		this.beneSwiftCode = beneSwiftCode;
	}

	// original edit facility
	/*public void editBeneficarayPage(PersonalRemmitanceBeneficaryDataTable datatabledetails)  throws Exception{

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

		// Age Calculation
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(new Date());
		cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
		Date today18 = cal1.getTime();
		SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
		String minDateFinal = form1.format(today18);
		setEffectiveMinDate(minDateFinal);

		// relationship list fetching
		//relationDescList = getiPersonalRemittanceService().getRelationsDescriptionList(sessionmanage.getLanguageId());
		setRelationId(datatabledetails.getRelationShipId());
		if(datatabledetails.getMapSequenceId()!=null){
			setMapSequenceId(datatabledetails.getMapSequenceId());
		}

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

			if(contactDetails.get(0).getCountryTelCode() != null){
				setCountryCode(contactDetails.get(0).getCountryTelCode());
				setMcountryCode(contactDetails.get(0).getCountryTelCode());
			}else{
				List<CountryMasterDesc> countryListForContactCode = beneficaryCreation.fetchCountryContactCode(datatabledetails.getCountryId());
				if(countryListForContactCode.size() != 0){
					setCountryCode(countryListForContactCode.get(0).getFsCountryMaster().getCountryTelCode());
					setMcountryCode(countryListForContactCode.get(0).getFsCountryMaster().getCountryTelCode());
				}
			}

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
		setCurrencyName(datatabledetails.getCurrencyQuoteName() + " - " +datatabledetails.getCurrencyName());
		setBeneNationalaityName(datatabledetails.getNationalityName());
		setBeneRelationshipName(datatabledetails.getRelationShipName());

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

			for (BeneficaryAccount beneficaryAccount : accountList) {

				if(beneficaryAccount.getBank().getBankId().compareTo(datatabledetails.getBankId())==0){
					if(beneficaryAccount.getSwiftCode() != null)
					{
						setBeneSwiftCode(beneficaryAccount.getSwiftCode());
					}
					if(beneficaryAccount.getBankAccountTypeId() != null) {
						setBankAccountType(beneficaryAccount.getBankAccountTypeId());
					}
					serviceGroupIdforCash = iPersonalRemittanceService.getserviceGroupforCash(sessionmanage.getLanguageId());
					if (serviceGroupIdforCash != null && serviceGroupIdforCash.equals(getServiceGroupId())) {
						setBooenableAgentPanel(false);
						fetchAllBeneServiceProvider();
						fetchAllAgentsMasterBanks();
						setAgentMaster(datatabledetails.getBankId());
						popAgentBranch();
						if(datatabledetails.getServiceProvider()!=null)
						{
							setAgentBranch(datatabledetails.getBranchId());
							setServiceTypeId(datatabledetails.getServiceProvider());
							setBankBranchName(datatabledetails.getBankBranchName());
						}
						if(lstAgentsBanks != null && !lstAgentsBanks.isEmpty() && datatabledetails.getBankId() != null && datatabledetails.getBankCode()==null){
							for (ViewRoutingAgents banksview : lstAgentsBanks) {
								if(banksview.getAgentBankId().compareTo(datatabledetails.getBankId())==0){
									setBankCode(banksview.getAgentBankCode());
									break;
								}
							}
						}

						if(lstAgentLocationForCash != null && !lstAgentLocationForCash.isEmpty() && datatabledetails.getBranchId() != null && datatabledetails.getBranchCode()==null){
							for (ViewRoutingAgentLocations banksBranchview : lstAgentLocationForCash) {
								if(banksBranchview.getBankBranchId().compareTo(datatabledetails.getBranchId())==0){
									setBankBranchCode(banksBranchview.getBranchCode());
									break;
								}
							}
						}
					}else {
						fetchAllBeneServiceBanks();

						if(lstBankFromView != null && !lstBankFromView.isEmpty() && datatabledetails.getBankId() != null && datatabledetails.getBankCode()==null){
							for (BanksView banksview : lstBankFromView) {
								if(banksview.getBankId().compareTo(datatabledetails.getBankId())==0){
									setBankCode(banksview.getBankCode());
									break;
								}
							}
						}

						if(lstAgentLocationForCash != null && !lstAgentLocationForCash.isEmpty() && datatabledetails.getBranchId() != null && datatabledetails.getBranchCode()==null){
							for (ViewRoutingAgentLocations banksBranchview : lstAgentLocationForCash) {
								if(banksBranchview.getBankBranchId().compareTo(datatabledetails.getBranchId())==0){
									setBankBranchCode(banksBranchview.getBranchCode());
									break;
								}
							}
						}

						setBooenableAgentPanel(true);
					}
				}

			}
			setAliasFirstName(accountList.get(0).getAliasFirstName());
			setAliasSecondName(accountList.get(0).getAliasSecondName());
			setAliasThirdName(accountList.get(0).getAliasThirdName());
			setAliasFourthName(accountList.get(0).getAliasFourthName());
			setLastJavaRemittence(accountList.get(0).getLastJavaRemittence());
			setRecordStaus(accountList.get(0).getRecordStaus());
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
	}*/


	Boolean booDisableAccountType;
	private String recordStaus;
	private Date lastJavaRemittence;




	public String getRecordStaus() {
		return recordStaus;
	}
	public void setRecordStaus(String recordStaus) {
		this.recordStaus = recordStaus;
	}
	public Date getLastJavaRemittence() {
		return lastJavaRemittence;
	}
	public void setLastJavaRemittence(Date lastJavaRemittence) {
		this.lastJavaRemittence = lastJavaRemittence;
	}

	public Boolean getBooDisableAccountType() {
		return booDisableAccountType;
	}
	public void setBooDisableAccountType(Boolean booDisableAccountType) {
		this.booDisableAccountType = booDisableAccountType;
	}

	private String bankBranchFullName;

	public String getBankBranchFullName() {
		return bankBranchFullName;
	}
	public void setBankBranchFullName(String bankBranchFullName) {
		this.bankBranchFullName = bankBranchFullName;
	}
	public void selectBranchfromViewWindow(BranchDataTable branchDataTable) {
		System.out.println("Entering into selectBranchfromViewWindow method");
		setEnableDatatable(false);
		setEnableSearchPage(false);
		setBeneficaryBankState(null);
		setBeneficaryBankDistrict(null);
		setBeneficaryBankCity(null);
		setBeneSwiftCode(null);
		System.out.println(branchDataTable);
		setServicebankBranchId(branchDataTable.getBankBranchId());
		setBankBranchCode(branchDataTable.getBankBranchCode());
		setBankBranchFullName(branchDataTable.getBankFullName());
		setBankAccountNumber(null);
		//populateSwiftCode();
		if (branchDataTable.getStateName() != null) {
			setBeneficaryBankState(branchDataTable.getStateName());
		}
		if (branchDataTable.getDistrictName() != null) {
			setBeneficaryBankDistrict(branchDataTable.getDistrictName());
		}
		if (branchDataTable.getCityName() != null) {
			setBeneficaryBankCity(branchDataTable.getCityName());
		}
		if(branchDataTable.getSwiftCode() != null){
			setBeneSwiftCode(branchDataTable.getSwiftCode());
		}

		try {
			
			if(lstBankAccountTypeFromView != null || !lstBankAccountTypeFromView.isEmpty()){
				lstBankAccountTypeFromView.clear();
			}
			
			lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBenifisCountryId());
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
			if(serviceProviderAndCorrespondingBankslst != null || !serviceProviderAndCorrespondingBankslst.isEmpty()){
				serviceProviderAndCorrespondingBankslst.clear();
			}
		}

		benServiceCurrencyList();
	}


	public void fetchAllBeneServiceBanks(){
		if(lstBankFromView != null || !lstBankFromView.isEmpty()){
			lstBankFromView.clear();
		}

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
			lstDataTableBankbranch.clear();
			//setBooenableAgentPanel(false);
			setWesternunionPanel(false);
			setBooenableAgentPanel(true);
			setBooEnableBankChannelPanel(false);
		}else{
			//setBooenableAgentPanel(true);
			setWesternunionPanel(false);
			setBooenableAgentPanel(false);
			setBooEnableBankChannelPanel(true);

			popbranchlist();
		}
	}

	public void gotoRemittanceservice() {
		try {
			// editBene is false only new records, old records or new service , new account editBene is true. date check
			Boolean editBene = true;
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(false);
			setDisableSaveBack(false);
			PersonalRemmitanceBeneficaryDataTable personalRBDataTable = null;
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");

			BigDecimal beneAccountSeqid = null;

			if(getBeneficarymasterId() != null && getBenifisBankId() != null && getServicebankBranchId() != null && getBankAccountNumber() != null && getBenifisCurrencyId() != null){
				beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getBenifisBankId(),getServicebankBranchId(),getBankAccountNumber(),getBenifisCurrencyId());
			}else if(getBeneficarymasterId() != null && getAgentMaster() != null && getAgentBranch() != null && getBenifisCurrencyId() != null){
				//beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getAgentMaster(),getAgentBranch(),getBankAccountNumber(),getBenifisCurrencyId());
				beneAccountSeqid= beneficaryCreation.getbeneAccountSeqIdForCash(getBeneficarymasterId(),getAgentMaster(),getAgentBranch(),getBankAccountNumber(),getBenifisCurrencyId());
			}else{
				// not cash or banking channel
			}
			
			if(beneAccountSeqid != null){
				
				List<BenificiaryListView> isCoustomerExist = beneficaryCreation.fetchBeneficiaryNewRecord(getCustomerNo(), getBeneficarymasterId(), beneAccountSeqid,editBene,getBenifisCurrencyId(),getServiceGroupId());

				if(isCoustomerExist.size() != 0){

					BenificiaryListView rel = isCoustomerExist.get(0);
					
					// chk account type
					// bank account type check
					boolean chkAccTypeValue = chkBeneficiaryAccountTypeToRemit(rel);
					if(!chkAccTypeValue){
						log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getBenifisCountryId());
						log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getBankAccountType());
						log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getCustomerId());
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg("Account Type Mismatch");
						return;
					}

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
					personalRBDataTable.setStateId(rel.getStateId());
					personalRBDataTable.setDistrictId(rel.getDistrictId());
					personalRBDataTable.setCityId(rel.getCityId());
					personalRBDataTable.setMapSequenceId(rel.getMapSequenceId());
					personalRBDataTable.setBankAccountTypeId(rel.getBankAccountTypeId());

					List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"),rel.getServiceGroupId());
					if (lstServiceGroupMasterDesc.size() > 0) {
						personalRBDataTable.setServiceGroupName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
					}

					/*String telePhone = beneficaryCreation.getTelePhoneNumberString(rel.getBeneficaryMasterSeqId());
					if (telePhone != null) {
						personalRBDataTable.setTelphoneNum(telePhone);
					}*/

					List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(rel.getBeneficaryMasterSeqId());

					System.out.println("telePhone :"+telePhone.toString());
					if (telePhone != null && telePhone.size() != 0) {
						String telNumber = null;
						if(telePhone.size() == 1){
							if(telePhone.get(0).getTelephoneNumber()!=null){
								telNumber = telePhone.get(0).getTelephoneNumber();
							}else if(telePhone.get(0).getMobileNumber() !=null){
								telNumber = telePhone.get(0).getMobileNumber().toPlainString();
							}else{
								telNumber = null;
							}
							personalRBDataTable.setTelphoneNum(telNumber);
							personalRBDataTable.setTelphoneCode(telePhone.get(0).getCountryTelCode());
							personalRBDataTable.setBeneficiaryContactSeqId(telePhone.get(0).getBeneficaryTelephoneSeqId());
						}else{
							BeneficaryContact beneficaryContact = telePhone.get(0);
							if(beneficaryContact.getTelephoneNumber()!=null){
								telNumber = beneficaryContact.getTelephoneNumber();
							}else if(beneficaryContact.getMobileNumber()!=null){
								telNumber = beneficaryContact.getMobileNumber().toPlainString();
							}else{
								telNumber = null;
							}
							personalRBDataTable.setTelphoneNum(telNumber);
							personalRBDataTable.setTelphoneCode(beneficaryContact.getCountryTelCode());
							personalRBDataTable.setBeneficiaryContactSeqId(beneficaryContact.getBeneficaryTelephoneSeqId());
						}
					}

					personalRBDataTable.setThirdName(rel.getThirdName());
					personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
					personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());
					
					//Bene Details Added @koti 10/08/2016
					StringBuffer strAdd=new StringBuffer();
					if(rel.getStateName() != null){
						strAdd.append(rel.getStateName());
					}
					if(rel.getDistrictName() != null){
						strAdd.append(","+rel.getDistrictName());
					}
					if (rel.getCityName() != null) {
						strAdd.append(","+rel.getCityName());
					}
					if(strAdd != null){
						personalRBDataTable.setBeneAddressDetails(strAdd.toString());
					}
					
					personalRBDataTable.setBeneHouseNo(rel.getBuildingNo());
					personalRBDataTable.setBeneFlatNo(rel.getFlatNo());
					personalRBDataTable.setBeneStreetNo(rel.getStreetNo());

					System.out.println("***************************************************************************");
					System.out.println(personalRBDataTable);
					System.out.println("***************************************************************************");
					log.info("Page redirect to bankacccountdetails page");
					
					if(personalRBDataTable != null && !personalRBDataTable.getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
						// check routing setup
						HashMap<String, String> inputRoutingBankSetUpdetails = new HashMap<String, String>();

						inputRoutingBankSetUpdetails.put("P_APPLICATION_COUNTRY_ID", sessionmanage.getCountryId().toPlainString());
						inputRoutingBankSetUpdetails.put("P_BENE_COUNTRY_ID", personalRBDataTable.getCountryId().toPlainString()); // beneficiary bank Country Id
						inputRoutingBankSetUpdetails.put("P_BENE_BANK_ID", personalRBDataTable.getBankId().toPlainString());
						inputRoutingBankSetUpdetails.put("P_BENE_BANK_BRANCH_ID",personalRBDataTable.getBranchId().toPlainString());

						List<ServiceGroupMasterDesc> lstServiceCode = serviceGroupMasterService.getServiceGroupDescList(personalRBDataTable.getServiceGroupId());
						if (lstServiceCode != null) {
							ServiceGroupMasterDesc ServiceCode = lstServiceCode.get(0);
							inputRoutingBankSetUpdetails.put("P_SERVICE_GROUP_CODE", ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
						}

						inputRoutingBankSetUpdetails.put("P_CURRENCY_ID", personalRBDataTable.getCurrencyId().toPlainString());
						if( (sessionmanage.getBranchId()!=null || sessionmanage.getCustomerType().equals("E"))){ // && sessionStateManage.getRoleId().equals("1")
							inputRoutingBankSetUpdetails.put("P_USER_TYPE","BRANCH");
							//setBooSingleService(true);
						}else if(sessionmanage.getBranchId()!=null  &&  sessionmanage.getUserType().equalsIgnoreCase("K")){
							inputRoutingBankSetUpdetails.put("P_USER_TYPE","KIOSK");
							//setBooMultipleService(false);
						}else{
							//setBooMultipleService(false);
						}
						
						HashMap<String, String> outputRoutingBankSetUpdetails = iPersonalRemittanceService.getRoutingBankSetupDetails(inputRoutingBankSetUpdetails);

						System.out.println("P_ERROR_MESSAGE"+outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
						System.out.println("output RoutingBankSetUpdetails : "+outputRoutingBankSetUpdetails.toString());

						if (outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE") != null) {
							setErrmsg("EX_GET_ROUTING_SET_UP" + " : " +outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
							RequestContext.getCurrentInstance().execute("servicenotAvailable.show();");
							return;
						} else {
							
							String proceValiMessage =iPersonalRemittanceService.getValidateBeneficiaryProcedure(sessionmanage.getCountryId(), getCustomerNo(), sessionmanage.getUserName(), getBeneficarymasterId(), personalRBDataTable.getBeneficiaryAccountSeqId());

							if(proceValiMessage!=null && proceValiMessage.length()>0){
								setErrmsg(" EX_P_VALIDATE_BENEFICIARY : "+proceValiMessage);
								RequestContext.getCurrentInstance().execute("servicenotAvailable.show();");
								return;
							}else{
								if(personalRBDataTable.getBankId() != null){
									
									//setBeneficaryBankId(personalRBDataTable.getBankId());

									HashMap<String ,String> bannedBankProcedureOut = iPersonalRemittanceService.getBannedBankCheckProcedure(sessionmanage.getCountryId(),personalRBDataTable.getBankId(),getBeneficarymasterId());

									if(bannedBankProcedureOut.get("P_ERROR_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ERROR_MESSAGE").equals("")){
										System.out.println("P_error_message :"+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
										setErrmsg("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
										RequestContext.getCurrentInstance().execute("servicenotAvailable.show();");
										return;
									}else if(bannedBankProcedureOut.get("P_ALERT_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ALERT_MESSAGE").equals("")){
										System.out.println("P_ALERT_MESSAGE :"+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
										setErrmsg("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
										RequestContext.getCurrentInstance().execute("servicenotAvailable.show();");
										return;
									}else{
										objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
										FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
									}
									
								}else{
									objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
									FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
								}
							}
						}
					}else{
						objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
						FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
					}

				}else {
					objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
				}
			}else {
				objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
			}

		} catch (Exception e) {
			setErrmsg("Fetching the Record Failed "+e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");		
		}

	}

	public void gotoRemittanceserviceFromEditAccService() {
		try {
			// editBene is false only new records, old records or new service , new account editBene is true. date check
			Boolean editBene = true;
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(false);
			setDisableSaveBack(false);
			PersonalRemmitanceBeneficaryDataTable personalRBDataTable = null;

			/*if(getBeneficarymasterId() == null && getBeneMatsterSeqId() != null) {
				setBeneficarymasterId(getBeneMatsterSeqId());
			}*/

			BigDecimal beneAccountSeqid = null;

			if(getBeneficarymasterId() != null && getBenifisBankId() != null && getServicebankBranchId() != null && getBankAccountNumber() != null && getBenifisCurrencyId() != null){
				beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getBenifisBankId(),getServicebankBranchId(),getBankAccountNumber(),getBenifisCurrencyId());
			}else if(getBeneficarymasterId() != null && getAgentMaster() != null && getAgentBranch() != null && getBenifisCurrencyId() != null){
				//beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getAgentMaster(),getAgentBranch(),getBankAccountNumber(),getBenifisCurrencyId());
				beneAccountSeqid= beneficaryCreation.getbeneAccountSeqIdForCash(getBeneficarymasterId(),getAgentMaster(),getAgentBranch(),getBankAccountNumber(),getBenifisCurrencyId());
			}else{
				// not cash or banking channel
			}

			if(beneAccountSeqid != null){

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
					personalRBDataTable.setStateId(rel.getStateId());
					personalRBDataTable.setDistrictId(rel.getDistrictId());
					personalRBDataTable.setCityId(rel.getCityId());
					personalRBDataTable.setMapSequenceId(rel.getMapSequenceId());
					personalRBDataTable.setBankAccountTypeId(rel.getBankAccountTypeId());

					List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"),rel.getServiceGroupId());
					if (lstServiceGroupMasterDesc.size() > 0) {
						personalRBDataTable.setServiceGroupName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
					}

					/*String telePhone = beneficaryCreation.getTelePhoneNumberString(rel.getBeneficaryMasterSeqId());
					if (telePhone != null) {
						personalRBDataTable.setTelphoneNum(telePhone);
					}*/

					List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(rel.getBeneficaryMasterSeqId());

					System.out.println("telePhone :"+telePhone.toString());
					if (telePhone != null && telePhone.size() != 0) {
						String telNumber = null;
						if(telePhone.size() == 1){
							if(telePhone.get(0).getTelephoneNumber()!=null){
								telNumber = telePhone.get(0).getTelephoneNumber();
							}else if(telePhone.get(0).getMobileNumber() !=null){
								telNumber = telePhone.get(0).getMobileNumber().toPlainString();
							}else{
								telNumber = null;
							}
							personalRBDataTable.setTelphoneNum(telNumber);
							personalRBDataTable.setTelphoneCode(telePhone.get(0).getCountryTelCode());
							personalRBDataTable.setBeneficiaryContactSeqId(telePhone.get(0).getBeneficaryTelephoneSeqId());
						}else{
							BeneficaryContact beneficaryContact = telePhone.get(0);
							if(beneficaryContact.getTelephoneNumber()!=null){
								telNumber = beneficaryContact.getTelephoneNumber();
							}else if(beneficaryContact.getMobileNumber()!=null){
								telNumber = beneficaryContact.getMobileNumber().toPlainString();
							}else{
								telNumber = null;
							}
							personalRBDataTable.setTelphoneNum(telNumber);
							personalRBDataTable.setTelphoneCode(beneficaryContact.getCountryTelCode());
							personalRBDataTable.setBeneficiaryContactSeqId(beneficaryContact.getBeneficaryTelephoneSeqId());
						}
					}

					personalRBDataTable.setThirdName(rel.getThirdName());
					personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
					personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());

					System.out.println("***************************************************************************");
					System.out.println(personalRBDataTable);
					System.out.println("***************************************************************************");
					log.info("Page redirect to bankacccountdetails page");

				}
			}



			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficarySave", "yes");
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dataTable", personalRBDataTable);
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");



		} catch (Exception e) {
			setErrmsg("Fetching the Record Failed "+e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");	
		}

	}

	private String bankSpbranches;
	private boolean booRenderAgentMaster = true;
	private boolean booMultipleService = false;
	private boolean booSingleService = true;
	private String agentMasterBankName;

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

	public boolean isBooMultipleService() {
		return booMultipleService;
	}
	public void setBooMultipleService(boolean booMultipleService) {
		this.booMultipleService = booMultipleService;
	}

	public boolean isBooSingleService() {
		return booSingleService;
	}
	public void setBooSingleService(boolean booSingleService) {
		this.booSingleService = booSingleService;
	}

	public String getAgentMasterBankName() {
		return agentMasterBankName;
	}
	public void setAgentMasterBankName(String agentMasterBankName) {
		this.agentMasterBankName = agentMasterBankName;
	}

	@SuppressWarnings("unchecked")
	public void popFirstbranchlist() {
		setBankBranchName(null);

		try {
			Predicate predicate = new Predicate() {
				public boolean evaluate(Object object) {
					return ((BanksView) object).getBankId().equals(getBenifisBankId());
				}
			};
			@SuppressWarnings("rawtypes")
			Collection filtered = CollectionUtils.select(lstBankFromView, predicate);
			List<BanksView> tempList = (List<BanksView>) filtered;
			if (tempList != null && !tempList.isEmpty()) {
				System.out.println(tempList.size());
				System.out.println("BankCode "+ tempList.get(0).getBankCode());
				setBankCode(tempList.get(0).getBankCode());
				System.out.println("benifisBankId  ---------" + getBenifisBankId());
				System.out.println(getBankCode());
			}

			if(getBenifisCountryId() != null ){
				lstBankCountrystate = generalService.getStateList(sessionmanage.getLanguageId(), getBenifisCountryId());
			}

			setBooAotherLang(false);
			for (BanksView bankMaster : lstBankFromView) {

				if (bankMaster.getBankId().compareTo(getBenifisBankId()) == 0) {
					setBankCode(bankMaster.getBankCode());
					setBeneficaryBankName(bankMaster.getBankFullName());

					if(!bankMaster.getLanguageInd().equals(Constants.ENGLISH_LANGUAGE_ID))
					{
						setBooAotherLang(true);
						/*if(getFirstLName()==null || getFirstLName()!=null && getFirstLName().length()==0)
						{
							RequestContext.getCurrentInstance().execute("localLanaguageMisssing.show();");
							setErrmsg(" Need to enter Local Language's Beneficary names for this bank ");
							return;

						}*/
					}
					setAliasFirstName(null);
					setAliasSecondName(null);
					setAliasThirdName(null);
					setAliasFourthName(null);

					break;
				}
			}


		} catch (Exception e) {

			System.out.println(e);
		}

	}

	List<BeneficaryContact> lstContactDetails;
	List<BeneficaryContact> lstMobileDetails;

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
			setTelephoneNumber(getTelephoneNumberSelect().trim());
			for (BeneficaryContact contactDetails : lstContactDetails) {
				if (contactDetails.getTelephoneNumber().equals(getTelephoneNumberSelect())) {
					setCountryCode(contactDetails.getCountryTelCode());
					setMcountryCode(contactDetails.getCountryTelCode());
					setBeneficaryTelephoneSeqId(contactDetails.getBeneficaryTelephoneSeqId());
					if (contactDetails.getTelephoneNumber() != null) {
						setTelephoneNumber(contactDetails.getTelephoneNumber().trim());
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
							setTelephoneNumber(contactDetails.getTelephoneNumber().trim());
						} else {
							setTelephoneNumber(null);
						}
					}


				}

			}

		}
	}

	private String searchBranchName;
	private String searchIFSC;
	private String searchSwift;
	private String searchState;




	public String getSearchBranchName() {
		return searchBranchName;
	}
	public void setSearchBranchName(String searchBranchName) {
		this.searchBranchName = searchBranchName;
	}
	public String getSearchIFSC() {
		return searchIFSC;
	}
	public void setSearchIFSC(String searchIFSC) {
		this.searchIFSC = searchIFSC;
	}
	public String getSearchSwift() {
		return searchSwift;
	}
	public void setSearchSwift(String searchSwift) {
		this.searchSwift = searchSwift;
	}
	public String getSearchState() {
		return searchState;
	}
	public void setSearchState(String searchState) {
		this.searchState = searchState;
	}

	private Boolean enableDatatable;


	public Boolean getEnableDatatable() {
		return enableDatatable;
	}
	public void setEnableDatatable(Boolean enableDatatable) {
		this.enableDatatable = enableDatatable;
	}
	public void searchBranch()
	{
		try {

			if(getSearchBranchName()==null && getSearchIFSC()==null &&  getSearchSwift()==null&& getSearchState()==null)
			{
				setErrmsg("Please enter atleast any one of  branch Name , IFSC ,Swift ,State");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchBranchName()!=null && !getSearchBranchName().trim().equals("") && getSearchBranchName().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of Branch Name");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchIFSC()!=null && !getSearchIFSC().trim().equals("") &&  getSearchIFSC().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of IFSC");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchSwift()!=null && !getSearchSwift().trim().equals("") &&  getSearchSwift().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of Swift");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchState()!=null && !getSearchState().trim().equals("") && getSearchState().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of State");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			lstDataTableBankbranch.clear();
			System.out.println("Entering into searchBranch method");
			System.out.println(getSearchBranchName());
			System.out.println(getSearchIFSC());
			System.out.println(getSearchSwift());
			System.out.println(getSearchState());
			System.out.println("Exit into searchBranch method");
			setEnableDatatable(true);
			lstBankbranchView = beneficaryCreation.serachBranch(getBenifisBankId(), getSearchBranchName(), getSearchIFSC(), getSearchSwift(), getSearchState());
			BranchDataTable datatable = null;
			for (BankBranchView bankBranch : lstBankbranchView) {
				datatable = new BranchDataTable();
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
				if (bankBranch.getBankBranchId() != null) {
					datatable.setBankBranchId(bankBranch.getBankBranchId());
					datatable.setBankBranchCode(bankBranch.getBranchCode());
				}
				lstDataTableBankbranch.add(datatable);
			}
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg(e.toString());
			return;
		}
	}

	Boolean enableSearchPage;

	Boolean booenableBranchSearchDataTable;

	Boolean booDisbleChoseeBranchButton;






	public Boolean getBooDisbleChoseeBranchButton() {
		return booDisbleChoseeBranchButton;
	}
	public void setBooDisbleChoseeBranchButton(Boolean booDisbleChoseeBranchButton) {
		this.booDisbleChoseeBranchButton = booDisbleChoseeBranchButton;
	}
	public Boolean getBooenableBranchSearchDataTable() {
		return booenableBranchSearchDataTable;
	}
	public void setBooenableBranchSearchDataTable(Boolean booenableBranchSearchDataTable) {
		this.booenableBranchSearchDataTable = booenableBranchSearchDataTable;
	}
	public Boolean getEnableSearchPage() {
		return enableSearchPage;
	}
	public void setEnableSearchPage(Boolean enableSearchPage) {
		this.enableSearchPage = enableSearchPage;


	}
	public void enableSearchpagePanel()
	{
		clearSearch();
		setServicebankBranchId(null);
		setEnableSearchPage(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void clearSearch()
	{
		setSearchIFSC(null);
		setSearchSwift(null);
		setSearchState(null);
		setEnableDatatable(null);
		setSearchBranchName(null);
		setBankBranchFullName(null);
	}


	public void enableSearchpagePanelforEdit()
	{
		clearSearch();
		setServicebankBranchId(null);
		setEnableSearchPage(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/firsttimebeneficaryedit.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void searchBranchforEdit()
	{
		try {

			if(getSearchBranchName()==null && getSearchIFSC()==null &&  getSearchSwift()==null&& getSearchState()==null)
			{
				setErrmsg("Please enter atleast any one of  branch Name , IFSC ,Swift ,State");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchBranchName()!=null && !getSearchBranchName().trim().equals("") && getSearchBranchName().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of Branch Name");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchIFSC()!=null && !getSearchIFSC().trim().equals("") &&  getSearchIFSC().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of IFSC");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchSwift()!=null && !getSearchSwift().trim().equals("") &&  getSearchSwift().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of Swift");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchState()!=null && !getSearchState().trim().equals("") && getSearchState().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of State");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			lstDataTableBankbranch.clear();
			System.out.println("Entering into searchBranch method");
			System.out.println(getSearchBranchName());
			System.out.println(getSearchIFSC());
			System.out.println(getSearchSwift());
			System.out.println(getSearchState());
			System.out.println("Exit into searchBranch method");
			setEnableDatatable(true);
			lstBankbranchView = beneficaryCreation.serachBranch(getBenifisBankId(), getSearchBranchName(), getSearchIFSC(), getSearchSwift(), getSearchState());
			BranchDataTable datatable = null;
			for (BankBranchView bankBranch : lstBankbranchView) {
				datatable = new BranchDataTable();
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
				if (bankBranch.getBankBranchId() != null) {
					datatable.setBankBranchId(bankBranch.getBankBranchId());
					datatable.setBankBranchCode(bankBranch.getBranchCode());
				}
				lstDataTableBankbranch.add(datatable);
			}
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/firsttimebeneficaryedit.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg(e.toString());
			return;
		}
	}


	public void selectBranchfromViewWindowforEdit(BranchDataTable branchDataTable) {
		System.out.println("Entering into selectBranchfromViewWindow method");
		setEnableDatatable(false);
		setEnableSearchPage(false);
		setBeneficaryBankState(null);
		setBeneficaryBankDistrict(null);
		setBeneficaryBankCity(null);
		setBeneSwiftCode(null);
		System.out.println(branchDataTable);
		setServicebankBranchId(branchDataTable.getBankBranchId());
		setBankBranchCode(branchDataTable.getBankBranchCode());
		setBankBranchFullName(branchDataTable.getBankFullName());
		setBankAccountNumber(null);
		//populateSwiftCode();
		setBankBranchName(branchDataTable.getBankBranchId() + " " + branchDataTable.getBankFullName());
		if (branchDataTable.getStateName() != null) {
			setBeneficaryBankState(branchDataTable.getStateName());
		}
		if (branchDataTable.getDistrictName() != null) {
			setBeneficaryBankDistrict(branchDataTable.getDistrictName());
		}
		if (branchDataTable.getCityName() != null) {
			setBeneficaryBankCity(branchDataTable.getCityName());
		}
		if(branchDataTable.getSwiftCode() != null){
			setBeneSwiftCode(branchDataTable.getSwiftCode());
		}

		try {
			
			if(lstBankAccountTypeFromView != null || !lstBankAccountTypeFromView.isEmpty()){
				lstBankAccountTypeFromView.clear();
			}
			
			lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBenifisCountryId());
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/firsttimebeneficaryedit.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Exit into selectBranchfromViewWindow method");
	}

	public void fetchAllAgentsMasterBanks(){
		log.info("Entering into fetchAllAgentsMasterBanks method");
		log.info("getAgentMaster" + getAgentMaster());
		if(agentAndCorrespondingBankslst != null || !agentAndCorrespondingBankslst.isEmpty()){
			agentAndCorrespondingBankslst.clear();
		}
		if(agentAndCorrespondingBankBranchlst != null || !agentAndCorrespondingBankBranchlst.isEmpty()){
			agentAndCorrespondingBankBranchlst.clear();
		}
		if(lstAgentsBanks != null || !lstAgentsBanks.isEmpty()){
			lstAgentsBanks.clear();
		}

		// fetch details from view
		List<ViewRoutingAgents> lstAgents = iroutingSetUpDetailsService.fetchAllRoutingAgents(sessionmanage.getCountryId(), getBenifisCountryId(), getServiceGroupId(),  getServiceTypeId(), getBenifisCurrencyId());

		if(lstAgents != null && lstAgents.size() != 0){
			lstAgentsBanks.addAll(lstAgents);
			if(lstAgents.size() == 1){
				setBooMultipleService(false);
				setBooSingleService(true);
				for (ViewRoutingAgents viewRoutingAgents : lstAgents) {
					setAgentMaster(viewRoutingAgents.getAgentBankId());
					setBankCode(viewRoutingAgents.getAgentBankCode());
					setAgentMasterBankName(viewRoutingAgents.getAgentBankName());
				}
			}else{
				setBooMultipleService(true);
				setBooSingleService(false);
				for (ViewRoutingAgents viewRoutingAgents : lstAgents) {
					PopulateDataWithCode lstBank = new PopulateDataWithCode();

					lstBank.setId(viewRoutingAgents.getAgentBankId());
					lstBank.setCode(viewRoutingAgents.getAgentBankCode());
					lstBank.setName(viewRoutingAgents.getAgentBankName());

					agentAndCorrespondingBankslst.add(lstBank);
				}
			}
		}else{
			if(agentAndCorrespondingBankslst != null || !agentAndCorrespondingBankslst.isEmpty()){
				agentAndCorrespondingBankslst.clear();
			}
			if(lstAgentsBanks != null || !lstAgentsBanks.isEmpty()){
				lstAgentsBanks.clear();
			}
			setBooMultipleService(false);
			setBooSingleService(true);
		}
		log.info("Exit into fetchAllAgentsMasterBanks method");
	}

	public void searchClicked() {
		try {
			//popBeneStatelist();

			HttpSession session = sessionmanage.getSession();
			session.setAttribute("BenificiaryBankID", getBenifisBankId());
			session.setAttribute("BenificiaryBankName", getBeneficaryBankName());
			session.setAttribute("EditBaneficiary", true);
			session.setAttribute("BenificiaryCountryID",getBenifisCountryId());
			session.setAttribute("BenificiaryCountryName", getBenifisCountryName());
			session.setAttribute("stateList",null);
			session.setAttribute("stateList", lstBankCountrystate);
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../beneficary/searchbranchdetails.xhtml");

		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}

	}

	public void checkingBacktoWesternUnion(){
		setAgeFocus(false);
		RequestContext.getCurrentInstance().execute("backcheckforWesternUnion.show();");
	}
	
	public void checkingBacktoWesternUnionUpload(){
		setAgeFocus(false);
		RequestContext.getCurrentInstance().execute("backcheckforWesternUnionUpload.show();");
	}

	public void backToWesternUnionScreen() {
		
		/*try {
			String  wuh2hstring = null;
			HttpSession session = sessionmanage.getSession();
			wuh2hstring = (String)session.getAttribute("WUH2H");
			
			
			
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(false);
			//setDisableSaveBack(false);
			setWesternunionPanel(false);
			setBooRenderBeneficaryCreation(true);
			log.info("Page redirect to bankacccountdetails page");
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			
			
			if(wuh2hstring!=null&&wuh2hstring.equalsIgnoreCase("WUH2H")){
				wuh2hBean.setBeneficiaryCountryId(getBenifisCountryId());
				wuh2hBean.populateBeneFiciaryCountry();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../wuh2h/wuh2hbenelistinfo.xhtml");
			}else{
				westernUnionTransferBean.setBeneficiaryCountryId(getBenifisCountryId());
				westernUnionTransferBean.populateBeneFiciaryCountry();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
			}

		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}*/
		try {
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(false);
			//setDisableSaveBack(false);
			setWesternunionPanel(false);
			setBooRenderBeneficaryCreation(true);
			log.info("Page redirect to bankacccountdetails page");
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			westernUnionTransferBean.setBeneficiaryCountryId(getBenifisCountryId());
			westernUnionTransferBean.populateBeneFiciaryCountry();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");

		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	
	public void backToWesternUnionScreenUpload() {
		try {
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(false);
			//setDisableSaveBack(false);
			setWesternunionPanel(false);
			setBooRenderBeneficaryCreation(true);
			log.info("Page redirect to bankacccountdetails page");
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			//westernUnionTransferBean.setBeneficiaryCountryId(getBenifisCountryId());
			//westernUnionTransferBean.populateBeneFiciaryCountry();
			
			wuTranxFileUploadBean.setBeneficiaryCountryId(getBenifisCountryId());
			wuTranxFileUploadBean.populateBeneFiciaryCountry();
			//FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wutranxfileuploaddetail.xhtml");

		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	public void saveBeneficaryForWesternUnion() throws ParseException {


		setAgeFocus(false);
		String engName =firstName+" "+ secondName+" "+thirdName;
		String localName =firstLName+" "+ secondLName+" "+thirdLName;

		/** added by Rabil on 03/12/2015 for EX_P_BANNED_NAME_CHECK*/ 
		String proceErrorMessage = getiPersonalRemittanceService().getBannedNameCheckProcedure(sessionmanage.getCountryId(), engName,localName);

		if(proceErrorMessage!=null && proceErrorMessage.length()>0){
			setErrmsg(proceErrorMessage);
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}

		if ((getTelephoneNumber() == null ||  getTelephoneNumber().trim().equals("")) && getMobileNumber() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Telephone or Mobile");
		} else if (getTelephoneNumber() != null && getCountryCode() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Telephone Country Code");
		} else if (getMobileNumber() != null && getMcountryCode() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Mobile Country Code");
		} else {
			setDisableSaveBack(true);
			System.out.println("Beneficary Master ----------------------------- > ");
			BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
			BeneficaryAccount beneficaryAccount = new BeneficaryAccount();
			BeneficaryContact contact = new BeneficaryContact();
			BeneficaryRelationship relationship = new BeneficaryRelationship();
			Map<String, Object> mapBeneMasterCheck = new HashMap<String, Object>();

			if(getFirstName() != null && !getFirstName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("FirstName", getFirstName().toUpperCase());
			}
			if(getSecondName() != null && !getSecondName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("SecondName", getSecondName().toUpperCase());
			}
			if(getThirdName() != null && !getThirdName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("ThirdName", getThirdName().toUpperCase());
			}
			if(getFourthName() != null && !getFourthName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("FourthName", getFourthName().toUpperCase());
			}
			if(getFifthName() != null && !getFifthName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("FifthName", getFifthName().toUpperCase());
			}
			if(getFirstLName() != null && !getFirstLName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("FirstLName", getFirstLName());
			}
			if(getSecondLName() != null && !getSecondLName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("SecondLName", getSecondLName());
			}
			if(getThirdLName() != null && !getThirdLName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("ThirdLName", getThirdLName());
			}
			if(getFourthLName() != null && !getFourthLName().equalsIgnoreCase("")){
				mapBeneMasterCheck.put("FourthLName", getFourthLName());
			}
			if(getBeneCountryid() != null){
				mapBeneMasterCheck.put("BeneCountryId", getBeneCountryid());
			}
			mapBeneMasterCheck.put("customerId", getCustomerNo());

			List<BeneficaryMaster> lstBeneMaster = westernUnionService.fetchBeneMasterRecForWU(mapBeneMasterCheck);

			if(lstBeneMaster != null && lstBeneMaster.size() != 0){

				//setBenifisCountryId(getBeneCountryid()); // beneficiary country as bank Country for Western Union
				List<BankMaster> bankMasterdetails = ibankMasterService.getBankMasterInfo(Constants.WU_BANK_CODE);

				if(bankMasterdetails != null && bankMasterdetails.size() != 0){

					BankMaster bankMasterId = bankMasterdetails.get(0);

					if(bankMasterId.getBankId() != null){
						setBenifisBankId(bankMasterId.getBankId());  // setting bankID western union
						setBankCode(bankMasterId.getBankCode());

						List<BankBranch> bankBranchdetails = bankBranchDetailsService.getBranchCodebyBank(bankMasterId.getBankId());

						if(bankBranchdetails != null && bankBranchdetails.size() != 0){

							BankBranch bankBranchId = bankBranchdetails.get(0);

							if(bankBranchId.getBankBranchId() != null){
								setServicebankBranchId(bankBranchId.getBankBranchId()); // western union bank branch id
								setBankBranchCode(bankBranchId.getBranchCode());
							}
						}
					}
				}

				for (BeneficaryMaster beneMaster : lstBeneMaster) {

					if (getFirstName() != null && getSecondName() != null && beneMaster.getFirstName() != null && beneMaster.getSecondName() != null && beneMaster.getFirstName().equalsIgnoreCase(getFirstName().toUpperCase()) && beneMaster.getSecondName().equalsIgnoreCase(getSecondName().toUpperCase())) {

						Boolean check = false;

						if(Objects.equals(getThirdName() == null || getThirdName().equalsIgnoreCase("") ? null:getThirdName().toUpperCase(), beneMaster.getThirdName())){
							if(Objects.equals(getFourthName() == null || getFourthName().equalsIgnoreCase("") ? null:getFourthName().toUpperCase(), beneMaster.getFourthName())){
								if(Objects.equals(getFifthName() == null || getFifthName().equalsIgnoreCase("") ? null:getFifthName().toUpperCase(), beneMaster.getFifthName())){
									if(getBeneCountryid() != null && beneMaster.getFsCountryMaster() != null && getBeneCountryid().compareTo(beneMaster.getFsCountryMaster().getCountryId())==0){
										// old record
										check = true;
									}else{
										// new record
										check = false;
									}
								}else{
									// new record
									check = false;
								}
							}else{
								// new record
								check = false;
							}
						}else{
							// new record
							check = false;
						}

						if(check){
							beneficaryMaster = beneMaster;
							break;
						}
					}
				}

				if(beneficaryMaster.getBeneficaryMasterSeqId() != null){

					// checking telephone and mobile with bene contact details
					List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(beneficaryMaster.getBeneficaryMasterSeqId());
					Boolean teleMobCheck = false;
					
					for (BeneficaryContact beneficaryContact : telePhone) {
						if (telePhone != null && telePhone.size() != 0) {
							if(beneficaryContact.getTelephoneNumber()!=null){
								if(getTelephoneNumber() != null && !getTelephoneNumber().equalsIgnoreCase("") && getTelephoneNumber().equalsIgnoreCase(beneficaryContact.getTelephoneNumber())){
									// old
									contact = beneficaryContact;
									teleMobCheck = true;
									break;
								}else if(getMobileNumber() != null && getMobileNumber().compareTo(new BigDecimal(beneficaryContact.getTelephoneNumber()))==0){
									// old
									contact = beneficaryContact;
									teleMobCheck = true;
									break;
								}else{
									// new
									teleMobCheck = false;
								}
							}else if(beneficaryContact.getMobileNumber() !=null){
								if(getTelephoneNumber() != null && !getTelephoneNumber().equalsIgnoreCase("") && beneficaryContact.getMobileNumber().compareTo(new BigDecimal(getTelephoneNumber()))==0){
									// old
									contact = beneficaryContact;
									teleMobCheck = true;
									break;
								}else if(getMobileNumber() != null && getMobileNumber().compareTo(beneficaryContact.getMobileNumber())==0){
									// old
									contact = beneficaryContact;
									teleMobCheck = true;
									break;
								}else{
									// new
									teleMobCheck = false;
								}
							}
						}
					}
										
					/*for (BeneficaryContact beneficaryContact : telePhone) {
						if (telePhone != null && telePhone.size() != 0) {
							if(beneficaryContact.getTelephoneNumber()!=null){
								if(getTelephoneNumber().equalsIgnoreCase(beneficaryContact.getTelephoneNumber())){
									// old
									contact = beneficaryContact;
									teleMobCheck = true;
									break;
								}else{
									// new
									teleMobCheck = false;
								}
							}else if(beneficaryContact.getMobileNumber() !=null){
								if(getMobileNumber().compareTo(beneficaryContact.getMobileNumber())==0){
									// old
									contact = beneficaryContact;
									teleMobCheck = true;
									break;
								}else{
									// new
									teleMobCheck = false;
								}
							}
						}
					}*/

					if(teleMobCheck){

						try {
							// checking duplicates if master id is old 							
							if(beneficaryMaster.getBeneficaryMasterSeqId() != null){
								HashMap<String, Object> checkDuplicates = checkDuplicatesforWesternUnion(beneficaryMaster.getBeneficaryMasterSeqId());
								Boolean status = (Boolean)checkDuplicates.get("Status");
								BeneficaryAccount beneAcc = (BeneficaryAccount)checkDuplicates.get("BeneAccRec");
								System.out.println("Customer Id :" +getCustomerNo());
								if(status != null && !status){
									if(beneAcc != null){
										if(beneAcc.getBeneficaryAccountSeqId() != null){
											// checking relationship and customer id matching or not
											List<BeneficaryRelationship> lstBeneRelationShip = iPersonalRemittanceService.isBenificaryRelationExist(beneficaryMaster.getBeneficaryMasterSeqId(), beneAcc.getBeneficaryAccountSeqId());
											if(lstBeneRelationShip != null && !lstBeneRelationShip.isEmpty() && getCustomerNo() != null){
												Boolean checkStatus = false;
												for (BeneficaryRelationship beneficaryRelationship : lstBeneRelationShip) {
													checkStatus = false;
													if(beneficaryRelationship.getCustomerId().getCustomerId().compareTo(getCustomerNo())==0){
														checkStatus = true;
														break;
													}else{
														checkStatus = false;
													}
												}
												if(checkStatus){
													// duplicates exists - Beneficiary Account checking MasterSeqId,BankCode,BankCountryId,currencyId 
													setErrmsg("This combination already exist in datatable");
													RequestContext.getCurrentInstance().execute("combinationexist.show();");
													return;
												}else{
													// creating new record customer maybe different
													beneficaryAccount = beneAcc;
													relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
													beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
												}
											}else{
												// Relationship coming empty - not possible
												System.out.println("Relationship coming empty - not possible");
											}
										}else{
											// beneficiary account Seq Id null - not possible
											System.out.println("beneficiary account Seq Id null - not possible");
										}
									}else{
										// beneficiary account null - not possible
										System.out.println(" beneficiary account null - not possible");
									}
								}else{
									// creating new record
									beneficaryAccount = saveBeneficiaryAccountDetails(beneficaryMaster);
									relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
									beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
								}

							}else{
								// creating new record
								beneficaryAccount = saveBeneficiaryAccountDetails(beneficaryMaster);
								relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
								beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
							}
						} catch (Exception e) {
							RequestContext.getCurrentInstance().execute("csp.show();");
							setErrmsg(e.getMessage());
							return;
						}
					}else{
						contact = saveBeneficaryTelephone(beneficaryMaster);

						try {
							// checking duplicates if master id is old 							
							if(beneficaryMaster.getBeneficaryMasterSeqId() != null){
								HashMap<String, Object> checkDuplicates = checkDuplicatesforWesternUnion(beneficaryMaster.getBeneficaryMasterSeqId());
								Boolean status = (Boolean)checkDuplicates.get("Status");
								BeneficaryAccount beneAcc = (BeneficaryAccount)checkDuplicates.get("BeneAccRec");
								System.out.println("Customer Id :" +getCustomerNo());
								if(status != null && !status){
									if(beneAcc != null){
										if(beneAcc.getBeneficaryAccountSeqId() != null){
											// checking relationship and customer id matching or not
											List<BeneficaryRelationship> lstBeneRelationShip = iPersonalRemittanceService.isBenificaryRelationExist(beneficaryMaster.getBeneficaryMasterSeqId(), beneAcc.getBeneficaryAccountSeqId());
											if(lstBeneRelationShip != null && !lstBeneRelationShip.isEmpty() && getCustomerNo() != null){
												Boolean checkStatus = false;
												for (BeneficaryRelationship beneficaryRelationship : lstBeneRelationShip) {
													checkStatus = false;
													if(beneficaryRelationship.getCustomerId().getCustomerId().compareTo(getCustomerNo())==0){
														checkStatus = true;
														break;
													}else{
														checkStatus = false;
													}
												}
												if(checkStatus){
													// duplicates exists - Beneficiary Account checking MasterSeqId,BankCode,BankCountryId,currencyId 
													setErrmsg("This combination already exist in datatable");
													RequestContext.getCurrentInstance().execute("combinationexist.show();");
													return;
												}else{
													// creating new record customer maybe different
													beneficaryAccount = beneAcc;
													relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
													beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
												}
											}else{
												// Relationship coming empty - not possible
												System.out.println("Relationship coming empty - not possible");
											}
										}else{
											// beneficiary account Seq Id null - not possible
											System.out.println("beneficiary account Seq Id null - not possible");
										}
									}else{
										// beneficiary account null - not possible
										System.out.println(" beneficiary account null - not possible");
									}
								}else{
									// creating new record
									beneficaryAccount = saveBeneficiaryAccountDetails(beneficaryMaster);
									relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
									beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
								}

							}else{
								// creating new record
								beneficaryAccount = saveBeneficiaryAccountDetails(beneficaryMaster);
								relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
								beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
							}
						} catch (Exception e) {
							RequestContext.getCurrentInstance().execute("csp.show();");
							setErrmsg(e.getMessage());
							return;
						}
					}

				}else{
					beneficaryMaster = beneficaryMasterSave();
					contact = saveBeneficaryTelephone(beneficaryMaster);
					beneficaryAccount = saveBeneficiaryAccountDetails(beneficaryMaster);
					relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);

					// creating new record
					beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
				}

			}else{

				//setBenifisCountryId(getBeneCountryid()); // beneficiary country as bank Country for Western Union
				List<BankMaster> bankMasterdetails = ibankMasterService.getBankMasterInfo(Constants.WU_BANK_CODE);

				if(bankMasterdetails != null && bankMasterdetails.size() != 0){

					BankMaster bankMasterId = bankMasterdetails.get(0);

					if(bankMasterId.getBankId() != null){
						setBenifisBankId(bankMasterId.getBankId());  // setting bankID western union
						setBankCode(bankMasterId.getBankCode());

						List<BankBranch> bankBranchdetails = bankBranchDetailsService.getBranchCodebyBank(bankMasterId.getBankId());

						if(bankBranchdetails != null && bankBranchdetails.size() != 0){

							BankBranch bankBranchId = bankBranchdetails.get(0);

							if(bankBranchId.getBankBranchId() != null){
								setServicebankBranchId(bankBranchId.getBankBranchId()); // western union bank branch id
								setBankBranchCode(bankBranchId.getBranchCode());
							}
						}
					}
				}

				beneficaryMaster = beneficaryMasterSave();
				contact = saveBeneficaryTelephone(beneficaryMaster);
				beneficaryAccount = saveBeneficiaryAccountDetails(beneficaryMaster);
				relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);

				// creating new record
				beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);

			}

			setBeneficarymasterId(beneficaryMaster.getBeneficaryMasterSeqId());
			String errorMessage = null;
			if(getBenifisBankId() != null && getServicebankBranchId() != null){
				errorMessage = beneficaryCreation.getBeneDetailProce(beneficaryMaster.getBeneficaryMasterSeqId(), getBenifisBankId(), getServicebankBranchId(), beneficaryAccount.getBeneficaryAccountSeqId(), getBenifisCurrencyId(), getCustomerNo());
			}

			if (errorMessage == null) {
				
				if(isBooRenderWesterUnion()){
					RequestContext.getCurrentInstance().execute("beneficarycompleteWU.show();");
				}else if(isBooRenderWesterUnionUpload()){
					RequestContext.getCurrentInstance().execute("beneficarycompleteWUupload.show();");
				}else{
					RequestContext.getCurrentInstance().execute("beneficarycompleteWU.show();");
				}
				
			} else {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("EX_POPULATE_BENE_DT " + errorMessage);
			}
		}

	}

	// Saving record for western union in Beneficiary Account 
	public BeneficaryAccount saveBeneficiaryAccountDetails(BeneficaryMaster beneficaryMaster){

		BeneficaryAccount beneficaryAccount = null;

		try{
			beneficaryAccount = new BeneficaryAccount();

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId()); // application country
			beneficaryAccount.setBeneApplicationCountry(countryMaster);

			if(getBenifisCountryId() != null){
				CountryMaster countryMaster1 = new CountryMaster();
				countryMaster1.setCountryId(getBenifisCountryId()); // Beneficiary Bank country
				beneficaryAccount.setBeneficaryCountry(countryMaster1);
			}

			if(getBenifisBankId() != null){
				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(getBenifisBankId()); // western union bank id
				beneficaryAccount.setBank(bankMaster);
			}

			beneficaryAccount.setBankCode(Constants.WU_BANK_CODE); // western union bank code

			if(getServicebankBranchId() != null){
				BankBranch bankBranch = new BankBranch();
				bankBranch.setBankBranchId(getServicebankBranchId()); // western union bank branch id
				beneficaryAccount.setBankBranch(bankBranch);
			}
			if(getBankBranchCode() != null){
				beneficaryAccount.setBankBranchCode(getBankBranchCode()); // western union bank branch code
			}

			List<ServiceGroupMaster>  lstServiceGroup = serviceGroupMasterService.toServiceGroupCodeAllValues(Constants.CashCode);
			if(lstServiceGroup.size() != 0){
				ServiceGroupMaster serviceGroup = lstServiceGroup.get(0);

				if(serviceGroup.getServiceGroupId() != null){
					ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
					serviceGroupMaster.setServiceGroupId(serviceGroup.getServiceGroupId()); //cash for western union
					beneficaryAccount.setServicegropupId(serviceGroupMaster);
				}
			}

			if(getBenifisCurrencyId() != null){
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(getBenifisCurrencyId()); // beneficiary country currency western union
				beneficaryAccount.setCurrencyId(currencyMaster);
			}

			beneficaryAccount.setIsActive(Constants.Yes);
			beneficaryAccount.setCreatedBy(sessionmanage.getUserName());
			beneficaryAccount.setCreatedDate(new Date());

			if(beneficaryMaster.getBeneficaryMasterSeqId() != null){
				BeneficaryMaster  beneficaryMasterId = new BeneficaryMaster();
				beneficaryMasterId.setBeneficaryMasterSeqId(beneficaryMaster.getBeneficaryMasterSeqId());
				beneficaryAccount.setBeneficaryMaster(beneficaryMasterId);
			}

		}catch(Exception e) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+ e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please Choose Branch");
			return beneficaryAccount;
		}

		return beneficaryAccount;
	}

	// saving record for western union in beneficiary relationship 
	public BeneficaryRelationship saveBeneficiaryRelationshipDetails(BeneficaryMaster beneficaryMaster,BeneficaryAccount beneficaryAccount){

		BeneficaryRelationship beneficaryRelationship = new BeneficaryRelationship(); 

		try{

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneficaryRelationship.setApplicationCountry(countryMaster);

			if(getCustomerNo() != null){
				Customer customer = new Customer();
				customer.setCustomerId(getCustomerNo());
				beneficaryRelationship.setCustomerId(customer);
			}

			if(getRelationId() != null){
				Relations relations = new Relations();
				relations.setRelationsId(getRelationId());
				beneficaryRelationship.setRelations(relations);
			}

			beneficaryRelationship.setIsActive(Constants.Yes);
			beneficaryRelationship.setCreatedBy(sessionmanage.getUserName());
			beneficaryRelationship.setCreatedDate(new Date());
			beneficaryRelationship.setBeneficaryAccount(beneficaryAccount);

			if(beneficaryMaster.getBeneficaryMasterSeqId() != null){
				BeneficaryMaster  beneficaryMasterId = new BeneficaryMaster();
				beneficaryMasterId.setBeneficaryMasterSeqId(beneficaryMaster.getBeneficaryMasterSeqId());
				beneficaryRelationship.setBeneficaryMaster(beneficaryMasterId);
			}

		}catch(Exception e) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+ e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg(e.getMessage());
		}	

		return beneficaryRelationship;
	}

	public void searchClickedEdiBeneAccount() {
		try {
			//popBeneStatelist();
			HttpSession session = sessionmanage.getSession();
			session.setAttribute("BenificiaryBankID", getBenifisBankId());
			session.setAttribute("BenificiaryBankName", getBeneficaryBankName());
			session.setAttribute("editBeneAccount", true);
			session.setAttribute("BenificiaryCountryID",getBenifisCountryId());
			session.setAttribute("BenificiaryCountryName", getBenifisCountryName());
			session.setAttribute("stateList",null);
			session.setAttribute("stateList", lstBankCountrystate);
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../beneficary/searchbranchdetails.xhtml");

		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}

	}

	public void selectBranchfromViewWindowEdit(BranchDataTable branchDataTable) {
		System.out.println("Entering into selectBranchfromViewWindowEdit method");
		setEnableDatatable(false);
		setEnableSearchPage(false);
		setBeneficaryBankState(null);
		setBeneficaryBankDistrict(null);
		setBeneficaryBankCity(null);
		setBeneSwiftCode(null);
		System.out.println(branchDataTable);
		setServicebankBranchId(branchDataTable.getBankBranchId());
		setBankBranchCode(branchDataTable.getBankBranchCode());
		setBankBranchFullName(branchDataTable.getBankFullName());
		setBankAccountNumber(null);
		//populateSwiftCode();
		if (branchDataTable.getStateName() != null) {
			setBeneficaryBankState(branchDataTable.getStateName());
		}
		if (branchDataTable.getDistrictName() != null) {
			setBeneficaryBankDistrict(branchDataTable.getDistrictName());
		}
		if (branchDataTable.getCityName() != null) {
			setBeneficaryBankCity(branchDataTable.getCityName());
		}
		if(branchDataTable.getSwiftCode() != null){
			setBeneSwiftCode(branchDataTable.getSwiftCode());
		}

		try {
			
			if(lstBankAccountTypeFromView != null || !lstBankAccountTypeFromView.isEmpty()){
				lstBankAccountTypeFromView.clear();
			}
			
			lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBenifisCountryId());
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/editbeneficiarybankaccountdetails.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Exit into selectBranchfromViewWindowEdit method");
	}

	public void gotoRemittanceserviceUpdate(){

		try {
			// editBene is false only new records, old records or new service , new account editBene is true. date check
			Boolean editBene = true;
			firstTime = null;
			firstTimeInBeneCreation = null;
			setBooAotherLang(false);
			setDisableSaveBack(false);
			PersonalRemmitanceBeneficaryDataTable personalRBDataTable = null;

			/*if(getBeneficarymasterId() == null && getBeneMatsterSeqId() != null) {
				setBeneficarymasterId(getBeneMatsterSeqId());
			}*/

			BigDecimal beneAccountSeqid = null;

			if(getBeneficarymasterId() != null && getBenifisBankId() != null && getServicebankBranchId() != null && getBankAccountNumber() != null && getBenifisCurrencyId() != null){
				beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getBenifisBankId(),getServicebankBranchId(),getBankAccountNumber(),getBenifisCurrencyId());
			}else if(getBeneficarymasterId() != null && getAgentMaster() != null && getAgentBranch() != null && getBenifisCurrencyId() != null){
				//beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getAgentMaster(),getAgentBranch(),getBankAccountNumber(),getBenifisCurrencyId());
				beneAccountSeqid= beneficaryCreation.getbeneAccountSeqIdForCash(getBeneficarymasterId(),getAgentMaster(),getAgentBranch(),getBankAccountNumber(),getBenifisCurrencyId());
			}else{
				// not cash or Banking Channel
			}

			if(beneAccountSeqid != null){

				List<BenificiaryListView> isCoustomerExist = beneficaryCreation.fetchBeneficiaryNewRecordUpdated(getCustomerNo(), getBeneficarymasterId(), beneAccountSeqid, editBene,getBenifisCurrencyId(),getServiceGroupId());

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
					personalRBDataTable.setStateId(rel.getStateId());
					personalRBDataTable.setDistrictId(rel.getDistrictId());
					personalRBDataTable.setCityId(rel.getCityId());
					personalRBDataTable.setMapSequenceId(rel.getMapSequenceId());
					personalRBDataTable.setBankAccountTypeId(rel.getBankAccountTypeId());

					List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"),rel.getServiceGroupId());
					if (lstServiceGroupMasterDesc.size() > 0) {
						personalRBDataTable.setServiceGroupName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
					}

					/*String telePhone = beneficaryCreation.getTelePhoneNumberString(rel.getBeneficaryMasterSeqId());
					if (telePhone != null) {
						personalRBDataTable.setTelphoneNum(telePhone);
					}*/

					List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(rel.getBeneficaryMasterSeqId());

					System.out.println("telePhone :"+telePhone.toString());
					if (telePhone != null && telePhone.size() != 0) {
						String telNumber = null;
						if(telePhone.size() == 1){
							if(telePhone.get(0).getTelephoneNumber()!=null){
								telNumber = telePhone.get(0).getTelephoneNumber();
							}else if(telePhone.get(0).getMobileNumber() !=null){
								telNumber = telePhone.get(0).getMobileNumber().toPlainString();
							}else{
								telNumber = null;
							}
							personalRBDataTable.setTelphoneNum(telNumber);
							personalRBDataTable.setTelphoneCode(telePhone.get(0).getCountryTelCode());
							personalRBDataTable.setBeneficiaryContactSeqId(telePhone.get(0).getBeneficaryTelephoneSeqId());
						}else{
							BeneficaryContact beneficaryContact = telePhone.get(0);
							if(beneficaryContact.getTelephoneNumber()!=null){
								telNumber = beneficaryContact.getTelephoneNumber();
							}else if(beneficaryContact.getMobileNumber()!=null){
								telNumber = beneficaryContact.getMobileNumber().toPlainString();
							}else{
								telNumber = null;
							}
							personalRBDataTable.setTelphoneNum(telNumber);
							personalRBDataTable.setTelphoneCode(beneficaryContact.getCountryTelCode());
							personalRBDataTable.setBeneficiaryContactSeqId(beneficaryContact.getBeneficaryTelephoneSeqId());
						}
					}

					personalRBDataTable.setThirdName(rel.getThirdName());
					personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
					personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());

					System.out.println("***************************************************************************");
					System.out.println(personalRBDataTable);
					System.out.println("***************************************************************************");
					log.info("Page redirect to bankacccountdetails page");

				}
			}

			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficarySave", "yes");
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dataTable", personalRBDataTable);

			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");


		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	public List<StateMasterDesc> getLstBankCountrystate() {
		return lstBankCountrystate;
	}
	public void setLstBankCountrystate(List<StateMasterDesc> lstBankCountrystate) {
		this.lstBankCountrystate = lstBankCountrystate;
	}

	public void checkDuplicateforCash()
	{
		PersonalRemmitanceBeneficaryDataTable tempTable = null;
		if (coustomerBeneficaryDTList != null && coustomerBeneficaryDTList.isEmpty()) {
			log.info("=====================getCustomerNo()" + getCustomerNo() + "" + getBenifisCountryId());
			List<BenificiaryListView> isCoustomerExist = iPersonalRemittanceService.getBeneficiaryCountryList(getCustomerNo(), getBenifisCountryId());
			log.info("=====================" + isCoustomerExist.size());
			// List<BenificiaryListView> isCoustomerExist =
			// beneficaryCreation.getBeneficaryList(getCustomerNo());
			PersonalRemmitanceBeneficaryDataTable personalRBDataTable = null;
			BenificiaryListView rel = null;
			if (isCoustomerExist.size() > 0) {
				for (int i = 0; i < isCoustomerExist.size(); i++) {
					rel = isCoustomerExist.get(i);
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
					personalRBDataTable.setBooDisabled(rel.getBankAccountNumber() != null ? true : false);
					personalRBDataTable.setBranchCode(rel.getBranchCode());
					personalRBDataTable.setBranchId(rel.getBranchId());
					personalRBDataTable.setCityName(rel.getCityName());
					personalRBDataTable.setCountryId(rel.getBenificaryCountry());
					personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName());
					personalRBDataTable.setCreatedBy(rel.getCreatedBy());
					personalRBDataTable.setCreatedDate(rel.getCreatedDate());
					personalRBDataTable.setCurrencyId(rel.getCurrencyId());
					personalRBDataTable.setCurrencyName(rel.getCurrencyName());
					personalRBDataTable.setCurrencyQuoteName(rel.getCurrencyQuoteName() == null ? "" : rel.getCurrencyQuoteName());
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
					personalRBDataTable.setStateId(rel.getStateId());
					personalRBDataTable.setDistrictId(rel.getDistrictId());
					personalRBDataTable.setCityId(rel.getCityId());
					personalRBDataTable.setMapSequenceId(rel.getMapSequenceId());
					personalRBDataTable.setBankAccountTypeId(rel.getBankAccountTypeId());
					
					if (rel.getLastEmosRemittance() == null) {
						personalRBDataTable.setTransactinStatus(Constants.NO);
					} else {
						personalRBDataTable.setTransactinStatus(Constants.YES);
					}
					personalRBDataTable.setThirdName(rel.getThirdName());
					personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
					personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());
					coustomerBeneficaryDTList.add(personalRBDataTable);
					personalRBDataTable = null;
					rel = null;
				}
			}
		}
		/*
		 * System.out.println("coustomerBeneficaryDTList size" +
		 * coustomerBeneficaryDTList.size());
		 */
		for (int m = 0; m < coustomerBeneficaryDTList.size(); m++) {
			tempTable = coustomerBeneficaryDTList.get(m);
			/*
			 * System.out.println(
			 * "*******************************************************************************8"
			 * ); System.out.println(tempTable); System.out.println(
			 * "*******************************************************************************8"
			 * );
			 */
			if (tempTable.getBankId() != null && getAgentMaster() != null && tempTable.getBankId().equals(getAgentMaster()) && tempTable.getCurrencyId() != null && tempTable.getCurrencyId().equals(getBenifisCurrencyId()) && tempTable.getBenificaryCountry() != null
					&& tempTable.getBenificaryCountry().equals(getBenifisCountryId()) && getAgentBranch() != null && tempTable.getBranchId() != null && tempTable.getBranchId().equals(getAgentBranch()) && tempTable.getServiceGroupCode().equalsIgnoreCase("C")) {
				RequestContext.getCurrentInstance().execute("cashcheck.show();");
				setErrmsg("Cash Product already exist ");
				//setServicebankBranchId(null);
				setAgentBranch(null);
				setBankBranchCode(null);
				return;
			}
		}


		fetchingBankBranchCode();

	}


	public void clearAgentBranch() {
		setAgentBranch(null);
		/*if (getTempAccountNumber() != null) {
			setBankAccountNumber(getTempAccountNumber());
		}*/
	}

	public void checkingDuplicatesForDiffServiceandAccount(){

		try {
			// checking duplicates if master id is old 							
			if(getBeneficarymasterId() != null){
				HashMap<String, Object> checkDuplicates = checkDuplicatesForCashBankingChannel(getBeneficarymasterId());
				Boolean status = (Boolean)checkDuplicates.get("Status");
				BeneficaryAccount beneAcc = (BeneficaryAccount)checkDuplicates.get("BeneAccRec");
				System.out.println("Customer Id :" +getCustomerNo());
				if(status != null && !status){
					if(beneAcc != null){
						if(beneAcc.getBeneficaryAccountSeqId() != null){
							// checking relationship and customer id matching or not
							List<BeneficaryRelationship> lstBeneRelationShip = iPersonalRemittanceService.isBenificaryRelationExist(getBeneficarymasterId(), beneAcc.getBeneficaryAccountSeqId());
							if(lstBeneRelationShip != null && !lstBeneRelationShip.isEmpty() && getCustomerNo() != null){
								Boolean checkStatus = false;
								for (BeneficaryRelationship beneficaryRelationship : lstBeneRelationShip) {
									checkStatus = false;
									if(beneficaryRelationship.getCustomerId().getCustomerId().compareTo(getCustomerNo())==0){
										checkStatus = true;
										break;
									}else{
										checkStatus = false;
									}
								}
								if(checkStatus){
									// duplicates exists - Beneficiary Account checking MasterSeqId,BankCode,BankCountryId,currencyId 
									setErrmsg("This combination already exist in datatable");
									RequestContext.getCurrentInstance().execute("csp.show();");
									return;
								}else{
									fetchingBankBranchCode();
								}
							}else{
								// Relationship coming empty - not possible
								System.out.println("Relationship coming empty - not possible");
							}
						}else{
							// beneficiary account Seq Id null - not possible
							System.out.println("beneficiary account Seq Id null - not possible");
						}
					}else{
						// beneficiary account null - not possible
						System.out.println(" beneficiary account null - not possible");
					}
				}else{
					fetchingBankBranchCode();
				}

			}else{
				fetchingBankBranchCode();
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg(e.getMessage());
			return;
		}

		/*if(getBeneMatsterSeqId() != null){
			Boolean checkDuplicates = checkDuplicatesForCashBankingChannel(getBeneMatsterSeqId());
			if(!checkDuplicates){
				// duplicates exists
				setErrmsg("This combination already exist in datatable");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}else{
				fetchingBankBranchCode();
			}
		}else{
			fetchingBankBranchCode();
		}*/
	}


	// variables started
	public Boolean getBooAotherLang() {
		return booAotherLang;
	}
	public void setBooAotherLang(Boolean booAotherLang) {
		this.booAotherLang = booAotherLang;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerLocalFullName() {
		return customerLocalFullName;
	}
	public void setCustomerLocalFullName(String customerLocalFullName) {
		this.customerLocalFullName = customerLocalFullName;
	}

	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public BigDecimal getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(BigDecimal customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public List<ViewRoutingAgents> getLstAgentsBanks() {
		return lstAgentsBanks;
	}
	public void setLstAgentsBanks(List<ViewRoutingAgents> lstAgentsBanks) {
		this.lstAgentsBanks = lstAgentsBanks;
	}

	public String getBeneRelationshipName() {
		return beneRelationshipName;
	}
	public void setBeneRelationshipName(String beneRelationshipName) {
		this.beneRelationshipName = beneRelationshipName;
	}

	public String getBeneNationalaityName() {
		return beneNationalaityName;
	}
	public void setBeneNationalaityName(String beneNationalaityName) {
		this.beneNationalaityName = beneNationalaityName;
	}

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

	/*public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}*/

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

	/*public BigDecimal getBeneficaryBankBranchId() {
		return beneficaryBankBranchId;
	}
	public void setBeneficaryBankBranchId(BigDecimal beneficaryBankBranchId) {
		this.beneficaryBankBranchId = beneficaryBankBranchId;
	}*/

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

	public BigDecimal getActivatebeneficaryRelationshipId() {
		return activatebeneficaryRelationshipId;
	}
	public void setActivatebeneficaryRelationshipId(BigDecimal activatebeneficaryRelationshipId) {
		this.activatebeneficaryRelationshipId = activatebeneficaryRelationshipId;
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

	public String getBenifisCountryName() {
		return benifisCountryName;
	}
	public void setBenifisCountryName(String benifisCountryName) {
		this.benifisCountryName = benifisCountryName;
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

	public BigDecimal getMapSequenceId() {
		return mapSequenceId;
	}
	public void setMapSequenceId(BigDecimal mapSequenceId) {
		this.mapSequenceId = mapSequenceId;
	}

	public String getWesternunionBank() {
		return westernunionBank;
	}
	public void setWesternunionBank(String westernunionBank) {
		this.westernunionBank = westernunionBank;
	}

	public String getWesternunionBranch() {
		return westernunionBranch;
	}
	public void setWesternunionBranch(String westernunionBranch) {
		this.westernunionBranch = westernunionBranch;
	}

	public boolean isWesternunionPanel() {
		return westernunionPanel;
	}
	public void setWesternunionPanel(boolean westernunionPanel) {
		this.westernunionPanel = westernunionPanel;
	}

	public boolean isBooEnableBankChannelPanel() {
		return booEnableBankChannelPanel;
	}
	public void setBooEnableBankChannelPanel(boolean booEnableBankChannelPanel) {
		this.booEnableBankChannelPanel = booEnableBankChannelPanel;
	}

	public Boolean getBooDisableBeneficaryType() {
		return booDisableBeneficaryType;
	}
	public void setBooDisableBeneficaryType(Boolean booDisableBeneficaryType) {
		this.booDisableBeneficaryType = booDisableBeneficaryType;
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

	/*public BigDecimal getBeneficaryBankId() {
		return beneficaryBankId;
	}
	public void setBeneficaryBankId(BigDecimal beneficaryBankId) {
		this.beneficaryBankId = beneficaryBankId;
	}*/

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

	public boolean isBooRenderWesterUnion() {
		return booRenderWesterUnion;
	}
	public void setBooRenderWesterUnion(boolean booRenderWesterUnion) {
		this.booRenderWesterUnion = booRenderWesterUnion;
	}
	
	public boolean isBooRenderWesterUnionUpload() {
		return booRenderWesterUnionUpload;
	}
	public void setBooRenderWesterUnionUpload(boolean booRenderWesterUnionUpload) {
		this.booRenderWesterUnionUpload = booRenderWesterUnionUpload;
	}

	public boolean isBooRenderBeneficaryCreation() {
		return booRenderBeneficaryCreation;
	}
	public void setBooRenderBeneficaryCreation(boolean booRenderBeneficaryCreation) {
		this.booRenderBeneficaryCreation = booRenderBeneficaryCreation;
	}

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

	public boolean isBeneAccEditOrCreate() {
		return beneAccEditOrCreate;
	}
	public void setBeneAccEditOrCreate(boolean beneAccEditOrCreate) {
		this.beneAccEditOrCreate = beneAccEditOrCreate;
	}

	public String getSaveOrNextButton() {
		return saveOrNextButton;
	}
	public void setSaveOrNextButton(String saveOrNextButton) {
		this.saveOrNextButton = saveOrNextButton;
	}

	public PersonalRemmitanceBeneficaryDataTable getDataTableBeneObj() {
		return dataTableBeneObj;
	}
	public void setDataTableBeneObj(PersonalRemmitanceBeneficaryDataTable dataTableBeneObj) {
		this.dataTableBeneObj = dataTableBeneObj;
	}

	public boolean isBoorenderBenemaster() {
		return boorenderBenemaster;
	}
	public void setBoorenderBenemaster(boolean boorenderBenemaster) {
		this.boorenderBenemaster = boorenderBenemaster;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	// save condition check in beneficiary creation
	public void beneficiarycheck(){
		setAgeFocus(false);
		try {
			
			// bank account type check
			boolean chkAccTypeValue = chkBeneficiaryAccountType();
			if(!chkAccTypeValue){
				log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getBenifisCountryId());
				log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getBankAccountType());
				log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getCustomerId());
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Account Type Mismatch");
				return;
			}
			
			if ((getTelephoneNumber() == null || getTelephoneNumber().trim().equals("")) && getMobileNumber() == null) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Please enter Telephone or Mobile");
			} else if (getTelephoneNumber() != null && getCountryCode() == null) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Please enter Telephone Country Code");
			} else if (getMobileNumber() != null && getMcountryCode() == null) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Please enter Mobile Country Code");
			} else {
				// bene age , date of birth and year of birth any one mandatory
				/*if(getAge() == null && getDateOfBrith() == null && getYearOfBrith() == null)
				{
					setErrmsg("Age or Date of Birth or Year of Birth any one mandatory!!!");
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}else{
					saveBeneficaryCreation();
				}*/
				
				saveBeneficaryCreation();
			}	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// saving beneficiary creation
	public void saveBeneficaryCreation() throws ParseException {

		lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));

		if(lstfetchCashId != null && !lstfetchCashId.isEmpty()){
			if(getServiceGroupId() != null && (getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0)){
				if(getAgentMaster() != null && getAgentBranch() != null){
					//saveBeneficiaryCashBankingChannel();
					saveBenificaryToDataBase();
				}else{
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg("Agent Bank and Branch Mandatory");
					return;
				}
			}else{
				if(getServicebankBranchId()==null)
				{
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg("Please Choose Branch");
					return;
				}
				//saveBeneficiaryCashBankingChannel();
				saveBenificaryToDataBase();
			}	
		}else{
			if(getServicebankBranchId()==null)
			{
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Please Choose Branch");
				return;
			}
			//saveBeneficiaryCashBankingChannel();
			saveBenificaryToDataBase();
		}
	}

	// beneficiary save for new and old account number
	public void saveBenificaryToDataBase(){
		try {

			setDisableSaveBack(true);
			System.out.println("Beneficary Master ----------------------------- > ");
			BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
			BeneficaryAccount beneficaryAccount = new BeneficaryAccount();
			BeneficaryContact contact = new BeneficaryContact();
			BeneficaryRelationship relationship = new BeneficaryRelationship();

			// for new beneficiary save
			if(!isBeneAccEditOrCreate()){
				setBeneficarymasterId(null);
			}
			setBeneficaryAccountSeqId(null);
			BeneficaryMaster beneficaryMasterDt = null;

			// checking duplicates if master id will be empty here 							
			HashMap<String, Object> checkDuplicates = checkDuplicatesForCashBankingChannel(getBeneficarymasterId());
			Boolean status = (Boolean)checkDuplicates.get("Status");
			BeneficaryAccount beneAcc = (BeneficaryAccount)checkDuplicates.get("BeneAccRec");
			System.out.println("Customer Id :" +getCustomerNo());
			if(status != null && !status){
				if(beneAcc != null){
					if(beneAcc.getBeneficaryAccountSeqId() != null && beneAcc.getBeneficaryMaster().getBeneficaryMasterSeqId() != null){
						// checking relationship and customer id matching or not
						setBeneficarymasterId(beneAcc.getBeneficaryMaster().getBeneficaryMasterSeqId());
						setBeneficaryAccountSeqId(beneAcc.getBeneficaryAccountSeqId());
						if(beneAcc.getBankAccountNumber() != null){
							setServicebankBranchId(beneAcc.getBankBranch().getBankBranchId());
						}
						List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices.checkBenificaryRelationExist(getBeneficarymasterId(), beneAcc.getBeneficaryAccountSeqId());
						if(lstBeneRelationShip != null && !lstBeneRelationShip.isEmpty() && getCustomerNo() != null){
							Boolean checkStatus = false;
							String errorStatus = Constants.No;
							List<BigDecimal> duplCheck = new ArrayList<BigDecimal>();
							for (BeneficaryRelationship beneficaryRelationship : lstBeneRelationShip) {
								checkStatus = false;
								errorStatus = Constants.No;
								if(!duplCheck.contains(beneficaryRelationship.getBeneficaryRelationshipId())){
									duplCheck.add(beneficaryRelationship.getBeneficaryRelationshipId());
									if(beneficaryRelationship.getCustomerId().getCustomerId() != null && getCustomerNo() != null && beneficaryRelationship.getCustomerId().getCustomerId().compareTo(getCustomerNo())==0){
										checkStatus = true;
										if(beneficaryRelationship.getIsActive().equalsIgnoreCase(Constants.Yes)){
											setActivatebeneficaryRelationshipId(null);
											errorStatus = Constants.Yes;
											break;
										}else{
											setActivatebeneficaryRelationshipId(beneficaryRelationship.getBeneficaryRelationshipId());
											errorStatus = Constants.No;
											break;
										}
									}else{
										checkStatus = false; 
									}
								}
							}
							if(checkStatus){
								if(errorStatus.equalsIgnoreCase(Constants.Yes)){
									setBankAccountNumber(null);
									// beneficiary exists , need name of the customer
									// duplicates exists - Beneficiary Account checking MasterSeqId,BankCode,BankCountryId,currencyId 
									setErrmsg("This combination already exist for Same Customer. Please check Beneficiary list");
									log.info("Warning Message for csp : " + getErrmsg());
									RequestContext.getCurrentInstance().execute("csp.show();");
									return;
								}else{
									setErrmsg("This combination already exist for Same Customer but De-Activated!!. Do you want to Activate ?");
									log.info("Warning Message for deactivedRecord : " + getErrmsg());
									RequestContext.getCurrentInstance().execute("deactivedRecord.show();");
									//RequestContext.getCurrentInstance().execute("csp.show();");
									// if creating only enter. Edit of service and account should not enter
									if(!isBeneAccEditOrCreate()){
										// beneficiary exists , need name of the customer
										List<BeneficaryMaster> lstBeneDetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
										List<BeneficaryRelationship> lstBeneRelationShipDetails = lstBeneRelationShip;
										BeneficaryAccount beneAccDetails = beneAcc;
										// bank account type fetching
										beneficiaryStatusList();
										fetchAllBeneficiaryDetails(lstBeneDetails,lstBeneRelationShipDetails,beneAccDetails);
									}else{
										// if different service and different account

										// different customer for same account details with same telephone
										List<BeneficaryMaster> lstBeneMasterDetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
										if(lstBeneMasterDetails != null && lstBeneMasterDetails.size() != 0){
											beneficaryMasterDt = lstBeneMasterDetails.get(0);
										}

										beneficaryMaster = beneficaryMasterDt;
										beneficaryAccount = beneAcc;
										contact = null;
										relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
										
										String errMsg = checkBeneficiaryDetailsWithProc();
										
										if(errMsg != null){
											setErrmsg(errMsg);
											log.info("Warning Message for csp : " + getErrmsg());
											RequestContext.getCurrentInstance().execute("csp.show();");
											return;
										}else{
											// proceed to save
											// creating record for relationship only
											beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
										}

									}
								}
							}else{
								// new Record for this customer but record exists for different customer
								// if creating only enter. Edit of service and account should not enter
								if(!isBeneAccEditOrCreate()){
									// beneficiary exists , need name of the customer
									// checking telephone and mobile with beneficiary contact details
									List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(getBeneficarymasterId());
									Boolean teleMobCheck = false;
									BeneficaryContact contactExist = null;
									for (BeneficaryContact beneficaryContact : telePhone) {
										if (telePhone != null && telePhone.size() != 0) {
											if(beneficaryContact.getTelephoneNumber()!=null){
												if(getTelephoneNumber() != null && !getTelephoneNumber().equalsIgnoreCase("") && getTelephoneNumber().equalsIgnoreCase(beneficaryContact.getTelephoneNumber())){
													// old
													contactExist = beneficaryContact;
													teleMobCheck = true;
													break;
												}else if(getMobileNumber() != null  && getMobileNumber().compareTo(new BigDecimal(beneficaryContact.getTelephoneNumber()))==0){
													// old
													contactExist = beneficaryContact;
													teleMobCheck = true;
													break;
												}else{
													// new
													teleMobCheck = false;
												}
											}else if(beneficaryContact.getMobileNumber() !=null){
												if(getTelephoneNumber() != null && !getTelephoneNumber().equalsIgnoreCase("") && beneficaryContact.getMobileNumber().compareTo(new BigDecimal(getTelephoneNumber()))==0){
													// old
													contactExist = beneficaryContact;
													teleMobCheck = true;
													break;
												}else if(getMobileNumber() != null && getMobileNumber().compareTo(beneficaryContact.getMobileNumber())==0){
													// old
													contactExist = beneficaryContact;
													teleMobCheck = true;
													break;
												}else{
													// new
													teleMobCheck = false;
												}
											}
										}
									}
									if(teleMobCheck){
										// different customer for same account details with same telephone
										List<BeneficaryMaster> lstBeneMasterDetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
										if(lstBeneMasterDetails != null && lstBeneMasterDetails.size() != 0){
											beneficaryMasterDt = lstBeneMasterDetails.get(0);
										}

										beneficaryMaster = beneficaryMasterSave(beneficaryMasterDt);
										beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
										contact = contactExist;
										relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
										
										String errMsg = checkBeneficiaryDetailsWithProc();
										
										if(errMsg != null){
											setErrmsg(errMsg);
											log.info("Warning Message for csp : " + getErrmsg());
											RequestContext.getCurrentInstance().execute("csp.show();");
											return;
										}else{
											// proceed to save
											// creating record for master , account if modified and created relationship only
											beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
										}

									}else{
										// different customer for same account details with different telephone
										List<BeneficaryMaster> lstBeneMasterDetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
										if(lstBeneMasterDetails != null && lstBeneMasterDetails.size() != 0){
											beneficaryMasterDt = lstBeneMasterDetails.get(0);
										}

										beneficaryMaster = beneficaryMasterSave(beneficaryMasterDt);
										beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
										relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
										contact = saveBeneficaryTelephone(beneficaryMaster);
										
										String errMsg = checkBeneficiaryDetailsWithProc();
										
										if(errMsg != null){
											setErrmsg(errMsg);
											log.info("Warning Message for csp : " + getErrmsg());
											RequestContext.getCurrentInstance().execute("csp.show();");
											return;
										}else{
											// proceed to save
											// creating record for master , account if modified and created contact , relationship only
											beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
										}

									}
								}else{
									// if different service and different account

									// different customer for same account details with same telephone
									List<BeneficaryMaster> lstBeneMasterDetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
									if(lstBeneMasterDetails != null && lstBeneMasterDetails.size() != 0){
										beneficaryMasterDt = lstBeneMasterDetails.get(0);
									}

									beneficaryMaster = beneficaryMasterDt;
									beneficaryAccount = beneAcc;
									contact = null;
									relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
									
									String errMsg = checkBeneficiaryDetailsWithProc();
									
									if(errMsg != null){
										setErrmsg(errMsg);
										log.info("Warning Message for csp : " + getErrmsg());
										RequestContext.getCurrentInstance().execute("csp.show();");
										return;
									}else{
										// proceed to save
										// creating new record for relationship only
										beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
									}

								}
							}
						}else{
							// Relationship coming empty - not possible
							System.out.println("Relationship coming empty - not possible");
						}
					}else{
						// beneficiary account Seq Id null - not possible
						System.out.println("beneficiary account Seq Id null - not possible");
					}
				}else{
					// beneficiary account null - not possible
					System.out.println(" beneficiary account null - not possible");
				}
			}else{
				// new Record
				try{
					if(isBeneAccEditOrCreate()){
						// beneficiary exists , need name of the customer
						// checking telephone and mobile with beneficiary contact details
						List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(getBeneficarymasterId());
						Boolean teleMobCheck = false;
						BeneficaryContact contactExist = null;
						for (BeneficaryContact beneficaryContact : telePhone) {
							if (telePhone != null && telePhone.size() != 0) {
								if(beneficaryContact.getTelephoneNumber()!=null){
									if(getTelephoneNumber() != null && !getTelephoneNumber().equalsIgnoreCase("") && getTelephoneNumber().equalsIgnoreCase(beneficaryContact.getTelephoneNumber())){
										// old
										contactExist = beneficaryContact;
										teleMobCheck = true;
										break;
									}else if(getMobileNumber() != null && getMobileNumber().compareTo(new BigDecimal(beneficaryContact.getTelephoneNumber()))==0){
										// old
										contactExist = beneficaryContact;
										teleMobCheck = true;
										break;
									}else{
										// new
										teleMobCheck = false;
									}
								}else if(beneficaryContact.getMobileNumber() !=null){
									if(getTelephoneNumber() != null && !getTelephoneNumber().equalsIgnoreCase("") && beneficaryContact.getMobileNumber().compareTo(new BigDecimal(getTelephoneNumber()))==0){
										// old
										contactExist = beneficaryContact;
										teleMobCheck = true;
										break;
									}else if(getMobileNumber().compareTo(beneficaryContact.getMobileNumber())==0){
										// old
										contactExist = beneficaryContact;
										teleMobCheck = true;
										break;
									}else{
										// new
										teleMobCheck = false;
									}
								}
							}
						}
						if(teleMobCheck){
							// different customer for same account details with same telephone
							List<BeneficaryMaster> lstBeneMasterDetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
							if(lstBeneMasterDetails != null && lstBeneMasterDetails.size() != 0){
								beneficaryMasterDt = lstBeneMasterDetails.get(0);
							}

							beneficaryMaster = beneficaryMasterSave(beneficaryMasterDt);
							beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
							contact = contactExist;
							relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
							
							String errMsg = checkBeneficiaryDetailsWithProc();
							
							if(errMsg != null){
								setErrmsg(errMsg);
								log.info("Warning Message for csp : " + getErrmsg());
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}else{
								// proceed to save
								//  creating record for master , account if modified and created relationship only
								beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
							}

						}else{
							// different customer for same account details with different telephone
							List<BeneficaryMaster> lstBeneMasterDetails = iPersonalRemittanceService.getAllMasterValues(getBeneficarymasterId());
							if(lstBeneMasterDetails != null && lstBeneMasterDetails.size() != 0){
								beneficaryMasterDt = lstBeneMasterDetails.get(0);
							}

							beneficaryMaster = beneficaryMasterSave(beneficaryMasterDt);
							beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
							relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
							contact = saveBeneficaryTelephone(beneficaryMaster);
							
							String errMsg = checkBeneficiaryDetailsWithProc();
							
							if(errMsg != null){
								setErrmsg(errMsg);
								log.info("Warning Message for csp : " + getErrmsg());
								RequestContext.getCurrentInstance().execute("csp.show();");
								return;
							}else{
								// proceed to save
								// creating record for master , account if modified and created contact , relationship only
								beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
							}

						}
					}else{
						// Total New Beneficiary
						beneficaryMaster = beneficaryMasterSave();
						contact = saveBeneficaryTelephone(beneficaryMaster);
						beneficaryAccount = saveBeneficaryAccount(beneficaryMaster);
						relationship = saveBeneficiaryRelationshipDetails(beneficaryMaster, beneficaryAccount);
						
						String errMsg = checkBeneficiaryDetailsWithProc();
						
						if(errMsg != null){
							setErrmsg(errMsg);
							log.info("Warning Message for csp : " + getErrmsg());
							RequestContext.getCurrentInstance().execute("csp.show();");
							return;
						}else{
							// proceed to save
							// creating new record for all tables
							beneficaryCreation.concurrentSaveBeneficaryDetails(beneficaryMaster, contact, beneficaryAccount, relationship);
						}

					}

				}catch(Exception e){
					setErrmsg(e.getMessage());
					log.info("Warning Message for csp : " + getErrmsg());
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}
			}

			setBeneficarymasterId(beneficaryMaster.getBeneficaryMasterSeqId());
			String errorMessage = null;

			if(getBenifisBankId() != null && getServicebankBranchId() != null){
				errorMessage = beneficaryCreation.getBeneDetailProce(beneficaryMaster.getBeneficaryMasterSeqId(), getBenifisBankId(), getServicebankBranchId(), beneficaryAccount.getBeneficaryAccountSeqId(), getBenifisCurrencyId(), getCustomerNo());
			}else{
				errorMessage = beneficaryCreation.getBeneDetailProce(beneficaryMaster.getBeneficaryMasterSeqId(), getAgentMaster(), getAgentBranch(), beneficaryAccount.getBeneficaryAccountSeqId(), getBenifisCurrencyId(), getCustomerNo());
			}

			if (errorMessage == null) {
				RequestContext.getCurrentInstance().execute("beneficarycomplete.show();");
			} else {
				setErrmsg("EX_POPULATE_BENE_DT : " + errorMessage);
				log.info("Warning Message for csp : " + getErrmsg());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}

		} catch (Exception e) {
			setErrmsg(e.getMessage());
			log.info("Warning Message for csp : " + getErrmsg());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
	}

	// call check beneficiary for validate the beneficiary details EX_CHK_BENEFICIARY
	public String checkBeneficiaryDetailsWithProc(){

		String errMsg = null;

		try{

			HashMap<String, Object> checkBeneDetails = new HashMap<String, Object>();

			checkBeneDetails.put("BeneBankCountryId", getBenifisCountryId());
			//checkBeneDetails.put("BeneBankAccountType", getBankAccountType());
			checkBeneDetails.put("BeneServiceGroupId", getServiceGroupId());
			if(getServiceTypeId() != null){
				checkBeneDetails.put("BeneBankId", getAgentMaster());
				checkBeneDetails.put("BeneBankBranchId", getAgentBranch());
			}else{
				checkBeneDetails.put("BeneBankId", getBenifisBankId());
				checkBeneDetails.put("BeneBankBranchId", getServicebankBranchId());
			}
			checkBeneDetails.put("BeneCurrencyId", getBenifisCurrencyId());
			checkBeneDetails.put("BeneAccountNumber", getBankAccountNumber());
			checkBeneDetails.put("BeneBankServiceProvider", getServiceTypeId());
			checkBeneDetails.put("BeneFirstName", getFirstName());
			checkBeneDetails.put("BeneSecondName", getSecondName());
			checkBeneDetails.put("BeneThirdName", getThirdName());
			checkBeneDetails.put("BeneFourthName", getFourthName());
			checkBeneDetails.put("BeneFifthName", getFifthName());
			checkBeneDetails.put("BeneArabicFirstName", getFirstLName());
			checkBeneDetails.put("BeneArabicSecondName", getSecondLName());
			checkBeneDetails.put("BeneArabicThirdName", getThirdLName());
			checkBeneDetails.put("BeneArabicFourthName", getFourthLName());
			checkBeneDetails.put("BeneTelephone", getTelephoneNumber().trim());
			checkBeneDetails.put("BeneMobileNumber", getMobileNumber());
			checkBeneDetails.put("BeneCountryId", getBeneCountryid());
			checkBeneDetails.put("BeneStateId", getBenifisStateId());
			checkBeneDetails.put("BeneDistrictId", getDistictId());
			checkBeneDetails.put("BeneCityId", getCityId());
			
			log.info("checkBeneficiaryDetailsWithProc : " + checkBeneDetails.toString());

			errMsg = beneficaryCreation.checkBeneDetailsValidation(checkBeneDetails);

		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		return errMsg;
	}
	
	//clear only beneficiary master
	public void clearBeneMaster(){

		setFirstName(null);
		setSecondName(null);
		setThirdName(null);
		setFourthName(null);
		setFifthName(null);
		setFirstLName(null);
		setSecondLName(null);
		setThirdLName(null);
		setFourthLName(null);
		setFifthLName(null);
		setRelationId(null);
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
		//setBenifisCountryId(null);
		setDistictId(null);
		setCityId(null);
		lststate.clear();
		lstDistict.clear();
		lstCity.clear();
		setReadOnlyOccupation(false);
		setMinagevalidation(false);
		setDisablerelation(false);
		setReadOnlyFirstName(false);
		setReadOnlySecondName(false);
		setReadOnlyThirdName(false);
		setReadOnlyAddress(false);
		setReadOnlyNationality(false);
		setReadOnlyRelations(false);
		setReadOnlyFifthName(false);
		setReadOnlyFourthName(false);
		setReadOnlyDateOfBirth(false);
		setReadOnlyYearOfBirth(false);
		setReadOnlyAge(false);
		setDisableDataOfBirth(false);
		setDisableBeneficiaryAccountExist(false);
		if (getDateOfBrith() != null) {
			setDisableResetDataOfBirth(false);
		}
		setDisablerelation(false);
		setDisableNationality(false);
		/*if (!getBeneficaryStatusName().equals(Constants.CUSTOMERTYPE_INDIVIDUAL)) {
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
		}*/	
	}

	private Boolean booBenefiPersonalRemit=false;
	private Boolean booBenefiCorporateRemit=false;

	public Boolean getBooBenefiPersonalRemit() {
		return booBenefiPersonalRemit;
	}

	public void setBooBenefiPersonalRemit(Boolean booBenefiPersonalRemit) {
		this.booBenefiPersonalRemit = booBenefiPersonalRemit;
	}

	public Boolean getBooBenefiCorporateRemit() {
		return booBenefiCorporateRemit;
	}

	public void setBooBenefiCorporateRemit(Boolean booBenefiCorporateRemit) {
		this.booBenefiCorporateRemit = booBenefiCorporateRemit;
	}
	public void toFetchRelationCorporate(){
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
	}
	
	private String beneAddressDetails;
	private String beneHouseNo;
	private String beneFlatNo;
	private String beneStreetNo;

	public String getBeneAddressDetails() {
		return beneAddressDetails;
	}

	public void setBeneAddressDetails(String beneAddressDetails) {
		this.beneAddressDetails = beneAddressDetails;
	}

	public String getBeneHouseNo() {
		return beneHouseNo;
	}

	public void setBeneHouseNo(String beneHouseNo) {
		this.beneHouseNo = beneHouseNo;
	}

	public String getBeneFlatNo() {
		return beneFlatNo;
	}

	public void setBeneFlatNo(String beneFlatNo) {
		this.beneFlatNo = beneFlatNo;
	}

	public String getBeneStreetNo() {
		return beneStreetNo;
	}

	public void setBeneStreetNo(String beneStreetNo) {
		this.beneStreetNo = beneStreetNo;
	}

	public Boolean getAgeFocus() {
		return ageFocus;
	}

	public void setAgeFocus(Boolean ageFocus) {
		this.ageFocus = ageFocus;
	}
	
	public String getFifthLName() {
		return fifthLName;
	}

	public void setFifthLName(String fifthLName) {
		this.fifthLName = fifthLName;
	}
	
	
	/*private WesternUnionBeneficaryDataTable dataTableBeneWUObj = new WesternUnionBeneficaryDataTable();
	
	public WesternUnionBeneficaryDataTable getDataTableBeneWUObj() {
		return dataTableBeneWUObj;
	}

	public void setDataTableBeneWUObj(WesternUnionBeneficaryDataTable dataTableBeneWUObj) {
		this.dataTableBeneWUObj = dataTableBeneWUObj;
	}

		// first time edit of beneficiary all details
		public void renderBeneficiaryFullWUEditNavigation(){
			// clearing all fields
			clear();
			if(nationalityList!=null && nationalityList.isEmpty()) {
				nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
			}
			if(beneCountryList!=null && beneCountryList.isEmpty() && lstCountry != null && lstCountry.isEmpty()){
				beneCountryList = generalService.getCountryList(sessionmanage.getLanguageId());
			}else{
				beneCountryList = lstCountry;
			}
			lstCountry = beneCountryList;

			editWUBeneficaryFirstTime();
		}
		
		
		public void editWUBeneficaryFirstTime(){
			if (getDataTableBeneObj() != null) {
				try {
					WesternUnionBeneficaryDataTable dataTableBene = getDataTableBeneWUObj();
					setEnableSearchPage(null);
					editBeneficarayWUPageFirstTime(dataTableBene);
				} catch (Exception e) {
					ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
					try {
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("error", e.getMessage());
						//context.redirect("../remittance/PersonalRemittance.xhtml");
						WesternUnionTransferBean<T> objectWU = (WesternUnionTransferBean)appContext.getBean("wuBean");
						objectPersonalRemittance.personalRemittanceBackFromBene(null);
						FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
					} catch (Exception e1) {
						System.out.println("Exception occured" + e1);
					}
				}
			}
		}
	
		
		// fetch all beneficiary details from dataTable object
		public void editBeneficarayWUPageFirstTime(WesternUnionBeneficaryDataTable datatabledetails){
			
			
			
			log.info("Entering into editBeneficarayWUPageFirstTime method ");
			
			try{
				log.info("Entering into edit method ");
				log.info(datatabledetails);
				log.info(datatabledetails.getBeneficaryMasterSeqId());

				// clearing first and second panels
				clear();
				clearSearch();

				setBeneficarymasterId(datatabledetails.getBeneficaryMasterSeqId());
				setBeneficaryRelationshipId(datatabledetails.getBeneficiaryRelationShipSeqId());
				setBeneficaryAccountSeqId(datatabledetails.getBeneficiaryAccountSeqId());

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

				*//** added by Rabil on 03/12/2015 for EX_P_BANNED_NAME_CHECK*//* 
				String engName =getFirstName()+" "+ getSecondName()+" "+getThirdName();
				String localName =getFirstLName()+" "+ getSecondLName()+" "+getThirdLName();

				String proceErrorMessage = getiPersonalRemittanceService().getBannedNameCheckProcedure(sessionmanage.getCountryId(), engName,localName);

				if(proceErrorMessage!=null && proceErrorMessage.length()>0){
					//throw new Exception(proceErrorMessage);
					setErrmsg(proceErrorMessage);
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
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
				if(datatabledetails.getMapSequenceId()!=null){
					setMapSequenceId(datatabledetails.getMapSequenceId());
				}

				// nationality 
				if(datatabledetails.getNationality() != null){
					setNationalityName(new BigDecimal(datatabledetails.getNationality()));
				}

				// beneficiary Contact details
				List<BeneficaryContact> contactDetails = beneficaryCreation.getTelephoneDetails(datatabledetails.getBeneficaryMasterSeqId());		
				if (contactDetails != null && !contactDetails.isEmpty()) {
					
					BeneficaryContact benecontact = contactDetails.get(0);
					if(benecontact.getCreatedBy() != null && benecontact.getCreatedDate() != null){
						setContactCreatedBy(benecontact.getCreatedBy());
						setContactCreatedDate(benecontact.getCreatedDate());
					}else{
						setContactCreatedBy(datatabledetails.getCreatedBy());
						setContactCreatedDate(datatabledetails.getCreatedDate());
					}
					
					List<CountryMasterDesc> countryList =beneficaryCreation.checkCountryCode(benecontact.getCountryTelCode());
					if(countryList!= null && !countryList.isEmpty())
					{
						if(countryList.size() >1)
						{
							//throw new Exception("Setup Error - Duplicate Tel Country Code exist "+ contactDetails.get(0).getCountryTelCode());
							setErrmsg("Setup Error - Duplicate Tel Country Code exist "+ benecontact.getCountryTelCode());
							RequestContext.getCurrentInstance().execute("csp.show();");
						}
					}

					if(benecontact.getCountryTelCode() != null){
						setCountryCode(benecontact.getCountryTelCode());
						setMcountryCode(benecontact.getCountryTelCode());
					}else{
						List<CountryMasterDesc> countryListForContactCode = beneficaryCreation.fetchCountryContactCode(datatabledetails.getCountryId());
						if(countryListForContactCode.size() != 0){
							setCountryCode(countryListForContactCode.get(0).getFsCountryMaster().getCountryTelCode());
							setMcountryCode(countryListForContactCode.get(0).getFsCountryMaster().getCountryTelCode());
						}
					}

					setBeneficaryTelephoneSeqId(benecontact.getBeneficaryTelephoneSeqId());
					if (contactDetails.get(0).getTelephoneNumber() != null) {
						setTelephoneNumber(benecontact.getTelephoneNumber());
					} else {
						setTelephoneNumber(null);
					}
					if (contactDetails.get(0).getMobileNumber() != null) {
						setMobileNumber(benecontact.getMobileNumber());
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
				setAge(datatabledetails.getAge());
				
				if(getAge() == null && getDateOfBrith() == null && getYearOfBrith() == null)
				{
					setReadOnlyDateOfBirth(false);
					setReadOnlyYearOfBirth(false);
					setReadOnlyAge(false);
					setDisableDataOfBirth(false);
					setDisableResetDataOfBirth(false);
				}
				
				setOccupation(datatabledetails.getOccupation());

				// bank country Id and Name
				setBenifisCountryId(datatabledetails.getCountryId());
				if(getBenifisCountryId() != null ){
					lstBankCountrystate = generalService.getStateList(sessionmanage.getLanguageId(), getBenifisCountryId());
				}
				benServiceCurrencyList();
				setBenifisCountryName(datatabledetails.getCountryName());
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
				setBeneficaryBankName(datatabledetails.getBankName());
				setBankName(datatabledetails.getBankName());
				setBankCode(datatabledetails.getBankCode());
				setServicebankBranchId(datatabledetails.getBranchId());
				setBankBranchName(datatabledetails.getBankBranchName());
				setBankBranchCode(datatabledetails.getBranchCode());

				setBenifisCurrencyId(datatabledetails.getCurrencyId());

				log.info("Exit into edit method ");
			}catch(Exception e){
				log.info("editBeneficarayPageFirstTime() : "+e.getMessage());
			}

		}
		
		public void checkingEditBacktoWU(){
			RequestContext.getCurrentInstance().execute("backcheckforWU.show();");
		}
		
		
		// update beneficiary WesternUnion modification
		public void updateBeneficaryForWesternUnion() {

			if (getTelephoneNumber() == null && getMobileNumber() == null) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Please enter mobile number or Telephone number");
				return;
			}

			if (getTelephoneNumber() != null  && (getCountryCode()==null || getCountryCode()!=null &&  getCountryCode().length() == 0)) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Please select Telephone Country Code");
				return;
			}

			if (getMobileNumber() != null  && (getMcountryCode()==null || getMcountryCode()!=null &&  getMcountryCode().length() == 0)) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Please select Mobile Country Code");
				return;
			}
			

			if (getCountryCode() == null && getMcountryCode() == null) {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Please enter mobile number Code or Telephone number Code");
				return;
			}

			if ((getCountryCode()!= null && getCountryCode().length() == 0) || (getMcountryCode()!=null &&  getMcountryCode().length() == 0)) {
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
					if (contactDetails.getTelephoneNumber() != null && getTelephoneNumberSelect() != null && contactDetails.getTelephoneNumber().equals(getTelephoneNumberSelect())) {
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
					if (contactDetails.getMobileNumber() != null && getMobileNumberSelect() != null && contactDetails.getMobileNumber().equals(getMobileNumberSelect())) {
						removeMobileContact = contactDetails;
					}
				}
			}

			if(removeMobileContact!=null)
			{
				lstMobileDetails.remove(removeMobileContact);
				beneficaryCreation.deActivateMultipleMobileNumbers(lstMobileDetails);
			}

			// beneficiary master
			BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
			beneficaryMaster.setBeneficaryMasterSeqId(getBeneficarymasterId());
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getApplicationCountryId());
			beneficaryMaster.setApplicationCountryId(countryMaster);
			CountryMaster countryMaster1 = new CountryMaster();
			countryMaster1.setCountryId(getBeneCountryid());
			beneficaryMaster.setFsCountryMaster(countryMaster1);
			beneficaryMaster.setFirstName(getFirstName()==null ? null : getFirstName().toUpperCase());
			beneficaryMaster.setSecondName(getSecondName()==null ? null : getSecondName().toUpperCase());
			beneficaryMaster.setThirdName(getThirdName()==null ? null : getThirdName().toUpperCase());
			beneficaryMaster.setFourthName(getFourthName()==null ? null : getFourthName().toUpperCase());
			beneficaryMaster.setFifthName(getFifthName()==null ? null : getFifthName().toUpperCase());

			beneficaryMaster.setLocalFirstName(getFirstLName());
			beneficaryMaster.setLocalSecondName(getSecondLName());
			beneficaryMaster.setLocalThirdName(getThirdLName());
			beneficaryMaster.setLocalFourthName(getFourthLName());
			beneficaryMaster.setAge(getAge());
			if(getBeneficaryStatusId() != null){
				BeneficaryStatus beneficaryStatus = new BeneficaryStatus();
				beneficaryStatus.setBeneficaryStatusId(getBeneficaryStatusId());
				beneficaryMaster.setBeneficaryStatus(beneficaryStatus);
			}

			beneficaryMaster.setNationality(getNationalityName().toString());
			beneficaryMaster.setDateOfBrith(getDateOfBrith());
			beneficaryMaster.setYearOfBrith(getYearOfBrith());
			beneficaryMaster.setAge(getAge());

			if (getBenifisStateId() != null) {
				StateMaster stateMaster = new StateMaster();
				stateMaster.setStateId(getBenifisStateId());
				beneficaryMaster.setFsStateMaster(stateMaster);
				if(getStateName() != null){
					beneficaryMaster.setStateName(getStateName());
				}else{
					String statename = generalService.getStateName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId());
					if (statename != null) {
						beneficaryMaster.setStateName(statename);
					}
				}
			}

			if (getDistictId() != null) {
				DistrictMaster districtMaster = new DistrictMaster();
				districtMaster.setDistrictId(getDistictId());
				beneficaryMaster.setFsDistrictMaster(districtMaster);
				if(getDistrictName() != null){
					beneficaryMaster.setDistrictName(getDistrictName());
				}else{
					String districtname = generalService.getDistrictName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId(), getDistictId());
					if (districtname != null) {
						beneficaryMaster.setDistrictName(districtname);
					}
				}
			}

			if (getCityId() != null) {
				CityMaster cityMaster = new CityMaster();
				cityMaster.setCityId(getCityId());
				beneficaryMaster.setFsCityMaster(cityMaster);
				if(getCityName() != null){
					beneficaryMaster.setCityName(getCityName());
				}else{
					String cityname = generalService.getCityName(sessionmanage.getLanguageId(), getBeneCountryid(),getBenifisStateId(), getDistictId(), getCityId());
					if (cityname != null) {
						beneficaryMaster.setCityName(cityname);
					}
				}
			}

			beneficaryMaster.setOccupation(getOccupation());
			beneficaryMaster.setNoOfRemittance(getNoOfRemittance());
			beneficaryMaster.setIsActive(Constants.Yes);
			beneficaryMaster.setCreatedBy(getMasterCreatedBy());
			beneficaryMaster.setCreatedDate(getMasterCreatedDate());
			beneficaryMaster.setModifiedBy(sessionmanage.getUserName());
			beneficaryMaster.setModifiedDate(new Date());
			beneficaryMaster.setBeneficaryStatusName(getBeniStatusName());
			
			beneficaryMaster.setBuildingNo(getBeneHouseNo());
			beneficaryMaster.setFlatNo(getBeneFlatNo());
			beneficaryMaster.setStreetNo(getBeneStreetNo());
			
			
			BeneficaryAccount account = new BeneficaryAccount();
			account.setBeneficaryAccountSeqId(getBeneficaryAccountSeqId());
			
			
			


			BeneficaryAccount beneficaryAccount = null;

			try{
				beneficaryAccount = new BeneficaryAccount();

				CountryMaster countryMaster2 = new CountryMaster();
				countryMaster2.setCountryId(sessionmanage.getCountryId()); // application country
				beneficaryAccount.setBeneApplicationCountry(countryMaster2);

				if(getBenifisCountryId() != null){
					CountryMaster countryMaster3 = new CountryMaster();
					countryMaster3.setCountryId(getBenifisCountryId()); // Beneficiary Bank country
					beneficaryAccount.setBeneficaryCountry(countryMaster3);
				}

				if(getBenifisBankId() != null){
					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(getBenifisBankId()); // western union bank id
					beneficaryAccount.setBank(bankMaster);
				}

				beneficaryAccount.setBankCode(Constants.WU_BANK_CODE); // western union bank code

				if(getServicebankBranchId() != null){
					BankBranch bankBranch = new BankBranch();
					bankBranch.setBankBranchId(getServicebankBranchId()); // western union bank branch id
					beneficaryAccount.setBankBranch(bankBranch);
				}
				if(getBankBranchCode() != null){
					beneficaryAccount.setBankBranchCode(getBankBranchCode()); // western union bank branch code
				}

				List<ServiceGroupMaster>  lstServiceGroup = serviceGroupMasterService.toServiceGroupCodeAllValues(Constants.CashCode);
				if(lstServiceGroup.size() != 0){
					ServiceGroupMaster serviceGroup = lstServiceGroup.get(0);

					if(serviceGroup.getServiceGroupId() != null){
						ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
						serviceGroupMaster.setServiceGroupId(serviceGroup.getServiceGroupId()); //cash for western union
						beneficaryAccount.setServicegropupId(serviceGroupMaster);
					}
				}

				if(getBenifisCurrencyId() != null){
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(getBenifisCurrencyId()); // beneficiary country currency western union
					beneficaryAccount.setCurrencyId(currencyMaster);
				}

				beneficaryAccount.setIsActive(Constants.Yes);
				beneficaryAccount.setCreatedBy(sessionmanage.getUserName());
				beneficaryAccount.setCreatedDate(new Date());

				if(beneficaryMaster.getBeneficaryMasterSeqId() != null){
					BeneficaryMaster  beneficaryMasterId = new BeneficaryMaster();
					beneficaryMasterId.setBeneficaryMasterSeqId(beneficaryMaster.getBeneficaryMasterSeqId());
					beneficaryAccount.setBeneficaryMaster(beneficaryMasterId);
				}
				
				account.setBeneficaryAccountSeqId(getBeneficaryAccountSeqId());
				
				account.setBankAccountTypeId(getBankAccountType());
				account.setSwiftCode(getBeneSwiftCode());
				//account.setBankBranchCode(bankBranch.getBranchCode());
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
				if(getAccountCreatedBy() != null && getAccountCreatedDate() != null){
					account.setCreatedBy(getAccountCreatedBy());
					account.setCreatedDate(getAccountCreatedDate());
				}else{
					account.setCreatedBy(getMasterCreatedBy());
					account.setCreatedDate(getMasterCreatedDate());
				}
				
				account.setModifiedBy(sessionmanage.getUserName());
				account.setModifiedDate(new Date());
				account.setLastJavaRemittence(getLastJavaRemittence());
				account.setRecordStaus(getRecordStaus());
				if (getServiceTypeId() != null) {
					BankMaster bankMasterServiceProvider = new BankMaster();
					bankMasterServiceProvider.setBankId(getServiceTypeId());
					account.setServiceProvider(bankMasterServiceProvider);
					if(getServiceProviderBankBranchId() != null){
						account.setServiceProviderBranchId(getServiceProviderBankBranchId());
					}
				}
				account.setBeneficaryMaster(beneficaryMaster);

			}catch(Exception e) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+ e.getMessage());
				
			}

			
			// beneficiary contact
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
			if(getBeneficarymasterId() != null && getBeneficaryAccountSeqId() != null){
				List<BeneficaryRelationship> lstBeneCnt = iPersonalRemittanceService.isBenificaryRelationExist(getBeneficarymasterId(), getBeneficaryAccountSeqId());
				if(lstBeneCnt != null && !lstBeneCnt.isEmpty()){
					BeneficaryRelationship beneRel = lstBeneCnt.get(0);
					if(beneRel.getCreatedBy() != null && beneRel.getCreatedDate() != null){
						beneficaryRelationship.setCreatedBy(beneRel.getCreatedBy());
						beneficaryRelationship.setCreatedDate(beneRel.getCreatedDate());
					}else{
						beneficaryRelationship.setCreatedBy(getMasterCreatedBy());
						beneficaryRelationship.setCreatedDate(getMasterCreatedDate());
					}
				}else{
					beneficaryRelationship.setCreatedBy(getMasterCreatedBy());
					beneficaryRelationship.setCreatedDate(getMasterCreatedDate());
				}
			}
			
			beneficaryRelationship.setModifiedBy(sessionmanage.getUserName());
			beneficaryRelationship.setModifiedDate(new Date());
			beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
			beneficaryRelationship.setBeneficaryAccount(account);
			if(getMapSequenceId()!=null){
				beneficaryRelationship.setMapSequenceId(getMapSequenceId());
			}
			// beneficiary Contact
			BeneficaryContact contact = new BeneficaryContact();
			contact.setBeneficaryTelephoneSeqId(getBeneficaryTelephoneSeqId());
			contact.setApplicationCountryId(countryMaster);
			if (getTelephoneNumber() != null) {
				contact.setTelephoneNumber(getTelephoneNumber());
			} else {
				contact.setTelephoneNumber(null);
			}
			contact.setIsActive(Constants.Yes);
			
			if(getContactCreatedBy() != null && getContactCreatedDate() != null){
				contact.setCreatedBy(getContactCreatedBy());
				contact.setCreatedDate(getContactCreatedDate());
			}else if(getBeneficaryTelephoneSeqId() != null ) {
				List<BeneficaryContact> lstBeneCnt = beneficaryCreation.getTelephoneDetails(getBeneficaryTelephoneSeqId());
				
				if(lstBeneCnt != null && !lstBeneCnt.isEmpty()){
					BeneficaryContact beneContact = lstBeneCnt.get(0);
					if(beneContact.getCreatedBy() != null && beneContact.getCreatedDate() != null ){
						contact.setCreatedBy(beneContact.getCreatedBy());
						contact.setCreatedDate(beneContact.getCreatedDate());
					}else{
						contact.setCreatedBy(getMasterCreatedBy());
						contact.setCreatedDate(getMasterCreatedDate());
					}
				}
			}
			
			contact.setModifiedBy(sessionmanage.getUserName());
			contact.setModifiedDate(new Date());
			
			contact.setBeneficaryMaster(beneficaryMaster);
			if(getCountryCode() != null && getCountryCode().length() != 0 && getCountryCode().length() <= 10 ){
				contact.setCountryTelCode(getCountryCode());
			}

			contact.setMobileNumber(getMobileNumber());
			
			
			try {
				
				String errMsg = checkBeneficiaryDetailsWithProc();
				
				if(errMsg != null){
					setErrmsg(errMsg);
					log.info("Warning Message for csp : " + getErrmsg());
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}else{
					// proceed to save
					beneficaryCreation.updateBeneficaryDataforWU(beneficaryMaster,account,  beneficaryRelationship, contact);
					
					String errorMessage = null;
					if(getBenifisBankId() != null && getServicebankBranchId() != null){
						errorMessage = beneficaryCreation.getBeneDetailProce(getBeneficarymasterId(),getBenifisBankId(),getServicebankBranchId(),getBeneficaryAccountSeqId(),getBenifisCurrencyId(),getCustomerNo());
					}else{
						errorMessage = beneficaryCreation.getBeneDetailProce(getBeneficarymasterId(), getAgentMaster(), getAgentBranch(), getBeneficaryAccountSeqId(), getBenifisCurrencyId(), getCustomerNo());
					}

					if(errorMessage==null){
						RequestContext.getCurrentInstance().execute("beneficarycomplete.show();");
					}else{
						RequestContext.getCurrentInstance().execute("csp.show();");
						setErrmsg("EX_POPULATE_BENE_DT "+errorMessage);
						return;
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Exception occured while update the Benificary" + e.getMessage());
				return;
			}
		}
		
		
		public void gotoWesternUnion()
		{
			backToWesternUnionScreen();
		}*/
		
	// clear bank account
	public void clearBankAccountNumber(){
		setBankAccountNumber(null);
	}

	public Boolean getDisableBeneficiaryAccountExist() {
		return disableBeneficiaryAccountExist;
	}

	public void setDisableBeneficiaryAccountExist(Boolean disableBeneficiaryAccountExist) {
		this.disableBeneficiaryAccountExist = disableBeneficiaryAccountExist;
	}

	
}

