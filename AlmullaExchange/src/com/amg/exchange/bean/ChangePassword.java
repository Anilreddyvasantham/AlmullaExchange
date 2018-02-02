package com.amg.exchange.bean;

/**
 * @author Arul JayaSingh
 *
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.registration.service.IUserSignUpService;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.UserRegUtils;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

@Component("changepassword")
@Scope("session")
public class ChangePassword<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6943727369371894292L;

	private static final Logger LOG = Logger.getLogger(ChangePassword.class);
	
	

	private String oldPassword;
	private String newPassword;
	private String retypePassword;
	private String statusMsg;
	private String passwordMatchMessage;
	private String passewordLengthValidateMsg;
	private String globalmessage;
	private CustomerLogin customerLogin;

	List<CustomerLogin> customerInfo = null;
	@Autowired
	ILoginService<T> loginService;
	@Autowired
	IUserSignUpService<T> userSignupService;

	public ChangePassword() {

	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return this.oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPass
	 */
	public String getNewPassword() {
		return this.newPassword;
	}

	/**
	 * @param newPass
	 *            the newPass to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the retypePassword
	 */
	public String getRetypePassword() {
		return this.retypePassword;
	}

	/**
	 * @param retypePassword
	 *            the retypePassword to set
	 */
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	/**
	 * @return the statusMsg
	 */
	public String getStatusMsg() {
		return this.statusMsg;
	}

	/**
	 * @param statusMsg
	 *            the statusMsg to set
	 */
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	/**
	 * @return the passwordMatchMessage
	 */
	public String getPasswordMatchMessage() {
		return this.passwordMatchMessage;
	}

	/**
	 * @param passwordMatchMessage
	 *            the passwordMatchMessage to set
	 */
	public void setPasswordMatchMessage(String passwordMatchMessage) {
		this.passwordMatchMessage = passwordMatchMessage;
	}

	/**
	 * @return the passewordLengthValidateMsg
	 */
	public String getPassewordLengthValidateMsg() {
		return this.passewordLengthValidateMsg;
	}

	/**
	 * @param passewordLengthValidateMsg
	 *            the passewordLengthValidateMsg to set
	 */
	public void setPassewordLengthValidateMsg(String passewordLengthValidateMsg) {
		this.passewordLengthValidateMsg = passewordLengthValidateMsg;
	}

	/**
	 * @return the userSignupService
	 */
	public IUserSignUpService<T> getUserSignupService() {
		return this.userSignupService;
	}

	/**
	 * @param userSignupService
	 *            the userSignupService to set
	 */
	public void setUserSignupService(IUserSignUpService<T> userSignupService) {
		this.userSignupService = userSignupService;
	}

	/**
	 * @return the globalmessage
	 */
	public String getGlobalmessage() {
		return this.globalmessage;
	}

	/**
	 * @param globalmessage
	 *            the globalmessage to set
	 */
	public void setGlobalmessage(String globalmessage) {
		this.globalmessage = globalmessage;
	}

	/**
	 * @return the loginService
	 */
	public ILoginService<T> getLoginService() {
		return this.loginService;
	}

	/**
	 * @param loginService
	 *            the loginService to set
	 */
	public void setLoginService(ILoginService<T> loginService) {
		this.loginService = loginService;
	}

	/**
	 * @return the customerLogin
	 */
	public CustomerLogin getCustomerLogin() {
		return this.customerLogin;
	}

	/**
	 * @param customerLogin
	 *            the customerLogin to set
	 */
	public void setCustomerLogin(CustomerLogin customerLogin) {
		this.customerLogin = customerLogin;
	}

	public void reset() {
		this.newPassword = null;
		this.oldPassword = null;
		this.retypePassword = null;
		setStatusMsg(null);
		setPassewordLengthValidateMsg(null);
		setPasswordMatchMessage(null);
	}

	SessionStateManage sessionState = new SessionStateManage();

	public boolean checkPassword() {
		LOG.info("Entering into checkPassword method");
		boolean ret = true;
		String userName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userName");
		try {
			customerInfo = new ArrayList<CustomerLogin>();
			iCypherSecurity cypherSecurity = new CypherSecurityImpl();
			// add by nazish for adding salt
			//old logic
			/*String userName1 = new StringBuffer(userName).reverse().toString().toUpperCase();
			customerInfo.addAll(getLoginService().getLoginInfoForCustomers(sessionState.getCountryId(), userName, cypherSecurity.encodePassword(getOldPassword(), userName1)));*/
			//new Logic
			customerInfo.addAll(getLoginService().getLoginInfoForCustomers(sessionState.getCountryId(), userName, UserRegUtils.getHash(userName,getOldPassword())));
			LOG.info("cust size;;;;" + customerInfo.size());
			if (customerInfo.size() == 1) {
				LOG.info("Password success" + getOldPassword());
			} else {
				ret = false;
				LOG.info("Enter Correct Password" + getOldPassword());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		}
		LOG.info("Exit into checkPassword method");
		return ret;
	}

	public String updatePassword() {
		LOG.info("Entering  into updatePassword method");
		String userName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userName");

		iCypherSecurity cypherSecurity = new CypherSecurityImpl();
		FacesMessage facesMessage = null;
		boolean isAvailable = false;
		try {
			// add by nazish for adding salt
			//old logic
			//String userName1 = new StringBuffer(userName).reverse().toString().toUpperCase();
           
			
			//isAvailable = getLoginService().getLoginInfoForCustomers(sessionState.getCountryId(), userName, cypherSecurity.encodePassword(getNewPassword(), userName1)).size() > 0 ? true : false;
			isAvailable = getLoginService().getLoginInfoForCustomers(sessionState.getCountryId(), userName, UserRegUtils.getHash(userName,getNewPassword())).size() > 0 ? true : false;
		} catch (Exception e) {
			LOG.error("New Password is not available .Exception occured in ChangePassword ::updatePassword method ", e);
		}
		if (!checkPassword()) {
			facesMessage = new FacesMessage("Current password incorrect!");
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("changePasswordForm:oldpassword", facesMessage);
		} else if (getOldPassword().equals(getNewPassword())) {
			setPassewordLengthValidateMsg("New password not equal to Current Password");
			facesMessage = new FacesMessage(getPassewordLengthValidateMsg());
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("changePasswordForm:newpassword", facesMessage);
		} else if (isAvailable) {
			setPassewordLengthValidateMsg("New password not equal to Current Password");
			facesMessage = new FacesMessage(getPassewordLengthValidateMsg());
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("changePasswordForm:newpassword", facesMessage);
		} else {
			setPassewordLengthValidateMsg("");
			try {
				setGlobalmessage("Enter password");
				if (userName != null && getOldPassword() != null && getOldPassword() != "") {

					LOG.info("customerInfo  :" + customerInfo.get(0).getCustLoginId());
					// add by nazish for adding salt
					//old logic
					//String secretKey = userName.toUpperCase();
					//StringBuffer secretKeys = new StringBuffer(secretKey).reverse();
					//customerInfo.get(0).setPassword(cypherSecurity.encodePassword(getNewPassword(), secretKeys.toString()));
					customerInfo.get(0).setPassword(UserRegUtils.getHash(userName,getNewPassword()));
					getUserSignupService().updateChangePassword(customerInfo.get(0));
				}
				LOG.info("Password update sucess");
			} catch (Exception e) {
				setStatusMsg("Please Enter Valid Password");
				LOG.error("Exception occured in ChangePassword ::updatePassword method ", e);
				return "changepassword?faces-redirect=true";
			}

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('changepasswordsuccess').show();");

			// return "login?faces-redirect=true";
		}
		LOG.info("Exit  into updatePassword method");
		return null;
	}

	// added by Nazish on 16-Feb-2015

	public String changePasswordSuccess() {
		setOldPassword(null);
		setNewPassword(null);
		setRetypePassword(null);
		return "login?faces-redirect=true";
	}
	// added by Nazish on 16-Feb-2015
}
