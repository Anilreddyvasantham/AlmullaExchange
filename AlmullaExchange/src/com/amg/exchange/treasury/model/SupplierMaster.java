package com.amg.exchange.treasury.model;
// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CountryMaster;

/**
 * ExSupplierMaster Created by Chennai ODC
 */
@Entity
@Table(name = "EX_SUPPLIER_MASTER")
public class SupplierMaster implements java.io.Serializable {

	/**
	 * Generated Serialize ID
	 */
	private static final long serialVersionUID = 8129440983201932977L;
	private BigDecimal supplierMasterId;
	private CountryMaster fsCountryMaster;
	private String supplierCode;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Set<DealSupplier> exDealSuppliers = new HashSet<DealSupplier>(0);

	public SupplierMaster() {
	}

	public SupplierMaster(BigDecimal supplierMasterId) {
		this.supplierMasterId = supplierMasterId;
	}

	public SupplierMaster(BigDecimal supplierMasterId,
			CountryMaster fsCountryMaster, String supplierCode,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, Set<DealSupplier> exDealSuppliers) {
		this.supplierMasterId = supplierMasterId;
		this.fsCountryMaster = fsCountryMaster;
		this.supplierCode = supplierCode;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.exDealSuppliers = exDealSuppliers;
	}
	
	@Id
	@GeneratedValue(generator="ex_supplier_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_supplier_master_seq",sequenceName="EX_SUPPLIER_MASTER_SEQ",allocationSize=1)
	@Column(name = "SUPPLIER_MASTER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getSupplierMasterId() {
		return this.supplierMasterId;
	}

	public void setSupplierMasterId(BigDecimal supplierMasterId) {
		this.supplierMasterId = supplierMasterId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "SUPPLIER_CODE", length = 50)
	public String getSupplierCode() {
		return this.supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Serializable getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 15)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Serializable getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exSupplierMaster")
	public Set<DealSupplier> getExDealSuppliers() {
		return this.exDealSuppliers;
	}

	public void setExDealSuppliers(Set<DealSupplier> exDealSuppliers) {
		this.exDealSuppliers = exDealSuppliers;
	}

}
