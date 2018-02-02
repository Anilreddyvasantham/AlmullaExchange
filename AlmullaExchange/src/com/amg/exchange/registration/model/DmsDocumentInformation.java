package com.amg.exchange.registration.model;

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
@Table(name = "DMS_DOC_INFO")
public class DmsDocumentInformation implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  private BigDecimal countryCode;
	  private BigDecimal documentBlobId;
	  private BigDecimal seqNo;
	  private String documentName;
	  private String documentType;
	  private String documentCategory;
	  private String documentRemarks;
	  private String documentFormat;
	  private String status;
	  private Date createdDate;
	  private String createdBy;
	  private String createdPG;
	  private String cretaedMG;
	  private Date updatedDate;
	  private String updatedBy;
	  private String updatedPG;
	  private String updatedMG;
	  private BigDecimal documentFinanceYear;

	  public DmsDocumentInformation() {
	  }

	  public DmsDocumentInformation(BigDecimal countryCode, BigDecimal documentBlobId, BigDecimal seqNo, String documentName, String documentType, String documentCategory, String documentRemarks, String documentFormat, String status, Date createdDate, String createdBy, String createdPG, String cretaedMG,
			      Date updatedDate, String updatedBy, String updatedPG, String updatedMG, BigDecimal documentFinanceYear) {
		    super();
		    this.countryCode = countryCode;
		    this.documentBlobId = documentBlobId;
		    this.seqNo = seqNo;
		    this.documentName = documentName;
		    this.documentType = documentType;
		    this.documentCategory = documentCategory;
		    this.documentRemarks = documentRemarks;
		    this.documentFormat = documentFormat;
		    this.status = status;
		    this.createdDate = createdDate;
		    this.createdBy = createdBy;
		    this.createdPG = createdPG;
		    this.cretaedMG = cretaedMG;
		    this.updatedDate = updatedDate;
		    this.updatedBy = updatedBy;
		    this.updatedPG = updatedPG;
		    this.updatedMG = updatedMG;
		    this.documentFinanceYear = documentFinanceYear;
	  }

	  
	  @Column(name = "CNTRYCD")
	  public BigDecimal getCountryCode() {
		    return countryCode;
	  }

	  public void setCountryCode(BigDecimal countryCode) {
		    this.countryCode = countryCode;
	  }

	  @Column(name = "DOC_BLOB_ID")
	  public BigDecimal getDocumentBlobId() {
		    return documentBlobId;
	  }

	  public void setDocumentBlobId(BigDecimal documentBlobId) {
		    this.documentBlobId = documentBlobId;
	  }

	  @Column(name = "SEQ_NO")
	  public BigDecimal getSeqNo() {
		    return seqNo;
	  }

	  public void setSeqNo(BigDecimal seqNo) {
		    this.seqNo = seqNo;
	  }

	  @Column(name = "DOC_NAME")
	  public String getDocumentName() {
		    return documentName;
	  }

	  public void setDocumentName(String documentName) {
		    this.documentName = documentName;
	  }

	  @Column(name = "DOC_TYPE")
	  public String getDocumentType() {
		    return documentType;
	  }

	  public void setDocumentType(String documentType) {
		    this.documentType = documentType;
	  }

	  @Column(name = "DOC_CATEG")
	  public String getDocumentCategory() {
		    return documentCategory;
	  }

	  public void setDocumentCategory(String documentCategory) {
		    this.documentCategory = documentCategory;
	  }

	  @Column(name = "DOC_REMARK")
	  public String getDocumentRemarks() {
		    return documentRemarks;
	  }

	  public void setDocumentRemarks(String documentRemarks) {
		    this.documentRemarks = documentRemarks;
	  }

	  @Column(name = "DOC_FORMAT")
	  public String getDocumentFormat() {
		    return documentFormat;
	  }

	  public void setDocumentFormat(String documentFormat) {
		    this.documentFormat = documentFormat;
	  }

	  @Column(name = "STATUS")
	  public String getStatus() {
		    return status;
	  }

	  public void setStatus(String status) {
		    this.status = status;
	  }

	  @Column(name = "CRE_DT")
	  public Date getCreatedDate() {
		    return createdDate;
	  }

	  public void setCreatedDate(Date createdDate) {
		    this.createdDate = createdDate;
	  }

	  @Column(name = "CRE_BY")
	  public String getCreatedBy() {
		    return createdBy;
	  }

	  public void setCreatedBy(String createdBy) {
		    this.createdBy = createdBy;
	  }

	  @Column(name = "CRE_PG")
	  public String getCreatedPG() {
		    return createdPG;
	  }

	  public void setCreatedPG(String createdPG) {
		    this.createdPG = createdPG;
	  }

	  @Column(name = "CRE_MC")
	  public String getCretaedMG() {
		    return cretaedMG;
	  }

	  public void setCretaedMG(String cretaedMG) {
		    this.cretaedMG = cretaedMG;
	  }

	  @Column(name = "UPD_DT")
	  public Date getUpdatedDate() {
		    return updatedDate;
	  }

	  public void setUpdatedDate(Date updatedDate) {
		    this.updatedDate = updatedDate;
	  }

	  @Column(name = "UPD_BY")
	  public String getUpdatedBy() {
		    return updatedBy;
	  }

	  public void setUpdatedBy(String updatedBy) {
		    this.updatedBy = updatedBy;
	  }

	  @Column(name = "UPD_PG")
	  public String getUpdatedPG() {
		    return updatedPG;
	  }

	  public void setUpdatedPG(String updatedPG) {
		    this.updatedPG = updatedPG;
	  }

	  @Column(name = "UPD_MC")
	  public String getUpdatedMG() {
		    return updatedMG;
	  }

	  public void setUpdatedMG(String updatedMG) {
		    this.updatedMG = updatedMG;
	  }

	  @Column(name = "DOC_FIN_YR")
	  public BigDecimal getDocumentFinanceYear() {
		    return documentFinanceYear;
	  }

	  public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		    this.documentFinanceYear = documentFinanceYear;
	  }

}
