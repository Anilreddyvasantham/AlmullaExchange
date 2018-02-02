package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_BULK_FX_DEAL")
public class VwExBulkFxDeal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private BigDecimal applicationCountryId;
	private String bankFullName;
	private BigDecimal dealWithBankId;
	private BigDecimal companyId;
	private String createdBy;
	private Date createdDate;
	
	private BigDecimal docFinYear;
	private BigDecimal documentNumber;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal purchaseExchangeRate;
	private BigDecimal saleAmount;
	private BigDecimal totalPurchaseFcAmount;
	private BigDecimal treasuryDealHeaderId;
	private Date valueDate;
	private String reutersIndicator;
	private BigDecimal bankCountryId;
	
	private String pdbankFullName;
	private String pdCurrencyName;
	private String sdCurrencyName;
	
	//Getters and setters.
	
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name="BANK_FULL_NAME")
	public String getBankFullName() {
		return bankFullName;
	}
	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}
	
	
	@Column(name="DEAL_WITH_BANK_ID")
	public BigDecimal getDealWithBankId() {
		return dealWithBankId;
	}
	public void setDealWithBankId(BigDecimal dealWithBankId) {
		this.dealWithBankId = dealWithBankId;
	}
	@Column(name="COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}	
	
	
	@Column(name="DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocFinYear() {
		return docFinYear;
	}
	public void setDocFinYear(BigDecimal docFinYear) {
		this.docFinYear = docFinYear;
	}
	
	@Column(name="DOCUMENT_NUMBER")
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name="PURCHASE_EXCHANGE_RATE")
	public BigDecimal getPurchaseExchangeRate() {
		return purchaseExchangeRate;
	}
	public void setPurchaseExchangeRate(BigDecimal purchaseExchangeRate) {
		this.purchaseExchangeRate = purchaseExchangeRate;
	}
	
	@Column(name="SALE_AMOUNT")
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	@Column(name="TOTAL_PURCHASE_FC_AMOUNT")
	public BigDecimal getTotalPurchaseFcAmount() {
		return totalPurchaseFcAmount;
	}
	public void setTotalPurchaseFcAmount(BigDecimal totalPurchaseFcAmount) {
		this.totalPurchaseFcAmount = totalPurchaseFcAmount;
	}
	
	@Id
	@Column(name="TREASURY_DEAL_HEADER_ID")
	public BigDecimal getTreasuryDealHeaderId() {
		return treasuryDealHeaderId;
	}
	public void setTreasuryDealHeaderId(BigDecimal treasuryDealHeaderId) {
		this.treasuryDealHeaderId = treasuryDealHeaderId;
	}
	
	@Column(name="VALUE_DATE")
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	@Column(name="REUTERS_INDICATOR")
	public String getReutersIndicator() {
		return reutersIndicator;
	}
	public void setReutersIndicator(String reutersIndicator) {
		this.reutersIndicator = reutersIndicator;
	}
	
	@Column(name="BANK_COUNTRY_ID")
	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}
	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}
	
	@Column(name="PURCHASE_BANK_NAME")
	public String getPdbankFullName() {
		return pdbankFullName;
	}
	public void setPdbankFullName(String pdbankFullName) {
		this.pdbankFullName = pdbankFullName;
	}
	
	@Column(name="PURCHASE_CURRENCY")
	public String getPdCurrencyName() {
		return pdCurrencyName;
	}
	public void setPdCurrencyName(String pdCurrencyName) {
		this.pdCurrencyName = pdCurrencyName;
	}
	
	
	@Column(name="SALE_CURRENCY")
	public String getSdCurrencyName() {
		return sdCurrencyName;
	}
	public void setSdCurrencyName(String sdCurrencyName) {
		this.sdCurrencyName = sdCurrencyName;
	}
	
	
	
	
}
