package com.amg.exchange.validator;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.amg.exchange.validator.PercentageCheckValidator")
public class PercentageCheckValidator implements Validator {
	
	@Override
	public void validate(FacesContext context, UIComponent component,
	        Object value) throws ValidatorException {

	    try {
	        if (new BigDecimal(value.toString()).signum()<1 || new BigDecimal(value.toString()).intValue()>100) {
	            FacesMessage msg = new FacesMessage("Validation failed.", 
	                    "Number Must Be Between 1 and 100");
	            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	            throw new ValidatorException(msg); 
	        } 
	    } catch (NumberFormatException ex) {
	        FacesMessage msg = new FacesMessage("Validation failed.", "Not a number");
	        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(msg); 
	    }
	}

}
