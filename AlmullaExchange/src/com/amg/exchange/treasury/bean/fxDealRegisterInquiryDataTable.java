package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class fxDealRegisterInquiryDataTable implements Serializable{

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
	// to view purpose
	private String view;
	
	
	public BigDecimal getTreasuryDealHeaderId() {
		return treasuryDealHeaderId;
	}
	public void setTreasuryDealHeaderId(BigDecimal treasuryDealHeaderId) {
		this.treasuryDealHeaderId = treasuryDealHeaderId;
	}
	
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	public BigDecimal getTotalPurchaseFCAmount() {
		return totalPurchaseFCAmount;
	}
	public void setTotalPurchaseFCAmount(BigDecimal totalPurchaseFCAmount) {
		this.totalPurchaseFCAmount = totalPurchaseFCAmount;
	}
	
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	
	public BigDecimal getPurchaseExchageRate() {
		return purchaseExchageRate;
	}
	public void setPurchaseExchageRate(BigDecimal purchaseExchageRate) {
		this.purchaseExchageRate = purchaseExchageRate;
	}
	
	public String getDealWithType() {
		return dealWithType;
	}
	public void setDealWithType(String dealWithType) {
		this.dealWithType = dealWithType;
	}
	
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	public BigDecimal getPdCurrencyId() {
		return pdCurrencyId;
	}
	public void setPdCurrencyId(BigDecimal pdCurrencyId) {
		this.pdCurrencyId = pdCurrencyId;
	}
	
	public BigDecimal getPdFCAmount() {
		return pdFCAmount;
	}
	public void setPdFCAmount(BigDecimal pdFCAmount) {
		this.pdFCAmount = pdFCAmount;
	}
	
	public BigDecimal getPdLocalExchangeRate() {
		return pdLocalExchangeRate;
	}
	public void setPdLocalExchangeRate(BigDecimal pdLocalExchangeRate) {
		this.pdLocalExchangeRate = pdLocalExchangeRate;
	}
	
	public BigDecimal getPdLocalAmount() {
		return pdLocalAmount;
	}
	public void setPdLocalAmount(BigDecimal pdLocalAmount) {
		this.pdLocalAmount = pdLocalAmount;
	}
	
	public String getPdLineType() {
		return pdLineType;
	}
	public void setPdLineType(String pdLineType) {
		this.pdLineType = pdLineType;
	}
	
	public BigDecimal getPdExchangeRate() {
		return pdExchangeRate;
	}
	public void setPdExchangeRate(BigDecimal pdExchangeRate) {
		this.pdExchangeRate = pdExchangeRate;
	}
	
	public BigDecimal getPdBankAcctDetId() {
		return pdBankAcctDetId;
	}
	public void setPdBankAcctDetId(BigDecimal pdBankAcctDetId) {
		this.pdBankAcctDetId = pdBankAcctDetId;
	}
	
	public BigDecimal getPdBankId() {
		return pdBankId;
	}
	public void setPdBankId(BigDecimal pdBankId) {
		this.pdBankId = pdBankId;
	}
	
	public String getPdBankCode() {
		return pdBankCode;
	}
	public void setPdBankCode(String pdBankCode) {
		this.pdBankCode = pdBankCode;
	}
	
	public String getPdQuoteName() {
		return pdQuoteName;
	}
	public void setPdQuoteName(String pdQuoteName) {
		this.pdQuoteName = pdQuoteName;
	}
	
	public BigDecimal getSdCurrencyId() {
		return sdCurrencyId;
	}
	public void setSdCurrencyId(BigDecimal sdCurrencyId) {
		this.sdCurrencyId = sdCurrencyId;
	}
	
	public BigDecimal getSdFCAmount() {
		return sdFCAmount;
	}
	public void setSdFCAmount(BigDecimal sdFCAmount) {
		this.sdFCAmount = sdFCAmount;
	}
	
	public BigDecimal getSdLocalExchangeRate() {
		return sdLocalExchangeRate;
	}
	public void setSdLocalExchangeRate(BigDecimal sdLocalExchangeRate) {
		this.sdLocalExchangeRate = sdLocalExchangeRate;
	}
	
	public BigDecimal getSdLocalAmount() {
		return sdLocalAmount;
	}
	public void setSdLocalAmount(BigDecimal sdLocalAmount) {
		this.sdLocalAmount = sdLocalAmount;
	}
	
	public String getSdLineType() {
		return sdLineType;
	}
	public void setSdLineType(String sdLineType) {
		this.sdLineType = sdLineType;
	}
	
	public BigDecimal getSdExchangeRate() {
		return sdExchangeRate;
	}
	public void setSdExchangeRate(BigDecimal sdExchangeRate) {
		this.sdExchangeRate = sdExchangeRate;
	}
	
	public BigDecimal getSdBankAcctDetId() {
		return sdBankAcctDetId;
	}
	public void setSdBankAcctDetId(BigDecimal sdBankAcctDetId) {
		this.sdBankAcctDetId = sdBankAcctDetId;
	}
	
	public BigDecimal getSdBankId() {
		return sdBankId;
	}
	public void setSdBankId(BigDecimal sdBankId) {
		this.sdBankId = sdBankId;
	}
	
	public String getSdBankCode() {
		return sdBankCode;
	}
	public void setSdBankCode(String sdBankCode) {
		this.sdBankCode = sdBankCode;
	}
	
	public String getSdQuoteName() {
		return sdQuoteName;
	}
	public void setSdQuoteName(String sdQuoteName) {
		this.sdQuoteName = sdQuoteName;
	}
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	
	public String getPdBankFullName() {
		return pdBankFullName;
	}
	public void setPdBankFullName(String pdBankFullName) {
		this.pdBankFullName = pdBankFullName;
	}
	
	public String getSdBankFullName() {
		return sdBankFullName;
	}
	public void setSdBankFullName(String sdBankFullName) {
		this.sdBankFullName = sdBankFullName;
	}
	
	

}
