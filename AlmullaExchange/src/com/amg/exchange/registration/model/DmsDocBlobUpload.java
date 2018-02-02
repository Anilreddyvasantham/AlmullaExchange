package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Author : Nazish Ehsan Hashmi
 */
@Entity
@Table(name = "DMS_DOC_BLOB_UPLOAD_JAVA" )
public class DmsDocBlobUpload implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal countryCode;
	private BigDecimal documentBlob_Id;
	private BigDecimal sequenceNo;
	private Blob docContent;
	private Date createdDate;
	private String createdBy;
	private String createdProgram;
	private String createdMachine;
	private Date updatedDate;
	private String updatedBy;
	private String updatedProgram;
	private String updatedMachine;
	private String compressedInd;
	private BigDecimal docFinanceYear;
	
  
   @Column(name = "CNTRYCD")
   public BigDecimal getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(BigDecimal countryCode) {
		this.countryCode = countryCode;
	}
	 @Id	
	@Column(name = "DOC_BLOB_ID")
	public BigDecimal getDocumentBlob_Id() {
		return documentBlob_Id;
	}
	public void setDocumentBlob_Id(BigDecimal documentBlob_Id) {
		this.documentBlob_Id = documentBlob_Id;
	}
	@Column(name = "SEQ_NO")
	public BigDecimal getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(BigDecimal sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	@Column(name = "DOC_CONTENT")
	public Blob getDocContent() {
		return docContent;
	}
	public void setDocContent(Blob docContent) {
		this.docContent = docContent;
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
	public String getCreatedProgram() {
		return createdProgram;
	}
	public void setCreatedProgram(String createdProgram) {
		this.createdProgram = createdProgram;
	}
	@Column(name = "CRE_MC")
	public String getCreatedMachine() {
		return createdMachine;
	}
	public void setCreatedMachine(String createdMachine) {
		this.createdMachine = createdMachine;
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
	public String getUpdatedProgram() {
		return updatedProgram;
	}
	public void setUpdatedProgram(String updatedProgram) {
		this.updatedProgram = updatedProgram;
	}
	@Column(name = "UPD_MC")
	public String getUpdatedMachine() {
		return updatedMachine;
	}
	public void setUpdatedMachine(String updatedMachine) {
		this.updatedMachine = updatedMachine;
	}
	@Column(name = "DOC_FIN_YR")
	public BigDecimal getDocFinanceYear() {
		return docFinanceYear;
	}
	public void setDocFinanceYear(BigDecimal docFinanceYear) {
		this.docFinanceYear = docFinanceYear;
	}
	
	
	@Column(name = "COMPRESS_IND")
	public String getCompressedInd() {
		return compressedInd;
	}
	public void setCompressedInd(String compressedInd) {
		this.compressedInd = compressedInd;
	}
	
}
