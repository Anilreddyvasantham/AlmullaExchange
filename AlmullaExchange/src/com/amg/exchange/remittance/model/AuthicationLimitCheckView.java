package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_AUTH_LIMIT")
public class AuthicationLimitCheckView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal applicationCountryId;
	private String authorizationType;
	private BigDecimal authId;
	private BigDecimal authLimit;
	private String authMessage;
	private BigDecimal authPercentage;
	
	@Id
	@Column(name = "AUTH_ID")
	public BigDecimal getAuthId() {
		return authId;
	}
	public void setAuthId(BigDecimal authId) {
		this.authId = authId;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "AUTHORIZATION_TYPE")
	public String getAuthorizationType() {
		return authorizationType;
	}
	public void setAuthorizationType(String authorizationType) {
		this.authorizationType = authorizationType;
	}
	
	@Column(name = "AUTH_LIMIT")
	public BigDecimal getAuthLimit() {
		return authLimit;
	}
	public void setAuthLimit(BigDecimal authLimit) {
		this.authLimit = authLimit;
	}
	
	@Column(name = "AUTH_MESSAGE")
	public String getAuthMessage() {
		return authMessage;
	}
	public void setAuthMessage(String authMessage) {
		this.authMessage = authMessage;
	}
	
	@Column(name = "AUTH_PERC")
	public BigDecimal getAuthPercentage() {
		return authPercentage;
	}
	public void setAuthPercentage(BigDecimal authPercentage) {
		this.authPercentage = authPercentage;
	}
	
	

}
