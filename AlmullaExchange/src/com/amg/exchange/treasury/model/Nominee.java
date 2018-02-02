package com.amg.exchange.treasury.model;

// Generated Jun 5, 2014 3:20:12  Created by Chennai ODC

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

import com.amg.exchange.registration.model.Customer;

/**
 * Nominee Created by Chennai ODC
 */
@Entity
@Table(name = "FS_NOMINEE")
public class Nominee implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal nomineeId;
	private Customer fsCustomerByNomineeCustId;
	private Customer fsCustomerByNominatorCustId;
	private Date effectiveDate;
	private Date endDate;
	private String status;
	private String createdBy;
	private String updatedBy;
	private Date creationDate;
	private Date lastUpdated;

	public Nominee() {
	}

	public Nominee(BigDecimal nomineeId) {
		this.nomineeId = nomineeId;
	}

	public Nominee(BigDecimal nomineeId,
			Customer fsCustomerByNomineeCustId,
			Customer fsCustomerByNominatorCustId, Date effectiveDate,
			Date endDate, String status, String createdBy,
			String updatedBy, Date creationDate,
			Date lastUpdated) {
		this.nomineeId = nomineeId;
		this.fsCustomerByNomineeCustId = fsCustomerByNomineeCustId;
		this.fsCustomerByNominatorCustId = fsCustomerByNominatorCustId;
		this.effectiveDate = effectiveDate;
		this.endDate = endDate;
		this.status = status;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.creationDate = creationDate;
		this.lastUpdated = lastUpdated;
	}

	/*@Id
	@TableGenerator(name="nomineeid",table="fs_nomineeidpk",pkColumnName="nomineeidkey",pkColumnValue="nomineeidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="nomineeid")
	@Column(name = "NOMINEE_ID", unique = true, nullable = false, precision = 22, scale = 0)*/
	
	//Added by kani begin
	@Id
	@GeneratedValue(generator="fs_nominee_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_nominee_seq",sequenceName="FS_NOMINEE_SEQ",allocationSize=1)
	@Column(name = "NOMINEE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	
	//added by kani end
	
	
	public BigDecimal getNomineeId() {
		return this.nomineeId;
	}

	public void setNomineeId(BigDecimal nomineeId) {
		this.nomineeId = nomineeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NOMINEE_CUST_ID")
	public Customer getFsCustomerByNomineeCustId() {
		return this.fsCustomerByNomineeCustId;
	}

	public void setFsCustomerByNomineeCustId(
			Customer fsCustomerByNomineeCustId) {
		this.fsCustomerByNomineeCustId = fsCustomerByNomineeCustId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NOMINATOR_CUST_ID")
	public Customer getFsCustomerByNominatorCustId() {
		return this.fsCustomerByNominatorCustId;
	}

	public void setFsCustomerByNominatorCustId(
			Customer fsCustomerByNominatorCustId) {
		this.fsCustomerByNominatorCustId = fsCustomerByNominatorCustId;
	}

	@Column(name = "EFFICTIVE_DATE")
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column(name = "END_DATE")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATED_BY", length = 30)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "UPDATED_BY", length = 30)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "CREATION_DATE")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "LAST_UPDATED")
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
