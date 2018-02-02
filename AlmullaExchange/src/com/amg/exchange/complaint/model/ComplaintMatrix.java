package com.amg.exchange.complaint.model;

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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.ServiceMaster;

@Entity
@Table(name = "EX_COMPLAINT_MATRIX")
public class ComplaintMatrix implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal complaintMatrixId;
	private CountryMaster applicationCountry;
	private ComplaintTypeMaster complaintTypeMaster;
	private CountryMaster fsCountry;
	private BankMaster bankMaster;
	private ServiceMaster serviceMaster;
	private ComplaintAction complaintAction;
	private ComplaintAssigned complaintAssigned;
	private ComplaintAssigned complaintDestinationId;
	private CommunicationMethod communicationMethod;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;

	

	public ComplaintMatrix(BigDecimal complaintMatrixId,
			CountryMaster applicationCountry,
			ComplaintTypeMaster complaintTypeMaster, CountryMaster fsCountry,
			BankMaster bankMaster, ServiceMaster serviceMaster,
			ComplaintAction complaintAction,
			ComplaintAssigned complaintAssigned,
			ComplaintAssigned complaintDestinationId,
			CommunicationMethod communicationMethod, String isActive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String remarks) {
		super();
		this.complaintMatrixId = complaintMatrixId;
		this.applicationCountry = applicationCountry;
		this.complaintTypeMaster = complaintTypeMaster;
		this.fsCountry = fsCountry;
		this.bankMaster = bankMaster;
		this.serviceMaster = serviceMaster;
		this.complaintAction = complaintAction;
		this.complaintAssigned = complaintAssigned;
		this.complaintDestinationId = complaintDestinationId;
		this.communicationMethod = communicationMethod;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
	}

	public ComplaintMatrix() {
	}
	
	@Id
	@GeneratedValue(generator = "ex_complaint_matrix_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_complaint_matrix_seq", sequenceName = "EX_COMPLAINT_MATRIX_SEQ", allocationSize = 1)
	@Column(name = "COMPLAINT_MATRIX_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComplaintMatrixId() {
		return complaintMatrixId;
	}

	public void setComplaintMatrixId(BigDecimal complaintMatrixId) {
		this.complaintMatrixId = complaintMatrixId;
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
	@JoinColumn(name = "COMPLAINT_TYPE_ID")
	public ComplaintTypeMaster getComplaintTypeMaster() {
		return complaintTypeMaster;
	}

	public void setComplaintTypeMaster(ComplaintTypeMaster complaintTypeMaster) {
		this.complaintTypeMaster = complaintTypeMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountry() {
		return fsCountry;
	}

	public void setFsCountry(CountryMaster fsCountry) {
		this.fsCountry = fsCountry;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBankMaster() {
		return bankMaster;
	}

	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_ID")
	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_ACTION_ID")
	public ComplaintAction getComplaintAction() {
		return complaintAction;
	}

	public void setComplaintAction(ComplaintAction complaintAction) {
		this.complaintAction = complaintAction;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_TAKEN_BY_ID")
	public ComplaintAssigned getcomplaintAssigned() {
		return complaintAssigned;
	}

	public void setComplaintAssigned(ComplaintAssigned complaintAssigned) {
		this.complaintAssigned = complaintAssigned;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_DESTINATION_ID")
	public ComplaintAssigned getComplaintDestinationId() {
		return complaintDestinationId;
	}

	public void setComplaintDestinationId(ComplaintAssigned complaintDestinationId) {
		this.complaintDestinationId = complaintDestinationId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMMUNICATION_METHOD_ID")
	public CommunicationMethod getCommunicationMethod() {
		return communicationMethod;
	}

	public void setCommunicationMethod(CommunicationMethod communicationMethod) {
		this.communicationMethod = communicationMethod;
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

	

}
