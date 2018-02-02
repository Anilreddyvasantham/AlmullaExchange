package com.amg.exchange.remittance.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_SUB_AGENT")
public class ViewSubAgent implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*AGENT_CODE    NOT NULL VARCHAR2(20)  
AGENT_HO_CODE          VARCHAR2(20)  
AGENT_NAME    NOT NULL VARCHAR2(100) 
BNKCOD        NOT NULL VARCHAR2(6)   
COUNTRY       NOT NULL VARCHAR2(3)   
CURCOD        NOT NULL VARCHAR2(3)   
REMTMOD       NOT NULL VARCHAR2(2)   
STATE_CODE             VARCHAR2(4)   
STATE         NOT NULL VARCHAR2(30)     */
	
	private String agentCode;
	private String bankCode;
	private String countryAlphaCode;
	private String currencyAlphaCode;
	private String remittanceModeCode;
	private String agentHoCode;
	private String agentName;
	private String stateCode;
	private String stateName;

	
	
	@Column(name="BNKCOD")
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name="COUNTRY")
	public String getCountryAlphaCode() {
		return countryAlphaCode;
	}
	public void setCountryAlphaCode(String countryAlphaCode) {
		this.countryAlphaCode = countryAlphaCode;
	}
	
	@Column(name="CURCOD")
	public String getCurrencyAlphaCode() {
		return currencyAlphaCode;
	}
	public void setCurrencyAlphaCode(String currencyAlphaCode) {
		this.currencyAlphaCode = currencyAlphaCode;
	}
	
	@Column(name="REMTMOD")
	public String getRemittanceModeCode() {
		return remittanceModeCode;
	}
	public void setRemittanceModeCode(String remittanceModeCode) {
		this.remittanceModeCode = remittanceModeCode;
	}
	
	@Column(name="AGENT_HO_CODE")
	public String getAgentHoCode() {
		return agentHoCode;
	}
	public void setAgentHoCode(String agentHoCode) {
		this.agentHoCode = agentHoCode;
	}
	
	@Column(name="AGENT_NAME")
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	@Id
	@Column(name="AGENT_CODE")
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	
	@Column(name="STATE_CODE")
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	@Column(name="STATE")
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
		
	
}
