package com.amg.exchange.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.amg.exchange.validator.passwordValidation")
public class PasswordValidator implements Validator {
	private Pattern pattern,pattern1;
	private Matcher matcher,matcher1;

	private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%*!]).{8,20})";

	private static final String NUMBER_PATTREN = "^[0-9.]*$";

	public PasswordValidator(){
		pattern = Pattern.compile(PASSWORD_PATTERN);
		pattern1 = Pattern.compile(NUMBER_PATTREN);
	}

	/**
	 * Validate password with regular expression
	 * @param password password for validation
	 * @return true valid password, false invalid password
	 */
	public boolean validate(final String password){

		matcher = pattern.matcher(password);
		return matcher.matches();

	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
			FacesMessage msg = new FacesMessage("Password must be minimum 8 character with combination Of Lowercase, Uppercase, Numbers and Special Symbol(eg.#,@,%,$,*,!)");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}else{
			String password = value.toString();
			String userNameChk = password.substring(0,1);

			matcher1 = pattern1.matcher(userNameChk);
			if(matcher1.matches()){
				FacesMessage msg = new FacesMessage("Password must not start with Number");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}
	}
}
