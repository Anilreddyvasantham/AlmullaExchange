package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BankTransferEnquiryDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String company;
	private String documentDecsrption;
	private String description;
	private String attention;
	private Date valueDate;
	private Date documentDate;
	private String bankTransferYear;
	private String bankTransferReference;
	private List<BankTransferEnquiryDataTable> fromList;
	private List<BankTransferEnquiryDataTable> toList;
	private BigDecimal frmPk;
	private String frmBank;
	private String frmAccount;
	private String frmCurrency;
	private BigDecimal frmfcAmount;
	private BigDecimal frmlocalFcAmount;
	private BigDecimal frmExchangeRate;
	private String frmInstructionNumber;
	private String frmInstructionDescription;

	private  BigDecimal toPk;
	private String toBank;
	private String toAccount;
	private String toCurrency;
	private BigDecimal  TofcAmount;
	private BigDecimal toLocalFcAmount;
	private BigDecimal toExchangeRate;
	private String toInstructionNumber;
	private String toInstructionDescription;
	private  BigDecimal fromBankId;
	private  BigDecimal toBankId;
	private  BigDecimal fromCurrencyId;
	private  BigDecimal toCurrencyId;
	private String createdBy;
	private Date createdDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDocumentDecsrption() {
		return documentDecsrption;
	}
	public void setDocumentDecsrption(String documentDecsrption) {
		this.documentDecsrption = documentDecsrption;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getBankTransferYear() {
		return bankTransferYear;
	}
	public void setBankTransferYear(String bankTransferYear) {
		this.bankTransferYear = bankTransferYear;
	}
	public String getBankTransferReference() {
		return bankTransferReference;
	}
	public void setBankTransferReference(String bankTransferReference) {
		this.bankTransferReference = bankTransferReference;
	}
	public String getFrmBank() {
		return frmBank;
	}
	public void setFrmBank(String frmBank) {
		this.frmBank = frmBank;
	}
	public String getFrmAccount() {
		return frmAccount;
	}
	public void setFrmAccount(String frmAccount) {
		this.frmAccount = frmAccount;
	}
	public String getFrmCurrency() {
		return frmCurrency;
	}
	public void setFrmCurrency(String frmCurrency) {
		this.frmCurrency = frmCurrency;
	}
	public BigDecimal getFrmfcAmount() {
		return frmfcAmount;
	}
	public void setFrmfcAmount(BigDecimal frmfcAmount) {
		this.frmfcAmount = frmfcAmount;
	}
	public BigDecimal getFrmlocalFcAmount() {
		return frmlocalFcAmount;
	}
	public void setFrmlocalFcAmount(BigDecimal frmlocalFcAmount) {
		this.frmlocalFcAmount = frmlocalFcAmount;
	}
	public BigDecimal getFrmExchangeRate() {
		return frmExchangeRate;
	}
	public void setFrmExchangeRate(BigDecimal frmExchangeRate) {
		this.frmExchangeRate = frmExchangeRate;
	}
	public String getFrmInstructionNumber() {
		return frmInstructionNumber;
	}
	public void setFrmInstructionNumber(String frmInstructionNumber) {
		this.frmInstructionNumber = frmInstructionNumber;
	}
	public String getFrmInstructionDescription() {
		return frmInstructionDescription;
	}
	public void setFrmInstructionDescription(String frmInstructionDescription) {
		this.frmInstructionDescription = frmInstructionDescription;
	}
	public String getToBank() {
		return toBank;
	}
	public void setToBank(String toBank) {
		this.toBank = toBank;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public String getToCurrency() {
		return toCurrency;
	}
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	public BigDecimal getTofcAmount() {
		return TofcAmount;
	}
	public void setTofcAmount(BigDecimal tofcAmount) {
		TofcAmount = tofcAmount;
	}
	public BigDecimal getToLocalFcAmount() {
		return toLocalFcAmount;
	}
	public void setToLocalFcAmount(BigDecimal toLocalFcAmount) {
		this.toLocalFcAmount = toLocalFcAmount;
	}
	public BigDecimal getToExchangeRate() {
		return toExchangeRate;
	}
	public void setToExchangeRate(BigDecimal toExchangeRate) {
		this.toExchangeRate = toExchangeRate;
	}
	public String getToInstructionNumber() {
		return toInstructionNumber;
	}
	public void setToInstructionNumber(String toInstructionNumber) {
		this.toInstructionNumber = toInstructionNumber;
	}
	public String getToInstructionDescription() {
		return toInstructionDescription;
	}
	public void setToInstructionDescription(String toInstructionDescription) {
		this.toInstructionDescription = toInstructionDescription;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public List<BankTransferEnquiryDataTable> getFromList() {
		return fromList;
	}
	public void setFromList(List<BankTransferEnquiryDataTable> fromList) {
		this.fromList = fromList;
	}
	public List<BankTransferEnquiryDataTable> getToList() {
		return toList;
	}
	public void setToList(List<BankTransferEnquiryDataTable> toList) {
		this.toList = toList;
	}
	public BigDecimal getToPk() {
		return toPk;
	}
	public void setToPk(BigDecimal toPk) {
		this.toPk = toPk;
	}
	public BigDecimal getFrmPk() {
		return frmPk;
	}
	public void setFrmPk(BigDecimal frmPk) {
		this.frmPk = frmPk;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getFromBankId() {
		return fromBankId;
	}
	public void setFromBankId(BigDecimal fromBankId) {
		this.fromBankId = fromBankId;
	}
	public BigDecimal getToBankId() {
		return toBankId;
	}
	public void setToBankId(BigDecimal toBankId) {
		this.toBankId = toBankId;
	}
	public BigDecimal getFromCurrencyId() {
		return fromCurrencyId;
	}
	public void setFromCurrencyId(BigDecimal fromCurrencyId) {
		this.fromCurrencyId = fromCurrencyId;
	}
	public BigDecimal getToCurrencyId() {
		return toCurrencyId;
	}
	public void setToCurrencyId(BigDecimal toCurrencyId) {
		this.toCurrencyId = toCurrencyId;
	}


}
