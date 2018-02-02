package com.amg.exchange.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/*
 *  Author Rahamathali Shaik
*/
@FacesValidator("arabicValidator")
public class ArabicValidator implements Validator {
	
	public static boolean isProbablyArabic(String s) {
	    for (int i = 0; i < s.length();) {
	        int c = s.codePointAt(i);
	        if (c >= 0x0600 && c <=0x06E0)
	            return true;
	        i += Character.charCount(c);            
	    }
	    return false;
	  }

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value)
			throws ValidatorException {
		if(isProbablyArabic(value.toString())==false){
			FacesMessage msg = 
					new FacesMessage("Enter Arabic Language Only");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
		}
	    }
		
	}


