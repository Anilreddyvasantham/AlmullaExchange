package com.amg.exchange.bean;

public class AddCompoDesc {
	
	private String componentCode;
	private String componentName;
	private String min;
	private String max;
	
	private String mandatory;
	private String numeric;
	private String visible;
	
	public AddCompoDesc(String name, String min, String max, String mandatory, String numeric, String visible, String componentCode){
		this.componentName = name;
		this.setComponentCode(componentCode);
		this.min = min;
		this.max = max;
		this.mandatory = mandatory;
		this.numeric = numeric;
		this.visible = visible;
	}
	
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public String getNumeric() {
		return numeric;
	}
	public void setNumeric(String numeric) {
		this.numeric = numeric;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getComponentCode() {
		return componentCode;
	}

	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
}
