package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_TREASURY_DEAL_REGISTER")
public class TreasuryDealRegisterView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal treasuryDealHeaderId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNumber;
	private BigDecimal documentId;
	private Date documentDate;
	private BigDecimal totalPurchaseFCAmount;
	private Date valueDate;
	private BigDecimal purchaseExchageRate;
	private String dealWithType;
	private BigDecimal saleAmount;
	private BigDecimal pdCurrencyId;
	private BigDecimal pdFCAmount;
	private BigDecimal pdLocalExchangeRate;
	private BigDecimal pdLocalAmount;
	private String pdLineType;
	private BigDecimal pdExchangeRate;
	private BigDecimal pdBankAcctDetId;
	private BigDecimal pdBankId;
	private String pdBankCode;
	private String pdBankFullName;
	private String pdQuoteName;
	private BigDecimal sdCurrencyId;
	private BigDecimal sdFCAmount;
	private BigDecimal sdLocalExchangeRate;
	private BigDecimal sdLocalAmount;
	private String sdLineType;
	private BigDecimal sdExchangeRate;
	private BigDecimal sdBankAcctDetId;
	private BigDecimal sdBankId;
	private String sdBankCode;
	private String sdBankFullName;
	private String sdQuoteName;
	private String isActive;
	
	@Id
	@Column(name = "TREASURY_DEAL_HEADER_ID")
	public BigDecimal getTreasuryDealHeaderId() {
		return treasuryDealHeaderId;
	}
	public void setTreasuryDealHeaderId(BigDecimal treasuryDealHeaderId) {
		this.treasuryDealHeaderId = treasuryDealHeaderId;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	
	@Column(name = "DOCUMENT_NUMBER")
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	
	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	@Column(name = "TOTAL_PURCHASE_FC_AMOUNT")
	public BigDecimal getTotalPurchaseFCAmount() {
		return totalPurchaseFCAmount;
	}
	public void setTotalPurchaseFCAmount(BigDecimal totalPurchaseFCAmount) {
		this.totalPurchaseFCAmount = totalPurchaseFCAmount;
	}
	
	@Column(name = "VALUE_DATE")
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	
	@Column(name = "PURCHASE_EXCHANGE_RATE")
	public BigDecimal getPurchaseExchageRate() {
		return purchaseExchageRate;
	}
	public void setPurchaseExchageRate(BigDecimal purchaseExchageRate) {
		this.purchaseExchageRate = purchaseExchageRate;
	}
	
	@Column(name = "DEAL_WITH_TYPE")
	public String getDealWithType() {
		return dealWithType;
	}
	public void setDealWithType(String dealWithType) {
		this.dealWithType = dealWithType;
	}
	
	@Column(name = "SALE_AMOUNT")
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	@Column(name = "PD_CURRENCY_ID")
	public BigDecimal getPdCurrencyId() {
		return pdCurrencyId;
	}
	public void setPdCurrencyId(BigDecimal pdCurrencyId) {
		this.pdCurrencyId = pdCurrencyId;
	}
	
	@Column(name = "PD_FC_AMOUNT")
	public BigDecimal getPdFCAmount() {
		return pdFCAmount;
	}
	public void setPdFCAmount(BigDecimal pdFCAmount) {
		this.pdFCAmount = pdFCAmount;
	}
	
	@Column(name = "PD_LOCAL_EXCHANGE_RATE")
	public BigDecimal getPdLocalExchangeRate() {
		return pdLocalExchangeRate;
	}
	public void setPdLocalExchangeRate(BigDecimal pdLocalExchangeRate) {
		this.pdLocalExchangeRate = pdLocalExchangeRate;
	}
	
	@Column(name = "PD_LOCAL_AMOUNT")
	public BigDecimal getPdLocalAmount() {
		return pdLocalAmount;
	}
	public void setPdLocalAmount(BigDecimal pdLocalAmount) {
		this.pdLocalAmount = pdLocalAmount;
	}
	
	@Column(name = "PD_LINE_TYPE")
	public String getPdLineType() {
		return pdLineType;
	}
	public void setPdLineType(String pdLineType) {
		this.pdLineType = pdLineType;
	}
	
	@Column(name = "PD_EXCHANGE_RATE")
	public BigDecimal getPdExchangeRate() {
		return pdExchangeRate;
	}
	public void setPdExchangeRate(BigDecimal pdExchangeRate) {
		this.pdExchangeRate = pdExchangeRate;
	}
	
	@Column(name = "PD_BANK_ACCT_DET_ID")
	public BigDecimal getPdBankAcctDetId() {
		return pdBankAcctDetId;
	}
	public void setPdBankAcctDetId(BigDecimal pdBankAcctDetId) {
		this.pdBankAcctDetId = pdBankAcctDetId;
	}
	
	@Column(name = "PD_BANK_ID")
	public BigDecimal getPdBankId() {
		return pdBankId;
	}
	public void setPdBankId(BigDecimal pdBankId) {
		this.pdBankId = pdBankId;
	}
	
	@Column(name = "PD_BANK_CODE")
	public String getPdBankCode() {
		return pdBankCode;
	}
	public void setPdBankCode(String pdBankCode) {
		this.pdBankCode = pdBankCode;
	}
	
	@Column(name = "PD_QUOTE_NAME")
	public String getPdQuoteName() {
		return pdQuoteName;
	}
	public void setPdQuoteName(String pdQuoteName) {
		this.pdQuoteName = pdQuoteName;
	}
	
	@Column(name = "SD_CURRENCY_ID")
	public BigDecimal getSdCurrencyId() {
		return sdCurrencyId;
	}
	public void setSdCurrencyId(BigDecimal sdCurrencyId) {
		this.sdCurrencyId = sdCurrencyId;
	}
	
	@Column(name = "SD_FC_AMOUNT")
	public BigDecimal getSdFCAmount() {
		return sdFCAmount;
	}
	public void setSdFCAmount(BigDecimal sdFCAmount) {
		this.sdFCAmount = sdFCAmount;
	}
	
	@Column(name = "SD_LOCAL_EXCHANGE_RATE")
	public BigDecimal getSdLocalExchangeRate() {
		return sdLocalExchangeRate;
	}
	public void setSdLocalExchangeRate(BigDecimal sdLocalExchangeRate) {
		this.sdLocalExchangeRate = sdLocalExchangeRate;
	}
	
	@Column(name = "SD_LOCAL_AMOUNT")
	public BigDecimal getSdLocalAmount() {
		return sdLocalAmount;
	}
	public void setSdLocalAmount(BigDecimal sdLocalAmount) {
		this.sdLocalAmount = sdLocalAmount;
	}
	
	@Column(name = "SD_LINE_TYPE")
	public String getSdLineType() {
		return sdLineType;
	}
	public void setSdLineType(String sdLineType) {
		this.sdLineType = sdLineType;
	}
	
	@Column(name = "SD_EXCHANGE_RATE")
	public BigDecimal getSdExchangeRate() {
		return sdExchangeRate;
	}
	public void setSdExchangeRate(BigDecimal sdExchangeRate) {
		this.sdExchangeRate = sdExchangeRate;
	}
	
	@Column(name = "SD_BANK_ACCT_DET_ID")
	public BigDecimal getSdBankAcctDetId() {
		return sdBankAcctDetId;
	}
	public void setSdBankAcctDetId(BigDecimal sdBankAcctDetId) {
		this.sdBankAcctDetId = sdBankAcctDetId;
	}
	
	@Column(name = "SD_BANK_ID")
	public BigDecimal getSdBankId() {
		return sdBankId;
	}
	public void setSdBankId(BigDecimal sdBankId) {
		this.sdBankId = sdBankId;
	}
	
	@Column(name = "SD_BANK_CODE")
	public String getSdBankCode() {
		return sdBankCode;
	}
	public void setSdBankCode(String sdBankCode) {
		this.sdBankCode = sdBankCode;
	}
	
	@Column(name = "SD_QUOTE_NAME")
	public String getSdQuoteName() {
		return sdQuoteName;
	}
	public void setSdQuoteName(String sdQuoteName) {
		this.sdQuoteName = sdQuoteName;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name = "PD_BANK_FULL_NAME")
	public String getPdBankFullName() {
		return pdBankFullName;
	}
	public void setPdBankFullName(String pdBankFullName) {
		this.pdBankFullName = pdBankFullName;
	}
	
	@Column(name = "SD_BANK_FULL_NAME")
	public String getSdBankFullName() {
		return sdBankFullName;
	}
	public void setSdBankFullName(String sdBankFullName) {
		this.sdBankFullName = sdBankFullName;
	}
	
	
	
	

}
