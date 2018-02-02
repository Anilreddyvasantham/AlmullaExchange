package com.amg.exchange.validator;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.amg.exchange.validator.doubleCheckValidator")
public class DoubleCheckValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;
	
	String Component1,Component2,ComponentValue,str;

	private final static String ZeroChecking_PATTERN = "^[0]+(\\." +"[0]+)*$";

	
	public DoubleCheckValidator(){
		  pattern = Pattern.compile(ZeroChecking_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {
		
		try {
	        if (new BigDecimal(value.toString()).compareTo(BigDecimal.ZERO)<=0) {
	            FacesMessage msg = new FacesMessage("Validation failed.", 
	                    "Negative and Zero Not Allowed");
	            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	            throw new ValidatorException(msg); 
	        } else{
	        	ComponentValue=component.getClientId();

	    		try {

	    			String[] parts = ComponentValue.split(":");
	    			Component1 = parts[0];
	    			Component2 = parts[1];

	    		} catch (Exception nme) {
	    			System.out.println("No Validation Component");
	    		}

	    		matcher = pattern.matcher(value.toString());
	    	    str = value.toString();
	    	    
	    		if (matcher.matches()) {

	    			FacesMessage msg = new FacesMessage("Invalid Zero.",
	    					"Invalid Zero for " +Component2+ ". Please Enter Minimum eg. 0.001");

	    			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    			throw new ValidatorException(msg);

	    		} else if (str.indexOf(".") > 0) {

	    		//	try {
	    			    new BigDecimal(str);
	    				//Double.parseDouble(str);

	    				String[] parts = str.split("\\.");
	    				String part1 = parts[0];
	    				String part2 = parts[1];

	    				try {
	    					new BigDecimal(part2);
	    				} catch (Exception nme) {
	    					
	    /*					FacesMessage msg = new FacesMessage("Wrong Amount.",
	    							"Please Enter Proper Decimal " +Component2+ ", Allows Only 3 values After Dot. Eg 0.001");
	    					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    					throw new ValidatorException(msg);*/
	    				}
	    				
	    				if ("ikonRate".equals(Component2)) {

	    					int size = part2.length();
	    					if (size > 9) {

	    						FacesMessage msg = new FacesMessage("More Value.","Please Enter Proper Decimal " +Component2+ ", Allows Only 9 values After Dot");
	    						msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    						throw new ValidatorException(msg);
	    					}
	    				}else if ("exchangerateid".equals(Component2)) {
	    					int size1 = part2.length();
	    					if (size1 > 9) {
	    					FacesMessage msg = new FacesMessage("Decimal Value.",
	    							"Please Enter Proper Decimal " +Component2+ ", Allows Only 9 Decimal values After Dot");
	    					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    					throw new ValidatorException(msg);
	    					}
	    				}else if ("bnkTrFromExchangeRateid".equals(Component2)) {
	    					int size1 = part2.length();
	    					if (size1 > 9) {
	    					FacesMessage msg = new FacesMessage("Decimal Value.",
	    							"Please Enter Proper Decimal " +Component2+ ", Allows Only 9 Decimal values After Dot");
	    					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    					throw new ValidatorException(msg);
	    					}
	    				}else if ("bnkTrToExchangeRateid".equals(Component2)) {
	    					int size1 = part2.length();
	    					if (size1 > 9) {
	    					FacesMessage msg = new FacesMessage("Decimal Value.",
	    							"Please Enter Proper Decimal " +Component2+ ", Allows Only 9 Decimal values After Dot");
	    					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    					throw new ValidatorException(msg);
	    					}
	    				}else if ("exchangeRateId".equals(Component2)) {
	    					int size1 = part2.length();
	    					if (size1 > 9) {
	    					FacesMessage msg = new FacesMessage("Decimal Value.",
	    							"Please Enter Proper Decimal " +Component2+ ", Allows Only 9 Decimal values After Dot");
	    					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    					throw new ValidatorException(msg);
	    					}
	    				}else if ("dataTableId".equals(Component2)) {
	    					int size1 = part2.length();
	    					if (size1 > 6) {
	    					FacesMessage msg = new FacesMessage("Decimal Value.",
	    							"Please Enter Proper Decimal " +Component2+ ", Allows Only 6 Decimal values After Dot");
	    					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    					throw new ValidatorException(msg);
	    					}
	    				}
	    				

	    			/*} catch (Exception nme) {
	    				FacesMessage msg = new FacesMessage("Invalid Amount Decimal.",
	    						"Please Enter Proper Decimal " +Component2+ ", Allows Only 3 values After Dot. Eg 0.001");
	    				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    				throw new ValidatorException(msg);
	    			}*/

	    		} else {
	    			try {
	    				Integer.parseInt(str);
	    			} catch (Exception nme) {
	    				FacesMessage msg = new FacesMessage("Invalid Amount Interger.",
	    						"Invalid Format " +Component2+ ". Special Characters Doesn't Allow - Interger");
	    				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	    				throw new ValidatorException(msg);
	    			}
	    		}
	        }
	    } catch (NumberFormatException ex) {
	        FacesMessage msg = new FacesMessage("Validation failed.", "Not a number");
	        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(msg); 
	    }
	}

}