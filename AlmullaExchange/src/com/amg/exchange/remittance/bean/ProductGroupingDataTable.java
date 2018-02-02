package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductGroupingDataTable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal productGroupId;
	private BigDecimal productGroupSerial;
	private String productgroupdesc;
	private BigDecimal bankId;
	private String bankCode;
	private BigDecimal currencyId;
	private String currencyCode;
	private String accountNo;
	private String productMode;
	private String productModeDesc;
	private BigDecimal transactionLimit;
	private BigDecimal bankLimit;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String dynamicLabelForActivateDeactivate;
	
	public String getProductgroupdesc() {
		return productgroupdesc;
	}
	public void setProductgroupdesc(String productgroupdesc) {
		this.productgroupdesc = productgroupdesc;
	}
	
	public BigDecimal getProductGroupSerial() {
		return productGroupSerial;
	}
	public void setProductGroupSerial(BigDecimal productGroupSerial) {
		this.productGroupSerial = productGroupSerial;
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getProductMode() {
		return productMode;
	}
	public void setProductMode(String productMode) {
		this.productMode = productMode;
	}
	
	public BigDecimal getTransactionLimit() {
		return transactionLimit;
	}
	public void setTransactionLimit(BigDecimal transactionLimit) {
		this.transactionLimit = transactionLimit;
	}
	
	public BigDecimal getBankLimit() {
		return bankLimit;
	}
	public void setBankLimit(BigDecimal bankLimit) {
		this.bankLimit = bankLimit;
	}
	
	public String getProductModeDesc() {
		return productModeDesc;
	}
	public void setProductModeDesc(String productModeDesc) {
		this.productModeDesc = productModeDesc;
	}
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public BigDecimal getProductGroupId() {
		return productGroupId;
	}
	public void setProductGroupId(BigDecimal productGroupId) {
		this.productGroupId = productGroupId;
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
	
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	
	

}
