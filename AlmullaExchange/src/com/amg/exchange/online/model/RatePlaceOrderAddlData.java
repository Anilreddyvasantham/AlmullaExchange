package com.amg.exchange.online.model;

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

@Entity
@Table(name = "EX_RATE_PO_ADDL_DATA")
public class RatePlaceOrderAddlData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal ratePoAddlId;
	private RatePlaceOrder ratePlaceOrder;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private BigDecimal documentCode;
	private BigDecimal documentId;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private BigDecimal additionalBankRuleId;
	private String flexFiled;
	private String flexFiledValue;
	private String amiecCode;
	private String createdBy;
	private Date createdDate;
	
	
	public RatePlaceOrderAddlData()
	{
		
	}
	

	public RatePlaceOrderAddlData(BigDecimal ratePoAddlId,
			RatePlaceOrder ratePlaceOrder, BigDecimal applicationCountryId,
			BigDecimal companyId, BigDecimal documentCode,
			BigDecimal documentId, BigDecimal documentFinanceYear,
			BigDecimal documentNo, BigDecimal additionalBankRuleId,
			String flexFiled, String flexFiledValue, String amiecCode,
			String createdBy, Date createdDate) {
		super();
		this.ratePoAddlId = ratePoAddlId;
		this.ratePlaceOrder = ratePlaceOrder;
		this.applicationCountryId = applicationCountryId;
		this.companyId = companyId;
		this.documentCode = documentCode;
		this.documentId = documentId;
		this.documentFinanceYear = documentFinanceYear;
		this.documentNo = documentNo;
		this.additionalBankRuleId = additionalBankRuleId;
		this.flexFiled = flexFiled;
		this.flexFiledValue = flexFiledValue;
		this.amiecCode = amiecCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}




	@Id
	@GeneratedValue(generator = "ex_rate_po_addl_data_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_rate_po_addl_data_seq", sequenceName = "EX_RATE_PO_ADDL_DATA_SEQ", allocationSize = 1)
	@Column(name = "RATE_PO_ADDL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getRatePoAddlId() {
		return ratePoAddlId;
	}


	public void setRatePoAddlId(BigDecimal ratePoAddlId) {
		this.ratePoAddlId = ratePoAddlId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RATE_PLACE_ORDER_ID", nullable = false)
	public RatePlaceOrder getRatePlaceOrder() {
		return ratePlaceOrder;
	}


	public void setRatePlaceOrder(RatePlaceOrder ratePlaceOrder) {
		this.ratePlaceOrder = ratePlaceOrder;
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

	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocumentCode() {
		return documentCode;
	}


	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_ID")
	public BigDecimal getDocumentId() {
		return documentId;
	}


	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
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

	@Column(name = "ADDITIONAL_BANK_RULE_ID")
	public BigDecimal getAdditionalBankRuleId() {
		return additionalBankRuleId;
	}


	public void setAdditionalBankRuleId(BigDecimal additionalBankRuleId) {
		this.additionalBankRuleId = additionalBankRuleId;
	}

	@Column(name = "FLEX_FIELD")
	public String getFlexFiled() {
		return flexFiled;
	}


	public void setFlexFiled(String flexFiled) {
		this.flexFiled = flexFiled;
	}

	@Column(name = "FLEX_FIELD_VALUE")
	public String getFlexFiledValue() {
		return flexFiledValue;
	}


	public void setFlexFiledValue(String flexFiledValue) {
		this.flexFiledValue = flexFiledValue;
	}

	@Column(name = "AMIEC_CODE")
	public String getAmiecCode() {
		return amiecCode;
	}


	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
