package com.amg.exchange.treasury.model;

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

import com.amg.exchange.treasury.model.BankMaster;

@Entity
@Table(name = "EX_BANK_PREFIX")
public class BankPrefix implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal bankPrefixId;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String bankCode;
	private BankMaster  fsBankMaster;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String bankPrefix;

	@Id
	@GeneratedValue(generator = "bank_prefix_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "bank_prefix_seq", sequenceName = "EX_BANK_PREFIX_SEQ", allocationSize = 1)
	@Column(name = "BANK_PREFEX_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getBankPrefixId() {
		return bankPrefixId;
	}

	public void setBankPrefixId(BigDecimal bankPrefixId) {
		this.bankPrefixId = bankPrefixId;
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

	@Column(name = "BANK_CODE")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getFsBankMaster() {
		return fsBankMaster;
	}

	public void setFsBankMaster(BankMaster fsBankMaster) {
		this.fsBankMaster = fsBankMaster;
	}

	public BankPrefix() {
		super();

	}

	

	public BankPrefix(BigDecimal bankPrefixId, String isActive, String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String bankCode, BankMaster fsBankMaster, String approvedBy, Date approvedDate,
			String remarks) {
		super();
		this.bankPrefixId = bankPrefixId;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.bankCode = bankCode;
		this.fsBankMaster = fsBankMaster;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
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
	@Column(name = "BANK_PREFIX")
	public String getBankPrefix() {
		return bankPrefix;
	}

	public void setBankPrefix(String bankPrefix) {
		this.bankPrefix = bankPrefix;
	}

	
	
	
}
