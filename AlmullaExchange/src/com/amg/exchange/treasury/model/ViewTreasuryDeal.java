package com.amg.exchange.treasury.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_TREASURY_DEAL_TICKET")
public class ViewTreasuryDeal {

	private BigDecimal treasuryDealHeaderId;
	private BigDecimal treasuryDealDetailsId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyid;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentId;
	private BigDecimal documentNumber;
	private String dealNo;
	private Date documentDate;
	private BigDecimal dealWith;
	private String bankAddress;
	private BigDecimal totalPurchaseFcAmount;
	private Date valuedate;
	private BigDecimal purchaseExchangeRate;
	private BigDecimal saleAmount;
	private String dealConcludedWith;
	private String dealConcludedBy;
	private String dealDescription;
	private BigDecimal documentCode;
	private String faAccountNo;
	private BigDecimal currencyId;
	private String currencyCode;
	private String currencyname;
	private BigDecimal fcAmount;
	private BigDecimal localExchangeRate;
	private BigDecimal exchangeRate;
	private BigDecimal localAmount;
	private String lineType;
	private String dealWithType;
	private String remarks;
	private String bankCode;
	private String accountNumber;
	private String telephone;
	private String faxNo;
	private String bankAddress1;
	private String bankFullName;
	

	
//	private String standardInstruction;

	
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
	public BigDecimal getCompanyid() {
		return companyid;
	}

	public void setCompanyid(BigDecimal companyid) {
		this.companyid = companyid;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	@Column(name = "DEAL_NO")
	public String getDealNo() {
		return dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	@Column(name = "DEAL_WITH")
	public BigDecimal getDealWith() {
		return dealWith;
	}

	public void setDealWith(BigDecimal dealWith) {
		this.dealWith = dealWith;
	}

	@Column(name = "BANK_ADDRESS")
	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	@Column(name = "VALUE_DATE")
	public Date getValuedate() {
		return valuedate;
	}

	public void setValuedate(Date valuedate) {
		this.valuedate = valuedate;
	}

	@Column(name = "PURCHASE_EXCHANGE_RATE")
	public BigDecimal getPurchaseExchangeRate() {
		return purchaseExchangeRate;
	}

	public void setPurchaseExchangeRate(BigDecimal purchaseExchangeRate) {
		this.purchaseExchangeRate = purchaseExchangeRate;
	}

	@Column(name = "SALE_AMOUNT")
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	@Column(name = "DEAL_CONCLUDED_WITH")
	public String getDealConcludedWith() {
		return dealConcludedWith;
	}

	public void setDealConcludedWith(String dealConcludedWith) {
		this.dealConcludedWith = dealConcludedWith;
	}

	@Column(name = "DEAL_CONCLUDED_BY")
	public String getDealConcludedBy() {
		return dealConcludedBy;
	}

	public void setDealConcludedBy(String dealConcludedBy) {
		this.dealConcludedBy = dealConcludedBy;
	}

	@Column(name = "DEAL_DESCRIPTION")
	public String getDealDescription() {
		return dealDescription;
	}

	public void setDealDescription(String dealDescription) {
		this.dealDescription = dealDescription;
	}

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "FA_ACCOUNT_NUMBER")
	public String getFaAccountNo() {
		return faAccountNo;
	}

	public void setFaAccountNo(String faAccountNo) {
		this.faAccountNo = faAccountNo;
	}

	@Column(name = "CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "CURRENCY_CODE")
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "CURRENCY_NAME")
	public String getCurrencyname() {
		return currencyname;
	}

	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}

	@Column(name = "FC_AMOUNT")
	public BigDecimal getFcAmount() {
		return fcAmount;
	}

	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}

	@Column(name = "LOCAL_EXCHANGE_RATE")
	public BigDecimal getLocalExchangeRate() {
		return localExchangeRate;
	}

	public void setLocalExchangeRate(BigDecimal localExchangeRate) {
		this.localExchangeRate = localExchangeRate;
	}

	@Column(name = "LOCAL_AMOUNT")
	public BigDecimal getLocalAmount() {
		return localAmount;
	}

	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}

	@Column(name = "LINE_TYPE")
	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	@Column(name = "DOCUMENT_NUMBER")
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Column(name = "TOTAL_PURCHASE_FC_AMOUNT")
	public BigDecimal getTotalPurchaseFcAmount() {
		return totalPurchaseFcAmount;
	}

	public void setTotalPurchaseFcAmount(BigDecimal totalPurchaseFcAmount) {
		this.totalPurchaseFcAmount = totalPurchaseFcAmount;
	}

	@Column(name = "DEAL_WITH_TYPE")
	public String getDealWithType() {
		return dealWithType;
	}

	public void setDealWithType(String dealWithType) {
		this.dealWithType = dealWithType;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Id
	@Column(name = "TREASURY_DEAL_DETAIL_ID")
	public BigDecimal getTreasuryDealDetailsId() {
		return treasuryDealDetailsId;
	}

	public void setTreasuryDealDetailsId(BigDecimal treasuryDealDetailsId) {
		this.treasuryDealDetailsId = treasuryDealDetailsId;
	}
	@Column(name = "EXCHANGE_RATE")
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "ACCOUNT_NUMBER")
	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	

	@Column(name = "TELEPONE_NO")
	public String getTelephone() {
		return telephone;
	}


	@Column(name = "FAX_NO")
	public String getFaxNo() {
		return faxNo;
	}


	@Column(name = "BANK_ADDRESS1")
	public String getBankAddress1() {
		return bankAddress1;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}


	public void setBankAddress1(String bankAddress1) {
		this.bankAddress1 = bankAddress1;
	}


	@Column(name = "BANK_FULL_NAME")
	public String getBankFullName() {
		return bankFullName;
	}


	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}

/*	@Column(name = "MESSAGE_DESCRIPTION")
	public String getStandardInstruction() {
		return standardInstruction;
	}


	public void setStandardInstruction(String standardInstruction) {
		this.standardInstruction = standardInstruction;
	}
	*/
	
	
	
	

}
