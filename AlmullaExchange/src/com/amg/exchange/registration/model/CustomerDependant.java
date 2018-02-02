package com.amg.exchange.registration.model;

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

import com.amg.exchange.remittance.model.Relations;

/*******************************************************************************************************************

File		: PartnerAuthorized.java

Project	: AlmullaExchange

Package	: com.amg.exchange.registration.model

Created	:	
				Date	: 22-JULY-2015 
				By		: Nazish Ehsan Hashmi
				Revision:

Last Change:
				Date	: 22-JULY-2015 
				By		: Nazish Ehsan Hashmi
				Revision:

Description:
********************************************************************************************************************/
@Entity
@Table(name = "FS_CUSTOMER_DEPENDANT")
public class CustomerDependant implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal customerDependantId;
	private Customer fsCustomer;
	private Customer fsCustomerByRefCustomerId;
	private Relations exRelations;
	private BigDecimal customerRefrenceNo;
	private BigDecimal dependantCustomerRefNo;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	public CustomerDependant() {
		
	}
	public CustomerDependant(BigDecimal customerDependantId,
			Customer fsCustomer, Customer fsCustomerByRefCustomerId,
			Relations exRelations, BigDecimal customerRefrenceNo,
			BigDecimal dependantCustomerRefNo, String isActive,
			String createdBy, Date createdDate, String updatedBy,
			Date updatedDate) {
		super();
		this.customerDependantId = customerDependantId;
		this.fsCustomer = fsCustomer;
		this.fsCustomerByRefCustomerId = fsCustomerByRefCustomerId;
		this.exRelations = exRelations;
		this.customerRefrenceNo = customerRefrenceNo;
		this.dependantCustomerRefNo = dependantCustomerRefNo;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}
	@Id
	@GeneratedValue(generator = "fs_customer_dependant_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_customer_dependant_seq", sequenceName = "FS_CUSTOMER_DEPENDANT_SEQ", allocationSize = 1)
	@Column(name = "CUSTOMER_DEPENDANT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCustomerDependantId() {
		return customerDependantId;
	}
	public void setCustomerDependantId(BigDecimal customerDependantId) {
		this.customerDependantId = customerDependantId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}
	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPENDANT_CUSTOMER_ID")
	public Customer getFsCustomerByRefCustomerId() {
		return fsCustomerByRefCustomerId;
	}
	public void setFsCustomerByRefCustomerId(Customer fsCustomerByRefCustomerId) {
		this.fsCustomerByRefCustomerId = fsCustomerByRefCustomerId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIP_ID")
	public Relations getExRelations() {
		return exRelations;
	}
	public void setExRelations(Relations exRelations) {
		this.exRelations = exRelations;
	}
	
	@Column(name = "CUSTOMER_REFERENCE")
	public BigDecimal getCustomerRefrenceNo() {
		return customerRefrenceNo;
	}
	public void setCustomerRefrenceNo(BigDecimal customerRefrenceNo) {
		this.customerRefrenceNo = customerRefrenceNo;
	}
	
	@Column(name = "DEPENDANT_CUSTOMER_REF")
	public BigDecimal getDependantCustomerRefNo() {
		return dependantCustomerRefNo;
	}
	public void setDependantCustomerRefNo(BigDecimal dependantCustomerRefNo) {
		this.dependantCustomerRefNo = dependantCustomerRefNo;
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
	
	@Column(name = "UPDATED_BY")
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Column(name = "UPDATED_DATE")
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	

}
