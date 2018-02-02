package com.amg.exchange.bean;

import java.math.BigDecimal;

public class StateBean {
	private BigDecimal stateId;
	private String stateName;
	StateBean(BigDecimal stateId,String stateName) {
		this.stateId = stateId ;
		this.stateName = stateName;
	}
	
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}
}
