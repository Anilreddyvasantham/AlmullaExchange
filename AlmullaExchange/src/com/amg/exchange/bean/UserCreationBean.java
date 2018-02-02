package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.bean.RestClient;
import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.service.IEncrptionDescriptionService;
import com.amg.exchange.registration.service.IUserCreationService;
import com.amg.exchange.registration.service.IUserSignUpService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.ExUtils;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.UserRegUtils;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

@Component(value = "userCreationBean")
@Scope("session")
public class UserCreationBean<T> implements Serializable{

	Logger log = Logger.getLogger(UserSignUp.class);
	private static final long serialVersionUID = 1L;
	private BigDecimal customerLoginId=null; 
	private String country;
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
	private boolean boopasswordChec = false;

	private Boolean renderUserNamepanel=true;
	private Boolean renderPasswordpanel=false;
	private Boolean renderSecurityQuespanel = false;;
	private Boolean renderPhisingImagepanel = false;
	private Boolean renderSuccesspanel = false;
	private String caption;
	private String imageUrl;  
	private String saveImageUrl;
	private Boolean booIdTypeCheck = false;
	private BigDecimal companyId= null;
	private BigDecimal customerId = null;

	//SessionStateManage sessionStateManage = new SessionStateManage();

	// Timezone for application country based creation log
	private Date currentTime;



	public Date getCurrentTime() {
		Date objList = getGeneralService().getSysDateTimestamp(new BigDecimal(getCountry()));

		if(objList != null){
			currentTime =objList;}
		else{
			currentTime =null;
		}
		return currentTime; 
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}


	public String getImageUrl() {
		setSaveImageUrl(imageUrl);
		return imageUrl;
	}
	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getSaveImageUrl() {
		return saveImageUrl;
	}
	public void setSaveImageUrl(String saveImageUrl) {
		this.saveImageUrl = saveImageUrl;
	}
	public BigDecimal getCustomerLoginId() {
		return customerLoginId;
	}
	public void setCustomerLoginId(BigDecimal customerLoginId) {
		this.customerLoginId = customerLoginId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
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
	public BigDecimal getSecureQuest4() {
		return secureQuest4;
	}
	public void setSecureQuest4(BigDecimal secureQuest4) {
		this.secureQuest4 = secureQuest4;
	}
	public BigDecimal getSecureQuest5() {
		return secureQuest5;
	}
	public void setSecureQuest5(BigDecimal secureQuest5) {
		this.secureQuest5 = secureQuest5;
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
	public List<CustomerLogin> getUserList() {
		return userList;
	}
	public void setUserList(List<CustomerLogin> userList) {
		this.userList = userList;
	}
	public Map<BigDecimal, String> getMapSecurityQuestion() {
		return mapSecurityQuestion;
	}
	public void setMapSecurityQuestion(Map<BigDecimal, String> mapSecurityQuestion) {
		this.mapSecurityQuestion = mapSecurityQuestion;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
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
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public Map<BigDecimal, String> getDrpdList1() {
		generateSecurityQuestions();
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



	public Boolean getRenderUserNamepanel() {
		return renderUserNamepanel;
	}
	public void setRenderUserNamepanel(Boolean renderUserNamepanel) {
		this.renderUserNamepanel = renderUserNamepanel;
	}
	public Boolean getRenderPasswordpanel() {
		return renderPasswordpanel;
	}
	public void setRenderPasswordpanel(Boolean renderPasswordpanel) {
		this.renderPasswordpanel = renderPasswordpanel;
	}
	public Boolean getRenderSecurityQuespanel() {
		return renderSecurityQuespanel;
	}
	public void setRenderSecurityQuespanel(Boolean renderSecurityQuespanel) {
		this.renderSecurityQuespanel = renderSecurityQuespanel;
	}
	public Boolean getRenderPhisingImagepanel() {
		return renderPhisingImagepanel;
	}
	public void setRenderPhisingImagepanel(Boolean renderPhisingImagepanel) {
		this.renderPhisingImagepanel = renderPhisingImagepanel;
	}
	public Boolean getRenderSuccesspanel() {
		return renderSuccesspanel;
	}
	public void setRenderSuccesspanel(Boolean renderSuccesspanel) {
		this.renderSuccesspanel = renderSuccesspanel;
	}



	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}



	public Boolean getBooIdTypeCheck() {
		return booIdTypeCheck;
	}

	public void setBooIdTypeCheck(Boolean booIdTypeCheck) {
		this.booIdTypeCheck = booIdTypeCheck;
	}



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


	@Autowired
	IUserCreationService<T> iuserCreation;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	ApplicationMailer mailService;

	/*@ManagedProperty(value="#{restClient}")*/
	@Autowired
	private RestClient restClient;

	@Autowired
	private RuleEngine<T> ruleEngine;

	@Autowired
	IEncrptionDescriptionService<T> encryptionDescriptionService;

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

	/*
	 * method to check the userName is exist or not
	 */
	public List<CustomerLogin> getUserList(String userName) {
		userList = new ArrayList<CustomerLogin>();
		userList.addAll(iuserCreation.searchUser(userName));
		return userList;
	}

	/*
	 * method to get the country Name name and country code from dataBase
	 */
	public List<CountryMasterDesc> getCountryNameList() {

		SessionStateManage sessionStateManage = new SessionStateManage();
		return getGeneralService().getBusinessCountryList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"));
	}

	public void changeCountry(){


		SessionStateManage manage = new SessionStateManage();
		manage.setSessionValue("countryId", getCountry());
		mapSecurityQuestion = new HashMap<BigDecimal, String>();
		drpdList1 = new HashMap<BigDecimal, String>();
		drpdList2 = new HashMap<BigDecimal, String>();
		drpdList3 = new HashMap<BigDecimal, String>();
		drpdList4 = new HashMap<BigDecimal, String>();
		drpdList5 = new HashMap<BigDecimal, String>();


	}



	public void redirectLogin(){
		reset();
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../almullagroup/almullagroup.xhtml");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private int getSQAnswerCount(){

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
	}

	public String saveOnlineUsrData() {

		ResourceBundle resourceBundle = ruleEngine.getResourceBundle();
		setBoopasswordChec(validate());
		boopasswordChec = validate();
		String page = null;  
		if(getBooEmailCheck()==false){
			if(isBoopasswordChec()){
				page = "";
				FacesContext.getCurrentInstance().addMessage("signup:retypesecondarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.passmatch"))));
			} else {
				int languageID = 1;
				if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")){
					languageID = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().equalsIgnoreCase("ar")?2:1;
				}

				if(getSecureQuest1()!=null && getSecureQuest1().intValue()!=0 && getSecureAnsr1().trim().equals("")){
					FacesContext.getCurrentInstance().addMessage("signup:secAns1", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Security Answer required"));
				}
				if(getSecureQuest2()!=null && getSecureQuest2().intValue()!=0 && getSecureAnsr2().trim().equals("")){
					FacesContext.getCurrentInstance().addMessage("signup:secAns2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Security Answer required"));
				}
				if(getSecureQuest3()!=null && getSecureQuest3().intValue()!=0 && getSecureAnsr3().trim().equals("")){
					FacesContext.getCurrentInstance().addMessage("signup:secAns3", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Security Answer required"));
				}
				if(getSecureQuest4()!=null && getSecureQuest4().intValue()!=0 && getSecureAnsr4().trim().equals("")){
					FacesContext.getCurrentInstance().addMessage("signup:secAns4", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Security Answer required"));
				}
				if(getSecureQuest5()!=null && getSecureQuest5().intValue()!=0 && getSecureAnsr5().trim().equals("")){
					FacesContext.getCurrentInstance().addMessage("signup:secAns5", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Security Answer required"));
				}

				if(!getPassword().equals(getRetypePassword())){
					FacesContext.getCurrentInstance().addMessage("signup:primarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.primarypassword"))+" "+(resourceBundle.getString("lbl.shouldmatchwith"))+(resourceBundle.getString("lbl.retypeprimarypassword"))));
				}
				if(!getSecoundaryPassword().equals(getRetypeSecoundaryPassword())){
					FacesContext.getCurrentInstance().addMessage("signup:secondarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.secondarypassword"))+" "+(resourceBundle.getString("lbl.shouldmatchwith"))+(resourceBundle.getString("lbl.retypesecondarypassword"))));
				}

				/*if(getSQAnswerCount()<mapComponentBehavior.get("Security Question").getMinValue().intValue()){
				FacesContext.getCurrentInstance().addMessage("signup:securityQuestionPanel", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Atleast "+(mapComponentBehavior.get("Security Question Answers").getMinValue().intValue())+" Security question and answer is requried" ));
			}*/

				iCypherSecurity cypherSecurity = new CypherSecurityImpl();
				String secretKey = getUserName();
				CustomerLogin customerLogin = new CustomerLogin();
				customerLogin.setEmail(getEmail());
				customerLogin.setUserType("Individual");
				customerLogin.setUserName(getUserName());
				customerLogin.setPassword(cypherSecurity.encodePassword(getPassword(), secretKey));
				customerLogin.setSecondaryPassword(cypherSecurity.encodePassword(getSecoundaryPassword(), secretKey));


				BizComponentData bizComponentData;

				if(getSecureQuest1()!=null && getSecureQuest1().intValue()!=0 && !getSecureAnsr1().trim().equals("")){
					bizComponentData = new BizComponentData();
					bizComponentData.setComponentDataId(getSecureQuest1());
					customerLogin.setFsBizComponentDataBySecurityQuestion1(bizComponentData);
					customerLogin.setSecurityAnswer1(cypherSecurity.encodePassword(getSecureAnsr1(), secretKey));
				}
				if(getSecureQuest2()!=null && getSecureQuest2().intValue()!=0 && !getSecureAnsr2().trim().equals("")){
					bizComponentData = new BizComponentData();
					bizComponentData.setComponentDataId(getSecureQuest2());
					customerLogin.setFsBizComponentDataBySecurityQuestion2(bizComponentData);
					customerLogin.setSecurityAnswer2(cypherSecurity.encodePassword(getSecureAnsr2(), secretKey));
				}
				if(getSecureQuest3()!=null && getSecureQuest3().intValue()!=0 && !getSecureAnsr3().trim().equals("")){
					bizComponentData = new BizComponentData();
					bizComponentData.setComponentDataId(getSecureQuest3());
					customerLogin.setFsBizComponentDataBySecurityQuestion3(bizComponentData);
					customerLogin.setSecurityAnswer3(cypherSecurity.encodePassword(getSecureAnsr3(), secretKey));
				}
				if(getSecureQuest4()!=null && getSecureQuest4().intValue()!=0 && !getSecureAnsr4().trim().equals("")){
					bizComponentData = new BizComponentData();
					bizComponentData.setComponentDataId(getSecureQuest4());
					customerLogin.setFsBizComponentDataBySecurityQuestion4(bizComponentData);
					customerLogin.setSecurityAnswer4(cypherSecurity.encodePassword(getSecureAnsr4(), secretKey));
				}
				if(getSecureQuest5()!=null && getSecureQuest5().intValue()!=0 && !getSecureAnsr5().trim().equals("")){
					bizComponentData = new BizComponentData();
					bizComponentData.setComponentDataId(getSecureQuest5());
					customerLogin.setFsBizComponentDataBySecurityQuestion5(bizComponentData);
					customerLogin.setSecurityAnswer5(cypherSecurity.encodePassword(getSecureAnsr5(), secretKey));
				}

				customerLogin.setCreatedBy(getUserName());
				//CR for creation date based on application country
				//customerLogin.setCreationDate(new Date());
				customerLogin.setCreationDate(getCurrentTime());


				//Insert Country ID
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(getCountry().equals("")?null:new BigDecimal(getCountry()));
				customerLogin.setFsCountryMaster(countryMaster);

				//Insert Language ID from session   
				LanguageType languageType = new LanguageType();
				languageType.setLanguageId(new BigDecimal(languageID));
				customerLogin.setFsLanguageType(languageType);

				if(FacesContext.getCurrentInstance().getMessageList().size()==0){
					page = "success";
					reset();
					iuserCreation.saveOnlineUsrData(customerLogin);
					try{
						getMailService().sendRegistrationMail(getEmail(), "Successfully Registered", getUserName());
					} catch(Exception e) {
						log.info("Problem to send Email  To:"+getEmail()+" User:"+getUserName());
						try {
							ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
							context.invalidateSession();
							context.redirect("login.xhtml");
						} catch(Exception ex) {
							log.info("Problem to Redirect the page to Login Page: "+ex);
						}
					}
				}
			}
		}
		return page;
	}

	private void clearSecurityQuestions(){

		drpdList1 = new HashMap<BigDecimal, String>();
		drpdList2 = new HashMap<BigDecimal, String>();
		drpdList3 = new HashMap<BigDecimal, String>();
		drpdList4 = new HashMap<BigDecimal, String>();
		drpdList5 = new HashMap<BigDecimal, String>();
	}

	private void addSecurityQuestions(Map<BigDecimal, String> mapSQ){

		drpdList1.putAll(mapSQ);
		drpdList2.putAll(mapSQ);
		drpdList3.putAll(mapSQ);
		drpdList4.putAll(mapSQ);
		drpdList5.putAll(mapSQ);
	}

	public Map<BigDecimal, String> getQuestionOne(){

		generateSecurityQuestions();
		return drpdList1;
	}

	@SuppressWarnings("unchecked")
	public void generateSecurityQuestions(){

		clearSecurityQuestions();

		Map<BigDecimal, String> mapTempSQList = new HashMap<BigDecimal, String>();

		if(getMapSecurityQuestion()==null || getMapSecurityQuestion().size()==0){
			try{
				mapSecurityQuestion = ruleEngine.getComponentData("Security Question");
			}catch(Exception e){}
		}

		mapTempSQList.putAll(getMapSecurityQuestion());

		mapTempSQList.remove(getSecureQuest1());
		mapTempSQList.remove(getSecureQuest2());
		mapTempSQList.remove(getSecureQuest3());
		mapTempSQList.remove(getSecureQuest4());
		mapTempSQList.remove(getSecureQuest5());

		addSecurityQuestions(mapTempSQList);

		if(getSecureQuest1()!=null  && getSecureQuest1().intValue()!=0){
			drpdList1.put(getSecureQuest1(), getMapSecurityQuestion().get(getSecureQuest1()));
		}
		if(getSecureQuest2()!=null && getSecureQuest2().intValue()!=0){
			drpdList2.put(getSecureQuest2(), getMapSecurityQuestion().get(getSecureQuest2()));
		}
		if(getSecureQuest3()!=null && getSecureQuest3().intValue()!=0){
			drpdList3.put(getSecureQuest3(), getMapSecurityQuestion().get(getSecureQuest3()));
		}
		if(getSecureQuest4()!=null && getSecureQuest4().intValue()!=0){
			drpdList4.put(getSecureQuest4(), getMapSecurityQuestion().get(getSecureQuest4()));
		}
		if(getSecureQuest5()!=null && getSecureQuest5().intValue()!=0){
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
			setBooIdTypeCheck(false);
			List<CustomerLogin> userList = getUserList(getUserName());
			if (userList.size() == 0) {
				setStatusMsg ("");
				setStatus(1);
				//added by nazish for CR 22-06-2015
				if(getCountry()!=null){

					getCountryAlphCode();
					if(getCountryAlphaTwoCode().equalsIgnoreCase(Constants.KUWAIT_ALPHA_TWO_CODE)){
						civilIdValidation();
					}

				}

			} else {
				setStatusMsg ("");
				FacesContext.getCurrentInstance().addMessage("usercreation:username", new FacesMessage(FacesMessage.SEVERITY_ERROR, "User Already Exists...", "User Already Exists..."));
				setUserName("");
				setStatus(0);
				setFlag(0);
			}
		} catch (HibernateException he) {
			he.printStackTrace();

		} catch(Exception e){

		}
	}

	public void resetCurrentStatus() {
		setStatusMsg ("");
		setStatus(1);
		setFlag(1);
	}

	private boolean validate(){

		boolean check = false;

		if(this.password.equalsIgnoreCase(this.secoundaryPassword)){
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

	public boolean reset(){
		setBooIdTypeCheck(false);
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
		caption = "";
		saveImageUrl = "";
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
		List<CustomerLogin> matchEmail =  new ArrayList<CustomerLogin>();
		matchEmail.addAll(getGeneralService().getEmailCheckUser(getEmail()));


		if (matchEmail.size()>0) {
			setBooEmailCheck(true);


		} else {

			setBooEmailCheck(false);

		}
	}

	public void popInfo(){

		RequestContext.getCurrentInstance().execute("openInfo.show();");
	}
	CustomerLogin customerLogin = null;


	public void saveUserPanel(){

		//	customerLogin = new CustomerLogin();

		if(getBooEmailCheck()==false && getBooIdTypeCheck()==false){
			/*int languageID = 1;
		if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")){
			languageID = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().equalsIgnoreCase("ar")?2:1;
		}*/
			//Insert Country ID
			/*CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(getCountry().equals("")?null:new BigDecimal(getCountry()));
		customerLogin.setFsCountryMaster(countryMaster);

		//Insert Language ID from session   
		LanguageType languageType = new LanguageType();
		languageType.setLanguageId(new BigDecimal(languageID));
		customerLogin.setFsLanguageType(languageType);


		customerLogin.setCreatedBy(getUserName());
		customerLogin.setCreationDate(new Date());


		customerLogin.setEmail(getEmail());
		customerLogin.setUserType("Individual");
		customerLogin.setUserName(getUserName());*/


			//iuserCreation.saveOnlineUsrData(customerLogin);
			//getMailService().sendRegistrationInitiationMail(getEmail(), "Successfully Initiated", getUserName());

			setRenderUserNamepanel(false);
			setRenderPasswordpanel(true);
			setRenderSecurityQuespanel(false);
			setRenderPhisingImagepanel(false);
			setRenderSuccesspanel(false);
		}

	}

	public void nextPassword(){



		ResourceBundle resourceBundle = ruleEngine.getResourceBundle();
		setBoopasswordChec(validate());
		boopasswordChec = validate();

		if(isBoopasswordChec()){

			setRenderUserNamepanel(false);
			setRenderPasswordpanel(true);
			setRenderSecurityQuespanel(false);
			setRenderPhisingImagepanel(false);
			setRenderSuccesspanel(false);
			FacesContext.getCurrentInstance().addMessage("usercreation:retypesecondarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.passmatch"))));

		} else {

			if(!getPassword().equals(getRetypePassword())){
				FacesContext.getCurrentInstance().addMessage("usercreation:primarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.primarypassword"))+" "+(resourceBundle.getString("lbl.shouldmatchwith"))+(resourceBundle.getString("lbl.retypeprimarypassword"))));
			}
			if(!getSecoundaryPassword().equals(getRetypeSecoundaryPassword())){
				FacesContext.getCurrentInstance().addMessage("usercreation:secondarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.secondarypassword"))+" "+(resourceBundle.getString("lbl.shouldmatchwith"))+(resourceBundle.getString("lbl.retypesecondarypassword"))));
			}



			setRenderUserNamepanel(false);
			setRenderPasswordpanel(false);
			setRenderSecurityQuespanel(true);
			setRenderPhisingImagepanel(false);
			setRenderSuccesspanel(false);


		}


	}

	public void savePasswordPanel(){

		ResourceBundle resourceBundle = ruleEngine.getResourceBundle();
		setBoopasswordChec(validate());
		boopasswordChec = validate();
		customerLogin = new CustomerLogin();

		if(isBoopasswordChec()){

			setRenderUserNamepanel(false);
			setRenderPasswordpanel(true);
			setRenderSecurityQuespanel(false);
			setRenderPhisingImagepanel(false);
			setRenderSuccesspanel(false);
			FacesContext.getCurrentInstance().addMessage("usercreation:retypesecondarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.passmatch"))));

		} else {

			if(!getPassword().equals(getRetypePassword())){
				FacesContext.getCurrentInstance().addMessage("usercreation:primarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.primarypassword"))+" "+(resourceBundle.getString("lbl.shouldmatchwith"))+(resourceBundle.getString("lbl.retypeprimarypassword"))));
			}
			if(!getSecoundaryPassword().equals(getRetypeSecoundaryPassword())){
				FacesContext.getCurrentInstance().addMessage("usercreation:secondarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.secondarypassword"))+" "+(resourceBundle.getString("lbl.shouldmatchwith"))+(resourceBundle.getString("lbl.retypesecondarypassword"))));
			}

			//Insert Country ID
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(getCountry().equals("")?null:new BigDecimal(getCountry()));
			customerLogin.setFsCountryMaster(countryMaster);

			int languageID = 1;
			if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("languageCode")){
				languageID = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("languageCode").toString().equalsIgnoreCase("ar")?2:1;
			}

			customerLogin.setCustLoginId(getCustomerLoginId());
			//Insert Language ID from session   
			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(new BigDecimal(languageID));
			customerLogin.setFsLanguageType(languageType);


			customerLogin.setCreatedBy(getUserName());
			//CR for creation date based on application country
			//customerLogin.setCreationDate(new Date());
			customerLogin.setCreationDate(getCurrentTime());


			customerLogin.setEmail(getEmail());
			customerLogin.setUserType("Individual");
			customerLogin.setUserName(getUserName());
			//Old Logic
			/*iCypherSecurity cypherSecurity = new CypherSecurityImpl();
			String secretKey = getUserName().toUpperCase();
			StringBuffer secretKeys = new StringBuffer(secretKey).reverse();
			customerLogin.setPassword(cypherSecurity.encodePassword(getPassword(), secretKeys.toString()));
			customerLogin.setSecondaryPassword(cypherSecurity.encodePassword(getSecoundaryPassword(), secretKeys.toString()));*/
			//customerLogin.setPassword(cypherSecurity.encodePassword(getPassword(), secretKeys.toString()));
			//customerLogin.setSecondaryPassword(cypherSecurity.encodePassword(getSecoundaryPassword(), secretKeys.toString()));
			// Old Logic Ends
			// New Logic
			customerLogin.setPassword(UserRegUtils.getHash(getUserName(),getPassword()));
			//String secondaryPassword = "SecondPwd",UserRegUtils.getENCrypted(getUserName(),getSecoundaryPassword());
			customerLogin.setSecondaryPassword(encryptionDescriptionService.getENCrypted(getUserName(),getSecoundaryPassword()));
			iuserCreation.saveOnlineUsrData(customerLogin);

		}

	}

	public void saveSecurityPanel(){


		customerLogin.setCustLoginId(getCustomerLoginId());
		checkIDNo();
		savePasswordPanel();

		BizComponentData bizComponentData;
		iCypherSecurity cypherSecurity = new CypherSecurityImpl();
		// String secretKey = getUserName().toUpperCase();
		//StringBuffer secretKeys = new StringBuffer(secretKey).reverse();


		if(getSecureQuest1()!=null && getSecureQuest1().intValue()!=0 && !getSecureAnsr1().trim().equals("")){
			bizComponentData = new BizComponentData();
			bizComponentData.setComponentDataId(getSecureQuest1());
			String secretKey = getSecureQuest1().toPlainString();
			customerLogin.setFsBizComponentDataBySecurityQuestion1(bizComponentData);
			//customerLogin.setSecurityAnswer1(cypherSecurity.encodePassword(getSecureAnsr1(),  secretKey));
			customerLogin.setSecurityAnswer1(UserRegUtils.getHash(getUserName(),ExUtils.simplifyString(getSecureAnsr1())));
		}
		if(getSecureQuest2()!=null && getSecureQuest2().intValue()!=0 && !getSecureAnsr2().trim().equals("")){
			bizComponentData = new BizComponentData();
			String secretKey = getSecureQuest2().toPlainString();
			bizComponentData.setComponentDataId(getSecureQuest2());
			customerLogin.setFsBizComponentDataBySecurityQuestion2(bizComponentData);
			//customerLogin.setSecurityAnswer2(cypherSecurity.encodePassword(getSecureAnsr2(), secretKey));
			customerLogin.setSecurityAnswer2(UserRegUtils.getHash(getUserName(),ExUtils.simplifyString(getSecureAnsr2())));
		}
		if(getSecureQuest3()!=null && getSecureQuest3().intValue()!=0 && !getSecureAnsr3().trim().equals("")){
			bizComponentData = new BizComponentData();
			String secretKey = getSecureQuest3().toPlainString();
			bizComponentData.setComponentDataId(getSecureQuest3());
			customerLogin.setFsBizComponentDataBySecurityQuestion3(bizComponentData);
			//customerLogin.setSecurityAnswer3(cypherSecurity.encodePassword(getSecureAnsr3(), secretKey));
			customerLogin.setSecurityAnswer3(UserRegUtils.getHash(getUserName(),ExUtils.simplifyString(getSecureAnsr3())));
		}
		if(getSecureQuest4()!=null && getSecureQuest4().intValue()!=0 && !getSecureAnsr4().trim().equals("")){
			bizComponentData = new BizComponentData();
			String secretKey = getSecureQuest4().toPlainString();
			bizComponentData.setComponentDataId(getSecureQuest4());
			customerLogin.setFsBizComponentDataBySecurityQuestion4(bizComponentData);
			//customerLogin.setSecurityAnswer4(cypherSecurity.encodePassword(getSecureAnsr4(), secretKey));
			customerLogin.setSecurityAnswer4(UserRegUtils.getHash(getUserName(),ExUtils.simplifyString(getSecureAnsr4())));
		}
		if(getSecureQuest5()!=null && getSecureQuest5().intValue()!=0 && !getSecureAnsr5().trim().equals("")){
			bizComponentData = new BizComponentData();
			String secretKey = getSecureQuest5().toPlainString();
			bizComponentData.setComponentDataId(getSecureQuest5());
			customerLogin.setFsBizComponentDataBySecurityQuestion5(bizComponentData);
			//old logic
			//customerLogin.setSecurityAnswer5(cypherSecurity.encodePassword(getSecureAnsr5(),  secretKey));
			customerLogin.setSecurityAnswer5(UserRegUtils.getHash(getUserName(),ExUtils.simplifyString(getSecureAnsr5())));

		}
		//old logic
		//customerLogin.setCaption(getCaption());
		//New Logic
		customerLogin.setCaption(encryptionDescriptionService.getENCrypted(getUserName(),getCaption()));
		//Added on 09-07-2015
		customerLogin.setStatus(Constants.STATUS_ACTIVE);
		if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file1.jpg")){
			customerLogin.setImageUrl("1");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file2.jpg")){
			customerLogin.setImageUrl("2");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file3.jpg")){
			customerLogin.setImageUrl("3");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file4.jpg")){
			customerLogin.setImageUrl("4");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file5.jpg")){
			customerLogin.setImageUrl("5");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file6.jpg")){
			customerLogin.setImageUrl("6");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file7.jpg")){
			customerLogin.setImageUrl("7");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file8.jpg")){
			customerLogin.setImageUrl("8");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file9.jpg")){
			customerLogin.setImageUrl("9");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file10.jpg")){
			customerLogin.setImageUrl("10");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file11.jpg")){
			customerLogin.setImageUrl("11");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file12.jpg")){
			customerLogin.setImageUrl("12");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file13.jpg")){
			customerLogin.setImageUrl("13");
		}
		else if(getSaveImageUrl().equalsIgnoreCase("../registrationimage/file14.jpg")){
			customerLogin.setImageUrl("14");
		}else{
			customerLogin.setImageUrl("15");
		}

		customerLogin.setLoginType(Constants.USER);

		if(getCompanyId()!=null){
			CompanyMaster CompanyMaster = new CompanyMaster();
			CompanyMaster.setCompanyId(getCompanyId());
			customerLogin.setFsCompanyMaster(CompanyMaster);

		}
		if(getCustomerId()!=null){
			Customer customer = new Customer();
			customer.setCustomerId(getCustomerId());
			customerLogin.setFsCustomer(customer);

		}
		iuserCreation.saveOnlineUsrData(customerLogin);
		
		String customer_fullname = generalService.getCustomerNameCustomerId(getCustomerId());
		System.out.println("customer_fullname : "+customer_fullname);
		
		ResourceBundle resourceBundle = ruleEngine.getResourceBundle();
		
		//getMailService().sendMail(getEmail(), resourceBundle.getString("lbl.customer_reg_data") , content.toString());
		
		getMailService().sendRegistrationMail(getEmail(), resourceBundle.getString("lbl.customer_reg_data"), customer_fullname);
		
		setRenderUserNamepanel(false);
		setRenderPasswordpanel(false);
		setRenderSecurityQuespanel(false);
		setRenderPhisingImagepanel(false);
		setRenderSuccesspanel(true);

	}



	public void cancel() {
		reset();
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../almullagroup/almullagroup.xhtml");
			setCountry(null);	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void newUserCreation() throws IOException {

		reset();


		setRenderUserNamepanel(true);
		setRenderPasswordpanel(false);
		setRenderSecurityQuespanel(false);
		setRenderPhisingImagepanel(false);
		setRenderSuccesspanel(false);

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		context.redirect("../login/usercreation.xhtml");

	}

	public void goContinue(){

		setBoopasswordChec(validate());
		boopasswordChec = validate();
		customerLogin = new CustomerLogin();

		if(isBoopasswordChec()){

			setRenderUserNamepanel(false);
			setRenderPasswordpanel(false);
			setRenderSecurityQuespanel(true);
			setRenderPhisingImagepanel(false);
			setRenderSuccesspanel(false);


		} else {

			if(getSecureQuest1()!=null && getSecureQuest1().intValue()!=0 && getSecureAnsr1().trim().equals("")){
				FacesContext.getCurrentInstance().addMessage("usercreation:secAns1", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Security Answer required"));
			}
			if(getSecureQuest2()!=null && getSecureQuest2().intValue()!=0 && getSecureAnsr2().trim().equals("")){
				FacesContext.getCurrentInstance().addMessage("usercreation:secAns2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Security Answer required"));
			}
			if(getSecureQuest3()!=null && getSecureQuest3().intValue()!=0 && getSecureAnsr3().trim().equals("")){
				FacesContext.getCurrentInstance().addMessage("usercreation:secAns3", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Security Answer required"));
			}
			if(getSecureQuest4()!=null && getSecureQuest4().intValue()!=0 && getSecureAnsr4().trim().equals("")){
				FacesContext.getCurrentInstance().addMessage("usercreation:secAns4", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Security Answer required"));
			}
			if(getSecureQuest5()!=null && getSecureQuest5().intValue()!=0 && getSecureAnsr5().trim().equals("")){
				FacesContext.getCurrentInstance().addMessage("usercreation:secAns5", new FacesMessage(FacesMessage.SEVERITY_ERROR, "","Security Answer required"));
			}

			setRenderUserNamepanel(false);
			setRenderPasswordpanel(false);
			setRenderSecurityQuespanel(false);
			setRenderPhisingImagepanel(true);
			setRenderSuccesspanel(false);

		}
	}

	public UserCreationBean() {  
		imageUrl = "../registrationimage/file1.jpg";  
	}  

	//Added by Nazish for CR 22-06-2015.
	private String countryAlphaTwoCode=null;


	public String getCountryAlphaTwoCode() {
		return countryAlphaTwoCode;
	}

	public void setCountryAlphaTwoCode(String countryAlphaTwoCode) {
		this.countryAlphaTwoCode = countryAlphaTwoCode;
	}

	public void getCountryAlphCode(){
		List<CountryMaster> coutryAlphaList = generalService.getCountryAlphaList(new BigDecimal(getCountry()));

		if(coutryAlphaList.size()>0){
			setCountryAlphaTwoCode(coutryAlphaList.get(0).getCountryAlpha2Code());
		}

	}

	public void civilIdValidation() {

		boolean status = false;
		try {
			String returnString = getGeneralService()
					.getCivilIdStatus(new BigDecimal(getUserName()));
			if (returnString.equalsIgnoreCase("y")) {
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {
			status = false;
		}

		setBooIdTypeCheck(!status);

	}


	public void checkIDNo(){
		setCompanyId(null);
		setCustomerId(null);
		List<CustomerIdProof> listCheck = iuserCreation.checkIdproof(new BigDecimal(getCountry()), getUserName());
		if(listCheck.size()>0){
			setCompanyId(listCheck.get(0).getFsCustomer().getFsCompanyMaster().getCompanyId());
			setCustomerId(listCheck.get(0).getFsCustomer().getCustomerId());
			setEmail(listCheck.get(0).getFsCustomer().getEmail());
		}
	}

}


