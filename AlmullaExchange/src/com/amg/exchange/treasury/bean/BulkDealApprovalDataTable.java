package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BulkDealApprovalDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal treasuryHeaderId;
	private BigDecimal dealNo;
	private BigDecimal dealYear;
	private String saleBank;
	private BigDecimal salebankId;
	private BigDecimal saleCurrencyid;
	private String saleCurrencyName;
	private BigDecimal saleAmount;
	private BigDecimal exchangeRate;
	private BigDecimal purchaseAmount;
	private BigDecimal dealBankId;
	private String dealBankName;
	private String autoManual;
	private Date dealDate;
	private Boolean selectCheck;
	private String createdBy;
	private Date createdDate;
	private String ModifiedBy;
	private Date modifiedDate;
	private String isActive;
	
	private String pdbankFullName;
	private String pdCurrencyName;
	
	private String sdCurrencyName;
	
	public String getSdCurrencyName() {
		return sdCurrencyName;
	}
	public void setSdCurrencyName(String sdCurrencyName) {
		this.sdCurrencyName = sdCurrencyName;
	}
	public String getPdbankFullName() {
		return pdbankFullName;
	}
	public void setPdbankFullName(String pdbankFullName) {
		this.pdbankFullName = pdbankFullName;
	}
	public String getPdCurrencyName() {
		return pdCurrencyName;
	}
	public void setPdCurrencyName(String pdCurrencyName) {
		this.pdCurrencyName = pdCurrencyName;
	}
	public BigDecimal getTreasuryHeaderId() {
		return treasuryHeaderId;
	}
	public void setTreasuryHeaderId(BigDecimal treasuryHeaderId) {
		this.treasuryHeaderId = treasuryHeaderId;
	}
	public BigDecimal getDealNo() {
		return dealNo;
	}
	public void setDealNo(BigDecimal dealNo) {
		this.dealNo = dealNo;
	}
	public BigDecimal getDealYear() {
		return dealYear;
	}
	public void setDealYear(BigDecimal dealYear) {
		this.dealYear = dealYear;
	}
	public String getSaleBank() {
		return saleBank;
	}
	public void setSaleBank(String saleBank) {
		this.saleBank = saleBank;
	}
	public BigDecimal getSalebankId() {
		return salebankId;
	}
	public void setSalebankId(BigDecimal salebankId) {
		this.salebankId = salebankId;
	}
	public BigDecimal getSaleCurrencyid() {
		return saleCurrencyid;
	}
	public void setSaleCurrencyid(BigDecimal saleCurrencyid) {
		this.saleCurrencyid = saleCurrencyid;
	}
	public String getSaleCurrencyName() {
		return saleCurrencyName;
	}
	public void setSaleCurrencyName(String saleCurrencyName) {
		this.saleCurrencyName = saleCurrencyName;
	}
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	public BigDecimal getDealBankId() {
		return dealBankId;
	}
	public void setDealBankId(BigDecimal dealBankId) {
		this.dealBankId = dealBankId;
	}
	public String getDealBankName() {
		return dealBankName;
	}
	public void setDealBankName(String dealBankName) {
		this.dealBankName = dealBankName;
	}
	public String getAutoManual() {
		return autoManual;
	}
	public void setAutoManual(String autoManual) {
		this.autoManual = autoManual;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public Boolean getSelectCheck() {
		return selectCheck;
	}
	public void setSelectCheck(Boolean selectCheck) {
		this.selectCheck = selectCheck;
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
		return ModifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	

}
