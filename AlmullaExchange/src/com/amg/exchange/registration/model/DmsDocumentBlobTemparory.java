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

import java.sql.Blob;

@Entity
@Table(name = "DMS_DOC_BLOB_TEMP_JAVA")
public class DmsDocumentBlobTemparory implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	
	
	
	private BigDecimal tempSeqNo;
	private BigDecimal countryCode;
	private BigDecimal docFinYear;
	private BigDecimal docBlobId;
	private BigDecimal seqNo;
	private Blob documentContent;
	private String createdBy;
	private Date createdDate;
	
	
	public DmsDocumentBlobTemparory()
	{

	}
	
	public DmsDocumentBlobTemparory(BigDecimal tempSeqNo,
			BigDecimal countryCode, BigDecimal docFinYear,
			BigDecimal docBlobId, BigDecimal seqNo, Blob documentContent,
			String createdBy, Date createdDate) {
		super();
		this.tempSeqNo = tempSeqNo;
		this.countryCode = countryCode;
		this.docFinYear = docFinYear;
		this.docBlobId = docBlobId;
		this.seqNo = seqNo;
		this.documentContent = documentContent;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	@Id
	@GeneratedValue(generator="ex_dms_doc_blob_temp_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_dms_doc_blob_temp_seq",sequenceName="EX_DMS_DOC_BLOB_TEMP_SEQ",allocationSize=1)
	@Column(name = "TEMP_SEQNO", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getTempSeqNo() {
		return tempSeqNo;
	}


	public void setTempSeqNo(BigDecimal tempSeqNo) {
		this.tempSeqNo = tempSeqNo;
	}

	@Column(name = "CNTRYCD")
	public BigDecimal getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(BigDecimal countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name = "DOC_FIN_YR")
	public BigDecimal getDocFinYear() {
		return docFinYear;
	}


	public void setDocFinYear(BigDecimal docFinYear) {
		this.docFinYear = docFinYear;
	}

	@Column(name = "DOC_BLOB_ID")
	public BigDecimal getDocBlobId() {
		return docBlobId;
	}


	public void setDocBlobId(BigDecimal docBlobId) {
		this.docBlobId = docBlobId;
	}

	@Column(name = "SEQ_NO")
	public BigDecimal getSeqNo() {
		return seqNo;
	}


	public void setSeqNo(BigDecimal seqNo) {
		this.seqNo = seqNo;
	}

	@Column(name = "DOC_CONTENT")
	public Blob getDocumentContent() {
		return documentContent;
	}


	public void setDocumentContent(Blob documentContent) {
		this.documentContent = documentContent;
	}

	@Column(name = "CRE_BY")
	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CRE_DT")
	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
	
}
