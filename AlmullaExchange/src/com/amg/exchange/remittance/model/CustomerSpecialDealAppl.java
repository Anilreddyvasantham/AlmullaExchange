package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_CUSTOMER_SPECIAL_DEAL_APPL")
public class CustomerSpecialDealAppl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal customerSpecialDealApplId;
	private BigDecimal customerSpecialDealReqId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyMasterId;
	private BigDecimal documentId;
	private BigDecimal financialYearId;
	private BigDecimal documentNumber;
	private BigDecimal applicationFinancialYear;
	private BigDecimal applicationDocumentNumber;
	private BigDecimal foriegnCurrencyAmount;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String documentStatus;
	
	@Id
	@GeneratedValue(generator="ex_customer_spl_deal_appl_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_customer_spl_deal_appl_seq",sequenceName="EX_CUSTOMER_SPL_DEAL_APPL_SEQ",allocationSize=1)
	@Column(name ="CUSTOMER_SPECIAL_DEAL_APPL_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getCustomerSpecialDealApplId() {
		return customerSpecialDealApplId;
	}
	public void setCustomerSpecialDealApplId(BigDecimal customerSpecialDealApplId) {
		this.customerSpecialDealApplId = customerSpecialDealApplId;
	}
	
	@Column(name="CUSTOMER_SPECIAL_DEAL_REQ_ID")
	public BigDecimal getCustomerSpecialDealReqId() {
		return customerSpecialDealReqId;
	}
	public void setCustomerSpecialDealReqId(BigDecimal customerSpecialDealReqId) {
		this.customerSpecialDealReqId = customerSpecialDealReqId;
	}
	
	@Column(name="APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name="COMPANY_ID")
	public BigDecimal getCompanyMasterId() {
		return companyMasterId;
	}
	public void setCompanyMasterId(BigDecimal companyMasterId) {
		this.companyMasterId = companyMasterId;
	}
	
	@Column(name="DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	
	@Column(name="FINANCIAL_YEAR_ID")
	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}
	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}
	
	@Column(name="DOCUMENT_NUMBER")
	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	@Column(name="APPLICATION_FINANCE_YEAR")
	public BigDecimal getApplicationFinancialYear() {
		return applicationFinancialYear;
	}
	public void setApplicationFinancialYear(BigDecimal applicationFinancialYear) {
		this.applicationFinancialYear = applicationFinancialYear;
	}
	
	@Column(name="APPLICATION_DOCUMENT_NO")
	public BigDecimal getApplicationDocumentNumber() {
		return applicationDocumentNumber;
	}
	public void setApplicationDocumentNumber(BigDecimal applicationDocumentNumber) {
		this.applicationDocumentNumber = applicationDocumentNumber;
	}
	
	@Column(name="FOREIGN_CURRENCY_AMOUNT")
	public BigDecimal getForiegnCurrencyAmount() {
		return foriegnCurrencyAmount;
	}
	public void setForiegnCurrencyAmount(BigDecimal foriegnCurrencyAmount) {
		this.foriegnCurrencyAmount = foriegnCurrencyAmount;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name="DOCUMENT_STATUS")
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
		
}
