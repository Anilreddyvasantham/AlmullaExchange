package com.amg.exchange.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("AllowCharectorswithSpace")
public class AllowCharectorswithSpace implements Validator{
	
private static final String CHARECTOR_PATTREN = "^[a-zA-Z ]*$";
	
	private Pattern pattern;
	private Matcher matcher;
 
	public AllowCharectorswithSpace(){
		  pattern = Pattern.compile(CHARECTOR_PATTREN);
	}
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			FacesMessage msg = 
					new FacesMessage("Enter Only Charectors ");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
	    }

}
}
