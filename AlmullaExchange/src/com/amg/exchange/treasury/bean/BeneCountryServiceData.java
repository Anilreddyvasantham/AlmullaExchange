package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BeneCountryServiceData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal bnCntryservicepk;
	
	private String countryName;
	private String currencyName;
	private String serviceCode;
	private String remitanceMode;
	private String deliveryMode;
	private BigDecimal CountryId;
	private BigDecimal currencyId;
	private BigDecimal serviceId;
	private BigDecimal remitanceId;
	private BigDecimal deliveryId;
	private String createBy;
	private Date createDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private Boolean remarksCheck;
	private boolean booEditButton;
	private Boolean renderEditButton;
	private Boolean permanetDeleteCheck=false;
	private Boolean activateRecordCheck=false;
	
	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	
	
	
	
	
	public boolean isBooEditButton() {
		return booEditButton;
	}

	public void setBooEditButton(boolean booEditButton) {
		this.booEditButton = booEditButton;
	}

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}
	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getBnCntryservicepk() {
		return bnCntryservicepk;
	}
	public void setBnCntryservicepk(BigDecimal bnCntryservicepk) {
		this.bnCntryservicepk = bnCntryservicepk;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getRemitanceMode() {
		return remitanceMode;
	}
	public void setRemitanceMode(String remitanceMode) {
		this.remitanceMode = remitanceMode;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public BigDecimal getCountryId() {
		return CountryId;
	}
	public void setCountryId(BigDecimal countryId) {
		CountryId = countryId;
	}
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public BigDecimal getServiceId() {
		return serviceId;
	}
	public void setServiceId(BigDecimal serviceId) {
		this.serviceId = serviceId;
	}
	public BigDecimal getRemitanceId() {
		return remitanceId;
	}
	public void setRemitanceId(BigDecimal remitanceId) {
		this.remitanceId = remitanceId;
	}
	public BigDecimal getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(BigDecimal deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	private String dynamicLabelForActivateDeactivate;
	
	
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	
	
	public Boolean getPermanetDeleteCheck() {
		return permanetDeleteCheck;
	}

	public void setPermanetDeleteCheck(Boolean permanetDeleteCheck) {
		this.permanetDeleteCheck = permanetDeleteCheck;
	}

	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	@Override
	public String toString() {
		return "BeneCountryServiceData [bnCntryservicepk=" + bnCntryservicepk + ", countryName=" + countryName + ", currencyName=" + currencyName + ", serviceCode=" + serviceCode + ", remitanceMode=" + remitanceMode + ", deliveryMode=" + deliveryMode + ", CountryId=" + CountryId + ", currencyId="
				+ currencyId + ", serviceId=" + serviceId + ", remitanceId=" + remitanceId + ", deliveryId=" + deliveryId + ", createBy=" + createBy + ", createDate=" + createDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", isActive=" + isActive + ", approvedBy="
				+ approvedBy + ", approvedDate=" + approvedDate + ", remarks=" + remarks + ", dynamicLabelForActivateDeactivate=" + dynamicLabelForActivateDeactivate + "]";
	}
	
	
	
	
	
	

}
