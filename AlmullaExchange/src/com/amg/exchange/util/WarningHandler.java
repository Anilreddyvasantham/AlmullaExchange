package com.amg.exchange.util;

import java.math.BigDecimal;
import java.util.ResourceBundle;

public class WarningHandler {

	public static String showWarningMessage(String labelName,BigDecimal languageId){

		String propertyName= null;
		try{
			if(languageId.compareTo(BigDecimal.ONE)==0){
				ResourceBundle props = ResourceBundle.getBundle("com.amg.exchange.messages.amgamx_en");
				propertyName =	props.getString(labelName);
			}else{
				ResourceBundle props = ResourceBundle.getBundle("com.amg.exchange.messages.amgamx_ar");
				propertyName =	props.getString(labelName);
			}
		}catch(Exception exeption){
			System.out.println("Exception while getting properties" +exeption.getMessage());
		}
		if(propertyName == null){
			propertyName = labelName;
		}
		return propertyName;
	}



}
