package com.amg.exchange.beneficiary.bean;
import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.util.Constants;

@Component("beneficiaryNonIndidualBean")
@Scope("session")
public class BeneficiaryCreationNonIndidualBean implements Serializable {/*
	private static final Logger log = Logger.getLogger(BeneficiaryCreationNonIndidualBean.class);
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
	private BankMaster bank;
	private BigDecimal bankAccountNumber;
	private BigDecimal tempAccountNumber;
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
	private BigDecimal telephoneNumber = null;
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
	private BigDecimal benifisStateId;
	private BigDecimal distictId;
	private BigDecimal cityId;
	private boolean disablerelation = false;
	private String countryCode;
	private String mcountryCode;
	 private BigDecimal serviceId; 
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
	// chiru code added
	public String databenificaryservicegroup;
	private BigDecimal dataservicegroupid;
	private String effectiveMinDate;
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

	public BigDecimal getTempAccountNumber() {
		return tempAccountNumber;
	}

	public void setTempAccountNumber(BigDecimal tempAccountNumber) {
		this.tempAccountNumber = tempAccountNumber;
	}

	iCypherSecurity cypherSecurity = new CypherSecurityImpl();
	private List<CurrencyMaster> currencyMasterList = new ArrayList<CurrencyMaster>();
	private List<BankAccountDetails> listCurrencyAccountDetails = new ArrayList<BankAccountDetails>();
	private List<PersonalRemmitanceBeneficaryDataTable> coustomerBeneficaryDTList = new ArrayList<PersonalRemmitanceBeneficaryDataTable>();
	private List<CustomerIdProof> customerDetailsList = new ArrayList<CustomerIdProof>();
	private List<Relations> relationsList = new ArrayList<Relations>();
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
	private CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();
	private List<BankApplicability> bankMasterList = new CopyOnWriteArrayList<BankApplicability>();
	private Boolean mainPanelRender = false;
	
	private List<BeneficaryAccount> lstBeneficaryAccount = new ArrayList<BeneficaryAccount>();
	
	private Map<String, Object> mapTeleExistToCheck = new HashMap<String, Object>();

	
	
	
	
	private List<PersonalRemittanceTeleExistDTBean> lstBeneficaryAccountDailog = new ArrayList<PersonalRemittanceTeleExistDTBean>();
	private List<BeneficaryAccount> lstUpdateBeneficaryAccount = new ArrayList<BeneficaryAccount>();
	
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

	public BigDecimal getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(BigDecimal bankAccountNumber) {
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

	
	 * public BigDecimal getServiceId() { return serviceId; }
	 * 
	 * public void setServiceId(BigDecimal serviceId) { this.serviceId =
	 * serviceId; }
	 
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

	public List<Relations> getRelationsList() {
		return getiPersonalRemittanceService().getRelationsList();
	}

	public void setRelationsList(List<Relations> relationsList) {
		this.relationsList = relationsList;
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

	public BigDecimal getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(BigDecimal telephoneNumber) {
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

	public BigDecimal getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(BigDecimal idNumber) {
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
		 listCurrency=getiPersonalRemittanceService().getCurrencyList(); 
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
		serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());
		lstBank.clear();
		lstBank = generalService.getBankList(getBenifisCountryId());
		benServiceCurrencyList();
		popStatelist();
	}

	public void popbranchlist() {
		popCurrencylistBank();
		lstBankbranch = getiPersonalRemittanceService().getBankbranchlist(getBenifisBankId());
		for (BankMaster bankMaster : lstBank) {
			if (bankMaster.getBankId().compareTo(getBenifisBankId()) == 0) {
				setBankCode(bankMaster.getBankCode());
			}
		}
	}

	public void popbranchcode() {
		for (BankBranch bankBranchMaster : lstBankbranch) {
			if (bankBranchMaster.getBankBranchId().compareTo(getServicebankBranchId()) == 0) {
				setBankBranchCode(bankBranchMaster.getBranchCode());
			}
		}
	}

	public void popCurrencylist() {
		listCurrency = getiPersonalRemittanceService().getCurrencyList(getBenifisCountryId());
	}

	public void popStatelist() {
		lststate = generalService.getStateList(sessionmanage.getLanguageId(), getBenifisCountryId());
	}

	public void popDistict() {
		lstDistict = generalService.getDistrictList(sessionmanage.getLanguageId(), getBenifisCountryId(), getBenifisStateId());
		for (StateMasterDesc stateMaster : lststate) {
			if (stateMaster.getStateDescId().compareTo(getBenifisStateId()) == 0) {
				setStateName(stateMaster.getStateName());
			}
		}
	}

	public void popCitylist() {
		lstCity = generalService.getCityList(sessionmanage.getLanguageId(), getBenifisCountryId(), getBenifisStateId(), getDistictId());
		for (DistrictMasterDesc districtMaster : lstDistict) {
			if (districtMaster.getDistrictDescId().compareTo(getDistictId()) == 0) {
				setDistrictName(districtMaster.getDistrict());
			}
		}
	}

	public void cityNameset() {
		for (CityMasterDesc cityMaster : lstCity) {
			if (cityMaster.getCityMasterId().compareTo(getCityId()) == 0) {
				setCityName(cityMaster.getCityName());
			}
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

	*//**
	 * 
	 *//*
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
			setNationality(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			setNationalityName(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			log.info("#############################################" + customerDetails.getFsCustomer().getDateOfBirth());
			setDateOfBrith(customerDetails.getFsCustomer().getDateOfBirth());
			 setDateOfBrith(null); 
			String teleCountryId = generalService.getTelephoneCountryBasedOnNationality(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			setCountryCode(teleCountryId);
			setMcountryCode(teleCountryId);
			BigDecimal occupationID = generalService.getOccupationId(customerDetails.getFsCustomer().getCustomerId());
			if (occupationID != null) {
				String occupation = generalService.getOccupationDesc(occupationID);
				setOccupation(occupation);
			} else {
				setOccupation("UN-EMPLOYEE");
			}
		}
		log.info("Exit into getCustomerDetails method ");
	}

	// first method after go clicked to fetch all customer details
	public void goFromOldSmartCardpanel() throws ParseException {
		log.info("Entering into goFromOldSmartCardpanel method");
		coustomerBeneficaryDTList.clear();
		lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
		nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
		 lstCountry.clear 
		customerDetailsList = getiPersonalRemittanceService().getCustomerDetails(getIdNumber().toString(), getSelectCard());
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
	}

	// Second Method to Populate Records which is Approved all condition
	public void populateCustomerDetailsFromBeneRelation() {
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
				System.out.println("rrrrrr" + relation.size());
				System.out.println("rrrrrr" + relation.size());
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
					
					 * if
					 * (rel.getCustomerId().getCustomerId().equals(getCustomerNo
					 * ())) {
					 
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
					personalRBDataTable.setBankNameId(rel.getBeneficaryAccount().getBank().getBankId());
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
	}

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

	public void changeIndidualorNonIndidual() {
		for (BeneficaryStatus beneficarystatus : benificaryStatusName) {
			if (beneficarystatus.getBeneficaryStatusId().compareTo(getBeneficaryStatusId()) == 0) {
				setBeneficaryStatusName(beneficarystatus.getBeneficaryStatusName());
				System.out.println("##########" + getBeneficaryStatusName());
				Calendar cal1 = new GregorianCalendar();
				cal1.setTime(new Date());
				// cal1.add(Calendar.YEAR, -Constants.AGE_LIMIT);
				cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
				Date today18 = cal1.getTime();
				SimpleDateFormat form1 = new SimpleDateFormat("dd/MM/yyyy");
				String minDateFinal = form1.format(today18);
				setEffectiveMinDate(minDateFinal);
				if (getBeneficaryStatusName().equals("Individual")) {
					setBeneficaryStatusId(new BigDecimal(1));
					setBeneficaryTypeId(new BigDecimal(1));
					checkBeneficaryType();
					setMinagevalidation(false);
					setRenderBackButton(false);
					setBeneficaryTypeId(new BigDecimal(1));
					setBooRenderBenificaryFirstPanel(false);
					setBooRenderBenificarySearchPanel(false);
					setBooRenderBenificaryStatusPanel(true);
					setBooRenderIndBenificaryStatusPanel(true);
					setBeneficaryTypeId(new BigDecimal(1));
					return;
				}
				if (getBeneficaryStatusName().equals("Non-Individual")) {
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiaryCreationNonIndidual.xhtml");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					return;
				}
			}
		}
		beneficaryMap.put(1, "Self");
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
		}
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
		 int benfiTypeId = getBeneficaryTypeId().intValue(); 
		int benfiTypeId = 0;
		if (getBeneficaryTypeId() != null)
			benfiTypeId = getBeneficaryTypeId().intValue();
		if (benfiTypeId == 2) {
			setOccupation(null);
			setReadOnlyOccupation(false);
			setMinagevalidation(false);
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
		} else if (benfiTypeId == 1) {
			customerDetailsList = getiPersonalRemittanceService().getCustomerDetails(getIdNumber().toString(), getSelectCard());
			if (customerDetailsList.size() > 0 && customerDetailsList != null) {
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
				log.info("#################before customer fetch#############################");
				getCustomerDetails();
				log.info("##################customer fetch completed#############################");
				checkRelationExistFromDb();
				log.info("##########################relation check completed####################");
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
			}
		} else if (benfiTypeId == 0) {
		}
	}

	private BeneficaryMaster beneficaryMasterSave() {
		BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionmanage.getCountryId());
		beneficaryMaster.setApplicationCountryId(countryMaster);
		CountryMaster countryMaster1 = new CountryMaster();
		countryMaster1.setCountryId(getBenifisCountryId());
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
		StateMaster stateMaster = new StateMaster();
		stateMaster.setStateId(getBenifisStateId());
		beneficaryMaster.setFsStateMaster(stateMaster);
		CityMaster cityMaster = new CityMaster();
		cityMaster.setCityId(getCityId());
		beneficaryMaster.setFsCityMaster(cityMaster);
		DistrictMaster districtMaster = new DistrictMaster();
		districtMaster.setDistrictId(getDistictId());
		beneficaryMaster.setFsDistrictMaster(districtMaster);
		beneficaryMaster.setOccupation(getOccupation());
		beneficaryMaster.setNoOfRemittance(getNoOfRemittance());
		beneficaryMaster.setIsActive(Constants.Yes);
		beneficaryMaster.setCreatedBy(sessionmanage.getUserName());
		beneficaryMaster.setCreatedDate(new Date());
		beneficaryMaster.setCityName(getCityName());
		beneficaryMaster.setDistrictName(getDistrictName());
		beneficaryMaster.setStateName(getStateName());
		System.out.println("$$$$$$$$$$$$$$$$$$" + benificaryStatusName.size());
		for (BeneficaryStatus beneficaryStatusvalues : benificaryStatusName) {
			if (getBeneficaryStatusId().compareTo(beneficaryStatusvalues.getBeneficaryStatusId()) == 0) {
				System.out.println("%%%%%%%%%%%%%%%" + Constants.Individual);
				beneficaryMaster.setBeneficaryStatusName(Constants.Individual);
				break;
			} else {
				System.out.println("%%%%%%%%%%%%%%%" + Constants.NonIndividual);
				beneficaryMaster.setBeneficaryStatusName(Constants.NonIndividual);
			}
		}
		getiPersonalRemittanceService().saveBeneficiaryMaster(beneficaryMaster);
		return beneficaryMaster;
	}

	// For Save Beneficary Master
	@SuppressWarnings("unchecked")
	public void saveBeneficaryMaster() throws ParseException {
		System.out.println("Beneficary Master ----------------------------- > ");
		Map<String, Object> mapTeleExist = getMapTeleExistToCheck();
		if (mapTeleExist.size() > 0) {
			BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
			Boolean telePhoneExist = (Boolean) mapTeleExist.get("TeleExist");
			if (telePhoneExist.booleanValue()) {
				System.out.println("TeleExist");
				List<BeneficaryContact> lstBeneficaryTelaphone = (List<BeneficaryContact>) mapTeleExist.get("LstTeleExist");
				BeneficaryContact beneficaryTelaphone = lstBeneficaryTelaphone.get(0);
				BigDecimal beneMatsterSeqId = beneficaryTelaphone.getBeneficaryMaster().getBeneficaryMasterSeqId();
				beneficaryMaster.setBeneficaryMasterSeqId(beneMatsterSeqId);
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
				BeneficaryAccount beneficaryAccount = new BeneficaryAccount();
				beneficaryAccount.setBeneficaryAccountSeqId(beneAccountSeqId);
				List<BeneficaryAccount> beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExistInDb(getBankAccountNumber(), getBenifisCountryId(), getBenifisBankId(), getServicebankBranchId());
				if (beneficiaryAccountDetailList.size() == 0) {
					BeneficaryAccount beneficaryAccountSave = saveBeneficaryAccount(beneficaryMaster);
					saveBeneficaryRelation(beneficaryMaster, beneficaryAccountSave);
				} else {
					beneficaryMaster.setBeneficaryMasterSeqId(beneficiaryAccountDetailList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId());
					BeneficaryAccount beneficaryAccountExist = beneficiaryAccountDetailList.get(0);
					saveBeneficaryRelation(beneficaryMaster, beneficaryAccountExist);
				}
				
				 * Boolean
				 * relaShipExist=(Boolean)mapTeleExist.get("RelationExist");
				 * if(relaShipExist.booleanValue()) { Boolean
				 * accNoExist=(Boolean)mapTeleExist.get("AccountExist");
				 * System.out.println("RelationExist");
				 * if(accNoExist.booleanValue()) { List<BeneficaryAccount>
				 * lstBeneficaryAccount
				 * =(List<BeneficaryAccount>)mapTeleExist.get
				 * ("LstAccountExist");
				 * 
				 * } }
				 
			} else {
				List<BeneficaryAccount> beneficiaryAccountDetailList = null;
				if (lstfetchCashId != null && !lstfetchCashId.isEmpty()) {
					if (getServiceGroupId() != null && !(getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0)) {
						beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExistInDb(getBankAccountNumber(), getBenifisCountryId(), getBenifisBankId(), getServicebankBranchId());
					}
				}
				if (beneficiaryAccountDetailList != null && beneficiaryAccountDetailList.size() == 0) {
					beneficaryMaster = beneficaryMasterSave();
					saveBeneficaryTelephone(beneficaryMaster);
					BeneficaryAccount beneficaryAccountSave = saveBeneficaryAccount(beneficaryMaster);
					saveBeneficaryRelation(beneficaryMaster, beneficaryAccountSave);
				} else if (beneficiaryAccountDetailList == null) {
					beneficaryMaster = beneficaryMasterSave();
					saveBeneficaryTelephone(beneficaryMaster);
					BeneficaryAccount beneficaryAccountSave = saveBeneficaryAccount(beneficaryMaster);
					saveBeneficaryRelation(beneficaryMaster, beneficaryAccountSave);
				} else {
					beneficaryMaster = beneficaryMasterSave();
					saveBeneficaryTelephone(beneficaryMaster);
					BeneficaryAccount beneficaryAccountExist = beneficiaryAccountDetailList.get(0);
					saveBeneficaryRelation(beneficaryMaster, beneficaryAccountExist);
				}
			}
		} else {
			BeneficaryMaster beneficaryMaster = beneficaryMasterSave();
			saveBeneficaryTelephone(beneficaryMaster);
			List<BeneficaryAccount> beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExistInDb(getBankAccountNumber(), getBenifisCountryId(), getBenifisBankId(), getServicebankBranchId());
			if (beneficiaryAccountDetailList.size() == 0) {
				BeneficaryAccount beneficaryAccountSave = saveBeneficaryAccount(beneficaryMaster);
				saveBeneficaryRelation(beneficaryMaster, beneficaryAccountSave);
			} else {
				BeneficaryAccount beneficaryAccountExist = beneficiaryAccountDetailList.get(0);
				saveBeneficaryRelation(beneficaryMaster, beneficaryAccountExist);
			}
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
		RequestContext.getCurrentInstance().execute("beneficarycomplete.show();");
		
		 * clear(); coustomerBeneficaryDTList.clear();
		 * goFromOldSmartCardpanel();
		 
	}

	// For Save Beneficary Relation
	public void saveBeneficaryRelation(BeneficaryMaster beneficaryMaster, BeneficaryAccount beneficaryAccount) {
		System.out.println("1 -------------------------------------------------------------- > ");
		
		 * List<BeneficaryRelationship> objBeneficaryRelationshipList =
		 * getiPersonalRemittanceService
		 * ().isBenificaryRelationExistInDB(beneficaryMaster
		 * .getBeneficaryMasterSeqId());
		 
		List<BeneficaryRelationship> objBeneficaryRelationshipList = getiPersonalRemittanceService().isBenificaryRelationExist(beneficaryMaster.getBeneficaryMasterSeqId(), beneficaryAccount.getBeneficaryAccountSeqId());
		System.out.println(objBeneficaryRelationshipList.size());
		if (objBeneficaryRelationshipList.size() > 0) {
			System.out.println("Condition in side ----------------------------------------------------- > ");
			BeneficaryRelationship beneficaryRelationship = new BeneficaryRelationship();
			beneficaryRelationship.setBeneficaryRelationshipId((objBeneficaryRelationshipList.get(0).getBeneficaryRelationshipId()));
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
			getiPersonalRemittanceService().saveBeneficiaryRelationship(beneficaryRelationship);
		} else {
			System.out.println("Beneficicary Relation else ---------------------------------- > ");
			BeneficaryRelationship beneficaryRelationship = new BeneficaryRelationship();
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
			getiPersonalRemittanceService().saveBeneficiaryRelationship(beneficaryRelationship);
		}
	}

	// For Save Beneficary Telephone
	public void saveBeneficaryTelephone(BeneficaryMaster beneficaryMaster) {
		List<BeneficaryAccount> beneficaryAccounts = getiPersonalRemittanceService().isBeneficaryAccountExistInDb(beneficaryMaster.getBeneficaryMasterSeqId());
		if (beneficaryAccounts.size() > 0) {
			System.out.println("Condition in side ----------------------------------------------------- >3 ");
			BeneficaryContact beneficaryTelaphone = new BeneficaryContact();
			beneficaryTelaphone.setApplicationCountryId(beneficaryAccounts.get(0).getBeneApplicationCountry());
			beneficaryTelaphone.setTelephoneNumber(getCountryTelephoneNumber());
			beneficaryTelaphone.setIsActive(Constants.Yes);
			beneficaryTelaphone.setCreatedBy(sessionmanage.getUserName());
			beneficaryTelaphone.setCreatedDate(new Date());
			beneficaryTelaphone.setBeneficaryMaster(beneficaryMaster);
			beneficaryTelaphone.setCountryTelCode(getCountryCode());
			beneficaryTelaphone.setMobileNumber(getMobileNumber());
			System.out.println("Condition in side -----------------------------------------------------  before save 4");
			getiPersonalRemittanceService().saveBeneficiaryTelephone(beneficaryTelaphone);
		} else {
			System.out.println("Beneficicary Telephone ---------------------------------- > ");
			BeneficaryContact beneficaryTelaphone = new BeneficaryContact();
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
			getiPersonalRemittanceService().saveBeneficiaryTelephone(beneficaryTelaphone);
		}
	}

	// For Save Beneficary Account Detail
	public BeneficaryAccount saveBeneficaryAccount(BeneficaryMaster beneficaryMaster) {
		BeneficaryAccount beneficaryAccount = new BeneficaryAccount();
		if (getServiceGroupId() != null && !(getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0)) {
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
			
			 * ServiceMaster serviceMaster = new ServiceMaster();
			 * serviceMaster.setServiceId(getServiceId());
			 * beneficaryAccount.setExServiceId(serviceMaster);
			 
			ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
			serviceGroupMaster.setServiceGroupId(getServiceGroupId());
			beneficaryAccount.setServicegropupId(serviceGroupMaster);
			beneficaryAccount.setBankAccountNumber(getBankAccountNumber());
			beneficaryAccount.setBankCode(getBankCode());
			beneficaryAccount.setBankBranchCode(bankBranch.getBranchCode());
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getBenifisCurrencyId());
			beneficaryAccount.setCurrencyId(currencyMaster);
			beneficaryAccount.setBankBranchCode(getBankBranchCode());
			beneficaryAccount.setBankCode(getBankCode());
			beneficaryAccount.setAliasFirstName(getAliasFirstName());
			beneficaryAccount.setAliasSecondName(getAliasSecondName());
			beneficaryAccount.setAliasThirdName(getAliasThirdName());
			beneficaryAccount.setAliasFourthName(getAliasFourthName());
			beneficaryAccount.setIsActive(Constants.Yes);
			beneficaryAccount.setCreatedBy(sessionmanage.getUserName());
			beneficaryAccount.setCreatedDate(new Date());
			
			 * if (getServiceTypeId() != null) { BankMaster
			 * bankMasterServiceProvider = new BankMaster();
			 * bankMasterServiceProvider.setBankId(getServiceTypeId());
			 * beneficaryAccount.setServiceProvider(bankMasterServiceProvider);
			 * }
			 
			beneficaryAccount.setBeneficaryMaster(beneficaryMaster);
			getiPersonalRemittanceService().saveBeneficiaryAccount(beneficaryAccount);
		} else {
			beneficaryAccount = new BeneficaryAccount();
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(getBenifisBankId());
			beneficaryAccount.setBank(bankMaster);
			BankBranch bankBranch = new BankBranch();
			bankBranch.setBankBranchId(getServicebankBranchId());
			beneficaryAccount.setBankBranch(bankBranch);
			ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
			serviceGroupMaster.setServiceGroupId(getServiceGroupId());
			beneficaryAccount.setServicegropupId(serviceGroupMaster);
			BankMaster bankMasterServiceProvider = new BankMaster();
			bankMasterServiceProvider.setBankId(getServiceTypeId());
			beneficaryAccount.setServiceProvider(bankMasterServiceProvider);
			beneficaryAccount.setBeneficaryAccountSeqId(getBeneficaryAccountSeqId());
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionmanage.getCountryId());
			beneficaryAccount.setBeneApplicationCountry(countryMaster);
			CountryMaster countryMaster1 = new CountryMaster();
			countryMaster1.setCountryId(getBenifisCountryId());
			beneficaryAccount.setBeneficaryCountry(countryMaster1);
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(getBenifisCurrencyId());
			beneficaryAccount.setCurrencyId(currencyMaster);
			beneficaryAccount.setCreatedBy(sessionmanage.getUserName());
			beneficaryAccount.setCreatedDate(new Date());
			beneficaryAccount.setIsActive(Constants.Yes);
			beneficaryAccount.setBeneficaryMaster(beneficaryMaster);
			getiPersonalRemittanceService().saveBeneficiaryAccount(beneficaryAccount);
		}
		return beneficaryAccount;
	}

	public void saveBeneficaryCreation() throws ParseException {
		saveBeneficaryMaster();
	}

	public void backFromRemmitanceServicePanel() {
		setBooRenderTypeOfServicePanel(true);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderRemittanceServicePanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryFirstPanel(false);
	}

	public void backTobeneficiaryDetails() {
		
		try {
			log.info("Page redirect to bankacccountdetails page");
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
			log.info("Page redirect to bankacccountdetails page");
			setBeneficaryStatusId(null);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	public void gotoBeneficaryAccountDetails() {
		if ((getDateOfBrith() == null && getYearOfBrith() == null && getAge() == null)) {
			setYearOfBrith(null);
			setAge(null);
			RequestContext.getCurrentInstance().execute("checking.show();");
		} else if (getTelephoneNumber() == null && getMobileNumber() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Telephone or Mobile");
		} else {
			try {
				log.info("Page redirect to bankacccountdetails page");
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

	
	 * public void editRecord(PersonalRemmitanceBeneficaryDataTable
	 * personalBenfObj) {
	 * 
	 * }
	 
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
		 List<BankApplicability> lst = new ArrayList<BankApplicability>(); 
		serviceProviderInd = generalService.getBankListbyIndicatoronly(pkServProBankInd, sessionmanage.getCompanyId());
		if (serviceProviderInd.size() != 0) {
			setServiceproviderlst(serviceProviderInd);
		} else {
			
			 * RequestContext.getCurrentInstance().execute(
			 * "noserviceprovider.show();");
			 
		}
		return serviceProviderInd;
	}

	// method called from menubar - navigation
	public void personalRemittanceMainScreen() {
		try {
			firstTime = null;
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
		firstName = null;
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
		setServicebankBranchId(null);
		setBankAccountNumber(null);
		setAliasFirstName(null);
		setAliasSecondName(null);
		setAliasThirdName(null);
		setAliasFourthName(null);
		// setOccupation(null);
		setBenifisStateId(null);
		setDistictId(null);
		setCityId(null);
		setProviance(null);
		setServiceTypeId(null);
		lstBank.clear();
		lstBankbranch.clear();
		lststate.clear();
		lstDistict.clear();
		lstCity.clear();
		serviceGroupMasterDescList.clear();
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
				Map<String, Object> mapTeleExist = getiPersonalRemittanceService().checkTelephoneExist(getTelephoneNumber(), getCountryCode(), "telephone");
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
								RequestContext.getCurrentInstance().execute("PF('teleRelaAccountExist').show();");
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
			 String ccc = getCountryCode().trim(); 
			// BigDecimal tleCountry=new BigDecimal(ccc);
			log.info("getCountryCode " + getCountryCode());
			Map<String, Object> mapTeleExist = getiPersonalRemittanceService().checkTelephoneExistWithCustIdwithPhone(getTelephoneNumber(), getCustomerNo(), getCountryCode(), "telephone");
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
							RequestContext.getCurrentInstance().execute("PF('teleRelaAccountExist').show();");
							System.out.println("AccountExist");
						}
					}
				}
			}
		}
	}

	public void mobileNumberExistInDB() {
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
								RequestContext.getCurrentInstance().execute("PF('teleRelaAccountExist').show();");
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
			
			 * RequestContext context = RequestContext.getCurrentInstance();
			 * context.execute("relationExistCheck.show();");
			 
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
		BigDecimal telephoneNumber = getTelephoneNumber();
		List<BeneficaryContact> beneficiaryList = getiPersonalRemittanceService().isCoustomerTelephoneExistInDB(telephoneNumber);
		List<BeneficaryAccount> objAccountList = getiPersonalRemittanceService().getBeneficaryAccountDetails(beneficiaryList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId());
		List<BeneficaryMaster> objMasterList = getiPersonalRemittanceService().getBeneficaryMasterDetails(beneficiaryList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId());
		setServiceGroupId(objAccountList.get(0).getServicegropupId().getServiceGroupId());
		setBankAccountNumber(objAccountList.get(0).getBankAccountNumber());
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
		popStatelist();
		setBenifisStateId(objMasterList.get(0).getFsStateMaster().getStateId());
		popDistict();
		setDistictId(objMasterList.get(0).getFsDistrictMaster().getDistrictId());
		popCitylist();
		setCityId(objMasterList.get(0).getFsCityMaster().getCityId());
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
		int accountLength = getBankAccountNumber().toPlainString().length();
		int i = 0;
		String lengthValues = "";
		List<BankAccountLength> lstofAccLength = getiPersonalRemittanceService().getBankAccountLengthByBank(getBenifisBankId());
		if (lstofAccLength.size() != 0) {
			for (BankAccountLength bankAccountLength : lstofAccLength) {
				if (bankAccountLength.getAcLength().compareTo(new BigDecimal(accountLength)) == 0) {
					i = 1;
					break;
				} else {
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
				setCheckAccountNoExist("NO");
				setAccountNoExist(false);
				
				 * List<BeneficaryAccount> beneficiaryAccountDetailList =
				 * getiPersonalRemittanceService
				 * ().isBankAccountNumberExistInDb(getBankAccountNumber(),
				 * getBenifisCountryId(), getBenifisBankId(),
				 * getServicebankBranchId());
				 
				List<BeneficaryAccount> beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExist(getBankAccountNumber(), getBenifisBankId(), getBenifisCurrencyId(), getBenifisCountryId(), getServicebankBranchId());
				if (beneficiaryAccountDetailList.size() > 0 && getBeneficaryTypeId().equals(new BigDecimal(1))) {
					RequestContext.getCurrentInstance().execute("accountcheck.show();");
					setErrmsg("Data already exist for this combination");
					return;
					
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
					 
				} else {
					setCheckAccountNoExist("NO");
					setAccountNoExist(false);
				}
			}
		} else {
			setBankAccountNumber(null);
			RequestContext.getCurrentInstance().execute("acclengtherr.show();");
		}
	}

	public void clearAccountField() {
		setBankAccountNumber(null);
		if (getTempAccountNumber() != null) {
			setBankAccountNumber(getTempAccountNumber());
		}
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
		
		 * saveBeneficaryRelation(beneficiaryAccountDetailList.get(0)
		 * .getBeneficaryMaster());
		 
		setBooRenderBenificarySearchPanel(true);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(false);
	}

	// By subramanian for telephone validation Ends
	// added by Nazish
	
	 * private List<ServiceMasterDesc> serviceMasterDescList = new
	 * ArrayList<ServiceMasterDesc>();
	 
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
		lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
		if (lstfetchCashId.size() != 0) {
			
			 * if(getServiceGroupId() != null &&
			 * (getServiceGroupId().compareTo(lstfetchCashId
			 * .get(0).getServiceMaster().getServiceId())==0)) {
			 
			if (getServiceGroupId() != null && (getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0)) {
				setDisableDependService(false);
				setBooenableAgentPanel(false);
				popAgentlist();
			} else {
				setServiceTypeId(null);
				setDisableDependService(true);
				setBooenableAgentPanel(true);
				popBankingChennalbanklist();
			}
			clearBeneandAgentDetails();
		}
	}

	public void popBankingChennalbanklist() {
		lstBank.clear();
		List<BankApplicability> FbankList = bankApplicabilityService.getApplicabilityBankingChennalBankList(sessionmanage.getCountryId(), Constants.BANK_INDICATOR_AGENT_BANK);
		for (BankApplicability bankApplicability : FbankList) {
			lstBank.add(bankApplicability.getBankMaster());
		}
		benServiceCurrencyList();
		popStatelist();
	}

	public void popAgentlist() {
		lstBank.clear();
		List<BankApplicability> FbankList = bankApplicabilityService.getApplicabilityBankList(sessionmanage.getCountryId(), Constants.BANK_INDICATOR_AGENT_BANK);
		for (BankApplicability bankApplicability : FbankList) {
			lstBank.add(bankApplicability.getBankMaster());
		}
		benServiceCurrencyList();
		popStatelist();
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
		listCurrencyAccountDetails = ifundservice.getCurrencyOfBank(getBenifisBankId());
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

	
	 * @PostConstruct public void createforeignCurrencySaleBean() {
	 * foreignCurrencySaleBean = new ForeignCurrencySaleBean(); }
	 
	public List<CountryMasterDesc> getNationalityList() {
		return nationalityList;// generalService.getNationalityList(sessionmanage.getLanguageId());
	}

	public void setNationalityList(List<CountryMasterDesc> nationalityList) {
		this.nationalityList = nationalityList;
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
		log.info("getYearOfBrith" + getYearOfBrith());
		log.info("getDateOfBrith" + getDateOfBrith());
		log.info("getAge" + getAge());
		if (getAge() == null && getDateOfBrith() == null) {
			if (getYearOfBrith() != null) {
				if (!isInteger(getYearOfBrith().toPlainString())) {
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg("Please enter numbers only");
					setYearOfBrith(null);
					return;
				}
				if (getDateOfBrith() != null && getYearOfBrith().toPlainString().length() != 0) {
					setYearOfBrith(null);
					RequestContext.getCurrentInstance().execute("dateOfBirth.show();");
					return;
				} else if (getYearOfBrith() != null && getYearOfBrith().toPlainString().length() != 0) {
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
						
						 * setErrmsg("Customer's age is calculated as " +
						 * (Calendar.getInstance().get(Calendar.YEAR) + " - " +
						 * getYearOfBrith().intValue()) + " = " + getyr +
						 * ".Please verify");
						 
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
				} else if (getAge() != null && getYearOfBrith().toPlainString().length() != 0) {
					setYearOfBrith(null);
					RequestContext.getCurrentInstance().execute("age.show();");
					return;
				}
			}
		} else {
			if (getAge() != null) {
				setYearOfBrith(null);
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Already age entered");
			}
			if (getDateOfBrith() != null) {
				setYearOfBrith(null);
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("Already Date of Birth entered");
			}
		}
	}

	public void ageSelect() {
		if (getAge() != null) {
			if (getDateOfBrith() != null && getAge().toPlainString().length() != 0) {
				setAge(null);
				RequestContext.getCurrentInstance().execute("dateOfBirth.show();");
				return;
			} else if (getYearOfBrith() != null && getAge().toPlainString().length() != 0) {
				setAge(null);
				RequestContext.getCurrentInstance().execute("yearOFBirth.show();");
				return;
			}
		}
	}

	public void dateOfBithSelect() {
		if (getAge() != null && getDateOfBrith() != null) {
			setDateOfBrith(null);
			RequestContext.getCurrentInstance().execute("age.show();");
			return;
		} else if (getYearOfBrith() != null && getDateOfBrith() != null) {
			setDateOfBrith(null);
			RequestContext.getCurrentInstance().execute("yearOFBirth.show();");
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

	public void ageValidation(SelectEvent e) {
		dateOfBithSelect();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -(Integer.parseInt(Constants.AGE_LIMIT)));
		Date today18 = cal.getTime();
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
			context.redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
		try {
			goFromOldSmartCardpanel();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void clearEditFields() {
		setBeneMatsterSeqId(null);
		setBeniStatusName(null);
		 setBenificaryStatusName(null); 
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
		setBenifisCurrencyId(null);
		setServicebankBranchId(null);
		setBankAccountNumber(null);
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
	}

	public void clearBeneandAgentDetails() {
		setBenifisBankId(null);
		setServicebankBranchId(null);
		setBenifisCurrencyId(null);
		setBankAccountNumber(null);
		setAliasFirstName(null);
		setAliasSecondName(null);
		setAliasThirdName(null);
		setAliasFourthName(null);
		setBenifisStateId(null);
		setDistictId(null);
		setCityId(null);
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
	private BigDecimal idNumber;
	private List<BeneficaryStatus> benificaryStatusList = new ArrayList<BeneficaryStatus>();
	private List<BeneficaryStatus> benificaryStatusName = new ArrayList<BeneficaryStatus>();
	@Autowired
	IGeneralService<T> generalService;

	public void populateNonBenificaryDetails() {
		if (firstTime == null) {
			try {
				clear();
				setRenderBackButton(true);
				setBooRenderIndBenificaryStatusPanel(false);
				setBeneficaryStatusId(new BigDecimal(2));
				setDisableBeneficaryType(true);
				setIdNumber(new BigDecimal(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idNumber").toString()));
				setSelectCard(new BigDecimal(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectCard").toString()));
				log.info("Id Number from personal remittence is " + getIdNumber());
				log.info("select card value from personal remittence is " + getSelectCard());
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idNumber");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectCard");
				log.info("idNumber and selectCard successfully removed from session object");
				nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
				log.info("nationality added");
				populateBenificaryAccountDetails();
				log.info("country  added");
			} catch (Exception e) {
				log.info("Exception occured while gettting id number from personal remittence screen");
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
			beneficiaryStatusList();
			firstTime = true;
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
					benificaryStatusList.addAll(beneStatus);
					benificaryStatusName.addAll(beneStatus);
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

	public void populateBenificaryAccountDetails() {
		lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
		serviceGroupMasterDescList = iPersonalRemittanceService.getAllServiceGroupDesc(sessionmanage.getLanguageId());
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
				setAliasFirstName(beneficaryAccount.getAliasFirstName());
				setAliasSecondName(beneficaryAccount.getAliasSecondName());
				setAliasThirdName(beneficaryAccount.getAliasThirdName());
				setAliasFourthName(beneficaryAccount.getAliasFourthName());
				setOccupation(beneficaryAccount.getBeneficaryMaster().getOccupation());
				setBenifisStateId(beneficaryAccount.getBeneficaryMaster().getFsStateMaster().getStateId());
				popDistictTelePhone(beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getBeneficaryMaster().getFsStateMaster().getStateId());
				popCitylistTelePhone(beneficaryAccount.getBeneficaryCountry().getCountryId(), beneficaryAccount.getBeneficaryMaster().getFsStateMaster().getStateId(), beneficaryAccount.getBeneficaryMaster().getFsDistrictMaster().getDistrictId());
				setDistictId(beneficaryAccount.getBeneficaryMaster().getFsDistrictMaster().getDistrictId());
				setCityId(beneficaryAccount.getBeneficaryMaster().getFsCityMaster().getCityId());
				log.info("Telephone numnber already exist. forwared to benificaryaccountdetails page");
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
		for (BankMaster bankMaster : lstBank) {
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
*/}