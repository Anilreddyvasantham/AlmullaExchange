package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_TREASURY_CUSTOMER_DEAL")
public class TreasuryCustomerDeal implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IDNO")
	private BigDecimal idNo;
	
	@Column(name="TREASURY_DEAL_HEADER_ID") 
	private BigDecimal treasuryDealHeaderId;
	
	@Column(name="TREASURY_DEAL_DETAIL_ID")
	private BigDecimal tresuryDealDetailId;
	
	@Column(name="APPLICATION_COUNTRY_ID")
	private BigDecimal applicationCountryId;
	
	@Column(name="COMPANY_ID")
	private BigDecimal companyId;
	
	@Column(name="DOCUMENT_FINANCE_YEAR")
	private BigDecimal documentFinanceYear;	
	
	@Column(name="DOCUMENT_NUMBER")
	private BigDecimal documentNumber;
	
	@Column(name="DOCUMENT_ID")
	private BigDecimal documentId;
	
	@Column(name="DEAL_NO")
	private String dealNo;
	
	@Column(name="DOCUMENT_DATE")
	private Date documentDate;
	
	@Column(name="DEAL_WITH")
	private BigDecimal dealWith;
	
	@Column(name="BANK_ADDRESS")
	private String bankAddress;
	
	@Column(name="TOTAL_PURCHASE_FC_AMOUNT")
	private BigDecimal totalPurchaseFcAmount;
	
	@Column(name="VALUE_DATE")
	private Date valueDate;
	
	@Column(name="PURCHASE_EXCHANGE_RATE")
	private BigDecimal purchaseExchangeRate;
	
	@Column(name="DEAL_WITH_TYPE")
	private String dealWithType;
	
	@Column(name="SALE_AMOUNT")
	private BigDecimal saleAmount;
	
	@Column(name="DEAL_CONCLUDED_WITH")
	private String dealConcludedWith;
	
	@Column(name="DEAL_CONCLUDED_BY")
	private String dealConcludedBy;
	
	@Column(name="DEAL_DESCRIPTION")
	private String dealDescription;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="DOCUMENT_CODE")
	private BigDecimal documentCode;
	
	@Column(name="FA_ACCOUNT_NUMBER")
	private String faAccountNumber;
	
	@Column(name="CURRENCY_ID")
	private BigDecimal currencyId;
	
	@Column(name="CURRENCY_CODE")
	private String currencyCode;
	
	@Column(name="CURRENCY_NAME")
	private String currencyName;
	
	@Column(name="FC_AMOUNT")
	private BigDecimal fcAmount;
	
	@Column(name="LOCAL_EXCHANGE_RATE")
	private BigDecimal localExchangeRate;
	
	@Column(name="LOCAL_AMOUNT")
	private BigDecimal localAmount;
	
	@Column(name="LINE_TYPE")
	private String lineType;
	
	@Column(name="EXCHANGE_RATE")
	private BigDecimal exchangeRate;
	
	@Column(name="BANK_CODE")
	private String bankCode;
	
	@Column(name="BANK_NAME")
	private String bankName;
	
	@Column(name="ACCOUNT_NUMBER")
	private String accountNumber;
	
	@Column(name="PAYMENT_INDICATOR")
	private String paymentIndicator;
	
	@Column(name="PAYMENT_DOCUMENT_ID")
	private BigDecimal paymentDocumentId;
	
	@Column(name="PAYMENT_FINANCE_YEAR")
	private BigDecimal paymentFinanceYear;
	
	@Column(name="PAYMENT_DOCUMENT_NO")
	private BigDecimal paymentDocumentNumber;
	
	

	public BigDecimal getIdNo() {
		return idNo;
	}

	public BigDecimal getTreasuryDealHeaderId() {
		return treasuryDealHeaderId;
	}

	public BigDecimal getTresuryDealDetailId() {
		return tresuryDealDetailId;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public String getDealNo() {
		return dealNo;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public BigDecimal getDealWith() {
		return dealWith;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public BigDecimal getTotalPurchaseFcAmount() {
		return totalPurchaseFcAmount;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public BigDecimal getPurchaseExchangeRate() {
		return purchaseExchangeRate;
	}

	public String getDealWithType() {
		return dealWithType;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public String getDealConcludedWith() {
		return dealConcludedWith;
	}

	public String getDealConcludedBy() {
		return dealConcludedBy;
	}

	public String getDealDescription() {
		return dealDescription;
	}

	public String getRemarks() {
		return remarks;
	}

	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public String getFaAccountNumber() {
		return faAccountNumber;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public BigDecimal getFcAmount() {
		return fcAmount;
	}

	public BigDecimal getLocalExchangeRate() {
		return localExchangeRate;
	}

	public BigDecimal getLocalAmount() {
		return localAmount;
	}

	public String getLineType() {
		return lineType;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getPaymentIndicator() {
		return paymentIndicator;
	}

	public BigDecimal getPaymentDocumentId() {
		return paymentDocumentId;
	}

	public BigDecimal getPaymentFinanceYear() {
		return paymentFinanceYear;
	}

	public BigDecimal getPaymentDocumentNumber() {
		return paymentDocumentNumber;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}

	public void setTreasuryDealHeaderId(BigDecimal treasuryDealHeaderId) {
		this.treasuryDealHeaderId = treasuryDealHeaderId;
	}

	public void setTresuryDealDetailId(BigDecimal tresuryDealDetailId) {
		this.tresuryDealDetailId = tresuryDealDetailId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public void setDealWith(BigDecimal dealWith) {
		this.dealWith = dealWith;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public void setTotalPurchaseFcAmount(BigDecimal totalPurchaseFcAmount) {
		this.totalPurchaseFcAmount = totalPurchaseFcAmount;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public void setPurchaseExchangeRate(BigDecimal purchaseExchangeRate) {
		this.purchaseExchangeRate = purchaseExchangeRate;
	}

	public void setDealWithType(String dealWithType) {
		this.dealWithType = dealWithType;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public void setDealConcludedWith(String dealConcludedWith) {
		this.dealConcludedWith = dealConcludedWith;
	}

	public void setDealConcludedBy(String dealConcludedBy) {
		this.dealConcludedBy = dealConcludedBy;
	}

	public void setDealDescription(String dealDescription) {
		this.dealDescription = dealDescription;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	public void setFaAccountNumber(String faAccountNumber) {
		this.faAccountNumber = faAccountNumber;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	public void setLocalExchangeRate(BigDecimal localExchangeRate) {
		this.localExchangeRate = localExchangeRate;
	}

	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setPaymentIndicator(String paymentIndicator) {
		this.paymentIndicator = paymentIndicator;
	}

	public void setPaymentDocumentId(BigDecimal paymentDocumentId) {
		this.paymentDocumentId = paymentDocumentId;
	}

	public void setPaymentFinanceYear(BigDecimal paymentFinanceYear) {
		this.paymentFinanceYear = paymentFinanceYear;
	}

	public void setPaymentDocumentNumber(BigDecimal paymentDocumentNumber) {
		this.paymentDocumentNumber = paymentDocumentNumber;
	}
	
	

}
