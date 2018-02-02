package com.amg.exchange.promotion.model;

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
import com.amg.exchange.treasury.model.Document;

@Entity
@Table(name = "EX_PROMO_HD")
public class PromotionMaster implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal promotionMasterId;
	private CountryMaster applicationCountry;
	private CompanyMaster companyMaster;
	private Document documentMaster;
	private BigDecimal documentFinancialYear;
	private BigDecimal documentNo;
	private Date fromDate;
	private Date toDate;
	private String promotionDescription;
	private String isActive;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private String approvedBy;
	private Date approvedDate;
	
	public PromotionMaster()
	{

	}
	
	public PromotionMaster(BigDecimal promotionMasterId,
			CountryMaster applicationCountry, CompanyMaster companyMaster,
			Document documentMaster, BigDecimal documentFinancialYear,
			BigDecimal documentNo, Date fromDate, Date toDate,
			String promotionDescription, String isActive, Date createdDate,
			String createdBy, Date modifiedDate, String modifiedBy,
			String approvedBy, Date approvedDate) {
		super();
		this.promotionMasterId = promotionMasterId;
		this.applicationCountry = applicationCountry;
		this.companyMaster = companyMaster;
		this.documentMaster = documentMaster;
		this.documentFinancialYear = documentFinancialYear;
		this.documentNo = documentNo;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.promotionDescription = promotionDescription;
		this.isActive = isActive;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}

	@Id
	@GeneratedValue(generator="ex_promo_hd_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_promo_hd_seq",sequenceName="EX_PROMO_HD_SEQ",allocationSize=1)
	@Column(name = "EX_PROMO_HD_SEQ", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getPromotionMasterId() {
		return promotionMasterId;
	}

	public void setPromotionMasterId(BigDecimal promotionMasterId) {
		this.promotionMasterId = promotionMasterId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getApplicationCountry() {
		return applicationCountry;
	}

	public void setApplicationCountry(CountryMaster applicationCountry) {
		this.applicationCountry = applicationCountry;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getCompanyMaster() {
		return companyMaster;
	}

	public void setCompanyMaster(CompanyMaster companyMaster) {
		this.companyMaster = companyMaster;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCUMENT_ID")
	public Document getDocumentMaster() {
		return documentMaster;
	}

	public void setDocumentMaster(Document documentMaster) {
		this.documentMaster = documentMaster;
	}
	@Column(name = "DOCUMENT_FINANCIAL_YEAR")
	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}

	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}
	@Column(name = "DOCUMENT_NO")
	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	@Column(name = "FROM_DATE")
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	@Column(name = "TO_DATE")
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	@Column(name = "PROMOTION_DESCRIPTION")
	public String getPromotionDescription() {
		return promotionDescription;
	}

	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}
	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	

}
