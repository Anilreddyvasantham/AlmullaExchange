package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import com.amg.exchange.treasury.model.TreasuryDealRegisterView;

public class FxDealRegisterInquiryReport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String company;
	private String bank;
	private String purchaseCurrency;
	private String saleCurrency;
	private Date fromDocDate;
	private Date toDocDate;
	
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNumber;
	private Date documentDate;
	private String pdBankCode;
	private String pdBankFullName;
	private String pdQuoteName;
	private BigDecimal pdFCAmount;
	private Date valueDate;
	private String sdBankCode;
	private String sdBankFullName;
	private String sdQuoteName;
	private BigDecimal sdFCAmount;
	private BigDecimal pdExchangeRate;
	
	private List<FxDealRegisterInquiryReport> listFxDealRegisterInquiryReport;
	
	
	
	//Getters and Setters.
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getPurchaseCurrency() {
		return purchaseCurrency;
	}
	public void setPurchaseCurrency(String purchaseCurrency) {
		this.purchaseCurrency = purchaseCurrency;
	}
	public String getSaleCurrency() {
		return saleCurrency;
	}
	public void setSaleCurrency(String saleCurrency) {
		this.saleCurrency = saleCurrency;
	}
	public Date getFromDocDate() {
		return fromDocDate;
	}
	public void setFromDocDate(Date fromDocDate) {
		this.fromDocDate = fromDocDate;
	}
	public Date getToDocDate() {
		return toDocDate;
	}
	public void setToDocDate(Date toDocDate) {
		this.toDocDate = toDocDate;
	}
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	public List<FxDealRegisterInquiryReport> getListFxDealRegisterInquiryReport() {
		return listFxDealRegisterInquiryReport;
	}
	public void setListFxDealRegisterInquiryReport(
			List<FxDealRegisterInquiryReport> listFxDealRegisterInquiryReport) {
		this.listFxDealRegisterInquiryReport = listFxDealRegisterInquiryReport;
	}
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public String getPdBankCode() {
		return pdBankCode;
	}
	public void setPdBankCode(String pdBankCode) {
		this.pdBankCode = pdBankCode;
	}
	public String getPdBankFullName() {
		return pdBankFullName;
	}
	public void setPdBankFullName(String pdBankFullName) {
		this.pdBankFullName = pdBankFullName;
	}
	public String getPdQuoteName() {
		return pdQuoteName;
	}
	public void setPdQuoteName(String pdQuoteName) {
		this.pdQuoteName = pdQuoteName;
	}
	public BigDecimal getPdFCAmount() {
		return pdFCAmount;
	}
	public void setPdFCAmount(BigDecimal pdFCAmount) {
		this.pdFCAmount = pdFCAmount;
	}
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public String getSdBankCode() {
		return sdBankCode;
	}
	public void setSdBankCode(String sdBankCode) {
		this.sdBankCode = sdBankCode;
	}
	public String getSdBankFullName() {
		return sdBankFullName;
	}
	public void setSdBankFullName(String sdBankFullName) {
		this.sdBankFullName = sdBankFullName;
	}
	public String getSdQuoteName() {
		return sdQuoteName;
	}
	public void setSdQuoteName(String sdQuoteName) {
		this.sdQuoteName = sdQuoteName;
	}
	public BigDecimal getSdFCAmount() {
		return sdFCAmount;
	}
	public void setSdFCAmount(BigDecimal sdFCAmount) {
		this.sdFCAmount = sdFCAmount;
	}
	public BigDecimal getPdExchangeRate() {
		return pdExchangeRate;
	}
	public void setPdExchangeRate(BigDecimal pdExchangeRate) {
		this.pdExchangeRate = pdExchangeRate;
	}
	
}
