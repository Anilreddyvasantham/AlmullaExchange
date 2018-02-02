package com.amg.exchange.cancelreissue.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "VW_EX_REMIT_DOC")
public class ViewRemittanceDocument {
	
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private BigDecimal customerId;
	private BigDecimal documanetCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private String isActive;
	private BigDecimal remittanceTransactionId;
	
	
	
	
	//Getters and Setters.
	
	@Id
	@Column(name = "REMITTANCE_TRANSACTION_ID")
	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}
	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumanetCode() {
		return documanetCode;
	}
	public void setDocumanetCode(BigDecimal documanetCode) {
		this.documanetCode = documanetCode;
	}
	
	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}
	
	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}	
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
}
