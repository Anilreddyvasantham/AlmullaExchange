package com.amg.exchange.bean;

public class State {
	
	private String stateCode;
	private String stateName;
	
	public State(String code, String name){
		this.stateCode = code;
		this.stateName = name;
	}
	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
