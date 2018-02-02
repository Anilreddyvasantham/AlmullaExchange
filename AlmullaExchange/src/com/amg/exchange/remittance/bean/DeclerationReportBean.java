package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeclerationReportBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String transactionRefNo;
	private BigDecimal documentNo;
	private String customerName;
	private BigDecimal localTransactionAmount;
	private String contactNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private String nationality;
	private String civilId;
	private String sourceOfIncome;
	private String logoPath;
	private String receiptReferenceNo;
	private BigDecimal applicationCountryId;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentId;
	private String toAddress;
	private String cashierName;
	private String companyName;
	private String purpose;
	private String signatutre;
	private String employeer;
	private String title;
	private String currencyAmount;
	private String currencyAmountCash;

	// Added by rabil from SVN 
	private String beneRelation;	
	private String txnTypeSelect;	
	private String declImagePath1;
	private String declImagePath2;
	private String declImagePath3;
	private BigDecimal customerReference;
	private String branchName;
	
	private String declImagePath4;
	public String getDeclImagePath4() {
			return declImagePath4;
		}

		public void setDeclImagePath4(String declImagePath4) {
			this.declImagePath4 = declImagePath4;
		}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmployeer() {
		return employeer;
	}

	public void setEmployeer(String employeer) {
		this.employeer = employeer;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSignatutre() {
		return signatutre;
	}

	public void setSignatutre(String signatutre) {
		this.signatutre = signatutre;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public String getTransactionRefNo() {
		return transactionRefNo;
	}

	public void setTransactionRefNo(String transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getLocalTransactionAmount() {
		return localTransactionAmount;
	}

	public void setLocalTransactionAmount(BigDecimal localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCivilId() {
		return civilId;
	}

	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}

	public String getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getReceiptReferenceNo() {
		return receiptReferenceNo;
	}

	public void setReceiptReferenceNo(String receiptReferenceNo) {
		this.receiptReferenceNo = receiptReferenceNo;
	}

	public String getCurrencyAmount() {
		return currencyAmount;
	}

	public void setCurrencyAmount(String currencyAmount) {
		this.currencyAmount = currencyAmount;
	}

	public String getCurrencyAmountCash() {
		return currencyAmountCash;
	}

	public void setCurrencyAmountCash(String currencyAmountCash) {
		this.currencyAmountCash = currencyAmountCash;
	}

	public String getBeneRelation() {
		return beneRelation;
	}

	public void setBeneRelation(String beneRelation) {
		this.beneRelation = beneRelation;
	}

	public String getTxnTypeSelect() {
		return txnTypeSelect;
	}

	public void setTxnTypeSelect(String txnTypeSelect) {
		this.txnTypeSelect = txnTypeSelect;
	}

	public String getDeclImagePath1() {
		return declImagePath1;
	}

	public void setDeclImagePath1(String declImagePath1) {
		this.declImagePath1 = declImagePath1;
	}

	public String getDeclImagePath2() {
		return declImagePath2;
	}

	public void setDeclImagePath2(String declImagePath2) {
		this.declImagePath2 = declImagePath2;
	}

	public String getDeclImagePath3() {
		return declImagePath3;
	}

	public void setDeclImagePath3(String declImagePath3) {
		this.declImagePath3 = declImagePath3;
	}

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	
	
}
