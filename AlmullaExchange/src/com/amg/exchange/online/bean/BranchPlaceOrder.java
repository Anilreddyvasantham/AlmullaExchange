package com.amg.exchange.online.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.registration.bean.CustomerPersonalInfoBean;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("branchPlaceOrder")
@Scope("session")
public class BranchPlaceOrder<T> {

	Logger log = Logger.getLogger(BranchPlaceOrder.class);

	private String exceptionMessage;
	private String customerExpireDateMsg;
	private boolean booRenderBenificaryFirstPanel = false;
	private boolean booRenderOldSmartCardPanel = false;
	private int selectCardType;
	private BigDecimal selectCard;
	private String idNumber;
	private BigDecimal cardType;

	private Map<BigDecimal, String> mapcomIdentityType = new HashMap<BigDecimal, String>();
	SessionStateManage sessionStateManage = new SessionStateManage();
	private List<CustomerIdProof> customerDetailsList = new ArrayList<CustomerIdProof>();

	@Autowired
	IPlaceAnOrderCreationService  iPlaceOnOrderCreationService;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;


	public BigDecimal getCardType() {
		return cardType;
	}

	public void setCardType(BigDecimal cardType) {
		this.cardType = cardType;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getCustomerExpireDateMsg() {
		return customerExpireDateMsg;
	}

	public void setCustomerExpireDateMsg(String customerExpireDateMsg) {
		this.customerExpireDateMsg = customerExpireDateMsg;
	}

	public boolean isBooRenderBenificaryFirstPanel() {
		return booRenderBenificaryFirstPanel;
	}

	public void setBooRenderBenificaryFirstPanel(
			boolean booRenderBenificaryFirstPanel) {
		this.booRenderBenificaryFirstPanel = booRenderBenificaryFirstPanel;
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


	public Map<BigDecimal, String> getMapcomIdentityType() {
		return mapcomIdentityType;
	}

	public void setMapcomIdentityType(Map<BigDecimal, String> mapcomIdentityType) {
		this.mapcomIdentityType = mapcomIdentityType;
	}

	@SuppressWarnings("rawtypes")
	public void redirectToCustomerPage() throws DOMException, ParseException, ParserConfigurationException, SAXException, IOException {
		CustomerPersonalInfoBean customerPesonel = (CustomerPersonalInfoBean) appContext.getBean("customerPersonalInfo");
		customerPesonel.resetValues();
		customerPesonel.setSelectType(Constants.MANUAL);
		customerPesonel.setBooManual(true);
		customerPesonel.setBooIdType(true);
		customerPesonel.setSelectedIdType(getSelectCard().toString());
		log.info("==========REDIRECT==========" + getSelectCard().toString() + "" + getIdNumber());
		customerPesonel.setIdNumber(getIdNumber());
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

	public void redirectToCustomerFirstPage() throws DOMException, ParseException, ParserConfigurationException, SAXException, IOException {
		CustomerPersonalInfoBean customerPesonel = (CustomerPersonalInfoBean) appContext.getBean("customerPersonalInfo");
		customerPesonel.resetValues();
		customerPesonel.setSelectType(Constants.MANUAL);
		customerPesonel.setBooManual(true);
		customerPesonel.setBooIdType(true);
		customerPesonel.setSelectedIdType(getSelectCard().toString());
		log.info("==========REDIRECT==========" + getSelectCard().toString() + "" + getIdNumber());
		customerPesonel.setIdNumber(getIdNumber());
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

	public void showCardTypePanel() throws Exception {
		int typecard = getSelectCardType();
		if (typecard == 2) {
			setBooRenderBenificaryFirstPanel(true);
			setBooRenderOldSmartCardPanel(true);
			BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
			if (idtypeCivilIdnew != null) {
				setSelectCard(idtypeCivilIdnew);
			}
		} else if (typecard == 1) {
			fetchSmartCardIdNumber();
			if (getIdNumber() != null && getSelectCard() != null) {
				/*if (coustomerBeneficaryDTList != null && !coustomerBeneficaryDTList.isEmpty()) {
					coustomerBeneficaryDTList.clear();
				}*/
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

	// first method after go clicked to fetch all customer details
	public void goFromOldSmartCardpanel() throws ParseException {
		log.info("Entering into goFromOldSmartCardpanel method");
		if (getIdNumber() != null && !getIdNumber().equalsIgnoreCase("")) {
			if (getSelectCard() != null) {
				//customerDetailsList = iPersonalRemittanceService.getCustomerDetails(getIdNumber().toUpperCase(), getSelectCard());
				customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber().toUpperCase(), getSelectCard());
				if (customerDetailsList.size() != 0) {
					CustomerIdProof customerDetails = customerDetailsList.get(0);
					boolean checkRateOrder = fetchPlaceonOrderDetails(customerDetails);
				} else {
					// comparing with civil id
					BigDecimal identityTpeIds = generalService.getComponentId(Constants.CIVILID, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
					if (getSelectCard().compareTo(identityTpeIds) != 0) {
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), identityTpeIds);
						if (customerDetailsList.size() != 0) {
							CustomerIdProof customerDetails = customerDetailsList.get(0);
							boolean checkRateOrder = fetchPlaceonOrderDetails(customerDetails);
							if(!checkRateOrder)
							{
								RequestContext.getCurrentInstance().execute("placeorderexistedOnload.show();");
								return ;
							}
						} else {
							// comparing with civil id new
							BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
							customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idtypeCivilIdnew);
							if (customerDetailsList.size() != 0) {
								CustomerIdProof customerDetails = customerDetailsList.get(0);
								boolean checkRateOrder=fetchPlaceonOrderDetails(customerDetails);
								if(!checkRateOrder)
								{
									RequestContext.getCurrentInstance().execute("placeorderexistedOnload.show();");
									return ;
								}
							} else {
								// failed all conditions
								setCardType(null);
								setIdNumber(null);
								setBooRenderBenificaryFirstPanel(true);
								RequestContext.getCurrentInstance().execute("idNotFound.show();");
								return;
							}
						}
					} else {
						// comparing with civil id new
						BigDecimal idtypeCivilIdnew = generalService.getComponentId(Constants.CIVIL_ID_NEW, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
						customerDetailsList = iPersonalRemittanceService.getCustomerDetailsActiveRec(getIdNumber(), idtypeCivilIdnew);
						if (customerDetailsList.size() != 0) {
							CustomerIdProof customerDetails = customerDetailsList.get(0);
							boolean checkRateOrder=fetchPlaceonOrderDetails(customerDetails);
							if(!checkRateOrder)
							{
								RequestContext.getCurrentInstance().execute("placeorderexistedOnload.show();");
								return ;
							}
						} else {
							// failed all conditions
							setCardType(null);
							setIdNumber(null);
							setBooRenderBenificaryFirstPanel(true);
							RequestContext.getCurrentInstance().execute("idNotFound.show();");
							return;
						}
					}
				}
			}
		} else {
			RequestContext.getCurrentInstance().execute("idNumbernotenter.show();");
			return;
		}
		log.info("Exit into goFromOldSmartCardpanel method");
		/*try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/PlaceOnOrderCreationBeanBranch.xhtml");
		} catch (IOException e) {

			e.printStackTrace();
		}*/
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

	@SuppressWarnings("unused")
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
			setIdNumber(null);
			setSelectCard(null);
			RequestContext.getCurrentInstance().execute("dldInserCard.show();");
		}
		return sb.toString();
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void branchPlaceOrderPageNavigation() {
		//hideAllPanels();
		//assignNullValues();
		setCardType(null);
		setSelectCardType(0);
		setIdNumber(null);
		loadIdType();

		//checkProExp = false;
		//saveCount = 0;
		setBooRenderBenificaryFirstPanel(true);
		setBooRenderOldSmartCardPanel(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "BranchPlaceOrder.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/BranchPlaceOrder.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadIdType() {
		mapcomIdentityType = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), "I", "Identity Type");
	}

	private boolean fetchPlaceonOrderDetails(CustomerIdProof customerDetails)
	{
		//PlaceOnOrderCreationBean specialRateRequestForOnlineTrnxBean = (PlaceOnOrderCreationBean) appContext.getBean("placeOnOrderCreationBean");
		PlaceOrderCreationBean<T> placeOrderCreationBean = (PlaceOrderCreationBean) appContext.getBean("placeOrderCreationBean");
		placeOrderCreationBean.setCustomerId(customerDetails.getFsCustomer().getCustomerId());
		placeOrderCreationBean.setBooRenderPlaceOrderFromRemit(false);
		placeOrderCreationBean.branchPageNavigation();
		return true;
	}
	
	public void cancel()
	{
		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}catch(Exception e){
			//setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}  
	}

	// search operation in Personal remittance
	public void searchClicked() {

		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("request", "branchPlaceOrder");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
		} catch (Exception e) {
			log.info("Problem to Redirect the page : " + e);
		}

	}
}
