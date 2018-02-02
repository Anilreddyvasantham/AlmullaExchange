package com.amg.exchange.validator;

import java.math.BigDecimal;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.service.IGeneralService;

@FacesValidator("com.amg.exchange.validator.CivilIDValidator")
public class CivilIDValidator implements Validator, ClientValidator{
	
		@SuppressWarnings("rawtypes")
		@Autowired
		IGeneralService generalService;
		
		@SuppressWarnings("rawtypes")
		public IGeneralService getGeneralService() {
			return generalService;
		}

		@SuppressWarnings("rawtypes")
		public void setGeneralService(IGeneralService generalService) {
			this.generalService = generalService;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void validate(FacesContext context, UIComponent component,
				Object value) throws ValidatorException {
			
			 /*String validityStatus = "";
	         String civilidPartial = "";
	         int remainder = 0;
	         int civilidLength = 0;
	         int civilidCal = 0;
	         int civilidLastDigit = 0;
	         int civilidCalChkDigit = 0;
	         final int ELEVEN=11;
	         final int TWELVE=12;
	         // -- Check Length Civil ID
	         civilidLength = value.toString().length();
	         if (civilidLength != TWELVE) {
	                validityStatus = "false";
	         } else {
	                // -- First 11 Characters for Calculation.
	                civilidPartial = value.toString().substring(0, ELEVEN);
	                // -- 12th Character Check Digit
	                value.toString().intern();
	                civilidLastDigit = Integer.parseInt(value.toString().substring(11, 12));
	                // -- Calculation as per PACI Rules for calculating check digit
	                civilidCal = Integer.parseInt(civilidPartial.substring(0, 1)) * 2
	                             + Integer.parseInt(civilidPartial.substring(1, 2)) * 1
	                             + Integer.parseInt(civilidPartial.substring(2, 3)) * 6
	                             + Integer.parseInt(civilidPartial.substring(3, 4)) * 3
	                             + Integer.parseInt(civilidPartial.substring(4, 5)) * 7
	                             + Integer.parseInt(civilidPartial.substring(5, 6)) * 9
	                             + Integer.parseInt(civilidPartial.substring(6, 7)) * 10
	                             + Integer.parseInt(civilidPartial.substring(7, 8)) * 5
	                             + Integer.parseInt(civilidPartial.substring(8, 9)) * 8
	                             + Integer.parseInt(civilidPartial.substring(9, 10)) * 4
	                             + Integer.parseInt(civilidPartial.substring(10, 11)) * 2;
	               
	                // -- Calculate Remainder by dividing 11
	                remainder = civilidCal % ELEVEN;
	        
	                // -- Get Check Digit by subtracting W_REM from 11
	                civilidCalChkDigit = ELEVEN - remainder;
	               
	                // -- Check digit can not be 0 or 10
	                if (civilidCalChkDigit == 0 || civilidCalChkDigit == 10) {
	                      validityStatus = "false";
	                } else {
	                      // -- Calculated and Actual Check Digit same OK
	                      if (civilidCalChkDigit == civilidLastDigit)
	                      {
	                             validityStatus = "true";
	                      } else {
	                             validityStatus = "false";
	                      }

	                }

	         }*/
			String validityStatus = "false";
			RuleEngine ruleEngine = (RuleEngine)context.getApplication().evaluateExpressionGet(context, "#{ruleEngine}", RuleEngine.class);
			
			try{
				validityStatus = ruleEngine.getCivilIdStatus(new BigDecimal(value.toString().trim()));
				validityStatus = validityStatus.equalsIgnoreCase("y")?"true":"false";
			}catch(Exception e){
				validityStatus = "false";
				e.printStackTrace();
			}
	         
	         if(validityStatus.equalsIgnoreCase("false")){
	        	 FacesMessage msg = 
							new FacesMessage("Civil ID validation failed.", 
									"Wrong Civil ID.");
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

		 
         
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
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
	}*/
//}
