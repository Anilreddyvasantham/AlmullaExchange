package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.bean.RestClient;
import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.service.IUserSignUpService;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

@Component(value = "onLineUsrRegBean")
@Scope("session")
public class UserSignUp<T> implements Serializable {
	/**
	 * 
	 */
	Logger log = Logger.getLogger(UserSignUp.class);
	private static final long serialVersionUID = 1L;
	private String userType;
	private String userName;
	private String checkAvailability;
	private String password;
	private String retypePassword;
	private String secoundaryPassword;
	private String retypeSecoundaryPassword;
	private String email;
	private BigDecimal secureQuest1;
	private BigDecimal secureQuest2;
	private BigDecimal secureQuest3;
	private BigDecimal secureQuest4;
	private BigDecimal secureQuest5;
	private String secureAnsr1;
	private String secureAnsr2;
	private String secureAnsr3;
	private String secureAnsr4;
	private String secureAnsr5;
	private String country;
	private List<CustomerLogin> userList;

	private Map<BigDecimal, String> mapSecurityQuestion = new HashMap<BigDecimal, String>();
	private int flag = 0;
	private int status = 0;
	private String statusMsg;
	private Map<BigDecimal, String> drpdList1 = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> drpdList2 = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> drpdList3 = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> drpdList4 = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> drpdList5 = new HashMap<BigDecimal, String>();

	private boolean boopasswordChec = false;

	@Autowired
	IUserSignUpService<T> iUserSignUp;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	ApplicationMailer mailService;

	@Autowired
	private RestClient restClient;

	@Autowired
	private RuleEngine<T> ruleEngine;

	public RestClient getRestClient() {
		return restClient;
	}

	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}

	public ApplicationMailer getMailService() {
		return mailService;
	}

	public void setMailService(ApplicationMailer mailService) {
		this.mailService = mailService;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public IUserSignUpService<T> getiUserSignUp() {
		return iUserSignUp;
	}

	public void setiUserSignUp(IUserSignUpService<T> iUserSignUp) {
		this.iUserSignUp = iUserSignUp;
	}

	public Map<BigDecimal, String> getMapSecurityQuestion() {
		return mapSecurityQuestion;
	}

	public void setMapSecurityQuestion(Map<BigDecimal, String> mapSecurityQuestion) {
		this.mapSecurityQuestion = mapSecurityQuestion;
	}

	public Map<BigDecimal, String> getDrpdList1() {
		return drpdList1;
	}

	public void setDrpdList1(Map<BigDecimal, String> drpdList1) {
		this.drpdList1 = drpdList1;
	}

	public Map<BigDecimal, String> getDrpdList2() {
		return drpdList2;
	}

	public void setDrpdList2(Map<BigDecimal, String> drpdList2) {
		this.drpdList2 = drpdList2;
	}

	public Map<BigDecimal, String> getDrpdList3() {
		return drpdList3;
	}

	public void setDrpdList3(Map<BigDecimal, String> drpdList3) {
		this.drpdList3 = drpdList3;
	}

	public Map<BigDecimal, String> getDrpdList4() {
		return drpdList4;
	}

	public void setDrpdList4(Map<BigDecimal, String> drpdList4) {
		this.drpdList4 = drpdList4;
	}

	public Map<BigDecimal, String> getDrpdList5() {
		return drpdList5;
	}

	public void setDrpdList5(Map<BigDecimal, String> drpdList5) {
		this.drpdList5 = drpdList5;
	}

	public List<CustomerLogin> getUserList() {
		return userList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setUserList(List<CustomerLogin> userList) {
		this.userList = userList;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCheckAvailability() {
		return checkAvailability;
	}

	public void setCheckAvailability(String checkAvailability) {
		this.checkAvailability = checkAvailability;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public String getSecoundaryPassword() {
		return secoundaryPassword;
	}

	public void setSecoundaryPassword(String secoundaryPassword) {
		this.secoundaryPassword = secoundaryPassword;
	}

	public String getRetypeSecoundaryPassword() {
		return retypeSecoundaryPassword;
	}

	public void setRetypeSecoundaryPassword(String retypeSecoundaryPassword) {
		this.retypeSecoundaryPassword = retypeSecoundaryPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getSecureQuest1() {
		return secureQuest1;
	}

	public void setSecureQuest1(BigDecimal secureQuest1) {
		this.secureQuest1 = secureQuest1;
	}

	public BigDecimal getSecureQuest2() {
		return secureQuest2;
	}

	public void setSecureQuest2(BigDecimal secureQuest2) {
		this.secureQuest2 = secureQuest2;
	}

	public BigDecimal getSecureQuest3() {
		return secureQuest3;
	}

	public void setSecureQuest3(BigDecimal secureQuest3) {
		this.secureQuest3 = secureQuest3;
	}

	public BigDecimal getSecureQuest5() {
		return secureQuest5;
	}

	public void setSecureQuest5(BigDecimal secureQuest5) {
		this.secureQuest5 = secureQuest5;
	}

	public BigDecimal getSecureQuest4() {
		return secureQuest4;
	}

	public void setSecureQuest4(BigDecimal secureQuest4) {
		this.secureQuest4 = secureQuest4;
	}

	public String getSecureAnsr1() {
		return secureAnsr1;
	}

	public void setSecureAnsr1(String secureAnsr1) {
		this.secureAnsr1 = secureAnsr1;
	}

	public String getSecureAnsr2() {
		return secureAnsr2;
	}

	public void setSecureAnsr2(String secureAnsr2) {
		this.secureAnsr2 = secureAnsr2;
	}

	public String getSecureAnsr3() {
		return secureAnsr3;
	}

	public void setSecureAnsr3(String secureAnsr3) {
		this.secureAnsr3 = secureAnsr3;
	}

	public String getSecureAnsr4() {
		return secureAnsr4;
	}

	public void setSecureAnsr4(String secureAnsr4) {
		this.secureAnsr4 = secureAnsr4;
	}

	public String getSecureAnsr5() {
		return secureAnsr5;
	}

	public void setSecureAnsr5(String secureAnsr5) {
		this.secureAnsr5 = secureAnsr5;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public void cancel() {
		reset();
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../almullagroup/almullagroup.xhtml");
			setCountry(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* method to get Security Question from DataBase */

	/*
	 * method to check the userName is exist or not
	 */
	public List<CustomerLogin> getUserList(String userName) {
		userList = new ArrayList<CustomerLogin>();
		userList.addAll(getiUserSignUp().searchUser(userName));
		return userList;
	}

	/*
	 * method to get the country Name name and country code from dataBase
	 */
	public List<CountryMasterDesc> getCountryNameList() {

		SessionStateManage sessionStateManage = new SessionStateManage();
		return getGeneralService().getBusinessCountryList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
	}

	public void changeCountry() {

		/*
		 * Map<String, String> mapCountryList =
		 * ruleEngine.getComponentDataList("signup:country");
		 */
		SessionStateManage manage = new SessionStateManage();
		manage.setSessionValue("countryId", getCountry());
		mapSecurityQuestion = new HashMap<BigDecimal, String>();
		drpdList1 = new HashMap<BigDecimal, String>();
		drpdList2 = new HashMap<BigDecimal, String>();
		drpdList3 = new HashMap<BigDecimal, String>();
		drpdList4 = new HashMap<BigDecimal, String>();
		drpdList5 = new HashMap<BigDecimal, String>();
		ruleEngine.init();

	}

	/*@SuppressWarnings("unchecked")
	public Map<BigDecimal, String> getCountryNameList() {

		SessionStateManage sessionStateManage = new SessionStateManage();
		
		Map<BigDecimal, String> mapCountryList = null;
		try{
			JSONObject object = new JSONObject();
			JSONObject objectParams = new JSONObject();
			objectParams.put("languageId", sessionStateManage.getLanguageId());
			objectParams.put("countryActive", "Y");
			object.put("input", objectParams);
			mapCountryList = JsonUtil.parseMapObject(restClient.postRequest(ServiceConfig.COUNTRY_LIST, object));
			mapCountryList = JsonUtil.sortMapByValueAsc(mapCountryList);
		}catch(Exception e){
			mapCountryList = new HashMap<BigDecimal, String>();
			e.printStackTrace();
		}
		 
		return mapCountryList;
		
		return getGeneralService().getAllCountry(sessionStateManage.getLanguageId());
	}*/

	/* method to save the form data in dataBase */
	/* public String redirectLogin(){ return "login"; } */

	public void redirectLogin() {

		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../almullagroup/almullagroup.xhtml");

			context.invalidateSession();
			setCountry(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*private int getSQAnswerCount(){
		
		int answerCount = 0;
		if(!getSecureAnsr1().trim().equals("") && getSecureQuest1()!=null && getSecureQuest1().intValue()!=0){
			answerCount++;
		}
		if(!getSecureAnsr2().trim().equals("") && getSecureQuest2()!=null && getSecureQuest2().intValue()!=0){
			answerCount++;
		}
		if(!getSecureAnsr3().trim().equals("") && getSecureQuest3()!=null && getSecureQuest3().intValue()!=0){
			answerCount++;
		}
		if(!getSecureAnsr4().trim().equals("") && getSecureQuest4()!=null && getSecureQuest4().intValue()!=0){
			answerCount++;
		}
		if(!getSecureAnsr5().trim().equals("") && getSecureQuest5()!=null && getSecureQuest5().intValue()!=0){
			answerCount++;
		}
		return answerCount;
	}*/

	public String saveOnlineUsrData() {

		ResourceBundle resourceBundle = ruleEngine.getResourceBundle();
		setBoopasswordChec(validate());
		boopasswordChec = validate();
		String page = null;
		if (getBooEmailCheck() == false) {
			if (isBoopasswordChec()) {
				page = "";
				FacesContext.getCurrentInstance().addMessage("signup:retypesecondarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", (resourceBundle.getString("lbl.passmatch"))));
			} else {
				int languageID = 1;
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")) {
					languageID = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().equalsIgnoreCase("ar") ? 2 : 1;
				}

				if (getSecureQuest1() != null && getSecureQuest1().intValue() != 0 && getSecureAnsr1().trim().equals("")) {
					FacesContext.getCurrentInstance().addMessage("signup:secAns1", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Security Answer required"));
				}
				if (getSecureQuest2() != null && getSecureQuest2().intValue() != 0 && getSecureAnsr2().trim().equals("")) {
					FacesContext.getCurrentInstance().addMessage("signup:secAns2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Security Answer required"));
				}
				if (getSecureQuest3() != null && getSecureQuest3().intValue() != 0 && getSecureAnsr3().trim().equals("")) {
					FacesContext.getCurrentInstance().addMessage("signup:secAns3", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Security Answer required"));
				}
				if (getSecureQuest4() != null && getSecureQuest4().intValue() != 0 && getSecureAnsr4().trim().equals("")) {
					FacesContext.getCurrentInstance().addMessage("signup:secAns4", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Security Answer required"));
				}
				if (getSecureQuest5() != null && getSecureQuest5().intValue() != 0 && getSecureAnsr5().trim().equals("")) {
					FacesContext.getCurrentInstance().addMessage("signup:secAns5", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Security Answer required"));
				}

				if (!getPassword().equals(getRetypePassword())) {
				FacesContext.getCurrentInstance().addMessage("signup:primarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.primarypassword"))+" "+(resourceBundle.getString("lbl.shouldmatchwith"))+(resourceBundle.getString("lbl.retypeprimarypassword"))));
				}
				if (!getSecoundaryPassword().equals(getRetypeSecoundaryPassword())) {
				FacesContext.getCurrentInstance().addMessage("signup:secondarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.secondarypassword"))+" "+(resourceBundle.getString("lbl.shouldmatchwith"))+(resourceBundle.getString("lbl.retypesecondarypassword"))));
				}

			/*if(getSQAnswerCount()<mapComponentBehavior.get("Security Question").getMinValue().intValue()){
				FacesContext.getCurrentInstance().addMessage("signup:securityQuestionPanel", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Atleast "+(mapComponentBehavior.get("Security Question Answers").getMinValue().intValue())+" Security question and answer is requried" ));
			}*/

				iCypherSecurity cypherSecurity = new CypherSecurityImpl();
				String secretKey = getUserName();
				CustomerLogin objregusr = new CustomerLogin();
				objregusr.setEmail(getEmail());
				objregusr.setUserType("Individual");
				objregusr.setUserName(getUserName());
				objregusr.setPassword(cypherSecurity.encodePassword(getPassword(), secretKey));
				objregusr.setSecondaryPassword(cypherSecurity.encodePassword(getSecoundaryPassword(), secretKey));

				BizComponentData bizComponentData;

				if (getSecureQuest1() != null && getSecureQuest1().intValue() != 0 && !getSecureAnsr1().trim().equals("")) {
					bizComponentData = new BizComponentData();
					bizComponentData.setComponentDataId(getSecureQuest1());
					objregusr.setFsBizComponentDataBySecurityQuestion1(bizComponentData);
					objregusr.setSecurityAnswer1(cypherSecurity.encodePassword(getSecureAnsr1(), secretKey));
				}
				if (getSecureQuest2() != null && getSecureQuest2().intValue() != 0 && !getSecureAnsr2().trim().equals("")) {
					bizComponentData = new BizComponentData();
					bizComponentData.setComponentDataId(getSecureQuest2());
					objregusr.setFsBizComponentDataBySecurityQuestion2(bizComponentData);
					objregusr.setSecurityAnswer2(cypherSecurity.encodePassword(getSecureAnsr2(), secretKey));
				}
				if (getSecureQuest3() != null && getSecureQuest3().intValue() != 0 && !getSecureAnsr3().trim().equals("")) {
					bizComponentData = new BizComponentData();
					bizComponentData.setComponentDataId(getSecureQuest3());
					objregusr.setFsBizComponentDataBySecurityQuestion3(bizComponentData);
					objregusr.setSecurityAnswer3(cypherSecurity.encodePassword(getSecureAnsr3(), secretKey));
				}
				if (getSecureQuest4() != null && getSecureQuest4().intValue() != 0 && !getSecureAnsr4().trim().equals("")) {
					bizComponentData = new BizComponentData();
					bizComponentData.setComponentDataId(getSecureQuest4());
					objregusr.setFsBizComponentDataBySecurityQuestion4(bizComponentData);
					objregusr.setSecurityAnswer4(cypherSecurity.encodePassword(getSecureAnsr4(), secretKey));
				}
				if (getSecureQuest5() != null && getSecureQuest5().intValue() != 0 && !getSecureAnsr5().trim().equals("")) {
					bizComponentData = new BizComponentData();
					bizComponentData.setComponentDataId(getSecureQuest5());
					objregusr.setFsBizComponentDataBySecurityQuestion5(bizComponentData);
					objregusr.setSecurityAnswer5(cypherSecurity.encodePassword(getSecureAnsr5(), secretKey));
				}

				objregusr.setCreatedBy(getUserName());
				objregusr.setCreationDate(new Date());

				// Insert Country ID
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(getCountry().equals("") ? null : new BigDecimal(getCountry()));
				objregusr.setFsCountryMaster(countryMaster);

				// Insert Language ID from session
				LanguageType languageType = new LanguageType();
				languageType.setLanguageId(new BigDecimal(languageID));
				objregusr.setFsLanguageType(languageType);

				if (FacesContext.getCurrentInstance().getMessageList().size() == 0) {
					page = "success";
					reset();
					getiUserSignUp().saveOnlineUsrData(objregusr);
					try {
						getMailService().sendRegistrationMail(getEmail(), "Successfully Registered", getUserName());
					} catch (Exception e) {
						log.error("Problem to send Email  To:" + getEmail() + " User:" + getUserName());
						try {
							ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
							context.invalidateSession();
							context.redirect("login.xhtml");
						} catch (Exception ex) {
							log.error("Problem to Redirect the page to Login Page: ", ex);
						}
					}
				}
			}
		}
		return page;
	}

	private void clearSecurityQuestions() {

		drpdList1 = new HashMap<BigDecimal, String>();
		drpdList2 = new HashMap<BigDecimal, String>();
		drpdList3 = new HashMap<BigDecimal, String>();
		drpdList4 = new HashMap<BigDecimal, String>();
		drpdList5 = new HashMap<BigDecimal, String>();
	}

	private void addSecurityQuestions(Map<BigDecimal, String> mapSQ) {

		drpdList1.putAll(mapSQ);
		drpdList2.putAll(mapSQ);
		drpdList3.putAll(mapSQ);
		drpdList4.putAll(mapSQ);
		drpdList5.putAll(mapSQ);
	}

	public Map<BigDecimal, String> getQuestionOne() {

		generateSecurityQuestions();
		return drpdList1;
	}

	public void generateSecurityQuestions() {

		clearSecurityQuestions();

		Map<BigDecimal, String> mapTempSQList = new HashMap<BigDecimal, String>();

		if (getMapSecurityQuestion() == null || getMapSecurityQuestion().size() == 0) {
			try {
				mapSecurityQuestion = ruleEngine.getComponentData("Security Question");
			} catch (Exception e) {
			}
		}

		mapTempSQList.putAll(getMapSecurityQuestion());

		mapTempSQList.remove(getSecureQuest1());
		mapTempSQList.remove(getSecureQuest2());
		mapTempSQList.remove(getSecureQuest3());
		mapTempSQList.remove(getSecureQuest4());
		mapTempSQList.remove(getSecureQuest5());

		addSecurityQuestions(mapTempSQList);

		if (getSecureQuest1() != null && getSecureQuest1().intValue() != 0) {
			drpdList1.put(getSecureQuest1(), getMapSecurityQuestion().get(getSecureQuest1()));
		}
		if (getSecureQuest2() != null && getSecureQuest2().intValue() != 0) {
			drpdList2.put(getSecureQuest2(), getMapSecurityQuestion().get(getSecureQuest2()));
		}
		if (getSecureQuest3() != null && getSecureQuest3().intValue() != 0) {
			drpdList3.put(getSecureQuest3(), getMapSecurityQuestion().get(getSecureQuest3()));
		}
		if (getSecureQuest4() != null && getSecureQuest4().intValue() != 0) {
			drpdList4.put(getSecureQuest4(), getMapSecurityQuestion().get(getSecureQuest4()));
		}
		if (getSecureQuest5() != null && getSecureQuest5().intValue() != 0) {
			drpdList5.put(getSecureQuest5(), getMapSecurityQuestion().get(getSecureQuest5()));
		}

	}

	/*
	 * method to get all users name from data base and to check the user name is
	 * exist or not
	 */
	public void currentStatus(AjaxBehaviorEvent event) {
		try {
			setFlag(1);
			List<CustomerLogin> userList = getUserList(getUserName());
			if (userList.size() == 0) {
				setStatusMsg("");
				setStatus(1);
			} else {
				setStatusMsg("");
				FacesContext.getCurrentInstance().addMessage("signup:username", new FacesMessage(FacesMessage.SEVERITY_ERROR, "User Already Exists...", "User Already Exists..."));
				setUserName("");
				setStatus(0);
				setFlag(0);
			}
		} catch (HibernateException he) {
			he.printStackTrace();

		} catch (Exception e) {

		}
	}

	public void resetCurrentStatus() {
		setStatusMsg("");
		setStatus(1);
		setFlag(1);
	}

	private boolean validate() {

		boolean check = false;

		if (this.password.equalsIgnoreCase(this.secoundaryPassword)) {
			check = true;
		} else {
			check = false;
		}
		return check;
	}

	public boolean isBoopasswordChec() {
		return boopasswordChec;
	}

	public void setBoopasswordChec(boolean boopasswordChec) {
		this.boopasswordChec = boopasswordChec;
	}

	public boolean reset() {

		userType = "";
		userName = "";
		checkAvailability = "";
		password = "";
		retypePassword = "";
		secoundaryPassword = "";
		retypeSecoundaryPassword = "";
		email = "";
		secureQuest1 = null;
		secureQuest2 = null;
		secureQuest3 = null;
		secureQuest4 = null;
		secureQuest5 = null;
		secureAnsr1 = "";
		secureAnsr2 = "";
		secureAnsr3 = "";
		secureAnsr4 = "";
		secureAnsr5 = "";
		country = "";
		statusMsg = "";

		flag = 0;
		status = 0;
		boopasswordChec = false;
		userList = null;
		setBooEmailCheck(false);
		mapSecurityQuestion = new HashMap<BigDecimal, String>();
		drpdList1 = new HashMap<BigDecimal, String>();
		drpdList2 = new HashMap<BigDecimal, String>();
		drpdList3 = new HashMap<BigDecimal, String>();
		drpdList4 = new HashMap<BigDecimal, String>();
		drpdList5 = new HashMap<BigDecimal, String>();

		return false;
	}

	private Boolean booEmailCheck = false;

	public Boolean getBooEmailCheck() {
		return booEmailCheck;
	}

	public void setBooEmailCheck(Boolean booEmailCheck) {
		this.booEmailCheck = booEmailCheck;
	}

	public void checkEmail() {
		setBooEmailCheck(false);
		List<CustomerLogin> matchEmail = new ArrayList<CustomerLogin>();
		matchEmail.addAll(getGeneralService().getEmailCheckUser(getEmail()));

		if (matchEmail.size() > 0) {
			setBooEmailCheck(true);

		} else {

			setBooEmailCheck(false);

		}
	}

}