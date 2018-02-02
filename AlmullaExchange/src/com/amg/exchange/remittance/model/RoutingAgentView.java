package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EX_V_AGENTS")
public class RoutingAgentView implements Serializable{

	private static final long serialVersionUID = 1L;
	private BigDecimal agentId;
	private BigDecimal bankIndicatorId;
	private String bankCode;
	private String bankFullName;
	
	
	@Id	
	@Column(name="AGENT_ID")	
	public BigDecimal getAgentId() {
		return agentId;
	}
	
	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}
	
	@Column(name="BANK_INDICATOR_ID")	
	public BigDecimal getBankIndicatorId() {
		return bankIndicatorId;
	}
	
	public void setBankIndicatorId(BigDecimal bankIndicatorId) {
		this.bankIndicatorId = bankIndicatorId;
	}
	@Column(name="BANK_CODE")	
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name="BANK_FULL_NAME")	
	public String getBankFullName() {
		return bankFullName;
	}
	
	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}
			

}