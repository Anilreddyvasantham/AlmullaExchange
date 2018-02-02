package com.amg.exchange.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.amg.exchange.common.DeclareConstants;

@FacesValidator("com.amg.exchange.util.RegularExpressionValidation")
public class RegularExpressionValidation implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {

		Pattern pattern = null;
		Matcher matcher = null;
		FacesMessage msg = null;
		
		int minValue = -1;
		int maxValue = -1;
		
		SessionStateManage sessionStateManage = new SessionStateManage();
		if(sessionStateManage.isExists("languageCode")){
			setLanguageCode(sessionStateManage.getSessionValue("languageCode").toUpperCase());
		}else{
			setLanguageCode("EN");
		}
		
    	pattern = Pattern.compile((String)component.getAttributes().get("regexValue"));
        
    	try{
    		if(component.getAttributes().containsKey("minValue")){
    			minValue = Integer.parseInt(component.getAttributes().get("minValue").toString());
    		}else{
    			//System.out.println("Min value not found for component "+component.getClientId());
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	try{
    		if(component.getAttributes().containsKey("maxValue")){
    			maxValue = Integer.parseInt((String)component.getAttributes().get("maxValue").toString());
    		}else{
    			//System.out.println("Max value not found for component "+component.getClientId());
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
		matcher = pattern.matcher(value.toString());
		
		if(!matcher.matches()){
 
			if(DeclareConstants.REGEX_EMAIL.equals(pattern.toString())){
				msg = new FacesMessage(DeclareConstants.EMAIL_VAL_MSG.get(getLanguageCode()));
			}else if(isArabicLang(component)){
				/*matchedArabicPattern(pattern.pattern());*/
				msg = new FacesMessage(DeclareConstants.LOCAL_LANG.get(getLanguageCode()));
			}else{
				msg = new FacesMessage(matchedPattern(pattern.pattern()));
			}
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
 
		}else if(minValue!=-1 && value.toString().length()<minValue ){
			msg = new FacesMessage((DeclareConstants.REGEX_MIN_LENGTH_LABEL.get(getLanguageCode())).replace("{0}", ""+minValue).replace("{1}", ""));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}else if(maxValue!=-1 && value.toString().length()>maxValue ){
			msg = new FacesMessage((DeclareConstants.REGEX_MAX_LENGTH_LABEL.get(getLanguageCode())).replace("{0}", ""+maxValue));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
	
	public boolean isArabicLang(UIComponent component){
		
		if(component instanceof HtmlInputText){
			HtmlInputText htmlInputText = (HtmlInputText)component;
			if(htmlInputText.getLang()!=null && htmlInputText.getLang().equalsIgnoreCase("ar")){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	private String matchedPattern(String receivedPattern){
		String ret = "";
		
		for(int i=0;i<=1;i++){//isNumeric
			for(int j=0;j<=1;j++){//isAlphabet
				for(int k=0;k<=1;k++){//isSpecial
					if(receivedPattern.equals(getPattern(i==1?true:false,j==1?true:false,k==1?true:false))){
						ret = getValidationMessage(i==1?true:false,j==1?true:false,k==1?true:false);
					}
				}
			}
		}
		
		return ret;
	}

	/*private String matchedArabicPattern(String receivedPattern){
		String ret = "";
		
		for(int i=0;i<=1;i++){//isNumeric
			for(int j=0;j<=1;j++){//isAlphabet
				for(int k=0;k<=1;k++){//isSpecial
					if(receivedPattern.equals(getArabicPattern(i==1?true:false,j==1?true:false,k==1?true:false))){
						ret = getValidationMessage(i==1?true:false,j==1?true:false,k==1?true:false);
					}
				}
			}
		}
		
		return ret;
	}*/
	
	private String getPattern(boolean isNumeric, boolean isAlphabet, boolean isSpecial){
		
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
	
	private String getValidationMessage(boolean isNumeric, boolean isAlphabet, boolean isSpecial){
		
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
			
			str = DeclareConstants.REGEX_ALLOW_LABEL.get(getLanguageCode())+""+(trueCount==1?" "+DeclareConstants.REGEX_ONLY_LABEL.get(getLanguageCode()):"")+(isNumeric?numeric:"")+(join1)+(isAlphabet?alpha:"")+(join2)+(isSpecial?special:"")+"";
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
}
