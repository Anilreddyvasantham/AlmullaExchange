package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerInquiryDataTable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date TransferDate;
	private String beneDetails;
	private String bankPayable;
	private String currencyName;
	private BigDecimal transactionAmount;
	private String status;
	private BigDecimal transactionNumber;
	private BigDecimal documentFinanceYear;
	private String product;
	private BigDecimal collectionDocumentNo;
	private String transactionTypeDesc;
	private Boolean declarationReport=false;
	private BigDecimal collectionDocYear;
	private BigDecimal collectionDocCode;
	private String correspondingBankCode;
	

	private BigDecimal customerReference;

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	

	public CustomerInquiryDataTable() {
		super();
	}

	public CustomerInquiryDataTable(Date transferDate, String beneDetails,
			String bankPayable, String currencyName,
			BigDecimal transactionAmount, String status,
			BigDecimal transactionNumber, BigDecimal documentFinanceYear,
			String product, BigDecimal collectionDocumentNo,
			String transactionTypeDesc, Boolean declarationReport,
			BigDecimal collectionDocYear, BigDecimal collectionDocCode,
			String correspondingBankCode) {
		super();
		TransferDate = transferDate;
		this.beneDetails = beneDetails;
		this.bankPayable = bankPayable;
		this.currencyName = currencyName;
		this.transactionAmount = transactionAmount;
		this.status = status;
		this.transactionNumber = transactionNumber;
		this.documentFinanceYear = documentFinanceYear;
		this.product = product;
		this.collectionDocumentNo = collectionDocumentNo;
		this.transactionTypeDesc = transactionTypeDesc;
		this.declarationReport = declarationReport;
		this.collectionDocYear = collectionDocYear;
		this.collectionDocCode = collectionDocCode;
		this.correspondingBankCode = correspondingBankCode;
	}

	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}

	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	public BigDecimal getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(BigDecimal transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public Date getTransferDate() {
		return TransferDate;
	}
	public void setTransferDate(Date transferDate) {
		TransferDate = transferDate;
	}

	public String getBeneDetails() {
		return beneDetails;
	}
	public void setBeneDetails(String beneDetails) {
		this.beneDetails = beneDetails;
	}

	public String getBankPayable() {
		return bankPayable;
	}
	public void setBankPayable(String bankPayable) {
		this.bankPayable = bankPayable;
	}

	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public BigDecimal getCollectionDocumentNo() {
		return collectionDocumentNo;
	}
	public void setCollectionDocumentNo(BigDecimal collectionDocumentNo) {
		this.collectionDocumentNo = collectionDocumentNo;
	}

	public String getTransactionTypeDesc() {
		return transactionTypeDesc;
	}
	public void setTransactionTypeDesc(String transactionTypeDesc) {
		this.transactionTypeDesc = transactionTypeDesc;
	}

	public Boolean getDeclarationReport() {
		return declarationReport;
	}
	public void setDeclarationReport(Boolean declarationReport) {
		this.declarationReport = declarationReport;
	}

	public BigDecimal getCollectionDocYear() {
		return collectionDocYear;
	}
	public void setCollectionDocYear(BigDecimal collectionDocYear) {
		this.collectionDocYear = collectionDocYear;
	}

	public BigDecimal getCollectionDocCode() {
		return collectionDocCode;
	}
	public void setCollectionDocCode(BigDecimal collectionDocCode) {
		this.collectionDocCode = collectionDocCode;
	}

	public String getCorrespondingBankCode() {
		return correspondingBankCode;
	}
	public void setCorrespondingBankCode(String correspondingBankCode) {
		this.correspondingBankCode = correspondingBankCode;
	}
	
}
