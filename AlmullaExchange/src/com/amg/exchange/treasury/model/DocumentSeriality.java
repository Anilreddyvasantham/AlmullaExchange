package com.amg.exchange.treasury.model;

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
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;

@Entity
@Table(name="FS_DOCUMENT_SERIALITY")
public class DocumentSeriality implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal documentSerialityId;
	private CountryMaster docCountry;
	private CompanyMaster docCompany;
	private BigDecimal exDocument;
	private BigDecimal docFinancialYear;
	private BigDecimal startNo;
	private BigDecimal endNo;
	private BigDecimal nextNo;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;	
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	
	public DocumentSeriality(BigDecimal documentSerialityId, CountryMaster docCountry, CompanyMaster docCompany, BigDecimal exDocument, BigDecimal docFinancialYear, BigDecimal startNo, BigDecimal endNo, BigDecimal nextNo, String isActive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate, String remarks) {
		this.documentSerialityId = documentSerialityId;
		this.docCountry = docCountry;
		this.docCompany = docCompany;
		this.exDocument = exDocument;
		this.docFinancialYear = docFinancialYear;
		this.startNo = startNo;
		this.endNo = endNo;
		this.nextNo = nextNo;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
	}
	
	
	
	public DocumentSeriality() {
	}



	@Id
	@GeneratedValue(generator="ex_document_seriality_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_document_seriality_seq",sequenceName="FS_DOCUMENT_SERIALITY_SEQ",allocationSize=1)
	@Column(name = "DOCUMENT_SERIALITY_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDocumentSerialityId() {
		return documentSerialityId;
	}
	public void setDocumentSerialityId(BigDecimal documentSerialityId) {
		this.documentSerialityId = documentSerialityId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getDocCountry() {
		return docCountry;
	}
	public void setDocCountry(CountryMaster docCountry) {
		this.docCountry = docCountry;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getDocCompany() {
		return docCompany;
	}
	public void setDocCompany(CompanyMaster docCompany) {
		this.docCompany = docCompany;
	}
	//@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getExDocument() {
		return exDocument;
	}
	public void setExDocument(BigDecimal exDocument) {
		this.exDocument = exDocument;
	}
	//@ManyToOne(fetch = FetchType.LAZY)
	@Column(name ="DOCUMENT_FINANCE_YR")
	public BigDecimal getDocFinancialYear() {
		return docFinancialYear;
	}
	public void setDocFinancialYear(BigDecimal docFinancialYear) {
		this.docFinancialYear = docFinancialYear;
	}
	@Column(name="START_NO")
	public BigDecimal getStartNo() {
		return startNo;
	}
	public void setStartNo(BigDecimal startNo) {
		this.startNo = startNo;
	}
	@Column(name="END_NO")
	public BigDecimal getEndNo() {
		return endNo;
	}
	public void setEndNo(BigDecimal endNo) {
		this.endNo = endNo;
	}
	@Column(name="NEXT_NO")
	public BigDecimal getNextNo() {
		return nextNo;
	}
	public void setNextNo(BigDecimal nextNo) {
		this.nextNo = nextNo;
	}
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	@Column(name="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	
	

}
