package com.amg.exchange.util;

public class BooleanManager {
	
	/**
	 * This method will convert to Y/N
	 * @param value
	 * @return
	 */
	public String BooleanChecker(String value) {
		
		String finalValue = null; 
		
		if(value.equalsIgnoreCase("FALSE")){
			finalValue = "N";
		} else if(value.equalsIgnoreCase("TRUE")){
			finalValue = "Y";
		}else if(value.equalsIgnoreCase("YES")){
			finalValue = "Y";
		}else if(value.equalsIgnoreCase("NO")){
			finalValue = "N";
		}else if(value.equalsIgnoreCase("0")){
			finalValue = "Y";
		}else if(value.equalsIgnoreCase("1")){
			finalValue = "N";
		}
		return finalValue;
	}
	
	/**
	 * This method will convert Y/N to True/False
	 * @param value
	 * @return
	 */
	public String reverseBooleanMaker(String value){
		String finalValue = null;
		if(value.equalsIgnoreCase("Y")){
			finalValue = "TRUE";
		} else if(value.equalsIgnoreCase("N")){
			finalValue = "FALSE";
		}
		return finalValue;
	}
}
