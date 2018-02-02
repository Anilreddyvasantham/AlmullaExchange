/**
 * 
 */
package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Subramaniam
 *
 */
public class ServiceExceptionDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal bankBranchId;
	private BigDecimal bankBranchCode;
	private String bankBranchName;
	private BigDecimal bankId;
	private BigDecimal countryId;
	private BigDecimal remittanceModeId;
	private BigDecimal deliveryId;
	private String remittanceMode;
	private String remittanceCode;
	private String deliveryMode;
	private String deliveryCode;
	private BigDecimal serviceId;
	private String serviceName; 
	private BigDecimal currencyId;
	private String currencyName;
	private String recordStatus;
	private String isActive;
	private Boolean selectedrecord = false;
	
	
	public ServiceExceptionDataTable() {
		super();
	}

	public ServiceExceptionDataTable(BigDecimal bankBranchId,
			BigDecimal bankBranchCode, String bankBranchName,
			BigDecimal bankId, BigDecimal countryId,
			BigDecimal remittanceModeId, BigDecimal deliveryId,
			String remittanceMode, String remittanceCode, String deliveryMode,
			String deliveryCode, BigDecimal serviceId, String serviceName,
			BigDecimal currencyId, String currencyName, String recordStatus,
			String isActive, Boolean selectedrecord) {
		super();
		this.bankBranchId = bankBranchId;
		this.bankBranchCode = bankBranchCode;
		this.bankBranchName = bankBranchName;
		this.bankId = bankId;
		this.countryId = countryId;
		this.remittanceModeId = remittanceModeId;
		this.deliveryId = deliveryId;
		this.remittanceMode = remittanceMode;
		this.remittanceCode = remittanceCode;
		this.deliveryMode = deliveryMode;
		this.deliveryCode = deliveryCode;
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.currencyId = currencyId;
		this.currencyName = currencyName;
		this.recordStatus = recordStatus;
		this.isActive = isActive;
		this.selectedrecord = selectedrecord;
	}


	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public BigDecimal getBankBranchCode() {
		return bankBranchCode;
	}
	public void setBankBranchCode(BigDecimal bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}
	
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}

	public BigDecimal getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(BigDecimal deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getRemittanceMode() {
		return remittanceMode;
	}
	public void setRemittanceMode(String remittanceMode) {
		this.remittanceMode = remittanceMode;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public BigDecimal getServiceId() {
		return serviceId;
	}
	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getRemittanceCode() {
		return remittanceCode;
	}
	public void setRemittanceCode(String remittanceCode) {
		this.remittanceCode = remittanceCode;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}
	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public Boolean getSelectedrecord() {
		return selectedrecord;
	}
	public void setSelectedrecord(Boolean selectedrecord) {
		this.selectedrecord = selectedrecord;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	

}
