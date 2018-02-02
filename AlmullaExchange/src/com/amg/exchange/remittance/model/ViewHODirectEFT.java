package com.amg.exchange.remittance.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_HO_DIRECT_EFT")
public class ViewHODirectEFT implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String bankCode;
	private String countryAlphaCode;
	private String currencyAlphaCode;
	private String remittanceModeCode;
	private String agentHoCode;
	private String agentName;
	private String typeFlag;
	private String centralized;
	private String thirdPartyArrangement;
	private String beneBank;
	
	
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
	
	@Id
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
	
	@Column(name="TYPE_FLAG")
	public String getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	
	@Column(name="CENTRALIZED")
	public String getCentralized() {
		return centralized;
	}
	public void setCentralized(String centralized) {
		this.centralized = centralized;
	}
	
	@Column(name="THIRD_PARTY_ARRANGEMENT")
	public String getThirdPartyArrangement() {
		return thirdPartyArrangement;
	}
	public void setThirdPartyArrangement(String thirdPartyArrangement) {
		this.thirdPartyArrangement = thirdPartyArrangement;
	}
	
	@Column(name="BENE_BANK")
	public String getBeneBank() {
		return beneBank;
	}
	public void setBeneBank(String beneBank) {
		this.beneBank = beneBank;
	}
	
	
	
}
