package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class RemittanceBranchWiseEnquiryDataTableBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private BigDecimal documentYear;
	private BigDecimal documentNo;
	private BigDecimal customerRef;
	private String beneficiaryName;
	private String beneficiaryBank;
	private String beneficiaryBranch;
	// Bank service
	private String routingBank;
	private String bankingChannel;
	private String applicationType;
	private BigDecimal fcAmount;
	private BigDecimal exchangeRate;
	private BigDecimal netAmount;
	private String beneficiaryBankAndBranch;
	private String bankService;
	private String fcAmountAndExchangeRate;
	
	
	
	
	public String getBeneficiaryBankAndBranch() {
		return beneficiaryBankAndBranch;
	}
	public String getBankService() {
		return bankService;
	}
	public String getFcAmountAndExchangeRate() {
		return fcAmountAndExchangeRate;
	}
	public void setBeneficiaryBankAndBranch(String beneficiaryBankAndBranch) {
		this.beneficiaryBankAndBranch = beneficiaryBankAndBranch;
	}
	public void setBankService(String bankService) {
		this.bankService = bankService;
	}
	public void setFcAmountAndExchangeRate(String fcAmountAndExchangeRate) {
		this.fcAmountAndExchangeRate = fcAmountAndExchangeRate;
	}
	public BigDecimal getDocumentYear() {
		return documentYear;
	}
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public BigDecimal getCustomerRef() {
		return customerRef;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public String getBeneficiaryBank() {
		return beneficiaryBank;
	}
	public String getBeneficiaryBranch() {
		return beneficiaryBranch;
	}
	public String getRoutingBank() {
		return routingBank;
	}
	public String getBankingChannel() {
		return bankingChannel;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public BigDecimal getFcAmount() {
		return fcAmount;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setDocumentYear(BigDecimal documentYear) {
		this.documentYear = documentYear;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	public void setCustomerRef(BigDecimal customerRef) {
		this.customerRef = customerRef;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}
	public void setBeneficiaryBranch(String beneficiaryBranch) {
		this.beneficiaryBranch = beneficiaryBranch;
	}
	public void setRoutingBank(String routingBank) {
		this.routingBank = routingBank;
	}
	public void setBankingChannel(String bankingChannel) {
		this.bankingChannel = bankingChannel;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public void setFcAmount(BigDecimal fcAmount) {
		this.fcAmount = fcAmount;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	
	
}
