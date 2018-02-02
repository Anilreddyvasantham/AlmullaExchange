package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="VW_EX_APPLICATION_PURPOSE")
public class RemittanceApplicationPurpose implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal idNo;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentId;
	private BigDecimal documentNo;
	private BigDecimal bankId;
	private String flexField;
	private String flexFieldValue;
	private String amiecCode;
	private String amiecDescription;
	private String bankCode;
	private String bankDescription;
	
	
	@Id
	@Column(name="IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	@Column(name="COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	@Column(name="DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	@Column(name="DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	@Column(name="DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	@Column(name="BANK_ID")
	public BigDecimal getBankId() {
		return bankId;
	}
	@Column(name="FLEX_FIELD")
	public String getFlexField() {
		return flexField;
	}
	@Column(name="FLEX_FIELD_VALUE")
	public String getFlexFieldValue() {
		return flexFieldValue;
	}
	@Column(name="AMIEC_CODE")
	public String getAmiecCode() {
		return amiecCode;
	}
	@Column(name="AMIEC_DESCRIPTION")
	public String getAmiecDescription() {
		return amiecDescription;
	}
	@Column(name="BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}
	@Column(name="BANK_DESCRIPTION")
	public String getBankDescription() {
		return bankDescription;
	}
	
	
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}
	public void setFlexFieldValue(String flexFieldValue) {
		this.flexFieldValue = flexFieldValue;
	}
	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}
	public void setAmiecDescription(String amiecDescription) {
		this.amiecDescription = amiecDescription;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public void setBankDescription(String bankDescription) {
		this.bankDescription = bankDescription;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
