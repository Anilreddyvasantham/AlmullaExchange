package com.amg.exchange.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.apache.axis.AxisFault;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.MarketingData;
import com.amg.exchange.common.service.IAlmullagroupService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.mail.ApllicationMailer1;
import com.amg.exchange.mail.ApplicationEmailLog;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.LoginLogoutHistory;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.registration.service.IRemmiterOnlineRegService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.send_sms.Call_Response;
import com.amg.exchange.send_sms.EoExOwsLoginCredentials;
import com.amg.exchange.send_sms.EoOwsParamRespcode;
import com.amg.exchange.send_sms.SmsLogModel;
import com.amg.exchange.send_sms.SmsServiceLocator;
import com.amg.exchange.send_sms.SmsServiceSoap_BindingStub;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.DateUtil;
import com.amg.exchange.util.PasswordGenerator;
import com.amg.exchange.util.SessionCacheManager;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;



@Component("loginBean")
@Scope("session")
public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(LoginBean.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	ApplicationContext context;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IEmployeeService employeeService;
	@Autowired
	IAlmullagroupService<T> ialmullagroupService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	ICustomerRegistrationBranchService<T> customerRegistrationBranchService;
	@Autowired
	ILoginService<T> loginService;
	@Autowired
	IRemmiterOnlineRegService iRemmiterOnlineRegService;
	@Autowired
	ApllicationMailer1 apllicationMailer1;


	// page variable
	private BigDecimal employeeId;
	private String employeeNumber;
	private BigDecimal employeeBranchId;
	private String civilIdNumber;
	private String empCivilId;
	private String userName;
	private String userPhoneNumber;
	private BigDecimal selectCardType;
	private boolean booRenderUserDetails;
	private boolean booRenderAuthorization;
	private boolean booRenderPartnerAuthorization;
	private boolean booRenderCivilId;
	private boolean booRenderUserOTP;
	private boolean booRequiredCivilId;
	private boolean booRenderPartnerLogin;
	private String errorMessage;
	private String generatedPassword;
	private String userPassword;
	private String partnerCivilId;
	private String partnerPassword;
	private BigDecimal partnerBranchId;
	private String smartCardSelection;

	// back side variable
	private String applicationCountryName;
	private BigDecimal applicationCountryId;
	private BigDecimal pid;
	private String loginHdrTitle;
	private StreamedContent loginHdrlogo;
	private String loginHdrTxt;
	private String loginArbTitle;
	private StreamedContent loginArblog;
	private String loginArbTxt;
	private String innerHdrTitle;
	private StreamedContent innerHdrlog;
	private String innerHdrTxt;
	private String innerHdrArbtitle;
	private StreamedContent innerHdrArbBlog;
	private String innerHdrArbTxt;
	private String loginfooterTxt;
	private String innerfooterTxt;
	private StreamedContent logBnrImg;
	private String loginNewsEvent;
	private Boolean booRenderloginHdrlogoKW = true;
	private Boolean booRenderloginHdrlogoOM = false;
	private String allowFCTrnx;
	private Boolean isValidate = false;

	int count;
	int countSecurityQues;
	int countPw;

	private Map<BigDecimal, String> mapcomIdentityType = new HashMap<BigDecimal, String>();
	private List<MarketingData> loadMarketData = new ArrayList<MarketingData>();
	private List<Employee> lstEmployeeDetails = new ArrayList<Employee>();
	private iCypherSecurity cypherSecurity = null;
	private List<Employee> lstEmpLogin = null;
	private Employee employeeDetails = null;

	/*
	 * Service Provider Navigation
	 */
	public void pageNavigation() {
		try {
			log.info("Entering into pageNavigation method ");
			clearAll();
			fetchdata();
			FacesBean facesBean = (FacesBean) context.getBean("facesBean");
			facesBean.setVisible(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../login/login.xhtml");
			log.info("Exit from pageNavigation method ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// checking EcNo with V_HR_EMPLOYEE
	public void checkEcNoWithHR(String smartCartStatus){

		try {
			if(smartCartStatus != null){
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("companyId", BigDecimal.ONE);
				
				setSmartCardSelection(smartCartStatus);
				List<Employee> lstEmployee = new ArrayList<Employee>();
				setLstEmployeeDetails(null);
				if(getEmployeeNumber() != null){
					//String checkEmpName = employeeService.checkECNUMBERwithHRView(getEmployeeNumber());
					String checkEmpName = "KANMANI1";
					if(checkEmpName != null){
						//records exists
						List<Employee> lstEmployeeDB = employeeService.toCheckEmpCodeExist(getEmployeeNumber());
						if (lstEmployeeDB.size() > 0) {
							if(lstEmployeeDB.size() == 1){
								Employee empl = lstEmployeeDB.get(0);
								if(empl.getIsActive() != null && empl.getIsActive().equalsIgnoreCase(Constants.Yes)){
									setLstEmployeeDetails(null);
									setEmployeeId(empl.getEmployeeId());
									setUserName(empl.getUserName());
									setEmpCivilId(empl.getCivilId());
									setUserPhoneNumber(empl.getTelephoneNumber());
									setEmployeeBranchId(empl.getFsCountryBranch().getCountryBranchId());
									setEmployeeDetails(empl);

									String errorMessage = checkIpaddressForEmployee();
									if(errorMessage != null){
										FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,errorMessage, errorMessage));
									}else{
										showCardTypePanel(smartCartStatus);
									}
								}else{
									setErrorMessage("Employee Number is deactivated, Kindly contact IT department");
									RequestContext.getCurrentInstance().execute("alertmsg.show();");
								}
							}else{
								for (Employee employee : lstEmployeeDB) {
									if(employee.getIsActive() != null && employee.getIsActive().equalsIgnoreCase(Constants.Yes)){
										// BLOCKING NON ARMS
										if(employee.getUserName() != null){
											String userNameChk = employee.getUserName().substring(0, 4);
											if(!userNameChk.equalsIgnoreCase(Constants.ARMS)){
												lstEmployee.add(employee);
											}
										}
									}
								}

								if(lstEmployee != null && lstEmployee.size() > 0){
									if(lstEmployee.size() == 1){
										setLstEmployeeDetails(null);
										Employee empl = lstEmployee.get(0);
										setEmployeeId(empl.getEmployeeId());
										setUserName(empl.getUserName());
										setEmpCivilId(empl.getCivilId());
										setUserPhoneNumber(empl.getTelephoneNumber());
										setEmployeeBranchId(empl.getFsCountryBranch().getCountryBranchId());
										setEmployeeDetails(empl);
										String errorMessage = checkIpaddressForEmployee();
										if(errorMessage != null){
											FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,errorMessage, errorMessage));
										}else{
											showCardTypePanel(smartCartStatus);
										}
									}else{
										/*setLstEmployeeDetails(lstEmployee);
										Employee empl = lstEmployee.get(0);
										setEmployeeId(empl.getEmployeeId());
										setUserName(empl.getUserName());
										setEmpCivilId(empl.getCivilId());
										setUserPhoneNumber(empl.getTelephoneNumber());
										setEmployeeBranchId(empl.getFsCountryBranch().getCountryBranchId());
										showCardTypePanel();*/

										String dupEmpUsers = "";
										for (Employee employee : lstEmployee) {
											dupEmpUsers = dupEmpUsers == "" ? dupEmpUsers.concat(employee.getUserName()) : dupEmpUsers.concat(",").concat(employee.getUserName());
										}

										setErrorMessage("Multiple records found, Kindly contact IT department, provide users to deactivate : " + dupEmpUsers);
										RequestContext.getCurrentInstance().execute("alertmsg.show();");
									}
								}else{
									setErrorMessage("No records found, Kindly contact IT department");
									RequestContext.getCurrentInstance().execute("alertmsg.show();");
								}
							}
						}else{
							setErrorMessage("No records found, Kindly contact IT department");
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}
					}else{
						setErrorMessage("Employee Number not available with HR Records"); 
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}
				}else{
					setErrorMessage("please enter Employee Number"); 
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}else{
				setErrorMessage("MethodName::checkEcNoWithHR");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			}
		}  catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::checkEcNoWithHR");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;       
		}
	}

	// clear fields
	public void clearAll(){
		log.info("Entering into clearAll method ");
		setEmployeeDetails(null);
		setEmployeeId(null);
		setEmployeeNumber(null);
		setCivilIdNumber(null);
		setUserPassword(null);
		setPartnerCivilId(null);
		setPartnerPassword(null);
		setLstEmployeeDetails(null);
		setSmartCardSelection(null);
		setBooRenderUserDetails(Boolean.TRUE);
		setBooRenderAuthorization(Boolean.FALSE);
		setBooRenderPartnerAuthorization(Boolean.FALSE);
		setBooRenderCivilId(Boolean.FALSE);
		setBooRequiredCivilId(Boolean.FALSE);
		setBooRenderUserOTP(Boolean.FALSE);
		setBooRenderPartnerLogin(Boolean.FALSE);
		log.info("Exit from clearAll method ");
	}

	public void redirectLogin() {
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../almullagroup/almullagroup.xhtml");
		} catch (Exception e) {
			log.error("Exception occured in LoginBean ::redirectLogin method ", e);
		}
	}

	@PostConstruct
	public void loadIdType() {
		log.info("Entering into loadIdType method ");
		mapcomIdentityType = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), "I", "Identity Type");
		log.info("Exit from loadIdType method ");
	}

	//Show Card type
	public void showCardTypePanel(String smartCartStatus) throws Exception {
		log.info("Entering into showCardTypePanel method ");
		try {
			if(smartCartStatus.equalsIgnoreCase(Constants.Yes)){
				/*
				String smartCard = fetchSmartCardIdNumber();
				String[] str = smartCard.split("#");
				if (str.length > 1) {
					for (int i = 0; i < str.length; i++) {
						String string = str[i];
						// System.out.println("str :"+string);
						if (i == 8) {
							System.out.println("Civil Id ");
							String[] parts = string.split("=");
							String part1 = parts[0];
							String part2 = parts[1];
							setCivilIdNumber(part2);
						}
					}

					validateCivilId();
				} else {
					setCivilIdNumber(null);
					RequestContext.getCurrentInstance().execute("dldInserCard.show();");
				}
               */
				setCivilIdNumber("281050207628"); // testing
				validateCivilId();
			}else if(smartCartStatus.equalsIgnoreCase(Constants.No)){
				setBooRenderCivilId(Boolean.TRUE);
				setBooRequiredCivilId(Boolean.FALSE);
				setBooRenderUserOTP(Boolean.FALSE);
			}else{
				setBooRenderCivilId(Boolean.TRUE);
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::" + ne.getMessage());
			setErrorMessage("Method Name::showCardTypePanel");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage("Smart card issue. Please contact IT team");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
			return;
		}
		log.info("Exit from showCardTypePanel method ");
	}

	// validate civil id and emp civil id
	public void validateCivilId(){
		if (getCivilIdNumber() != null) {
			if(getEmpCivilId() != null){
				if(getCivilIdNumber().equalsIgnoreCase(getEmpCivilId())){
					// continue - generate OTP to login
					setBooRenderAuthorization(Boolean.TRUE);
					setBooRenderPartnerAuthorization(Boolean.FALSE);
					setBooRenderCivilId(Boolean.TRUE);
					setBooRequiredCivilId(Boolean.TRUE);
					setBooRenderUserOTP(Boolean.TRUE);
					generateOTPforStaff();
					//Call_Response callResponse =  generateOTPforStaff();
					String loginType = null;
					if(getSmartCardSelection().equalsIgnoreCase(Constants.Yes)){
						loginType = "SMART";
					}else{
						loginType = "MANUAL";
					}

					saveUserSmsLog(null,loginType);
				}else{
					// error staff civil Id not matching
					sendEmailECNoCivilIdFail();
					setErrorMessage("Civil Id details not matching.Kindly contact IT department");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}else{
				sendEmailECNoCivilIdFail();
				setErrorMessage("Civil Id details not available.Kindly contact IT department");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		} else {
			if(isBooRequiredCivilId()){
				RequestContext.getCurrentInstance().execute("dldInserCard.show();");
			}else{
				setErrorMessage("Please enter the ID Number");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}
	}

	// generate OTP
	public Call_Response  generateOTPforStaff(){
		Call_Response callres = null;
		try {
			int passwordLength = 8;
			String password = generate_random_number(passwordLength, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
			String msg = null;
			if(password != null){
				setGeneratedPassword(password);
				msg = password;
			}

			if(getUserPhoneNumber() != null){

				HashMap<String, String> inputValues = new HashMap<String, String>();

				inputValues.put("P_SENDER_ID", "AlMulla EXC");
				inputValues.put("P_MOBILE", getUserPhoneNumber());
				inputValues.put("P_APPLNAME", "");
				inputValues.put("P_AL1COD", "");
				inputValues.put("P_ACNTCOD", "");
				inputValues.put("P_CREATOR", "");
				inputValues.put("P_LANGUAGE", Constants.ENGLISH_LANGUAGE_ID);
				inputValues.put("P_COMCOD", "");
				inputValues.put("P_DOCCOD", "");
				inputValues.put("P_DOCFYR", "");
				inputValues.put("P_DOCNO", "");
				inputValues.put("P_MESSAGE", msg);

				/*callres = send_sms("965"+getUserPhoneNumber(), msg);

				System.out.println(callres.toString());
				System.out.println("Restult : " + callres.getResult());
				System.out.println("Response : " + callres.getResponse_desc());
				System.out.println("Restult : " + callres.getRequest_xml());
				System.out.println("Response : " + callres.getResponse_xml());

				if(callres.getResult()){
					setErrorMessage("SMS is send to Mobile");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");

					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userName", getUserName());
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countryId", lstEmpLogin.get(0).getCountryId());
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("companyId", lstEmpLogin.get(0).getFsCompanyMaster().getCompanyId());
				}else{
					setErrorMessage(callres.getResponse_desc());
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}*/

				HashMap<String, String> outputValues = customerRegistrationBranchService.callOTPSendProcedure(inputValues);
				if(outputValues != null && outputValues.size() != 0){
					if(outputValues.get("P_ERROR_MESSAGE") != null){
						setErrorMessage(outputValues.get("P_ERROR_MESSAGE"));
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}else{
						setErrorMessage("SMS is send to Mobile");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");

						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userName", getUserName());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countryId", lstEmpLogin.get(0).getCountryId());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("companyId", lstEmpLogin.get(0).getFsCompanyMaster().getCompanyId());
					}
				}
			}else{
				setErrorMessage("Mobile Not Available in Customer Registeration");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}

		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

		return callres;
	}

	// add log after sms
	public void saveUserSmsLog(Call_Response callResponse,String loginType){

		try {

			SmsLogModel smslog = new SmsLogModel();

			smslog.setCreated_By(getUserName());
			smslog.setCreated_Date(new Date());
			smslog.setEcNo(getEmployeeNumber());
			smslog.setIdNumber(getCivilIdNumber());
			smslog.setMobile(getUserPhoneNumber());
			smslog.setOtpNo(getGeneratedPassword());

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String hostname = getClientIpAddress(request);
			smslog.setIpAddress(hostname);

			smslog.setLoginType(loginType);
			smslog.setOtpType("LOGIN");
			if(callResponse != null){
				smslog.setErrorMsg(callResponse.getResponse_desc());
				smslog.setRequestXml(stringToClob(callResponse.getRequest_xml()));
				smslog.setResponseXml(stringToClob(callResponse.getResponse_xml()));
			}

			loginService.saveSmsLog(smslog);

		} catch (Exception e) {
			log.info(e.getMessage());
		}

	}

	public java.sql.Clob stringToClob(String source) throws Exception {
		try {
			return new javax.sql.rowset.serial.SerialClob(source.toCharArray());
		} catch (Exception e) {
			return null;
		}
	}

	// add log after sms
	public void savePartnerSmsLog(Call_Response callResponse,String loginType){
		try {
			SmsLogModel smslog = new SmsLogModel();

			smslog.setCreated_By(getUserName());
			smslog.setCreated_Date(new Date());
			smslog.setEcNo(getEmployeeNumber());
			smslog.setIdNumber(getPartnerCivilId());
			smslog.setMobile(getUserPhoneNumber());
			smslog.setOtpNo(getGeneratedPassword());

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String hostname = getClientIpAddress(request);
			smslog.setIpAddress(hostname);

			smslog.setLoginType(loginType);
			smslog.setOtpType("LOGIN");
			if(callResponse != null){
				smslog.setErrorMsg(callResponse.getResponse_desc());
				smslog.setRequestXml(stringToClob(callResponse.getRequest_xml()));
				smslog.setResponseXml(stringToClob(callResponse.getResponse_xml()));
			}

			loginService.saveSmsLog(smslog);

		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	// generate random number
	public String generate_random_number(int number_of_digits, boolean useLower, boolean useUpper, boolean useDigits, boolean usePunctuation){
		PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useLower(useLower).useUpper(useUpper).useDigits(useDigits).usePunctuation(usePunctuation).build();
		//return passwordGenerator.generate(number_of_digits);
		return "12345";
	}

	// fetch partner civil id
	public void fetchPartnerCivilId(){
		String smartCard;
		try {
			smartCard = fetchSmartCardIdNumber();
			String[] str = smartCard.split("#");
			if (str.length > 1) {
				for (int i = 0; i < str.length; i++) {
					String string = str[i];
					// System.out.println("str :"+string);
					if (i == 8) {
						System.out.println("Civil Id ");
						String[] parts = string.split("=");
						String part1 = parts[0];
						String part2 = parts[1];
						setPartnerCivilId(part2);
					}
				}

			} else {
				setPartnerCivilId(null);
				RequestContext.getCurrentInstance().execute("dldInserCard.show();");
			}

			//setPartnerCivilId("289053104436"); // testing

			if(getPartnerCivilId() != null){
				// based on civilId need to send OTP to staff
				List<Employee> fetchEmployee = loginService.getLoginDetailsForEmployee(getPartnerCivilId());
				if(fetchEmployee != null && fetchEmployee.size() != 0){
					if(fetchEmployee.size() == 1){
						Employee emp = fetchEmployee.get(0);
						setPartnerBranchId(emp.getFsCountryBranch().getCountryBranchId());
						if(emp.getTelephoneNumber() != null){
							setUserPhoneNumber(emp.getTelephoneNumber());
							if(getPartnerBranchId() != null && getEmployeeBranchId() != null){
								if(getPartnerBranchId().compareTo(getEmployeeBranchId()) == 0){
									setBooRenderPartnerLogin(Boolean.TRUE);
									// otp generation
									generateOTPforStaff();
									//Call_Response callResponse =  generateOTPforStaff();

									String loginType = "PARTNER";
									savePartnerSmsLog(null,loginType);
								}else{
									setErrorMessage("Partner Branch is not matching with Employee Branch");
									RequestContext.getCurrentInstance().execute("alertmsg.show();");
								}
							}else{
								setErrorMessage("Partner Branch or Employee Branch is not available");
								RequestContext.getCurrentInstance().execute("alertmsg.show();");
							}
						}else{
							setErrorMessage("Partner mobile not registered,Please contact IT department");
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}
					}else{
						setErrorMessage("Muliple records available,Please contact IT department");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}
				}else{
					setErrorMessage("No active records available,Please contact IT department");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// validate partner OTP
	public void checkPartnerOTPVerification(){
		if(getPartnerPassword() != null){
			if(getPartnerPassword().equals(getGeneratedPassword())){
				login();
			}else{
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				System.out.println("Local HostAddress: "+request.getRemoteAddr());
				String hostname = getClientIpAddress(request);
				String localIpadress = hostname;
				System.out.println("Local host name: "+hostname);

				loginService.lockEmployeeLoginFail(getUserName(), localIpadress);

				if(getUserName() != null){
					boolean lockCheck = loginService.lockEmployeeCheck(getUserName());
					if(lockCheck){
						sendEmailOTPFail();
						clearAll();
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login failed ! Account has been locked. Please Contact HELP DESK TEAM!", ""));
						return;
					}else{
						setErrorMessage("Please enter correct password");
						RequestContext.getCurrentInstance().execute("alertmsg.show();");
					}
				}

			}
		}else{
			setErrorMessage("Please enter partner password");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// read smart card url
	public String fetchSmartCardIdNumber() throws ParseException {
		String userCivilId = null;
		log.info("Entering into fetchSmartCardIdNumber method ");
		if (sessionStateManage.getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			userCivilId = smartCardDisplay(ipAddress, "8085", "M", "test");
		}
		log.info("Exit from fetchSmartCardIdNumber method ");

		return userCivilId;
	}

	// Display smart card
	public String smartCardDisplay(String host, String prdPort, String requestType, String env) throws ParseException {
		log.info("Entering into smartCardDisplay method ");
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
		} finally {
			if (testyc != null) {
				testyc.disconnect();
			}
			if (prdyc != null) {
				testyc.disconnect();
			}
		}

		log.info("Exit from smartCardDisplay method ");

		return sb.toString();
	}

	public String fetchdata() {
		log.info("Entering into fetchdata method");
		try {
			List<MarketingData> data = ialmullagroupService.getCountryList(sessionStateManage.getLanguageId(),getApplicationCountryId());
			if (data.size() > 0) {
				setLoadMarketData(data);
				setPid(data.get(0).getMarkdataId());
				setLoginHdrTitle(data.get(0).getLoginHeaderTitle());
				setLoginHdrTxt(data.get(0).getLoginHeaderText());
				setLoginArbTitle(data.get(0).getLoginHeaderArabicTitle());
				setLoginArbTxt(data.get(0).getLoginHeaderArabicText());
				setInnerHdrTitle(data.get(0).getInnerHeaderTitle());
				setInnerHdrTxt(data.get(0).getInnerHeaderText());
				setInnerHdrArbtitle(data.get(0).getInnerHeaderArabicTitle());
				setInnerHdrArbTxt(data.get(0).getInnerHeaderArabicText());
				setLoginfooterTxt(data.get(0).getLoginFooterText());
				setInnerfooterTxt(data.get(0).getInnerFooterText());
				setLoginNewsEvent(data.get(0).getLoginNewsEvent());
			}
		} catch (Exception e) {
			log.error("Exception occured in LoginBean ::fetchdata method ", e);
		}
		log.info("Exit into fetchdata method");
		return "";
	}

	// check the otp
	public void checkOTPVerification(){
		if(getEmployeeNumber() != null){
			if(getCivilIdNumber() != null){
				if(getUserPassword() != null && getGeneratedPassword() != null){
					if(getUserPassword().equals(getGeneratedPassword())){
						// successfully login
						if(getLstEmployeeDetails() != null && getLstEmployeeDetails().size() != 0){
							// Multiple records
						}else if(getUserName() != null){
							//Boolean passwordUpdateStatus = updateCurrenctPassword();
							Boolean passwordUpdateStatus = Boolean.TRUE;
							if(passwordUpdateStatus){
								if(getSmartCardSelection() != null){
									if(getSmartCardSelection().equalsIgnoreCase(Constants.Yes)){
										// login successful to page
										login();
									}else{
										// populate partner to insert smart card
										setBooRenderCivilId(Boolean.FALSE);
										setBooRenderUserOTP(Boolean.FALSE);
										setBooRenderAuthorization(Boolean.FALSE);
										setBooRenderUserDetails(Boolean.FALSE);
										setBooRenderPartnerAuthorization(Boolean.TRUE);
									}
								}else{
									// login successful to page
									login();
								}
							}else{
								setErrorMessage("unable to update the password for : " +getUserName());
								RequestContext.getCurrentInstance().execute("alertmsg.show();");
							}
						}else{
							setErrorMessage("User Name is not captured");
							RequestContext.getCurrentInstance().execute("alertmsg.show();");
						}
					}else{
						HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
						System.out.println("Local HostAddress: "+request.getRemoteAddr());
						String hostname = getClientIpAddress(request);
						String localIpadress = hostname;
						System.out.println("Local host name: "+hostname);

						loginService.lockEmployeeLoginFail(getUserName(), localIpadress);

						if(getUserName() != null){
							boolean lockCheck = loginService.lockEmployeeCheck(getUserName());
							if(lockCheck){
								sendEmailOTPFail();
								clearAll();
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login failed ! Account has been locked. Please Contact HELP DESK TEAM!", ""));
								return;
							}else{
								setErrorMessage("Please enter correct password");
								RequestContext.getCurrentInstance().execute("alertmsg.show();");
							}
						}
					}
				}else{
					setErrorMessage("Please enter password(OTP)");
					RequestContext.getCurrentInstance().execute("alertmsg.show();");
				}
			}else{
				setErrorMessage("Staff civil Id is not captured");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}else{
			setErrorMessage("Employee Number is not available");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	private static final String[] HEADERS_TO_TRY = { 
		"X-Forwarded-For",
		"Proxy-Client-IP",
		"WL-Proxy-Client-IP",
		"HTTP_X_FORWARDED_FOR",
		"HTTP_X_FORWARDED",
		"HTTP_X_CLUSTER_CLIENT_IP",
		"HTTP_CLIENT_IP",
		"HTTP_FORWARDED_FOR",
		"HTTP_FORWARDED",
		"HTTP_VIA",
		"REMOTE_ADDR" 
	};

	// client ip address fetching
	public static String getClientIpAddress(HttpServletRequest request) {
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	// client system name
	public String fetchClientSystemName(String ipAddress){
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String hostname = addr.getHostName();

		return hostname;
	}

	public int daysBetween(Date d1, Date d2){
		return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	// checking password date for Fs_Employee
	public HashMap<String, Object> checkingPasswordDate(Date passwordDate){
		HashMap<String, Object> mapObj = new HashMap<String, Object>();
		Boolean allowStatus = Boolean.FALSE;
		Boolean warningStatus = Boolean.FALSE;
		if(passwordDate != null){
			// check the date with current server date if crossed 7 push to change password screen
			int days = daysBetween(passwordDate, new Date());
			System.out.println(days);
			if(days == 6){
				allowStatus = Boolean.TRUE;
				warningStatus = Boolean.TRUE;
			}else if(days >= 7)
			{
				allowStatus = Boolean.FALSE;
				warningStatus = Boolean.FALSE;
				try {
					FacesContext.getCurrentInstance() .getExternalContext().redirect("../login/changePasswordExpired.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				allowStatus = Boolean.TRUE;
				warningStatus = Boolean.FALSE;
			}
		}else{
			// push to change password screen
			allowStatus = Boolean.FALSE;
			try {
				FacesContext.getCurrentInstance() .getExternalContext().redirect("../login/changePasswordExpired.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		mapObj.put("allowStatus", allowStatus);
		mapObj.put("warningStatus", warningStatus);

		return mapObj;
	}

	// update the password to Employee master
	public Boolean updateCurrenctPassword(){
		Boolean passwordCurrenctStatus = Boolean.FALSE;
		log.info("Entering  into updatePassword method");
		String userName = getUserName();
		String passwordType = Constants.LoginPassword;
		iCypherSecurity cypherSecurity = new CypherSecurityImpl();
		try {
			String secretKey = userName.toUpperCase();
			StringBuffer secretKeys = new StringBuffer(secretKey).reverse();
			employeeService.updateChangePasswordforChangePassword(getEmployeeId(),cypherSecurity.encodePassword(getUserPassword(), secretKeys.toString()),passwordType);
			log.info("Password update sucess");
			passwordCurrenctStatus = Boolean.TRUE;
		} catch (Exception e) {
			log.error("Exception occured in ChangePassword ::updatePassword method ", e);
			return passwordCurrenctStatus;
		}	
		log.info("Exit  into updatePassword method");
		return passwordCurrenctStatus;
	}

	// check employee ip address and branch ip address and view
	public String checkIpaddressForEmployee(){
		String errorMessage = null;

		String userName = getUserName().trim();
		//String password = getUserPassword().trim();
		log.info("userName ==" + userName);
		//Date passwordDate = null;
		String systemname = null;
		// ip address of employee and system ip address should match -- 24/04/2016
		String branchIpaddress = null , localIpadress = null ,localFullIpadress = null;
		String[] localSystemIp = null;
		String[] branchIp = null;
		BigDecimal branchId = null;
		BigDecimal countryBranchId = null;

		lstEmpLogin = new ArrayList<Employee>();

		try {

			boolean lockCheck = loginService.lockEmployeeCheck(getUserName());
			if(lockCheck){
				log.error("Login failed ! Account has been locked. Please Contact HELP DESK TEAM!");
				System.out.println("Login failed ! Account has been locked. Please Contact HELP DESK TEAM!");
				errorMessage = "Login failed ! Account has been locked. Please Contact HELP DESK TEAM!";
				//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login failed ! Account has been locked. Please Contact HELP DESK TEAM!", ""));
				//return;
			}else{
				// once login success
				lstEmpLogin.add(getEmployeeDetails());
				String userType = lstEmpLogin.get(0).getUserType()==null ? "" : lstEmpLogin.get(0).getUserType();
				BigDecimal fsEmployeeId = lstEmpLogin.get(0).getEmployeeId();
				if(lstEmpLogin.get(0).getFsCountryBranch() != null){
					branchId = lstEmpLogin.get(0).getFsCountryBranch().getBranchId(); // branch code
					countryBranchId = lstEmpLogin.get(0).getFsCountryBranch().getCountryBranchId();
				}

				if(userType != null && !userType.equalsIgnoreCase("")){

					HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
					System.out.println("Local HostAddress: "+request.getRemoteAddr());
					String hostname = getClientIpAddress(request);
					localFullIpadress = hostname;
					System.out.println("Local host name: "+hostname);

					localSystemIp = (hostname).split("\\.");
					System.out.println("localSystemIp : "+localSystemIp.toString());

					if(localSystemIp != null && localSystemIp.length != 0){
						localIpadress =  (localIpadress==null ? "" : localIpadress.concat(".")).concat(localSystemIp[0]).concat(".").concat(localSystemIp[1]).concat(".").concat(localSystemIp[2]);
						System.out.println(localIpadress);
					}

					if(branchId != null){
						List<CountryBranch> lstCountryBranch = iRemmiterOnlineRegService.getCountryBranch(branchId);
						if(lstCountryBranch != null && lstCountryBranch.size() != 0){
							CountryBranch ipAddBranch = lstCountryBranch.get(0);
							if(ipAddBranch.getIpAddress() != null){
								String ipaddressBranch = ipAddBranch.getIpAddress();
								System.out.println("Branch Ip Address : "+ipaddressBranch);

								branchIp = ((String) ipaddressBranch).split("\\.");
								System.out.println("branchIp : "+branchIp.toString());
								log.info("Entering into login method branchIp :"+branchIp);

								if(branchIp != null && branchIp.length != 0 && localSystemIp != null && localSystemIp.length != 0){

									if(branchIp != null && branchIp.length != 0){
										branchIpaddress =  (branchIpaddress==null ? "" : branchIpaddress.concat(".")).concat(branchIp[0]).concat(".").concat(branchIp[1]).concat(".").concat(branchIp[2]);
										System.out.println(branchIpaddress);
									}

									//if(branchIpaddress != null && localIpadress != null && branchIpaddress.equalsIgnoreCase(localIpadress)){
									/*
									 if(true){	
									   // need to check with view ip address allocated
										systemname = fetchClientSystemName(localFullIpadress);

										String system[] = systemname.split("\\.");
										String sysName = system[0];
										log.error("system name : "+sysName);
										boolean ipAllocated = loginService.checkIpAddressAllocated(userName, sysName, localFullIpadress, countryBranchId, fsEmployeeId);
										if(ipAllocated){
											// allow
											log.info("Success");
										}
										else{
											// throw error message - Invalid Exchange Branch/User 
											log.error("Throw Error Message - Invalid Exchange Branch/User");
											System.out.println("Throw Error Message - Invalid Exchange Branch/User");
											errorMessage = "System is not alloted to you, Please contact Sales Team";
											//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "System is not alloted to you, Please contact Sales Team : "+sysName, "System is not alloted to you, Please contact Sale Team : "+sysName));
											//return;
										}
									}else{
										// throw error message - Invalid Exchange Branch/User 
										log.error("Throw Error Message - Invalid Exchange Branch/User");
										System.out.println("Throw Error Message - Invalid Exchange Branch/User");
										errorMessage = "Invalid Exchange Branch/User";
										//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Exchange Branch/User", "Invalid Exchange Branch/User"));
										//return;
									}*/
								}else{
									// throw error message - Invalid Exchange Branch/User
									log.error("Throw Error Message - Invalid Exchange Branch/User");
									System.out.println("Throw Error Message - Invalid Exchange Branch/User ");
									errorMessage = "Invalid Exchange Branch/User";
									//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Exchange Branch/User", "Invalid Exchange Branch/User"));
									//return;
								}
							}else{
								// ip address in country branch - EX_COUNTRY_BRANCH - Not Available
								errorMessage = "Country Branch, Ip Address Not Available , Please Contact Head Office";
								//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Country Branch, Ip Address Not Available , Please Contact Head Office", "Country Branch, Ip Address Not Available, Please Contact Head Office"));
								//return;
							}
						}else{
							// country branch for that user not available record - EX_COUNTRY_BRANCH
							errorMessage = "Country Branch for User Not Available Record, Please Contact Head Office";
							//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Country Branch for User Not Available Record, Please Contact Head Office", "Country Branch for User Not Available Record, Please Contact Head Office"));
							//return;
						}
					}else{
						// EX_COUNTRY_BRANCH - branchId is empty
						errorMessage = "Employee Branch Code is Empty, Please Contact Head Office";
						//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Employee Branch Code is Empty, Please Contact Head Office", "Employee Branch is Empty, Please Contact Head Office"));
						//return;
					}
				}else{
					errorMessage = "Employee User Type is Empty, Please Contact Head Office";
					//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Employee User Type is Empty, Please Contact Head Office", "Employee User Type is Empty, Please Contact Head Office"));
					//return;
				}
			}
		}catch (Exception e) {
			errorMessage = e.getMessage();
			//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,e.getMessage(), e.getMessage()));
			//return;
		}

		return errorMessage;

	}

	public void login() {
		log.info("Entering into login method");
		String formattedDate = null;
		String lastLoginGMTTime = null;

		/**Added by Rabil on 10/04/2016 */
		if(userName!=null && !userName.equalsIgnoreCase("")){
			loginService.killUserSession(userName);
		}

		if(sessionStateManage.getUserName()!=null && !sessionStateManage.getUserName().equals("root") && !sessionStateManage.getEmail().equals("1") && !sessionStateManage.getUserName().equals(userName))
		{
			count++;
			if(count==1) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "User "+ sessionStateManage.getUserName()+" Logged in!", " "));
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "User  "+ sessionStateManage.getUserName() + " Logged in!", "You are tried "+ count +" times"+ ".Please Sign out  " +sessionStateManage.getUserName() + ". and Try again"));
			}
		} else if(sessionStateManage.getUserName() != null) {

			//cypherSecurity = new CypherSecurityImpl();
			//String userNames = getUserName();
			//String userName1 = new StringBuffer(userNames).reverse().toString().toUpperCase();
			//lstEmpLogin.addAll(loginService.getLoginInfoForEmployees(getApplicationCountryId(), userName, cypherSecurity.encodePassword(password, userName1)));

			List<LoginLogoutHistory> lastLoginLogutList = new ArrayList<LoginLogoutHistory>();
			lstEmpLogin = new ArrayList<Employee>();

			// once login success
			lstEmpLogin.add(getEmployeeDetails());
			if (lstEmpLogin.size() == 1) {

				//passwordDate = lstEmpLogin.get(0).getPasswordDate();
				String CountryBranchStatus = lstEmpLogin.get(0).getFsCountryBranch().getIsActive();
				String branchName = lstEmpLogin.get(0).getFsCountryBranch().getBranchName();
				//BigDecimal fsEmployeeId = lstEmpLogin.get(0).getEmployeeId();

				setAllowFCTrnx(lstEmpLogin.get(0).getAllowFcTransaction()==null?"N":lstEmpLogin.get(0).getAllowFcTransaction()); 

				if(CountryBranchStatus != null && (CountryBranchStatus.equalsIgnoreCase(Constants.Yes))){

					String roleId = lstEmpLogin.get(0).getFsRoleMaster() == null ? "empty" : lstEmpLogin.get(0).getFsRoleMaster().getRoleId().toPlainString();
					List<RoleMaster> rolList = generalService.getRoleList(new BigDecimal(roleId));
					List<CountryMaster> countryAlphaList = generalService.getCountryAlphaList(lstEmpLogin.get(0).getCountryId());
					List<CurrencyMaster> currencyList = generalService.getCountryCurrencyList(lstEmpLogin.get(0).getCountryId());

					if(rolList.size()>0){

						HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
						System.out.println("Local HostAddress: "+request.getRemoteAddr());
						String hostname = getClientIpAddress(request);
						String localFullIpadress = hostname;
						System.out.println("Local host name: "+hostname);

						if(lstEmpLogin.get(0).getLockCount() != null && lstEmpLogin.get(0).getLockCount().compareTo(BigDecimal.ZERO) != 0){
							loginService.unLockEmployeeLogin(getUserName(),localFullIpadress,lstEmpLogin.get(0).getEmployeeId(),Boolean.TRUE);
						}

						// change password not required
						/*HashMap<String, Object> mapObject = checkingPasswordDate(passwordDate);
						if(mapObject != null && mapObject.get("allowStatus") != null && !(Boolean)mapObject.get("allowStatus")){
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userName", userName);
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("appCountry", getApplicationCountryId());
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("location", lstEmpLogin.get(0).getFsCountryBranch().getBranchName());
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("branchCode", lstEmpLogin.get(0).getFsCountryBranch().getBranchId());
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loginUserDetail", lstEmpLogin.get(0).getEmployeeName());
							return;
						}else{
							if((Boolean)mapObject.get("warningStatus")){
								SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								Calendar cal = Calendar.getInstance();
								try {
									cal.setTime(dateFormat.parse(dateFormat.format(new Date())));
								} catch (ParseException e) {
									e.printStackTrace();
								}
								cal.add(Calendar.DATE, 1);
								FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passwordExpiry", "Password will be expired on : " +dateFormat.format(cal.getTime()));
							}
						}*/

						BigDecimal loginid = getLoginLogoutDetail(Constants.USER_TYPE_EMPLOYEEID, lstEmpLogin.get(0).getUserName());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loginid", loginid);
						BigDecimal lastLoginId = loginid.subtract(new BigDecimal(1));
						lastLoginLogutList = (List<LoginLogoutHistory>) loginService.getLastLoginLogoutDetails(lstEmpLogin.get(0).getUserName(), lastLoginId);
						if (lastLoginLogutList.size() > 0) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss aa");
							lastLoginGMTTime = dateFormat.format(lastLoginLogutList.get(0).getLoginTime());
						}

						Date currentDate = generalService.getSysDateTimestamp(sessionStateManage.getCountryId());
						if(currentDate!=null){
							Timestamp currentTime = DateUtil.getLoginCountryCurrentDate(currentDate);
							SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss aa");
							formattedDate = dateFormat.format(currentTime);
						}

						// allocating session objects
						setIsValidate(false);
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userName", userName);
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countryId", lstEmpLogin.get(0).getCountryId());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("companyId", lstEmpLogin.get(0).getFsCompanyMaster().getCompanyId());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("roleId", lstEmpLogin.get(0).getFsRoleMaster().getRoleId());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("branchId", lstEmpLogin.get(0).getFsCountryBranch().getCountryBranchId());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("branchCode", lstEmpLogin.get(0).getFsCountryBranch().getBranchId());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currencyId", currencyList.get(0).getCurrencyId());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("location", lstEmpLogin.get(0).getFsCountryBranch().getBranchName());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("employeeId", lstEmpLogin.get(0).getEmployeeId());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("telephoneNumber", lstEmpLogin.get(0).getTelephoneNumber());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("email", lstEmpLogin.get(0).getEmail());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("customerType", "E");
						// For display login user information in header
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentDateTime", formattedDate);
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loginUserDetail", lstEmpLogin.get(0).getEmployeeName());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("appCountry", getApplicationCountryName());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastLoginTime", lastLoginGMTTime);
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countryAlphaTwoCode", countryAlphaList.get(0).getCountryAlpha2Code());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("wuUsername", lstEmpLogin.get(0).getWuUsername());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", lstEmpLogin.get(0).getUserType()==null ?"1":lstEmpLogin.get(0).getUserType());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fcTrnxInd", lstEmpLogin.get(0).getAllowFcTransaction()==null ?"N":lstEmpLogin.get(0).getAllowFcTransaction());

						if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_MANAGER) ||
								rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_GENERALMANAGER)) {
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../registration/employeehome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../registration/employeehome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_BRANCHSTAFF)) {
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../registration/branchhome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../registration/branchhome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER)) {
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../registration/branchhome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../registration/branchhome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						}else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_CASHIER)) {
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../registration/employeehome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../registration/branchhome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_CHIEF_CASHIER)) {
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../registration/branchhome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../registration/branchhome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_SALES)){
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../common/saleshome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../common/saleshome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_TREASURY)){
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../registration/employeehome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../registration/employeehome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						}else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_REMITTANCE)){
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../registration/employeehome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../registration/employeehome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						}else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_CORPORATE)){
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../registration/corporatehome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../registration/corporatehome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						}else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_AML)){
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../registration/employeehome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../registration/employeehome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						}else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_MARKETING)){
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../common/marketingHome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../common/marketingHome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						} else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_HELP_DESK)) {
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../common/helpDeskHome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../common/helpDeskHome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						}else if (rolList.get(0).getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_FCSTAFF)) {
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuPage", "../registration/corporatehome.xhtml");
							try {
								FacesContext.getCurrentInstance() .getExternalContext().redirect("../registration/corporatehome.xhtml");
							} catch (IOException e) {
								e.printStackTrace();
							}

						}else{
							setIsValidate(true);
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login!", "Please Try Again!"));
							return;
						}
					}
				}else{
					setIsValidate(true);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "This "+branchName.toLowerCase()+" "+"branch is inactivated! ", "Please Contact Help Desk!"));
				}
			} else {
				setIsValidate(true);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login!", "Please Try Again!"));
				return;
			}

		}
		log.info("Exit into login method");
	}

	public BigDecimal getLoginLogoutDetail(String loginType, String userName) {
		log.info("Entering into getLoginLogoutDetail method");
		log.info("loginType==" + loginType);
		log.info("userName==" + userName);

		Date currentDate = generalService.getSysDateTimestamp(sessionStateManage.getCountryId());
		Timestamp currentTime = DateUtil.getLoginCountryCurrentDate(currentDate);

		LoginLogoutHistory loginLogoutHistory = new LoginLogoutHistory();
		loginLogoutHistory.setLoginTime(currentTime);
		loginLogoutHistory.setUserName(getUserName());
		loginLogoutHistory.setLoginType(loginType);
		loginService.saveLoginLogoutDetails(loginLogoutHistory);
		log.info("Exit into getLoginLogoutDetail method");
		return loginLogoutHistory.getLoginLogoutId();
	}

	public Call_Response send_sms(String destination_mobile,
			String message_to_send) {
		// This method is used to send SMS messages to customer

		// In House fields
		String response = null, responseCode = null, responseDesc = null, action_ind = null;
		String service_Provider_Code = "OTP", SEND_SMS_METHOD_IND = "13";

		// Authentication attributes shared by partner
		String senderID = null, username = null, password = null, accountID = null, flexifiled1 = null;

		// Transaction related details
		String language = "L" /* English */, track_id = null, txnID = null;

		Call_Response return_response = new Call_Response();
		SmsServiceSoap_BindingStub otp_bindingstub = null;

		try {
			// Fetching details from DB
			String corsBnk = new String("OTP");
			EoExOwsLoginCredentials exOwsLoginCredentialsObj = loginService.fetchLoginCreditials(corsBnk);
			// Authentication attributes shared by partner
			senderID = exOwsLoginCredentialsObj.getWsPin();
			username = exOwsLoginCredentialsObj.getWsUserName();
			password = exOwsLoginCredentialsObj.getWsPwd();
			accountID = exOwsLoginCredentialsObj.getWsAgentId();
			flexifiled1 = exOwsLoginCredentialsObj.getFlexiFiled1();

			// Transaction related details
			txnID = generate_random_number(8/* number_of_digits */,	true/* useLower */, false/* useUpper */,true/* useDigits */, false/* usePunctuation */);

			if (destination_mobile != null) {
				destination_mobile = destination_mobile.trim();
				// TODO Make sure that you should receive the mobile number with
				// the correct country code
			}

			try {
				// Creating locater Object
				SmsServiceLocator otp_ServiceLocator = new SmsServiceLocator();

				// Setting Binding Stub
				otp_ServiceLocator.setSmsServiceSoapEndpointAddress(flexifiled1);
				otp_bindingstub = (SmsServiceSoap_BindingStub) otp_ServiceLocator.getSmsServiceSoap();

				// Calling External API
				response = otp_bindingstub.sendSMS(destination_mobile,message_to_send, language, senderID, username,password, accountID, String.valueOf(txnID));
			} catch (Exception e) {
				// faultString: A timeout occurred during processing
				// faultString: java.net.ConnectException: Connection timed out:
				// connect
				// faultString: (0)null
				// faultString: (502)Bad Gateway

				if (e instanceof AxisFault) {
					responseCode = String.valueOf(((AxisFault) e).getFaultCode());
					responseDesc = String.valueOf(((AxisFault) e).getFaultString());
				} else {
					return_response.setResult(false);
					return_response.setResponse_desc(responseDesc);
					throw new Exception("Error while connecting inputStream");
				}
			}

			if (response == null) // it could be No response from partner Or
				// error while executing handler class
			{
				return_response.setResult(false);
				return_response.setResponse_desc("No response from SMS sending agent");
			} else {

				response = response.trim(); // no spaces at the beginning

				responseCode = StringUtils.substring(response, 0, 2); // First two character for response code

				if (responseCode == null || responseCode.length() == 0) // it could be No response from partner Or error while executing handler class
				{
					return_response.setResult(false);
					return_response.setResponse_desc("No valid response from SMS sending agent");

					throw new Exception("No ErrorCode Code in the response");
				}

				action_ind = getActionInd(service_Provider_Code,responseCode, SEND_SMS_METHOD_IND);

				if (action_ind != null && action_ind.length() > 0) {
					// Please remember to add any new action_ind that comes from the OWS_PARAM_responseCode table in here As of now we have P, I, R, C, D, T, A, H
					// P, I not sending any mail R, U, T, A, H, HT, HA - BO C, D - IT

					if (action_ind.equalsIgnoreCase("S")) {
						track_id = response.substring(3); // If success, the rest of response will be SMS track ID
						return_response.setResult(true);
						return_response.setResponse_desc(track_id);
					} else {
						return_response.setResult(false);
						return_response.setResponse_desc("Error from SMS sending agent: "+ response.substring(3));
					}
				} else {
					return_response.setResult(false);
					return_response.setResponse_desc("Unknown response from SMS sending agent: "+ responseCode);
				}
			}
		} catch (Exception e) {
			if (return_response.getResult() == false && return_response.getResponse_desc() == null) {
				return_response.setResult(false);
				return_response.setResponse_desc("Unknown Error. Check system logs");
			}
		}

		if (return_response.getResult() == false) // Log incoming data in case
			// of error
		{
			System.out
			.println("----------- Error while sending OTP ------------");
			System.out.println("Error: " + return_response.getResponse_desc());
			System.out.println("Mobile: " + destination_mobile);
		}

		if (otp_bindingstub != null) {
			return_response.setRequest_xml(otp_bindingstub.getRequest_xml());
			return_response.setResponse_xml(otp_bindingstub.getResponse_xml());
		}

		return return_response;
	}

	// getActionInd : To get the appropriate action indicator from OWS_PARAM_RESPCODE
	public String getActionInd(String bankCode, String respCode, String wsCallType)
	{
		String action_ind = null;

		try
		{
			List<EoOwsParamRespcode> owsParamRespCodeQuery_ResultList = loginService.fetchResponseCode(bankCode, respCode, wsCallType);

			if (owsParamRespCodeQuery_ResultList != null && owsParamRespCodeQuery_ResultList.size() == 1)
			{
				action_ind = owsParamRespCodeQuery_ResultList.get(0).getActionInd();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return action_ind;
	}

	public void sendEmailOTPFail(){
		try {

			HashMap<String, String> inputValues=new HashMap<String, String>();

			inputValues.put("userName", sessionStateManage.getUserName());

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String hostname = getClientIpAddress(request);
			inputValues.put("ipAddress", hostname);
			inputValues.put("ecNo", getEmployeeNumber());
			inputValues.put("civilId", getCivilIdNumber());
			inputValues.put("partnerCivilId", getPartnerCivilId());

			List<CountryBranch> countryBranch = generalService.getCountryBranchLocCode(getEmployeeBranchId());
			if(countryBranch != null && countryBranch.size() != 0){
				CountryBranch countryBranchDt = countryBranch.get(0);
				inputValues.put("countryBranchName", countryBranchDt.getBranchName());
			}

			List<ApplicationSetup> lstApplicationSetup = iPersonalRemittanceService.getEmailFromAppSetup(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId());

			// testing purpose blocked
			apllicationMailer1.sendLoginOTPFail(lstApplicationSetup, inputValues);

			// save log email
			saveApplicationEmailLog("Branch Login OTP");

		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendEmailECNoCivilIdFail(){
		try {

			HashMap<String, String> inputValues=new HashMap<String, String>();

			inputValues.put("userName", sessionStateManage.getUserName());

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String hostname = getClientIpAddress(request);
			inputValues.put("ipAddress", hostname);
			inputValues.put("ecNo", getEmployeeNumber());
			inputValues.put("civilId", getCivilIdNumber());
			inputValues.put("DBcivilId", getEmpCivilId());
			//inputValues.put("partnerCivilId", getPartnerCivilId());

			List<CountryBranch> countryBranch = generalService.getCountryBranchLocCode(getEmployeeBranchId());
			if(countryBranch != null && countryBranch.size() != 0){
				CountryBranch countryBranchDt = countryBranch.get(0);
				inputValues.put("countryBranchName", countryBranchDt.getBranchName());
			}

			List<ApplicationSetup> lstApplicationSetup = iPersonalRemittanceService.getEmailFromAppSetup(sessionStateManage.getCompanyId(), sessionStateManage.getCountryId());

			// testing purpose blocked
			apllicationMailer1.sendLoginECNumberCivilIdFail(lstApplicationSetup, inputValues);

			// save log email
			saveApplicationEmailLog("Branch Login EC/CIVIL ID");

		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AMGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// save email log
	public void saveApplicationEmailLog(String emailType){

		ApplicationEmailLog appEmailLog = new ApplicationEmailLog();

		appEmailLog.setEmployeeNumber(getEmployeeNumber());
		appEmailLog.setEmployeeId(getEmployeeId());
		appEmailLog.setUserName(getUserName());
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String hostname = getClientIpAddress(request);
		appEmailLog.setIpAddress(hostname);
		appEmailLog.setCivilId(getCivilIdNumber());
		appEmailLog.setPartnerCivilId(getPartnerCivilId());
		appEmailLog.setEmailType(emailType);
		appEmailLog.setCountryBranchId(getEmployeeBranchId());
		appEmailLog.setCreatedDate(new Date());

		loginService.saveApplicationEmailLog(appEmailLog);

		//appEmailLog.setBankBranchId(bankBranchId);
		//appEmailLog.setBankBranchName(bankBranchName);
		//appEmailLog.setBankCountryId(bankCountryId);
		//appEmailLog.setBankCountryName(bankCountryName);
		//appEmailLog.setBankId(bankId);
		//appEmailLog.setBankName(bankName);
		//appEmailLog.setBeneficiaryId(beneficiaryId);
		//appEmailLog.setBeneficiaryName(beneficiaryName);
		//appEmailLog.setCurrencyId(currencyId);
		//appEmailLog.setCurrencyName(currencyName);
		//appEmailLog.setCustomerId(customerId);
		//appEmailLog.setCustomerName(customerName);
		//appEmailLog.setCustomerReference(customerReference);
	}

	// getter and setters
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getCivilIdNumber() {
		return civilIdNumber;
	}
	public void setCivilIdNumber(String civilIdNumber) {
		this.civilIdNumber = civilIdNumber;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}


	public boolean isBooRenderUserDetails() {
		return booRenderUserDetails;
	}
	public void setBooRenderUserDetails(boolean booRenderUserDetails) {
		this.booRenderUserDetails = booRenderUserDetails;
	}

	public boolean isBooRenderAuthorization() {
		return booRenderAuthorization;
	}
	public void setBooRenderAuthorization(boolean booRenderAuthorization) {
		this.booRenderAuthorization = booRenderAuthorization;
	}

	public boolean isBooRenderPartnerAuthorization() {
		return booRenderPartnerAuthorization;
	}
	public void setBooRenderPartnerAuthorization(boolean booRenderPartnerAuthorization) {
		this.booRenderPartnerAuthorization = booRenderPartnerAuthorization;
	}

	public BigDecimal getSelectCardType() {
		return selectCardType;
	}
	public void setSelectCardType(BigDecimal selectCardType) {
		this.selectCardType = selectCardType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Map<BigDecimal, String> getMapcomIdentityType() {
		return mapcomIdentityType;
	}
	public void setMapcomIdentityType(Map<BigDecimal, String> mapcomIdentityType) {
		this.mapcomIdentityType = mapcomIdentityType;
	}

	public String getApplicationCountryName() {
		return applicationCountryName;
	}
	public void setApplicationCountryName(String applicationCountryName) {
		this.applicationCountryName = applicationCountryName;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public Boolean getBooRenderloginHdrlogoKW() {
		return booRenderloginHdrlogoKW;
	}
	public void setBooRenderloginHdrlogoKW(Boolean booRenderloginHdrlogoKW) {
		this.booRenderloginHdrlogoKW = booRenderloginHdrlogoKW;
	}

	public Boolean getBooRenderloginHdrlogoOM() {
		return booRenderloginHdrlogoOM;
	}
	public void setBooRenderloginHdrlogoOM(Boolean booRenderloginHdrlogoOM) {
		this.booRenderloginHdrlogoOM = booRenderloginHdrlogoOM;
	}

	public List<MarketingData> getLoadMarketData() {
		return loadMarketData;
	}
	public void setLoadMarketData(List<MarketingData> loadMarketData) {
		this.loadMarketData = loadMarketData;
	}

	public BigDecimal getPid() {
		return pid;
	}
	public void setPid(BigDecimal pid) {
		this.pid = pid;
	}

	public String getLoginHdrTitle() {
		return loginHdrTitle;
	}
	public void setLoginHdrTitle(String loginHdrTitle) {
		this.loginHdrTitle = loginHdrTitle;
	}

	public StreamedContent getLoginHdrlogo() {
		return loginHdrlogo;
	}
	public void setLoginHdrlogo(StreamedContent loginHdrlogo) {
		this.loginHdrlogo = loginHdrlogo;
	}

	public String getLoginHdrTxt() {
		return loginHdrTxt;
	}
	public void setLoginHdrTxt(String loginHdrTxt) {
		this.loginHdrTxt = loginHdrTxt;
	}

	public String getLoginArbTitle() {
		return loginArbTitle;
	}
	public void setLoginArbTitle(String loginArbTitle) {
		this.loginArbTitle = loginArbTitle;
	}

	public StreamedContent getLoginArblog() {
		return loginArblog;
	}
	public void setLoginArblog(StreamedContent loginArblog) {
		this.loginArblog = loginArblog;
	}

	public String getLoginArbTxt() {
		return loginArbTxt;
	}
	public void setLoginArbTxt(String loginArbTxt) {
		this.loginArbTxt = loginArbTxt;
	}

	public String getInnerHdrTitle() {
		return innerHdrTitle;
	}
	public void setInnerHdrTitle(String innerHdrTitle) {
		this.innerHdrTitle = innerHdrTitle;
	}

	public StreamedContent getInnerHdrlog() {
		return innerHdrlog;
	}
	public void setInnerHdrlog(StreamedContent innerHdrlog) {
		this.innerHdrlog = innerHdrlog;
	}

	public String getInnerHdrTxt() {
		return innerHdrTxt;
	}
	public void setInnerHdrTxt(String innerHdrTxt) {
		this.innerHdrTxt = innerHdrTxt;
	}

	public String getInnerHdrArbtitle() {
		return innerHdrArbtitle;
	}
	public void setInnerHdrArbtitle(String innerHdrArbtitle) {
		this.innerHdrArbtitle = innerHdrArbtitle;
	}

	public StreamedContent getInnerHdrArbBlog() {
		return innerHdrArbBlog;
	}
	public void setInnerHdrArbBlog(StreamedContent innerHdrArbBlog) {
		this.innerHdrArbBlog = innerHdrArbBlog;
	}

	public String getInnerHdrArbTxt() {
		return innerHdrArbTxt;
	}
	public void setInnerHdrArbTxt(String innerHdrArbTxt) {
		this.innerHdrArbTxt = innerHdrArbTxt;
	}

	public String getLoginfooterTxt() {
		return loginfooterTxt;
	}
	public void setLoginfooterTxt(String loginfooterTxt) {
		this.loginfooterTxt = loginfooterTxt;
	}

	public String getInnerfooterTxt() {
		return innerfooterTxt;
	}
	public void setInnerfooterTxt(String innerfooterTxt) {
		this.innerfooterTxt = innerfooterTxt;
	}

	public StreamedContent getLogBnrImg() {
		return logBnrImg;
	}
	public void setLogBnrImg(StreamedContent logBnrImg) {
		this.logBnrImg = logBnrImg;
	}

	public String getLoginNewsEvent() {
		return loginNewsEvent;
	}
	public void setLoginNewsEvent(String loginNewsEvent) {
		this.loginNewsEvent = loginNewsEvent;
	}

	public String getEmpCivilId() {
		return empCivilId;
	}
	public void setEmpCivilId(String empCivilId) {
		this.empCivilId = empCivilId;
	}

	public boolean isBooRenderPartnerLogin() {
		return booRenderPartnerLogin;
	}
	public void setBooRenderPartnerLogin(boolean booRenderPartnerLogin) {
		this.booRenderPartnerLogin = booRenderPartnerLogin;
	}

	public boolean isBooRenderCivilId() {
		return booRenderCivilId;
	}
	public void setBooRenderCivilId(boolean booRenderCivilId) {
		this.booRenderCivilId = booRenderCivilId;
	}

	public boolean isBooRenderUserOTP() {
		return booRenderUserOTP;
	}
	public void setBooRenderUserOTP(boolean booRenderUserOTP) {
		this.booRenderUserOTP = booRenderUserOTP;
	}

	public boolean isBooRequiredCivilId() {
		return booRequiredCivilId;
	}
	public void setBooRequiredCivilId(boolean booRequiredCivilId) {
		this.booRequiredCivilId = booRequiredCivilId;
	}

	public String getGeneratedPassword() {
		return generatedPassword;
	}
	public void setGeneratedPassword(String generatedPassword) {
		this.generatedPassword = generatedPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getPartnerCivilId() {
		return partnerCivilId;
	}
	public void setPartnerCivilId(String partnerCivilId) {
		this.partnerCivilId = partnerCivilId;
	}

	public String getPartnerPassword() {
		return partnerPassword;
	}
	public void setPartnerPassword(String partnerPassword) {
		this.partnerPassword = partnerPassword;
	}

	public List<Employee> getLstEmployeeDetails() {
		return lstEmployeeDetails;
	}
	public void setLstEmployeeDetails(List<Employee> lstEmployeeDetails) {
		this.lstEmployeeDetails = lstEmployeeDetails;
	}

	public String getAllowFCTrnx() {
		return allowFCTrnx;
	}
	public void setAllowFCTrnx(String allowFCTrnx) {
		this.allowFCTrnx = allowFCTrnx;
	}

	public Boolean getIsValidate() {
		return this.isValidate;
	}
	public void setIsValidate(Boolean isValidate) {
		if(!isValidate) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String hostname = getClientIpAddress(request);
			SessionCacheManager.getInstance().setValid("B" + "#"+ getUserName(),"B" + "#"+ hostname);
		}

		this.isValidate = isValidate;
	}

	public BigDecimal getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public String getSmartCardSelection() {
		return smartCardSelection;
	}
	public void setSmartCardSelection(String smartCardSelection) {
		this.smartCardSelection = smartCardSelection;
	}

	public BigDecimal getEmployeeBranchId() {
		return employeeBranchId;
	}
	public void setEmployeeBranchId(BigDecimal employeeBranchId) {
		this.employeeBranchId = employeeBranchId;
	}

	public BigDecimal getPartnerBranchId() {
		return partnerBranchId;
	}
	public void setPartnerBranchId(BigDecimal partnerBranchId) {
		this.partnerBranchId = partnerBranchId;
	}

	public Employee getEmployeeDetails() {
		return employeeDetails;
	}
	public void setEmployeeDetails(Employee employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

}
