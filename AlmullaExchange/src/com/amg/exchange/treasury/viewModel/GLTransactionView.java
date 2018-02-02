package com.amg.exchange.treasury.viewModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_GL_TRNX")
public class GLTransactionView implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "SERIAL_NO")
	private BigDecimal serialNo;
	@Column(name = "APPLICATION_COUNTRY_ID")
	private BigDecimal applicationCountryId;
	@Column(name = "COMPANY_ID")
	private BigDecimal companyId;
	@Column(name = "DOCUMENT_ID")
	private BigDecimal documentId;
	@Column(name = "DOCUMENT_NUMBER")
	private BigDecimal documentNo;
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	private BigDecimal documentFinancialYear;
	@Column(name = "ACYYMM")
	private Date acMMYY;
	@Column(name = "DOCUMENT_DATE")
	private Date documentDate;
	@Column(name = "ISACTIVE")
	private String isActive;
	@Column(name = "MTCNO")
	private String mctNo;
	@Column(name = "FA_ACCOUNT_NUMBER")
	private String faAccountNumber;
	@Column(name = "LINE_NUMBER")
	private BigDecimal lineNumber;
	@Column(name = "CURRENCY_ID")
	private BigDecimal currencyId;
	@Column(name = "ITEM_DESCRIPTION")
	private String itemDescription;
	/*@Column(name = "CURRENCY_DESC")
	private String currencyDescription;*/
	@Column(name = "FOREIGN_AMOUNT")
	private BigDecimal foreignAmount;
	@Column(name = "EXCHANGE_RATE")
	private BigDecimal exchangeRate;
	@Column(name = "LOCAL_AMOUNT")
	private BigDecimal localAmount;
	@Column(name = "SPECIAL_DEAL")
	private String specialDeal;
	@Column(name = "DOCUMENT_CODE")
	private BigDecimal documentCode;
	@Column(name = "COMPANY_CODE")
	private BigDecimal copmanyCode;
	@Column(name = "CUSTOMER_ID")
	private BigDecimal cuctomerRef;
	@Column(name = "STATUS_DESCRIPTION")
	private String statusDesc;

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

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
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
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

	public BigDecimal getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(BigDecimal lineNumber) {
		this.lineNumber = lineNumber;
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

	/*public String getCurrencyDescription() {
		return currencyDescription;
	}

	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}*/

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

	public BigDecimal getCuctomerRef() {
		return cuctomerRef;
	}

	public void setCuctomerRef(BigDecimal cuctomerRef) {
		this.cuctomerRef = cuctomerRef;
	}
}
