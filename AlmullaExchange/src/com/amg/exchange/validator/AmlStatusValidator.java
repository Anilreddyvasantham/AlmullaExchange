package com.amg.exchange.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.amg.exchange.bean.CustomerRegistrationBranchBean;
 
@FacesValidator("com.amg.exchange.validator.AMLValidator")
public class AmlStatusValidator implements Validator{
	
	public AmlStatusValidator(){

	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		CustomerRegistrationBranchBean element = (CustomerRegistrationBranchBean)context.getApplication().evaluateExpressionGet(context, "#{branchRegistration}", CustomerRegistrationBranchBean.class);
		
		String status = element.amlCheck(value.toString());
		
		if(status.equalsIgnoreCase("NOT FOUND") || status.equalsIgnoreCase("AML STATUS Failed")) {
			
			element.setAMLStatus("Failed");
			
			FacesMessage msg = new FacesMessage("Invalid User.", "AMLStatus failed.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
 
		} else {
			element.setAMLStatus("Pass");
		}
 
	}
}
