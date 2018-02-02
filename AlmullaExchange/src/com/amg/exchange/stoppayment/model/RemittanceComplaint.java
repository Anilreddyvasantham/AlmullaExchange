package com.amg.exchange.stoppayment.model;

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

/**
* Nazish Ehsan Hashmi
*/
@Entity
@Table(name = "EX_REMIT_COMPLAINT")
public class RemittanceComplaint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal remittanceComplaintId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private BigDecimal companyCode;
	private BigDecimal documentFinanceYear;
	private BigDecimal documentId;
	private BigDecimal documentCode;
	private BigDecimal documentNo;
	private Date stopDocumentDate;
	private BigDecimal stopDocumentCode;
	private BigDecimal stopDocumentFinanceYear;
	private BigDecimal stopDocumentNumber;
	private Date cancelDocumentDate;
	private BigDecimal cancelDocumentCode;
	private BigDecimal cancelDocumentFinanceYear;
	private BigDecimal cancelDocumentNumber;
	private String problemStatus;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String cancellationStatus;
	private String bankStatus;
	private String bankStatusUser;
	private Date bankStatusDate;
	private BigDecimal remittanceTransactionId;
	
	public RemittanceComplaint() {
		super();
	}

	public RemittanceComplaint(BigDecimal remittanceComplaintId,
			BigDecimal applicationCountryId, BigDecimal companyId,
			BigDecimal companyCode, BigDecimal documentFinanceYear,
			BigDecimal documentId, BigDecimal documentCode,
			BigDecimal documentNo, Date stopDocumentDate,
			BigDecimal stopDocumentCode, BigDecimal stopDocumentFinanceYear,
			BigDecimal stopDocumentNumber, Date cancelDocumentDate,
			BigDecimal cancelDocumentCode,
			BigDecimal cancelDocumentFinanceYear,
			BigDecimal cancelDocumentNumber, String problemStatus,
			String isactive, String createdBy, Date createdDate,
			String modifiedBy, Date modifiedDate, String cancellationStatus,
			String bankStatus, String bankStatusUser, Date bankStatusDate,
			BigDecimal remittanceTransactionId) {
		super();
		this.remittanceComplaintId = remittanceComplaintId;
		this.applicationCountryId = applicationCountryId;
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.documentFinanceYear = documentFinanceYear;
		this.documentId = documentId;
		this.documentCode = documentCode;
		this.documentNo = documentNo;
		this.stopDocumentDate = stopDocumentDate;
		this.stopDocumentCode = stopDocumentCode;
		this.stopDocumentFinanceYear = stopDocumentFinanceYear;
		this.stopDocumentNumber = stopDocumentNumber;
		this.cancelDocumentDate = cancelDocumentDate;
		this.cancelDocumentCode = cancelDocumentCode;
		this.cancelDocumentFinanceYear = cancelDocumentFinanceYear;
		this.cancelDocumentNumber = cancelDocumentNumber;
		this.problemStatus = problemStatus;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.cancellationStatus = cancellationStatus;
		this.bankStatus = bankStatus;
		this.bankStatusUser = bankStatusUser;
		this.bankStatusDate = bankStatusDate;
		this.remittanceTransactionId = remittanceTransactionId;
	}


	@Id
	@GeneratedValue(generator="ex_remittance_complaint_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_remittance_complaint_seq",sequenceName="EX_REMITTANCE_COMPLAINT_SEQ",allocationSize=1)
	@Column(name = "REMITTANCE_COMPLAINT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getRemittanceComplaintId() {
		return this.remittanceComplaintId;
	}

	public void setRemittanceComplaintId(BigDecimal remittanceComplaintId) {
		this.remittanceComplaintId = remittanceComplaintId;
	}

	@Column(name = "APPLICATION_COUNTRY_ID", precision = 22, scale = 0)
	public BigDecimal getApplicationCountryId() {
		return this.applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "COMPANY_ID", precision = 22, scale = 0)
	public BigDecimal getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	@Column(name = "COMPANY_CODE", precision = 22, scale = 0)
	public BigDecimal getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "DOCUMENT_FINANCE_YEAR_ID", length = 50)
	public BigDecimal getDocumentFinanceYear() {
		return this.documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	@Column(name = "DOCUMENT_ID", precision = 22, scale = 0)
	public BigDecimal getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	@Column(name = "DOCUMENT_CODE", precision = 22, scale = 0)
	public BigDecimal getDocumentCode() {
		return this.documentCode;
	}

	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_NO", precision = 22, scale = 0)
	public BigDecimal getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	
	@Column(name = "STOP_DOCUMENT_DATE", length = 7)
	public Date getStopDocumentDate() {
		return this.stopDocumentDate;
	}

	public void setStopDocumentDate(Date stopDocumentDate) {
		this.stopDocumentDate = stopDocumentDate;
	}

	@Column(name = "STOP_DOCUMENT_CODE", precision = 22, scale = 0)
	public BigDecimal getStopDocumentCode() {
		return this.stopDocumentCode;
	}

	public void setStopDocumentCode(BigDecimal stopDocumentCode) {
		this.stopDocumentCode = stopDocumentCode;
	}

	@Column(name = "STOP_DOCUMENT_FINANCE_YEAR", length = 50)
	public BigDecimal getStopDocumentFinanceYear() {
		return this.stopDocumentFinanceYear;
	}

	public void setStopDocumentFinanceYear(BigDecimal stopDocumentFinanceYear) {
		this.stopDocumentFinanceYear = stopDocumentFinanceYear;
	}

	@Column(name = "STOP_DOCUMENT_NO", precision = 22, scale = 0)
	public BigDecimal getStopDocumentNumber() {
		return this.stopDocumentNumber;
	}

	public void setStopDocumentNumber(BigDecimal stopDocumentNumber) {
		this.stopDocumentNumber = stopDocumentNumber;
	}

	
	@Column(name = "CANCEL_DOCUMENT_DATE", length = 7)
	public Date getCancelDocumentDate() {
		return this.cancelDocumentDate;
	}

	public void setCancelDocumentDate(Date cancelDocumentDate) {
		this.cancelDocumentDate = cancelDocumentDate;
	}

	@Column(name = "CANCEL_DOCUMENT_CODE", precision = 22, scale = 0)
	public BigDecimal getCancelDocumentCode() {
		return this.cancelDocumentCode;
	}

	public void setCancelDocumentCode(BigDecimal cancelDocumentCode) {
		this.cancelDocumentCode = cancelDocumentCode;
	}

	@Column(name = "CANCEL_DOCUMENT_FINANCE_YEAR", length = 50)
	public BigDecimal getCancelDocumentFinanceYear() {
		return this.cancelDocumentFinanceYear;
	}

	public void setCancelDocumentFinanceYear(BigDecimal cancelDocumentFinanceYear) {
		this.cancelDocumentFinanceYear = cancelDocumentFinanceYear;
	}

	@Column(name = "CANCEL_DOCUMENT_NO", precision = 22, scale = 0)
	public BigDecimal getCancelDocumentNumber() {
		return this.cancelDocumentNumber;
	}

	public void setCancelDocumentNumber(BigDecimal cancelDocumentNumber) {
		this.cancelDocumentNumber = cancelDocumentNumber;
	}

	@Column(name = "PROBLEM_STATUS", length = 50)
	public String getProblemStatus() {
		return this.problemStatus;
	}

	public void setProblemStatus(String problemStatus) {
		this.problemStatus = problemStatus;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "CREATED_BY", length = 80)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	@Column(name = "CREATED_DATE", length = 7)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 80)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name = "MODIFIED_DATE", length = 7)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name = "CANCELLATION_STATUS", length = 1)
	public String getCancellationStatus() {
		return cancellationStatus;
	}

	public void setCancellationStatus(String cancellationStatus) {
		this.cancellationStatus = cancellationStatus;
	}

	@Column(name = "BANK_STATUS")
	public String getBankStatus() {
		return bankStatus;
	}

	public void setBankStatus(String bankStatus) {
		this.bankStatus = bankStatus;
	}
	
	@Column(name = "BANK_STATUS_USER")
	public String getBankStatusUser() {
		return bankStatusUser;
	}
	
	public void setBankStatusUser(String bankStatusUser) {
		this.bankStatusUser = bankStatusUser;
	}
	
	@Column(name = "BANK_STATUS_DATE")
	public Date getBankStatusDate() {
		return bankStatusDate;
	}
	
	public void setBankStatusDate(Date bankStatusDate) {
		this.bankStatusDate = bankStatusDate;
	}

	@Column(name = "REMITTANCE_TRANSACTION_ID")
	public BigDecimal getRemittanceTransactionId() {
		return remittanceTransactionId;
	}

	public void setRemittanceTransactionId(BigDecimal remittanceTransactionId) {
		this.remittanceTransactionId = remittanceTransactionId;
	}
	
}
