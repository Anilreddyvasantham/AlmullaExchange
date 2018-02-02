package com.amg.exchange.wuh2h.bean;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.Locator;

import com.amg.exchange.aop.FcSaleReport;
import com.amg.exchange.beneficiary.bean.BeneficiaryCreationBean;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.CutomerDetailsView;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.bean.ForeignLocalCurrencyDataTable;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.TempCollectDetail;
import com.amg.exchange.foreigncurrency.model.TempCollection;
import com.amg.exchange.foreigncurrency.model.TempForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.registration.bean.CustomerPersonalInfoBean;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.ICompanyMasterservice;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.remittance.bean.AddAdditionalBankData;
import com.amg.exchange.remittance.bean.AddDynamicLebel;
import com.amg.exchange.remittance.bean.BeneficiaryEditBean;
import com.amg.exchange.remittance.bean.CustomerAlmTrasactionCheckProcedure;
import com.amg.exchange.remittance.bean.PersonalRemittanceCollectionDataTable;
import com.amg.exchange.remittance.bean.PersonalRemmitanceBeneficaryDataTable;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.bean.PurposeOfRemittanceReportBean;
import com.amg.exchange.remittance.bean.RemittanceDeclarationMainReportBean;
import com.amg.exchange.remittance.bean.RemittanceReceiptSubreport;
import com.amg.exchange.remittance.bean.RemittanceReportBean;
import com.amg.exchange.remittance.bean.ShoppingCartDataTableBean;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AdditionalDataDisplayView;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BeneficaryStatus;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.CustomerBank;
import com.amg.exchange.remittance.model.DebitAutendicationView;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.model.ViewBeneServiceCurrency;
import com.amg.exchange.remittance.model.ViewHODirectEFT;
import com.amg.exchange.remittance.model.ViewHODirectInDirect;
import com.amg.exchange.remittance.model.ViewStatesForICASH;
import com.amg.exchange.remittance.model.ViewStock;
import com.amg.exchange.remittance.model.ViewSubAgent;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BankPrefix;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceGroupMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.IBeneCountryServices;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wu.model.WesternUnionTransfer;
import com.amg.exchange.wu.service.IWesternUnionService;
import com.amg.exchange.wuh2h.bean.ReceiveReceiptView;
import com.amg.exchange.wuh2h.bean.XMLGenerator;
import com.amg.exchange.wuh2h.model.WURemittanceApplicationView;
import com.amg.exchange.wuh2h.service.IWUH2HService;


@SuppressWarnings({ "unused","unchecked","rawtypes" })
@Component("wuh2hbean")
@Scope("session")
public class WesternUnionHToHBean<T> implements Serializable {/*
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(WesternUnionHToHBean.class);

	
	 * Autowire Configuration
	 

	@Autowired
	ApplicationContext context;

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

	@Autowired
	ICompanyMasterservice iCompanyMasterservice;
	
	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;
	
	@Autowired
	ICustomerBankService icustomerBankService;
	
	@Autowired
	IGLTransactionForADocument iglTransactionForADocument;

	@Autowired
	IWUH2HService iwuh2hService;

	public WesternUnionHToHBean() {
		hideAllPanels();
		clear();

	}

	
	 * Western Union Page Navigation
	 
	public void pageNavigation() {
		try {
			hideAllPanels();
			clear();
			setWuCardno(null);
			setWucardLookup(false);
			wuh2hSendClear();
			wuh2hReceiveClear();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuhtohmoneytransfer.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Show Card type
	public void showCardTypePanel() throws Exception {
		
		try{
			int typecard = getSelectCardType();
			if (typecard == 2) {
				setBooRenderBenificaryFirstPanel(true);
				setBooRenderOldSmartCardPanel(true);
				BigDecimal idtypeCivilIdnew = generalService
						.getComponentId(Constants.CIVILID,
								sessionStateManage.getLanguageId())
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
					RequestContext.getCurrentInstance().execute(
							"dldInserCard.show();");
				}
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::showCardTypePanel");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		
	}
	
	public void wuEnrolmentContinue() throws AMGException, InterruptedException{
		String returnMessage =  null;
		returnMessage =  wuh2hWUCardEnrolment();
		if(returnMessage !=null){
			setMessage(returnMessage);
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		setTxnType(1);
		changeTxnType();
	}

	
	 * Transaction Type (Send/Receive)
	 
	public void changeTxnType() throws AMGException, InterruptedException {
		String wuerrormessage = null;
		try {
			setErrorMessage(null);
			wuh2hSendClear();
			wuh2hReceiveClear();
			if (getTxnType() == 1) {
				setBooTxnType(true);
				setBooSourceEnable(false);
				setBooPurposeEnable(false);
				setBooMtcEnable(false);
				setSourceOfIncome(null);
				setPurposeOfTransactions(null);
				setBooRenderBenificaryFirstPanel(false);
				setBooCheckStatusPanel(false);
				setBeneficiaryCountryId(null);
				populateBeneFiciaryCountry();
				setEnablePromotion("false");
				setSenderPointsEarned(null);
				if(getSendWUcardNo()!=null){
					setWuCardno(getSendWUcardNo());
					wuh2hWUCardLookup();
				}
				
				if(getMessageDetailList().size()>0){
					setEnableEditMessage(true);
					setEnableMessage(false);
				}else{
					setEnableEditMessage(false);
					setEnableMessage(true);
				}
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../wuh2h/wuh2hbenelistinfo.xhtml");
			}
			if (getTxnType() == 2) {
				wuh2hReceiveClear();
				setDisplayReceiverInfo(false);
				setBooTxnType(false);
				setBooSourceEnable(false);
				setBooPurposeEnable(false);
				setBooMtcEnable(false);
				setSourceOfIncome(null);
				setPurposeOfTransactions(null);
				List<UserFinancialYear> finYearList = generalService
						.getDealYear(new Date());
				if (finYearList != null) {
					setMtcYear(finYearList.get(0).getFinancialYear());
				}
				setBooRenderBenificaryFirstPanel(false);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../wuh2h/wuh2hreceivemoneysearch.xhtml");
			}
			if (getTxnType() == 3) {				
				RequestContext.getCurrentInstance().execute("wumodify.show();");
				return;
			}

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::changeTxnType");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void fetchSmartCardIdNumber() throws ParseException {
		if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(
				Constants.KUWAIT_ALPHA_TWO_CODE)) {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			smartCardDisplay(ipAddress, "8085", "M", "test");
			// smartCardDisplay("10.200.4.69", "8085", "M", "test");
		}
	}

	// Display smart card
	public String smartCardDisplay(String host, String prdPort,
			String requestType, String env) throws ParseException {
		StringBuffer sb = new StringBuffer();
		StringBuffer urlBuffer = new StringBuffer();
		String appender = "?";
		String ampersand = "&";
		String equals = "=";
		String colon = ":";
		String rootContext = "/KwtSmartCard/SmartCartServ"; // KwtSmartCard/smartcard
		if (env.equalsIgnoreCase("test")) {
			urlBuffer.append("http://").append(host).append(colon)
					.append(prdPort).append(rootContext).append(appender);
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
				in = new BufferedReader(new InputStreamReader(
						testyc.getInputStream()));
			} else if (env.equalsIgnoreCase("live")) {
				prdyc = (HttpsURLConnection) knetRequest.openConnection();
				in = new BufferedReader(new InputStreamReader(
						prdyc.getInputStream()));
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
		} finally {
			if (testyc != null) {
				testyc.disconnect();
			}
			if (prdyc != null) {
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
					setSelectCard(generalService
							.getComponentId(Constants.CIVILID,
									sessionStateManage.getLanguageId())
							.getFsBizComponentData().getComponentDataId());
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

			if (coustomerBeneficaryDTList != null
					|| !coustomerBeneficaryDTList.isEmpty()) {
				coustomerBeneficaryDTList.clear();
			}

			if (getSelectCard() != null) {

				// customerDetailsList =
				// iPersonalRemittanceService.getCustomerDetails(getIdNumber().toUpperCase(),
				// getSelectCard());
				customerDetailsList = iPersonalRemittanceService
						.getCustomerDetailsActiveRec(getIdNumber()
								.toUpperCase(), getSelectCard());

				if (customerDetailsList.size() != 0) {
					CustomerIdProof customerDetails = customerDetailsList
							.get(0); customerDetailsList.get(0).getFsCustomer();
					fetchCustomerBeneficiaryDetails(customerDetails);
				} else {
					// comparing with civil id
					BigDecimal identityTpeIds = generalService
							.getComponentId(Constants.CIVILID,
									sessionStateManage.getLanguageId())
							.getFsBizComponentData().getComponentDataId();

					if (getSelectCard().compareTo(identityTpeIds) != 0) {
						// customerDetailsList =
						// iPersonalRemittanceService.getCustomerDetails(getIdNumber(),
						// identityTpeIds);
						customerDetailsList = iPersonalRemittanceService
								.getCustomerDetailsActiveRec(getIdNumber(),
										identityTpeIds);
						if (customerDetailsList.size() != 0) {
							CustomerIdProof customerDetails = customerDetailsList
									.get(0);
							fetchCustomerBeneficiaryDetails(customerDetails);
						} else {
							// comparing with civil id new
							BigDecimal idtypeCivilIdnew = generalService
									.getComponentId(Constants.CIVIL_ID_NEW,
											sessionStateManage.getLanguageId())
									.getFsBizComponentData()
									.getComponentDataId();
							// customerDetailsList =
							// iPersonalRemittanceService.getCustomerDetails(getIdNumber(),
							// idtypeCivilIdnew);
							customerDetailsList = iPersonalRemittanceService
									.getCustomerDetailsActiveRec(getIdNumber(),
											idtypeCivilIdnew);
							if (customerDetailsList.size() != 0) {
								CustomerIdProof customerDetails = customerDetailsList
										.get(0);

								fetchCustomerBeneficiaryDetails(customerDetails);
							} else {
								// failed all conditions
								setCardType(null);
								setIdNumber(null);
								setBooRenderBenificaryFirstPanel(true);
								RequestContext.getCurrentInstance().execute(
										"idNotFound.show();");
							}
						}
					} else {
						// comparing with civil id new
						BigDecimal idtypeCivilIdnew = generalService
								.getComponentId(Constants.CIVIL_ID_NEW,
										sessionStateManage.getLanguageId())
								.getFsBizComponentData().getComponentDataId();
						// customerDetailsList =
						// iPersonalRemittanceService.getCustomerDetails(getIdNumber(),
						// idtypeCivilIdnew);
						customerDetailsList = iPersonalRemittanceService
								.getCustomerDetailsActiveRec(getIdNumber(),
										idtypeCivilIdnew);
						if (customerDetailsList.size() != 0) {
							CustomerIdProof customerDetails = customerDetailsList
									.get(0);
							fetchCustomerBeneficiaryDetails(customerDetails);
						} else {
							// failed all conditions
							setCardType(null);
							setIdNumber(null);
							setBooRenderBenificaryFirstPanel(true);
							RequestContext.getCurrentInstance().execute(
									"idNotFound.show();");
						}

					}
				}

			}

		} else {
			RequestContext.getCurrentInstance().execute(
					"idNumbernotenter.show();");
		}

		log.info("Exit into goFromOldSmartCardpanel method");
	}

	// calling remit amount page
	public void fetchCustomerBeneficiaryDetails(
			CustomerIdProof fetchedcustomerDetails) {

		try {

			// Added by Rabil.
			String userType = null;
			if ((sessionStateManage.getBranchId() != null || sessionStateManage
					.getCustomerType().equals("E"))
					&& sessionStateManage.getRoleId().equals("1")) {
				userType = "BRANCH";
			} else if (sessionStateManage.getBranchId() != null
					&& sessionStateManage.getUserType().equalsIgnoreCase("K")) {
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
				setCustomerExpireDateMsg(new SimpleDateFormat("dd/MM/yyyy")
						.format(getCustomerExpDate()));
			}

			log.info("getCustomerNo() :" + getCustomerNo()
					+ "\t sessionStateManage.getCountryId():"
					+ sessionStateManage.getCountryId()
					+ "\t sessionStateManage.getUserName() :"
					+ sessionStateManage.getUserName());
			HashMap<String, String> customerValiMessage = iPersonalRemittanceService
					.getValidateCustomerProcedure(
							sessionStateManage.getCountryId(), getCustomerNo(),
							sessionStateManage.getUserName(), userType);
			log.info("customerValiMessage :" + customerValiMessage);
			log.info("INDICATOR====" + customerValiMessage.get("INDICATOR"));
			customerValiMessage.put("ERROR_MESSAGE", null);
			if (customerValiMessage.get("ERROR_MESSAGE") != null) {
				setExceptionMessage(customerValiMessage.get("ERROR_MESSAGE"));
				RequestContext.getCurrentInstance().execute(
						"expiredCustomer.show();");
				return;
			} else if (customerValiMessage != null
					&& customerValiMessage.get("INDICATOR") != null) {
				setExceptionMessage(customerValiMessage.get("ERROR_MESSAGE"));
				setCardType(null);
				if (customerValiMessage.get("INDICATOR").equalsIgnoreCase(
						Constants.Yes)) {
					RequestContext.getCurrentInstance().execute(
							"customerregproceed.show();");
					return;
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

				if (getCustomerIsActive() != null
						&& getCustomerIsActive()
								.equalsIgnoreCase(Constants.Yes)) {

					// 18 years condition check
					if (getDateOfBrith() != null) {
						SimpleDateFormat formatter = new SimpleDateFormat(
								"dd/MM/yyyy");
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(new Date());
						cal1.add(Calendar.YEAR, -new Integer(
								Constants.AGE_LIMIT));
						Date today18 = cal1.getTime();
						setBirthdate(formatter.format(getDateOfBrith()));

						if (getBirthdate() != null
								&& !formatter.parse(getBirthdate()).before(
										today18)) {
							RequestContext.getCurrentInstance().execute(
									"dobcheck.show();");
							return;
						} else {
							lstCountry = generalService
									.getCountryList(sessionStateManage
											.getLanguageId());
							nationalityList = generalService
									.getNationalityList(sessionStateManage
											.getLanguageId());

							// ValidateCustomerProcedure
							if (getCustomerCrNumber() != null
									&& !getCustomerCrNumber().equalsIgnoreCase(
											"")) {
								// setBooRenderOverseaCharges(true);
								backFromBenificaryStatusPanel();
							} else {
								// setBooRenderOverseaCharges(false);
								backFromBenificaryStatusPanel();
							}
						}
					} else {
						boolean civilIdCheck = false;
						BigDecimal idtypeCivilId = generalService
								.getComponentId(Constants.CIVILID,
										sessionStateManage.getLanguageId())
								.getFsBizComponentData().getComponentDataId();
						if (idtypeCivilId != null
								&& idtypeCivilId.compareTo(getSelectCard()) == 0) {
							civilIdCheck = true;
						} else {
							BigDecimal idtypeCivilIdnew = generalService
									.getComponentId(Constants.CIVIL_ID_NEW,
											sessionStateManage.getLanguageId())
									.getFsBizComponentData()
									.getComponentDataId();
							if (idtypeCivilIdnew != null
									&& idtypeCivilIdnew
											.compareTo(getSelectCard()) == 0) {
								civilIdCheck = true;
							} else {
								civilIdCheck = false;
							}
						}

						if (civilIdCheck) {
							String id = getIdNumber();
							String dob = null;
							if (id.charAt(0) == '2') {
								dob = id.substring(5, 7) + "/"
										+ id.substring(3, 5) + "/19"
										+ id.substring(1, 3);
							} else {
								dob = id.substring(5, 7) + "/"
										+ id.substring(3, 5) + "/20"
										+ id.substring(1, 3);
							}
							SimpleDateFormat formatter = new SimpleDateFormat(
									"dd/MM/yyyy");
							Date date = formatter.parse(dob);
							setBirthdate(formatter.format(date));

							Calendar cal1 = Calendar.getInstance();
							cal1.setTime(new Date());
							cal1.add(Calendar.YEAR, -new Integer(
									Constants.AGE_LIMIT));
							Date today18 = cal1.getTime();

							if (getBirthdate() != null
									&& !formatter.parse(getBirthdate()).before(
											today18)) {
								RequestContext.getCurrentInstance().execute(
										"dobcheck.show();");
								return;
							} else {
								lstCountry = generalService
										.getCountryList(sessionStateManage
												.getLanguageId());
								nationalityList = generalService
										.getNationalityList(sessionStateManage
												.getLanguageId());

								// ValidateCustomerProcedure
								if (getCustomerCrNumber() != null
										&& !getCustomerCrNumber()
												.equalsIgnoreCase("")) {
									// setBooRenderOverseaCharges(true);
									backFromBenificaryStatusPanel();
								} else {
									// setBooRenderOverseaCharges(false);
									backFromBenificaryStatusPanel();
								}
							}
						} else {
							lstCountry = generalService
									.getCountryList(sessionStateManage
											.getLanguageId());
							nationalityList = generalService
									.getNationalityList(sessionStateManage
											.getLanguageId());

							// ValidateCustomerProcedure
							if (getCustomerCrNumber() != null
									&& !getCustomerCrNumber().equalsIgnoreCase(
											"")) {
								// setBooRenderOverseaCharges(true);
								backFromBenificaryStatusPanel();
							} else {
								// setBooRenderOverseaCharges(false);
								backFromBenificaryStatusPanel();
							}
						}
					}

				} else {
					setCardType(null);
					setBooRenderBenificaryFirstPanel(true);
					RequestContext.getCurrentInstance().execute(
							"activatecustomer.show();");
					return;
				}
			}
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2htransfercustomerinfo.xhtml");
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void backFromBenificaryStatusPanel() {
		setBooRenderBenificaryFirstPanel(false);
		// setBooRenderBenificarySearchPanel(true);
		// setBooRenderBeniListPanel(false);
		setBooRendercashdenomination(false);
		setBooMtcEnable(false);
	}

	// Second Method to Populate Records which is Approved all condition
	public void populateCustomerDetailsFromBeneRelation() {
		log.info("Entering into populateCustomerDetailsFromBeneRelation method ");

		// reset the dataTable
		resetFilters("form1:dataTable");

		setBooDenoEnable(true);
		try {

			if (coustomerBeneficaryDTList != null
					|| !coustomerBeneficaryDTList.isEmpty()) {
				coustomerBeneficaryDTList.clear();
			}

			List<BenificiaryListView> isCoustomerExist = iPersonalRemittanceService
					.getBeneficiaryCountryList(getCustomerNo(),
							getBeneficiaryCountryId());

			WesternUnionH2HBeneficaryDataTable personalRBDataTable = null;
			if (isCoustomerExist.size() > 0) {

				List<BenificiaryListView> duplicateCheck = new ArrayList<BenificiaryListView>();

				for (BenificiaryListView rel : isCoustomerExist) {

					if (duplicateCheck.isEmpty()) {
						duplicateCheck.add(rel);
						addBeneficiaryViewList(rel);
					} else {
						Boolean checkavailable = false;
						for (BenificiaryListView dupl : duplicateCheck) {
							if (dupl.getBeneficaryMasterSeqId().compareTo(
									rel.getBeneficaryMasterSeqId()) == 0) {
								if (dupl.getCurrencyId().compareTo(
										rel.getCurrencyId()) == 0) {
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
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

		log.info("Exit into populateCustomerDetailsFromBeneRelation method ");
	}

	public void addBeneficiaryViewList(BenificiaryListView rel) {

		WesternUnionH2HBeneficaryDataTable personalRBDataTable = new WesternUnionH2HBeneficaryDataTable();
		
		if(rel.getBankCode().equalsIgnoreCase("WU")){
			
		
			personalRBDataTable.setApplicationCountryId(rel
					.getApplicationCountryId());
			personalRBDataTable.setBenificaryName(rel.getBenificaryName());
			personalRBDataTable.setArbenificaryName(rel.getArbenificaryName());
			personalRBDataTable.setBeneficaryMasterSeqId(rel
					.getBeneficaryMasterSeqId());
			personalRBDataTable.setBeneficiaryAccountSeqId(rel
					.getBeneficiaryAccountSeqId());
			personalRBDataTable.setBenificaryCountry(rel.getCountryId());
			personalRBDataTable.setBenificaryCountryName(rel.getCountryName());
			personalRBDataTable.setCountryId(rel.getBenificaryCountry());
			personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName());
			personalRBDataTable.setBenificaryStatusId(rel.getBenificaryStatusId());
			personalRBDataTable.setBenificaryStatusName(rel
					.getBenificaryStatusName());
			personalRBDataTable.setCityName(rel.getCityName());
			personalRBDataTable.setCreatedBy(rel.getCreatedBy());
			personalRBDataTable.setCreatedDate(rel.getCreatedDate());
			personalRBDataTable.setCurrencyId(rel.getCurrencyId());
			personalRBDataTable.setCurrencyName(rel.getCurrencyName());
			personalRBDataTable
					.setCurrencyQuoteName(rel.getCurrencyQuoteName() == null ? ""
							: rel.getCurrencyQuoteName());
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
			personalRBDataTable.setBeneficiaryRelationShipSeqId(rel
					.getBeneficiaryRelationShipSeqId());
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
			personalRBDataTable.setBuildingNo(rel.getBuildingNo());
			
			List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),rel.getServiceGroupId());
			if (lstServiceGroupMasterDesc.size() > 0) {
				personalRBDataTable.setServiceGroupName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
			}

			coustomerBeneficaryDTList.add(personalRBDataTable);
		
		}

	}

	// Fetch Customer Details
	public void getCustomerDetails() {

		clearCustomerDetails();

		log.info("Entering into getCustomerDetails method ");

		if (customerDetailsList.size() != 0) {

			CustomerIdProof customerDetails = customerDetailsList.get(0);
			
			setCustomerName(customerDetails.getFsCustomer().getFirstName());
			setCustomerCrNumber(customerDetails.getFsCustomer().getCrNo() == null ? ""
					: customerDetails.getFsCustomer().getCrNo());
			setCustomerNo(customerDetails.getFsCustomer().getCustomerId());
			setCustomerrefno(customerDetails.getFsCustomer()
					.getCustomerReference());
			setFirstName(customerDetails.getFsCustomer().getFirstName());
			setSecondName(customerDetails.getFsCustomer().getMiddleName());
			setThirdName(customerDetails.getFsCustomer().getLastName());
			setCustomerFullName(nullCheck(getFirstName()) + " "
					+ nullCheck(getSecondName()) + " "
					+ nullCheck(getThirdName()));
			setCustomerFullName(nullCheck(getFirstName()) + " "
					+ nullCheck(getSecondName()));
			setCustomerLocalFullName(nullCheck(customerDetails.getFsCustomer()
					.getFirstNameLocal())
					+ " "
					+ nullCheck(customerDetails.getFsCustomer()
							.getMiddleNameLocal())
					+ " "
					+ nullCheck(customerDetails.getFsCustomer()
							.getLastNameLocal()));
			setCustomerIsActive(customerDetails.getFsCustomer().getIsActive());
			setCustomerExpDate(customerDetails.getIdentityExpiryDate());
			setCustomerTypeId(customerDetails
					.getFsBizComponentDataByCustomerTypeId()
					.getComponentDataId());

			BigDecimal customerTypeId= customerDetails
					.getFsBizComponentDataByIdentityTypeId()
					.getComponentDataId();
			if(customerTypeId.compareTo(new BigDecimal("198"))==0 || customerTypeId.compareTo(new BigDecimal("2000"))==0 )
			{
				setCustomerIDType("B");
				setCustomerTypeIdDesc("CIVIL ID");
			}else if(customerTypeId.compareTo(new BigDecimal("204"))==0 )
			{
				setCustomerIDType("A");
				setCustomerTypeIdDesc("PASSPORT");
			}
			
			
			String customerTypeString = iPersonalRemittanceService
					.getCustomerType(getCustomerTypeId());

			if (customerTypeString != null) {
				setCustomerType(customerTypeString);
			}
			if (getCustomerExpDate() != null) {
				setCustomerExpireDateMsg(new SimpleDateFormat("dd/MM/yyyy")
						.format(getCustomerExpDate()));
			}
			setNationality(customerDetails.getFsCustomer()
					.getFsCountryMasterByNationality().getCountryId());

			String nationality = null;
			nationality = generalService.getNationalityName(
					sessionStateManage.getLanguageId(), customerDetails
							.getFsCustomer().getFsCountryMasterByNationality()
							.getCountryId());

			if (nationality != null) {
				setNationalityName(nationality);
			}
			
			
			String countryName = null;
			countryName = generalService.getCountryName(
					sessionStateManage.getLanguageId(), customerDetails
							.getFsCustomer().getFsCountryMasterByNationality()
							.getCountryId());

			if (countryName != null) {
				setCustomerCountryName(countryName);
			}
			
			//setCustomerCountryName

			setDateOfBrith(customerDetails.getFsCustomer().getDateOfBirth());
			String teleCountryId = generalService
					.getTelephoneCountryBasedOnNationality(customerDetails
							.getFsCustomer().getFsCountryMasterByNationality()
							.getCountryId());
			setCountryCode(teleCountryId);
			setMcountryCode(teleCountryId);

			BigDecimal occupationID = generalService
					.getOccupationId(customerDetails.getFsCustomer()
							.getCustomerId());
			if (occupationID != null) {
				String occupation = generalService.getOccupationDesc(
						occupationID, sessionStateManage.getLanguageId());
				if (occupation != null) {
					setOccupation(occupation);
				} else {
					setOccupation("UN-EMPLOYEE");
				}
			} else {
				setOccupation("UN-EMPLOYEE");
			}

			setSenderIdType(getCustomerTypeId().toString());
			setSenderIdNumber(getIdNumber());
			// setSenderIdIssueCountry(get);
			setSenderOccupation(getOccupation());
			setSenderFirstName(getFirstName());
			setSenderLastName(getSecondName());
			String wuEnrollReference= iwuh2hService.getWUEnrollReference(customerDetails.getFsCustomer().getCustomerId());
			System.out.println("wu card no =="+wuEnrollReference);
			setSendWUcardNo(wuEnrollReference);
			//setCustomerTypeIdDesc("CIVIL ID");
			
			//setCustomerTelephone(customerDetails.getFsCustomer().getMobile());
			setCutomerMobileNumber(customerDetails.getFsCustomer().getMobile());

			if(getSendWUcardNo()==null){
				setEnableWUCardLookup("true");
				setEnableWUEnroll("true");
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
		setCustomerCountryName(null);
		setCustomerTelephone(null);
		setCutomerMobileNumber(null);
	}

	@PostConstruct
	public void loadIdType() {
		mapcomIdentityType = icustomerRegistrationService
				.getAllComponentComboDataForCustomer(
						sessionStateManage.getLanguageId(), "I",
						"Identity Type");
	}

	public void exitWU() {
		hideAllPanels();
		clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuhtohmoneytransfer.xhtml");
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::exitWU()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	// Hide All panels
	public void hideAllPanels() {
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(true);
		// setBooRenderBenificarySearchPanel(false);
		setBooRenderOldSmartCardPanel(false);
		// setBooRenderBeniListPanel(false);
		setBooRendercashdenomination(false);
		setBoorefundPaymentDetails(false);
		setBooRenderSendReceivePanel(false);
		// setBooSaveRender(false);
		setBooSaveDenomRender(false);
		setBooRenderPaymentDetails(false);
		setBooSaveAll(false);
		setBooCheckStatusPanel(false);

		setEnableCardLookup(false);
		// setEnableSendMoneyConfirm(false);
		setEnableSendMoneyTxnStatus(false);
		// setEnableSendTransferInfo(false);
		// setEnableBeneListData(false);
	}

	// back to mainpage
	public void clearHideAll() {
		clear();
		hideAllPanels();
	}

	// Call beneficiary creation from western union
	public void gotToNewBenificaryCreation() {

		try {
			
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("WUH2H", "WUH2H");
			
			BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean) context
					.getBean("beneficiaryCreationBean");
			// clearCustomerDetails();
			beneficiaryStatusList();
			setBooRenderBenificaryFirstPanel(false);
			// setBooRenderBenificarySearchPanel(false);
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
			// objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);

			objectBene.renderWesternUnionNavigation();

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../beneficary/beneficiaryCreation.xhtml");
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::gotToNewBenificaryCreation()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	private void beneficiaryStatusList() {
		benificaryStatusList.clear();
		benificaryStatusName.clear();
		List<BeneficaryStatus> beneStatus = iPersonalRemittanceService
				.getBeneficaryStatusList();
		List<CountryBranch> lstToCountryBranch = generalService
				.getBranchDetailsFromBeneStatus(sessionStateManage
						.getCountryId(),
						new BigDecimal(sessionStateManage.getBranchId()));
		if (lstToCountryBranch.size() != 0) {
			for (CountryBranch countryBranch : lstToCountryBranch) {
				if (countryBranch.getCorporateStatus().equalsIgnoreCase(
						Constants.Yes)) {
					benificaryStatusList.clear();
					benificaryStatusName.clear();
					benificaryStatusList.addAll(beneStatus);
					benificaryStatusName.addAll(beneStatus);
					break;
				} else {
					for (BeneficaryStatus finalListStatus : beneStatus) {
						if (!finalListStatus.getBeneficaryStatusName()
								.equalsIgnoreCase(Constants.Corporate)) {
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

		try{
			

			//afterSendWU();
			
			
			if (getTxnType() == 1) {

				if (getToalUsedAmount() != null && getCalNetAmountPaid() != null && getToalUsedAmount().compareTo(getCalNetAmountPaid()) >= 0) {
					
					if(getCashAmount().compareTo(getToalUsedAmount())!=0){
						setErrorMessage("Total Used amount shuld equal to collection amount !");
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}

				if (getTempCash() != null) {
					setCashAmount(GetRound.roundBigDecimal(getTempCash(),
							foreignLocalCurrencyDenominationService
									.getDecimalPerCurrency(new BigDecimal(
											sessionStateManage.getCurrencyId()))));
				

				// Call WUH2H send money validate service
				String validateMessage = wuH2HSendMoneyValidate();
				
				if(validateMessage!=null){
					setErrorMessage(validateMessage);
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
				
				renderingDenominationDT();
				denaMinLstData();
				setMainPanelRender(true);
				setBooRenderBenificaryFirstPanel(false);
				setBooRenderOldSmartCardPanel(false);
				setBooSaveDenomRender(false);
				setBooRenderPaymentDetails(false);

				if (lstData != null || !lstData.isEmpty()) {
					lstData.clear();
				}

				if (lstRefundData != null || !lstRefundData.isEmpty()) {
					lstRefundData.clear();
				}
				setBooRendercashdenomination(true);
				denaMinLstData();
				refundDenominationData();
				setBoorefundPaymentDetails(false);
				setBooSaveRender(false);

				setBooSaveAll(true);
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../wuh2h/wuhtohmoneytransfer.xhtml");
				}else{
					//renderingDenominationDT();
					//denaMinLstData();
					setMainPanelRender(true);
					setBooRenderBenificaryFirstPanel(false);
					setBooRenderOldSmartCardPanel(false);
					setBooSaveDenomRender(false);
					setBooRenderPaymentDetails(false);
					wuh2hSaveAll();
					String message = null;
					message = wuh2hSaveAll();
					
					if(message !=null){
						setErrorMessage(message);
						RequestContext.getCurrentInstance().execute("error.show();");
					}
				}
				
			}else {
				RequestContext.getCurrentInstance().execute("amountmatch.show();");
				return;
			}
				
			} else if (getTxnType() == 2) {
				//renderingDenominationDT();
				setDenomtotalCash(null);
				setSendPurposeOfTransactions(null);
				fetchLstData();
				
				//refundDenominationData();
				setBooRendercashdenomination(false);
				setBoorefundPaymentDetails(true);
				setBooSaveRender(false);
				setBooSaveAll(true);
				try {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("../wuh2h/wuh2hreceivemoneylocaldenom.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(getTxnType() == 3) {
				
				if (getToalUsedAmount() != null && getCalNetAmountPaid() != null) {
					if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) >= 0) {

					} else {
						RequestContext.getCurrentInstance().execute(
								"amountmatch.show();");
						return;
					}
				} else {
					setErrmsg("Amount Not Avaiable");
					RequestContext.getCurrentInstance().execute("csp.show();");
					return;
				}

				if (getTempCash() != null) {
					setCashAmount(GetRound.roundBigDecimal(getTempCash(),
							foreignLocalCurrencyDenominationService
									.getDecimalPerCurrency(new BigDecimal(
											sessionStateManage.getCurrencyId()))));
				}

				// Call WUH2H send money validate service
				//wuH2HSendMoneyValidate();

				renderingDenominationDT();
				denaMinLstData();
				setMainPanelRender(true);
				setBooRenderBenificaryFirstPanel(false);
				setBooRenderOldSmartCardPanel(false);
				setBooSaveDenomRender(false);
				setBooRenderPaymentDetails(false);

				if (lstData != null || !lstData.isEmpty()) {
					lstData.clear();
				}

				if (lstRefundData != null || !lstRefundData.isEmpty()) {
					lstRefundData.clear();
				}
				setBooRendercashdenomination(true);
				denaMinLstData();
				refundDenominationData();
				setBoorefundPaymentDetails(false);
				setBooSaveRender(false);

				setBooSaveAll(true);
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../wuh2h/wuhtohmoneytransfer.xhtml");
				
			}
			
			
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::nextpaneltoPaymentDetails()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		

	}

	// Render denomination data
	public void renderingDenominationDT() {
		if (coldatatablevalues.size() != 0) {
			BigDecimal paymentModeCashId = ipaymentService
					.fetchPaymodeMasterId(
							Constants.CASHNAME,
							new BigDecimal(
									sessionStateManage.isExists("languageId") ? sessionStateManage
											.getSessionValue("languageId")
											: "1"));
			if (paymentModeCashId != null) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					if (collectionlst.getColpaymentmodeIDtypeDT().compareTo(
							paymentModeCashId) == 0) {
						setDenomtotalCashreceived(getColamountKWD());
						setBooRendercashdenomination(true);
						setCollectedAmount(GetRound.roundBigDecimal(
								BigDecimal.ZERO,
								foreignLocalCurrencyDenominationService
										.getDecimalPerCurrency(new BigDecimal(
												sessionStateManage
														.getCurrencyId()))));
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
				if (collectionlst.getColpaymentmodeCode().equalsIgnoreCase(
						Constants.KNETCode)) {
					totalCardCash = totalCardCash.add(collectionlst
							.getColAmountDT());
				} else if (collectionlst.getColpaymentmodeCode()
						.equalsIgnoreCase(Constants.ChequeCode)) {
					totalCreditCardCash = totalCreditCardCash.add(collectionlst
							.getColAmountDT());
				}
			}
			setKnetValue(GetRound.roundBigDecimal(totalCardCash,
					foreignLocalCurrencyDenominationService
							.getDecimalPerCurrency(new BigDecimal(
									sessionStateManage.getCurrencyId()))));
			setChequeValue(GetRound.roundBigDecimal(totalCreditCardCash,
					foreignLocalCurrencyDenominationService
							.getDecimalPerCurrency(new BigDecimal(
									sessionStateManage.getCurrencyId()))));
		}
	}

	// Denomination List Data
	private void denaMinLstData() throws Exception {
		try {
			lstData.clear();
			ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
			
			 * Checking that it's first time or not, first time list size will
			 * be 0
			 
			// double sAmount = 0;
			localLstData.clear();
			if (localLstData.size() == 0) {
				 Responsible to show serial number in datatable 
				// int i = 0;
				// Responsible to hold each row in different bean object of
				// datatable

				List<CurrencyWiseDenomination> currencyWiseDenolst = iPersonalRemittanceService
						.getCurrencyDenominations(new BigDecimal(
								sessionStateManage.getCurrencyId()),
								sessionStateManage.getCountryId());
				int serial = 1;
				for (CurrencyWiseDenomination currencyDenObj : currencyWiseDenolst) {
					ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
					forLocalCurrencyDtObj.setSerial(serial);
					forLocalCurrencyDtObj.setItem(currencyDenObj
							.getDenominationAmount());
					forLocalCurrencyDtObj.setQty("");
					forLocalCurrencyDtObj.setPrice("");
					forLocalCurrencyDtObj.setDenominationId(currencyDenObj
							.getDenominationId());
					forLocalCurrencyDtObj.setCurrencyId(currencyDenObj
							.getExCurrencyMaster().getCurrencyId());
					forLocalCurrencyDtObj.setDenominationDesc(currencyDenObj
							.getDenominationDesc());
					forLocalCurrencyDtObj.setDenominationAmount(currencyDenObj
							.getDenominationAmount());

					localLstData.add(forLocalCurrencyDtObj);
					serial++;
				}

			}

			 Responsible to keep sum of total amount of cash entered 
			int totalSum = 0;
			 Responsible to calculate sum of entered cash amount 
			for (ForeignLocalCurrencyDataTable element : localLstData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum + Integer.parseInt(element.getPrice());
				}
			}
			System.out.println(totalSum);
			setDenomtotalCash(GetRound.roundBigDecimal(
					new BigDecimal(totalSum),
					foreignLocalCurrencyDenominationService
							.getDecimalPerCurrency(new BigDecimal(
									sessionStateManage.getCurrencyId()))));
			// setCollectedAmount(getDenomtotalCash());
			setPayNetPaidAmount(getCalNetAmountPaid());
			setPayPaidAmount(GetRound.roundBigDecimal(getToalUsedAmount(),
					foreignLocalCurrencyDenominationService
							.getDecimalPerCurrency(new BigDecimal(
									sessionStateManage.getCurrencyId()))));
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
			throw new Exception();

		}
	}

	// Refund denomination data
	private void refundDenominationData() {
		lstRefundData.clear();
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		 Checking that it's first time or not, first time list size will be 0 
		// double sAmount = 0;
		localLstData.clear();
		if (localLstData.size() == 0) {

			List<ViewStock> stockList = iPersonalRemittanceService
					.toCheckStockForView(sessionStateManage.getCountryId(),
							sessionStateManage.getUserName(),
							sessionStateManage.getBranchId(),
							sessionStateManage.getCompanyId(),
							sessionStateManage.getCurrencyId());

			int serial = 1;
			for (ViewStock viewStockObj : stockList) {
				ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
				forLocalCurrencyDtObj.setSerial(serial);
				forLocalCurrencyDtObj.setItem(viewStockObj
						.getDenominationAmount());
				forLocalCurrencyDtObj.setQty("");
				forLocalCurrencyDtObj.setPrice("");
				if (viewStockObj.getCurrentStock() != null) {
					forLocalCurrencyDtObj.setStock(viewStockObj
							.getCurrentStock().intValue());
				} else {
					forLocalCurrencyDtObj.setStock(0);
				}
				forLocalCurrencyDtObj.setDenominationId(viewStockObj
						.getDenominationId());
				forLocalCurrencyDtObj.setCurrencyId(viewStockObj
						.getCurrencyId());
				forLocalCurrencyDtObj.setDenominationDesc(viewStockObj
						.getDenominationDEsc());
				forLocalCurrencyDtObj.setDenominationAmount(viewStockObj
						.getDenominationAmount());

				localLstData.add(forLocalCurrencyDtObj);
				serial++;
			}

		}

		 Responsible to keep sum of total amount of cash entered 
		int totalSum = 0;
		 Responsible to calculate sum of entered cash amount 
		for (ForeignLocalCurrencyDataTable element : localLstData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum + Integer.parseInt(element.getPrice());
			}
		}
		System.out.println("Refund  :" + totalSum);
		if (getTxnType() == 1) {
			setRefundAmount(getPayRefund());
			setCollectedRefundAmount(new BigDecimal(0));
			setLstRefundData(localLstData);
			// Added by Rabil
		} else if (getTxnType() == 2) {
			setDenomtotalCash(GetRound.roundBigDecimal(
					new BigDecimal(totalSum),
					foreignLocalCurrencyDenominationService
							.getDecimalPerCurrency(new BigDecimal(
									sessionStateManage.getCurrencyId()))));
			System.out.println("getTxnType()==2 getDenomtotalCash :"
					+ getDenomtotalCash());
			setPayNetPaidAmount(getCalNetAmountPaid());
			System.out.println("getTxnType()==2 getPayNetPaidAmount :"
					+ getPayNetPaidAmount());
			setPayPaidAmount(GetRound.roundBigDecimal(getToalUsedAmount(),
					foreignLocalCurrencyDenominationService
							.getDecimalPerCurrency(new BigDecimal(
									sessionStateManage.getCurrencyId()))));
			System.out.println("getPayPaidAmount() :" + getPayPaidAmount()
					+ "\t getPayNetPaidAmount() :" + getPayNetPaidAmount());
			setPayRefund(getPayNetPaidAmount());// getPayPaidAmount().subtract(getPayNetPaidAmount()));
			System.out.println("getTxnType()==2 setPayRefund :"
					+ getPayRefund());
			setLstRefundData(localLstData);
			//setRefundAmount(getPayNetPaidAmount());
			setNextOrSaveButton(Constants.Save);
		}
		if (localLstData.size() != 0) {
			setDenominationchecking(Constants.DenominationAvailable);
		} else {
			setDenominationchecking(Constants.DenominationNotAvailable);
		}

	}

	// Refund cell edit
	public void onRefundCellEdit(
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {

		int quantity = 0;

		if (foreignLocalCurrencyDataTable.getQty() == ""
				&& foreignLocalCurrencyDataTable.getQty() != null) {
			quantity = 0;
		} else {
			try {
				quantity = Integer.parseInt(foreignLocalCurrencyDataTable
						.getQty());

			} catch (Exception e) {
				System.out.println("Exception occured" + e.getMessage());
				quantity = 0;

			}
		}

		if (foreignLocalCurrencyDataTable.getStock() >= quantity
				&& quantity != 0) {

			BigDecimal totalcashAmount = foreignLocalCurrencyDataTable
					.getItem().multiply(
							new BigDecimal(foreignLocalCurrencyDataTable
									.getQty() == "" ? "0"
									: foreignLocalCurrencyDataTable.getQty()));
			if (foreignLocalCurrencyDataTable.getQty().equals("")) {
				foreignLocalCurrencyDataTable.setQty("");
				// added by rabil for clear if blank
				foreignLocalCurrencyDataTable.setPrice("");
			}
			if (totalcashAmount != null && totalcashAmount.doubleValue() != 0) {
				foreignLocalCurrencyDataTable.setPrice(GetRound
						.roundBigDecimal(
								totalcashAmount,
								foreignLocalCurrencyDenominationService
										.getDecimalPerCurrency(new BigDecimal(
												sessionStateManage
														.getCurrencyId())))
						.toPlainString());
			} else {
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
						totalSum = totalSum.add(new BigDecimal(element
								.getPrice()));
					}
				}
				setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,
						foreignLocalCurrencyDenominationService
								.getDecimalPerCurrency(new BigDecimal(
										sessionStateManage.getCurrencyId()))));
				foreignLocalCurrencyDataTable.setQty("");
				foreignLocalCurrencyDataTable.setPrice("");
				RequestContext.getCurrentInstance().execute(
						"clearrefunddenominationexceed.show();");
				return;

			} else {
				setCollectedRefundAmount(GetRound.roundBigDecimal(totalSum,
						foreignLocalCurrencyDenominationService
								.getDecimalPerCurrency(new BigDecimal(
										sessionStateManage.getCurrencyId()))));
			}
		} else {
			RequestContext.getCurrentInstance().execute(
					"stockNotAvailable.show();");
			foreignLocalCurrencyDataTable.setQty("");
			foreignLocalCurrencyDataTable.setPrice("");
		}
	}

	// Collection cell edit
	public void onCellEdit(
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {
		
		try{
			
			BigDecimal qty = null;

			if (foreignLocalCurrencyDataTable.getQty() == ""
					&& foreignLocalCurrencyDataTable.getQty() != null) {
				qty = new BigDecimal(0);
				System.out
						.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq ZEROOOOOOOOOOOOOOOO");
			} else {
				System.out.println("foreignLocalCurrencyDataTable.getQty()"
						+ foreignLocalCurrencyDataTable.getQty());

				qty = new BigDecimal(foreignLocalCurrencyDataTable.getQty());
			}
			BigDecimal totalcashAmount = null;
			totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(
					qty);

			System.out.println("@@@@@@@@@@@@@@@@@"
					+ foreignLocalCurrencyDataTable.getQty() == null);
			System.out.println("#################"
					+ foreignLocalCurrencyDataTable.getQty().equals(""));

			if (foreignLocalCurrencyDataTable.getQty().equals("")) {
				foreignLocalCurrencyDataTable.setQty("");
				// added by rabil for clear if blank
				foreignLocalCurrencyDataTable.setPrice("");
			}
			if (totalcashAmount != null && totalcashAmount.doubleValue() != 0.0) {

				foreignLocalCurrencyDataTable.setPrice(GetRound
						.roundBigDecimal(
								totalcashAmount,
								foreignLocalCurrencyDenominationService
										.getDecimalPerCurrency(new BigDecimal(
												sessionStateManage
														.getCurrencyId())))
						.toPlainString());
			} else {
				foreignLocalCurrencyDataTable.setPrice("");
			}
			BigDecimal totalSum = BigDecimal.ZERO;
			 Responsible to calculate sum of entered cash amount 
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
						foreignLocalCurrencyDenominationService
								.getDecimalPerCurrency(new BigDecimal(
										sessionStateManage.getCurrencyId()))));
				setCollectedAmount(getDenomtotalCash());
				RequestContext.getCurrentInstance().execute(
						"cleardenominationexceed.show();");
				setDataTableClear(foreignLocalCurrencyDataTable);
				foreignLocalCurrencyDataTable.setQty("");
				return;

			} else {
				setDenomtotalCash(GetRound.roundBigDecimal(totalSum,
						foreignLocalCurrencyDenominationService
								.getDecimalPerCurrency(new BigDecimal(
										sessionStateManage.getCurrencyId()))));
				setCollectedAmount(getDenomtotalCash());
			}
			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::onCellEdit()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

		

	}

	public void clearDataTableClearDenomination() {
		if (getDataTableClear() != null) {
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable = getDataTableClear();

			System.out.println("foreignLocalCurrencyDataTable"
					+ foreignLocalCurrencyDataTable);
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../wuh2h/wuhtohmoneytransfer.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// calculation of cash while entering
	public void checkcashcollection() {
		setToalUsedAmount(getCashAmount());
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



	// Saving record for western union in Beneficiary Account
	public BeneficaryAccount saveBeneficiaryAccountDetails(
			WesternUnionH2HBeneficaryDataTable selectedRecord,
			BigDecimal bankCountryId, BigDecimal bankCurrencyId) {

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

			List<BankMaster> bankMasterdetails = bankMasterService
					.getBankMasterInfo(Constants.WU_BANK_CODE);

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

					List<BankBranch> bankBranchdetails = bankBranchDetailsService
							.getBranchCodebyBank(bankMasterId.getBankId());

					if (bankBranchdetails != null
							&& bankBranchdetails.size() != 0) {

						BankBranch bankBranchId = bankBranchdetails.get(0);

						if (bankBranchId.getBankBranchId() != null) {
							dbankBranchId = bankBranchId.getBankBranchId();
							BankBranch bankBranch = new BankBranch();
							bankBranch.setBankBranchId(bankBranchId
									.getBankBranchId()); // western
							// union
							// bank
							// branch
							// id
							beneficaryAccount.setBankBranch(bankBranch);

							beneficaryAccount.setBankBranchCode(bankBranchId
									.getBranchCode()); // western
							// union
							// bank
							// branch
							// code
						}
					}
				}
			}

			List<ServiceGroupMaster> lstServiceGroup = serviceGroupMasterService
					.toServiceGroupCodeAllValues(Constants.CashCode);
			if (lstServiceGroup.size() != 0) {
				ServiceGroupMaster serviceGroup = lstServiceGroup.get(0);

				if (serviceGroup.getServiceGroupId() != null) {
					ServiceGroupMaster serviceGroupMaster = new ServiceGroupMaster();
					serviceGroupMaster.setServiceGroupId(serviceGroup
							.getServiceGroupId()); // cash
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
				beneficaryMaster.setBeneficaryMasterSeqId(selectedRecord
						.getBeneficaryMasterSeqId());
				beneficaryAccount.setBeneficaryMaster(beneficaryMaster);
			}

			iPersonalRemittanceService
					.saveBeneficiaryAccount(beneficaryAccount);

			// saving relation with master_id , relation_id and account_id
			BeneficaryRelationship beneficaryRelationship = saveBeneficiaryRelationshipDetails(
					selectedRecord, beneficaryAccount);

			if (selectedRecord.getBeneficaryMasterSeqId() != null
					&& dbankId != null
					&& dbankBranchId != null
					&& beneficaryAccount.getBeneficaryAccountSeqId() != null
					&& beneficaryAccount.getCurrencyId().getCurrencyId() != null
					&& beneficaryRelationship.getCustomerId().getCustomerId() != null) {
				String errorMessage = beneficaryCreation.getBeneDetailProce(
						selectedRecord.getBeneficaryMasterSeqId(), dbankId,
						dbankBranchId,
						beneficaryAccount.getBeneficaryAccountSeqId(),
						beneficaryAccount.getCurrencyId().getCurrencyId(),
						beneficaryRelationship.getCustomerId().getCustomerId());
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
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ e.getMessage());
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return beneficaryAccount;
		}

		return beneficaryAccount;
	}

	// saving record for western union in beneficiary relationship
	public BeneficaryRelationship saveBeneficiaryRelationshipDetails(
			WesternUnionH2HBeneficaryDataTable selectedRecord,
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
			beneficaryRelationship.setCreatedBy(sessionStateManage
					.getUserName());
			beneficaryRelationship.setCreatedDate(new Date());
			beneficaryRelationship.setBeneficaryAccount(beneficaryAccount);

			if (selectedRecord.getBeneficaryMasterSeqId() != null) {
				BeneficaryMaster beneficaryMaster = new BeneficaryMaster();
				beneficaryMaster.setBeneficaryMasterSeqId(selectedRecord
						.getBeneficaryMasterSeqId());
				beneficaryRelationship.setBeneficaryMaster(beneficaryMaster);
			}

			iPersonalRemittanceService
					.saveBeneficiaryRelationship(beneficaryRelationship);

		} catch (Exception e) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ e.getMessage());
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

		return beneficaryRelationship;
	}

	public void redirectToCustomerFirstPage()  {
		
		try{
			
			CustomerPersonalInfoBean customerPesonel = (CustomerPersonalInfoBean) appContext
					.getBean("customerPersonalInfo");
			customerPesonel.resetValues();

			customerPesonel.setSelectType(Constants.MANUAL);
			customerPesonel.setBooManual(true);
			customerPesonel.setBooIdType(true);
			customerPesonel.setSelectedIdType(getSelectCard().toString());
			log.info("==========REDIRECT==========" + getSelectCard().toString()
					+ "" + getIdNumber());
			if (getIdNumber() != null) {
				customerPesonel.setIdNumber(getIdNumber().toString());
			} else {
				customerPesonel.setIdNumber(null);
			}
			// customerPesonel.appearCarddetail() ;
			customerPesonel.setIdType(getSelectCard().toString());
			customerPesonel.setMobileNoFetch(null);
			customerPesonel.checkingIdWithDBForProcessing();

			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/customerregistrationmain.xhtml");
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::redirectToCustomerFirstPage()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		

	}

	public void redirectToCustomerPage()  {
		
		try{
			CustomerPersonalInfoBean customerPesonel = (CustomerPersonalInfoBean) appContext
					.getBean("customerPersonalInfo");
			customerPesonel.resetValues();

			customerPesonel.setSelectType(Constants.MANUAL);
			customerPesonel.setBooManual(true);
			customerPesonel.setBooIdType(true);
			customerPesonel.setSelectedIdType(getSelectCard().toString());
			log.info("==========REDIRECT==========" + getSelectCard().toString()
					+ "" + getIdNumber());
			if (getIdNumber() != null) {
				customerPesonel.setIdNumber(getIdNumber().toString());
			} else {
				customerPesonel.setIdNumber(null);
			}
			// customerPesonel.appearCarddetail() ;
			customerPesonel.setIdType(getSelectCard().toString());
			customerPesonel.setMobileNoFetch(null);
			customerPesonel.checkingIdWithDBForProcessing();
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/customermanualinfo.xhtml");
			
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::redirectToCustomerPage()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		

	}

	public void afterSendWU() {

		try {
			List<WesternUnionTransfer> wutxndtl = new ArrayList<WesternUnionTransfer>();
			BigDecimal sendAmount = null;
			if (getTxnType() == 1) {
				String trnxType = "S";

				sendAmount = getCashAmount();
				setPayNetPaidAmount(getSendGrossTotalAmount());
				setColCurrency("KWD");
				setCalNetAmountPaid(getSendGrossTotalAmount());
				setCollectionDocumentNo(getSendDocumentNo());
				setColpaymentmodeName("Cash");

			} else if (getTxnType() == 2) {
				setPayNetPaidAmount(getSendGrossTotalAmount());
				setColCurrency("KWD");
				setCalNetAmountPaid(getSendGrossTotalAmount());
				setCollectionDocumentNo(getReceiveDocumentNo());
				setColpaymentmodeName("Cash");
			}

			setMainPanelRender(true);
			setBooRenderBenificaryFirstPanel(false);
			setBooRenderOldSmartCardPanel(false);
			setBooRendercashdenomination(false);
			setBoorefundPaymentDetails(false);
			setBooRenderPaymentDetails(true);
			setBooSaveDenomRender(false);
			setBooCheckStatusPanel(false);
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::afterSendWU()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	// to render panel 8 - Refund Denomination details
	public void nextpaneltoRefundDenomination() {
		// first panel
		setMainPanelRender(true);
		setBooRenderOldSmartCardPanel(false);
		setBooRenderBenificaryFirstPanel(false);
		// setBooRenderBenificarySearchPanel(false);
		setBooRendercashdenomination(false);
		// setBooRenderBeniListPanel(false);
		setBoorefundPaymentDetails(true);
		setBooRenderPaymentDetails(false);
		setBooSaveDenomRender(false);
		setBooCheckStatusPanel(false);
		refundDenominationData();
	}

	public void backFromBeneList() {

		try {

			// clearing token number , doc code , doc no , company code
			setReceiveCompanyCode(null);
			setReceiveDocumentCode(null);
			setReceiveDocumentFinanceYear(null);
			setReceiveDocumentNo(null);
			setTxnType(0);
			// setEnableBeneListData(false);

			if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
				selectedWUBeneList.clear();
			}
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2htransfercustomerinfo.xhtml");
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::checkWUStatus()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void gotoMainPage() {
		clear();
		setMainPanelRender(true);
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderOldSmartCardPanel(true);
		// setBooRenderBenificarySearchPanel(false);
		// setBooRenderBeniListPanel(false);
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
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH))
				+ "/" + year;
	}

	public String getDocumentSerialCollectTrnx(String processIn) {
		System.out.println("Start getDocumentSerialID ...." + getFinaceYear());
		log.info("process in :" + processIn);
		String documentSerialId = generalService
				.getNextDocumentReferenceNumber(
						Integer.parseInt(sessionStateManage
								.getSessionValue("countryId")),
						Integer.parseInt(sessionStateManage
								.getSessionValue("companyId")),
						Integer.parseInt(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),
						getFinaceYear().intValue(), processIn,
						sessionStateManage.getCountryBranchCode());
		System.out.println("end getDocumentSerialID  :" + documentSerialId);
		return documentSerialId;
	}

	// Source Of Income List
	public List<SourceOfIncomeDescription> getSourceOfIncomeList() {
		try {
			lstSourceOfIncomes = getForeignCurrencyPurchaseService()
					.getSourceofIncome(sessionStateManage.getLanguageId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstSourceOfIncomes;
	}

	// Purpose of Transaction List
	public List<PurposeOfTransaction> getPurposeOfTransactionsList() {
		try {
			lstPurposeOfTransactions = getForeignCurrencyPurchaseService()
					.getAllPurposeOfTransaction();
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::nextpaneltoPaymentDetails()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			if(lstPurposeOfTransactions!=null)
			{
				lstPurposeOfTransactions.clear();
			}
			return lstPurposeOfTransactions;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			if(lstPurposeOfTransactions!=null)
			{
				lstPurposeOfTransactions.clear();
			}
			return lstPurposeOfTransactions;
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
		setAcknowledgement(true);
		setWuCardLevelCode(null);
		setSendWUcardNo(null);
		setPromotionCode(null);
		setWuCardno(null);
		// setLstData(null);
		// setLstRefundData(null);
		// setLstselectedrecord(null);
		if (lstData != null || !lstData.isEmpty()) {
			lstData.clear();
		}

		if (lstRefundData != null || !lstRefundData.isEmpty()) {
			lstRefundData.clear();
		}
		setEnableWUEnroll("false");
		setEnableWUCardLookup("false");
		setReceiverMessage(null);
		coldatatablevalues.clear();
		setTotalAmount(null);
		setToalUsedAmount(null);
		setTotalbalanceAmount(null);
		setTotalrefundAmount(null);
		
	}

	public void populateBeneFiciaryCountry() {
		try {
			if (allBeneCountryList != null || !allBeneCountryList.isEmpty()) {
				allBeneCountryList.clear();
			}

			if (coustomerBeneficaryDTList != null
					|| !coustomerBeneficaryDTList.isEmpty()) {
				coustomerBeneficaryDTList.clear();
			}

			List<PopulateData> countryList = iPersonalRemittanceService
					.getBeneficaryList(getCustomerNo());

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

	public void differentCurrencyDetails(
			WesternUnionH2HBeneficaryDataTable beneAccountCurrencyDetails) {
		System.out.println(beneAccountCurrencyDetails);
		setSelectWesternUnionBeneRec(beneAccountCurrencyDetails);
		setBenifisCountryId(null);
		setBenifisCurrencyId(null);
		if (beneServiceCurrencyList != null
				|| !beneServiceCurrencyList.isEmpty()) {
			beneServiceCurrencyList.clear();
		}
		lstCountry = generalService.getCountryList(sessionStateManage
				.getLanguageId()); // beneficiary
		// bank
		// country
		// list
		RequestContext.getCurrentInstance()
				.execute("beneCurrencyPanel.show();");
	}

	public void benServiceCurrencyListDetails(AjaxBehaviorEvent event) {
		setBenifisCurrencyId(null);
		if (getBenifisCountryId() != null) {
			beneServiceCurrencyList = iPersonalRemittanceService
					.getViewBeneCurrency(getBenifisCountryId()); // beneficiary
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

			WesternUnionH2HBeneficaryDataTable westerUnionSelected = getSelectWesternUnionBeneRec();
			// checking bank_country , bank_id , bank_branch_id , currency_id
			// with master_seq_id
			if (getBenifisCountryId() != null && getBenifisCurrencyId() != null
					&& getSelectWesternUnionBeneRec() != null) {

				List<BankMaster> bankMasterdetails = bankMasterService
						.getBankMasterInfo(Constants.WU_BANK_CODE);

				if (bankMasterdetails != null && bankMasterdetails.size() != 0) {

					BankMaster bankMasterId = bankMasterdetails.get(0);

					if (bankMasterId.getBankId() != null) {
						dbankId = bankMasterId.getBankId(); // setting bankID
						dbankCode = Constants.WU_BANK_CODE; // western union
						// bank code

						List<BankBranch> bankBranchdetails = bankBranchDetailsService
								.getBranchCodebyBank(bankMasterId.getBankId());

						if (bankBranchdetails != null
								&& bankBranchdetails.size() != 0) {

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

				if (getBenifisCountryId() != null
						&& dbankId != null
						&& dbankBranchId != null
						&& getBenifisCurrencyId() != null
						&& westerUnionSelected.getBeneficaryMasterSeqId() != null) {
					boolean checkWesternUnionCountryCurrency = westernUnionService
							.checkBeneficaryAccountDetailsForWUnion(
									getBenifisCountryId(), dbankId,
									dbankBranchId, getBenifisCurrencyId(),
									westerUnionSelected
											.getBeneficaryMasterSeqId());
					if (checkWesternUnionCountryCurrency) {
						// record already Available
						setErrmsg("BanK Country and Currency already Available for Beneficary");
						RequestContext.getCurrentInstance().execute(
								"csp.show();");
						return;
					} else {
						// insert to database
						saveBeneficiaryAccountDetails(
								getSelectWesternUnionBeneRec(),
								getBenifisCountryId(), getBenifisCurrencyId());
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
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(clearDataTable);
		if (dataTable != null) {
			dataTable.reset();
		}
	}

	// search operation in Personal remittance
	public void searchClicked() {
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("request", "wuBean");
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}
	}

	public void deleteBeneRelationIsActive(
			WesternUnionH2HBeneficaryDataTable deletebene) {

		setDeleteBeneRelationId(null);

		List<BeneficaryAccount> beneAccDt = beneficaryCreation
				.getBeneAccountDetails(deletebene.getBeneficaryMasterSeqId(),
						deletebene.getCurrencyId(), Constants.WU_BANK_CODE);

		// only western union records to deleted not the banking channel or cash
		// product
		if (beneAccDt != null && beneAccDt.size() != 0) {
			BigDecimal beneAccountSeqId = beneAccDt.get(0)
					.getBeneficaryAccountSeqId();

			List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices
					.checkBenificaryRelationExist(
							deletebene.getBeneficaryMasterSeqId(),
							beneAccountSeqId);
			if (lstBeneRelationShip.size() == 1) {

				setDeleteBeneRelationId(lstBeneRelationShip.get(0)
						.getBeneficaryRelationshipId());
				setExceptionMessage("Do you want to delete Western Union Beneficiary Account?");
				RequestContext.getCurrentInstance().execute(
						"deleteBeneAcc.show();");

			}

			setDeleteBeneRelationId(lstBeneRelationShip.get(0)
					.getBeneficaryRelationshipId());

		} else {
			setErrmsg("Beneficiary Doesn't Belong to WESTERN UNION");
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	public void deleteBeneficiaryAccount() {
		if (getDeleteBeneRelationId() != null) {
			BigDecimal deletebeneRel = getDeleteBeneRelationId();
			if (deletebeneRel != null) {
				String status = Constants.D;
				// iPersonalRemittanceService.deleteBeneAccountRecord(deletebene.getBeneficiaryAccountSeqId()
				// ,deletebene.getBeneficiaryRelationShipSeqId(),status);
				beneficaryCreation.deleteBeneRelationRecord(deletebeneRel,
						status);
				if (getBeneficiaryCountryId() != null) {
					// populateCustomerDetailsFromBeneRelation();
					populateBeneFiciaryCountry();
				}
			}
		}
	}

	public void editWUBenificary(
			WesternUnionH2HBeneficaryDataTable datatabledetails) {

		try {

			log.info("Exit into edit method ");

			// checking beneficiary Account for that master and currency and
			// western union code
			List<BeneficaryAccount> beneAccDt = beneficaryCreation
					.getBeneAccountDetails(
							datatabledetails.getBeneficaryMasterSeqId(),
							datatabledetails.getCurrencyId(),
							Constants.WU_BANK_CODE);

			// only western union records to deleted not the banking channel or
			// cash product
			if (beneAccDt != null && beneAccDt.size() != 0) {
				BigDecimal beneAccountSeqId = beneAccDt.get(0)
						.getBeneficaryAccountSeqId();

				// update if deleted D to activate Y
				List<BeneficaryRelationship> lstBeneRelationShip = ibeneCountryServices
						.checkBenificaryRelationExist(
								datatabledetails.getBeneficaryMasterSeqId(),
								beneAccountSeqId);
				if (lstBeneRelationShip.size() != 0) {

					BeneficaryRelationship beneRel = lstBeneRelationShip.get(0);

					BigDecimal beneRelationShipId = beneRel
							.getBeneficaryRelationshipId();

					if (!beneRel.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						String status = Constants.Yes;
						iPersonalRemittanceService.deleteBeneAccountRecord(
								beneAccountSeqId, beneRelationShipId, status);
					}
				}

				PersonalRemmitanceBeneficaryDataTable pdatatabledetails = populateCustomerBeneDetails(
						datatabledetails, beneAccountSeqId);
				fetchAllRecordForNonTrnx(pdatatabledetails);
			} else {
				setErrmsg("Beneficiary Doesn't Belong to WESTERN UNION");
				RequestContext.getCurrentInstance().execute("csp.show();");
			}

			
			 * BeneficiaryCreationBean objectBene =
			 * (BeneficiaryCreationBean)appContext
			 * .getBean("beneficiaryCreationBean"); //
			 * objectBene.setDataTableBeneObj(datatabledetails);
			 * 
			 * objectBene.setDataTableBeneWUObj(datatabledetails);
			 * objectBene.renderBeneficiaryFullWUEditNavigation();
			 * context.redirect
			 * ("../beneficary/firsttimebeneficarywuedit.xhtml");
			 

		} catch (Exception e) {
			setProcedureError(e.getMessage());
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
		}

	}

	// Second Method to Populate Records which is Approved all condition
	public PersonalRemmitanceBeneficaryDataTable populateCustomerBeneDetails(
			WesternUnionH2HBeneficaryDataTable datatabledetails,
			BigDecimal beneAccountSeqId) {
		resetFilters("form1:dataTable");

		log.info("Entering into populateCustomerBeneDetails method ");

		PersonalRemmitanceBeneficaryDataTable personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();

		try {

			log.info("=====================getCustomerNo()" + getCustomerNo()
					+ " : getBeneficiaryCountryId() : "
					+ getBeneficiaryCountryId());
			List<BenificiaryListView> isCoustomerExist = iPersonalRemittanceService
					.getBeneficiaryDtFromView(datatabledetails.getCustomerId(),
							datatabledetails.getCountryId(),
							datatabledetails.getBeneficaryMasterSeqId(),
							datatabledetails.getCurrencyId(),
							Constants.WU_BANK_CODE, beneAccountSeqId);
			log.info("=====================" + isCoustomerExist.size());

			if (isCoustomerExist.size() == 1) {

				BenificiaryListView rel = isCoustomerExist.get(0);

				personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();

				personalRBDataTable.setAccountStatus(rel.getAccountStatus());
				personalRBDataTable.setAge(rel.getAge());
				personalRBDataTable.setApplicationCountryId(rel
						.getApplicationCountryId());
				personalRBDataTable.setArbenificaryName(rel
						.getArbenificaryName());
				personalRBDataTable.setBankAccountNumber(rel
						.getBankAccountNumber());
				personalRBDataTable.setBankBranchName(rel.getBankBranchName());
				personalRBDataTable.setBankCode(rel.getBankCode());
				personalRBDataTable.setBankId(rel.getBankId());
				personalRBDataTable.setBankName(rel.getBankName());
				personalRBDataTable.setBeneficaryMasterSeqId(rel
						.getBeneficaryMasterSeqId());
				personalRBDataTable.setBeneficiaryAccountSeqId(rel
						.getBeneficiaryAccountSeqId());
				personalRBDataTable.setBeneficiaryRelationShipSeqId(rel
						.getBeneficiaryRelationShipSeqId());
				personalRBDataTable.setBenificaryCountry(rel.getCountryId()); // bene
																				// country
																				// Id
				personalRBDataTable.setBenificaryCountryName(rel
						.getCountryName()); // bene country Name
				personalRBDataTable.setBenificaryName(rel.getBenificaryName());
				personalRBDataTable.setBenificaryStatusId(rel
						.getBenificaryStatusId());
				personalRBDataTable.setBenificaryStatusName(rel
						.getBenificaryStatusName());
				personalRBDataTable
						.setBooDisabled(rel.getBankAccountNumber() != null ? true
								: false);
				personalRBDataTable.setBranchCode(rel.getBranchCode());
				personalRBDataTable.setBranchId(rel.getBranchId());
				personalRBDataTable.setCityName(rel.getCityName());
				personalRBDataTable.setCountryId(rel.getBenificaryCountry()); // bank
																				// country
																				// Id
				personalRBDataTable.setCountryName(rel
						.getBenificaryBankCountryName()); // bank country Name
				personalRBDataTable.setCreatedBy(rel.getCreatedBy());
				personalRBDataTable.setCreatedDate(rel.getCreatedDate());
				personalRBDataTable.setCurrencyId(rel.getCurrencyId());
				personalRBDataTable.setCurrencyName(rel.getCurrencyName());
				personalRBDataTable.setCurrencyQuoteName(rel
						.getCurrencyQuoteName() == null ? "" : rel
						.getCurrencyQuoteName());
				personalRBDataTable.setCustomerId(rel.getCustomerId());
				personalRBDataTable.setDateOfBirth(rel.getDateOfBirth());
				personalRBDataTable.setDistrictName(rel.getDistrictName());
				personalRBDataTable.setFiftheName(rel.getFiftheName());
				personalRBDataTable.setFifthNameLocal(rel.getFifthNameLocal());
				personalRBDataTable.setFirstName(rel.getFirstName());
				personalRBDataTable.setFirstNameLocal(rel.getFirstNameLocal());
				personalRBDataTable.setFourthName(rel.getFourthName());
				personalRBDataTable
						.setFourthNameLocal(rel.getFourthNameLocal());
				personalRBDataTable.setIsActive(rel.getIsActive());
				personalRBDataTable.setLocation(rel.getNationalityName());
				personalRBDataTable.setModifiedBy(rel.getModifiedBy());
				personalRBDataTable.setModifiedDate(rel.getModifiedDate());
				personalRBDataTable.setNationality(rel.getNationality());
				personalRBDataTable
						.setNationalityName(rel.getNationalityName());
				personalRBDataTable.setNoOfRemittance(rel.getNoOfRemittance());
				personalRBDataTable.setOccupation(rel.getOccupation());
				personalRBDataTable.setRelationShipId(rel.getRelationShipId());
				personalRBDataTable.setRelationShipName(rel
						.getRelationShipName());
				personalRBDataTable.setRemarks(rel.getRemarks());
				personalRBDataTable
						.setSecondNameLocal(rel.getSecondNameLocal());
				personalRBDataTable.setSecondName(rel.getSecondName());
				personalRBDataTable.setServiceGroupCode(rel
						.getServiceGroupCode());
				personalRBDataTable.setServiceGroupId(rel.getServiceGroupId());
				personalRBDataTable
						.setServiceProvider(rel.getServiceProvider());
				personalRBDataTable.setStateName(rel.getStateName());
				personalRBDataTable.setMapSequenceId(rel.getMapSequenceId());
				// Bene Details Added @koti 10/08/2016
				StringBuffer strAdd = new StringBuffer();
				if (rel.getStateName() != null) {
					strAdd.append(rel.getStateName());
				}
				if (rel.getDistrictName() != null) {
					strAdd.append("," + rel.getDistrictName());
				}
				if (rel.getCityName() != null) {
					strAdd.append("," + rel.getCityName());
				}
				if (strAdd != null) {
					personalRBDataTable
							.setBeneAddressDetails(strAdd.toString());
				}

				personalRBDataTable.setBeneHouseNo(rel.getBuildingNo());
				personalRBDataTable.setBeneFlatNo(rel.getFlatNo());
				personalRBDataTable.setBeneStreetNo(rel.getStreetNo());

				// For swift beneficiary
				personalRBDataTable.setSwiftBic(rel.getSwiftBic());
				if (rel.getLastEmosRemittance() == null) {
					personalRBDataTable.setTransactinStatus(Constants.NO);
				} else {
					personalRBDataTable.setTransactinStatus(Constants.YES);
				}

				List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService
						.LocalServiceGroupDescription(
								new BigDecimal(
										sessionStateManage
												.isExists("languageId") ? sessionStateManage
												.getSessionValue("languageId")
												: "1"), rel.getServiceGroupId());
				if (lstServiceGroupMasterDesc.size() > 0) {
					personalRBDataTable
							.setServiceGroupName(lstServiceGroupMasterDesc.get(
									0).getServiceGroupDesc());
				}

				List<BeneficaryContact> telePhone = beneficaryCreation
						.getTelephoneDetails(rel.getBeneficaryMasterSeqId());

				System.out.println("telePhone :" + telePhone.toString());
				if (telePhone != null && telePhone.size() != 0) {
					String telNumber = null;
					if (telePhone.size() == 1) {
						if (telePhone.get(0).getTelephoneNumber() != null) {
							telNumber = telePhone.get(0).getTelephoneNumber();
						} else if (telePhone.get(0).getMobileNumber() != null) {
							telNumber = telePhone.get(0).getMobileNumber()
									.toPlainString();
						} else {
							telNumber = null;
						}
						personalRBDataTable.setTelphoneNum(telNumber);
						personalRBDataTable.setTelphoneCode(telePhone.get(0)
								.getCountryTelCode());
						personalRBDataTable
								.setBeneficiaryContactSeqId(telePhone.get(0)
										.getBeneficaryTelephoneSeqId());
					} else {
						BeneficaryContact beneficaryContact = telePhone.get(0);
						if (beneficaryContact.getTelephoneNumber() != null) {
							telNumber = beneficaryContact.getTelephoneNumber();
						} else if (beneficaryContact.getMobileNumber() != null) {
							telNumber = beneficaryContact.getMobileNumber()
									.toPlainString();
						} else {
							telNumber = null;
						}
						personalRBDataTable.setTelphoneNum(telNumber);
						personalRBDataTable.setTelphoneCode(beneficaryContact
								.getCountryTelCode());
						personalRBDataTable
								.setBeneficiaryContactSeqId(beneficaryContact
										.getBeneficaryTelephoneSeqId());
					}
				}

				personalRBDataTable.setThirdName(rel.getThirdName());
				personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
				personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());

				personalRBDataTable.setStateId(rel.getStateId());
				personalRBDataTable.setDistrictId(rel.getDistrictId());
				personalRBDataTable.setCityId(rel.getCityId());
				personalRBDataTable.setBankAccountTypeId(rel
						.getBankAccountTypeId());

			} else if (isCoustomerExist.size() > 1) {
				// more than one record
			} else {
				// no records
			}

		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}

		log.info("Exit into populateCustomerDetailsFromBeneRelation method ");
		return personalRBDataTable;
	}

	// full edit
	public void fetchAllRecordForNonTrnx(
			PersonalRemmitanceBeneficaryDataTable datatabledetails) {
		try {
			BeneficiaryCreationBean objectBene = (BeneficiaryCreationBean) appContext
					.getBean("beneficiaryCreationBean");
			// objectBene.setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);
			objectBene.setBooRenderWesterUnion(true);
			objectBene.setDataTableBeneObj(datatabledetails);
			objectBene.renderBeneficiaryFullEditNavigation();

			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../beneficary/firsttimebeneficaryedit.xhtml");
		} catch (IOException e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public boolean isEnableSendMoneyTxnStatus() {
		return enableSendMoneyTxnStatus;
	}

	public void setEnableSendMoneyTxnStatus(boolean enableSendMoneyTxnStatus) {
		this.enableSendMoneyTxnStatus = enableSendMoneyTxnStatus;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	
	 * **********************************************************************************************************************************************************************************************
	 * New Code Start
	 * ***********************************************************
	 * ***************
	 * ***********************************************************
	 * *********************************************************
	 


	public void nextSendMoneyConfirmation() {

		try {
			if(getWuMessageCharge().compareTo(BigDecimal.ZERO) == 0 && messageDetailList.size()>0){
				setErrorMessage("Sender message added , Please get Fee Inquiry Details again! ");
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
			
			if(getSendAmount()==null||getRecordingCurrencyId()==null||getReceiveAmount()==null||getSendExchangeRate()==null||getSendCommission()==null||getSendGrossTotalAmount()==null){
				setErrorMessage("Fee Inquiry Details required! ");
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
			clearQuestionAnswerMessage();
			
			if (getWuMessageCharge().compareTo(BigDecimal.ZERO) > 0){
				setErrorMessage("Message Charge:"+getWuMessageCharge()+"(KWD), Do you want proceed");
				RequestContext.getCurrentInstance().execute("msgconfirm.show();");
				return;
			}	
			
			
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hsendmonyconfirm.xhtml");
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::checkWUStatus()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void backSendMoneyConfirmation() {

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hmoneyreciverInfo.xhtml");
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::checkWUStatus()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void backSendMoneyTransfer() {

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hbenelistinfo.xhtml");
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::checkWUStatus()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void backFromSendMoneyDenom() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hsendmonyconfirm.xhtml");
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::wuh2hsendmonyconfirm.xhtml()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	private List<CurrencyMaster> currencyList = new ArrayList<CurrencyMaster>();
	private Map<BigDecimal, String> currencyMap = new HashMap<BigDecimal, String>();

	*//**
	 * 
	 * @return currency List
	 *//*
	public void fetchCurrencyList() {

		if (currencyList != null || !currencyList.isEmpty()) {
			currencyList.clear();
		}
		try {
			List<CurrencyMaster> lstcurrency = iwuh2hService
					.getWUh2HCurrencyList(
							new BigDecimal(sessionStateManage.getCurrencyId()),
							getSendBeneCurrencyId());
			if (lstcurrency != null && lstcurrency.size() != 0) {
				currencyList.addAll(lstcurrency);
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::fetchCurrencyList()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	
	 * Fetch Recording/originating Country/Currency
	 

	public void fetchRecordingCountryCurrency() {

		wuh2hSendClear();
		List<CurrencyMaster> currencyMasterList = generalService
				.getCurrency(getRecordingCurrencyId());

		if (currencyMasterList.size() > 0) {
			setOriginISOCountryId(currencyMasterList.get(0)
					.getFsCountryMaster().getCountryId());

			String alpha2code = iwuh2hService.getNameDescription(
					currencyMasterList.get(0).getFsCountryMaster()
							.getCountryId(), null, "countryalpha");

			setOriginISOCountryCode("KW");
			setOriginISOCurrencyId(currencyMasterList.get(0).getCurrencyId());
			setOriginISOCurrencyCode("KWD");

			setRecordingCountryCode("KW");
			setRecordingCurrencyId(currencyMasterList.get(0).getCurrencyId());
			setRecordingISOCurrencyCode("KWD");
			
			setFixedCurrencyCode(currencyMasterList.get(0)
					.getIsoCurrencyCode());

		}
	}

	private String convertedString = null;
	private String xmlhead = null;
	private String xmlstring = null;	
	private Long longhundred = new Long("100");
	private BigDecimal bigdecimalhundred = new BigDecimal("100");


	
	 * Fee inquiry call to WU gateway
	 
	public void wuH2HFeeEnquiry() {
		FeeInquiryRequest feerequest = null;
		FeeInquiryReply feeresponse = null;
		clearQuestionAnswer();
		String referencno = null;
		try {
			
			if(getWuTransReferenceNo()!=null){
				setWuTransReferenceNo(null);
			}
	
			referencno = iwuh2hService.getNextReferenceNo();
			setWuTransReferenceNo(referencno);

			feeresponse = new FeeInquiryReply();
			Certification.keystoreSetup();
			
			feerequest = fetchWUH2HFeeEnquiryRequest();
			
			convertedString = XMLGenerator.generateXML(feerequest);
			xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			xmlstring = xmlhead+"\n"+convertedString;			
			writeToFile("feeenquiryqreq",xmlstring);			
			System.out.println(xmlstring);

			System.out.println("Recording country/currency************"
					+ feerequest.getPaymentDetails()
							.getRecordingCountryCurrency().getIsoCode()
							.getCountryCode()
					+ "/ "
					+ feerequest.getPaymentDetails()
							.getRecordingCountryCurrency().getIsoCode()
							.getCurrencyCode());
			System.out.println("Originating country/currency************"
					+ feerequest.getPaymentDetails()
							.getOriginatingCountryCurrency().getIsoCode()
							.getCountryCode()
					+ "/ "
					+ feerequest.getPaymentDetails()
							.getOriginatingCountryCurrency().getIsoCode()
							.getCurrencyCode());
			System.out.println("Destination country/currency************"
					+ feerequest.getPaymentDetails()
							.getDestinationCountryCurrency().getIsoCode()
							.getCountryCode()
					+ "/ "
					+ feerequest.getPaymentDetails()
							.getDestinationCountryCurrency().getIsoCode()
							.getCurrencyCode());
			System.out.println("Origination Principal Amount************"
					+ feerequest.getFinancials()
							.getOriginatorsPrincipalAmount());
			System.out.println("Destination Principal Amount************"
					+ feerequest.getFinancials()
							.getDestinationPrincipalAmount());
			System.out.println("Transaction Type ************"
					+ feerequest.getPaymentDetails().getTransactionType());
			
			
			FeeInquiryReplyHelper helper = null;
			helper = FeeEnquiryBO.getFeeEnquiry(feerequest);
			
			if(helper.getErrorReply()==null){
				feeresponse = helper.getFeeInquiryReply();
				
				convertedString = XMLGenerator.generateXML(feeresponse);
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("feeenquiryreply",xmlstring);
				System.out.println(xmlstring);
			
				if(feeresponse.getPromotions()!=null){
					setPromoInfo(feeresponse.getPromotions());
				}
				
				if(feeresponse.getFinancials()!=null){
					//setFinancialsInfo(feeresponse.getFinancials());
					
					BigDecimal localCurrencyId= new BigDecimal(sessionStateManage.getCurrencyId());
					
					setSendGrossTotalAmount(convertLongToBigDecimal(feeresponse.getFinancials().getGrossTotalAmount(),getCurrencyCode(localCurrencyId)));
					setReceiveAmount(convertLongToBigDecimal(feeresponse.getFinancials().getDestinationPrincipalAmount(),getCurrencyCode(getSendBeneCurrencyId())));
					setSendCharges(convertLongToBigDecimal(feeresponse.getFinancials().getPlusChargesAmount(),getCurrencyCode(localCurrencyId)));
					setSendCommission(convertLongToBigDecimal(feeresponse.getFinancials().getCharges(),getCurrencyCode(localCurrencyId)));
					setSendNetAmountSent(convertLongToBigDecimal(feeresponse.getFinancials().getOriginatorsPrincipalAmount(),getCurrencyCode(localCurrencyId)));
					setSendPayableAmount(convertLongToBigDecimal(feeresponse.getFinancials().getGrossTotalAmount(),getCurrencyCode(localCurrencyId)));
					setSendDiscountAmount(convertLongToBigDecimal(feeresponse.getFinancials().getTotalDiscount(),getCurrencyCode(localCurrencyId)));
					setSendPromoDiscountAmount(convertLongToBigDecimal(feeresponse.getPromotions().getPromoDiscountAmount(),getCurrencyCode(localCurrencyId)));
					setSendOriginPrincipleAmount(convertLongToBigDecimal(feeresponse.getFinancials().getOriginatorsPrincipalAmount(),getFixedCurrencyCode()));
					setSendDestPrincipleAmount(convertLongToBigDecimal(feeresponse.getFinancials().getDestinationPrincipalAmount(),getReceiverISOCurrencyCode()));					
					setWuMessageCharge(convertLongToBigDecimal(feeresponse.getFinancials().getMessageCharge(),getCurrencyCode(localCurrencyId)));
				}
				if(feeresponse.getPaymentDetails()!=null){
					setPaymentDetailsInfo(feeresponse.getPaymentDetails());
				
					setSendExchangeRate(BigDecimal.valueOf(feeresponse.getPaymentDetails().getExchangeRate() == null ? new Double(0) : feeresponse.getPaymentDetails().getExchangeRate()));
					
					
				}
				if(feeresponse.getDeliveryServices()!=null){
					
					String testQuestion= feeresponse.getDeliveryServices().getTestQuestionAvailable();
					if(testQuestion!=null && testQuestion.equalsIgnoreCase(Constants.Yes))
					{
						setSecurityQuestionAvailable(true);
					}else
					{
						setSecurityQuestionAvailable(false);
					}
					
					
					setDeliveryService(feeresponse.getDeliveryServices().getCode());
				}
				if(feeresponse.getChannel()!=null){
					setChannelInfo(feeresponse.getChannel());
				}
				if(feeresponse.getForeignRemoteSystem()!=null){
					setForeignRemoteSystemInfo(feeresponse.getForeignRemoteSystem());
				}			
			
			}else{
				setErrorMessage(helper.getErrorReply().getError());
				RequestContext.getCurrentInstance().execute("error.show();");
				
				convertedString = XMLGenerator.generateXML(helper.getErrorReply());
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("feeenquiryreply",xmlstring);
				System.out.println(xmlstring);
				
				feeEnqClear();
				
				//wuh2hSendClear();
				
				return;
			}
			
			
		}catch (NullPointerException ne) {
			log.info("Null Pointer"+ ne.getMessage());
			setErrorMessage("Method Name::wuH2HFeeEnquiry()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}


	}

	
	 * Send Money validation call to WU gateway
	 
	public String wuH2HSendMoneyValidate() {

		SendMoneyValidationRequest request = null;
		SendMoneyValidationReply response = null;
		String message = null;

		try {
			response = new SendMoneyValidationReply();
			request = fetchWUH2HSendMoneyValidation();			
				
			convertedString = XMLGenerator.generateXML(request);
			xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			xmlstring = xmlhead+"\n"+convertedString;			
			writeToFile("sendmoneyvalidationrequest",xmlstring);
			System.out.println(xmlstring);
			
			Certification.keystoreSetup();
			
			SendMoneyValidationReplyHelper helper = null;
			helper = SendMoneyValidationBO.sendMoneyValidation(request);
			
			if(helper.getErrorReply()==null){
				response = helper.getSendMoneyValidationReply();
				message = null;
				
				convertedString = XMLGenerator.generateXML(response);
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("sendmoneyvalidationreply",xmlstring);
				System.out.println(xmlstring);
				
				
				setSendMtcno(response.getMtcn());
				setSendNewMtcno(response.getNewMtcn());
				
				setExpectedStateCode(response.getPaymentDetails().getExpectedPayoutLocation().getStateCode());
				setExpectedCityName(response.getPaymentDetails().getExpectedPayoutLocation().getCity());
				
				if(response.getPromotions()!=null){
					setPromoInfo(response.getPromotions());
				}
				if(response.getSender()!=null){
					setSenderInfo(response.getSender());
				}
				if(response.getReceiver()!=null){
					setReceiverInfo(response.getReceiver());
				}
				if(response.getDeliveryServices()!=null){
					setDeliveryServicesInfo(response.getDeliveryServices());
				}
				if(response.getForeignRemoteSystem()!=null){
					setForeignRemoteSystemInfo(response.getForeignRemoteSystem());
				}
				if(response.getChannel()!=null){
					setChannelInfo(response.getChannel());
				}
				if(response.getFinancials()!=null){
					setFinancialsInfo(response.getFinancials());
				}
				if(response.getPaymentDetails()!=null){
					setPaymentDetailsInfo(response.getPaymentDetails());
				}
				
				
				 * 
				 * New code 
				 * 
				 
				//setSendMtcno(response.getMtcn());
				//setSendNewMtcno(response.getNewMtcn());
		
				setSenderFirstName(response.getSender().getName().getFirstName());
				setSenderLastName(response.getSender().getName().getLastName());
				setSenderContactPhone(response.getSender().getContactPhone());
				
				if(response.getSender().getIdDetails()!=null){
					setSenderIdType(response.getSender().getIdDetails().getIdType());
					setSenderIdNumber(response.getSender().getIdDetails().getIdNumber());
					setSenderIdIssueCountry(response.getSender().getIdDetails()
							.getIdCountryOfIssue());
				}
				
				setReceiverFirstName(response.getReceiver().getName()
						.getFirstName());
				setReceiverLastName(response.getReceiver().getName().getLastName());
				setReceiverContactPhone(response.getReceiver().getContactPhone());
		
				setSendCommission(convertLongToBigDecimal(response.getFinancials().getCharges(),getFixedCurrencyCode())) ;
				setSendOriginPrincipleAmount(convertLongToBigDecimal(response.getFinancials().getOriginatorsPrincipalAmount(),getFixedCurrencyCode()));
				setSendDestPrincipleAmount(convertLongToBigDecimal(response.getFinancials().getDestinationPrincipalAmount(),getReceiverISOCurrencyCode()));
				setSendGrossTotalAmount(convertLongToBigDecimal(response.getFinancials().getGrossTotalAmount(),getFixedCurrencyCode()));
				setSendPlusChargeAmount(convertLongToBigDecimal(response.getFinancials().getPlusChargesAmount(),getFixedCurrencyCode()));
				
				//setSendExchangeRate(BigDecimal.valueOf(response.getPaymentDetails().getExchangeRate()== null ? new Double(0) : response.getPaymentDetails().getExchangeRate().doubleValue()).setScale(decimalPlacesInfo(), BigDecimal.ROUND_UP));
		
				setOriginISOCurrencyCode(response.getPaymentDetails()
						.getOriginatingCountryCurrency().getIsoCode()
						.getCurrencyCode());
				setReceiverISOCurrencyCode(response.getPaymentDetails()
						.getDestinationCountryCurrency().getIsoCode()
						.getCurrencyCode());
		
				setPurposeOfTransactions(response.getSender()
						.getComplianceDetails().getTransactionReason());
				setSourceOfIncome(response.getSender().getComplianceDetails()
						.getSourceOfFunds());
				
			}else{
				setErrorMessage(helper.getErrorReply().getError());
				RequestContext.getCurrentInstance().execute("error.show();");				
				message = helper.getErrorReply().getError();
				
				convertedString = XMLGenerator.generateXML(helper.getErrorReply());
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("sendmoneyvalidationreply",xmlstring);
				System.out.println(xmlstring);
				
				setSendMtcno(response.getMtcn());
				setSendNewMtcno(response.getNewMtcn());
			}
			
		} catch (NullPointerException ne) {
			log.info("Null Pointer"+ ne.getMessage());
			setErrorMessage("Method Name::wuH2HSendMoneyValidate()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			message = ne.getMessage();
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			message = exception.getMessage();
		}
		
		return message;

	}


	
	 * Send Money store call to WU gateway
	 
	public String wuH2HSendMoneyStore() {

		SendMoneyStoreRequest request = null;
		SendMoneyStoreReply response = null;
		String message = null;
		try {
			response = new SendMoneyStoreReply();
			Certification.keystoreSetup();
			request = fetchWUH2HSendMoneyStore();
			
			convertedString = XMLGenerator.generateXML(request);
			xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			xmlstring = xmlhead+"\n"+convertedString;			
			writeToFile("sendmoneystorerequest",xmlstring);
			System.out.println(xmlstring);
			
			SendMoneyStoreReplyHelper helper = null;
			helper = SendMoneyStoreBO.sendMoneyStore(request);
			
			if(helper.getErrorReply()==null){
				response = helper.getSendMoneyStoreReply();
				message = null;
				
				convertedString = XMLGenerator.generateXML(response);
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("sendmoneystorereply",xmlstring);
				System.out.println(xmlstring);
				
				System.out
				.println("********************* Send Store info *********************");
				System.out.println("Origin Principle********************:"
						+ response.getFinancials().getOriginatorsPrincipalAmount());
				System.out.println("Charge********************:"
						+ response.getFinancials().getCharges());
				System.out.println("Plus Charge********************:"
						+ response.getFinancials().getPlusChargesAmount());
				System.out.println("Toatl Gross Amount********************:"
						+ response.getFinancials().getGrossTotalAmount());
				System.out.println("Exchange Rate********************:"
						+ response.getPaymentDetails().getExchangeRate());
				System.out.println("Destination Amount********************:"
						+ response.getFinancials().getDestinationPrincipalAmount());
				System.out.println("Send Country/Currency********************:"
						+ response.getPaymentDetails()
								.getOriginatingCountryCurrency().getCountryName()
						+ "/ "
						+ response.getPaymentDetails()
								.getOriginatingCountryCurrency().getIsoCode()
								.getCurrencyCode());
				System.out
						.println("Destination Country/Currency********************:"
								+ response.getPaymentDetails()
										.getDestinationCountryCurrency()
										.getCountryName()
								+ "/ "
								+ response.getPaymentDetails()
										.getDestinationCountryCurrency()
										.getIsoCode().getCurrencyCode());
				System.out
						.println("MTCN********************:" + response.getMtcn());
				System.out.println("points********************:"
						+ response.getPointsEarned());
				System.out.println("POS message********************:"
						+ response.getPosMessage());
				System.out
						.println("********************* Send Store info *********************");
		
				setSendMtcno(response.getMtcn());
				setSendNewMtcno(response.getNewMtcn());
		
				setSenderFirstName(response.getSender().getName().getFirstName());
				setSenderLastName(response.getSender().getName().getLastName());
				setSenderContactPhone(response.getSender().getContactPhone());
				
				if(response.getSender().getIdDetails()!=null){
					setSenderIdType(response.getSender().getIdDetails().getIdType());
					setSenderIdNumber(response.getSender().getIdDetails().getIdNumber());
					setSenderIdIssueCountry(response.getSender().getIdDetails()
							.getIdCountryOfIssue());
				}
				
				setReceiverFirstName(response.getReceiver().getName()
						.getFirstName());
				setReceiverLastName(response.getReceiver().getName().getLastName());
				setReceiverContactPhone(response.getReceiver().getContactPhone());
		
				setSendCommission(convertLongToBigDecimal(response.getFinancials().getCharges(),getFixedCurrencyCode())) ;
				setSendOriginPrincipleAmount(convertLongToBigDecimal(response.getFinancials().getOriginatorsPrincipalAmount(),getFixedCurrencyCode()));
				setSendDestPrincipleAmount(convertLongToBigDecimal(response.getFinancials().getDestinationPrincipalAmount(),getReceiverISOCurrencyCode()));
				setSendGrossTotalAmount(convertLongToBigDecimal(response.getFinancials().getGrossTotalAmount(),getFixedCurrencyCode()));
				setSendPlusChargeAmount(convertLongToBigDecimal(response.getFinancials().getPlusChargesAmount(),getFixedCurrencyCode()));
				
				//setSendExchangeRate(BigDecimal.valueOf(response.getPaymentDetails().getExchangeRate()== null ? new Double(0) : response.getPaymentDetails().getExchangeRate().doubleValue()).setScale(decimalPlacesInfo(), BigDecimal.ROUND_UP));
		
				setOriginISOCurrencyCode(response.getPaymentDetails()
						.getOriginatingCountryCurrency().getIsoCode()
						.getCurrencyCode());
				setReceiverISOCurrencyCode(response.getPaymentDetails()
						.getDestinationCountryCurrency().getIsoCode()
						.getCurrencyCode());
		
				setPurposeOfTransactions(response.getSender()
						.getComplianceDetails().getTransactionReason());
				setSourceOfIncome(response.getSender().getComplianceDetails()
						.getSourceOfFunds());
				if(response.getPointsEarned()!=null){
					setSenderPointsEarned(response.getPointsEarned());
				}
				if(response.getPosMessage()!=null){
					setSendPOSMessage(response.getPosMessage());
				}
				
				if(response.getNewPointsEarned()!=null){
					setSenderNewPointsEarned(String.valueOf(response.getNewPointsEarned()==null ? new Long(0):response.getNewPointsEarned()));
				}

			}else{
				setErrorMessage(helper.getErrorReply().getError());
				RequestContext.getCurrentInstance().execute("error.show();");
				message = helper.getErrorReply().getError();
				
				convertedString = XMLGenerator.generateXML(helper.getErrorReply());
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("sendmoneystorereply",xmlstring);
				System.out.println(xmlstring);
			}
			

		} catch (NullPointerException ne) {
			log.info("Null Pointer"+ ne.getMessage());
			setErrorMessage("Method Name::wuH2HSendMoneyStore()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			message = ne.getMessage();
			
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			message = exception.getMessage();
		}
		
		return message;
	}
	
	public void wuh2hSendMoneyModifySearch(){
		
		String wuerrormessage=null;		
		wuerrormessage = wuh2hWUModifySendMoneySearch(getModifyMtcn());				
		if(wuerrormessage!=null){
			setTxnType(0);
			setErrorMessage(wuerrormessage);
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}else{
			
			fetchCurrencyList();
			fetchWUStateList();
			
			setDisplayWUState(false);
			setDisplayWUCity(false);
			
			if(getReceiverISOCountryCode().equals("MX")){
				setDisplayWUState(true);
				setDisplayWUCity(true);
			}
			if(getReceiverISOCountryCode().equals("US")){
				setDisplayWUState(true);
				setDisplayWUCity(false);
			}
			
			try {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../wuh2h/wuh2hmoneyreciverInfo.xhtml");
			} catch (IOException e) {
				setTxnType(0);
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}
	}
	
	public String wuh2hWUModifySendMoneySearch(String mtcn){
		
		ModifySendMoneySearchRequest request = null;
		ModifySendMoneySearchReply response = null;
		
		String errormessage = null;
		try{
			
			response = new ModifySendMoneySearchReply();
			request = new ModifySendMoneySearchRequest();
			Certification.keystoreSetup();
			
			request = fetchModifySendMoneySearch(mtcn);	
			
			convertedString = XMLGenerator.generateXML(request);
			xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			xmlstring = xmlhead+"\n"+convertedString;			
			writeToFile("sendmoneymodifysearchrequest",xmlstring);
			System.out.println(xmlstring);
			
			
			ModifySendMoneySearchReplyHelper helper = null;
			helper = ModifySendMoneySearchBO.modifySendMoneySearch(request);
			
			if(helper.getErrorReply()==null){
				response = helper.getModifySendMoneySearchReply();
				
				convertedString = XMLGenerator.generateXML(response);
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("sendmoneymodifysearchresponse",xmlstring);
				System.out.println(xmlstring);
				
				List<PaymentTransaction> modifyTransactionList =response.getPaymentTransactions().getPaymentTransaction();
				
				if(modifyTransactionList.size()>0){					
					
					//Financial
					setSendOriginPrincipleAmount(convertLongToBigDecimal(modifyTransactionList.get(0).getFinancials().getOriginatorsPrincipalAmount(),getFixedCurrencyCode()));
					setSendDestPrincipleAmount(convertLongToBigDecimal(modifyTransactionList.get(0).getFinancials().getDestinationPrincipalAmount() ,getReceiverISOCurrencyCode()));
					setSendGrossTotalAmount(convertLongToBigDecimal(modifyTransactionList.get(0).getFinancials().getGrossTotalAmount(),getFixedCurrencyCode()));
					setSendPlusChargeAmount(convertLongToBigDecimal(modifyTransactionList.get(0).getFinancials().getPlusChargesAmount(),getFixedCurrencyCode()));
					setSendCommission(convertLongToBigDecimal(modifyTransactionList.get(0).getFinancials().getCharges(),getFixedCurrencyCode()));
					setSendMessageAmount(convertLongToBigDecimal(modifyTransactionList.get(0).getFinancials().getMessageCharge(),getFixedCurrencyCode()));
					
					//Sender Info
					setSenderFirstName(modifyTransactionList.get(0).getSender().getName().getFirstName());
					setSenderLastName(modifyTransactionList.get(0).getSender().getName().getLastName());
					
					setSendAddressLine1(modifyTransactionList.get(0).getSender().getAddress().getAddrLine1());
					setSendAddressLine2(modifyTransactionList.get(0).getSender().getAddress().getAddrLine2());
					setSenderPostalCode(modifyTransactionList.get(0).getSender().getAddress().getPostalCode());
					setSenderContactPhone(modifyTransactionList.get(0).getSender().getContactPhone());
					setSenderCity(modifyTransactionList.get(0).getSender().getAddress().getCity());
					setSenderCountryName(modifyTransactionList.get(0).getSender().getAddress().getCountryCode().getCountryName());
					
					//setOriginISOCountryCode(modifyTransactionList.get(0).getSender().getAddress().getCountryCode().getIsoCode().getCountryCode());
					//setOriginISOCurrencyCode(modifyTransactionList.get(0).getSender().getAddress().getCountryCode().getIsoCode().getCurrencyCode());
					
					//Receiver Info
					setReceiverFirstName(modifyTransactionList.get(0).getReceiver().getName().getFirstName());					
					setReceiverLastName(modifyTransactionList.get(0).getReceiver().getName().getLastName());					
					setReceiverAddress1(modifyTransactionList.get(0).getReceiver().getAddress().getAddrLine1());					
					setReceiverCityName(modifyTransactionList.get(0).getReceiver().getAddress().getCity());
					setReceiverStateName(modifyTransactionList.get(0).getReceiver().getAddress().getState());					
					setReceiverPostalcode(modifyTransactionList.get(0).getReceiver().getAddress().getPostalCode());					
					//setReceiverISOCountryCode(modifyTransactionList.get(0).getReceiver().getAddress().getCountryCode().getIsoCode().getCountryCode());					
					//setReceiverISOCurrencyCode(modifyTransactionList.get(0).getReceiver().getAddress().getCountryCode().getIsoCode().getCurrencyCode());					
					setReceiverCountryName(modifyTransactionList.get(0).getReceiver().getAddress().getCountryCode().getCountryName());
					
					setSendReason(modifyTransactionList.get(0).getReceiver().getReasonForSending());					
					setReceiverContactPhone(modifyTransactionList.get(0).getReceiver().getContactPhone());
					
					//Payment Info					
					setRecordingCountryCode(modifyTransactionList.get(0).getPaymentDetails().getOriginatingCountryCurrency().getIsoCode().getCountryCode());
					setRecordingISOCurrencyCode(modifyTransactionList.get(0).getPaymentDetails().getOriginatingCountryCurrency().getIsoCode().getCurrencyCode());
					
					setOriginISOCountryCode(modifyTransactionList.get(0).getPaymentDetails().getOriginatingCountryCurrency().getIsoCode().getCountryCode());
					setOriginISOCurrencyCode(modifyTransactionList.get(0).getPaymentDetails().getOriginatingCountryCurrency().getIsoCode().getCurrencyCode());
					
					setReceiverISOCountryCode(modifyTransactionList.get(0).getPaymentDetails().getDestinationCountryCurrency().getIsoCode().getCountryCode());
					setReceiverISOCurrencyCode(modifyTransactionList.get(0).getPaymentDetails().getDestinationCountryCurrency().getIsoCode().getCurrencyCode());					
					setSendExchangeRate(BigDecimal.valueOf(modifyTransactionList.get(0).getPaymentDetails().getExchangeRate() == null ?  new Double(0) : modifyTransactionList.get(0).getPaymentDetails().getExchangeRate()));
					
					setSendMtcno(modifyTransactionList.get(0).getMtcn());
					setSendNewMtcno(modifyTransactionList.get(0).getNewMtcn());					
					setSendMoneyTransferKey(modifyTransactionList.get(0).getMoneyTransferKey());					
					
				}				
				errorMessage =  null;
				
			}else{
				
				errormessage = helper.getErrorReply().getError();	
				
				convertedString = XMLGenerator.generateXML(helper.getErrorReply());
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("sendmoneymodifysearchresponse",xmlstring);
				System.out.println(xmlstring);
			}
			
		}catch (NullPointerException ne) {
			log.info("Null Pointer"+ ne.getMessage());
			errormessage = ne.getMessage();	
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			errormessage = exception.getMessage();	
		}
		return errorMessage;
	}
	
	public ModifySendMoneySearchRequest fetchModifySendMoneySearch(String mtcn){
		
		ModifySendMoneySearchRequest request = null;
		
		ModifySendMoneySearchReply response = new ModifySendMoneySearchReply();
		ForeignRemoteSystem foreignRemoteSystem = null;
		Channel channel = null;
		PaymentDetails paymentDetails = null;
		
		PaymentTransaction paymentTransaction = null;
		CountryCurrencyInfo originCountryCurrencyInfo = null;
		IsoCode originIsoCode = null;
		try{
			
			request = new ModifySendMoneySearchRequest();
			
			channel = new Channel();
			channel.setType(ChannelType.H_2_H);
			channel.setName(channelName);
			channel.setVersion(channelVersion);	
			request.setChannel(channel);			
			
			// Foreign remote system info
			foreignRemoteSystem = new ForeignRemoteSystem();
			foreignRemoteSystem.setReferenceNo(remoteRefno);
			foreignRemoteSystem.setCounterId(remoteCounterId);
			foreignRemoteSystem.setIdentifier(remoteIdentifier);	
			request.setForeignRemoteSystem(foreignRemoteSystem);			
			
			paymentTransaction = new PaymentTransaction();
			
			// Payment Details- Origin Country Currency Info
			originCountryCurrencyInfo = new CountryCurrencyInfo();
			originIsoCode = new IsoCode();
			originIsoCode.setCountryCode("KW");
			originIsoCode.setCurrencyCode("KWD");
			originCountryCurrencyInfo.setIsoCode(originIsoCode);
			
			paymentDetails = new PaymentDetails();
			paymentDetails.setOriginatingCountryCurrency(originCountryCurrencyInfo);
			paymentTransaction.setPaymentDetails(paymentDetails);
			paymentTransaction.setMtcn(mtcn);
			request.setPaymentTransaction(paymentTransaction);
			
		}catch (Exception exception) {
			request = null;
		}
		
		return request;
	}
	
	
	public String wuH2HSendMoneyModify() {

		ModifySendMoneyRequest request = null;
		ModifySendMoneyReply response = null;
		String message = null;
		try {
			response = new ModifySendMoneyReply();
			Certification.keystoreSetup();
			request = fetchWUH2HModifySendMoney();
			
			convertedString = XMLGenerator.generateXML(request);
			xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			xmlstring = xmlhead+"\n"+convertedString;			
			writeToFile("sendmoneymodifyrequest",xmlstring);
			System.out.println(xmlstring);
			
			
			ModifySendMoneyReplyHelper helper = null;
			helper = ModifySendMoneyBO.modifySendMoney(request);
			
			if(helper.getErrorReply()==null){
				response = helper.getModifySendMoneyReply();
				message = null;
				
				convertedString = XMLGenerator.generateXML(response);
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("sendmoneymodifyrequest",xmlstring);
				System.out.println(xmlstring);
				
				System.out
				.println("********************* Send modify Store info *********************");
				System.out.println("Origin Principle********************:"
						+ response.getFinancials().getOriginatorsPrincipalAmount());
				System.out.println("Charge********************:"
						+ response.getFinancials().getCharges());
				System.out.println("Plus Charge********************:"
						+ response.getFinancials().getPlusChargesAmount());
				System.out.println("Toatl Gross Amount********************:"
						+ response.getFinancials().getGrossTotalAmount());
				System.out.println("Exchange Rate********************:"
						+ response.getPaymentDetails().getExchangeRate());
				System.out.println("Destination Amount********************:"
						+ response.getFinancials().getDestinationPrincipalAmount());
				System.out.println("Send Country/Currency********************:"
						+ response.getPaymentDetails()
								.getOriginatingCountryCurrency().getCountryName()
						+ "/ "
						+ response.getPaymentDetails()
								.getOriginatingCountryCurrency().getIsoCode()
								.getCurrencyCode());
				System.out
						.println("Destination Country/Currency********************:"
								+ response.getPaymentDetails()
										.getDestinationCountryCurrency()
										.getCountryName()
								+ "/ "
								+ response.getPaymentDetails()
										.getDestinationCountryCurrency()
										.getIsoCode().getCurrencyCode());
				System.out
						.println("MTCN********************:" + response.getMtcn());
				System.out.println("points********************:"
						+ response.getPointsEarned());
				System.out.println("POS message********************:"
						+ response.getPosMessage());
				System.out
						.println("********************* Send modify Store info *********************");
		
				setSendMtcno(response.getMtcn());
				setSendNewMtcno(response.getNewMtcn());
		
				setSenderFirstName(response.getSender().getName().getFirstName());
				setSenderLastName(response.getSender().getName().getLastName());
				setSenderContactPhone(response.getSender().getContactPhone());
				
				if(response.getSender().getIdDetails()!=null){
					setSenderIdType(response.getSender().getIdDetails().getIdType());
					setSenderIdNumber(response.getSender().getIdDetails().getIdNumber());
					setSenderIdIssueCountry(response.getSender().getIdDetails()
							.getIdCountryOfIssue());
				}
				
				setReceiverFirstName(response.getReceiver().getName()
						.getFirstName());
				setReceiverLastName(response.getReceiver().getName().getLastName());
				setReceiverContactPhone(response.getReceiver().getContactPhone());
		
				setSendCommission(convertLongToBigDecimal(response.getFinancials().getCharges() ,getFixedCurrencyCode()));
				setSendOriginPrincipleAmount(convertLongToBigDecimal(response.getFinancials().getOriginatorsPrincipalAmount() ,getFixedCurrencyCode()));
				setSendDestPrincipleAmount(convertLongToBigDecimal(response.getFinancials().getDestinationPrincipalAmount() ,getFixedCurrencyCode()));
				setSendGrossTotalAmount(convertLongToBigDecimal(response.getFinancials().getGrossTotalAmount() ,getFixedCurrencyCode()));
				setSendPlusChargeAmount(convertLongToBigDecimal(response.getFinancials().getPlusChargesAmount() ,getFixedCurrencyCode()));
				setSendExchangeRate(BigDecimal.valueOf(response.getPaymentDetails().getExchangeRate() == null ?  new Long(0) : response.getPaymentDetails().getExchangeRate()));			
		
				setOriginISOCurrencyCode(response.getPaymentDetails()
						.getOriginatingCountryCurrency().getIsoCode()
						.getCurrencyCode());
				setReceiverISOCurrencyCode(response.getPaymentDetails()
						.getDestinationCountryCurrency().getIsoCode()
						.getCurrencyCode());
		
				setPurposeOfTransactions(response.getSender()
						.getComplianceDetails().getTransactionReason());
				setSourceOfIncome(response.getSender().getComplianceDetails()
						.getSourceOfFunds());
				if(response.getPointsEarned()!=null){
					setSenderPointsEarned(response.getPointsEarned());
				}
				if(response.getPosMessage()!=null){
					setSendPOSMessage(response.getPosMessage());
				}
				
				if(response.getNewPointsEarned()!=null){
					setSenderNewPointsEarned(String.valueOf(response.getNewPointsEarned()==null ? new Long(0):response.getNewPointsEarned()));
				}

				
			}else{
					setErrorMessage(helper.getErrorReply().getError());
					RequestContext.getCurrentInstance().execute("error.show();");
					message = helper.getErrorReply().getError();
					
					convertedString = XMLGenerator.generateXML(helper.getErrorReply());
					xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
					xmlstring = xmlhead+"\n"+convertedString;			
					writeToFile("sendmoneymodifyrequest",xmlstring);
					System.out.println(xmlstring);
			}			
			
		} catch (NullPointerException ne) {
			log.info("Null Pointer"+ ne.getMessage());
			setErrorMessage("Method Name::wuH2HSendMoneyStore()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			message = ne.getMessage();
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			message = exception.getMessage();
		}
		return message;
	}
	
	public ModifySendMoneyRequest fetchWUH2HModifySendMoney() {

		ModifySendMoneyRequest request = null;
		ForeignRemoteSystem foreignRemoteSystem = null;
		Channel channel = null;
		Promotions promotions = null;
		Financials financials = null;
		DeliveryServices deliveryServices = null;
		PaymentDetails paymentDetails = null;
		CountryCurrencyInfo originCountryCurrencyInfo = null;
		CountryCurrencyInfo destCountryCurrencyInfo = null;
		CountryCurrencyInfo recCountryCurrencyInfo = null;
		CountryCurrencyInfo addCountryCurrencyInfo = null;
		CountryCurrencyInfo thirdpartyCountryCurrencyInfo = null;
		IsoCode originIsoCode = null;
		IsoCode destIsoCode = null;
		IsoCode recIsoCode = null;
		IsoCode addIsoCode = null;
		IsoCode thirdpartyIsoCode = null;

		Sender sender = null;
		GeneralName generalName = null;
		GeneralName thirdpartyName = null;
		Address address = null;
		ComplianceAddress thirtpartyAddress = null;
		PreferredCustomer preferredCustomer = null;

		ComplianceDetails complianceDetails = null;
		IdDetails idDetails = null;
		IdDetails thirdparttId = null;
		ThirdPartyDetails thirdPartyDetails = null;
		Receiver receiver = null;

		try {

			request = new ModifySendMoneyRequest();

			// Channel Info
			channel = new Channel();
			channel.setType(ChannelType.H_2_H);
			channel.setName(channelName);
			channel.setVersion(channelVersion);
			request.setChannel(channel);

			
			// Financial value
			financials = new Financials();
			financials
					.setOriginatorsPrincipalAmount(getSendOriginPrincipleAmount() == null ? new Long(0):getSendOriginPrincipleAmount().longValue()*longhundred);
			financials
					.setDestinationPrincipalAmount(getSendDestPrincipleAmount()==null ? new Long(0) :getSendDestPrincipleAmount().longValue()*longhundred);
			financials.setGrossTotalAmount(getSendGrossTotalAmount()== null ? new Long(0)  :getSendGrossTotalAmount().longValue()*longhundred);
			financials.setPlusChargesAmount(getSendPlusChargeAmount()== null ? new Long(0) : getSendPlusChargeAmount().longValue()*longhundred);
			financials.setCharges(getSendCommission()== null ? new Long(0)  :getSendCommission().longValue()*longhundred);
			financials.setMessageCharge(getSendMessageAmount()==null ? new Long(0): getSendMessageAmount().longValue()*longhundred);
			request.setFinancials(financials);
			
			// Sender Details
			sender = new Sender();
			generalName = new GeneralName();
			generalName.setNameType(NameType.D);
			generalName.setFirstName(getSenderFirstName());
			generalName.setLastName(getSenderLastName());
			sender.setName(generalName);

			List<ContactDetail> customerContactdetails = icustomerRegistrationService
					.getCustomerContactDetails(getCustomerNo());

			// Sender Address
			address = new Address();
			StringBuffer addrline1 = null;
			StringBuffer addrline2 = null;
			String senderCountryName = null;
			String senderCityname = null;
			
			address.setAddrLine1(getSendAddressLine1());
			address.setAddrLine2(getSendAddressLine2());
			address.setCity(getSenderCity());
			address.setPostalCode(getSenderPostalCode());
			sender.setContactPhone(getSenderContactPhone());			
			
			addCountryCurrencyInfo = new CountryCurrencyInfo();
			addIsoCode = new IsoCode();
			addIsoCode.setCountryCode(getOriginISOCountryCode());
			addIsoCode.setCurrencyCode(getOriginISOCurrencyCode());
			addCountryCurrencyInfo.setIsoCode(addIsoCode);
			
			addCountryCurrencyInfo.setCountryName(getSenderCountryName());
			address.setCountryCode(addCountryCurrencyInfo);
			sender.setAddress(address);

			// Prefered Customer

			
			 * preferredCustomer = new PreferredCustomer();
			 * preferredCustomer.setPermanentChange
			 * (CardUpdateIndicator.LOOK_UP);
			 * preferredCustomer.setMywuNumber("100875370");
			 * sender.setPreferredCustomer(preferredCustomer);
			 
			
			
			complianceDetails = new ComplianceDetails();
			complianceDetails.setTemplateId("KWT_01_S");

			idDetails = new IdDetails();
			idDetails.setIdType("A");
			idDetails.setIdNumber(getSenderIdNumber());
			idDetails.setIdCountryOfIssue("India");
			complianceDetails.setIdDetails(idDetails);
			complianceDetails.setIdExpirationDate("18122030");
			complianceDetails.setIdIssueDate("18122007");
			complianceDetails.setDateOfBirth("18121976");
			complianceDetails.setCountryOfBirth("India");
			complianceDetails.setDoesTheIDHaveAnExpirationDate(YesNoFlag.Y);
			complianceDetails.setAckFlag("X");
			complianceDetails.setOccupation(getSenderOccupation());
			complianceDetails.setTransactionReason("Family expenses");

			sender.setComplianceDetails(complianceDetails);
			request.setSender(sender);

			// Receiver Info
			receiver = new Receiver();
			GeneralName recvName = new GeneralName();
			recvName.setNameType(NameType.D);
			recvName.setFirstName(getReceiverFirstName());
			recvName.setLastName(getReceiverLastName());
			receiver.setName(recvName);
		
			Address recvaddress = new Address();
			recvaddress.setAddrLine1(getReceiverAddress1());
			recvaddress.setCity(getReceiverCityName());
			recvaddress.setState(getReceiverStateName());

			// recvaddress.setCity(getReceiverCityName());
			// recvaddress.setState(getReceiverStateName());

			recvaddress.setPostalCode(getReceiverPostalcode());

			CountryCurrencyInfo recvCountryCurrencyInfo = new CountryCurrencyInfo();
			IsoCode recvIsoCode = new IsoCode();
			recvIsoCode.setCountryCode(getReceiverISOCountryCode());
			recvIsoCode.setCurrencyCode(getReceiverISOCurrencyCode());
			recvCountryCurrencyInfo.setIsoCode(recvIsoCode);
			recvaddress.setCountryCode(recvCountryCurrencyInfo);
			recvCountryCurrencyInfo.setCountryName(getReceiverCountryName());

			// recvCountryCurrencyInfo.setCountryName(getReceiverCountryName());

			receiver.setAddress(recvaddress);
			receiver.setReasonForSending(getSendReason());
			receiver.setContactPhone(getReceiverContactPhone());

			request.setReceiver(receiver);

			// Payment details
			paymentDetails = new PaymentDetails();

			// receiver country/currency
			recCountryCurrencyInfo = new CountryCurrencyInfo();
			recIsoCode = new IsoCode();
			recIsoCode.setCountryCode(getRecordingCountryCode());
			recIsoCode.setCurrencyCode(getRecordingISOCurrencyCode());
			recCountryCurrencyInfo.setIsoCode(recIsoCode);

			originCountryCurrencyInfo = new CountryCurrencyInfo();
			originIsoCode = new IsoCode();
			originIsoCode.setCountryCode(getOriginISOCountryCode());
			originIsoCode.setCurrencyCode(getOriginISOCurrencyCode());
			originCountryCurrencyInfo.setIsoCode(originIsoCode);

			destCountryCurrencyInfo = new CountryCurrencyInfo();
			destIsoCode = new IsoCode();
			destIsoCode.setCountryCode(getReceiverISOCountryCode());
			destIsoCode.setCurrencyCode(getReceiverISOCurrencyCode());
			destCountryCurrencyInfo.setIsoCode(destIsoCode);

			paymentDetails.setRecordingCountryCurrency(recCountryCurrencyInfo);
			paymentDetails
					.setOriginatingCountryCurrency(originCountryCurrencyInfo);
			paymentDetails
					.setDestinationCountryCurrency(destCountryCurrencyInfo);
			if (getRecordingISOCurrencyCode().equals("KWD")) {
				paymentDetails.setTransactionType(TransactionType.WMN);
			} else {
				paymentDetails.setTransactionType(TransactionType.WMF);
			}
			paymentDetails.setPaymentType(PaymentType.CASH);
			paymentDetails.setFixOnSend(YesNo.FALSE);
			paymentDetails.setPayWoIdIndicator(YesNo.N);
			paymentDetails.setExchangeRate(getSendExchangeRate()== null ? new Double(0) : getSendExchangeRate().doubleValue());
			paymentDetails.setDuplicateDetectionFlag("D");

			request.setPaymentDetails(paymentDetails);
			request.setMtcn(getSendMtcno());
			request.setNewMtcn(getSendNewMtcno());

			// Delivery services value
			deliveryServices = new DeliveryServices();
			deliveryServices.setCode("000");
			request.setDeliveryServices(deliveryServices);

			// Foreign remote system info
			foreignRemoteSystem = new ForeignRemoteSystem();
			foreignRemoteSystem.setReferenceNo(remoteRefno);
			foreignRemoteSystem.setCounterId(remoteCounterId);
			foreignRemoteSystem.setIdentifier(remoteIdentifier);	
			request.setForeignRemoteSystem(foreignRemoteSystem);

		} catch (Exception exception) {
			request = null;
		}
		return request;
	}
	
	public void wuh2hWUCardLookup(){
		WuCardLookupRequest request = null;
		WuCardLookupReply response = null;
		String referenceno = null;
		try{
			
			if(getWuTransReferenceNo()!=null){
				setWuTransReferenceNo(null);
			}
			
			referenceno = iwuh2hService.getNextReferenceNo();
			setWuTransReferenceNo(referenceno);
			
			response = new WuCardLookupReply();
			request = new WuCardLookupRequest();
			Certification.keystoreSetup();
			
			request = fetchWUCardLookup(getWuCardno());	
			
			convertedString = XMLGenerator.generateXML(request);
			xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			xmlstring = xmlhead+"\n"+convertedString;			
			writeToFile("wucardlookuprequest",xmlstring);
			System.out.println(xmlstring);
			
			WuCardLookupReplyHelper helper = null;
			helper = WUCardLookupBO.getWUCardLookup(request);
			
			if(helper.getErrorReply()==null){
				response = helper.getWuCardLookupReply();
				
				convertedString = XMLGenerator.generateXML(response);
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("wucardlookupreply",xmlstring);
				System.out.println(xmlstring);
				
				
				String customername = null;
				String lookupname = null;
				customername = getFirstName().concat(getSecondName());						
				customername = customername.trim();
				customername = customername.toUpperCase();								
				
				if(response.getSender()!=null){
					lookupname = response.getSender().get(0).getName().getFirstName().concat(response.getSender().get(0).getName().getLastName());
					lookupname = lookupname.trim();
					lookupname = lookupname.toUpperCase();
					
					if(!customername.equals(lookupname)){
						setErrorMessage("Customer name and WU Card holder name is mismatched!");
						RequestContext.getCurrentInstance().execute("error.show();");
						setWucardLookup(false);
						setEnablePromotion("false");
						return;
					}else{						
						setWuCardno(response.getSender().get(0).getPreferredCustomer().getMywuNumber());
						setSendWUcardNo(response.getSender().get(0).getPreferredCustomer().getMywuNumber());
						setWuCardLevelCode(response.getSender().get(0).getPreferredCustomer().getLevelCode());
						setPreferredCustomerInfo(response.getSender().get(0).getPreferredCustomer());
					}
				}
				
				if(response.getWuCard().getPromoCode().length()>0){
					setPromotionCode(response.getWuCard().getPromoCode());
					setWuCardInfo(response.getWuCard());
					
					setEnablePromotion("true");										
				}else{
					setEnablePromotion("false");
				}
				if(response.getWuCard().getTotalPointsEarned()!=null){
					setSenderPointsEarned(String.valueOf(response.getWuCard().getTotalPointsEarned()==null ? new Long(0):response.getWuCard().getTotalPointsEarned()));
					
				}
				
			}else{
				setErrorMessage(helper.getErrorReply().getError());
				RequestContext.getCurrentInstance().execute("error.show();");
				
				convertedString = XMLGenerator.generateXML(helper.getErrorReply());
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("wucardlookupreply",xmlstring);
				System.out.println(xmlstring);
				
				return;
			}
			
			
		}catch (NullPointerException ne) {
			log.info("Null Pointer"+ ne.getMessage());
			setMessage("Method Name::wuh2hWUCardEnrolment()"+ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public void wuh2hWUCardEnrolmentInvoke(){
		
		StringBuffer addrline1=new StringBuffer();
		StringBuffer addrline2=new StringBuffer();
		StringBuffer address=new StringBuffer();
		String isoCurrencyCode = null;
		String isoCountryCode = null; 
		String senderCountryName = null;
		List<ContactDetail> customerContactdetails = icustomerRegistrationService
				.getCustomerContactDetails(getCustomerNo());
		
		if (customerContactdetails.size() > 0) {

			addrline1 = new StringBuffer();
			addrline1 = addrline1.append(customerContactdetails.get(0)
					.getBlock());
			if (customerContactdetails.get(0).getBuildingNo() != null
					|| customerContactdetails.get(0).getBuildingNo()
							.length() != 0) {
				addrline1 = addrline1.append(",");
				addrline1 = addrline1.append(customerContactdetails.get(0)
						.getBuildingNo());
			}
			//address.setAddrLine1("1 st Avenue");

			addrline2 = new StringBuffer();
			addrline2 = addrline2.append(customerContactdetails.get(0)
					.getFlat());
			if (customerContactdetails.get(0).getStreet() != null
					|| customerContactdetails.get(0).getStreet().length() != 0) {
				addrline2 = addrline2.append(customerContactdetails.get(0)
						.getStreet());
			}
			setEnrolAddress(addrline1.toString());

			String senderCityname = iwuh2hService.getNameDescription(
					customerContactdetails.get(0).getFsCityMaster()
							.getCityId(),
					sessionStateManage.getLanguageId(), "city");
			
			setEnrolCityName(senderCityname);
			setEnrolContactPhone(customerContactdetails.get(0)
					.getMobile());
			
			isoCountryCode = customerContactdetails.get(0).getFsCountryMaster()
					.getCountryAlpha2Code();
			setEnrolCountryCode(isoCountryCode);
			
			List<CurrencyMaster> countryCurrencyList = generalService.getCountryCurrencyList(customerContactdetails.get(0).getFsCountryMaster()
					.getCountryId());
			
			if(countryCurrencyList.size()>0){
				isoCurrencyCode = countryCurrencyList.get(0).getIsoCurrencyCode();
			}	
			setEnrolCurrencyCode(isoCurrencyCode);
			
		}
		
		senderCountryName = iwuh2hService.getNameDescription(
				customerContactdetails.get(0).getFsCountryMaster()
						.getCountryId(),
				sessionStateManage.getLanguageId(), "country");
		setEnrolCountryName(senderCountryName);
				
		RequestContext.getCurrentInstance()
		.execute("message.show();");
		
	}
	
	public String  wuh2hWUCardEnrolment(){
		
		WuCardEnrollmentRequest request = null;
		WuCardEnrollmentReply response = null;
		String referenceno = null;
		String returnMessage = null;
		try{
			if(getWuTransReferenceNo()!=null){
				setWuTransReferenceNo(null);
			}
			
			referenceno = iwuh2hService.getNextReferenceNo();
			setWuTransReferenceNo(referenceno);
			
			request = new WuCardEnrollmentRequest();
			response = new WuCardEnrollmentReply();
			Certification.keystoreSetup();
			
			request = fetchWUCardEnrolment();
			
			convertedString = XMLGenerator.generateXML(request);
			xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			xmlstring = xmlhead+"\n"+convertedString;			
			writeToFile("wucardenrollmentrequest",xmlstring);
			System.out.println(xmlstring);
			
			WuCardEnrollmentReplyHelper helper = null;
			helper =WUCardEnrollmentBO.getWUCardEnrollment(request);
			
			if((helper.getErrorReply())==null){
				response = helper.getWuCardEnrollmentReply();
				
				convertedString = XMLGenerator.generateXML(response);
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("wucardenrollmentreply",xmlstring);
				System.out.println(xmlstring);
				
				System.out.println(response.getSender().getPreferredCustomer().getMywuNumber());
				setWuCardno(response.getSender().getPreferredCustomer().getMywuNumber());
				updateWUCardNo(getWuCardno());
				setSendWUcardNo(getWuCardno());
				
				returnMessage = null;
				
			}else{
				setErrorMessage(helper.getErrorReply().getError());
				RequestContext.getCurrentInstance().execute("error.show();");
				
				convertedString = XMLGenerator.generateXML(helper.getErrorReply());
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("wucardenrollmentreply",xmlstring);
				System.out.println(xmlstring);
				
				returnMessage = helper.getErrorReply().getError();
				
			}			
			
			
		}catch (NullPointerException ne) {
			log.info("Null Pointer"+ ne.getMessage());
			
			returnMessage = "Method Name::wuh2hWUCardEnrolment()"+ne.getMessage();
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			
			returnMessage = exception.getMessage();
		}
		return returnMessage;
	}
	
	
	public WuCardEnrollmentRequest fetchWUCardEnrolment(){
		
		WuCardEnrollmentRequest request = null;
		
		WuCardEnrollmentReply response = new WuCardEnrollmentReply();
		ForeignRemoteSystem foreignRemoteSystem = null;
		Channel channel = null;
		PaymentDetails paymentDetails = null;
		CountryCurrencyInfo originCountryCurrencyInfo = null;
		CountryCurrencyInfo destCountryCurrencyInfo = null;
		CountryCurrencyInfo recCountryCurrencyInfo = null;
		CountryCurrencyInfo addCountryCurrencyInfo = null;
		IsoCode originIsoCode = null;
		IsoCode destIsoCode = null;
		IsoCode recIsoCode = null;
		IsoCode addIsoCode = null;
		
		Sender sender = null;
		GeneralName generalName = null;
		Address address = null;
		
		try{			
			request = new WuCardEnrollmentRequest();
			
			channel = new Channel();
			channel.setType(ChannelType.H_2_H);
			channel.setName(channelName);
			channel.setVersion(channelVersion);	
			request.setChannel(channel);
			
			// Sender Details
			sender = new Sender();
			generalName = new GeneralName();
			generalName.setNameType(NameType.D);
			generalName.setFirstName(getSenderFirstName());
			generalName.setLastName(getSenderLastName());
			sender.setName(generalName);

			// Sender Address
			address = new Address();
			StringBuffer addrline1 = null;
			StringBuffer addrline2 = null;
			String senderCountryName = null;
			String senderCityname = null;
			
			
			String isoCountryCode = null;
			String isoCurrencyCode = null;
			
			List<ContactDetail> customerContactdetails = icustomerRegistrationService
					.getCustomerContactDetails(getCustomerNo());
			
		   for( ContactDetail contactDetail:customerContactdetails){
			   if(contactDetail.getFsCountryMaster().getCountryId().compareTo(new BigDecimal(91)) == 0 ){
					addrline1 = new StringBuffer();
					addrline1 = addrline1.append(contactDetail
							.getBlock());
					if (contactDetail.getBuildingNo() != null
							|| contactDetail.getBuildingNo()
									.length() != 0) {
						addrline1 = addrline1.append(",");
						addrline1 = addrline1.append(contactDetail
								.getBuildingNo());
					}
					address.setAddrLine1("1 st Avenue");

					addrline2 = new StringBuffer();
					addrline2 = addrline2.append(contactDetail
							.getFlat());
					if (contactDetail.getStreet() != null
							|| contactDetail.getStreet().length() != 0) {
						addrline2 = addrline2.append(contactDetail
								.getStreet());
					}
					//address.setAddrLine2(addrline2.toString());

					senderCityname = iwuh2hService.getNameDescription(
							contactDetail.getFsCityMaster()
									.getCityId(),
							sessionStateManage.getLanguageId(), "city");
					address.setCity(senderCityname);
					address.setPostalCode("852741");
					//address.setStreet("1st Avenue");
					sender.setContactPhone(contactDetail
							.getMobile());
					
					isoCountryCode = contactDetail.getFsCountryMaster()
							.getCountryAlpha2Code();
					
					List<CurrencyMaster> countryCurrencyList = generalService.getCountryCurrencyList(contactDetail.getFsCountryMaster()
							.getCountryId());			
					if(countryCurrencyList.size()>0){
						isoCurrencyCode = countryCurrencyList.get(0).getIsoCurrencyCode();
					}				
					addCountryCurrencyInfo = new CountryCurrencyInfo();
					addIsoCode = new IsoCode();
					addIsoCode.setCountryCode(isoCountryCode);
					addIsoCode.setCurrencyCode(isoCurrencyCode);
					addCountryCurrencyInfo.setIsoCode(addIsoCode);
				
					senderCountryName = iwuh2hService.getNameDescription(
							contactDetail.getFsCountryMaster()
									.getCountryId(),
					sessionStateManage.getLanguageId(), "country");
				   
			   }
			   
		   }
			

			addCountryCurrencyInfo.setCountryName(senderCountryName);
			address.setCountryCode(addCountryCurrencyInfo);
			sender.setAddress(address);			
			request.setSender(sender);
			
			// Payment details
			paymentDetails = new PaymentDetails();
			
			if(isoCurrencyCode!=null&&isoCountryCode!=null){			
			
				// receiver country/currency
				recCountryCurrencyInfo = new CountryCurrencyInfo();
				recIsoCode = new IsoCode();
				recIsoCode.setCountryCode(isoCountryCode);
				recIsoCode.setCurrencyCode(isoCurrencyCode);
				recCountryCurrencyInfo.setIsoCode(recIsoCode);
	
				originCountryCurrencyInfo = new CountryCurrencyInfo();
				originIsoCode = new IsoCode();
				originIsoCode.setCountryCode(isoCountryCode);
				originIsoCode.setCurrencyCode(isoCurrencyCode);
				originCountryCurrencyInfo.setIsoCode(originIsoCode);
	
				destCountryCurrencyInfo = new CountryCurrencyInfo();
				destIsoCode = new IsoCode();
				destIsoCode.setCountryCode(isoCountryCode);
				destIsoCode.setCurrencyCode(isoCurrencyCode);
				destCountryCurrencyInfo.setIsoCode(destIsoCode);
	
				paymentDetails.setRecordingCountryCurrency(recCountryCurrencyInfo);
				paymentDetails
						.setOriginatingCountryCurrency(originCountryCurrencyInfo);
				paymentDetails
						.setDestinationCountryCurrency(destCountryCurrencyInfo);
				
				request.setPaymentDetails(paymentDetails);			
			}
			
			// Foreign remote system info
			foreignRemoteSystem = new ForeignRemoteSystem();
			foreignRemoteSystem.setReferenceNo(getWuTransReferenceNo());
			foreignRemoteSystem.setCounterId(remoteCounterId);
			foreignRemoteSystem.setIdentifier(remoteIdentifier);	
			request.setForeignRemoteSystem(foreignRemoteSystem);
			
			
		}catch (Exception exception) {
			request = null;
		}
		
		return request;
	}

	
	
	public WuCardLookupRequest fetchWUCardLookup(String wucardno){
		
		WuCardLookupRequest request = null;
		WuCardLookupReply response = null;
		
		ForeignRemoteSystem foreignRemoteSystem = null;
		Channel channel = null;
		Sender sender = null;
		PreferredCustomer preferredCustomer = null;
		try{
			
			request = new WuCardLookupRequest();
			
			// Channel Info
			channel = new Channel();
			channel.setType(ChannelType.H_2_H);
			channel.setName(channelName);
			channel.setVersion(channelVersion);
			request.setChannel(channel);	
			
			sender = new Sender();
			 preferredCustomer = new PreferredCustomer();
			 preferredCustomer.setPermanentChange(CardUpdateIndicator.LOOK_UP);
			 preferredCustomer.setMywuNumber(wucardno);
			 sender.setPreferredCustomer(preferredCustomer);
			 
			 request.setSender(sender);
			
			// Foreign remote system infouh2hf
			foreignRemoteSystem = new ForeignRemoteSystem();
			foreignRemoteSystem.setReferenceNo(getWuTransReferenceNo());
			foreignRemoteSystem.setCounterId(remoteCounterId);
			foreignRemoteSystem.setIdentifier(remoteIdentifier);	
			request.setForeignRemoteSystem(foreignRemoteSystem);
			request.setReceiverIndexNumber("001");
			request.setCardLookupSearchType("S");
			
		}catch (Exception exception) {
			request = null;
		}
		return request;
	}

	
	 * Fetch fee inquiry request
	 

	public FeeInquiryRequest fetchWUH2HFeeEnquiryRequest() {

		FeeInquiryRequest request = null;
		ForeignRemoteSystem foreignRemoteSystem = null;
		Channel channel = null;
		Financials financials = null;
		Promotions promotions = null;
		DeliveryServices deliveryServices = null;
		PaymentDetails paymentDetails = null;
		CountryCurrencyInfo originCountryCurrencyInfo = null;
		CountryCurrencyInfo destCountryCurrencyInfo = null;
		CountryCurrencyInfo recCountryCurrencyInfo = null;
		IsoCode originIsoCode = null;
		IsoCode destIsoCode = null;
		IsoCode recIsoCode = null;
		MessageDetails msgDetails = null;
		Messages messages = null;
		
		
		try {
			request = new FeeInquiryRequest();

			// Channel Info
			channel = new Channel();
			channel.setType(ChannelType.H_2_H);
			channel.setName(channelName);
			channel.setVersion(channelVersion);
			request.setChannel(channel);

			// Financial value
			financials = new Financials();
			if (getFixedCurrencyCode().equals("KWD")) {
				financials.setOriginatorsPrincipalAmount(getSendAmount().multiply(bigdecimalhundred).longValue());
				financials.setDestinationPrincipalAmount(new Long(0));
			} else {
				financials.setOriginatorsPrincipalAmount(new Long(0));
				financials.setDestinationPrincipalAmount(getSendAmount().multiply(bigdecimalhundred).longValue());
			}
			request.setFinancials(financials);

			// Payment details
			paymentDetails = new PaymentDetails();
			
			// Recording Country/ Currency
			recCountryCurrencyInfo = new CountryCurrencyInfo();
			recIsoCode = new IsoCode();
			recIsoCode.setCountryCode(getRecordingCountryCode());
			recIsoCode.setCurrencyCode(getRecordingISOCurrencyCode());
			recCountryCurrencyInfo.setIsoCode(recIsoCode);
			
			// Origination Country/Currency
			originCountryCurrencyInfo = new CountryCurrencyInfo();
			originIsoCode = new IsoCode();
			originIsoCode.setCountryCode(getOriginISOCountryCode());
			originIsoCode.setCurrencyCode(getOriginISOCurrencyCode());
			originCountryCurrencyInfo.setIsoCode(originIsoCode);
			
			// Destination Country/Currency
			destCountryCurrencyInfo = new CountryCurrencyInfo();
			destIsoCode = new IsoCode();
			destIsoCode.setCountryCode(getReceiverISOCountryCode());
			destIsoCode.setCurrencyCode(getReceiverISOCurrencyCode());
			destCountryCurrencyInfo.setIsoCode(destIsoCode);

			paymentDetails.setRecordingCountryCurrency(recCountryCurrencyInfo);
			paymentDetails
					.setOriginatingCountryCurrency(originCountryCurrencyInfo);
			paymentDetails
					.setDestinationCountryCurrency(destCountryCurrencyInfo);

			if (getFixedCurrencyCode().equals("KWD")) {
				paymentDetails.setTransactionType(TransactionType.WMN);
				paymentDetails.setFixOnSend(YesNo.FALSE);
			} else {
				paymentDetails.setTransactionType(TransactionType.WMF);
				paymentDetails.setFixOnSend(YesNo.TRUE);
			}
			paymentDetails.setPaymentType(PaymentType.CASH);			
			request.setPaymentDetails(paymentDetails);
			
			// Promotions value
			if (getPromotionCode() != null && getPromotionCode().length() != 0) {
				promotions = new Promotions();
				promotions.setCouponsPromotions(getPromotionCode());
				request.setPromotions(promotions);
			}

			// Delivery services value
			deliveryServices = new DeliveryServices();
			deliveryServices.setCode("000");
			
			
			if(messageDetailList.size()>0){
				messages = new Messages();
				messages.setContext(String.valueOf(messageDetailList.size()));
				List<String> text = new ArrayList<String>();
				StringBuffer senderMessage =new StringBuffer();
				int i = 0;
				for(MessageDetail detail: messageDetailList){
					i=i+1;
					msgDetails = new MessageDetails();
					text.add(detail.getWuMessage());
					messages.setText(text);
					msgDetails.setMessageDetails(messages);
					
					
					senderMessage = senderMessage.append(i+".  "+detail.getWuMessage());
					senderMessage = senderMessage.append("\n");
				}
				deliveryServices.setMessage(msgDetails);
				setReceiverMessage(senderMessage.toString());
			}	
			request.setDeliveryServices(deliveryServices);
			
			if(getSendWUcardNo()!=null){
				//request.setPreferredCustomerNo(getSendWUcardNo());
				request.setMywuNumber(getSendWUcardNo());
			}
			// Foreign remote system infouh2hf
			foreignRemoteSystem = new ForeignRemoteSystem();
			foreignRemoteSystem.setReferenceNo(getWuTransReferenceNo());
			foreignRemoteSystem.setCounterId(remoteCounterId);
			foreignRemoteSystem.setIdentifier(remoteIdentifier);	
			request.setForeignRemoteSystem(foreignRemoteSystem);
			

		}catch (Exception exception) {
			request = null;
		}

		return request;
	}

	
	 * 
	 * Fetching request for Send Money Validation
	 

	public SendMoneyValidationRequest fetchWUH2HSendMoneyValidation() {

		SendMoneyValidationRequest request = null;
		ForeignRemoteSystem foreignRemoteSystem = null;
		Channel channel = null;
		Promotions promotions = null;
		Financials financials = null;
		DeliveryServices deliveryServices = null;
		PaymentDetails paymentDetails = null;
		CountryCurrencyInfo originCountryCurrencyInfo = null;
		CountryCurrencyInfo destCountryCurrencyInfo = null;
		CountryCurrencyInfo recCountryCurrencyInfo = null;
		CountryCurrencyInfo addCountryCurrencyInfo = null;
		CountryCurrencyInfo thirdpartyCountryCurrencyInfo = null;
		IsoCode originIsoCode = null;
		IsoCode destIsoCode = null;
		IsoCode recIsoCode = null;
		IsoCode addIsoCode = null;
		IsoCode thirdpartyIsoCode = null;

		GeneralName generalName = null;
		GeneralName thirdpartyName = null;
		Address address = null;
		ComplianceAddress thirtpartyAddress = null;
		PreferredCustomer preferredCustomer = null;

		ComplianceDetails complianceDetails = null;
		IdDetails idDetails = null;
		IdDetails thirdparttId = null;
		ThirdPartyDetails thirdPartyDetails = null;
		Receiver receiver = null;
		Sender sender = null;
		MessageDetails msgDetails = null;
		Messages messages = null;

		try {
			request = new SendMoneyValidationRequest();
			
			// Channel Info
			channel = new Channel();
			channel.setType(ChannelType.H_2_H);
			channel.setName(channelName);
			channel.setVersion(channelVersion);
			request.setChannel(channel);

			// Financial value
			financials = new Financials();
			if (getFixedCurrencyCode().equals("KWD")) {
				financials.setOriginatorsPrincipalAmount(getSendAmount().multiply(bigdecimalhundred).longValue());
				financials.setDestinationPrincipalAmount(new Long(0));
			} else {
				financials.setOriginatorsPrincipalAmount(new Long(0));
				financials.setDestinationPrincipalAmount(getSendAmount().multiply(bigdecimalhundred).longValue());
			}
			request.setFinancials(financials);

			List<ContactDetail> customerContactdetails = icustomerRegistrationService
					.getCustomerContactDetails(getCustomerNo());

			// Sender Details
			sender = new Sender();
			generalName = new GeneralName();
			generalName.setNameType(NameType.D);
			generalName.setFirstName(getSenderFirstName());
			generalName.setLastName(getSenderLastName());
			sender.setName(generalName);			
		
			// Sender Address
			address = new Address();
			StringBuffer addrline1 = null;
			StringBuffer addrline2 = null;
			String senderCountryName = null;
			String senderCityname = null;
			if (customerContactdetails.size() > 0) {

				addrline1 = new StringBuffer();
				addrline1 = addrline1.append(customerContactdetails.get(0)
						.getBlock());
				if (customerContactdetails.get(0).getBuildingNo() != null) {
					addrline1 = addrline1.append(",");
					addrline1 = addrline1.append(customerContactdetails.get(0)
							.getBuildingNo());
				}
				address.setAddrLine1(addrline1.toString());

				addrline2 = new StringBuffer();
				addrline2 = addrline2.append(customerContactdetails.get(0)
						.getFlat());
				if (customerContactdetails.get(0).getStreet() != null) {
					addrline2 = addrline2.append(customerContactdetails.get(0)
							.getStreet());
				}
				address.setAddrLine2(addrline2.toString());

				senderCityname = iwuh2hService.getNameDescription(
						customerContactdetails.get(0).getFsCityMaster()
								.getCityId(),
						sessionStateManage.getLanguageId(), "city");
				address.setCity(senderCityname);
				address.setPostalCode("81101");
				sender.setContactPhone(customerContactdetails.get(0)
						.getTelephone());
			} else {
				address.setAddrLine1("1st Avenue");
				address.setCity("Kuwait");
				address.setPostalCode("81101");
			}
			addCountryCurrencyInfo = new CountryCurrencyInfo();
			addIsoCode = new IsoCode();
			addIsoCode.setCountryCode(getOriginISOCountryCode());
			addIsoCode.setCurrencyCode(getOriginISOCurrencyCode());
			addCountryCurrencyInfo.setIsoCode(addIsoCode);

			senderCountryName = iwuh2hService.getNameDescription(
					customerContactdetails.get(0).getFsCountryMaster()
							.getCountryId(),
					sessionStateManage.getLanguageId(), "country");

			addCountryCurrencyInfo.setCountryName(senderCountryName);
			address.setCountryCode(addCountryCurrencyInfo);
			sender.setAddress(address);

			System.out.println("origin " + getOriginISOCountryCode());
			System.out.println("origin " + getOriginISOCurrencyCode());
			
			// Prefered Customer			
			preferredCustomer = new PreferredCustomer();			
			if(getSendWUcardNo()!=null){
				preferredCustomer.setMywuNumber(getSendWUcardNo());
				//preferredCustomer.setLevelCode(getWuCardLevelCode());
				sender.setPreferredCustomer(preferredCustomer);
			}
			
			String expDate=new SimpleDateFormat("ddMMyyyy").format(getCustomerExpDate()) ;
			
			Date dtExpDate=new SimpleDateFormat("ddMMyyyy").parse(expDate);
			Calendar calendar =Calendar.getInstance();
			calendar.setTime(dtExpDate);
			calendar.add(Calendar.YEAR, -1);
			
			Date issueDate =calendar.getTime();
			

			complianceDetails = new ComplianceDetails();
			complianceDetails.setTemplateId("KWT_01_S");

			idDetails = new IdDetails();
			idDetails.setIdType(getCustomerIDType());
			idDetails.setIdNumber(getSenderIdNumber());
			idDetails.setIdCountryOfIssue("Kuwait");
			complianceDetails.setIdDetails(idDetails);
			complianceDetails.setIdExpirationDate("18122030");a
			complianceDetails.setIdIssueDate("18122007");
			complianceDetails.setDateOfBirth("18121976");
			complianceDetails.setCountryOfBirth("India");
			complianceDetails.setDoesTheIDHaveAnExpirationDate(YesNoFlag.Y);
			complianceDetails.setAckFlag("X");
			complianceDetails.setOccupation(getSenderOccupation());
			complianceDetails.setTransactionReason("Family expenses");
			
			

			complianceDetails.setIdDetails(idDetails);
			complianceDetails.setIdExpirationDate(new SimpleDateFormat("ddMMyyyy").format(getCustomerExpDate()) );
			complianceDetails.setIdIssueDate(new SimpleDateFormat("ddMMyyyy").format(issueDate));
			complianceDetails.setDateOfBirth(new SimpleDateFormat("ddMMyyyy").format(getDateOfBrith()));
			complianceDetails.setCountryOfBirth(getCustomerCountryName());
			complianceDetails.setDoesTheIDHaveAnExpirationDate(YesNoFlag.Y);
			complianceDetails.setAckFlag("X");
			complianceDetails.setOccupation(getSenderOccupation());
			if(!getWuPurposeTransaction().equalsIgnoreCase("others")){
				complianceDetails.setTransactionReason(getWuPurposeTransaction());
			}else{
				complianceDetails.setTransactionReason(getWuPurposeTransaction());
				complianceDetails.setOtherPurposeOfTransaction(getOtherPurposeTransaction());
			}

			
			sender.setComplianceDetails(complianceDetails);
			request.setSender(sender);

			// Receiver Info
			receiver = new Receiver();
			GeneralName recvName = new GeneralName();
			recvName.setNameType(NameType.D);
			recvName.setFirstName(getReceiverFirstName());
			recvName.setLastName(getReceiverLastName());
			receiver.setName(recvName);

			StringBuffer receiverAddrline1 = null;
			StringBuffer receiverAddrline2 = null;
			String receiverCountryName = null;
			String receiverCityname = null;
			String receiverStatename = null;

			Address recvaddress = new Address();
			recvaddress.setAddrLine1(getReceiverAddress1());

			receiverCityname = null;
			//recvaddress.setCity("Bangalore");
			//recvaddress.setState("KA");

			// recvaddress.setCity(getReceiverCityName());
			// recvaddress.setState(getReceiverStateName());
			recvaddress.setPostalCode(getReceiverPostalcode());

			CountryCurrencyInfo recvCountryCurrencyInfo = new CountryCurrencyInfo();
			IsoCode recvIsoCode = new IsoCode();
			recvIsoCode.setCountryCode(getReceiverISOCountryCode());
			recvIsoCode.setCurrencyCode(getReceiverISOCurrencyCode());
			recvCountryCurrencyInfo.setIsoCode(recvIsoCode);
			recvaddress.setCountryCode(recvCountryCurrencyInfo);

			//recvCountryCurrencyInfo.setCountryName("India");
			recvCountryCurrencyInfo.setCountryName(getReceiverCountryName());

			receiver.setAddress(recvaddress);
			//receiver.setReasonForSending("Family support"); //Optional no need now
			receiver.setContactPhone(getReceiverContactPhone());
			request.setReceiver(receiver);
			
			// Promotions value
			if (getPromotionCode() != null && getPromotionCode().length() != 0) {
				request.setPromotions(getPromoInfo());
			}
			// Promotions value
						if (getPromotionCode() != null && getPromotionCode().length() != 0) {
							promotions = new Promotions();
							promotions.setCouponsPromotions(getPromotionCode());
							request.setPromotions(promotions);
						}
			
			// Payment details
			paymentDetails = new PaymentDetails();

			// receiver country/currency
			recCountryCurrencyInfo = new CountryCurrencyInfo();
			recIsoCode = new IsoCode();
			recIsoCode.setCountryCode(getRecordingCountryCode());
			recIsoCode.setCurrencyCode(getRecordingISOCurrencyCode());
			recCountryCurrencyInfo.setIsoCode(recIsoCode);
			
			// Origination Country/ Currency
			originCountryCurrencyInfo = new CountryCurrencyInfo();
			originIsoCode = new IsoCode();
			originIsoCode.setCountryCode(getOriginISOCountryCode());
			originIsoCode.setCurrencyCode(getOriginISOCurrencyCode());
			originCountryCurrencyInfo.setIsoCode(originIsoCode);
			
			// Destination Country/Currency
			destCountryCurrencyInfo = new CountryCurrencyInfo();
			destIsoCode = new IsoCode();
			destIsoCode.setCountryCode(getReceiverISOCountryCode());
			destIsoCode.setCurrencyCode(getReceiverISOCurrencyCode());
			destCountryCurrencyInfo.setIsoCode(destIsoCode);

			paymentDetails.setRecordingCountryCurrency(recCountryCurrencyInfo);
			paymentDetails
					.setOriginatingCountryCurrency(originCountryCurrencyInfo);
			paymentDetails
					.setDestinationCountryCurrency(destCountryCurrencyInfo);
			if (getFixedCurrencyCode().equals("KWD")) {
				paymentDetails.setTransactionType(TransactionType.WMN);
				paymentDetails.setFixOnSend(YesNo.FALSE);
			} else {
				paymentDetails.setTransactionType(TransactionType.WMF);
				paymentDetails.setFixOnSend(YesNo.TRUE);
			}
			paymentDetails.setPaymentType(PaymentType.CASH);			
			paymentDetails.setPayWoIdIndicator(YesNo.N);
			
			if(getReceiverISOCountryCode().equals("MX")){
				ExpectedPayoutLocation expectedPayoutLocation = new ExpectedPayoutLocation();
				expectedPayoutLocation.setStateCode(getWuStateCode());
				expectedPayoutLocation.setCity(getWuCityName());
				paymentDetails.setExpectedPayoutLocation(expectedPayoutLocation);
			}
			if(getReceiverISOCountryCode().equals("US")){
				ExpectedPayoutLocation expectedPayoutLocation = new ExpectedPayoutLocation();
				expectedPayoutLocation.setStateCode(getWuStateCode());
				paymentDetails.setExpectedPayoutLocation(expectedPayoutLocation);
				
			}			
			paymentDetails.setDuplicateDetectionFlag(duplicateDetectFlag);			
			request.setPaymentDetails(paymentDetails);
			
			deliveryServices = new DeliveryServices();
			if(getReceiverMessage()!=null && getReceiverMessage().length()>0)
			{
				//Message Details
				msgDetails = new MessageDetails();
				messages = new Messages();
				messages.setContext(getReceiverMessage());
				msgDetails.setMessageDetails(messages);
				deliveryServices.setMessage(msgDetails);
			}
			
			
			
			if(messageDetailList.size()>0){
				messages = new Messages();
				messages.setContext(String.valueOf(messageDetailList.size()));
				List<String> text = new ArrayList<String>();
				StringBuffer senderMessage =new StringBuffer();
				int i = 0;
				for(MessageDetail detail: messageDetailList){
					i=i+1;
					msgDetails = new MessageDetails();
					text.add(detail.getWuMessage());
					messages.setText(text);
					msgDetails.setMessageDetails(messages);
					
					
					senderMessage = senderMessage.append(i+".  "+detail.getWuMessage());
					senderMessage = senderMessage.append("\n");
				}
				deliveryServices.setMessage(msgDetails);
				setReceiverMessage(senderMessage.toString());
			}
			
			if(getSecurityQuestionAvailable()!=null && getSecurityQuestionAvailable())
			{
				if((getQuestion()!=null && getQuestion().length()>0) && (getAnswer()!=null && getAnswer().length()>0))
				{
					IdentificationQuestion iq = new IdentificationQuestion();
					iq.setQuestion(getQuestion());
					iq.setAnswer(getAnswer());
					deliveryServices.setIdentificationQuestion(iq);
				}
				
			}
			deliveryServices.setCode("000");
			
			
			request.setDeliveryServices(deliveryServices);
			
			// Foreign remote system info
			request.setForeignRemoteSystem(getForeignRemoteSystemInfo());

		} catch (Exception exception) {
			request = null;
		}

		return request;

	}

	
	 * 
	 * Fetching request for Send Money Store
	 

	public SendMoneyStoreRequest fetchWUH2HSendMoneyStore() {

		SendMoneyStoreRequest request = null;
		MessageDetails msgDetails = null;
		Messages messages = null;
		Channel channel = null;
		try {

			request = new SendMoneyStoreRequest();	
			
			//request.setChannel(getChannelInfo());
			// Channel Info
			channel = new Channel();
			channel.setType(ChannelType.H_2_H);
			channel.setName(channelName);
			channel.setVersion(channelVersion);
			request.setChannel(channel);
			request.setSender(getSenderInfo());
			request.setReceiver(getReceiverInfo());
			request.setPromotions(getPromoInfo());
			request.setForeignRemoteSystem(getForeignRemoteSystemInfo());
			
			ExpectedPayoutLocation expectedPayoutLocation = new ExpectedPayoutLocation();
			expectedPayoutLocation.setStateCode(getExpectedStateCode());
			expectedPayoutLocation.setCity(getExpectedCityName());
			getPaymentDetailsInfo().setExpectedPayoutLocation(expectedPayoutLocation);
			
			DeliveryServices deliveryServices = new DeliveryServices();
			if(getReceiverMessage()!=null && getReceiverMessage().length()>0)
			{
				//Message Details
				MessageDetails msgDetails = new MessageDetails();
				Messages messages = new Messages();
				messages.setContext(getReceiverMessage());
				msgDetails.setMessageDetails(messages);
				deliveryServices.setMessage(msgDetails);
			}
			if(messageDetailList.size()>0){
				messages = new Messages();
				messages.setContext(String.valueOf(messageDetailList.size()));
				List<String> text = new ArrayList<String>();
				StringBuffer senderMessage =new StringBuffer();
				int i = 0;
				for(MessageDetail detail: messageDetailList){
					i=i+1;
					msgDetails = new MessageDetails();
					text.add(detail.getWuMessage());
					messages.setText(text);
					msgDetails.setMessageDetails(messages);
					
					
					senderMessage = senderMessage.append(i+".  "+detail.getWuMessage());
					senderMessage = senderMessage.append("\n");
				}
				deliveryServices.setMessage(msgDetails);
				setReceiverMessage(senderMessage.toString());
			}
			
			if(getSecurityQuestionAvailable()!=null && getSecurityQuestionAvailable())
			{
				if((getQuestion()!=null && getQuestion().length()>0) && (getAnswer()!=null && getAnswer().length()>0))
				{
					IdentificationQuestion iq = new IdentificationQuestion();
					iq.setQuestion(getQuestion());
					iq.setAnswer(getAnswer());
					deliveryServices.setIdentificationQuestion(iq);
				}
				
			}
			deliveryServices.setCode("000");
			
			request.setDeliveryServices(deliveryServices);
						
			request.setPaymentDetails(getPaymentDetailsInfo());
			request.setFinancials(getFinancialsInfo());
			
			request.setMtcn(getSendMtcno());
			request.setNewMtcn(getSendNewMtcno());
		
		} catch (Exception exception) {
			request = null;
		}

		return request;
	}

	public void wuh2hBackFromReceiveMoneySearch() {

		try {
			setTxnType(0);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2htransfercustomerinfo.xhtml");
		} catch (Exception e) {
			System.out.println("");
		}
	}

	public void wuh2hReceiveMoneySearch() {
		ReceiveMoneySearchRequest request = null;
		ReceiveMoneySearchReply response = null;
		String referencno = null;
		try {

			if(getWuTransReferenceNo()!=null){
				setWuTransReferenceNo(null);
			}
			
			referencno = iwuh2hService.getNextReferenceNo();
			setWuTransReferenceNo(referencno);

			setDisplayReceiverInfo(true);
			Certification.keystoreSetup();
			response = new ReceiveMoneySearchReply();
			request = fetchWUH2HReceiveMoneySearh();
			
			convertedString = XMLGenerator.generateXML(request);
			xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			xmlstring = xmlhead+"\n"+convertedString;			
			writeToFile("receivemoneysearchrequest",xmlstring);
			System.out.println(xmlstring);
		
			ReceiveMoneySearchReplyHelper helper = null;
			helper = ReceiveMoneySearchBO.searchReceiveMoney(request);
			
			if(helper.getErrorReply()==null){
				response = helper.getReceiveMoneySearchReply();
				
				convertedString = XMLGenerator.generateXML(response);
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("receivemoneysearchreply",xmlstring);
				System.out.println(xmlstring);
				
				fetchReceiveMoneySearchReply(response);
			}else{
				setErrorMessage(helper.getErrorReply().getError());
				RequestContext.getCurrentInstance().execute("error.show();");
				
				convertedString = XMLGenerator.generateXML(helper.getErrorReply());
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("receivemoneysearchreply",xmlstring);
				System.out.println(xmlstring);
				
				return;
			}
			
		} catch (NullPointerException ne) {
			log.info("Null Pointer"+ ne.getMessage());
			setErrorMessage("Method Name::wuh2hReceiveMoneySearch()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}
	
	public ReceiveMoneySearchRequest fetchWUH2HReceiveMoneySearh() {

		ReceiveMoneySearchRequest request = null;
		ForeignRemoteSystem foreignRemoteSystem = null;
		Channel channel = null;
		PaymentTransaction paymentTransaction = null;
		receiverSearchReplyList.clear();
		
		PaymentDetails paymentDetails = null;
		CountryCurrencyInfo destCountryCurrencyInfo = null;
		IsoCode destIsoCode = null;
				
		try {

			request = new ReceiveMoneySearchRequest();

			channel = new Channel();
			channel.setType(ChannelType.H_2_H);
			channel.setName(channelName);
			channel.setVersion(channelVersion);
			request.setChannel(channel);

			// Foreign remote system info
			foreignRemoteSystem = new ForeignRemoteSystem();
			foreignRemoteSystem.setReferenceNo(getWuTransReferenceNo());
			foreignRemoteSystem.setCounterId(remoteCounterId);
			foreignRemoteSystem.setIdentifier(remoteIdentifier);	
			request.setForeignRemoteSystem(foreignRemoteSystem);
			
			destCountryCurrencyInfo = new CountryCurrencyInfo();
			destIsoCode = new IsoCode();
			destIsoCode.setCountryCode("KW");
			destIsoCode.setCurrencyCode("KWD");
			destCountryCurrencyInfo.setIsoCode(destIsoCode);
			
			paymentDetails = new PaymentDetails();
			paymentDetails.setDestinationCountryCurrency(destCountryCurrencyInfo);
			
			paymentTransaction = new PaymentTransaction();
			paymentTransaction.setMtcn(getReceiveMtcnno());
			paymentTransaction.setPaymentDetails(paymentDetails);
			request.setPaymentTransaction(paymentTransaction);

		} catch (Exception e) {
			System.out.println("exception--" + e.getMessage());
			
			return null;
		}
		return request;
	}
	
	List<WUH2HReceiveInfoDataTable> receiverSearchReplyList = new ArrayList<WUH2HReceiveInfoDataTable>();

	public void fetchReceiveMoneySearchReply(
			ReceiveMoneySearchReply response) {

		List<PaymentTransaction> wuReceivepaymentTXNList = response
				.getPaymentTransactions().getPaymentTransaction();
		WUH2HReceiveInfoDataTable datatable = null;
		
		
		for (PaymentTransaction paymentTransaction : wuReceivepaymentTXNList) {
			datatable = new WUH2HReceiveInfoDataTable();		
			
			//Financial detail
			datatable.setGrossTotalAmount(paymentTransaction.getFinancials()
					.getGrossTotalAmount());
			datatable.setOriginationAmount(paymentTransaction.getFinancials()
					.getPrincipalAmount());		  //  RECEIPT PAYMENT === FC_AMOUNT	
			BigDecimal originationAmount=BigDecimal.valueOf(paymentTransaction.getFinancials()
					.getPrincipalAmount() == null ?  new Long(0) : paymentTransaction.getFinancials()
							.getPrincipalAmount());
			
			datatable.setOriginationAmountDisplay(originationAmount.movePointLeft(2));
			datatable.setPayAmount(paymentTransaction.getFinancials().getPayAmount());    //  RECEIPT PAYMENT  == LOCAL_AMOUNT
			datatable.setPlusChargesAmount(paymentTransaction.getFinancials()
					.getPlusChargesAmount());
			datatable.setCharge(paymentTransaction.getFinancials().getCharges());
			
			//Payment Details
			datatable.setExchangeRate(paymentTransaction.getPaymentDetails().getExchangeRate());    // RECEIPT PAYMENT  == EXCHANGE RATE
			datatable.setOriginatingCountryISOCode(paymentTransaction.getPaymentDetails().getOriginatingCountryCurrency().getIsoCode().getCountryCode());
			datatable.setOriginatingCurrencyISOCode(paymentTransaction.getPaymentDetails().getOriginatingCountryCurrency().getIsoCode().getCurrencyCode());
			
			datatable.setOriginalCountryISOCode(paymentTransaction.getPaymentDetails().getOriginalDestinationCountryCurrency().getIsoCode().getCountryCode());
			datatable.setOriginalCurrencyISOCode(paymentTransaction.getPaymentDetails().getOriginalDestinationCountryCurrency().getIsoCode().getCurrencyCode());
			
			datatable.setDestinationCountryISOCode(paymentTransaction.getPaymentDetails().getDestinationCountryCurrency().getIsoCode().getCountryCode());
			datatable.setDestinationCurrencyISOCode(paymentTransaction.getPaymentDetails().getDestinationCountryCurrency().getIsoCode().getCurrencyCode());			
			
			//Sender Details
			datatable.setSenderFirstName(paymentTransaction.getSender().getName().getFirstName());
			datatable.setSenderLastName(paymentTransaction.getSender().getName().getLastName());			
			datatable.setSenderAddressLine1(paymentTransaction.getSender().getAddress().getAddrLine1());
			datatable.setSenderAddressLine1(paymentTransaction.getSender().getAddress().getAddrLine2());
			datatable.setSenderCityName(paymentTransaction.getSender().getAddress().getCity());
			datatable.setSenderStateName(paymentTransaction.getSender().getAddress().getState());
			datatable.setSenderStreet(paymentTransaction.getSender().getAddress().getStreet());
			datatable.setSenderPostelCode(paymentTransaction.getSender().getAddress().getStateZip());
			datatable.setSenderCountryIsoCode(paymentTransaction.getSender().getAddress().getCountryCode().getIsoCode().getCountryCode());
			datatable.setSenderCurrencyIsoCode(paymentTransaction.getSender().getAddress().getCountryCode().getIsoCode().getCurrencyCode());
			datatable.setSenderContactNo(paymentTransaction.getSender().getContactPhone());
			datatable.setSenderNatinalityNo(paymentTransaction.getSender().getMobilePhone().getPhoneNumber().getNationalNumber());
			
			
			//Receiver			
			datatable.setReceiverFirstName(paymentTransaction.getReceiver().getName().getFirstName());
			datatable.setReceiverLasttName(paymentTransaction.getReceiver().getName().getLastName());
			datatable.setReceiverAddressLine1(paymentTransaction.getReceiver().getAddress().getAddrLine1());
			datatable.setReceiverAddressLine2(paymentTransaction.getReceiver().getAddress().getAddrLine2());
			datatable.setReceiverCity(paymentTransaction.getReceiver().getAddress().getCity());
			datatable.setReceiverState(paymentTransaction.getReceiver().getAddress().getState());
			datatable.setReceiverStreet(paymentTransaction.getReceiver().getAddress().getStreet());
			datatable.setReceiverContactPhone(paymentTransaction.getReceiver().getContactPhone());
			datatable.setReceiverPostelCode(paymentTransaction.getReceiver().getAddress().getStateZip());				
			datatable.setReceiverCountryIsoCode(paymentTransaction.getReceiver().getAddress().getCountryCode().getIsoCode().getCountryCode());
			datatable.setReceiverCurrencyIsoCode(paymentTransaction.getReceiver().getAddress().getCountryCode().getIsoCode().getCurrencyCode());			
			datatable.setReceiverMobileCountryCode(paymentTransaction.getReceiver().getMobilePhone().getPhoneNumber().getCountryCode());
			datatable.setReceiverMobileNationalNo(paymentTransaction.getReceiver().getMobilePhone().getPhoneNumber().getNationalNumber());
			datatable.setReceiverStateZip(paymentTransaction.getReceiver().getAddress().getStateZip());
			
			//Complaince details
			datatable.setReceiverDateOfBirth(paymentTransaction.getReceiver().getComplianceDetails().getDateOfBirth());
			datatable.setReceiverIdType(paymentTransaction.getReceiver().getComplianceDetails().getIdDetails().getIdType());
			datatable.setReceiverIdNumber(paymentTransaction.getReceiver().getComplianceDetails().getIdDetails().getIdNumber());
			datatable.setReceiverIdCountryOfIssue(paymentTransaction.getReceiver().getComplianceDetails().getIdDetails().getIdCountryOfIssue());
			datatable.setReceiverIdExpirationDate(paymentTransaction.getReceiver().getComplianceDetails().getIdExpirationDate());		
			datatable.setReceiverIdIssueDate(paymentTransaction.getReceiver().getComplianceDetails().getIdIssueDate());
			
			//Expected 				
			datatable.setExpectedCityName(paymentTransaction.getPaymentDetails().getExpectedPayoutLocation().getCity());
			datatable.setExpectedStateCode(paymentTransaction.getPaymentDetails().getExpectedPayoutLocation().getStateCode());
			datatable.setExpectedStateName(paymentTransaction.getPaymentDetails().getExpectedPayoutLocation().getStateName());			
			
			datatable.setMoneyTransferKey(paymentTransaction.getMoneyTransferKey());					
			datatable.setStatus(paymentTransaction.getStatus());
			datatable.setMtcn(paymentTransaction.getMtcn());
			datatable.setNewMtcn(paymentTransaction.getNewMtcn());
			datatable.setPayStatusDescription(paymentTransaction.getPayStatusDescription());			
			
			datatable
					.setCharge(paymentTransaction.getFinancials().getCharges());
			datatable.setPayAmount(paymentTransaction.getFinancials()
					.getPayAmount());
			datatable.setTotalDiscount(paymentTransaction.getFinancials()
					.getTotalDiscount());
			datatable.setDailyLimit(paymentTransaction.getFinancials()
					.getDailyLimit());
			datatable.setTransactionLimit(paymentTransaction.getFinancials()
					.getTransactionLimit());
			datatable.setTaxAmount(paymentTransaction.getFinancials()
					.getTaxes().getTaxAmount());
			datatable.setMinTransactionLimit(paymentTransaction.getFinancials()
					.getMaxTransactionLimit());
			datatable.setPayAmount(paymentTransaction.getFinancials()
					.getPayAmount());
			datatable.setAvailableAmount(paymentTransaction.getFinancials()
					.getAvailableAmount());
			datatable.setMoneyTransferLimit(paymentTransaction.getFinancials()
					.getMinTransactionLimit());
			datatable.setMoneyTransferLimit(paymentTransaction.getFinancials()
					.getMoneyTransferLimit());

			Sender sender=paymentTransaction.getSender();
			Address address = paymentTransaction.getReceiver().getAddress();
			String contactPhoneNumber=paymentTransaction.getReceiver().getContactPhone();
			datatable.setReceiverContactPhone(contactPhoneNumber);
			Financials financials = paymentTransaction.getFinancials();
			datatable.setAddress(address);
			datatable.setFinancials(financials);
			datatable.setSenderDetails(sender);
			
			receiverSearchReplyList.add(datatable);
			
			

		}
		
		StringBuilder sb= new StringBuilder();
		DeliveryServices  deliveryServices =response.getDeliveryServices();
		if(deliveryServices!=null)
		{
			MessageDetails messageDetails= response.getDeliveryServices().getMessage();
			if(messageDetails!=null)
			{
				com.westernunion.schema.xrsi.Messages messages =response.getDeliveryServices().getMessage().getMessageDetails();
				if(messages!=null)
				{
					List<String> lstMessages=messages.getText();
					int i=0;
					for(String message:lstMessages)
					{	i=i+1;
						sb.append(i+".  "+message);
						sb.append("\n");
						
					}
				}
			}
		}
		
		setSenderMessage(sb.toString());
	}

	public void onRowReceivePaySelect(SelectEvent event) {

		try {
			if (selectedWUH2HPayList != null || !selectedWUH2HPayList.isEmpty()) {
				selectedWUH2HPayList.clear();
			}

			selectedWUH2HPayList.add((WUH2HReceiveInfoDataTable) event
					.getObject());
			setIsWUSend(false);

			WUH2HReceiveInfoDataTable selectedRecord = selectedWUH2HPayList
					.get(0);

			if (selectedRecord != null) {				
								
				setReceiverFirstName(selectedRecord.getReceiverFirstName());
				setReceiverLastName(selectedRecord.getReceiverLasttName());
				setReceiverCurrencyName(selectedRecord.getReceiverCurrencyIsoCode());
				setReceiverISOCurrencyCode(selectedRecord.getOriginalCurrencyISOCode());//local Currency
				setReceiverISOCountryCode(selectedRecord.getOriginalCountryISOCode());
				setReceiverPostalcode(selectedRecord.getReceiverPostelCode());
				setReceiverCityName(selectedRecord.getReceiverCity());				
				setReceiverStreet(selectedRecord.getReceiverStreet());
				setReceiverStateName(selectedRecord.getReceiverState());
				setReceiverStateZip(selectedRecord.getReceiverStateZip());				
				setReceiverCurrency(selectedRecord.getReceiverCurrencyIsoCode());
				setReceiverCountry(selectedRecord.getReceiverCountryIsoCode());				
				setReceiverMobileCountryCode(selectedRecord.getReceiverMobileCountryCode());
				setReceiverMobileNationalNo(selectedRecord.getReceiverMobileNationalNo());
				
				
				
				//sender details
				setSenderFirstName(selectedRecord.getSenderFirstName());
				setSenderLastName(selectedRecord.getSenderLastName());
				setSenderaddresLine1(selectedRecord.getSenderAddressLine1());
				setSenderaddresLine2(selectedRecord.getSenderAddressLine2());
				setSenderStateName(selectedRecord.getSenderStateName());
				setSenderCity(selectedRecord.getSenderCityName());
				setSenderCountryName(selectedRecord.getSenderCountryIsoCode());				
				setSenderPostalCode(selectedRecord.getSenderPostelCode());
				setSenderStreetName(selectedRecord.getSenderStreet());
				setSenderContactNo(selectedRecord.getSenderContactNo());
				setSenderNatinalityNo(selectedRecord.getSenderNatinalityNo());
				
				//expected location
				setExpectedCityName(selectedRecord.getExpectedCityName());
				setExpectedStateCode(selectedRecord.getExpectedStateCode());
				setExpectedStateName(selectedRecord.getExpectedStateName());
				

				setReceiveAmount(BigDecimal.valueOf(selectedRecord.getDestinationAmount()== null ? new Long(0): selectedRecord.getDestinationAmount()));
				setReceiveMtcnno(selectedRecord.getMtcn());
				// setReceiveDocumentCode(selectedRecord.getreceived);
				
				setReceiverAddress1(selectedRecord.getReceiverAddressLine1());
				setOriginationAmount(BigDecimal.valueOf(selectedRecord.getOriginationAmount()== null ? new Long(0): selectedRecord.getOriginationAmount()));
				setDestinationAmount(BigDecimal.valueOf(selectedRecord.getPayAmount()== null ? new Long(0): selectedRecord.getPayAmount()));
				
				setSendCommission(BigDecimal.valueOf(selectedRecord.getCharge() == null ? new Long(0): selectedRecord.getCharge()));
				setSendCharges(BigDecimal.valueOf(selectedRecord.getPlusChargesAmount() ==null ? new Long(0) : selectedRecord.getPlusChargesAmount()));
				
				setOriginationCountryName(selectedRecord.getSenderCountryName());
				setDestinatonCountryName(selectedRecord.getReceiverCountryName());
				setSendCurrencyName(selectedRecord.getSenderCurrencyName());

				setSendExchangeRate(BigDecimal.valueOf(selectedRecord.getExchangeRate() == null ? new Double(0) : selectedRecord.getExchangeRate()));
				setSendPlusChargeAmount(BigDecimal.valueOf(selectedRecord.getPlusChargesAmount()== null ? new Long(0) : selectedRecord.getPlusChargesAmount()));
				setSendGrossTotalAmount(BigDecimal.valueOf(selectedRecord.getGrossTotalAmount()== null ? new Long(0) : selectedRecord.getGrossTotalAmount()));
				BigDecimal payAmount=BigDecimal.valueOf(selectedRecord.getPayAmount() == null ?  new Long(0) : selectedRecord.getPayAmount());
				setSendPayableAmountDisplay(payAmount.movePointLeft(2));
				setSendPayableAmount(payAmount);
				
				setSendMtcno(selectedRecord.getMtcn());
				setSendNewMtcno(selectedRecord.getNewMtcn());
				setMoneyTransferKey(selectedRecord.getMoneyTransferKey());
				
				setOriginISOCountryCode(selectedRecord.getOriginatingCountryISOCode());
				setOriginISOCurrencyCode(selectedRecord.getOriginatingCurrencyISOCode());//foregin currency
				
				setOriginalDestinationISOCountryCode(selectedRecord.getOriginalCountryISOCode());
				setOriginalDestinationISOCurrencyCode(selectedRecord.getOriginalCurrencyISOCode());
				
				setSenderDetails(selectedRecord.getSenderDetails());
				setReceiverAddress(selectedRecord.getAddress());
				setReceiverFinancials(selectedRecord.getFinancials());
				setReceiverContactPhone(selectedRecord.getReceiverContactPhone());
				//ID Details
				
				setReceiverDateOfBirth(selectedRecord.getReceiverDateOfBirth());
				setReceiverIdType(selectedRecord.getReceiverIdType());
				setReceiverIdNumber(selectedRecord.getReceiverIdNumber());
				setReceiverIdCountryOfIssue(selectedRecord.getReceiverIdCountryOfIssue());
				setReceiverIdExpirationDate(selectedRecord.getReceiverIdExpirationDate());		
				setReceiverIdIssueDate(selectedRecord.getReceiverIdIssueDate());				

			}
			String customername = null;
			String receivername = null;
			customername = getFirstName().concat(getSecondName());
					
			customername = customername.trim();
			customername = customername.toUpperCase();
			
			receivername = getReceiverFirstName().concat(getReceiverLastName());
			receivername = receivername.trim();
			receivername = receivername.toUpperCase();
			
			if(!customername.equals(receivername)){
				setErrorMessage("Customer name and Receiver name mismatched!");
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
			setAcknowledgement(true);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hreceivemoneypay.xhtml");

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::onRowReceivePaySelect()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
		// checkingBeneAccountDetails();
	}

	public void onRowReceivePayUnselect(UnselectEvent event) {
		if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
			selectedWUBeneList.clear();
		}
		this.selectedValues = null;
	}

	public void wuh2hReceiveMoneySelect() {

		ReceiveMoneySelectRequest request = null;
		ReceiveMoneySelectReply response = null;

		try {

			response = new ReceiveMoneySelectReply();
			Certification.keystoreSetup();

			// request = fetchWUH2HReceiveMoneySearh();
			// response = ReceiveMoneySearchBO.searchReceiveMoney(request);

			System.out.println(response);
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::wuh2hReceiveMoneySelect()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public String  wuh2hReceiveMoneyPay() {

		ReceiveMoneyPayRequest request = null;
		ReceiveMoneyPayReply response = null;
		String message = null;
		try {

			response = new ReceiveMoneyPayReply();
			Certification.keystoreSetup();

			request = wuh2hFetchReceiveMoneyPay();
			
			convertedString = XMLGenerator.generateXML(request);
			xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			xmlstring = xmlhead+"\n"+convertedString;			
			writeToFile("receivemoneypayrequest",xmlstring);
			System.out.println(xmlstring);
					
			ReceiveMoneyPayReplyHelper helper = null;
			helper = ReceiveMoneyPayBO.payReceiveMoney(request);
			
			if(helper.getErrorReply()==null){
				response = helper.getReceiveMoneyPayReply();
				message = null;
				
				convertedString = XMLGenerator.generateXML(helper.getReceiveMoneyPayReply());
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("receivemoneypayreply",xmlstring);
				System.out.println(xmlstring);
				
				setReceiveMoneyPaidOn(response.getPaidDateTime());
				setReceiveMtcnno(response.getMtcn());
				System.out.println("Receive paid status:"+response.getMtcn()+"Paid  "+response.getPaidDateTime());
				
				if(!getWuPurposeTransaction().equalsIgnoreCase("others")){
					setPurposeOfTransactions(getWuPurposeTransaction());
				}else{
					setPurposeOfTransactions(getOtherPurposeTransaction());
				}
				
				response.getMtcn();
				response.getNewMtcn();
				response.getPaidDateTime();
				response.getExternalReferenceNo();
				response.getNewPointsEarned();
				response.getReceiver().getName().getFirstName();
				response.getReceiver().getName().getLastName();
				response.getReceiver().getContactPhone();
				response.getReceiver().getIdDetails().getIdCountryOfIssue();
				response.getReceiver().getIdDetails().getIdIssuingAuthority();
				response.getReceiver().getIdDetails().getIdNumber();
				response.getReceiver().getIdDetails().getIdPlaceOfIssue();
				response.getReceiver().getIdDetails().getIdType();
	
				response.getHostMessageSet1();
				response.getHostMessageSet2();
				response.getHostMessageSet3();
				response.getPaymentDetails().getExchangeRate();
				response.getPaymentDetails()
						.getOriginalDestinationCountryCurrency().getCountryName();
				response.getPaymentDetails()
						.getOriginalDestinationCountryCurrency().getIsoCode()
						.getCountryCode();
				response.getPaymentDetails()
						.getOriginalDestinationCountryCurrency().getIsoCode()
						.getCurrencyCode();
				response.getPaymentDetails().getSendDate();
				response.getPaymentDetails().getDestination()
						.getActualPayoutAmount();
				response.getPaymentDetails().getDestination()
						.getExpectedPayoutAmount();
				response.getPaymentDetails().getDestination()
						.getExpectedPayoutMethod();
				response.getPaymentDetails().getDestination().getCountryIsoCode();
	
				response.getPaymentDetails().getMtRequestedStatus();
				response.getPaymentDetails().getOverrideCharges();
				response.getPaymentDetails().getReceiptOptOut();
				response.getPaymentDetails().getAuthStatus();
			}else{
				setErrorMessage(helper.getErrorReply().getError());
				RequestContext.getCurrentInstance().execute("error.show();");
				message = helper.getErrorReply().getError();
				
				convertedString = XMLGenerator.generateXML(helper.getErrorReply());
				xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				xmlstring = xmlhead+"\n"+convertedString;			
				writeToFile("receivemoneypayreply",xmlstring);
				System.out.println(xmlstring);
			}				
			

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::wuh2hReceiveMoneyPay()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			message = ne.getMessage();
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			message = exception.getMessage();
		}
		
		return message;

	}

	public ReceiveMoneyPayRequest wuh2hFetchReceiveMoneyPay() {

		ReceiveMoneyPayRequest request = null;

		ForeignRemoteSystem foreignRemoteSystem = null;
		Channel channel = null;
		Promotions promotions = null;
		Financials financials = null;
		DeliveryServices deliveryServices = null;
		PaymentDetails paymentDetails = null;
		CountryCurrencyInfo originCountryCurrencyInfo = null;
		CountryCurrencyInfo destCountryCurrencyInfo = null;
		CountryCurrencyInfo originalDestCountryCurrencyInfo = null;
		CountryCurrencyInfo addCountryCurrencyInfo = null;
		CountryCurrencyInfo thirdpartyCountryCurrencyInfo = null;
		IsoCode originIsoCode = null;
		IsoCode destIsoCode = null;
		IsoCode recIsoCode = null;
		IsoCode addIsoCode = null;
		IsoCode thirdpartyIsoCode = null;

		GeneralName generalName = null;
		GeneralName thirdpartyName = null;
		Address address = null;
		ComplianceAddress thirtpartyAddress = null;
		PreferredCustomer preferredCustomer = null;

		ComplianceDetails complianceDetails = null;
		IdDetails idDetails = null;
		IdDetails thirdparttId = null;
		ThirdPartyDetails thirdPartyDetails = null;
		Receiver receiver = null;
		Sender sender = null;

		ComplianceAddress complianceAddress = null;
		MobilePhone recvMobilePhone = null;
		InternationalPhoneNumber internPhoneno = null;
		ExpectedPayoutLocation expectedPayoutLocation = null;
		IsoCode originalDestIsoCode = null;
		try {
			StringBuffer addrline1=null;
			StringBuffer addrline2= null;
			String cityName=null;
			String stateCode=null;
			String countryName=null;
			List<ContactDetail> customerContactdetails = icustomerRegistrationService
					.getCustomerContactDetails(getCustomerNo());
			if (customerContactdetails!=null && customerContactdetails.size() > 0) {
				
				for(ContactDetail contactDetail :customerContactdetails )
				{
					if(contactDetail.getFsBizComponentDataByContactTypeId().getComponentDataId().intValue() == generalService.getComponentId(Constants.RESIDENCE, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()){
						addrline1 = new StringBuffer();
						addrline1 = addrline1.append(contactDetail
								.getBlock());
						if (contactDetail.getBuildingNo() != null ) {
							//addrline1 = addrline1.append(" ");
							addrline1 = addrline1.append(contactDetail
									.getBuildingNo());
						}
						
						addrline2 = new StringBuffer();
						addrline2 = addrline2.append(contactDetail
								.getFlat());//.append(" ");
						if (contactDetail.getStreet() != null) {
							addrline2 = addrline2.append(contactDetail
									.getStreet());
						}
						
						cityName = iwuh2hService.getNameDescription(
								contactDetail.getFsCityMaster()
										.getCityId(),
								sessionStateManage.getLanguageId(), "city");
						
						stateCode =contactDetail.getFsStateMaster().getStateCode();
						
						countryName = iwuh2hService.getNameDescription(
								contactDetail.getFsCountryMaster()
										.getCountryId(),
								sessionStateManage.getLanguageId(), "country");
						
					}
				}
				//contactDetails.getContactTypeId().intValue() == getGeneralService().getComponentId(Constants.RESIDENCE, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()
						
				
			}
			request = new ReceiveMoneyPayRequest();

			channel = new Channel();
			channel.setType(ChannelType.H_2_H);
			channel.setName(channelName);
			channel.setVersion(channelVersion);
			request.setChannel(channel);

			// Receiver Info
			receiver = new Receiver();
			GeneralName recvName = new GeneralName();
			recvName.setNameType(NameType.D);
			recvName.setFirstName(getReceiverFirstName());
			recvName.setLastName(getReceiverLastName());
			receiver.setName(recvName);

			// Receiver Address
			Address recvaddress = new Address();
			StringBuffer receiverAddrline1 = null;
			StringBuffer receiverAddrline2 = null;
			String receiverCountryName = null;
			String receiverCityname = null;
			String receiverStatename = null;

			CountryCurrencyInfo recvCountryCurrencyInfo = new CountryCurrencyInfo();
			IsoCode recvIsoCode = new IsoCode();
			recvIsoCode.setCountryCode(getReceiverISOCountryCode());
			recvIsoCode.setCurrencyCode(getReceiverISOCurrencyCode());
			//recvIsoCode.setCountryCode(getReceiverAddress().getCountryCode().getIsoCode().getCountryCode());
			//recvIsoCode.setCurrencyCode(getReceiverAddress().getCountryCode().getIsoCode().getCurrencyCode());
			
			recvCountryCurrencyInfo.setIsoCode(recvIsoCode);
			
			recvaddress.setCountryCode(recvCountryCurrencyInfo);
			recvaddress.setCity(cityName);
			
			recvaddress.setStateZip(getReceiverAddress().getStateZip());
			recvaddress.setStreet(addrline2.toString());
			recvaddress.setState(stateCode);
			//recvaddress.setLocalDeliveryArea("taluk");
			receiver.setAddress(recvaddress);
			
			
			
			 * preferredCustomer = new PreferredCustomer();
			 * preferredCustomer.setPermanentChange
			 * (CardUpdateIndicator.LOOK_UP);
			 * preferredCustomer.setMywuNumber("100875370");
			 * receiver.setPreferredCustomer(preferredCustomer);
			 

			// Receiver Compliance Details
			complianceDetails = new ComplianceDetails();
			complianceDetails.setTemplateId("KWT_01_P");		

			idDetails = new IdDetails();
			idDetails.setIdType(civilIdType);
			idDetails.setIdNumber(getSenderIdNumber());
			idDetails.setIdCountryOfIssue("Kueait");
			complianceDetails.setIdDetails(idDetails);
			complianceDetails.setIdExpirationDate("18122030");
			complianceDetails.setIdIssueDate("18122007");
			complianceDetails.setDateOfBirth("18121976");
			complianceDetails.setCountryOfBirth("India");
			complianceDetails.setDoesTheIDHaveAnExpirationDate(YesNoFlag.Y);
			complianceDetails.setAckFlag("X");
			complianceDetails.setOccupation(getSenderOccupation());
			complianceDetails.setTransactionReason("Family expenses");
			complianceDetails.setDoesReceiverHaveAPhoneNumber(YesNoFlag.Y);
			complianceDetails.setContactPhone("9751765207");
			
									
			idDetails = new IdDetails();
			
			idDetails.setIdType(getCustomerIDType());//Id type check
			idDetails.setIdNumber(getSenderIdNumber());//CIVIL ID
			idDetails.setIdCountryOfIssue("KUWAIT");//KUWAIT
			complianceDetails.setIdDetails(idDetails);
			
			String expDate=new SimpleDateFormat("ddMMyyyy").format(getCustomerExpDate()) ;
			
			Date dtExpDate=new SimpleDateFormat("ddMMyyyy").parse(expDate);
			Calendar calendar =Calendar.getInstance();
			calendar.setTime(dtExpDate);
			calendar.add(Calendar.YEAR, -1);
			
			Date issueDate =calendar.getTime();
			
			
			complianceDetails.setIdExpirationDate(new SimpleDateFormat("ddMMyyyy").format(getCustomerExpDate()) );//Check
			complianceDetails.setIdIssueDate(new SimpleDateFormat("ddMMyyyy").format(issueDate));//Check
			complianceDetails.setDateOfBirth(new SimpleDateFormat("ddMMyyyy").format(getDateOfBrith()));//Check
			complianceDetails.setCountryOfBirth(getCustomerCountryName());//Check
			complianceDetails.setDoesTheIDHaveAnExpirationDate(YesNoFlag.Y);
			complianceDetails.setAckFlag("X");
			complianceDetails.setOccupation(getOccupation());//Check(Corrected)
			if(!getWuPurposeTransaction().equalsIgnoreCase("others")){
				complianceDetails.setTransactionReason(getWuPurposeTransaction());
			}else{
				complianceDetails.setTransactionReason(getWuPurposeTransaction());
				complianceDetails.setOtherPurposeOfTransaction(getOtherPurposeTransaction());
			}
			complianceDetails.setDoesReceiverHaveAPhoneNumber(YesNoFlag.Y);
			
			complianceDetails.setContactPhone(getCutomerMobileNumber());

			complianceAddress = new ComplianceAddress();
			complianceAddress.setAddrLine1(addrline1.toString());
			complianceAddress.setAddrLine2(addrline2.toString());
			complianceAddress.setCity(cityName);
			complianceAddress.setStateCode(stateCode);
			complianceAddress.setPostalCode(getReceiverAddress().getStateZip());
			complianceAddress.setCountry(countryName);
			complianceDetails.setCurrentAddress(complianceAddress);
			
			//complianceDetails.setContactPhone(getReceiverContactPhone());
			
			receiver.setComplianceDetails(complianceDetails);
			 //<originating_city>CAMP HILLPA1</originating_city> Check

			// Receiver Mobile details
			recvMobilePhone = new MobilePhone();
			internPhoneno = new InternationalPhoneNumber();
			internPhoneno.setNationalNumber(getReceiverMobileNationalNo());
			internPhoneno.setCountryCode(getReceiverMobileCountryCode());
			recvMobilePhone.setPhoneNumber(internPhoneno);
			receiver.setMobilePhone(recvMobilePhone);

			// Payment Details
			paymentDetails = new PaymentDetails();

			expectedPayoutLocation = new ExpectedPayoutLocation();
			expectedPayoutLocation.setStateCode(getExpectedStateCode());
			//expectedPayoutLocation.setStateName(getExpectedStateName());
			expectedPayoutLocation.setCity(getExpectedCityName());
			paymentDetails.setExpectedPayoutLocation(expectedPayoutLocation);

			
			// Payment Details - Destination Country Currency Info
			destCountryCurrencyInfo = new CountryCurrencyInfo();
			destIsoCode = new IsoCode();
			destIsoCode.setCountryCode(getReceiverISOCountryCode());
			destIsoCode.setCurrencyCode(getReceiverISOCurrencyCode());
			destCountryCurrencyInfo.setIsoCode(destIsoCode);
			
			// Payment Details- Origin Country Currency Info
			originCountryCurrencyInfo = new CountryCurrencyInfo();
			originIsoCode = new IsoCode();
			originIsoCode.setCountryCode(getOriginISOCountryCode());
			originIsoCode.setCurrencyCode(getOriginISOCurrencyCode());
			originCountryCurrencyInfo.setIsoCode(originIsoCode);
			
			paymentDetails.setTransactionType(TransactionType.WMN);
			paymentDetails.setOriginatingCity(getOriginatingCity());
			paymentDetails.setExchangeRate(getSendExchangeRate()== null ? new Double(0) : getSendExchangeRate().doubleValue());

			// Payment Details - Original Country Currency Info
			originalDestCountryCurrencyInfo = new CountryCurrencyInfo();
			originalDestIsoCode = new IsoCode();
			originalDestIsoCode.setCountryCode(getOriginalDestinationISOCountryCode());
			originalDestIsoCode.setCurrencyCode(getOriginalDestinationISOCurrencyCode());
			originalDestCountryCurrencyInfo.setIsoCode(originalDestIsoCode);

			paymentDetails
					.setOriginalDestinationCountryCurrency(originalDestCountryCurrencyInfo);
			paymentDetails
					.setOriginatingCountryCurrency(originCountryCurrencyInfo);
			paymentDetails
					.setDestinationCountryCurrency(destCountryCurrencyInfo);			
			paymentDetails.setPayWoIdIndicator(YesNo.N);

			// Financial Details
			financials = new Financials();
			Taxes taxes = new Taxes();
			TaxWorksheetDetails taxWorksheetDetails = new TaxWorksheetDetails();
			taxes.setTaxWorksheetDetails(taxWorksheetDetails);
			financials.setTaxes(taxes);
			
					
			financials.setGrossTotalAmount(getReceiverFinancials().getGrossTotalAmount());
			financials.setPayAmount(getReceiverFinancials().getPayAmount());
			financials.setPrincipalAmount(getReceiverFinancials().getPrincipalAmount());
			financials.setCharges(getReceiverFinancials().getCharges());
			financials.setTolls(getReceiverFinancials().getTolls());
			
			// Foreign remote system info
			foreignRemoteSystem = new ForeignRemoteSystem();
			foreignRemoteSystem.setReferenceNo(getWuTransReferenceNo());
			foreignRemoteSystem.setCounterId(remoteCounterId);
			foreignRemoteSystem.setIdentifier(remoteIdentifier);	

			request.setReceiver(receiver);
			request.setPaymentDetails(paymentDetails);
			request.setFinancials(financials);
			request.setMoneyTransferKey(getMoneyTransferKey());
			request.setMtcn(getSendMtcno());
			request.setNewMtcn(getSendNewMtcno());
			request.setPayOrDoNotPayIndicator(PayOrDoNotPayIndicator.P);
			request.setForeignRemoteSystem(foreignRemoteSystem);

		} catch (Exception e) {

			System.out.println("exception--" + e.getMessage());
		}
		return request;
	}

	public void wuh2hBackFromReceiveMoneyPay() {

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hreceivemoneysearch.xhtml");
		} catch (Exception e) {
			System.out.println("");
		}
	}

	public void wuh2hBackFromReceiveMoneyDenom() {

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hreceivemoneypay.xhtml");
		} catch (Exception e) {
			System.out.println("");
		}
	}

	
	 * 
	 * SAVE CPOLLECTION DETAILS
	 
	
	public void selectWUCardLookup(){
		if(isWucardLookup()){
			setWucardLookup(true);
			setEnablePromotion("true");
			RequestContext.getCurrentInstance().execute("wulookup.show();");
			
			return;	
		}else{
			
			try {
				setWucardLookup(false);
				setSendWUcardNo(null);
				setPromotionCode(null);
				setWuCardno(null);
				setEnablePromotion("false");
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../wuh2h/wuh2hbenelistinfo.xhtml");
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	public void submitWUCard(){		
		wuh2hWUCardLookup();		
	}

	public void onRowSelect(SelectEvent event) {

		try {
			
			//Ram Mohan 06/01/2016
			clearRemitanceDetails();
			loadRemitanceDetails();
			
			setRecordingCurrencyId(null);
			wuh2hSendClear();
			
			setIsWUSend(false);
		
			if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
				selectedWUBeneList.clear();
			}

			selectedWUBeneList.add((WesternUnionH2HBeneficaryDataTable) event
					.getObject());
			setIsWUSend(false);

			WesternUnionH2HBeneficaryDataTable selectedRecord = selectedWUBeneList
					.get(0);			
			loadOtherDetails(selectedRecord);
			
			
			// ADD H2H INFO
			List<CountryMaster> countrymasterlist = generalService
					.getCountryAlphaList(selectedRecord.getCountryId());
			List<CurrencyMaster> currencyMasterList = generalService
					.getCurrency(selectedRecord.getCurrencyId());

			if (countrymasterlist.size() > 0) {
				setReceiverISOCountryCode(countrymasterlist.get(0)
						.getCountryAlpha2Code());
				setReceiverISOCountryId(countrymasterlist.get(0).getCountryId());
			}
			if (currencyMasterList.size() > 0) {
				setReceiverISOCurrencyCode(currencyMasterList.get(0)
						.getIsoCurrencyCode());
				setReceiverISOCurrencyId(currencyMasterList.get(0).getCurrencyId());
			}

			// RECEIVER INFO
			setReceiverAddress1(selectedRecord.getBuildingNo());
			setReceiverContactPhone("32422243");
			setReceiverPostalcode("445555");
			setReceiverCityName(selectedRecord.getCityName());
			setReceiverStateName(selectedRecord.getStateName());
			setReceiverCountryName(selectedRecord.getCountryName());
			setReceiverCurrencyName(selectedRecord.getCurrencyName());
			setSendRoutingBankId(selectedRecord.getBankId());
			setReceiverDebitAccountNo(selectedRecord.getBankAccountNumber());
			setReceiverSwiftBic(selectedRecord.getSwiftBic());
			setWuBeneBankId(selectedRecord.getBankId());
			setSendBeneCurrencyId(selectedRecord.getCurrencyId());			

			setReceiverFirstName(selectedRecord.getFirstName());
			setReceiverLastName(selectedRecord.getSecondName());

			setReceiverCountry(selectedRecord.getCountryName());
			setReceiverCurrency(selectedRecord.getCurrencyName());
			
			setWuBeneBranchId(selectedRecord.getBranchId());

			setEnableCardLookup(false);
			setEnableSendMoneyTxnStatus(false);
			fetchCurrencyList();
			fetchWUStateList();
			
			setDisplayWUState(false);
			setDisplayWUCity(false);
			
			if(getReceiverISOCountryCode().equals("MX")){
				setDisplayWUState(true);
				setDisplayWUCity(true);
			}
			if(getReceiverISOCountryCode().equals("US")){
				setDisplayWUState(true);
				setDisplayWUCity(false);
			}
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hmoneyreciverInfo.xhtml");

		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::onRowSelect()");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

	}

	public void onRowUnselect(UnselectEvent event) {
		if (selectedWUBeneList != null || !selectedWUBeneList.isEmpty()) {
			selectedWUBeneList.clear();
		}
		this.selectedValues = null;
	}
	
	public List<TempCollectDetail> wuh2hSaveCollectionDetailList(TempCollection collect) {
		
		BigDecimal totalAmt=BigDecimal.ZERO;
		List<TempCollectDetail> tempCollectionDetails = new ArrayList<TempCollectDetail>();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		String date = getCurrentDateWithFormat();
		int i = 1;
		Date acc_Month = null;
		
		CountryMaster countryMaster = null;
		CurrencyMaster currencyMaster = null;
		
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
		

		return tempCollectionDetails;

	}


	public HashMap<String, Object> wuh2hSaveCollection() {

		List<TempCollection> collectIdDetails = new ArrayList<TempCollection>();
		HashMap<String, Object> returnResult = new HashMap<String, Object>();
		TempCollection collect = new TempCollection();

		collect.setApplicationCountryId(sessionStateManage.getCountryId());
		Customer customer = new Customer();
		customer.setCustomerId(getCustomerNo());
		collect.setFsCustomer(customer);
		collect.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION));
		collect.setCollectDate(new Date());
		CurrencyMaster forcurrencymaster = new CurrencyMaster();
		forcurrencymaster.setCurrencyId(getRecordingCurrencyId());
		collect.setExCurrencyMaster(forcurrencymaster);
		collect.setPaidAmount(getPayPaidAmount());
		collect.setRefoundAmount(getPayRefund());
		collect.setNetAmount(getPayNetPaidAmount());
		try {
			collect.setAccountMMYYYY(new SimpleDateFormat("dd/MM/yyyy")
					.parse(getCurrentDateWithFormat()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		CountryBranch countryBrnach = new CountryBranch();
		countryBrnach.setCountryBranchId(new BigDecimal(sessionStateManage
				.getBranchId()));
		collect.setCountryBranch(countryBrnach);

		collect.setReceiptType(Constants.RECEIPT_TYPE);
		collect.setCreatedBy(sessionStateManage.getUserName());
		collect.setCreatedDate(new Date());
		collectIdDetails.add(collect);

		returnResult.put("Collect", collect);

		return returnResult;
	}


	// Save Currency denomination to currency adjust
	public List<TempForeignCurrencyAdjust> wuh2hSaveForeignCurrencyAdjust(
			TempCollection collect) {

		BigDecimal totalCollect = BigDecimal.ZERO;
		BigDecimal totalRefund = BigDecimal.ZERO;
		Boolean saveTempCurrencyAdjust = false;
		List<TempForeignCurrencyAdjust> foreignCurrencyAdjustlst = new ArrayList<TempForeignCurrencyAdjust>();

		CountryMaster countryMaster = null;
		CountryBranch countryBranch = null;
		CurrencyMaster currencyMaster = null;

		try {

			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
			Date acc_Month = null;
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());

			List<UserFinancialYear> finYearList = generalService
					.getDealYear(new Date());

			int i = 0;
			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstData) {
				if (!foreignLocalCurrencyDataTable.getQty().equals("")
						&& foreignLocalCurrencyDataTable.getQty() != null
						&& !foreignLocalCurrencyDataTable.getQty().equals("0")) {

					TempForeignCurrencyAdjust foreignCurrencyAdjust = new TempForeignCurrencyAdjust();

					// Country save
					countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(
							sessionStateManage.getSessionValue("countryId")));
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

					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(
							Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION));

					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(
							++i));
					foreignCurrencyAdjust.setDocumentDate(new Date());
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

					// country branch Id
					countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(
							sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					// currency Id
					currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(new BigDecimal(
							sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(
							foreignLocalCurrencyDataTable.getPrice()));
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(
							foreignLocalCurrencyDataTable.getQty()));
					// calculate amount
					totalCollect = totalCollect.add(foreignCurrencyAdjust
							.getAdjustmentAmount());

					// currency denomination Id
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster
							.setDenominationId(foreignLocalCurrencyDataTable
									.getDenominationId());
					foreignCurrencyAdjust
							.setFsDenominationId(denominationMaster);

					// western union exchange rate not available
					// foreignCurrencyAdjust.setExchangeRate(new
					// BigDecimal(getSendExchangeRate()));

					foreignCurrencyAdjust
							.setDenaminationAmount(foreignLocalCurrencyDataTable
									.getDenominationAmount());
					foreignCurrencyAdjust
							.setProgNumber(Constants.FC_SALE_REMIT);
					foreignCurrencyAdjust.setProgNumber("WESTERN UNION");

					foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					foreignCurrencyAdjust.setTransactionType(Constants.C);
					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage
							.getUserName());

					if (finYearList != null && !finYearList.isEmpty()) {
						foreignCurrencyAdjust
								.setDocumentFinanceYear(finYearList.get(0)
										.getFinancialYear());
					}
					if (collect != null) {
						foreignCurrencyAdjust.setTempCollection(collect);
					}

					// MTC no/ExchangeRate get from WUH2H Gateway response

					if (getSendMtcno() != null) {
						foreignCurrencyAdjust
								.setWesternUnionMicNo(getSendMtcno());
						foreignCurrencyAdjust
								.setExchangeRate(getSendExchangeRate());
					}

					foreignCurrencyAdjustlst.add(foreignCurrencyAdjust);

				} else {
					log.info("Number of notes is 0");
				}
			}

			int j = 0;
			for (ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable : lstRefundData) {
				if (!foreignLocalCurrencyDataTable.getQty().equals("")
						&& foreignLocalCurrencyDataTable.getQty() != null
						&& !foreignLocalCurrencyDataTable.getQty().equals("0")) {

					TempForeignCurrencyAdjust foreignCurrencyAdjust = new TempForeignCurrencyAdjust();

					// Country save
					countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(
							sessionStateManage.getSessionValue("countryId")));
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

					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(
							Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION));
					
					 * if (getTxnType() == 1) { foreignCurrencyAdjust
					 * .setDocumentNo(getSendDocumentNo()); } else if
					 * (getTxnType() == 2) { foreignCurrencyAdjust
					 * .setDocumentNo(getReceiveDocumentNo()); }
					 
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(
							++j));
					foreignCurrencyAdjust.setDocumentDate(new Date());
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

					// country branch Id
					countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(
							sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					// currency Id
					currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(new BigDecimal(
							sessionStateManage.getCurrencyId()));
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(
							foreignLocalCurrencyDataTable.getPrice()));
					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(
							foreignLocalCurrencyDataTable.getQty()));
					// calculate amount
					totalRefund = totalRefund.add(foreignCurrencyAdjust
							.getAdjustmentAmount());

					// currency denomination Id
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster
							.setDenominationId(foreignLocalCurrencyDataTable
									.getDenominationId());
					foreignCurrencyAdjust
							.setFsDenominationId(denominationMaster);

					// western union exchange rate not available
					// foreignCurrencyAdjust.setExchangeRate(BigDecimal.ONE);

					foreignCurrencyAdjust
							.setDenaminationAmount(foreignLocalCurrencyDataTable
									.getDenominationAmount());
					foreignCurrencyAdjust
							.setProgNumber(Constants.FC_SALE_REMIT);
					// foreignCurrencyAdjust.setProgNumber("WESTERN UNION");

					foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					foreignCurrencyAdjust.setTransactionType(Constants.F);

					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage
							.getUserName());

					if (finYearList != null && !finYearList.isEmpty()) {
						foreignCurrencyAdjust
								.setDocumentFinanceYear(finYearList.get(0)
										.getFinancialYear());
					}
					if (collect != null) {
						foreignCurrencyAdjust.setTempCollection(collect);
					}

					// MTC no/ExchangeRate get from WUH2H Gateway response
					if (getSendMtcno() != null) {
						foreignCurrencyAdjust
								.setWesternUnionMicNo(getSendMtcno());
						foreignCurrencyAdjust
								.setExchangeRate(getSendExchangeRate());
					}

					foreignCurrencyAdjustlst.add(foreignCurrencyAdjust);

				} else {
					log.info("Number of notes is 0");
				}
			}

			
			 * if (totalCollect.compareTo(BigDecimal.ZERO) != 0 ||
			 * totalRefund.compareTo(BigDecimal.ZERO) != 0) { BigDecimal
			 * originalAmount = BigDecimal.ZERO; if
			 * (totalCollect.compareTo(totalRefund) >= 0) { originalAmount =
			 * totalCollect.subtract(totalRefund); } else { originalAmount =
			 * totalRefund.subtract(totalCollect); }
			 * 
			 * if (getCalNetAmountPaid() != null &&
			 * getCalNetAmountPaid().compareTo(BigDecimal.ZERO) != 0) { if
			 * (originalAmount.compareTo(BigDecimal.ZERO) != 0 &&
			 * originalAmount.compareTo(getCalNetAmountPaid()) == 0) { // save
			 * rec saveTempCurrencyAdjust = true; westernUnionService
			 * .saveForeignCurrencyAdjust(foreignCurrencyAdjustlst); } else {
			 * saveTempCurrencyAdjust = false; } } else { saveTempCurrencyAdjust
			 * = null; } }
			 

		} catch (NullPointerException ne) {
			log.info("Exception in wuh2hSaveForeignCurrencyAdjust:"
					+ ne.getMessage());
			setErrorMessage("Method Name::wuh2hSaveForeignCurrencyAdjust");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
		} catch (Exception exception) {
			log.info("Exception in wuh2hSaveForeignCurrencyAdjust:"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		return foreignCurrencyAdjustlst;
	}

	public boolean wuh2hSaveRemittance() throws Exception{
		boolean rtnMainSaveSts=false;
		try{
			
			String errorMessage = null;
				// if any records available in Temp Currency Adjust- Hard Delete
				westernUnionService.deleteFromExTempCurrencyAdjust(
						getSendCompanyCode(), getSendDocumentCode(),
						getSendDocumentFinanceYear(), getSendDocumentNo(),
						getMtcNo());

				dynamicLevel();
				matchData();

				HashMap<String, Object> returnResult = wuh2hSaveCollection();
				TempCollection tempCollection = (TempCollection) returnResult
						.get("Collect");
				List<TempCollectDetail> tempDetailsList = wuh2hSaveCollectionDetailList(tempCollection);
				List<TempForeignCurrencyAdjust> tempAdjustList = wuh2hSaveForeignCurrencyAdjust(tempCollection);

				collectionId = iwuh2hService
						.saveTempCollectionwithDetailsandTempCurrencyAdjust(
								tempCollection, tempDetailsList, tempAdjustList);
				
			setSendTxnCollectionId(collectionId);
			
				booAmlCheckProcess();
				boolean saveSts=wuh2hSaveRemittanceApplication();
			if(saveSts){				
						rtnMainSaveSts=true;
			}else{
						rtnMainSaveSts=false;
						return rtnMainSaveSts;
					}
			
		}catch (NullPointerException ne) {
			rtnMainSaveSts=false;
			log.info("Exception in wuh2hSaveForeignCurrencyAdjust:"
					+ ne.getMessage());
			setErrorMessage("Method Name::wuh2hSaveRemittance");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
		} catch (Exception exception) {
			rtnMainSaveSts=false;
			log.info("Exception in wuh2hSaveForeignCurrencyAdjust:"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		
		return rtnMainSaveSts;

	}
	
	public void wuh2hSaveSendTxn(){
		String message = null;
		message = wuh2hSaveAll();
		
		if(message !=null){
			setErrorMessage(message);
			RequestContext.getCurrentInstance().execute("error.show();");
		}else{
			try {
				FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						"../wuh2h/wuh2hsendmoneysuccess.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Save all denomination
	public void  wuh2hSaveAll() {

		String validateMessage = null;
		String procedureMessage = null;
		String returnmessage = null;
		try {
			if (getBooRendercashdenomination()) {
				if (lstData.size() != 0) {
					if (getCashAmount().compareTo(getDenomtotalCash()) == 0) {
						if (getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
							nextpaneltoRefundDenomination();
							setNextOrSaveButton(Constants.Save);
						} else {

							String recordExist = Constants.No;
							String wuSendMoneyStoreSts = null;
							if (getSendMtcno() != null) {
								List<RemittanceApplication> lstRemittanceApplication = iwuh2hService
										.getRemittanceAppBasedonMtcNo(getSendMtcno());
								if (lstRemittanceApplication != null
										&& lstRemittanceApplication.size() > 0) {
									recordExist = Constants.Yes;
									RemittanceApplication remittanceApplication = lstRemittanceApplication
											.get(0);
									wuSendMoneyStoreSts = remittanceApplication
											.getWuSendMoneyStoreSts();
								}
							}
							boolean rtnMainSaveSts = false;
							if (recordExist.equalsIgnoreCase(Constants.No)) {
								rtnMainSaveSts = wuh2hSaveRemittance();
								System.out
										.println("********* APPLICATION CREATED");
							} else {
								rtnMainSaveSts = true;
							}
							if (rtnMainSaveSts) {
								if (getTxnType() == 1) {

									if (wuSendMoneyStoreSts == null) {
										validateMessage = wuH2HSendMoneyStore();

										if (validateMessage != null) {
											returnmessage = validateMessage;
											setErrorMessage(returnmessage);
											RequestContext.getCurrentInstance().execute("error.show();");
										} else {
											iwuh2hService
													.updateWuSendMoneyStoreSts(
															getSendMtcno(),
															getSenderNewPointsEarned());
											returnmessage = null;
										}
									}

								}
								if (getTxnType() == 3) {
									validateMessage = wuH2HSendMoneyModify();
									if (validateMessage != null) {
										returnmessage = validateMessage;
										setErrorMessage(returnmessage);
										RequestContext.getCurrentInstance().execute("error.show();");
									}
								}
								procedureMessage = wuh2hSendFinalTransaction();
								System.out
										.println("********* WUH2H STORE FINISHED");
								if (procedureMessage != null) {
									returnmessage = procedureMessage;
									setErrorMessage(returnmessage);
									RequestContext.getCurrentInstance().execute("error.show();");
								}
								System.out
										.println("********* FINAL TXN FINISHED");
								FacesContext
										.getCurrentInstance()
										.getExternalContext()
										.redirect(
												"../wuh2h/wuh2hsendmoneysuccess.xhtml");
							} else {
								returnmessage = null;
							}

						}
					} else {
						returnmessage = "Total Cash Received should be equal to Total Cash";
						setErrorMessage(returnmessage);
						RequestContext.getCurrentInstance().execute("error.show();");
						
					}
				} else {
					returnmessage = "Denomination Details Not Available";
					setErrorMessage(returnmessage);
					RequestContext.getCurrentInstance().execute("error.show();");
					
				}
			} else if (getBoorefundPaymentDetails()) {
				if (lstRefundData.size() != 0) {
					if (getCollectedRefundAmount().compareTo(getPayRefund()) == 0) {

						boolean rtnMainSaveSts = wuh2hSaveRemittance();
						System.out.println("**********APPLICATION CREATED");
						if (rtnMainSaveSts) {
							if (getTxnType() == 1) {
								validateMessage = wuH2HSendMoneyStore();

								if (validateMessage != null) {
									returnmessage = validateMessage;
									setErrorMessage(returnmessage);
									RequestContext.getCurrentInstance().execute("error.show();");
								}
							}
							if (getTxnType() == 3) {
								validateMessage = wuH2HSendMoneyModify();
								if (validateMessage != null) {
									returnmessage = validateMessage;
									setErrorMessage(returnmessage);
									RequestContext.getCurrentInstance().execute("error.show();");
								}
							}
							System.out
									.println("********* WUH2H STORE FINISHED");
							procedureMessage = wuh2hSendFinalTransaction();
							if (procedureMessage != null) {
								returnmessage = procedureMessage;
								setErrorMessage(returnmessage);
								RequestContext.getCurrentInstance().execute("error.show();");
							}
							System.out.println("*********FINAL TXN  FINISHED");
							FacesContext
									.getCurrentInstance()
									.getExternalContext()
									.redirect(
											"../wuh2h/wuh2hsendmoneysuccess.xhtml");
						} else {
							returnmessage = null;
						}

					} else {
						returnmessage = "Refund Amount and Total Refund Amount Not Matching";
						setErrorMessage(returnmessage);
						RequestContext.getCurrentInstance().execute("error.show();");
					}
				} else {
					returnmessage = "Denomination Details Not Available";
					setErrorMessage(returnmessage);
					RequestContext.getCurrentInstance().execute("error.show();");
				}
			}else if(getBooRendercashdenomination()==false){			
				System.out.println("Without Cash payment txn");
				setPayNetPaidAmount(getCalNetAmountPaid());
				setPayPaidAmount(GetRound.roundBigDecimal(getToalUsedAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
				setPayNetPaidAmount(getPayPaidAmount());
				setPayRefund(getPayPaidAmount().subtract(getPayNetPaidAmount()));
				
				String recordExist = Constants.No;
				String wuSendMoneyStoreSts = null;
				if (getSendMtcno() != null) {
					List<RemittanceApplication> lstRemittanceApplication = iwuh2hService
							.getRemittanceAppBasedonMtcNo(getSendMtcno());
					if (lstRemittanceApplication != null
							&& lstRemittanceApplication.size() > 0) {
						recordExist = Constants.Yes;
						RemittanceApplication remittanceApplication = lstRemittanceApplication
								.get(0);
						wuSendMoneyStoreSts = remittanceApplication
								.getWuSendMoneyStoreSts();
					}
				}
				boolean rtnMainSaveSts = false;
				if (recordExist.equalsIgnoreCase(Constants.No)) {
					rtnMainSaveSts = wuh2hSaveRemittance();
					System.out
							.println("********* APPLICATION CREATED");
				} else {
					rtnMainSaveSts = true;
				}
				if (rtnMainSaveSts) {
					if (getTxnType() == 1) {

						if (wuSendMoneyStoreSts == null) {
							validateMessage = wuH2HSendMoneyStore();

							if (validateMessage != null) {
								returnmessage = validateMessage;
								setErrorMessage(returnmessage);
								RequestContext.getCurrentInstance().execute("error.show();");
							} else {
								iwuh2hService
										.updateWuSendMoneyStoreSts(
												getSendMtcno(),
												getSenderNewPointsEarned());
								returnmessage = null;
							}
						}

					}
					if (getTxnType() == 3) {
						validateMessage = wuH2HSendMoneyModify();
						if (validateMessage != null) {
							returnmessage = validateMessage;
						}
					}
					procedureMessage = wuh2hSendFinalTransaction();
					System.out
							.println("********* WUH2H STORE FINISHED");
					if (procedureMessage != null) {
						returnmessage = procedureMessage;
						setErrorMessage(returnmessage);
						RequestContext.getCurrentInstance().execute("error.show();");
					}
					System.out
							.println("********* FINAL TXN FINISHED");
					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.redirect(
									"../wuh2h/wuh2hsendmoneysuccess.xhtml");
				} else {
					returnmessage = "Details  not saved";
					setErrorMessage(returnmessage);
					RequestContext.getCurrentInstance().execute("error.show();");
				}

			
			}else {
				if (getPayRefund().compareTo(BigDecimal.ZERO) > 0) {
					nextpaneltoRefundDenomination();
					setNextOrSaveButton(Constants.Save);
				} else {

					boolean rtnMainSaveSts = wuh2hSaveRemittance();
					System.out.println("*********APPLICATION CREATED");
					if (rtnMainSaveSts) {
						if (getTxnType() == 1) {
							validateMessage = wuH2HSendMoneyStore();

							if (validateMessage != null) {
								returnmessage = validateMessage;
								setErrorMessage(returnmessage);
								RequestContext.getCurrentInstance().execute("error.show();");
							}
						}
						if (getTxnType() == 3) {
							validateMessage = wuH2HSendMoneyModify();
							if (validateMessage != null) {
								returnmessage = validateMessage;
							}
						}

						System.out.println("***********WUH2H STORE FINISHED");
						procedureMessage = wuh2hSendFinalTransaction();
						if (procedureMessage != null) {
							returnmessage = procedureMessage;
							setErrorMessage(returnmessage);
							RequestContext.getCurrentInstance().execute("error.show();");
						}
						System.out.println("***********TRANSACTION FINISHED");
						FacesContext
								.getCurrentInstance()
								.getExternalContext()
								.redirect(
										"../wuh2h/wuh2hsendmoneysuccess.xhtml");
					} else {
						returnmessage = "Transaction Not finished";
						setErrorMessage(returnmessage);
						RequestContext.getCurrentInstance().execute("error.show();");
					}

				}
				
				
				
				
			}
		} catch (NullPointerException ne) {
			log.info("Exception in wuh2hSaveAll:" + ne.getMessage());
			returnmessage = "Exception in wuh2hSaveAll:Null Pointer Exception";
			setErrorMessage(returnmessage);
			RequestContext.getCurrentInstance().execute("error.show();");
		} catch (Exception exception) {
			log.info("Exception in wuh2hSaveAll:" + exception.getMessage());
			returnmessage = exception.getMessage();
			setErrorMessage(returnmessage);
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		//return returnmessage;
	}
	// calling procedure to move appl to remit
	public boolean wuh2hSaveCollectbyProcedure(TempCollection tempCollection,
			BigDecimal collectionId) throws AMGException {

		boolean rtnSaveSts = false;

		HashMap<String, Object> returnResult = new HashMap<String, Object>();
		String errormsg = null;
		BigDecimal cFinYear = null;
		BigDecimal collectionNumber = null;

		log.info("Parameter 1 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCountryId(): "
				+ sessionStateManage.getCountryId());
		log.info("Parameter 2 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCompanyId(): "
				+ sessionStateManage.getCompanyId());
		log.info("Parameter 3 saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo(): "
				+ getCustomerNo());
		log.info("Parameter 4 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName(): "
				+ sessionStateManage.getUserName());
		log.info("Parameter 5 saveRemittance EX_INSERT_REMITTANCE_TRANX getColremittanceNo(): "
				+ getColremittanceNo());
		log.info("Parameter 6 saveRemittance EX_INSERT_REMITTANCE_TRANX tempCollection.getDocumentCode(): "
				+ tempCollection.getDocumentCode());
		log.info("Parameter 7 saveRemittance EX_INSERT_REMITTANCE_TRANX collectionId: "
				+ collectionId);

		try {

			returnResult.put("CountryId", sessionStateManage.getCountryId());
			returnResult.put("CompanyId", sessionStateManage.getCompanyId());
			returnResult.put("CustomerId", getCustomerNo());
			returnResult.put("UserName", sessionStateManage.getUserName());
			returnResult.put("NoofTrnx", BigDecimal.ONE);
			returnResult.put("TempDocCode", tempCollection.getDocumentCode());
			returnResult.put("TempCollectionId", collectionId);

			HashMap<String, Object> outRecord = iPersonalRemittanceService
					.saveAllRemittanceTransaction(returnResult);

			collectionNumber = (BigDecimal) outRecord.get("CollectionDocNo");
			cFinYear = (BigDecimal) outRecord.get("CollectionFinYear");
			errormsg = (String) outRecord.get("ErrorMsg");

			log.info("=====================CALLED SAVE ALL");

			log.info("Out1 -----> cFinYear : " + cFinYear);
			log.info("Out2------>collectionNumber : " + collectionNumber);
			log.info("Out3------->errormsg : " + errormsg);

			if (cFinYear == null || collectionNumber == null) {
				String status = null;
				// updating Status "null" in different Tables

				log.info("Exception occured while executing procedure");
				rtnSaveSts = false;
				throw new SQLException(
						"Exception occured while executing procedure");

			}

			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo():"
					+ getCustomerNo());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName():"
					+ sessionStateManage.getUserName());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX  getColremittanceNo(): "
					+ getColremittanceNo());
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX collectionNumber:"
					+ collectionNumber);
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX cFinYear:"
					+ cFinYear);
			log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX errormsg:"
					+ errormsg);

			if (errormsg == null || errormsg.equalsIgnoreCase("")) {

				lstselectedrecord.clear();
				setMainPanelRender(false);

				remittanceNo = new BigDecimal(0);
				fcsaleNo = new BigDecimal(0);
				cashAmount = new BigDecimal(0);
				coldatatablevalues.clear();

				// for report generation
				//setDocumentNo(collectionNumber.toPlainString());

				// Icash product Id
				BigDecimal icashBankID = null;

				List<BankMaster> lstICashBankID = generalService
						.getAllBankCodeFromBankMaster("ICASH");

				if (lstICashBankID != null && !lstICashBankID.isEmpty()) {
					BankMaster icashBankRecord = lstICashBankID.get(0);
					icashBankID = icashBankRecord.getBankId();
				}

				// checking ICash and Routing Bank Id
				String checkICASH = iPersonalRemittanceService
						.checkICASHProduct(
								tempCollection.getApplicationCountryId(),
								sessionStateManage.getCompanyId(),
								tempCollection.getDocumentCode(), cFinYear,
								collectionNumber, icashBankID);

				if (checkICASH != null) {
					String status = null;
					// updating Status "null" in different Tables

					log.info(" Records Not Saved : " + errormsg);
					setProcedureError("INSERT_SERVICE_PIN_JAVA" + " : "
							+ checkICASH);
					RequestContext.getCurrentInstance().execute(
							"procedureErr.show();");
					rtnSaveSts = false;
					return rtnSaveSts;
				} else {
					// calling EX_INSERT_EMOS_TRANSFER_LIVE procedure changes -
					// 17/12/2015
					if (tempCollection != null) {
						iPersonalRemittanceService.insertEMOSLIVETransfer(
								tempCollection.getApplicationCountryId(),
								sessionStateManage.getCompanyId(),
								tempCollection.getDocumentCode(), cFinYear,
								collectionNumber);
					}

				}
				rtnSaveSts = true;
			} else {
				String status = null;
				// updating Status "null" in different Tables

				log.info(" Records Not Saved : " + errormsg);
				setErrorMessage(errormsg);
				RequestContext.getCurrentInstance().execute("error.show();");
				rtnSaveSts = false;
				return rtnSaveSts;
			}
		} catch (SQLException e) {
			rtnSaveSts = false;
			throw new AMGException(e.getMessage());
		}
		return rtnSaveSts;
	}

	// saving data to Remittance App Benificary Table
	public RemittanceAppBenificiary wuh2hSaveRemittanceAppBenificary(
			RemittanceApplication remittanceApplication) {
		RemittanceAppBenificiary remittanceAppBenificary = new RemittanceAppBenificiary();
		WesternUnionH2HBeneficaryDataTable beneDataTable =null;
		if(selectedWUBeneList!=null && selectedWUBeneList.size()>0)
		{
			beneDataTable = selectedWUBeneList.get(0);
		}
		
				
		try {
			// to get the document details - Hard Code
			List<Document> lstDocument = generalService
					.getDocument(
							new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),
							new BigDecimal(
									sessionStateManage.isExists("languageId") ? sessionStateManage
											.getSessionValue("languageId")
											: "1"));
			if (lstDocument.size() > 0) {
				for (Document lstdoc : lstDocument) {
					setDocumentId(lstdoc.getDocumentID());
					// setDocumentdesc(lstdoc.getDocumentDesc());
					// setDocumentcode(lstdoc.getDocumentCode());
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
			List<CompanyMasterDesc> lstcompanymaster = iCompanyMasterservice
					.viewById(sessionStateManage.getCompanyId());
			if (lstcompanymaster.size() != 0) {
				CompanyMasterDesc companycode = lstcompanymaster.get(0);
				remittanceAppBenificary.setCompanyCode(companycode
						.getFsCompanyMaster().getCompanyCode());
			}

			// User Financial Year for Transaction
			UserFinancialYear userfinancialyear = new UserFinancialYear();
			userfinancialyear.setFinancialYearID(generalService
					.getDealYear(new Date()).get(0).getFinancialYearID());
			remittanceAppBenificary.setExUserFinancialYear(userfinancialyear);

			// RemittanceApplication Id
			remittanceAppBenificary
					.setExRemittanceAppfromBenfi(remittanceApplication);
			remittanceAppBenificary.setDocumentCode(getSendDocumentCode());
			remittanceAppBenificary.setDocumentNo(remittanceApplication
					.getDocumentNo());
			remittanceAppBenificary.setBeneficiaryId(beneDataTable
					.getBeneficaryMasterSeqId());

			if (getReceiverDebitAccountNo() != null) {
				remittanceAppBenificary
						.setBeneficiaryAccountNo(getReceiverDebitAccountNo());
			}

			
			 * if (getSwiftId1() != null) { //
			 * remittanceAppBenificary.setBeneficiarySwiftBank1
			 * (getSwiftId1().toString());
			 * remittanceAppBenificary.setBeneficiarySwiftBank1(null);
			 * remittanceAppBenificary
			 * .setBeneficiarySwiftBank1Id(getSwiftId1());
			 * 
			 * } if (getSwiftId2() != null) { //
			 * remittanceAppBenificary.setBeneficiarySwiftBank2
			 * (getSwiftId2().toString());
			 * remittanceAppBenificary.setBeneficiarySwiftBank2(null);
			 * remittanceAppBenificary
			 * .setBeneficiarySwiftBank2Id(getSwiftId2()); }
			 * 
			 * if (getBeneSwiftBankAddr1() != null) {
			 * remittanceAppBenificary.setBeneficiarySwiftAddr1
			 * (getBeneSwiftBankAddr1()); }
			 * 
			 * if (getBeneSwiftBankAddr2() != null) {
			 * remittanceAppBenificary.setBeneficiarySwiftAddr2
			 * (getBeneSwiftBankAddr2()); }
			 
			remittanceAppBenificary.setCreatedBy(sessionStateManage
					.getUserName());
			remittanceAppBenificary.setCreatedDate(new Date());
			remittanceAppBenificary.setIsactive(Constants.Yes);

			if (beneDataTable.getBenificaryName() != null) {
				remittanceAppBenificary.setBeneficiaryName(beneDataTable
						.getBenificaryName());
			}

			if (beneDataTable.getFirstName() != null) {
				remittanceAppBenificary.setBeneficiaryFirstName(beneDataTable
						.getFirstName());
			}

			if (beneDataTable.getSecondName() != null) {
				remittanceAppBenificary.setBeneficiarySecondName(beneDataTable
						.getSecondName());
			}

			if (beneDataTable.getThirdName() != null) {
				remittanceAppBenificary.setBeneficiaryThirdName(beneDataTable
						.getThirdName());
			}

			if (beneDataTable.getFourthName() != null) {
				remittanceAppBenificary.setBeneficiaryFourthName(beneDataTable
						.getFourthName());
			}

			if (beneDataTable.getFiftheName() != null) {
				remittanceAppBenificary.setBeneficiaryFifthName(beneDataTable
						.getFiftheName());
			}

			if (beneDataTable.getSwiftBic() != null) {
				remittanceAppBenificary.setBeneficiaryBankSwift(beneDataTable
						.getSwiftBic());
			}

			remittanceAppBenificary.setBeneficiaryBankCountryId(beneDataTable
					.getCountryId());
			remittanceAppBenificary.setBeneficiaryBank(beneDataTable
					.getBankName());
			remittanceAppBenificary.setBeneficiaryBranch(beneDataTable
					.getBankBranchName());
			remittanceAppBenificary.setBeneficiaryBankId(beneDataTable
					.getBankId());
			remittanceAppBenificary.setBeneficiaryBankBranchId(beneDataTable
					.getBranchId());
			// remittanceAppBenificary.setBeneficiaryBranchStateId(beneDataTable.getb);
			// remittanceAppBenificary.setBeneficiaryBranchDistrictId(getBeneDistrictId());
			// remittanceAppBenificary.setBeneficiaryBranchCityId(beneDataTable.getc);
			remittanceAppBenificary.setBeneficiaryAccountSeqId(beneDataTable
					.getBeneficiaryAccountSeqId());
			remittanceAppBenificary
					.setBeneficiaryRelationShipSeqId(beneDataTable
							.getBeneficiaryRelationShipSeqId());
			remittanceAppBenificary.setBeneficiaryTelephoneNumber(beneDataTable
					.getTelphoneNum());

		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}
		return remittanceAppBenificary;
	}

	public void booAmlCheckProcess() {

		setHaveAmlCheck(false);
		setFromAMLCheck(true);
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

		List<BenificiaryListView> isCoustomerExist = beneficaryCreation
				.getBeneficaryList(getCustomerNo());
		if (isCoustomerExist.size() > 0) {
			beneCountr = isCoustomerExist.get(0).getBenificaryCountry();
			BigDecimal masterSeqId = isCoustomerExist.get(0)
					.getBeneficaryMasterSeqId();
			try {
				HashMap<String, String> list = iPersonalRemittanceService
						.isAmlTranxAmountCheckForRemittance(
								sessionStateManage.getCountryId(), beneCountr,
								getCustomerNo(), masterSeqId, getCashAmount());
				if (list != null && !list.isEmpty()) {
					if (list.get("MESSAGE1").equalsIgnoreCase("")
							&& list.get("MESSAGE2").equalsIgnoreCase("")
							&& list.get("MESSAGE3").equalsIgnoreCase("")
							&& list.get("MESSAGE4").equalsIgnoreCase("")) {
						setHaveAmlCheck(false);
						setAmlboomsg(false);
						setAmlboo(false);
					} else {

						setAmlboomsg(true);
						setAmlboo(true);

						if (list.get("MESSAGE1") != null
								&& list.get("MESSAGE1") != "") {
							setAmlMessageOne(list.get("MESSAGE1"));
							setMessageOne(list.get("MESSAGE1"));
						}
						if (list.get("MESSAGE2") != null
								&& list.get("MESSAGE2") != "") {
							setAmlMessageTwo(list.get("MESSAGE2"));
							setMessageTwo(list.get("MESSAGE2"));
						}
						if (list.get("MESSAGE3") != null
								&& list.get("MESSAGE3") != "") {
							setMessageThree(list.get("MESSAGE3"));
							setAmlMessageThree(list.get("MESSAGE3"));
						}
						if (list.get("MESSAGE4") != null
								&& list.get("MESSAGE4") != "") {
							setMessageFour(list.get("MESSAGE4"));
							setAmlMessageFour(list.get("MESSAGE4"));
						}

						setHaveAmlCheck(true);
						almcheckList.clear();
						if (list.get("MESSAGE3") != null
								&& list.get("MESSAGE3") != "") {
							CustomerAlmTrasactionCheckProcedure almcheckSinglerow = new CustomerAlmTrasactionCheckProcedure();
							// almcheckSinglerow.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(),getDatabenificarycountry()));
							almcheckSinglerow
									.setCountryName(getReceiverCountryName());

							setRangeFromOne(list.get("RANGE1FROM") == null ? ""
									: list.get("RANGE1FROM"));
							setRangeToOne(list.get("RANGE1TO") == null ? ""
									: list.get("RANGE1TO"));
							almcheckSinglerow.setSlab1(Integer.parseInt(list
									.get("RANGE1COUNT") == null ? "0" : list
									.get("RANGE1COUNT")));
							setRangeFromTwo(list.get("RANGE2FROM") == null ? ""
									: list.get("RANGE2FROM"));
							setRangeToTwo(list.get("RANGE2TO") == null ? ""
									: list.get("RANGE2TO"));
							almcheckSinglerow.setSlab2(Integer.parseInt(list
									.get("RANGE2COUNT") == null ? "0" : list
									.get("RANGE2COUNT")));
							setRangeFromThree(list.get("RANGE3FROM") == null ? ""
									: list.get("RANGE3FROM"));
							setRangeToThree(list.get("RANGE3TO") == null ? ""
									: list.get("RANGE3TO"));
							almcheckSinglerow.setSlab3(Integer.parseInt(list
									.get("RANGE3COUNT") == null ? "0" : list
									.get("RANGE3COUNT")));
							setRangeFromFour(list.get("RANGE4FROM") == null ? ""
									: list.get("RANGE4FROM"));
							setRangeToFour(list.get("RANGE4TO") == null ? ""
									: list.get("RANGE4TO"));
							almcheckSinglerow.setSlab4(Integer.parseInt(list
									.get("RANGE4COUNT") == null ? "0" : list
									.get("RANGE4COUNT")));
							setRangeFromFive(list.get("RANGE5FROM") == null ? ""
									: list.get("RANGE5FROM"));
							setRangeToFive(list.get("RANGE5TO") == null ? ""
									: list.get("RANGE5TO"));
							almcheckSinglerow.setSlab5(Integer.parseInt(list
									.get("RANGE5COUNT") == null ? "0" : list
									.get("RANGE5COUNT")));
							setRangeFromSix(list.get("RANGE6FROM") == null ? ""
									: list.get("RANGE6FROM"));
							setRangeToSix(list.get("RANGE6TO") == null ? ""
									: list.get("RANGE6TO"));
							almcheckSinglerow.setSlab6(Integer.parseInt(list
									.get("RANGE6COUNT") == null ? "0" : list
									.get("RANGE6COUNT")));

							almcheckList.add(almcheckSinglerow);

							if (list.get("RANGE5FROM") != null
									&& list.get("RANGE5TO") != null
									&& list.get("RANGE5COUNT") != null) {
								setRenderRangeFive(true);
							} else {
								setRenderRangeFive(false);
							}

							if (list.get("RANGE6FROM") != null
									&& list.get("RANGE6TO") != null
									&& list.get("RANGE6COUNT") != null) {
								setRenderRangeSix(true);
							} else {
								setRenderRangeSix(false);
							}
							setAlmcheckList(almcheckList);
						}

						if (list.get("AUTHTYPE1") != null
								&& list.get("AUTHTYPE1") != "") {
							amlAuthuTYpe.put(
									(list.get("AUTHTYPE1").toString()),
									getAmlMessageOne());
							amlAuthuTYpeLog.put("AUTHTYPE1",
									list.get("AUTHTYPE1").toString());
						}
						if (list.get("AUTHTYPE2") != null
								&& list.get("AUTHTYPE2") != "") {
							amlAuthuTYpe.put(
									(list.get("AUTHTYPE2").toString()),
									getAmlMessageTwo());
							amlAuthuTYpeLog.put("AUTHTYPE2",
									list.get("AUTHTYPE2").toString());
						}

						if (list.get("AUTHTYPE3") != null
								&& list.get("AUTHTYPE3") != "") {
							amlAuthuTYpe.put(
									(list.get("AUTHTYPE3").toString()),
									getAmlMessageThree());
							amlAuthuTYpeLog.put("AUTHTYPE3",
									list.get("AUTHTYPE3").toString());
						}

						if (list.get("AUTHTYPE4") != null
								&& list.get("AUTHTYPE4") != "") {
							amlAuthuTYpe.put(
									(list.get("AUTHTYPE4").toString()),
									getAmlMessageFour());
							amlAuthuTYpeLog.put("AUTHTYPE4",
									list.get("AUTHTYPE4").toString());
						}

					}
				} else {
					setHaveAmlCheck(false);
				}
			} catch (AMGException e) {
				setHaveAmlCheck(false);
				log.error("Exception In AML Check Procedure" + e.getMessage());
			}

		}

		slb1total1 = 0.0;
		slb1total2 = 0.0;
		slb1total3 = 0.0;
		slb1total4 = 0.0;
		slb1total5 = 0.0;
		slb1total6 = 0.0;
	}

	// saving data to Remittance Application AML
	public List<RemitApplAml> wuh2hSaveRemittanceAppAML(
			RemittanceApplication remittanceApplication) {

		List<RemitApplAml> remitApplAmlList = new ArrayList<RemitApplAml>();

		try {

			Iterator<Map.Entry<String, String>> entries = amlAuthuTYpe
					.entrySet().iterator();

			if (!amlAuthuTYpe.isEmpty()) {
				while (entries.hasNext()) {

					Map.Entry<String, String> entry = entries.next();

					RemitApplAml remitApplAml = new RemitApplAml();
					// company Id
					CompanyMaster companymaster = new CompanyMaster();
					companymaster.setCompanyId(sessionStateManage
							.getCompanyId());
					remitApplAml.setFsCompanyMaster(companymaster);
					// RemittanceApplication Id
					remitApplAml
							.setExRemittanceAppfromAml(remittanceApplication);
					// benificary Country
					CountryMaster bencountrymaster = new CountryMaster();
					bencountrymaster.setCountryId(sessionStateManage
							.getCountryId());
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
					// remitApplAml.setAuthorizedBy(getAmlAuthorizedBy());
					remitApplAml.setAuthType(entry.getKey());
					remitApplAml.setBlackListReason(entry.getValue());
					// remitApplAml.setBlackListRemarks(getAmlRemarks());
					remitApplAmlList.add(remitApplAml);

				}
			}
		} catch (Exception e) {
			setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("sqlexception.show();");
		}

		return remitApplAmlList;
	}


	private List<AddDynamicLebel> listDynamicLebel = new ArrayList<AddDynamicLebel>();
	private List<AddAdditionalBankData> listAdditionalBankDataTable = new ArrayList<AddAdditionalBankData>();

	public void dynamicLevel() throws AMGException {
		listDynamicLebel.clear();
		setExceptionMessage("");
		List<AdditionalDataDisplayView> serviceAppRuleList = iPersonalRemittanceService
				.getAdditionalDataFromServiceApplicability(
						sessionStateManage.getCountryId(),
						getSendRoutingBankId(), getReceiverISOCountryId(),
						new BigDecimal(32), new BigDecimal(121));
		if (serviceAppRuleList.size() > 0) {
			for (AdditionalDataDisplayView serviceRule : serviceAppRuleList) {
				AddDynamicLebel addDynamic = new AddDynamicLebel();
				addDynamic.setLebelId(serviceRule
						.getServiceApplicabilityRuleId());
				addDynamic.setFieldLength(serviceRule.getFieldLength());
				if (serviceRule.getFieldDescription() != null) {
					addDynamic.setLebelDesc(serviceRule.getFieldDescription());
				}

				addDynamic.setFlexiField(serviceRule.getFlexField());
				addDynamic.setValidation(serviceRule.getValidationsReq());
				addDynamic.setNavicable(serviceRule.getIsRendered());
				addDynamic.setMinLenght(serviceRule.getMinLength());
				addDynamic.setMaxLenght(serviceRule.getMaxLength());

				if (serviceRule.getIsRequired() != null
						&& serviceRule.getIsRequired().equalsIgnoreCase(
								Constants.Yes)) {
					addDynamic.setMandatory("*");
					addDynamic.setRequired(true);
				}
				listDynamicLebel.add(addDynamic);
			}
			setAdditionalCheck(true);

		} else {
			setAdditionalCheck(false);
		}
	}

	public void matchData() {
		setExceptionMessage(null);
		listAdditionalBankDataTable.clear();
		try {
			for (AddDynamicLebel dyamicLabel : listDynamicLebel) {
				AddAdditionalBankData adddata = new AddAdditionalBankData();
				if (dyamicLabel.getValidation() != null
						&& dyamicLabel.getValidation().equalsIgnoreCase(
								Constants.Yes)) {
					List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService
							.getDynamicLevelMatch(getSendRoutingBankId(),
									dyamicLabel.getFlexiField());
					if (listAdditinalBankfield.size() > 0) {
						for (AdditionalBankRuleMap listAdd : listAdditinalBankfield) {

							List<AdditionalBankDetailsView> listAdditionaView = iPersonalRemittanceService
									.getAmiecDetails(getReceiverISOCountryId(),
											getWuBeneBankId(), new BigDecimal(
													32), new BigDecimal(121),
											getOriginISOCountryId(), listAdd
													.getFlexField());

							if (listAdditionaView.size() > 0) {

								// setting dynamic functionality
								adddata.setMandatory(dyamicLabel.getMandatory());
								if (dyamicLabel.getMinLenght() != null) {
									adddata.setMinLenght(dyamicLabel
											.getMinLenght().intValue());
								} else {
									adddata.setMinLenght(0);
								}
								if (dyamicLabel.getMaxLenght() != null) {
									adddata.setMaxLenght(dyamicLabel
											.getMaxLenght());
								} else {
									adddata.setMaxLenght(new BigDecimal(50));
								}
								adddata.setFieldLength(dyamicLabel
										.getFieldLength());
								adddata.setRequired(dyamicLabel.getRequired());

								adddata.setAdditionalBankRuleFiledId(listAdd
										.getAdditionalBankRuleId());
								adddata.setFlexiField(listAdd.getFlexField());
								if (listAdd.getFieldName() != null) {
									adddata.setAdditionalDesc(listAdd
											.getFieldName());
								} else {
									setExceptionMessage(((getExceptionMessage() == null ? ""
											: getExceptionMessage())
											.equalsIgnoreCase("") ? "" : ",")
											+ dyamicLabel.getFlexiField());
								}
								adddata.setRenderInputText(false);
								adddata.setRenderSelect(true);
								adddata.setRenderOneSelect(false);
								adddata.setListadditionAmiecData(listAdditionaView);

							}
						}

						if (getExceptionMessage() != null
								&& !getExceptionMessage().equalsIgnoreCase("")) {
							setAdditionalCheck(false);
							setExceptionMessage(getExceptionMessage());
							RequestContext.getCurrentInstance().execute(
									"dataexception.show();");
						} else {
							setAdditionalCheck(true);
							setExceptionMessage(null);
						}
					}
				} else {
					
					 * if (dyamicLabel.getValidation()!=null &&
					 * dyamicLabel.getValidation
					 * ().equalsIgnoreCase(Constants.No)) {
					 
					adddata.setMandatory(dyamicLabel.getMandatory());
					if (dyamicLabel.getMinLenght() != null) {
						adddata.setMinLenght(dyamicLabel.getMinLenght()
								.intValue());
					} else {
						adddata.setMinLenght(0);
					}
					if (dyamicLabel.getMaxLenght() != null) {
						adddata.setMaxLenght(dyamicLabel.getMaxLenght());
					} else {
						adddata.setMaxLenght(new BigDecimal(50));
					}

					adddata.setFieldLength(dyamicLabel.getFieldLength());
					adddata.setRequired(dyamicLabel.getRequired());
					adddata.setRenderInputText(true);
					adddata.setRenderSelect(false);
					adddata.setRenderOneSelect(false);
					adddata.setFlexiField(dyamicLabel.getFlexiField());
					if (dyamicLabel.getLebelDesc() != null) {
						adddata.setAdditionalDesc(dyamicLabel.getLebelDesc());
					} else {
						List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService
								.getDynamicLevelMatch(
										getBeneficiaryCountryId(),
										dyamicLabel.getFlexiField());
						if (listAdditinalBankfield.size() > 0) {
							if (listAdditinalBankfield.get(0).getFieldName() != null) {
								adddata.setAdditionalDesc(listAdditinalBankfield
										.get(0).getFieldName());
							} else {
								setExceptionMessage(((getExceptionMessage() == null ? ""
										: getExceptionMessage())
										.equalsIgnoreCase("") ? "" : ",")
										+ dyamicLabel.getFlexiField());
							}
						} else {
							setExceptionMessage(((getExceptionMessage() == null ? ""
									: getExceptionMessage())
									.equalsIgnoreCase("") ? "" : ",")
									+ dyamicLabel.getFlexiField());
						}
					}

					if (getOriginISOCountryId() != null
							&& dyamicLabel.getFlexiField() != null) {
						List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService
								.getDynamicLevelMatch(getOriginISOCountryId(),
										dyamicLabel.getFlexiField());
						if (listAdditinalBankfield.size() > 0) {
							if (listAdditinalBankfield.get(0).getFieldName() != null) {
								adddata.setAdditionalDesc(listAdditinalBankfield
										.get(0).getFieldName());
							} else {
								setExceptionMessage(((getExceptionMessage() == null ? ""
										: getExceptionMessage())
										.equalsIgnoreCase("") ? "" : ",")
										+ dyamicLabel.getFlexiField());
							}
						} else {
							setExceptionMessage(((getExceptionMessage() == null ? ""
									: getExceptionMessage())
									.equalsIgnoreCase("") ? "" : ",")
									+ dyamicLabel.getFlexiField());
						}
					} else {
						setExceptionMessage(((getExceptionMessage() == null ? ""
								: getExceptionMessage()).equalsIgnoreCase("") ? ""
								: ",")
								+ dyamicLabel.getFlexiField());
					}
				}
				listAdditionalBankDataTable.add(adddata);
			}

			if (getExceptionMessage() != null
					&& !getExceptionMessage().equalsIgnoreCase("")) {
				setAdditionalCheck(false);
				setExceptionMessage(getExceptionMessage());
				RequestContext.getCurrentInstance().execute(
						"dataexception.show();");
			} else {
				setAdditionalCheck(true);
				setExceptionMessage(null);
			}

		} catch (Exception e) {
			log.info(e);
		}
	}

	public List<AdditionalInstructionData> saveAdditionalInstnData(
			RemittanceApplication remittanceApplication) {
		List<AdditionalInstructionData> lstAddInstrData = new ArrayList<AdditionalInstructionData>();
		Document document = new Document();
		document.setDocumentID(generalService
				.getDocument(
						new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "1")).get(0)
				.getDocumentID());
		// company Id
		CompanyMaster companymaster = new CompanyMaster();
		companymaster.setCompanyId(sessionStateManage.getCompanyId());
		// Application Country
		CountryMaster countrymaster = new CountryMaster();
		countrymaster.setCountryId(sessionStateManage.getCountryId());

		for (AddAdditionalBankData dynamicList : listAdditionalBankDataTable) {

			AdditionalInstructionData additionalInsData = new AdditionalInstructionData();
			AdditionalBankRuleMap additionalBank = new AdditionalBankRuleMap();
			if (dynamicList.getAdditionalBankRuleFiledId() != null) {
				additionalBank.setAdditionalBankRuleId(dynamicList
						.getAdditionalBankRuleFiledId());
				additionalInsData.setAdditionalBankFieldsId(additionalBank);
			}

			// System.out.println("dynamicList.getFlexiField() :"+dynamicList.getFlexiField()+"\t dynamicList.getAmicCode() :"+dynamicList.getAmicCode());

			additionalInsData.setFlexField(dynamicList.getFlexiField());
			if (dynamicList.getAdditionalBankRuleFiledId() != null) {
				String amiecdec = dynamicList.getVariableName();
				String amicCode = null;
				String amicDesc = null;
				if (amiecdec != null) {

					String[] amiecdecValues = amiecdec.split("-");
					if (amiecdecValues.length > 0) {
						amicCode = amiecdecValues[0];

					}
					if (amiecdecValues.length > 1) {
						amicDesc = amiecdecValues[1];
					}
				}
				additionalInsData.setAmiecCode(amicCode);
				additionalInsData.setFlexFieldValue(amicDesc);
			} else {
				additionalInsData.setAmiecCode(Constants.AMIEC_CODE);
				additionalInsData.setFlexFieldValue(dynamicList
						.getVariableName());
			}

			additionalInsData.setExDocument(document);
			additionalInsData.setFsCountryMaster(countrymaster);
			additionalInsData.setFsCompanyMaster(companymaster);
			additionalInsData.setExRemittanceApplication(remittanceApplication);
			additionalInsData.setExUserFinancialYear(remittanceApplication
					.getExUserFinancialYearByDocumentFinanceYear());
			additionalInsData.setDocumentFinanceYear(remittanceApplication
					.getDocumentFinancialyear());

			additionalInsData.setCreatedBy(sessionStateManage.getUserName());
			additionalInsData.setCreatedDate(new Date());
			additionalInsData.setIsactive(Constants.Yes);
			additionalInsData.setDocumentNo(new BigDecimal(getDocumentNo()));

			// iPersonalRemittanceService.saveAdditionalInsData(additionalInsData);

			lstAddInstrData.add(additionalInsData);
		}

		return lstAddInstrData;
	}

	// SAVE DATA TO Remittance Application
		public boolean wuh2hSaveRemittanceApplication() {

			boolean rtnSts = false;
			try {
				HashMap<String, Object> mapWUH2HAllAppl = new HashMap<String, Object>();

				RemittanceApplication remittanceApplication = new RemittanceApplication();
				
				setDocumentNo(getDocumentSerialID(Constants.Yes));
				// Foriegn Currency id
				CurrencyMaster forcurrencymaster = new CurrencyMaster();
				forcurrencymaster.setCurrencyId(getReceiverISOCurrencyId());
				remittanceApplication
						.setExCurrencyMasterByForeignCurrencyId(forcurrencymaster);

				// Local Commission Currency Id - KWD
				CurrencyMaster commisioncurrencymaster = new CurrencyMaster();
				commisioncurrencymaster.setCurrencyId(getOriginISOCurrencyId());
				remittanceApplication
						.setExCurrencyMasterByLocalCommisionCurrencyId(commisioncurrencymaster);

				remittanceApplication.setDocumentNo(getSendDocumentNo());

				// local Transaction Currency Id
				CurrencyMaster tranxcurrencymaster = new CurrencyMaster();
				tranxcurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage
						.getCurrencyId()));
				remittanceApplication
						.setExCurrencyMasterByLocalTranxCurrencyId(tranxcurrencymaster);

				// local Charge Currency Id -KWD
				CurrencyMaster chargecurrencymaster = new CurrencyMaster();
				chargecurrencymaster.setCurrencyId(new BigDecimal(
						sessionStateManage.getCurrencyId()));
				remittanceApplication
						.setExCurrencyMasterByLocalChargeCurrencyId(chargecurrencymaster);

				// local Net Currency Id - kWD
				CurrencyMaster netcurrencymaster = new CurrencyMaster();
				netcurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage
						.getCurrencyId()));
				remittanceApplication
						.setExCurrencyMasterByLocalNetCurrencyId(netcurrencymaster);

				// local Delivery Currency Id - kWD
				CurrencyMaster devcurrencymaster = new CurrencyMaster();
				devcurrencymaster.setCurrencyId(new BigDecimal(sessionStateManage
						.getCurrencyId()));
				remittanceApplication
						.setExCurrencyMasterByLocalDeliveryCurrencyId(null);

				// remittanceApplication.setSpotRateInd(getSpotRate());
				// remittanceApplication.setLoyaltyPointInd(getAvailLoyaltyPoints());

				List<Document> lstDocument = generalService
						.getDocument(
								new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION),
								new BigDecimal(
										sessionStateManage.isExists("languageId") ? sessionStateManage
												.getSessionValue("languageId")
												: "1"));
				if (lstDocument != null && lstDocument.size() != 0) {
					Document documentid = new Document();
					documentid.setDocumentID(lstDocument.get(0).getDocumentID());
					remittanceApplication.setExDocument(documentid);
				}
				remittanceApplication.setDocumentCode(new BigDecimal(
						Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION));

				// company Id
				CompanyMaster companymaster = new CompanyMaster();
				companymaster.setCompanyId(sessionStateManage.getCompanyId());
				remittanceApplication.setFsCompanyMaster(companymaster);

				// User Financial Year for Document
				UserFinancialYear docuserfinancialyear = new UserFinancialYear();
				docuserfinancialyear.setFinancialYearID(generalService
						.getDealYear(new Date()).get(0).getFinancialYearID());
				remittanceApplication
						.setExUserFinancialYearByDocumentFinanceYear(docuserfinancialyear);

				// Country Branch
				CountryBranch countryBranch = new CountryBranch();
				countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage
						.getBranchId()));
				remittanceApplication.setExCountryBranch(countryBranch);

				// Application Country
				CountryMaster appcountrymaster = new CountryMaster();
				appcountrymaster.setCountryId(sessionStateManage.getCountryId());
				remittanceApplication
						.setFsCountryMasterByApplicationCountryId(appcountrymaster);

				// bank country id
				CountryMaster bencountrymaster = new CountryMaster();
				bencountrymaster.setCountryId(getReceiverISOCountryId());
				remittanceApplication
						.setFsCountryMasterByBankCountryId(bencountrymaster);

				// Customer id
				Customer customerid = new Customer();
				customerid.setCustomerId(getCustomerNo());
				remittanceApplication.setFsCustomer(customerid);

				// Routing Bank
				BankMaster bankmaster = new BankMaster();
				bankmaster.setBankId(getWuBeneBankId());
				remittanceApplication.setExBankMaster(bankmaster);

				// Routing Bank Branch
				BankBranch bankbranch = new BankBranch();
				
				 * bankbranch.setBankBranchId(new BigDecimal(sessionStateManage
				 * .getBranchId()));
				 
				
				bankbranch.setBankBranchId(getWuBeneBranchId());
				remittanceApplication.setExBankBranch(bankbranch);

				// Delivery Mode from service
				DeliveryMode deliverymode = new DeliveryMode();
				deliverymode.setDeliveryModeId(getDeliverModeId());
				remittanceApplication.setExDeliveryMode(deliverymode);

				// RemittanceModeMaster to get Remittance
				// BigDecimal remitModeId=iwuh2hService.getRemitModeForCash();
				RemittanceModeMaster remittancemode = new RemittanceModeMaster();
				remittancemode.setRemittanceModeId(getRemitanceModeId());
				remittanceApplication.setExRemittanceMode(remittancemode);
				// remittanceApplication.setDocumentNo(getDocumentId());
				remittanceApplication.setDocumentDate(new Date());
				// remittanceApplication.setTransactionRefNo(transactionRefNo);
				remittanceApplication.setCustomerRef(getCustomerrefno());

				if (getReceiverDebitAccountNo() != null) {
					remittanceApplication
							.setDebitAccountNo(getReceiverDebitAccountNo());
				}

				remittanceApplication
						.setForeignTranxAmount(getSendDestPrincipleAmount());
				remittanceApplication
						.setLocalTranxAmount(getSendOriginPrincipleAmount());
				remittanceApplication.setExchangeRateApplied(getSendExchangeRate());
				remittanceApplication
						.setLocalNetTranxAmount(getSendGrossTotalAmount());
				remittanceApplication.setLocalCommisionAmount(getSendCommission());
				remittanceApplication
				.setLocalChargeAmount(getSendPlusChargeAmount());
				remittanceApplication
				.setLocalChargeAmount(getWuMessageCharge());
				remittanceApplication.setLocalDeliveryAmount(getSendCommission());
				remittanceApplication.setLocalChargeAmount(getWuMessageCharge());
				remittanceApplication.setWuPromoDiscount(getSendPromoDiscountAmount());

				
				 * remittanceApplication.setForeignTranxAmount(new BigDecimal(
				 * "287")); remittanceApplication.setLocalTranxAmount(new
				 * BigDecimal( "1"));
				 * remittanceApplication.setExchangeRateApplied(new BigDecimal(
				 * "0.00473")); remittanceApplication.setLocalNetTranxAmount(new
				 * BigDecimal( "2"));
				 * remittanceApplication.setLocalCommisionAmount(new BigDecimal(
				 * "1"));
				 * remittanceApplication.setLocalChargeAmount(BigDecimal.ZERO);
				 * remittanceApplication.setLocalDeliveryAmount(BigDecimal.ZERO);
				 

				// remittanceApplication.setForeignTranxAmount(new
				// BigDecimal(getSendDestPrincipleAmount()));
				// remittanceApplication.setLocalTranxAmount(new
				// BigDecimal(getSendAmount()));
				// remittanceApplication.setExchangeRateApplied(new
				// BigDecimal(getSendExchangeRate()));
				// remittanceApplication.setLocalCommisionAmount(new
				// BigDecimal(getSendCommission()));
				// remittanceApplication.setLocalChargeAmount(getOverseasamt());
				// remittanceApplication.setLocalDeliveryAmount(getCommission());

				// remittanceApplication.setLoyaltyPointsEncashed(getLoyaltyAmountAvailed());
				remittanceApplication.setDocumentFinancialyear(generalService
						.getDealYear(new Date()).get(0).getFinancialYear());
				remittanceApplication.setTransactionFinancialyear(generalService
						.getDealYear(new Date()).get(0).getFinancialYear());
				remittanceApplication
						.setSelectedCurrencyId(getOriginISOCurrencyId());
				remittanceApplication.setEmployeeId(sessionStateManage
						.getEmployeeId());

				remittanceApplication.setApplInd(Constants.C);
				remittanceApplication.setLoccod(sessionStateManage
						.getCountryBranchCode());
				remittanceApplication.setCompanyCode(getSendCompanyCode());

				remittanceApplication.setAccountMmyyyy(new SimpleDateFormat(
						"dd/MM/yyyy").parse(getCurrentDateWithFormat()));

				if (getSendMtcno() != null) {
					remittanceApplication.setWesternUnionMtcno(getSendMtcno());
				}
				remittanceApplication
						.setCreatedBy(sessionStateManage.getUserName());
				remittanceApplication.setCreatedDate(new Date());
				remittanceApplication.setIsactive(Constants.Yes);
				remittanceApplication.setApplicaitonStatus("S");
				remittanceApplication.setDocumentNo(getSendDocumentNo());

				remittanceApplication.setSourceofincome(getSendSourceOfIncome());
				
				remittanceApplication.setWuSenderMessage(getReceiverMessage());
				System.out.println("new points ="+getSenderNewPointsEarned());
				remittanceApplication.setWuTotalPointsEarned(String.valueOf(getSenderPointsEarned()==null ? new Long(0)  : getSenderPointsEarned()));
				remittanceApplication.setWuNewPointsEarned(String.valueOf(getSenderNewPointsEarned()==null ? new Long(0)  : getSenderNewPointsEarned()));
				
				
				
				if(!getWuPurposeTransaction().equalsIgnoreCase("others")){
					remittanceApplication.setWuPurposeOfTransaction(getWuPurposeTransaction());
				}else{
					remittanceApplication.setWuPurposeOfTransaction(getOtherPurposeTransaction());
				}
				StringBuffer senderMessage =null;;
				if(messageDetailList.size()>0){
					List<String> text = new ArrayList<String>();
					for(MessageDetail detail: messageDetailList){
						senderMessage =  new StringBuffer();
						senderMessage=senderMessage.append(detail.getWuMessage());
						senderMessage = senderMessage.append(" ");
					}				
					remittanceApplication.setWuSenderMessage(senderMessage.toString());
				}
				
				remittanceApplication.setWuSecretQuestion(getQuestion());
				remittanceApplication.setWuSecretAnswer(getAnswer());
				
				RemittanceAppBenificiary remitAppBene = wuh2hSaveRemittanceAppBenificary(remittanceApplication);

				// save Beneficiary Application Transaction
				mapWUH2HAllAppl.put("EX_APPL_BENE", remitAppBene);

				// save to EX_APPL_TRNX
				mapWUH2HAllAppl.put("EX_APPL_TRNX", remittanceApplication);

				// Financial Year
				mapWUH2HAllAppl.put("FinancialYear", getFinaceYear());

				List<RemitApplAml> remitApplAml = wuh2hSaveRemittanceAppAML(remittanceApplication);

				mapWUH2HAllAppl.put("EX_APPL_AML", remitApplAml);

				List<AdditionalInstructionData> lstAddInstData = saveAdditionalInstnData(remittanceApplication);

				mapWUH2HAllAppl.put("EX_APPL_ADDL_DATA", lstAddInstData);

				iwuh2hService.saveAllApplTransaction(mapWUH2HAllAppl);
				
				
				rtnSts = true;
			} catch (Exception e) {
				rtnSts = false;
				System.out.println("EXCEPTION IN :" + e.getMessage());
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("sqlexception.show();");
			}
			return rtnSts;
		}
	public void wuh2hSendClear() {

		setSendAmount(null);
		setReceiveAmount(null);
		setSendCommission(null);
		setSendGrossTotalAmount(null);
		setSendOriginPrincipleAmount(null);
		setSendDestPrincipleAmount(null);
		setTransferFee(null);
		setSendCharges(null);
		setSendNetAmountSent(null);
		setSendPayableAmount(null);
		setSendPayableAmountDisplay(null);
		setSendExchangeRate(null);
		setSendDiscountAmount(null);

		setCashAmount(null);
		setSendPurposeOfTransactions(null);
		setSendSourceOfIncome(null);
		//setWuCardno(null);
		setDeliveryService(null);
		setSendMtcno(null);
		setSendNewMtcno(null);

		setOriginISOCountryId(null);
		setOriginISOCountryCode(null);
		setOriginISOCurrencyId(null);
		setOriginISOCurrencyCode(null);
		setRecordingCountryCode(null);
		// setRecordingCurrencyId(null);
		setRecordingISOCurrencyCode(null);
		setAcknowledgement(true);
		
		setSendPromoDiscountAmount(null);	
		setWuPurposeTransaction(null);
		setOtherPurposeTransaction(null);
		setOtherPurposeEnable(false);
		//setSendWUcardNo(null);
		//setWuCardno(null);
		
		setWuStateCode(null);
		setWuCityName(null);
		
		setQuestion(null);
		setAnswer(null);
		setWuTransReferenceNo(null);
		receiverSearchReplyList.clear();
		setSendTxnCollectionId(null);
		//setWuCardLevelCode(null);
		setWuMessageCharge(null);
		messageDetailList.clear();
		
		coldatatablevalues.clear();
		
		coldatatablevalues.clear();
		setTotalAmount(null);
		setToalUsedAmount(null);
		setTotalbalanceAmount(null);
		setTotalrefundAmount(null);
		

	}

	public void wuh2hSendClearEnq() {

		setReceiveAmount(null);
		setSendCommission(null);
		setSendGrossTotalAmount(null);
		setSendOriginPrincipleAmount(null);
		setSendDestPrincipleAmount(null);
		setTransferFee(null);
		setSendCharges(null);
		setSendNetAmountSent(null);
		setSendPayableAmount(null);
		setSendPayableAmountDisplay(null);
		setSendExchangeRate(null);
		setSendDiscountAmount(null);

	}

	public void wuh2hSaveReceive() {
		String validateMessage = null;
		String returnmessage = null;
		try {
			
			if (getSendPayableAmountDisplay().compareTo(getDenomtotalCash())!=0 ) {
				System.out.println("difference");
				RequestContext.getCurrentInstance().execute(
						"amountmatch.show();");
				return;
			}
			
			if(getSaveOrNext().equalsIgnoreCase(Constants.Next))
			{
				
				//renderingDenominationDT();
				setCollectedRefundAmount(null);
				
				refundDenominationData();
				setBooRendercashdenomination(false);
				setBoorefundPaymentDetails(true);
				setBooSaveRender(false);
				setBooSaveAll(true);
				
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../wuh2h/wuhtohreceivemoneydenom.xhtml");
			}else
			{
				if(getDenominationchecking().equalsIgnoreCase(Constants.DenominationNotAvailable))
				{
					RequestContext.getCurrentInstance().execute("denominationerror.show();");
					return;
				}
				
				if(getDenomtotalCash()!=null && getDenomtotalCash().compareTo(BigDecimal.ZERO)!=0)
				{
										
					returnmessage = saveReceiveAll();
					if(returnmessage!=null){
						setErrmsg("Exception occured while saving collection and details " + returnmessage);
						log.info("Exception occured while saving collection and details ");
						RequestContext.getCurrentInstance().execute("err.show();");
						return;
					}else{
					validateMessage = wuh2hReceiveMoneyPay();
					
					if(validateMessage!=null){
						setErrorMessage(validateMessage);
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}
					
						//saveReceiveAll();
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("../wuh2h/wuh2hreceivemoneypaysuccess.xhtml");
					}
				}else
				{
					RequestContext.getCurrentInstance().execute("enterDenomination.show();");
					return;
				}
				
			}
			

		} catch (Exception e) {

		}
	
		
	}

	List<WUH2HReceiveInfoDataTable> selectedWUH2HPayList = new ArrayList<WUH2HReceiveInfoDataTable>();

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

	private String messageFour;
	private String amlMessageFour;
	private Boolean booMessageFour;
	private BigDecimal swiftId1;
	private BigDecimal swiftId2;
	private boolean setadditionalCheck;

	private List<ViewBeneServiceCurrency> beneServiceCurrencyList = new ArrayList<ViewBeneServiceCurrency>();
	private BigDecimal benifisCountryId;
	private BigDecimal benifisCurrencyId;
	private WesternUnionH2HBeneficaryDataTable selectWesternUnionBeneRec = new WesternUnionH2HBeneficaryDataTable();

	Map<String, String> amlAuthuTYpe = new HashMap<String, String>();
	Map<String, String> amlAuthuTYpeLog = new HashMap<String, String>();
	private List<CustomerAlmTrasactionCheckProcedure> almcheckList = new ArrayList<CustomerAlmTrasactionCheckProcedure>();

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

	public Map<String, String> getAmlAuthuTYpe() {
		return amlAuthuTYpe;
	}

	public void setAmlAuthuTYpe(Map<String, String> amlAuthuTYpe) {
		this.amlAuthuTYpe = amlAuthuTYpe;
	}

	public Map<String, String> getAmlAuthuTYpeLog() {
		return amlAuthuTYpeLog;
	}

	public void setAmlAuthuTYpeLog(Map<String, String> amlAuthuTYpeLog) {
		this.amlAuthuTYpeLog = amlAuthuTYpeLog;
	}

	public boolean isSetadditionalCheck() {
		return setadditionalCheck;
	}

	public void setSetadditionalCheck(boolean setadditionalCheck) {
		this.setadditionalCheck = setadditionalCheck;
	}

	public boolean isHaveAmlCheck() {
		return isHaveAmlCheck;
	}

	public void setHaveAmlCheck(boolean isHaveAmlCheck) {
		this.isHaveAmlCheck = isHaveAmlCheck;
	}

	public boolean isFromAMLCheck() {
		return fromAMLCheck;
	}

	public void setFromAMLCheck(boolean fromAMLCheck) {
		this.fromAMLCheck = fromAMLCheck;
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

	public boolean isBooMessageOne() {
		return booMessageOne;
	}

	public void setBooMessageOne(boolean booMessageOne) {
		this.booMessageOne = booMessageOne;
	}

	public boolean isBooMessageTwo() {
		return booMessageTwo;
	}

	public void setBooMessageTwo(boolean booMessageTwo) {
		this.booMessageTwo = booMessageTwo;
	}

	public boolean isBooMessageThree() {
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

	public String getAmlAuthorizedPassword() {
		return amlAuthorizedPassword;
	}

	public void setAmlAuthorizedPassword(String amlAuthorizedPassword) {
		this.amlAuthorizedPassword = amlAuthorizedPassword;
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

	public String getAmlAuntenticationMessageOne() {
		return amlAuntenticationMessageOne;
	}

	public void setAmlAuntenticationMessageOne(
			String amlAuntenticationMessageOne) {
		this.amlAuntenticationMessageOne = amlAuntenticationMessageOne;
	}

	public String getAmlAuntenticationAuthorizedBy() {
		return amlAuntenticationAuthorizedBy;
	}

	public void setAmlAuntenticationAuthorizedBy(
			String amlAuntenticationAuthorizedBy) {
		this.amlAuntenticationAuthorizedBy = amlAuntenticationAuthorizedBy;
	}

	public String getAmlAuntenticationAuthorizedPassword() {
		return amlAuntenticationAuthorizedPassword;
	}

	public void setAmlAuntenticationAuthorizedPassword(
			String amlAuntenticationAuthorizedPassword) {
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

	public List<CustomerAlmTrasactionCheckProcedure> getAlmcheckList() {
		return almcheckList;
	}

	public void setAlmcheckList(
			List<CustomerAlmTrasactionCheckProcedure> almcheckList) {
		this.almcheckList = almcheckList;
	}

	
	 * Properties
	 

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
	private String customerCountryName;

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
	private int finaceYear;

	// private String sourceOfIncomes;
	private String sourceOfIncome;
	private String purposeOfTransactions;
	private List<SourceOfIncomeDescription> lstSourceOfIncomes;
	private List<PurposeOfTransaction> lstPurposeOfTransactions;

	
	 * boolean properties for rendering
	 
	private boolean booRenderBenificaryFirstPanel;
	private boolean mainPanelRender;
	private boolean booRenderOldSmartCardPanel;
	// private boolean booRenderOverseaCharges = false;
	private boolean booRenderLayaltyServicePanel = false;
	// private boolean booRenderBenificarySearchPanel = false;
	// private boolean booRenderBeniListPanel;
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
	private List<WesternUnionH2HBeneficaryDataTable> coustomerBeneficaryDTList = new ArrayList<WesternUnionH2HBeneficaryDataTable>();
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

	List<WesternUnionH2HBeneficaryDataTable> selectedWUBeneList = new ArrayList<WesternUnionH2HBeneficaryDataTable>();
	ForeignLocalCurrencyDataTable dataTableClear = new ForeignLocalCurrencyDataTable();

	private List<PurposeOfTransaction> lstPurposeOfTransaction = new ArrayList<>();
	private List<SourceOfIncome> lstSourceOfIncome = new ArrayList<>();
	private BigDecimal deleteBeneRelationId;

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
	private WesternUnionH2HBeneficaryDataTable selectedValues;

	private BigDecimal collectionId;
	private BigDecimal collectionDocumentNo;
	private BigDecimal relationId;

	private String errorMessage;
	private String birthdate;
	private BigDecimal wucheckRecCustomer;

	// To check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	
	public String getCustomerCountryName() {
		return customerCountryName;
	}

	public void setCustomerCountryName(String customerCountryName) {
		this.customerCountryName = customerCountryName;
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

	public void setBooRenderBenificaryFirstPanel(
			Boolean booRenderBenificaryFirstPanel) {
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

	public List<WesternUnionH2HBeneficaryDataTable> getCoustomerBeneficaryDTList() {
		return coustomerBeneficaryDTList;
	}

	public void setCoustomerBeneficaryDTList(
			List<WesternUnionH2HBeneficaryDataTable> coustomerBeneficaryDTList) {
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

	public boolean isBooRenderLayaltyServicePanel() {
		return booRenderLayaltyServicePanel;
	}

	public void setBooRenderLayaltyServicePanel(
			boolean booRenderLayaltyServicePanel) {
		this.booRenderLayaltyServicePanel = booRenderLayaltyServicePanel;
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

	public void setBenificaryStatusList(
			List<BeneficaryStatus> benificaryStatusList) {
		this.benificaryStatusList = benificaryStatusList;
	}

	public List<BeneficaryStatus> getBenificaryStatusName() {
		return benificaryStatusName;
	}

	public void setBenificaryStatusName(
			List<BeneficaryStatus> benificaryStatusName) {
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

	public void setLstRefundData(
			ArrayList<ForeignLocalCurrencyDataTable> lstRefundData) {
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

	public void setBooRenderBenificaryFirstPanel(
			boolean booRenderBenificaryFirstPanel) {
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

	public void setColdatatablevalues(
			CopyOnWriteArrayList<PersonalRemittanceCollectionDataTable> coldatatablevalues) {
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

	public void setBooRendercollectiondatatable(
			boolean booRendercollectiondatatable) {
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

	public void setLstselectedrecord(
			CopyOnWriteArrayList<ShoppingCartDataTableBean> lstselectedrecord) {
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
		return ipaymentService
				.fetchPaymodeDesc(
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "1"),
						Constants.Yes);
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

	public void setReceiveDocumentFinanceYear(
			BigDecimal receiveDocumentFinanceYear) {
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
			List<UserFinancialYear> financialYearList = foreignCurrencyPurchaseService
					.getUserFinancialYear(new Date());
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

	public void setShoppingcartDTList(
			List<ShoppingCartDataTableBean> shoppingcartDTList) {
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

	public List<WesternUnionH2HBeneficaryDataTable> getSelectedWUBeneList() {
		return selectedWUBeneList;
	}

	public void setSelectedWUBeneList(
			List<WesternUnionH2HBeneficaryDataTable> selectedWUBeneList) {
		this.selectedWUBeneList = selectedWUBeneList;
	}

	public boolean getIsWUSend() {
		return isWUSend;
	}

	public void setIsWUSend(boolean isWUSend) {
		this.isWUSend = isWUSend;
	}

	public WesternUnionH2HBeneficaryDataTable getSelectedValues() {
		return selectedValues;
	}

	public void setSelectedValues(
			WesternUnionH2HBeneficaryDataTable selectedValues) {
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

	public void setLstPurposeOfTransaction(
			List<PurposeOfTransaction> lstPurposeOfTransaction) {
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

	public void setLstSourceOfIncomes(
			List<SourceOfIncomeDescription> lstSourceOfIncomes) {
		this.lstSourceOfIncomes = lstSourceOfIncomes;
	}

	public List<PurposeOfTransaction> getLstPurposeOfTransactions() {
		return lstPurposeOfTransactions;
	}

	public void setLstPurposeOfTransactions(
			List<PurposeOfTransaction> lstPurposeOfTransactions) {
		this.lstPurposeOfTransactions = lstPurposeOfTransactions;
	}

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(
			IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
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

	public List<ViewBeneServiceCurrency> getBeneServiceCurrencyList() {
		return beneServiceCurrencyList;
	}

	public void setBeneServiceCurrencyList(
			List<ViewBeneServiceCurrency> beneServiceCurrencyList) {
		this.beneServiceCurrencyList = iPersonalRemittanceService
				.getViewBeneCurrency(getBenifisCountryId());
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

	public WesternUnionH2HBeneficaryDataTable getSelectWesternUnionBeneRec() {
		return selectWesternUnionBeneRec;
	}

	public void setSelectWesternUnionBeneRec(
			WesternUnionH2HBeneficaryDataTable selectWesternUnionBeneRec) {
		this.selectWesternUnionBeneRec = selectWesternUnionBeneRec;
	}

	public BigDecimal getDeleteBeneRelationId() {
		return deleteBeneRelationId;
	}

	public void setDeleteBeneRelationId(BigDecimal deleteBeneRelationId) {
		this.deleteBeneRelationId = deleteBeneRelationId;
	}

	
	 * WUH2H properties
	 

	private String sendMtcno;
	private String sendNewMtcno;
	private BigDecimal wuBeneBankId;
	private BigDecimal countryId;
	private BigDecimal currencyId;
	private BigDecimal sendSourceOfIncome;
	private BigDecimal sendPurposeOfTransactions;
	private BigDecimal sendExchangeRate;
	private BigDecimal sendCharges;
	private BigDecimal sendCommission;
	private BigDecimal sendLoyaltyAmountAvailed;
	private BigDecimal sendGrossTotalAmount;
	private BigDecimal sendPayableAmount;
	private BigDecimal sendPayableAmountDisplay;
	private BigDecimal sendNetAmountSent;
	private BigDecimal sendDiscountAmount;
	private BigDecimal sendOriginPrincipleAmount;
	private BigDecimal sendDestPrincipleAmount;
	private BigDecimal sendMessageAmount;
	private BigDecimal sendPlusChargeAmount;
	private String senderIdType;
	private String senderIdNumber;
	private String senderIdIssueCountry;
	private String senderOccupation;
	private String senderFirstName;
	private String senderLastName;
	private BigDecimal sendRoutingBankId;
	private String originISOCountryCode;
	private BigDecimal originISOCountryId;
	private BigDecimal originISOCurrencyId;
	private String originISOCurrencyCode;
	
	private BigDecimal wuBeneBranchId;
	
	private boolean wucardLookup;
	private String sendWUcardNo;

	private BigDecimal recordingCurrencyId;
	private String recordingISOCurrencyCode;
	private String recordingCountryCode;
	private BigDecimal originationAmount;
	private BigDecimal destinationAmount;
	private String originationCountryName;
	private String destinatonCountryName;
	private String sendCurrencyName;
	private String senderContactPhone;
	private String moneyTransferKey;

	private String wuCardno;
	private String payoutCountry;
	private String promotionCode;
	private String deliveryService;
	private BigDecimal receiveAmount;
	private BigDecimal sendAmount;
	private BigDecimal transferFee;
	private BigDecimal totalAmount;
	private BigDecimal recordingCountryId;

	private boolean enableCardLookup;
	private boolean enableSendMoneyTxnStatus;

	private BigDecimal receiverISOCountryId;
	private String receiverISOCountryCode;
	private String receiverISOCurrencyCode;
	private BigDecimal receiverISOCurrencyId;
	private String receiverAddress1;
	private String receiverPostalcode;
	private String receiverCityName;
	private String receiverContactPhone;
	private String receiverStateName;
	private String receiverCountryName;
	private String receiverCurrencyName;
	private String receiverDebitAccountNo;
	private String receiverSwiftBic;
	private String receiverFirstName;
	private String receiverLastName;
	private String receiverCurrency;
	private String receiverCountry;
	private String receiveMtcnno;
	private boolean displayReceiverInfo;
	private String senderPointsEarned;
	private String senderNewPointsEarned;
	private String senderMessage;
	private String senderContactNo;
	private String senderNatinalityNo;
	private Address receiverAddress;
	private Financials receiverFinancials;
	//private Address senderAddress;
	private Sender senderDetails;
	
	
	public Sender getSenderDetails() {
		return senderDetails;
	}

	public void setSenderDetails(Sender senderDetails) {
		this.senderDetails = senderDetails;
	}

	public Address getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(Address senderAddress) {
		this.senderAddress = senderAddress;
	}

	public Address getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(Address receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public Financials getReceiverFinancials() {
		return receiverFinancials;
	}

	public void setReceiverFinancials(Financials receiverFinancials) {
		this.receiverFinancials = receiverFinancials;
	}

	public BigDecimal getSendPayableAmountDisplay() {
		return sendPayableAmountDisplay;
	}

	public void setSendPayableAmountDisplay(BigDecimal sendPayableAmountDisplay) {
		this.sendPayableAmountDisplay = sendPayableAmountDisplay;
	}

	public String getSenderContactNo() {
		return senderContactNo;
	}

	public void setSenderContactNo(String senderContactNo) {
		this.senderContactNo = senderContactNo;
	}

	public String getSenderNatinalityNo() {
		return senderNatinalityNo;
	}

	public void setSenderNatinalityNo(String senderNatinalityNo) {
		this.senderNatinalityNo = senderNatinalityNo;
	}

	public String getSenderMessage() {
		return senderMessage;
	}

	public void setSenderMessage(String senderMessage) {
		this.senderMessage = senderMessage;
	}

	public BigDecimal getOriginationAmount() {
		return originationAmount;
	}

	public void setOriginationAmount(BigDecimal originationAmount) {
		this.originationAmount = originationAmount;
	}

	public BigDecimal getDestinationAmount() {
		return destinationAmount;
	}

	public void setDestinationAmount(BigDecimal destinationAmount) {
		this.destinationAmount = destinationAmount;
	}

	public String getOriginationCountryName() {
		return originationCountryName;
	}

	public void setOriginationCountryName(String originationCountryName) {
		this.originationCountryName = originationCountryName;
	}

	public String getDestinatonCountryName() {
		return destinatonCountryName;
	}

	public void setDestinatonCountryName(String destinatonCountryName) {
		this.destinatonCountryName = destinatonCountryName;
	}

	public String getSendCurrencyName() {
		return sendCurrencyName;
	}

	public void setSendCurrencyName(String sendCurrencyName) {
		this.sendCurrencyName = sendCurrencyName;
	}

	public String getMoneyTransferKey() {
		return moneyTransferKey;
	}

	public void setMoneyTransferKey(String moneyTransferKey) {
		this.moneyTransferKey = moneyTransferKey;
	}

	private BigDecimal sendBeneCurrencyId;

	public BigDecimal getSendBeneCurrencyId() {
		return sendBeneCurrencyId;
	}

	public void setSendBeneCurrencyId(BigDecimal sendBeneCurrencyId) {
		this.sendBeneCurrencyId = sendBeneCurrencyId;
	}

	public String getSenderContactPhone() {
		return senderContactPhone;
	}

	public void setSenderContactPhone(String senderContactPhone) {
		this.senderContactPhone = senderContactPhone;
	}

	public String getSenderPointsEarned() {
		return senderPointsEarned;
	}

	public void setSenderPointsEarned(String senderPointsEarned) {
		this.senderPointsEarned = senderPointsEarned;
	}

	private String sendPOSMessage;

	public String getSendPOSMessage() {
		return sendPOSMessage;
	}

	public void setSendPOSMessage(String sendPOSMessage) {
		this.sendPOSMessage = sendPOSMessage;
	}

	public String getSenderNewPointsEarned() {
		return senderNewPointsEarned;
	}

	public void setSenderNewPointsEarned(String senderNewPointsEarned) {
		this.senderNewPointsEarned = senderNewPointsEarned;
	}

	public BigDecimal getWuBeneBankId() {
		return wuBeneBankId;
	}

	public void setWuBeneBankId(BigDecimal wuBeneBankId) {
		this.wuBeneBankId = wuBeneBankId;
	}

	public String getRecordingCountryCode() {
		return recordingCountryCode;
	}

	public void setRecordingCountryCode(String recordingCountryCode) {
		this.recordingCountryCode = recordingCountryCode;
	}

	public String getRecordingISOCurrencyCode() {
		return recordingISOCurrencyCode;
	}

	public void setRecordingISOCurrencyCode(String recordingISOCurrencyCode) {
		this.recordingISOCurrencyCode = recordingISOCurrencyCode;
	}

	public BigDecimal getOriginISOCountryId() {
		return originISOCountryId;
	}

	public void setOriginISOCountryId(BigDecimal originISOCountryId) {
		this.originISOCountryId = originISOCountryId;
	}

	public BigDecimal getOriginISOCurrencyId() {
		return originISOCurrencyId;
	}

	public void setOriginISOCurrencyId(BigDecimal originISOCurrencyId) {
		this.originISOCurrencyId = originISOCurrencyId;
	}

	public BigDecimal getRecordingCurrencyId() {
		return recordingCurrencyId;
	}

	public void setRecordingCurrencyId(BigDecimal recordingCurrencyId) {
		this.recordingCurrencyId = recordingCurrencyId;
	}

	public String getOriginISOCountryCode() {
		return originISOCountryCode;
	}

	public void setOriginISOCountryCode(String originISOCountryCode) {
		this.originISOCountryCode = originISOCountryCode;
	}

	public String getOriginISOCurrencyCode() {
		return originISOCurrencyCode;
	}

	public void setOriginISOCurrencyCode(String originISOCurrencyCode) {
		this.originISOCurrencyCode = originISOCurrencyCode;
	}

	public String getReceiverISOCountryCode() {
		return receiverISOCountryCode;
	}

	public void setReceiverISOCountryCode(String receiverISOCountryCode) {
		this.receiverISOCountryCode = receiverISOCountryCode;
	}

	public String getReceiverISOCurrencyCode() {
		return receiverISOCurrencyCode;
	}

	public void setReceiverISOCurrencyCode(String receiverISOCurrencyCode) {
		this.receiverISOCurrencyCode = receiverISOCurrencyCode;
	}

	
	 * Sender properties
	 

	public BigDecimal getSendRoutingBankId() {
		return sendRoutingBankId;
	}

	public void setSendRoutingBankId(BigDecimal sendRoutingBankId) {
		this.sendRoutingBankId = sendRoutingBankId;
	}

	public BigDecimal getSendOriginPrincipleAmount() {
		return sendOriginPrincipleAmount;
	}

	public void setSendOriginPrincipleAmount(BigDecimal sendOriginPrincipleAmount) {
		this.sendOriginPrincipleAmount = sendOriginPrincipleAmount;
	}

	public BigDecimal getSendDestPrincipleAmount() {
		return sendDestPrincipleAmount;
	}

	public void setSendDestPrincipleAmount(BigDecimal sendDestPrincipleAmount) {
		this.sendDestPrincipleAmount = sendDestPrincipleAmount;
	}

	public BigDecimal getSendMessageAmount() {
		return sendMessageAmount;
	}

	public void setSendMessageAmount(BigDecimal sendMessageAmount) {
		this.sendMessageAmount = sendMessageAmount;
	}

	public BigDecimal getSendPlusChargeAmount() {
		return sendPlusChargeAmount;
	}

	public void setSendPlusChargeAmount(BigDecimal sendPlusChargeAmount) {
		this.sendPlusChargeAmount = sendPlusChargeAmount;
	}

	public BigDecimal getSendExchangeRate() {
		return sendExchangeRate;
	}

	public void setSendExchangeRate(BigDecimal sendExchangeRate) {
		this.sendExchangeRate = sendExchangeRate;
	}

	public BigDecimal getSendCommission() {
		return sendCommission;
	}

	public void setSendCommission(BigDecimal sendCommission) {
		this.sendCommission = sendCommission;
	}

	public BigDecimal getSendLoyaltyAmountAvailed() {
		return sendLoyaltyAmountAvailed;
	}

	public void setSendLoyaltyAmountAvailed(BigDecimal sendLoyaltyAmountAvailed) {
		this.sendLoyaltyAmountAvailed = sendLoyaltyAmountAvailed;
	}

	public BigDecimal getSendGrossTotalAmount() {
		return sendGrossTotalAmount;
	}

	public void setSendGrossTotalAmount(BigDecimal sendGrossTotalAmount) {
		this.sendGrossTotalAmount = sendGrossTotalAmount;
	}

	public BigDecimal getSendPayableAmount() {
		return sendPayableAmount;
	}

	public void setSendPayableAmount(BigDecimal sendPayableAmount) {
		this.sendPayableAmount = sendPayableAmount;
	}

	public BigDecimal getSendNetAmountSent() {
		return sendNetAmountSent;
	}

	public void setSendNetAmountSent(BigDecimal sendNetAmountSent) {
		this.sendNetAmountSent = sendNetAmountSent;
	}

	public BigDecimal getSendCharges() {
		return sendCharges;
	}

	public void setSendCharges(BigDecimal sendCharges) {
		this.sendCharges = sendCharges;
	}

	public BigDecimal getSendDiscountAmount() {
		return sendDiscountAmount;
	}

	public void setSendDiscountAmount(BigDecimal sendDiscountAmount) {
		this.sendDiscountAmount = sendDiscountAmount;
	}

	public String getSenderFirstName() {
		return senderFirstName;
	}

	public void setSenderFirstName(String senderFirstName) {
		this.senderFirstName = senderFirstName;
	}

	public String getSenderLastName() {
		return senderLastName;
	}

	public void setSenderLastName(String senderLastName) {
		this.senderLastName = senderLastName;
	}

	public String getSenderOccupation() {
		return senderOccupation;
	}

	public void setSenderOccupation(String senderOccupation) {
		this.senderOccupation = senderOccupation;
	}

	public String getSenderIdType() {
		return senderIdType;
	}

	public void setSenderIdType(String senderIdType) {
		this.senderIdType = senderIdType;
	}

	public String getSenderIdNumber() {
		return senderIdNumber;
	}

	public void setSenderIdNumber(String senderIdNumber) {
		this.senderIdNumber = senderIdNumber;
	}

	public String getSenderIdIssueCountry() {
		return senderIdIssueCountry;
	}

	public void setSenderIdIssueCountry(String senderIdIssueCountry) {
		this.senderIdIssueCountry = senderIdIssueCountry;
	}

	public String getReceiverSwiftBic() {
		return receiverSwiftBic;
	}

	public void setReceiverSwiftBic(String receiverSwiftBic) {
		this.receiverSwiftBic = receiverSwiftBic;
	}

	public String getReceiverDebitAccountNo() {
		return receiverDebitAccountNo;
	}

	public void setReceiverDebitAccountNo(String receiverDebitAccountNo) {
		this.receiverDebitAccountNo = receiverDebitAccountNo;
	}

	public String getReceiverCurrencyName() {
		return receiverCurrencyName;
	}

	public void setReceiverCurrencyName(String receiverCurrencyName) {
		this.receiverCurrencyName = receiverCurrencyName;
	}

	public String getReceiverStateName() {
		return receiverStateName;
	}

	public void setReceiverStateName(String receiverStateName) {
		this.receiverStateName = receiverStateName;
	}

	public String getReceiverCountryName() {
		return receiverCountryName;
	}

	public void setReceiverCountryName(String receiverCountryName) {
		this.receiverCountryName = receiverCountryName;
	}

	public String getReceiverAddress1() {
		return receiverAddress1;
	}

	public void setReceiverAddress1(String receiverAddress1) {
		this.receiverAddress1 = receiverAddress1;
	}

	public String getReceiverPostalcode() {
		return receiverPostalcode;
	}

	public void setReceiverPostalcode(String receiverPostalcode) {
		this.receiverPostalcode = receiverPostalcode;
	}

	public String getReceiverCityName() {
		return receiverCityName;
	}

	public void setReceiverCityName(String receiverCityName) {
		this.receiverCityName = receiverCityName;
	}

	public String getReceiverContactPhone() {
		return receiverContactPhone;
	}

	public void setReceiverContactPhone(String receiverContactPhone) {
		this.receiverContactPhone = receiverContactPhone;
	}

	public String getSendMtcno() {
		return sendMtcno;
	}

	public void setSendMtcno(String sendMtcno) {
		this.sendMtcno = sendMtcno;
	}

	public String getSendNewMtcno() {
		return sendNewMtcno;
	}

	public void setSendNewMtcno(String sendNewMtcno) {
		this.sendNewMtcno = sendNewMtcno;
	}

	private String customerTypeIdDesc;

	public String getCustomerTypeIdDesc() {
		return customerTypeIdDesc;
	}

	public void setCustomerTypeIdDesc(String customerTypeIdDesc) {
		this.customerTypeIdDesc = customerTypeIdDesc;
	}

	public String getReceiverFirstName() {
		return receiverFirstName;
	}

	public void setReceiverFirstName(String receiverFirstName) {
		this.receiverFirstName = receiverFirstName;
	}

	public String getReceiverLastName() {
		return receiverLastName;
	}

	public void setReceiverLastName(String receiverLastName) {
		this.receiverLastName = receiverLastName;
	}

	public String getReceiverCurrency() {
		return receiverCurrency;
	}

	public void setReceiverCurrency(String receiverCurrency) {
		this.receiverCurrency = receiverCurrency;
	}

	public String getReceiverCountry() {
		return receiverCountry;
	}

	public void setReceiverCountry(String receiverCountry) {
		this.receiverCountry = receiverCountry;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public List<CurrencyMaster> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<CurrencyMaster> currencyList) {
		this.currencyList = currencyList;
	}

	public BigDecimal getSendSourceOfIncome() {
		return sendSourceOfIncome;
	}

	public void setSendSourceOfIncome(BigDecimal sendSourceOfIncome) {
		this.sendSourceOfIncome = sendSourceOfIncome;
	}

	public BigDecimal getSendPurposeOfTransactions() {
		return sendPurposeOfTransactions;
	}

	public void setSendPurposeOfTransactions(
			BigDecimal sendPurposeOfTransactions) {
		this.sendPurposeOfTransactions = sendPurposeOfTransactions;
	}

	public BigDecimal getRecordingCountryId() {
		return recordingCountryId;
	}

	public void setRecordingCountryId(BigDecimal recordingCountryId) {
		this.recordingCountryId = recordingCountryId;
	}

	public String getWuCardno() {
		return wuCardno;
	}

	public void setWuCardno(String wuCardno) {
		this.wuCardno = wuCardno;
	}

	public String getPayoutCountry() {
		return payoutCountry;
	}

	public void setPayoutCountry(String payoutCountry) {
		this.payoutCountry = payoutCountry;
	}

	public String getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(String deliveryService) {
		this.deliveryService = deliveryService;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public BigDecimal getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(BigDecimal sendAmount) {
		this.sendAmount = sendAmount;
	}

	public BigDecimal getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(BigDecimal transferFee) {
		this.transferFee = transferFee;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public boolean isEnableCardLookup() {
		return enableCardLookup;
	}

	public void setEnableCardLookup(boolean enableCardLookup) {
		this.enableCardLookup = enableCardLookup;
	}

	public BigDecimal getReceiverISOCountryId() {
		return receiverISOCountryId;
	}

	public void setReceiverISOCountryId(BigDecimal receiverISOCountryId) {
		this.receiverISOCountryId = receiverISOCountryId;
	}

	public BigDecimal getReceiverISOCurrencyId() {
		return receiverISOCurrencyId;
	}

	public void setReceiverISOCurrencyId(BigDecimal receiverISOCurrencyId) {
		this.receiverISOCurrencyId = receiverISOCurrencyId;
	}

	public boolean isDisplayReceiverInfo() {
		return displayReceiverInfo;
	}

	public void setDisplayReceiverInfo(boolean displayReceiverInfo) {
		this.displayReceiverInfo = displayReceiverInfo;
	}

	public String getReceiveMtcnno() {
		return receiveMtcnno;
	}

	public void setReceiveMtcnno(String receiveMtcnno) {
		this.receiveMtcnno = receiveMtcnno;
	}

	public List<WUH2HReceiveInfoDataTable> getReceiverSearchReplyList() {
		return receiverSearchReplyList;
	}

	public void setReceiverSearchReplyList(
			List<WUH2HReceiveInfoDataTable> receiverSearchReplyList) {
		this.receiverSearchReplyList = receiverSearchReplyList;
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

	public void setListAdditionalBankDataTable(
			List<AddAdditionalBankData> listAdditionalBankDataTable) {
		this.listAdditionalBankDataTable = listAdditionalBankDataTable;
	}

	private boolean additionalCheck;

	public boolean getAdditionalCheck() {
		return additionalCheck;
	}

	public void setAdditionalCheck(boolean additionalCheck) {
		this.additionalCheck = additionalCheck;
	}

	public boolean isWucardLookup() {
		return wucardLookup;
	}

	public void setWucardLookup(boolean wucardLookup) {
		this.wucardLookup = wucardLookup;
	}

	public String getSendWUcardNo() {
		return sendWUcardNo;
	}

	public void setSendWUcardNo(String sendWUcardNo) {
		this.sendWUcardNo = sendWUcardNo;
	}

	
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public BigDecimal getWuBeneBranchId() {
		return wuBeneBranchId;
	}

	public void setWuBeneBranchId(BigDecimal wuBeneBranchId) {
		this.wuBeneBranchId = wuBeneBranchId;
	}
	
	// Ram Mohan added -06/01/2017
	private BigDecimal remitanceModeId;
	private BigDecimal deliverModeId;

	public BigDecimal getRemitanceModeId() {
		return remitanceModeId;
	}

	public void setRemitanceModeId(BigDecimal remitanceModeId) {
		this.remitanceModeId = remitanceModeId;
	}

	public BigDecimal getDeliverModeId() {
		return deliverModeId;
	}

	public void setDeliverModeId(BigDecimal deliverModeId) {
		this.deliverModeId = deliverModeId;
	}

	private void clearRemitanceDetails() {
		setRemitanceModeId(null);
		setDeliverModeId(null);
		//setReceiverMessage(null);
		setSecurityQuestionAvailable(false);
		setQuestion(null);
		setAnswer(null);
	}

	private void loadRemitanceDetails() {
		BigDecimal remitModeId = iwuh2hService.getRemitModeForCash();
		setRemitanceModeId(remitModeId);
		setDeliverModeId(new BigDecimal(105));
	}

	private void loadOtherDetails(WesternUnionH2HBeneficaryDataTable rel) {
		// setReceiverAddress1(rel.getBuildingNo());
		setReceiverContactPhone("32422243");
		setReceiverPostalcode("445555");
		setReceiverCityName(rel.getCityName());
		setReceiverStateName(rel.getStateName());
		setReceiverCountryName(rel.getCountryName());
		setReceiverCurrencyName(rel.getCurrencyName());
		setSendRoutingBankId(rel.getBankId());
		setReceiverDebitAccountNo(rel.getBankAccountNumber());
		setReceiverSwiftBic(rel.getSwiftBic());
		// setWuBeneBankId(rel.getBankId());
		setSendBeneCurrencyId(rel.getCurrencyId());
	}
	
	
	private String expectedStateCode;
	private String expectedStateName;
	private String expectedCityName;
	private String receiverMobileCountryCode;
	private String receiverMobileNationalNo;
	private String originatingCity;
	private String originalDestinationISOCountryCode;
	private String originalDestinationISOCurrencyCode;

	public String getExpectedStateCode() {
		return expectedStateCode;
	}

	public void setExpectedStateCode(String expectedStateCode) {
		this.expectedStateCode = expectedStateCode;
	}

	public String getExpectedStateName() {
		return expectedStateName;
	}

	public void setExpectedStateName(String expectedStateName) {
		this.expectedStateName = expectedStateName;
	}

	public String getExpectedCityName() {
		return expectedCityName;
	}

	public void setExpectedCityName(String expectedCityName) {
		this.expectedCityName = expectedCityName;
	}

	public String getReceiverMobileCountryCode() {
		return receiverMobileCountryCode;
	}

	public void setReceiverMobileCountryCode(String receiverMobileCountryCode) {
		this.receiverMobileCountryCode = receiverMobileCountryCode;
	}

	public String getReceiverMobileNationalNo() {
		return receiverMobileNationalNo;
	}

	public void setReceiverMobileNationalNo(String receiverMobileNationalNo) {
		this.receiverMobileNationalNo = receiverMobileNationalNo;
	}

	public String getOriginatingCity() {
		return originatingCity;
	}

	public void setOriginatingCity(String originatingCity) {
		this.originatingCity = originatingCity;
	}

	public String getOriginalDestinationISOCountryCode() {
		return originalDestinationISOCountryCode;
	}

	public void setOriginalDestinationISOCountryCode(
			String originalDestinationISOCountryCode) {
		this.originalDestinationISOCountryCode = originalDestinationISOCountryCode;
	}

	public String getOriginalDestinationISOCurrencyCode() {
		return originalDestinationISOCurrencyCode;
	}

	public void setOriginalDestinationISOCurrencyCode(
			String originalDestinationISOCurrencyCode) {
		this.originalDestinationISOCurrencyCode = originalDestinationISOCurrencyCode;
	}

	private String receiverStreet;

	public String getReceiverStreet() {
		return receiverStreet;
	}

	public void setReceiverStreet(String receiverStreet) {
		this.receiverStreet = receiverStreet;
	}

	// new 12/1/2016
	private String receiverDateOfBirth;
	private String receiverIdType;
	private String receiverIdNumber;
	private String receiverIdCountryOfIssue;
	private String receiverIdExpirationDate;
	private String receiverIdIssueDate;
	private String receiverStateZip;
	private String receiveMoneyPaidOn;
	private String receiverPaidDateTime;

	public String getReceiverDateOfBirth() {
		return receiverDateOfBirth;
	}

	public void setReceiverDateOfBirth(String receiverDateOfBirth) {
		this.receiverDateOfBirth = receiverDateOfBirth;
	}

	public String getReceiverIdType() {
		return receiverIdType;
	}

	public void setReceiverIdType(String receiverIdType) {
		this.receiverIdType = receiverIdType;
	}

	public String getReceiverIdNumber() {
		return receiverIdNumber;
	}

	public void setReceiverIdNumber(String receiverIdNumber) {
		this.receiverIdNumber = receiverIdNumber;
	}

	public String getReceiverIdCountryOfIssue() {
		return receiverIdCountryOfIssue;
	}

	public void setReceiverIdCountryOfIssue(String receiverIdCountryOfIssue) {
		this.receiverIdCountryOfIssue = receiverIdCountryOfIssue;
	}

	public String getReceiverIdExpirationDate() {
		return receiverIdExpirationDate;
	}

	public void setReceiverIdExpirationDate(String receiverIdExpirationDate) {
		this.receiverIdExpirationDate = receiverIdExpirationDate;
	}

	public String getReceiverIdIssueDate() {
		return receiverIdIssueDate;
	}

	public void setReceiverIdIssueDate(String receiverIdIssueDate) {
		this.receiverIdIssueDate = receiverIdIssueDate;
	}

	public String getReceiverStateZip() {
		return receiverStateZip;
	}

	public void setReceiverStateZip(String receiverStateZip) {
		this.receiverStateZip = receiverStateZip;
	}

	public String getReceiverPaidDateTime() {
		return receiverPaidDateTime;
	}

	public void setReceiverPaidDateTime(String receiverPaidDateTime) {
		this.receiverPaidDateTime = receiverPaidDateTime;
	}

	public String getReceiveMoneyPaidOn() {
		return receiveMoneyPaidOn;
	}

	public void setReceiveMoneyPaidOn(String receiveMoneyPaidOn) {
		this.receiveMoneyPaidOn = receiveMoneyPaidOn;
	}

	private String sendMoneyTransferKey;
	private String sendReason;
	private String sendAddressLine1;
	private String sendAddressLine2;
	private String senderPostalCode;
	private String senderCity;
	private String senderCountryName;
	private String modifyMtcn;

	public String getModifyMtcn() {
		return modifyMtcn;
	}

	public void setModifyMtcn(String modifyMtcn) {
		this.modifyMtcn = modifyMtcn;
	}

	public String getSenderCountryName() {
		return senderCountryName;
	}

	public void setSenderCountryName(String senderCountryName) {
		this.senderCountryName = senderCountryName;
	}

	public String getSenderCity() {
		return senderCity;
	}

	public void setSenderCity(String senderCity) {
		this.senderCity = senderCity;
	}

	public String getSendAddressLine1() {
		return sendAddressLine1;
	}

	public void setSendAddressLine1(String sendAddressLine1) {
		this.sendAddressLine1 = sendAddressLine1;
	}

	public String getSendAddressLine2() {
		return sendAddressLine2;
	}

	public void setSendAddressLine2(String sendAddressLine2) {
		this.sendAddressLine2 = sendAddressLine2;
	}

	public String getSenderPostalCode() {
		return senderPostalCode;
	}

	public void setSenderPostalCode(String senderPostalCode) {
		this.senderPostalCode = senderPostalCode;
	}

	public String getSendMoneyTransferKey() {
		return sendMoneyTransferKey;
	}

	public void setSendMoneyTransferKey(String sendMoneyTransferKey) {
		this.sendMoneyTransferKey = sendMoneyTransferKey;
	}

	public String getSendReason() {
		return sendReason;
	}

	public void setSendReason(String sendReason) {
		this.sendReason = sendReason;
	}

	public void wuh2hReceiveClear() {

		setReceiveMtcnno(null);
		receiverSearchReplyList.clear();

		setReceiverFirstName(null);
		setReceiverLastName(null);
		setReceiverCurrencyName(null);
		setReceiverISOCurrencyCode(null);
		setReceiverISOCountryCode(null);
		setReceiverPostalcode(null);
		setReceiverCityName(null);
		setReceiverStreet(null);
		setReceiverStateName(null);
		setReceiverStateZip(null);
		setReceiverCurrency(null);
		setReceiverCountry(null);
		setReceiverMobileCountryCode(null);
		setReceiverMobileNationalNo(null);

		setExpectedCityName(null);
		setExpectedStateCode(null);
		setExpectedStateName(null);

		setReceiveAmount(null);
		setReceiveMtcnno(null);

		setReceiverAddress1(null);
		setOriginationAmount(null);
		setDestinationAmount(null);

		setSendCommission(null);
		setSendCharges(null);

		setOriginationCountryName(null);
		setDestinatonCountryName(null);
		setSendCurrencyName(null);

		setSendExchangeRate(null);
		setSendPlusChargeAmount(null);
		setSendGrossTotalAmount(null);
		setSendPayableAmount(null);
		setSendPayableAmountDisplay(null);

		setSendMtcno(null);
		setSendNewMtcno(null);
		setMoneyTransferKey(null);

		setOriginISOCountryCode(null);
		setOriginISOCurrencyCode(null);

		setOriginalDestinationISOCountryCode(null);
		setOriginalDestinationISOCurrencyCode(null);
		
		//new
		setWuPurposeTransaction(null);
		setOtherPurposeTransaction(null);
		setOtherPurposeEnable(false);
		setPurposeOfTransactions(null);

		setWuStateCode(null);
		setWuCityName(null);
		setWuTransReferenceNo(null);

		//setTxnType(0);

	}

	
	 * Load WUH2H properties
	 
	private static String propertyFilePath = "/wuh2h.properties";
	static Properties prop = null;
	static InputStream input = null;
	static String channelName = null;
	static String channelVersion = null;
	static String remoteRefno = null;
	static String remoteCounterId = null;
	static String remoteIdentifier = null;
	static String sendTemplateId = null;
	static String payTemplateId = null;
	static String civilIdType = null;	
	static String duplicateDetectFlag = null;
	static String xmlStorePath = null;
	
	static {

		prop = new Properties();
		input = WesternUnionHToHBean.class.getClassLoader()
				.getResourceAsStream(propertyFilePath);
		try {
			prop.load(input);
			channelName = prop.getProperty("WUH2H.CHANNEL_NAME");

			remoteRefno = prop.getProperty("WUH2H.FOREIGN_REMOTE_REFNO");
			remoteCounterId = prop
					.getProperty("WUH2H.FOREIGN_REMOTE_COUNTERID");
			remoteIdentifier = prop
					.getProperty("WUH2H.FOREIGN_REMOTE_IDENTIFIER");
			
			sendTemplateId = prop
					.getProperty("WUH2H.send_TEMPLATE_ID = KWT_01_S");
			payTemplateId = prop
					.getProperty("WUH2H.PAY_TEMPLATE_ID = KWT_01_P");
			civilIdType = prop
					.getProperty("WUH2H.CIVILID_TYPE");
			duplicateDetectFlag = prop
					.getProperty("WUH2H.DUPLICATE_SEND_FLAG");
			xmlStorePath = prop.getProperty("WUH2H.XML_STORE_PATH");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}	
	
	private String sendMoneyType;

	public String getSendMoneyType() {
		return sendMoneyType;
	}

	public void setSendMoneyType(String sendMoneyType) {
		this.sendMoneyType = sendMoneyType;
	}
	
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	
	private JasperPrint jasperPrint;
	public void whh2hSendMoneyReceiptReportInit() throws JRException {

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(wuRemittanceReceiptSubreportList);
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String reportPath = realPath +"//reports//design//wusendmainreport.jasper";
		System.out.println(reportPath);

		//String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceiptNewReport.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}
	
	public void generateWUSendReport(){
		ServletOutputStream servletOutputStream=null;
		try {
				
			fetchWUSendReceiptReportData(new BigDecimal(getDocumentNo()),getFinaceYear(),Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION);

			whh2hSendMoneyReceiptReportInit();
			byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
			httpServletResponse.addHeader("Content-disposition","attachment; filename=WUSendmomeyReport.pdf");
			servletOutputStream=httpServletResponse.getOutputStream();  
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();

		}catch (NullPointerException ne) {
			log.info("Exception in generateWUReceiveReport:" + ne.getMessage());
			setErrorMessage("Method Name::generateWUReceiveReport");
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
		} catch (Exception exception) {
			log.info("Exception in generateWUReceiveReport:" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
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

	private List<WURemittanceReceiptSubreport> wuRemittanceReceiptSubreportList;
	private void fetchWUSendReceiptReportData(BigDecimal documentNumber,BigDecimal finaceYear, String documentCode) throws Exception{ 

		wuRemittanceReceiptSubreportList = new CopyOnWriteArrayList<WURemittanceReceiptSubreport>();
		List<WURemittanceApplicationView> remittanceApplicationList = new ArrayList<WURemittanceApplicationView>();
		List<WURemittanceApplicationView> fcsaleList = new ArrayList<WURemittanceApplicationView>();
		List<WURemittanceReportBean> remittanceApplList = new ArrayList<WURemittanceReportBean>();

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		int noOfTransactions = 0;

		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(sessionStateManage.getUserName());

		List<WURemittanceApplicationView> remittanceViewlist = iwuh2hService.getRecordsForWURemittanceReceiptReport(documentNumber,finaceYear,documentCode);
		if (remittanceViewlist.size() > 0) {
			for (WURemittanceApplicationView remittanceAppview : remittanceViewlist) {
				remittanceApplicationList.add(remittanceAppview);
				noOfTransactions= noOfTransactions+1;
			}
			//remittance List
			for (WURemittanceApplicationView view : remittanceApplicationList) {

				WURemittanceReportBean obj = new WURemittanceReportBean();
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
				
				
				obj.setSenderPointsEarned(String.valueOf(view.getWuTotalPointsEarned()==null ? new Long(0)  : view.getWuTotalPointsEarned()));
				obj.setSenderNewPointsEarned(String.valueOf(view.getWuNewPointsEarned()==null ? new Long(0)  : view.getWuNewPointsEarned()));
				
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
				
				String bnfName = view.getBeneficiaryName();
				bnfName  = bnfName.replace("(TEL NO:)", "");
				obj.setBeneficiaryName(bnfName);
				//obj.setBeneficiaryName(view.getBeneficiaryName());
				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				//obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
				obj.setBenefeciaryBranchName("Any Where");
				obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
				obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setUserName(sessionStateManage.getUserName());
				obj.setPinNo(view.getPinNo() );
				obj.setMtcn(getSendMtcno());
				
				obj.setWuCardNo(getSendWUcardNo());
				
				if(view.getSecurityQuestion()!=null){
					obj.setQuestionLabel("Security Question");
					obj.setQuestion(": " +view.getSecurityQuestion());
				}
				if(view.getSecurityAnswer()!=null){
					obj.setAnswerLabel("Security Answer");
					obj.setAnswer(": " +view.getSecurityAnswer());
				}
				if(view.getSenderMessage()!=null){
					obj.setMessageLabel("Sender Message");
					obj.setMessage(view.getSenderMessage());
				}else{
					obj.setMessage(".");
				}
			
				
				if(view.getSecurityQuestion()!=null){
					obj.setQuestionLabel("Security Question");
					obj.setQuestion(": " +view.getSecurityQuestion());
				}
				if(view.getSecurityAnswer()!=null){
					obj.setAnswerLabel("Security Answer");
					obj.setAnswer(": " +view.getSecurityAnswer());
				}
				if(view.getSenderMessage()!=null){
					obj.setMessageLabel(view.getSenderMessage());
					obj.setMessage(": " +view.getSenderMessage());
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
					//paymentChannel.append(" - ");
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
				if(view.getWuPromoDiscount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal promoDiscount=GetRound.roundBigDecimal((view.getWuPromoDiscount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setPromotionDiscount(currencyQuoteName+"     ******"+promoDiscount.toString());
				}
				obj.setFutherInstructions(view.getInstructions());
				obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
				obj.setIntermediataryBank(view.getBenefeciaryInterBank1());
				
				obj.setPurposeOfRemittance(view.getWuPurposeOfTransaction());

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
				List<CollectionPaymentDetailsView> collectionPmtDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(view.getCompanyId(),view.getCollectionDocumentNo(),view.getCollectionDocFinanceYear(),view.getCollectionDocCode());
				for(CollectionPaymentDetailsView collPaymentDetailsView: collectionPmtDetailList){
					if(collPaymentDetailsView.getCollectionMode().equalsIgnoreCase(Constants.VOCHERCODE)){
						lessLoyaltyEncash = collPaymentDetailsView.getCollectAmount();
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}else{
						amountPayable=amountPayable.add(collPaymentDetailsView.getCollectAmount());
					}
					if(collPaymentDetailsView.getApprovalNo()!=null){
						obj.setApprovalNo(collPaymentDetailsView.getApprovalNo());
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
				
				// Company Information
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
				
				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());

					if (customerList.size() > 0) {
						CutomerDetailsView cust = customerList.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
					}
				}
				remittanceApplList.add(obj);
			}

			//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
			WURemittanceReceiptSubreport remittanceObj = new WURemittanceReceiptSubreport();
			remittanceObj.setWaterMarkLogoPath(waterMark);
			remittanceObj.setWaterMarkCheck(false);
			remittanceObj.setRemittanceApplList(remittanceApplList);
			remittanceObj.setSubReport(subReportPath);

			if(remittanceApplList!=null && remittanceApplList.size()>0){
				remittanceObj.setRemittanceReceiptCheck(true);
			}
			wuRemittanceReceiptSubreportList.add(remittanceObj);

		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
			return;
		}

	}
	
	public List<WURemittanceReportBean> calculateCollectionMode(WURemittanceApplicationView viewCollectionObj){	
		List<WURemittanceReportBean> collectionDetailList = new ArrayList<WURemittanceReportBean>();
		List<CollectionPaymentDetailsView> collectionPaymentDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(viewCollectionObj.getCompanyId(),viewCollectionObj.getCollectionDocumentNo(),viewCollectionObj.getCollectionDocFinanceYear(),viewCollectionObj.getCollectionDocCode());

		int size = collectionPaymentDetailList.size();
		for(CollectionPaymentDetailsView viewObj: collectionPaymentDetailList){
			WURemittanceReportBean obj = new WURemittanceReportBean();
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
					obj.setApprovalNo(viewObj.getApprovalNo());
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
	
	*//**
	 * Document Seriality
	 *//*
	public String getDocumentSerialID(String processIn) {
		System.out.println("Start getDocumentSerialID ...." + getFinaceYear());

		String documentSerialId="";

		log.info("process in :" + processIn);
		try {
			HashMap<String, String> outPutValues= generalService.getNextDocumentRefNumber(Integer.parseInt(sessionStateManage.getSessionValue("countryId")), Integer.parseInt(sessionStateManage.getSessionValue("companyId")), 
					Integer.parseInt(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION
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
	
	
	// Save Receive money starts
	private String saveOrNext = Constants.Save;
	private String validNoOFQuantity = null;
	private String totalValue = null;
	
	
	
	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	public String getValidNoOFQuantity() {
		return validNoOFQuantity;
	}

	public void setValidNoOFQuantity(String validNoOFQuantity) {
		this.validNoOFQuantity = validNoOFQuantity;
	}

	public String getSaveOrNext() {
		return saveOrNext;
	}

	public void setSaveOrNext(String saveOrNext) {
		this.saveOrNext = saveOrNext;
	}

	private Map<String , BigDecimal> mapFetchAllPayMode = new HashMap<String, BigDecimal>();
	public String  saveReceiveAll()
	{
		String errormessage = null;
		try {
			setReciveDocNo(null);
			toFetchPaymentDetails();
			BigDecimal docId = generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")).get(0).getDocumentID();
			
			String saveDocumentSerialID = getDocumentSerialID(Constants.U);
			setReciveDocNo(new BigDecimal(saveDocumentSerialID));
			HashMap<String, Object> returnResult 			 = saveCollectionList(docId,new BigDecimal(saveDocumentSerialID));
			Collect  	collect								 =(Collect)returnResult.get("collect");
			
			
			List<CollectDetail> collectionDetailList 		 =  saveCollectionDetailList(collect,docId,new BigDecimal(saveDocumentSerialID));
			List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = saveForeignCurrecnyList(collect,docId,new BigDecimal(saveDocumentSerialID));
			
			List<Payment> paymentRefundData = savePaymentRecord(collect,docId,new BigDecimal(saveDocumentSerialID));
			
			//List<ReceiptPayment> listReceiptPmt				 =savePaymentReceiptList();
			ReceiptPayment receiptPayment = savePaymentReceiptList(collect,docId,new BigDecimal(saveDocumentSerialID));

			getForeignCurrencyPurchaseService().saveCollect(collect);
			iwuh2hService.saveCollectDetail(collectionDetailList);
			iwuh2hService.saveForeignCurrencyAdjust(foreignCurrencyAdjustList);
			iwuh2hService.savePayment(paymentRefundData.get(0));
			iwuh2hService.saveReceiptPayment(receiptPayment);
			
			//BigDecimal collectionId = new BigDecimal(0);
			
			//collectionId = getForeignCurrencyPurchaseService().saveCollectCollecDetailCurrAdjustAndFinalReceipt(collect, collectionDetailList, foreignCurrencyAdjustList, receiptPayment, paymentRefundData);
			
			//log.info(" collection id is "+ collectionId);
			
			iwuh2hService.saveCurrAdjustReceiptAndPayment(foreignCurrencyAdjustList, receiptPayment, paymentRefundData);
			
			
			//TO DO: Commented by visw
			//setPurposeOfTransactions(getForeignCurrencyPurchaseService().getPurposeofTransaction(getSendPurposeOfTransactions()));
			
			//setPurposeOfTransactions(getForeignCurrencyPurchaseService().getPurposeofTransaction(new BigDecimal(1)));
			errormessage = null;
		} catch (Exception e2) {
			//setErrmsg("Exception occured while saving collection and details " + e2.getMessage());
			log.info("Exception occured while saving collection and details "+e2);
			//RequestContext.getCurrentInstance().execute("err.show();");
			
			errormessage = "Exception occured while saving collection and details " + e2.getMessage();
			
		}
		
		return errormessage;
	}
	
	public HashMap<String, Object> saveCollectionList(BigDecimal documentId, BigDecimal documnetSerialNumber) {
		Collect collect = new Collect();
		HashMap<String, Object> returnResult = new HashMap<String, Object>();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		String date = getCurrentDateWithFormat();
		Date acc_Month = null;
		try{

			acc_Month = DATE_FORMAT.parse(date);			
			// Company save
			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
			collect.setFsCompanyMaster(companyMaster);
			log.info("saleAmount  ..." + getSendPayableAmount());
			// customer Save
			//collect.setDocumentNo(new BigDecimal(documentSerialId));
			collect.setDocumentNo(documnetSerialNumber);
			collect.setDocumentId(documentId);
			
			if(getCustomerId() != null){
				customer = new Customer();
				customer.setCustomerId(getCustomerId());
				collect.setFsCustomer(customer);
			}
			
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerNo());
			collect.setFsCustomer(customer);
			
			List<UserFinancialYear> finYearList = generalService
					.getDealYear(new Date());

			if (finYearList != null && !finYearList.isEmpty()) {
				collect.setDocumentFinanceYear(finYearList.get(0)
										.getFinancialYear());
			}
			
			//collect.setDocumentFinanceYear(new BigDecimal(finaceYear));

			CountryBranch countryBranch = new CountryBranch();
			countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			collect.setExBankBranch(countryBranch);
			collect.setApplicationCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));

			collect.setPaidAmount(getOriginationAmount()); // fc Amount
			collect.setRefoundAmount(BigDecimal.ZERO); // no refund is possible for FC Purchase
			collect.setNetAmount(getOriginationAmount()); // fc Amount
			
			//collect.setPaidAmount(new BigDecimal(getTotalValue()==null ? getSaleAmount() : getTotalValue())); // Local currency sale amount 
			if (getTotalRefundLocalCashEntered() != null && Double.parseDouble(getTotalRefundLocalCashEntered()) > 0) {
				collect.setRefoundAmount(new BigDecimal(getTotalRefundLocalCashEntered()));
				collect.setNetAmount(new BigDecimal(getSaleAmount()));
			} else {
				collect.setRefoundAmount(new BigDecimal(0.0));
				//collect.setNetAmount(new BigDecimal(Double.parseDouble(String.valueOf(getTotalPurchaseAmount())) - 0.0));
				collect.setNetAmount(new BigDecimal(getSaleAmount()));
			}


			collect.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
			collect.setIsActive(Constants.Yes);

			collect.setAccountMMYYYY(acc_Month);

			collect.setGeneralLegerDate(new Date());
			collect.setCollectDate(new Date());
			BigDecimal currencyId= generalService.getCurrencyIDFromQuote(getOriginISOCurrencyCode());
			// Currency Insert
			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(currencyId);
			collect.setExCurrencyMaster(currencyMaster);

			collect.setCreatedBy(sessionStateManage.getSessionValue("userName"));
			collect.setCreatedDate(new Date());
			
			List<CompanyMasterDesc> companyCode = generalService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			if(companyCode != null && !companyCode.isEmpty() && companyCode.get(0).getFsCompanyMaster() != null){
				BigDecimal companyCodeValue = companyCode.get(0).getFsCompanyMaster().getCompanyCode();
				collect.setCompanyCode(companyCodeValue);
			}
			
			collect.setLocCode(sessionStateManage.getCountryBranchCode());

			returnResult.put("collect", collect);

		}catch (Exception e) {
			e.printStackTrace();
		}

		return returnResult;
	}


	*//**
	 * Collection Information Save
	 *//*

	public List<CollectDetail> saveCollectionDetailList(Collect collect,BigDecimal documentId, BigDecimal documnetSerialNumber){
		List<CollectDetail> collectDetailList = new ArrayList<CollectDetail>();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		String date = getCurrentDateWithFormat();
		int i = 0;
		Date acc_Month = null;

		try{

			if(collect!=null){
				acc_Month = DATE_FORMAT.parse(date);
				CollectDetail collectDetail = new CollectDetail();

				Customer customer = new Customer();
				customer.setCustomerId(getCustomerNo());
				collectDetail.setFsCustomer(customer);

				// Company save
				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
				collectDetail.setFsCompanyMaster(companyMaster);

				// Country Save
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
				collectDetail.setFsCountryMaster(countryMaster);

				collectDetail.setDocumentFinanceYear(collect.getDocumentFinanceYear());
				
				collectDetail.setCollectionMode(Constants.C); // only foreign currency is paid in cash C
				collectDetail.setPaymentModeId(mapFetchAllPayMode.get(Constants.C));
				
				//collectDetail.setPaymentModeId(paymentModeId);
				collectDetail.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
				collectDetail.setDocumentId(collect.getDocumentId());
				//collectDetail.setDocumentNo(new BigDecimal(documentSerialId));
				collectDetail.setDocumentNo(collect.getDocumentNo());

				CountryBranch countryBranch = new CountryBranch();
				countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
				collectDetail.setExBankBranch(countryBranch);

				collectDetail.setDocumentLineNo(new BigDecimal(++i));

				BigDecimal currencyId= generalService.getCurrencyIDFromQuote(getOriginISOCurrencyCode());
				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(currencyId);
				collectDetail.setExCurrencyMaster(currencyMaster);

				collectDetail.setAcyymm(acc_Month);

				// collectDetail.setExBankBranch();
				collectDetail.setDocumentDate(new Date());
				collectDetail.setCollAmt(collect.getPaidAmount());
				collectDetail.setIsActive(Constants.Yes);
				collectDetail.setCreatedDate(new Date());
				collectDetail.setCreatedBy(sessionStateManage.getSessionValue("userName"));
				
				collectDetail.setCompanyCode(collect.getCompanyCode());
				
				collectDetail.setLocCode(sessionStateManage.getCountryBranchCode());
				
				collectDetailList.add(collectDetail);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return collectDetailList;
	}

	public List<ForeignCurrencyAdjust>  saveForeignCurrecnyList(Collect collect,BigDecimal documentId, BigDecimal documnetSerialNumber){

		List<ForeignCurrencyAdjust>  lstCurrencyAdjust = new ArrayList<>();

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		Date acc_Month = null;

		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());

			int i = 0;
			for (DenominationBean denominationBean : denominationBeanList) {
				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
				//if (denominationBean.getNoOfNotes() != 0) {
				if (denominationBean.getNoOfNotes() !=null && !denominationBean.getNoOfNotes().equals("0") && !denominationBean.getNoOfNotes().equals("")) {
					// Company save
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
					foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);

					// Country Save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					// customer Save
					if(getCustomerId() != null){
						customer = new Customer();
						customer.setCustomerId(getCustomerId());
						foreignCurrencyAdjust.setFsCustomer(customer);
					}
					
					Customer customer = new Customer();
					customer.setCustomerId(getCustomerId());
					foreignCurrencyAdjust.setFsCustomer(customer);

					foreignCurrencyAdjust.setDocumentDate(new Date());

					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(getCurrencyId());
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(denominationBean.getNoOfNotes()));
					foreignCurrencyAdjust.setAdjustmentAmount(denominationBean.getPurchaseAmount());
					
					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(new BigDecimal(denominationBean.getDenominationID()));
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					//foreignCurrencyAdjust.setExchangeRate(denominationBean.getExchangeRate());
					foreignCurrencyAdjust.setExchangeRate(GetRound.roundBigDecimal(getAvgExchageRate(), 9));
					foreignCurrencyAdjust.setDenaminationAmount(denominationBean.getPurchaseAmount());
					foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(finaceYear));
					foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());
					*//****************************************************************************************//*
					// Tanumoy

					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE));
					foreignCurrencyAdjust.setDocumentNo(new BigDecimal(getDocSerialIdNumberForSave()));
					foreignCurrencyAdjust.setDocumentId(getDocumentIdPk());
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

					// BankBranch bankbranch = new BankBranch();
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					CurrencyWiseDenomination currencyWiseDenomination = new CurrencyWiseDenomination();
					currencyWiseDenomination.setDenominationId(new BigDecimal(denominationBean.getDenominationID()));
					foreignCurrencyAdjust.setFsDenominationId(currencyWiseDenomination);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(denominationBean.getNoOfNotes()));
					//foreignCurrencyAdjust.setExchangeRate(denominationBean.getExchangeRate());
					foreignCurrencyAdjust.setDenaminationAmount(denominationBean.getDenominationAmount());
					foreignCurrencyAdjust.setProgNumber(Constants.FC_PURCHAGE);
					
					//foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					// now document status is set P not Y
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);
					
					foreignCurrencyAdjust.setTransactionType(Constants.P);
					
					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getSessionValue("userName"));
					// no approval screen - so added directly while creating - Mandatory
					foreignCurrencyAdjust.setApprovalBy(sessionStateManage.getUserName());
					foreignCurrencyAdjust.setApprovalDate(new Date());

					foreignCurrencyAdjust.setCollect(collect);
					
					foreignCurrencyAdjust.setCompanyCode(collect.getCompanyCode());

					//Set in the List.
					lstCurrencyAdjust.add(foreignCurrencyAdjust);
				}
			}



			// For 2nd Panel
			i = 0;
			for (ForeignLocalCurrencyDataTable stockBean : lstData) {

				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();

				if (stockBean.getQty() != null && !stockBean.getQty().equals("") && !stockBean.getQty().equals("0")) {

					// Company save
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
					foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);

					// Country Save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					// customer Save
					if(getCustomerId() != null){
						customer = new Customer();
						customer.setCustomerId(getCustomerId());
						foreignCurrencyAdjust.setFsCustomer(customer);
					}
					
					Customer customer = new Customer();
					customer.setCustomerId(getCustomerNo());
					foreignCurrencyAdjust.setFsCustomer(customer);
					
					foreignCurrencyAdjust.setDocumentDate(new Date());

					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(stockBean.getCurrencyId());
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(stockBean.getQty()));

					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(stockBean.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					// It's for KWD
					foreignCurrencyAdjust.setExchangeRate(BigDecimal.ZERO);
					foreignCurrencyAdjust.setDenaminationAmount(stockBean.getItem());
					List<UserFinancialYear> finYearList = generalService
							.getDealYear(new Date());

					if (finYearList != null && !finYearList.isEmpty()) {
						foreignCurrencyAdjust.setDocumentFinanceYear(finYearList.get(0)
												.getFinancialYear());
					}
					//foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(finaceYear));
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(stockBean.getPrice()));
					foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());
					*//****************************************************************************************//*

					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					//foreignCurrencyAdjust.setDocumentNo(new BigDecimal(documentSerialId));
					foreignCurrencyAdjust.setDocumentNo(documnetSerialNumber);
					foreignCurrencyAdjust.setDocumentId(documentId);
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					CurrencyWiseDenomination currencyWiseDenomination = new CurrencyWiseDenomination();
					currencyWiseDenomination.setDenominationId(stockBean.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(currencyWiseDenomination);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(stockBean.getQty()));
					// foreignCurrencyAdjust.setExchangeRate(stockBean.getExchangeRate());
					foreignCurrencyAdjust.setDenaminationAmount(stockBean.getItem());
					foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
					
					//foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					// now document status is set P not Y
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);
					
					foreignCurrencyAdjust.setTransactionType(Constants.F); // Refund Amount F to Customer Local Currency
					*//**
					 * CR : from O to S
					 *//*
					// foreignCurrencyAdjust.setTransactionType("S");

					*//********************************************************************************************//*


					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getSessionValue("userName"));
					// no approval screen - so added directly while creating - Mandatory
					foreignCurrencyAdjust.setApprovalBy(sessionStateManage.getUserName());
					foreignCurrencyAdjust.setApprovalDate(new Date());
					
					
					List<CompanyMasterDesc> companyCode = generalService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
					if(companyCode != null && !companyCode.isEmpty() && companyCode.get(0).getFsCompanyMaster() != null){
						BigDecimal companyCodeValue = companyCode.get(0).getFsCompanyMaster().getCompanyCode();
						foreignCurrencyAdjust.setCompanyCode(companyCodeValue);
					}
					
					
					
					//foreignCurrencyAdjust.setCollect(collect);

					//Set in the List.
					lstCurrencyAdjust.add(foreignCurrencyAdjust);

				} 
			}

			// 3rd Panel
			i = 0;
			for (ForeignLocalCurrencyDataTable stockBean : lstRefundData) {
				ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();

				if (stockBean.getQty() != null && !stockBean.getQty().equals("") && !stockBean.getQty().equals("0")) {

					// Company save
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
					foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);

					// Country Save
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
					foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

					// customer Save
					if(getCustomerNo() != null){
						Customer customer = new Customer();
						customer.setCustomerId(getCustomerNo());
						foreignCurrencyAdjust.setFsCustomer(customer);
					}
					

					foreignCurrencyAdjust.setDocumentDate(new Date());

					// currency Id
					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(stockBean.getCurrencyId());
					foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(stockBean.getQty()));

					CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
					denominationMaster.setDenominationId(stockBean.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

					// It's for KWD
					foreignCurrencyAdjust.setExchangeRate(BigDecimal.ZERO);
					foreignCurrencyAdjust.setDenaminationAmount(stockBean.getItem());
					
					List<UserFinancialYear> finYearList = generalService
							.getDealYear(new Date());

					if (finYearList != null && !finYearList.isEmpty()) {
						foreignCurrencyAdjust.setDocumentFinanceYear(finYearList.get(0)
												.getFinancialYear());
					}
					
					//foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(finaceYear));
					foreignCurrencyAdjust.setOracleUser(sessionStateManage.getUserName());


					foreignCurrencyAdjust.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
					foreignCurrencyAdjust.setDocumentLineNumber(new BigDecimal(++i));
					foreignCurrencyAdjust.setDocumentId(documentId);
					foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);
					foreignCurrencyAdjust.setDocumentNo(documnetSerialNumber);

					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					foreignCurrencyAdjust.setCountryBranch(countryBranch);

					CurrencyWiseDenomination currencyWiseDenomination = new CurrencyWiseDenomination();
					currencyWiseDenomination.setDenominationId(stockBean.getDenominationId());
					foreignCurrencyAdjust.setFsDenominationId(currencyWiseDenomination);

					foreignCurrencyAdjust.setNotesQuantity(new BigDecimal(stockBean.getQty()));
					// foreignCurrencyAdjust.setExchangeRate(stockBean.getExchangeRate());
					foreignCurrencyAdjust.setDenaminationAmount(stockBean.getItem());
					foreignCurrencyAdjust.setAdjustmentAmount(new BigDecimal(stockBean.getPrice()));
					foreignCurrencyAdjust.setProgNumber(Constants.FC_SALE_REMIT);
					
					// foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
					// now document status is set P not Y
					foreignCurrencyAdjust.setDocumentStatus(Constants.P);
					
					foreignCurrencyAdjust.setTransactionType(Constants.C); // collected Amount C - collected from customer

					foreignCurrencyAdjust.setCreatedDate(new Date());
					foreignCurrencyAdjust.setCreatedBy(sessionStateManage.getSessionValue("userName"));
					// no approval screen - so added directly while creating - Mandatory
					foreignCurrencyAdjust.setApprovalBy(sessionStateManage.getUserName());
					foreignCurrencyAdjust.setApprovalDate(new Date());
					
					List<CompanyMasterDesc> companyCode = generalService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
					if(companyCode != null && !companyCode.isEmpty() && companyCode.get(0).getFsCompanyMaster() != null){
						BigDecimal companyCodeValue = companyCode.get(0).getFsCompanyMaster().getCompanyCode();
						foreignCurrencyAdjust.setCompanyCode(companyCodeValue);
					}
					foreignCurrencyAdjust.set
					getReceiveMtcnno()
					//foreignCurrencyAdjust.setCollect(collect);

					//Set in the List.
					lstCurrencyAdjust.add(foreignCurrencyAdjust);
				} 
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lstCurrencyAdjust;
	}
	
	public List<Payment> savePaymentRecord(Collect collect,BigDecimal documentId, BigDecimal documnetSerialNumber){
		
		List<Payment> lstPayment = new ArrayList<Payment>();
		
		// if refund available need to store in Ex_Payment
		if(lstData != null && !lstData.isEmpty()){
			
			try{
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
				String date = getCurrentDateWithFormat();
				int i = 0;
				Date acc_Month = null;
				acc_Month = DATE_FORMAT.parse(date);

				Payment payment = new Payment();

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(sessionStateManage.getCountryId());
				payment.setCountryId(countryMaster);

				payment.setCompanyId(sessionStateManage.getCompanyId());

				if(getCustomerId() != null){
					payment.setCustomerId(getCustomerId());
				}
				
				payment.setCustomerId(getCustomerNo());

				payment.setDocCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));

				List<UserFinancialYear> finYearList = generalService
						.getDealYear(new Date());

				if (finYearList != null && !finYearList.isEmpty()) {
					payment.setDocYear(finYearList.get(0)
											.getFinancialYear());
				}
				

				payment.setAcyymm(acc_Month);

				payment.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));

				//payment.setReceiptType(Constants.RECEIPT_TYPE); // 70 - fc Purchase
				payment.setPaymentDate(new Date());
				payment.setPaymentmode(Constants.C);// not only collection mode Cash - C , Bank Transfer - T , Bank Cheque - B
				payment.setCurrencyId(lstData.get(0).getCurrencyId()); // stock currency id always application currency id
				payment.setPaidAmount(getSendPayableAmountDisplay());

				payment.setDocNumber(documnetSerialNumber);

				payment.setIsActive(Constants.Yes);

				payment.setCreatedBy(sessionStateManage.getUserName());
				payment.setCreatedDate(new Date());
				
				payment.setLocCod(sessionStateManage.getCountryBranchCode());
				
				List<CompanyMasterDesc> companyCode = generalService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
				if(companyCode != null && !companyCode.isEmpty() && companyCode.get(0).getFsCompanyMaster() != null){
					BigDecimal companyCodeValue = companyCode.get(0).getFsCompanyMaster().getCompanyCode();
					payment.setCompanyCode(companyCodeValue);
				}
				
				//payment.setCompanyCode(collect.getCompanyCode());
				payment.setDocumentId(documentId);
				setDocumentNo(payment.getDocNumber().toString());
				lstPayment.add(payment);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return lstPayment;
	}


	public ReceiptPayment savePaymentReceiptList(Collect collect,BigDecimal documentId, BigDecimal documnetSerialNumber){
		List<ReceiptPayment> receiptPaymentList = new ArrayList<ReceiptPayment>();
		ReceiptPayment receiptPayment=null;

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
		Date acc_Month = null;

		try {
			acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
			receiptPayment = new ReceiptPayment();
			
			System.out.println("acc_Month :"+acc_Month);

			log.info("paymentReceipt start ..");

			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(new BigDecimal(sessionStateManage.getSessionValue("companyId")));
			receiptPayment.setFsCompanyMaster(companyMaster);

			// Country Save
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(new BigDecimal(sessionStateManage.getSessionValue("countryId")));
			receiptPayment.setFsCountryMaster(countryMaster);

			// customer Save
			if(getCustomerId() != null){
				customer = new Customer();
				customer.setCustomerId(getCustomerId());
				receiptPayment.setFsCustomer(customer);
			}
			List<UserFinancialYear> finYearList = generalService
					.getDealYear(new Date());

			if (finYearList != null && !finYearList.isEmpty()) {
				receiptPayment.setDocumentFinanceYear(finYearList.get(0)
										.getFinancialYear());
			}
						
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerNo());
			receiptPayment.setFsCustomer(customer);
			
			
			if(getCustomerrefno() != null){
				receiptPayment.setCustomerReference(getCustomerrefno());
			}

			//receiptPayment.setDocumentFinanceYear(new BigDecimal(finaceYear));
			receiptPayment.setCustomerName(getCustomerFullName());
			BigDecimal principalAmount= getOriginationAmount();
			receiptPayment.setForignTrnxAmount(principalAmount.movePointLeft(2));
			
			BigDecimal payoutAmount=getSendPayableAmount();
			receiptPayment.setLocalTrnxAmount(payoutAmount.movePointLeft(2));
			receiptPayment.setTransactionActualRate(getSendExchangeRate());
			receiptPayment.setLocalNetAmount(payoutAmount.movePointLeft(2));
			
			// receiptPayment.setDocumentStatus(Constants.Yes);
			// now document status is set P not Y
			receiptPayment.setDocumentStatus(Constants.P);
			
			receiptPayment.setGeneralLegerDate(new Date());
			receiptPayment.setIsActive(Constants.Yes);
			receiptPayment.setAccountMMYYYY(acc_Month);
			receiptPayment.setTransactionType(Constants.P);

			receiptPayment.setDocumentId(documentId);
			receiptPayment.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT));
			receiptPayment.setDocumentNo(documnetSerialNumber);
			//BankBranch bankBranch = new BankBranch();
			CountryBranch countryBranch= new CountryBranch();
			countryBranch.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			bankBranch.setBankBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			receiptPayment.setFsbankBranch(bankBranch);

			receiptPayment.setCountryBranch(countryBranch);
			receiptPayment.setDocumentDate(new Date());

			BigDecimal forgenCurrencyId= generalService.getCurrencyIDFromQuote(getOriginISOCurrencyCode());
			
			CurrencyMaster currencyMaster2 = new CurrencyMaster();
			currencyMaster2.setCurrencyId(forgenCurrencyId);
			receiptPayment.setForeignFsCountryMaster(currencyMaster2);
			
			BigDecimal localCurrencyId= generalService.getCurrencyIDFromQuote(getReceiverISOCurrencyCode());
			CurrencyMaster localCurrencyMaster = new CurrencyMaster();
			localCurrencyMaster.setCurrencyId(localCurrencyId);
			receiptPayment.setLocalFsCountryMaster(localCurrencyMaster);
			
			receiptPayment.setLocalCommisionCurrencyId(localCurrencyId);
			receiptPayment.setLocalOtherAdjCurrencyId(localCurrencyId);
			receiptPayment.setLocalOtherAdjAmount(payoutAmount.movePointLeft(2));
			receiptPayment.setWesternUnoinMycNo(getReceiveMtcnno());
			
			
			receiptPayment.setCreatedBy(sessionStateManage.getUserName());
			receiptPayment.setCreatedDate(new Date());
			
			
			PurposeOfTransaction purposeofTransaction = new PurposeOfTransaction();
			
			// TO Do: Commented by viswa
			//purposeofTransaction.setPurposeId(getSendPurposeOfTransactions());
			//receiptPayment.setPurposeOfTransaction(purposeofTransaction);
			
			purposeofTransaction.setPurposeId(new BigDecimal(1));
			receiptPayment.setPurposeOfTransaction(purposeofTransaction);
			

			//receiptPayment.setLocalTrnxAmount(getSendPayableAmount());
			// TO DO -- Ram mohan
			// purposeoftransaction
			PurposeOfTransaction purposeofTransaction = new PurposeOfTransaction();
			purposeofTransaction.setPurposeId(new BigDecimal(getPurposeOfTransactions()));
			receiptPayment.setPurposeOfTransaction(purposeofTransaction);

			SourceOfIncome sourceOfIncome = new SourceOfIncome();
			sourceOfIncome.setSourceId(new BigDecimal(getSourceOfIncomes()));
			receiptPayment.setSourceOfIncome(sourceOfIncome);

			receiptPayment.setRemarks(getRemarks());
			//receiptPayment.setSignature(getSignature());
			receiptPayment.setSignatureSpecimenClob(stringToClob(digitalSignature));
			receiptPayment.setCreatedDate(new Date());
			receiptPayment.setCreatedBy(sessionStateManage.getSessionValue("userName"));

			//Added by Rabil 21/02/2016

			receiptPayment.setColDocCode(collect.getDocumentCode());
			receiptPayment.setColDocFyr(collect.getDocumentFinanceYear());
			receiptPayment.setColDocNo(collect.getDocumentNo());
			receiptPayment.setSourceofIncomeId(new BigDecimal(getSourceOfIncomes()));
			
			
			
			//receiptPayment.setDocumentFinanceYearId(getFinanceYearId());
			
			receiptPayment.setDocumentFinanceYearId(generalService
					.getDealYear(new Date()).get(0).getFinancialYearID());

			//receiptPayment.setApplicationDocumentNo(applicationDocumentNo);
			//receiptPayment.setApplicationFinanceYear(applicationFinanceYear);
			//TO DO Ram mohan
			receiptPayment.setCollectionMode(getColpaymentmodeCode()); // not only collection mode Cash - C , Bank Transfer - T , Bank Cheque - B

			if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.ChequeCode)){
				// Bank Cheque 
				receiptPayment.setChequeBankCode(getColBankCode());
				receiptPayment.setChequeRef(getColChequeRef());
				receiptPayment.setChequeDate(getColChequeDate());
				receiptPayment.setApprovalNo(getColApprovalNo());
			}else if(getColpaymentmodeCode() != null && getColpaymentmodeCode().equalsIgnoreCase(Constants.BankTransferCode)){
				// Bank Transfer
				receiptPayment.setChequeBankCode(getColBankCode());
				receiptPayment.setApprovalNo(getColApprovalNo());
			}else{
				receiptPayment.setChequeBankCode(null);
				receiptPayment.setChequeRef(null);
				receiptPayment.setChequeDate(null);
				receiptPayment.setApprovalNo(null);
			}
			
			List<CompanyMasterDesc> companyCode = generalService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
			if(companyCode != null && !companyCode.isEmpty() && companyCode.get(0).getFsCompanyMaster() != null){
				BigDecimal companyCodeValue = companyCode.get(0).getFsCompanyMaster().getCompanyCode();
				receiptPayment.setCompanyCode(companyCodeValue);
			}
			//receiptPayment.setCompanyCode(collect.getCompanyCode());
			setDocumentNo(receiptPayment.getDocumentNo().toString());
			receiptPayment.setLocCode(sessionStateManage.getCountryBranchCode());

			BigDecimal countryId= iwuh2hService.getCountryIDFromCode(getSenderDetails().getAddress().getCountryCode().getIsoCode().getCountryCode());
			receiptPayment.setWuSenderCountryId(countryId);
			//receiptPayment.setWuSenderIdNumber(getSenderDetails().get);
			receiptPayment.setWuSenderFirstName(getSenderDetails().getName().getFirstName());
			receiptPayment.setWuSenderLastName(getSenderDetails().getName().getLastName());
			MobileDetails mobileDetails=getSenderDetails().getMobileDetails();
			
			if(mobileDetails!=null)
			{
				if(mobileDetails.getNumber()!=null)
				{
					receiptPayment.setWuSenderMobileNo( new BigDecimal(mobileDetails.getNumber()));
				}
				
			}
			
			receiptPayment.setWuSenderState(getSenderDetails().getAddress().getState());
			receiptPayment.setWuSendetCity(getSenderDetails().getAddress().getCity());
			receiptPayment.setWuSenderMessage(getSenderMessage());
			
			
			if(!getWuPurposeTransaction().equalsIgnoreCase("others")){
				receiptPayment.setWuPurposeOfTransaction(getWuPurposeTransaction());
			}else{
				receiptPayment.setWuPurposeOfTransaction(getOtherPurposeTransaction());
			}
			
			//receiptPaymentList.add(receiptPayment);


		}catch(Exception e){
			e.printStackTrace();
		}

		return receiptPayment;
	}
	
	//to fetch payment details
	public void toFetchPaymentDetails(){

			if(lstFetchAllPayMode != null || !lstFetchAllPayMode.isEmpty()){
				lstFetchAllPayMode.clear();
				mapFetchAllPayMode.clear();
			}

			List<PaymentModeDesc> list=ipaymentService.fetchPaymodeDesc(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),Constants.Yes);
			if(list.size() !=0){
				// only cash , bank transfer and bank cheque
				for (PaymentModeDesc paymentModeDesc : list) {
					if(paymentModeDesc.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.CashCode) || paymentModeDesc.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.ChequeCode)
							|| paymentModeDesc.getPaymentMode().getPaymentCode().equalsIgnoreCase(Constants.BankTransferCode)){
						lstFetchAllPayMode.add(paymentModeDesc);
						mapFetchAllPayMode.put(paymentModeDesc.getPaymentMode().getPaymentCode(), paymentModeDesc.getPaymentMode().getPaymentModeId());
					}
				}
			}
		}
	
	
	
	public void fetchLstData() {

		 Checking that it's first time or not, first time list size will be 0 
		double sAmount = 0;
		sAmount = Double.parseDouble(getSendGrossTotalAmount().toPlainString());
		//double saleAmt = 0.0;
		//setTotalValue("0");
		if (lstData.size() == 0) {
			 Responsible to show serial number in data table 
			int i = 0;
			
			 * Responsible to hold each row in different bean object of data table
			 
			ForeignLocalCurrencyDataTable item = null;

			List<Stock> dataFromDb = foreignLocalCurrencyDenominationService.getLocalCurrencyDenominationFromStock(sessionStateManage.getCountryId(), sessionStateManage.getUserName(), sessionStateManage.getBranchId(), sessionStateManage.getCompanyId(), sessionStateManage.getCurrencyId());
			//saleAmt = sAmount;
			 putting the value in list to show in data table 

			boolean deFalg = false;
			for (Stock element : dataFromDb) {
				int stock = element.getOpenQuantity() + element.getPurchaseQuantity() + element.getReceivedQuantity() - (element.getSaleQuantity() + element.getTransactionQuantity());

				String qty = null;
				double count = 0, totalNotes = 0;
				// double sAmount = 0;
				double denamination = 0;
				String amount = null;
				double result = 0.0;

				int cal = 0;

				if (stock > 0) {
					denamination = Double.parseDouble(element.getDenominationId().getDenominationAmount().toString());

					if (denamination < sAmount) {
						count = sAmount / denamination;
					}
					totalNotes = totalNotes + count;
					sAmount = sAmount % denamination;

					if (count != 0) {

						qty = new Integer((int) Math.ceil(count)).toString();
						cal = new Integer((int) Math.ceil(count));
						result = cal * denamination;
						log.info("result ----------- > " + result);
						if (result == new Integer((int) Math.ceil(saleAmt))) {
							amount = new Integer((int) Math.ceil(result)).toString();

						} else {
							if ((result - 1) == (new Integer((int) Math.ceil(saleAmt)))) {
								amount = new Integer((int) Math.ceil(result)).toString();
							} else {
								qty = "";

								amount = "0";
							}

						}

					} else {
						qty = "";
						amount = "0";
					}

					item = new ForeignLocalCurrencyDataTable(++i, element.getDenominationId().getDenominationAmount(), qty, amount, stock, element.getStockId(), element.getDenominationId().getDenominationId(), element.getDenominationId().getExCurrencyMaster().getCurrencyId(), element
							.getDenominationId().getDenominationDesc(),element.getDenominationId().getDenominationAmount());

					lstData.add(item);

				}// if(deFalg) break;
			}
		}
	
		

		
		ArrayList<ForeignLocalCurrencyDataTable> localLstData = new ArrayList<ForeignLocalCurrencyDataTable>();
		 Checking that it's first time or not, first time list size will be 0 
		// double sAmount = 0;
		localLstData.clear();
		if (localLstData.size() == 0) {

			List<ViewStock> stockList = iPersonalRemittanceService
					.toCheckStockForView(sessionStateManage.getCountryId(),
							sessionStateManage.getUserName(),
							sessionStateManage.getBranchId(),
							sessionStateManage.getCompanyId(),
							sessionStateManage.getCurrencyId());

			int serial = 1;
			for (ViewStock viewStockObj : stockList) {
				ForeignLocalCurrencyDataTable forLocalCurrencyDtObj = new ForeignLocalCurrencyDataTable();
				forLocalCurrencyDtObj.setSerial(serial);
				forLocalCurrencyDtObj.setItem(viewStockObj
						.getDenominationAmount());
				forLocalCurrencyDtObj.setQty("");
				forLocalCurrencyDtObj.setPrice("");
				if (viewStockObj.getCurrentStock() != null) {
					forLocalCurrencyDtObj.setStock(viewStockObj
							.getCurrentStock().intValue());
				} else {
					forLocalCurrencyDtObj.setStock(0);
				}
				forLocalCurrencyDtObj.setDenominationId(viewStockObj
						.getDenominationId());
				forLocalCurrencyDtObj.setCurrencyId(viewStockObj
						.getCurrencyId());
				forLocalCurrencyDtObj.setDenominationDesc(viewStockObj
						.getDenominationDEsc());
				forLocalCurrencyDtObj.setDenominationAmount(viewStockObj
						.getDenominationAmount());

				localLstData.add(forLocalCurrencyDtObj);
				serial++;
			}

		}
		setLstData(localLstData);
		 Responsible to keep sum of total amount of cash entered 
		int totalSum = 0;
		 Responsible to calculate sum of entered cash amount 
		for (ForeignLocalCurrencyDataTable element : localLstData) {
			if (element.getPrice().length() != 0) {
				totalSum = totalSum + Integer.parseInt(element.getPrice());
			}
		}
		System.out.println("Refund  :" + totalSum);
		
		if (localLstData.size() != 0) {
			setDenominationchecking(Constants.DenominationAvailable);
		} else {
			setDenominationchecking(Constants.DenominationNotAvailable);
		}

	
	}

	public void oncellEditForeign(ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable) {

		try {

			if (bean.getQty()!=null && !bean.getQty().equals("") && Integer.parseInt(bean.getQty()) > bean.getStock()) {
				bean.setQty("");
				bean.setPrice("");
				setValidNoOFQuantity(String.valueOf(bean.getStock()));
				RequestContext.getCurrentInstance().execute("invalidNoOFNotes.show();");
				return;
			} else {
				//setDenomIdCheckFor(bean.getPkDenom());

				String result = null;

				if(!bean.getQty().equals("") && bean.getQty()!=null) {
					result = String.valueOf(Double.parseDouble((bean.getItem()).toString()) * Double.parseDouble(bean.getQty()));
					BigDecimal total=((bean.getItem()).multiply(new BigDecimal(bean.getQty())));
					result=total.toString();
					bean.setPrice(new GetRound().round(Double.parseDouble(result), foreignLocalCurrencyDenominationService.getDecimalPerCountry(sessionStateManage.getCountryId())));
				}else{
					bean.setPrice("0");
				}

				//we are changed int to bigdecimal bcoz we need to calculate Decimal values also 
				 Responsible to keep sum of total amount of cash entered 
				BigDecimal totalSum= new BigDecimal(0);
				 Responsible to calculate sum of entered cash amount 
				for (ForeignLocalCurrencyDataTable element : lstData) {
					if (element.getPrice() != null && !element.getPrice().equalsIgnoreCase("") && element.getPrice().length() != 0) {
						//totalSum = totalSum + Integer.parseInt(element.getPrice());
						BigDecimal valueOfPrice=new BigDecimal(element.getPrice());
						totalSum=totalSum.add(valueOfPrice);
					}
				}

				 setting the summation value in bean object 
				setTotalValue(String.valueOf(totalSum));
				
				// render Save or Next button
				if (Double.parseDouble(getTotalValue()) <= Double.parseDouble(String.valueOf(getSendGrossTotalAmount().toPlainString()))) {
					setSaveOrNext(Constants.Save);
				}else{
					setSaveOrNext(Constants.Next);
				}
				if (totalSum.compareTo(getSendGrossTotalAmount())==0 || totalSum.compareTo(getSendGrossTotalAmount())==-1) {
					setSaveOrNext(Constants.Save);
				}else if(totalSum.compareTo(getSendGrossTotalAmount())==1){
					setSaveOrNext(Constants.Next);
				}
			}
		}catch (Exception e) {
			bean.setQty("");
			bean.setPrice("");
			setValidNoOFQuantity(String.valueOf(bean.getStock()));
			RequestContext.getCurrentInstance().execute("invalidNoOFNotes.show();");
			return;
		}
	

		
		try{
			
			int qty = 0;

			if (foreignLocalCurrencyDataTable.getQty() == ""
					&& foreignLocalCurrencyDataTable.getQty() != null) {
				qty = 0;
			} else {
				try {
					qty = Integer.parseInt(foreignLocalCurrencyDataTable
							.getQty());

				} catch (Exception e) {
					System.out.println("Exception occured" + e.getMessage());
					qty = 0;
				
				}
			}
			
			if (qty != 0 && foreignLocalCurrencyDataTable.getStock() < qty	 ) {

				RequestContext.getCurrentInstance().execute(
						"stockNotAvailable.show();");
				foreignLocalCurrencyDataTable.setQty("");
				foreignLocalCurrencyDataTable.setPrice("");
				return;
			}
			BigDecimal totalcashAmount = null;
			totalcashAmount = foreignLocalCurrencyDataTable.getItem().multiply(BigDecimal.valueOf(qty));


			if (foreignLocalCurrencyDataTable.getQty().equals("")) {
				foreignLocalCurrencyDataTable.setQty("");
				// added by rabil for clear if blank
				foreignLocalCurrencyDataTable.setPrice("");
			}
			if (totalcashAmount != null && totalcashAmount.doubleValue() != 0.0) {

				foreignLocalCurrencyDataTable.setPrice(GetRound
						.roundBigDecimal(
								totalcashAmount,
								foreignLocalCurrencyDenominationService
										.getDecimalPerCurrency(new BigDecimal(
												sessionStateManage
														.getCurrencyId())))
						.toPlainString());
			} else {
				foreignLocalCurrencyDataTable.setPrice("");
			}
			BigDecimal totalSum = BigDecimal.ZERO;
			 Responsible to calculate sum of entered cash amount 
			for (ForeignLocalCurrencyDataTable element : lstData) {
				if (element.getPrice().length() != 0) {
					totalSum = totalSum.add(new BigDecimal(element.getPrice()));
				}
			}
			BigDecimal totalDenoCash = getDenomtotalCash();
			if (getSendGrossTotalAmount().compareTo(totalSum) < 0) {
				totalSum = BigDecimal.ZERO;
				foreignLocalCurrencyDataTable.setQty("");
				foreignLocalCurrencyDataTable.setPrice("");
				for (ForeignLocalCurrencyDataTable element : lstData) {
					if (element.getPrice().length() != 0) {
						totalSum = totalSum.add(new BigDecimal(element.getPrice()));
					}
				}
				setDenomtotalCash(GetRound.roundBigDecimal(totalSum,
						foreignLocalCurrencyDenominationService
								.getDecimalPerCurrency(new BigDecimal(
										sessionStateManage.getCurrencyId()))));
				setCollectedAmount(getDenomtotalCash());
				RequestContext.getCurrentInstance().execute(
						"cleardenominationexceed.show();");
				setDataTableClear(foreignLocalCurrencyDataTable);
				foreignLocalCurrencyDataTable.setQty("");
				return;

			} else {
				setDenomtotalCash(GetRound.roundBigDecimal(totalSum,
						foreignLocalCurrencyDenominationService
								.getDecimalPerCurrency(new BigDecimal(
										sessionStateManage.getCurrencyId()))));
				setCollectedAmount(getDenomtotalCash());
			}
			setDenomtotalCash(GetRound.roundBigDecimal(totalSum,
					foreignLocalCurrencyDenominationService
							.getDecimalPerCurrency(new BigDecimal(
									sessionStateManage.getCurrencyId()))));
			setCollectedAmount(getDenomtotalCash());
			if (totalSum.compareTo(getSendGrossTotalAmount())==0 || totalSum.compareTo(getSendGrossTotalAmount())==-1) {
				setSaveOrNext(Constants.Save);
			}else if(totalSum.compareTo(getSendGrossTotalAmount())==1){
				setSaveOrNext(Constants.Next);
				setRefundAmount(totalSum.subtract(getSendGrossTotalAmount()));
			}
		}catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::onCellEdit()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

		

		
	
	}
	
	public void clearDataTableCashClearDenomination() {
		if (getDataTableClear() != null) {
			ForeignLocalCurrencyDataTable foreignLocalCurrencyDataTable = getDataTableClear();

			
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../wuh2h/wuh2hreceivemoneylocaldenom.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void wuh2hSaveReceiveRefund()	
	{
		String validateMessage = null;
		String returnmessage = null;
		try {
			
			if (lstRefundData.size() != 0) {
				if (getRefundAmount().compareTo(getCollectedRefundAmount()) == 0) {
					
					returnmessage = saveReceiveAll();
					if(returnmessage!=null){
						setErrmsg("Exception occured while saving collection and details " + returnmessage);
						log.info("Exception occured while saving collection and details ");
						RequestContext.getCurrentInstance().execute("err.show();");
						return;
					}else{				
					
					validateMessage = wuh2hReceiveMoneyPay();
					
					if(validateMessage!=null){
						setErrorMessage(validateMessage);
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}
					
						//saveReceiveAll();
					
					FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hreceivemoneypaysuccess.xhtml");
					}
					
				} else {
					RequestContext.getCurrentInstance().execute("refundamounterror.show();");
				}
			} else {
				RequestContext.getCurrentInstance().execute("denominationerror.show();");
			}
			
			
			} catch (Exception e) {

		}
	}
	public void backFromRefund()
	{
		try {
			setDenomtotalCash(getCollectedAmount());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hreceivemoneylocaldenom.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Save Receive money end
	
	
	//viswa new code 3/2/2017
	
	//private List<RemittanceReceiptSubreport> remittanceReceiptSubreportList;
	private void fetchWUReceiveReceiptReportData(BigDecimal documentNumber,BigDecimal finaceYear, String documentCode) throws Exception{ 
		
		wuRemittanceReceiptSubreportList = new CopyOnWriteArrayList<WURemittanceReceiptSubreport>();
		List<ReceiveReceiptView> remittanceApplicationList = new ArrayList<ReceiveReceiptView>();
		List<WURemittanceReportBean> remittanceApplList = new ArrayList<WURemittanceReportBean>();
		List<WURemittanceReportBean> fcsaleAppList = new ArrayList<WURemittanceReportBean>();

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		String subReportPath = realPath + Constants.SUB_REPORT_PATH;
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String currentDate = dateFormat.format(new Date());
		int noOfTransactions = 0;

		String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
		List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(sessionStateManage.getUserName());

		List<ReceiveReceiptView> remittanceViewlist = iwuh2hService.getReceiveReceiptData(documentNumber, finaceYear, new BigDecimal(documentCode));
		if (remittanceViewlist.size() > 0) {
			for (ReceiveReceiptView remittanceAppview : remittanceViewlist) {
				remittanceApplicationList.add(remittanceAppview);
				noOfTransactions= noOfTransactions+1;
			}
			//remittance List
			for (ReceiveReceiptView view : remittanceApplicationList) {
				WURemittanceReportBean obj = new WURemittanceReportBean();

				// setting customer reference	
				StringBuffer customerReff = new StringBuffer();
				if(view.getCustomerReference() != null){
					customerReff.append(view.getCustomerReference());
					customerReff.append(" / ");
				}

				StringBuffer customerName = new StringBuffer();
			    List<Customer> customerList=	icustomerRegistrationService.getCustomerInfo(view.getCustomerId());			    
			    if(customerList.size()>0){			    	
			    	if(customerList.get(0).getFirstName() != null){
						customerName.append(" ");
						customerName.append(customerList.get(0).getFirstName());
						customerReff.append(" ");
						customerReff.append(customerList.get(0).getFirstName());
					}
					if(customerList.get(0).getMiddleName() != null){
						customerName.append(" ");
						customerName.append(customerList.get(0).getMiddleName());
						customerReff.append(" ");
						customerReff.append(customerList.get(0).getMiddleName());
					}
					if(customerList.get(0).getLastName()!=null){
						customerName.append(" ");
						customerName.append(customerList.get(0).getLastName());
						customerReff.append(" ");
						customerReff.append(customerList.get(0).getLastName());
					}						
					obj.setMobileNo(customerList.get(0).getContactNumber());
					obj.setCivilId(customerList.get(0).getCivilId());
					Date sysdate = getCustomerExpDate();
					if(sysdate!=null){
						obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
					}
			    }
				obj.setFirstName(getCustomerNo()+" /" +view.getCustomerName());
				setCustomerNameForReport(view.getCustomerName());
				obj.setLocation(sessionStateManage.getLocation());

				//setting receipt Number
				StringBuffer receiptNo = new StringBuffer();
				if(view.getDocumentFinanceYear()!=null){
					receiptNo.append(view.getDocumentFinanceYear());
					receiptNo.append(" / ");
				}
				if(view.getCollectonDocumentNo()!=null){
					receiptNo.append(view.getCollectonDocumentNo());
				}
				obj.setReceiptNo(receiptNo.toString());

				// setting transaction Number
				StringBuffer transactionNo = new StringBuffer();
				if(view.getDocumentFinanceYear()!=null){
					transactionNo.append(view.getDocumentFinanceYear());
					transactionNo.append(" / ");
				}
				if(view.getDocumentNo()!=null){
					transactionNo.append(view.getDocumentNo());
				}
				obj.setTransactionNo(transactionNo.toString());
				obj.setDate(currentDate);
				
				
				//Sender Details
								
				StringBuffer sendername = new StringBuffer();				
				if(view.getWuSenderFirstName()!=null){
					sendername.append(view.getWuSenderFirstName()+" ");
				}
				if(view.getWuSenderLastName()!=null){
					sendername.append(view.getWuSenderLastName());
				}
				obj.setBeneficiaryName(sendername.toString());
				
				obj.setSenderCountry(view.getWuSenderCountry());
				obj.setSenderCity(view.getWuSenderCity());
				obj.setSenderState(view.getWuSenderState());
				obj.setSenderMobileno(view.getWuSenderMobile());
				//obj.setSenderMessage(view.getWuSenderMessage());
				
				if(view.getWuSenderMessage()!=null){
					obj.setMessageLabel("Sender Message");
					obj.setMessage(": " +view.getWuSenderMessage());
				}else{
					obj.setMessage(".");
				}
				
				StringBuffer address = new StringBuffer();
				if(getSendAddressLine1()!=null){
					address.append(getSendAddressLine1()+",");
				}
				if(getSendAddressLine2()!=null){
					address.append(getSendAddressLine2());
				}
				obj.setSenderAddress(address.toString());
				
				obj.setBenefeciaryBankName(view.getBeneficiaryBank());
				obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
				obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
				obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
				obj.setPhoneNumber(view.getPhoneNumber());
				obj.setUserName(sessionStateManage.getUserName());
				//obj.setPinNo(view.getPinNo() );
				obj.setMtcn(view.getMtcno());				

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

				String currencyAndAmount = null;
				BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyid()));
				if(view.getForeignCurrencyQuoteName()!=null && foreignTransationAmount!=null){
					currencyAndAmount = view.getForeignCurrencyQuoteName()+"     ******"+foreignTransationAmount;
				}
				obj.setCurrencyAndAmount(currencyAndAmount);

				if(view.getForeignCurrencyQuoteName()!=null && currencyQuoteName!=null && view.getExchangeRate()!=null){
					obj.setExchangeRate(view.getForeignCurrencyQuoteName()+" / "+currencyQuoteName+"     "+view.getExchangeRate().toString());
				}

				if(view.getLocalTransactionAmount()!=null && view.getLocalCurrencyId()!=null){
					BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
				}

				if(view.getLocalCommissionAmount()!=null && view.getLocalCurrencyId()!=null){
					BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
				}

				if(view.getLocalChargeAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
					BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
					obj.setOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
				}

				if(view.getLocalNetAmount()!=null && view.getLocalCurrencyId()!=null){
					BigDecimal netAmount=GetRound.roundBigDecimal((view.getLocalNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setTotalAmount(currencyQuoteName+"     ******"+netAmount.toString());
				}

				if(view.getLocalNetAmount()!=null && view.getLocalCurrencyId()!=null){
					BigDecimal collectNetAmount=GetRound.roundBigDecimal((view.getLocalNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
				}

				if(view.getPaidAmount()!=null && view.getLocalCurrencyId()!=null){
					BigDecimal collectPaidAmount=GetRound.roundBigDecimal((view.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
				}
				
				if(view.getPaidAmount()!=null && currencyQuoteName!=null && view.getLocalCurrencyId()!=null){
					BigDecimal payable=GetRound.roundBigDecimal((view.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalCurrencyId()));
					obj.setAmountPayable(currencyQuoteName+"     ******"+payable);
				}
				
				if(getSendPlusChargeAmount()!=null){
					obj.setOtherCharges(currencyQuoteName+"     ******"+getSendPlusChargeAmount().toString());
				}
				
				if(getSendExchangeRate()!=null){
					obj.setExchangeRate(currencyQuoteName+"     "+getSendExchangeRate().toString());
				}
				obj.setPaymentMode("Cash");
				obj.setApprovalNo(view.getApprovalNo());				
				//obj.setPurposeOfRemittance("Family Maintenance / Savings");
				
				if(!getWuPurposeTransaction().equalsIgnoreCase("others")){
					obj.setPurposeOfRemittance(getWuPurposeTransaction());
				}else{
					obj.setPurposeOfRemittance(getOtherPurposeTransaction());
				}
				obj.setPurposeOfRemittance(view.getWuPurposeOfTransaction());
				
				obj.setMobileNo(new BigDecimal("7357500709"));
				obj.setSubReport(subReportPath);
			
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
			
				if(obj.getFirstName() == null || obj.getFirstName().isEmpty()){
					List<CutomerDetailsView> customerList1 = iPersonalRemittanceService.getCustomerDetails(view.getCustomerId());
					if (customerList1.size() > 0) {
						CutomerDetailsView cust = customerList1.get(0);
						obj.setFirstName(cust.getCustomerName());
						if(cust.getContactNumber()!=null && !cust.getContactNumber().trim().equalsIgnoreCase("")){
							obj.setMobileNo(new BigDecimal(cust.getContactNumber()));
						}
						obj.setCivilId(cust.getIdNumber());
						Date sysdate1 = cust.getIdExp();
						if(sysdate1 != null){
							obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate1));
						}
					}
				}
				remittanceApplList.add(obj);
			}

			//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
			WURemittanceReceiptSubreport remittanceObj = new WURemittanceReceiptSubreport();

			remittanceObj.setWaterMarkLogoPath(waterMark);
			remittanceObj.setWaterMarkCheck(false);
			remittanceObj.setRemittanceApplList(remittanceApplList);
			remittanceObj.setSubReport(subReportPath);

			if(remittanceApplList!=null && remittanceApplList.size()>0){
				remittanceObj.setRemittanceReceiptCheck(true);
			}else{
				remittanceObj.setRemittanceReceiptCheck(false);
			}

			wuRemittanceReceiptSubreportList.add(remittanceObj);

		} else {
			RequestContext.getCurrentInstance().execute("noDataForReport.show();");
			return;
		}

	}
		
		
		public void whh2hReceiveMoneyReceiptReportInit() throws JRException {

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(wuRemittanceReceiptSubreportList);
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			String reportPath = realPath +"//reports//design//wureceivemainreport.jasper";
			System.out.println(reportPath);
			jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
		}
		
		public void generateWUReceiveReport(){
			ServletOutputStream servletOutputStream=null;
				try {
					
				fetchWUReceiveReceiptReportData(getReciveDocNo(),getFinaceYear(),Constants.DOCUMENT_CODE_FOR_PAYMENT);
				whh2hReceiveMoneyReceiptReportInit();
				byte[] pdfasbytes = JasperExportManager.exportReportToPdf(jasperPrint);

				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
				httpServletResponse.addHeader("Content-disposition","attachment; filename=WUReceivemomeyReport.pdf");
				servletOutputStream=httpServletResponse.getOutputStream();  
				JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();

			} catch (NullPointerException ne) {
				log.info("Exception in generateWUReceiveReport:" + ne.getMessage());
				setErrorMessage("Method Name::generateWUReceiveReport");
				RequestContext.getCurrentInstance()
						.execute("nullPointerId.show();");
			} catch (Exception exception) {
				log.info("Exception in generateWUReceiveReport:" + exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
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
		
		
		public void writeToFile(String filename, String content){ 
			
			String suffix = new SimpleDateFormat("yyyyMMddhhmmssms").format(new Date());
			filename = filename+"_"+suffix+".xml";
			File file = new File(xmlStorePath+filename+".xml");
		
			try (FileOutputStream fop = new FileOutputStream(file)) {

				// if file doesn't exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = content.getBytes();

				fop.write(contentInBytes);
				fop.flush();
				fop.close();

				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private boolean acknowledgement;
		private String commonPromotionCode;
		public boolean isAcknowledgement() {
			return acknowledgement;
		}

		public void setAcknowledgement(boolean acknowledgement) {
			this.acknowledgement = acknowledgement;
		}

		public String getCommonPromotionCode() {
			return commonPromotionCode;
		}

		public void setCommonPromotionCode(String commonPromotionCode) {
			this.commonPromotionCode = commonPromotionCode;
		}
		
		
		private String senderaddresLine1;
		private String senderaddresLine2;
		private String senderStateName;
		private String senderStreetName;
	
		public String getSenderaddresLine1() {
			return senderaddresLine1;
		}

		public void setSenderaddresLine1(String senderaddresLine1) {
			this.senderaddresLine1 = senderaddresLine1;
		}

		public String getSenderaddresLine2() {
			return senderaddresLine2;
		}

		public void setSenderaddresLine2(String senderaddresLine2) {
			this.senderaddresLine2 = senderaddresLine2;
		}

		public String getSenderStateName() {
			return senderStateName;
		}

		public void setSenderStateName(String senderStateName) {
			this.senderStateName = senderStateName;
		}

		public String getSenderStreetName() {
			return senderStreetName;
		}

		public void setSenderStreetName(String senderStreetName) {
			this.senderStreetName = senderStreetName;
		}
		
		// new 07/02/2017
		
		private String wuStateCode;
		private String wuStateName;
		private String wuCityName;

		public String getWuStateCode() {
			return wuStateCode;
		}

		public void setWuStateCode(String wuStateCode) {
			this.wuStateCode = wuStateCode;
		}

		public String getWuStateName() {
			return wuStateName;
		}

		public void setWuStateName(String wuStateName) {
			this.wuStateName = wuStateName;
		}

		public String getWuCityName() {
			return wuCityName;
		}

		public void setWuCityName(String wuCityName) {
			this.wuCityName = wuCityName;
		}
		
		List<WUStateCityModel> wuStateList = new ArrayList<WUStateCityModel>();
		List<WUStateCityModel> wuCityList = new ArrayList<WUStateCityModel>();
		public void fetchWUStateList() {

			if (wuStateList != null || !wuStateList.isEmpty()) {
				wuStateList.clear();
			}
			try {
				List<WUStateCityModel> lststate = iwuh2hService.getWUStateList(getReceiverISOCountryCode());
				if (lststate != null && lststate.size() != 0) {
					wuStateList.addAll(lststate);
				}
			} catch (NullPointerException ne) {
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"
						+ ne.getMessage());
				setErrorMessage("Method Name::fetchWUStateList()" + ne.getMessage());
				RequestContext.getCurrentInstance()
						.execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"
						+ exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}
		
		public void fetchWUCityList() {

			if (wuCityList != null || !wuCityList.isEmpty()) {
				wuCityList.clear();
			}
			try {
				List<WUStateCityModel> lstcity = iwuh2hService.getWUCityList(getReceiverISOCountryCode(), getWuStateCode());
				if (lstcity != null && lstcity.size() != 0) {
					wuCityList.addAll(lstcity);
				}
			} catch (NullPointerException ne) {
				log.info("ne.getMessage():::::::::::::::::::::::::::::::"
						+ ne.getMessage());
				setErrorMessage("Method Name::fetchWUCityList()" + ne.getMessage());
				RequestContext.getCurrentInstance()
						.execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"
						+ exception.getMessage());
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
		}

		public List<WUStateCityModel> getWuStateList() {
			return wuStateList;
		}

		public void setWuStateList(List<WUStateCityModel> wuStateList) {
			this.wuStateList = wuStateList;
		}

		public List<WUStateCityModel> getWuCityList() {
			return wuCityList;
		}

		public void setWuCityList(List<WUStateCityModel> wuCityList) {
			this.wuCityList = wuCityList;
		}
		
		private boolean displayWUState;
		private boolean displayWUCity;

		public boolean isDisplayWUState() {
			return displayWUState;
		}

		public void setDisplayWUState(boolean displayWUState) {
			this.displayWUState = displayWUState;
		}

		public boolean isDisplayWUCity() {
			return displayWUCity;
		}

		public void setDisplayWUCity(boolean displayWUCity) {
			this.displayWUCity = displayWUCity;
		}
		
		public void updateWUCardNo(String wucardno){
			
			int updateSts=iwuh2hService.updateWUEnrollCardNo(getCustomerNo(), wucardno);
			List<Customer> customerList = new ArrayList<Customer>();
			customerList = icustomerRegistrationService.getCustomerInfo(getCustomerNo());
			for(Customer customer:customerList){
				customer.setCustomerId(getCustomerNo());
				//customer.setWuCardNo(wucardno);
				icustomerRegistrationService.saveCustomer(customer);
				
			}
		}
		
		public void wuh2hTC(){
			fetchTc();
			RequestContext.getCurrentInstance().execute("tc.show();");

		}
		
		public int decimalPlacesInfo(){
			int decimalcount;
			if(getRecordingISOCurrencyCode().equals("KWD")){
				decimalcount= 3;
			}else{
				decimalcount = 2;
			}
			return decimalcount;
		}
		
		List<WUTermsAndConditions> termsList = new ArrayList<WUTermsAndConditions>();
		List<WUTCDataTable> tcList = new ArrayList<WUTCDataTable>();
		public void fetchTc(){
			
			WUTCDataTable dtable=null;
			termsList = iwuh2hService.getWUTermsAndConditions();
			for(WUTermsAndConditions wuTermsAndConditions : termsList){
				
				 dtable= new WUTCDataTable();
				 
				 dtable.setLineNo(wuTermsAndConditions.getLineNo());
				 dtable.setEnglishDescription(wuTermsAndConditions.getEnglishMessage());
				 dtable.setArabicDescription(wuTermsAndConditions.getArabicMessage());
				 tcList.add(dtable);
			}
		}

		public List<WUTCDataTable> getTcList() {
			return tcList;
		}

		public void setTcList(List<WUTCDataTable> tcList) {
			this.tcList = tcList;
		}
		
		public void acceptTC(){
			setAcknowledgement(true);
		}
		public void rejectTC(){
			setAcknowledgement(false);
		}
				
		private BigDecimal sendPromoDiscountAmount;

		public BigDecimal getSendPromoDiscountAmount() {
			return sendPromoDiscountAmount;
		}

		public void setSendPromoDiscountAmount(BigDecimal sendPromoDiscountAmount) {
			this.sendPromoDiscountAmount = sendPromoDiscountAmount;
		}		

		public BigDecimal convertLongToBigDecimal(Long value, String currency){			
			BigDecimal convertedValue = new BigDecimal(0);
			Long hundred = new Long("100");			
			int decimalcount;
			if(currency.equals("KWD")){
				decimalcount= 3;
			}else{
				decimalcount = 2;
			}			
			//convertedValue = BigDecimal.valueOf(value==(Long)null ? new Long(0):(double)(value/hundred)).setScale(decimalcount, BigDecimal.ROUND_UP);	
			
			
			BigDecimal bd = BigDecimal.valueOf(value==null ? new Long(0):value);
			
			BigDecimal bd1=bd.movePointLeft(2);
			convertedValue=bd1.setScale(decimalcount);
			return convertedValue;
		}
		
		private String enablePromotion;

		public String getEnablePromotion() {
			return enablePromotion;
		}

		public void setEnablePromotion(String enablePromotion) {
			this.enablePromotion = enablePromotion;
		}
		
		private Promotions promoInfo;
		private PreferredCustomer preferredCustomerInfo;
		private Sender senderInfo;
		private Receiver receiverInfo;
		private Channel channelInfo;
		private ForeignRemoteSystem foreignRemoteSystemInfo;
		private Financials financialsInfo;
		private DeliveryServices deliveryServicesInfo;
		private PaymentDetails paymentDetailsInfo;
		
		private WuCard wuCardInfo;
		public Promotions getPromoInfo() {
			return promoInfo;
		}

		public void setPromoInfo(Promotions promoInfo) {
			this.promoInfo = promoInfo;
		}

		public PreferredCustomer getPreferredCustomerInfo() {
			return preferredCustomerInfo;
		}

		public void setPreferredCustomerInfo(PreferredCustomer preferredCustomerInfo) {
			this.preferredCustomerInfo = preferredCustomerInfo;
		}

		public WuCard getWuCardInfo() {
			return wuCardInfo;
		}

		public void setWuCardInfo(WuCard wuCardInfo) {
			this.wuCardInfo = wuCardInfo;
		}
		
		public Sender getSenderInfo() {
			return senderInfo;
		}

		public void setSenderInfo(Sender senderInfo) {
			this.senderInfo = senderInfo;
		}

		public Receiver getReceiverInfo() {
			return receiverInfo;
		}

		public void setReceiverInfo(Receiver receiverInfo) {
			this.receiverInfo = receiverInfo;
		}

		public Channel getChannelInfo() {
			return channelInfo;
		}

		public void setChannelInfo(Channel channelInfo) {
			this.channelInfo = channelInfo;
		}

		public ForeignRemoteSystem getForeignRemoteSystemInfo() {
			return foreignRemoteSystemInfo;
		}

		public void setForeignRemoteSystemInfo(
				ForeignRemoteSystem foreignRemoteSystemInfo) {
			this.foreignRemoteSystemInfo = foreignRemoteSystemInfo;
		}

		public Financials getFinancialsInfo() {
			return financialsInfo;
		}

		public void setFinancialsInfo(Financials financialsInfo) {
			this.financialsInfo = financialsInfo;
		}

		public DeliveryServices getDeliveryServicesInfo() {
			return deliveryServicesInfo;
		}

		public void setDeliveryServicesInfo(DeliveryServices deliveryServicesInfo) {
			this.deliveryServicesInfo = deliveryServicesInfo;
		}		

		public PaymentDetails getPaymentDetailsInfo() {
			return paymentDetailsInfo;
		}

		public void setPaymentDetailsInfo(PaymentDetails paymentDetailsInfo) {
			this.paymentDetailsInfo = paymentDetailsInfo;
		}	
		
		public String fixedCurrencyCode;

		public String getFixedCurrencyCode() {
			return fixedCurrencyCode;
		}

		public void setFixedCurrencyCode(String fixedCurrencyCode) {
			this.fixedCurrencyCode = fixedCurrencyCode;
		}
		
		private String question;
		private String answer;
		
		private String questionLabel;
		private String answerLabel;
		private String messageLabel;

		public String getQuestion() {
			return question;
		}

		public void setQuestion(String question) {
			this.question = question;
		}

		public String getAnswer() {
			return answer;
		}

		public void setAnswer(String answer) {
			this.answer = answer;
		}
		
		
		
		public String getQuestionLabel() {
			return questionLabel;
		}

		public void setQuestionLabel(String questionLabel) {
			this.questionLabel = questionLabel;
		}

		public String getAnswerLabel() {
			return answerLabel;
		}

		public void setAnswerLabel(String answerLabel) {
			this.answerLabel = answerLabel;
		}

		public String getMessageLabel() {
			return messageLabel;
		}

		public void setMessageLabel(String messageLabel) {
			this.messageLabel = messageLabel;
		}

		private String getCurrencyCode(BigDecimal currencyId)
		{
			List<CurrencyMaster> currencyMasterList = generalService
					.getCurrency(currencyId);
			String currencyCode=null;
			if(currencyMasterList!=null && currencyMasterList.size()==1)
			{
				currencyCode=currencyMasterList.get(0).getIsoCurrencyCode();
			}
			return currencyCode;
		}
		
				
		private String receiverMessage;
		private Boolean securityQuestionAvailable;

		public String getReceiverMessage() {
			return receiverMessage;
		}

		public void setReceiverMessage(String receiverMessage) {
			this.receiverMessage = receiverMessage;
		}

		public Boolean getSecurityQuestionAvailable() {
			return securityQuestionAvailable;
		}

		public void setSecurityQuestionAvailable(Boolean securityQuestionAvailable) {
			this.securityQuestionAvailable = securityQuestionAvailable;
		}
		
		private void clearQuestionAnswer()
		{
			//setReceiverMessage(null);
			setSecurityQuestionAvailable(false);
			setQuestion(null);
			setAnswer(null);
		}
		private void clearQuestionAnswerMessage()
		{
			//setReceiverMessage(null);
			setQuestion(null);
			setAnswer(null);
		}
		
		private BigDecimal reciveDocNo;
		private String customerIDType;

		public String getCustomerIDType() {
			return customerIDType;
		}

		public void setCustomerIDType(String customerIDType) {
			this.customerIDType = customerIDType;
		}

		public BigDecimal getReciveDocNo() {
			return reciveDocNo;
		}

		public void setReciveDocNo(BigDecimal reciveDocNo) {
			this.reciveDocNo = reciveDocNo;
		}
		
		public String customerTelephone;
		public String getCustomerTelephone() {
			return customerTelephone;
		}

		public void setCustomerTelephone(String customerTelephone) {
			this.customerTelephone = customerTelephone;
		}

		public String getCutomerMobileNumber() {
			return cutomerMobileNumber;
		}

		public void setCutomerMobileNumber(String cutomerMobileNumber) {
			this.cutomerMobileNumber = cutomerMobileNumber;
		}

		public String cutomerMobileNumber;
		
		private String wuPurposeTransaction;

		public String getWuPurposeTransaction() {
			return wuPurposeTransaction;
		}

		public void setWuPurposeTransaction(String wuPurposeTransaction) {
			this.wuPurposeTransaction = wuPurposeTransaction;
		}
		
		private String otherPurposeTransaction;
		private boolean otherPurposeEnable;

		public String getOtherPurposeTransaction() {
			return otherPurposeTransaction;
		}

		public void setOtherPurposeTransaction(String otherPurposeTransaction) {
			this.otherPurposeTransaction = otherPurposeTransaction;
		}

		public boolean isOtherPurposeEnable() {
			return otherPurposeEnable;
		}

		public void setOtherPurposeEnable(boolean otherPurposeEnable) {
			this.otherPurposeEnable = otherPurposeEnable;
		}
		
		public void checkOtherPurpose(){
			if(getWuPurposeTransaction().equalsIgnoreCase("others")){
				setOtherPurposeEnable(true);
			}else{
				setOtherPurposeEnable(false);
			}
			
			try {
				if(getTxnType()==1){
					FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hsendmonyconfirm.xhtml");
				}
				if(getTxnType()==2){
					FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hreceivemoneypay.xhtml");
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		private String enrolCountryCode;
		private String enrolCurrencyCode;
		private String enrolCountryName;
		private String enrolCurrencyName;
		private String enrolCityName;
		private String enrolAddress;
		private String enrolContactPhone;

		public String getEnrolCountryCode() {
			return enrolCountryCode;
		}

		public void setEnrolCountryCode(String enrolCountryCode) {
			this.enrolCountryCode = enrolCountryCode;
		}

		public String getEnrolCurrencyCode() {
			return enrolCurrencyCode;
		}

		public void setEnrolCurrencyCode(String enrolCurrencyCode) {
			this.enrolCurrencyCode = enrolCurrencyCode;
		}

		public String getEnrolCountryName() {
			return enrolCountryName;
		}

		public void setEnrolCountryName(String enrolCountryName) {
			this.enrolCountryName = enrolCountryName;
		}

		public String getEnrolCurrencyName() {
			return enrolCurrencyName;
		}

		public void setEnrolCurrencyName(String enrolCurrencyName) {
			this.enrolCurrencyName = enrolCurrencyName;
		}

		public String getEnrolCityName() {
			return enrolCityName;
		}

		public void setEnrolCityName(String enrolCityName) {
			this.enrolCityName = enrolCityName;
		}

		public String getEnrolAddress() {
			return enrolAddress;
		}

		public void setEnrolAddress(String enrolAddress) {
			this.enrolAddress = enrolAddress;
		}

		public String getEnrolContactPhone() {
			return enrolContactPhone;
		}

		public void setEnrolContactPhone(String enrolContactPhone) {
			this.enrolContactPhone = enrolContactPhone;
		}
		
		
		
		private String wuTransReferenceNo;

		public String getWuTransReferenceNo() {
			return wuTransReferenceNo;
		}

		public void setWuTransReferenceNo(String wuTransReferenceNo) {
			this.wuTransReferenceNo = wuTransReferenceNo;
		}
		
		
		private String enableWUEnroll;
		private String enableWUCardLookup;

		public String getEnableWUEnroll() {
			return enableWUEnroll;
		}

		public void setEnableWUEnroll(String enableWUEnroll) {
			this.enableWUEnroll = enableWUEnroll;
		}

		public String getEnableWUCardLookup() {
			return enableWUCardLookup;
		}

		public void setEnableWUCardLookup(String enableWUCardLookup) {
			this.enableWUCardLookup = enableWUCardLookup;
		}
		
		// Move to transaction tables by proedure
		public String wuh2hSendFinalTransaction() throws Exception{
			
			boolean rtnMainSaveSts=false;
			String errormsg = null;
			try{						

				HashMap<String, Object> returnResult = wuh2hSaveCollection();
				TempCollection tempCollection = (TempCollection) returnResult.get("Collect");		
			
				errormsg =wuh2hSaveCollectbyProcedure(tempCollection, collectionId);			
				
			}catch (NullPointerException ne) {
				log.info("Exception in wuh2hSendFinalTransaction:"
						+ ne.getMessage());
				errormsg = "Method Name::wuh2hSendFinalTransaction";
			} catch (Exception exception) {
				errormsg = exception.getMessage();
				log.info("Exception in wuh2hSendFinalTransaction:"
						+ exception.getMessage());
			}			
			return errormsg;
		}
				
		public String wuh2hSaveCollectbyProcedure(TempCollection tempCollection,
				BigDecimal collectionId) throws AMGException {
			String procError = null;

			HashMap<String, Object> returnResult = new HashMap<String, Object>();
			String errormsg = null;
			BigDecimal cFinYear = null;
			BigDecimal collectionNumber = null;
		
			log.info("Parameter 1 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCountryId(): "
					+ sessionStateManage.getCountryId());
			log.info("Parameter 2 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCompanyId(): "
					+ sessionStateManage.getCompanyId());
			log.info("Parameter 3 saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo(): "
					+ getCustomerNo());
			log.info("Parameter 4 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName(): "
					+ sessionStateManage.getUserName());
			log.info("Parameter 5 saveRemittance EX_INSERT_REMITTANCE_TRANX getColremittanceNo(): "
					+ getColremittanceNo());
			log.info("Parameter 6 saveRemittance EX_INSERT_REMITTANCE_TRANX tempCollection.getDocumentCode(): "
					+ tempCollection.getDocumentCode());
			log.info("Parameter 7 saveRemittance EX_INSERT_REMITTANCE_TRANX collectionId: "
					+ collectionId);

			try {
		
				returnResult.put("CountryId", sessionStateManage.getCountryId());
				returnResult.put("CompanyId", sessionStateManage.getCompanyId());
				returnResult.put("CustomerId", getCustomerNo());
				returnResult.put("UserName", sessionStateManage.getUserName());
				returnResult.put("NoofTrnx", BigDecimal.ONE);
				returnResult.put("TempDocCode", tempCollection.getDocumentCode());
				returnResult.put("TempCollectionId", collectionId);

				HashMap<String, Object> outRecord = iwuh2hService
						.saveAllRemittanceTransaction(returnResult);

				collectionNumber = (BigDecimal) outRecord.get("CollectionDocNo");
				cFinYear = (BigDecimal) outRecord.get("CollectionFinYear");
				errormsg = (String) outRecord.get("ErrorMsg");

				log.info("=====================CALLED SAVE ALL");

				log.info("Out1 -----> cFinYear : " + cFinYear);
				log.info("Out2------>collectionNumber : " + collectionNumber);
				log.info("Out3------->errormsg : " + errormsg);

				if (cFinYear == null || collectionNumber == null) {
					String status = null;
					// updating Status "null" in different Tables

					log.info("Exception occured while executing procedure");
					procError = "Exception occured while executing procedure";
					throw new SQLException(
							"Exception occured while executing procedure");
					

				}

				log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo():"
						+ getCustomerNo());
				log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName():"
						+ sessionStateManage.getUserName());
				log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX  getColremittanceNo(): "
						+ getColremittanceNo());
				log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX collectionNumber:"
						+ collectionNumber);
				log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX cFinYear:"
						+ cFinYear);
				log.info("saveRemittance EX_INSERT_REMITTANCE_TRANX errormsg:"
						+ errormsg);

				if (errormsg == null || errormsg.equalsIgnoreCase("")) {
					remittanceNo = new BigDecimal(0);
					fcsaleNo = new BigDecimal(0);
					cashAmount = new BigDecimal(0);
					coldatatablevalues.clear();

					// Icash product Id
					BigDecimal icashBankID = null;
					List<BankMaster> lstICashBankID = generalService
							.getAllBankCodeFromBankMaster("ICASH");

					if (lstICashBankID != null && !lstICashBankID.isEmpty()) {
						BankMaster icashBankRecord = lstICashBankID.get(0);
						icashBankID = icashBankRecord.getBankId();
					}

					// checking ICash and Routing Bank Id
					String checkICASH = iPersonalRemittanceService
							.checkICASHProduct(
									tempCollection.getApplicationCountryId(),
									sessionStateManage.getCompanyId(),
									tempCollection.getDocumentCode(), cFinYear,
									collectionNumber, icashBankID);

					if (checkICASH != null) {
						procError = "INSERT_SERVICE_PIN_JAVA" + " : " + checkICASH;
					} else {
						if (tempCollection != null) {
							iPersonalRemittanceService.insertEMOSLIVETransfer(
									tempCollection.getApplicationCountryId(),
									sessionStateManage.getCompanyId(),
									tempCollection.getDocumentCode(), cFinYear,
									collectionNumber);
						}

					}
					procError = null;
				} else {
					String status = null;
					// updating Status "null" in different Tables

					log.info(" Records Not Saved : " + errormsg);
					setErrorMessage(errormsg);
					procError = errormsg;
				}
			} catch (SQLException e) {
				procError = e.getMessage();
			}
			return procError;
		}
		
		
	private BigDecimal sendTxnCollectionId;
	private String wuCardLevelCode;

	public BigDecimal getSendTxnCollectionId() {
		return sendTxnCollectionId;
	}

	public void setSendTxnCollectionId(BigDecimal sendTxnCollectionId) {
		this.sendTxnCollectionId = sendTxnCollectionId;
	}

	public String getWuCardLevelCode() {
		return wuCardLevelCode;
	}

	public void setWuCardLevelCode(String wuCardLevelCode) {
		this.wuCardLevelCode = wuCardLevelCode;
	}
	
	public void callToWuSendTransactions() {
		log.info("Entering into callToBeneficaryTransactions method");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("customerRefNo", getCustomerrefno());
			session.setAttribute("IdNumber", getIdNumber());
			session.setAttribute("remit", "P");
			session.setAttribute("WUCardno", getSendWUcardNo());
			context.redirect("../wuh2h/wuh2hsendtransactionreprint.xhtml");
		} catch (Exception ex) {
			log.info("Problem in VIEW :" + ex);
			setProcedureError(ex.getMessage());
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
		}
	}
	
	public void callToWuReceiveTransactions() {
		log.info("Entering into callToBeneficaryTransactions method");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("customerRefNo", getCustomerrefno());
			session.setAttribute("IdNumber", getIdNumber());
			session.setAttribute("WUCardno", getSendWUcardNo());
			session.setAttribute("remit", "P");
			context.redirect("../wuh2h/wuh2hreceivetransactionreprint.xhtml");
		} catch (Exception ex) {
			log.info("Problem in VIEW :" + ex);
			setProcedureError(ex.getMessage());
			RequestContext.getCurrentInstance().execute("procedureErr.show();");
		}
	}
	
	
	public void wuh2hPrepareMessage(){		
		//messageDetailList.clear();
		
		if(messageDetailList.size()<=0){
			setMessageCount(1);
			MessageDetail messageDetal = null;
			for(int i=0;i<getMessageCount();i++){
				messageDetal = new MessageDetail();
				
				messageDetal.setWuMessage(messageDetal.getWuMessage());
				messageDetailList.add(messageDetal);
			}
		}
		RequestContext.getCurrentInstance().execute("wumessage.show();");
	}
	
	
	public void addMoreMessage(){
		System.out.println("message line"+messageDetailList.size());
		if(messageDetailList.size()>13){
			setErrorMessage("Sender Message 14 lines only allowed!");
			RequestContext.getCurrentInstance()
					.execute("error.show();");
			return;
		}
		if(messageDetailList.size()>0){
			for(MessageDetail detail:messageDetailList){
				int wordcount = countWords(detail.getWuMessage());
				if(wordcount>15){
					setErrorMessage("Each Sender Message 15 words only allowed!");
					RequestContext.getCurrentInstance()
							.execute("error.show();");
					return;
				}
			}
		}else{
			setErrorMessage("Please enter sender message!");
			RequestContext.getCurrentInstance()
			.execute("error.show();");
			return;
		}
		
		MessageDetail messageDetail = new MessageDetail(getWuMessage());
		messageDetailList.add(messageDetail);
	}
	
	public void removeMessage(MessageDetail msg){
		messageDetailList.remove(msg);
		if(messageDetailList.size()==0){
			try {
				FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../wuh2h/wuh2hmoneyreciverInfo.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void removeAllMessage(){
		
		messageDetailList.clear();
		setSendAmount(null);
		setReceiveAmount(null);
		setSendCommission(null);
		setSendGrossTotalAmount(null);
		setSendOriginPrincipleAmount(null);
		setSendDestPrincipleAmount(null);
		setTransferFee(null);
		setSendCharges(null);
		setSendNetAmountSent(null);
		setSendPayableAmount(null);
		setSendPayableAmountDisplay(null);
		setSendExchangeRate(null);
		setSendDiscountAmount(null);
		setSendPromoDiscountAmount(null);			
		setWuMessageCharge(null);
		try {
			if(getMessageDetailList().size()>0){
				setEnableEditMessage(true);
				setEnableMessage(false);
			}else{
				setEnableEditMessage(false);
				setEnableMessage(true);
			}
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../wuh2h/wuh2hmoneyreciverInfo.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void submitMessage(){		
		System.out.println(messageDetailList.size());
		try {
			
			if(messageDetailList.size()>0){
				for(MessageDetail detail:messageDetailList){
					int wordcount = countWords(detail.getWuMessage());
					if(wordcount>15){
						setErrorMessage("Each Sender Message 15 words only allowed!");
						RequestContext.getCurrentInstance()
								.execute("error.show();");
						return;
					}
				}
			}else{
				setErrorMessage("Please enter sender message!");
				RequestContext.getCurrentInstance()
						.execute("error.show();");
				return;
			}
			if(getMessageDetailList().size()>0){
				setEnableEditMessage(true);
				setEnableMessage(false);
			}else{
				setEnableEditMessage(false);
				setEnableMessage(true);
			}
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../wuh2h/wuh2hmoneyreciverInfo.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<MessageDetail> messageDetailList = new ArrayList<MessageDetail>();

	public List<MessageDetail> getMessageDetailList() {
		return messageDetailList;
	}

	public void setMessageDetailList(List<MessageDetail> messageDetailList) {
		this.messageDetailList = messageDetailList;
	}
	
	private int messageCount;
	private String wuMessage;
	private BigDecimal wuMessageCharge;
	
	public String getWuMessage() {
		return wuMessage;
	}

	public void setWuMessage(String wuMessage) {
		this.wuMessage = wuMessage;
	}

	public int getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	public BigDecimal getWuMessageCharge() {
		return wuMessageCharge;
	}

	public void setWuMessageCharge(BigDecimal wuMessageCharge) {
		this.wuMessageCharge = wuMessageCharge;
	}
	
	public void conformMessageCharge(){
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../wuh2h/wuh2hsendmonyconfirm.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int countWords(String s){

	    int wordCount = 0;

	    boolean word = false;
	    int endOfLine = s.length() - 1;

	    for (int i = 0; i < s.length(); i++) {
	        // if the char is a letter, word = true.
	        if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
	            word = true;
	            // if char isn't a letter and there have been letters before,
	            // counter goes up.
	        } else if (!Character.isLetter(s.charAt(i)) && word) {
	            wordCount++;
	            word = false;
	            // last word of String; if it doesn't end with a non letter, it
	            // wouldn't count without this.
	        } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
	            wordCount++;
	        }
	    }
	    return wordCount;
	}
	public void cancelEnrolment(){
		try {
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../wuh2h/wuh2htransfercustomerinfo.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	 * PAYMENT MODE 
	 * 
	 
	
	public void nextPaymentMode(){
		try {
			afterSendWU();
			
			if(isAcknowledgement()==false){
				setErrorMessage("Please Check Terms & Conditions!");
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
			
			if(getCashAmount().compareTo(getSendPayableAmount())<0){
				RequestContext.getCurrentInstance().execute("amountmatch.show();");
				return;
			}
			
			
			// Call WUH2H send money validate service
			String validateMessage = wuH2HSendMoneyValidate();
			
			if(validateMessage!=null){
				setErrorMessage(validateMessage);
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}
			
			getLocalBankListforIndicator();
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../wuh2h/wuh2hpaymentmode.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			localbankListForBankCode = icustomerBankService
					.getCustomerLocalBankListFromView(
							sessionStateManage.getCountryId(), getColBankCode());

			List<CustomerBank> localBankListinCollection = icustomerBankService
					.fetchcustomerBanksDetails(getCustomerNo(),
							getColBankCode());

			if (localbankListForBankCode.size() != 0) {
				ViewBankDetails bankLength = localbankListForBankCode.get(0);
				cardlength = bankLength.getDebitCardLength();
				System.out.println("cardlength :" + cardlength);
			}

			if (localBankListinCollection.size() != 0) {
				if (cardlength.compareTo(BigDecimal.ZERO) != 0) {
					setColCardNoLength(cardlength);
					if (localBankListinCollection.size() == 1) {
						for (CustomerBank customerBank : localBankListinCollection) {
							if (customerBank.getBankCode().equals(
									getColBankCode())) {
								setPopulatedDebitCardNumber(new BigDecimal(
										encryptionDescriptionService.getDECrypted(
												customerBank.getDebitCardName(),
												customerBank.getDebitCard())));
								setColCardNo(new BigDecimal(
										encryptionDescriptionService.getDECrypted(
												customerBank.getDebitCardName(),
												customerBank.getDebitCard())));
								setColNameofCard(customerBank
										.getDebitCardName());
								if (customerBank.getAuthorizedBy() != null) {
									// List<Employee> localEmpllist =
									// generalService.getEmployeelist(sessionStateManage.getCountryId(),new
									// BigDecimal(sessionStateManage.getBranchId()),new
									// BigDecimal(sessionStateManage.getRoleId()));

									List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService
											.getdebitAutendicationList();

									setEmpllist(localEmpllist);
									setColAuthorizedby(customerBank
											.getAuthorizedBy());
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
								lstofDebitCard
										.setDebitCard(encryptionDescriptionService.getDECrypted(
												lstDebitcrd.getDebitCardName(),
												lstDebitcrd.getDebitCard()));
								*//** Added by Rabil on 13/01/2016 **//*
								lstofDebitCard.setDebitCardName(lstDebitcrd
										.getDebitCardName());
								*//** End by Rabil on 13/01/2016 **//*
								lstDebitCard.add(lstofDebitCard);
							}
						}
					}
				} else {
					RequestContext.getCurrentInstance().execute(
							"bankDebitCardLenErr.show();");
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
						RequestContext.getCurrentInstance().execute(
								"bankDebitCardLenErr.show();");
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

		if (getColNameofCard() != null) {
			String errMsg = validateDebitCard();

			if (errMsg != null) {
				setExceptionMessage(errMsg);
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}

	}

	// card number check calling procedure EX_P_VALIDATE_DEBITCARD
	public String validateDebitCard() {
		String errorMsg = null;
		try {
			HashMap<String, Object> inputValues = new HashMap<String, Object>();
			inputValues.put("P_APPLICATION_COUNTRY_ID",
					sessionStateManage.getCountryId());
			inputValues.put("P_CUSTOMER_ID", getCustomerNo());
			inputValues.put("P_DEBIT_CARD", getColCardNo());
			inputValues.put("P_DB_CARD_NAME", getColNameofCard());
			inputValues.put("P_BANK_CODE", getColBankCode());

			log.info("EX_P_VALIDATE_DEBITCARD INPUT: " + inputValues.toString());

			errorMsg = iPersonalRemittanceService
					.validateDebitCardNo(inputValues);

			log.info("EX_P_VALIDATE_DEBITCARD OUTPUT: " + errorMsg);
		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorMsg;
	}

	// change by payment mode - cash and debit card
	public void changeofPaymentMode() {
		clearDebitDetails();
		List<PaymentModeDesc> lstofPayment = ipaymentService
				.getPaymentDescLangList(new BigDecimal(sessionStateManage
						.isExists("languageId") ? sessionStateManage
						.getSessionValue("languageId") : "1"));
		// Boolean checkCash = false;
		String paymentModedesc = null;
		String paymentModeCode = null;
		setBooColApprovalNo(false);
		if (lstofPayment.size() != 0) {
			for (PaymentModeDesc paymentModeDesc : lstofPayment) {
				if ((getColpaymentmodeId() == null ? BigDecimal.ZERO
						: getColpaymentmodeId()).compareTo(paymentModeDesc
						.getPaymentMode().getPaymentModeId()) == 0) {
					paymentModedesc = paymentModeDesc.getLocalPaymentName();
					paymentModeCode = paymentModeDesc.getPaymentMode()
							.getPaymentCode();
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
				List<PaymentMode> paymentModedetails = ipaymentService
						.getPaymentCheck(getColpaymentmodeCode());

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

					if (getColpaymentmodeCode().equalsIgnoreCase(
							Constants.CashCode)) {
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						setBooRenderColBankTransfer(false);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(
							Constants.KNETCode)) {
						setBooRenderColCheque(false);
						setBooRenderColDebitCard(true);
						setBooRenderColBankTransfer(false);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(
							Constants.ChequeCode)) {
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(true);
						setBooRenderColBankTransfer(false);
					} else if (getColpaymentmodeCode().equalsIgnoreCase(
							Constants.BankTransferCode)) {
						setBooRenderColBankTransfer(true);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
					} else {
						setColpaymentmodeId(null);
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						System.out.println("Payment Mode Newly added");
						RequestContext.getCurrentInstance().execute(
								"checkPaymentModeService.show();");
						return;
					}

				}
			} else {
				setBooRenderColDebitCard(false);
				setBooRenderColCheque(false);
				setBooRenderColBankTransfer(false);
			}

			
			 * if (getColpaymentmodeName() != null &&
			 * getColpaymentmodeName().equalsIgnoreCase(Constants.CHEQUENAME)) {
			 * RequestContext.getCurrentInstance().execute("chequeMsg.show();");
			 * return; }
			 

			// this work only for cash and knet so blocked
			
			 * BigDecimal paymentModeId =
			 * ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,new
			 * BigDecimal(sessionStateManage.isExists("languageId") ?
			 * sessionStateManage.getSessionValue("languageId") : "1")); if
			 * (paymentModeId != null) { if ((getColpaymentmodeId() == null ?
			 * new BigDecimal(0) :
			 * getColpaymentmodeId()).compareTo(paymentModeId) == 0) { checkCash
			 * = true; } else { checkCash = false; } if (checkCash) {
			 * setBooRenderColDebitCard(false); setColBankCode(null);
			 * setColCardNo(null); setPopulatedDebitCardNumber(null);
			 * setColCash(null); setColAuthorizedby(null); setColpassword(null);
			 * setColNameofCard(null); setColApprovalNo(null); } else {
			 * setColCash(null); setBooRenderColDebitCard(true); } }
			 
		}

	}
		
	public void clearDebitDetails() {
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
		
	public void clearDebitPrefixDetails() {
		setSingleDebitCardPrefex(null);
		setDebitCardNo(null);
		setColCardNo(null);
		setDoubleDebitCardNo(null);
		setColNameofCard(null);
		setColApprovalNo(null);
		setColAuthorizedby(null);
		setBooAuthozed(false);
	}

	public void clearDebit() {
		setDebitCardNo(null);
		setColCardNo(null);
		setDoubleDebitCardNo(null);
		setColNameofCard(null);
		setColApprovalNo(null);
		setColAuthorizedby(null);
		setBooAuthozed(false);
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

		// clearBank();

		if (getColBankCode() != null) {
			localbankListForBankCode = icustomerBankService
					.getCustomerLocalBankListFromView(
							sessionStateManage.getCountryId(), getColBankCode());
			List<ViewBankDetails> lstViewBankDetails = icustomerBankService
					.getCustomerLocalBankListFromView(
							sessionStateManage.getCountryId(), getColBankCode());
			if (lstViewBankDetails != null && lstViewBankDetails.size() != 0) {
				ViewBankDetails viewBankDetails = lstViewBankDetails.get(0);
				List<BankPrefix> lstBankPrefix = icustomerBankService
						.getBankPrefix(getColBankCode(),
								viewBankDetails.getChequeBankId());

				if (lstBankPrefix != null) {
					cardlength = viewBankDetails.getDebitCardLength()
							.intValue();
					setDebitCardNoLength(viewBankDetails.getDebitCardLength());
					setColCardNoLength(viewBankDetails.getDebitCardLength());
					if (lstBankPrefix.size() == 1) {
						setBooRenderSingleeCardPrefix(true);
						setBooRenderMultipleCardPrefix(false);
						setSingleDebitCardPrefex(lstBankPrefix.get(0)
								.getBankPrefix());
						int prefixLength = getSingleDebitCardPrefex().length();

						setColCardNoLength(new BigDecimal(cardlength
								- prefixLength));
						populateDebitcardNumbers();
					}
					if (lstBankPrefix.size() > 1) {
						setBooRenderSingleeCardPrefix(false);
						setBooRenderMultipleCardPrefix(true);
						setLstBankPrefix(lstBankPrefix);
					}
					if (lstBankPrefix.size() == 0) {
						RequestContext.getCurrentInstance().execute(
								"noBankPrefix.show();");
						return;
					}
				} else {
					setBooRenderSingleeCardPrefix(true);
					setBooRenderMultipleCardPrefix(false);
					RequestContext.getCurrentInstance().execute(
							"noBankPrefix.show();");
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
				setExceptionMessage("This Card is already registered under customer "+nullCheck(firstName) + " " + nullCheck(secondName) + " " + nullCheck(thirdName) +
						" with Civil Id "+customerIdProof.getIdentityInt());
				+" ID type " + customerIdProof.getFsBizComponentDataByIdentityTypeId().getFsBusinessComponent().getComponentName());
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
				return;
			}

		}
	}
	public void populateDebitcardNumbers() {
		setBooRenderSingleDebit(false);
		setBooRenderMulDebit(false);
		setDebitCardLst(null);

		setDebitCardNo(null);
		setDoubleDebitCardNo(null);
		setColCardNo(null);
		// localbankListForBankCode =
		// icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(),
		// getColBankCode());
		List<CustomerBank> lstCustomerBank = icustomerBankService
				.fetchcustomerBanksDetails(getCustomerNo(), getColBankCode());

		if (lstCustomerBank != null && lstCustomerBank.size() != 0) {
			List<String> lstDebitCard = new ArrayList<String>();
			String bankPrefix = null;
			for (CustomerBank customerBank : lstCustomerBank) {

				String debitCardNo = encryptionDescriptionService.getDECrypted(
						Constants.EncriptionKey, customerBank.getDebitCard());

				if (getBooRenderMultipleCardPrefix()
						&& (getLstBankPrefix() != null && getLstBankPrefix()
								.size() > 0)) {
					bankPrefix = getColDebitCardPrefex();
				} else {
					bankPrefix = getSingleDebitCardPrefex();
				}

				if (bankPrefix != null) {
					String debitCardBankPrefix = debitCardNo.substring(0,
							bankPrefix.length());

					if (debitCardBankPrefix.equalsIgnoreCase(bankPrefix)) {
						String debitCard = debitCardNo.substring(
								bankPrefix.length(), debitCardNo.length());

						lstDebitCard.add(debitCard);
					}

					
					 * if(debitCardNo.contains(bankPrefix)) { String
					 * debitCard=debitCardNo
					 * .substring(bankPrefix.length(),debitCardNo.length());
					 * 
					 * lstDebitCard.add(debitCard); }
					 
				}
			}

			if (lstDebitCard != null && lstDebitCard.size() == 0) {
				setBooRenderSingleDebit(true);
				setBooRenderMulDebit(false);
			} else if (lstDebitCard != null && lstDebitCard.size() == 1) {
				setBooRenderSingleDebit(true);
				setBooRenderMulDebit(false);

				setDebitCardNo(lstDebitCard.get(0));
				setDoubleDebitCardNo(bankPrefix + lstDebitCard.get(0));
				setColCardNo(new BigDecimal(bankPrefix + lstDebitCard.get(0)));
				// populateCustomerDetails();

				BigDecimal rtnCustomerId = checkDebitcardRegistered();
				if (rtnCustomerId.compareTo(BigDecimal.ZERO) != 0) {
					List<CustomerIdProof> lstCustomerIdProof = icustomerBankService
							.getCustomerBasedOnId(rtnCustomerId);
					if (lstCustomerIdProof.size() != 0) {
						CustomerIdProof customerIdProof = lstCustomerIdProof
								.get(0);
						String firstName = customerIdProof.getFsCustomer()
								.getFirstName();
						String secondName = customerIdProof.getFsCustomer()
								.getMiddleName();
						String thirdName = customerIdProof.getFsCustomer()
								.getLastName();
						clearDebit();
						setExceptionMessage("This Card is already registered under customer "
								+ nullCheck(firstName)
								+ " "
								+ nullCheck(secondName)
								+ " "
								+ nullCheck(thirdName)
								+ " with Civil Id "
								+ customerIdProof.getIdentityInt());
						
						 * +" ID type " +
						 * customerIdProof.getFsBizComponentDataByIdentityTypeId
						 * ().getFsBusinessComponent().getComponentName());
						 
						RequestContext.getCurrentInstance().execute(
								"alertmsg.show();");
						return;
					}
				}
				populateCustomerNameAuthDetails();

			} else if (lstDebitCard != null && lstDebitCard.size() > 1) {
				setBooRenderSingleDebit(false);
				setBooRenderMulDebit(true);
				setDebitCardLst(lstDebitCard);
			}

		} else {
			setBooRenderSingleDebit(true);
			setBooRenderMulDebit(false);
		}
	}

	public void populateCustomerNameAuthDetails() {
		String bankPrefix = null;
		String bankDebiCardNo = null;
		setPopulatedDebitCardNumber(null);
		setColCardNo(null);
		if (getBooRenderMultipleCardPrefix()
				&& (getLstBankPrefix() != null && getLstBankPrefix().size() > 0)) {
			bankPrefix = getColDebitCardPrefex();
		} else {
			bankPrefix = getSingleDebitCardPrefex();
		}

		if (getBooRenderMulDebit()
				&& (getDoubleDebitCardNo() != null && getDebitCardLst().size() > 0)) {
			bankDebiCardNo = getDoubleDebitCardNo().toString();
		} else {
			bankDebiCardNo = getDebitCardNo();
		}
		String selectedDebidCard = bankPrefix + bankDebiCardNo;
		setColCardNo(new BigDecimal(selectedDebidCard));
		List<CustomerBank> localBankListinCollection = icustomerBankService
				.fetchcustomerBanksDetails(getCustomerNo(), getColBankCode());
		if (localBankListinCollection != null
				&& localBankListinCollection.size() > 0) {
			for (CustomerBank customerBank : localBankListinCollection) {
				if (customerBank.getBankCode().equals(getColBankCode())) {
					String localDebiCardNo = encryptionDescriptionService
							.getDECrypted(Constants.EncriptionKey,
									customerBank.getDebitCard());
					setPopulatedDebitCardNumber(new BigDecimal(
							encryptionDescriptionService.getDECrypted(
									Constants.EncriptionKey,
									customerBank.getDebitCard())));
					if (selectedDebidCard.equalsIgnoreCase(localDebiCardNo)) {
						setColNameofCard(customerBank.getDebitCardName());
						if (customerBank.getAuthorizedBy() != null) {
							// List<Employee> localEmpllist =
							// generalService.getEmployeelist(sessionStateManage.getCountryId(),new
							// BigDecimal(sessionStateManage.getBranchId()),new
							// BigDecimal(sessionStateManage.getRoleId()));

							List<DebitAutendicationView> localEmpllist = iPersonalRemittanceService
									.getdebitAutendicationList();

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

				} else {
					setColAuthorizedby(null);
					setColpassword(null);
					setBooAuthozed(false);
				}
			}

			lstDebitCard.clear();
		}
		 * else {
		 * 
		 * System.out.println("cardlength :" + cardlength);
		 * 
		 * if (cardlength != null) {
		 * 
		 * if (cardlength.compareTo(BigDecimal.ZERO) != 0) {
		 * setColCardNoLength(cardlength); lstDebitCard.clear();
		 * setColAuthorizedby(null); setColCardNo(null);
		 * setPopulatedDebitCardNumber(null); setColNameofCard(null);
		 * setColpassword(null); setColApprovalNo(null); setBooAuthozed(false);
		 * setBooRenderSingleDebit(true); setBooRenderMulDebit(false); } else {
		 * RequestContext
		 * .getCurrentInstance().execute("bankDebitCardLenErr.show();"); } } }
		 

	}

	public BigDecimal checkDebitcardRegistered() {
		String prefix = null;
		if (getBooRenderMultipleCardPrefix()) {
			prefix = getColDebitCardPrefex();
		}

		if (getBooRenderSingleeCardPrefix()) {
			prefix = getSingleDebitCardPrefex();
		}

		String suffix = getColCardNo().toPlainString().substring(12);

		BigDecimal bankId = null;
		List<ViewBankDetails> lstViewBankDetails = icustomerBankService
				.getChequeBnakIdFromView(getColBankCode());
		if (lstViewBankDetails != null && lstViewBankDetails.size() > 0) {
			ViewBankDetails viewBankDetails = lstViewBankDetails.get(0);
			bankId = viewBankDetails.getChequeBankId();
		}

		List<BigDecimal> lstCustomerId = icustomerBankService
				.checkDebitCardWithActiveStatus(getColCardNo().toString(),
						prefix, suffix, bankId);

		BigDecimal rtnCustomerId = BigDecimal.ZERO;
		if (lstCustomerId != null) {
			for (BigDecimal customerId : lstCustomerId) {
				if (getCustomerNo().compareTo(customerId) != 0) {
					rtnCustomerId = customerId;
				}
			}
		}
		return rtnCustomerId;
	}
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

	public void verifyPassword() {

		checkcashcollection();
		boolean isCashAvail=false; 
		if (!checkKnetAmount) {

			String errorMessage;
			try {
				errorMessage = iPersonalRemittanceService
						.getExCheckCashLimitProcedure(
								sessionStateManage.getCountryId(),
								getCustomerNo(), getColpaymentmodeId(),
								getColamountKWD());
				System.out.println("errorMessage :" + errorMessage);
				if (errorMessage != null && !errorMessage.equals("")) {
					setExcheckCashLimitMessage(errorMessage);
					RequestContext.getCurrentInstance().execute(
							"exCheckCashLimit.show();");
				} else {
					setExcheckCashLimitMessage(null);
					
					
					
					if (getColpaymentmodeCode() != null) {
						List<PaymentMode> paymentModedetails = ipaymentService
								.getPaymentCheck(getColpaymentmodeCode());

						if (paymentModedetails.size() != 0) {
							
							if (getColpaymentmodeCode().equalsIgnoreCase(
									Constants.CashCode)) {
								calculatingNetAmountDT();
								isCashAvail=true;
							} else if (getColpaymentmodeCode()
									.equalsIgnoreCase(Constants.KNETCode)) {
								if (getColAuthorizedby() != null) {
									List<DebitAutendicationView> lstEmpLogin = new ArrayList<DebitAutendicationView>();
									String userNames = getColAuthorizedby();
									
									 * String authorname = new
									 * StringBuffer(userNames)
									 * .reverse().toString().toUpperCase();
									 * lstEmpLogin
									 * .addAll(loginService.getLoginInfoForEmployees
									 * (sessionStateManage.getCountryId(),
									 * getColAuthorizedby
									 * (),cypherSecurity.encodePassword
									 * (getColpassword(), authorname)));
									 

									lstEmpLogin = iPersonalRemittanceService
											.getdebitAutendicationListByUserId(
													getColAuthorizedby(),
													getColpassword());

									if (lstEmpLogin.size() != 0) {
										
										 * String secretKey =
										 * getColpassword().toUpperCase();
										 * StringBuffer secretKeys = new
										 * StringBuffer(secretKey).reverse();
										 * String cyperpassword =
										 * cypherSecurity2
										 * .encodePassword(getColpassword
										 * (),secretKeys.toString());
										 
										// setColpassword(getColpassword());
										// setCyberPassword(cyperpassword);
										checkingPaymentCardinDB();
									} else {
										setColpassword(null);
										RequestContext
												.getCurrentInstance()
												.execute(
														"passwordcheck.show();");
									}
								} else {
									checkingPaymentCardinDB();
								}

							} else if (getColpaymentmodeCode()
									.equalsIgnoreCase(Constants.ChequeCode)) {

								// Boolean checkdata =
								// checkingChequeDuplicateCheck();
								// blocking duplicate condition for Cheque based
								// on user Requirement
								Boolean checkdata = Boolean.TRUE;
								if (checkdata) {
									localbankListForBankCode = icustomerBankService
											.getCustomerLocalBankListFromView(
													sessionStateManage
															.getCountryId(),
													getColchequebankCode());
									addPaymentModerecord();
									if (coldatatablevalues.size() > 0) {
										for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
											totalUamount = totalUamount
													.add(collectionlst
															.getColAmountDT());
										}
										setToalUsedAmount(totalUamount);
										if (getToalUsedAmount().compareTo(
												getCalNetAmountPaid()) > 0) {
											setTotalbalanceAmount(BigDecimal.ZERO);
											setTotalrefundAmount(getToalUsedAmount()
													.subtract(
															getCalNetAmountPaid()));
										} else if (getToalUsedAmount()
												.compareTo(
														getCalNetAmountPaid()) < 0) {
											setTotalbalanceAmount(getCalNetAmountPaid()
													.subtract(
															getToalUsedAmount()));
											setTotalrefundAmount(BigDecimal.ZERO);
										} else {
											setTotalbalanceAmount(BigDecimal.ZERO);
											setTotalrefundAmount(BigDecimal.ZERO);
										}

									}
									setBooRendercollectiondatatable(true);
									clearingDetailAfterAdding();
								} else {
									RequestContext.getCurrentInstance()
											.execute("chequerefexists.show();");
								}
							} else if (getColpaymentmodeCode()
									.equalsIgnoreCase(
											Constants.BankTransferCode)) {
								// Boolean checkBT =
								// checkingBankTransferDuplicateCheck();
								// blocking duplicate condition for bank
								// Transfer based on user Requirement
								Boolean checkBT = Boolean.TRUE;
								if (checkBT) {
									localbankListForBankCode = icustomerBankService
											.getCustomerLocalBankListFromView(
													sessionStateManage
															.getCountryId(),
													getColBankTransferBankCode());
									addPaymentModerecord();
									if (coldatatablevalues.size() > 0) {
										for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
											totalUamount = totalUamount
													.add(collectionlst
															.getColAmountDT());
										}
										setToalUsedAmount(totalUamount);
										if (getToalUsedAmount().compareTo(
												getCalNetAmountPaid()) > 0) {
											setTotalbalanceAmount(BigDecimal.ZERO);
											setTotalrefundAmount(getToalUsedAmount()
													.subtract(
															getCalNetAmountPaid()));
										} else if (getToalUsedAmount()
												.compareTo(
														getCalNetAmountPaid()) < 0) {
											setTotalbalanceAmount(getCalNetAmountPaid()
													.subtract(
															getToalUsedAmount()));
											setTotalrefundAmount(BigDecimal.ZERO);
										} else {
											setTotalbalanceAmount(BigDecimal.ZERO);
											setTotalrefundAmount(BigDecimal.ZERO);
										}

									}
									setBooRendercollectiondatatable(true);
									clearingDetailAfterAdding();
								} else {
									setErrmsg("Bank Transfer Payment Mode Already Exists in DataTable");
									log.info("Bank Transfer Payment Mode Already Exists in DataTable");
									RequestContext.getCurrentInstance()
											.execute("warningmsg.show();");
									return;
								}
							} else {
								System.out.println("Other Payment Mode");
							}

						}
						
						
					} else {
						setBooRenderColDebitCard(false);
						setBooRenderColCheque(false);
						setBooRenderColBankTransfer(false);
					}
				}
				
				if(isCashAvail==true){
					  setPaymentModeSaveBtn(Constants.Next); 
				}else{
					  setPaymentModeSaveBtn(Constants.Save); 
				}
					 
			} catch (AMGException e) {
				setExceptionMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute(
						"sqlexception.show();");
			}
		}

	}

	// adding to datatable list in payment mode
	public void addPaymentModerecord() {
		BigDecimal paymentModeCashId = ipaymentService
				.fetchPaymodeMasterId(
						Constants.CASHNAME,
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			totalUamount = new BigDecimal(0);
			PersonalRemittanceCollectionDataTable personalcollectionDT = new PersonalRemittanceCollectionDataTable();
			personalcollectionDT
					.setColpaymentmodeIDtypeDT(getColpaymentmodeId());
			personalcollectionDT
					.setColpaymentmodetypeDT(getColpaymentmodeName());
			personalcollectionDT.setColpaymentmodeCode(getColpaymentmodeCode());
			if (getColBankCode() != null || getColchequebankCode() != null
					|| getColBankTransferBankCode() != null) {
				if (localbankListForBankCode.size() != 0) {
					ViewBankDetails lstDetaiks = localbankListForBankCode
							.get(0);
					personalcollectionDT.setColBankIdDT(lstDetaiks
							.getChequeBankId());
					personalcollectionDT.setColbankNameDT(lstDetaiks
							.getBankFullName());

					if (getColChequeRef() != null
							&& getColchequebankCode() != null) {
						personalcollectionDT
								.setColBankCodeDT(getColchequebankCode());
						personalcollectionDT
								.setColchequeRefNo(getColChequeRef());
						personalcollectionDT
								.setColchequeDate(getColChequeDate());
						personalcollectionDT
								.setColApprovalNo(getColChequeApprovalNo());
					} else if (getColBankCode() != null) {
						personalcollectionDT.setColBankCodeDT(getColBankCode());
						personalcollectionDT.setColCardNumberDT(getColCardNo());
						if(getColCardNo()!=null)
						{	
							personalcollectionDT
							.setMaskCardNumberDT(maskCCNumber(getColCardNo()==null ?"":	getColCardNo().toString()));
						}
						
						personalcollectionDT
								.setColNameofCardDT(getColNameofCard());
						personalcollectionDT
								.setColAuthorizedByDT(getColAuthorizedby());
						personalcollectionDT
								.setColApprovalNo(getColApprovalNo());
						personalcollectionDT
								.setKnetReceiptDT(getKnetIposReceipt());
						personalcollectionDT.setKnetTransIdDT(getKnetTranId());
						personalcollectionDT
								.setKneRceiptTimeDT(getKnetReceiptDate());
						personalcollectionDT.setBooDisbale(false);
					} else if (getColBankTransferBankCode() != null) {
						personalcollectionDT
								.setColBankCodeDT(getColBankTransferBankCode());
					}
				}
			}
			personalcollectionDT.setColAmountDT(GetRound.roundBigDecimal(
					getColCash(), foreignLocalCurrencyDenominationService
							.getDecimalPerCurrency(new BigDecimal(
									sessionStateManage.getCurrencyId()))));
			if (getColpaymentmodeId().compareTo(paymentModeCashId) == 0) {
				setTempCash(GetRound.roundBigDecimal(getColCash(),
						foreignLocalCurrencyDenominationService
								.getDecimalPerCurrency(new BigDecimal(
										sessionStateManage.getCurrencyId()))));
			}
			
			coldatatablevalues.add(personalcollectionDT);
		}

		setBooRenderSingleDebit(true);
		setBooRenderMulDebit(false);
		lstDebitCard.clear();
		setColCardNo(null);
	}

	public String maskCCNumber(String ccnum) {
		long starttime = System.currentTimeMillis();
		int total = ccnum.length();
		int startlen = 6, endlen = 4;
		int masklen = total - (startlen + endlen);
		StringBuffer maskedbuf = new StringBuffer(ccnum.substring(0, startlen));
		for (int i = 0; i < masklen; i++) {
			maskedbuf.append('*');
		}
		maskedbuf.append(ccnum.substring(startlen + masklen, total));
		String masked = maskedbuf.toString();
		long endtime = System.currentTimeMillis();

		return masked;
	}

	public void checkingPaymentCardinDB() {
		if (lstDebitCard.size() != 0) {
			int i = 0;
			for (CustomerBank lstDebit : lstDebitCard) {
				if (lstDebit.getDebitCard().equalsIgnoreCase(
						getColCardNo().toString())) {
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
		
	// to add values to data table in collection
	public void calculatingNetAmountDT() {
		int i = 0;
		BigDecimal paymentModeCashId = ipaymentService
				.fetchPaymodeMasterId(
						Constants.CASHNAME,
						new BigDecimal(sessionStateManage
								.isExists("languageId") ? sessionStateManage
								.getSessionValue("languageId") : "1"));
		if (paymentModeCashId != null) {
			boolean flag = true;
			setBooRendercollectiondatatable(true);
			// setBoRenderTotalAmountCalPanel(true);
			if (coldatatablevalues.size() != 0) {
				for (PersonalRemittanceCollectionDataTable collectionlst : coldatatablevalues) {
					i = 0;
					if (collectionlst.getColpaymentmodeIDtypeDT().compareTo(
							(getColpaymentmodeId() == null ? new BigDecimal(0)
									: getColpaymentmodeId())) == 0) {
						if (collectionlst.getColpaymentmodeIDtypeDT()
								.compareTo(paymentModeCashId) == 0) {
							clearingDetailAfterAdding();
							RequestContext.getCurrentInstance().execute(
									"cashexists.show();");
							flag = false;
							break;
						} else {
							if (collectionlst.getColBankCodeDT().compareTo(
									getColBankCode()) == 0) {
								if (collectionlst.getColCardNumberDT()
										.compareTo(getColCardNo()) == 0) {
									if (collectionlst.getColApprovalNo()
											.compareTo(getColApprovalNo()) == 0) {
										clearingDetailAfterAdding();
										RequestContext.getCurrentInstance()
												.execute("bankexists.show();");
										flag = false;
										break;
									} else {
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
					totalUamount = totalUamount.add(collectionlst
							.getColAmountDT());
				}
				setToalUsedAmount(totalUamount);
				if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) > 0) {
					setTotalbalanceAmount(BigDecimal.ZERO);
					setTotalrefundAmount(getToalUsedAmount().subtract(
							getCalNetAmountPaid()));
				} else if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) < 0) {
					setTotalbalanceAmount(getCalNetAmountPaid().subtract(
							getToalUsedAmount()));
					setTotalrefundAmount(BigDecimal.ZERO);
				} else {
					setTotalbalanceAmount(BigDecimal.ZERO);
					setTotalrefundAmount(BigDecimal.ZERO);
				}
			}

			clearingDetailAfterAdding();
		}
	}

	public void editRecord(
			PersonalRemittanceCollectionDataTable personalRemitObj) {

		if (coldatatablevalues.size() > 0) {
			subtractedAmount = getToalUsedAmount().subtract(
					personalRemitObj.getColAmountDT());
			setToalUsedAmount(subtractedAmount);
			if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) > 0) {
				setTotalbalanceAmount(BigDecimal.ZERO);
				setTotalrefundAmount(getToalUsedAmount().subtract(
						getCalNetAmountPaid()));
			} else if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) < 0) {
				setTotalbalanceAmount(getCalNetAmountPaid().subtract(
						getToalUsedAmount()));
				setTotalrefundAmount(BigDecimal.ZERO);
			} else {
				setTotalbalanceAmount(BigDecimal.ZERO);
				setTotalrefundAmount(BigDecimal.ZERO);
			}
		} else {
			setToalUsedAmount(null);
			setTotalbalanceAmount(null);
			setTotalrefundAmount(null);
		}

		if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(
				Constants.CashCode)) {
			setBooRenderColDebitCard(false);
			setBooRenderColCheque(false);
			setBooRenderColBankTransfer(false);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColCash(personalRemitObj.getColAmountDT());
			coldatatablevalues.remove(personalRemitObj);
		} else if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(
				Constants.KNETCode)) {
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
		} else if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(
				Constants.ChequeCode)) {
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
		} else if (personalRemitObj.getColpaymentmodeCode().equalsIgnoreCase(
				Constants.BankTransferCode)) {
			setBooRenderColDebitCard(false);
			setBooRenderColCheque(false);
			setBooRenderColBankTransfer(true);
			setColpaymentmodeId(personalRemitObj.getColpaymentmodeIDtypeDT());
			setColpaymentmodeName(personalRemitObj.getColpaymentmodetypeDT());
			setColpaymentmodeCode(personalRemitObj.getColpaymentmodeCode());
			setColBankTransferBankCode(personalRemitObj.getColBankCodeDT());
			// setColChequeRef(personalRemitObj.getColchequeRefNo());
			// setColChequeDate(personalRemitObj.getColchequeDate());
			setColCash(personalRemitObj.getColAmountDT());
			// setColChequeApprovalNo(personalRemitObj.getColApprovalNo());
			coldatatablevalues.remove(personalRemitObj);
		} else {
			System.out.println("Other Payment mode");
		}

		if (coldatatablevalues.size() != 0) {
			setBooRendercollectiondatatable(true);
		} else {
			setBooRendercollectiondatatable(false);
		}

	}

	// to remove details from data table after adding
	public void deletePaymentModeRecords(
			PersonalRemittanceCollectionDataTable collectionDt) {

		if (coldatatablevalues.size() > 0) {
			subtractedAmount = getToalUsedAmount().subtract(
					collectionDt.getColAmountDT());
			setToalUsedAmount(subtractedAmount);
			if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) > 0) {
				setTotalbalanceAmount(BigDecimal.ZERO);
				setTotalrefundAmount(getToalUsedAmount().subtract(
						getCalNetAmountPaid()));
			} else if (getToalUsedAmount().compareTo(getCalNetAmountPaid()) < 0) {
				setTotalbalanceAmount(getCalNetAmountPaid().subtract(
						getToalUsedAmount()));
				setTotalrefundAmount(BigDecimal.ZERO);
			} else {
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
			// setBoRenderTotalAmountCalPanel(true);
		} else {
			setBooRendercollectiondatatable(false);
			// setBoRenderTotalAmountCalPanel(false);
			clearingDetailAfterAdding();
		}
	}

	*//** TO STORE THE NEW BENIFICARY CUSTOMER DETAILS TO CUSTOMER BANK **//*
	public void saveCustomerDetailsToCustomerBank() {

		CustomerBank customerBank = new CustomerBank();

		customerBank.setCollectionMode(Constants.COLLECTIONMODE);

		List<ViewBankDetails> lstViewBankDetails = icustomerBankService
				.getChequeBnakIdFromView(getColBankCode());

		if (lstViewBankDetails.size() != 0) {
			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(lstViewBankDetails.get(0).getChequeBankId());
			customerBank.setFsBankMaster(bankMaster);
		}

		customerBank.setBankCode(getColBankCode()); // this is
													// fixed//generalService.getBankCode(getColBankid()));

		if (getCustomerNo() != null) {
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerNo());
			customerBank.setFsCustomer(customer);
		}

		if (getColAuthorizedby() != null) {
			customerBank.setAuthorizedBy(getColAuthorizedby());
			customerBank.setAuthorizedDate(new Date());
			customerBank.setPassword(getCyberPassword());
		}

		customerBank.setDebitCard(encryptionDescriptionService.getENCrypted(
				getColNameofCard(), getColCardNo().toString()));

		String prefix = getColCardNo().toPlainString().substring(0, 6);
		customerBank.setBankPrefix(prefix);

		String lastFour = getColCardNo().toPlainString().substring(12);
		customerBank.setBankSuffix(lastFour);

		customerBank.setDebitCardName(getColNameofCard());
		customerBank.setIsActive(Constants.Yes);
		customerBank.setCreatedBy(sessionStateManage.getUserName());
		customerBank.setCreatedDate(new Date());
		customerBank.setCustomerReference(iglTransactionForADocument
				.getCustomeReference(getCustomerNo()));
		// customerBank.setModifiedBy(null);
		// customerBank.setModifiedDate(null);
		icustomerBankService.save(customerBank);
		calculatingNetAmountDT();
		// RequestContext.getCurrentInstance().execute("locbankid.show();");
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
		// clear cheque details
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

		lstDebitCard.clear();
		changeofPaymentMode();

	}	
		
	// Extra Variables without Getters and Setters
	private BigDecimal tempCalGrossAmount = new BigDecimal(0);
	private BigDecimal tempCalNetAmountPaid = new BigDecimal(0);
	private BigDecimal totalUamount = new BigDecimal(0);
	private BigDecimal subtractedAmount = new BigDecimal(0);

	// to get the local bank list or customer bank list
	public void getLocalBankListforIndicator() {
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<BigDecimal> duplicateCheck1 = new ArrayList<BigDecimal>();
		List<ViewBankDetails> lstofBank = new ArrayList<ViewBankDetails>();
		List<ViewBankDetails> lstofBank1 = new ArrayList<ViewBankDetails>();
		bankMasterList.clear();
		chequebankMasterList.clear();

		localbankList = generalService
				.getLocalBankListFromView(sessionStateManage.getCountryId());

		// cheque banks purpose
		if (localbankList.size() != 0) {
			chequebankMasterList.addAll(localbankList);
		}

		// lstoflocalbank =
		// generalService.getLocalBankList(sessionmanage.getCountryId());
		List<ViewBankDetails> localBankListinCollection = icustomerBankService
				.customerBanks(getCustomerNo(), getColBankCode());
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

	private boolean booeanbooColApprovalNo;
	private boolean booColApprovalNo;

	private String paymentModeSaveBtn;
	private String colBankTransferBankCode;
	private boolean booRenderColBankTransfer = false;

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
	private String cashRounding;
	private Boolean booRenderSingleDebit = true;
	private Boolean booRenderMulDebit = false;

	// Debit card changes
	private BigDecimal debitCardNoLength;
	private Boolean booRenderMultipleCardPrefix;
	private Boolean booRenderSingleeCardPrefix;
	private List<BankPrefix> lstBankPrefix;
	private String singleDebitCardPrefex;
	private String colDebitCardPrefex;
	private String debitCardNo;
	private String doubleDebitCardNo;

	private List<String> debitCardLst;

	private String excheckCashLimitMessage = null;

	// Eight and Ninth Panel Lists
	private List<ViewBankDetails> bankMasterList = new CopyOnWriteArrayList<ViewBankDetails>();
	private List<ViewBankDetails> chequebankMasterList = new ArrayList<ViewBankDetails>();
	private List<ViewBankDetails> localbankList = new ArrayList<ViewBankDetails>();
	private List<CustomerBank> lstDebitCard = new ArrayList<CustomerBank>();
	private List<DebitAutendicationView> empllist = new ArrayList<DebitAutendicationView>();
	private List<DebitAutendicationView> emplAutenticationlist = new ArrayList<DebitAutendicationView>();
	private List<ViewBankDetails> localbankListForBankCode = new ArrayList<ViewBankDetails>();
	private List<FcSaleReport> fcSaleReportDTOList = null;

	private List<ViewSubAgent> lstIcashSubAgents = new ArrayList<ViewSubAgent>();
	private List<ViewHODirectEFT> lstIcashAgentsEFTDetails = new ArrayList<ViewHODirectEFT>();
	private List<ViewHODirectInDirect> lstIcashAgentsTTDetails = new ArrayList<ViewHODirectInDirect>();
	private List<ViewStatesForICASH> lstIcashState = new ArrayList<ViewStatesForICASH>();

	private boolean booAuthozed;

	public boolean isBooAuthozed() {
		return booAuthozed;
	}

	public void setBooAuthozed(boolean booAuthozed) {
		this.booAuthozed = booAuthozed;
	}

	public List<ViewBankDetails> getBankMasterList() {
		return bankMasterList;
	}

	public void setBankMasterList(List<ViewBankDetails> bankMasterList) {
		this.bankMasterList = bankMasterList;
	}

	public List<ViewBankDetails> getChequebankMasterList() {
		return chequebankMasterList;
	}

	public void setChequebankMasterList(
			List<ViewBankDetails> chequebankMasterList) {
		this.chequebankMasterList = chequebankMasterList;
	}

	public List<ViewBankDetails> getLocalbankList() {
		return localbankList;
	}

	public void setLocalbankList(List<ViewBankDetails> localbankList) {
		this.localbankList = localbankList;
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

	public void setEmplAutenticationlist(
			List<DebitAutendicationView> emplAutenticationlist) {
		this.emplAutenticationlist = emplAutenticationlist;
	}

	public List<ViewBankDetails> getLocalbankListForBankCode() {
		return localbankListForBankCode;
	}

	public void setLocalbankListForBankCode(
			List<ViewBankDetails> localbankListForBankCode) {
		this.localbankListForBankCode = localbankListForBankCode;
	}

	public String getPaymentModeSaveBtn() {
		return paymentModeSaveBtn;
	}

	public void setPaymentModeSaveBtn(String paymentModeSaveBtn) {
		this.paymentModeSaveBtn = paymentModeSaveBtn;
	}

	public String getColBankTransferBankCode() {
		return colBankTransferBankCode;
	}

	public void setColBankTransferBankCode(String colBankTransferBankCode) {
		this.colBankTransferBankCode = colBankTransferBankCode;
	}

	public boolean isBooRenderColBankTransfer() {
		return booRenderColBankTransfer;
	}

	public void setBooRenderColBankTransfer(boolean booRenderColBankTransfer) {
		this.booRenderColBankTransfer = booRenderColBankTransfer;
	}

	public boolean isBooeanbooColApprovalNo() {
		return booeanbooColApprovalNo;
	}

	public void setBooeanbooColApprovalNo(boolean booeanbooColApprovalNo) {
		this.booeanbooColApprovalNo = booeanbooColApprovalNo;
	}

	public boolean isBooColApprovalNo() {
		return booColApprovalNo;
	}

	public void setBooColApprovalNo(boolean booColApprovalNo) {
		this.booColApprovalNo = booColApprovalNo;
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

	public void setBooRenderMultipleCardPrefix(
			Boolean booRenderMultipleCardPrefix) {
		this.booRenderMultipleCardPrefix = booRenderMultipleCardPrefix;
	}

	public Boolean getBooRenderSingleeCardPrefix() {
		return booRenderSingleeCardPrefix;
	}

	public void setBooRenderSingleeCardPrefix(Boolean booRenderSingleeCardPrefix) {
		this.booRenderSingleeCardPrefix = booRenderSingleeCardPrefix;
	}

	public String getSingleDebitCardPrefex() {
		return singleDebitCardPrefex;
	}

	public void setSingleDebitCardPrefex(String singleDebitCardPrefex) {
		this.singleDebitCardPrefex = singleDebitCardPrefex;
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

	public Map<String, BigDecimal> getMapFetchAllPayMode() {
		return mapFetchAllPayMode;
	}

	public void setMapFetchAllPayMode(Map<String, BigDecimal> mapFetchAllPayMode) {
		this.mapFetchAllPayMode = mapFetchAllPayMode;
	}

	public List<CustomerBank> getLstDebitCard() {
		return lstDebitCard;
	}

	public void setLstDebitCard(List<CustomerBank> lstDebitCard) {
		this.lstDebitCard = lstDebitCard;
	}

	public List<ViewSubAgent> getLstIcashSubAgents() {
		return lstIcashSubAgents;
	}

	public void setLstIcashSubAgents(List<ViewSubAgent> lstIcashSubAgents) {
		this.lstIcashSubAgents = lstIcashSubAgents;
	}

	public List<ViewHODirectEFT> getLstIcashAgentsEFTDetails() {
		return lstIcashAgentsEFTDetails;
	}

	public void setLstIcashAgentsEFTDetails(
			List<ViewHODirectEFT> lstIcashAgentsEFTDetails) {
		this.lstIcashAgentsEFTDetails = lstIcashAgentsEFTDetails;
	}

	public List<ViewHODirectInDirect> getLstIcashAgentsTTDetails() {
		return lstIcashAgentsTTDetails;
	}

	public void setLstIcashAgentsTTDetails(
			List<ViewHODirectInDirect> lstIcashAgentsTTDetails) {
		this.lstIcashAgentsTTDetails = lstIcashAgentsTTDetails;
	}

	public List<ViewStatesForICASH> getLstIcashState() {
		return lstIcashState;
	}

	public void setLstIcashState(List<ViewStatesForICASH> lstIcashState) {
		this.lstIcashState = lstIcashState;
	}

	public List<BankPrefix> getLstBankPrefix() {
		return lstBankPrefix;
	}

	public void setLstBankPrefix(List<BankPrefix> lstBankPrefix) {
		this.lstBankPrefix = lstBankPrefix;
	}

	public String getExcheckCashLimitMessage() {
		return excheckCashLimitMessage;
	}

	public void setExcheckCashLimitMessage(String excheckCashLimitMessage) {
		this.excheckCashLimitMessage = excheckCashLimitMessage;
	}

	*//** Added by Rabil *//*
	private String knetIposReceipt;
	private String knetReceiptDate;

	public List<FcSaleReport> getFcSaleReportDTOList() {
		return fcSaleReportDTOList;
	}

	public void setFcSaleReportDTOList(List<FcSaleReport> fcSaleReportDTOList) {
		this.fcSaleReportDTOList = fcSaleReportDTOList;
	}

	public BigDecimal getApplicationDocNum() {
		return applicationDocNum;
	}

	public void setApplicationDocNum(BigDecimal applicationDocNum) {
		this.applicationDocNum = applicationDocNum;
	}

	public boolean isBooRenderMultiDocNum() {
		return booRenderMultiDocNum;
	}

	public void setBooRenderMultiDocNum(boolean booRenderMultiDocNum) {
		this.booRenderMultiDocNum = booRenderMultiDocNum;
	}

	public boolean isBooRenderSingleDocNum() {
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

	public boolean isBooShowCashRoundingPanel() {
		return booShowCashRoundingPanel;
	}

	public void setBooShowCashRoundingPanel(boolean booShowCashRoundingPanel) {
		this.booShowCashRoundingPanel = booShowCashRoundingPanel;
	}

	public boolean isBooRenderModifiedRoundData() {
		return booRenderModifiedRoundData;
	}

	public void setBooRenderModifiedRoundData(boolean booRenderModifiedRoundData) {
		this.booRenderModifiedRoundData = booRenderModifiedRoundData;
	}

	public BigDecimal getCalGrossAmount() {
		return calGrossAmount;
	}

	public void setCalGrossAmount(BigDecimal calGrossAmount) {
		this.calGrossAmount = calGrossAmount;
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

	String knetTranId = null;

	public String getKnetTranId() {
		return knetTranId;
	}

	public void setKnetTranId(String knetTranId) {
		this.knetTranId = knetTranId;
	}

	boolean checkKnetAmount;

	public boolean isCheckKnetAmount() {
		return checkKnetAmount;
	}

	public void setCheckKnetAmount(boolean checkKnetAmount) {
		this.checkKnetAmount = checkKnetAmount;
	}
	private boolean enableMessage;
	private boolean enableEditMessage;

	public boolean getEnableMessage() {
		return enableMessage;
	}

	public void setEnableMessage(boolean enableMessage) {
		this.enableMessage = enableMessage;
	}

	public boolean getEnableEditMessage() {
		return enableEditMessage;
	}

	public void setEnableEditMessage(boolean enableEditMessage) {
		this.enableEditMessage = enableEditMessage;
	}
	
	*//** populate new bankList for Customer **//*
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
	
	public void feeEnqClear(){
		setSendAmount(null);
		setSendCommission(null);
		setSendGrossTotalAmount(null);
		setSendOriginPrincipleAmount(null);
		setSendDestPrincipleAmount(null);
		setTransferFee(null);
		setSendCharges(null);
		setSendNetAmountSent(null);
		setSendPayableAmount(null);
		setSendPayableAmountDisplay(null);
		setSendExchangeRate(null);
	
		setCashAmount(null);
		setSendPurposeOfTransactions(null);
		setSendSourceOfIncome(null);
		setDeliveryService(null);
		setSendMtcno(null);
		setSendNewMtcno(null);

		setAcknowledgement(true);
		
		setSendPromoDiscountAmount(null);	
		setWuPurposeTransaction(null);
		setOtherPurposeTransaction(null);
		setOtherPurposeEnable(false);
		setReceiveAmount(null);
		setWuMessageCharge(null);
	}
	
*/}
