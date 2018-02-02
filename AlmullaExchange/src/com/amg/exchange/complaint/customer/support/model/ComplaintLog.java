package com.amg.exchange.complaint.customer.support.model;

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
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.complaint.model.ComplaintTypeMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.ServiceMaster;

@Entity
@Table(name = "EX_COMPLAINT_LOG")
public class ComplaintLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal complaintLogId;
	private CountryMaster applicationCountry;
	private CompanyMaster companyMaster;
	private CompanyMaster remittanceCompany;
	private BigDecimal document;
	private Customer customer;
	private ComplaintAssigned tokenById;
	private BigDecimal customerReference;
	private CountryMaster country;
	private ServiceMaster service;
	private BankMaster bank;
	private CountryBranch countryBranch;
	private ComplaintAssigned assignedToId;
	private ComplaintAssigned complaintFromId;
	private ComplaintTypeMaster complaintType;
	private ComplaintRemarksMaster complaintRemarks;
	private String countryBranchCode;
	private String tokenByCode;
	private String assignedToCode;
	private String complaintFromCode;
	private String complaintTypeCode;
	private BigDecimal complaintFinancialYear;
	private BigDecimal complaintReference;
	private Date logDate;
	private Date assignedDate;
	private String complaintRemarksCode;
	private Date closedDate;
	private BigDecimal smsSequence;
	private String remittanceCompanyCode;
	private Document remittanceDocument;
	private String remittanceDocumentCode;
	private BigDecimal remittanceDocumentFinancialYear;
	private BigDecimal remittanceDocumentNo;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	//private BigDecimal assingedTo;
	private BigDecimal documentNumber;
	private Date remittanceDate;
	private BigDecimal foriegnCurrencyAmount;
	private String foriegnCurrencyName;

	public ComplaintLog() {
		// TODO Auto-generated constructor stub
	}

	public ComplaintLog(BigDecimal complaintLogId, CountryMaster applicationCountry, CompanyMaster companyMaster, CompanyMaster remittanceCompany, BigDecimal document, Customer customer, ComplaintAssigned tokenById, BigDecimal customerReference, CountryMaster country, ServiceMaster service,
			BankMaster bank, CountryBranch countryBranch, ComplaintAssigned assignedToId, ComplaintAssigned complaintFromId, ComplaintTypeMaster complaintType, ComplaintRemarksMaster complaintRemarks, String countryBranchCode, String tokenByCode, String assignedToCode, String complaintFromCode,
			String complaintTypeCode, BigDecimal complaintFinancialYear, BigDecimal complaintReference, Date logDate, Date assignedDate, String complaintRemarksCode, Date closedDate, BigDecimal smsSequence, String remittanceCompanyCode, Document remittanceDocument, String remittanceDocumentCode,
			BigDecimal remittanceDocumentFinancialYear, BigDecimal remittanceDocumentNo, String isActive, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate, String remarks, BigDecimal documentNumber
			,Date remittanceDate,BigDecimal foriegnCurrencyAmount,String foriegnCurrencyName) {
		this.complaintLogId = complaintLogId;
		this.applicationCountry = applicationCountry;
		this.companyMaster = companyMaster;
		this.remittanceCompany = remittanceCompany;
		this.document = document;
		this.customer = customer;
		this.tokenById = tokenById;
		this.customerReference = customerReference;
		this.country = country;
		this.service = service;
		this.bank = bank;
		this.countryBranch = countryBranch;
		this.assignedToId = assignedToId;
		this.complaintFromId = complaintFromId;
		this.complaintType = complaintType;
		this.complaintRemarks = complaintRemarks;
		this.countryBranchCode = countryBranchCode;
		this.tokenByCode = tokenByCode;
		this.assignedToCode = assignedToCode;
		this.complaintFromCode = complaintFromCode;
		this.complaintTypeCode = complaintTypeCode;
		this.complaintFinancialYear = complaintFinancialYear;
		this.complaintReference = complaintReference;
		this.logDate = logDate;
		this.assignedDate = assignedDate;
		this.complaintRemarksCode = complaintRemarksCode;
		this.closedDate = closedDate;
		this.smsSequence = smsSequence;
		this.remittanceCompanyCode = remittanceCompanyCode;
		this.remittanceDocument = remittanceDocument;
		this.remittanceDocumentCode = remittanceDocumentCode;
		this.remittanceDocumentFinancialYear = remittanceDocumentFinancialYear;
		this.remittanceDocumentNo = remittanceDocumentNo;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
		//this.assingedTo= assingedTo;
		this.documentNumber= documentNumber;
		this.remittanceDate=remittanceDate;
		this.foriegnCurrencyAmount=foriegnCurrencyAmount;
		this.foriegnCurrencyName=foriegnCurrencyName;
	}


	@Id
	@GeneratedValue(generator = "ex_complaint_log_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_complaint_log_seq", sequenceName = "EX_COMPLAINT_LOG_SEQ", allocationSize = 1)
	@Column(name = "COMPLAINT_LOG_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComplaintLogId() {
		return complaintLogId;
	}

	public void setComplaintLogId(BigDecimal complaintLogId) {
		this.complaintLogId = complaintLogId;
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
	@JoinColumn(name = "REMITTANCE_COMPANY_ID")
	public CompanyMaster getRemittanceCompany() {
		return remittanceCompany;
	}

	public void setRemittanceCompany(CompanyMaster remittanceCompany) {
		this.remittanceCompany = remittanceCompany;
	}

	
	@Column(name = "DOCUMENT_CODE")
	public BigDecimal getDocument() {
		return document;
	}

	public void setDocument(BigDecimal document) {
		this.document = document;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAKEN_BY_ID")
	public ComplaintAssigned getTokenById() {
		return tokenById;
	}

	public void setTokenById(ComplaintAssigned tokenById) {
		this.tokenById = tokenById;
	}

	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getCountry() {
		return country;
	}

	public void setCountry(CountryMaster country) {
		this.country = country;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_ID")
	public ServiceMaster getService() {
		return service;
	}

	public void setService(ServiceMaster service) {
		this.service = service;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBank() {
		return bank;
	}

	public void setBank(BankMaster bank) {
		this.bank = bank;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOCATION_ID")
	public CountryBranch getCountryBranch() {
		return countryBranch;
	}

	public void setCountryBranch(CountryBranch countryBranch) {
		this.countryBranch = countryBranch;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSIGN_TO_ID")
	public ComplaintAssigned getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(ComplaintAssigned assignedToId) {
		this.assignedToId = assignedToId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_FROM_ID")
	public ComplaintAssigned getComplaintFromId() {
		return complaintFromId;
	}

	public void setComplaintFromId(ComplaintAssigned complaintFromId) {
		this.complaintFromId = complaintFromId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_TYPE_ID")
	public ComplaintTypeMaster getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(ComplaintTypeMaster complaintType) {
		this.complaintType = complaintType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REMARKS_ID")
	public ComplaintRemarksMaster getComplaintRemarks() {
		return complaintRemarks;
	}

	public void setComplaintRemarks(ComplaintRemarksMaster complaintRemarks) {
		this.complaintRemarks = complaintRemarks;
	}

	@Column(name = "LOCATION_CODE")
	public String getCountryBranchCode() {
		return countryBranchCode;
	}

	public void setCountryBranchCode(String countryBranchCode) {
		this.countryBranchCode = countryBranchCode;
	}

	@Column(name = "TAKEN_BY_CODE")
	public String getTokenByCode() {
		return tokenByCode;
	}

	public void setTokenByCode(String tokenByCode) {
		this.tokenByCode = tokenByCode;
	}

	@Column(name = "ASSIGN_TO_CODE")
	public String getAssignedToCode() {
		return assignedToCode;
	}

	public void setAssignedToCode(String assignedToCode) {
		this.assignedToCode = assignedToCode;
	}

	@Column(name = "COMPLAINT_FROM_CODE")
	public String getComplaintFromCode() {
		return complaintFromCode;
	}

	public void setComplaintFromCode(String complaintFromCode) {
		this.complaintFromCode = complaintFromCode;
	}

	@Column(name = "COMPLAINT_TYPE_CODE")
	public String getComplaintTypeCode() {
		return complaintTypeCode;
	}

	public void setComplaintTypeCode(String complaintTypeCode) {
		this.complaintTypeCode = complaintTypeCode;
	}

	@Column(name = "COMPLAINT_FINANCE_YEAR")
	public BigDecimal getComplaintFinancialYear() {
		return complaintFinancialYear;
	}

	public void setComplaintFinancialYear(BigDecimal complaintFinancialYear) {
		this.complaintFinancialYear = complaintFinancialYear;
	}

	@Column(name = "COMPLAINT_REFERENCE")
	public BigDecimal getComplaintReference() {
		return complaintReference;
	}

	public void setComplaintReference(BigDecimal complaintReference) {
		this.complaintReference = complaintReference;
	}

	@Column(name = "LOG_DATE")
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	@Column(name = "ASSIGN_DATE")
	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	@Column(name = "REMARKS_CODE")
	public String getComplaintRemarksCode() {
		return complaintRemarksCode;
	}

	public void setComplaintRemarksCode(String complaintRemarksCode) {
		this.complaintRemarksCode = complaintRemarksCode;
	}

	@Column(name = "CLOSED_DATE")
	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	@Column(name = "SMS_SEQUENCE")
	public BigDecimal getSmsSequence() {
		return smsSequence;
	}

	public void setSmsSequence(BigDecimal smsSequence) {
		this.smsSequence = smsSequence;
	}

	@Column(name = "REMITTANCE_COMPANY_CODE")
	public String getRemittanceCompanyCode() {
		return remittanceCompanyCode;
	}

	public void setRemittanceCompanyCode(String remittanceCompanyCode) {
		this.remittanceCompanyCode = remittanceCompanyCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REMITTANCE_DOCUMENT_ID")
	public Document getRemittanceDocument() {
		return remittanceDocument;
	}

	public void setRemittanceDocument(Document remittanceDocument) {
		this.remittanceDocument = remittanceDocument;
	}

	@Column(name = "REMITTANCE_DOCUMENT_CODE")
	public String getRemittanceDocumentCode() {
		return remittanceDocumentCode;
	}

	public void setRemittanceDocumentCode(String remittanceDocumentCode) {
		this.remittanceDocumentCode = remittanceDocumentCode;
	}

	@Column(name = "REMITTANCE_DOCUMENT_YEAR")
	public BigDecimal getRemittanceDocumentFinancialYear() {
		return remittanceDocumentFinancialYear;
	}

	public void setRemittanceDocumentFinancialYear(BigDecimal remittanceDocumentFinancialYear) {
		this.remittanceDocumentFinancialYear = remittanceDocumentFinancialYear;
	}

	@Column(name = "REMITTANCE_DOCUMENT_NO")
	public BigDecimal getRemittanceDocumentNo() {
		return remittanceDocumentNo;
	}

	public void setRemittanceDocumentNo(BigDecimal remittanceDocumentNo) {
		this.remittanceDocumentNo = remittanceDocumentNo;
	}

	@Column(name = "IS_ACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/*@Column(name = "ASSIGNED_TO")
	public BigDecimal getAssingedTo() {
		return assingedTo;
	}

	public void setAssingedTo(BigDecimal assingedTo) {
		this.assingedTo = assingedTo;
	}*/
	@Column(name = "DOCUMENT_NO")
        public BigDecimal getDocumentNumber() {
        	  return documentNumber;
        }
	
        public void setDocumentNumber(BigDecimal documentNumber) {
        	  this.documentNumber = documentNumber;
        }
        @Column(name = "REMITTANCE_DATE")
        public Date getRemittanceDate() {
        	  return remittanceDate;
        }
        
        public void setRemittanceDate(Date remittanceDate) {
        	  this.remittanceDate = remittanceDate;
        }
        @Column(name = "FOREIGN_CURRENCY_AMOUNT")
        public BigDecimal getForiegnCurrencyAmount() {
        	  return foriegnCurrencyAmount;
        }
       
        public void setForiegnCurrencyAmount(BigDecimal foriegnCurrencyAmount) {
        	  this.foriegnCurrencyAmount = foriegnCurrencyAmount;
        }
        @Column(name = "FOREIGN_CURRENCY_NAME")
        public String getForiegnCurrencyName() {
        	  return foriegnCurrencyName;
        }
        
        public void setForiegnCurrencyName(String foriegnCurrencyName) {
        	  this.foriegnCurrencyName = foriegnCurrencyName;
        }
        	
        	
        
        }
