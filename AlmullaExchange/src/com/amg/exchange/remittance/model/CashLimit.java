package com.amg.exchange.remittance.model;

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

@Entity
@Table(name = "EX_CASH_LIMIT")
public class CashLimit implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal cashLimitId;
	private String roleLimitType;
	private CountryMaster country;
	private CashLimitType limitType;
	private BigDecimal limit1;
	private BigDecimal limit2;
	private String isActive;
	private String createdBy;
	private String modifiedBy;
	private Date createdDate;
	private Date modifiedDate;
	private String remarks;

	public CashLimit(BigDecimal cashLimitId, String roleLimitType, CountryMaster country, CashLimitType limitType, BigDecimal limit1, BigDecimal limit2, String isActive, String createdBy, String modifiedBy, Date createdDate, Date modifiedDate, String remarks) {
		super();
		this.cashLimitId = cashLimitId;
		this.roleLimitType = roleLimitType;
		this.country = country;
		this.limitType = limitType;
		this.limit1 = limit1;
		this.limit2 = limit2;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.remarks = remarks;
	}

	public CashLimit() {
	}

	@Id
	@GeneratedValue(generator = "fs_cash_limit_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "fs_cash_limit_seq", sequenceName = "EX_CASH_LIMIT_SEQ", allocationSize = 1)
	@Column(name = "CASH_LIMIT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCashLimitId() {
		return cashLimitId;
	}

	public void setCashLimitId(BigDecimal cashLimitId) {
		this.cashLimitId = cashLimitId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getCountry() {
		return country;
	}

	public void setCountry(CountryMaster country) {
		this.country = country;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LIMIT_TYPE_ID")
	public CashLimitType getLimitType() {
		return limitType;
	}

	@Column(name = "ROLE_LIMIT_TYPE")
	public String getRoleLimitType() {
		return roleLimitType;
	}

	public void setRoleLimitType(String roleLimitType) {
		this.roleLimitType = roleLimitType;
	}

	@Column(name = "LIMIT1")
	public BigDecimal getLimit1() {
		return limit1;
	}

	public void setLimitType(CashLimitType limitType) {
		this.limitType = limitType;
	}

	public void setLimit1(BigDecimal limit1) {
		this.limit1 = limit1;
	}

	@Column(name = "LIMIT2")
	public BigDecimal getLimit2() {
		return limit2;
	}

	public void setLimit2(BigDecimal limit2) {
		this.limit2 = limit2;
	}

	@Column(name = "ISACTIVE", length = 1)
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

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "CashLimit [cashLimitId=" + cashLimitId + ", roleLimitType=" + roleLimitType + ", country=" + country.getCountryId() + ", limitType=" + limitType.getLimitTypeId() + ", limit1=" + limit1 + ", limit2=" + limit2 + ", isActive=" + isActive + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", remarks=" + remarks + "]";
	}
}
