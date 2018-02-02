/*package com.amg.exchange.validator;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;
import com.amg.exchange.bean.CustomerInfoVerificationBranchManageBean;

@FacesValidator("com.amg.exchange.validator.CivilIDBranchVerification")
public class CivilIDBranchVerification implements Validator, ClientValidator{
	
				@SuppressWarnings("rawtypes")
		@Override
		public void validate(FacesContext context, UIComponent component,
				Object value) throws ValidatorException {
			
	         CustomerInfoVerificationBranchManageBean customerInfoVerificationBranchManageBean = (CustomerInfoVerificationBranchManageBean)context.getApplication().evaluateExpressionGet(context, "#{customerInfoBranch}", CustomerInfoVerificationBranchManageBean.class);
	         System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	         String data = customerInfoVerificationBranchManageBean.getBranchpageService().checkExistOrNot(value);
	         if(data.equalsIgnoreCase("0")) {
	        	 FacesMessage msg = 	new FacesMessage("ID is Not Registered.", "ID is Not Registered.");
				 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				 throw new ValidatorException(msg);
	         }
		}

		@Override
		public Map<String, Object> getMetadata() {
			return null;
		}

		@Override
		public String getValidatorId() {
			return "com.amg.exchange.validator.CivilIDValidator";
		}
	}

		 
         
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
				"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
				"(\\.[A-Za-z]{2,})$";
	 
		private Pattern pattern;
		private Matcher matcher;
	 
		public EmailValidator(){
			  pattern = Pattern.compile(EMAIL_PATTERN);
		}
	 
		@Override
		public void validate(FacesContext context, UIComponent component,
				Object value) throws ValidatorException {
	 
			matcher = pattern.matcher(value.toString());
			if(!matcher.matches()){
	 
				FacesMessage msg = 
					new FacesMessage("E-mail validation failed.", 
							"Invalid E-mail format.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
	 
			}
	 
		}
	}
//}
*/