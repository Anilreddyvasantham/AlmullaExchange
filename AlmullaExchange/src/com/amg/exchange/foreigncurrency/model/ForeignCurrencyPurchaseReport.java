package com.amg.exchange.foreigncurrency.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EX_V_FC_PURCHASE_REPORT")
public class ForeignCurrencyPurchaseReport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal slNo;
	private BigDecimal customerId;
	private String firstName;
	private String mobileNo;
	private Date identityExpiryDate;
	private String identityInt;
	private BigDecimal identityTypeId;
	private String purposeDesc;
	private String sourceDesc;
	private String paymentMode;
	private BigDecimal collectAmt;
	private String foreignCurrency;
	private String quoteName;
	private BigDecimal documentNo;
	private BigDecimal docFynYear;
	private String remarks;
	private BigDecimal tranxActualRate;
	private Date collectionDate;
	private BigDecimal paidAmount;
	private BigDecimal netAmount;
	private BigDecimal refundAmount;
	private String signatureSpecimen;
	private BigDecimal foreignTranxAmount;
	private BigDecimal contactNo;
	private BigDecimal exchangeRate;
	private BigDecimal customerReference;
	private String location;
	private String employeeName;
	private String telephoneNo;
	private BigDecimal saleAmount;
	private BigDecimal applicationCountryId;
	private BigDecimal companyID;
	private BigDecimal documentCode;
	private BigDecimal sourceofIncomeID;
	private Clob employeeSignature;
	private Date documentDate;
	private BigDecimal currencyId;
	private Clob signatureSpecimenClob;
	
	
	
	
 
		
	@Id
	@Column(name="SLNO")
	public BigDecimal getSlNo() {
		return slNo;
	}
	public void setSlNo(BigDecimal slNo) {
		this.slNo = slNo;
	}
	
	@Column(name="CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name="FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name="MOBILE")
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	@Column(name="IDENTITY_EXPIRY_DATE")
	public Date getIdentityExpiryDate() {
		return identityExpiryDate;
	}
	public void setIdentityExpiryDate(Date identityExpiryDate) {
		this.identityExpiryDate = identityExpiryDate;
	}
	
	@Column(name="IDENTITY_INT")
	public String getIdentityInt() {
		return identityInt;
	}
	public void setIdentityInt(String identityInt) {
		this.identityInt = identityInt;
	}
	
	@Column(name="IDENTITY_TYPE_ID")
	public BigDecimal getIdentityTypeId() {
		return identityTypeId;
	}
	public void setIdentityTypeId(BigDecimal identityTypeId) {
		this.identityTypeId = identityTypeId;
	}
	
	@Column(name="PURPOSE_FULL_DESC")
	public String getPurposeDesc() {
		return purposeDesc;
	}
	public void setPurposeDesc(String purposeDesc) {
		this.purposeDesc = purposeDesc;
	}
	
	@Column(name="SOURCE_FULL_DESC")
	public String getSourceDesc() {
		return sourceDesc;
	}
	public void setSourceDesc(String sourceDesc) {
		this.sourceDesc = sourceDesc;
	}
	
	@Column(name="FOREIGN_CURRENCY")
	public String getForeignCurrency() {
		return foreignCurrency;
	}
	public void setForeignCurrency(String foreignCurrency) {
		this.foreignCurrency = foreignCurrency;
	}
	
	@Column(name="DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	@Column(name="DOCUMENT_FINANCE_YR")
	public BigDecimal getDocFynYear() {
		return docFynYear;
	}
	public void setDocFynYear(BigDecimal docFynYear) {
		this.docFynYear = docFynYear;
	}
	
	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Column(name="TRANSACTION_ACTUAL_RATE")
	public BigDecimal getTranxActualRate() {
		return tranxActualRate;
	}
	public void setTranxActualRate(BigDecimal tranxActualRate) {
		this.tranxActualRate = tranxActualRate;
	}
	
	@Column(name="COLLECT_DATE")
	public Date getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}	
	
	@Column(name="PAID_AMOUNT")
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	@Column(name="NET_AMOUNT")
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	@Column(name="REFUNDED_AMOUNT")
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	@Column(name="SIGNATURE_SPECIMEN")
	public String getSignatureSpecimen() {
		return signatureSpecimen;
	}
	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}
	@Column(name="FOREIGN_TRNX_AMOUNT")
	public BigDecimal getForeignTranxAmount() {
		return foreignTranxAmount;
	}
	public void setForeignTranxAmount(BigDecimal foreignTranxAmount) {
		this.foreignTranxAmount = foreignTranxAmount;
	}
	@Column(name="CONTACT_NUMBER")
	public BigDecimal getContactNo() {
		return contactNo;
	}
	public void setContactNo(BigDecimal contactNo) {
		this.contactNo = contactNo;
	}	
	@Column(name="AVERAGE_RATE")
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	@Column(name="COLLECT_MODE")
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	@Column(name="COLLAMT")
	public BigDecimal getCollectAmt() {
		return collectAmt;
	}	
	public void setCollectAmt(BigDecimal collectAmt) {
		this.collectAmt = collectAmt;
	}
	
	@Column(name="QUOTE_NAME")
	public String getQuoteName() {
		return quoteName;
	}
	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}
	
	@Column(name="CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}	
	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}
	
	@Column(name="BRANCH_NAME")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name="EMPLOYEE_NAME")
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Column(name="TELEPHONE")
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	
	@Column(name="LOCAL_TRNX_AMOUNT")
	public BigDecimal getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name="COMPANY_ID")
	public BigDecimal getCompanyID() {
		return companyID;
	}
	public void setCompanyID(BigDecimal companyID) {
		this.companyID = companyID;
	}
	
	@Column(name="DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	
	@Column(name="SOURCE_OF_INCOME_ID1")
	public BigDecimal getSourceofIncomeID() {
		return sourceofIncomeID;
	}
	public void setSourceofIncomeID(BigDecimal sourceofIncomeID) {
		this.sourceofIncomeID = sourceofIncomeID;
	}
	@Column(name="EMPLOYEE_SIGNATURE_CLOB")
	public Clob getEmployeeSignature() {
		return employeeSignature;
	}
	public void setEmployeeSignature(Clob employeeSignature) {
		this.employeeSignature = employeeSignature;
	}
	
	@Column(name="DOCUMENT_DATE")
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	@Column(name="CURRENCY_ID")
	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}
	
	@Column(name="SIGNATURE_SPECIMEN_CLOB")
	public Clob getSignatureSpecimenClob() {
		return signatureSpecimenClob;
	}
	public void setSignatureSpecimenClob(Clob signatureSpecimenClob) {
		this.signatureSpecimenClob = signatureSpecimenClob;
	}

}
