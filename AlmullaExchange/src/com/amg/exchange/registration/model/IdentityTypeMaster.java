package com.amg.exchange.registration.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.amg.exchange.common.model.BusinessComponent;

/*******************************************************************************************************************
 * File : Customer.java
 * 
 * Project : AlmullaExchange
 * 
 * Package : com.amg.exchange.model
 * 
 * Created :
 * Date : 25-Dec-2014
 * By : Nazish Ehsan Hashmi
 * Revision:
 * 
 * Last Change:
 * Date : 25-Dec-2014
 * By : Nazish Ehsan Hashmi
 * Revision:
 * 
 * Description: TODO
 ********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_IDENTITY_TYPE_MASTER")
public class IdentityTypeMaster implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal identityTypeId;
	private BusinessComponent businessComponent;
	private BigDecimal applicationCountryId;
	private BigDecimal businessComponentId;
	private String identityType;
	private String validity;
	private String isActive;
	private String ocrStstus;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvBy;
	private Date approvDate;
	private String remarks;
	private String customerType;
	
	public IdentityTypeMaster() {

	}

	public IdentityTypeMaster(BigDecimal identityTypeId) {
		this.identityTypeId = identityTypeId;

	}

	public IdentityTypeMaster(BigDecimal identityTypeId,
			BigDecimal applicationCountryId, BigDecimal businessComponentId,
			String identityType, String validity, 
			String isActive, String ocrStstus, String createdBy,
			Date createdDate, String modifiedBy, Date modifiedDate,
			String approvBy, Date approvDate, String remarks,BusinessComponent businessComponent,
			String customerType) {
		super();
		this.identityTypeId = identityTypeId;
		this.applicationCountryId = applicationCountryId;
		this.businessComponentId = businessComponentId;
		this.identityType = identityType;
		this.validity = validity;
		this.isActive = isActive;
		this.ocrStstus = ocrStstus;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvBy = approvBy;
		this.approvDate = approvDate;
		this.remarks = remarks;
		this.businessComponent=businessComponent;
		this.customerType=customerType;
	}

	@Id
	@GeneratedValue(generator = "fs_identity_type_master_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_identity_type_master_seq", sequenceName = "FS_IDENTITY_TYPE_MASTER_SEQ", allocationSize = 1)
	@Column(name = "IDENTITY_TYPE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdentityTypeId() {
		return identityTypeId;
	}

	public void setIdentityTypeId(BigDecimal identityTypeId) {
		this.identityTypeId = identityTypeId;
	}

	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}

	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	@Column(name = "BUSINESS_COMPONENT_ID")
	public BigDecimal getBusinessComponentId() {
		return businessComponentId;
	}

	public void setBusinessComponentId(BigDecimal businessComponentId) {
		this.businessComponentId = businessComponentId;
	}

	@Column(name = "IDENTITY_TYPE")
	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	@Column(name = "VALIDITY")
	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "OCR_STATUS")
	public String getOcrStstus() {
		return ocrStstus;
	}

	public void setOcrStstus(String ocrStstus) {
		this.ocrStstus = ocrStstus;
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
	public String getApprovBy() {
		return approvBy;
	}

	public void setApprovBy(String approvBy) {
		this.approvBy = approvBy;
	}

	@Column(name = "APPROVED_DATE")
	public Date getApprovDate() {
		return approvDate;
	}

	public void setApprovDate(Date approvDate) {
		this.approvDate = approvDate;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BIZ_COMPONENT_ID", nullable = false)
	public BusinessComponent getBusinessComponent() {
		return businessComponent;
	}

	public void setBusinessComponent(BusinessComponent businessComponent) {
		this.businessComponent = businessComponent;
	}

	@Column(name = "CUSTOMER_TYPE")
	public String getCustomerType() {
		return customerType;
	}
	
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	

}
