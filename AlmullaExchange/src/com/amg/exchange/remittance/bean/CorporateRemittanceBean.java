package com.amg.exchange.remittance.bean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.internet.AddressException;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import KNET_WinEPTS_API.Receipt;
import KNET_WinEPTS_API.WinEPTS_Wrapper;

import com.amg.exchange.aop.FcSaleReport;
import com.amg.exchange.bean.RoutingCountry;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.bean.ForeignLocalCurrencyDataTable;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustApp;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.TempCollectDetail;
import com.amg.exchange.foreigncurrency.model.TempCollection;
import com.amg.exchange.foreigncurrency.model.TempForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmployeeInfoView;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICorporateRegService;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AdditionalDataDisplayView;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.AmlLimit;
import com.amg.exchange.remittance.model.AuthicationLimitCheckView;
import com.amg.exchange.remittance.model.AuthorizedLog;
import com.amg.exchange.remittance.model.BankDebitCardLengthViewModel;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BeneficaryStatus;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.CustomerDeclerationView;
import com.amg.exchange.remittance.model.CustomerSpecialDealAppl;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.KnetLog;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.RemittanceApplicationPurpose;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.remittance.model.ShoppingCartDetails;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;
import com.amg.exchange.remittance.service.ICorporateRemittanceService;
import com.amg.exchange.remittance.service.ICustomerAlmTransationCheckService;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IRelationsTypeService;
import com.amg.exchange.remittance.service.IRoutingSetUpDetailsService;
import com.amg.exchange.remittance.service.ISpecialRateRequestService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceGroupMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.treasury.service.IBankApplicabilityService;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.treasury.service.IBeneCountryServices;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.treasury.service.IRatesUpdateService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.ISupplierService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

@Component("corporateRemittanceBean")
@Scope("session")
@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class CorporateRemittanceBean<T> implements Serializable {
	Logger log = Logger.getLogger(CorporateRemittanceBean.class);
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
	private String bankAccountNumber;
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
	private String sourceOfFund;
	private String intermediaryBank;
	private BigDecimal netAmountSent;
	private String furthuerInstructions;
	private BigDecimal netAmountPayable;
	private BigDecimal loyaltyAmountAvailed;
	private BigDecimal grossAmountCalculated;
	private BigDecimal commission;
	private String additionBankDetails;
	private String chargesOverseas;
	private String agent;
	private BigDecimal exchangeRate;
	private String cashRounding;
	private String availLoyaltyPoints;
	private String specialRateRef;
	private BigDecimal amountToRemit;
	private BigDecimal currency;
	private BigDecimal modeOfPayment;
	private BigDecimal routingBranch;
	private BigDecimal deliveryMode;
	private BigDecimal remitMode;
	private BigDecimal routingBank;
	private BigDecimal routingCountry;
	private BigDecimal Overseasamt;
	private BigDecimal foriegnCurrency;
	private BigDecimal beneficarymasterId;
	// rahamath
	private Boolean booRenderBenificarySearchPanel = false;
	private Boolean booRenderBenificaryStatusPanel = false;
	private Boolean booRenderTypeOfServicePanel = false;
	private Boolean booRenderRemittanceServicePanel = false;
	private Boolean booRenderOldSmartCardPanel = false;
	private Boolean booRenderBenificaryFirstPanel = false;
	private Boolean booRenderIndBenificaryStatusPanel = false;
	private Boolean booRenderNonIndBenificaryStatusPanel = false;
	private Boolean booRenderLayaltyServicePanel = false;
	private Boolean booRenderRoutingCountryBankBranch = false;
	private Boolean booRenderCustomerSignaturePanel = false;
	private Boolean booRenderRouting = false;
	private Boolean booRenderAgent = false;
	private Boolean booRenderOverseaCharges = false;
	private int selectCardType = 70;
	private String idNumber ;
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
	private String dataAccountnum;
	private String benificarystatus;
	private String databenificarybankname;
	private String databenificarybranchname;
	private String databenificaryname;
	private BigDecimal paymentModeId;
	private String BranchApplicabilty;
	private String spotRate = Constants.No;
	private Boolean checkingSplPool;
	private boolean booSingleRoutingCountry = true;
	private boolean booMultipleRoutingCountry = false;
	private String routingCountryName;
	private boolean booSingleRoutingBank = true;
	private boolean booMultipleRoutingBank = false;
	private String routingBankName;
	private boolean booSingleRoutingBranch = true;
	private boolean booMultipleRoutingBranch = false;
	private String routingBranchName;
	private boolean booSingleRemit = true;
	private boolean booMultipleRemit = false;
	private String remittanceName;
	private String routingBankBranchName;
	private BigDecimal serviceGroupIdforCash;
	private String exceptionMessage;
	private boolean booRenderPoll = false;
	private boolean amlboomsg = false;
	private String rangeFromOne;
	private String rangeToOne;
	private String rangeFromTwo;
	private String rangeToTwo;
	private String rangeFromThree;
	private String rangeToThree;
	private String rangeFromFour;
	private String rangeToFour;
	private String amlRangeOneDescription;
	private String amlRangeTwoDescription;
	private String amlRangeThreeDescription;
	private String amlRangeFourDescription;
	private BigDecimal calNetAmountPaid;
	private BigDecimal calGrossAmount;
	private BigDecimal calNetAmountSend;
	private BigDecimal idProof;
	private BigDecimal purposeOfRemittance;
	private BigDecimal sourceOfIncome;
	private String messageToUser;
	private Boolean booShowCashRoundingPanel = false;
	private Boolean disableSpotRatePanel = false;
	private Boolean booRenderDeliveryModeInputPanel = true;
	private Boolean booRenderDeliveryModeDDPanel = false;
	private String deliveryModeInput;
	private BigDecimal populatedDebitCardNumber;
	private BigDecimal cashAmount = new BigDecimal(0);
	
	private String documentserialityno = null;
	private int minLenght;
	private BigDecimal additionalbankFieldId;
	private BigDecimal refundAmount;
	private BigDecimal collectedRefundAmount;
	private Boolean boorefundPaymentDetails = false;
	private Boolean booSpecialCusFCCalDataTable = false;
	private String customerName;
	private String customerCrNumber;
	private List<Document> lstDocument = new ArrayList<Document>();
	
	private BigDecimal spldealreqid;
	private BigDecimal splcompanyid;
	private BigDecimal spldocumentid;
	private BigDecimal splfinyear;
	private BigDecimal spldocnum;
	// private String exchangeType;
	private BigDecimal loyalitypoints;
	private boolean boorenderlastpanel;
	private boolean selectrecord = false;
	// for AML Check extra Columns
	private String amlMessageOne;
	private String amlMessageTwo;
	private String amlMessageThree;
	private String amlAuthorizedBy;
	private String amlRemarks;
	BigDecimal tempCalNetAmountPaid = new BigDecimal(0);
	BigDecimal tempCalGrossAmount = new BigDecimal(0);
	private BigDecimal remittanceNo = new BigDecimal(0);
	private BigDecimal fcsaleNo = new BigDecimal(0);
	private BigDecimal toalUsedAmount = new BigDecimal(0);
	private BigDecimal totalUamount = new BigDecimal(0);
	private BigDecimal subtractedAmount = new BigDecimal(0);
	private Boolean boRenderTotalAmountCalPanel = false;
	private CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> personalRemittCalFCAmountDTList = new CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable>();
	// private Map<BigDecimal, String> banklistMap = new HashMap<BigDecimal,
	// String>();
	SessionStateManage sessionStateManage = new SessionStateManage();
	private Boolean marqueeRender = false;
	private Boolean nextRender = false;
	private boolean amlboo = true;
	private double slb1total1 = 0.0;
	private double slb1total2 = 0.0;
	private double slb1total3 = 0.0;
	private double slb1total4 = 0.0;
	// new panel is been adding collection -- panel 6
	private Boolean booRenderCollection = false;
	private Boolean booRenderColDebitCard = false;
	private Boolean booRendercollectiondatatable = false;
	// List<PersonalRemittanceCollectionDataTable> coldatatablevalues = new
	// ArrayList<PersonalRemittanceCollectionDataTable>();
	private BigDecimal colremittanceNo;
	private BigDecimal colfcsaleNo;
	private BigDecimal colpaymentmodeId;
	private String colpaymentmodeName;
	private BigDecimal colCash;
	private BigDecimal colBankid;
	private String colCurrency;
	private BigDecimal colCardNo;
	private BigDecimal colCardNoLength;
	private String colNameofCard;
	private String colAuthorizedby;
	private String colpassword;
	private String cyberPassword;
	private String colApprovalNo;
	private Boolean booRenderPaymentDetails = false;
	private Boolean booRendercashdenomination = false;
	private BigDecimal colamountKWD = new BigDecimal(0);
	private boolean booRenderSingleDebit = true;
	private boolean booRenderMulDebit = false;
	private int i = 0;
	private BigDecimal tempCash;
	private BigDecimal denomtotalCashreceived;
	private BigDecimal knetValue;
	private BigDecimal denomtotalCash;
	private String paymentDetailsremark;
	private BigDecimal payPaidAmount;
	private BigDecimal payNetPaidAmount;
	private BigDecimal payRefund;
	private String errmsg;
	private String documentSerialId;
	//private String documentNo;
	private Boolean renderBackButton = false;
	private Boolean paymentDeatailsPanel = false;
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
	// rounding values
	private BigDecimal applicationDocNum;
	private BigDecimal shoppingcartExchangeRate;
	private BigDecimal dummiTotalNetAmount;
	private BigDecimal dummiTotalGrossAmount;
	private Boolean booRenderMultiDocNum = false;
	private Boolean booRenderSingleDocNum = true;
	private Boolean booRenderModifiedRoundData = false;
	private Boolean booRenderModifiedApplTrnxReportByRound = false;
	
	private String customerLocalFullName;
	

	private BigDecimal approvalYear;
	private BigDecimal approvalNo;
	

	//added by nazish for dynamic fetch I and C,

	private BigDecimal customerTypeId;
	private String customerType;

	@Autowired
	ICorporateRemittanceService corporateRemittanceService;
	@Autowired
	IBankApplicabilityService<T> bankApplicabilityService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;
	@Autowired
	ISupplierService<T> iSupplierService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IRatesUpdateService<T> ratesUpdateService;
	@Autowired
	IRoutingSetUpDetailsService<T> iroutingSetUpDetailsService;
	@Autowired
	IBankIndicatorService bankIndicatorService;
	@Autowired
	IPaymentService ipaymentService;
	@Autowired
	IRelationsTypeService irelation;
	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;
	@Autowired
	IGLTransactionForADocument iglTransactionForADocument;
	@Autowired
	ServiceCodeMasterService serviceMasterService;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchase;
	@Autowired
	IEmployeeService iEmployeeService;
	@Autowired
	ApllicationMailer1 mailService1;
	@Autowired(required = true)
	JavaMailSender mailSender1;
	@Autowired
	ISpecialRateRequestService iSpecialRateRequest;
	@Autowired
	ICustomerAlmTransationCheckService<T> icustomerAlmTransationCheckService;
	@Autowired
	IBeneCountryServices<T> ibeneCountryServices;
	@Autowired
	IFundEstimationService<T> ifundservice;
	@Autowired
	ICustomerBankService icustomerBankService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	@Autowired
	ILoginService<T> iloginService;
	@Autowired
	IRemittanceModeService iRemitModeMaster;
	@Autowired
	DeliveryModeService iDeliveryModeMaster;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;

	@Autowired
	IBeneficaryCreation beneficaryCreation;
	
	@Autowired
	ISourceOfIncomeService isourceOfIncome;
	
	@Autowired
	ICompanyMasterservice iCompanyMasterservice;

	
	

	public ICompanyMasterservice getiCompanyMasterservice() {
		return iCompanyMasterservice;
	}

	public void setiCompanyMasterservice(ICompanyMasterservice iCompanyMasterservice) {
		this.iCompanyMasterservice = iCompanyMasterservice;
	}

	public ISourceOfIncomeService getIsourceOfIncome() {
		return isourceOfIncome;
	}

	public void setIsourceOfIncome(ISourceOfIncomeService isourceOfIncome) {
		this.isourceOfIncome = isourceOfIncome;
	}

	public String getCustomerLocalFullName() {
		return customerLocalFullName;
	}

	public void setCustomerLocalFullName(String customerLocalFullName) {
		this.customerLocalFullName = customerLocalFullName;
	}

	public BigDecimal getTempAccountNumber() {
		return tempAccountNumber;
	}

	public void setTempAccountNumber(BigDecimal tempAccountNumber) {
		this.tempAccountNumber = tempAccountNumber;
	}

	private Map<BigDecimal, String> mapcomIdentityType = new HashMap<BigDecimal, String>();
	iCypherSecurity cypherSecurity = new CypherSecurityImpl();
	private List<CurrencyMaster> currencyMasterList = new ArrayList<CurrencyMaster>();
	private List<BankAccountDetails> listCurrencyAccountDetails = new ArrayList<BankAccountDetails>();
	private List<PersonalRemmitanceBeneficaryDataTable> coustomerBeneficaryDTList = new ArrayList<PersonalRemmitanceBeneficaryDataTable>();
	private List<CustomerIdProof> customerDetailsList = new ArrayList<CustomerIdProof>();
	private List<BeneficaryStatus> benificaryStatusList = new ArrayList<BeneficaryStatus>();
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
	private List<BeneficaryStatus> benificaryStatusName = new ArrayList<BeneficaryStatus>();
	// CR for getting All Beneficary Account Details Based on masterid
	private List<BeneficaryAccount> beneficaryAccountList = new ArrayList<BeneficaryAccount>();
	private List<BeneficaryContact> beneficiaryTel = new ArrayList<BeneficaryContact>();
	private List<BeneficaryMaster> beneficiaryMaster = new ArrayList<BeneficaryMaster>();
	private List<RoutingCountry> routingCountryvalues = new ArrayList<RoutingCountry>();
	private List<RoutingCountry> routingbankvalues = new ArrayList<RoutingCountry>();
	private List<RoutingHeader> routingbranchvalues = new ArrayList<RoutingHeader>();
	private List<RemittanceModeDescription> servicedatafromdb = new ArrayList<RemittanceModeDescription>();
	private List<DeliveryModeDesc> deliverydataBasedonService = new ArrayList<DeliveryModeDesc>();
	private List<PaymentModeDesc> paymentModes = new ArrayList<PaymentModeDesc>();
	private List<BankBranch> lstforAll = new ArrayList<BankBranch>();
	private List<RoutingDetails> lstforSpecific = new ArrayList<RoutingDetails>();
	private List<PersonalRemittanceRoutingBankBranches> lstofRoutingBranches = new ArrayList<PersonalRemittanceRoutingBankBranches>();
	private List<PopulateData> lstcur = new ArrayList<PopulateData>();
	private List<PopulateData> lstofRemittance = new ArrayList<PopulateData>();
	private List<PopulateData> lstofDelivery = new ArrayList<PopulateData>();
	private List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();
	private List<RelationsDescription> relationDescList = new ArrayList<RelationsDescription>();
	private List<BankAccountDetails> currencylistForService = new ArrayList<BankAccountDetails>();
	private List<UserFinancialYear> financialYearList = new ArrayList<>();
	private List<CustomerAlmTrasactionCheckProcedure> almcheckList = new ArrayList<CustomerAlmTrasactionCheckProcedure>();
	private List<ServiceGroupMasterDesc> serviceGroupMasterDescList = new ArrayList<ServiceGroupMasterDesc>();
	private List<RemittanceModeDescription> listRemittanceDesc = new ArrayList<RemittanceModeDescription>();
	private List<DeliveryModeDesc> listdeliveryDesc = new ArrayList<DeliveryModeDesc>();
	private List<BankAccountDetails> currencylist = new ArrayList<BankAccountDetails>();
	private List<BankAccountDetails> currencylist2 = new ArrayList<BankAccountDetails>();
	private BigDecimal countSplCust = BigDecimal.ZERO;
	private BigDecimal remitAmountSplCust = BigDecimal.ZERO;
	private CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> selectedSplDeal = new CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable>();
	private List<CountryMasterDesc> nationalityList = new ArrayList<CountryMasterDesc>();
	private CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();
	//private List<BankApplicability> bankMasterList = new CopyOnWriteArrayList<BankApplicability>();
	//private List<CustomerBank> lstDebitCard = new ArrayList<CustomerBank>();
	private List<RemittanceApplication> lstRemitApp = new ArrayList<RemittanceApplication>();
	private List<BankApplicability> lstoflocalbank = new ArrayList<BankApplicability>();
	//private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();
	//private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private List<ShoppingCartDataTableBean> shoppingcartDTList = new ArrayList<ShoppingCartDataTableBean>();
	//private List<Employee> empllist = new ArrayList<Employee>();
	private List<ViewBeneServiceCurrency> beneServiceCurrencyList = new ArrayList<ViewBeneServiceCurrency>();
	//private List<PaymentModeDesc> lstFetchAllPayMode = new ArrayList<PaymentModeDesc>();
	private List<PersonalRemittanceCollectionDataTable> cashdenominationlst = new ArrayList<PersonalRemittanceCollectionDataTable>();
	

	
	// For Report generation
	private Boolean receiptReportPanel = false;
	// private Boolean applicationReportPanel = false;
	private Boolean mainPanelRender = false;
	private List<RemittanceReportBean> remittanceApplicationReportList = new CopyOnWriteArrayList<RemittanceReportBean>();
	private List<RemittanceReportBean> remittanceReceiptReportList = new CopyOnWriteArrayList<RemittanceReportBean>();
	// Jasper Report generation
	private JasperPrint jasperPrint;
	private List<PersonalRemittanceTeleExistDTBean> lstBeneficaryAccountDailog = new ArrayList<PersonalRemittanceTeleExistDTBean>();
	private List<BeneficaryAccount> lstBeneficaryAccount = new ArrayList<BeneficaryAccount>();
	private List<BeneficaryAccount> lstUpdateBeneficaryAccount = new ArrayList<BeneficaryAccount>();
	private Map<String, Object> mapTeleExistToCheck = new HashMap<String, Object>();
	private List<BigDecimal> lstApplDocNumber = new ArrayList<BigDecimal>();
	/*private List<SourceOfIncome> lstSourceofIncome = new ArrayList<SourceOfIncome>();*/

	private List<SourceOfIncomeDescription> lstSourceofIncome = new  ArrayList<SourceOfIncomeDescription>(); ;

	public Map<BigDecimal, String> getMapcomIdentityType() {
		return mapcomIdentityType;
	}

	public void setMapcomIdentityType(Map<BigDecimal, String> mapcomIdentityType) {
		this.mapcomIdentityType = mapcomIdentityType;
	}



	/*public List<SourceOfIncome> getLstSourceofIncome() {
		return lstSourceofIncome;
	}

	public void setLstSourceofIncome(List<SourceOfIncome> lstSourceofIncome) {
		this.lstSourceofIncome = lstSourceofIncome;
	}*/

	public List<SourceOfIncomeDescription> getLstSourceofIncome() {
		return lstSourceofIncome;
	}

	public void setLstSourceofIncome(List<SourceOfIncomeDescription> lstSourceofIncome) {
		this.lstSourceofIncome = lstSourceofIncome;
	}

	public Boolean getBooRenderModifiedApplTrnxReportByRound() {
		return booRenderModifiedApplTrnxReportByRound;
	}

	public void setBooRenderModifiedApplTrnxReportByRound(Boolean booRenderModifiedApplTrnxReportByRound) {
		this.booRenderModifiedApplTrnxReportByRound = booRenderModifiedApplTrnxReportByRound;
	}

	public Boolean getBooRenderRouting() {
		return booRenderRouting;
	}

	public void setBooRenderRouting(Boolean booRenderRouting) {
		this.booRenderRouting = booRenderRouting;
	}

	public Boolean getBooRenderAgent() {
		return booRenderAgent;
	}

	public void setBooRenderAgent(Boolean booRenderAgent) {
		this.booRenderAgent = booRenderAgent;
	}

	public Boolean getBooRenderModifiedRoundData() {
		return booRenderModifiedRoundData;
	}

	public void setBooRenderModifiedRoundData(Boolean booRenderModifiedRoundData) {
		this.booRenderModifiedRoundData = booRenderModifiedRoundData;
	}

	public Boolean getBooRenderMultiDocNum() {
		return booRenderMultiDocNum;
	}

	public void setBooRenderMultiDocNum(Boolean booRenderMultiDocNum) {
		this.booRenderMultiDocNum = booRenderMultiDocNum;
	}

	public Boolean getBooRenderSingleDocNum() {
		return booRenderSingleDocNum;
	}

	public void setBooRenderSingleDocNum(Boolean booRenderSingleDocNum) {
		this.booRenderSingleDocNum = booRenderSingleDocNum;
	}

	public BigDecimal getDummiTotalNetAmount() {
		return dummiTotalNetAmount;
	}

	public void setDummiTotalNetAmount(BigDecimal dummiTotalNetAmount) {
		this.dummiTotalNetAmount = dummiTotalNetAmount;
	}

	public BigDecimal getDummiTotalGrossAmount() {
		return dummiTotalGrossAmount;
	}

	public void setDummiTotalGrossAmount(BigDecimal dummiTotalGrossAmount) {
		this.dummiTotalGrossAmount = dummiTotalGrossAmount;
	}

	public BigDecimal getShoppingcartExchangeRate() {
		return shoppingcartExchangeRate;
	}

	public void setShoppingcartExchangeRate(BigDecimal shoppingcartExchangeRate) {
		this.shoppingcartExchangeRate = shoppingcartExchangeRate;
	}

	public BigDecimal getApplicationDocNum() {
		return applicationDocNum;
	}

	public void setApplicationDocNum(BigDecimal applicationDocNum) {
		this.applicationDocNum = applicationDocNum;
	}

	public List<BigDecimal> getLstApplDocNumber() {
		return lstApplDocNumber;
	}

	public void setLstApplDocNumber(List<BigDecimal> lstApplDocNumber) {
		this.lstApplDocNumber = lstApplDocNumber;
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

	public Boolean getBooRenderCustomerSignaturePanel() {
		return booRenderCustomerSignaturePanel;
	}

	public void setBooRenderCustomerSignaturePanel(Boolean booRenderCustomerSignaturePanel) {
		this.booRenderCustomerSignaturePanel = booRenderCustomerSignaturePanel;
	}

	public Boolean getBooRenderRoutingCountryBankBranch() {
		return booRenderRoutingCountryBankBranch;
	}

	public void setBooRenderRoutingCountryBankBranch(Boolean booRenderRoutingCountryBankBranch) {
		this.booRenderRoutingCountryBankBranch = booRenderRoutingCountryBankBranch;
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

	public List<PopulateData> getLstofCurrency() {
		return lstofCurrency;
	}

	public void setLstofCurrency(List<PopulateData> lstofCurrency) {
		this.lstofCurrency = lstofCurrency;
	}

	public List<PopulateData> getLstcur() {
		return lstcur;
	}

	public void setLstcur(List<PopulateData> lstcur) {
		this.lstcur = lstcur;
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

	public BigDecimal getOverseasamt() {
		return Overseasamt;
	}

	public void setOverseasamt(BigDecimal overseasamt) {
		Overseasamt = overseasamt;
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

	public List<RoutingDetails> getLstforSpecific() {
		return lstforSpecific;
	}

	public void setLstforSpecific(List<RoutingDetails> lstforSpecific) {
		this.lstforSpecific = lstforSpecific;
	}

	SessionStateManage sessionmanage = new SessionStateManage();

	public List<RoutingHeader> getRoutingbranchvalues() {
		return routingbranchvalues;
	}

	public void setRoutingbranchvalues(List<RoutingHeader> routingbranchvalues) {
		this.routingbranchvalues = routingbranchvalues;
	}

	public List<PaymentModeDesc> getPaymentModes() {
		return paymentModes;
	}

	public void setPaymentModes(List<PaymentModeDesc> paymentModes) {
		this.paymentModes = paymentModes;
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

	public List<RoutingCountry> getRoutingbankvalues() {
		return routingbankvalues;
	}

	public void setRoutingbankvalues(List<RoutingCountry> routingbankvalues) {
		this.routingbankvalues = routingbankvalues;
	}

	public List<RoutingCountry> getRoutingCountryvalues() {
		return routingCountryvalues;
	}

	public void setRoutingCountryvalues(List<RoutingCountry> routingCountryvalues) {
		this.routingCountryvalues = routingCountryvalues;
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

	/*
	 * public List<ServiceMasterDesc> getServiceMasterList() { return
	 * ratesUpdateService.getServiceMastersList(sessionmanage.getLanguageId());
	 * }
	 * 
	 * public void setServiceMasterList(List<ServiceMasterDesc>
	 * serviceMasterList) { this.serviceMasterList = serviceMasterList; }
	 */
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

	public Boolean getBooRenderNonIndBenificaryStatusPanel() {
		return booRenderNonIndBenificaryStatusPanel;
	}

	public void setBooRenderNonIndBenificaryStatusPanel(Boolean booRenderNonIndBenificaryStatusPanel) {
		this.booRenderNonIndBenificaryStatusPanel = booRenderNonIndBenificaryStatusPanel;
	}

	public Boolean getBooRenderLayaltyServicePanel() {
		return booRenderLayaltyServicePanel;
	}

	public void setBooRenderLayaltyServicePanel(Boolean booRenderLayaltyServicePanel) {
		this.booRenderLayaltyServicePanel = booRenderLayaltyServicePanel;
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

	public List<PopulateData> getLstofDelivery() {
		return lstofDelivery;
	}

	public void setLstofDelivery(List<PopulateData> lstofDelivery) {
		this.lstofDelivery = lstofDelivery;
	}

	public void popbanklist() {
		// lstBank=getiPersonalRemittanceService().getbanklist(getBenifisCurrencyId());
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

	public String getSourceOfFund() {
		return sourceOfFund;
	}

	public void setSourceOfFund(String sourceOfFund) {
		this.sourceOfFund = sourceOfFund;
	}

	public String getIntermediaryBank() {
		return intermediaryBank;
	}

	public void setIntermediaryBank(String intermediaryBank) {
		this.intermediaryBank = intermediaryBank;
	}

	public BigDecimal getNetAmountSent() {
		return netAmountSent;
	}

	public void setNetAmountSent(BigDecimal netAmountSent) {
		this.netAmountSent = netAmountSent;
	}

	public String getFurthuerInstructions() {
		return furthuerInstructions;
	}

	public void setFurthuerInstructions(String furthuerInstructions) {
		this.furthuerInstructions = furthuerInstructions;
	}

	public BigDecimal getNetAmountPayable() {
		return netAmountPayable;
	}

	public void setNetAmountPayable(BigDecimal netAmountPayable) {
		this.netAmountPayable = netAmountPayable;
	}

	public BigDecimal getLoyaltyAmountAvailed() {
		return loyaltyAmountAvailed;
	}

	public void setLoyaltyAmountAvailed(BigDecimal loyaltyAmountAvailed) {
		this.loyaltyAmountAvailed = loyaltyAmountAvailed;
	}

	public BigDecimal getGrossAmountCalculated() {
		return grossAmountCalculated;
	}

	public void setGrossAmountCalculated(BigDecimal grossAmountCalculated) {
		this.grossAmountCalculated = grossAmountCalculated;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
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

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getCashRounding() {
		return cashRounding;
	}

	public void setCashRounding(String cashRounding) {
		this.cashRounding = cashRounding;
	}

	public String getAvailLoyaltyPoints() {
		return availLoyaltyPoints;
	}

	public void setAvailLoyaltyPoints(String availLoyaltyPoints) {
		this.availLoyaltyPoints = availLoyaltyPoints;
	}

	public String getSpecialRateRef() {
		return specialRateRef;
	}

	public void setSpecialRateRef(String specialRateRef) {
		this.specialRateRef = specialRateRef;
	}

	public BigDecimal getAmountToRemit() {
		return amountToRemit;
	}

	public void setAmountToRemit(BigDecimal amountToRemit) {
		this.amountToRemit = amountToRemit;
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

	public BigDecimal getRemitMode() {
		return remitMode;
	}

	public void setRemitMode(BigDecimal remitMode) {
		this.remitMode = remitMode;
	}

	public BigDecimal getRoutingBank() {
		return routingBank;
	}

	public void setRoutingBank(BigDecimal routingBank) {
		this.routingBank = routingBank;
	}

	public BigDecimal getRoutingCountry() {
		return routingCountry;
	}

	public void setRoutingCountry(BigDecimal routingCountry) {
		this.routingCountry = routingCountry;
	}

	// Added for first panel
	public BigDecimal getForiegnCurrency() {
		return foriegnCurrency;
	}

	public void setForiegnCurrency(BigDecimal foriegnCurrency) {
		this.foriegnCurrency = foriegnCurrency;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	// For Hiding all panels except firstPanel
	public void hideAllPanels() {
		//first panel
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderOldSmartCardPanel(false);
		//second panel
		setBooRenderBenificarySearchPanel(false);
		//third panel
		setBooRenderRemittanceServicePanel(false);
		setBooSpecialCusFCCalDataTable(false);
		//fourth panel
		setBooRenderLayaltyServicePanel(false);
		setBooRenderInstructions(false);
		setBooRenderSwiftBank1(false);
		setBooRenderSwiftBank2(false);
		//fifth panel
		setAmlboo(false);
		setAmlboomsg(false);
		setBooMessageThree(false);
		//sixth panel
		setBooRenderCustomerSignaturePanel(false);
		setBooRenderSignatureMsg(false);
		//seventh panel
		setBoorenderlastpanel(false);
		setBooShowAuthenticationPanel(false);
		setBooShowCashRoundingPanel(false);
		setBooRenderModifiedRoundData(false);
		//eight panel
		setBooRenderCollection(false);
		setBooRenderColDebitCard(false);
		setBooRendercollectiondatatable(false);
		//ninth panel
		setBooRenderPaymentDetails(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);		
		setPaymentDeatailsPanel(false);
		//final report panel
		setReceiptReportPanel(false);
	}

	public void corporateRemittancePageNavigation() {
		hideAllPanels();
		assignNullValues();
		setCardType(null);
		setSelectCardType(0);
		setIdNumber(null);
		// ../remittance/
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void loadIdType() {
		mapcomIdentityType = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), "I", "Identity Type");
	}

	public void showCardTypePanel() throws Exception {
		int typecard = getSelectCardType();
		if (typecard == 2) {
			setBooRenderBenificaryFirstPanel(true);
			setBooRenderOldSmartCardPanel(true);
		} else if (typecard == 1) {
			fetchSmartCardIdNumber();
			goFromOldSmartCardpanel();
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderOldSmartCardPanel(false);
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

	public List<PopulateData> getLstofRemittance() {
		return lstofRemittance;
	}

	public void setLstofRemittance(List<PopulateData> lstofRemittance) {
		this.lstofRemittance = lstofRemittance;
	}

	public String getCustomerCrNumber() {
		return customerCrNumber;
	}

	public void setCustomerCrNumber(String customerCrNumber) {
		this.customerCrNumber = customerCrNumber;
	}

	public BigDecimal getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(BigDecimal customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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
			setFirstName(customerDetails.getFsCustomer().getCompanyName());
			setSecondName(customerDetails.getFsCustomer().getMiddleName());
			setThirdName(customerDetails.getFsCustomer().getLastName());
			setCustomerFullName(nullCheck(getFirstName()) + " " + nullCheck(getSecondName()) + " " + nullCheck(getThirdName()));
			setCustomerIsActive(customerDetails.getFsCustomer().getIsActive());
			setCustomerExpDate(customerDetails.getIdentityExpiryDate());
			setCustomerLocalFullName(nullCheck(customerDetails.getFsCustomer().getFirstNameLocal()) + " " 
					+ nullCheck(customerDetails.getFsCustomer().getMiddleNameLocal()) + " "
					+ nullCheck(customerDetails.getFsCustomer().getLastNameLocal()));

			setCustomerTypeId(customerDetails.getFsBizComponentDataByCustomerTypeId().getComponentDataId());
			String customerTypeString = iPersonalRemittanceService.getCustomerType(getCustomerTypeId());
			if(customerTypeString !=null){
				setCustomerType(customerTypeString);
			}

			if (getCustomerExpDate() != null) {
				setCustomerExpireDateMsg(new SimpleDateFormat("dd/MM/yyyy").format(getCustomerExpDate()));
			}
			setNationality(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			setNationalityName(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
			log.info("#############################################" + customerDetails.getFsCustomer().getDateOfBirth());
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

	@Autowired
	private ICorporateRegService<T> corpRegService;

	public void getCustomerDetailsforCorp() {
		customerDetailsList = corporateRemittanceService.getRegisterId(getIdNumber().toString(), sessionStateManage.getCountryId());
		if (customerDetailsList.size() > 0 && customerDetailsList != null) {
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
		}
	}

	// first method after go clicked to fetch all customer details
	public void goFromOldSmartCardpanel() throws ParseException {
		try {
			setVisible(false);
			getCustomerDetailsforCorp();
			log.info("Entering into goFromOldSmartCardpanel method");
			coustomerBeneficaryDTList.clear();
			lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
			nationalityList = generalService.getNationalityList(sessionmanage.getLanguageId());
			/* lstCountry.clear */
			// customerDetailsList =
			// getiPersonalRemittanceService().getCustomerDetails(getIdNumber().toString(),
			// getSelectCard());
			
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
			clearData();
			clearEditFields();
			log.info("Exit into goFromOldSmartCardpanel method");
		} catch (Exception e) {
			setErrmsg("Exception occurred "+e);
			setVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			
			
		}
	}
	
	

	// Second Method to Populate Records which is Approved all condition
	public void populateCustomerDetailsFromBeneRelation() {
		log.info("Entering into populateCustomerDetailsFromBeneRelation method ");
		try{
			
			if(coustomerBeneficaryDTList != null && !coustomerBeneficaryDTList.isEmpty()){
				coustomerBeneficaryDTList.clear();
			}
			List<BenificiaryListView> isCoustomerExist = beneficaryCreation.getBeneficaryList(getCustomerNo());
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
					
					List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),rel.getServiceGroupId());
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
					

					coustomerBeneficaryDTList.add(personalRBDataTable);
					
					personalRBDataTable = null;
					rel = null;
				}
			}
			
			backFromBenificaryStatusPanel();
			
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		log.info("Exit into populateCustomerDetailsFromBeneRelation method ");
	}


	/*// Second Method to Populate Records which is Approved all condition
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
						String relationDescription = irelation.getEngRelation(rel.getRelations().getRelationsId());
						personalRBDataTable.setRelationName(relationDescription);
						List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"), rel.getBeneficaryAccount().getServicegropupId()
								.getServiceGroupId());
						if (lstServiceGroupMasterDesc.size() > 0) {
							personalRBDataTable.setServiceName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
						}
						personalRBDataTable.setBenificaryCountryName(generalService.getCountryName(sessionmanage.getLanguageId(), rel.getBeneficaryAccount().getBeneficaryCountry().getCountryId()));
						coustomerBeneficaryDTList.add(personalRBDataTable);
					}
				}

	 * if (beneficaryAccountList.size() != 0) { for (BeneficaryAccount
	 * beneficaryAccount : beneficaryAccountList) {
	 * PersonalRemmitanceBeneficaryDataTable personalRBDataTable = new
	 * PersonalRemmitanceBeneficaryDataTable();
	 * personalRBDataTable.setBenificaryMasterId
	 * (beneficaryAccount.getBeneficaryMaster
	 * ().getBeneficaryMasterSeqId());
	 * personalRBDataTable.setBenificaryAccDetailsId
	 * (beneficaryAccount.getBeneficaryAccountSeqId());
	 * personalRBDataTable
	 * .setBenificaryCountryId(beneficaryAccount.getBeneficaryCountry
	 * ().getCountryId());
	 * personalRBDataTable.setBenificaryCountryName(beneficaryAccount
	 * .getBeneficaryCountry
	 * ().getFsCountryMasterDescs().get(0).getCountryName());
	 * personalRBDataTable
	 * .setAccountNo(beneficaryAccount.getBankAccountNumber());
	 * personalRBDataTable
	 * .setBenificaryName(beneficaryAccount.getBeneficaryMaster
	 * ().getFirstName());
	 * personalRBDataTable.setBranchNameId(beneficaryAccount
	 * .getBankBranch().getBankBranchId());
	 * personalRBDataTable.setBankName
	 * (beneficaryAccount.getBank().getBankFullName());
	 * personalRBDataTable
	 * .setBankNameId(beneficaryAccount.getBank().getBankId());
	 * personalRBDataTable
	 * .setBranchName(beneficaryAccount.getBankBranch(
	 * ).getBranchFullName());
	 * personalRBDataTable.setLocation(beneficaryAccount
	 * .getBeneficaryMaster().getNationality());
	 * personalRBDataTable.setServiceName
	 * (beneficaryAccount.getServicegropupId().getServiceGroupCode());
	 * personalRBDataTable
	 * .setServiceNameId(beneficaryAccount.getServicegropupId
	 * ().getServiceGroupId());
	 * personalRBDataTable.setCurrencyId(beneficaryAccount
	 * .getCurrencyId().getCurrencyId());
	 * personalRBDataTable.setCurrencyName
	 * (beneficaryAccount.getCurrencyId().getCurrencyName());
	 * personalRBDataTable
	 * .setBranchLocation(beneficaryAccount.getBankBranch
	 * ().getLocation()); List<ServiceGroupMasterDesc>
	 * lstServiceGroupMasterDesc =
	 * serviceMasterService.LocalServiceGroupDescription(new
	 * BigDecimal(sessionmanage.isExists("languageId") ?
	 * sessionmanage.getSessionValue("languageId") : "1"),
	 * beneficaryAccount.getServicegropupId().getServiceGroupId()); if
	 * (lstServiceGroupMasterDesc.size() > 0) {
	 * personalRBDataTable.setServiceName
	 * (lstServiceGroupMasterDesc.get(0).getServiceGroupDesc()); }
	 * personalRBDataTable
	 * .setBenificaryCountryName(generalService.getCountryName
	 * (sessionmanage.getLanguageId(),
	 * beneficaryAccount.getBeneficaryCountry().getCountryId()));
	 * personalRBDataTable.setRelationName(relationName);
	 * coustomerBeneficaryDTList.add(personalRBDataTable); } }

			}
			 } 
			 } 
			log.info("coustomerBeneficaryDTList--------------size " + coustomerBeneficaryDTList.size());
			log.info("####################################################################################");
			Set<PersonalRemmitanceBeneficaryDataTable> avoidDuplicate = new HashSet<PersonalRemmitanceBeneficaryDataTable>();

	 * for (PersonalRemmitanceBeneficaryDataTable datatable :
	 * coustomerBeneficaryDTList) { avoidDuplicate.add(datatable);
	 * log.info(datatable); }


	 * for (int i = 0; i < coustomerBeneficaryDTList.size(); i++) {
	 * datatable = coustomerBeneficaryDTList.get(i);
	 * avoidDuplicate.add(datatable); log.info(datatable);
	 * log.info(datatable.hashCode());
	 * 
	 * }

			log.info("####################################################################################");
			log.info("coustomerBeneficaryDTList--------------size " + avoidDuplicate.size());
			 avoidDuplicate.addAll(coustomerBeneficaryDTList); 
			log.info("coustomerBeneficaryDTList----------after----size " + avoidDuplicate.size());
			 coustomerBeneficaryDTList.addAll(avoidDuplicate); 
			backFromBenificaryStatusPanel();
			log.info("Exit into populateCustomerDetailsFromBeneRelation method ");
		}*/




	public void exitFromPersonalRemmitance() {
		hideAllPanels();
		setSelectCardType(0);
		setCardType(null);
		setIdNumber(null);
		setAmlRecheckAuthentication(false);
		checkProExp = false;
		saveCount = 0;
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancelBooRenderIndBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancelBooRenderNonIndBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nextFromBooRenderBenificarySearchPanel() {
		beneficiaryStatusList();
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(true);
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBeneficaryStatusId(null);
		setRenderBackButton(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gotToNewBenificaryCreation() {
		beneficiaryStatusList();
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(true);
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBeneficaryStatusId(null);
		setRenderBackButton(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idNumber", getIdNumber().toString());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectCard", "0");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromCorparateRemittenceScreen", "yes");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("customerNo", getCustomerNo().toString());
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/corparatebeneficiaryCreation.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeBooRenderIndBenificaryStatusPanel() {
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
					setBooRenderNonIndBenificaryStatusPanel(false);
					setBooRenderIndBenificaryStatusPanel(true);
					setBeneficaryTypeId(new BigDecimal(1));
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
			setBooRenderNonIndBenificaryStatusPanel(false);
			setBooRenderIndBenificaryStatusPanel(true);
			setBeneficaryTypeId(new BigDecimal(0));
			setFirstName(null);
			setSecondName(null);
			setThirdName(null);
			/* @Added clear data 28/01/2015 start */
			setFourthName(null);
			setFifthName(null);
			/* @Ended 28/01/2015 */
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
			setBooRenderNonIndBenificaryStatusPanel(true);
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
			/* @Added clear data 28/01/2015 start */
			setFourthName(null);
			setFifthName(null);
			/* @Ended 28/01/2015 */
			setAddress(null);
			setNationality(null);
			setNationalityName(null);
			setTelephoneNumber(null);
			setMobileNumber(null);
		} else if (statusId == 0) {
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBooRenderBenificaryStatusPanel(true);
			setBooRenderNonIndBenificaryStatusPanel(false);
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
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void nextBooRenderNonIndBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		/* int benfiTypeId = getBeneficaryTypeId().intValue(); */
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
			customerDetailsList = getiPersonalRemittanceService().getCustomerDetailsActiveRec(getIdNumber().toString(), getSelectCard());
			if (customerDetailsList.size() > 0 && customerDetailsList != null) {
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
	public void saveBeneficaryMaster() throws ParseException {
		System.out.println("Beneficary Master ----------------------------- > ");
		Map<String, Object> mapTeleExist = getMapTeleExistToCheck();
		if (mapTeleExist.size() > 0) {
			System.out.println("*****************************************************************************************************************");
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
					/*	beneficaryMaster.setBeneficaryMasterSeqId(beneficiaryAccountDetailList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId());
					BeneficaryAccount beneficaryAccountExist = beneficiaryAccountDetailList.get(0);
					saveBeneficaryRelation(beneficaryMaster, beneficaryAccountExist);*/

					beneficaryMaster = beneficaryMasterSave();
					saveBeneficaryTelephone(beneficaryMaster);
					BeneficaryAccount beneficaryAccountExist = beneficiaryAccountDetailList.get(0);
					beneficaryMaster.setBeneficaryMasterSeqId(beneficaryAccountExist.getBeneficaryMaster().getBeneficaryMasterSeqId());
					saveBeneficaryRelation(beneficaryMaster, beneficaryAccountExist);

				}
				/*
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
				 */
			} else {

				System.out.println("#######################################################################################");


				List<BeneficaryAccount> beneficiaryAccountDetailList = null;
				if (lstfetchCashId != null && !lstfetchCashId.isEmpty()) {
					if (getServiceGroupId() != null && !(getServiceGroupId().compareTo(lstfetchCashId.get(0).getServiceGroupMasterId().getServiceGroupId()) == 0)) {
						beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExistInDb(getBankAccountNumber(), getBenifisCountryId(), getBenifisBankId(), getServicebankBranchId());
					}
				}
				if (beneficiaryAccountDetailList != null && beneficiaryAccountDetailList.size() == 0) {

					System.out.println("Customer No " + getCustomerNo());

					System.out.println("Customer Id " + getCustomerId());

					List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.SELF);

					BigDecimal selfRelationid  = selfid.get(0).getRelations().getRelationsId();

					BigDecimal masterSequenceId = null;

					if(selfRelationid.equals(getRelationId()))
					{
						masterSequenceId = beneficaryCreation.checkMasterSequenceExist(getCustomerNo(),getRelationId());
						System.out.println("masterSequenceId " + masterSequenceId);

					}
					if(masterSequenceId!=null) {
						beneficaryMaster = beneficaryMasterSave(masterSequenceId);
					}
					else
					{
						beneficaryMaster =  beneficaryMasterSave();
					}
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
					beneficaryMaster.setBeneficaryMasterSeqId(beneficaryAccountExist.getBeneficaryMaster().getBeneficaryMasterSeqId());
					saveBeneficaryRelation(beneficaryMaster, beneficaryAccountExist);
				}
			}
		} else {

			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

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
		/*
		 * clear(); coustomerBeneficaryDTList.clear();
		 * goFromOldSmartCardpanel();
		 */
	}

	private BeneficaryMaster beneficaryMasterSave(BigDecimal masterseqId) {
		BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
		beneficaryMaster.setBeneficaryMasterSeqId(masterseqId);

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
		/*
		 * for (BeneficaryStatus beneficaryStatusvalues : benificaryStatusName)
		 * { if (getBeneficaryStatusId().compareTo(beneficaryStatusvalues.
		 * getBeneficaryStatusId()) == 0) { System.out.println("%%%%%%%%%%%%%%%"
		 * + Constants.Individual);
		 * beneficaryMaster.setBeneficaryStatusName(Constants.Individual);
		 * break; } else { System.out.println("%%%%%%%%%%%%%%%" +
		 * Constants.NonIndividual);
		 * beneficaryMaster.setBeneficaryStatusName(Constants.NonIndividual); }
		 * }
		 */
		/*
		 * if (getBeneficaryStatusName().equals(Constants.NonIndividual) ||
		 * getBeneficaryStatusName().equals(Constants.Corporate)) {
		 * beneficaryMaster.setBeneficaryStatusName(Constants.NonIndividual); }
		 */
		if (getBeneficaryStatusName().equals(Constants.CUSTOMERTYPE_INDIVIDUAL)) {
			beneficaryMaster.setBeneficaryStatusName(Constants.Individual);
		} else {
			beneficaryMaster.setBeneficaryStatusName(Constants.NonIndividual);
		}
		getiPersonalRemittanceService().saveBeneficiaryMaster(beneficaryMaster);
		return beneficaryMaster;
	}

	// For Save Beneficary Relation
	// For Save Beneficary Relation
	public void saveBeneficaryRelation(BeneficaryMaster beneficaryMaster, BeneficaryAccount beneficaryAccount) {
		System.out.println("1 -------------------------------------------------------------- > ");
		/*
		 * List<BeneficaryRelationship> objBeneficaryRelationshipList =
		 * getiPersonalRemittanceService
		 * ().isBenificaryRelationExistInDB(beneficaryMaster
		 * .getBeneficaryMasterSeqId());
		 */

		List<BeneficaryRelationship> objBeneficaryRelationshipList = getiPersonalRemittanceService().isBenificaryRelationExist(beneficaryMaster.getBeneficaryMasterSeqId(), beneficaryAccount.getBeneficaryAccountSeqId());


		log.info("@@@@@@@@@@@@@" + getRelationId());
		log.info("@@@@@@@@@@@@@" + getRelationId());
		log.info("@@@@@@@@@@@@@" + getRelationId());


		List<RelationsDescription> selfid = irelation.getAllActiveInActive(Constants.SELF);

		BigDecimal selfRelationid  = selfid.get(0).getRelations().getRelationsId();

		//	List<BeneficaryRelationship> objBeneficaryRelationshipList = beneficaryCreation.isBenificaryRelationExist(beneficaryMaster.getBeneficaryMasterSeqId(), getRelationId());



		if(objBeneficaryRelationshipList.size() > 0  && selfRelationid!= null && selfRelationid.equals(objBeneficaryRelationshipList.get(0).getRelations().getRelationsId())
				&& getBeneficaryTypeId().equals(new BigDecimal(2))	)
		{
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
		else if (objBeneficaryRelationshipList.size() > 0 ) {
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
			/*
			 * ServiceMaster serviceMaster = new ServiceMaster();
			 * serviceMaster.setServiceId(getServiceId());
			 * beneficaryAccount.setExServiceId(serviceMaster);
			 */
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
			/*
			 * if (getServiceTypeId() != null) { BankMaster
			 * bankMasterServiceProvider = new BankMaster();
			 * bankMasterServiceProvider.setBankId(getServiceTypeId());
			 * beneficaryAccount.setServiceProvider(bankMasterServiceProvider);
			 * }
			 */
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

	public void saveRemittanceInfo() throws ParseException {
		saveBeneficaryMaster();
	}

	public void backFromRemmitanceServicePanel() {
		setBooRenderTypeOfServicePanel(true);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderRemittanceServicePanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderCustomerSignaturePanel(false);
	}

	public void backFromTypesOfServicePanel() {/*
	 * if
	 * (getBeneficaryStatusId().equals
	 * (new BigDecimal(1))) {
	 * setBooRenderBenificaryStatusPanel
	 * (true);
	 * setBooRenderIndBenificaryStatusPanel
	 * (true);
	 * setBooRenderRemittanceServicePanel
	 * (false);
	 * setBooRenderTypeOfServicePanel
	 * (false);
	 * setBooRenderBenificarySearchPanel
	 * (false);
	 * setBooRenderNonIndBenificaryStatusPanel
	 * (false);
	 * setBooRenderBenificaryFirstPanel
	 * (false);
	 * setBooRenderCustomerSignaturePanel
	 * (false); } else if
	 * (getBeneficaryStatusId
	 * ().equals(new BigDecimal(2)))
	 * {
	 * setBooRenderBenificaryStatusPanel
	 * (true);
	 * setBooRenderNonIndBenificaryStatusPanel
	 * (true);
	 * setBooRenderRemittanceServicePanel
	 * (false);
	 * setBooRenderTypeOfServicePanel
	 * (false);
	 * setBooRenderBenificarySearchPanel
	 * (false);
	 * setBooRenderIndBenificaryStatusPanel
	 * (false);
	 * setBooRenderBenificaryFirstPanel
	 * (false);
	 * setBooRenderCustomerSignaturePanel
	 * (false); }
	 */
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/corparatebeneficiaryCreation.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void nextToRemmitancePanel() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderRemittanceServicePanel(true);
		setBooRenderTypeOfServicePanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderCustomerSignaturePanel(false);
	}

	public void backFromBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(true);
		setBooRenderRemittanceServicePanel(false);
		setBooRenderTypeOfServicePanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooSpecialCusFCCalDataTable(false);
		setBoorenderlastpanel(false);
		setBooRenderCustomerSignaturePanel(false);
	}

	public void nextToTypesServicePanel() {
		if ((getDateOfBrith() == null && getYearOfBrith() == null && getAge() == null)) {
			setYearOfBrith(null);
			setAge(null);
			RequestContext.getCurrentInstance().execute("checking.show();");
		} else if (getTelephoneNumber() == null && getMobileNumber() == null) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg("Please enter Telephone or Mobile");
		} else {
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBooRenderBenificaryStatusPanel(false);
			setBooRenderNonIndBenificaryStatusPanel(false);
			setBooRenderIndBenificaryStatusPanel(false);
			setBooRenderTypeOfServicePanel(true);
			secondClear();
			setBooenableAgentPanel(null);
			setBooRenderCustomerSignaturePanel(false);
		}
	}

	public void backFromBenificarySearchPanel() {
		//first panel
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderOldSmartCardPanel(false);
		//second panel
		setBooRenderBenificarySearchPanel(false);
		//third panel
		setBooRenderRemittanceServicePanel(false);
		setBooSpecialCusFCCalDataTable(false);
		//fourth panel
		setBooRenderLayaltyServicePanel(false);
		//fifth panel
		setAmlboo(false);
		setAmlboomsg(false);
		//sixth panel
		setBooRenderCustomerSignaturePanel(false);
		setBooRenderSignatureMsg(false);
		//seventh panel
		setBoorenderlastpanel(false);
		//eight panel
		setBooRenderCollection(false);
		//ninth panel
		setBooRenderPaymentDetails(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);		
		setPaymentDeatailsPanel(false);
		//final report panel
		setReceiptReportPanel(false);
	}

	public void getServiceFirstPanel() {
		// calculateForeignCurrencyAmount();
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderLayaltyServicePanel(false);
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooRenderRemittanceServicePanel(true);
		setBooRenderTypeOfServicePanel(false);
		setBooRenderCustomerSignaturePanel(false);
	}

	public void backToServiceFirstPanel() {
		if (personalRemittCalFCAmountDTList.size() != 0) {
			setBooSpecialCusFCCalDataTable(true);
		} else {
			setBooSpecialCusFCCalDataTable(false);
		}
		getServiceFirstPanel();
	}

	// TO render service second panel
	public Boolean getCheckingSplPool() {
		return checkingSplPool;
	}

	public void setCheckingSplPool(Boolean checkingSplPool) {
		this.checkingSplPool = checkingSplPool;
	}
	
	int saveCount = 0;
	Boolean checkProExp = false;

	public void nextServiceSecondPanel() throws ParseException, AMGException {/*
		try {
			setVisible(false);
			setCheckingSplPool(false);
			dynamicLevel();
			matchData();
			if (personalRemittCalFCAmountDTList.size() != 0 && getBooSpecialCusFCCalDataTable().equals(true)) {
				for (PersonalRemittanceCalFCAmountDataTable dataTableObj : getPersonalRemittCalFCAmountDTList()) {
					if (dataTableObj.getSelect().equals(true)) {
						setSpldealreqid(dataTableObj.getSpecialCustomerPrimaryKey());
						setSplcompanyid(dataTableObj.getSplcompanyID());
						setSpldocnum(dataTableObj.getSpldocnumID());
						setSplfinyear(dataTableObj.getSplfinyearID());
						setSpldocumentid(dataTableObj.getSpldocumentID());
						checkAvailableBalance(dataTableObj);
						setCheckingSplPool(true);
					} else {
						// RequestContext.getCurrentInstance().execute("fcSelect.show();");
					}
				}
				if (getCheckingSplPool()) {
					setSpecialRateRef(Constants.Yes);
					setSpotRate(Constants.No);
				} else {
					setSpecialRateRef(Constants.No);
					setSpotRate(Constants.No);
					gotoNextPanel();
				}
			} else {
				gotoNextPanel();
			}
		} catch (Exception e) {
			
			setErrmsg("Exception occurred "+e);
					setVisible(true);
					RequestContext.getCurrentInstance().execute("error.show();");
		}
	*/
	

		
		updateSpotrateRecord();
		
		if(getSpotRate()!=null){
		
		checkProExp = false;
		saveCount = 0;
		setExceptionMessage(null);
		setCheckingSplPool(false);
		setAdditionalCheck(true);
		dynamicLevel();
		matchData();
		BigDecimal sourceId = isourceOfIncome.getSourceOfIncomeIdBasedOnName("SALARY" , sessionStateManage.getLanguageId());
		if(sourceId !=null){
			setSourceOfIncome(sourceId);
		}
		if (personalRemittCalFCAmountDTList.size() != 0 && getBooSpecialCusFCCalDataTable()) {
			for (PersonalRemittanceCalFCAmountDataTable dataTableObj : getPersonalRemittCalFCAmountDTList()) {
				if (dataTableObj.getSelect()) {
					setSpldealreqid(dataTableObj.getSpecialCustomerPrimaryKey());
					setSplcompanyid(dataTableObj.getSplcompanyID());
					setSpldocnum(dataTableObj.getSpldocnumID());
					setSplfinyear(dataTableObj.getSplfinyearID());
					setSpldocumentid(dataTableObj.getSpldocumentID());
					checkAvailableBalance(dataTableObj);
					setCheckingSplPool(true);
				} else {
					// RequestContext.getCurrentInstance().execute("fcSelect.show();");
				}
			}
			if (getCheckingSplPool()) {
				setSpecialRateRef(Constants.Yes);
				setSpotRate(Constants.No);
				gotoNextPanel();
			} else {
				setSpecialRateRef(Constants.No);
				setSpotRate(Constants.No);
				gotoNextPanel();
			}
		} else {
			gotoNextPanel();
		}
		}
	
	
	}
	
	public void updateSpotrateRecord(){
		if(getSpotRate().equalsIgnoreCase(Constants.Yes)){
			SimpleDateFormat formate = new SimpleDateFormat("dd-MM-YY");
			String currentDate = formate.format(new Date());
			List<SpecialRateRequest> spotRateValue = iSpecialRateRequest.getActiveSpecialRateRequest(getCustomerNo(), getAmountToRemit(),currentDate, getMasterId(),getBeneficaryBankId());
			log.info("spotrateamount=="+getAmountToRemit()+""+spotRateValue.size());
			if(spotRateValue.size()==0){
				List<SpecialRateRequest> spotRateValues = iSpecialRateRequest.fetchSpotRateRecords(getCustomerNo(), currentDate, getMasterId(),getBeneficaryBankId());
				if(spotRateValues.size()>0){ 
					SpecialRateRequest listValue=spotRateValues.get(0);
					setSpotRate(null);
					setSpotRateForDispay(listValue.getSellRate());
					setRemittanceAmountForDisplay(listValue.getFcAmount() );
					log.info("insideelseblock===="+getAmountToRemit());
					if(listValue.getSellRate()!=null){
						log.info(" ======="+getSpotRate());

						RequestContext.getCurrentInstance().execute("spotrateback.show();");
					}
				}
			}
		}
	}

	public void gotoNextPanel()  {
		
		
		getExchangeRatevalues();
		
		//dynamicLevel();
	}

	public void getServiceSecondPanel() {
		setBooSpecialCusFCCalDataTable(true);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderLayaltyServicePanel(true);
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooRenderRemittanceServicePanel(false);
		setBooRenderTypeOfServicePanel(false);
		setBooRenderCustomerSignaturePanel(false);
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
	public void personalRemittanceNavigation() {
		try {
			goFromOldSmartCardpanel();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
			backFromBenificaryStatusPanel();
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
		beneServiceCurrencyList.clear();
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

	public String getDataAccountnum() {
		return dataAccountnum;
	}

	public void setDataAccountnum(String dataAccountnum) {
		this.dataAccountnum = dataAccountnum;
	}

	public String getSpotRate() {
		return spotRate;
	}

	public void setSpotRate(String spotRate) {
		this.spotRate = spotRate;
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

	public boolean isBooSingleRoutingCountry() {
		return booSingleRoutingCountry;
	}

	public void setBooSingleRoutingCountry(boolean booSingleRoutingCountry) {
		this.booSingleRoutingCountry = booSingleRoutingCountry;
	}

	public boolean isBooMultipleRoutingCountry() {
		return booMultipleRoutingCountry;
	}

	public void setBooMultipleRoutingCountry(boolean booMultipleRoutingCountry) {
		this.booMultipleRoutingCountry = booMultipleRoutingCountry;
	}

	public boolean isBooSingleRoutingBank() {
		return booSingleRoutingBank;
	}

	public void setBooSingleRoutingBank(boolean booSingleRoutingBank) {
		this.booSingleRoutingBank = booSingleRoutingBank;
	}

	public boolean isBooMultipleRoutingBank() {
		return booMultipleRoutingBank;
	}

	public void setBooMultipleRoutingBank(boolean booMultipleRoutingBank) {
		this.booMultipleRoutingBank = booMultipleRoutingBank;
	}

	public boolean isBooSingleRoutingBranch() {
		return booSingleRoutingBranch;
	}

	public void setBooSingleRoutingBranch(boolean booSingleRoutingBranch) {
		this.booSingleRoutingBranch = booSingleRoutingBranch;
	}

	public boolean isBooMultipleRoutingBranch() {
		return booMultipleRoutingBranch;
	}

	public void setBooMultipleRoutingBranch(boolean booMultipleRoutingBranch) {
		this.booMultipleRoutingBranch = booMultipleRoutingBranch;
	}

	public String getRoutingCountryName() {
		return routingCountryName;
	}

	public void setRoutingCountryName(String routingCountryName) {
		this.routingCountryName = routingCountryName;
	}

	public String getRoutingBankName() {
		return routingBankName;
	}

	public void setRoutingBankName(String routingBankName) {
		this.routingBankName = routingBankName;
	}

	public String getRoutingBranchName() {
		return routingBranchName;
	}

	public void setRoutingBranchName(String routingBranchName) {
		this.routingBranchName = routingBranchName;
	}

	public boolean isBooSingleRemit() {
		return booSingleRemit;
	}

	public void setBooSingleRemit(boolean booSingleRemit) {
		this.booSingleRemit = booSingleRemit;
	}

	public boolean isBooMultipleRemit() {
		return booMultipleRemit;
	}

	public void setBooMultipleRemit(boolean booMultipleRemit) {
		this.booMultipleRemit = booMultipleRemit;
	}

	public String getRemittanceName() {
		return remittanceName;
	}

	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}

	public void clearTransactionDetails() {
		setDatabenificarycountry(null);
		setDatabenificarycurrency(null);
		setDatabenificaryservice(null);
		setDataserviceid(null);
		setDataAccountnum(null);
		setDatabenificarycountryname(null);
		setDatabenificarycurrencyname(null);
		setDatabenificarybankname(null);
		setDatabenificarybranchname(null);
		setDatabenificaryname(null);
		setBenificiaryryNameRemittance(null);
		setMasterId(null);
		setBeneficaryBankId(null);
		setBeneficaryBankBranchId(null);
		setRoutingCountryName(null);
		setRoutingCountry(null);
		setRoutingBankName(null);
		setRoutingBank(null);
		setRoutingBranchName(null);
		setRoutingBankBranchName(null);
		setRoutingBranch(null);
		setBenificarystatus(null);
		setBenificaryTelephone(null);
		setCurrency(null);
		setSpotRate(null);
		setSpecialRateRef(null);
		setAvailLoyaltyPoints(null);
		setChargesOverseas(null);
		setForiegnCurrency(null);
	}

	public void gotoRemittanceservice(PersonalRemmitanceBeneficaryDataTable datatabledetails) {
		clearData();
		clearTransactionDetails();
		setDatabenificarycountry(datatabledetails.getBenificaryCountry());
		setDatabenificarycurrency(datatabledetails.getCurrencyId());
		setDatabenificaryservicegroup(datatabledetails.getServiceGroupCode());
		setDataservicegroupid(datatabledetails.getServiceGroupId());
		setDataAccountnum(datatabledetails.getBankAccountNumber());
		setDatabenificarycountryname(datatabledetails.getBenificaryCountryName());
		setDatabenificarycurrencyname(datatabledetails.getCurrencyName());
		setDatabenificarybankname(datatabledetails.getBankName());
		setDatabenificarybranchname(datatabledetails.getBankBranchName());
		setDatabenificaryname(datatabledetails.getBenificaryName());
		setBenificiaryryNameRemittance(datatabledetails.getBenificaryName());
		setMasterId(datatabledetails.getBeneficaryMasterSeqId());
		setBeneficaryBankId(datatabledetails.getBankId());
		setBeneficaryBankBranchId(datatabledetails.getBranchId());
		boolean checkingbankIndicator = iPersonalRemittanceService.bankIndcheckingbank(Constants.BANK_INDICATOR_CORR_BANK, getBeneficaryBankId());
		if (checkingbankIndicator) {
			setBooRenderRoutingCountryBankBranch(false);
			setRoutingCountryName(datatabledetails.getBenificaryCountryName());
			setRoutingCountry(datatabledetails.getBenificaryCountry());
			setRoutingBankName(datatabledetails.getBankName());
			setRoutingBank(datatabledetails.getBankId());
			setRoutingBranchName(datatabledetails.getBankBranchName());
			setRoutingBranch(datatabledetails.getBranchId());
			List<BeneCountryService> lstService = iPersonalRemittanceService.getServiceIdBeneCountrySer(datatabledetails.getBenificaryCountry(), datatabledetails.getCurrencyId(), datatabledetails.getServiceGroupId(), getBeneficaryBankId());
			if (lstService.size() != 0) {
				if (lstService.size() == 1) {
					setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"), lstService.get(0).getServiceId().getServiceId()).get(0).getLocalServiceDescription());
					setDataserviceid(lstService.get(0).getServiceId().getServiceId());
					setRemittanceName(iRemitModeMaster.getRemittanceDesc(lstService.get(0).getRemitanceId().getRemittanceModeId(), new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1")));
					setRemitMode(lstService.get(0).getRemitanceId().getRemittanceModeId());
					setDeliveryModeInput(iDeliveryModeMaster.getDeliveryDesc(lstService.get(0).getDeliveryId().getDeliveryModeId(), new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1")));
					setDeliveryMode(lstService.get(0).getDeliveryId().getDeliveryModeId());
				} else {
					BigDecimal serviceId = serviceMasterService.getServiceIdbyServiceName(Constants.EFTNAME);
					for (BeneCountryService beneCountryService : lstService) {
						if (beneCountryService.getServiceId().getServiceId().compareTo(serviceId) == 0) {
							setDataserviceid(beneCountryService.getServiceId().getServiceId());
							setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"), beneCountryService.getServiceId().getServiceId()).get(0).getLocalServiceDescription());
							setRemittanceName(iRemitModeMaster.getRemittanceDesc(beneCountryService.getRemitanceId().getRemittanceModeId(), new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1")));
							setRemitMode(beneCountryService.getRemitanceId().getRemittanceModeId());
							setDeliveryModeInput(iDeliveryModeMaster.getDeliveryDesc(beneCountryService.getDeliveryId().getDeliveryModeId(), new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1")));
							setDeliveryMode(beneCountryService.getDeliveryId().getDeliveryModeId());
						}
					}
				}
			}
			specialRequestFcAmountCalculation();
		} else {
			setBooRenderRoutingCountryBankBranch(true);
			List<BeneCountryService> lstService = iPersonalRemittanceService.getServiceIdBeneCountrySer(datatabledetails.getBenificaryCountry(), datatabledetails.getCurrencyId(), datatabledetails.getServiceGroupId(), getBeneficaryBankId());
			if (lstService.size() != 0) {
				if (lstService.size() == 1) {
					setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"), lstService.get(0).getServiceId().getServiceId()).get(0).getLocalServiceDescription());
					setDataserviceid(lstService.get(0).getServiceId().getServiceId());
					setRemittanceName(iRemitModeMaster.getRemittanceDesc(lstService.get(0).getRemitanceId().getRemittanceModeId(), new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1")));
					setRemitMode(lstService.get(0).getRemitanceId().getRemittanceModeId());
					setDeliveryModeInput(iDeliveryModeMaster.getDeliveryDesc(lstService.get(0).getDeliveryId().getDeliveryModeId(), new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1")));
					setDeliveryMode(lstService.get(0).getDeliveryId().getDeliveryModeId());
				} else {
					BigDecimal serviceId = serviceMasterService.getServiceIdbyServiceName(Constants.TTNAME);
					for (BeneCountryService beneCountryService : lstService) {
						if (beneCountryService.getServiceId().getServiceId().compareTo(serviceId) == 0) {
							setDataserviceid(beneCountryService.getServiceId().getServiceId());
							setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"), beneCountryService.getServiceId().getServiceId()).get(0).getLocalServiceDescription());
							setRemittanceName(iRemitModeMaster.getRemittanceDesc(beneCountryService.getRemitanceId().getRemittanceModeId(), new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1")));
							setRemitMode(beneCountryService.getRemitanceId().getRemittanceModeId());
							setDeliveryModeInput(iDeliveryModeMaster.getDeliveryDesc(beneCountryService.getDeliveryId().getDeliveryModeId(), new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1")));
							setDeliveryMode(beneCountryService.getDeliveryId().getDeliveryModeId());
						}
					}
				}
			}
			// to get routing Country from EX_BENE_COUNTRY_SERVICE and
			// EX_ROUTING_HEADER
			List<RoutingCountry> lstRoutingCountry = generalService.getAllRoutingCountryList(datatabledetails.getBenificaryCountry(), datatabledetails.getCurrencyId(), datatabledetails.getServiceGroupId(), getBeneficaryBankId());
			if (lstRoutingCountry.size() == 0) {
				setBooSingleRoutingCountry(true);
				setBooMultipleRoutingCountry(false);
				setRoutingCountryName(null);
				setRoutingCountry(null);
				RequestContext.getCurrentInstance().execute("routingCountryNoData.show();");
				return;
			} else if (lstRoutingCountry.size() == 1) {
				setBooSingleRoutingCountry(true);
				setBooMultipleRoutingCountry(false);
				setRoutingCountryName(lstRoutingCountry.get(0).getRoutingCountryName());
				setRoutingCountry(lstRoutingCountry.get(0).getRoutingCountryId());
				getBeneficiaryBankList();
			} else {
				setRoutingCountryName(null);
				setBooSingleRoutingCountry(false);
				setBooMultipleRoutingCountry(true);
				setRoutingCountryvalues(lstRoutingCountry);
			}
			if (getDatabenificaryservicegroup().equalsIgnoreCase(Constants.CASHNAME)) {
				setBooRenderAgent(true);
				setBooRenderRouting(false);
			} else {
				setBooRenderAgent(false);
				setBooRenderRouting(true);
			}
		}
		// to get Beneficary status from Benificary Master
		beneficiaryMaster = iPersonalRemittanceService.getAllMasterValues(getMasterId());
		if (beneficiaryMaster.size() > 0) {
			setBenificarystatus(beneficiaryMaster.get(0).getBeneficaryStatusName());
		}
		// to get Beneficary telephone from telephone details table
		beneficiaryTel = iPersonalRemittanceService.getTelephoneDetails(getMasterId());
		for (BeneficaryContact lsttelenum : beneficiaryTel) {
			if (beneficiaryTel.size() > 0) {
				if (lsttelenum.getBeneficaryMaster().getBeneficaryMasterSeqId().compareTo(datatabledetails.getBeneficaryMasterSeqId()) == 0) {
					if (lsttelenum.getTelephoneNumber() !=null ){

						setBenificaryTelephone(new BigDecimal(lsttelenum.getTelephoneNumber()));
					}

				}
			}
		}
		lstofCurrency.clear();
		PopulateData populatedata = new PopulateData(getDatabenificarycurrency(), getDatabenificarycurrencyname());
		PopulateData populatedata1 = null;
		String localCurrencyName = generalService.getCurrencyName(new BigDecimal(sessionmanage.getCurrencyId()));
		if(localCurrencyName != null){
			populatedata1 = new PopulateData(new BigDecimal(sessionmanage.getCurrencyId()),localCurrencyName);
		}
		lstofCurrency.add(populatedata);
		if(populatedata1 != null){
			lstofCurrency.add(populatedata1);
		}
		if (lstofCurrency.size() != 0) {
			for (PopulateData lstofcurrency : lstofCurrency) {
				if (lstofcurrency.getPopulateId().compareTo(new BigDecimal(sessionmanage.getCurrencyId())) != 0) {
					setForiegnCurrency(lstofcurrency.getPopulateId());
				}
			}
		}
		setCurrency(new BigDecimal(sessionmanage.getCurrencyId()));
		setSpotRate(Constants.No);
		setSpecialRateRef(Constants.No);
		setNextRender(true);
		setAvailLoyaltyPoints(Constants.No);
		setChargesOverseas(Constants.No);
		getServiceFirstPanel();
	}

	/*
	 * public void gotoRemittanceservice(PersonalRemmitanceBeneficaryDataTable
	 * datatabledetails) { clearData();
	 * setDatabenificarycountry(datatabledetails.getBenificaryCountryId());
	 * setDatabenificarycurrency(datatabledetails.getCurrencyId());
	 * setDatabenificaryservice(datatabledetails.getServiceNameId());
	 * setDataserviceid(datatabledetails.getServiceNameId());
	 * setDataAccountnum(datatabledetails.getAccountNo());
	 * setDatabenificarycountryname
	 * (datatabledetails.getBenificaryCountryName());
	 * setDatabenificarycurrencyname(datatabledetails.getCurrencyName());
	 * setDatabenificarybankname(datatabledetails.getBankName());
	 * setDatabenificarybranchname(datatabledetails.getBranchName());
	 * setDatabenificaryname(datatabledetails.getBenificaryName());
	 * setBenificiaryryNameRemittance(datatabledetails.getBenificaryName());
	 * setMasterId(datatabledetails.getBenificaryMasterId()); // setting
	 * bankId,brachId,telephone num
	 * setBenificaryTelephone(datatabledetails.getTelphoneNum());
	 * setBeneficaryBankId(datatabledetails.getBankNameId());
	 * setBeneficaryBankBranchId(datatabledetails.getBranchNameId()); // to get
	 * Beneficary status from Benificary Master beneficiaryMaster =
	 * iPersonalRemittanceService.getAllMasterValues(getMasterId()); if
	 * (beneficiaryMaster.size() > 0) {
	 * setBenificarystatus(beneficiaryMaster.get(0).getBeneficaryStatusName());
	 * } // to get Beneficary telephone from telephone details table
	 * beneficiaryTel =
	 * iPersonalRemittanceService.getTelephoneDetails(getMasterId()); if
	 * (beneficiaryTel.size() > 0) { if
	 * (beneficiaryTel.get(0).getBeneficaryMaster
	 * ().getBeneficaryMasterSeqId().compareTo
	 * (datatabledetails.getBenificaryMasterId()) == 0) {
	 * setBenificaryTelephone(datatabledetails.getTelphoneNum()); } } // to get
	 * Beneficary Bank and Branch from Acc num Details beneficaryAccountList =
	 * iPersonalRemittanceService.getCustomerBeneficaryDetails(getMasterId());
	 * if (beneficaryAccountList.size() > 0) { if
	 * (beneficaryAccountList.get(0).getBeneficaryMaster
	 * ().getBeneficaryMasterSeqId
	 * ().compareTo(datatabledetails.getBenificaryMasterId()) == 0) {
	 * setBeneficaryBankName(datatabledetails.getBankName());
	 * setBeneficaryBankId(datatabledetails.getBankNameId());
	 * setBeneficaryBankBranchId(datatabledetails.getBranchNameId());
	 * setBeneficaryBankBranchName(datatabledetails.getBranchName()); } } // to
	 * get routing Country from EX_BENE_COUNTRY_SERVICE and // EX_ROUTING_HEADER
	 * List<RoutingCountry> lstRoutingCountry =
	 * generalService.getAllRoutingCountryList
	 * (datatabledetails.getBenificaryCountryId(),
	 * datatabledetails.getCurrencyId(), datatabledetails.getServiceNameId(),
	 * getBeneficaryBankId()); if (lstRoutingCountry.size() == 0) {
	 * setBooSingleRoutingCountry(true); setBooMultipleRoutingCountry(false);
	 * setRoutingCountryName(null); setRoutingCountry(null);
	 * RequestContext.getCurrentInstance
	 * ().execute("routingCountryNoData.show();"); return; } else if
	 * (lstRoutingCountry.size() == 1) { setBooSingleRoutingCountry(true);
	 * setBooMultipleRoutingCountry(false);
	 * setRoutingCountryName(lstRoutingCountry.get(0).getRoutingCountryName());
	 * setRoutingCountry(lstRoutingCountry.get(0).getRoutingCountryId());
	 * getBeneficiaryBankList(); } else { setRoutingCountryName(null);
	 * setBooSingleRoutingCountry(false); setBooMultipleRoutingCountry(true);
	 * setRoutingCountryvalues(lstRoutingCountry); } // to get mode of payment
	 * paymentModes = iPersonalRemittanceService.getPaymentModeDetails(new
	 * BigDecimal(sessionmanage.isExists("languageId") ?
	 * sessionmanage.getSessionValue("languageId") : "1")); if
	 * (paymentModes.size() > 0) { System.out.println(paymentModeId +
	 * "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + paymentModes.size()); } // to get
	 * remitter mode and delivery mode servicecodedeclaration(); // CR Changed
	 * For Data INR Not Populating // currencyMasterList = //
	 * iPersonalRemittanceService
	 * .getCurrencyMasterList(sessionmanage.getCountryId(), //
	 * datatabledetails.getCurrencyId());
	 * 
	 * if(currencyMasterList.size() != 0){ for (CurrencyMaster lstofcurrency :
	 * currencyMasterList) { if(lstofcurrency.getCurrencyId().compareTo(new
	 * BigDecimal(sessionmanage.getCurrencyId()))!=0){
	 * setForiegnCurrency(lstofcurrency.getCurrencyId()); } } }
	 * 
	 * lstofCurrency.clear(); PopulateData populatedata = new
	 * PopulateData(getDatabenificarycurrency(),
	 * getDatabenificarycurrencyname()); PopulateData populatedata1 = new
	 * PopulateData(new BigDecimal(sessionmanage.getCurrencyId()),
	 * generalService.getCurrencyName(new
	 * BigDecimal(sessionmanage.getCurrencyId())));
	 * lstofCurrency.add(populatedata); lstofCurrency.add(populatedata1); if
	 * (lstofCurrency.size() != 0) { for (PopulateData lstofcurrency :
	 * lstofCurrency) { if (lstofcurrency.getPopulateId().compareTo(new
	 * BigDecimal(sessionmanage.getCurrencyId())) != 0) {
	 * setForiegnCurrency(lstofcurrency.getPopulateId()); } } } setCurrency(new
	 * BigDecimal(sessionmanage.getCurrencyId())); setSpotRate(Constants.No);
	 * setNextRender(true); setAvailLoyaltyPoints(Constants.No);
	 * setChargesOverseas(Constants.No); getServiceFirstPanel(); }
	 */
	public List<CurrencyMaster> getCurrencyMasterList() {
		return currencyMasterList;
	}

	public void setCurrencyMasterList(List<CurrencyMaster> currencyMasterList) {
		this.currencyMasterList = currencyMasterList;
	}

	public List<BankAccountDetails> getCurrencylistForService() {
		return currencylistForService;
	}

	public void setCurrencylistForService(List<BankAccountDetails> currencylistForService) {
		this.currencylistForService = currencylistForService;
	}

	// get Routing Bank from Routing Header
	public void getBeneficiaryBankList() {
		try {
			setVisible(false);
			List<RoutingCountry> lstRoutingBank = generalService.getAllRoutingBankList(getRoutingCountry(), getDatabenificarycurrency(), getDataservicegroupid(), getBeneficaryBankId());
			if (lstRoutingBank.size() == 0) {
				setBooSingleRoutingBank(true);
				setBooMultipleRoutingBank(false);
				setRoutingBankName(null);
				setRoutingBank(null);
				RequestContext.getCurrentInstance().execute("routingBankNoData.show();");
				return;
			} else if (lstRoutingBank.size() == 1) {
				setBooSingleRoutingBank(true);
				setBooMultipleRoutingBank(false);
				setRoutingBankName(lstRoutingBank.get(0).getRoutingCountryName());
				setRoutingBank(lstRoutingBank.get(0).getRoutingCountryId());
				getRoutingbranch();
			} else {
				setRoutingBankName(null);
				setBooSingleRoutingBank(false);
				setBooMultipleRoutingBank(true);
				setRoutingbankvalues(lstRoutingBank);
			}
		} catch (Exception e) {
			setErrmsg("Exception occurred "+e);
			setVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	// on select of service code
	public void servicecodedeclaration() {
		servicedatafromdb.clear();
		deliverydataBasedonService.clear();
		// to get remittance details based on service
		/*
		 * remittancedata =
		 * iroutingSetUpDetailsService.getRemittanceModeMaster(sessionmanage
		 * .getLanguageId(), getDataserviceid());
		 */
		List<PopulateData> lstofRemittance = iPersonalRemittanceService.lstOfRemittance(getRoutingCountry(), getDatabenificarycurrency(), getDataserviceid(), getRoutingBank());
		if (lstofRemittance.size() == 0) {
			setBooSingleRemit(true);
			setBooMultipleRemit(false);
			setRemittanceName(null);
			setRemitMode(null);
			RequestContext.getCurrentInstance().execute("remittanceNoData.show();");
			return;
		} else if (lstofRemittance.size() == 1) {
			setBooSingleRemit(true);
			setBooMultipleRemit(false);
			setRemittanceName(lstofRemittance.get(0).getPopulateName());
			setRemitMode(lstofRemittance.get(0).getPopulateId());
		} else {
			setRemittanceName(null);
			setBooSingleRemit(false);
			setBooMultipleRemit(true);
			setLstofRemittance(lstofRemittance);
		}
		// to get the delivery details based on service
		/*
		 * deliverydata =
		 * iroutingSetUpDetailsService.getDeliveryMode(sessionmanage
		 * .getLanguageId(), getDataserviceid());
		 */
		List<PopulateData> lstofDelivery = iPersonalRemittanceService.lstOfDelivery(getRoutingCountry(), getDatabenificarycurrency(), getDataserviceid(), getRoutingBank());
		if (lstofDelivery.size() == 0) {
			setBooRenderDeliveryModeInputPanel(true);
			setBooRenderDeliveryModeDDPanel(false);
			setDeliveryModeInput(null);
			setDeliveryMode(null);
			RequestContext.getCurrentInstance().execute("DeliveryNoData.show();");
			return;
		} else if (lstofDelivery.size() == 1) {
			setBooRenderDeliveryModeInputPanel(true);
			setBooRenderDeliveryModeDDPanel(false);
			setDeliveryModeInput(lstofDelivery.get(0).getPopulateName());
			setDeliveryMode(lstofDelivery.get(0).getPopulateId());
		} else {
			setDeliveryModeInput(null);
			setBooRenderDeliveryModeInputPanel(false);
			setBooRenderDeliveryModeDDPanel(true);
			setLstofDelivery(lstofDelivery);
		}
	}

	// to get routing branch by routing country and routing bank
	public void getRoutingbranch() {
		try {
			setVisible(false);
			lstBankbranch.clear();
			lstofRoutingBranches.clear();
			routingbranchvalues = generalService.getAllSpecificList(getRoutingCountry(), getDatabenificarycurrency(), getDataserviceid(), getRoutingBank());
			if (routingbranchvalues.size() > 0) {
				for (RoutingHeader routingHeader : routingbranchvalues) {
					setBranchApplicabilty(routingHeader.getBranchApplicability());
				}
				if (getBranchApplicabilty().equals(Constants.ALL)) {
					lstforAll = iPersonalRemittanceService.getALLListBankBranch(getRoutingCountry(), getRoutingBank());
					if (lstforAll.size() > 0) {
						for (BankBranch bankBranch : lstforAll) {
							PersonalRemittanceRoutingBankBranches alllist = new PersonalRemittanceRoutingBankBranches();
							alllist.setBankbranchid(bankBranch.getBankBranchId());
							alllist.setBankbranchName(bankBranch.getBranchShortName());
							lstofRoutingBranches.add(alllist);
						}
					}
				} else {
					lstforSpecific = iPersonalRemittanceService.getSpecificListBankBranch(getRoutingCountry(), getRoutingBank());
					if (lstforSpecific.size() > 0) {
						for (RoutingDetails routingDetails : lstforSpecific) {
							PersonalRemittanceRoutingBankBranches alllist = new PersonalRemittanceRoutingBankBranches();
							alllist.setBankbranchid(routingDetails.getExBankBranchId().getBankBranchId());
							alllist.setBankbranchName(routingDetails.getExBankBranchId().getBranchShortName());
							lstofRoutingBranches.add(alllist);
						}
					}
				}
			}
			if (lstofRoutingBranches.size() == 0) {
				setBooSingleRoutingBranch(true);
				setBooMultipleRoutingBranch(false);
				setRoutingBranchName(null);
				setRoutingBranch(null);
			} else if (lstofRoutingBranches.size() == 1) {
				setBooSingleRoutingBranch(true);
				setBooMultipleRoutingBranch(false);
				setRoutingBranchName(lstofRoutingBranches.get(0).getBankbranchName());
				setRoutingBranch(lstofRoutingBranches.get(0).getBankbranchid());
				getRoutingbranchName();
			} else {
				setRoutingBranchName(null);
				setBooSingleRoutingBranch(false);
				setBooMultipleRoutingBranch(true);
			}
			specialRequestFcAmountCalculation();
		} catch (Exception e) {
			setErrmsg("Exception occurred "+e);
			setVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public String getRoutingBankBranchName() {
		return routingBankBranchName;
	}

	public void setRoutingBankBranchName(String routingBankBranchName) {
		this.routingBankBranchName = routingBankBranchName;
	}

	// to get routing bank branch name
	public void getRoutingbranchName() {
		for (PersonalRemittanceRoutingBankBranches branchname : lstofRoutingBranches) {
			if (branchname.getBankbranchid().compareTo(getRoutingBranch()) == 0) {
				setRoutingBankBranchName(branchname.getBankbranchName());
			}
		}
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

	// to call procedure and get values
	public void getExchangeRatevalues() {
		

		try {

			setBooRenderPoll(false);

			HashMap<String, String> inputValues = new HashMap<String, String>();

			inputValues.put("P_APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId() == null ? "0" : sessionStateManage.getCountryId().toString());
			inputValues.put("P_ROUTING_COUNTRY_ID", getRoutingCountry() == null ? "0" : getRoutingCountry().toString());
			inputValues.put("P_BRANCH_ID", sessionStateManage.getBranchId());
			inputValues.put("P_COMPANY_ID", sessionStateManage.getCompanyId() == null ? "0" : sessionStateManage.getCompanyId().toString());
			inputValues.put("P_ROUTING_BANK_ID", getRoutingBank() == null ? "0" : getRoutingBank().toString());
			//inputValues.put("P_SERVICE_MASTER_ID", getDataserviceid() == null ? "0" : getDataserviceid().toString());
			inputValues.put("P_SERVICE_MASTER_ID","101");
			inputValues.put("P_DELIVERY_MODE_ID", getDeliveryMode()==null ? "0" : getDeliveryMode().toString());
			inputValues.put("P_REMITTANCE_MODE_ID", getRemitMode()==null ? "0" : getRemitMode().toString());
			inputValues.put("P_FOREIGN_CURRENCY_ID", getForiegnCurrency() == null ? "0" : getForiegnCurrency().toString());
			inputValues.put("P_SELECTED_CURRENCY_ID", getCurrency() == null ? "0" : getCurrency().toString());
			inputValues.put("P_CUSTOMER_ID", getCustomerNo() == null ? "0" : getCustomerNo().toString());
			inputValues.put("P_CUSTOMER_TYPE", getCustomerType());
			inputValues.put("P_LOYALTY_POINTS_IND", getAvailLoyaltyPoints());

			// special Deal Ind Changed to special Deal Rate
			inputValues.put("P_SPECIAL_DEAL_RATE",getSpecialDealRate() == null ? "0" : getSpecialDealRate().toString());
			inputValues.put("P_OVERSEAS_CHRG_IND", getChargesOverseas());
			inputValues.put("P_SELECTED_CURRENCY_AMOUNT", getAmountToRemit() == null ? "0": getAmountToRemit().toString());
			// spot Rate Ind Changed to spot Exchange Rate
			inputValues.put("P_SPOT_RATE", getSpotExchangeRate() == null ? "0": getSpotExchangeRate().toString());
			inputValues.put("P_CASH_ROUND_IND", getCashRounding());
			inputValues.put("P_ROUTING_BANK_BRANCH_ID", getRoutingBranch() == null ? "0" : getRoutingBranch().toString());
			inputValues.put("P_BENE_ID", getMasterId() == null ? "0" : getMasterId().toString());
			inputValues.put("P_BENE_COUNTRY_ID", getDatabenificarycountry() == null ? "0" : getDatabenificarycountry().toString());
			inputValues.put("P_BENE_BANK_ID", getBeneficaryBankId() == null ? "0" : getBeneficaryBankId().toString());
			inputValues.put("P_BENE_BANK_BRANCH_ID",getBeneficaryBankBranchId() == null ? "0" : getBeneficaryBankBranchId().toString());
			inputValues.put("P_BENE_ACCOUNT_NO",getDataAccountnum());
			
			//Added by Rabil on 09/03/2016
			inputValues.put("P_APPROVAL_YEAR",getApprovalYear()==null?"0":getApprovalYear().toString());
			inputValues.put("P_APPROVAL_NO",getApprovalNo()==null?"0":getApprovalNo().toString());
			

			HashMap<String, String> outputValues = iPersonalRemittanceService.getExchangeRateValues(inputValues);

			if (outputValues.size() > 0) {

				if (outputValues.get("P_ERROR_MESSAGE") != null && !outputValues.get("P_ERROR_MESSAGE").equalsIgnoreCase("")) {

					setProcedureError("EX_GET_EXCHANGE_RATE" + " : " +outputValues.get("P_ERROR_MESSAGE"));
					RequestContext.getCurrentInstance().execute("procedureErr.show();");
					return;

				} else {

					setExchangeRate(new BigDecimal(outputValues.get("P_EXCHANGE_RATE_APPLIED")));
					setOverseasamt(GetRound.roundBigDecimal(new BigDecimal(outputValues.get("P_LOCAL_CHARGE_AMOUNT")),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					setCommission(GetRound.roundBigDecimal(new BigDecimal(outputValues.get("P_LOCAL_COMMISION_AMOUNT")),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					setGrossAmountCalculated(GetRound.roundBigDecimal(new BigDecimal(outputValues.get("P_LOCAL_GROSS_AMOUNT")),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					setLoyaltyAmountAvailed(GetRound.roundBigDecimal(new BigDecimal(outputValues.get("P_LOYALTY_AMOUNT")),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					setNetAmountPayable(GetRound.roundBigDecimal(new BigDecimal(outputValues.get("P_LOCAL_NET_PAYABLE")),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					setNetAmountSent(GetRound.roundBigDecimal(new BigDecimal(outputValues.get("P_LOCAL_NET_SENT")),
							foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getForiegnCurrency())));


					// Remittance Id and Delivery Id Changes
					if (getRemitMode().compareTo(new BigDecimal(outputValues.get("P_NEW_REMITTANCE_MODE_ID"))) != 0) {
						setNewRemittanceModeId(getRemitMode());
						setNewRemittanceModeName(getRemittanceName());
						setRemitMode(new BigDecimal(outputValues.get("P_NEW_REMITTANCE_MODE_ID")));
						if (new BigDecimal(outputValues.get("P_NEW_REMITTANCE_MODE_ID")).compareTo(BigDecimal.ZERO) != 0) {
							String remitName = iRemitModeMaster.getRemittanceDesc(getRemitMode(),
									new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"));
							if (remitName != null) {
								setBooRenderRemit(true);
								setRemittanceName(remitName);
							} else {
								setRemittanceName(null);
								RequestContext.getCurrentInstance().execute("remittanceNoData.show();");
								return;
							}
						}
					} else {
						setBooRenderRemit(false);
					}

					if (getDeliveryMode().compareTo(new BigDecimal(outputValues.get("P_NEW_DELIVERY_MODE_ID"))) != 0) {

						setNewDeliveryModeId(getDeliveryMode());
						setNewDeliveryModeName(getDeliveryModeInput());
						setDeliveryMode(new BigDecimal(outputValues.get("P_NEW_DELIVERY_MODE_ID")));
						if (new BigDecimal(outputValues.get("P_NEW_DELIVERY_MODE_ID")).compareTo(BigDecimal.ZERO) != 0) {
							String deliveryName = iDeliveryModeMaster.getDeliveryDesc(getDeliveryMode(),
									new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"));
							if (deliveryName != null) {
								setBooRenderDelivery(true);
								setDeliveryModeInput(deliveryName);
							} else {
								setDeliveryModeInput(null);
								RequestContext.getCurrentInstance().execute("DeliveryNoData.show();");
								return;
							}
						}
					} else {
						setBooRenderDelivery(false);
					}



					String additionlMsg = getAdditionalCheckProcedure();

					//setDatabenificarybankname(null);
					//setDatabenificarybranchname(null);
					setBeneStateId(null);
					setBeneDistrictId(null);
					setBeneCityId(null);
					setPbeneFullName(null);
					setPbeneFirstName(null);
					setPbeneSecondName(null);
					setPbeneThirdName(null);
					setPbeneFourthName(null);
					setPbeneFifthName(null);

					if (additionlMsg != null) {
						setExceptionMessage(additionlMsg);
						RequestContext.getCurrentInstance().execute("sqlexception.show();");
					} else {

						HashMap<String, String> lstofAdditionalBeneDetails = getAdditionalBeneDetails();

						if (lstofAdditionalBeneDetails != null && lstofAdditionalBeneDetails.size() != 0) {

							if (lstofAdditionalBeneDetails.get("P_ERROR_MESSAGE") != null) {
								setExceptionMessage("EX_GET_ADDL_BENE_DETAILS" + " : " +lstofAdditionalBeneDetails.get("P_ERROR_MESSAGE"));
								RequestContext.getCurrentInstance().execute("sqlexception.show();");
							} else {

								System.out.println(lstofAdditionalBeneDetails.toString());

								if (lstofAdditionalBeneDetails.get("P_BENE_BANK_NAME") != null) {
									setDatabenificarybankname(lstofAdditionalBeneDetails.get("P_BENE_BANK_NAME"));
								}else{
									setDatabenificarybankname(null);
								}

								if (lstofAdditionalBeneDetails.get("P_BENE_BRANCH_NAME") != null) {
									setDatabenificarybranchname(lstofAdditionalBeneDetails.get("P_BENE_BRANCH_NAME"));
								}else{
									setDatabenificarybranchname(null);
								}

								if (!lstofAdditionalBeneDetails.get("P_BENE_STATE_ID").equalsIgnoreCase("0")) {
									setBeneStateId(new BigDecimal(lstofAdditionalBeneDetails.get("P_BENE_STATE_ID")));
								}else{
									setBeneStateId(null);
								}

								if (!lstofAdditionalBeneDetails.get("P_BENE_DISTRICT_ID").equalsIgnoreCase("0")) {
									setBeneDistrictId(new BigDecimal(lstofAdditionalBeneDetails.get("P_BENE_DISTRICT_ID")));
								}else{
									setBeneDistrictId(null);
								}

								if (!lstofAdditionalBeneDetails.get("P_BENE_CITY_ID").equalsIgnoreCase("0")) {
									setBeneCityId(new BigDecimal(lstofAdditionalBeneDetails.get("P_BENE_CITY_ID")));
								}else{
									setBeneCityId(null);
								}

								if (lstofAdditionalBeneDetails.get("P_BENE_NAME") != null) {
									setPbeneFullName(lstofAdditionalBeneDetails.get("P_BENE_NAME"));
								}else{
									setPbeneFullName(null);
								}

								if (lstofAdditionalBeneDetails.get("P_BENEFICIARY_FIRST_NAME") != null) {
									setPbeneFirstName(lstofAdditionalBeneDetails.get("P_BENEFICIARY_FIRST_NAME"));
								}else{
									setPbeneFirstName(null);
								}

								if (lstofAdditionalBeneDetails.get("P_BENEFICIARY_SECOND_NAME") != null) {
									setPbeneSecondName(lstofAdditionalBeneDetails.get("P_BENEFICIARY_SECOND_NAME"));
								}else{
									setPbeneSecondName(null);
								}

								if (lstofAdditionalBeneDetails.get("P_BENEFICIARY_THIRD_NAME") != null) {
									setPbeneThirdName(lstofAdditionalBeneDetails.get("P_BENEFICIARY_THIRD_NAME"));
								}else{
									setPbeneThirdName(null);
								}

								if (lstofAdditionalBeneDetails.get("P_BENEFICIARY_FOURTH_NAME") != null) {
									setPbeneFourthName(lstofAdditionalBeneDetails.get("P_BENEFICIARY_FOURTH_NAME"));
								}else{
									setPbeneFourthName(null);
								}

								if (lstofAdditionalBeneDetails.get("P_BENEFICIARY_FIFTH_NAME") != null) {
									setPbeneFifthName(lstofAdditionalBeneDetails.get("P_BENEFICIARY_FIFTH_NAME"));
								}else{
									setPbeneFifthName(null);
								}

								// to render Instrn , SwiftBank1 , SwiftBank2 conditions
								checkingInstrnSiftBanksRequired();

								if(isBooRenderSwiftBank1() || isBooRenderSwiftBank2()){
									// to fetch swift master list
									fetchingAllSwiftMaster();
								}

								// to fetch source of income list
								getSourceofIncomeDetails();
								// rendering panel to proceed
								getServiceSecondPanel();

								// dialogue box to populate if remittance and delivery while procedure changed
								/*if (isBooRenderDelivery() == true || isBooRenderRemit() == true) {
									RequestContext.getCurrentInstance().execute("remitDeliveryModifiedRecords.show();");
								}*/
							}

						}

					}

				}

			} else {
				getServiceFirstPanel();
			}

		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			setAmountToRemit(null);
			specialRequestFcAmountCalculation();
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}
	
		
		
		
		/*
		List<BigDecimal> outvalues = new ArrayList<BigDecimal>();
		try {
			outvalues = iPersonalRemittanceService.getExchangeRateAllValues(sessionmanage.getCountryId(), getRoutingCountry(), new BigDecimal(sessionmanage.getBranchId()), sessionmanage.getCompanyId(), getRoutingBank(), getDataserviceid(), getDeliveryMode(), getRemitMode(), getForiegnCurrency(),
					getCurrency(), getCustomerNo(), getCustomerType(), getAvailLoyaltyPoints(), getSpecialRateRef(), getChargesOverseas(), getAmountToRemit(), getSpotRate(), getCashRounding());
		} catch (AMGException e) {
			// Added by kani begin
			CollectionUtil collUtil = new CollectionUtil();
			setExceptionMessage(collUtil.formatErrorMessage(e.getMessage()));
			System.out.println("*******Error message ********" + getExceptionMessage());
			// Added by kani end
			setAmountToRemit(null);
			specialRequestFcAmountCalculation();
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}
		System.out.println("##############" + outvalues.size());
		if (outvalues.size() > 0) {
			setExchangeRate((outvalues.get(0)) == null ? new BigDecimal(0) : (outvalues.get(0)));
			setOverseasamt((outvalues.get(1)) == null ? new BigDecimal(0) : (outvalues.get(1)));
			setCommission((outvalues.get(2)) == null ? new BigDecimal(0) : (outvalues.get(2)));
			setGrossAmountCalculated((outvalues.get(3)) == null ? new BigDecimal(0) : (outvalues.get(3)));
			setLoyaltyAmountAvailed((outvalues.get(4)) == null ? new BigDecimal(0) : (outvalues.get(4)));
			setNetAmountPayable((outvalues.get(5)) == null ? new BigDecimal(0) : (outvalues.get(5)));
			setNetAmountSent(GetRound.roundBigDecimal(outvalues.get(6), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(getForiegnCurrency())));
			getSourceofIncomeDetails();
			getServiceSecondPanel();
		} else {
			getServiceFirstPanel();
		}
	*/}

	/*public void getSourceofIncomeDetails() {
		lstSourceofIncome.clear();
		List<SourceOfIncomeDescription> lstSource = getForeignCurrencyPurchaseService().getSourceofIncome(sessionStateManage.getLanguageId());
		if (lstSource.size() != 0) {
			lstSourceofIncome.addAll(lstSource);
		}
	}*/

	public String getDocumentserialityno() {
		return documentserialityno;
	}

	public void setDocumentserialityno(String documentserialityno) {
		this.documentserialityno = documentserialityno;
	}

	public BigDecimal getFinaceYearId() {
		try {
			financialYearList = foreignCurrencyPurchase.getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null)
				finaceYearId = financialYearList.get(0).getFinancialYearID();
			setFinaceYearId(finaceYearId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finaceYearId;
	}

	
	public BigDecimal getFinaceYear() {
		try {
			List<UserFinancialYear> financialYearList = foreignCurrencyPurchaseService.getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null){
				finaceYear = financialYearList.get(0).getFinancialYear();
				setFinaceYear(finaceYear);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return finaceYear;
	}

	

	// to save in spot rate data base if spot rate is "Y"
	public void saveRemittanceInfoSpecialRate() {
		/** START 29/01/2015 */
		List<SpecialRateRequest> lstofspot = new ArrayList<SpecialRateRequest>();
		/** END 29/01/2015 */
		SpecialRateRequest specialReqRate = new SpecialRateRequest();
		/** START 29/01/2015 */
		lstofspot = iPersonalRemittanceService.getDocumentSeriality();
		/** END 29/01/2015 */
		specialReqRate.setApplicationCountryId(sessionmanage.getCountryId());
		// to set Customer Id
		Customer customer = new Customer();
		customer.setCustomerId(getCustomerNo());
		specialReqRate.setFsCustomer(customer);
		// to set Routing Bank Id
		BankMaster bankMaster = new BankMaster();
		bankMaster.setBankId(getRoutingBank());
		specialReqRate.setFsBankMaster(bankMaster);
		// to set currency Id
		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(getCurrency());
		specialReqRate.setFsCurrencyMaster(currencyMaster);
		// to set company Id
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionmanage.getCompanyId());
		specialReqRate.setCompanyMaster(companyMaster);
		// to set User Financial Year
		UserFinancialYear userFinancialYear = new UserFinancialYear();
		userFinancialYear.setFinancialYearID(generalService.getDealYear(new Date()).get(0).getFinancialYearID());
		specialReqRate.setFsFinanceYear(userFinancialYear);
		// to set Document code
		Document document = new Document();
		/** START 29/01/2015 */
		document.setDocumentID(generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FC_SALE_APP), new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1")).get(0).getDocumentID());
		/** END 29/01/2015 */
		specialReqRate.setFsDocument(document);
		specialReqRate.setDocumentNo(new BigDecimal(lstofspot.size() + 1));
		specialReqRate.setFcAmount(getAmountToRemit());
		specialReqRate.setCreatedBy(sessionmanage.getUserName());
		specialReqRate.setCreatedDate(new Date());
		specialReqRate.setIsActive(Constants.Yes);
		iPersonalRemittanceService.saveSpecialRateRequest(specialReqRate);
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> getPersonalRemittCalFCAmountDTList() {
		return personalRemittCalFCAmountDTList;
	}

	public void setPersonalRemittCalFCAmountDTList(CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> personalRemittCalFCAmountDTList) {
		this.personalRemittCalFCAmountDTList = personalRemittCalFCAmountDTList;
	}

	public Boolean getBooSpecialCusFCCalDataTable() {
		return booSpecialCusFCCalDataTable;
	}

	public void setBooSpecialCusFCCalDataTable(Boolean booSpecialCusFCCalDataTable) {
		this.booSpecialCusFCCalDataTable = booSpecialCusFCCalDataTable;
	}

	// To calculate Foreign Currency Amount
	public void specialRequestFcAmountCalculation() {
		personalRemittCalFCAmountDTList.clear();
		setRemitAmountSplCust(BigDecimal.ZERO);
		// for Bank list --- more time to fetch so blocked
		/*
		 * List<BankMaster> bankList =
		 * generalService.getAllBankListFromBankMaster(); for (BankMaster
		 * bankMaster : bankList) { banklistMap.put(bankMaster.getBankId(),
		 * bankMaster.getBankFullName()); }
		 */
		calculateForeignCurrencyAmount();
	}

	public void calculateForeignCurrencyAmount() {

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		BigDecimal fcAmount = BigDecimal.ZERO;
		if (getRoutingBank() != null && getRoutingCountry() != null) {
			getShoppingCartDetails(getCustomerNo());
			List<CustomerSpecialDealRequest> specialCustomerRequestFCamountList = iPersonalRemittanceService.getSpecialRequestFcAmountCalList(getCustomerNo(),sessionStateManage.getCountryId(), getRoutingBank(),getRoutingCountry());
			if (specialCustomerRequestFCamountList.size() > 0) {
				// to fetch parameter Percentage
				List<AuthicationLimitCheckView> authPercCheck = percentageSplRateofSplCustDeal();

				for (CustomerSpecialDealRequest cusSplDealreq : specialCustomerRequestFCamountList) {
					String fetchedDate = dateformat.format(cusSplDealreq.getValidUpto());
					Date dateFetched = null;
					try {
						dateFetched = dateformat.parse(fetchedDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					try {
						if (dateFetched.compareTo(dateformat.parse(dateformat.format(new Date()))) >= 0) {
							if (cusSplDealreq.getCustomerSpeacialDealReqCurrencyMaster().getCurrencyId().compareTo(getDatabenificarycurrency()) == 0) {
								fcAmount = BigDecimal.ZERO;
								fcAmount = cusSplDealreq.getForeignCurrencyAmount().subtract(cusSplDealreq.getUtilizedAmount());
								if (fcAmount.compareTo(BigDecimal.ZERO) > 0) {
									setMarqueeRender(false);
									setNextRender(true);
									setBooSpecialCusFCCalDataTable(true);
									PersonalRemittanceCalFCAmountDataTable personalRemittanceCalFCAmountDT = new PersonalRemittanceCalFCAmountDataTable();
									personalRemittanceCalFCAmountDT.setBankId(cusSplDealreq.getCustomerSpeacialDealReqBankMaster().getBankId());
									personalRemittanceCalFCAmountDT.setBankName(generalService.getBankName(cusSplDealreq.getCustomerSpeacialDealReqBankMaster().getBankId()));
									personalRemittanceCalFCAmountDT.setCustomerName(getCustomerName());
									personalRemittanceCalFCAmountDT.setRate(cusSplDealreq.getSellRate());
									// BigDecimal fcAmount = cusSplDealreq.getForeignCurrencyAmount().subtract(iPersonalRemittanceService.getUtilizedAmount(getCustomerNo(),sessionmanage.getCountryId(),getRoutingBank(),getRoutingCountry()));
									for (ShoppingCartDataTableBean lstShoppingData : shoppingcartDTList) {
										if ((lstShoppingData.getSpldeal() == null ? Constants.No : lstShoppingData.getSpldeal()).equalsIgnoreCase(Constants.Yes)) {
											if(lstShoppingData.getSpldeal().equalsIgnoreCase(Constants.Yes)){
												List<CustomerSpecialDealAppl> specialCustomerAppl = iPersonalRemittanceService.fetchAllCustSplDealByApplTrnxDoc(lstShoppingData.getDocumentNo());
												if(specialCustomerAppl.size()!=0){
													for (CustomerSpecialDealAppl customerSpecialDealAppl : specialCustomerAppl) {
														if ((customerSpecialDealAppl.getCompanyMasterId().compareTo(cusSplDealreq.getCustomerSpeacialDealReqCompanyMaster().getCompanyId()) == 0) 
																&& (customerSpecialDealAppl.getDocumentId().compareTo(cusSplDealreq.getCustomerSpeacialDealReqDocument().getDocumentID()) == 0)
																&& (customerSpecialDealAppl.getDocumentNumber().compareTo(cusSplDealreq.getDocumentNumber()) == 0) 
																&& (customerSpecialDealAppl.getFinancialYearId().compareTo(cusSplDealreq.getDocumentFinancialYear().getFinancialYear()) == 0)) {
															fcAmount = fcAmount.subtract(customerSpecialDealAppl.getForiegnCurrencyAmount());
														}
													}
												}

											}

										}
									}
									personalRemittanceCalFCAmountDT.setCurrencyId(cusSplDealreq.getCustomerSpeacialDealReqCurrencyMaster().getCurrencyId());
									personalRemittanceCalFCAmountDT.setAmount(fcAmount);
									personalRemittanceCalFCAmountDT.setUtilizedAmount(cusSplDealreq.getUtilizedAmount());
									personalRemittanceCalFCAmountDT.setValideUpto(cusSplDealreq.getValidUpto());
									personalRemittanceCalFCAmountDT.setSpecialCustomerPrimaryKey(cusSplDealreq.getCustomerSpecialDealReqId());
									personalRemittanceCalFCAmountDT.setSplcompanyID(cusSplDealreq.getCustomerSpeacialDealReqCompanyMaster().getCompanyId());
									personalRemittanceCalFCAmountDT.setSpldocnumID(cusSplDealreq.getDocumentNumber());
									personalRemittanceCalFCAmountDT.setSpldocumentID(cusSplDealreq.getCustomerSpeacialDealReqDocument().getDocumentID());
									personalRemittanceCalFCAmountDT.setSplfinyearID(cusSplDealreq.getDocumentFinancialYear().getFinancialYear());
									personalRemittanceCalFCAmountDT.setSelect(false);
									personalRemittanceCalFCAmountDT.setPercentageAddedAmount(BigDecimal.ZERO);

									if(authPercCheck != null && authPercCheck.size() != 0){
										AuthicationLimitCheckView lstParaAUTHPerc = authPercCheck.get(0);
										BigDecimal percentageSplDealRate = lstParaAUTHPerc.getAuthPercentage();
										String errorMsg = lstParaAUTHPerc.getAuthMessage();
										BigDecimal percentageParameterAmount = percentageSplDealRate.multiply(fcAmount); // percentage of Auth * fc_Amount
										personalRemittanceCalFCAmountDT.setPercentageParameterAmount(fcAmount.add(percentageParameterAmount)); // percentage amount + fc_Amount
										personalRemittanceCalFCAmountDT.setPercentageParameterErrorMsg(errorMsg);
									}else{
										personalRemittanceCalFCAmountDT.setPercentageParameterAmount(fcAmount); // percentage amount + fc_Amount
										personalRemittanceCalFCAmountDT.setPercentageParameterErrorMsg("");
									}

									personalRemittanceCalFCAmountDT.setBooRenderInputFCAmount(true);
									personalRemittCalFCAmountDTList.add(personalRemittanceCalFCAmountDT);
								} else {
									setBooSpecialCusFCCalDataTable(false);
									setSpecialRateRef(Constants.No);
									setDisableSpotRatePanel(false);
								}
							} else {
								setBooSpecialCusFCCalDataTable(false);
								setSpecialRateRef(Constants.No);
								setDisableSpotRatePanel(false);
							}
						} else {
							setBooSpecialCusFCCalDataTable(false);
							setSpecialRateRef(Constants.No);
							setDisableSpotRatePanel(false);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			} else {
				setBooSpecialCusFCCalDataTable(false);
				setSpecialRateRef(Constants.No);
				setDisableSpotRatePanel(false);
			}
		} else {
			//clearData();
		}
	}

	// percentage Amount of spl rate
	public List<AuthicationLimitCheckView> percentageSplRateofSplCustDeal(){
		List<AuthicationLimitCheckView> percentageSplRate = new ArrayList<AuthicationLimitCheckView>();
		try{
			//String authorization_Type = "98"; Constants.Percentage_authorization_Type = 98 SPL Rate
			percentageSplRate = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.Percentage_authorization_Type);
			return percentageSplRate;
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		return percentageSplRate;
	}

	/*
	 * private BigDecimal utilizedAmount;
	 * 
	 * public BigDecimal getUtilizedAmount() { return utilizedAmount; }
	 * 
	 * public void setUtilizedAmount(BigDecimal utilizedAmount) {
	 * this.utilizedAmount = utilizedAmount; }
	 */
	/*
	 * public void
	 * calculateRemittanceAmount(PersonalRemittanceCalFCAmountDataTable
	 * perRemitCalObj){ setSpecialRateRef("Y");
	 * //setUtilizedAmount(getAmountToRemit
	 * ().add(perRemitCalObj.getUtilizedAmount())); //
	 * iPersonalRemittanceService
	 * .calculateRemittanceAmount(perRemitCalObj.getSpecialCustomerPrimaryKey
	 * (),utilizedAmount); }
	 */
	public void checkAvailableBalance(PersonalRemittanceCalFCAmountDataTable dataTableObj) {
		if (getAmountToRemit().intValue() != 0) {
			Date todayDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String sysDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			/* if(personalRemittCalFCAmountDTList.size()>0){ */
			/*
			 * for(PersonalRemittanceCalFCAmountDataTable
			 * dataTableObj:getPersonalRemittCalFCAmountDTList()){
			 */
			Date valideUpto = new Date(dataTableObj.getValideUpto().getTime());
			String strValideUpto = new SimpleDateFormat("dd/MM/yyyy").format(dataTableObj.getValideUpto());
			Date date1 = null;
			try {
				date1 = sdf.parse(sysDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date date2 = null;
			try {
				date2 = sdf.parse(strValideUpto);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			/* if(dataTableObj.getSelect()!=null){ */
			if (date1.compareTo(date2) < 0 || date1.compareTo(date2) == 0) {
				if (dataTableObj.getAmount().intValue() == 0 || dataTableObj.getAmount().intValue() < (getAmountToRemit().intValue())) {
					RequestContext.getCurrentInstance().execute("balanceCheck.show();");
					setAmountToRemit(null);
					specialRequestFcAmountCalculation();
				} else {
					// blocked and used at last in AML Save condition -
					// calculateRemittanceAmount(dataTableObj);
					// calculateRemittanceAmount(dataTableObj);
					// calculateForeignCurrencyAmount();
					// setSpecialRateRef("Y");
					gotoNextPanel();
				}
			} else {
				RequestContext.getCurrentInstance().execute("expDate.show();");
				setAmountToRemit(null);
				specialRequestFcAmountCalculation();
			}
			/*
			 * }else{
			 * RequestContext.getCurrentInstance().execute("fcSelect.show();");
			 * setAmountToRemit(null); }
			 */
			// }
			// }
		}
	}

	// rahamathali code end
	List<AddDynamicLebel> listDynamicLebel = new ArrayList<AddDynamicLebel>();

	public List<AddDynamicLebel> getListDynamicLebel() {
		return listDynamicLebel;
	}

	public void setListDynamicLebel(List<AddDynamicLebel> listDynamicLebel) {
		this.listDynamicLebel = listDynamicLebel;
	}

	/*public void saveAdditionalInstnData(RemittanceApplication remittanceApplication) {
		System.out.println("========================CALEEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD+++++++++++++++++++++=");
		Document document = new Document();
		document.setDocumentID(generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FC_SALE_APP), new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1")).get(0).getDocumentID());
		// company Id
		CompanyMaster companymaster = new CompanyMaster();
		companymaster.setCompanyId(sessionmanage.getCompanyId());
		// Application Country
		CountryMaster countrymaster = new CountryMaster();
		countrymaster.setCountryId(sessionmanage.getCountryId());
		for (AddAdditionalBankData dynamicList : listAdditionalBankDataTable) {
			AdditionalInstructionData additionalInsData = new AdditionalInstructionData();
			AdditionalBankRuleMap additionalBank = new AdditionalBankRuleMap();
			additionalBank.setAdditionalBankRuleId(dynamicList.getAdditionalBankRuleFiledId());
			additionalInsData.setFlexField(dynamicList.getFlexiField());
			additionalInsData.setFlexFieldValue(dynamicList.getVariableName());
			additionalInsData.setFlexField(dynamicList.getAmicCode());
			additionalInsData.setFlexFieldValue(dynamicList.getAmicDesc());
			additionalInsData.setExDocument(document);
			additionalInsData.setFsCountryMaster(countrymaster);
			additionalInsData.setFsCompanyMaster(companymaster);
			additionalInsData.setExRemittanceApplication(remittanceApplication);
			additionalInsData.setExUserFinancialYear(remittanceApplication.getExUserFinancialYearByDocumentFinanceYear());
			additionalInsData.setDocumentFinanceYear(remittanceApplication.getDocumentFinancialyear());
			additionalInsData.setAdditionalBankFieldsId(additionalBank);
			additionalInsData.setCreatedBy(sessionmanage.getUserName());
			additionalInsData.setCreatedDate(new Date());
			additionalInsData.setIsactive(Constants.Yes);
			additionalInsData.setDocumentNo(new BigDecimal(getDocumentNo()));
			iPersonalRemittanceService.saveAdditionalInsData(additionalInsData);
		}
	}*/

	List<AddAdditionalBankData> listAdditionalBankDataTable = new ArrayList<AddAdditionalBankData>();
	List<AddAdditionalBankData> listAdditionalBankDataTableForSize = new ArrayList<AddAdditionalBankData>();

	public List<AddAdditionalBankData> getListAdditionalBankDataTable() {
		return listAdditionalBankDataTable;
	}

	public void setListAdditionalBankDataTable(List<AddAdditionalBankData> listAdditionalBankDataTable) {
		this.listAdditionalBankDataTable = listAdditionalBankDataTable;
	}

	public void dynamicLevel() throws AMGException{/*
		listDynamicLebel.clear();
		//List<ServiceApplicabilityRule> serviceAppRuleList = iPersonalRemittanceService.getDynamicLevel(sessionmanage.getCountryId(), getCurrency(), getRemitMode(), getDeliveryMode());

		List<ServiceApplicabilityRule> serviceAppRuleList = iPersonalRemittanceService.getDynamicLevel(sessionmanage.getCountryId(), getRoutingCountry(), getCurrency(), getRemitMode(), getDeliveryMode());
		if(serviceAppRuleList.size()>0){
			for (ServiceApplicabilityRule serviceRule : serviceAppRuleList) {
				AddDynamicLebel addDynamic = new AddDynamicLebel();
				addDynamic.setLebelId(serviceRule.getServiceApplicabilityRuleId());
				addDynamic.setFieldLength(serviceRule.getFieldLength());
				if (serviceRule.getMandatory().equalsIgnoreCase(Constants.Yes)) {
					addDynamic.setLebelDesc(serviceRule.getFieldName());
					addDynamic.setFlexiField(serviceRule.getFieldDesc());
					addDynamic.setValidation(serviceRule.getValidate());
					if (serviceRule.getValidate().equalsIgnoreCase(Constants.Yes)) {
						addDynamic.setMinLenght(serviceRule.getMinLenght());
						addDynamic.setMaxLenght(serviceRule.getMaxLenght());
						addDynamic.setMandatory("*");
						addDynamic.setRequired(true);
					}
					listDynamicLebel.add(addDynamic);
				}

			}
		}
	*/

		listDynamicLebel.clear();
		setExceptionMessage("");
		List<AdditionalDataDisplayView> serviceAppRuleList = iPersonalRemittanceService.getAdditionalDataFromServiceApplicability(sessionStateManage.getCountryId(),
				getRoutingCountry(), getForiegnCurrency(), getRemitMode(),getDeliveryMode());
		if (serviceAppRuleList.size() > 0) {
			for (AdditionalDataDisplayView serviceRule : serviceAppRuleList) {
				AddDynamicLebel addDynamic = new AddDynamicLebel();
				addDynamic.setLebelId(serviceRule.getServiceApplicabilityRuleId());
				addDynamic.setFieldLength(serviceRule.getFieldLength());
				if(serviceRule.getFieldDescription()!=null){
					addDynamic.setLebelDesc(serviceRule.getFieldDescription());
				}

				addDynamic.setFlexiField(serviceRule.getFlexField());
				addDynamic.setValidation(serviceRule.getValidationsReq());
				addDynamic.setNavicable(serviceRule.getIsRendered());
				addDynamic.setMinLenght(serviceRule.getMinLength());
				addDynamic.setMaxLenght(serviceRule.getMaxLength());

				if(serviceRule.getIsRequired()!=null && serviceRule.getIsRequired().equalsIgnoreCase(Constants.Yes)){
					addDynamic.setMandatory("*");
					addDynamic.setRequired(true);
				}

				listDynamicLebel.add(addDynamic);
			}

			setAdditionalCheck(true);

		}else{
			setAdditionalCheck(false);
		}
		
	
	}

	public void matchData() {/*
		listAdditionalBankDataTable.clear();
		try {
			for (AddDynamicLebel dyamicLabel : listDynamicLebel) {
				AddAdditionalBankData adddata = new AddAdditionalBankData();
				if(dyamicLabel.getValidation()!=null && dyamicLabel.getValidation().equalsIgnoreCase(Constants.Yes)){
					List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService
							.getDynamicLevelMatch(getRoutingCountry(),
									dyamicLabel.getFlexiField());
					if (listAdditinalBankfield.size() > 0) {
						for (AdditionalBankRuleMap listAdd : listAdditinalBankfield) {

							//adddata.setAdditionalBankRuleData(additionalBankRuleData);
							List<AdditionalBankDetailsView> listAdditionaView = iPersonalRemittanceService.getAmiecDetails(getForiegnCurrency(),getRoutingBank(), getRemitMode(),
									getDeliveryMode(),getRoutingCountry(),listAdd.getFlexField());

							if(listAdditionaView.size()>0){

								adddata.setAdditionalBankRuleFiledId(listAdd
										.getAdditionalBankRuleId());
								adddata.setFlexiField(listAdd.getFlexField());
								adddata.setAdditionalDesc(listAdd.getFieldName());

								adddata.setRenderInputText(false);
								adddata.setRenderSelect(true);
								adddata.setRenderOneSelect(false);
								adddata.setListadditionAmiecData(listAdditionaView);

							}
						}
					}
				}else{
					if (dyamicLabel.getValidation()!=null && dyamicLabel.getValidation().equalsIgnoreCase(
								Constants.No)) {
					adddata.setMandatory(dyamicLabel.getMandatory());
					if (dyamicLabel.getMinLenght() != null) {
						adddata.setMinLenght(dyamicLabel.getMinLenght()
								.intValue());
					}else{
						adddata.setMinLenght(0);
					}
					if(dyamicLabel.getMaxLenght()!=null){
						adddata.setMaxLenght(dyamicLabel.getMaxLenght());
					}else{
						adddata.setMaxLenght(new BigDecimal(50));	
					}

					adddata.setFieldLength(dyamicLabel.getFieldLength());
					adddata.setRequired(dyamicLabel.getRequired());
					adddata.setRenderInputText(true);
					adddata.setRenderSelect(false);
					adddata.setRenderOneSelect(false);
					adddata.setFlexiField(dyamicLabel.getFlexiField());
					if(dyamicLabel.getLebelDesc()!=null){
						adddata.setAdditionalDesc(dyamicLabel.getLebelDesc());	
					}else{
						List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService.getDynamicLevelMatch(getRoutingCountry(),dyamicLabel.getFlexiField());
						if(listAdditinalBankfield.size()>0){
							adddata.setAdditionalDesc(listAdditinalBankfield.get(0).getFieldName());
						}

					}

				}
				listAdditionalBankDataTable.add(adddata);
			}

		} catch (Exception e) {
			log.info(e);
		}
	*/
		setExceptionMessage(null);
		listAdditionalBankDataTable.clear();
		try {
			for (AddDynamicLebel dyamicLabel : listDynamicLebel) {
				AddAdditionalBankData adddata = new AddAdditionalBankData();
				if(dyamicLabel.getValidation()!=null && dyamicLabel.getValidation().equalsIgnoreCase(Constants.Yes)){
					List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService.getDynamicLevelMatch(getRoutingCountry(),dyamicLabel.getFlexiField());
					if (listAdditinalBankfield.size() > 0) {
						for (AdditionalBankRuleMap listAdd : listAdditinalBankfield) {

							List<AdditionalBankDetailsView> listAdditionaView = iPersonalRemittanceService.getAmiecDetails(getForiegnCurrency(),getRoutingBank(), getRemitMode(),
									getDeliveryMode(),getRoutingCountry(),listAdd.getFlexField());

							if(listAdditionaView.size()>0){

								//setting dynamic functionality
								adddata.setMandatory(dyamicLabel.getMandatory());
								if (dyamicLabel.getMinLenght() != null) {
									adddata.setMinLenght(dyamicLabel.getMinLenght().intValue());
								}else{
									adddata.setMinLenght(0);
								}
								if(dyamicLabel.getMaxLenght()!=null){
									adddata.setMaxLenght(dyamicLabel.getMaxLenght());
								}else{
									adddata.setMaxLenght(new BigDecimal(50));	
								}
								adddata.setFieldLength(dyamicLabel.getFieldLength());
								adddata.setRequired(dyamicLabel.getRequired());

								adddata.setAdditionalBankRuleFiledId(listAdd.getAdditionalBankRuleId());
								adddata.setFlexiField(listAdd.getFlexField());
								if(listAdd.getFieldName() != null){
									adddata.setAdditionalDesc(listAdd.getFieldName());
								}else{
									setExceptionMessage((getExceptionMessage().equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
								}
								adddata.setRenderInputText(false);
								adddata.setRenderSelect(true);
								adddata.setRenderOneSelect(false);
								adddata.setListadditionAmiecData(listAdditionaView);

							}
						}

						if(getExceptionMessage() != null && !getExceptionMessage().equalsIgnoreCase("")){
							setAdditionalCheck(false);
							setExceptionMessage(getExceptionMessage());
							RequestContext.getCurrentInstance().execute("dataexception.show();");
						}else{
							setAdditionalCheck(true);
							setExceptionMessage(null);
						}
					}
				}else{
					/*if (dyamicLabel.getValidation()!=null && dyamicLabel.getValidation().equalsIgnoreCase(Constants.No)) {*/
					adddata.setMandatory(dyamicLabel.getMandatory());
					if (dyamicLabel.getMinLenght() != null) {
						adddata.setMinLenght(dyamicLabel.getMinLenght().intValue());
					}else{
						adddata.setMinLenght(0);
					}
					if(dyamicLabel.getMaxLenght()!=null){
						adddata.setMaxLenght(dyamicLabel.getMaxLenght());
					}else{
						adddata.setMaxLenght(new BigDecimal(50));	
					}

					adddata.setFieldLength(dyamicLabel.getFieldLength());
					adddata.setRequired(dyamicLabel.getRequired());
					adddata.setRenderInputText(true);
					adddata.setRenderSelect(false);
					adddata.setRenderOneSelect(false);
					adddata.setFlexiField(dyamicLabel.getFlexiField());
					if(dyamicLabel.getLebelDesc()!=null){
						adddata.setAdditionalDesc(dyamicLabel.getLebelDesc());
					}else{
						List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService.getDynamicLevelMatch(getRoutingCountry(),dyamicLabel.getFlexiField());
						if(listAdditinalBankfield.size()>0){
							if(listAdditinalBankfield.get(0).getFieldName() != null){
								adddata.setAdditionalDesc(listAdditinalBankfield.get(0).getFieldName());
							}else{
								setExceptionMessage((getExceptionMessage().equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
							}
						}else{
							setExceptionMessage((getExceptionMessage().equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
						}
					}

				}
				listAdditionalBankDataTable.add(adddata);
			}

			if(getExceptionMessage() != null && !getExceptionMessage().equalsIgnoreCase("")){
				setAdditionalCheck(false);
				setExceptionMessage(getExceptionMessage());
				RequestContext.getCurrentInstance().execute("dataexception.show();");
			}else{
				setAdditionalCheck(true);
				setExceptionMessage(null);
			}

		} catch (Exception e) {
			log.info(e);
		}
	}

	public int getMinLenght() {
		// listAdditionalBankDataTable.clear();
		for (AddAdditionalBankData listAdditionals : listAdditionalBankDataTable) {
			setMinLenght(listAdditionals.getMinLenght());
			break;
		}
		return minLenght;
	}

	public void setMinLenght(int minLenght) {
		this.minLenght = minLenght;
	}

	public BigDecimal getAdditionalbankFieldId() {
		return additionalbankFieldId;
	}

	public void setAdditionalbankFieldId(BigDecimal additionalbankFieldId) {
		this.additionalbankFieldId = additionalbankFieldId;
	}

	public Boolean getMarqueeRender() {
		return marqueeRender;
	}

	public void setMarqueeRender(Boolean marqueeRender) {
		this.marqueeRender = marqueeRender;
	}

	public Boolean getNextRender() {
		return nextRender;
	}

	public void setNextRender(Boolean nextRender) {
		this.nextRender = nextRender;
	}

	public ApllicationMailer1 getMailService1() {
		return mailService1;
	}

	public void setMailService1(ApllicationMailer1 mailService1) {
		this.mailService1 = mailService1;
	}

	public JavaMailSender getMailSender1() {
		return mailSender1;
	}

	public void setMailSender1(JavaMailSender mailSender1) {
		this.mailSender1 = mailSender1;
	}

	int count = 0;

	public void emailSend() {
		if (count == 0) {
			if (getSpotRate().equals(Constants.Yes) && getRemitMode() != null && getDeliveryMode() != null && getModeOfPayment() != null) {
				setMarqueeRender(true);
				setNextRender(false);
				setBooRenderPoll(true);
				saveRemittanceInfoSpecialRate();
				count = count + 1;
				String roleId1 = sessionStateManage.getRoleId();
				String sender = sessionStateManage.getUserName();
				String branchId = sessionStateManage.getBranchId();
				String employeeid = sessionStateManage.getEmployeeId().toString();
				String telephoneno = sessionStateManage.getTelephoneNumber();
				String email = sessionStateManage.getEmail();
				System.out.println(telephoneno);
				int roleId = Integer.parseInt(roleId1);
				List<Employee> empList = iEmployeeService.getEmployees();
				for (Employee emp : empList) {
					if (emp.getFsRoleMaster().getRoleId().intValue() == roleId && emp.getEmployeeId().toString().equals(employeeid)) {
						int countryBranchId = emp.getFsCountryBranch().getCountryBranchId().intValue();
						List<Employee> newlist = iEmployeeService.getEmployees();
						ListIterator<Employee> le = newlist.listIterator();
						while (le.hasNext()) {
							Employee e1 = le.next();
							if (e1.getFsCountryBranch().getCountryBranchId().intValue() == countryBranchId && e1.getFsRoleMaster().getRoleId().intValue() < roleId)
								if ((e1.getFsCountryBranch().getCountryBranchId().intValue() == countryBranchId) && (e1.getFsRoleMaster().getRoleId().intValue() == roleId - 1)) {
									System.out.println(e1.getFsRoleMaster().getRoleId());
									String employeename = e1.getEmployeeName();
									mailService1.sendMailToCustomer(email, e1.getEmail(), "welcome");
								}
						}
					}
				}
			} else {
				setMarqueeRender(false);
				setNextRender(true);
				setBooRenderPoll(false);
			}
		}/** NAG CODE 07/02/2015 **/
		else {
			// RequestContext.getCurrentInstance().execute("spotrate.show();");
			if (getSpotRate().equals(Constants.No)) {
				setMarqueeRender(false);
				setNextRender(true);
				setBooRenderPoll(false);
			} else {
				setMarqueeRender(true);
				setNextRender(false);
				setBooRenderPoll(true);
			}
		}
		/** NAG CODE END 07/02/2015 **/
	}

	public void refresh() {
		/** START 29/01/2015 */
		List<SpecialRateRequest> lstofspotRate = new ArrayList<SpecialRateRequest>();
		lstofspotRate = iPersonalRemittanceService.getDocumentSeriality();
		Boolean isUpdate = iSpecialRateRequest.isSpotRate(new BigDecimal(lstofspotRate.size()));
		/** END 29/01/2015 */
		System.out.println(isUpdate);
		if (isUpdate) {
			setNextRender(true);
			setMarqueeRender(false);
			setBooRenderPoll(false);
		} else {
			RequestContext.getCurrentInstance().execute("spotrate.show();");
		}
	}

	/** NAG CODE START 06/02/2015 **/
	public void updateSpotRateCustomer() {
		List<SpecialRateRequest> lstofSpotrate = new ArrayList<SpecialRateRequest>();
		lstofSpotrate = iPersonalRemittanceService.getDocumentSeriality();
		for (SpecialRateRequest spotRate : lstofSpotrate) {
			if (spotRate.getSellRate() == null && spotRate.getIsActive().equals(Constants.Yes)) {
				setSpotRate(Constants.No);
				spotRate.setSpecialRateRequestId(spotRate.getSpecialRateRequestId());
				spotRate.setApplicationCountryId(spotRate.getApplicationCountryId());
				// to set Customer Id
				Customer customer = new Customer();
				customer.setCustomerId(spotRate.getFsCustomer().getCustomerId());
				spotRate.setFsCustomer(customer);
				// to set Routing Bank Id
				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(spotRate.getFsBankMaster().getBankId());
				spotRate.setFsBankMaster(bankMaster);
				// to set currency Id
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(spotRate.getFsCurrencyMaster().getCurrencyId());
				spotRate.setFsCurrencyMaster(currencyMaster);
				// to set company Id
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(spotRate.getCompanyMaster().getCompanyId());
				spotRate.setCompanyMaster(companyMaster);
				// to set User Financial Year
				UserFinancialYear userFinancialYear = new UserFinancialYear();
				userFinancialYear.setFinancialYearID(spotRate.getFsFinanceYear().getFinancialYearID());
				spotRate.setFsFinanceYear(userFinancialYear);
				// to set Document code
				Document document = new Document();
				/** START 29/01/2015 */
				document.setDocumentID(spotRate.getFsDocument().getDocumentID());
				/** END 29/01/2015 */
				spotRate.setFsDocument(document);
				spotRate.setDocumentNo(spotRate.getDocumentNo());
				spotRate.setFcAmount(spotRate.getFcAmount());
				spotRate.setCreatedBy(spotRate.getCreatedBy());
				spotRate.setCreatedDate(spotRate.getCreatedDate());
				spotRate.setIsActive(Constants.No);
				setBooRenderPoll(false);
				iSpecialRateRequest.updateRecord(spotRate);
				setNextRender(true);
				setMarqueeRender(false);
				refresh();
			}
		}
	}

	public boolean isBooRenderPoll() {
		return booRenderPoll;
	}

	public void setBooRenderPoll(boolean booRenderPoll) {
		this.booRenderPoll = booRenderPoll;
	}

	public void pleaseWait() {
		List<SpecialRateRequest> lstofSpotrate = new ArrayList<SpecialRateRequest>();
		lstofSpotrate = iPersonalRemittanceService.getDocumentSeriality();
		for (SpecialRateRequest spotRate : lstofSpotrate) {
			// if(spotRate.getSellRate()==null&&spotRate.getIsActive().equals("Y")){
			spotRate.setSpecialRateRequestId(spotRate.getSpecialRateRequestId());
			spotRate.setApplicationCountryId(spotRate.getApplicationCountryId());
			// to set Customer Id
			Customer customer = new Customer();
			customer.setCustomerId(spotRate.getFsCustomer().getCustomerId());
			spotRate.setFsCustomer(customer);
			// to set Routing Bank Id
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(spotRate.getFsBankMaster().getBankId());
			spotRate.setFsBankMaster(bankMaster);
			// to set currency Id
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(spotRate.getFsCurrencyMaster().getCurrencyId());
			spotRate.setFsCurrencyMaster(currencyMaster);
			// to set company Id
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(spotRate.getCompanyMaster().getCompanyId());
			spotRate.setCompanyMaster(companyMaster);
			// to set User Financial Year
			UserFinancialYear userFinancialYear = new UserFinancialYear();
			userFinancialYear.setFinancialYearID(spotRate.getFsFinanceYear().getFinancialYearID());
			spotRate.setFsFinanceYear(userFinancialYear);
			// to set Document code
			Document document = new Document();
			/** START 29/01/2015 */
			document.setDocumentID(spotRate.getFsDocument().getDocumentID());
			/** END 29/01/2015 */
			spotRate.setFsDocument(document);
			spotRate.setDocumentNo(spotRate.getDocumentNo());
			spotRate.setFcAmount(spotRate.getFcAmount());
			spotRate.setCreatedBy(spotRate.getCreatedBy());
			spotRate.setCreatedDate(spotRate.getCreatedDate());
			spotRate.setIsActive(Constants.Yes);
			iSpecialRateRequest.updateRecord(spotRate);
			setSpotRate(Constants.Yes);
			setMarqueeRender(true);
			setNextRender(false);
		}
	}

	public boolean isAmlboo() {
		return amlboo;
	}

	public void setAmlboo(boolean amlboo) {
		this.amlboo = amlboo;
	}

	public double getSlb1total1() {
		return slb1total1;
	}

	public void setSlb1total1(double slb1total1) {
		this.slb1total1 = slb1total1;
	}

	public double getSlb1total2() {
		return slb1total2;
	}

	public void setSlb1total2(double slb1total2) {
		this.slb1total2 = slb1total2;
	}

	public double getSlb1total3() {
		return slb1total3;
	}

	public void setSlb1total3(double slb1total3) {
		this.slb1total3 = slb1total3;
	}

	public double getSlb1total4() {
		return slb1total4;
	}

	public void setSlb1total4(double slb1total4) {
		this.slb1total4 = slb1total4;
	}

	public boolean isAmlboomsg() {
		return amlboomsg;
	}

	public void setAmlboomsg(boolean amlboomsg) {
		this.amlboomsg = amlboomsg;
	}

	public String getRangeFromOne() {
		return rangeFromOne;
	}

	public void setRangeFromOne(String rangeFromOne) {
		this.rangeFromOne = rangeFromOne;
	}

	public String getRangeToOne() {
		return rangeToOne;
	}

	public void setRangeToOne(String rangeToOne) {
		this.rangeToOne = rangeToOne;
	}

	public String getRangeFromTwo() {
		return rangeFromTwo;
	}

	public void setRangeFromTwo(String rangeFromTwo) {
		this.rangeFromTwo = rangeFromTwo;
	}

	public String getRangeToTwo() {
		return rangeToTwo;
	}

	public void setRangeToTwo(String rangeToTwo) {
		this.rangeToTwo = rangeToTwo;
	}

	public String getRangeFromThree() {
		return rangeFromThree;
	}

	public void setRangeFromThree(String rangeFromThree) {
		this.rangeFromThree = rangeFromThree;
	}

	public String getRangeToThree() {
		return rangeToThree;
	}

	public void setRangeToThree(String rangeToThree) {
		this.rangeToThree = rangeToThree;
	}

	public String getRangeFromFour() {
		return rangeFromFour;
	}

	public void setRangeFromFour(String rangeFromFour) {
		this.rangeFromFour = rangeFromFour;
	}

	public String getRangeToFour() {
		return rangeToFour;
	}

	public void setRangeToFour(String rangeToFour) {
		this.rangeToFour = rangeToFour;
	}

	public String getAmlRangeOneDescription() {
		return amlRangeOneDescription;
	}

	public void setAmlRangeOneDescription(String amlRangeOneDescription) {
		this.amlRangeOneDescription = amlRangeOneDescription;
	}

	public String getAmlRangeTwoDescription() {
		return amlRangeTwoDescription;
	}

	public void setAmlRangeTwoDescription(String amlRangeTwoDescription) {
		this.amlRangeTwoDescription = amlRangeTwoDescription;
	}

	public String getAmlRangeThreeDescription() {
		return amlRangeThreeDescription;
	}

	public void setAmlRangeThreeDescription(String amlRangeThreeDescription) {
		this.amlRangeThreeDescription = amlRangeThreeDescription;
	}

	public String getAmlRangeFourDescription() {
		return amlRangeFourDescription;
	}

	public void setAmlRangeFourDescription(String amlRangeFourDescription) {
		this.amlRangeFourDescription = amlRangeFourDescription;
	}

	// End by subramanian 02/02/2015
	public List<CustomerAlmTrasactionCheckProcedure> getAlmcheckList() {
		almcheckList.clear();
		CustomerAlmTrasactionCheckProcedure almcheckSinglerow = new CustomerAlmTrasactionCheckProcedure();
		almcheckSinglerow = icustomerAlmTransationCheckService.getCustomerAlmTrasactionCheckService(sessionmanage.getCountryId(), getCustomerId());
		almcheckSinglerow.setCountryName(generalService.getCountryName(sessionmanage.getLanguageId(), sessionmanage.getCountryId()));
		// Start by subramamian 02/02/2015
		List<AmlLimit> objAmlLimit = getiPersonalRemittanceService().getAmlLimitCheckList();
		System.out.println("AML LIMIT Check === = = = = > " + objAmlLimit.size());
		if (objAmlLimit.size() != 0) {
			for (AmlLimit amlLimit : objAmlLimit) {
				almcheckSinglerow.setRangeFrom(amlLimit.getRangeFromOne());
				almcheckSinglerow.setRangeTo(amlLimit.getRangeToOne());
				almcheckSinglerow.setRangeFrom(amlLimit.getRangeFromTwo());
				almcheckSinglerow.setRangeTo(amlLimit.getRangeToTwo());
				almcheckSinglerow.setRangeFrom(amlLimit.getRangeFromThree());
				almcheckSinglerow.setRangeTo(amlLimit.getRangeToThree());
				almcheckSinglerow.setRangeFrom(amlLimit.getRangeFromFour());
				almcheckSinglerow.setRangeTo(amlLimit.getRangeToFour());
				setRangeFromOne(amlLimit.getRangeFromOne());
				setRangeToOne(amlLimit.getRangeToOne());
				setAmlRangeOneDescription(amlLimit.getAmlRangeOneDescription());
				setRangeFromTwo(amlLimit.getRangeFromTwo());
				setRangeToTwo(amlLimit.getRangeToTwo());
				setAmlRangeTwoDescription(amlLimit.getAmlRangeTwoDescription());
				setRangeFromThree(amlLimit.getRangeFromThree());
				setRangeToThree(amlLimit.getRangeToThree());
				setAmlRangeThreeDescription(amlLimit.getAmlRangeThreeDescription());
				setRangeFromFour(amlLimit.getRangeFromFour());
				setRangeToFour(amlLimit.getRangeToFour());
				setAmlRangeFourDescription(amlLimit.getAmlRangeFourDescription());
				// End by subramanian 02/02/2015
				almcheckList.add(almcheckSinglerow);
			}
			for (CustomerAlmTrasactionCheckProcedure slabs : almcheckList) {
				slb1total1 = getSlb1total1() + slabs.getSlab1();
				slb1total2 = getSlb1total2() + slabs.getSlab2();
				slb1total3 = getSlb1total3() + slabs.getSlab3();
				slb1total4 = getSlb1total4() + slabs.getSlab4();
			}
		}
		return almcheckList;
	}

	public void getAlmcheck(List<CustomerAlmTrasactionCheckProcedure> almcheckList) {
		this.almcheckList = almcheckList;
	}

	
	public void setAlmcheckList(List<CustomerAlmTrasactionCheckProcedure> almcheckList) {
		this.almcheckList = almcheckList;
	}

	public void amlCheckPanelOff() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooSpecialCusFCCalDataTable(false);
		setBooRenderRemittanceServicePanel(false);
		setBooRenderCollection(false);
		setBooRendercashdenomination(false);
		setPaymentDeatailsPanel(false);
		setMinagevalidation(false);
		setMainPanelRender(true);
		setReceiptReportPanel(false);
		setBooRenderCustomerSignaturePanel(false);
		setBooRenderLayaltyServicePanel(false);
	}


	String messageOne ;
	String messageTwo ;
	String messageThree ;

	Boolean booMessageOne;
	Boolean booMessageTwo;
	Boolean booMessageThree;


	Boolean booNewAMLPanel;

	String amlAuthorizedPassword;


	public Boolean getBooMessageOne() {
		return booMessageOne;
	}

	public void setBooMessageOne(Boolean booMessageOne) {
		this.booMessageOne = booMessageOne;
	}

	public Boolean getBooMessageTwo() {
		return booMessageTwo;
	}

	public void setBooMessageTwo(Boolean booMessageTwo) {
		this.booMessageTwo = booMessageTwo;
	}

	public Boolean getBooMessageThree() {
		return booMessageThree;
	}

	public void setBooMessageThree(Boolean booMessageThree) {
		this.booMessageThree = booMessageThree;
	}

	public String getMessageOne() {
		return messageOne;
	}

	public void setMessageOne(String messageOne) {
		this.messageOne = messageOne;
	}

	public String getMessageTwo() {
		return messageTwo;
	}

	public void setMessageTwo(String messageTwo) {
		this.messageTwo = messageTwo;
	}

	public String getMessageThree() {
		return messageThree;
	}

	public void setMessageThree(String messageThree) {
		this.messageThree = messageThree;
	}



	AuthorizedLog authorizedLog;


	public String getAmlAuthorizedPassword() {
		return amlAuthorizedPassword;
	}

	public void setAmlAuthorizedPassword(String amlAuthorizedPassword) {
		this.amlAuthorizedPassword = amlAuthorizedPassword;
	}

	public Boolean getBooNewAMLPanel() {
		return booNewAMLPanel;
	}

	public void setBooNewAMLPanel(Boolean booNewAMLPanel) {
		this.booNewAMLPanel = booNewAMLPanel;
	}



	Boolean fromAMLCheck;


	public Boolean getFromAMLCheck() {
		return fromAMLCheck;
	}

	public void setFromAMLCheck(Boolean fromAMLCheck) {
		this.fromAMLCheck = fromAMLCheck;
	}


	BigDecimal beneCountr ;

	public void booAmlCheck() {

		try {
			setVisible(false);
			setFromAMLCheck(true);


			amlCheckPanelOff();
			almcheckList.clear();


			List<BenificiaryListView> isCoustomerExist = beneficaryCreation.getBeneficaryList(getCustomerNo());
			if(isCoustomerExist.size()>0){
				beneCountr = isCoustomerExist.get(0).getBenificaryCountry();
				BigDecimal masterSeqId = isCoustomerExist.get(0).getBeneficaryMasterSeqId();

				try {
					List<Object> list = getiPersonalRemittanceService().isAmlTranxAmountCheck(sessionmanage.getCountryId(),beneCountr, getCustomerNo(),masterSeqId, getNetAmountSent());

					if(list!=null && !list.isEmpty())
					{

						if( list.size() > 6 && list.get(5)!=null )
						{
							setMessageThree((String)list.get(5));

							setAmlMessageThree((String)list.get(5));
						}
						else
						{
							if( list.size() > 2 && list.get(1)!=null )
							{
								setAmlMessageOne((String)list.get(1));
							}
							if( list.size() > 4 && list.get(3)!=null )
							{
								setAmlMessageTwo((String)list.get(3));
							}
						}

					}
				} catch (AMGException e) {
					e.printStackTrace();
				}

			}
			setAmlboomsg(true);
			setAmlboo(true);

			setBoorenderlastpanel(false);
			slb1total1 = 0.0;
			slb1total2 = 0.0;
			slb1total3 = 0.0;
			slb1total4 = 0.0;


			if (getMessageOne() != null && getMessageTwo() != null) {
				setBooMessageOne(true);
				setBooMessageTwo(true);
			} else if (getMessageOne() != null) {
				setBooMessageOne(true);
			} else if (getMessageTwo() != null) {
				setBooMessageTwo(true);
			} else if (getMessageThree() != null) {
				setBooMessageThree(true);
			} else {

				setAmlboomsg(false);
				setAmlboo(false);
				setFromAMLCheck(false);
				setBooRenderCustomerSignaturePanel(true);
			}
		} catch (Exception e) {
			setErrmsg("Exception occurred "+e);
			setVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
		}



	}

	public void booAmlCheckBack() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooSpecialCusFCCalDataTable(false);
		setBooRenderRemittanceServicePanel(false);
		setAmlboo(false);
		setBoorenderlastpanel(false);
		setBooRenderCollection(false);
		setBooRendercashdenomination(false);
		setPaymentDeatailsPanel(false);
		setMinagevalidation(false);
		setMainPanelRender(true);
		setReceiptReportPanel(false);
		setBooRenderCustomerSignaturePanel(false);
		setBooRenderLayaltyServicePanel(true);
		setAmlboo(false);
		setBoorenderlastpanel(false);
		slb1total1 = 0.0;
		slb1total2 = 0.0;
		slb1total3 = 0.0;
		slb1total4 = 0.0;
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
			/* String ccc = getCountryCode().trim(); */
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

	public void popbranchlistTelePhone(BigDecimal bankID) {
		popCurrencylistBankTelePhone(bankID);
		lstBankbranch = getiPersonalRemittanceService().getBankbranchlist(bankID);
		for (BankMaster bankMaster : lstBank) {
			if (bankMaster.getBankId().compareTo(bankID) == 0) {
				setBankCode(bankMaster.getBankCode());
			}
		}
	}

	public void popCurrencylistBankTelePhone(BigDecimal bankID) {
		listCurrencyAccountDetails = ifundservice.getCurrencyOfBank(bankID);
		System.out.println("*********************" + getBenifisBankId());
		System.out.println("*********************" + listCurrencyAccountDetails.size());
	}

	public void popbranchcodeTelePhone(BigDecimal baranchID) {
		for (BankBranch bankBranchMaster : lstBankbranch) {
			if (bankBranchMaster.getBankBranchId().compareTo(baranchID) == 0) {
				setBankBranchCode(bankBranchMaster.getBranchCode());
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

	// Check Telephone Existed for particular customer -- 06/02/2015 Starts-Ram
	// Mohan
	/*
	 * public void checkYes() { setCheckTelePhoneExist("YES");
	 * setTelePhoneExist(false); }
	 */
	public void getSelectRecordFrom(PersonalRemittanceTeleExistDTBean perRmTeleDtBean) {
		/*
		 * if(perRmTeleDtBean.isSelectRecord()) {
		 * System.out.println("selected"); List<BeneficaryAccount>
		 * lstBeneficaryAccount=getLstBeneficaryAccount(); for(int i=0;
		 * i<lstBeneficaryAccount.size();i++) { BeneficaryAccount
		 * beneficaryAccount=lstBeneficaryAccount.get(i);
		 * if(perRmTeleDtBean.getBeneficaryAccountSeqId
		 * ().compareTo(beneficaryAccount.getBeneficaryAccountSeqId())==0) {
		 * lstUpdateBeneficaryAccount.add(beneficaryAccount); }
		 * 
		 * }
		 * 
		 * }else { System.out.println("deselect"); //List<BeneficaryAccount>
		 * lstBeneficaryAccount=getLstBeneficaryAccount(); for(int i=0;
		 * i<lstUpdateBeneficaryAccount.size();i++) { BeneficaryAccount
		 * beneficaryAccount=lstUpdateBeneficaryAccount.get(i);
		 * if(perRmTeleDtBean
		 * .getBeneficaryAccountSeqId().compareTo(beneficaryAccount
		 * .getBeneficaryAccountSeqId())==0) {
		 * if(lstUpdateBeneficaryAccount.size()>0) {
		 * lstUpdateBeneficaryAccount.remove(i); }
		 * 
		 * }
		 * 
		 * } }
		 */
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
			}
		}
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
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(false);
		setBooRenderCustomerSignaturePanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancelFromDialogToBenificaryStatusPanel() {
		BigDecimal telephoneNumber = getTelephoneNumber();
		List<BeneficaryContact> beneficiaryList = getiPersonalRemittanceService().isCoustomerTelephoneExistInDB(telephoneNumber);
		List<BeneficaryAccount> objAccountList = getiPersonalRemittanceService().getBeneficaryAccountDetails(beneficiaryList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId());
		List<BeneficaryMaster> objMasterList = getiPersonalRemittanceService().getBeneficaryMasterDetails(beneficiaryList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId());
		// saveBeneficaryRelation(beneficiaryList.get(0).getBeneficaryMaster());
		BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
		beneficaryMaster.setFirstName(objMasterList.get(0).getFirstName());
		beneficaryMaster.setSecondName(objMasterList.get(0).getSecondName());
		beneficaryMaster.setThirdName(objMasterList.get(0).getThirdName());
		beneficaryMaster.setFourthName(objMasterList.get(0).getFourthName());
		beneficaryMaster.setFifthName(objMasterList.get(0).getFifthName());
		beneficaryMaster.setDateOfBrith(objMasterList.get(0).getDateOfBrith());
		beneficaryMaster.setYearOfBrith(objMasterList.get(0).getYearOfBrith());
		beneficaryMaster.setAge(objMasterList.get(0).getAge());
		if (getBeneficaryStatusId().equals(new BigDecimal(1))) {
			setBooRenderBenificaryStatusPanel(true);
			setBooRenderIndBenificaryStatusPanel(true);
			setBooRenderRemittanceServicePanel(false);
			setBooRenderTypeOfServicePanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBooRenderNonIndBenificaryStatusPanel(false);
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderCustomerSignaturePanel(false);
		} else if (getBeneficaryStatusId().equals(new BigDecimal(2))) {
			setBooRenderBenificaryStatusPanel(true);
			setBooRenderNonIndBenificaryStatusPanel(true);
			setBooRenderRemittanceServicePanel(false);
			setBooRenderTypeOfServicePanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBooRenderIndBenificaryStatusPanel(false);
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderCustomerSignaturePanel(false);
		}
		setTelephoneNumber(null);
		setCountryCode(null);
		setMcountryCode(null);
		setMobileNumber(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
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
				/*
				 * List<BeneficaryAccount> beneficiaryAccountDetailList =
				 * getiPersonalRemittanceService
				 * ().isBankAccountNumberExistInDb(getBankAccountNumber(),
				 * getBenifisCountryId(), getBenifisBankId(),
				 * getServicebankBranchId());
				 */
				List<BeneficaryAccount> beneficiaryAccountDetailList = getiPersonalRemittanceService().isBankAccountNumberExist(new BigDecimal(getBankAccountNumber()), getBenifisBankId(), getBenifisCurrencyId(), getBenifisCountryId(), getServicebankBranchId());
				if (beneficiaryAccountDetailList.size() > 0 && getBeneficaryTypeId().equals(new BigDecimal(1))) {
					RequestContext.getCurrentInstance().execute("accountcheck.show();");
					setErrmsg("Data already exist for this combination");
					return;
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
			setBankAccountNumber(getTempAccountNumber().toString());
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
		/*
		 * saveBeneficaryRelation(beneficiaryAccountDetailList.get(0)
		 * .getBeneficaryMaster());
		 */
		setBooRenderBenificarySearchPanel(true);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderTypeOfServicePanel(false);
		setBooRenderCustomerSignaturePanel(false);
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

	/*
	 * public List<ServiceMasterDesc> getServiceMasterDescList() { return
	 * igeneralService.getAllServiceDesc(sessionmanage.getLanguageId()); }
	 * 
	 * public void setServiceMasterDescList( List<ServiceMasterDesc>
	 * serviceMasterDescList) { this.serviceMasterDescList =
	 * serviceMasterDescList; }
	 */
	List<ServiceGroupMasterDesc> lstfetchCashId;

	public void enableServiceProvider() {
		/*
		 * List<ServiceMasterDesc> lstfetchCashId =
		 * iPersonalRemittanceService.fetchCashServiceId(Constants.CASHNAME, new
		 * BigDecimal(sessionmanage.isExists("languageId") ?
		 * sessionmanage.getSessionValue("languageId") : "1"));
		 */
		lstfetchCashId = iPersonalRemittanceService.fetchCashServiceGorupId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
		if (lstfetchCashId.size() != 0) {
			/*
			 * if(getServiceGroupId() != null &&
			 * (getServiceGroupId().compareTo(lstfetchCashId
			 * .get(0).getServiceMaster().getServiceId())==0)) {
			 */
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

	public Boolean getDisableSpotRatePanel() {
		return disableSpotRatePanel;
	}

	public void setDisableSpotRatePanel(Boolean disableSpotRatePanel) {
		this.disableSpotRatePanel = disableSpotRatePanel;
	}

	public Boolean getBooShowCashRoundingPanel() {
		return booShowCashRoundingPanel;
	}

	public void setBooShowCashRoundingPanel(Boolean booShowCashRoundingPanel) {
		this.booShowCashRoundingPanel = booShowCashRoundingPanel;
	}

	public BigDecimal getIdProof() {
		return idProof;
	}

	public void setIdProof(BigDecimal idProof) {
		this.idProof = idProof;
	}

	public BigDecimal getPurposeOfRemittance() {
		return purposeOfRemittance;
	}

	public void setPurposeOfRemittance(BigDecimal purposeOfRemittance) {
		this.purposeOfRemittance = purposeOfRemittance;
	}

	public BigDecimal getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(BigDecimal sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public String getMessageToUser() {
		return messageToUser;
	}

	public void setMessageToUser(String messageToUser) {
		this.messageToUser = messageToUser;
	}

	public void showModeOfPayment() {
		for (PaymentModeDesc paymodedesc : paymentModes) {
			if (getModeOfPayment().compareTo(paymodedesc.getPaymentMode().getPaymentModeId()) == 0) {
				if (paymodedesc.getLocalPaymentName().equalsIgnoreCase(Constants.CASHNAME)) {
					setBooShowCashRoundingPanel(true);
					break;
				}
			} else {
				setBooShowCashRoundingPanel(false);
			}
		}
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

	public List<BankAccountDetails> getCurrencylist2() {
		return currencylist2;
	}

	public void setCurrencylist2(List<BankAccountDetails> currencylist2) {
		this.currencylist2 = currencylist2;
	}

	public List<BankAccountDetails> getCurrencylist() {
		return currencylist;
	}

	public void setCurrencylist(List<BankAccountDetails> currencylist) {
		this.currencylist = currencylist;
	}

	/*
	 * public void popbankCurrency() {
	 * 
	 * currencylist2 =
	 * fundEstimationService.getCurrencyOfBank(getBeneficaryBankId()); if
	 * (currencylist2.size() == 1) { setSingleCurrency(true);
	 * setMultCurrency(false);
	 * setCurrency(currencylist2.get(0).getFsCurrencyMaster() .getCurrencyId());
	 * setCurrencyName(currencylist2.get(0).getFsCurrencyMaster()
	 * .getCurrencyName()); } else { setSingleCurrency(false);
	 * setMultCurrency(true); setCurrencylist(currencylist2); }
	 * 
	 * }
	 */
	// saving all data in EX_REMITTANCE_APPLICATION
	// List for Document ID and Desc
	public String getAmlMessageOne() {
		return amlMessageOne;
	}

	public void setAmlMessageOne(String amlMessageOne) {
		this.amlMessageOne = amlMessageOne;
	}

	public String getAmlMessageTwo() {
		return amlMessageTwo;
	}

	public void setAmlMessageTwo(String amlMessageTwo) {
		this.amlMessageTwo = amlMessageTwo;
	}

	public String getAmlMessageThree() {
		return amlMessageThree;
	}

	public void setAmlMessageThree(String amlMessageThree) {
		this.amlMessageThree = amlMessageThree;
	}

	public String getAmlAuthorizedBy() {
		return amlAuthorizedBy;
	}

	public void setAmlAuthorizedBy(String amlAuthorizedBy) {
		this.amlAuthorizedBy = amlAuthorizedBy;
	}

	public String getAmlRemarks() {
		return amlRemarks;
	}

	public void setAmlRemarks(String amlRemarks) {
		this.amlRemarks = amlRemarks;
	}

	public boolean isSelectrecord() {
		return selectrecord;
	}

	public void setSelectrecord(boolean selectrecord) {
		this.selectrecord = selectrecord;
	}

	public boolean isBoorenderlastpanel() {
		return boorenderlastpanel;
	}

	public void setBoorenderlastpanel(boolean boorenderlastpanel) {
		this.boorenderlastpanel = boorenderlastpanel;
	}

	public BigDecimal getLoyalitypoints() {
		return loyalitypoints;
	}

	public void setLoyalitypoints(BigDecimal loyalitypoints) {
		this.loyalitypoints = loyalitypoints;
	}

	/*
	 * public String getExchangeType() { return exchangeType; }
	 * 
	 * public void setExchangeType(String exchangeType) { this.exchangeType =
	 * exchangeType; }
	 */
	public List<Document> getLstDocument() {
		return lstDocument;
	}

	public void setLstDocument(List<Document> lstDocument) {
		this.lstDocument = lstDocument;
	}



	public BigDecimal getSplcompanyid() {
		return splcompanyid;
	}

	public void setSplcompanyid(BigDecimal splcompanyid) {
		this.splcompanyid = splcompanyid;
	}

	public BigDecimal getSpldocumentid() {
		return spldocumentid;
	}

	public void setSpldocumentid(BigDecimal spldocumentid) {
		this.spldocumentid = spldocumentid;
	}

	public BigDecimal getSplfinyear() {
		return splfinyear;
	}

	public void setSplfinyear(BigDecimal splfinyear) {
		this.splfinyear = splfinyear;
	}

	public BigDecimal getSpldocnum() {
		return spldocnum;
	}

	public void setSpldocnum(BigDecimal spldocnum) {
		this.spldocnum = spldocnum;
	}

	public BigDecimal getSpldealreqid() {
		return spldealreqid;
	}

	public void setSpldealreqid(BigDecimal spldealreqid) {
		this.spldealreqid = spldealreqid;
	}

	

	public void saveApplicationTransaction() {/*
		if (getDigitalSignature() != null && !getDigitalSignature().equalsIgnoreCase("")) {
			saveRemittanceApplication(saveCount);
		} else {
			RequestContext.getCurrentInstance().execute("signatureError.show();");
		}
	
		
	
	*/
		


		if (getDigitalSignature() != null && !getDigitalSignature().equalsIgnoreCase("")) {
			setBooRenderSignatureMsg(false);
			setSignatureMandatoryMsg(null);
			saveRemittanceApplication(saveCount);
		} else {
			setBooRenderSignatureMsg(true);
			setSignatureMandatoryMsg("Please Sign and Continue");
			//RequestContext.getCurrentInstance().execute("signatureError.show();");
		}

	
	}
	public java.sql.Clob stringToClob(String source) throws Exception {
		try {
			return new javax.sql.rowset.serial.SerialClob(source.toCharArray());
		} catch (Exception e) {
			return null;
		}
	}

	// saving data to Remittance Application
	public void saveRemittanceApplication(int saveCount) {



		try {
			if(saveCount==0){

				// start 28/01/2015
				setDocumentNo(getDocumentSerialID(Constants.Yes));
				// End 28/01/2015verifyPassword
				RemittanceApplication remittanceApplication = new RemittanceApplication();
				// Foriegn Currency id
				CurrencyMaster forcurrencymaster = new CurrencyMaster();
				forcurrencymaster.setCurrencyId(getForiegnCurrency());
				remittanceApplication.setExCurrencyMasterByForeignCurrencyId(forcurrencymaster);
				// Local Commission Currency Id - KWD
				CurrencyMaster commisioncurrencymaster = new CurrencyMaster();
				commisioncurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
				if (getCommission()!=null && getCommission().compareTo(BigDecimal.ZERO) != 0) {
					remittanceApplication.setExCurrencyMasterByLocalCommisionCurrencyId(commisioncurrencymaster); // mru
				} else {
					commisioncurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
					remittanceApplication.setExCurrencyMasterByLocalCommisionCurrencyId(commisioncurrencymaster);
				}
				// local Transaction Currency Id
				CurrencyMaster tranxcurrencymaster = new CurrencyMaster();
				tranxcurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
				remittanceApplication.setExCurrencyMasterByLocalTranxCurrencyId(tranxcurrencymaster);
				// local Charge Currency Id -KWD
				CurrencyMaster chargecurrencymaster = new CurrencyMaster();
				chargecurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
				remittanceApplication.setExCurrencyMasterByLocalChargeCurrencyId(chargecurrencymaster);
				// local Net Currency Id - kWD
				CurrencyMaster netcurrencymaster = new CurrencyMaster();
				netcurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
				remittanceApplication.setExCurrencyMasterByLocalNetCurrencyId(netcurrencymaster);
				// local Delivery Currency Id - kWD
				CurrencyMaster devcurrencymaster = new CurrencyMaster();
				devcurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
				// Modifed by Rabil
				remittanceApplication.setExCurrencyMasterByLocalDeliveryCurrencyId(null);

				//Added by Rabil
				remittanceApplication.setSpotRateInd(getSpotRate());
				remittanceApplication.setLoyaltyPointInd(getAvailLoyaltyPoints());

				// Document Id
				if (getFromAMLCheck()) {

					AuthorizedLog authorizedLog = new AuthorizedLog();

					CompanyMaster com = new CompanyMaster();
					com.setCompanyId(sessionStateManage.getCompanyId());
					authorizedLog.setCompanymaster(com);

					try {
						authorizedLog.setAccountYearMonth(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
					} catch (ParseException e) {
						e.printStackTrace();
					}

					authorizedLog.setAuthorizedType("Message 3 Test");
					authorizedLog.setDocumentNo(new BigDecimal(getDocumentNo()));

					iPersonalRemittanceService.saveAuthorizedLog(authorizedLog);
				}

				List<Document> lstDocument = generalService
						.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FC_SALE_APP),
								new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
				if (lstDocument.size() != 0) {
					Document documentid = new Document();
					documentid.setDocumentID(lstDocument.get(0).getDocumentID());
					remittanceApplication.setExDocument(documentid);
				}

				// company Id
				CompanyMaster companymaster = new CompanyMaster();
				companymaster.setCompanyId(sessionStateManage.getCompanyId());
				remittanceApplication.setFsCompanyMaster(companymaster);
				// User Financial Year for Document
				UserFinancialYear docuserfinancialyear = new UserFinancialYear();
				docuserfinancialyear.setFinancialYearID(generalService.getDealYear(new Date()).get(0).getFinancialYearID());
				remittanceApplication.setExUserFinancialYearByDocumentFinanceYear(docuserfinancialyear);
				// User Financial Year for Transaction
				//UserFinancialYear userfinancialyear = new UserFinancialYear();
				//userfinancialyear.setFinancialYearID(generalService.getDealYear(new Date()).get(0).getFinancialYearID());
				//remittanceApplication.setExUserFinancialYearByTransactionFinanceYear(null);// userfinancialyear);
				// Country Branch
				CountryBranch countryBranch = new CountryBranch();
				countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
				remittanceApplication.setExCountryBranch(countryBranch);
				// Application Country
				CountryMaster appcountrymaster = new CountryMaster();
				appcountrymaster.setCountryId(sessionStateManage.getCountryId());
				remittanceApplication.setFsCountryMasterByApplicationCountryId(appcountrymaster);
				// routing Country
				CountryMaster bencountrymaster = new CountryMaster();
				bencountrymaster.setCountryId(getRoutingCountry());
				remittanceApplication.setFsCountryMasterByBankCountryId(bencountrymaster);
				// Corresponding Country - Routing Country
				/*CountryMaster routcountrymaster = new CountryMaster();
			routcountrymaster.setCountryId(getRoutingCountry());
			remittanceApplication.setFsCountryMasterByCorespondingCountryId(routcountrymaster);*/
				// Delivery Mode from service
				if (getDeliveryMode() != null) {
					DeliveryMode deliverymode = new DeliveryMode();
					deliverymode.setDeliveryModeId(getDeliveryMode());
					remittanceApplication.setExDeliveryMode(deliverymode);
				}

				// RemittanceModeMaster to get Remittance
				if (getRemitMode() != null) {
					RemittanceModeMaster remittancemode = new RemittanceModeMaster();
					remittancemode.setRemittanceModeId(getRemitMode());
					remittanceApplication.setExRemittanceMode(remittancemode);
				}

				// Customer id
				Customer customerid = new Customer();
				customerid.setCustomerId(getCustomerNo());
				remittanceApplication.setFsCustomer(customerid);
				// Routing Bank
				BankMaster bankmaster = new BankMaster();
				bankmaster.setBankId(getRoutingBank());
				remittanceApplication.setExBankMaster(bankmaster);
				// Routing Bank Branch
				BankBranch bankbranch = new BankBranch();
				bankbranch.setBankBranchId(getRoutingBranch());
				remittanceApplication.setExBankBranch(bankbranch);
				// remittanceApplication.setDocumentNo(getDocumentId());
				remittanceApplication.setDocumentDate(new Date());
				// remittanceApplication.setTransactionRefNo(transactionRefNo);
				remittanceApplication.setCustomerRef(getCustomerrefno());
				if (getDataAccountnum() != null) {
					remittanceApplication.setDebitAccountNo(getDataAccountnum());
				}
				remittanceApplication.setForeignTranxAmount(getNetAmountSent());
				remittanceApplication.setLocalTranxAmount(getGrossAmountCalculated());
				remittanceApplication.setExchangeRateApplied(getExchangeRate());
				remittanceApplication.setLocalCommisionAmount(getCommission());
				remittanceApplication.setLocalChargeAmount(getOverseasamt());
				// remittanceApplication.setLocalDeliveryAmount(getCommission());
				remittanceApplication.setLocalDeliveryAmount(new BigDecimal(0));
				remittanceApplication.setLocalNetTranxAmount(getNetAmountPayable());
				remittanceApplication.setLoyaltyPointsEncashed(getLoyaltyAmountAvailed());
				remittanceApplication.setDocumentFinancialyear(generalService.getDealYear(new Date()).get(0).getFinancialYear());
				//remittanceApplication.setTransactionFinancialyear(generalService.getDealYear(new Date()).get(0).getFinancialYear());
				remittanceApplication.setSelectedCurrencyId(getCurrency());
				// remittanceApplication.setApplicaitonStatus("P"); // set
				// Application
				// Pass P , Fail F
				// remittanceApplication.setBlackListIndicator("Y"); // set Black
				// List
				// Yes Y , NO N
				try {
					remittanceApplication.setAccountMmyyyy(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				// remittanceApplication.setWesternUnionMtcno(null);
				remittanceApplication.setCreatedBy(sessionStateManage.getUserName());
				remittanceApplication.setCreatedDate(new Date());
				remittanceApplication.setIsactive(Constants.Yes);
				// remittanceApplication.setApplicaitonStatus("R");
				remittanceApplication.setDocumentNo(new BigDecimal(getDocumentNo()));
				remittanceApplication.setSourceofincome(getSourceOfIncome());
				// blocked and inserted into new Table EX_CUSTOMER_SPECIAL_DEAL_APPL
				if (selectedSplDeal.size() != 0) {
					remittanceApplication.setSpldeal(Constants.Yes);
					//for (PersonalRemittanceCalFCAmountDataTable lstSelectedSplDeal : selectedSplDeal) {
					//remittanceApplication.setSpldealcompanyId(lstSelectedSplDeal.getSplcompanyID());
					//remittanceApplication.setSpldealdocumentcode(lstSelectedSplDeal.getSpldocumentID());
					//remittanceApplication.setSpldealdocumentnumber(lstSelectedSplDeal.getSpldocnumID());
					//remittanceApplication.setSpldealdocumentyear(lstSelectedSplDeal.getSplfinyearID());
					//}
				}

				if (getDigitalSignature() != null) {
					// remittanceApplication.setCustomerSignature(getDigitalSignature());
					try {
						remittanceApplication.setCustomerSignatureClob(stringToClob(getDigitalSignature()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				remittanceApplication.setInstruction(getFurthuerInstructions());



				// iPersonalRemittanceService.saveRemittanceApplication(remittanceApplication);

				HashMap<String, Object> mapAllDetailPersonalRemittanceApplSave = new HashMap<String, Object>();

				// save to EX_APPL_TRNX
				mapAllDetailPersonalRemittanceApplSave.put("EX_APPL_TRNX",remittanceApplication);

				RemittanceAppBenificiary remitAppBene = saveRemittanceAppBenificary(remittanceApplication);

				mapAllDetailPersonalRemittanceApplSave.put("EX_APPL_BENE",remitAppBene);

				List<RemitApplAml> remitApplAml= saveRemittanceAppAML(remittanceApplication);

				mapAllDetailPersonalRemittanceApplSave.put("EX_APPL_AML",remitApplAml);

				List<AdditionalInstructionData> lstAddInstData = saveAdditionalInstnData(remittanceApplication);

				mapAllDetailPersonalRemittanceApplSave.put("EX_APPL_ADDL_DATA",lstAddInstData);

				mapAllDetailPersonalRemittanceApplSave.put("SPOT_RATE_PK",getSpotExchangeRatePk());

				mapAllDetailPersonalRemittanceApplSave.put("FinancialYear", getFinaceYear());

				// customer special deal records
				List<CustomerSpecialDealAppl> lstCustSplDealApplRec = saveCustomerSpecialDealApplication(remittanceApplication);
				mapAllDetailPersonalRemittanceApplSave.put("EX_CUSTOMER_SPECIAL_DEAL_APPL", lstCustSplDealApplRec);


				iPersonalRemittanceService.saveAllApplTransaction(mapAllDetailPersonalRemittanceApplSave);
				// updatingUtilizedAmount();
				assignNullValues();
				checkProExp = true;
				saveCount = saveCount+1;
			}

		} catch (AMGException e) {
			if(!checkProExp){
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}
		}catch (Exception e) {
			if(!checkProExp){
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}
		}

		if(checkProExp){
			RequestContext.getCurrentInstance().execute("remittanceaAppSave.show();");
		}

		
	
	}
	
	// customer special deal save
		public List<CustomerSpecialDealAppl> saveCustomerSpecialDealApplication(RemittanceApplication remittanceApplication){

			List<CustomerSpecialDealAppl> lstCustSplDealAppl = new ArrayList<CustomerSpecialDealAppl>();

			if(personalRemittCalFCAmountDTList.size() != 0){

				for (PersonalRemittanceCalFCAmountDataTable lstCustomerSpecialDeal : personalRemittCalFCAmountDTList) {

					if(lstCustomerSpecialDeal.getSelect()){
						CustomerSpecialDealAppl custSplDealAppl = new CustomerSpecialDealAppl();

						//custSplDealAppl.setCustomerSpecialDealApplId(lstCustomerSpecialDeal.get);
						custSplDealAppl.setCustomerSpecialDealReqId(lstCustomerSpecialDeal.getSpecialCustomerPrimaryKey());
						custSplDealAppl.setApplicationCountryId(sessionStateManage.getCountryId());
						custSplDealAppl.setCompanyMasterId(sessionStateManage.getCompanyId());
						custSplDealAppl.setDocumentId(lstCustomerSpecialDeal.getSpldocumentID());
						custSplDealAppl.setFinancialYearId(lstCustomerSpecialDeal.getSplfinyearID());
						custSplDealAppl.setDocumentNumber(lstCustomerSpecialDeal.getSpldocnumID());
						custSplDealAppl.setApplicationFinancialYear(remittanceApplication.getDocumentFinancialyear());
						custSplDealAppl.setApplicationDocumentNumber(remittanceApplication.getDocumentNo());
						custSplDealAppl.setForiegnCurrencyAmount(lstCustomerSpecialDeal.getPercentageAddedAmount());
						custSplDealAppl.setCreatedBy(sessionStateManage.getUserName());
						custSplDealAppl.setCreatedDate(new Date());
						custSplDealAppl.setDocumentStatus(Constants.Yes);

						lstCustSplDealAppl.add(custSplDealAppl);
					}
				}
			}
			return lstCustSplDealAppl;
		}

	// saving data to Remittance App Benificary Table
		public RemittanceAppBenificiary saveRemittanceAppBenificary(RemittanceApplication remittanceApplication) {
			RemittanceAppBenificiary remittanceAppBenificary = new RemittanceAppBenificiary();
			try{
				// to get the document details - Hard Code
				List<Document> lstDocument = generalService
						.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCSALE_REMITTANCE_APPLICATION),
								new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
				if (lstDocument.size() > 0) {
					for (Document lstdoc : lstDocument) {
						setDocumentId(lstdoc.getDocumentID());
						setDocumentdesc(lstdoc.getDocumentDesc());
						setDocumentcode(lstdoc.getDocumentCode());
					}
				}
				// Document Id
				Document documentid = new Document();
				documentid.setDocumentID(getDocumentId());
				remittanceAppBenificary.setExDocument(documentid);
				// company Id
				CompanyMaster companymaster = new CompanyMaster();
				companymaster.setCompanyId(sessionStateManage.getCompanyId());
				remittanceAppBenificary.setFsCompanyMaster(companymaster);

				// company code
				List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice.viewById(sessionStateManage.getCompanyId());
				if(lstcompanymaster.size() != 0){
					CompanyMasterDesc companycode = lstcompanymaster.get(0);
					remittanceAppBenificary.setCompanyCode(companycode.getFsCompanyMaster().getCompanyCode());
				}

				// User Financial Year for Transaction
				UserFinancialYear userfinancialyear = new UserFinancialYear();
				userfinancialyear.setFinancialYearID(generalService.getDealYear(new Date()).get(0).getFinancialYearID());
				remittanceAppBenificary.setExUserFinancialYear(userfinancialyear);
				// RemittanceApplication Id
				remittanceAppBenificary.setExRemittanceAppfromBenfi(remittanceApplication);
				remittanceAppBenificary.setDocumentCode(getDocumentcode());
				remittanceAppBenificary.setDocumentNo(remittanceApplication.getDocumentNo());
				remittanceAppBenificary.setBeneficiaryId(getMasterId());

				// remittanceAppBenificary.setBeneficiaryAccountNo(getBeneficaryAccountList().get(0).getBankAccountNumber().toPlainString());
				if (getDataAccountnum() != null) {
					remittanceAppBenificary.setBeneficiaryAccountNo(getDataAccountnum().toString());
				}

				if (getBeneSwiftBank1() != null) {
					// remittanceAppBenificary.setBeneficiarySwiftBank1(getBeneSwiftBank1().toString());
					remittanceAppBenificary.setBeneficiarySwiftBank1(null);
					remittanceAppBenificary.setBeneficiarySwiftBank1Id(new BigDecimal(getBeneSwiftBank1()));

				}
				if (getBeneSwiftBank2() != null) {
					// remittanceAppBenificary.setBeneficiarySwiftBank2(getBeneSwiftBank2().toString());
					remittanceAppBenificary.setBeneficiarySwiftBank2(null);
					remittanceAppBenificary.setBeneficiarySwiftBank2Id(new BigDecimal(getBeneSwiftBank2()));
				}

				if (getBeneSwiftBankAddr1() != null) {
					remittanceAppBenificary.setBeneficiarySwiftAddr1(getBeneSwiftBankAddr1());
				}

				if (getBeneSwiftBankAddr2() != null) {
					remittanceAppBenificary.setBeneficiarySwiftAddr2(getBeneSwiftBankAddr2());
				}

				remittanceAppBenificary.setCreatedBy(sessionStateManage.getUserName());
				remittanceAppBenificary.setCreatedDate(new Date());
				remittanceAppBenificary.setIsactive(Constants.Yes);
				// to get Beneficary status from Benificary Master
				if (getPbeneFullName() != null) {
					remittanceAppBenificary.setBeneficiaryName(getPbeneFullName());
				}

				if (getPbeneFirstName() != null) {
					remittanceAppBenificary.setBeneficiaryFirstName(getPbeneFirstName());
				}

				if (getPbeneSecondName() != null) {
					remittanceAppBenificary.setBeneficiarySecondName(getPbeneSecondName());
				}

				if (getPbeneThirdName() != null) {
					remittanceAppBenificary.setBeneficiaryThirdName(getPbeneThirdName());
				}

				if (getPbeneFourthName() != null) {
					remittanceAppBenificary.setBeneficiaryFourthName(getPbeneFourthName());
				}

				if (getPbeneFifthName() != null) {
					remittanceAppBenificary.setBeneficiaryFifthName(getPbeneFifthName());
				}

				remittanceAppBenificary.setBeneficiaryBankCountryId(getDatabenificarycountry());
				remittanceAppBenificary.setBeneficiaryBank(getDatabenificarybankname());
				remittanceAppBenificary.setBeneficiaryBranch(getDatabenificarybranchname());
				remittanceAppBenificary.setBeneficiaryBankId(getBeneficaryBankId());
				remittanceAppBenificary.setBeneficiaryBankBranchId(getBeneficaryBankBranchId());
				remittanceAppBenificary.setBeneficiaryBranchStateId(getBeneStateId());
				remittanceAppBenificary.setBeneficiaryBranchDistrictId(getBeneDistrictId());
				remittanceAppBenificary.setBeneficiaryBranchCityId(getBeneCityId());
				remittanceAppBenificary.setBeneficiaryAccountSeqId(getBeneficiaryAccountSeqId());
				remittanceAppBenificary.setBeneficiaryRelationShipSeqId(getBeneficiaryRelationShipSeqId());

			}catch(Exception e){
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}

			return remittanceAppBenificary;
		}

		// saving data to Remittance Application AML
		public List<RemitApplAml> saveRemittanceAppAML(RemittanceApplication remittanceApplication) {

			List<RemitApplAml> remitApplAmlList = new ArrayList<RemitApplAml>();

			try{

				Iterator<Map.Entry<String, String>> entries = amlAuthuTYpe.entrySet().iterator();

				if(!amlAuthuTYpe.isEmpty())
				{
					while (entries.hasNext()) {

						Map.Entry<String, String> entry = entries.next();

						RemitApplAml remitApplAml = new RemitApplAml();
						// company Id
						CompanyMaster companymaster = new CompanyMaster();
						companymaster.setCompanyId(sessionStateManage.getCompanyId());
						remitApplAml.setFsCompanyMaster(companymaster);
						// RemittanceApplication Id
						remitApplAml.setExRemittanceAppfromAml(remittanceApplication);
						// benificary Country
						CountryMaster bencountrymaster = new CountryMaster();
						bencountrymaster.setCountryId(getDatabenificarycountry());
						remitApplAml.setFsCountryMaster(bencountrymaster);
						// remitApplAml.setBlackListReason(blackListReason);
						// remitApplAml.setBlackListClear(blackListClear);
						// remitApplAml.setBlackListRemarks(blackListRemarks);
						// remitApplAml.setBlackListDate(blackListDate);
						// remitApplAml.setBlackListUser(blackListUser);
						// remitApplAml.setBlackClearedUser(blackClearedUser);
						// remitApplAml.setCustomerSignature(customerSignature);
						remitApplAml.setCreatedBy(sessionStateManage.getUserName());
						remitApplAml.setCreatedDate(new Date());
						remitApplAml.setIsactive(Constants.Yes);
						remitApplAml.setAuthorizedBy(getAmlAuthorizedBy());
						remitApplAml.setAuthType(entry.getKey());
						remitApplAml.setBlackListReason(entry.getValue());
						remitApplAml.setBlackListRemarks(getAmlRemarks());
						remitApplAmlList.add(remitApplAml);

					}
				}
			}catch(Exception e){
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}

			return remitApplAmlList;
		}
		
		public List<AdditionalInstructionData> saveAdditionalInstnData(RemittanceApplication remittanceApplication) {
			List<AdditionalInstructionData> lstAddInstrData = new ArrayList<AdditionalInstructionData>();
			System.out.println("========================CALEEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD+++++++++++++++++++++=");
			Document document = new Document();
			document.setDocumentID(generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FC_SALE_APP),
					new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")).get(0).getDocumentID());
			// company Id
			CompanyMaster companymaster = new CompanyMaster();
			companymaster.setCompanyId(sessionStateManage.getCompanyId());
			// Application Country
			CountryMaster countrymaster = new CountryMaster();
			countrymaster.setCountryId(sessionStateManage.getCountryId());

			for (AddAdditionalBankData dynamicList : listAdditionalBankDataTable) {

				AdditionalInstructionData additionalInsData = new AdditionalInstructionData();
				AdditionalBankRuleMap additionalBank = new AdditionalBankRuleMap();
				if(dynamicList.getAdditionalBankRuleFiledId()!=null){
					additionalBank.setAdditionalBankRuleId(dynamicList.getAdditionalBankRuleFiledId());
					additionalInsData.setAdditionalBankFieldsId(additionalBank);
				}

				// System.out.println("dynamicList.getFlexiField() :"+dynamicList.getFlexiField()+"\t dynamicList.getAmicCode() :"+dynamicList.getAmicCode());

				additionalInsData.setFlexField(dynamicList.getFlexiField());
				if(dynamicList.getAdditionalBankRuleFiledId()!=null){
					String amiecdec = dynamicList.getVariableName();
					String amicCode=null;
					String amicDesc=null;
					if(amiecdec!=null)
					{

						String [] amiecdecValues =amiecdec.split("-");
						if(amiecdecValues.length>0)
						{
							amicCode=amiecdecValues[0];		

						}
						if(amiecdecValues.length>1)
						{
							amicDesc=amiecdecValues[1];		

						}

					}

					additionalInsData.setAmiecCode(amicCode);
					additionalInsData.setFlexFieldValue(amicDesc);
				}else{
					additionalInsData.setAmiecCode(Constants.AMIEC_CODE);
					additionalInsData.setFlexFieldValue(dynamicList.getVariableName());
				}

				additionalInsData.setExDocument(document);
				additionalInsData.setFsCountryMaster(countrymaster);
				additionalInsData.setFsCompanyMaster(companymaster);
				additionalInsData.setExRemittanceApplication(remittanceApplication);
				additionalInsData.setExUserFinancialYear(remittanceApplication.getExUserFinancialYearByDocumentFinanceYear());
				additionalInsData.setDocumentFinanceYear(remittanceApplication.getDocumentFinancialyear());

				additionalInsData.setCreatedBy(sessionStateManage.getUserName());
				additionalInsData.setCreatedDate(new Date());
				additionalInsData.setIsactive(Constants.Yes);
				additionalInsData.setDocumentNo(new BigDecimal(getDocumentNo()));

				// iPersonalRemittanceService.saveAdditionalInsData(additionalInsData);

				lstAddInstrData.add(additionalInsData);
			}

			return lstAddInstrData;
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
	
	// Checking Records Avaliable in Shopping Cart
		public void checkShoppingCartRecords(){
			setAmlRecheckAuthentication(false);
			getShoppingCartDetails(getCustomerNo());
			if (shoppingcartDTList.size() != 0) {
				nextrenderingLastPanel();
			} else {
				lstselectedrecord.clear();
				backFromBenificaryStatusPanel();
				setCalGrossAmount(null);
				setCalNetAmountPaid(null);
				shoppingcartDTList.clear();
				RequestContext.getCurrentInstance().execute("noshoppingcard.show();");
			}
			if (getCalGrossAmount() == null) {
				setBooShowCashRoundingPanel(false);
				setCashRounding(null);
				setApplicationDocNum(null);
				setBooRenderSingleDocNum(true);
				setBooRenderMultiDocNum(false);
				lstApplDocNumber.clear();
				setBooRenderModifiedRoundData(false);
				lstModifyRoundRecord.clear();
			}

		}

	// to render to show last panel
	public void nextrenderingLastPanel() {
		setCalGrossAmount(null);
		setCalNetAmountPaid(null);
		coldatatablevalues.clear();
		setColpaymentmodeId(null);
		setToalUsedAmount(null);
		setTotalbalanceAmount(null);
		setTotalrefundAmount(null);
		setColCash(null);
		setColBankCode(null);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		setColAuthorizedby(null);
		setColpassword(null);
		setBoRenderTotalAmountCalPanel(false);
		// all panel off
		allPanelOff();
		setBoorenderlastpanel(true);	

		getShoppingCartDetails(getCustomerNo());
		if (shoppingcartDTList.size() != 0) {
			lstselectedrecord.clear();
			setCalGrossAmount(null);
			setCalNetAmountPaid(null);
			tempCalNetAmountPaid = new BigDecimal(0);
			tempCalGrossAmount = new BigDecimal(0);
			remittanceNo = new BigDecimal(0);
			fcsaleNo = new BigDecimal(0);
		} else {
			lstselectedrecord.clear();
			backFromBenificaryStatusPanel();
			setCalGrossAmount(null);
			setCalNetAmountPaid(null);
			shoppingcartDTList.clear();
			//RequestContext.getCurrentInstance().execute("noshoppingcard.show();");
		}

		// round functionality
		if (getCalGrossAmount() == null) {
			setAmlAuntenticationAuthorizedBy(null);
			setAmlAuntenticationAuthorizedPassword(null);
			setAmlAuntenticationType(null);
			setAmlAuntenticationMessageOne(null);
			setAmlRecheckAuthentication(false);
			setAmlAuntenticationRemarks(null);
			setBooShowCashRoundingPanel(false);
			setBooShowAuthenticationPanel(false);
			setCashRounding(null);
			setApplicationDocNum(null);
			setBooRenderSingleDocNum(true);
			setBooRenderMultiDocNum(false);
			lstApplDocNumber.clear();
			setBooRenderModifiedRoundData(false);
			lstModifyRoundRecord.clear();
		}

	}

	public List<RemittanceApplication> getLstRemitApp() {
		return lstRemitApp;
	}

	public void setLstRemitApp(List<RemittanceApplication> lstRemitApp) {
		this.lstRemitApp = lstRemitApp;
	}

	// to pop datatable in final panel
	public void getRemittanceApplicationDetails() {
		lstRemitApp.clear();
		setSelectrecord(false);
		setCalNetAmountPaid(null);
		setCalGrossAmount(null);
		lstRemitApp = iPersonalRemittanceService.getRemittanceApplicationAllDetails(getCustomerNo());
		if (lstRemitApp.size() > 0) {
			System.out.println("$$$$$$$$$$$lstRemitApp$$$$$$$$$$$$$$" + lstRemitApp.size());
		}
	}

	// for last final save to remittance - list which to be true
	CopyOnWriteArrayList<ShoppingCartDataTableBean> lstselectedrecord = new CopyOnWriteArrayList<ShoppingCartDataTableBean>();
	CopyOnWriteArrayList<ShoppingCartDataTableBean> lstModifyRoundRecord = new CopyOnWriteArrayList<ShoppingCartDataTableBean>();

	public CopyOnWriteArrayList<ShoppingCartDataTableBean> getLstModifyRoundRecord() {
		return lstModifyRoundRecord;
	}

	public void setLstModifyRoundRecord(CopyOnWriteArrayList<ShoppingCartDataTableBean> lstModifyRoundRecord) {
		this.lstModifyRoundRecord = lstModifyRoundRecord;
	}

	public CopyOnWriteArrayList<ShoppingCartDataTableBean> getLstselectedrecord() {
		return lstselectedrecord;
	}

	public void setLstselectedrecord(CopyOnWriteArrayList<ShoppingCartDataTableBean> lstselectedrecord) {
		this.lstselectedrecord = lstselectedrecord;
	}

	public BigDecimal getCalNetAmountPaid() {
		return calNetAmountPaid;
	}

	public void setCalNetAmountPaid(BigDecimal calNetAmountPaid) {
		this.calNetAmountPaid = calNetAmountPaid;
	}

	public BigDecimal getCalGrossAmount() {
		return calGrossAmount;
	}

	public void setCalGrossAmount(BigDecimal calGrossAmount) {
		this.calGrossAmount = calGrossAmount;
	}

	public BigDecimal getCalNetAmountSend() {
		return calNetAmountSend;
	}

	public void setCalNetAmountSend(BigDecimal calNetAmountSend) {
		this.calNetAmountSend = calNetAmountSend;
	}

	// selected in remittance to make save in remittance
	public void getSelectedRecordstoRemittance(ShoppingCartDataTableBean shoppingCartDataTableBean) {
		setColCurrency(generalService.getCurrencyName(new BigDecimal(sessionmanage.getCurrencyId())));
		if (shoppingCartDataTableBean.getSelectedrecord()) {
			if (shoppingCartDataTableBean.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
				remittanceNo = remittanceNo.add(new BigDecimal(1));
			} else if (shoppingCartDataTableBean.getApplicationType().equalsIgnoreCase(Constants.FCSale)) {
				fcsaleNo = fcsaleNo.add(new BigDecimal(1));
			}
			tempCalGrossAmount = tempCalGrossAmount.add(shoppingCartDataTableBean.getLocalTranxAmount() == null ? new BigDecimal(0) : shoppingCartDataTableBean.getLocalTranxAmount());
			setCalGrossAmount(tempCalGrossAmount);
			tempCalNetAmountPaid = tempCalNetAmountPaid.add(shoppingCartDataTableBean.getLocalNextTranxAmount() == null ? new BigDecimal(0) : shoppingCartDataTableBean.getLocalNextTranxAmount());
			setCalNetAmountPaid(tempCalNetAmountPaid);
			shoppingCartDataTableBean.setDeleteStatus(Boolean.FALSE);
			lstselectedrecord.add(shoppingCartDataTableBean);
			lstApplDocNumber.add(shoppingCartDataTableBean.getDocumentNo());
		} else if (shoppingCartDataTableBean.getSelectedrecord() == false) {
			if (shoppingCartDataTableBean.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
				remittanceNo = remittanceNo.subtract(new BigDecimal(1));
			} else if (shoppingCartDataTableBean.getApplicationType().equalsIgnoreCase(Constants.FCSale)) {
				fcsaleNo = fcsaleNo.subtract(new BigDecimal(1));
			}
			tempCalGrossAmount = tempCalGrossAmount.subtract(shoppingCartDataTableBean.getLocalTranxAmount() == null ? new BigDecimal(0) : shoppingCartDataTableBean.getLocalTranxAmount());
			setCalGrossAmount(tempCalGrossAmount);
			tempCalNetAmountPaid = tempCalNetAmountPaid.subtract(shoppingCartDataTableBean.getLocalNextTranxAmount() == null ? new BigDecimal(0) : shoppingCartDataTableBean.getLocalNextTranxAmount());
			setCalNetAmountPaid(tempCalNetAmountPaid);
			shoppingCartDataTableBean.setDeleteStatus(Boolean.TRUE);
			for (int i = 0; i < lstselectedrecord.size(); i++) {
				ShoppingCartDataTableBean shoppingCartDataTableBean1 = lstselectedrecord.get(i);
				if (shoppingCartDataTableBean1.getApplicationDetailsId().compareTo(shoppingCartDataTableBean.getApplicationDetailsId()) == 0) {
					lstselectedrecord.remove(i);
				}
			}
			lstApplDocNumber.remove(shoppingCartDataTableBean.getDocumentNo());
		} else {
			System.out.println("checkbox not working");
		}
		if (lstselectedrecord.size() == 0) {
			setCalGrossAmount(null);
			setCalNetAmountPaid(null);
			setShoppingcartExchangeRate(null);
			setBooRenderMultiDocNum(false);
			setBooRenderSingleDocNum(true);
			setApplicationDocNum(null);
			lstApplDocNumber.clear();
		} else if (lstselectedrecord.size() == 1) {
			setShoppingcartExchangeRate(lstselectedrecord.get(0).getExchangeRateApplied());
			setBooRenderSingleDocNum(true);
			setBooRenderMultiDocNum(false);
			setApplicationDocNum(lstselectedrecord.get(0).getDocumentNo());
		} else {
			setShoppingcartExchangeRate(null);
			setBooRenderMultiDocNum(true);
			setBooRenderSingleDocNum(false);
			setApplicationDocNum(null);
			setCashRounding(null);
		}
		// to call round function while click
		if (getCalNetAmountPaid() != null) {
			setDummiTotalGrossAmount(getCalGrossAmount());
			setDummiTotalNetAmount(getCalNetAmountPaid());
			roundingShoppingCartList();
		} else {
			setCashRounding(null);
			setApplicationDocNum(null);
			setCalGrossAmount(null);
			setCalNetAmountPaid(null);
			setDummiTotalGrossAmount(null);
			setDummiTotalNetAmount(null);
			setBooShowCashRoundingPanel(false);
			lstApplDocNumber.clear();
		}
	}

	// blocked for not using on true condition dependent on special customer
	// table size
	public CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> getSelectedSplDeal() {
		return selectedSplDeal;
	}

	public void setSelectedSplDeal(CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> selectedSplDeal) {
		this.selectedSplDeal = selectedSplDeal;
	}

	public BigDecimal getCountSplCust() {
		return countSplCust;
	}

	public void setCountSplCust(BigDecimal countSplCust) {
		this.countSplCust = countSplCust;
	}

	public BigDecimal getRemitAmountSplCust() {
		return remitAmountSplCust;
	}

	public void setRemitAmountSplCust(BigDecimal remitAmountSplCust) {
		this.remitAmountSplCust = remitAmountSplCust;
	}

	public void changeSpotRate(PersonalRemittanceCalFCAmountDataTable personalRemObj) throws ParseException {
		try {
			setVisible(false);
			if (personalRemObj.getSelect()) {
				setCountSplCust(getCountSplCust().add(BigDecimal.ONE));
				setRemitAmountSplCust(getRemitAmountSplCust().add(personalRemObj.getAmount()));
				selectedSplDeal.add(personalRemObj);
			} else {
				setCountSplCust(getCountSplCust().subtract(BigDecimal.ONE));
				setRemitAmountSplCust(getRemitAmountSplCust().subtract(personalRemObj.getAmount()));
				if (selectedSplDeal.size() != 0) {
					for (int i = 0; i < selectedSplDeal.size(); i++) {
						PersonalRemittanceCalFCAmountDataTable lstSpl = selectedSplDeal.get(i);
						if (personalRemObj.getSpecialCustomerPrimaryKey().compareTo(lstSpl.getSpecialCustomerPrimaryKey()) == 0) {
							selectedSplDeal.remove(i);
						}
					}
				}
			}
			System.out.println("Count : " + getCountSplCust());
			setAmountToRemit(getRemitAmountSplCust());
			if (getCountSplCust().compareTo(BigDecimal.ZERO) == 0) {
				setSpecialRateRef(Constants.No);
				setCurrency(new BigDecimal(sessionmanage.getCurrencyId()));
			} else {
				setSpecialRateRef(Constants.Yes);
				setCurrency(personalRemObj.getCurrencyId());
			}
			/*
			 * if(personalRemObj.getSelect().equals(true)){
			 * //checkAvailableBalance(personalRemObj); setSpecialRateRef("Y");
			 * //setSpotRate("N"); setDisableSpotRatePanel(true);
			 * //setNextRender(true); setMarqueeRender(false);
			 * setCurrency(personalRemObj.getCurrencyId()); }else{
			 * setSpecialRateRef("N"); //setSpotRate(null); //setNextRender(false);
			 * setDisableSpotRatePanel(false); }
			 */
		} catch (Exception e) {
			setErrmsg("Exception occurred "+e);
			setVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	/*
	 * @PostConstruct public void createforeignCurrencySaleBean() {
	 * foreignCurrencySaleBean = new ForeignCurrencySaleBean<T>(); }
	 */
	public void clearData() {
		selectedSplDeal.clear();
		setDisableSpotRatePanel(false);
		setModeOfPayment(null);
		setBooSpecialCusFCCalDataTable(false);
		// setCurrency(null);
		// setCurrencyName(null);
		setSpotRate(null);
		setAmountToRemit(null);
		setSpecialRateRef(null);
		setAvailLoyaltyPoints(null);
		setChargesOverseas(null);
		setRoutingBank(null);
		setRemitMode(null);
		setRoutingBranch(null);
		setCashRounding(null);
		setRoutingCountry(null);
		setMarqueeRender(false);
		setAmlMessageThree(null);
		setAmlMessageOne(null);
		setAmlMessageTwo(null);
		setAmlAuthorizedBy(null);
		setAmlRemarks(null);
		setDocumentNo(null);
		setForiegnCurrency(null);
		setBooShowCashRoundingPanel(false);
	}

	public List<CountryMasterDesc> getNationalityList() {
		return nationalityList;// generalService.getNationalityList(sessionmanage.getLanguageId());
	}

	public void setNationalityList(List<CountryMasterDesc> nationalityList) {
		this.nationalityList = nationalityList;
	}

	// Calling FC Sale functionality by Ram Mohan 22/Jan/2015
	/*
	 * @Autowired ForeignCurrencySaleBean<T> foreignCurrencySaleBean;
	 */
	public void callToFCSale() {
		log.info("Entering into callToFCSale method");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			HttpSession session = sessionmanage.getSession();
			session.setAttribute("customerNo", getCustomerNo());
			session.setAttribute("IdNumber", getIdNumber());
			session.setAttribute("remit", "C");
			context.redirect("../foreigncurrency/foreigncurrencysale.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect:" + e);
		}
	}

	public CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> getColdatatablevalues() {
		return coldatatablevalues;
	}

	public void setColdatatablevalues(CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
		this.coldatatablevalues = coldatatablevalues;
	}

	public Boolean getBooRenderColDebitCard() {
		return booRenderColDebitCard;
	}

	public void setBooRenderColDebitCard(Boolean booRenderColDebitCard) {
		this.booRenderColDebitCard = booRenderColDebitCard;
	}

	public Boolean getBooRenderCollection() {
		return booRenderCollection;
	}

	public void setBooRenderCollection(Boolean booRenderCollection) {
		this.booRenderCollection = booRenderCollection;
	}

	public BigDecimal getColremittanceNo() {
		return colremittanceNo;
	}

	public void setColremittanceNo(BigDecimal colremittanceNo) {
		this.colremittanceNo = colremittanceNo;
	}

	public BigDecimal getColfcsaleNo() {
		return colfcsaleNo;
	}

	public void setColfcsaleNo(BigDecimal colfcsaleNo) {
		this.colfcsaleNo = colfcsaleNo;
	}

	public BigDecimal getColpaymentmodeId() {
		return colpaymentmodeId;
	}

	public void setColpaymentmodeId(BigDecimal colpaymentmodeId) {
		this.colpaymentmodeId = colpaymentmodeId;
	}

	public BigDecimal getColCash() {
		return colCash;
	}

	public void setColCash(BigDecimal colCash) {
		this.colCash = colCash;
	}

	public BigDecimal getColBankid() {
		return colBankid;
	}

	public void setColBankid(BigDecimal colBankid) {
		this.colBankid = colBankid;
	}

	public String getColCurrency() {
		return colCurrency;
	}

	public void setColCurrency(String colCurrency) {
		this.colCurrency = colCurrency;
	}

	public BigDecimal getColCardNo() {
		return colCardNo;
	}

	public void setColCardNo(BigDecimal colCardNo) {
		this.colCardNo = colCardNo;
	}

	public BigDecimal getColCardNoLength() {
		return colCardNoLength;
	}

	public void setColCardNoLength(BigDecimal colCardNoLength) {
		this.colCardNoLength = colCardNoLength;
	}

	public String getColNameofCard() {
		return colNameofCard;
	}

	public void setColNameofCard(String colNameofCard) {
		this.colNameofCard = colNameofCard;
	}

	public String getColAuthorizedby() {
		return colAuthorizedby;
	}

	public void setColAuthorizedby(String colAuthorizedby) {
		this.colAuthorizedby = colAuthorizedby;
	}

	public String getColpassword() {
		return colpassword;
	}

	public void setColpassword(String colpassword) {
		this.colpassword = colpassword;
	}

	public String getCyberPassword() {
		return cyberPassword;
	}

	public void setCyberPassword(String cyberPassword) {
		this.cyberPassword = cyberPassword;
	}

	public String getColpaymentmodeName() {
		return colpaymentmodeName;
	}

	public void setColpaymentmodeName(String colpaymentmodeName) {
		this.colpaymentmodeName = colpaymentmodeName;
	}

	public Boolean getBooRendercollectiondatatable() {
		return booRendercollectiondatatable;
	}

	public void setBooRendercollectiondatatable(Boolean booRendercollectiondatatable) {
		this.booRendercollectiondatatable = booRendercollectiondatatable;
	}

	// private List<BankApplicability> customerBankList = new
	// ArrayList<BankApplicability>();
	public String getColApprovalNo() {
		return colApprovalNo;
	}

	public void setColApprovalNo(String colApprovalNo) {
		this.colApprovalNo = colApprovalNo;
	}

	

	List<CustomerBank> localBankListinCollection = new ArrayList<CustomerBank>();

	public void setLocalBankListinCollection(List<CustomerBank> localBankListinCollection) {
		this.localBankListinCollection = localBankListinCollection;
	}

	public boolean isBooRenderSingleDebit() {
		return booRenderSingleDebit;
	}

	public void setBooRenderSingleDebit(boolean booRenderSingleDebit) {
		this.booRenderSingleDebit = booRenderSingleDebit;
	}

	public boolean isBooRenderMulDebit() {
		return booRenderMulDebit;
	}

	public void setBooRenderMulDebit(boolean booRenderMulDebit) {
		this.booRenderMulDebit = booRenderMulDebit;
	}



	public void populateCustKnetCardDetails() {
		for (CustomerBank customerBank : lstDebitCard) {
			if (customerBank.getDebitCard().equalsIgnoreCase(getColCardNo().toPlainString())) {
				if (customerBank.getAuthorizedBy() != null) {
					List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
					//List<Employee> localEmpllist = generalService.getEmployeelist(sessionmanage.getCountryId(), new BigDecimal(sessionmanage.getBranchId()), new BigDecimal(sessionmanage.getRoleId()));
					setEmpllist(localEmpllist);
					setColAuthorizedby(customerBank.getAuthorizedBy());
					setColpassword(null);
					setBooAuthozed(true);
					break;
				} else {
					setColNameofCard(customerBank.getDebitCardName());
					setColAuthorizedby(null);
					setColpassword(null);
					setBooAuthozed(false);
				}
			}
		}
	}

	public BigDecimal getPopulatedDebitCardNumber() {
		return populatedDebitCardNumber;
	}

	public void setPopulatedDebitCardNumber(BigDecimal populatedDebitCardNumber) {
		this.populatedDebitCardNumber = populatedDebitCardNumber;
	}

	public void populateCustomerDetails() {
		localBankListinCollection.clear();
		lstDebitCard.clear();
		setColAuthorizedby(null);
		setColCardNo(null);
		setColCardNoLength(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		setColApprovalNo(null);
		if (getColBankid() != null) {
			localBankListinCollection = icustomerBankService.customerBanks(getCustomerNo(), getColBankid());
			if (localBankListinCollection.size() != 0) {
				CustomerBank cardlength = localBankListinCollection.get(0);
				List<BankDebitCardLengthViewModel> lstCard = icustomerBankService.bankCardLengthView(cardlength.getBankCode());
				if (lstCard.size() != 0) {
					setColCardNoLength(lstCard.get(0).getBankLength());
					if (localBankListinCollection.size() == 1) {
						for (CustomerBank customerBank : localBankListinCollection) {
							if (customerBank.getFsBankMaster().getBankId().equals(getColBankid())) {
								setPopulatedDebitCardNumber(new BigDecimal(encryptionDescriptionService.getDECrypted(customerBank.getDebitCardName(), customerBank.getDebitCard())));
								setColCardNo(new BigDecimal(encryptionDescriptionService.getDECrypted(customerBank.getDebitCardName(), customerBank.getDebitCard())));
								setColNameofCard(customerBank.getDebitCardName());
								if (customerBank.getAuthorizedBy() != null) {
								//	List<Employee> localEmpllist = generalService.getEmployeelist(sessionmanage.getCountryId(), new BigDecimal(sessionmanage.getBranchId()), new BigDecimal(sessionmanage.getRoleId()));
									List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
									setEmpllist(localEmpllist);
									setColAuthorizedby(customerBank.getAuthorizedBy());
									setColpassword(null);
									setBooAuthozed(true);
									break;
								} else {
									setColAuthorizedby(null);
									setColpassword(null);
									setBooAuthozed(false);
								}
							} else {
								setColAuthorizedby(null);
								setColpassword(null);
								setBooAuthozed(false);
							}
						}
						setBooRenderSingleDebit(true);
						setBooRenderMulDebit(false);
						lstDebitCard.clear();
					} else {
						setBooRenderSingleDebit(false);
						setBooRenderMulDebit(true);
						if (localBankListinCollection.size() != 0) {
							for (CustomerBank lstDebitcrd : localBankListinCollection) {
								CustomerBank lstofDebitCard = new CustomerBank();
								lstofDebitCard.setDebitCard(encryptionDescriptionService.getDECrypted(lstDebitcrd.getDebitCardName(), lstDebitcrd.getDebitCard()));
								lstDebitCard.add(lstofDebitCard);
							}
						}
					}
				} else {
					RequestContext.getCurrentInstance().execute("bankDebitCardLenErr.show();");
				}
			} else {
				String bankCode = generalService.getBankCode(getColBankid());
				if (bankCode != null) {
					List<BankDebitCardLengthViewModel> lstCard = icustomerBankService.bankCardLengthView(bankCode);
					if (lstCard.size() != 0) {
						setColCardNoLength(lstCard.get(0).getBankLength());
						lstDebitCard.clear();
						setColAuthorizedby(null);
						setColCardNo(null);
						setPopulatedDebitCardNumber(null);
						setColNameofCard(null);
						setColpassword(null);
						setColApprovalNo(null);
						setBooAuthozed(false);
						setBooRenderSingleDebit(true);
						setBooRenderMulDebit(false);
					} else {
						RequestContext.getCurrentInstance().execute("bankDebitCardLenErr.show();");
					}
				}
			}
		} else {
			lstDebitCard.clear();
			setColAuthorizedby(null);
			setColCardNo(null);
			setColCardNoLength(null);
			setPopulatedDebitCardNumber(null);
			setColNameofCard(null);
			setColpassword(null);
			setColApprovalNo(null);
			setBooAuthozed(false);
			setBooRenderSingleDebit(true);
			setBooRenderMulDebit(false);
		}
	}

	public void checkingCardNumberLength() {
		if (getColCardNo() != null && getColCardNoLength() != null) {
			if ((getColCardNo().toString()).length() != getColCardNoLength().intValue()) {
				setColCardNo(null);
				RequestContext.getCurrentInstance().execute("misMatchBankCardLength.show();");
			}
		}
	}

	// NAG CODE END 27/01/2015
	// to get the Collection Panel checking check box condition in datatable
	// panel
	public void nextpaneltoCollection() {
		  lstFetchAllPayMode.clear();
		  toFetchPaymentDetails();  
		setColremittanceNo(remittanceNo);
		setColfcsaleNo(fcsaleNo);
		clearingDetailAfterAdding();
		lstRefundData.clear();
		lstData.clear();
		if (getBoorefundPaymentDetails()) {
			nextpaneltoPaymentDetails();
		} else if (lstselectedrecord.size() > 0) {
			if(isAmlRecheckAuthentication()){
				nextAmlCheckAunticationPanel();
			}else{
				// checking AML check of  beneficiary - Multiple Customer For The Same Beneficiary Exceeds Limit
				boolean checkSameBeneFromMulCustomer = checkingSameBeneficiaryExceedsLimit();

				if(checkSameBeneFromMulCustomer){
					setBooShowAuthenticationPanel(true);
					if(getAmlAuntenticationMessageOne() != null)
					{
						setAmlAuntenticationAuthorizedBy(null);
						setAmlAuntenticationAuthorizedPassword(null);
						setAmlAuntenticationRemarks(null);
						List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
						setEmplAutenticationlist(localEmpllist);
					}
				}else{
					collectionPaymentModePanel();
				}
			}
		} else {
			RequestContext.getCurrentInstance().execute("checkboxchecking.show();");
		}
	}

	// change by payment mode - cash and debit card
	public void changeofPaymentMode() {
		List<PaymentModeDesc> lstofPayment = ipaymentService.getPaymentDescLangList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		//Boolean checkCash = false;
		String paymentModedesc = null;
		String paymentModeCode = null;
		setBooColApprovalNo(false);
		if (lstofPayment.size() != 0) {
			for (PaymentModeDesc paymentModeDesc : lstofPayment) {
				if ((getColpaymentmodeId() == null ? BigDecimal.ZERO : getColpaymentmodeId()).compareTo(paymentModeDesc.getPaymentMode().getPaymentModeId()) == 0) {
					paymentModedesc = paymentModeDesc.getLocalPaymentName();
					paymentModeCode = paymentModeDesc.getPaymentMode().getPaymentCode();
					setColpaymentmodeName(paymentModedesc);
					setColpaymentmodeCode(paymentModeCode);
					break;
				} else {
					//paymentModedesc = null;
					setColpaymentmodeName(null);
					setColpaymentmodeCode(null);
				}
			}

			if(getColpaymentmodeCode() != null){
				List<PaymentMode> paymentModedetails = ipaymentService.getPaymentCheck(getColpaymentmodeCode());

				if (paymentModedetails.size() != 0) {
					// payment mode bank variables
					setColBankCode(null);
					setColCardNo(null);
					setPopulatedDebitCardNumber(null);
					setColCash(null);
					setColAuthorizedby(null);
					setColpassword(null);
					setColNameofCard(null);
					setColApprovalNo(null);

					// payment mode Cheque variables
					setColchequebankCode(null);
					setColChequeRef(null);
					setColChequeDate(null);
					setColChequeApprovalNo(null);

					if(getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)){
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
					}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)){
						setBooRenderColCheque(false);
						setBooRenderColDebitCard(true);
					}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(true);
					}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)){
						setColpaymentmodeId(null);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						RequestContext.getCurrentInstance().execute("checkPaymentModeService.show();");
						return;
					}else{
						setColpaymentmodeId(null);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						System.out.println("Payment Mode Newly added");
						RequestContext.getCurrentInstance().execute("checkPaymentModeService.show();");
						return;
					}

				}
			}else{
				setBooRenderColDebitCard(false);
				setBooRenderColCheque(false);
			}

			/*if (getColpaymentmodeName() != null && getColpaymentmodeName().equalsIgnoreCase(Constants.CHEQUENAME)) {
				RequestContext.getCurrentInstance().execute("chequeMsg.show();");
				return;
			}*/

			// this work only for cash and knet so blocked
			/*BigDecimal paymentModeId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			if (paymentModeId != null) {
				if ((getColpaymentmodeId() == null ? new BigDecimal(0) : getColpaymentmodeId()).compareTo(paymentModeId) == 0) {
					checkCash = true;
				} else {
					checkCash = false;
				}
				if (checkCash) {
					setBooRenderColDebitCard(false);
					setColBankCode(null);
					setColCardNo(null);
					setPopulatedDebitCardNumber(null);
					setColCash(null);
					setColAuthorizedby(null);
					setColpassword(null);
					setColNameofCard(null);
					setColApprovalNo(null);
				} else {
					setColCash(null);
					setBooRenderColDebitCard(true);
				}
			}*/
		}

	}

	public BigDecimal getTempCash() {
		return tempCash;
	}

	public void setTempCash(BigDecimal tempCash) {
		this.tempCash = tempCash;
	}

	// to add values to data table in collection
	public void calculatingNetAmountDT() {
		BigDecimal paymentModeCashId =null;

		if(getChequeSelectionCheck()!=null && getChequeSelectionCheck().equals(true)){
			paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CHEQUENAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
		}else{
			paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME, new BigDecimal(sessionmanage.isExists("languageId") ? sessionmanage.getSessionValue("languageId") : "1"));
		}
		if (paymentModeCashId != null) {
			boolean flag = true;
			setBooRendercollectiondatatable(true);
			setBoRenderTotalAmountCalPanel(true);
			if (coldatatablevalues.size() != 0) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					i = 0;
					if (collectionlst.getColpaymentmodeIDtypeDT().compareTo((getColpaymentmodeId() == null ? new BigDecimal(0) : getColpaymentmodeId())) == 0) {
						if (collectionlst.getColpaymentmodeIDtypeDT().compareTo(paymentModeCashId) == 0) {
							clearingDetailAfterAdding();
							RequestContext.getCurrentInstance().execute("cashexists.show();");
							flag = false;
							break;
						} else {
							if (collectionlst.getColBankIdDT().compareTo(getColBankid()) == 0) {
								if (collectionlst.getColCardNumberDT().compareTo(getColCardNo()) == 0) {
									clearingDetailAfterAdding();
									RequestContext.getCurrentInstance().execute("bankexists.show();");
									flag = false;
									break;
								} else {
									i = 1;
								}
							} else {
								i = 1;
							}
						}
					} else {
						i = 1;
					}
				}
				if (i == 1) {
					addPaymentModerecord();
				}
			} else {
				setToalUsedAmount(null);
				addPaymentModerecord();
			}
			if (coldatatablevalues.size() > 0 && flag == true) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					// totalUamount=totalUamount+collectionlst.getColAmountDT().intValue();
					totalUamount = totalUamount.add(collectionlst.getColAmountDT());
				}
				setToalUsedAmount(totalUamount);
			}
			clearingDetailAfterAdding();
		}
	}

	// adding to datatable list in payment mode
	public void addPaymentModerecord() {
		BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,	new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			totalUamount = new BigDecimal(0);
			PersonalRemittanceCollectionDataTable personalcollectionDT = new PersonalRemittanceCollectionDataTable();
			personalcollectionDT.setColpaymentmodeIDtypeDT(getColpaymentmodeId());
			personalcollectionDT.setColpaymentmodetypeDT(getColpaymentmodeName());
			personalcollectionDT.setColpaymentmodeCode(getColpaymentmodeCode());
			if (getColBankCode() != null || getColchequebankCode() != null) {
				if(localbankListForBankCode.size() != 0){
					ViewBankDetails lstDetaiks = localbankListForBankCode.get(0);
					personalcollectionDT.setColBankIdDT(lstDetaiks.getChequeBankId());
					personalcollectionDT.setColbankNameDT(lstDetaiks.getBankFullName());

					if(getColChequeRef() != null){
						personalcollectionDT.setColBankCodeDT(getColchequebankCode());
						personalcollectionDT.setColchequeRefNo(getColChequeRef());
						personalcollectionDT.setColchequeDate(getColChequeDate());
						personalcollectionDT.setColApprovalNo(getColChequeApprovalNo());
					}else{
						personalcollectionDT.setColBankCodeDT(getColBankCode());
						personalcollectionDT.setColCardNumberDT(getColCardNo());
						personalcollectionDT.setColNameofCardDT(getColNameofCard());
						personalcollectionDT.setColAuthorizedByDT(getColAuthorizedby());
						personalcollectionDT.setColApprovalNo(getColApprovalNo());
						personalcollectionDT.setKnetReceiptDT(getKnetIposReceipt());
						personalcollectionDT.setKnetTransIdDT(getKnetTranId());
						personalcollectionDT.setKneRceiptTimeDT(getKnetReceiptDate());
						personalcollectionDT.setBooDisbale(false);
					}
				}
			}
			personalcollectionDT.setColAmountDT(GetRound.roundBigDecimal(getColCash(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			if (getColpaymentmodeId().compareTo(paymentModeCashId) == 0) {
				setTempCash(GetRound.roundBigDecimal(getColCash(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			}
			coldatatablevalues.add(personalcollectionDT);
		}

		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		lstDebitCard.clear();
		setColCardNo(null);
	}

	// after adding data to datatable and then reseting
	public void clearingDetailAfterAdding() {
		setColpaymentmodeId(null);
		setColpaymentmodeName(null);
		setColpaymentmodeCode(null);
		setColBankCode(null);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
		setColCash(null);
		setColAuthorizedby(null);
		setColpassword(null);
		setColNameofCard(null);
		setBooAuthozed(false);
		setColApprovalNo(null);
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		//clear cheque details
		// payment mode Cheque variables
		setColchequebankCode(null);
		setColChequeRef(null);
		setColChequeDate(null);
		setColChequeApprovalNo(null);

		setDigitalSignature(null);
		setSignatureSpecimen(null);

		lstDebitCard.clear();
		changeofPaymentMode();

	}

	// to remove details from data table after adding
	public void deletePaymentModeRecords(PersonalRemittanceCollectionDataTable collectionDt) {
		if (coldatatablevalues.size() > 0) {
			// subtractedAmount =getToalUsedAmount().intValue() -
			// collectionDt.getColAmountDT().intValue();
			subtractedAmount = getToalUsedAmount().subtract(collectionDt.getColAmountDT());
			setToalUsedAmount(subtractedAmount);
		} else {
			setToalUsedAmount(null);
		}
		coldatatablevalues.remove(collectionDt);
		if (coldatatablevalues.size() != 0) {
			setBooRendercollectiondatatable(true);
			setBoRenderTotalAmountCalPanel(true);
		} else {
			setBooRendercollectiondatatable(false);
			setBoRenderTotalAmountCalPanel(false);
			clearingDetailAfterAdding();
		}
	}

	// new panel adding to get payment details -- panel 7 Denomination and
	// payment details
	public List<PersonalRemittanceCollectionDataTable> getCashdenominationlst() {
		return cashdenominationlst;
	}

	public void setCashdenominationlst(List<PersonalRemittanceCollectionDataTable> cashdenominationlst) {
		this.cashdenominationlst = cashdenominationlst;
	}

	public Boolean getBooRenderPaymentDetails() {
		return booRenderPaymentDetails;
	}

	public void setBooRenderPaymentDetails(Boolean booRenderPaymentDetails) {
		this.booRenderPaymentDetails = booRenderPaymentDetails;
	}

	public Boolean getBooRendercashdenomination() {
		return booRendercashdenomination;
	}

	public void setBooRendercashdenomination(Boolean booRendercashdenomination) {
		this.booRendercashdenomination = booRendercashdenomination;
	}

	// to render panel 7 - payment details denomination
	public void nextpaneltoPaymentDetails() {
		setCheckEmailForReport(false);
		setRenderEmailForReport(false);
		setEmailToSendReport(null);
		if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) >= 0) {

			allPanelOff();
			setBooRenderPaymentDetails(true);
			setPaymentDeatailsPanel(true);

			if(getTempCash() != null){
				setCashAmount(GetRound.roundBigDecimal(getTempCash(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			}
			// to render the denomination in payment details panel -7
			renderingDenominationDT();
			denaMinLstData();
			setPaymentDetailsremark(null);
		} else {
			RequestContext.getCurrentInstance().execute("amountmatch.show();");
		}
	}

	// to render panel 8 - Refund Denomination details
	public void nextpaneltoRefundDenomination() {
		// setBooRenderBenificarySearchPanel(false);
		setBooSpecialCusFCCalDataTable(false);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBenificaryStatusPanel(false);
		setBooRenderIndBenificaryStatusPanel(false);
		setBooRenderLayaltyServicePanel(false);
		setBooRenderNonIndBenificaryStatusPanel(false);
		setBooRenderRemittanceServicePanel(false);
		setBooRenderTypeOfServicePanel(false);
		setAmlboo(false);
		setBoorenderlastpanel(false);
		setBooRenderPaymentDetails(true);
		setBooRenderCollection(false);
		// setPaymentDeatailsPanel(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(true);
		setPaymentDetailsremark(null);
		refundDenominationData();
	}

	// to render denomination based on datatable added
	public void renderingDenominationDT() {
		if (coldatatablevalues.size() != 0) {
			BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,new BigDecimal(
					sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"));
			if (paymentModeCashId != null) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					if (collectionlst.getColpaymentmodeIDtypeDT().compareTo(paymentModeCashId) == 0) {
						setDenomtotalCashreceived(getColamountKWD());
						setBooRendercashdenomination(true);
						setCollectedAmount(GetRound.roundBigDecimal(BigDecimal.ZERO,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
						break;
					} else {
						setCollectedAmount(getToalUsedAmount());
						setDenomtotalCashreceived(getColamountKWD());
						setBooRendercashdenomination(false);
					}
				}
			}
			BigDecimal totalCardCash = BigDecimal.ZERO;
			BigDecimal totalCreditCardCash = BigDecimal.ZERO;
			for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
				if(collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)){
					totalCardCash = totalCardCash.add(collectionlst.getColAmountDT());
				}else if(collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
					totalCreditCardCash = totalCreditCardCash.add(collectionlst.getColAmountDT());
				}
			}
			setKnetValue(GetRound.roundBigDecimal(totalCardCash,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setChequeValue(GetRound.roundBigDecimal(totalCreditCardCash,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
		}
	}

	public List<BankApplicability> getLstoflocalbank() {
		return lstoflocalbank;
	}

	public void setLstoflocalbank(List<BankApplicability> lstoflocalbank) {
		this.lstoflocalbank = lstoflocalbank;
	}

	/*
	 * // to get all Local Bank list public List<BankApplicability>
	 * getLocalBankListinCollection() {
	 * 
	 * lstoflocalbank = generalService.getLocalBankList(sessionmanage
	 * .getCountryId());
	 * 
	 * return lstoflocalbank; }
	 */
	public BigDecimal getColamountKWD() {
		return colamountKWD;
	}

	public void setColamountKWD(BigDecimal colamountKWD) {
		this.colamountKWD = colamountKWD;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	// calculation of cash while entering
	public void checkcashcollection() {
		checkKnetAmount = false;
		if (coldatatablevalues.size() != 0) {
			colamountKWD = new BigDecimal(0);
			for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
				colamountKWD = colamountKWD.add(collectionlst.getColAmountDT());
				setColamountKWD(colamountKWD);
			}
			checkingKnetExtraAmount();
		} else {
			checkingKnetExtraAmount();
		}
	}
	
	// Knet 5% calculation
		public void checkingKnetExtraAmount(){
			BigDecimal totalAmount = BigDecimal.ZERO;
			setErrcolCashExistsLimit(null);
			if(getColpaymentmodeCode()!= null){
				if(getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)){
					setColamountKWD(getColCash());
				}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)){
					BigDecimal colKnetAmount = BigDecimal.ZERO;
					for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
						if(collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)){
							colKnetAmount = colKnetAmount.add(collectionlst.getColAmountDT());
						}
					}
					colKnetAmount = colKnetAmount.add(getColCash());
					BigDecimal percentage = new BigDecimal(5).divide(new BigDecimal(100));
					BigDecimal percentageAmount = percentage.multiply(getCalNetAmountPaid());
					totalAmount = GetRound.roundBigDecimal(percentageAmount.add(getCalNetAmountPaid()), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));

					if (colKnetAmount.compareTo(totalAmount) <= 0) {
						setColamountKWD(getColCash());
					} else {
						setColCash(null);
						setErrcolCashExistsLimit(totalAmount);
						RequestContext.getCurrentInstance().execute("amountgreater.show();");
						checkKnetAmount=true;
					}
				}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
					setColamountKWD(getColCash());
				}else{
					System.out.println("Other Payment Mode Selected");
				}
			}else{
				setColamountKWD(getColCash());
			}



			/*String paymentDesc = ipaymentService.paymentModeDescription(getColpaymentmodeId(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			if (paymentDesc != null) {
				if (paymentDesc.equalsIgnoreCase(Constants.CASHNAME)) {
					setColamountKWD(getColCash());
				} else {
					BigDecimal percentage = new BigDecimal(5).divide(new BigDecimal(100));
					BigDecimal percentageAmount = percentage.multiply(getCalNetAmountPaid());
					BigDecimal totalAmount = percentageAmount.add(getCalNetAmountPaid());
					if (getColCash().compareTo(totalAmount) <= 0) {
						setColamountKWD(getColCash());
					} else {
						setColCash(null);
						RequestContext.getCurrentInstance().execute("amountgreater.show();");
					}
				}
			}*/

		}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<ForeignLocalCurrencyDataTable> lstData) {
		this.lstData = lstData;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstRefundData() {
		return lstRefundData;
	}

	public void setLstRefundData(ArrayList<ForeignLocalCurrencyDataTable> lstRefundData) {
		this.lstRefundData = lstRefundData;
	}

	private void denaMinLstData() {
		lstData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		/* Checking that it's first time or not, first time list size will be 0 */
		//	double sAmount = 0;
		localLstData.clear();
		if (localLstData.size() == 0) {
			/* Responsible to show serial number in datatable */
			//	int i = 0;
			// Responsible to hold each row in different bean object of datatable

			//	ForeignLocalCurrencyDataTable item = null;
			/*List<Stock> dataFromDb = foreignLocalCurrencyDenominationService
					.getLocalCurrencyDenominationFromStock(
							sessionStateManage.getCountryId(),
							sessionStateManage.getUserName(),
							sessionStateManage.getBranchId(),
							sessionStateManage.getCompanyId(),
							sessionStateManage.getCurrencyId());*/


			List<CurrencyWiseDenomination> currencyWiseDenolst = iPersonalRemittanceService.getCurrencyDenominations(new BigDecimal(sessionStateManage.getCurrencyId()),sessionStateManage.getCountryId());

			/* putting the value in list to show in datatable */
			/*for (Stock element : dataFromDb) {
				int stock = element.getOpenQuantity()
						+ element.getPurchaseQuantity()
						+ element.getReceivedQuantity()
						- (element.getSaleQuantity() + element
								.getTransactionQuantity());
				String qty = null;
				double count = 0, totalNotes = 0;
				// double sAmount = 0;
				double denamination = 0;
				String amount = null;
				Double result = 0.0;
				int cal = 0;
				//if (stock >0) { //
					if (stock >= 0) { //	

					denamination = Double.parseDouble(element.getDenominationId().getDenominationAmount().toString());
					if (denamination <= sAmount) {
						count = sAmount / denamination;
					}
					totalNotes = totalNotes + count;
					sAmount = sAmount % denamination;
					if (count != 0) {
						qty = new Integer((int) Math.floor(count)).toString();
						cal = new Integer((int) Math.floor(count));
						result = cal * denamination;
						amount = new Integer((int) Math.ceil(result)).toString();
					} else {
						qty = "";
						amount = "0";
					}
					item = new ForeignLocalCurrencyDataTable(++i, element.getDenominationId().getDenominationAmount(), qty,amount, stock, element.getStockId(), element
							.getDenominationId().getDenominationId(),element.getDenominationId().getExCurrencyMaster().getCurrencyId(), element
							.getDenominationId().getDenominationDesc(),element.getDenominationId().getDenominationAmount());
					localLstData.add(item);
				}
			}
			 */
			int serial = 1;
			for(CurrencyWiseDenomination currencyDenObj:currencyWiseDenolst){
				ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
				forLocalCurrencyDtObj.setSerial(serial);
				forLocalCurrencyDtObj.setItem(currencyDenObj.getDenominationAmount());
				forLocalCurrencyDtObj.setQty("");
				forLocalCurrencyDtObj.setPrice("");
				forLocalCurrencyDtObj.setDenominationId(currencyDenObj.getDenominationId());
				forLocalCurrencyDtObj.setCurrencyId(currencyDenObj.getExCurrencyMaster().getCurrencyId());
				forLocalCurrencyDtObj.setDenominationDesc(currencyDenObj.getDenominationDesc());
				forLocalCurrencyDtObj.setDenominationAmount(currencyDenObj.getDenominationAmount());

				localLstData.add(forLocalCurrencyDtObj);
				serial++;
			}


		}



		/* Responsible to keep sum of total amount of cash entered */
		int totalSum = 0;
		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : localLstData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum + Integer.parseInt(element.getPrice());
			}
		}
		System.out.println(totalSum);
		setDenomtotalCash(GetRound.roundBigDecimal(new BigDecimal(totalSum),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
		//setCollectedAmount(getDenomtotalCash());
		setPayNetPaidAmount(getCalNetAmountPaid());
		setPayPaidAmount(GetRound.roundBigDecimal(getToalUsedAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
		setPayRefund(getPayPaidAmount().subtract(getPayNetPaidAmount()));
		if (getPayRefund().compareTo(BigDecimal.ZERO) == 0) {
			setNextOrSaveButton(Constants.Save);
			setSavetimeReportEmailCheck(true);
		} else {
			setNextOrSaveButton(Constants.Next);
			setSavetimeReportEmailCheck(false);
		}
		setLstData(localLstData);  //Adding denomication here
		if(localLstData.size() != 0){
			setDenominationchecking(Constants.DenominationAvailable);
		}else{
			setDenominationchecking(Constants.DenominationNotAvailable);
		}
	}

	public void onCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {
		// if (getDenomtotalCash().compareTo(BigDecimal.ZERO) != 0) {
		BigDecimal totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(new BigDecimal(foreignLocalCurrencyDataTable.getQty() == "" ? "0" : foreignLocalCurrencyDataTable.getQty()));
		if (foreignLocalCurrencyDataTable.getQty().equals("")) {
			foreignLocalCurrencyDataTable.setQty("0");
		}
		foreignLocalCurrencyDataTable.setPrice(totalcashAmount.toPlainString());
		BigDecimal totalSum = BigDecimal.ZERO;
		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : lstData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum.add(new BigDecimal(element.getPrice()));
			}
		}
		BigDecimal totalDenoCash = getDenomtotalCash();
		if (getCashAmount().compareTo(totalSum) < 0) {
			totalSum = BigDecimal.ZERO;
			foreignLocalCurrencyDataTable.setQty("0");
			foreignLocalCurrencyDataTable.setPrice("0");
			for (ForeignLocalCurrencyDataTable element : lstData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}
			setDenomtotalCash(totalSum);
			RequestContext.getCurrentInstance().execute("denominationexceed.show();");
		} else {
			setDenomtotalCash(totalSum);
		}
		
	}

	// panel 8 data
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getCollectedRefundAmount() {
		return collectedRefundAmount;
	}

	public void setCollectedRefundAmount(BigDecimal collectedRefundAmount) {
		this.collectedRefundAmount = collectedRefundAmount;
	}

	public Boolean getBoorefundPaymentDetails() {
		return boorefundPaymentDetails;
	}

	public void setBoorefundPaymentDetails(Boolean boorefundPaymentDetails) {
		this.boorefundPaymentDetails = boorefundPaymentDetails;
	}

	private void refundDenominationData() {
		lstRefundData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		/* Checking that it's first time or not, first time list size will be 0 */
		double sAmount = 0;
		localLstData.clear();
		if (localLstData.size() == 0) {
			/* Responsible to show serial number in datatable */
			int i = 0;
			/*
			 * Responsible to hold each row in different bean object of
			 * datatable
			 */
			ForeignLocalCurrencyDataTable item = null;
			List<Stock> dataFromDb = foreignLocalCurrencyDenominationService.getLocalCurrencyDenominationFromStock(sessionmanage.getCountryId(), sessionStateManage.getUserName(), sessionStateManage.getBranchId(), sessionStateManage.getCompanyId(), sessionStateManage.getCurrencyId());
			/* putting the value in list to show in datatable */
			for (Stock element : dataFromDb) {
				int stock = element.getOpenQuantity() + element.getPurchaseQuantity() + element.getReceivedQuantity() - (element.getSaleQuantity() + element.getTransactionQuantity());
				String qty = null;
				double count = 0, totalNotes = 0;
				// double sAmount = 0;
				double denamination = 0;
				String amount = null;
				Double result = 0.0;
				int cal = 0;
				if (stock > 0) {
					denamination = Double.parseDouble(element.getDenominationId().getDenominationAmount().toString());
					if (denamination <= sAmount) {
						count = sAmount / denamination;
					}
					totalNotes = totalNotes + count;
					sAmount = sAmount % denamination;
					if (count != 0) {
						qty = new Integer((int) Math.floor(count)).toString();
						cal = new Integer((int) Math.floor(count));
						result = cal * denamination;
						amount = new Integer((int) Math.ceil(result)).toString();
					} else {
						qty = "0";
						amount = "0";
					}
					item = new ForeignLocalCurrencyDataTable(++i, element.getDenominationId().getDenominationAmount(), qty, amount, stock, element.getStockId(), element.getDenominationId().getDenominationId(), element.getDenominationId().getExCurrencyMaster().getCurrencyId(), element
							.getDenominationId().getDenominationDesc(),element.getDenominationId().getDenominationAmount());
					localLstData.add(item);
				}
			}
		}
		/* Responsible to keep sum of total amount of cash entered */
		int totalSum = 0;
		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : localLstData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum + Integer.parseInt(element.getPrice());
			}
		}
		System.out.println(totalSum);
		setRefundAmount(getPayRefund());
		setCollectedRefundAmount(new BigDecimal(0));
		setLstRefundData(localLstData);
	}

	public void onRefundCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {
		BigDecimal totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(new BigDecimal(foreignLocalCurrencyDataTable.getQty() == "" ? "0" : foreignLocalCurrencyDataTable.getQty()));
		if (foreignLocalCurrencyDataTable.getQty().equals("")) {
			foreignLocalCurrencyDataTable.setQty("0");
		}
		foreignLocalCurrencyDataTable.setPrice(totalcashAmount.toPlainString());
		BigDecimal totalSum = BigDecimal.ZERO;
		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : lstRefundData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum.add(new BigDecimal(element.getPrice()));
			}
		}
		BigDecimal totalDenoCash = getDenomtotalCash();
		if (getRefundAmount().compareTo(totalSum) < 0) {
			totalSum = BigDecimal.ZERO;
			foreignLocalCurrencyDataTable.setQty("0");
			foreignLocalCurrencyDataTable.setPrice("0");
			for (ForeignLocalCurrencyDataTable element : lstRefundData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}
			setCollectedRefundAmount(totalSum);
			RequestContext.getCurrentInstance().execute("refunddenominationexceed.show();");
		} else {
			setCollectedRefundAmount(totalSum);
		}
	}

	// last panel Payment details
	public BigDecimal getDenomtotalCashreceived() {
		return denomtotalCashreceived;
	}

	public void setDenomtotalCashreceived(BigDecimal denomtotalCashreceived) {
		this.denomtotalCashreceived = denomtotalCashreceived;
	}

	public BigDecimal getKnetValue() {
		return knetValue;
	}

	public void setKnetValue(BigDecimal knetValue) {
		this.knetValue = knetValue;
	}

	public BigDecimal getDenomtotalCash() {
		return denomtotalCash;
	}

	public void setDenomtotalCash(BigDecimal denomtotalCash) {
		this.denomtotalCash = denomtotalCash;
	}

	public String getPaymentDetailsremark() {
		return paymentDetailsremark;
	}

	public void setPaymentDetailsremark(String paymentDetailsremark) {
		this.paymentDetailsremark = paymentDetailsremark;
	}

	public BigDecimal getPayPaidAmount() {
		return payPaidAmount;
	}

	public void setPayPaidAmount(BigDecimal payPaidAmount) {
		this.payPaidAmount = payPaidAmount;
	}

	public BigDecimal getPayNetPaidAmount() {
		return payNetPaidAmount;
	}

	public void setPayNetPaidAmount(BigDecimal payNetPaidAmount) {
		this.payNetPaidAmount = payNetPaidAmount;
	}

	public BigDecimal getPayRefund() {
		return payRefund;
	}

	public void setPayRefund(BigDecimal payRefund) {
		this.payRefund = payRefund;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	String rtnStrin = "";
	public String saveAll() {
		rtnStrin = "";
		if (getBooRendercashdenomination()) {
			if (lstData.size() != 0) {
				if (getCashAmount().compareTo(getDenomtotalCash()) == 0) {
					if (getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
						nextpaneltoRefundDenomination();
						setNextOrSaveButton(Constants.Save);
						setSavetimeReportEmailCheck(true);
					} else {
						saveRemittance();
					}
				} else {
					RequestContext.getCurrentInstance().execute("netamountgreater.show();");
				}
			} else {
				RequestContext.getCurrentInstance().execute("denominationerror.show();");
			}
		} else if (getBoorefundPaymentDetails()) {
			if (lstRefundData.size() != 0) {
				if (getCollectedRefundAmount().compareTo(getPayRefund()) == 0) {
					saveRemittance();
				} else {
					RequestContext.getCurrentInstance().execute("refundamounterror.show();");
				}
			} else {
				RequestContext.getCurrentInstance().execute("denominationerror.show();");
			}
		} else {
			if (getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
				nextpaneltoRefundDenomination();
				setNextOrSaveButton(Constants.Save);
				setSavetimeReportEmailCheck(true);
			} else {
				saveRemittance();
			}
		}
		//		i=0;	

		return rtnStrin;
	}

	// OLD saving - 09/10/2016
	/*public void saveRemittance() {
		log.info("Entering into saveRemittance method");
		setBooDeclarationReportDisplay(false);
		setBooReportDisplay(false);
		// if round functionality done then Ex_Appl_Trnx record changed
		if (lstModifyRoundRecord.size() != 0) {
			ShoppingCartDataTableBean lstModifyRec = lstModifyRoundRecord.get(0);
			iPersonalRemittanceService.saveApplicationTransactionModifiedRecordByRound(lstModifyRec);
			// modified record storing separetly
			try {
				@SuppressWarnings("unused")
				List<RemittanceReportBean> lstModified = fetchApplicationRemittanceReportData(lstModifyRec.getDocumentNo());
			} catch (Exception e) {
				log.info("Exception in fetchApplicationRemittanceReportData" + e.getMessage());
			}
			setBooRenderModifiedApplTrnxReportByRound(true);
		} else {
			setBooRenderModifiedApplTrnxReportByRound(false);
		}
		// updating Status "S" in different Tables and checking application status null or not if not null then 
		boolean checkStatus = updadateRecordsNotNull(Constants.S);
		if (!checkStatus) {
			setErrmsg("Shopping cart Application already processed / changed.");
			log.info("Exception occured while Checking Shopping cart Application status.");
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			return;
		}


		log.info("=====================CALLED SAVE ALL");
		String out = null;
		String errormsg = null;
		BigDecimal cFinYear = null;
		BigDecimal collectionNumber = null;
		Connection connection = null;
		connection = generalService.getDataSourceFromHibernateSession();
		List<AuthorizedLog> lstAuthenStoredRecords = new ArrayList<AuthorizedLog>();

		if(lstAuthenStoredRecords != null && !lstAuthenStoredRecords.isEmpty()){
			lstAuthenStoredRecords.clear();
		}

		if(lstBeneAuthentication.size()!=0){
			// save authentication records
			lstAuthenStoredRecords.addAll(lstBeneAuthentication);
		}

		HashMap<String, Object> returnResult = saveCollect();
		TempCollection tempCollection = (TempCollection)returnResult.get("Collect");
		List<TempCollectDetail> tempDetailsList= saveCollectionDetail(tempCollection);
		List<TempForeignCurrencyAdjust> tempAdjustList = saveForeignCurrencyAdjust(tempCollection);

		BigDecimal collectionId = new BigDecimal(0);
		try {
			collectionId =iPersonalRemittanceService.saveTempCollectionwithDetailsandTempCurrencyAdjust(tempCollection,tempDetailsList,tempAdjustList,lstAuthenStoredRecords);
			log.info(" collection id is "+ collectionId);
		} catch (Exception e2) {

			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			setErrmsg("Exception occured while saving collection and details " + e2.getMessage());
			log.info("Exception occured while saving collection and details "+e2);
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			return;
		}
		log.info(" collection id is "+ collectionId);
		if(collectionId==null || collectionId.compareTo(new BigDecimal(0))==0)
		{
			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			setErrmsg("Exception occured while saving collection and details " );
			log.info("Exception occured while saving collection and details ");
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			return;
		}
		
		
		log.info("Parameter 1 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCountryId(): "+sessionStateManage.getCountryId());
		log.info("Parameter 2 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCompanyId(): "+sessionStateManage.getCompanyId());
		log.info("Parameter 3 saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo(): "+getCustomerNo());
		log.info("Parameter 4 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName(): "+sessionStateManage.getUserName());
		log.info("Parameter 5 saveRemittance EX_INSERT_REMITTANCE_TRANX getColremittanceNo() :"+getColremittanceNo());
		log.info("Parameter 6 saveRemittance EX_INSERT_REMITTANCE_TRANX tempCollection.getDocumentCode(): "+tempCollection.getDocumentCode());
		log.info("Parameter 7 saveRemittance EX_INSERT_REMITTANCE_TRANX collectionId: "+collectionId);

		CallableStatement cs;
		try {

			cs = connection.prepareCall(" { call EX_INSERT_REMITTANCE_TRANX (?,?,?,?,?,?,?,?,?,?)}");
			cs.setBigDecimal(1, sessionStateManage.getCountryId());
			cs.setBigDecimal(2, sessionStateManage.getCompanyId());
			cs.setBigDecimal(3, getCustomerNo());
			cs.setString(4, sessionStateManage.getUserName());
			cs.setBigDecimal(5, getColremittanceNo());
			cs.setBigDecimal(6, tempCollection.getDocumentCode());
			cs.setBigDecimal(7,collectionId);
			cs.registerOutParameter(8, java.sql.Types.INTEGER);
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			cs.registerOutParameter(10, java.sql.Types.VARCHAR);
			cs.execute();

			cFinYear = cs.getBigDecimal(8);
			collectionNumber = cs.getBigDecimal(9);
			errormsg = cs.getString(10);


			log.info("Out1 -----> cFinYear : "+cFinYear);
			log.info("Out2------>collectionNumber : "+collectionNumber);
			log.info("Out3------->errormsg : "+errormsg);

			if(cFinYear==null || collectionNumber==null)
			{
				String status = null;
				// updating Status "null" in different Tables
				updadateRecords(status);

				log.info("Exception occured while executing procedure");
				throw new SQLException("Exception occured while executing procedure");
			}

			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo():"+getCustomerNo());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName():"+sessionStateManage.getUserName());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX  getColremittanceNo():"+ getColremittanceNo());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX collectionNumber:"+collectionNumber);
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX cFinYear:"+cFinYear);
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX errormsg:"+errormsg);

			log.info("================Output=========" + out+"\t errormsg :"+errormsg);
			if (errormsg==null || errormsg.equalsIgnoreCase("")) {

				lstselectedrecord.clear();
				allPanelOff();
				setMainPanelRender(false);
				rtnStrin = "corporateremittancesuccesspage";
				remittanceNo = new BigDecimal(0);
				fcsaleNo = new BigDecimal(0);
				cashAmount = new BigDecimal(0);
				coldatatablevalues.clear();

				//for report generation
				setDocumentNo(collectionNumber.toPlainString());

				// calling EX_INSERT_EMOS_TRANSFER_LIVE procedure changes - 17/12/2015 
				Collect collect = (Collect)returnResult.get("Collect");

				if (tempCollection != null) {
					try {
						iPersonalRemittanceService.insertEMOSLIVETransfer(tempCollection.getApplicationCountryId(),sessionStateManage.getCompanyId(), tempCollection.getDocumentCode(), cFinYear, collectionNumber);
					} catch (AMGException e) {
						log.info("Exception occurs while moving data to Old Emos:" + cFinYear + " - " + collectionNumber);
					} catch (Exception e1) {
						log.info("Exception occurs while moving data to Old Emos:" + cFinYear + " - " + collectionNumber);
					}
				}
				
				if(getCheckEmailForReport() && getEmailToSendReport()!=null)
				{
					setBooReportDisplay(false);
					generatePersonalRemittanceReceiptReport();
					if(!getCustomerEmail().equalsIgnoreCase(getEmailToSendReport()))
					{
						int updateSts=iPersonalRemittanceService.updtaeCustEmail(getCustomerNo(), getEmailToSendReport());
					}
				}else
				{
					setBooReportDisplay(true);
				}

			} else {
				String status = null;
				// updating Status "null" in different Tables
				updadateRecords(status);
				log.info(" Records Not Saved : " + errormsg);
				setErrmsg(errormsg);
				RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			}
		} catch (SQLException e) {
			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			log.info(" Records Not Saved : " + errormsg);
			setErrmsg(errormsg);
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
		}

		catch (Exception e) {
			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			log.info(" Records Not Saved : " + errormsg);
			setErrmsg(errormsg);
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
		}

		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				setErrmsg(errormsg);
				RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			}
		}
	}*/
	
	public void saveRemittance() {
		
		log.info("Entering into saveRemittance method");
		
		setBooDeclarationReportDisplay(false);
		setBooReportDisplay(false);
		
		// if round functionality done then Ex_Appl_Trnx record changed
		if (lstModifyRoundRecord.size() != 0) {
			ShoppingCartDataTableBean lstModifyRec = lstModifyRoundRecord.get(0);
			iPersonalRemittanceService.saveApplicationTransactionModifiedRecordByRound(lstModifyRec);
			// modified record storing separetly
			try {
				@SuppressWarnings("unused")
				List<RemittanceReportBean> lstModified = fetchApplicationRemittanceReportData(lstModifyRec.getDocumentNo());
			} catch (Exception e) {
				log.info("Exception in fetchApplicationRemittanceReportData" + e.getMessage());
			}
			setBooRenderModifiedApplTrnxReportByRound(true);
		} else {
			setBooRenderModifiedApplTrnxReportByRound(false);
		}
		
		// updating Status "S" in different Tables and checking application status null or not if not null then 
		boolean checkStatus = updadateRecordsNotNull(Constants.S);
		if (!checkStatus) {
			setErrmsg("Shopping cart Application already processed / changed.");
			log.info("Exception occured while Checking Shopping cart Application status.");
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			return;
		}

		log.info("=====================CALLED SAVE ALL");
		String out = null;
		String errormsg = null;
		BigDecimal cFinYear = null;
		BigDecimal collectionNumber = null;
		
		List<AuthorizedLog> lstAuthenStoredRecords = new ArrayList<AuthorizedLog>();

		if(lstAuthenStoredRecords != null && !lstAuthenStoredRecords.isEmpty()){
			lstAuthenStoredRecords.clear();
		}

		if(lstBeneAuthentication.size()!=0){
			// save authentication records
			lstAuthenStoredRecords.addAll(lstBeneAuthentication);
		}

		HashMap<String, Object> returnResult = saveCollect();
		TempCollection tempCollection = (TempCollection)returnResult.get("Collect");
		List<TempCollectDetail> tempDetailsList= saveCollectionDetail(tempCollection);
		List<TempForeignCurrencyAdjust> tempAdjustList = saveForeignCurrencyAdjust(tempCollection);

		BigDecimal collectionId = new BigDecimal(0);
		try {
			collectionId =iPersonalRemittanceService.saveTempCollectionwithDetailsandTempCurrencyAdjust(tempCollection,tempDetailsList,tempAdjustList,lstAuthenStoredRecords);
			log.info(" collection id is "+ collectionId);
		} catch (Exception e2) {

			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			setErrmsg("Exception occured while saving collection and details " + e2.getMessage());
			log.info("Exception occured while saving collection and details "+e2);
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			return;
		}
		log.info(" collection id is "+ collectionId);
		if(collectionId==null || collectionId.compareTo(new BigDecimal(0))==0)
		{
			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			setErrmsg("Exception occured while saving collection and details " );
			log.info("Exception occured while saving collection and details ");
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			return;
		}
		
		log.info("Parameter 1 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCountryId(): "+sessionStateManage.getCountryId());
		log.info("Parameter 2 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCompanyId(): "+sessionStateManage.getCompanyId());
		log.info("Parameter 3 saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo(): "+getCustomerNo());
		log.info("Parameter 4 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName(): "+sessionStateManage.getUserName());
		log.info("Parameter 5 saveRemittance EX_INSERT_REMITTANCE_TRANX getColremittanceNo() :"+getColremittanceNo());
		log.info("Parameter 6 saveRemittance EX_INSERT_REMITTANCE_TRANX tempCollection.getDocumentCode(): "+tempCollection.getDocumentCode());
		log.info("Parameter 7 saveRemittance EX_INSERT_REMITTANCE_TRANX collectionId: "+collectionId);

		try {
			// calling procedure to move appl to remit
			saveCollectbyProcedure(tempCollection, collectionId);
		} catch (AMGException e) {
			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			log.info(" Records Not Saved : " + errormsg);
			setExceptionMessage(errormsg);
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
		}
		
	}
	
	// calling procedure to move appl to remit
	public void saveCollectbyProcedure(TempCollection tempCollection,BigDecimal collectionId) throws AMGException{
		
		HashMap<String, Object> returnResult = new HashMap<String, Object>();
		String errormsg = null;
		BigDecimal cFinYear = null;
		BigDecimal collectionNumber = null;

		log.info("Parameter 1 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCountryId(): "+sessionStateManage.getCountryId());
		log.info("Parameter 2 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCompanyId(): "+sessionStateManage.getCompanyId());
		log.info("Parameter 3 saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo(): "+getCustomerNo());
		log.info("Parameter 4 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName(): "+sessionStateManage.getUserName());
		log.info("Parameter 5 saveRemittance EX_INSERT_REMITTANCE_TRANX getColremittanceNo(): "+getColremittanceNo());
		log.info("Parameter 6 saveRemittance EX_INSERT_REMITTANCE_TRANX tempCollection.getDocumentCode(): "+tempCollection.getDocumentCode());
		log.info("Parameter 7 saveRemittance EX_INSERT_REMITTANCE_TRANX collectionId: "+collectionId);

		try {
			
			returnResult.put("CountryId",sessionStateManage.getCountryId());
			returnResult.put("CompanyId",sessionStateManage.getCompanyId());
			returnResult.put("CustomerId",getCustomerNo());
			returnResult.put("UserName",sessionStateManage.getUserName());
			returnResult.put("NoofTrnx",getColremittanceNo());
			returnResult.put("TempDocCode",tempCollection.getDocumentCode());
			returnResult.put("TempCollectionId",collectionId);
			
			HashMap<String, Object> outRecord = iPersonalRemittanceService.saveAllRemittanceTransaction(returnResult);
			
			collectionNumber = (BigDecimal) outRecord.get("CollectionDocNo");
			cFinYear = (BigDecimal) outRecord.get("CollectionFinYear");
			errormsg = (String) outRecord.get("ErrorMsg");

			log.info("=====================CALLED SAVE ALL");

			log.info("Out1 -----> cFinYear : "+cFinYear);
			log.info("Out2------>collectionNumber : "+collectionNumber);
			log.info("Out3------->errormsg : "+errormsg);

			if(cFinYear==null || collectionNumber==null)
			{
				String status = null;
				// updating Status "null" in different Tables
				updadateRecords(status);

				log.info("Exception occured while executing procedure");
				throw new SQLException("Exception occured while executing procedure");
			}

			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo():"+getCustomerNo());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName():"+sessionStateManage.getUserName());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX  getColremittanceNo(): "+getColremittanceNo());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX collectionNumber:"+collectionNumber);
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX cFinYear:"+cFinYear);
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX errormsg:"+errormsg);

			if (errormsg==null || errormsg.equalsIgnoreCase("")) {

				lstselectedrecord.clear();
				allPanelOff();
				setMainPanelRender(false);
				rtnStrin = "corporateremittancesuccesspage";
				remittanceNo = new BigDecimal(0);
				fcsaleNo = new BigDecimal(0);
				cashAmount = new BigDecimal(0);
				coldatatablevalues.clear();

				//for report generation
				setDocumentNo(collectionNumber.toPlainString());

				// calling EX_INSERT_EMOS_TRANSFER_LIVE procedure changes - 17/12/2015 
				/*Collect collect = (Collect)returnResult.get("Collect");*/

				if (tempCollection != null) {
					try {
						iPersonalRemittanceService.insertEMOSLIVETransfer(tempCollection.getApplicationCountryId(),sessionStateManage.getCompanyId(), tempCollection.getDocumentCode(), cFinYear, collectionNumber);
					} catch (AMGException e) {
						log.info("Exception occurs while moving data to Old Emos:" + cFinYear + " - " + collectionNumber);
					} catch (Exception e1) {
						log.info("Exception occurs while moving data to Old Emos:" + cFinYear + " - " + collectionNumber);
					}
				}
				
				if(getCheckEmailForReport() && getEmailToSendReport()!=null)
				{
					setBooReportDisplay(false);
					try {
						generatePersonalRemittanceReceiptReport();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(!getCustomerEmail().equalsIgnoreCase(getEmailToSendReport()))
					{
						int updateSts=iPersonalRemittanceService.updtaeCustEmail(getCustomerNo(), getEmailToSendReport());
					}
				}else
				{
					setBooReportDisplay(true);
				}

			} else {
				String status = null;
				// updating Status "null" in different Tables
				updadateRecords(status);
				log.info(" Records Not Saved : " + errormsg);
				setExceptionMessage(errormsg);
				RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			}
		} catch (SQLException e) {
			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			log.info(" Records Not Saved : " + errormsg);
			setExceptionMessage(errormsg);
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
		}
	}
	

	public Boolean getBoRenderTotalAmountCalPanel() {
		return boRenderTotalAmountCalPanel;
	}

	public void setBoRenderTotalAmountCalPanel(Boolean boRenderTotalAmountCalPanel) {
		this.boRenderTotalAmountCalPanel = boRenderTotalAmountCalPanel;
	}

	public BigDecimal getToalUsedAmount() {
		return toalUsedAmount;
	}

	public void setToalUsedAmount(BigDecimal toalUsedAmount) {
		this.toalUsedAmount = toalUsedAmount;
	}

	public void editRecord(PersonalRemittanceCollectionDataTable personalRemitObj) {

		if (coldatatablevalues.size() > 0) {
			subtractedAmount = getToalUsedAmount().subtract(personalRemitObj.getColAmountDT());
			setToalUsedAmount(subtractedAmount);
			if(getToalUsedAmount().compareTo(getCalNetAmountPaid())>0){
				setTotalbalanceAmount(BigDecimal.ZERO);
				setTotalrefundAmount(getToalUsedAmount().subtract(getCalNetAmountPaid()));
			}else if(getToalUsedAmount().compareTo(getCalNetAmountPaid())<0){
				setTotalbalanceAmount(getCalNetAmountPaid().subtract(getToalUsedAmount()));
				setTotalrefundAmount(BigDecimal.ZERO);
			}else{
				setTotalbalanceAmount(BigDecimal.ZERO);
				setTotalrefundAmount(BigDecimal.ZERO);
			}
		} else {
			setToalUsedAmount(null);
			setTotalbalanceAmount(null);
			setTotalrefundAmount(null);
		}

		if(personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)){
			setBooRenderColDebitCard(false);
			setBooRenderColCheque(false);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColCash(personalRemitObj.getColAmountDT());
			coldatatablevalues.remove(personalRemitObj);
		}else if(personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)){
			setBooRenderColDebitCard(true);
			setBooRenderColCheque(false);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColBankCode(personalRemitObj.getColBankCodeDT());
			setPopulatedDebitCardNumber(personalRemitObj.getColCardNumberDT());
			setColCardNo(personalRemitObj.getColCardNumberDT());
			setColNameofCard(personalRemitObj.getColNameofCardDT());
			setColCash(personalRemitObj.getColAmountDT());
			setColApprovalNo(personalRemitObj.getColApprovalNo());
			if (personalRemitObj.getColAuthorizedByDT() != null) {
				setColAuthorizedby(personalRemitObj.getColAuthorizedByDT());
				setColpassword(null);
				setBooAuthozed(true);
			} else {
				setBooAuthozed(false);
			}
			coldatatablevalues.remove(personalRemitObj);
			checkcashcollection();
		}else if(personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
			setBooRenderColDebitCard(false);
			setBooRenderColCheque(true);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColchequebankCode(personalRemitObj.getColBankCodeDT());
			setColChequeRef(personalRemitObj.getColchequeRefNo());
			setColChequeDate(personalRemitObj.getColchequeDate());
			setColCash(personalRemitObj.getColAmountDT());
			setColChequeApprovalNo(personalRemitObj.getColApprovalNo());
			coldatatablevalues.remove(personalRemitObj);
		}else{
			System.out.println("Other Payment mode");
		}

		if (coldatatablevalues.size() != 0) {
			setBooRendercollectiondatatable(true);
		} else {
			setBooRendercollectiondatatable(false);
		}

	}

	/**
	 * Document Seriality
	 */
	public String getDocumentSerialID(String processIn) {
		System.out.println("Start getDocumentSerialID ...." + getFinaceYear());
		log.info("process in :" + processIn);
		String documentSerialId = generalService.getNextDocumentReferenceNumber(
				Integer.parseInt(sessionStateManage
						.getSessionValue("countryId")),
						Integer.parseInt(sessionStateManage
								.getSessionValue("companyId")),
								Integer.parseInt(Constants.DOCUMENT_CODE_FOR_REMITTANCE_APPLICATION
										.trim()), getFinaceYear().intValue(), processIn,sessionStateManage.getCountryBranchCode());

		System.out.println("end getDocumentSerialID  :" + documentSerialId);
		return documentSerialId;
	}

	public String getDocumentSerialCollectTrnx(String processIn) {
		System.out.println("Start getDocumentSerialID ...." + getFinaceYear());
		log.info("process in :" + processIn);
		String documentSerialId =  generalService.getNextDocumentReferenceNumber(
				Integer.parseInt(sessionStateManage
						.getSessionValue("countryId")),
						Integer.parseInt(sessionStateManage
								.getSessionValue("companyId")),
								Integer.parseInt(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),
								getFinaceYear().intValue(), processIn,sessionStateManage.getCountryBranchCode());
		System.out.println("end getDocumentSerialID  :" + documentSerialId);
		return documentSerialId;
	}

	/**
	 * END 27/01/2015
	 */
	// START 27/01/2015 ---Ram Mohan
	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}

	/*
	 * // save reords of fs in collection and collection details from view
	 * public void finalSave() { saveCollect(); // saveCollectionDetail(); }
	 */
	// save reords of fs in collection and collection details from view
	public HashMap<String, Object> saveCollect() {
		List<TempCollection> collectIdDetails = new ArrayList<TempCollection>();
		HashMap<String, Object> returnResult = new HashMap<String, Object>();
		if (lstselectedrecord.size() > 0) {
			ShoppingCartDataTableBean shoppingCartDetails = lstselectedrecord.get(0);
			TempCollection collect = new TempCollection();

			collect.setApplicationCountryId(sessionStateManage.getCountryId());
			Customer customer = new Customer();
			customer.setCustomerId(shoppingCartDetails.getCustomerId());
			collect.setFsCustomer(customer);
			collect.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION));
			collect.setCollectDate(new Date());
			CurrencyMaster forcurrencymaster = new CurrencyMaster();
			forcurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));			
			collect.setExCurrencyMaster(forcurrencymaster);
			collect.setPaidAmount(getPayPaidAmount());
			collect.setRefoundAmount(getPayRefund());
			collect.setNetAmount(getPayNetPaidAmount());
			try {
				collect.setAccountMMYYYY(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			CountryBranch countryBrnach = new CountryBranch();
			countryBrnach.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			collect.setCountryBranch(countryBrnach);
			System.out.println("Constants.RECEIPT_TYPE :"+Constants.RECEIPT_TYPE);

			collect.setReceiptType(Constants.RECEIPT_TYPE);
			collect.setCreatedBy(sessionStateManage.getUserName());
			collect.setCreatedDate(new Date());
			collectIdDetails.add(collect);
			returnResult.put("Collect", collect);
			returnResult.put("ExchangeRate",shoppingCartDetails.getExchangeRateApplied());
			returnResult.put("LocalTrnsAmount",shoppingCartDetails.getLocalNextTranxAmount());

		}
		return returnResult;
	}

	

	

	// End to set the DocumentNo 28/01/2015 ---Ramakrishna
	// START 28/01/2015 ---- Rahamath
	public void assignNullValues() {
		setBenificiaryryNameRemittance(null);
		setCountryTelephoneNumber(null);
		// setBankFullName(null);
		// setBranchFullName(null);
		setRoutingCountry(null);
		setRoutingBank(null);
		setRemitMode(null);
		setDeliveryMode(null);
		setRoutingBranch(null);
		personalRemittCalFCAmountDTList.clear();
		setModeOfPayment(null);
		setCurrencyName(null);
		setCurrency(null);
		setAmountToRemit(null);
		setSpotRate(null);
		setAvailLoyaltyPoints(null);
		setChargesOverseas(null);
		setCashRounding(null);
		setBenificiaryryNameRemittance(null);
		setExchangeRate(null);
		setDataAccountnum(null);
		setOverseasamt(null);
		setDatabenificarycurrencyname(null);
		setCommission(null);
		setDatabenificarycountryname(null);
		setGrossAmountCalculated(null);
		setDatabenificarybankname(null);
		setLoyaltyAmountAvailed(null);
		setDatabenificarybranchname(null);
		setNetAmountPayable(null);
		setAgent(null);
		setNetAmountSent(null);
		setFurthuerInstructions(null);
		setIntermediaryBank(null);
		setIdProof(null);
		setPurposeOfRemittance(null);
		setSourceOfIncome(null);
		setMessageToUser(null);
		almcheckList.clear();
		slb1total1 = 0.0;
		slb1total2 = 0.0;
		slb1total3 = 0.0;
		slb1total4 = 0.0;
		setAmlMessageOne(null);
		setAmlMessageTwo(null);
		setAmlMessageThree(null);
		setAmlAuthorizedBy(null);
		setAmlRemarks(null);
		setCalGrossAmount(null);
		setCalNetAmountPaid(null);
	}

	public Boolean getRenderBackButton() {
		return renderBackButton;
	}

	public void setRenderBackButton(Boolean renderBackButton) {
		this.renderBackButton = renderBackButton;
	}

	public void updadateRecords(String status) {
		BigDecimal financialYearId = getFinaceYearId();
		for (ShoppingCartDataTableBean shoppingCartDataTableObj : shoppingcartDTList) {
			if (shoppingCartDataTableObj.getSelectedrecord()) {
				if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.FCSale)) {
					BigDecimal paymentReciptPk = iPersonalRemittanceService.getReceiptPaymentTablePk(shoppingCartDataTableObj.getCustomerId(),shoppingCartDataTableObj.getDocumentNo());
					iPersonalRemittanceService.updateReceiptPaymentTableData(paymentReciptPk,status);
					List<ForeignCurrencyAdjustApp> foreignCurrencyAdjustAppList = iPersonalRemittanceService.getForeignCurrencyAdjustAppPk(shoppingCartDataTableObj.getCustomerId(),shoppingCartDataTableObj.getDocumentNo());
					for (ForeignCurrencyAdjustApp foreignCurrencyAdjustAppPk : foreignCurrencyAdjustAppList) {
						iPersonalRemittanceService.updateForeignCurrencyAdjustAppTableData(foreignCurrencyAdjustAppPk.getForeignCurrencyAdjustId(),status);
					}
				} else if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
					/*BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(),shoppingCartDataTableObj.getDocumentNo());
					iPersonalRemittanceService.updateRemittanceApplicationTableData(remittanceAppPk,status);*/
					BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo(),shoppingCartDataTableObj.getDocumentId(),financialYearId,shoppingCartDataTableObj.getCompanyId());
					if (remittanceAppPk != null) {
						iPersonalRemittanceService.updateRemittanceApplicationTableData(remittanceAppPk, status);
					}
				}
			}
		}
	}

	public Boolean getPaymentDeatailsPanel() {
		return paymentDeatailsPanel;
	}

	public void setPaymentDeatailsPanel(Boolean paymentDeatailsPanel) {
		this.paymentDeatailsPanel = paymentDeatailsPanel;
	}

	public void finalSavePageNavigation() {
		setReceiptReportPanel(false);
		// setApplicationReportPanel(false);
		setMainPanelRender(true);
		tempCalNetAmountPaid = new BigDecimal(0);
		tempCalGrossAmount = new BigDecimal(0);
		nextrenderingLastPanel();
		setBooRendercashdenomination(false);
		setPaymentDeatailsPanel(false);
		setBoorefundPaymentDetails(false);
		setCalGrossAmount(null);
		setCalNetAmountPaid(null);
		setRefundAmount(null);
		setCollectedRefundAmount(null);
		lstData.clear();
		lstRefundData.clear();
		checkProExp = false;
		saveCount = 0;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("backToRemitanceFirstPanel", Constants.YES);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/CorporateRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<ShoppingCartDataTableBean> getShoppingcartDTList() {
		return shoppingcartDTList;
	}

	public void setShoppingcartDTList(List<ShoppingCartDataTableBean> shoppingcartDTList) {
		this.shoppingcartDTList = shoppingcartDTList;
	}

	public void getShoppingCartDetails(BigDecimal customerNo) {
		shoppingcartDTList.clear();
		List<ShoppingCartDetails> shoppingCartList = new ArrayList<ShoppingCartDetails>();
		try{
			shoppingCartList = iPersonalRemittanceService.getShoppingCartDetails(customerNo);
			if (shoppingCartList.size() > 0) {
				for (ShoppingCartDetails shoppingCartDetails : shoppingCartList) {
					ShoppingCartDataTableBean shoppingCartDataTableBean = new ShoppingCartDataTableBean();
					if (shoppingCartDetails.getRemittanceApplicationId() != null)
						shoppingCartDataTableBean.setRemittanceApplicationId(shoppingCartDetails.getRemittanceApplicationId());
					if (shoppingCartDetails.getApplicationType() != null)
						shoppingCartDataTableBean.setApplicationType(shoppingCartDetails.getApplicationType());
					if (shoppingCartDetails.getApplicationTypeDesc() != null)
						shoppingCartDataTableBean.setApplicationTypeDesc(shoppingCartDetails.getApplicationTypeDesc());
					if (shoppingCartDetails.getBeneficiaryAccountNo() != null)
						shoppingCartDataTableBean.setBeneficiaryAccountNo(shoppingCartDetails.getBeneficiaryAccountNo());
					if (shoppingCartDetails.getBeneficiaryBank() != null)
						shoppingCartDataTableBean.setBeneficiaryBank(shoppingCartDetails.getBeneficiaryBank());
					if (shoppingCartDetails.getBeneficiaryBranch() != null)
						shoppingCartDataTableBean.setBeneficiaryBranch(shoppingCartDetails.getBeneficiaryBranch());
					if (shoppingCartDetails.getBeneficiaryFirstName() != null)
						shoppingCartDataTableBean.setBeneficiaryFirstName(shoppingCartDetails.getBeneficiaryFirstName());
					if (shoppingCartDetails.getBeneficiarySecondName() != null)
						shoppingCartDataTableBean.setBeneficiarySecondName(shoppingCartDetails.getBeneficiarySecondName());
					if (shoppingCartDetails.getBeneficiaryThirdName() != null)
						shoppingCartDataTableBean.setBeneficiaryThirdName(shoppingCartDetails.getBeneficiaryThirdName());
					if (shoppingCartDetails.getBeneficiaryFourthName() != null)
						shoppingCartDataTableBean.setBeneficiaryFourthName(shoppingCartDetails.getBeneficiaryFourthName());
					if (shoppingCartDetails.getBeneficiaryId() != null)
						shoppingCartDataTableBean.setBeneficiaryId(shoppingCartDetails.getBeneficiaryId());
					if (shoppingCartDetails.getBeneficiarySwiftAddrOne() != null)
						shoppingCartDataTableBean.setBeneficiaryInterBankOne(shoppingCartDetails.getBeneficiarySwiftAddrOne());
					if (shoppingCartDetails.getBeneficiarySwiftAddrTwo() != null)
						shoppingCartDataTableBean.setBeneficiaryInterBankTwo(shoppingCartDetails.getBeneficiarySwiftAddrTwo());
					if (shoppingCartDetails.getBeneficiarySwiftBankOne() != null)
						shoppingCartDataTableBean.setBeneficiarySwiftBankOne(shoppingCartDetails.getBeneficiarySwiftBankOne());
					if (shoppingCartDetails.getBeneficiarySwiftBankTwo() != null)
						shoppingCartDataTableBean.setBeneficiarySwiftBankTwo(shoppingCartDetails.getBeneficiarySwiftBankTwo());
					if (shoppingCartDetails.getBeneficiaryName() != null)
						shoppingCartDataTableBean.setBeneficiaryName(shoppingCartDetails.getBeneficiaryName());
					if (shoppingCartDetails.getCompanyId() != null)
						shoppingCartDataTableBean.setCompanyId(shoppingCartDetails.getCompanyId());
					if (shoppingCartDetails.getDocumentFinanceYear() != null)
						shoppingCartDataTableBean.setDocumentFinanceYear(shoppingCartDetails.getDocumentFinanceYear());
					if (shoppingCartDetails.getDocumentId() != null)
						shoppingCartDataTableBean.setDocumentId(shoppingCartDetails.getDocumentId());
					if (shoppingCartDetails.getForeignTranxAmount() != null)
						shoppingCartDataTableBean.setForeignTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getForeignTranxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(shoppingCartDetails.getForeignCurrency())));
					if (shoppingCartDetails.getLocalTranxAmount() != null)
						shoppingCartDataTableBean.setLocalTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalTranxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					if (shoppingCartDetails.getLocalChargeAmount() != null)
						shoppingCartDataTableBean.setLocalChargeAmount(shoppingCartDetails.getLocalChargeAmount());
					if (shoppingCartDetails.getLocalCommisionAmount() != null)
						shoppingCartDataTableBean.setLocalCommisionAmount(shoppingCartDetails.getLocalCommisionAmount());
					if (shoppingCartDetails.getLocalDeliveryAmount() != null)
						shoppingCartDataTableBean.setLocalDeliveryAmount(shoppingCartDetails.getLocalDeliveryAmount());
					if (shoppingCartDetails.getIsActive() != null)
						shoppingCartDataTableBean.setIsActive(shoppingCartDetails.getIsActive());
					if (shoppingCartDetails.getLocalNextTranxAmount() != null)
						shoppingCartDataTableBean.setLocalNextTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalNextTranxAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					if (shoppingCartDetails.getCustomerId() != null)
						shoppingCartDataTableBean.setCustomerId(shoppingCartDetails.getCustomerId());
					if (shoppingCartDetails.getExchangeRateApplied() != null)
						shoppingCartDataTableBean.setExchangeRateApplied(shoppingCartDetails.getExchangeRateApplied());
					if (shoppingCartDetails.getApplicationId() != null)
						shoppingCartDataTableBean.setApplicationDetailsId(shoppingCartDetails.getApplicationId());
					if (shoppingCartDetails.getDocumentNo() != null)
						shoppingCartDataTableBean.setDocumentNo(shoppingCartDetails.getDocumentNo());
					if (shoppingCartDetails.getForeignCurrency() != null)
						shoppingCartDataTableBean.setForeigncurrency(shoppingCartDetails.getForeignCurrency());
					if (shoppingCartDetails.getForeignCurrencyDesc() != null)
						shoppingCartDataTableBean.setForeignCurrencyDesc(shoppingCartDetails.getForeignCurrencyDesc());
					if (shoppingCartDetails.getLocalCurrency() != null)
						shoppingCartDataTableBean.setLocalcurrency(shoppingCartDetails.getLocalCurrency());
					if (shoppingCartDetails.getSpldeal() != null) {
						shoppingCartDataTableBean.setSpldeal(shoppingCartDetails.getSpldeal());
						shoppingCartDataTableBean.setSpldealStatus(Constants.YES);
					} else {
						shoppingCartDataTableBean.setSpldealStatus(Constants.NO);
					}

					shoppingCartDataTableBean.setSelectedrecord(Boolean.FALSE);
					shoppingCartDataTableBean.setDeleteStatus(Boolean.TRUE);

					shoppingCartDataTableBean.setBooReportEligible(true);

					if(shoppingCartDetails.getApplicationType()!=null && shoppingCartDetails.getApplicationType().equals(Constants.FCSale))
					{
						shoppingCartDataTableBean.setBooReportEligible(false);
					}


					if(shoppingCartDetails.getLoyaltsPointIndicator()!=null)
					{
						shoppingCartDataTableBean.setLoyaltsPointIndicator(shoppingCartDetails.getLoyaltsPointIndicator());
					}

					if(shoppingCartDetails.getLoyaltsPointencahsed()!=null)
					{
						shoppingCartDataTableBean.setLoyaltsPointencahsed(shoppingCartDetails.getLoyaltsPointencahsed());
					}

					shoppingcartDTList.add(shoppingCartDataTableBean);


				}
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// end by subramanian
	/**
	 * Foreign Currency Adjust Save
	 */
	public void saveForeignCurrencyAdjust(HashMap<String, Object> returnResult) {
		Collect collect = (Collect) returnResult.get("Collect");
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		// String date = "01/09/14" ;Collect collectid , BigDecimal exchangerate
		// , BigDecimal localnettrastamount
		Date acc_Month = null;
		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int i = 0;
		for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstData) {
			System.out.println("foreignLocalCurrencyDataTable.getQty() ^^^^^^^^^^^^^^^^^" + foreignLocalCurrencyDataTable.getQty());
			if (foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
				// Company save
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
				foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);
				// Country Save
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
				foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
				// customer Save
				Customer customer = new Customer();
				customer.setCustomerId(getCustomerNo());
				foreignCurrencyAdjust.setFsCustomer(customer);
				foreignCurrencyAdjust.setDocumentDate(new Date());
				// currency Id
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(new BigDecimal(sessionmanage.getCurrencyId()));
				foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
				foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
				foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
				denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
				foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
				foreignCurrencyAdjust.setExchangeRate((BigDecimal) returnResult.get("ExchangeRate"));
				foreignCurrencyAdjust.setDenaminationAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				// foreignCurrencyAdjust.setDocumentNo();
				foreignCurrencyAdjust.setDocumentFinanceYear(getFinaceYear());
				foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());
				/****************************************************************************************/
				// Tanumoy
				try {
					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE)); // wait
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);
					/*
					 * CurrencyWiseDenomination currencyWiseDenomination = new
					 * CurrencyWiseDenomination();
					 * currencyWiseDenomination.setDenominationId(new
					 * BigDecimal(denominationBean.getDenominationID()));
					 * foreignCurrencyAdjust
					 * .setFsDenominationId(currencyWiseDenomination);
					 */
					/*
					 * foreignCurrencyAdjust.setNotesQuantity(denominationBean.
					 * getNoOfNotes());
					 * foreignCurrencyAdjust.setExchangeRate(denominationBean
					 * .getExchangeRate());
					 * foreignCurrencyAdjust.setDenaminationAmount
					 * (denominationBean.getDenominationAmount());
					 */
					/*
					 * returnResult.put("Collect", collect);
					 * returnResult.put("ExchangeRate",
					 * shoppingCartDetails.getExchangeRateApplied());
					 * returnResult.put("LocalTrnsAmount",
					 * shoppingCartDetails.getLocalNextTranxAmount());
					 */
					foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
					foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					foreignCurrencyAdjust.setTransactionType(Constants.P); // P
					// -
					// purchase
					// ,
					// O
					// -
					// Sale
					// ,
					// R
					// -
					// Refund
				} catch (Exception e) {
					e.printStackTrace();
				}
				/********************************************************************************************/
				/*
				 * foreignCurrencyAdjust.setApprovalBy(sessionStateManage.
				 * getSessionValue("userName"));
				 * foreignCurrencyAdjust.setApprovalDate(new Date());
				 */
				foreignCurrencyAdjust.setCreatedDate(new Date());
				foreignCurrencyAdjust.setCreatedBy(sessionmanage.getUserName());
				if (collect != null) {
					foreignCurrencyAdjust.setDocumentNo(collect.getDocumentNo());
					foreignCurrencyAdjust.setCollect(collect);
				}
				iPersonalRemittanceService.saveForeignCurrencyAdjust(foreignCurrencyAdjust);
			} else {
				log.info("Number of notes is 0");
			}
		}
		int j = 0;
		for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstRefundData) {
			System.out.println("foreignLocalCurrencyDataTable.getQty() ^^^^^^^^^^^^^^^^^" + foreignLocalCurrencyDataTable.getQty());
			if (foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
				// Company save
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
				foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);
				// Country Save
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
				foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
				// customer Save
				Customer customer = new Customer();
				customer.setCustomerId(getCustomerNo());
				foreignCurrencyAdjust.setFsCustomer(customer);
				foreignCurrencyAdjust.setDocumentDate(new Date());
				// currency Id
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(new BigDecimal(sessionmanage.getCurrencyId()));
				foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
				foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
				foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
				denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
				foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
				foreignCurrencyAdjust.setExchangeRate((BigDecimal) returnResult.get("ExchangeRate"));
				foreignCurrencyAdjust.setDenaminationAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				// foreignCurrencyAdjust.setDocumentNo();
				foreignCurrencyAdjust.setDocumentFinanceYear(getFinaceYear());
				foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());
				/****************************************************************************************/
				// Tanumoy
				try {
					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE)); // wait
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++j));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);
					/*
					 * CurrencyWiseDenomination currencyWiseDenomination = new
					 * CurrencyWiseDenomination();
					 * currencyWiseDenomination.setDenominationId(new
					 * BigDecimal(denominationBean.getDenominationID()));
					 * foreignCurrencyAdjust
					 * .setFsDenominationId(currencyWiseDenomination);
					 */
					/*
					 * foreignCurrencyAdjust.setNotesQuantity(denominationBean.
					 * getNoOfNotes());
					 * foreignCurrencyAdjust.setExchangeRate(denominationBean
					 * .getExchangeRate());
					 * foreignCurrencyAdjust.setDenaminationAmount
					 * (denominationBean.getDenominationAmount());
					 */
					/*
					 * returnResult.put("Collect", collect);
					 * returnResult.put("ExchangeRate",
					 * shoppingCartDetails.getExchangeRateApplied());
					 * returnResult.put("LocalTrnsAmount",
					 * shoppingCartDetails.getLocalNextTranxAmount());
					 */
					foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
					foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					foreignCurrencyAdjust.setTransactionType(Constants.R); // P
					// -
					// purchase
					// ,
					// O
					// -
					// Sale
					// ,
					// R
					// -
					// Refund
				} catch (Exception e) {
					e.printStackTrace();
				}
				/********************************************************************************************/
				/*
				 * foreignCurrencyAdjust.setApprovalBy(sessionStateManage.
				 * getSessionValue("userName"));
				 * foreignCurrencyAdjust.setApprovalDate(new Date());
				 */
				foreignCurrencyAdjust.setCreatedDate(new Date());
				foreignCurrencyAdjust.setCreatedBy(sessionmanage.getUserName());
				if (collect != null) {
					foreignCurrencyAdjust.setDocumentNo(collect.getDocumentNo());
					foreignCurrencyAdjust.setCollect(collect);
				}
				iPersonalRemittanceService.saveForeignCurrencyAdjust(foreignCurrencyAdjust);
			} else {
				log.info("Number of notes is 0");
			}
		}
	}

	// validation checking
	public boolean isBooAuthozed() {
		return booAuthozed;
	}

	public void setBooAuthozed(boolean booAuthozed) {
		this.booAuthozed = booAuthozed;
	}

	/** populate new bankList for Customer **/
	public void addNewBenificiary() {
		setColBankCode(null);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		setColCash(null);
		setColAuthorizedby(null);
		setColpassword(null);
		setColApprovalNo(null);
		setBooAuthozed(false);
		bankMasterList.clear();
		localbankList.clear();// From View V_EX_CBNK
		lstDebitCard.clear();

		// to fetch All Banks from View
		//getLocalBankListforIndicatorFromView();
		localbankList = generalService.getLocalBankListFromView(sessionStateManage.getCountryId());

		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
		if (localbankList.size() != 0) {
			for (ViewBankDetails lstBank : localbankList) {
				if (!duplicateCheck.contains(lstBank.getChequeBankId())) {
					duplicateCheck.add(lstBank.getChequeBankId());
					lstofBank.add(lstBank);
				}
			}
		}
		setBankMasterList(lstofBank);
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
	}

	// to get the local bank list or customer bank list
	public void getLocalBankListforIndicator() {
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<BigDecimal> duplicateCheck1 = new ArrayList<BigDecimal>();
		List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
		List<ViewBankDetails> lstofBank1 = new ArrayList<ViewBankDetails>();
		bankMasterList.clear();
		chequebankMasterList.clear();

		localbankList = generalService.getLocalBankListFromView(sessionStateManage.getCountryId());

		// cheque banks purpose 
		if(localbankList.size() != 0){
			chequebankMasterList.addAll(localbankList);
		}

		//lstoflocalbank = generalService.getLocalBankList(sessionmanage.getCountryId());
		List<ViewBankDetails> localBankListinCollection = icustomerBankService.customerBanks(getCustomerNo(), getColBankCode());
		if (localBankListinCollection.size() > 0) {
			bankMasterList.addAll(localBankListinCollection);
		} else {
			bankMasterList.addAll(localbankList);
		}
		if (bankMasterList.size() != 0) {
			for (ViewBankDetails lstBank : bankMasterList) {
				if (!duplicateCheck.contains(lstBank.getChequeBankId())) {
					duplicateCheck.add(lstBank.getChequeBankId());
					lstofBank.add(lstBank);
				}
			}
		}
		bankMasterList.clear();
		bankMasterList.addAll(lstofBank);


		if (chequebankMasterList.size() != 0) {
			for (ViewBankDetails lstBank : chequebankMasterList) {
				if (!duplicateCheck1.contains(lstBank.getChequeBankId())) {
					duplicateCheck1.add(lstBank.getChequeBankId());
					lstofBank1.add(lstBank);
				}
			}
		}
		chequebankMasterList.clear();
		chequebankMasterList.addAll(lstofBank1);

	}

	// checking customer name and debit card name of card
	public void firstNameCheck() {
		if (getColNameofCard().equalsIgnoreCase(getFirstName())) {
			// empllist.clear();
			setColAuthorizedby(null);
			setColpassword(null);
			setBooAuthozed(false);
		} else {
			//List<Employee> localEmpllist = generalService.getEmployeelist(sessionmanage.getCountryId(), new BigDecimal(sessionmanage.getBranchId()), new BigDecimal(sessionmanage.getRoleId()));
			List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
			setEmpllist(localEmpllist);
			setColAuthorizedby(null);
			setColpassword(null);
			setBooAuthozed(true);
		}
	}
	
	boolean checkKnetAmount ;

	private String excheckCashLimitMessage = null;
	
	
	private String colpaymentmodeCode;
	private BigDecimal errcolCashExistsLimit;
	
	// Eight Panel Credit card Details
		private boolean booRenderColCheque = false;
		private String colchequebankCode;
		private String colChequeRef;
		private Date colChequeDate;
		//private Date effectiveMinDate = new Date();
		private String colChequeApprovalNo;
		
		
	
	public boolean isCheckKnetAmount() {
			return checkKnetAmount;
		}

		public void setCheckKnetAmount(boolean checkKnetAmount) {
			this.checkKnetAmount = checkKnetAmount;
		}

		public String getExcheckCashLimitMessage() {
			return excheckCashLimitMessage;
		}

		public void setExcheckCashLimitMessage(String excheckCashLimitMessage) {
			this.excheckCashLimitMessage = excheckCashLimitMessage;
		}

		public String getColpaymentmodeCode() {
			return colpaymentmodeCode;
		}

		public void setColpaymentmodeCode(String colpaymentmodeCode) {
			this.colpaymentmodeCode = colpaymentmodeCode;
		}

		public BigDecimal getErrcolCashExistsLimit() {
			return errcolCashExistsLimit;
		}

		public void setErrcolCashExistsLimit(BigDecimal errcolCashExistsLimit) {
			this.errcolCashExistsLimit = errcolCashExistsLimit;
		}

		public boolean isBooRenderColCheque() {
			return booRenderColCheque;
		}

		public void setBooRenderColCheque(boolean booRenderColCheque) {
			this.booRenderColCheque = booRenderColCheque;
		}

		public String getColchequebankCode() {
			return colchequebankCode;
		}

		public void setColchequebankCode(String colchequebankCode) {
			this.colchequebankCode = colchequebankCode;
		}

		public String getColChequeRef() {
			return colChequeRef;
		}

		public void setColChequeRef(String colChequeRef) {
			this.colChequeRef = colChequeRef;
		}

		public Date getColChequeDate() {
			return colChequeDate;
		}

		public void setColChequeDate(Date colChequeDate) {
			this.colChequeDate = colChequeDate;
		}

		public String getColChequeApprovalNo() {
			return colChequeApprovalNo;
		}

		public void setColChequeApprovalNo(String colChequeApprovalNo) {
			this.colChequeApprovalNo = colChequeApprovalNo;
		}

		// checking cheque data with datatable
		public Boolean checkingChequeDuplicateCheck(){
			Boolean checkCheque = false;
			int i = 0;
			if (getColchequebankCode()!=null && coldatatablevalues.size() != 0 ) {
				for (PersonalRemittanceCollectionDataTable lstpaymentdata : coldatatablevalues) {
					i = 0;
					if(lstpaymentdata.getColBankCodeDT()!=null){
						if(lstpaymentdata.getColBankCodeDT().equalsIgnoreCase(getColchequebankCode())){
							if(lstpaymentdata.getColchequeRefNo().equalsIgnoreCase(getColChequeRef())){
								i = 1;
								break;
							}else{
								i = 0;
							}

						}else{
							i = 0;
						}
					}else{
						i = 0;
					}
				}

				if(i == 1){
					checkCheque = false;	
				}else{
					checkCheque = true;	
				}

			}else{
				checkCheque = true;	
			}


			return checkCheque;
		}

	//	private List<ViewBankDetails> localbankListForBankCode = new ArrayList<ViewBankDetails>();
		
		
	
	public List<ViewBankDetails> getLocalbankListForBankCode() {
			return localbankListForBankCode;
		}

		public void setLocalbankListForBankCode(List<ViewBankDetails> localbankListForBankCode) {
			this.localbankListForBankCode = localbankListForBankCode;
		}

		
	// verifying the password with authoried person
	public void verifyPassword() {

		checkcashcollection();

		if(!checkKnetAmount){


			String errorMessage;
			try {
				errorMessage = iPersonalRemittanceService.getExCheckCashLimitProcedure(sessionStateManage.getCountryId(),getCustomerNo(), getColpaymentmodeId(),getColamountKWD());
				System.out.println("errorMessage :" + errorMessage);
				if (errorMessage != null && !errorMessage.equals("")) {
					setExcheckCashLimitMessage(errorMessage);
					RequestContext.getCurrentInstance().execute("exCheckCashLimit.show();");
				} else {
					setExcheckCashLimitMessage(null);

					if(getColpaymentmodeCode() != null){
						List<PaymentMode> paymentModedetails = ipaymentService.getPaymentCheck(getColpaymentmodeCode());

						if (paymentModedetails.size() != 0) {

							if(getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)){
								calculatingNetAmountDT();
							}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)){
								if (getColAuthorizedby() != null) {
									List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();
									String userNames = getColAuthorizedby();
									/*String authorname = new StringBuffer(userNames)	.reverse().toString().toUpperCase();
								lstEmpLogin.addAll(loginService.getLoginInfoForEmployees(sessionStateManage.getCountryId(), getColAuthorizedby(),cypherSecurity.encodePassword(getColpassword(), authorname)));*/


									lstEmpLogin = 	iPersonalRemittanceService.getdebitAutendicationListByUserId(getColAuthorizedby(),getColpassword());				

									if (lstEmpLogin.size() != 0) {
										/*String secretKey = getColpassword().toUpperCase();
									StringBuffer secretKeys = new StringBuffer(secretKey).reverse();
									String cyperpassword = cypherSecurity2.encodePassword(getColpassword(),secretKeys.toString());*/
										//	setColpassword(getColpassword());
										//		setCyberPassword(cyperpassword);
										checkingPaymentCardinDB();
									} else {
										setColpassword(null);
										RequestContext.getCurrentInstance().execute("passwordcheck.show();");
									}
								} else {
									checkingPaymentCardinDB();
								}

							}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){

								Boolean checkdata = checkingChequeDuplicateCheck();

								if(checkdata){
									localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getColchequebankCode());
									addPaymentModerecord();
									if (coldatatablevalues.size() > 0) {
										for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
											totalUamount = totalUamount.add(collectionlst.getColAmountDT());
										}
										setToalUsedAmount(totalUamount);
										if(getToalUsedAmount().compareTo(getCalNetAmountPaid())>0){
											setTotalbalanceAmount(BigDecimal.ZERO);
											setTotalrefundAmount(getToalUsedAmount().subtract(getCalNetAmountPaid()));
										}else if(getToalUsedAmount().compareTo(getCalNetAmountPaid())<0){
											setTotalbalanceAmount(getCalNetAmountPaid().subtract(getToalUsedAmount()));
											setTotalrefundAmount(BigDecimal.ZERO);
										}else{
											setTotalbalanceAmount(BigDecimal.ZERO);
											setTotalrefundAmount(BigDecimal.ZERO);
										}

									}
									setBooRendercollectiondatatable(true);
									clearingDetailAfterAdding();
								}else{
									RequestContext.getCurrentInstance().execute("chequerefexists.show();");
								}
							}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)){
								System.out.println("BANK TRANSFER");
							}else{
								System.out.println("Other Payment Mode");
							}

						}
					}else{
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
					}


					/*BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME, new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
				if (paymentModeCashId != null) {
					if (getColpaymentmodeId().compareTo(paymentModeCashId) == 0) {
						calculatingNetAmountDT();
					} else {
						if (getColAuthorizedby() != null) {
							List<Employee> lstEmpLogin = new ArrayList<Employee>();
							String userNames = getColAuthorizedby();
							String authorname = new StringBuffer(userNames)
							.reverse().toString().toUpperCase();
							lstEmpLogin.addAll(loginService.getLoginInfoForEmployees(sessionStateManage.getCountryId(), getColAuthorizedby(),cypherSecurity.encodePassword(getColpassword(), authorname)));
							if (lstEmpLogin.size() != 0) {
								iCypherSecurity cypherSecurity2 = new CypherSecurityImpl();
								String secretKey = getColpassword().toUpperCase();
								StringBuffer secretKeys = new StringBuffer(secretKey).reverse();
								String cyperpassword = cypherSecurity2.encodePassword(getColpassword(),secretKeys.toString());
								setColpassword(getColpassword());
								setCyberPassword(cyperpassword);
								checkingPaymentCardinDB();
							} else {
								setColpassword(null);
								RequestContext.getCurrentInstance().execute("passwordcheck.show();");
							}
						} else {
							checkingPaymentCardinDB();
						}
					}
				}*/
				}
			} catch (AMGException e) {
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}
		}
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

	// Check Telephone Existed for particular customer -- 06/02/2015 End-Ram
	// Mohan
	
	/** TO STORE THE NEW BENIFICARY CUSTOMER DETAILS TO CUSTOMER BANK **/
	public void saveCustomerDetailsToCustomerBank() {

		CustomerBank customerBank = new CustomerBank();

		customerBank.setCollectionMode(Constants.COLLECTIONMODE);

		if(localbankListForBankCode.size() != 0){
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(localbankListForBankCode.get(0).getChequeBankId()); 
			customerBank.setFsBankMaster(bankMaster);
		}

		customerBank.setBankCode(getColBankCode()); // this is fixed//generalService.getBankCode(getColBankid()));

		Customer customer = new Customer();
		customer.setCustomerId(getCustomerNo());
		customerBank.setFsCustomer(customer);
		customerBank.setAuthorizedBy(getColAuthorizedby());
		customerBank.setAuthorizedDate(new Date());
		customerBank.setDebitCard(encryptionDescriptionService.getENCrypted(getColNameofCard(), getColCardNo().toString()));
		customerBank.setDebitCardName(getColNameofCard());
		customerBank.setPassword(getCyberPassword());
		customerBank.setIsActive(Constants.Yes);
		customerBank.setCreatedBy(sessionStateManage.getUserName());
		customerBank.setCreatedDate(new Date());
		customerBank.setCustomerReference(iglTransactionForADocument.getCustomeReference(getCustomerNo()));
		// customerBank.setModifiedBy(null);
		// customerBank.setModifiedDate(null);
		icustomerBankService.save(customerBank);
		calculatingNetAmountDT();
		// RequestContext.getCurrentInstance().execute("locbankid.show();");
	}

	public void checkingPaymentCardinDB() {
		if (lstDebitCard.size() != 0) {
			int i = 0;
			for (CustomerBank lstDebit : lstDebitCard) {
				if (lstDebit.getDebitCard().equalsIgnoreCase(getColCardNo().toString())) {
					i = 1;
					break;
				} else {
					i = 0;
				}
			}
			if (i == 0) {
				// RequestContext.getCurrentInstance().execute("addDebitCard.show();");
				saveCustomerDetailsToCustomerBank();
			} else {
				calculatingNetAmountDT();
			}
		} else {
			if (getPopulatedDebitCardNumber() != null) {
				if (getPopulatedDebitCardNumber().compareTo(getColCardNo()) == 0) {
					calculatingNetAmountDT();
				} else {
					// RequestContext.getCurrentInstance().execute("addDebitCard.show();");
					saveCustomerDetailsToCustomerBank();
				}
			} else {
				// RequestContext.getCurrentInstance().execute("addDebitCard.show();");
				saveCustomerDetailsToCustomerBank();
			}
		}
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
			} else if (getAge() != null && getYearOfBrith().toPlainString().length() != 0) {
				setYearOfBrith(null);
				RequestContext.getCurrentInstance().execute("age.show();");
				return;
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

	public List<ViewBeneServiceCurrency> getBeneServiceCurrencyList() {
		return beneServiceCurrencyList;
	}

	public void setBeneServiceCurrencyList(List<ViewBeneServiceCurrency> beneServiceCurrencyList) {
		this.beneServiceCurrencyList = beneServiceCurrencyList;
	}

	public void benServiceCurrencyList() {
		beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(getBenifisCountryId());
	}

	public List<PaymentModeDesc> getLstFetchAllPayMode() {
		  return lstFetchAllPayMode;
	}
	public void setLstFetchAllPayMode(List<PaymentModeDesc> lstFetchAllPayMode) {
	  this.lstFetchAllPayMode = lstFetchAllPayMode;
	}

	public void updateRemittanceTransactionSelectedList(HashMap<String, Object> returnResult) {
		Collect collect = (Collect) returnResult.get("Collect");
		List<ShoppingCartDataTableBean> lstToUpdate = new ArrayList<ShoppingCartDataTableBean>();
		lstToUpdate.clear();
		for (ShoppingCartDataTableBean shoppingCartDetails : lstselectedrecord) {
			// if(shoppingCartDetails.getApplicationType().equalsIgnoreCase("R")){
			System.out.println(shoppingCartDetails.getRemittanceApplicationId());
			System.out.println(shoppingCartDetails.getDocumentFinanceYear());
			System.out.println(shoppingCartDetails.getDocumentNo());
			lstToUpdate.add(shoppingCartDetails);
			// }
		}
		iPersonalRemittanceService.updateRemitTranxTable(collect, lstToUpdate);
	}

	// updating utilized amount
	public void updatingUtilizedAmount() {
		BigDecimal totalUtilizedAmount = BigDecimal.ZERO;
		BigDecimal custSplPk = null;
		if (personalRemittCalFCAmountDTList.size() != 0) {
			for (PersonalRemittanceCalFCAmountDataTable lstofUtilizedAmount : personalRemittCalFCAmountDTList) {
				if (lstofUtilizedAmount.getSelect()) {
					totalUtilizedAmount = totalUtilizedAmount.add(lstofUtilizedAmount.getUtilizedAmount());
					System.out.println(lstofUtilizedAmount.getUtilizedAmount());
					custSplPk = lstofUtilizedAmount.getSpecialCustomerPrimaryKey();
				}
			}
			if (custSplPk != null) {
				totalUtilizedAmount = totalUtilizedAmount.add(getAmountToRemit());
				iPersonalRemittanceService.saveUpdateCustSplDealUtilizedAmount(custSplPk, totalUtilizedAmount);
			}
		}
	}

	public void deleteRecordShoppingCart(ShoppingCartDataTableBean shoppingCartData) {
		System.out.println(shoppingCartData.getRemittanceApplicationId());
		iPersonalRemittanceService.deleteShoppingCartByApplId(shoppingCartData.getRemittanceApplicationId());
		getShoppingCartDetails(getCustomerNo());
	}

	public void fetchSmartCardIdNumber() throws ParseException {
		if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			smartCardDisplay(ipAddress, "8085", "M", "test");
			// smartCardDisplay("10.200.4.69", "8085", "M", "test");
		}
	}

	public String smartCardDisplay(String host, String prdPort, String requestType, String env) throws ParseException {
		StringBuffer sb = new StringBuffer();
		StringBuffer urlBuffer = new StringBuffer();
		String appender = "?";
		String ampersand = "&";
		String equals = "=";
		String colon = ":";
		String rootContext = "/KwtSmartCard/SmartCartServ"; // KwtSmartCard/smartcard
		if (env.equalsIgnoreCase("test")) {
			urlBuffer.append("http://").append(host).append(colon).append(prdPort).append(rootContext).append(appender);
		} else if (env.equalsIgnoreCase("live")) {
			urlBuffer.append("https://").append(host);
			if (prdPort != null && prdPort.length() > 0) {
				urlBuffer.append(colon).append(prdPort);
			}
			urlBuffer.append(rootContext).append(appender);
		}
		urlBuffer.append("type").append(equals).append("M").append(ampersand);
		try {
			URL knetRequest = new URL(urlBuffer.toString());
			HttpURLConnection testyc = null;
			HttpsURLConnection prdyc = null;
			BufferedReader in = null;
			if (env.equalsIgnoreCase("test")) {
				testyc = (HttpURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(testyc.getInputStream()));
			} else if (env.equalsIgnoreCase("live")) {
				prdyc = (HttpsURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(prdyc.getInputStream()));
			}
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine + "##");
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] str = sb.toString().split("#");
		if (str.length > 1) {
			for (int i = 0; i < str.length; i++) {
				String string = str[i];
				// System.out.println("str :"+string);
				if (i == 8) {
					System.out.println("Civil Id ");
					String[] parts = string.split("=");
					String part1 = parts[0]; // 004
					String part2 = parts[1];
					setIdNumber(part2);
					setSelectCard(generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
				}
			}
		} else {
			RequestContext.getCurrentInstance().execute("dldInserCard.show();");
		}
		return sb.toString();
	}
	Boolean visible ;

	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public void backtoFCSale() {
		log.info("Entering into backtoFCSale method ");
		setVisible(false);
		HttpSession session = sessionStateManage.getSession();
		String error = (String) session.getAttribute("error");
		System.out.println(error);
		System.out.println(error);
		if(error!=null && error.length()!=0)
		{
			session.removeAttribute("error");
			log.info("#################################");
			try {
				goFromOldSmartCardpanel();
			}
			catch(Exception e)
			{
				log.info("Exception occured "+e);
			}
			setVisible(true);
			RequestContext.getCurrentInstance().execute("PF('error').show();");
			setErrmsg(error);
			return;
		}
		String backToRemitance = (String) session.getAttribute("backToFC");
		String backToRemitanceFirstPanel = (String) session.getAttribute("cbackToRemitanceFirstPanel");
		if (backToRemitance != null && backToRemitance.equals("yes")) {
			log.info("Back to nextrenderingLastPanel ");
			getShoppingCartDetails(sessionStateManage.getCustomerId());
			lstselectedrecord = new CopyOnWriteArrayList<ShoppingCartDataTableBean>();
			nextrenderingLastPanel();
			session.setAttribute("backToFC", "no");
		} else if (backToRemitanceFirstPanel != null && backToRemitanceFirstPanel.equals("yes")) {
			try {
				log.info("***************backToRemitanceFirstPanel *****************");
				fromAccountExistDialogToBeneficaryTelephone();
			} catch (Exception e) {
				// log.info("Exception occured"+e);
			}
			session.setAttribute("cbackToRemitanceFirstPanel", "no");
		} else {
			log.info("backtoFCSale method");
		}
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cfromBeneficary") != null) {
			String cfromBeneficary = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cfromBeneficary").toString();
			if (cfromBeneficary != null && cfromBeneficary.equals("yes")) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cfromBeneficary");
				log.info("#################################");
				try {
					goFromOldSmartCardpanel();
				} catch (Exception e) {
					log.info("Exception occured " + e);
				}
			}
		}
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cexitBeneficary") != null) {
			String cexitBeneficary = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cexitBeneficary").toString();
			if (cexitBeneficary != null && cexitBeneficary.equals("yes")) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cexitBeneficary");
				log.info("exit from beneficary creation ");
				try {
					corporateRemittancePageNavigation();
				} catch (Exception e) {
					log.info("Exception occured " + e);
				}
			}
		}
	}

	public void editDebitCardtoEnter() {
		// lstDebitCard.clear();
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
	}

	public Boolean getMainPanelRender() {
		return mainPanelRender;
	}

	public void setMainPanelRender(Boolean mainPanelRender) {
		this.mainPanelRender = mainPanelRender;
	}

	public Boolean getReceiptReportPanel() {
		return receiptReportPanel;
	}

	public void setReceiptReportPanel(Boolean receiptReportPanel) {
		this.receiptReportPanel = receiptReportPanel;
	}

	/*
	 * public Boolean getApplicationReportPanel() { return
	 * applicationReportPanel; }
	 * 
	 * public void setApplicationReportPanel(Boolean applicationReportPanel) {
	 * this.applicationReportPanel = applicationReportPanel; }
	 */
	public void remittanceApplicationReportInit() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceApplicationReportList);
		System.out.println("Report  String getApplicationContextPath :"+ FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
		
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceApplication.jasper");
		System.out.println("String reportPath :"+ reportPath);
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void generatePersonalRemittanceApplicationReport(ShoppingCartDataTableBean shoppingCartDataTableBean) throws JRException, IOException, ParseException {
		ServletOutputStream servletOutputStream=null;
		try {
			fetchApplicationRemittanceReportData(shoppingCartDataTableBean.getDocumentNo());
			remittanceApplicationReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=RemittanceApplicationReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			System.out.println("exception" + e.getMessage());
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
	}

	public void remittanceReceiptReportInit() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptReportList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceipt.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}

	public void generatePersonalRemittanceReceiptReport(ActionEvent actionEvent) throws JRException, IOException, ParseException {
		ServletOutputStream servletOutputStream=null;
		try {
			fetchRemittanceReceiptReportData(new BigDecimal(getDocumentNo().toString()));
			remittanceReceiptReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=RemittanceReceiptReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			System.out.println("exception" + e.getMessage());
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
	}

	private List<RemittanceReportBean> fetchApplicationRemittanceReportData(BigDecimal documentNumber){


		remittanceApplicationReportList.clear();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		List<ShoppingCartDetails> remittanceViewlist = iPersonalRemittanceService.getRecordsRemittanceApplication(documentNumber);
		String purposeOfRemittance = "";
		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));


		if (remittanceViewlist.size() > 0) {
			for (ShoppingCartDetails view : remittanceViewlist) {
				RemittanceReportBean obj = new RemittanceReportBean();
				String applicationNo = view.getDocumentFinanceYear().toPlainString()+ "/" + view.getDocumentNo().toPlainString();
				obj.setApplicationNo(applicationNo);
				obj.setDate(currentDate);
				String foreignCurrencyQuoteName = generalService.getCurrencyQuote(view.getForeignCurrency());
				if(view.getForeignTranxAmount()!=null){
					BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTranxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrency()));
					obj.setProposedCurrencyAndAmount(foreignCurrencyQuoteName+"     ******"+foreignTransationAmount.toString());
				}
				if(view.getExchangeRateApplied()!=null){
					obj.setProposedExchangeRate(foreignCurrencyQuoteName+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
				}
				if(view.getLocalTranxAmount()!=null){
					BigDecimal localTranxAmount=GetRound.roundBigDecimal((view.getLocalTranxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setProposedKWDAmount(currencyQuoteName+"     ******"+localTranxAmount.toString());
				}
				if(view.getLocalCommisionAmount()!=null){
					BigDecimal localCommisionAmount=GetRound.roundBigDecimal((view.getLocalCommisionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setProposedCommission(currencyQuoteName+"     ******"+localCommisionAmount.toString());
				}
				if(view.getLocalChargeAmount()!=null){
					BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalCommisionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setProposedOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
				}
				if(view.getLocalNextTranxAmount()!=null){
					BigDecimal localNextTranxAmount=GetRound.roundBigDecimal((view.getLocalCommisionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setTotalKWDAmount(currencyQuoteName+"     ******"+localNextTranxAmount.toString());
				}
				obj.setFutherInstructions(view.getInstruction());

				obj.setBeneSwiftBank1(view.getBeneficiarySwiftBankOne());
				obj.setBeneSwiftBank2(view.getBeneficiarySwiftBankTwo());
				obj.setBeneSwiftAddr1(view.getBeneficiarySwiftAddrOne());
				obj.setBeneSwiftAddr2(view.getBeneficiarySwiftAddrTwo());

				if (view.getBeneficiaryFirstName() != null && view.getBeneficiarySecondName() != null) {
					obj.setBeneficiaryName(view.getBeneficiaryFirstName() + " " + view.getBeneficiarySecondName());
				} else if (view.getBeneficiaryFirstName() == null && view.getBeneficiarySecondName() != null) {
					obj.setBeneficiaryName(view.getBeneficiarySecondName());
				} else if (view.getBeneficiaryFirstName() != null && view.getBeneficiarySecondName() == null) {
					obj.setBeneficiaryName(view.getBeneficiaryFirstName());
				}


				if (view.getRemittanceDescription() != null && view.getDeliveryDescription() != null) {
					obj.setBenefPaymentMode(view.getRemittanceDescription()+ " - " + view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() == null && view.getDeliveryDescription() != null) {
					obj.setBenefPaymentMode(view.getDeliveryDescription());
				} else if (view.getRemittanceDescription() != null && view.getDeliveryDescription() == null) {
					obj.setBenefPaymentMode(view.getRemittanceDescription());
				}
				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryAccountNumber(view.getBeneficiaryAccountNo());
				List<RemittanceApplicationPurpose>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittanceForApplication(sessionStateManage.getCountryId(),view.getDocumentFinanceYear(),view.getDocumentId(),view.getDocumentNo());

				for(RemittanceApplicationPurpose purposeObj :purposeOfRemittanceList){
					if(purposeOfRemittance.equals("")){
						purposeOfRemittance  = purposeObj.getAmiecDescription();
					}else{
						purposeOfRemittance  = purposeOfRemittance+", "+purposeObj.getAmiecDescription();
					}
				}
				obj.setPurposeOfRemittance(purposeOfRemittance);

				if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + "," + view.getBeneDistrictName() + ","+ view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneDistrictName() + "," + view.getBeneStateName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
					obj.setAddress(view.getBeneCityName() + ", " + view.getBeneStateName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName());
				} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneDistrictName());
				} else if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
					obj.setAddress(view.getBeneCityName() + ", "+ view.getBeneDistrictName());
				}

				obj.setIncomeSource(view.getSourceOfIncomeDesc());
				// obj.setSignature(view.getCustomerSignature());
				// Added by Rabil
				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerList.size() > 0) {
					obj.setSenderName(customerList.get(0).getCustomerName());
					obj.setContactNumber(customerList.get(0).getContactNumber());
					obj.setCivilId(customerList.get(0).getIdNumber());
					Date sysdate = customerList.get(0).getIdExp();
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));
				}

				remittanceApplicationReportList.add(obj);
			}
		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
		}
		return remittanceApplicationReportList;

	}

	private void fetchRemittanceReceiptReportData(BigDecimal documentNumber){
		remittanceReceiptReportList.clear();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsRemittanceReceipt(documentNumber);
		if(remittanceViewlist.size()>0){
			int i=0;
			if(i==0){
				for(RemittanceApplicationView view:remittanceViewlist){
					i++;
					RemittanceReportBean obj=new RemittanceReportBean();
					obj.setCustomerId(view.getCustomerId());
					obj.setFirstName(view.getFirstName());
					obj.setMiddleName(view.getMiddleName());
					if(view.getContactNumber() != null){
						obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
					}
					obj.setCivilId(view.getIdentityInt());
					Date sysdate = view.getIdentityExpiryDate();
					obj.setIdExpiryDate(new SimpleDateFormat("dd-MM-yyyy").format(sysdate));
					obj.setInsurence1(" ");
					obj.setInsurence2(" ");
					obj.setLocation(view.getBenefeciaryBranch());
					//	obj.setPhoneNumber("");
					obj.setDate(currentDate);

					if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
						obj.setReceiptNo(view.getDocumentFinancialYear()+"/"+view.getCollectionDocumentNo());
					}else if(view.getCollectionDocumentNo()!=null){
						obj.setReceiptNo(view.getCollectionDocumentNo().toString());
					}


					if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
						obj.setTransactionNo(view.getDocumentFinancialYear()+"/"+view.getDocumentNo());
					}else if(view.getDocumentNo()!=null){
						obj.setTransactionNo(view.getDocumentNo().toString());
					}
					obj.setBeneficiaryName(view.getBeneficiaryName());
					obj.setBenefeciaryBankName(view.getBeneficiaryBank());
					obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
					obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
					obj.setAddress(" ");
					obj.setPerposeOfRemittance(" ");
					obj.setPaymentChannel(" ");
					String currencyAndAmount = "INR"+view.getNetAmount().toPlainString();
					obj.setCurrencyAndAmount(currencyAndAmount);
					//	obj.setExchangeRate(view.getExchangeRateApplied());

					BigDecimal transationAmount=round((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLocalTransactionAmount("KWD"+"     "+transationAmount.toString());

					BigDecimal localCommitionAmount=round((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setCommision("KWD"+"     "+localCommitionAmount.toString());

					BigDecimal localChargeAmount=round((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setOtherCharges("KWD"+"     "+localChargeAmount.toString());

					BigDecimal netAmount=round((view.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setTotalAmount("KWD"+"     "+netAmount.toString());
					obj.setFutherInstructions(" ");
					obj.setSourceOfIncome(" ");
					obj.setIntermediataryBank(view.getBenefeciaryInterBank1());
					obj.setAmountPaid(view.getPaidAmount());
					obj.setAmountRefund(view.getRefundedAmount());
					obj.setNetPaidAmount(view.getNetAmount());
					//	obj.setPaymentMode(view.getCollectionMode());


					remittanceReceiptReportList.add(obj);
				}
			}
		}else{
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
			return;
		}
	}


	public static BigDecimal round(BigDecimal value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = value;
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd;
	}

	public void editBeneficaray(PersonalRemmitanceBeneficaryDataTable datatabledetails) {
		
		log.info("Exit into edit method ");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.getSessionMap().put("datatabledetails", datatabledetails);
		try {
			context.redirect("../beneficary/corporatebeneficaryedit.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	public void backtoPersonalRemit() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect("../remittance/CorporateRemittance.xhtml");
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

	private String digitalSignature;// new property for digital sign
	private String signatureSpecimen; // property :Signature Specimen

	public String getSignatureSpecimen() {
		return signatureSpecimen;
	}

	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}

	public String getDigitalSignature() {
		return digitalSignature;
	}

	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

	public void nextAmlCheckToCustomerSignature() {
		try {
			setVisible(false);
			setBooSpecialCusFCCalDataTable(false);
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBooRenderBenificaryStatusPanel(false);
			setBooRenderIndBenificaryStatusPanel(false);
			setBooRenderLayaltyServicePanel(false);
			setBooRenderNonIndBenificaryStatusPanel(false);
			setBooRenderRemittanceServicePanel(false);
			setBooRenderTypeOfServicePanel(false);
			// setApplicationReportPanel(false);
			setAmlboo(false);
			setAmlboomsg(false);
			setBooRenderCustomerSignaturePanel(true);
			customerSignatureSpecimen();
		} catch (Exception e) {
			setErrmsg("Exception occurred "+e);
			setVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public void customerSignatureSpecimen() {
		List<CustomerIdProof> data = getForeignCurrencyPurchaseService().dataCust(getIdNumber().toString());
		if (data != null && data.size() > 0) {
			setSignatureSpecimen(data.get(0).getFsCustomer().getSignatureSpecimen());
		}
	}

	public void popDistictwtihCheckDuplicateCash() {
		List<BeneficaryAccount> beneficiaryAccountDetailList = getiPersonalRemittanceService().isCashAccountExist(getBenifisBankId(), getBenifisCurrencyId(), getBenifisCountryId(), getServicebankBranchId());
		if (beneficiaryAccountDetailList.size() > 0 /*
		 * &&
		 * getBeneficaryTypeId().equals
		 * (new BigDecimal(1))
		 */) {
			BigDecimal masterId = beneficiaryAccountDetailList.get(0).getBeneficaryMaster().getBeneficaryMasterSeqId();
			List<BeneficaryRelationship> masterList = getiPersonalRemittanceService().getCustomerBeneficaryDetailswithRelations(masterId);
			if (masterList.get(0).getCustomerId().getCustomerId().equals(getCustomerNo())) {
				RequestContext.getCurrentInstance().execute("cashcheck.show();");
				setErrmsg("Data already exist for this combination");
				return;
			}
		}
		popbranchcode();
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

	public void roundingShoppingCartList() {

		BigDecimal differenceNetAmount = BigDecimal.ZERO;
		BigDecimal upNetAmount = BigDecimal.ZERO;
		BigDecimal downNetAmount = BigDecimal.ZERO;
		String roundtype = null;
		setBooRenderModifiedRoundData(false);
		lstModifyRoundRecord.clear();

		if (getCashRounding() == null) {
			setCashRounding(Constants.U);

			BigDecimal roundingValue;
			try {
				roundingValue = iPersonalRemittanceService.roundingTotalNetAmountbyFunction(sessionmanage.getCountryId(),getCalNetAmountPaid(), getCashRounding());

				if (roundingValue != null) {
					if (getCalNetAmountPaid().compareTo(roundingValue) == 0) {
						setBooShowCashRoundingPanel(false);
						setCashRounding(null);
						// setApplicationDocNum(null);
					} else {
						setBooShowCashRoundingPanel(true);
						setCashRounding(null);
						// setApplicationDocNum(null);
					}
				} else {
					// round value null
					setCashRounding(null);
					RequestContext.getCurrentInstance().execute(
							"roundValueErr.show();");
				}

			} catch (AMGException e) {
				setExceptionMessage(e.getMessage());
				System.out.println("*******Error message ********"+ getExceptionMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}

		} else {

			if (getShoppingcartExchangeRate() != null) {
				BigDecimal roundingValue;
				try {
					roundingValue = iPersonalRemittanceService.roundingTotalNetAmountbyFunction(sessionmanage.getCountryId(),getDummiTotalNetAmount(), getCashRounding());

					if (roundingValue != null) {
						if (getDummiTotalNetAmount().compareTo(roundingValue) < 0) {
							differenceNetAmount = roundingValue
									.subtract(getDummiTotalNetAmount());
							int decimalvalue = foreignLocalCurrencyDenominationService
									.getDecimalPerCurrency(new BigDecimal(
											sessionmanage.getCurrencyId()));
							upNetAmount = differenceNetAmount.divide(
									getShoppingcartExchangeRate(), decimalvalue,
									BigDecimal.ROUND_HALF_UP);
							setCalNetAmountPaid(GetRound.roundBigDecimal(
									roundingValue, decimalvalue));
							setCalGrossAmount(getDummiTotalGrossAmount().add(
									differenceNetAmount));
							roundtype = Constants.UP;
							lstModifyRoundRecord.clear();
							if (lstselectedrecord.size() == 1) {
								ShoppingCartDataTableBean selectedrec = lstselectedrecord
										.get(0);
								ShoppingCartDataTableBean lstModifiedData = changeRoundCalculationinShoppingCart(
										selectedrec, upNetAmount, roundtype,
										differenceNetAmount);
								lstModifyRoundRecord.add(lstModifiedData);
								setBooRenderModifiedRoundData(true);
							}

						} else {
							differenceNetAmount = getDummiTotalNetAmount()
									.subtract(roundingValue);
							int decimalvalue = foreignLocalCurrencyDenominationService
									.getDecimalPerCurrency(new BigDecimal(
											sessionmanage.getCurrencyId()));
							downNetAmount = differenceNetAmount.divide(
									getShoppingcartExchangeRate(), decimalvalue,
									BigDecimal.ROUND_HALF_UP);
							setCalNetAmountPaid(GetRound.roundBigDecimal(
									roundingValue, decimalvalue));
							setCalGrossAmount(getDummiTotalGrossAmount().subtract(
									differenceNetAmount));
							roundtype = Constants.DOWN;
							lstModifyRoundRecord.clear();
							if (lstselectedrecord.size() == 1) {
								ShoppingCartDataTableBean selectedrec = lstselectedrecord
										.get(0);
								ShoppingCartDataTableBean lstModifiedData = changeRoundCalculationinShoppingCart(
										selectedrec, downNetAmount, roundtype,
										differenceNetAmount);
								lstModifyRoundRecord.add(lstModifiedData);
								setBooRenderModifiedRoundData(true);
							}
						}
					} else {
						// round value null
						setCashRounding(null);
						RequestContext.getCurrentInstance().execute(
								"roundValueErr.show();");
					}
				} catch (AMGException e) {
					setExceptionMessage(e.getMessage());
					System.out.println("*******Error message ********"+ getExceptionMessage());
					RequestContext.getCurrentInstance().execute("sqlexception.show();");
				}



			} else {
				if (getApplicationDocNum() != null) {
					if (lstselectedrecord.size() != 0) {

						for (ShoppingCartDataTableBean selectedrec : lstselectedrecord) {

							if (getApplicationDocNum().compareTo(
									selectedrec.getDocumentNo()) == 0) {
								BigDecimal roundingValue;
								try {
									roundingValue = iPersonalRemittanceService.roundingTotalNetAmountbyFunction(	sessionmanage.getCountryId(),getDummiTotalNetAmount(),getCashRounding());

									if (roundingValue != null) {
										if (getDummiTotalNetAmount().compareTo(
												roundingValue) < 0) {
											differenceNetAmount = roundingValue
													.subtract(getDummiTotalNetAmount());
											int decimalvalue = foreignLocalCurrencyDenominationService
													.getDecimalPerCurrency(new BigDecimal(
															sessionmanage
															.getCurrencyId()));
											upNetAmount = differenceNetAmount
													.divide(selectedrec
															.getExchangeRateApplied(),
															decimalvalue,
															BigDecimal.ROUND_HALF_UP);
											setCalNetAmountPaid(GetRound
													.roundBigDecimal(roundingValue,
															decimalvalue));
											setCalGrossAmount(getDummiTotalGrossAmount()
													.add(differenceNetAmount));
											roundtype = Constants.UP;
											lstModifyRoundRecord.clear();
											ShoppingCartDataTableBean lstModifiedData = changeRoundCalculationinShoppingCart(
													selectedrec, upNetAmount,
													roundtype, differenceNetAmount);
											lstModifyRoundRecord
											.add(lstModifiedData);
											setBooRenderModifiedRoundData(true);

										} else {
											differenceNetAmount = getDummiTotalNetAmount()
													.subtract(roundingValue);
											int decimalvalue = foreignLocalCurrencyDenominationService
													.getDecimalPerCurrency(new BigDecimal(
															sessionmanage
															.getCurrencyId()));
											downNetAmount = differenceNetAmount
													.divide(selectedrec
															.getExchangeRateApplied(),
															decimalvalue,
															BigDecimal.ROUND_HALF_UP);
											setCalNetAmountPaid(GetRound
													.roundBigDecimal(roundingValue,
															decimalvalue));
											setCalGrossAmount(getDummiTotalGrossAmount()
													.subtract(differenceNetAmount));
											roundtype = Constants.DOWN;
											lstModifyRoundRecord.clear();
											ShoppingCartDataTableBean lstModifiedData = changeRoundCalculationinShoppingCart(
													selectedrec, downNetAmount,
													roundtype, differenceNetAmount);
											lstModifyRoundRecord
											.add(lstModifiedData);
											setBooRenderModifiedRoundData(true);
										}
									} else {
										// round value null
										setCashRounding(null);
										RequestContext.getCurrentInstance()
										.execute("roundValueErr.show();");
									}
								} catch (AMGException e) {
									setExceptionMessage(e.getMessage());
									System.out.println("*******Error message ********"+ getExceptionMessage());
									RequestContext.getCurrentInstance().execute("sqlexception.show();");
								}
							}
						}
					}
				}
			}
		}
	}

	public ShoppingCartDataTableBean changeRoundCalculationinShoppingCart(ShoppingCartDataTableBean shoppingCartDetails, BigDecimal netAmount, String roundtype, BigDecimal differenceNetAmount) {
		ShoppingCartDataTableBean shoppingCartDataTableBean = new ShoppingCartDataTableBean();
		shoppingCartDataTableBean.setRemittanceApplicationId(shoppingCartDetails.getRemittanceApplicationId());
		shoppingCartDataTableBean.setApplicationType(shoppingCartDetails.getApplicationType());
		shoppingCartDataTableBean.setApplicationTypeDesc(shoppingCartDetails.getApplicationTypeDesc());
		shoppingCartDataTableBean.setBeneficiaryAccountNo(shoppingCartDetails.getBeneficiaryAccountNo());
		shoppingCartDataTableBean.setBeneficiaryBank(shoppingCartDetails.getBeneficiaryBank());
		shoppingCartDataTableBean.setBeneficiaryBranch(shoppingCartDetails.getBeneficiaryBranch());
		shoppingCartDataTableBean.setBeneficiaryFirstName(shoppingCartDetails.getBeneficiaryFirstName());
		shoppingCartDataTableBean.setBeneficiarySecondName(shoppingCartDetails.getBeneficiarySecondName());
		shoppingCartDataTableBean.setBeneficiaryThirdName(shoppingCartDetails.getBeneficiaryThirdName());
		shoppingCartDataTableBean.setBeneficiaryFourthName(shoppingCartDetails.getBeneficiaryFourthName());
		shoppingCartDataTableBean.setBeneficiaryId(shoppingCartDetails.getBeneficiaryId());
		shoppingCartDataTableBean.setBeneficiaryInterBankOne(shoppingCartDetails.getBeneficiaryInterBankOne());
		shoppingCartDataTableBean.setBeneficiaryInterBankTwo(shoppingCartDetails.getBeneficiaryInterBankTwo());
		shoppingCartDataTableBean.setBeneficiarySwiftBankOne(shoppingCartDetails.getBeneficiarySwiftBankOne());
		shoppingCartDataTableBean.setBeneficiarySwiftBankTwo(shoppingCartDetails.getBeneficiarySwiftBankTwo());
		shoppingCartDataTableBean.setBeneficiaryName(shoppingCartDetails.getBeneficiaryName());
		shoppingCartDataTableBean.setCompanyId(shoppingCartDetails.getCompanyId());
		shoppingCartDataTableBean.setDocumentFinanceYear(shoppingCartDetails.getDocumentFinanceYear());
		shoppingCartDataTableBean.setDocumentId(shoppingCartDetails.getDocumentId());
		shoppingCartDataTableBean.setForeigncurrency(shoppingCartDetails.getForeigncurrency());
		if (roundtype.equalsIgnoreCase(Constants.UP)) {
			shoppingCartDataTableBean.setLocalTranxAmount(shoppingCartDetails.getLocalTranxAmount().add(differenceNetAmount));
			shoppingCartDataTableBean.setLocalNextTranxAmount(shoppingCartDetails.getLocalNextTranxAmount().add(differenceNetAmount));
			shoppingCartDataTableBean.setForeignTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getForeignTranxAmount().add(netAmount), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(shoppingCartDetails.getForeigncurrency())));
		} else {
			shoppingCartDataTableBean.setLocalTranxAmount(shoppingCartDetails.getLocalTranxAmount().subtract(differenceNetAmount));
			shoppingCartDataTableBean.setLocalNextTranxAmount(shoppingCartDetails.getLocalNextTranxAmount().subtract(differenceNetAmount));
			shoppingCartDataTableBean.setForeignTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getForeignTranxAmount().subtract(netAmount), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(shoppingCartDetails.getForeigncurrency())));
		}
		shoppingCartDataTableBean.setLocalChargeAmount(shoppingCartDetails.getLocalChargeAmount());
		shoppingCartDataTableBean.setLocalCommisionAmount(shoppingCartDetails.getLocalCommisionAmount());
		shoppingCartDataTableBean.setLocalDeliveryAmount(shoppingCartDetails.getLocalDeliveryAmount());
		shoppingCartDataTableBean.setIsActive(shoppingCartDetails.getIsActive());
		shoppingCartDataTableBean.setCustomerId(shoppingCartDetails.getCustomerId());
		shoppingCartDataTableBean.setExchangeRateApplied(shoppingCartDetails.getExchangeRateApplied());
		shoppingCartDataTableBean.setApplicationDetailsId(shoppingCartDetails.getApplicationDetailsId());
		shoppingCartDataTableBean.setDocumentNo(shoppingCartDetails.getDocumentNo());
		shoppingCartDataTableBean.setLocalcurrency(shoppingCartDetails.getLocalcurrency());
		if (shoppingCartDetails.getSpldeal() != null) {
			shoppingCartDataTableBean.setSpldeal(shoppingCartDetails.getSpldeal());
			shoppingCartDataTableBean.setSpldealStatus(Constants.YES);
		} else {
			shoppingCartDataTableBean.setSpldealStatus(Constants.NO);
		}
		shoppingCartDataTableBean.setSelectedrecord(Boolean.FALSE);
		shoppingCartDataTableBean.setDeleteStatus(Boolean.TRUE);
		return shoppingCartDataTableBean;
	}

	public void generatePersonalRemittanceApplicationModified(ActionEvent actionEvent) throws JRException, IOException, ParseException {
		ServletOutputStream servletOutputStream=null;
		try {
			remittanceApplicationReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=RemittanceApplicationReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			System.out.println("exception" + e.getMessage());
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
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


	// if payment mode is CHEQUE

	private Date chequeDate;
	private String chequeRefNo;
	private String chequeBankAcountNumber;
	private String chequeBank;

	private Boolean chequePanel=false;

	private Boolean chequeSelectionCheck = false;



	public Boolean getChequeSelectionCheck() {
		return chequeSelectionCheck;
	}

	public void setChequeSelectionCheck(Boolean chequeSelectionCheck) {
		this.chequeSelectionCheck = chequeSelectionCheck;
	}

	public String getChequeBankAcountNumber() {
		return chequeBankAcountNumber;
	}

	public void setChequeBankAcountNumber(String chequeBankAcountNumber) {
		this.chequeBankAcountNumber = chequeBankAcountNumber;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public String getChequeRefNo() {
		return chequeRefNo;
	}

	public String getChequeBank() {
		return chequeBank;
	}

	public Boolean getChequePanel() {
		return chequePanel;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public void setChequeRefNo(String chequeRefNo) {
		this.chequeRefNo = chequeRefNo;
	}

	public void setChequeBank(String chequeBank) {
		this.chequeBank = chequeBank;
	}

	public void setChequePanel(Boolean chequePanel) {
		this.chequePanel = chequePanel;
	}





	public void clearChequePanel(){
		setChequeDate(null);
		setChequeRefNo(null);
		setChequeBankAcountNumber(null);
		setChequeBank(null);
		setChequePanel(false);

	}


	public void editDifferentBankAccount(PersonalRemmitanceBeneficaryDataTable datatabledetails) {
		log.info("Exit into edit method ");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.getSessionMap().put("cdatatabledetail", datatabledetails);
		context.getSessionMap().put("fromCorpRemittanceDifferntAccount", "yes");
		context.getSessionMap().put("customerNumber", getCustomerNo());
		try {
			context.redirect("../beneficary/editcorparatebeneficiarybankaccountdetails.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}


	public void editDifferentService(PersonalRemmitanceBeneficaryDataTable datatabledetails) {
		log.info("Exit into edit method ");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.getSessionMap().put("cdatatabledetail", datatabledetails);
		context.getSessionMap().put("cfromCorpemittanceDifferntService", "yes");
		context.getSessionMap().put("customerNumber", getCustomerNo());
		try {
			context.redirect("../beneficary/editcorparatebeneficiarybankaccountdetails.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	private BigDecimal spotRateForDispay;
	private BigDecimal remittanceAmountForDisplay;




	public BigDecimal getSpotRateForDispay() {
		return spotRateForDispay;
	}

	public void setSpotRateForDispay(BigDecimal spotRateForDispay) {
		this.spotRateForDispay = spotRateForDispay;
	}

	public BigDecimal getRemittanceAmountForDisplay() {
		return remittanceAmountForDisplay;
	}

	public void setRemittanceAmountForDisplay(BigDecimal remittanceAmountForDisplay) {
		this.remittanceAmountForDisplay = remittanceAmountForDisplay;
	}
	
	
	private boolean additionalCheck = true;


	//private boolean booSpecialCusFCCalDataTable = false;


	public boolean isAdditionalCheck() {
		return additionalCheck;
	}

	public void setAdditionalCheck(boolean additionalCheck) {
		this.additionalCheck = additionalCheck;
	}
	
	public void checkingInstrnSiftBanksRequired(){

		HashMap<String, String> inputValues = new HashMap<String, String>();

		inputValues.put("APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId().toString());
		inputValues.put("ROUTING_COUNTRY_ID", getRoutingCountry().toString());
		inputValues.put("ROUTING_CURRENCY_ID", getForiegnCurrency().toString());
		inputValues.put("REMITTANCE_MODE_ID", getRemitMode().toString());
		inputValues.put("DELIVERY_MODE_ID", getDeliveryMode().toString());

		HashMap<String, String> checkData = new HashMap<String, String>();
		try {

			checkData = iPersonalRemittanceService.getSwitInstrustionFromServiceApplicability(inputValues);

			if(!checkData.isEmpty()){

				if(checkData.get("BENEFICIARY_SWIFT_BANK1").equalsIgnoreCase(Constants.Yes)){
					setBooRenderSwiftBank1(true);
				}else{
					setBooRenderSwiftBank1(false);
				}

				if(checkData.get("BENEFICIARY_SWIFT_BANK2").equalsIgnoreCase(Constants.Yes)){
					setBooRenderSwiftBank2(true);
				}else{
					setBooRenderSwiftBank2(false);
				}

				if(checkData.get("INSTRUCTION").equalsIgnoreCase(Constants.Yes)){
					setBooRenderInstructions(true);
				}else{
					setBooRenderInstructions(false);
				}

			}
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}

	}
	public void fetchingAllSwiftMaster() {
		lstSwiftMasterBank1.clear();
		lstSwiftMasterBank2.clear();
		setBeneSwiftBank1(null);
		setBeneSwiftBank2(null);
		setBeneSwiftBankAddr1(null);
		setBeneSwiftBankAddr2(null);
		// List<SwiftMaster> lstSwiftRecords =
		// iswiftMasterService.toFetchAllActiveRecords();
		List<PopulateData> lstSwiftRecords = iPersonalRemittanceService
				.fetchingViewSwiftMasterByCountryId(getDatabenificarycountry());

		if (lstSwiftRecords.size() != 0) {
			lstSwiftMasterBank1.addAll(lstSwiftRecords);
			lstSwiftMasterBank2.addAll(lstSwiftRecords);
		}
	}
	
	public void getSourceofIncomeDetails() {
		lstSourceofIncome.clear();
		List<SourceOfIncomeDescription> lstSource = foreignCurrencyPurchaseService.getSourceofIncome(sessionStateManage.getLanguageId());
		if (lstSource.size() != 0) {
			lstSourceofIncome.addAll(lstSource);
		}
	}

	private String procedureError;
	private List<PopulateData> lstSwiftMasterBank1 = new ArrayList<PopulateData>();
	private List<PopulateData> lstSwiftMasterBank2 = new ArrayList<PopulateData>();
	private String beneSwiftBank1;
	private String beneSwiftBank2;
	private String beneSwiftBankAddr1;
	private String beneSwiftBankAddr2;
	private BigDecimal beneStateId;
	private BigDecimal beneDistrictId;
	private BigDecimal beneCityId;
	private String PbeneFullName;
	private String PbeneFirstName;
	private String PbeneSecondName;
	private String PbeneThirdName;
	private String PbeneFourthName;
	private String PbeneFifthName;
	private boolean booRenderInstructions;
	private boolean booRenderSwiftBank1;
	private boolean booRenderSwiftBank2;




	public BigDecimal getBeneCountr() {
		return beneCountr;
	}

	public void setBeneCountr(BigDecimal beneCountr) {
		this.beneCountr = beneCountr;
	}

	public String getProcedureError() {
		return procedureError;
	}

	public void setProcedureError(String procedureError) {
		this.procedureError = procedureError;
	}

	public List<PopulateData> getLstSwiftMasterBank1() {
		return lstSwiftMasterBank1;
	}

	public void setLstSwiftMasterBank1(List<PopulateData> lstSwiftMasterBank1) {
		this.lstSwiftMasterBank1 = lstSwiftMasterBank1;
	}

	public List<PopulateData> getLstSwiftMasterBank2() {
		return lstSwiftMasterBank2;
	}

	public void setLstSwiftMasterBank2(List<PopulateData> lstSwiftMasterBank2) {
		this.lstSwiftMasterBank2 = lstSwiftMasterBank2;
	}

	public String getBeneSwiftBank1() {
		return beneSwiftBank1;
	}

	public void setBeneSwiftBank1(String beneSwiftBank1) {
		this.beneSwiftBank1 = beneSwiftBank1;
	}

	public String getBeneSwiftBank2() {
		return beneSwiftBank2;
	}

	public void setBeneSwiftBank2(String beneSwiftBank2) {
		this.beneSwiftBank2 = beneSwiftBank2;
	}

	public String getBeneSwiftBankAddr1() {
		return beneSwiftBankAddr1;
	}

	public void setBeneSwiftBankAddr1(String beneSwiftBankAddr1) {
		this.beneSwiftBankAddr1 = beneSwiftBankAddr1;
	}

	public String getBeneSwiftBankAddr2() {
		return beneSwiftBankAddr2;
	}

	public void setBeneSwiftBankAddr2(String beneSwiftBankAddr2) {
		this.beneSwiftBankAddr2 = beneSwiftBankAddr2;
	}

	public BigDecimal getBeneStateId() {
		return beneStateId;
	}

	public void setBeneStateId(BigDecimal beneStateId) {
		this.beneStateId = beneStateId;
	}

	public BigDecimal getBeneDistrictId() {
		return beneDistrictId;
	}

	public void setBeneDistrictId(BigDecimal beneDistrictId) {
		this.beneDistrictId = beneDistrictId;
	}

	public BigDecimal getBeneCityId() {
		return beneCityId;
	}

	public void setBeneCityId(BigDecimal beneCityId) {
		this.beneCityId = beneCityId;
	}

	public String getPbeneFullName() {
		return PbeneFullName;
	}

	public void setPbeneFullName(String pbeneFullName) {
		PbeneFullName = pbeneFullName;
	}

	public String getPbeneFirstName() {
		return PbeneFirstName;
	}

	public void setPbeneFirstName(String pbeneFirstName) {
		PbeneFirstName = pbeneFirstName;
	}

	public String getPbeneSecondName() {
		return PbeneSecondName;
	}

	public void setPbeneSecondName(String pbeneSecondName) {
		PbeneSecondName = pbeneSecondName;
	}

	public String getPbeneThirdName() {
		return PbeneThirdName;
	}

	public void setPbeneThirdName(String pbeneThirdName) {
		PbeneThirdName = pbeneThirdName;
	}

	public String getPbeneFourthName() {
		return PbeneFourthName;
	}

	public void setPbeneFourthName(String pbeneFourthName) {
		PbeneFourthName = pbeneFourthName;
	}

	public String getPbeneFifthName() {
		return PbeneFifthName;
	}

	public void setPbeneFifthName(String pbeneFifthName) {
		PbeneFifthName = pbeneFifthName;
	}

	public boolean isBooRenderInstructions() {
		return booRenderInstructions;
	}

	public void setBooRenderInstructions(boolean booRenderInstructions) {
		this.booRenderInstructions = booRenderInstructions;
	}

	public boolean isBooRenderSwiftBank1() {
		return booRenderSwiftBank1;
	}

	public void setBooRenderSwiftBank1(boolean booRenderSwiftBank1) {
		this.booRenderSwiftBank1 = booRenderSwiftBank1;
	}

	public boolean isBooRenderSwiftBank2() {
		return booRenderSwiftBank2;
	}

	public void setBooRenderSwiftBank2(boolean booRenderSwiftBank2) {
		this.booRenderSwiftBank2 = booRenderSwiftBank2;
	}

	
	private BigDecimal newRemittanceModeId;
	private String newRemittanceModeName;
	private BigDecimal newDeliveryModeId;
	private String newDeliveryModeName;
	private boolean booRenderDelivery;




	public BigDecimal getNewRemittanceModeId() {
		return newRemittanceModeId;
	}

	public void setNewRemittanceModeId(BigDecimal newRemittanceModeId) {
		this.newRemittanceModeId = newRemittanceModeId;
	}

	public String getNewRemittanceModeName() {
		return newRemittanceModeName;
	}

	public void setNewRemittanceModeName(String newRemittanceModeName) {
		this.newRemittanceModeName = newRemittanceModeName;
	}

	public BigDecimal getNewDeliveryModeId() {
		return newDeliveryModeId;
	}

	public void setNewDeliveryModeId(BigDecimal newDeliveryModeId) {
		this.newDeliveryModeId = newDeliveryModeId;
	}

	public String getNewDeliveryModeName() {
		return newDeliveryModeName;
	}

	public void setNewDeliveryModeName(String newDeliveryModeName) {
		this.newDeliveryModeName = newDeliveryModeName;
	}

	public boolean isBooRenderDelivery() {
		return booRenderDelivery;
	}

	public void setBooRenderDelivery(boolean booRenderDelivery) {
		this.booRenderDelivery = booRenderDelivery;
	}
	
	private BigDecimal specialDealRate;
	private BigDecimal spotExchangeRate;
	private boolean booRenderRemit;




	public BigDecimal getSpecialDealRate() {
		return specialDealRate;
	}

	public void setSpecialDealRate(BigDecimal specialDealRate) {
		this.specialDealRate = specialDealRate;
	}

	public BigDecimal getSpotExchangeRate() {
		return spotExchangeRate;
	}

	public void setSpotExchangeRate(BigDecimal spotExchangeRate) {
		this.spotExchangeRate = spotExchangeRate;
	}

	public boolean isBooRenderRemit() {
		return booRenderRemit;
	}

	public void setBooRenderRemit(boolean booRenderRemit) {
		this.booRenderRemit = booRenderRemit;
	}
	
	
	public HashMap<String, String> getAdditionalBeneDetails() {
		HashMap<String, String> additionalBeneDetails = null;

		try {
			additionalBeneDetails = iPersonalRemittanceService.toFetchDetilaFromAddtionalBenficiaryDetails(getMasterId(),
					getBeneficaryBankId(), getBeneficaryBankBranchId(),
					getBeneficiaryAccountSeqId(), getRoutingCountry(),
					getRoutingBank(), getRoutingBranch(),
					getDataserviceid(), // getBeneficaryBankId(), bankId
					sessionStateManage.getCountryId(), getForiegnCurrency(), // Foriegn CurrencyId
					getRemitMode(), getDeliveryMode());

		} catch (AMGException e) {
			setExceptionMessage("EX_GET_ADDL_BENE_DETAILS" + " : " +e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}

		System.out.println("additionalCheckMessage :" + additionalBeneDetails);

		return additionalBeneDetails;
	}
	
	private BigDecimal beneficiaryAccountSeqId;




	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}

	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}
	
	public String getAdditionalCheckProcedure() {
		String errorMessage = "";
		String additionalCheckMessage = null;
		try {
			additionalCheckMessage = iPersonalRemittanceService.getAdditionalCheckProcedure(sessionStateManage.getCountryId(),getCustomerNo(),
					new BigDecimal(sessionStateManage.getBranchId()),getMasterId(),getDatabenificarycountry(),getBeneficaryBankId(),
					getBeneficaryBankBranchId(),getDataAccountnum(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRoutingBranch(),
					getRemitMode(),getDeliveryMode(),getSourceOfIncome(),getExchangeRate(),
					new BigDecimal(sessionStateManage.getCurrencyId()), // localCommisionCurrencyId
					getCommission(),new BigDecimal(sessionStateManage.getCurrencyId()), // localChargeCurrencyId
					getOverseasamt(),new BigDecimal(sessionStateManage.getCurrencyId()), // localDelivCurrencyId
					getGrossAmountCalculated(),new BigDecimal(0),// serviceProvider e.g -->InsantCast etc,
					getDatabenificarycurrency(),getNetAmountSent(),new BigDecimal(sessionStateManage.getCurrencyId()), // localNetCurrecnyId,
					getNetAmountPayable(), getBeneSwiftBank1(), getBeneSwiftBank2(),// beneSwiftBank1,beneSwiftBank2,
					errorMessage);
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}

		System.out.println("additionalCheckMessage :" + additionalCheckMessage);

		return additionalCheckMessage;
	}
	

	private BigDecimal spotExchangeRatePk;




	public BigDecimal getSpotExchangeRatePk() {
		return spotExchangeRatePk;
	}

	public void setSpotExchangeRatePk(BigDecimal spotExchangeRatePk) {
		this.spotExchangeRatePk = spotExchangeRatePk;
	}
	
	Map<String,String> amlAuthuTYpe= new HashMap<String,String>();
	//private List<CustomerAlmTrasactionCheckProcedure> almcheckList = new ArrayList<CustomerAlmTrasactionCheckProcedure>();
	public void booAmlCheckProcess(){

		setIsHaveAmlCheck(false);
		setFromAMLCheck(true);
		//allPanelOff();
		almcheckList.clear();
		amlAuthuTYpe.clear();

		List<BenificiaryListView> isCoustomerExist = beneficaryCreation.getBeneficaryList(getCustomerNo());
		if (isCoustomerExist.size() > 0) {
			beneCountr = isCoustomerExist.get(0).getBenificaryCountry();
			BigDecimal masterSeqId = isCoustomerExist.get(0).getBeneficaryMasterSeqId();
			try {
				HashMap<String, String> list = iPersonalRemittanceService.isAmlTranxAmountCheckForRemittance(sessionStateManage.getCountryId(),beneCountr,getCustomerNo(),masterSeqId,getNetAmountPayable());
				if (list != null && !list.isEmpty()) {
					if (list.get("MESSAGE1").equalsIgnoreCase("") && list.get("MESSAGE2").equalsIgnoreCase("") && list.get("MESSAGE3").equalsIgnoreCase("")) {
						setIsHaveAmlCheck(false);
					} else {
						if (list.get("MESSAGE3") != null && list.get("MESSAGE3")!="" ) {
							setMessageThree(list.get("MESSAGE3"));
							setAmlMessageThree(list.get("MESSAGE3"));
						} else {
							if (list.get("MESSAGE1") != null && list.get("MESSAGE1")!="") {
								setAmlMessageOne(list.get("MESSAGE1"));
								setMessageOne(list.get("MESSAGE1"));
							}
							if (list.get("MESSAGE2") != null && list.get("MESSAGE2")!="") {
								setAmlMessageTwo(list.get("MESSAGE2"));
								setMessageTwo(list.get("MESSAGE2"));
							}
						}
						setIsHaveAmlCheck(true);
						almcheckList.clear();
						if (list.get("MESSAGE3") != null && list.get("MESSAGE3")!="" ) {
							CustomerAlmTrasactionCheckProcedure almcheckSinglerow = new CustomerAlmTrasactionCheckProcedure();
							almcheckSinglerow.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(),getDatabenificarycountry()));

							setRangeFromOne(list.get("RANGE1FROM") == null ? "": list.get("RANGE1FROM"));
							setRangeToOne(list.get("RANGE1TO") == null ? "": list.get("RANGE1TO"));
							almcheckSinglerow.setSlab1(Integer.parseInt(list.get("RANGE1COUNT") == null ? "0": list.get("RANGE1COUNT")));
							setRangeFromTwo(list.get("RANGE2FROM") == null ? "": list.get("RANGE2FROM"));
							setRangeToTwo(list.get("RANGE2TO") == null ? "": list.get("RANGE2TO"));
							almcheckSinglerow.setSlab2(Integer.parseInt(list.get("RANGE2COUNT") == null ? "0": list.get("RANGE2COUNT")));
							setRangeFromThree(list.get("RANGE3FROM") == null ? "": list.get("RANGE3FROM"));
							setRangeToThree(list.get("RANGE3TO") == null ? "": list.get("RANGE3TO"));
							almcheckSinglerow.setSlab3(Integer.parseInt(list.get("RANGE3COUNT") == null ? "0": list.get("RANGE3COUNT")));
							setRangeFromFour(list.get("RANGE4FROM") == null ? "": list.get("RANGE4FROM"));
							setRangeToFour(list.get("RANGE4TO") == null ? "": list.get("RANGE4TO"));
							almcheckSinglerow.setSlab4(Integer.parseInt(list.get("RANGE4COUNT") == null ? "0": list.get("RANGE4COUNT")));
							setRangeFromFive(list.get("RANGE5FROM") == null ? "": list.get("RANGE5FROM"));
							setRangeToFive(list.get("RANGE5TO") == null ? "": list.get("RANGE5TO"));
							almcheckSinglerow.setSlab5(Integer.parseInt(list.get("RANGE5COUNT") == null ? "0": list.get("RANGE5COUNT")));
							setRangeFromSix(list.get("RANGE6FROM") == null ? "": list.get("RANGE6FROM"));
							setRangeToSix(list.get("RANGE6TO") == null ? "": list.get("RANGE6TO"));
							almcheckSinglerow.setSlab6(Integer.parseInt(list.get("RANGE6COUNT") == null ? "0": list.get("RANGE6COUNT")));

							almcheckList.add(almcheckSinglerow);


							if (list.get("RANGE5FROM") != null && list.get("RANGE5TO") != null && list.get("RANGE5COUNT") != null) {
								setRenderRangeFive(true);
							} else {
								setRenderRangeFive(false);
							}

							if (list.get("RANGE6FROM") != null && list.get("RANGE6TO") != null && list.get("RANGE6COUNT") != null) {
								setRenderRangeSix(true);
							} else {
								setRenderRangeSix(false);
							}
							setAlmcheckList(almcheckList);
						}


						if(list.get("AUTHTYPE1") != null  && list.get("AUTHTYPE1") != "")
						{
							amlAuthuTYpe.put((list.get("AUTHTYPE1").toString()),getAmlMessageOne());
						}
						if(list.get("AUTHTYPE2") != null && list.get("AUTHTYPE2") != "")
						{
							amlAuthuTYpe.put((list.get("AUTHTYPE2").toString()),getAmlMessageTwo());
						}

						if(list.get("AUTHTYPE3") != null && list.get("AUTHTYPE3") != "")
						{
							amlAuthuTYpe.put((list.get("AUTHTYPE3").toString()),getAmlMessageThree());
						}


					}
				} else {
					setIsHaveAmlCheck(false);
				}
			} catch (AMGException e) {
				setIsHaveAmlCheck(false);
				log.error("Exception In AML Check Procedure"+ e.getMessage());
			}

		}
		setAmlboomsg(true);
		setAmlboo(true);

		slb1total1 = 0.0;
		slb1total2 = 0.0;
		slb1total3 = 0.0;
		slb1total4 = 0.0;
		slb1total5 = 0.0;
		slb1total6 = 0.0;


		if(getAmlMessageOne() != null ||  getAmlMessageTwo() != null || getAmlMessageThree() != null)
		{
			List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
			//setEmpllist(localEmpllist);
		}

		if (getMessageOne() != null && getMessageTwo() != null) {
			setBooMessageOne(true);
			setBooMessageTwo(true);
		} else if (getMessageOne() != null) {
			setBooMessageOne(true);
		} else if (getMessageTwo() != null) {
			setBooMessageTwo(true);
		} else if (getMessageThree() != null) {
			setBooMessageThree(true);
		} else {
			setAmlboomsg(false);
			setAmlboo(false);
			setFromAMLCheck(false);
			setBooRenderCustomerSignaturePanel(true);
			customerSignatureSpecimen();
		}

	}
	

	
	private BigDecimal beneficiaryRelationShipSeqId;
	private boolean isHaveAmlCheck;
	
	private String rangeFromFive;
	private String rangeToFive;
	private String rangeFromSix;
	private String rangeToSix;
	private boolean renderRangeFive;
	private boolean renderRangeSix;
	private double slb1total5 = 0.0;
	private double slb1total6 = 0.0;

	private String amlAuntenticationMessageOne;
	private String amlAuntenticationAuthorizedBy;
	private String amlAuntenticationAuthorizedPassword;
	private String amlAuntenticationRemarks;
	private String amlAuntenticationType;
	private boolean amlRecheckAuthentication = false;




	public Map<String, String> getAmlAuthuTYpe() {
		return amlAuthuTYpe;
	}

	public void setAmlAuthuTYpe(Map<String, String> amlAuthuTYpe) {
		this.amlAuthuTYpe = amlAuthuTYpe;
	}

	public BigDecimal getBeneficiaryRelationShipSeqId() {
		return beneficiaryRelationShipSeqId;
	}

	public void setBeneficiaryRelationShipSeqId(BigDecimal beneficiaryRelationShipSeqId) {
		this.beneficiaryRelationShipSeqId = beneficiaryRelationShipSeqId;
	}

	public boolean isHaveAmlCheck() {
		return isHaveAmlCheck;
	}

	public void setHaveAmlCheck(boolean isHaveAmlCheck) {
		this.isHaveAmlCheck = isHaveAmlCheck;
	}

	public String getRangeFromFive() {
		return rangeFromFive;
	}

	public void setRangeFromFive(String rangeFromFive) {
		this.rangeFromFive = rangeFromFive;
	}

	public String getRangeToFive() {
		return rangeToFive;
	}

	public void setRangeToFive(String rangeToFive) {
		this.rangeToFive = rangeToFive;
	}

	public String getRangeFromSix() {
		return rangeFromSix;
	}

	public void setRangeFromSix(String rangeFromSix) {
		this.rangeFromSix = rangeFromSix;
	}

	public String getRangeToSix() {
		return rangeToSix;
	}

	public void setRangeToSix(String rangeToSix) {
		this.rangeToSix = rangeToSix;
	}

	public boolean isRenderRangeFive() {
		return renderRangeFive;
	}

	public void setRenderRangeFive(boolean renderRangeFive) {
		this.renderRangeFive = renderRangeFive;
	}

	public boolean isRenderRangeSix() {
		return renderRangeSix;
	}

	public void setRenderRangeSix(boolean renderRangeSix) {
		this.renderRangeSix = renderRangeSix;
	}

	public double getSlb1total5() {
		return slb1total5;
	}

	public void setSlb1total5(double slb1total5) {
		this.slb1total5 = slb1total5;
	}

	public double getSlb1total6() {
		return slb1total6;
	}

	public void setSlb1total6(double slb1total6) {
		this.slb1total6 = slb1total6;
	}

	public String getAmlAuntenticationMessageOne() {
		return amlAuntenticationMessageOne;
	}

	public void setAmlAuntenticationMessageOne(String amlAuntenticationMessageOne) {
		this.amlAuntenticationMessageOne = amlAuntenticationMessageOne;
	}

	public String getAmlAuntenticationAuthorizedBy() {
		return amlAuntenticationAuthorizedBy;
	}

	public void setAmlAuntenticationAuthorizedBy(String amlAuntenticationAuthorizedBy) {
		this.amlAuntenticationAuthorizedBy = amlAuntenticationAuthorizedBy;
	}

	public String getAmlAuntenticationAuthorizedPassword() {
		return amlAuntenticationAuthorizedPassword;
	}

	public void setAmlAuntenticationAuthorizedPassword(String amlAuntenticationAuthorizedPassword) {
		this.amlAuntenticationAuthorizedPassword = amlAuntenticationAuthorizedPassword;
	}

	public String getAmlAuntenticationRemarks() {
		return amlAuntenticationRemarks;
	}

	public void setAmlAuntenticationRemarks(String amlAuntenticationRemarks) {
		this.amlAuntenticationRemarks = amlAuntenticationRemarks;
	}

	public String getAmlAuntenticationType() {
		return amlAuntenticationType;
	}

	public void setAmlAuntenticationType(String amlAuntenticationType) {
		this.amlAuntenticationType = amlAuntenticationType;
	}

	public boolean isAmlRecheckAuthentication() {
		return amlRecheckAuthentication;
	}

	public void setAmlRecheckAuthentication(boolean amlRecheckAuthentication) {
		this.amlRecheckAuthentication = amlRecheckAuthentication;
	}
	
	
	
	public boolean getIsHaveAmlCheck() {
		return isHaveAmlCheck;
	}
	public void setIsHaveAmlCheck(boolean isHaveAmlCheck) {
		this.isHaveAmlCheck = isHaveAmlCheck;
	}

	public void backFromSignaturePanel() {

		if (getIsHaveAmlCheck()) {
			//allPanelOff();
			setAmlboo(true);
			setAmlboomsg(true);
			setBooMessageThree(true);
		} else {
			booAmlCheckBack();
		}

	}
	private BigDecimal totalbalanceAmount = new BigDecimal(0);
	private BigDecimal totalrefundAmount = new BigDecimal(0);

	private String colBankCode;

	private boolean booShowAuthenticationPanel = false;




	public BigDecimal getTotalbalanceAmount() {
		return totalbalanceAmount;
	}

	public void setTotalbalanceAmount(BigDecimal totalbalanceAmount) {
		this.totalbalanceAmount = totalbalanceAmount;
	}

	public BigDecimal getTotalrefundAmount() {
		return totalrefundAmount;
	}

	public void setTotalrefundAmount(BigDecimal totalrefundAmount) {
		this.totalrefundAmount = totalrefundAmount;
	}

	public String getColBankCode() {
		return colBankCode;
	}

	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}

	public boolean isBooShowAuthenticationPanel() {
		return booShowAuthenticationPanel;
	}

	public void setBooShowAuthenticationPanel(boolean booShowAuthenticationPanel) {
		this.booShowAuthenticationPanel = booShowAuthenticationPanel;
	}
	// fifth panel rendering
		public void allPanelOff() {
			//first panel
			setMainPanelRender(true);
			setBooRenderOldSmartCardPanel(false);
			setBooRenderBenificaryFirstPanel(false);
			//second panel
			setBooRenderBenificarySearchPanel(false);
			//third panel
			setBooRenderRemittanceServicePanel(false);
			setBooSpecialCusFCCalDataTable(false);
			//fourth panel
			setBooRenderLayaltyServicePanel(false);
			//fifth panel
			setAmlboo(false);
			setAmlboomsg(false);
			//sixth panel
			setBooRenderCustomerSignaturePanel(false);
			setBooRenderSignatureMsg(false);
			//seventh panel
			setBoorenderlastpanel(false);
			//eight panel
			setBooRenderCollection(false);
			setBooRenderColDebitCard(false);
			setBooRendercollectiondatatable(false);
			//ninth panel
			setBooRenderPaymentDetails(false);
			setBooRendercashdenomination(false);
			setBoorefundPaymentDetails(false);		
			setPaymentDeatailsPanel(false);
			//final report panel
			setReceiptReportPanel(false);
		}
		//private boolean boorenderlastpanel;
		private String signatureMandatoryMsg;
		private boolean booRenderSignatureMsg = false;
		//private boolean booShowAuthenticationPanel = false;




		public String getSignatureMandatoryMsg() {
			return signatureMandatoryMsg;
		}

		public void setSignatureMandatoryMsg(String signatureMandatoryMsg) {
			this.signatureMandatoryMsg = signatureMandatoryMsg;
		}

		public boolean isBooRenderSignatureMsg() {
			return booRenderSignatureMsg;
		}

		public void setBooRenderSignatureMsg(boolean booRenderSignatureMsg) {
			this.booRenderSignatureMsg = booRenderSignatureMsg;
		}

	
		private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();
		//private CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();
		private List<ViewBankDetails> bankMasterList = new CopyOnWriteArrayList<ViewBankDetails>();
		private List<ViewBankDetails> chequebankMasterList = new ArrayList<ViewBankDetails>();
		private List<ViewBankDetails> localbankList = new ArrayList<ViewBankDetails>();
		private List<PaymentModeDesc> lstFetchAllPayMode = new ArrayList<PaymentModeDesc>();
		private List<CustomerBank> lstDebitCard = new ArrayList<CustomerBank>();
		private List<DebitAutendicationView> empllist = new ArrayList<DebitAutendicationView>();
		private List<DebitAutendicationView> emplAutenticationlist = new ArrayList<DebitAutendicationView>();
		private List<ViewBankDetails> localbankListForBankCode = new ArrayList<ViewBankDetails>();
		private List<FcSaleReport> fcSaleReportDTOList = null;



		

		public List<ViewBankDetails> getBankMasterList() {
			return bankMasterList;
		}

		public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
			this.bankMasterList = bankMasterList;
		}

		public List<ViewBankDetails> getChequebankMasterList() {
			return chequebankMasterList;
		}

		public void setChequebankMasterList(List<ViewBankDetails> chequebankMasterList) {
			this.chequebankMasterList = chequebankMasterList;
		}

		public List<ViewBankDetails> getLocalbankList() {
			return localbankList;
		}

		public void setLocalbankList(List<ViewBankDetails> localbankList) {
			this.localbankList = localbankList;
		}

		public List<CustomerBank> getLstDebitCard() {
			return lstDebitCard;
		}

		public void setLstDebitCard(List<CustomerBank> lstDebitCard) {
			this.lstDebitCard = lstDebitCard;
		}

		public List<DebitAutendicationView> getEmplAutenticationlist() {
			return emplAutenticationlist;
		}

		public void setEmplAutenticationlist(List<DebitAutendicationView> emplAutenticationlist) {
			this.emplAutenticationlist = emplAutenticationlist;
		}

		public List<FcSaleReport> getFcSaleReportDTOList() {
			return fcSaleReportDTOList;
		}

		public void setFcSaleReportDTOList(List<FcSaleReport> fcSaleReportDTOList) {
			this.fcSaleReportDTOList = fcSaleReportDTOList;
		}

		public List<DebitAutendicationView> getEmpllist() {
			return empllist;
		}

		public void setEmpllist(List<DebitAutendicationView> empllist) {
			this.empllist = empllist;
		}

		
				
		private boolean booColApprovalNo=false;
		private String knetIposReceipt;
		private String knetReceiptDate;
		private String knetReceiptTime;

		// WinEPTS_Wrapper API = new WinEPTS_Wrapper();
		Receipt receipt =null;
		String knetTranId=null;
		
		

		public Receipt getReceipt() {
			return receipt;
		}

		public void setReceipt(Receipt receipt) {
			this.receipt = receipt;
		}

		public String getKnetTranId() {
			return knetTranId;
		}

		public void setKnetTranId(String knetTranId) {
			this.knetTranId = knetTranId;
		}

		public String getKnetIposReceipt() {
			return knetIposReceipt;
		}

		public void setKnetIposReceipt(String knetIposReceipt) {
			this.knetIposReceipt = knetIposReceipt;
		}

		public String getKnetReceiptDate() {
			return knetReceiptDate;
		}

		public void setKnetReceiptDate(String knetReceiptDate) {
			this.knetReceiptDate = knetReceiptDate;
		}

		public String getKnetReceiptTime() {
			return knetReceiptTime;
		}

		public void setKnetReceiptTime(String knetReceiptTime) {
			this.knetReceiptTime = knetReceiptTime;
		}

		public boolean isBooColApprovalNo() {
			return booColApprovalNo;
		}

		public void setBooColApprovalNo(boolean booColApprovalNo) {
			this.booColApprovalNo = booColApprovalNo;
		}
		
		//private List<CustomerAlmTrasactionCheckProcedure> almcheckList = new ArrayList<CustomerAlmTrasactionCheckProcedure>();
		private List<BigDecimal> lstBeneAuthenticationPanel = new ArrayList<BigDecimal>();
		private List<AuthorizedLog> lstBeneAuthentication = new ArrayList<AuthorizedLog>();

		
		
		public List<BigDecimal> getLstBeneAuthenticationPanel() {
			return lstBeneAuthenticationPanel;
		}

		public void setLstBeneAuthenticationPanel(List<BigDecimal> lstBeneAuthenticationPanel) {
			this.lstBeneAuthenticationPanel = lstBeneAuthenticationPanel;
		}

		public List<AuthorizedLog> getLstBeneAuthentication() {
			return lstBeneAuthentication;
		}

		public void setLstBeneAuthentication(List<AuthorizedLog> lstBeneAuthentication) {
			this.lstBeneAuthentication = lstBeneAuthentication;
		}

		public void toFetchPaymentDetails(){
			  List<PaymentModeDesc> list=ipaymentService.fetchPaymodeDesc(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),Constants.Yes);
			  if(list.size() !=0){
				    lstFetchAllPayMode.addAll(list);   
			  }
			  
		}
		
		// checking security authentication
		public void nextAmlCheckAunticationPanel() {

			  String userName=null;
			  String password=null;
			  if(getAmlAuntenticationAuthorizedBy() != null){
			      userName= getAmlAuntenticationAuthorizedBy().trim();
			    }
			  if(getAmlAuntenticationAuthorizedPassword() != null){
			      password= getAmlAuntenticationAuthorizedPassword();
			    }
			  
			BigDecimal authorizedLogId = null; 

			if(lstBeneAuthentication != null && !lstBeneAuthentication.isEmpty()){
				lstBeneAuthentication.clear();
			}

			List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();

			lstEmpLogin = iPersonalRemittanceService.getdebitAutendicationListByUserId(userName,password);

			if (lstEmpLogin.size() == 1) {

				for (ShoppingCartDataTableBean lstRecord : lstselectedrecord) {
					for (BigDecimal benelst : lstBeneAuthenticationPanel) {
						if(lstRecord.getSelectedrecord() && lstRecord.getBeneficiaryId().compareTo(benelst)==0){

							AuthorizedLog authorizedLog = new AuthorizedLog();

							authorizedLog.setAuthorizedLogId(authorizedLogId);

							authorizedLog.setDocumentFinancialYear(getFinaceYear());
							authorizedLog.setDocumentDate(new Date());
							authorizedLog.setDocumentId(lstRecord.getDocumentId());
							authorizedLog.setDocumentNo(lstRecord.getDocumentNo());
							authorizedLog.setAuthorizedBy(getAmlAuntenticationAuthorizedBy());
							authorizedLog.setAmlRemarks(getAmlAuntenticationRemarks());
							authorizedLog.setAuthorizedPassword(getAmlAuntenticationAuthorizedPassword());
							authorizedLog.setAuthorizedLogId(sessionStateManage.getEmployeeId());
							authorizedLog.setAuthorizedType(getAmlAuntenticationType());
							authorizedLog.setCreatedBy(sessionStateManage.getUserName());
							authorizedLog.setCreatedDate(new Date());

							try {
								authorizedLog.setAccountYearMonth(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
							} catch (ParseException e) {
								e.printStackTrace();
							}

							Customer customer = new Customer();
							customer.setCustomerId(getCustomerNo());
							authorizedLog.setCustomerId(customer);

							CountryMaster applicationCountry = new CountryMaster();
							applicationCountry.setCountryId(sessionStateManage.getCountryId());
							authorizedLog.setAppCountryId(applicationCountry);

							CompanyMaster company = new CompanyMaster();
							company.setCompanyId(sessionStateManage.getCompanyId());
							authorizedLog.setCompanymaster(company);

							lstBeneAuthentication.add(authorizedLog);
						}
					}
				}

				// collection panel
				collectionPaymentModePanel();

			} else {
				RequestContext.getCurrentInstance().execute("inValidLogin.show()");
				return;
			}

		}
		
		// process to collection and payment mode
		public void collectionPaymentModePanel(){
			// if false proceed to next
			allPanelOff();
			setBooRenderCollection(true);
			if(coldatatablevalues.size() != 0){
				setBooRendercollectiondatatable(true);
			} else {
				BigDecimal loyaltyPointencashed = new BigDecimal(0);
				ShoppingCartDataTableBean shoppingCartDataTableBean = null;
				for (int i = 0; i < lstselectedrecord.size(); i++) {
					shoppingCartDataTableBean = lstselectedrecord.get(i);
					if (shoppingCartDataTableBean.getLoyaltsPointIndicator() != null && shoppingCartDataTableBean.getLoyaltsPointIndicator().equals(Constants.Yes)) {
						if (shoppingCartDataTableBean.getLoyaltsPointencahsed() != null) {
							loyaltyPointencashed = loyaltyPointencashed.add(shoppingCartDataTableBean.getLoyaltsPointencahsed());
						}
					}
				}
				if (loyaltyPointencashed.compareTo(new BigDecimal(0)) > 0) {
					PersonalRemittanceCollectionDataTable voucher = new PersonalRemittanceCollectionDataTable();
					voucher.setColAmountDT(loyaltyPointencashed);
					voucher.setColpaymentmodeCode(Constants.V);
					PaymentModeDesc voucherDesc = iPersonalRemittanceService.getvoucherModeId(Constants.V, sessionStateManage.getLanguageId());
					if(voucherDesc!=null) {
						voucher.setColpaymentmodetypeDT(voucherDesc.getLocalPaymentName());
						voucher.setColpaymentmodeIDtypeDT(voucherDesc.getPaymentMode().getPaymentModeId());

					}
					else
					{
						RequestContext.getCurrentInstance().execute("paymentModemissing.show();");
						setErrmsg("Voucher entery is missing in Payment Mode");
						return;
					}
					voucher.setBooDisbale(true);
					coldatatablevalues.add(voucher);
					setBooRendercollectiondatatable(true);
				}
			}
			getLocalBankListforIndicator();
		}
		// checking AML check of  beneficiary - Multiple Customer For The Same Beneficiary Exceeds Limit
		public Boolean checkingSameBeneficiaryExceedsLimit(){
			String authicationOverLimitMsg = null;
			BigDecimal authicationOverLimit = null;
			String authicationOverLimitType = null;
			boolean booAmlCheckSameBene = false;
			if(lstBeneAuthentication != null && !lstBeneAuthentication.isEmpty()){
				lstBeneAuthentication.clear();
			}
			if(lstBeneAuthenticationPanel != null && !lstBeneAuthenticationPanel.isEmpty()){
				lstBeneAuthenticationPanel.clear();
			}


			List<AuthicationLimitCheckView> authicationMsgCount = iPersonalRemittanceService.parameterLimitCheckForSameBene();
			System.out.println(authicationMsgCount.size());
			List<BigDecimal> lstBeneFinalRecordsToStore = new ArrayList<BigDecimal>();
			List<BigDecimal> lstBeneId = new CopyOnWriteArrayList<BigDecimal>();
			HashMap<BigDecimal,BigDecimal> lstBeneShoppingCartCount = new HashMap<BigDecimal, BigDecimal>();
			HashMap<BigDecimal,BigDecimal> lstBeneDBCount = new HashMap<BigDecimal, BigDecimal>();
			// and then check with same bene available in same shopping cart
			for (ShoppingCartDataTableBean lstBeneRec : lstselectedrecord) {
				// only remittance to check beneficiary id aml check
				if(lstBeneRec.getApplicationType().equalsIgnoreCase(Constants.Remittance)){
					// EX_REMIT_BENE count of beneficiary should calculated.
					// maintain all bene without duplicate and get count for all bene
					if(lstBeneId != null){
						if(lstBeneId.size() != 0){
							for (BigDecimal bigDecimal : lstBeneId) {
								if(bigDecimal.compareTo(lstBeneRec.getBeneficiaryId())!=0){
									lstBeneId.add(lstBeneRec.getBeneficiaryId());
								}
							}

						}else{
							if(lstBeneRec.getBeneficiaryId()!=null){
								lstBeneId.add(lstBeneRec.getBeneficiaryId());
							}
						}
					}
					log.info("bene id =" + lstBeneId.toString());
				}else{
					// fc sale no condition to check
				}
			}

			if(lstBeneId != null && lstBeneId.size() != 0){
				lstBeneDBCount = iPersonalRemittanceService.beneficiaryCount(lstBeneId);
				log.info("bene count and id =" + lstBeneDBCount.toString());
				if(lstBeneId != null  && lstBeneId.size() != 0){
					for (BigDecimal bigDecimal : lstBeneId) {
						BigDecimal shoppingCartCount = BigDecimal.ZERO;
						for (ShoppingCartDataTableBean lstBeneRecChecking : lstselectedrecord) {
							if(bigDecimal.compareTo(lstBeneRecChecking.getBeneficiaryId())==0){
								shoppingCartCount = shoppingCartCount.add(BigDecimal.ONE);
							}
						}
						lstBeneShoppingCartCount.put(bigDecimal, shoppingCartCount);
					}
				}
				log.info("Shopping cart bene and count =" + lstBeneShoppingCartCount.toString());
			}

			if(lstBeneShoppingCartCount != null && lstBeneShoppingCartCount.size() != 0 && lstBeneDBCount != null && lstBeneDBCount.size() != 0){
				// view of authentication msg to populate
				if(authicationMsgCount.size() != 0){
					AuthicationLimitCheckView authMsgOverLimit = authicationMsgCount.get(0);
					authicationOverLimitMsg = authMsgOverLimit.getAuthMessage();
					setAmlAuntenticationMessageOne(authicationOverLimitMsg);
					authicationOverLimit = authMsgOverLimit.getAuthLimit();
					authicationOverLimitType = authMsgOverLimit.getAuthorizationType();
					setAmlAuntenticationType(authicationOverLimitType);
				}
				// check parameter master
				if(lstBeneId != null  && lstBeneId.size() != 0 && authicationOverLimitMsg != null && authicationOverLimit != null){
					for (BigDecimal bigDecimal : lstBeneId) {
						BigDecimal finalcount =  lstBeneShoppingCartCount.get(bigDecimal).add(lstBeneDBCount.get(bigDecimal));
						if(finalcount.compareTo(authicationOverLimit)>0){
							lstBeneFinalRecordsToStore.add(bigDecimal);
						}
					}
					lstBeneAuthenticationPanel.addAll(lstBeneFinalRecordsToStore);
					if(lstBeneAuthenticationPanel.size()!=0){
						booAmlCheckSameBene = true;
					}else{
						booAmlCheckSameBene = false;
					}
				}
			}
			// checking done or not
			setAmlRecheckAuthentication(true);

			return booAmlCheckSameBene;
		}
		
		// Sixth Panel Variables
		
		private BigDecimal documentId;
		private BigDecimal documentcode;
		private String documentNo;
		private String documentdesc;
		private BigDecimal finaceYear;
		private BigDecimal finaceYearId;




		public List<UserFinancialYear> getFinancialYearList() {
			return financialYearList;
		}

		public void setFinancialYearList(List<UserFinancialYear> financialYearList) {
			this.financialYearList = financialYearList;
		}

		public BigDecimal getDocumentId() {
			return documentId;
		}

		public void setDocumentId(BigDecimal documentId) {
			this.documentId = documentId;
		}

		public BigDecimal getDocumentcode() {
			return documentcode;
		}

		public void setDocumentcode(BigDecimal documentcode) {
			this.documentcode = documentcode;
		}

		public String getDocumentNo() {
			return documentNo;
		}

		public void setDocumentNo(String documentNo) {
			this.documentNo = documentNo;
		}

		public String getDocumentdesc() {
			return documentdesc;
		}

		public void setDocumentdesc(String documentdesc) {
			this.documentdesc = documentdesc;
		}

		public void setFinaceYearId(BigDecimal finaceYearId) {
			this.finaceYearId = finaceYearId;
		}

		public void setFinaceYear(BigDecimal finaceYear) {
			this.finaceYear = finaceYear;
		}


		private String denominationchecking;




		public String getDenominationchecking() {
			return denominationchecking;
		}

		public void setDenominationchecking(String denominationchecking) {
			this.denominationchecking = denominationchecking;
		}
		
		private Boolean renderEmailForReport=false;
		private String emailToSendReport;
		private Boolean checkEmailForReport=false;
		private Boolean savetimeReportEmailCheck=false;




		public Boolean getRenderEmailForReport() {
			return renderEmailForReport;
		}

		public void setRenderEmailForReport(Boolean renderEmailForReport) {
			this.renderEmailForReport = renderEmailForReport;
		}

		public String getEmailToSendReport() {
			return emailToSendReport;
		}

		public void setEmailToSendReport(String emailToSendReport) {
			this.emailToSendReport = emailToSendReport;
		}

		public Boolean getCheckEmailForReport() {
			return checkEmailForReport;
		}

		public void setCheckEmailForReport(Boolean checkEmailForReport) {
			this.checkEmailForReport = checkEmailForReport;
		}

		public Boolean getSavetimeReportEmailCheck() {
			return savetimeReportEmailCheck;
		}

		public void setSavetimeReportEmailCheck(Boolean savetimeReportEmailCheck) {
			this.savetimeReportEmailCheck = savetimeReportEmailCheck;
		}


		private BigDecimal chequeValue;
		private BigDecimal collectedAmount;

		private String nextOrSaveButton;

		
		
		public String getNextOrSaveButton() {
			return nextOrSaveButton;
		}

		public void setNextOrSaveButton(String nextOrSaveButton) {
			this.nextOrSaveButton = nextOrSaveButton;
		}

		public BigDecimal getCollectedAmount() {
			return collectedAmount;
		}

		public void setCollectedAmount(BigDecimal collectedAmount) {
			this.collectedAmount = collectedAmount;
		}

		public BigDecimal getChequeValue() {
			return chequeValue;
		}

		public void setChequeValue(BigDecimal chequeValue) {
			this.chequeValue = chequeValue;
		}
		
		
		public List<TempCollectDetail> saveCollectionDetail(TempCollection collect) {
			int i = 1;
			BigDecimal totalAmt=BigDecimal.ZERO;
			setBooDeclarationReportDisplay(false);
			List<TempCollectDetail> tempCollectionDetails = new ArrayList<TempCollectDetail>();
			for (PersonalRemittanceCollectionDataTable lstofPayment : coldatatablevalues) {
				TempCollectDetail collectDetails = new TempCollectDetail();
				TempCollection collection = new TempCollection();
				collection.setCollectionId(collect.getCollectionId());
				collectDetails.setCashCollectionId(collection);
				Customer customer = new Customer();
				customer.setCustomerId(collect.getFsCustomer().getCustomerId());
				collectDetails.setFsCustomer(customer);
				CountryMaster appcountrymaster = new CountryMaster();
				appcountrymaster.setCountryId(sessionStateManage.getCountryId());
				collectDetails.setFsCountryMaster(appcountrymaster);
				collectDetails.setDocumentCode(collect.getDocumentCode());
				CountryBranch countryBranch = new CountryBranch();
				countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
				collectDetails.setCountryBranch(countryBranch);
				collectDetails.setDocumentDate(new Date());
				collectDetails.setDocumentLineNo(new BigDecimal(i++));
				CurrencyMaster forcurrencymaster = new CurrencyMaster();
				forcurrencymaster.setCurrencyId(collect.getExCurrencyMaster().getCurrencyId());
				collectDetails.setExCurrencyMaster(forcurrencymaster);
				collectDetails.setCollectionMode(lstofPayment.getColpaymentmodeCode());
				if (lstofPayment.getColBankCodeDT() != null) {
					collectDetails.setChequeBankRef(lstofPayment.getColBankCodeDT());
				}
				collectDetails.setCollAmt(lstofPayment.getColAmountDT());
				totalAmt=totalAmt.add(lstofPayment.getColAmountDT());
				if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
					collectDetails.setDebitCard(lstofPayment.getColCardNumberDT().toPlainString());
					collectDetails.setDbCardName(lstofPayment.getColNameofCardDT());
					collectDetails.setApprovalNo(lstofPayment.getColApprovalNo());
					//Added by Rabil on 01/02/2016
					collectDetails.setKnetReceipt(lstofPayment.getKnetReceiptDT());
					if(lstofPayment.getKneRceiptTimeDT()!=null){
						collectDetails.setKnetReceiptDateTime(lstofPayment.getKneRceiptTimeDT());
					}else{
						Date dNow = new Date( );
						SimpleDateFormat ft =  new SimpleDateFormat ("dd/MM/YYYY hh:mm");
						collectDetails.setKnetReceiptDateTime(ft.format(dNow));
					}
					collectDetails.setTransId(lstofPayment.getKnetTransIdDT());

					if (lstofPayment.getColAuthorizedByDT() != null) {
						collectDetails.setAuthby(lstofPayment.getColAuthorizedByDT());
						collectDetails.setAuthdate(new Date());
					}
				}else if(lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
					collectDetails.setChequeRef(lstofPayment.getColchequeRefNo());
					collectDetails.setChequeDate(lstofPayment.getColchequeDate());
					collectDetails.setApprovalNo(lstofPayment.getColApprovalNo());
				}

				if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode))
				{
					List<AuthicationLimitCheckView> declarationReportCashAmt = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.DeclarationReportforCash);
					
					BigDecimal declarationCashAmt = BigDecimal.ZERO;
					if(declarationReportCashAmt.size() != 0){
						AuthicationLimitCheckView authRemitAmtLimit = declarationReportCashAmt.get(0);
						declarationCashAmt = authRemitAmtLimit.getAuthLimit();
					}
					
					if(lstofPayment.getColAmountDT().compareTo(declarationCashAmt)>=1)
					{
						setBooDeclarationReportDisplay(true);
					}
				}
				try {
					collectDetails.setAcyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				collectDetails.setCreatedBy(sessionStateManage.getUserName());
				collectDetails.setCreatedDate(new Date());
				collectDetails.setPaymentModeId(lstofPayment.getColpaymentmodeIDtypeDT());
				tempCollectionDetails.add(collectDetails);
			}
			
			List<AuthicationLimitCheckView> declarationReportTotal = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.DeclarationReportTotalAmt);
			BigDecimal declarationTotalAmt = BigDecimal.ZERO;
			if(declarationReportTotal.size() != 0){
				AuthicationLimitCheckView authRemitAmtLimit = declarationReportTotal.get(0);
				declarationTotalAmt = authRemitAmtLimit.getAuthLimit();
			}
			
			if(totalAmt.compareTo(declarationTotalAmt)>=1)
			{
				setBooDeclarationReportDisplay(true);
			}

			return tempCollectionDetails;

		}

		private String reportFileName;
		private boolean booDeclarationReportDisplay;
		private Boolean visableExceptionDailogForReceipt=false;
		private Boolean visableExceptionDailogForApplication=false;




		public String getReportFileName() {
			return reportFileName;
		}

		public void setReportFileName(String reportFileName) {
			this.reportFileName = reportFileName;
		}

		public boolean isBooDeclarationReportDisplay() {
			return booDeclarationReportDisplay;
		}

		public void setBooDeclarationReportDisplay(boolean booDeclarationReportDisplay) {
			this.booDeclarationReportDisplay = booDeclarationReportDisplay;
		}

		public Boolean getVisableExceptionDailogForReceipt() {
			return visableExceptionDailogForReceipt;
		}

		public void setVisableExceptionDailogForReceipt(Boolean visableExceptionDailogForReceipt) {
			this.visableExceptionDailogForReceipt = visableExceptionDailogForReceipt;
		}

		public Boolean getVisableExceptionDailogForApplication() {
			return visableExceptionDailogForApplication;
		}

		public void setVisableExceptionDailogForApplication(Boolean visableExceptionDailogForApplication) {
			this.visableExceptionDailogForApplication = visableExceptionDailogForApplication;
		}
		
		
		//Added Ram Mohan for customer E-mail 29/02/2016
		private String customerEmail;
		private Boolean booReportDisplay;

		

		public Boolean getBooReportDisplay() {
			return booReportDisplay;
		}
		public void setBooReportDisplay(Boolean booReportDisplay) {
			this.booReportDisplay = booReportDisplay;
		}
		public String getCustomerEmail() {
			return customerEmail;
		}
		public void setCustomerEmail(String customerEmail) {
			this.customerEmail = customerEmail;
		}
		public List<TempForeignCurrencyAdjust> saveForeignCurrencyAdjust(TempCollection collect) {
			List<TempForeignCurrencyAdjust> lstdenominationRecords = new ArrayList<TempForeignCurrencyAdjust>();
			try {
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				Date acc_Month = null;
				try {
					acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ShoppingCartDataTableBean shoppingCartDetails = null;
				if (lstselectedrecord.size() > 0) {
					shoppingCartDetails = lstselectedrecord.get(0);
				}
				int i = 0;
				for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstData) {
					log.info("foreignLocalCurrencyDataTable.getQty() ^^^^^^^^^^^^^^^^^" + foreignLocalCurrencyDataTable.getQty());
					if (!foreignLocalCurrencyDataTable.getQty().equals("") && foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
						TempForeignCurrencyAdjust foreignCurrencyAdjust = new TempForeignCurrencyAdjust();
						CountryMaster countryMaster = new CountryMaster();
						countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
						foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
						Customer customer = new Customer();
						customer.setCustomerId(getCustomerNo());
						foreignCurrencyAdjust.setFsCustomer(customer);
						foreignCurrencyAdjust.setDocumentDate(new Date());
						CurrencyMaster currencyMaster = new CurrencyMaster();
						currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
						foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
						foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
						foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
						CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
						denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
						foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
						foreignCurrencyAdjust.setExchangeRate(shoppingCartDetails.getExchangeRateApplied());
						foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());
						foreignCurrencyAdjust.setDocumentFinanceYear(generalService.getDealYear(new Date()).get(0).getFinancialYear());
						try {
							foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE));
							foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
							foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
							CountryBranch countryBranch = new CountryBranch();
							countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
							foreignCurrencyAdjust.setCountryBranch(countryBranch);
							foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
							foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
							foreignCurrencyAdjust.setTransactionType(Constants.C);
						} catch (Exception e) {
							e.printStackTrace();
						}
						foreignCurrencyAdjust.setCreatedDate(new Date());
						foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());
						if (collect != null) {
							foreignCurrencyAdjust.setTempCollection(collect);
						}
						lstdenominationRecords.add(foreignCurrencyAdjust);
					} else {
						log.info("Number of notes is 0");
					}
				}
				int j = 0;
				for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstRefundData) {
					log.info("foreignLocalCurrencyDataTable.getQty() ^^^^^^^^^^^^^^^^^" + foreignLocalCurrencyDataTable.getQty());
					if (!foreignLocalCurrencyDataTable.getQty().equals("") && foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
						TempForeignCurrencyAdjust foreignCurrencyAdjust = new TempForeignCurrencyAdjust();
						CountryMaster countryMaster = new CountryMaster();
						countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
						foreignCurrencyAdjust.setFsCountryMaster(countryMaster);
						Customer customer = new Customer();
						customer.setCustomerId(getCustomerNo());
						foreignCurrencyAdjust.setFsCustomer(customer);
						foreignCurrencyAdjust.setDocumentDate(new Date());
						CurrencyMaster currencyMaster = new CurrencyMaster();
						currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
						foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
						foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
						foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
						CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
						denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
						foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
						foreignCurrencyAdjust.setExchangeRate(shoppingCartDetails.getExchangeRateApplied());
						foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());
						try {
							foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE));
							foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++j));
							foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
							CountryBranch countryBranch = new CountryBranch();
							countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
							foreignCurrencyAdjust.setCountryBranch(countryBranch);
							foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
							foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
							foreignCurrencyAdjust.setTransactionType(Constants.F);
						} catch (Exception e) {
							e.printStackTrace();
						}
						foreignCurrencyAdjust.setCreatedDate(new Date());
						foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());
						if (collect != null) {
							foreignCurrencyAdjust.setTempCollection(collect);
						}
						lstdenominationRecords.add(foreignCurrencyAdjust);
					} else {
						log.info("Number of notes is 0");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstdenominationRecords;
		}
		public void generatePersonalRemittanceReceiptReport() throws IOException{
			setVisableExceptionDailogForReceipt(false);
			setExceptionMessageForReport(null);
			OutputStream outstream=null;
			try {
				fetchRemittanceReceiptReportData(new BigDecimal(getDocumentNo().toString()),getFinaceYear(),Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION);

				remittanceReceiptReportInit();
				byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);
				
				if(getCheckEmailForReport() && getEmailToSendReport()!=null)
				{
					//email sending to customer
					sendEmail(pdfasbytes);
				}else
				{
					HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
					httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
					httpServletResponse.addHeader("Content-disposition","attachment; filename=RemittanceReceiptReport.pdf");
					/*JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
					FacesContext.getCurrentInstance().responseComplete();*/
				

					outstream = httpServletResponse.getOutputStream();
					httpServletResponse.setContentType("application/pdf");
					httpServletResponse.setContentLength(pdfasbytes.length);
					String strSettings = "inline;filename=\"RemittanceReceiptReport.pdf\"";
					httpServletResponse.setHeader("Content-Disposition", strSettings);
					outstream.write(pdfasbytes, 0, pdfasbytes.length);
					outstream.flush();
					FacesContext.getCurrentInstance().responseComplete();
				}
				

			} catch (Exception e) {
				setExceptionMessageForReport(null);
				setVisableExceptionDailogForReceipt(true);
				if(e.getMessage()!=null){
					setExceptionMessageForReport(e.getMessage());	
				}else{
					setExceptionMessageForReport("Exception  :"+e);
				}
				RequestContext.getCurrentInstance().execute("exceptioninReceipt.show();");

				/*if(i>0){	
					try {
						Thread.sleep(40000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				i=i+1;*/
			}finally{
				if(outstream!=null){
					outstream.close();
				}
			}
		}
		public boolean updadateRecordsNotNull(String status) {
			BigDecimal financialYearId = getFinaceYearId();
			for (ShoppingCartDataTableBean shoppingCartDataTableObj : shoppingcartDTList) {
				if (shoppingCartDataTableObj.getSelectedrecord()) {
					if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.FCSale)) {
						if (shoppingCartDataTableObj.getSelectedrecord()) {
							BigDecimal paymentReciptPk = iPersonalRemittanceService.getReceiptPaymentTableStatus(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo());
							if (paymentReciptPk == null) {
								return false;
							}
						}
					} else if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
						if (shoppingCartDataTableObj.getSelectedrecord()) {
							BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationstatus(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo(),shoppingCartDataTableObj.getDocumentId(),status,financialYearId,shoppingCartDataTableObj.getCompanyId());
							if (remittanceAppPk == null) {
								return false;
							}
						}
					}
				}
			}
			for (ShoppingCartDataTableBean shoppingCartDataTableObj : shoppingcartDTList) {
				if (shoppingCartDataTableObj.getSelectedrecord()) {
					if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.FCSale)) {
						if (shoppingCartDataTableObj.getSelectedrecord()) {
							BigDecimal paymentReciptPk = iPersonalRemittanceService.getReceiptPaymentTablePk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo());
							iPersonalRemittanceService.updateReceiptPaymentTableData(paymentReciptPk, status);
							List<ForeignCurrencyAdjustApp> foreignCurrencyAdjustAppList = iPersonalRemittanceService.getForeignCurrencyAdjustAppPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo());
							for (ForeignCurrencyAdjustApp foreignCurrencyAdjustAppPk : foreignCurrencyAdjustAppList) {
								iPersonalRemittanceService.updateForeignCurrencyAdjustAppTableData(foreignCurrencyAdjustAppPk.getForeignCurrencyAdjustId(), status);
							}
						}
					} else if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
						if (shoppingCartDataTableObj.getSelectedrecord()) {
							//BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo());
							BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo(),shoppingCartDataTableObj.getDocumentId(),financialYearId,shoppingCartDataTableObj.getCompanyId());
							if (remittanceAppPk != null) {
								iPersonalRemittanceService.updateRemittanceApplicationTableData(remittanceAppPk, status);
							}
						}
					}
				}
			}
			return true;
		}
		

		private String exceptionMessageForReport;

		@Autowired
		ApllicationMailer1 mailService;
		
		


		public ApllicationMailer1 getMailService() {
			return mailService;
		}

		public void setMailService(ApllicationMailer1 mailService) {
			this.mailService = mailService;
		}

		public String getExceptionMessageForReport() {
			return exceptionMessageForReport;
		}

		public void setExceptionMessageForReport(String exceptionMessageForReport) {
			this.exceptionMessageForReport = exceptionMessageForReport;
		}
		public void sendEmail(byte[] pdfasbytes) throws com.lowagie.text.DocumentException, IOException, AddressException, javax.mail.MessagingException{

			String from = sessionStateManage.getEmail();
			String subject = "Remittance Receipt Report From Al-Mulla Exchange";
			try {
				List<ApplicationSetup> lstApplicationSetup=iPersonalRemittanceService.getEmailFromAppSetup(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId());
				
				
				mailService.sendMailToCustomerWithAttachment(lstApplicationSetup, getEmailToSendReport(), subject, pdfasbytes,getCustomerNameForReport());
			} catch (AMGException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		private String customerNameForReport;

		@Autowired
		ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

		public String getCustomerNameForReport() {
			return customerNameForReport;
		}
		public void setCustomerNameForReport(String customerNameForReport) {
			this.customerNameForReport = customerNameForReport;
		}
		private List<CollectionDetailView> collectionViewList = new CopyOnWriteArrayList<CollectionDetailView>();

		private List<RemittanceReceiptSubreport> remittanceReceiptSubreportList = new ArrayList<RemittanceReceiptSubreport>();
		

		public List<CollectionDetailView> getCollectionViewList() {
			return collectionViewList;
		}

		public void setCollectionViewList(List<CollectionDetailView> collectionViewList) {
			this.collectionViewList = collectionViewList;
		}

		
		public ISpecialCustomerDealRequestService<T> getSpecialCustomerDealRequestService() {
			return specialCustomerDealRequestService;
		}

		public void setSpecialCustomerDealRequestService(ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService) {
			this.specialCustomerDealRequestService = specialCustomerDealRequestService;
		}

		private void fetchRemittanceReceiptReportData(BigDecimal documentNumber,BigDecimal finaceYear, String documentCode) throws Exception{

			collectionViewList.clear();
			remittanceReceiptSubreportList = new ArrayList<RemittanceReceiptSubreport>();



			List<RemittanceApplicationView> remittanceApplicationList = new ArrayList<RemittanceApplicationView>();

			List<RemittanceApplicationView> fcsaleList = new ArrayList<RemittanceApplicationView>();

			List<RemittanceReportBean> remittanceApplList = new ArrayList<RemittanceReportBean>();

			List<RemittanceReportBean> fcsaleAppList = new ArrayList<RemittanceReportBean>();

			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			String subReportPath = realPath + Constants.SUB_REPORT_PATH;
			String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String currentDate = dateFormat.format(new Date());
			int noOfTransactions = 0;

			String purposeOfRemittance = "";
			String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));

			List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(sessionStateManage.getUserName());

			List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsForRemittanceReceiptReport(documentNumber,finaceYear,documentCode);

			if (remittanceViewlist.size() > 0) {

				for (RemittanceApplicationView remittanceAppview : remittanceViewlist) {

					if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("REMITTANCE")) {
						remittanceApplicationList.add(remittanceAppview);
						noOfTransactions= noOfTransactions+1;
					} else if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("FOREIGN CURRENCY SALE")) {
						fcsaleList.add(remittanceAppview);
						noOfTransactions= noOfTransactions+1;
					}
				}

				//remittance List
				for (RemittanceApplicationView view : remittanceApplicationList) {

					RemittanceReportBean obj = new RemittanceReportBean();


					// setting customer reference	
					StringBuffer customerReff = new StringBuffer();

					if(view.getCustomerReference() != null){
						customerReff.append(view.getCustomerReference());
						customerReff.append(" / ");
					}

					StringBuffer customerName = new StringBuffer();

					if(view.getFirstName() != null){
						customerName.append(" ");
						customerName.append(view.getFirstName());
						customerReff.append(" ");
						customerReff.append(view.getFirstName());
					}
					if(view.getMiddleName() != null){
						customerName.append(" ");
						customerName.append(view.getMiddleName());
						customerReff.append(" ");
						customerReff.append(view.getMiddleName());
					}
					if(view.getLastName()!=null){
						customerName.append(" ");
						customerName.append(view.getLastName());
						customerReff.append(" ");
						customerReff.append(view.getLastName());
					}

					obj.setFirstName(customerReff.toString());
					setCustomerNameForReport(customerName.toString());

					if(view.getContactNumber()!=null){
						obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
					}

					obj.setCivilId(view.getIdentityInt());

					Date sysdate = view.getIdentityExpiryDate();

					if(sysdate!=null){
						obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
					}

					obj.setLocation(sessionStateManage.getLocation());

					//setting receipt Number
					StringBuffer receiptNo = new StringBuffer();

					if(view.getDocumentFinancialYear()!=null){
						receiptNo.append(view.getDocumentFinancialYear());
						receiptNo.append(" / ");
					}
					if(view.getCollectionDocumentNo()!=null){
						receiptNo.append(view.getCollectionDocumentNo());
					}
					obj.setReceiptNo(receiptNo.toString());


					// setting transaction Number
					StringBuffer transactionNo = new StringBuffer();
					if(view.getDocumentFinancialYear()!=null){
						transactionNo.append(view.getDocumentFinancialYear());
						transactionNo.append(" / ");
					}
					if(view.getDocumentNo()!=null){
						transactionNo.append(view.getDocumentNo());
					}
					obj.setTransactionNo(transactionNo.toString());

					obj.setDate(currentDate);
					obj.setBeneficiaryName(view.getBeneficiaryName());
					obj.setBenefeciaryBankName(view.getBeneficiaryBank());
					obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
					obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
					obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
					obj.setPhoneNumber(view.getPhoneNumber());
					obj.setUserName(sessionStateManage.getUserName());
					obj.setPinNo(null);



					HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

					String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
					String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
					String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
					String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
					String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
					String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");


					StringBuffer loyaltyPoint = new StringBuffer();
					if(!prLtyStr1.trim().equals("")){
						loyaltyPoint.append(prLtyStr1);
					}
					if(!prLtyStr2.trim().equals("")){
						loyaltyPoint.append("\n");
						loyaltyPoint.append(prLtyStr2);
					}
					obj.setLoyalityPointExpiring(loyaltyPoint.toString());

					StringBuffer insurence1 = new StringBuffer();
					if(!prInsStr1.trim().equals("")){
						insurence1.append(prInsStr1);
					}
					if(!prInsStrAr1.trim().equals("")){
						insurence1.append("\n");
						insurence1.append(prInsStrAr1);
					}
					obj.setInsurence1(insurence1.toString());

					StringBuffer insurence2 = new StringBuffer();
					if(!prInsStr2.trim().equals("")){
						insurence2.append(prInsStr2);
					}
					if(!prInsStrAr2.trim().equals("")){
						insurence2.append(prInsStrAr2);
					}
					obj.setInsurence2(insurence2.toString());


					// setting beneficiary Address
					StringBuffer  address = new StringBuffer();
					if(view.getBeneStateName() != null){
						address.append(view.getBeneStateName());
						address.append(",  ");
					}
					if(view.getBeneCityName() != null){
						address.append(view.getBeneCityName());
						address.append(",  ");
					}
					if(view.getBeneDistrictName() != null){
						address.append(view.getBeneDistrictName());
					}
					obj.setAddress(address.toString());

					//setting payment channel 
					StringBuffer paymentChannel = new StringBuffer();
					if(view.getRemittanceDescription() != null){
						paymentChannel.append(view.getRemittanceDescription());
						paymentChannel.append(" - ");
					}
					if(view.getDeliveryDescription() != null){
						paymentChannel.append(view.getDeliveryDescription());
					}
					obj.setPaymentChannel(paymentChannel.toString());

					String currencyAndAmount=null;
					BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));
					if(view.getCurrencyQuoteName()!=null && foreignTransationAmount!=null){
						currencyAndAmount = view.getCurrencyQuoteName()+"     ******"+foreignTransationAmount;
					}
					obj.setCurrencyAndAmount(currencyAndAmount);
					List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

					/*if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
						obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
					}*/
					for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
						if(purposeOfRemittance.equals("")){
							purposeOfRemittance  = purposeObj.getFlexiFieldValue();
						}else{
							purposeOfRemittance  = purposeOfRemittance+", "+purposeObj.getFlexiFieldValue();
						}
					}
					
					if(purposeOfRemittance != null && !purposeOfRemittance.equalsIgnoreCase("")){
						obj.setPerposeOfRemittance(purposeOfRemittance);
					}

					if(view.getCurrencyQuoteName()!=null && currencyQuoteName!=null && view.getExchangeRateApplied()!=null){
						obj.setExchangeRate(view.getCurrencyQuoteName()+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
					}

					if(view.getLocalTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
					}

					if(view.getLocalCommissionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
					}

					if(view.getLocalChargeAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
					}

					if(view.getLocalNetTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal netAmount=GetRound.roundBigDecimal((view.getLocalNetTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setTotalAmount(currencyQuoteName+"     ******"+netAmount.toString());
					}

					obj.setFutherInstructions(view.getInstructions());
					obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
					obj.setIntermediataryBank(view.getBenefeciaryInterBank1());


					List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());

					CollectionDetailView collectionDetailView = collectionDetailList1.get(0);

					if(collectionDetailView.getNetAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectNetAmount=GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
					}

					if(collectionDetailView.getPaidAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectPaidAmount=GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
					}

					if(collectionDetailView.getRefundedAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectRefundAmount=GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);
					}

					obj.setSubReport(subReportPath);
					obj.setCollectionDetailList(calculateCollectionMode(view));

					//obj.setSignature(view.getCustomerSignature()); 
					// Rabil

					// Added by Rabil
					try {
						if (view.getCustomerSignatureClob() != null) {
							obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					if(employeeList!=null && employeeList.size()>0){
						try {
							Employee employee = employeeList.get(0);
							if (employee.getSignatureSpecimenClob() != null) {
								obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}


					remittanceApplList.add(obj);
				}

				// for foreign currency Sale report
				for (RemittanceApplicationView view : fcsaleList) {

					RemittanceReportBean obj = new RemittanceReportBean();

					// setting customer reference	
					StringBuffer customerReff = new StringBuffer();

					if(view.getCustomerReference() != null){
						customerReff.append(view.getCustomerReference());
						customerReff.append(" / ");
					}

					StringBuffer customerName = new StringBuffer();

					if(view.getFirstName() != null){
						customerName.append(" ");
						customerName.append(view.getFirstName());
						customerReff.append(" ");
						customerReff.append(view.getFirstName());
					}
					if(view.getMiddleName() != null){
						customerName.append(" ");
						customerName.append(view.getMiddleName());
						customerReff.append(" ");
						customerReff.append(view.getMiddleName());
					}
					if(view.getLastName()!=null){
						customerName.append(" ");
						customerName.append(view.getLastName());
						customerReff.append(" ");
						customerReff.append(view.getLastName());
					}

					obj.setFirstName(customerReff.toString());
					setCustomerNameForReport(customerName.toString());

					if(view.getContactNumber()!=null){
						obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
					}
					obj.setCivilId(view.getIdentityInt());

					Date sysdate = view.getIdentityExpiryDate();
					if(sysdate!=null){
						obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));
					}


					HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

					String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
					String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
					String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
					String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
					String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
					String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");


					StringBuffer loyaltyPoint = new StringBuffer();

					if(!prLtyStr1.trim().equals("")){
						loyaltyPoint.append(prLtyStr1);
					}
					if(!prLtyStr2.trim().equals("")){
						loyaltyPoint.append("\n");
						loyaltyPoint.append(prLtyStr2);
					}
					obj.setLoyalityPointExpiring(loyaltyPoint.toString());

					StringBuffer insurence = new StringBuffer();

					if(!prInsStr1.trim().equals("")){
						insurence.append(prInsStr1);
					}
					if(prInsStrAr1.trim().equals("")){
						insurence.append("\n");
						insurence.append(prInsStrAr1);
					}

					if(!prInsStr2.trim().equals("")){
						insurence.append("\n");
						insurence.append(prInsStr2);
					}
					if(!prInsStrAr2.trim().equals("")){
						insurence.append("\n");
						insurence.append(prInsStrAr2);
					}
					obj.setInsurence1(insurence.toString());


					obj.setLocation(view.getCountryBranchName());
					obj.setPhoneNumber(view.getPhoneNumber());
					obj.setDate(currentDate);
					obj.setUserName(sessionStateManage.getUserName());

					//setting receipt Number
					StringBuffer receiptNo = new StringBuffer();
					if(view.getDocumentFinancialYear()!=null){
						receiptNo.append(view.getDocumentFinancialYear());
						receiptNo.append(" / ");
					}
					if(view.getCollectionDocumentNo()!=null){
						receiptNo.append(view.getCollectionDocumentNo());
					}
					obj.setReceiptNo(receiptNo.toString());

					// setting transaction Number
					StringBuffer transactionNo = new StringBuffer();
					if(view.getDocumentFinancialYear()!=null){
						transactionNo.append(view.getDocumentFinancialYear());
						transactionNo.append(" / ");
					}
					if(view.getDocumentNo()!=null){
						transactionNo.append(view.getDocumentNo());
					}
					obj.setTransactionNo(transactionNo.toString());

					if(view.getForeignCurrencyId()!=null){
						String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(view.getForeignCurrencyId());
						obj.setCurrencyQuoteName(saleCurrency);
					}
					String saleCurrencyCode =null;

					if(view.getForeignCurrencyId()!=null){
						saleCurrencyCode = generalService.getCurrencyQuote(view.getForeignCurrencyId());
					}

					if (view.getForeignTransactionAmount() != null && saleCurrencyCode != null) {
						BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));	
						obj.setSaleAmount( saleCurrencyCode+"     ******"+foreignTransationAmount.toString());
					} 

					if( view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
						BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setPurchageAmount(currencyQuoteName+"     ******"+transationAmount.toString());
					}

					if(saleCurrencyCode!=null && currencyQuoteName!=null && view.getExchangeRateApplied()!=null){
						obj.setExchangeRate(saleCurrencyCode+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
					}

					if(view.getLocalTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
						BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
					}

					if(view.getLocalCommissionAmount()!=null && view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
						BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
						obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
					}

					List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

					if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
						obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getFlexiFieldValue());
					}
					obj.setSourceOfIncome(view.getSourceOfIncomeDesc());



					List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
					if(collectionDetailList1.size()>0){
						CollectionDetailView collectionDetailView = collectionDetailList1.get(0);
						if(collectionDetailView.getNetAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
							BigDecimal collectNetAmount=GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
							obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
						}
						if(collectionDetailView.getPaidAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
							BigDecimal collectPaidAmount=GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
							obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
						}
						if(collectionDetailView.getRefundedAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
							BigDecimal collectRefundAmount=GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
							obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);
						}
						obj.setCollectionDetailList(calculateCollectionMode(view));
					}

					obj.setSubReport(subReportPath);
					obj.setUserName(sessionStateManage.getUserName());

					//	obj.setSignature(view.getCustomerSignature());

					try {
						if (view.getCustomerSignatureClob() != null) {
							obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					if(employeeList!=null && employeeList.size()>0){
						try {
							Employee employee = employeeList.get(0);
							if (employee.getSignatureSpecimenClob() != null) {
								obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}


					fcsaleAppList.add(obj);


				}

				//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
				RemittanceReceiptSubreport remittanceObj = new RemittanceReceiptSubreport();

				remittanceObj.setWaterMarkLogoPath(waterMark);
				remittanceObj.setWaterMarkCheck(false);
				remittanceObj.setFcsaleAppList(fcsaleAppList);
				remittanceObj.setRemittanceApplList(remittanceApplList);
				remittanceObj.setSubReport(subReportPath);

				if (fcsaleAppList!=null && fcsaleAppList.size()>0) {
					remittanceObj.setFcsaleApplicationCheck(true);
				} /*else {
						remittanceObj.setFcsaleApplicationCheck(false);
					}*/
				if(remittanceApplList!=null && remittanceApplList.size()>0){
					remittanceObj.setRemittanceReceiptCheck(true);
				}/*else{
						remittanceObj.setRemittanceReceiptCheck(false);
					}*/

				remittanceReceiptSubreportList.add(remittanceObj);

			} else {
				RequestContext.getCurrentInstance().execute("noDataForReport.show();");
				return;
			}


		}
		
		public List<RemittanceReportBean> calculateCollectionMode(RemittanceApplicationView viewCollectionObj){	
			List<RemittanceReportBean> collectionDetailList = new ArrayList<RemittanceReportBean>();
			List<CollectionPaymentDetailsView> collectionPaymentDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(viewCollectionObj.getCompanyId(),viewCollectionObj.getCollectionDocumentNo(),viewCollectionObj.getCollectionDocFinanceYear(),viewCollectionObj.getCollectionDocCode());

			int size = collectionPaymentDetailList.size();
			for(CollectionPaymentDetailsView viewObj: collectionPaymentDetailList){
				RemittanceReportBean obj = new RemittanceReportBean();
				if(viewObj.getCollectionMode()!=null && viewObj.getCollectionMode().equalsIgnoreCase("K")){
					obj.setCollectionMode(viewObj.getCollectionModeDesc());
					obj.setApprovalNo(viewObj.getApprovalNo());
					obj.setTransactionId(viewObj.getTransactionId());
					obj.setKnetreceiptDateTime(viewObj.getKnetReceiptDatenTime());
					obj.setKnetBooleanCheck(true);
					if(viewObj.getCollectAmount()!=null && viewCollectionObj.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectAmount=GetRound.roundBigDecimal((viewObj.getCollectAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
						obj.setCollectAmount(collectAmount);
					}
				}else{
					obj.setCollectionMode(viewObj.getCollectionModeDesc());
					obj.setKnetBooleanCheck(false);
					if(viewObj.getCollectAmount()!=null && viewCollectionObj.getLocalTransactionCurrencyId()!=null){
						BigDecimal collectAmount=GetRound.roundBigDecimal((viewObj.getCollectAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewCollectionObj.getLocalTransactionCurrencyId()));
						obj.setCollectAmount(collectAmount);
					}
				}
				if(size>1){
					obj.setDrawLine(true);
				}else{
					obj.setDrawLine(false);
				}
				collectionDetailList.add(obj);
				size = size-1;
			}
			return collectionDetailList;
		}
		
		public void approvalNoFromKnetIpos() throws UnsupportedEncodingException, IOException {

			try {
				log.info("******approvalNoFromKnetIpos START *****\n" + getColCash());
				System.out.println("getColCash amount  method " + getColCash() + "\t getColBankCode() :" + getColBankCode() + "\t getColCardNo() :"
						+ getColCardNo());
				int trnxAmount = 0;
				if (getColCash() != null) {
					trnxAmount = (int) (getColCash().intValue() * 1000);
				} else {
					setExceptionMessage("Please Enter Amount");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					return;
				}

				if (getColBankCode() != null) {
					log.info("Get Card No :" + getColBankCode());
				} else {
					setExceptionMessage("Please Select Bank");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					return;
				}

				if (getColCardNo() != null) {
					log.info("getColCardNo :" + getColCardNo());
				} else {
					setExceptionMessage("Please Enter Card Number");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					return;
				}

				if (getColNameofCard() != null) {
					log.info("getColNameofCard :" + getColNameofCard());
				} else {
					setExceptionMessage("Please Enter Name of Card");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					return;
				}

				String workingDir = System.getProperty("user.dir");
				log.info("workingDir :" + workingDir);
				System.out.println("web  workingDir approvalNoFromKnetIpos  application Current working directory : " + workingDir);

				String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

				log.info("absoluteWebPath :" + absoluteWebPath);

				if (receipt != null) {
					receipt.clearAll();
				}

				System.out.println("web absoluteWebPath approvalNoFromKnetIpos  application Current working directory : " + absoluteWebPath);

				WinEPTS_Wrapper api = new WinEPTS_Wrapper();
				String cashhier = sessionStateManage.getUserName() + "-" + getCustomerNo();
				log.info("*********Trnx net amount :" + trnxAmount + "\t Cashier or user Name: " + cashhier + "\t Document No." + getApplicationDocNum());


				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				String ipAddress = request.getHeader("X-FORWARDED-FOR");
				if (ipAddress == null) {
					ipAddress = request.getRemoteAddr();
				}

				log.info("ipAddress --"+ipAddress);
				api.setIPAdress(ipAddress);


				api.Payment(trnxAmount, cashhier, getApplicationDocNum().toString());
				receipt = api.getReceipt();
				//log.info("*********Web receipt receiptData:" + receipt.receiptData);

				if (receipt != null) {
					System.out.println("IF approvalNoFromKnetIpos  method Receipt Data  :\n" + receipt.receiptData);
					log.info("Receive  from KNET-IPOS:" + receipt.toString());
					log.info("KNET-IPO auth code :" + receipt.authorizationCode);
					log.info("KNET-IPO error description  :" + receipt.errorDescription);
					// setColApprovalNo(receipt.authorizationCode);
					log.info("******approvalNoFromKnetIpos :\n" + receipt.receiptData);

					// Approved :176 error 255

					if (receipt.errorDescription.equalsIgnoreCase("APPROVED") || (receipt.msgCode == 176)) {
						KnetLog knetLog = new KnetLog();
						setBooColApprovalNo(true);
						setKnetIposReceipt(receipt.receiptData.toString());
						setColApprovalNo(receipt.authorizationCode);
						setKnetTranId(receipt.transID);

						String dateTime = null;
						if (receipt.receiptDate != null && receipt.receiptTime != null) {
							dateTime = receipt.receiptDate.substring(0, 2) + "/" + receipt.receiptDate.substring(2, 4) + "/"
									+ receipt.receiptDate.substring(4, 8) + " " + receipt.receiptTime.substring(0, 2) + ":"
									+ receipt.receiptTime.substring(2, 4);

						}
						log.info("Knet ReceiptDate and Time: " + dateTime);
						System.out.println("Knet ReceiptDate and Time: " + dateTime);
						setKnetReceiptDate(dateTime);
						setKnetReceiptTime(receipt.receiptTime);

						knetLog.setApplicationCountryId(sessionStateManage.getCountryId());
						knetLog.setCompanyId(sessionStateManage.getCompanyId());
						knetLog.setCustmoerId(getCustomerNo());
						knetLog.setAuthCode(receipt.authorizationCode);
						knetLog.setCreatedBy(sessionStateManage.getUserName());
						knetLog.setCreatedDate(new Date());
						knetLog.setIsActive("Y");
						knetLog.setKnetAmount(new BigDecimal(receipt.amount).divide(new BigDecimal(1000)));
						knetLog.setKnetMessage("TRANSACTION APPROVED");
						knetLog.setReceipt(receipt.toString());
						knetLog.setReceiptData(receipt.receiptData);
						iPersonalRemittanceService.saveKnetLogDetails(knetLog);

					} else {

						String dateTime = null;
						if (receipt.receiptDate != null && receipt.receiptTime != null) {
							dateTime = receipt.receiptDate.substring(0, 2) + "/" + receipt.receiptDate.substring(2, 4) + "/"
									+ receipt.receiptDate.substring(4, 8) + " " + receipt.receiptTime.substring(0, 2) + ":"
									+ receipt.receiptTime.substring(2, 4);

						}
						log.info("Knet ReceiptDate and Time: " + dateTime);

						KnetLog knetLog = new KnetLog();
						knetLog.setApplicationCountryId(sessionStateManage.getCountryId());
						knetLog.setCompanyId(sessionStateManage.getCompanyId());
						knetLog.setCustmoerId(getCustomerNo());
						knetLog.setAuthCode(receipt.authorizationCode);
						knetLog.setCreatedBy(sessionStateManage.getUserName());
						knetLog.setCreatedDate(new Date());
						knetLog.setIsActive("N");
						knetLog.setKnetAmount(new BigDecimal(receipt.amount).divide(new BigDecimal(1000)));
						knetLog.setKnetMessage(receipt.errorDescription);
						knetLog.setReceipt(receipt.toString());
						knetLog.setReceiptData(receipt.receiptData);

						iPersonalRemittanceService.saveKnetLogDetails(knetLog);

						setBooColApprovalNo(false);
						setKnetIposReceipt(null);
						setExceptionMessage(receipt.errorDescription + " PLEASE REMOVE CARD AND TRY AGAIN");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
						return;
					}
				} else {

					KnetLog knetLog = new KnetLog();
					knetLog.setApplicationCountryId(sessionStateManage.getCountryId());
					knetLog.setCompanyId(sessionStateManage.getCompanyId());
					knetLog.setCustmoerId(getCustomerNo());
					knetLog.setAuthCode("C");
					knetLog.setCreatedBy(sessionStateManage.getUserName());
					knetLog.setCreatedDate(new Date());
					knetLog.setIsActive("C");
					knetLog.setKnetAmount(getColCash());
					knetLog.setKnetMessage("CANNOT CONNECT TO KNET SERVER . ");
					knetLog.setReceipt("CANNOT CONNECT TO KNET SERVER. ");
					knetLog.setReceiptData("CANNOT CONNECT TO KNET SERVER. ");

					iPersonalRemittanceService.saveKnetLogDetails(knetLog);

					setBooColApprovalNo(false);
					setExceptionMessage("CANNOT CONNECT TO KNET SERVER. ");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					return;
				}

			} catch (Exception e) {
				setBooColApprovalNo(false);
				e.printStackTrace();
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}

			System.out.println("++++++++++++++++++PaymentInitiation END ++++++++++++++++++++++\n");
			log.info("******approvalNoFromKnetIpos END *****\n");
		}
		
		private List<CustomerDeclerationView> lstDeclarationView = new ArrayList<CustomerDeclerationView>();

		private List<DeclerationReportBean> lstDispDeclarationView = new ArrayList<DeclerationReportBean>();

		@Autowired
		ICashTransferLToLService cashTransferLToLService;
		
		
		
		public ICashTransferLToLService getCashTransferLToLService() {
			return cashTransferLToLService;
		}

		public void setCashTransferLToLService(ICashTransferLToLService cashTransferLToLService) {
			this.cashTransferLToLService = cashTransferLToLService;
		}

		public List<CustomerDeclerationView> getLstDeclarationView() {
			return lstDeclarationView;
		}

		public void setLstDeclarationView(List<CustomerDeclerationView> lstDeclarationView) {
			this.lstDeclarationView = lstDeclarationView;
		}

		public List<DeclerationReportBean> getLstDispDeclarationView() {
			return lstDispDeclarationView;
		}

		public void setLstDispDeclarationView(List<DeclerationReportBean> lstDispDeclarationView) {
			this.lstDispDeclarationView = lstDispDeclarationView;
		}

		public void generateDeclerationReports() throws IOException{
			setVisableExceptionDailogForReceipt(false);
			setExceptionMessageForReport(null);
			OutputStream outstream=null;
			try{
				lstDeclarationView.clear();
				lstDispDeclarationView.clear();
				fetchDeclatationReports();
				initDeclerationReports();
				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.addHeader("Content-disposition", "attachment; filename=DeclarationReport.pdf");
				/*			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();*/


				byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

				outstream = httpServletResponse.getOutputStream();
				httpServletResponse.setContentType("application/pdf");
				httpServletResponse.setContentLength(pdfasbytes.length);
				String strSettings = "inline;filename=\"DeclarationReport.pdf\"";
				httpServletResponse.setHeader("Content-Disposition", strSettings);
				outstream.write(pdfasbytes, 0, pdfasbytes.length);
				outstream.flush();
				FacesContext.getCurrentInstance().responseComplete();

			}catch(Exception e){
				setExceptionMessageForReport(null);
				setVisableExceptionDailogForReceipt(true);
				if(e.getMessage()!=null){
					setExceptionMessageForReport(e.getMessage());	
				}else{
					setExceptionMessageForReport("Exception  :"+e);
				}
				RequestContext.getCurrentInstance().execute("exceptioninReceipt.show();");
			}finally{
				if(outstream!=null){
					outstream.close();
				}
			}
		}
		public void initDeclerationReports() throws JRException {
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lstDispDeclarationView);
			String reportPath = null;
			reportPath = getReportFileName();
			jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
		}
		public void fetchDeclatationReports()throws Exception {
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			//String logoPath = realPath + Constants.LOGO_PATH;
			
			
			String logoPath =null;
			if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
				logoPath = realPath+Constants.LOGO_PATH;
			}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
				logoPath = realPath+Constants.LOGO_PATH_OM;
			}else{
				logoPath =realPath+Constants.LOGO_PATH;
			}
			

			//	lstDeclarationView = iPersonalRemittanceService.getCustomerDeclerationData(sessionStateManage.getCountryId(), getFinaceYear(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION), new BigDecimal(getDocumentNo()));

			lstDeclarationView = iPersonalRemittanceService.getCustomerDeclerationData(sessionStateManage.getCountryId(), new BigDecimal(2015), new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION), new BigDecimal(90000144));

			String paymentMode = "C";
			/*	for(PersonalRemittanceCollectionDataTable dataObj:coldatatablevalues){
				if(dataObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)){
					paymentMode = Constants.CashCode;
					break;
				}else{
					paymentMode = "A";
				}
			}*/

			if (lstDeclarationView !=null && lstDeclarationView.size() > 0) {
				String reportType = iPersonalRemittanceService.declartionLimitCheck(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), getCustomerNo(), new Date(), paymentMode);
				if (reportType.trim()!="") {
					if (reportType.equalsIgnoreCase("Y")) {
						if (sessionStateManage.getLanguageId().equals(new BigDecimal("1"))) {
							//setReportFileName(realPath + "\\reports\\design\\decleration2500reportenglish.jasper");//For window WF
							setReportFileName(realPath + "//reports//design//decleration2500reportenglish.jasper");
						} else if (sessionStateManage.getLanguageId().equals(new BigDecimal("2"))) {
							//setReportFileName(realPath + "\\reports\\design\\declaration2500reportarabic.jasper");// For window
							
							setReportFileName(realPath + "//reports//design//declaration2500reportarabic.jasper");
						}
					} else{
						if (sessionStateManage.getLanguageId().equals(new BigDecimal("1"))) {
							//setReportFileName(realPath + "\\reports\\design\\decleration1000reportenglish.jasper");
							setReportFileName(realPath + "//reports//design//decleration1000reportenglish.jasper");
						} else if (sessionStateManage.getLanguageId().equals(new BigDecimal("2"))) {
							//setReportFileName(realPath + "\\reports\\design\\declaration1000reportarabic.jasper");
							setReportFileName(realPath + "//reports//design//declaration1000reportarabic.jasper");
						}
					}
				}

				for (CustomerDeclerationView customerDeclerationView : lstDeclarationView) {

					DeclerationReportBean declerationReportBean = new DeclerationReportBean();
					//set branch name
					String branchName =new String();
					String companyName =new String();
					StringBuffer toAddress =new StringBuffer();
					StringBuffer customerName=new StringBuffer();
					String countryName =new String();
					List<CountryBranch> branchList = cashTransferLToLService.getBranchName(new BigDecimal(sessionStateManage.getBranchId()));
					if(branchList.size()>0){
						branchName = branchList.get(0).getBranchName();
					}

					List<CompanyMasterDesc> companyDesc =	generalService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
					countryName =  generalService.getCountryName(sessionStateManage.getCountryId());
					CompanyMasterDesc companyDesObj = companyDesc.get(0);

					//set company address
					if(companyDesObj!=null){
						companyName	 = companyDesObj.getCompanyName();
					}

					// to set to address
					toAddress.append("THE BRANCH MANAGER");
					toAddress.append("\n");
					toAddress.append(branchName==null?"":branchName.toUpperCase());
					toAddress.append("\n");
					toAddress.append(companyName==null?"":companyName.toUpperCase());
					toAddress.append("\n");
					toAddress.append(countryName==null?"":countryName.toUpperCase());

					// tp set Customer Name
					customerName.append(customerDeclerationView.getFirstName()==null?"":customerDeclerationView.getFirstName());
					customerName.append(" ");
					customerName.append(customerDeclerationView.getMiddleName()==null?"":customerDeclerationView.getMiddleName());
					customerName.append(" ");
					customerName.append(customerDeclerationView.getLastName()==null?"":customerDeclerationView.getLastName());

					//to set Employee ment details
					StringBuffer employer = new StringBuffer();
					List<CustomerEmployeeInfoView> customerEmployeeInfo = icustomerRegistrationService.findCustomerEmployeeInfo(customerDeclerationView.getCustomerId());
					if(customerEmployeeInfo.size()>0){
						CustomerEmployeeInfoView customerEmployee = customerEmployeeInfo.get(0);
						employer.append(customerEmployee.getEmployeeName()==null?"":customerEmployee.getEmployeeName());
						employer.append(" ");
						employer.append(customerEmployee.getEmployeeProofDesc()==null?"":customerEmployee.getEmployeeProofDesc());
					}
					
					// to set Purpose of transcation
					String purpose ="";
					List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(customerDeclerationView.getDocumentNo(),customerDeclerationView.getDocumentFinanceYear());
					for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
						if(purpose.equals("")){
							purpose  = purposeObj.getFlexiFieldValue();
						}else{
							purpose  = purpose+", "+purposeObj.getFlexiFieldValue();
						}
					}
					
					if(purpose != null && !purpose.equalsIgnoreCase("")){
						declerationReportBean.setPurpose(purpose.toUpperCase());
					}
					
					declerationReportBean.setCustomerName(customerName==null?"":customerName.toString().toUpperCase());
					declerationReportBean.setCivilId(customerDeclerationView.getCivilId()==null?"":customerDeclerationView.getCivilId().toPlainString());
					declerationReportBean.setSourceOfIncome(customerDeclerationView.getSourceOfIncome()==null?" ":customerDeclerationView.getSourceOfIncome().toUpperCase());
					declerationReportBean.setEmployeer(employer==null?" ":employer.toString().toUpperCase());
					declerationReportBean.setCompanyName(companyName==null?" ":companyName.toUpperCase());
					declerationReportBean.setTitle(customerDeclerationView.getTitle());
					declerationReportBean.setNationality(customerDeclerationView.getNationality().toUpperCase());
					declerationReportBean.setContactNo(customerDeclerationView.getContactNo()==null?"":customerDeclerationView.getContactNo().toString());
					declerationReportBean.setToAddress(toAddress.toString());
					declerationReportBean.setLogoPath(logoPath);

					StringBuffer receiptRef = new StringBuffer();
					receiptRef.append(customerDeclerationView.getCollectionDocumentFinanceYear()==null?"":customerDeclerationView.getCollectionDocumentFinanceYear());
					receiptRef.append(" / ");
					receiptRef.append(customerDeclerationView.getCollectionDocumentNo()==null?"":customerDeclerationView.getCollectionDocumentNo());
					declerationReportBean.setReceiptReferenceNo(receiptRef.toString());

					StringBuffer transactionRef = new StringBuffer();
					transactionRef.append(customerDeclerationView.getCollectionDocumentFinanceYear()==null?"":customerDeclerationView.getCollectionDocumentFinanceYear());
					transactionRef.append(" / ");
					transactionRef.append(customerDeclerationView.getDocumentNo()==null?"":customerDeclerationView.getDocumentNo());
					declerationReportBean.setTransactionRefNo(transactionRef.toString());

					declerationReportBean.setCashierName(sessionStateManage.getUserName().toUpperCase());

					if (customerDeclerationView.getSignatureSpecimenClob() != null) {
						declerationReportBean.setSignatutre(customerDeclerationView.getSignatureSpecimenClob().getSubString(1,(int) customerDeclerationView.getSignatureSpecimenClob().length()));
					}
					
					// currency and amount based on parameter
					String localCurrencyQuote = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
					
					List<AuthicationLimitCheckView> declarationReportTotal = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.DeclarationReportTotalAmt);
					BigDecimal declarationTotalAmt = BigDecimal.ZERO;
					if(declarationReportTotal.size() != 0){
						AuthicationLimitCheckView authRemitAmtLimit = declarationReportTotal.get(0);
						declarationTotalAmt = authRemitAmtLimit.getAuthLimit();
					}
					
					declerationReportBean.setCurrencyAmount(localCurrencyQuote + " " + declarationTotalAmt);
					
					List<AuthicationLimitCheckView> declarationReportCashAmt = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.DeclarationReportforCash);
					
					BigDecimal declarationCashAmt = BigDecimal.ZERO;
					if(declarationReportCashAmt.size() != 0){
						AuthicationLimitCheckView authRemitAmtLimit = declarationReportCashAmt.get(0);
						declarationCashAmt = authRemitAmtLimit.getAuthLimit();
					}
					declerationReportBean.setCurrencyAmountCash(localCurrencyQuote + " " + declarationCashAmt);


					lstDispDeclarationView.add(declerationReportBean);

				}
			}
		}

		public BigDecimal getApprovalYear() {
			return approvalYear;
		}

		public void setApprovalYear(BigDecimal approvalYear) {
			this.approvalYear = approvalYear;
		}

		public BigDecimal getApprovalNo() {
			return approvalNo;
		}

		public void setApprovalNo(BigDecimal approvalNo) {
			this.approvalNo = approvalNo;
		}
		
}