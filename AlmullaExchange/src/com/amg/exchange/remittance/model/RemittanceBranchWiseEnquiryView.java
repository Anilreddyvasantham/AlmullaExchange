package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_BRANCH_DAY_ENQUIRY")
public class RemittanceBranchWiseEnquiryView implements Serializable{

	private static final long serialVersionUID = 1L;
	private BigDecimal slNo;
	private BigDecimal applicationCountryId;
	private BigDecimal customerId;
	private BigDecimal documentId;
	private BigDecimal documentCode;
	private BigDecimal documnetFinanceYear;
	private BigDecimal documentNo;
	private Date documentDate;
	private BigDecimal countryBranchId;
	private String quoteName;
	private BigDecimal foreignTranxAmount;
	private BigDecimal localTranxCurrencyId;
	private BigDecimal localTranxAmount;
	private BigDecimal exchangeRateApplied;
	private BigDecimal localNetTranxAmount;
	private String highValueTranx;
	private String beneficiaryBank;
	private String beneficiayBranch;
	private String beneficiayName;
	private String beneficiayAccountNo;
	private String beneficiayFirstName;
	private String beneficiaySecondName;
	private String beneficiayThirdName;
	private String beneficiayFourthName;
	private String beneficiayFifthName;
	private String applicationTypeDesc;
	private String remittanceDescription;
	private String deliveryDescription;
	private String createdBy;
	private String routingBank;
	private BigDecimal customerReference;
	

	@Id
	@Column(name = "SLNO")
	public BigDecimal getSlNo() {
		return slNo;
	}
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumnetFinanceYear() {
		return documnetFinanceYear;
	}
	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	@Column(name = "DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	@Column(name = "COUNTRY_BRANCH_ID")
	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	@Column(name = "QUOTE_NAME")
	public String getQuoteName() {
		return quoteName;
	}
	@Column(name = "FOREIGN_TRANX_AMOUNT")
	public BigDecimal getForeignTranxAmount() {
		return foreignTranxAmount;
	}
	@Column(name = "LOCAL_TRANX_CURRENCY_ID")
	public BigDecimal getLocalTranxCurrencyId() {
		return localTranxCurrencyId;
	}
	@Column(name = "LOCAL_TRANX_AMOUNT")
	public BigDecimal getLocalTranxAmount() {
		return localTranxAmount;
	}
	@Column(name = "EXCHANGE_RATE_APPLIED")
	public BigDecimal getExchangeRateApplied() {
		return exchangeRateApplied;
	}
	@Column(name = "LOCAL_NET_TRANX_AMOUNT")
	public BigDecimal getLocalNetTranxAmount() {
		return localNetTranxAmount;
	}
	@Column(name = "HIGH_VALUE_TRANX")
	public String getHighValueTranx() {
		return highValueTranx;
	}
	@Column(name = "BENEFICIARY_BANK")
	public String getBeneficiaryBank() {
		return beneficiaryBank;
	}
	@Column(name = "BENEFICIARY_BRANCH")
	public String getBeneficiayBranch() {
		return beneficiayBranch;
	}
	@Column(name = "BENEFICIARY_NAME")
	public String getBeneficiayName() {
		return beneficiayName;
	}
	@Column(name = "BENEFICIARY_ACCOUNT_NO")
	public String getBeneficiayAccountNo() {
		return beneficiayAccountNo;
	}
	@Column(name = "BENEFICIARY_FIRST_NAME")
	public String getBeneficiayFirstName() {
		return beneficiayFirstName;
	}
	@Column(name = "BENEFICIARY_SECOND_NAME")
	public String getBeneficiaySecondName() {
		return beneficiaySecondName;
	}
	@Column(name = "BENEFICIARY_THIRD_NAME")
	public String getBeneficiayThirdName() {
		return beneficiayThirdName;
	}
	@Column(name = "BENEFICIARY_FOURTH_NAME")
	public String getBeneficiayFourthName() {
		return beneficiayFourthName;
	}
	@Column(name = "BENEFICIARY_FIFTH_NAME")
	public String getBeneficiayFifthName() {
		return beneficiayFifthName;
	}
	@Column(name = "APPLICATION_TYPE_DESC")
	public String getApplicationTypeDesc() {
		return applicationTypeDesc;
	}
	@Column(name = "REMITTANCE_DESCRIPTION")
	public String getRemittanceDescription() {
		return remittanceDescription;
	}
	@Column(name = "DELIVERY_DESCRIPTION")
	public String getDeliveryDescription() {
		return deliveryDescription;
	}
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	@Column(name = "ROUTING_BANK")
	public String getRoutingBank() {
		return routingBank;
	}
	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}
	
	
	public void setSlNo(BigDecimal slNo) {
		this.slNo = slNo;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	public void setDocumnetFinanceYear(BigDecimal documnetFinanceYear) {
		this.documnetFinanceYear = documnetFinanceYear;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	public void setForeignTranxAmount(BigDecimal foreignTranxAmount) {
		this.foreignTranxAmount = foreignTranxAmount;
	}
	public void setLocalTranxCurrencyId(BigDecimal localTranxCurrencyId) {
		this.localTranxCurrencyId = localTranxCurrencyId;
	}
	public void setLocalTranxAmount(BigDecimal localTranxAmount) {
		this.localTranxAmount = localTranxAmount;
	}
	public void setExchangeRateApplied(BigDecimal exchangeRateApplied) {
		this.exchangeRateApplied = exchangeRateApplied;
	}
	public void setLocalNetTranxAmount(BigDecimal localNetTranxAmount) {
		this.localNetTranxAmount = localNetTranxAmount;
	}
	public void setHighValueTranx(String highValueTranx) {
		this.highValueTranx = highValueTranx;
	}
	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}
	public void setBeneficiayBranch(String beneficiayBranch) {
		this.beneficiayBranch = beneficiayBranch;
	}
	public void setBeneficiayName(String beneficiayName) {
		this.beneficiayName = beneficiayName;
	}
	public void setBeneficiayAccountNo(String beneficiayAccountNo) {
		this.beneficiayAccountNo = beneficiayAccountNo;
	}
	public void setBeneficiayFirstName(String beneficiayFirstName) {
		this.beneficiayFirstName = beneficiayFirstName;
	}
	public void setBeneficiaySecondName(String beneficiaySecondName) {
		this.beneficiaySecondName = beneficiaySecondName;
	}
	public void setBeneficiayThirdName(String beneficiayThirdName) {
		this.beneficiayThirdName = beneficiayThirdName;
	}
	public void setBeneficiayFourthName(String beneficiayFourthName) {
		this.beneficiayFourthName = beneficiayFourthName;
	}
	public void setBeneficiayFifthName(String beneficiayFifthName) {
		this.beneficiayFifthName = beneficiayFifthName;
	}
	public void setApplicationTypeDesc(String applicationTypeDesc) {
		this.applicationTypeDesc = applicationTypeDesc;
	}
	public void setRemittanceDescription(String remittanceDescription) {
		this.remittanceDescription = remittanceDescription;
	}
	public void setDeliveryDescription(String deliveryDescription) {
		this.deliveryDescription = deliveryDescription;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public void setRoutingBank(String routingBank) {
		this.routingBank = routingBank;
	}
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	
	
	
}
