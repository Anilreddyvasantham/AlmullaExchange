package com.amg.exchange.treasury.model;
// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

import java.math.BigDecimal;

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
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.LanguageType;

/**
 * ExSupplierDetails Created by Chennai ODC
 */
@Entity
@Table(name = "EX_SUPPLIER_DETAILS")
public class SupplierDetails implements java.io.Serializable {

	/**
	 * Generated Serializable UID
	 */
	private static final long serialVersionUID = -2661323531336214109L;
	private BigDecimal supplierDetailsId;
	private LanguageType fsLanguageType;
	private BigDecimal supplierMasterId;
	private String supplierName;
	private String supplierDescription;

	public SupplierDetails() {
	}

	public SupplierDetails(BigDecimal supplierDetailsId) {
		this.supplierDetailsId = supplierDetailsId;
	}

	public SupplierDetails(BigDecimal supplierDetailsId,
			LanguageType fsLanguageType, BigDecimal supplierMasterId,
			String supplierName, String supplierDescription) {
		this.supplierDetailsId = supplierDetailsId;
		this.fsLanguageType = fsLanguageType;
		this.supplierMasterId = supplierMasterId;
		this.supplierName = supplierName;
		this.supplierDescription = supplierDescription;
	}
	
	@Id
	@GeneratedValue(generator="ex_supplier_details_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_supplier_details_seq",sequenceName="EX_SUPPLIER_DETAILS_SEQ",allocationSize=1)
	@Column(name = "SUPPLIER_DETAILS_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getSupplierDetailsId() {
		return this.supplierDetailsId;
	}

	public void setSupplierDetailsId(BigDecimal supplierDetailsId) {
		this.supplierDetailsId = supplierDetailsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	@Column(name = "SUPPLIER_MASTER_ID", precision = 22, scale = 0)
	public BigDecimal getSupplierMasterId() {
		return this.supplierMasterId;
	}

	public void setSupplierMasterId(BigDecimal supplierMasterId) {
		this.supplierMasterId = supplierMasterId;
	}

	@Column(name = "SUPPLIER_NAME", length = 50)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "SUPPLIER_DESCRIPTION", length = 100)
	public String getSupplierDescription() {
		return this.supplierDescription;
	}

	public void setSupplierDescription(String supplierDescription) {
		this.supplierDescription = supplierDescription;
	}

}
