package com.amg.exchange.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/*
 *  Author Rahamathali Shaik
*/
@FacesValidator("allowOnlyNumber")
public class AllowOnlyNumber implements Validator {
	private static final String NUMBER_PATTREN = "^[0-9.]*$";
	
	private Pattern pattern;
	private Matcher matcher;
 
	public AllowOnlyNumber(){
		  pattern = Pattern.compile(NUMBER_PATTREN);
	}
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
		if(value!=null){
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			FacesMessage msg = 
					new FacesMessage("Enter only positive numbers ");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
	    }
		}

}
}
