package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_SERVICE_PROVIDER")
public class ServiceProviderView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	BigDecimal idNo;
	String serviceProviderCode;
	String serviceProvider;
	
	@Id
	@Column(name="IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	
	@Column(name="SRV_PROVIDER_CODE")
	public String getServiceProviderCode() {
		return serviceProviderCode;
	}
	
	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}
	
	@Column(name="SERVICE_PROVIDER")
	public String getServiceProvider() {
		return serviceProvider;
	}
	
	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	
	
	
}
