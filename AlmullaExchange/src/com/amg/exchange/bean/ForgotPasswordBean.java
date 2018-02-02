package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.TokenGeneration;
import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.mail.ApplicationMailer;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.service.IForgotPasswordService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.util.CollectionUtil;
import com.amg.exchange.util.ExUtils;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.UserRegUtils;
import com.amg.exchange.util.WarningHandler;

@Component(value = "forgotpassword")
@Scope("session")
public class ForgotPasswordBean<T> implements Serializable {

	//TODO : Variables and object declaration
	private static final long serialVersionUID = 6965017961255250321L;

	private BigDecimal languageId;

	private String username;
	private String questionUsername;
	private String emailid;
	private boolean userAvailableStatus;
	private String userAvailableStatusMsg;
	private boolean quesUserAvailableStatus;
	private String quesUserAvailableStatusMsg;
	private String globalmessage;
	private String panelQuestion = "";
	private String panelQuestionAnswer = "";

	private int forgotoptions;
	private int rightAnswerCount = 2;
	private boolean optiongridstatus = true;
	private boolean usergridstatus = false;
	private boolean questiongridstatus = false;
	private boolean globalgridstatus = false;
	private boolean questionUsernameVisibility = false;
	private boolean questionUsernameavailablity = false;

	private int currentDisplayId = -1;

	private List<ForgotPasswordQuestionBean> lstQuestionList = new ArrayList<ForgotPasswordQuestionBean>();
	private Map<String, BizComponentConfDetail> mapComponentBehavior = new HashMap<String,BizComponentConfDetail>();
	private Map<BigDecimal, String> mapSecurityQuestion = new HashMap<BigDecimal, String>(); 
	/*private Map<BigDecimal, String > mapSecurityQuestionMaster = new HashMap<BigDecimal, String>();*/
	private CustomerLogin customerLogin;
	private List<CustomerLogin> lstLoginInfo = new ArrayList<CustomerLogin>();

	//TODO : Auto wired service declaration
	@Autowired
	IForgotPasswordService<T> forgotPasswordService;
	@Autowired
	ApplicationMailer mailService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	RuleEngine ruleEngine;
	@Autowired
	ILoginService<T> loginService;
	
	SessionStateManage sessionmanage = new SessionStateManage(); 

	//TODO : Getter And Setter Methods

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public ApplicationMailer getMailService() {
		return mailService;
	}

	public Map<String, BizComponentConfDetail> getMapComponentBehavior() {
		return mapComponentBehavior;
	}

	public void setMapComponentBehavior(Map<String, BizComponentConfDetail> mapComponentBehavior) {
		this.mapComponentBehavior = mapComponentBehavior;
	}

	public Map<BigDecimal, String> getMapSecurityQuestion() {
		return mapSecurityQuestion;
	}

	public void setMapSecurityQuestion(Map<BigDecimal, String> mapSecurityQuestion) {
		this.mapSecurityQuestion = mapSecurityQuestion;
	}

	public CustomerLogin getCustomerLogin() {
		return customerLogin;
	}

	public void setCustomerLogin(CustomerLogin customerLogin) {
		this.customerLogin = customerLogin;
	}

	public int getRightAnswerCount() {
		return rightAnswerCount;
	}

	public void setRightAnswerCount(int rightAnswerCount) {
		this.rightAnswerCount = rightAnswerCount;
	}

	public String getPanelQuestionAnswer() {
		return panelQuestionAnswer;
	}

	public void setPanelQuestionAnswer(String panelQuestionAnswer) {
		this.panelQuestionAnswer = panelQuestionAnswer;
	}

	public String getPanelQuestion() {
		return panelQuestion;
	}

	public void setPanelQuestion(String panelQuestion) {
		this.panelQuestion = panelQuestion;
	}

	public boolean isQuestionUsernameavailablity() {
		return questionUsernameavailablity;
	}

	public void setQuestionUsernameavailablity(boolean questionUsernameavailablity) {
		this.questionUsernameavailablity = questionUsernameavailablity;
	}

	public boolean isQuestionUsernameVisibility() {
		return questionUsernameVisibility;
	}

	public void setQuestionUsernameVisibility(boolean questionUsernameVisibility) {
		this.questionUsernameVisibility = questionUsernameVisibility;
	}

	public int getCurrentDisplayId() {
		return currentDisplayId;
	}

	public void setCurrentDisplayId(int currentDisplayId) {
		this.currentDisplayId = currentDisplayId;
	}

	public List<ForgotPasswordQuestionBean> getLstQuestionList() {
		return lstQuestionList;
	}

	public void setLstQuestionList(List<ForgotPasswordQuestionBean> lstQuestionList) {
		this.lstQuestionList = lstQuestionList;
	}

	/*public Map<BigDecimal, String> getMapSecurityQuestionMaster() {
		return mapSecurityQuestionMaster;
	}

	public void setMapSecurityQuestionMaster(
			Map<BigDecimal, String> mapSecurityQuestionMaster) {
		this.mapSecurityQuestionMaster = mapSecurityQuestionMaster;
	}*/

	public void setMailService(ApplicationMailer mailService) {
		this.mailService = mailService;
	}

	public BigDecimal getLanguageId() {
		return languageId;
	}

	public boolean isQuesUserAvailableStatus() {
		return quesUserAvailableStatus;
	}

	public void setQuesUserAvailableStatus(boolean quesUserAvailableStatus) {
		this.quesUserAvailableStatus = quesUserAvailableStatus;
	}

	public String getQuesUserAvailableStatusMsg() {
		return quesUserAvailableStatusMsg;
	}

	public void setQuesUserAvailableStatusMsg(String quesUserAvailableStatusMsg) {
		this.quesUserAvailableStatusMsg = quesUserAvailableStatusMsg;
	}

	public boolean isOptiongridstatus() {
		return optiongridstatus;
	}

	public void setOptiongridstatus(boolean optiongridstatus) {
		this.optiongridstatus = optiongridstatus;
	}

	public boolean isUsergridstatus() {
		return usergridstatus;
	}

	public void setUsergridstatus(boolean usergridstatus) {
		this.usergridstatus = usergridstatus;
	}

	public boolean isQuestiongridstatus() {
		return questiongridstatus;
	}

	public void setQuestiongridstatus(boolean questiongridstatus) {
		this.questiongridstatus = questiongridstatus;
	}

	public String getQuestionUsername() {
		return questionUsername;
	}

	public void setQuestionUsername(String questionUsername) {
		this.questionUsername = questionUsername;
	}

	public void setLanguageId(BigDecimal languageId) {
		this.languageId = languageId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getGlobalmessage() {
		return globalmessage;
	}

	public void setGlobalmessage(String globalmessage) {
		this.globalmessage = globalmessage;
	}

	public boolean isGlobalgridstatus() {
		return globalgridstatus;
	}

	public void setGlobalgridstatus(boolean globalgridstatus) {
		this.globalgridstatus = globalgridstatus;
	}

	public boolean isUserAvailableStatus() {
		return userAvailableStatus;
	}

	public void setUserAvailableStatus(boolean userAvailableStatus) {
		this.userAvailableStatus = userAvailableStatus;
	}

	public String getUserAvailableStatusMsg() {
		return userAvailableStatusMsg;
	}

	public void setUserAvailableStatusMsg(String userAvailableStatusMsg) {
		this.userAvailableStatusMsg = userAvailableStatusMsg;
	}

	public int getForgotoptions() {
		return forgotoptions;
	}

	public void setForgotoptions(int forgotoptions) {
		this.forgotoptions = forgotoptions;
	}

	public IForgotPasswordService<T> getForgotPasswordService() {
		return forgotPasswordService;
	}

	public void setForgotPasswordService(IForgotPasswordService<T> forgotPasswordService) {
		this.forgotPasswordService = forgotPasswordService;
	}

	//TODO : Bean Methods

	//check username is exists or not
	public void checkUsername(AjaxBehaviorEvent event) {

		try {
			if (getForgotPasswordService().getCustomerDetail(getUsername()).size() > 0) {
				setUserAvailableStatus(true);
				setUserAvailableStatusMsg("");
			} else {
				setUserAvailableStatus(false);
				setUserAvailableStatusMsg("Invalid Username");
			}
		} catch (Exception e) {
			setUserAvailableStatus(false);
			setUserAvailableStatusMsg("Invalid Username");
		}
	}

	@SuppressWarnings("unchecked")
	private void insertQuestionDetail(BizComponentData bizComponentData, String actualAnswer, int position){

		ForgotPasswordQuestionBean forgotPasswordQuestionBean = new ForgotPasswordQuestionBean();
		if(mapSecurityQuestion==null || mapSecurityQuestion.size()==0){

			mapSecurityQuestion = ruleEngine.getComponentData("Security Question");
		}   
		/*mapSecurityQuestionMaster.put(
				customerLogin.getFsBussComponentComboDataBySecurityQuestion1().getComponentComboDataId(), 
				customerLogin.getFsBussComponentComboDataBySecurityQuestion1().getComponentData()
				);*/
		forgotPasswordQuestionBean.setPosition(position++); 
		forgotPasswordQuestionBean.setQuestionactualanswer(actualAnswer);
		forgotPasswordQuestionBean.setQuestionanswer("");
		forgotPasswordQuestionBean.setQuestionId(bizComponentData.getComponentDataId());
		forgotPasswordQuestionBean.setQuestiondesc(mapSecurityQuestion.get(forgotPasswordQuestionBean.getQuestionId()));
		lstQuestionList.add(forgotPasswordQuestionBean);
	}

	//check username (Question based) is exists or not
	public void checkUsernameQuestionBased(AjaxBehaviorEvent event) {

		try {

			//Get customer login list based on user entered user name
			List<CustomerLogin> lstCustomerLogin = getForgotPasswordService().getCustomerDetail(getQuestionUsername());

			//Check user exist or not
			if (lstCustomerLogin.size() > 0) {
				setQuestionUsernameavailablity(true);
			} else {
				setQuestionUsernameavailablity(false);
			}

			//Get language id from session
			SessionStateManage sessionStateManage = new SessionStateManage();
			setLanguageId(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));

			//Clear list objects
			lstQuestionList.clear();
			int position = 0;

			//Prepare security question and and answer bean
			for (CustomerLogin customerLogin : lstCustomerLogin) {

				setCustomerLogin(customerLogin);

				//Prepare security question id list

				if(customerLogin.getFsBizComponentDataBySecurityQuestion1() !=null && customerLogin.getSecurityAnswer1()!=null){
					insertQuestionDetail(customerLogin.getFsBizComponentDataBySecurityQuestion1(), customerLogin.getSecurityAnswer1(), position++);
				}
				if(customerLogin.getFsBizComponentDataBySecurityQuestion2()!=null && customerLogin.getSecurityAnswer2()!=null){
					insertQuestionDetail(customerLogin.getFsBizComponentDataBySecurityQuestion2(), customerLogin.getSecurityAnswer2(), position++);
				}
				if(customerLogin.getFsBizComponentDataBySecurityQuestion3()!=null && customerLogin.getSecurityAnswer3()!=null){
					insertQuestionDetail(customerLogin.getFsBizComponentDataBySecurityQuestion3(), customerLogin.getSecurityAnswer3(), position++);
				}
				if(customerLogin.getFsBizComponentDataBySecurityQuestion4()!=null && customerLogin.getSecurityAnswer4()!=null){
					insertQuestionDetail(customerLogin.getFsBizComponentDataBySecurityQuestion4(), customerLogin.getSecurityAnswer4(), position++);
				}
				if(customerLogin.getFsBizComponentDataBySecurityQuestion5()!=null && customerLogin.getSecurityAnswer5()!=null){
					insertQuestionDetail(customerLogin.getFsBizComponentDataBySecurityQuestion5(), customerLogin.getSecurityAnswer5(), position++);
				}
			}

			//Prepare user availability message
			if (lstCustomerLogin.size() > 0) {
				setQuesUserAvailableStatus(true);
				setQuesUserAvailableStatusMsg("");
			} else {
				setQuesUserAvailableStatus(true);
				setQuesUserAvailableStatusMsg("Invalid username");
			}
		} catch (Exception e) {
			setQuesUserAvailableStatus(true);
			setQuesUserAvailableStatusMsg("Invalid username");
		}
	}

	//Show display depends on selection
	public void showPanel(AjaxBehaviorEvent event) {
		if (getForgotoptions() == 1) {
			falseAll();
			setUsergridstatus(true);
		} else if (getForgotoptions() == 2) {
			falseAll();
			setQuestiongridstatus(true);
			setQuestionUsernameVisibility(true);
		} else {

		}
	}

	//Reset password when user knows their user name and email id
	public void resetPassword() {
		try {

			//Check user name and email combination is available or not
			try {
				setCustomerLogin(getForgotPasswordService().getCustomerDetail(getUsername(), getEmailid()).get(0));
			} catch (Exception e) {
				setGlobalmessage("Invalid combination of username and email id");
				throw e;
			}

			//Call password reset method
			resettingPassword();

		} catch (Exception e) {
			e.printStackTrace();
		}

		String message = getGlobalmessage();
		falseAll();
		resetFields();
		setGlobalmessage(message);
		setGlobalgridstatus(true);
	}



	//When user click next button 
	public void nextQuestion() {

		boolean isLast = false;
		boolean rightAttempt = false;
		if(lstLoginInfo != null){
			lstLoginInfo.clear();
		}
		//Check user name availability. if exists then continue the process otherwise it will ask proper user name
		if (isQuestionUsernameavailablity()) {

			if (isQuestionUsernameVisibility()) {
				setQuestionUsernameVisibility(false);
			} else {
			}

			//For display next security question
			nextPosition();

			System.out.println("---------------------- "+lstQuestionList);
			if (getCurrentDisplayId() < lstQuestionList.size()) {
				if (lstQuestionList.size() > 0 && getCurrentDisplayId() < lstQuestionList.size() && getCurrentDisplayId() >= 0) {
					setPanelQuestion(lstQuestionList.get(getCurrentDisplayId()).getQuestiondesc());
				}
				if (getCurrentDisplayId() > 0) {
					lstQuestionList.get(getCurrentDisplayId() - 1).setQuestionanswer(getPanelQuestionAnswer());
					setPanelQuestionAnswer("");
				}

			} else if(lstQuestionList.size()>0) {//when cursor move to last question
				lstQuestionList.get(getCurrentDisplayId() - 1).setQuestionanswer(getPanelQuestionAnswer());
				setPanelQuestionAnswer("");
				isLast = true;
			} else{
				setPanelQuestionAnswer("");
				isLast = true;
			}

			int tempCount = 0;
			lstLoginInfo.addAll(loginService.getLoginInfoForSecurityAnswer(getCustomerLogin().getUserName()));

			for (CustomerLogin questionsId : lstLoginInfo) {

				for (ForgotPasswordQuestionBean forgotPasswordQuestionBean : lstQuestionList) {

					System.out.println("forgotPasswordQuestionBean.getPosition()"+forgotPasswordQuestionBean.getPosition());
					System.out.println("forgotPasswordQuestionBean.getQuestionactualanswer()"+forgotPasswordQuestionBean.getQuestionactualanswer());
					System.out.println("forgotPasswordQuestionBean.getQuestionanswer()"+forgotPasswordQuestionBean.getQuestionanswer());
					System.out.println("forgotPasswordQuestionBean.getQuestiondesc()"+forgotPasswordQuestionBean.getQuestiondesc());
					System.out.println("forgotPasswordQuestionBean.getQuestionId()"+forgotPasswordQuestionBean.getQuestionId());

					String securityAnswer = UserRegUtils.getHash(getCustomerLogin().getUserName(),ExUtils.simplifyString(forgotPasswordQuestionBean.getQuestionanswer()));
					System.out.println("securityAnswer"+securityAnswer);

					if ((questionsId.getFsBizComponentDataBySecurityQuestion1().getComponentDataId().compareTo(forgotPasswordQuestionBean.getQuestionId())==0) && (questionsId.getSecurityAnswer1().equalsIgnoreCase(securityAnswer))) {
						tempCount++;
					}else if ((questionsId.getFsBizComponentDataBySecurityQuestion2().getComponentDataId().compareTo(forgotPasswordQuestionBean.getQuestionId())==0) && (questionsId.getSecurityAnswer2().equalsIgnoreCase(securityAnswer))) {
						tempCount++;
					}else if ((questionsId.getFsBizComponentDataBySecurityQuestion3().getComponentDataId().compareTo(forgotPasswordQuestionBean.getQuestionId())==0) && (questionsId.getSecurityAnswer3().equalsIgnoreCase(securityAnswer))) {
						tempCount++;
					}else if ((questionsId.getFsBizComponentDataBySecurityQuestion4().getComponentDataId().compareTo(forgotPasswordQuestionBean.getQuestionId())==0) && (questionsId.getSecurityAnswer4().equalsIgnoreCase(securityAnswer))) {
						tempCount++;
					}else if ((questionsId.getFsBizComponentDataBySecurityQuestion5().getComponentDataId().compareTo(forgotPasswordQuestionBean.getQuestionId())==0) && (questionsId.getSecurityAnswer5().equalsIgnoreCase(securityAnswer))) {
						tempCount++;
					}else{
						System.out.println("tempCount"+tempCount );
					}
				}
			}

			//Check how many correct answer's . this count cross get right answer count system automatically reset user password
			if (getRightAnswerCount() == tempCount) {
				resettingPassword();
				String message = getGlobalmessage();
				falseAll();
				resetFields();
				setGlobalmessage(message);
				setGlobalgridstatus(true);
				rightAttempt = true;
			}
		}

		//If all answers are failed then following block will execute
		if (isLast && !rightAttempt) {
			setGlobalmessage("Unable to reset your password, Your security question answers are not matched !");
			String message = getGlobalmessage();
			falseAll();
			resetFields();
			setGlobalmessage(message);
			setGlobalgridstatus(true);
		}
	}

	//Make screen look like initial screen
	public void resetForgotPassword() {

		falseAll();
		resetFields();
		setOptiongridstatus(true);
	}

	//Reset password and send mail to user with reseted password
	private void resettingPassword() {
		try {

			//Get random password
			String resetKey = new TokenGeneration().getRandomIdentifier(6);

			//Update model bean password attribute value
			// add by nazish for adding salt
			String userName1 = new StringBuffer(getCustomerLogin().getUserName()).reverse().toString().toUpperCase();
			System.out.println("userName1 : "+userName1);
			
			String customer_fullname = generalService.getCustomerNameCustomerId(getCustomerLogin().getFsCustomer().getCustomerId());
			
			System.out.println("customer_fullname : "+customer_fullname);
			
			// date format
			Calendar cal = Calendar.getInstance();
			Date date = new Date(cal.getTimeInMillis());
			new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(date);
			System.out.println("dateformate : "+date);


			try {
				//new logic
				customerLogin.setPassword(UserRegUtils.getHash(getCustomerLogin().getUserName(),resetKey));
			} catch (Exception e) {
				setGlobalmessage("Unable to generate your random password, Please try again");
				throw e;
			}

			//Update random password into customer login table
			try {
				getForgotPasswordService().setNewPassword(customerLogin);
			} catch (Exception e) {
				setGlobalmessage("Unable to reset your password");
				throw e;
			}
			
			/*String dear = WarningHandler.showWarningMessage("lbl.dear", sessionmanage.getLanguageId());
			String password_rest = WarningHandler.showWarningMessage("lbl.password_rest", sessionmanage.getLanguageId());
			String password_rest1 = WarningHandler.showWarningMessage("lbl.password_rest1", sessionmanage.getLanguageId());
			String password_rest2 = WarningHandler.showWarningMessage("lbl.password_rest2", sessionmanage.getLanguageId());
			String password_rest3 = WarningHandler.showWarningMessage("lbl.password_rest3", sessionmanage.getLanguageId());
			String password_rest4 = WarningHandler.showWarningMessage("lbl.password_rest4", sessionmanage.getLanguageId());
			
			String common_data = WarningHandler.showWarningMessage("lbl.common_data", sessionmanage.getLanguageId());
			String common_data1 = WarningHandler.showWarningMessage("lbl.common_data1", sessionmanage.getLanguageId());
			String common_data2 = WarningHandler.showWarningMessage("lbl.common_data2", sessionmanage.getLanguageId());
			String common_data3 = WarningHandler.showWarningMessage("lbl.common_data3", sessionmanage.getLanguageId());
			
			String address_data = WarningHandler.showWarningMessage("lbl.address_data", sessionmanage.getLanguageId());
			String address_data1 = WarningHandler.showWarningMessage("lbl.address_data1", sessionmanage.getLanguageId());
			String address_data2 = WarningHandler.showWarningMessage("lbl.address_data2", sessionmanage.getLanguageId());
			String address_data3 = WarningHandler.showWarningMessage("lbl.address_data3", sessionmanage.getLanguageId());
			String address_data4 = WarningHandler.showWarningMessage("lbl.address_data4", sessionmanage.getLanguageId());
			String address_data5 = WarningHandler.showWarningMessage("lbl.address_data5", sessionmanage.getLanguageId());
			String address_data6 = WarningHandler.showWarningMessage("lbl.address_data6", sessionmanage.getLanguageId());
			String address_data7 = WarningHandler.showWarningMessage("lbl.address_data7", sessionmanage.getLanguageId());*/
			
			//Prepare mail content
			/*StringBuffer mailContent = new StringBuffer(dear).append(customer_fullname.toUpperCase()).append(", \n").
					append("\n").append(password_rest1).append("\n").
					append("\n").append(password_rest2).append("\n").
					append("\n").append(password_rest3 +" \n").append(password_rest4).append(resetKey).append("\n").
					append("\n").append(common_data).append("\n").
					append(common_data1).append("\n").
					append(common_data2).append("\n").
					append(common_data3).append("\n").
					append("\n").append(address_data).append("\n").
					append(address_data1).append("\n").
					append(address_data2).append("\n").
					append(address_data3).append("\n").
					append(address_data4).append("\n").
					append("\n").append("---------------------------------------------------------------------------------------------------------------").append("\n").
					append(address_data5+ " " +date).
					append("\n").append("---------------------------------------------------------------------------------------------------------------").append("\n").
					append("\n").append(address_data6).append("\n").
					append("\n").append(address_data7).append("\n");*/
			//FacesContext.getCurrentInstance().addMessage("signup:primarypassword", new FacesMessage(FacesMessage.SEVERITY_ERROR, "",(resourceBundle.getString("lbl.primarypassword"))+" "+(resourceBundle.getString("lbl.shouldmatchwith"))+(resourceBundle.getString("lbl.retypeprimarypassword"))));
			ResourceBundle resourceBundle = ruleEngine.getResourceBundle();
			
			StringBuffer mailContent = new StringBuffer(resourceBundle.getString("lbl.dear")).append(customer_fullname.toUpperCase()).append(", \n").
					append("\n").append(resourceBundle.getString("lbl.password_rest1")).append("\n").
					append("\n").append(resourceBundle.getString("lbl.password_rest2")).append("\n").
					append("\n").append(resourceBundle.getString("lbl.password_rest3") +" \n").append(resourceBundle.getString("lbl.password_rest4")).append(resetKey).append("\n").
					append("\n").append(resourceBundle.getString("lbl.common_data")).append("\n").
					append(resourceBundle.getString("lbl.common_data1")).append("\n").
					append(resourceBundle.getString("lbl.common_data2")).append("\n").
					append(resourceBundle.getString("lbl.common_data3")).append("\n").
					append("\n").append(resourceBundle.getString("lbl.address_data")).append("\n").
					append(resourceBundle.getString("lbl.address_data1")).append("\n").
					append(resourceBundle.getString("lbl.address_data2")).append("\n").
					append(resourceBundle.getString("lbl.address_data3")).append("\n").
					append(resourceBundle.getString("lbl.address_data4")).append("\n").
					append("\n").append("---------------------------------------------------------------------------------------------------------------").append("\n").
					append(resourceBundle.getString("lbl.address_data5") + " " +date).
					append("\n").append("---------------------------------------------------------------------------------------------------------------").append("\n").
					append("\n").append(resourceBundle.getString("lbl.address_data6")).append("\n").
					append("\n").append(resourceBundle.getString("lbl.address_data7")).append("\n");
			

			//Send email with random password
			try {
				//getMailService().sendMail(getCustomerLogin().getEmail(), "Reg. Password reset :: Almulla account", mailContent.toString());
				getMailService().sendMail(getCustomerLogin().getEmail(), resourceBundle.getString("lbl.password_rest") , mailContent.toString());
			} catch (Exception e) {
				setGlobalmessage("Unable to complete your request due to our mail server problem");
				throw e;
			}

			setGlobalmessage("Your password reset success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Get Next position of display id [display id - for display the security question one by one]
	private void nextPosition() {

		setCurrentDisplayId(getCurrentDisplayId() + 1);
	}

	//Make all boolean object value as false
	private void falseAll() {
		setOptiongridstatus(false);
		setUsergridstatus(false);
		setQuestiongridstatus(false);
		setGlobalgridstatus(false);
	}

	// Reset component values
	private void resetFields() {

		setUsername(null);
		setQuestionUsername(null);
		setEmailid(null);
		setUserAvailableStatusMsg(null);
		setGlobalmessage(null);
		setQuesUserAvailableStatusMsg(null);
		setForgotoptions(0);
		setCurrentDisplayId(-1);
		setPanelQuestion("");
		setPanelQuestionAnswer("");

		setUserAvailableStatus(false);
		setOptiongridstatus(false);
		setUsergridstatus(false);
		setQuestiongridstatus(false);
		setGlobalgridstatus(false);
		setQuesUserAvailableStatus(false);
		setQuestionUsernameVisibility(false);
		setQuestionUsernameavailablity(false);
	}

	public String viewBehaviorBean(String componentName, String type){

		if(mapComponentBehavior.size()==0){
			setPageIdIntoSession();
			prepareBehavior();
		}
		return new CollectionUtil().fetchBehavior(mapComponentBehavior, componentName, type);
	}

	private void setPageIdIntoSession(){

		String pageName = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		pageName = pageName.substring(pageName.lastIndexOf('/')+1, pageName.indexOf(".xhtml"));

		try{
			BigDecimal pageId = getGeneralService().getPageId(pageName);
			new SessionStateManage().setSessionValue("pageId", pageId.toString());
		}catch(Exception e){
			System.out.println("Page id not found for pagename :: "+pageName+" :: "+e);
		}
	}

	private void prepareBehavior(){ 

		SessionStateManage manage = new SessionStateManage(); 
		List<String> lstComponentName = Arrays.asList("Country","User Name","Password","Email","Security Question","Security Question Answers");
		mapComponentBehavior =  getGeneralService().getComponentBehavior(lstComponentName, manage.getLevel(),manage.getApplicationId(),manage.getCompanyId(),manage.getCountryId(),manage.getPageId());

		//Get Security Question list
		try{
			mapSecurityQuestion.clear();
			mapSecurityQuestion = getGeneralService().getAllComponentComboData(
					mapComponentBehavior.get("Security Question").getFsBusinessComponentConf().getComponentConfId() , 
					manage.getLanguageId());
		}catch(Exception e){
			Logger.getLogger(this.getClass()).error("Unable to fetch security question from component data :: "+e);
		}
	}

	public void goForgotPasword() throws IOException {
		resetForgotPassword();
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.redirect("../login/forgotpassword.xhtml");

	}
}
