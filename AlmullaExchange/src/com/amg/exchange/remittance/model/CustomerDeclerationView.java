package com.amg.exchange.remittance.model;

import java.math.BigDecimal;
import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EX_CUST_HV_TRNX_DECLARATION")
public class CustomerDeclerationView {

	private BigDecimal idNo;
	private BigDecimal documentNo;
	private BigDecimal localTransactionAmount;
	private BigDecimal contactNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private String nationality;
	private BigDecimal civilId;
	private String sourceOfIncome;	
	private BigDecimal applicationCountryId;
	private BigDecimal documentFinanceYear;	
	private BigDecimal documentId;
	private BigDecimal customerId;
	private String title;
	private BigDecimal collectionDocumentFinanceYear;
	private BigDecimal collectionDocumentNo;
	private BigDecimal remittanceTransactionId;
	private Clob signatureSpecimenClob;
	private BigDecimal collectionDocumentId;
	
	private String relationDescription;;

	@Column(name="RELATION_DESC")
	public String getRelationDescription() {
		return relationDescription;
	}

	public void setRelationDescription(String relationDescription) {
		this.relationDescription = relationDescription;
	}

	
	//COLLECTION_DOC_ID
	
	@Id
	@Column(name = "ID_NO")
	public BigDecimal getIdNo() {
		return idNo;
	}

	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	
	
	
	/*@Column(name = "TRANSACTION_REF_NO")
	public BigDecimal getTransactionRefNo() {
		return transactionRefNo;
	}

	public void setTransactionRefNo(BigDecimal transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}*/

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "SIGNATURE_SPECIMEN_CLOB")
	public Clob getSignatureSpecimenClob() {
		return signatureSpecimenClob;
	}

	public void setSignatureSpecimenClob(Clob signatureSpecimenClob) {
		this.signatureSpecimenClob = signatureSpecimenClob;
	}
	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	/*@Column(name = "CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}*/

	@Column(name = "LOCAL_TRANX_AMOUNT")
	public BigDecimal getLocalTransactionAmount() {
		return localTransactionAmount;
	}

	public void setLocalTransactionAmount(BigDecimal localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}

	@Column(name = "CONTACT_NUMBER")
	public BigDecimal getContactNo() {
		return contactNo;
	}

	public void setContactNo(BigDecimal contactNo) {
		this.contactNo = contactNo;
	}

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "MIDDLE_NAME")
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "NATIONALITY")
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "CIVIL_ID")
	public BigDecimal getCivilId() {
		return civilId;
	}

	public void setCivilId(BigDecimal civilId) {
		this.civilId = civilId;
	}

	@Column(name = "SOURCE_OF_INCOME")
	public String getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
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
		
	@Column(name = "COLLECTION_DOC_FINANCE_YEAR")
	public BigDecimal getCollectionDocumentFinanceYear() {
		return collectionDocumentFinanceYear;
	}

	public void setCollectionDocumentFinanceYear(BigDecimal collectionDocumentFinanceYear) {
		this.collectionDocumentFinanceYear = collectionDocumentFinanceYear;
	}
	
	@Column(name = "COLLECTION_DOCUMENT_NO")
	public BigDecimal getCollectionDocumentNo() {
		return collectionDocumentNo;
	}

	public void setCollectionDocumentNo(BigDecimal collectionDocumentNo) {
		this.collectionDocumentNo = collectionDocumentNo;
	}
	@Column(name = "REMITTANCE_TRANSACTION_ID")
	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}

	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}

    @Column(name="COLLECTION_DOC_ID")
	public BigDecimal getCollectionDocumentId() {
		return collectionDocumentId;
	}

	public void setCollectionDocumentId(BigDecimal collectionDocumentId) {
		this.collectionDocumentId = collectionDocumentId;
	}

}