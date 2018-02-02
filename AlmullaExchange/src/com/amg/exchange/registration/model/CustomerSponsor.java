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
				Date	: 1-August-2015 
				By		: Nazish Ehsan Hashmi
				Revision:

Last Change:
				Date	: 1-Aug-2015 
				By		: Nazish Ehsan Hashmi
				Revision:

Description:
********************************************************************************************************************/
@Entity
@Table(name = "FS_CUSTOMER_SPONSOR")
public class CustomerSponsor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal customerSponsorId;
	private Customer fsCustomer;
	private String sponsorName;
	private Relations exRelations;
	private BigDecimal customerRefrenceNo;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	public CustomerSponsor() {
		
	}
	
	public CustomerSponsor(BigDecimal customerSponsorId, Customer fsCustomer,
			String sponsorName, Relations exRelations,
			BigDecimal customerRefrenceNo, String isActive, String createdBy,
			Date createdDate, String updatedBy, Date updatedDate) {
		super();
		this.customerSponsorId = customerSponsorId;
		this.fsCustomer = fsCustomer;
		this.sponsorName = sponsorName;
		this.exRelations = exRelations;
		this.customerRefrenceNo = customerRefrenceNo;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	@Id
	@GeneratedValue(generator = "fs_customer_sponsor_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_customer_sponsor_seq", sequenceName = "FS_CUSTOMER_SPONSOR_SEQ", allocationSize = 1)
	@Column(name = "CUSTOMER_SPONSOR_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCustomerSponsorId() {
		return customerSponsorId;
	}


	public void setCustomerSponsorId(BigDecimal customerSponsorId) {
		this.customerSponsorId = customerSponsorId;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	public Customer getFsCustomer() {
		return fsCustomer;
	}

	public void setFsCustomer(Customer fsCustomer) {
		this.fsCustomer = fsCustomer;
	}

	
	@Column(name = " SPONSOR_NAME")
	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
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
