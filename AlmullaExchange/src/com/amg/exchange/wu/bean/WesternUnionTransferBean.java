package com.amg.exchange.wu.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.amg.exchange.beneficiary.bean.BeneficiaryCreationBean;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.bean.ForeignLocalCurrencyDataTable;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustWU;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.bean.CustomerPersonalInfoBean;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.bean.BeneficiaryEditBean;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.bean.PersonalRemmitanceBeneficaryDataTable;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.bean.ShoppingCartDataTableBean;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BeneficaryStatus;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ServiceGroupMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.IBeneCountryServices;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.DateUtil;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;
import com.amg.exchange.wu.model.WesternUnionTransfer;
import com.amg.exchange.wu.service.IWesternUnionService;

/*
 * Author: Viswa
 * Module: Western Union Money Transfer
 * 
 */
@SuppressWarnings({ "unused" })
@Component("wuBean")
@Scope("session")
public class WesternUnionTransferBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(WesternUnionTransferBean.class);

	/*
	 * Autowired Services
	 */
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	@Autowired
	IBeneficaryCreation beneficaryCreation;

	@Autowired
	ServiceCodeMasterService serviceMasterService;

	@Autowired
	IWesternUnionService westernUnionService;

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	@Autowired
	IPaymentService ipaymentService;

	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	@Autowired
	ISourceOfIncomeService sourceofIncomeservice;

	@Autowired
	IBankMasterService bankMasterService;

	@Autowired
	IBankBranchDetailsService bankBranchDetailsService;

	@Autowired
	IServiceGroupMasterService serviceGroupMasterService;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	BeneficiaryEditBean beneficiaryEditBean;
	
	@Autowired
	IBeneCountryServices<T> ibeneCountryServices;

	/*
	 * Property declaration
	 */
	private BigDecimal selectCard;
	private int selectCardType;
	private String idNumber;
	private BigDecimal cardType;
	private int txnType;

	// Customer Details variables
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
	private String nationalityName;
	private Date dateOfBrith;
	private String countryCode;
	private String mcountryCode;
	private String occupation;
	private String customerEmail;

	private BigDecimal sendDocumentNo;
	private BigDecimal receiveDocumentNo;
	private BigDecimal sendDocumentCode;
	private BigDecimal sendCompanyCode;
	private BigDecimal sendDocumentFinanceYear;
	private BigDecimal receiveDocumentCode;
	private BigDecimal receiveCompanyCode;
	private BigDecimal receiveDocumentFinanceYear;
	private BigDecimal mtcYear;
	private String mtcNo;

	// private String sourceOfIncomes;
	private String sourceOfIncome;
	private String purposeOfTransactions;
	private List<SourceOfIncomeDescription> lstSourceOfIncomes;
	private List<PurposeOfTransaction> lstPurposeOfTransactions;

	/*
	 * boolean properties for rendering
	 */
	private boolean booRenderBenificaryFirstPanel;
	private boolean mainPanelRender;
	private boolean booRenderOldSmartCardPanel;
	private boolean booRenderOverseaCharges = false;
	private boolean booRenderLayaltyServicePanel = false;
	private boolean booRenderBenificarySearchPanel = false;
	private boolean booRenderBeniListPanel;
	private boolean renderBackButton;
	private boolean booRendercashdenomination = false;
	private boolean boorefundPaymentDetails;
	private boolean booRendercollectiondatatable;
	private boolean booRenderSendReceivePanel;
	private boolean booSaveRender;
	private boolean booSaveDenomRender;
	private boolean booRenderPaymentDetails;
	private boolean booSaveAll;
	private boolean booTxnType;
	private boolean booCheckStatusPanel;
	private boolean booSourceEnable;
	private boolean booPurposeEnable;
	private boolean booMtcEnable;
	private boolean booDenoEnable = false;

	// Map properties
	private Map<BigDecimal, String> mapcomIdentityType = new HashMap<BigDecimal, String>();

	// List Properties
	private List<WesternUnionBeneficaryDataTable> coustomerBeneficaryDTList = new ArrayList<WesternUnionBeneficaryDataTable>();
	private List<CountryMasterDesc> lstCountry = new ArrayList<CountryMasterDesc>();
	private List<CountryMasterDesc> nationalityList = new ArrayList<CountryMasterDesc>();
	private List<CustomerIdProof> customerDetailsList = new ArrayList<CustomerIdProof>();
	private List<BeneficaryStatus> benificaryStatusList = new ArrayList<BeneficaryStatus>();
	private List<BeneficaryStatus> benificaryStatusName = new ArrayList<BeneficaryStatus>();
	private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues = new CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable>();
	private CopyOnWriteArrayList<ShoppingCartDataTableBean> lstselectedrecord = new CopyOnWriteArrayList<ShoppingCartDataTableBean>();
	private List<PaymentModeDesc> lstFetchAllPayMode = new ArrayList<PaymentModeDesc>();
	private List<ShoppingCartDataTableBean> shoppingcartDTList = new ArrayList<ShoppingCartDataTableBean>();

	List<WesternUnionBeneficaryDataTable> selectedWUBeneList = new ArrayList<WesternUnionBeneficaryDataTable>();
	ForeignLocalCurrencyDataTable dataTableClear = new ForeignLocalCurrencyDataTable();

	private List<PurposeOfTransaction> lstPurposeOfTransaction = new ArrayList<>();
	private List<SourceOfIncome> lstSourceOfIncome = new ArrayList<>();

	// Others
	private String exceptionMessage;
	private BigDecimal beneficaryStatusId;
	private String denominationchecking;
	private BigDecimal denomtotalCash;
	private BigDecimal refundAmount;
	private BigDecimal collectedRefundAmount;
	private BigDecimal payRefund;
	private BigDecimal chequeValue;
	private BigDecimal knetValue;
	private BigDecimal cashAmount = new BigDecimal(0);
	private BigDecimal collectedAmount;
	private BigDecimal toalUsedAmount = new BigDecimal(0);
	private BigDecimal remittanceNo;
	private BigDecimal colamountKWD = new BigDecimal(0);
	private BigDecimal denomtotalCashreceived;
	private BigDecimal tempCash;
	private BigDecimal calNetAmountPaid;
	private BigDecimal beneSequenceId;
	private String location;

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
	private boolean booRenderColDebitCard = false;
	private BigDecimal fcsaleNo;

	private BigDecimal populatedDebitCardNumber;
	private boolean booRenderColCheque = false;
	private String colchequebankCode;
	private String colChequeRef;
	private Date colChequeDate;
	private Date effectiveMinDate = new Date();
	private String colChequeApprovalNo;

	private String documentNo;
	private BigDecimal financeYear;
	private BigDecimal payPaidAmount;
	private BigDecimal payNetPaidAmount;
	private BigDecimal totalbalanceAmount = new BigDecimal(0);
	private BigDecimal totalrefundAmount = new BigDecimal(0);

	private String nextOrSaveButton;
	private String errmsg;
	private BigDecimal companyId;
	private BigDecimal documentId;
	private String procedureError;

	private boolean isWUSend;
	private boolean isWUStatus;
	private boolean status;
	private WesternUnionBeneficaryDataTable selectedValues;

	private BigDecimal collectionId;
	private BigDecimal collectionDocumentNo;
	private BigDecimal relationId;

	private String errorMessage;
	private String birthdate;
	private BigDecimal wucheckRecCustomer;

	public WesternUnionTransferBean() {
		hideAllPanels();
		clear();
	}

	/*
	 * Western Union Page Navigation
	 */
	public void wuNavigation() {
		try {
			hideAllPanels();
			clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Show Card type
	public void showCardTypePanel() throws Exception {
		int typecard = getSelectCardType();
		if (typecard == 2) {
			setBooRenderBenificaryFirstPanel(true);
			setBooRenderOldSmartCardPanel(true);
			BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId())
					.getFsBizComponentData().getComponentDataId();
			if (idtypeCivilIdnew != null) {
				setSelectCard(idtypeCivilIdnew);
			}
		} else if (typecard == 1) {
			fetchSmartCardIdNumber();
			if (getIdNumber() != null && getSelectCard() != null) {
				goFromOldSmartCardpanel();
				setBooRenderBenificaryFirstPanel(false);
				setBooRenderOldSmartCardPanel(false);
			} else {
				setSelectCardType(0);
				// setBooRenderOldSmartCardPanel(false);
				RequestContext.getCurrentInstance().execute("dldInserCard.show();");
			}
		}
	}

	/*
	 * Transaction Type (Send/Receive)
	 */
	public void changeTxnType() {
		setErrorMessage(null);
		if (getTxnType() == 1) {
			setBooTxnType(true);
			setBooSourceEnable(true);
			setBooPurposeEnable(true);
			setBooMtcEnable(false);
			setSourceOfIncome(null);
			setPurposeOfTransactions(null);
		}
		if (getTxnType() == 2) {
			setBooTxnType(false);
			setBooSourceEnable(false);
			setBooPurposeEnable(true);
			setBooMtcEnable(true);
			setSourceOfIncome(null);
			setPurposeOfTransactions(null);
			List<UserFinancialYear> finYearList = generalService.getDealYear(new Date());
			if(finYearList!=null){
				setMtcYear(finYearList.get(0).getFinancialYear());
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

	// Display smart card
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
					setSelectCard(generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData()
							.getComponentDataId());
				}
			}
		} else {
			setIdNumber(null);
			setSelectCard(null);
			RequestContext.getCurrentInstance().execute("dldInserCard.show();");
		}
		return sb.toString();
	}

	public void goFromOldSmartCardpanel() throws ParseException {
		setErrorMessage(null);
		log.info("Entering into goFromOldSmartCardpanel method");

		if (getIdNumber() != null && !getIdNumber().equalsIgnoreCase("")) {

			if (coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()) {
				coustomerBeneficaryDTList.clear();
			}

			if (getSelectCard() != null) {

				//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber().toUpperCase(), getSelectCard());
				customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber().toUpperCase(), getSelectCard());

				if (customerDetailsList.size() != 0) {
					CustomerIdProof customerDetails = customerDetailsList.get(0);
					fetchCustomerBeneficiaryDetails(customerDetails);
				} else {
					// comparing with civil id
					BigDecimal identityTpeIds = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId())
							.getFsBizComponentData().getComponentDataId();

					if (getSelectCard().compareTo(identityTpeIds) != 0) {
						//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), identityTpeIds);
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), identityTpeIds);
						if (customerDetailsList.size() != 0) {
							CustomerIdProof customerDetails = customerDetailsList.get(0);
							fetchCustomerBeneficiaryDetails(customerDetails);
						} else {
							// comparing with civil id new
							BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
									.getFsBizComponentData().getComponentDataId();
							//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), idtypeCivilIdnew);
							customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idtypeCivilIdnew);
							if (customerDetailsList.size() != 0) {
								CustomerIdProof customerDetails = customerDetailsList.get(0);
								fetchCustomerBeneficiaryDetails(customerDetails);
							} else {
								// failed all conditions
								setCardType(null);
								setIdNumber(null);
								setBooRenderBenificaryFirstPanel(true);
								RequestContext.getCurrentInstance().execute("idNotFound.show();");
							}
						}
					} else {
						// comparing with civil id new
						BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId())
								.getFsBizComponentData().getComponentDataId();
						//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), idtypeCivilIdnew);
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idtypeCivilIdnew);
						if (customerDetailsList.size() != 0) {
							CustomerIdProof customerDetails = customerDetailsList.get(0);
							fetchCustomerBeneficiaryDetails(customerDetails);
						} else {
							// failed all conditions
							setCardType(null);
							setIdNumber(null);
							setBooRenderBenificaryFirstPanel(true);
							RequestContext.getCurrentInstance().execute("idNotFound.show();");
						}

					}
				}

			}

		} else {
			RequestContext.getCurrentInstance().execute("idNumbernotenter.show();");
		}

		log.info("Exit into goFromOldSmartCardpanel method");
	}

	// calling remit amount page
	public void fetchCustomerBeneficiaryDetails(CustomerIdProof fetchedcustomerDetails) {

		try {

			// Added by Rabil.
			String userType = null;
			if ((sessionStateManage.getBranchId() != null || sessionStateManage.getCustomerType().equals("E"))
					&& sessionStateManage.getRoleId().equals("1")) {
				userType = "BRANCH";
			} else if (sessionStateManage.getBranchId() != null && sessionStateManage.getUserType().equalsIgnoreCase("K")) {
				userType = "KIOSK";
			} else {
				// userType ="ONLINE";
			}

			CustomerIdProof customerDetails = fetchedcustomerDetails;

			if (customerDetails.getFsCustomer() != null) {
				setCustomerNo(customerDetails.getFsCustomer().getCustomerId());
			}
			if (customerDetails.getIdentityExpiryDate() != null) {
				setCustomerExpDate(customerDetails.getIdentityExpiryDate());
			}
			if (getCustomerExpDate() != null) {
				setCustomerExpireDateMsg(new SimpleDateFormat("dd/MM/yyyy").format(getCustomerExpDate()));
			}

			log.info("getCustomerNo() :" + getCustomerNo() + "\t sessionStateManage.getCountryId():" + sessionStateManage.getCountryId()
					+ "\t sessionStateManage.getUserName() :" + sessionStateManage.getUserName());
			HashMap<String, String> customerValiMessage = iPersonalRemittanceService.getValidateCustomerProcedure(sessionStateManage.getCountryId(),
					getCustomerNo(), sessionStateManage.getUserName(), userType);
			log.info("customerValiMessage :" + customerValiMessage);
			log.info("INDICATOR====" + customerValiMessage.get("INDICATOR"));

			//customerValiMessage.put("ERROR_MESSAGE", null);
			if (customerValiMessage.get("ERROR_MESSAGE") != null) {
				setExceptionMessage(customerValiMessage.get("ERROR_MESSAGE"));
				RequestContext.getCurrentInstance().execute("expiredCustomer.show();");
				return;
			} else if (customerValiMessage != null && customerValiMessage.get("INDICATOR") != null) {
				setExceptionMessage(customerValiMessage.get("ERROR_MESSAGE"));
				setCardType(null);
				if (customerValiMessage.get("INDICATOR").equalsIgnoreCase(Constants.Yes)) {
					RequestContext.getCurrentInstance().execute("customerregproceed.show();");
				}
			} else {
				setBooSourceEnable(false);
				setBooPurposeEnable(false);

				if (allBeneCountryList != null || !allBeneCountryList.isEmpty()) {
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
							lstCountry = generalService.getCountryList(sessionStateManage.getLanguageId());
							nationalityList = generalService.getNationalityList(sessionStateManage.getLanguageId());

							// ValidateCustomerProcedure
							if (getCustomerCrNumber() != null && !getCustomerCrNumber().equalsIgnoreCase("")) {
								setBooRenderOverseaCharges(true);
								backFromBenificaryStatusPanel();
							} else {
								setBooRenderOverseaCharges(false);
								backFromBenificaryStatusPanel();
							}
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
								lstCountry = generalService.getCountryList(sessionStateManage.getLanguageId());
								nationalityList = generalService.getNationalityList(sessionStateManage.getLanguageId());

								// ValidateCustomerProcedure
								if (getCustomerCrNumber() != null && !getCustomerCrNumber().equalsIgnoreCase("")) {
									setBooRenderOverseaCharges(true);
									backFromBenificaryStatusPanel();
								} else {
									setBooRenderOverseaCharges(false);
									backFromBenificaryStatusPanel();
								}
							}
						}else{
							lstCountry = generalService.getCountryList(sessionStateManage.getLanguageId());
							nationalityList = generalService.getNationalityList(sessionStateManage.getLanguageId());

							// ValidateCustomerProcedure
							if (getCustomerCrNumber() != null && !getCustomerCrNumber().equalsIgnoreCase("")) {
								setBooRenderOverseaCharges(true);
								backFromBenificaryStatusPanel();
							} else {
								setBooRenderOverseaCharges(false);
								backFromBenificaryStatusPanel();
							}
						}
					}

				} else {
					setCardType(null);
					setBooRenderBenificaryFirstPanel(true);
					RequestContext.getCurrentInstance().execute("activatecustomer.show();");
					return;
				}
			}

		} catch (Exception e) {
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}

	}

	// Second Method to Populate Records which is Approved all condition
	public void populateCustomerDetailsFromBeneRelation() {
		log.info("Entering into populateCustomerDetailsFromBeneRelation method ");

		// reset the dataTable
		resetFilters("form1:dataTable");

		setBooDenoEnable(true);
		try {

			if (coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()) {
				coustomerBeneficaryDTList.clear();
			}

			List<BenificiaryListView> isCoustomerExist = iPersonalRemittanceService.getBeneficiaryCountryList(getCustomerNo(),
					getBeneficiaryCountryId());

			WesternUnionBeneficaryDataTable personalRBDataTable = null;
			if (isCoustomerExist.size() > 0) {

				List<BenificiaryListView> duplicateCheck = new ArrayList<BenificiaryListView>();

				for (BenificiaryListView rel : isCoustomerExist) {

					if (duplicateCheck.isEmpty()) {
						duplicateCheck.add(rel);
						addBeneficiaryViewList(rel);
					} else {
						Boolean checkavailable = false;
						for (BenificiaryListView dupl : duplicateCheck) {
							if (dupl.getBeneficaryMasterSeqId().compareTo(rel.getBeneficaryMasterSeqId()) == 0) {
								if (dupl.getCurrencyId().compareTo(rel.getCurrencyId()) == 0) {
									checkavailable = true;
									break;
								} else {
									checkavailable = false;
								}
							} else {
								checkavailable = false;
							}
						}

						if (!checkavailable) {
							duplicateCheck.add(rel);
							addBeneficiaryViewList(rel);
						}

					}

				}
			}

		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		log.info("Exit into populateCustomerDetailsFromBeneRelation method ");
	}

	public void addBeneficiaryViewList(BenificiaryListView rel) {

		WesternUnionBeneficaryDataTable personalRBDataTable = new WesternUnionBeneficaryDataTable();

		personalRBDataTable.setApplicationCountryId(rel.getApplicationCountryId());
		personalRBDataTable.setBenificaryName(rel.getBenificaryName());
		personalRBDataTable.setArbenificaryName(rel.getArbenificaryName());
		personalRBDataTable.setBeneficaryMasterSeqId(rel.getBeneficaryMasterSeqId());
		personalRBDataTable.setBeneficiaryAccountSeqId(rel.getBeneficiaryAccountSeqId());
		personalRBDataTable.setBenificaryCountry(rel.getCountryId());
		personalRBDataTable.setBenificaryCountryName(rel.getCountryName());
		personalRBDataTable.setCountryId(rel.getBenificaryCountry());
		personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName());
		personalRBDataTable.setBenificaryStatusId(rel.getBenificaryStatusId());
		personalRBDataTable.setBenificaryStatusName(rel.getBenificaryStatusName());
		personalRBDataTable.setCityName(rel.getCityName());
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
		personalRBDataTable.setBeneficiaryRelationShipSeqId(rel.getBeneficiaryRelationShipSeqId());
		personalRBDataTable.setRemarks(rel.getRemarks());
		personalRBDataTable.setSecondNameLocal(rel.getSecondNameLocal());
		personalRBDataTable.setSecondName(rel.getSecondName());
		personalRBDataTable.setStateName(rel.getStateName());
		personalRBDataTable.setThirdName(rel.getThirdName());
		personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
		personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());
		personalRBDataTable.setCountryId(rel.getBenificaryCountry());
		personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName());
		personalRBDataTable.setBankCode(rel.getBankCode());
		personalRBDataTable.setBankId(rel.getBankId());
		personalRBDataTable.setBankName(rel.getBankName());
		personalRBDataTable.setBankBranchName(rel.getBankBranchName());
		personalRBDataTable.setBranchCode(rel.getBranchCode());
		personalRBDataTable.setBranchId(rel.getBranchId());
		personalRBDataTable.setIdNo(rel.getIdNo());

		coustomerBeneficaryDTList.add(personalRBDataTable);
	}

	// Fetch Customer Details
	public void getCustomerDetails() {

		clearCustomerDetails();

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
			setCustomerLocalFullName(nullCheck(customerDetails.getFsCustomer().getFirstNameLocal()) + " "
					+ nullCheck(customerDetails.getFsCustomer().getMiddleNameLocal()) + " "
					+ nullCheck(customerDetails.getFsCustomer().getLastNameLocal()));
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

			String nationality = null;
			nationality = generalService.getNationalityName(sessionStateManage.getLanguageId(), customerDetails.getFsCustomer()
					.getFsCountryMasterByNationality().getCountryId());

			if (nationality != null) {
				setNationalityName(nationality);
			}

			setDateOfBrith(customerDetails.getFsCustomer().getDateOfBirth());
			String teleCountryId = generalService.getTelephoneCountryBasedOnNationality(customerDetails.getFsCustomer()
					.getFsCountryMasterByNationality().getCountryId());
			setCountryCode(teleCountryId);
			setMcountryCode(teleCountryId);
			BigDecimal occupationID = generalService.getOccupationId(customerDetails.getFsCustomer().getCustomerId());
			if (occupationID != null) {
				String occupation = generalService.getOccupationDesc(occupationID, sessionStateManage.getLanguageId());
				if (occupation != null) {
					setOccupation(occupation);
				} else {
					setOccupation("UN-EMPLOYEE");
				}
			} else {
				setOccupation("UN-EMPLOYEE");
			}
		}
		log.info("Exit into getCustomerDetails method ");
	}

	// clear All Customer Details
	public void clearCustomerDetails() {
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

	@PostConstruct
	public void loadIdType() {
		mapcomIdentityType = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), "I",
				"Identity Type");
	}

	public void exitWU() {
		hideAllPanels();
		clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Click next button from customer details
	public void nextToBeneDetailsList() throws AMGException {
		//txnType 1. Send 2. Receive
		//if transaction type is send source of found and purpose  mandatory
		//if transaction type is Receive  purpose  mandatory
		if(getTxnType()!=0){
			if(getTxnType()==1){
				if(getSourceOfIncome()!=null && !getSourceOfIncome().isEmpty() && getPurposeOfTransactions()!=null && !getPurposeOfTransactions().isEmpty()){
					setErrorMessage(null);
				}else{
					setErrorMessage(WarningHandler.showWarningMessage("lbl.SourcePurposeMandatory", sessionStateManage.getLanguageId()));
					return;
				}
			}else{
				if(getPurposeOfTransactions()!=null && !getPurposeOfTransactions().isEmpty()  && getMtcNo()!=null && !getMtcNo().isEmpty()){
					setErrorMessage(null);
				}else{
					setErrorMessage(WarningHandler.showWarningMessage("lbl.purposeMycNoMandatory", sessionStateManage.getLanguageId()));
					return;
				}
			}
		}else{
			setErrorMessage(WarningHandler.showWarningMessage("lbl.plsSelectSendReceive", sessionStateManage.getLanguageId()));
			return;
		}
		// reset the dataTable
		resetFilters("form1:dataTable");

		// clearing token number , doc code , doc no , company code
		setReceiveCompanyCode(null);
		setReceiveDocumentCode(null);
		setReceiveDocumentFinanceYear(null);
		setReceiveDocumentNo(null);

		setSendCompanyCode(null);
		setSendDocumentFinanceYear(null);
		setSendDocumentCode(null);
		setSendDocumentNo(null);

		setWucheckRecCustomer(null);

		if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
			selectedWUBeneList.clear();
		}

		// checking condition of exception list
		try {
			List<WesternUnionTransfer> lstWesternUnionTransfer = westernUnionService.getWUTransactionWithOutDenomination(sessionStateManage.getUserName(), sessionStateManage.getCountryBranchCode().toString(), getCustomerrefno());

			if (lstWesternUnionTransfer != null && lstWesternUnionTransfer.size() > 0) {
				// need to western union denomination exception list form
				// FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wudenominationexceptionlist.xhtml");

				if (lstWesternUnionTransfer != null && lstWesternUnionTransfer.size() != 0) {

					WesternUnionTransfer westernUnionTransfer = lstWesternUnionTransfer.get(0);

					if (westernUnionTransfer != null) {
						List<Customer> lstRec = generalService.getCustomerDeatilsBasedOnRef(westernUnionTransfer.getCustomerReference());
						if (lstRec != null && !lstRec.isEmpty()) {
							setWucheckRecCustomer(lstRec.get(0).getCustomerId());
						}

						if (westernUnionTransfer.getInorOut() != null && westernUnionTransfer.getInorOut().equalsIgnoreCase(Constants.WU_INOUT_SEND)) {
							// Send - 1
							setTxnType(1);
							// sender details
							setSendCompanyCode(westernUnionTransfer.getCompanyCode());
							setSendDocumentFinanceYear(westernUnionTransfer.getDocumentFinanceYear());
							setSendDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
							setSendDocumentNo(westernUnionTransfer.getDocumentNo());
						}

						if (westernUnionTransfer.getInorOut() != null && westernUnionTransfer.getInorOut().equalsIgnoreCase(Constants.WU_INOUT_RECEIVE)) {
							// Receive - 2
							setTxnType(2);
							// receiver details
							setReceiveCompanyCode(westernUnionTransfer.getCompanyCode());
							setReceiveDocumentFinanceYear(westernUnionTransfer.getDocumentFinanceYear());
							setReceiveDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
							setReceiveDocumentNo(westernUnionTransfer.getDocumentNo());
						}

					}else{
						setErrmsg("Records Empty");
						RequestContext.getCurrentInstance().execute("csp.show();");
						return;
					}

				}

				try {
					checkWUStatus();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				setWucheckRecCustomer(null);

				hideAllPanels();

				if (getTxnType() == 1) {

					setBooRenderBenificaryFirstPanel(false);
					setBooRenderBeniListPanel(true);
					setIsWUSend(true);
					setStatus(true);
					setBooCheckStatusPanel(true);
					// setBooRenderSendReceivePanel(false);
					// populateCustomerDetailsFromBeneRelation();
					setBeneficiaryCountryId(null);
					populateBeneFiciaryCountry();

				} else if (getTxnType() == 2) {

					setBooRenderBenificaryFirstPanel(false);
					setBooRenderBenificarySearchPanel(true);

					// added financial year
					//List<WesternUnionTransfer> wuDataList = westernUnionService.getWUDataByMtcNo(getMtcNo());  
					if(getFinanceYear() != null){
						List<WesternUnionTransfer> wuDataList = westernUnionService.getWUDataByMtcNo(getFinanceYear(),getMtcNo());
					}else{
						List<WesternUnionTransfer> wuDataList = westernUnionService.getWUDataByMtcNo(getFinaceYear(),getMtcNo());
					}
					
					String mtcno = null;
					String purposeCode = null;
					System.out.println("mtc no =" + getMtcYear() + "/" + getMtcNo());
					System.out.println("mtc no =" + getMtcNo());
					BigDecimal mtcyear = BigDecimal.ZERO;
					/*
					 * if (wuDataList.size() > 0) { mtcno =
					 * wuDataList.get(0).getMtcNo(); mtcyear =
					 * wuDataList.get(0).getMtcYear(); purposeCode =
					 * wuDataList.get(0).getPurposeId(); }
					 */
					if (getMtcYear() == null || getMtcNo().length() <= 0) {
						RequestContext.getCurrentInstance().execute("requiredmtcno.show();");
						return;
					}/*
					 * else {
					 * 
					 * if (!getMtcNo().equals(mtcno)) {
					 * RequestContext.getCurrentInstance
					 * ().execute("invalidmtcno.show();"); return; }else { try {
					 * saveWUReceive(); } catch (IOException e) {
					 * e.printStackTrace(); } catch (InterruptedException e) {
					 * e.printStackTrace(); } } }
					 */
					try {
						saveWUReceive();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}

	}

	public void backFromBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(true);
		setBooRenderBeniListPanel(false);
		setBooRendercashdenomination(false);
		setBooMtcEnable(false);
	}

	// Hide All panels
	public void hideAllPanels() {
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBeniListPanel(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);
		setBooRenderSendReceivePanel(false);
		// setBooSaveRender(false);
		setBooSaveDenomRender(false);
		setBooRenderPaymentDetails(false);
		setBooSaveAll(false);
		setBooCheckStatusPanel(false);
	}

	// back to mainpage
	public void clearHideAll() {
		clear();
		hideAllPanels();
	}

	@Autowired
	ApplicationContext context;

	// Call beneficiary creation from western union
	public void gotToNewBenificaryCreation() {

		try {
			BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean) context.getBean("beneficiaryCreationBean");
			// clearCustomerDetails();
			beneficiaryStatusList();
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBeneficaryStatusId(null);
			setRenderBackButton(true);
			// getCustomerDetails();

			objectBene.setBooenableAgentPanel(false);
			objectBene.setBooEnableBankChannelPanel(false);

			objectBene.setIdNumber(getIdNumber());
			objectBene.setSelectCard(getSelectCard());
			objectBene.setCustomerNo(getCustomerNo());
			objectBene.setCustomerrefno(getCustomerrefno());
			objectBene.setCustomerFullName(getCustomerFullName());
			objectBene.setBooBenefiPersonalRemit(true);
			//objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);

			objectBene.renderWesternUnionNavigation();

			FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiaryCreation.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void beneficiaryStatusList() {
		benificaryStatusList.clear();
		benificaryStatusName.clear();
		List<BeneficaryStatus> beneStatus = iPersonalRemittanceService.getBeneficaryStatusList();
		List<CountryBranch> lstToCountryBranch = generalService.getBranchDetailsFromBeneStatus(sessionStateManage.getCountryId(), new BigDecimal(
				sessionStateManage.getBranchId()));
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

	// Render payment details
	public void nextpaneltoPaymentDetails() {

		System.out.println("getToalUsedAmount()" + getToalUsedAmount());
		System.out.println("getCalNetAmountPaid()" + getCalNetAmountPaid());

		if(getToalUsedAmount() != null && getCalNetAmountPaid() != null){
			if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) >= 0) {

			} else {
				RequestContext.getCurrentInstance().execute("amountmatch.show();");
				return;
			}
		}else {
			setErrmsg("Amount Not Avaiable");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}



		if (getTempCash() != null) {
			setCashAmount(GetRound.roundBigDecimal(getTempCash(),
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
		}
		renderingDenominationDT();
		/* comment by MRU  on 17/05/2016*/
		// denaMinLstData();
		//refundDenominationData();
		/* comment by MRU */
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBeniListPanel(false);
		//setBooRendercashdenomination(true);
		// setBoorefundPaymentDetails(false);
		// setBooSaveRender(false);
		setBooSaveDenomRender(false);
		setBooRenderPaymentDetails(false);
		/*if (getTxnType() == 1) {
			setBoorefundPaymentDetails(false);
			//denaMinLstData();
		} else if (getTxnType() == 2) {
			setBoorefundPaymentDetails(true);
		}
		 */

		if(lstData != null || !lstData.isEmpty()){
			lstData.clear();
		}

		if(lstRefundData != null || !lstRefundData.isEmpty()){
			lstRefundData.clear();
		}


		if(getTxnType() == 1){
			setBooRendercashdenomination(true);
			denaMinLstData();
			refundDenominationData();
			setBoorefundPaymentDetails(false);
			setBooSaveRender(false);
		}else if(getTxnType() == 2){
			refundDenominationData();
			//denaMinLstData();
			setBooRendercashdenomination(false);
			setBoorefundPaymentDetails(true);
			setBooSaveRender(false);
		}



		setBooSaveAll(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Render denomination data
	public void renderingDenominationDT() {
		if (coldatatablevalues.size() != 0) {
			BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,
					new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
			if (paymentModeCashId != null) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					if (collectionlst.getColpaymentmodeIDtypeDT().compareTo(paymentModeCashId) == 0) {
						setDenomtotalCashreceived(getColamountKWD());
						setBooRendercashdenomination(true);
						setCollectedAmount(GetRound.roundBigDecimal(BigDecimal.ZERO,
								foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
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
				if (collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
					totalCardCash = totalCardCash.add(collectionlst.getColAmountDT());
				} else if (collectionlst.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
					totalCreditCardCash = totalCreditCardCash.add(collectionlst.getColAmountDT());
				}
			}
			setKnetValue(GetRound.roundBigDecimal(totalCardCash,
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setChequeValue(GetRound.roundBigDecimal(totalCreditCardCash,
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
		}
	}

	// Denomination List Data
	private void denaMinLstData() {
		try {
			lstData.clear();
			ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
			/*
			 * Checking that it's first time or not, first time list size will
			 * be 0
			 */
			// double sAmount = 0;
			localLstData.clear();
			if (localLstData.size() == 0) {
				/* Responsible to show serial number in datatable */
				// int i = 0;
				// Responsible to hold each row in different bean object of
				// datatable



				List<CurrencyWiseDenomination> currencyWiseDenolst = iPersonalRemittanceService.getCurrencyDenominations(new BigDecimal(
						sessionStateManage.getCurrencyId()), sessionStateManage.getCountryId());

				int serial = 1;
				for (CurrencyWiseDenomination currencyDenObj : currencyWiseDenolst) {
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
			setDenomtotalCash(GetRound.roundBigDecimal(new BigDecimal(totalSum),
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			// setCollectedAmount(getDenomtotalCash());
			setPayNetPaidAmount(getCalNetAmountPaid());
			setPayPaidAmount(GetRound.roundBigDecimal(getToalUsedAmount(),
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setPayRefund(getPayPaidAmount().subtract(getPayNetPaidAmount()));
			if (getPayRefund().compareTo(BigDecimal.ZERO) == 0) {
				setNextOrSaveButton(Constants.Save);
				// setSavetimeReportEmailCheck(true);
			} else {
				setNextOrSaveButton(Constants.Next);
				// setSavetimeReportEmailCheck(false);
			}
			setLstData(localLstData); // Adding denomication here
			if (localLstData.size() != 0) {
				setDenominationchecking(Constants.DenominationAvailable);
			} else {
				setDenominationchecking(Constants.DenominationNotAvailable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occured During Denomination " + e.getMessage());

		}
	}

	// Refund denomination data
	private void refundDenominationData() {
		lstRefundData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		/* Checking that it's first time or not, first time list size will be 0 */
		// double sAmount = 0;
		localLstData.clear();
		if (localLstData.size() == 0) {

			List<ViewStock> stockList = iPersonalRemittanceService.toCheckStockForView(sessionStateManage.getCountryId(),
					sessionStateManage.getUserName(), sessionStateManage.getBranchId(), sessionStateManage.getCompanyId(),
					sessionStateManage.getCurrencyId());

			int serial = 1;
			for (ViewStock viewStockObj : stockList) {
				ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
				forLocalCurrencyDtObj.setSerial(serial);
				forLocalCurrencyDtObj.setItem(viewStockObj.getDenominationAmount());
				forLocalCurrencyDtObj.setQty("");
				forLocalCurrencyDtObj.setPrice("");
				if (viewStockObj.getCurrentStock() != null) {
					forLocalCurrencyDtObj.setStock(viewStockObj.getCurrentStock().intValue());
				} else {
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
		System.out.println("Refund  :"+totalSum);
		if(getTxnType()==1){
			setRefundAmount(getPayRefund());
			setCollectedRefundAmount(new BigDecimal(0));
			setLstRefundData(localLstData);
			//Added by Rabil 
		}else if(getTxnType()==2){
			setDenomtotalCash(GetRound.roundBigDecimal(new BigDecimal(totalSum),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			System.out.println("getTxnType()==2 getDenomtotalCash :"+getDenomtotalCash());
			setPayNetPaidAmount(getCalNetAmountPaid());
			System.out.println("getTxnType()==2 getPayNetPaidAmount :"+getPayNetPaidAmount());
			setPayPaidAmount(GetRound.roundBigDecimal(getToalUsedAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			System.out.println("getPayPaidAmount() :"+getPayPaidAmount()+"\t getPayNetPaidAmount() :"+getPayNetPaidAmount());
			setPayRefund(getPayNetPaidAmount());//getPayPaidAmount().subtract(getPayNetPaidAmount()));
			System.out.println("getTxnType()==2 setPayRefund :"+getPayRefund());
			setLstRefundData(localLstData);
			setRefundAmount(getPayNetPaidAmount());
			setNextOrSaveButton(Constants.Save);
		}
		if (localLstData.size() != 0) {
			setDenominationchecking(Constants.DenominationAvailable);
		} else {
			setDenominationchecking(Constants.DenominationNotAvailable);
		}

	}

	// Refund cell edit
	public void onRefundCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {

		int quantity = 0; 

		if (foreignLocalCurrencyDataTable.getQty() == "" && foreignLocalCurrencyDataTable.getQty() != null) {
			quantity = 0;
		} else {
			try {
				quantity = Integer.parseInt(foreignLocalCurrencyDataTable.getQty());

			} catch (Exception e) {
				System.out.println("Exception occured" + e.getMessage());
				quantity = 0;

			}
		}


		if (foreignLocalCurrencyDataTable.getStock() >= quantity && quantity != 0) {

			BigDecimal totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(
					new BigDecimal(foreignLocalCurrencyDataTable.getQty() == "" ? "0" : foreignLocalCurrencyDataTable.getQty()));
			if (foreignLocalCurrencyDataTable.getQty().equals("")) {
				foreignLocalCurrencyDataTable.setQty("");
				// added by rabil for clear if blank
				foreignLocalCurrencyDataTable.setPrice("");
			}
			if (totalcashAmount != null && totalcashAmount.doubleValue() != 0) {
				foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount,
						foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())))
						.toPlainString());
			} else {
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
				foreignLocalCurrencyDataTable.setQty("");
				foreignLocalCurrencyDataTable.setPrice("");
				RequestContext.getCurrentInstance().execute("clearrefunddenominationexceed.show();");
				return;

			} else {
				setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			}
		} else {
			RequestContext.getCurrentInstance().execute("stockNotAvailable.show();");
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
		}
	}

	// Collection cell edit
	public void onCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {

		BigDecimal qty = null;

		if (foreignLocalCurrencyDataTable.getQty() == "" && foreignLocalCurrencyDataTable.getQty() != null) {
			qty = new BigDecimal(0);
			System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq ZEROOOOOOOOOOOOOOOO");
		} else {
			System.out.println("foreignLocalCurrencyDataTable.getQty()" + foreignLocalCurrencyDataTable.getQty());

			try {
				qty = new BigDecimal(foreignLocalCurrencyDataTable.getQty());
			} catch (Exception e) {

				System.out.println("Exception occcured " + e);
				System.out.println("Exception occcured " + e);
				System.out.println("Exception occcured " + e);
				qty = new BigDecimal(0);

			}

			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		}
		BigDecimal totalcashAmount = null;
		try {
			totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(qty);
		} catch (Exception e) {
			System.out.println("Exceptionnnnnnnnnnnnn---------------------->QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ" + e);
		}

		System.out.println("@@@@@@@@@@@@@@@@@" + foreignLocalCurrencyDataTable.getQty() == null);
		System.out.println("#################" + foreignLocalCurrencyDataTable.getQty().equals(""));

		if (foreignLocalCurrencyDataTable.getQty().equals("")) {
			foreignLocalCurrencyDataTable.setQty("");
			// added by rabil for clear if blank
			foreignLocalCurrencyDataTable.setPrice("");
		}
		if (totalcashAmount != null && totalcashAmount.doubleValue() != 0.0) {

			try {
				foreignLocalCurrencyDataTable.setPrice(GetRound.roundBigDecimal(totalcashAmount,
						foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())))
						.toPlainString());
			} catch (Exception e) {
				System.out.println("Exceptionnnnnnnnnnnnn---------------------->11111" + e);
			}

		} else {
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
			setDenomtotalCash(GetRound.roundBigDecimal(totalSum,
					foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setCollectedAmount(getDenomtotalCash());
			RequestContext.getCurrentInstance().execute("cleardenominationexceed.show();");
			setDataTableClear(foreignLocalCurrencyDataTable);
			foreignLocalCurrencyDataTable.setQty("");
			return;

		} else {
			try {
				setDenomtotalCash(GetRound.roundBigDecimal(totalSum,
						foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			} catch (Exception e) {
				System.out.println("Exceptionnnnnnnnnnnnn---------------------->33333333" + e);
			}

			setCollectedAmount(getDenomtotalCash());
		}

	}

	public void clearDataTableClearDenomination() {
		if (getDataTableClear() != null) {
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable = getDataTableClear();

			System.out.println("foreignLocalCurrencyDataTable" + foreignLocalCurrencyDataTable);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Change of payment mode
	public void changeofPaymentMode() {
		List<PaymentModeDesc> lstofPayment = ipaymentService.getPaymentDescLangList(new BigDecimal(
				sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		// Boolean checkCash = false;
		String paymentModedesc = null;
		String paymentModeCode = null;
		if (lstofPayment.size() != 0) {
			for (PaymentModeDesc paymentModeDesc : lstofPayment) {
				if ((getColpaymentmodeId() == null ? BigDecimal.ZERO : getColpaymentmodeId()).compareTo(paymentModeDesc.getPaymentMode()
						.getPaymentModeId()) == 0) {
					paymentModedesc = paymentModeDesc.getLocalPaymentName();
					paymentModeCode = paymentModeDesc.getPaymentMode().getPaymentCode();
					setColpaymentmodeName(paymentModedesc);
					setColpaymentmodeCode(paymentModeCode);
					break;
				} else {
					// paymentModedesc = null;
					setColpaymentmodeName(null);
					setColpaymentmodeCode(null);
				}
			}

			if (getColpaymentmodeCode() != null) {
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

					if (getColpaymentmodeCode().equalsIgnoreCase(Constants.CashCode)) {
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
						setBooRenderColCheque(false);
						setBooRenderColDebitCard(true);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(true);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)) {
						setColpaymentmodeId(null);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						RequestContext.getCurrentInstance().execute("checkPaymentModeService.show();");
						return;
					} else {
						setColpaymentmodeId(null);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						System.out.println("Payment Mode Newly added");
						RequestContext.getCurrentInstance().execute("checkPaymentModeService.show();");
						return;
					}
				}
			} else {
				setBooRenderColDebitCard(false);
				setBooRenderColCheque(false);
			}
		}

	}

	// calculation of cash while entering
	public void checkcashcollection() {
		setToalUsedAmount(getCashAmount());
	}

	// save records of fs in collection and collection details from view
	/*public HashMap<String, Object> saveCollect() {
		List<Collect> collectIdDetails = new ArrayList<Collect>();
		HashMap<String, Object> returnResult = new HashMap<String, Object>();
		// boolean collectdata = false;
		if (lstselectedrecord.size() > 0) {
			setDocumentNo(getDocumentSerialCollectTrnx("U"));
			// for (ShoppingCartDataTableBean shoppingCartDetails :
			// lstselectedrecord) {
			ShoppingCartDataTableBean shoppingCartDetails = lstselectedrecord.get(0);
			Collect collect = new Collect();
			// company Id
			CompanyMaster companymaster = new CompanyMaster();
			companymaster.setCompanyId(shoppingCartDetails.getCompanyId());
			collect.setFsCompanyMaster(companymaster);

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
			// Foriegn Currency id
			CurrencyMaster forcurrencymaster = new CurrencyMaster();
			// forcurrencymaster.setCurrencyId(shoppingCartDetails.getForeigncurrency());
			// collect.setExCurrencyMaster(forcurrencymaster);

			// It should be always local currecny //added by Rabil
			forcurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
			collect.setExCurrencyMaster(forcurrencymaster);

			collect.setPaidAmount(getPayPaidAmount());
			// collect.setPaidAmount(getPayPaidAmount().add(getKnetValue()));
			collect.setRefoundAmount(getPayRefund());
			collect.setNetAmount(getPayNetPaidAmount());
			collect.setIsActive(Constants.Yes);
			try {
				collect.setAccountMMYYYY(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			CountryBranch bankbranch = new CountryBranch();
			bankbranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			collect.setExBankBranch(bankbranch);
			System.out.println("Constants.RECEIPT_TYPE :" + Constants.RECEIPT_TYPE);

			collect.setReceiptType(Constants.RECEIPT_TYPE);

			collect.setGeneralLegerDate(new Date());
			collect.setCreatedBy(sessionStateManage.getUserName());
			collect.setCreatedDate(new Date());
			iPersonalRemittanceService.saveCollectTableData(collect);
			collectIdDetails.add(collect);
			returnResult.put("Collect", collect);
			returnResult.put("ExchangeRate", shoppingCartDetails.getExchangeRateApplied());
			returnResult.put("LocalTrnsAmount", shoppingCartDetails.getLocalNextTranxAmount());

			saveCollectionDetail(collect);
		}
		// }
		return returnResult;
	}*/

	public void saveCollectionDetail(Collect collect) {
		int i = 1;
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

			if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.KNETCode)) {
				collectDetails.setDebitCard(lstofPayment.getColCardNumberDT().toPlainString());
				collectDetails.setDbCardName(lstofPayment.getColNameofCardDT());
				collectDetails.setApprovalNo(lstofPayment.getColApprovalNo());
				if (lstofPayment.getColAuthorizedByDT() != null) {
					collectDetails.setAuthby(lstofPayment.getColAuthorizedByDT());
					collectDetails.setAuthdate(new Date());
				}
			} else if (lstofPayment.getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)) {
				collectDetails.setChequeRef(lstofPayment.getColchequeRefNo());
				collectDetails.setChequeDate(lstofPayment.getColchequeDate());
				collectDetails.setApprovalNo(lstofPayment.getColApprovalNo());
			}

			/*
			 * if
			 * (!lstofPayment.getColpaymentmodetypeDT().equalsIgnoreCase(Constants
			 * .CASHNAME)) {
			 * collectDetails.setDebitCard(lstofPayment.getColCardNumberDT
			 * ().toPlainString());
			 * collectDetails.setDbCardName(lstofPayment.getColNameofCardDT());
			 * collectDetails.setApprovalNo(lstofPayment.getColApprovalNo()); if
			 * (lstofPayment.getColAuthorizedByDT() != null) {
			 * collectDetails.setAuthby(lstofPayment.getColAuthorizedByDT());
			 * collectDetails.setAuthdate(new Date()); } }
			 */
			collectDetails.setIsActive(Constants.Yes);
			try {
				collectDetails.setAcyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			collectDetails.setCreatedBy(sessionStateManage.getUserName());
			collectDetails.setCreatedDate(new Date());
			collectDetails.setPaymentModeId(lstofPayment.getColpaymentmodeIDtypeDT());

			iPersonalRemittanceService.saveCollectDetailTableData(collectDetails);
		}
	}

	// Save Currency denomination to currency adjust
	public Boolean saveForeignCurrencyAdjust() {

		BigDecimal totalCollect = BigDecimal.ZERO;
		BigDecimal totalRefund = BigDecimal.ZERO;
		Boolean saveTempCurrencyAdjust = false;

		List<ForeignCurrencyAdjustWU> foreignCurrencyAdjustlst = new ArrayList<ForeignCurrencyAdjustWU>();

		try {

			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
			Date acc_Month = null;
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());

			List<UserFinancialYear> finYearList = generalService.getDealYear(new Date());

			int i = 0;

			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstData) {

				if (!foreignLocalCurrencyDataTable.getQty().equals("") && foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {

					ForeignCurrencyAdjustWU foreignCurrencyAdjust = new ForeignCurrencyAdjustWU();

					// Country save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					// customer Save
					if (getWucheckRecCustomer() != null) {
						Customer customer = new Customer();
						customer.setCustomerId(getWucheckRecCustomer());
						foreignCurrencyAdjust.setFsCustomer(customer);
					} else {
						Customer customer = new Customer();
						customer.setCustomerId(getCustomerNo());
						foreignCurrencyAdjust.setFsCustomer(customer);
					}

					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
					// send document number
					if (getTxnType() == 1) {
						foreignCurrencyAdjust.setDocumentNo(getSendDocumentNo());
					} else if (getTxnType() == 2) {
						foreignCurrencyAdjust.setDocumentNo(getReceiveDocumentNo());
					}
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					foreignCurrencyAdjust.setDocumentDate(new Date());
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

					// country branch Id
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
					// calculate amount
					totalCollect = totalCollect.add(foreignCurrencyAdjust.getAdjustmentAmount());

					// currency denomination Id
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					// western union exchange rate not available
					//foreignCurrencyAdjust.setExchangeRate(BigDecimal.ONE);

					foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());

					foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
					//foreignCurrencyAdjust.setProgNumber("WESTERN UNION");

					foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					foreignCurrencyAdjust.setTransactionType(Constants.C);
					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());

					foreignCurrencyAdjust.setMtcNo(getMtcNo());

					if(finYearList != null && !finYearList.isEmpty()){
						foreignCurrencyAdjust.setDocumentFinanceYear(finYearList.get(0).getFinancialYear());
					}

					//westernUnionService.saveForeignCurrencyAdjust(foreignCurrencyAdjust);
					foreignCurrencyAdjustlst.add(foreignCurrencyAdjust);

				} else {
					log.info("Number of notes is 0");
				}
			}


			int j = 0;

			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstRefundData) {

				if (!foreignLocalCurrencyDataTable.getQty().equals("") && foreignLocalCurrencyDataTable.getQty() != null && !foreignLocalCurrencyDataTable.getQty().equals("0")) {

					ForeignCurrencyAdjustWU foreignCurrencyAdjust = new ForeignCurrencyAdjustWU();

					// Country save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					// customer Save
					if (getWucheckRecCustomer() != null) {
						Customer customer = new Customer();
						customer.setCustomerId(getWucheckRecCustomer());
						foreignCurrencyAdjust.setFsCustomer(customer);
					} else {
						Customer customer = new Customer();
						customer.setCustomerId(getCustomerNo());
						foreignCurrencyAdjust.setFsCustomer(customer);
					}

					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
					if (getTxnType() == 1) {
						foreignCurrencyAdjust.setDocumentNo(getSendDocumentNo());
					} else if (getTxnType() == 2) {
						foreignCurrencyAdjust.setDocumentNo(getReceiveDocumentNo());
					}
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++j));
					foreignCurrencyAdjust.setDocumentDate(new Date());
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

					// country branch Id
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
					// calculate amount
					totalRefund = totalRefund.add(foreignCurrencyAdjust.getAdjustmentAmount());

					// currency denomination Id
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					// western union exchange rate not available
					//foreignCurrencyAdjust.setExchangeRate(BigDecimal.ONE);

					foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());

					foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
					//foreignCurrencyAdjust.setProgNumber("WESTERN UNION");

					foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					foreignCurrencyAdjust.setTransactionType(Constants.F);

					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());
					foreignCurrencyAdjust.setMtcNo(getMtcNo());

					if(finYearList != null && !finYearList.isEmpty()){
						foreignCurrencyAdjust.setDocumentFinanceYear(finYearList.get(0).getFinancialYear());
					}

					//westernUnionService.saveForeignCurrencyAdjust(foreignCurrencyAdjust);
					foreignCurrencyAdjustlst.add(foreignCurrencyAdjust);

				} else {
					log.info("Number of notes is 0");
				}
			}

			if(totalCollect.compareTo(BigDecimal.ZERO) != 0 || totalRefund.compareTo(BigDecimal.ZERO) != 0){
				BigDecimal originalAmount = BigDecimal.ZERO;
				if(totalCollect.compareTo(totalRefund)>=0){
					originalAmount = totalCollect.subtract(totalRefund);
				}else{
					originalAmount = totalRefund.subtract(totalCollect);
				}

				if(getCalNetAmountPaid() != null && getCalNetAmountPaid().compareTo(BigDecimal.ZERO) != 0){
					if(originalAmount.compareTo(BigDecimal.ZERO) != 0 && originalAmount.compareTo(getCalNetAmountPaid())==0){
						// save rec
						saveTempCurrencyAdjust = true;
						westernUnionService.saveForeignCurrencyAdjust(foreignCurrencyAdjustlst);
					}else{
						saveTempCurrencyAdjust = false;
					}
				}else{
					saveTempCurrencyAdjust = null;
				}
			}

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::saveForeignCurrencyAdjust");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

		return saveTempCurrencyAdjust;
	}

	// Save Denomination
	public void saveRemittance() {

		String errorMessage = null;

		if (getTxnType() == 1) {

			// if any records available in Temp Currency Adjust- Hard Delete
			westernUnionService.deleteFromExTempCurrencyAdjust(getSendCompanyCode(), getSendDocumentCode(), getSendDocumentFinanceYear(), getSendDocumentNo(), getMtcNo());

			Boolean saveRec = saveForeignCurrencyAdjust();

			if(saveRec != null){
				if(saveRec){
					errorMessage = westernUnionService.updateWUTransfer(getSendCompanyCode(), getSendDocumentCode(), getSendDocumentFinanceYear(),getSendDocumentNo());
					if (errorMessage == null) {
						finalRender();
					} else {
						setProcedureError(errorMessage);
						RequestContext.getCurrentInstance().execute("procedureErr.show();");
						return;
					}
				}else{
					setErrmsg("Amount Not Matching");
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}
			}else{
				setErrmsg("Amount Not Avaiable");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}

		} else if (getTxnType() == 2) {
			// if any records available in Temp Currency Adjust- Hard Delete
			westernUnionService.deleteFromExTempCurrencyAdjust(getReceiveCompanyCode(), getReceiveDocumentCode(), getReceiveDocumentFinanceYear(), getReceiveDocumentNo(), getMtcNo());

			Boolean saveRec = saveForeignCurrencyAdjust();

			if(saveRec != null){
				if(saveRec){
					errorMessage = westernUnionService.updateWUTransfer(getReceiveCompanyCode(), getReceiveDocumentCode(), getReceiveDocumentFinanceYear(),getReceiveDocumentNo());
					if (errorMessage == null) {
						finalRender();
					} else {
						setProcedureError(errorMessage);
						RequestContext.getCurrentInstance().execute("procedureErr.show();");
						return;
					}
				}else{
					setErrmsg("Amount Not Matching");
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}
			}else{
				setErrmsg("Amount Not Avaiable");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}

		}

	}

	// Get western union application url
	public String getWUAppUrl(BigDecimal docno, BigDecimal finyear) {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		// ipAddress = "localhost";
		String servleturl = "http://" + ipAddress + ":8085/WUApp?documentNo=" + docno + "&documentFinanceYear=" + finyear;
		System.out.println("WU APP URL==============>" + servleturl);
		return servleturl;
	}

	// Read from western union transfer by document no
	public BigDecimal readWUTransfer(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal tokenNo) {
		List<WesternUnionTransfer> wuDataList = westernUnionService.getWUData(companyCode, documentCode, documentFinanceYr, tokenNo);
		BigDecimal exdocno = BigDecimal.ZERO;
		if (wuDataList.size() > 0) {
			exdocno = wuDataList.get(0).getExDocumentNo();
		}
		return exdocno;
	}

	public void onRowSelect(SelectEvent event) {
		setIsWUSend(false);
		if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
			selectedWUBeneList.clear();
		}

		selectedWUBeneList.add((WesternUnionBeneficaryDataTable) event.getObject());
		setIsWUSend(false);

		checkingBeneAccountDetails();

	}

	public void onRowUnselect(UnselectEvent event) {
		if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
			selectedWUBeneList.clear();
		}
		this.selectedValues = null;
	}

	// checking weather beneficiary account created or not
	public void checkingBeneAccountDetails() {

		try {

			if (selectedWUBeneList != null && selectedWUBeneList.size() == 1) {
				this.setBooDenoEnable(true);

				System.out.println("selectedWUBeneList select object" + selectedWUBeneList.size());

				WesternUnionBeneficaryDataTable selectedRecord = selectedWUBeneList.get(0);

				List<BeneficaryAccount> lstBene = westernUnionService.getBeneficaryAccountDetails(selectedRecord.getBeneficaryMasterSeqId(),
						Constants.WU_BANK_CODE, selectedRecord.getCountryId(), selectedRecord.getCurrencyId());

				if (lstBene != null && !lstBene.isEmpty()) {

					if (lstBene != null && lstBene.size() == 1) {

						BeneficaryAccount beneAcc = lstBene.get(0);

						// update if deleted D to activate Y
						List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices.checkBenificaryRelationExist(beneAcc.getBeneficaryMaster().getBeneficaryMasterSeqId(), beneAcc.getBeneficaryAccountSeqId());
						if(lstBeneRelationShip.size() != 0){

							BeneficaryRelationship beneRel = lstBeneRelationShip.get(0);

							BigDecimal beneRelationShipId = beneRel.getBeneficaryRelationshipId();

							if(!beneRel.getIsActive().equalsIgnoreCase(Constants.Yes)){
								String status = Constants.Yes;
								iPersonalRemittanceService.deleteBeneAccountRecord(beneAcc.getBeneficaryAccountSeqId() ,beneRelationShipId,status);
							}
						}


						try {
							saveWUTransfer(selectedRecord, beneAcc);
						} catch (IOException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("error.show();");
							return;
						} catch (InterruptedException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("error.show();");
							return;
						} catch (AMGException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("error.show();");
							return;
						}
					} else {
						setErrmsg("Beneficiary Account Not Available");
						RequestContext.getCurrentInstance().execute("csp.show();");
						return;
					}
				} else {
					// BeneficaryAccount beneficiaryAccRecord =
					// saveBeneficiaryAccountDetails(selectedRecord);
					BeneficaryAccount beneficiaryAccRecord = saveBeneficiaryAccountDetails(selectedRecord, selectedRecord.getCountryId(),
							selectedRecord.getCurrencyId());
					if (beneficiaryAccRecord != null) {
						try {
							saveWUTransfer(selectedRecord, beneficiaryAccRecord);
						} catch (IOException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("error.show();");
							return;
						} catch (InterruptedException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("error.show();");
							return;
						} catch (AMGException e) {
							log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
							setErrorMessage(e.getMessage());
							RequestContext.getCurrentInstance().execute("error.show();");
							return;
						}
					}
				}

			} else {
				setErrorMessage("Beneficiary not available");
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::saveWUTransfer()");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	// Saving record for western union in Beneficiary Account
	public BeneficaryAccount saveBeneficiaryAccountDetails(WesternUnionBeneficaryDataTable selectedRecord, BigDecimal bankCountryId,
			BigDecimal bankCurrencyId) {

		BigDecimal dbankId = null;
		BigDecimal dbankBranchId = null;
		BeneficaryAccount beneficaryAccount = null;

		try {
			beneficaryAccount = new BeneficaryAccount();

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId()); // application
			// country
			beneficaryAccount.setBeneApplicationCountry(countryMaster);

			CountryMaster countryMaster1 = new CountryMaster();
			countryMaster1.setCountryId(bankCountryId); // beneficiary bank
			// country
			beneficaryAccount.setBeneficaryCountry(countryMaster1);

			List<BankMaster> bankMasterdetails = bankMasterService.getBankMasterInfo(Constants.WU_BANK_CODE);

			if (bankMasterdetails != null && bankMasterdetails.size() != 0) {

				BankMaster bankMasterId = bankMasterdetails.get(0);

				if (bankMasterId.getBankId() != null) {
					dbankId = bankMasterId.getBankId(); // setting bankID
					// western union
					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(bankMasterId.getBankId()); // western
					// union
					// bank id
					beneficaryAccount.setBank(bankMaster);

					beneficaryAccount.setBankCode(Constants.WU_BANK_CODE); // western
					// union
					// bank
					// code

					List<BankBranch> bankBranchdetails = bankBranchDetailsService.getBranchCodebyBank(bankMasterId.getBankId());

					if (bankBranchdetails != null && bankBranchdetails.size() != 0) {

						BankBranch bankBranchId = bankBranchdetails.get(0);

						if (bankBranchId.getBankBranchId() != null) {
							dbankBranchId = bankBranchId.getBankBranchId();
							BankBranch bankBranch = new BankBranch();
							bankBranch.setBankBranchId(bankBranchId.getBankBranchId()); // western
							// union
							// bank
							// branch
							// id
							beneficaryAccount.setBankBranch(bankBranch);

							beneficaryAccount.setBankBranchCode(bankBranchId.getBranchCode()); // western
							// union
							// bank
							// branch
							// code
						}
					}
				}
			}

			List<ServiceGroupMaster> lstServiceGroup = serviceGroupMasterService.toServiceGroupCodeAllValues(Constants.CashCode);
			if (lstServiceGroup.size() != 0) {
				ServiceGroupMaster serviceGroup = lstServiceGroup.get(0);

				if (serviceGroup.getServiceGroupId() != null) {
					ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
					serviceGroupMaster.setServiceGroupId(serviceGroup.getServiceGroupId()); // cash
					// for
					// western
					// union
					beneficaryAccount.setServicegropupId(serviceGroupMaster);
				}
			}

			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(bankCurrencyId); // beneficiary country
			// currency western
			// union
			beneficaryAccount.setCurrencyId(currencyMaster);

			beneficaryAccount.setIsActive(Constants.Yes);
			beneficaryAccount.setCreatedBy(sessionStateManage.getUserName());
			beneficaryAccount.setCreatedDate(new Date());

			if (selectedRecord.getBeneficaryMasterSeqId() != null) {
				BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
				beneficaryMaster.setBeneficaryMasterSeqId(selectedRecord.getBeneficaryMasterSeqId());
				beneficaryAccount.setBeneficaryMaster(beneficaryMaster);
			}

			iPersonalRemittanceService.saveBeneficiaryAccount(beneficaryAccount);

			// saving relation with master_id , relation_id and account_id
			BeneficaryRelationship beneficaryRelationship = saveBeneficiaryRelationshipDetails(selectedRecord, beneficaryAccount);

			if (selectedRecord.getBeneficaryMasterSeqId() != null && dbankId != null && dbankBranchId != null
					&& beneficaryAccount.getBeneficaryAccountSeqId() != null && beneficaryAccount.getCurrencyId().getCurrencyId() != null
					&& beneficaryRelationship.getCustomerId().getCustomerId() != null) {
				String errorMessage = beneficaryCreation.getBeneDetailProce(selectedRecord.getBeneficaryMasterSeqId(), dbankId, dbankBranchId,
						beneficaryAccount.getBeneficaryAccountSeqId(), beneficaryAccount.getCurrencyId().getCurrencyId(), beneficaryRelationship
						.getCustomerId().getCustomerId());
				if (errorMessage != null) {
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrmsg("EX_POPULATE_BENE_DT " + errorMessage);
					return beneficaryAccount;
				}
			} else {
				RequestContext.getCurrentInstance().execute("csp.show();");
				setErrmsg("EX_POPULATE_BENE_DT NOT EXECUTED");
				return beneficaryAccount;
			}

		} catch (Exception e) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return beneficaryAccount;
		}

		return beneficaryAccount;
	}

	// saving record for western union in beneficiary relationship
	public BeneficaryRelationship saveBeneficiaryRelationshipDetails(WesternUnionBeneficaryDataTable selectedRecord,
			BeneficaryAccount beneficaryAccount) {

		BeneficaryRelationship beneficaryRelationship = new BeneficaryRelationship();

		try {

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(sessionStateManage.getCountryId());
			beneficaryRelationship.setApplicationCountry(countryMaster);

			if (selectedRecord.getCustomerId() != null) {
				Customer customer = new Customer();
				customer.setCustomerId(selectedRecord.getCustomerId());
				beneficaryRelationship.setCustomerId(customer);
			}

			if (selectedRecord.getRelationShipId() != null) {
				Relations relations = new Relations();
				relations.setRelationsId(selectedRecord.getRelationShipId());
				beneficaryRelationship.setRelations(relations);
			}

			beneficaryRelationship.setIsActive(Constants.Yes);
			beneficaryRelationship.setCreatedBy(sessionStateManage.getUserName());
			beneficaryRelationship.setCreatedDate(new Date());
			beneficaryRelationship.setBeneficaryAccount(beneficaryAccount);

			if (selectedRecord.getBeneficaryMasterSeqId() != null) {
				BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
				beneficaryMaster.setBeneficaryMasterSeqId(selectedRecord.getBeneficaryMasterSeqId());
				beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
			}

			iPersonalRemittanceService.saveBeneficiaryRelationship(beneficaryRelationship);

		} catch (Exception e) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + e.getMessage());
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

		return beneficaryRelationship;
	}

	// Save to Western Union money transfer ( Send prompt)
	public void saveWUTransfer(WesternUnionBeneficaryDataTable selectedRecord, BeneficaryAccount beneAcc) throws IOException, InterruptedException,
	AMGException {

		try {

			WesternUnionTransfer westernUnionTransfer = new WesternUnionTransfer();

			List<UserFinancialYear> finYearList = generalService.getDealYear(new Date());
			Date accyymm = null;

			try {
				accyymm = DateUtil.getCurrentAccountYearMonth();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			List<CompanyMasterDesc> companyList = generalService
					.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			if (companyList.size() > 0) {
				westernUnionTransfer.setCompanyCode(companyList.get(0).getFsCompanyMaster().getCompanyCode());
				setCompanyId(companyList.get(0).getFsCompanyMaster().getCompanyId());
			}

			westernUnionTransfer.setLocationCode(sessionStateManage.getCountryBranchCode().toString());

			if (finYearList.size() > 0) {
				westernUnionTransfer.setDocumentFinanceYear(finYearList.get(0).getFinancialYear());
			}
			if (accyymm != null) {
				westernUnionTransfer.setAccountYearMonth(accyymm);
			}
			westernUnionTransfer.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
			westernUnionTransfer.setDocumentDate(new Date());

			setSendCompanyCode(westernUnionTransfer.getCompanyCode());
			setSendDocumentFinanceYear(westernUnionTransfer.getDocumentFinanceYear());
			setSendDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));

			westernUnionTransfer.setTerminalId(Constants.TERMINAL_ID);
			System.out.println("WUID=======>" + sessionStateManage.getWUUsername());

			westernUnionTransfer.setWuId(sessionStateManage.getWUUsername());
			westernUnionTransfer.setCreatedDate(new Date());
			westernUnionTransfer.setCreatedBy(sessionStateManage.getUserName());
			westernUnionTransfer.setJavaTransaction(Constants.JAVA_TRANSACTION_YES);

			String tokenno = westernUnionService.getNextToken();
			System.out.println("Token No=========>" + tokenno);

			westernUnionTransfer.setDocumentNo(new BigDecimal(tokenno));
			setSendDocumentNo(westernUnionTransfer.getDocumentNo());

			westernUnionTransfer.setInorOut(Constants.WU_INOUT_SEND);

			// System.out.println("exist customer reference==="+customerDetails.getFsCustomer().getCustomerReference());

			westernUnionTransfer.setPurposeId(getPurposeOfTransactions());
			westernUnionTransfer.setSourceOfIncomeId(getSourceOfIncome());

			if (beneAcc != null) {

				List<BeneficaryRelationship> beneRelList = westernUnionService.fetchBeneficiaryRelationShip(
						selectedRecord.getBeneficaryMasterSeqId(), beneAcc.getBeneficaryAccountSeqId(), getCustomerNo());

				System.out.println("customer id" + beneRelList.get(0).getCustomerId().getCustomerId());

				if (beneRelList.size() > 0) {
					System.out.println("sequence" + beneRelList.get(0).getMapSequenceId());
					if (beneRelList.get(0).getMapSequenceId() != null) {
						westernUnionTransfer.setBeneficiarySequence(beneRelList.get(0).getMapSequenceId());

						CustomerIdProof customerDetails = customerDetailsList.get(0);
						// westernUnionTransfer.setCustomerReference(customerDetails.getFsCustomer().getCustomerReference());

						List<Customer> custdetail = westernUnionService.getCustomerDetail(beneRelList.get(0).getCustomerId().getCustomerId());

						if (customerDetails != null && custdetail != null && !custdetail.isEmpty()
								&& customerDetails.getFsCustomer().getCustomerReference().compareTo(custdetail.get(0).getCustomerReference()) == 0) {
							westernUnionTransfer.setCustomerReference(custdetail.get(0).getCustomerReference());
						}

						else {
							setErrmsg("Customer Reference vaildation failed .Please contact Admin");
							RequestContext.getCurrentInstance().execute("csp.show();");
							return;
						}
						// Save western Union Money Transfer
						westernUnionService.saveWesternUnionTransfer(westernUnionTransfer);

						// comment
						/*
						 * Connection connection = null; connection =
						 * generalService.getDataSourceFromHibernateSession();
						 * 
						 * CallableStatement cs;
						 * 
						 * cs = connection.prepareCall(
						 * " { call TEMP_WU_MONEY_COLLECT (?,?,?)}");
						 * cs.setBigDecimal(1,
						 * westernUnionTransfer.getDocumentFinanceYear());
						 * cs.setBigDecimal(2,
						 * westernUnionTransfer.getDocumentNo());
						 * cs.setString(3, westernUnionTransfer.getInorOut());
						 * 
						 * cs.execute();
						 */

						// TODO: FOR TESTING PURPOSE WE COMMENT THE BELOW
						// EXTERNAL PART CALLING.

						// Call Western union application
						HttpURLConnection servletConnection=null;
						try {

							URL url = new URL(getWUAppUrl(westernUnionTransfer.getDocumentNo(), westernUnionTransfer.getDocumentFinanceYear()));
							servletConnection = (HttpURLConnection) url.openConnection();

							InputStream response = servletConnection.getInputStream();
							servletConnection.setDoOutput(true);
							refundDenominationData();
							setMainPanelRender(true);
							setBooRenderBenificaryFirstPanel(false);
							setBooRenderOldSmartCardPanel(false);
							setBooRenderBenificarySearchPanel(false);
							setBooRenderBeniListPanel(true);
							setBooRendercashdenomination(false);
							setBoorefundPaymentDetails(false);
							setBooRenderPaymentDetails(false);
							// setBooSaveRender(true);
							setBooSaveDenomRender(false);
							setBooSaveAll(false);
							setIsWUSend(true);
							setStatus(false);

							//checkWUStatus(); // Check WU status and go to DENOMICATION pAGE

						} catch (Exception e) {
							log.info("Method Name::SaveWUTransfer" + e.getMessage());
							// setProcedureError("Western Union App Error:" +
							// e.getMessage());
							// RequestContext.getCurrentInstance().execute("appnotconnected.show();");
							// return;
						}finally{
							if(servletConnection!=null){
								servletConnection.disconnect();
							}
						}

					} else {
						RequestContext.getCurrentInstance().execute("nomapid.show();");
						return;
					}
				} else {
					RequestContext.getCurrentInstance().execute("nomapid.show();");
					return;
				}
			}

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::saveWUTransfer()");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	// Save to Western Union money transfer ( Receive prompt)
	public void saveWUReceive() throws IOException, InterruptedException, AMGException {

		try {
			setBooDenoEnable(true);

			WesternUnionTransfer westernUnionTransfer = new WesternUnionTransfer();
			List<UserFinancialYear> finYearList = generalService.getDealYear(new Date());
			Date accyymm = null;
			try {
				accyymm = DateUtil.getCurrentAccountYearMonth();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			List<CompanyMasterDesc> companyList = generalService
					.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			if (companyList.size() > 0) {
				westernUnionTransfer.setCompanyCode(companyList.get(0).getFsCompanyMaster().getCompanyCode());
				setCompanyId(companyList.get(0).getFsCompanyMaster().getCompanyId());
			}

			westernUnionTransfer.setLocationCode(sessionStateManage.getCountryBranchCode().toString());

			if (finYearList.size() > 0) {
				westernUnionTransfer.setDocumentFinanceYear(finYearList.get(0).getFinancialYear());
			}
			if (accyymm != null) {
				westernUnionTransfer.setAccountYearMonth(accyymm);
			}

			westernUnionTransfer.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
			westernUnionTransfer.setDocumentDate(new Date());

			westernUnionTransfer.setTerminalId(Constants.TERMINAL_ID);
			westernUnionTransfer.setWuId(sessionStateManage.getWUUsername());
			westernUnionTransfer.setJavaTransaction(Constants.JAVA_TRANSACTION_YES);
			westernUnionTransfer.setInorOut(Constants.WU_INOUT_RECEIVE);
			westernUnionTransfer.setMtcYear(getMtcYear());

			String mtcno = getMtcNo();
			String mtc1 = mtcno.substring(0, 3);
			String mtc2 = mtcno.substring(3, 6);
			String mtc3 = mtcno.substring(6);
			String wumtcno = mtc1 + "-" + mtc2 + "-" + mtc3;

			westernUnionTransfer.setWuMTCNo(wumtcno);
			// setMtcNo(wumtcno);

			westernUnionTransfer.setPurposeId(getPurposeOfTransactions());
			westernUnionTransfer.setSourceOfIncomeId(getSourceOfIncome());

			// checking Western union Receive condition
			HashMap<String, String> lstWuTransfer = new HashMap<String, String>();
			lstWuTransfer.put("Company_Code", westernUnionTransfer.getCompanyCode().toPlainString());
			lstWuTransfer.put("Documnent_Code", Constants.DOCUMENT_CODE_FOR_WU);
			lstWuTransfer.put("Documnent_Year", westernUnionTransfer.getDocumentFinanceYear().toPlainString());
			lstWuTransfer.put("MTC_Number", wumtcno);
			lstWuTransfer.put("INorOUT", Constants.WU_INOUT_RECEIVE);
			List<WesternUnionTransfer> lstWesternUnion = westernUnionService.fetchWesternUnionTransfer(lstWuTransfer);

			/*if (lstWesternUnion != null && !lstWesternUnion.isEmpty()) {

				WesternUnionTransfer lastestWUTrnsf = lstWesternUnion.get(0);

				westernUnionTransfer.setWesternUnionId(lastestWUTrnsf.getWesternUnionId());
				westernUnionTransfer.setModifiedBy(sessionStateManage.getUserName());
				westernUnionTransfer.setUpdatedDate(new Date());
				westernUnionTransfer.setCreatedDate(lastestWUTrnsf.getCreatedDate());
				westernUnionTransfer.setCreatedBy(lastestWUTrnsf.getCreatedBy());
				westernUnionTransfer.setDocumentNo(lastestWUTrnsf.getDocumentNo());
				westernUnionTransfer.setCustomerReference(lastestWUTrnsf.getCustomerReference());

			} else {
				westernUnionTransfer.setCreatedDate(new Date());
				westernUnionTransfer.setCreatedBy(sessionStateManage.getUserName());

				// Generate Token no
				String tokenno = westernUnionService.getNextToken();
				westernUnionTransfer.setDocumentNo(new BigDecimal(tokenno));

				westernUnionTransfer.setCustomerReference(getCustomerrefno());

			}
*/
			//Each time token is generated.
			westernUnionTransfer.setCreatedDate(new Date());
			westernUnionTransfer.setCreatedBy(sessionStateManage.getUserName());
			// Generate Token no
			String tokenno = westernUnionService.getNextToken();
			westernUnionTransfer.setDocumentNo(new BigDecimal(tokenno));
			westernUnionTransfer.setCustomerReference(getCustomerrefno());
			 // Each time token generation code end
			
			setReceiveCompanyCode(westernUnionTransfer.getCompanyCode());
			setReceiveDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU));
			setReceiveDocumentFinanceYear(westernUnionTransfer.getDocumentFinanceYear());
			setReceiveDocumentNo(westernUnionTransfer.getDocumentNo());

			// Save western Union Money Transfer
			westernUnionService.saveWesternUnionTransfer(westernUnionTransfer);

			// Call Western union application
			HttpURLConnection servletConnection=null;
			try {

				URL url = new URL(getWUAppUrl(getReceiveDocumentNo(), westernUnionTransfer.getDocumentFinanceYear()));
				System.out.println("Western Union App URL=" + url);
				servletConnection = (HttpURLConnection) url.openConnection();
				InputStream response = servletConnection.getInputStream();
				servletConnection.setDoOutput(true);
				refundDenominationData();
				setMainPanelRender(true);
				setBooRenderBenificaryFirstPanel(false);
				setBooRenderOldSmartCardPanel(false);
				setBooRenderBenificarySearchPanel(false);
				setBooRenderBeniListPanel(false);
				setBooRendercashdenomination(false);
				setBoorefundPaymentDetails(false);
				setBooRenderPaymentDetails(false);
				setBooCheckStatusPanel(true);
				// setBooSaveRender(true);
				setBooSaveDenomRender(false);
				setBooSaveAll(false);
				setIsWUSend(true);
				setStatus(false);

				// TimeUnit.SECONDS.sleep(Integer.parseInt(Constants.WU_APP_DELAY_TIME));
				//checkWUStatus(); // Check WU status and go to payment option

			} catch (Exception e) {
				log.info("Method Name::saveWUReceive()" + e.getMessage());
				// setProcedureError("Western Union App Error:" +
				// e.getMessage());
				// RequestContext.getCurrentInstance().execute("appnotconnected.show();");
				// return;
			}finally{
				if(servletConnection!=null){
					servletConnection.disconnect();
				}
			}

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::saveWUReceive()");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void redirectToCustomerFirstPage() throws DOMException, ParseException, ParserConfigurationException, SAXException, IOException {
		CustomerPersonalInfoBean customerPesonel = (CustomerPersonalInfoBean) appContext.getBean("customerPersonalInfo");
		customerPesonel.resetValues();

		customerPesonel.setSelectType(Constants.MANUAL);
		customerPesonel.setBooManual(true);
		customerPesonel.setBooIdType(true);
		customerPesonel.setSelectedIdType(getSelectCard().toString());
		log.info("==========REDIRECT==========" + getSelectCard().toString() + "" + getIdNumber());
		if (getIdNumber() != null) {
			customerPesonel.setIdNumber(getIdNumber().toString());
		} else {
			customerPesonel.setIdNumber(null);
		}
		// customerPesonel.appearCarddetail() ;
		customerPesonel.setIdType(getSelectCard().toString());
		customerPesonel.setMobileNoFetch(null);
		customerPesonel.checkingIdWithDBForProcessing();

		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationmain.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  CutomerRegistration");
		}

	}

	public void redirectToCustomerPage() throws DOMException, ParseException, ParserConfigurationException, SAXException, IOException {
		CustomerPersonalInfoBean customerPesonel = (CustomerPersonalInfoBean) appContext.getBean("customerPersonalInfo");
		customerPesonel.resetValues();

		customerPesonel.setSelectType(Constants.MANUAL);
		customerPesonel.setBooManual(true);
		customerPesonel.setBooIdType(true);
		customerPesonel.setSelectedIdType(getSelectCard().toString());
		log.info("==========REDIRECT==========" + getSelectCard().toString() + "" + getIdNumber());
		if (getIdNumber() != null) {
			customerPesonel.setIdNumber(getIdNumber().toString());
		} else {
			customerPesonel.setIdNumber(null);
		}
		// customerPesonel.appearCarddetail() ;
		customerPesonel.setIdType(getSelectCard().toString());
		customerPesonel.setMobileNoFetch(null);
		customerPesonel.checkingIdWithDBForProcessing();

		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customermanualinfo.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to  CutomerRegistration");
		}

	}

	// Check status whether data updated or not ..Back-up on 10/10/2016 without Time Delay check
	public void checkWUStatus() throws AMGException, InterruptedException {

		// clearing cashAmount
		setCashAmount(null);

		int wuTimeDelay = Integer.parseInt(Constants.WU_APP_DELAY_TIME);
		int wuSleepInterval = Integer.parseInt(Constants.WU_APP_DELAY_SLEEP_INTERVAL);

		try {

			//for (int i = 0; i <= wuTimeDelay; i++) {
			//	TimeUnit.SECONDS.sleep(wuSleepInterval);
			//	i = i + wuSleepInterval;
			//	System.out.println("Check WU status :" + i);

			String mtcno = null;
			if (getTxnType() == 1) {

				if (getSendCompanyCode() != null && getSendDocumentCode() != null && getSendDocumentFinanceYear() != null
						&& getSendDocumentNo() != null) {

					List<WesternUnionTransfer> wuDataList = westernUnionService.getWUData(getSendCompanyCode(), getSendDocumentCode(),
							getSendDocumentFinanceYear(), getSendDocumentNo());
					if (wuDataList.size() > 0) {
						mtcno = wuDataList.get(0).getMtcNo();
						setMtcNo(mtcno);
						if (getMtcNo() != null) {
							afterSendWU();
							try {
								FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
								//break;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
						gotoMainPage();
						RequestContext.getCurrentInstance().execute("wuwarning.show();");
						return;
					}
				} else {
					setErrorMessage(WarningHandler.showWarningMessage("lbl.tokennotgenerated", sessionStateManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}

			} else if (getTxnType() == 2) {
				if (getReceiveCompanyCode() != null && getReceiveDocumentCode() != null && getReceiveDocumentFinanceYear() != null
						&& getReceiveDocumentNo() != null) {
					List<WesternUnionTransfer> wuDataList = westernUnionService.getWUData(getReceiveCompanyCode(), getReceiveDocumentCode(),
							getReceiveDocumentFinanceYear(), getReceiveDocumentNo());
					if (wuDataList.size() > 0) {
						mtcno = wuDataList.get(0).getWuMTCNo().replace("-", "");
						// mtcno = wuDataList.get(0).getMtcNo();
						setMtcNo(mtcno);
						if (wuDataList.get(0).getModifiedBy() != null) {
							afterSendWU();
							try {
								FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
								//break;
							} catch (IOException e) {
								e.printStackTrace();
							}

						}
					} else {
						gotoMainPage();
						RequestContext.getCurrentInstance().execute("wuwarning.show();");
						return;
					}
				} else {
					setErrorMessage(WarningHandler.showWarningMessage("lbl.tokennotgenerated", sessionStateManage.getLanguageId()));
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}

			}
			//}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::checkWUStatus()");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	//Back-up on 10/10/2016 with Time Delay check

	/*// Check status whether data updated or not.
		public void checkWUStatus() throws AMGException, InterruptedException {

			// clearing cashAmount
			setCashAmount(null);

			int wuTimeDelay = Integer.parseInt(Constants.WU_APP_DELAY_TIME);
			int wuSleepInterval = Integer.parseInt(Constants.WU_APP_DELAY_SLEEP_INTERVAL);

			try {

				for (int i = 0; i <= wuTimeDelay; i++) {
					TimeUnit.SECONDS.sleep(wuSleepInterval);
					i = i + wuSleepInterval;
					System.out.println("Check WU status :" + i);

					String mtcno = null;
					if (getTxnType() == 1) {

						if (getSendCompanyCode() != null && getSendDocumentCode() != null && getSendDocumentFinanceYear() != null
								&& getSendDocumentNo() != null) {

							List<WesternUnionTransfer> wuDataList = westernUnionService.getWUData(getSendCompanyCode(), getSendDocumentCode(),
									getSendDocumentFinanceYear(), getSendDocumentNo());
							if (wuDataList.size() > 0) {
								mtcno = wuDataList.get(0).getMtcNo();
								setMtcNo(mtcno);
								if (getMtcNo() != null) {
									afterSendWU();
									try {
										FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
										break;
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							} else {
								gotoMainPage();
								RequestContext.getCurrentInstance().execute("wuwarning.show();");
								return;
							}
						} else {
							setErrorMessage(WarningHandler.showWarningMessage("lbl.tokennotgenerated", sessionStateManage.getLanguageId()));
							RequestContext.getCurrentInstance().execute("error.show();");
							return;
						}

					} else if (getTxnType() == 2) {
						if (getReceiveCompanyCode() != null && getReceiveDocumentCode() != null && getReceiveDocumentFinanceYear() != null
								&& getReceiveDocumentNo() != null) {
							List<WesternUnionTransfer> wuDataList = westernUnionService.getWUData(getReceiveCompanyCode(), getReceiveDocumentCode(),
									getReceiveDocumentFinanceYear(), getReceiveDocumentNo());
							if (wuDataList.size() > 0) {
								mtcno = wuDataList.get(0).getWuMTCNo().replace("-", "");
								// mtcno = wuDataList.get(0).getMtcNo();
								setMtcNo(mtcno);
								if (wuDataList.get(0).getModifiedBy() != null) {
									afterSendWU();
									try {
										FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
										break;
									} catch (IOException e) {
										e.printStackTrace();
									}

								}
							} else {
								gotoMainPage();
								RequestContext.getCurrentInstance().execute("wuwarning.show();");
								return;
							}
						} else {
							setErrorMessage(WarningHandler.showWarningMessage("lbl.tokennotgenerated", sessionStateManage.getLanguageId()));
							RequestContext.getCurrentInstance().execute("error.show();");
							return;
						}

					}
				}
			} catch (NullPointerException ne) {
				log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
				setErrorMessage("Method Name::checkWUStatus()");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}

		}
	 */
	// Save all denomination
	public void saveAll() {
		if (getBooRendercashdenomination()) {
			if (lstData.size() != 0) {
				if (getCashAmount().compareTo(getDenomtotalCash()) == 0) {
					if (getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
						nextpaneltoRefundDenomination();
						setNextOrSaveButton(Constants.Save);
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
			} else {
				saveRemittance();
			}
		}
	}

	public void afterSendSaveWU() {
		refundDenominationData();
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBeniListPanel(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);
		// setBooSaveRender(true);
		setBooRenderPaymentDetails(false);
		setBooSaveDenomRender(false);
		setBooSaveAll(false);
		setBooCheckStatusPanel(false);
	}

	public void afterSendWU() {

		try {
			List<WesternUnionTransfer> wutxndtl = new ArrayList<WesternUnionTransfer>();
			BigDecimal sendAmount = null;
			if (getTxnType() == 1) {
				String trnxType="S";
				sendAmount = westernUnionService.getCollectionAmount(getSendCompanyCode(), getSendDocumentCode(), getSendDocumentFinanceYear(),getSendDocumentNo());
				//wutxndtl = westernUnionService.getWUDataReceive(getSendCompanyCode(), getSendDocumentCode(), getSendDocumentFinanceYear(), getMtcNo());
				wutxndtl = westernUnionService.getWUDataReceive(getSendCompanyCode(), getSendDocumentCode(), getSendDocumentFinanceYear(), getMtcNo(),trnxType);

				if (wutxndtl != null) {
					setPayNetPaidAmount(sendAmount);
					setColCurrency(wutxndtl.get(0).getSendCurrency());
					setCalNetAmountPaid(sendAmount);
					setCollectionDocumentNo(getSendDocumentNo());
					setColpaymentmodeName(wutxndtl.get(0).getPaymentMode());
				}

			} else if (getTxnType() == 2) {

				// String mtcno = getMtcNo().replace("-", "");
				String mtcno = getMtcNo();
				String trnxType="R";
				sendAmount = westernUnionService.getCollectionAmount(getReceiveCompanyCode(), getReceiveDocumentCode(),	getReceiveDocumentFinanceYear(), getReceiveDocumentNo());
				//wutxndtl = westernUnionService.getWUDataReceive(getReceiveCompanyCode(), getReceiveDocumentCode(), getReceiveDocumentFinanceYear(),mtcno);
				wutxndtl = westernUnionService.getWUDataReceive(getReceiveCompanyCode(), getReceiveDocumentCode(), getReceiveDocumentFinanceYear(),	mtcno,trnxType);

				if (wutxndtl != null && wutxndtl.size() > 0) {
					setPayNetPaidAmount(sendAmount);
					setColCurrency(wutxndtl.get(0).getSendCurrency());
					setCalNetAmountPaid(sendAmount);
					setCollectionDocumentNo(getReceiveDocumentNo());
					setColpaymentmodeName(wutxndtl.get(0).getPaymentMode());
				} else {
					// if record not available need to show error message
				}
			}

			setMainPanelRender(true);
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderOldSmartCardPanel(false);
			setBooRenderBenificarySearchPanel(false);
			setBooRenderBeniListPanel(false);
			setBooRendercashdenomination(false);
			setBoorefundPaymentDetails(false);
			// setBooSaveRender(false);
			setBooRenderPaymentDetails(true);
			setBooSaveDenomRender(false);
			setBooCheckStatusPanel(false);
			// setBooRenderSendReceivePanel(false);

			/*
			 * try {
			 * FacesContext.getCurrentInstance().getExternalContext().redirect(
			 * "../wu/westernuniontransfer.xhtml"); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::afterSendWU()" + ne.getMessage());
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void saveDinomination() {
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBeniListPanel(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);
		// setBooSaveRender(false);
		saveRemittance();
		setBooRenderPaymentDetails(false);
		setBooSaveDenomRender(false);
		setBooCheckStatusPanel(false);
		// setBooRenderSendReceivePanel(false);
	}

	public void fromReceive() {
		denaMinLstData();
		refundDenominationData();
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBeniListPanel(false);
		setBooRendercashdenomination(true);
		setBoorefundPaymentDetails(false);
		setBooRenderPaymentDetails(false);
		setBooSaveDenomRender(false);
		setBooCheckStatusPanel(false);
		// setBooRenderSendReceivePanel(false);
	}

	// to render panel 8 - Refund Denomination details
	public void nextpaneltoRefundDenomination() {
		// first panel
		setMainPanelRender(true);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRendercashdenomination(false);
		setBooRenderBeniListPanel(false);
		setBoorefundPaymentDetails(true);
		setBooRenderPaymentDetails(false);
		setBooSaveDenomRender(false);
		setBooCheckStatusPanel(false);
		refundDenominationData();
	}

	public void backFromBeneList() {
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificarySearchPanel(true);
		setBooRenderBeniListPanel(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);
		// setBooSaveRender(false);
		setBooRenderPaymentDetails(false);
		setBooSaveDenomRender(false);
		setBooCheckStatusPanel(false);
		setBooDenoEnable(false);

		// clearing token number , doc code , doc no , company code
		setReceiveCompanyCode(null);
		setReceiveDocumentCode(null);
		setReceiveDocumentFinanceYear(null);
		setReceiveDocumentNo(null);

		if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
			selectedWUBeneList.clear();
		}

	}

	public void gotoMainPage() {
		clear();
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderOldSmartCardPanel(true);
		setBooRenderBenificarySearchPanel(false);
		setBooRenderBeniListPanel(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);
		setBooRenderPaymentDetails(false);
		setBooSaveDenomRender(false);
		setIsWUSend(false);
		setStatus(false);
		setBooCheckStatusPanel(false);
		setBooDenoEnable(false);
	}

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
		// String year = String.valueOf(new Date().getYear()).substring(1, 3);

		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String year = String.valueOf(calendar.get(Calendar.YEAR));

		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	public String getDocumentSerialCollectTrnx(String processIn) {
		System.out.println("Start getDocumentSerialID ...." + getFinaceYear());
		log.info("process in :" + processIn);
		String documentSerialId = generalService.getNextDocumentReferenceNumber(Integer.parseInt(sessionStateManage.getSessionValue("countryId")),
				Integer.parseInt(sessionStateManage.getSessionValue("companyId")), Integer.parseInt(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),
				getFinaceYear().intValue(), processIn, sessionStateManage.getCountryBranchCode());
		System.out.println("end getDocumentSerialID  :" + documentSerialId);
		return documentSerialId;
	}

	public void finalRender() {
		setMainPanelRender(true);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRendercashdenomination(false);
		setBooRenderBeniListPanel(false);
		setBoorefundPaymentDetails(false);
		setBooRenderPaymentDetails(false);
		setBooSaveDenomRender(true);
		setBooSaveAll(false);
		setBooCheckStatusPanel(false);
	}

	public void backFromFinal() {
		setMainPanelRender(true);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificaryFirstPanel(false);
		setBooRenderBenificarySearchPanel(false);
		setBooRendercashdenomination(false);
		setBooRenderBeniListPanel(false);
		setBoorefundPaymentDetails(false);
		setBooRenderPaymentDetails(true);
		setBooSaveDenomRender(false);
		setBooSaveAll(false);
		setBooCheckStatusPanel(false);
		setBooDenoEnable(false);

	}

	// Source Of Income List
	public List<SourceOfIncomeDescription> getSourceOfIncomeList() {
		try {
			lstSourceOfIncomes = getForeignCurrencyPurchaseService().getSourceofIncome(sessionStateManage.getLanguageId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstSourceOfIncomes;
	}

	// Purpose of Transaction List
	public List<PurposeOfTransaction> getPurposeOfTransactionsList() {
		try {
			lstPurposeOfTransactions = getForeignCurrencyPurchaseService().getAllPurposeOfTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstPurposeOfTransactions;
	}

	public void clear() {
		setTxnType(0);
		setMtcNo(null);
		setMtcYear(null);
		setCardType(null);
		setSelectCardType(0);
		setIdNumber(null);
		setIsWUSend(true);
		setBooCheckStatusPanel(false);
		setBooDenoEnable(false);
		setCollectedAmount(null);
		setDenomtotalCash(null);
		setDenomtotalCashreceived(null);
		setPayNetPaidAmount(null);
		setPayPaidAmount(null);
		setPayRefund(null);
		setPurposeOfTransactions(null);
		setSourceOfIncome(null);
		//setLstData(null);
		//setLstRefundData(null);
		//setLstselectedrecord(null);
		if(lstData != null || !lstData.isEmpty()){
			lstData.clear();
		}

		if(lstRefundData != null || !lstRefundData.isEmpty()){
			lstRefundData.clear();
		}
	}

	// To check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Boolean getBooRenderBenificaryFirstPanel() {
		return booRenderBenificaryFirstPanel;
	}

	public void setBooRenderBenificaryFirstPanel(Boolean booRenderBenificaryFirstPanel) {
		this.booRenderBenificaryFirstPanel = booRenderBenificaryFirstPanel;
	}

	public BigDecimal getCardType() {
		return cardType;
	}

	public void setCardType(BigDecimal cardType) {
		this.cardType = cardType;
	}

	public Boolean getMainPanelRender() {
		return mainPanelRender;
	}

	public void setMainPanelRender(Boolean mainPanelRender) {
		this.mainPanelRender = mainPanelRender;
	}

	public Boolean getBooRenderOldSmartCardPanel() {
		return booRenderOldSmartCardPanel;
	}

	public void setBooRenderOldSmartCardPanel(Boolean booRenderOldSmartCardPanel) {
		this.booRenderOldSmartCardPanel = booRenderOldSmartCardPanel;
	}

	public Map<BigDecimal, String> getMapcomIdentityType() {
		return mapcomIdentityType;
	}

	public void setMapcomIdentityType(Map<BigDecimal, String> mapcomIdentityType) {
		this.mapcomIdentityType = mapcomIdentityType;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public List<WesternUnionBeneficaryDataTable> getCoustomerBeneficaryDTList() {
		return coustomerBeneficaryDTList;
	}

	public void setCoustomerBeneficaryDTList(List<WesternUnionBeneficaryDataTable> coustomerBeneficaryDTList) {
		this.coustomerBeneficaryDTList = coustomerBeneficaryDTList;
	}

	public List<CountryMasterDesc> getLstCountry() {
		return lstCountry;
	}

	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}

	public List<CountryMasterDesc> getNationalityList() {
		return nationalityList;
	}

	public void setNationalityList(List<CountryMasterDesc> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public List<CustomerIdProof> getCustomerDetailsList() {
		return customerDetailsList;
	}

	public void setCustomerDetailsList(List<CustomerIdProof> customerDetailsList) {
		this.customerDetailsList = customerDetailsList;
	}

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

	public String getCustomerIsActive() {
		return customerIsActive;
	}

	public void setCustomerIsActive(String customerIsActive) {
		this.customerIsActive = customerIsActive;
	}

	public Date getCustomerExpDate() {
		return customerExpDate;
	}

	public void setCustomerExpDate(Date customerExpDate) {
		this.customerExpDate = customerExpDate;
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

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	public Date getDateOfBrith() {
		return dateOfBrith;
	}

	public void setDateOfBrith(Date dateOfBrith) {
		this.dateOfBrith = dateOfBrith;
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

	public boolean isBooRenderOverseaCharges() {
		return booRenderOverseaCharges;
	}

	public void setBooRenderOverseaCharges(boolean booRenderOverseaCharges) {
		this.booRenderOverseaCharges = booRenderOverseaCharges;
	}

	public boolean isBooRenderLayaltyServicePanel() {
		return booRenderLayaltyServicePanel;
	}

	public void setBooRenderLayaltyServicePanel(boolean booRenderLayaltyServicePanel) {
		this.booRenderLayaltyServicePanel = booRenderLayaltyServicePanel;
	}

	public boolean isBooRenderBenificarySearchPanel() {
		return booRenderBenificarySearchPanel;
	}

	public void setBooRenderBenificarySearchPanel(boolean booRenderBenificarySearchPanel) {
		this.booRenderBenificarySearchPanel = booRenderBenificarySearchPanel;
	}

	public BigDecimal getMtcYear() {
		return mtcYear;
	}

	public void setMtcYear(BigDecimal mtcYear) {
		this.mtcYear = mtcYear;
	}

	public String getMtcNo() {
		return mtcNo;
	}

	public void setMtcNo(String mtcNo) {
		this.mtcNo = mtcNo;
	}

	public boolean isBooRenderBeniListPanel() {
		return booRenderBeniListPanel;
	}

	public void setBooRenderBeniListPanel(boolean booRenderBeniListPanel) {
		this.booRenderBeniListPanel = booRenderBeniListPanel;
	}

	public BigDecimal getBeneficaryStatusId() {
		return beneficaryStatusId;
	}

	public void setBeneficaryStatusId(BigDecimal beneficaryStatusId) {
		this.beneficaryStatusId = beneficaryStatusId;
	}

	public boolean isRenderBackButton() {
		return renderBackButton;
	}

	public void setRenderBackButton(boolean renderBackButton) {
		this.renderBackButton = renderBackButton;
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

	public ArrayList<ForeignLocalCurrencyDataTable> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<ForeignLocalCurrencyDataTable> lstData) {
		this.lstData = lstData;
	}

	public String getDenominationchecking() {
		return denominationchecking;
	}

	public void setDenominationchecking(String denominationchecking) {
		this.denominationchecking = denominationchecking;
	}

	public boolean getBooRendercashdenomination() {
		return booRendercashdenomination;
	}

	public void setBooRendercashdenomination(boolean booRendercashdenomination) {
		this.booRendercashdenomination = booRendercashdenomination;
	}

	public boolean isBoorefundPaymentDetails() {
		return boorefundPaymentDetails;
	}

	public void setBoorefundPaymentDetails(boolean boorefundPaymentDetails) {
		this.boorefundPaymentDetails = boorefundPaymentDetails;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstRefundData() {
		return lstRefundData;
	}

	public void setLstRefundData(ArrayList<ForeignLocalCurrencyDataTable> lstRefundData) {
		this.lstRefundData = lstRefundData;
	}

	public BigDecimal getDenomtotalCash() {
		return denomtotalCash;
	}

	public void setDenomtotalCash(BigDecimal denomtotalCash) {
		this.denomtotalCash = denomtotalCash;
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

	public BigDecimal getPayRefund() {
		return payRefund;
	}

	public void setPayRefund(BigDecimal payRefund) {
		this.payRefund = payRefund;
	}

	public int getTxnType() {
		return txnType;
	}

	public void setTxnType(int txnType) {
		this.txnType = txnType;
	}

	public boolean isBooTxnType() {
		return booTxnType;
	}

	public void setBooTxnType(boolean booTxnType) {
		this.booTxnType = booTxnType;
	}

	public BigDecimal getChequeValue() {
		return chequeValue;
	}

	public void setChequeValue(BigDecimal chequeValue) {
		this.chequeValue = chequeValue;
	}

	public BigDecimal getKnetValue() {
		return knetValue;
	}

	public void setKnetValue(BigDecimal knetValue) {
		this.knetValue = knetValue;
	}

	public BigDecimal getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}

	public BigDecimal getBeneSequenceId() {
		return beneSequenceId;
	}

	public void setBeneSequenceId(BigDecimal beneSequenceId) {
		this.beneSequenceId = beneSequenceId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getCollectedAmount() {
		return collectedAmount;
	}

	public void setCollectedAmount(BigDecimal collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public BigDecimal getToalUsedAmount() {
		return toalUsedAmount;
	}

	public void setToalUsedAmount(BigDecimal toalUsedAmount) {
		this.toalUsedAmount = toalUsedAmount;
	}

	public BigDecimal getRemittanceNo() {
		return remittanceNo;
	}

	public void setRemittanceNo(BigDecimal remittanceNo) {
		this.remittanceNo = remittanceNo;
	}

	public void setBooRenderBenificaryFirstPanel(boolean booRenderBenificaryFirstPanel) {
		this.booRenderBenificaryFirstPanel = booRenderBenificaryFirstPanel;
	}

	public void setBooRenderOldSmartCardPanel(boolean booRenderOldSmartCardPanel) {
		this.booRenderOldSmartCardPanel = booRenderOldSmartCardPanel;
	}

	public BigDecimal getColamountKWD() {
		return colamountKWD;
	}

	public void setColamountKWD(BigDecimal colamountKWD) {
		this.colamountKWD = colamountKWD;
	}

	public void setMainPanelRender(boolean mainPanelRender) {
		this.mainPanelRender = mainPanelRender;
	}

	public BigDecimal getDenomtotalCashreceived() {
		return denomtotalCashreceived;
	}

	public void setDenomtotalCashreceived(BigDecimal denomtotalCashreceived) {
		this.denomtotalCashreceived = denomtotalCashreceived;
	}

	public CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> getColdatatablevalues() {
		return coldatatablevalues;
	}

	public void setColdatatablevalues(CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
		this.coldatatablevalues = coldatatablevalues;
	}

	public BigDecimal getTempCash() {
		return tempCash;
	}

	public void setTempCash(BigDecimal tempCash) {
		this.tempCash = tempCash;
	}

	public BigDecimal getCalNetAmountPaid() {
		return calNetAmountPaid;
	}

	public void setCalNetAmountPaid(BigDecimal calNetAmountPaid) {
		this.calNetAmountPaid = calNetAmountPaid;
	}

	public BigDecimal getColCardNo() {
		return colCardNo;
	}

	public void setColCardNo(BigDecimal colCardNo) {
		this.colCardNo = colCardNo;
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

	public String getColCurrency() {
		return colCurrency;
	}

	public void setColCurrency(String colCurrency) {
		this.colCurrency = colCurrency;
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

	public boolean isBooRenderColDebitCard() {
		return booRenderColDebitCard;
	}

	public void setBooRenderColDebitCard(boolean booRenderColDebitCard) {
		this.booRenderColDebitCard = booRenderColDebitCard;
	}

	public boolean isBooRendercollectiondatatable() {
		return booRendercollectiondatatable;
	}

	public void setBooRendercollectiondatatable(boolean booRendercollectiondatatable) {
		this.booRendercollectiondatatable = booRendercollectiondatatable;
	}

	public BigDecimal getFcsaleNo() {
		return fcsaleNo;
	}

	public void setFcsaleNo(BigDecimal fcsaleNo) {
		this.fcsaleNo = fcsaleNo;
	}

	public boolean getBoorefundPaymentDetails() {
		return boorefundPaymentDetails;
	}

	public CopyOnWriteArrayList<ShoppingCartDataTableBean> getLstselectedrecord() {
		return lstselectedrecord;
	}

	public void setLstselectedrecord(CopyOnWriteArrayList<ShoppingCartDataTableBean> lstselectedrecord) {
		this.lstselectedrecord = lstselectedrecord;
	}

	public boolean isBooRenderSendReceivePanel() {
		return booRenderSendReceivePanel;
	}

	public void setBooRenderSendReceivePanel(boolean booRenderSendReceivePanel) {
		this.booRenderSendReceivePanel = booRenderSendReceivePanel;
	}

	public BigDecimal getPopulatedDebitCardNumber() {
		return populatedDebitCardNumber;
	}

	public void setPopulatedDebitCardNumber(BigDecimal populatedDebitCardNumber) {
		this.populatedDebitCardNumber = populatedDebitCardNumber;
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

	public List<PaymentModeDesc> getLstFetchAllPayMode() {
		return ipaymentService.fetchPaymodeDesc(
				new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"), Constants.Yes);
	}

	public void setLstFetchAllPayMode(List<PaymentModeDesc> lstFetchAllPayMode) {
		this.lstFetchAllPayMode = lstFetchAllPayMode;
	}

	public BigDecimal getSendDocumentNo() {
		return sendDocumentNo;
	}

	public void setSendDocumentNo(BigDecimal sendDocumentNo) {
		this.sendDocumentNo = sendDocumentNo;
	}

	public BigDecimal getReceiveDocumentNo() {
		return receiveDocumentNo;
	}

	public void setReceiveDocumentNo(BigDecimal receiveDocumentNo) {
		this.receiveDocumentNo = receiveDocumentNo;
	}

	public BigDecimal getSendDocumentCode() {
		return sendDocumentCode;
	}

	public void setSendDocumentCode(BigDecimal sendDocumentCode) {
		this.sendDocumentCode = sendDocumentCode;
	}

	public BigDecimal getSendCompanyCode() {
		return sendCompanyCode;
	}

	public void setSendCompanyCode(BigDecimal sendCompanyCode) {
		this.sendCompanyCode = sendCompanyCode;
	}

	public BigDecimal getSendDocumentFinanceYear() {
		return sendDocumentFinanceYear;
	}

	public void setSendDocumentFinanceYear(BigDecimal sendDocumentFinanceYear) {
		this.sendDocumentFinanceYear = sendDocumentFinanceYear;
	}

	public boolean isBooSaveRender() {
		return booSaveRender;
	}

	public void setBooSaveRender(boolean booSaveRender) {
		this.booSaveRender = booSaveRender;
	}

	public boolean isBooSaveDenomRender() {
		return booSaveDenomRender;
	}

	public void setBooSaveDenomRender(boolean booSaveDenomRender) {
		this.booSaveDenomRender = booSaveDenomRender;
	}

	public BigDecimal getReceiveDocumentCode() {
		return receiveDocumentCode;
	}

	public void setReceiveDocumentCode(BigDecimal receiveDocumentCode) {
		this.receiveDocumentCode = receiveDocumentCode;
	}

	public BigDecimal getReceiveCompanyCode() {
		return receiveCompanyCode;
	}

	public void setReceiveCompanyCode(BigDecimal receiveCompanyCode) {
		this.receiveCompanyCode = receiveCompanyCode;
	}

	public BigDecimal getReceiveDocumentFinanceYear() {
		return receiveDocumentFinanceYear;
	}

	public void setReceiveDocumentFinanceYear(BigDecimal receiveDocumentFinanceYear) {
		this.receiveDocumentFinanceYear = receiveDocumentFinanceYear;
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
			if (financialYearList != null) {
				financeYear = financialYearList.get(0).getFinancialYear();
				setFinanceYear(financeYear);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return financeYear;
	}

	public void setFinanceYear(BigDecimal financeYear) {
		this.financeYear = financeYear;
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

	public List<ShoppingCartDataTableBean> getShoppingcartDTList() {
		return shoppingcartDTList;
	}

	public void setShoppingcartDTList(List<ShoppingCartDataTableBean> shoppingcartDTList) {
		this.shoppingcartDTList = shoppingcartDTList;
	}

	public BigDecimal getFinanceYear() {
		return financeYear;
	}

	public String getNextOrSaveButton() {
		return nextOrSaveButton;
	}

	public void setNextOrSaveButton(String nextOrSaveButton) {
		this.nextOrSaveButton = nextOrSaveButton;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public boolean isBooRenderPaymentDetails() {
		return booRenderPaymentDetails;
	}

	public void setBooRenderPaymentDetails(boolean booRenderPaymentDetails) {
		this.booRenderPaymentDetails = booRenderPaymentDetails;
	}

	public boolean isBooSaveAll() {
		return booSaveAll;
	}

	public void setBooSaveAll(boolean booSaveAll) {
		this.booSaveAll = booSaveAll;
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

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public String getProcedureError() {
		return procedureError;
	}

	public void setProcedureError(String procedureError) {
		this.procedureError = procedureError;
	}

	public List<WesternUnionBeneficaryDataTable> getSelectedWUBeneList() {
		return selectedWUBeneList;
	}

	public void setSelectedWUBeneList(List<WesternUnionBeneficaryDataTable> selectedWUBeneList) {
		this.selectedWUBeneList = selectedWUBeneList;
	}

	public boolean getIsWUSend() {
		return isWUSend;
	}

	public void setIsWUSend(boolean isWUSend) {
		this.isWUSend = isWUSend;
	}

	public WesternUnionBeneficaryDataTable getSelectedValues() {
		return selectedValues;
	}

	public void setSelectedValues(WesternUnionBeneficaryDataTable selectedValues) {
		this.selectedValues = selectedValues;
	}

	public boolean isWUStatus() {
		return isWUStatus;
	}

	public void setWUStatus(boolean isWUStatus) {
		this.isWUStatus = isWUStatus;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isBooCheckStatusPanel() {
		return booCheckStatusPanel;
	}

	public void setBooCheckStatusPanel(boolean booCheckStatusPanel) {
		this.booCheckStatusPanel = booCheckStatusPanel;
	}

	public BigDecimal getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(BigDecimal collectionId) {
		this.collectionId = collectionId;
	}

	public BigDecimal getCollectionDocumentNo() {
		return collectionDocumentNo;
	}

	public void setCollectionDocumentNo(BigDecimal collectionDocumentNo) {
		this.collectionDocumentNo = collectionDocumentNo;
	}

	public ForeignLocalCurrencyDataTable getDataTableClear() {
		return dataTableClear;
	}

	public void setDataTableClear(ForeignLocalCurrencyDataTable dataTableClear) {
		this.dataTableClear = dataTableClear;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getWucheckRecCustomer() {
		return wucheckRecCustomer;
	}

	public void setWucheckRecCustomer(BigDecimal wucheckRecCustomer) {
		this.wucheckRecCustomer = wucheckRecCustomer;
	}

	public List<PurposeOfTransaction> getLstPurposeOfTransaction() {
		return lstPurposeOfTransaction;
	}
	
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public void setLstPurposeOfTransaction(List<PurposeOfTransaction> lstPurposeOfTransaction) {
		this.lstPurposeOfTransaction = lstPurposeOfTransaction;
	}

	public List<SourceOfIncome> getLstSourceOfIncome() {
		return lstSourceOfIncome;
	}

	public void setLstSourceOfIncome(List<SourceOfIncome> lstSourceOfIncome) {
		this.lstSourceOfIncome = lstSourceOfIncome;
	}

	public String getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public String getPurposeOfTransactions() {
		return purposeOfTransactions;
	}

	public void setPurposeOfTransactions(String purposeOfTransactions) {
		this.purposeOfTransactions = purposeOfTransactions;
	}

	public List<SourceOfIncomeDescription> getLstSourceOfIncomes() {
		return lstSourceOfIncomes;
	}

	public void setLstSourceOfIncomes(List<SourceOfIncomeDescription> lstSourceOfIncomes) {
		this.lstSourceOfIncomes = lstSourceOfIncomes;
	}

	public List<PurposeOfTransaction> getLstPurposeOfTransactions() {
		return lstPurposeOfTransactions;
	}

	public void setLstPurposeOfTransactions(List<PurposeOfTransaction> lstPurposeOfTransactions) {
		this.lstPurposeOfTransactions = lstPurposeOfTransactions;
	}

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}

	public BigDecimal getRelationId() {
		return relationId;
	}

	public void setRelationId(BigDecimal relationId) {
		this.relationId = relationId;
	}

	public boolean isBooSourceEnable() {
		return booSourceEnable;
	}

	public void setBooSourceEnable(boolean booSourceEnable) {
		this.booSourceEnable = booSourceEnable;
	}

	public boolean isBooPurposeEnable() {
		return booPurposeEnable;
	}

	public void setBooPurposeEnable(boolean booPurposeEnable) {
		this.booPurposeEnable = booPurposeEnable;
	}

	public boolean isBooMtcEnable() {
		return booMtcEnable;
	}

	public void setBooMtcEnable(boolean booMtcEnable) {
		this.booMtcEnable = booMtcEnable;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	private List<PopulateData> allBeneCountryList = new ArrayList<PopulateData>();
	private BigDecimal beneficiaryCountryId;
	private Boolean renderBeneCountry = false;

	public List<PopulateData> getAllBeneCountryList() {
		return allBeneCountryList;
	}

	public void setAllBeneCountryList(List<PopulateData> allBeneCountryList) {
		this.allBeneCountryList = allBeneCountryList;
	}

	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}

	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}

	public Boolean getRenderBeneCountry() {
		return renderBeneCountry;
	}

	public void setRenderBeneCountry(Boolean renderBeneCountry) {
		this.renderBeneCountry = renderBeneCountry;
	}

	public void populateBeneFiciaryCountry() {
		try {
			if (allBeneCountryList != null || !allBeneCountryList.isEmpty()) {
				allBeneCountryList.clear();
			}

			if (coustomerBeneficaryDTList != null || !coustomerBeneficaryDTList.isEmpty()) {
				coustomerBeneficaryDTList.clear();
			}

			List<PopulateData> countryList = iPersonalRemittanceService.getBeneficaryList(getCustomerNo());

			if (countryList != null && countryList.size() > 1) {
				allBeneCountryList.addAll(countryList);
				if (getBeneficiaryCountryId() != null) {
					populateCustomerDetailsFromBeneRelation();
				}
			} else if (countryList != null && countryList.size() == 1) {
				allBeneCountryList.addAll(countryList);
				PopulateData populateData = countryList.get(0);
				setBeneficiaryCountryId(populateData.getPopulateId());
				populateCustomerDetailsFromBeneRelation();
			}

		} catch (Exception e) {
			setProcedureError(e.getMessage());
			RequestContext.getCurrentInstance().execute("functionErr.show();");
		}

	}

	private List<ViewBeneServiceCurrency> beneServiceCurrencyList = new ArrayList<ViewBeneServiceCurrency>();
	private BigDecimal benifisCountryId;
	private BigDecimal benifisCurrencyId;
	private WesternUnionBeneficaryDataTable selectWesternUnionBeneRec = new WesternUnionBeneficaryDataTable();

	public List<ViewBeneServiceCurrency> getBeneServiceCurrencyList() {
		return beneServiceCurrencyList;
	}

	public void setBeneServiceCurrencyList(List<ViewBeneServiceCurrency> beneServiceCurrencyList) {
		this.beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(getBenifisCountryId());
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

	public boolean isBooDenoEnable() {
		return booDenoEnable;
	}

	public void setBooDenoEnable(boolean booDenoEnable) {
		this.booDenoEnable = booDenoEnable;
	}

	public WesternUnionBeneficaryDataTable getSelectWesternUnionBeneRec() {
		return selectWesternUnionBeneRec;
	}

	public void setSelectWesternUnionBeneRec(WesternUnionBeneficaryDataTable selectWesternUnionBeneRec) {
		this.selectWesternUnionBeneRec = selectWesternUnionBeneRec;
	}

	public void differentCurrencyDetails(WesternUnionBeneficaryDataTable beneAccountCurrencyDetails) {
		System.out.println(beneAccountCurrencyDetails);
		setSelectWesternUnionBeneRec(beneAccountCurrencyDetails);
		setBenifisCountryId(null);
		setBenifisCurrencyId(null);
		if (beneServiceCurrencyList != null || !beneServiceCurrencyList.isEmpty()) {
			beneServiceCurrencyList.clear();
		}
		lstCountry = generalService.getCountryList(sessionStateManage.getLanguageId()); // beneficiary
		// bank
		// country
		// list
		RequestContext.getCurrentInstance().execute("beneCurrencyPanel.show();");
	}

	public void benServiceCurrencyListDetails(AjaxBehaviorEvent event) {
		setBenifisCurrencyId(null);
		if (getBenifisCountryId() != null) {
			beneServiceCurrencyList = iPersonalRemittanceService.getViewBeneCurrency(getBenifisCountryId()); // beneficiary
			// bank
			// country
			// currency
			// list
		}
	}

	public void differentCurrencyData() {
		try {
			BigDecimal dbankId = null;
			BigDecimal dbankBranchId = null;
			String dbankCode = null;
			BigDecimal dbankBranchCode = null;

			WesternUnionBeneficaryDataTable westerUnionSelected = getSelectWesternUnionBeneRec();
			// checking bank_country , bank_id , bank_branch_id , currency_id
			// with master_seq_id
			if (getBenifisCountryId() != null && getBenifisCurrencyId() != null && getSelectWesternUnionBeneRec() != null) {

				List<BankMaster> bankMasterdetails = bankMasterService.getBankMasterInfo(Constants.WU_BANK_CODE);

				if (bankMasterdetails != null && bankMasterdetails.size() != 0) {

					BankMaster bankMasterId = bankMasterdetails.get(0);

					if (bankMasterId.getBankId() != null) {
						dbankId = bankMasterId.getBankId(); // setting bankID
						dbankCode = Constants.WU_BANK_CODE; // western union
						// bank code

						List<BankBranch> bankBranchdetails = bankBranchDetailsService.getBranchCodebyBank(bankMasterId.getBankId());

						if (bankBranchdetails != null && bankBranchdetails.size() != 0) {

							BankBranch bankBranchId = bankBranchdetails.get(0);

							if (bankBranchId.getBankBranchId() != null) {
								dbankBranchId = bankBranchId.getBankBranchId();
								dbankBranchCode = bankBranchId.getBranchCode(); // western
								// union
								// bank
								// branch
								// code
							}
						}
					}
				}

				if (getBenifisCountryId() != null && dbankId != null && dbankBranchId != null && getBenifisCurrencyId() != null
						&& westerUnionSelected.getBeneficaryMasterSeqId() != null) {
					boolean checkWesternUnionCountryCurrency = westernUnionService.checkBeneficaryAccountDetailsForWUnion(getBenifisCountryId(),
							dbankId, dbankBranchId, getBenifisCurrencyId(), westerUnionSelected.getBeneficaryMasterSeqId());
					if (checkWesternUnionCountryCurrency) {
						// record already Available
						setErrmsg("BanK Country and Currency already Available for Beneficary");
						RequestContext.getCurrentInstance().execute("csp.show();");
						return;
					} else {
						// insert to database
						saveBeneficiaryAccountDetails(getSelectWesternUnionBeneRec(), getBenifisCountryId(), getBenifisCurrencyId());
						populateBeneFiciaryCountry();
						setBeneficiaryCountryId(getBenifisCountryId());
						// populateCustomerDetailsFromBeneRelation();
						setBenifisCountryId(null);
						setBenifisCurrencyId(null);
						if (getSelectWesternUnionBeneRec() != null) {
							setSelectWesternUnionBeneRec(null);
						}
					}
				} else {
					setErrmsg("BanK Country and Currency and Selected Beneficiary are Null");
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}

			} else {
				// details from Dialogue box not correctly updated.
			}
		} catch (Exception e) {
			setBenifisCountryId(null);
			setBenifisCurrencyId(null);
			if (getSelectWesternUnionBeneRec() != null) {
				setSelectWesternUnionBeneRec(null);
			}
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg(e.getMessage());
			return;
		}

	}

	public void resetFilters(String clearDataTable) {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(clearDataTable);
		if (dataTable != null) {
			dataTable.reset();
		}
	}

	// search operation in Personal remittance
	public void searchClicked() {
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("request", "wuBean");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}
	}


	//private List<WesternUnionBeneficaryDataTable> deleteBeneRelationList = new ArrayList<WesternUnionBeneficaryDataTable>();

	/*public List<WesternUnionBeneficaryDataTable> getDeleteBeneRelationList() {
		return deleteBeneRelationList;
	}

	public void setDeleteBeneRelationList(List<WesternUnionBeneficaryDataTable> deleteBeneRelationList) {
		this.deleteBeneRelationList = deleteBeneRelationList;
	}*/
	
	private BigDecimal deleteBeneRelationId;
	
	public BigDecimal getDeleteBeneRelationId() {
		return deleteBeneRelationId;
	}
	public void setDeleteBeneRelationId(BigDecimal deleteBeneRelationId) {
		this.deleteBeneRelationId = deleteBeneRelationId;
	}

	public void deleteBeneRelationIsActive(WesternUnionBeneficaryDataTable deletebene){
		
		setDeleteBeneRelationId(null);
		
		List<BeneficaryAccount> beneAccDt = beneficaryCreation.getBeneAccountDetails(deletebene.getBeneficaryMasterSeqId(), deletebene.getCurrencyId(), Constants.WU_BANK_CODE);
		
		// only western union records to deleted not the banking channel or cash product
		if(beneAccDt != null && beneAccDt.size() != 0){
			BigDecimal beneAccountSeqId = beneAccDt.get(0).getBeneficaryAccountSeqId();
			
			List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices.checkBenificaryRelationExist(deletebene.getBeneficaryMasterSeqId(), beneAccountSeqId);
			if(lstBeneRelationShip.size() == 1){
				
				setDeleteBeneRelationId(lstBeneRelationShip.get(0).getBeneficaryRelationshipId());
				setExceptionMessage("Do you want to delete Western Union Beneficiary Account?");
				RequestContext.getCurrentInstance().execute("deleteBeneAcc.show();");
				
			}
			
			setDeleteBeneRelationId(lstBeneRelationShip.get(0).getBeneficaryRelationshipId());
			
		}else {
			setErrmsg("Beneficiary Doesn't Belong to WESTERN UNION");
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		
	}



	public void deleteBeneficiaryAccount(){
		if(getDeleteBeneRelationId() != null){
			BigDecimal deletebeneRel = getDeleteBeneRelationId();
			if(deletebeneRel != null){
				String status = Constants.D;
				//iPersonalRemittanceService.deleteBeneAccountRecord(deletebene.getBeneficiaryAccountSeqId() ,deletebene.getBeneficiaryRelationShipSeqId(),status);
				beneficaryCreation.deleteBeneRelationRecord(deletebeneRel,status);
				if(getBeneficiaryCountryId()!=null){
					//populateCustomerDetailsFromBeneRelation();
					populateBeneFiciaryCountry();
				}
			}
		}
	}


	public void editWUBenificary(WesternUnionBeneficaryDataTable datatabledetails) {

		try{

			log.info("Exit into edit method ");
			
			// checking beneficiary Account for that master and currency and western union code
			List<BeneficaryAccount> beneAccDt = beneficaryCreation.getBeneAccountDetails(datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getCurrencyId(), Constants.WU_BANK_CODE);
			
			// only western union records to deleted not the banking channel or cash product
			if(beneAccDt != null && beneAccDt.size() != 0){
				BigDecimal beneAccountSeqId = beneAccDt.get(0).getBeneficaryAccountSeqId();
				
				// update if deleted D to activate Y
				List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices.checkBenificaryRelationExist(datatabledetails.getBeneficaryMasterSeqId(), beneAccountSeqId);
				if(lstBeneRelationShip.size() != 0){

					BeneficaryRelationship beneRel = lstBeneRelationShip.get(0);

					BigDecimal beneRelationShipId = beneRel.getBeneficaryRelationshipId();

					if(!beneRel.getIsActive().equalsIgnoreCase(Constants.Yes)){
						String status = Constants.Yes;
						iPersonalRemittanceService.deleteBeneAccountRecord(beneAccountSeqId ,beneRelationShipId,status);
					}
				}
				
				PersonalRemmitanceBeneficaryDataTable pdatatabledetails = populateCustomerBeneDetails(datatabledetails,beneAccountSeqId);
				fetchAllRecordForNonTrnx(pdatatabledetails);
			}else {
				setErrmsg("Beneficiary Doesn't Belong to WESTERN UNION");
				RequestContext.getCurrentInstance().execute("csp.show();");
			}

			/*BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean)appContext.getBean("beneficiaryCreationBean");
			//	objectBene.setDataTableBeneObj(datatabledetails);

			objectBene.setDataTableBeneWUObj(datatabledetails);
			objectBene.renderBeneficiaryFullWUEditNavigation();
			context.redirect("../beneficary/firsttimebeneficarywuedit.xhtml");*/

		}catch (Exception e) {
			setProcedureError(e.getMessage());
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
		}

	}
	
	// Second Method to Populate Records which is Approved all condition
	public PersonalRemmitanceBeneficaryDataTable populateCustomerBeneDetails(WesternUnionBeneficaryDataTable datatabledetails,BigDecimal beneAccountSeqId) {
		resetFilters("form1:dataTable");

		log.info("Entering into populateCustomerBeneDetails method ");
		
		PersonalRemmitanceBeneficaryDataTable personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();
		
		try{
			
			log.info( "=====================getCustomerNo()"+getCustomerNo()+" : getBeneficiaryCountryId() : "+getBeneficiaryCountryId());
			List<BenificiaryListView> isCoustomerExist = iPersonalRemittanceService.getBeneficiaryDtFromView(datatabledetails.getCustomerId(),datatabledetails.getCountryId(),datatabledetails.getBeneficaryMasterSeqId(), datatabledetails.getCurrencyId(), Constants.WU_BANK_CODE,beneAccountSeqId);
			log.info( "====================="+isCoustomerExist.size());

			if (isCoustomerExist.size() == 1) {

				BenificiaryListView rel =  isCoustomerExist.get(0);
				
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
				
				//For swift  beneficiary 
				personalRBDataTable.setSwiftBic(rel.getSwiftBic());
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

				personalRBDataTable.setStateId(rel.getStateId());
				personalRBDataTable.setDistrictId(rel.getDistrictId());
				personalRBDataTable.setCityId(rel.getCityId());
				personalRBDataTable.setBankAccountTypeId(rel.getBankAccountTypeId());

			
			} else if(isCoustomerExist.size() > 1){
				// more than one record
			}else{
				// no records
			}

		}catch(Exception e){
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		log.info("Exit into populateCustomerDetailsFromBeneRelation method ");
		return personalRBDataTable;
	}


	// full edit
	public void fetchAllRecordForNonTrnx(PersonalRemmitanceBeneficaryDataTable datatabledetails){
		try {
			BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean)appContext.getBean("beneficiaryCreationBean");
			//objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);
			objectBene.setBooRenderWesterUnion(true);
			objectBene.setBooRenderWesterUnionUpload(false);
			objectBene.setBooRenderBeneficaryCreation(false);
			objectBene.setDataTableBeneObj(datatabledetails);
			objectBene.renderBeneficiaryFullEditNavigation();
			
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../beneficary/firsttimebeneficaryedit.xhtml");
		} catch (IOException e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

}
