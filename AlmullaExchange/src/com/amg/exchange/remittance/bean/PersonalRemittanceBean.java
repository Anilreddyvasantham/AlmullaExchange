package com.amg.exchange.remittance.bean;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
import javax.xml.parsers.ParserConfigurationException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import KNET_WinEPTS_API.Receipt;
import KNET_WinEPTS_API.WinEPTS_Wrapper;

import com.amg.exchange.aop.FcSaleReport;
import com.amg.exchange.bean.LoginBean;
import com.amg.exchange.beneficiary.bean.BeneficiaryCreationBean;
import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.bean.ForeignLocalCurrencyDataTable;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustApp;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentApp;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.TempCollectDetail;
import com.amg.exchange.foreigncurrency.model.TempCollection;
import com.amg.exchange.foreigncurrency.model.TempForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.online.bean.BranchStaffGSMRateBeen;
import com.amg.exchange.online.bean.PlaceOrderCreationBean;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.registration.bean.CustomerPersonalInfoBean;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmployeeInfoView;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.CustomerMobileLogModel;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.remittance.model.AccountTypeFromView;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AdditionalDataDisplayView;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.AmtbCoupon;
import com.amg.exchange.remittance.model.AuthicationLimitCheckView;
import com.amg.exchange.remittance.model.AuthorizedLog;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BeneficaryMaster;
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
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.model.ShoppingCartDetails;
import com.amg.exchange.remittance.model.ViewAmtbCoupon;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.model.ViewHODirectEFT;
import com.amg.exchange.remittance.model.ViewHODirectInDirect;
import com.amg.exchange.remittance.model.ViewStatesForICASH;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.model.ViewSubAgent;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BankPrefix;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

/**
 * @author Chiranjeevi
 * 
 */
@Component("personalRemittanceBean")
@Scope("session")
public class PersonalRemittanceBean<T> implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(PersonalRemittanceBean.class);
	iCypherSecurity cypherSecurity = new CypherSecurityImpl();
	// Jasper Report generation
	private JasperPrint jasperPrint;

	// Autowired objects
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IBeneficaryCreation beneficaryCreation;
	@Autowired
	ServiceCodeMasterService serviceMasterService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	@Autowired
	IServiceGroupMasterService iServiceGroupMasterService;
	@Autowired
	IRemittanceModeService iRemitModeMaster;
	@Autowired
	DeliveryModeService iDeliveryModeMaster;
	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;
	@Autowired
	ApllicationMailer1 mailService;
	@Autowired
	IEmployeeService iEmployeeService;
	/*@Autowired
	ISpecialRateRequestService iSpecialRateRequest;*/
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	@Autowired
	ISourceOfIncomeService isourceOfIncome;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	@Autowired
	ILoginService<T> loginService;
	@Autowired
	IPaymentService ipaymentService;
	@Autowired
	ICustomerBankService icustomerBankService;
	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;
	@Autowired
	IGLTransactionForADocument iglTransactionForADocument;
	@Autowired
	ICompanyMasterservice iCompanyMasterservice;
	@Autowired
	ICashTransferLToLService cashTransferLToLService;
	@Autowired
	BeneficiaryEditBean beneficiaryEditBean;
	@Autowired
	ApplicationContext appContext;
	@Autowired
	LoginBean loginBean;
	
	@Autowired
	IPlaceAnOrderCreationService  iPlaceOnOrderCreationService;
	

	public PersonalRemittanceBean(){
		hideAllPanels();
		assignNullValues();
		setCardType(null);
		setSelectCardType(0);
		setSpecialApprovalRadio(0);
		setIdNumber(null);
		setBooRenderPlaceOrder(false);
		setBooRenderPlaceOrderByRemit(false);
		setRatePlaceOrderPk(null);
	}

	//Added by Rabil on 09/03/2016
	private BigDecimal approvalYear;
	private BigDecimal approvalNo;
	private BigDecimal equivalentRemitAmount;
	private String equivalentCurrency;
	private int specialApprovalRadio=0;
	private Boolean spotRateRender;
	private Boolean approvalRender;
	private BigDecimal countryId;
	private Boolean focus=true;
	private Boolean paymentmodeFocus = true;
	private Boolean appNoFocus = false;
	

	private Boolean renderCustomerSignatureCheck=false;
	
	//Added by Rabil on 03 April 2017 For AMTB Coupon
	
	private BigDecimal amtbCoupon;
	private Boolean amtbCouponRender=false;
	private BigDecimal amtbCouponAmount=BigDecimal.ZERO;
	private BigDecimal amtbCouponFinancialYear;


	// List to fetch Records
	// First Panel Lists
	private CopyOnWriteArrayList<ShoppingCartDataTableBean> lstselectedrecord = new CopyOnWriteArrayList<ShoppingCartDataTableBean>();
	//private List<CountryMasterDesc> lstCountry = new ArrayList<CountryMasterDesc>();
	//private List<CountryMasterDesc> nationalityList = new ArrayList<CountryMasterDesc>();
	private List<CustomerIdProof> customerDetailsList = new ArrayList<CustomerIdProof>();
	private List<ShoppingCartDataTableBean> shoppingcartDTList = new ArrayList<ShoppingCartDataTableBean>();
	private Map<BigDecimal, String> mapcomIdentityType = new HashMap<BigDecimal, String>();

	// Second Panel Lists
	private List<BeneficaryStatus> benificaryStatusList = new ArrayList<BeneficaryStatus>();
	private List<BeneficaryStatus> benificaryStatusName = new ArrayList<BeneficaryStatus>();
	private List<PersonalRemmitanceBeneficaryDataTable> coustomerBeneficaryDTList = new ArrayList<PersonalRemmitanceBeneficaryDataTable>();
	private List<PersonalRemmitanceBeneficaryDataTable> deleteBeneRelationList = new ArrayList<PersonalRemmitanceBeneficaryDataTable>();
	private CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> personalRemittCalFCAmountDTList = new CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable>();

	// Third Panel Lists
	private List<PopulateData> routingBankBranchlst = new ArrayList<PopulateData>();
	private List<PopulateData> routingbankvalues = new ArrayList<PopulateData>();
	private List<PopulateData> routingCountrylst = new ArrayList<PopulateData>();
	private List<BeneficaryMaster> beneficiaryMaster = new ArrayList<BeneficaryMaster>();
	private List<BeneficaryContact> beneficiaryTel = new ArrayList<BeneficaryContact>();
	private List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();
	private List<PopulateData> lstofRemittance = new ArrayList<PopulateData>();
	private List<PopulateData> lstofDelivery = new ArrayList<PopulateData>();
	//private List<PersonalRemittanceRoutingBankBranches> lstofRoutingBranches = new ArrayList<PersonalRemittanceRoutingBankBranches>();
	private CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> selectedSplDeal = new CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable>();

	//Added by Rabil

	List<PopulateData> serviceList = new ArrayList<PopulateData>();


	// Fourth Panel Lists
	private List<AddDynamicLebel> listDynamicLebel = new ArrayList<AddDynamicLebel>();
	private List<AddAdditionalBankData> listAdditionalBankDataTable = new ArrayList<AddAdditionalBankData>();
	private List<PopulateData> lstSwiftMasterBank1 = new ArrayList<PopulateData>();
	private List<PopulateData> lstSwiftMasterBank2 = new ArrayList<PopulateData>();
	private List<SourceOfIncomeDescription> lstSourceofIncome = new ArrayList<SourceOfIncomeDescription>();

	// Fifth Panel Lists
	private List<CustomerAlmTrasactionCheckProcedure> almcheckList = new ArrayList<CustomerAlmTrasactionCheckProcedure>();
	private List<BigDecimal> lstBeneAuthenticationPanel = new ArrayList<BigDecimal>();
	private List<AuthorizedLog> lstBeneAuthentication = new ArrayList<AuthorizedLog>();


	// Seventh Panel Lists
	private List<RemittanceReportBean> remittanceApplicationReportList = new CopyOnWriteArrayList<RemittanceReportBean>();
	private List<RemittanceReportBean> remittanceReceiptReportList = new CopyOnWriteArrayList<RemittanceReportBean>();
	private List<RemittanceReceiptSubreport> remittanceReceiptSubreportList;
	private List<BigDecimal> lstApplDocNumber = new ArrayList<BigDecimal>();
	// for last final save to remittance - list which to be true
	private CopyOnWriteArrayList<ShoppingCartDataTableBean> lstModifyRoundRecord = new CopyOnWriteArrayList<ShoppingCartDataTableBean>();
	private List<CollectionDetailView> collectionViewList = new CopyOnWriteArrayList<CollectionDetailView>();

	// Eight and Ninth Panel Lists
	private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();
	private List<ViewBankDetails> bankMasterList = new CopyOnWriteArrayList<ViewBankDetails>();
	private List<ViewBankDetails> chequebankMasterList = new ArrayList<ViewBankDetails>();
	private List<ViewBankDetails> localbankList = new ArrayList<ViewBankDetails>();
	private List<PaymentModeDesc> lstFetchAllPayMode = new ArrayList<PaymentModeDesc>();
	private List<CustomerBank> lstDebitCard = new ArrayList<CustomerBank>();
	private List<DebitAutendicationView> empllist = new ArrayList<DebitAutendicationView>();
	private List<DebitAutendicationView> emplAutenticationlist = new ArrayList<DebitAutendicationView>();
	private List<ViewBankDetails> localbankListForBankCode = new ArrayList<ViewBankDetails>();
	private List<FcSaleReport> fcSaleReportDTOList = null;

	private List<ViewSubAgent> lstIcashSubAgents = new ArrayList<ViewSubAgent>();
	private List<ViewHODirectEFT> lstIcashAgentsEFTDetails = new ArrayList<ViewHODirectEFT>();
	private List<ViewHODirectInDirect> lstIcashAgentsTTDetails = new ArrayList<ViewHODirectInDirect>();
	private List<ViewStatesForICASH> lstIcashState = new ArrayList<ViewStatesForICASH>();

    /** AMTB List */
	public List<ViewAmtbCoupon> lstVwAmtbCoupon = new ArrayList<ViewAmtbCoupon>();

	/** Added by Rabil on07/01/2016 **/

	// WinEPTS_Wrapper API = new WinEPTS_Wrapper();
	Receipt receipt =null;
	String knetTranId=null;
	private BigDecimal spotRateForDispay;
	private BigDecimal remittanceAmountForDisplay;
	private String swiftBic;

	//Added Ram Mohan for customer E-mail 29/02/2016
	private String customerEmail;
	private Boolean booReportDisplay;
	private String remarksForBeneDelete;

	
	public void appNoFocusVisible(){
		setAppNoFocus(true);
	}
	
	public void appNoFocusHide(){
		setAppNoFocus(false);
	}

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

	// getters and setters for Lists
	public CopyOnWriteArrayList<ShoppingCartDataTableBean> getLstselectedrecord() {
		return lstselectedrecord;
	}
	public void setLstselectedrecord(CopyOnWriteArrayList<ShoppingCartDataTableBean> lstselectedrecord) {
		this.lstselectedrecord = lstselectedrecord;
	}

	/*public List<CountryMasterDesc> getLstCountry() {
		return lstCountry;
	}
	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}*/

	public List<ViewSubAgent> getLstIcashSubAgents() {
		return lstIcashSubAgents;
	}
	public void setLstIcashSubAgents(List<ViewSubAgent> lstIcashSubAgents) {
		this.lstIcashSubAgents = lstIcashSubAgents;
	}

	public List<ViewStatesForICASH> getLstIcashState() {
		return lstIcashState;
	}
	public void setLstIcashState(List<ViewStatesForICASH> lstIcashState) {
		this.lstIcashState = lstIcashState;
	}

	public List<ViewHODirectEFT> getLstIcashAgentsEFTDetails() {
		return lstIcashAgentsEFTDetails;
	}
	public void setLstIcashAgentsEFTDetails(List<ViewHODirectEFT> lstIcashAgentsEFTDetails) {
		this.lstIcashAgentsEFTDetails = lstIcashAgentsEFTDetails;
	}

	public List<ViewHODirectInDirect> getLstIcashAgentsTTDetails() {
		return lstIcashAgentsTTDetails;
	}
	public void setLstIcashAgentsTTDetails(List<ViewHODirectInDirect> lstIcashAgentsTTDetails) {
		this.lstIcashAgentsTTDetails = lstIcashAgentsTTDetails;
	}

	/*public List<CountryMasterDesc> getNationalityList() {
		return nationalityList;
	}
	public void setNationalityList(List<CountryMasterDesc> nationalityList) {
		this.nationalityList = nationalityList;
	}*/	

	public List<CustomerIdProof> getCustomerDetailsList() {
		return customerDetailsList;
	}
	public void setCustomerDetailsList(List<CustomerIdProof> customerDetailsList) {
		this.customerDetailsList = customerDetailsList;
	}

	public List<ShoppingCartDataTableBean> getShoppingcartDTList() {
		return shoppingcartDTList;
	}
	public void setShoppingcartDTList(List<ShoppingCartDataTableBean> shoppingcartDTList) {
		this.shoppingcartDTList = shoppingcartDTList;
	}

	public Map<BigDecimal, String> getMapcomIdentityType() {
		return mapcomIdentityType;
	}
	public void setMapcomIdentityType(Map<BigDecimal, String> mapcomIdentityType) {
		this.mapcomIdentityType = mapcomIdentityType;
	}

	public List<BeneficaryStatus> getBenificaryStatusList() {
		return benificaryStatusList;
	}
	public void setBenificaryStatusList(List<BeneficaryStatus> benificaryStatusList) {
		this.benificaryStatusList = benificaryStatusList;
	}

	public List<BeneficaryStatus> getBenificaryStatusName() {
		return benificaryStatusName;
	}
	public void setBenificaryStatusName(List<BeneficaryStatus> benificaryStatusName) {
		this.benificaryStatusName = benificaryStatusName;
	}

	public List<PersonalRemmitanceBeneficaryDataTable> getCoustomerBeneficaryDTList() {
		return coustomerBeneficaryDTList;
	}

	public void setCoustomerBeneficaryDTList(List<PersonalRemmitanceBeneficaryDataTable> coustomerBeneficaryDTList) {
		this.coustomerBeneficaryDTList = coustomerBeneficaryDTList;
	}

	public List<PersonalRemmitanceBeneficaryDataTable> getDeleteBeneRelationList() {
		return deleteBeneRelationList;
	}
	public void setDeleteBeneRelationList(List<PersonalRemmitanceBeneficaryDataTable> deleteBeneRelationList) {
		this.deleteBeneRelationList = deleteBeneRelationList;
	}

	public CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> getPersonalRemittCalFCAmountDTList() {
		return personalRemittCalFCAmountDTList;
	}

	public void setPersonalRemittCalFCAmountDTList(CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> personalRemittCalFCAmountDTList) {
		this.personalRemittCalFCAmountDTList = personalRemittCalFCAmountDTList;
	}

	public List<PopulateData> getRoutingBankBranchlst() {
		return routingBankBranchlst;
	}
	public void setRoutingBankBranchlst(List<PopulateData> routingBankBranchlst) {
		this.routingBankBranchlst = routingBankBranchlst;
	}

	public List<PopulateData> getRoutingbankvalues() {
		return routingbankvalues;
	}
	public void setRoutingbankvalues(List<PopulateData> routingbankvalues) {
		this.routingbankvalues = routingbankvalues;
	}

	public List<PopulateData> getRoutingCountrylst() {
		return routingCountrylst;
	}
	public void setRoutingCountrylst(List<PopulateData> routingCountrylst) {
		this.routingCountrylst = routingCountrylst;
	}

	public List<BeneficaryMaster> getBeneficiaryMaster() {
		return beneficiaryMaster;
	}
	public void setBeneficiaryMaster(List<BeneficaryMaster> beneficiaryMaster) {
		this.beneficiaryMaster = beneficiaryMaster;
	}

	public List<BeneficaryContact> getBeneficiaryTel() {
		return beneficiaryTel;
	}
	public void setBeneficiaryTel(List<BeneficaryContact> beneficiaryTel) {
		this.beneficiaryTel = beneficiaryTel;
	}

	public List<PopulateData> getLstofCurrency() {
		return lstofCurrency;
	}
	public void setLstofCurrency(List<PopulateData> lstofCurrency) {
		this.lstofCurrency = lstofCurrency;
	}

	public List<PopulateData> getLstofRemittance() {
		return lstofRemittance;
	}
	public void setLstofRemittance(List<PopulateData> lstofRemittance) {
		this.lstofRemittance = lstofRemittance;
	}

	public List<PopulateData> getLstofDelivery() {
		return lstofDelivery;
	}
	public void setLstofDelivery(List<PopulateData> lstofDelivery) {
		this.lstofDelivery = lstofDelivery;
	}

	/*public List<PersonalRemittanceRoutingBankBranches> getLstofRoutingBranches() {
		return lstofRoutingBranches;
	}
	public void setLstofRoutingBranches(List<PersonalRemittanceRoutingBankBranches> lstofRoutingBranches) {
		this.lstofRoutingBranches = lstofRoutingBranches;
	}*/

	public CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> getSelectedSplDeal() {
		return selectedSplDeal;
	}
	public void setSelectedSplDeal(CopyOnWriteArrayList<PersonalRemittanceCalFCAmountDataTable> selectedSplDeal) {
		this.selectedSplDeal = selectedSplDeal;
	}

	public List<AddDynamicLebel> getListDynamicLebel() {
		return listDynamicLebel;
	}
	public void setListDynamicLebel(List<AddDynamicLebel> listDynamicLebel) {
		this.listDynamicLebel = listDynamicLebel;
	}

	public List<AddAdditionalBankData> getListAdditionalBankDataTable() {
		return listAdditionalBankDataTable;
	}
	public void setListAdditionalBankDataTable(List<AddAdditionalBankData> listAdditionalBankDataTable) {
		this.listAdditionalBankDataTable = listAdditionalBankDataTable;
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

	public List<SourceOfIncomeDescription> getLstSourceofIncome() {
		return lstSourceofIncome;
	}
	public void setLstSourceofIncome(List<SourceOfIncomeDescription> lstSourceofIncome) {
		this.lstSourceofIncome = lstSourceofIncome;
	}

	public List<CustomerAlmTrasactionCheckProcedure> getAlmcheckList() {
		return almcheckList;
	}
	public void setAlmcheckList(List<CustomerAlmTrasactionCheckProcedure> almcheckList) {
		this.almcheckList = almcheckList;
	}

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

	public List<RemittanceReportBean> getRemittanceApplicationReportList() {
		return remittanceApplicationReportList;
	}
	public void setRemittanceApplicationReportList(List<RemittanceReportBean> remittanceApplicationReportList) {
		this.remittanceApplicationReportList = remittanceApplicationReportList;
	}

	public List<RemittanceReportBean> getRemittanceReceiptReportList() {
		return remittanceReceiptReportList;
	}
	public void setRemittanceReceiptReportList(List<RemittanceReportBean> remittanceReceiptReportList) {
		this.remittanceReceiptReportList = remittanceReceiptReportList;
	}

	public List<RemittanceReceiptSubreport> getRemittanceReceiptSubreportList() {
		return remittanceReceiptSubreportList;
	}
	public void setRemittanceReceiptSubreportList(List<RemittanceReceiptSubreport> remittanceReceiptSubreportList) {
		this.remittanceReceiptSubreportList = remittanceReceiptSubreportList;
	}

	public List<BigDecimal> getLstApplDocNumber() {
		return lstApplDocNumber;
	}
	public void setLstApplDocNumber(List<BigDecimal> lstApplDocNumber) {
		this.lstApplDocNumber = lstApplDocNumber;
	}

	public CopyOnWriteArrayList<ShoppingCartDataTableBean> getLstModifyRoundRecord() {
		return lstModifyRoundRecord;
	}
	public void setLstModifyRoundRecord(CopyOnWriteArrayList<ShoppingCartDataTableBean> lstModifyRoundRecord) {
		this.lstModifyRoundRecord = lstModifyRoundRecord;
	}

	public List<CollectionDetailView> getCollectionViewList() {
		return collectionViewList;
	}
	public void setCollectionViewList(List<CollectionDetailView> collectionViewList) {
		this.collectionViewList = collectionViewList;
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

	public CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> getColdatatablevalues() {
		return coldatatablevalues;
	}
	public void setColdatatablevalues(CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
		this.coldatatablevalues = coldatatablevalues;
	}

	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}
	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public List<ViewBankDetails> getLocalbankList() {
		return localbankList;
	}
	public void setLocalbankList(List<ViewBankDetails> localbankList) {
		this.localbankList = localbankList;
	}

	/*public List<PaymentModeDesc> getLstFetchAllPayMode() {
		return ipaymentService.fetchPaymodeDesc(new BigDecimal(sessionStateManage
				.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),Constants.Yes);
	}
	public void setLstFetchAllPayMode(List<PaymentModeDesc> lstFetchAllPayMode) {
		this.lstFetchAllPayMode = lstFetchAllPayMode;
	}*/

	public List<PaymentModeDesc> getLstFetchAllPayMode() {
		return lstFetchAllPayMode;
	}
	public void setLstFetchAllPayMode(List<PaymentModeDesc> lstFetchAllPayMode) {
		this.lstFetchAllPayMode = lstFetchAllPayMode;
	}

	public List<CustomerBank> getLstDebitCard() {
		return lstDebitCard;
	}
	public void setLstDebitCard(List<CustomerBank> lstDebitCard) {
		this.lstDebitCard = lstDebitCard;
	}

	public List<DebitAutendicationView> getEmpllist() {
		return empllist;
	}
	public void setEmpllist(List<DebitAutendicationView> empllist) {
		this.empllist = empllist;
	}

	public List<DebitAutendicationView> getEmplAutenticationlist() {
		return emplAutenticationlist;
	}
	public void setEmplAutenticationlist(List<DebitAutendicationView> emplAutenticationlist) {
		this.emplAutenticationlist = emplAutenticationlist;
	}

	public List<ViewBankDetails> getLocalbankListForBankCode() {
		return localbankListForBankCode;
	}
	public void setLocalbankListForBankCode(List<ViewBankDetails> localbankListForBankCode) {
		this.localbankListForBankCode = localbankListForBankCode;
	}	

	public List<ViewBankDetails> getChequebankMasterList() {
		return chequebankMasterList;
	}
	public void setChequebankMasterList(List<ViewBankDetails> chequebankMasterList) {
		this.chequebankMasterList = chequebankMasterList;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// methods
	public void personalRemittancePageNavigation() {
		
		if(sessionStateManage.getUserName() != null){
			String userNameChk = sessionStateManage.getUserName().substring(0, 4);
			if(userNameChk.equalsIgnoreCase(Constants.ARMS)){
				log.error("Throw Error Message - USER NAME SHOULD BE EMPLOYEE LOGIN, NOT WITH ARMS");
				System.out.println("Throw Error Message - USER NAME SHOULD BE EMPLOYEE LOGIN, NOT WITH ARMS");
				//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "USER NAME SHOULD BE EMPLOYEE LOGIN, NOT WITH ARMS", ""));
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("ARMS Users cannot do Personal Remittances", null));
				context.getExternalContext().getFlash().setKeepMessages(true);
				return;
			}
		}
		
		setBooRenderCorporateBack(false);
		hideAllPanels();
		assignNullValues();
		setCardType(null);
		setSelectCardType(0);
		setIdNumber(null);
		checkProExp = false;
		saveCount = 0;
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "PersonalRemittance.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname.trim().concat(" ");
	}

	@PostConstruct
	public void loadIdType() {
		mapcomIdentityType = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), "I","Identity Type");
	}

	public void showCardTypePanel() throws Exception {
		setShowPlaceOrderScreen(true);
		int typecard = getSelectCardType();
		if (typecard == 2) {
			setMobileNoFetch(null);
			setBooCheckMobile(false);
			setBooCheckMobileInput(false);
			setBooRenderBenificaryFirstPanel(true);
			setBooRenderOldSmartCardPanel(true);
			BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVILID,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
			if(idtypeCivilIdnew !=null){
				setSelectCard(idtypeCivilIdnew);
			}
		} else if (typecard == 1) {
			fetchSmartCardIdNumber();
			if (getIdNumber() != null && getSelectCard() != null) {
				if(coustomerBeneficaryDTList != null && !coustomerBeneficaryDTList.isEmpty()){
					coustomerBeneficaryDTList.clear();
				}
				goFromOldSmartCardpanel();
				setBooRenderBenificaryFirstPanel(false);
				setBooRenderOldSmartCardPanel(false);
			} else {
				setSelectCardType(0);
				setBooRenderOldSmartCardPanel(false);
				RequestContext.getCurrentInstance().execute("dldInserCard.show();");
			}
		}
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

	public String smartCardDisplay(String host, String prdPort,String requestType, String env) throws ParseException {
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
		HttpURLConnection testyc = null;
		HttpsURLConnection prdyc = null;
		try {
			URL knetRequest = new URL(urlBuffer.toString());

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
		}finally{
			if(testyc!=null){
				testyc.disconnect();
			}
			if(prdyc!=null){
				testyc.disconnect();
			}
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
					setSelectCard(generalService.getComponentId(Constants.CIVILID,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId());
				}
			}
		} else {
			setIdNumber(null);
			setSelectCard(null);
			RequestContext.getCurrentInstance().execute("dldInserCard.show();");
		}
		return sb.toString();
	}

	public void personalRemittanceBackFromBene(PersonalRemmitanceBeneficaryDataTable personalRBDataTable){
		if(personalRBDataTable != null){
			if(personalRBDataTable != null && !personalRBDataTable.getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
				gotoRemittanceservice(personalRBDataTable);
			}else{
				backToBeneficiaryRecordsByCountry();
			}
		}else{
			try {
				goFromOldSmartCardpanel();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


	/*public void backtoFCSale() {
		log.info("Entering into backtoFCSale method ");
		setVisible(false);
		HttpSession session = sessionStateManage.getSession();
		String error = (String) session.getAttribute("error");

		if (error != null && error.length() != 0) {

			session.removeAttribute("error");
			log.info("############ backtoFCSale #####################");

			try {
				if(coustomerBeneficaryDTList != null && !coustomerBeneficaryDTList.isEmpty()){
					coustomerBeneficaryDTList.clear();
				}
				goFromOldSmartCardpanel();
			} catch (Exception e) {
				log.info("Exception occured " + e);
			}
			setVisible(true);
			RequestContext.getCurrentInstance().execute("PF('error').show();");
			setErrmsg(error);
			return;
		}

		String backToRemitance = (String) session.getAttribute("backToFC");
		String backToRemitanceFirstPanel = (String) session.getAttribute("backToRemitanceFirstPanel");
		if (backToRemitance != null && backToRemitance.equalsIgnoreCase(Constants.YES)) {
			log.info("Back to nextrenderingLastPanel ");
			getShoppingCartDetails(sessionStateManage.getCustomerId());
			lstselectedrecord = new CopyOnWriteArrayList<ShoppingCartDataTableBean>();
			nextrenderingLastPanel();
			session.setAttribute("backToFC", "no");
		} else if (backToRemitanceFirstPanel != null && backToRemitanceFirstPanel.equalsIgnoreCase(Constants.YES)) {
			try {
				log.info("*************** backToRemitanceFirstPanel *****************");
				if(coustomerBeneficaryDTList != null && !coustomerBeneficaryDTList.isEmpty()){
					coustomerBeneficaryDTList.clear();
				}
				goFromOldSmartCardpanel();
			} catch (Exception e) {
				log.info("Exception occured"+e);
			}
			session.setAttribute("backToRemitanceFirstPanel", "no");
		} else {
			log.info("backtoFCSale method");
		}
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromBeneficary") != null) {
			String fromBeneficary = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromBeneficary").toString();

			if (fromBeneficary != null && fromBeneficary.equalsIgnoreCase(Constants.YES)) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("fromBeneficary");
				log.info("#################################");
				try {
					if(coustomerBeneficaryDTList != null && !coustomerBeneficaryDTList.isEmpty()){
						coustomerBeneficaryDTList.clear();
					}
					goFromOldSmartCardpanel();
				} catch (Exception e) {
					log.info("Exception occured " + e);
				}
			}
		}


		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromBeneficaryTransactions") != null) {
			String fromBeneficary = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromBeneficaryTransactions").toString();

			if (fromBeneficary != null && fromBeneficary.equalsIgnoreCase(Constants.YES)) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("fromBeneficaryTransactions");
				log.info("#################################");
				try {
					if(coustomerBeneficaryDTList != null && !coustomerBeneficaryDTList.isEmpty()){
						coustomerBeneficaryDTList.clear();
					}
					goFromOldSmartCardpanel();
				} catch (Exception e) {
					log.info("Exception occured " + e);
				}
			}
		}

		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromBeneficarySave") != null) {
			String fromBeneficarySave = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fromBeneficarySave").toString();
			if (fromBeneficarySave != null && fromBeneficarySave.equalsIgnoreCase(Constants.YES)) {
				PersonalRemmitanceBeneficaryDataTable datatabledetails =(PersonalRemmitanceBeneficaryDataTable) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dataTable");
				log.info("####################Data table values###########");
				log.info(datatabledetails);
				log.info("####################Data table values###########");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("fromBeneficarySave");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("dataTable");
				try {
					if(datatabledetails != null && !datatabledetails.getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
						gotoRemittanceservice(datatabledetails);
					}else{
						backToBeneficiaryRecordsByCountry();
					}
				} catch (Exception e) {
					log.info("Exception occured " + e);
				}
			}
		}

		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("exitBeneficary") != null) {
			String exitBeneficary = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("exitBeneficary").toString();

			if (exitBeneficary != null && exitBeneficary.equalsIgnoreCase(Constants.YES)) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("exitBeneficary");
				log.info("exit from beneficary creation ");
				try {
					personalRemittancePageNavigation();
				} catch (Exception e) {
					log.info("Exception occured " + e);
				}
			}
		}
	}*/

	public void resetFilters(String clearDataTable) {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(clearDataTable);
		if (dataTable != null) {
			dataTable.reset();
		}
	}

	// on click of go button
	public void onClickGoButton(){
		if(customerDetailsList != null || !customerDetailsList.isEmpty()){
			customerDetailsList.clear();
		}

		if(isBooCheckMobile()){
			if(getMobileNoFetch() != null){
				List<Customer> customerList = icustomerRegistrationService.getCustomerData(sessionStateManage.getCountryId(), getMobileNoFetch());
				if(customerList.size() == 1){
					// fetch customer records
					Customer customer = customerList.get(0);
					setCustomerNo(customer.getCustomerId());

					List<CustomerIdProof> lstCustomerIdProof = icustomerRegistrationService.getCustomerIdProofList(getCustomerNo());
					if(lstCustomerIdProof.size() == 1){
						CustomerIdProof customerIdProof = lstCustomerIdProof.get(0);
						customerDetailsList.add(customerIdProof);
						fetchCustomerBeneficiaryDetails(customerIdProof);
					}else if(lstCustomerIdProof.size() > 1){
						// multiple 'Y' is-active Condition
						setExceptionMessage("Multiple Active Records Avaialble in Customer Id Proof for Same Customer");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}else{
						setExceptionMessage("Customer Id Proof is In-Active");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}

				}else if(customerList.size() > 1){
					// multiple records available
					setExceptionMessage("Multiple Customer Details Available for Same Number");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}else{
					setExceptionMessage(WarningHandler.showWarningMessage("lbl.mobilenodoesnotexistpleaseselectidtypeandidnumber", sessionStateManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}else{
				// please enter mobile number correctly
				setExceptionMessage("Please Enter Mobile Number");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}else{
			try {
				goFromOldSmartCardpanel();
				setBeneficiaryCountryId(getNationality());
				populateCustomerDetailsFromBeneRelation();
			} catch (ParseException e) {
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}
	}


	// first method after go clicked to fetch all customer details
	public void goFromOldSmartCardpanel() throws ParseException {

		log.info("Entering into goFromOldSmartCardpanel method");

		if(getIdNumber() != null && !getIdNumber().equalsIgnoreCase("")){
			// clearing data table
			resetFilters("form1:dataTable");

			if(coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()){
				coustomerBeneficaryDTList.clear();
			}

			if(getSelectCard() != null){

				//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber().toUpperCase(), getSelectCard());

				customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber().toUpperCase(), getSelectCard());

				if(customerDetailsList.size() != 0){
					if(getBooRenderCorporateBack()){
						CustomerIdProof customerDetails = customerDetailsList.get(0);
						getCustomerDetails();
						populateBeneFiciaryCountry();
						backFromBenificaryStatusPanel();
						clearData();
					}else{
						CustomerIdProof customerDetails = customerDetailsList.get(0);
						fetchCustomerBeneficiaryDetails(customerDetails);
						//List<Customer> customer = iPersonalRemittanceService.getBenCountryList(getCustomerrefno());
						setBeneficiaryCountryId(getNationality());
						populateCustomerDetailsFromBeneRelation();
					}
				}else{
					// comparing with civil id
					BigDecimal identityTpeIds = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();

					if(getSelectCard().compareTo(identityTpeIds)!=0){
						//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), identityTpeIds);
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), identityTpeIds);
						if(customerDetailsList.size() != 0){
							CustomerIdProof customerDetails = customerDetailsList.get(0);
							fetchCustomerBeneficiaryDetails(customerDetails);
							setBeneficiaryCountryId(getNationality());
							populateCustomerDetailsFromBeneRelation();
						}else{
							if(getBooRenderCorporateBack()){
								BigDecimal idNumber=generalService.getComponentId(Constants.COMPANY_REGISTRATION_DOC, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
								//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), idNumber);
								customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idNumber);
								if(customerDetailsList.size() != 0){
									CustomerIdProof customerDetails = customerDetailsList.get(0);
									getCustomerDetails();
									populateBeneFiciaryCountry();
									backFromBenificaryStatusPanel();
									clearData();
								}else{
									// failed all conditions
									setCardType(null);
									setBooRenderBenificaryFirstPanel(true);
									RequestContext.getCurrentInstance().execute("idNotFound.show();");
								}
							}else{
								// comparing with civil id new
								BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
								//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), idtypeCivilIdnew);
								customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idtypeCivilIdnew);
								if(customerDetailsList.size() != 0){
									CustomerIdProof customerDetails = customerDetailsList.get(0);
									fetchCustomerBeneficiaryDetails(customerDetails);
									setBeneficiaryCountryId(getNationality());
									populateCustomerDetailsFromBeneRelation();
								}else{
									// failed all conditions
									setCardType(null);
									//setIdNumber(null);
									setBooRenderBenificaryFirstPanel(true);
									RequestContext.getCurrentInstance().execute("idNotFound.show();");
								}
							}
						}
					}else{
						// comparing with civil id new
						BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
						//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), idtypeCivilIdnew);
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idtypeCivilIdnew);
						if(customerDetailsList.size() != 0){
							CustomerIdProof customerDetails = customerDetailsList.get(0);
							fetchCustomerBeneficiaryDetails(customerDetails);
							setBeneficiaryCountryId(getNationality());
							populateCustomerDetailsFromBeneRelation();
						}else{
							// failed all conditions
							setCardType(null);
							//setIdNumber(null);
							setBooRenderBenificaryFirstPanel(true);
							RequestContext.getCurrentInstance().execute("idNotFound.show();");
						}

					}
				}

			}

		}else{
			RequestContext.getCurrentInstance().execute("idNumbernotenter.show();");
		}

		log.info("Exit into goFromOldSmartCardpanel method");
	}

	// checking mobile enabale condition
	public void enableMobileNo(){
		if(isBooCheckMobile()){
			setBooCheckMobileInput(true);
		}else{
			setBooCheckMobileInput(false);
		}

	}

	// calling remit amount page
	public void fetchCustomerBeneficiaryDetails(CustomerIdProof fetchedcustomerDetails){

		try {

			//Added by Rabil.
			String userType=null;
			if((sessionStateManage.getBranchId()!=null || sessionStateManage.getCustomerType().equals("E")) && sessionStateManage.getRoleId().equals("1")){
				userType ="BRANCH"; 
			}else if(sessionStateManage.getBranchId()!=null  &&  sessionStateManage.getUserType().equalsIgnoreCase("K")){
				userType ="KIOSK";
			}else
			{
				//userType ="ONLINE";
			}

			CustomerIdProof customerDetails = fetchedcustomerDetails;

			if(customerDetails.getFsCustomer() != null){
				setCustomerNo(customerDetails.getFsCustomer().getCustomerId());

				//v$session update
				loginService.killUserSession(sessionStateManage.getUserName()+"-"+customerDetails.getFsCustomer().getCustomerId()+"-R");
			}
			if(customerDetails.getIdentityExpiryDate() != null){
				setCustomerExpDate(customerDetails.getIdentityExpiryDate());
			}
			if(getCustomerExpDate() != null){
				setCustomerExpireDateMsg(new SimpleDateFormat("dd/MM/yyyy").format(getCustomerExpDate()));
			}

			log.info("getCustomerNo() :"+getCustomerNo()+"\t sessionStateManage.getCountryId():"+sessionStateManage.getCountryId()+"\t sessionStateManage.getUserName() :"+sessionStateManage.getUserName());
			HashMap<String,String> customerValiMessage = iPersonalRemittanceService.getValidateCustomerProcedure(sessionStateManage.getCountryId(), getCustomerNo(),sessionStateManage.getUserName(),userType);
			log.info("customerValiMessage :"+customerValiMessage);
			log.info("INDICATOR===="+customerValiMessage.get( "INDICATOR"));
			//customerValiMessage.put("ERROR_MESSAGE", null); //for Testing purpose
			if(customerValiMessage.get("ERROR_MESSAGE")!=null){
				setExceptionMessage(customerValiMessage.get( "ERROR_MESSAGE"));
				RequestContext.getCurrentInstance().execute("expiredCustomer.show();");
				return;
			}else if(customerValiMessage!=null&&customerValiMessage.get( "INDICATOR")!=null ){
				setExceptionMessage(customerValiMessage.get( "ERROR_MESSAGE"));
				setCardType(null);
				if(customerValiMessage.get("INDICATOR").equalsIgnoreCase( Constants.Yes)){
					RequestContext.getCurrentInstance().execute("customerregproceed.show();");
				}
			}else{

				// clearing bene country list and country id
				if(allBeneCountryList != null || !allBeneCountryList.isEmpty()){
					allBeneCountryList.clear();
				}
				setBeneficiaryCountryId(null);

				// customer details
				getCustomerDetails();

				if (getCustomerIsActive() != null && getCustomerIsActive().equalsIgnoreCase(Constants.Yes)) {
					
					// 18 years condition check
					if(getDateOfBrith() != null){
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(new Date());
						cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
						Date today18 = cal1.getTime();
						setBirthdate(formatter.format(getDateOfBrith()));
						
						if (getBirthdate() != null && !formatter.parse(getBirthdate()).before(today18)) {
							RequestContext.getCurrentInstance().execute("dobcheck.show();");
							return;
						}else{
							setBooRenderOverseaCharges(true);
							if(getShowPlaceOrderScreen())
							{
								HashMap<String, String> rtnValues = checkPlaceAnOrderExist();
								
								String placeOrderExist = rtnValues.get("PLACEORDER_EXIST");
								
								if(placeOrderExist.equalsIgnoreCase(Constants.Yes))
								{
									
									//RequestContext.getCurrentInstance().execute("placeOrderExist.show();");
									//FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/PersonalRemitOnlineServicePanel.xhtml");
									return;
								}
							}
							populateBeneFiciaryCountry();
							//rendering beneficiary details and customer details
							backFromBenificaryStatusPanel();
							clearData();
						}
					}else{
						boolean civilIdCheck = false;
						BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
						if(idtypeCivilId !=null && idtypeCivilId.compareTo(getSelectCard())==0){
							civilIdCheck = true;
						}else{
							BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
							if(idtypeCivilIdnew !=null && idtypeCivilIdnew.compareTo(getSelectCard())==0){
								civilIdCheck = true;
							}else{
								civilIdCheck = false;
							}
						}
						
						if(civilIdCheck){
							String id = getIdNumber();
							String dob = null;
							if (id.charAt(0) == '2') {
								dob = id.substring(5, 7) + "/" + id.substring(3, 5) + "/19" + id.substring(1, 3);
							} else {
								dob = id.substring(5, 7) + "/" + id.substring(3, 5) + "/20" + id.substring(1, 3);
							}
							SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
							Date date = formatter.parse(dob);
							setBirthdate(formatter.format(date));
							
							Calendar cal1 = Calendar.getInstance();
							cal1.setTime(new Date());
							cal1.add(Calendar.YEAR, -new Integer(Constants.AGE_LIMIT));
							Date today18 = cal1.getTime();
							
							if (getBirthdate() != null && !formatter.parse(getBirthdate()).before(today18)) {
								RequestContext.getCurrentInstance().execute("dobcheck.show();");
								return;
							}else{
								setBooRenderOverseaCharges(true);
								if(getShowPlaceOrderScreen())
								{
									HashMap<String, String> rtnValues = checkPlaceAnOrderExist();
									
									String placeOrderExist = rtnValues.get("PLACEORDER_EXIST");
									
									if(placeOrderExist.equalsIgnoreCase(Constants.Yes))
									{
										
										//RequestContext.getCurrentInstance().execute("placeOrderExist.show();");
										//FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/PersonalRemitOnlineServicePanel.xhtml");
										return;
									}
								}
								populateBeneFiciaryCountry();
								//rendering beneficiary details and customer details
								backFromBenificaryStatusPanel();
								clearData();
							}
						}else{
							setBooRenderOverseaCharges(true);
							if(getShowPlaceOrderScreen())
							{
								HashMap<String, String> rtnValues = checkPlaceAnOrderExist();
								
								String placeOrderExist = rtnValues.get("PLACEORDER_EXIST");
								
								if(placeOrderExist.equalsIgnoreCase(Constants.Yes))
								{
									
									//RequestContext.getCurrentInstance().execute("placeOrderExist.show();");
									//FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/PersonalRemitOnlineServicePanel.xhtml");
									return;
								}
							}
							populateBeneFiciaryCountry();
							//rendering beneficiary details and customer details
							backFromBenificaryStatusPanel();
							clearData();
						}
					}

				}else{
					setCardType(null);
					setBooRenderBenificaryFirstPanel(true);
					RequestContext.getCurrentInstance().execute("activatecustomer.show();");
					return;
				}
			}

		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;
		}

	}

	// back button calling - remit amount page to beneficiary data table list
	/*public void backToBeneficiaryRecordsByCountry(){
		setFocus(true);
		if(getBeneficiaryCountryId()!=null){

			if(allBeneCountryList != null || !allBeneCountryList.isEmpty()){
				allBeneCountryList.clear();
			}

			if(coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()){
				coustomerBeneficaryDTList.clear();
			}

			List<PopulateData> countryList = iPersonalRemittanceService.getBeneficaryList(getCustomerNo());

			if(countryList != null && countryList.size() != 0){
				allBeneCountryList.addAll(countryList);
			}

			populateCustomerDetailsFromBeneRelation();
		}else{
			populateBeneFiciaryCountry();
		}
		//populateBeneFiciaryCountry();
		backFromBenificaryStatusPanel();
	}*/
	
	// back button calling - remit amount page to beneficiary data table list
	public void backToBeneficiaryRecordsByCountry(){
		setFocus(true);
        setAmtbCoupon(null);
		if(isBooRenderPlaceOrder()){
			BranchStaffGSMRateBeen branchStaffGSMRateBeen = (BranchStaffGSMRateBeen) appContext.getBean("branchStaffGSMRateBean");
			branchStaffGSMRateBeen.pageNavigationBranchStaff();
			setBooRenderPlaceOrder(false);
			setBooRenderPlaceOrderByRemit(false);
			setRatePlaceOrderPk(null);
		}else if(isBooRenderPlaceOrderByRemit()){
			BranchStaffGSMRateBeen branchStaffGSMRateBeen = (BranchStaffGSMRateBeen) appContext.getBean("branchStaffGSMRateBean");
			branchStaffGSMRateBeen.pageNavigationFromPersonalRemitt(getCustomerNo(),getIdNumber(),getSelectCard());
			setBooRenderPlaceOrder(false);
			setBooRenderPlaceOrderByRemit(false);
			setRatePlaceOrderPk(null);
		}else{
			setBooRenderPlaceOrder(false);
			setBooRenderPlaceOrderByRemit(false);
			setRatePlaceOrderPk(null);
			if(getBeneficiaryCountryId()!=null){

				if(allBeneCountryList != null || !allBeneCountryList.isEmpty()){
					allBeneCountryList.clear();
				}

				if(coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()){
					coustomerBeneficaryDTList.clear();
				}

				List<PopulateData> countryList = iPersonalRemittanceService.getBeneficaryList(getCustomerNo());

				if(countryList != null && countryList.size() != 0){
					allBeneCountryList.addAll(countryList);
				}

				populateCustomerDetailsFromBeneRelation();
			}else{
				populateBeneFiciaryCountry();
			}
			//populateBeneFiciaryCountry();
			backFromBenificaryStatusPanel();
		}

	}

	public void redirectToCustomerPage() throws DOMException, ParseException, ParserConfigurationException, SAXException, IOException{
		CustomerPersonalInfoBean customerPesonel=	(CustomerPersonalInfoBean)appContext.getBean("customerPersonalInfo");
		customerPesonel.resetValues();

		customerPesonel.setSelectType(Constants.MANUAL);
		customerPesonel.setBooManual(true);
		customerPesonel.setBooIdType(true);
		if(getSelectCard() != null){
			customerPesonel.setSelectedIdType(getSelectCard().toString());
		}
		if(getIdNumber() != null){
			customerPesonel.setIdNumber(getIdNumber().trim());
		}
		//customerPesonel.appearCarddetail() ;
		if(getSelectCard() != null){
			customerPesonel.setIdType(getSelectCard().toString());
		}
		customerPesonel.setMobileNoFetch(null);
		customerPesonel.checkingIdWithDBForProcessing();

		try {

			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/customermanualinfo.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  CutomerRegistration");
		}
	}

	public void redirectToCustomerFirstPage() throws DOMException, ParseException, ParserConfigurationException, SAXException, IOException{
		CustomerPersonalInfoBean customerPesonel=	(CustomerPersonalInfoBean)appContext.getBean("customerPersonalInfo");
		customerPesonel.resetValues();

		customerPesonel.setSelectType(Constants.MANUAL);
		customerPesonel.setBooManual(true);
		customerPesonel.setBooIdType(true);
		if(getSelectCard() != null){
			customerPesonel.setSelectedIdType(getSelectCard().toString());
		}
		if(getIdNumber() != null){
			customerPesonel.setIdNumber(getIdNumber().trim());
		}
		//customerPesonel.appearCarddetail() ;
		if(getSelectCard() != null){
			customerPesonel.setIdType(getSelectCard().toString());
		}
		customerPesonel.setMobileNoFetch(null);
		customerPesonel.checkingIdWithDBForProcessing();

		try {

			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/customerregistrationmain.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  CutomerRegistration");
		}

	}





	public void populateCustomerDetails() {

		BigDecimal cardlength = BigDecimal.ZERO;

		localbankListForBankCode.clear();

		lstDebitCard.clear();
		setColAuthorizedby(null);
		setColCardNo(null);
		setColCardNoLength(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		setColApprovalNo(null);
		if (getColBankCode() != null) {
			localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getColBankCode());

			List<CustomerBank> localBankListinCollection = icustomerBankService.fetchcustomerBanksDetails(getCustomerNo(), getColBankCode());

			if(localbankListForBankCode.size() != 0){
				ViewBankDetails bankLength = localbankListForBankCode.get(0);
				cardlength = bankLength.getDebitCardLength();
				System.out.println("cardlength :" + cardlength);
			}

			if (localBankListinCollection.size() != 0) {
				if (cardlength.compareTo(BigDecimal.ZERO) != 0) {
					setColCardNoLength(cardlength);
					if (localBankListinCollection.size() == 1) {
						for (CustomerBank customerBank : localBankListinCollection) {
							if (customerBank.getBankCode().equals(getColBankCode())) {
								setPopulatedDebitCardNumber(new BigDecimal(encryptionDescriptionService.getDECrypted(customerBank.getDebitCardName(),customerBank.getDebitCard())));
								setColCardNo(new BigDecimal(encryptionDescriptionService.getDECrypted(customerBank.getDebitCardName(),customerBank.getDebitCard())));
								setColNameofCard(customerBank.getDebitCardName());
								if (customerBank.getAuthorizedBy() != null) {
									//List<Employee> localEmpllist = generalService.getEmployeelist(sessionStateManage.getCountryId(),new BigDecimal(sessionStateManage.getBranchId()),new BigDecimal(sessionStateManage.getRoleId()));

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
								lstofDebitCard.setDebitCard(encryptionDescriptionService.getDECrypted(lstDebitcrd.getDebitCardName(),lstDebitcrd.getDebitCard()));
								/**Added by Rabil on 13/01/2016 **/	
								lstofDebitCard.setDebitCardName(lstDebitcrd.getDebitCardName());
								/**End by Rabil on 13/01/2016 **/	
								lstDebitCard.add(lstofDebitCard);
							}
						}
					}
				} else {
					RequestContext.getCurrentInstance().execute("bankDebitCardLenErr.show();");
				}
			} else {

				System.out.println("cardlength :" + cardlength);

				if (cardlength != null) {

					if (cardlength.compareTo(BigDecimal.ZERO) != 0) {
						setColCardNoLength(cardlength);
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

		if(getColNameofCard() != null){
			String errMsg = validateDebitCard();

			if(errMsg != null){
				setExceptionMessage(errMsg);
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}

	}

	//03/03/2016 ENHANCEMENT

	private List<PopulateData>  allBeneCountryList=new ArrayList<PopulateData>();
	private BigDecimal beneficiaryCountryId;
	private Boolean renderBeneCountry=false;
	public Boolean getRenderBeneCountry() {
		return renderBeneCountry;
	}
	public void setRenderBeneCountry(Boolean renderBeneCountry) {
		this.renderBeneCountry = renderBeneCountry;
	}

	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}
	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}

	public List<PopulateData>  getAllBeneCountryList() {
		return allBeneCountryList;
	}
	public void setAllBeneCountryList(List<PopulateData>  allBeneCountryList) {
		this.allBeneCountryList = allBeneCountryList;
	}

	public void populateBeneFiciaryCountry(){
		try{
			if(allBeneCountryList != null || !allBeneCountryList.isEmpty()){
				allBeneCountryList.clear();
			}

			if(coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()){
				coustomerBeneficaryDTList.clear();
			}

			//setBeneficiaryCountryId(null);
			//backFromBenificaryStatusPanel();
			List<PopulateData> countryList = iPersonalRemittanceService.getBeneficaryList(getCustomerNo());

			if(countryList != null && countryList.size() > 1){
				setBeneficiaryCountryId(null);
				allBeneCountryList.addAll(countryList);
			}else if(countryList != null && countryList.size() == 1) {
				allBeneCountryList.addAll(countryList);
				PopulateData populateData=countryList.get(0);
				setBeneficiaryCountryId(populateData.getPopulateId());
				populateCustomerDetailsFromBeneRelation();
			}

		}catch (Exception e) {
			setProcedureError(e.getMessage());
			RequestContext.getCurrentInstance().execute("functionErr.show();");
		}
	}

	//END  ENHANCEMENT 03/03/2016


	// Second Method to Populate Records which is Approved all condition
	public void populateCustomerDetailsFromBeneRelation() {
		resetFilters("form1:dataTable");
		//backFromBenificaryStatusPanel();
		setRenderBeneCountry(true);
		log.info("Entering into populateCustomerDetailsFromBeneRelation method ");
		try{

			if(coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()){
				coustomerBeneficaryDTList.clear();
			}
			log.info( "=====================getCustomerNo()"+getCustomerNo()+""+getBeneficiaryCountryId());
			List<BenificiaryListView> isCoustomerExist = iPersonalRemittanceService.getBeneficiaryCountryList(getCustomerNo(),getBeneficiaryCountryId());
			log.info( "====================="+isCoustomerExist.size());

			if (isCoustomerExist.size() > 0) {
				for (int i = 0; i < isCoustomerExist.size(); i++) {
					//rel = isCoustomerExist.get(i);
					BenificiaryListView rel = new BenificiaryListView();
					rel =isCoustomerExist.get(i);
					//personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();
					PersonalRemmitanceBeneficaryDataTable personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();

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
					personalRBDataTable.setBenificaryCountry(rel.getCountryId()); // bene country Id
					personalRBDataTable.setBenificaryCountryName(rel.getCountryName()); // bene country Name
					personalRBDataTable.setBenificaryName(rel.getBenificaryName());
					personalRBDataTable.setBenificaryStatusId(rel.getBenificaryStatusId());
					personalRBDataTable.setBenificaryStatusName(rel.getBenificaryStatusName());
					personalRBDataTable.setBooDisabled(rel.getBankAccountNumber()!=null ? true: false);
					personalRBDataTable.setBranchCode(rel.getBranchCode());
					personalRBDataTable.setBranchId(rel.getBranchId());
					personalRBDataTable.setCityName(rel.getCityName());
					personalRBDataTable.setCountryId(rel.getBenificaryCountry()); // bank country Id
					personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName()); // bank country Name
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
					//personalRBDataTable.setServiceProviderBankBranchId(rel.get);
					personalRBDataTable.setStateName(rel.getStateName());
					personalRBDataTable.setMapSequenceId(rel.getMapSequenceId());
					
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
					/*blocked by koti we are fetching view 
					 * List<BeneficaryMaster> lstbeneBeneficaryMasters=iPersonalRemittanceService.toFetchbeneHouseDetails(rel.getBeneficaryMasterSeqId());
					if(lstbeneBeneficaryMasters.size()>0){
						personalRBDataTable.setBeneHouseNo(lstbeneBeneficaryMasters.get(0).getBuildingNo());
						personalRBDataTable.setBeneFlatNo(lstbeneBeneficaryMasters.get(0).getFlatNo());
						personalRBDataTable.setBeneStreetNo(lstbeneBeneficaryMasters.get(0).getStreetNo());
					}*/
					//personalRBDataTable.setBeneHouseNo(rel.getB);
					//For swift  beneficiary 
					personalRBDataTable.setSwiftBic(rel.getSwiftBic());
					//if(rel.getLastJavaRemittance()!=null){
					if(rel.getLastEmosRemittance()==null){
						personalRBDataTable.setTransactinStatus(Constants.NO);
					}else{
						personalRBDataTable.setTransactinStatus(Constants.YES);
					}

					List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),rel.getServiceGroupId());
					if (lstServiceGroupMasterDesc.size() > 0) {
						personalRBDataTable.setServiceGroupName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
					}

					List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(rel.getBeneficaryMasterSeqId());

					System.out.println("telePhone :"+telePhone.toString());
					if (telePhone != null && telePhone.size() != 0) {
						String telNumber = null;
						if(telePhone.size() == 1){
							if(telePhone.get(0).getTelephoneNumber()!=null){
								telNumber = telePhone.get(0).getTelephoneNumber().trim();
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
								telNumber = beneficaryContact.getTelephoneNumber().trim();
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

					personalRBDataTable.setStateId(rel.getStateId());
					personalRBDataTable.setDistrictId(rel.getDistrictId());
					personalRBDataTable.setCityId(rel.getCityId());
					personalRBDataTable.setBankAccountTypeId(rel.getBankAccountTypeId());

					coustomerBeneficaryDTList.add(personalRBDataTable);

					//personalRBDataTable = null;
					//rel = null;
				}
			}

			//backFromBenificaryStatusPanel();//03032016

		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		log.info("Exit into populateCustomerDetailsFromBeneRelation method ");
	}

	// to fetch all Customer details
	public void getCustomerDetails() {

		clearCustomerDetails();

		try{
			if (customerDetailsList.size() != 0) {

				CustomerIdProof customerDetails = customerDetailsList.get(0);

				setSelectCard(customerDetails.getFsBizComponentDataByIdentityTypeId().getComponentDataId());
				setIdNumber(customerDetails.getIdentityInt());
				setCustomerName(customerDetails.getFsCustomer().getFirstName());
				setCustomerCrNumber(customerDetails.getFsCustomer().getCrNo() == null ? "" : customerDetails.getFsCustomer().getCrNo());
				setCustomerNo(customerDetails.getFsCustomer().getCustomerId());
				setCustomerrefno(customerDetails.getFsCustomer().getCustomerReference());
				setCountryId(customerDetails.getFsCustomer().getFsCountryMasterByCountryId().getCountryId());
				setFirstName(customerDetails.getFsCustomer().getFirstName());
				setSecondName(customerDetails.getFsCustomer().getMiddleName());
				setThirdName(customerDetails.getFsCustomer().getLastName());
				setCompanyName(customerDetails.getFsCustomer().getCompanyName());
				setCompanyNameLocal(customerDetails.getFsCustomer().getCompanyNameLocal());
				//setCustomerFullName(nullCheck(getFirstName()) + " " + nullCheck(getSecondName()) + " " + nullCheck(getThirdName()));
				//setCustomerLocalFullName(nullCheck(customerDetails.getFsCustomer().getFirstNameLocal()) + " " + nullCheck(customerDetails.getFsCustomer().getMiddleNameLocal()) + " " + nullCheck(customerDetails.getFsCustomer().getLastNameLocal()));
				if(getCompanyName() != null){
					setCustomerFullName(getCompanyName());	
				}else{
					setCustomerFullName(nullCheck(getFirstName()) + nullCheck(getSecondName()) + nullCheck(getThirdName()));
				}
				if(getCompanyNameLocal() != null){
					setCustomerLocalFullName(getCompanyNameLocal());	
				}else{
					setCustomerLocalFullName(nullCheck(customerDetails.getFsCustomer().getFirstNameLocal()) + nullCheck(customerDetails.getFsCustomer().getMiddleNameLocal()) + nullCheck(customerDetails.getFsCustomer().getLastNameLocal()));
				}
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
				String teleCountryId = generalService.getTelephoneCountryBasedOnNationality(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
				setCountryCode(teleCountryId);
				setMcountryCode(teleCountryId);
				BigDecimal occupationID = generalService.getOccupationId(customerDetails.getFsCustomer().getCustomerId());
				/*if(customerDetails.getFsCustomer().getLoyaltyPoints()!=null){
					setLoyaltyPoints(customerDetails.getFsCustomer().getLoyaltyPoints());
				}else{
					setLoyaltyPoints(new BigDecimal(0));
				}*/
				setLoyaltyPoints(iPersonalRemittanceService.getLoyaltyPointFromFunction(sessionStateManage.getCountryId(),getCustomerrefno()));
				System.out.println("Loyalty Points :"+customerDetails.getFsCustomer().getLoyaltyPoints());
				if (occupationID != null) {
					String occupation = generalService.getOccupationDesc(occupationID,sessionStateManage.getLanguageId());
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
				
				// if fetching all customer application checking for application status S and Transaction Doc No Null
				String status = null;
				iPersonalRemittanceService.updateRemittanceApplication(getCustomerNo(), status);

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
		setLoyaltyPoints(null);
	}

	// to fetch shopping cart records both remittance and fcsale
	public void getShoppingCartDetails(BigDecimal customerNo) {
		shoppingcartDTList.clear();
		boolean checkStatus = Boolean.TRUE;
		List<ShoppingCartDetails> shoppingCartList = new ArrayList<ShoppingCartDetails>();
		try{
			shoppingCartList = iPersonalRemittanceService.getShoppingCartDetails(customerNo);
			
			System.out.println("Corporate : "+getBooRenderCorporateBack());
			if(getBooRenderCorporateBack() != null && getBooRenderCorporateBack()){
				checkStatus = Boolean.TRUE;
			}else{
				checkStatus = generalService.checkCorporateUser(sessionStateManage.getCountryId(),new BigDecimal(sessionStateManage.getBranchId()),sessionStateManage.getUserName());
			}
			
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
						shoppingCartDataTableBean.setLocalChargeAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalChargeAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					if (shoppingCartDetails.getLocalCommisionAmount() != null)
						shoppingCartDataTableBean.setLocalCommisionAmount(GetRound.roundBigDecimal(shoppingCartDetails.getLocalCommisionAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
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

					if(shoppingCartDetails.getApplicationType()!=null && shoppingCartDetails.getApplicationType().equals(Constants.FCSale))
					{
						shoppingCartDataTableBean.setBooReportEligible(false);
					}else{
						shoppingCartDataTableBean.setBooReportEligible(true);
					}

					if(shoppingCartDetails.getLoyaltsPointIndicator()!=null)
					{
						shoppingCartDataTableBean.setLoyaltsPointIndicator(shoppingCartDetails.getLoyaltsPointIndicator());
					}

					if(shoppingCartDetails.getLoyaltsPointencahsed()!=null)
					{
						shoppingCartDataTableBean.setLoyaltsPointencahsed(shoppingCartDetails.getLoyaltsPointencahsed());
					}

                    if(shoppingCartDetails.getAmtbCouponEncashed()!=null){
						shoppingCartDataTableBean.setAmtbCouponencahsed(shoppingCartDetails.getAmtbCouponEncashed());
					}
                    
                    shoppingCartDataTableBean.setBooRenderCheckBox(checkStatus);
                    
					shoppingcartDTList.add(shoppingCartDataTableBean);


				}
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// to call beneficary Creation Method
	public void gotToNewBenificaryCreation() {
		try {
			BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean)appContext.getBean("beneficiaryCreationBean");
			beneficiaryStatusList();
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBeneficaryStatusId(null);
			setRenderBackButton(true);
			//objectBene.clearFirstTime();
			objectBene.setBooenableAgentPanel(false);
			objectBene.setBooEnableBankChannelPanel(false);
			objectBene.setWesternunionPanel(false);
			objectBene.setBooRenderBeneficaryCreation(true);
			objectBene.setDisabledServiceGroup(false);
			objectBene.setBeneCountryid(getCountryId());
			objectBene.setIdNumber(getIdNumber());
			objectBene.setSelectCard(getSelectCard());
			objectBene.setCustomerNo(getCustomerNo());
			objectBene.setCustomerrefno(getCustomerrefno());
			objectBene.setCustomerFullName(getCustomerFullName());
			objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);
			objectBene.setBeneAccEditOrCreate(false);
			objectBene.setBenifisCountryId(getNationality());
			BigDecimal currencyId = iPersonalRemittanceService.getCurrencyId(objectBene.getBenifisCountryId());
			objectBene.setBenifisCurrencyId(currencyId);

			objectBene.clearFirstTime();
			if(getBooRenderCorporateBack()){
				objectBene.setBooBenefiPersonalRemit(false);
				objectBene.setBooBenefiCorporateRemit(true);
			}else{
				objectBene.setBooBenefiPersonalRemit(true);
				objectBene.setBooBenefiCorporateRemit(false);
			}

			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void beneficiaryStatusList() {
		benificaryStatusList.clear();
		benificaryStatusName.clear();
		List<BeneficaryStatus> beneStatus = iPersonalRemittanceService.getBeneficaryStatusList();
		List<CountryBranch> lstToCountryBranch = generalService.getBranchDetailsFromBeneStatus(sessionStateManage.getCountryId(),new BigDecimal(sessionStateManage.getBranchId()));
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

	// to move from remittance to fc sale
	public void callToFCSale() {
		log.info("Entering into callToFCSale method");
		
		boolean cashierOptionStatus = checkEmployeeAllowedOrNot();
		if(cashierOptionStatus){
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			try {
				HttpSession session = sessionStateManage.getSession();
				session.setAttribute("customerNo", getCustomerNo());
				session.setAttribute("IdNumber", getIdNumber());
				session.setAttribute("remit", "P");
				session.setAttribute("cardType", getSelectCard());
				context.redirect("../foreigncurrency/foreigncurrencysale.xhtml");
			} catch (Exception e) {
				log.info("Problem to redirect:" + e);
			}
		}else{
			setExceptionMessage("You are not authorized to collect amount. Please request IT for permission.");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}


	public void callToBeneficaryTransactions() {
		log.info("Entering into callToBeneficaryTransactions method");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("customerRefNo", getCustomerrefno());
			session.setAttribute("IdNumber", getIdNumber());
			session.setAttribute("remit", "P");
			context.redirect("../beneficary/customertransactions.xhtml");
		} catch (Exception ex) {
			log.info("Problem in VIEW :" + ex);
			setProcedureError(ex.getMessage());
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
		}
	}

	// checking function and banking_Channel and Cash Product
	public void checkProductForEdit(PersonalRemmitanceBeneficaryDataTable datatabledetails){

		try{
			if(datatabledetails.getServiceGroupId() != null){

				if(datatabledetails.getMapSequenceId() != null){
					BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"));
					if(paymentModeCashId != null && datatabledetails.getServiceGroupId().compareTo(paymentModeCashId)==0){
						//cash product
						try{
							HashMap <String,String> inputValues = new HashMap<String,String>();
							inputValues.put("P_BENE_MASTER_SEQ", datatabledetails.getBeneficaryMasterSeqId()==null ? "0" : datatabledetails.getBeneficaryMasterSeqId().toPlainString());
							inputValues.put("P_BENE_ACCOUNT_SEQ", datatabledetails.getBeneficiaryAccountSeqId()==null ? "0" : datatabledetails.getBeneficiaryAccountSeqId().toPlainString());
							inputValues.put("P_CUSTOMER_ID", datatabledetails.getCustomerId()==null ? "0" : datatabledetails.getCustomerId().toPlainString());

							String beneEditStatus=iPersonalRemittanceService.getBeneficiaryStatusForEdit(inputValues);

							if(beneEditStatus!=null && beneEditStatus.equalsIgnoreCase("E5"))
							{
								// district is mandatory All Countries Except INDIA
								if(datatabledetails.getStateId() != null && datatabledetails.getTelphoneNum() != null && datatabledetails.getBenificaryCountry() != null){
									String countryCode = generalService.getCountryCode(datatabledetails.getBenificaryCountry());
									if(countryCode != null && countryCode.equalsIgnoreCase(Constants.IND_CODE)){
										gotoRemittanceservice(datatabledetails);
									}else{
										fetchContactNumberEdit(datatabledetails);
									}
								}else{
									fetchContactNumberEdit(datatabledetails);
								}
							}else if(beneEditStatus.equalsIgnoreCase("EC"))
							{
								if(datatabledetails.getTelphoneNum() != null){
									gotoRemittanceservice(datatabledetails);
								}else{
									fetchContactNumberEdit(datatabledetails);
								}
							}else
							{
								//gotoRemittanceservice(datatabledetails);
								if(datatabledetails.getTelphoneNum() != null){
									gotoRemittanceservice(datatabledetails);
								}else{
									fetchContactNumberEdit(datatabledetails);
								}
							}
						}catch(Exception e){
							setExceptionMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}
						//}
					}else{
						try{
							HashMap <String,String> inputValues = new HashMap<String,String>();
							inputValues.put("P_BENE_MASTER_SEQ", datatabledetails.getBeneficaryMasterSeqId()==null ? "0" : datatabledetails.getBeneficaryMasterSeqId().toPlainString());
							inputValues.put("P_BENE_ACCOUNT_SEQ", datatabledetails.getBeneficiaryAccountSeqId()==null ? "0" : datatabledetails.getBeneficiaryAccountSeqId().toPlainString());
							inputValues.put("P_CUSTOMER_ID", datatabledetails.getCustomerId()==null ? "0" : datatabledetails.getCustomerId().toPlainString());

							String beneEditStatus=iPersonalRemittanceService.getBeneficiaryStatusForEdit(inputValues);

							if(beneEditStatus!=null && beneEditStatus.equalsIgnoreCase("E5"))
							{
								// district is mandatory All Countries Except INDIA - bank account type id need to check
								if(datatabledetails.getStateId() != null && datatabledetails.getTelphoneNum() != null && datatabledetails.getBenificaryCountry() != null && datatabledetails.getBankAccountTypeId() != null){
									String countryCode = generalService.getCountryCode(datatabledetails.getBenificaryCountry());
									if(countryCode != null && countryCode.equalsIgnoreCase(Constants.IND_CODE)){
										gotoRemittanceservice(datatabledetails);
									}else{
										fetchContactNumberEdit(datatabledetails);
									}
								}else{
									fetchContactNumberEdit(datatabledetails);
								}
								//fetchFiveFieldsEdit(datatabledetails);
							}else if(beneEditStatus.equalsIgnoreCase("EC"))
							{
								if(datatabledetails.getTelphoneNum() != null){
									gotoRemittanceservice(datatabledetails);
								}else{
									fetchContactNumberEdit(datatabledetails);
								}
							}else
							{
								//gotoRemittanceservice(datatabledetails);
								if(datatabledetails.getTelphoneNum() != null){
									gotoRemittanceservice(datatabledetails);
								}else{
									fetchContactNumberEdit(datatabledetails);
								}
							}
						}catch(Exception e){
							setExceptionMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}
					}
				}else{
					// map Seq id is missing - pushing to fields 
					fetchContactNumberEdit(datatabledetails);
				}
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// edit only five fields
	/*public void fetchFiveFieldsEdit(PersonalRemmitanceBeneficaryDataTable datatabledetails){
		try {
			beneficiaryEditBean.clear();
			beneficiaryEditBean.setBeneficiaryCountryId(datatabledetails.getBenificaryCountry());
			beneficiaryEditBean.setBeneficiaryAccountSeqId(datatabledetails.getBeneficiaryAccountSeqId());
			beneficiaryEditBean.setBeneficaryMasterSeqId(datatabledetails.getBeneficaryMasterSeqId());
			beneficiaryEditBean.setBeneficiaryName(datatabledetails.getBenificaryName());
			beneficiaryEditBean.setBeneficiaryArabicName(datatabledetails.getArbenificaryName());
			beneficiaryEditBean.setBeneficiaryBank(datatabledetails.getBankName());
			beneficiaryEditBean.setBeneficiaryCountryTelePhoneNumber(datatabledetails.getTelphoneNum());
			beneficiaryEditBean.setTelePhoneCode(datatabledetails.getTelphoneCode());
			beneficiaryEditBean.setMobileCode(datatabledetails.getTelphoneCode());
			beneficiaryEditBean.setPersonalRemmitanceBeneficaryDataTables(datatabledetails);
			beneficiaryEditBean.setBooRenderCountryPanel(true);
			beneficiaryEditBean.setBooRenderFirstPanel(true);
			beneficiaryEditBean.populateSearchValue();
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../remittance/BeneficiaryEdit.xhtml");
		} catch (IOException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}*/

	// edit only contact Number
	public void fetchContactNumberEdit(PersonalRemmitanceBeneficaryDataTable datatabledetails){
		try {
			beneficiaryEditBean.clear();
			beneficiaryEditBean.setBeneficiaryCountryId(datatabledetails.getBenificaryCountry());
			beneficiaryEditBean.setBeneficiaryCountryName(datatabledetails.getBenificaryCountryName());
			beneficiaryEditBean.setBeneficaryMasterSeqId(datatabledetails.getBeneficaryMasterSeqId());
			beneficiaryEditBean.setBeneficiaryAccountSeqId(datatabledetails.getBeneficiaryAccountSeqId());
			beneficiaryEditBean.setBeneficiaryName(datatabledetails.getBenificaryName());
			beneficiaryEditBean.setBeneficiaryArabicName(datatabledetails.getArbenificaryName());
			beneficiaryEditBean.setBeneficiaryBank(datatabledetails.getBankName());
			beneficiaryEditBean.setBeneficiaryCountryTelePhoneNumber(datatabledetails.getTelphoneNum());
			beneficiaryEditBean.setTelePhoneCode(datatabledetails.getTelphoneCode());
			beneficiaryEditBean.setMobileCode(datatabledetails.getTelphoneCode());
			beneficiaryEditBean.setBooRenderCountryPanel(true);
			//new Fields added 23/08/2016
			beneficiaryEditBean.setBooRenderFirstPanel(true);
			//bank country
			beneficiaryEditBean.setBenifisBankCountryId(datatabledetails.getCountryId());
			beneficiaryEditBean.setBenifisBankCountryName(datatabledetails.getCountryName());
			beneficiaryEditBean.setBankBranchFullName(datatabledetails.getBranchCode()+" - "+datatabledetails.getBankBranchName());
			beneficiaryEditBean.setBeneSwiftCode(datatabledetails.getSwiftBic());
			beneficiaryEditBean.setBenifisBankId(datatabledetails.getBankId());
			beneficiaryEditBean.setRelationId(datatabledetails.getRelationShipId());
			beneficiaryEditBean.setPersonalRemmitanceBeneficaryDataTables(datatabledetails);
			beneficiaryEditBean.setBeneficiaryRelationShipSeqId(datatabledetails.getBeneficiaryRelationShipSeqId());
			beneficiaryEditBean.setServicebankBranchId(datatabledetails.getBranchId());
			beneficiaryEditBean.setBankBranchCode(datatabledetails.getBranchCode());
			if(datatabledetails.getNationality() != null){
				beneficiaryEditBean.setNationality(new BigDecimal(datatabledetails.getNationality()));
			}

			beneficiaryEditBean.populateSearchValue();
			String countryCode = generalService.getCountryCode(datatabledetails.getBenificaryCountry());
			if(countryCode != null && countryCode.equalsIgnoreCase(Constants.IND_CODE)){
				beneficiaryEditBean.setMandatoryOptional(false);
			}else{
				beneficiaryEditBean.setMandatoryOptional(true);
			}
			String roleNameDesc=iPersonalRemittanceService.toFetchRoleName(new BigDecimal(sessionStateManage.getRoleId()));
			String roleName = roleNameDesc.trim();
			if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCHSTAFF)) {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("../remittance/BeneficiaryEditCounterStaff.xhtml");
			}else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER) || roleName.equalsIgnoreCase(Constants.USER_ROLE_MANAGER) || roleName.equalsIgnoreCase(Constants.USER_ROLE_CORPORATE)) {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("../remittance/BeneficiaryEdit.xhtml");
			}else{
				/*RequestContext.getCurrentInstance().execute("roleNotExist.show();");
				return;*/ 
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("../remittance/BeneficiaryEditCounterStaff.xhtml");
			}

		} catch (IOException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// full edit
	public void fetchAllRecordForNonTrnx(PersonalRemmitanceBeneficaryDataTable datatabledetails){
		try {
			BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean)appContext.getBean("beneficiaryCreationBean");
			objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);
			
			objectBene.setBooRenderBeneficaryCreation(true);
			objectBene.setBooRenderWesterUnion(false);
			objectBene.setBooRenderWesterUnionUpload(false);
			
			objectBene.setDataTableBeneObj(datatabledetails);
			objectBene.renderBeneficiaryFullEditNavigation();
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../beneficary/firsttimebeneficaryedit.xhtml");
		} catch (IOException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}
	
	// check beneficiary account type
	public boolean chkBeneficiaryAccountType(PersonalRemmitanceBeneficaryDataTable datatabledetails){

		boolean chkAccType = false;
		if(datatabledetails.getBankAccountNumber() != null && datatabledetails.getCountryId() != null && datatabledetails.getBankAccountTypeId() != null){
			int valueAvail = 0;
			List<AccountTypeFromView> lstAccType = beneficaryCreation.getAccountTypeFromView(datatabledetails.getCountryId());
			if(lstAccType != null && lstAccType.size() != 0){
				for (AccountTypeFromView accountTypeFromView : lstAccType) {
					if(accountTypeFromView.getAdditionalAmiecId() != null && accountTypeFromView.getAdditionalAmiecId().compareTo(datatabledetails.getBankAccountTypeId())==0){
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

	// checking beneficiary only for western union to stop processing
	public void checkingWesternUnionRecord(PersonalRemmitanceBeneficaryDataTable datatabledetails){

		if(datatabledetails != null){
			if(datatabledetails.getBankCode() != null){
				if(datatabledetails.getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
					// matching bank code with western union
					setExceptionMessage(WarningHandler.showWarningMessage("lbl.wuremittancewarningmsg", sessionStateManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}else{
					checkProductForEdit(datatabledetails);
				}
			}else{
				//bank code null
				setExceptionMessage(WarningHandler.showWarningMessage("lbl.bankcodenotavailable", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}else{
			//dataTable throwing null
			setExceptionMessage(WarningHandler.showWarningMessage("lbl.datatablenullvalue", sessionStateManage.getLanguageId()));
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	//fetch all country alpha code
	public HashMap<BigDecimal, String> fetchAllCountryAlphaCode(){
		HashMap<BigDecimal, String> lstCountryAlphaCode = new HashMap<BigDecimal, String>();
		List<CountryMasterDesc> lstCountry = generalService.getCountryList(sessionStateManage.getLanguageId());
		if(lstCountry != null && !lstCountry.isEmpty()){
			for (CountryMasterDesc countryMasterDesc : lstCountry) {
				lstCountryAlphaCode.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getFsCountryMaster().getCountryAlpha2Code());
			}
		}else{
			lstCountryAlphaCode.clear();
		}

		return lstCountryAlphaCode;
	}

	// flowing to remittance Screen
	public void gotoRemittanceservice(PersonalRemmitanceBeneficaryDataTable datatabledetails) {
		
		// not a place order 
		setBooRenderPlaceOrder(false);
		setBooRenderPlaceOrderByRemit(false);
		setRatePlaceOrderPk(null);
		
		// bank account type check
		boolean chkAccTypeValue = chkBeneficiaryAccountType(datatabledetails);
		if(!chkAccTypeValue){
			log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+datatabledetails.getCountryId());
			log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+datatabledetails.getBankAccountTypeId());
			log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+datatabledetails.getCustomerId());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			setExceptionMessage("Account Type Mismatch, Please edit and save");
			return;
		}

		String errMsg = checkBeneficiaryDetailsWithProc(datatabledetails);

		if(errMsg != null && !errMsg.equalsIgnoreCase("")){
			backFromBenificaryStatusPanel();
			setProcedureError(" EX_CHK_BENEFICIARY : "+ errMsg + " Please Contact Branch Manager for Modification of Beneficiary Details");
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
			return;
		}else{
			try{
				clearData();
				clearTransactionDetails();
				clearICashProduct();

				setLoyaltyPoints(iPersonalRemittanceService.getLoyaltyPointFromFunction(sessionStateManage.getCountryId(),getCustomerrefno()));

				setDatabenificarycountry(datatabledetails.getBenificaryCountry()); // beneficiary Country Id
				setDatabenificarycountrycode(generalService.getCountryCode(datatabledetails.getBenificaryCountry()));
				setDatabenificarycountryname(datatabledetails.getBenificaryCountryName()); // beneficiary Country Id
				setDatabenificarycurrency(datatabledetails.getCurrencyId());
				setDatabenificaryservicegroup(datatabledetails.getServiceGroupName());
				setDataservicegroupid(datatabledetails.getServiceGroupId());

				System.out.println("setAccountNumberSeqId :"+datatabledetails.getBeneficiaryAccountSeqId()+"\t datatabledetails.getBenificaryMasterId() :"+datatabledetails.getBeneficaryMasterSeqId());

				setDataAccountnum(datatabledetails.getBankAccountNumber());

				setDatabenificarycurrencyname(datatabledetails.getCurrencyName());
				setDatabenificarybankname(datatabledetails.getBankName());
				setDatabenificarybankalphacode(datatabledetails.getBankCode());
				setDatabenificarybranchname(datatabledetails.getBankBranchName());
				setDatabenificaryname(datatabledetails.getBenificaryName());
				setBenificiaryryNameRemittance(datatabledetails.getBenificaryName());
				setMasterId(datatabledetails.getBeneficaryMasterSeqId());
				setBeneficaryBankId(datatabledetails.getBankId());
				setBeneficaryBankBranchId(datatabledetails.getBranchId());
				setBeneAddressDetails(datatabledetails.getBeneAddressDetails());
				//added By koti @ 10/08/2016
				setBeneHouseNo(datatabledetails.getBeneHouseNo());
				setBeneFlatNo(datatabledetails.getBeneFlatNo());
				setBeneStreetNo(datatabledetails.getBeneStreetNo());
				//set swift BIC
				if(datatabledetails.getSwiftBic()!=null){
					setSwiftBic(datatabledetails.getSwiftBic());
				}else{
					log.info("datatabledetails.getBankId() :"+datatabledetails.getBankId()+"\t Branch Id :"+datatabledetails.getBranchId());
					List<BankBranchView> bankBranchViewList =bankBranchDetailsService.toFetchAllDetailsFromBankBranch(datatabledetails.getBankId(),datatabledetails.getBranchId());
					if(bankBranchViewList!=null && bankBranchViewList.size()==1){
						log.info("bankBranchViewList.get(0).getSwift() "+bankBranchViewList.get(0).getSwift());
						setSwiftBic(bankBranchViewList.get(0).getSwift());
					}

				}

				setDataBankbenificarycountry(datatabledetails.getCountryId()); // beneficiary bank Country Id
				setDataBankbenificarycountryname(datatabledetails.getCountryName()); // beneficiary bank Country Name

				// account and relationship id
				setBeneficiaryAccountSeqId(datatabledetails.getBeneficiaryAccountSeqId());
				setBeneficiaryRelationShipSeqId(datatabledetails.getBeneficiaryRelationShipSeqId());

				// setting contact details
				setBooRenderBeneTelDisable(true);
				setBooRenderBeneTelMandatory(false);
				setDataBeneContactId(datatabledetails.getBeneficiaryContactSeqId());
				if(datatabledetails.getTelphoneNum() != null){
					setBenificaryTelephone(datatabledetails.getTelphoneNum());
					setDataTempBeneTelNum(datatabledetails.getTelphoneNum());
				}

				HashMap<String, String> inputRoutingBankSetUpdetails = new HashMap<String, String>();

				inputRoutingBankSetUpdetails.put("P_APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId().toPlainString());
				inputRoutingBankSetUpdetails.put("P_BENE_COUNTRY_ID", datatabledetails.getCountryId().toPlainString()); // beneficiary bank Country Id
				inputRoutingBankSetUpdetails.put("P_BENE_BANK_ID", datatabledetails.getBankId().toPlainString());
				inputRoutingBankSetUpdetails.put("P_BENE_BANK_BRANCH_ID",datatabledetails.getBranchId().toPlainString());

				List<ServiceGroupMasterDesc> lstServiceCode = iServiceGroupMasterService.getServiceGroupDescList(datatabledetails.getServiceGroupId());
				if (lstServiceCode != null) {
					ServiceGroupMasterDesc ServiceCode = lstServiceCode.get(0);
					inputRoutingBankSetUpdetails.put("P_SERVICE_GROUP_CODE", ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
					setServiceGroupCode(ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
				}

				inputRoutingBankSetUpdetails.put("P_CURRENCY_ID", datatabledetails.getCurrencyId().toPlainString());

				try {

					/** validating EX_P_VALIDATE_BENEFICIARY. added by Rabil on 03/12/2015.*/
					String proceValiMessage =null;

					proceValiMessage =iPersonalRemittanceService.getValidateBeneficiaryProcedure(sessionStateManage.getCountryId(), getCustomerNo(), sessionStateManage.getUserName(), getMasterId(), getBeneficiaryAccountSeqId());

					if(proceValiMessage!=null && proceValiMessage.length()>0){
						setProcedureError(" EX_P_VALIDATE_BENEFICIARY : "+proceValiMessage);
						RequestContext.getCurrentInstance().execute("procedureErr.show();");
						return;
					}

					HashMap<String ,String> bannedBankProcedureOut = iPersonalRemittanceService.getBannedBankCheckProcedure(sessionStateManage.getCountryId(),getBeneficaryBankId(),getMasterId());

					if(bannedBankProcedureOut.get("P_ERROR_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ERROR_MESSAGE").equals("")){
						System.out.println("P_error_message :"+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
						setProcedureError("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
						RequestContext.getCurrentInstance().execute("procedureErr.show();");
						return;
					}else if(bannedBankProcedureOut.get("P_ALERT_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ALERT_MESSAGE").equals("")){
						System.out.println("P_ALERT_MESSAGE :"+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
						setProcedureError("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
						RequestContext.getCurrentInstance().execute("procedureErr.show();");
					}

					//Added by Rabil.
					if( (sessionStateManage.getBranchId()!=null || sessionStateManage.getCustomerType().equals("E"))){ // && sessionStateManage.getRoleId().equals("1")
						inputRoutingBankSetUpdetails.put("P_USER_TYPE","BRANCH");
						setBooSingleService(true);
					}else if(sessionStateManage.getBranchId()!=null  &&  sessionStateManage.getUserType().equalsIgnoreCase("K")){
						inputRoutingBankSetUpdetails.put("P_USER_TYPE","KIOSK");
						setBooMultipleService(false);
					}else{
						setBooMultipleService(false);
					}

					if (getDatabenificaryservicegroup().equalsIgnoreCase(Constants.CASHNAME)) {
						setBooRenderAgent(true);
						setBooRenderRouting(false);

						List<BeneficaryAccount> lstBeneficaryAccount= iPersonalRemittanceService.getCashProductDetails(datatabledetails.getBeneficiaryAccountSeqId());

						if(lstBeneficaryAccount!=null && lstBeneficaryAccount.size()>0)
						{

							BeneficaryAccount beneficaryAccount =lstBeneficaryAccount.get(0);
							// setting service Master Id
							List<ServiceMaster> lstServiceMaster = serviceMasterService.toFetchServiceRecordsServiceMaster(beneficaryAccount.getServicegropupId().getServiceGroupId());
							if(lstServiceMaster.size() != 0){
								ServiceMaster serviceMasterId = lstServiceMaster.get(0);
								setDataserviceid(serviceMasterId.getServiceId());
								setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
								setBooSingleService(true);
								setBooMultipleService(false);
								System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());
							}

							setRoutingCountry(beneficaryAccount.getBank().getFsCountryMaster().getCountryId()); 
							String lstRcountry = generalService.getCountryName(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),getRoutingCountry());
							if (lstRcountry != null) {
								setRoutingCountryName(lstRcountry);
								setDataroutingcountrycode(generalService.getCountryCode(getRoutingCountry()));
							}

							if(beneficaryAccount.getServiceProvider() != null){
								setRoutingBank(beneficaryAccount.getServiceProvider().getBankId()); 
								String lstRBank = generalService.getBankName(getRoutingBank());
								if (lstRBank != null) {
									setRoutingBankName(lstRBank);
								}
							}

							if(beneficaryAccount.getServiceProviderBranchId() != null){
								setRoutingBranch(beneficaryAccount.getServiceProviderBranchId()); // routing Bank Branch Id
								List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
								if (lstRbranch != null && lstRbranch.size() != 0) {
									BankBranch routingBranchName = lstRbranch.get(0);
									setRoutingBranchName(routingBranchName.getBranchFullName());
								}
							}

							// to fetch remittance
							//need to check with routing setup Specific - S or ALL
							Map<String,Object> mapRoutingSetup = checkRoutingSetupBranchApplicability();
							if(mapRoutingSetup != null && mapRoutingSetup.size() != 0){
								if(mapRoutingSetup.get("BranchApplicability") != null){
									String BranchAppl = (String)mapRoutingSetup.get("BranchApplicability");
									if(BranchAppl.equalsIgnoreCase(Constants.ALL)){
										if(mapRoutingSetup.get("RoutingBankBranchId") != null){
											setRoutingBranch((BigDecimal)mapRoutingSetup.get("RoutingBankBranchId")); // routing Bank Branch Id
											List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
											if (lstRbranch != null && lstRbranch.size() != 0) {
												BankBranch routingBranchName = lstRbranch.get(0);
												setRoutingBranchName(routingBranchName.getBranchFullName());
											}
										}
									}
								}
							}

							// to fetch remittance
							//remittancelistByBankIdForCash(beneficaryAccount.getServicegropupId().getServiceGroupId());
							remittancelistByBankIdForCash();
							// to fetch remittance
							//remittancelistByBankId();
							// Icash for cash Product
							fetchICashAgent();
						}else
						{
							// if record not available
						}

					} else {

						HashMap<String, String> outputRoutingBankSetUpdetails = iPersonalRemittanceService.getRoutingBankSetupDetails(inputRoutingBankSetUpdetails);

						System.out.println("P_ERROR_MESSAGE"+outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
						System.out.println("output RoutingBankSetUpdetails : "+outputRoutingBankSetUpdetails.toString());

						if (outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE") != null) {
							setProcedureError("EX_GET_ROUTING_SET_UP" + " : " +outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
							RequestContext.getCurrentInstance().execute("procedureErr.show();");
							return;
						} else {

							if (!outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID").equalsIgnoreCase("0")) {
								setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
								List<ServiceMasterDesc> lstServiceMaster = serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"), getDataserviceid());
								if(lstServiceMaster != null && !lstServiceMaster.isEmpty()){
									ServiceMasterDesc serviceMasterDetails = lstServiceMaster.get(0);
									setDatabenificaryservice(serviceMasterDetails.getLocalServiceDescription());
									setDatabenificaryservicecode(serviceMasterDetails.getServiceMaster().getServiceCode());
								}

								setBooSingleService(true);
								setBooMultipleService(false);
								System.out.println("Service Desc :"+getDatabenificaryservice()+"\t setDatabenificaryservice :"+getDatabenificaryservice());

								if (!outputRoutingBankSetUpdetails.get("P_ROUTING_COUNTRY_ID").equalsIgnoreCase("0")) {

									HashMap<BigDecimal, String> lstCountryAlphaCode = fetchAllCountryAlphaCode();

									setRoutingCountry(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_COUNTRY_ID")));
									setDataroutingcountryalphacode(lstCountryAlphaCode.get(getRoutingCountry()));
									String lstRcountry = generalService.getCountryName(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),getRoutingCountry());
									if (lstRcountry != null) {
										setRoutingCountryName(lstRcountry);
										setDataroutingcountrycode(generalService.getCountryCode(getRoutingCountry()));
									}

									if (!outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_ID").equalsIgnoreCase("0")) {

										setRoutingBank(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_ID"))); 

										List<BankMaster> lstRBank = generalService.getBankDetailsList(sessionStateManage.getLanguageId(),getRoutingCountry(),getRoutingBank());
										if (lstRBank != null && !lstRBank.isEmpty()) {
											BankMaster bankRec = lstRBank.get(0);
											setRoutingBankName(bankRec.getBankFullName());
											setDataroutingbankalphacode(bankRec.getBankCode());
										}

										if (!outputRoutingBankSetUpdetails.get("P_REMITTANCE_MODE_ID").equalsIgnoreCase("0")) {

											// spl pool based on routing country , routing bank
											specialRequestFcAmountCalculation();
											setRemitMode(new BigDecimal(outputRoutingBankSetUpdetails.get("P_REMITTANCE_MODE_ID")));
											String remitName = iRemitModeMaster.getRemittanceDesc(getRemitMode(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"));
											if (remitName != null) {
												setRemittanceName(remitName);
											}

											if (!outputRoutingBankSetUpdetails.get("P_DELIVERY_MODE_ID").equalsIgnoreCase("0")) {
												setDeliveryMode(new BigDecimal(outputRoutingBankSetUpdetails.get("P_DELIVERY_MODE_ID")));
												String deliveryName = iDeliveryModeMaster.getDeliveryDesc(getDeliveryMode(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"));
												if (deliveryName != null) {
													setDeliveryModeInput(deliveryName);
												}

												if (!outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_BRANCH_ID").equalsIgnoreCase("0")) {
													// set All values in form , No Need of view
													setRoutingBranch(new BigDecimal(outputRoutingBankSetUpdetails.get("P_ROUTING_BANK_BRANCH_ID"))); // routing Bank Branch Id
													List<BankBranch> lstRbranch = bankBranchDetailsService.getBankBranchByBranchID(getRoutingBranch());
													if (lstRbranch != null && lstRbranch.size() != 0) {
														BankBranch routingBranchName = lstRbranch.get(0);
														setRoutingBranchName(routingBranchName.getBranchFullName());
													}

													// call the ICASH Product Method
													fetchICashAgent();

												} else {
													// to fetch Bank Branch
													bankBranchByBankView();
												}

											} else {
												// to fetch Delivery
												deliverylistByRemitId();
											}

										} else {
											// to fetch remittance
											remittancelistByBankId();
										}

									} else {
										// setting routing bank Id from view
										bankDetailsByCountry();
									}

								} else {
									// fetch routing country from view
									countryNameByServiceId();

								}

							}else{ 
								//IF SERVICE_MASTER_ID=0
								setDataserviceid(new BigDecimal(outputRoutingBankSetUpdetails.get("P_SERVICE_MASTER_ID")));
								getServiceListDetails();
							}

						}

						setBooRenderAgent(false);
						setBooRenderRouting(true);
					}


				} catch (AMGException e) {
					setProcedureError(e.getMessage());
					RequestContext.getCurrentInstance().execute("procedureErr.show();");
				}

				if (getDatabenificaryservicegroup().equalsIgnoreCase(Constants.CASHNAME)) {
					setBooRenderAgent(true);
					setBooRenderRouting(false);
				} else {
					setBooRenderAgent(false);
					setBooRenderRouting(true);
				}

				// to get Beneficiary status from Beneficiary Master
				beneficiaryMaster = iPersonalRemittanceService.getAllMasterValues(getMasterId());
				if (beneficiaryMaster.size() > 0) {
					setBenificarystatus(beneficiaryMaster.get(0).getBeneficaryStatusName());
				}

				lstofCurrency.clear();

				PopulateData populatedata = new PopulateData(getDatabenificarycurrency(), getDatabenificarycurrencyname());
				PopulateData populatedata1 = null;
				String localCurrencyName = generalService.getCurrencyName(new BigDecimal(sessionStateManage.getCurrencyId()));
				if(localCurrencyName != null){
					populatedata1 = new PopulateData(new BigDecimal(sessionStateManage.getCurrencyId()),localCurrencyName);
				}

				lstofCurrency.add(populatedata);
				if(populatedata1 != null){
					lstofCurrency.add(populatedata1);
				}

				if (lstofCurrency.size() != 0) {
					for (PopulateData lstofcurrency : lstofCurrency) {
						if (lstofcurrency.getPopulateId().compareTo(new BigDecimal(sessionStateManage.getCurrencyId())) != 0) {
							setForiegnCurrency(lstofcurrency.getPopulateId());
						}
					}
				}

				setCurrency(new BigDecimal(sessionStateManage.getCurrencyId()));
				setSpotRate(Constants.No);
				setSpecialRateRef(Constants.No);
				setNextRender(true);
				setAvailLoyaltyPoints(Constants.No);
				setChargesOverseas(Constants.No);
				getServiceFirstPanel();
				setSpecialApprovalRadio(Integer.parseInt(Constants.THREE));
				
	            //AMTB Coupon Value For that customer IF in Application setup EX_APPLICATION_SETUP --AMTBC_PROMOTION is 'Y' .
				List<ApplicationSetup> lstApplicationSetup=iPersonalRemittanceService.getEmailFromAppSetup(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId());
				if(lstApplicationSetup!=null && !lstApplicationSetup.isEmpty()){
					if(lstApplicationSetup.size()==1 && lstApplicationSetup.get(0).getAmtbcPromotion()!=null && lstApplicationSetup.get(0).getAmtbcPromotion().equalsIgnoreCase("Y")){
						List<ViewAmtbCoupon> lstAmtbFromView =iPersonalRemittanceService.getAmtbCouponFromView(idNumber);
						if(lstVwAmtbCoupon != null && !lstVwAmtbCoupon.isEmpty()){
							lstVwAmtbCoupon.clear();
						}
						if(lstAmtbFromView!=null && lstAmtbFromView.size()>0){
							setLstVwAmtbCoupon(lstAmtbFromView);
							setAmtbCouponRender(true);
						}
					}
				}

				// customer telephone number fetching
				HashMap<String, String> pValidatecustTelInPut = new HashMap<String, String>();

				pValidatecustTelInPut.put("APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId().toPlainString());
				pValidatecustTelInPut.put("CUSTOMER_ID", getCustomerNo().toPlainString());

				try{
					if(!getBooRenderCorporateBack()){
						HashMap<String, String> pValidatecustTelOutPut = iPersonalRemittanceService.pValidateCustomerTelephoneDetails(pValidatecustTelInPut);

						if (pValidatecustTelOutPut.get("P_ERROR_MESSAGE") != null) {
							setProcedureError(pValidatecustTelOutPut.get("P_ERROR_MESSAGE"));
							RequestContext.getCurrentInstance().execute("procedureErr.show();");
							return;
						} else {

							if(pValidatecustTelOutPut.get("P_CONTACT_ID") != null){
								setDataCustomerContactId(new BigDecimal(pValidatecustTelOutPut.get("P_CONTACT_ID")));
							}else{
								setDataCustomerContactId(null);
							}

							if(pValidatecustTelOutPut.get("P_TELEPHONE_NUMBER") != null && !pValidatecustTelOutPut.get("P_TELEPHONE_NUMBER").trim().equalsIgnoreCase("")){
								setDataCustomerTelephoneNumber(pValidatecustTelOutPut.get("P_TELEPHONE_NUMBER"));
								setDataTempCustomerMobile(pValidatecustTelOutPut.get("P_TELEPHONE_NUMBER"));
								setBooRenderCustTelDisable(true);
							}else{
								setDataCustomerTelephoneNumber(null);
								setDataTempCustomerMobile(null);
								setBooRenderCustTelDisable(false);
								setBooRenderCustTelMandatory(true);
							}
						}
					}
				}catch(AMGException e){
					setExceptionMessage(e.getMessage());
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}catch(Exception e){
					setExceptionMessage(e.getMessage());
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}

			}catch(Exception e){
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}

		}
	}
	
	// checking routing setup
	public HashMap<String,Object> checkRoutingSetupBranchApplicability(){
		
		HashMap<String,Object> mapRoutingSetUpDt = new HashMap<String, Object>();
		
		HashMap<String,Object> hashMapDt = new HashMap<String, Object>();
		hashMapDt.put("APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId());
		hashMapDt.put("CURRENCY_ID", getDatabenificarycurrency());
		hashMapDt.put("SERVICE_MASTER_ID", getDataserviceid());
		hashMapDt.put("ROUTING_COUNTRY_ID", getRoutingCountry());
		hashMapDt.put("ROUTING_BANK_ID", getRoutingBank());
		hashMapDt.put("BENEFICIARY_COUNTRY_ID", getDataBankbenificarycountry());
		
		try {
			HashMap<String,Object> lstRoutingSetUp = iPersonalRemittanceService.getRoutingSetupForCashProduct(hashMapDt);

			if (lstRoutingSetUp != null) {
				mapRoutingSetUpDt = lstRoutingSetUp;
			} 

		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
		
		return mapRoutingSetUpDt;
	}

	// icash Product Method call
	public void fetchICashAgent(){

		setIcashStateSubAgent(false);
		setIcashAgentPanel(false);
		setIcashEFT(false);
		setIcashTT(false);
		setIcashCash(false);
		setIcashAgent(null);

		// Icash product Id
		BigDecimal icashBankID = null;

		List<BankMaster> lstICashBankID = generalService.getAllBankCodeFromBankMaster("ICASH");

		if(lstICashBankID != null && !lstICashBankID.isEmpty()){
			BankMaster icashBankRecord = lstICashBankID.get(0);
			icashBankID = icashBankRecord.getBankId();
		}

		// checking ICash and Routing Bank Id  
		if(icashBankID != null && getDatabenificaryservicecode() != null && getRoutingBank() != null && icashBankID.compareTo(getRoutingBank())==0 && getDatabenificaryservicecode().equalsIgnoreCase(Constants.TTCodeForService)){
			// set State and Sub Agent
			setIcashStateSubAgent(false);
			setIcashAgentPanel(true);
			setIcashEFT(true);
			setIcashTT(false);
			setIcashCash(false);

			if(lstIcashAgentsEFTDetails != null || !lstIcashAgentsEFTDetails.isEmpty()){
				lstIcashAgentsEFTDetails.clear();
			}

			if(lstIcashAgentsTTDetails != null || !lstIcashAgentsTTDetails.isEmpty()){
				lstIcashAgentsTTDetails.clear();
			}

			List<CurrencyMaster> lstCurrency = generalService.getCurrency(getDatabenificarycurrency());
			if(lstCurrency != null && !lstCurrency.isEmpty()){
				CurrencyMaster currMast = lstCurrency.get(0);
				setDatabenificarycurrencyalphacode(currMast.getIsoCurrencyCode()); // ISO Code
			}



			// 1. checking weather there is records for EFT of ICASH by count of query - // pop up agents
			HashMap<String, String> checkEFTandTT = new HashMap<String, String>();

			checkEFTandTT.put("Bene_Country_Code", getDatabenificarycountrycode());
			checkEFTandTT.put("Routing_Country_Code", getDataroutingcountrycode());
			checkEFTandTT.put("Bene_Bank_Aplha_Code", getDatabenificarybankalphacode());
			checkEFTandTT.put("Routing_Bank_Aplha_Code", getDataroutingbankalphacode());
			checkEFTandTT.put("Routing_Country_Aplha_Code", getDataroutingcountryalphacode());
			checkEFTandTT.put("Bene_Currency_Aplha_Code", getDatabenificarycurrencyalphacode());
			checkEFTandTT.put("Agent_Status", Constants.AgentStatusICash);
			checkEFTandTT.put("Remittance_Mode_Code", Constants.EFTCode);

			Boolean icashForEFT =  iPersonalRemittanceService.checkEFTAndTTForICASHProduct(checkEFTandTT);

			if(icashForEFT){

				setIcashEFT(true);
				setIcashTT(false);

				System.out.println("Success for EFT");
				List<ViewHODirectEFT> lstviewHodirect =  iPersonalRemittanceService.fetchAgentforEFTICASHProduct(checkEFTandTT);
				if(lstviewHodirect != null && !lstviewHodirect.isEmpty()){
					lstIcashAgentsEFTDetails.addAll(lstviewHodirect);
				}

				fetchAllStates(checkEFTandTT);

			}else{

				// 2. checking weather there is records for TT of ICASH by count of query - // pop up agents
				checkEFTandTT.put("Remittance_Mode_Code", Constants.TTCodeForRemittance);

				Boolean icashForTT =  iPersonalRemittanceService.checkEFTAndTTForICASHProduct(checkEFTandTT);

				if(icashForTT){

					setIcashEFT(false);
					setIcashTT(true);

					System.out.println("Success for TT");
					List<ViewHODirectInDirect> lstviewHodirectIndirect =  iPersonalRemittanceService.fetchAgentforTTICASHProduct(checkEFTandTT);
					if(lstviewHodirectIndirect != null && !lstviewHodirectIndirect.isEmpty()){
						lstIcashAgentsTTDetails.addAll(lstviewHodirectIndirect);
					}

					fetchAllStates(checkEFTandTT);

				}else{

					setIcashEFT(true);
					setIcashTT(false);					
					// error msg -- No Agents Found
					setExceptionMessage("No Agents Found");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}

			}		

		}else if(icashBankID != null && getDatabenificaryservice() != null && getRoutingBank() != null && icashBankID.compareTo(getRoutingBank())==0 && getDatabenificaryservice().equalsIgnoreCase(Constants.CASHNAME)){
			// cash product
			setIcashStateSubAgent(false);
			setIcashAgentPanel(true);
			setIcashCash(true);
			setIcashAgent(Constants.ICASHAGENTCODE);
			setIcashEFT(false);
			setIcashTT(false);
		}else{
			setIcashStateSubAgent(false);
			setIcashAgentPanel(false);
			setIcashCash(false);
			setIcashAgent(null);
			setIcashEFT(false);
			setIcashTT(false);
		}
	}

	// fetch states from view -- V_State
	public void fetchAllStates(HashMap<String, String> checkEFTandTT){

		setIcashState(null);
		setIcashSubAgent(null);
		if(lstIcashSubAgents != null || !lstIcashSubAgents.isEmpty()){
			lstIcashSubAgents.clear();
		}
		if(lstIcashState != null || !lstIcashState.isEmpty()){
			lstIcashState.clear();
		}

		List<ViewStatesForICASH> lstIcashStateDetails = iPersonalRemittanceService.fetchStateForICash(checkEFTandTT);
		if(lstIcashStateDetails != null && !lstIcashStateDetails.isEmpty()){
			lstIcashState.addAll(lstIcashStateDetails);
		}else{
			// error msg -- No States Found
			setExceptionMessage("No States Found");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// fetch states from view -- V_State
	public void fetchAllSubAgents(){

		setIcashSubAgent(null);
		if(lstIcashSubAgents != null || !lstIcashSubAgents.isEmpty()){
			lstIcashSubAgents.clear();
		}

		HashMap<String, String> checkEFTandTT = new HashMap<String, String>();

		checkEFTandTT.put("Routing_Bank_Aplha_Code", getDataroutingbankalphacode());
		checkEFTandTT.put("Routing_Country_Aplha_Code", getDataroutingcountryalphacode());
		checkEFTandTT.put("Bene_Currency_Aplha_Code", getDatabenificarycurrencyalphacode());
		checkEFTandTT.put("Agent_Status", Constants.AgentStatusICash);
		checkEFTandTT.put("Agent_HO_Code", getIcashAgent());
		checkEFTandTT.put("State_Code", getIcashState());
		if(isIcashEFT()){
			checkEFTandTT.put("Remittance_Mode_Code", Constants.EFTCode);
		}else if(isIcashTT()){
			checkEFTandTT.put("Remittance_Mode_Code", Constants.TTCodeForRemittance);
		}else{
			// no values of Agents
		}

		List<ViewSubAgent> lstIcashSubAgentDetails = iPersonalRemittanceService.fetchSubAgentsForICash(checkEFTandTT);

		if(lstIcashSubAgentDetails != null && !lstIcashSubAgentDetails.isEmpty()){
			lstIcashSubAgents.addAll(lstIcashSubAgentDetails);
		}else{
			// error msg -- No States Found
			setExceptionMessage("No Sub Agents Found");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// checking for EFT - state and sub agent required or not
	public void checkStateSubAgentForEFT(){

		ViewHODirectEFT lstAgentsEFTCheckStateSubAgent = null;
		setIcashStateSubAgent(false);

		if(lstIcashAgentsEFTDetails != null && !lstIcashAgentsEFTDetails.isEmpty() && getIcashAgent() != null){
			for (ViewHODirectEFT lstagent : lstIcashAgentsEFTDetails) {
				if(lstagent.getAgentHoCode().equalsIgnoreCase(getIcashAgent())){
					lstAgentsEFTCheckStateSubAgent = lstagent;
					break;
				}
			}

			if(lstAgentsEFTCheckStateSubAgent != null){
				// 1. if condition Type Flag [C or N] and Centralized '1' - No state and No Sub Agent
				// 2. else if condition Type Flag 'N' and Centralized '0' - state and Sub Agent required
				if((lstAgentsEFTCheckStateSubAgent.getTypeFlag().equalsIgnoreCase(Constants.C) || lstAgentsEFTCheckStateSubAgent.getTypeFlag().equalsIgnoreCase(Constants.No))
						&& lstAgentsEFTCheckStateSubAgent.getCentralized().equalsIgnoreCase("1")){
					// not required
					setIcashStateSubAgent(false);
				}else if(lstAgentsEFTCheckStateSubAgent.getTypeFlag().equalsIgnoreCase(Constants.No) && lstAgentsEFTCheckStateSubAgent.getCentralized().equalsIgnoreCase("0")){
					// required
					setIcashStateSubAgent(true);
				}else{
					// err msg
					setIcashStateSubAgent(false);
				}
			}
		}
	}

	// checking for EFT - state and sub agent required or not
	public void checkStateSubAgentForTT(){

		setIcashStateSubAgent(false);
		ViewHODirectInDirect lstAgentsTTCheckStateSubAgent = null;

		if(lstIcashAgentsTTDetails != null && !lstIcashAgentsTTDetails.isEmpty() && getIcashAgent() != null){
			for (ViewHODirectInDirect lstagent : lstIcashAgentsTTDetails) {
				if(lstagent.getAgentHoCode().equalsIgnoreCase(getIcashAgent())){
					lstAgentsTTCheckStateSubAgent = lstagent;
					break;
				}
			}

			if(lstAgentsTTCheckStateSubAgent != null){
				// 1. if condition Third Party Arrangement '1' - state and Sub Agent required
				// 2. else if condition Third Party Arrangement '0' - No state and No Sub Agent
				if(lstAgentsTTCheckStateSubAgent.getThirdPartyArrangement().equalsIgnoreCase("1")){
					// required
					setIcashStateSubAgent(true);
				}else{
					// not required
					setIcashStateSubAgent(false);
				}
			}
		}
	}


	public void editBeneficaray(PersonalRemmitanceBeneficaryDataTable datatabledetails) {

		try{

			log.info("Exit into edit method ");
			//ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			//context.getSessionMap().put("datatabledetails", datatabledetails);

			//boolean doneSingleTransaction= beneficaryCreation.doneSingleTransaction(datatabledetails.getBeneficiaryAccountSeqId());

			HashMap <String,String> inputValues = new HashMap<String,String>();
			inputValues.put("P_BENE_MASTER_SEQ", datatabledetails.getBeneficaryMasterSeqId()==null ? "0" : datatabledetails.getBeneficaryMasterSeqId().toPlainString());
			inputValues.put("P_BENE_ACCOUNT_SEQ", datatabledetails.getBeneficiaryAccountSeqId()==null ? "0" : datatabledetails.getBeneficiaryAccountSeqId().toPlainString());
			inputValues.put("P_BENE_RELE_SEQ", datatabledetails.getBeneficiaryRelationShipSeqId()==null ? "0" : datatabledetails.getBeneficiaryRelationShipSeqId().toPlainString());
			inputValues.put("P_CUSTOMER_ID", datatabledetails.getCustomerId()==null ? "0" : datatabledetails.getCustomerId().toPlainString());

			String beneEditStatus=iPersonalRemittanceService.getBeneficiaryStatusForEdit(inputValues);

			if(beneEditStatus!=null)
			{
				/*if(beneEditStatus.equalsIgnoreCase("E5"))
				{

					if(datatabledetails.getStateId() != null && datatabledetails.getTelphoneNum() != null && datatabledetails.getBenificaryCountry() != null){
						String countryCode = generalService.getCountryCode(datatabledetails.getBenificaryCountry());
						if(countryCode != null && countryCode.equalsIgnoreCase(Constants.IND_CODE)){
							fetchAllRecordForNonTrnx(datatabledetails);
						}else{
							fetchFiveFieldsEdit(datatabledetails);
						}
					}else{
						fetchFiveFieldsEdit(datatabledetails);
					}

				}else if(beneEditStatus.equalsIgnoreCase("EC"))
				{
					fetchContactNumberEdit(datatabledetails);
				}else if(beneEditStatus.equalsIgnoreCase("EA"))
				{
					fetchAllRecordForNonTrnx(datatabledetails);
				}*/
				if(beneEditStatus.equalsIgnoreCase("EA"))
				{
					fetchAllRecordForNonTrnx(datatabledetails);
				}else
				{
					fetchContactNumberEdit(datatabledetails);
				}
			}

		}catch (AMGException ex) {
			log.info("Problem in Funtion :" + ex);
			setProcedureError(ex.getMessage());
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
		}catch (Exception e) {
			setProcedureError(e.getMessage());
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
		}

	}

	public void editDifferentService(PersonalRemmitanceBeneficaryDataTable datatabledetails) {
		log.info("Enter into editDifferentService method ");
		// check beneficiary account type, state , district id setted or not
		try{
			HashMap <String,String> inputValues = new HashMap<String,String>();
			inputValues.put("P_BENE_MASTER_SEQ", datatabledetails.getBeneficaryMasterSeqId()==null ? "0" : datatabledetails.getBeneficaryMasterSeqId().toPlainString());
			inputValues.put("P_BENE_ACCOUNT_SEQ", datatabledetails.getBeneficiaryAccountSeqId()==null ? "0" : datatabledetails.getBeneficiaryAccountSeqId().toPlainString());
			inputValues.put("P_CUSTOMER_ID", datatabledetails.getCustomerId()==null ? "0" : datatabledetails.getCustomerId().toPlainString());

			String beneEditStatus=iPersonalRemittanceService.getBeneficiaryStatusForEdit(inputValues);

			if(beneEditStatus!=null && beneEditStatus.equalsIgnoreCase("E5"))
			{
				// err msg acc type , state , district and city
				setExceptionMessage("Please Edit and Continue Different Service");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}else if(beneEditStatus.equalsIgnoreCase("EC"))
			{
				if(datatabledetails.getTelphoneNum() != null){
					ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
					BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean)appContext.getBean("beneficiaryCreationBean");
					//objectBene.clearFirstTime();
					objectBene.setBooenableAgentPanel(false);
					objectBene.setBooEnableBankChannelPanel(false);

					objectBene.setIdNumber(getIdNumber());
					objectBene.setSelectCard(getSelectCard());
					objectBene.setCustomerNo(getCustomerNo());
					objectBene.setCustomerrefno(getCustomerrefno());
					objectBene.setCustomerFullName(getCustomerFullName());
					objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);
					objectBene.setDataTableBeneObj(datatabledetails);
					objectBene.setDisabledServiceGroup(false);
					objectBene.setBeneAccEditOrCreate(true);
					objectBene.setBooDisbleChoseeBranchButton(true);

					objectBene.renderBeneficiaryAccountNavigation();
					
					BigDecimal currencyId = iPersonalRemittanceService.getCurrencyId(objectBene.getBenifisCountryId());
					objectBene.setBenifisCurrencyId(currencyId);

					try {
						context.redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
					} catch (Exception e) {
						System.out.println("Exception occured" + e);
					}
				}else{
					// err msg contact number
					setExceptionMessage("Please Edit and Continue Different Service");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}else {
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean)appContext.getBean("beneficiaryCreationBean");
				//objectBene.clearFirstTime();
				objectBene.setBooenableAgentPanel(false);
				objectBene.setBooEnableBankChannelPanel(false);

				objectBene.setIdNumber(getIdNumber());
				objectBene.setSelectCard(getSelectCard());
				objectBene.setCustomerNo(getCustomerNo());
				objectBene.setCustomerrefno(getCustomerrefno());
				objectBene.setCustomerFullName(getCustomerFullName());
				objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);
				objectBene.setDataTableBeneObj(datatabledetails);
				objectBene.setDisabledServiceGroup(false);
				objectBene.setBeneAccEditOrCreate(true);
				objectBene.setBooDisbleChoseeBranchButton(true);				

				objectBene.renderBeneficiaryAccountNavigation();
				
				BigDecimal currencyId = iPersonalRemittanceService.getCurrencyId(objectBene.getBenifisCountryId());
				objectBene.setBenifisCurrencyId(currencyId);

				try {
					context.redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
				} catch (Exception e) {
					System.out.println("Exception occured" + e);
				}
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
		log.info("Exit into editDifferentService method ");

	}

	public void editDifferentBankAccount(PersonalRemmitanceBeneficaryDataTable datatabledetails) {
		log.info("Enter into editDifferentBankAccount method ");
		// check beneficiary account type, state , district id setted or not
		try{
			HashMap <String,String> inputValues = new HashMap<String,String>();
			inputValues.put("P_BENE_MASTER_SEQ", datatabledetails.getBeneficaryMasterSeqId()==null ? "0" : datatabledetails.getBeneficaryMasterSeqId().toPlainString());
			inputValues.put("P_BENE_ACCOUNT_SEQ", datatabledetails.getBeneficiaryAccountSeqId()==null ? "0" : datatabledetails.getBeneficiaryAccountSeqId().toPlainString());
			inputValues.put("P_CUSTOMER_ID", datatabledetails.getCustomerId()==null ? "0" : datatabledetails.getCustomerId().toPlainString());

			String beneEditStatus=iPersonalRemittanceService.getBeneficiaryStatusForEdit(inputValues);

			if(beneEditStatus!=null && beneEditStatus.equalsIgnoreCase("E5"))
			{
				// err msg acc type , state , district and city
				setExceptionMessage("Please Edit and Continue Different Account");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}else if(beneEditStatus.equalsIgnoreCase("EC"))
			{
				if(datatabledetails.getTelphoneNum() != null){
					ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
					BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean)appContext.getBean("beneficiaryCreationBean");
					//objectBene.clearFirstTime();
					objectBene.setBooenableAgentPanel(false);
					objectBene.setBooEnableBankChannelPanel(false);

					objectBene.setIdNumber(getIdNumber());
					objectBene.setSelectCard(getSelectCard());
					objectBene.setCustomerNo(getCustomerNo());
					objectBene.setCustomerrefno(getCustomerrefno());
					objectBene.setCustomerFullName(getCustomerFullName());
					objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);
					objectBene.setDataTableBeneObj(datatabledetails);
					objectBene.setDisabledServiceGroup(true);
					objectBene.setBeneAccEditOrCreate(true);
					objectBene.setBooDisbleChoseeBranchButton(true);

					objectBene.renderBeneficiaryAccountNavigation();
					
					BigDecimal currencyId = iPersonalRemittanceService.getCurrencyId(objectBene.getBenifisCountryId());
					objectBene.setBenifisCurrencyId(currencyId);

					try {
						context.redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
					} catch (Exception e) {
						System.out.println("Exception occured" + e);
					}
				}else{
					// err msg contact number
					setExceptionMessage("Please Edit and Continue Different Service");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}else
			{
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean)appContext.getBean("beneficiaryCreationBean");
				//objectBene.clearFirstTime();
				objectBene.setBooenableAgentPanel(false);
				objectBene.setBooEnableBankChannelPanel(false);

				objectBene.setIdNumber(getIdNumber());
				objectBene.setSelectCard(getSelectCard());
				objectBene.setCustomerNo(getCustomerNo());
				objectBene.setCustomerrefno(getCustomerrefno());
				objectBene.setCustomerFullName(getCustomerFullName());
				objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);
				objectBene.setDataTableBeneObj(datatabledetails);
				objectBene.setDisabledServiceGroup(true);
				objectBene.setBeneAccEditOrCreate(true);
				objectBene.setBooDisbleChoseeBranchButton(true);

				objectBene.renderBeneficiaryAccountNavigation();
				
				BigDecimal currencyId = iPersonalRemittanceService.getCurrencyId(objectBene.getBenifisCountryId());
				objectBene.setBenifisCurrencyId(currencyId);

				try {
					context.redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
				} catch (Exception e) {
					System.out.println("Exception occured" + e);
				}
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
		log.info("Exit into editDifferentBankAccount method ");		

	}


	/**
	 * : Added byRabil
	 */
	public void  getServiceListDetails(){

		//List<PopulateData> serviceList = iPersonalRemittanceService.getServiceList(sessionStateManage.getCountryId(),getBeneficaryBankId(), getBeneficaryBankBranchId(),getDatabenificarycountry(),getDatabenificarycurrency(),getServiceGroupCode());
		List<PopulateData> serviceList = iPersonalRemittanceService.getServiceList(sessionStateManage.getCountryId(),getBeneficaryBankId(), getBeneficaryBankBranchId(),getDataBankbenificarycountry(),getDatabenificarycurrency(),getServiceGroupCode());
		this.setServiceList(serviceList);

		if(serviceList!=null  && serviceList.size()>0){
			setBooMultipleService(true);
			setBooSingleService(false);
			setDataserviceid(serviceList.get(0).getPopulateId());
			setDatabenificaryservicecode(serviceList.get(0).getPopulateCode());
			countryNameByServiceId();
			//deliverylistByRemitId();
		}else{
			RequestContext.getCurrentInstance().execute("serviceNoData.show();");
			return;
		}

	}

	public void bankDetailsByCountry() {

		System.out.println("bankDetailsByCountry getDataserviceid bankDetailsByCountry :"+getDataserviceid());
		if (getRoutingCountry() != null) {

			System.out.println("Service name : " + getDatabenificaryservice());
			Boolean ttCheckSameNotAllow = false;

			if (getDatabenificaryservice().equalsIgnoreCase(Constants.TTNAME)) {
				ttCheckSameNotAllow = true;
			} else {
				ttCheckSameNotAllow = false;
			}

			List<BankMaster> lstRBank = generalService.getBankDetailsList(sessionStateManage.getLanguageId(),getRoutingCountry(),getRoutingBank());
			if (lstRBank != null && !lstRBank.isEmpty()) {
				BankMaster bankRec = lstRBank.get(0);
				setRoutingBankName(bankRec.getBankFullName());
				setDataroutingbankalphacode(bankRec.getBankCode());
			}

			List<PopulateData> lstRoutingBank = iPersonalRemittanceService.getRoutingBankList(sessionStateManage.getCountryId(),
					getBeneficaryBankId(), getBeneficaryBankBranchId(),
					//getDatabenificarycountry(),
					getDataBankbenificarycountry(),
					getDatabenificarycurrency(), getDataserviceid(),
					getRoutingCountry(), ttCheckSameNotAllow);
			if (lstRoutingBank.size() == 0) {
				clearICashProduct();
				setBooSingleRoutingBank(true);
				setBooMultipleRoutingBank(false);
				setRoutingBankName(null);
				setRoutingBank(null);
				setDataroutingbankalphacode(null);
				RequestContext.getCurrentInstance().execute("routingBankNoData.show();");
				return;
			} else if (lstRoutingBank.size() == 1) {
				setBooSingleRoutingBank(true);
				setBooMultipleRoutingBank(false);
				setRoutingBank(lstRoutingBank.get(0).getPopulateId());
				setRoutingBankName(lstRoutingBank.get(0).getPopulateName());
				setDataroutingbankalphacode(lstRoutingBank.get(0).getPopulateCode());
				remittancelistByBankId();
			} else {
				clearICashProduct();
				setRoutingBankName(null);
				setRoutingBank(null);
				setDataroutingbankalphacode(null);
				setBooSingleRoutingBank(false);
				setBooMultipleRoutingBank(true);
				setRoutingbankvalues(lstRoutingBank);

				setRoutingBank(lstRoutingBank.get(0).getPopulateId());
				setRoutingBankName(lstRoutingBank.get(0).getPopulateName());
				setDataroutingbankalphacode(lstRoutingBank.get(0).getPopulateCode());
				remittancelistByBankId();
			}

		}

	}

	public void bankBranchByBankView() {
		if (getRoutingBank() != null) {

			List<PopulateData> lstRoutingBankBranch = iPersonalRemittanceService
					.getRoutingBranchList(sessionStateManage.getCountryId(),
							getBeneficaryBankId(), getBeneficaryBankBranchId(),
							//getDatabenificarycountry(),
							getDataBankbenificarycountry(),
							getDatabenificarycurrency(), getDataserviceid(),
							getRoutingCountry(), getRoutingBank(),
							getRemitMode(), getDeliveryMode());

			if (lstRoutingBankBranch.size() == 0) {
				clearICashProduct();
				setBooSingleRoutingBranch(true);
				setBooMultipleRoutingBranch(false);
				setRoutingBranchName(null);
				setRoutingBranch(null);
			} else if (lstRoutingBankBranch.size() == 1) {
				setBooSingleRoutingBranch(true);
				setBooMultipleRoutingBranch(false);
				setRoutingBranch(lstRoutingBankBranch.get(0).getPopulateId());
				setRoutingBranchName(lstRoutingBankBranch.get(0).getPopulateName());
				// checking with ICASH PRODUCT
				fetchICashAgent();

			} else {
				clearICashProduct();
				setRoutingBranchName(null);
				setRoutingBranch(null);
				setBooSingleRoutingBranch(false);
				setBooMultipleRoutingBranch(true);
				setRoutingBankBranchlst(lstRoutingBankBranch);

				setRoutingBranch(lstRoutingBankBranch.get(0).getPopulateId());
				setRoutingBranchName(lstRoutingBankBranch.get(0).getPopulateName());
				// checking with ICASH PRODUCT
				fetchICashAgent();
			}

		}

	}

	public void deliverylistByRemitId() {

		List<PopulateData> lstDeliveryView = iPersonalRemittanceService
				.getDeliveryListByCountryBankRemit(sessionStateManage.getCountryId(),getBeneficaryBankId(),getBeneficaryBankBranchId(),
						//getDatabenificarycountry(),
						getDataBankbenificarycountry(),
						getDatabenificarycurrency(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRemitMode(),
						new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));

		if (lstDeliveryView.size() == 0) {
			clearICashProduct();
			setBooRenderDeliveryModeInputPanel(true);
			setBooRenderDeliveryModeDDPanel(false);
			setDeliveryModeInput(null);
			setDeliveryMode(null);
			setRoutingBranchName(null);
			setRoutingBranch(null);
			RequestContext.getCurrentInstance().execute("DeliveryNoData.show();");
			return;
		} else if (lstDeliveryView.size() == 1) {
			setBooRenderDeliveryModeInputPanel(true);
			setBooRenderDeliveryModeDDPanel(false);
			setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
			setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
			bankBranchByBankView();
		} else {
			clearICashProduct();
			setDeliveryModeInput(null);
			setDeliveryMode(null);
			setRoutingBranchName(null);
			setRoutingBranch(null);
			setBooRenderDeliveryModeInputPanel(false);
			setBooRenderDeliveryModeDDPanel(true);
			setLstofDelivery(lstDeliveryView);

			setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
			setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
			bankBranchByBankView();
		}
	}

	public void remittancelistByBankId() {
		// spl pool based on routing country , routing bank
		specialRequestFcAmountCalculation();

		List<PopulateData> lstRemitView = iPersonalRemittanceService
				.getRemittanceListByCountryBank(sessionStateManage.getCountryId(),getBeneficaryBankId(),getBeneficaryBankBranchId(),
						//getDatabenificarycountry(),
						getDataBankbenificarycountry(),
						getDatabenificarycurrency(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),
						new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));

		if (lstRemitView.size() == 0) {
			clearICashProduct();
			setBooSingleRemit(true);
			setBooMultipleRemit(false);
			setRemittanceName(null);
			setRemitMode(null);
			RequestContext.getCurrentInstance().execute("remittanceNoData.show();");
			return;
		} else if (lstRemitView.size() == 1) {
			setBooSingleRemit(true);
			setBooMultipleRemit(false);
			setRemittanceName(lstRemitView.get(0).getPopulateName());
			setRemitMode(lstRemitView.get(0).getPopulateId());

			deliverylistByRemitId();
		} else {
			clearICashProduct();
			setRemittanceName(null);
			setRemitMode(null);
			setBooSingleRemit(false);
			setBooMultipleRemit(true);
			setLstofRemittance(lstRemitView);

			setRemitMode(lstRemitView.get(0).getPopulateId());
			setRemittanceName(lstRemitView.get(0).getPopulateName());
			deliverylistByRemitId();
		}

	}

	// To calculate Foreign Currency Amount
	public void specialRequestFcAmountCalculation() {
		if(personalRemittCalFCAmountDTList.size()!=0 && !personalRemittCalFCAmountDTList.isEmpty()){
			personalRemittCalFCAmountDTList.clear();
		}
		setRemitAmountSplCust(BigDecimal.ZERO);
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

	/**
	 * Added by Rabil .
	 */
	public void countryNameByServiceId(){

		if(getDataserviceid() != null){

			HashMap<BigDecimal, String> lstCountryAlphaCode = fetchAllCountryAlphaCode();

			//setDatabenificaryservice(serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"), getDataserviceid()).get(0).getLocalServiceDescription());
			List<ServiceMasterDesc> lstServiceMaster = serviceMasterService.LocalServiceDescriptionFromDB(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"), getDataserviceid());
			if(lstServiceMaster != null && !lstServiceMaster.isEmpty()){
				ServiceMasterDesc serviceMasterDetails = lstServiceMaster.get(0);
				setDatabenificaryservice(serviceMasterDetails.getLocalServiceDescription());
				setDatabenificaryservicecode(serviceMasterDetails.getServiceMaster().getServiceCode());
			}

			// fetch routing country from view
			List<PopulateData> lstRoutingCountry = iPersonalRemittanceService.getRoutingCountryList(sessionStateManage.getCountryId(),getBeneficaryBankId(),getBeneficaryBankBranchId(),
					//getDatabenificarycountry(),
					getDataBankbenificarycountry(),
					getDatabenificarycurrency(),getDataserviceid(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"));

			if (lstRoutingCountry.size() == 0) {

				clearICashProduct();

				setBooSingleRoutingCountry(true);
				setBooMultipleRoutingCountry(false);
				setRoutingCountryName(null);
				setDataroutingcountrycode(null);
				setRoutingCountry(null);
				setDataroutingcountryalphacode(null);
				RequestContext.getCurrentInstance().execute("routingCountryNoData.show();");
				return;
			} else if (lstRoutingCountry.size() == 1) {
				setBooSingleRoutingCountry(true);
				setBooMultipleRoutingCountry(false);
				setRoutingCountry(lstRoutingCountry.get(0).getPopulateId());
				setRoutingCountryName(lstRoutingCountry.get(0).getPopulateName());
				setDataroutingcountrycode(lstRoutingCountry.get(0).getPopulateCode());
				setDataroutingcountryalphacode(lstCountryAlphaCode.get(getRoutingCountry()));
				// setting routing bank Id from view
				bankDetailsByCountry();
			} else {

				clearICashProduct();

				setRoutingCountryName(null);
				setDataroutingcountrycode(null);
				setRoutingCountry(null);
				setDataroutingcountryalphacode(null);
				setBooSingleRoutingCountry(false);
				setBooMultipleRoutingCountry(true);
				setRoutingCountrylst(lstRoutingCountry);

				setRoutingCountry(lstRoutingCountry.get(0).getPopulateId());
				setRoutingCountryName(lstRoutingCountry.get(0).getPopulateName());
				setDataroutingcountrycode(lstRoutingCountry.get(0).getPopulateCode());
				setDataroutingcountryalphacode(lstCountryAlphaCode.get(getRoutingCountry()));
				// setting routing bank Id from view
				bankDetailsByCountry();

			}
		}

	}

	public void countryNamebyCountryId() {

		HashMap<BigDecimal, String> lstCountryAlphaCode = fetchAllCountryAlphaCode();

		for (PopulateData bankname : getRoutingCountrylst()) {
			if (bankname.getPopulateId().compareTo(getRoutingCountry()) == 0) {
				setRoutingCountryName(bankname.getPopulateName());
				setDataroutingcountrycode(generalService.getCountryCode(getRoutingCountry()));
			}
		}

		setDataroutingcountryalphacode(lstCountryAlphaCode.get(getRoutingCountry()));

		bankDetailsByCountry();
	}

	public void BankNamebyBankId() {
		for (PopulateData bankname : getRoutingbankvalues()) {
			if (bankname.getPopulateId().compareTo(getRoutingBank()) == 0) {
				setRoutingBankName(bankname.getPopulateName());
				setDataroutingbankalphacode(bankname.getPopulateCode());
			}
		}
		remittancelistByBankId();
	}

	public void deliveryNamebydeliveryId() {
		for (PopulateData deliveryname : getLstofDelivery()) {
			if (deliveryname.getPopulateId().compareTo(getDeliveryMode()) == 0) {
				setDeliveryModeInput(deliveryname.getPopulateName());
			}
		}
		/*if (!getDatabenificaryservicegroup().equalsIgnoreCase(Constants.CASHNAME))
		{
			bankBranchByBankView();
		}*/

		bankBranchByBankView();

	}


	public void remittanceNamebyremitId() {
		for (PopulateData remitname : getLstofRemittance()) {
			if (remitname.getPopulateId().compareTo(getRemitMode()) == 0) {
				setRemittanceName(remitname.getPopulateName());
			}
		}
		if (getDatabenificaryservicegroup().equalsIgnoreCase(Constants.CASHNAME))
		{
			deliverylistByRemitIdForCash();
		}else
		{
			deliverylistByRemitId();
		}

	}


	// to get routing bank branch name
	public void routingbranchNamebyroutingBankBranchId() {
		for (PopulateData branchname : routingBankBranchlst) {
			if (branchname.getPopulateId().compareTo(getRoutingBranch()) == 0) {
				setRoutingBranchName(branchname.getPopulateName());
			}
		}

		// checking with ICASH PRODUCT
		fetchICashAgent();
	}

	// special customer deal for the Routing bank
	public void changeSpotRate(PersonalRemittanceCalFCAmountDataTable personalRemObj) {

		Boolean checkRateMatch = false;
		if (personalRemObj.getSelect()) {

			if (selectedSplDeal.size() != 0) {

				for (PersonalRemittanceCalFCAmountDataTable lstSpl : selectedSplDeal) {
					if (lstSpl.getRate().compareTo(personalRemObj.getRate()) == 0) {
						checkRateMatch = true;
						break;
					} else {
						checkRateMatch = false;
					}
				}

				if (checkRateMatch) {
					setSpecialDealRate(personalRemObj.getRate());
					setCountSplCust(getCountSplCust().add(BigDecimal.ONE));
					personalRemObj.setPercentageAddedAmount(personalRemObj.getAmount());
					selectedSplDeal.add(personalRemObj);
					personalRemObj.setBooRenderInputFCAmount(false);
				} else {
					personalRemObj.setSelect(false);
					RequestContext.getCurrentInstance().execute("ratematcherr.show();");
					personalRemObj.setPercentageAddedAmount(BigDecimal.ZERO);
					personalRemObj.setBooRenderInputFCAmount(true);
				}

			} else {
				setSpecialDealRate(personalRemObj.getRate());
				setCountSplCust(getCountSplCust().add(BigDecimal.ONE));
				personalRemObj.setPercentageAddedAmount(personalRemObj.getAmount());
				selectedSplDeal.add(personalRemObj);
				personalRemObj.setBooRenderInputFCAmount(false);
			}

		} else {
			personalRemObj.setBooRenderInputFCAmount(true);
			setCountSplCust(getCountSplCust().subtract(BigDecimal.ONE));
			personalRemObj.setPercentageAddedAmount(BigDecimal.ZERO);
			if (selectedSplDeal.size() != 0) {
				for (int i = 0; i < selectedSplDeal.size(); i++) {
					PersonalRemittanceCalFCAmountDataTable lstSpl = selectedSplDeal.get(i);
					if (personalRemObj.getSpecialCustomerPrimaryKey().compareTo(lstSpl.getSpecialCustomerPrimaryKey()) == 0) {
						selectedSplDeal.remove(i);
					}
				}
			}
		}

		if (getCountSplCust().compareTo(BigDecimal.ZERO) == 0) {
			setSpecialRateRef(Constants.No);
			setSpecialDealRate(null);
			setCurrency(new BigDecimal(sessionStateManage.getCurrencyId()));
			setBooReadOnlyRemitAmount(false);
			setDisableSpotRatePanel(false);
			setRemitAmountSplCust(BigDecimal.ZERO);
			setAmountToRemit(BigDecimal.ZERO);
		} else {
			setSpecialRateRef(Constants.Yes);
			setCurrency(personalRemObj.getCurrencyId());
			setBooReadOnlyRemitAmount(true);
			setDisableSpotRatePanel(true);
			setDisableSpotRatePanel(true);
			BigDecimal totalSplDealAmount = BigDecimal.ZERO;
			for (PersonalRemittanceCalFCAmountDataTable lstSplDeal : personalRemittCalFCAmountDTList) {
				if(lstSplDeal.getSelect()){
					if(lstSplDeal.getPercentageAddedAmount().compareTo(BigDecimal.ZERO)==0){
						totalSplDealAmount = totalSplDealAmount.add(lstSplDeal.getAmount());
					}else{
						totalSplDealAmount = totalSplDealAmount.add(lstSplDeal.getPercentageAddedAmount());
					}
				}
			}
			setAmountToRemit(totalSplDealAmount);
		}
	}

	// adding amount if percentage amount modified.
	public void editForSpecialCustomerDealFCAmount(PersonalRemittanceCalFCAmountDataTable personalRemObj){
		// need to add or substract fc amount based on parameter percentage
		BigDecimal totalSplDealAmount = BigDecimal.ZERO;
		if (personalRemObj.getSelect()) {
			if(personalRemObj.getPercentageAddedAmount().compareTo(BigDecimal.ZERO)>0){
				if(personalRemObj.getPercentageAddedAmount().compareTo(personalRemObj.getPercentageParameterAmount())<=0){
					if(personalRemittCalFCAmountDTList.size()!=0){
						for (PersonalRemittanceCalFCAmountDataTable lstSplDeal : personalRemittCalFCAmountDTList) {
							if(lstSplDeal.getSelect()){
								if(lstSplDeal.getPercentageAddedAmount().compareTo(BigDecimal.ZERO)==0){
									totalSplDealAmount = totalSplDealAmount.add(lstSplDeal.getAmount());
								}else{
									totalSplDealAmount = totalSplDealAmount.add(lstSplDeal.getPercentageAddedAmount());
								}
							}
						}
						setAmountToRemit(totalSplDealAmount);
					}
				}else{
					personalRemObj.setSelect(false);
					personalRemObj.setPercentageAddedAmount(BigDecimal.ZERO);
					if(personalRemittCalFCAmountDTList.size()!=0){
						for (PersonalRemittanceCalFCAmountDataTable lstSplDeal : personalRemittCalFCAmountDTList) {
							if(lstSplDeal.getSelect()){
								if(lstSplDeal.getPercentageAddedAmount().compareTo(BigDecimal.ZERO)==0){
									totalSplDealAmount = totalSplDealAmount.add(lstSplDeal.getAmount());
								}else{
									totalSplDealAmount = totalSplDealAmount.add(lstSplDeal.getPercentageAddedAmount());
								}
							}
						}
						setAmountToRemit(totalSplDealAmount);
					}
					setExceptionMessage(personalRemObj.getPercentageParameterErrorMsg());
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}else{
				if(personalRemittCalFCAmountDTList.size()!=0){
					for (PersonalRemittanceCalFCAmountDataTable lstSplDeal : personalRemittCalFCAmountDTList) {
						if(lstSplDeal.getSelect()){
							if(lstSplDeal.getPercentageAddedAmount().compareTo(BigDecimal.ZERO)==0){
								totalSplDealAmount = totalSplDealAmount.add(lstSplDeal.getAmount());
							}else{
								totalSplDealAmount = totalSplDealAmount.add(lstSplDeal.getPercentageAddedAmount());
							}
						}
					}
					setAmountToRemit(totalSplDealAmount);
				}
			}
		}
	}





	public void emailSend() {

		try{
			setFocus(false);
			if(getAmountToRemit()!=null){
				if (getSpotRate().equals(Constants.Yes) && getRemitMode() != null && getDeliveryMode() != null ) {

					SimpleDateFormat formate = new SimpleDateFormat("dd-MM-yyyy");
					String currentDate = formate.format(new Date());


					//List<SpecialRateRequest> spotRateValue = iSpecialRateRequest.fetchSpotRateRecords(getCustomerNo(), currentDate, getMasterId(),getBeneficaryBankId());
					List<RatePlaceOrder> spotRateValue = iPlaceOnOrderCreationService.fetchSpotRateRecords(getCustomerNo(), new Date(), getMasterId(),getBeneficaryBankId(),getBeneficiaryAccountSeqId(),getDataservicegroupid(),getDatabenificarycountry(),getForiegnCurrency(),getAmountToRemit(),getCustomerrefno(),getDataAccountnum(), new BigDecimal(sessionStateManage.getBranchId()));
					log.info("size while select yes/no==="+spotRateValue.size()+"===" +getAmountToRemit() );
					if (spotRateValue.size() != 0) {
						if (spotRateValue.get(0).getRateOffered() == null) {
							if(spotRateValue.get(0).getTransactionAmount().compareTo(getAmountToRemit())==0){
								log.info("inside11111111111");
								setSpotExchangeRate(null);
								setSpotExchangeRatePk(null);
								setMarqueeRender(true);
								setNextRender(false);
								setBooRenderPoll(true);

							}else{
								log.info("inside222222222");
								if(getCurrency().compareTo(getForiegnCurrency())==0)
								{
									//iSpecialRateRequest.updateSpecialRateRequest(spotRateValue.get( 0).getSpecialRateRequestId(), getAmountToRemit());
									iPlaceOnOrderCreationService.updateSpecialRateRequest(spotRateValue.get( 0).getRatePlaceOrderId(), getAmountToRemit());
								}else
								{
									HashMap<String, String> outPutValues = getRemitEquivalentAmtForSpecialRate();

									String remitAmount=outPutValues.get("P_EQUIV_GROSS_AMOUNT");
									//iSpecialRateRequest.updateSpecialRateRequest(spotRateValue.get( 0).getSpecialRateRequestId(),new BigDecimal(remitAmount));
									iPlaceOnOrderCreationService.updateSpecialRateRequest(spotRateValue.get( 0).getRatePlaceOrderId(),new BigDecimal(remitAmount));
								}

								setSpotExchangeRate(null);
								setSpotExchangeRatePk(null);
								setMarqueeRender(true);
								setNextRender(false);
								setBooRenderPoll(true);
							}
						} else {
							log.info("inside33333333333");
							//List<SpecialRateRequest> spotRateValues = iSpecialRateRequest.fetchSpotRateRecords(getCustomerNo(), currentDate, getMasterId(),getBeneficaryBankId());
							List<RatePlaceOrder> spotRateValues =iPlaceOnOrderCreationService.fetchSpotRateRecords(getCustomerNo(), new Date(), getMasterId(),getBeneficaryBankId(),getBeneficiaryAccountSeqId(),getDataservicegroupid(),getDatabenificarycountry(),getForiegnCurrency(),getAmountToRemit(),getCustomerrefno(),getDataAccountnum() , new BigDecimal(sessionStateManage.getBranchId()));
							if(spotRateValues.size()>0){
								log.info("insideelseblock===="+getAmountToRemit());

								if(getCurrency().compareTo(getForiegnCurrency())==0)
								{
									//iSpecialRateRequest.updateSpecialRateRequest(spotRateValue.get( 0).getSpecialRateRequestId(), getAmountToRemit());
									iPlaceOnOrderCreationService.updateSpecialRateRequest(spotRateValue.get( 0).getRatePlaceOrderId(), getAmountToRemit());
								}else
								{
									HashMap<String, String> outPutValues = getRemitEquivalentAmtForSpecialRate();

									String remitAmount=outPutValues.get("P_EQUIV_GROSS_AMOUNT");
									//iSpecialRateRequest.updateSpecialRateRequest(spotRateValue.get( 0).getSpecialRateRequestId(),new BigDecimal(remitAmount));
									iPlaceOnOrderCreationService.updateSpecialRateRequest(spotRateValue.get( 0).getRatePlaceOrderId(),new BigDecimal(remitAmount));
								}


							}
							setSpotExchangeRate(spotRateValue.get(0).getRateOffered());
							setSpotExchangeRatePk(spotRateValue.get(0).getRatePlaceOrderId());
							setNextRender(false);
							setMarqueeRender(true);
							setBooRenderPoll(true);
						}
					} else {
						setMarqueeRender(true);
						setNextRender(false);
						setBooRenderPoll(true);

						if(getCurrency().compareTo(getForiegnCurrency())==0)
						{
							HashMap<String, String> outPutValues= new HashMap<String, String>();
							outPutValues.put("P_EQUIV_CURRENCY_ID",getForiegnCurrency() == null ? "0" : getForiegnCurrency().toPlainString());
							outPutValues.put("P_EQUIV_GROSS_AMOUNT", getAmountToRemit() == null ? "0" : getAmountToRemit().toPlainString());
							saveRemittanceInfoSpecialRate(outPutValues);
						}else
						{
							HashMap<String, String> outPutValues = getRemitEquivalentAmtForSpecialRate();
							saveRemittanceInfoSpecialRate(outPutValues);

						}

						//count = count + 1;
						String roleId1 = sessionStateManage.getRoleId();
						String sender = sessionStateManage.getUserName();
						String branchId = sessionStateManage.getBranchId();
						String employeeid = sessionStateManage.getEmployeeId().toString();
						String telephoneno = sessionStateManage.getTelephoneNumber().trim();
						String email = sessionStateManage.getEmail();
						System.out.println(telephoneno);
						int roleId = Integer.parseInt(roleId1);
						List<Employee> empList = iEmployeeService.getEmployees();
						for (Employee emp : empList) {
							if (emp.getFsRoleMaster().getRoleId().intValue() == roleId && emp.getEmployeeId().toString().equalsIgnoreCase(employeeid)) {
								int countryBranchId = emp.getFsCountryBranch().getCountryBranchId().intValue();
								List<Employee> newlist = iEmployeeService.getEmployees();
								ListIterator<Employee> le = newlist.listIterator();
								while (le.hasNext()) {
									Employee e1 = le.next();
									if (e1.getFsCountryBranch().getCountryBranchId().intValue() == countryBranchId && e1.getFsRoleMaster().getRoleId().intValue() < roleId)
										if ((e1.getFsCountryBranch().getCountryBranchId().intValue() == countryBranchId) && (e1.getFsRoleMaster().getRoleId().intValue() == roleId - 1)) {
											System.out.println(e1.getFsRoleMaster().getRoleId());
											String employeename = e1.getEmployeeName();
											if(e1.getEmail() != null){
												mailService.sendMailToCustomer(email,e1.getEmail(), "welcome");
											}
										}
								}
							}
						}
					}

				} else {
					setSpotExchangeRate(null);
					setSpotExchangeRatePk(null);
					setSpotRate(Constants.No);
					setMarqueeRender(false);
					setNextRender(true);
					setBooRenderPoll(false);
				}	

			}else{
				RequestContext.getCurrentInstance().execute("needRemittanceAmount.show();");
				setSpotRate(Constants.No);
				return;
			}

		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	public void updateSpotrateRecord(){
		if(getSpotRate() != null && getSpotRate().equalsIgnoreCase(Constants.Yes)){
			SimpleDateFormat formate = new SimpleDateFormat("dd-MM-YY");
			String currentDate = formate.format(new Date());
			BigDecimal remitAmt=BigDecimal.ZERO;
			if(getCurrency().compareTo(getForiegnCurrency())==0)
			{
				remitAmt= getAmountToRemit();
			}else
			{
				try {
					HashMap<String, String> outPutValues = getRemitEquivalentAmtForSpecialRate();
					String remitAmount=outPutValues.get("P_EQUIV_GROSS_AMOUNT");
					remitAmt= new BigDecimal(remitAmount);
				} catch (AMGException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			//List<SpecialRateRequest> spotRateValue = iSpecialRateRequest.getActiveSpecialRateRequest(getCustomerNo(), remitAmt,currentDate, getMasterId(),getBeneficaryBankId());
			List<RatePlaceOrder> spotRateValue = iPlaceOnOrderCreationService.getActiveSpecialRateRequest(getCustomerNo(), new Date(), getMasterId(),getBeneficaryBankId(),getBeneficiaryAccountSeqId(),getDataservicegroupid(),getDatabenificarycountry(),getForiegnCurrency(),remitAmt,getCustomerrefno(),getDataAccountnum(), new BigDecimal(sessionStateManage.getBranchId()));
			log.info("spotrateamount=="+getAmountToRemit()+""+spotRateValue.size());
			if(spotRateValue.size()==0){
				//List<SpecialRateRequest> spotRateValues = iSpecialRateRequest.fetchSpotRateRecords(getCustomerNo(), currentDate, getMasterId(),getBeneficaryBankId());
				List<RatePlaceOrder> spotRateValues = iPlaceOnOrderCreationService.fetchSpotRateRecords(getCustomerNo(), new Date(), getMasterId(),getBeneficaryBankId(),getBeneficiaryAccountSeqId(),getDataservicegroupid(),getDatabenificarycountry(),getForiegnCurrency(),remitAmt,getCustomerrefno(),getDataAccountnum(), new BigDecimal(sessionStateManage.getBranchId()));
				if(spotRateValues.size()>0){ 
					RatePlaceOrder listValue=spotRateValues.get(0);
					setSpotRate(null);
					setSpotRateForDispay(listValue.getRateOffered());
					setRemittanceAmountForDisplay(listValue.getTransactionAmount());
					log.info("insideelseblock===="+getAmountToRemit());
					/*if(listValue.getRateOffered()!=null){
						log.info(" ======="+getSpotRate());
						RequestContext.getCurrentInstance().execute("spotrateback.show();");
					}*/
				}
			}
		}
	}

	public void clickYes(){
		SimpleDateFormat formate = new SimpleDateFormat("dd-MM-YY");
		String currentDate = formate.format(new Date());
		//List<SpecialRateRequest> spotRateValue = iSpecialRateRequest.getActiveSpecialRateRequest(getCustomerNo(), getAmountToRemit(),currentDate, getMasterId(),getBeneficaryBankId());
		List<RatePlaceOrder> spotRateValue = iPlaceOnOrderCreationService.getActiveSpecialRateRequest(getCustomerNo(), new Date(), getMasterId(),getBeneficaryBankId(),getBeneficiaryAccountSeqId(),getDataservicegroupid(),getDatabenificarycountry(),getForiegnCurrency(),getAmountToRemit(),getCustomerrefno(),getDataAccountnum(), new BigDecimal(sessionStateManage.getBranchId()));
		if(spotRateValue.size()>0){

			BigDecimal amount=null;
			//iSpecialRateRequest.updateSpecialRateRequest(spotRateValue.get(0).getSpecialRateRequestId(),amount );
			iPlaceOnOrderCreationService.updateSpecialRateRequest(spotRateValue.get(0).getRatePlaceOrderId(),amount);
			setSpotRate(Constants.No);
			setSpotExchangeRate(null);
			setSpotExchangeRatePk(null);
			setMarqueeRender(false);
			setNextRender(true);
			setBooRenderPoll(false);
		} 


	}


	// to save in spot rate data base if spot rate is "Y"
	/*public void saveRemittanceInfoSpecialRate(HashMap<String, String> outPutValues) {
		*//** START 29/01/2015 *//*
		List<SpecialRateRequest> lstofspot = new ArrayList<SpecialRateRequest>();
		*//** END 29/01/2015 *//*
		SpecialRateRequest specialReqRate = new SpecialRateRequest();
		*//** START 29/01/2015 *//*
		lstofspot = iPersonalRemittanceService.getDocumentSeriality();
		*//** END 29/01/2015 *//*
		specialReqRate.setApplicationCountryId(sessionStateManage.getCountryId());
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
		currencyMaster.setCurrencyId(new BigDecimal(outPutValues.get("P_EQUIV_CURRENCY_ID")));
		specialReqRate.setFsCurrencyMaster(currencyMaster);
		// to set company Id
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(sessionStateManage.getCompanyId());
		specialReqRate.setCompanyMaster(companyMaster);
		// to set User Financial Year
		UserFinancialYear userFinancialYear = new UserFinancialYear();
		userFinancialYear.setFinancialYearID(generalService.getDealYear(new Date()).get(0).getFinancialYearID());
		specialReqRate.setFsFinanceYear(userFinancialYear);
		// to set Document code
		Document document = new Document();
		BigDecimal docId = generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FC_SALE_APP),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")).get(0).getDocumentID();
		if(docId != null){
			document.setDocumentID(docId);
			specialReqRate.setFsDocument(document);
		}
		specialReqRate.setDocumentNo(new BigDecimal(lstofspot.size() + 1));
		specialReqRate.setFcAmount(new BigDecimal(outPutValues.get("P_EQUIV_GROSS_AMOUNT")));
		specialReqRate.setCreatedBy(sessionStateManage.getUserName());
		specialReqRate.setCreatedDate(new Date());
		specialReqRate.setIsActive(Constants.U);
		// recently added
		specialReqRate.setBeneficiaryId(getMasterId());
		specialReqRate.setBeneficiaryBankId(getBeneficaryBankId());
		specialReqRate.setBeneficiaryBankBranchId(getBeneficaryBankBranchId());
		specialReqRate.setBranchId(sessionStateManage.getCountryBranchCode());
		specialReqRate.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));

		iPersonalRemittanceService.saveSpecialRateRequest(specialReqRate);
	}*/

	public void refresh() {

		SimpleDateFormat formate = new SimpleDateFormat("dd-MM-YY");
		String currentDate = formate.format(new Date());
		System.out.println("current Date : " + currentDate);
		log.info( "CustomerNo==="+getCustomerNo());
		log.info( " getAmountToRemit()==="+ getAmountToRemit());
		log.info( "getMasterId(),==="+getMasterId());
		log.info( "getBeneficaryBankId()==="+getBeneficaryBankId());

		BigDecimal remitAmt=BigDecimal.ZERO;

		if(getCurrency().compareTo(getForiegnCurrency())==0)
		{
			remitAmt= getAmountToRemit();
		}else
		{

			try {
				HashMap<String, String> outPutValues = getRemitEquivalentAmtForSpecialRate();
				String remitAmount=outPutValues.get("P_EQUIV_GROSS_AMOUNT");
				remitAmt= new BigDecimal(remitAmount);
			} catch (AMGException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		//List<SpecialRateRequest> spotRateValue = iSpecialRateRequest.getActiveSpecialRateRequest(getCustomerNo(), remitAmt,currentDate, getMasterId(),getBeneficaryBankId());
		List<RatePlaceOrder> spotRateValue = iPlaceOnOrderCreationService.getActiveSpecialRateRequest(getCustomerNo(), new Date(), getMasterId(),getBeneficaryBankId(),getBeneficiaryAccountSeqId(),getDataservicegroupid(),getDatabenificarycountry(),getForiegnCurrency(),remitAmt,getCustomerrefno(),getDataAccountnum(), new BigDecimal(sessionStateManage.getBranchId()));
		log.info( "getBeneficaryBankId()==="+spotRateValue.size());
		if (spotRateValue.size() != 0) {
			if (spotRateValue.get(0).getRateOffered() == null) {
				setSpotExchangeRate(null);
				setSpotExchangeRatePk(null);
				setMarqueeRender(true);
				setNextRender(false);
				setBooRenderPoll(true);
				RequestContext.getCurrentInstance().execute("spotrate.show();");

			} else {
				log.info("else refresh");
				setSpotExchangeRate(spotRateValue.get(0).getRateOffered());
				setSpotExchangeRatePk(spotRateValue.get(0).getRatePlaceOrderId());
				setNextRender(true);
				setMarqueeRender(false);
				setBooRenderPoll(false);

			}
		}else {
			log.info("dialog refresh");
			setSpotExchangeRate(null);
			setSpotExchangeRatePk(null);
			//RequestContext.getCurrentInstance().execute("spotrate.show();");
		}

	}
	
	//save customer mobile number in fs_customer_mobile_log
	public void saveCustomerMobileDetails(){

		CustomerMobileLogModel customerMobileLog = new CustomerMobileLogModel();

		customerMobileLog.setCreatedBy(sessionStateManage.getUserName());
		customerMobileLog.setCreatedDate(new Date());
		customerMobileLog.setIsActive(Constants.Yes);
		customerMobileLog.setMobile(getDataCustomerTelephoneNumber());
		customerMobileLog.setCustomerId(getCustomerNo());
		customerMobileLog.setCustomerReference(getCustomerrefno());
		//customerMobileLog.setOtpNo();
		//customerMobileLog.setOtpRetry();
		//customerMobileLog.setOtpRetryDate();
		//customerMobileLog.setOtpVerifiedBy();
		//customerMobileLog.setOtpVerifiedDate();
		
		iPersonalRemittanceService.saveCustomerMobileLog(customerMobileLog);
	}

	// calling exchange rate procedure , additional bank details , checking spot rate , special rate multiple calls avaibale
	public void nextServiceSecondPanel(){
		setFocus(true);

		try{			
			//toCheckBeneAddressDetails();
			if(getAmountToRemit() != null){
				if(getBooRenderCorporateBack()){
					if(getDataTempCustomerMobile() != null && getDataTempCustomerMobile().equalsIgnoreCase(getDataCustomerTelephoneNumber())){
						System.out.println("equal");
					}else{
						System.out.println("difference");
						//saveCustomerMobileDetails();
					}
				}else{

					// checking customer telephone number modified or not
					if(getDataCustomerTelephoneNumber() != null && !getDataCustomerTelephoneNumber().equalsIgnoreCase("")){
						if(getDataCustomerContactId() != null){
							if(getDataTempCustomerMobile() != null && getDataTempCustomerMobile().equalsIgnoreCase(getDataCustomerTelephoneNumber())){
								System.out.println("equal");
							}else{
								System.out.println("difference");
								saveCustomerMobileDetails();
								if(getBooRenderOtpAuthPanel()){
									saveAuthorization();
								}
							}
						}else{
							String errString = "Customer Contact Details Not Available, Please Update Contact Details in Customer Registration";
							setExceptionMessage(WarningHandler.showWarningMessage(errString, sessionStateManage.getLanguageId()));
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
							return;
						}
					}else{
						setExceptionMessage(WarningHandler.showWarningMessage("lbl.custMobNumisMand", sessionStateManage.getLanguageId()));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
						return;
					}
				}

				if(getBenificaryTelephone() != null && !getBenificaryTelephone().equalsIgnoreCase("")){
					if(getDataTempBeneTelNum() != null && getDataTempBeneTelNum().compareTo(getBenificaryTelephone())==0){
						System.out.println("equal");
					}else{
						String errMsg = null;
						System.out.println("difference");
						//saving beneficiary telephone number
						if(getDataBeneContactId() != null){

							HashMap<String, Object> migrateBeneTelephone = new HashMap<String, Object>();

							migrateBeneTelephone.put("BeneMasterSeqId", getMasterId());
							migrateBeneTelephone.put("BeneBankId", getBeneficaryBankId());
							migrateBeneTelephone.put("BeneBankBranchId", getBeneficaryBankBranchId());
							migrateBeneTelephone.put("BeneAccountSeqId", getBeneficiaryAccountSeqId());
							migrateBeneTelephone.put("BeneCurrencyId", getDatabenificarycurrency());
							migrateBeneTelephone.put("BeneCustomerId", getCustomerNo());
							migrateBeneTelephone.put("BeneContactSeqId", getDataBeneContactId());
							migrateBeneTelephone.put("BeneContactTelephone", getBenificaryTelephone());

							errMsg = beneficaryCreation.saveUpdatedBeneTelePhoneNumber(migrateBeneTelephone);

							if(errMsg != null){
								setExceptionMessage(errMsg);
								RequestContext.getCurrentInstance().execute("alertmsg.show();");
								return;
							}
						}
					}
				}else{
					setExceptionMessage(WarningHandler.showWarningMessage("lbl.beneTelNumisMand", sessionStateManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					return;
				}

				// BLOCKED TO NOT REQ
				//updateSpotrateRecord();
				/*if(getSpecialApprovalRadio()==1 && getApprovalNo()==null){
					setExceptionMessage(WarningHandler.showWarningMessage("lbl.enterapprovalnumber", sessionStateManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					return;
				}*/

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
					if (personalRemittCalFCAmountDTList.size() != 0 && isBooSpecialCusFCCalDataTable()) {
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
			}else{
				setExceptionMessage(WarningHandler.showWarningMessage("lbl.enterremittanceamount", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}catch(AMGException e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;
		}
	}

	public void checkAvailableBalance(PersonalRemittanceCalFCAmountDataTable dataTableObj) {
		if (getAmountToRemit().intValue() != 0) {
			Date todayDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String sysDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
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
			if (date1.compareTo(date2) < 0 || date1.compareTo(date2) == 0) {
				//if (dataTableObj.getAmount().intValue() == 0 || dataTableObj.getAmount().intValue() < (getAmountToRemit().intValue())) {
				if (dataTableObj.getAmount().intValue() == 0 ) {
					RequestContext.getCurrentInstance().execute("balanceCheck.show();");
					setAmountToRemit(null);
					setBooReadOnlyRemitAmount(false);
					setDisableSpotRatePanel(false);
					specialRequestFcAmountCalculation();
					return;
				} 
			} else {
				RequestContext.getCurrentInstance().execute("expDate.show();");
				setAmountToRemit(null);
				setBooReadOnlyRemitAmount(false);
				setDisableSpotRatePanel(false);
				specialRequestFcAmountCalculation();
			}
		}
	}

	public void dynamicLevel() throws AMGException {
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

	public void matchData() {
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

							if(listAdditionaView != null && listAdditionaView.size()>0){

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
									setExceptionMessage(((getExceptionMessage() == null ? "" : getExceptionMessage()).equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
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
					/*if(dyamicLabel.getLebelDesc()!=null){
						adddata.setAdditionalDesc(dyamicLabel.getLebelDesc());
					}else{
						List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService.getDynamicLevelMatch(getRoutingCountry(),dyamicLabel.getFlexiField());
						if(listAdditinalBankfield.size()>0){
							if(listAdditinalBankfield.get(0).getFieldName() != null){
								adddata.setAdditionalDesc(listAdditinalBankfield.get(0).getFieldName());
							}else{
								setExceptionMessage(((getExceptionMessage() == null ? "" : getExceptionMessage()).equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
							}
						}else{
							setExceptionMessage(((getExceptionMessage() == null ? "" : getExceptionMessage()).equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
						}
					}*/
					
					if(getRoutingCountry() != null && dyamicLabel.getFlexiField() != null){
						List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService.getDynamicLevelMatch(getRoutingCountry(),dyamicLabel.getFlexiField());
						if(listAdditinalBankfield.size()>0){
							if(listAdditinalBankfield.get(0).getFieldName() != null){
								adddata.setAdditionalDesc(listAdditinalBankfield.get(0).getFieldName());
							}else{
								setExceptionMessage(((getExceptionMessage() == null ? "" : getExceptionMessage()).equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
							}
						}else{
							setExceptionMessage(((getExceptionMessage() == null ? "" : getExceptionMessage()).equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
						}
					}else{
						setExceptionMessage(((getExceptionMessage() == null ? "" : getExceptionMessage()).equalsIgnoreCase("") ? "" : ",")  + dyamicLabel.getFlexiField());
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

	public void gotoNextPanel() {
		getExchangeRatevalues();
	}

	// NEW
	// to call procedure and get values
	/*public void getExchangeRatevalues() {
		try {

			setBooRenderPoll(false);

			HashMap<String, String> inputValues = new HashMap<String, String>();

			inputValues.put("P_APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId() == null ? "0" : sessionStateManage.getCountryId().toString());
			inputValues.put("P_ROUTING_COUNTRY_ID", getRoutingCountry() == null ? "0" : getRoutingCountry().toString());
			inputValues.put("P_BRANCH_ID", sessionStateManage.getBranchId());
			inputValues.put("P_COMPANY_ID", sessionStateManage.getCompanyId() == null ? "0" : sessionStateManage.getCompanyId().toString());
			inputValues.put("P_ROUTING_BANK_ID", getRoutingBank() == null ? "0" : getRoutingBank().toString());
			inputValues.put("P_SERVICE_MASTER_ID", getDataserviceid() == null ? "0" : getDataserviceid().toString());
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
			//inputValues.put("P_BENE_COUNTRY_ID", getDatabenificarycountry() == null ? "0" : getDatabenificarycountry().toString()); //getDataBankbenificarycountry(),
			inputValues.put("P_BENE_COUNTRY_ID", getDataBankbenificarycountry() == null ? "0" : getDataBankbenificarycountry().toString());
			inputValues.put("P_BENE_BANK_ID", getBeneficaryBankId() == null ? "0" : getBeneficaryBankId().toString());
			inputValues.put("P_BENE_BANK_BRANCH_ID",getBeneficaryBankBranchId() == null ? "0" : getBeneficaryBankBranchId().toString());
			inputValues.put("P_BENE_ACCOUNT_NO",getDataAccountnum());
			//Added by Rabil on 09/03/2016
			inputValues.put("P_APPROVAL_YEAR",getApprovalYear()==null?"0":getApprovalYear().toString());
			inputValues.put("P_APPROVAL_NO",getApprovalNo()==null?"0":getApprovalNo().toString());
			inputValues.put("P_USER_TYPE", Constants.C);
			inputValues.put("P_PAYMENT_MODE", null); // Kiosk Purpose
			inputValues.put("P_AMOUNT_UPDOWN", null);// Kiosk Purpose

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
					if(outputValues.get("P_ICASH_COST_RATE") != null && !outputValues.get("P_ICASH_COST_RATE").equalsIgnoreCase("0")){
						setIcashCostRate(new BigDecimal(outputValues.get("P_ICASH_COST_RATE")));
					}else{
						setIcashCostRate(null);
					}

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
								if (isBooRenderDelivery() == true || isBooRenderRemit() == true) {
									RequestContext.getCurrentInstance().execute("remitDeliveryModifiedRecords.show();");
								}
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
	}*/

	// OLD 
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
			inputValues.put("P_SERVICE_MASTER_ID", getDataserviceid() == null ? "0" : getDataserviceid().toString());
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
			//inputValues.put("P_BENE_COUNTRY_ID", getDatabenificarycountry() == null ? "0" : getDatabenificarycountry().toString()); //getDataBankbenificarycountry(),
			inputValues.put("P_BENE_COUNTRY_ID", getDataBankbenificarycountry() == null ? "0" : getDataBankbenificarycountry().toString());
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
					if(outputValues.get("P_ICASH_COST_RATE") != null && !outputValues.get("P_ICASH_COST_RATE").equalsIgnoreCase("0")){
						setIcashCostRate(new BigDecimal(outputValues.get("P_ICASH_COST_RATE")));
					}else{
						setIcashCostRate(null);
					}
					
					/** Added by Rabil for AMRB and Loyalty Amount should not be greater than local commission amount */
					
					BigDecimal localCommimount = new BigDecimal(outputValues.get("P_LOCAL_COMMISION_AMOUNT"));
					BigDecimal totalVoucherAmount =BigDecimal.ZERO;
					 
					if(getLoyaltyAmountAvailed()!=null && getAmtbCouponAmount()!=null){
						totalVoucherAmount = getLoyaltyAmountAvailed().add(getAmtbCouponAmount());
					}
					
					
					if(localCommimount.compareTo(totalVoucherAmount)<0){
						setExceptionMessage("Voucher and Coupon amount should not be greater than commission amount ");
						RequestContext.getCurrentInstance().execute("sqlexception.show();");
						return;
					}
					

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
								}else{
									// clearing all swift data if junk data available
									if(lstSwiftMasterBank1 != null || !lstSwiftMasterBank1.isEmpty()){
										lstSwiftMasterBank1.clear();
									}
									if(lstSwiftMasterBank2 != null || !lstSwiftMasterBank2.isEmpty()){
										lstSwiftMasterBank2.clear();
									}
									setBeneSwiftBank1(null);
									setBeneSwiftBank2(null);
									setSwiftId1(null);
									setSwiftId2(null);
									setBeneSwiftBankAddr1(null);
									setBeneSwiftBankAddr2(null);
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
	}

	/**
	 * 
	 * @return : Added for Additional check through Procedeure.
	 */

	public String getAdditionalCheckProcedure() {
		String errorMessage = "";
		String additionalCheckMessage = null;
		try {
			additionalCheckMessage = iPersonalRemittanceService.getAdditionalCheckProcedure(sessionStateManage.getCountryId(),getCustomerNo(),
					new BigDecimal(sessionStateManage.getBranchId()),getMasterId(),
					//getDatabenificarycountry(),
					getDataBankbenificarycountry(),
					getBeneficaryBankId(),
					getBeneficaryBankBranchId(),getDataAccountnum(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRoutingBranch(),
					getRemitMode(),getDeliveryMode(),getSourceOfIncome(),getExchangeRate(),
					new BigDecimal(sessionStateManage.getCurrencyId()), // localCommisionCurrencyId
					getCommission(),new BigDecimal(sessionStateManage.getCurrencyId()), // localChargeCurrencyId
					getOverseasamt(),new BigDecimal(sessionStateManage.getCurrencyId()), // localDelivCurrencyId
					getGrossAmountCalculated(),new BigDecimal(0),// serviceProvider e.g -->InsantCast etc,
					getDatabenificarycurrency(),getNetAmountSent(),new BigDecimal(sessionStateManage.getCurrencyId()), // localNetCurrecnyId,
					getNetAmountPayable(),getSwiftId1()==null ? "": getSwiftId1().toPlainString(), getSwiftId2()==null? "" : getSwiftId2().toPlainString(),// beneSwiftBank1,beneSwiftBank2,
							errorMessage);
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}

		System.out.println("additionalCheckMessage :" + additionalCheckMessage);

		return additionalCheckMessage;
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

				if(checkData.get("BNFADDR1").equalsIgnoreCase(Constants.Yes)){
					setBooRenderBeneAddreddDetails(true);
				}else{
					setBooRenderBeneAddreddDetails(false);
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
		setSwiftId1(null);
		setSwiftId2(null);
		setBeneSwiftBankAddr1(null);
		setBeneSwiftBankAddr2(null);
		// List<SwiftMaster> lstSwiftRecords =
		// iswiftMasterService.toFetchAllActiveRecords();
		//List<PopulateData> lstSwiftRecords = iPersonalRemittanceService.fetchingViewSwiftMasterByCountryId(getDatabenificarycountry());
		List<PopulateData> lstSwiftRecords = iPersonalRemittanceService.fetchingViewSwiftMasterByCountryId(getDataBankbenificarycountry());

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

	public void fetchBeneSwiftBankName1() {
		if (lstSwiftMasterBank1!=null && lstSwiftMasterBank1.size() != 0) {
			if (getSwiftId1() != null) {
				for (PopulateData lstSwiftBank1 : lstSwiftMasterBank1) {

					if (lstSwiftBank1.getPopulateId().compareTo(getSwiftId1()) == 0) {
						String[] parts = lstSwiftBank1.getPopulateName().split("-");
						String string1 = parts[0];
						String string2 = parts[1];
						String swiftBankAddr = string2;
						setBeneSwiftBankAddr1(swiftBankAddr);
					} else {
						if (getSwiftId2() != null) {

							if (getSwiftId1().compareTo(getSwiftId2()) == 0) {
								lstSwiftMasterBank2.clear();
								setBeneSwiftBank2(null);
								setBeneSwiftBankAddr2(null);
							}

						}
						lstSwiftMasterBank2.add(lstSwiftBank1);
					}

				}
			} else {
				setBeneSwiftBankAddr1(null);
			}
		}
	}

	public void fetchBeneSwiftBankName2() {
		setBeneSwiftBankAddr2(null);
		if (lstSwiftMasterBank2!=null && lstSwiftMasterBank2.size() != 0) {
			if (getSwiftId2() != null) {
				for (PopulateData lstSwiftBank2 : lstSwiftMasterBank2) {
					if (lstSwiftBank2.getPopulateId().compareTo(getSwiftId2()) == 0) {
						String[] parts = lstSwiftBank2.getPopulateName().split("-");
						String string1 = parts[0];
						String string2 = parts[1];
						String swiftBankAddr = string2;
						setBeneSwiftBankAddr2(swiftBankAddr);
					} else {
						if (getSwiftId1() != null) {

							if (getSwiftId1().compareTo(getSwiftId2()) == 0) {
								lstSwiftMasterBank1.clear();
								setBeneSwiftBank1(null);
								setBeneSwiftBankAddr1(null);
							}

						}

						lstSwiftMasterBank1.add(lstSwiftBank2);
					}
				}
			} else {
				setBeneSwiftBankAddr2(null);
			}
		}
	}

	public String checkingFurtherInstructionWithProcedure() {
		String furtherInstrMsg = null;
		try {
			furtherInstrMsg = iPersonalRemittanceService.toFtechPurtherInstractionErrorMessaage(sessionStateManage.getCountryId(), getRoutingCountry(),getRoutingBank(),
					getForiegnCurrency(), // new BigDecimal(sessionmanage.getCurrencyId()) , //CurrencyID
					getRemitMode(), getDeliveryMode(),
					getFurthuerInstructions(),getDataBankbenificarycountry());
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}
		return furtherInstrMsg;
	}

	/*public void backToServiceFirstPanel() {
		
		if(isBooRenderPlaceOrder()){
			BranchStaffGSMRateBeen branchStaffGSMRateBeen = (BranchStaffGSMRateBeen) appContext.getBean("branchStaffGSMRateBean");
			branchStaffGSMRateBeen.pageNavigationBranchStaff();
			setBooRenderPlaceOrder(false);
			setBooRenderPlaceOrderByRemit(false);
			setRatePlaceOrderPk(null);
		}else if(isBooRenderPlaceOrderByRemit()){
			BranchStaffGSMRateBeen branchStaffGSMRateBeen = (BranchStaffGSMRateBeen) appContext.getBean("branchStaffGSMRateBean");
			branchStaffGSMRateBeen.pageNavigationFromPersonalRemitt(getCustomerNo(),getIdNumber(),getSelectCard());
			setBooRenderPlaceOrder(false);
			setBooRenderPlaceOrderByRemit(false);
			setRatePlaceOrderPk(null);
		}else{
			setBooRenderPlaceOrder(false);
			setBooRenderPlaceOrderByRemit(false);
			setRatePlaceOrderPk(null);
			if (personalRemittCalFCAmountDTList.size() != 0) {
				setBooSpecialCusFCCalDataTable(true);
			} else {
				setBooSpecialCusFCCalDataTable(false);
			}
			getServiceFirstPanel();
		}
		
	}*/
	
	public void backToServiceFirstPanel() {
		if (personalRemittCalFCAmountDTList.size() != 0) {
			setBooSpecialCusFCCalDataTable(true);
		} else {
			setBooSpecialCusFCCalDataTable(false);
		}
		getServiceFirstPanel();
	}


	// checking whether Signature required for Cooperate Users
	public void renderDigitalSignatureBasedOnBranch(){

		Boolean corsSigIndCheck =  iPersonalRemittanceService.checkCorporateBranchForSignature(new BigDecimal(sessionStateManage.getBranchId()));
		if(corsSigIndCheck || sessionStateManage.getUserType().equalsIgnoreCase(Constants.E)){
			setRenderCustomerSignatureCheck(false);
		}else{
			setRenderCustomerSignatureCheck(true);
		}
	}

	public void booAmlCheck() {
		checkProExp = false;
		saveCount = 0;
		//updateBeneMaster();
		if(getBooRenderBeneAddreddDetails())
		{
			boolean mandCheck=toCheckMandatroyFields();
			if(!mandCheck){

				return;
			}
		}

		//Check Signature render if Branch is corporate  hide signature else show
		renderDigitalSignatureBasedOnBranch();


		if(getAdditionalCheck()){
			setExceptionMessage(null);
			String furtherInstrn = checkingFurtherInstructionWithProcedure();
			System.out.println("furtherInstrn :"+furtherInstrn+" exception Message :"+getExceptionMessage());

			//furtherInstrn ="";
			//if (furtherInstrn != null && !furtherInstrn.equalsIgnoreCase("")) {
			if (furtherInstrn != null && furtherInstrn.length()>0) {
				setExceptionMessage("EX_P_FURTHER_INSTR" + " : " +furtherInstrn);
				System.out.println("if booAmlCheck furtherInstrn :"+furtherInstrn+" exception Message :"+getExceptionMessage()); 
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
				return;
			} else {
				System.out.println("else  booAmlCheck furtherInstrn :checkingSwiftCodeWithProcedure exception Message :"+getExceptionMessage()); 
				HashMap<String, String> beneSwiftBank1Details = checkingSwiftCodeWithProcedure(getSwiftId1()==null ?"" :getSwiftId1().toPlainString(), Constants.BENEFICIARY_SWIFT_BANK1);

				if (beneSwiftBank1Details != null) {
					if (beneSwiftBank1Details.get("P_ERROR_MESSAGE") != null && !beneSwiftBank1Details.get("P_ERROR_MESSAGE").equalsIgnoreCase("")) {

						System.out.println("if (beneSwiftBank1Details  booAmlCheck furtherInstrn :checkingSwiftCodeWithProcedure exception Message :"+getExceptionMessage()); 
						setExceptionMessage("EX_P_CHECK_SWIFT_BANK" + " : " +beneSwiftBank1Details.get("P_ERROR_MESSAGE"));
						RequestContext.getCurrentInstance().execute("sqlexception.show();");
						return;
					} else {

						HashMap<String, String> beneSwiftBank2Details = checkingSwiftCodeWithProcedure(getSwiftId2()==null ?"" : getSwiftId2().toPlainString(),Constants.BENEFICIARY_SWIFT_BANK2);
						if (beneSwiftBank2Details != null) {
							if (beneSwiftBank2Details.get("P_ERROR_MESSAGE") != null && !beneSwiftBank2Details.get("P_ERROR_MESSAGE").equalsIgnoreCase("")) {

								System.out.println("if (beneSwiftBank2Details.  booAmlCheck furtherInstrn :checkingSwiftCodeWithProcedure exception Message :"+getExceptionMessage()); 
								setExceptionMessage("EX_P_CHECK_SWIFT_BANK" + " : " +beneSwiftBank2Details.get("P_ERROR_MESSAGE"));
								RequestContext.getCurrentInstance().execute("sqlexception.show();");
								return;
							} else {

								HashMap<String, String> bankIndicatorProcedureCheck = bankIndicatorsProcedureCheck();

								if(bankIndicatorProcedureCheck != null){

									if(bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE1") != null && !bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE1").equalsIgnoreCase("") ||
											bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE2") != null && !bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE2").equalsIgnoreCase("") ||
											bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE3") != null && !bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE3").equalsIgnoreCase("") ||
											bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE4") != null && !bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE4").equalsIgnoreCase("") ||
											bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE5") != null && !bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE5").equalsIgnoreCase("")){

										setExceptionMessage("EX_P_BANK_INDICATORS" + " : " +bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE1") == null ? "" : bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE1").concat(bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE2") == null ? "" : bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE2")) 
												.concat(bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE3") == null ? "" : bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE3")).concat(bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE4") == null ? "" : bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE4"))
												.concat(bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE5") == null ? "" : bankIndicatorProcedureCheck.get("P_ERROR_MESSAGE5")).trim() );
										RequestContext.getCurrentInstance().execute("sqlexception.show();");
										return;
									}else{
										booAmlCheckProcess();
									}
								}else{
									// procedure returning null - PROCEDURE "EX_P_BANK_INDICATORS " FAIL Condition
								}
							}
						}
					}
				}
			}
		}else{
			if(getExceptionMessage() != null  && !getExceptionMessage().equalsIgnoreCase("")){
				setExceptionMessage(getExceptionMessage());
				RequestContext.getCurrentInstance().execute("dataexception.show();");
			}else{
				RequestContext.getCurrentInstance().execute("additionaldatanot.show();");
			}
		}
	}


	Map<String,String> amlAuthuTYpe= new HashMap<String,String>();
	Map<String,String> amlAuthuTYpeLog= new HashMap<String,String>();

	public void booAmlCheckProcess(){

		setIsHaveAmlCheck(false);
		setFromAMLCheck(true);
		allPanelOff();
		almcheckList.clear();
		amlAuthuTYpe.clear();
		amlAuthuTYpeLog.clear();
		// clear All setted values
		setMessageThree(null);
		setAmlMessageThree(null);
		setAmlMessageOne(null);
		setMessageOne(null);
		setAmlMessageTwo(null);
		setMessageTwo(null);
		setMessageFour(null);
		setAmlMessageFour(null);

		setBooMessageOne(false);
		setBooMessageTwo(false);
		setBooMessageThree(false);
		setBooMessageFour(false);

		List<BenificiaryListView> isCoustomerExist = beneficaryCreation.getBeneficaryList(getCustomerNo());
		if (isCoustomerExist.size() > 0) {
			beneCountr = isCoustomerExist.get(0).getBenificaryCountry();
			BigDecimal masterSeqId = isCoustomerExist.get(0).getBeneficaryMasterSeqId();
			try {
				HashMap<String, String> list = iPersonalRemittanceService.isAmlTranxAmountCheckForRemittance(sessionStateManage.getCountryId(),beneCountr,getCustomerNo(),masterSeqId,getNetAmountPayable());
				if (list != null && !list.isEmpty()) {
					if (list.get("MESSAGE1").equalsIgnoreCase("") && list.get("MESSAGE2").equalsIgnoreCase("") && list.get("MESSAGE3").equalsIgnoreCase("") && list.get("MESSAGE4").equalsIgnoreCase("")) {
						setIsHaveAmlCheck(false);
						setAmlboomsg(false);
						setAmlboo(false);
					} else {

						setAmlboomsg(true);
						setAmlboo(true);

						if (list.get("MESSAGE1") != null && list.get("MESSAGE1")!="")
						{
							setAmlMessageOne(list.get("MESSAGE1"));
							setMessageOne(list.get("MESSAGE1"));
						}
						if (list.get("MESSAGE2") != null && list.get("MESSAGE2")!="")
						{
							setAmlMessageTwo(list.get("MESSAGE2"));
							setMessageTwo(list.get("MESSAGE2"));
						}
						if (list.get("MESSAGE3") != null && list.get("MESSAGE3")!="" ) {
							setMessageThree(list.get("MESSAGE3"));
							setAmlMessageThree(list.get("MESSAGE3"));
						} 
						if (list.get("MESSAGE4") != null && list.get("MESSAGE4")!="" ) {
							setMessageFour(list.get("MESSAGE4"));
							setAmlMessageFour(list.get("MESSAGE4"));
						} 

						setIsHaveAmlCheck(true);
						almcheckList.clear();
						if (list.get("MESSAGE3") != null && list.get("MESSAGE3")!="" ) {
							CustomerAlmTrasactionCheckProcedure almcheckSinglerow = new CustomerAlmTrasactionCheckProcedure();
							//almcheckSinglerow.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(),getDatabenificarycountry()));
							almcheckSinglerow.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(),getDataBankbenificarycountry()));

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
							amlAuthuTYpeLog.put("AUTHTYPE1", list.get("AUTHTYPE1").toString());
						}
						if(list.get("AUTHTYPE2") != null && list.get("AUTHTYPE2") != "")
						{
							amlAuthuTYpe.put((list.get("AUTHTYPE2").toString()),getAmlMessageTwo());
							amlAuthuTYpeLog.put("AUTHTYPE2", list.get("AUTHTYPE2").toString());
						}

						if(list.get("AUTHTYPE3") != null && list.get("AUTHTYPE3") != "")
						{
							amlAuthuTYpe.put((list.get("AUTHTYPE3").toString()),getAmlMessageThree());
							amlAuthuTYpeLog.put("AUTHTYPE3", list.get("AUTHTYPE3").toString());
						}

						if(list.get("AUTHTYPE4") != null && list.get("AUTHTYPE4") != "")
						{
							amlAuthuTYpe.put((list.get("AUTHTYPE4").toString()),getAmlMessageFour());
							amlAuthuTYpeLog.put("AUTHTYPE4", list.get("AUTHTYPE4").toString());
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

		slb1total1 = 0.0;
		slb1total2 = 0.0;
		slb1total3 = 0.0;
		slb1total4 = 0.0;
		slb1total5 = 0.0;
		slb1total6 = 0.0;


		if(getAmlMessageOne() != null ||  getAmlMessageTwo() != null || getAmlMessageThree() != null || getAmlMessageFour() != null)
		{
			List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
			setEmpllist(localEmpllist);
		}

		/*		if (getMessageOne() != null && getMessageTwo() != null) {
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
		}*/

		if (getMessageOne() != null || getMessageTwo() != null || getMessageThree()!=null || getMessageFour()!=null ) {

			if(getMessageOne() != null)
			{
				setBooMessageOne(true);
			}			
			if (getMessageTwo() != null)
			{
				setBooMessageTwo(true);
			}
			if (getMessageThree() != null) 
			{
				setBooMessageThree(true);
			}
			if (getMessageFour() != null) 
			{
				setBooMessageFour(true);
			}
		} else {
			setAmlboomsg(false);
			setAmlboo(false);
			setFromAMLCheck(false);
			
			if(getCustomerType() != null){
				if(getCustomerType().equalsIgnoreCase(Constants.NonIndividual)){
					// no check
				}else{
					// customer mobile number changed need to ask OTP
					checkCustomerMobileNumberModified();
				}
			}
			
			
			setBooRenderCustomerSignaturePanel(true);
			customerSignatureSpecimen();
		}

	}

	public HashMap<String, String> checkingSwiftCodeWithProcedure(String beneSwiftBank, String fieldName) {
		HashMap<String, String> swiftBankDetails = null;
		try {

			swiftBankDetails = iPersonalRemittanceService.toFtechSwiftBankProcedure(sessionStateManage.getCountryId(),
					getRoutingCountry(), getForiegnCurrency(), // new BigDecimal(sessionmanage.getCurrencyId()) //CurrencyID
					getRemitMode(), getDeliveryMode(), fieldName, // fieldName
					beneSwiftBank,getDataBankbenificarycountry());

		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}

		return swiftBankDetails;
	}

	public HashMap<String, String> bankIndicatorsProcedureCheck(){

		HashMap<String, String> bankIndicatorOutValues = null;
		try {

			HashMap<String, String> bankIndicatorInValues = new HashMap<String, String>();

			bankIndicatorInValues.put("P_APPLICATION_COUNTRY_ID", (sessionStateManage.getCountryId()).toString());
			bankIndicatorInValues.put("P_ROUTING_COUNTRY_ID", getRoutingCountry().toString());
			bankIndicatorInValues.put("P_CURRENCY_ID", getForiegnCurrency().toString());
			bankIndicatorInValues.put("P_ROUTING_BANK_ID", getRoutingBank().toString());
			bankIndicatorInValues.put("P_REMITTANCE_MODE_ID", getRemitMode().toString());
			bankIndicatorInValues.put("P_DELIVERY_MODE_ID", getDeliveryMode().toString());
			bankIndicatorInValues.put("P_BENE_BANK_COUNTRY", getDataBankbenificarycountry().toString());

			bankIndicatorOutValues = iPersonalRemittanceService.exPBankIndicatorsProcedureCheck(bankIndicatorInValues,listAdditionalBankDataTable);

		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}

		return bankIndicatorOutValues;
	}

	public void customerSignatureSpecimen() {
		List<CustomerIdProof> data = foreignCurrencyPurchaseService.dataCust(getIdNumber());
		if (data != null && data.size() > 0) {
			try {
				//setSignatureSpecimen(data.get(0).getFsCustomer().getSignatureSpecimenClob().getSubString(1,(int) data.get(0).getFsCustomer().getSignatureSpecimenClob().length()));
				if(data.get(0).getFsCustomer().getSignatureSpecimenClob() != null){
					setSignatureSpecimen(data.get(0).getFsCustomer().getSignatureSpecimenClob().getSubString(1,(int) data.get(0).getFsCustomer().getSignatureSpecimenClob().length()));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public void nextAmlCheckToCustomerSignature() {
		checkProExp = false;
		saveCount = 0;
		String userName = getAmlAuthorizedBy().trim();
		String password = getAmlAuthorizedPassword();

		//Check Signature render if Branch is corporate  hide signature else show
		renderDigitalSignatureBasedOnBranch();

		List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();
		/*String userNames = getAmlAuthorizedBy();
		String userName1 = new StringBuffer(userNames).reverse().toString().toUpperCase();*/
		/*	lstEmpLogin.addAll(loginService.getLoginInfoForEmployees(sessionStateManage.getCountryId(), userName,cypherSecurity.encodePassword(password, userName1)));*/

		lstEmpLogin = 	iPersonalRemittanceService.getdebitAutendicationListByUserId(userName,password);

		if (lstEmpLogin.size() == 1) {
		
			allPanelOff();
			
			if(getCustomerType() != null){
				if(getCustomerType().equalsIgnoreCase(Constants.NonIndividual)){
					// no check
				}else{
					// customer mobile number changed need to ask OTP
					checkCustomerMobileNumberModified();
				}
			}
			
			setBooRenderCustomerSignaturePanel(true);
			customerSignatureSpecimen();

		} else {
			RequestContext.getCurrentInstance().execute("inValidLogin.show()");
			return;
		}

	}

	/**
	 * Document Seriality
	 */
	public String getDocumentSerialID(String processIn) {
		System.out.println("Start getDocumentSerialID ...." + getFinaceYear());

		String documentSerialId="";

		log.info("process in :" + processIn);
		try {
			HashMap<String, String> outPutValues= generalService.getNextDocumentRefNumber(Integer.parseInt(sessionStateManage.getSessionValue("countryId")), Integer.parseInt(sessionStateManage.getSessionValue("companyId")), 
					Integer.parseInt(Constants.DOCUMENT_CODE_FOR_REMITTANCE_APPLICATION
							.trim()), getFinaceYear().intValue(), processIn,sessionStateManage.getCountryBranchCode());

			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setExceptionMessage(proceErrorMsg);
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}else {
				documentSerialId=outPutValues.get("DOCNO");
			}
		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
		return documentSerialId;

	}

	public void backFromSignaturePanel() {

		if (getIsHaveAmlCheck()) {
			allPanelOff();
			setAmlboo(true);
			setAmlboomsg(true);
			//setBooMessageThree(true);
		} else {
			booAmlCheckBack();
		}

	}


	// fields to stop multiple saving option
	int saveCount = 0;
	Boolean checkProExp = false;


	public void saveApplicationTransaction() {

		if (getRenderCustomerSignatureCheck().equals(true)){
			if(getDigitalSignature() != null && !getDigitalSignature().equalsIgnoreCase("")) {
				setBooRenderSignatureMsg(false);
				setSignatureMandatoryMsg(null);
				
				Boolean checkPin = checkPinNumberBeforeSave();
				if(checkPin){
					saveRemittanceApplication(saveCount);
				}
			} else {
				setBooRenderSignatureMsg(true);
				setSignatureMandatoryMsg("Please Sign and Continue");
			}
		}else{
			setBooRenderSignatureMsg(false);
			setSignatureMandatoryMsg(null);
			Boolean checkPin = checkPinNumberBeforeSave();
			if(checkPin){
				saveRemittanceApplication(saveCount);
			}
		}

	}

	// saving data to Remittance Application
	public void saveRemittanceApplication(int saveCount) {

		try {

			String documentNo = getDocumentSerialID(Constants.Yes);

			if(documentNo != null && !documentNo.equalsIgnoreCase("")){
				if(saveCount==0){

					if(getBooRenderBeneAddreddDetails())
					{
						updateBeneMaster();
					}

					// start 28/01/2015
					setDocumentNo(documentNo);
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



					List<Document> lstDocument = generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FC_SALE_APP),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
					if (lstDocument !=null && lstDocument.size() != 0) {
						Document documentid = new Document();
						documentid.setDocumentID(lstDocument.get(0).getDocumentID());
						remittanceApplication.setExDocument(documentid);
						
						remittanceApplication.setDocumentCode(lstDocument.get(0).getDocumentCode());
					}

					// company Id
					CompanyMaster companymaster = new CompanyMaster();
					companymaster.setCompanyId(sessionStateManage.getCompanyId());
					remittanceApplication.setFsCompanyMaster(companymaster);
					// User Financial Year for Document
					UserFinancialYear docuserfinancialyear = new UserFinancialYear();
					docuserfinancialyear.setFinancialYearID(generalService.getDealYear(new Date()).get(0).getFinancialYearID());
					remittanceApplication.setExUserFinancialYearByDocumentFinanceYear(docuserfinancialyear);
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
					remittanceApplication.setEmployeeId(sessionStateManage.getEmployeeId());
					
					remittanceApplication.setApplInd(Constants.C);
					remittanceApplication.setLoccod(sessionStateManage.getCountryBranchCode());
					
					// company code
					BigDecimal companyCode = null;
					List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice.viewById(sessionStateManage.getCompanyId());
					if(lstcompanymaster.size() != 0){
						CompanyMasterDesc companycode = lstcompanymaster.get(0);
						if(companycode.getFsCompanyMaster().getCompanyCode() != null){
							companyCode = companycode.getFsCompanyMaster().getCompanyCode();
						}
					}
					remittanceApplication.setCompanyCode(companyCode);
					
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
					}

					if (getDigitalSignature() != null) {
						try {
							remittanceApplication.setCustomerSignatureClob(stringToClob(getDigitalSignature()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					remittanceApplication.setInstruction(getFurthuerInstructions());
					remittanceApplication.setApprovalYear(getApprovalYear());
					remittanceApplication.setApprovalNumber(getApprovalNo());
                    //Added by Rabil on 05/04/2016 For testing purposer I puted this value ,actul set the value which is returned from  procedure.
					remittanceApplication.setAmtbCouponEncashed(getAmtbCouponAmount()==null?new BigDecimal("0"):getAmtbCouponAmount());

					// ICASH PRODUCT
					if(getIcashAgent() != null){

						remittanceApplication.setAgentCode(getIcashAgent());

						if(isIcashEFT()){
							remittanceApplication.setModeofTransfer(Constants.ModeofTransferEFT);  // D - EFT - DIRECT 
						}else if(isIcashTT()){
							remittanceApplication.setModeofTransfer(Constants.ModeofTransferTT);   // I - TT  - INDIRECT
						}else if(isIcashCash()){
							remittanceApplication.setModeofTransfer(Constants.ModeofTransferEFT);  // D - EFT - DIRECT
						}else{
							// no values of Agents
						}

						// sub agent
						if(getIcashSubAgent() != null){
							remittanceApplication.setSubAgentCode(getIcashSubAgent());
						}

						// usd amt and srv Prv settle rate
						if(getIcashCostRate() != null){
							String decimalvalue = Constants.THREE; // no of decimal "3" for USD Amount
							remittanceApplication.setUsdAmt(getNetAmountSent().divide(getIcashCostRate(),Integer.parseInt(decimalvalue),BigDecimal.ROUND_HALF_UP));
							remittanceApplication.setSrvPrvSettleRate(getIcashCostRate());
						}

					}

					HashMap<String, Object> mapAllDetailPersonalRemittanceApplSave = new HashMap<String, Object>();


					// Document Id
					if (getFromAMLCheck()) {

						/*AuthorizedLog authorizedLog = new AuthorizedLog();

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

						iPersonalRemittanceService.saveAuthorizedLog(authorizedLog);*/
						List<AuthorizedLog> lstAuthorizedLog=getAuthLogList();

						mapAllDetailPersonalRemittanceApplSave.put("AMLLOGSAVE", lstAuthorizedLog);


					}
					
					HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
					String hostname = loginBean.getClientIpAddress(request);
					
					remittanceApplication.setTerminalAddress(hostname);

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

					// customer mobile number modification will done fs_customer , fs_customer_contact_details
					// checking customer telephone number modified or not
					if(getDataCustomerTelephoneNumber() != null && getDataCustomerContactId() != null){
						if(getDataTempCustomerMobile() != null && getDataTempCustomerMobile().equalsIgnoreCase(getDataCustomerTelephoneNumber())){
							System.out.println("equal");
							// no save required for customer mobile number
							mapAllDetailPersonalRemittanceApplSave.put("Cust_Mobile_Change", Constants.No);
						}else{
							System.out.println("difference");
							// save only any difference in mobile number
							mapAllDetailPersonalRemittanceApplSave.put("Cust_Mobile_Change", Constants.Yes);
							mapAllDetailPersonalRemittanceApplSave.put("Cust_Mobile_No", getDataCustomerTelephoneNumber());
							mapAllDetailPersonalRemittanceApplSave.put("Cust_Contact_Id", getDataCustomerContactId());
							mapAllDetailPersonalRemittanceApplSave.put("Customer_Id", getCustomerNo());
							mapAllDetailPersonalRemittanceApplSave.put("Customer_Reference", getCustomerrefno());
							mapAllDetailPersonalRemittanceApplSave.put("OTP_NO", getOtpNo());
							mapAllDetailPersonalRemittanceApplSave.put("Authorized_By", getOtpAuthorizedBy());
							mapAllDetailPersonalRemittanceApplSave.put("Authorized_Remarks", getOtpRemarks());
						}
					}
					
					/** Added by Rabil on 3rd April 2017 to Store AMTB Coopon Table */
					if(getAmtbCoupon()!=null){
						AmtbCoupon amtbApplCoupon = saveAMTBCoupon(remittanceApplication);
						mapAllDetailPersonalRemittanceApplSave.put("EX_AMTB_COUPON",amtbApplCoupon);
					}
					/** end  by Rabil on 3rd April 2017 to Store AMTB Coopon Table */
					
					
					//place Order update
					RatePlaceOrder placeOrderDt = saveRatePlaceOrder();
					mapAllDetailPersonalRemittanceApplSave.put("EX_RATE_PLACE_ORDER", placeOrderDt);

					iPersonalRemittanceService.saveAllApplTransaction(mapAllDetailPersonalRemittanceApplSave);
					// updatingUtilizedAmount();
					assignNullValues();
					checkProExp = true;
					saveCount = saveCount+1;
					RequestContext.getCurrentInstance().execute("anil.show();");
				}
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

		/*if(checkProExp){
			RequestContext.getCurrentInstance().execute("remittanceaAppSave.show();");
		}*/

	}
	
	// save Place Order details in Ex_Rate_Place_Order
	public RatePlaceOrder saveRatePlaceOrder(){
		
		RatePlaceOrder ratePlaceOrder = new RatePlaceOrder();
		if(getRatePlaceOrderPk() != null){
			ratePlaceOrder.setRatePlaceOrderId(getRatePlaceOrderPk());
			ratePlaceOrder.setAppointmentTime(new Date());
			ratePlaceOrder.setSourceOfincomeId(getSourceOfIncome());
			ratePlaceOrder.setServiceMasterId(getDataserviceid());
			ratePlaceOrder.setRemittanceModeId(getRemitMode());
			ratePlaceOrder.setDeliveryModeId(getDeliveryMode());
			ratePlaceOrder.setRoutingBranchId(getRoutingBranch());
			//ratePlaceOrder.setCollectionMode(getPaymentmodeId());
		}

		return ratePlaceOrder;
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

			if (getSwiftId1() != null) {
				// remittanceAppBenificary.setBeneficiarySwiftBank1(getSwiftId1().toString());
				remittanceAppBenificary.setBeneficiarySwiftBank1(null);
				remittanceAppBenificary.setBeneficiarySwiftBank1Id(getSwiftId1());

			}
			if (getSwiftId2() != null) {
				// remittanceAppBenificary.setBeneficiarySwiftBank2(getSwiftId2().toString());
				remittanceAppBenificary.setBeneficiarySwiftBank2(null);
				remittanceAppBenificary.setBeneficiarySwiftBank2Id(getSwiftId2());
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
			if(getSwiftBic()!=null){
				remittanceAppBenificary.setBeneficiaryBankSwift(getSwiftBic());
			}

			//remittanceAppBenificary.setBeneficiaryBankCountryId(getDatabenificarycountry());
			remittanceAppBenificary.setBeneficiaryBankCountryId(getDataBankbenificarycountry());
			remittanceAppBenificary.setBeneficiaryBank(getDatabenificarybankname());
			remittanceAppBenificary.setBeneficiaryBranch(getDatabenificarybranchname());
			remittanceAppBenificary.setBeneficiaryBankId(getBeneficaryBankId());
			remittanceAppBenificary.setBeneficiaryBankBranchId(getBeneficaryBankBranchId());
			remittanceAppBenificary.setBeneficiaryBranchStateId(getBeneStateId());
			remittanceAppBenificary.setBeneficiaryBranchDistrictId(getBeneDistrictId());
			remittanceAppBenificary.setBeneficiaryBranchCityId(getBeneCityId());
			remittanceAppBenificary.setBeneficiaryAccountSeqId(getBeneficiaryAccountSeqId());
			remittanceAppBenificary.setBeneficiaryRelationShipSeqId(getBeneficiaryRelationShipSeqId());
			remittanceAppBenificary.setBeneficiaryTelephoneNumber(getBenificaryTelephone());

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
					bencountrymaster.setCountryId(sessionStateManage.getCountryId());
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

	/** Added by Rabil */
	
	public AmtbCoupon saveAMTBCoupon(RemittanceApplication remittanceApplication){
		AmtbCoupon amtbCoupon = new AmtbCoupon();
		amtbCoupon.setApplicationCountryId(sessionStateManage.getCountryId());
		amtbCoupon.setCompanyId(sessionStateManage.getCompanyId());
		amtbCoupon.setCivilId(getIdNumber());
		amtbCoupon.setCouponNo(getAmtbCoupon());
		amtbCoupon.setApplDocumentId(remittanceApplication.getExDocument().getDocumentID());
		amtbCoupon.setApplDocumentCode(remittanceApplication.getDocumentCode());
		amtbCoupon.setApplDocumentNo(remittanceApplication.getDocumentNo());
		amtbCoupon.setApplDocumentFinancialyear(remittanceApplication.getDocumentFinancialyear());
		amtbCoupon.setDocumentFinancialyear(getAmtbCouponFinancialYear());
		amtbCoupon.setCreatedDate(new Date());
	    return amtbCoupon;
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

	/**
	 * * @author : Rabil
	 */

	public java.sql.Clob stringToClob(String source) throws Exception {
		try {
			return new javax.sql.rowset.serial.SerialClob(source.toCharArray());
		} catch (Exception e) {
			return null;
		}
	}

	public void generatePersonalRemittanceApplicationReport(ShoppingCartDataTableBean shoppingCartDataTableBean){
		setVisableExceptionDailogForApplication(false);
		ServletOutputStream servletOutputStream=null;
		try{
			if(shoppingCartDataTableBean.getApplicationType().equalsIgnoreCase(Constants.Remittance)){
				fetchApplicationRemittanceReportData(shoppingCartDataTableBean.getDocumentNo());
				remittanceApplicationReportInit();
				String fileName = "RemittanceApplicationReport.pdf";
				printJasperDirectToPrinter(fileName);
				/*HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.addHeader("Content-disposition","attachment; filename=RemittanceApplicationReport.pdf");
				servletOutputStream = httpServletResponse.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();*/
			}else if(shoppingCartDataTableBean.getApplicationType().equalsIgnoreCase(Constants.FCSale)){
				generateNewUpdatedReports(shoppingCartDataTableBean.getCustomerId(),shoppingCartDataTableBean.getDocumentNo().toString());
			}
		}catch(Exception e){
			setExceptionMessage(null);
			setVisableExceptionDailogForApplication(true);
			if(e.getMessage()!=null){
				setExceptionMessage(e.getMessage());	
			}else{
				setExceptionMessage("Exception  :"+e);
			}
			RequestContext.getCurrentInstance().execute("exceptioninApplication.show();");
		}finally{
			if(servletOutputStream!=null){
				try {
					servletOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void printJasperDirectToPrinter(String fileName){
		try{
		JRExporter exporter = null;
		ByteArrayOutputStream out = null;
		ByteArrayInputStream input = null;
		BufferedOutputStream output = null; 

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		facesContext = FacesContext.getCurrentInstance();
		externalContext = facesContext.getExternalContext();
		response = (HttpServletResponse) externalContext.getResponse();

		out = new ByteArrayOutputStream(); 

		exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

		exporter.exportReport();

		input = new ByteArrayInputStream(out.toByteArray());

		response.reset();
		response.setHeader("Content-Type", "application/pdf");
		response.setHeader("Content-Length", String.valueOf(out.toByteArray().length));
		response.setHeader("Content-Disposition", "inline; filename="+fileName);
		output = new BufferedOutputStream(response.getOutputStream(), org.apache.jasper.Constants.DEFAULT_BUFFER_SIZE);
		byte[] buffer = new byte[org.apache.jasper.Constants.DEFAULT_BUFFER_SIZE];
		int length;
		while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}
		output.flush();
		facesContext.responseComplete();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public void closeExceptionApplicationDailog(){
		setVisableExceptionDailogForApplication(false);
	}


	public void generateNewUpdatedReports(BigDecimal customerId,String documentNo)throws Exception,IOException{
		ServletOutputStream servletOutputStream=null;
		try{
			generateReports(customerId,documentNo);
			init();
			String fileName = "ForeignCurrencySaleReport.pdf";
			printJasperDirectToPrinter(fileName);
			/*HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=ForeignCurrencySaleReport.pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();*/
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}finally{
			if(servletOutputStream!=null){
				servletOutputStream.close();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fcSaleReportDTOList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String reportPath = realPath +"\\reports\\design\\fcsalefinalReport.jasper"; //for window WF 

		String reportPath = realPath +"//reports//design//fcsalefinalReport.jasper";
		//		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/fcsalefinalReport.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
	}


	public void generateReports(BigDecimal customerId,String documentNo)throws Exception {
		fcSaleReportDTOList = new ArrayList<FcSaleReport>();

		String purchageCurrency = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));

		List<ForeignCurrencyAdjustApp> foreignCurrencyAdjustAppList = foreignCurrencyPurchaseService.getAllValuesForReportGenaration(customerId,documentNo, getFinaceYear());
		ForeignCurrencyAdjustApp foreignCurrencyAdjustApp = foreignCurrencyAdjustAppList.get(0);

		FcSaleReport fcSaleReport = new FcSaleReport();
		BigDecimal saleCurrencyId = foreignCurrencyAdjustApp.getFsCurrencyMaster().getCurrencyId();
		String saleCurrencyCode = generalService.getCurrencyQuote(saleCurrencyId);

		if(foreignCurrencyAdjustApp.getDocumentFinanceYear()!=0 && foreignCurrencyAdjustApp.getDocumentNo()!=0){
			fcSaleReport.setReceiptNo(foreignCurrencyAdjustApp.getDocumentFinanceYear()+" / "+foreignCurrencyAdjustApp.getDocumentNo());
		}else if(foreignCurrencyAdjustApp.getDocumentNo()!=0){
			fcSaleReport.setReceiptNo(foreignCurrencyAdjustApp.getDocumentNo()+"");
		}

		fcSaleReport.setLocation(sessionStateManage.getLocation());
		fcSaleReport.setCustomerName(foreignCurrencyAdjustApp.getFsCustomer().getFirstName());
		fcSaleReport.setTelephoneNo(foreignCurrencyAdjustApp.getFsCustomer().getMobile());
		List<CurrencyMaster> lstcontryCurr = generalService.getCountryCurrencyList(foreignCurrencyAdjustApp.getFsCountryMaster().getCountryId());
		fcSaleReport.setPurchageCurrency(lstcontryCurr.get(0).getCurrencyName());
		String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(foreignCurrencyAdjustApp.getFsCurrencyMaster().getCurrencyId());
		if(saleCurrency != null){
			fcSaleReport.setSaleCurrency(saleCurrency);
		}
		List<ReceiptPaymentApp> receiptPaymentAppList = foreignCurrencyPurchaseService.getReceiptPaymentForReportGeneration(new BigDecimal(documentNo));
		for (ReceiptPaymentApp receiptPaymentApp : receiptPaymentAppList) {


			if(receiptPaymentApp.getForignTrnxAmount()!=null && saleCurrencyId!=null && saleCurrencyCode!=null){
				BigDecimal saleAmount=GetRound.roundBigDecimal((receiptPaymentApp.getForignTrnxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(saleCurrencyId));
				fcSaleReport.setSaleAmount(saleCurrencyCode+"     ******"+saleAmount.toString());
			}

			if(receiptPaymentApp.getForignTrnxAmount()!=null && saleCurrencyId!=null && saleCurrencyCode!=null){
				BigDecimal totalSaleAmount=GetRound.roundBigDecimal((receiptPaymentApp.getForignTrnxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(saleCurrencyId));
				fcSaleReport.setTotalSaleAmount(saleCurrencyCode+"     ******"+totalSaleAmount.toString());
			}

			if(receiptPaymentApp.getLocalNetAmount()!=null && purchageCurrency!=null){
				BigDecimal totalPurchageAmount=GetRound.roundBigDecimal((receiptPaymentApp.getLocalNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
				fcSaleReport.setTotalPurchageAmount(purchageCurrency+"     ******"+totalPurchageAmount.toString());
			}

			if(receiptPaymentApp.getTransactionActualRate()!=null && purchageCurrency!=null && saleCurrencyCode!=null){
				fcSaleReport.setExchageRate(purchageCurrency+" / "+saleCurrencyCode+"     "+receiptPaymentApp.getTransactionActualRate().toString());
			}

		}
		BigDecimal customerRef = foreignCurrencyAdjustApp.getFsCustomer().getCustomerReference();
		fcSaleReport.setCustomerId(customerRef);

		
		/*if(getIdNumber()!=null){
			Date expadate = generalService.getValidExpiryDate(getIdNumber());
			if(expadate!=null){
				fcSaleReport.setIdExpdate(new SimpleDateFormat("dd/MM/yyyy").format(expadate));
			}
		}*/
		
		if(getIdNumber() != null){
			fcSaleReport.setCivilId(getIdNumber());
			if(getIdNumber()!=null){
				Date expadate = generalService.getValidExpiryDate(getIdNumber());
				if(expadate!=null){
					fcSaleReport.setIdExpdate(new SimpleDateFormat("dd/MM/yyyy").format(expadate));
				}
			}
		}else{

			// civil id not available then it is crno
			List<CustomerIdproofView> lstcustProof = generalService.getCustomerIdProofDetailsFromView(customerId);
			if(lstcustProof != null && lstcustProof.size() != 0){
				CustomerIdproofView custDet = lstcustProof.get(0);
				
				if(custDet != null){
					if(custDet.getIdProofInt() != null){
						fcSaleReport.setCivilId(custDet.getIdProofInt());
					}
					
					if(custDet.getIdProofExpiredDate() != null){
						fcSaleReport.setIdExpdate(new SimpleDateFormat("dd/MM/yyyy").format(custDet.getIdProofExpiredDate()));
					}
					
				}
				
			}
		
		}

		fcSaleReport.setTodayDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		fcSaleReport.setCommision(null);
		fcSaleReport.setPaymentMode(null);

		if(saleCurrencyCode!=null && saleCurrencyId!=null){
			BigDecimal amountRefund=GetRound.roundBigDecimal((BigDecimal.ZERO),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(saleCurrencyId));
			fcSaleReport.setAmountRefund(saleCurrencyCode+"     ******"+amountRefund.toString());
		}

		fcSaleReport.setRemrks(foreignCurrencyAdjustApp.getRemarks());
		String source = null;
		String purpose = null;
		if (foreignCurrencyAdjustApp.getPurposeOfTransaction() != null) {
			String pur = isourceOfIncome.getPurposeOfTransaction(new BigDecimal(foreignCurrencyAdjustApp.getPurposeOfTransaction()));
			if (pur != null) {
				purpose = pur;
			}
		}
		if (foreignCurrencyAdjustApp.getSourceOfIncome() != null) {
			String sur = isourceOfIncome.getSourceOfIncome(new BigDecimal(foreignCurrencyAdjustApp.getSourceOfIncome()));
			if (sur != null) {
				source = sur;
			}
		}


		HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(customerRef, foreignCurrencyAdjustApp.getDocumentDate());


		String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
		String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
		String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
		String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
		String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
		String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

		if(!prLtyStr1.trim().equals("") && !prLtyStr2.trim().equals("")){
			fcSaleReport.setLoyaltyPointExpiring(prLtyStr1+"  \n"+prLtyStr2);
		}else if(!prLtyStr1.trim().equals("")){
			fcSaleReport.setLoyaltyPointExpiring(prLtyStr1);
		}else if(!prLtyStr2.trim().equals("")){
			fcSaleReport.setLoyaltyPointExpiring(prLtyStr2);
		}
		
		List<CutomerDetailsView> customerListD = iPersonalRemittanceService.getCustomerDetails(customerId);

		if (customerListD != null && customerListD.size() > 0) {
			CutomerDetailsView cust = customerListD.get(0);
			if(cust.getIdType()!=null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
				fcSaleReport.setLoyaltyPointExpiring("");
			}else{
				fcSaleReport.setLoyaltyPointExpiring(fcSaleReport.getLoyaltyPointExpiring());
			}
		}
		

		String insurence1 ="";

		if(!prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
			insurence1 = prInsStr1+"  \n"+prInsStrAr1;
		}else if(!prInsStr1.trim().equals("")){
			insurence1 = prInsStr1;
		}else if(!prInsStrAr1.trim().equals("")){
			insurence1 = prInsStrAr1;
		}

		String insurence2 ="";

		if(!prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
			insurence2 = prInsStr2+"  \n"+prInsStrAr2;
		}else if(!prInsStr2.trim().equals("")){
			insurence2 = prInsStr2;
		}else if(!prInsStrAr2.trim().equals("")){
			insurence2 = prInsStrAr2;
		}

		if(!insurence1.trim().equals("") && !insurence2.trim().equals("")){
			fcSaleReport.setInsurence(insurence1+" \n"+insurence2);
		}else if(!insurence1.trim().equals("")){
			fcSaleReport.setInsurence(insurence1);
		}else if(!insurence2.trim().equals("")){
			fcSaleReport.setInsurence(insurence2);
		}

		fcSaleReport.setUserName(sessionStateManage.getUserName());
		fcSaleReport.setPurposeOfTransaction(purpose);
		fcSaleReport.setSourceOfIncome(source);
		fcSaleReport.setSignature(foreignCurrencyAdjustApp.getCustomerSignature());
		ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = context.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		
		
		fcSaleReport.setLogoPath(logoPath);

		//String reportPath = realPath + Constants.REPORT_WATERMARK_LOGO;
		String reportPath = realPath + "//images//tick_report.png";
		fcSaleReport.setWaterMarkLogoPath(reportPath);
		fcSaleReport.setWaterMarkCheck(true);

		fcSaleReport.setSignature(foreignCurrencyAdjustApp.getCustomerSignature());

		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(sessionStateManage.getUserName());

		if(employeeList!=null && employeeList.size()>0){
			try {
				Employee employee = employeeList.get(0);
				if (employee.getSignatureSpecimenClob() != null) {
					fcSaleReport.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}



		fcSaleReportDTOList.add(fcSaleReport);
		// }
	}




	public void remittanceApplicationReportInit() throws JRException {


		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceApplicationReportList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");

		System.out.println("realPath "+realPath);

		//String reportPath = realPath +"\\reports\\design\\RemittanceApplication.jasper"; //For WINDOW

		//String reportPath = realPath +"//reports//design//RemittanceApplication.jasper"; //FOR UNIX
		String reportPath = realPath +"//reports//design//RemittanceApplicationNewFormat.jasper"; //FOR UNIX
		System.out.println("reportPath "+reportPath);

		//	String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceApplication.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);

	}

  /*
	private List<RemittanceReportBean> fetchApplicationRemittanceReportData(BigDecimal documentNumber)throws Exception {


		remittanceApplicationReportList.clear();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		List<ShoppingCartDetails> remittanceViewlist = iPersonalRemittanceService.getRecordsRemittanceApplication(documentNumber);
		String purposeOfRemittance = "";
		String currencyQuoteName ="";
		if(generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()))!=null){
			currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
		}


		if (remittanceViewlist.size() > 0) {
			for (ShoppingCartDetails view : remittanceViewlist) {
				RemittanceReportBean obj = new RemittanceReportBean();
				StringBuffer applino =new StringBuffer();
				if(view.getDocumentFinanceYear()!=null){
					applino.append(view.getDocumentFinanceYear());
				}
				if(view.getDocumentNo()!=null){
					applino.append(" / ");
					applino.append(view.getDocumentNo());
				}
				obj.setApplicationNo(applino.toString());

				obj.setDate(currentDate);
				String foreignCurrencyQuoteName = "";

				if(generalService.getCurrencyQuote(view.getForeignCurrency())!=null){
					foreignCurrencyQuoteName = generalService.getCurrencyQuote(view.getForeignCurrency());
				}

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
					BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setProposedOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
				}
				if(view.getLocalNextTranxAmount()!=null){
					BigDecimal localNextTranxAmount=GetRound.roundBigDecimal((view.getLocalNextTranxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setTotalKWDAmount(currencyQuoteName+"     ******"+localNextTranxAmount.toString());
				}
				obj.setFutherInstructions(view.getInstruction());

				obj.setBeneSwiftBank1(view.getBeneficiarySwiftBankOne());
				obj.setBeneSwiftBank2(view.getBeneficiarySwiftBankTwo());
				obj.setBeneSwiftAddr1(view.getBeneficiarySwiftAddrOne());
				obj.setBeneSwiftAddr2(view.getBeneficiarySwiftAddrTwo());
				obj.setBenefeciaryBranchName(view.getBeneficiaryBranch() );
				StringBuffer beneficieryName = new StringBuffer();
				if(view.getBeneficiaryFirstName()!= null){
					beneficieryName.append(view.getBeneficiaryFirstName());
				}
				if(view.getBeneficiarySecondName()!= null){
					beneficieryName.append(" ");
					beneficieryName.append(view.getBeneficiarySecondName());
				}
				if(view.getBeneficiaryThirdName()!=null){
					beneficieryName.append(" ");
					beneficieryName.append(view.getBeneficiaryThirdName());
				}
				if(view.getBeneficiaryFourthName()!=null){
					beneficieryName.append(" ");
					beneficieryName.append(view.getBeneficiaryFourthName());
				}

				obj.setBeneficiaryName(view.getBeneficiaryName());

				StringBuffer benfPayMode = new StringBuffer();
				if(view.getRemittanceDescription()!=null){
					benfPayMode.append(view.getRemittanceDescription());
				}
				if(view.getDeliveryDescription()!= null){
					benfPayMode.append(" - ");
					benfPayMode.append(view.getDeliveryDescription());
				}
				obj.setBenefPaymentMode(benfPayMode.toString());

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

				StringBuffer address = new StringBuffer();
				if(view.getBeneCityName()!= null){
					address.append(view.getBeneCityName());
					address.append(", ");
				}
				if(view.getBeneDistrictName()!= null){
					address.append(view.getBeneDistrictName());
					address.append(", ");
				}
				if(view.getBeneStateName()!= null){
					address.append(view.getBeneStateName());
				}
				obj.setAddress(address.toString());
				obj.setIncomeSource(view.getSourceOfIncomeDesc());
				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerList.size() > 0) {
					
					CutomerDetailsView cust = customerList.get(0);
					
					obj.setSenderName(cust.getCustomerName());
					if(cust.getContactNumber() != null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
						obj.setContactNumber(cust.getContactNumber());
					}
					obj.setCivilId(cust.getIdNumber());
					Date sysdate = cust.getIdExp();
					obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));
				}

				remittanceApplicationReportList.add(obj);
			}
		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
		}
		return remittanceApplicationReportList;

	}

	*/

		//NEW METHOD
	
	private List<RemittanceReportBean> fetchApplicationRemittanceReportData(BigDecimal documentNumber)throws Exception {


		remittanceApplicationReportList.clear();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		List<ShoppingCartDetails> remittanceViewlist = iPersonalRemittanceService.getRecordsRemittanceApplication(documentNumber);
		String currencyQuoteName ="";
		if(generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()))!=null){
			currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
		}

		if (remittanceViewlist.size() > 0) {
			for (ShoppingCartDetails view : remittanceViewlist) {
				RemittanceReportBean obj = new RemittanceReportBean();
				obj.setDate(currentDate);
				String foreignCurrencyQuoteName = "";

				if(generalService.getCurrencyQuote(view.getForeignCurrency())!=null){
					foreignCurrencyQuoteName = generalService.getCurrencyQuote(view.getForeignCurrency());
				}

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
					BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setProposedOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
				}
				if(view.getLocalNextTranxAmount()!=null){
					BigDecimal localNextTranxAmount=GetRound.roundBigDecimal((view.getLocalNextTranxAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrency()));
					obj.setTotalKWDAmount(currencyQuoteName+"     ******"+localNextTranxAmount.toString());
				}
				obj.setFutherInstructions(view.getInstruction());

				obj.setBenefeciaryBranchName(view.getBeneficiaryBranch() );
				StringBuffer beneficieryName = new StringBuffer();
				if(view.getBeneficiaryFirstName()!= null){
					beneficieryName.append(view.getBeneficiaryFirstName());
				}
				if(view.getBeneficiarySecondName()!= null){
					beneficieryName.append(" ");
					beneficieryName.append(view.getBeneficiarySecondName());
				}
				if(view.getBeneficiaryThirdName()!=null){
					beneficieryName.append(" ");
					beneficieryName.append(view.getBeneficiaryThirdName());
				}
				if(view.getBeneficiaryFourthName()!=null){
					beneficieryName.append(" ");
					beneficieryName.append(view.getBeneficiaryFourthName());
				}

				obj.setBeneficiaryName(view.getBeneficiaryName());
				
				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryAccountNumber(view.getBeneficiaryAccountNo());
				
				ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				String realPath = ctx.getRealPath("//");
				String waterMark = realPath + "//images//cross.png";
				obj.setWaterImage(waterMark);


				try {
					if (view.getCustomerSignatureClob() != null) {
						obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				remittanceApplicationReportList.add(obj);
			}
		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
		}
		return remittanceApplicationReportList;

	}

	// checking cashier option in Fs_Employee is Y or N
	public boolean checkEmployeeAllowedOrNot(){
		boolean empStatus = Boolean.FALSE;
		List<Employee> checkEmployee = generalService.getEmployeeDetail(sessionStateManage.getEmployeeId());
		if(checkEmployee != null && checkEmployee.size() != 0){
			Employee empstatus = checkEmployee.get(0);
			if(empstatus.getCashierOpt() != null){
				if(empstatus.getCashierOpt().equalsIgnoreCase(Constants.Y)){
					empStatus = Boolean.TRUE;
				}else{
					empStatus = Boolean.FALSE;
				}
			}else{
				empStatus = Boolean.FALSE;
			}
		}

		return empStatus;
	}

	// selected in remittance to make save in remittance
	public void getSelectedRecordstoRemittance(ShoppingCartDataTableBean shoppingCartDataTableBean) {
		
		boolean cashierOptionStatus = checkEmployeeAllowedOrNot();
		if(cashierOptionStatus){
			//allow
			//shoppingcartDTList.size();
			setAmlRecheckAuthentication(false);
			setColCurrency(generalService.getCurrencyName(new BigDecimal(sessionStateManage.getCurrencyId())));
			if (shoppingCartDataTableBean.getSelectedrecord() && shoppingcartDTList.size() ==1) {
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
				nextpaneltoCollection();
			} else if (shoppingCartDataTableBean.getSelectedrecord()) {
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
			}else if (shoppingCartDataTableBean.getSelectedrecord() == false) {
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
				// on any click authentication need check again
				setBooShowAuthenticationPanel(false);
			} else if (lstselectedrecord.size() == 1) {
				setShoppingcartExchangeRate(lstselectedrecord.get(0).getExchangeRateApplied());
				setBooRenderSingleDocNum(true);
				setBooRenderMultiDocNum(false);
				setApplicationDocNum(lstselectedrecord.get(0).getDocumentNo());
				if(shoppingcartDTList.size() != 1){
					// on any click authentication need check again
					setBooShowAuthenticationPanel(false);
				}
			} else {
				setShoppingcartExchangeRate(null);
				setBooRenderMultiDocNum(true);
				setBooRenderSingleDocNum(false);
				setApplicationDocNum(null);
				setCashRounding(null);
				// on any click authentication need check again
				setBooShowAuthenticationPanel(false);
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
		}else{
			//dont allow
			setExceptionMessage("You are not authorized to collect amount. Please request IT for permission.");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

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
				roundingValue = iPersonalRemittanceService.roundingTotalNetAmountbyFunction(sessionStateManage.getCountryId(),getCalNetAmountPaid(), getCashRounding());

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
					RequestContext.getCurrentInstance().execute("roundValueErr.show();");
				}

			} catch (AMGException e) {
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}

		} else {

			if (getShoppingcartExchangeRate() != null) {
				BigDecimal roundingValue;
				try {
					roundingValue = iPersonalRemittanceService.roundingTotalNetAmountbyFunction(sessionStateManage.getCountryId(),getDummiTotalNetAmount(), getCashRounding());

					if (roundingValue != null) {
						if (getDummiTotalNetAmount().compareTo(roundingValue) < 0) {
							differenceNetAmount = roundingValue.subtract(getDummiTotalNetAmount());
							int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()));
							upNetAmount = differenceNetAmount.divide(getShoppingcartExchangeRate(), decimalvalue,BigDecimal.ROUND_HALF_UP);
							setCalNetAmountPaid(GetRound.roundBigDecimal(roundingValue, decimalvalue));
							setCalGrossAmount(getDummiTotalGrossAmount().add(differenceNetAmount));
							roundtype = Constants.UP;
							lstModifyRoundRecord.clear();
							if (lstselectedrecord.size() == 1) {
								ShoppingCartDataTableBean selectedrec = lstselectedrecord.get(0);
								ShoppingCartDataTableBean lstModifiedData = changeRoundCalculationinShoppingCart(selectedrec, upNetAmount, roundtype,differenceNetAmount);
								lstModifyRoundRecord.add(lstModifiedData);
								setBooRenderModifiedRoundData(true);
							}

						} else {
							differenceNetAmount = getDummiTotalNetAmount().subtract(roundingValue);
							int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()));
							downNetAmount = differenceNetAmount.divide(getShoppingcartExchangeRate(), decimalvalue,BigDecimal.ROUND_HALF_UP);
							setCalNetAmountPaid(GetRound.roundBigDecimal(roundingValue, decimalvalue));
							setCalGrossAmount(getDummiTotalGrossAmount().subtract(differenceNetAmount));
							roundtype = Constants.DOWN;
							lstModifyRoundRecord.clear();
							if (lstselectedrecord.size() == 1) {
								ShoppingCartDataTableBean selectedrec = lstselectedrecord.get(0);
								ShoppingCartDataTableBean lstModifiedData = changeRoundCalculationinShoppingCart(selectedrec, downNetAmount, roundtype,differenceNetAmount);
								lstModifyRoundRecord.add(lstModifiedData);
								setBooRenderModifiedRoundData(true);
							}
						}
					} else {
						// round value null
						setCashRounding(null);
						RequestContext.getCurrentInstance().execute("roundValueErr.show();");
					}
				} catch (AMGException e) {
					setExceptionMessage(e.getMessage());
					RequestContext.getCurrentInstance().execute("sqlexception.show();");
				}



			} else {
				if (getApplicationDocNum() != null) {
					if (lstselectedrecord.size() != 0) {

						for (ShoppingCartDataTableBean selectedrec : lstselectedrecord) {

							if (getApplicationDocNum().compareTo(selectedrec.getDocumentNo()) == 0) {
								BigDecimal roundingValue;
								try {
									roundingValue = iPersonalRemittanceService.roundingTotalNetAmountbyFunction(sessionStateManage.getCountryId(),getDummiTotalNetAmount(),getCashRounding());

									if (roundingValue != null) {
										if (getDummiTotalNetAmount().compareTo(roundingValue) < 0) {
											differenceNetAmount = roundingValue.subtract(getDummiTotalNetAmount());
											int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()));
											upNetAmount = differenceNetAmount.divide(selectedrec.getExchangeRateApplied(),decimalvalue,BigDecimal.ROUND_HALF_UP);
											setCalNetAmountPaid(GetRound.roundBigDecimal(roundingValue,decimalvalue));
											setCalGrossAmount(getDummiTotalGrossAmount().add(differenceNetAmount));
											roundtype = Constants.UP;
											lstModifyRoundRecord.clear();
											ShoppingCartDataTableBean lstModifiedData = changeRoundCalculationinShoppingCart(selectedrec, upNetAmount,roundtype, differenceNetAmount);
											lstModifyRoundRecord.add(lstModifiedData);
											setBooRenderModifiedRoundData(true);

										} else {
											differenceNetAmount = getDummiTotalNetAmount().subtract(roundingValue);
											int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()));
											downNetAmount = differenceNetAmount.divide(selectedrec.getExchangeRateApplied(),decimalvalue,BigDecimal.ROUND_HALF_UP);
											setCalNetAmountPaid(GetRound.roundBigDecimal(roundingValue,decimalvalue));
											setCalGrossAmount(getDummiTotalGrossAmount().subtract(differenceNetAmount));
											roundtype = Constants.DOWN;
											lstModifyRoundRecord.clear();
											ShoppingCartDataTableBean lstModifiedData = changeRoundCalculationinShoppingCart(selectedrec, downNetAmount,roundtype, differenceNetAmount);
											lstModifyRoundRecord.add(lstModifiedData);
											setBooRenderModifiedRoundData(true);
										}
									} else {
										// round value null
										setCashRounding(null);
										RequestContext.getCurrentInstance().execute("roundValueErr.show();");
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

	public ShoppingCartDataTableBean changeRoundCalculationinShoppingCart(ShoppingCartDataTableBean shoppingCartDetails,BigDecimal netAmount, String roundtype,BigDecimal differenceNetAmount) {

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
			shoppingCartDataTableBean.setForeignTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getForeignTranxAmount().add(netAmount),
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(shoppingCartDetails.getForeigncurrency())));
		} else {
			shoppingCartDataTableBean.setLocalTranxAmount(shoppingCartDetails.getLocalTranxAmount().subtract(differenceNetAmount));
			shoppingCartDataTableBean.setLocalNextTranxAmount(shoppingCartDetails.getLocalNextTranxAmount().subtract(differenceNetAmount));
			shoppingCartDataTableBean.setForeignTranxAmount(GetRound.roundBigDecimal(shoppingCartDetails.getForeignTranxAmount().subtract(netAmount),
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(shoppingCartDetails.getForeigncurrency())));
		}
		shoppingCartDataTableBean.setLocalChargeAmount(shoppingCartDetails.getLocalChargeAmount());
		shoppingCartDataTableBean.setLocalCommisionAmount(shoppingCartDetails.getLocalCommisionAmount());
		shoppingCartDataTableBean.setLocalDeliveryAmount(new BigDecimal(0));// shoppingCartDetails.getLocalDeliveryAmount());
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



	private String customerNameForReport;



	public String getCustomerNameForReport() {
		return customerNameForReport;
	}
	public void setCustomerNameForReport(String customerNameForReport) {
		this.customerNameForReport = customerNameForReport;
	}
	
	public void fetchRemittanceReceiptReportData(BigDecimal documentNumber,BigDecimal finaceYear, String documentCode) throws Exception{ 

		collectionViewList.clear();
		remittanceReceiptSubreportList = new CopyOnWriteArrayList<RemittanceReceiptSubreport>();



		List<RemittanceApplicationView> remittanceApplicationList = new ArrayList<RemittanceApplicationView>();

		List<RemittanceApplicationView> fcsaleList = new ArrayList<RemittanceApplicationView>();

		List<RemittanceReportBean> remittanceApplList = new ArrayList<RemittanceReportBean>();

		List<RemittanceReportBean> fcsaleAppList = new ArrayList<RemittanceReportBean>();

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		//String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;
		String waterMark = realPath + "//images//tick_report.png";

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		int noOfTransactions = 0;

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

				List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				/*if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
					obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
				}*/
				/*String purposeOfRemittance = "";
				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					if(purposeOfRemittance.equals("")){
						purposeOfRemittance  = purposeObj.getAmiecDescription();
					}else{
						purposeOfRemittance  = purposeOfRemittance+", "+purposeObj.getAmiecDescription();
					}
				}

				if(purposeOfRemittance != null && !purposeOfRemittance.equalsIgnoreCase("")){
					obj.setPerposeOfRemittance(purposeOfRemittance);
				}*/

				List<PurposeOfRemittanceReportBean> purposeOfRemitTrList1=new ArrayList<PurposeOfRemittanceReportBean>( );
				for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
					PurposeOfRemittanceReportBean beanObj=new PurposeOfRemittanceReportBean();
					beanObj.setPurposeOfTrField(purposeObj.getFlexfieldName());
					beanObj.setPurposeOfTrfieldArabic(null);
					beanObj.setPurposeOfTrValue(purposeObj.getFlexiFieldValue() );
					purposeOfRemitTrList1.add(beanObj);
				}

				if(purposeOfRemitTrList1.size()>0){
					obj.setPurposeOfRemitTrList(purposeOfRemitTrList1);
				}
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

				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
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
				obj.setPinNo(view.getPinNo() );



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
				
				List<CutomerDetailsView> customerListD = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerListD != null && customerListD.size() > 0) {
					CutomerDetailsView cust = customerListD.get(0);
					if(cust.getIdType()!=null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
						obj.setLoyalityPointExpiring("");
					}else{
						obj.setLoyalityPointExpiring(loyaltyPoint.toString());
					}
				}
				
				//obj.setLoyalityPointExpiring(loyaltyPoint.toString());

				if(prInsStr1 != null && prInsStrAr1 != null && !prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
					obj.setInsurence1(prInsStr1+"  \n"+prInsStrAr1);
				}else if(prInsStr1 != null && !prInsStr1.trim().equals("")){
					obj.setInsurence1(prInsStr1);
				}else if(prInsStrAr1 != null && !prInsStrAr1.trim().equals("")){
					obj.setInsurence1(prInsStrAr1);
				}


				if(prInsStr2 != null && prInsStrAr2 != null && !prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
					obj.setInsurence2(prInsStr2+"  \n"+prInsStrAr2);
				}else if(prInsStr2 != null && !prInsStr2.trim().equals("")){
					obj.setInsurence2(prInsStr2);
				}else if(prInsStrAr2 != null && !prInsStrAr2.trim().equals("")){
					obj.setInsurence2(prInsStrAr2);
				}


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

				//addedd new column
				BigDecimal lessLoyaltyEncash = BigDecimal.ZERO;
				BigDecimal amountPayable = BigDecimal.ZERO;
				
				//For AMTB COUPON added by Rabil
				BigDecimal lessAmtbCouponEncash = BigDecimal.ZERO;
				
				
				List<CollectionPaymentDetailsView> collectionPmtDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				for(CollectionPaymentDetailsView collPaymentDetailsView: collectionPmtDetailList){
					if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)){
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}else if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.AMTBC)){ //FOR AMTB COUPON 
						lessAmtbCouponEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}	else{
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
				}
				if(lessLoyaltyEncash.compareTo(BigDecimal.ZERO)==0){
					obj.setLessLoyaltyEncasement(null);					
				}else{
					BigDecimal loyaltyAmount=GetRound.roundBigDecimal((lessLoyaltyEncash),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessLoyaltyEncasement(currencyQuoteName+"     ******"+loyaltyAmount);
				}
				
				
				
				//For AMTB AMOUT
				if(lessAmtbCouponEncash.compareTo(BigDecimal.ZERO)==0){
					obj.setLessAmtbCouponEncasement(null);					
				}else{
					BigDecimal amtbAmount=GetRound.roundBigDecimal((lessAmtbCouponEncash),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessAmtbCouponEncasement(currencyQuoteName+"     ******"+amtbAmount);
				}
				

				if(amountPayable!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal payable=GetRound.roundBigDecimal((amountPayable),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setAmountPayable(currencyQuoteName+"     ******"+payable);
				}

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
				//For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster =	iPersonalRemittanceService.getCompanyMaster(sessionStateManage.getCompanyId());
				StringBuffer engCompanyInfo = null;	
				StringBuffer arabicCompanyInfo=null;
				if(companyMaster.size()>0){
					engCompanyInfo = new StringBuffer();				
					if(companyMaster.get(0).getAddress1()!=null&&companyMaster.get(0).getAddress1().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress1()+",");
					}
					if(companyMaster.get(0).getAddress2()!=null&&companyMaster.get(0).getAddress2().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress2()+",");
					}
					if(companyMaster.get(0).getAddress3()!=null&&companyMaster.get(0).getAddress3().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						engCompanyInfo=engCompanyInfo.append("C.R. "+companyMaster.get(0).getRegistrationNumber()+",");
					}
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						engCompanyInfo=engCompanyInfo.append("Share Capital-"+companyMaster.get(0).getCapitalAmount());
					}				
					obj.setEngCompanyInfo(engCompanyInfo.toString());
					
					arabicCompanyInfo = new StringBuffer();
					
					if(companyMaster.get(0).getArabicAddress1()!=null&&companyMaster.get(0).getArabicAddress1().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress1());
					}
					if(companyMaster.get(0).getArabicAddress2()!=null&&companyMaster.get(0).getArabicAddress2().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress2()+",");
					}
					if(companyMaster.get(0).getArabicAddress3()!=null&&companyMaster.get(0).getArabicAddress3().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR + " " +companyMaster.get(0).getRegistrationNumber()+",");
					}					
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital + " " +companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
				//For Company information ADDED BY VISWA --END
				
				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (customerList.size() > 0) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber().trim()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
						
						if(cust.getIdType()!=null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
							obj.setLoyalityPointExpiring("");
						}
					}
				}
				
				
				// Logo for Go Green added by Viswa
				
				ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				realPath = ctx.getRealPath("//");
				//String logoPath = realPath + Constants.LOGO_PATH;
				
				String logoPath =null;
				if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
					logoPath = realPath+Constants.LOGO_PATH;
				}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
					logoPath = realPath+Constants.LOGO_PATH_OM;
				}else{
					logoPath =realPath+Constants.LOGO_PATH;
				}
				List<String> customerList = icustomerRegistrationService.getCustomerEmailDetails(getCustomerNo());
				
				String customerEmail=null;
				String customerEmailVerifiedOn=null;
				if(customerList!=null && customerList.size()>0)
				{
				   	customerEmail=customerList.get(0);
				   	
					if(customerList.size()>1)
					{
						customerEmailVerifiedOn=customerList.get(1);
					}
					
				}
				if(customerEmail!=null && customerEmailVerifiedOn!=null)
				{
					obj.setSendEmail(true);
					obj.setLogoPath(logoPath);
				}else{
					obj.setSendEmail(false);
				}
				
				
				// End 


				remittanceApplList.add(obj);
			}

			// for foreign currency Sale report
			for (RemittanceApplicationView view : fcsaleList) {

				RemittanceReportBean obj = new RemittanceReportBean();
				
				//For Company information ADDED BY VISWA --START
				List<CompanyMaster> companyMaster =	iPersonalRemittanceService.getCompanyMaster(sessionStateManage.getCompanyId());
				StringBuffer engCompanyInfo = null;	
				StringBuffer arabicCompanyInfo=null;
				if(companyMaster.size()>0){
					engCompanyInfo = new StringBuffer();				
					if(companyMaster.get(0).getAddress1()!=null&&companyMaster.get(0).getAddress1().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress1()+",");
					}
					if(companyMaster.get(0).getAddress2()!=null&&companyMaster.get(0).getAddress2().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress2()+",");
					}
					if(companyMaster.get(0).getAddress3()!=null&&companyMaster.get(0).getAddress3().length()>0){
						engCompanyInfo=engCompanyInfo.append(companyMaster.get(0).getAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						engCompanyInfo=engCompanyInfo.append("C.R. "+companyMaster.get(0).getRegistrationNumber()+",");
					}
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						engCompanyInfo=engCompanyInfo.append("Share Capital-"+companyMaster.get(0).getCapitalAmount());
					}				
					obj.setEngCompanyInfo(engCompanyInfo.toString());
					
					arabicCompanyInfo = new StringBuffer();
					
					if(companyMaster.get(0).getArabicAddress1()!=null&&companyMaster.get(0).getArabicAddress1().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress1());
					}
					if(companyMaster.get(0).getArabicAddress2()!=null&&companyMaster.get(0).getArabicAddress2().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress2()+",");
					}
					if(companyMaster.get(0).getArabicAddress3()!=null&&companyMaster.get(0).getArabicAddress3().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(companyMaster.get(0).getArabicAddress3()+",");
					}
					if(companyMaster.get(0).getRegistrationNumber()!=null&&companyMaster.get(0).getRegistrationNumber().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.CR + " "+companyMaster.get(0).getRegistrationNumber()+",");
					}						
					if(companyMaster.get(0).getCapitalAmount()!=null&&companyMaster.get(0).getCapitalAmount().length()>0){
						arabicCompanyInfo=arabicCompanyInfo.append(Constants.Share_Capital + " " +companyMaster.get(0).getCapitalAmount());
					}
					obj.setArabicCompanyInfo(arabicCompanyInfo.toString());
				}
				//For Company information ADDED BY VISWA --END

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

				if(view.getContactNumber()!=null && !view.getContactNumber().trim().equalsIgnoreCase("")){
					obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
				}
				
				
				
				
				if(view.getIdentityInt() != null && view.getIdentityExpiryDate() != null){
					obj.setCivilId(view.getIdentityInt());
					Date sysdate = view.getIdentityExpiryDate();
					if(sysdate!=null){
						obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));
					}
				}else{
					// civil id not available then it is crno
					List<CustomerIdproofView> lstcustProof = generalService.getCustomerIdProofDetailsFromView(view.getCustomerId());
					if(lstcustProof != null && lstcustProof.size() != 0){
						CustomerIdproofView custDet = lstcustProof.get(0);
						
						if(custDet != null){
							if(custDet.getIdProofInt() != null){
								obj.setCivilId(custDet.getIdProofInt());
							}
							
							if(custDet.getIdProofExpiredDate() != null){
								obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(custDet.getIdProofExpiredDate()));
							}
							
						}
						
					}
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
				
				List<CutomerDetailsView> customerListD = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

				if (customerListD != null && customerListD.size() > 0) {
					CutomerDetailsView cust = customerListD.get(0);
					if(cust.getIdType()!=null && cust.getIdType().compareTo(new BigDecimal(Constants.CORPORATE_ID_TYPE))==0){
						obj.setLoyalityPointExpiring("");
					}else{
						obj.setLoyalityPointExpiring(loyaltyPoint.toString());
					}
				}
				
				
				
				//obj.setLoyalityPointExpiring(loyaltyPoint.toString());

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
				if(!insurence.equals("")){
					obj.setInsurence1(insurence.toString());
				}
				


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

				/*List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

				if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
					obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
				}*/
				obj.setPerposeOfRemittance(view.getPurposeOfTransaction());
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

				//addedd new column
				BigDecimal lessLoyaltyEncash = BigDecimal.ZERO;
				BigDecimal amountPayable = BigDecimal.ZERO;
				List<CollectionPaymentDetailsView> collectionPmtDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				for(CollectionPaymentDetailsView collPaymentDetailsView: collectionPmtDetailList){
					if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)){
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}else{
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
				}
				if(lessLoyaltyEncash.compareTo(BigDecimal.ZERO)==0){
					obj.setLessLoyaltyEncasement(null);					
				}else{
					BigDecimal loyaltyAmount=GetRound.roundBigDecimal((lessLoyaltyEncash),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setLessLoyaltyEncasement(currencyQuoteName+"     ******"+loyaltyAmount);
				}

				if(amountPayable!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal payable=GetRound.roundBigDecimal((amountPayable),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setAmountPayable(currencyQuoteName+"     ******"+payable);
				}

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
				
				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (customerList.size() > 0) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber().trim()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
					}
				}
				
				// Logo for Go Green added by Viswa
				
				ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
				realPath = ctx.getRealPath("//");
				//String logoPath = realPath + Constants.LOGO_PATH;
				
				String logoPath =null;
				if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
					logoPath = realPath+Constants.LOGO_PATH;
				}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
					logoPath = realPath+Constants.LOGO_PATH_OM;
				}else{
					logoPath =realPath+Constants.LOGO_PATH;
				}
				List<String> customerList = icustomerRegistrationService.getCustomerEmailDetails(getCustomerNo());
				
				String customerEmail=null;
				String customerEmailVerifiedOn=null;
				if(customerList!=null && customerList.size()>0)
				{
				   	customerEmail=customerList.get(0);
				   	
					if(customerList.size()>1)
					{
						customerEmailVerifiedOn=customerList.get(1);
					}
					
				}
				if(customerEmail!=null && customerEmailVerifiedOn!=null)
				{
					obj.setSendEmail(true);
					obj.setLogoPath(logoPath);
				}else{
					obj.setSendEmail(false);
				}


				fcsaleAppList.add(obj);


			}

			//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
			RemittanceReceiptSubreport remittanceObj = new RemittanceReceiptSubreport();

			remittanceObj.setWaterMarkLogoPath(waterMark);
			remittanceObj.setWaterMarkCheck(true);
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


	//	int i;
	public void generatePersonalRemittanceReceiptReport(){
		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		ServletOutputStream servletOutputStream=null;
		OutputStream outstream = null;
		try {
			fetchRemittanceReceiptReportData(new BigDecimal(getDocumentNo().toString()),getFinaceYear(),Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION);

			remittanceReceiptReportInit();
			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

			List<String> customerList = icustomerRegistrationService.getCustomerEmailDetails(getCustomerNo());
			String customerEmail=null;
			String customerEmailVerifiedOn=null;
			if(customerList!=null && customerList.size()>0)
			{
			   	customerEmail=customerList.get(0);

				if(customerList.size()>1)
				{
					customerEmailVerifiedOn=customerList.get(1);
				}

			}
			if(customerEmail!=null && customerEmailVerifiedOn!=null)
			{
				//email sending to customer
				sendEmail(pdfasbytes);
			}else
			{
				//String fileName = "RemittanceReceiptReport.pdf";
				//printJasperDirectToPrinter(fileName);

				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
				httpServletResponse.addHeader("Content-disposition","attachment; filename=RemittanceReceiptReport.pdf");
				servletOutputStream=httpServletResponse.getOutputStream();  
				JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();

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
		}finally{
			try {
				if(servletOutputStream!=null){
					servletOutputStream.close();
				}
				if(outstream != null){
					outstream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendEmail(byte[] pdfasbytes) throws com.lowagie.text.DocumentException, IOException, AddressException, javax.mail.MessagingException{

		String from = sessionStateManage.getEmail();
		String subject = "Remittance Receipt From Al Mulla Exchange";
		try {
			List<ApplicationSetup> lstApplicationSetup=iPersonalRemittanceService.getEmailFromAppSetup(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId());

			if(getEmailToSendReport()!=null && !getEmailToSendReport().equals("")){
				mailService.sendMailToCustomerWithAttachment(lstApplicationSetup, getEmailToSendReport(), subject, pdfasbytes,getCustomerNameForReport());
			}
		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void closeExceptionReceiptDailog(){
		setVisableExceptionDailogForReceipt(false);
	}
	public void remittanceReceiptReportInit() throws JRException {

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptSubreportList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String reportPath = realPath +"\\reports\\design\\RemittanceReceiptNewReport.jasper"; //for Window
		String reportPath = realPath +"//reports//design//RemittanceReceiptNewReport.jasper";
		System.out.println(reportPath);


		//		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceiptNewReport.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);


	}

	public void deleteRecordShoppingCart(ShoppingCartDataTableBean shoppingCartData) {

		if(shoppingCartData.getApplicationType()!=null && shoppingCartData.getApplicationType().equals(Constants.Remittance) && shoppingCartData.getRemittanceApplicationId() != null){
			setAmlRecheckAuthentication(false);
			System.out.println(shoppingCartData.getRemittanceApplicationId());
			iPersonalRemittanceService.deleteShoppingCartByApplId(shoppingCartData.getRemittanceApplicationId());
			getShoppingCartDetails(getCustomerNo());
		}else if(shoppingCartData.getApplicationType()!=null && shoppingCartData.getApplicationType().equals(Constants.FCSale) && shoppingCartData.getDocumentNo() != null){
			setAmlRecheckAuthentication(false);
			System.out.println(shoppingCartData.getDocumentNo());
			iPersonalRemittanceService.deleteShoppingCartForFCSale(shoppingCartData);
			getShoppingCartDetails(getCustomerNo());
		}else{
			// not fc sale or remittance
		}

		if (shoppingcartDTList.isEmpty()) {
			backFromBenificaryStatusPanel();
			setCalGrossAmount(null);
			setCalNetAmountPaid(null);
			
			tempCalGrossAmount = BigDecimal.ZERO;
			tempCalNetAmountPaid = BigDecimal.ZERO;
			remittanceNo = new BigDecimal(0);
			fcsaleNo = new BigDecimal(0);
			
			if(lstselectedrecord != null || !lstselectedrecord.isEmpty()){
				lstselectedrecord.clear();
			}
			if(shoppingcartDTList != null || !shoppingcartDTList.isEmpty()){
				shoppingcartDTList.clear();
			}
			if(coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()){
				coustomerBeneficaryDTList.clear();
			}
			setBeneficiaryCountryId(null);
			//RequestContext.getCurrentInstance().execute("noshoppingcard.show();");
		}else{
			setCalGrossAmount(null);
			setCalNetAmountPaid(null);
			tempCalGrossAmount = BigDecimal.ZERO;
			tempCalNetAmountPaid = BigDecimal.ZERO;
			remittanceNo = new BigDecimal(0);
			fcsaleNo = new BigDecimal(0);
		}

	}

	/*// to get the Collection Panel checking check box condition in datatable  panel
	public void nextpaneltoCollection() {
		setColremittanceNo(remittanceNo);
		setColfcsaleNo(fcsaleNo);
		clearingDetailAfterAdding();
		lstRefundData.clear();
		lstData.clear();
		if (getBoorefundPaymentDetails()) {
			nextpaneltoPaymentDetails();
		} else if (lstselectedrecord.size() > 0) {	
			allPanelOff();
			setBooRenderCollection(true);
			if(coldatatablevalues.size() != 0){
				setBooRendercollectiondatatable(true);
			}
			getLocalBankListforIndicator();
			// Added by Rabil
			//getLocalBankListforIndicatorFromView();
		} else {
			RequestContext.getCurrentInstance().execute("checkboxchecking.show();");
		}
	}*/
	//to fetch payment details
	public void toFetchPaymentDetails(){
		List<PaymentModeDesc> list=ipaymentService.fetchPaymodeDesc(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),Constants.Yes);
		if(list.size() !=0){
			lstFetchAllPayMode.addAll(list);   
		}

	}

	// to get the Collection Panel checking check box condition in datatable  panel
	public void nextpaneltoCollection() {		
		clearDebitDetails();
		lstFetchAllPayMode.clear();
		toFetchPaymentDetails();  
		setColremittanceNo(remittanceNo);
		setColfcsaleNo(fcsaleNo);
		clearingDetailAfterAdding();
		lstRefundData.clear();
		if(getBackNavigation() == true){

		}else{
			lstData.clear();
		}

		if (getBoorefundPaymentDetails()) {
			nextpaneltoPaymentDetails();
		}else if (getBooRenderPaymentDetails()) {
			collectionPaymentModePanel();
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
		setPaymentmodeFocus(true);
	}

	// process to collection and payment mode
	public void collectionPaymentModePanel(){
		// if false proceed to next
		allPanelOff();
		setAmlRecheckAuthentication(false);
		setBooRenderCollection(true);
		if(coldatatablevalues.size() != 0){
			setBooRendercollectiondatatable(true);
		} else {

			for(PaymentModeDesc paymentModeDesc : lstFetchAllPayMode){
				if(paymentModeDesc.getLocalPaymentName().equals(Constants.CASHNAME))
				{
					setColpaymentmodeId(paymentModeDesc.getPaymentMode().getPaymentModeId());
					changeofPaymentMode();
				}

			}


			BigDecimal loyaltyPointencashed = new BigDecimal(0);
			/* FOR AMTB COUPON CHECK Added by RABIL */
			BigDecimal amtbCouponencashed = new BigDecimal(0);
			/* FOR AMTB COUPON CHECK Added by RABIL */
			
			ShoppingCartDataTableBean shoppingCartDataTableBean = null;
			for (int i = 0; i < lstselectedrecord.size(); i++) {
				shoppingCartDataTableBean = lstselectedrecord.get(i);
				if ((shoppingCartDataTableBean.getLoyaltsPointIndicator() != null && shoppingCartDataTableBean.getLoyaltsPointIndicator().equals(Constants.Yes)) || 
						(shoppingCartDataTableBean.getAmtbCouponencahsed() != null && shoppingCartDataTableBean.getAmtbCouponencahsed().compareTo(BigDecimal.ZERO)>0)) {
					if (shoppingCartDataTableBean.getLoyaltsPointencahsed() != null) {
						loyaltyPointencashed = loyaltyPointencashed.add(shoppingCartDataTableBean.getLoyaltsPointencahsed());
					}
					if(shoppingCartDataTableBean.getAmtbCouponencahsed()!= null && shoppingCartDataTableBean.getAmtbCouponencahsed().compareTo(BigDecimal.ZERO)>0){
						amtbCouponencashed = amtbCouponencashed.add(shoppingCartDataTableBean.getAmtbCouponencahsed());
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

					System.out.println(" getCalNetAmountPaid" + getCalNetAmountPaid());
					System.out.println("getCalNetAmountPaid().subtract(voucher.getColAmountDT())" + getCalNetAmountPaid().subtract(voucher.getColAmountDT()));
					setTotalbalanceAmount(getCalNetAmountPaid().subtract(voucher.getColAmountDT()));
					System.out.println("getTotalbalanceAmount() "+ getTotalbalanceAmount());

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
			
			
/** FOR AMTB COUPON ADDED BY RABIL code Start Here*/
			
			if (amtbCouponencashed.compareTo(new BigDecimal(0)) > 0) {
				PersonalRemittanceCollectionDataTable amtbvoucher = new PersonalRemittanceCollectionDataTable();
				amtbvoucher.setColAmountDT(amtbCouponencashed);
				amtbvoucher.setColpaymentmodeCode(Constants.AMTBC);
				PaymentModeDesc voucherDesc = iPersonalRemittanceService.getvoucherModeId(Constants.AMTBC, sessionStateManage.getLanguageId());
				if(voucherDesc!=null) {
					amtbvoucher.setColpaymentmodetypeDT(voucherDesc.getLocalPaymentName());
					amtbvoucher.setColpaymentmodeIDtypeDT(voucherDesc.getPaymentMode().getPaymentModeId());

					System.out.println(" getCalNetAmountPaid" + getCalNetAmountPaid());
					System.out.println("getCalNetAmountPaid().subtract(voucher.getColAmountDT())" + getCalNetAmountPaid().subtract(amtbvoucher.getColAmountDT()));
					if(loyaltyPointencashed.compareTo(new BigDecimal(0)) > 0){
					setTotalbalanceAmount(getTotalbalanceAmount().subtract(amtbvoucher.getColAmountDT()));
					}else{
						setTotalbalanceAmount(getCalNetAmountPaid().subtract(amtbvoucher.getColAmountDT()));
					}
					System.out.println("getTotalbalanceAmount() "+ getTotalbalanceAmount());

				}
				else
				{
					RequestContext.getCurrentInstance().execute("paymentModemissing.show();");
					setErrmsg("AMTB Voucher entery is missing in Payment Mode");
					return;
				}
				amtbvoucher.setBooDisbale(true);
				coldatatablevalues.add(amtbvoucher);
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
						// only remittance to check beneficiary id aml check
						if(lstBeneRecChecking.getApplicationType().equalsIgnoreCase(Constants.Remittance)){
							if(bigDecimal.compareTo(lstBeneRecChecking.getBeneficiaryId())==0){
								shoppingCartCount = shoppingCartCount.add(BigDecimal.ONE);
							}
						}else{
							// fc sale no condition to check
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

		if(lstBeneAuthentication != null || !lstBeneAuthentication.isEmpty()){
			lstBeneAuthentication.clear();
		}

		List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();

		if(userName != null && password != null ){
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

	}


	// to render panel 7 - payment details denomination
	public void nextpaneltoPaymentDetails() {
		setPaymentmodeFocus(true);
		setAppNoFocus(false);
		setCheckEmailForReport(false);
		setRenderEmailForReport(false);
		setEmailToSendReport(null);
		if (getToalUsedAmount() != null && getCalNetAmountPaid() != null && getToalUsedAmount().compareTo(getCalNetAmountPaid()) >= 0) {

			checkEmailVerification();
			//nextDenaminationPanel();
			/*allPanelOff();
			setBooRenderPaymentDetails(true);
			setPaymentDeatailsPanel(true);

			if(getTempCash() != null){
				setCashAmount(GetRound.roundBigDecimal(getTempCash(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			}
			// to render the denomination in payment details panel -7
			renderingDenominationDT();
			denaMinLstData();
			setPaymentDetailsremark(null);*/
		} else {
			RequestContext.getCurrentInstance().execute("amountmatch.show();");
		}
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
	Boolean backNavigation=false;



	public Boolean getBackNavigation() {
		return backNavigation;
	}
	public void setBackNavigation(Boolean backNavigation) {
		this.backNavigation = backNavigation;
	}
	private void denaMinLstData() {
		//lstData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();

		/* Checking that it's first time or not, first time list size will be 0 */
		//	double sAmount = 0;
		//localLstData.clear();
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
			if(getBackNavigation()==true){

				for (ForeignLocalCurrencyDataTable element : lstData) {
					ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
					forLocalCurrencyDtObj.setSerial(serial);
					forLocalCurrencyDtObj.setItem(element.getDenominationAmount());
					forLocalCurrencyDtObj.setQty(element.getQty());
					forLocalCurrencyDtObj.setPrice(element.getPrice());
					forLocalCurrencyDtObj.setDenominationId(element.getDenominationId());
					//forLocalCurrencyDtObj.setCurrencyId(element.getExCurrencyMaster().getCurrencyId());
					forLocalCurrencyDtObj.setDenominationDesc(element.getDenominationDesc());
					forLocalCurrencyDtObj.setDenominationAmount(element.getDenominationAmount());

					localLstData.add(forLocalCurrencyDtObj);
					serial++;
				}

			}else{

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

		}



		/* Responsible to keep sum of total amount of cash entered */
		double totalSum = 0;

		/* Responsible to calculate sum of entered cash amount */
		for (ForeignLocalCurrencyDataTable element : localLstData) {
			System.out.println("*******");
			System.out.println(element);
			System.out.println("*******");
			if (element.getPrice().length() != 0) {
				totalSum = totalSum + Double.parseDouble(element.getPrice());
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
			setBackNavigation(true);

		}
		setLstData(localLstData);  //Adding denomication here
		if(localLstData.size() != 0){
			setDenominationchecking(Constants.DenominationAvailable);
		}else{
			setDenominationchecking(Constants.DenominationNotAvailable);
		}
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

	// change by payment mode - cash and debit card
	public void changeofPaymentMode() {
		setPaymentmodeFocus(false);
		clearDebitDetails();
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

					// bank Transfer Bank Code
					setColBankTransferBankCode(null);

					if(getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)){
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						setBooRenderColBankTransfer(false);
					}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)){
						setBooRenderColCheque(false);
						setBooRenderColDebitCard(true);
						setBooRenderColBankTransfer(false);
					}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(true);
						setBooRenderColBankTransfer(false);
					}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)){
						setBooRenderColBankTransfer(true);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
					}else{
						setColpaymentmodeId(null);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						setBooRenderColBankTransfer(false);
						System.out.println("Payment Mode Newly added");
						RequestContext.getCurrentInstance().execute("checkPaymentModeService.show();");
						return;
					}

				}
			}else{
				setBooRenderColDebitCard(false);
				setBooRenderColCheque(false);
				setBooRenderColBankTransfer(false);
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

	boolean checkKnetAmount ;
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
	}

	public void checkingCardNumberLength() {
		if (getColCardNo() != null && getColCardNoLength() != null) {
			if ((getColCardNo().toString()).length() != getColCardNoLength().intValue()) {
				setColCardNo(null);
				RequestContext.getCurrentInstance().execute("misMatchBankCardLength.show();");
			}
		}
	}

	public void populateCustKnetCardDetails() {
		for (CustomerBank customerBank : lstDebitCard) {
			if(getColCardNo() != null){
				if (customerBank.getDebitCard().equalsIgnoreCase(getColCardNo().toPlainString())) {
					if (customerBank.getAuthorizedBy() != null) {
						List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
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

		if(getColNameofCard() != null){
			String errMsg = validateDebitCard();

			if(errMsg != null){
				setExceptionMessage(errMsg);
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}
	}

	public void editDebitCardtoEnter() {
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
	}



	/**
	 * Added by Rabil on 07/01/2016 to get approval no form KNET-IPOS
	 */
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

			/** commented by Rabil on 01/05/2016
	api.setIPAdress(ipAddress);


	api.Payment(trnxAmount, cashhier, getApplicationDocNum().toString());
	receipt = api.getReceipt();
			 */
			//log.info("*********Web receipt receiptData:" + receipt.receiptData);


			//New code aded by Rabil on 1/05/2016
			Hashtable<String, String> ht = new Hashtable<String, String>();
			StringBuffer sb = new StringBuffer();
			StringBuffer urlBuffer = new StringBuffer();
			String appender = "?";
			String ampersand = "&";
			String equals = "=";
			String colon = ":";
			String rootContext = "/IposWeb/ipos/ipos.jsp"; // KwtSmartCard/smartcard
			String  env="test";
			//HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String host = request.getHeader("X-FORWARDED-FOR");
			if (host == null) {
				host = request.getRemoteAddr();
			}
			String prdPort="8085";

			if (env.equalsIgnoreCase("test")) {
				urlBuffer.append("http://").append(host).append(colon).append(prdPort).append(rootContext).append(appender);
			} else if (env.equalsIgnoreCase("live")) {
				urlBuffer.append("https://").append(host);
				if (prdPort != null && prdPort.length() > 0) {
					urlBuffer.append(colon).append(prdPort);
				}
				urlBuffer.append(rootContext).append(appender);
			}
			urlBuffer.append("amount").append(equals).append(trnxAmount).append(ampersand).append("cashier").append(equals).append(cashhier).append(ampersand).append("docno").append(equals).append(getApplicationDocNum().toString()).append(ampersand).append("action").append(equals).append("P");
			log.info("urlBuffer :"+urlBuffer.toString());
			HttpURLConnection testyc = null;
			HttpsURLConnection prdyc = null;
			try {
				URL knetRequest = new URL(urlBuffer.toString());

				BufferedReader in = null;
				if (env.equalsIgnoreCase("test")) {
					testyc = (HttpURLConnection) knetRequest.openConnection();
					in = new BufferedReader(new InputStreamReader(testyc.getInputStream()));
				} else if (env.equalsIgnoreCase("live")) {
					prdyc = (HttpsURLConnection) knetRequest.openConnection();
					in = new BufferedReader(new InputStreamReader(prdyc.getInputStream()));
				}
				String inputLine;
				//Hashtable<String, String> ht = new Hashtable<String, String>();
				String receiptDataKey ="Key-Receipt Data";
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					if(inputLine!=null){
						String[] str = inputLine.split(":");
						if(str!=null && str.length>1){
							ht.put(str[0].trim(),str[1].trim());
						}else{
							if(inputLine!=null && !inputLine.trim().equalsIgnoreCase("Receipt Data  :")){
								sb.append("").append(inputLine);
							}
						}
					}


					//sb.append(inputLine + "##");
				}
				System.out.println("sb :"+sb);
				ht.put(receiptDataKey,sb.toString());
				in.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(testyc!=null){
					testyc.disconnect();
				}
				if(prdyc!=null){
					prdyc.disconnect();
				}
			}
			System.out.println("ht :"+ht.toString());
			/*System.out.println("str REceipt:"+sb.toString());
	String[] str = sb.toString().split("#");
	System.out.println("str REceipt:"+str);

	if(sb!=null && sb.length()>0){
	String[] strSp = sb.toString().split("##");
	if(strSp!=null && strSp.length>0){
	for(int i=0;i<strSp.length;i++){
	System.out.println("strSp["+i+"] "+strSp[i]);
	if( strSp[i]!=null && !strSp[i].equals("")){
	if(strSp[i].trim().equalsIgnoreCase("Receipt Data")){
	ht.put("Receipt Data", strSp[i].replace("##", ""));
	}else{
	String[] strCol = strSp[i].split(":");
	System.out.println("Key  :"+strCol[0]+"\t Value :"+strCol[1]);
	ht.put(strCol[0], strCol[1]);
	}
	   }
	}
	}
	}*/

			//New Code ended here

			// if (receipt != null) {
			if(ht!=null){
				System.out.println("IF approvalNoFromKnetIpos  method Receipt Data  :\n" + ht.get("Key-Receipt Data"));
				log.info("Receive  from KNET-IPOS:" + ht.toString());
				log.info("KNET-IPO auth code :" + ht.get("Auth. Code"));
				log.info("KNET-IPO error description  :" +  ht.get("MsgCode")+"\t  :"+ ht.get("Error Desc."));
				if(ht.get("Auth. Code")!=null && ht.get("Auth. Code").equals("")){ 
					setColApprovalNo(ht.get("Auth. Code"));//receipt.authorizationCode);
				}
				//log.info("******approvalNoFromKnetIpos :\n" + receipt.receiptData);

				// Approved :176 error 255

				//if (receipt.errorDescription.equalsIgnoreCase("APPROVED") || (receipt.msgCode == 176)) {
				if(ht.get("Error Desc.").trim()!=null && (ht.get("Error Desc.").trim().equalsIgnoreCase("APPROVED") || ht.get("MsgCode").equals("176"))){
					KnetLog knetLog = new KnetLog();
					setBooColApprovalNo(true);
					setKnetIposReceipt(ht.get("Key-Receipt Data").trim());//receipt.receiptData.toString());
					setColApprovalNo(ht.get("Auth. Code").trim());//receipt.authorizationCode);
					setKnetTranId(ht.get("Trans. ID").trim());//(receipt.transID);

					String dateTime = null;
					if (ht.get("Receipt Date") != null && ht.get("Receipt Time") != null) {
						dateTime = ht.get("Receipt Date").substring(0, 2) + "/" + ht.get("Receipt Time").substring(2, 4) + "/"
								+ ht.get("Receipt Date").substring(4, 8) + " " + ht.get("Receipt Time").substring(0, 2) + ":"
								+ ht.get("Receipt Date").substring(2, 4);

					}
					log.info("Knet ReceiptDate and Time: " + dateTime);
					System.out.println("Knet ReceiptDate and Time: " + dateTime);
					setKnetReceiptDate(dateTime);
					setKnetReceiptTime(ht.get("Receipt Time"));//(receipt.receiptTime);

					knetLog.setApplicationCountryId(sessionStateManage.getCountryId());
					knetLog.setCompanyId(sessionStateManage.getCompanyId());
					knetLog.setCustmoerId(getCustomerNo());
					knetLog.setAuthCode(ht.get("Auth. Code"));//receipt.authorizationCode);
					knetLog.setCreatedBy(sessionStateManage.getUserName());
					knetLog.setCreatedDate(new Date());
					knetLog.setIsActive("Y");
					//knetLog.setKnetAmount(new BigDecimal(receipt.amount).divide(new BigDecimal(1000)));
					knetLog.setKnetAmount(new BigDecimal(ht.get("Amount")).divide(new BigDecimal(1000)));
					knetLog.setKnetMessage("TRANSACTION APPROVED");
					knetLog.setReceipt(ht.get("Key-Receipt Data"));//receipt.toString());
					knetLog.setReceiptData(ht.toString());//get("Key-Receipt Data"));//receipt.receiptData);
					iPersonalRemittanceService.saveKnetLogDetails(knetLog);


				} else if(ht.get("Error Desc.")!=null && (ht.get("Error Desc.").equalsIgnoreCase("ISSUER NOT AVAILABLE") || ht.get("MsgCode").equals("255"))){

					String dateTime = null;

					if (ht.get("Receipt Date") != null && ht.get("Receipt Time") != null) {
						dateTime = ht.get("Receipt Date").substring(0, 2) + "/" + ht.get("Receipt Time").substring(2, 4) + "/"
								+ ht.get("Receipt Date").substring(4, 8) + " " + ht.get("Receipt Time").substring(0, 2) + ":"
								+ ht.get("Receipt Date").substring(2, 4);

					}
					log.info("Knet ReceiptDate and Time: " + dateTime);

					KnetLog knetLog = new KnetLog();
					knetLog.setApplicationCountryId(sessionStateManage.getCountryId());
					knetLog.setCompanyId(sessionStateManage.getCompanyId());
					knetLog.setCustmoerId(getCustomerNo());
					knetLog.setAuthCode(ht.get("Auth. Code"));//receipt.authorizationCode);
					knetLog.setCreatedBy(sessionStateManage.getUserName());
					knetLog.setCreatedDate(new Date());
					knetLog.setIsActive("N");
					knetLog.setKnetAmount(new BigDecimal(ht.get("Amount")).divide(new BigDecimal(1000)));
					knetLog.setKnetMessage(ht.get("Error Desc."));//receipt.errorDescription);
					knetLog.setReceipt(ht.get("Key-Receipt Data"));//receipt.toString());
					//knetLog.setReceiptData(ht.get("Key-Receipt Data"));//receipt.receiptData);
					knetLog.setReceiptData(ht.toString());

					iPersonalRemittanceService.saveKnetLogDetails(knetLog);

					setBooColApprovalNo(false);
					setKnetIposReceipt(null);
					//setExceptionMessage(receipt.errorDescription + " PLEASE REMOVE CARD AND TRY AGAIN");
					setExceptionMessage(ht.get("Error Desc.")+ " PLEASE REMOVE CARD AND TRY AGAIN");
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




	/** populate new bankList for Customer **/
	public void addNewBenificiary() {
		setColBankTransferBankCode(null);
		setColBankCode(null);
		setColCardNo(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		setColCash(null);
		setColAuthorizedby(null);
		setColpassword(null);
		setColApprovalNo(null);
		setColDebitCardPrefex(null);
		setSingleDebitCardPrefex(null);
		setDebitCardNo(null);
		setBooAuthozed(false);
		bankMasterList.clear();
		localbankList.clear();// From View V_EX_CBNK
		lstDebitCard.clear();
		
		if(lstBankPrefix != null && lstBankPrefix.size() != 0){
			lstBankPrefix.clear();
		}
		
		setBooRenderSingleeCardPrefix(true);
		setBooRenderMultipleCardPrefix(false);

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

	// verifying the password with authoried person
	public void verifyPassword() {

		checkcashcollection();
		setPaymentmodeFocus(true);
		setAppNoFocus(false);

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
										//setColpassword(getColpassword());
										//setCyberPassword(cyperpassword);
										checkingPaymentCardinDB();
									} else {
										setColpassword(null);
										RequestContext.getCurrentInstance().execute("passwordcheck.show();");
									}
								} else {
									checkingPaymentCardinDB();
								}

							}else if(getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){

								//Boolean checkdata = checkingChequeDuplicateCheck();
								// blocking duplicate condition for Cheque based on user Requirement 
								Boolean checkdata = Boolean.TRUE;
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
								//Boolean checkBT = checkingBankTransferDuplicateCheck();
								// blocking duplicate condition for bank Transfer based on user Requirement 
								Boolean checkBT = Boolean.TRUE;
								if(checkBT){
									localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getColBankTransferBankCode());
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
									setErrmsg("Bank Transfer Payment Mode Already Exists in DataTable");
									log.info("Bank Transfer Payment Mode Already Exists in DataTable");
									RequestContext.getCurrentInstance().execute("warningmsg.show();");
									return;
								}								
							}else{
								System.out.println("Other Payment Mode");
							}

						}
					}else{
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						setBooRenderColBankTransfer(false);
					}
				}
			} catch (AMGException e) {
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}
		}
	}

	// checking cheque data with datatable
	public Boolean checkingChequeDuplicateCheck(){
		Boolean checkCheque = false;
		int i = 0;
		if (getColchequebankCode()!=null && coldatatablevalues.size() != 0 ) {
			for (PersonalRemittanceCollectionDataTable lstpaymentdata : coldatatablevalues) {
				i = 0;
				if(lstpaymentdata.getColBankCodeDT() != null && lstpaymentdata.getColBankCodeDT().equalsIgnoreCase(getColchequebankCode())){
					if(lstpaymentdata.getColchequeRefNo() != null && lstpaymentdata.getColchequeRefNo().equalsIgnoreCase(getColChequeRef())){
						i = 1;
						break;
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

	// checking cheque data with datatable
	public Boolean checkingBankTransferDuplicateCheck(){
		Boolean checkBankTransfer = false;
		int i = 0;
		if (getColpaymentmodeCode() != null && coldatatablevalues.size() != 0 ) {
			for (PersonalRemittanceCollectionDataTable lstpaymentdata : coldatatablevalues) {
				i = 0;
				if(lstpaymentdata.getColpaymentmodeCode() != null && lstpaymentdata.getColpaymentmodeCode().equalsIgnoreCase(getColpaymentmodeCode())){
					if(lstpaymentdata.getColBankCodeDT() != null && lstpaymentdata.getColBankCodeDT().equalsIgnoreCase(getColBankTransferBankCode())){
						i = 1;
						break;
					}else{
						i = 0;
					}
				}else{
					i = 0;
				}
			}

			if(i == 1){
				checkBankTransfer = false;	
			}else{
				checkBankTransfer = true;	
			}

		}else{
			checkBankTransfer = true;
		}

		return checkBankTransfer;
	}

	// to add values to data table in collection
	public void calculatingNetAmountDT() {
		int i = 0;
		BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
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
							if (collectionlst.getColBankCodeDT().compareTo(getColBankCode()) == 0) {
								if (collectionlst.getColCardNumberDT().compareTo(getColCardNo()) == 0) {
									if(collectionlst.getColApprovalNo().compareTo(getColApprovalNo()) == 0){
										clearingDetailAfterAdding();
										RequestContext.getCurrentInstance().execute("bankexists.show();");
										flag = false;
										break;
									}else {
										i = 1;
									}

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
				setTotalbalanceAmount(null);
				setTotalrefundAmount(null);
				addPaymentModerecord();
			}

			if (coldatatablevalues.size() > 0 && flag == true) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					// totalUamount=totalUamount+collectionlst.getColAmountDT().intValue();
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

			clearingDetailAfterAdding();
		}
	}

	public void checkingPaymentCardinDB() {
		if (lstDebitCard.size() != 0) {
			int i = 0;
			for (CustomerBank lstDebit : lstDebitCard) {
				if(getColCardNo() != null){
					if (lstDebit.getDebitCard().equalsIgnoreCase(getColCardNo().toString())) {
						i = 1;
						break;
					} else {
						i = 0;
					}
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

	/** TO STORE THE NEW BENIFICARY CUSTOMER DETAILS TO CUSTOMER BANK **/
	public void saveCustomerDetailsToCustomerBank() {

		CustomerBank customerBank = new CustomerBank();

		customerBank.setCollectionMode(Constants.COLLECTIONMODE);

		List<ViewBankDetails> lstViewBankDetails=icustomerBankService.getChequeBnakIdFromView(getColBankCode());

		if(lstViewBankDetails.size() != 0){
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(lstViewBankDetails.get(0).getChequeBankId()); 
			customerBank.setFsBankMaster(bankMaster);
		}

		customerBank.setBankCode(getColBankCode()); // this is fixed//generalService.getBankCode(getColBankid()));

		if(getCustomerNo() != null){
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerNo());
			customerBank.setFsCustomer(customer);
		}

		if(getColAuthorizedby() != null){
			customerBank.setAuthorizedBy(getColAuthorizedby());
			customerBank.setAuthorizedDate(new Date());
			customerBank.setPassword(getCyberPassword());
		}
				
		if(getColCardNo() != null){
			customerBank.setDebitCard(encryptionDescriptionService.getENCrypted(getColNameofCard(), getColCardNo().toPlainString()));
			String prefix = getColCardNo().toPlainString().substring(0,6);
			customerBank.setBankPrefix(prefix);
			
			String lastFour = getColCardNo().toPlainString().substring(12);
			customerBank.setBankSuffix(lastFour);
		}
		
		customerBank.setDebitCardName(getColNameofCard());
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

	// adding to datatable list in payment mode
	public void addPaymentModerecord() {
		BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,	new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			totalUamount = new BigDecimal(0);
			PersonalRemittanceCollectionDataTable personalcollectionDT = new PersonalRemittanceCollectionDataTable();
			personalcollectionDT.setColpaymentmodeIDtypeDT(getColpaymentmodeId());
			personalcollectionDT.setColpaymentmodetypeDT(getColpaymentmodeName());
			personalcollectionDT.setColpaymentmodeCode(getColpaymentmodeCode());
			if (getColBankCode() != null || getColchequebankCode() != null || getColBankTransferBankCode() != null) {
				if(localbankListForBankCode.size() != 0){
					ViewBankDetails lstDetaiks = localbankListForBankCode.get(0);
					personalcollectionDT.setColBankIdDT(lstDetaiks.getChequeBankId());
					personalcollectionDT.setColbankNameDT(lstDetaiks.getBankFullName());

					if(getColChequeRef() != null && getColchequebankCode() != null){
						personalcollectionDT.setColBankCodeDT(getColchequebankCode());
						personalcollectionDT.setColchequeRefNo(getColChequeRef());
						personalcollectionDT.setColchequeDate(getColChequeDate());
						personalcollectionDT.setColApprovalNo(getColChequeApprovalNo());
					}else if(getColBankCode() != null){
						personalcollectionDT.setColBankCodeDT(getColBankCode());
						personalcollectionDT.setColCardNumberDT(getColCardNo());
						if(getColCardNo() != null){
							personalcollectionDT.setMaskCardNumberDT(maskCCNumber(getColCardNo().toString()));
						}
						personalcollectionDT.setColNameofCardDT(getColNameofCard());
						personalcollectionDT.setColAuthorizedByDT(getColAuthorizedby());
						personalcollectionDT.setColApprovalNo(getColApprovalNo());
						personalcollectionDT.setKnetReceiptDT(getKnetIposReceipt());
						personalcollectionDT.setKnetTransIdDT(getKnetTranId());
						personalcollectionDT.setKneRceiptTimeDT(getKnetReceiptDate());
						personalcollectionDT.setBooDisbale(false);
					}else if(getColBankTransferBankCode() != null){
						personalcollectionDT.setColBankCodeDT(getColBankTransferBankCode());
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
			setBooRenderColBankTransfer(false);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColCash(personalRemitObj.getColAmountDT());
			coldatatablevalues.remove(personalRemitObj);
		}else if(personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)){
			setBooRenderColDebitCard(true);
			setBooRenderColCheque(false);
			setBooRenderColBankTransfer(false);
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
			setBooRenderColBankTransfer(false);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColchequebankCode(personalRemitObj.getColBankCodeDT());
			setColChequeRef(personalRemitObj.getColchequeRefNo());
			setColChequeDate(personalRemitObj.getColchequeDate());
			setColCash(personalRemitObj.getColAmountDT());
			setColChequeApprovalNo(personalRemitObj.getColApprovalNo());
			coldatatablevalues.remove(personalRemitObj);
		}else if(personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)){
			setBooRenderColDebitCard(false);
			setBooRenderColCheque(false);
			setBooRenderColBankTransfer(true);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColBankTransferBankCode(personalRemitObj.getColBankCodeDT());
			//setColChequeRef(personalRemitObj.getColchequeRefNo());
			//setColChequeDate(personalRemitObj.getColchequeDate());
			setColCash(personalRemitObj.getColAmountDT());
			//setColChequeApprovalNo(personalRemitObj.getColApprovalNo());
			coldatatablevalues.remove(personalRemitObj);
		}else{
			System.out.println("Other Payment mode");
		}

		if (coldatatablevalues.size() != 0) {
			setBooRendercollectiondatatable(true);
		} else {
			setBooRendercollectiondatatable(false);
		}
		setPaymentmodeFocus(true);
		setAppNoFocus(false);

	}

	/*public void editRecord(PersonalRemittanceCollectionDataTable personalRemitObj) {
		BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,
				new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			if (coldatatablevalues.size() > 0) {
				subtractedAmount = getToalUsedAmount().subtract(personalRemitObj.getColAmountDT());
				setToalUsedAmount(subtractedAmount);
			} else {
				setToalUsedAmount(null);
			}
			if (personalRemitObj.getColpaymentmodeIDtypeDT().equals(paymentModeCashId)) {
				setBooRenderColDebitCard(false);
				setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
				setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
				setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
				setColCash(personalRemitObj.getColAmountDT());
				coldatatablevalues.remove(personalRemitObj);
			} else {
				setBooRenderColDebitCard(true);
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
			}
		}
		if (coldatatablevalues.size() != 0) {
			setBooRendercollectiondatatable(true);
		} else {
			setBooRendercollectiondatatable(false);
		}
	}*/

	// checking customer name and debit card name of card
	public void firstNameCheck() {

		String errMsg = validateDebitCard();

		if(errMsg != null){
			setExceptionMessage(errMsg);
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}else{
			if(getColNameofCard() != null && getCustomerFullName() != null){
				if (getCustomerFullName().trim().equalsIgnoreCase(getColNameofCard().trim())) {
					setColAuthorizedby(null);
					setColpassword(null);
					setBooAuthozed(false);
				} else {
					List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService.getdebitAutendicationList();
					setEmpllist(localEmpllist);
					setColAuthorizedby(null);
					setColpassword(null);
					setBooAuthozed(true);
					// populate alert msg if customer name not match
					setExceptionMessage(Constants.NameCheckAlertMsg);
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}
		}
	}

	// card number check calling procedure EX_P_VALIDATE_DEBITCARD
	public String validateDebitCard(){
		String errorMsg = null;
		try {
			HashMap<String, Object> inputValues = new HashMap<String, Object>();
			inputValues.put("P_APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId());
			inputValues.put("P_CUSTOMER_ID", getCustomerNo());
			inputValues.put("P_DEBIT_CARD", getColCardNo());
			inputValues.put("P_DB_CARD_NAME", getColNameofCard());
			inputValues.put("P_BANK_CODE", getColBankCode());

			log.info("EX_P_VALIDATE_DEBITCARD INPUT: "+inputValues.toString());

			errorMsg = iPersonalRemittanceService.validateDebitCardNo(inputValues);

			log.info("EX_P_VALIDATE_DEBITCARD OUTPUT: "+errorMsg);
		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorMsg;
	}

	// to remove details from data table after adding
	public void deletePaymentModeRecords(PersonalRemittanceCollectionDataTable collectionDt) {

		if (coldatatablevalues.size() > 0) {
			subtractedAmount = getToalUsedAmount().subtract(collectionDt.getColAmountDT());
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

		coldatatablevalues.remove(collectionDt);
		if (coldatatablevalues.size() != 0) {
			setBooRendercollectiondatatable(true);
			setBoRenderTotalAmountCalPanel(true);
		} else {
			setBooRendercollectiondatatable(false);
			setBoRenderTotalAmountCalPanel(false);
			clearingDetailAfterAdding();
		}
		setPaymentmodeFocus(true);
		setAppNoFocus(false);
	}

	public void onCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {


		BigDecimal qty = null;

		if(foreignLocalCurrencyDataTable.getQty() == "" && foreignLocalCurrencyDataTable.getQty()!=null)
		{
			qty = new BigDecimal(0);
			System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq ZEROOOOOOOOOOOOOOOO");
		}
		else
		{
			System.out.println("foreignLocalCurrencyDataTable.getQty()" + foreignLocalCurrencyDataTable.getQty());

			try {
				qty= new BigDecimal(foreignLocalCurrencyDataTable.getQty());
			} catch (Exception e) {

				System.out.println("Exception occcured "+e	);
				System.out.println("Exception occcured "+e	);
				System.out.println("Exception occcured "+e	);
				qty = new BigDecimal(0);

			}

			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		}
		BigDecimal totalcashAmount =null;
		try {
			totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(qty);
		} catch (Exception e) {
			System.out.println("Exceptionnnnnnnnnnnnn---------------------->QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ"+e);
		}



		System.out.println("@@@@@@@@@@@@@@@@@"+foreignLocalCurrencyDataTable.getQty()==null);
		System.out.println("#################"+foreignLocalCurrencyDataTable.getQty().equals(""));

		if (foreignLocalCurrencyDataTable.getQty().equals("")) {
			foreignLocalCurrencyDataTable.setQty("");
			//added by  rabil for clear if blank
			foreignLocalCurrencyDataTable.setPrice("");
		}
		if(totalcashAmount!=null && totalcashAmount.doubleValue()!=0.0){

			try {
				foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))).toPlainString());	
			} catch (Exception e) {
				System.out.println("Exceptionnnnnnnnnnnnn---------------------->11111"+e);
			}

		}else{
			foreignLocalCurrencyDataTable.setPrice("");
		}
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
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
			for (ForeignLocalCurrencyDataTable element : lstData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}
			setDenomtotalCash(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setCollectedAmount(getDenomtotalCash());
			RequestContext.getCurrentInstance().execute("cleardenominationexceed.show();");
			setDataTableClear(foreignLocalCurrencyDataTable);
			foreignLocalCurrencyDataTable.setQty("");
			return;

		} else {
			try {
				setDenomtotalCash(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			} catch (Exception e) {
				System.out.println("Exceptionnnnnnnnnnnnn---------------------->33333333"+e);
			}

			setCollectedAmount(getDenomtotalCash());
		}

	}


	ForeignLocalCurrencyDataTable dataTableClear = new ForeignLocalCurrencyDataTable() ;


	public ForeignLocalCurrencyDataTable getDataTableClear() {
		return dataTableClear;
	}
	public void setDataTableClear(ForeignLocalCurrencyDataTable dataTableClear) {
		this.dataTableClear = dataTableClear;
	}
	public void clearDataTableClearDenomination()
	{
		if(getDataTableClear()!=null)
		{
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable = getDataTableClear();

			System.out.println("foreignLocalCurrencyDataTable"+ foreignLocalCurrencyDataTable);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	/*public void onRefundCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {


		int quantity = 0;

		if(foreignLocalCurrencyDataTable.getQty()=="" && foreignLocalCurrencyDataTable.getQty()!=null)
		{
			quantity = 0;
		}
		else
		{
			try
			{
				quantity = Integer.parseInt(foreignLocalCurrencyDataTable.getQty());
			}
			catch(Exception e)
			{
				System.out.println("Exception occured" +e);
				quantity =0;
			}
		}


		if(foreignLocalCurrencyDataTable.getStock()>=quantity && quantity!=0){
		BigDecimal totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(new BigDecimal(foreignLocalCurrencyDataTable.getQty() == "" ? "0" : foreignLocalCurrencyDataTable.getQty()));
		if (foreignLocalCurrencyDataTable.getQty().equals("")) {
			foreignLocalCurrencyDataTable.setQty("");
			//added by  rabil for clear if blank
			foreignLocalCurrencyDataTable.setPrice("");
		}
		if(totalcashAmount!=null && totalcashAmount.doubleValue()!=0){
		foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))).toPlainString());
		}else{
			foreignLocalCurrencyDataTable.setPrice("");	
		}
		BigDecimal totalSum = BigDecimal.ZERO;
		 Responsible to calculate sum of entered cash amount 
		for (ForeignLocalCurrencyDataTable element : lstRefundData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum.add(new BigDecimal(element.getPrice()));
			}
		}
		BigDecimal totalDenoCash = getDenomtotalCash();
		if (getRefundAmount().compareTo(totalSum) < 0) {
			totalSum = BigDecimal.ZERO;
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
			for (ForeignLocalCurrencyDataTable element : lstRefundData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}
			setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
			RequestContext.getCurrentInstance().execute(
					"clearrefunddenominationexceed.show();");
			return;
		} else {
			setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
		}
		}else{
			RequestContext.getCurrentInstance().execute("stockNotAvailable.show();");
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
		}
	}*/


	public void onRefundCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {
		int quantity = 0;
		if((foreignLocalCurrencyDataTable.getQty()=="") || (foreignLocalCurrencyDataTable.getQty() != null && foreignLocalCurrencyDataTable.getQty().equalsIgnoreCase("0")))
		{
			quantity = 0;
			BigDecimal totalSum = BigDecimal.ZERO;
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
			for (ForeignLocalCurrencyDataTable element : lstRefundData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}
			setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
		}
		else
		{
			try
			{
				quantity = Integer.parseInt(foreignLocalCurrencyDataTable.getQty());

				if(foreignLocalCurrencyDataTable.getStock()>=quantity && quantity!=0){
					BigDecimal totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(new BigDecimal(foreignLocalCurrencyDataTable.getQty() == "" ? "0" : foreignLocalCurrencyDataTable.getQty()));
					if (foreignLocalCurrencyDataTable.getQty().equals("")) {
						foreignLocalCurrencyDataTable.setQty("");
						//added by  rabil for clear if blank
						foreignLocalCurrencyDataTable.setPrice("");
					}
					if(totalcashAmount!=null && totalcashAmount.doubleValue()!=0){
						foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))).toPlainString());
					}else{
						foreignLocalCurrencyDataTable.setPrice("");	
					}
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
						foreignLocalCurrencyDataTable.setQty("");
						foreignLocalCurrencyDataTable.setPrice("");
						for (ForeignLocalCurrencyDataTable element : lstRefundData) {
							if (element.getPrice().length() != 0) {
								totalSum = totalSum.add(new BigDecimal(element.getPrice()));
							}
						}
						setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
						RequestContext.getCurrentInstance().execute("clearrefunddenominationexceed.show();");
						return;
					} else {
						setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					}
				}else{
					RequestContext.getCurrentInstance().execute("stockNotAvailable.show();");
					BigDecimal totalSum = BigDecimal.ZERO;
					foreignLocalCurrencyDataTable.setQty("");
					foreignLocalCurrencyDataTable.setPrice("");
					for (ForeignLocalCurrencyDataTable element : lstRefundData) {
						if (element.getPrice().length() != 0) {
							totalSum = totalSum.add(new BigDecimal(element.getPrice()));
						}
					}
					setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
				}
			}
			catch(Exception e)
			{
				System.out.println("Exception occured" +e);
				quantity =0;
			}
		}
	}


	private Boolean renderEmailForReport=false;
	private String emailToSendReport;
	private Boolean checkEmailForReport=false;
	private Boolean savetimeReportEmailCheck=false;




	public Boolean getRenderEmailForReport() {
		return renderEmailForReport;
	}
	public String getEmailToSendReport() {
		return emailToSendReport;
	}
	public Boolean getCheckEmailForReport() {
		return checkEmailForReport;
	}
	public Boolean getSavetimeReportEmailCheck() {
		return savetimeReportEmailCheck;
	}
	public void setRenderEmailForReport(Boolean renderEmailForReport) {
		this.renderEmailForReport = renderEmailForReport;
	}
	public void setEmailToSendReport(String emailToSendReport) {
		this.emailToSendReport = emailToSendReport;
	}
	public void setCheckEmailForReport(Boolean checkEmailForReport) {
		this.checkEmailForReport = checkEmailForReport;
	}
	public void setSavetimeReportEmailCheck(Boolean savetimeReportEmailCheck) {
		this.savetimeReportEmailCheck = savetimeReportEmailCheck;
	}


	public void showEmailInputField(){
		setEmailToSendReport(null);
		if(getCheckEmailForReport()){
			setRenderEmailForReport(true);
			setEmailToSendReport(getCustomerEmail());
		}else{
			setRenderEmailForReport(false);
		}
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
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("backToRemitanceFirstPanel", Constants.YES);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
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
						//checkEmailVerification();
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
					//checkEmailVerification();
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
				//checkEmailVerification();
			}
		}
		//		i=0;	

		return rtnStrin;
	}

	// saving
	/*public void saveRemittance() {
		setBooDeclarationReportDisplay(false);
		// if round functionality done then Ex_Appl_Trnx record changed
		if (lstModifyRoundRecord.size() != 0) {
			ShoppingCartDataTableBean lstModifyRec = lstModifyRoundRecord.get(0);
			iPersonalRemittanceService.saveApplicationTransactionModifiedRecordByRound(lstModifyRec);
			// modified record storing separetly
			try{
			List<RemittanceReportBean> lstModified = fetchApplicationRemittanceReportData(lstModifyRec.getDocumentNo());
			}catch(Exception e){
				log.info("Exception in fetchApplicationRemittanceReportData"+e.getMessage());
			}
			setBooRenderModifiedApplTrnxReportByRound(true);
		} else {
			setBooRenderModifiedApplTrnxReportByRound(false);
		}

		// updating Status "S" in different Tables
		updadateRecords(Constants.S);
		System.out.println("=====================CALLED SAVE ALL");
		String out = null;
		String errormsg = null;
		Connection connection = null;
		// connection = DBConnection.getdataconnection();
		connection = generalService.getDataSourceFromHibernateSession();

		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_INSERT_REMITTANCE_TRANX(?,?,?,?,?)}");
			cs.setBigDecimal(1, getCustomerNo());
			cs.setString(2, sessionStateManage.getUserName());
			cs.setBigDecimal(3, getColremittanceNo());
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.execute();
			out = cs.getString(4);
			errormsg = cs.getString(5);

			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo():"+getCustomerNo());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName():"+sessionStateManage.getUserName());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX  getColremittanceNo():"+ getColremittanceNo());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX out:"+out);
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX errormsg:"+errormsg);

			System.out.println("================Output=========" + out+"\t errormsg :"+errormsg);
			if (out.equalsIgnoreCase("PASS")) {
				HashMap<String, Object> returnResult = saveCollect();
				saveForeignCurrencyAdjust(returnResult);
				updateRemittanceTransactionSelectedList(returnResult);
				lstselectedrecord.clear();
				// RequestContext.getCurrentInstance().execute("remittanceSave.show();");
				// setApplicationReportPanel(false);
				allPanelOff();
				setMainPanelRender(false);
				//setReceiptReportPanel(true);
				rtnStrin = "personalRemittanceSuccessPage";
				remittanceNo = new BigDecimal(0);
				fcsaleNo = new BigDecimal(0);
				cashAmount = new BigDecimal(0);
				coldatatablevalues.clear();

				// calling EX_INSERT_EMOS_TRANSFER_LIVE procedure changes - 17/12/2015 
				Collect collect = (Collect)returnResult.get("Collect");

				if (collect != null) {
					try {
						iPersonalRemittanceService.insertEMOSLIVETransfer(collect.getApplicationCountryId(), collect.getFsCompanyMaster().getCompanyId(), collect.getDocumentCode(), collect.getDocumentFinanceYear(), collect.getDocumentNo());
					} catch (AMGException e) {
						log.info("Exception occurs while moving data to Old Emos:" + collect.getDocumentFinanceYear() + " - " + collect.getDocumentNo());
					} catch (Exception e1) {
						log.info("Exception occurs while moving data to Old Emos:" + collect.getDocumentFinanceYear() + " - " + collect.getDocumentNo());
					}
				}


			} else {
				String status = null;
				// updating Status "null" in different Tables
				updadateRecords(status);
				System.out.println(" Records Not Saved : " + errormsg);
				setErrmsg(errormsg);
				RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			}
		} catch (SQLException e) {
			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			System.out.println(" Records Not Saved : " + errormsg);
			setErrmsg(errormsg);
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				setErrmsg(errormsg);
				RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			}
		}
	}*/

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

	/*// save records of fs in collection and collection details from view
	public TempCollection saveCollect() {

		TempCollection collect = new TempCollection();

		try{

			if (lstselectedrecord.size() > 0) {
				//fetch doc number which is available
				setDocumentNo(getDocumentSerialCollectTrnx(Constants.Yes));

				ShoppingCartDataTableBean shoppingCartDetails = lstselectedrecord.get(0);

				// company Id
				CompanyMaster companymaster = new CompanyMaster();
				companymaster.setCompanyId(shoppingCartDetails.getCompanyId());
				collect.setFsCompanyMaster(companymaster);
				// app country Id
				collect.setApplicationCountryId(sessionStateManage.getCountryId());
				// customer Id
				Customer customer = new Customer();
				customer.setCustomerId(shoppingCartDetails.getCustomerId());
				collect.setFsCustomer(customer);
				// document Id
				collect.setDocumentNo(new BigDecimal(getDocumentNo()));
				collect.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION));
				// Document Financial Year
				collect.setDocumentFinanceYear(shoppingCartDetails.getDocumentFinanceYear());
				collect.setCollectDate(new Date());
				// Foriegn Currency id //It should be always local currecny //added by Rabil
				CurrencyMaster forcurrencymaster = new CurrencyMaster();
				forcurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));			
				collect.setExCurrencyMaster(forcurrencymaster);
				// CountryBranch ID 
				CountryBranch bankbranch = new CountryBranch();
				bankbranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
				collect.setExBankBranch(bankbranch);

				collect.setPaidAmount(getPayPaidAmount());
				collect.setRefoundAmount(getPayRefund());
				collect.setNetAmount(getPayNetPaidAmount());
				collect.setIsActive(Constants.Yes);
				collect.setReceiptType(Constants.RECEIPT_TYPE);
				collect.setGeneralLegerDate(new Date());
				collect.setCreatedBy(sessionStateManage.getUserName());
				collect.setCreatedDate(new Date());

				try {
					collect.setAccountMMYYYY(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				//partical save 24/01/2016 added
				//iPersonalRemittanceService.saveCollectTableData(collect);

				//collectIdDetails.add(collect);
				//returnResult.put("Collect", collect);
				//returnResult.put("ExchangeRate",shoppingCartDetails.getExchangeRateApplied());
				//returnResult.put("LocalTrnsAmount",shoppingCartDetails.getLocalNextTranxAmount());

				//partical save 24/01/2016 added
				//saveCollectionDetail(collect);
			}

		}catch(Exception e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
		}

		return collect;
	}*/

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

			
			List<AuthicationLimitCheckView> declarationReportTotal = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.DeclarationReportTotalAmt);
			BigDecimal declarationTotalAmt = BigDecimal.ZERO;
			if(declarationReportTotal.size() != 0){
				AuthicationLimitCheckView authRemitAmtLimit = declarationReportTotal.get(0);
				declarationTotalAmt = authRemitAmtLimit.getAuthLimit();
			}
			
			if(getPayNetPaidAmount().compareTo(declarationTotalAmt)>=1)
			{
				log.info("========================= total");
				setBooDeclarationReportDisplay(true);
				
				collect.setCashDeclarationIndicator(Constants.Yes);
				//iPersonalRemittanceService.updateDeclarationIndicatorTotal(collectionId);
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


	/*	
public List<CollectDetail> saveCollectionDetail(Collect collect) {

		List<CollectDetail> lstCollDt = new ArrayList<CollectDetail>();

		try{
			int i = 1;
			BigDecimal totalAmt=BigDecimal.ZERO;
			setBooDeclarationReportDisplay(false);
			for (PersonalRemittanceCollectionDataTable lstofPayment : coldatatablevalues) {

				CollectDetail collectDetails = new CollectDetail();

				Collect collection = new Collect();
				collection.setCollectionId(collect.getCollectionId());
				collectDetails.setCashCollectionId(collection);
				// customer Id
				Customer customer = new Customer();
				customer.setCustomerId(collect.getFsCustomer().getCustomerId());
				collectDetails.setFsCustomer(customer);
				// Application Country
				CountryMaster appcountrymaster = new CountryMaster();
				appcountrymaster.setCountryId(sessionStateManage.getCountryId());
				collectDetails.setFsCountryMaster(appcountrymaster);
				// company Id
				CompanyMaster companymaster = new CompanyMaster();
				companymaster.setCompanyId(collect.getFsCompanyMaster().getCompanyId());
				collectDetails.setFsCompanyMaster(companymaster);
				// document Id
				collectDetails.setDocumentNo(collect.getDocumentNo());
				collectDetails.setDocumentCode(collect.getDocumentCode());
				// Document Financial Year
				collectDetails.setDocumentFinanceYear(collect.getDocumentFinanceYear());
				// Routing Bank Branch
				CountryBranch bankbranch = new CountryBranch();
				bankbranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
				collectDetails.setExBankBranch(bankbranch);
				collectDetails.setDocumentDate(new Date());
				collectDetails.setDocumentLineNo(new BigDecimal(i++));
				// Foriegn Currency id //It should be loccal currency Id
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
					if(lstofPayment.getColAmountDT().compareTo(new BigDecimal(2500))>=1)
					{
						setBooDeclarationReportDisplay(true);
					}
				}

				collectDetails.setIsActive(Constants.Yes);
				try {
					collectDetails.setAcyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				collectDetails.setCreatedBy(sessionStateManage.getUserName());
				collectDetails.setCreatedDate(new Date());

				collectDetails.setPaymentModeId(lstofPayment.getColpaymentmodeIDtypeDT());

				//iPersonalRemittanceService.saveCollectDetailTableData(collectDetails);
				lstCollDt.add(collectDetails);
			}
			if(totalAmt.compareTo(new BigDecimal(10000))>=1)
			{
				setBooDeclarationReportDisplay(true);
			}
			return lstCollDt;
		}catch(Exception e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			return null;
		}
	}*/



	public List<TempCollectDetail> saveCollectionDetail(TempCollection collect) {
		remitDeclarionMainReport =new RemittanceDeclarationMainReportBean();
		int i = 1;
		BigDecimal totalAmt=BigDecimal.ZERO;
		//setBooDeclarationReportDisplay(false);
		List<TempCollectDetail> tempCollectionDetails = new ArrayList<TempCollectDetail>();
		coldatatablevaluesListForReport.clear();
		coldatatablevaluesListForReport.addAll(coldatatablevalues );
		//setColdatatablevaluesListForReport(coldatatablevalues);
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
   
			  /*
			if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode))
			{
				List<AuthicationLimitCheckView> declarationReportCashAmt = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.DeclarationReportforCash);
				
				BigDecimal declarationCashAmt = BigDecimal.ZERO;
				if(declarationReportCashAmt.size() != 0){
					AuthicationLimitCheckView authRemitAmtLimit = declarationReportCashAmt.get(0);
					declarationCashAmt = authRemitAmtLimit.getAuthLimit();
				}
				
				/* Old code 
				 * if(lstofPayment.getColAmountDT().compareTo(declarationCashAmt)>=1)
				{
					setBooDeclarationReportDisplay(true);
					//remitDeclarionMainReport.setDeclaration2500Check(true);
				}
				
				// New Code added on 29/08/2017
				if(lstofPayment.getColAmountDT().compareTo(new BigDecimal(2500))>=1)
				{
				setBooDeclarationReportDisplay(true);
				//remitDeclarionMainReport.setDeclaration2500Check(true);
				}
				
			}*/
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

		/*List<AuthicationLimitCheckView> declarationReportTotal = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.DeclarationReportTotalAmt);
		BigDecimal declarationTotalAmt = BigDecimal.ZERO;
		if(declarationReportTotal.size() != 0){
			AuthicationLimitCheckView authRemitAmtLimit = declarationReportTotal.get(0);
			declarationTotalAmt = authRemitAmtLimit.getAuthLimit();
		}*/
		
		/*if(totalAmt.compareTo(declarationTotalAmt)>=1)
		{
			//remitDeclarionMainReport.setDeclaration10000Check( true);
			setBooDeclarationReportDisplay(true);
		}*/

		return tempCollectionDetails;

	}


	public void updateRemittanceTransactionSelectedList(Collect collect) {
		try{
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
		}catch(Exception e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
		}
	}

	/**
	 * Foreign Currency Adjust Save
	 */


	/*public List<ForeignCurrencyAdjust> saveForeignCurrencyAdjust(Collect collect) {

		List<ForeignCurrencyAdjust> lstdenominationRecords = new ArrayList<ForeignCurrencyAdjust>();
		try{
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
			// String date = "01/09/14" ;Collect collectid , BigDecimal exchangerate
			// , BigDecimal localnettrastamount
			Date acc_Month = null;
			try {
				acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//need to check for clarification
			ShoppingCartDataTableBean shoppingCartDetails = null;
			if (lstselectedrecord.size() > 0) {
				shoppingCartDetails = lstselectedrecord.get(0); 
			}

			int i = 0;
			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstData) {
				System.out.println("foreignLocalCurrencyDataTable.getQty() ^^^^^^^^^^^^^^^^^"+ foreignLocalCurrencyDataTable.getQty());
				if (!foreignLocalCurrencyDataTable.getQty().equals("")&& foreignLocalCurrencyDataTable.getQty()!= null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
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
					currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));

					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();

					denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());

					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
					foreignCurrencyAdjust.setExchangeRate(shoppingCartDetails.getExchangeRateApplied());  // only first record exchange rate 
					foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());
					foreignCurrencyAdjust.setDocumentFinanceYear(getFinaceYear());
					foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());

					try {
						foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE)); 
						foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
						foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
						CountryBranch countryBranch = new CountryBranch();
						countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
						foreignCurrencyAdjust.setCountryBranch(countryBranch);

						foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
						foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);

						// Collecting from Customer : Transaction Type will be C
						// Refund to Customer: Transaction type will be F
						foreignCurrencyAdjust.setTransactionType(Constants.C);

					} catch (Exception e) {
						e.printStackTrace();
					}

					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());
					if (collect != null) {
						foreignCurrencyAdjust.setDocumentNo(collect.getDocumentNo());
						foreignCurrencyAdjust.setCollect(collect);
					}
					// partical save added 24/01/2016
					//iPersonalRemittanceService.saveForeignCurrencyAdjust(foreignCurrencyAdjust);
					lstdenominationRecords.add(foreignCurrencyAdjust);
				} else {
					log.info("Number of notes is 0");
				}
			}
			int j = 0;
			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstRefundData) {
				System.out.println("foreignLocalCurrencyDataTable.getQty() ^^^^^^^^^^^^^^^^^"+ foreignLocalCurrencyDataTable.getQty());
				if (!foreignLocalCurrencyDataTable.getQty().equals("") && foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {
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
					currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
					foreignCurrencyAdjust.setExchangeRate(shoppingCartDetails.getExchangeRateApplied()); // only first record exchange rate 
					foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());
					foreignCurrencyAdjust.setDocumentFinanceYear(getFinaceYear());
					foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());

					try {
						foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE)); 
						foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++j));
						foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
						CountryBranch countryBranch = new CountryBranch();
						countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
						foreignCurrencyAdjust.setCountryBranch(countryBranch);
						foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
						foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);

						// Collecting from Customer : Transaction Type will be C
						// Refund to Customer: Transaction type will be F
						foreignCurrencyAdjust.setTransactionType(Constants.F);

					} catch (Exception e) {
						e.printStackTrace();
					}

					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());
					if (collect != null) {
						foreignCurrencyAdjust.setDocumentNo(collect.getDocumentNo());
						foreignCurrencyAdjust.setCollect(collect);
					}
					// partical save added 24/01/2016
					//iPersonalRemittanceService.saveForeignCurrencyAdjust(foreignCurrencyAdjust);
					lstdenominationRecords.add(foreignCurrencyAdjust);
				} else {
					log.info("Number of notes is 0");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lstdenominationRecords;
	}*/




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

	public void updadateRecords(String status) {
		BigDecimal financialYearId = getFinaceYearId();
		for (ShoppingCartDataTableBean shoppingCartDataTableObj : shoppingcartDTList) {
			if (shoppingCartDataTableObj.getSelectedrecord()) {
				if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.FCSale)) {
					if(shoppingCartDataTableObj.getSelectedrecord()){
						BigDecimal paymentReciptPk = iPersonalRemittanceService.getReceiptPaymentTablePk(shoppingCartDataTableObj.getCustomerId(),shoppingCartDataTableObj.getDocumentNo());
						iPersonalRemittanceService.updateReceiptPaymentTableData(paymentReciptPk,status);
						List<ForeignCurrencyAdjustApp> foreignCurrencyAdjustAppList = iPersonalRemittanceService.getForeignCurrencyAdjustAppPk(shoppingCartDataTableObj.getCustomerId(),shoppingCartDataTableObj.getDocumentNo());
						for (ForeignCurrencyAdjustApp foreignCurrencyAdjustAppPk : foreignCurrencyAdjustAppList) {
							iPersonalRemittanceService.updateForeignCurrencyAdjustAppTableData(foreignCurrencyAdjustAppPk.getForeignCurrencyAdjustId(),status);
						}
					}
				} else if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
					if(shoppingCartDataTableObj.getSelectedrecord()){
						//BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo());
						BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo(),shoppingCartDataTableObj.getDocumentId(),financialYearId,shoppingCartDataTableObj.getCompanyId());
						if (remittanceAppPk != null) {
							iPersonalRemittanceService.updateRemittanceApplicationTableData(remittanceAppPk, status);
						}
					}
				}
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
	
	
	/*public boolean updadateRecordsNotNull(String status) {

		BigDecimal financialYearId = getFinaceYearId();

		for (ShoppingCartDataTableBean shoppingCartDataTableObj : lstselectedrecord) {
			if (shoppingCartDataTableObj.getSelectedrecord()) {
				if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
					if (shoppingCartDataTableObj.getSelectedrecord()) {
						BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationstatus(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo(),shoppingCartDataTableObj.getDocumentId(),status,financialYearId,shoppingCartDataTableObj.getCompanyId());
						if (remittanceAppPk == null) {
							return false;
						}
					}
				}
			}
		}
		for (ShoppingCartDataTableBean shoppingCartDataTableObj : lstselectedrecord) {
			if (shoppingCartDataTableObj.getSelectedrecord()) {
				if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
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
	}*/

	/*public void updadateRecordsNull(String status) {
		BigDecimal financialYearId = getFinaceYearId();
		for (ShoppingCartDataTableBean shoppingCartDataTableObj : lstselectedrecord) {
			if (shoppingCartDataTableObj.getSelectedrecord()) {
				if (shoppingCartDataTableObj.getApplicationType().equalsIgnoreCase(Constants.Remittance)) {
					BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(),shoppingCartDataTableObj.getDocumentNo());
					iPersonalRemittanceService.updateRemittanceApplicationTableData(remittanceAppPk,status);
					BigDecimal remittanceAppPk = iPersonalRemittanceService.getRemittanceApplicationPk(shoppingCartDataTableObj.getCustomerId(), shoppingCartDataTableObj.getDocumentNo(),shoppingCartDataTableObj.getDocumentId(),financialYearId,shoppingCartDataTableObj.getCompanyId());
					if (remittanceAppPk != null) {
						iPersonalRemittanceService.updateRemittanceApplicationTableData(remittanceAppPk, status);
					}
				}
			}
		}
	}*/


	private void refundDenominationData() {
		lstRefundData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		/* Checking that it's first time or not, first time list size will be 0 */
		//	double sAmount = 0;
		localLstData.clear();
		if (localLstData.size() == 0) {
			/* Responsible to show serial number in datatable */
			/*
			 * Responsible to hold each row in different bean object of
			 * datatable
			 */
			/*ForeignLocalCurrencyDataTable item = null;
			List<Stock> dataFromDb = foreignLocalCurrencyDenominationService
					.getLocalCurrencyDenominationFromStock(
							sessionStateManage.getCountryId(),
							sessionStateManage.getUserName(),
							sessionStateManage.getBranchId(),
							sessionStateManage.getCompanyId(),
							sessionStateManage.getCurrencyId());
			 putting the value in list to show in datatable 
			for (Stock element : dataFromDb) {
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
				if (stock > 0) {
					denamination = Double.parseDouble(element
							.getDenominationId().getDenominationAmount()
							.toString());
					if (denamination <= sAmount) {
						count = sAmount / denamination;
					}
					totalNotes = totalNotes + count;
					sAmount = sAmount % denamination;
					if (count != 0) {
						qty = new Integer((int) Math.floor(count)).toString();
						cal = new Integer((int) Math.floor(count));
						result = cal * denamination;
						amount = new Integer((int) Math.ceil(result))
						.toString();
					} else {
						qty = "";
						amount = "";
					}
					item = new ForeignLocalCurrencyDataTable(++i, element
							.getDenominationId().getDenominationAmount(), qty,
							amount, stock, element.getStockId(), element
							.getDenominationId().getDenominationId(),
							element.getDenominationId().getExCurrencyMaster()
							.getCurrencyId(), element
							.getDenominationId().getDenominationDesc(),
							element.getDenominationId().getDenominationAmount());
					localLstData.add(item);
				}
			}
		}

			 */

			List<ViewStock> stockList = iPersonalRemittanceService.toCheckStockForView(
					sessionStateManage.getCountryId(),
					sessionStateManage.getUserName(),
					sessionStateManage.getBranchId(),
					sessionStateManage.getCompanyId(),
					sessionStateManage.getCurrencyId());


			int serial = 1;
			for(ViewStock viewStockObj:stockList){
				ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
				forLocalCurrencyDtObj.setSerial(serial);
				forLocalCurrencyDtObj.setItem(viewStockObj.getDenominationAmount());
				forLocalCurrencyDtObj.setQty("");
				forLocalCurrencyDtObj.setPrice("");
				if(viewStockObj.getCurrentStock()!=null){
					forLocalCurrencyDtObj.setStock(viewStockObj.getCurrentStock().intValue());
				}else{
					forLocalCurrencyDtObj.setStock(0);
				}
				forLocalCurrencyDtObj.setDenominationId(viewStockObj.getDenominationId());
				forLocalCurrencyDtObj.setCurrencyId(viewStockObj.getCurrencyId());
				forLocalCurrencyDtObj.setDenominationDesc(viewStockObj.getDenominationDEsc());
				forLocalCurrencyDtObj.setDenominationAmount(viewStockObj.getDenominationAmount());

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
		setRefundAmount(getPayRefund());
		setCollectedRefundAmount(new BigDecimal(0));
		setLstRefundData(localLstData);
		if(localLstData.size() != 0){
			setDenominationchecking(Constants.DenominationAvailable);
		}else{
			setDenominationchecking(Constants.DenominationNotAvailable);
		}

	}

	public void generatePersonalRemittanceApplicationModified(ActionEvent actionEvent){
		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		OutputStream outstream=null;
		try {
			remittanceApplicationReportInit();
			
			String fileName = "RemittanceApplicationReport.pdf";
			printJasperDirectToPrinter(fileName);
			
			// blocked due direct print
			/*HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=RemittanceApplicationReport.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();

			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

			outstream = httpServletResponse.getOutputStream();
			httpServletResponse.setContentType("application/pdf");
			httpServletResponse.setContentLength(pdfasbytes.length);
			String strSettings = "inline;filename=\"RemittanceApplicationReport.pdf\"";
			httpServletResponse.setHeader("Content-Disposition", strSettings);
			outstream.write(pdfasbytes, 0, pdfasbytes.length);
			outstream.flush();
			FacesContext.getCurrentInstance().responseComplete();*/


		} catch (Exception e) {
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
				try {
					outstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//hide panels 
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
		setBooRenderColCheque(false);
		setBooRenderColBankTransfer(false);
		setBooRendercollectiondatatable(false);
		//ninth panel
		setBooRenderPaymentDetails(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);		
		setPaymentDeatailsPanel(false);
		//final report panel
		setReceiptReportPanel(false);
		setBooRenderBeneAddreddDetails(false);
	}

	// first panel rendering
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

	// second panel rendering
	public void backFromBenificaryStatusPanel() {
		//first panel
		setMainPanelRender(true);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificaryFirstPanel(false);
		//second panel
		setBooRenderBenificarySearchPanel(true);
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

	// third panel rendering
	public void getServiceFirstPanel() {
		//first panel
		setMainPanelRender(true);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificaryFirstPanel(false);
		//second panel
		setBooRenderBenificarySearchPanel(false);
		//third panel
		setBooRenderRemittanceServicePanel(true);
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
		setBooRenderColCheque(false);
		setBooRenderColBankTransfer(false);
		setBooRendercollectiondatatable(false);
		//ninth panel
		setBooRenderPaymentDetails(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);		
		setPaymentDeatailsPanel(false);
		//final report panel
		setReceiptReportPanel(false);
	}

	// fourth panel rendering
	public void getServiceSecondPanel() {
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
		setBooRenderLayaltyServicePanel(true);
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
		setBooRenderColCheque(false);
		setBooRenderColBankTransfer(false);
		setBooRendercollectiondatatable(false);
		//ninth panel
		setBooRenderPaymentDetails(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);		
		setPaymentDeatailsPanel(false);
		//final report panel
		setReceiptReportPanel(false);
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
		setBooRenderColCheque(false);
		setBooRenderColBankTransfer(false);
		setBooRendercollectiondatatable(false);
		//ninth panel
		setBooRenderPaymentDetails(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);		
		setPaymentDeatailsPanel(false);
		//final report panel
		setReceiptReportPanel(false);
	}

	// sixth panel rendering
	public void booAmlCheckBack() {
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
		setBooRenderLayaltyServicePanel(true);
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
		setBooRenderColCheque(false);
		setBooRenderColBankTransfer(false);
		setBooRendercollectiondatatable(false);
		//ninth panel
		setBooRenderPaymentDetails(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);		
		setPaymentDeatailsPanel(false);
		//final report panel
		setReceiptReportPanel(false);

		slb1total1 = 0.0;
		slb1total2 = 0.0;
		slb1total3 = 0.0;
		slb1total4 = 0.0;
		slb1total5 = 0.0;
		slb1total6 = 0.0;
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
		setPaymentmodeFocus(true);
		setAppNoFocus(false);
		setBackNavigation(false);
		setCalGrossAmount(null);
		setCalNetAmountPaid(null);
		coldatatablevalues.clear();
		setColpaymentmodeId(null);
		setToalUsedAmount(null);
		setTotalbalanceAmount(null);
		setTotalrefundAmount(null);
		setColCash(null);
		setColBankCode(null);
		setColBankTransferBankCode(null);
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
			backToBeneficiaryRecordsByCountry();
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

	// to render panel 8 - Refund Denomination details
	public void nextpaneltoRefundDenomination() {
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
		setBooRenderColCheque(false);
		setBooRenderColBankTransfer(false);
		setBooRendercollectiondatatable(false);
		//ninth panel
		setBooRenderPaymentDetails(true);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(true);		
		setPaymentDeatailsPanel(true);
		//final report panel
		setReceiptReportPanel(false);
		setPaymentDetailsremark(null);
		refundDenominationData();
	}

	// clear Values
	public void clearData() {
		selectedSplDeal.clear();
		setDisableSpotRatePanel(false);
		setBooSpecialCusFCCalDataTable(false);
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
		setMasterId(null);
		setDataserviceid(null);
		setDatabenificaryservice(null);
		setDatabenificaryservicecode(null);
		setDataroutingcountrycode(null);
		setRoutingCountryName(null);
		setRoutingBank(null);
		setRoutingBankName(null);
		setDataroutingbankalphacode(null);
		setRoutingBranch(null);
		setRoutingBranchName(null);
		setRemitMode(null);
		setRemittanceName(null);
		setDeliveryMode(null);
		setDeliveryModeInput(null);
		setSourceOfIncome(null);
		setFurthuerInstructions(null);
		setBeneSwiftBank1(null);
		setBeneSwiftBank2(null);
		setSwiftId1(null);
		setSwiftId2(null);
		setBeneSwiftBankAddr1(null);
		setBeneSwiftBankAddr2(null);
		setDigitalSignature(null);
		setSignatureSpecimen(null);
		setSignatureMandatoryMsg(null);
		setBooRenderSignatureMsg(false);
		setDigitalSignature(null);
		setBeneficiaryAccountSeqId(null);
		setBeneficiaryRelationShipSeqId(null);
		//Added by Rabil on 09/03/2016
		setEquivalentRemitAmount(null);
		setEquivalentCurrency(null);
		setApprovalNo(null);
		setApprovalYear(null);
		setSpecialApprovalRadio(0);
		setApprovalRender(false);
		setSpotRateRender(false);
		// spl customer clear
		if (personalRemittCalFCAmountDTList != null && !personalRemittCalFCAmountDTList.isEmpty()) {
			personalRemittCalFCAmountDTList.clear();
		}
		setBooReadOnlyRemitAmount(false);
		setCountSplCust(BigDecimal.ZERO);

		// bene tel phone
		setDataBeneContactId(null);
		setBenificaryTelephone(null);
		setDataTempBeneTelNum(null);
		//Added by Rabil 
		setAmtbCoupon(null);
		setAmtbCouponRender(false);
		setAmtbCouponAmount(null);
		setAmtbCouponFinancialYear(null);

	}

	public void clearTransactionDetails() {
		setBooRenderPoll(false);
		setDatabenificarycountry(null);
		setDatabenificarycountrycode(null);
		setDataroutingcountrycode(null);
		setDatabenificarycurrency(null);
		setDatabenificaryservice(null);
		setDatabenificaryservicecode(null);
		setDataserviceid(null);
		setDataAccountnum(null);
		setDatabenificarycountryname(null);
		setDatabenificarycurrencyname(null);
		setDatabenificarybankname(null);
		setDatabenificarybankalphacode(null);
		setDatabenificarybranchname(null);
		setDatabenificaryname(null);
		setBenificiaryryNameRemittance(null);
		setMasterId(null);
		setBeneficaryBankId(null);
		setBeneficaryBankBranchId(null);
		setRoutingCountryName(null);
		setDataroutingcountrycode(null);
		setRoutingCountry(null);
		setDataroutingcountryalphacode(null);
		setRoutingBankName(null);
		setDataroutingbankalphacode(null);
		setRoutingBank(null);
		setRoutingBranchName(null);
		setRoutingBranch(null);
		setBenificarystatus(null);
		setBenificaryTelephone(null);
		setCurrency(null);
		setSpotRate(null);
		setSpecialRateRef(null);
		setAvailLoyaltyPoints(null);
		setChargesOverseas(null);
		setForiegnCurrency(null);

		// list Clear
		//Added by Rabil

		setServiceList(null);
		if(getServiceList()!=null && serviceList.size()!=0){
			serviceList.clear();
		}

		setRoutingCountrylst(null);
		if (getRoutingCountrylst() != null && routingCountrylst.size() != 0) {
			routingCountrylst.clear();
		}
		setRoutingbankvalues(null);
		if (getRoutingbankvalues() != null && routingbankvalues.size() != 0) {
			routingbankvalues.clear();
		}
		setLstofRemittance(null);
		if (getLstofRemittance() != null && lstofRemittance.size() != 0) {
			lstofRemittance.clear();
		}
		setLstofDelivery(null);
		if (getLstofDelivery() != null && lstofDelivery.size() != 0) {
			lstofDelivery.clear();
		}
		setRoutingBankBranchlst(null);
		if (getRoutingBankBranchlst() != null
				&& routingBankBranchlst.size() != 0) {
			routingBankBranchlst.clear();
		}

		//Added by Rabil

		setBooSingleRoutingCountry(true);
		setBooMultipleRoutingCountry(false);

		setBooSingleRoutingBank(true);
		setBooMultipleRoutingBank(false);
		setBooSingleRemit(true);
		setBooMultipleRemit(false);
		setBooRenderDeliveryModeInputPanel(true);
		setBooRenderDeliveryModeDDPanel(false);
		setBooSingleRoutingBranch(true);
		setBooMultipleRoutingBranch(false);
		// new Records from procedure
		setNewDeliveryModeId(null);
		setNewDeliveryModeName(null);
		setBooRenderDelivery(false);
		setNewRemittanceModeId(null);
		setNewRemittanceModeName(null);
		setBooRenderRemit(false);

		setPbeneFifthName(null);
		setPbeneFirstName(null);
		setPbeneFourthName(null);
		setPbeneFullName(null);
		setPbeneSecondName(null);
		setPbeneThirdName(null);

		setSpecialDealRate(null);
		setSpotExchangeRate(null);
		setSpotExchangeRatePk(null);

		setDigitalSignature(null);
		setSignatureSpecimen(null);
		setSignatureMandatoryMsg(null);
		setBooRenderSignatureMsg(false);
		// clearing bank bene id 
		setBeneStateId(null);
		setBeneDistrictId(null);
		setBeneCityId(null);
		//Swift
		setSwiftBic(null);
		//added By koti @10/08/2016
		setBeneAddressDetails(null);
		setBeneHouseNo(null);
		setBeneFlatNo(null);
		setBeneStreetNo(null);

	}

	// icash Product clear
	public void clearICashProduct(){
		// icash product
		setDatabenificarycurrencyalphacode(null);

		setIcashStateSubAgent(false);
		setIcashAgentPanel(false);
		setIcashEFT(false);
		setIcashTT(false);
		setIcashCash(false);
		setIcashState(null);
		setIcashAgent(null);
		setIcashSubAgent(null);
		if(lstIcashAgentsEFTDetails != null || !lstIcashAgentsEFTDetails.isEmpty()){
			lstIcashAgentsEFTDetails.clear();
		}
		if(lstIcashAgentsTTDetails != null || !lstIcashAgentsTTDetails.isEmpty()){
			lstIcashAgentsTTDetails.clear();
		}
		if(lstIcashState != null || !lstIcashState.isEmpty()){
			lstIcashState.clear();
		}
		if(lstIcashSubAgents != null || !lstIcashSubAgents.isEmpty()){
			lstIcashSubAgents.clear();
		}
	}

	// after adding data to datatable and then reseting
	public void clearingDetailAfterAdding() {
		setColpaymentmodeId(null);
		setColpaymentmodeName(null);
		setColpaymentmodeCode(null);
		setColBankCode(null);
		setColBankTransferBankCode(null);
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
		
		setLstBankPrefix(null);
		setBooRenderSingleeCardPrefix(true);
		setBooRenderMultipleCardPrefix(false);
		setDebitCardNoLength(null);
		setColDebitCardPrefex(null);
		setSingleDebitCardPrefex(null);

		setDigitalSignature(null);
		setSignatureSpecimen(null);

		lstDebitCard.clear();
		changeofPaymentMode();

	}

	public void assignNullValues() {
		// clearing beneficiary lists
		if(coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()){
			coustomerBeneficaryDTList.clear();
		}
		if(personalRemittCalFCAmountDTList != null || !personalRemittCalFCAmountDTList.isEmpty()){
			personalRemittCalFCAmountDTList.clear();
		}

		setBenificiaryryNameRemittance(null);
		setRoutingCountry(null);
		setDataroutingcountryalphacode(null);
		setRoutingBank(null);
		setRemitMode(null);
		setDeliveryMode(null);
		setRoutingBranch(null);

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
		setDatabenificarybankalphacode(null);
		setLoyaltyAmountAvailed(null);
		setDatabenificarybranchname(null);
		setNetAmountPayable(null);
		setNetAmountSent(null);
		setIcashCostRate(null);
		setFurthuerInstructions(null);
		setSourceOfIncome(null);
		almcheckList.clear();
		slb1total1 = 0.0;
		slb1total2 = 0.0;
		slb1total3 = 0.0;
		slb1total4 = 0.0;
		slb1total5 = 0.0;
		slb1total6 = 0.0;
		setAmlMessageOne(null);
		setAmlMessageTwo(null);
		setAmlMessageThree(null);
		setAmlAuthorizedBy(null);
		setAmlRemarks(null);
		setCalGrossAmount(null);
		setCalNetAmountPaid(null);
		setDigitalSignature(null);
		setSignatureSpecimen(null);
		// clearing bank bene id 
		setBeneStateId(null);
		setBeneDistrictId(null);
		setBeneCityId(null);
		setPbeneFullName(null);
		setPbeneFirstName(null);
		setPbeneSecondName(null);
		setPbeneThirdName(null);
		setPbeneFourthName(null);
		setPbeneFifthName(null);
		setCompanyName(null);
		setCompanyNameLocal(null);
		//Added by Rabil
		setAmtbCoupon(null);
		setAmtbCouponRender(false);
		setAmtbCouponAmount(null);
		setAmtbCouponFinancialYear(null);
	}

	public void exitFromPersonalRemmitance() {
		setFocus(true);
		setPaymentmodeFocus(true);
		if(getBooRenderCorporateBack()){
			//back to corporate Remittance
			try {
				setBooRenderCorporateBack(false);
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/newCorporateRemittance.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			hideAllPanels();
			setSelectCardType(0);
			setSpecialApprovalRadio(0);
			setCardType(null);
			setIdNumber(null);
			setBeneficiaryCountryId( null);
			setAmlRecheckAuthentication(false);
			checkProExp = false;
			saveCount = 0;
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// All Variables Based on Panels
	// customer details variables
	private String customerName;
	private String customerCrNumber;
	private BigDecimal customerNo;
	private BigDecimal customerrefno;
	private String firstName;
	private String secondName;
	private String thirdName;
	private String customerFullName;
	private String customerLocalFullName;
	private String customerIsActive;
	private Date customerExpDate;
	private String customerExpireDateMsg;
	private String customerType;
	private BigDecimal customerTypeId;
	private BigDecimal nationality;
	private BigDecimal nationalityName;
	private Date dateOfBrith;
	private String birthdate;
	private String countryCode;
	private String mcountryCode;
	private String occupation;
	//Added by Rabil on 11/04/2016
	private BigDecimal loyaltyPoints;

	// getters and setters for customer details variables
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCrNumber() {
		return customerCrNumber;
	}
	public void setCustomerCrNumber(String customerCrNumber) {
		this.customerCrNumber = customerCrNumber;
	}

	public BigDecimal getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(BigDecimal customerNo) {
		this.customerNo = customerNo;
	}

	public BigDecimal getCustomerrefno() {
		return customerrefno;
	}
	public void setCustomerrefno(BigDecimal customerrefno) {
		this.customerrefno = customerrefno;
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

	public String getCustomerFullName() {
		return customerFullName;
	}
	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getCustomerLocalFullName() {
		return customerLocalFullName;
	}
	public void setCustomerLocalFullName(String customerLocalFullName) {
		this.customerLocalFullName = customerLocalFullName;
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

	public BigDecimal getNationality() {
		return nationality;
	}
	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}

	public BigDecimal getNationalityName() {
		return nationalityName;
	}
	public void setNationalityName(BigDecimal nationalityName) {
		this.nationalityName = nationalityName;
	}

	public Date getDateOfBrith() {
		return dateOfBrith;
	}
	public void setDateOfBrith(Date dateOfBrith) {
		this.dateOfBrith = dateOfBrith;
	}

	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMcountryCode() {
		return mcountryCode;
	}
	public void setMcountryCode(String mcountryCode) {
		this.mcountryCode = mcountryCode;
	}

	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public BigDecimal getLoyaltyPoints() {
		return loyaltyPoints;
	}
	public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	// first panel Variables
	private BigDecimal selectCard;
	private int selectCardType;
	private boolean booRenderBenificaryFirstPanel = false;
	private boolean booRenderOldSmartCardPanel = false;
	private BigDecimal cardType;
	private String idNumber;
	private boolean visible;
	private String errmsg;
	private String warningMessage;
	private BigDecimal minLenght;
	private boolean booCheckMobile;
	private boolean booCheckMobileInput;
	private String mobileNoFetch;


	// getters and setters for First Panel Variables
	public BigDecimal getSelectCard() {
		return selectCard;
	}
	public void setSelectCard(BigDecimal selectCard) {
		this.selectCard = selectCard;
	}

	public int getSelectCardType() {
		return selectCardType;
	}
	public void setSelectCardType(int selectCardType) {
		this.selectCardType = selectCardType;
	}

	public boolean getBooRenderBenificaryFirstPanel() {
		return booRenderBenificaryFirstPanel;
	}
	public void setBooRenderBenificaryFirstPanel(boolean booRenderBenificaryFirstPanel) {
		this.booRenderBenificaryFirstPanel = booRenderBenificaryFirstPanel;
	}

	public boolean getBooRenderOldSmartCardPanel() {
		return booRenderOldSmartCardPanel;
	}
	public void setBooRenderOldSmartCardPanel(boolean booRenderOldSmartCardPanel) {
		this.booRenderOldSmartCardPanel = booRenderOldSmartCardPanel;
	}

	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public BigDecimal getCardType() {
		return cardType;
	}
	public void setCardType(BigDecimal cardType) {
		this.cardType = cardType;
	}

	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getWarningMessage() {
		return warningMessage;
	}
	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public BigDecimal getMinLenght() {
		for (AddAdditionalBankData listAdditionals : listAdditionalBankDataTable) {
			setMinLenght(new BigDecimal(listAdditionals.getMinLenght()));
			break;
		}
		return minLenght;
	}
	public void setMinLenght(BigDecimal minLenght) {
		this.minLenght = minLenght;
	}

	public boolean isBooCheckMobile() {
		return booCheckMobile;
	}
	public void setBooCheckMobile(boolean booCheckMobile) {
		this.booCheckMobile = booCheckMobile;
	}

	public boolean isBooCheckMobileInput() {
		return booCheckMobileInput;
	}
	public void setBooCheckMobileInput(boolean booCheckMobileInput) {
		this.booCheckMobileInput = booCheckMobileInput;
	}

	public String getMobileNoFetch() {
		return mobileNoFetch;
	}
	public void setMobileNoFetch(String mobileNoFetch) {
		this.mobileNoFetch = mobileNoFetch;
	}

	// second Panel Variables
	private BigDecimal beneficaryStatusId;
	private String procedureError;
	private boolean renderBackButton = false;
	private boolean booRenderBenificarySearchPanel = false;
	private BigDecimal beneficiaryAccountSeqId;
	private BigDecimal beneficiaryRelationShipSeqId;

	// getters and setters for second Panel Variables	
	public String getProcedureError() {
		return procedureError;
	}
	public void setProcedureError(String procedureError) {
		this.procedureError = procedureError;
	}

	public BigDecimal getBeneficaryStatusId() {
		return beneficaryStatusId;
	}
	public void setBeneficaryStatusId(BigDecimal beneficaryStatusId) {
		this.beneficaryStatusId = beneficaryStatusId;
	}

	public boolean getRenderBackButton() {
		return renderBackButton;
	}
	public void setRenderBackButton(boolean renderBackButton) {
		this.renderBackButton = renderBackButton;
	}

	public boolean getBooRenderBenificarySearchPanel() {
		return booRenderBenificarySearchPanel;
	}
	public void setBooRenderBenificarySearchPanel(boolean booRenderBenificarySearchPanel) {
		this.booRenderBenificarySearchPanel = booRenderBenificarySearchPanel;
	}

	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}
	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}

	public BigDecimal getBeneficiaryRelationShipSeqId() {
		return beneficiaryRelationShipSeqId;
	}
	public void setBeneficiaryRelationShipSeqId(BigDecimal beneficiaryRelationShipSeqId) {
		this.beneficiaryRelationShipSeqId = beneficiaryRelationShipSeqId;
	}

	// Beneficiary details from view of data table
	private BigDecimal databenificarycountry;
	private String databenificarycountryname;
	private BigDecimal dataBankbenificarycountry;
	private String dataBankbenificarycountryname;
	private BigDecimal databenificarycurrency;
	private String databenificarycurrencyname;
	private String databenificaryservice;
	private String databenificaryservicecode;
	private BigDecimal databenificarydelivery;
	private BigDecimal dataserviceid;
	private String dataAccountnum;
	private String databenificarybankname;
	private String databenificarybranchname;
	private String databenificaryname;
	public String databenificaryservicegroup;
	private BigDecimal dataservicegroupid;
	private BigDecimal dataservicemasterid;
	private String benificarystatus;
	//private BigDecimal accountNumberSeqId;
	private String benificiaryryNameRemittance;
	private BigDecimal masterId;
	private BigDecimal beneficaryBankId;
	private BigDecimal beneficaryBankBranchId;
	private String benificaryTelephone;

	private BigDecimal dataCustomerContactId;
	private String dataCustomerTelephoneNumber;
	private Boolean booRenderCustTelMandatory = false;
	private Boolean booRenderCustTelDisable = true;
	private String dataTempCustomerMobile;
	private Boolean booRenderBeneTelDisable = true;
	private Boolean booRenderBeneTelMandatory = false;
	private BigDecimal dataBeneContactId;
	private String dataTempBeneTelNum;

	// Icash Product
	private String databenificarycountrycode;
	private String dataroutingcountrycode;
	private String databenificarybankalphacode;
	private String dataroutingcountryalphacode;
	private String dataroutingbankalphacode;
	private String databenificarycurrencyalphacode;


	// getters and setters for second Panel Variables
	public BigDecimal getDataBankbenificarycountry() {
		return dataBankbenificarycountry;
	}
	public void setDataBankbenificarycountry(BigDecimal dataBankbenificarycountry) {
		this.dataBankbenificarycountry = dataBankbenificarycountry;
	}

	public String getDataBankbenificarycountryname() {
		return dataBankbenificarycountryname;
	}
	public void setDataBankbenificarycountryname(String dataBankbenificarycountryname) {
		this.dataBankbenificarycountryname = dataBankbenificarycountryname;
	}

	public BigDecimal getDatabenificarycountry() {
		return databenificarycountry;
	}
	public void setDatabenificarycountry(BigDecimal databenificarycountry) {
		this.databenificarycountry = databenificarycountry;
	}

	public String getDatabenificarycountryname() {
		return databenificarycountryname;
	}
	public void setDatabenificarycountryname(String databenificarycountryname) {
		this.databenificarycountryname = databenificarycountryname;
	}

	public BigDecimal getDatabenificarycurrency() {
		return databenificarycurrency;
	}
	public void setDatabenificarycurrency(BigDecimal databenificarycurrency) {
		this.databenificarycurrency = databenificarycurrency;
	}

	public String getDatabenificarycurrencyname() {
		return databenificarycurrencyname;
	}
	public void setDatabenificarycurrencyname(String databenificarycurrencyname) {
		this.databenificarycurrencyname = databenificarycurrencyname;
	}

	public String getDatabenificaryservice() {
		return databenificaryservice;
	}
	public void setDatabenificaryservice(String databenificaryservice) {
		this.databenificaryservice = databenificaryservice;
	}

	public String getDatabenificaryservicecode() {
		return databenificaryservicecode;
	}
	public void setDatabenificaryservicecode(String databenificaryservicecode) {
		this.databenificaryservicecode = databenificaryservicecode;
	}

	public BigDecimal getDatabenificarydelivery() {
		return databenificarydelivery;
	}
	public void setDatabenificarydelivery(BigDecimal databenificarydelivery) {
		this.databenificarydelivery = databenificarydelivery;
	}

	public BigDecimal getDataserviceid() {
		return dataserviceid;
	}
	public void setDataserviceid(BigDecimal dataserviceid) {
		this.dataserviceid = dataserviceid;
	}

	public String getDataAccountnum() {
		return dataAccountnum;
	}
	public void setDataAccountnum(String dataAccountnum) {
		this.dataAccountnum = dataAccountnum;
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

	public String getDatabenificaryname() {
		return databenificaryname;
	}
	public void setDatabenificaryname(String databenificaryname) {
		this.databenificaryname = databenificaryname;
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

	public BigDecimal getDataservicemasterid() {
		return dataservicemasterid;
	}
	public void setDataservicemasterid(BigDecimal dataservicemasterid) {
		this.dataservicemasterid = dataservicemasterid;
	}

	public String getBenificarystatus() {
		return benificarystatus;
	}
	public void setBenificarystatus(String benificarystatus) {
		this.benificarystatus = benificarystatus;
	}

	public String getBenificiaryryNameRemittance() {
		return benificiaryryNameRemittance;
	}
	public void setBenificiaryryNameRemittance(String benificiaryryNameRemittance) {
		this.benificiaryryNameRemittance = benificiaryryNameRemittance;
	}

	public BigDecimal getMasterId() {
		return masterId;
	}
	public void setMasterId(BigDecimal masterId) {
		this.masterId = masterId;
	}

	public BigDecimal getBeneficaryBankId() {
		return beneficaryBankId;
	}
	public void setBeneficaryBankId(BigDecimal beneficaryBankId) {
		this.beneficaryBankId = beneficaryBankId;
	}

	public BigDecimal getBeneficaryBankBranchId() {
		return beneficaryBankBranchId;
	}
	public void setBeneficaryBankBranchId(BigDecimal beneficaryBankBranchId) {
		this.beneficaryBankBranchId = beneficaryBankBranchId;
	}	

	public String getBenificaryTelephone() {
		return benificaryTelephone;
	}
	public void setBenificaryTelephone(String benificaryTelephone) {
		this.benificaryTelephone = benificaryTelephone;
	}

	public BigDecimal getDataCustomerContactId() {
		return dataCustomerContactId;
	}
	public void setDataCustomerContactId(BigDecimal dataCustomerContactId) {
		this.dataCustomerContactId = dataCustomerContactId;
	}

	public String getDataCustomerTelephoneNumber() {
		return dataCustomerTelephoneNumber;
	}
	public void setDataCustomerTelephoneNumber(String dataCustomerTelephoneNumber) {
		this.dataCustomerTelephoneNumber = dataCustomerTelephoneNumber;
	}

	public Boolean getBooRenderCustTelMandatory() {
		return booRenderCustTelMandatory;
	}
	public void setBooRenderCustTelMandatory(Boolean booRenderCustTelMandatory) {
		this.booRenderCustTelMandatory = booRenderCustTelMandatory;
	}

	public Boolean getBooRenderCustTelDisable() {
		return booRenderCustTelDisable;
	}
	public void setBooRenderCustTelDisable(Boolean booRenderCustTelDisable) {
		this.booRenderCustTelDisable = booRenderCustTelDisable;
	}

	public String getDataTempCustomerMobile() {
		return dataTempCustomerMobile;
	}
	public void setDataTempCustomerMobile(String dataTempCustomerMobile) {
		this.dataTempCustomerMobile = dataTempCustomerMobile;
	}

	public Boolean getBooRenderBeneTelDisable() {
		return booRenderBeneTelDisable;
	}
	public void setBooRenderBeneTelDisable(Boolean booRenderBeneTelDisable) {
		this.booRenderBeneTelDisable = booRenderBeneTelDisable;
	}

	public Boolean getBooRenderBeneTelMandatory() {
		return booRenderBeneTelMandatory;
	}
	public void setBooRenderBeneTelMandatory(Boolean booRenderBeneTelMandatory) {
		this.booRenderBeneTelMandatory = booRenderBeneTelMandatory;
	}

	public BigDecimal getDataBeneContactId() {
		return dataBeneContactId;
	}
	public void setDataBeneContactId(BigDecimal dataBeneContactId) {
		this.dataBeneContactId = dataBeneContactId;
	}

	public String getDataTempBeneTelNum() {
		return dataTempBeneTelNum;
	}
	public void setDataTempBeneTelNum(String dataTempBeneTelNum) {
		this.dataTempBeneTelNum = dataTempBeneTelNum;
	}

	/*public String getDatabenificarycountryalphacode() {
		return databenificarycountryalphacode;
	}
	public void setDatabenificarycountryalphacode(String databenificarycountryalphacode) {
		this.databenificarycountryalphacode = databenificarycountryalphacode;
	}*/

	public String getDatabenificarycountrycode() {
		return databenificarycountrycode;
	}
	public void setDatabenificarycountrycode(String databenificarycountrycode) {
		this.databenificarycountrycode = databenificarycountrycode;
	}

	public String getDataroutingcountrycode() {
		return dataroutingcountrycode;
	}
	public void setDataroutingcountrycode(String dataroutingcountrycode) {
		this.dataroutingcountrycode = dataroutingcountrycode;
	}

	public String getDatabenificarybankalphacode() {
		return databenificarybankalphacode;
	}
	public void setDatabenificarybankalphacode(String databenificarybankalphacode) {
		this.databenificarybankalphacode = databenificarybankalphacode;
	}

	public String getDataroutingcountryalphacode() {
		return dataroutingcountryalphacode;
	}
	public void setDataroutingcountryalphacode(String dataroutingcountryalphacode) {
		this.dataroutingcountryalphacode = dataroutingcountryalphacode;
	}

	public String getDataroutingbankalphacode() {
		return dataroutingbankalphacode;
	}
	public void setDataroutingbankalphacode(String dataroutingbankalphacode) {
		this.dataroutingbankalphacode = dataroutingbankalphacode;
	}

	public String getDatabenificarycurrencyalphacode() {
		return databenificarycurrencyalphacode;
	}
	public void setDatabenificarycurrencyalphacode(String databenificarycurrencyalphacode) {
		this.databenificarycurrencyalphacode = databenificarycurrencyalphacode;
	}

	// third panel Variables
	private BigDecimal serviceCode;
	private BigDecimal routingCountry;
	private String routingCountryName;
	private BigDecimal routingBank;
	private String routingBankName;
	private BigDecimal routingBranch;
	private String routingBranchName;
	private BigDecimal remitMode;
	private String remittanceName;
	private BigDecimal deliveryMode;
	private String deliveryModeInput;
	private BigDecimal foriegnCurrency;
	private BigDecimal currency;
	private String specialRateRef;
	private String availLoyaltyPoints;
	private String chargesOverseas;
	private String spotRate = Constants.No;
	private boolean nextRender = false;
	private BigDecimal remitAmountSplCust = BigDecimal.ZERO;
	private BigDecimal countSplCust = BigDecimal.ZERO;
	private BigDecimal specialDealRate;
	private BigDecimal amountToRemit;
	private BigDecimal spotExchangeRate;
	private BigDecimal spotExchangeRatePk;
	private boolean booRenderPoll = false;
	private boolean checkingSplPool;
	// components for third panel
	private boolean booSingleRoutingCountry = true;
	private boolean booMultipleRoutingCountry = false;
	private boolean booReadOnlyRemitAmount = false;

	//Added by Rabil 08/12/2015
	private boolean booSingleService=true;
	private boolean booMultipleService=false;

	private boolean booRenderRouting = false;
	private boolean booRenderAgent = false;
	private boolean booSingleRoutingBank = true;
	private boolean booMultipleRoutingBank = false;
	private boolean booSingleRoutingBranch = true;
	private boolean booMultipleRoutingBranch = false;
	private boolean booRenderDeliveryModeInputPanel = true;
	private boolean booRenderDeliveryModeDDPanel = false;
	private boolean booSingleRemit = true;
	private boolean booMultipleRemit = false;
	private boolean disableSpotRatePanel = false;
	private boolean marqueeRender = false;
	private boolean booSpecialCusFCCalDataTable = false;
	private boolean booRenderRemittanceServicePanel = false;
	private String serviceGroupCode=null;

	// ICASH Product added on 27/04/16
	private boolean icashStateSubAgent = false;
	private boolean icashAgentPanel = false;
	private boolean icashEFT = false;
	private boolean icashTT = false;
	private boolean icashCash = false;
	private String icashAgent;
	private String icashState;
	private String icashSubAgent;
	private BigDecimal icashCostRate;

	// getters and setters for third panel Variables
	public BigDecimal getRemitAmountSplCust() {
		return remitAmountSplCust;
	}
	public void setRemitAmountSplCust(BigDecimal remitAmountSplCust) {
		this.remitAmountSplCust = remitAmountSplCust;
	}

	public BigDecimal getRoutingBranch() {
		return routingBranch;
	}
	public void setRoutingBranch(BigDecimal routingBranch) {
		this.routingBranch = routingBranch;
	}

	public String getRoutingBranchName() {
		return routingBranchName;
	}
	public void setRoutingBranchName(String routingBranchName) {
		this.routingBranchName = routingBranchName;
	}

	public String getDeliveryModeInput() {
		return deliveryModeInput;
	}
	public void setDeliveryModeInput(String deliveryModeInput) {
		this.deliveryModeInput = deliveryModeInput;
	}

	public String getRemittanceName() {
		return remittanceName;
	}
	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}

	public String getRoutingBankName() {
		return routingBankName;
	}
	public void setRoutingBankName(String routingBankName) {
		this.routingBankName = routingBankName;
	}

	public String getRoutingCountryName() {
		return routingCountryName;
	}
	public void setRoutingCountryName(String routingCountryName) {
		this.routingCountryName = routingCountryName;
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

	public BigDecimal getForiegnCurrency() {
		return foriegnCurrency;
	}
	public void setForiegnCurrency(BigDecimal foriegnCurrency) {
		this.foriegnCurrency = foriegnCurrency;
	}

	public BigDecimal getCurrency() {
		return currency;
	}
	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

	public String getSpecialRateRef() {
		return specialRateRef;
	}
	public void setSpecialRateRef(String specialRateRef) {
		this.specialRateRef = specialRateRef;
	}

	public String getAvailLoyaltyPoints() {
		return availLoyaltyPoints;
	}
	public void setAvailLoyaltyPoints(String availLoyaltyPoints) {
		this.availLoyaltyPoints = availLoyaltyPoints;
	}

	public String getChargesOverseas() {
		return chargesOverseas;
	}
	public void setChargesOverseas(String chargesOverseas) {
		this.chargesOverseas = chargesOverseas;
	}

	public String getSpotRate() {
		return spotRate;
	}
	public void setSpotRate(String spotRate) {
		this.spotRate = spotRate;
	}

	public boolean getNextRender() {
		return nextRender;
	}
	public void setNextRender(boolean nextRender) {
		this.nextRender = nextRender;
	}

	public BigDecimal getSpecialDealRate() {
		return specialDealRate;
	}
	public void setSpecialDealRate(BigDecimal specialDealRate) {
		this.specialDealRate = specialDealRate;
	}

	public BigDecimal getCountSplCust() {
		return countSplCust;
	}
	public void setCountSplCust(BigDecimal countSplCust) {
		this.countSplCust = countSplCust;
	}

	public BigDecimal getAmountToRemit() {
		return amountToRemit;
	}
	public void setAmountToRemit(BigDecimal amountToRemit) {
		this.amountToRemit = amountToRemit;
	}	

	public BigDecimal getSpotExchangeRate() {
		return spotExchangeRate;
	}
	public void setSpotExchangeRate(BigDecimal spotExchangeRate) {
		this.spotExchangeRate = spotExchangeRate;
	}

	public BigDecimal getSpotExchangeRatePk() {
		return spotExchangeRatePk;
	}
	public void setSpotExchangeRatePk(BigDecimal spotExchangeRatePk) {
		this.spotExchangeRatePk = spotExchangeRatePk;
	}

	public boolean isBooRenderPoll() {
		return booRenderPoll;
	}
	public void setBooRenderPoll(boolean booRenderPoll) {
		this.booRenderPoll = booRenderPoll;
	}

	public boolean getCheckingSplPool() {
		return checkingSplPool;
	}
	public void setCheckingSplPool(boolean checkingSplPool) {
		this.checkingSplPool = checkingSplPool;
	}


	// components for third panel
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

	public boolean getBooRenderRouting() {
		return booRenderRouting;
	}
	public void setBooRenderRouting(boolean booRenderRouting) {
		this.booRenderRouting = booRenderRouting;
	}

	public boolean getBooRenderAgent() {
		return booRenderAgent;
	}
	public void setBooRenderAgent(boolean booRenderAgent) {
		this.booRenderAgent = booRenderAgent;
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

	public boolean getBooRenderDeliveryModeInputPanel() {
		return booRenderDeliveryModeInputPanel;
	}
	public void setBooRenderDeliveryModeInputPanel(boolean booRenderDeliveryModeInputPanel) {
		this.booRenderDeliveryModeInputPanel = booRenderDeliveryModeInputPanel;
	}

	public boolean getBooRenderDeliveryModeDDPanel() {
		return booRenderDeliveryModeDDPanel;
	}
	public void setBooRenderDeliveryModeDDPanel(boolean booRenderDeliveryModeDDPanel) {
		this.booRenderDeliveryModeDDPanel = booRenderDeliveryModeDDPanel;
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

	public boolean isDisableSpotRatePanel() {
		return disableSpotRatePanel;
	}
	public void setDisableSpotRatePanel(boolean disableSpotRatePanel) {
		this.disableSpotRatePanel = disableSpotRatePanel;
	}

	public boolean isMarqueeRender() {
		return marqueeRender;
	}
	public void setMarqueeRender(boolean marqueeRender) {
		this.marqueeRender = marqueeRender;
	}

	public boolean isBooSpecialCusFCCalDataTable() {
		return booSpecialCusFCCalDataTable;
	}
	public void setBooSpecialCusFCCalDataTable(boolean booSpecialCusFCCalDataTable) {
		this.booSpecialCusFCCalDataTable = booSpecialCusFCCalDataTable;
	}

	public boolean getBooRenderRemittanceServicePanel() {
		return booRenderRemittanceServicePanel;
	}
	public void setBooRenderRemittanceServicePanel(boolean booRenderRemittanceServicePanel) {
		this.booRenderRemittanceServicePanel = booRenderRemittanceServicePanel;
	}	

	public boolean isBooReadOnlyRemitAmount() {
		return booReadOnlyRemitAmount;
	}
	public void setBooReadOnlyRemitAmount(boolean booReadOnlyRemitAmount) {
		this.booReadOnlyRemitAmount = booReadOnlyRemitAmount;
	}

	public boolean isIcashStateSubAgent() {
		return icashStateSubAgent;
	}
	public void setIcashStateSubAgent(boolean icashStateSubAgent) {
		this.icashStateSubAgent = icashStateSubAgent;
	}

	public boolean isIcashAgentPanel() {
		return icashAgentPanel;
	}
	public void setIcashAgentPanel(boolean icashAgentPanel) {
		this.icashAgentPanel = icashAgentPanel;
	}

	public boolean isIcashEFT() {
		return icashEFT;
	}
	public void setIcashEFT(boolean icashEFT) {
		this.icashEFT = icashEFT;
	}

	public boolean isIcashTT() {
		return icashTT;
	}
	public void setIcashTT(boolean icashTT) {
		this.icashTT = icashTT;
	}

	public boolean isIcashCash() {
		return icashCash;
	}
	public void setIcashCash(boolean icashCash) {
		this.icashCash = icashCash;
	}

	public String getIcashAgent() {
		return icashAgent;
	}
	public void setIcashAgent(String icashAgent) {
		this.icashAgent = icashAgent;
	}

	public String getIcashState() {
		return icashState;
	}
	public void setIcashState(String icashState) {
		this.icashState = icashState;
	}

	public String getIcashSubAgent() {
		return icashSubAgent;
	}
	public void setIcashSubAgent(String icashSubAgent) {
		this.icashSubAgent = icashSubAgent;
	}

	public BigDecimal getIcashCostRate() {
		return icashCostRate;
	}
	public void setIcashCostRate(BigDecimal icashCostRate) {
		this.icashCostRate = icashCostRate;
	}

	// fourth Panel Variables
	private boolean additionalCheck = true;
	private BigDecimal sourceOfIncome;
	private String exceptionMessage;
	private String exceptionMessageForReport;
	private BigDecimal exchangeRate;
	private BigDecimal Overseasamt;
	private BigDecimal commission;
	private BigDecimal grossAmountCalculated;
	private BigDecimal loyaltyAmountAvailed;
	private BigDecimal netAmountPayable;
	private BigDecimal netAmountSent;
	private BigDecimal newRemittanceModeId;
	private String newRemittanceModeName;
	private boolean booRenderRemit;
	private BigDecimal newDeliveryModeId;
	private String newDeliveryModeName;
	private boolean booRenderDelivery;
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
	private String furthuerInstructions;
	//spl deal records
	private BigDecimal spldealreqid;
	private BigDecimal splcompanyid;
	private BigDecimal spldocumentid;
	private BigDecimal splfinyear;
	private BigDecimal spldocnum;
	// rendering variables
	private boolean booRenderOverseaCharges = false;
	private boolean booRenderLayaltyServicePanel = false;
	private boolean booRenderPlaceOrder = false;
	private boolean booRenderPlaceOrderByRemit = false;
	private BigDecimal ratePlaceOrderPk;


	public String getExceptionMessageForReport() {
		return exceptionMessageForReport;
	}
	public void setExceptionMessageForReport(String exceptionMessageForReport) {
		this.exceptionMessageForReport = exceptionMessageForReport;
	}
	// getters and setters for fourth Panel Variables
	public boolean getAdditionalCheck() {
		return additionalCheck;
	}
	public void setAdditionalCheck(boolean additionalCheck) {
		this.additionalCheck = additionalCheck;
	}

	public BigDecimal getSourceOfIncome() {
		return sourceOfIncome;
	}
	public void setSourceOfIncome(BigDecimal sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public BigDecimal getSpldealreqid() {
		return spldealreqid;
	}
	public void setSpldealreqid(BigDecimal spldealreqid) {
		this.spldealreqid = spldealreqid;
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

	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getOverseasamt() {
		return Overseasamt;
	}
	public void setOverseasamt(BigDecimal overseasamt) {
		Overseasamt = overseasamt;
	}

	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getNetAmountPayable() {
		return netAmountPayable;
	}
	public void setNetAmountPayable(BigDecimal netAmountPayable) {
		this.netAmountPayable = netAmountPayable;
	}

	public BigDecimal getNetAmountSent() {
		return netAmountSent;
	}
	public void setNetAmountSent(BigDecimal netAmountSent) {
		this.netAmountSent = netAmountSent;
	}

	public BigDecimal getGrossAmountCalculated() {
		return grossAmountCalculated;
	}
	public void setGrossAmountCalculated(BigDecimal grossAmountCalculated) {
		this.grossAmountCalculated = grossAmountCalculated;
	}

	public BigDecimal getLoyaltyAmountAvailed() {
		return loyaltyAmountAvailed;
	}
	public void setLoyaltyAmountAvailed(BigDecimal loyaltyAmountAvailed) {
		this.loyaltyAmountAvailed = loyaltyAmountAvailed;
	}

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

	public boolean isBooRenderRemit() {
		return booRenderRemit;
	}
	public void setBooRenderRemit(boolean booRenderRemit) {
		this.booRenderRemit = booRenderRemit;
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

	public String getFurthuerInstructions() {
		return furthuerInstructions;
	}
	public void setFurthuerInstructions(String furthuerInstructions) {
		this.furthuerInstructions = furthuerInstructions;
	}

	public boolean getBooRenderOverseaCharges() {
		return booRenderOverseaCharges;
	}
	public void setBooRenderOverseaCharges(boolean booRenderOverseaCharges) {
		this.booRenderOverseaCharges = booRenderOverseaCharges;
	}

	public boolean getBooRenderLayaltyServicePanel() {
		return booRenderLayaltyServicePanel;
	}
	public void setBooRenderLayaltyServicePanel(boolean booRenderLayaltyServicePanel) {
		this.booRenderLayaltyServicePanel = booRenderLayaltyServicePanel;
	}
	
	public boolean isBooRenderPlaceOrder() {
		return booRenderPlaceOrder;
	}
	public void setBooRenderPlaceOrder(boolean booRenderPlaceOrder) {
		this.booRenderPlaceOrder = booRenderPlaceOrder;
	}

	public boolean isBooRenderPlaceOrderByRemit() {
		return booRenderPlaceOrderByRemit;
	}
	public void setBooRenderPlaceOrderByRemit(boolean booRenderPlaceOrderByRemit) {
		this.booRenderPlaceOrderByRemit = booRenderPlaceOrderByRemit;
	}
	
	public BigDecimal getRatePlaceOrderPk() {
		return ratePlaceOrderPk;
	}
	public void setRatePlaceOrderPk(BigDecimal ratePlaceOrderPk) {
		this.ratePlaceOrderPk = ratePlaceOrderPk;
	}

	// Fifth Panel Variables
	private boolean isHaveAmlCheck;
	private boolean fromAMLCheck;
	private BigDecimal beneCountr;
	private String messageOne;
	private String messageTwo;
	private String messageThree;
	private boolean booMessageOne;
	private boolean booMessageTwo;
	private boolean booMessageThree;
	private String amlMessageOne;
	private String amlMessageTwo;
	private String amlMessageThree;
	private String amlAuthorizedBy;
	private String amlAuthorizedPassword;
	private String amlRemarks;
	private String rangeFromOne;
	private String rangeToOne;
	private String rangeFromTwo;
	private String rangeToTwo;
	private String rangeFromThree;
	private String rangeToThree;
	private String rangeFromFour;
	private String rangeToFour;
	private String rangeFromFive;
	private String rangeToFive;
	private String rangeFromSix;
	private String rangeToSix;
	private boolean renderRangeFive;
	private boolean renderRangeSix;
	private double slb1total1 = 0.0;
	private double slb1total2 = 0.0;
	private double slb1total3 = 0.0;
	private double slb1total4 = 0.0;
	private double slb1total5 = 0.0;
	private double slb1total6 = 0.0;
	private boolean amlboomsg = false;
	private boolean amlboo = true;

	private String amlAuntenticationMessageOne;
	private String amlAuntenticationAuthorizedBy;
	private String amlAuntenticationAuthorizedPassword;
	private String amlAuntenticationRemarks;
	private String amlAuntenticationType;
	private boolean amlRecheckAuthentication = false;

	// getters and setters for Fifth Panel Variables
	public boolean getIsHaveAmlCheck() {
		return isHaveAmlCheck;
	}
	public void setIsHaveAmlCheck(boolean isHaveAmlCheck) {
		this.isHaveAmlCheck = isHaveAmlCheck;
	}

	public boolean getFromAMLCheck() {
		return fromAMLCheck;
	}
	public void setFromAMLCheck(boolean fromAMLCheck) {
		this.fromAMLCheck = fromAMLCheck;
	}

	public BigDecimal getBeneCountr() {
		return beneCountr;
	}
	public void setBeneCountr(BigDecimal beneCountr) {
		this.beneCountr = beneCountr;
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

	public boolean getBooMessageOne() {
		return booMessageOne;
	}
	public void setBooMessageOne(boolean booMessageOne) {
		this.booMessageOne = booMessageOne;
	}

	public boolean getBooMessageTwo() {
		return booMessageTwo;
	}
	public void setBooMessageTwo(boolean booMessageTwo) {
		this.booMessageTwo = booMessageTwo;
	}

	public boolean getBooMessageThree() {
		return booMessageThree;
	}
	public void setBooMessageThree(boolean booMessageThree) {
		this.booMessageThree = booMessageThree;
	}

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
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}	

	public Boolean getFocus() {
		return focus;
	}
	public void setFocus(Boolean focus) {
		this.focus = focus;
	}
	
	public void hideFocus(){
		setFocus(false);
		checkMobile();
	}

	public Boolean getPaymentmodeFocus() {
		return paymentmodeFocus;
	}
	public void setPaymentmodeFocus(Boolean paymentmodeFocus) {
		this.paymentmodeFocus = paymentmodeFocus;
	}	

	public Boolean getAppNoFocus() {
		return appNoFocus;
	}
	public void setAppNoFocus(Boolean appNoFocus) {
		this.appNoFocus = appNoFocus;
	}

	public String getAmlRemarks() {
		return amlRemarks;
	}
	public void setAmlRemarks(String amlRemarks) {
		this.amlRemarks = amlRemarks;
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

	public boolean getRenderRangeFive() {
		return renderRangeFive;
	}
	public void setRenderRangeFive(boolean renderRangeFive) {
		this.renderRangeFive = renderRangeFive;
	}

	public boolean getRenderRangeSix() {
		return renderRangeSix;
	}
	public void setRenderRangeSix(boolean renderRangeSix) {
		this.renderRangeSix = renderRangeSix;
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

	public boolean isAmlboomsg() {
		return amlboomsg;
	}
	public void setAmlboomsg(boolean amlboomsg) {
		this.amlboomsg = amlboomsg;
	}

	public boolean isAmlboo() {
		return amlboo;
	}
	public void setAmlboo(boolean amlboo) {
		this.amlboo = amlboo;
	}

	public String getAmlAuthorizedPassword() {
		return amlAuthorizedPassword;
	}
	public void setAmlAuthorizedPassword(String amlAuthorizedPassword) {
		this.amlAuthorizedPassword = amlAuthorizedPassword;
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

	// Sixth Panel Variables
	private boolean booRenderCustomerSignaturePanel = false;
	private String digitalSignature;
	private String signatureSpecimen;
	private BigDecimal documentId;
	private BigDecimal documentcode;
	private String documentNo;
	private String documentdesc;
	private BigDecimal finaceYear;
	private BigDecimal finaceYearId;

	// getters and setters for Sixth Panel Variables
	public String getDigitalSignature() {
		return digitalSignature;
	}
	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

	public String getSignatureSpecimen() {
		return signatureSpecimen;
	}
	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	} 

	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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
	public void setFinaceYear(BigDecimal finaceYear) {
		this.finaceYear = finaceYear;
	}

	public BigDecimal getFinaceYearId() {
		try {
			List<UserFinancialYear> financialYearList = foreignCurrencyPurchaseService.getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null)
				finaceYearId = financialYearList.get(0).getFinancialYearID();
			setFinaceYearId(finaceYearId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finaceYearId;
	}
	public void setFinaceYearId(BigDecimal finaceYearId) {
		this.finaceYearId = finaceYearId;
	}

	public boolean getBooRenderCustomerSignaturePanel() {
		return booRenderCustomerSignaturePanel;
	}
	public void setBooRenderCustomerSignaturePanel(boolean booRenderCustomerSignaturePanel) {
		this.booRenderCustomerSignaturePanel = booRenderCustomerSignaturePanel;
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

	public String getDocumentdesc() {
		return documentdesc;
	}
	public void setDocumentdesc(String documentdesc) {
		this.documentdesc = documentdesc;
	}

	// Seventh Panel Variables
	private boolean boorenderlastpanel;
	private String signatureMandatoryMsg;
	private boolean booRenderSignatureMsg = false;
	private boolean booShowAuthenticationPanel = false;

	// getters and setters for Seventh Panel Variables
	public boolean isBoorenderlastpanel() {
		return boorenderlastpanel;
	}
	public void setBoorenderlastpanel(boolean boorenderlastpanel) {
		this.boorenderlastpanel = boorenderlastpanel;
	}

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

	public boolean isBooShowAuthenticationPanel() {
		return booShowAuthenticationPanel;
	}
	public void setBooShowAuthenticationPanel(boolean booShowAuthenticationPanel) {
		this.booShowAuthenticationPanel = booShowAuthenticationPanel;
	}

	// Eight Panel Variables
	private BigDecimal applicationDocNum;
	private BigDecimal shoppingcartExchangeRate;
	private boolean booRenderMultiDocNum = false;
	private boolean booRenderSingleDocNum = true;
	private BigDecimal dummiTotalNetAmount;
	private BigDecimal dummiTotalGrossAmount;
	private boolean booShowCashRoundingPanel = false;
	private boolean booRenderModifiedRoundData = false;
	private BigDecimal calGrossAmount;
	private BigDecimal calNetAmountPaid;
	private String cashRounding;
	private Boolean booRenderSingleDebit = true;
	private Boolean booRenderMulDebit = false;

	/** Added  by Rabil */
	private boolean booColApprovalNo=false;
	private String knetIposReceipt;
	private String knetReceiptDate;
	private String knetReceiptTime;


	// getters and setters for Eight Panel Variables
	public BigDecimal getCalGrossAmount() {
		return calGrossAmount;
	}
	public void setCalGrossAmount(BigDecimal calGrossAmount) {
		this.calGrossAmount = calGrossAmount;
	}

	public BigDecimal getCalNetAmountPaid() {
		return calNetAmountPaid;
	}
	public void setCalNetAmountPaid(BigDecimal calNetAmountPaid) {
		this.calNetAmountPaid = calNetAmountPaid;
	}

	public BigDecimal getApplicationDocNum() {
		return applicationDocNum;
	}
	public void setApplicationDocNum(BigDecimal applicationDocNum) {
		this.applicationDocNum = applicationDocNum;
	}

	public BigDecimal getShoppingcartExchangeRate() {
		return shoppingcartExchangeRate;
	}
	public void setShoppingcartExchangeRate(BigDecimal shoppingcartExchangeRate) {
		this.shoppingcartExchangeRate = shoppingcartExchangeRate;
	}

	public boolean getBooRenderMultiDocNum() {
		return booRenderMultiDocNum;
	}
	public void setBooRenderMultiDocNum(boolean booRenderMultiDocNum) {
		this.booRenderMultiDocNum = booRenderMultiDocNum;
	}

	public boolean getBooRenderSingleDocNum() {
		return booRenderSingleDocNum;
	}
	public void setBooRenderSingleDocNum(boolean booRenderSingleDocNum) {
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

	public boolean getBooShowCashRoundingPanel() {
		return booShowCashRoundingPanel;
	}
	public void setBooShowCashRoundingPanel(boolean booShowCashRoundingPanel) {
		this.booShowCashRoundingPanel = booShowCashRoundingPanel;
	}

	public boolean getBooRenderModifiedRoundData() {
		return booRenderModifiedRoundData;
	}
	public void setBooRenderModifiedRoundData(boolean booRenderModifiedRoundData) {
		this.booRenderModifiedRoundData = booRenderModifiedRoundData;
	}

	public String getCashRounding() {
		return cashRounding;
	}
	public void setCashRounding(String cashRounding) {
		this.cashRounding = cashRounding;
	}


	public Boolean getBooRenderSingleDebit() {
		return booRenderSingleDebit;
	}
	public void setBooRenderSingleDebit(Boolean booRenderSingleDebit) {
		this.booRenderSingleDebit = booRenderSingleDebit;
	}


	public Boolean getBooRenderMulDebit() {
		return booRenderMulDebit;
	}
	public void setBooRenderMulDebit(Boolean booRenderMulDebit) {
		this.booRenderMulDebit = booRenderMulDebit;
	}

	// Eight panel Bank details
	private BigDecimal colCardNo;
	private BigDecimal colremittanceNo;
	private BigDecimal colfcsaleNo;
	private BigDecimal colpaymentmodeId;
	private String colpaymentmodeName;
	private String colpaymentmodeCode;
	private BigDecimal colCash;
	private BigDecimal errcolCashExistsLimit;
	private String colCurrency;
	private String colAuthorizedby;
	private String colpassword;
	private String cyberPassword;
	private String colApprovalNo;
	private String colNameofCard;
	private BigDecimal colCardNoLength;
	private String colBankCode;
	private String colBankTransferBankCode;
	private BigDecimal colamountKWD = new BigDecimal(0);
	private BigDecimal cashAmount = new BigDecimal(0);
	private boolean booRenderColDebitCard = false;
	private boolean booRenderColBankTransfer = false;


	// getters and setters for Eight Panel Bank details Variables
	public BigDecimal getColCardNo() {
		return colCardNo;
	}
	public void setColCardNo(BigDecimal colCardNo) {
		this.colCardNo = colCardNo;
	}

	public String getColCurrency() {
		return colCurrency;
	}
	public void setColCurrency(String colCurrency) {
		this.colCurrency = colCurrency;
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

	public String getColpaymentmodeName() {
		return colpaymentmodeName;
	}
	public void setColpaymentmodeName(String colpaymentmodeName) {
		this.colpaymentmodeName = colpaymentmodeName;
	}

	public String getColpaymentmodeCode() {
		return colpaymentmodeCode;
	}
	public void setColpaymentmodeCode(String colpaymentmodeCode) {
		this.colpaymentmodeCode = colpaymentmodeCode;
	}

	public BigDecimal getColCash() {
		return colCash;
	}
	public void setColCash(BigDecimal colCash) {
		this.colCash = colCash;
	}

	public BigDecimal getErrcolCashExistsLimit() {
		return errcolCashExistsLimit;
	}
	public void setErrcolCashExistsLimit(BigDecimal errcolCashExistsLimit) {
		this.errcolCashExistsLimit = errcolCashExistsLimit;
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

	public String getColApprovalNo() {
		return colApprovalNo;
	}
	public void setColApprovalNo(String colApprovalNo) {
		this.colApprovalNo = colApprovalNo;
	}

	public String getColNameofCard() {
		return colNameofCard;
	}
	public void setColNameofCard(String colNameofCard) {
		this.colNameofCard = colNameofCard;
	}

	public BigDecimal getColCardNoLength() {
		return colCardNoLength;
	}
	public void setColCardNoLength(BigDecimal colCardNoLength) {
		this.colCardNoLength = colCardNoLength;
	}

	public String getColBankCode() {
		return colBankCode;
	}
	public void setColBankCode(String colBankCode) {
		this.colBankCode = colBankCode;
	}

	public String getColBankTransferBankCode() {
		return colBankTransferBankCode;
	}
	public void setColBankTransferBankCode(String colBankTransferBankCode) {
		this.colBankTransferBankCode = colBankTransferBankCode;
	}

	public boolean getBooRenderColDebitCard() {
		return booRenderColDebitCard;
	}
	public void setBooRenderColDebitCard(boolean booRenderColDebitCard) {
		this.booRenderColDebitCard = booRenderColDebitCard;
	}

	public boolean isBooRenderColBankTransfer() {
		return booRenderColBankTransfer;
	}
	public void setBooRenderColBankTransfer(boolean booRenderColBankTransfer) {
		this.booRenderColBankTransfer = booRenderColBankTransfer;
	}

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


	// Extra Variables without Getters and Setters
	private BigDecimal remittanceNo = new BigDecimal(0);
	private BigDecimal fcsaleNo = new BigDecimal(0);
	private BigDecimal tempCalGrossAmount = new BigDecimal(0);
	private BigDecimal tempCalNetAmountPaid = new BigDecimal(0);
	private BigDecimal totalUamount = new BigDecimal(0);
	private BigDecimal subtractedAmount = new BigDecimal(0);


	// Ninth Panel Variables
	private BigDecimal toalUsedAmount = new BigDecimal(0);
	private BigDecimal totalbalanceAmount = new BigDecimal(0);
	private BigDecimal totalrefundAmount = new BigDecimal(0);
	private boolean boorefundPaymentDetails = false;
	private boolean booRenderPaymentDetails = false;
	private boolean booRendercashdenomination = false;
	private boolean booRenderCollection = false;
	private boolean paymentDeatailsPanel = false;
	private BigDecimal tempCash;
	private String paymentDetailsremark;
	private BigDecimal denomtotalCashreceived;
	private BigDecimal knetValue;
	private BigDecimal chequeValue;
	private BigDecimal denomtotalCash;
	private BigDecimal collectedAmount;
	private BigDecimal payPaidAmount;
	private BigDecimal payNetPaidAmount;
	private BigDecimal payRefund;
	private String nextOrSaveButton;
	private BigDecimal populatedDebitCardNumber;
	private boolean booAuthozed = false;
	private String excheckCashLimitMessage = null;
	private boolean booRendercollectiondatatable = false;
	private boolean boRenderTotalAmountCalPanel = false;
	private BigDecimal refundAmount;
	private BigDecimal collectedRefundAmount;
	private boolean receiptReportPanel = false;
	private boolean mainPanelRender = false;
	private boolean booRenderModifiedApplTrnxReportByRound = false;
	private String denominationchecking;

	// getters and setters for Ninth Panel Variables
	public boolean getBoorefundPaymentDetails() {
		return boorefundPaymentDetails;
	}
	public void setBoorefundPaymentDetails(boolean boorefundPaymentDetails) {
		this.boorefundPaymentDetails = boorefundPaymentDetails;
	}

	public boolean getBooRenderPaymentDetails() {
		return booRenderPaymentDetails;
	}
	public void setBooRenderPaymentDetails(boolean booRenderPaymentDetails) {
		this.booRenderPaymentDetails = booRenderPaymentDetails;
	}

	public boolean getBooRendercashdenomination() {
		return booRendercashdenomination;
	}
	public void setBooRendercashdenomination(boolean booRendercashdenomination) {
		this.booRendercashdenomination = booRendercashdenomination;
	}

	public boolean getBooRenderCollection() {
		return booRenderCollection;
	}
	public void setBooRenderCollection(boolean booRenderCollection) {
		this.booRenderCollection = booRenderCollection;
	}

	public BigDecimal getToalUsedAmount() {
		return toalUsedAmount;
	}
	public void setToalUsedAmount(BigDecimal toalUsedAmount) {
		this.toalUsedAmount = toalUsedAmount;
	}

	public boolean getPaymentDeatailsPanel() {
		return paymentDeatailsPanel;
	}
	public void setPaymentDeatailsPanel(boolean paymentDeatailsPanel) {
		this.paymentDeatailsPanel = paymentDeatailsPanel;
	}

	public BigDecimal getTempCash() {
		return tempCash;
	}
	public void setTempCash(BigDecimal tempCash) {
		this.tempCash = tempCash;
	}

	public String getPaymentDetailsremark() {
		return paymentDetailsremark;
	}
	public void setPaymentDetailsremark(String paymentDetailsremark) {
		this.paymentDetailsremark = paymentDetailsremark;
	}

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

	public BigDecimal getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(BigDecimal collectedAmount) {
		this.collectedAmount = collectedAmount;
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

	public String getNextOrSaveButton() {
		return nextOrSaveButton;
	}
	public void setNextOrSaveButton(String nextOrSaveButton) {
		this.nextOrSaveButton = nextOrSaveButton;
	}

	public BigDecimal getPopulatedDebitCardNumber() {
		return populatedDebitCardNumber;
	}
	public void setPopulatedDebitCardNumber(BigDecimal populatedDebitCardNumber) {
		this.populatedDebitCardNumber = populatedDebitCardNumber;
	}

	public boolean isBooAuthozed() {
		return booAuthozed;
	}
	public void setBooAuthozed(boolean booAuthozed) {
		this.booAuthozed = booAuthozed;
	}

	public String getExcheckCashLimitMessage() {
		return excheckCashLimitMessage;
	}
	public void setExcheckCashLimitMessage(String excheckCashLimitMessage) {
		this.excheckCashLimitMessage = excheckCashLimitMessage;
	}

	public boolean getBooRendercollectiondatatable() {
		return booRendercollectiondatatable;
	}
	public void setBooRendercollectiondatatable(boolean booRendercollectiondatatable) {
		this.booRendercollectiondatatable = booRendercollectiondatatable;
	}

	public boolean getBoRenderTotalAmountCalPanel() {
		return boRenderTotalAmountCalPanel;
	}
	public void setBoRenderTotalAmountCalPanel(boolean boRenderTotalAmountCalPanel) {
		this.boRenderTotalAmountCalPanel = boRenderTotalAmountCalPanel;
	}

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

	public boolean getMainPanelRender() {
		return mainPanelRender;
	}
	public void setMainPanelRender(boolean mainPanelRender) {
		this.mainPanelRender = mainPanelRender;
	}

	public boolean getReceiptReportPanel() {
		return receiptReportPanel;
	}
	public void setReceiptReportPanel(boolean receiptReportPanel) {
		this.receiptReportPanel = receiptReportPanel;
	}

	public boolean getBooRenderModifiedApplTrnxReportByRound() {
		return booRenderModifiedApplTrnxReportByRound;
	}
	public void setBooRenderModifiedApplTrnxReportByRound(boolean booRenderModifiedApplTrnxReportByRound) {
		this.booRenderModifiedApplTrnxReportByRound = booRenderModifiedApplTrnxReportByRound;
	}

	public String getDenominationchecking() {
		return denominationchecking;
	}
	public void setDenominationchecking(String denominationchecking) {
		this.denominationchecking = denominationchecking;
	}

	public BigDecimal getChequeValue() {
		return chequeValue;
	}
	public void setChequeValue(BigDecimal chequeValue) {
		this.chequeValue = chequeValue;
	}

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

	// Eight Panel Credit card Details
	private boolean booRenderColCheque = false;
	private String colchequebankCode;
	private String colChequeRef;
	private Date colChequeDate;
	private Date effectiveMinDate = new Date();
	private String colChequeApprovalNo;

	// getter and setter for Eight Panel Credit card Details
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

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}
	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public String getColChequeApprovalNo() {
		return colChequeApprovalNo;
	}
	public void setColChequeApprovalNo(String colChequeApprovalNo) {
		this.colChequeApprovalNo = colChequeApprovalNo;
	}

	public List<PopulateData> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<PopulateData> serviceList) {
		this.serviceList = serviceList;
	}
	public BigDecimal getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(BigDecimal serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceGroupCode() {
		return serviceGroupCode;
	}
	public void setServiceGroupCode(String serviceGroupCode) {
		this.serviceGroupCode = serviceGroupCode;
	}
	public boolean isBooSingleService() {
		return booSingleService;
	}
	public void setBooSingleService(boolean booSingleService) {
		this.booSingleService = booSingleService;
	}
	public boolean isBooMultipleService() {
		return booMultipleService;
	}
	public void setBooMultipleService(boolean booMultipleService) {
		this.booMultipleService = booMultipleService;
	}


	/*      DECLERATION REPORT               */

	/*      DECLERATION REPORT               */
	private RemittanceDeclarationMainReportBean remitDeclarionMainReport;
	private String reportFileName;
	private boolean booDeclarationReportDisplay;
	private Boolean visableExceptionDailogForReceipt=false;
	private Boolean visableExceptionDailogForApplication=false;
	private CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevaluesListForReport = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();


	public CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> getColdatatablevaluesListForReport() {
		return coldatatablevaluesListForReport;
	}
	public void setColdatatablevaluesListForReport(
			CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevaluesListForReport) {
		this.coldatatablevaluesListForReport = coldatatablevaluesListForReport;
	}
	public Boolean getVisableExceptionDailogForApplication() {
		return visableExceptionDailogForApplication;
	}
	public void setVisableExceptionDailogForApplication(
			Boolean visableExceptionDailogForApplication) {
		this.visableExceptionDailogForApplication = visableExceptionDailogForApplication;
	}
	public Boolean getVisableExceptionDailogForReceipt() {
		return visableExceptionDailogForReceipt;
	}
	public void setVisableExceptionDailogForReceipt(
			Boolean visableExceptionDailogForReceipt) {
		this.visableExceptionDailogForReceipt = visableExceptionDailogForReceipt;
	}

	private List<CustomerDeclerationView> lstDeclarationView = new ArrayList<CustomerDeclerationView>();

	private List<DeclerationReportBean> lstDispDeclarationView = new ArrayList<DeclerationReportBean>();


	public void generateDeclerationReports(){
		setVisableExceptionDailogForReceipt(false);
		setExceptionMessageForReport(null);
		List<RemittanceDeclarationMainReportBean> remitDeclartionMainReportList = new CopyOnWriteArrayList<RemittanceDeclarationMainReportBean>();
		OutputStream outstream=null;
		try{
			lstDeclarationView.clear();
			lstDispDeclarationView.clear();

			fetchDeclatationReports();
			remitDeclartionMainReportList.add(remitDeclarionMainReport);
			initDeclerationReports(remitDeclartionMainReportList);
			
			String fileName = "DeclarationReport.pdf";
			printJasperDirectToPrinter(fileName);
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
				try {
					outstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void initDeclerationReports(List<RemittanceDeclarationMainReportBean> remitDeclartionMainReportList) throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remitDeclartionMainReportList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String reportPath = realPath +"\\reports\\design\\RemittanceReceiptNewReport.jasper"; //for Window
		String reportPath;
		/*
		 * OLd code 
		 * 
		 * if (sessionStateManage.getLanguageId().equals(new BigDecimal("1"))) {
			reportPath = realPath +"//reports//design//RemittanceDeclarationMainReport.jasper";
		}else{
			reportPath = realPath +"//reports//design//RemittanceDeclationArabicReport.jasper";
		}*/
		
		reportPath = realPath +"//reports//design//declarationmainrpt.jasper";
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}



	public void fetchDeclatationReports()throws Exception {/*

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
		
		
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;
		remitDeclarionMainReport.setWaterMarkCheck(false);
		remitDeclarionMainReport.setWaterMarkLogoPath(waterMark);
		remitDeclarionMainReport.setSubReport(subReportPath);

		lstDeclarationView = iPersonalRemittanceService.getCustomerDeclerationData(sessionStateManage.getCountryId(), getFinaceYear(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION), new BigDecimal(getDocumentNo()));

		String paymentMode = "C";

		for(PersonalRemittanceCollectionDataTable dataObj:coldatatablevaluesListForReport){

			if (lstDeclarationView !=null && lstDeclarationView.size() > 0) {
				String reportType = iPersonalRemittanceService.declartionLimitCheck(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), getCustomerNo(), new Date(), dataObj.getColpaymentmodeCode());
				if (reportType.trim()!="") {
					if (reportType.equalsIgnoreCase("Y")&&dataObj.getColpaymentmodeCode().equalsIgnoreCase( Constants.CashCode)) {
						remitDeclarionMainReport.setDeclaration2500Check(true);
					} else if(reportType.equalsIgnoreCase("Y")&&!dataObj.getColpaymentmodeCode().equalsIgnoreCase( Constants.CashCode)){
						remitDeclarionMainReport.setDeclaration10000Check(true);
					} else if(reportType.equalsIgnoreCase("N")&&!dataObj.getColpaymentmodeCode().equalsIgnoreCase( Constants.CashCode)){
						remitDeclarionMainReport.setDeclaration2500Check(false);
					} else if(reportType.equalsIgnoreCase("N")&&dataObj.getColpaymentmodeCode().equalsIgnoreCase( Constants.CashCode)){
						remitDeclarionMainReport.setDeclaration10000Check(false);
					}
				}

			}
		}
		if (lstDeclarationView !=null && lstDeclarationView.size() > 0) {
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
				declerationReportBean.setPurpose(purpose.toUpperCase());

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

				lstDispDeclarationView.add(declerationReportBean);

			}
		}
		remitDeclarionMainReport.setRemittanceDeclaration2500List(lstDispDeclarationView);
		remitDeclarionMainReport.setRemittanceDeclaration10000List(lstDispDeclarationView);

	*/


		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;
		remitDeclarionMainReport.setWaterMarkCheck(true);
		remitDeclarionMainReport.setWaterMarkLogoPath(waterMark);
		remitDeclarionMainReport.setSubReport(subReportPath);

		//lstDeclarationView = iPersonalRemittanceService.getCustomerDeclerationData(sessionStateManage.getCountryId(), collectionDocYear, new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),  collectionDocNo);
		lstDeclarationView = iPersonalRemittanceService.getCustomerDeclerationData(sessionStateManage.getCountryId(), getFinaceYear(), new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION), new BigDecimal(getDocumentNo()));

		String paymentMode = "C";
		String selectImagePath = realPath + Constants.WUH2H_SELECT_DECLARATION;
		String unselectImagePath = realPath + Constants.WUH2H_UNSELECT_DECLARATION;		
		Date transactionDate = new Date();
		
		if (lstDeclarationView !=null && lstDeclarationView.size() > 0) {
			for (CustomerDeclerationView customerDeclerationView : lstDeclarationView) {

				DeclerationReportBean declerationReportBean = new DeclerationReportBean();
				
				if (lstDeclarationView !=null && lstDeclarationView.size() > 0) {

					//String reportType = iPersonalRemittanceService.declartionLimitCheck(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(), getCustomerNo(),transactionDate, paymentMode);
					
					declerationReportBean.setTxnTypeSelect("Y");
					declerationReportBean.setDeclImagePath1(unselectImagePath);
					declerationReportBean.setDeclImagePath2(unselectImagePath);
					declerationReportBean.setDeclImagePath3(unselectImagePath);
					declerationReportBean.setDeclImagePath4(unselectImagePath);

				}
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
				/*if(companyDesObj!=null){
					companyName	 = companyDesObj.getCompanyName();
				}*/

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
					
					companyName = customerEmployee.getEmployeeName()==null?"":customerEmployee.getEmployeeName();
					//employer.append(customerEmployee.getEmployeeName()==null?"":customerEmployee.getEmployeeName());
					//employer.append(" ");
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
				
				declerationReportBean.setBranchName(branchName);
				declerationReportBean.setCustomerReference(getCustomerrefno());
				
				declerationReportBean.setBeneRelation(customerDeclerationView.getRelationDescription());
				

				declerationReportBean.setCashierName(sessionStateManage.getUserName().toUpperCase());

				if (customerDeclerationView.getSignatureSpecimenClob() != null) {
					declerationReportBean.setSignatutre(customerDeclerationView.getSignatureSpecimenClob().getSubString(1,(int) customerDeclerationView.getSignatureSpecimenClob().length()));
				}

				lstDispDeclarationView.add(declerationReportBean);

			}
		}
		remitDeclarionMainReport.setDeclarationList(lstDispDeclarationView);
			
	}

	public List<CustomerDeclerationView> getLstDeclarationView() {
		return lstDeclarationView;
	}

	public void setLstDeclarationView(List<CustomerDeclerationView> lstDeclarationView) {
		this.lstDeclarationView = lstDeclarationView;
	}

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




	/*	// final save 
		public void saveRemittance() {

			try{
				String outValue = null;
				String errorMsg = null;
				setBooDeclarationReportDisplay(false);
				// if round functionality done then Ex_Appl_Trnx record changed
				if (lstModifyRoundRecord.size() != 0) {
					ShoppingCartDataTableBean lstModifyRec = lstModifyRoundRecord.get(0);
					iPersonalRemittanceService.saveApplicationTransactionModifiedRecordByRound(lstModifyRec);
					// modified record storing separetly
					try{
						List<RemittanceReportBean> lstModified = fetchApplicationRemittanceReportData(lstModifyRec.getDocumentNo());
					}catch(Exception e){
						log.info("Exception in fetchApplicationRemittanceReportData"+e.getMessage());
					}
					setBooRenderModifiedApplTrnxReportByRound(true);
				} else {
					setBooRenderModifiedApplTrnxReportByRound(false);
				}


				// updating Status "S" in different Tables
				updadateRecords(Constants.S);

				HashMap<String, Object> returnResult = new HashMap<String, Object>();

				Collect collect = saveCollect();
				returnResult.put("Collect",collect);

				if(collect != null && collect.getDocumentCode() != null && collect.getDocumentFinanceYear() != null && collect.getDocumentNo() != null && collect.getNetAmount() != null){
					List<CollectDetail> lstCollectDetails = saveCollectionDetail(collect);
					if(lstCollectDetails != null && lstCollectDetails.size() != 0){
						BigDecimal collectionAmount = BigDecimal.ZERO;
						BigDecimal cashAmount = BigDecimal.ZERO;
						BigDecimal refundAmount = BigDecimal.ZERO;
						for (CollectDetail collectDetail : lstCollectDetails) {
							collectionAmount = collectionAmount.add(collectDetail.getCollAmt());
							if(collectDetail.getCollectionMode().equalsIgnoreCase(Constants.C)){
								cashAmount = cashAmount.add(collectDetail.getCollAmt());
							}
							if(collect.getRefoundAmount() != null && collect.getRefoundAmount().compareTo(BigDecimal.ZERO)!=0  ){
								refundAmount = collect.getRefoundAmount();
							}
						}

						if(collectionAmount.compareTo(collect.getNetAmount())==0){

							// collect details list
							returnResult.put("CollectDetail",lstCollectDetails);

							List<ForeignCurrencyAdjust> lstCurrencyAdjustment = saveForeignCurrencyAdjust(collect);
							if(lstCurrencyAdjustment != null && lstCurrencyAdjustment.size() != 0){
								BigDecimal totalNetCashAmount = BigDecimal.ZERO;
								BigDecimal totalNetRefundAmount = BigDecimal.ZERO;
								for (ForeignCurrencyAdjust foreignCurrencyAdjust : lstCurrencyAdjustment) {
									if(foreignCurrencyAdjust.getTransactionType().equalsIgnoreCase(Constants.C)){
										totalNetCashAmount = totalNetCashAmount.add(foreignCurrencyAdjust.getAdjustmentAmount());
									}
									if(foreignCurrencyAdjust.getTransactionType().equalsIgnoreCase(Constants.F)){
										totalNetRefundAmount = totalNetRefundAmount.add(foreignCurrencyAdjust.getAdjustmentAmount());
									}
								}

								if(totalNetCashAmount.compareTo(cashAmount)==0 && refundAmount.compareTo(totalNetRefundAmount)==0){

									// currency adjustment list
									returnResult.put("CurrencyAdjust",lstCurrencyAdjustment);

									returnResult.put("CountryId",sessionStateManage.getCountryId());
									returnResult.put("CompanyId",sessionStateManage.getCompanyId());
									returnResult.put("CustomerId",getCustomerNo());
									returnResult.put("UserName",sessionStateManage.getUserName());
									returnResult.put("NoofTrnx",getColremittanceNo());

									HashMap<String, Object> outRecord = iPersonalRemittanceService.saveAllRemittanceTransaction(returnResult);

									Collect collectDB = (Collect) outRecord.get("Collect");
									outValue = (String) outRecord.get("OutValue");
									errorMsg = (String) outRecord.get("ErrorMsg");

									if(errorMsg != null){
										String status = null;
										// updating Status "null" in different Tables
										updadateRecords(status);
										setErrmsg(errorMsg);
										RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
									}else{
										if(outValue != null){
											if (outValue.equalsIgnoreCase("PASS")) {
												//updateRemittanceTransactionSelectedList(collectDB);
												lstselectedrecord.clear();
												allPanelOff();
												setMainPanelRender(false);
												rtnStrin = "personalRemittanceSuccessPage";
												remittanceNo = new BigDecimal(0);
												fcsaleNo = new BigDecimal(0);
												cashAmount = new BigDecimal(0);
												coldatatablevalues.clear();

												// calling EX_INSERT_EMOS_TRANSFER_LIVE procedure changes - 17/12/2015 
												//Collect collect = (Collect)returnResult.get("Collect");

												if (collectDB != null && collectDB.getCollectionId() != null) {
													try {
														iPersonalRemittanceService.insertEMOSLIVETransfer(collectDB.getApplicationCountryId(), collectDB.getFsCompanyMaster().getCompanyId(), collectDB.getDocumentCode(), collectDB.getDocumentFinanceYear(), collectDB.getDocumentNo());
													} catch (AMGException e) {
														log.info("Exception occurs while moving data to Old Emos:" + collect.getDocumentFinanceYear() + " - " + collect.getDocumentNo());
													} catch (Exception e1) {
														log.info("Exception occurs while moving data to Old Emos:" + collect.getDocumentFinanceYear() + " - " + collect.getDocumentNo());
													}
												}
											} else {
												String status = null;
												// updating Status "null" in different Tables
												updadateRecords(status);
												System.out.println("out value is FAIL");
												log.info("Un reachable code out value in procedure is FAIL");
											}
										}else{
											String status = null;
											// updating Status "null" in different Tables
											updadateRecords(status);
											System.out.println("out value is null");
											log.info("Un reachable code out value in procedure is NULL");
										}
									}
								}else{
									String status = null;
									// updating Status "null" in different Tables
									updadateRecords(status);
									// Currency adjustment saved amount not maching - dialogue message
									System.out.println("Amount Not Matching with cash amount");
									setErrmsg(WarningHandler.showWarningMessage("lbl.cashamountnotmatchingincollectdetailsandcurrencyadjustment", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
									log.info("Exception occurs while checking Cash Amount in Collect Details and Currency adjust table :"+getErrmsg());
									RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
								}

							}else{
								String status = null;
								// updating Status "null" in different Tables
								updadateRecords(status);
								// Currency adjustment not saved - dialogue message
								System.out.println("Currency Adjustment not saved");
								setErrmsg(WarningHandler.showWarningMessage("lbl.norecordsfoundincurrencyadjust", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
								log.info("Exception occurs while checking Currency adjust not found :"+getErrmsg());
								RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
							}
						}else{
							String status = null;
							// updating Status "null" in different Tables
							updadateRecords(status);
							// collect Details saved amount not matching - dialogue message
							System.out.println("Amount Not Matching");
							setErrmsg(WarningHandler.showWarningMessage("lbl.netamountnotmatchingincollectionandcollectdetails", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
							log.info("Exception occurs while checking Amount in Collect Details and Collection table :"+getErrmsg());
							RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
						}
					}else{
						String status = null;
						// updating Status "null" in different Tables
						updadateRecords(status);
						// collect Details not yet save - dialogue message
						System.out.println("Collect Details No Records");
						setErrmsg(WarningHandler.showWarningMessage("lbl.norecordsfoundincollectdetails", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
						log.info("Exception occurs while checking Collect Details not found :"+getErrmsg());
						RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
					}
				}else{
					String status = null;
					// updating Status "null" in different Tables
					updadateRecords(status);
					// collection not yet save - dialogue message
					System.out.println("Collection No Records");
					setErrmsg(WarningHandler.showWarningMessage("lbl.norecordsfoundincollection", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
					log.info("Exception occurs while Collections not found :"+getErrmsg());
					RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
				}

			}catch(AMGException e){
				String status = null;
				// updating Status "null" in different Tables
				updadateRecords(status);
				System.out.println(" Records Not Saved : " + e.getMessage());
				setErrmsg(e.getMessage());
				log.info("Exception occurs while final Saving :"+getErrmsg());
				RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			}catch(Exception e){
				String status = null;
				// updating Status "null" in different Tables
				updadateRecords(status);
				System.out.println(" Records Not Saved : " + e.getMessage());
				setErrmsg(e.getMessage());
				log.info("Exception occurs while final Saving :"+getErrmsg());
				RequestContext.getCurrentInstance().execute("finalerrormsg.show();");
			}
		}
	 */

	// saving
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
			//THIS CODE ADDED FOR DECLARATION REPRINT REPORT PURPOSE
			BigDecimal totalAmt=BigDecimal.ZERO;
			if(tempDetailsList.size()>0){

				for(TempCollectDetail temCollectiondet:tempDetailsList){
					totalAmt=totalAmt.add(temCollectiondet.getCollAmt());
					if (temCollectiondet.getCollectionMode().equalsIgnoreCase(Constants.CashCode))
					{
						if(temCollectiondet.getCollAmt().compareTo(new BigDecimal(2500))>=1)
						{
							log.info("=========================cash"+collectionId);
							iPersonalRemittanceService.updateDeclarationIndicator( collectionId);

						}
					}
				}
				if(totalAmt.compareTo(new BigDecimal(10000))>=1)
				{
					log.info("========================= total"+collectionId);
					iPersonalRemittanceService.updateDeclarationIndicatorTotal(collectionId);


				}

			}

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
				rtnStrin = "personalRemittanceSuccessPage";
				remittanceNo = new BigDecimal(0);
				fcsaleNo = new BigDecimal(0);
				cashAmount = new BigDecimal(0);
				coldatatablevalues.clear();

				//for report generation
				setDocumentNo(collectionNumber.toPlainString());

				// Icash product Id
				BigDecimal icashBankID = null;

				List<BankMaster> lstICashBankID = generalService.getAllBankCodeFromBankMaster("ICASH");

				if(lstICashBankID != null && !lstICashBankID.isEmpty()){
					BankMaster icashBankRecord = lstICashBankID.get(0);
					icashBankID = icashBankRecord.getBankId();
				}

				// checking ICash and Routing Bank Id
				String checkICASH = iPersonalRemittanceService.checkICASHProduct(tempCollection.getApplicationCountryId(),sessionStateManage.getCompanyId(), tempCollection.getDocumentCode(), cFinYear, collectionNumber,icashBankID);

				if(checkICASH != null){
					String status = null;
					// updating Status "null" in different Tables
					updadateRecords(status);
					log.info(" Records Not Saved : " + errormsg);
					setProcedureError("INSERT_SERVICE_PIN_JAVA" + " : " +checkICASH);
					RequestContext.getCurrentInstance().execute("procedureErr.show();");
					return;
				}else{
					// calling EX_INSERT_EMOS_TRANSFER_LIVE procedure changes - 17/12/2015 
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
		setCheckRecieptEmail(false);
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
			//THIS CODE ADDED FOR DECLARATION REPRINT REPORT PURPOSE
			BigDecimal totalAmt=BigDecimal.ZERO;
			if(tempDetailsList.size()>0){
               /*
				for(TempCollectDetail temCollectiondet:tempDetailsList){
					totalAmt=totalAmt.add(temCollectiondet.getCollAmt());
					if (temCollectiondet.getCollectionMode().equalsIgnoreCase(Constants.CashCode))
					{
						List<AuthicationLimitCheckView> declarationReportCashAmt = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.DeclarationReportforCash);
						
						BigDecimal declarationCashAmt = BigDecimal.ZERO;
						if(declarationReportCashAmt.size() != 0){
							AuthicationLimitCheckView authRemitAmtLimit = declarationReportCashAmt.get(0);
							declarationCashAmt = authRemitAmtLimit.getAuthLimit();
						}
						
						
						if(temCollectiondet.getCollAmt().compareTo(declarationCashAmt)>=1)
						{
							log.info("=========================cash"+collectionId);
							iPersonalRemittanceService.updateDeclarationIndicator( collectionId);
						}
						
						// New format added on 29/08/2017
						if(isBooDeclarationReportDisplay())
						{
						log.info("=========================cash"+collectionId);
						iPersonalRemittanceService.updateDeclarationIndicator( collectionId);
						}
					}
				} */

				

			}

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

		try {
			// calling procedure to move appl to remit
			saveCollectbyProcedure(tempCollection, collectionId);
		} catch (AMGException e) {
			String status = null;
			// updating Status "null" in different Tables
			updadateRecords(status);
			log.info(" Records Not Saved : " + errormsg);
			setErrmsg(errormsg);
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
				rtnStrin = "personalRemittanceSuccessPage";
				remittanceNo = new BigDecimal(0);
				fcsaleNo = new BigDecimal(0);
				cashAmount = new BigDecimal(0);
				coldatatablevalues.clear();
				setBooReportDisplay(true);

				//for report generation
				setDocumentNo(collectionNumber.toPlainString());

				// Icash product Id
				BigDecimal icashBankID = null;

				List<BankMaster> lstICashBankID = generalService.getAllBankCodeFromBankMaster("ICASH");

				if(lstICashBankID != null && !lstICashBankID.isEmpty()){
					BankMaster icashBankRecord = lstICashBankID.get(0);
					icashBankID = icashBankRecord.getBankId();
				}

				// checking ICash and Routing Bank Id
				String checkICASH = iPersonalRemittanceService.checkICASHProduct(tempCollection.getApplicationCountryId(),sessionStateManage.getCompanyId(), tempCollection.getDocumentCode(), cFinYear, collectionNumber,icashBankID);

				if(checkICASH != null){
					String status = null;
					// updating Status "null" in different Tables
					updadateRecords(status);
					log.info(" Records Not Saved : " + errormsg);
					setProcedureError("INSERT_SERVICE_PIN_JAVA" + " : " +checkICASH);
					RequestContext.getCurrentInstance().execute("procedureErr.show();");
					return;
				}else{
					// calling EX_INSERT_EMOS_TRANSFER_LIVE procedure changes - 17/12/2015 
					if (tempCollection != null) {
						try {
							iPersonalRemittanceService.insertEMOSLIVETransfer(tempCollection.getApplicationCountryId(),sessionStateManage.getCompanyId(), tempCollection.getDocumentCode(), cFinYear, collectionNumber);
						} catch (AMGException e) {
							log.info("Exception occurs while moving data to Old Emos:" + cFinYear + " - " + collectionNumber);
						} catch (Exception e1) {
							log.info("Exception occurs while moving data to Old Emos:" + cFinYear + " - " + collectionNumber);
						}
					}
					
					
					List<String> customerList = icustomerRegistrationService.getCustomerEmailDetails(getCustomerNo());

					if(customerList!=null && customerList.size()>0)
					{
						String customerEmail=customerList.get(0);
						String customerEmailVerifiedOn=null;
						if(customerList.size()>1)
						{
							customerEmailVerifiedOn = customerList.get(1);
						}
						
						if((customerEmail != null && getEmailToSendReport() != null && customerEmail.equalsIgnoreCase(getEmailToSendReport()) && customerEmailVerifiedOn != null))
						{
							setBooReportDisplay(false);
							setCheckRecieptEmail(true);
							generatePersonalRemittanceReceiptReport();
						}else
						{
							setBooReportDisplay(true);
							setCheckRecieptEmail(false);
							if(customerEmail != null && getEmailToSendReport() != null && !customerEmail.equalsIgnoreCase(getEmailToSendReport()))
							{
								//Update customer table ,email verified on to null
								icustomerRegistrationService.updateCustomerEmailAndVerifiedOn(getCustomerNo(),getEmailToSendReport());
								
								//Send verification mail to new email
								if(getEmailToSendReport() != null && !getEmailToSendReport().trim().equalsIgnoreCase("")){
									sendEmailForVerification(getCustomerNo(), getCustomerFullName());
								}
								
							}else{
								//Send verification mail to new email
								if(getEmailToSendReport() != null && !getEmailToSendReport().trim().equalsIgnoreCase("")){
									sendEmailForVerification(getCustomerNo(), getCustomerFullName());
								}
							}
						}
					}else
					{
						setBooReportDisplay(true);
						setCheckRecieptEmail(false);
					}
					
					/*if(getCheckEmailForReport() && getEmailToSendReport()!=null)
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
					}*/
					if(getSpotExchangeRatePk()!=null)
					{
						iPlaceOnOrderCreationService.updatePlaceOrderRemitDocumentNo(getSpotExchangeRatePk(), collectionNumber, cFinYear);
					}

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

	public boolean isBooColApprovalNo()
	{
		return booColApprovalNo;
	}
	public void setBooColApprovalNo(boolean booColApprovalNo)
	{
		this.booColApprovalNo = booColApprovalNo;
	}
	public String getKnetIposReceipt()
	{
		return knetIposReceipt;
	}
	public void setKnetIposReceipt(String knetIposReceipt)
	{
		this.knetIposReceipt = knetIposReceipt;
	}
	public String getKnetTranId()
	{
		return knetTranId;
	}
	public void setKnetTranId(String knetTranId)
	{
		this.knetTranId = knetTranId;
	}
	public String getKnetReceiptDate()
	{
		return knetReceiptDate;
	}
	public void setKnetReceiptDate(String knetReceiptDate)
	{
		this.knetReceiptDate = knetReceiptDate;
	}
	public String getKnetReceiptTime()
	{
		return knetReceiptTime;
	}
	public void setKnetReceiptTime(String knetReceiptTime)
	{
		this.knetReceiptTime = knetReceiptTime;
	}

	public void remittancelistByBankIdForCash() {
		// spl pool based on routing country , routing bank
		specialRequestFcAmountCalculation();


		List<PopulateData> lstRemitView;
		try {
			lstRemitView = iPersonalRemittanceService
					.getRemittanceListByCountryBankForCashProduct(sessionStateManage.getCountryId(),getBeneficaryBankId(),getBeneficaryBankBranchId(),
							//getDatabenificarycountry(),
							getDataBankbenificarycountry(),
							getDatabenificarycurrency(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRoutingBranch(),
							new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));



			if (lstRemitView.size() == 0) {
				setBooSingleRemit(true);
				setBooMultipleRemit(false);
				setRemittanceName(null);
				setRemitMode(null);
				RequestContext.getCurrentInstance().execute("remittanceNoData.show();");
				return;
			} else if (lstRemitView.size() == 1) {
				setBooSingleRemit(true);
				setBooMultipleRemit(false);
				setRemittanceName(lstRemitView.get(0).getPopulateName());
				setRemitMode(lstRemitView.get(0).getPopulateId());

				deliverylistByRemitIdForCash();

			} else {
				setRemittanceName(null);
				setRemitMode(null);
				setBooSingleRemit(false);
				setBooMultipleRemit(true);
				setLstofRemittance(lstRemitView);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public void deliverylistByRemitIdForCash() {

		List<PopulateData> lstDeliveryView;
		try {
			lstDeliveryView = iPersonalRemittanceService
					.getDeliveryListByCountryBankRemitForCashProduct(sessionStateManage.getCountryId(),getBeneficaryBankId(),getBeneficaryBankBranchId(),
							//getDatabenificarycountry(),
							getDataBankbenificarycountry(),
							getDatabenificarycurrency(),getDataserviceid(),getRoutingCountry(),getRoutingBank(),getRoutingBranch(),getRemitMode(),
							new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));

			if (lstDeliveryView.size() == 0) {
				setBooRenderDeliveryModeInputPanel(true);
				setBooRenderDeliveryModeDDPanel(false);
				setDeliveryModeInput(null);
				setDeliveryMode(null);
				RequestContext.getCurrentInstance().execute("DeliveryNoData.show();");
				return;
			} else if (lstDeliveryView.size() == 1) {
				setBooRenderDeliveryModeInputPanel(true);
				setBooRenderDeliveryModeDDPanel(false);
				setDeliveryModeInput(lstDeliveryView.get(0).getPopulateName());
				setDeliveryMode(lstDeliveryView.get(0).getPopulateId());
				//bankBranchByBankView();
			} else {
				setDeliveryModeInput(null);
				setDeliveryMode(null);
				setBooRenderDeliveryModeInputPanel(false);
				setBooRenderDeliveryModeDDPanel(true);
				setLstofDelivery(lstDeliveryView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
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
	/**
	 * Added by MRU on 09/03/2016
	 */

	public void remitEquivalentCounterAmountForDisplay() throws AMGException{
		BigDecimal equivalentCurrency= null;
		if(isBooRenderPlaceOrder() && getAmountToRemit() != null){
			for (PopulateData popCurrency : lstofCurrency) {
				if(popCurrency.getPopulateId().compareTo(getCurrency()) != 0){
					equivalentCurrency = popCurrency.getPopulateId();
					break;
				}
			}
			
			int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(equivalentCurrency);
			if(getCurrency() != null && getCurrency().compareTo(new BigDecimal(sessionStateManage.getCurrencyId()))==0){
				setEquivalentRemitAmount(getAmountToRemit().divide(getSpotExchangeRate(),decimalvalue,BigDecimal.ROUND_HALF_UP));
			}else{
				setEquivalentRemitAmount(GetRound.roundBigDecimal(getAmountToRemit().multiply(getSpotExchangeRate()),decimalvalue));
			}
		}else if(isBooRenderPlaceOrderByRemit() && getAmountToRemit() != null){
			for (PopulateData popCurrency : lstofCurrency) {
				if(popCurrency.getPopulateId().compareTo(getCurrency()) != 0){
					equivalentCurrency = popCurrency.getPopulateId();
					break;
				}
			}
			
			int decimalvalue = foreignLocalCurrencyDenominationService.getDecimalPerCurrency(equivalentCurrency);
			if(getCurrency() != null && getCurrency().compareTo(new BigDecimal(sessionStateManage.getCurrencyId()))==0){
				setEquivalentRemitAmount(getAmountToRemit().divide(getSpotExchangeRate(),decimalvalue,BigDecimal.ROUND_HALF_UP));
			}else{
				setEquivalentRemitAmount(GetRound.roundBigDecimal(getAmountToRemit().multiply(getSpotExchangeRate()),decimalvalue));
			}
		}else{

			setEquivalentRemitAmount(null);
			setEquivalentCurrency(null);
			if(getAmountToRemit() != null){
				HashMap<String, String> hmoutPutValues = getRemitEquivalentAmtForSpecialRate();
				if(hmoutPutValues!=null){
					setEquivalentRemitAmount(new BigDecimal(hmoutPutValues.get("P_EQUIV_GROSS_AMOUNT")));
					if(hmoutPutValues.get("P_EQUIV_CURRENCY_ID")!=null && !hmoutPutValues.get("P_EQUIV_CURRENCY_ID").equals("0")){
						setEquivalentCurrency("["+generalService.getCurrencyQuote(new BigDecimal(hmoutPutValues.get("P_EQUIV_CURRENCY_ID")))+"]"); 
					}
				}
			}else{
				setEquivalentRemitAmount(null);
			}

		}
	}


	public void approvalNumberValidation() throws AMGException{
		try{
			setFocus(false);
			HashMap<String, String> inputOutValues = new HashMap<String, String>();
			inputOutValues.put("P_APPROVAL_YEAR", getApprovalYear()==null?"0":getApprovalYear().toString());
			inputOutValues.put("P_APPROVAL_NO", getApprovalNo()==null?"0":getApprovalNo().toString());
			System.out.println("currency :"+getCurrency());
			System.out.println("getCurrency :"+getCurrency() +"\t session currency :"+sessionStateManage.getCurrencyId());

			if(getApprovalNo()!=null && !getApprovalNo().equals("0") ){

				if(getCurrency()!=null){
					if(sessionStateManage.getCurrencyId().equalsIgnoreCase(getCurrency().toPlainString())){ 
						// kwd 
						System.out.println("approvalNumberValidation getCurrency :"+getCurrency() +"\t getForiegnCurrency() :"+getForiegnCurrency());
						inputOutValues.put("P_FOREIGN_CURRENCY_ID", getForiegnCurrency()==null?"0":getForiegnCurrency().toString());
						inputOutValues.put("P_FOREIGN_AMT",getEquivalentRemitAmount()==null?"0":getEquivalentRemitAmount().toString());
					}else{
						// foreign currency
						System.out.println("approvalNumberValidation getCurrency :"+getCurrency() +"\t getForiegnCurrency() :"+getForiegnCurrency());
						inputOutValues.put("P_FOREIGN_CURRENCY_ID", getForiegnCurrency()==null?"0":getForiegnCurrency().toString());
						inputOutValues.put("P_FOREIGN_AMT",getAmountToRemit()==null?"0":getAmountToRemit().toString());
					}
				}	

				HashMap<String, String> outPutValues= iPersonalRemittanceService.getSpecialApprovalCheck(inputOutValues);
				System.out.println("outPutValues. :"+outPutValues.get("P_ERROR_MESSAG"));

				if (outPutValues.get("P_ERROR_MESSAG") != null && !outPutValues.get("P_ERROR_MESSAG").equalsIgnoreCase("")) {
					setProcedureError("EX_SPECIAL_APPROVAL_CHECK" + " : " +outPutValues.get("P_ERROR_MESSAG"));
					RequestContext.getCurrentInstance().execute("procedureErr.show();");
					return;
				}
			}

		}catch(Exception e){
			setProcedureError("EX_SPECIAL_APPROVAL_CHECK" + e.getMessage());
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
			return;
		}

	}


	public void specialRateApproval(){
		setFocus(false);
		if(getAmountToRemit() != null){
			try{
				// calling currency equivalent amount , local currency amount
				//remitEquivalentCounterAmountForDisplay();

				int speApp = getSpecialApprovalRadio();
				System.out.println("speApp :"+speApp);
				if(speApp==1){ //For Appoval Number
					setApprovalRender(true);
					setSpotRateRender(false);
					setApprovalYear(getFinaceYear());
					setSpotExchangeRate(null);
					setSpotExchangeRatePk(null);
					setSpotRate(Constants.No);
					setMarqueeRender(false);
					setNextRender(true);
					setBooRenderPoll(false);
				}else if(speApp==2){ //For Spot Rate
					setApprovalRender(false);
					setSpotRateRender(true);
					setApprovalNo(null);
					setApprovalYear(null);
				}else if(speApp==3){
					setApprovalRender(false);
					setSpotRateRender(false);
					setApprovalNo(null);
					setApprovalYear(null);
					setSpotExchangeRate(null);
					setSpotExchangeRatePk(null);
					setSpotRate(Constants.No);
					setMarqueeRender(false);
					setNextRender(true);
					setBooRenderPoll(false);
				}
			}catch(Exception e){
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}else{

			setApprovalRender(false);
			setSpotRateRender(false);
			setApprovalNo(null);
			setApprovalYear(null);
			setSpotExchangeRate(null);
			setSpotExchangeRatePk(null);
			setSpotRate(Constants.No);
			setMarqueeRender(false);
			setNextRender(true);
			setBooRenderPoll(false);
			setSpecialApprovalRadio(Integer.parseInt(Constants.THREE));
			setExceptionMessage(WarningHandler.showWarningMessage("lbl.enterremittanceamount", sessionStateManage.getLanguageId()));
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// NEW
	/*private HashMap<String, String> getRemitEquivalentAmtForSpecialRate() throws AMGException
	{
		HashMap<String, String> inputValues = new HashMap<String, String>();

		inputValues.put("P_APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId() == null ? "0" : sessionStateManage.getCountryId().toString());
		inputValues.put("P_ROUTING_COUNTRY_ID", getRoutingCountry() == null ? "0" : getRoutingCountry().toString());
		inputValues.put("P_BRANCH_ID", sessionStateManage.getBranchId());
		inputValues.put("P_COMPANY_ID", sessionStateManage.getCompanyId() == null ? "0" : sessionStateManage.getCompanyId().toString());
		inputValues.put("P_ROUTING_BANK_ID", getRoutingBank() == null ? "0" : getRoutingBank().toString());
		inputValues.put("P_SERVICE_MASTER_ID", getDataserviceid() == null ? "0" : getDataserviceid().toString());
		inputValues.put("P_DELIVERY_MODE_ID", getDeliveryMode()==null ? "0" : getDeliveryMode().toString());
		inputValues.put("P_REMITTANCE_MODE_ID", getRemitMode()==null ? "0" : getRemitMode().toString());
		inputValues.put("P_FOREIGN_CURRENCY_ID", getForiegnCurrency() == null ? "0" : getForiegnCurrency().toString());
		inputValues.put("P_SELECTED_CURRENCY_ID", getCurrency() == null ? "0" : getCurrency().toString());
		inputValues.put("P_CUSTOMER_TYPE", getCustomerType());
		inputValues.put("P_SELECTED_CURRENCY_AMOUNT", getAmountToRemit() == null ? "0": getAmountToRemit().toString());
		inputValues.put("P_USER_TYPE", Constants.C);

		HashMap<String, String> outputValues = iPersonalRemittanceService.getRemitExchangeEquivalentAount(inputValues);

		return outputValues;
	}*/

	// OLD
	private HashMap<String, String> getRemitEquivalentAmtForSpecialRate() throws AMGException
	{
		HashMap<String, String> inputValues = new HashMap<String, String>();

		inputValues.put("P_APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId() == null ? "0" : sessionStateManage.getCountryId().toString());
		inputValues.put("P_ROUTING_COUNTRY_ID", getRoutingCountry() == null ? "0" : getRoutingCountry().toString());
		inputValues.put("P_BRANCH_ID", sessionStateManage.getBranchId());
		inputValues.put("P_COMPANY_ID", sessionStateManage.getCompanyId() == null ? "0" : sessionStateManage.getCompanyId().toString());
		inputValues.put("P_ROUTING_BANK_ID", getRoutingBank() == null ? "0" : getRoutingBank().toString());
		inputValues.put("P_SERVICE_MASTER_ID", getDataserviceid() == null ? "0" : getDataserviceid().toString());
		inputValues.put("P_DELIVERY_MODE_ID", getDeliveryMode()==null ? "0" : getDeliveryMode().toString());
		inputValues.put("P_REMITTANCE_MODE_ID", getRemitMode()==null ? "0" : getRemitMode().toString());
		inputValues.put("P_FOREIGN_CURRENCY_ID", getForiegnCurrency() == null ? "0" : getForiegnCurrency().toString());
		inputValues.put("P_SELECTED_CURRENCY_ID", getCurrency() == null ? "0" : getCurrency().toString());
		inputValues.put("P_CUSTOMER_TYPE", getCustomerType());
		inputValues.put("P_SELECTED_CURRENCY_AMOUNT", getAmountToRemit() == null ? "0": getAmountToRemit().toString());

		HashMap<String, String> outputValues = iPersonalRemittanceService.getRemitExchangeEquivalentAount(inputValues);

		return outputValues;
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
	public BigDecimal getEquivalentRemitAmount() {
		return equivalentRemitAmount;
	}
	public void setEquivalentRemitAmount(BigDecimal equivalentRemitAmount) {
		this.equivalentRemitAmount = equivalentRemitAmount;
	}
	public String getEquivalentCurrency() {
		return equivalentCurrency;
	}
	public void setEquivalentCurrency(String equivalentCurrency) {
		this.equivalentCurrency = equivalentCurrency;
	}
	public int getSpecialApprovalRadio() {
		return specialApprovalRadio;
	}
	public void setSpecialApprovalRadio(int specialApprovalRadio) {
		this.specialApprovalRadio = specialApprovalRadio;
	}
	public Boolean getSpotRateRender() {
		return spotRateRender;
	}
	public void setSpotRateRender(Boolean spotRateRender) {
		this.spotRateRender = spotRateRender;
	}
	public Boolean getApprovalRender() {
		return approvalRender;
	}
	public void setApprovalRender(Boolean approvalRender) {
		this.approvalRender = approvalRender;
	}

	// calling to modify the mobile number
	public void editableCustMob(){
		if(getBooRenderCustTelDisable() == true){
			setBooRenderCustTelDisable(false);
			setBooRenderCustTelMandatory(true);
			setDataCustomerTelephoneNumber(null);
		}else{
			setBooRenderCustTelDisable(true);
			setBooRenderCustTelMandatory(false);
		}
	}

	// delete account from beneficary list - deactivate "D" in Bene Relation Ship Table
	public void deleteBeneficiaryAccount(){
		if(deleteBeneRelationList.size() == 1){
			PersonalRemmitanceBeneficaryDataTable deletebene = deleteBeneRelationList.get(0);
			if(deletebene.getBeneficiaryRelationShipSeqId() != null){
				String status = Constants.D;
				iPersonalRemittanceService.deleteBeneAccountRecordPesonal(deletebene.getBeneficiaryAccountSeqId() ,deletebene.getBeneficiaryRelationShipSeqId(),status,getRemarksForBeneDelete());

				if(getBeneficiaryCountryId()!=null){
					//populateCustomerDetailsFromBeneRelation();
					populateBeneFiciaryCountry();
				}
				if(deleteBeneRelationList != null && !deleteBeneRelationList.isEmpty()){
					deleteBeneRelationList.clear();
				}
			}else{
				// bene account is null
				setExceptionMessage(WarningHandler.showWarningMessage("lbl.beneAccountSeqNull", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}
	}

	// delete confirmation condition
	public void deleteBeneRelationIsActive(PersonalRemmitanceBeneficaryDataTable deletebene){
		setRemarksForBeneDelete( null);
		if(deleteBeneRelationList != null && !deleteBeneRelationList.isEmpty()){
			deleteBeneRelationList.clear();
		}

		deleteBeneRelationList.add(deletebene);

		if(deleteBeneRelationList.size() == 1){
			// call dialogue box to confirm
			setExceptionMessage("Do you want to delete Beneficiary Account?");
			RequestContext.getCurrentInstance().execute("deleteBeneAcc1.show();");
		}
	}

	// calling to modify the mobile number
	public void editableBeneTelNum(){
		// calling to modify the mobile number
		if(getBooRenderBeneTelDisable() == true){
			setBooRenderBeneTelDisable(false);
			setBooRenderBeneTelMandatory(true);
			setBenificaryTelephone(null);
		}else{
			setBooRenderBeneTelDisable(true);
			setBooRenderBeneTelMandatory(false);
		}
	}

	// search operation in Personal remittance
	public void searchClicked() {
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("request", "personalRemittanceBean");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}
	}

	// call check beneficiary for validate the beneficiary details EX_CHK_BENEFICIARY
	public String checkBeneficiaryDetailsWithProc(PersonalRemmitanceBeneficaryDataTable datatabledetails){

		String errMsg = null;

		try{

			HashMap<String, Object> checkBeneDetails = new HashMap<String, Object>();

			checkBeneDetails.put("BeneBankCountryId", datatabledetails.getCountryId()); // bene bank country id
			checkBeneDetails.put("BeneServiceGroupId", datatabledetails.getServiceGroupId());
			checkBeneDetails.put("BeneBankId", datatabledetails.getBankId());
			checkBeneDetails.put("BeneBankBranchId", datatabledetails.getBranchId());
			checkBeneDetails.put("BeneCurrencyId", datatabledetails.getCurrencyId());
			checkBeneDetails.put("BeneAccountNumber", datatabledetails.getBankAccountNumber());
			checkBeneDetails.put("BeneBankServiceProvider", datatabledetails.getServiceProvider());
			checkBeneDetails.put("BeneFirstName", datatabledetails.getFirstName());
			checkBeneDetails.put("BeneSecondName", datatabledetails.getSecondName());
			checkBeneDetails.put("BeneThirdName", datatabledetails.getThirdName());
			checkBeneDetails.put("BeneFourthName", datatabledetails.getFourthName());
			checkBeneDetails.put("BeneFifthName", datatabledetails.getFiftheName());
			checkBeneDetails.put("BeneArabicFirstName", datatabledetails.getFirstNameLocal());
			checkBeneDetails.put("BeneArabicSecondName", datatabledetails.getSecondNameLocal());
			checkBeneDetails.put("BeneArabicThirdName", datatabledetails.getThirdNameLocal());
			checkBeneDetails.put("BeneArabicFourthName", datatabledetails.getFourthNameLocal());

			List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(datatabledetails.getBeneficaryMasterSeqId());

			if (telePhone != null && telePhone.size() != 0) {
				BeneficaryContact beneficaryContact = telePhone.get(0);
				if(beneficaryContact.getTelephoneNumber() != null){
					String telNumber = beneficaryContact.getTelephoneNumber().trim();
					checkBeneDetails.put("BeneTelephone", telNumber);
				}
				if(beneficaryContact.getMobileNumber() != null){
					BigDecimal mobileNumber = beneficaryContact.getMobileNumber();
					checkBeneDetails.put("BeneMobileNumber", mobileNumber);
				}
			}

			checkBeneDetails.put("BeneCountryId", datatabledetails.getBenificaryCountry());
			checkBeneDetails.put("BeneStateId", datatabledetails.getStateId());
			checkBeneDetails.put("BeneDistrictId", datatabledetails.getDistrictId());
			checkBeneDetails.put("BeneCityId", datatabledetails.getCityId());

			errMsg = beneficaryCreation.checkBeneDetailsValidation(checkBeneDetails);

		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			errMsg = getExceptionMessage();
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
		
		return errMsg;
	}
	
	public String getSwiftBic() {
		return swiftBic;
	}
	public void setSwiftBic(String swiftBic) {
		this.swiftBic = swiftBic;
	}

	private Boolean booRenderCorporateBack=false;

	public Boolean getBooRenderCorporateBack() {
		return booRenderCorporateBack;
	}
	public void setBooRenderCorporateBack(Boolean booRenderCorporateBack) {
		this.booRenderCorporateBack = booRenderCorporateBack;
	}

	public void corporateRemitNavtoPersonalRemit(){

		try {
			populateBeneFiciaryCountry();
			backFromBenificaryStatusPanel();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
			clearData();
			setBooRenderCorporateBack(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String companyName;
	private String companyNameLocal;

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyNameLocal() {
		return companyNameLocal;
	}
	public void setCompanyNameLocal(String companyNameLocal) {
		this.companyNameLocal = companyNameLocal;
	}

	//Added koti @09/08/2016

	/*public void toCheckBeneAddressDetails(){
		try {
			List<AdditionalDataDisplayView> lstAdditionalDataDisplayViews=iPersonalRemittanceService.tofetchRenderAddtionalDetails(sessionStateManage.getCountryId(),"BNFADDR1");
			if(lstAdditionalDataDisplayViews.size()>0){
				setBooRenderBeneAddreddDetails(true);
			}else{
				setBooRenderBeneAddreddDetails(false);
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}*/

	public void updateBeneMaster(){
		iPersonalRemittanceService.updateBeneMasterDetails(getMasterId(),getBeneHouseNo(),getBeneFlatNo(),getBeneStreetNo());
	}

	public boolean toCheckMandatroyFields(){
		boolean mandCheck=false;
		try {
			HashMap<String, String> inputValuesBeneAdd=new HashMap<String, String>();
			inputValuesBeneAdd.put("P_APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId() == null ? "0" : sessionStateManage.getCountryId().toString());
			inputValuesBeneAdd.put("P_ROUTING_COUNTRY_ID", getRoutingCountry() == null ? "0" : getRoutingCountry().toString());
			inputValuesBeneAdd.put("P_ROUTING_BANK_ID", getRoutingBank() == null ? "0" : getRoutingBank().toString());
			inputValuesBeneAdd.put("P_FOREIGN_CURRENCY_ID", getForiegnCurrency() == null ? "0" : getForiegnCurrency().toString());
			inputValuesBeneAdd.put("P_REMITTANCE_MODE_ID", getRemitMode()==null ? "0" : getRemitMode().toString());
			inputValuesBeneAdd.put("P_DELIVERY_MODE_ID", getDeliveryMode()==null ? "0" : getDeliveryMode().toString());
			inputValuesBeneAdd.put("P_BENEFICARY_HOUSE_NO", getBeneHouseNo());
			inputValuesBeneAdd.put("P_BENEFICARY_FLAT_NO", getBeneFlatNo());
			inputValuesBeneAdd.put("P_BENEFICARY_STREET_NO", getBeneStreetNo());
			inputValuesBeneAdd.put("P_BENE_COUNTRY_ID", getDataBankbenificarycountry() == null ? "0" : getDataBankbenificarycountry().toString());

			HashMap<String, String> outPutValuesBeneAdd=iPersonalRemittanceService.toValidateBeneAddrMandCheck(inputValuesBeneAdd);
			if(outPutValuesBeneAdd.get("P_ERROR_MESSAGE")!=null && !outPutValuesBeneAdd.get("P_ERROR_MESSAGE").equals("")){
				System.out.println("P_error_message :"+outPutValuesBeneAdd.get("P_ERROR_MESSAGE"));
				mandCheck=false;
				setProcedureError("EX_P_BENEFICARY_ADDRESS "+outPutValuesBeneAdd.get("P_ERROR_MESSAGE"));
				RequestContext.getCurrentInstance().execute("procedureErr.show();");
			}else{
				mandCheck=true;
			}
		} catch (Exception e) {
			setProcedureError("EX_P_BENEFICARY_ADDRESS "+e.getMessage());
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
		}
		return mandCheck;
	}

	private Boolean booRenderBeneAddreddDetails=false;
	private String beneAddressDetails;
	private String beneHouseNo;
	private String beneFlatNo;
	private String beneStreetNo;

	public Boolean getBooRenderBeneAddreddDetails() {
		return booRenderBeneAddreddDetails;
	}
	public void setBooRenderBeneAddreddDetails(Boolean booRenderBeneAddreddDetails) {
		this.booRenderBeneAddreddDetails = booRenderBeneAddreddDetails;
	}
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

	//added by Ram Mohan @10/08/2016




	public void searchSwiftCode()
	{
		SearchSwiftCodeBean searchSwiftCodeBean=	(SearchSwiftCodeBean)appContext.getBean("searchSwiftCodeBean");
		searchSwiftCodeBean.setFromSwiftCode("FromSwiftCode1");
		searchSwiftCodeBean.pageNavigation();
	}

	public void searchSwiftCode2()
	{
		SearchSwiftCodeBean searchSwiftCodeBean=	(SearchSwiftCodeBean)appContext.getBean("searchSwiftCodeBean");
		searchSwiftCodeBean.setFromSwiftCode("FromSwiftCode2");
		searchSwiftCodeBean.pageNavigation();
	}

	public void fetchSwiftBankName()
	{
		if(getBeneSwiftBank1()!=null && !getBeneSwiftBank1().isEmpty())
		{
			String swiftBankName=iPersonalRemittanceService.getSwiftBankNameFromSwiftBic(getBeneSwiftBank1());
			if(swiftBankName!=null && !swiftBankName.equalsIgnoreCase(""))
			{
				setBeneSwiftBankAddr1(swiftBankName);
			}else
			{
				setBeneSwiftBank1(null);
				setBeneSwiftBankAddr1(null);
				RequestContext.getCurrentInstance().execute("swiftBicNotExist.show();");		
				return;
			}

		}

	}

	public void fetchSwiftBankName2()
	{
		if(getBeneSwiftBank2()!=null && !getBeneSwiftBank2().isEmpty())
		{
			String swiftBankName=iPersonalRemittanceService.getSwiftBankNameFromSwiftBic(getBeneSwiftBank2());
			if(swiftBankName!=null && !swiftBankName.equalsIgnoreCase(""))
			{
				setBeneSwiftBankAddr2(swiftBankName);
			}else
			{
				setBeneSwiftBank2(null);
				setBeneSwiftBankAddr2(null);
				RequestContext.getCurrentInstance().execute("swiftBicNotExist.show();");		
				return;
			}

		}

	}

	public void gotoBeneficiaryEnquiry(){
		BenefeciaryEnquiryBean benEnqBean =(BenefeciaryEnquiryBean)appContext.getBean("benefeciaryEnquiryBean");
		benEnqBean.setCustomerNo(getCustomerNo());
		benEnqBean.setCustomerRefno(getCustomerrefno());
		benEnqBean.setIdNumber(getIdNumber());
		benEnqBean.setCustomerFullName(getCustomerFullName());
		benEnqBean.setCustomerLocalFullName(getCustomerLocalFullName());
		benEnqBean.preLoadData();
		benEnqBean.setBeneficiaryCountryId(getNationality());
		benEnqBean.populateBeneficiaryEnqData();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/beneficiaryEnquiry.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String messageFour;
	private String amlMessageFour;
	private Boolean booMessageFour;
	private BigDecimal swiftId1;
	private BigDecimal swiftId2;

	public String getMessageFour() {
		return messageFour;
	}
	public void setMessageFour(String messageFour) {
		this.messageFour = messageFour;
	}
	public String getAmlMessageFour() {
		return amlMessageFour;
	}
	public void setAmlMessageFour(String amlMessageFour) {
		this.amlMessageFour = amlMessageFour;
	}
	public Boolean getBooMessageFour() {
		return booMessageFour;
	}
	public void setBooMessageFour(Boolean booMessageFour) {
		this.booMessageFour = booMessageFour;
	}
	public BigDecimal getSwiftId1() {
		return swiftId1;
	}
	public void setSwiftId1(BigDecimal swiftId1) {
		this.swiftId1 = swiftId1;
	}
	public BigDecimal getSwiftId2() {
		return swiftId2;
	}
	public void setSwiftId2(BigDecimal swiftId2) {
		this.swiftId2 = swiftId2;
	}
	private AuthorizedLog getAuthLogSave()
	{
		AuthorizedLog authorizedLog = new AuthorizedLog();
		//setDocumentNo(getDocumentSerialID(Constants.Yes));
		authorizedLog.setDocumentFinancialYear(generalService.getDealYear(new Date()).get(0).getFinancialYear());
		authorizedLog.setDocumentDate(new Date());
		authorizedLog.setDocumentId(new BigDecimal(1));
		authorizedLog.setDocumentNo(new BigDecimal(getDocumentNo()));
		authorizedLog.setAuthorizedBy(getAmlAuthorizedBy());
		authorizedLog.setAmlRemarks(getAmlRemarks());
		authorizedLog.setAuthorizedPassword(getAmlAuthorizedPassword());
		authorizedLog.setAuthorizedLogId(sessionStateManage.getEmployeeId());
		authorizedLog.setCreatedBy(sessionStateManage.getUserName());
		authorizedLog.setCreatedDate(new Date());

		CountryMaster beneCountry = new CountryMaster();
		beneCountry.setCountryId(getDataBankbenificarycountry());
		authorizedLog.setBeneCountryId(beneCountry);
		Customer customer = new Customer();
		customer.setCustomerId(getCustomerNo());
		authorizedLog.setCustomerId(customer);

		CountryMaster applicationCountry = new CountryMaster();
		applicationCountry.setCountryId(sessionStateManage.getCountryId());

		authorizedLog.setAppCountryId(applicationCountry);

		CompanyMaster company = new CompanyMaster();
		company.setCompanyId(sessionStateManage.getCompanyId());
		authorizedLog.setCompanymaster(company);
		try {
			authorizedLog.setAccountYearMonth(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
		} catch (ParseException e) {
			e.printStackTrace();
		}


		return authorizedLog;
	}

	public List<AuthorizedLog> getAuthLogList()
	{

		List<AuthorizedLog> lstAuthorizedLog= new ArrayList<AuthorizedLog>();

		if(getBooMessageOne())
		{
			AuthorizedLog authorizedLog =getAuthLogSave();
			authorizedLog.setAuthorizedType(amlAuthuTYpeLog.get("AUTHTYPE1"));

			lstAuthorizedLog.add(authorizedLog);
		}			
		if (getBooMessageTwo())
		{
			AuthorizedLog authorizedLog =getAuthLogSave();
			authorizedLog.setAuthorizedType(amlAuthuTYpeLog.get("AUTHTYPE2"));
			lstAuthorizedLog.add(authorizedLog);
		}
		if (getBooMessageThree()) 
		{
			AuthorizedLog authorizedLog =getAuthLogSave();
			authorizedLog.setAuthorizedType(amlAuthuTYpeLog.get("AUTHTYPE3"));
			lstAuthorizedLog.add(authorizedLog);
		}

		if (getBooMessageFour()) 
		{
			AuthorizedLog authorizedLog =getAuthLogSave();
			authorizedLog.setAuthorizedType(amlAuthuTYpeLog.get("AUTHTYPE4"));
			lstAuthorizedLog.add(authorizedLog);

		}

		return lstAuthorizedLog;
	}
	public String getRemarksForBeneDelete() {
		return remarksForBeneDelete;
	}
	public void setRemarksForBeneDelete(String remarksForBeneDelete) {
		this.remarksForBeneDelete = remarksForBeneDelete;
	}

	public Boolean getRenderCustomerSignatureCheck() {
		return renderCustomerSignatureCheck;
	}
	public void setRenderCustomerSignatureCheck(Boolean renderCustomerSignatureCheck) {
		this.renderCustomerSignatureCheck = renderCustomerSignatureCheck;
	}

	// pending Application Call
	public void fetchPendingApplication(){

		try {
			getShoppingCartDetails(getCustomerNo());
			if (shoppingcartDTList.size() != 0) {
				RemittanceShoppingCart remittanceShoppingCart = (RemittanceShoppingCart)appContext.getBean("remittanceShoppingCart");
				remittanceShoppingCart.setCustomerFullName(getCustomerFullName());
				remittanceShoppingCart.setCustomerLocalFullName(getCustomerLocalFullName());
				remittanceShoppingCart.setCustomerId(getCustomerNo());
				remittanceShoppingCart.getShoppingCartDetails(getCustomerNo());

				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/remittanceShoppingCart.xhtml");
			} else {
				lstselectedrecord.clear();
				shoppingcartDTList.clear();
				RequestContext.getCurrentInstance().execute("noshoppingcard.show();");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Debit card changes
	private BigDecimal debitCardNoLength;
	private Boolean booRenderMultipleCardPrefix;
	private Boolean booRenderSingleeCardPrefix;
	private List<BankPrefix> lstBankPrefix;
	private String singleDebitCardPrefex;
	private String colDebitCardPrefex;
	private String debitCardNo;
	private String doubleDebitCardNo;

	private List<String> debitCardLst ;




	public String getDoubleDebitCardNo() {
		return doubleDebitCardNo;
	}
	public void setDoubleDebitCardNo(String doubleDebitCardNo) {
		this.doubleDebitCardNo = doubleDebitCardNo;
	}
	public List<String> getDebitCardLst() {
		return debitCardLst;
	}
	public void setDebitCardLst(List<String> debitCardLst) {
		this.debitCardLst = debitCardLst;
	}
	public String getColDebitCardPrefex() {
		return colDebitCardPrefex;
	}
	public void setColDebitCardPrefex(String colDebitCardPrefex) {
		this.colDebitCardPrefex = colDebitCardPrefex;
	}
	public String getDebitCardNo() {
		return debitCardNo;
	}
	public void setDebitCardNo(String debitCardNo) {
		this.debitCardNo = debitCardNo;
	}
	public String getSingleDebitCardPrefex() {
		return singleDebitCardPrefex;
	}
	public void setSingleDebitCardPrefex(String singleDebitCardPrefex) {
		this.singleDebitCardPrefex = singleDebitCardPrefex;
	}
	public BigDecimal getDebitCardNoLength() {
		return debitCardNoLength;
	}
	public void setDebitCardNoLength(BigDecimal debitCardNoLength) {
		this.debitCardNoLength = debitCardNoLength;
	}
	public Boolean getBooRenderMultipleCardPrefix() {
		return booRenderMultipleCardPrefix;
	}
	public void setBooRenderMultipleCardPrefix(Boolean booRenderMultipleCardPrefix) {
		this.booRenderMultipleCardPrefix = booRenderMultipleCardPrefix;
	}
	public Boolean getBooRenderSingleeCardPrefix() {
		return booRenderSingleeCardPrefix;
	}
	public void setBooRenderSingleeCardPrefix(Boolean booRenderSingleeCardPrefix) {
		this.booRenderSingleeCardPrefix = booRenderSingleeCardPrefix;
	}
	public List<BankPrefix> getLstBankPrefix() {
		return lstBankPrefix;
	}
	public void setLstBankPrefix(List<BankPrefix> lstBankPrefix) {
		this.lstBankPrefix = lstBankPrefix;
	}
	public void populateCustomerBankDetails() {

		int cardlength = 0;

		clearDebitDetails();

		localbankListForBankCode.clear();

		lstDebitCard.clear();
		setColAuthorizedby(null);
		setColCardNo(null);
		setColCardNoLength(null);
		setPopulatedDebitCardNumber(null);
		setColNameofCard(null);
		setColApprovalNo(null);
		setBooAuthozed(false);
		setColAuthorizedby(null);
		setColpassword(null);
		setLstBankPrefix(null);
		setBooRenderSingleeCardPrefix(true);
		setBooRenderMultipleCardPrefix(false);
		setDebitCardNoLength(null);

		//clearBank();

		if (getColBankCode() != null) {
			
			boolean OthersSts = checkKNETBanks();
			if(OthersSts){
				setColBankCode(null);
				setExceptionMessage(WarningHandler.showWarningMessage("lbl.bnkOthers", sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}
			
			localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getColBankCode());
			List<ViewBankDetails> lstViewBankDetails = icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getColBankCode());
			if(lstViewBankDetails!=null && lstViewBankDetails.size()!=0)
			{
				ViewBankDetails viewBankDetails = lstViewBankDetails.get(0);
				List<BankPrefix> lstBankPrefix = icustomerBankService.getBankPrefix(getColBankCode(), viewBankDetails.getChequeBankId());

				if(lstBankPrefix!=null)
				{
					cardlength = viewBankDetails.getDebitCardLength().intValue();
					setDebitCardNoLength(viewBankDetails.getDebitCardLength());
					setColCardNoLength(viewBankDetails.getDebitCardLength());
					if(lstBankPrefix.size()==1)
					{
						setBooRenderSingleeCardPrefix(true);
						setBooRenderMultipleCardPrefix(false);
						setSingleDebitCardPrefex(lstBankPrefix.get(0).getBankPrefix());
						int prefixLength=getSingleDebitCardPrefex().length();

						setColCardNoLength(new  BigDecimal(cardlength-prefixLength));
						populateDebitcardNumbers();
					}
					if(lstBankPrefix.size()>1)
					{
						setBooRenderSingleeCardPrefix(false);
						setBooRenderMultipleCardPrefix(true);
						setLstBankPrefix(lstBankPrefix);
					}
					if(lstBankPrefix.size()==0)
					{
						RequestContext.getCurrentInstance().execute("noBankPrefix.show();");
						return;
					}
				}else
				{
					setBooRenderSingleeCardPrefix(true);
					setBooRenderMultipleCardPrefix(false);
					RequestContext.getCurrentInstance().execute("noBankPrefix.show();");
					return;
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

	public void findPrefixLength()
	{
		clearDebitPrefixDetails();
		setBooRenderSingleDebit(false);
		setBooRenderMulDebit(false);
		populateDebitcardNumbers();
	}

	public void checkingCardNumberLength1() {

		if(getBooRenderSingleeCardPrefix())
		{
			String prefix=getSingleDebitCardPrefex();
			String debitCardno=null;
			if(getBooRenderSingleDebit())
			{
				debitCardno=getDebitCardNo();
			}else
			{
				debitCardno= getDoubleDebitCardNo();
			}

			setColCardNo(new BigDecimal(prefix+debitCardno));
			//setColCardNoLength( new BigDecimal(prefix.length()+debitCardno.length()));
		}
		if(getBooRenderMultipleCardPrefix())
		{
			String prefix=getColDebitCardPrefex();
			String debitCardno=null;
			if(getBooRenderSingleDebit())
			{
				debitCardno=getDebitCardNo();
			}else
			{
				debitCardno= getDoubleDebitCardNo();
			}

			setColCardNo(new BigDecimal(prefix+debitCardno));
			//setColCardNoLength( new BigDecimal(prefix.length()+debitCardno.length()));
		}

		if (getColCardNo() != null && getColCardNoLength() != null) {
			if ((getColCardNo().toString()).length() != getDebitCardNoLength().intValue()) {
				setDebitCardNo(null);
				setColCardNo(null);
				RequestContext.getCurrentInstance().execute("misMatchBankCardLength.show();");
				return;
			}
		}

		BigDecimal rtnCustomerId= checkDebitcardRegistered();
		if(rtnCustomerId.compareTo(BigDecimal.ZERO)!=0)
		{
			List<CustomerIdProof> lstCustomerIdProof =icustomerBankService.getCustomerBasedOnId(rtnCustomerId);
			if(lstCustomerIdProof.size()!=0)
			{
				CustomerIdProof customerIdProof = lstCustomerIdProof.get(0);
				String firstName=customerIdProof.getFsCustomer().getFirstName();
				String secondName=customerIdProof.getFsCustomer().getMiddleName();
				String thirdName=customerIdProof.getFsCustomer().getLastName();
				clearDebit();
				setExceptionMessage("This Card is already registered under customer "+ nullCheck(firstName) + nullCheck(secondName) + nullCheck(thirdName) +
						" with Civil Id "+customerIdProof.getIdentityInt());
				/*+" ID type " + customerIdProof.getFsBizComponentDataByIdentityTypeId().getFsBusinessComponent().getComponentName());*/
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}

		}
	}

	public void populateDebitcardNumbers()
	{
		setBooRenderSingleDebit(false);
		setBooRenderMulDebit(false);
		setDebitCardLst(null);

		setDebitCardNo(null);
		setDoubleDebitCardNo(null);
		setColCardNo(null);
		//localbankListForBankCode = icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getColBankCode());
		List<CustomerBank> lstCustomerBank = icustomerBankService.fetchcustomerBanksDetails(getCustomerNo(), getColBankCode());

		if(lstCustomerBank!=null && lstCustomerBank.size()!=0)
		{
			List<String> lstDebitCard = new ArrayList<String>();
			String bankPrefix=null;
			for (CustomerBank customerBank : lstCustomerBank) {

				String debitCardNo=encryptionDescriptionService.getDECrypted(Constants.EncriptionKey,customerBank.getDebitCard());


				if(getBooRenderMultipleCardPrefix() && (getLstBankPrefix()!=null && getLstBankPrefix().size()>0))
				{
					bankPrefix=getColDebitCardPrefex();
				}else
				{
					bankPrefix=getSingleDebitCardPrefex();
				}

				if(bankPrefix!=null)
				{
					String debitCardBankPrefix=debitCardNo.substring(0,bankPrefix.length());


					if(debitCardBankPrefix.equalsIgnoreCase(bankPrefix))
					{
						String debitCard=debitCardNo.substring(bankPrefix.length(),debitCardNo.length());

						lstDebitCard.add(debitCard);
					}

					/*if(debitCardNo.contains(bankPrefix))
						{
							String debitCard=debitCardNo.substring(bankPrefix.length(),debitCardNo.length());

							lstDebitCard.add(debitCard);
						}*/
				}
			}

			if(lstDebitCard!=null && lstDebitCard.size()==0)
			{
				setBooRenderSingleDebit(true);
				setBooRenderMulDebit(false);
			}else if(lstDebitCard!=null && lstDebitCard.size()==1)
			{
				setBooRenderSingleDebit(true);
				setBooRenderMulDebit(false);

				setDebitCardNo(lstDebitCard.get(0));
				setDoubleDebitCardNo(bankPrefix+lstDebitCard.get(0));
				setColCardNo(new BigDecimal(bankPrefix+lstDebitCard.get(0)));
				//populateCustomerDetails();

				BigDecimal rtnCustomerId= checkDebitcardRegistered();
				if(rtnCustomerId.compareTo(BigDecimal.ZERO)!=0)
				{
					List<CustomerIdProof> lstCustomerIdProof =icustomerBankService.getCustomerBasedOnId(rtnCustomerId);
					if(lstCustomerIdProof.size()!=0)
					{
						CustomerIdProof customerIdProof = lstCustomerIdProof.get(0);
						String firstName=customerIdProof.getFsCustomer().getFirstName();
						String secondName=customerIdProof.getFsCustomer().getMiddleName();
						String thirdName=customerIdProof.getFsCustomer().getLastName();
						clearDebit();
						setExceptionMessage("This Card is already registered under customer "+ nullCheck(firstName) + nullCheck(secondName) + nullCheck(thirdName) +
								" with Civil Id "+customerIdProof.getIdentityInt());
						/*+" ID type " + customerIdProof.getFsBizComponentDataByIdentityTypeId().getFsBusinessComponent().getComponentName());*/
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
						return;
					}
				}
				populateCustomerNameAuthDetails();

			}else if(lstDebitCard!=null && lstDebitCard.size()>1)
			{
				setBooRenderSingleDebit(false);
				setBooRenderMulDebit(true);
				setDebitCardLst(lstDebitCard);
			}


		}else
		{
			setBooRenderSingleDebit(true);
			setBooRenderMulDebit(false);
		}
	}

	/*public void getDebitCardDetails()
		{
			BigDecimal cardlength = BigDecimal.ZERO;
			if (getColBankCode() != null)
			{

				List<ViewBankDetails> localbankListFormView = icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getColBankCode());

				List<CustomerBank> localBankListinCollection = icustomerBankService.fetchcustomerBanksDetails(getCustomerNo(), getColBankCode());

				if(localbankListFormView.size() != 0){
					ViewBankDetails bankLength = localbankListFormView.get(0);
					cardlength = bankLength.getDebitCardLength();

				}

				if (localBankListinCollection.size() != 0) 
				{
					setColCardNoLength(cardlength);
					if (cardlength.compareTo(BigDecimal.ZERO) != 0) 
					{
						if (localBankListinCollection.size() == 1) 
						{
							for (CustomerBank customerBank : localBankListinCollection) {
								if (customerBank.getBankCode().equals(getColBankCode())) {
									setPopulatedDebitCardNumber(new BigDecimal(encryptionDescriptionService.getDECrypted(customerBank.getDebitCardName(),customerBank.getDebitCard())));
									setColCardNo(new BigDecimal(encryptionDescriptionService.getDECrypted(customerBank.getDebitCardName(),customerBank.getDebitCard())));
									setColNameofCard(customerBank.getDebitCardName());
									if (customerBank.getAuthorizedBy() != null) {
										//List<Employee> localEmpllist = generalService.getEmployeelist(sessionStateManage.getCountryId(),new BigDecimal(sessionStateManage.getBranchId()),new BigDecimal(sessionStateManage.getRoleId()));

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
						}

					}else {
						RequestContext.getCurrentInstance().execute("bankDebitCardLenErr.show();");
					}
				}


			}
		}*/

	public BigDecimal checkDebitcardRegistered()
	{
		String prefix = null;
		if(getBooRenderMultipleCardPrefix()){
			prefix = getColDebitCardPrefex();
		}
		
		if(getBooRenderSingleeCardPrefix()){
			prefix = getSingleDebitCardPrefex();
		}
		
		String suffix = null;
		if(getColCardNo() != null){
			suffix = getColCardNo().toPlainString().substring(12);
		}
		
		
		BigDecimal bankId = null;
		List<ViewBankDetails> lstViewBankDetails=  icustomerBankService.getChequeBnakIdFromView(getColBankCode());
		if(lstViewBankDetails!=null && lstViewBankDetails.size()>0)
		{
			ViewBankDetails viewBankDetails=lstViewBankDetails.get(0);
			bankId = viewBankDetails.getChequeBankId();
		}
		
		String cardNo = null;
		if(getColCardNo() != null){
			cardNo = getColCardNo().toPlainString();
		}
		
		List<BigDecimal>  lstCustomerId=icustomerBankService.checkDebitCardWithActiveStatus(cardNo,prefix,suffix,bankId);

		BigDecimal rtnCustomerId=BigDecimal.ZERO;
		if(lstCustomerId!=null)
		{
			for(BigDecimal customerId:lstCustomerId)
			{
				if(getCustomerNo().compareTo(customerId)!=0)
				{
					rtnCustomerId=customerId;
				}
			}
		}
		return rtnCustomerId;
	}

	public void clearDebitDetails()
	{
		setColDebitCardPrefex(null);
		setSingleDebitCardPrefex(null);
		setDebitCardNo(null);
		setColCardNo(null);
		setDoubleDebitCardNo(null);
		setColNameofCard(null);
		setColApprovalNo(null);
		setColAuthorizedby(null);
		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		setDebitCardLst(null);
		setColCardNo(null);
		setBooRenderSingleeCardPrefix(true);
		setBooRenderMultipleCardPrefix(false);
		setLstBankPrefix(null);
		setDebitCardNoLength(null);
		setBooAuthozed(false);
	}
	
	public void clearDebitPrefixDetails()
	{
		setSingleDebitCardPrefex(null);
		setDebitCardNo(null);
		setColCardNo(null);
		setDoubleDebitCardNo(null);
		setColNameofCard(null);
		setColApprovalNo(null);
		setColAuthorizedby(null);
		setBooAuthozed(false);
	}
	
	public void clearDebit()
	{
		setDebitCardNo(null);
		setColCardNo(null);
		setDoubleDebitCardNo(null);
		setColNameofCard(null);
		setColApprovalNo(null);
		setColAuthorizedby(null);
		setBooAuthozed(false);
	}

	public void populateCustomerNameAuthDetails()
	{
		String bankPrefix=null;
		String bankDebiCardNo=null;
		setPopulatedDebitCardNumber(null);
		setColCardNo(null);
		if(getBooRenderMultipleCardPrefix() && (getLstBankPrefix()!=null && getLstBankPrefix().size()>0))
		{
			bankPrefix=getColDebitCardPrefex();
		}else
		{
			bankPrefix=getSingleDebitCardPrefex();
		}

		if(getBooRenderMulDebit() && (getDoubleDebitCardNo()!=null && getDebitCardLst().size()>0))
		{
			bankDebiCardNo=getDoubleDebitCardNo().toString();
		}else
		{
			bankDebiCardNo=getDebitCardNo();
		}
		String selectedDebidCard=bankPrefix+bankDebiCardNo;
		setColCardNo(new BigDecimal(selectedDebidCard));
		List<CustomerBank> localBankListinCollection = icustomerBankService.fetchcustomerBanksDetails(getCustomerNo(), getColBankCode());
		if(localBankListinCollection!=null && localBankListinCollection.size()>0)
		{
			for (CustomerBank customerBank : localBankListinCollection) {
				if (customerBank.getBankCode().equals(getColBankCode())) {
					String localDebiCardNo=encryptionDescriptionService.getDECrypted(Constants.EncriptionKey,customerBank.getDebitCard());
					setPopulatedDebitCardNumber(new BigDecimal(encryptionDescriptionService.getDECrypted(Constants.EncriptionKey,customerBank.getDebitCard())));
					if(selectedDebidCard.equalsIgnoreCase(localDebiCardNo))
					{
						setColNameofCard(customerBank.getDebitCardName());
						if (customerBank.getAuthorizedBy() != null) {
							//List<Employee> localEmpllist = generalService.getEmployeelist(sessionStateManage.getCountryId(),new BigDecimal(sessionStateManage.getBranchId()),new BigDecimal(sessionStateManage.getRoleId()));


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
					}else
					{
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

			lstDebitCard.clear();
		}/*else {

				System.out.println("cardlength :" + cardlength);

				if (cardlength != null) {

					if (cardlength.compareTo(BigDecimal.ZERO) != 0) {
						setColCardNoLength(cardlength);
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
			}*/

	}
	
	// CHECK IF ANY DUPLIACTE AVAILABLE WHILE DOING TRANSACTION
	public String checkDuplicateDebitCard(){

		String errMsg = null;
		
		
		String prefix = null;
		if(getBooRenderMultipleCardPrefix()){
			prefix = getColDebitCardPrefex();
		}
		
		if(getBooRenderSingleeCardPrefix()){
			prefix = getSingleDebitCardPrefex();
		}
		
		String suffix = null;
		if(getColCardNo() != null){
			suffix = getColCardNo().toPlainString().substring(12);
		}
		
		BigDecimal bankId = null;
		List<ViewBankDetails> lstViewBankDetails=  icustomerBankService.getChequeBnakIdFromView(getColBankCode());
		if(lstViewBankDetails!=null && lstViewBankDetails.size()>0)
		{
			ViewBankDetails viewBankDetails=lstViewBankDetails.get(0);
			bankId = viewBankDetails.getChequeBankId();
		}
		
		String cardNo = null;
		if(getColCardNo() != null){
			cardNo = getColCardNo().toPlainString();
		}
		
		List<BigDecimal> customerBankPkId = icustomerBankService.getCustomerbankIdForDeactivatee(cardNo,prefix,suffix,bankId);

		if(customerBankPkId != null && customerBankPkId.size() != 0){
			if(customerBankPkId != null && customerBankPkId.size() == 1){
				// allow if active
				errMsg = null;
			}else{
				// multiple record
				errMsg = "Multiple Records Available Please Contact IT Department";
			}
		}else{
			// no record found
			errMsg = "Record Not Available";
		}
		
		return errMsg;
	}

	public void populateCustomerNameForMultiDebitCard()
	{
		lstDebitCard.clear();
		String bankPrefix=null;
		String bankDebiCardNo=null;
		setPopulatedDebitCardNumber(null);
		setColCardNo(null);
		if(getBooRenderMultipleCardPrefix() && (getLstBankPrefix()!=null && getLstBankPrefix().size()>0))
		{
			bankPrefix=getColDebitCardPrefex();
		}else
		{
			bankPrefix=getSingleDebitCardPrefex();
		}

		if(getBooRenderMulDebit() && (getDoubleDebitCardNo()!=null && getDebitCardLst().size()>0))
		{
			bankDebiCardNo=getDoubleDebitCardNo().toString();
		}else
		{
			bankDebiCardNo=getDebitCardNo();
		}
		String selectedDebidCard=bankPrefix+bankDebiCardNo;
		setColCardNo(new BigDecimal(selectedDebidCard));
		
		String checkTrnx = checkDuplicateDebitCard();
		
		if(checkTrnx != null){
			setDoubleDebitCardNo(null);
			setExceptionMessage(checkTrnx);
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}else{
			List<CustomerBank> localBankListinCollection = icustomerBankService.fetchcustomerBanksDetails(getCustomerNo(), getColBankCode());
			if(localBankListinCollection!=null && localBankListinCollection.size()>0)
			{
				for (CustomerBank customerBank : localBankListinCollection) {
					String localDebiCardNo=encryptionDescriptionService.getDECrypted(Constants.EncriptionKey,customerBank.getDebitCard());


					if(selectedDebidCard.equalsIgnoreCase(localDebiCardNo))
					{
						setColNameofCard(customerBank.getDebitCardName());
						if (customerBank.getAuthorizedBy() != null) {
							//List<Employee> localEmpllist = generalService.getEmployeelist(sessionStateManage.getCountryId(),new BigDecimal(sessionStateManage.getBranchId()),new BigDecimal(sessionStateManage.getRoleId()));


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

						CustomerBank lstofDebitCard = new CustomerBank();
						lstofDebitCard.setDebitCard(localDebiCardNo);
						/**Added by Rabil on 13/01/2016 **/	
						lstofDebitCard.setDebitCardName(customerBank.getDebitCardName());
						/**End by Rabil on 13/01/2016 **/	
						lstDebitCard.add(lstofDebitCard);
					}
				}
			}
		}
		
	}

	public String maskCCNumber(String ccnum){
		long starttime = System.currentTimeMillis();
		int total = ccnum.length();
		int startlen=6,endlen = 4;
		int masklen = total-(startlen + endlen) ;
		StringBuffer maskedbuf = new StringBuffer(ccnum.substring(0,startlen));
		for(int i=0;i<masklen;i++) {
			maskedbuf.append('*');
		}
		maskedbuf.append(ccnum.substring(startlen+masklen, total));
		String masked = maskedbuf.toString();
		long endtime = System.currentTimeMillis();

		return masked;
	}

//calling Mobile Appp service provide setup start
	public void callMobileAppVivaService()
	{
		MobileAppVivaServiceBean<T> mobileAppVivaServiceBean=	(MobileAppVivaServiceBean<T>)appContext.getBean("mobileAppVivaServiceBean");
		mobileAppVivaServiceBean.resetVlaues();
		mobileAppVivaServiceBean.setCustomerId(getCustomerNo());
		mobileAppVivaServiceBean.setCustomerName(getCustomerFullName());
		mobileAppVivaServiceBean.setCustomerArbicName(getCustomerLocalFullName());
		mobileAppVivaServiceBean.setCustomerrefno(getCustomerrefno());
		mobileAppVivaServiceBean.pageNavigateMobileAppVivaService();
	}
		
	//calling Mobile Appp service provide setup end
	
	//Place order
	private Boolean showPlaceOrderScreen;
	
	
	
	public Boolean getShowPlaceOrderScreen() {
		return showPlaceOrderScreen;
	}
	public void setShowPlaceOrderScreen(Boolean showPlaceOrderScreen) {
		this.showPlaceOrderScreen = showPlaceOrderScreen;
	}
	
	public HashMap<String, String> checkPlaceAnOrderExist()
	{
		HashMap<String, String> rtnValues = new HashMap<String, String>();
		
		try{
			List<RatePlaceOrder> lstRatePlaceOrder =iPlaceOnOrderCreationService.checkPlaceAnorderExist(getCustomerNo(),new BigDecimal(sessionStateManage.getBranchId()));
			
			if(lstRatePlaceOrder!=null && lstRatePlaceOrder.size()>0)
			{
				rtnValues.put("PLACEORDER_EXIST", Constants.Yes);
				setShowPlaceOrderScreen(false);
				
				BranchStaffGSMRateBeen branchStaffGSMRateBeen = (BranchStaffGSMRateBeen) appContext.getBean("branchStaffGSMRateBean");
				branchStaffGSMRateBeen.setBooRenderBranchStaffRemittance(true);
				branchStaffGSMRateBeen.pageNavigationFromPersonalRemitt(getCustomerNo(),getIdNumber(),getSelectCard());
			}else
			{
				rtnValues.put("PLACEORDER_EXIST", Constants.No);
			}
			
		} catch (AMGException ex) {
			log.info("Problem in Funtion :" + ex);
			setProcedureError(ex.getMessage());
			rtnValues.put("PLACEORDER_EXIST", Constants.No);
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
		} catch (Exception e) {
			rtnValues.put("PLACEORDER_EXIST", Constants.No);
			setProcedureError(e.getMessage());
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
		}
		return rtnValues;
	}
	
	public void saveRemittanceInfoSpecialRate(HashMap<String, String> outPutValues) {
		/** START 29/01/2015 */
		List<RatePlaceOrder> lstofspot = new ArrayList<RatePlaceOrder>();
		/** END 29/01/2015 */
		RatePlaceOrder ratePlaceOrder = new RatePlaceOrder();
		/** START 29/01/2015 */
		//lstofspot = iPersonalRemittanceService.getDocumentSeriality();
		/** END 29/01/2015 */
		ratePlaceOrder.setApplicationCountryId(sessionStateManage.getCountryId());
		
		getDocumentDescription();
		getFinaceYear();
		BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.U);
		
		RatePlaceOrder ratePlaceOrderObj=new RatePlaceOrder();

		ratePlaceOrderObj.setBeneficiaryBankId(getBeneficaryBankId());
		ratePlaceOrderObj.setCustomerId(getCustomerNo());
		ratePlaceOrderObj.setCreatedBy(sessionStateManage.getUserName());
		ratePlaceOrderObj.setCreatedDate(new Date());
		ratePlaceOrderObj.setBeneficiaryCountryId(getBeneficiaryCountryId());
		ratePlaceOrderObj.setBeneficiaryAccountNo(getDataAccountnum());
		ratePlaceOrderObj.setRemitType(getDataservicegroupid());
		ratePlaceOrderObj.setCustomerEmail(getCustomerEmail());
		//ratePlaceOrderObj.setTransactionAmount(new BigDecimal(outPutValues.get("P_EQUIV_GROSS_AMOUNT")));
		ratePlaceOrderObj.setTransactionAmount(getAmountToRemit());
		ratePlaceOrderObj.setIsActive(Constants.U);

		if(getBeneficiaryAccountSeqId()!=null){
			BeneficaryAccount beneAccountObj=new BeneficaryAccount();
			beneAccountObj.setBeneficaryAccountSeqId(getBeneficiaryAccountSeqId());
			ratePlaceOrderObj.setAccountSeqquenceId(beneAccountObj );
		}
		if(getMasterId()!=null)
		{
			BeneficaryMaster beneMasterObj=new BeneficaryMaster();
			beneMasterObj.setBeneficaryMasterSeqId(getMasterId());
			ratePlaceOrderObj.setBeneficiaryMasterId(beneMasterObj);
		}
		if(getForiegnCurrency()!=null)
		{
			ratePlaceOrderObj.setDestinationCurrenyId(new BigDecimal(outPutValues.get("P_EQUIV_CURRENCY_ID")));
		}
		if(getCurrency()!=null)
		{
			ratePlaceOrderObj.setRequestCurrencyId(getCurrency()); 
		}

		ratePlaceOrderObj.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
		
		ratePlaceOrderObj.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER));
		ratePlaceOrderObj.setDocumentId(getDocumentId());
		ratePlaceOrderObj.setDocumentNumber(docRefNumber);
		ratePlaceOrderObj.setDocumentFinanceYear(getFinaceYear());
		ratePlaceOrderObj.setCompanyId(sessionStateManage.getCompanyId());
		ratePlaceOrderObj.setApplicationCountryId(sessionStateManage.getCountryId());
		ratePlaceOrderObj.setCustomerreference(getCustomerrefno());
		ratePlaceOrderObj.setValueDate(new Date());
		
		iPlaceOnOrderCreationService.saveRecord(ratePlaceOrderObj);
	}
	
	public String getDocumentDescription() {
		String  documentDescription=null;

		try{
			List<Document> documentDesc=generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			for(Document des:documentDesc)
			{
				setDocumentId(des.getDocumentID());

			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return null;       
		}
		return documentDescription;
	}
	
	public BigDecimal getDocumentSerialIdNumber(String processIn) {
		
		String documentSerialId="0";
		 try {
				HashMap<String, String> outPutValues= generalService.getNextDocumentRefNumber(sessionStateManage.getCountryId().intValue() , sessionStateManage.getCompanyId().intValue(), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_PLACEORDER) , getFinaceYear().intValue(),processIn,sessionStateManage.getCountryBranchCode());
				
				//String proceErrorFlag=outPutValues.get("PROCE_ERORRFLAG");
				String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
				if(proceErrorMsg!=null)
				{
					//setBooDocVisble(Boolean.TRUE);
					setErrmsg(proceErrorMsg);
					RequestContext.getCurrentInstance().execute("proceErr.show();");
					return BigDecimal.ZERO;
				}else if(outPutValues.get("DOCNO") !=null && new BigDecimal(outPutValues.get("DOCNO")).compareTo(BigDecimal.ZERO)==0){
					//setBooDocVisble(Boolean.TRUE);
					RequestContext.getCurrentInstance().execute("docZero.show();");
					return BigDecimal.ZERO;
					
				}else
				{
					//setBooDocVisble(Boolean.FALSE);
					documentSerialId=outPutValues.get("DOCNO");
				}
			} catch (NumberFormatException | AMGException e) {
				
				//setBooDocVisble(Boolean.TRUE);
				setErrmsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return BigDecimal.ZERO;
			}
		
		
		return new BigDecimal(documentSerialId);
	}
	
	// place order redirect to create
	public void createPlaceOrderForBene(PersonalRemmitanceBeneficaryDataTable datatabledetails){

		log.info("Enter into createPlaceOrderForBene method ");
		// check beneficiary account type, state , district id setted or not
		try{
			HashMap <String,String> inputValues = new HashMap<String,String>();
			inputValues.put("P_BENE_MASTER_SEQ", datatabledetails.getBeneficaryMasterSeqId()==null ? "0" : datatabledetails.getBeneficaryMasterSeqId().toPlainString());
			inputValues.put("P_BENE_ACCOUNT_SEQ", datatabledetails.getBeneficiaryAccountSeqId()==null ? "0" : datatabledetails.getBeneficiaryAccountSeqId().toPlainString());
			inputValues.put("P_CUSTOMER_ID", datatabledetails.getCustomerId()==null ? "0" : datatabledetails.getCustomerId().toPlainString());

			String beneEditStatus=iPersonalRemittanceService.getBeneficiaryStatusForEdit(inputValues);

			if(beneEditStatus!=null && beneEditStatus.equalsIgnoreCase("E5"))
			{
				// err msg acc type , state , district and city
				setExceptionMessage("Please edit beneficiary and fill the blank details and save the record and then continue Place Order");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}else {
				if(!datatabledetails.getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
					if(datatabledetails.getMapSequenceId() != null){
						
						// bank account type check
						boolean chkAccTypeValue = chkBeneficiaryAccountType(datatabledetails);
						if(!chkAccTypeValue){
							log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+datatabledetails.getCountryId());
							log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+datatabledetails.getBankAccountTypeId());
							log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+datatabledetails.getCustomerId());
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
							setExceptionMessage("Account Type Mismatch, Please edit and save");
							return;
						}
						
						String errMsg = checkBeneficiaryDetailsWithProc(datatabledetails);

						if(errMsg != null && !errMsg.equalsIgnoreCase("")){
							setProcedureError(" EX_CHK_BENEFICIARY : "+ errMsg + " Please Contact Branch Manager for Modification of Beneficiary Details");
							RequestContext.getCurrentInstance().execute("procedureErr.show();");
							return;
						}else{
							
							String proceValiMessage =null;

							proceValiMessage = iPersonalRemittanceService.getValidateBeneficiaryProcedure(sessionStateManage.getCountryId(),datatabledetails.getCustomerId(),sessionStateManage.getUserName(),datatabledetails.getBeneficaryMasterSeqId(),datatabledetails.getBeneficiaryAccountSeqId());

							if(proceValiMessage!=null && proceValiMessage.length()>0){
								setProcedureError(" EX_P_VALIDATE_BENEFICIARY : "+proceValiMessage);
								RequestContext.getCurrentInstance().execute("procedureErr.show();");
								return;
							}

							HashMap<String ,String> bannedBankProcedureOut = iPersonalRemittanceService.getBannedBankCheckProcedure(sessionStateManage.getCountryId(),datatabledetails.getBankId(),datatabledetails.getBeneficaryMasterSeqId());

							if(bannedBankProcedureOut.get("P_ERROR_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ERROR_MESSAGE").equals("")){
								System.out.println("P_error_message :"+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
								setProcedureError("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
								RequestContext.getCurrentInstance().execute("procedureErr.show();");
								return;
							}else if(bannedBankProcedureOut.get("P_ALERT_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ALERT_MESSAGE").equals("")){
								System.out.println("P_ALERT_MESSAGE :"+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
								setProcedureError("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
								RequestContext.getCurrentInstance().execute("procedureErr.show();");
							}else{
								// no error in EX_P_BANNED_BANK_CHECK
							}
							
							PlaceOrderCreationBean<T> placeOrderCreationBean = (PlaceOrderCreationBean) appContext.getBean("placeOrderCreationBean");
							placeOrderCreationBean.setCustomerId(getCustomerNo());
							placeOrderCreationBean.setBooRenderPlaceOrderFromRemit(true);
							placeOrderCreationBean.branchPersonalRemitPageNavigation(datatabledetails);
						}
					}else{
						setExceptionMessage("Please edit beneficiary and fill the blank details and save the record and then continue Place Order");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}
				}else{
					setExceptionMessage("Western Union Place Order cannot be created");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}
		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
		log.info("Exit into createPlaceOrderForBene method ");
		
	}
	

	// accept button action should be process
	public void fetchAllPlaceOrderDetails(){
		HashMap<String, String> rtnValues = checkPlaceAnOrderExist();
		if(rtnValues.get("PLACEORDER_EXIST") != null && rtnValues.get("PLACEORDER_EXIST").equalsIgnoreCase(Constants.No) ){
			setExceptionMessage("No Place Orders Available");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}
	
	//OTP implementation starts
	private BigDecimal otpNo;
	private Boolean booRenderOtpPanel;
	private Boolean otpSelection;
	private Boolean booRenderOtpLinkPanel;
	private DateTime prevouseClickedtime;
	private String otpAuthorizedBy;
	private String otpAuthorizedPassword;
	private String otpRemarks;
	
	// otp
	public Boolean booRenderOtpAuthPanel = false;
	private BigDecimal otpRetry;
	private Date otpRetryDate;
	private Date otpVerifiedDate;
	private String otpVerifiedBy;
	
	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private List<DebitAutendicationView> branchManagerApproval = new ArrayList<DebitAutendicationView>();
	
	public Boolean getBooRenderOtpAuthPanel() {
		return booRenderOtpAuthPanel;
	}
	public void setBooRenderOtpAuthPanel(Boolean booRenderOtpAuthPanel) {
		this.booRenderOtpAuthPanel = booRenderOtpAuthPanel;
	}
	
	public BigDecimal getOtpRetry() {
		return otpRetry;
	}
	public void setOtpRetry(BigDecimal otpRetry) {
		this.otpRetry = otpRetry;
	}

	public Date getOtpRetryDate() {
		return otpRetryDate;
	}
	public void setOtpRetryDate(Date otpRetryDate) {
		this.otpRetryDate = otpRetryDate;
	}

	public Date getOtpVerifiedDate() {
		return otpVerifiedDate;
	}
	public void setOtpVerifiedDate(Date otpVerifiedDate) {
		this.otpVerifiedDate = otpVerifiedDate;
	}

	public String getOtpVerifiedBy() {
		return otpVerifiedBy;
	}
	public void setOtpVerifiedBy(String otpVerifiedBy) {
		this.otpVerifiedBy = otpVerifiedBy;
	}
	
	public DateTime getPrevouseClickedtime() {
		return prevouseClickedtime;
	}
	public void setPrevouseClickedtime(DateTime prevouseClickedtime) {
		this.prevouseClickedtime = prevouseClickedtime;
	}

	public Boolean getBooRenderOtpLinkPanel() {
		return booRenderOtpLinkPanel;
	}
	public void setBooRenderOtpLinkPanel(Boolean booRenderOtpLinkPanel) {
		this.booRenderOtpLinkPanel = booRenderOtpLinkPanel;
	}

	public Boolean getOtpSelection() {
		return otpSelection;
	}
	public void setOtpSelection(Boolean otpSelection) {
		this.otpSelection = otpSelection;
	}

	public BigDecimal getOtpNo() {
		return otpNo;
	}
	public void setOtpNo(BigDecimal otpNo) {
		this.otpNo = otpNo;
	}

	public Boolean getBooRenderOtpPanel() {
		return booRenderOtpPanel;
	}
	public void setBooRenderOtpPanel(Boolean booRenderOtpPanel) {
		this.booRenderOtpPanel = booRenderOtpPanel;
	}

	public String getOtpAuthorizedBy() {
		return otpAuthorizedBy;
	}
	public void setOtpAuthorizedBy(String otpAuthorizedBy) {
		this.otpAuthorizedBy = otpAuthorizedBy;
	}

	public String getOtpAuthorizedPassword() {
		return otpAuthorizedPassword;
	}
	public void setOtpAuthorizedPassword(String otpAuthorizedPassword) {
		this.otpAuthorizedPassword = otpAuthorizedPassword;
	}

	public String getOtpRemarks() {
		return otpRemarks;
	}
	public void setOtpRemarks(String otpRemarks) {
		this.otpRemarks = otpRemarks;
	}

	public List<DebitAutendicationView> getBranchManagerApproval() {
		return branchManagerApproval;
	}
	public void setBranchManagerApproval(List<DebitAutendicationView> branchManagerApproval) {
		this.branchManagerApproval = branchManagerApproval;
	}
	
	public void checkOtpNo()
	{
		try {

			if(getOtpNo() != null){
				String rtnMessage=icustomerRegistrationService.verifyOtpNo(getOtpNo(), getCustomerNo(), sessionStateManage.getUserName());
				if(rtnMessage!=null)
				{
					if(rtnMessage.equalsIgnoreCase("MATCH"))
					{
						setOtpAuthorizedBy(null);
						setOtpAuthorizedPassword(null);
						setOtpRemarks(null);
						setBooRenderOtpAuthPanel(false);
						setBooRenderOtpLinkPanel(false);
						setOtpSelection(false);
					}else if(rtnMessage.equalsIgnoreCase("OTPNOTMATCH"))
					{
						setOtpNo(null);
						setExceptionMessage(WarningHandler.showWarningMessage("lbl.otpNotMatch", sessionStateManage.getLanguageId()));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
						//RequestContext.getCurrentInstance().execute("otpNotMatch.show();");
					}
				}
			}

		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}
	
	public void enableAuthorization(){

		try {

			if(getOtpNo() != null){
				String rtnMessage;
				rtnMessage = icustomerRegistrationService.verifyOtpNo(getOtpNo(), getCustomerNo(), sessionStateManage.getUserName());
				if(rtnMessage!=null)
				{
					if(rtnMessage.equalsIgnoreCase("MATCH"))
					{
						// dont display
						setOtpAuthorizedBy(null);
						setOtpAuthorizedPassword(null);
						setOtpRemarks(null);
						setBooRenderOtpAuthPanel(false);
					}else if(rtnMessage.equalsIgnoreCase("OTPNOTMATCH"))
					{
						setOtpAuthorizedBy(null);
						setOtpAuthorizedPassword(null);
						setOtpRemarks(null);
						setBooRenderOtpAuthPanel(true);
						fetchAuthorizationDetails();
					}
				}else{
					setOtpAuthorizedBy(null);
					setOtpAuthorizedPassword(null);
					setOtpRemarks(null);
					setBooRenderOtpAuthPanel(true);
					fetchAuthorizationDetails();
				}
			}else{
				setOtpAuthorizedBy(null);
				setOtpAuthorizedPassword(null);
				setOtpRemarks(null);
				setBooRenderOtpAuthPanel(true);
				fetchAuthorizationDetails();
			}
		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}
	
	public void checkCustomerMobileNumberModified(){
		
		// clear all fields
		setOtpNo(null);
		setOtpAuthorizedBy(null);
		setOtpAuthorizedPassword(null);
		setOtpRemarks(null);
		setOtpRetry(null);
		setOtpRetryDate(null);
		setOtpSelection(null);
		setOtpVerifiedBy(null);
		setOtpVerifiedDate(null);
		setBooRenderOtpAuthPanel(false);
		
		// checking customer telephone number modified or not
		if(getDataCustomerTelephoneNumber() != null && !getDataCustomerTelephoneNumber().equalsIgnoreCase("")){
			if(getDataCustomerContactId() != null){
				if(getDataTempCustomerMobile() != null && getDataTempCustomerMobile().equalsIgnoreCase(getDataCustomerTelephoneNumber())){
					System.out.println("equal");
					setBooRenderOtpPanel(false);
				}else{
					System.out.println("difference");
					setBooRenderOtpPanel(true);
				}
			}else{
				String errString = "Customer Contact Details Not Available, Please Update Contact Details in Customer Registration";
				setExceptionMessage(WarningHandler.showWarningMessage(errString, sessionStateManage.getLanguageId()));
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}
		}else{
			setExceptionMessage(WarningHandler.showWarningMessage("lbl.custMobNumisMand", sessionStateManage.getLanguageId()));
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;
		}
	}
	
	// authorization 
	public void fetchAuthorizationDetails(){
		setOtpAuthorizedBy(null);
		setOtpAuthorizedPassword(null);
		setOtpRemarks(null);
		branchManagerApproval.clear();
		List<DebitAutendicationView> localAuthlist = iPersonalRemittanceService.getdebitAutendicationList();
		if(localAuthlist != null && localAuthlist.size() != 0){
			branchManagerApproval.addAll(localAuthlist);
		}
	}
	
	public void checkMobile() {

		List<Customer> matchMobile = new ArrayList<Customer>();
		matchMobile.clear();
		String customerIdentityInt = null;
		matchMobile.addAll(generalService.getMobileCheck(sessionStateManage.getCountryId(), getDataCustomerTelephoneNumber()));

		if (getCustomerNo() != null) {

			List<Customer> customerList = icustomerRegistrationService.getCustomerInfo(getCustomerNo());

			if (getCustomerNo().intValue() == customerList.get(0).getCustomerId().intValue()
					&& getDataCustomerTelephoneNumber().equalsIgnoreCase(customerList.get(0).getMobile())) {
				// same customer id and same mobile
			} else if (matchMobile != null && matchMobile.size() > 0) {
				// same mobile but different customer id
				log.info("Mobile No :" + matchMobile.get(0).getCustomerId());
				if (matchMobile.get(0).getCustomerId() != null) {
					customerIdentityInt = matchMobile.get(0).getCivilId();
				}
				setDataCustomerTelephoneNumber(getDataTempCustomerMobile());
				setExceptionMessage(WarningHandler.showWarningMessage("lbl.mobileisexist", sessionStateManage.getLanguageId()));
				setExceptionMessage(getExceptionMessage() + " " + customerIdentityInt);
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}else{
				// different mobile for same customer need to save in FS_CUSTOMER_MOBILE_LOG
			}

		} else if (matchMobile != null && matchMobile.size() > 0) {
			setDataCustomerTelephoneNumber(getDataTempCustomerMobile());
			setExceptionMessage(WarningHandler.showWarningMessage("lbl.mobileisexist", sessionStateManage.getLanguageId()));
			setExceptionMessage(getExceptionMessage() + " " + customerIdentityInt);
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;
		}else{
			// different mobile for new customer need to save in FS_CUSTOMER_MOBILE_LOG
		}

	}
	
	// save authorization
	public void saveAuthorization(){
		// Authorized Log Insert and Fs_Customer Update OTP Verified By and Verified Date
		HashMap<String, Object> checkAlreadyVerfied;
		try {

			checkAlreadyVerfied = icustomerRegistrationService.checkOTPVerify(getCustomerNo());

			BigDecimal otpNo = (BigDecimal) checkAlreadyVerfied.get("OTP_NO");

			if(otpNo != null && otpNo.compareTo(BigDecimal.ZERO) != 0)
			{
				AuthorizedLog authorizedLog = new AuthorizedLog();

				authorizedLog.setDocumentDate(new Date());
				authorizedLog.setAuthorizedBy(getOtpAuthorizedBy());

				if(getOtpRemarks() != null && !getOtpRemarks().equalsIgnoreCase("")){
					authorizedLog.setAmlRemarks(getOtpRemarks().concat(" - OTP : ").concat(otpNo.toPlainString()));
				}else{
					authorizedLog.setAmlRemarks("OTP : " +otpNo.toPlainString());
				}

				authorizedLog.setAuthorizedPassword(getOtpAuthorizedPassword());
				authorizedLog.setAuthorizedLogId(sessionStateManage.getEmployeeId());
				authorizedLog.setAuthorizedType("95");
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

				icustomerRegistrationService.saveAuthorizedLogForOTP(authorizedLog);

			}

		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;
		}

	}	//OTP implementation end

	// check authorization or pin number check
	public Boolean checkPinNumberBeforeSave(){

		try{

			if(getBooRenderOtpPanel() != null && getBooRenderOtpPanel())
			{
				// check authorized by correct
				if(getOtpNo() == null && getOtpAuthorizedBy() == null && (getOtpAuthorizedPassword() == null || getOtpAuthorizedPassword().equalsIgnoreCase("")))
				{
					
					setExceptionMessage(WarningHandler.showWarningMessage("lbl.otpAuthorize", sessionStateManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
					//RequestContext.getCurrentInstance().execute("otpAuthorize.show();");
					return false;
				}

				if(getOtpNo() != null){
					//boolean checkAlreadyVerfied = icustomerRegistrationService.checkOtpVerified(getPkCustomerId());
					String rtnMessage = icustomerRegistrationService.verifyOtpNo(getOtpNo(), getCustomerNo(), sessionStateManage.getUserName());

					if(rtnMessage!=null)
					{
						if(rtnMessage.equalsIgnoreCase("MATCH"))
						{
							// continue - still verification not done
						}else{
							if(getOtpAuthorizedBy() == null)
							{
								setExceptionMessage(WarningHandler.showWarningMessage("lbl.authorizedByEmpty", sessionStateManage.getLanguageId()));
								RequestContext.getCurrentInstance().execute("alertmsg.show();");
								//RequestContext.getCurrentInstance().execute("authorizedByEmpty.show();");
								return false;
							} else if(getOtpAuthorizedPassword() == null || getOtpAuthorizedPassword().equalsIgnoreCase(""))
							{
								setExceptionMessage(WarningHandler.showWarningMessage("lbl.authorizedPasswordEmpty", sessionStateManage.getLanguageId()));
								RequestContext.getCurrentInstance().execute("alertmsg.show();");
								//RequestContext.getCurrentInstance().execute("authorizedPasswordEmpty.show();");
								return false;
							}else if (getOtpAuthorizedBy() != null && getOtpAuthorizedPassword() != null) {
								List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();

								lstEmpLogin = 	iPersonalRemittanceService.getdebitAutendicationListByUserId(getOtpAuthorizedBy(),getOtpAuthorizedPassword());				

								if (lstEmpLogin.size() != 0) {
									// continue
								} else {
									setOtpAuthorizedPassword(null);
									setExceptionMessage(WarningHandler.showWarningMessage("lbl.sorrypassworddeosnotmatch", sessionStateManage.getLanguageId()));
									RequestContext.getCurrentInstance().execute("alertmsg.show();");
									//RequestContext.getCurrentInstance().execute("passwordcheck.show();");
									return false;
								}
							}
						}
					}else{
						setExceptionMessage(WarningHandler.showWarningMessage("lbl.otpAuthorize", sessionStateManage.getLanguageId()));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
						//RequestContext.getCurrentInstance().execute("otpAuthorize.show();");
						return false;
					}
				}else {
					if(getOtpAuthorizedBy() == null)
					{
						setExceptionMessage(WarningHandler.showWarningMessage("lbl.authorizedByEmpty", sessionStateManage.getLanguageId()));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
						//RequestContext.getCurrentInstance().execute("authorizedByEmpty.show();");
						return false;
					} else if(getOtpAuthorizedPassword() == null || getOtpAuthorizedPassword().equalsIgnoreCase(""))
					{
						setExceptionMessage(WarningHandler.showWarningMessage("lbl.authorizedPasswordEmpty", sessionStateManage.getLanguageId()));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
						//RequestContext.getCurrentInstance().execute("authorizedPasswordEmpty.show();");
						return false;
					}else if (getOtpAuthorizedBy() != null && getOtpAuthorizedPassword() != null) {
						List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();

						lstEmpLogin = 	iPersonalRemittanceService.getdebitAutendicationListByUserId(getOtpAuthorizedBy(),getOtpAuthorizedPassword());				

						if (lstEmpLogin.size() != 0) {
							// continue - verification done user name and password matching 
						} else {
							setOtpAuthorizedPassword(null);
							setExceptionMessage(WarningHandler.showWarningMessage("lbl.sorrypassworddeosnotmatch", sessionStateManage.getLanguageId()));
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
							//RequestContext.getCurrentInstance().execute("passwordcheck.show();");
							return false;
						}
					}
				}		
			}

			return true;
		}catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return false;
		}

	}
	
	// place order calling personalRemittance
	public String callPersonalRemitFromPlaceOrder(){
		String errorMsg = null;
		setExceptionMessage(null);
		setProcedureError(null);
		//nextServiceSecondPanel();
		getServiceFirstPanel();
		// Icash for cash Product
		fetchICashAgent();
		if(getExceptionMessage() != null){
			errorMsg = getExceptionMessage();
		}
		if(getProcedureError() != null){
			errorMsg = getProcedureError();
		}
		return errorMsg;
	}

    private String emailVerifiedStatus;
	
	
	public String getEmailVerifiedStatus() {
		return emailVerifiedStatus;
	}
	public void setEmailVerifiedStatus(String emailVerifiedStatus) {
		this.emailVerifiedStatus = emailVerifiedStatus;
	}
	private void checkEmailVerification()
	{
		
		List<AuthicationLimitCheckView> lsRremittancePaidAmount = iPersonalRemittanceService.parameterDeTails_AUTH_View(Constants.GO_GREEN_REMIT_AMT_LIMIT);
		BigDecimal remittanceLimitAmount=BigDecimal.ZERO;
		//String authOverLimtMsg=null;
		//String authOverLimitType=null;
		if(lsRremittancePaidAmount.size() != 0){
			AuthicationLimitCheckView authRemitAmtLimit = lsRremittancePaidAmount.get(0);
			//authOverLimtMsg = authRemitAmtLimit.getAuthMessage();
			remittanceLimitAmount = authRemitAmtLimit.getAuthLimit();
			//authOverLimitType = authRemitAmtLimit.getAuthorizationType();
			
		}
		
		if((remittanceLimitAmount.compareTo(BigDecimal.ZERO)!=0 ) && (getCalNetAmountPaid().compareTo(remittanceLimitAmount)==0 || getCalNetAmountPaid().compareTo(remittanceLimitAmount)==1))
		{
			
			setCustomerEmail(null);		
			List<String> customerList = icustomerRegistrationService.getCustomerEmailDetails(getCustomerNo());
			if(customerList!=null && customerList.size()>0)
			{
				String customerEmail=customerList.get(0);
				String customerEmailVerifiedOn=null;
				if(customerList.size()>1)
				{
					customerEmailVerifiedOn=customerList.get(1);
				}
				setCustomerEmail(customerEmail);
				if(customerEmailVerifiedOn==null)
				{
					setEmailVerifiedStatus("Email is not verified!");
					RequestContext.getCurrentInstance().execute("emailVerification.show();");
				}else
				{
					//saveRemittance();
					setEmailToSendReport(getCustomerEmail());
					setEmailVerifiedStatus("Email already verified!");
					nextDenaminationPanel();
				}
			}else
			{
				setEmailVerifiedStatus("Email is not verified!");
				RequestContext.getCurrentInstance().execute("emailVerification.show();");
			}
			
		}else
		{
			List<String> customerList = icustomerRegistrationService.getCustomerEmailDetails(getCustomerNo());
			if(customerList!=null && customerList.size()>0)
			{
				String customerEmail=customerList.get(0);
				String customerEmailVerifiedOn=null;
				setCustomerEmail(customerEmail);
				if(customerList.size()>1)
				{
					customerEmailVerifiedOn=customerList.get(1);
				}
				if(customerEmailVerifiedOn==null)
				{
					setEmailToSendReport(customerEmail);
					setEmailVerifiedStatus("Email is not verified!");
					
				}else
				{
					//saveRemittance();
					setEmailToSendReport(customerEmail);
					setEmailVerifiedStatus("Email already verified!");
					
				}
			}
			nextDenaminationPanel();
		}
	}

	private void nextDenaminationPanel()
	{
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
	}
	public void sendEmailidVerification()
	{
		try {
			if(getCustomerEmail()== null || getCustomerEmail().trim().equalsIgnoreCase(""))
			{
				nextDenaminationPanel();
				return;
			}
			boolean validateEmail=isValidEmailAddress(getCustomerEmail());
			if(!validateEmail)
			{
				RequestContext.getCurrentInstance().execute("emailPopUp.show();");
				return;
			}
			
			setEmailToSendReport(getCustomerEmail());
			icustomerRegistrationService.updateCustomerEmailAndVerifiedOn(getCustomerNo(), getCustomerEmail());
			
			sendEmailForVerification(getCustomerNo(), getCustomerFullName());

			nextDenaminationPanel();

		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}


	}
	
	public void sendEmailForVerification(BigDecimal customerIdPk,String customerName)
	{
		String subject = "Activation Email";
		
		try {
			List<ApplicationSetup> lstApplicationSetup = iPersonalRemittanceService.getEmailFromAppSetup(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId());
			if(getCustomerEmail()!=null && !getCustomerEmail().equals("")){
				mailService.sendCustomerEmailVerification(lstApplicationSetup, getCustomerEmail(), subject, customerName,customerIdPk);
			}

		} catch (AMGException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		
	}
	
	public void verifyEmail()
	{
		try {
			List<String> customerList = icustomerRegistrationService.getCustomerEmailDetails(getCustomerNo());
			

			if(getEmailToSendReport()!=null && !getEmailToSendReport().equalsIgnoreCase(""))
			{
				if(customerList!=null && customerList.size()>0)
				{
					String customerEmail=customerList.get(0);
					String customerEmailVerifiedOn=null;
					if(customerList.size()>1)
					{
						customerEmailVerifiedOn=customerList.get(1);
					}
					if((customerEmail==null || !customerEmail.equalsIgnoreCase(getEmailToSendReport())))
					{
						//Update customer table ,email verified on to null
						icustomerRegistrationService.updateCustomerEmailAndVerifiedOn(getCustomerNo(),getEmailToSendReport());
						setCustomerEmail(getEmailToSendReport());
						//Send verification mail to new email
						sendEmailForVerification(getCustomerNo(), getCustomerFullName());

						setEmailVerifiedStatus("Email is not verified!");
						setExceptionMessage(WarningHandler.showWarningMessage("lbl.emailcustomer", sessionStateManage.getLanguageId()));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");

					}else if(customerEmail.equalsIgnoreCase(getEmailToSendReport()) && customerEmailVerifiedOn== null)
					{
						sendEmailForVerification(getCustomerNo(), getCustomerFullName());
						
						setEmailVerifiedStatus("Email is not verified!");
						setExceptionMessage(WarningHandler.showWarningMessage("lbl.emailcustomer", sessionStateManage.getLanguageId()));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}else if((customerEmail.equalsIgnoreCase(getEmailToSendReport()) && customerEmailVerifiedOn!= null))
					{
						setEmailVerifiedStatus("Email already verified!");
						RequestContext.getCurrentInstance().execute("alreadyEmailVerified.show();");
					}

				}else
				{
					icustomerRegistrationService.updateCustomerEmailAndVerifiedOn(getCustomerNo(),getEmailToSendReport());
					setCustomerEmail(getEmailToSendReport());
					//Send verification mail to new email
					sendEmailForVerification(getCustomerNo(), getCustomerFullName());
					
					setEmailVerifiedStatus("Email is not verified!");
					setExceptionMessage(WarningHandler.showWarningMessage("lbl.emailcustomer", sessionStateManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}else
			{
				RequestContext.getCurrentInstance().execute("emptyEmailCheck.show();");
			}
			
		}catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

	}
	public void checkEmailPopup()
	{
		RequestContext.getCurrentInstance().execute("emailVerification.show();");
	}
	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
	/** 
	 * Added by Rabil
	 */
	public BigDecimal getAmtbCouponValueFromView(){
		BigDecimal amtbCoAmt = BigDecimal.ZERO;
		setAmtbCouponAmount(amtbCoAmt);
		ViewAmtbCoupon vw = iPersonalRemittanceService.getAmtbCouponAmountFromView(getAmtbCoupon(), idNumber);
		if(vw!=null){
			amtbCoAmt = vw.getCouponAmount();
			setAmtbCouponAmount(GetRound.roundBigDecimal(amtbCoAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setAmtbCouponFinancialYear(vw.getAmtbFinYear());

		}
		return amtbCoAmt;
	}
	
	public BigDecimal getAmtbCoupon() {
		return amtbCoupon;
	}
	public void setAmtbCoupon(BigDecimal amtbCoupon) {
		this.amtbCoupon = amtbCoupon;
	}
	public Boolean getAmtbCouponRender() {
		return amtbCouponRender;
	}
	public void setAmtbCouponRender(Boolean amtbCouponRender) {
		this.amtbCouponRender = amtbCouponRender;
	}
	public BigDecimal getAmtbCouponAmount() {
		return amtbCouponAmount;
	}
	public void setAmtbCouponAmount(BigDecimal amtbCouponAmount) {
		this.amtbCouponAmount = amtbCouponAmount;
	}
	public BigDecimal getAmtbCouponFinancialYear() {
		return amtbCouponFinancialYear;
	}
	public void setAmtbCouponFinancialYear(BigDecimal amtbCouponFinancialYear) {
		this.amtbCouponFinancialYear = amtbCouponFinancialYear;
	}
	public List<ViewAmtbCoupon> getLstVwAmtbCoupon() {
		return lstVwAmtbCoupon;
	}
	public void setLstVwAmtbCoupon(List<ViewAmtbCoupon> lstVwAmtbCoupon) {
		this.lstVwAmtbCoupon = lstVwAmtbCoupon;
	}
	private Boolean checkRecieptEmail;


	public Boolean getCheckRecieptEmail() {
		return checkRecieptEmail;
	}

	public void setCheckRecieptEmail(Boolean checkRecieptEmail) {
		this.checkRecieptEmail = checkRecieptEmail;
	}
	
	// check for KNET  - OTHERS 10 not allowed
	public boolean checkKNETBanks(){
		boolean checkStatus = false;
		if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)){
			if(getColBankCode() != null && getColBankCode().equalsIgnoreCase("10")){
				checkStatus = true;
			}
		}
		return checkStatus;
	}
	
}