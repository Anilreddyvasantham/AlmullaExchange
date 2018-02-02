package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.util.Date;

public class GLTransactionForADocumentDataTable {

	private BigDecimal serialNo;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private BigDecimal DocumentId;
	private BigDecimal documentNo;
	private BigDecimal documentFinancialYear;
	private Date acMMYY;
	private Date documentDate;
	private String isActive;
	private String mctNo;
	private String faAccountNumber;
	private BigDecimal currencyId;
	private String itemDescription;
	private String currencyDescription;
	private BigDecimal foreignAmount;
	private BigDecimal exchangeRate;
	private BigDecimal localAmount;
	private String specialDeal;
	private BigDecimal documentCode; 
	private BigDecimal copmanyCode;
	private BigDecimal lineNumber;
	private BigDecimal customerRef;
	
	public BigDecimal getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(BigDecimal serialNo) {
		this.serialNo = serialNo;
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
	public BigDecimal getDocumentId() {
		return DocumentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		DocumentId = documentId;
	}
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}
	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}
	
	public BigDecimal getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(BigDecimal lineNumber) {
		this.lineNumber = lineNumber;
	}
	public Date getAcMMYY() {
		return acMMYY;
	}
	public void setAcMMYY(Date acMMYY) {
		this.acMMYY = acMMYY;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getMctNo() {
		return mctNo;
	}
	public void setMctNo(String mctNo) {
		this.mctNo = mctNo;
	}
	public String getFaAccountNumber() {
		return faAccountNumber;
	}
	public void setFaAccountNumber(String faAccountNumber) {
		this.faAccountNumber = faAccountNumber;
	}
	
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getCurrencyDescription() {
		return currencyDescription;
	}
	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}
	public BigDecimal getForeignAmount() {
		return foreignAmount;
	}
	public void setForeignAmount(BigDecimal foreignAmount) {
		this.foreignAmount = foreignAmount;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public BigDecimal getLocalAmount() {
		return localAmount;
	}
	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}
	public String getSpecialDeal() {
		return specialDeal;
	}
	public void setSpecialDeal(String specialDeal) {
		this.specialDeal = specialDeal;
	}
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	public BigDecimal getCopmanyCode() {
		return copmanyCode;
	}
	public void setCopmanyCode(BigDecimal copmanyCode) {
		this.copmanyCode = copmanyCode;
	}
	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}
	
}
