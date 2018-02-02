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

/**
 * ExSupplierInstructionMaster Created by Chennai ODC
 */
@Entity
@Table(name = "EX_SUPPLIER_INSTRUCTION_MASTER")
public class SupplierInstructionMaster implements Serializable {

	/**
	 * Generated Serialize Add
	 */
	private static final long serialVersionUID = 4206580424731720040L;
	private BigDecimal supplierInstId;
	private CurrencyMaster exCurrencyMaster;
	private BankMaster exBankMaster;
	private short instuctionRef;
	private String instuctionDes;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Set<DealPurchase> exDealPurchases = new HashSet<DealPurchase>(0);
	private Set<DealSales> exDealSaleses = new HashSet<DealSales>(0);
	private Set<SupplierInstructionDetail> exSupplierInstructionDetails = new HashSet<SupplierInstructionDetail>(0);

	public SupplierInstructionMaster() {
	}

	public SupplierInstructionMaster(BigDecimal supplierInstId,
			BankMaster exBankMaster, short instuctionRef) {
		this.supplierInstId = supplierInstId;
		this.exBankMaster = exBankMaster;
		this.instuctionRef = instuctionRef;
	}

	public SupplierInstructionMaster(BigDecimal supplierInstId,
			CurrencyMaster exCurrencyMaster, BankMaster exBankMaster,
			short instuctionRef, String instuctionDes, String isactive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, Set<DealPurchase> exDealPurchases, Set<DealSales> exDealSaleses,
			Set<SupplierInstructionDetail> exSupplierInstructionDetails) {
		this.supplierInstId = supplierInstId;
		this.exCurrencyMaster = exCurrencyMaster;
		this.exBankMaster = exBankMaster;
		this.instuctionRef = instuctionRef;
		this.instuctionDes = instuctionDes;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.exDealPurchases = exDealPurchases;
		this.exDealSaleses = exDealSaleses;
		this.exSupplierInstructionDetails = exSupplierInstructionDetails;
	}
	
	@Id
	@GeneratedValue(generator="ex_suppl_instru_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_suppl_instru_master_seq",sequenceName="EX_SUPPL_INSTRU_MASTER_SEQ",allocationSize=1)
	@Column(name = "SUPPLIER_INST_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getSupplierInstId() {
		return this.supplierInstId;
	}

	public void setSupplierInstId(BigDecimal supplierInstId) {
		this.supplierInstId = supplierInstId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID")
	public CurrencyMaster getExCurrencyMaster() {
		return this.exCurrencyMaster;
	}

	public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		this.exCurrencyMaster = exCurrencyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID", nullable = false)
	public BankMaster getExBankMaster() {
		return this.exBankMaster;
	}

	public void setExBankMaster(BankMaster exBankMaster) {
		this.exBankMaster = exBankMaster;
	}

	@Column(name = "INSTUCTION_REF", nullable = false, precision = 3, scale = 0)
	public short getInstuctionRef() {
		return this.instuctionRef;
	}

	public void setInstuctionRef(short instuctionRef) {
		this.instuctionRef = instuctionRef;
	}

	@Column(name = "INSTUCTION_DES", length = 30)
	public String getInstuctionDes() {
		return this.instuctionDes;
	}

	public void setInstuctionDes(String instuctionDes) {
		this.instuctionDes = instuctionDes;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
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
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exSupplierInstructionMaster")
	public Set<DealPurchase> getExDealPurchases() {
		return this.exDealPurchases;
	}

	public void setExDealPurchases(Set<DealPurchase> exDealPurchases) {
		this.exDealPurchases = exDealPurchases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exSupplierInstructionMaster")
	public Set<DealSales> getExDealSaleses() {
		return this.exDealSaleses;
	}

	public void setExDealSaleses(Set<DealSales> exDealSaleses) {
		this.exDealSaleses = exDealSaleses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exSupplierInstructionMaster")
	public Set<SupplierInstructionDetail> getExSupplierInstructionDetails() {
		return this.exSupplierInstructionDetails;
	}

	public void setExSupplierInstructionDetails(Set<SupplierInstructionDetail> exSupplierInstructionDetails) {
		this.exSupplierInstructionDetails = exSupplierInstructionDetails;
	}

}
