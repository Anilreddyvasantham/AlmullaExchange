package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;

public class RoutingAgentViewDataTable {

	
	private BigDecimal bankIndicatorId;
	private String bankCode;
	private String bankFullName;
	private Boolean isCheck=false;
	private BigDecimal agentId;
	
	
	
	
	public BigDecimal getBankIndicatorId() {
		return bankIndicatorId;
	}
	public void setBankIndicatorId(BigDecimal bankIndicatorId) {
		this.bankIndicatorId = bankIndicatorId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankFullName() {
		return bankFullName;
	}
	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}
	public Boolean getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}
	public BigDecimal getAgentId() {
		return agentId;
	}
	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}

	
}
