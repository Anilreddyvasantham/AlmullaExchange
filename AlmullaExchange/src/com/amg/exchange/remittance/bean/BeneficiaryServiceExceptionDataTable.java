/**
 * 
 */
package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Subramaniam
 * 
 */
public class BeneficiaryServiceExceptionDataTable {

	/**
	 * 
	 */

	/*BENE_SERVICE_EXCEP_SETUP_ID
COUNTRY_ID
COUNTRY_CODE
CURRENCY_ID
CURRENCY_CODE
BANK_ID
BANK_CODE
BANK_BRANCH_ID
BANK_BRANCH_CODE
REMITTANCE_MODE_ID
REMITTANCE_CODE
DELIVERY_MODE_ID
DELIVERY_MODE_CODE
CREATION_DATE
CREATED_BY
UPDATED_DATE
UPDATED_BY
ISACTIVE
APPLICATION_COUNTRY_ID
APPROVED_BY
APPROVED_DATE
REMARKS*/
	private BigDecimal beneServiceExcepId;
	
	private BigDecimal countryId;
	private String countryCode;
	
	private BigDecimal currencyId;
	private String currencyCode;
	
	private BigDecimal bankId;
	private String bankCode;	
	
	private BigDecimal bankBranchId;
	private String bankBranchCode;	
	private String bankBranchName;
	
	private BigDecimal remittanceModeId;
	private String remittanceMode;
	
	private BigDecimal deliveryId;	
	private String deliveryMode;
	
	private BigDecimal serviceId;	
	private String serviceName;
	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	
	private String modeOfOperation;
	private String dynamicLabelForActivateDeactivate;	
	private Boolean remarksCheck = false;
	private Boolean activateRecordCheck = false;
	private Boolean booCheckDelete = false;

	public BeneficiaryServiceExceptionDataTable() {
		// TODO Auto-generated constructor stub
	}

	public BeneficiaryServiceExceptionDataTable(BigDecimal beneServiceExcepId, BigDecimal bankBranchId, String bankBranchCode, String bankBranchName, BigDecimal bankId, String bankCode, BigDecimal countryId, String countryCode, BigDecimal currencyId, String currencyCode,
			BigDecimal remittanceModeId, BigDecimal deliveryId, String remittanceMode, String deliveryMode, BigDecimal serviceId, String serviceName, String modeOfOperation, String dynamicLabelForActivateDeactivate, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate,
			String approvedBy, Date approvedDate, String remarks, Boolean remarksCheck, Boolean activateRecordCheck, Boolean booCheckDelete) {
		this.beneServiceExcepId = beneServiceExcepId;
		this.bankBranchId = bankBranchId;
		this.bankBranchCode = bankBranchCode;
		this.bankBranchName = bankBranchName;
		this.bankId = bankId;
		this.bankCode = bankCode;
		this.countryId = countryId;
		this.countryCode = countryCode;
		this.currencyId = currencyId;
		this.currencyCode = currencyCode;
		this.remittanceModeId = remittanceModeId;
		this.deliveryId = deliveryId;
		this.remittanceMode = remittanceMode;
		this.deliveryMode = deliveryMode;
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.modeOfOperation = modeOfOperation;
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		this.remarksCheck = remarksCheck;
		this.activateRecordCheck = activateRecordCheck;
		this.booCheckDelete = booCheckDelete;
	}

	public BigDecimal getBeneServiceExcepId() {
		return beneServiceExcepId;
	}

	public void setBeneServiceExcepId(BigDecimal beneServiceExcepId) {
		this.beneServiceExcepId = beneServiceExcepId;
	}

	public BigDecimal getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(BigDecimal bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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

	public String getModeOfOperation() {
		return modeOfOperation;
	}

	public void setModeOfOperation(String modeOfOperation) {
		this.modeOfOperation = modeOfOperation;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}

	public Boolean getActivateRecordCheck() {
		return activateRecordCheck;
	}

	public void setActivateRecordCheck(Boolean activateRecordCheck) {
		this.activateRecordCheck = activateRecordCheck;
	}

	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

}
