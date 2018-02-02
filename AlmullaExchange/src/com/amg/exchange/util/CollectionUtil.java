package com.amg.exchange.util;

import java.math.BigDecimal;
import java.util.Map;

import com.amg.exchange.common.DeclareConstants;
import com.amg.exchange.common.model.BizComponentConfDetail;

/*******************************************************************************************************************

		 File		: CollectionUtil.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.util
 
		 Created	:	
 						Date	: 14-May-2014 9:22:43 am
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 14-May-2014 9:22:43 am
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
public class CollectionUtil {
	
	
	public String fetchBehavior(Map<String, BizComponentConfDetail> mapComponentBehavior, String componentName, String propertyName){
		
		String ret = "";
		if(mapComponentBehavior!=null && mapComponentBehavior.containsKey(componentName)){
			switch(propertyName){
			
				case "MIN_VALUE":
					ret = mapComponentBehavior.get(componentName).getMinValue().toString();
					break;
					
				case "MAX_VALUE":
					ret = mapComponentBehavior.get(componentName).getMaxValue().toString();
					break;
					
				case "NUMERIC":
					ret = mapComponentBehavior.get(componentName).getIsNumeric().equals("Y")?"^[0-9]*":"";
					break;
					
				case "ALPHABET":
					ret = mapComponentBehavior.get(componentName).getIsAlphabet().equals("Y")?"[a-zA-Z]":"";
					break;
					
				case "SPECIAL":
					ret = mapComponentBehavior.get(componentName).getIsSpecial().equals("Y")?"[$&+,:;=?@#|'<>.^*()%!-/]":"";
					break;
					
				case "REQUIRED":
					ret = mapComponentBehavior.get(componentName).getIsRequired().equals("Y")?"true":"false";
					break;
					
				case "READ_ONLY":
					ret = mapComponentBehavior.get(componentName).getIsReadOnly().equals("Y")?"true":"false";
					break;
					
				case "ENABLE":
					ret = mapComponentBehavior.get(componentName).getIsEnable().equals("Y")?"false":"true";
					break;
					
				case "MANDATORY":
					ret = mapComponentBehavior.get(componentName).getIsRequired().equals("Y")?"*":"";
					break;
					
				case "VAL_MSG":
					ret = getValidationMessage(
							mapComponentBehavior.get(componentName).getIsNumeric().equals("Y"),
							mapComponentBehavior.get(componentName).getIsAlphabet().equals("Y"),
							mapComponentBehavior.get(componentName).getIsSpecial().equals("Y")
							);
					break;
					
				case "PATTERN":
					ret = getPattern(
							mapComponentBehavior.get(componentName).getIsNumeric().equals("Y"),
							mapComponentBehavior.get(componentName).getIsAlphabet().equals("Y"),
							mapComponentBehavior.get(componentName).getIsSpecial().equals("Y")
							);
					break;
				default:
					ret = "";
					break;
			}
		}
		return ret;
	}
	
	public String getPattern(boolean isNumeric, boolean isAlphabet, boolean isSpecial){
		
		String special = DeclareConstants.REGEX_SPECIAL;
		String numeric = DeclareConstants.REGEX_NUMERIC;
		String alpha = DeclareConstants.REGEX_ALPHA;
		
		if(!isNumeric && !isAlphabet && !isSpecial){
			return "";
		}else{
			return "^["+(isAlphabet?alpha:"")+(isNumeric?numeric:"")+(isSpecial?special:"")+"]*$";
		}
	}
	
	public String getArabicPattern(boolean isNumeric, boolean isAlphabet, boolean isSpecial){
		
		/*String special = DeclareConstants.REGEX_SPECIAL_AR;*/
		String numeric = DeclareConstants.REGEX_NUMERIC_AR;
		String alpha = DeclareConstants.REGEX_ALPHA_AR;
		
		if(!isNumeric && !isAlphabet){
			return "";
		}else{
			return "^["+(isAlphabet?alpha:"")+(isNumeric?numeric:"")+"]*$";
		}
	}
	
	public String getValidationMessage(boolean isNumeric, boolean isAlphabet, boolean isSpecial){
		
		SessionStateManage sessionStateManage = new SessionStateManage();
		if(sessionStateManage.isExists("languageCode")){
			setLanguageCode(sessionStateManage.getSessionValue("languageCode").toUpperCase());
		}else{
			setLanguageCode("EN");
		}
		
		String special = DeclareConstants.REGEX_SPECIAL_LABEL.get(getLanguageCode());
		String numeric = DeclareConstants.REGEX_NUMERIC_LABEL.get(getLanguageCode());
		String alpha = DeclareConstants.REGEX_ALPHA_LABEL.get(getLanguageCode());
		
		int trueCount = (isNumeric?1:0)+(isAlphabet?1:0)+(isSpecial?1:0);
		
		String str = "";
		
		if(!isNumeric && !isAlphabet && !isSpecial){
			str = ""+(alpha)+","+(numeric)+" "+DeclareConstants.REGEX_AND_LABEL.get(getLanguageCode())+(special)+" "+DeclareConstants.REGEX_NOT_ALLOW_LABEL.get(getLanguageCode());
		}else{
			String join1 = "";
			String join2 = "";
			if(isNumeric && trueCount==3){
				join1 = ",";
			}else if(isNumeric && trueCount==2){
				join1 = " "+DeclareConstants.REGEX_AND_LABEL.get(getLanguageCode());
			}
			
			if(isAlphabet && trueCount==3){
				join2 = " "+DeclareConstants.REGEX_AND_LABEL.get(getLanguageCode());
			}else if(isAlphabet && trueCount==2 && isSpecial){
				join2 = " "+DeclareConstants.REGEX_AND_LABEL.get(getLanguageCode());
			}
			
			str =  DeclareConstants.REGEX_ALLOW_LABEL.get(getLanguageCode())+" "+(trueCount==1?" "+DeclareConstants.REGEX_ONLY_LABEL.get(getLanguageCode()):"")+(isNumeric?numeric:"")+(join1)+(isAlphabet?alpha:"")+(join2)+(isSpecial?special:"")+"";
		}
		return str;
	}
	
	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	private String languageCode;
	
	
	
	
	// Start by kani

	
	public   String formatErrorMessage(String errormsg){
		
		
		 String formatError=errormsg;
		 String first="";
		 formatError=formatError.replace('"', '@');
			  //  String[] splitString = (formatError.split(":"));
			
			    first=formatError.replaceAll("\\SQL EXCEPTION"," ");
			    first=first.replace("ORA-", " ");
			    first=first.replace("Error", " ");
			    
			    first=first.substring(0,first.indexOf('@'));
			   /*
			    first=first.substring(first.indexOf(':'));
			    
			    first=first.replace("20001:", " ");*/
			  //  first=first.replace("06512: at ", " ");
			    first=first.substring(0,first.lastIndexOf('0'));
			    first=first.replace("20001:", " ");
			    first=first.replace("Dp_Get_Exchange_Rate", " ");
			    first=first.replace("In", " ");
			    
			    
			   // System.out.println(first);
		
		
		
		return first;
		 
	 }
	
	
	
	// End By kani 
	
	
	
	public static  boolean isIntegerValue(BigDecimal bd) {
	    boolean ret;

	    try {
	        bd.toBigIntegerExact();
	        ret = true;
	    } catch (ArithmeticException ex) {
	        ret = false;
	    }	
	    catch(NumberFormatException ne)
	    {
	    	  ret = false;
	    }

	    return ret;
	}
	
	
	
}
