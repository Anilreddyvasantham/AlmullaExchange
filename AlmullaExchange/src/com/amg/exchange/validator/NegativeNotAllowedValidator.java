package com.amg.exchange.validator;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.amg.exchange.validator.NegativeNotAllowedValidator")
public class NegativeNotAllowedValidator implements Validator {
			@Override
		public void validate(FacesContext context, UIComponent component,
		        Object value) throws ValidatorException {

		    try {
		        if (new BigDecimal(value.toString()).compareTo(BigDecimal.ZERO)<=0) {
		            FacesMessage msg = new FacesMessage("Validation failed.", 
		                    "Negative and Zero Not Allowed");
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
