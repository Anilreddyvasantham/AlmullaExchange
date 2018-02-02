package com.amg.exchange.common.bean;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

public class RuleBean {

	public static final String IS_NUMERIC = "IS_NUMERIC";
	public static final String IS_ALPHABET = "IS_ALPHABET";
	public static final String IS_SPECIAL = "IS_SPECIAL";
	public static final String IS_REQUIRED = "IS_REQUIRED";
	public static final String IS_READ_ONLY = "IS_READ_ONLY";
	public static final String IS_ENABLE = "IS_ENABLE";
	public static final String IS_VISIBLE = "IS_VISIBLE";
	public static final String MIN_VALUE = "MIN_VALUE";
	public static final String MAX_VALUE = "MAX_VALUE";
	
	private boolean numeric;
	private boolean alphabet;
	private boolean special;
	private boolean required;
	private boolean readOnly;
	private boolean enable;
	private boolean visible;
	private int minValue;
	private int maxValue;
	
	public RuleBean() {
	}
	
	public RuleBean(JSONObject jsonObject) throws JSONException, NumberFormatException {
		
		setNumeric(parseBoolean(jsonObject.getString(IS_NUMERIC)));
		setAlphabet(parseBoolean(jsonObject.getString(IS_ALPHABET)));
		setSpecial(parseBoolean(jsonObject.getString(IS_SPECIAL)));
		setRequired(parseBoolean(jsonObject.getString(IS_REQUIRED)));
		setReadOnly(parseBoolean(jsonObject.getString(IS_READ_ONLY)));
		setEnable(parseBoolean(jsonObject.getString(IS_ENABLE)));
		setVisible(parseBoolean(jsonObject.getString(IS_VISIBLE)));
		setMinValue(Integer.parseInt(jsonObject.getString(MIN_VALUE)));
		setMaxValue(Integer.parseInt(jsonObject.getString(MAX_VALUE)));
	}

	public boolean isNumeric() {
		return numeric;
	}

	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}

	public boolean isAlphabet() {
		return alphabet;
	}

	public void setAlphabet(boolean alphabet) {
		this.alphabet = alphabet;
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	private boolean parseBoolean(String input){
		
		if(input == null){
			return false;
		}else if(input.equalsIgnoreCase("Y")){
			return true;
		}else{
			return false;
		}
	}
}
