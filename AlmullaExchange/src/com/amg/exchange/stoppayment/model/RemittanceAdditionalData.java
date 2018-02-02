package com.amg.exchange.stoppayment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.Document;

@Entity
@Table(name = "EX_REMIT_ADDL_DATA")
public class RemittanceAdditionalData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*ADDITIONAL_BANK_RULE_ID	NUMBER
	AMIEC_CODE	VARCHAR2(60 BYTE)
	APPLICATION_COUNTRY_ID	NUMBER
	COMPANY_ID	NUMBER
	CREATED_BY	VARCHAR2(40 BYTE)
	CREATED_DATE	DATE
	DOCUMENT_FINANCE_YEAR	NUMBER(4,0)
	DOCUMENT_FINANCE_YEAR_ID	NUMBER
	DOCUMENT_ID	NUMBER
	DOCUMENT_NO	NUMBER
	FLEX_FIELD	VARCHAR2(100 BYTE)
	FLEX_FIELD_VALUE	VARCHAR2(100 BYTE)
	ISACTIVE	VARCHAR2(1 BYTE)
	MODIFIED_BY	VARCHAR2(40 BYTE)
	MODIFIED_DATE	DATE
	REMITTANCE_TRANSACTION_ID	NUMBER
	REMITTANCE_TRANX_ADD_DATA_ID	NUMBER*/
	
	private BigDecimal remittanceTransactionAddDataId;
	private BigDecimal remittanceTransactionId;
	private BigDecimal additionalBankRuleId;
	private String amiecCode;
	private CountryMaster applicationCountryId;
	private CompanyMaster companyId;
	private Date createdDate;
	private String createdBy;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentFinanceYearId;
	private Document documentId;
	private BigDecimal documentNo;
	private String flexField;
	private String flexFieldValue;
	private String isactive;
	private Date modifiedDate;
	private String modifiedBy;
	
	public RemittanceAdditionalData() {
		super();
	}

	public RemittanceAdditionalData(BigDecimal remittanceTransactionAddDataId,
			BigDecimal remittanceTransactionId,
			BigDecimal additionalBankRuleId, String amiecCode,
			CountryMaster applicationCountryId, CompanyMaster companyId,
			Date createdDate, String createdBy, BigDecimal documentFinanceYear,
			BigDecimal documentFinanceYearId, Document documentId,
			BigDecimal documentNo, String flexField, String flexFieldValue,
			String isactive, Date modifiedDate, String modifiedBy) {
		super();
		this.remittanceTransactionAddDataId = remittanceTransactionAddDataId;
		this.remittanceTransactionId = remittanceTransactionId;
		this.additionalBankRuleId = additionalBankRuleId;
		this.amiecCode = amiecCode;
		this.applicationCountryId = applicationCountryId;
		this.companyId = companyId;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.documentFinanceYear = documentFinanceYear;
		this.documentFinanceYearId = documentFinanceYearId;
		this.documentId = documentId;
		this.documentNo = documentNo;
		this.flexField = flexField;
		this.flexFieldValue = flexFieldValue;
		this.isactive = isactive;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	@Id
	@GeneratedValue(generator="ex_remit_addl_data_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_remit_addl_data_seq",sequenceName="EX_REMIT_ADDL_DATA_SEQ",allocationSize=1)
	@Column(name = "REMITTANCE_TRANX_ADD_DATA_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getRemittanceTransactionAddDataId() {
		return remittanceTransactionAddDataId;
	}
	public void setRemittanceTransactionAddDataId(BigDecimal remittanceTransactionAddDataId) {
		this.remittanceTransactionAddDataId = remittanceTransactionAddDataId;
	}

	@Column(name = "REMITTANCE_TRANSACTION_ID")
	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}
	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}

	@Column(name = "ADDITIONAL_BANK_RULE_ID")
	public BigDecimal getAdditionalBankRuleId() {
		return additionalBankRuleId;
	}
	public void setAdditionalBankRuleId(BigDecimal additionalBankRuleId) {
		this.additionalBankRuleId = additionalBankRuleId;
	}

	@Column(name = "AMIEC_CODE")
	public String getAmiecCode() {
		return amiecCode;
	}
	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(CountryMaster applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getCompanyId() {
		return companyId;
	}
	public void setCompanyId(CompanyMaster companyId) {
		this.companyId = companyId;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR")
	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}
	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR_ID")
	public BigDecimal getDocumentFinanceYearId() {
		return documentFinanceYearId;
	}
	public void setDocumentFinanceYearId(BigDecimal documentFinanceYearId) {
		this.documentFinanceYearId = documentFinanceYearId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Document documentId) {
		this.documentId = documentId;
	}

	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "FLEX_FIELD")
	public String getFlexField() {
		return flexField;
	}
	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}

	@Column(name = "FLEX_FIELD_VALUE")
	public String getFlexFieldValue() {
		return flexFieldValue;
	}
	public void setFlexFieldValue(String flexFieldValue) {
		this.flexFieldValue = flexFieldValue;
	}

	@Column(name = "ISACTIVE")
	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
