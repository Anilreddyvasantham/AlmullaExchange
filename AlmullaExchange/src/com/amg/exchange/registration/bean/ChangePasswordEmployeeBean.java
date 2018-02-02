package com.amg.exchange.registration.bean;

/**
 * @author Nazish Ehsan Hashmi
 *
 */

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.UserRegUtils;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

@Component("changepasswordEmployee")
@Scope("session")
public class ChangePasswordEmployeeBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6943727369371894292L;

	private static final Logger LOG = Logger.getLogger(ChangePasswordEmployeeBean.class);
	SessionStateManage sessionState = new SessionStateManage();


	private BigDecimal employeeIdPk =null;
	private String passwordType;
	private String oldPassword;
	private String newPassword;
	private String retypePassword;
	private String statusMsg;
	private String passwordMatchMessage;
	private String passewordLengthValidateMsg;
	private String globalmessage;
	private CustomerLogin customerLogin;

	List<Employee> employeeInfo = null;
	@Autowired
	ILoginService<T> loginService;
	@Autowired
	IEmployeeService employeeService;



	public ChangePasswordEmployeeBean() {

	}


	public String getPasswordType() {
		return passwordType;
	}
	public void setPasswordType(String passwordType) {
		this.passwordType = passwordType;
	}

	public BigDecimal getEmployeeIdPk() {
		return employeeIdPk;
	}
	public void setEmployeeIdPk(BigDecimal employeeIdPk) {
		this.employeeIdPk = employeeIdPk;
	}

	public String getOldPassword() {
		return this.oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return this.newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRetypePassword() {
		return this.retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public String getStatusMsg() {
		return this.statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getPasswordMatchMessage() {
		return this.passwordMatchMessage;
	}
	public void setPasswordMatchMessage(String passwordMatchMessage) {
		this.passwordMatchMessage = passwordMatchMessage;
	}

	public String getPassewordLengthValidateMsg() {
		return this.passewordLengthValidateMsg;
	}
	public void setPassewordLengthValidateMsg(String passewordLengthValidateMsg) {
		this.passewordLengthValidateMsg = passewordLengthValidateMsg;
	}

	public String getGlobalmessage() {
		return this.globalmessage;
	}
	public void setGlobalmessage(String globalmessage) {
		this.globalmessage = globalmessage;
	}

	public ILoginService<T> getLoginService() {
		return this.loginService;
	}
	public void setLoginService(ILoginService<T> loginService) {
		this.loginService = loginService;
	}

	public CustomerLogin getCustomerLogin() {
		return this.customerLogin;
	}
	public void setCustomerLogin(CustomerLogin customerLogin) {
		this.customerLogin = customerLogin;
	}

	public void navigateToChangePassword() throws IOException{
		reset();
		setPasswordType(Constants.LoginPassword);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../login/employeechangepassword.xhtml");	
	}

	public void reset() {
		this.newPassword = null;
		this.oldPassword = null;
		this.retypePassword = null;
		setEmployeeIdPk(null);
		setStatusMsg(null);
		setPassewordLengthValidateMsg(null);
		setPasswordMatchMessage(null);
	}

	public boolean checkPassword() {
		LOG.info("Entering into checkPassword method");
		boolean ret = true;
		String userName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userName");
		try {
			employeeInfo = new ArrayList<Employee>();
			iCypherSecurity cypherSecurity = new CypherSecurityImpl();
			String userName1 = new StringBuffer(userName).reverse().toString().toUpperCase();
			employeeInfo.addAll(getLoginService().getLoginInfoForEmployeeForChangePassword(sessionState.getCountryId(), userName, cypherSecurity.encodePassword(getOldPassword(), userName1),getPasswordType()));
			if (employeeInfo.size() == 1) {
				setEmployeeIdPk(employeeInfo.get(0).getEmployeeId());
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

	public String updatePassword()  {
		LOG.info("Entering  into updatePassword method");
		String userName = sessionState.getUserName();

		iCypherSecurity cypherSecurity = new CypherSecurityImpl();
		FacesMessage facesMessage = null;
		boolean isAvailable = false;
		try {
			String userName1 = new StringBuffer(userName).reverse().toString().toUpperCase();

			isAvailable = getLoginService().getLoginInfoForEmployeeForChangePassword(sessionState.getCountryId(), userName, cypherSecurity.encodePassword(getNewPassword(), userName1),getPasswordType()).size() > 0 ? true : false;
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
					String secretKey = userName.toUpperCase();
					StringBuffer secretKeys = new StringBuffer(secretKey).reverse();
					//employeeInfo.get(0).setPassword(cypherSecurity.encodePassword(getNewPassword(), secretKeys.toString()));
					employeeService.updateChangePasswordforChangePassword(getEmployeeIdPk(),cypherSecurity.encodePassword(getNewPassword(), secretKeys.toString()),getPasswordType());
				}
				LOG.info("Password update sucess");
			} catch (Exception e) {
				setStatusMsg("Please Enter Valid Password");
				LOG.error("Exception occured in ChangePassword ::updatePassword method ", e);
				reset();
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../login/employeechangepassword.xhtml");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				return "";
			}

			if(getPasswordType().equalsIgnoreCase(Constants.LoginPassword)){
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('changepasswordsuccess').show();");
			}else if(getPasswordType().equalsIgnoreCase(Constants.WesternUnionPassword)){
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('branchhomepage').show();");
				//branchhome.xhtml
			}

		}
		LOG.info("Exit  into updatePassword method");
		return null;
	}

	public String changePasswordSuccess() {
		setPasswordType(null);
		setOldPassword(null);
		setNewPassword(null);
		setRetypePassword(null);
		return "login?faces-redirect=true";
	}

	// for Exit Button
	public void branchHomePage() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
