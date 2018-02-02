package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VW_EX_REMITTANCE_PURPOSE")
public class PurposeOfRemittanceView implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IDNO")
	private BigDecimal idno;
	
	@Column(name="FIELD_NAME")
	private String flexfieldName;
	/*@Column(name="BANK_DESCRIPTION")
	private String bankDescription;
	*/
	
	@Column(name="FLEX_FIELD_VALUE")
	private String flexiFieldValue;
	
	@Column(name="AMIEC_CODE")
	private String amiecCode;
	
	
/*	@Column(name="AMIEC_DESCRIPTION")
	private String amiecDescription;
	
	@Column(name="BANK_CODE")
	private String bankCode;*/
	
	@Column(name="APPLICATION_COUNTRY_ID")
	private BigDecimal applicationCountryId ;
	
	@Column(name="COMPANY_ID")
	private BigDecimal companyId;
	
	@Column(name="DOCUMENT_FINANCE_YEAR")
	private BigDecimal documentFinancialYear;
	
	@Column(name="DOCUMENT_ID")
	private BigDecimal documentId;
	
	@Column(name="DOCUMENT_NO")
	private BigDecimal documentNumber;
	
	@Column(name="COLLECTION_DOC_ID")
	private BigDecimal collectionDocumentId;
	
	@Column(name="COLLECTION_DOC_FINANCE_YEAR")
	private BigDecimal collectionDocumenFinancialYear;
	
	@Column(name="COLLECTION_DOCUMENT_NO")
	private BigDecimal collectionDocumentNumber;
	
	@Column(name="BANK_ID")
	private BigDecimal bankId;
	
	@Column(name="FLEX_FIELD")
	private String flexField;
	
	

	public BigDecimal getIdno() {
		return idno;
	}

	/*public String getBankDescription() {
		return bankDescription;
	}*/

	public String getFlexiFieldValue() {
		return flexiFieldValue;
	}

	public String getAmiecCode() {
		return amiecCode;
	}

	/*public String getAmiecDescription() {
		return amiecDescription;
	}

	public String getBankCode() {
		return bankCode;
	}*/

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}

	public BigDecimal getCollectionDocumentId() {
		return collectionDocumentId;
	}

	public BigDecimal getCollectionDocumenFinancialYear() {
		return collectionDocumenFinancialYear;
	}

	public BigDecimal getCollectionDocumentNumber() {
		return collectionDocumentNumber;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public String getFlexField() {
		return flexField;
	}

	public void setIdno(BigDecimal idno) {
		this.idno = idno;
	}

	/*public void setBankDescription(String bankDescription) {
		this.bankDescription = bankDescription;
	}*/

	public void setFlexiFieldValue(String flexiFieldValue) {
		this.flexiFieldValue = flexiFieldValue;
	}

	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}

	/*public void setAmiecDescription(String amiecDescription) {
		this.amiecDescription = amiecDescription;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
*/
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}

	public void setCollectionDocumentId(BigDecimal collectionDocumentId) {
		this.collectionDocumentId = collectionDocumentId;
	}

	public void setCollectionDocumenFinancialYear(
			BigDecimal collectionDocumenFinancialYear) {
		this.collectionDocumenFinancialYear = collectionDocumenFinancialYear;
	}

	public void setCollectionDocumentNumber(BigDecimal collectionDocumentNumber) {
		this.collectionDocumentNumber = collectionDocumentNumber;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}
	public String getFlexfieldName() {
		return flexfieldName;
	}

	public void setFlexfieldName(String flexfieldName) {
		this.flexfieldName = flexfieldName;
	}
	
	
	
	
}
