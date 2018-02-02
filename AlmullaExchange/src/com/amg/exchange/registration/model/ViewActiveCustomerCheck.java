package com.amg.exchange.registration.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************************************************************************************************

File		: ViewActiveCustomerCheck.java

Project	: AlmullaExchange

Package	: com.amg.exchange.registration.model

Created	:	
				Date	: 19-Oct-2015
				By		: Mir Rabil Uddin
				Revision:

********************************************************************************************************************/
@Entity
@Table(name="V_EX_ACTIVE_CUSTOMER_CHECK")
public class ViewActiveCustomerCheck {
			
	@Column(name="COUNTRY_ID")
	private BigDecimal countryId;
	
	@Id
	@Column(name="CUSTOMER_ID")
	private BigDecimal customerId;
	
	@Column(name="ISACTIVE")
	private String isActive;
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
}
