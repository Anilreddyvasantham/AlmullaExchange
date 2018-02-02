package com.amg.exchange.intercompany.bean;

import java.io.BufferedReader;
import java.io.IOException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.bean.ForeignLocalCurrencyDataTable;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.intercompany.model.IntraTrnxModel;
import com.amg.exchange.intercompany.service.IntraCompanyService;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.miscellaneous.service.IMiscellaneousReceiptPaymentService;
import com.amg.exchange.registration.bean.CustomerPersonalInfoBean;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.bean.PersonalRemmitanceBeneficaryDataTable;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;

/**
 * @author Chiranjeevi
 * 
 */
@Component("intraCompanyTrnxBean")
@Scope("session")
public class IntraCompanyTrnxBean<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(IntraCompanyTrnxBean.class);

	// variables

	// panel 1 Variables
	private Boolean booRenderIdDetails;
	private boolean booRenderOldSmartCardPanel = false;
	private int selectCardType;
	private BigDecimal selectCard;
	private String idNumber;
	private String errmsg;

	// customer details variables
	private String customerName;
	private String customerCrNumber;
	private BigDecimal customerId;
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
	private Date dateOfBrith;
	private String birthdate;
	private String countryCode;
	private String mcountryCode;
	private String occupation;
	private BigDecimal loyaltyPoints;

	// panel 2 Variables
	private Boolean booRenderPinDetails;
	private String pinNo;
	private Date documentDate;
	private BigDecimal beneficiaryId;
	private String beneficiaryName;
	private String beneficiaryNameLocal;
	private String nationalityName;
	private BigDecimal nationalityId;
	private String beneCountryName;
	private BigDecimal beneCountryId;
	private String beneStateName;
	private BigDecimal beneStateId;
	private String beneDistrictName;
	private BigDecimal beneDistrictId;
	private String beneCityName;
	private BigDecimal beneCityId;
	private String countryTelCode;
	private String telephoneNumber;
	private BigDecimal mobileNumber;
	private String currencyName;
	private BigDecimal amount;
	private String senderCountryName;
	private BigDecimal senderCountryId;
	private String senderName;
	private String senderNameLocal;
	private String remarks;

	// panel 3 Variables
	private Boolean booRefundPaymentDetails;
	private Boolean booRenderPaymentDetails;
	private Boolean booRenderCollectionDetails;
	private String denominationchecking;
	private BigDecimal totalAmountPaid;
	private BigDecimal amountToPay;
	private BigDecimal denomtotalCash;
	private String paymentDetailsremark;
	private BigDecimal paidAmount;
	private BigDecimal payPaidAmount;
	private BigDecimal payNetPaidAmount;
	private BigDecimal collectedAmount;
	private BigDecimal totalcollectedAmount;
	private String saveOrnext;

	// extra variables
	private BigDecimal documentYear=null;
	private BigDecimal documentYearId=null;
	private String receiptpayment;

	// Auto wired
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	ApplicationContext appContext;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ILoginService<T> loginService;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	@Autowired
	IMiscellaneousReceiptPaymentService<T> miscellaneousReceiptPaymentService;
	@Autowired
	IntraCompanyService intraCompanyService;

	// list
	private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private List<PersonalRemmitanceBeneficaryDataTable> customerBeneficaryDTList = new ArrayList<PersonalRemmitanceBeneficaryDataTable>();
	private List<CustomerIdProof> customerDetailsList = new ArrayList<CustomerIdProof>();
	private List<PopulateData> allBeneCountryList=new ArrayList<PopulateData>();
	private Map<BigDecimal, String> mapcomIdentityType = new HashMap<BigDecimal, String>();
	private HashMap<String, Object> savingMap = new HashMap<String, Object>();

	// page navigation
	public void pageNavigation(){
		try {
			// panel 1
			setBooRenderIdDetails(true);
			setBooRenderOldSmartCardPanel(false);
			// panel 2
			setBooRenderPinDetails(false);
			// panel 3
			setBooRenderPaymentDetails(false);
			setBooRenderPaymentDetails(false);
			setBooRenderCollectionDetails(false);

			setIdNumber(null);
			setSelectCardType(0);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/IntraCompanyTrnx.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void loadIdType() {
		mapcomIdentityType = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), "I","Identity Type");
	}

	// first panel smart or manual
	public void showCardTypePanel() throws Exception {
		int typecard = getSelectCardType();
		if (typecard == 2) {
			setBooRenderIdDetails(true);
			setBooRenderOldSmartCardPanel(true);
			BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVILID,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
			if(idtypeCivilIdnew !=null){
				setSelectCard(idtypeCivilIdnew);
			}
		} else if (typecard == 1) {
			fetchSmartCardIdNumber();
			if (getIdNumber() != null && getSelectCard() != null) {
				if(customerBeneficaryDTList != null && !customerBeneficaryDTList.isEmpty()){
					customerBeneficaryDTList.clear();
				}
				goFromOldSmartCardpanel();
				setBooRenderIdDetails(false);
				setBooRenderOldSmartCardPanel(false);
			} else {
				setSelectCardType(0);
				setBooRenderOldSmartCardPanel(false);
				RequestContext.getCurrentInstance().execute("dldInserCard.show();");
			}
		}
	}

	// smart card for kuwait
	public void fetchSmartCardIdNumber() throws ParseException {
		if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			smartCardDisplay(ipAddress, "8085", "M", "test");
		}
	}

	// smart card display from machine
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

	// redirecting to customer page
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

	// search operation in Personal remittance
	public void searchClicked() {
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("request", "interCompanyTrnx");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}
	}

	// on click of go button
	public void onClickGoButton(){
		if(customerDetailsList != null || !customerDetailsList.isEmpty()){
			customerDetailsList.clear();
		}

		try {
			goFromOldSmartCardpanel();
		} catch (ParseException e) {
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// first method after go clicked to fetch all customer details
	public void goFromOldSmartCardpanel() throws ParseException {

		log.info("Entering into goFromOldSmartCardpanel method");

		// clear
		clearCustomerDetails();

		if(getIdNumber() != null && !getIdNumber().equalsIgnoreCase("")){
			// clearing data table
			resetFilters("form1:dataTable");

			if(customerBeneficaryDTList != null || !customerBeneficaryDTList.isEmpty()){
				customerBeneficaryDTList.clear();
			}

			if(getSelectCard() != null){

				customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber().toUpperCase(), getSelectCard());

				if(customerDetailsList.size() != 0){
					CustomerIdProof customerDetails = customerDetailsList.get(0);
					fetchCustomerBeneficiaryDetails(customerDetails);
				}else{
					// comparing with civil id
					BigDecimal identityTpeIds = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();

					if(getSelectCard().compareTo(identityTpeIds)!=0){
						//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), identityTpeIds);
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), identityTpeIds);
						if(customerDetailsList.size() != 0){
							CustomerIdProof customerDetails = customerDetailsList.get(0);
							fetchCustomerBeneficiaryDetails(customerDetails);
						}else{
							// comparing with civil id new
							BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
							//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber(), idtypeCivilIdnew);
							customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idtypeCivilIdnew);
							if(customerDetailsList.size() != 0){
								CustomerIdProof customerDetails = customerDetailsList.get(0);
								fetchCustomerBeneficiaryDetails(customerDetails);
							}else{
								// failed all conditions
								//setCardType(null);
								//setIdNumber(null);
								setBooRenderIdDetails(true);
								RequestContext.getCurrentInstance().execute("idNotFound.show();");
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
						}else{
							// failed all conditions
							//setCardType(null);
							//setIdNumber(null);
							setBooRenderIdDetails(true);
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

	// redirecting to customer from Inter Company 
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

	// calling remittance amount page
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
				userType ="BRANCH"; 
			}

			CustomerIdProof customerDetails = fetchedcustomerDetails;

			if(customerDetails.getFsCustomer() != null){
				setCustomerId(customerDetails.getFsCustomer().getCustomerId());

				//v$session update
				loginService.killUserSession(sessionStateManage.getUserName()+"-"+customerDetails.getFsCustomer().getCustomerId()+"-R");
			}
			if(customerDetails.getIdentityExpiryDate() != null){
				setCustomerExpDate(customerDetails.getIdentityExpiryDate());
			}
			if(getCustomerExpDate() != null){
				setCustomerExpireDateMsg(new SimpleDateFormat("dd/MM/yyyy").format(getCustomerExpDate()));
			}

			log.info("getCustomerNo() :"+getCustomerId()+"\t sessionStateManage.getCountryId():"+sessionStateManage.getCountryId()+"\t sessionStateManage.getUserName() :"+sessionStateManage.getUserName());
			HashMap<String,String> customerValiMessage = iPersonalRemittanceService.getValidateCustomerProcedure(sessionStateManage.getCountryId(), getCustomerId(),sessionStateManage.getUserName(),userType);
			log.info("customerValiMessage :"+customerValiMessage);
			log.info("INDICATOR===="+customerValiMessage.get( "INDICATOR"));

			if(customerValiMessage.get( "ERROR_MESSAGE")!=null){
				setErrmsg(customerValiMessage.get( "ERROR_MESSAGE"));
				RequestContext.getCurrentInstance().execute("expiredCustomer.show();");
				return;
			}else if(customerValiMessage!=null&&customerValiMessage.get( "INDICATOR")!=null ){
				setErrmsg(customerValiMessage.get( "ERROR_MESSAGE"));
				//setCardType(null);
				if(customerValiMessage.get("INDICATOR").equalsIgnoreCase( Constants.Yes)){
					RequestContext.getCurrentInstance().execute("customerregproceed.show();");
				}
			}else{

				// clearing bene country list and country id
				if(allBeneCountryList != null || !allBeneCountryList.isEmpty()){
					allBeneCountryList.clear();
				}

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
							clearPanelBeneDetails();

							// first Panel
							setBooRenderIdDetails(false);
							setBooRenderOldSmartCardPanel(false);
							// second Panel
							setBooRenderPinDetails(true);
							// third Panel
							setBooRenderPaymentDetails(false);
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
								clearPanelBeneDetails();

								// first Panel
								setBooRenderIdDetails(false);
								setBooRenderOldSmartCardPanel(false);
								// second Panel
								setBooRenderPinDetails(true);
								// third Panel
								setBooRenderPaymentDetails(false);
							}
						}else{
							clearPanelBeneDetails();

							// first Panel
							setBooRenderIdDetails(false);
							setBooRenderOldSmartCardPanel(false);
							// second Panel
							setBooRenderPinDetails(true);
							// third Panel
							setBooRenderPaymentDetails(false);
						}
					}


				}else{
					//setCardType(null);
					setBooRenderIdDetails(true);
					RequestContext.getCurrentInstance().execute("activatecustomer.show();");
					return;
				}
			}

		} catch (Exception e) {
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;
		}

	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	// to fetch all Customer details
	public void getCustomerDetails() {

		try{
			if (customerDetailsList.size() != 0) {

				CustomerIdProof customerDetails = customerDetailsList.get(0);

				setSelectCard(customerDetails.getFsBizComponentDataByIdentityTypeId().getComponentDataId());
				setIdNumber(customerDetails.getIdentityInt());
				setCustomerName(customerDetails.getFsCustomer().getFirstName());
				setCustomerCrNumber(customerDetails.getFsCustomer().getCrNo() == null ? "" : customerDetails.getFsCustomer().getCrNo());
				setCustomerId(customerDetails.getFsCustomer().getCustomerId());
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
				setDateOfBrith(customerDetails.getFsCustomer().getDateOfBirth());
				String teleCountryId = generalService.getTelephoneCountryBasedOnNationality(customerDetails.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
				setCountryCode(teleCountryId);
				setMcountryCode(teleCountryId);
				BigDecimal occupationID = generalService.getOccupationId(customerDetails.getFsCustomer().getCustomerId());
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
			}
		}catch(Exception e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		log.info("Exit into getCustomerDetails method ");
	}

	// populate records from VW_REMITTANCE_TRANSACTION based on Pin Number
	public void populateRemittanceDetails(){

		//List<RemittanceApplicationView> remittanceTrnxlist = iPersonalRemittanceService.fetchRemitTrnxViewByPin(getPinNo());
		List<IntraTrnxModel> remittanceTrnxlist = iPersonalRemittanceService.fetchRemitTrnxByPin(getPinNo());

		if(remittanceTrnxlist.size() == 1){

			IntraTrnxModel remitTrnxDt = remittanceTrnxlist.get(0);

			setDocumentDate(remitTrnxDt.getSendDocumentDate());

			setBeneficiaryName(nullCheck(remitTrnxDt.getRecvFirstName())+ " " +nullCheck(remitTrnxDt.getRecvSecondName())+ " " +
					nullCheck(remitTrnxDt.getRecvThirdName())+ " " +nullCheck(remitTrnxDt.getRecvFourthName())+ " " +
					nullCheck(remitTrnxDt.getRecvFifthName()));
			setBeneficiaryNameLocal(nullCheck(remitTrnxDt.getRecvFirstNameLocal())+ " " +nullCheck(remitTrnxDt.getRecvSecondNameLocal())+ " " +
					nullCheck(remitTrnxDt.getRecvThirdNameLocal())+ " " +nullCheck(remitTrnxDt.getRecvFourthNameLocal())+ " " +
					nullCheck(remitTrnxDt.getRecvFifthNameLocal()));
			setBeneficiaryId(remitTrnxDt.getBeneficiaryId());

			// fetch bene country name
			setBeneCountryName(remitTrnxDt.getRecvCountryName());
			setBeneStateName(remitTrnxDt.getRecvStateName());
			setBeneDistrictName(remitTrnxDt.getRecvDistrictName());
			setBeneCityName(remitTrnxDt.getRecvCityName());
			setCountryTelCode(remitTrnxDt.getRecvCountryTelCode());
			setTelephoneNumber(remitTrnxDt.getRecvTelephoneNumber());
			setMobileNumber(remitTrnxDt.getRecvMobileNumber());
			setAmount(remitTrnxDt.getForeignTrnxAmount());
			setCurrencyName(remitTrnxDt.getForeignCurrencyName());

			// fetch bene country name
			setSenderCountryName(remitTrnxDt.getSendCountryName());
			setSenderName(nullCheck(remitTrnxDt.getSendFirstName())+ " " +nullCheck(remitTrnxDt.getSendSecondName())+ " " +
					nullCheck(remitTrnxDt.getSendThirdName()));
			setSenderNameLocal(nullCheck(remitTrnxDt.getSendFirstNameLocal())+ " " +nullCheck(remitTrnxDt.getSendSecondNameLocal())+ " " +
					nullCheck(remitTrnxDt.getSendThirdNameLocal()));

		}else if(remittanceTrnxlist.size() > 1){
			// multiple records not possible
			clearPanelBeneDetails();
			setErrmsg("MULTIPLE RECORDS FOUND FOR SAME PIN NUMBER");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}else{
			// no records found
			setErrmsg("NO RECORDS FOUND");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	//  Next button
	public void nextButtonToPayDenomination(){
		setBooRenderIdDetails(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderPinDetails(false);
		setBooRenderPaymentDetails(true);
		setSaveOrnext(Constants.Save);
		refundDenominationData();
		setPaidAmount(BigDecimal.ZERO);
		setPayNetPaidAmount(getAmount());
		setCollectedAmount(BigDecimal.ZERO);
		setAmountToPay(getAmount());
	}

	// refund denomination details
	public void refundDenominationData() {
		lstRefundData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		/* Checking that it's first time or not, first time list size will be 0 */
		//	double sAmount = 0;
		localLstData.clear();
		if (localLstData.size() == 0) {

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
		setAmountToPay(getCollectedAmount());
		setTotalAmountPaid(new BigDecimal(0));
		setLstRefundData(localLstData);
		setBooRefundPaymentDetails(true);
		if(localLstData.size() != 0){
			setDenominationchecking(Constants.DenominationAvailable);
		}else{
			setDenominationchecking(Constants.DenominationNotAvailable);
		}

	}

	// currency wise denomination - collected from customer
	// refund denomination details
	public void currencyDenominationData() {
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		/* Checking that it's first time or not, first time list size will be 0 */
		localLstData.clear();
		if (localLstData.size() == 0) {

			List<CurrencyWiseDenomination> currencyWiseDenolst = iPersonalRemittanceService.getCurrencyDenominations(new BigDecimal(sessionStateManage.getCurrencyId()),sessionStateManage.getCountryId());

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

		setLstData(localLstData);
	}

	// back button
	public void backButtonToIddetails(){
		// first Panel
		setBooRenderIdDetails(true);
		setBooRenderOldSmartCardPanel(false);
		// second Panel
		setBooRenderPinDetails(false);
		// third Panel
		setBooRenderPaymentDetails(false);
		setSelectCardType(0);

		setIdNumber(null);
	}

	// back button to first screen PIN Details
	public void backButtonToPinDetails(){

		if(getBooRefundPaymentDetails()){
			// back to pin details
			setBooRenderIdDetails(false);
			setBooRenderOldSmartCardPanel(false);
			setBooRenderPinDetails(true);
			setBooRenderPaymentDetails(false);
			setBooRefundPaymentDetails(false);
			setBooRenderCollectionDetails(false);
			setSaveOrnext(Constants.Save);
		}else if(getBooRenderCollectionDetails()){
			// back to refund details
			setBooRenderIdDetails(false);
			setBooRenderOldSmartCardPanel(false);
			setBooRenderPinDetails(false);
			setBooRenderPaymentDetails(true);
			setBooRefundPaymentDetails(true);
			setBooRenderCollectionDetails(false);
			setSaveOrnext(Constants.Next);
		}else{
			// both not available - wrong condition
		}

	}

	// check amount before save
	public void checkSaveCondition(){
		if(getSaveOrnext() != null && getSaveOrnext().equalsIgnoreCase(Constants.Save)){
			if(getPaidAmount() != null && getPayNetPaidAmount() != null){
				if(getBooRefundPaymentDetails()){
					// refund details should be captured
					if(getLstRefundData() != null && getLstRefundData().size() != 0){
						if(getPaidAmount().compareTo(getPayNetPaidAmount()) == 0){
							try {
								savePaymentRecord();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							// not equal amount
							setErrmsg(WarningHandler.showWarningMessage("lbl.totalAmtPaidShouldBeEqlExceedAmtToPay", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}
					}else{
						setErrmsg(WarningHandler.showWarningMessage("lbl.denominationDetailsNotAvailable", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}
				}else if(getBooRenderCollectionDetails()){
					// refund and collection details should be captured
					if(getLstData() != null && getLstData().size() != 0){
						if(getCollectedAmount().compareTo(getTotalcollectedAmount()) == 0){
							try {
								savePaymentRecord();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							// not equal amount
							setErrmsg(WarningHandler.showWarningMessage("lbl.totCollAmtShouldBeEqualToCollAmt", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}
					}else{
						setErrmsg(WarningHandler.showWarningMessage("lbl.denominationDetailsNotAvailable", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}
				}else{
					// both not available - wrong condition
				}
			}else{
				// null value
			}
		}else if(getSaveOrnext() != null && getSaveOrnext().equalsIgnoreCase(Constants.Next)){
			// redirect to collect amount denomination
			setBooRenderIdDetails(false);
			setBooRenderOldSmartCardPanel(false);
			setBooRenderPinDetails(false);
			setBooRenderPaymentDetails(true);
			setBooRefundPaymentDetails(false);
			setBooRenderCollectionDetails(true);
			setSaveOrnext(Constants.Save);
			currencyDenominationData();
			setTotalcollectedAmount(BigDecimal.ZERO);
		}else{
			// if not save or next
		}
	}

	public void getFinanceYearFromdb() {
		try{
			List<UserFinancialYear> applicationYearList = generalService.getDealYear(new Date());
			if(applicationYearList.size()>0){
				setDocumentYear(applicationYearList.get(0).getFinancialYear());
				setDocumentYearId(applicationYearList.get(0).getFinancialYearID());
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
	}

	@SuppressWarnings("deprecation")
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
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	public String getDocumentSerialIdNumberFromDB(String processIn) {
		log.info( "document seriality method called ===================");
		try{
			setReceiptpayment(Constants.DOCUMENT_CODE_FOR_PAYMENT);
			HashMap<String, String> outPutValues = generalService.getNextDocumentRefNumber(sessionStateManage.getCountryId().intValue() , sessionStateManage.getCompanyId().intValue(), Integer.parseInt(getReceiptpayment()) ,  getDocumentYear().intValue(),processIn,sessionStateManage.getCountryBranchCode());
			String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
			if(proceErrorMsg!=null)
			{
				setErrmsg(proceErrorMsg );
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return "0";
			}else{
				String documentSerialID=outPutValues.get("DOCNO");
				return documentSerialID;
			}
		}catch(NumberFormatException | AMGException e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return "0";
		}
	}

	// submit button
	public ReceiptPayment saveReceiptPayment(){

		System.out.println("SAVE");

		ReceiptPayment receiptPaymentObj = new ReceiptPayment();

		List<IntraTrnxModel> remittanceTrnxlist = iPersonalRemittanceService.fetchRemitTrnxByPin(getPinNo());

		if(remittanceTrnxlist.size() == 1){

			IntraTrnxModel lstIntra = remittanceTrnxlist.get(0); 

			// get Doc Year
			getFinanceYearFromdb();

			receiptPaymentObj.setCustomerName(getCustomerFullName());
			receiptPaymentObj.setIsActive(Constants.Yes);
			receiptPaymentObj.setCreatedBy(sessionStateManage.getUserName());
			receiptPaymentObj.setCreatedDate(new Date());
			receiptPaymentObj.setRemarks(getRemarks());

			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
			Date acc_Month = null;
			try {
				acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String saveDocumentSerialID =  getDocumentSerialIdNumberFromDB(Constants.U);
			if(saveDocumentSerialID.equalsIgnoreCase("0")){
				setErrmsg("Document Seriality Number Not Available");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}else{
				receiptPaymentObj.setDocumentNo(new BigDecimal(saveDocumentSerialID) );
			}
			log.info( "docnumber is=================="+saveDocumentSerialID);
			receiptPaymentObj.setAccountMMYYYY(acc_Month );
			receiptPaymentObj.setDocumentFinanceYear(getDocumentYear());
			receiptPaymentObj.setDocumentFinanceYearId(getDocumentYearId());
			receiptPaymentObj.setTransferFinanceYear(lstIntra.getSendDocumentFinanceYear());
			receiptPaymentObj.setTransferReference(lstIntra.getSendDocumentNo());
			receiptPaymentObj.setCustomerReference(getCustomerrefno());
			receiptPaymentObj.setDocumentStatus(Constants.Yes);

			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));								
			receiptPaymentObj.setLocalFsCountryMaster(currencyMaster);

			//date = formatter.parse(getDocumentDate());
			//receiptPaymentObj.setDocumentDate(date);
			receiptPaymentObj.setDocumentDate(getDocumentDate());

			//receiptPaymentObj.setLocalChargeCurrencyId(viewRemitTranx.getLocalChargeCurrencyId());
			receiptPaymentObj.setLocalChargeCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));

			/*if(getCharges()==null){
			receiptPaymentObj.setLocalChargeAmount(BigDecimal.ZERO);
		    }else{
			receiptPaymentObj.setLocalChargeAmount(getCharges());
		    }*/


			//receiptPaymentObj.setLocalCommisionCurrencyId(viewRemitTranx.getLocalCommissionCurrencyId());
			receiptPaymentObj.setLocalCommisionCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));

			//receiptPaymentObj.setLocalCommisionAmoumnt(getCommision() );

			//receiptPaymentObj.setLocalDeliveryCurrencyId(viewRemitTranx.getLocalDeliveryCurrencyId() );

			receiptPaymentObj.setLocalDeliveryCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));

			//receiptPaymentObj.setLocalDeliveryAmount( getDeliveryCharge());

			//	receiptPaymentObj.setLocalTrnxAmount(viewRemitTranx.getLocalTransactionAmount() );
			//receiptPaymentObj.setLocalOtherAdjAmount(getOtherAdj());
			//receiptPaymentObj.setLocalRateAmount(getRateAdj());
			//receiptPaymentObj.setLocalOtherAdjCurrencyId(viewRemitTranx.getLocalOtherAdjCurrencyId());

			receiptPaymentObj.setLocalOtherAdjCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));

			//receiptPaymentObj.setLocalRateAdjCurrencyId(viewRemitTranx.getLocalNetCurrencyId());
			receiptPaymentObj.setLocalRateAdjCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
			receiptPaymentObj.setLocalNetAmount(getAmount());
			//	receiptPaymentObj.setForignTrnxAmount(viewRemitTranx.getForeignTransactionAmount());
			/*Clob signature=stopPaymentService.getSignatureOfRemitter(viewRemitTranx.getDocumentNo(),viewRemitTranx.getDocumentFinYear(),viewRemitTranx.getDocumentId().toPlainString(),viewRemitTranx.getCompanyId());
		    if(signature!=null){
			try {
				log.info("signature======"+signature );
				receiptPaymentObj.setSignature(signature.getSubString(1, (int) signature.length()));
			} catch (SQLException e) {

				e.printStackTrace();
			}
		    } */
			if(getCustomerId() != null){
				Customer custObj=new Customer();
				custObj.setCustomerId(getCustomerId());
				receiptPaymentObj.setFsCustomer(custObj );
			}


			if(sessionStateManage.getCompanyId() != null){
				CompanyMaster companyMasterObj=new CompanyMaster();
				companyMasterObj.setCompanyId(sessionStateManage.getCompanyId());
				receiptPaymentObj.setFsCompanyMaster(companyMasterObj);
			}

			if(sessionStateManage.getCountryId() != null){	
				CountryMaster countryMasterObj=new CountryMaster();
				countryMasterObj.setCountryId(sessionStateManage.getCountryId());
				receiptPaymentObj.setFsCountryMaster(countryMasterObj);
			}

			if(sessionStateManage.getCurrencyId() !=null){
				CurrencyMaster currencyObj=new CurrencyMaster();
				currencyObj.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
				receiptPaymentObj.setLocalFsCountryMaster(currencyObj);
			}

			if(sessionStateManage.getBranchId() !=null){
				CountryBranch countryBranch=new CountryBranch();
				countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
				receiptPaymentObj.setCountryBranch(countryBranch);
			}

			/*if(viewRemitTranx.getSourceOfIncome()!=null){
				SourceOfIncome sourceOfInc=new SourceOfIncome();
				sourceOfInc.setSourceId(viewRemitTranx.getSourceOfIncome());
				receiptPaymentObj.setSourceOfIncome(sourceOfInc);  
			}*/

			//		receiptPaymentObj.setTransactionType(viewRemitTranx.getTransactionType());
			receiptPaymentObj.setAccountMMYYYY(acc_Month);
			//	receiptPaymentObj.setTransactionActualRate(viewRemitTranx.getExchangeRateApplied() );

			//		receiptPaymentObj.setGeneralLegerDate( viewRemitTranx.getGeneralLedgerDate());
			receiptPaymentObj.setIsActive(Constants.Yes);
			BigDecimal locCode=BigDecimal.ZERO;

			List<CountryBranch> listCode= generalService.getCountryBranchLocCode(new BigDecimal(sessionStateManage.getBranchId() ) );
			if(listCode!=null && listCode.size()>0){
				locCode	=listCode.get(0).getBranchId();
			}
			receiptPaymentObj.setLocCode(locCode);

			List<Document> document = null;

			document = miscellaneousReceiptPaymentService.getDocumentId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
			if(document.size()>0){
				receiptPaymentObj.setDocumentId(document.get(0).getDocumentID());
			}

			receiptPaymentObj.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
			receiptPaymentObj.setReceiptType("99");

			List<CompanyMaster> companyMaster = null;


			companyMaster = miscellaneousReceiptPaymentService.getCompanyCode(sessionStateManage.getCompanyId());
			if(companyMaster.size() >0){
				receiptPaymentObj.setCompanyCode(companyMaster.get(0).getCompanyCode());
			}

			//miscellaneousReceiptPaymentService.saveOrUpdate(receiptPaymentObj);

		}

		return receiptPaymentObj;
	}

	public void savePaymentRecord() throws Exception{
		if(savingMap != null){
			savingMap.clear();
		}
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		// String date = "01/09/14" ;Collect collectid , BigDecimal exchangerate , BigDecimal localnettrastamount
		Date acc_Month = null;
		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		savingMap.put("DOCUMENTCODE", Constants.DOCUMENT_CODE_FOR_PAYMENT);
		savingMap.put("USERNAME", sessionStateManage.getUserName());

		ReceiptPayment receiptPaymentObj = saveReceiptPayment();
		savingMap.put("RECEIPTPAYMENT", receiptPaymentObj);

		Payment payment = savePayment(acc_Month,receiptPaymentObj);
		savingMap.put("PAYMENT", payment);

		if(getLstRefundData()!=null && getLstRefundData().size()>0){
			List<ForeignCurrencyAdjust> lstRefund = saveDenominationRefund(acc_Month,receiptPaymentObj);
			savingMap.put("FOREIGNCURRENCYADJUSTREFUND", lstRefund);
		}
		if(getLstData()!=null && getLstData().size()>0){
			List<ForeignCurrencyAdjust> lstcollected = saveDenominationPaid(acc_Month,receiptPaymentObj);
			savingMap.put("FOREIGNCURRENCYADJUSTDENOMINATION", lstcollected);
		}
		
		// mapvalue
		System.out.println(savingMap.toString());
		
		intraCompanyService.saveRecords(savingMap);
		clearDenominationDetails();

		RequestContext.getCurrentInstance().execute("intraAppSave.show();");
		
		
	}

	public List<ForeignCurrencyAdjust> saveDenominationPaid(Date acc_Month,ReceiptPayment receiptPaymentObj)throws Exception{
		int i = 0;
		List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = new ArrayList<ForeignCurrencyAdjust>();
		for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : getLstData()) {

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
				customer.setCustomerId(getCustomerId());
				foreignCurrencyAdjust.setFsCustomer(customer);
				foreignCurrencyAdjust.setDocumentDate(new Date());
				// currency Id
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
				foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
				if(foreignLocalCurrencyDataTable.getQty()!=null){
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
				}
				if(foreignLocalCurrencyDataTable.getPrice()!=null){
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				}

				CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();

				denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());

				foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
				//	foreignCurrencyAdjust.setExchangeRate();
				foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());
				foreignCurrencyAdjust.setDocumentNo(receiptPaymentObj.getDocumentNo());
				foreignCurrencyAdjust.setDocumentFinanceYear(receiptPaymentObj.getDocumentFinanceYear());
				foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());

				try {
					foreignCurrencyAdjust.setDocumentCode(receiptPaymentObj.getDocumentCode()); 
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					foreignCurrencyAdjust.setProgNumber("PAYMENT");
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);

				} catch (Exception e) {
					e.printStackTrace();
				}
				foreignCurrencyAdjust.setCreatedDate(new Date());
				foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());
				/*Collect collect =(Collect)savingMap.get("COLLECT");
				if (collect != null) {
					foreignCurrencyAdjust.setDocumentNo(collect.getDocumentNo());
					foreignCurrencyAdjust.setCollect(collect);
				}else{
					foreignCurrencyAdjust.setDocumentNo(getDocumentNo());
				}*/
				List<Document> document = null;


				document = generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT), sessionStateManage.getLanguageId());
				if(document != null){
					foreignCurrencyAdjust.setDocumentId(document.get(0).getDocumentID());
				}


				foreignCurrencyAdjust.setTransactionType(Constants.C);

				BigDecimal companyCode=null;
				companyCode=miscellaneousReceiptPaymentService.toFetchCompanyCode(sessionStateManage.getCompanyId());
				foreignCurrencyAdjust.setCompanyCode(companyCode);
				foreignCurrencyAdjustList.add(foreignCurrencyAdjust);
			} else {
				log.info("Number of notes is 0");
			}
		}
		
		return foreignCurrencyAdjustList;
	}


	public List<ForeignCurrencyAdjust> saveDenominationRefund(Date acc_Month,ReceiptPayment receiptPaymentObj)throws Exception{

		List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = new ArrayList<ForeignCurrencyAdjust>();
		int j = 0;
		for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : getLstRefundData()) {
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
				customer.setCustomerId(getCustomerId());
				foreignCurrencyAdjust.setFsCustomer(customer);
				foreignCurrencyAdjust.setDocumentDate(new Date());
				// currency Id
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
				foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);
				if(foreignLocalCurrencyDataTable.getQty()!=null){
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(foreignLocalCurrencyDataTable.getQty()));
				}
				if(foreignLocalCurrencyDataTable.getPrice()!=null){
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(foreignLocalCurrencyDataTable.getPrice()));
				}

				CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
				denominationMaster.setDenominationId(foreignLocalCurrencyDataTable.getDenominationId());
				foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
				//	foreignCurrencyAdjust.setExchangeRate((BigDecimal) returnResult.get("ExchangeRate"));
				foreignCurrencyAdjust.setDenaminationAmount(foreignLocalCurrencyDataTable.getDenominationAmount());
				foreignCurrencyAdjust.setDocumentFinanceYear(receiptPaymentObj.getDocumentFinanceYear());
				foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());

				try {
					foreignCurrencyAdjust.setDocumentCode(receiptPaymentObj.getDocumentCode());
					foreignCurrencyAdjust.setDocumentNo(receiptPaymentObj.getDocumentNo());
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++j));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);
					foreignCurrencyAdjust.setProgNumber("PAYMENT");
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);

				} catch (Exception e) {
					e.printStackTrace();
				}

				foreignCurrencyAdjust.setCreatedDate(new Date());
				foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getUserName());

				List<Document> document = null;

				document = generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT), sessionStateManage.getLanguageId());
				if(document != null){
					foreignCurrencyAdjust.setDocumentId(document.get(0).getDocumentID());
				}


				foreignCurrencyAdjust.setTransactionType(Constants.F);

				BigDecimal companyCode=null;
				companyCode=miscellaneousReceiptPaymentService.toFetchCompanyCode(sessionStateManage.getCompanyId());
				foreignCurrencyAdjust.setCompanyCode(companyCode);
				/*Collect collect =(Collect)savingMap.get("COLLECT");
				if (collect != null) {
					foreignCurrencyAdjust.setDocumentNo(collect.getDocumentNo());
					foreignCurrencyAdjust.setCollect(collect);
				}else{
					foreignCurrencyAdjust.setDocumentNo(getDocumentNo());
				}*/
				foreignCurrencyAdjustList.add(foreignCurrencyAdjust);


			} else {
				log.info("Number of notes is 0");
			}
		}
		
		return foreignCurrencyAdjustList;
	}

	public Payment savePayment(Date acc_Month,ReceiptPayment receiptPaymentObj){

		Payment payment =new Payment();

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(sessionStateManage.getCountryId());
		payment.setCountryId(countryMaster);

		payment.setCompanyId(sessionStateManage.getCompanyId());
		payment.setDocCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
		payment.setDocNumber(receiptPaymentObj.getDocumentNo());
		payment.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
		payment.setReceiptType("99");
		//payment.setPaymentmode();
		//payment.setChequeReference(chequeReference);
		//payment.setChequekdate(chequekdate);
		//payment.setApprovalNo(approvalNo);
		payment.setCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
		//lstofPayment
		//payment.setPaidAmount(getTotalCashEntered());
		//payment.setIsCashDeposit(isCashDeposit);
		//payment.setCdepDoccod(cdepDoccod);
		//payment.setCdepDocfyr(cdepDocfyr);
		//payment.setCdepDocno(cdepDocno);
		//payment.setCdepDate(cdepDate);
		payment.setIsActive(Constants.Yes);
		payment.setAcyymm(acc_Month);
		payment.setCreatedBy(sessionStateManage.getUserName());
		payment.setCreatedDate(new Date());
		payment.setModifiedBy(null);
		payment.setModifiedDate(null);
		payment.setDocYear(getDocumentYear());
		payment.setCustomerId(getCustomerId());

		BigDecimal documentId=BigDecimal.ZERO;
		BigDecimal companyCode=BigDecimal.ZERO;
		BigDecimal locCode=BigDecimal.ZERO;

		List<Document> listDocument = generalService.getDocument(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT, sessionStateManage.getLanguageId());
		if(listDocument!=null && listDocument.size()>0){
			documentId=listDocument.get(0).getDocumentID();
		}

		List<CompanyMasterDesc> data=generalService.getCompanyList(sessionStateManage.getCompanyId(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		if(data!=null && data.size()>0)
		{
			companyCode=data.get(0).getFsCompanyMaster().getCompanyCode();
		}

		payment.setCompanyCode(companyCode);
		payment.setDocumentId(documentId );

		List<CountryBranch> listCode= generalService.getCountryBranchLocCode(new BigDecimal(sessionStateManage.getBranchId()));
		if(listCode!=null && listCode.size()>0){
			locCode	= listCode.get( 0).getBranchId();
		}
		payment.setLocCod( locCode);
		payment.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
		payment.setPaymentDate(new Date());
		payment.setPaymentmode(Constants.CashCode);
		
		return payment;
	}

	// after save back to pin number
	public void completedtrnx(){
		clearPanelBeneDetails();
		setBooRenderIdDetails(false);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderPinDetails(true);
		setBooRenderPaymentDetails(false);
		setBooRefundPaymentDetails(false);
		setBooRenderCollectionDetails(false);
		setSaveOrnext(Constants.Save);
	}

	// exit button
	public void exitButton(){
		try{
			if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}

	// clear data table
	public void resetFilters(String clearDataTable) {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(clearDataTable);
		if (dataTable != null) {
			dataTable.reset();
		}
	}

	// on data table cell edit for refund
	public void onRefundCellEdit(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {
		int quantity = 0;
		if((foreignLocalCurrencyDataTable.getQty()=="") || (foreignLocalCurrencyDataTable.getQty() != null && foreignLocalCurrencyDataTable.getQty().equalsIgnoreCase("0"))) {
			quantity = 0;
			BigDecimal totalSum = BigDecimal.ZERO;
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
			for (ForeignLocalCurrencyDataTable element : lstRefundData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}
			setTotalAmountPaid(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
			setPaidAmount(getTotalAmountPaid());

			if (getAmountToPay() != null && getAmountToPay().compareTo(totalSum) < 0) {
				setSaveOrnext(Constants.Next);
				setCollectedAmount(totalSum.subtract(getAmountToPay()));
			} else {
				setSaveOrnext(Constants.Save);
				setCollectedAmount(BigDecimal.ZERO);
			}
		} else {
			try
			{
				quantity = Integer.parseInt(foreignLocalCurrencyDataTable.getQty());

				if(foreignLocalCurrencyDataTable.getStock() >= quantity && quantity != 0){
					BigDecimal totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(new BigDecimal(foreignLocalCurrencyDataTable.getQty() == "" ? "0" : foreignLocalCurrencyDataTable.getQty()));
					if (foreignLocalCurrencyDataTable.getQty().equals("")) {
						foreignLocalCurrencyDataTable.setQty("");
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

					if (getAmountToPay() != null && getAmountToPay().compareTo(totalSum) < 0) {
						setTotalAmountPaid(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
						setPaidAmount(getTotalAmountPaid());
						setSaveOrnext(Constants.Next);
						setCollectedAmount(totalSum.subtract(getAmountToPay()));
					} else {
						setTotalAmountPaid(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
						setPaidAmount(getTotalAmountPaid());
						setSaveOrnext(Constants.Save);
						setCollectedAmount(BigDecimal.ZERO);
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
					setTotalAmountPaid(GetRound.roundBigDecimal(totalSum,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					setPaidAmount(getTotalAmountPaid());
					if (getAmountToPay() != null && getAmountToPay().compareTo(totalSum) < 0) {
						setSaveOrnext(Constants.Next);
						setCollectedAmount(totalSum.subtract(getAmountToPay()));
					} else {
						setSaveOrnext(Constants.Save);
						setCollectedAmount(BigDecimal.ZERO);
					}
				}
			}
			catch(Exception e)
			{
				quantity = 0;
				setErrmsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}
	}

	// on cell edit for collected amount denomination
	public void onCellEdit(ForeignLocalCurrencyDataTable denominationBean){

		try{
			String quantity = (denominationBean.getQty()==""?"0":denominationBean.getQty());
			if(denominationBean.getItem()!=null){
				if(!quantity.trim().equals("")){
					BigDecimal collAmount = denominationBean.getItem().multiply(new BigDecimal(quantity));
					// Here purchageAmount!=0
					if(collAmount!=null && collAmount.compareTo(BigDecimal.ZERO) != 0){
						denominationBean.setPrice(GetRound.roundBigDecimal(collAmount,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))).toPlainString());
					}else{
						denominationBean.setPrice("");
					}
				} else{
					denominationBean.setPrice("");	
				}
			}
			BigDecimal totalCashEntered = BigDecimal.ZERO;
			for(ForeignLocalCurrencyDataTable foreignCurrencyObj : getLstData()){
				if(foreignCurrencyObj.getPrice()!=null && foreignCurrencyObj.getPrice().length()!=0){
					totalCashEntered = totalCashEntered.add(new BigDecimal(foreignCurrencyObj.getPrice()));
				}
			}

			if(totalCashEntered != null && totalCashEntered.compareTo(BigDecimal.ZERO) != 0){
				if(totalCashEntered.compareTo(getCollectedAmount())<=0){
					// allow to enter
					setTotalcollectedAmount(totalCashEntered);
				}else{
					setTotalcollectedAmount(totalCashEntered);
					denominationBean.setPrice("");
					denominationBean.setQty("");
					RequestContext.getCurrentInstance().execute("cleardenominationexceed.show();");
					return;
				}
			}else{
				denominationBean.setPrice("");
				denominationBean.setQty("");
			}

		}catch(Exception e){
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

	}

	// clear customer details
	public void clearCustomerDetails(){
		setCustomerName(null);
		setCustomerCrNumber(null);
		setCustomerId(null);
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
		setDateOfBrith(null);
		setCountryCode(null);
		setMcountryCode(null);
		setLoyaltyPoints(null);
		setOccupation(null);
	}

	// clear panel 1 variables
	public void clearPanelBeneDetails(){
		setPinNo(null);
		setDocumentDate(null);
		setBeneficiaryId(null);
		setBeneficiaryName(null);
		setBeneficiaryNameLocal(null);
		setNationalityName(null);
		setNationalityId(null);
		setBeneCountryName(null);
		setBeneCountryId(null);
		setBeneStateName(null);
		setBeneStateId(null);
		setBeneDistrictName(null);
		setBeneDistrictId(null);
		setBeneCityName(null);
		setBeneCityId(null);
		setCountryTelCode(null);
		setTelephoneNumber(null);
		setMobileNumber(null);
		setAmount(null);
		setCurrencyName(null);
		setSenderCountryName(null);
		setSenderCountryId(null);
		setSenderName(null);
		setSenderNameLocal(null);
		setRemarks(null);
	}
	
	// clear panel 2 variables
	public void clearDenominationDetails(){
		
		if(lstRefundData != null && lstRefundData.size() != 0){
			lstRefundData.clear();
		}
		if(lstData != null && lstData.size() != 0){
			lstData.clear();
		}
		setPaidAmount(BigDecimal.ZERO);
		setPayNetPaidAmount(BigDecimal.ZERO);
		setCollectedAmount(BigDecimal.ZERO);
		setPaymentDetailsremark(null);
		setAmountToPay(BigDecimal.ZERO);
		setTotalAmountPaid(BigDecimal.ZERO);
		setCollectedAmount(BigDecimal.ZERO);
		setTotalcollectedAmount(BigDecimal.ZERO);
		
	}


	// getters and setters
	public String getPinNo() {
		return pinNo;
	}
	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	public BigDecimal getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(BigDecimal beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryNameLocal() {
		return beneficiaryNameLocal;
	}
	public void setBeneficiaryNameLocal(String beneficiaryNameLocal) {
		this.beneficiaryNameLocal = beneficiaryNameLocal;
	}

	public String getNationalityName() {
		return nationalityName;
	}
	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	public BigDecimal getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(BigDecimal nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getBeneCountryName() {
		return beneCountryName;
	}
	public void setBeneCountryName(String beneCountryName) {
		this.beneCountryName = beneCountryName;
	}

	public BigDecimal getBeneCountryId() {
		return beneCountryId;
	}
	public void setBeneCountryId(BigDecimal beneCountryId) {
		this.beneCountryId = beneCountryId;
	}

	public String getBeneStateName() {
		return beneStateName;
	}
	public void setBeneStateName(String beneStateName) {
		this.beneStateName = beneStateName;
	}

	public BigDecimal getBeneStateId() {
		return beneStateId;
	}
	public void setBeneStateId(BigDecimal beneStateId) {
		this.beneStateId = beneStateId;
	}

	public String getBeneDistrictName() {
		return beneDistrictName;
	}
	public void setBeneDistrictName(String beneDistrictName) {
		this.beneDistrictName = beneDistrictName;
	}

	public BigDecimal getBeneDistrictId() {
		return beneDistrictId;
	}
	public void setBeneDistrictId(BigDecimal beneDistrictId) {
		this.beneDistrictId = beneDistrictId;
	}

	public String getBeneCityName() {
		return beneCityName;
	}
	public void setBeneCityName(String beneCityName) {
		this.beneCityName = beneCityName;
	}

	public BigDecimal getBeneCityId() {
		return beneCityId;
	}
	public void setBeneCityId(BigDecimal beneCityId) {
		this.beneCityId = beneCityId;
	}

	public String getCountryTelCode() {
		return countryTelCode;
	}
	public void setCountryTelCode(String countryTelCode) {
		this.countryTelCode = countryTelCode;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public BigDecimal getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(BigDecimal mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSenderCountryName() {
		return senderCountryName;
	}
	public void setSenderCountryName(String senderCountryName) {
		this.senderCountryName = senderCountryName;
	}

	public BigDecimal getSenderCountryId() {
		return senderCountryId;
	}
	public void setSenderCountryId(BigDecimal senderCountryId) {
		this.senderCountryId = senderCountryId;
	}

	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderNameLocal() {
		return senderNameLocal;
	}
	public void setSenderNameLocal(String senderNameLocal) {
		this.senderNameLocal = senderNameLocal;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDenominationchecking() {
		return denominationchecking;
	}
	public void setDenominationchecking(String denominationchecking) {
		this.denominationchecking = denominationchecking;
	}

	public Boolean getBooRefundPaymentDetails() {
		return booRefundPaymentDetails;
	}
	public void setBooRefundPaymentDetails(Boolean booRefundPaymentDetails) {
		this.booRefundPaymentDetails = booRefundPaymentDetails;
	}

	public Boolean getBooRenderPaymentDetails() {
		return booRenderPaymentDetails;
	}
	public void setBooRenderPaymentDetails(Boolean booRenderPaymentDetails) {
		this.booRenderPaymentDetails = booRenderPaymentDetails;
	}

	public Boolean getBooRenderCollectionDetails() {
		return booRenderCollectionDetails;
	}
	public void setBooRenderCollectionDetails(Boolean booRenderCollectionDetails) {
		this.booRenderCollectionDetails = booRenderCollectionDetails;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstRefundData() {
		return lstRefundData;
	}
	public void setLstRefundData(ArrayList<ForeignLocalCurrencyDataTable> lstRefundData) {
		this.lstRefundData = lstRefundData;
	}

	public ArrayList<ForeignLocalCurrencyDataTable> getLstData() {
		return lstData;
	}
	public void setLstData(ArrayList<ForeignLocalCurrencyDataTable> lstData) {
		this.lstData = lstData;
	}

	public BigDecimal getTotalAmountPaid() {
		return totalAmountPaid;
	}
	public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}

	public BigDecimal getAmountToPay() {
		return amountToPay;
	}
	public void setAmountToPay(BigDecimal amountToPay) {
		this.amountToPay = amountToPay;
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

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
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

	public BigDecimal getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(BigDecimal collectedAmount) {
		this.collectedAmount = collectedAmount;
	}

	public BigDecimal getTotalcollectedAmount() {
		return totalcollectedAmount;
	}
	public void setTotalcollectedAmount(BigDecimal totalcollectedAmount) {
		this.totalcollectedAmount = totalcollectedAmount;
	}

	public Boolean getBooRenderPinDetails() {
		return booRenderPinDetails;
	}
	public void setBooRenderPinDetails(Boolean booRenderPinDetails) {
		this.booRenderPinDetails = booRenderPinDetails;
	}

	public Boolean getBooRenderIdDetails() {
		return booRenderIdDetails;
	}
	public void setBooRenderIdDetails(Boolean booRenderIdDetails) {
		this.booRenderIdDetails = booRenderIdDetails;
	}

	public boolean isBooRenderOldSmartCardPanel() {
		return booRenderOldSmartCardPanel;
	}
	public void setBooRenderOldSmartCardPanel(boolean booRenderOldSmartCardPanel) {
		this.booRenderOldSmartCardPanel = booRenderOldSmartCardPanel;
	}

	public int getSelectCardType() {
		return selectCardType;
	}
	public void setSelectCardType(int selectCardType) {
		this.selectCardType = selectCardType;
	}

	public BigDecimal getSelectCard() {
		return selectCard;
	}
	public void setSelectCard(BigDecimal selectCard) {
		this.selectCard = selectCard;
	}

	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public List<PersonalRemmitanceBeneficaryDataTable> getCustomerBeneficaryDTList() {
		return customerBeneficaryDTList;
	}
	public void setCustomerBeneficaryDTList(List<PersonalRemmitanceBeneficaryDataTable> customerBeneficaryDTList) {
		this.customerBeneficaryDTList = customerBeneficaryDTList;
	}

	public String getCustomerCrNumber() {
		return customerCrNumber;
	}
	public void setCustomerCrNumber(String customerCrNumber) {
		this.customerCrNumber = customerCrNumber;
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

	public List<CustomerIdProof> getCustomerDetailsList() {
		return customerDetailsList;
	}
	public void setCustomerDetailsList(List<CustomerIdProof> customerDetailsList) {
		this.customerDetailsList = customerDetailsList;
	}

	public List<PopulateData> getAllBeneCountryList() {
		return allBeneCountryList;
	}
	public void setAllBeneCountryList(List<PopulateData> allBeneCountryList) {
		this.allBeneCountryList = allBeneCountryList;
	}

	public Map<BigDecimal, String> getMapcomIdentityType() {
		return mapcomIdentityType;
	}
	public void setMapcomIdentityType(Map<BigDecimal, String> mapcomIdentityType) {
		this.mapcomIdentityType = mapcomIdentityType;
	}

	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getSaveOrnext() {
		return saveOrnext;
	}
	public void setSaveOrnext(String saveOrnext) {
		this.saveOrnext = saveOrnext;
	}

	public BigDecimal getDocumentYear() {
		return documentYear;
	}
	public void setDocumentYear(BigDecimal documentYear) {
		this.documentYear = documentYear;
	}

	public BigDecimal getDocumentYearId() {
		return documentYearId;
	}
	public void setDocumentYearId(BigDecimal documentYearId) {
		this.documentYearId = documentYearId;
	}

	public String getReceiptpayment() {
		return receiptpayment;
	}
	public void setReceiptpayment(String receiptpayment) {
		this.receiptpayment = receiptpayment;
	}



}


