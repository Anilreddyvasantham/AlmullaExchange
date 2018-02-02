package com.amg.exchange.remittance.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_CASH_LIMIT_TYPE")
public class CashLimitType implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal limitTypeId;
	private String limitTypeCode;
	private String limitTypeDesc;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	@Id
	@GeneratedValue(generator = "fs_cash_limit_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_cash_limit_seq", sequenceName = "EX_CASH_LIMIT_SEQ", allocationSize = 1)
	@Column(name = "LIMIT_TYPE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLimitTypeId() {
		return limitTypeId;
	}

	public void setLimitTypeId(BigDecimal limitTypeId) {
		this.limitTypeId = limitTypeId;
	}

	@Column(name = "LIMIT_TYPE_CODE", length = 2)
	public String getLimitTypeCode() {
		return limitTypeCode;
	}

	public void setLimitTypeCode(String limitTypeCode) {
		this.limitTypeCode = limitTypeCode;
	}

	@Column(name = "LIMIT_TYPE_DESC", length = 2)
	public String getLimitTypeDesc() {
		return limitTypeDesc;
	}

	public void setLimitTypeDesc(String limitTypeDesc) {
		this.limitTypeDesc = limitTypeDesc;
	}

	@Column(name = "IS_ACTIVE", length = 1)
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "CREATED_BY", length = 30)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "MODIFIED_BY", length = 30)
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
