package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("firstTimeRegistrationProcess")
@Scope("session")
public class FirstTimeRegistrationProcessBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean booRegistrationDetails = false;
	private Boolean booRegistrationDetailSuccess = false;
	private Boolean booPasswordRegistrationDetails = false;
	
	


	public Boolean getBooRegistrationDetails() {
		return booRegistrationDetails;
	}

	public void setBooRegistrationDetails(Boolean booRegistrationDetails) {
		this.booRegistrationDetails = booRegistrationDetails;
	}

	public Boolean getBooRegistrationDetailSuccess() {
		return booRegistrationDetailSuccess;
	}

	public void setBooRegistrationDetailSuccess(Boolean booRegistrationDetailSuccess) {
		this.booRegistrationDetailSuccess = booRegistrationDetailSuccess;
	}

	
	public Boolean getBooPasswordRegistrationDetails() {
		return booPasswordRegistrationDetails;
	}

	public void setBooPasswordRegistrationDetails(Boolean booPasswordRegistrationDetails) {
		this.booPasswordRegistrationDetails = booPasswordRegistrationDetails;
	}

	public void firstTimeRegistrationProcessPageNavigation() {
		try {

			setBooRegistrationDetailSuccess(false);
			setBooRegistrationDetails(true);
			setBooPasswordRegistrationDetails(false);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registrationprocess/registrationprocess.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void submit() {

		RequestContext.getCurrentInstance().execute("otp.show();");
		return;
	}

	public void otpCancel() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registrationprocess/registrationprocess.xhtml");

		} catch (Exception exception) {
			System.out.println(exception);
		}

	}

	public void otpOk() {
		try {
			
			setBooRegistrationDetailSuccess(true);
			setBooRegistrationDetails(false);
			setBooPasswordRegistrationDetails(false);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registrationprocess/registrationprocess.xhtml");

		} catch (Exception exception) {
			System.out.println(exception);
		}

	}
	
	public void save() {

		try {
			setBooRegistrationDetailSuccess(false);
			setBooRegistrationDetails(false);
			setBooPasswordRegistrationDetails(true);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registrationprocess/registrationprocess.xhtml");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	public void otpfailOk() {

		RequestContext.getCurrentInstance().execute("otpfail.show();");
		return;
	}

}
